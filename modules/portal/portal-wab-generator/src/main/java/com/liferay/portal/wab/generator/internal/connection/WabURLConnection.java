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

package com.liferay.portal.wab.generator.internal.connection;

import com.liferay.portal.kernel.util.FastDateFormatFactoryUtil;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.HttpUtil;
import com.liferay.portal.kernel.util.StreamUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.util.FastDateFormatFactoryImpl;
import com.liferay.portal.util.FileImpl;
import com.liferay.portal.util.HttpImpl;
import com.liferay.portal.wab.generator.internal.processor.WabProcessor;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import java.net.URL;
import java.net.URLConnection;

import java.util.Map;

import org.osgi.framework.BundleContext;

/**
 * @author Raymond Augé
 * @author Miguel Pastor
 */
public class WabURLConnection extends URLConnection {

	public WabURLConnection(
		BundleContext bundleContext, ClassLoader classLoader, URL url) {

		super(url);

		_bundleContext = bundleContext;
		_classLoader = classLoader;

		wireSpringUtils();
	}

	@Override
	public void connect() throws IOException {
	}

	@Override
	public InputStream getInputStream() throws IOException {
		URL url = getURL();

		String query = url.getQuery();

		Map<String, String[]> parameters = HttpUtil.getParameterMap(query);

		if (!parameters.containsKey("Web-ContextPath")) {
			throw new IllegalArgumentException(
				"The parameter map does not contain the required parameter " +
					"Web-ContextPath");
		}

		File file = transferToTempFile(new URL(url.getPath()));

		try {
			WabProcessor wabProcessor = new WabProcessor(
				_classLoader, file, parameters);

			return wabProcessor.getInputStream();
		}
		finally {
			FileUtil.deltree(file.getParentFile());
		}
	}

	protected File transferToTempFile(URL url) throws IOException {
		String path = url.getPath();

		String fileName = path.substring(
			path.lastIndexOf(StringPool.SLASH) + 1);

		File file = new File(FileUtil.createTempFolder(), fileName);

		StreamUtil.transfer(url.openStream(), new FileOutputStream(file));

		return file;
	}

	protected void wireSpringUtils() {
		if (FastDateFormatFactoryUtil.getFastDateFormatFactory() == null) {
			FastDateFormatFactoryUtil instance =
				new FastDateFormatFactoryUtil();

			instance.setFastDateFormatFactory(new FastDateFormatFactoryImpl());
		}

		if (FileUtil.getFile() == null) {
			FileUtil instance = new FileUtil();

			instance.setFile(new FileImpl());
		}

		if (HttpUtil.getHttp() == null) {
			HttpUtil instance = new HttpUtil();

			instance.setHttp(new HttpImpl());
		}
	}

	private final BundleContext _bundleContext;
	private final ClassLoader _classLoader;

}