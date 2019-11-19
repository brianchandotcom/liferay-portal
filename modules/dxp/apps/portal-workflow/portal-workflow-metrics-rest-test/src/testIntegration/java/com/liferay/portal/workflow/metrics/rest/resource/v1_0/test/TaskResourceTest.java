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
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.odata.entity.EntityField;
import com.liferay.portal.search.document.DocumentBuilderFactory;
import com.liferay.portal.search.engine.adapter.SearchEngineAdapter;
import com.liferay.portal.search.query.Queries;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.workflow.metrics.rest.client.dto.v1_0.Process;
import com.liferay.portal.workflow.metrics.rest.client.dto.v1_0.Task;
import com.liferay.portal.workflow.metrics.rest.client.pagination.Page;
import com.liferay.portal.workflow.metrics.rest.client.pagination.Pagination;
import com.liferay.portal.workflow.metrics.rest.resource.v1_0.test.helper.WorkflowMetricsRESTTestHelper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

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
public class TaskResourceTest extends BaseTaskResourceTestCase {

	@BeforeClass
	public static void setUpClass() throws Exception {
		BaseTaskResourceTestCase.setUpClass();

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

		_deleteTasks();
	}

	@Override
	@Test
	public void testGetProcessTasksPage() throws Exception {
		super.testGetProcessTasksPage();

		_deleteTasks();

		_workflowMetricsRESTTestHelper.updateProcess(
			testGroup.getCompanyId(), _process.getId(), "2.0");

		Task task1 = randomTask();

		task1.setBreachedInstanceCount(0L);
		task1.setDurationAvg(1000L);
		task1.setInstanceCount(1L);
		task1.setOnTimeInstanceCount(0L);
		task1.setOverdueInstanceCount(0L);

		testGetProcessTasksPage_addTask(
			_process.getId(), "COMPLETED", task1, "2.0");

		Task task2 = randomTask();

		task2.setBreachedInstanceCount(0L);
		task2.setDurationAvg(2000L);
		task2.setInstanceCount(1L);
		task2.setOnTimeInstanceCount(0L);
		task2.setOverdueInstanceCount(0L);

		testGetProcessTasksPage_addTask(
			_process.getId(), "COMPLETED", task2, "2.0");

		Page<Task> page = taskResource.getProcessTasksPage(
			_process.getId(), true, null, null, null, Pagination.of(1, 2),
			"durationAvg:asc");

		assertEquals(
			Arrays.asList(
				new Task() {
					{
						breachedInstanceCount =
							task1.getBreachedInstanceCount();
						breachedInstancePercentage =
							task1.getBreachedInstancePercentage();
						durationAvg = task1.getDurationAvg();
						instanceCount = task1.getInstanceCount();
						key = task1.getKey();
						name = task1.getName();
					}
				},
				new Task() {
					{
						breachedInstanceCount =
							task2.getBreachedInstanceCount();
						breachedInstancePercentage =
							task2.getBreachedInstancePercentage();
						durationAvg = task2.getDurationAvg();
						instanceCount = task2.getInstanceCount();
						key = task2.getKey();
						name = task2.getName();
					}
				}),
			(List<Task>)page.getItems());

		page = taskResource.getProcessTasksPage(
			_process.getId(), true, null, null, null, Pagination.of(1, 2),
			"overdueInstanceCount:asc");

		assertEqualsIgnoringOrder(
			Arrays.asList(
				new Task() {
					{
						breachedInstanceCount =
							task1.getBreachedInstanceCount();
						breachedInstancePercentage =
							task1.getBreachedInstancePercentage();
						durationAvg = task1.getDurationAvg();
						instanceCount = task1.getInstanceCount();
						key = task1.getKey();
						name = task1.getName();
					}
				},
				new Task() {
					{
						breachedInstanceCount =
							task2.getBreachedInstanceCount();
						breachedInstancePercentage =
							task2.getBreachedInstancePercentage();
						durationAvg = task2.getDurationAvg();
						instanceCount = task2.getInstanceCount();
						key = task2.getKey();
						name = task2.getName();
					}
				}),
			(List<Task>)page.getItems());

		page = taskResource.getProcessTasksPage(
			_process.getId(), true, null, null, task1.getKey(),
			Pagination.of(1, 2), null);

		assertEquals(
			Arrays.asList(
				new Task() {
					{
						breachedInstanceCount =
							task1.getBreachedInstanceCount();
						breachedInstancePercentage =
							task1.getBreachedInstancePercentage();
						durationAvg = task1.getDurationAvg();
						instanceCount = task1.getInstanceCount();
						key = task1.getKey();
						name = task1.getName();
					}
				}),
			(List<Task>)page.getItems());

		page = taskResource.getProcessTasksPage(
			_process.getId(), true, RandomTestUtil.nextDate(),
			DateUtils.addMinutes(RandomTestUtil.nextDate(), -2), null,
			Pagination.of(1, 2), "durationAvg:desc");

		assertEquals(
			Arrays.asList(
				new Task() {
					{
						breachedInstanceCount =
							task2.getBreachedInstanceCount();
						breachedInstancePercentage =
							task2.getBreachedInstancePercentage();
						durationAvg = task2.getDurationAvg();
						instanceCount = task2.getInstanceCount();
						key = task2.getKey();
						name = task2.getName();
					}
				},
				new Task() {
					{
						breachedInstanceCount =
							task1.getBreachedInstanceCount();
						breachedInstancePercentage =
							task1.getBreachedInstancePercentage();
						durationAvg = task1.getDurationAvg();
						instanceCount = task1.getInstanceCount();
						key = task1.getKey();
						name = task1.getName();
					}
				}),
			(List<Task>)page.getItems());

		page = taskResource.getProcessTasksPage(
			_process.getId(), true,
			DateUtils.addHours(RandomTestUtil.nextDate(), -1),
			DateUtils.addHours(RandomTestUtil.nextDate(), -2), null,
			Pagination.of(1, 2), "durationAvg:desc");

		Assert.assertEquals(2, page.getTotalCount());

		assertEqualsIgnoringOrder(
			Arrays.asList(
				new Task() {
					{
						breachedInstanceCount = 0L;
						durationAvg = 0L;
						instanceCount = 0L;
						key = task1.getKey();
						name = task1.getName();
					}
				},
				new Task() {
					{
						breachedInstanceCount = 0L;
						durationAvg = 0L;
						instanceCount = 0L;
						key = task2.getKey();
						name = task2.getName();
					}
				}),
			(List<Task>)page.getItems());

		page = taskResource.getProcessTasksPage(
			_process.getId(), true, null, null, null, Pagination.of(0, 0),
			null);

		assertEqualsIgnoringOrder(
			Arrays.asList(
				new Task() {
					{
						key = task1.getKey();
						name = task1.getName();
					}
				},
				new Task() {
					{
						key = task2.getKey();
						name = task2.getName();
					}
				}),
			(List<Task>)page.getItems());

		Task task3 = randomTask();

		task3.setBreachedInstanceCount(2L);
		task3.setBreachedInstancePercentage(100.0);
		task3.setDurationAvg(3000L);
		task3.setInstanceCount(2L);
		task3.setOnTimeInstanceCount(0L);
		task3.setOverdueInstanceCount(2L);

		testGetProcessTasksPage_addTask(
			_process.getId(), "COMPLETED", task3, "2.0");

		Task task4 = randomTask();

		task4.setBreachedInstanceCount(1L);
		task4.setBreachedInstancePercentage(50.0);
		task4.setDurationAvg(4000L);
		task4.setInstanceCount(2L);
		task4.setOnTimeInstanceCount(1L);
		task4.setOverdueInstanceCount(1L);

		testGetProcessTasksPage_addTask(
			_process.getId(), "COMPLETED", task4, "2.0");

		page = taskResource.getProcessTasksPage(
			_process.getId(), true, null, null, null, Pagination.of(1, 2),
			"breachedInstancePercentage:desc");

		assertEquals(
			Arrays.asList(
				new Task() {
					{
						breachedInstanceCount =
							task3.getBreachedInstanceCount();
						breachedInstancePercentage =
							task3.getBreachedInstancePercentage();
						durationAvg = task3.getDurationAvg();
						instanceCount = task3.getInstanceCount();
						key = task3.getKey();
						name = task3.getName();
					}
				},
				new Task() {
					{
						breachedInstanceCount =
							task4.getBreachedInstanceCount();
						breachedInstancePercentage =
							task4.getBreachedInstancePercentage();
						durationAvg = task4.getDurationAvg();
						instanceCount = task4.getInstanceCount();
						key = task4.getKey();
						name = task4.getName();
					}
				}),
			(List<Task>)page.getItems());
	}

	@Override
	@Test
	public void testGetProcessTasksPageWithSortInteger() throws Exception {
		testGetProcessTasksPageWithSort(
			EntityField.Type.INTEGER,
			(entityField, task1, task2) -> {
				task1.setInstanceCount(0L);
				task1.setOnTimeInstanceCount(0L);
				task1.setOverdueInstanceCount(0L);

				task2.setInstanceCount(3L);
				task2.setOnTimeInstanceCount(1L);
				task2.setOverdueInstanceCount(1L);
			});
	}

	@Override
	protected String[] getAdditionalAssertFieldNames() {
		return new String[] {
			"durationAvg", "instanceCount", "key", "name",
			"onTimeInstanceCount", "overdueInstanceCount"
		};
	}

	@Override
	protected String[] getIgnoredEntityFieldNames() {
		return new String[] {"durationAvg"};
	}

	@Override
	protected Task randomTask() throws Exception {
		Task task = super.randomTask();

		task.setBreachedInstancePercentage(0.0);
		task.setDurationAvg(0L);

		int instanceCount = RandomTestUtil.randomInt(0, 20);

		task.setInstanceCount((long)instanceCount);

		task.setName(task.getKey());

		int onTimeInstanceCount = RandomTestUtil.randomInt(0, instanceCount);

		task.setOnTimeInstanceCount((long)onTimeInstanceCount);

		task.setOverdueInstanceCount(
			(long)RandomTestUtil.randomInt(
				0, instanceCount - onTimeInstanceCount));

		return task;
	}

	protected Task testGetProcessTasksPage_addTask(
			Long processId, String status, Task task, String version)
		throws Exception {

		User adminUser = UserTestUtil.getAdminUser(testGroup.getCompanyId());

		task = _workflowMetricsRESTTestHelper.addTask(
			adminUser.getUserId(), testGroup.getCompanyId(),
			() -> _workflowMetricsRESTTestHelper.addInstance(
				testGroup.getCompanyId(), Objects.equals(status, "COMPLETED"),
				processId),
			processId, status, task, version);

		_tasks.add(task);

		return task;
	}

	@Override
	protected Task testGetProcessTasksPage_addTask(Long processId, Task task)
		throws Exception {

		return testGetProcessTasksPage_addTask(processId, task, "1.0");
	}

	protected Task testGetProcessTasksPage_addTask(
			Long processId, Task task, String version)
		throws Exception {

		return testGetProcessTasksPage_addTask(
			processId, "RUNNING", task, version);
	}

	@Override
	protected Long testGetProcessTasksPage_getProcessId() throws Exception {
		return _process.getId();
	}

	private void _deleteTasks() throws Exception {
		for (Task task : _tasks) {
			_workflowMetricsRESTTestHelper.deleteTask(
				testGroup.getCompanyId(), _process.getId(), task);
		}
	}

	@Inject
	private static DocumentBuilderFactory _documentBuilderFactory;

	@Inject
	private static Queries _queries;

	@Inject(blocking = false, filter = "search.engine.impl=Elasticsearch")
	private static SearchEngineAdapter _searchEngineAdapter;

	private static WorkflowMetricsRESTTestHelper _workflowMetricsRESTTestHelper;

	private Process _process;
	private final List<Task> _tasks = new ArrayList<>();

}