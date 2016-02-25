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
			Constructor<? extends Increment<?>> constructor =
				counterClass.getConstructor(value.getClass());

			return constructor.newInstance(value);
		}
		catch (Exception e) {
			throw new SystemException(e);
		}
	}

}