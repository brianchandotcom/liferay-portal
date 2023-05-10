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

package com.liferay.object.field.business.type;

import com.liferay.object.constants.ObjectFieldConstants;
import com.liferay.object.constants.ObjectFieldSettingConstants;
import com.liferay.object.exception.ObjectFieldDefaultValueException;
import com.liferay.object.exception.ObjectFieldSettingNameException;
import com.liferay.object.exception.ObjectFieldSettingValueException;
import com.liferay.object.field.render.ObjectFieldRenderingContext;
import com.liferay.object.model.ObjectField;
import com.liferay.object.model.ObjectFieldSetting;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.feature.flag.FeatureFlagManagerUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.vulcan.extension.PropertyDefinition;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

/**
 * @author Marcela Cunha
 */
public interface ObjectFieldBusinessType {

	public default Set<String> getAllowedObjectFieldSettingsNames() {
		return Collections.emptySet();
	}

	public String getDBType();

	public String getDDMFormFieldTypeName();

	public default String getDescription(Locale locale) {
		return StringPool.BLANK;
	}

	public String getLabel(Locale locale);

	public String getName();

	public default Map<String, Object> getProperties(
			ObjectField objectField,
			ObjectFieldRenderingContext objectFieldRenderingContext)
		throws PortalException {

		return Collections.emptyMap();
	}

	public PropertyDefinition.PropertyType getPropertyType();

	public default Set<String> getRequiredObjectFieldSettingsNames(
		ObjectField objectField) {

		return Collections.emptySet();
	}

	public default Set<String> getUnmodifiablObjectFieldSettingsNames() {
		return Collections.emptySet();
	}

	public default Object getValue(
			ObjectField objectField, Map<String, Object> values)
		throws PortalException {

		return values.get(objectField.getName());
	}

	public default boolean isVisible() {
		return true;
	}

	public default void predefineObjectFieldSettings(
			ObjectField newObjectField, ObjectField oldObjectField,
			List<ObjectFieldSetting> objectFieldSettings)
		throws PortalException {
	}

	public default void validateObjectFieldSettings(
			ObjectField objectField,
			List<ObjectFieldSetting> objectFieldSettings)
		throws PortalException {

		Set<String> missingRequiredObjectFieldSettingsNames = new HashSet<>();

		Map<String, String> objectFieldSettingsValuesMap = new HashMap<>();

		for (ObjectFieldSetting objectFieldSetting : objectFieldSettings) {
			objectFieldSettingsValuesMap.put(
				objectFieldSetting.getName(), objectFieldSetting.getValue());
		}

		for (String requiredObjectFieldSettingName :
				getRequiredObjectFieldSettingsNames(objectField)) {

			if (Validator.isNull(
					objectFieldSettingsValuesMap.get(
						requiredObjectFieldSettingName))) {

				missingRequiredObjectFieldSettingsNames.add(
					requiredObjectFieldSettingName);
			}
		}

		if (!missingRequiredObjectFieldSettingsNames.isEmpty()) {
			throw new ObjectFieldSettingValueException.MissingRequiredValues(
				objectField.getName(), missingRequiredObjectFieldSettingsNames);
		}

		Set<String> notAllowedObjectFieldSettingsNames = new HashSet<>(
			objectFieldSettingsValuesMap.keySet());

		notAllowedObjectFieldSettingsNames.removeAll(
			getAllowedObjectFieldSettingsNames());
		notAllowedObjectFieldSettingsNames.removeAll(
			getRequiredObjectFieldSettingsNames(objectField));

		if (!notAllowedObjectFieldSettingsNames.isEmpty()) {
			if (!FeatureFlagManagerUtil.isEnabled("LPS-163716") &&
				notAllowedObjectFieldSettingsNames.contains(
					ObjectFieldSettingConstants.NAME_DEFAULT_VALUE)) {

				throw new ObjectFieldDefaultValueException(
					StringBundler.concat(
						"Object field can only have a default type when the ",
						"business type is \"",
						ObjectFieldConstants.BUSINESS_TYPE_PICKLIST, "\""));
			}

			throw new ObjectFieldSettingNameException.NotAllowedNames(
				objectField.getName(), notAllowedObjectFieldSettingsNames);
		}

		validateObjectFieldSettingsDefaultValue(
			objectField, objectFieldSettingsValuesMap);
	}

	public default void validateObjectFieldSettingsDefaultValue(
			ObjectField objectField,
			Map<String, String> objectFieldSettingsValuesMap)
		throws PortalException {

		String defaultValueType = objectFieldSettingsValuesMap.get(
			ObjectFieldSettingConstants.NAME_DEFAULT_VALUE_TYPE);

		if (defaultValueType == null) {
			return;
		}

		if (!(StringUtil.equals(
				defaultValueType,
				ObjectFieldSettingConstants.VALUE_EXPRESSION_BUILDER) ||
			  StringUtil.equals(
				  defaultValueType,
				  ObjectFieldSettingConstants.VALUE_INPUT_AS_VALUE))) {

			throw new ObjectFieldSettingValueException.InvalidValue(
				objectField.getName(),
				ObjectFieldSettingConstants.NAME_DEFAULT_VALUE_TYPE,
				defaultValueType);
		}
	}

}