/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.oauth2.provider.dynamic.registration.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.oauth2.provider.client.test.BaseClientTestCase;
import com.liferay.oauth2.provider.client.test.BaseTestPreparatorBundleActivator;
import com.liferay.oauth2.provider.constants.OAuth2ProviderConstants;
import com.liferay.oauth2.provider.model.OAuth2Application;
import com.liferay.oauth2.provider.service.OAuth2ApplicationLocalService;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.Property;
import com.liferay.portal.kernel.dao.orm.PropertyFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.test.rule.FeatureFlag;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.client.Invocation;
import jakarta.ws.rs.client.WebTarget;
import jakarta.ws.rs.core.MultivaluedHashMap;
import jakarta.ws.rs.core.MultivaluedMap;
import jakarta.ws.rs.core.Response;

import java.util.Collections;
import java.util.List;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.osgi.framework.BundleActivator;

/**
 * @author Jorge García Jiménez
 */
@RunWith(Arquillian.class)
public class DynamicRegistrationServiceTest extends BaseClientTestCase {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@FeatureFlag("LPD-63416")
	@Test
	public void test() throws Exception {
		WebTarget tokenWebTarget = getTokenWebTarget();

		Invocation.Builder invocationBuilderToken = tokenWebTarget.request();

		OAuth2Application oAuth2Application =
			_oAuth2ApplicationLocalService.fetchOAuth2Application(
				TestPropsValues.getCompanyId(),
				"oauthDynamicRegisterTestApplication");

		MultivaluedMap<String, String> formData = new MultivaluedHashMap<>();

		formData.add("client_id", oAuth2Application.getClientId());
		formData.add("client_secret", oAuth2Application.getClientSecret());
		formData.add("grant_type", "client_credentials");

		String tokenString = parseTokenString(
			invocationBuilderToken.post(Entity.form(formData)));

		Assert.assertNotNull(tokenString);

		WebTarget webTarget = getRegisterWebTarget();

		Invocation.Builder invocationBuilderRegister = authorize(
			webTarget.request(), tokenString);

		Response response = invocationBuilderRegister.method(
			"post",
			Entity.json(
				JSONUtil.put(
					"client_name", RandomTestUtil.randomString()
				).toString()));

		Assert.assertEquals(401, response.getStatus());

		oAuth2Application = _getRegistratorOauth2Application();

		Assert.assertNotNull(oAuth2Application);

		formData = new MultivaluedHashMap<>();

		formData.add("client_id", oAuth2Application.getClientId());
		formData.add("client_secret", oAuth2Application.getClientSecret());
		formData.add("grant_type", "client_credentials");

		tokenString = parseTokenString(
			invocationBuilderToken.post(Entity.form(formData)));

		Assert.assertNotNull(tokenString);

		String clientName = RandomTestUtil.randomString();

		JSONObject jsonObject = JSONUtil.put(
			"client_name", clientName
		).put(
			"grant_types", new String[] {"client_credentials"}
		).put(
			"logo_uri", RandomTestUtil.randomString()
		).put(
			"redirect_uris",
			new String[] {
				"https://client.example.org/callback",
				"https://client.example.org/callback2"
			}
		).put(
			"scope", "Liferay.Headless.Admin.Site.everything"
		);

		response = invocationBuilderRegister.method(
			"post", Entity.json(jsonObject.toString()));

		Assert.assertEquals(401, response.getStatus());

		invocationBuilderRegister = authorize(webTarget.request(), tokenString);

		response = invocationBuilderRegister.method(
			"post", Entity.json(jsonObject.toString()));

		Assert.assertEquals(201, response.getStatus());

		JSONObject responseJSONObject = parseJSONObject(response);

		Assert.assertEquals(
			clientName, responseJSONObject.getString("client_name"));

		String clientId = responseJSONObject.getString("client_id");

		jsonObject.put("response_types", Collections.singletonList("code"));

		response = invocationBuilderRegister.method(
			"post", Entity.json(jsonObject.toString()));

		Assert.assertEquals(400, response.getStatus());

		String errorString = parseError(response);

		Assert.assertEquals("invalid_client_metadata", errorString);

		webTarget = getRegisterWebTarget(clientId);

		invocationBuilderRegister = authorize(webTarget.request(), tokenString);

		response = invocationBuilderRegister.get();

		Assert.assertEquals(200, response.getStatus());

		responseJSONObject = parseJSONObject(response);

		Assert.assertEquals(
			clientName, responseJSONObject.getString("client_name"));
	}

	protected static WebTarget getRegisterWebTarget() {
		WebTarget webTarget = getOAuth2WebTarget();

		return webTarget.path("register");
	}

	protected static WebTarget getRegisterWebTarget(String target) {
		WebTarget webTarget = getRegisterWebTarget();

		return webTarget.path(target);
	}

	@Override
	protected BundleActivator getBundleActivator() {
		return new DynamicRegistrationTestPreparatorBundleActivator();
	}

	private OAuth2Application _getRegistratorOauth2Application()
		throws Exception {

		DynamicQuery dynamicQuery =
			_oAuth2ApplicationLocalService.dynamicQuery();

		Property companyIdProperty = PropertyFactoryUtil.forName("companyId");

		dynamicQuery.add(companyIdProperty.eq(TestPropsValues.getCompanyId()));

		Property nameProperty = PropertyFactoryUtil.forName("name");

		dynamicQuery.add(
			nameProperty.eq(
				OAuth2ProviderConstants.OAUTH2_PROVIDER_DYNAMIC_REGISTRATOR));

		List<OAuth2Application> oAuth2Applications =
			_oAuth2ApplicationLocalService.dynamicQuery(dynamicQuery);

		if (!oAuth2Applications.isEmpty()) {
			return oAuth2Applications.get(0);
		}

		return null;
	}

	@Inject
	private OAuth2ApplicationLocalService _oAuth2ApplicationLocalService;

	private class DynamicRegistrationTestPreparatorBundleActivator
		extends BaseTestPreparatorBundleActivator {

		@Override
		protected void prepareTest() throws Exception {
			long companyId = TestPropsValues.getCompanyId();

			User user = UserTestUtil.getAdminUser(companyId);

			createOAuth2Application(
				companyId, user, "oauthDynamicRegisterTestApplication");
		}

	}

}