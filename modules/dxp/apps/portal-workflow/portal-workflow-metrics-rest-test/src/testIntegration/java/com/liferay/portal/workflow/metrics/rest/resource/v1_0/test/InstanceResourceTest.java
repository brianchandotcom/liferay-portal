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
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.test.rule.DataGuard;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.search.test.util.SearchTestRule;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.workflow.metrics.rest.client.dto.v1_0.Assignee;
import com.liferay.portal.workflow.metrics.rest.client.dto.v1_0.Creator;
import com.liferay.portal.workflow.metrics.rest.client.dto.v1_0.Instance;
import com.liferay.portal.workflow.metrics.rest.client.dto.v1_0.Process;
import com.liferay.portal.workflow.metrics.rest.client.dto.v1_0.SLAResult;
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
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Rafael Praxedes
 */
@DataGuard(scope = DataGuard.Scope.METHOD)
@RunWith(Arquillian.class)
public class InstanceResourceTest extends BaseInstanceResourceTestCase {

	@Before
	@Override
	public void setUp() throws Exception {
		super.setUp();

		_classPK = RandomTestUtil.nextLong();
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
			new Long[] {_user.getUserId()}, null, null,
			(instance1, instance2, page) -> assertEquals(
				Collections.singletonList(instance2),
				(List<Instance>)page.getItems()));
		_testGetProcessInstancesPage(
			null, new Long[] {_classPK}, null,
			(instance1, instance2, page) -> assertEquals(
				Collections.singletonList(instance1),
				(List<Instance>)page.getItems()));
		_testGetProcessInstancesPage(
			null, null, true,
			(instance1, instance2, page) -> assertEquals(
				Collections.singletonList(instance1),
				(List<Instance>)page.getItems()));
		_testGetProcessInstancesPage(
			null, null, null,
			(instance1, instance2, page) -> assertEqualsIgnoringOrder(
				Arrays.asList(instance1, instance2),
				(List<Instance>)page.getItems()));
		_testGetProcessInstancesPage(
			null, null, false,
			(instance1, instance2, page) -> assertEquals(
				Collections.singletonList(instance2),
				(List<Instance>)page.getItems()));
	}

	@Rule
	public SearchTestRule searchTestRule = new SearchTestRule();

	@Override
	protected String[] getAdditionalAssertFieldNames() {
		return new String[] {
			"assetTitle", "assetType", "classPK", "processId", "slaResults"
		};
	}

	@Override
	protected Instance randomInstance() throws Exception {
		Instance instance = super.randomInstance();

		instance.setAssetTitle_i18n(
			HashMapBuilder.put(
				LocaleUtil.US.toLanguageTag(), instance.getAssetTitle()
			).build());
		instance.setAssetType_i18n(
			HashMapBuilder.put(
				LocaleUtil.US.toLanguageTag(), instance.getAssetType()
			).build());

		instance.setAssignees(new Assignee[0]);

		User adminUser = UserTestUtil.getAdminUser(testGroup.getCompanyId());

		instance.setCreator(
			new Creator() {
				{
					id = adminUser.getUserId();
					name = adminUser.getFullName();
				}
			});

		instance.setCompleted(false);
		instance.setDateCompletion((Date)null);
		instance.setProcessId(_process.getId());
		instance.setProcessVersion(_process.getVersion());
		instance.setSlaResults(
			new SLAResult[] {
				_toSLAResult(true, SLAResult.Status.RUNNING),
				_toSLAResult(false, SLAResult.Status.RUNNING)
			});

		return instance;
	}

	@Override
	protected Instance testDeleteProcessInstance_addInstance()
		throws Exception {

		return testGetProcessInstance_addInstance();
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

		for (Assignee assignee : instance.getAssignees()) {
			_workflowMetricsRESTTestHelper.addTask(
				assignee, testGroup.getCompanyId(), instance,
				TestPropsValues.getUser());
		}

		if (instance.getCompleted()) {
			_workflowMetricsRESTTestHelper.completeInstance(
				testGroup.getCompanyId(), instance);
		}

		_workflowMetricsRESTTestHelper.addSLAInstanceResults(
			testGroup.getCompanyId(), instance, instance.getSlaResults());

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

	@Override
	protected Instance testPatchProcessInstance_addInstance() throws Exception {
		return testGetProcessInstance_addInstance();
	}

	@Override
	protected Instance testPatchProcessInstanceComplete_addInstance()
		throws Exception {

		Instance instance = testGetProcessInstance_addInstance();

		instance.setCompleted(true);
		instance.setDateCompletion(RandomTestUtil.nextDate());

		return instance;
	}

	protected Instance testPostProcessInstance_addInstance(Instance instance)
		throws Exception {

		return testGetProcessInstancesPage_addInstance(
			_process.getId(), instance);
	}

	private void _deleteInstances() throws Exception {
		for (Instance instance : _instances) {
			_workflowMetricsRESTTestHelper.deleteInstance(
				testGroup.getCompanyId(), instance);
		}

		_instances.clear();
	}

	private void _testGetProcessInstancesPage(
			Long[] assigneeIds, Long[] classPKs, Boolean completed,
			UnsafeTriConsumer<Instance, Instance, Page<Instance>, Exception>
				unsafeTriConsumer)
		throws Exception {

		_deleteInstances();

		Instance instance1 = randomInstance();

		_workflowMetricsRESTTestHelper.addSLAInstanceResults(
			testGroup.getCompanyId(), instance1,
			_toSLAResult(true, SLAResult.Status.STOPPED),
			_toSLAResult(true, SLAResult.Status.PAUSED));

		instance1.setClassPK(_classPK);
		instance1.setCompleted(true);
		instance1.setDateCompletion(RandomTestUtil.nextDate());

		testGetProcessInstancesPage_addInstance(_process.getId(), instance1);

		Instance instance2 = randomInstance();

		instance2.setAssignees(
			new Assignee[] {
				new Assignee() {
					{
						id = _user.getUserId();
					}
				}
			});

		testGetProcessInstancesPage_addInstance(_process.getId(), instance2);

		Page<Instance> page = instanceResource.getProcessInstancesPage(
			_process.getId(), assigneeIds, classPKs, completed, null, null,
			null, null, Pagination.of(1, 2));

		unsafeTriConsumer.accept(instance1, instance2, page);
	}

	private SLAResult _toSLAResult(
		boolean overdue, SLAResult.Status slaResultStatus) {

		return new SLAResult() {
			{
				dateOverdue = null;
				id = RandomTestUtil.randomLong();
				name = StringPool.BLANK;
				onTime = !overdue;
				remainingTime = overdue ? -1L : 1L;
				status = slaResultStatus;
			}
		};
	}

	private Long _classPK;
	private final List<Instance> _instances = new ArrayList<>();
	private Process _process;
	private User _user;

	@Inject
	private WorkflowMetricsRESTTestHelper _workflowMetricsRESTTestHelper;

}