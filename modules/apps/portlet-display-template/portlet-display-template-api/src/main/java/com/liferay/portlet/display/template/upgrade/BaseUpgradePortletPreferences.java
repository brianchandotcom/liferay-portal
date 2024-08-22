/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portlet.display.template.upgrade;

import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.PortletPreferencesFactoryUtil;
import com.liferay.portal.kernel.upgrade.BasePortletPreferencesUpgradeProcess;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.Validator;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.portlet.PortletPreferences;

/**
 * @author Lourdes Fernández Besada
 */
public abstract class BaseUpgradePortletPreferences
	extends BasePortletPreferencesUpgradeProcess {

	protected String getGroupExternalReferenceCode(long groupId)
		throws Exception {

		return _groupIdMap.computeIfAbsent(
			groupId, curGroupId -> _getGroupExternalReferenceCode(curGroupId));
	}

	protected long getGroupId(long companyId, String groupKey)
		throws Exception {

		Map<String, Long> companyMap = _groupKeyMap.computeIfAbsent(
			companyId, curCompanyId -> new ConcurrentHashMap<>());

		return companyMap.computeIfAbsent(
			groupKey,
			curGroupKey -> {
				Object[] group = _getGroup(companyId, curGroupKey);

				if (group == null) {
					if (_log.isWarnEnabled()) {
						_log.warn(
							StringBundler.concat(
								"Unable to get group for company ID ",
								companyId, " and group key ", curGroupKey));
					}

					return 0L;
				}

				String externalReferenceCode = (String)group[0];
				long groupId = (long)group[1];

				_groupIdMap.computeIfAbsent(
					groupId, curGroupId -> externalReferenceCode);

				return groupId;
			});
	}

	@Override
	protected abstract String[] getPortletIds();

	protected String getScopeExternalReferenceCode(long plid, long scopeGroupId)
		throws Exception {

		long layoutGroupId = _getLayoutGroupId(plid);

		if ((layoutGroupId == 0L) || (layoutGroupId == scopeGroupId)) {
			return StringPool.BLANK;
		}

		return getGroupExternalReferenceCode(scopeGroupId);
	}

	protected String getScopeExternalReferenceCode(
			long companyId, long plid, String scopeGroupKey)
		throws Exception {

		long layoutGroupId = _getLayoutGroupId(plid);
		long scopeGroupId = getGroupId(companyId, scopeGroupKey);

		if ((layoutGroupId == 0L) || (layoutGroupId == scopeGroupId)) {
			return StringPool.BLANK;
		}

		return getGroupExternalReferenceCode(scopeGroupId);
	}

	protected abstract void upgradePreferences(
			long companyId, long ownerId, int ownerType, long plid,
			String portletId, PortletPreferences portletPreferences)
		throws Exception;

	@Override
	protected String upgradePreferences(
			long companyId, long ownerId, int ownerType, long plid,
			String portletId, String xml)
		throws Exception {

		PortletPreferences portletPreferences =
			PortletPreferencesFactoryUtil.fromXML(
				companyId, ownerId, ownerType, plid, portletId, xml);

		String displayStyleGroupKey = portletPreferences.getValue(
			"displayStyleGroupKey", null);

		String groupExternalReferenceCode = null;

		if (Validator.isNotNull(displayStyleGroupKey)) {
			groupExternalReferenceCode = getScopeExternalReferenceCode(
				companyId, plid, displayStyleGroupKey);
		}
		else {
			long displayStyleGroupId = GetterUtil.getLong(
				portletPreferences.getValue("displayStyleGroupId", null));

			if (displayStyleGroupId > 0) {
				groupExternalReferenceCode = getScopeExternalReferenceCode(
					plid, displayStyleGroupId);
			}
		}

		if (Validator.isNotNull(groupExternalReferenceCode)) {
			portletPreferences.setValue(
				"displayStyleGroupExternalReferenceCode",
				groupExternalReferenceCode);
		}

		upgradePreferences(
			companyId, ownerId, ownerType, plid, portletId, portletPreferences);

		return PortletPreferencesFactoryUtil.toXML(portletPreferences);
	}

	private Object[] _getGroup(long companyId, String groupKey) {
		try (PreparedStatement preparedStatement = connection.prepareStatement(
				"select externalReferenceCode, groupId from Group_ where " +
					"companyId = ? and groupKey = ?")) {

			preparedStatement.setLong(1, companyId);
			preparedStatement.setString(2, groupKey);

			try (ResultSet resultSet = preparedStatement.executeQuery()) {
				if (resultSet.next()) {
					return new Object[] {
						resultSet.getString("externalReferenceCode"),
						resultSet.getLong("groupId")
					};
				}
			}
		}
		catch (Exception exception) {
			if (_log.isWarnEnabled()) {
				_log.warn(
					StringBundler.concat(
						"Unable to get group for company ID ", companyId,
						" and group key ", groupKey),
					exception);
			}
		}

		return null;
	}

	private String _getGroupExternalReferenceCode(long groupId) {
		try (PreparedStatement preparedStatement = connection.prepareStatement(
				"select externalReferenceCode from Group_ where groupId = ?")) {

			preparedStatement.setLong(1, groupId);

			try (ResultSet resultSet = preparedStatement.executeQuery()) {
				if (resultSet.next()) {
					return resultSet.getString("externalReferenceCode");
				}
			}
		}
		catch (Exception exception) {
			if (_log.isWarnEnabled()) {
				_log.warn(
					"Unable to get external reference code for group ID " +
						groupId,
					exception);
			}
		}

		return StringPool.BLANK;
	}

	private long _getLayoutGroupId(long plid) {
		return _plidMap.computeIfAbsent(
			plid,
			curPlid -> {
				try {
					Object[] layout = getLayout(curPlid);

					return (long)layout[0];
				}
				catch (Exception exception) {
					if (_log.isWarnEnabled()) {
						_log.warn(
							"Unable to get layout for PLID " + curPlid,
							exception);
					}
				}

				return 0L;
			});
	}

	private static final Log _log = LogFactoryUtil.getLog(
		BaseUpgradePortletPreferences.class);

	private final Map<Long, String> _groupIdMap = new ConcurrentHashMap<>();
	private final Map<Long, Map<String, Long>> _groupKeyMap =
		new ConcurrentHashMap<>();
	private final Map<Long, Long> _plidMap = new ConcurrentHashMap<>();

}