/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.scim.rest.resource.v1_0.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.portal.configuration.test.util.ConfigurationTestUtil;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DataGuard;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.HashMapDictionaryBuilder;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.scim.rest.client.http.HttpInvoker;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Jorge García Jiménez
 */
@DataGuard(scope = DataGuard.Scope.METHOD)
@RunWith(Arquillian.class)
public class ServiceProviderConfigResourceTest
	extends BaseServiceProviderConfigResourceTestCase {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@BeforeClass
	public static void setUpClass() throws Exception {
		BaseServiceProviderConfigResourceTestCase.setUpClass();
	}

	@Before
	@Override
	public void setUp() throws Exception {
		super.setUp();

		_pid = ConfigurationTestUtil.createFactoryConfiguration(
			"com.liferay.scim.rest.internal.configuration." +
				"ScimClientOAuth2ApplicationConfiguration",
			HashMapDictionaryBuilder.<String, Object>put(
				"companyId", TestPropsValues.getCompanyId()
			).put(
				"matcherField", "email"
			).put(
				"oAuth2ApplicationName", "scim-client-test"
			).put(
				"userId", TestPropsValues.getUserId()
			).build());
	}

	@After
	@Override
	public void tearDown() throws Exception {
		ConfigurationTestUtil.deleteConfiguration(_pid);
	}

	@Override
	@Test
	public void testGetV2ServiceProviderConfig() throws Exception {
		HttpInvoker.HttpResponse httpResponse =
			serviceProviderConfigResource.
				getV2ServiceProviderConfigHttpResponse();

		assertHttpResponseStatusCode(200, httpResponse);

		JSONObject serviceProviderConfigJSONObject =
			_jsonFactory.createJSONObject(httpResponse.getContent());

		JSONObject filterJSONObject =
			serviceProviderConfigJSONObject.getJSONObject("filter");

		Assert.assertEquals(100, filterJSONObject.getInt("maxResults"));
		Assert.assertTrue(filterJSONObject.getBoolean("supported"));

		Assert.assertTrue(
			serviceProviderConfigJSONObject.getJSONObject(
				"patch"
			).getBoolean(
				"supported"
			));

		JSONArray schemasJSONArray =
			serviceProviderConfigJSONObject.getJSONArray("schemas");

		Assert.assertEquals(
			"urn:ietf:params:scim:schemas:core:2.0:ServiceProviderConfig",
			schemasJSONArray.get(0));

		ConfigurationTestUtil.deleteConfiguration(_pid);

		assertHttpResponseStatusCode(
			404,
			serviceProviderConfigResource.
				getV2ServiceProviderConfigHttpResponse());
	}

	private static String _pid;

	@Inject
	private JSONFactory _jsonFactory;

}