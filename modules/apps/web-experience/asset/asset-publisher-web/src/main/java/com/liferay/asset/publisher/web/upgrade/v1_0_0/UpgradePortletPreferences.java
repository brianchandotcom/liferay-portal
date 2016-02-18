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

package com.liferay.asset.publisher.web.upgrade.v1_0_0;

import com.liferay.asset.publisher.web.constants.AssetPublisherPortletKeys;
import com.liferay.document.library.kernel.model.DLFileEntryType;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.portlet.PortletPreferencesFactoryUtil;
import com.liferay.portal.kernel.upgrade.BaseUpgradePortletPreferences;
import com.liferay.portal.kernel.upgrade.UpgradeException;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.DateFormatFactoryUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.xml.Document;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.kernel.xml.SAXReaderUtil;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.text.DateFormat;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.portlet.PortletPreferences;

/**
 * @author Sam Ziemer
 */
public class UpgradePortletPreferences extends BaseUpgradePortletPreferences {

	public UpgradePortletPreferences(
		DateFormatFactoryUtil dateFormatFactoryUtil) {

		_newDateFormat = dateFormatFactoryUtil.getSimpleDateFormat(
			"yyyy-MM-dd");
		_oldDateFormat = dateFormatFactoryUtil.getSimpleDateFormat(
			"yyyyMMddHHmmss");
	}

	protected JSONObject getDDMStructureJSONObject(long structureId)
		throws Exception {

		JSONObject ddmStructureJSONObject = _ddmSructureJSONObjects.get(
			structureId);

		if (ddmStructureJSONObject != null) {
			return ddmStructureJSONObject;
		}

		try (PreparedStatement ps = connection.prepareStatement(
				"select definition from DDMStructure where structureId = ?")) {

			ps.setLong(1, structureId);

			try (ResultSet rs = ps.executeQuery()) {
				if (rs.next()) {
					String definition = rs.getString("definition");

					ddmStructureJSONObject = JSONFactoryUtil.createJSONObject(
						definition);

					_ddmSructureJSONObjects.put(
						structureId, ddmStructureJSONObject);

					return ddmStructureJSONObject;
				}

				throw new UpgradeException(
					"Unable to find dynamic data mapping structure " +
						structureId);
			}
		}
	}

	protected JSONObject getFieldJSONObject(
		JSONArray fieldsJSONArray, String selectedFieldName) {

		JSONObject fieldJSONObject = null;

		for (int i = 0; i < fieldsJSONArray.length(); i++) {
			JSONObject curFieldJSONObject = fieldsJSONArray.getJSONObject(i);

			String fieldName = curFieldJSONObject.getString("name");

			if (fieldName.equals(selectedFieldName)) {
				fieldJSONObject = curFieldJSONObject;

				break;
			}

			if (curFieldJSONObject.has("nestedFields")) {
				JSONArray nestedFieldsJSONArray =
					curFieldJSONObject.getJSONArray("nestedFields");

				fieldJSONObject = getFieldJSONObject(
					nestedFieldsJSONArray, selectedFieldName);

				if (fieldJSONObject != null) {
					break;
				}
			}
		}

		return fieldJSONObject;
	}

	protected String getJournalArticleResourceUuid(String journalArticleUuid)
		throws Exception {

		StringBundler sb = new StringBundler(5);

		sb.append("select JournalArticleResource.uuid_ from ");
		sb.append("JournalArticleResource inner join JournalArticle on ");
		sb.append("JournalArticle.resourcePrimKey = ");
		sb.append("JournalArticleResource.resourcePrimKey where ");
		sb.append("JournalArticle.uuid_ = ?");

		try (PreparedStatement ps = connection.prepareStatement(
				sb.toString())) {

			ps.setString(1, journalArticleUuid);

			try (ResultSet rs = ps.executeQuery()) {
				if (rs.next()) {
					return rs.getString("uuid_");
				}

				return null;
			}
		}
	}

	@Override
	protected String[] getPortletIds() {
		return new String[] {
			AssetPublisherPortletKeys.ASSET_PUBLISHER + "_INSTANCE_%"
		};
	}

	protected boolean isDateField(
		JSONObject ddmStructureJSONObject, String selectedFieldName) {

		JSONArray fieldsJSONArray = ddmStructureJSONObject.getJSONArray(
			"fields");

		JSONObject fieldJSONObject = getFieldJSONObject(
			fieldsJSONArray, selectedFieldName);

		if ((fieldJSONObject != null) &&
			Validator.equals(fieldJSONObject.getString("type"), "ddm-date")) {

			return true;
		}

		return false;
	}

	protected boolean isFilterByFieldEnable(
		PortletPreferences portletPreferences, String key) {

		return GetterUtil.getBoolean(
			portletPreferences.getValue(key, Boolean.FALSE.toString()));
	}

	protected void transformDateFieldValue(
			PortletPreferences portletPreferences)
		throws Exception {

		String value = GetterUtil.getString(
			portletPreferences.getValue(_DDM_STRUCTURE_FIELD_VALUE, null));

		if (Validator.isNotNull(value)) {
			Date date = _oldDateFormat.parse(value);

			portletPreferences.setValue(
				_DDM_STRUCTURE_FIELD_VALUE, _newDateFormat.format(date));
		}
	}

	protected void upgradeDLDateFieldsValues(
			PortletPreferences portletPreferences)
		throws Exception {

		long fileEntryTypeId = GetterUtil.getLong(
			portletPreferences.getValue(_DL_CLASS_TYPE, "0"));

		if (fileEntryTypeId > 0) {
			long fileEntryTypeClassNameId = PortalUtil.getClassNameId(
				DLFileEntryType.class);

			try (PreparedStatement ps = connection.prepareStatement(
					"select structureId from DDMStructureLink where " +
						"classNameId = ? and classPK = ?")) {

				ps.setLong(1, fileEntryTypeClassNameId);
				ps.setLong(2, fileEntryTypeId);

				try (ResultSet rs = ps.executeQuery()) {
					String selectedFieldName = GetterUtil.getString(
						portletPreferences.getValue(
							_DDM_STRUCTURE_FIELD_NAME, null));

					while (rs.next()) {
						long structureId = rs.getLong("structureId");

						JSONObject ddmStructureJSONObject =
							getDDMStructureJSONObject(structureId);

						if (isDateField(
								ddmStructureJSONObject, selectedFieldName)) {

							transformDateFieldValue(portletPreferences);

							break;
						}
					}
				}
			}
		}
	}

	protected void upgradeJournalDateFieldValue(
			PortletPreferences portletPreferences)
		throws Exception {

		long structureId = GetterUtil.getLong(
			portletPreferences.getValue(_JOURNAL_CLASS_TYPE, "0"));

		if (structureId > 0) {
			String selectedFieldName = GetterUtil.getString(
				portletPreferences.getValue(_DDM_STRUCTURE_FIELD_NAME, null));

			JSONObject ddmStructureJSONObject = getDDMStructureJSONObject(
				structureId);

			if (isDateField(ddmStructureJSONObject, selectedFieldName)) {
				transformDateFieldValue(portletPreferences);
			}
		}
	}

	protected void upgradeOrderByColumn(
			PortletPreferences portletPreferences, String column)
		throws Exception {

		String value = GetterUtil.getString(
				portletPreferences.getValue(column, null));

		if (Validator.isNotNull(value) &&
				(value.startsWith(DDM_FIELD_OLD_PREFIX) ||
					value.startsWith(DDM_FIELD_PREFIX))) {

			String [] values = new String[0];

			boolean isOldFormat = false;

			if (value.startsWith(DDM_FIELD_OLD_PREFIX)) {
				isOldFormat = true;
				values = StringUtil.split(value, DDM_FIELD_OLD_SEPARATOR);
			} else {
				values = StringUtil.split(value, DDM_FIELD_SEPARATOR);
			}

			if (values.length == 4 && isOldFormat) {
				value = StringUtil.replace(
					value, DDM_FIELD_OLD_SEPARATOR, DDM_FIELD_SEPARATOR);
			} else if (values.length == 3) {
				long structureId = GetterUtil.getLong(values[1]);

				JSONObject ddmStructureJSONObject =
					getDDMStructureJSONObject(structureId);

				JSONArray fieldsJSONArray =
					ddmStructureJSONObject.getJSONArray("fields");

				JSONObject fieldJSONObject = getFieldJSONObject(
					fieldsJSONArray, values[2]);

				if (fieldJSONObject != null &&
						Validator.isNotNull(
							fieldJSONObject.getString("indexType"))) {

					StringBundler sb = new StringBundler(7);
					sb.append(values[0]);
					sb.append(DDM_FIELD_SEPARATOR);
					sb.append(fieldJSONObject.getString("indexType"));
					sb.append(DDM_FIELD_SEPARATOR);
					sb.append(values[1]);
					sb.append(DDM_FIELD_SEPARATOR);
					sb.append(values[2]);

					value = sb.toString();
				}
			}

			portletPreferences.setValue(column, value);
		}
	}

	protected void upgradeOrderByColumns(
			PortletPreferences portletPreferences)
		throws Exception {

		upgradeOrderByColumn(portletPreferences, _ORDER_BY_COLUMN_1);

		upgradeOrderByColumn(portletPreferences, _ORDER_BY_COLUMN_2);

	}

	@Override
	protected String upgradePreferences(
			long companyId, long ownerId, int ownerType, long plid,
			String portletId, String xml)
		throws Exception {

		PortletPreferences portletPreferences =
			PortletPreferencesFactoryUtil.fromXML(
				companyId, ownerId, ownerType, plid, portletId, xml);

		String[] assetEntryXmls = portletPreferences.getValues(
			"asset-entry-xml", new String[0]);

		if (ArrayUtil.isNotEmpty(assetEntryXmls)) {
			upgradeUuids(assetEntryXmls);

			portletPreferences.setValues("assetEntryXml", assetEntryXmls);
		}

		boolean subtypeFieldsFilterEnabled = GetterUtil.getBoolean(
			portletPreferences.getValue(
				"subtypeFieldsFilterEnabled", Boolean.FALSE.toString()));

		if (subtypeFieldsFilterEnabled) {
			boolean dlFilterByFieldEnable = isFilterByFieldEnable(
				portletPreferences, _DL_FILTER_BY_FIELD_ENABLED_KEY);
			boolean journalFilterByFieldEnable = isFilterByFieldEnable(
				portletPreferences, _JOURNAL_FILTER_BY_FIELD_ENABLED_KEY);

			if (dlFilterByFieldEnable) {
				upgradeDLDateFieldsValues(portletPreferences);
			}
			else if (journalFilterByFieldEnable) {
				upgradeJournalDateFieldValue(portletPreferences);
			}

			upgradeOrderByColumns(portletPreferences);
		}

		return PortletPreferencesFactoryUtil.toXML(portletPreferences);
	}

	protected void upgradeUuids(String[] assetEntryXmls) throws Exception {
		for (int i = 0; i < assetEntryXmls.length; i++) {
			String assetEntry = assetEntryXmls[i];

			Document document = SAXReaderUtil.read(assetEntry);

			Element rootElement = document.getRootElement();

			Element assetTypeElementUuid = rootElement.element(
				"asset-entry-uuid");

			String journalArticleResourceUuid = getJournalArticleResourceUuid(
				assetTypeElementUuid.getStringValue());

			if (journalArticleResourceUuid == null) {
				continue;
			}

			rootElement.remove(assetTypeElementUuid);

			assetTypeElementUuid.setText(journalArticleResourceUuid);

			rootElement.add(assetTypeElementUuid);

			document.setRootElement(rootElement);

			assetEntryXmls[i] = document.formattedString(StringPool.BLANK);
		}
	}

	public static final String DDM_FIELD_NAMESPACE = "ddm";

	public static final String DDM_FIELD_OLD_SEPARATOR =
		StringPool.FORWARD_SLASH;

	public static final String DDM_FIELD_OLD_PREFIX =
		DDM_FIELD_NAMESPACE + DDM_FIELD_OLD_SEPARATOR;

	public static final String DDM_FIELD_SEPARATOR =
		StringPool.DOUBLE_UNDERLINE;

	public static final String DDM_FIELD_PREFIX =
		DDM_FIELD_NAMESPACE + DDM_FIELD_SEPARATOR;

	private static final String _DDM_STRUCTURE_FIELD_NAME =
		"ddmStructureFieldName";

	private static final String _DDM_STRUCTURE_FIELD_VALUE =
		"ddmStructureFieldValue";

	private static final String _DL_CLASS_TYPE =
		"anyClassTypeDLFileEntryAssetRendererFactory";

	private static final String _DL_FILTER_BY_FIELD_ENABLED_KEY =
		"subtypeFieldsFilterEnabledDLFileEntryAssetRendererFactory";

	private static final String _JOURNAL_CLASS_TYPE =
		"anyClassTypeJournalArticleAssetRendererFactory";

	private static final String _JOURNAL_FILTER_BY_FIELD_ENABLED_KEY =
		"subtypeFieldsFilterEnabledJournalArticleAssetRendererFactory";

	private static final String _ORDER_BY_COLUMN_1 =
		"orderByColumn1";

	private static final String _ORDER_BY_COLUMN_2 =
		"orderByColumn2";

	private static final Map<Long, JSONObject> _ddmSructureJSONObjects =
		new HashMap<>();

	private final DateFormat _newDateFormat;
	private final DateFormat _oldDateFormat;

}