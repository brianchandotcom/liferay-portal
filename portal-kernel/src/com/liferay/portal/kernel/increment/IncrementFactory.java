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