/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.captcha.internal.upgrade.v1_0_0;

import com.liferay.captcha.configuration.CaptchaConfiguration;
import com.liferay.captcha.constants.LegacyCaptchaPropsKeys;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.HashMapDictionary;
import com.liferay.portal.kernel.util.PrefsProps;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;

import java.util.Dictionary;

import javax.portlet.PortletPreferences;

import org.osgi.service.cm.Configuration;
import org.osgi.service.cm.ConfigurationAdmin;

/**
 * @author Pei-Jung Lan
 */
public class UpgradeCaptchaConfiguration extends UpgradeProcess {

	public UpgradeCaptchaConfiguration(
		ConfigurationAdmin configurationAdmin, PrefsProps prefsProps) {

		_configurationAdmin = configurationAdmin;
		_prefsProps = prefsProps;
	}

	@Override
	protected void doUpgrade() throws Exception {
		Configuration configuration = _configurationAdmin.getConfiguration(
			CaptchaConfiguration.class.getName(), StringPool.QUESTION);

		Dictionary properties = configuration.getProperties();

		if (properties == null) {
			properties = new HashMapDictionary();
		}

		if (Validator.isNotNull(
				_prefsProps.getString(
					LegacyCaptchaPropsKeys.CAPTCHA_ENGINE_IMPL))) {

			properties.put(
				"captchaEngine",
				_prefsProps.getString(
					LegacyCaptchaPropsKeys.CAPTCHA_ENGINE_IMPL));
		}

		if (Validator.isNotNull(
				_prefsProps.getString(
					LegacyCaptchaPropsKeys.
						CAPTCHA_CHECK_PORTAL_CREATE_ACCOUNT))) {

			properties.put(
				"createAccountCaptchaEnabled",
				_prefsProps.getBoolean(
					LegacyCaptchaPropsKeys.
						CAPTCHA_CHECK_PORTAL_CREATE_ACCOUNT));
		}

		if (Validator.isNotNull(
				_prefsProps.getString(
					LegacyCaptchaPropsKeys.CAPTCHA_MAX_CHALLENGES))) {

			properties.put(
				"maxChallenges",
				_prefsProps.getInteger(
					LegacyCaptchaPropsKeys.CAPTCHA_MAX_CHALLENGES));
		}

		if (Validator.isNotNull(
				_prefsProps.getString(
					LegacyCaptchaPropsKeys.
						CAPTCHA_CHECK_PORTLET_MESSAGE_BOARDS_EDIT_CATEGORY))) {

			properties.put(
				"messageBoardsEditCategoryCaptchaEnabled",
				_prefsProps.getBoolean(
					LegacyCaptchaPropsKeys.
						CAPTCHA_CHECK_PORTLET_MESSAGE_BOARDS_EDIT_CATEGORY));
		}

		if (Validator.isNotNull(
				_prefsProps.getString(
					LegacyCaptchaPropsKeys.
						CAPTCHA_CHECK_PORTLET_MESSAGE_BOARDS_EDIT_MESSAGE))) {

			properties.put(
				"messageBoardsEditMessageCaptchaEnabled",
				_prefsProps.getBoolean(
					LegacyCaptchaPropsKeys.
						CAPTCHA_CHECK_PORTLET_MESSAGE_BOARDS_EDIT_MESSAGE));
		}

		if (Validator.isNotNull(
				_prefsProps.getString(
					LegacyCaptchaPropsKeys.
						CAPTCHA_ENGINE_RECAPTCHA_URL_NOSCRIPT))) {

			properties.put(
				"reCaptchaNoScriptURL",
				_prefsProps.getString(
					LegacyCaptchaPropsKeys.
						CAPTCHA_ENGINE_RECAPTCHA_URL_NOSCRIPT));
		}

		if (Validator.isNotNull(
				_prefsProps.getString(
					LegacyCaptchaPropsKeys.
						CAPTCHA_ENGINE_RECAPTCHA_KEY_PRIVATE))) {

			properties.put(
				"reCaptchaPrivateKey",
				_prefsProps.getString(
					LegacyCaptchaPropsKeys.
						CAPTCHA_ENGINE_RECAPTCHA_KEY_PRIVATE));
		}

		if (Validator.isNotNull(
				_prefsProps.getString(
					LegacyCaptchaPropsKeys.
						CAPTCHA_ENGINE_RECAPTCHA_KEY_PUBLIC))) {

			properties.put(
				"reCaptchaPublicKey",
				_prefsProps.getString(
					LegacyCaptchaPropsKeys.
						CAPTCHA_ENGINE_RECAPTCHA_KEY_PUBLIC));
		}

		if (Validator.isNotNull(
				_prefsProps.getString(
					LegacyCaptchaPropsKeys.
						CAPTCHA_ENGINE_RECAPTCHA_URL_SCRIPT))) {

			properties.put(
				"reCaptchaScriptURL",
				_prefsProps.getString(
					LegacyCaptchaPropsKeys.
						CAPTCHA_ENGINE_RECAPTCHA_URL_SCRIPT));
		}

		if (Validator.isNotNull(
				_prefsProps.getString(
					LegacyCaptchaPropsKeys.
						CAPTCHA_ENGINE_RECAPTCHA_URL_VERIFY))) {

			properties.put(
				"reCaptchaVerifyURL",
				_prefsProps.getString(
					LegacyCaptchaPropsKeys.
						CAPTCHA_ENGINE_RECAPTCHA_URL_VERIFY));
		}

		if (Validator.isNotNull(
				_prefsProps.getString(
					LegacyCaptchaPropsKeys.
						CAPTCHA_CHECK_PORTAL_SEND_PASSWORD))) {

			properties.put(
				"sendPasswordCaptchaEnabled",
				_prefsProps.getBoolean(
					LegacyCaptchaPropsKeys.CAPTCHA_CHECK_PORTAL_SEND_PASSWORD));
		}

		if (Validator.isNotNull(
				_prefsProps.getString(
					LegacyCaptchaPropsKeys.
						CAPTCHA_ENGINE_SIMPLECAPTCHA_BACKGROUND_PRODUCERS))) {

			properties.put(
				"simpleCaptchaBackgroundProducers",
				_prefsProps.getStringArray(
					LegacyCaptchaPropsKeys.
						CAPTCHA_ENGINE_SIMPLECAPTCHA_BACKGROUND_PRODUCERS,
					StringPool.COMMA));
		}

		if (Validator.isNotNull(
				_prefsProps.getString(
					LegacyCaptchaPropsKeys.
						CAPTCHA_ENGINE_SIMPLECAPTCHA_GIMPY_RENDERERS))) {

			properties.put(
				"simpleCaptchaGimpyRenderers",
				_prefsProps.getStringArray(
					LegacyCaptchaPropsKeys.
						CAPTCHA_ENGINE_SIMPLECAPTCHA_GIMPY_RENDERERS,
					StringPool.COMMA));
		}

		if (Validator.isNotNull(
				_prefsProps.getString(
					LegacyCaptchaPropsKeys.
						CAPTCHA_ENGINE_SIMPLECAPTCHA_HEIGHT))) {

			properties.put(
				"simpleCaptchaHeight",
				_prefsProps.getInteger(
					LegacyCaptchaPropsKeys.
						CAPTCHA_ENGINE_SIMPLECAPTCHA_HEIGHT));
		}

		if (Validator.isNotNull(
				_prefsProps.getString(
					LegacyCaptchaPropsKeys.
						CAPTCHA_ENGINE_SIMPLECAPTCHA_NOISE_PRODUCERS))) {

			properties.put(
				"simpleCaptchaNoiseProducers",
				_prefsProps.getStringArray(
					LegacyCaptchaPropsKeys.
						CAPTCHA_ENGINE_SIMPLECAPTCHA_NOISE_PRODUCERS,
					StringPool.COMMA));
		}

		if (Validator.isNotNull(
				_prefsProps.getString(
					LegacyCaptchaPropsKeys.
						CAPTCHA_ENGINE_SIMPLECAPTCHA_TEXT_PRODUCERS))) {

			properties.put(
				"simpleCaptchaTextProducers",
				_prefsProps.getStringArray(
					LegacyCaptchaPropsKeys.
						CAPTCHA_ENGINE_SIMPLECAPTCHA_TEXT_PRODUCERS,
					StringPool.COMMA));
		}

		if (Validator.isNotNull(
				_prefsProps.getString(
					LegacyCaptchaPropsKeys.
						CAPTCHA_ENGINE_SIMPLECAPTCHA_WIDTH))) {

			properties.put(
				"simpleCaptchaWidth",
				_prefsProps.getInteger(
					LegacyCaptchaPropsKeys.CAPTCHA_ENGINE_SIMPLECAPTCHA_WIDTH));
		}

		if (Validator.isNotNull(
				_prefsProps.getString(
					LegacyCaptchaPropsKeys.
						CAPTCHA_ENGINE_SIMPLECAPTCHA_WORD_RENDERERS))) {

			properties.put(
				"simpleCaptchaWordRenderers",
				_prefsProps.getStringArray(
					LegacyCaptchaPropsKeys.
						CAPTCHA_ENGINE_SIMPLECAPTCHA_WORD_RENDERERS,
					StringPool.COMMA));
		}

		configuration.update(properties);

		PortletPreferences portletPreferences = _prefsProps.getPreferences();

		for (String key : LegacyCaptchaPropsKeys.CAPTCHA_KEYS) {
			portletPreferences.reset(key);
		}
	}

	private final ConfigurationAdmin _configurationAdmin;
	private final PrefsProps _prefsProps;

}