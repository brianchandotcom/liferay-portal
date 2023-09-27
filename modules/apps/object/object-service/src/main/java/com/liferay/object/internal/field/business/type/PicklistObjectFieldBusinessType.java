/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.object.internal.field.business.type;

import com.liferay.dynamic.data.mapping.form.field.type.constants.DDMFormFieldTypeConstants;
import com.liferay.dynamic.data.mapping.model.DDMFormFieldOptions;
import com.liferay.dynamic.data.mapping.model.LocalizedValue;
import com.liferay.list.type.model.ListTypeEntry;
import com.liferay.list.type.service.ListTypeEntryLocalService;
import com.liferay.object.constants.ObjectFieldConstants;
import com.liferay.object.constants.ObjectFieldSettingConstants;
import com.liferay.object.exception.ObjectFieldSettingValueException;
import com.liferay.object.field.business.type.ObjectFieldBusinessType;
import com.liferay.object.field.render.ObjectFieldRenderingContext;
import com.liferay.object.field.setting.util.ObjectFieldSettingUtil;
import com.liferay.object.model.ObjectField;
import com.liferay.object.model.ObjectFieldSetting;
import com.liferay.object.model.ObjectState;
import com.liferay.object.model.ObjectStateFlow;
import com.liferay.object.rest.dto.v1_0.ListEntry;
import com.liferay.object.service.ObjectFieldSettingLocalService;
import com.liferay.object.service.ObjectStateFlowLocalService;
import com.liferay.object.service.ObjectStateLocalService;
import com.liferay.petra.function.transform.TransformUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.vulcan.extension.PropertyDefinition;

import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Marcela Cunha
 */
@Component(
	property = "object.field.business.type.key=" + ObjectFieldConstants.BUSINESS_TYPE_PICKLIST,
	service = ObjectFieldBusinessType.class
)
public class PicklistObjectFieldBusinessType
	implements ObjectFieldBusinessType {

	@Override
	public Set<String> getAllowedObjectFieldSettingsNames() {
		return SetUtil.fromArray(
			ObjectFieldSettingConstants.NAME_DEFAULT_VALUE,
			ObjectFieldSettingConstants.NAME_DEFAULT_VALUE_TYPE,
			ObjectFieldSettingConstants.NAME_STATE_FLOW);
	}

	@Override
	public String getDBType() {
		return ObjectFieldConstants.DB_TYPE_STRING;
	}

	@Override
	public String getDDMFormFieldTypeName() {
		return DDMFormFieldTypeConstants.SELECT;
	}

	@Override
	public String getDescription(Locale locale) {
		return _language.get(locale, "choose-from-a-picklist");
	}

	@Override
	public String getLabel(Locale locale) {
		return _language.get(locale, "picklist");
	}

	@Override
	public String getName() {
		return ObjectFieldConstants.BUSINESS_TYPE_PICKLIST;
	}

	@Override
	public Map<String, Object> getProperties(
			ObjectField objectField,
			ObjectFieldRenderingContext objectFieldRenderingContext)
		throws PortalException {

		return HashMapBuilder.<String, Object>put(
			"options",
			_getDDMFormFieldOptions(objectField, objectFieldRenderingContext)
		).put(
			"predefinedValue",
			() -> {
				LocalizedValue localizedValue = new LocalizedValue(
					objectFieldRenderingContext.getLocale());

				localizedValue.addString(
					objectFieldRenderingContext.getLocale(),
					ObjectFieldSettingUtil.getDefaultValueAsString(
						null, objectField.getObjectFieldId(),
						_objectFieldSettingLocalService, null));

				return localizedValue;
			}
		).build();
	}

	@Override
	public PropertyDefinition.PropertyType getPropertyType() {
		return PropertyDefinition.PropertyType.TEXT;
	}

	@Override
	public Set<String> getRequiredObjectFieldSettingsNames(
		ObjectField objectField) {

		if (!objectField.isState()) {
			return Collections.emptySet();
		}

		return SetUtil.fromArray(
			ObjectFieldSettingConstants.NAME_DEFAULT_VALUE,
			ObjectFieldSettingConstants.NAME_DEFAULT_VALUE_TYPE);
	}

	@Override
	public void predefineObjectFieldSettings(
			ObjectField newObjectField, ObjectField oldObjectField,
			List<ObjectFieldSetting> objectFieldSettings)
		throws PortalException {

		if (oldObjectField != null) {
			_objectStateFlowLocalService.updateDefaultObjectStateFlow(
				newObjectField, oldObjectField);

			return;
		}

		for (ObjectFieldSetting objectFieldSetting : objectFieldSettings) {
			if (!StringUtil.equals(
					objectFieldSetting.getName(),
					ObjectFieldSettingConstants.NAME_STATE_FLOW) ||
				(objectFieldSetting.getObjectStateFlow() == null)) {

				continue;
			}

			ObjectStateFlow objectStateFlow =
				objectFieldSetting.getObjectStateFlow();

			_objectStateFlowLocalService.addObjectStateFlow(
				newObjectField.getUserId(), newObjectField.getObjectFieldId(),
				objectStateFlow.getObjectStates());

			return;
		}

		_objectStateFlowLocalService.addDefaultObjectStateFlow(newObjectField);
	}

	@Override
	public void validateObjectFieldSettingsDefaultValue(
			ObjectField objectField,
			Map<String, String> objectFieldSettingsValuesMap)
		throws PortalException {

		if (objectFieldSettingsValuesMap.isEmpty()) {
			return;
		}

		ObjectFieldBusinessType.super.validateObjectFieldSettingsDefaultValue(
			objectField, objectFieldSettingsValuesMap);

		String defaultValueType = objectFieldSettingsValuesMap.get(
			ObjectFieldSettingConstants.NAME_DEFAULT_VALUE_TYPE);

		if (StringUtil.equals(
				defaultValueType,
				ObjectFieldSettingConstants.VALUE_EXPRESSION_BUILDER)) {

			if (objectField.isState()) {
				throw new ObjectFieldSettingValueException.InvalidValue(
					objectField.getName(),
					ObjectFieldSettingConstants.NAME_DEFAULT_VALUE_TYPE,
					defaultValueType);
			}

			return;
		}

		String defaultValue = objectFieldSettingsValuesMap.get(
			ObjectFieldSettingConstants.NAME_DEFAULT_VALUE);

		if (defaultValue == null) {
			return;
		}

		ListTypeEntry listTypeEntry =
			_listTypeEntryLocalService.fetchListTypeEntry(
				objectField.getListTypeDefinitionId(), defaultValue);

		if (listTypeEntry == null) {
			throw new ObjectFieldSettingValueException.InvalidValue(
				objectField.getName(),
				ObjectFieldSettingConstants.NAME_DEFAULT_VALUE, defaultValue);
		}
	}

	private DDMFormFieldOptions _getDDMFormFieldOptions(
			ObjectField objectField,
			ObjectFieldRenderingContext objectFieldRenderingContext)
		throws PortalException {

		DDMFormFieldOptions ddmFormFieldOptions = new DDMFormFieldOptions();

		for (ListTypeEntry listTypeEntry :
				_getListTypeEntries(objectField, objectFieldRenderingContext)) {

			ddmFormFieldOptions.addOptionLabel(
				listTypeEntry.getKey(), objectFieldRenderingContext.getLocale(),
				GetterUtil.getString(
					listTypeEntry.getName(
						objectFieldRenderingContext.getLocale()),
					listTypeEntry.getName(
						listTypeEntry.getDefaultLanguageId())));
		}

		return ddmFormFieldOptions;
	}

	private List<ListTypeEntry> _getListTypeEntries(
			ObjectField objectField,
			ObjectFieldRenderingContext objectFieldRenderingContext)
		throws PortalException {

		if (!objectField.isState()) {
			return _listTypeEntryLocalService.getListTypeEntries(
				objectField.getListTypeDefinitionId());
		}

		String listEntryKey = ObjectFieldSettingUtil.getDefaultValueAsString(
			null, objectField.getObjectFieldId(),
			_objectFieldSettingLocalService, null);

		if (MapUtil.isNotEmpty(objectFieldRenderingContext.getProperties())) {
			ListEntry listEntry =
				(ListEntry)objectFieldRenderingContext.getProperty(
					objectField.getName());

			if (listEntry == null) {
				return _listTypeEntryLocalService.getListTypeEntries(
					objectField.getListTypeDefinitionId());
			}

			listEntryKey = listEntry.getKey();
		}

		ListTypeEntry listTypeEntry =
			_listTypeEntryLocalService.fetchListTypeEntry(
				objectField.getListTypeDefinitionId(), listEntryKey);

		if (listTypeEntry == null) {
			return Collections.emptyList();
		}

		ObjectStateFlow objectStateFlow =
			_objectStateFlowLocalService.fetchObjectFieldObjectStateFlow(
				objectField.getObjectFieldId());

		ObjectState objectState =
			_objectStateLocalService.getObjectStateFlowObjectState(
				listTypeEntry.getListTypeEntryId(),
				objectStateFlow.getObjectStateFlowId());

		List<ListTypeEntry> listTypeEntries = TransformUtil.transform(
			_objectStateLocalService.getNextObjectStates(
				objectState.getObjectStateId()),
			nextObjectState -> _listTypeEntryLocalService.getListTypeEntry(
				nextObjectState.getListTypeEntryId()));

		listTypeEntries.add(
			_listTypeEntryLocalService.getListTypeEntry(
				objectState.getListTypeEntryId()));

		return listTypeEntries;
	}

	@Reference
	private Language _language;

	@Reference
	private ListTypeEntryLocalService _listTypeEntryLocalService;

	@Reference
	private ObjectFieldSettingLocalService _objectFieldSettingLocalService;

	@Reference
	private ObjectStateFlowLocalService _objectStateFlowLocalService;

	@Reference
	private ObjectStateLocalService _objectStateLocalService;

}