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

package com.liferay.oauth2.provider.rest.internal.jaxrs.feature;

import com.liferay.oauth2.provider.scope.RequiresScope;
import com.liferay.petra.reflect.AnnotationLocator;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Method;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.ws.rs.HttpMethod;
import javax.ws.rs.Path;

/**
 * @author Carlos Sierra Andrés
 */
public class RequiresScopeAnnotationFinder {

	public static Collection<String> find(Class<?> clazz) {
		Set<String> scopes = new HashSet<>();

		_find(new HashSet<>(), scopes, true, clazz);

		return scopes;
	}

	public static <T extends Annotation> T getScopeAnnotation(
		AnnotatedElement annotatedElement, Class<T> annotationClass) {

		T t = annotatedElement.getAnnotation(annotationClass);

		if (t != null) {
			return t;
		}

		List<Annotation> annotations = Collections.emptyList();

		if (annotatedElement instanceof Class<?>) {
			annotations = AnnotationLocator.locate((Class<?>)annotatedElement);
		}
		else if (annotatedElement instanceof Method) {
			annotations = AnnotationLocator.locate(
				(Method)annotatedElement, null);
		}

		for (Annotation annotation : annotations) {
			Class<? extends Annotation> curAnnotationClass =
				annotation.annotationType();

			if (curAnnotationClass.equals(annotationClass)) {
				return (T)annotation;
			}

			t = curAnnotationClass.getAnnotation(annotationClass);

			if (t != null) {
				return t;
			}
		}

		return null;
	}

	private static void _find(
		Set<Class<?>> classes, Set<String> scopes, boolean recurse,
		Class<?> clazz) {

		RequiresScope requiresScope = getScopeAnnotation(
			clazz, RequiresScope.class);

		if (requiresScope != null) {
			Collections.addAll(scopes, requiresScope.value());
		}

		Stream<Method> stream = Arrays.stream(clazz.getMethods());

		List<Method> methods = stream.filter(
			RequiresScopeAnnotationFinder::_isAnnotatedMethod
		).collect(
			Collectors.toList()
		);

		for (Method method : methods) {
			_find(classes, scopes, recurse, method);
		}

		classes.remove(clazz);
	}

	private static void _find(
		Set<Class<?>> visited, Set<String> scopes, boolean recurse,
		Method method) {

		RequiresScope requiresScope = getScopeAnnotation(
			method, RequiresScope.class);

		if (requiresScope != null) {
			Collections.addAll(scopes, requiresScope.value());
		}

		if (_isSubresourceLocator(method) && recurse) {
			_find(visited, scopes, false, method.getReturnType());
		}
	}

	private static boolean _isAnnotatedMethod(Method method) {
		List<Annotation> annotations = AnnotationLocator.locate(
			method, Path.class);

		if (!annotations.isEmpty() || _isHttpMethod(method)) {
			return true;
		}

		return false;
	}

	private static boolean _isHttpMethod(Method method) {
		List<Annotation> annotations = AnnotationLocator.locate(method, null);

		for (Annotation annotation : annotations) {
			Class<? extends Annotation> annotationType =
				annotation.annotationType();

			if (annotationType.getAnnotationsByType(HttpMethod.class) != null) {
				return true;
			}
		}

		return false;
	}

	private static boolean _isSubresourceLocator(Method method) {
		if ((method.getDeclaredAnnotation(Path.class) != null) ||
			!_isHttpMethod(method)) {

			return true;
		}

		return false;
	}

}