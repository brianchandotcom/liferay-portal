/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.captcha.internal.configuration.persistence.listener.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.captcha.recaptcha.ReCaptchaImpl;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.configuration.persistence.listener.ConfigurationModelListener;
import com.liferay.portal.configuration.persistence.listener.ConfigurationModelListenerException;
import com.liferay.portal.kernel.util.HashMapDictionaryBuilder;
import com.liferay.portal.kernel.util.LocaleThreadLocal;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.util.Dictionary;
import java.util.Locale;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Christian Moura
 */
@RunWith(Arquillian.class)
public class CaptchaConfigurationModelListenerTest {

	@ClassRule
	@Rule
	public static final LiferayIntegrationTestRule liferayIntegrationTestRule =
		new LiferayIntegrationTestRule();

	@BeforeClass
	public static void setUpClass() {
		_locale = LocaleThreadLocal.getThemeDisplayLocale();

		LocaleThreadLocal.setThemeDisplayLocale(LocaleUtil.ENGLISH);

		_reCaptchaProperties = HashMapDictionaryBuilder.<String, Object>put(
			"captchaEngine", ReCaptchaImpl.class.getName()
		).put(
			"reCaptchaNoScriptURL",
			"https://www.google.com/recaptcha/api/fallback?k="
		).put(
			"reCaptchaPrivateKey", "test"
		).put(
			"reCaptchaPublicKey", "test"
		).put(
			"reCaptchaScriptURL", "https://www.google.com/recaptcha/api.js"
		).put(
			"reCaptchaVerifyURL",
			"https://www.google.com/recaptcha/api/siteverify"
		).build();
	}

	@AfterClass
	public static void tearDownClass() {
		LocaleThreadLocal.setThemeDisplayLocale(_locale);
	}

	@Test
	public void testValidateReCaptchaNoScriptURL() throws Exception {
		_assertPropertyThrowsException(
			"reCaptchaNoScriptURL",
			"https://www.test.com/recaptcha/api/fallback?k=",
			"The reCAPTCHA no script URL is not valid.");
	}

	@Test
	public void testValidateReCaptchaPrivateKey() throws Exception {
		_assertPropertyThrowsException(
			"reCaptchaPrivateKey", StringPool.BLANK,
			"The reCAPTCHA private key is not valid.");
	}

	@Test
	public void testValidateReCaptchaPublicKey() throws Exception {
		_assertPropertyThrowsException(
			"reCaptchaPublicKey", StringPool.BLANK,
			"The reCAPTCHA public key is not valid.");
	}

	@Test
	public void testValidateReCaptchaScriptURL() throws Exception {
		_assertPropertyThrowsException(
			"reCaptchaScriptURL", "https://www.test.com/recaptcha/api.js",
			"The reCAPTCHA script URL is not valid.");
	}

	@Test
	public void testValidateReCaptchaVerifyURL() throws Exception {
		_assertPropertyThrowsException(
			"reCaptchaVerifyURL",
			"https://www.test.com/recaptcha/api/siteverify",
			"The reCAPTCHA verify URL is not valid.");
	}

	private void _assertPropertyThrowsException(
			String key, String value, String exceptionMessage)
		throws Exception {

		try (AutoCloseable autoCloseable = _swapReCaptchaConfiguration(
				_reCaptchaProperties, key, value)) {

			_configurationModelListener.onBeforeSave(
				StringPool.BLANK, _reCaptchaProperties);

			Assert.fail();
		}
		catch (ConfigurationModelListenerException
					configurationModelListenerException) {

			Assert.assertTrue(
				configurationModelListenerException.getMessage(
				).contains(
					exceptionMessage
				));
		}
	}

	private AutoCloseable _swapReCaptchaConfiguration(
		Dictionary<String, Object> properties, String key, String value) {

		String previousValue = (String)properties.put(key, value);

		return () -> properties.put(key, previousValue);
	}

	private static Locale _locale;
	private static Dictionary<String, Object> _reCaptchaProperties;

	@Inject(
		filter = "model.class.name=com.liferay.captcha.configuration.CaptchaConfiguration"
	)
	private ConfigurationModelListener _configurationModelListener;

}