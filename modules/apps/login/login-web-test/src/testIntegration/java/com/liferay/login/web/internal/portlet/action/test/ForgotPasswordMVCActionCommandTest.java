/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.login.web.internal.portlet.action.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.portal.kernel.model.PasswordPolicy;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.service.CompanyLocalServiceUtil;
import com.liferay.portal.kernel.service.PasswordPolicyLocalServiceUtil;
import com.liferay.portal.kernel.service.RoleLocalService;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.service.UserLocalServiceUtil;
import com.liferay.portal.kernel.test.portlet.MockLiferayPortletActionRequest;
import com.liferay.portal.kernel.test.portlet.MockLiferayPortletActionResponse;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.JavaConstants;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Alvaro Saugar
 */
@RunWith(Arquillian.class)
public class ForgotPasswordMVCActionCommandTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@BeforeClass
	public static void setUpClass() throws Exception {
		_companyId = TestPropsValues.getCompanyId();
		_setUser();
		_setMockLiferayPortletActionRequest();
	}

	@AfterClass
	public static void tearDownClass() throws Exception {
		UserLocalServiceUtil.deleteUser(_forgotPasswordUser);
	}

	@Test
	public void testLockoutAnyUser() throws Exception {
		_mvcActionCommand.processAction(
			_mockLiferayPortletActionRequest,
			new MockLiferayPortletActionResponse());

		User userReturn = (User)_mockLiferayPortletActionRequest.getAttribute(
			WebKeys.FORGOT_PASSWORD_REMINDER_USER);

		Assert.assertEquals(
			userReturn.getEmailAddress(),
			_forgotPasswordUser.getEmailAddress());
	}

	private static ThemeDisplay _getThemeDisplay() throws Exception {
		ThemeDisplay themeDisplay = new ThemeDisplay();

		themeDisplay.setCompany(
			CompanyLocalServiceUtil.fetchCompany(_companyId));

		return themeDisplay;
	}

	private static void _setMockLiferayPortletActionRequest() throws Exception {
		_mockLiferayPortletActionRequest =
			new MockLiferayPortletActionRequest();

		_mockLiferayPortletActionRequest.setAttribute(
			WebKeys.THEME_DISPLAY, _getThemeDisplay());

		HttpServletRequest httpServletRequest =
			_mockLiferayPortletActionRequest.getHttpServletRequest();

		httpServletRequest.setAttribute(
			JavaConstants.JAVAX_PORTLET_CONFIG, null);

		HttpSession httpSession = httpServletRequest.getSession();

		httpSession.setAttribute(WebKeys.CAPTCHA_TEXT, _CAPTCHA_TEXT);

		_mockLiferayPortletActionRequest.addParameter(
			"captchaText", _CAPTCHA_TEXT);

		_mockLiferayPortletActionRequest.addParameter(
			"login", _forgotPasswordUser.getEmailAddress());
	}

	private static void _setUser() throws Exception {
		_forgotPasswordUser = UserTestUtil.addUser();

		_forgotPasswordUser.setLockout(true);

		_forgotPasswordUser.setLockoutDate(new Date());

		PasswordPolicy passwordPolicy = _forgotPasswordUser.getPasswordPolicy();

		passwordPolicy.setLockout(true);
		passwordPolicy.setLockoutDuration(0);

		PasswordPolicyLocalServiceUtil.updatePasswordPolicy(passwordPolicy);

		UserLocalServiceUtil.updateUser(_forgotPasswordUser);
	}

	private static final String _CAPTCHA_TEXT = StringUtil.toLowerCase(
		RandomTestUtil.randomString());

	private static long _companyId;
	private static User _forgotPasswordUser;
	private static MockLiferayPortletActionRequest
		_mockLiferayPortletActionRequest;

	@Inject(
		filter = "mvc.command.name=/login/forgot_password",
		type = MVCActionCommand.class
	)
	private MVCActionCommand _mvcActionCommand;

	@Inject
	private RoleLocalService _roleLocalService;

	@Inject
	private UserLocalService _userLocalService;

}