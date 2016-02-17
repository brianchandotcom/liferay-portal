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

package com.liferay.dynamic.data.mapping.form.values.query.internal.model;

import com.liferay.dynamic.data.mapping.storage.DDMFormFieldValue;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Pablo Carvalho
 */
public class DDMFormFieldValueMatchesAllMatcher
	implements DDMFormFieldValueMatcher {

	public void addDDMFormFieldValueMatcher(
		DDMFormFieldValueMatcher ddmFormFieldValueMatcher) {

		_ddmFormFieldValueMatchers.add(ddmFormFieldValueMatcher);
	}

	@Override
	public boolean matches(DDMFormFieldValue ddmFormFieldValue) {
		for (DDMFormFieldValueMatcher ddmFormFieldValueMatcher :
				_ddmFormFieldValueMatchers) {

			if (!ddmFormFieldValueMatcher.matches(ddmFormFieldValue)) {
				return false;
			}
		}

		return true;
	}

	private final List<DDMFormFieldValueMatcher> _ddmFormFieldValueMatchers =
		new ArrayList<>();

}