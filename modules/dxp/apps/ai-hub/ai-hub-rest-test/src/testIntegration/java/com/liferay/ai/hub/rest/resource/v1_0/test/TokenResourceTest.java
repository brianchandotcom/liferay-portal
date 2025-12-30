/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.ai.hub.rest.resource.v1_0.test;

import com.liferay.ai.hub.configuration.AIHubConfiguration;
import com.liferay.ai.hub.rest.resource.v1_0.test.util.SseEventSourceTestUtil;
import com.liferay.ai.hub.rest.resource.v1_0.util.SseUtil;
import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.oauth2.provider.constants.ClientProfile;
import com.liferay.oauth2.provider.constants.GrantType;
import com.liferay.oauth2.provider.model.OAuth2Application;
import com.liferay.oauth2.provider.service.OAuth2ApplicationLocalService;
import com.liferay.oauth2.provider.util.OAuth2SecureRandomGenerator;
import com.liferay.portal.configuration.test.util.ConfigurationTestUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.util.HTTPTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.HashMapDictionaryBuilder;
import com.liferay.portal.kernel.util.Http;
import com.liferay.portal.test.rule.FeatureFlag;
import com.liferay.portal.test.rule.Inject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Feliphe Marinho
 */
@FeatureFlag("LPD-62272")
@RunWith(Arquillian.class)
public class TokenResourceTest extends BaseTokenResourceTestCase {

	@AfterClass
	public static void tearDownClass() {
		SseUtil.closeAll();

		try {
			ConfigurationTestUtil.deleteConfiguration(
				AIHubConfiguration.class.getName());
		}
		catch (Exception exception) {
			throw new RuntimeException(exception);
		}
	}

	@Override
	@Test
	public void testPostToken() throws Exception {
		User user = TestPropsValues.getUser();

		OAuth2Application oAuth2Application =
			_oAuth2ApplicationLocalService.addOAuth2Application(
				user.getCompanyId(), user.getUserId(), user.getFullName(),
				List.of(
					GrantType.AUTHORIZATION_CODE, GrantType.CLIENT_CREDENTIALS,
					GrantType.REFRESH_TOKEN),
				"client_secret_post", user.getUserId(),
				OAuth2SecureRandomGenerator.generateClientId(),
				ClientProfile.WEB_APPLICATION.id(),
				OAuth2SecureRandomGenerator.generateClientSecret(), "",
				List.of(), "http://localhost:8080", 0, null, "AI Hub", "",
				List.of("http://localhost:8080"), false,
				Arrays.asList("Liferay.AI.Hub.REST.everything"), false,
				new ServiceContext());

		ConfigurationTestUtil.saveConfiguration(
			AIHubConfiguration.class.getName(),
			HashMapDictionaryBuilder.<String, Object>put(
				"clientId", oAuth2Application.getClientId()
			).put(
				"clientSecret", oAuth2Application.getClientSecret()
			).put(
				"serviceURL", "http://localhost:8080"
			).build());

		JSONObject jsonObject = HTTPTestUtil.invokeToJSONObject(
			null, "ai-hub/v1.0/tokens", Http.Method.POST);

		Assert.assertTrue(jsonObject.has("accessToken"));
		Assert.assertTrue(jsonObject.has("scope"));

		Assert.assertNotNull(
			SseEventSourceTestUtil.open(
				"Bearer " + jsonObject.getString("accessToken"), List.of(),
				new ArrayList<>(), "chats/subscribe"));
	}

	@Inject
	private OAuth2ApplicationLocalService _oAuth2ApplicationLocalService;

}