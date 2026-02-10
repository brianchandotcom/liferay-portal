/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.cmp.resource.v1_0.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.depot.constants.DepotConstants;
import com.liferay.depot.model.DepotEntry;
import com.liferay.depot.service.DepotEntryLocalService;
import com.liferay.headless.cmp.client.dto.v1_0.TaskAssignee;
import com.liferay.headless.cmp.client.pagination.Page;
import com.liferay.headless.cmp.client.problem.Problem;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.model.role.RoleConstants;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.RoleTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.test.log.LogCapture;
import com.liferay.portal.test.log.LoggerTestUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Carolina Barbosa
 */
@RunWith(Arquillian.class)
public class TaskAssigneeResourceTest extends BaseTaskAssigneeResourceTestCase {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE);

	@Override
	@Test
	public void testGetTaskAssigneesPage() throws Exception {
		Role role = RoleTestUtil.addRole(
			"Custom Asset Library Role", RoleConstants.TYPE_DEPOT);

		DepotEntry depotEntry = _depotEntryLocalService.addDepotEntry(
			HashMapBuilder.put(
				LocaleUtil.getDefault(), RandomTestUtil.randomString()
			).build(),
			HashMapBuilder.put(
				LocaleUtil.getDefault(), RandomTestUtil.randomString()
			).build(),
			DepotConstants.TYPE_SPACE,
			ServiceContextTestUtil.getServiceContext());

		User user = UserTestUtil.addUser(
			TestPropsValues.getCompanyId(), TestPropsValues.getUserId(),
			RandomTestUtil.randomString(), LocaleUtil.getDefault(), "John",
			"Doe", new long[] {depotEntry.getGroupId()},
			ServiceContextTestUtil.getServiceContext());

		Page<TaskAssignee> page = taskAssigneeResource.getTaskAssigneesPage(
			"Custom", null);

		assertEquals(
			new TaskAssignee() {
				{
					setExternalReferenceCode(role::getExternalReferenceCode);
					setName(role::getName);
					setType(() -> "Role");
				}
			},
			page.fetchFirstItem());

		page = taskAssigneeResource.getTaskAssigneesPage("Doe", null);

		assertEquals(
			new TaskAssignee() {
				{
					setExternalReferenceCode(user::getExternalReferenceCode);
					setName(user::getFullName);
					setType(() -> "User");
				}
			},
			page.fetchFirstItem());

		page = taskAssigneeResource.getTaskAssigneesPage("John", null);

		assertEquals(
			new TaskAssignee() {
				{
					setExternalReferenceCode(user::getExternalReferenceCode);
					setName(user::getFullName);
					setType(() -> "User");
				}
			},
			page.fetchFirstItem());

		_assertTaskAssigneeType(
			"Role", taskAssigneeResource.getTaskAssigneesPage(null, "Role"));
		_assertTaskAssigneeType(
			"User", taskAssigneeResource.getTaskAssigneesPage(null, "User"));

		try (LogCapture logCapture = LoggerTestUtil.configureLog4JLogger(
				"com.liferay.portal.vulcan.internal.jaxrs.exception.mapper." +
					"WebApplicationExceptionMapper",
				LoggerTestUtil.ERROR)) {

			String invalidType = RandomTestUtil.randomString();

			try {
				taskAssigneeResource.getTaskAssigneesPage(null, invalidType);

				Assert.fail();
			}
			catch (Problem.ProblemException problemException) {
				Problem problem = problemException.getProblem();

				Assert.assertEquals("BAD_REQUEST", problem.getStatus());
				Assert.assertEquals(
					"Invalid type: " + invalidType, problem.getTitle());
			}
		}
	}

	protected String[] getAdditionalAssertFieldNames() {
		return new String[] {"externalReferenceCode", "name", "type"};
	}

	private void _assertTaskAssigneeType(
		String expectedType, Page<TaskAssignee> page) {

		for (TaskAssignee taskAssignee : page.getItems()) {
			Assert.assertEquals(expectedType, taskAssignee.getType());
		}
	}

	@Inject
	private DepotEntryLocalService _depotEntryLocalService;

}