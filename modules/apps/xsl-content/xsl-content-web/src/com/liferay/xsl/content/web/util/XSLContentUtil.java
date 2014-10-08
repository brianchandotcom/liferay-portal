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

package com.liferay.xsl.content.web.util;

import com.liferay.portal.kernel.io.unsync.UnsyncByteArrayOutputStream;
import com.liferay.portal.kernel.util.HttpUtil;
import com.liferay.xsl.content.web.configuration.XSLContentConfiguration;

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

	public static String transform(
			URL xmlUrl, URL xslUrl,
			XSLContentConfiguration xslContentConfiguration)
		throws Exception {

		String xml = HttpUtil.URLtoString(xmlUrl);
		String xsl = HttpUtil.URLtoString(xslUrl);

		DocumentBuilderFactory documentBuilderFactory =
			DocumentBuilderFactory.newInstance();

		documentBuilderFactory.setFeature(
			_FEATURE_EXTERNAL_GENERAL_ENTITIES,
			xslContentConfiguration.isXmlExternalGeneralEntitiesAllowed());
		documentBuilderFactory.setFeature(
			_FEATURE_EXTERNAL_PARAMETER_ENTITIES,
			xslContentConfiguration.isXmlExternalGeneralEntitiesAllowed());
		documentBuilderFactory.setFeature(
			_FEATURE_DISALLOW_DOCTYPE_DECLARATION,
			!xslContentConfiguration.isXmlDoctypeDeclarationAllowed());

		documentBuilderFactory.setNamespaceAware(true);

		DocumentBuilder documentBuilder =
			documentBuilderFactory.newDocumentBuilder();

		Document xmlDocument = documentBuilder.parse(
			new ByteArrayInputStream(xml.getBytes()));
		Document xslDocument = documentBuilder.parse(
			new ByteArrayInputStream(xsl.getBytes()));

		Source xmlSource = new DOMSource(xmlDocument);
		Source xslSource = new DOMSource(xslDocument);

		TransformerFactory transformerFactory =
			TransformerFactory.newInstance();

		transformerFactory.setFeature(
			XMLConstants.FEATURE_SECURE_PROCESSING,
			xslContentConfiguration.isXslSecureProcessingEnabled());

		Transformer transformer = transformerFactory.newTransformer(xslSource);

		UnsyncByteArrayOutputStream unsyncByteArrayOutputStream =
			new UnsyncByteArrayOutputStream();

		transformer.transform(
			xmlSource, new StreamResult(unsyncByteArrayOutputStream));

		return unsyncByteArrayOutputStream.toString();
	}

	private static final String _FEATURE_DISALLOW_DOCTYPE_DECLARATION =
		"http://apache.org/xml/features/disallow-doctype-decl";

	private static final String _FEATURE_EXTERNAL_GENERAL_ENTITIES =
		"http://xml.org/sax/features/external-general-entities";

	private static final String _FEATURE_EXTERNAL_PARAMETER_ENTITIES =
		"http://xml.org/sax/features/external-parameter-entities";

}