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

package com.liferay.apio.architect.wiring.osgi.internal.manager;

import com.liferay.apio.architect.error.ApioDeveloperError.MustHaveValidGenericType;
import com.liferay.apio.architect.functional.Try;
import com.liferay.apio.architect.wiring.osgi.util.GenericUtil;

/**
 * Util class for managers.
 *
 * @author Alejandro Hernández
 * @review
 */
public class ManagerUtil {

	/**
	 * Return a type param from a generic interface of the class of an object at
	 * a certain position or fail.
	 *
	 * @param  t the object
	 * @param  interfaceClass the interface class to look for
	 * @param  position the type param position
	 * @return the type param
	 * @review
	 */
	public static <T, U> Class<U> getTypeParamOrFail(
		T t, Class<?> interfaceClass, Integer position) {

		Class<?> clazz = t.getClass();

		Try<Class<U>> classTry = GenericUtil.getGenericTypeArgumentTry(
			clazz, interfaceClass, position);

		return classTry.orElseThrow(() -> new MustHaveValidGenericType(clazz));
	}

}