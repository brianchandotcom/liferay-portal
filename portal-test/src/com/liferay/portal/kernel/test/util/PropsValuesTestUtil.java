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

package com.liferay.portal.kernel.test.util;

import com.liferay.petra.lang.SafeCloseable;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.util.PropsUtil;
import com.liferay.portal.util.PropsValues;

/**
 * @author Shuyang Zhou
 */
public class PropsValuesTestUtil {

	public static SafeCloseable swapWithSafeCloseable(
		String propsKeysFieldName, Object value) {

		String propsKeysName = ReflectionTestUtil.getFieldValue(
			PropsKeys.class, propsKeysFieldName);

		String originalPropsValue = PropsUtil.get(propsKeysName);

		PropsUtil.set(propsKeysName, String.valueOf(value));

		Object originalValue = ReflectionTestUtil.getAndSetFieldValue(
			PropsValues.class, propsKeysFieldName, value);

		return () -> {
			PropsUtil.set(propsKeysName, originalPropsValue);

			ReflectionTestUtil.setFieldValue(
				PropsValues.class, propsKeysFieldName, originalValue);
		};
	}

}