/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.workflow.metrics.rest.resource.v1_0.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.test.rule.DataGuard;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.search.test.util.IdempotentRetryAssert;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.workflow.metrics.rest.client.dto.v1_0.Process;
import com.liferay.portal.workflow.metrics.rest.client.dto.v1_0.ProcessVersion;
import com.liferay.portal.workflow.metrics.rest.client.pagination.Page;
import com.liferay.portal.workflow.metrics.rest.client.serdes.v1_0.ProcessVersionSerDes;
import com.liferay.portal.workflow.metrics.rest.resource.v1_0.test.helper.WorkflowMetricsRESTTestHelper;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Feliphe Marinho
 */
@DataGuard(scope = DataGuard.Scope.METHOD)
@RunWith(Arquillian.class)
public class ProcessVersionResourceTest
	extends BaseProcessVersionResourceTestCase {

	@Before
	@Override
	public void setUp() throws Exception {
		super.setUp();

		_process = _workflowMetricsRESTTestHelper.addProcess(
			testGroup.getCompanyId());
	}

	@After
	@Override
	public void tearDown() throws Exception {
		super.tearDown();

		_workflowMetricsRESTTestHelper.deleteProcess(
			testGroup.getCompanyId(), _process);
	}

	@Override
	@Test
	public void testGetProcessProcessVersionsPage() throws Exception {
		Page<ProcessVersion> page =
			processVersionResource.getProcessProcessVersionsPage(
				_process.getId());

		assertEqualsIgnoringOrder(
			Arrays.asList(
				new ProcessVersion() {
					{
						name = "1.0";
					}
				}),
			(List<ProcessVersion>)page.getItems());

		_workflowMetricsRESTTestHelper.updateProcess(
			testGroup.getCompanyId(), _process.getId(), "2.0");

		IdempotentRetryAssert.retryAssert(
			3, TimeUnit.SECONDS,
			() -> {
				Page<ProcessVersion> processVersionsPage =
					processVersionResource.getProcessProcessVersionsPage(
						_process.getId());

				assertEqualsIgnoringOrder(
					Arrays.asList(
						new ProcessVersion() {
							{
								name = "1.0";
							}
						},
						new ProcessVersion() {
							{
								name = "2.0";
							}
						}),
					(List<ProcessVersion>)processVersionsPage.getItems());

				return null;
			});
	}

	@Test
	public void testGraphQLGetProcessProcessVersionsPage() throws Exception {
		BaseProcessVersionResourceTestCase.GraphQLField graphQLField =
			new BaseProcessVersionResourceTestCase.GraphQLField(
				"processProcessVersions",
				HashMapBuilder.<String, Object>put(
					"processId", _process.getId()
				).build(),
				new BaseProcessVersionResourceTestCase.GraphQLField(
					"items", getGraphQLFields()));

		JSONObject processVersionsJSONObject = JSONUtil.getValueAsJSONObject(
			invokeGraphQLQuery(graphQLField), "JSONObject/data",
			"JSONObject/processProcessVersions");

		assertEqualsIgnoringOrder(
			Arrays.asList(
				new ProcessVersion() {
					{
						name = "1.0";
					}
				}),
			Arrays.asList(
				ProcessVersionSerDes.toDTOs(
					processVersionsJSONObject.getString("items"))));
	}

	@Override
	protected String[] getAdditionalAssertFieldNames() {
		return new String[] {"name"};
	}

	private Process _process;

	@Inject
	private WorkflowMetricsRESTTestHelper _workflowMetricsRESTTestHelper;

}