/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.captcha.rest.internal.util;

import com.liferay.portal.kernel.captcha.CaptchaTextException;
import com.liferay.portal.kernel.encryptor.EncryptorException;
import com.liferay.portal.kernel.encryptor.EncryptorUtil;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Time;

import java.util.Date;

/**
 * @author Loc Pham
 */
public class CaptchaTokenUtil {

	public static void checkAnswer(
			Company company, String captchaToken, String answer)
		throws Exception {

		String captchaJSONString = EncryptorUtil.decrypt(
			company.getKeyObj(), captchaToken);

		JSONObject captchaJSONObject = JSONFactoryUtil.createJSONObject(
			captchaJSONString);

		if (!isValidCaptchaToken(captchaJSONObject)) {
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

	public static String generateCaptchaToken(Company company, String answer)
		throws EncryptorException {

		return EncryptorUtil.encrypt(
			company.getKeyObj(),
			JSONUtil.put(
				"answer", answer
			).put(
				"expiryTime",
				System.currentTimeMillis() + _CAPTCHA_TOKEN_EXPIRY_DURATION
			).toString());
	}

	public static boolean isValidCaptchaToken(JSONObject captchaJSONObject) {
		if ((captchaJSONObject == null) ||
			(captchaJSONObject.getString("answer") == null) ||
			(captchaJSONObject.get("expiryTime") == null)) {

			return false;
		}

		return true;
	}

	private static final long _CAPTCHA_TOKEN_EXPIRY_DURATION = Time.MINUTE * 5;

}