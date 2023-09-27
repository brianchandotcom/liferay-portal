/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.configuration.admin.web.internal.util;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.StringUtil;

import java.util.Dictionary;
import java.util.Vector;

import org.osgi.service.cm.Configuration;
import org.osgi.service.metatype.AttributeDefinition;

/**
 * @author André de Oliveira
 */
public class AttributeDefinitionUtil {

	public static String[] getDefaultValue(
		AttributeDefinition attributeDefinition) {

		String[] defaultValues = attributeDefinition.getDefaultValue();

		if (ArrayUtil.isEmpty(defaultValues)) {
			return new String[] {StringPool.BLANK};
		}

		if ((attributeDefinition.getCardinality() == 0) ||
			(defaultValues.length > 1)) {

			return defaultValues;
		}

		return StringUtil.split(defaultValues[0], StringPool.PIPE);
	}

	public static Object getPropertyObject(
		AttributeDefinition attributeDefinition, Configuration configuration) {

		Dictionary<String, Object> properties = configuration.getProperties();

		Object property = properties.get(attributeDefinition.getID());

		if (property == null) {
			return new String[0];
		}

		int cardinality = attributeDefinition.getCardinality();

		if (cardinality == 0) {
			return String.valueOf(property);
		}

		if (cardinality > 0) {
			if (property instanceof Object[]) {
				return ArrayUtil.toStringArray((Object[])property);
			}

			return String.valueOf(property);
		}

		Vector<?> vector = (Vector<?>)property;

		return ArrayUtil.toStringArray(vector.toArray());
	}

	public static String[] getPropertyStringArray(
		AttributeDefinition attributeDefinition, Configuration configuration) {

		Object value = getPropertyObject(attributeDefinition, configuration);

		if (value instanceof String[]) {
			return (String[])value;
		}

		return new String[] {String.valueOf(value)};
	}

}