/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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

		Map<String, Field> extendedEntityFieldsMap = new HashMap<>();

		Class<?> extendedEntityClassSuperclass =
			extendedEntityClass.getSuperclass();

		Field[] extendedEntityFields =
			extendedEntityClassSuperclass.getDeclaredFields();

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