/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.users.admin.web.internal.portlet.action.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.model.GroupConstants;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.LayoutTypePortlet;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.model.UserConstants;
import com.liferay.portal.kernel.portlet.LiferayActionRequest;
import com.liferay.portal.kernel.portlet.LiferayStateAwareResponse;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.security.auth.PrincipalThreadLocal;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.PermissionCheckerFactoryUtil;
import com.liferay.portal.kernel.security.permission.PermissionThreadLocal;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.service.PortletLocalService;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.service.WorkflowDefinitionLinkLocalService;
import com.liferay.portal.kernel.service.WorkflowInstanceLinkLocalService;
import com.liferay.portal.kernel.servlet.PortletServlet;
import com.liferay.portal.kernel.test.portlet.MockLiferayPortletActionRequest;
import com.liferay.portal.kernel.test.portlet.MockLiferayPortletActionResponse;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.Sync;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.kernel.workflow.WorkflowTask;
import com.liferay.portal.kernel.workflow.WorkflowTaskManager;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;
import com.liferay.portlet.ActionRequestFactory;
import com.liferay.users.admin.constants.UsersAdminPortletKeys;

import jakarta.portlet.ActionRequest;
import jakarta.portlet.Event;
import jakarta.portlet.Portlet;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Brian I. Kim
 */
@RunWith(Arquillian.class)
@Sync
public class EditUserMVCActionCommandTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE);

	@Test
	public void testActivateUserWithWorkflowCompletesWorkflowTask()
		throws Exception {

		String originalName = PrincipalThreadLocal.getName();

		PermissionChecker originalPermissionChecker =
			PermissionThreadLocal.getPermissionChecker();

		try {
			PrincipalThreadLocal.setName(TestPropsValues.getUserId());

			PermissionChecker adminPermissionChecker =
				PermissionCheckerFactoryUtil.create(TestPropsValues.getUser());

			PermissionThreadLocal.setPermissionChecker(adminPermissionChecker);

			_workflowDefinitionLinkLocalService.addWorkflowDefinitionLink(
				null, TestPropsValues.getUserId(),
				TestPropsValues.getCompanyId(),
				GroupConstants.DEFAULT_LIVE_GROUP_ID, User.class.getName(), 0,
				0, "Single Approver", 1);

			User user = _userLocalService.addUserWithWorkflow(
				0, TestPropsValues.getCompanyId(), false, "test", "test", false,
				RandomTestUtil.randomString(),
				RandomTestUtil.randomString() + "@liferay.com", LocaleUtil.US,
				RandomTestUtil.randomString(), RandomTestUtil.randomString(),
				RandomTestUtil.randomString(), 0, 0, true, 1, 1, 1970,
				StringPool.BLANK, UserConstants.TYPE_REGULAR, null, null, null,
				null, true,
				ServiceContextTestUtil.getServiceContext(
					TestPropsValues.getCompanyId(),
					TestPropsValues.getGroupId(), TestPropsValues.getUserId()));

			Assert.assertEquals(
				WorkflowConstants.STATUS_PENDING, user.getStatus());

			_processActionRequest(
				"/users_admin/edit_user",
				HashMapBuilder.put(
					Constants.CMD, Constants.RESTORE
				).put(
					"deleteUserIds", String.valueOf(user.getUserId())
				).build(),
				adminPermissionChecker);

			List<WorkflowTask> workflowTasks =
				_workflowTaskManager.getWorkflowTasksByWorkflowInstance(
					TestPropsValues.getCompanyId(), null,
					_workflowInstanceLinkLocalService.fetchWorkflowInstanceLink(
						TestPropsValues.getCompanyId(),
						WorkflowConstants.DEFAULT_GROUP_ID,
						User.class.getName(), user.getUserId()
					).getWorkflowInstanceId(),
					null, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);

			Assert.assertEquals(
				workflowTasks.toString(), 1, workflowTasks.size());

			WorkflowTask workflowTask = workflowTasks.get(0);

			Assert.assertTrue(workflowTask.isCompleted());

			user = _userLocalService.getUser(user.getUserId());

			Assert.assertEquals(
				WorkflowConstants.STATUS_APPROVED, user.getStatus());
		}
		finally {
			PrincipalThreadLocal.setName(originalName);

			PermissionThreadLocal.setPermissionChecker(
				originalPermissionChecker);
		}
	}

	private ThemeDisplay _getThemeDisplay(PermissionChecker permissionChecker)
		throws Exception {

		ThemeDisplay themeDisplay = new ThemeDisplay();

		themeDisplay.setCompany(
			_companyLocalService.getCompany(TestPropsValues.getCompanyId()));
		themeDisplay.setPermissionChecker(permissionChecker);
		themeDisplay.setScopeGroupId(TestPropsValues.getGroupId());
		themeDisplay.setSiteGroupId(TestPropsValues.getGroupId());

		Layout layout = _layoutLocalService.getLayout(
			TestPropsValues.getPlid());

		themeDisplay.setLayout(layout);
		themeDisplay.setLayoutSet(layout.getLayoutSet());
		themeDisplay.setLayoutTypePortlet(
			(LayoutTypePortlet)layout.getLayoutType());

		themeDisplay.setUser(TestPropsValues.getUser());

		return themeDisplay;
	}

	private void _processActionRequest(
			String actionName, Map<String, String> params,
			PermissionChecker permissionChecker)
		throws Exception {

		MockLiferayPortletActionRequest mockLiferayPortletActionRequest =
			new MockLiferayPortletActionRequest();

		mockLiferayPortletActionRequest.addParameter(
			ActionRequest.ACTION_NAME, actionName);

		for (Map.Entry<String, String> entry : params.entrySet()) {
			mockLiferayPortletActionRequest.addParameter(
				entry.getKey(), entry.getValue());
		}

		mockLiferayPortletActionRequest.setAttribute(
			PortletServlet.PORTLET_SERVLET_REQUEST,
			mockLiferayPortletActionRequest.getHttpServletRequest());
		mockLiferayPortletActionRequest.setAttribute(
			WebKeys.PORTLET_ID, UsersAdminPortletKeys.USERS_ADMIN);
		mockLiferayPortletActionRequest.setAttribute(
			WebKeys.THEME_DISPLAY, _getThemeDisplay(permissionChecker));
		mockLiferayPortletActionRequest.setAttribute(
			WebKeys.USER_ID, TestPropsValues.getUserId());

		LiferayActionRequest liferayActionRequest = ActionRequestFactory.create(
			mockLiferayPortletActionRequest.getHttpServletRequest(),
			_portletLocalService.getPortletById(
				UsersAdminPortletKeys.USERS_ADMIN),
			null, null, null, null, null, TestPropsValues.getPlid());

		liferayActionRequest.setPortletRequestDispatcherRequest(
			mockLiferayPortletActionRequest.getHttpServletRequest());

		_portlet.processAction(
			liferayActionRequest,
			new EditUserMVCActionCommandTest.
				CustomMockLiferayPortletActionResponse());
	}

	@Inject
	private static WorkflowDefinitionLinkLocalService
		_workflowDefinitionLinkLocalService;

	@Inject
	private static WorkflowInstanceLinkLocalService
		_workflowInstanceLinkLocalService;

	@Inject
	private CompanyLocalService _companyLocalService;

	@Inject
	private LayoutLocalService _layoutLocalService;

	@Inject(filter = "mvc.command.name=/users_admin/edit_user")
	private MVCActionCommand _mvcActionCommand;

	@Inject(
		filter = "component.name=com.liferay.users.admin.web.internal.portlet.UsersAdminPortlet"
	)
	private Portlet _portlet;

	@Inject
	private PortletLocalService _portletLocalService;

	@Inject
	private UserLocalService _userLocalService;

	@Inject
	private WorkflowTaskManager _workflowTaskManager;

	private class CustomMockLiferayPortletActionResponse
		extends MockLiferayPortletActionResponse
		implements LiferayStateAwareResponse {

		@Override
		public List<Event> getEvents() {
			return Collections.emptyList();
		}

		@Override
		public String getRedirectLocation() {
			return StringPool.BLANK;
		}

		@Override
		public boolean isCalledSetRenderParameter() {
			return false;
		}

	}

}