/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.captcha.internal.function.captcha;

import com.liferay.captcha.internal.configuration.FunctionCaptchaImplConfiguration;
import com.liferay.portal.catapult.PortalCatapult;
import com.liferay.portal.json.JSONArrayImpl;
import com.liferay.portal.json.JSONObjectImpl;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import java.util.concurrent.Future;

import javax.servlet.http.HttpServletRequest;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

/**
 * @author Manuele Castro
 */
public class FunctionCaptchaImplTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Test
	public void testValidateChallenge() throws Exception {
		FunctionCaptchaImplConfiguration functionCaptchaImplConfiguration =
			Mockito.mock(FunctionCaptchaImplConfiguration.class);

		Mockito.when(
			functionCaptchaImplConfiguration.responseParameterName()
		).thenReturn(
			RandomTestUtil.randomString()
		);

		Mockito.when(
			functionCaptchaImplConfiguration.
				oAuth2ApplicationExternalReferenceCode()
		).thenReturn(
			RandomTestUtil.randomString()
		);

		Mockito.when(
			functionCaptchaImplConfiguration.resourcePath()
		).thenReturn(
			RandomTestUtil.randomString()
		);

		FunctionCaptchaImpl functionCaptchaImpl = new FunctionCaptchaImpl();

		ReflectionTestUtil.setFieldValue(
			functionCaptchaImpl, "_functionCaptchaImplConfiguration",
			functionCaptchaImplConfiguration);

		Future<byte[]> future = Mockito.mock(Future.class);

		Mockito.when(
			future.get()
		).thenReturn(
			RandomTestUtil.randomBytes()
		);

		HttpServletRequest httpServletRequest = Mockito.mock(
			HttpServletRequest.class);

		Mockito.when(
			httpServletRequest.getRemoteAddr()
		).thenReturn(
			RandomTestUtil.randomString()
		);

		Mockito.when(
			ParamUtil.getString(
				httpServletRequest,
				functionCaptchaImplConfiguration.responseParameterName())
		).thenReturn(
			RandomTestUtil.randomString()
		);

		PortalCatapult portalCatapult = Mockito.mock(PortalCatapult.class);

		Mockito.when(
			portalCatapult.launch(
				Mockito.anyLong(), Mockito.any(), Mockito.anyString(),
				Mockito.any(), Mockito.anyString(), Mockito.anyLong())
		).thenReturn(
			future
		);

		ReflectionTestUtil.setFieldValue(
			functionCaptchaImpl, "_portalCatapult", portalCatapult);

		User user = Mockito.mock(User.class);

		UserLocalService userLocalService = Mockito.mock(
			UserLocalService.class);

		Mockito.when(
			user.getUserId()
		).thenReturn(
			RandomTestUtil.randomLong()
		);

		Mockito.when(
			userLocalService.getUserByScreenName(
				Mockito.anyLong(), Mockito.anyString())
		).thenReturn(
			user
		);

		ReflectionTestUtil.setFieldValue(
			functionCaptchaImpl, "_userLocalService", userLocalService);

		JSONFactory jsonFactory = _mockJSONFactory(true);

		ReflectionTestUtil.setFieldValue(
			functionCaptchaImpl, "_jsonFactory", jsonFactory);

		functionCaptchaImpl.validateChallenge(httpServletRequest);

		ArgumentCaptor<JSONObject> argumentCaptor = ArgumentCaptor.forClass(
			JSONObject.class);

		Mockito.verify(
			portalCatapult, Mockito.times(1)
		).launch(
			Mockito.anyLong(), Mockito.any(), Mockito.anyString(),
			argumentCaptor.capture(), Mockito.anyString(), Mockito.anyLong()
		);

		JSONObject payloadJSONObject = argumentCaptor.getValue();

		Assert.assertTrue(payloadJSONObject.has("remoteip"));
		Assert.assertTrue(payloadJSONObject.has("response"));

		jsonFactory = _mockJSONFactory(false);

		ReflectionTestUtil.setFieldValue(
			functionCaptchaImpl, "_jsonFactory", jsonFactory);

		try {
			functionCaptchaImpl.validateChallenge(httpServletRequest);

			Assert.fail();
		}
		catch (Exception exception) {
		}
	}

	private JSONFactory _mockJSONFactory(boolean success) throws Exception {
		JSONFactory jsonFactory = Mockito.mock(JSONFactory.class);

		Mockito.when(
			jsonFactory.createJSONObject()
		).thenReturn(
			new JSONObjectImpl()
		);

		JSONObject jsonObject = new JSONObjectImpl();

		jsonObject.put("success", success);

		if (!success) {
			JSONArray jsonArray = new JSONArrayImpl(
			).put(
				"error-code-1"
			).put(
				"error-code-2"
			);

			jsonObject.put("error-codes", jsonArray);
		}

		Mockito.when(
			jsonFactory.createJSONObject(Mockito.anyString())
		).thenReturn(
			jsonObject
		);

		return jsonFactory;
	}

}