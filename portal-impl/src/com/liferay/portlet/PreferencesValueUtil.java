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

package com.liferay.portlet;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.StringUtil;

/**
 * @author Tina Tian
 */
public class PreferencesValueUtil {

	public static String getActualValue(String value) {
		if ((value == null) || value.equals(_NULL_VALUE)) {
			return null;
		}

		return _fromCompactSafe(value);
	}

	public static String[] getActualValues(String[] values) {
		if (values == null) {
			return null;
		}

		if (values.length == 1) {
			String actualValue = getActualValue(values[0]);

			if (actualValue == null) {
				return null;
			}
			else if (actualValue.equals(_NULL_ELEMENT)) {
				return new String[] {null};
			}
			else {
				return new String[] {actualValue};
			}
		}

		String[] actualValues = new String[values.length];

		for (int i = 0; i < actualValues.length; i++) {
			actualValues[i] = getActualValue(values[i]);
		}

		return actualValues;
	}

	public static String getXMLSafeValue(String value) {
		if (value == null) {
			return _NULL_VALUE;
		}

		return toCompactSafe(value);
	}

	public static String[] getXMLSafeValues(String[] values) {
		if (values == null) {
			return new String[] {_NULL_VALUE};
		}

		if ((values.length == 1) && (values[0] == null)) {
			return new String[] {_NULL_ELEMENT};
		}

		String[] xmlSafeValues = new String[values.length];

		for (int i = 0; i < xmlSafeValues.length; i++) {
			xmlSafeValues[i] = getXMLSafeValue(values[i]);
		}

		return xmlSafeValues;
	}

	public static boolean isNull(String[] values) {
		if (ArrayUtil.isEmpty(values) ||
			((values.length == 1) && (getActualValue(values[0]) == null))) {

			return true;
		}

		return false;
	}

	public static String toCompactSafe(String xml) {
		return StringUtil.replace(
			xml,
			new String[] {
				StringPool.RETURN_NEW_LINE, StringPool.NEW_LINE,
				StringPool.RETURN
			},
			new String[] {"[$NEW_LINE$]", "[$NEW_LINE$]", "[$NEW_LINE$]"});
	}

	private static String _fromCompactSafe(String xml) {
		return StringUtil.replace(xml, "[$NEW_LINE$]", StringPool.NEW_LINE);
	}

	private static final String _NULL_ELEMENT = "NULL_ELEMENT";

	private static final String _NULL_VALUE = "NULL_VALUE";

}