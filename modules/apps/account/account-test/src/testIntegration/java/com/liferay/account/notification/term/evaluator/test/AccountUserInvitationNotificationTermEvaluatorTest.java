/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.account.notification.term.evaluator.test;

import com.liferay.account.model.AccountEntry;
import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.notification.context.NotificationContext;
import com.liferay.notification.context.NotificationContextBuilder;
import com.liferay.notification.term.evaluator.NotificationTermEvaluator;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Stefano Motta
 */
@RunWith(Arquillian.class)
public class AccountUserInvitationNotificationTermEvaluatorTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Before
	public void setUp() throws Exception {
		_user = UserTestUtil.addUser();
	}

	@Test
	public void testEvaluate() throws Exception {
		String accountName = RandomTestUtil.randomString();
		String callbackURL = RandomTestUtil.randomString();
		String emailAddress =
			"A" + RandomTestUtil.randomString() + "@liferay.com";
		String inviteSenderName = RandomTestUtil.randomString();

		NotificationContext notificationContext =
			new NotificationContextBuilder(
			).className(
				AccountEntry.class.getName()
			).termValues(
				HashMapBuilder.<String, Object>put(
					"[%ACCOUNT_NAME%]", accountName
				).put(
					"[%CREATE_ACCOUNT_URL%]", callbackURL
				).put(
					"[%INVITE_SENDER_NAME%]", inviteSenderName
				).put(
					"[%TO%]", emailAddress
				).build()
			).userId(
				_user.getUserId()
			).build();

		Assert.assertEquals(
			accountName,
			_notificationTermEvaluator.evaluate(
				NotificationTermEvaluator.Context.CONTENT, notificationContext,
				"[%ACCOUNT_NAME%]"));
		Assert.assertEquals(
			callbackURL,
			_notificationTermEvaluator.evaluate(
				NotificationTermEvaluator.Context.CONTENT, notificationContext,
				"[%CREATE_ACCOUNT_URL%]"));
		Assert.assertEquals(
			inviteSenderName,
			_notificationTermEvaluator.evaluate(
				NotificationTermEvaluator.Context.CONTENT, notificationContext,
				"[%INVITE_SENDER_NAME%]"));
		Assert.assertEquals(
			emailAddress,
			_notificationTermEvaluator.evaluate(
				NotificationTermEvaluator.Context.CONTENT, notificationContext,
				"[%TO%]"));

		String randomString = RandomTestUtil.randomString();

		Assert.assertEquals(
			"[%" + randomString + "%]",
			_notificationTermEvaluator.evaluate(
				NotificationTermEvaluator.Context.CONTENT, notificationContext,
				"[%" + randomString + "%]"));

		Assert.assertEquals(
			_user.getUserId(),
			MapUtil.getLong(
				notificationContext.getTermValues(), "currentUserId"));
	}

	@Inject(
		filter = "component.name=com.liferay.account.internal.notification.term.evaluator.AccountUserInvitationNotificationTermEvaluator"
	)
	private NotificationTermEvaluator _notificationTermEvaluator;

	@DeleteAfterTestRun
	private User _user;

}