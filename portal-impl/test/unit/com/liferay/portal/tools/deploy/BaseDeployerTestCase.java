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

package com.liferay.portal.tools.deploy;

import com.liferay.portal.kernel.deploy.Deployer;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.PropertiesUtil;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.util.StreamUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.SystemProperties;
import com.liferay.portal.kernel.xml.Document;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.kernel.xml.SAXReaderUtil;
import com.liferay.portal.kernel.xml.UnsecureSAXReaderUtil;
import com.liferay.portal.util.FileImpl;
import com.liferay.portal.util.PropsImpl;
import com.liferay.portal.xml.SAXReaderImpl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import java.util.Properties;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;

/**
 * @author Igor Beslic
 */
public abstract class BaseDeployerTestCase {

	public abstract Deployer getDeployer();

	@Before
	public void setUp() throws Exception {
		FileUtil fileUtil = new FileUtil();

		fileUtil.setFile(new FileImpl());

		PropsUtil.setProps(new PropsImpl());

		SAXReaderUtil saxReaderUtil = new SAXReaderUtil();

		SAXReaderImpl secureSAXReaderImpl = new SAXReaderImpl();

		secureSAXReaderImpl.setSecure(true);

		saxReaderUtil.setSAXReader(secureSAXReaderImpl);

		UnsecureSAXReaderUtil unsecureSAXReaderUtil =
			new UnsecureSAXReaderUtil();

		SAXReaderImpl unsecureSAXReaderImpl = new SAXReaderImpl();

		unsecureSAXReaderUtil.setSAXReader(unsecureSAXReaderImpl);

		setUpLiferayPluginPackageProperties();
	}

	@After
	public void tearDown() throws Exception {
		FileUtil.deltree(getRootDir());
	}

	protected Properties getLiferayPluginPackageProperties()
		throws IOException {

		InputStream inputStream = new FileInputStream(
			new File(getWebInfDir(), "liferay-plugin-package.properties"));

		Properties properties = PropertiesUtil.load(
			StringUtil.read(inputStream));

		Assert.assertFalse(properties.isEmpty());

		return properties;
	}

	protected File getRootDir() {
		if (_rootDir == null) {
			_rootDir = new File(
				SystemProperties.get(SystemProperties.TMP_DIR),
				StringUtil.randomId());
		}

		return _rootDir;
	}

	protected File getWebInfDir() {
		return new File(getRootDir(), "WEB-INF");
	}

	protected void setUpLiferayPluginPackageProperties() throws Exception {
		InputStream inputStream = null;
		OutputStream outputStream = null;

		try {
			inputStream = BaseDeployerTestCase.class.getResourceAsStream(
				"dependencies/liferay-plugin-package.properties");

			File webInfDir = getWebInfDir();

			webInfDir.mkdirs();

			outputStream = new FileOutputStream(
				new File(webInfDir, "liferay-plugin-package.properties"));

			StreamUtil.transfer(inputStream, outputStream);
		}
		finally {
			inputStream.close();
			outputStream.close();
		}
	}

	protected void validateLiferayPluginPackageXMLFile(File xmlFile)
		throws Exception {

		Assert.assertTrue(xmlFile.exists());

		String liferayPluginPackageXML = FileUtil.read(xmlFile);

		Assert.assertNotNull(liferayPluginPackageXML);

		Document document = UnsecureSAXReaderUtil.read(
			liferayPluginPackageXML, true);

		Element rootElement = document.getRootElement();

		Element element = rootElement.element("name");

		Assert.assertNotNull(element);

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

	private File _rootDir;

}