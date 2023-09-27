/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.vulcan.fields;

import com.liferay.petra.function.UnsafeFunction;
import com.liferay.portal.kernel.util.ListUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Carlos Correa
 */
public class NestedFieldsSupplier<T> {

	public static void addFieldName(String fieldName) {
		NestedFieldsContext nestedFieldsContext =
			NestedFieldsContextThreadLocal.getNestedFieldsContext();

		if (nestedFieldsContext != null) {
			nestedFieldsContext.addFieldName(fieldName);
		}
	}

	public static <T> T supply(
			String fieldName,
			UnsafeFunction<String, T, Exception> unsafeFunction)
		throws Exception {

		NestedFieldsContext nestedFieldsContext =
			NestedFieldsContextThreadLocal.getNestedFieldsContext();

		if (!_mustProcessNestedFields(nestedFieldsContext)) {
			return null;
		}

		List<String> fieldNames = nestedFieldsContext.getFieldNames();

		if (!fieldNames.contains(fieldName)) {
			return null;
		}

		nestedFieldsContext.incrementCurrentDepth();

		try {
			return unsafeFunction.apply(fieldName);
		}
		finally {
			nestedFieldsContext.decrementCurrentDepth();
		}
	}

	public static <T> Map<String, T> supply(
			UnsafeFunction<String, T, Exception> unsafeFunction)
		throws Exception {

		NestedFieldsContext nestedFieldsContext =
			NestedFieldsContextThreadLocal.getNestedFieldsContext();

		if (!_mustProcessNestedFields(nestedFieldsContext)) {
			return null;
		}

		Map<String, T> nestedFieldValues = new HashMap<>();

		nestedFieldsContext.incrementCurrentDepth();

		for (String fieldName : nestedFieldsContext.getFieldNames()) {
			T value = unsafeFunction.apply(fieldName);

			if (value != null) {
				nestedFieldValues.put(fieldName, value);
			}
		}

		nestedFieldsContext.decrementCurrentDepth();

		return nestedFieldValues;
	}

	private static boolean _mustProcessNestedFields(
		NestedFieldsContext nestedFieldsContext) {

		if ((nestedFieldsContext != null) &&
			(nestedFieldsContext.getCurrentDepth() <
				nestedFieldsContext.getDepth()) &&
			ListUtil.isNotEmpty(nestedFieldsContext.getFieldNames())) {

			return true;
		}

		return false;
	}

}