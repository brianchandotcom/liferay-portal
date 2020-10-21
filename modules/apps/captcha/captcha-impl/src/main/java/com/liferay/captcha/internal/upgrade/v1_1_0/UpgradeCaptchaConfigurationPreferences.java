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

package com.liferay.captcha.internal.upgrade.v1_1_0;

import com.liferay.captcha.internal.constants.LegacyCaptchaPropsKeys;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.GetterUtil;

import java.util.Arrays;
import java.util.Dictionary;
import java.util.stream.Stream;

import org.osgi.service.cm.Configuration;
import org.osgi.service.cm.ConfigurationAdmin;

/**
 * @author Marta Medio
 */
public class UpgradeCaptchaConfigurationPreferences extends UpgradeProcess {

	public UpgradeCaptchaConfigurationPreferences(
		ConfigurationAdmin configurationAdmin) {

		_configurationAdmin = configurationAdmin;
	}

	@Override
	protected void doUpgrade() throws Exception {
		Configuration configuration = _configurationAdmin.getConfiguration(
			"com.liferay.captcha.configuration.CaptchaConfiguration",
			StringPool.QUESTION);

		Dictionary<String, Object> properties = configuration.getProperties();

		if (properties == null) {
			return;
		}

		String[] simpleCaptchaGimpyRenderers = GetterUtil.getStringValues(
			properties.get(
				LegacyCaptchaPropsKeys.
					CAPTCHA_CONFIGURATION_SIMPLECAPTCHA_GIMPY_RENDERERS_PROPERTY));

		if (simpleCaptchaGimpyRenderers.length == 0) {
			return;
		}

		String[] upgradedSimpleCaptchaGimpyRenderers = _replaceArrayValue(
			simpleCaptchaGimpyRenderers,
			LegacyCaptchaPropsKeys.
				CAPTCHA_CONFIGURATION_SIMPLECAPTCHA_BLOCK_GYMPY_RENDERER_DEPRECATED_CLASS,
			LegacyCaptchaPropsKeys.
				CAPTCHA_CONFIGURATION_SIMPLECAPTCHA_BLOCK_GYMPY_RENDERER_CLASS);

		upgradedSimpleCaptchaGimpyRenderers = _replaceArrayValue(
			upgradedSimpleCaptchaGimpyRenderers,
			LegacyCaptchaPropsKeys.
				CAPTCHA_CONFIGURATION_SIMPLECAPTCHA_DROP_SHADOW_GYMPY_RENDERER_DEPRECATED_CLASS,
			LegacyCaptchaPropsKeys.
				CAPTCHA_CONFIGURATION_SIMPLECAPTCHA_DROP_SHADOW_GYMPY_RENDERER_CLASS);

		upgradedSimpleCaptchaGimpyRenderers = _replaceArrayValue(
			upgradedSimpleCaptchaGimpyRenderers,
			LegacyCaptchaPropsKeys.
				CAPTCHA_CONFIGURATION_SIMPLECAPTCHA_RIPPLE_GYMPY_RENDERER_DEPRECATED_CLASS,
			LegacyCaptchaPropsKeys.
				CAPTCHA_CONFIGURATION_SIMPLECAPTCHA_RIPPLE_GYMPY_RENDERER_CLASS);

		properties.put(
			LegacyCaptchaPropsKeys.
				CAPTCHA_CONFIGURATION_SIMPLECAPTCHA_GIMPY_RENDERERS_PROPERTY,
			upgradedSimpleCaptchaGimpyRenderers);

		configuration.update(properties);
	}

	private String[] _replaceArrayValue(
		String[] array, String target, String replacement) {

		Stream<String> stream = Arrays.stream(array);

		return stream.map(
			s -> target.equals(s.trim()) ? replacement : s
		).toArray(
			size -> new String[size]
		);
	}

	private final ConfigurationAdmin _configurationAdmin;

}