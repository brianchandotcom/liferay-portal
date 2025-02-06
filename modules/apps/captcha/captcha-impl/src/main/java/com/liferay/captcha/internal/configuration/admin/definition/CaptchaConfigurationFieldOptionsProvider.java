/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.captcha.internal.configuration.admin.definition;

import com.liferay.captcha.provider.CaptchaProvider;
import com.liferay.configuration.admin.definition.ConfigurationFieldOptionsProvider;
import com.liferay.petra.function.transform.TransformUtil;
import com.liferay.portal.kernel.captcha.Captcha;

import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Pedro Victor Silvestre
 */
@Component(
	property = {
		"configuration.field.name=captchaEngine",
		"configuration.pid=com.liferay.captcha.configuration.CaptchaConfiguration"
	},
	service = ConfigurationFieldOptionsProvider.class
)
public class CaptchaConfigurationFieldOptionsProvider
	implements ConfigurationFieldOptionsProvider {

	@Override
	public List<Option> getOptions() {
		Map<String, Captcha> captchas = _captchaProvider.getCaptchas();

		return TransformUtil.transform(
			captchas.entrySet(),
			entry -> new Option() {

				@Override
				public String getLabel(Locale locale) {
					Captcha captcha = entry.getValue();

					return captcha.getName();
				}

				@Override
				public String getValue() {
					return entry.getKey();
				}

			});
	}

	@Reference
	private CaptchaProvider _captchaProvider;

}