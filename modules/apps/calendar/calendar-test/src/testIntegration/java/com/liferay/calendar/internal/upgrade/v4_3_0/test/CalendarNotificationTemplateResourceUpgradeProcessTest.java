/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.calendar.internal.upgrade.v4_3_0.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.calendar.model.CalendarNotificationTemplate;
import com.liferay.calendar.model.CalendarResource;
import com.liferay.calendar.notification.NotificationTemplateType;
import com.liferay.calendar.notification.NotificationType;
import com.liferay.calendar.service.CalendarNotificationTemplateLocalService;
import com.liferay.calendar.test.util.CalendarResourceTestUtil;
import com.liferay.portal.kernel.cache.MultiVMPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.ResourceConstants;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.ResourceLocalService;
import com.liferay.portal.kernel.service.ResourcePermissionLocalService;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.test.log.LogCapture;
import com.liferay.portal.test.log.LoggerTestUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.upgrade.registry.UpgradeStepRegistrator;
import com.liferay.portal.upgrade.test.util.UpgradeTestUtil;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestRule;
import org.junit.runner.RunWith;

/**
 * @author Feliphe Marinho
 */
@RunWith(Arquillian.class)
public class CalendarNotificationTemplateResourceUpgradeProcessTest {

	@ClassRule
	@Rule
	public static final TestRule testRule = new LiferayIntegrationTestRule();

	@Test
	public void testUpgrade() throws PortalException {
		User user = TestPropsValues.getUser();

		CalendarResource calendarResource =
			CalendarResourceTestUtil.addCalendarResource(
				_groupLocalService.getGroup(user.getGroupId()));

		CalendarNotificationTemplate calendarNotificationTemplate =
			_calendarNotificationTemplateLocalService.
				fetchCalendarNotificationTemplate(
					calendarResource.getDefaultCalendarId(),
					NotificationType.EMAIL, NotificationTemplateType.INVITE);

		_resourceLocalService.deleteResource(
			calendarNotificationTemplate, ResourceConstants.SCOPE_INDIVIDUAL);

		Assert.assertEquals(
			0,
			_resourcePermissionLocalService.getResourcePermissionsCount(
				TestPropsValues.getCompanyId(),
				CalendarNotificationTemplate.class.getName(),
				ResourceConstants.SCOPE_INDIVIDUAL,
				String.valueOf(
					calendarNotificationTemplate.
						getCalendarNotificationTemplateId())));

		try (LogCapture logCapture = LoggerTestUtil.configureLog4JLogger(
				_CLASS_NAME, LoggerTestUtil.OFF)) {

			UpgradeProcess upgradeProcess = UpgradeTestUtil.getUpgradeStep(
				_upgradeStepRegistrator, _CLASS_NAME);

			upgradeProcess.upgrade();

			_multiVMPool.clear();
		}

		Assert.assertEquals(
			1,
			_resourcePermissionLocalService.getResourcePermissionsCount(
				TestPropsValues.getCompanyId(),
				CalendarNotificationTemplate.class.getName(),
				ResourceConstants.SCOPE_INDIVIDUAL,
				String.valueOf(
					calendarNotificationTemplate.
						getCalendarNotificationTemplateId())));
	}

	private static final String _CLASS_NAME =
		"com.liferay.calendar.internal.upgrade.v4_3_0." +
			"CalendarNotificationTemplateResourceUpgradeProcess";

	@Inject
	private CalendarNotificationTemplateLocalService
		_calendarNotificationTemplateLocalService;

	@Inject
	private GroupLocalService _groupLocalService;

	@Inject
	private MultiVMPool _multiVMPool;

	@Inject
	private ResourceLocalService _resourceLocalService;

	@Inject
	private ResourcePermissionLocalService _resourcePermissionLocalService;

	@Inject(
		filter = "(&(component.name=com.liferay.calendar.internal.upgrade.registry.CalendarServiceUpgradeStepRegistrator))"
	)
	private UpgradeStepRegistrator _upgradeStepRegistrator;

}