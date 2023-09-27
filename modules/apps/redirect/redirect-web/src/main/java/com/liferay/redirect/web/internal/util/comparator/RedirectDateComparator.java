/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.redirect.web.internal.util.comparator;

import com.liferay.portal.kernel.model.BaseModel;
import com.liferay.portal.kernel.util.DateUtil;

import java.util.Date;
import java.util.function.Function;

/**
 * @author Alejandro Tardín
 */
public class RedirectDateComparator<T extends BaseModel>
	extends RedirectComparator<T, Date> {

	public RedirectDateComparator(
		String modelName, String fieldName,
		Function<T, Date> fieldValueFunction, boolean ascending) {

		super(modelName, fieldName, fieldValueFunction, ascending);
	}

	@Override
	public int compare(T baseModel1, T baseModel2) {
		int value = DateUtil.compareTo(
			getFieldValue(baseModel1), getFieldValue(baseModel2));

		if (isAscending()) {
			return value;
		}

		return -value;
	}

}