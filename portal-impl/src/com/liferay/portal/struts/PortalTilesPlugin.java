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

		XmlDefinitionsSet xmlDefinitionsSet = new XmlDefinitionsSet();

		try (InputStream inputStream = servletContext.getResourceAsStream(
				"/WEB-INF/tiles-defs.xml")) {

			Document document = SAXReaderUtil.read(inputStream, false);

			Element rootElement = document.getRootElement();

			for (Element definitionElement :
					rootElement.elements("definition")) {

				XmlDefinition xmlDefinition = new XmlDefinition();

				xmlDefinition.setExtends(
					definitionElement.attributeValue("extends"));
				xmlDefinition.setName(definitionElement.attributeValue("name"));
				xmlDefinition.setPath(definitionElement.attributeValue("path"));

				for (Element putElement : definitionElement.elements("put")) {
					xmlDefinition.putAttribute(
						putElement.attributeValue("name"),
						putElement.attributeValue("value"));
				}

				xmlDefinitionsSet.putDefinition(xmlDefinition);
			}

			xmlDefinitionsSet.resolveInheritances();

			servletContext.setAttribute(
				DEFINITIONS, xmlDefinitionsSet.getDefinitions());
		}
		catch (Exception e) {
			throw new ServletException(e);
		}
	}

}