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

package com.liferay.notification.service.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.counter.kernel.service.CounterLocalService;
import com.liferay.notification.constants.NotificationConstants;
import com.liferay.notification.constants.NotificationTemplateConstants;
import com.liferay.notification.context.NotificationContext;
import com.liferay.notification.model.NotificationRecipient;
import com.liferay.notification.model.NotificationRecipientSetting;
import com.liferay.notification.model.NotificationTemplate;
import com.liferay.notification.service.NotificationRecipientLocalService;
import com.liferay.notification.service.NotificationRecipientSettingLocalService;
import com.liferay.notification.service.NotificationTemplateLocalService;
import com.liferay.notification.service.NotificationTemplateService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.security.auth.PrincipalThreadLocal;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.PermissionCheckerFactoryUtil;
import com.liferay.portal.kernel.security.permission.PermissionThreadLocal;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.util.Arrays;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Gustavo Lima
 */
@RunWith(Arquillian.class)
public class NotificationTemplateServiceTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Before
	public void setUp() throws Exception {
		_adminUser = TestPropsValues.getUser();
		_originalName = PrincipalThreadLocal.getName();
		_originalPermissionChecker =
			PermissionThreadLocal.getPermissionChecker();
		_user = UserTestUtil.addUser();
	}

	@After
	public void tearDown() {
		PermissionThreadLocal.setPermissionChecker(_originalPermissionChecker);

		PrincipalThreadLocal.setName(_originalName);
	}

	@Test
	public void testAddNotificationTemplate() throws Exception {
		try {
			_testAddNotificationTemplate(_user);

			Assert.fail();
		}
		catch (PrincipalException.MustHavePermission principalException) {
			String message = principalException.getMessage();

			Assert.assertTrue(
				message.contains(
					"User " + _user.getUserId() +
						" must have ADD_NOTIFICATION_TEMPLATE permission for"));
		}

		_testAddNotificationTemplate(_adminUser);
	}

	@Test
	public void testDeleteNotificationTemplate() throws Exception {
		try {
			_testDeleteNotificationTemplate(_adminUser, _user);

			Assert.fail();
		}
		catch (PrincipalException.MustHavePermission principalException) {
			String message = principalException.getMessage();

			Assert.assertTrue(
				message.contains(
					"User " + _user.getUserId() +
						" must have DELETE permission for"));
		}

		_testDeleteNotificationTemplate(_adminUser, _adminUser);
		_testDeleteNotificationTemplate(_user, _user);
	}

	@Test
	public void testGetListNotificationTemplate() throws Exception {
		try {
			_testGetNotificationTemplate(_adminUser, _user);

			Assert.fail();
		}
		catch (PrincipalException.MustHavePermission principalException) {
			String message = principalException.getMessage();

			Assert.assertTrue(
				message.contains(
					"User " + _user.getUserId() +
						" must have VIEW permission for"));
		}

		_testGetNotificationTemplate(_adminUser, _adminUser);
		_testGetNotificationTemplate(_user, _user);
	}

	@Test
	public void testUpdateNotificationTemplate() throws Exception {
		try {
			_testUpdateNotificationTemplate(_adminUser, _user);

			Assert.fail();
		}
		catch (PrincipalException.MustHavePermission principalException) {
			String message = principalException.getMessage();

			Assert.assertTrue(
				message.contains(
					"User " + _user.getUserId() +
						" must have UPDATE permission for"));
		}

		_testUpdateNotificationTemplate(_adminUser, _adminUser);
		_testUpdateNotificationTemplate(_user, _user);
	}

	private NotificationContext _createNotificationContext(User user)
		throws PortalException {

		NotificationContext notificationContext = new NotificationContext();

		NotificationTemplate notificationTemplate =
			_notificationTemplateLocalService.createNotificationTemplate(
				RandomTestUtil.randomInt());

		notificationTemplate.setUserId(user.getUserId());
		notificationTemplate.setEditorType(
			NotificationTemplateConstants.EDITOR_TYPE_RICH_TEXT);
		notificationTemplate.setName(RandomTestUtil.randomString());
		notificationTemplate.setType(NotificationConstants.TYPE_EMAIL);

		notificationContext.setNotificationTemplate(notificationTemplate);

		NotificationRecipient notificationRecipient =
			_notificationRecipientLocalService.createNotificationRecipient(
				_counterLocalService.increment());

		notificationRecipient.setClassName(
			NotificationTemplate.class.getName());
		notificationRecipient.setClassPK(
			notificationTemplate.getNotificationTemplateId());

		notificationContext.setNotificationRecipient(notificationRecipient);

		NotificationRecipientSetting notificationRecipientSetting =
			_notificationRecipientSettingLocalService.
				createNotificationRecipientSetting(
					_counterLocalService.increment());

		notificationRecipientSetting.setNotificationRecipientId(
			notificationRecipient.getNotificationRecipientId());

		notificationContext.setNotificationRecipientSettings(
			Arrays.asList(notificationRecipientSetting));

		notificationContext.setType(
			NotificationConstants.TYPE_USER_NOTIFICATION);

		return notificationContext;
	}

	private void _setUser(User user) {
		PermissionThreadLocal.setPermissionChecker(
			PermissionCheckerFactoryUtil.create(user));

		PrincipalThreadLocal.setName(user.getUserId());
	}

	private void _testAddNotificationTemplate(User user) throws Exception {
		NotificationTemplate notificationTemplate = null;

		try {
			_setUser(user);

			notificationTemplate =
				_notificationTemplateService.addNotificationTemplate(
					_createNotificationContext(user));
		}
		finally {
			if ((notificationTemplate != null) &&
				!notificationTemplate.isNew()) {

				_notificationTemplateLocalService.deleteNotificationTemplate(
					notificationTemplate);
			}
		}
	}

	private void _testDeleteNotificationTemplate(User ownerUser, User user)
		throws Exception {

		NotificationTemplate deleteNotificationTemplate = null;
		NotificationTemplate notificationTemplate = null;

		try {
			_setUser(user);

			notificationTemplate =
				_notificationTemplateLocalService.addNotificationTemplate(
					_createNotificationContext(ownerUser));

			deleteNotificationTemplate =
				_notificationTemplateService.deleteNotificationTemplate(
					notificationTemplate.getNotificationTemplateId());
		}
		finally {
			if (deleteNotificationTemplate == null) {
				_notificationTemplateService.deleteNotificationTemplate(
					notificationTemplate);
			}
		}
	}

	private void _testGetNotificationTemplate(User ownerUser, User user)
		throws Exception {

		NotificationTemplate notificationTemplate = null;

		try {
			_setUser(user);

			notificationTemplate =
				_notificationTemplateLocalService.addNotificationTemplate(
					_createNotificationContext(ownerUser));

			_notificationTemplateService.getNotificationTemplate(
				notificationTemplate.getNotificationTemplateId());
		}
		finally {
			if (notificationTemplate != null) {
				_notificationTemplateLocalService.deleteNotificationTemplate(
					notificationTemplate);
			}
		}
	}

	private void _testUpdateNotificationTemplate(User ownerUser, User user)
		throws Exception {

		NotificationTemplate notificationTemplate = null;

		try {
			_setUser(user);

			NotificationContext notificationContext =
				_createNotificationContext(ownerUser);

			notificationTemplate =
				_notificationTemplateLocalService.addNotificationTemplate(
					notificationContext);

			notificationContext.setNotificationTemplate(notificationTemplate);

			notificationTemplate =
				_notificationTemplateService.updateNotificationTemplate(
					notificationContext);
		}
		finally {
			if (notificationTemplate != null) {
				_notificationTemplateLocalService.deleteNotificationTemplate(
					notificationTemplate);
			}
		}
	}

	private User _adminUser;

	@Inject
	private CounterLocalService _counterLocalService;

	@Inject
	private NotificationRecipientLocalService
		_notificationRecipientLocalService;

	@Inject
	private NotificationRecipientSettingLocalService
		_notificationRecipientSettingLocalService;

	@Inject
	private NotificationTemplateLocalService _notificationTemplateLocalService;

	@Inject
	private NotificationTemplateService _notificationTemplateService;

	private String _originalName;
	private PermissionChecker _originalPermissionChecker;
	private User _user;

}