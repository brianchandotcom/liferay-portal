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
import com.liferay.object.exception.NoSuchObjectStateException;
import com.liferay.object.field.business.type.ObjectFieldBusinessType;
import com.liferay.object.field.render.ObjectFieldRenderingContext;
import com.liferay.object.model.ObjectField;
import com.liferay.object.model.ObjectState;
import com.liferay.object.model.ObjectStateFlow;
import com.liferay.object.rest.dto.v1_0.ListEntry;
import com.liferay.object.service.ObjectStateFlowLocalService;
import com.liferay.object.service.ObjectStateLocalService;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.ResourceBundleUtil;
import com.liferay.portal.vulcan.util.TransformUtil;

import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Marcela Cunha
 */
@Component(
	immediate = true,
	property = "object.field.business.type.key=" + ObjectFieldConstants.BUSINESS_TYPE_PICKLIST,
	service = {
		ObjectFieldBusinessType.class, PicklistObjectFieldBusinessType.class
	}
)
public class PicklistObjectFieldBusinessType
	implements ObjectFieldBusinessType {

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
		return LanguageUtil.get(
			ResourceBundleUtil.getModuleAndPortalResourceBundle(
				locale, getClass()),
			"choose-from-a-picklist");
	}

	@Override
	public String getLabel(Locale locale) {
		return LanguageUtil.get(
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
		throws NoSuchObjectStateException {

		return HashMapBuilder.<String, Object>put(
			"options",
			_getDDMFormFieldOptions(objectField, objectFieldRenderingContext)
		).put(
			"predefinedValue",
			_getDDMFormFieldPredefinedValue(
				objectField, objectFieldRenderingContext)
		).build();
	}

	private DDMFormFieldOptions _getDDMFormFieldOptions(
			ObjectField objectField,
			ObjectFieldRenderingContext objectFieldRenderingContext)
		throws NoSuchObjectStateException {

		DDMFormFieldOptions ddmFormFieldOptions = new DDMFormFieldOptions();

		List<ListTypeEntry> listTypeEntries =
			_listTypeEntryLocalService.getListTypeEntries(
				objectField.getListTypeDefinitionId());

		if (objectField.isState()) {
			String listEntryKey = objectField.getDefaultValue();

			if (MapUtil.isNotEmpty(
					objectFieldRenderingContext.getProperties())) {

				ListEntry listEntry =
					(ListEntry)objectFieldRenderingContext.getProperty(
						objectField.getName());

				listEntryKey = listEntry.getKey();
			}

			ListTypeEntry listTypeEntry =
				_listTypeEntryLocalService.fetchListTypeEntry(
					objectField.getListTypeDefinitionId(), listEntryKey);

			ObjectStateFlow objectStateFlow =
				_objectStateFlowLocalService.getObjectFieldObjectStateFlow(
					objectField.getObjectFieldId());

			ObjectState currentObjectState =
				_objectStateLocalService.
					getObjectStatesByListTypeEntryIdAndObjectStateFlowId(
						listTypeEntry.getListTypeEntryId(),
						objectStateFlow.getObjectStateFlowId());

			List<Long> listTypeEntryIds = ListUtil.toList(
				_objectStateLocalService.getNextObjectStates(
					currentObjectState.getObjectStateId()),
				ObjectState::getListTypeEntryId);

			listTypeEntryIds.add(currentObjectState.getListTypeEntryId());

			listTypeEntries = TransformUtil.transform(
				listTypeEntryIds,
				listTypeEntryId -> _listTypeEntryLocalService.getListTypeEntry(
					listTypeEntryId));
		}

		for (ListTypeEntry listTypeEntry : listTypeEntries) {
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

	@Reference
	private ListTypeEntryLocalService _listTypeEntryLocalService;

	@Reference
	private ObjectStateFlowLocalService _objectStateFlowLocalService;

	@Reference
	private ObjectStateLocalService _objectStateLocalService;

}