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
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.model.role.RoleConstants;
import com.liferay.portal.kernel.service.RoleLocalService;
import com.liferay.portal.kernel.service.UserGroupRoleLocalService;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
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
import org.apache.commons.lang.time.DateUtils;

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
		assigneeUser1.setOverdueTaskCount(3L);
		assigneeUser1.setTaskCount(3L);

		Instance instance1 = _workflowMetricsRESTTestHelper.addInstance(
			testGroup.getCompanyId(), false, _process.getId());

		_addTask(
			assigneeUser1.getId(), () -> instance1, _process.getId(),
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

		_addTask(
			assigneeUser1.getId(), () -> instance1, _process.getId(),
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

		Instance instance2 = _workflowMetricsRESTTestHelper.addInstance(
			testGroup.getCompanyId(), false, _process.getId());

		_addTask(
			assigneeUser1.getId(), () -> instance2, _process.getId(),
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

		Role siteAdministrationRole = _roleLocalService.getRole(
			TestPropsValues.getCompanyId(), RoleConstants.SITE_ADMINISTRATOR);

		AssigneeUser assigneeUser2 = _randomAssigneeUser(
			siteAdministrationRole);

		assigneeUser2.setOnTimeTaskCount(1L);
		assigneeUser2.setOverdueTaskCount(1L);
		assigneeUser2.setTaskCount(2L);

		_addTask(
			assigneeUser2.getId(), () -> instance1, _process.getId(),
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

		_addTask(
			assigneeUser2.getId(), () -> instance2, _process.getId(),
			new Task() {
				{
					durationAvg = 0L;
					instanceCount = 1L;
					key = "submit";
					name = "submit";
					onTimeInstanceCount = 0L;
					overdueInstanceCount = 1L;
				}
			});

		Page<AssigneeUser> page =
			assigneeUserResource.getProcessAssigneeUsersPage(
				_process.getId(), false, null, null, null, null,
				new String[] {"update"}, Pagination.of(1, 10), "taskCount:asc");

		Assert.assertEquals(1, page.getTotalCount());

		assertEquals(
			Arrays.asList(
				new AssigneeUser() {
					{
						durationTaskAvg = 0L;
						id = assigneeUser1.getId();
						name = assigneeUser1.getName();
						onTimeTaskCount = 0L;
						overdueTaskCount = 2L;
						taskCount = 2L;
					}
				}),
			(List<AssigneeUser>)page.getItems());

		page = assigneeUserResource.getProcessAssigneeUsersPage(
			_process.getId(), false, null, null, null, null,
			new String[] {"review"}, Pagination.of(1, 10),
			"overdueTaskCount:desc");

		Assert.assertEquals(2, page.getTotalCount());

		assertEquals(
			Arrays.asList(
				new AssigneeUser() {
					{
						durationTaskAvg = 0L;
						id = assigneeUser1.getId();
						name = assigneeUser1.getName();
						onTimeTaskCount = 0L;
						overdueTaskCount = 1L;
						taskCount = 1L;
					}
				},
				new AssigneeUser() {
					{
						durationTaskAvg = 0L;
						id = assigneeUser2.getId();
						name = assigneeUser2.getName();
						onTimeTaskCount = 1L;
						overdueTaskCount = 0L;
						taskCount = 1L;
					}
				}),
			(List<AssigneeUser>)page.getItems());

		page = assigneeUserResource.getProcessAssigneeUsersPage(
			_process.getId(), false, null, null, null,
			new Long[] {siteAdministrationRole.getRoleId()},
			new String[] {"review"}, Pagination.of(1, 10),
			"overdueTaskCount:desc");

		Assert.assertEquals(1, page.getTotalCount());

		assertEquals(
			Arrays.asList(
				new AssigneeUser() {
					{
						durationTaskAvg = 0L;
						id = assigneeUser2.getId();
						name = assigneeUser2.getName();
						onTimeTaskCount = 1L;
						overdueTaskCount = 0L;
						taskCount = 1L;
					}
				}),
			(List<AssigneeUser>)page.getItems());

		page = assigneeUserResource.getProcessAssigneeUsersPage(
			_process.getId(), false, null, null, assigneeUser2.getName(),
			new Long[] {siteAdministrationRole.getRoleId()},
			new String[] {"review"}, Pagination.of(1, 10),
			"overdueTaskCount:desc");

		Assert.assertEquals(1, page.getTotalCount());

		assertEquals(
			Arrays.asList(
				new AssigneeUser() {
					{
						durationTaskAvg = 0L;
						id = assigneeUser2.getId();
						name = assigneeUser2.getName();
						onTimeTaskCount = 1L;
						overdueTaskCount = 0L;
						taskCount = 1L;
					}
				}),
			(List<AssigneeUser>)page.getItems());

		page = assigneeUserResource.getProcessAssigneeUsersPage(
			_process.getId(), false, null, null, assigneeUser1.getName(),
			new Long[] {siteAdministrationRole.getRoleId()},
			new String[] {"review"}, Pagination.of(1, 10),
			"overdueTaskCount:desc");

		Assert.assertEquals(0, page.getTotalCount());

		AssigneeUser assigneeUser3 = randomAssigneeUser();

		assigneeUser3.setOnTimeTaskCount(0L);
		assigneeUser3.setOverdueTaskCount(0L);
		assigneeUser3.setTaskCount(1L);

		_addTask(
			assigneeUser3.getId(), () -> instance1, _process.getId(),
			new Task() {
				{
					durationAvg = 0L;
					instanceCount = 1L;
					key = "review";
					name = "review";
					onTimeInstanceCount = 0L;
					overdueInstanceCount = 0L;
				}
			});

		page = assigneeUserResource.getProcessAssigneeUsersPage(
			_process.getId(), false, null, null, null, null, null,
			Pagination.of(1, 10), "overdueTaskCount:desc");

		Assert.assertEquals(3, page.getTotalCount());

		assertEquals(
			Arrays.asList(assigneeUser1, assigneeUser2, assigneeUser3),
			(List<AssigneeUser>)page.getItems());

		Instance instance3 = _workflowMetricsRESTTestHelper.addInstance(
			testGroup.getCompanyId(), true, _process.getId());

		_addTask(
			assigneeUser1.getId(), () -> instance3, _process.getId(),
			"COMPLETED",
			new Task() {
				{
					durationAvg = 1000L;
					instanceCount = 1L;
					key = "review";
					name = "review";
					onTimeInstanceCount = 1L;
					overdueInstanceCount = 0L;
				}
			},
			new Task() {
				{
					durationAvg = 2000L;
					instanceCount = 1L;
					key = "update";
					name = "update";
					onTimeInstanceCount = 1L;
					overdueInstanceCount = 0L;
				}
			});

		_addTask(
			assigneeUser2.getId(), () -> instance3, _process.getId(),
			"COMPLETED",
			new Task() {
				{
					durationAvg = 2000L;
					instanceCount = 1L;
					key = "review";
					name = "review";
					onTimeInstanceCount = 1L;
					overdueInstanceCount = 0L;
				}
			},
			new Task() {
				{
					durationAvg = 4000L;
					instanceCount = 1L;
					key = "update";
					name = "update";
					onTimeInstanceCount = 1L;
					overdueInstanceCount = 0L;
				}
			});

		page = assigneeUserResource.getProcessAssigneeUsersPage(
			_process.getId(), true, RandomTestUtil.nextDate(),
			DateUtils.addMinutes(RandomTestUtil.nextDate(), -2), null, null,
			null, Pagination.of(1, 10), "durationTaskAvg:asc");

		Assert.assertEquals(2, page.getTotalCount());

		assertEquals(
			Arrays.asList(
				new AssigneeUser() {
					{
						durationTaskAvg = 1500L;
						id = assigneeUser1.getId();
						name = assigneeUser1.getName();
						onTimeTaskCount = 2L;
						overdueTaskCount = 0L;
						taskCount = 2L;
					}
				},
				new AssigneeUser() {
					{
						durationTaskAvg = 3000L;
						id = assigneeUser2.getId();
						name = assigneeUser2.getName();
						onTimeTaskCount = 2L;
						overdueTaskCount = 0L;
						taskCount = 2L;
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

	@Override
	protected String[] getAdditionalAssertFieldNames() {
		return new String[] {
			"durationTaskAvg", "id", "name", "onTimeTaskCount",
			"overdueTaskCount", "taskCount"
		};
	}

	@Override
	protected String[] getIgnoredEntityFieldNames() {
		return new String[] {"durationTaskAvg"};
	}

	@Override
	protected AssigneeUser randomAssigneeUser() throws Exception {
		User user = UserTestUtil.addUser();

		return new AssigneeUser() {
			{
				durationTaskAvg = 0L;
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

		_addTask(
			assigneeUser.getId(),
			() -> _workflowMetricsRESTTestHelper.addInstance(
				testGroup.getCompanyId(), false, _process.getId()),
			processId,
			new Task() {
				{
					durationAvg = assigneeUser.getDurationTaskAvg();
					instanceCount = assigneeUser.getTaskCount();

					String randomString = RandomTestUtil.randomString();

					key = randomString;
					name = randomString;

					onTimeInstanceCount = assigneeUser.getOnTimeTaskCount();
					overdueInstanceCount = assigneeUser.getOverdueTaskCount();
				}
			});

		return assigneeUser;
	}

	@Override
	protected Long testGetProcessAssigneeUsersPage_getProcessId()
		throws Exception {

		return _process.getId();
	}

	private void _addRoleUser(Role role, long userId) throws Exception {
		_userLocalService.addRoleUser(role.getRoleId(), userId);

		_userGroupRoleLocalService.addUserGroupRoles(
			new long[] {userId}, TestPropsValues.getGroupId(),
			role.getRoleId());
	}

	private void _addTask(
			long assigneeId,
			UnsafeSupplier<Instance, Exception> instanceSupplier,
			long processId, String status, Task... tasks)
		throws Exception {

		for (Task task : tasks) {
			_tasks.add(
				_workflowMetricsRESTTestHelper.addTask(
					assigneeId, testGroup.getCompanyId(), instanceSupplier,
					processId, status, task, "1.0"));
		}
	}

	private void _addTask(
			long assigneeId,
			UnsafeSupplier<Instance, Exception> instanceSupplier,
			long processId, Task... tasks)
		throws Exception {

		_addTask(assigneeId, instanceSupplier, processId, "RUNNING", tasks);
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

	private AssigneeUser _randomAssigneeUser(Role role) throws Exception {
		AssigneeUser assigneeUser = randomAssigneeUser();

		_addRoleUser(role, assigneeUser.getId());

		return assigneeUser;
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

	@Inject
	private RoleLocalService _roleLocalService;

	private final List<Task> _tasks = new ArrayList<>();

	@Inject
	private UserGroupRoleLocalService _userGroupRoleLocalService;

	@Inject
	private UserLocalService _userLocalService;

}