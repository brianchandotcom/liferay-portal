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

package com.liferay.oauth2.provider.scope.internal.jaxrs.feature;

import com.liferay.oauth2.provider.scope.RequiresScope;

import java.lang.reflect.Method;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.apache.cxf.Bus;
import org.apache.cxf.jaxrs.model.ClassResourceInfo;
import org.apache.cxf.jaxrs.model.MethodDispatcher;
import org.apache.cxf.jaxrs.model.OperationResourceInfo;
import org.apache.cxf.jaxrs.utils.ResourceUtils;

/**
 * This class collects scope information from an annotated JAX-RS application.
 *
 * @author Carlos Sierra Andrés
 * @review
 */
public class RequiresScopeAnnotationFinder {

	public static Collection<String> find(Class<?> clazz, Bus bus) {
		ClassResourceInfo classResourceInfo =
			ResourceUtils.createClassResourceInfo(
				clazz, clazz, true, true, bus);

		Set<String> scopes = new HashSet<>();

		_find(classResourceInfo, true, scopes);

		return scopes;
	}

	private static void _find(
		ClassResourceInfo classResourceInfo, boolean recurse,
		Set<String> scopes) {

		Class<?> resourceClass = classResourceInfo.getResourceClass();

		RequiresScope requiresScope = resourceClass.getDeclaredAnnotation(
			RequiresScope.class);

		if (requiresScope != null) {
			Collections.addAll(scopes, requiresScope.value());
		}

		MethodDispatcher methodDispatcher =
			classResourceInfo.getMethodDispatcher();

		Set<OperationResourceInfo> operationResourceInfos =
			methodDispatcher.getOperationResourceInfos();

		for (OperationResourceInfo operationResourceInfo :
				operationResourceInfos) {

			_find(operationResourceInfo, recurse, scopes);
		}
	}

	private static void _find(
		OperationResourceInfo operationResourceInfo, boolean recurse,
		Set<String> scopes) {

		Method annotatedMethod = operationResourceInfo.getAnnotatedMethod();

		RequiresScope requiresScope = annotatedMethod.getDeclaredAnnotation(
			RequiresScope.class);

		if (requiresScope != null) {
			Collections.addAll(scopes, requiresScope.value());
		}

		if (recurse && operationResourceInfo.isSubResourceLocator()) {
			ClassResourceInfo classResourceInfo =
				operationResourceInfo.getClassResourceInfo();

			Class<?> returnType = annotatedMethod.getReturnType();

			ClassResourceInfo subresource = classResourceInfo.getSubResource(
				returnType, returnType);

			if (subresource != null) {
				_find(subresource, false, scopes);
			}
		}
	}

}