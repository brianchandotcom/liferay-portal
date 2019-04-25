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

package com.liferay.portal.error.code.dynamic.include.impl;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.servlet.taglib.DynamicInclude;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.xml.Document;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.kernel.xml.SAXReader;

import java.io.PrintWriter;
import java.io.StringWriter;

import java.util.Map;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Carlos Sierra Andrés
 */
@Component(
	property = "mime.type=application/xml", service = DynamicInclude.class
)
public class ApplicationXMLPortalErrorCodeDynamicInclude
	extends BasePortalErrorCodeDynamicInclude {

	@Activate
	protected void activate(Map<String, Object> properties) {
		_mimeType = MapUtil.getString(
			properties, "mime.type", "application/xml");
	}

	@Override
	protected String getMimeType() {
		return _mimeType;
	}

	@Override
	protected void writeDetailedMessage(
		String message, String requestURI, int statusCode, Throwable throwable,
		PrintWriter printWriter) {

		Document document = _saxReader.createDocument(StringPool.UTF8);

		Element errorElement = document.addElement("error");

		Element messageElement = errorElement.addElement("message");

		messageElement.addText(message);

		Element requestURIElement = errorElement.addElement("requestURI");

		requestURIElement.addText(requestURI);

		Element statusCodeElement = errorElement.addElement("statusCode");

		statusCodeElement.addText(String.valueOf(statusCode));

		if (throwable != null) {
			Element throwableElement = errorElement.addElement("throwable");

			StringWriter stackTrace = new StringWriter();

			throwable.printStackTrace(new PrintWriter(stackTrace));

			throwableElement.addCDATA(stackTrace.toString());
		}

		printWriter.print(document.asXML());
	}

	@Override
	protected void writeMessage(
		int statusCode, String message, PrintWriter printWriter) {

		Document document = _saxReader.createDocument(StringPool.UTF8);

		Element errorElement = document.addElement("error");

		Element messageElement = errorElement.addElement("message");

		messageElement.addText(message);

		Element statusCodeElement = errorElement.addElement("statusCode");

		statusCodeElement.addText(String.valueOf(statusCode));

		printWriter.print(document.asXML());
	}

	private String _mimeType;

	@Reference
	private SAXReader _saxReader;

}