/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.captcha.configuration.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.captcha.BaseCaptchaTestCase;
import com.liferay.captcha.configuration.CaptchaConfiguration;
import com.liferay.portal.configuration.test.util.CompanyConfigurationTemporarySwapper;
import com.liferay.portal.configuration.test.util.ConfigurationTemporarySwapper;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.HashMapDictionaryBuilder;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Istvan Sajtos
 */
@RunWith(Arquillian.class)
public class CaptchaConfigurationTest extends BaseCaptchaTestCase {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Test
	public void test() throws Exception {
		_test(false, false, true);
		_test(true, true, false);
	}

	private void _test(
			boolean expectedCaptchaRendered,
			boolean instanceSettingsCreateAccountCaptchaEnabled,
			boolean systemSettingsCreateAccountCaptchaEnabled)
		throws Exception {

		try (CompanyConfigurationTemporarySwapper
				companyConfigurationTemporarySwapper =
					new CompanyConfigurationTemporarySwapper(
						TestPropsValues.getCompanyId(),
						CaptchaConfiguration.class.getName(),
						new HashMapDictionaryBuilder(
						).<String, Object>put(
							"createAccountCaptchaEnabled",
							instanceSettingsCreateAccountCaptchaEnabled
						).build());
			ConfigurationTemporarySwapper configurationTemporarySwapper =
				new ConfigurationTemporarySwapper(
					CaptchaConfiguration.class.getName(),
					new HashMapDictionaryBuilder(
					).<String, Object>put(
						"createAccountCaptchaEnabled",
						systemSettingsCreateAccountCaptchaEnabled
					).build())) {

			Assert.assertEquals(
				expectedCaptchaRendered, isCaptchaRendered("CAPTCHA"));
		}
	}

}