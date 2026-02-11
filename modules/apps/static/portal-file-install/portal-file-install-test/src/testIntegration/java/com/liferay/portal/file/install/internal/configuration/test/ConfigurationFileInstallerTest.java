/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.file.install.internal.configuration.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.osgi.service.tracker.collections.list.ServiceTrackerList;
import com.liferay.osgi.service.tracker.collections.list.ServiceTrackerListFactory;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.file.install.FileInstaller;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.instance.PortalInstancePool;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.HashMapDictionaryBuilder;
import com.liferay.portal.kernel.util.PropsValues;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.io.File;

import java.util.Dictionary;
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
					"Configuration file ", configFile, " which is in ",
					PropsValues.MODULE_FRAMEWORK_CONFIGS_DIR,
					" folder should transformed by ConfigurationFileInstaller"),
				configurationFileInstaller.canTransformURL(configFile));

			configFile = new File(
				PropsValues.MODULE_FRAMEWORK_MODULES_DIR, "test.config");

			Assert.assertFalse(
				StringBundler.concat(
					"Configuration file ", configFile, " which is not in ",
					PropsValues.MODULE_FRAMEWORK_CONFIGS_DIR,
					" folder should not be transformed by ",
					"ConfigurationFileInstaller"),
				configurationFileInstaller.canTransformURL(configFile));
		}
	}

	@Test
	public void testGetCompanyId() throws PortalException {
		if (!PropsValues.DATABASE_PARTITION_ENABLED) {
			return;
		}

		Bundle bundle = FrameworkUtil.getBundle(
			ConfigurationFileInstallerTest.class);

		String fileName = RandomTestUtil.randomString();

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

			long companyId = ReflectionTestUtil.invoke(
				configurationFileInstaller, "_getCompanyId",
				new Class<?>[] {Dictionary.class, String.class},
				HashMapDictionaryBuilder.put(
					"companyId", TestPropsValues.getCompanyId()
				).build(),
				fileName);

			Assert.assertEquals(TestPropsValues.getCompanyId(), companyId);

			companyId = ReflectionTestUtil.invoke(
				configurationFileInstaller, "_getCompanyId",
				new Class<?>[] {Dictionary.class, String.class},
				HashMapDictionaryBuilder.put(
					"companyWebId",
					PortalInstancePool.getWebId(TestPropsValues.getCompanyId())
				).build(),
				fileName);

			Assert.assertEquals(TestPropsValues.getCompanyId(), companyId);

			companyId = ReflectionTestUtil.invoke(
				configurationFileInstaller, "_getCompanyId",
				new Class<?>[] {Dictionary.class, String.class},
				HashMapDictionaryBuilder.put(
					"companyId", TestPropsValues.getCompanyId()
				).put(
					"groupId", TestPropsValues.getGroupId()
				).build(),
				fileName);

			Assert.assertEquals(TestPropsValues.getCompanyId(), companyId);

			companyId = ReflectionTestUtil.invoke(
				configurationFileInstaller, "_getCompanyId",
				new Class<?>[] {Dictionary.class, String.class},
				HashMapDictionaryBuilder.put(
					"groupKey",
					StringBundler.concat(
						PortalInstancePool.getWebId(
							TestPropsValues.getCompanyId()),
						"---", TestPropsValues.getGroupId())
				).build(),
				fileName);

			Assert.assertEquals(TestPropsValues.getCompanyId(), companyId);

			ReflectionTestUtil.invoke(
				configurationFileInstaller, "_getCompanyId",
				new Class<?>[] {Dictionary.class, String.class},
				HashMapDictionaryBuilder.put(
					"groupId", TestPropsValues.getGroupId()
				).build(),
				fileName);

			Assert.fail();
		}
		catch (IllegalArgumentException illegalArgumentException) {
			Assert.assertEquals(
				StringBundler.concat(
					"Unable to process group scoped configuration ", fileName,
					" because required property \"companyId\" is missing"),
				illegalArgumentException.getMessage());
		}
	}

}