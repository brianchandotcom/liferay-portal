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

	public NestedFieldsSupplier() {
		_nestedFieldsContext =
			NestedFieldsContextThreadLocal.getNestedFieldsContext();
	}

	public <T> T supply(
			String fieldName,
			UnsafeFunction<String, T, Exception> unsafeFunction)
		throws Exception {

		if (!_mustProcessNestedFields(_nestedFieldsContext)) {
			return null;
		}

		List<String> fieldNames = _nestedFieldsContext.getFieldNames();

		if (!fieldNames.contains(fieldName)) {
			return null;
		}

		_nestedFieldsContext.incrementCurrentDepth();

		try {
			return unsafeFunction.apply(fieldName);
		}
		finally {
			_nestedFieldsContext.decrementCurrentDepth();
		}
	}

	public <T> Map<String, T> supply(
			UnsafeFunction<String, T, Exception> unsafeFunction)
		throws Exception {

		if (!_mustProcessNestedFields(_nestedFieldsContext)) {
			return null;
		}

		Map<String, T> nestedFields = new HashMap<>();

		_nestedFieldsContext.incrementCurrentDepth();

		for (String nestedFieldName : _nestedFieldsContext.getFieldNames()) {
			T value = unsafeFunction.apply(nestedFieldName);

			if (value != null) {
				nestedFields.put(nestedFieldName, value);
			}
		}

		_nestedFieldsContext.decrementCurrentDepth();

		return nestedFields;
	}

	private boolean _mustProcessNestedFields(
		NestedFieldsContext nestedFieldsContext) {

		if ((nestedFieldsContext != null) &&
			(nestedFieldsContext.getCurrentDepth() <
				nestedFieldsContext.getDepth()) &&
			ListUtil.isNotEmpty(nestedFieldsContext.getFieldNames())) {

			return true;
		}

		return false;
	}

	private final NestedFieldsContext _nestedFieldsContext;

}