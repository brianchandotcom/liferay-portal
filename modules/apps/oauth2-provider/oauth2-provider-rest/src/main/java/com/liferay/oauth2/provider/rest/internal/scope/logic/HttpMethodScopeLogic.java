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

import com.liferay.oauth2.provider.rest.spi.scope.logic.ScopeLogic;
import com.liferay.oauth2.provider.scope.ScopeChecker;
import com.liferay.oauth2.provider.scope.spi.scope.finder.ScopeFinder;
import com.liferay.osgi.util.StringPlus;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.vulcan.jaxrs.JaxRsResourceRegistry;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import javax.ws.rs.HttpMethod;
import javax.ws.rs.core.Application;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Carlos Correa
 * @author Stian Sigvartsen
 */
@Component(
	property = "oauth2.scope.checker.type=http.method",
	service = ScopeLogic.class
)
public class HttpMethodScopeLogic implements ScopeLogic {

	@Override
	public boolean check(
		ScopeChecker scopeChecker, Class<?> resourceClass,
		Method resourceMethod) {

		try {
			String applicationName = _getApplicationName(resourceClass);

			Set<String> ignoreMissingScopes = Optional.ofNullable(
				_getIgnoreMissingScopes(applicationName)
			).orElse(
				_ignoreMissingScopes
			);

			ScopeFinder scopeFinder = _bundleContext.getService(
				_getServiceReference(applicationName, ScopeFinder.class));

			Collection<String> scopes = scopeFinder.findScopes();

			String httpMethod = _getHttpMethod(resourceMethod);

			if ((!scopes.contains(httpMethod) &&
				 ignoreMissingScopes.contains(httpMethod)) ||
				scopeChecker.checkScope(httpMethod)) {

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
	}

	private String _getApplicationName(Class<?> resourceClass) {
		String applicationName =
			(String)_jaxRsResourceRegistry.getPropertyValue(
				resourceClass.getName(), "osgi.jaxrs.application.select");

		return StringUtil.removeSubstrings(
			applicationName, "(osgi.jaxrs.name=", ")");
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

	private Set<String> _getIgnoreMissingScopes(String applicationName)
		throws Exception {

		ServiceReference<Application> serviceReference =
			(ServiceReference<Application>)_getServiceReference(
				applicationName, Application.class);

		Object ignoreMissingScopesObject = serviceReference.getProperty(
			"ignore.missing.scopes");

		if (ignoreMissingScopesObject == null) {
			return null;
		}

		return new HashSet<>(StringPlus.asList(ignoreMissingScopesObject));
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

	private final Set<String> _ignoreMissingScopes = new HashSet<String>() {
		{
			add(HttpMethod.HEAD);

			add(HttpMethod.OPTIONS);
		}
	};

	@Reference
	private JaxRsResourceRegistry _jaxRsResourceRegistry;

}