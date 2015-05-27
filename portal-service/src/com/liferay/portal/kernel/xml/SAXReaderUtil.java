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

package com.liferay.portal.kernel.xml;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.security.pacl.permission.PortalRuntimePermission;
import com.liferay.portal.kernel.util.GetterUtil;
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
@ProviderType
public class SAXReaderUtil {

	public static Attribute createAttribute(
		Element element, QName qName, String value) {

		return getSAXReader().createAttribute(element, qName, value);
	}

	public static Attribute createAttribute(
		Element element, String name, String value) {

		return getSAXReader().createAttribute(element, name, value);
	}

	public static Document createDocument() {
		return getSAXReader().createDocument();
	}

	public static Document createDocument(Element rootElement) {
		return getSAXReader().createDocument(rootElement);
	}

	public static Document createDocument(String encoding) {
		return getSAXReader().createDocument(encoding);
	}

	public static Element createElement(QName qName) {
		return getSAXReader().createElement(qName);
	}

	public static Element createElement(String name) {
		return getSAXReader().createElement(name);
	}

	public static Entity createEntity(String name, String text) {
		return getSAXReader().createEntity(name, text);
	}

	public static Namespace createNamespace(String uri) {
		return getSAXReader().createNamespace(uri);
	}

	public static Namespace createNamespace(String prefix, String uri) {
		return getSAXReader().createNamespace(prefix, uri);
	}

	public static ProcessingInstruction createProcessingInstruction(
		String target, Map<String, String> data) {

		return getSAXReader().createProcessingInstruction(target, data);
	}

	public static ProcessingInstruction createProcessingInstruction(
		String target, String data) {

		return getSAXReader().createProcessingInstruction(target, data);
	}

	public static QName createQName(String localName) {
		return getSAXReader().createQName(localName);
	}

	public static QName createQName(String localName, Namespace namespace) {
		return getSAXReader().createQName(localName, namespace);
	}

	public static Text createText(String text) {
		return getSAXReader().createText(text);
	}

	public static XPath createXPath(String xPathExpression) {
		return getSAXReader().createXPath(xPathExpression);
	}

	public static XPath createXPath(
		String xPathExpression, Map<String, String> namespaceContextMap) {

		return getSAXReader().createXPath(xPathExpression, namespaceContextMap);
	}

	public static XPath createXPath(
		String xPathExpression, String prefix, String namespace) {

		return getSAXReader().createXPath(xPathExpression, prefix, namespace);
	}

	public static SAXReader getSAXReader() {
		PortalRuntimePermission.checkGetBeanProperty(SAXReaderUtil.class);

		if (isCallerWhitelisted()) {
			return UnsecureSAXReaderUtil.getSAXReader();
		}

		return _saxReader;
	}

	public static Document read(File file) throws DocumentException {
		return getSAXReader().read(file);
	}

	public static Document read(File file, boolean validate)
		throws DocumentException {

		return getSAXReader().read(file, validate);
	}

	public static Document read(InputStream is) throws DocumentException {
		return getSAXReader().read(is);
	}

	public static Document read(InputStream is, boolean validate)
		throws DocumentException {

		return getSAXReader().read(is, validate);
	}

	public static Document read(Reader reader) throws DocumentException {
		return getSAXReader().read(reader);
	}

	public static Document read(Reader reader, boolean validate)
		throws DocumentException {

		return getSAXReader().read(reader, validate);
	}

	public static Document read(String xml) throws DocumentException {
		return getSAXReader().read(xml);
	}

	public static Document read(String xml, boolean validate)
		throws DocumentException {

		return getSAXReader().read(xml, validate);
	}

	public static Document read(String xml, XMLSchema xmlSchema)
		throws DocumentException {

		return getSAXReader().read(xml, xmlSchema);
	}

	public static Document read(URL url) throws DocumentException {
		return getSAXReader().read(url);
	}

	public static Document read(URL url, boolean validate)
		throws DocumentException {

		return getSAXReader().read(url, validate);
	}

	public static Document readURL(String url)
		throws DocumentException, MalformedURLException {

		return getSAXReader().readURL(url);
	}

	public static Document readURL(String url, boolean validate)
		throws DocumentException, MalformedURLException {

		return getSAXReader().readURL(url, validate);
	}

	public static List<Node> selectNodes(
		String xPathFilterExpression, List<Node> nodes) {

		return getSAXReader().selectNodes(xPathFilterExpression, nodes);
	}

	public static List<Node> selectNodes(
		String xPathFilterExpression, Node node) {

		return getSAXReader().selectNodes(xPathFilterExpression, node);
	}

	public static void sort(List<Node> nodes, String xPathExpression) {
		getSAXReader().sort(nodes, xPathExpression);
	}

	public static void sort(
		List<Node> nodes, String xPathExpression, boolean distinct) {

		getSAXReader().sort(nodes, xPathExpression, distinct);
	}

	public void setSAXReader(SAXReader saxReader) {
		PortalRuntimePermission.checkSetBeanProperty(getClass());

		_saxReader = saxReader;
	}

	protected static boolean isCallerWhitelisted() {
		if (!_XML_SECURITY_ENABLED) {
			return true;
		}

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

	private static final boolean _XML_SECURITY_ENABLED = GetterUtil.getBoolean(
		PropsUtil.get(PropsKeys.XML_SECURITY_ENABLED));

	private static final String[] _XML_SECURITY_WHITELIST = PropsUtil.getArray(
		PropsKeys.XML_SECURITY_WHITELIST);

	private static final Log _log = LogFactoryUtil.getLog(SAXReaderUtil.class);

	private static SAXReader _saxReader;

}