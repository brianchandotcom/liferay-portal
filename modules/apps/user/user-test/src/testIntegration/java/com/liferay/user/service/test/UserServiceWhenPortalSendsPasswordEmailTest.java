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

package com.liferay.user.service.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.service.UserService;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.kernel.util.Localization;
import com.liferay.portal.kernel.util.LocalizationUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.ProxyUtil;
import com.liferay.portal.test.mail.MailServiceTestUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.SynchronousMailTestRule;
import com.liferay.portal.util.PrefsPropsUtil;
import com.liferay.portal.util.PropsUtil;

import java.util.Objects;

import javax.portlet.PortletPreferences;

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
 * @author Brian Wing Shun Chan
 * @author José Manuel Navarro
 * @author Drew Brokke
 */
@RunWith(Arquillian.class)
public class UserServiceWhenPortalSendsPasswordEmailTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(), SynchronousMailTestRule.INSTANCE);

	@BeforeClass
	public static void setUpClass() throws Exception {
		_adminEmailPasswordResetSubject = PropsUtil.get(
			PropsKeys.ADMIN_EMAIL_PASSWORD_RESET_SUBJECT);
		_adminEmailPasswordResetBody = PropsUtil.get(
			PropsKeys.ADMIN_EMAIL_PASSWORD_RESET_BODY);

		PropsUtil.set(
			PropsKeys.ADMIN_EMAIL_PASSWORD_RESET_SUBJECT,
			"com/liferay/user/service/test/dependencies" +
				"/email_password_reset_subject.tmpl");
		PropsUtil.set(
			PropsKeys.ADMIN_EMAIL_PASSWORD_RESET_BODY,
			"com/liferay/user/service/test/dependencies" +
				"/email_password_reset_body.tmpl");

		_localization = LocalizationUtil.getLocalization();

		_localizationUtil.setLocalization(
			(Localization)ProxyUtil.newProxyInstance(
				Localization.class.getClassLoader(),
				new Class<?>[] {Localization.class},
				(proxy, method, args) -> {
					if (Objects.equals(
							method.getName(), "getLocalizationMap") &&
						(args.length == 3)) {

						return _localization.getLocalizationMap(
							(PortletPreferences)args[0], (String)args[1],
							(String)args[2], PropsUtil.get((String)args[2]),
							UserServiceWhenPortalSendsPasswordEmailTest.class.
								getClassLoader());
					}

					throw new UnsupportedOperationException();
				}));
	}

	@AfterClass
	public static void tearDownClass() {
		PropsUtil.set(
			PropsKeys.ADMIN_EMAIL_PASSWORD_RESET_SUBJECT,
			_adminEmailPasswordResetSubject);
		PropsUtil.set(
			PropsKeys.ADMIN_EMAIL_PASSWORD_RESET_BODY,
			_adminEmailPasswordResetBody);

		_localizationUtil.setLocalization(_localization);
	}

	@Before
	public void setUp() throws Exception {
		_user = UserTestUtil.addUser();

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				_user.getGroupId(), _user.getUserId());

		ServiceContextThreadLocal.pushServiceContext(serviceContext);
	}

	@After
	public void tearDown() {
		ServiceContextThreadLocal.popServiceContext();
	}

	@Test
	public void testShouldSendResetLinkEmailByEmailAddress() throws Exception {
		PortletPreferences portletPreferences =
			givenThatCompanySendsResetPasswordLink();

		try {
			int initialInboxSize = MailServiceTestUtil.getInboxSize();

			boolean sentPassword = _userService.sendPasswordByEmailAddress(
				_user.getCompanyId(), _user.getEmailAddress());

			Assert.assertFalse(sentPassword);

			Assert.assertEquals(
				initialInboxSize + 1, MailServiceTestUtil.getInboxSize());
			Assert.assertTrue(
				MailServiceTestUtil.lastMailMessageContains(
					"email_password_reset_body.tmpl"));
		}
		finally {
			restorePortletPreferences(portletPreferences);
		}
	}

	@Test
	public void testShouldSendResetLinkEmailByScreenName() throws Exception {
		PortletPreferences portletPreferences =
			givenThatCompanySendsResetPasswordLink();

		try {
			int initialInboxSize = MailServiceTestUtil.getInboxSize();

			boolean sentPassword = _userService.sendPasswordByScreenName(
				_user.getCompanyId(), _user.getScreenName());

			Assert.assertFalse(sentPassword);

			Assert.assertEquals(
				initialInboxSize + 1, MailServiceTestUtil.getInboxSize());
			Assert.assertTrue(
				MailServiceTestUtil.lastMailMessageContains(
					"email_password_reset_body.tmpl"));
		}
		finally {
			restorePortletPreferences(portletPreferences);
		}
	}

	@Test
	public void testShouldSendResetLinkEmailByUserId() throws Exception {
		PortletPreferences portletPreferences =
			givenThatCompanySendsResetPasswordLink();

		try {
			int initialInboxSize = MailServiceTestUtil.getInboxSize();

			boolean sentPassword = _userService.sendPasswordByUserId(
				_user.getUserId());

			Assert.assertFalse(sentPassword);

			Assert.assertEquals(
				initialInboxSize + 1, MailServiceTestUtil.getInboxSize());
			Assert.assertTrue(
				MailServiceTestUtil.lastMailMessageContains(
					"email_password_reset_body.tmpl"));
		}
		finally {
			restorePortletPreferences(portletPreferences);
		}
	}

	protected PortletPreferences givenThatCompanySendsResetPasswordLink()
		throws Exception {

		PortletPreferences portletPreferences = PrefsPropsUtil.getPreferences(
			_user.getCompanyId(), false);

		portletPreferences.setValue(
			PropsKeys.COMPANY_SECURITY_SEND_PASSWORD_RESET_LINK,
			Boolean.TRUE.toString());

		portletPreferences.store();

		return portletPreferences;
	}

	protected void restorePortletPreferences(
			PortletPreferences portletPreferences)
		throws Exception {

		portletPreferences.reset(
			PropsKeys.COMPANY_SECURITY_SEND_PASSWORD_RESET_LINK);

		portletPreferences.store();
	}

	private static String _adminEmailPasswordResetBody;
	private static String _adminEmailPasswordResetSubject;
	private static Localization _localization;

	@Inject
	private static LocalizationUtil _localizationUtil;

	@DeleteAfterTestRun
	private User _user;

	@Inject
	private UserService _userService;

}