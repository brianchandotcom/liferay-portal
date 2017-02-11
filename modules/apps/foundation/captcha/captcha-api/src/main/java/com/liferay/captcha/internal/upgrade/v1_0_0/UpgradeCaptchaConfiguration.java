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
import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
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

	protected void doUpgrade() throws Exception {
		CaptchaConfiguration defaultConfig =
			ConfigurableUtil.createConfigurable(
				CaptchaConfiguration.class, new HashMapDictionary<>());

		Dictionary properties = new HashMapDictionary();

		properties.put(
			"captchaEngine",
			_prefsProps.getString(
				LegacyCaptchaPropsKeys.CAPTCHA_ENGINE_IMPL,
				defaultConfig.captchaEngine()));
		properties.put(
			"createAccountCaptchaEnabled",
			_prefsProps.getBoolean(
				LegacyCaptchaPropsKeys.CAPTCHA_CHECK_PORTAL_CREATE_ACCOUNT,
				defaultConfig.createAccountCaptchaEnabled()));
		properties.put(
			"maxChallenges",
			_prefsProps.getInteger(
				LegacyCaptchaPropsKeys.CAPTCHA_MAX_CHALLENGES,
				defaultConfig.maxChallenges()));
		properties.put(
			"messageBoardsEditCategoryCaptchaEnabled",
			_prefsProps.getBoolean(
				LegacyCaptchaPropsKeys.
					CAPTCHA_CHECK_PORTLET_MESSAGE_BOARDS_EDIT_CATEGORY,
				defaultConfig.messageBoardsEditCategoryCaptchaEnabled()));
		properties.put(
			"messageBoardsEditMessageCaptchaEnabled",
			_prefsProps.getBoolean(
				LegacyCaptchaPropsKeys.
					CAPTCHA_CHECK_PORTLET_MESSAGE_BOARDS_EDIT_MESSAGE,
				defaultConfig.messageBoardsEditMessageCaptchaEnabled()));
		properties.put(
			"reCaptchaNoScriptUrl",
			_prefsProps.getString(
				LegacyCaptchaPropsKeys.CAPTCHA_ENGINE_RECAPTCHA_URL_NOSCRIPT,
				defaultConfig.reCaptchaNoScriptUrl()));

		String reCaptchaPrivateKey = _prefsProps.getString(
			LegacyCaptchaPropsKeys.CAPTCHA_ENGINE_RECAPTCHA_KEY_PRIVATE,
			defaultConfig.reCaptchaPrivateKey());

		if (Validator.isNull(reCaptchaPrivateKey)) {
			reCaptchaPrivateKey = StringPool.BLANK;
		}

		properties.put("reCaptchaPrivateKey", reCaptchaPrivateKey);

		String reCaptchaPublicKey = _prefsProps.getString(
			LegacyCaptchaPropsKeys.CAPTCHA_ENGINE_RECAPTCHA_KEY_PUBLIC,
			defaultConfig.reCaptchaPublicKey());

		if (Validator.isNull(reCaptchaPublicKey)) {
			reCaptchaPublicKey = StringPool.BLANK;
		}

		properties.put("reCaptchaPublicKey", reCaptchaPublicKey);

		properties.put(
			"reCaptchaScriptUrl",
			_prefsProps.getString(
				LegacyCaptchaPropsKeys.CAPTCHA_ENGINE_RECAPTCHA_URL_SCRIPT,
				defaultConfig.reCaptchaScriptUrl()));
		properties.put(
			"reCaptchaVerifyUrl",
			_prefsProps.getString(
				LegacyCaptchaPropsKeys.CAPTCHA_ENGINE_RECAPTCHA_URL_VERIFY,
				defaultConfig.reCaptchaVerifyUrl()));
		properties.put(
			"sendPasswordCaptchaEnabled",
			_prefsProps.getBoolean(
				LegacyCaptchaPropsKeys.CAPTCHA_CHECK_PORTAL_SEND_PASSWORD,
				defaultConfig.sendPasswordCaptchaEnabled()));
		properties.put(
			"simpleCaptchaBackgroundProducers",
			_prefsProps.getStringArray(
				LegacyCaptchaPropsKeys.
					CAPTCHA_ENGINE_SIMPLECAPTCHA_BACKGROUND_PRODUCERS,
				StringPool.COMMA,
				defaultConfig.simpleCaptchaBackgroundProducers()));
		properties.put(
			"simpleCaptchaGimpyRenderers",
			_prefsProps.getStringArray(
				LegacyCaptchaPropsKeys.
					CAPTCHA_ENGINE_SIMPLECAPTCHA_GIMPY_RENDERERS,
				StringPool.COMMA, defaultConfig.simpleCaptchaGimpyRenderers()));
		properties.put(
			"simpleCaptchaHeight",
			_prefsProps.getInteger(
				LegacyCaptchaPropsKeys.CAPTCHA_ENGINE_SIMPLECAPTCHA_HEIGHT,
				defaultConfig.simpleCaptchaHeight()));
		properties.put(
			"simpleCaptchaNoiseProducers",
			_prefsProps.getStringArray(
				LegacyCaptchaPropsKeys.
					CAPTCHA_ENGINE_SIMPLECAPTCHA_NOISE_PRODUCERS,
				StringPool.COMMA, defaultConfig.simpleCaptchaNoiseProducers()));
		properties.put(
			"simpleCaptchaTextProducers",
			_prefsProps.getStringArray(
				LegacyCaptchaPropsKeys.
					CAPTCHA_ENGINE_SIMPLECAPTCHA_TEXT_PRODUCERS,
				StringPool.COMMA, defaultConfig.simpleCaptchaTextProducers()));
		properties.put(
			"simpleCaptchaWidth",
			_prefsProps.getInteger(
				LegacyCaptchaPropsKeys.CAPTCHA_ENGINE_SIMPLECAPTCHA_WIDTH,
				defaultConfig.simpleCaptchaWidth()));
		properties.put(
			"simpleCaptchaWordRenderers",
			_prefsProps.getStringArray(
				LegacyCaptchaPropsKeys.
					CAPTCHA_ENGINE_SIMPLECAPTCHA_WORD_RENDERERS,
				StringPool.COMMA, defaultConfig.simpleCaptchaWordRenderers()));

		Configuration configuration = _configurationAdmin.getConfiguration(
			CaptchaConfiguration.class.getName(), StringPool.QUESTION);

		configuration.update(properties);

		PortletPreferences portletPreferences = _prefsProps.getPreferences();

		for (String key : LegacyCaptchaPropsKeys.KEYS) {
			portletPreferences.reset(key);
		}
	}

	private final ConfigurationAdmin _configurationAdmin;
	private final PrefsProps _prefsProps;

}