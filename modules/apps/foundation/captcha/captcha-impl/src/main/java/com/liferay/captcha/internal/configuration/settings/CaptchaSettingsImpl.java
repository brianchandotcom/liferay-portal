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

package com.liferay.captcha.internal.configuration.settings;

import com.liferay.captcha.configuration.CaptchaConfiguration;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.kernel.captcha.CaptchaSettings;

import java.util.Dictionary;
import java.util.Hashtable;
import java.util.Map;

import org.osgi.service.cm.Configuration;
import org.osgi.service.cm.ConfigurationAdmin;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Modified;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Pei-Jung Lan
 */
@Component(
	configurationPid = "com.liferay.captcha.configuration.CaptchaConfiguration",
	immediate = true, service = CaptchaSettings.class
)
public class CaptchaSettingsImpl implements CaptchaSettings {

	@Override
	public String getCaptchaEngine() {
		return _captchaConfiguration.captchaEngine();
	}

	@Override
	public int getMaxChallenges() {
		return _captchaConfiguration.maxChallenges();
	}

	@Override
	public String getReCaptchaNoScriptURL() {
		return _captchaConfiguration.reCaptchaNoScriptURL();
	}

	@Override
	public String getReCaptchaPrivateKey() {
		return _captchaConfiguration.reCaptchaPrivateKey();
	}

	@Override
	public String getReCaptchaPublicKey() {
		return _captchaConfiguration.reCaptchaPublicKey();
	}

	@Override
	public String getReCaptchaScriptURL() {
		return _captchaConfiguration.reCaptchaScriptURL();
	}

	@Override
	public String getReCaptchaVerifyURL() {
		return _captchaConfiguration.reCaptchaVerifyURL();
	}

	@Override
	public String[] getSimpleCaptchaBackgroundProducers() {
		return _captchaConfiguration.simpleCaptchaBackgroundProducers();
	}

	@Override
	public String[] getSimpleCaptchaGimpyRenderers() {
		return _captchaConfiguration.simpleCaptchaGimpyRenderers();
	}

	@Override
	public int getSimpleCaptchaHeight() {
		return _captchaConfiguration.simpleCaptchaHeight();
	}

	@Override
	public String[] getSimpleCaptchaNoiseProducers() {
		return _captchaConfiguration.simpleCaptchaNoiseProducers();
	}

	@Override
	public String[] getSimpleCaptchaTextProducers() {
		return _captchaConfiguration.simpleCaptchaTextProducers();
	}

	@Override
	public int getSimpleCaptchaWidth() {
		return _captchaConfiguration.simpleCaptchaWidth();
	}

	@Override
	public String[] getSimpleCaptchaWordRenderers() {
		return _captchaConfiguration.simpleCaptchaWordRenderers();
	}

	@Override
	public boolean isCreateAccountCaptchaEnabled() {
		return _captchaConfiguration.createAccountCaptchaEnabled();
	}

	@Override
	public boolean isMessageBoardsEditCategoryCaptchaEnabled() {
		return _captchaConfiguration.messageBoardsEditCategoryCaptchaEnabled();
	}

	@Override
	public boolean isMessageBoardsEditMessageCaptchaEnabled() {
		return _captchaConfiguration.messageBoardsEditMessageCaptchaEnabled();
	}

	@Override
	public boolean isSendPasswordCaptchaEnabled() {
		return _captchaConfiguration.sendPasswordCaptchaEnabled();
	}

	@Override
	public void setCaptchaEngine(String className) throws Exception {
		Configuration configuration = _configurationAdmin.getConfiguration(
			"com.liferay.captcha.configuration.CaptchaConfiguration",
			StringPool.QUESTION);

		Dictionary<String, Object> properties = configuration.getProperties();

		if (properties == null) {
			properties = new Hashtable<>();
		}

		properties.put("captchaEngine", className);

		configuration.update(properties);
	}

	@Activate
	@Modified
	protected void activate(Map<String, Object> properties) {
		_captchaConfiguration = ConfigurableUtil.createConfigurable(
			CaptchaConfiguration.class, properties);
	}

	private CaptchaConfiguration _captchaConfiguration;

	@Reference
	private ConfigurationAdmin _configurationAdmin;

}