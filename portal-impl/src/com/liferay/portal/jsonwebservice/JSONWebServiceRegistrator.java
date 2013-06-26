/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.ProxyUtil;
import com.liferay.portal.spring.aop.ServiceBeanAopProxy;
import com.liferay.portal.util.PropsValues;

import java.lang.reflect.Method;

import java.util.HashMap;
import java.util.Map;

import org.springframework.aop.TargetSource;
import org.springframework.aop.framework.AdvisedSupport;

/**
 * @author Igor Spasic
 */
public class JSONWebServiceRegistrator {

	public JSONWebServiceRegistrator() {
		_jsonWebServiceNaming = new JSONWebServiceNaming();

		_jsonWebServiceMappingResolver = new JSONWebServiceMappingResolver(
			_jsonWebServiceNaming);
	}

	public JSONWebServiceRegistrator(
		JSONWebServiceNaming jsonWebServiceNaming) {

		_jsonWebServiceNaming = jsonWebServiceNaming;

		_jsonWebServiceMappingResolver = new JSONWebServiceMappingResolver(
			_jsonWebServiceNaming);
	}

	public void processAllBeans(String contextPath, BeanLocator beanLocator) {
		if (beanLocator == null) {
			return;
		}

		String[] beanNames = beanLocator.getNames();

		for (String beanName : beanNames) {
			processBean(contextPath, beanLocator, beanName);
		}
	}

	public void processBean(
		String contextPath, BeanLocator beanLocator, String beanName) {

		if (!PropsValues.JSON_WEB_SERVICE_ENABLED) {
			return;
		}

		Object bean = null;

		try {
			bean = beanLocator.locate(beanName);
		}
		catch (BeanLocatorException e) {
			return;
		}

		JSONWebService jsonWebService = AnnotationLocator.locate(
			bean.getClass(), JSONWebService.class);

		if (jsonWebService != null) {
			try {
				onJSONWebServiceBean(contextPath, bean, jsonWebService);
			}
			catch (Exception e) {
				_log.error(e, e);
			}
		}
	}

	public void processBean(String contextPath, Object bean) {
		if (!PropsValues.JSON_WEB_SERVICE_ENABLED) {
			return;
		}

		JSONWebService jsonWebService = AnnotationLocator.locate(
			bean.getClass(), JSONWebService.class);

		if (jsonWebService == null) {
			return;
		}

		try {
			onJSONWebServiceBean(contextPath, bean, jsonWebService);
		}
		catch (Exception e) {
			_log.error(e, e);
		}
	}

	public void setWireViaUtil(boolean wireViaUtil) {
		this._wireViaUtil = wireViaUtil;
	}

	protected Class<?> getTargetClass(Object service) throws Exception {
		if (ProxyUtil.isProxyClass(service.getClass())) {
			AdvisedSupport advisedSupport =
				ServiceBeanAopProxy.getAdvisedSupport(service);

			TargetSource targetSource = advisedSupport.getTargetSource();

			service = targetSource.getTarget();
		}

		return service.getClass();
	}

	protected Class<?> loadUtilClass(Class<?> implementationClass)
		throws ClassNotFoundException {

		if (_utilClasses == null) {
			_utilClasses = new HashMap<Class<?>, Class<?>>();
		}

		Class<?> utilClass = _utilClasses.get(implementationClass);

		if (utilClass != null) {
			return utilClass;
		}

		String utilClassName =
			_jsonWebServiceNaming.implClassNameToUtilClassName(
				implementationClass);

		ClassLoader classLoader = implementationClass.getClassLoader();

		utilClass = classLoader.loadClass(utilClassName);

		_utilClasses.put(implementationClass, utilClass);

		return utilClass;
	}

	protected void onJSONWebServiceBean(
			String contextPath, Object serviceBean,
			JSONWebService jsonWebService)
		throws Exception {

		JSONWebServiceMode jsonWebServiceMode = JSONWebServiceMode.MANUAL;

		if (jsonWebService != null) {
			jsonWebServiceMode = jsonWebService.mode();
		}

		Class<?> serviceBeanClass = getTargetClass(serviceBean);

		Method[] methods = serviceBeanClass.getMethods();

		for (Method method : methods) {
			Class<?> declaringClass = method.getDeclaringClass();

			if (declaringClass != serviceBeanClass) {
				continue;
			}

			if (!_jsonWebServiceNaming.acceptMethod(method)) {
				continue;
			}

			JSONWebService methodJSONWebService = method.getAnnotation(
				JSONWebService.class);

			if (jsonWebServiceMode.equals(JSONWebServiceMode.AUTO)) {
				if (methodJSONWebService == null) {
					registerJSONWebServiceAction(
						contextPath, serviceBean, serviceBeanClass, method);
				}
				else {
					JSONWebServiceMode methodJSONWebServiceMode =
						methodJSONWebService.mode();

					if (!methodJSONWebServiceMode.equals(
							JSONWebServiceMode.IGNORE)) {

						registerJSONWebServiceAction(
							contextPath, serviceBean, serviceBeanClass, method);
					}
				}
			}
			else if (methodJSONWebService != null) {
				JSONWebServiceMode methodJSONWebServiceMode =
					methodJSONWebService.mode();

				if (!methodJSONWebServiceMode.equals(
						JSONWebServiceMode.IGNORE)) {

					registerJSONWebServiceAction(
						contextPath, serviceBean, serviceBeanClass, method);
				}
			}
		}
	}

	protected void registerJSONWebServiceAction(
			String contextPath, Object serviceBean, Class<?> serviceBeanClass,
			Method method)
		throws Exception {

		String httpMethod = _jsonWebServiceMappingResolver.resolveHttpMethod(
			method);

		if (!_jsonWebServiceNaming.acceptHttpMethod(httpMethod)) {
			return;
		}

		if (_wireViaUtil == true) {
			Class<?> utilClass = loadUtilClass(serviceBeanClass);

			try {
				method = utilClass.getMethod(
					method.getName(), method.getParameterTypes());
			}
			catch (NoSuchMethodException nsme) {
				return;
			}

			String path = _jsonWebServiceMappingResolver.resolvePath(
				serviceBeanClass, method);

			JSONWebServiceActionsManagerUtil.registerJSONWebServiceAction(
				contextPath, method.getDeclaringClass(), method, path,
				httpMethod);
		}
		else {
			String path = _jsonWebServiceMappingResolver.resolvePath(
				serviceBeanClass, method);

			JSONWebServiceActionsManagerUtil.registerJSONWebServiceAction(
				contextPath, serviceBean, serviceBeanClass, method, path,
				httpMethod);
		}
	}

	private static Log _log = LogFactoryUtil.getLog(
		JSONWebServiceRegistrator.class);

	private final JSONWebServiceMappingResolver _jsonWebServiceMappingResolver;
	private final JSONWebServiceNaming _jsonWebServiceNaming;
	private Map<Class<?>, Class<?>> _utilClasses;
	private boolean _wireViaUtil;

}