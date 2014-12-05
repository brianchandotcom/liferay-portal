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

package com.liferay.config.admin.web.util;

import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portlet.dynamicdatamapping.model.Value;
import com.liferay.portlet.dynamicdatamapping.storage.DDMFormFieldValue;

import java.util.Dictionary;
import java.util.Hashtable;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Vector;

import org.osgi.service.metatype.AttributeDefinition;
import org.osgi.service.metatype.ObjectClassDefinition;

/**
 * @author Kamesh Sampath
 * @author Raymond Augé
 */
public class ConfigurationProperties {

	public static Dictionary<String, Object> load(
		ObjectClassDefinition objectClassDefinition,
		Map<String, List<DDMFormFieldValue>> ddmFormFieldValuesMap,
		Locale locale) {

		Dictionary<String, Object> properties = new Hashtable<String, Object>();

		AttributeDefinition[] attributeDefinitions =
			objectClassDefinition.getAttributeDefinitions(
				ObjectClassDefinition.ALL);

		for (AttributeDefinition attributeDefinition : attributeDefinitions) {
			String id = attributeDefinition.getID();
			int type = attributeDefinition.getType();
			int cardinality = attributeDefinition.getCardinality();

			Object paramValue = null;

			if (cardinality == 0) {
				paramValue = typedParamValue(
					ddmFormFieldValuesMap, id, type, locale);
			}
			else if (cardinality > 0) {
				paramValue = typedParamArray(
					ddmFormFieldValuesMap, id, type, locale);
			}
			else if (cardinality < 0) {
				paramValue = typedParamVector(
					ddmFormFieldValuesMap, id, type, locale);
			}

			properties.put(id, paramValue);
		}

		return properties;
	}

	private static Object typedParamArray(
		Map<String, List<DDMFormFieldValue>> ddmFormFieldValuesMap, String id,
		int type, Locale locale) {

		List<DDMFormFieldValue> list = ddmFormFieldValuesMap.get(id);

		switch(type) {
			case AttributeDefinition.BOOLEAN: {
				boolean[] values = new boolean[list.size()];

				for (int i = 0; i < values.length; i++) {
					DDMFormFieldValue ddmFormFieldValue = list.get(i);

					Value value = ddmFormFieldValue.getValue();

					values[i] = GetterUtil.getBoolean(value.getString(locale));
				}

				return values;
			}

			case AttributeDefinition.LONG: {
				long[] values = new long[list.size()];

				for (int i = 0; i < values.length; i++) {
					DDMFormFieldValue ddmFormFieldValue = list.get(i);

					Value value = ddmFormFieldValue.getValue();

					values[i] = GetterUtil.getLong(value.getString(locale));
				}

				return values;
			}

			case AttributeDefinition.DOUBLE: {
				double[] values = new double[list.size()];

				for (int i = 0; i < values.length; i++) {
					DDMFormFieldValue ddmFormFieldValue = list.get(i);

					Value value = ddmFormFieldValue.getValue();

					values[i] = GetterUtil.getDouble(value.getString(locale));
				}

				return values;
			}

			case AttributeDefinition.FLOAT: {
				float[] values = new float[list.size()];

				for (int i = 0; i < values.length; i++) {
					DDMFormFieldValue ddmFormFieldValue = list.get(i);

					Value value = ddmFormFieldValue.getValue();

					values[i] = GetterUtil.getFloat(value.getString(locale));
				}

				return values;
			}

			case AttributeDefinition.INTEGER: {
				int[] values = new int[list.size()];

				for (int i = 0; i < values.length; i++) {
					DDMFormFieldValue ddmFormFieldValue = list.get(i);

					Value value = ddmFormFieldValue.getValue();

					values[i] = GetterUtil.getInteger(value.getString(locale));
				}

				return values;
			}

			default: {
				String[] values = new String[list.size()];

				for (int i = 0; i < values.length; i++) {
					DDMFormFieldValue ddmFormFieldValue = list.get(i);

					Value value = ddmFormFieldValue.getValue();

					values[i] = GetterUtil.getString(value.getString(locale));
				}

				return values;
			}
		}
	}

	private static Object typedParamValue(
		Map<String, List<DDMFormFieldValue>> ddmFormFieldValuesMap, String id,
		int type, Locale locale) {

		List<DDMFormFieldValue> list = ddmFormFieldValuesMap.get(id);

		DDMFormFieldValue ddmFormFieldValue = list.get(0);

		Value value = ddmFormFieldValue.getValue();

		switch(type) {
			case AttributeDefinition.BOOLEAN: {
				return GetterUtil.getBoolean(value.getString(locale));
			}

			case AttributeDefinition.LONG: {
				return GetterUtil.getLong(value.getString(locale));
			}

			case AttributeDefinition.DOUBLE: {
				return GetterUtil.getDouble(value.getString(locale));
			}

			case AttributeDefinition.FLOAT: {
				return GetterUtil.getFloat(value.getString(locale));
			}

			case AttributeDefinition.INTEGER: {
				return GetterUtil.getBoolean(value.getString(locale));
			}

			default: {
				return GetterUtil.getString(value.getString(locale));
			}
		}
	}

	private static Object typedParamVector(
		Map<String, List<DDMFormFieldValue>> ddmFormFieldValuesMap, String id,
		int type, Locale locale) {

		List<DDMFormFieldValue> list = ddmFormFieldValuesMap.get(id);

		switch(type) {
			case AttributeDefinition.BOOLEAN: {
				Vector<Boolean> values = new Vector<>();

				for (DDMFormFieldValue ddmFormFieldValue : list) {
					Value value = ddmFormFieldValue.getValue();

					values.add(GetterUtil.getBoolean(value.getString(locale)));
				}

				return values;
			}

			case AttributeDefinition.LONG: {
				Vector<Long> values = new Vector<>();

				for (DDMFormFieldValue ddmFormFieldValue : list) {
					Value value = ddmFormFieldValue.getValue();

					values.add(GetterUtil.getLong(value.getString(locale)));
				}

				return values;
			}

			case AttributeDefinition.DOUBLE: {
				Vector<Double> values = new Vector<>();

				for (DDMFormFieldValue ddmFormFieldValue : list) {
					Value value = ddmFormFieldValue.getValue();

					values.add(GetterUtil.getDouble(value.getString(locale)));
				}

				return values;
			}

			case AttributeDefinition.FLOAT: {
				Vector<Float> values = new Vector<>();

				for (DDMFormFieldValue ddmFormFieldValue : list) {
					Value value = ddmFormFieldValue.getValue();

					values.add(GetterUtil.getFloat(value.getString(locale)));
				}

				return values;
			}

			case AttributeDefinition.INTEGER: {
				Vector<Integer> values = new Vector<>();

				for (DDMFormFieldValue ddmFormFieldValue : list) {
					Value value = ddmFormFieldValue.getValue();

					values.add(GetterUtil.getInteger(value.getString(locale)));
				}

				return values;
			}

			default: {
				Vector<String> values = new Vector<>();

				for (DDMFormFieldValue ddmFormFieldValue : list) {
					Value value = ddmFormFieldValue.getValue();

					values.add(GetterUtil.getString(value.getString(locale)));
				}

				return values;
			}
		}
	}

}