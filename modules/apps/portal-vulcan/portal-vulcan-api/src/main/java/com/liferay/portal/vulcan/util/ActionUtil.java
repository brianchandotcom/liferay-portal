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
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.vulcan.graphql.util.GraphQLNamingUtil;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.ws.rs.HttpMethod;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.UriInfo;

/**
 * @author Javier Gamarra
 */
public class ActionUtil {

	public static Map<String, String> addAction(
		String actionName, Class<?> clazz, GroupedModel groupedModel,
		String methodName, Object object, UriInfo uriInfo) {

		return addAction(
			actionName, clazz, (Long)groupedModel.getPrimaryKeyObj(),
			methodName, object, groupedModel.getUserId(),
			groupedModel.getModelClassName(), groupedModel.getGroupId(),
			uriInfo);
	}

	/**
	 * @deprecated As of Athanasius (7.3.x), replaced by {@link
	 *             #addAction(String, Class, Long, String, Object, Long, String,
	 *             Long, UriInfo)}
	 */
	@Deprecated
	public static Map<String, String> addAction(
		String actionName, Class<?> clazz, GroupedModel groupedModel,
		String methodName, UriInfo uriInfo) {

		return addAction(
			actionName, clazz, (Long)groupedModel.getPrimaryKeyObj(),
			methodName, null, groupedModel.getUserId(),
			groupedModel.getModelClassName(), groupedModel.getGroupId(),
			uriInfo);
	}

	/**
	 * @deprecated As of Athanasius (7.3.x), replaced by {@link
	 *             #addAction(String, Class, Long, String, Object,
	 *             ModelResourcePermission, UriInfo)}
	 */
	@Deprecated
	public static Map<String, String> addAction(
		String actionName, Class<?> clazz, Long id, String methodName,
		Object object, Long ownerId, String permissionName, Long siteId,
		UriInfo uriInfo) {

		try {
			return _addAction(
				actionName, clazz, id, methodName, null, object, ownerId,
				permissionName, siteId, uriInfo);
		}
		catch (Exception exception) {
			throw new RuntimeException(exception);
		}
	}

	public static Map<String, String> addAction(
		String actionName, Class<?> clazz, Long id, String methodName,
		Object object, ModelResourcePermission<?> modelResourcePermission,
		UriInfo uriInfo) {

		try {
			return _addAction(
				actionName, clazz, id, methodName, modelResourcePermission,
				object, null, null, null, uriInfo);
		}
		catch (Exception exception) {
			throw new RuntimeException(exception);
		}
	}

	/**
	 * @deprecated As of Athanasius (7.3.x), replaced by {@link
	 *             #addAction(String, Class, Long, String, Object, Long, String,
	 *             Long, UriInfo)}
	 */
	@Deprecated
	public static Map<String, String> addAction(
		String actionName, Class<?> clazz, Long id, String methodName,
		String permissionName, Long siteId, UriInfo uriInfo) {

		return addAction(
			actionName, clazz, id, methodName, null, null, permissionName,
			siteId, uriInfo);
	}

	/**
	 * @deprecated As of Athanasius (7.3.x), replaced by {@link
	 *             #addAction(String, Class, Long, String, Object, Long, String,
	 *             Long, UriInfo)}
	 */
	@Deprecated
	public static Map<String, String> addAction(
		String actionName, Class<?> clazz, Long id, String methodName,
		String permissionName, Object object, Long siteId, UriInfo uriInfo) {

		return addAction(
			actionName, clazz, id, methodName, object, null, permissionName,
			siteId, uriInfo);
	}

	private static Map<String, String> _addAction(
			String actionName, Class<?> clazz, Long id, String methodName,
			ModelResourcePermission<?> modelResourcePermission, Object object,
			Long ownerId, String permissionName, Long siteId, UriInfo uriInfo)
		throws Exception {

		if (uriInfo == null) {
			return new HashMap<>();
		}

		MultivaluedMap<String, String> queryParameters =
			uriInfo.getQueryParameters();

		String restrictFields = queryParameters.getFirst("restrictFields");

		if (restrictFields != null) {
			List<String> strings = Arrays.asList(restrictFields.split(","));

			if (strings.contains("actions")) {
				return null;
			}
		}

		PermissionChecker permissionChecker =
			PermissionThreadLocal.getPermissionChecker();

		if (modelResourcePermission == null) {
			List<String> modelResourceActions =
				ResourceActionsUtil.getModelResourceActions(permissionName);

			if (!modelResourceActions.contains(actionName) ||
				!_hasPermission(
					actionName, id, ownerId, permissionChecker, permissionName,
					siteId)) {

				return null;
			}
		}
		else if (!modelResourcePermission.contains(
					permissionChecker, id, actionName)) {

			return null;
		}

		Method method = _getMethod(clazz, methodName);

		String httpMethodName = _getHttpMethodName(clazz, method);

		if ((object != null) &&
			OAuth2ProviderScopeLiferayAccessControlContext.
				isOAuth2AuthVerified()) {

			ScopeChecker scopeChecker = (ScopeChecker)object;

			if (!scopeChecker.checkScope(httpMethodName)) {
				return null;
			}
		}

		String baseURIString = UriInfoUtil.getBasePath(uriInfo);

		if (baseURIString.contains("/graphql")) {
			String operation = null;
			String type = null;

			if (httpMethodName.equals("GET")) {
				Class<?> returnType = method.getReturnType();

				Stream<Method> stream = Arrays.stream(clazz.getMethods());

				operation = GraphQLNamingUtil.getGraphQLPropertyName(
					methodName, returnType.getName(),
					stream.map(
						Method::getName
					).collect(
						Collectors.toList()
					));

				type = "query";
			}
			else {
				operation = GraphQLNamingUtil.getGraphQLMutationName(
					methodName);
				type = "mutation";
			}

			return HashMapBuilder.put(
				"operation", operation
			).put(
				"type", type
			).build();
		}

		return HashMapBuilder.put(
			"href",
			UriInfoUtil.getBaseUriBuilder(
				uriInfo
			).path(
				_getVersion(uriInfo)
			).path(
				clazz.getSuperclass(), methodName
			).toTemplate()
		).put(
			"method", httpMethodName
		).build();
	}

	private static String _getHttpMethodName(Class<?> clazz, Method method)
		throws Exception {

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

		return null;
	}

	private static Method _getMethod(Class<?> clazz, String methodName) {
		for (Method method : clazz.getMethods()) {
			if (!methodName.equals(method.getName())) {
				continue;
			}

			return method;
		}

		return null;
	}

	private static String _getVersion(UriInfo uriInfo) {
		String version = "";

		List<String> matchedURIs = uriInfo.getMatchedURIs();

		if (!matchedURIs.isEmpty()) {
			version = matchedURIs.get(matchedURIs.size() - 1);
		}

		return version;
	}

	private static boolean _hasPermission(
		String actionName, Long id, Long ownerId,
		PermissionChecker permissionChecker, String permissionName,
		Long siteId) {

		if (((ownerId != null) &&
			 permissionChecker.hasOwnerPermission(
				 permissionChecker.getCompanyId(), permissionName, id, ownerId,
				 actionName)) ||
			permissionChecker.hasPermission(
				siteId, permissionName, id, actionName)) {

			return true;
		}

		return false;
	}

}