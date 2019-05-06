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

import java.lang.reflect.Field;

/**
 * @author     Shuyang Zhou
 * @deprecated As of Mueller (7.2.x), with no direct replacement
 */
@Deprecated
public class ReferenceRegistry {

	public static void registerReference(
		Class<?> clazz, Object object, String fieldName) {
	}

	public static void registerReference(Class<?> clazz, String fieldName) {
	}

	public static void registerReference(Field field) {
	}

	public static void registerReference(Object object, Field field) {
	}

	public static void releaseReferences() {
	}

	/**
	 * @deprecated As of Judson (7.1.x), with no direct replacement
	 */
	@Deprecated
	public interface PACL {

		public ReferenceEntry getReferenceEntry(
				Class<?> clazz, Object object, String fieldName)
			throws NoSuchFieldException, SecurityException;

	}

}