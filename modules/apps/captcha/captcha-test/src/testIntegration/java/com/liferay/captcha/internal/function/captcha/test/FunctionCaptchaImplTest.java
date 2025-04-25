/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.captcha.internal.function.captcha.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.captcha.BaseCaptchaTestCase;
import com.liferay.captcha.configuration.CaptchaConfiguration;
import com.liferay.client.extension.type.CustomElementCET;
import com.liferay.client.extension.type.configuration.CETConfiguration;
import com.liferay.client.extension.type.manager.CETManager;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.configuration.test.util.CompanyConfigurationTemporarySwapper;
import com.liferay.portal.configuration.test.util.ConfigurationTestUtil;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.HashMapDictionaryBuilder;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Pedro Victor Silvestre
 */
@RunWith(Arquillian.class)
public class FunctionCaptchaImplTest extends BaseCaptchaTestCase {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@BeforeClass
	public static void setUpClass() throws Exception {
		_cet = (CustomElementCET)_cetManager.addCET(
			ConfigurableUtil.createConfigurable(
				CETConfiguration.class,
				HashMapBuilder.<String, Object>put(
					"baseURL", "${portalURL}/o/test_" + _VIRTUAL_HOSTNAME
				).put(
					"name", "Test " + _VIRTUAL_HOSTNAME
				).put(
					"type", "customElement"
				).put(
					"typeSettings", new String[] {"htmlElementName=test"}
				).build()),
			TestPropsValues.getCompanyId(), "LXC:test");

		_pid = ConfigurationTestUtil.createFactoryConfiguration(
			"com.liferay.captcha.internal.configuration." +
				"FunctionCaptchaImplConfiguration",
			HashMapDictionaryBuilder.<String, Object>put(
				"captchaName", "ClientExtensionCaptcha"
			).put(
				"customElementExternalReferenceCode", "LXC:test"
			).build());
	}

	@AfterClass
	public static void tearDownClass() throws Exception {
		_cetManager.deleteCET(_cet);

		ConfigurationTestUtil.deleteConfiguration(_pid);
	}

	@Test
	public void test() throws Exception {
		String servicePid = StringUtil.extractLast(_pid, StringPool.TILDE);

		try (CompanyConfigurationTemporarySwapper
				companyConfigurationTemporarySwapper =
					new CompanyConfigurationTemporarySwapper(
						TestPropsValues.getCompanyId(),
						CaptchaConfiguration.class.getName(),
						new HashMapDictionaryBuilder(
						).<String, Object>put(
							"captchaEngine",
							"com.liferay.captcha.internal.function.captcha." +
								"FunctionCaptchaImpl#" + servicePid
						).build())) {

			Assert.assertTrue(
				isCaptchaRendered(
					StringPool.LESS_THAN + _cet.getHTMLElementName()));
		}
	}

	private static final String _VIRTUAL_HOSTNAME =
		RandomTestUtil.randomString() + ".localtest.me";

	private static volatile CustomElementCET _cet;

	@Inject
	private static CETManager _cetManager;

	private static String _pid;

}