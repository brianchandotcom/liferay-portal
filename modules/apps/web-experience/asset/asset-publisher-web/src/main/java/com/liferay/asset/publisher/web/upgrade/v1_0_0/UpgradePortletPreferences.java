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
import com.liferay.dynamic.data.mapping.model.DDMForm;
import com.liferay.dynamic.data.mapping.model.DDMFormField;
import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.model.DDMStructureLink;
import com.liferay.dynamic.data.mapping.service.DDMStructureLinkLocalService;
import com.liferay.dynamic.data.mapping.service.DDMStructureLocalService;
import com.liferay.portal.kernel.portlet.PortletPreferencesFactoryUtil;
import com.liferay.portal.kernel.upgrade.BaseUpgradePortletPreferences;
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
import com.liferay.portal.kernel.xml.SAXReader;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.text.DateFormat;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.portlet.PortletPreferences;

/**
 * @author Sam Ziemer
 */
public class UpgradePortletPreferences extends BaseUpgradePortletPreferences {

	public UpgradePortletPreferences(
		DDMStructureLocalService ddmStructureLocalService,
		DDMStructureLinkLocalService ddmStructureLinkLocalService,
		SAXReader saxReader) {

		_ddmStructureLocalService = ddmStructureLocalService;
		_ddmStructureLinkLocalService = ddmStructureLinkLocalService;
		_saxReader = saxReader;

		_newDateFormat = DateFormatFactoryUtil.getSimpleDateFormat(
			"yyyy-MM-dd");
		_oldDateFormat = DateFormatFactoryUtil.getSimpleDateFormat(
			"yyyyMMddHHmmss");
	}

	protected DDMForm getDDMForm(long structureId) throws Exception {
		DDMForm ddmForm = _ddmSructureDDMForms.get(structureId);

		if (ddmForm != null) {
			return ddmForm;
		}

		DDMStructure ddmStructure = _ddmStructureLocalService.getStructure(
			structureId);

		ddmForm = ddmStructure.getDDMForm();

		_ddmSructureDDMForms.put(structureId, ddmForm);

		return ddmForm;
	}

	protected DDMFormField getDDMFormField(DDMForm ddmForm, String fieldName) {
		Map<String, DDMFormField> ddmFormFieldsMap =
			ddmForm.getDDMFormFieldsMap(false);
		return ddmFormFieldsMap.get(fieldName);
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

	protected boolean isDateField(DDMForm ddmForm, String fieldName) {
		DDMFormField ddmFormField = getDDMFormField(ddmForm, fieldName);

		if (ddmFormField == null) {
			return false;
		}

		if (Validator.equals("ddm-date", ddmFormField.getType())) {
			return true;
		}

		return false;
	}

	protected boolean isFilterByFieldEnable(
		PortletPreferences portletPreferences, String key) {

		return GetterUtil.getBoolean(
			portletPreferences.getValue(key, Boolean.FALSE.toString()));
	}

	protected boolean isOldDDMPreferenceValueFormat(String value) {
		if (value.startsWith(_DDM_FIELD_OLD_PREFIX)) {
			return true;
		}

		return false;
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

			List<DDMStructureLink> ddmStructureLinks =
				_ddmStructureLinkLocalService.getStructureLinks(
					fileEntryTypeClassNameId, fileEntryTypeId);

			String selectedFieldName = GetterUtil.getString(
				portletPreferences.getValue(_DDM_STRUCTURE_FIELD_NAME, null));

			for (DDMStructureLink ddmStructureLink : ddmStructureLinks) {
				DDMForm ddmForm = getDDMForm(ddmStructureLink.getStructureId());

				if (isDateField(ddmForm, selectedFieldName)) {
					transformDateFieldValue(portletPreferences);
					break;
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

			DDMForm ddmForm = getDDMForm(structureId);

			if (isDateField(ddmForm, selectedFieldName)) {
				transformDateFieldValue(portletPreferences);
			}
		}
	}

	protected void upgradeOrderByColumn(
			PortletPreferences portletPreferences, String column)
		throws Exception {

		String value = GetterUtil.getString(
			portletPreferences.getValue(column, null));

		if (Validator.isNull(value)) {
			return;
		}

		if (value.startsWith(_DDM_FIELD_OLD_PREFIX) ||
			value.startsWith(_DDM_FIELD_PREFIX)) {

			String[] values = new String[0];

			boolean oldDDMPreferenceValueFormat = isOldDDMPreferenceValueFormat(
				value);

			if (oldDDMPreferenceValueFormat) {
				values = StringUtil.split(value, _DDM_FIELD_OLD_SEPARATOR);
			}
			else {
				values = StringUtil.split(value, _DDM_FIELD_SEPARATOR);
			}

			if (values.length == 3) {
				long structureId = GetterUtil.getLong(values[1]);

				DDMForm ddmForm = getDDMForm(structureId);

				DDMFormField ddmFormField = getDDMFormField(ddmForm, values[2]);

				if ((ddmFormField != null) &&
					Validator.isNotNull(ddmFormField.getIndexType())) {

					StringBundler sb = new StringBundler(7);
					sb.append(values[0]);
					sb.append(_DDM_FIELD_SEPARATOR);
					sb.append(ddmFormField.getIndexType());
					sb.append(_DDM_FIELD_SEPARATOR);
					sb.append(values[1]);
					sb.append(_DDM_FIELD_SEPARATOR);
					sb.append(values[2]);

					value = sb.toString();
				}
			}
			else if ((values.length == 4) && oldDDMPreferenceValueFormat) {
				value = StringUtil.replace(
					value, _DDM_FIELD_OLD_SEPARATOR, _DDM_FIELD_SEPARATOR);
			}

			portletPreferences.setValue(column, value);
		}
	}

	protected void upgradeOrderByColumns(PortletPreferences portletPreferences)
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

			Document document = _saxReader.read(assetEntry);

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

	private static final String _DDM_FIELD_NAMESPACE = "ddm";

	private static final String _DDM_FIELD_OLD_PREFIX =
		_DDM_FIELD_NAMESPACE + StringPool.FORWARD_SLASH;

	private static final String _DDM_FIELD_OLD_SEPARATOR =
		StringPool.FORWARD_SLASH;

	private static final String _DDM_FIELD_PREFIX =
		_DDM_FIELD_NAMESPACE + StringPool.DOUBLE_UNDERLINE;

	private static final String _DDM_FIELD_SEPARATOR =
		StringPool.DOUBLE_UNDERLINE;

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

	private static final String _ORDER_BY_COLUMN_1 = "orderByColumn1";

	private static final String _ORDER_BY_COLUMN_2 = "orderByColumn2";

	private static final Map<Long, DDMForm> _ddmSructureDDMForms =
		new HashMap<>();

	private final DDMStructureLinkLocalService _ddmStructureLinkLocalService;
	private final DDMStructureLocalService _ddmStructureLocalService;
	private final DateFormat _newDateFormat;
	private final DateFormat _oldDateFormat;
	private final SAXReader _saxReader;

}