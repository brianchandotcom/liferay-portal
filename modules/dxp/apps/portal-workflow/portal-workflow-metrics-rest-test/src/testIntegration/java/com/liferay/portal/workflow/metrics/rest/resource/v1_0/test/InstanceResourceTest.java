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
import com.liferay.portal.workflow.metrics.rest.client.dto.v1_0.Instance;
import com.liferay.portal.workflow.metrics.rest.client.dto.v1_0.Process;
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
public class InstanceResourceTest extends BaseInstanceResourceTestCase {

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

		_singleApproverDocument =
			_workflowMetricsRESTTestHelper.getSingleApproverDocument(
				testGroup.getCompanyId());

		_workflowMetricsRESTTestHelper.deleteProcess(_singleApproverDocument);
	}

	@After
	@Override
	public void tearDown() throws Exception {
		super.tearDown();

		_workflowMetricsRESTTestHelper.restoreProcess(_singleApproverDocument);

		if (_process == null) {
			return;
		}

		_workflowMetricsRESTTestHelper.deleteProcess(
			testGroup.getCompanyId(), _process.getId());

		for (Instance instance : _instances) {
			_workflowMetricsRESTTestHelper.deleteInstance(
				testGroup.getCompanyId(), instance.getId(), _process.getId());
		}

		_instances = new ArrayList<>();
	}

	@Override
	protected Instance testGetProcessInstance_addInstance() throws Exception {
		Instance instance = randomInstance();

		_process = _workflowMetricsRESTTestHelper.addProcess(
			testGroup.getCompanyId());

		instance.setProcessId(_process.getId());

		instance = _workflowMetricsRESTTestHelper.addInstance(
			testGroup.getCompanyId(), instance);

		_instances.add(instance);

		return instance;
	}

	@Override
	protected Instance testGetProcessInstancesPage_addInstance(
			Long processId, Instance instance)
		throws Exception {

		instance.setProcessId(processId);

		instance = _workflowMetricsRESTTestHelper.addInstance(
			testGroup.getCompanyId(), instance);

		_instances.add(instance);

		return instance;
	}

	@Override
	protected Long testGetProcessInstancesPage_getProcessId() throws Exception {
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

	private List<Instance> _instances = new ArrayList<>();
	private Process _process;

}