/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.oauth2.provider.dynamic.registration.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.oauth2.provider.client.test.BaseClientTestCase;
import com.liferay.oauth2.provider.client.test.BaseTestPreparatorBundleActivator;
import com.liferay.oauth2.provider.constants.OAuth2ApplicationConstants;
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

import org.apache.cxf.rs.security.oauth2.utils.OAuthConstants;

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
		OAuth2Application oAuth2Application =
			_oAuth2ApplicationLocalService.fetchOAuth2Application(
				TestPropsValues.getCompanyId(),
				"oauthDynamicRegisterTestApplication");

		WebTarget webTarget = getRegisterWebTarget();

		Invocation.Builder invocationBuilder = authorize(
			webTarget.request(), _getToken(oAuth2Application));

		Response response = invocationBuilder.method(
			"post",
			Entity.json(
				JSONUtil.put(
					"client_name", RandomTestUtil.randomString()
				).toString()));

		Assert.assertEquals(401, response.getStatus());

		oAuth2Application = _getDynamicRegistratorOAuth2Application();

		Assert.assertNotNull(oAuth2Application);

		String clientName = RandomTestUtil.randomString();

		JSONObject jsonObject = JSONUtil.put(
			"client_name", clientName
		).put(
			"grant_types",
			new String[] {OAuthConstants.CLIENT_CREDENTIALS_GRANT}
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

		response = invocationBuilder.method(
			"post", Entity.json(jsonObject.toString()));

		Assert.assertEquals(401, response.getStatus());

		invocationBuilder = authorize(
			webTarget.request(), _getToken(oAuth2Application));

		response = invocationBuilder.method(
			"post", Entity.json(jsonObject.toString()));

		Assert.assertEquals(201, response.getStatus());

		JSONObject responseJSONObject = parseJSONObject(response);

		Assert.assertEquals(
			clientName, responseJSONObject.getString("client_name"));

		String clientId = responseJSONObject.getString(
			OAuthConstants.CLIENT_ID);

		jsonObject.put(
			"response_types",
			Collections.singletonList(OAuthConstants.CODE_RESPONSE_TYPE));

		response = invocationBuilder.method(
			"post", Entity.json(jsonObject.toString()));

		Assert.assertEquals(400, response.getStatus());

		String errorString = parseError(response);

		Assert.assertEquals("invalid_client_metadata", errorString);

		webTarget = getRegisterWebTarget(clientId);

		invocationBuilder = authorize(
			webTarget.request(), _getToken(oAuth2Application));

		response = invocationBuilder.get();

		Assert.assertEquals(200, response.getStatus());

		responseJSONObject = parseJSONObject(response);

		Assert.assertEquals(
			clientName, responseJSONObject.getString("client_name"));
	}

	@FeatureFlag("LPD-63416")
	@Test
	public void testDelete() throws Exception {
		OAuth2Application oAuth2Application1 =
			_oAuth2ApplicationLocalService.fetchOAuth2Application(
				TestPropsValues.getCompanyId(),
				"oauthDynamicRegisterTestApplication");

		OAuth2Application oAuth2Application2 =
			_oAuth2ApplicationLocalService.fetchOAuth2Application(
				TestPropsValues.getCompanyId(), "oauthDeleteMeApplication");

		WebTarget webTarget = getRegisterWebTarget(
			oAuth2Application2.getClientId());

		Invocation.Builder invocationBuilder = authorize(
			webTarget.request(), _getToken(oAuth2Application1));

		Response response = invocationBuilder.delete();

		Assert.assertEquals(403, response.getStatus());

		oAuth2Application1 = _getDynamicRegistratorOAuth2Application();

		Assert.assertNotNull(oAuth2Application1);

		invocationBuilder = authorize(
			webTarget.request(), _getToken(oAuth2Application1));

		response = invocationBuilder.delete();

		Assert.assertEquals(204, response.getStatus());

		response = invocationBuilder.delete();

		Assert.assertEquals(401, response.getStatus());
	}

	@FeatureFlag("LPD-63416")
	@Test
	public void testPut() throws Exception {
		OAuth2Application oAuth2Application =
			_oAuth2ApplicationLocalService.fetchOAuth2Application(
				TestPropsValues.getCompanyId(), "oauthDeleteMeApplication");

		WebTarget webTarget = getRegisterWebTarget(
			oAuth2Application.getClientId());

		Invocation.Builder invocationBuilder = authorize(
			webTarget.request(),
			_getToken(_getDynamicRegistratorOAuth2Application()));

		String clientName = RandomTestUtil.randomString();

		Response response = invocationBuilder.method(
			"put",
			Entity.json(
				JSONUtil.put(
					"client_name", clientName
				).put(
					"grant_types",
					new String[] {OAuthConstants.CLIENT_CREDENTIALS_GRANT}
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
				).toString()));

		Assert.assertEquals(200, response.getStatus());

		JSONObject responseJSONObject = parseJSONObject(response);

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
		return new DynamicRegistrationServiceTestPreparatorBundleActivator();
	}

	private OAuth2Application _getDynamicRegistratorOAuth2Application()
		throws Exception {

		DynamicQuery dynamicQuery =
			_oAuth2ApplicationLocalService.dynamicQuery();

		Property companyIdProperty = PropertyFactoryUtil.forName("companyId");

		dynamicQuery.add(companyIdProperty.eq(TestPropsValues.getCompanyId()));

		Property nameProperty = PropertyFactoryUtil.forName("name");

		dynamicQuery.add(
			nameProperty.eq(
				OAuth2ApplicationConstants.NAME_DYNAMIC_REGISTRATOR));

		List<OAuth2Application> oAuth2Applications =
			_oAuth2ApplicationLocalService.dynamicQuery(dynamicQuery);

		if (oAuth2Applications.isEmpty()) {
			return null;
		}

		return oAuth2Applications.get(0);
	}

	private String _getToken(OAuth2Application oAuth2Application) {
		WebTarget tokenWebTarget = getTokenWebTarget();

		Invocation.Builder invocationBuilder = tokenWebTarget.request();

		MultivaluedMap<String, String> formData = new MultivaluedHashMap<>();

		formData.add(OAuthConstants.CLIENT_ID, oAuth2Application.getClientId());
		formData.add(
			OAuthConstants.CLIENT_SECRET, oAuth2Application.getClientSecret());
		formData.add(
			OAuthConstants.GRANT_TYPE, OAuthConstants.CLIENT_CREDENTIALS_GRANT);

		String tokenString = parseTokenString(
			invocationBuilder.post(Entity.form(formData)));

		Assert.assertNotNull(tokenString);

		return tokenString;
	}

	@Inject
	private OAuth2ApplicationLocalService _oAuth2ApplicationLocalService;

	private class DynamicRegistrationServiceTestPreparatorBundleActivator
		extends BaseTestPreparatorBundleActivator {

		@Override
		protected void prepareTest() throws Exception {
			long companyId = TestPropsValues.getCompanyId();

			User user = UserTestUtil.getAdminUser(companyId);

			createOAuth2Application(
				companyId, user, "oauthDynamicRegisterTestApplication");

			createOAuth2Application(
				companyId, user, "oauthDeleteMeApplication");
		}

	}

}