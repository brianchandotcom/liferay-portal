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

package com.liferay.object.internal.field.business.type;

import com.liferay.dynamic.data.mapping.form.field.type.constants.DDMFormFieldTypeConstants;
import com.liferay.dynamic.data.mapping.model.DDMFormFieldOptions;
import com.liferay.dynamic.data.mapping.model.LocalizedValue;
import com.liferay.list.type.model.ListTypeEntry;
import com.liferay.list.type.service.ListTypeEntryLocalService;
import com.liferay.object.constants.ObjectFieldConstants;
import com.liferay.object.constants.ObjectFieldSettingConstants;
import com.liferay.object.field.business.type.ObjectFieldBusinessType;
import com.liferay.object.field.render.ObjectFieldRenderingContext;
import com.liferay.object.model.ObjectField;
import com.liferay.object.model.ObjectState;
import com.liferay.object.model.ObjectStateFlow;
import com.liferay.object.rest.dto.v1_0.ListEntry;
import com.liferay.object.service.ObjectStateFlowLocalService;
import com.liferay.object.service.ObjectStateLocalService;
import com.liferay.petra.function.transform.TransformUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.ResourceBundleUtil;
import com.liferay.portal.kernel.util.SetUtil;
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
	service = {
		ObjectFieldBusinessType.class, PicklistObjectFieldBusinessType.class
	}
)
public class PicklistObjectFieldBusinessType
	implements ObjectFieldBusinessType {

	@Override
	public Set<String> getAllowedObjectFieldSettingsNames() {
		return SetUtil.fromArray(ObjectFieldSettingConstants.NAME_STATE_FLOW);
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
		return _language.get(
			ResourceBundleUtil.getModuleAndPortalResourceBundle(
				locale, getClass()),
			"choose-from-a-picklist");
	}

	@Override
	public String getLabel(Locale locale) {
		return _language.get(
			ResourceBundleUtil.getModuleAndPortalResourceBundle(
				locale, getClass()),
			"picklist");
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
			_getDDMFormFieldPredefinedValue(
				objectField, objectFieldRenderingContext)
		).build();
	}

	@Override
	public PropertyDefinition.PropertyType getPropertyType() {
		return PropertyDefinition.PropertyType.TEXT;
	}

	@Override
	public void predefineObjectFieldSettings(
			ObjectField newObjectField, ObjectField oldObjectField)
		throws PortalException {

		if (oldObjectField == null) {
			_objectStateFlowLocalService.addDefaultObjectStateFlow(
				newObjectField);

			return;
		}

		_objectStateFlowLocalService.updateDefaultObjectStateFlow(
			newObjectField, oldObjectField);
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

	private LocalizedValue _getDDMFormFieldPredefinedValue(
		ObjectField objectField,
		ObjectFieldRenderingContext objectFieldRenderingContext) {

		LocalizedValue ddmFormFieldPredefinedValueLocalizedValue =
			new LocalizedValue(objectFieldRenderingContext.getLocale());

		if (objectField.isState()) {
			ddmFormFieldPredefinedValueLocalizedValue.addString(
				objectFieldRenderingContext.getLocale(),
				objectField.getDefaultValue());
		}

		return ddmFormFieldPredefinedValueLocalizedValue;
	}

	private List<ListTypeEntry> _getListTypeEntries(
			ObjectField objectField,
			ObjectFieldRenderingContext objectFieldRenderingContext)
		throws PortalException {

		if (!objectField.isState()) {
			return _listTypeEntryLocalService.getListTypeEntries(
				objectField.getListTypeDefinitionId());
		}

		String listEntryKey = objectField.getDefaultValue();

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
	private ObjectStateFlowLocalService _objectStateFlowLocalService;

	@Reference
	private ObjectStateLocalService _objectStateLocalService;

}