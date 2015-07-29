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

package com.liferay.portal.jsonwebservice;

import com.liferay.portal.kernel.annotation.AnnotationLocator;
import com.liferay.portal.kernel.bean.BeanLocator;
import com.liferay.portal.kernel.bean.BeanLocatorException;
import com.liferay.portal.kernel.jsonwebservice.JSONWebService;
import com.liferay.portal.kernel.jsonwebservice.JSONWebServiceActionsManagerUtil;
import com.liferay.portal.kernel.jsonwebservice.JSONWebServiceMappingResolver;
import com.liferay.portal.kernel.jsonwebservice.JSONWebServiceMode;
import com.liferay.portal.kernel.jsonwebservice.JSONWebServiceNaming;
import com.liferay.portal.kernel.jsonwebservice.JSONWebServiceRegistrator;
import com.liferay.portal.kernel.jsonwebservice.JSONWebServiceScannerStrategy;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.util.PropsValues;

import java.lang.reflect.Method;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Igor Spasic
 */
public class DefaultJSONWebServiceRegistrator
	implements JSONWebServiceRegistrator {

	public DefaultJSONWebServiceRegistrator() {
		_jsonWebServiceNaming =
			JSONWebServiceActionsManagerUtil.getJSONWebServiceNaming();

		_jsonWebServiceMappingResolver = new JSONWebServiceMappingResolver(
			_jsonWebServiceNaming);

		_jsonWebServiceScannerStrategy =
			new SpringJSONWebServiceScannerStrategy();
	}

	public DefaultJSONWebServiceRegistrator(
		JSONWebServiceNaming jsonWebServiceNaming,
		JSONWebServiceScannerStrategy jsonWebServiceScannerStrategy) {

		_jsonWebServiceNaming = jsonWebServiceNaming;
		_jsonWebServiceScannerStrategy = jsonWebServiceScannerStrategy;

		_jsonWebServiceMappingResolver = new JSONWebServiceMappingResolver(
			_jsonWebServiceNaming);
	}

	public DefaultJSONWebServiceRegistrator(
		JSONWebServiceScannerStrategy jsonWebServiceScannerStrategy) {

		this(
			JSONWebServiceActionsManagerUtil.getJSONWebServiceNaming(),
			jsonWebServiceScannerStrategy);
	}

	public void processAllBeans(
		String contextName, String contextPath, BeanLocator beanLocator) {

		if (beanLocator == null) {
			return;
		}

		String[] beanNames = beanLocator.getNames();

		for (String beanName : beanNames) {
			processBean(contextName, contextPath, beanLocator, beanName);
		}
	}

	public void processBean(
		String contextName, String contextPath, BeanLocator beanLocator,
		String beanName) {

		if (!PropsValues.JSON_WEB_SERVICE_ENABLED) {
			return;
		}

		Object bean = null;

		try {
			bean = beanLocator.locate(beanName);
		}
		catch (BeanLocatorException ble) {
			return;
		}

		if (bean == null) {
			return;
		}

		JSONWebService jsonWebService = AnnotationLocator.locate(
			bean.getClass(), JSONWebService.class);

		if (jsonWebService != null) {
			try {
				onJSONWebServiceBean(
					contextName, contextPath, bean, jsonWebService);
			}
			catch (Exception e) {
				_log.error(e, e);
			}
		}
	}

	@Override
	public void processBean(
		String contextName, String contextPath, Object bean) {

		if (!PropsValues.JSON_WEB_SERVICE_ENABLED) {
			return;
		}

		JSONWebService jsonWebService = AnnotationLocator.locate(
			bean.getClass(), JSONWebService.class);

		if (jsonWebService == null) {
			return;
		}

		try {
			onJSONWebServiceBean(
				contextName, contextPath, bean, jsonWebService);
		}
		catch (Exception e) {
			_log.error(e, e);
		}
	}

	public void setWireViaUtil(boolean wireViaUtil) {
		this._wireViaUtil = wireViaUtil;
	}

	protected Class<?> loadUtilClass(Class<?> implementationClass)
		throws ClassNotFoundException {

		if (_utilClasses == null) {
			_utilClasses = new HashMap<>();
		}

		Class<?> utilClass = _utilClasses.get(implementationClass);

		if (utilClass != null) {
			return utilClass;
		}

		String utilClassName =
			_jsonWebServiceNaming.convertServiceImplClassToUtilClassName(
				implementationClass);

		ClassLoader classLoader = implementationClass.getClassLoader();

		utilClass = classLoader.loadClass(utilClassName);

		_utilClasses.put(implementationClass, utilClass);

		return utilClass;
	}

	protected void onJSONWebServiceBean(
			String contextName, String contextPath, Object serviceBean,
			JSONWebService jsonWebService)
		throws Exception {

		JSONWebServiceMode jsonWebServiceMode = JSONWebServiceMode.MANUAL;

		if (jsonWebService != null) {
			jsonWebServiceMode = jsonWebService.mode();
		}

		JSONWebServiceScannerStrategy.MethodDescriptor[] methodDescriptors =
			_jsonWebServiceScannerStrategy.scan(serviceBean);

		for (JSONWebServiceScannerStrategy.MethodDescriptor methodDescriptor :
				methodDescriptors) {

			Method method = methodDescriptor.getMethod();

			if (!_jsonWebServiceNaming.isIncludedMethod(method)) {
				continue;
			}

			JSONWebService methodJSONWebService = method.getAnnotation(
				JSONWebService.class);

			if (jsonWebServiceMode.equals(JSONWebServiceMode.AUTO)) {
				if (methodJSONWebService == null) {
					registerJSONWebServiceAction(
						contextName, contextPath, serviceBean,
						methodDescriptor.getDeclaringClass(), method);
				}
				else {
					JSONWebServiceMode methodJSONWebServiceMode =
						methodJSONWebService.mode();

					if (!methodJSONWebServiceMode.equals(
							JSONWebServiceMode.IGNORE)) {

						registerJSONWebServiceAction(
							contextName, contextPath, serviceBean,
							methodDescriptor.getDeclaringClass(), method);
					}
				}
			}
			else if (methodJSONWebService != null) {
				JSONWebServiceMode methodJSONWebServiceMode =
					methodJSONWebService.mode();

				if (!methodJSONWebServiceMode.equals(
						JSONWebServiceMode.IGNORE)) {

					registerJSONWebServiceAction(
						contextName, contextPath, serviceBean,
						methodDescriptor.getDeclaringClass(), method);
				}
			}
		}
	}

	protected void registerJSONWebServiceAction(
			String contextName, String contextPath, Object serviceBean,
			Class<?> serviceBeanClass, Method method)
		throws Exception {

		String httpMethod = _jsonWebServiceMappingResolver.resolveHttpMethod(
			method);

		if (!_jsonWebServiceNaming.isValidHttpMethod(httpMethod)) {
			return;
		}

		if (_wireViaUtil) {
			Class<?> utilClass = loadUtilClass(serviceBeanClass);

			try {
				method = utilClass.getMethod(
					method.getName(), method.getParameterTypes());
			}
			catch (NoSuchMethodException nsme) {
				return;
			}
		}

		String path = _jsonWebServiceMappingResolver.resolvePath(
			serviceBeanClass, method);

		if (!_jsonWebServiceNaming.isIncludedPath(contextPath, path)) {
			return;
		}

		if (_wireViaUtil) {
			JSONWebServiceActionsManagerUtil.registerJSONWebServiceAction(
				contextName, contextPath, method.getDeclaringClass(), method,
				path, httpMethod);
		}
		else {
			JSONWebServiceActionsManagerUtil.registerJSONWebServiceAction(
				contextName, contextPath, serviceBean, serviceBeanClass, method,
				path, httpMethod);
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		DefaultJSONWebServiceRegistrator.class);

	private final JSONWebServiceMappingResolver _jsonWebServiceMappingResolver;
	private final JSONWebServiceNaming _jsonWebServiceNaming;
	private final JSONWebServiceScannerStrategy _jsonWebServiceScannerStrategy;
	private Map<Class<?>, Class<?>> _utilClasses;
	private boolean _wireViaUtil;

}