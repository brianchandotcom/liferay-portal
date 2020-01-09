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

import com.liferay.portal.kernel.model.GroupedModel;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.PermissionThreadLocal;
import com.liferay.portal.kernel.security.permission.ResourceActionsUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

import java.util.List;
import java.util.Map;

import javax.ws.rs.HttpMethod;
import javax.ws.rs.core.UriInfo;

/**
 * @author Javier Gamarra
 */
public class PermissionsUtil {

	public static Map<String, String> addAction(
		String actionName, Class clazz, GroupedModel groupedModel,
		String methodName, UriInfo uriInfo) {

		Class<? extends GroupedModel> groupedClass = groupedModel.getClass();

		Class<?> superClass = groupedClass.getSuperclass();

		Class<?>[] interfaceClasses = superClass.getInterfaces();

		String permissionName = interfaceClasses[0].getName();

		return addAction(
			actionName, clazz, (Long)groupedModel.getPrimaryKeyObj(),
			methodName, permissionName, groupedModel.getGroupId(), uriInfo);
	}

	public static Map<String, String> addAction(
		String actionName, Class clazz, Long id, String methodName,
		String permissionName, Long siteId, UriInfo uriInfo) {

		try {
			return _addAction(
				actionName, clazz, id, methodName, permissionName, siteId,
				uriInfo);
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	private static Map<String, String> _addAction(
			String actionName, Class clazz, Long id, String methodName,
			String permissionName, Long siteId, UriInfo uriInfo)
		throws Exception {

		List<String> modelResourceActions =
			ResourceActionsUtil.getModelResourceActions(permissionName);

		PermissionChecker permissionChecker =
			PermissionThreadLocal.getPermissionChecker();

		if (!modelResourceActions.contains(actionName) ||
			permissionChecker.hasPermission(
				siteId, permissionName, id, actionName)) {

			return null;
		}

		List<String> matchedURIs = uriInfo.getMatchedURIs();

		String version = "";

		if (!matchedURIs.isEmpty()) {
			version = matchedURIs.get(matchedURIs.size() - 1);
		}

		return HashMapBuilder.put(
			"href",
			uriInfo.getBaseUriBuilder(
			).path(
				version
			).path(
				clazz.getSuperclass(), methodName
			).toTemplate()
		).put(
			"method", _getHttpMethodName(clazz, methodName)
		).build();
	}

	private static String _getHttpMethodName(Class clazz, String methodName)
		throws NoSuchMethodException {

		for (Method method : clazz.getMethods()) {
			if (!methodName.equals(method.getName())) {
				continue;
			}

			Class<?> superClass = clazz.getSuperclass();

			Method superMethod = superClass.getMethod(
				method.getName(), method.getParameterTypes());

			for (Annotation annotation : superMethod.getAnnotations()) {
				Class<? extends Annotation> annotationType =
					annotation.annotationType();

				Annotation[] annotations = annotationType.getAnnotationsByType(
					HttpMethod.class);

				if (annotations.length > 0) {
					HttpMethod httpMethod = (HttpMethod)annotations[0];

					return httpMethod.value();
				}
			}
		}

		return null;
	}

}