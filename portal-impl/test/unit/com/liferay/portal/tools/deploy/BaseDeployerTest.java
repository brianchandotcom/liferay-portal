/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.tools.deploy;

import com.liferay.portal.kernel.deploy.Deployer;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.PropertiesUtil;
import com.liferay.portal.kernel.util.StreamUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.SystemProperties;
import com.liferay.portal.kernel.xml.Document;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.kernel.xml.SAXReaderUtil;
import com.liferay.portal.util.FileImpl;
import com.liferay.portal.xml.SAXReaderImpl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import java.util.Properties;

import org.junit.Assert;
import org.junit.BeforeClass;

/**
 * @author Igor Beslic
 */
public abstract class BaseDeployerTest {

	@BeforeClass
	public static void setUpClass() throws Exception {
		FileUtil fileUtil = new FileUtil();

		fileUtil.setFile(new FileImpl());

		SAXReaderUtil saxReaderUtil = new SAXReaderUtil();

		saxReaderUtil.setSAXReader(new SAXReaderImpl());

		transferPropertiesToTempFile();
	}

	public abstract Deployer getDeployer();

	public File getRootDeploymentFolder() {
		return getWebInfFolder().getParentFile();
	}

	public File getWebInfFolder() {
		return new File(WEB_INF_FOLDER);
	}

	protected static void transferPropertiesToTempFile() throws IOException {
		InputStream inputStream = null;
		OutputStream outputStream = null;

		try {
			inputStream = BaseDeployerTest.class.getResourceAsStream(
				"dependencies/liferay-plugin-package.properties");

			FileUtil.mkdirs(WEB_INF_FOLDER);

			outputStream = new FileOutputStream(
				new File(WEB_INF_FOLDER, "liferay-plugin-package.properties"));

			StreamUtil.transfer(inputStream, outputStream);
		}
		finally {
			inputStream.close();
			outputStream.close();
		}
	}

	protected Properties getLiferayPluginPackageProperties()
		throws IOException {

		InputStream inputStream = new FileInputStream(
			WEB_INF_FOLDER + "/liferay-plugin-package.properties");

		String stringProperties = StringUtil.read(inputStream);

		Properties properties = PropertiesUtil.load(stringProperties);

		Assert.assertFalse(
			"Test properties have not been loaded properly.",
			properties.isEmpty());

		return properties;
	}

	protected void validateLiferayPluginPackageXMLFile(File xmlFile)
		throws Exception {

		Assert.assertTrue(
			"liferay-plugin-package.xml must be created.", xmlFile.exists());

		String liferayPluginPackageXML = FileUtil.read(xmlFile);

		Assert.assertNotNull(
			"XML file content must not be null.", liferayPluginPackageXML);

		Document document = SAXReaderUtil.read(liferayPluginPackageXML, true);

		Element rootElement = document.getRootElement();

		Element element = rootElement.element("name");

		Assert.assertNotNull("Name element missing.", element);

		element = rootElement.element("tags");

		Assert.assertNotNull(element);

		Assert.assertNotNull(element.getTextTrim());

		element = rootElement.element("short-description");

		Assert.assertNotNull(element);

		Assert.assertEquals("Test", element.getTextTrim());

		element = rootElement.element("page-url");

		Assert.assertNotNull(element);

		Assert.assertNotNull(element.getTextTrim());

		element = rootElement.element("author");

		Assert.assertNotNull(element);

		Assert.assertNotNull(element.getTextTrim());

		element = rootElement.element("liferay-versions");

		Assert.assertNotNull(element);

		Assert.assertNotNull(element.getTextTrim());
	}

	private static final String WEB_INF_FOLDER = SystemProperties.get(
		SystemProperties.TMP_DIR) + "/WEB-INF";

}