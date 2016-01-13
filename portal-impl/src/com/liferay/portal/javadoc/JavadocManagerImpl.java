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

package com.liferay.portal.javadoc;

import com.liferay.portal.kernel.javadoc.BaseJavadoc;
import com.liferay.portal.kernel.javadoc.EmptyJavadocMethod;
import com.liferay.portal.kernel.javadoc.JavadocClass;
import com.liferay.portal.kernel.javadoc.JavadocManager;
import com.liferay.portal.kernel.javadoc.JavadocMethod;
import com.liferay.portal.kernel.javadoc.JavadocMethodImpl;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.security.pacl.DoPrivileged;
import com.liferay.portal.kernel.util.StreamUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.xml.Document;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.kernel.xml.UnsecureSAXReaderUtil;
import com.liferay.portal.util.PropsValues;

import java.io.InputStream;

import java.lang.reflect.Method;

import java.net.URL;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * @author Igor Spasic
 */
@DoPrivileged
public class JavadocManagerImpl implements JavadocManager {

	@Override
	public void load(String servletContextName, ClassLoader classLoader) {
		if (!PropsValues.JAVADOC_MANAGER_ENABLED) {
			return;
		}

		if (_log.isInfoEnabled()) {
			_log.info("Loading Javadocs for \"" + servletContextName + '\"');
		}

		Document document = getDocument(classLoader);

		if (document == null) {
			return;
		}

		parseDocument(servletContextName, classLoader, document);

		if (_log.isInfoEnabled()) {
			_log.info("Loaded Javadocs for \"" + servletContextName + '\"');
		}
	}

	@Override
	public JavadocClass lookupJavadocClass(Class<?> clazz) {
		return _javadocClasses.get(clazz);
	}

	@Override
	public JavadocMethod lookupJavadocMethod(Method method) {
		JavadocMethod javadocMethod = _javadocMethods.get(method);

		if (javadocMethod != null) {
			return javadocMethod;
		}

		Class<?> clazz = method.getDeclaringClass();

		String className = clazz.getName();

		if (!className.contains(".service.") ||
			!className.endsWith("ServiceUtil")) {

			return null;
		}

		String implClassName = StringUtil.replace(
			className, new String[] {".service.", "ServiceUtil"},
			new String[] {".service.impl.", "ServiceImpl"});

		if (_log.isDebugEnabled()) {
			_log.debug(
				"Attempting to load method from class " + implClassName +
					" instead of " + className);
		}

		try {
			Class<?> implClass = JavadocUtil.loadClass(
				clazz.getClassLoader(), implClassName);

			Method implMethod = implClass.getMethod(
				method.getName(), method.getParameterTypes());

			return _javadocMethods.get(implMethod);
		}
		catch (NoSuchMethodException nsme) {
			if (_log.isWarnEnabled()) {
				_log.warn(
					"Unable to load method " + method.getName() +
						" from class " + implClassName);
			}
		}
		catch (Exception e) {
			if (_log.isWarnEnabled()) {
				_log.warn(
					"Unable to load implementation class " + implClassName);
			}
		}

		return null;
	}

	@Override
	public void unload(String servletContextName) {
		if (_log.isInfoEnabled()) {
			_log.info("Unloading Javadocs for \"" + servletContextName + '\"');
		}

		unload(servletContextName, _javadocClasses.values());
		unload(servletContextName, _javadocMethods.values());

		if (_log.isInfoEnabled()) {
			_log.info("Unloaded Javadocs for \"" + servletContextName + '\"');
		}
	}

	protected Document getDocument(ClassLoader classLoader) {
		InputStream inputStream = null;

		try {
			URL url = classLoader.getResource("META-INF/javadocs-rt.xml");

			if (url == null) {
				return null;
			}

			inputStream = url.openStream();

			return UnsecureSAXReaderUtil.read(inputStream, true);
		}
		catch (Exception e) {
			_log.error(e, e);
		}
		finally {
			StreamUtil.cleanUp(inputStream);
		}

		return null;
	}

	protected void parseDocument(
		String servletContextName, ClassLoader classLoader, Document document) {

		Element rootElement = document.getRootElement();

		List<Element> javadocElements = rootElement.elements("javadoc");

		for (Element javadocElement : javadocElements) {
			String type = javadocElement.elementText("type");

			Class<?> clazz = null;

			try {
				clazz = JavadocUtil.loadClass(classLoader, type);
			}
			catch (ClassNotFoundException cnfe) {
				if (_log.isWarnEnabled()) {
					_log.warn("Unable to load class " + type);
				}

				continue;
			}

			String className = clazz.getName();

			if (className.endsWith("LocalServiceImpl")) {
				continue;
			}

			JavadocClass javadocClass = parseJavadocClass(
				servletContextName, javadocElement, clazz);

			_javadocClasses.put(clazz, javadocClass);

			List<Element> methodElements = javadocElement.elements("method");

			for (Element methodElement : methodElements) {
				try {
					JavadocMethod javadocMethod = parseJavadocMethod(
						servletContextName, clazz, methodElement);

					if (javadocMethod == null) {
						continue;
					}

					_javadocMethods.put(
						javadocMethod.getMethod(), javadocMethod);
				}
				catch (Exception e) {
					String methodName = methodElement.elementText("name");

					if (_log.isWarnEnabled()) {
						_log.warn(
							"Unable to load method " + methodName +
								" from class " + type);
					}
				}
			}
		}
	}

	protected JavadocClass parseJavadocClass(
		String servletContextName, Element javadocElement, Class<?> clazz) {

		JavadocClass javadocClass = new JavadocClass(
			servletContextName, javadocElement.elementText("comment"), clazz);

		List<Element> authorElements = javadocElement.elements("author");

		String[] authors = new String[authorElements.size()];

		for (int i = 0; i < authorElements.size(); i++) {
			Element authorElement = authorElements.get(i);

			authors[i] = authorElement.getText();
		}

		javadocClass.setAuthors(authors);

		return javadocClass;
	}

	protected JavadocMethod parseJavadocMethod(
			String servletContextName, Class<?> clazz, Element methodElement)
		throws Exception {

		String name = methodElement.elementText("name");

		if (name.equals(clazz.getSimpleName()) ||
			name.startsWith(StringPool.UNDERLINE)) {

			return null;
		}

		List<Element> paramElements = methodElement.elements("param");

		Class<?>[] parameterTypeClasses = new Class<?>[paramElements.size()];
		String[] parameterComments = new String[paramElements.size()];

		for (int i = 0; i < paramElements.size(); i++) {
			Element paramElement = paramElements.get(i);

			String parameterType = paramElement.elementText("type");

			Class<?> parametarTypeClass = JavadocUtil.loadClass(
				clazz.getClassLoader(), parameterType);

			parameterTypeClasses[i] = parametarTypeClass;

			String parameterComment = paramElement.elementText("comment");

			parameterComments[i] = parameterComment;
		}

		Method method = clazz.getDeclaredMethod(name, parameterTypeClasses);

		String comment = methodElement.elementText("comment");

		if (Validator.isNull(comment)) {
			return new EmptyJavadocMethod(servletContextName, method);
		}

		JavadocMethodImpl javadocMethodImpl = new JavadocMethodImpl(
			servletContextName, comment, method);

		javadocMethodImpl.setParameterComments(parameterComments);

		Element returnElement = methodElement.element("return");

		if (returnElement != null) {
			String returnComment = returnElement.elementText("comment");

			javadocMethodImpl.setReturnComment(returnComment);
		}

		List<Element> throwsElements = methodElement.elements("throws");

		String[] throwsComments = new String[throwsElements.size()];

		for (int i = 0; i < throwsElements.size(); i++) {
			Element throwElement = throwsElements.get(i);

			throwsComments[i] = throwElement.elementText("comment");
		}

		javadocMethodImpl.setThrowsComments(throwsComments);

		return javadocMethodImpl;
	}

	protected void unload(
		String servletContextName,
		Collection<? extends BaseJavadoc> collection) {

		Iterator<? extends BaseJavadoc> iterator = collection.iterator();

		while (iterator.hasNext()) {
			BaseJavadoc javadoc = iterator.next();

			if (servletContextName.equals(javadoc.getServletContextName())) {
				iterator.remove();
			}
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		JavadocManagerImpl.class);

	private final Map<Class<?>, JavadocClass> _javadocClasses = new HashMap<>();
	private final Map<Method, JavadocMethod> _javadocMethods = new HashMap<>();

}