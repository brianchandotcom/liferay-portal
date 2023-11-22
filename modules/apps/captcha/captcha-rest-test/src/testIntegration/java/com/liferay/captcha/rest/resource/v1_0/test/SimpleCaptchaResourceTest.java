/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.captcha.rest.resource.v1_0.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.captcha.rest.client.dto.v1_0.SimpleCaptcha;
import com.liferay.captcha.rest.client.dto.v1_0.SimpleCaptchaForm;
import com.liferay.captcha.rest.client.http.HttpInvoker;
import com.liferay.captcha.rest.client.resource.v1_0.SimpleCaptchaResource;
import com.liferay.portal.kernel.encryptor.EncryptorUtil;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
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
	public void testGetSimpleCaptcha() throws Exception {
	}

	@Override
	@Test
	public void testPostSimpleCaptcha() throws Exception {
	}

	@Test
	public void testStatelessCaptcha() throws Exception {
		SimpleCaptchaResource.Builder builder = SimpleCaptchaResource.builder();

		SimpleCaptchaResource captchaResourceForGuestAccess = builder.build();

		String captchaToken = _getCaptchaToken();

		JSONObject jsonObject = JSONFactoryUtil.createJSONObject(
			EncryptorUtil.decrypt(testCompany.getKeyObj(), captchaToken));

		String answer = jsonObject.getString("answer");

		_assertStatus(
			captchaToken, RandomTestUtil.randomString(10), 400,
			captchaResourceForGuestAccess);

		_assertStatus(captchaToken, answer, 204, captchaResourceForGuestAccess);
	}

	private void _assertStatus(
			String captchaToken, String answer, int status,
			SimpleCaptchaResource captchaResource)
		throws Exception {

		SimpleCaptchaForm captchaForm = new SimpleCaptchaForm();

		captchaForm.setCaptchaToken(captchaToken);
		captchaForm.setAnswer(answer);

		HttpInvoker.HttpResponse httpResponse =
			captchaResource.postSimpleCaptchaHttpResponse(captchaForm);

		Assert.assertEquals(status, httpResponse.getStatusCode());
	}

	private String _getCaptchaToken() throws Exception {
		SimpleCaptchaResource.Builder builder = SimpleCaptchaResource.builder();

		SimpleCaptchaResource captchaResourceForGuestAccess = builder.build();

		SimpleCaptcha simpleCaptcha =
			captchaResourceForGuestAccess.getSimpleCaptcha();

		return simpleCaptcha.getCaptchaToken();
	}

	@Inject
	private CompanyLocalService _companyLocalService;

}