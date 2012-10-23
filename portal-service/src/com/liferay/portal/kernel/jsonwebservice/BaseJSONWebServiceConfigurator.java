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

package com.liferay.portal.kernel.jsonwebservice;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.CharPool;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringUtil;

import java.io.IOException;
import java.io.InputStream;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.servlet.ServletContext;

/**
 * @author Igor Spasic
 * @author Raymond Augé
 */
public abstract class BaseJSONWebServiceConfigurator
	implements JSONWebServiceConfigurator {

	public void clean() {
		int count =
			JSONWebServiceActionsManagerUtil.unregisterJSONWebServiceActions(
				_servletContextPath);

		_registeredActionsCount -= count;

		if (_log.isDebugEnabled()) {
			if (count != 0) {
				_log.debug(
					"Removed " + count +
						" existing JSON Web Service actions that belonged to " +
							_servletContextPath);
			}
		}
	}

	public abstract void configure() throws PortalException, SystemException;

	public ClassLoader getClassLoader() {
		return _classLoader;
	}

	public int getRegisteredActionsCount() {
		return _registeredActionsCount;
	}

	public ServletContext getServletContext() {
		return _servletContext;
	}

	public String getServletContextPath() {
		return _servletContextPath;
	}

	public void init(
		String servletContextPath, ServletContext servletContext,
		ClassLoader classLoader) {

		_classLoader = classLoader;
		_servletContext = servletContext;
		_servletContextPath = servletContextPath;
	}

	public void registerClass(String className, InputStream inputStream)
		throws Exception {

		if (!className.endsWith("Service") &&
			!className.endsWith("ServiceImpl")) {

			return;
		}

		if (inputStream.markSupported()) {
			inputStream.mark(Integer.MAX_VALUE);
		}

		if (!isTypeSignatureInUse(inputStream)) {
			return;
		}

		if (inputStream.markSupported()) {
			inputStream.reset();

			try {
				JSONWebServiceClassVisitor jsonWebServiceClassVisitor =
					JSONWebServiceClassVisitorFactoryUtil.create(inputStream);

				jsonWebServiceClassVisitor.accept();

				if (!className.equals(
						jsonWebServiceClassVisitor.getClassName())) {

					return;
				}
			}
			catch (Exception e) {
				_log.error(e, e);

				return;
			}
		}

		_onJSONWebServiceClass(className);
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

		if (_invalidHttpMethods.contains(httpMethod)) {
			return;
		}

		Class<?> utilClass = _loadUtilClass(implementationClass);

		try {
			method = utilClass.getMethod(
				method.getName(), method.getParameterTypes());
		}
		catch (NoSuchMethodException nsme) {
			return;
		}

		JSONWebServiceActionsManagerUtil.registerJSONWebServiceAction(
			_servletContextPath, method.getDeclaringClass(), method, path,
			httpMethod);

		_registeredActionsCount++;
	}

	protected byte[] getTypeSignatureBytes(Class<?> clazz) {
		return ('L' + clazz.getName().replace('.', '/') + ';').getBytes();
	}

	protected boolean isTypeSignatureInUse(InputStream inputStream) {
		try {

			// create a buffer the same length as the type signature array

			byte[] buffer = new byte[_jsonWebServiceAnnotationBytes.length];

			// read an initial number of bytes into the buffer

			int value = inputStream.read(buffer);

			// if the number of bytes read is less than the type signature
			// length immediately fail

			if ((value < _jsonWebServiceAnnotationBytes.length)) {
				return false;
			}

			// if the buffer equals the type signature immediately succeed

			if (Arrays.equals(buffer, _jsonWebServiceAnnotationBytes)) {
				return true;
			}

			// read a single byte from the stream

			while ((value = inputStream.read()) != -1) {
				// immediately fail if we're at the end of the stream

				if (value == -1) {
					return false;
				}

				// shift the buffer left

				System.arraycopy(buffer, 1, buffer, 0, buffer.length - 1);

				// add the read byte to the end of the buffer

				buffer[buffer.length - 1] = (byte)value;

				// compare the updated buffer to the signature

				if (Arrays.equals(buffer, _jsonWebServiceAnnotationBytes)) {
					return true;
				}
			}
		}
		catch (IOException ioe) {
			throw new IllegalStateException(
				"Unable to read bytes from input stream.", ioe);
		}

		return false;
	}

	private static Log _log = LogFactoryUtil.getLog(
		BaseJSONWebServiceConfigurator.class);

	private ClassLoader _classLoader;
	private Set<String> _invalidHttpMethods = SetUtil.fromArray(
		PropsUtil.getArray(PropsKeys.JSONWS_WEB_SERVICE_INVALID_HTTP_METHODS));
	private final byte[] _jsonWebServiceAnnotationBytes = getTypeSignatureBytes(
		JSONWebService.class);
	private JSONWebServiceMappingResolver _jsonWebServiceMappingResolver =
		new JSONWebServiceMappingResolver();
	private int _registeredActionsCount;
	private ServletContext _servletContext;
	private String _servletContextPath;
	private Map<Class<?>, Class<?>> _utilClasses =
		new HashMap<Class<?>, Class<?>>();

}