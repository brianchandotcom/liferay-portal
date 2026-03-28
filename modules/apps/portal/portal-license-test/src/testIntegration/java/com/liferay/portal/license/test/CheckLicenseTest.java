/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.license.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.portal.kernel.license.util.LicenseManagerUtil;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.AssumeTestRule;
import com.liferay.portal.kernel.util.Time;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.util.LicenseUtil;

import java.io.InputStream;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

import net.bytebuddy.agent.builder.ResettableClassFileTransformer;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Assume;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Tina Tian
 */
@RunWith(Arquillian.class)
public class CheckLicenseTest extends BaseLicenseTestCase {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new AssumeTestRule("assume"), new LiferayIntegrationTestRule());

	public static void assume() {
		Assume.assumeTrue(isReleaseBundle());
	}

	@BeforeClass
	public static void setUpClass() {
		_disableKeyValidatorResettableClassFileTransformer = disableValidate();
		_setVersionResettableClassFileTransformer = setVersion("2026.Q1.0 LTS");
	}

	@AfterClass
	public static void tearDownClass() {
		resetClassFileTransformer(
			_disableKeyValidatorResettableClassFileTransformer);
		resetClassFileTransformer(_setVersionResettableClassFileTransformer);
	}

	@After
	public void tearDown() throws Exception {
		resetLicenseData();

		resetLifecycleAction();
	}

	@Test
	public void testCheckLicenseForCMP() throws Exception {
		assertLicensePropertiesNotExisted(getCMPProductId());

		deployCMPLicense(Time.HOUR);

		assertLicensePropertiesExisted(getCMPProductId());

		Assert.assertTrue(LicenseManagerUtil.isCMPEnabled());
	}

	@Test
	public void testCheckLicenseWithBinaryFile2026_Q1_0() throws Exception {
		_testCheckLicense("binary_file_2026_Q1_0.li");
	}

	@Test
	public void testCheckLicenseWithBinaryFileAfter2026_Q1_0()
		throws Exception {

		_testCheckLicense("binary_file_after_2026_Q1_0.li");
	}

	@Test
	public void testCheckLicenseWithBinaryFileBefore2026_Q1_0()
		throws Exception {

		_testCheckLicense("binary_file_before_2026_Q1_0.li");
	}

	@Test
	public void testCheckLicenseWithFreeTierBinaryFile2026_Q1_0()
		throws Exception {

		_testCheckLicense("free_tier_binary_file_2026_Q1_0.li");
	}

	@Test
	public void testCheckLicenseWithFreeTierBinaryFileAfter2026_Q1_0()
		throws Exception {

		_testCheckLicense("free_tier_binary_file_after_2026_Q1_0.li");
	}

	private void _testCheckLicense(String fileName) throws Exception {
		Path target = Path.of(LicenseUtil.LICENSE_REPOSITORY_DIR, fileName);

		try (InputStream inputStream =
				CheckLicenseTest.class.getResourceAsStream(
					"dependencies/" + fileName)) {

			Files.createDirectories(target.getParent());

			Files.copy(
				inputStream, target, StandardCopyOption.REPLACE_EXISTING);
		}

		assertLicensePropertiesNotExisted(getPortalProductId());

		LicenseManagerUtil.checkLicense(getPortalProductId());

		assertLicensePropertiesExisted(getPortalProductId());

		assertPortalLicenseRegistered();
	}

	private static ResettableClassFileTransformer
		_disableKeyValidatorResettableClassFileTransformer;
	private static ResettableClassFileTransformer
		_setVersionResettableClassFileTransformer;

}