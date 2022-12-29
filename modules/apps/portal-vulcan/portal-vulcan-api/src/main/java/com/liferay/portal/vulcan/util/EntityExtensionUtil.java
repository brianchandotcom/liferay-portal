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

package com.liferay.portal.vulcan.util;

import com.liferay.petra.function.UnsafeConsumer;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Luis Miguel Barcos
 */
public class EntityExtensionUtil {

	public static <T, S extends T> S extend(
			T baseEntity, Class<T> baseEntityClass,
			Class<S> extendedEntityClass,
			UnsafeConsumer<S, ? extends Exception> unsafeConsumer)
		throws Exception {

		S extendedEntity = extendedEntityClass.newInstance();

		Class<?> extendedEntityClassSuperclass =
			extendedEntityClass.getSuperclass();

		Field[] extendedEntityFields =
			extendedEntityClassSuperclass.getDeclaredFields();

		Map<String, Field> extendedEntityFieldsMap = new HashMap<>();

		for (Field field : extendedEntityFields) {
			extendedEntityFieldsMap.put(field.getName(), field);
		}

		for (Field baseEntityField : baseEntityClass.getDeclaredFields()) {
			int modifiers = baseEntityField.getModifiers();

			if (Modifier.isFinal(modifiers) || Modifier.isStatic(modifiers)) {
				continue;
			}

			Field extendedEntityField = extendedEntityFieldsMap.get(
				baseEntityField.getName());

			baseEntityField.setAccessible(true);
			extendedEntityField.setAccessible(true);
			extendedEntityField.set(
				extendedEntity, baseEntityField.get(baseEntity));
		}

		unsafeConsumer.accept(extendedEntity);

		return extendedEntity;
	}

}