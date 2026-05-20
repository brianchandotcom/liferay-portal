/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.redirect.web.internal.portlet.action.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.model.role.RoleConstants;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.security.permission.PermissionCheckerFactoryUtil;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.TestInfo;
import com.liferay.portal.kernel.test.context.ContextUserReplace;
import com.liferay.portal.kernel.test.portlet.MockLiferayPortletActionRequest;
import com.liferay.portal.kernel.test.portlet.MockLiferayPortletActionResponse;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;
import com.liferay.redirect.model.RedirectNotFoundEntry;
import com.liferay.redirect.service.RedirectNotFoundEntryLocalService;

import jakarta.portlet.ActionRequest;
import jakarta.portlet.ActionResponse;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Jonathan McCann
 */
@RunWith(Arquillian.class)
public class EditRedirectNotFoundEntryMVCActionCommandTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE);

	@Before
	public void setUp() throws Exception {
		_group1 = GroupTestUtil.addGroup();
		_group2 = GroupTestUtil.addGroup();

		_user = UserTestUtil.addGroupUser(
			_group1, RoleConstants.SITE_ADMINISTRATOR);
	}

	@Test
	@TestInfo("LPD-90722")
	public void testDoProcessAction() throws Exception {
		RedirectNotFoundEntry redirectNotFoundEntry =
			_redirectNotFoundEntryLocalService.addOrUpdateRedirectNotFoundEntry(
				_group1, "url");

		try (ContextUserReplace contextUserReplace = new ContextUserReplace(
				_user, PermissionCheckerFactoryUtil.create(_user))) {

			ReflectionTestUtil.invoke(
				_mvcActionCommand, "doProcessAction",
				new Class<?>[] {ActionRequest.class, ActionResponse.class},
				_getMockLiferayPortletActionRequest(
					redirectNotFoundEntry.getRedirectNotFoundEntryId(), null,
					true),
				new MockLiferayPortletActionResponse());
		}

		redirectNotFoundEntry =
			_redirectNotFoundEntryLocalService.fetchRedirectNotFoundEntry(
				redirectNotFoundEntry.getRedirectNotFoundEntryId());

		Assert.assertTrue(redirectNotFoundEntry.isIgnored());
	}

	@Test
	@TestInfo("LPD-90722")
	public void testDoProcessActionWithRedirectNotFoundEntryIdFromOtherGroup()
		throws Exception {

		RedirectNotFoundEntry redirectNotFoundEntry =
			_redirectNotFoundEntryLocalService.addOrUpdateRedirectNotFoundEntry(
				_group2, "url");

		long redirectNotFoundEntryId =
			redirectNotFoundEntry.getRedirectNotFoundEntryId();

		try (ContextUserReplace contextUserReplace = new ContextUserReplace(
				_user, PermissionCheckerFactoryUtil.create(_user))) {

			Assert.assertThrows(
				PrincipalException.class,
				() -> ReflectionTestUtil.invoke(
					_mvcActionCommand, "doProcessAction",
					new Class<?>[] {ActionRequest.class, ActionResponse.class},
					_getMockLiferayPortletActionRequest(
						redirectNotFoundEntryId, null, true),
					new MockLiferayPortletActionResponse()));
		}

		redirectNotFoundEntry =
			_redirectNotFoundEntryLocalService.fetchRedirectNotFoundEntry(
				redirectNotFoundEntryId);

		Assert.assertFalse(redirectNotFoundEntry.isIgnored());
	}

	@Test
	@TestInfo("LPD-90722")
	public void testDoProcessActionWithRowIdsFromOtherGroup() throws Exception {
		RedirectNotFoundEntry redirectNotFoundEntry =
			_redirectNotFoundEntryLocalService.addOrUpdateRedirectNotFoundEntry(
				_group2, "url");

		long redirectNotFoundEntryId =
			redirectNotFoundEntry.getRedirectNotFoundEntryId();

		try (ContextUserReplace contextUserReplace = new ContextUserReplace(
				_user, PermissionCheckerFactoryUtil.create(_user))) {

			Assert.assertThrows(
				PrincipalException.class,
				() -> ReflectionTestUtil.invoke(
					_mvcActionCommand, "doProcessAction",
					new Class<?>[] {ActionRequest.class, ActionResponse.class},
					_getMockLiferayPortletActionRequest(
						null, new long[] {redirectNotFoundEntryId}, true),
					new MockLiferayPortletActionResponse()));
		}

		redirectNotFoundEntry =
			_redirectNotFoundEntryLocalService.fetchRedirectNotFoundEntry(
				redirectNotFoundEntryId);

		Assert.assertFalse(redirectNotFoundEntry.isIgnored());
	}

	private MockLiferayPortletActionRequest _getMockLiferayPortletActionRequest(
			Long redirectNotFoundEntryId, long[] rowIds, boolean ignored)
		throws Exception {

		MockLiferayPortletActionRequest mockLiferayPortletActionRequest =
			new MockLiferayPortletActionRequest();

		mockLiferayPortletActionRequest.setAttribute(
			WebKeys.THEME_DISPLAY, _getThemeDisplay());

		if (redirectNotFoundEntryId != null) {
			mockLiferayPortletActionRequest.setParameter(
				"redirectNotFoundEntryId",
				String.valueOf(redirectNotFoundEntryId));
		}

		if (rowIds != null) {
			mockLiferayPortletActionRequest.setParameter(
				"rowIds", StringUtil.merge(rowIds));
		}

		mockLiferayPortletActionRequest.setParameter(
			"ignored", String.valueOf(ignored));

		return mockLiferayPortletActionRequest;
	}

	private ThemeDisplay _getThemeDisplay() throws Exception {
		ThemeDisplay themeDisplay = new ThemeDisplay();

		themeDisplay.setPermissionChecker(
			PermissionCheckerFactoryUtil.create(_user));
		themeDisplay.setScopeGroupId(_group1.getGroupId());

		return themeDisplay;
	}

	private Group _group1;
	private Group _group2;

	@Inject(filter = "mvc.command.name=/redirect/edit_redirect_not_found_entry")
	private MVCActionCommand _mvcActionCommand;

	@Inject
	private RedirectNotFoundEntryLocalService
		_redirectNotFoundEntryLocalService;

	private User _user;

}