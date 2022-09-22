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

package com.liferay.batch.engine.internal.writer;

import com.liferay.object.rest.dto.v1_0.ListEntry;
import com.liferay.object.rest.dto.v1_0.ObjectEntry;
import com.liferay.petra.concurrent.ConcurrentReferenceKeyHashMap;
import com.liferay.petra.concurrent.ConcurrentReferenceValueHashMap;
import com.liferay.petra.memory.FinalizeManager;
import com.liferay.petra.string.CharPool;

import java.lang.reflect.Field;

import java.math.BigDecimal;
import java.math.BigInteger;

import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * @author Shuyang Zhou
 * @author Igor Beslic
 */
public class ItemClassIndexUtil {

	public static Map<String, Field> index(Class<?> itemClass) {
		return _fieldsMap.computeIfAbsent(
			itemClass,
			clazz -> {
				Map<String, Field> fieldsMap = new HashMap<>();

				while (clazz != Object.class) {
					for (Field field : clazz.getDeclaredFields()) {
						if (isMultidimensionalArray(field.getType())) {
							continue;
						}

						field.setAccessible(true);

						String name = field.getName();

						if (name.charAt(0) == CharPool.UNDERLINE) {
							name = name.substring(1);
						}

						if (field.isSynthetic()) {
							continue;
						}

						fieldsMap.put(name, field);

						Class<?> fieldClass = field.getType();

						if (!isIterable(fieldClass) && !isMap(fieldClass) &&
							!isSingleColumnAdoptableArray(fieldClass) &&
							!isSingleColumnAdoptableValue(fieldClass) &&
							!Objects.equals(clazz, fieldClass)) {

							index(fieldClass);
						}
					}

					if (Objects.equals(
							clazz.getSuperclass(), clazz.getDeclaringClass())) {

						break;
					}

					clazz = clazz.getSuperclass();
				}

				return fieldsMap;
			});
	}

	public static boolean isIterable(Class<?> valueClass) {
		if (valueClass.isArray() ||
			Collection.class.isAssignableFrom(valueClass)) {

			return true;
		}

		return false;
	}

	public static boolean isListEntry(Object object) {
		if (object instanceof ListEntry) {
			return true;
		}

		return false;
	}

	public static boolean isMap(Class<?> clazz) {
		if (Objects.equals(clazz, Map.class)) {
			return true;
		}

		return false;
	}

	public static boolean isMultidimensionalArray(Class<?> clazz) {
		if (!clazz.isArray()) {
			return false;
		}

		Class<?> componentTypeClass = clazz.getComponentType();

		if (!componentTypeClass.isArray()) {
			return false;
		}

		return true;
	}

	public static boolean isObjectEntryProperties(Field field) {
		if ((field == null) ||
			!Objects.equals(field.getDeclaringClass(), ObjectEntry.class) ||
			!Objects.equals(field.getType(), Map.class)) {

			return false;
		}

		return true;
	}

	public static boolean isSingleColumnAdoptableArray(Class<?> clazz) {
		if (!clazz.isArray()) {
			return false;
		}

		if (isSingleColumnAdoptableValue(clazz.getComponentType())) {
			return true;
		}

		return false;
	}

	public static boolean isSingleColumnAdoptableValue(Class<?> clazz) {
		if (!clazz.isPrimitive() && !_objectTypes.contains(clazz) &&
			!Enum.class.isAssignableFrom(clazz)) {

			return false;
		}

		return true;
	}

	private static final Map<Class<?>, Map<String, Field>> _fieldsMap =
		new ConcurrentReferenceKeyHashMap<>(
			new ConcurrentReferenceValueHashMap<>(
				FinalizeManager.WEAK_REFERENCE_FACTORY),
			FinalizeManager.WEAK_REFERENCE_FACTORY);
	private static final List<Class<?>> _objectTypes = Arrays.asList(
		Boolean.class, BigDecimal.class, BigInteger.class, Byte.class,
		Date.class, Double.class, Float.class, Integer.class, Long.class,
		String.class);

}