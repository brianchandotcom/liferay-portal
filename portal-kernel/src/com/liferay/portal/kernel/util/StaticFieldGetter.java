/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
		return _staticFieldGetter;
	}

	public Object getFieldValue(String className, String fieldName) {
		Object object = null;

		try {
			Class<?> objClass = Class.forName(className);

			Field field = objClass.getField(fieldName);

			object = field.get(objClass);
		}
		catch (Exception exception) {
			_log.error("Unable to access static field", exception);
		}

		return object;
	}

	private StaticFieldGetter() {
	}

	private static final Log _log = LogFactoryUtil.getLog(
		StaticFieldGetter.class);

	private static final StaticFieldGetter _staticFieldGetter =
		new StaticFieldGetter();

}