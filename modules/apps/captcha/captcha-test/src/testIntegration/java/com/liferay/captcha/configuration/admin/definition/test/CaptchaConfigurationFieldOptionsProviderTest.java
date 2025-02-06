/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.captcha.configuration.admin.definition.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.captcha.recaptcha.ReCaptchaImpl;
import com.liferay.captcha.simplecaptcha.SimpleCaptchaImpl;
import com.liferay.configuration.admin.definition.ConfigurationFieldOptionsProvider;
import com.liferay.portal.kernel.captcha.Captcha;
import com.liferay.portal.kernel.module.util.SystemBundleUtil;
import com.liferay.portal.kernel.util.HashMapDictionaryBuilder;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.util.List;
import java.util.Objects;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestRule;
import org.junit.runner.RunWith;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;

/**
 * @author Pedro Victor Silvestre
 */
@RunWith(Arquillian.class)
public class CaptchaConfigurationFieldOptionsProviderTest {

	@ClassRule
	@Rule
	public static final TestRule testRule = new LiferayIntegrationTestRule();

	@BeforeClass
	public static void setUpClass() {
		BundleContext bundleContext = SystemBundleUtil.getBundleContext();

		_serviceRegistration = bundleContext.registerService(
			Captcha.class, new TestCaptcha(),
			HashMapDictionaryBuilder.<String, Object>put(
				"captcha.engine.impl",
				"com.liferay.captcha.configuration.admin.definition.test." +
					"CaptchaConfigurationFieldOptionsProviderTest$TestCaptcha"
			).build());
	}

	@AfterClass
	public static void tearDownClass() {
		_serviceRegistration.unregister();
	}

	@Test
	public void testGetOptions() {
		List<ConfigurationFieldOptionsProvider.Option> options =
			_configurationFieldOptionsProvider.getOptions();

		_assertContainsOption(new ReCaptchaImpl(), options);
		_assertContainsOption(new SimpleCaptchaImpl(), options);
		_assertContainsOption(new TestCaptcha(), options);
	}

	public static class TestCaptcha extends SimpleCaptchaImpl {

		@Override
		public String getName() {
			return "TestCaptcha";
		}

	}

	private void _assertContainsOption(
		Captcha captcha,
		List<ConfigurationFieldOptionsProvider.Option> options) {

		Assert.assertTrue(
			ListUtil.exists(
				options,
				option -> {
					Class<? extends Captcha> clazz = captcha.getClass();

					if (Objects.equals(
							captcha.getName(),
							option.getLabel(LocaleUtil.getDefault())) &&
						Objects.equals(clazz.getName(), option.getValue())) {

						return true;
					}

					return false;
				}));
	}

	private static ServiceRegistration<Captcha> _serviceRegistration;

	@Inject(
		filter = "(&(configuration.pid=com.liferay.captcha.configuration.CaptchaConfiguration)(configuration.field.name=captchaEngine))"
	)
	private ConfigurationFieldOptionsProvider
		_configurationFieldOptionsProvider;

}