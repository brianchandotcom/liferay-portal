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
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.vulcan.graphql.servlet.ServletData;

import org.junit.Assert;
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

/**
 * @author Luis Miguel Barcos
 */
@RunWith(Arquillian.class)
public class GraphQLServletTest extends BaseGraphQLServlet {

	@ClassRule
	@Rule
	public static final LiferayIntegrationTestRule liferayIntegrationTestRule =
		new LiferayIntegrationTestRule();

	@Test
	public void test() throws Exception {
		Bundle bundle = FrameworkUtil.getBundle(GraphQLServletTest.class);

		BundleContext bundleContext = bundle.getBundleContext();

		TestServletData testServletData = new TestServletData();

		ServiceRegistration<ServletData> serviceRegistration =
			bundleContext.registerService(
				ServletData.class, testServletData, null);

		String testDTOSimpleClassName = StringUtil.lowerCaseFirstLetter(
			TestDTO.class.getSimpleName());

		GraphQLField graphQLField = new GraphQLField(
			testDTOSimpleClassName, new GraphQLField("field"),
			new GraphQLField("_id"));

		JSONObject jsonObject = JSONUtil.getValueAsJSONObject(
			invoke(graphQLField), "JSONObject/data",
			"JSONObject/" + testDTOSimpleClassName);

		TestQuery testQuery = testServletData.getQuery();

		Assert.assertEquals(jsonObject.get("field"), testQuery.getField());
		Assert.assertEquals(jsonObject.get("_id"), testQuery.getId());

		serviceRegistration.unregister();
	}

	@Test
	public void testWithQueryDepthLimit() throws Exception {
		Bundle bundle = FrameworkUtil.getBundle(GraphQLServletTest.class);

		BundleContext bundleContext = bundle.getBundleContext();

		ServiceRegistration<ServletData> serviceRegistration =
			bundleContext.registerService(
				ServletData.class, new TestServletData(), null);

		Configuration[] configurations = _configurationAdmin.listConfigurations(
			StringBundler.concat(
				"(&(service.factoryPid=com.liferay.portal.vulcan.internal.",
				"configuration.HeadlessAPICompanyConfiguration.scoped)(companyId=",
				TestPropsValues.getCompanyId(), "))"));

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

			String testDTOSimpleClassName = StringUtil.lowerCaseFirstLetter(
				TestDTO.class.getSimpleName());

			GraphQLField graphQLField = new GraphQLField(
				testDTOSimpleClassName, new GraphQLField("field"),
				new GraphQLField("_id"));

			JSONObject jsonObject = invoke(graphQLField);

			Assert.assertNull(
				JSONUtil.getValueAsJSONObject(jsonObject, "JSONObject/data"));
			Assert.assertEquals(
				"Maximum query depth exceeded 2 > 1",
				JSONUtil.getValueAsString(
					jsonObject, "JSONArray/errors", "Object/0",
					"Object/message"));

			serviceRegistration.unregister();
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

}