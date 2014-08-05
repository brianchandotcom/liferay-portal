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

package com.liferay.portal.service.configuration.servlet;

import com.liferay.portal.service.configuration.ServiceComponentConfiguration;

import javax.servlet.ServletContext;
import java.io.InputStream;

/**
 * @author Miguel Pastor
 */
public class ServletContextComponentConfiguration implements
	ServiceComponentConfiguration {

	public ServletContextComponentConfiguration(ServletContext servletContext) {
		_servletContext = servletContext;
	}

	@Override
	public InputStream hibernate() {
		return _servletContext.getResourceAsStream(
			"classes/META-INF/portlet-hbm.xml");
	}

	@Override
	public InputStream modelHints() {
		return _servletContext.getResourceAsStream(
			"META-INF/portlet-model-hints.xml");
	}

	@Override
	public InputStream modelHintsExt() {
		return _servletContext.getResourceAsStream(
			"META-INF/portlet-model-hints-ext.xml");
	}

	@Override
	public InputStream sqlIndexes() {
		return _servletContext.getResourceAsStream("/WEB-INF/sql/indexes.sql");
	}

	@Override
	public InputStream sqlSequences() {
		return _servletContext.getResourceAsStream(
			"/WEB-INF/sql/sequences.sql");
	}

	@Override
	public InputStream sqlTables() {
		return _servletContext.getResourceAsStream("/WEB-INF/sql/tables.sql");
	}

	private ServletContext _servletContext;

}