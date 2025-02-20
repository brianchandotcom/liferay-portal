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
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.util.HashMapDictionaryBuilder;
import com.liferay.portal.kernel.util.LocaleThreadLocal;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.StringUtil;
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
	public void testOnBeforeSave() throws Exception {
		_testOnBeforeSave(
			"reCaptchaNoScriptURL",
			"https://www.test.com/recaptcha/api/fallback?k=",
			"the-recaptcha-no-script-url-is-not-valid");
		_testOnBeforeSave(
			"reCaptchaPrivateKey", StringPool.BLANK,
			"the-recaptcha-public-key-is-not-valid");
		_testOnBeforeSave(
			"reCaptchaPublicKey", StringPool.BLANK,
			"the-recaptcha-private-key-is-not-valid");
		_testOnBeforeSave(
			"reCaptchaScriptURL", "https://www.test.com/recaptcha/api.js",
			"the-recaptcha-script-url-is-not-valid");
		_testOnBeforeSave(
			"reCaptchaVerifyURL",
			"https://www.test.com/recaptcha/api/siteverify",
			"the-recaptcha-verify-url-is-not-valid");
	}

	private AutoCloseable _swapReCaptchaConfiguration(
		String key, String value) {

		String previousValue = (String)_reCaptchaProperties.put(key, value);

		return () -> _reCaptchaProperties.put(key, previousValue);
	}

	private void _testOnBeforeSave(
			String key, String value, String exceptionMessageKey)
		throws Exception {

		try (AutoCloseable autoCloseable = _swapReCaptchaConfiguration(
				key, value)) {

			_configurationModelListener.onBeforeSave(
				StringPool.BLANK, _reCaptchaProperties);

			Assert.fail();
		}
		catch (ConfigurationModelListenerException
					configurationModelListenerException) {

			Assert.assertTrue(
				StringUtil.contains(
					configurationModelListenerException.getMessage(),
					_language.get(LocaleUtil.US, exceptionMessageKey),
					StringPool.BLANK));
		}
	}

	private static Locale _locale;
	private static Dictionary<String, Object> _reCaptchaProperties;

	@Inject(
		filter = "model.class.name=com.liferay.captcha.configuration.CaptchaConfiguration"
	)
	private ConfigurationModelListener _configurationModelListener;

	@Inject
	private Language _language;

}