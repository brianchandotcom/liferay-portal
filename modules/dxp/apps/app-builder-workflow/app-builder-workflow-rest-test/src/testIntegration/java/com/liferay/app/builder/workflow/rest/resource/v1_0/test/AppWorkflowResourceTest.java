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

package com.liferay.app.builder.workflow.rest.resource.v1_0.test;

import com.liferay.app.builder.model.AppBuilderApp;
import com.liferay.app.builder.service.AppBuilderAppLocalService;
import com.liferay.app.builder.workflow.rest.client.dto.v1_0.AppWorkflow;
import com.liferay.app.builder.workflow.rest.client.dto.v1_0.AppWorkflowDataLayoutLink;
import com.liferay.app.builder.workflow.rest.client.dto.v1_0.AppWorkflowState;
import com.liferay.app.builder.workflow.rest.client.dto.v1_0.AppWorkflowTask;
import com.liferay.app.builder.workflow.rest.client.dto.v1_0.AppWorkflowTransition;
import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.model.role.RoleConstants;
import com.liferay.portal.kernel.service.RoleLocalService;
import com.liferay.portal.kernel.test.rule.DataGuard;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.test.rule.Inject;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Rafael Praxedes
 */
@DataGuard(scope = DataGuard.Scope.METHOD)
@RunWith(Arquillian.class)
public class AppWorkflowResourceTest extends BaseAppWorkflowResourceTestCase {

	@Before
	@Override
	public void setUp() throws Exception {
		super.setUp();

		_appBuilderApp = _appBuilderAppLocalService.addAppBuilderApp(
			testGroup.getGroupId(), testCompany.getCompanyId(),
			testGroup.getCreatorUserId(), true, RandomTestUtil.nextLong(),
			RandomTestUtil.nextLong(), RandomTestUtil.nextLong(),
			RandomTestUtil.nextLong(), RandomTestUtil.randomLocaleStringMap(),
			RandomTestUtil.randomString());
	}

	@Override
	@Test
	public void testGetAppWorkflow() throws Exception {
		AppWorkflow postAppWorkflow = testPostAppWorkflow_addAppWorkflow(
			randomAppWorkflow());

		AppWorkflow getAppWorkflow = appWorkflowResource.getAppWorkflow(
			postAppWorkflow.getAppId());

		assertEquals(postAppWorkflow, getAppWorkflow);
		assertValid(getAppWorkflow);
	}

	@Override
	@Test
	public void testPutAppWorkflow() throws Exception {
		AppWorkflow postAppWorkflow = testPostAppWorkflow_addAppWorkflow(
			randomAppWorkflow());

		AppWorkflow randomAppWorkflow = randomAppWorkflow();

		AppWorkflow putAppWorkflow = appWorkflowResource.putAppWorkflow(
			postAppWorkflow.getAppId(), randomAppWorkflow);

		assertEquals(randomAppWorkflow, putAppWorkflow);
		assertValid(putAppWorkflow);

		AppWorkflow getAppWorkflow = appWorkflowResource.getAppWorkflow(
			putAppWorkflow.getAppId());

		assertEquals(randomAppWorkflow, getAppWorkflow);
		assertValid(getAppWorkflow);
	}

	@Override
	protected String[] getAdditionalAssertFieldNames() {
		return new String[] {"appId", "appWorkflowStates", "appWorkflowTasks"};
	}

	@Override
	protected AppWorkflow randomAppWorkflow() throws Exception {
		String appWorkflowTaskName = StringUtil.toLowerCase(
			RandomTestUtil.randomString());
		String terminalStateName = StringUtil.toLowerCase(
			RandomTestUtil.randomString());

		return new AppWorkflow() {
			{
				appId = _appBuilderApp.getAppBuilderAppId();
				appWorkflowStates = new AppWorkflowState[] {
					new AppWorkflowState() {
						{
							appWorkflowTransitions =
								new AppWorkflowTransition[] {
									new AppWorkflowTransition() {
										{
											name = StringUtil.toLowerCase(
												RandomTestUtil.randomString());
											primary = true;
											transitionTo = appWorkflowTaskName;
										}
									}
								};
							initial = true;
							name = StringUtil.toLowerCase(
								RandomTestUtil.randomString());
						}
					},
					new AppWorkflowState() {
						{
							appWorkflowTransitions =
								new AppWorkflowTransition[0];
							initial = false;
							name = terminalStateName;
						}
					}
				};
				appWorkflowTasks = new AppWorkflowTask[] {
					new AppWorkflowTask() {
						{
							appWorkflowDataLayoutLinks =
								new AppWorkflowDataLayoutLink[] {
									new AppWorkflowDataLayoutLink() {
										{
											dataLayoutId =
												_appBuilderApp.
													getDdmStructureLayoutId();
											readOnly = false;
										}
									}
								};
							appWorkflowTransitions =
								new AppWorkflowTransition[] {
									new AppWorkflowTransition() {
										{
											name = StringUtil.toLowerCase(
												RandomTestUtil.randomString());
											primary = true;
											transitionTo = terminalStateName;
										}
									}
								};
							name = appWorkflowTaskName;

							setRoleIds(
								() -> {
									Role role = _roleLocalService.getRole(
										testCompany.getCompanyId(),
										RoleConstants.PORTAL_CONTENT_REVIEWER);

									return new Long[] {role.getRoleId()};
								});
						}
					}
				};
			}
		};
	}

	@Override
	protected AppWorkflow testPostAppWorkflow_addAppWorkflow(
			AppWorkflow appWorkflow)
		throws Exception {

		return appWorkflowResource.postAppWorkflow(
			appWorkflow.getAppId(), appWorkflow);
	}

	private AppBuilderApp _appBuilderApp;

	@Inject
	private AppBuilderAppLocalService _appBuilderAppLocalService;

	@Inject
	private RoleLocalService _roleLocalService;

}