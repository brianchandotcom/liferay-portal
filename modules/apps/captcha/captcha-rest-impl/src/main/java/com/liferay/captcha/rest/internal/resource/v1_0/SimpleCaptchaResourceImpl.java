/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.captcha.rest.internal.resource.v1_0;

import com.liferay.captcha.configuration.CaptchaConfiguration;
import com.liferay.captcha.rest.dto.v1_0.SimpleCaptcha;
import com.liferay.captcha.rest.dto.v1_0.SimpleCaptchaForm;
import com.liferay.captcha.rest.internal.util.CaptchaTokenUtil;
import com.liferay.captcha.rest.resource.v1_0.SimpleCaptchaResource;
import com.liferay.captcha.simplecaptcha.SimpleCaptchaImpl;
import com.liferay.captcha.util.CaptchaUtil;
import com.liferay.petra.string.StringUtil;
import com.liferay.portal.configuration.module.configuration.ConfigurationProvider;
import com.liferay.portal.kernel.captcha.Captcha;
import com.liferay.portal.kernel.feature.flag.FeatureFlagManagerUtil;
import com.liferay.portal.kernel.util.Base64;

import java.io.ByteArrayOutputStream;

import javax.ws.rs.ForbiddenException;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ServiceScope;

/**
 * @author Loc Pham
 * @author Stian Sigvartsen
 */
@Component(
	properties = "OSGI-INF/liferay/rest/v1_0/simple-captcha.properties",
	scope = ServiceScope.PROTOTYPE, service = SimpleCaptchaResource.class
)
public class SimpleCaptchaResourceImpl extends BaseSimpleCaptchaResourceImpl {

	@Override
	public SimpleCaptcha getSimpleCaptcha() throws Exception {
		_checkSimpleCaptchaConfiguration();

		Captcha captcha = CaptchaUtil.getCaptcha();

		ByteArrayOutputStream imageByteArrayOutputStream =
			new ByteArrayOutputStream();

		String answer = captcha.serveImage(imageByteArrayOutputStream);

		String base64CaptchaImage =
			"data:image/png;base64," +
				Base64.encode(imageByteArrayOutputStream.toByteArray());

		imageByteArrayOutputStream.close();

		return new SimpleCaptcha() {
			{
				captchaToken = CaptchaTokenUtil.generateCaptchaToken(
					contextCompany, answer);

				image = base64CaptchaImage;
			}
		};
	}

	@Override
	public void postSimpleCaptcha(SimpleCaptchaForm simpleCaptchaForm)
		throws Exception {

		_checkSimpleCaptchaConfiguration();

		CaptchaTokenUtil.checkAnswer(
			contextCompany, simpleCaptchaForm.getCaptchaToken(),
			simpleCaptchaForm.getAnswer());
	}

	private void _checkSimpleCaptchaConfiguration() throws Exception {
		if (!FeatureFlagManagerUtil.isEnabled("LPS-185150")) {
			throw new UnsupportedOperationException();
		}

		CaptchaConfiguration captchaConfiguration =
			_configurationProvider.getSystemConfiguration(
				CaptchaConfiguration.class);

		if (!StringUtil.equalsIgnoreCase(
				captchaConfiguration.captchaEngine(),
				SimpleCaptchaImpl.class.getName())) {

			throw new ForbiddenException(
				"Simple Captcha Headless API is not enabled");
		}
	}

	@Reference
	private ConfigurationProvider _configurationProvider;

}