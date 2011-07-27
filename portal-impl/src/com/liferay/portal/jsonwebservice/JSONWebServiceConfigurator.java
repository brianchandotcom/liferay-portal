/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.jsonwebservice.JSONWebService;
import com.liferay.portal.kernel.jsonwebservice.JSONWebServiceActionsManager;
import com.liferay.portal.kernel.jsonwebservice.JSONWebServiceMode;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.CharPool;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.util.PortalUtil;

import java.io.File;
import java.io.InputStream;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

import java.net.URL;

import java.util.HashMap;
import java.util.Map;

import jodd.io.findfile.FindClass;
import jodd.io.findfile.FindFile;
import jodd.io.findfile.WildcardFindFile;

import jodd.util.ClassLoaderUtil;

import org.apache.commons.lang.time.StopWatch;

/**
 * @author Igor Spasic
 */
public class JSONWebServiceConfigurator extends FindClass {

	public JSONWebServiceConfigurator() {
		setIncludedJars(
			"*portal-impl.jar", "*portal-service.jar", "*_wl_cls_gen.jar",
			"*-portlet-service.jar");
	}

	public void configure(ClassLoader classLoader) throws PortalException {
		File[] classPathFiles = null;

		if (classLoader != null) {
			URL servicePropertiesURL = classLoader.getResource(
				"service.properties");

			String servicePropertiesPath = servicePropertiesURL.getPath();

			File classPathFile = null;

			File libDir = null;

			int pos = servicePropertiesPath.indexOf("_wl_cls_gen.jar!");

			if (pos != -1) {
				String wlClsGenJarPath = servicePropertiesPath.substring(
					0, pos + 15);

				classPathFile = new File(wlClsGenJarPath);

				libDir = new File(classPathFile.getParent());
			}
			else {
				File servicePropertiesFile = new File(servicePropertiesPath);

				classPathFile = servicePropertiesFile.getParentFile();

				libDir = new File(classPathFile.getParent(), "lib");

			}

			classPathFiles = new File[2];

			classPathFiles[0] = classPathFile;

			FindFile findFile = new WildcardFindFile(
				libDir, "*-portlet-service.jar");

			classPathFiles[1] = findFile.nextFile();
		}
		else {
			Thread currentThread = Thread.currentThread();

			classLoader = currentThread.getContextClassLoader();

			File portalImplJarFile = new File(
				PortalUtil.getPortalLibDir(), "portal-impl.jar");
			File portalServiceJarFile = new File(
				PortalUtil.getGlobalLibDir(), "portal-service.jar");

			if (portalImplJarFile.exists() && portalServiceJarFile.exists()) {
				classPathFiles = new File[2];

				classPathFiles[0] = portalImplJarFile;
				classPathFiles[1] = portalServiceJarFile;
			}
			else {
				classPathFiles = ClassLoaderUtil.getDefaultClasspath(
					classLoader);
			}
		}

		_classLoader = classLoader;

		configure(classPathFiles);
	}

	public void configure(File... classPathFiles) throws PortalException {
		StopWatch stopWatch = null;

		if (_log.isDebugEnabled()) {
			_log.debug("Configure JSON web service actions");

			stopWatch = new StopWatch();

			stopWatch.start();
		}

		try {
			scanPaths(classPathFiles);
		}
		catch (Exception e) {
			throw new PortalException(e.getMessage(), e);
		}

		if (_log.isDebugEnabled()) {
			_log.debug(
				"Configuring " + _registeredActionsCount +
					" actions in " + stopWatch.getTime() + " ms");
		}
	}

	public void setCheckBytecodeSignature(boolean checkBytecodeSignature) {
		_checkBytecodeSignature = checkBytecodeSignature;
	}

	public void setJSONWebServiceActionsManager(
		JSONWebServiceActionsManager jsonWebServiceActionsManager) {

		_jsonWebServiceActionsManager = jsonWebServiceActionsManager;
	}

	@Override
	protected void onEntry(FindClass.EntryData entryData) throws Exception {
		String className = entryData.getName();

		if (className.endsWith("Service") ||
			className.endsWith("ServiceImpl")) {

			if (_checkBytecodeSignature) {
				InputStream inputStream = entryData.openInputStream();

				if (!isTypeSignatureInUse(
						inputStream, _jsonWebServiceAnnotationBytes)) {

					return;
				}
			}

			_onJSONWebServiceClass(className);
		}
	}

	private boolean _hasAnnotatedServiceImpl(String className) {
		StringBundler implClassName = new StringBundler(4);

		int pos = className.lastIndexOf(CharPool.PERIOD);

		implClassName.append(className.substring(0, pos));
		implClassName.append(".impl");
		implClassName.append(className.substring(pos));
		implClassName.append("Impl");

		Class<?> implClass = null;

		try {
			implClass = _classLoader.loadClass(implClassName.toString());
		}
		catch (ClassNotFoundException cnfe) {
			return false;
		}

		if (implClass.getAnnotation(JSONWebService.class) != null) {
			return true;
		}
		else {
			return false;
		}
	}

	private boolean _isJSONWebServiceClass(Class<?> clazz) {
		if (!clazz.isAnonymousClass() && !clazz.isArray() && !clazz.isEnum() &&
			!clazz.isLocalClass() && !clazz.isPrimitive() &&
			!(clazz.isMemberClass() ^
				Modifier.isStatic(clazz.getModifiers()))) {

			return true;
		}

		return false;
	}

	private Class<?> _loadUtilClass(Class<?> implementationClass)
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

		utilClass = _classLoader.loadClass(utilClassName);

		_utilClasses.put(implementationClass, utilClass);

		return utilClass;
	}

	private void _onJSONWebServiceClass(String className) throws Exception {
		Class<?> actionClass = _classLoader.loadClass(className);

		if (!_isJSONWebServiceClass(actionClass)) {
			return;
		}

		if (actionClass.isInterface() && _hasAnnotatedServiceImpl(className)) {
			return;
		}

		JSONWebService classAnnotation = actionClass.getAnnotation(
			JSONWebService.class);

		JSONWebServiceMode classAnnotationMode = JSONWebServiceMode.MANUAL;

		if (classAnnotation != null) {
			classAnnotationMode = classAnnotation.mode();
		}

		Method[] methods = actionClass.getMethods();

		for (Method method : methods) {
			Class<?> methodDeclaringClass = method.getDeclaringClass();

			if (!methodDeclaringClass.equals(actionClass)) {
				continue;
			}

			boolean registerMethod = false;

			JSONWebService methodAnnotation = method.getAnnotation(
				JSONWebService.class);

			if (classAnnotationMode.equals(JSONWebServiceMode.AUTO)) {
				registerMethod = true;

				if (methodAnnotation != null) {
					JSONWebServiceMode methodAnnotationMode =
						methodAnnotation.mode();

					if (methodAnnotationMode.equals(
							JSONWebServiceMode.IGNORE)) {

						registerMethod = false;
					}
				}
			}
			else {
				if (methodAnnotation != null) {
					JSONWebServiceMode methodAnnotationMode =
						methodAnnotation.mode();

					if (!methodAnnotationMode.equals(
							JSONWebServiceMode.IGNORE)) {

						registerMethod = true;
					}
				}
			}

			if (registerMethod) {
				_registerJSONWebServiceAction(actionClass, method);
			}
		}
	}

	private void _registerJSONWebServiceAction(
			Class<?> implementationClass, Method method)
		throws Exception {

		String path = _jsonWebServiceMappingResolver.resolvePath(
			implementationClass, method);

		String httpMethod = _jsonWebServiceMappingResolver.resolveHttpMethod(
			method);

		Class<?> utilClass = _loadUtilClass(implementationClass);

		try {
			method = utilClass.getMethod(
				method.getName(), method.getParameterTypes());
		}
		catch (NoSuchMethodException nsme) {
			return;
		}

		_jsonWebServiceActionsManager.registerJSONWebServiceAction(
			method.getDeclaringClass(), method, path, httpMethod);

		_registeredActionsCount++;
	}

	private static Log _log = LogFactoryUtil.getLog(
		JSONWebServiceConfigurator.class);

	private boolean _checkBytecodeSignature = true;
	private ClassLoader _classLoader;
	private JSONWebServiceActionsManager _jsonWebServiceActionsManager;
	private byte[] _jsonWebServiceAnnotationBytes =
		getTypeSignatureBytes(JSONWebService.class);
	private JSONWebServiceMappingResolver _jsonWebServiceMappingResolver =
		new JSONWebServiceMappingResolver();
	private int _registeredActionsCount;
	private Map<Class<?>, Class<?>> _utilClasses =
		new HashMap<Class<?>, Class<?>>();

}