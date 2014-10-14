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

package com.liferay.portal.kernel.util;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

import java.lang.reflect.Field;

/**
 * @author Brian Wing Shun Chan
 */
public class StaticFieldGetter {

	public static StaticFieldGetter getInstance() {
		return _instance;
	}

	public Object getFieldValue(String className, String fieldName) {
		Object obj = null;

		try {
			Class<?> objClass = Class.forName(className);

			Field field = objClass.getField(fieldName);

			obj = field.get(objClass);
		}
		catch (Exception e) {
			_log.error(e);
		}

		return obj;
	}

	private StaticFieldGetter() {
	}

	private static final Log _log = LogFactoryUtil.getLog(
		StaticFieldGetter.class);

	private static final StaticFieldGetter _instance = new StaticFieldGetter();

}