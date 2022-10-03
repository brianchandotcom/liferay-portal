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

package com.liferay.portal.vulcan.internal.graphql.validation;

import com.liferay.portal.kernel.util.ObjectValuePair;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.vulcan.graphql.servlet.ServletData;
import com.liferay.portal.vulcan.graphql.validation.GraphQLRequestContext;
import com.liferay.portal.vulcan.internal.graphql.servlet.ServletDataAdapter;

import java.lang.reflect.Method;

import java.util.Objects;

/**
 * @author Carlos Correa
 */
public class ServletDataRequestContext implements GraphQLRequestContext {

	public ServletDataRequestContext(
		long companyId, Method method, boolean mutation,
		ServletData servletData) {

		_companyId = companyId;
		_method = method;
		_servletData = servletData;

		_namespace = _getNamespace(servletData);
		_resourceClass = _getResourceClass(method, mutation, servletData);
		_resourceMethod = _getResourceMethod(method, mutation, servletData);
	}

	@Override
	public String getApplicationName() {
		return _servletData.getApplicationName();
	}

	@Override
	public long getCompanyId() {
		return _companyId;
	}

	@Override
	public Method getMethod() {
		return _method;
	}

	@Override
	public String getNamespace() {
		return _namespace;
	}

	@Override
	public Class<?> getResourceClass() {
		return _resourceClass;
	}

	@Override
	public Method getResourceMethod() {
		return _resourceMethod;
	}

	@Override
	public boolean isValidationRequired() {
		if ((_servletData == null) ||
			(_servletData instanceof ServletDataAdapter)) {

			return false;
		}

		return true;
	}

	private String _getNamespace(ServletData servletData) {
		if ((servletData == null) ||
			(servletData.getGraphQLNamespace() == null)) {

			return null;
		}

		return StringUtil.upperCaseFirstLetter(
			servletData.getGraphQLNamespace());
	}

	private Class<?> _getResourceClass(
		Method method, boolean mutation, ServletData servletData) {

		if (servletData == null) {
			return null;
		}

		ObjectValuePair<Class<?>, String> resourceMethodPair =
			servletData.getResourceMethodPair(method.getName(), mutation);

		if (resourceMethodPair == null) {
			return null;
		}

		return resourceMethodPair.getKey();
	}

	private Method _getResourceMethod(
		Method method, boolean mutation, ServletData servletData) {

		if (servletData == null) {
			return null;
		}

		ObjectValuePair<Class<?>, String> resourceMethodPair =
			servletData.getResourceMethodPair(method.getName(), mutation);

		if (resourceMethodPair == null) {
			return null;
		}

		Class<?> resourceClass = resourceMethodPair.getKey();

		for (Method resourceMethod : resourceClass.getMethods()) {
			if (Objects.equals(
					resourceMethod.getName(), resourceMethodPair.getValue())) {

				return resourceMethod;
			}
		}

		return null;
	}

	private final long _companyId;
	private final Method _method;
	private final String _namespace;
	private final Class<?> _resourceClass;
	private final Method _resourceMethod;
	private final ServletData _servletData;

}