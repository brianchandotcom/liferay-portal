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
import com.liferay.list.type.model.ListTypeEntry;
import com.liferay.list.type.service.ListTypeEntryLocalService;
import com.liferay.object.constants.ObjectFieldConstants;
import com.liferay.object.field.business.type.ObjectFieldBusinessType;
import com.liferay.object.field.render.ObjectFieldRenderingContext;
import com.liferay.object.model.ObjectField;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.vulcan.extension.PropertyDefinition;

import java.util.Locale;
import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Paulo Albuquerque
 */
@Component(
	property = "object.field.business.type.key=" + ObjectFieldConstants.BUSINESS_TYPE_MULTISELECT_PICKLIST,
	service = {
		MultiselectPicklistObjectFieldBusinessType.class,
		ObjectFieldBusinessType.class
	}
)
public class MultiselectPicklistObjectFieldBusinessType
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
		return _language.get(locale, "choose-from-a-picklist");
	}

	@Override
	public String getLabel(Locale locale) {
		return _language.get(locale, "multiselect-picklist");
	}

	@Override
	public String getName() {
		return ObjectFieldConstants.BUSINESS_TYPE_MULTISELECT_PICKLIST;
	}

	@Override
	public Map<String, Object> getProperties(
			ObjectField objectField,
			ObjectFieldRenderingContext objectFieldRenderingContext)
		throws PortalException {

		return HashMapBuilder.<String, Object>put(
			"defaultSearch", true
		).put(
			"multiple", true
		).put(
			"options",
			() -> {
				DDMFormFieldOptions ddmFormFieldOptions =
					new DDMFormFieldOptions();

				for (ListTypeEntry listTypeEntry :
						_listTypeEntryLocalService.getListTypeEntries(
							objectField.getListTypeDefinitionId())) {

					ddmFormFieldOptions.addOptionLabel(
						listTypeEntry.getKey(),
						objectFieldRenderingContext.getLocale(),
						GetterUtil.getString(
							listTypeEntry.getName(
								objectFieldRenderingContext.getLocale()),
							listTypeEntry.getName(
								listTypeEntry.getDefaultLanguageId())));
				}

				return ddmFormFieldOptions;
			}
		).build();
	}

	@Override
	public PropertyDefinition.PropertyType getPropertyType() {
		return PropertyDefinition.PropertyType.TEXT;
	}

	@Reference
	private Language _language;

	@Reference
	private ListTypeEntryLocalService _listTypeEntryLocalService;

}