/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.captcha.internal.function.captcha;

import com.liferay.captcha.internal.configuration.FunctionCaptchaImplConfiguration;
import com.liferay.portal.catapult.PortalCatapult;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import java.util.concurrent.Future;

import javax.servlet.http.HttpServletRequest;

import org.junit.Assert;
import org.junit.BeforeClass;
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

	@BeforeClass
	public static void setUpClass() throws Exception {
		_setUpPortalCatapult();
		_setUpUserLocalService();
		_setUpFunctionCaptchaImplConfiguration();
		_setUpHttpServletRequest();

		_setUpFunctionCaptchaImpl();
	}

	@Test
	public void testValidateChallenge() throws Exception {
		_testValidateChallenge(false);
		_testValidateChallenge(true);
	}

	private static void _setUpFunctionCaptchaImpl() {
		_functionCaptchaImpl = new FunctionCaptchaImpl();

		ReflectionTestUtil.setFieldValue(
			_functionCaptchaImpl, "_functionCaptchaImplConfiguration",
			_functionCaptchaImplConfiguration);
		ReflectionTestUtil.setFieldValue(
			_functionCaptchaImpl, "_portalCatapult", _portalCatapult);
		ReflectionTestUtil.setFieldValue(
			_functionCaptchaImpl, "_userLocalService", _userLocalService);
	}

	private static void _setUpFunctionCaptchaImplConfiguration() {
		_functionCaptchaImplConfiguration = Mockito.mock(
			FunctionCaptchaImplConfiguration.class);

		Mockito.when(
			_functionCaptchaImplConfiguration.
				oAuth2ApplicationExternalReferenceCode()
		).thenReturn(
			RandomTestUtil.randomString()
		);

		Mockito.when(
			_functionCaptchaImplConfiguration.resourcePath()
		).thenReturn(
			RandomTestUtil.randomString()
		);

		Mockito.when(
			_functionCaptchaImplConfiguration.responseParameterName()
		).thenReturn(
			RandomTestUtil.randomString()
		);
	}

	private static void _setUpHttpServletRequest() {
		_httpServletRequest = Mockito.mock(HttpServletRequest.class);

		Mockito.when(
			_httpServletRequest.getRemoteAddr()
		).thenReturn(
			RandomTestUtil.randomString()
		);

		Mockito.when(
			ParamUtil.getString(
				_httpServletRequest,
				_functionCaptchaImplConfiguration.responseParameterName())
		).thenReturn(
			RandomTestUtil.randomString()
		);
	}

	private static void _setUpPortalCatapult() throws Exception {
		Future<byte[]> future = Mockito.mock(Future.class);

		Mockito.when(
			future.get()
		).thenReturn(
			RandomTestUtil.randomBytes()
		);

		_portalCatapult = Mockito.mock(PortalCatapult.class);

		Mockito.when(
			_portalCatapult.launch(
				Mockito.anyLong(), Mockito.any(), Mockito.anyString(),
				Mockito.any(), Mockito.anyString(), Mockito.anyLong())
		).thenReturn(
			future
		);
	}

	private static void _setUpUserLocalService() throws Exception {
		User user = Mockito.mock(User.class);

		Mockito.when(
			user.getUserId()
		).thenReturn(
			RandomTestUtil.randomLong()
		);

		_userLocalService = Mockito.mock(UserLocalService.class);

		Mockito.when(
			_userLocalService.getUserByScreenName(
				Mockito.anyLong(), Mockito.anyString())
		).thenReturn(
			user
		);
	}

	private JSONFactory _mockJSONFactory(boolean success) throws Exception {
		JSONFactory jsonFactory = Mockito.mock(JSONFactory.class);

		Mockito.when(
			jsonFactory.createJSONObject()
		).thenReturn(
			JSONUtil.put(
				RandomTestUtil.randomString(), RandomTestUtil.randomString())
		);

		JSONObject jsonObject = JSONUtil.put("success", success);

		if (!success) {
			JSONArray jsonArray = JSONUtil.put(RandomTestUtil.randomString());

			jsonObject.put("error-codes", jsonArray);
		}

		Mockito.when(
			jsonFactory.createJSONObject(Mockito.anyString())
		).thenReturn(
			jsonObject
		);

		return jsonFactory;
	}

	private void _testValidateChallenge(boolean success) throws Exception {
		JSONFactory jsonFactory = _mockJSONFactory(success);

		ReflectionTestUtil.setFieldValue(
			_functionCaptchaImpl, "_jsonFactory", jsonFactory);

		if (success) {
			_functionCaptchaImpl.validateChallenge(_httpServletRequest);

			ArgumentCaptor<JSONObject> argumentCaptor = ArgumentCaptor.forClass(
				JSONObject.class);

			Mockito.verify(
				_portalCatapult, Mockito.atLeastOnce()
			).launch(
				Mockito.anyLong(), Mockito.any(), Mockito.anyString(),
				argumentCaptor.capture(), Mockito.anyString(), Mockito.anyLong()
			);

			JSONObject payloadJSONObject = argumentCaptor.getValue();

			Assert.assertTrue(payloadJSONObject.has("remoteip"));
			Assert.assertTrue(payloadJSONObject.has("response"));
		}
		else {
			try {
				_functionCaptchaImpl.validateChallenge(_httpServletRequest);

				Assert.fail();
			}
			catch (Exception exception) {
			}
		}
	}

	private static FunctionCaptchaImpl _functionCaptchaImpl;
	private static FunctionCaptchaImplConfiguration
		_functionCaptchaImplConfiguration;
	private static HttpServletRequest _httpServletRequest;
	private static PortalCatapult _portalCatapult;
	private static UserLocalService _userLocalService;

}