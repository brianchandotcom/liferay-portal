/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.increment;

import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.util.ClassUtil;

import java.lang.reflect.Constructor;

/**
 * @author Shuyang Zhou
 */
public class IncrementFactory {

	@SuppressWarnings("rawtypes")
	public static Increment createIncrement(
		Class<? extends Increment<?>> counterClass, Object value) {

		if ((counterClass == NumberIncrement.class) &&
			(value instanceof Number)) {

			return new NumberIncrement((Number)value);
		}

		try {
			Class<?> valueClass = value.getClass();

			do {
				try {
					Constructor<? extends Increment<?>> constructor =
						counterClass.getConstructor(valueClass);

					return constructor.newInstance(value);
				}
				catch (NoSuchMethodException noSuchMethodException) {
					valueClass = valueClass.getSuperclass();

					if (valueClass.equals(Object.class)) {
						throw new SystemException(
							counterClass.getName() +
								" is unable to increment " +
									ClassUtil.getClassName(value),
							noSuchMethodException);
					}
				}
			}
			while (true);
		}
		catch (Exception exception) {
			throw new SystemException(exception);
		}
	}

}