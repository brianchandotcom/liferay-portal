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

package com.liferay.portal.jsp.engine.internal.delegate;

import com.liferay.shielded.container.ShieldedContainerInitializer;

import java.io.InputStream;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.descriptor.JspConfigDescriptor;
import javax.servlet.descriptor.TaglibDescriptor;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.tomcat.util.descriptor.web.JspConfigDescriptorImpl;
import org.apache.tomcat.util.descriptor.web.TaglibDescriptorImpl;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * @author Shuyang Zhou
 */
public class JspConfigDescriptorServletContextDelegate {

	public JspConfigDescriptorServletContextDelegate(
		ServletContext servletContext) {

		_servletContext = servletContext;
	}

	public JspConfigDescriptor getJspConfigDescriptor() {
		List<TaglibDescriptor> taglibDescriptors = new ArrayList<>();

		DocumentBuilderFactory documentBuilderFactory =
			DocumentBuilderFactory.newInstance();

		try (InputStream inputStream = _servletContext.getResourceAsStream(
				ShieldedContainerInitializer.SHIELDED_CONTAINER_WEB_XML)) {

			DocumentBuilder documentBuilder =
				documentBuilderFactory.newDocumentBuilder();

			Document document = documentBuilder.parse(inputStream);

			NodeList taglibNodeList = document.getElementsByTagName("taglib");

			for (int i = 0; i < taglibNodeList.getLength(); i++) {
				Element taglibElement = (Element)taglibNodeList.item(i);

				NodeList taglibLocationNodeList =
					taglibElement.getElementsByTagName("taglib-location");

				Node taglibLocationNode = taglibLocationNodeList.item(0);

				NodeList taglibURINodeList = taglibElement.getElementsByTagName(
					"taglib-uri");

				Node taglibURINode = taglibURINodeList.item(0);

				taglibDescriptors.add(
					new TaglibDescriptorImpl(
						taglibLocationNode.getTextContent(),
						taglibURINode.getTextContent()));
			}
		}
		catch (Exception exception) {
			throw new RuntimeException(exception);
		}

		return new JspConfigDescriptorImpl(
			Collections.emptySet(), taglibDescriptors);
	}

	private final ServletContext _servletContext;

}