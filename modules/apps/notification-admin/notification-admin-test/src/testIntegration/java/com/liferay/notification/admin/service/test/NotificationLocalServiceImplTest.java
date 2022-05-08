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

package com.liferay.notification.admin.service.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.notification.admin.exception.NotificationTemplateFromException;
import com.liferay.notification.admin.exception.NotificationTemplateNameException;
import com.liferay.notification.admin.model.NotificationQueueEntry;
import com.liferay.notification.admin.model.NotificationTemplate;
import com.liferay.notification.admin.service.NotificationQueueEntryLocalService;
import com.liferay.notification.admin.service.NotificationTemplateLocalService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.util.Date;

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
public class NotificationLocalServiceImplTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Before
	public void setUp() throws Exception {
		_group = GroupTestUtil.addGroup();

		_user = UserTestUtil.addUser();

		_serviceContext = ServiceContextTestUtil.getServiceContext(
			_group.getCompanyId(), _group.getGroupId(), _user.getUserId());
	}

	@Test
	public void testAddNotificationQueueEntryTemplate() throws Exception {
		NotificationTemplate notificationTemplate = _addNotificationTemplate(
			TestPropsValues.getUserId(), TestPropsValues.getGroupId(),
			RandomTestUtil.randomString(), RandomTestUtil.randomString());

		Assert.assertNotNull(notificationTemplate);

		NotificationQueueEntry notificationQueueEntry =
			_addNotificationQueueEntry(notificationTemplate);

		Assert.assertNotNull(notificationQueueEntry);
		Assert.assertNotNull(
			_notificationQueueEntryLocalService.fetchNotificationQueueEntry(
				notificationQueueEntry.getNotificationQueueEntryId()));
	}

	@Test
	public void testAddNotificationTemplate() throws Exception {
		NotificationTemplate notificationTemplate = _addNotificationTemplate(
			TestPropsValues.getUserId(), TestPropsValues.getGroupId(),
			RandomTestUtil.randomString(), RandomTestUtil.randomString());

		Assert.assertNotNull(notificationTemplate);
		Assert.assertNotNull(
			_notificationTemplateLocalService.fetchNotificationTemplate(
				notificationTemplate.getNotificationTemplateId()));
	}

	@Test
	public void testDeleteNotificationQueueEntry() throws Exception {
		NotificationTemplate notificationTemplate = _addNotificationTemplate(
			_user.getUserId(), _group.getGroupId(),
			RandomTestUtil.randomString(), RandomTestUtil.randomString());

		Assert.assertNotNull(notificationTemplate);

		NotificationQueueEntry notificationQueueEntry1 =
			_addNotificationQueueEntry(notificationTemplate);

		_notificationQueueEntryLocalService.deleteNotificationQueueEntry(
			notificationQueueEntry1);

		Assert.assertNull(
			_notificationQueueEntryLocalService.fetchNotificationQueueEntry(
				notificationQueueEntry1.getNotificationTemplateId()));

		NotificationQueueEntry notificationQueueEntry2 =
			_addNotificationQueueEntry(notificationTemplate);

		_notificationQueueEntryLocalService.deleteNotificationQueueEntry(
			notificationQueueEntry2.getNotificationQueueEntryId());

		Assert.assertNull(
			_notificationQueueEntryLocalService.fetchNotificationQueueEntry(
				notificationQueueEntry2.getNotificationTemplateId()));

		NotificationQueueEntry notificationQueueEntry3 =
			_addNotificationQueueEntry(notificationTemplate);

		_notificationQueueEntryLocalService.deleteNotificationQueueEntries(
			_group.getGroupId());

		Assert.assertNull(
			_notificationQueueEntryLocalService.fetchNotificationQueueEntry(
				notificationQueueEntry3.getNotificationTemplateId()));

		NotificationQueueEntry notificationQueueEntry4 =
			_addNotificationQueueEntry(notificationTemplate);

		_notificationQueueEntryLocalService.deleteNotificationQueueEntries(
			new Date());

		Assert.assertNull(
			_notificationQueueEntryLocalService.fetchNotificationQueueEntry(
				notificationQueueEntry4.getNotificationTemplateId()));
	}

	@Test
	public void testDeleteNotificationTemplate() throws Exception {
		NotificationTemplate notificationTemplate1 = _addNotificationTemplate(
			_user.getUserId(), _group.getGroupId(),
			RandomTestUtil.randomString(), RandomTestUtil.randomString());

		NotificationTemplate notificationTemplate2 = _addNotificationTemplate(
			_user.getUserId(), _group.getGroupId(),
			RandomTestUtil.randomString(), RandomTestUtil.randomString());

		NotificationTemplate notificationTemplate3 = _addNotificationTemplate(
			_user.getUserId(), _group.getGroupId(),
			RandomTestUtil.randomString(), RandomTestUtil.randomString());

		Assert.assertNotNull(notificationTemplate1);
		Assert.assertNotNull(notificationTemplate2);
		Assert.assertNotNull(notificationTemplate3);

		_notificationTemplateLocalService.deleteNotificationTemplate(
			notificationTemplate1.getNotificationTemplateId());

		Assert.assertNull(
			_notificationTemplateLocalService.fetchNotificationTemplate(
				notificationTemplate1.getNotificationTemplateId()));

		_notificationTemplateLocalService.deleteNotificationTemplate(
			notificationTemplate2);

		Assert.assertNull(
			_notificationTemplateLocalService.fetchNotificationTemplate(
				notificationTemplate2.getNotificationTemplateId()));

		_notificationTemplateLocalService.deleteNotificationTemplates(
			_group.getGroupId());

		Assert.assertNull(
			_notificationTemplateLocalService.fetchNotificationTemplate(
				notificationTemplate3.getNotificationTemplateId()));
	}

	@Test
	public void testExceptionNotificationTemplate() throws Exception {
		try {
			_addNotificationTemplate(
				_user.getUserId(), _group.getGroupId(), null,
				RandomTestUtil.randomString());
		}
		catch (NotificationTemplateNameException
					notificationTemplateNameException) {

			Assert.assertNotNull(notificationTemplateNameException);
		}

		try {
			_addNotificationTemplate(
				_user.getUserId(), _group.getGroupId(),
				RandomTestUtil.randomString(), null);
		}
		catch (NotificationTemplateFromException
					notificationTemplateFromException) {

			Assert.assertNotNull(notificationTemplateFromException);
		}
	}

	@Test
	public void testGetNotificationQueueEntry() throws Exception {
		NotificationTemplate notificationTemplate = _addNotificationTemplate(
			_user.getUserId(), _group.getGroupId(),
			RandomTestUtil.randomString(), RandomTestUtil.randomString());

		Assert.assertNotNull(notificationTemplate);

		_addNotificationQueueEntry(notificationTemplate);

		int count =
			_notificationQueueEntryLocalService.
				getNotificationQueueEntriesCount(_group.getGroupId());

		Assert.assertEquals(1, count);
	}

	@Test
	public void testGetNotificationTemplate() throws Exception {
		NotificationTemplate notificationTemplate = _addNotificationTemplate(
			_user.getUserId(), _group.getGroupId(),
			RandomTestUtil.randomString(), RandomTestUtil.randomString());

		Assert.assertNotNull(notificationTemplate);

		int count =
			_notificationTemplateLocalService.getNotificationTemplatesCount(
				_group.getGroupId());

		Assert.assertEquals(1, count);
	}

	@Test
	public void testUpdateNotificationTemplate() throws Exception {
		NotificationTemplate notificationTemplate = _addNotificationTemplate(
			_user.getUserId(), _group.getGroupId(),
			RandomTestUtil.randomString(), RandomTestUtil.randomString());

		Assert.assertNotNull(notificationTemplate);

		notificationTemplate =
			_notificationTemplateLocalService.updateNotificationTemplate(
				notificationTemplate.getNotificationTemplateId(), "name2",
				RandomTestUtil.randomString(), RandomTestUtil.randomString(),
				RandomTestUtil.randomLocaleStringMap(),
				RandomTestUtil.randomString(), null, null, true,
				RandomTestUtil.randomLocaleStringMap(),
				RandomTestUtil.randomLocaleStringMap(), _serviceContext);

		Assert.assertEquals("name2", notificationTemplate.getName());
	}

	private NotificationQueueEntry _addNotificationQueueEntry(
			NotificationTemplate notificationTemplate)
		throws Exception {

		return _notificationQueueEntryLocalService.addNotificationQueueEntry(
			_user.getUserId(), _group.getGroupId(), getClass().getName(),
			RandomTestUtil.nextLong(),
			notificationTemplate.getNotificationTemplateId(),
			RandomTestUtil.randomString(), null, null, null, null, null, null,
			null, 1.0);
	}

	private NotificationTemplate _addNotificationTemplate(
			long user, long group, String name, String from)
		throws PortalException {

		return _notificationTemplateLocalService.addNotificationTemplate(
			user, group, name, "description", from,
			RandomTestUtil.randomLocaleStringMap(), "to", null, null, true,
			RandomTestUtil.randomLocaleStringMap(),
			RandomTestUtil.randomLocaleStringMap(), _serviceContext);
	}

	@DeleteAfterTestRun
	private Group _group;

	@Inject
	private NotificationQueueEntryLocalService
		_notificationQueueEntryLocalService;

	@Inject
	private NotificationTemplateLocalService _notificationTemplateLocalService;

	private ServiceContext _serviceContext;

	@DeleteAfterTestRun
	private User _user;

}