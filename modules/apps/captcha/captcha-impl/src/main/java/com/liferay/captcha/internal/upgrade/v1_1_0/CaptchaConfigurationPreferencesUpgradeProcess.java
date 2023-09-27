/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.captcha.internal.upgrade.v1_1_0;

import com.liferay.captcha.internal.constants.LegacyCaptchaPropsKeys;
import com.liferay.petra.function.transform.TransformUtil;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.GetterUtil;

import java.util.Dictionary;

import org.osgi.service.cm.Configuration;
import org.osgi.service.cm.ConfigurationAdmin;

/**
 * @author Marta Medio
 */
public class CaptchaConfigurationPreferencesUpgradeProcess
	extends UpgradeProcess {

	public CaptchaConfigurationPreferencesUpgradeProcess(
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

		return TransformUtil.transform(
			array, s -> target.equals(s.trim()) ? replacement : s,
			String.class);
	}

	private final ConfigurationAdmin _configurationAdmin;

}