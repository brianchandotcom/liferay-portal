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
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.search.engine.adapter.SearchEngineAdapter;
import com.liferay.portal.search.query.Queries;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.workflow.metrics.rest.client.dto.v1_0.AssigneeUser;
import com.liferay.portal.workflow.metrics.rest.client.dto.v1_0.CreatorUser;
import com.liferay.portal.workflow.metrics.rest.client.dto.v1_0.Instance;
import com.liferay.portal.workflow.metrics.rest.client.dto.v1_0.Process;
import com.liferay.portal.workflow.metrics.rest.client.pagination.Page;
import com.liferay.portal.workflow.metrics.rest.client.pagination.Pagination;
import com.liferay.portal.workflow.metrics.rest.resource.v1_0.test.helper.WorkflowMetricsRESTTestHelper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Rafael Praxedes
 */
@RunWith(Arquillian.class)
public class InstanceResourceTest extends BaseInstanceResourceTestCase {

	@BeforeClass
	public static void setUpClass() throws Exception {
		BaseInstanceResourceTestCase.setUpClass();

		_workflowMetricsRESTTestHelper = new WorkflowMetricsRESTTestHelper(
			_queries, _searchEngineAdapter);
	}

	@Before
	@Override
	public void setUp() throws Exception {
		super.setUp();

		_process = _workflowMetricsRESTTestHelper.addProcess(
			testGroup.getCompanyId());

		_user = UserTestUtil.addUser();
	}

	@After
	@Override
	public void tearDown() throws Exception {
		super.tearDown();

		if (_process != null) {
			_workflowMetricsRESTTestHelper.deleteProcess(
				testGroup.getCompanyId(), _process);
		}

		_deleteInstances();
	}

	@Override
	@Test
	public void testGetProcessInstancesPage() throws Exception {
		super.testGetProcessInstancesPage();

		_testGetProcessInstancesPage(
			new Long[] {_user.getUserId()}, null,
			(instance1, instance2, page) -> assertEquals(
				Collections.singletonList(instance2),
				(List<Instance>)page.getItems()));
		_testGetProcessInstancesPage(
			null, new String[] {"Completed"},
			(instance1, instance2, page) -> assertEquals(
				Collections.singletonList(instance1),
				(List<Instance>)page.getItems()));
		_testGetProcessInstancesPage(
			null, new String[] {"Completed", "Pending"},
			(instance1, instance2, page) -> assertEqualsIgnoringOrder(
				Arrays.asList(instance1, instance2),
				(List<Instance>)page.getItems()));
		_testGetProcessInstancesPage(
			null, new String[] {"Pending"},
			(instance1, instance2, page) -> assertEquals(
				Collections.singletonList(instance2),
				(List<Instance>)page.getItems()));
	}

	@Ignore
	@Override
	@Test
	public void testGraphQLGetProcessInstance() throws Exception {
	}

	@Override
	protected String[] getAdditionalAssertFieldNames() {
		return new String[] {"assetTitle", "assetType", "processId"};
	}

	@Override
	protected Instance randomInstance() throws Exception {
		Instance instance = super.randomInstance();

		instance.setAssigneeUsers(new AssigneeUser[0]);

		User adminUser = UserTestUtil.getAdminUser(testGroup.getCompanyId());

		instance.setCreatorUser(
			new CreatorUser() {
				{
					id = adminUser.getUserId();
					name = adminUser.getFullName();
				}
			});

		instance.setDateCompletion((Date)null);

		return instance;
	}

	@Override
	protected Instance testGetProcessInstance_addInstance() throws Exception {
		return testGetProcessInstancesPage_addInstance(
			_process.getId(), randomInstance());
	}

	@Override
	protected Instance testGetProcessInstancesPage_addInstance(
			Long processId, Instance instance)
		throws Exception {

		instance.setProcessId(processId);

		instance = _workflowMetricsRESTTestHelper.addInstance(
			testGroup.getCompanyId(), instance);

		for (AssigneeUser assigneeUser : instance.getAssigneeUsers()) {
			_workflowMetricsRESTTestHelper.addToken(
				assigneeUser.getId(), testGroup.getCompanyId(), instance);
		}

		_instances.add(instance);

		return instance;
	}

	@Override
	protected Long testGetProcessInstancesPage_getProcessId() throws Exception {
		return _process.getId();
	}

	@Override
	protected Instance testGraphQLInstance_addInstance() throws Exception {
		return testGetProcessInstance_addInstance();
	}

	private void _deleteInstances() throws Exception {
		for (Instance instance : _instances) {
			_workflowMetricsRESTTestHelper.deleteInstance(
				testGroup.getCompanyId(), instance);
		}
	}

	private void _testGetProcessInstancesPage(
			Long[] assigneeUserIds, String[] statuses,
			UnsafeTriConsumer<Instance, Instance, Page<Instance>, Exception>
				unsafeTriConsumer)
		throws Exception {

		_deleteInstances();

		Instance instance1 = randomInstance();

		instance1.setDateCompletion(RandomTestUtil.nextDate());

		testGetProcessInstancesPage_addInstance(_process.getId(), instance1);

		Instance instance2 = randomInstance();

		instance2.setAssigneeUsers(
			new AssigneeUser[] {
				new AssigneeUser() {
					{
						id = _user.getUserId();
					}
				}
			});

		testGetProcessInstancesPage_addInstance(_process.getId(), instance2);

		Page<Instance> page = instanceResource.getProcessInstancesPage(
			_process.getId(), assigneeUserIds, null, null, null, statuses, null,
			Pagination.of(1, 2));

		unsafeTriConsumer.accept(instance1, instance2, page);
	}

	@Inject
	private static Queries _queries;

	@Inject(blocking = false, filter = "search.engine.impl=Elasticsearch")
	private static SearchEngineAdapter _searchEngineAdapter;

	private static WorkflowMetricsRESTTestHelper _workflowMetricsRESTTestHelper;

	private final List<Instance> _instances = new ArrayList<>();
	private Process _process;

	@DeleteAfterTestRun
	private User _user;

}