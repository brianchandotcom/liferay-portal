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

import org.junit.Assert;
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

	@Test
	public void testOnBeforeSave() {
		LocaleThreadLocal.setThemeDisplayLocale(LocaleUtil.ENGLISH);

		Dictionary<String, Object> properties =
			HashMapDictionaryBuilder.<String, Object>put(
				"captchaEngine", ReCaptchaImpl.class.getName()
			).put(
				"reCaptchaNoScriptURL",
				"https://www.google.com/recaptcha/api/fallback?k="
			).put(
				"reCaptchaScriptURL", "https://www.google.com/recaptcha/api.js"
			).put(
				"reCaptchaVerifyURL",
				"https://www.google.com/recaptcha/api/siteverify"
			).build();

		_assertPropertyThrowsException(
			properties, "The reCAPTCHA public key is not valid.");

		properties.put("reCaptchaPublicKey", "test");

		_assertPropertyThrowsException(
			properties, "The reCAPTCHA private key is not valid.");

		properties.put("reCaptchaPrivateKey", "test");

		properties.put(
			"reCaptchaVerifyURL",
			"https://www.test.com/recaptcha/api/siteverify");

		_assertPropertyThrowsException(
			properties, "The reCAPTCHA verify url is not valid.");

		properties.put(
			"reCaptchaNoScriptURL",
			"https://www.test.com/recaptcha/api/fallback?k=");

		_assertPropertyThrowsException(
			properties, "The reCAPTCHA no script url is not valid.");

		properties.put(
			"reCaptchaScriptURL", "https://www.test.com/recaptcha/api.js");

		_assertPropertyThrowsException(
			properties, "The reCAPTCHA script url is not valid.");
	}

	private void _assertPropertyThrowsException(
		Dictionary<String, Object> properties, String exceptionMessage) {

		try {
			_configurationModelListener.onBeforeSave(
				StringPool.BLANK, properties);

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

	@Inject(
		filter = "model.class.name=com.liferay.captcha.configuration.CaptchaConfiguration"
	)
	private ConfigurationModelListener _configurationModelListener;

}