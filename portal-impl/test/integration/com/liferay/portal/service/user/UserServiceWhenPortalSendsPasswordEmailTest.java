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

package com.liferay.portal.service.user;

import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.UserServiceUtil;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.test.mail.MailServiceTestUtil;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.SynchronousMailTestRule;
import com.liferay.portal.util.PrefsPropsUtil;

import javax.portlet.PortletPreferences;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Brian Wing Shun Chan
 * @author José Manuel Navarro
 * @author Drew Brokke
 */
public class UserServiceWhenPortalSendsPasswordEmailTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(), SynchronousMailTestRule.INSTANCE);

	@Before
	public void setUp() throws Exception {
		_user = UserTestUtil.addUser();
	}

	@Test
	public void shouldSendNewPasswordEmailByEmailAddress() throws Exception {
		PortletPreferences portletPreferences =
			givenThatCompanySendsNewPassword();

		try {
			int initialInboxSize = MailServiceTestUtil.getInboxSize();

			boolean sentPassword = UserServiceUtil.sendPasswordByEmailAddress(
				_user.getCompanyId(), _user.getEmailAddress());

			Assert.assertTrue(sentPassword);

			Assert.assertEquals(
				initialInboxSize + 1, MailServiceTestUtil.getInboxSize());
			Assert.assertTrue(
				MailServiceTestUtil.lastMailMessageContains(
					"email_password_sent_body.tmpl"));
		}
		finally {
			restorePortletPreferences(portletPreferences);
		}
	}

	@Test
	public void shouldSendNewPasswordEmailByScreenName() throws Exception {
		PortletPreferences portletPreferences =
			givenThatCompanySendsNewPassword();

		try {
			int initialInboxSize = MailServiceTestUtil.getInboxSize();

			boolean sentPassword = UserServiceUtil.sendPasswordByScreenName(
				_user.getCompanyId(), _user.getScreenName());

			Assert.assertTrue(sentPassword);

			Assert.assertEquals(
				initialInboxSize + 1, MailServiceTestUtil.getInboxSize());
			Assert.assertTrue(
				MailServiceTestUtil.lastMailMessageContains(
					"email_password_sent_body.tmpl"));
		}
		finally {
			restorePortletPreferences(portletPreferences);
		}
	}

	@Test
	public void shouldSendNewPasswordEmailByUserId() throws Exception {
		PortletPreferences portletPreferences =
			givenThatCompanySendsNewPassword();

		try {
			int initialInboxSize = MailServiceTestUtil.getInboxSize();

			boolean sentPassword = UserServiceUtil.sendPasswordByUserId(
				_user.getUserId());

			Assert.assertTrue(sentPassword);

			Assert.assertEquals(
				initialInboxSize + 1, MailServiceTestUtil.getInboxSize());
			Assert.assertTrue(
				MailServiceTestUtil.lastMailMessageContains(
					"email_password_sent_body.tmpl"));
		}
		finally {
			restorePortletPreferences(portletPreferences);
		}
	}

	@Test
	public void shouldSendResetLinkEmailByEmailAddress() throws Exception {
		PortletPreferences portletPreferences =
			givenThatCompanySendsResetPasswordLink();

		try {
			int initialInboxSize = MailServiceTestUtil.getInboxSize();

			boolean sentPassword = UserServiceUtil.sendPasswordByEmailAddress(
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
	public void shouldSendResetLinkEmailByScreenName() throws Exception {
		PortletPreferences portletPreferences =
			givenThatCompanySendsResetPasswordLink();

		try {
			int initialInboxSize = MailServiceTestUtil.getInboxSize();

			boolean sentPassword = UserServiceUtil.sendPasswordByScreenName(
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
	public void shouldSendResetLinkEmailByUserId() throws Exception {
		PortletPreferences portletPreferences =
			givenThatCompanySendsResetPasswordLink();

		try {
			int initialInboxSize = MailServiceTestUtil.getInboxSize();

			boolean sentPassword = UserServiceUtil.sendPasswordByUserId(
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

	protected PortletPreferences givenThatCompanySendsNewPassword()
		throws Exception {

		PortletPreferences portletPreferences = PrefsPropsUtil.getPreferences(
			_user.getCompanyId(), false);

		portletPreferences.setValue(
			PropsKeys.COMPANY_SECURITY_SEND_PASSWORD, Boolean.TRUE.toString());

		portletPreferences.setValue(
			PropsKeys.COMPANY_SECURITY_SEND_PASSWORD_RESET_LINK,
			Boolean.FALSE.toString());

		portletPreferences.store();

		return portletPreferences;
	}

	protected PortletPreferences givenThatCompanySendsResetPasswordLink()
		throws Exception {

		PortletPreferences portletPreferences = PrefsPropsUtil.getPreferences(
			_user.getCompanyId(), false);

		portletPreferences.setValue(
			PropsKeys.COMPANY_SECURITY_SEND_PASSWORD, Boolean.FALSE.toString());
		portletPreferences.setValue(
			PropsKeys.COMPANY_SECURITY_SEND_PASSWORD_RESET_LINK,
			Boolean.TRUE.toString());

		portletPreferences.store();

		return portletPreferences;
	}

	protected void restorePortletPreferences(
			PortletPreferences portletPreferences)
		throws Exception {

		portletPreferences.reset(PropsKeys.COMPANY_SECURITY_SEND_PASSWORD);
		portletPreferences.reset(
			PropsKeys.COMPANY_SECURITY_SEND_PASSWORD_RESET_LINK);

		portletPreferences.store();
	}

	@DeleteAfterTestRun
	private User _user;

}