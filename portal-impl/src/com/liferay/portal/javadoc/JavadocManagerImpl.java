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

package com.liferay.portal.javadoc;

import com.liferay.portal.kernel.javadoc.BaseJavadoc;
import com.liferay.portal.kernel.javadoc.JavadocConstructor;
import com.liferay.portal.kernel.javadoc.JavadocManager;
import com.liferay.portal.kernel.javadoc.JavadocMethod;
import com.liferay.portal.kernel.javadoc.JavadocType;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.StreamUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.xml.Document;
import com.liferay.portal.kernel.xml.Node;
import com.liferay.portal.kernel.xml.SAXReaderUtil;

import java.io.InputStream;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

import java.net.URL;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * @author Igor Spasic
 */
public class JavadocManagerImpl implements JavadocManager {

	public void load(String servletContextName, ClassLoader classLoader) {
		if (_log.isDebugEnabled()) {
			_log.debug("Loading javadocs.xml");
		}

		Document document = _loadJavadocsXmlFile(classLoader);

		if (document == null) {
			return;
		}

		_parseJavadocsDocument(document, servletContextName, classLoader);

		if (_log.isInfoEnabled()) {
			_log.info("Javadocs loaded for: " + servletContextName);
		}
	}

	public JavadocMethod lookupJavadoc(Method method) {
		return _javadocMethods.get(method);
	}

	public JavadocConstructor lookupJavadoc(Constructor constructor) {
		return _javadocConstructors.get(constructor);
	}

	public JavadocType lookupJavadoc(Class type) {
		return _javadocTypes.get(type);
	}

	public JavadocMethod lookupServiceUtilMethodJavadoc(Method method) {
		String implClassName = method.getDeclaringClass().getName();

		implClassName =
			StringUtil.replace(implClassName, "ServiceUtil", "ServiceImpl");

		implClassName =
			StringUtil.replace(implClassName, "service.", "service.impl.");

		try {
			Class methodDeclaringClass = method.getDeclaringClass();

			ClassLoader methodDeclaringClassClassLoader
				= methodDeclaringClass.getClassLoader();

			Class implClass = JavadocUtil.loadClass(
				implClassName, methodDeclaringClassClassLoader);

			method = implClass.getMethod(
				method.getName(), method.getParameterTypes());

			return lookupJavadoc(method);
		}
		catch (Exception e) {
			if (_log.isWarnEnabled()) {
				_log.warn(
					"Implementation class " + implClassName + " not found", e);
			}
		}

		return null;
	}

	public void unload(String servletContextName) {

		Iterator<? extends BaseJavadoc> iterator = null;

		iterator = _javadocMethods.values().iterator();

		while (iterator.hasNext()) {
			BaseJavadoc javadoc = iterator.next();

			if (javadoc.getServletContextName().equals(servletContextName)) {
				iterator.remove();
			}
		}

		iterator = _javadocConstructors.values().iterator();

		while (iterator.hasNext()) {
			BaseJavadoc javadoc = iterator.next();

			if (javadoc.getServletContextName().equals(servletContextName)) {
				iterator.remove();
			}
		}

		iterator = _javadocTypes.values().iterator();

		while (iterator.hasNext()) {
			BaseJavadoc javadoc = iterator.next();

			if (javadoc.getServletContextName().equals(servletContextName)) {
				iterator.remove();
			}
		}

		if (_log.isInfoEnabled()) {
			_log.info("Javadocs unloaded for: " + servletContextName);
		}
	}

	private String _getChildNodeText(Node rootNode, String childNodeName) {
		Node childNode = rootNode.selectSingleNode(childNodeName);

		if (childNode == null) {
			return null;
		}

		String text = childNode.getText();

		if (text == null) {
			return null;
		}

		text = text.trim();

		if (text.length() == 0) {
			return null;
		}

		return text;
	}

	private Document _loadJavadocsXmlFile(ClassLoader classLoader) {

		InputStream inputStream = null;

		try {
			URL javadocsUrl =
				classLoader.getResource("META-INF/javadocs.xml");

			if (javadocsUrl == null) {
				if (_log.isInfoEnabled()) {
					_log.info("Javadocs not available.");
				}
				return null;
			}

			inputStream = javadocsUrl.openStream();

			return SAXReaderUtil.read(inputStream, true);
		}
		catch (Exception e) {
			_log.error(e, e);
		}
		finally {
			StreamUtil.cleanUp(inputStream);
		}
		return null;
	}

	private JavadocConstructor _parseJavadocConstructor(
		Class clazz, Node ctorNode) throws ClassNotFoundException {

		String ctorComment = _getChildNodeText(ctorNode, "comment");

		List<Node> paramNodes = ctorNode.selectNodes("param");

		String[] parametersComments = new String[paramNodes.size()];
		Class<?>[] parameterTypes = new Class<?>[paramNodes.size()];

		int index = 0;

		for (Node paramNode : paramNodes) {

			String parameterComment = _getChildNodeText(paramNode, "comment");
			String parameterTypeName = _getChildNodeText(paramNode, "type");

			Class parametarType = JavadocUtil.loadClass(
				parameterTypeName, clazz.getClassLoader());

			parameterTypes[index] = parametarType;
			parametersComments[index] = parameterComment;

			index++;
		}

		Constructor constructor = null;
		try {
			constructor = clazz.getDeclaredConstructor(parameterTypes);
		}
		catch (NoSuchMethodException nsmex) {
			if (_log.isWarnEnabled()) {
				_log.warn("Constructor not found: " + nsmex.getMessage());
			}
		}

		JavadocConstructor javadocConstructor =
			new JavadocConstructor(constructor, ctorComment);

		javadocConstructor.setParametersComments(parametersComments);
		javadocConstructor.setThrowsComments(_parseThrowsComments(ctorNode));

		return javadocConstructor;
	}

	private JavadocMethod _parseJavadocMethod(Class clazz, Node methodNode)
		throws ClassNotFoundException {

		String methodName = _getChildNodeText(methodNode, "name");
		String methodComment = _getChildNodeText(methodNode, "comment");
		String methodReturnComment = _getChildNodeText(methodNode, "return");

		List<Node> paramNodes = methodNode.selectNodes("param");

		String[] parametersComments = new String[paramNodes.size()];
		Class<?>[] parameterTypes = new Class<?>[paramNodes.size()];

		int index = 0;

		for (Node paramNode : paramNodes) {

			String parameterComment = _getChildNodeText(paramNode, "comment");
			String parameterTypeName = _getChildNodeText(paramNode, "type");

			Class parametarType = JavadocUtil.loadClass(
				parameterTypeName, clazz.getClassLoader());

			parameterTypes[index] = parametarType;
			parametersComments[index] = parameterComment;

			index++;
		}

		Method method = null;
		try {
			method = clazz.getDeclaredMethod(methodName, parameterTypes);
		}
		catch (NoSuchMethodException nsmex) {
			if (_log.isWarnEnabled()) {
				_log.warn("Method not found: " + nsmex.getMessage());
			}
		}

		JavadocMethod javadocMethod = new JavadocMethod(method, methodComment);

		javadocMethod.setParametersComments(parametersComments);
		javadocMethod.setReturnComment(methodReturnComment);
		javadocMethod.setThrowsComments(_parseThrowsComments(methodNode));

		return javadocMethod;
	}

	private JavadocType _parseJavadocType(Class type, Node typeNode) {
		String typeComment = _getChildNodeText(typeNode, "comment");

		List<Node> authorNodeList = typeNode.selectNodes("author");

		String[] authors = new String[authorNodeList.size()];

		int index = 0;

		for (Node author : authorNodeList) {

			authors[index] = author.getText();

			index++;
		}

		JavadocType javadocType = new JavadocType(type, typeComment);

		javadocType.setAuthors(authors);

		return javadocType;
	}

	private void _parseJavadocsDocument(
		Document document, String servletContextName, ClassLoader classLoader) {

		List<Node> nodeList = document.selectNodes("/javadocs/javadoc");

		for (Node javadocNode : nodeList) {
			String typeName = _getChildNodeText(javadocNode, "type");

			Class type = null;
			try {
				type = JavadocUtil.loadClass(typeName, classLoader);
			}
			catch (ClassNotFoundException e) {
				if (_log.isWarnEnabled()) {
					_log.warn(typeName + " not found.", e);
				}
			}

			JavadocType javadocType = _parseJavadocType(type, javadocNode);

			javadocType.setServletContextName(servletContextName);

			_javadocTypes.put(type, javadocType);

			List<Node> methodNodeList = javadocNode.selectNodes("method");

			for (Node methodNode : methodNodeList) {

				String methodName = _getChildNodeText(methodNode, "name");

				try {
					if (type.getSimpleName().equals(methodName)) {

						JavadocConstructor javadocConstructor =
							_parseJavadocConstructor(type, methodNode);

						javadocConstructor.setServletContextName(
							servletContextName);

						_javadocConstructors.put(
							javadocConstructor.getConstructor(),
							javadocConstructor);
					}
					else {
						JavadocMethod javadocMethod =
							_parseJavadocMethod(type, methodNode);

						javadocMethod.setServletContextName(
							servletContextName);

						_javadocMethods.put(
							javadocMethod.getMethod(), javadocMethod);
					}
				}
				catch (Exception e) {
					if (_log.isWarnEnabled()) {
						_log.warn(
							typeName + '#' + methodName + " not found.", e);
					}
				}
			}
		}
	}

	private String[] _parseThrowsComments(Node methodNode) {
		List<Node> throwsNodes = methodNode.selectNodes("throws");

		if ((throwsNodes == null) || throwsNodes.isEmpty()) {
			return null;
		}

		String[] throwsComments = new String[throwsNodes.size()];

		int index = 0;

		for (Node throwNode : throwsNodes) {
			throwsComments[index] = _getChildNodeText(throwNode, "comment");
			index++;
		}

		return throwsComments;
	}

	private static Log _log = LogFactoryUtil.getLog(JavadocManager.class);
	private Map<Constructor, JavadocConstructor> _javadocConstructors =
		new HashMap<Constructor, JavadocConstructor>();
	private Map<Method, JavadocMethod> _javadocMethods =
		new HashMap<Method, JavadocMethod>();
	private Map<Class, JavadocType> _javadocTypes =
		new HashMap<Class, JavadocType>();

}