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
import com.liferay.portal.workflow.metrics.rest.client.dto.v1_0.AssigneeMetric;
import com.liferay.portal.workflow.metrics.rest.client.dto.v1_0.AssigneeMetricBulkSelection;
import com.liferay.portal.workflow.metrics.rest.client.dto.v1_0.Instance;
import com.liferay.portal.workflow.metrics.rest.client.dto.v1_0.Node;
import com.liferay.portal.workflow.metrics.rest.client.dto.v1_0.NodeMetric;
import com.liferay.portal.workflow.metrics.rest.client.dto.v1_0.Process;
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
public class AssigneeMetricResourceTest
	extends BaseAssigneeMetricResourceTestCase {

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
	}

	@Override
	@Test
	public void testPostProcessAssigneeMetricsPage() throws Exception {
		AssigneeMetric assigneeMetric1 = randomAssigneeMetric();

		assigneeMetric1.setOnTimeTaskCount(0L);
		assigneeMetric1.setOverdueTaskCount(3L);
		assigneeMetric1.setTaskCount(3L);

		Assignee assignee1 = assigneeMetric1.getAssignee();

		Instance instance1 = _workflowMetricsRESTTestHelper.addInstance(
			testGroup.getCompanyId(), false, _process.getId());
		Long reviewNodeId = RandomTestUtil.nextLong();

		_addNodeMetric(
			assignee1, () -> instance1, _process.getId(),
			new NodeMetric() {
				{
					durationAvg = 0L;
					instanceCount = 1L;
					node = new Node() {
						{
							id = reviewNodeId;
							label = "review";
							name = "review";
						}
					};
					onTimeInstanceCount = 0L;
					overdueInstanceCount = 1L;
				}
			});

		Long updateNodeId = RandomTestUtil.nextLong();

		_addNodeMetric(
			assignee1, () -> instance1, _process.getId(),
			new NodeMetric() {
				{
					durationAvg = 0L;
					instanceCount = 1L;
					node = new Node() {
						{
							id = updateNodeId;
							label = "update";
							name = "update";
						}
					};
					onTimeInstanceCount = 0L;
					overdueInstanceCount = 1L;
				}
			});

		Instance instance2 = _workflowMetricsRESTTestHelper.addInstance(
			testGroup.getCompanyId(), false, _process.getId());

		_addNodeMetric(
			assignee1, () -> instance2, _process.getId(),
			new NodeMetric() {
				{
					durationAvg = 0L;
					instanceCount = 1L;
					node = new Node() {
						{
							id = updateNodeId;
							label = "update";
							name = "update";
						}
					};
					onTimeInstanceCount = 0L;
					overdueInstanceCount = 1L;
				}
			});

		Role siteAdministrationRole = _roleLocalService.getRole(
			TestPropsValues.getCompanyId(), RoleConstants.SITE_ADMINISTRATOR);

		AssigneeMetric assigneeMetric2 = _randomAssigneeMetric(
			siteAdministrationRole);

		Assignee assignee2 = assigneeMetric2.getAssignee();

		assigneeMetric2.setOnTimeTaskCount(1L);
		assigneeMetric2.setOverdueTaskCount(1L);
		assigneeMetric2.setTaskCount(2L);

		_addNodeMetric(
			assignee2, () -> instance1, _process.getId(),
			new NodeMetric() {
				{
					durationAvg = 0L;
					instanceCount = 1L;
					node = new Node() {
						{
							id = reviewNodeId;
							label = "review";
							name = "review";
						}
					};
					onTimeInstanceCount = 1L;
					overdueInstanceCount = 0L;
				}
			});

		Long submitNodeId = RandomTestUtil.nextLong();

		_addNodeMetric(
			assignee2, () -> instance2, _process.getId(),
			new NodeMetric() {
				{
					durationAvg = 0L;
					instanceCount = 1L;
					node = new Node() {
						{
							id = submitNodeId;
							label = "submit";
							name = "submit";
						}
					};
					onTimeInstanceCount = 0L;
					overdueInstanceCount = 1L;
				}
			});

		Page<AssigneeMetric> page =
			assigneeMetricResource.postProcessAssigneeMetricsPage(
				_process.getId(), Pagination.of(1, 10), "taskCount:asc",
				new AssigneeMetricBulkSelection() {
					{
						completed = false;
						taskNames = new String[] {"update"};
					}
				});

		Assert.assertEquals(1, page.getTotalCount());

		assertEquals(
			Arrays.asList(
				new AssigneeMetric() {
					{
						assignee = assignee1;
						durationTaskAvg = 0L;
						onTimeTaskCount = 0L;
						overdueTaskCount = 2L;
						taskCount = 2L;
					}
				}),
			(List<AssigneeMetric>)page.getItems());

		page = assigneeMetricResource.postProcessAssigneeMetricsPage(
			_process.getId(), Pagination.of(1, 10), "overdueTaskCount:desc",
			new AssigneeMetricBulkSelection() {
				{
					completed = false;
					taskNames = new String[] {"review"};
				}
			});

		Assert.assertEquals(2, page.getTotalCount());

		assertEquals(
			Arrays.asList(
				new AssigneeMetric() {
					{
						assignee = assignee1;
						durationTaskAvg = 0L;
						onTimeTaskCount = 0L;
						overdueTaskCount = 1L;
						taskCount = 1L;
					}
				},
				new AssigneeMetric() {
					{
						assignee = assignee2;
						durationTaskAvg = 0L;
						onTimeTaskCount = 1L;
						overdueTaskCount = 0L;
						taskCount = 1L;
					}
				}),
			(List<AssigneeMetric>)page.getItems());

		page = assigneeMetricResource.postProcessAssigneeMetricsPage(
			_process.getId(), Pagination.of(1, 10), "overdueTaskCount:desc",
			new AssigneeMetricBulkSelection() {
				{
					completed = false;
					roleIds = new Long[] {siteAdministrationRole.getRoleId()};
					taskNames = new String[] {"review"};
				}
			});

		Assert.assertEquals(1, page.getTotalCount());

		assertEquals(
			Arrays.asList(
				new AssigneeMetric() {
					{
						assignee = assignee2;
						durationTaskAvg = 0L;
						onTimeTaskCount = 1L;
						overdueTaskCount = 0L;
						taskCount = 1L;
					}
				}),
			(List<AssigneeMetric>)page.getItems());

		page = assigneeMetricResource.postProcessAssigneeMetricsPage(
			_process.getId(), Pagination.of(1, 10), "overdueTaskCount:desc",
			new AssigneeMetricBulkSelection() {
				{
					completed = false;
					keywords = assignee2.getName();
					roleIds = new Long[] {siteAdministrationRole.getRoleId()};
					taskNames = new String[] {"review"};
				}
			});

		Assert.assertEquals(1, page.getTotalCount());

		assertEquals(
			Arrays.asList(
				new AssigneeMetric() {
					{
						assignee = assignee2;
						durationTaskAvg = 0L;
						onTimeTaskCount = 1L;
						overdueTaskCount = 0L;
						taskCount = 1L;
					}
				}),
			(List<AssigneeMetric>)page.getItems());

		page = assigneeMetricResource.postProcessAssigneeMetricsPage(
			_process.getId(), Pagination.of(1, 10), "overdueTaskCount:desc",
			new AssigneeMetricBulkSelection() {
				{
					completed = false;
					keywords = assignee1.getName();
					roleIds = new Long[] {siteAdministrationRole.getRoleId()};
					taskNames = new String[] {"review"};
				}
			});

		Assert.assertEquals(0, page.getTotalCount());

		AssigneeMetric assigneeMetric3 = randomAssigneeMetric();

		Assignee assignee3 = assigneeMetric3.getAssignee();

		assigneeMetric3.setOnTimeTaskCount(0L);
		assigneeMetric3.setOverdueTaskCount(0L);
		assigneeMetric3.setTaskCount(1L);

		_addNodeMetric(
			assignee3, () -> instance1, _process.getId(),
			new NodeMetric() {
				{
					durationAvg = 0L;
					instanceCount = 1L;
					node = new Node() {
						{
							id = reviewNodeId;
							label = "review";
							name = "review";
						}
					};
					onTimeInstanceCount = 0L;
					overdueInstanceCount = 0L;
				}
			});

		page = assigneeMetricResource.postProcessAssigneeMetricsPage(
			_process.getId(), Pagination.of(1, 10), "overdueTaskCount:desc",
			new AssigneeMetricBulkSelection() {
				{
					completed = false;
				}
			});

		Assert.assertEquals(3, page.getTotalCount());

		assertEquals(
			Arrays.asList(assigneeMetric1, assigneeMetric2, assigneeMetric3),
			(List<AssigneeMetric>)page.getItems());

		Instance instance3 = _workflowMetricsRESTTestHelper.addInstance(
			testGroup.getCompanyId(), true, _process.getId());

		_addNodeMetric(
			assignee1, () -> instance3, _process.getId(), "COMPLETED",
			new NodeMetric() {
				{
					durationAvg = 1000L;
					instanceCount = 1L;
					node = new Node() {
						{
							id = reviewNodeId;
							label = "review";
							name = "review";
						}
					};
					onTimeInstanceCount = 1L;
					overdueInstanceCount = 0L;
				}
			},
			new NodeMetric() {
				{
					durationAvg = 2000L;
					instanceCount = 1L;
					node = new Node() {
						{
							id = updateNodeId;
							label = "update";
							name = "update";
						}
					};
					onTimeInstanceCount = 1L;
					overdueInstanceCount = 0L;
				}
			});

		_addNodeMetric(
			assignee2, () -> instance3, _process.getId(), "COMPLETED",
			new NodeMetric() {
				{
					durationAvg = 2000L;
					instanceCount = 1L;
					node = new Node() {
						{
							id = reviewNodeId;
							label = "review";
							name = "review";
						}
					};
					onTimeInstanceCount = 1L;
					overdueInstanceCount = 0L;
				}
			},
			new NodeMetric() {
				{
					durationAvg = 4000L;
					instanceCount = 1L;
					node = new Node() {
						{
							id = updateNodeId;
							label = "update";
							name = "update";
						}
					};
					onTimeInstanceCount = 1L;
					overdueInstanceCount = 0L;
				}
			});

		page = assigneeMetricResource.postProcessAssigneeMetricsPage(
			_process.getId(), Pagination.of(1, 10), "durationTaskAvg:asc",
			new AssigneeMetricBulkSelection() {
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
				new AssigneeMetric() {
					{
						assignee = assignee1;
						durationTaskAvg = 1500L;
						onTimeTaskCount = 2L;
						overdueTaskCount = 0L;
						taskCount = 2L;
					}
				},
				new AssigneeMetric() {
					{
						assignee = assignee2;
						durationTaskAvg = 3000L;
						onTimeTaskCount = 2L;
						overdueTaskCount = 0L;
						taskCount = 2L;
					}
				}),
			(List<AssigneeMetric>)page.getItems());
	}

	@Override
	protected String[] getAdditionalAssertFieldNames() {
		return new String[] {
			"durationTaskAvg", "onTimeTaskCount", "overdueTaskCount",
			"taskCount"
		};
	}

	@Override
	protected String[] getIgnoredEntityFieldNames() {
		return new String[] {"durationTaskAvg"};
	}

	@Override
	protected AssigneeMetric randomAssigneeMetric() throws Exception {
		User user = UserTestUtil.addUser();

		return new AssigneeMetric() {
			{
				assignee = new Assignee() {
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
				durationTaskAvg = 0L;
				onTimeTaskCount = 1L;
				overdueTaskCount = 0L;
				taskCount = 1L;
			}
		};
	}

	private void _addNodeMetric(
			Assignee assignee,
			UnsafeSupplier<Instance, Exception> instanceSupplier,
			long processId, NodeMetric... nodeMetrics)
		throws Exception {

		_addNodeMetric(
			assignee, instanceSupplier, processId, "RUNNING", nodeMetrics);
	}

	private void _addNodeMetric(
			Assignee assignee,
			UnsafeSupplier<Instance, Exception> instanceSupplier,
			long processId, String status, NodeMetric... nodeMetrics)
		throws Exception {

		for (NodeMetric nodeMetric : nodeMetrics) {
			_workflowMetricsRESTTestHelper.addNodeMetric(
				assignee, testGroup.getCompanyId(), instanceSupplier, processId,
				status, nodeMetric, "1.0");
		}
	}

	private void _addRoleUser(Role role, long userId) throws Exception {
		_userLocalService.addRoleUser(role.getRoleId(), userId);

		_userGroupRoleLocalService.addUserGroupRoles(
			new long[] {userId}, TestPropsValues.getGroupId(),
			role.getRoleId());
	}

	private void _deleteSLATaskResults() throws Exception {
		_workflowMetricsRESTTestHelper.deleteSLATaskResults(
			testGroup.getCompanyId(), _process.getId());
	}

	private void _deleteTasks() throws Exception {
		_workflowMetricsRESTTestHelper.deleteTasks(
			testGroup.getCompanyId(), _process.getId());
	}

	private AssigneeMetric _randomAssigneeMetric(Role role) throws Exception {
		AssigneeMetric assigneeMetric = randomAssigneeMetric();

		Assignee assignee = assigneeMetric.getAssignee();

		_addRoleUser(role, assignee.getId());

		return assigneeMetric;
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