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
import com.liferay.object.exception.ObjectFieldSettingNameException;
import com.liferay.object.exception.ObjectFieldSettingValueException;
import com.liferay.object.field.business.type.ObjectFieldBusinessType;
import com.liferay.object.field.render.ObjectFieldRenderingContext;
import com.liferay.object.model.ObjectField;
import com.liferay.object.model.ObjectFieldSetting;
import com.liferay.object.service.ObjectFieldSettingLocalService;
import com.liferay.petra.string.StringPool;
import com.liferay.petra.string.StringUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.vulcan.extension.PropertyDefinition;

import java.util.Collections;
import java.util.HashMap;
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
	property = "object.field.business.type.key=" + ObjectFieldConstants.BUSINESS_TYPE_LONG_TEXT,
	service = {
		LongTextObjectFieldBusinessType.class, ObjectFieldBusinessType.class
	}
)
public class LongTextObjectFieldBusinessType
	implements ObjectFieldBusinessType {

	@Override
	public Set<String> getAllowedObjectFieldSettingsNames() {
		return SetUtil.fromArray("maxLength", "showCounter");
	}

	@Override
	public String getDBType() {
		return ObjectFieldConstants.DB_TYPE_CLOB;
	}

	@Override
	public String getDDMFormFieldTypeName() {
		return DDMFormFieldTypeConstants.TEXT;
	}

	@Override
	public String getDescription(Locale locale) {
		return _language.get(locale, "add-text-that-up-to-65,000-characters");
	}

	@Override
	public String getLabel(Locale locale) {
		return _language.get(locale, "long-text");
	}

	@Override
	public String getName() {
		return ObjectFieldConstants.BUSINESS_TYPE_LONG_TEXT;
	}

	@Override
	public Map<String, Object> getProperties(
		ObjectField objectField,
		ObjectFieldRenderingContext objectFieldRenderingContext) {

		Map<String, Object> properties = HashMapBuilder.<String, Object>put(
			"displayStyle", "multiline"
		).build();

		ListUtil.isNotEmptyForEach(
			_objectFieldSettingLocalService.getObjectFieldObjectFieldSettings(
				objectField.getObjectFieldId()),
			objectFieldSetting -> properties.put(
				objectFieldSetting.getName(), objectFieldSetting.getValue()));

		return properties;
	}

	@Override
	public PropertyDefinition.PropertyType getPropertyType() {
		return PropertyDefinition.PropertyType.TEXT;
	}

	@Override
	public void validateObjectFieldSettings(
			long objectDefinitionId, String objectFieldName,
			List<ObjectFieldSetting> objectFieldSettings)
		throws PortalException {

		ObjectFieldBusinessType.super.validateObjectFieldSettings(
			objectDefinitionId, objectFieldName, objectFieldSettings);

		Map<String, String> objectFieldSettingsValues = new HashMap<>();

		objectFieldSettings.forEach(
			objectFieldSetting -> objectFieldSettingsValues.put(
				objectFieldSetting.getName(), objectFieldSetting.getValue()));

		String showCounter = objectFieldSettingsValues.get("showCounter");

		if (Validator.isNull(showCounter) ||
			StringUtil.equalsIgnoreCase(showCounter, StringPool.FALSE)) {

			if (objectFieldSettingsValues.containsKey("maxLength")) {
				throw new ObjectFieldSettingNameException.NotAllowedNames(
					objectFieldName, Collections.singleton("maxLength"));
			}
		}
		else if (StringUtil.equalsIgnoreCase(showCounter, StringPool.TRUE)) {
			String maxLength = objectFieldSettingsValues.get("maxLength");

			if (Validator.isNull(maxLength)) {
				throw new ObjectFieldSettingValueException.
					MissingRequiredValues(
						objectFieldName, Collections.singleton("maxLength"));
			}

			int maxLengthInteger = GetterUtil.getInteger(maxLength);

			if ((maxLengthInteger < 1) || (maxLengthInteger > 65000)) {
				throw new ObjectFieldSettingValueException.InvalidValue(
					objectFieldName, "maxLength", maxLength);
			}
		}
		else {
			throw new ObjectFieldSettingValueException.InvalidValue(
				objectFieldName, "showCounter", showCounter);
		}
	}

	@Reference
	private Language _language;

	@Reference
	private ObjectFieldSettingLocalService _objectFieldSettingLocalService;

}