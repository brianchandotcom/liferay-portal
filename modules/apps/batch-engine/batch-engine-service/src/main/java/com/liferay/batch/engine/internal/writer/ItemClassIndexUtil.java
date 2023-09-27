/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Queue;

/**
 * @author Shuyang Zhou
 * @author Igor Beslic
 */
public class ItemClassIndexUtil {

	public static Map<String, Field> index(Class<?> itemClass) {
		Queue<Class<?>> queue = new LinkedList<>();

		Map<String, Field> fieldsMap = _fieldsMap.computeIfAbsent(
			itemClass, clazz -> _index(clazz, queue));

		while ((itemClass = queue.poll()) != null) {
			_fieldsMap.computeIfAbsent(
				itemClass, clazz -> _index(clazz, queue));
		}

		return fieldsMap;
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

	private static Map<String, Field> _index(
		Class<?> clazz, Queue<Class<?>> queue) {

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

					queue.add(clazz);
				}
			}

			if (Objects.equals(
					clazz.getSuperclass(), clazz.getDeclaringClass())) {

				break;
			}

			clazz = clazz.getSuperclass();
		}

		return fieldsMap;
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