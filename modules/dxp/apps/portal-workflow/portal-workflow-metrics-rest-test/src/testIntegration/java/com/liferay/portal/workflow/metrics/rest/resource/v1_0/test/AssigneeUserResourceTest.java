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

package com.liferay.portal.workflow.metrics.rest.resource.v1_0.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.search.engine.adapter.SearchEngineAdapter;
import com.liferay.portal.search.query.Queries;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.workflow.metrics.rest.client.dto.v1_0.AssigneeUser;
import com.liferay.portal.workflow.metrics.rest.client.dto.v1_0.Process;
import com.liferay.portal.workflow.metrics.rest.resource.v1_0.test.helper.WorkflowMetricsRESTTestHelper;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;

/**
 * @author Rafael Praxedes
 */
@RunWith(Arquillian.class)
public class AssigneeUserResourceTest extends BaseAssigneeUserResourceTestCase {

	@BeforeClass
	public static void setUpClass() throws Exception {
		BaseProcessResourceTestCase.setUpClass();

		_workflowMetricsRESTTestHelper = new WorkflowMetricsRESTTestHelper(
			_queries, _searchEngineAdapter);
	}

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

		if (_process != null) {
			_workflowMetricsRESTTestHelper.deleteProcess(
				testGroup.getCompanyId(), _process);
		}
	}

	@Override
	protected AssigneeUser randomAssigneeUser() throws Exception {
		User user = UserTestUtil.addUser();

		return new AssigneeUser() {
			{
				id = user.getUserId();
				image = user.getPortraitURL(
					new ThemeDisplay() {
						{
							setPathImage(_portal.getPathImage());
						}
					});
				name = user.getFullName();
			}
		};
	}

	@Override
	protected AssigneeUser testGetProcessAssigneeUsersPage_addAssigneeUser(
			Long processId, AssigneeUser assigneeUser)
		throws Exception {

		_workflowMetricsRESTTestHelper.addTask(
			assigneeUser.getId(), testGroup.getCompanyId(), processId);

		return assigneeUser;
	}

	@Override
	protected Long testGetProcessAssigneeUsersPage_getProcessId()
		throws Exception {

		return _process.getId();
	}

	@Inject
	private static Queries _queries;

	@Inject(blocking = false, filter = "search.engine.impl=Elasticsearch")
	private static SearchEngineAdapter _searchEngineAdapter;

	private static WorkflowMetricsRESTTestHelper _workflowMetricsRESTTestHelper;

	@Inject
	private Portal _portal;

	private Process _process;

}