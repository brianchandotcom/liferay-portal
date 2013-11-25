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

import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.SystemProperties;
import com.liferay.portal.kernel.xml.Document;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.kernel.xml.SAXReaderUtil;
import com.liferay.portal.util.FileImpl;
import com.liferay.portal.xml.SAXReaderImpl;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.io.File;

/**
 * @author Igor Beslic
 */
@PrepareForTest({FileUtil.class, SAXReaderUtil.class})
@RunWith(PowerMockRunner.class)
public class BaseDeployerTest extends PowerMockito {

	public static final String LIFERAY_PLUGIN_PACKAGE_PROPERTIES_CONTENT =
		"name=Test Theme EE\n" +
		"module-group-id=liferay-ee\n" +
		"module-incremental-version=1\n" +
		"tags=alfa,beta,gama,delta\n" +
		"short-description=Test <\n" +
		"long-description=This is test\n" +
		"change-log=\n" +
		"page-url=http://www.liferay.com\n" +
		"author=Liferay, Inc.\n" +
		"licenses=EE\n" +
		"liferay-versions=6.1.20+\n\n" +
		"required-deployment-contexts=\\\n" +
		"    portal-compat-hook";

	public static String TMP_TEST_DIR = "";

	@BeforeClass
	public static void setUpClass() throws Exception {
		FileUtil fileUtil = new FileUtil();

		fileUtil.setFile(new FileImpl());

		SAXReaderUtil saxReaderUtil = new SAXReaderUtil();

		saxReaderUtil.setSAXReader(new SAXReaderImpl());

		String tmpDir = SystemProperties.get(SystemProperties.TMP_DIR);

		TMP_TEST_DIR = tmpDir + "/WEB-INF";

		File tmpTestDir = new File(TMP_TEST_DIR);

		if (!tmpTestDir.exists()) {
			tmpTestDir.mkdir();
		}

		FileUtil.write(
			tmpTestDir + "/liferay-plugin-package.properties",
			LIFERAY_PLUGIN_PACKAGE_PROPERTIES_CONTENT);
	}

	@AfterClass
	public static void tearDownClass() {
		File tmpTestDir = new File(TMP_TEST_DIR);

		if (tmpTestDir.exists()) {
			FileUtil.deltree(tmpTestDir);

			FileUtil.delete(tmpTestDir);
		}
	}

	protected String format(Exception e, int stackTraceDepth) {

		if (e.getStackTrace().length == 0) {
			return "";
		}

		StringBundler sb = new StringBundler(stackTraceDepth * 2);

		sb.append(e.getMessage());

		StackTraceElement[] stackTraceElements = e.getStackTrace();

		for (int i = 0;
				(i < stackTraceElements.length) && (i < stackTraceDepth);) {

			sb.append(stackTraceElements[i].toString());

			i++;

			if ((i != stackTraceElements.length) && (i != stackTraceDepth)) {
				sb.append("\n");
			}
		}

		return sb.toString();
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

		Assert.assertEquals("Test <", element.getTextTrim());

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

}