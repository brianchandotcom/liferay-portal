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

package com.liferay.portal.kernel.service.configuration.servlet;

import com.liferay.portal.kernel.service.configuration.ServiceComponentConfiguration;

import java.io.InputStream;

import javax.servlet.ServletContext;

/**
 * @author Miguel Pastor
 */
public class ServletServiceContextComponentConfiguration
	implements ServiceComponentConfiguration {

	public ServletServiceContextComponentConfiguration(
		ServletContext servletContext) {

		_servletContext = servletContext;
	}

	@Override
	public InputStream getHibernateInputStream() {
		return _servletContext.getResourceAsStream(
			"/WEB-INF/classes/META-INF/portlet-hbm.xml");
	}

	@Override
	public InputStream getModelHintsExtInputStream() {
		return _servletContext.getResourceAsStream(
			"/WEB-INF/classes/META-INF/portlet-model-hints-ext.xml");
	}

	@Override
	public InputStream getModelHintsInputStream() {
		return _servletContext.getResourceAsStream(
			"/WEB-INF/classes/META-INF/portlet-model-hints.xml");
	}

	@Override
	public String getServletContextName() {
		return _servletContext.getServletContextName();
	}

	@Override
	public InputStream getSQLIndexesInputStream() {
		return _servletContext.getResourceAsStream("/WEB-INF/sql/indexes.sql");
	}

	@Override
	public InputStream getSQLSequencesInputStream() {
		return _servletContext.getResourceAsStream(
			"/WEB-INF/sql/sequences.sql");
	}

	@Override
	public InputStream getSQLTablesInputStream() {
		return _servletContext.getResourceAsStream("/WEB-INF/sql/tables.sql");
	}

	private final ServletContext _servletContext;

}