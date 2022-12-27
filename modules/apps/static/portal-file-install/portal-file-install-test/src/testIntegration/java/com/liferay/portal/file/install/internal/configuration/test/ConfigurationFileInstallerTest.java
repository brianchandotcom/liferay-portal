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

package com.liferay.portal.file.install.internal.configuration.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.osgi.service.tracker.collections.list.ServiceTrackerList;
import com.liferay.osgi.service.tracker.collections.list.ServiceTrackerListFactory;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.file.install.FileInstaller;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.util.PropsValues;

import java.io.File;

import java.util.Objects;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;

/**
 * @author Tina Tian
 */
@RunWith(Arquillian.class)
public class ConfigurationFileInstallerTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Test
	public void testCanTransformURL() {
		Bundle bundle = FrameworkUtil.getBundle(
			ConfigurationFileInstallerTest.class);

		try (ServiceTrackerList<FileInstaller> serviceTrackerList =
				ServiceTrackerListFactory.open(
					bundle.getBundleContext(), FileInstaller.class)) {

			FileInstaller configurationFileInstaller = null;

			for (FileInstaller fileInstaller : serviceTrackerList.toList()) {
				Class<?> clazz = fileInstaller.getClass();

				if (Objects.equals(
						clazz.getName(),
						"com.liferay.portal.file.install.internal." +
							"configuration.ConfigurationFileInstaller")) {

					configurationFileInstaller = fileInstaller;

					break;
				}
			}

			Assert.assertNotNull(configurationFileInstaller);

			File configFile = new File(
				PropsValues.MODULE_FRAMEWORK_CONFIGS_DIR, "test.config");

			Assert.assertTrue(
				StringBundler.concat(
					"Configuration file", configFile, " which are in ",
					PropsValues.MODULE_FRAMEWORK_CONFIGS_DIR,
					" folder should be able to be transformed by ",
					"ConfigurationFileInstaller"),
				configurationFileInstaller.canTransformURL(configFile));

			configFile = new File(
				PropsValues.MODULE_FRAMEWORK_MODULES_DIR, "test.config");

			Assert.assertFalse(
				StringBundler.concat(
					"Configuration file", configFile, " which are not in ",
					PropsValues.MODULE_FRAMEWORK_CONFIGS_DIR,
					" folder should not be able to be transformed by ",
					"ConfigurationFileInstaller"),
				configurationFileInstaller.canTransformURL(configFile));
		}
	}

}