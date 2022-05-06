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

package com.liferay.notifications.admin.service.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.notifications.admin.exception.NotificationsTemplateFromException;
import com.liferay.notifications.admin.exception.NotificationsTemplateNameException;
import com.liferay.notifications.admin.model.NotificationsQueueEntry;
import com.liferay.notifications.admin.model.NotificationsTemplate;
import com.liferay.notifications.admin.service.NotificationsQueueEntryLocalService;
import com.liferay.notifications.admin.service.NotificationsTemplateLocalService;
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
public class NotificationsLocalServiceImplTest {

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
		NotificationsTemplate notificationsTemplate = _addNotificationsTemplate(
			TestPropsValues.getUserId(), TestPropsValues.getGroupId(),
			RandomTestUtil.randomString(), RandomTestUtil.randomString());

		Assert.assertNotNull(notificationsTemplate);

		NotificationsQueueEntry notificationsQueueEntry =
			_addNotificationsQueueEntry(notificationsTemplate);

		Assert.assertNotNull(notificationsQueueEntry);
		Assert.assertNotNull(
			_notificationsQueueEntryLocalService.fetchNotificationsQueueEntry(
				notificationsQueueEntry.getNotificationsQueueEntryId()));
	}

	@Test
	public void testAddNotificationsTemplate() throws Exception {
		NotificationsTemplate notificationsTemplate = _addNotificationsTemplate(
			TestPropsValues.getUserId(), TestPropsValues.getGroupId(),
			RandomTestUtil.randomString(), RandomTestUtil.randomString());

		Assert.assertNotNull(notificationsTemplate);
		Assert.assertNotNull(
			_notificationsTemplateLocalService.fetchNotificationsTemplate(
				notificationsTemplate.getNotificationsTemplateId()));
	}

	@Test
	public void testDeleteNotificationsQueueEntry() throws Exception {
		NotificationsTemplate notificationsTemplate = _addNotificationsTemplate(
			_user.getUserId(), _group.getGroupId(),
			RandomTestUtil.randomString(), RandomTestUtil.randomString());

		Assert.assertNotNull(notificationsTemplate);

		NotificationsQueueEntry notificationsQueueEntry1 =
			_addNotificationsQueueEntry(notificationsTemplate);

		_notificationsQueueEntryLocalService.deleteNotificationsQueueEntry(
			notificationsQueueEntry1);

		Assert.assertNull(
			_notificationsQueueEntryLocalService.fetchNotificationsQueueEntry(
				notificationsQueueEntry1.getNotificationsTemplateId()));

		NotificationsQueueEntry notificationsQueueEntry2 =
			_addNotificationsQueueEntry(notificationsTemplate);

		_notificationsQueueEntryLocalService.deleteNotificationsQueueEntry(
			notificationsQueueEntry2.getNotificationsQueueEntryId());

		Assert.assertNull(
			_notificationsQueueEntryLocalService.fetchNotificationsQueueEntry(
				notificationsQueueEntry2.getNotificationsTemplateId()));

		NotificationsQueueEntry notificationsQueueEntry3 =
			_addNotificationsQueueEntry(notificationsTemplate);

		_notificationsQueueEntryLocalService.deleteNotificationsQueueEntries(
			_group.getGroupId());

		Assert.assertNull(
			_notificationsQueueEntryLocalService.fetchNotificationsQueueEntry(
				notificationsQueueEntry3.getNotificationsTemplateId()));

		NotificationsQueueEntry notificationsQueueEntry4 =
			_addNotificationsQueueEntry(notificationsTemplate);

		_notificationsQueueEntryLocalService.deleteNotificationsQueueEntries(
			new Date());

		Assert.assertNull(
			_notificationsQueueEntryLocalService.fetchNotificationsQueueEntry(
				notificationsQueueEntry4.getNotificationsTemplateId()));
	}

	@Test
	public void testDeleteNotificationsTemplate() throws Exception {
		NotificationsTemplate notificationsTemplate1 =
			_addNotificationsTemplate(
				_user.getUserId(), _group.getGroupId(),
				RandomTestUtil.randomString(), RandomTestUtil.randomString());

		NotificationsTemplate notificationsTemplate2 =
			_addNotificationsTemplate(
				_user.getUserId(), _group.getGroupId(),
				RandomTestUtil.randomString(), RandomTestUtil.randomString());

		NotificationsTemplate notificationsTemplate3 =
			_addNotificationsTemplate(
				_user.getUserId(), _group.getGroupId(),
				RandomTestUtil.randomString(), RandomTestUtil.randomString());

		Assert.assertNotNull(notificationsTemplate1);
		Assert.assertNotNull(notificationsTemplate2);
		Assert.assertNotNull(notificationsTemplate3);

		_notificationsTemplateLocalService.deleteNotificationsTemplate(
			notificationsTemplate1.getNotificationsTemplateId());

		Assert.assertNull(
			_notificationsTemplateLocalService.fetchNotificationsTemplate(
				notificationsTemplate1.getNotificationsTemplateId()));

		_notificationsTemplateLocalService.deleteNotificationsTemplate(
			notificationsTemplate2);

		Assert.assertNull(
			_notificationsTemplateLocalService.fetchNotificationsTemplate(
				notificationsTemplate2.getNotificationsTemplateId()));

		_notificationsTemplateLocalService.deleteNotificationsTemplates(
			_group.getGroupId());

		Assert.assertNull(
			_notificationsTemplateLocalService.fetchNotificationsTemplate(
				notificationsTemplate3.getNotificationsTemplateId()));
	}

	@Test
	public void testExceptionNotificationsTemplate() throws Exception {
		try {
			_addNotificationsTemplate(
				_user.getUserId(), _group.getGroupId(), null,
				RandomTestUtil.randomString());
		}
		catch (NotificationsTemplateNameException
					notificationsTemplateNameException) {

			Assert.assertNotNull(notificationsTemplateNameException);
		}

		try {
			_addNotificationsTemplate(
				_user.getUserId(), _group.getGroupId(),
				RandomTestUtil.randomString(), null);
		}
		catch (NotificationsTemplateFromException
					notificationsTemplateFromException) {

			Assert.assertNotNull(notificationsTemplateFromException);
		}
	}

	@Test
	public void testGetNotificationsQueueEntry() throws Exception {
		NotificationsTemplate notificationsTemplate = _addNotificationsTemplate(
			_user.getUserId(), _group.getGroupId(),
			RandomTestUtil.randomString(), RandomTestUtil.randomString());

		Assert.assertNotNull(notificationsTemplate);

		_addNotificationsQueueEntry(notificationsTemplate);

		int count =
			_notificationsQueueEntryLocalService.
				getNotificationsQueueEntriesCount(_group.getGroupId());

		Assert.assertEquals(1, count);
	}

	@Test
	public void testGetNotificationsTemplate() throws Exception {
		NotificationsTemplate notificationsTemplate = _addNotificationsTemplate(
			_user.getUserId(), _group.getGroupId(),
			RandomTestUtil.randomString(), RandomTestUtil.randomString());

		Assert.assertNotNull(notificationsTemplate);

		int count =
			_notificationsTemplateLocalService.getNotificationsTemplatesCount(
				_group.getGroupId());

		Assert.assertEquals(1, count);
	}

	@Test
	public void testUpdateNotificationsTemplate() throws Exception {
		NotificationsTemplate notificationsTemplate = _addNotificationsTemplate(
			_user.getUserId(), _group.getGroupId(),
			RandomTestUtil.randomString(), RandomTestUtil.randomString());

		Assert.assertNotNull(notificationsTemplate);

		notificationsTemplate =
			_notificationsTemplateLocalService.updateNotificationsTemplate(
				notificationsTemplate.getNotificationsTemplateId(), "name2",
				RandomTestUtil.randomString(), RandomTestUtil.randomString(),
				RandomTestUtil.randomLocaleStringMap(),
				RandomTestUtil.randomString(), null, null, true,
				RandomTestUtil.randomLocaleStringMap(),
				RandomTestUtil.randomLocaleStringMap(), _serviceContext);

		Assert.assertEquals("name2", notificationsTemplate.getName());
	}

	private NotificationsQueueEntry _addNotificationsQueueEntry(
			NotificationsTemplate notificationsTemplate)
		throws Exception {

		return _notificationsQueueEntryLocalService.addNotificationsQueueEntry(
			_user.getUserId(), _group.getGroupId(), getClass().getName(),
			RandomTestUtil.nextLong(),
			notificationsTemplate.getNotificationsTemplateId(),
			RandomTestUtil.randomString(), null, null, null, null, null, null,
			null, 1.0);
	}

	private NotificationsTemplate _addNotificationsTemplate(
			long user, long group, String name, String from)
		throws PortalException {

		return _notificationsTemplateLocalService.addNotificationsTemplate(
			user, group, name, "description", from,
			RandomTestUtil.randomLocaleStringMap(), "to", null, null, true,
			RandomTestUtil.randomLocaleStringMap(),
			RandomTestUtil.randomLocaleStringMap(), _serviceContext);
	}

	@DeleteAfterTestRun
	private Group _group;

	@Inject
	private NotificationsQueueEntryLocalService
		_notificationsQueueEntryLocalService;

	@Inject
	private NotificationsTemplateLocalService
		_notificationsTemplateLocalService;

	private ServiceContext _serviceContext;

	@DeleteAfterTestRun
	private User _user;

}