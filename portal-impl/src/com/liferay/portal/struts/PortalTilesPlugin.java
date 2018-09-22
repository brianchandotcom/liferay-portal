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

import java.io.InputStream;

import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;

import org.apache.struts.action.ActionServlet;
import org.apache.struts.action.PlugIn;
import org.apache.struts.config.ModuleConfig;
import org.apache.struts.config.PlugInConfig;
import org.apache.struts.tiles.ComponentDefinition;
import org.apache.struts.tiles.DefinitionsFactory;
import org.apache.struts.tiles.DefinitionsFactoryConfig;
import org.apache.struts.tiles.DefinitionsFactoryException;
import org.apache.struts.tiles.NoSuchDefinitionException;
import org.apache.struts.tiles.TilesUtilImpl;
import org.apache.struts.tiles.xmlDefinition.XmlDefinitionsSet;
import org.apache.struts.tiles.xmlDefinition.XmlParser;

/**
 * @author Brian Wing Shun Chan
 */
public class PortalTilesPlugin implements PlugIn {

	@Override
	public void destroy() {
	}

	@Override
	public void init(ActionServlet servlet, ModuleConfig moduleConfig)
		throws ServletException {

		ServletContext servletContext = servlet.getServletContext();

		XmlDefinitionsSet xmlDefinitionsSet = new XmlDefinitionsSet();

		XmlParser xmlParser = new XmlParser();

		xmlParser.setValidating(true);

		try (InputStream inputStream = servletContext.getResourceAsStream(
				_fileName)) {

			xmlParser.parse(inputStream, xmlDefinitionsSet);

			xmlDefinitionsSet.resolveInheritances();

			servletContext.setAttribute(
				TilesUtilImpl.DEFINITIONS_FACTORY,
				new DefinitionsFactoryAdaptor(xmlDefinitionsSet));
		}
		catch (Exception e) {
			throw new ServletException(e);
		}
	}

	public void setCurrentPlugInConfigObject(PlugInConfig plugInConfig) {
		Map<String, String> properties = plugInConfig.getProperties();

		_fileName = properties.get(
			DefinitionsFactoryConfig.DEFINITIONS_CONFIG_PARAMETER_NAME);
	}

	private String _fileName;

	private static class DefinitionsFactoryAdaptor
		implements DefinitionsFactory {

		@Override
		public void destroy() {
		}

		@Override
		public DefinitionsFactoryConfig getConfig() {
			return null;
		}

		@Override
		public ComponentDefinition getDefinition(
				String name, ServletRequest servletRequest,
				ServletContext servletContext)
			throws DefinitionsFactoryException, NoSuchDefinitionException {

			return _definitionsFactory.getDefinition(
				name, servletRequest, servletContext);
		}

		@Override
		public void init(
			DefinitionsFactoryConfig definitionsFactoryConfig,
			ServletContext servletContext) {
		}

		@Override
		public void setConfig(
			DefinitionsFactoryConfig definitionsFactoryConfig,
			ServletContext servletContext) {
		}

		private DefinitionsFactoryAdaptor(XmlDefinitionsSet xmlDefinitionsSet)
			throws NoSuchDefinitionException {

			_definitionsFactory =
				new org.apache.struts.tiles.xmlDefinition.DefinitionsFactory(
					xmlDefinitionsSet);
		}

		private final org.apache.struts.tiles.xmlDefinition.DefinitionsFactory
			_definitionsFactory;

	}

}