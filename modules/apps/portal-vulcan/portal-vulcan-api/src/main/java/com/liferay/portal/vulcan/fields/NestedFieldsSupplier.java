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