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

import com.liferay.oauth2.provider.scope.ScopeChecker;
import com.liferay.oauth2.provider.scope.liferay.OAuth2ProviderScopeLiferayAccessControlContext;
import com.liferay.portal.kernel.model.GroupedModel;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.PermissionThreadLocal;
import com.liferay.portal.kernel.security.permission.ResourceActionsUtil;

import javax.ws.rs.HttpMethod;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Javier Gamarra
 */
public class PermissionsUtil {

	public static Map<String, String> addAction(
		String actionName, Class clazz, GroupedModel groupedModel,
		String methodName, ScopeChecker scopeChecker,
		UriInfo uriInfo) {

		Class<? extends GroupedModel> groupedClass = groupedModel.getClass();

		Class<?> superClass = groupedClass.getSuperclass();

		Class<?>[] interfaces = superClass.getInterfaces();

		String permissionName = interfaces[0].getName();

		return addAction(
			actionName, clazz, (Long)groupedModel.getPrimaryKeyObj(),
			methodName, permissionName, scopeChecker, groupedModel.getGroupId(),
			uriInfo);
	}

	public static Map<String, String> addAction(
		String actionName, Class clazz, Long id, String methodName,
		String permissionName, ScopeChecker scopeChecker, Long siteId,
		UriInfo uriInfo) {

		try {
			List<String> modelResourceActions =
				ResourceActionsUtil.getModelResourceActions(permissionName);

			PermissionChecker permissionChecker =
				PermissionThreadLocal.getPermissionChecker();

			String httpMethodName = _getHttpMethodName(clazz, methodName);

			if (modelResourceActions.contains(actionName) &&
				permissionChecker.hasPermission(
					siteId, permissionName, id, actionName) &&
				(!OAuth2ProviderScopeLiferayAccessControlContext.
					isOAuth2AuthVerified() ||
				 scopeChecker.checkScope(httpMethodName))) {

				UriBuilder uriBuilder = uriInfo.getAbsolutePathBuilder();

				String uri = uriBuilder.path(
					clazz.getSuperclass(), methodName
				).toTemplate();

				Map<String, String> actions = new HashMap<>();

				actions.put("href", uri);
				actions.put("method", httpMethodName);

				return actions;
			}
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}

		return null;
	}

	private static String _getHttpMethodName(Class clazz, String methodName)
		throws NoSuchMethodException {

		for (Method method : clazz.getMethods()) {
			if (methodName.equals(method.getName())) {
				Class<?> superclass = clazz.getSuperclass();

				Method superMethod = superclass.getMethod(
					method.getName(), method.getParameterTypes());

				for (Annotation annotation : superMethod.getAnnotations()) {
					Class<? extends Annotation> annotationType =
						annotation.annotationType();

					Annotation[] annotations =
						annotationType.getAnnotationsByType(HttpMethod.class);

					if (annotations.length > 0) {
						HttpMethod methodAnnotation =
							(HttpMethod)annotations[0];

						return methodAnnotation.value();
					}
				}
			}
		}

		return null;
	}

}