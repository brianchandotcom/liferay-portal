/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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