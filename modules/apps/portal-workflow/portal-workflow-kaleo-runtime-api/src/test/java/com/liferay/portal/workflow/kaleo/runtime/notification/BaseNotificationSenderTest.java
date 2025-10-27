/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.workflow.kaleo.runtime.notification;

import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.test.rule.LiferayUnitTestRule;
import com.liferay.portal.workflow.kaleo.definition.NotificationReceptionType;
import com.liferay.portal.workflow.kaleo.definition.RecipientType;
import com.liferay.portal.workflow.kaleo.model.KaleoNotificationRecipient;
import com.liferay.portal.workflow.kaleo.runtime.ExecutionContext;
import com.liferay.portal.workflow.kaleo.runtime.notification.recipient.NotificationRecipientBuilder;
import com.liferay.portal.workflow.kaleo.runtime.notification.recipient.NotificationRecipientBuilderRegistry;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import org.mockito.Mockito;

/**
 * @author Victor Kammerer
 */
public class BaseNotificationSenderTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Before
	public void setUp() throws Exception {
		Mockito.when(
			_notificationRecipientBuilderRegistry.
				getNotificationRecipientBuilder(RecipientType.SCRIPT)
		).thenReturn(
			Mockito.mock(NotificationRecipientBuilder.class)
		);

		ReflectionTestUtil.setFieldValue(
			_baseNotificationSender, "notificationRecipientBuilderRegistry",
			_notificationRecipientBuilderRegistry);
	}

	@Test
	public void testGetNotificationRecipientsMap() throws Exception {
		ExecutionContext executionContext = Mockito.mock(
			ExecutionContext.class);

		List<KaleoNotificationRecipient> kaleoNotificationRecipients =
			new ArrayList<>();

		KaleoNotificationRecipient kaleoNotificationRecipient = Mockito.mock(
			KaleoNotificationRecipient.class);

		Mockito.when(
			kaleoNotificationRecipient.getNotificationReceptionType()
		).thenReturn(
			String.valueOf(NotificationReceptionType.TO)
		);

		Mockito.when(
			kaleoNotificationRecipient.getRecipientClassName()
		).thenReturn(
			String.valueOf(RecipientType.SCRIPT)
		);

		kaleoNotificationRecipients.add(kaleoNotificationRecipient);

		Map<NotificationReceptionType, Set<NotificationRecipient>>
			notificationRecipientsMap =
				_baseNotificationSender.getNotificationRecipientsMap(
					kaleoNotificationRecipients, executionContext);

		Set<NotificationRecipient> notificationRecipients =
			notificationRecipientsMap.get(NotificationReceptionType.TO);

		Assert.assertTrue(notificationRecipients.isEmpty());
	}

	private final BaseNotificationSender _baseNotificationSender = Mockito.spy(
		BaseNotificationSender.class);
	private final NotificationRecipientBuilderRegistry
		_notificationRecipientBuilderRegistry = Mockito.mock(
			NotificationRecipientBuilderRegistry.class);

}