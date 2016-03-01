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

package com.liferay.marketplace.deployer.connection;

import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.StreamUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.StringReader;

import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import java.util.Enumeration;
import java.util.Properties;
import java.util.jar.Attributes;
import java.util.jar.JarFile;
import java.util.jar.JarOutputStream;
import java.util.jar.Manifest;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import org.osgi.framework.Constants;

/**
 * @author Miguel Pastor
 */
public class LiferayPackageConnection extends URLConnection {

	public LiferayPackageConnection(URL url) {
		super(url);

		try {
			_innerURL = new URL(url.getPath());
		}
		catch (MalformedURLException murle) {
			throw new RuntimeException(
				"Unable to build URL from " + url.getPath());
		}
	}

	@Override
	public void connect() throws IOException {
	}

	@Override
	public InputStream getInputStream() throws IOException {
		ByteArrayOutputStream byteArrayOutputStream =
			new ByteArrayOutputStream();
		File file = null;

		try {
			file = transferToTempFile(_innerURL);

			transform(file, byteArrayOutputStream);

			return new ByteArrayInputStream(
				byteArrayOutputStream.toByteArray());
		}
		catch (Exception e) {
			throw new IOException("Error dealing with file " + getURL());
		}
		finally {
			byteArrayOutputStream.close();

			if (file != null) {
				file.delete();
			}
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

	protected void transform(File file, OutputStream outputStream)
		throws IOException {

		ZipFile zipFile = new ZipFile(file);

		JarOutputStream jarOutputStream = new JarOutputStream(outputStream);
		Properties properties = new Properties();

		ZipEntry zipEntry = zipFile.getEntry("liferay-marketplace.properties");

		InputStream inputStream = zipFile.getInputStream(zipEntry);

		properties.load(new StringReader(StringUtil.read(inputStream)));

		Manifest manifest = new Manifest();

		Attributes attributes = manifest.getMainAttributes();

		attributes.putValue("Manifest-Version", "2");
		attributes.putValue(Constants.BUNDLE_MANIFESTVERSION, "2");
		attributes.putValue(
			Constants.BUNDLE_SYMBOLICNAME, properties.getProperty("title"));
		attributes.putValue(
			Constants.BUNDLE_DESCRIPTION,
			properties.getProperty("description"));
		attributes.putValue(
			Constants.BUNDLE_VERSION, properties.getProperty("version"));

		ZipEntry newZipEntry = new ZipEntry(JarFile.MANIFEST_NAME);

		jarOutputStream.putNextEntry(newZipEntry);

		manifest.write(jarOutputStream);

		jarOutputStream.closeEntry();

		Enumeration<? extends ZipEntry> entries = zipFile.entries();

		while (entries.hasMoreElements()) {
			zipEntry = entries.nextElement();

			newZipEntry = new ZipEntry(zipEntry.getName());

			jarOutputStream.putNextEntry(newZipEntry);

			StreamUtil.transfer(
				zipFile.getInputStream(zipEntry), jarOutputStream, false);

			jarOutputStream.closeEntry();
		}

		jarOutputStream.close();
	}

	private final URL _innerURL;

}