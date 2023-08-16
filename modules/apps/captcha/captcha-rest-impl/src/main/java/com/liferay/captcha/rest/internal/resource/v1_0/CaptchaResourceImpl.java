/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.captcha.rest.internal.resource.v1_0;

import com.liferay.captcha.configuration.CaptchaConfiguration;
import com.liferay.captcha.rest.dto.v1_0.Captcha;
import com.liferay.captcha.rest.dto.v1_0.CaptchaForm;
import com.liferay.captcha.rest.internal.util.CaptchaTokenUtil;
import com.liferay.captcha.rest.resource.v1_0.CaptchaResource;
import com.liferay.captcha.simplecaptcha.SimpleCaptchaImpl;
import com.liferay.captcha.util.CaptchaUtil;
import com.liferay.portal.kernel.captcha.CaptchaTextException;
import com.liferay.portal.kernel.feature.flag.FeatureFlagManagerUtil;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.module.configuration.ConfigurationProvider;
import com.liferay.portal.kernel.util.Base64;
import com.liferay.portal.kernel.util.StringUtil;

import java.io.ByteArrayOutputStream;

import javax.servlet.http.HttpServletRequest;

import javax.ws.rs.BadRequestException;
import javax.ws.rs.ForbiddenException;
import javax.ws.rs.NotAcceptableException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ServiceScope;

/**
 * @author Loc Pham
 */
@Component(
	properties = "OSGI-INF/liferay/rest/v1_0/captcha.properties",
	scope = ServiceScope.PROTOTYPE, service = CaptchaResource.class
)
public class CaptchaResourceImpl extends BaseCaptchaResourceImpl {

	@Override
	public Captcha getSimpleCaptcha() throws Exception {
		_checkSimpleCaptchaConfiguration();

		com.liferay.portal.kernel.captcha.Captcha captcha =
			CaptchaUtil.getCaptcha();

		ByteArrayOutputStream imageByteArrayOutputStream =
			new ByteArrayOutputStream();

		String captchaAnswer = captcha.serveImageOutputStream(
			_httpServletRequest, imageByteArrayOutputStream);

		String base64CaptchaImage =
			"data:image/png;base64," +
				Base64.encode(imageByteArrayOutputStream.toByteArray());

		imageByteArrayOutputStream.close();

		return new Captcha() {
			{
				captchaToken = CaptchaTokenUtil.generateCaptchaToken(
					_contextCompany, captchaAnswer);

				image = base64CaptchaImage;
			}
		};
	}

	@Override
	public CaptchaForm postSimpleCaptcha(CaptchaForm captchaForm)
		throws Exception {

		_checkSimpleCaptchaConfiguration();

		try {
			CaptchaTokenUtil.checkAnswer(
				_contextCompany, captchaForm.getCaptchaToken(),
				captchaForm.getAnswer());
		}
		catch (CaptchaTextException captchaTextException) {
			throw new NotAcceptableException(
				captchaTextException.getMessage(),
				Response.status(
					Response.Status.NOT_ACCEPTABLE
				).entity(
					getSimpleCaptcha()
				).build());
		}
		catch (Exception exception) {
			throw new BadRequestException(exception.getMessage());
		}

		return captchaForm;
	}

	private void _checkSimpleCaptchaConfiguration() throws Exception {
		if (!FeatureFlagManagerUtil.isEnabled("LPS-185213")) {
			throw new UnsupportedOperationException();
		}

		CaptchaConfiguration captchaConfiguration =
			_configurationProvider.getSystemConfiguration(
				CaptchaConfiguration.class);

		if (!captchaConfiguration.enableSimpleCaptchaHeadlessAPI() ||
			!StringUtil.equalsIgnoreCase(
				captchaConfiguration.captchaEngine(),
				SimpleCaptchaImpl.class.getName())) {

			throw new ForbiddenException(
				"Simple Captcha Headless API is not enabled");
		}
	}

	@Reference
	private ConfigurationProvider _configurationProvider;

	@Context
	private Company _contextCompany;

	@Context
	private HttpServletRequest _httpServletRequest;

}