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

import java.io.File;

import net.bytebuddy.agent.builder.ResettableClassFileTransformer;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assume;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Kevin Lee
 * @author Tina Tian
 */
@RunWith(Arquillian.class)
public class DXPModuleLicenseTest extends BaseLicenseTestCase {

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
		_setVersionResettableClassFileTransformer = setVersion("2026.Q1.0");
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
	public void testFreeTierLicense() throws Exception {
		assertLicensePropertiesNotExisted(getPortalProductId());

		assertBundlesExisted(
			_getDxpOnlyModuleSymbolicName(), _getEnterpriseAppSymbolicName());

		assertLicenseNotRegistered(hitHomePage("localhost", 8080));

		assertBundlesExisted(
			_getDxpOnlyModuleSymbolicName(), _getEnterpriseAppSymbolicName());

		File binaryFile = deployFreeTierLicense(Time.HOUR);

		assertLicensePropertiesExisted(getPortalProductId());

		assertLicenseRegistered(hitHomePage("localhost", 8080));

		assertBundlesNotExisted(
			_getDxpOnlyModuleSymbolicName(), _getEnterpriseAppSymbolicName());

		binaryFile.delete();

		LicenseManagerUtil.checkLicense(getPortalProductId());

		assertLicensePropertiesNotExisted(getPortalProductId());

		resetLifecycleAction();

		assertBundlesExisted(
			_getDxpOnlyModuleSymbolicName(), _getEnterpriseAppSymbolicName());

		assertLicenseNotRegistered(hitHomePage("localhost", 8080));
	}

	private String _getDxpOnlyModuleSymbolicName() {
		return getProperty("dxp.only.module.symbolic.name");
	}

	private String _getEnterpriseAppSymbolicName() {
		return getProperty("enterprise.app.symbolic.name");
	}

	private static ResettableClassFileTransformer
		_disableKeyValidatorResettableClassFileTransformer;
	private static ResettableClassFileTransformer
		_setVersionResettableClassFileTransformer;

}