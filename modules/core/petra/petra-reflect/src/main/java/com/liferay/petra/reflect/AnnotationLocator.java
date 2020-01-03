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

package com.liferay.petra.reflect;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

/**
 * @author Shuyang Zhou
 */
public class AnnotationLocator {

	public static Map<Class<? extends Annotation>, Annotation> index(
		Class<?> targetClass) {

		Queue<Class<?>> queue = new LinkedList<>();

		queue.offer(targetClass);

		Map<Class<? extends Annotation>, Annotation> indexMap =
			new LinkedHashMap<>();

		Class<?> clazz = null;

		while ((clazz = queue.poll()) != null) {
			_mergeAnnotations(clazz.getAnnotations(), indexMap);

			_queueSuperTypes(queue, clazz);
		}

		return indexMap;
	}

	public static Map<Class<? extends Annotation>, Annotation> index(
		Method method, Class<?> targetClass) {

		Queue<Class<?>> queue = new LinkedList<>();

		if (targetClass == null) {
			queue.offer(method.getDeclaringClass());
		}
		else {
			queue.offer(targetClass);
		}

		Map<Class<? extends Annotation>, Annotation> indexMap =
			new LinkedHashMap<>();

		Class<?> clazz = null;

		while ((clazz = queue.poll()) != null) {
			try {
				Method specificMethod = clazz.getDeclaredMethod(
					method.getName(), method.getParameterTypes());

				_mergeAnnotations(specificMethod.getAnnotations(), indexMap);
			}
			catch (Exception e) {
			}

			try {

				// Ensure the class has a publicly inherited method

				clazz.getMethod(method.getName(), method.getParameterTypes());

				_mergeAnnotations(clazz.getAnnotations(), indexMap);
			}
			catch (Exception e) {
			}

			_queueSuperTypes(queue, clazz);
		}

		return indexMap;
	}

	public static List<Annotation> locate(Class<?> targetClass) {
		Map<Class<? extends Annotation>, Annotation> indexMap = index(
			targetClass);

		return new ArrayList<>(indexMap.values());
	}

	public static <T extends Annotation> T locate(
		Class<?> targetClass, Class<T> annotationClass) {

		Queue<Class<?>> queue = new LinkedList<>();

		queue.offer(targetClass);

		Class<?> clazz = null;

		while ((clazz = queue.poll()) != null) {
			T annotation = clazz.getAnnotation(annotationClass);

			if (annotation == null) {
				_queueSuperTypes(queue, clazz);
			}
			else {
				return annotation;
			}
		}

		return null;
	}

	public static List<Annotation> locate(Method method, Class<?> targetClass) {
		Map<Class<? extends Annotation>, Annotation> indexMap = index(
			method, targetClass);

		return new ArrayList<>(indexMap.values());
	}

	public static <T extends Annotation> T locate(
		Method method, Class<?> targetClass, Class<T> annotationClass) {

		Queue<Class<?>> queue = new LinkedList<>();

		if (targetClass == null) {
			queue.offer(method.getDeclaringClass());
		}
		else {
			queue.offer(targetClass);
		}

		Class<?> clazz = null;

		while ((clazz = queue.poll()) != null) {
			T annotation = null;

			try {
				Method specificMethod = clazz.getDeclaredMethod(
					method.getName(), method.getParameterTypes());

				annotation = specificMethod.getAnnotation(annotationClass);

				if (annotation != null) {
					return annotation;
				}
			}
			catch (Exception e) {
			}

			try {

				// Ensure the class has a publicly inherited method

				clazz.getMethod(method.getName(), method.getParameterTypes());

				annotation = clazz.getAnnotation(annotationClass);
			}
			catch (Exception e) {
			}

			if (annotation == null) {
				_queueSuperTypes(queue, clazz);
			}
			else {
				return annotation;
			}
		}

		return null;
	}

	private static void _mergeAnnotations(
		Annotation[] sourceAnnotations,
		Map<Class<? extends Annotation>, Annotation> indexMap) {

		for (Annotation sourceAnnotation : sourceAnnotations) {
			indexMap.putIfAbsent(
				sourceAnnotation.annotationType(), sourceAnnotation);
		}
	}

	private static void _queueSuperTypes(
		Queue<Class<?>> queue, Class<?> clazz) {

		Class<?> superClass = clazz.getSuperclass();

		if ((superClass != null) && (superClass != Object.class)) {
			queue.offer(superClass);
		}

		Class<?>[] interfaceClasses = clazz.getInterfaces();

		for (Class<?> interfaceClass : interfaceClasses) {
			if (!queue.contains(interfaceClass)) {
				queue.offer(interfaceClass);
			}
		}
	}

}