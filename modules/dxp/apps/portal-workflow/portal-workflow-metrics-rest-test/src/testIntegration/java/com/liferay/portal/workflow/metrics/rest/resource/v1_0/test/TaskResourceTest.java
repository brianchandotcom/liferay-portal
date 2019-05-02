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
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.search.engine.adapter.SearchEngineAdapter;
import com.liferay.portal.search.query.Queries;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.workflow.metrics.rest.client.dto.v1_0.Process;
import com.liferay.portal.workflow.metrics.rest.client.dto.v1_0.Task;
import com.liferay.portal.workflow.metrics.rest.resource.v1_0.test.helper.WorkflowMetricsRESTTestHelper;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
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
	public void setUp() throws Exception {
		super.setUp();

		_singleApproverDocument =
			_workflowMetricsRESTTestHelper.getSingleApproverDocument(
				testGroup.getCompanyId());

		_workflowMetricsRESTTestHelper.deleteProcess(_singleApproverDocument);
	}

	@After
	@Override
	public void tearDown() throws Exception {
		super.tearDown();

		_workflowMetricsRESTTestHelper.addProcess(_singleApproverDocument);

		if (_process == null) {
			return;
		}

		_workflowMetricsRESTTestHelper.deleteProcess(
			testGroup.getCompanyId(), _process.getId());

		for (Task task : _tasks) {
			_workflowMetricsRESTTestHelper.deleteTask(
				testGroup.getCompanyId(), _process.getId(), task.getName());
		}

		_tasks = new ArrayList<>();
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
		_process = _workflowMetricsRESTTestHelper.addProcess(
			testGroup.getCompanyId());

		return _process.getId();
	}

	@Inject
	private static Queries _queries;

	@Inject
	private static SearchEngineAdapter _searchEngineAdapter;

	private static Document _singleApproverDocument;
	private static WorkflowMetricsRESTTestHelper _workflowMetricsRESTTestHelper;

	private Process _process;
	private List<Task> _tasks = new ArrayList<>();

}