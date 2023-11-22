/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.captcha.rest.resource.v1_0.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.captcha.rest.client.dto.v1_0.SimpleCaptcha;
import com.liferay.captcha.rest.client.http.HttpInvoker;
import com.liferay.captcha.rest.client.resource.v1_0.SimpleCaptchaResource;
import com.liferay.portal.kernel.encryptor.EncryptorUtil;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.util.Base64;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.test.rule.Inject;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Loc Pham
 */
@RunWith(Arquillian.class)
public class SimpleCaptchaResourceTest
	extends BaseSimpleCaptchaResourceTestCase {

	@Override
	@Test
	public void testGetSimpleCaptchaChallenge() throws Exception {
		SimpleCaptchaResource.Builder builder = SimpleCaptchaResource.builder();

		SimpleCaptchaResource simpleCaptchaResourceForGuestAccess =
			builder.build();

		SimpleCaptcha simpleCaptcha =
			simpleCaptchaResourceForGuestAccess.getSimpleCaptchaChallenge();

		String token = simpleCaptcha.getToken();

		Assert.assertNotNull("CaptchaToken was not returned", token);

		JSONObject jsonObject = JSONFactoryUtil.createJSONObject(
			EncryptorUtil.decrypt(testCompany.getKeyObj(), token));

		Assert.assertNotNull(
			"Decrypted captcha token does not include the captcha answer",
			jsonObject.get("answer"));

		Assert.assertTrue(
			"Decrypted captcha token does not include a future expiry time",
			(GetterUtil.getLong(jsonObject.get("expiryTime")) - 1000L) >
				System.currentTimeMillis());

		String base64Header = "data:image/png;base64,";
		String base64CaptchaImage = simpleCaptcha.getImage();

		Assert.assertEquals(
			"Expected image data to start with \"" + base64Header + "\"",
			base64Header,
			base64CaptchaImage.substring(0, base64Header.length()));

		Assert.assertTrue(
			"Invalid Base64 encoded image data returned",
			Base64.decode(
				base64CaptchaImage.substring(base64Header.length())).length >
					0);
	}

	@Override
	@Test
	public void testPostSimpleCaptchaResponse() throws Exception {
		String token = _getToken();

		SimpleCaptchaResource.Builder builder = SimpleCaptchaResource.builder();

		_assertStatus(
			token, RandomTestUtil.randomString(10), 400, builder.build());
	}

	@Test
	public void testStatelessCaptcha() throws Exception {
		String token = _getToken();

		JSONObject jsonObject = JSONFactoryUtil.createJSONObject(
			EncryptorUtil.decrypt(testCompany.getKeyObj(), token));

		SimpleCaptchaResource.Builder builder = SimpleCaptchaResource.builder();

		_assertStatus(
			token, jsonObject.getString("answer"), 204, builder.build());

		_assertStatus(
			token, jsonObject.getString("answer"), 400, builder.build());
	}

	private void _assertStatus(
			String captchaToken, String answer, int status,
			SimpleCaptchaResource captchaResource)
		throws Exception {

		SimpleCaptcha simpleCaptcha = new SimpleCaptcha();

		simpleCaptcha.setToken(captchaToken);
		simpleCaptcha.setAnswer(answer);

		HttpInvoker.HttpResponse httpResponse =
			captchaResource.postSimpleCaptchaResponseHttpResponse(
				simpleCaptcha);

		Assert.assertEquals(status, httpResponse.getStatusCode());
	}

	private String _getToken() throws Exception {
		SimpleCaptchaResource.Builder builder = SimpleCaptchaResource.builder();

		SimpleCaptchaResource simpleCaptchaResourceForGuestAccess =
			builder.build();

		SimpleCaptcha simpleCaptcha =
			simpleCaptchaResourceForGuestAccess.getSimpleCaptchaChallenge();

		return simpleCaptcha.getToken();
	}

	@Inject
	private CompanyLocalService _companyLocalService;

}