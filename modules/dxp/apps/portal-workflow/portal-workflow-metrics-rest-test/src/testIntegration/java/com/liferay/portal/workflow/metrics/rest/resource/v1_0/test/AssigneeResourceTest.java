/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.workflow.metrics.rest.resource.v1_0.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.test.rule.DataGuard;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.search.test.util.SearchTestRule;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.workflow.metrics.rest.client.dto.v1_0.Assignee;
import com.liferay.portal.workflow.metrics.rest.client.dto.v1_0.AssigneeBulkSelection;
import com.liferay.portal.workflow.metrics.rest.client.dto.v1_0.Instance;
import com.liferay.portal.workflow.metrics.rest.client.dto.v1_0.Process;
import com.liferay.portal.workflow.metrics.rest.client.pagination.Page;
import com.liferay.portal.workflow.metrics.rest.resource.v1_0.test.helper.WorkflowMetricsRESTTestHelper;

import java.util.Arrays;
import java.util.List;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Rafael Praxedes
 */
@DataGuard(scope = DataGuard.Scope.METHOD)
@RunWith(Arquillian.class)
public class AssigneeResourceTest extends BaseAssigneeResourceTestCase {

	@Before
	@Override
	public void setUp() throws Exception {
		super.setUp();

		_process = _workflowMetricsRESTTestHelper.addProcess(
			testGroup.getCompanyId());

		_instance = _workflowMetricsRESTTestHelper.addInstance(
			testGroup.getCompanyId(), false, _process.getId());
	}

	@After
	@Override
	public void tearDown() throws Exception {
		super.tearDown();

		if (_process != null) {
			_workflowMetricsRESTTestHelper.deleteProcess(
				testGroup.getCompanyId(), _process);
		}

		if (_instance != null) {
			_workflowMetricsRESTTestHelper.deleteInstance(
				testGroup.getCompanyId(), _instance);
		}
	}

	@Override
	@Test
	public void testPostProcessAssigneesPage() throws Exception {
		Assignee assignee1 = randomAssignee();

		_workflowMetricsRESTTestHelper.addTask(
			assignee1, testGroup.getCompanyId(), _instance,
			TestPropsValues.getUser());

		Assignee assignee2 = randomAssignee();

		_workflowMetricsRESTTestHelper.addTask(
			assignee2, testGroup.getCompanyId(), _instance,
			TestPropsValues.getUser());

		Page<Assignee> page = assigneeResource.postProcessAssigneesPage(
			_process.getId(), new AssigneeBulkSelection());

		Assert.assertEquals(2, page.getTotalCount());

		assertEqualsIgnoringOrder(
			Arrays.asList(assignee1, assignee2),
			(List<Assignee>)page.getItems());
		assertValid(page);

		page = assigneeResource.postProcessAssigneesPage(
			_process.getId(),
			new AssigneeBulkSelection() {
				{
					instanceIds = new Long[] {_instance.getId()};
				}
			});

		Assert.assertEquals(2, page.getTotalCount());

		assertEqualsIgnoringOrder(
			Arrays.asList(assignee1, assignee2),
			(List<Assignee>)page.getItems());
		assertValid(page);

		page = assigneeResource.postProcessAssigneesPage(
			_process.getId(),
			new AssigneeBulkSelection() {
				{
					instanceIds = new Long[] {0L};
				}
			});

		Assert.assertEquals(0, page.getTotalCount());
	}

	@Rule
	public SearchTestRule searchTestRule = new SearchTestRule();

	@Override
	protected Assignee randomAssignee() throws Exception {
		User user = UserTestUtil.addUser();

		return new Assignee() {
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
	}

	private Instance _instance;

	@Inject
	private Portal _portal;

	private Process _process;

	@Inject
	private WorkflowMetricsRESTTestHelper _workflowMetricsRESTTestHelper;

}