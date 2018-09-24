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

package com.liferay.portal.struts;

import com.liferay.portal.kernel.xml.Document;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.kernel.xml.SAXReaderUtil;

import java.io.InputStream;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;

import org.apache.struts.action.ActionServlet;
import org.apache.struts.action.PlugIn;
import org.apache.struts.config.ModuleConfig;

/**
 * @author Brian Wing Shun Chan
 */
public class PortalTilesPlugin implements PlugIn {

	public static final String DEFINITIONS =
		"org.apache.struts.tiles.definitions";

	@Override
	public void destroy() {
	}

	@Override
	public void init(ActionServlet servlet, ModuleConfig moduleConfig)
		throws ServletException {

		ServletContext servletContext = servlet.getServletContext();

		Map<String, Definition> definitions = new HashMap<>();

		List<Element> deferedElements = new ArrayList<>();

		try (InputStream inputStream = servletContext.getResourceAsStream(
				"/WEB-INF/tiles-defs.xml")) {

			Document document = SAXReaderUtil.read(inputStream, false);

			Element rootElement = document.getRootElement();

			for (Element definitionElement :
					rootElement.elements("definition")) {

				String parentName = definitionElement.attributeValue("extends");

				if (parentName == null) {
					_addDefinition(definitions, definitionElement, null);
				}
				else {
					Definition parentDefinition = definitions.get(parentName);

					if (parentDefinition == null) {
						deferedElements.add(rootElement);
					}
					else {
						_addDefinition(
							definitions, definitionElement, parentDefinition);
					}
				}
			}

			for (Element definitionElement : deferedElements) {
				Definition parentDefinition = definitions.get(
					definitionElement.attributeValue("extends"));

				_addDefinition(
					definitions, definitionElement, parentDefinition);
			}

			servletContext.setAttribute(DEFINITIONS, definitions);
		}
		catch (Exception e) {
			throw new ServletException(e);
		}
	}

	private void _addDefinition(
		Map<String, Definition> definitions, Element definitionElement,
		Definition parentDefinition) {

		String name = definitionElement.attributeValue("name");

		Map<String, String> attributes = new HashMap<>();

		String path = definitionElement.attributeValue("path");

		if (parentDefinition != null) {
			attributes.putAll(parentDefinition.getAttributes());

			if (path == null) {
				path = parentDefinition.getPath();
			}
		}

		for (Element putElement : definitionElement.elements("put")) {
			attributes.put(
				putElement.attributeValue("name"),
				putElement.attributeValue("value"));
		}

		definitions.put(name, new Definition(path, attributes));
	}

}