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

package com.liferay.captcha.configuration;

import aQute.bnd.annotation.metatype.Meta;

import com.liferay.portal.configuration.metatype.annotations.ExtendedObjectClassDefinition;

/**
 * @author Pei-Jung Lan
 */
@ExtendedObjectClassDefinition(category = "security-tools")
@Meta.OCD(
	id = "com.liferay.captcha.configuration.CaptchaConfiguration",
	localization = "content/Language", name = "captcha-configuration-name"
)
public interface CaptchaConfiguration {

	@Meta.AD(
		deflt = "1", description = "max-challenges-help",
		name = "max-challenges", required = false
	)
	public int maxChallenges();

	@Meta.AD(
		deflt = "true", name = "create-account-captcha-enabled",
		required = false
	)
	public boolean createAccountCaptchaEnabled();

	@Meta.AD(
		deflt = "true", name = "send-password-captcha-enabled", required = false
	)
	public boolean sendPasswordCaptchaEnabled();

	@Meta.AD(
		deflt = "false", name = "message-boards-edit-category-captcha-enabled",
		required = false
	)
	public boolean messageBoardsEditCategoryCaptchaEnabled();

	@Meta.AD(
		deflt = "false", name = "message-boards-edit-message-captcha-enabled",
		required = false
	)
	public boolean messageBoardsEditMessageCaptchaEnabled();

	@Meta.AD(
		deflt = "com.liferay.captcha.simplecaptcha.SimpleCaptchaImpl",
		description = "captcha-engine-help", name = "captcha-engine",
		optionLabels = {"SimpleCaptcha", "reCAPTCHA"},
		optionValues = {
			"com.liferay.captcha.simplecaptcha.SimpleCaptchaImpl",
			"com.liferay.captcha.recaptcha.ReCaptchaImpl"
		},
		required = false
	)
	public String captchaEngine();

	@Meta.AD(name = "recaptcha-public-key", required = false)
	public String reCaptchaPublicKey();

	@Meta.AD(name = "recaptcha-private-key", required = false)
	public String reCaptchaPrivateKey();

	@Meta.AD(
		deflt = "https://www.google.com/recaptcha/api.js",
		name = "recaptcha-script-url", required = false
	)
	public String reCaptchaScriptURL();

	@Meta.AD(
		deflt = "https://www.google.com/recaptcha/api/fallback?k=",
		name = "recaptcha-no-script-url", required = false
	)
	public String reCaptchaNoScriptURL();

	@Meta.AD(
		deflt = "https://www.google.com/recaptcha/api/siteverify",
		name = "recaptcha-verify-url", required = false
	)
	public String reCaptchaVerifyURL();

	@Meta.AD(
		deflt = "50", description = "simple-captcha-height-help",
		name = "simple-captcha-height", required = false
	)
	public int simpleCaptchaHeight();

	@Meta.AD(
		deflt = "150", description = "simple-captcha-width-help",
		name = "simple-captcha-width", required = false
	)
	public int simpleCaptchaWidth();

	@Meta.AD(
		deflt = "nl.captcha.backgrounds.FlatColorBackgroundProducer|nl.captcha.backgrounds.GradiatedBackgroundProducer|nl.captcha.backgrounds.SquigglesBackgroundProducer|nl.captcha.backgrounds.TransparentBackgroundProducer",
		description = "simple-captcha-background-producers-help",
		name = "simple-captcha-background-producers", required = false
	)
	public String[] simpleCaptchaBackgroundProducers();

	@Meta.AD(
		deflt = "com.liferay.captcha.simplecaptcha.gimpy.BlockGimpyRenderer|com.liferay.captcha.simplecaptcha.gimpy.DropShadowGimpyRenderer|nl.captcha.gimpy.FishEyeGimpyRenderer|com.liferay.captcha.simplecaptcha.gimpy.RippleGimpyRenderer|nl.captcha.gimpy.ShearGimpyRenderer",
		description = "simple-captcha-gimpy-renderers-help",
		name = "simple-captcha-gimpy-renderers", required = false
	)
	public String[] simpleCaptchaGimpyRenderers();

	@Meta.AD(
		deflt = "nl.captcha.noise.CurvedLineNoiseProducer|nl.captcha.noise.StraightLineNoiseProducer",
		description = "simple-captcha-noise-producers-help",
		name = "simple-captcha-noise-producers", required = false
	)
	public String[] simpleCaptchaNoiseProducers();

	@Meta.AD(
		deflt = "com.liferay.captcha.simplecaptcha.DictionaryWordTextProducer|com.liferay.captcha.simplecaptcha.PinNumberTextProducer|nl.captcha.text.producer.DefaultTextProducer|nl.captcha.text.producer.FiveLetterFirstNameTextProducer",
		description = "simple-captcha-text-producers-help",
		name = "simple-captcha-text-producers", required = false
	)
	public String[] simpleCaptchaTextProducers();

	@Meta.AD(
		deflt = "nl.captcha.text.renderer.DefaultWordRenderer",
		description = "simple-captcha-word-renderers-help",
		name = "simple-captcha-word-renderers", required = false
	)
	public String[] simpleCaptchaWordRenderers();

}