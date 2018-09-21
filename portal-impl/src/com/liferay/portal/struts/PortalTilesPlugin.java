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

import com.liferay.petra.string.StringPool;

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
import org.apache.struts.tiles.xmlDefinition.FactorySet;

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

		try {
			servletContext.setAttribute(
				TilesUtilImpl.DEFINITIONS_FACTORY,
				new DefinitionsFactoryAdaptor(
					new I18nFactorySet(servletContext, _properties)));
		}
		catch (DefinitionsFactoryException dfe) {
			throw new ServletException(dfe);
		}
	}

	public void setCurrentPlugInConfigObject(PlugInConfig plugInConfig) {
		_properties = plugInConfig.getProperties();

		_properties.put(
			DefinitionsFactoryConfig.PARSER_VALIDATE_PARAMETER_NAME,
			StringPool.TRUE);
	}

	private Map<String, String> _properties;

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

			return _factorySet.getDefinition(
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

		private DefinitionsFactoryAdaptor(FactorySet factorySet) {
			_factorySet = factorySet;
		}

		private final FactorySet _factorySet;

	}

}