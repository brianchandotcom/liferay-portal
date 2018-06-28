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
import com.liferay.captcha.internal.constants.LegacyCaptchaPropsKeys;
import com.liferay.portal.configuration.upgrade.PrefsPropsToConfigurationUpgradeHelper;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.KeyValuePair;

/**
 * @author Pei-Jung Lan
 * @author Drew Brokke
 */
public class UpgradeCaptchaConfiguration extends UpgradeProcess {

	public UpgradeCaptchaConfiguration(
		PrefsPropsToConfigurationUpgradeHelper
			prefsPropsToConfigurationUpgradeHelper) {

		_prefsPropsToConfigurationUpgradeHelper =
			prefsPropsToConfigurationUpgradeHelper;
	}

	@Override
	protected void doUpgrade() throws Exception {
		_prefsPropsToConfigurationUpgradeHelper.mapConfigurations(
			CaptchaConfiguration.class,
			new KeyValuePair(
				LegacyCaptchaPropsKeys.CAPTCHA_ENGINE_IMPL, "captchaEngine"),
			new KeyValuePair(
				LegacyCaptchaPropsKeys.CAPTCHA_CHECK_PORTAL_CREATE_ACCOUNT,
				"createAccountCaptchaEnabled"),
			new KeyValuePair(
				LegacyCaptchaPropsKeys.CAPTCHA_MAX_CHALLENGES, "maxChallenges"),
			new KeyValuePair(
				LegacyCaptchaPropsKeys.
					CAPTCHA_CHECK_PORTLET_MESSAGE_BOARDS_EDIT_CATEGORY,
				"messageBoardsEditCategoryCaptchaEnabled"),
			new KeyValuePair(
				LegacyCaptchaPropsKeys.
					CAPTCHA_CHECK_PORTLET_MESSAGE_BOARDS_EDIT_MESSAGE,
				"messageBoardsEditMessageCaptchaEnabled"),
			new KeyValuePair(
				LegacyCaptchaPropsKeys.CAPTCHA_ENGINE_RECAPTCHA_URL_NOSCRIPT,
				"reCaptchaNoScriptURL"),
			new KeyValuePair(
				LegacyCaptchaPropsKeys.CAPTCHA_ENGINE_RECAPTCHA_KEY_PRIVATE,
				"reCaptchaPrivateKey"),
			new KeyValuePair(
				LegacyCaptchaPropsKeys.CAPTCHA_ENGINE_RECAPTCHA_KEY_PUBLIC,
				"reCaptchaPublicKey"),
			new KeyValuePair(
				LegacyCaptchaPropsKeys.CAPTCHA_ENGINE_RECAPTCHA_URL_SCRIPT,
				"reCaptchaScriptURL"),
			new KeyValuePair(
				LegacyCaptchaPropsKeys.CAPTCHA_ENGINE_RECAPTCHA_URL_VERIFY,
				"reCaptchaVerifyURL"),
			new KeyValuePair(
				LegacyCaptchaPropsKeys.CAPTCHA_CHECK_PORTAL_SEND_PASSWORD,
				"sendPasswordCaptchaEnabled"),
			new KeyValuePair(
				LegacyCaptchaPropsKeys.
					CAPTCHA_ENGINE_SIMPLECAPTCHA_BACKGROUND_PRODUCERS,
				"simpleCaptchaBackgroundProducers"),
			new KeyValuePair(
				LegacyCaptchaPropsKeys.
					CAPTCHA_ENGINE_SIMPLECAPTCHA_GIMPY_RENDERERS,
				"simpleCaptchaGimpyRenderers"),
			new KeyValuePair(
				LegacyCaptchaPropsKeys.CAPTCHA_ENGINE_SIMPLECAPTCHA_HEIGHT,
				"simpleCaptchaHeight"),
			new KeyValuePair(
				LegacyCaptchaPropsKeys.
					CAPTCHA_ENGINE_SIMPLECAPTCHA_NOISE_PRODUCERS,
				"simpleCaptchaNoiseProducers"),
			new KeyValuePair(
				LegacyCaptchaPropsKeys.
					CAPTCHA_ENGINE_SIMPLECAPTCHA_TEXT_PRODUCERS,
				"simpleCaptchaTextProducers"),
			new KeyValuePair(
				LegacyCaptchaPropsKeys.CAPTCHA_ENGINE_SIMPLECAPTCHA_WIDTH,
				"simpleCaptchaWidth"),
			new KeyValuePair(
				LegacyCaptchaPropsKeys.
					CAPTCHA_ENGINE_SIMPLECAPTCHA_WORD_RENDERERS,
				"simpleCaptchaWordRenderers"));
	}

	private final PrefsPropsToConfigurationUpgradeHelper
		_prefsPropsToConfigurationUpgradeHelper;

}