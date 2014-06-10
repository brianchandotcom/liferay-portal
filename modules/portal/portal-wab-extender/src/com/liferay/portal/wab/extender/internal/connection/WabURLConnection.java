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

package com.liferay.portal.wab.extender.internal.connection;

import com.liferay.portal.kernel.util.FastDateFormatFactoryUtil;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.HttpUtil;
import com.liferay.portal.kernel.util.StreamUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.util.FastDateFormatFactoryImpl;
import com.liferay.portal.util.FileImpl;
import com.liferay.portal.util.HttpImpl;
import com.liferay.portal.wab.extender.internal.processor.WabProcessor;

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

	public static final String WEB_CONTEXTPATH = "Web-ContextPath";

	public WabURLConnection(
		BundleContext bundleContext, ClassLoader classLoader, URL url) {

		super(url);

		_bundleContext = bundleContext;
		_classLoader = classLoader;

		_wireSpringUtils();
	}

	@Override
	public void connect() throws IOException {
	}

	@Override
	public InputStream getInputStream() throws IOException {
		URL url = getURL();

		String query = url.getQuery();

		Map<String, String[]> parameters = HttpUtil.getParameterMap(query);

		if (!parameters.containsKey(WEB_CONTEXTPATH)) {
			throw new IllegalArgumentException(
				WEB_CONTEXTPATH + " parameter is required");
		}

		URL innerURL = new URL(url.getPath());

		File tempFile = _transferToTempFolder(innerURL);

		try {
			WabProcessor wabProcessor = new WabProcessor(
				_bundleContext, _classLoader, tempFile, parameters);

			wabProcessor.process();

			return wabProcessor.getInputStream();
		}
		finally {
			FileUtil.deltree(tempFile.getParentFile());
		}
	}

	private File _transferToTempFolder(URL url) throws IOException {
		File tempFolder = FileUtil.createTempFolder();

		int start = url.getPath().lastIndexOf(StringPool.SLASH);

		String fileName = url.getPath().substring(start + 1);

		File tempFile = new File(tempFolder, fileName);

		StreamUtil.transfer(url.openStream(), new FileOutputStream(tempFile));

		return tempFile;
	}

	private void _wireSpringUtils() {
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

	private BundleContext _bundleContext;
	private ClassLoader _classLoader;

}