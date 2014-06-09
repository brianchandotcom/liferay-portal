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

package com.liferay.portal.wab.extender.internal.processor;

import java.io.File;
import java.io.IOException;

import java.util.Map;

import org.osgi.framework.BundleContext;

/**
 * @author Raymond Augé
 * @author Miguel Pastor
 */
public class WabProcessor {

	public WabProcessor(
		BundleContext bundleContext, ClassLoader classLoader, File file,
		Map<String, String[]> parameters) {

		_bundleContext = bundleContext;
		_classLoader = classLoader;
		_file = file;
		_parameters = parameters;
	}

	public java.io.InputStream getInputStream() throws IOException {
		return null;
	}

	public void process() throws IOException {
	}

	private BundleContext _bundleContext;
	private ClassLoader _classLoader;
	private File _file;
	private File _manifestFile;
	private Map<String, String[]> _parameters;

}