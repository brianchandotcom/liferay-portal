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
import com.liferay.petra.function.UnsafeSupplier;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.odata.entity.EntityField;
import com.liferay.portal.search.document.DocumentBuilderFactory;
import com.liferay.portal.search.engine.adapter.SearchEngineAdapter;
import com.liferay.portal.search.query.Queries;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.workflow.metrics.rest.client.dto.v1_0.AssigneeUser;
import com.liferay.portal.workflow.metrics.rest.client.dto.v1_0.Instance;
import com.liferay.portal.workflow.metrics.rest.client.dto.v1_0.Process;
import com.liferay.portal.workflow.metrics.rest.client.dto.v1_0.Task;
import com.liferay.portal.workflow.metrics.rest.client.pagination.Page;
import com.liferay.portal.workflow.metrics.rest.client.pagination.Pagination;
import com.liferay.portal.workflow.metrics.rest.resource.v1_0.test.helper.WorkflowMetricsRESTTestHelper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import org.apache.commons.beanutils.BeanUtils;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Rafael Praxedes
 */
@RunWith(Arquillian.class)
public class AssigneeUserResourceTest extends BaseAssigneeUserResourceTestCase {

	@BeforeClass
	public static void setUpClass() throws Exception {
		BaseAssigneeUserResourceTestCase.setUpClass();

		_workflowMetricsRESTTestHelper = new WorkflowMetricsRESTTestHelper(
			_documentBuilderFactory, _queries, _searchEngineAdapter);
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

		_deleteSLATaskResults();
		_deleteTasks();
		_deleteTokens();
	}

	@Override
	@Test
	public void testGetProcessAssigneeUsersPage() throws Exception {
		super.testGetProcessAssigneeUsersPage();

		_deleteSLATaskResults();
		_deleteTasks();
		_deleteTokens();

		AssigneeUser assigneeUser1 = randomAssigneeUser();

		assigneeUser1.setOnTimeTaskCount(0L);
		assigneeUser1.setOverdueTaskCount(2L);
		assigneeUser1.setTaskCount(2L);

		Instance instance1 = _workflowMetricsRESTTestHelper.addInstance(
			testGroup.getCompanyId(), false, _process.getId());

		testGetProcessAssigneeUsersPage_addAssigneeUser(
			_process.getId(), assigneeUser1, () -> instance1,
			new Task() {
				{
					durationAvg = 0L;
					instanceCount = 1L;
					key = "review";
					name = "review";
					onTimeInstanceCount = 0L;
					overdueInstanceCount = 1L;
				}
			});

		Instance instance2 = _workflowMetricsRESTTestHelper.addInstance(
			testGroup.getCompanyId(), false, _process.getId());

		testGetProcessAssigneeUsersPage_addAssigneeUser(
			_process.getId(), assigneeUser1, () -> instance2,
			new Task() {
				{
					durationAvg = 0L;
					instanceCount = 1L;
					key = "update";
					name = "update";
					onTimeInstanceCount = 0L;
					overdueInstanceCount = 1L;
				}
			});

		AssigneeUser assigneeUser2 = randomAssigneeUser();

		assigneeUser2.setOnTimeTaskCount(1L);
		assigneeUser2.setOverdueTaskCount(0L);
		assigneeUser2.setTaskCount(1L);

		testGetProcessAssigneeUsersPage_addAssigneeUser(
			_process.getId(), assigneeUser2, () -> instance1,
			new Task() {
				{
					durationAvg = 0L;
					instanceCount = 1L;
					key = "review";
					name = "review";
					onTimeInstanceCount = 1L;
					overdueInstanceCount = 0L;
				}
			});

		Page<AssigneeUser> page =
			assigneeUserResource.getProcessAssigneeUsersPage(
				_process.getId(), new String[] {"update"}, Pagination.of(1, 10),
				"taskCount:asc");

		Assert.assertEquals(1, page.getTotalCount());

		assertEquals(
			Arrays.asList(
				new AssigneeUser() {
					{
						id = assigneeUser1.getId();
						name = assigneeUser1.getName();
						onTimeTaskCount = 0L;
						overdueTaskCount = 1L;
						taskCount = 1L;
					}
				}),
			(List<AssigneeUser>)page.getItems());

		page = assigneeUserResource.getProcessAssigneeUsersPage(
			_process.getId(), new String[] {"review"}, Pagination.of(1, 10),
			"overdueTaskCount:desc");

		Assert.assertEquals(2, page.getTotalCount());

		assertEquals(
			Arrays.asList(
				new AssigneeUser() {
					{
						id = assigneeUser1.getId();
						name = assigneeUser1.getName();
						onTimeTaskCount = 0L;
						overdueTaskCount = 1L;
						taskCount = 1L;
					}
				},
				new AssigneeUser() {
					{
						id = assigneeUser2.getId();
						name = assigneeUser2.getName();
						onTimeTaskCount = 1L;
						overdueTaskCount = 0L;
						taskCount = 1L;
					}
				}),
			(List<AssigneeUser>)page.getItems());
	}

	@Override
	@Test
	public void testGetProcessAssigneeUsersPageWithSortInteger()
		throws Exception {

		testGetProcessAssigneeUsersPageWithSort(
			EntityField.Type.INTEGER,
			(entityField, assigneeUser1, assigneeUser2) -> {
				if (Objects.equals(entityField.getName(), "taskCount")) {
					assigneeUser1.setTaskCount(1L);
					assigneeUser2.setTaskCount(3L);
				}
				else if (Objects.equals(
							entityField.getName(), "onTimeTaskCount")) {

					assigneeUser1.setOnTimeTaskCount(0L);
					assigneeUser2.setOnTimeTaskCount(1L);
				}
				else if (Objects.equals(
							entityField.getName(), "overdueTaskCount")) {

					assigneeUser1.setOverdueTaskCount(1L);
					assigneeUser2.setOverdueTaskCount(2L);
				}
				else {
					BeanUtils.setProperty(
						assigneeUser1, entityField.getName(), 1);
					BeanUtils.setProperty(
						assigneeUser2, entityField.getName(), 2);
				}
			});
	}

	protected String[] getAdditionalAssertFieldNames() {
		return new String[] {
			"id", "name", "onTimeTaskCount", "overdueTaskCount", "taskCount"
		};
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
				onTimeTaskCount = 1L;
				overdueTaskCount = 0L;
				taskCount = 1L;
			}
		};
	}

	@Override
	protected AssigneeUser testGetProcessAssigneeUsersPage_addAssigneeUser(
			Long processId, AssigneeUser assigneeUser)
		throws Exception {

		return testGetProcessAssigneeUsersPage_addAssigneeUser(
			processId, assigneeUser,
			() -> _workflowMetricsRESTTestHelper.addInstance(
				testGroup.getCompanyId(), false, _process.getId()),
			new Task() {
				{
					String randomString = RandomTestUtil.randomString();

					durationAvg = 0L;
					instanceCount = assigneeUser.getTaskCount();
					key = randomString;
					name = randomString;
					onTimeInstanceCount = assigneeUser.getOnTimeTaskCount();
					overdueInstanceCount = assigneeUser.getOverdueTaskCount();
				}
			});
	}

	protected AssigneeUser testGetProcessAssigneeUsersPage_addAssigneeUser(
			Long processId, AssigneeUser assigneeUser,
			UnsafeSupplier<Instance, Exception> instanceSupplier, Task... tasks)
		throws Exception {

		for (Task task : tasks) {
			_tasks.add(task);

			_workflowMetricsRESTTestHelper.addTask(
				assigneeUser.getId(), testGroup.getCompanyId(),
				instanceSupplier, processId, "RUNNING", task, "1.0");
		}

		return assigneeUser;
	}

	@Override
	protected Long testGetProcessAssigneeUsersPage_getProcessId()
		throws Exception {

		return _process.getId();
	}

	private void _deleteSLATaskResults() throws Exception {
		_workflowMetricsRESTTestHelper.deleteSLATaskResults(
			testGroup.getCompanyId(), _process.getId());
	}

	private void _deleteTasks() throws Exception {
		for (Task task : _tasks) {
			_workflowMetricsRESTTestHelper.deleteTask(
				testGroup.getCompanyId(), _process.getId(), task);
		}

		_tasks.clear();
	}

	private void _deleteTokens() throws Exception {
		_workflowMetricsRESTTestHelper.deleteTokens(
			testGroup.getCompanyId(), _process.getId());
	}

	@Inject
	private static DocumentBuilderFactory _documentBuilderFactory;

	@Inject
	private static Queries _queries;

	@Inject(blocking = false, filter = "search.engine.impl=Elasticsearch")
	private static SearchEngineAdapter _searchEngineAdapter;

	private static WorkflowMetricsRESTTestHelper _workflowMetricsRESTTestHelper;

	@Inject
	private Portal _portal;

	private Process _process;
	private final List<Task> _tasks = new ArrayList<>();

}