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

package com.liferay.portal.spring.extender.loader;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.service.configuration.ServiceComponentConfiguration;

import java.io.IOException;
import java.io.InputStream;

import java.net.URL;

import org.osgi.framework.Bundle;

/**
 * @author Miguel Pastor
 */
public class ModuleResourceLoader implements ServiceComponentConfiguration {

	public ModuleResourceLoader(Bundle bundle) {
		_bundle = bundle;
	}

	@Override
	public InputStream getHibernateInputStream() {
		return getInputStream("/META-INF/module-hbm.xml");
	}

	@Override
	public InputStream getModelHintsExtInputStream() {
		return getInputStream("/META-INF/portlet-model-hints-ext.xml");
	}

	@Override
	public InputStream getModelHintsInputStream() {
		return getInputStream("/META-INF/portlet-model-hints.xml");
	}

	@Override
	public InputStream getSQLIndexesInputStream() {
		return getInputStream("/META-INF/sql/indexes.sql");
	}

	@Override
	public InputStream getSQLSequencesInputStream() {
		return getInputStream("/META-INF/sql/sequences.sql");
	}

	@Override
	public InputStream getSQLTablesInputStream() {
		return getInputStream("/META-INF/sql/tables.sql");
	}

	protected InputStream getInputStream(String location) {
		URL url = _bundle.getResource(location);

		if (url == null) {
			if (_log.isInfoEnabled()) {
				_log.info("Unable to find " + location);
			}

			return null;
		}

		InputStream inputStream = null;

		try {
			inputStream = url.openStream();
		}
		catch (IOException ioe) {
			_log.error("Unable to read " + location, ioe);
		}

		return inputStream;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		ModuleResourceLoader.class);

	private final Bundle _bundle;

}