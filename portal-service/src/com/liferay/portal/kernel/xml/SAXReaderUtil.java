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

package com.liferay.portal.kernel.xml;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.security.pacl.permission.PortalRuntimePermission;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;

import java.io.File;
import java.io.InputStream;
import java.io.Reader;

import java.net.MalformedURLException;
import java.net.URL;

import java.util.List;
import java.util.Map;

/**
 * @author Brian Wing Shun Chan
 */
public class SAXReaderUtil {

	public static Attribute createAttribute(
		Element element, QName qName, String value) {

		return getSecureSAXReader().createAttribute(element, qName, value);
	}

	public static Attribute createAttribute(
		Element element, String name, String value) {

		return getSecureSAXReader().createAttribute(element, name, value);
	}

	public static Document createDocument() {
		return getSecureSAXReader().createDocument();
	}

	public static Document createDocument(Element rootElement) {
		return getSecureSAXReader().createDocument(rootElement);
	}

	public static Document createDocument(String encoding) {
		return getSecureSAXReader().createDocument(encoding);
	}

	public static Element createElement(QName qName) {
		return getSecureSAXReader().createElement(qName);
	}

	public static Element createElement(String name) {
		return getSecureSAXReader().createElement(name);
	}

	public static Entity createEntity(String name, String text) {
		return getSecureSAXReader().createEntity(name, text);
	}

	public static Namespace createNamespace(String uri) {
		return getSecureSAXReader().createNamespace(uri);
	}

	public static Namespace createNamespace(String prefix, String uri) {
		return getSecureSAXReader().createNamespace(prefix, uri);
	}

	public static ProcessingInstruction createProcessingInstruction(
		String target, Map<String, String> data) {

		return getSecureSAXReader().createProcessingInstruction(target, data);
	}

	public static ProcessingInstruction createProcessingInstruction(
		String target, String data) {

		return getSecureSAXReader().createProcessingInstruction(target, data);
	}

	public static QName createQName(String localName) {
		return getSecureSAXReader().createQName(localName);
	}

	public static QName createQName(String localName, Namespace namespace) {
		return getSecureSAXReader().createQName(localName, namespace);
	}

	public static Text createText(String text) {
		return getSecureSAXReader().createText(text);
	}

	public static XPath createXPath(String xPathExpression) {
		return getSecureSAXReader().createXPath(xPathExpression);
	}

	public static XPath createXPath(
		String xPathExpression, Map<String, String> namespaceContextMap) {

		return getSecureSAXReader().createXPath(
			xPathExpression, namespaceContextMap);
	}

	public static XPath createXPath(
		String xPathExpression, String prefix, String namespace) {

		return getSecureSAXReader().createXPath(
			xPathExpression, prefix, namespace);
	}

	/**
	 * @deprecated As of 6.2.0, renamed to {@link #getSecureSAXReader}
	 */
	public static SAXReader getSAXReader() {
		return getSecureSAXReader();
	}

	public static SAXReader getSecureSAXReader() {
		PortalRuntimePermission.checkGetBeanProperty(SAXReaderUtil.class);

		if (isCallerWhitelisted()) {
			return getUnsecureSAXReader();
		}

		return _saxReader;
	}

	public static SAXReader getUnsecureSAXReader() {
		PortalRuntimePermission.checkGetBeanProperty(
			SAXReaderUtil.class, "unsecureSAXReader");

		return _unsecureSAXReader;
	}

	public static Document read(File file) throws DocumentException {
		return getSecureSAXReader().read(file);
	}

	public static Document read(File file, boolean validate)
		throws DocumentException {

		return getSecureSAXReader().read(file, validate);
	}

	public static Document read(InputStream is) throws DocumentException {
		return getSecureSAXReader().read(is);
	}

	public static Document read(InputStream is, boolean validate)
		throws DocumentException {

		return getSecureSAXReader().read(is, validate);
	}

	public static Document read(Reader reader) throws DocumentException {
		return getSecureSAXReader().read(reader);
	}

	public static Document read(Reader reader, boolean validate)
		throws DocumentException {

		return getSecureSAXReader().read(reader, validate);
	}

	public static Document read(String xml) throws DocumentException {
		return getSecureSAXReader().read(xml);
	}

	public static Document read(String xml, boolean validate)
		throws DocumentException {

		return getSecureSAXReader().read(xml, validate);
	}

	public static Document read(String xml, XMLSchema xmlSchema)
		throws DocumentException {

		return getSecureSAXReader().read(xml, xmlSchema);
	}

	public static Document read(URL url) throws DocumentException {
		return getSecureSAXReader().read(url);
	}

	public static Document read(URL url, boolean validate)
		throws DocumentException {

		return getSecureSAXReader().read(url, validate);
	}

	public static Document readURL(String url)
		throws DocumentException, MalformedURLException {

		return getSecureSAXReader().readURL(url);
	}

	public static Document readURL(String url, boolean validate)
		throws DocumentException, MalformedURLException {

		return getSecureSAXReader().readURL(url, validate);
	}

	public static List<Node> selectNodes(
		String xPathFilterExpression, List<Node> nodes) {

		return getSecureSAXReader().selectNodes(xPathFilterExpression, nodes);
	}

	public static List<Node> selectNodes(
		String xPathFilterExpression, Node node) {

		return getSecureSAXReader().selectNodes(xPathFilterExpression, node);
	}

	public static void sort(List<Node> nodes, String xPathExpression) {
		getSecureSAXReader().sort(nodes, xPathExpression);
	}

	public static void sort(
		List<Node> nodes, String xPathExpression, boolean distinct) {

		getSecureSAXReader().sort(nodes, xPathExpression, distinct);
	}

	public void setSecureSAXReader(SAXReader saxReader) {
		PortalRuntimePermission.checkSetBeanProperty(getClass());

		_saxReader = saxReader;
	}

	public void setUnsecureSAXReader(SAXReader unsecureSAXReader) {
		PortalRuntimePermission.checkSetBeanProperty(
			getClass(), "unsecureSAXReader");

		_unsecureSAXReader = unsecureSAXReader;
	}

	protected static boolean isCallerWhitelisted() {
		StringBundler sb = new StringBundler(3);

		Exception e = new Exception();

		StackTraceElement[] stackTraceElements = e.getStackTrace();

		StackTraceElement stackTraceElement = stackTraceElements[2];

		String methodName = stackTraceElement.getMethodName();

		if (!methodName.startsWith("read")) {
			return false;
		}

		stackTraceElement = stackTraceElements[3];

		sb.append(stackTraceElement.getClassName());
		sb.append(StringPool.POUND);
		sb.append(stackTraceElement.getMethodName());

		String callerSignature = sb.toString();

		for (String whitelistSignature : _XML_SECURITY_WHITELIST) {
			if (callerSignature.startsWith(whitelistSignature)) {
				if (_log.isDebugEnabled()) {
					_log.debug(
						"Unsecure SAX reader allowed for " + callerSignature +
							" based on the \"" + whitelistSignature +
								"\" whitelist");
				}

				return true;
			}
		}

		if (_log.isDebugEnabled()) {
			_log.debug("Unsecure SAX reader disallowed for " + callerSignature);
		}

		return false;
	}

	private static Log _log = LogFactoryUtil.getLog(SAXReaderUtil.class);

	private static final String[] _XML_SECURITY_WHITELIST = PropsUtil.getArray(
		PropsKeys.XML_SECURITY_WHITELIST);

	private static SAXReader _saxReader;
	private static SAXReader _unsecureSAXReader;

}