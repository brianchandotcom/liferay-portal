/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.spring.bean;

import com.liferay.portal.kernel.jsonwebservice.JSONWebService;
import com.liferay.portal.kernel.jsonwebservice.JSONWebServiceActionsManagerUtil;
import com.liferay.portal.kernel.jsonwebservice.JSONWebServiceMappingResolver;
import com.liferay.portal.kernel.jsonwebservice.JSONWebServiceMode;
import com.liferay.portal.kernel.portlet.PortletClassLoaderUtil;
import com.liferay.portal.kernel.servlet.ServletContextPool;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.util.PropsValues;

import java.lang.reflect.Method;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;

/**
 * @author Igor Spasic
 */
public class JSONWebServiceDetectorBeanPostProcessor
	implements BeanPostProcessor {

	public Object postProcessAfterInitialization(Object bean, String beanName)
		throws BeansException {

		return bean;
	}

	public Object postProcessBeforeInitialization(Object bean, String beanName)
			throws BeansException {

		if (!PropsValues.JSON_WEB_SERVICE_ENABLED) {
			return bean;
		}

		Class<?> beanClass = bean.getClass();

		if (beanName.endsWith("Service")) {

			JSONWebService jsonNWebServiceAnnotation = beanClass.getAnnotation(
				JSONWebService.class);

			if (jsonNWebServiceAnnotation == null) {

				Class type = beanClass;

				loop:
				while (type != Object.class) {

					// check the interface

					Class<?>[] beanInterfaces = type.getInterfaces();

					for (Class<?> beanInterface : beanInterfaces) {
						if (!beanInterface.getName().endsWith("Service")) {
							continue;
						}

						jsonNWebServiceAnnotation = beanInterface.getAnnotation(
							JSONWebService.class);

						if (jsonNWebServiceAnnotation != null) {
							break loop;
						}
					}

					type = type.getSuperclass();
				}
			}

			if (jsonNWebServiceAnnotation != null) {
				try {
					onJSONWebServiceBean(bean, jsonNWebServiceAnnotation);
				}
				catch (Exception e) {
					e.printStackTrace();
				}
			}
		}

		return bean;
	}

	protected Class<?> loadUtilClass(Class<?> implementationClass)
		throws ClassNotFoundException {

		Class<?> utilClass = _utilClasses.get(implementationClass);

		if (utilClass != null) {
			return utilClass;
		}

		String utilClassName = implementationClass.getName();

		if (utilClassName.endsWith("Impl")) {
			utilClassName = utilClassName.substring(
				0, utilClassName.length() - 4);

		}

		utilClassName += "Util";

		utilClassName = StringUtil.replace(utilClassName, ".impl.", ".");

		ClassLoader classLoader = implementationClass.getClassLoader();

		utilClass = classLoader.loadClass(utilClassName);

		_utilClasses.put(implementationClass, utilClass);

		return utilClass;
	}

	protected void onJSONWebServiceBean(
			Object serviceBean, JSONWebService classJSONWebService)
		throws Exception {

		JSONWebServiceMode classJSONWebServiceMode = JSONWebServiceMode.MANUAL;

		if (classJSONWebService != null) {
			classJSONWebServiceMode = classJSONWebService.mode();
		}

		Class serviceBeanClass = serviceBean.getClass();
		Method[] methods = serviceBeanClass.getMethods();

		for (Method method : methods) {
			Class<?> methodDeclaringClass = method.getDeclaringClass();

			if (!methodDeclaringClass.equals(serviceBeanClass)) {
				continue;
			}

			if ((_excludedMethodNames != null) &&
				_excludedMethodNames.contains(method.getName())) {

				continue;
			}

			boolean registerMethod = false;

			JSONWebService methodJSONWebService = method.getAnnotation(
				JSONWebService.class);

			if (classJSONWebServiceMode.equals(JSONWebServiceMode.AUTO)) {
				registerMethod = true;

				if (methodJSONWebService != null) {
					JSONWebServiceMode methodJSONWebServiceMode =
						methodJSONWebService.mode();

					if (methodJSONWebServiceMode.equals(
							JSONWebServiceMode.IGNORE)) {

						registerMethod = false;
					}
				}
			}
			else {
				if (methodJSONWebService != null) {
					JSONWebServiceMode methodJSONWebServiceMode =
						methodJSONWebService.mode();

					if (!methodJSONWebServiceMode.equals(
							JSONWebServiceMode.IGNORE)) {

						registerMethod = true;
					}
				}
			}

			if (registerMethod) {
				registerJSONWebServiceAction(serviceBean, method);
			}
		}
	}

	protected void registerJSONWebServiceAction(
			Object serviceBean, Method method)
		throws Exception {

		Class serviceBeanClass = serviceBean.getClass();

		String path = _jsonWebServiceMappingResolver.resolvePath(
			serviceBeanClass, method);

		String httpMethod = _jsonWebServiceMappingResolver.resolveHttpMethod(
			method);

		if (_invalidHttpMethods.contains(httpMethod)) {
			return;
		}

		Class<?> utilClass = loadUtilClass(serviceBeanClass);
		try {
			method = utilClass.getMethod(
				method.getName(), method.getParameterTypes());
		}
		catch (NoSuchMethodException nsme) {
			return;
		}

		String servletContextName =
			PortletClassLoaderUtil.getServletContextName();

		if (servletContextName != null) {
			servletContextName = ServletContextPool.get(
				servletContextName).getContextPath();
		}
		else {
			servletContextName = PropsValues.PORTAL_CTX;

			if (servletContextName.equals("/")) {
				servletContextName = StringPool.BLANK;
			}
		}

		JSONWebServiceActionsManagerUtil.registerJSONWebServiceAction(
			servletContextName, method.getDeclaringClass(), method, path,
			httpMethod);
	}

	private static Set<String> _excludedMethodNames = SetUtil.fromArray(
		new String[] {"getBeanIdentifier", "setBeanIdentifier"});

	private Set<String> _invalidHttpMethods = SetUtil.fromArray(
		PropsUtil.getArray(PropsKeys.JSONWS_WEB_SERVICE_INVALID_HTTP_METHODS));

	private JSONWebServiceMappingResolver _jsonWebServiceMappingResolver =
		new JSONWebServiceMappingResolver();

	private Map<Class<?>, Class<?>> _utilClasses =
		new HashMap<Class<?>, Class<?>>();

}