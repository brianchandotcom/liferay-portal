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

package com.liferay.configuration.admin.web.util;

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
			Object paramValue = null;

			if (attributeDefinition.getCardinality() == 0) {
				paramValue = _toSimpleValue(
					ddmFormFieldValuesMap, attributeDefinition.getID(),
					attributeDefinition.getType(), locale);
			}
			else if (attributeDefinition.getCardinality() > 0) {
				paramValue = _toArrayValue(
					ddmFormFieldValuesMap, attributeDefinition.getID(),
					attributeDefinition.getType(), locale);
			}
			else if (attributeDefinition.getCardinality() < 0) {
				paramValue = _toVectorValue(
					ddmFormFieldValuesMap, attributeDefinition.getID(),
					attributeDefinition.getType(), locale);
			}

			properties.put(attributeDefinition.getID(), paramValue);
		}

		return properties;
	}

	private static Object _toArrayValue(
		Map<String, List<DDMFormFieldValue>> ddmFormFieldValuesMap, String id,
		int type, Locale locale) {

		List<DDMFormFieldValue> ddmFormFieldValues = ddmFormFieldValuesMap.get(
			id);

		if (type == AttributeDefinition.BOOLEAN) {
			boolean[] values = new boolean[ddmFormFieldValues.size()];

			for (int i = 0; i < values.length; i++) {
				DDMFormFieldValue ddmFormFieldValue = ddmFormFieldValues.get(i);

				Value value = ddmFormFieldValue.getValue();

				values[i] = GetterUtil.getBoolean(value.getString(locale));
			}

			return values;
		}
		else if (type == AttributeDefinition.DOUBLE) {
			double[] values = new double[ddmFormFieldValues.size()];

			for (int i = 0; i < values.length; i++) {
				DDMFormFieldValue ddmFormFieldValue = ddmFormFieldValues.get(i);

				Value value = ddmFormFieldValue.getValue();

				values[i] = GetterUtil.getDouble(value.getString(locale));
			}

			return values;
		}
		else if (type == AttributeDefinition.FLOAT) {
			float[] values = new float[ddmFormFieldValues.size()];

			for (int i = 0; i < values.length; i++) {
				DDMFormFieldValue ddmFormFieldValue = ddmFormFieldValues.get(i);

				Value value = ddmFormFieldValue.getValue();

				values[i] = GetterUtil.getFloat(value.getString(locale));
			}

			return values;
		}
		else if (type == AttributeDefinition.INTEGER) {
			int[] values = new int[ddmFormFieldValues.size()];

			for (int i = 0; i < values.length; i++) {
				DDMFormFieldValue ddmFormFieldValue = ddmFormFieldValues.get(i);

				Value value = ddmFormFieldValue.getValue();

				values[i] = GetterUtil.getInteger(value.getString(locale));
			}

			return values;
		}
		else if (type == AttributeDefinition.LONG) {
			long[] values = new long[ddmFormFieldValues.size()];

			for (int i = 0; i < values.length; i++) {
				DDMFormFieldValue ddmFormFieldValue = ddmFormFieldValues.get(i);

				Value value = ddmFormFieldValue.getValue();

				values[i] = GetterUtil.getLong(value.getString(locale));
			}

			return values;
		}

		String[] values = new String[ddmFormFieldValues.size()];

		for (int i = 0; i < values.length; i++) {
			DDMFormFieldValue ddmFormFieldValue = ddmFormFieldValues.get(i);

			Value value = ddmFormFieldValue.getValue();

			values[i] = GetterUtil.getString(value.getString(locale));
		}

		return values;
	}

	private static Object _toSimpleValue(
		Map<String, List<DDMFormFieldValue>> ddmFormFieldValuesMap, String id,
		int type, Locale locale) {

		List<DDMFormFieldValue> ddFormFieldValues = ddmFormFieldValuesMap.get(
			id);

		DDMFormFieldValue ddmFormFieldValue = ddFormFieldValues.get(0);

		Value value = ddmFormFieldValue.getValue();

		if (type == AttributeDefinition.BOOLEAN) {
			return GetterUtil.getBoolean(value.getString(locale));
		}
		else if (type == AttributeDefinition.DOUBLE) {
			return GetterUtil.getDouble(value.getString(locale));
		}
		else if (type == AttributeDefinition.FLOAT) {
			return GetterUtil.getFloat(value.getString(locale));
		}
		else if (type == AttributeDefinition.INTEGER) {
			return GetterUtil.getBoolean(value.getString(locale));
		}
		else if (type == AttributeDefinition.LONG) {
			return GetterUtil.getLong(value.getString(locale));
		}

		return GetterUtil.getString(value.getString(locale));
	}

	private static Object _toVectorValue(
		Map<String, List<DDMFormFieldValue>> ddmFormFieldValuesMap, String id,
		int type, Locale locale) {

		List<DDMFormFieldValue> ddFormFieldValues = ddmFormFieldValuesMap.get(
			id);

		if (type == AttributeDefinition.BOOLEAN) {
			Vector<Boolean> values = new Vector<>();

			for (DDMFormFieldValue ddmFormFieldValue : ddFormFieldValues) {
				Value value = ddmFormFieldValue.getValue();

				values.add(GetterUtil.getBoolean(value.getString(locale)));
			}

			return values;
		}
		else if (type == AttributeDefinition.DOUBLE) {
			Vector<Double> values = new Vector<>();

			for (DDMFormFieldValue ddmFormFieldValue : ddFormFieldValues) {
				Value value = ddmFormFieldValue.getValue();

				values.add(GetterUtil.getDouble(value.getString(locale)));
			}

			return values;
		}
		else if (type == AttributeDefinition.FLOAT) {
			Vector<Float> values = new Vector<>();

			for (DDMFormFieldValue ddmFormFieldValue : ddFormFieldValues) {
				Value value = ddmFormFieldValue.getValue();

				values.add(GetterUtil.getFloat(value.getString(locale)));
			}

			return values;
		}
		else if (type == AttributeDefinition.INTEGER) {
			Vector<Integer> values = new Vector<>();

			for (DDMFormFieldValue ddmFormFieldValue : ddFormFieldValues) {
				Value value = ddmFormFieldValue.getValue();

				values.add(GetterUtil.getInteger(value.getString(locale)));
			}

			return values;
		}
		else if (type == AttributeDefinition.LONG) {
			Vector<Long> values = new Vector<>();

			for (DDMFormFieldValue ddmFormFieldValue : ddFormFieldValues) {
				Value value = ddmFormFieldValue.getValue();

				values.add(GetterUtil.getLong(value.getString(locale)));
			}

			return values;
		}

		Vector<String> values = new Vector<>();

		for (DDMFormFieldValue ddmFormFieldValue : ddFormFieldValues) {
			Value value = ddmFormFieldValue.getValue();

			values.add(GetterUtil.getString(value.getString(locale)));
		}

		return values;
	}

}