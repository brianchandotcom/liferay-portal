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
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.ResourceBundleUtil;
import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.portal.kernel.util.Validator;

import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Marcela Cunha
 */
@Component(
	immediate = true,
	property = "object.field.business.type.key=" + ObjectFieldConstants.BUSINESS_TYPE_AGGREGATION,
	service = {
		AggregationObjectFieldBusinessType.class, ObjectFieldBusinessType.class
	}
)
public class AggregationObjectFieldBusinessType
	implements ObjectFieldBusinessType {

	@Override
	public Set<String> getAllowedObjectFieldSettingsNames() {
		return SetUtil.fromArray(
			"filters", "function", "relationship", "summarizeField");
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
	public String getDescription(Locale locale) {
		return _language.get(
			ResourceBundleUtil.getModuleAndPortalResourceBundle(
				locale, getClass()),
			"summarize-data-values");
	}

	@Override
	public String getLabel(Locale locale) {
		return _language.get(
			ResourceBundleUtil.getModuleAndPortalResourceBundle(
				locale, getClass()),
			"aggregation");
	}

	@Override
	public String getName() {
		return ObjectFieldConstants.BUSINESS_TYPE_AGGREGATION;
	}

	@Override
	public Map<String, Object> getProperties(
		ObjectField objectField,
		ObjectFieldRenderingContext objectFieldRenderingContext) {

		Map<String, Object> properties = HashMapBuilder.<String, Object>put(
			"readOnly", true
		).build();

		ListUtil.isNotEmptyForEach(
			_objectFieldSettingLocalService.getObjectFieldObjectFieldSettings(
				objectField.getObjectFieldId()),
			objectFieldSetting -> properties.put(
				objectFieldSetting.getName(), objectFieldSetting.getValue()));

		return properties;
	}

	@Override
	public Set<String> getRequiredObjectFieldSettingsNames() {
		return SetUtil.fromArray("function", "relationship", "summarizeField");
	}

	@Override
	public void validateObjectFieldSettings(
			String objectFieldName,
			List<ObjectFieldSetting> objectFieldSettings)
		throws PortalException {

		Set<String> missingRequiredObjectFieldSettingsNames = new HashSet<>();

		Stream<ObjectFieldSetting> stream = objectFieldSettings.stream();

		Map<String, String> objectFieldSettingsValuesMap = stream.collect(
			Collectors.toMap(
				ObjectFieldSetting::getName, ObjectFieldSetting::getValue));

		Set<String> requiredObjectFieldSettingsNames =
			getRequiredObjectFieldSettingsNames();

		if (Objects.equals(
				GetterUtil.getString(
					objectFieldSettingsValuesMap.get("function")),
				"COUNT")) {

			requiredObjectFieldSettingsNames.remove("summarizeField");
		}

		for (String requiredObjectFieldSettingName :
				requiredObjectFieldSettingsNames) {

			if (Validator.isNull(
					objectFieldSettingsValuesMap.get(
						requiredObjectFieldSettingName))) {

				missingRequiredObjectFieldSettingsNames.add(
					requiredObjectFieldSettingName);
			}
		}

		if (!missingRequiredObjectFieldSettingsNames.isEmpty()) {
			throw new ObjectFieldSettingValueException.MissingRequiredValues(
				objectFieldName, missingRequiredObjectFieldSettingsNames);
		}

		Set<String> notAllowedObjectFieldSettingsNames = new HashSet<>(
			objectFieldSettingsValuesMap.keySet());

		notAllowedObjectFieldSettingsNames.removeAll(
			getAllowedObjectFieldSettingsNames());
		notAllowedObjectFieldSettingsNames.removeAll(
			requiredObjectFieldSettingsNames);

		if (!notAllowedObjectFieldSettingsNames.isEmpty()) {
			throw new ObjectFieldSettingNameException.NotAllowedNames(
				objectFieldName, notAllowedObjectFieldSettingsNames);
		}
	}

	@Reference
	private Language _language;

	@Reference
	private ObjectFieldSettingLocalService _objectFieldSettingLocalService;

}