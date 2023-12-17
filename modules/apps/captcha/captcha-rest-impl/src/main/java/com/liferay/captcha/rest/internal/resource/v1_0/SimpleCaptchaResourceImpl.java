/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.captcha.rest.internal.resource.v1_0;

import com.liferay.captcha.configuration.CaptchaConfiguration;
import com.liferay.captcha.rest.dto.v1_0.SimpleCaptcha;
import com.liferay.captcha.rest.resource.v1_0.SimpleCaptchaResource;
import com.liferay.captcha.simplecaptcha.SimpleCaptchaImpl;
import com.liferay.captcha.util.CaptchaUtil;
import com.liferay.petra.string.StringUtil;
import com.liferay.portal.configuration.module.configuration.ConfigurationProvider;
import com.liferay.portal.kernel.captcha.Captcha;
import com.liferay.portal.kernel.captcha.CaptchaTextException;
import com.liferay.portal.kernel.encryptor.EncryptorException;
import com.liferay.portal.kernel.encryptor.EncryptorUtil;
import com.liferay.portal.kernel.feature.flag.FeatureFlagManagerUtil;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.util.Base64;
import com.liferay.portal.kernel.util.Time;
import com.liferay.portal.servlet.filters.secure.NonceUtil;

import java.io.ByteArrayOutputStream;

import java.util.Date;

import javax.ws.rs.ForbiddenException;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ServiceScope;

/**
 * @author Loc Pham
 */
@Component(
	properties = "OSGI-INF/liferay/rest/v1_0/simple-captcha.properties",
	scope = ServiceScope.PROTOTYPE, service = SimpleCaptchaResource.class
)
public class SimpleCaptchaResourceImpl extends BaseSimpleCaptchaResourceImpl {

	@Override
	public SimpleCaptcha getSimpleCaptchaChallenge() throws Exception {
		_checkSimpleCaptchaConfiguration();

		Captcha captcha = CaptchaUtil.getCaptcha();

		try (ByteArrayOutputStream byteArrayOutputStream =
				new ByteArrayOutputStream()) {

			String answer = captcha.serveImage(byteArrayOutputStream);

			return new SimpleCaptcha() {
				{
					image =
						"data:image/png;base64," +
							Base64.encode(byteArrayOutputStream.toByteArray());
					token = _generateCaptchaToken(
						contextCompany, answer,
						NonceUtil.generate(
							contextCompany.getCompanyId(),
							contextHttpServletRequest.getRemoteAddr()));
				}
			};
		}
	}

	@Override
	public void postSimpleCaptchaResponse(SimpleCaptcha simpleCaptcha)
		throws Exception {

		_checkSimpleCaptchaConfiguration();

		_checkAnswer(
			contextCompany, simpleCaptcha.getToken(),
			simpleCaptcha.getAnswer());
	}

	private void _checkAnswer(
			Company company, String captchaToken, String answer)
		throws Exception {

		String captchaJSONString = EncryptorUtil.decrypt(
			company.getKeyObj(), captchaToken);

		JSONObject captchaJSONObject = _jsonFactory.createJSONObject(
			captchaJSONString);

		if (!_isValidCaptchaToken(captchaJSONObject) ||
			!NonceUtil.verify(captchaJSONObject.getString("nonce"))) {

			throw new IllegalArgumentException(
				"Illegal captcha token: " + captchaToken);
		}

		Date expiryDate = new Date(captchaJSONObject.getLong("expiryTime"));

		if (expiryDate.before(new Date()) ||
			!StringUtil.equalsIgnoreCase(
				captchaJSONObject.getString("answer"), answer)) {

			throw new CaptchaTextException("Invalid answer");
		}
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

	private String _generateCaptchaToken(
			Company company, String answer, String nonce)
		throws EncryptorException {

		return EncryptorUtil.encrypt(
			company.getKeyObj(),
			JSONUtil.put(
				"answer", answer
			).put(
				"expiryTime",
				System.currentTimeMillis() + _CAPTCHA_TOKEN_EXPIRY_DURATION
			).put(
				"nonce", nonce
			).toString());
	}

	private boolean _isValidCaptchaToken(JSONObject captchaJSONObject) {
		if ((captchaJSONObject == null) ||
			(captchaJSONObject.getString("answer") == null) ||
			(captchaJSONObject.get("expiryTime") == null)) {

			return false;
		}

		return true;
	}

	private static final long _CAPTCHA_TOKEN_EXPIRY_DURATION = Time.MINUTE * 5;

	@Reference
	private ConfigurationProvider _configurationProvider;

	@Reference
	private JSONFactory _jsonFactory;

}