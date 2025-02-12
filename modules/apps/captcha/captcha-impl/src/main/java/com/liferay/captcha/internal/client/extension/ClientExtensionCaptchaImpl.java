/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.captcha.internal.client.extension;

import com.liferay.captcha.configuration.ClientExtensionCaptchaImplConfiguration;
import com.liferay.captcha.simplecaptcha.SimpleCaptchaImpl;
import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.kernel.captcha.Captcha;

import java.util.Map;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;

/**
 * @author Pedro Victor Silvestre
 */
@Component(
	configurationPid = "com.liferay.captcha.configuration.ClientExtensionCaptchaImplConfiguration",
	factory = "com.liferay.captcha.internal.client.extension.ClientExtensionCaptchaImpl",
	service = Captcha.class
)
public class ClientExtensionCaptchaImpl extends SimpleCaptchaImpl {

	@Override
	public String getName() {
		return _clientExtensionCaptchaImplConfiguration.captchaName();
	}

	@Activate
	protected void activate(Map<String, Object> properties) {
		_clientExtensionCaptchaImplConfiguration =
			ConfigurableUtil.createConfigurable(
				ClientExtensionCaptchaImplConfiguration.class, properties);
	}

	private volatile ClientExtensionCaptchaImplConfiguration
		_clientExtensionCaptchaImplConfiguration;

}