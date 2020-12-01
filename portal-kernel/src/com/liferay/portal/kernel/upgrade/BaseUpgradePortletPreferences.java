/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.portal.kernel.upgrade;

import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.dao.jdbc.AutoBatchPreparedStatementUtil;
import com.liferay.portal.kernel.model.ModelHintsUtil;
import com.liferay.portal.kernel.model.PortletConstants;
import com.liferay.portal.kernel.model.PortletPreferenceValue;
import com.liferay.portal.kernel.portlet.PortletPreferencesFactoryUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.LoggingTimer;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.xml.simple.Element;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import javax.portlet.PortletPreferences;
import javax.portlet.ReadOnlyException;

/**
 * @author Jorge Ferrer
 * @author Brian Wing Shun Chan
 */
public abstract class BaseUpgradePortletPreferences extends UpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		updatePortletPreferences();
	}

	protected long getCompanyId(String sql, long primaryKey) throws Exception {
		long companyId = 0;

		try (PreparedStatement ps = connection.prepareStatement(sql)) {
			ps.setLong(1, primaryKey);

			try (ResultSet rs = ps.executeQuery()) {
				while (rs.next()) {
					companyId = rs.getLong("companyId");
				}
			}
		}

		return companyId;
	}

	protected Object[] getGroup(long groupId) throws Exception {
		Object[] group = null;

		try (PreparedStatement ps = connection.prepareStatement(
				"select companyId from Group_ where groupId = ?")) {

			ps.setLong(1, groupId);

			try (ResultSet rs = ps.executeQuery()) {
				while (rs.next()) {
					long companyId = rs.getLong("companyId");

					group = new Object[] {groupId, companyId};
				}
			}
		}

		return group;
	}

	protected Object[] getLayout(long plid) throws Exception {
		Object[] layout = null;

		try (PreparedStatement ps = connection.prepareStatement(
				"select groupId, companyId, privateLayout, layoutId from " +
					"Layout where plid = ?")) {

			ps.setLong(1, plid);

			try (ResultSet rs = ps.executeQuery()) {
				while (rs.next()) {
					long groupId = rs.getLong("groupId");
					long companyId = rs.getLong("companyId");
					boolean privateLayout = rs.getBoolean("privateLayout");
					long layoutId = rs.getLong("layoutId");

					layout = new Object[] {
						groupId, companyId, privateLayout, layoutId
					};
				}
			}
		}

		if (layout == null) {
			layout = getLayoutRevision(plid);
		}

		return layout;
	}

	protected Object[] getLayoutRevision(long layoutRevisionId)
		throws Exception {

		Object[] layoutRevision = null;

		try (PreparedStatement ps = connection.prepareStatement(
				"select groupId, companyId, privateLayout, layoutRevisionId " +
					"from LayoutRevision where layoutRevisionId = ?")) {

			ps.setLong(1, layoutRevisionId);

			try (ResultSet rs = ps.executeQuery()) {
				while (rs.next()) {
					long groupId = rs.getLong("groupId");
					long companyId = rs.getLong("companyId");
					boolean privateLayout = rs.getBoolean("privateLayout");
					long layoutId = rs.getLong("layoutRevisionId");

					layoutRevision = new Object[] {
						groupId, companyId, privateLayout, layoutId
					};
				}
			}
		}

		return layoutRevision;
	}

	protected String getLayoutUuid(long plid, long layoutId) throws Exception {
		Object[] layout = getLayout(plid);

		if (layout == null) {
			return null;
		}

		String uuid = null;

		try (PreparedStatement ps = connection.prepareStatement(
				"select uuid_ from Layout where groupId = ? and " +
					"privateLayout = ? and layoutId = ?")) {

			long groupId = (Long)layout[0];
			boolean privateLayout = (Boolean)layout[2];

			ps.setLong(1, groupId);
			ps.setBoolean(2, privateLayout);
			ps.setLong(3, layoutId);

			try (ResultSet rs = ps.executeQuery()) {
				if (rs.next()) {
					uuid = rs.getString("uuid_");
				}
			}
		}

		return uuid;
	}

	protected String[] getPortletIds() {
		return new String[0];
	}

	protected String getUpdatePortletPreferencesWhereClause() {
		String[] portletIds = getPortletIds();

		if (portletIds.length == 0) {
			throw new IllegalArgumentException(
				"Subclasses must override getPortletIds or " +
					"getUpdatePortletPreferencesWhereClause");
		}

		StringBundler sb = new StringBundler((portletIds.length * 5) - 1);

		for (int i = 0; i < portletIds.length; i++) {
			String portletId = portletIds[i];

			sb.append("PortletPreferences.portletId ");

			if (portletId.contains(StringPool.PERCENT)) {
				sb.append(" like '");
				sb.append(portletId);
				sb.append("'");
			}
			else {
				sb.append(" = '");
				sb.append(portletId);
				sb.append("'");
			}

			if ((i + 1) < portletIds.length) {
				sb.append(" or ");
			}
		}

		return sb.toString();
	}

	protected void updatePortletPreferences() throws Exception {
		try (LoggingTimer loggingTimer = new LoggingTimer()) {
			if (hasColumn("PortletPreferences", "preferences")) {
				_updatePortletPreferences();
			}
			else {
				_updatePortletPreferenceValues();
			}
		}
	}

	protected void upgradeMultiValuePreference(
			PortletPreferences portletPreferences, String key)
		throws ReadOnlyException {

		String value = portletPreferences.getValue(key, StringPool.BLANK);

		if (Validator.isNotNull(value)) {
			portletPreferences.setValues(key, StringUtil.split(value));
		}
	}

	protected abstract String upgradePreferences(
			long companyId, long ownerId, int ownerType, long plid,
			String portletId, String xml)
		throws Exception;

	private Map<String, PreferenceValues> _getPreferenceValuesMap(
			PreparedStatement selectPreparedStatement)
		throws Exception {

		Map<String, PreferenceValues> preferenceValuesMap = new HashMap<>();

		try (ResultSet resultSet = selectPreparedStatement.executeQuery()) {
			while (resultSet.next()) {
				long portletPreferenceValueId = resultSet.getLong(
					"portletPreferenceValueId");
				String name = resultSet.getString("name");

				String value = resultSet.getString("smallValue");

				if (Validator.isBlank(value)) {
					value = resultSet.getString("largeValue");
				}

				boolean readOnly = resultSet.getBoolean("readOnly");

				PreferenceValues preferenceValues =
					preferenceValuesMap.computeIfAbsent(
						name, key -> new PreferenceValues());

				preferenceValues._portletPreferenceValueIds.add(
					portletPreferenceValueId);
				preferenceValues._readOnly |= readOnly;
				preferenceValues._values.add(value);
			}
		}

		return preferenceValuesMap;
	}

	private String _toXMLString(Map<String, PreferenceValues> preferenceMap) {
		if (preferenceMap.isEmpty()) {
			return PortletConstants.DEFAULT_PREFERENCES;
		}

		Element portletPreferencesElement = new Element(
			"portlet-preferences", false);

		for (Map.Entry<String, PreferenceValues> entry :
				preferenceMap.entrySet()) {

			Element preferenceElement = portletPreferencesElement.addElement(
				"preference");

			preferenceElement.addElement("name", entry.getKey());

			PreferenceValues preferenceValues = entry.getValue();

			for (String value : preferenceValues._values) {
				preferenceElement.addElement("value", value);
			}

			if (preferenceValues._readOnly) {
				preferenceElement.addElement("read-only", Boolean.TRUE);
			}
		}

		return portletPreferencesElement.toXMLString();
	}

	private void _updatePortletPreferences() throws Exception {
		StringBundler sb = new StringBundler(5);

		sb.append("select portletPreferencesId, companyId, ownerId, ");
		sb.append("ownerType, plid, portletId, preferences from ");
		sb.append("PortletPreferences");

		String whereClause = getUpdatePortletPreferencesWhereClause();

		if (Validator.isNotNull(whereClause)) {
			sb.append(" where ");
			sb.append(whereClause);
		}

		try (PreparedStatement ps1 = connection.prepareStatement(sb.toString());
			PreparedStatement ps2 =
				AutoBatchPreparedStatementUtil.concurrentAutoBatch(
					connection,
					"update PortletPreferences set preferences = ? where " +
						"portletPreferencesId = ?");
			PreparedStatement ps3 =
				AutoBatchPreparedStatementUtil.concurrentAutoBatch(
					connection,
					"delete from PortletPreferences where " +
						"portletPreferencesId = ?");
			ResultSet rs = ps1.executeQuery()) {

			while (rs.next()) {
				long portletPreferencesId = rs.getLong("portletPreferencesId");
				long companyId = rs.getLong("companyId");

				if (companyId > 0) {
					int ownerType = rs.getInt("ownerType");
					long plid = rs.getLong("plid");
					long ownerId = rs.getLong("ownerId");
					String portletId = rs.getString("portletId");
					String preferences = GetterUtil.getString(
						rs.getString("preferences"));

					String newPreferences = upgradePreferences(
						companyId, ownerId, ownerType, plid, portletId,
						preferences);

					if (!preferences.equals(newPreferences)) {
						ps2.setString(1, newPreferences);
						ps2.setLong(2, portletPreferencesId);

						ps2.addBatch();
					}
				}
				else {
					ps3.setLong(1, portletPreferencesId);

					ps3.addBatch();
				}
			}

			ps2.executeBatch();

			ps3.executeBatch();
		}
	}

	private void _updatePortletPreferenceValues() throws Exception {
		StringBundler sb = new StringBundler(5);

		sb.append("select ctCollectionId, portletPreferencesId, companyId, ");
		sb.append("ownerId, ownerType, plid, portletId from ");
		sb.append("PortletPreferences");

		String whereClause = getUpdatePortletPreferencesWhereClause();

		if (Validator.isNotNull(whereClause)) {
			sb.append(" where ");
			sb.append(whereClause);
		}

		try (PreparedStatement ps1 = connection.prepareStatement(sb.toString());
			PreparedStatement ps2 = connection.prepareStatement(
				"select portletPreferenceValueId, largeValue, name, " +
					"readOnly, smallValue from PortletPreferenceValue where " +
						"portletPreferencesId = ? order by index_ asc");
			PreparedStatement ps3 =
				AutoBatchPreparedStatementUtil.concurrentAutoBatch(
					connection,
					StringBundler.concat(
						"insert into PortletPreferenceValue (mvccVersion, ",
						"ctCollectionId, portletPreferenceValueId, companyId, ",
						"portletPreferencesId, index_, largeValue, name, ",
						"readOnly, smallValue) values (0, ?, ?, ?, ?, ?, ?, ",
						"?, ?, ?)"));
			PreparedStatement ps4 =
				AutoBatchPreparedStatementUtil.concurrentAutoBatch(
					connection,
					"update PortletPreferenceValue set largeValue = ?, " +
						"readOnly = ?, smallValue = ? where " +
							"portletPreferenceValueId = ?");
			PreparedStatement ps5 =
				AutoBatchPreparedStatementUtil.concurrentAutoBatch(
					connection,
					"delete from PortletPreferenceValue where " +
						"portletPreferenceValueId = ?");
			PreparedStatement ps6 =
				AutoBatchPreparedStatementUtil.concurrentAutoBatch(
					connection,
					"delete from PortletPreferences where " +
						"portletPreferencesId = ?");
			PreparedStatement ps7 =
				AutoBatchPreparedStatementUtil.concurrentAutoBatch(
					connection,
					"delete from PortletPreferenceValue where " +
						"portletPreferencesId = ?");
			ResultSet rs = ps1.executeQuery()) {

			while (rs.next()) {
				long portletPreferencesId = rs.getLong("portletPreferencesId");
				long companyId = rs.getLong("companyId");

				if (companyId > 0) {
					int ownerType = rs.getInt("ownerType");
					long plid = rs.getLong("plid");
					long ownerId = rs.getLong("ownerId");
					String portletId = rs.getString("portletId");

					ps2.setLong(1, portletPreferencesId);

					Map<String, PreferenceValues> preferenceValuesMap =
						_getPreferenceValuesMap(ps2);

					String preferences = _toXMLString(preferenceValuesMap);

					String newPreferences = upgradePreferences(
						companyId, ownerId, ownerType, plid, portletId,
						preferences);

					if (preferences.equals(newPreferences)) {
						continue;
					}

					_upgradePortletPreferenceValues(
						preferenceValuesMap, rs.getLong("ctCollectionId"),
						portletPreferencesId, companyId, newPreferences, ps3,
						ps4, ps5);
				}
				else {
					ps6.setLong(1, portletPreferencesId);

					ps6.addBatch();

					ps7.setLong(1, portletPreferencesId);

					ps7.addBatch();
				}
			}

			ps3.executeBatch();

			ps4.executeBatch();

			ps5.executeBatch();

			ps6.executeBatch();

			ps7.executeBatch();
		}
	}

	private void _upgradePortletPreferenceValues(
			Map<String, PreferenceValues> preferenceMap, long ctCollectionId,
			long portletPreferencesId, long companyId, String newPreferences,
			PreparedStatement insertPreparedStatement,
			PreparedStatement updatePreparedStatement,
			PreparedStatement deletePreparedStatement)
		throws Exception {

		PortletPreferences portletPreferences =
			PortletPreferencesFactoryUtil.fromDefaultXML(newPreferences);

		Map<String, Map.Entry<PreferenceValues, PreferenceValues>>
			preferenceEntries = new HashMap<>(preferenceMap.size());

		int newCount = 0;

		Map<String, String[]> newPreferenceMap = portletPreferences.getMap();

		for (Map.Entry<String, String[]> entry : newPreferenceMap.entrySet()) {
			String[] values = entry.getValue();

			if (values == null) {
				continue;
			}

			int size = 0;

			String name = entry.getKey();

			PreferenceValues preferenceValues = preferenceMap.remove(name);

			if (preferenceValues != null) {
				size = preferenceValues._values.size();
			}

			if (values.length > size) {
				newCount += values.length - size;
			}

			PreferenceValues newPreferenceValues = new PreferenceValues();

			newPreferenceValues._readOnly = portletPreferences.isReadOnly(
				entry.getKey());

			Collections.addAll(newPreferenceValues._values, values);

			preferenceEntries.put(
				name,
				new AbstractMap.SimpleImmutableEntry<>(
					preferenceValues, newPreferenceValues));
		}

		for (PreferenceValues portletPreferenceValues :
				preferenceMap.values()) {

			for (long portletPreferenceValueId :
					portletPreferenceValues._portletPreferenceValueIds) {

				deletePreparedStatement.setLong(1, portletPreferenceValueId);

				deletePreparedStatement.addBatch();
			}
		}

		long batchCounter = 0;

		if (newCount > 0) {
			batchCounter = increment(
				PortletPreferenceValue.class.getName(), newCount);

			batchCounter -= newCount;
		}

		int smallValueMaxLength = ModelHintsUtil.getMaxLength(
			PortletPreferenceValue.class.getName(), "smallValue");

		for (Map.Entry<String, Map.Entry<PreferenceValues, PreferenceValues>>
				entry : preferenceEntries.entrySet()) {

			Map.Entry<PreferenceValues, PreferenceValues>
				preferenceValuesEntry = entry.getValue();

			PreferenceValues oldPreferenceValues =
				preferenceValuesEntry.getKey();

			PreferenceValues newPreferenceValues =
				preferenceValuesEntry.getValue();

			List<String> newValues = newPreferenceValues._values;

			int oldSize = 0;

			if (oldPreferenceValues != null) {
				oldSize = oldPreferenceValues._values.size();
			}

			for (int i = 0; i < newValues.size(); i++) {
				String value = newValues.get(i);

				if (oldSize > i) {
					if (!Objects.equals(
							value, oldPreferenceValues._values.get(i)) ||
						(newPreferenceValues._readOnly !=
							oldPreferenceValues._readOnly)) {

						String largeValue = null;
						String smallValue = null;

						if (value.length() > smallValueMaxLength) {
							largeValue = value;
						}
						else {
							smallValue = value;
						}

						updatePreparedStatement.setString(1, largeValue);
						updatePreparedStatement.setBoolean(
							2, newPreferenceValues._readOnly);
						updatePreparedStatement.setString(3, smallValue);

						updatePreparedStatement.setLong(
							4,
							oldPreferenceValues._portletPreferenceValueIds.get(
								i));

						updatePreparedStatement.addBatch();
					}
				}
				else {
					String largeValue = null;
					String smallValue = null;

					if (value.length() > smallValueMaxLength) {
						largeValue = value;
					}
					else {
						smallValue = value;
					}

					insertPreparedStatement.setLong(1, ctCollectionId);
					insertPreparedStatement.setLong(2, ++batchCounter);
					insertPreparedStatement.setLong(3, companyId);
					insertPreparedStatement.setLong(4, portletPreferencesId);
					insertPreparedStatement.setInt(5, i);
					insertPreparedStatement.setString(6, largeValue);
					insertPreparedStatement.setString(7, entry.getKey());
					insertPreparedStatement.setBoolean(
						8, newPreferenceValues._readOnly);
					insertPreparedStatement.setString(9, smallValue);

					insertPreparedStatement.addBatch();
				}
			}

			for (int i = newValues.size(); i < oldSize; i++) {
				deletePreparedStatement.setLong(
					1, oldPreferenceValues._portletPreferenceValueIds.get(i));

				deletePreparedStatement.addBatch();
			}
		}
	}

	private static class PreferenceValues {

		private final List<Long> _portletPreferenceValueIds = new ArrayList<>();
		private boolean _readOnly;
		private final List<String> _values = new ArrayList<>();

	}

}