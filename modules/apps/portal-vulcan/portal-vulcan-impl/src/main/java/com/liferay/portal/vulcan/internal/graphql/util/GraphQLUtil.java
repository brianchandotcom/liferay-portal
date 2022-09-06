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

package com.liferay.portal.vulcan.internal.graphql.util;

import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.vulcan.graphql.annotation.GraphQLField;
import com.liferay.portal.vulcan.graphql.annotation.GraphQLName;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Method;

/**
 * @author Carlos Correa
 */
public class GraphQLUtil {

	public static String getGraphQLNameValue(
		AnnotatedElement annotatedElement) {

		GraphQLName graphQLName = annotatedElement.getAnnotation(
			GraphQLName.class);

		if (graphQLName != null) {
			return graphQLName.value();
		}

		Object value = _getAnnotationValue(
			annotatedElement,
			graphql.annotations.annotationTypes.GraphQLName.class);

		if (value == null) {
			return null;
		}

		return (String)value;
	}

	public static boolean isGraphQLFieldValue(
		AnnotatedElement annotatedElement) {

		GraphQLField graphQLField = annotatedElement.getAnnotation(
			GraphQLField.class);

		if (graphQLField != null) {
			return graphQLField.value();
		}

		Object value = _getAnnotationValue(
			annotatedElement,
			graphql.annotations.annotationTypes.GraphQLField.class);

		if (value == null) {
			return false;
		}

		return (boolean)value;
	}

	private static Object _getAnnotationValue(
		AnnotatedElement annotatedElement, Class<?> clazz) {

		for (Annotation annotation :
				annotatedElement.getDeclaredAnnotations()) {

			Class<? extends Annotation> typeClass = annotation.annotationType();

			if (StringUtil.equals(typeClass.getName(), clazz.getName())) {
				try {
					Method method = typeClass.getMethod("value");

					return method.invoke(annotation);
				}
				catch (Exception exception) {
					throw new RuntimeException(exception);
				}
			}
		}

		return null;
	}

}