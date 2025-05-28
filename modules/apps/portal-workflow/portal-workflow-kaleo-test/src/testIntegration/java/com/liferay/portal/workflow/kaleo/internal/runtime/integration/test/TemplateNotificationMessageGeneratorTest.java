/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.workflow.kaleo.internal.runtime.integration.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.petra.lang.SafeCloseable;
import com.liferay.portal.kernel.test.AssertUtils;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.PropsValuesTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;
import com.liferay.portal.workflow.kaleo.model.KaleoNode;
import com.liferay.portal.workflow.kaleo.runtime.ExecutionContext;
import com.liferay.portal.workflow.kaleo.runtime.notification.NotificationMessageGenerationException;
import com.liferay.portal.workflow.kaleo.runtime.notification.NotificationMessageGenerator;

import java.util.HashMap;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Anderson Luiz
 */
@RunWith(Arquillian.class)
public class TemplateNotificationMessageGeneratorTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE);

	@Test
	public void testGenerateMessage()
		throws NotificationMessageGenerationException {

		try (SafeCloseable safeCloseable =
				PropsValuesTestUtil.swapWithSafeCloseable(
					"NOTIFICATION_EMAIL_TEMPLATE_ENABLED", true)) {

			String message = _notificationMessageGenerator.generateMessage(
				KaleoNode.class.getName(), RandomTestUtil.randomLong(),
				RandomTestUtil.randomString(), "freemarker",
				"Hello ${serviceLocator}!",
				new ExecutionContext(null, new HashMap<>(), null));

			Assert.assertTrue(message.contains("ServiceLocator"));
		}

		try (SafeCloseable safeCloseable =
				PropsValuesTestUtil.swapWithSafeCloseable(
					"NOTIFICATION_EMAIL_TEMPLATE_ENABLED", false)) {

			AssertUtils.assertFailure(
				NotificationMessageGenerationException.class,
				"Unable to generate notification message",
				() -> _notificationMessageGenerator.generateMessage(
					KaleoNode.class.getName(), RandomTestUtil.randomLong(),
					RandomTestUtil.randomString(), "freemarker",
					"Hello ${serviceLocator}!",
					new ExecutionContext(null, new HashMap<>(), null)));
		}
	}

	@Inject
	private NotificationMessageGenerator _notificationMessageGenerator;

}