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
import com.liferay.object.constants.ObjectFieldConstants;
import com.liferay.object.constants.ObjectFieldSettingConstants;
import com.liferay.object.field.business.type.ObjectFieldBusinessType;
import com.liferay.object.model.ObjectField;
import com.liferay.object.model.ObjectFieldSetting;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.portal.vulcan.extension.PropertyDefinition;

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
	property = "object.field.business.type.key=" + ObjectFieldConstants.BUSINESS_TYPE_TEXT,
	service = ObjectFieldBusinessType.class
)
public class TextObjectFieldBusinessType extends BaseObjectFieldBusinessType {

	@Override
	public Set<String> getAllowedObjectFieldSettingsNames() {
		return SetUtil.fromArray(
			ObjectFieldSettingConstants.NAME_MAX_LENGTH,
			ObjectFieldSettingConstants.NAME_SHOW_COUNTER,
			ObjectFieldSettingConstants.NAME_UNIQUE_VALUES);
	}

	@Override
	public String getDBType() {
		return ObjectFieldConstants.DB_TYPE_STRING;
	}

	@Override
	public String getDDMFormFieldTypeName() {
		return DDMFormFieldTypeConstants.TEXT;
	}

	@Override
	public String getDDMFormFieldTypeName(boolean localized) {
		if (localized) {
			return DDMFormFieldTypeConstants.LOCALIZABLE_TEXT;
		}

		return getDDMFormFieldTypeName();
	}

	@Override
	public String getDescription(Locale locale) {
		return _language.get(locale, "add-text-up-to-280-characters");
	}

	@Override
	public String getLabel(Locale locale) {
		return _language.get(locale, "text");
	}

	@Override
	public String getName() {
		return ObjectFieldConstants.BUSINESS_TYPE_TEXT;
	}

	@Override
	public PropertyDefinition.PropertyType getPropertyType() {
		return PropertyDefinition.PropertyType.TEXT;
	}

	@Override
	public Set<String> getUnmodifiableObjectFieldSettingsNames() {
		return SetUtil.fromArray(
			ObjectFieldSettingConstants.NAME_UNIQUE_VALUES);
	}

	@Override
	public void validateObjectFieldSettings(
			ObjectField objectField,
			List<ObjectFieldSetting> objectFieldSettings)
		throws PortalException {

		super.validateObjectFieldSettings(objectField, objectFieldSettings);

		Map<String, String> objectFieldSettingsValues =
			getObjectFieldSettingsValues(objectFieldSettings);

		validateBooleanObjectFieldSetting(
			objectField.getName(),
			ObjectFieldSettingConstants.NAME_UNIQUE_VALUES,
			objectFieldSettingsValues);
		validateRelatedObjectFieldSettings(
			objectField, ObjectFieldSettingConstants.NAME_SHOW_COUNTER,
			ObjectFieldSettingConstants.NAME_MAX_LENGTH,
			objectFieldSettingsValues);
	}

	@Reference
	private Language _language;

}