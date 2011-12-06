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

import com.liferay.portal.kernel.javadoc.JavadocManager;
import com.liferay.portal.kernel.javadoc.JavadocMethod;
import com.liferay.portal.kernel.javadoc.JavadocType;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.StreamUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.xml.Document;
import com.liferay.portal.kernel.xml.Node;
import com.liferay.portal.kernel.xml.SAXReaderUtil;

import java.io.InputStream;

import java.lang.reflect.Method;

import java.net.URL;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Igor Spasic
 */
public class JavadocManagerImpl implements JavadocManager {

	public void init() {

		Document document = _loadJavadocsXmlFile();

		javadocMethods = new HashMap<Method, JavadocMethod>();
		javadocTypes = new HashMap<Class, JavadocType>();

		if (document == null) {
			return;
		}

		if (_log.isDebugEnabled()) {
			_log.debug("Parsing javadocs.xml");
		}

		_parseJavadocsDocument(document);

		_log.debug("Javadocs ready.");
	}

	public JavadocMethod lookupMethodJavadoc(Method method) {
		if (javadocMethods == null) {
			init();
		}
		return javadocMethods.get(method);
	}

	public JavadocMethod lookupServiceUtilMethodJavadoc(Method method) {
		String implClassName = method.getDeclaringClass().getName();

		implClassName =
			StringUtil.replace(implClassName, "ServiceUtil", "ServiceImpl");

		implClassName =
			StringUtil.replace(implClassName, "service.", "service.impl.");

		try {
			Thread currentThread = Thread.currentThread();

			ClassLoader classLoader = currentThread.getContextClassLoader();

			Class implClass = classLoader.loadClass(implClassName);

			method = implClass.getMethod(
				method.getName(), method.getParameterTypes());

			return lookupMethodJavadoc(method);
		}
		catch (Exception e) {
			if (_log.isWarnEnabled()) {
				_log.warn(
					"Implementation class " + implClassName + " not found", e);
			}
		}

		return null;
	}

	public JavadocType lookupTypeJavadoc(Class type) {
		if (javadocTypes == null) {
			init();
		}
		return javadocTypes.get(type);
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

	private Document _loadJavadocsXmlFile() {

		InputStream inputStream = null;

		try {
			URL javadocsUrl =
				this.getClass().getResource("/META-INF/javadocs.xml");

			inputStream = javadocsUrl.openStream();

			if (_log.isDebugEnabled()) {
				_log.debug("Loading javadocs.xml");
			}

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

	private Class _loadPrimitiveClass(String type) {
		if (type == null) {
			return null;
		}

		if (type.indexOf('[') != -1) {

			if (type.equals("long[]")) {
				return long[].class;
			}
			else if (type.equals("int[]")) {
				return int[].class;
			}
			else if (type.equals("byte[]")) {
				return byte[].class;
			}
			else if (type.equals("short[]")) {
				return short[].class;
			}
			else if (type.equals("double[]")) {
				return double[].class;
			}
			else if (type.equals("float[]")) {
				return float[].class;
			}
			else if (type.equals("boolean[]")) {
				return boolean[].class;
			} else {
				return null;
			}
		}

		if (type.equals("long")) {
			return long.class;
		}
		else if (type.equals("int")) {
			return int.class;
		}
		else if (type.equals("byte")) {
			return byte.class;
		}
		else if (type.equals("short")) {
			return short.class;
		}
		else if (type.equals("double")) {
			return double.class;
		}
		else if (type.equals("float")) {
			return float.class;
		}
		else if (type.equals("boolean")) {
			return boolean.class;
		} else {
			return null;
		}
	}

	private JavadocMethod _parseJavadocMethod(Class clazz, Node methodNode)
		throws ClassNotFoundException, NoSuchMethodException {

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

			Class parametarType = _loadPrimitiveClass(parameterTypeName);

			if (parametarType == null) {
				int bracketCount = StringUtil.count(
					parameterTypeName, StringPool.OPEN_BRACKET);

				if (bracketCount > 0) {
					char[] brackets = new char[bracketCount];
					for (int i = 0; i < brackets.length; i++) {
						brackets[i] = '[';
					}

					int bracketIndex = parameterTypeName.indexOf('[');

					parameterTypeName = new String(brackets) + "L" +
						parameterTypeName.substring(0, bracketIndex) + ";";
				}

				ClassLoader classLoader = clazz.getClassLoader();

				parametarType = classLoader.loadClass(parameterTypeName);
			}

			parameterTypes[index] = parametarType;
			parametersComments[index] = parameterComment;

			index++;
		}

		Method method = clazz.getDeclaredMethod(methodName, parameterTypes);

		JavadocMethod javadocMethod = new JavadocMethod(method, methodComment);

		javadocMethod.setParametersComments(parametersComments);
		javadocMethod.setReturnComment(methodReturnComment);
		javadocMethod.setThrowsComments(_parseThrowsComments(methodNode));

		return javadocMethod;
	}

	private void _parseJavadocType(Class type, Node typeNode) {
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

		javadocTypes.put(type, javadocType);
	}

	private void _parseJavadocsDocument(Document document) {

		List<Node> nodeList = document.selectNodes("/javadocs/javadoc");

		for (Node javadocNode : nodeList) {
			String typeName = _getChildNodeText(javadocNode, "type");

			Class type = null;
			try {
				Thread currentThread = Thread.currentThread();

				ClassLoader classLoader = currentThread.getContextClassLoader();

				type = classLoader.loadClass(typeName);
			}
			catch (ClassNotFoundException e) {
				if (_log.isWarnEnabled()) {
					_log.warn(typeName + " not found.", e);
				}
			}

			_parseJavadocType(type, javadocNode);

			List<Node> methodNodeList = javadocNode.selectNodes("method");

			for (Node methodNode : methodNodeList) {
				try {
					JavadocMethod javadocMethod =
						_parseJavadocMethod(type, methodNode);

					javadocMethods.put(
						javadocMethod.getMethod(), javadocMethod);
				}
				catch (Exception e) {
					if (_log.isWarnEnabled()) {
						String methodName =
							_getChildNodeText(methodNode, "name");

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

	private Map<Method, JavadocMethod> javadocMethods;
	private Map<Class, JavadocType> javadocTypes;

}