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

package com.liferay.xsl.content.web.internal.util;

import com.liferay.portal.kernel.io.unsync.UnsyncByteArrayOutputStream;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.HttpUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.xsl.content.web.internal.configuration.XSLContentConfiguration;

import java.io.ByteArrayInputStream;

import java.net.URL;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;

/**
 * @author Brian Wing Shun Chan
 * @author Samuel Kong
 */
public class XSLContentUtil {

	public static final String DEFAULT_XML_URL = "/example.xml";

	public static final String DEFAULT_XSL_URL = "/example.xsl";

	public static String replaceUrlTokens(
		ThemeDisplay themeDisplay, String contextPath, String url) {

		return StringUtil.replace(
			url, new String[] {"@portal_url@", "@portlet_context_url@"},
			new String[] {
				themeDisplay.getPortalURL(),
				themeDisplay.getPortalURL() + contextPath
			});
	}

	public static String transform(
			XSLContentConfiguration xslContentConfiguration, URL xmlUrl,
			URL xslUrl)
		throws Exception {

		TransformerFactory transformerFactory = getTransformerFactory(
			xslContentConfiguration);

		DocumentBuilder documentBuilder = getDocumentBuilder(
			xslContentConfiguration);

		Transformer transformer = transformerFactory.newTransformer(
			getXslSource(documentBuilder, xslUrl));

		UnsyncByteArrayOutputStream unsyncByteArrayOutputStream =
			new UnsyncByteArrayOutputStream();

		transformer.transform(
			getXmlSource(documentBuilder, xmlUrl),
			new StreamResult(unsyncByteArrayOutputStream));

		return unsyncByteArrayOutputStream.toString();
	}

	protected static DocumentBuilder getDocumentBuilder(
			XSLContentConfiguration xslContentConfiguration)
		throws Exception {

		DocumentBuilderFactory documentBuilderFactory =
			DocumentBuilderFactory.newInstance();

		documentBuilderFactory.setFeature(
			"http://apache.org/xml/features/disallow-doctype-decl",
			!xslContentConfiguration.xmlDoctypeDeclarationAllowed());
		documentBuilderFactory.setFeature(
			"http://xml.org/sax/features/external-general-entities",
			xslContentConfiguration.xmlExternalGeneralEntitiesAllowed());
		documentBuilderFactory.setFeature(
			"http://xml.org/sax/features/external-parameter-entities",
			xslContentConfiguration.xmlExternalGeneralEntitiesAllowed());

		documentBuilderFactory.setNamespaceAware(true);

		return documentBuilderFactory.newDocumentBuilder();
	}

	protected static TransformerFactory getTransformerFactory(
			XSLContentConfiguration xslContentConfiguration)
		throws Exception {

		TransformerFactory transformerFactory =
			TransformerFactory.newInstance();

		transformerFactory.setFeature(
			XMLConstants.FEATURE_SECURE_PROCESSING,
			xslContentConfiguration.xslSecureProcessingEnabled());

		return transformerFactory;
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