/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.vulcan.internal.graphql.servlet.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.HashMapDictionaryBuilder;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.vulcan.graphql.servlet.ServletData;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.ServiceRegistration;
import org.osgi.service.cm.Configuration;
import org.osgi.service.cm.ConfigurationAdmin;

import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;

/**
 * @author Luis Miguel Barcos
 */
@RunWith(Arquillian.class)
public class GraphQLServletTest extends BaseGraphQLServlet {

	@ClassRule
	@Rule
	public static final LiferayIntegrationTestRule liferayIntegrationTestRule =
		new LiferayIntegrationTestRule();

	@Before
	public void setUp() {
		Bundle bundle = FrameworkUtil.getBundle(GraphQLServletTest.class);

		BundleContext bundleContext = bundle.getBundleContext();

		TestServletData testServletData = new TestServletData();

		_serviceRegistration = bundleContext.registerService(
			ServletData.class, testServletData, null);

		_testQuery = testServletData.getQuery();
	}

	@After
	public void tearDown() {
		_serviceRegistration.unregister();
	}

	@Test
	public void test() throws Exception {
		JSONObject jsonObject = JSONUtil.getValueAsJSONObject(
			invoke(
				new GraphQLField(
					"testDTO", new GraphQLField("extendedStringField"),
					new GraphQLField("id"), new GraphQLField("stringField"))),
			"JSONObject/data", "JSONObject/testDTO");

		Assert.assertEquals(
			jsonObject.get("extendedStringField"),
			_testQuery.getExtendedStringField());
		Assert.assertEquals(jsonObject.get("id"), _testQuery.getId());
		Assert.assertEquals(
			jsonObject.get("stringField"), _testQuery.getStringField());
	}

	@Test
	public void testQueryDepthLimit() throws Exception {
		Configuration[] configurations = _configurationAdmin.listConfigurations(
			StringBundler.concat(
				"(&(companyId=", TestPropsValues.getCompanyId(),
				")(service.factoryPid=com.liferay.portal.vulcan.internal.",
				"configuration.HeadlessAPICompanyConfiguration.scoped))"));

		Configuration factoryConfiguration =
			_configurationAdmin.createFactoryConfiguration(
				"com.liferay.portal.vulcan.internal.configuration." +
					"HeadlessAPICompanyConfiguration.scoped",
				StringPool.QUESTION);

		try {
			factoryConfiguration.update(
				HashMapDictionaryBuilder.<String, Object>put(
					"companyId", TestPropsValues.getCompanyId()
				).put(
					"queryDepthLimit", 1
				).build());

			JSONObject jsonObject = invoke(
				new GraphQLField(
					"testDTO", new GraphQLField("id"),
					new GraphQLField("stringField")));

			Assert.assertNull(
				JSONUtil.getValueAsJSONObject(jsonObject, "JSONObject/data"));
			JSONAssert.assertEquals(
				JSONUtil.put(
					"extensions",
					JSONUtil.put(
						"code", "Bad Request"
					).put(
						"exception", JSONUtil.put("errno", 400)
					)
				).put(
					"message",
					"Depth 2 is greater than the query depth limit of 1"
				).toString(),
				JSONUtil.getValueAsString(
					jsonObject, "JSONArray/errors", "Object/0"),
				JSONCompareMode.LENIENT);

			factoryConfiguration.update(
				HashMapDictionaryBuilder.<String, Object>put(
					"companyId", TestPropsValues.getCompanyId()
				).put(
					"queryDepthLimit", 2
				).build());

			jsonObject = JSONUtil.getValueAsJSONObject(
				invoke(
					new GraphQLField(
						"testDTO", new GraphQLField("id"),
						new GraphQLField("stringField"))),
				"JSONObject/data", "JSONObject/testDTO");

			Assert.assertEquals(jsonObject.get("id"), _testQuery.getId());
			Assert.assertEquals(
				jsonObject.get("stringField"), _testQuery.getStringField());
		}
		finally {
			if (configurations == null) {
				factoryConfiguration.delete();
			}
			else {
				Configuration configuration = configurations[0];

				factoryConfiguration.update(configuration.getProperties());
			}
		}
	}

	@Inject
	private ConfigurationAdmin _configurationAdmin;

	private ServiceRegistration<ServletData> _serviceRegistration;
	private TestQuery _testQuery;

}