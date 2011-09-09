/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.service.persistence;

import com.liferay.portal.NoSuchUserNotificationEventException;
import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQueryFactoryUtil;
import com.liferay.portal.kernel.dao.orm.ProjectionFactoryUtil;
import com.liferay.portal.kernel.dao.orm.RestrictionsFactoryUtil;
import com.liferay.portal.model.UserNotificationEvent;
import com.liferay.portal.service.persistence.BasePersistenceTestCase;

import java.util.List;

/**
 * @author Brian Wing Shun Chan
 */
public class UserNotificationEventPersistenceTest
	extends BasePersistenceTestCase {
	@Override
	public void setUp() throws Exception {
		super.setUp();

		_persistence = (UserNotificationEventPersistence)PortalBeanLocatorUtil.locate(UserNotificationEventPersistence.class.getName());
	}

	public void testCreate() throws Exception {
		long pk = nextLong();

		UserNotificationEvent userNotificationEvent = _persistence.create(pk);

		assertNotNull(userNotificationEvent);

		assertEquals(userNotificationEvent.getPrimaryKey(), pk);
	}

	public void testRemove() throws Exception {
		UserNotificationEvent newUserNotificationEvent = addUserNotificationEvent();

		_persistence.remove(newUserNotificationEvent);

		UserNotificationEvent existingUserNotificationEvent = _persistence.fetchByPrimaryKey(newUserNotificationEvent.getPrimaryKey());

		assertNull(existingUserNotificationEvent);
	}

	public void testUpdateNew() throws Exception {
		addUserNotificationEvent();
	}

	public void testUpdateExisting() throws Exception {
		long pk = nextLong();

		UserNotificationEvent newUserNotificationEvent = _persistence.create(pk);

		newUserNotificationEvent.setUuid(randomString());

		newUserNotificationEvent.setCompanyId(nextLong());

		newUserNotificationEvent.setUserId(nextLong());

		newUserNotificationEvent.setType(randomString());

		newUserNotificationEvent.setTimestamp(nextLong());

		newUserNotificationEvent.setDeliverBy(nextLong());

		newUserNotificationEvent.setPayload(randomString());

		newUserNotificationEvent.setArchived(randomBoolean());

		_persistence.update(newUserNotificationEvent, false);

		UserNotificationEvent existingUserNotificationEvent = _persistence.findByPrimaryKey(newUserNotificationEvent.getPrimaryKey());

		assertEquals(existingUserNotificationEvent.getUuid(),
			newUserNotificationEvent.getUuid());
		assertEquals(existingUserNotificationEvent.getUserNotificationEventId(),
			newUserNotificationEvent.getUserNotificationEventId());
		assertEquals(existingUserNotificationEvent.getCompanyId(),
			newUserNotificationEvent.getCompanyId());
		assertEquals(existingUserNotificationEvent.getUserId(),
			newUserNotificationEvent.getUserId());
		assertEquals(existingUserNotificationEvent.getType(),
			newUserNotificationEvent.getType());
		assertEquals(existingUserNotificationEvent.getTimestamp(),
			newUserNotificationEvent.getTimestamp());
		assertEquals(existingUserNotificationEvent.getDeliverBy(),
			newUserNotificationEvent.getDeliverBy());
		assertEquals(existingUserNotificationEvent.getPayload(),
			newUserNotificationEvent.getPayload());
		assertEquals(existingUserNotificationEvent.getArchived(),
			newUserNotificationEvent.getArchived());
	}

	public void testFindByPrimaryKeyExisting() throws Exception {
		UserNotificationEvent newUserNotificationEvent = addUserNotificationEvent();

		UserNotificationEvent existingUserNotificationEvent = _persistence.findByPrimaryKey(newUserNotificationEvent.getPrimaryKey());

		assertEquals(existingUserNotificationEvent, newUserNotificationEvent);
	}

	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = nextLong();

		try {
			_persistence.findByPrimaryKey(pk);

			fail(
				"Missing entity did not throw NoSuchUserNotificationEventException");
		}
		catch (NoSuchUserNotificationEventException nsee) {
		}
	}

	public void testFetchByPrimaryKeyExisting() throws Exception {
		UserNotificationEvent newUserNotificationEvent = addUserNotificationEvent();

		UserNotificationEvent existingUserNotificationEvent = _persistence.fetchByPrimaryKey(newUserNotificationEvent.getPrimaryKey());

		assertEquals(existingUserNotificationEvent, newUserNotificationEvent);
	}

	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = nextLong();

		UserNotificationEvent missingUserNotificationEvent = _persistence.fetchByPrimaryKey(pk);

		assertNull(missingUserNotificationEvent);
	}

	public void testDynamicQueryByPrimaryKeyExisting()
		throws Exception {
		UserNotificationEvent newUserNotificationEvent = addUserNotificationEvent();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(UserNotificationEvent.class,
				UserNotificationEvent.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("userNotificationEventId",
				newUserNotificationEvent.getUserNotificationEventId()));

		List<UserNotificationEvent> result = _persistence.findWithDynamicQuery(dynamicQuery);

		assertEquals(1, result.size());

		UserNotificationEvent existingUserNotificationEvent = result.get(0);

		assertEquals(existingUserNotificationEvent, newUserNotificationEvent);
	}

	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(UserNotificationEvent.class,
				UserNotificationEvent.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("userNotificationEventId",
				nextLong()));

		List<UserNotificationEvent> result = _persistence.findWithDynamicQuery(dynamicQuery);

		assertEquals(0, result.size());
	}

	public void testDynamicQueryByProjectionExisting()
		throws Exception {
		UserNotificationEvent newUserNotificationEvent = addUserNotificationEvent();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(UserNotificationEvent.class,
				UserNotificationEvent.class.getClassLoader());

		dynamicQuery.setProjection(ProjectionFactoryUtil.property(
				"userNotificationEventId"));

		Object newUserNotificationEventId = newUserNotificationEvent.getUserNotificationEventId();

		dynamicQuery.add(RestrictionsFactoryUtil.in("userNotificationEventId",
				new Object[] { newUserNotificationEventId }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		assertEquals(1, result.size());

		Object existingUserNotificationEventId = result.get(0);

		assertEquals(existingUserNotificationEventId, newUserNotificationEventId);
	}

	public void testDynamicQueryByProjectionMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(UserNotificationEvent.class,
				UserNotificationEvent.class.getClassLoader());

		dynamicQuery.setProjection(ProjectionFactoryUtil.property(
				"userNotificationEventId"));

		dynamicQuery.add(RestrictionsFactoryUtil.in("userNotificationEventId",
				new Object[] { nextLong() }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		assertEquals(0, result.size());
	}

	protected UserNotificationEvent addUserNotificationEvent()
		throws Exception {
		long pk = nextLong();

		UserNotificationEvent userNotificationEvent = _persistence.create(pk);

		userNotificationEvent.setUuid(randomString());

		userNotificationEvent.setCompanyId(nextLong());

		userNotificationEvent.setUserId(nextLong());

		userNotificationEvent.setType(randomString());

		userNotificationEvent.setTimestamp(nextLong());

		userNotificationEvent.setDeliverBy(nextLong());

		userNotificationEvent.setPayload(randomString());

		userNotificationEvent.setArchived(randomBoolean());

		_persistence.update(userNotificationEvent, false);

		return userNotificationEvent;
	}

	private UserNotificationEventPersistence _persistence;
}