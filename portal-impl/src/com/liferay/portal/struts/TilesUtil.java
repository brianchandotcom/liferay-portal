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

/**
 * @author Shuyang Zhou
 */
public class TilesUtil {

	public static final String DEFINITION =
		"com.liferay.portal.struts.definition";

	public static final String DEFINITIONS =
		"com.liferay.portal.struts.definitions";

	public static void loadDefinitions(ServletContext servletContext)
		throws Exception {

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
	}

	private static void _addDefinition(
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