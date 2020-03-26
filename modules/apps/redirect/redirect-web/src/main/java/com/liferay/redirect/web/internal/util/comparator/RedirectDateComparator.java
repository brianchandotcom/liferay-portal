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