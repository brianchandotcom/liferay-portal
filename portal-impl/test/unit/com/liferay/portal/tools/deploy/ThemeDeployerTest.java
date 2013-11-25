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

import com.liferay.portal.kernel.plugin.PluginPackage;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.PropertiesUtil;
import com.liferay.portal.kernel.xml.Document;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.kernel.xml.SAXReaderUtil;
import com.liferay.portal.plugin.PluginPackageUtil;
import org.junit.Assert;
import org.junit.Test;

import java.io.File;
import java.util.Map;
import java.util.Properties;

/**
 * @author Igor Beslic
 */
public class ThemeDeployerTest extends BaseDeployerTest {

	@Test
	public void testProcessPluginPackageProperties() {
		try {
			String displayName = "test-theme";

			Properties properties = PropertiesUtil.load(
				LIFERAY_PLUGIN_PACKAGE_PROPERTIES_CONTENT);

			Assert.assertFalse(
				"Test properties not loaded.", properties.isEmpty());

			PluginPackage pluginPackage =
				PluginPackageUtil.readPluginPackageProperties(
					displayName, properties);

			Assert.assertNotNull(pluginPackage);

			Assert.assertEquals("Test Theme EE", pluginPackage.getName());

			ThemeDeployer themeDeployer = new ThemeDeployer();

			File testDir = new File(TMP_TEST_DIR);

			Assert.assertTrue(testDir.exists());

			Map<String, String> filterMap =
				themeDeployer.processPluginPackageProperties(
					testDir.getParentFile(), displayName, pluginPackage);

			Assert.assertNotNull("FilterMap must not be null.", filterMap);

			Assert.assertFalse(
				"FilterMap must not be empty.", filterMap.isEmpty());

			File xmlFile = new File(
				TMP_TEST_DIR + "/liferay-plugin-package.xml");

			validateLiferayPluginPackageXMLFile(xmlFile);

			xmlFile = new File(TMP_TEST_DIR + "/liferay-look-and-feel.xml");

			validateLiferayLookAndFeelXMLFile(xmlFile);
		}
		catch (Exception e) {
			Assert.fail(format(e, 5));
		}
	}

	protected void validateLiferayLookAndFeelXMLFile(File xmlFile)
		throws Exception {

		Assert.assertTrue(
			"liferay-look-and-feel.xml must be created.", xmlFile.exists());

		String liferayLookAndFeelXML = FileUtil.read(xmlFile);

		Assert.assertNotNull(
			"XML file content must not be null.", liferayLookAndFeelXML);

		Document document = SAXReaderUtil.read(liferayLookAndFeelXML, true);

		Element rootElement = document.getRootElement();

		Element element = rootElement.element("theme");

		String value = element.attribute("name").getValue();

		Assert.assertEquals("Test Theme EE", value);

		value = element.attribute("id").getValue();

		Assert.assertNotNull(value);

		element = rootElement.element("compatibility");

		Assert.assertNotNull(element);

		Assert.assertNotNull(element.getTextTrim());
	}

}