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

package com.liferay.poshi.runner.resources;

import com.liferay.poshi.core.PoshiContext;
import com.liferay.poshi.core.PoshiValidation;
import com.liferay.poshi.core.util.Dom4JUtil;
import com.liferay.poshi.core.util.FileUtil;
import com.liferay.poshi.core.util.PropsUtil;

import java.io.File;
import java.io.FileOutputStream;

import java.lang.reflect.Method;

import java.net.URL;
import java.net.URLClassLoader;

import java.nio.channels.Channels;
import java.nio.channels.FileChannel;
import java.nio.channels.ReadableByteChannel;

import org.dom4j.Document;
import org.dom4j.Element;

import org.junit.Before;
import org.junit.Test;

/**
 * @author Kenji Heigel
 */
public abstract class BasePoshiResourcesTestCase {

	public void runTest(String branchName) throws Exception {
		String latestVersion = _getLatestPoshiRunnerResourcesVersion(
			branchName);

		String fileName = "portal-" + branchName + "-" + latestVersion + ".jar";

		URL url = new URL(
			_BASE_URL + "/portal-" + branchName + "/" + latestVersion + "/" +
				fileName);

		File file = new File(fileName);

		if (!file.exists()) {
			_downloadFile(url, fileName);
		}

		_addToClasspath(FileUtil.getURL(file));

		PoshiContext.readFiles();

		PoshiValidation.validate();
	}

	@Before
	public void setUp() throws Exception {
		PoshiContext.clear();
		PropsUtil.clear();

		File file = new File(
			"src/test/java/com/liferay/poshi/runner/resources" +
				"/BasePoshiResourcesTest.java");

		PropsUtil.set("test.base.dir.name", file.getCanonicalPath());
	}

	@Test
	public abstract void testLatestVersion() throws Exception;

	private void _addToClasspath(URL url) {
		try {
			Thread thread = Thread.currentThread();

			URLClassLoader classLoader =
				(URLClassLoader)thread.getContextClassLoader();

			Method method = URLClassLoader.class.getDeclaredMethod(
				"addURL", URL.class);

			method.setAccessible(true);
			method.invoke(classLoader, url);
		}
		catch (Exception exception) {
			throw new RuntimeException(exception);
		}
	}

	private void _downloadFile(URL url, String fileName) throws Exception {
		ReadableByteChannel readableByteChannel = Channels.newChannel(
			url.openStream());

		FileOutputStream fileOutputStream = new FileOutputStream(fileName);

		FileChannel fileChannel = fileOutputStream.getChannel();

		fileChannel.transferFrom(readableByteChannel, 0, Long.MAX_VALUE);
	}

	private String _getLatestPoshiRunnerResourcesVersion(String branchName)
		throws Exception {

		URL mavenMetadataXMLURL = new URL(
			_BASE_URL + "portal-" + branchName + "/maven-metadata.xml");

		Document document = Dom4JUtil.parse(FileUtil.read(mavenMetadataXMLURL));

		Element rootElement = document.getRootElement();

		Element versioningElement = rootElement.element("versioning");

		Element releaseElement = versioningElement.element("release");

		return releaseElement.getText();
	}

	private static final String _BASE_URL =
		"https://repository.liferay.com/nexus/content/repositories" +
			"/liferay-public-releases/com/liferay/poshi/runner/resources/";

}