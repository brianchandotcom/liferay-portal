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
		assertLicensePropertiesNotExisted(PRODUCT_ID_PORTAL);

		assertBundlesExisted(
			_ENTERPRISE_APP_SYMBOLIC_NAME, _DXP_ONLY_MODULE_SYMBOLIC_NAME);

		assertLicenseNotRegistered(hitHomePage("localhost", 8080));

		assertBundlesExisted(
			_ENTERPRISE_APP_SYMBOLIC_NAME, _DXP_ONLY_MODULE_SYMBOLIC_NAME);

		File binaryFile = deployFreeTierLicense(Time.HOUR);

		assertLicensePropertiesExisted(PRODUCT_ID_PORTAL);

		assertLicenseRegistered(hitHomePage("localhost", 8080));

		assertBundlesNotExisted(
			_ENTERPRISE_APP_SYMBOLIC_NAME, _DXP_ONLY_MODULE_SYMBOLIC_NAME);

		binaryFile.delete();

		LicenseManagerUtil.checkLicense(PRODUCT_ID_PORTAL);

		assertLicensePropertiesNotExisted(PRODUCT_ID_PORTAL);

		resetLifecycleAction();

		assertBundlesExisted(
			_ENTERPRISE_APP_SYMBOLIC_NAME, _DXP_ONLY_MODULE_SYMBOLIC_NAME);

		assertLicenseNotRegistered(hitHomePage("localhost", 8080));
	}

	private static final String _DXP_ONLY_MODULE_SYMBOLIC_NAME =
		"com.liferay.saml.persistence.api";

	private static final String _ENTERPRISE_APP_SYMBOLIC_NAME =
		"com.liferay.portal.license.enterprise.app";

	private static ResettableClassFileTransformer
		_disableKeyValidatorResettableClassFileTransformer;
	private static ResettableClassFileTransformer
		_setVersionResettableClassFileTransformer;

}