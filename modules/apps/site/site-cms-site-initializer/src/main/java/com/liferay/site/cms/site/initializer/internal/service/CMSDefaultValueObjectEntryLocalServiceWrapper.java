/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.site.cms.site.initializer.internal.service;

import com.liferay.batch.engine.thread.local.BatchEngineThreadLocal;
import com.liferay.depot.constants.DepotConstants;
import com.liferay.depot.model.DepotEntry;
import com.liferay.depot.service.DepotEntryLocalService;
import com.liferay.exportimport.kernel.lar.ExportImportThreadLocal;
import com.liferay.list.type.model.ListTypeDefinition;
import com.liferay.list.type.model.ListTypeEntry;
import com.liferay.list.type.service.ListTypeDefinitionLocalService;
import com.liferay.list.type.service.ListTypeEntryLocalService;
import com.liferay.object.constants.ObjectDefinitionConstants;
import com.liferay.object.constants.ObjectFieldConstants;
import com.liferay.object.constants.ObjectFieldSettingConstants;
import com.liferay.object.field.setting.util.ObjectFieldSettingUtil;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.model.ObjectEntry;
import com.liferay.object.model.ObjectEntryFolder;
import com.liferay.object.model.ObjectField;
import com.liferay.object.petra.sql.dsl.DynamicObjectDefinitionTableUtil;
import com.liferay.object.rest.filter.factory.FilterFactory;
import com.liferay.object.service.ObjectEntryFolderLocalService;
import com.liferay.object.service.ObjectEntryLocalServiceWrapper;
import com.liferay.object.service.ObjectFieldLocalService;
import com.liferay.petra.sql.dsl.expression.Predicate;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.feature.flag.FeatureFlagManagerUtil;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceWrapper;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.site.cms.site.initializer.util.CMSDefaultValueUtil;

import java.io.Serializable;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Stefano Motta
 */
@Component(service = ServiceWrapper.class)
public class CMSDefaultValueObjectEntryLocalServiceWrapper
	extends ObjectEntryLocalServiceWrapper {

	@Override
	public ObjectEntry addObjectEntry(
			long groupId, long userId, long objectDefinitionId,
			long objectEntryFolderId, String defaultLanguageId,
			Map<String, Serializable> values, ServiceContext serviceContext)
		throws PortalException {

		if (FeatureFlagManagerUtil.isEnabled(
				CompanyThreadLocal.getCompanyId(), "LPD-112761")) {

			ObjectDefinition objectDefinition =
				CMSDefaultValueUtil.fetchObjectDefinition(
					CompanyThreadLocal.getCompanyId());

			if ((objectDefinition != null) &&
				(objectDefinition.getObjectDefinitionId() ==
					objectDefinitionId)) {

				_putTreePath(
					MapUtil.getString(values, "classExternalReferenceCode"),
					MapUtil.getString(values, "className"),
					objectDefinition.getCompanyId(), groupId, values);
			}
			else {
				_putDefaultValues(
					defaultLanguageId, groupId, objectDefinitionId,
					objectEntryFolderId, values);
			}
		}

		return super.addObjectEntry(
			groupId, userId, objectDefinitionId, objectEntryFolderId,
			defaultLanguageId, values, serviceContext);
	}

	@Override
	public ObjectEntry partialUpdateObjectEntry(
			long userId, long objectEntryId, long objectEntryFolderId,
			Map<String, Serializable> values, ServiceContext serviceContext)
		throws PortalException {

		if (FeatureFlagManagerUtil.isEnabled(
				CompanyThreadLocal.getCompanyId(), "LPD-112761")) {

			_putTreePath(objectEntryId, values);
		}

		return super.partialUpdateObjectEntry(
			userId, objectEntryId, objectEntryFolderId, values, serviceContext);
	}

	@Override
	public ObjectEntry updateObjectEntry(
			long userId, long objectEntryId, long objectEntryFolderId,
			Map<String, Serializable> values, ServiceContext serviceContext)
		throws PortalException {

		if (FeatureFlagManagerUtil.isEnabled(
				CompanyThreadLocal.getCompanyId(), "LPD-112761")) {

			_putTreePath(objectEntryId, values);
		}

		return super.updateObjectEntry(
			userId, objectEntryId, objectEntryFolderId, values, serviceContext);
	}

	private String _getListTypeDefinitionExternalReferenceCode(
		ObjectField objectField) {

		if (objectField.getListTypeDefinitionId() == 0) {
			return null;
		}

		ListTypeDefinition listTypeDefinition =
			_listTypeDefinitionLocalService.fetchListTypeDefinition(
				objectField.getListTypeDefinitionId());

		if (listTypeDefinition == null) {
			return null;
		}

		return listTypeDefinition.getExternalReferenceCode();
	}

	private Serializable _getValue(ObjectField objectField, Object value) {
		if (Objects.equals(
				objectField.getBusinessType(),
				ObjectFieldConstants.BUSINESS_TYPE_PICKLIST)) {

			String key = String.valueOf(value);

			if (value instanceof JSONObject) {
				JSONObject jsonObject = (JSONObject)value;

				key = jsonObject.getString("key");
			}

			if (Validator.isNull(key)) {
				return null;
			}

			ListTypeEntry listTypeEntry =
				_listTypeEntryLocalService.fetchListTypeEntry(
					objectField.getListTypeDefinitionId(), key);

			if (listTypeEntry == null) {
				return null;
			}

			return key;
		}

		if (value instanceof JSONArray || value instanceof JSONObject ||
			!(value instanceof Serializable) || !_isValid(objectField, value)) {

			return null;
		}

		return (Serializable)value;
	}

	private boolean _isValid(ObjectField objectField, Object value) {
		String valueString = String.valueOf(value);

		if (Objects.equals(
				objectField.getDBType(),
				ObjectFieldConstants.DB_TYPE_INTEGER)) {

			if ((GetterUtil.getInteger(valueString) != 0) ||
				Objects.equals(valueString, "0")) {

				return true;
			}

			return false;
		}

		if (Objects.equals(
				objectField.getDBType(), ObjectFieldConstants.DB_TYPE_LONG)) {

			if ((GetterUtil.getLong(valueString) != 0) ||
				Objects.equals(valueString, "0")) {

				return true;
			}

			return false;
		}

		if (Objects.equals(
				objectField.getBusinessType(),
				ObjectFieldConstants.BUSINESS_TYPE_LONG_TEXT) ||
			Objects.equals(
				objectField.getBusinessType(),
				ObjectFieldConstants.BUSINESS_TYPE_TEXT)) {

			int maxLength = GetterUtil.getInteger(
				ObjectFieldSettingUtil.getValue(
					ObjectFieldSettingConstants.NAME_MAX_LENGTH, objectField),
				DynamicObjectDefinitionTableUtil.getMaxLength(
					objectField.getBusinessType()));

			if (valueString.length() <= maxLength) {
				return true;
			}

			return false;
		}

		return true;
	}

	private void _putDefaultValues(
			String defaultLanguageId, long groupId, long objectDefinitionId,
			long objectEntryFolderId, Map<String, Serializable> values)
		throws PortalException {

		if ((groupId == 0) || BatchEngineThreadLocal.isBatchImportInProcess() ||
			ExportImportThreadLocal.isImportInProcess()) {

			return;
		}

		DepotEntry depotEntry = _depotEntryLocalService.fetchGroupDepotEntry(
			groupId);

		if ((depotEntry == null) ||
			(depotEntry.getType() != DepotConstants.TYPE_SPACE)) {

			return;
		}

		Map<String, JSONObject> defaultValues =
			CMSDefaultValueUtil.getInheritedDefaultValues(
				groupId, _filterFactory, objectEntryFolderId);

		if (defaultValues.isEmpty()) {
			return;
		}

		for (ObjectField objectField :
				_objectFieldLocalService.getObjectFields(objectDefinitionId)) {

			if (objectField.isMetadata() ||
				!ArrayUtil.contains(
					_BUSINESS_TYPES, objectField.getBusinessType()) ||
				(values.get(objectField.getName()) != null) ||
				(objectField.isLocalized() &&
				 (values.get(objectField.getI18nObjectFieldName()) != null))) {

				continue;
			}

			JSONObject jsonObject = defaultValues.get(
				CMSDefaultValueUtil.getObjectFieldKey(
					_getListTypeDefinitionExternalReferenceCode(objectField),
					objectField.getName(), objectField.getBusinessType()));

			if (jsonObject == null) {
				continue;
			}

			_putValue(
				defaultLanguageId, groupId, objectField,
				jsonObject.get("value"), values);
		}
	}

	private void _putTreePath(
			long objectEntryId, Map<String, Serializable> values)
		throws PortalException {

		ObjectEntry objectEntry = getObjectEntry(objectEntryId);

		ObjectDefinition objectDefinition =
			CMSDefaultValueUtil.fetchObjectDefinition(
				objectEntry.getCompanyId());

		if ((objectDefinition == null) ||
			(objectDefinition.getObjectDefinitionId() !=
				objectEntry.getObjectDefinitionId())) {

			return;
		}

		Map<String, Serializable> objectEntryValues = objectEntry.getValues();

		_putTreePath(
			MapUtil.getString(
				values, "classExternalReferenceCode",
				MapUtil.getString(
					objectEntryValues, "classExternalReferenceCode")),
			MapUtil.getString(
				values, "className",
				MapUtil.getString(objectEntryValues, "className")),
			objectEntry.getCompanyId(), objectEntry.getGroupId(), values);
	}

	private void _putTreePath(
		String classExternalReferenceCode, String className, long companyId,
		long groupId, Map<String, Serializable> values) {

		values.put("treePath", StringPool.BLANK);

		if (!Objects.equals(className, ObjectEntryFolder.class.getName())) {
			return;
		}

		ObjectEntryFolder objectEntryFolder =
			_objectEntryFolderLocalService.
				fetchObjectEntryFolderByExternalReferenceCode(
					classExternalReferenceCode, groupId, companyId);

		if (objectEntryFolder != null) {
			values.put("treePath", objectEntryFolder.getTreePath());
		}
	}

	private void _putValue(
		String defaultLanguageId, long groupId, ObjectField objectField,
		Object value, Map<String, Serializable> values) {

		if (!objectField.isLocalized() || !(value instanceof JSONObject)) {
			Serializable serializable = _getValue(objectField, value);

			if (serializable != null) {
				values.put(objectField.getName(), serializable);
			}

			return;
		}

		JSONObject jsonObject = (JSONObject)value;

		HashMap<String, Serializable> localizedValues = new HashMap<>();

		for (String languageId : jsonObject.keySet()) {
			if (!_language.isAvailableLocale(groupId, languageId)) {
				continue;
			}

			Serializable serializable = _getValue(
				objectField, jsonObject.get(languageId));

			if (serializable == null) {
				continue;
			}

			localizedValues.put(languageId, serializable);
		}

		if (localizedValues.isEmpty()) {
			return;
		}

		if (Validator.isNull(defaultLanguageId)) {
			defaultLanguageId = LocaleUtil.toLanguageId(
				LocaleUtil.getSiteDefault());
		}

		Serializable defaultValue = localizedValues.get(defaultLanguageId);

		if (defaultValue != null) {
			values.put(objectField.getName(), defaultValue);
		}

		values.put(objectField.getI18nObjectFieldName(), localizedValues);
	}

	private static final String[] _BUSINESS_TYPES = {
		ObjectFieldConstants.BUSINESS_TYPE_BOOLEAN,
		ObjectFieldConstants.BUSINESS_TYPE_DATE,
		ObjectFieldConstants.BUSINESS_TYPE_DATE_TIME,
		ObjectFieldConstants.BUSINESS_TYPE_DECIMAL,
		ObjectFieldConstants.BUSINESS_TYPE_INTEGER,
		ObjectFieldConstants.BUSINESS_TYPE_LONG_INTEGER,
		ObjectFieldConstants.BUSINESS_TYPE_LONG_TEXT,
		ObjectFieldConstants.BUSINESS_TYPE_PICKLIST,
		ObjectFieldConstants.BUSINESS_TYPE_PRECISION_DECIMAL,
		ObjectFieldConstants.BUSINESS_TYPE_RICH_TEXT,
		ObjectFieldConstants.BUSINESS_TYPE_TEXT
	};

	@Reference
	private DepotEntryLocalService _depotEntryLocalService;

	@Reference(
		target = "(filter.factory.key=" + ObjectDefinitionConstants.STORAGE_TYPE_DEFAULT + ")"
	)
	private FilterFactory<Predicate> _filterFactory;

	@Reference
	private Language _language;

	@Reference
	private ListTypeDefinitionLocalService _listTypeDefinitionLocalService;

	@Reference
	private ListTypeEntryLocalService _listTypeEntryLocalService;

	@Reference
	private ObjectEntryFolderLocalService _objectEntryFolderLocalService;

	@Reference
	private ObjectFieldLocalService _objectFieldLocalService;

}