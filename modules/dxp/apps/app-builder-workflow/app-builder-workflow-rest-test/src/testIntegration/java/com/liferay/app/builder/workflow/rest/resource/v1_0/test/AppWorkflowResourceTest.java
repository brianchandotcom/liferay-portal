/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.app.builder.workflow.rest.resource.v1_0.test;

import com.liferay.app.builder.model.AppBuilderApp;
import com.liferay.app.builder.service.AppBuilderAppDataRecordLinkLocalService;
import com.liferay.app.builder.workflow.rest.client.dto.v1_0.AppWorkflow;
import com.liferay.app.builder.workflow.rest.client.dto.v1_0.AppWorkflowTask;
import com.liferay.app.builder.workflow.rest.resource.v1_0.test.helper.AppWorkflowTestHelper;
import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.dynamic.data.lists.model.DDLRecord;
import com.liferay.dynamic.data.lists.service.DDLRecordLocalService;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.messaging.proxy.ProxyMessageListener;
import com.liferay.portal.kernel.model.WorkflowInstanceLink;
import com.liferay.portal.kernel.security.permission.ResourceActionsUtil;
import com.liferay.portal.kernel.service.RoleLocalService;
import com.liferay.portal.kernel.service.WorkflowDefinitionLinkLocalService;
import com.liferay.portal.kernel.service.WorkflowInstanceLinkLocalService;
import com.liferay.portal.kernel.test.rule.DataGuard;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.kernel.workflow.WorkflowTask;
import com.liferay.portal.kernel.workflow.WorkflowTaskManager;
import com.liferay.portal.test.log.LogCapture;
import com.liferay.portal.test.log.LoggerTestUtil;
import com.liferay.portal.test.rule.Inject;

import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Rafael Praxedes
 */
@DataGuard(scope = DataGuard.Scope.METHOD)
@RunWith(Arquillian.class)
public class AppWorkflowResourceTest extends BaseAppWorkflowResourceTestCase {

	@Before
	@Override
	public void setUp() throws Exception {
		super.setUp();

		_appBuilderApp = _appWorkflowTestHelper.addAppBuilderApp(
			testCompany.getCompanyId(), testGroup);
	}

	@Override
	@Test
	public void testDeleteAppWorkflow() throws Exception {
		AppWorkflow appWorkflow = testPostAppWorkflow_addAppWorkflow(
			randomAppWorkflow());

		assertHttpResponseStatusCode(
			204,
			appWorkflowResource.deleteAppWorkflowHttpResponse(
				appWorkflow.getAppId()));

		try (LogCapture logCapture = LoggerTestUtil.configureLog4JLogger(
				ProxyMessageListener.class.getName(), LoggerTestUtil.OFF)) {

			assertHttpResponseStatusCode(
				404,
				appWorkflowResource.getAppWorkflowHttpResponse(
					appWorkflow.getAppId()));

			assertHttpResponseStatusCode(
				404, appWorkflowResource.getAppWorkflowHttpResponse(0L));
		}
	}

	@Override
	@Test
	public void testGetAppWorkflow() throws Exception {
		AppWorkflow postAppWorkflow = testPostAppWorkflow_addAppWorkflow(
			randomAppWorkflow());

		AppWorkflow getAppWorkflow = appWorkflowResource.getAppWorkflow(
			postAppWorkflow.getAppId());

		assertEquals(postAppWorkflow, getAppWorkflow);
		assertValid(getAppWorkflow);

		Assert.assertNotNull(
			_workflowDefinitionLinkLocalService.fetchWorkflowDefinitionLink(
				testCompany.getCompanyId(), testGroup.getGroupId(),
				ResourceActionsUtil.getCompositeModelName(
					AppBuilderApp.class.getName(), DDLRecord.class.getName()),
				getAppWorkflow.getAppId(), 0));

		DDLRecord ddlRecord = _appWorkflowTestHelper.addDDLRecord(
			_appBuilderApp, testGroup);

		WorkflowInstanceLink workflowInstanceLink =
			_workflowInstanceLinkLocalService.fetchWorkflowInstanceLink(
				testCompany.getCompanyId(), testGroup.getGroupId(),
				ResourceActionsUtil.getCompositeModelName(
					AppBuilderApp.class.getName(), DDLRecord.class.getName()),
				ddlRecord.getRecordId());

		Assert.assertNotNull(workflowInstanceLink);

		Assert.assertEquals(
			WorkflowConstants.STATUS_PENDING, ddlRecord.getStatus());

		AppWorkflowTask[] appWorkflowTasks =
			getAppWorkflow.getAppWorkflowTasks();

		Assert.assertEquals(
			appWorkflowTasks.toString(), 2, appWorkflowTasks.length);

		AppWorkflowTask appWorkflowTask = appWorkflowTasks[0];

		List<WorkflowTask> workflowTasks =
			_workflowTaskManager.getWorkflowTasksByWorkflowInstance(
				testCompany.getCompanyId(), null,
				workflowInstanceLink.getWorkflowInstanceId(), false,
				QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);

		Assert.assertEquals(workflowTasks.toString(), 1, workflowTasks.size());

		WorkflowTask workflowTask = workflowTasks.get(0);

		Assert.assertEquals(appWorkflowTask.getName(), workflowTask.getName());

		_ddlRecordLocalService.deleteDDLRecord(ddlRecord);

		Assert.assertNull(
			_appBuilderAppDataRecordLinkLocalService.
				fetchDDLRecordAppBuilderAppDataRecordLink(
					ddlRecord.getRecordId()));

		Assert.assertNull(
			_workflowInstanceLinkLocalService.fetchWorkflowInstanceLink(
				testCompany.getCompanyId(), testGroup.getGroupId(),
				ResourceActionsUtil.getCompositeModelName(
					AppBuilderApp.class.getName(), DDLRecord.class.getName()),
				ddlRecord.getRecordId()));
	}

	@Override
	@Test
	public void testGraphQLDeleteAppWorkflow() throws Exception {
		AppWorkflow appWorkflow = testPostAppWorkflow_addAppWorkflow(
			randomAppWorkflow());

		Assert.assertTrue(
			JSONUtil.getValueAsBoolean(
				invokeGraphQLMutation(
					new GraphQLField(
						"deleteAppWorkflow",
						HashMapBuilder.<String, Object>put(
							"appId", appWorkflow.getAppId()
						).build())),
				"JSONObject/data", "Object/deleteAppWorkflow"));

		try (LogCapture logCapture1 = LoggerTestUtil.configureLog4JLogger(
				"graphql.execution.SimpleDataFetcherExceptionHandler",
				LoggerTestUtil.WARN);
			LogCapture logCapture2 = LoggerTestUtil.configureLog4JLogger(
				ProxyMessageListener.class.getName(), LoggerTestUtil.OFF)) {

			JSONArray errorsJSONArray = JSONUtil.getValueAsJSONArray(
				invokeGraphQLQuery(
					new GraphQLField(
						"appWorkflow",
						HashMapBuilder.<String, Object>put(
							"appId", appWorkflow.getAppId()
						).build(),
						new GraphQLField("appId"))),
				"JSONArray/errors");

			Assert.assertTrue(errorsJSONArray.length() > 0);
		}
	}

	@Override
	@Test
	public void testPutAppWorkflow() throws Exception {
		AppWorkflow postAppWorkflow = testPostAppWorkflow_addAppWorkflow(
			randomAppWorkflow());

		AppWorkflow randomAppWorkflow = randomAppWorkflow();

		AppWorkflow putAppWorkflow = appWorkflowResource.putAppWorkflow(
			postAppWorkflow.getAppId(), randomAppWorkflow);

		assertEquals(randomAppWorkflow, putAppWorkflow);
		assertValid(putAppWorkflow);

		AppWorkflow getAppWorkflow = appWorkflowResource.getAppWorkflow(
			putAppWorkflow.getAppId());

		assertEquals(randomAppWorkflow, getAppWorkflow);
		assertValid(getAppWorkflow);
	}

	@Override
	protected String[] getAdditionalAssertFieldNames() {
		return new String[] {"appId", "appWorkflowStates", "appWorkflowTasks"};
	}

	@Override
	protected AppWorkflow randomAppWorkflow() throws Exception {
		return _appWorkflowTestHelper.createAppWorkflow(_appBuilderApp);
	}

	@Override
	protected AppWorkflow testPostAppWorkflow_addAppWorkflow(
			AppWorkflow appWorkflow)
		throws Exception {

		return appWorkflowResource.postAppWorkflow(
			appWorkflow.getAppId(), appWorkflow);
	}

	private AppBuilderApp _appBuilderApp;

	@Inject
	private AppBuilderAppDataRecordLinkLocalService
		_appBuilderAppDataRecordLinkLocalService;

	@Inject
	private AppWorkflowTestHelper _appWorkflowTestHelper;

	@Inject
	private DDLRecordLocalService _ddlRecordLocalService;

	@Inject
	private RoleLocalService _roleLocalService;

	@Inject
	private WorkflowDefinitionLinkLocalService
		_workflowDefinitionLinkLocalService;

	@Inject
	private WorkflowInstanceLinkLocalService _workflowInstanceLinkLocalService;

	@Inject
	private WorkflowTaskManager _workflowTaskManager;

}