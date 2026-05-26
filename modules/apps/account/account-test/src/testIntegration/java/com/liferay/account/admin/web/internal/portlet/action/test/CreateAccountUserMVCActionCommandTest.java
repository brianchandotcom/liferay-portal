/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.account.admin.web.internal.portlet.action.test;

import com.liferay.account.constants.AccountPortletKeys;
import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.layout.test.util.LayoutTestUtil;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.LayoutTypePortlet;
import com.liferay.portal.kernel.model.Ticket;
import com.liferay.portal.kernel.model.TicketConstants;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.security.permission.PermissionThreadLocal;
import com.liferay.portal.kernel.service.ClassNameLocalService;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.TicketLocalService;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.TestInfo;
import com.liferay.portal.kernel.test.portlet.MockLiferayPortletActionRequest;
import com.liferay.portal.kernel.test.portlet.MockLiferayPortletActionResponse;
import com.liferay.portal.kernel.test.portlet.MockPortletSession;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.rule.Sync;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.test.rule.FeatureFlag;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;
import com.liferay.sharing.model.SharingEntry;
import com.liferay.sharing.security.permission.SharingEntryAction;
import com.liferay.sharing.service.SharingEntryLocalService;

import jakarta.portlet.ActionRequest;
import jakarta.portlet.ActionResponse;

import java.util.Arrays;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.mock.web.MockHttpServletRequest;

/**
 * @author Alicia García
 */
@FeatureFlag("LPD-52006")
@RunWith(Arquillian.class)
@Sync
public class CreateAccountUserMVCActionCommandTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE);

	@Before
	public void setUp() throws Exception {
		_group = GroupTestUtil.addGroup();

		_company = _companyLocalService.getCompany(_group.getCompanyId());
		_layout = LayoutTestUtil.addTypePortletLayout(_group);
	}

	@Test
	@TestInfo("LPD-48130")
	public void testDoTransactionalCommand() throws Exception {
		String emailAddress = RandomTestUtil.randomString() + "@liferay.com";

		Ticket ticket = _ticketLocalService.addTicket(
			_company.getCompanyId(), Group.class.getName(), _group.getGroupId(),
			TicketConstants.TYPE_INVITE_COLLABORATOR, null, emailAddress,
			new Date(System.currentTimeMillis() + TimeUnit.HOURS.toMillis(48)),
			new ServiceContext());

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				_group.getGroupId(), TestPropsValues.getUserId());

		serviceContext.setRequest(new MockHttpServletRequest());

		SharingEntry sharingEntry = _sharingEntryLocalService.addSharingEntry(
			null, TestPropsValues.getUserId(), ticket.getTicketId(), 0, 0,
			_classNameLocalService.getClassNameId(Group.class.getName()),
			_group.getGroupId(), _group.getGroupId(), true,
			Arrays.asList(SharingEntryAction.VIEW), null, serviceContext);

		MockLiferayPortletActionRequest mockLiferayPortletActionRequest =
			_getMockLiferayPortletActionRequest();

		mockLiferayPortletActionRequest.setParameter(
			"firstName", RandomTestUtil.randomString());
		mockLiferayPortletActionRequest.setParameter(
			"languageId", LocaleUtil.toLanguageId(LocaleUtil.getDefault()));
		mockLiferayPortletActionRequest.setParameter(
			"lastName", RandomTestUtil.randomString());

		String password = RandomTestUtil.randomString();

		mockLiferayPortletActionRequest.setParameter("password1", password);
		mockLiferayPortletActionRequest.setParameter("password2", password);

		mockLiferayPortletActionRequest.setParameter(
			"screenName", RandomTestUtil.randomString());
		mockLiferayPortletActionRequest.setParameter(
			"ticketKey", ticket.getKey());

		ReflectionTestUtil.invoke(
			_mvcActionCommand, "doTransactionalCommand",
			new Class<?>[] {ActionRequest.class, ActionResponse.class},
			mockLiferayPortletActionRequest,
			new MockLiferayPortletActionResponse());

		User user = _userLocalService.fetchUserByEmailAddress(
			_company.getCompanyId(), emailAddress);

		Assert.assertNull(
			_ticketLocalService.fetchTicket(ticket.getTicketId()));

		sharingEntry = _sharingEntryLocalService.fetchSharingEntry(
			sharingEntry.getSharingEntryId());

		Assert.assertEquals(0, sharingEntry.getToTicketId());
		Assert.assertEquals(user.getUserId(), sharingEntry.getToUserId());

		_userLocalService.deleteUser(user);
	}

	private MockLiferayPortletActionRequest
			_getMockLiferayPortletActionRequest()
		throws Exception {

		MockLiferayPortletActionRequest mockLiferayPortletActionRequest =
			new MockLiferayPortletActionRequest();

		mockLiferayPortletActionRequest.setAttribute(
			WebKeys.PORTLET_ID, AccountPortletKeys.ACCOUNT_USERS_REGISTRATION);
		mockLiferayPortletActionRequest.setAttribute(
			WebKeys.THEME_DISPLAY, _getThemeDisplay());
		mockLiferayPortletActionRequest.setPortletSession(
			new MockPortletSession());

		return mockLiferayPortletActionRequest;
	}

	private ThemeDisplay _getThemeDisplay() throws Exception {
		ThemeDisplay themeDisplay = new ThemeDisplay();

		themeDisplay.setCompany(_company);
		themeDisplay.setLayout(_layout);
		themeDisplay.setLayoutSet(_layout.getLayoutSet());
		themeDisplay.setLayoutTypePortlet(
			(LayoutTypePortlet)_layout.getLayoutType());
		themeDisplay.setPermissionChecker(
			PermissionThreadLocal.getPermissionChecker());
		themeDisplay.setPlid(_layout.getPlid());
		themeDisplay.setScopeGroupId(_group.getGroupId());
		themeDisplay.setSiteGroupId(_group.getGroupId());
		themeDisplay.setUser(TestPropsValues.getUser());

		return themeDisplay;
	}

	@Inject
	private ClassNameLocalService _classNameLocalService;

	private Company _company;

	@Inject
	private CompanyLocalService _companyLocalService;

	@DeleteAfterTestRun
	private Group _group;

	private Layout _layout;

	@Inject(filter = "mvc.command.name=/account_admin/create_account_user")
	private MVCActionCommand _mvcActionCommand;

	@Inject
	private SharingEntryLocalService _sharingEntryLocalService;

	@Inject
	private TicketLocalService _ticketLocalService;

	@Inject
	private UserLocalService _userLocalService;

}