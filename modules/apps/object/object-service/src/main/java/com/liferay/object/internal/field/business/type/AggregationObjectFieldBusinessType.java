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
import com.liferay.object.exception.NoSuchObjectFieldException;
import com.liferay.object.exception.NoSuchObjectRelationshipException;
import com.liferay.object.exception.ObjectFieldSettingNameException;
import com.liferay.object.exception.ObjectFieldSettingValueException;
import com.liferay.object.field.business.type.ObjectFieldBusinessType;
import com.liferay.object.field.render.ObjectFieldRenderingContext;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.model.ObjectField;
import com.liferay.object.model.ObjectFieldSetting;
import com.liferay.object.model.ObjectFilter;
import com.liferay.object.model.ObjectRelationship;
import com.liferay.object.service.ObjectDefinitionLocalService;
import com.liferay.object.service.ObjectFieldLocalService;
import com.liferay.object.service.ObjectFieldSettingLocalService;
import com.liferay.object.service.ObjectRelationshipLocalService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.ResourceBundleUtil;
import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.vulcan.extension.PropertyDefinition;

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
			"filters", "function", "objectFieldName", "objectRelationshipName");
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
	public PropertyDefinition.PropertyType getPropertyType() {
		return PropertyDefinition.PropertyType.TEXT;
	}

	@Override
	public Set<String> getRequiredObjectFieldSettingsNames() {
		return SetUtil.fromArray(
			"function", "objectFieldName", "objectRelationshipName");
	}

	@Override
	public void validateObjectFieldSettings(
			long objectDefinitionId, String objectFieldName,
			List<ObjectFieldSetting> objectFieldSettings)
		throws PortalException {

		Stream<ObjectFieldSetting> stream = objectFieldSettings.stream();

		Map<String, Object> objectFieldSettingsValuesMap = stream.collect(
			Collectors.toMap(
				ObjectFieldSetting::getName,
				objectFieldSetting -> {
					if (Objects.equals(
							objectFieldSetting.getName(), "filters")) {

						return objectFieldSetting.getObjectFilters();
					}

					return objectFieldSetting.getValue();
				}));

		String function = GetterUtil.getString(
			objectFieldSettingsValuesMap.get("function"));

		Set<String> requiredObjectFieldSettingsNames =
			getRequiredObjectFieldSettingsNames();

		if (!ArrayUtil.contains(_FUNCTION, function)) {
			throw new ObjectFieldSettingValueException.InvalidValue(
				objectFieldName, "function", function);
		}
		else if (Objects.equals(function, "COUNT")) {
			requiredObjectFieldSettingsNames.remove("objectFieldName");
		}

		Set<String> missingRequiredObjectFieldSettingsNames = new HashSet<>();

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

		try {
			ObjectRelationship objectRelationship =
				_objectRelationshipLocalService.getObjectRelationship(
					objectDefinitionId,
					GetterUtil.getString(
						objectFieldSettingsValuesMap.get(
							"objectRelationshipName")));

			ObjectDefinition objectDefinition =
				_objectDefinitionLocalService.fetchObjectDefinition(
					objectRelationship.getObjectDefinitionId2());

			if (!Objects.equals(function, "COUNT")) {
				ObjectField objectField =
					_objectFieldLocalService.getObjectField(
						objectDefinition.getObjectDefinitionId(),
						GetterUtil.getString(
							objectFieldSettingsValuesMap.get(
								"objectFieldName")));

				if (!ArrayUtil.contains(
						_NUMERIC_BUSINESS_TYPES,
						objectField.getBusinessType())) {

					throw new ObjectFieldSettingValueException.InvalidValue(
						objectFieldName, "objectFieldName",
						GetterUtil.getString(
							objectFieldSettingsValuesMap.get(
								"objectFieldName")));
				}
			}

			_validateObjectFilters(
				objectDefinition, objectFieldName,
				(List<ObjectFilter>)objectFieldSettingsValuesMap.get(
					"filters"));
		}
		catch (NoSuchObjectFieldException noSuchObjectFieldException) {
			throw new ObjectFieldSettingValueException.InvalidValue(
				objectFieldName, "objectFieldName",
				GetterUtil.getString(
					objectFieldSettingsValuesMap.get("objectFieldName")),
				noSuchObjectFieldException);
		}
		catch (NoSuchObjectRelationshipException
					noSuchObjectRelationshipException) {

			throw new ObjectFieldSettingValueException.InvalidValue(
				objectFieldName, "objectRelationshipName",
				GetterUtil.getString(
					objectFieldSettingsValuesMap.get("objectRelationshipName")),
				noSuchObjectRelationshipException);
		}
		catch (PortalException portalException) {
			throw new RuntimeException(portalException);
		}
	}

	private void _validateObjectFilters(
			ObjectDefinition objectDefinition, String objectFieldName,
			List<ObjectFilter> objectFilters)
		throws PortalException {

		if (ListUtil.isEmpty(objectFilters)) {
			return;
		}

		for (ObjectFilter objectFilter : objectFilters) {
			Set<String> missingObjectFilterValues = new HashSet<>();

			_validateObjectFilterValue(
				missingObjectFilterValues, "filterBy",
				objectFilter.getFilterBy());
			_validateObjectFilterValue(
				missingObjectFilterValues, "filterType",
				objectFilter.getFilterType());
			_validateObjectFilterValue(
				missingObjectFilterValues, "json", objectFilter.getJSON());

			if (!missingObjectFilterValues.isEmpty()) {
				throw new ObjectFieldSettingValueException.
					MissingRequiredValues(
						objectFieldName, missingObjectFilterValues);
			}

			ObjectField objectField = _objectFieldLocalService.fetchObjectField(
				objectDefinition.getObjectDefinitionId(),
				GetterUtil.getString(objectFilter.getFilterBy()));

			if ((objectField == null) ||
				objectField.compareBusinessType(
					ObjectFieldConstants.BUSINESS_TYPE_AGGREGATION)) {

				throw new ObjectFieldSettingValueException.InvalidValue(
					objectFieldName, "filterBy",
					GetterUtil.getString(objectFilter.getFilterBy()));
			}
		}
	}

	private void _validateObjectFilterValue(
		Set<String> missingObjectFilterValues, String objectFilterName,
		String objectFilterValue) {

		if (Validator.isNull(objectFilterValue)) {
			missingObjectFilterValues.add(objectFilterName);
		}
	}

	private static final String[] _FUNCTION = {
		"AVERAGE", "COUNT", "MAX", "MIN", "SUM"
	};

	private static final String[] _NUMERIC_BUSINESS_TYPES = {
		ObjectFieldConstants.BUSINESS_TYPE_DECIMAL,
		ObjectFieldConstants.BUSINESS_TYPE_INTEGER,
		ObjectFieldConstants.BUSINESS_TYPE_LONG_INTEGER,
		ObjectFieldConstants.BUSINESS_TYPE_PRECISION_DECIMAL
	};

	@Reference
	private Language _language;

	@Reference
	private ObjectDefinitionLocalService _objectDefinitionLocalService;

	@Reference
	private ObjectFieldLocalService _objectFieldLocalService;

	@Reference
	private ObjectFieldSettingLocalService _objectFieldSettingLocalService;

	@Reference
	private ObjectRelationshipLocalService _objectRelationshipLocalService;

}