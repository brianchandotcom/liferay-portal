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
import com.liferay.portal.kernel.model.role.RoleConstants;
import com.liferay.portal.kernel.service.RoleLocalService;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.RoleTestUtil;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.workflow.metrics.rest.client.dto.v1_0.Assignee;
import com.liferay.portal.workflow.metrics.rest.client.dto.v1_0.Process;
import com.liferay.portal.workflow.metrics.rest.client.dto.v1_0.Role;
import com.liferay.portal.workflow.metrics.rest.client.pagination.Page;
import com.liferay.portal.workflow.metrics.rest.resource.v1_0.test.helper.WorkflowMetricsRESTTestHelper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import org.junit.Assert;
import org.junit.Before;
import org.junit.runner.RunWith;

/**
 * @author Rafael Praxedes
 */
@RunWith(Arquillian.class)
public class RoleResourceTest extends BaseRoleResourceTestCase {

	@Before
	@Override
	public void setUp() throws Exception {
		super.setUp();

		_process = _workflowMetricsRESTTestHelper.addProcess(
			testGroup.getCompanyId());
	}

	@Override
	public void testGetProcessRolesPage() throws Exception {
		super.testGetProcessRolesPage();

		Role role1 = testGetProcessRolesPage_addRole(
			_process.getId(), randomRole(), "COMPLETED");

		Role role2 = testGetProcessRolesPage_addRole(
			_process.getId(), randomRole(), "COMPLETED");

		Page<Role> page = roleResource.getProcessRolesPage(
			_process.getId(), true);

		Assert.assertEquals(2, page.getTotalCount());

		assertEqualsIgnoringOrder(
			Arrays.asList(role1, role2), (List<Role>)page.getItems());
		assertValid(page);
	}

	@Override
	protected String[] getAdditionalAssertFieldNames() {
		return new String[] {"id", "name"};
	}

	@Override
	protected Role randomRole() throws Exception {
		com.liferay.portal.kernel.model.Role role1 = _addRole(
			RoleConstants.TYPE_SITE);

		return new Role() {
			{
				id = role1.getRoleId();
				name = role1.getName();
			}
		};
	}

	@Override
	protected Role testGetProcessRolesPage_addRole(Long processId, Role role)
		throws Exception {

		return testGetProcessRolesPage_addRole(processId, role, "RUNNING");
	}

	protected Role testGetProcessRolesPage_addRole(
			Long processId, Role role, String status)
		throws Exception {

		User user = UserTestUtil.addUser();

		com.liferay.portal.kernel.model.Role serviceBuilderRole =
			_roleLocalService.getRole(
				testCompany.getCompanyId(), RoleConstants.USER);

		_userLocalService.deleteRoleUser(
			serviceBuilderRole.getRoleId(), user.getUserId());

		_userLocalService.addRoleUser(role.getId(), user);

		_workflowMetricsRESTTestHelper.addNodeMetric(
			new Assignee() {
				{
					id = user.getUserId();
				}
			},
			testGroup.getCompanyId(),
			() -> _workflowMetricsRESTTestHelper.addInstance(
				testGroup.getCompanyId(), Objects.equals(status, "COMPLETED"),
				processId),
			processId, status);

		return role;
	}

	@Override
	protected Long testGetProcessRolesPage_getProcessId() throws Exception {
		return _process.getId();
	}

	private com.liferay.portal.kernel.model.Role _addRole(int roleType)
		throws Exception {

		com.liferay.portal.kernel.model.Role role = RoleTestUtil.addRole(
			roleType);

		_roles.add(role);

		return role;
	}

	private Process _process;

	@Inject
	private RoleLocalService _roleLocalService;

	@DeleteAfterTestRun
	private final List<com.liferay.portal.kernel.model.Role> _roles =
		new ArrayList<>();

	@Inject
	private UserLocalService _userLocalService;

	@Inject
	private WorkflowMetricsRESTTestHelper _workflowMetricsRESTTestHelper;

}