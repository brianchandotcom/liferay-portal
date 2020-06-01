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
import com.liferay.app.builder.service.AppBuilderAppLocalService;
import com.liferay.app.builder.workflow.rest.client.dto.v1_0.AppWorkflowAction;
import com.liferay.app.builder.workflow.rest.client.dto.v1_0.AppWorkflowTask;
import com.liferay.app.builder.workflow.rest.client.pagination.Page;
import com.liferay.app.builder.workflow.rest.client.serdes.v1_0.AppWorkflowTaskSerDes;
import com.liferay.app.builder.workflow.rest.resource.v1_0.test.BaseAppWorkflowTaskResourceTestCase.GraphQLField;
import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.test.rule.DataGuard;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.test.rule.Inject;

import java.util.Arrays;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.runner.RunWith;

/**
 * @author Rafael Praxedes
 */
@DataGuard(scope = DataGuard.Scope.METHOD)
@RunWith(Arquillian.class)
public class AppWorkflowTaskResourceTest
	extends BaseAppWorkflowTaskResourceTestCase {

	@Before
	public void setUp() throws Exception {
		super.setUp();

		_appBuilderApp = _appBuilderAppLocalService.addAppBuilderApp(
			testGroup.getGroupId(), testCompany.getCompanyId(),
			testGroup.getCreatorUserId(), true, RandomTestUtil.nextLong(),
			RandomTestUtil.nextLong(), RandomTestUtil.nextLong(),
			RandomTestUtil.nextLong(), RandomTestUtil.randomLocaleStringMap(),
			RandomTestUtil.randomString());
	}

	@Override
	public void testGraphQLGetAppWorkflowTasksPage() throws Exception {
		Long appId = testGetAppWorkflowTasksPage_getAppId();

		GraphQLField graphQLField = new GraphQLField(
			"appWorkflowTasks",
			HashMapBuilder.<String, Object>put(
				"appId", appId
			).build(),
			new GraphQLField("items", getGraphQLFields()),
			new GraphQLField("page"), new GraphQLField("totalCount"));

		JSONObject appWorkflowTasksJSONObject = JSONUtil.getValueAsJSONObject(
			invokeGraphQLQuery(graphQLField), "JSONObject/data",
			"JSONObject/appWorkflowTasks");

		Assert.assertEquals(0, appWorkflowTasksJSONObject.get("totalCount"));

		AppWorkflowTask appWorkflowTask1 =
			testGetAppWorkflowTasksPage_addAppWorkflowTask(
				appId, randomAppWorkflowTask());
		AppWorkflowTask appWorkflowTask2 =
			testGetAppWorkflowTasksPage_addAppWorkflowTask(
				appId, randomAppWorkflowTask());

		appWorkflowTasksJSONObject = JSONUtil.getValueAsJSONObject(
			invokeGraphQLQuery(graphQLField), "JSONObject/data",
			"JSONObject/appWorkflowTasks");

		Assert.assertEquals(2, appWorkflowTasksJSONObject.get("totalCount"));

		assertEqualsIgnoringOrder(
			Arrays.asList(appWorkflowTask1, appWorkflowTask2),
			Arrays.asList(
				AppWorkflowTaskSerDes.toDTOs(
					appWorkflowTasksJSONObject.getString("items"))));
	}

	@Override
	public void testPostAppWorkflowTasks() throws Exception {
		AppWorkflowTask appWorkflowTask1 = randomAppWorkflowTask();
		AppWorkflowTask appWorkflowTask2 = randomAppWorkflowTask();

		Page<AppWorkflowTask> page =
			appWorkflowTaskResource.postAppWorkflowTasks(
				_appBuilderApp.getAppBuilderAppId(),
				new AppWorkflowTask[] {appWorkflowTask1, appWorkflowTask2});

		Assert.assertEquals(2, page.getTotalCount());

		assertEqualsIgnoringOrder(
			Arrays.asList(appWorkflowTask1, appWorkflowTask2),
			(List<AppWorkflowTask>)page.getItems());
		assertValid(page);
	}

	protected String[] getAdditionalAssertFieldNames() {
		return new String[] {"appId", "dataLayoutIds", "name"};
	}

	@Override
	protected AppWorkflowTask randomAppWorkflowTask() throws Exception {
		return new AppWorkflowTask() {
			{
				appId = _appBuilderApp.getAppBuilderAppId();
				appWorkflowActions = new AppWorkflowAction[0];
				dataLayoutIds = new Long[] {
					_appBuilderApp.getDdmStructureLayoutId()
				};
				name = StringUtil.toLowerCase(RandomTestUtil.randomString());
			}
		};
	}

	@Override
	protected AppWorkflowTask testGetAppWorkflowTasksPage_addAppWorkflowTask(
			Long appId, AppWorkflowTask appWorkflowTask)
		throws Exception {

		Page<AppWorkflowTask> page =
			appWorkflowTaskResource.getAppWorkflowTasksPage(appId);

		List<AppWorkflowTask> appWorkflowTasks =
			(List<AppWorkflowTask>)page.getItems();

		appWorkflowTasks.add(appWorkflowTask);

		page = appWorkflowTaskResource.postAppWorkflowTasks(
			appId, appWorkflowTasks.toArray(new AppWorkflowTask[0]));

		appWorkflowTasks = (List<AppWorkflowTask>)page.getItems();

		return appWorkflowTasks.get(appWorkflowTasks.size() - 1);
	}

	@Override
	protected Long testGetAppWorkflowTasksPage_getAppId() throws Exception {
		return _appBuilderApp.getAppBuilderAppId();
	}

	private AppBuilderApp _appBuilderApp;

	@Inject
	private AppBuilderAppLocalService _appBuilderAppLocalService;

}