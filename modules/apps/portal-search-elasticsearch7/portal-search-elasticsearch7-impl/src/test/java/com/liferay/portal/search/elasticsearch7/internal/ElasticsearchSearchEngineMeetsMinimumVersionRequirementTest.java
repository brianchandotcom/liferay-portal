/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.elasticsearch7.internal;

import com.liferay.portal.kernel.module.util.SystemBundleUtil;
import com.liferay.portal.kernel.version.Version;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import org.mockito.MockedStatic;
import org.mockito.Mockito;

import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;

/**
 * @author Bryan Engler
 */
public class ElasticsearchSearchEngineMeetsMinimumVersionRequirementTest {

	@ClassRule
	@Rule
	public static LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@BeforeClass
	public static void setUpClass() {
		BundleContext bundleContext = SystemBundleUtil.getBundleContext();

		_frameworkUtilMockedStatic.when(
			() -> FrameworkUtil.getBundle(Mockito.any())
		).thenReturn(
			bundleContext.getBundle()
		);
	}

	@AfterClass
	public static void tearDownClass() {
		_frameworkUtilMockedStatic.close();
	}

	@Test
	public void testMeetsRequirement() {
		_testMeetsRequirement("7.17.1", "8.3.2", true);
		_testMeetsRequirement("7.17.0", "7.17.1", true);
		_testMeetsRequirement("7.15.3", "7.14.4", false);
		_testMeetsRequirement("7.15", "7.16.4", true);
		_testMeetsRequirement("7.15", "7.15.1", true);
		_testMeetsRequirement("7.15", "7.15.0", true);
		_testMeetsRequirement("7.15", "7.14.4", false);
		_testMeetsRequirement("7.5.3", "7.6.5", true);
		_testMeetsRequirement("7.5.3", "7.5.3", true);
		_testMeetsRequirement("7.5.3", "7.5.2", false);
		_testMeetsRequirement("7.5.3", "7.4.4", false);
		_testMeetsRequirement("7.5.3", "7.4.3", false);
		_testMeetsRequirement("7.5.3", "6.6.5", false);
	}

	private void _testMeetsRequirement(
		String minimumVersionString, String versionString,
		boolean meetsRequirement) {

		ElasticsearchSearchEngine elasticsearchSearchEngine =
			new ElasticsearchSearchEngine();

		Assert.assertEquals(
			minimumVersionString + " -> " + versionString, meetsRequirement,
			elasticsearchSearchEngine.meetsMinimumVersionRequirement(
				Version.parseVersion(minimumVersionString), versionString));
	}

	private static final MockedStatic<FrameworkUtil>
		_frameworkUtilMockedStatic = Mockito.mockStatic(FrameworkUtil.class);

}