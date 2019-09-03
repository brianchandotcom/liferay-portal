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
import com.liferay.petra.function.UnsafeTriConsumer;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.odata.entity.EntityField;
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

import org.junit.After;
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

		_deleteTasks();
	}

	@Override
	@Test
	public void testGetProcessTasksPage() throws Exception {
		super.testGetProcessTasksPage();

		_testGetProcessTasksPage(
			true,
			(task1, task2, page) -> assertEqualsIgnoringOrder(
				Arrays.asList(task1, task2), (List<Task>)page.getItems()));
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
			"durationAvg", "instanceCount", "key", "onTimeInstanceCount",
			"overdueInstanceCount"
		};
	}

	@Override
	protected String[] getIgnoredEntityFieldNames() {
		return new String[] {"durationAvg"};
	}

	@Override
	protected Task randomTask() throws Exception {
		Task task = super.randomTask();

		task.setDurationAvg(0L);

		int instanceCount = RandomTestUtil.randomInt(0, 20);

		task.setInstanceCount((long)instanceCount);

		int onTimeInstanceCount = RandomTestUtil.randomInt(0, instanceCount);

		task.setOnTimeInstanceCount((long)onTimeInstanceCount);

		task.setOverdueInstanceCount(
			(long)RandomTestUtil.randomInt(
				0, instanceCount - onTimeInstanceCount));

		return task;
	}

	@Override
	protected Task testGetProcessTasksPage_addTask(Long processId, Task task)
		throws Exception {

		task = _workflowMetricsRESTTestHelper.addTask(
			testGroup.getCompanyId(), processId, task);

		_tasks.add(task);

		return task;
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

	private void _testGetProcessTasksPage(
			boolean completed,
			UnsafeTriConsumer<Task, Task, Page<Task>, Exception>
				unsafeTriConsumer)
		throws Exception {

		_deleteTasks();

		Long processId = testGetProcessTasksPage_getProcessId();

		Task task1 = randomTask();

		if (completed) {
			task1.setDurationAvg(1000L);
			task1.setOnTimeInstanceCount(0L);
			task1.setOverdueInstanceCount(0L);
		}

		testGetProcessTasksPage_addTask(processId, task1);

		Task task2 = randomTask();

		if (completed) {
			task2.setDurationAvg(2000L);
			task2.setOnTimeInstanceCount(0L);
			task2.setOverdueInstanceCount(0L);
		}

		testGetProcessTasksPage_addTask(processId, task2);

		Page<Task> page =
			page = taskResource.getProcessTasksPage(
				processId, completed, null, null, Pagination.of(1, 2), null);

		unsafeTriConsumer.accept(task1, task2, page);
	}

	@Inject
	private static Queries _queries;

	@Inject(blocking = false, filter = "search.engine.impl=Elasticsearch")
	private static SearchEngineAdapter _searchEngineAdapter;

	private static WorkflowMetricsRESTTestHelper _workflowMetricsRESTTestHelper;

	private Process _process;
	private final List<Task> _tasks = new ArrayList<>();

}