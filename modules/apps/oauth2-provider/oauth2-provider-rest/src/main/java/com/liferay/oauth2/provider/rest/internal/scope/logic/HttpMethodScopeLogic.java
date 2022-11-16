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

package com.liferay.oauth2.provider.rest.internal.scope.logic;

import com.liferay.oauth2.provider.scope.ScopeChecker;
import com.liferay.oauth2.provider.scope.spi.scope.finder.ScopeFinder;
import com.liferay.osgi.util.StringPlus;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ListUtil;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;

import javax.ws.rs.HttpMethod;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;

/**
 * @author Carlos Correa
 * @author Stian Sigvartsen
 */
@Component(
	property = {
		"ignore.missing.scopes=HEAD", "ignore.missing.scopes=OPTIONS",
		"oauth2.scope.checker.type=http.method"
	},
	service = {HttpMethodScopeLogic.class, ScopeLogic.class}
)
public class HttpMethodScopeLogic implements ScopeLogic {

	@Override
	public boolean check(
		Function<String, Object> propertyAccessorFunction,
		Class<?> resourceClass, Method resourceMethod,
		ScopeChecker scopeChecker) {

		return check(
			propertyAccessorFunction, _getHttpMethod(resourceMethod),
			scopeChecker);
	}

	public boolean check(
		Function<String, Object> propertyAccessorFunction, String requestMethod,
		ScopeChecker scopeChecker) {

		try {
			String applicationName = GetterUtil.getString(
				propertyAccessorFunction.apply("osgi.jaxrs.name"));

			Object ignoreMissingScopesObject = propertyAccessorFunction.apply(
				"ignore.missing.scopes");

			Set<String> ignoreMissingScopes = _ignoreMissingScopes;

			if (ignoreMissingScopesObject != null) {
				ignoreMissingScopes = new HashSet<>(
					StringPlus.asList(ignoreMissingScopesObject));
			}

			ScopeFinder scopeFinder = _bundleContext.getService(
				_getServiceReference(applicationName, ScopeFinder.class));

			Collection<String> scopes = scopeFinder.findScopes();

			if ((!scopes.contains(requestMethod) &&
				 ignoreMissingScopes.contains(requestMethod)) ||
				scopeChecker.checkScope(requestMethod)) {

				return true;
			}
		}
		catch (Exception exception) {
			if (_log.isWarnEnabled()) {
				_log.warn(exception);
			}
		}

		return false;
	}

	@Activate
	protected void activate(
		BundleContext bundleContext, Map<String, Object> properties) {

		_bundleContext = bundleContext;

		_ignoreMissingScopes = new HashSet<>(
			StringPlus.asList(properties.get("ignore.missing.scopes")));
	}

	private String _getHttpMethod(Method method) {
		while (method != null) {
			for (Annotation annotation : method.getAnnotations()) {
				Class<? extends Annotation> annotationType =
					annotation.annotationType();

				HttpMethod[] annotationsByType =
					annotationType.getAnnotationsByType(HttpMethod.class);

				if (annotationsByType != null) {
					for (HttpMethod httpMethod : annotationsByType) {
						return httpMethod.value();
					}
				}
			}

			method = _getSuperMethod(method);
		}

		throw new UnsupportedOperationException();
	}

	private <T> ServiceReference<? extends T> _getServiceReference(
			String applicationName, Class<? extends T> clazz)
		throws Exception {

		List<ServiceReference<T>> serviceReferences =
			(List<ServiceReference<T>>)_bundleContext.<T>getServiceReferences(
				(Class<T>)clazz, "(osgi.jaxrs.name=" + applicationName + ")");

		if (ListUtil.isNotEmpty(serviceReferences)) {
			return serviceReferences.get(0);
		}

		throw new UnsupportedOperationException(
			"Invalid JAX-RS application " + applicationName);
	}

	private Method _getSuperMethod(Method method) {
		Class<?> clazz = method.getDeclaringClass();

		clazz = clazz.getSuperclass();

		if (clazz == Object.class) {
			return null;
		}

		try {
			return clazz.getDeclaredMethod(
				method.getName(), method.getParameterTypes());
		}
		catch (NoSuchMethodException noSuchMethodException) {
			if (_log.isDebugEnabled()) {
				_log.debug(noSuchMethodException);
			}

			return null;
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		HttpMethodScopeLogic.class);

	private BundleContext _bundleContext;
	private Set<String> _ignoreMissingScopes;

}