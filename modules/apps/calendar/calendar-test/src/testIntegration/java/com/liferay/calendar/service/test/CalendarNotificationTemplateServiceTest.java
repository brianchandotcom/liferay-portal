/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.calendar.service.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.calendar.model.CalendarNotificationTemplate;
import com.liferay.calendar.model.CalendarResource;
import com.liferay.calendar.notification.NotificationTemplateType;
import com.liferay.calendar.notification.NotificationType;
import com.liferay.calendar.service.CalendarNotificationTemplateLocalService;
import com.liferay.calendar.service.CalendarNotificationTemplateService;
import com.liferay.calendar.test.util.CalendarResourceTestUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.model.ResourceConstants;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.model.role.RoleConstants;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.security.auth.PrincipalThreadLocal;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.PermissionCheckerFactoryUtil;
import com.liferay.portal.kernel.security.permission.PermissionThreadLocal;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.ResourcePermissionLocalService;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.test.AssertUtils;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.RoleTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Feliphe Marinho
 */
@RunWith(Arquillian.class)
public class CalendarNotificationTemplateServiceTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE);

	@Test
	public void testUpdateCalendarNotificationTemplate() throws Exception {
		User user = UserTestUtil.addUser();

		PermissionThreadLocal.setPermissionChecker(
			PermissionCheckerFactoryUtil.create(user));

		PrincipalThreadLocal.setName(user.getUserId());

		CalendarResource calendarResource =
			CalendarResourceTestUtil.addCalendarResource(
				_groupLocalService.getGroup(user.getGroupId()));

		CalendarNotificationTemplate calendarNotificationTemplate =
			_calendarNotificationTemplateLocalService.
				fetchCalendarNotificationTemplate(
					calendarResource.getDefaultCalendarId(),
					NotificationType.EMAIL, NotificationTemplateType.INVITE);

		AssertUtils.assertFailure(
			PrincipalException.MustHavePermission.class,
			StringBundler.concat(
				"User ", user.getUserId(), " must have UPDATE permission for ",
				CalendarNotificationTemplate.class.getName(), StringPool.SPACE,
				calendarNotificationTemplate.
					getCalendarNotificationTemplateId()),
			() ->
				_calendarNotificationTemplateService.
					updateCalendarNotificationTemplate(
						calendarNotificationTemplate.
							getCalendarNotificationTemplateId(),
						calendarNotificationTemplate.
							getNotificationTypeSettings(),
						calendarNotificationTemplate.getSubject(),
						calendarNotificationTemplate.getBody(),
						ServiceContextTestUtil.getServiceContext()));

		Role role = RoleTestUtil.addRole(RoleConstants.TYPE_REGULAR);

		_resourcePermissionLocalService.setResourcePermissions(
			TestPropsValues.getCompanyId(),
			CalendarNotificationTemplate.class.getName(),
			ResourceConstants.SCOPE_COMPANY,
			String.valueOf(TestPropsValues.getCompanyId()), role.getRoleId(),
			new String[] {ActionKeys.UPDATE});

		_userLocalService.addRoleUser(role.getRoleId(), user);

		Assert.assertNotNull(
			_calendarNotificationTemplateService.
				updateCalendarNotificationTemplate(
					calendarNotificationTemplate.
						getCalendarNotificationTemplateId(),
					calendarNotificationTemplate.getNotificationTypeSettings(),
					calendarNotificationTemplate.getSubject(),
					calendarNotificationTemplate.getBody(),
					ServiceContextTestUtil.getServiceContext()));
	}

	@Inject
	private CalendarNotificationTemplateLocalService
		_calendarNotificationTemplateLocalService;

	@Inject
	private CalendarNotificationTemplateService
		_calendarNotificationTemplateService;

	@Inject
	private GroupLocalService _groupLocalService;

	@Inject
	private ResourcePermissionLocalService _resourcePermissionLocalService;

	@Inject
	private UserLocalService _userLocalService;

}