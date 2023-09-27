/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.captcha.internal.constants;

/**
 * @author Pei-Jung Lan
 */
public class LegacyCaptchaPropsKeys {

	public static final String CAPTCHA_CHECK_PORTAL_CREATE_ACCOUNT =
		"captcha.check.portal.create_account";

	public static final String CAPTCHA_CHECK_PORTAL_SEND_PASSWORD =
		"captcha.check.portal.send_password";

	public static final String
		CAPTCHA_CHECK_PORTLET_MESSAGE_BOARDS_EDIT_CATEGORY =
			"captcha.check.portlet.message_boards.edit_category";

	public static final String
		CAPTCHA_CHECK_PORTLET_MESSAGE_BOARDS_EDIT_MESSAGE =
			"captcha.check.portlet.message_boards.edit_message";

	public static final String
		CAPTCHA_CONFIGURATION_SIMPLECAPTCHA_BLOCK_GYMPY_RENDERER_CLASS =
			"com.liferay.captcha.simplecaptcha.gimpy.BlockGimpyRenderer";

	public static final String
		CAPTCHA_CONFIGURATION_SIMPLECAPTCHA_BLOCK_GYMPY_RENDERER_DEPRECATED_CLASS =
			"nl.captcha.gimpy.BlockGimpyRenderer";

	public static final String
		CAPTCHA_CONFIGURATION_SIMPLECAPTCHA_DROP_SHADOW_GYMPY_RENDERER_CLASS =
			"com.liferay.captcha.simplecaptcha.gimpy.DropShadowGimpyRenderer";

	public static final String
		CAPTCHA_CONFIGURATION_SIMPLECAPTCHA_DROP_SHADOW_GYMPY_RENDERER_DEPRECATED_CLASS =
			"nl.captcha.gimpy.DropShadowGimpyRenderer";

	public static final String
		CAPTCHA_CONFIGURATION_SIMPLECAPTCHA_GIMPY_RENDERERS_PROPERTY =
			"simpleCaptchaGimpyRenderers";

	public static final String
		CAPTCHA_CONFIGURATION_SIMPLECAPTCHA_RIPPLE_GYMPY_RENDERER_CLASS =
			"com.liferay.captcha.simplecaptcha.gimpy.RippleGimpyRenderer";

	public static final String
		CAPTCHA_CONFIGURATION_SIMPLECAPTCHA_RIPPLE_GYMPY_RENDERER_DEPRECATED_CLASS =
			"nl.captcha.gimpy.RippleGimpyRenderer";

	public static final String CAPTCHA_ENGINE_IMPL = "captcha.engine.impl";

	public static final String CAPTCHA_ENGINE_RECAPTCHA_KEY_PRIVATE =
		"captcha.engine.recaptcha.key.private";

	public static final String CAPTCHA_ENGINE_RECAPTCHA_KEY_PUBLIC =
		"captcha.engine.recaptcha.key.public";

	public static final String CAPTCHA_ENGINE_RECAPTCHA_URL_NOSCRIPT =
		"captcha.engine.recaptcha.url.noscript";

	public static final String CAPTCHA_ENGINE_RECAPTCHA_URL_SCRIPT =
		"captcha.engine.recaptcha.url.script";

	public static final String CAPTCHA_ENGINE_RECAPTCHA_URL_VERIFY =
		"captcha.engine.recaptcha.url.verify";

	public static final String
		CAPTCHA_ENGINE_SIMPLECAPTCHA_BACKGROUND_PRODUCERS =
			"captcha.engine.simplecaptcha.background.producers";

	public static final String CAPTCHA_ENGINE_SIMPLECAPTCHA_GIMPY_RENDERERS =
		"captcha.engine.simplecaptcha.gimpy.renderers";

	public static final String CAPTCHA_ENGINE_SIMPLECAPTCHA_HEIGHT =
		"captcha.engine.simplecaptcha.height";

	public static final String CAPTCHA_ENGINE_SIMPLECAPTCHA_NOISE_PRODUCERS =
		"captcha.engine.simplecaptcha.noise.producers";

	public static final String CAPTCHA_ENGINE_SIMPLECAPTCHA_TEXT_PRODUCERS =
		"captcha.engine.simplecaptcha.text.producers";

	public static final String CAPTCHA_ENGINE_SIMPLECAPTCHA_WIDTH =
		"captcha.engine.simplecaptcha.width";

	public static final String CAPTCHA_ENGINE_SIMPLECAPTCHA_WORD_RENDERERS =
		"captcha.engine.simplecaptcha.word.renderers";

	public static final String[] CAPTCHA_KEYS = {
		CAPTCHA_CHECK_PORTAL_CREATE_ACCOUNT, CAPTCHA_CHECK_PORTAL_SEND_PASSWORD,
		CAPTCHA_CHECK_PORTLET_MESSAGE_BOARDS_EDIT_CATEGORY,
		CAPTCHA_CHECK_PORTLET_MESSAGE_BOARDS_EDIT_MESSAGE, CAPTCHA_ENGINE_IMPL,
		CAPTCHA_ENGINE_RECAPTCHA_KEY_PRIVATE,
		CAPTCHA_ENGINE_RECAPTCHA_KEY_PUBLIC,
		CAPTCHA_ENGINE_RECAPTCHA_URL_NOSCRIPT,
		CAPTCHA_ENGINE_RECAPTCHA_URL_SCRIPT,
		CAPTCHA_ENGINE_RECAPTCHA_URL_VERIFY,
		CAPTCHA_ENGINE_SIMPLECAPTCHA_BACKGROUND_PRODUCERS,
		CAPTCHA_ENGINE_SIMPLECAPTCHA_GIMPY_RENDERERS,
		CAPTCHA_ENGINE_SIMPLECAPTCHA_HEIGHT,
		CAPTCHA_ENGINE_SIMPLECAPTCHA_NOISE_PRODUCERS,
		CAPTCHA_ENGINE_SIMPLECAPTCHA_TEXT_PRODUCERS,
		CAPTCHA_ENGINE_SIMPLECAPTCHA_WIDTH,
		CAPTCHA_ENGINE_SIMPLECAPTCHA_WORD_RENDERERS,
		LegacyCaptchaPropsKeys.CAPTCHA_MAX_CHALLENGES
	};

	public static final String CAPTCHA_MAX_CHALLENGES =
		"captcha.max.challenges";

}