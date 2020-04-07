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
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.workflow.metrics.rest.client.dto.v1_0.Assignee;
import com.liferay.portal.workflow.metrics.rest.client.dto.v1_0.AssigneeBulkSelection;
import com.liferay.portal.workflow.metrics.rest.client.dto.v1_0.Instance;
import com.liferay.portal.workflow.metrics.rest.client.dto.v1_0.Process;
import com.liferay.portal.workflow.metrics.rest.client.dto.v1_0.Task;
import com.liferay.portal.workflow.metrics.rest.client.pagination.Page;
import com.liferay.portal.workflow.metrics.rest.client.pagination.Pagination;
import com.liferay.portal.workflow.metrics.rest.resource.v1_0.test.helper.WorkflowMetricsRESTTestHelper;

import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang.time.DateUtils;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Rafael Praxedes
 */
@RunWith(Arquillian.class)
public class AssigneeResourceTest extends BaseAssigneeResourceTestCase {

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
		_deleteTasks();
	}

	@Override
	@Test
	public void testPostProcessAssigneesPage() throws Exception {
		Assignee assignee1 = randomAssignee();

		assignee1.setOnTimeTaskCount(0L);
		assignee1.setOverdueTaskCount(3L);
		assignee1.setTaskCount(3L);

		Instance instance1 = _workflowMetricsRESTTestHelper.addInstance(
			testGroup.getCompanyId(), false, _process.getId());

		_addTask(
			assignee1.getId(), () -> instance1, _process.getId(),
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
			assignee1.getId(), () -> instance1, _process.getId(),
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
			assignee1.getId(), () -> instance2, _process.getId(),
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

		Assignee assignee2 = _randomAssignee(siteAdministrationRole);

		assignee2.setOnTimeTaskCount(1L);
		assignee2.setOverdueTaskCount(1L);
		assignee2.setTaskCount(2L);

		_addTask(
			assignee2.getId(), () -> instance1, _process.getId(),
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
			assignee2.getId(), () -> instance2, _process.getId(),
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

		Page<Assignee> page = assigneeResource.postProcessAssigneesPage(
			_process.getId(), Pagination.of(1, 10), "taskCount:asc",
			new AssigneeBulkSelection() {
				{
					completed = false;
					taskKeys = new String[] {"update"};
				}
			});

		Assert.assertEquals(1, page.getTotalCount());

		assertEquals(
			Arrays.asList(
				new Assignee() {
					{
						durationTaskAvg = 0L;
						id = assignee1.getId();
						name = assignee1.getName();
						onTimeTaskCount = 0L;
						overdueTaskCount = 2L;
						taskCount = 2L;
					}
				}),
			(List<Assignee>)page.getItems());

		page = assigneeResource.postProcessAssigneesPage(
			_process.getId(), Pagination.of(1, 10), "overdueTaskCount:desc",
			new AssigneeBulkSelection() {
				{
					completed = false;
					taskKeys = new String[] {"review"};
				}
			});

		Assert.assertEquals(2, page.getTotalCount());

		assertEquals(
			Arrays.asList(
				new Assignee() {
					{
						durationTaskAvg = 0L;
						id = assignee1.getId();
						name = assignee1.getName();
						onTimeTaskCount = 0L;
						overdueTaskCount = 1L;
						taskCount = 1L;
					}
				},
				new Assignee() {
					{
						durationTaskAvg = 0L;
						id = assignee2.getId();
						name = assignee2.getName();
						onTimeTaskCount = 1L;
						overdueTaskCount = 0L;
						taskCount = 1L;
					}
				}),
			(List<Assignee>)page.getItems());

		page = assigneeResource.postProcessAssigneesPage(
			_process.getId(), Pagination.of(1, 10), "overdueTaskCount:desc",
			new AssigneeBulkSelection() {
				{
					completed = false;
					roleIds = new Long[] {siteAdministrationRole.getRoleId()};
					taskKeys = new String[] {"review"};
				}
			});

		Assert.assertEquals(1, page.getTotalCount());

		assertEquals(
			Arrays.asList(
				new Assignee() {
					{
						durationTaskAvg = 0L;
						id = assignee2.getId();
						name = assignee2.getName();
						onTimeTaskCount = 1L;
						overdueTaskCount = 0L;
						taskCount = 1L;
					}
				}),
			(List<Assignee>)page.getItems());

		page = assigneeResource.postProcessAssigneesPage(
			_process.getId(), Pagination.of(1, 10), "overdueTaskCount:desc",
			new AssigneeBulkSelection() {
				{
					completed = false;
					keywords = assignee2.getName();
					roleIds = new Long[] {siteAdministrationRole.getRoleId()};
					taskKeys = new String[] {"review"};
				}
			});

		Assert.assertEquals(1, page.getTotalCount());

		assertEquals(
			Arrays.asList(
				new Assignee() {
					{
						durationTaskAvg = 0L;
						id = assignee2.getId();
						name = assignee2.getName();
						onTimeTaskCount = 1L;
						overdueTaskCount = 0L;
						taskCount = 1L;
					}
				}),
			(List<Assignee>)page.getItems());

		page = assigneeResource.postProcessAssigneesPage(
			_process.getId(), Pagination.of(1, 10), "overdueTaskCount:desc",
			new AssigneeBulkSelection() {
				{
					completed = false;
					keywords = assignee1.getName();
					roleIds = new Long[] {siteAdministrationRole.getRoleId()};
					taskKeys = new String[] {"review"};
				}
			});

		Assert.assertEquals(0, page.getTotalCount());

		Assignee assignee3 = randomAssignee();

		assignee3.setOnTimeTaskCount(0L);
		assignee3.setOverdueTaskCount(0L);
		assignee3.setTaskCount(1L);

		_addTask(
			assignee3.getId(), () -> instance1, _process.getId(),
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

		page = assigneeResource.postProcessAssigneesPage(
			_process.getId(), Pagination.of(1, 10), "overdueTaskCount:desc",
			new AssigneeBulkSelection() {
				{
					completed = false;
				}
			});

		Assert.assertEquals(3, page.getTotalCount());

		assertEquals(
			Arrays.asList(assignee1, assignee2, assignee3),
			(List<Assignee>)page.getItems());

		Instance instance3 = _workflowMetricsRESTTestHelper.addInstance(
			testGroup.getCompanyId(), true, _process.getId());

		_addTask(
			assignee1.getId(), () -> instance3, _process.getId(), "COMPLETED",
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
			assignee2.getId(), () -> instance3, _process.getId(), "COMPLETED",
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

		page = assigneeResource.postProcessAssigneesPage(
			_process.getId(), Pagination.of(1, 10), "durationTaskAvg:asc",
			new AssigneeBulkSelection() {
				{
					completed = true;
					dateEnd = RandomTestUtil.nextDate();
					dateStart = DateUtils.addMinutes(
						RandomTestUtil.nextDate(), -2);
				}
			});

		Assert.assertEquals(2, page.getTotalCount());

		assertEquals(
			Arrays.asList(
				new Assignee() {
					{
						durationTaskAvg = 1500L;
						id = assignee1.getId();
						name = assignee1.getName();
						onTimeTaskCount = 2L;
						overdueTaskCount = 0L;
						taskCount = 2L;
					}
				},
				new Assignee() {
					{
						durationTaskAvg = 3000L;
						id = assignee2.getId();
						name = assignee2.getName();
						onTimeTaskCount = 2L;
						overdueTaskCount = 0L;
						taskCount = 2L;
					}
				}),
			(List<Assignee>)page.getItems());
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
	protected Assignee randomAssignee() throws Exception {
		User user = UserTestUtil.addUser();

		return new Assignee() {
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
			_workflowMetricsRESTTestHelper.addTask(
				assigneeId, testGroup.getCompanyId(), instanceSupplier,
				processId, status, task, "1.0");
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
		_workflowMetricsRESTTestHelper.deleteTasks(
			testGroup.getCompanyId(), _process.getId());
	}

	private Assignee _randomAssignee(Role role) throws Exception {
		Assignee assignee = randomAssignee();

		_addRoleUser(role, assignee.getId());

		return assignee;
	}

	@Inject
	private Portal _portal;

	private Process _process;

	@Inject
	private RoleLocalService _roleLocalService;

	@Inject
	private UserGroupRoleLocalService _userGroupRoleLocalService;

	@Inject
	private UserLocalService _userLocalService;

	@Inject
	private WorkflowMetricsRESTTestHelper _workflowMetricsRESTTestHelper;

}