/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.settings.web.internal.portlet.action.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.security.auth.PrincipalThreadLocal;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.PermissionThreadLocal;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.test.portlet.MockLiferayPortletActionRequest;
import com.liferay.portal.kernel.test.portlet.MockLiferayPortletActionResponse;
import com.liferay.portal.kernel.test.portlet.MockPortletSession;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.CompanyTestUtil;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.JavaConstants;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;

import jakarta.servlet.http.HttpServletRequest;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Ivica Cardic
 */
@RunWith(Arquillian.class)
public class ResetTermsOfUseConsentMVCActionCommandTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE);

	@BeforeClass
	public static void setUpClass() throws Exception {
		_company = CompanyTestUtil.addCompany();

		_companyAdminUser = UserTestUtil.addCompanyAdminUser(_company);
	}

	@AfterClass
	public static void tearDownClass() throws Exception {
		_companyLocalService.deleteCompany(_company);
	}

	@Before
	public void setUp() throws Exception {
		_originalName = PrincipalThreadLocal.getName();
		_originalPermissionChecker =
			PermissionThreadLocal.getPermissionChecker();
	}

	@After
	public void tearDown() {
		PermissionThreadLocal.setPermissionChecker(_originalPermissionChecker);
		PrincipalThreadLocal.setName(_originalName);
	}

	@Test
	public void testResetTermsOfUseConsentAsCompanyAdmin() throws Exception {
		User user1 = _addUserAgreedToTermsOfUse();
		User user2 = _addUserAgreedToTermsOfUse();

		UserTestUtil.setUser(_companyAdminUser);

		Assert.assertTrue(
			_mvcActionCommand.processAction(
				_getMockLiferayPortletActionRequest(),
				new MockLiferayPortletActionResponse()));

		_waitForTermsOfUseConsentReset(user1.getUserId());
		_waitForTermsOfUseConsentReset(user2.getUserId());

		user1 = _userLocalService.getUser(user1.getUserId());

		Assert.assertFalse(user1.isAgreedToTermsOfUse());

		user2 = _userLocalService.getUser(user2.getUserId());

		Assert.assertFalse(user2.isAgreedToTermsOfUse());
	}

	@Test
	public void testResetTermsOfUseConsentAsRegularUser() throws Exception {
		User user = _addUserAgreedToTermsOfUse();

		UserTestUtil.setUser(user);

		MockLiferayPortletActionRequest mockLiferayPortletActionRequest =
			_getMockLiferayPortletActionRequest();

		Assert.assertFalse(
			_mvcActionCommand.processAction(
				mockLiferayPortletActionRequest,
				new MockLiferayPortletActionResponse()));

		Assert.assertTrue(
			SessionErrors.contains(
				mockLiferayPortletActionRequest,
				PrincipalException.class.getName()));

		user = _userLocalService.getUser(user.getUserId());

		Assert.assertTrue(user.isAgreedToTermsOfUse());
	}

	private User _addUserAgreedToTermsOfUse() throws Exception {
		User user = UserTestUtil.addUser(_company);

		return _userLocalService.updateAgreedToTermsOfUse(
			user.getUserId(), true);
	}

	private MockLiferayPortletActionRequest
			_getMockLiferayPortletActionRequest()
		throws Exception {

		MockLiferayPortletActionRequest mockLiferayPortletActionRequest =
			new MockLiferayPortletActionRequest();

		HttpServletRequest httpServletRequest =
			mockLiferayPortletActionRequest.getHttpServletRequest();

		httpServletRequest.removeAttribute(
			JavaConstants.JAKARTA_PORTLET_CONFIG);

		mockLiferayPortletActionRequest.setAttribute(
			WebKeys.THEME_DISPLAY, _getThemeDisplay());

		mockLiferayPortletActionRequest.setPortletSession(
			new MockPortletSession());

		return mockLiferayPortletActionRequest;
	}

	private ThemeDisplay _getThemeDisplay() throws Exception {
		ThemeDisplay themeDisplay = new ThemeDisplay();

		themeDisplay.setCompany(_company);
		themeDisplay.setLocale(LocaleUtil.getDefault());
		themeDisplay.setPermissionChecker(
			PermissionThreadLocal.getPermissionChecker());

		return themeDisplay;
	}

	private void _waitForTermsOfUseConsentReset(long userId) throws Exception {
		long endTimeMillis = System.currentTimeMillis() + _ASYNC_TIMEOUT_MILLIS;

		while (System.currentTimeMillis() < endTimeMillis) {
			User user = _userLocalService.getUser(userId);

			if (!user.isAgreedToTermsOfUse()) {
				return;
			}

			Thread.sleep(100);
		}
	}

	private static final long _ASYNC_TIMEOUT_MILLIS = 10 * 1000;

	private static Company _company;
	private static User _companyAdminUser;

	@Inject
	private static CompanyLocalService _companyLocalService;

	@Inject(
		filter = "mvc.command.name=/portal_settings/reset_terms_of_use_consent"
	)
	private MVCActionCommand _mvcActionCommand;

	private String _originalName;
	private PermissionChecker _originalPermissionChecker;

	@Inject
	private UserLocalService _userLocalService;

}