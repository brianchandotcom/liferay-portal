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

package com.liferay.dynamic.data.mapping.io.internal;

import com.liferay.dynamic.data.mapping.annotations.DDMFormField;
import com.liferay.dynamic.data.mapping.io.DDMFormJSONSerializer;
import com.liferay.dynamic.data.mapping.io.DDMFormLayoutJSONSerializer;
import com.liferay.dynamic.data.mapping.model.DDMForm;
import com.liferay.dynamic.data.mapping.model.DDMFormLayout;
import com.liferay.dynamic.data.mapping.model.DDMFormLayoutColumn;
import com.liferay.dynamic.data.mapping.model.DDMFormLayoutPage;
import com.liferay.dynamic.data.mapping.model.DDMFormLayoutRow;
import com.liferay.dynamic.data.mapping.util.DDMFormFactory;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.util.Function;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Marcellus Tavares
 */
public class DDMFormFieldTypeSettingsSerializerHelper {

	public DDMFormFieldTypeSettingsSerializerHelper(
		Class<?> ddmFormFieldTypeSettings,
		DDMFormJSONSerializer ddmFormJSONSerializer,
		DDMFormLayoutJSONSerializer ddmFormLayoutJSONSerializer,
		JSONFactory jsonFactory) {

		_ddmFormFieldTypeSettings = ddmFormFieldTypeSettings;
		_ddmFormJSONSerializer = ddmFormJSONSerializer;
		_ddmFormLayoutJSONSerializer = ddmFormLayoutJSONSerializer;
		_jsonFactory = jsonFactory;
	}

	public JSONObject getSettingsJSONObject() throws PortalException {
		DDMForm ddmForm = DDMFormFactory.create(_ddmFormFieldTypeSettings);

		String serializedDDMForm = _ddmFormJSONSerializer.serialize(ddmForm);

		return _jsonFactory.createJSONObject(serializedDDMForm);
	}

	public JSONObject getSettingsLayoutJSONObject() throws PortalException {
		DDMFormLayout ddmFormLayout = createDDMFormLayout(
			_ddmFormFieldTypeSettings);

		String serializedDDMFormLayout = _ddmFormLayoutJSONSerializer.serialize(
			ddmFormLayout);

		return _jsonFactory.createJSONObject(serializedDDMFormLayout);
	}

	protected void collectDDMFormFieldSetting(
		Class<?> clazz,
		Map<String, List<DDMFormFieldSetting>> ddmFormFieldSettingsMap) {

		for (Class<?> interfaceClass : clazz.getInterfaces()) {
			collectDDMFormFieldSetting(interfaceClass, ddmFormFieldSettingsMap);
		}

		for (Method method : clazz.getDeclaredMethods()) {
			if (!method.isAnnotationPresent(_DDM_FORM_FIELD_ANNOTATION)) {
				continue;
			}

			DDMFormField ddmFormField = method.getAnnotation(
				DDMFormField.class);

			String ddmFormFieldName = method.getName();

			if (Validator.isNotNull(ddmFormField.name())) {
				ddmFormFieldName = ddmFormField.name();
			}

			DDMFormFieldSetting setting = new DDMFormFieldSetting(
				ddmFormFieldName, ddmFormField.properties());

			List<DDMFormFieldSetting> ddmFormFieldSettings =
				ddmFormFieldSettingsMap.get(setting.getCategory());

			for (DDMFormFieldSetting ddmFormFieldSetting :
					ddmFormFieldSettings) {

				if (Validator.equals(
						ddmFormFieldSetting.getName(), setting.getName())) {

					ddmFormFieldSettings.remove(ddmFormFieldSetting);

					break;
				}
			}

			ddmFormFieldSettings.add(setting);
		}
	}

	protected DDMFormLayout createDDMFormLayout(
		Class<?> ddmFormLayoutSettings) {

		Map<String, List<DDMFormFieldSetting>> ddmFormFieldSettingsMap =
			new HashMap<>();

		ddmFormFieldSettingsMap.put(
			"basic", new ArrayList<DDMFormFieldSetting>());
		ddmFormFieldSettingsMap.put(
			"advanced", new ArrayList<DDMFormFieldSetting>());

		collectDDMFormFieldSetting(
			ddmFormLayoutSettings, ddmFormFieldSettingsMap);

		DDMFormLayout ddmFormLayout = new DDMFormLayout();

		ddmFormLayout.addDDMFormLayoutPage(
			createSettingPage(ddmFormFieldSettingsMap.get("basic")));
		ddmFormLayout.addDDMFormLayoutPage(
			createSettingPage(ddmFormFieldSettingsMap.get("advanced")));

		return ddmFormLayout;
	}

	protected DDMFormLayoutPage createSettingPage(
		List<DDMFormFieldSetting> ddmFormFieldSettings) {

		DDMFormLayoutPage ddmFormLayoutPage = new DDMFormLayoutPage();

		DDMFormLayoutRow ddmFormLayoutRow = new DDMFormLayoutRow();

		DDMFormLayoutColumn ddmFormLayoutColumn = new DDMFormLayoutColumn();

		ddmFormLayoutColumn.setSize(DDMFormLayoutColumn.FULL);

		Collections.sort(ddmFormFieldSettings);

		List<String> ddmFormFieldNames = ListUtil.toList(
			ddmFormFieldSettings,
			new Function<DDMFormFieldSetting, String>() {

				@Override
				public String apply(DDMFormFieldSetting ddmFormFieldSetting) {
					return ddmFormFieldSetting.getName();
				}

			});

		ddmFormLayoutColumn.setDDMFormFieldNames(ddmFormFieldNames);

		ddmFormLayoutRow.addDDMFormLayoutColumn(ddmFormLayoutColumn);

		ddmFormLayoutPage.addDDMFormLayoutRow(ddmFormLayoutRow);

		return ddmFormLayoutPage;
	}

	private static final Class<? extends Annotation>
		_DDM_FORM_FIELD_ANNOTATION =
			com.liferay.dynamic.data.mapping.annotations.DDMFormField.class;

	private final Class<?> _ddmFormFieldTypeSettings;
	private final DDMFormJSONSerializer _ddmFormJSONSerializer;
	private final DDMFormLayoutJSONSerializer _ddmFormLayoutJSONSerializer;
	private final JSONFactory _jsonFactory;

	private static class DDMFormFieldSetting
		implements Comparable<DDMFormFieldSetting> {

		public DDMFormFieldSetting(String name, String[] properties) {
			_name = name;

			for (String property : properties) {
				String propertyName = StringUtil.extractFirst(
					property, StringPool.EQUAL);
				String propertyValue = StringUtil.extractLast(
					property, StringPool.EQUAL);

				if (Validator.equals(propertyName, "setting.category")) {
					_category = propertyValue;
				}

				if (Validator.equals(propertyName, "setting.weight")) {
					_weight = Integer.valueOf(propertyValue);
				}
			}
		}

		@Override
		public int compareTo(DDMFormFieldSetting ddmFormFieldSetting) {
			return -(Integer.compare(_weight, ddmFormFieldSetting._weight));
		}

		public String getCategory() {
			return _category;
		}

		public String getName() {
			return _name;
		}

		public void setCategory(String category) {
			_category = category;
		}

		public void setWeight(int weight) {
			_weight = weight;
		}

		private String _category = "advanced";
		private final String _name;
		private int _weight;

	}

}