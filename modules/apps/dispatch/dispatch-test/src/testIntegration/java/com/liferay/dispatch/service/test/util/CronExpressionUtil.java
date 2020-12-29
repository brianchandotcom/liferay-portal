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

package com.liferay.dispatch.service.test.util;

import com.liferay.portal.kernel.test.util.RandomTestUtil;

import java.util.Calendar;

/**
 * @author Igor Beslic
 */
public class CronExpressionUtil {

	public static String getCronExpression() {
		return String.format("0 0 0 ? %d/2 * %d", _MONTH, _YEAR);
	}

	public static int getMonth() {
		return _MONTH;
	}

	public static int getYear() {
		return _YEAR;
	}

	private static final int _MONTH;

	private static final int _YEAR;

	static {
		_MONTH = RandomTestUtil.randomInt(1, 9);

		Calendar calendar = Calendar.getInstance();

		_YEAR = calendar.get(Calendar.YEAR) + 1;
	}

}