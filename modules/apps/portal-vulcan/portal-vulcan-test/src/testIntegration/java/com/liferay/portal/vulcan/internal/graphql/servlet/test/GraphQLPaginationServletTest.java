/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.vulcan.internal.graphql.servlet.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.vulcan.graphql.servlet.ServletData;
import com.liferay.portal.vulcan.internal.util.PaginationConfigurationTestUtil;

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
import org.osgi.service.cm.ConfigurationAdmin;

/**
 * @author Sergio Jiménez del Coso
 */
@RunWith(Arquillian.class)
public class GraphQLPaginationServletTest extends BaseGraphQLServlet {

	@ClassRule
	@Rule
	public static final LiferayIntegrationTestRule liferayIntegrationTestRule =
		new LiferayIntegrationTestRule();

	@Before
	public void setUp() {
		Bundle bundle = FrameworkUtil.getBundle(GraphQLServletTest.class);

		BundleContext bundleContext = bundle.getBundleContext();

		TestServletData testServletDataPagination = new TestServletData(
			new TestPagination());

		_serviceRegistration = bundleContext.registerService(
			ServletData.class, testServletDataPagination, null);
	}

	@After
	public void tearDown() {
		_serviceRegistration.unregister();
	}

	@Test
	public void testQueryPagination() throws Exception {

		// Default limited page size and limited page size requested

		_test(1, 20, null, null);
		_test(1, 5, null, 5);
		_test(1, 30, null, 30);
		_test(1, 20, null, null);
		_test(1, 15, null, 15);
		_test(1, 30, null, 30);
		_test(1, 40, null, 40);
		_test(2, 20, 2, null);
		_test(3, 20, 3, null);

		// Default limited page size and unlimited page size requested

		_test(1, 500, null, -1);
		_test(1, 500, null, 0);
		_test(1, 500, -1, null);
		_test(1, 500, 0, null);

		// Limited page size configured and limited page size requested

		PaginationConfigurationTestUtil.withPageSizeLimit(
			10, () -> _test(1, 10, null, null));
		PaginationConfigurationTestUtil.withPageSizeLimit(
			10, () -> _test(1, 5, null, 5));
		PaginationConfigurationTestUtil.withPageSizeLimit(
			10, () -> _test(1, 10, null, 30));
		PaginationConfigurationTestUtil.withPageSizeLimit(
			30, () -> _test(1, 20, null, null));
		PaginationConfigurationTestUtil.withPageSizeLimit(
			30, () -> _test(1, 15, null, 15));
		PaginationConfigurationTestUtil.withPageSizeLimit(
			30, () -> _test(1, 30, null, 30));
		PaginationConfigurationTestUtil.withPageSizeLimit(
			30, () -> _test(1, 30, null, 40));
		PaginationConfigurationTestUtil.withPageSizeLimit(
			50, () -> _test(2, 20, 2, null));
		PaginationConfigurationTestUtil.withPageSizeLimit(
			50, () -> _test(3, 20, 3, null));

		// Limited page size configured and unlimited page size requested

		PaginationConfigurationTestUtil.withPageSizeLimit(
			50, () -> _test(1, 50, null, -1));
		PaginationConfigurationTestUtil.withPageSizeLimit(
			50, () -> _test(1, 50, null, 0));
		PaginationConfigurationTestUtil.withPageSizeLimit(
			50, () -> _test(1, 50, -1, null));
		PaginationConfigurationTestUtil.withPageSizeLimit(
			50, () -> _test(1, 50, 0, null));

		// Unlimited page size configured and limited page size requested

		PaginationConfigurationTestUtil.withPageSizeLimit(
			-1, () -> _test(1, 20, null, null));
		PaginationConfigurationTestUtil.withPageSizeLimit(
			-1, () -> _test(1, 25, null, 25));
		PaginationConfigurationTestUtil.withPageSizeLimit(
			-1, () -> _test(2, 20, 2, null));
		PaginationConfigurationTestUtil.withPageSizeLimit(
			-1, () -> _test(2, 25, 2, 25));
		PaginationConfigurationTestUtil.withPageSizeLimit(
			0, () -> _test(1, 20, null, null));
		PaginationConfigurationTestUtil.withPageSizeLimit(
			0, () -> _test(1, 25, null, 25));
		PaginationConfigurationTestUtil.withPageSizeLimit(
			0, () -> _test(2, 20, 2, null));
		PaginationConfigurationTestUtil.withPageSizeLimit(
			0, () -> _test(2, 25, 2, 25));

		// Unlimited page size configured and unlimited page size requested

		PaginationConfigurationTestUtil.withPageSizeLimit(
			-1, () -> _test(-1, -1, -1, null));
		PaginationConfigurationTestUtil.withPageSizeLimit(
			-1, () -> _test(-1, -1, 0, null));
		PaginationConfigurationTestUtil.withPageSizeLimit(
			-1, () -> _test(-1, -1, null, -1));
		PaginationConfigurationTestUtil.withPageSizeLimit(
			-1, () -> _test(-1, -1, null, 0));
		PaginationConfigurationTestUtil.withPageSizeLimit(
			0, () -> _test(-1, -1, -1, null));
		PaginationConfigurationTestUtil.withPageSizeLimit(
			0, () -> _test(-1, -1, 0, null));
		PaginationConfigurationTestUtil.withPageSizeLimit(
			0, () -> _test(-1, -1, null, -1));
		PaginationConfigurationTestUtil.withPageSizeLimit(
			0, () -> _test(-1, -1, null, 0));
	}

	private void _test(
			int expectedPage, int expectedPageSize, Integer requestPage,
			Integer requestPageSize)
		throws Exception {

		JSONObject jsonObject = JSONUtil.getValueAsJSONObject(
			invoke(
				new GraphQLField(
					"testPagination",
					HashMapBuilder.put(
						"page", (Object)requestPage
					).put(
						"pageSize", (Object)requestPageSize
					).build(),
					new GraphQLField("page"), new GraphQLField("pageSize")),
				"query"),
			"JSONObject/data", "JSONObject/testPagination");

		Assert.assertEquals(expectedPage, jsonObject.getInt("page"));
		Assert.assertEquals(expectedPageSize, jsonObject.getInt("pageSize"));
	}

	@Inject
	private ConfigurationAdmin _configurationAdmin;

	private ServiceRegistration<ServletData> _serviceRegistration;

}