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

package com.liferay.portlet.xslcontent.util;

import com.liferay.portal.kernel.io.unsync.UnsyncByteArrayOutputStream;
import com.liferay.portal.kernel.util.HttpUtil;
import com.liferay.portal.util.PropsValues;

import java.io.ByteArrayInputStream;

import java.net.URL;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;

/**
 * @author Brian Wing Shun Chan
 * @author Samuel Kong
 */
public class XSLContentUtil {

	public static final String DEFAULT_XML_URL =
		"@portal_url@/html/portlet/xsl_content/example.xml";

	public static final String DEFAULT_XSL_URL =
		"@portal_url@/html/portlet/xsl_content/example.xsl";

	public static String transform(URL xmlUrl, URL xslUrl) throws Exception {
		DocumentBuilderFactory documentBuilderFactory =
			DocumentBuilderFactory.newInstance();

		documentBuilderFactory.setFeature(
			"http://apache.org/xml/features/disallow-doctype-decl",
			PropsValues.XSL_CONTENT_XML_DOCTYPE_DECLARATION_ALLOWED);
		documentBuilderFactory.setFeature(
			"http://xml.org/sax/features/external-general-entities",
			PropsValues.XSL_CONTENT_XML_EXTERNAL_GENERAL_ENTITIES_ALLOWED);
		documentBuilderFactory.setFeature(
			"http://xml.org/sax/features/external-parameter-entities",
			PropsValues.XSL_CONTENT_XML_EXTERNAL_PARAMETER_ENTITIES_ALLOWED);

		documentBuilderFactory.setNamespaceAware(true);

		DocumentBuilder documentBuilder =
			documentBuilderFactory.newDocumentBuilder();

		TransformerFactory transformerFactory =
			TransformerFactory.newInstance();

		try {
			transformerFactory.setFeature(
				XMLConstants.FEATURE_SECURE_PROCESSING,
				PropsValues.XSL_CONTENT_XSL_SECURE_PROCESSING_ENABLED);
		}
		catch (TransformerConfigurationException tce) {
		}

		Transformer transformer = transformerFactory.newTransformer(
			getXslSource(documentBuilder, xslUrl));

		UnsyncByteArrayOutputStream unsyncByteArrayOutputStream =
			new UnsyncByteArrayOutputStream();

		transformer.transform(
			getXmlSource(documentBuilder, xmlUrl),
			new StreamResult(unsyncByteArrayOutputStream));

		return unsyncByteArrayOutputStream.toString();
	}

	protected static Source getXmlSource(
			DocumentBuilder documentBuilder, URL xmlUrl)
		throws Exception {

		String xml = HttpUtil.URLtoString(xmlUrl);

		Document xmlDocument = documentBuilder.parse(
			new ByteArrayInputStream(xml.getBytes()));

		return new DOMSource(xmlDocument);
	}

	protected static Source getXslSource(
			DocumentBuilder documentBuilder, URL xslUrl)
		throws Exception {

		String xsl = HttpUtil.URLtoString(xslUrl);

		Document xslDocument = documentBuilder.parse(
			new ByteArrayInputStream(xsl.getBytes()));

		return new DOMSource(xslDocument);
	}

}