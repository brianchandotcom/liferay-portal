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
import com.liferay.portal.configuration.test.util.ConfigurationTemporarySwapper;
import com.liferay.portal.kernel.model.PasswordPolicy;
import com.liferay.portal.kernel.model.Ticket;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.service.CompanyLocalServiceUtil;
import com.liferay.portal.kernel.service.PasswordPolicyLocalServiceUtil;
import com.liferay.portal.kernel.service.TicketLocalService;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.service.UserLocalServiceUtil;
import com.liferay.portal.kernel.test.portlet.MockLiferayPortletActionRequest;
import com.liferay.portal.kernel.test.portlet.MockLiferayPortletActionResponse;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.HashMapDictionaryBuilder;
import com.liferay.portal.kernel.util.JavaConstants;
import com.liferay.portal.kernel.util.PrefsPropsUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.util.Date;
import java.util.List;

import javax.portlet.PortletPreferences;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Alvaro Saugar
 * @author Olivér Kecskeméty
 */
@RunWith(Arquillian.class)
public class ForgotPasswordMVCActionCommandTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@BeforeClass
	public static void setUpClass() throws Exception {
		_createUser();

		_portletPreferences = PrefsPropsUtil.getPreferences(
			TestPropsValues.getCompanyId());

		_portletPreferences.setValue(
			PropsKeys.USERS_REMINDER_QUERIES_ENABLED, Boolean.FALSE.toString());

		_portletPreferences.store();
	}

	@AfterClass
	public static void tearDownClass() throws Exception {
		_portletPreferences.reset(PropsKeys.USERS_REMINDER_QUERIES_ENABLED);
	}

	@Test
	public void testSendPasswordReminderToLockedOutUser() throws Exception {
		try (ConfigurationTemporarySwapper configurationTemporarySwapper =
				new ConfigurationTemporarySwapper(
					"com.liferay.captcha.configuration.CaptchaConfiguration",
					HashMapDictionaryBuilder.<String, Object>put(
						"sendPasswordCaptchaEnabled", false
					).build())) {

			List<Ticket> ticketsBefore = _ticketLocalService.getTickets(
				_user.getCompanyId(), User.class.getName(), _user.getUserId());

			_mvcActionCommand.processAction(
				_getMockLiferayPortletActionRequest(),
				new MockLiferayPortletActionResponse());

			List<Ticket> ticketsAfter = _ticketLocalService.getTickets(
				_user.getCompanyId(), User.class.getName(), _user.getUserId());

			Assert.assertTrue(
				(ticketsBefore.size() + 1) == ticketsAfter.size());
		}
	}

	private static void _createUser() throws Exception {
		_user = UserTestUtil.addUser();

		_user.setLockout(true);

		_user.setLockoutDate(new Date());

		PasswordPolicy passwordPolicy = _user.getPasswordPolicy();

		passwordPolicy.setLockout(true);
		passwordPolicy.setLockoutDuration(0);

		PasswordPolicyLocalServiceUtil.updatePasswordPolicy(passwordPolicy);

		UserLocalServiceUtil.updateUser(_user);
	}

	private MockLiferayPortletActionRequest
			_getMockLiferayPortletActionRequest()
		throws Exception {

		MockLiferayPortletActionRequest mockLiferayPortletActionRequest =
			new MockLiferayPortletActionRequest();

		ThemeDisplay themeDisplay = new ThemeDisplay();

		themeDisplay.setCompany(
			CompanyLocalServiceUtil.fetchCompany(
				TestPropsValues.getCompanyId()));
		themeDisplay.setUser(_user);

		mockLiferayPortletActionRequest.setAttribute(
			JavaConstants.JAVAX_PORTLET_CONFIG, null);
		mockLiferayPortletActionRequest.setAttribute(
			WebKeys.THEME_DISPLAY, themeDisplay);

		mockLiferayPortletActionRequest.addParameter(
			"login", _user.getEmailAddress());

		return mockLiferayPortletActionRequest;
	}

	private static PortletPreferences _portletPreferences;

	@DeleteAfterTestRun
	private static User _user;

	@Inject(
		filter = "mvc.command.name=/login/forgot_password",
		type = MVCActionCommand.class
	)
	private MVCActionCommand _mvcActionCommand;

	@Inject
	private TicketLocalService _ticketLocalService;

	@Inject
	private UserLocalService _userLocalService;

}