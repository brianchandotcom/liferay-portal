/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

import com.liferay.portal.NoSuchPasswordTrackerException;
import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQueryFactoryUtil;
import com.liferay.portal.kernel.dao.orm.ProjectionFactoryUtil;
import com.liferay.portal.kernel.dao.orm.RestrictionsFactoryUtil;
import com.liferay.portal.kernel.util.Time;
import com.liferay.portal.model.PasswordTracker;
import com.liferay.portal.service.persistence.BasePersistenceTestCase;

import java.util.List;

/**
 * @author Brian Wing Shun Chan
 */
public class PasswordTrackerPersistenceTest extends BasePersistenceTestCase {
	@Override
	public void setUp() throws Exception {
		super.setUp();

		_persistence = (PasswordTrackerPersistence)PortalBeanLocatorUtil.locate(PasswordTrackerPersistence.class.getName());
	}

	public void testCreate() throws Exception {
		long pk = nextLong();

		PasswordTracker passwordTracker = _persistence.create(pk);

		assertNotNull(passwordTracker);

		assertEquals(passwordTracker.getPrimaryKey(), pk);
	}

	public void testRemove() throws Exception {
		PasswordTracker newPasswordTracker = addPasswordTracker();

		_persistence.remove(newPasswordTracker);

		PasswordTracker existingPasswordTracker = _persistence.fetchByPrimaryKey(newPasswordTracker.getPrimaryKey());

		assertNull(existingPasswordTracker);
	}

	public void testUpdateNew() throws Exception {
		addPasswordTracker();
	}

	public void testUpdateExisting() throws Exception {
		long pk = nextLong();

		PasswordTracker newPasswordTracker = _persistence.create(pk);

		newPasswordTracker.setUserId(nextLong());

		newPasswordTracker.setCreateDate(nextDate());

		newPasswordTracker.setPassword(randomString());

		_persistence.update(newPasswordTracker, false);

		PasswordTracker existingPasswordTracker = _persistence.findByPrimaryKey(newPasswordTracker.getPrimaryKey());

		assertEquals(existingPasswordTracker.getPasswordTrackerId(),
			newPasswordTracker.getPasswordTrackerId());
		assertEquals(existingPasswordTracker.getUserId(),
			newPasswordTracker.getUserId());
		assertEquals(Time.getShortTimestamp(
				existingPasswordTracker.getCreateDate()),
			Time.getShortTimestamp(newPasswordTracker.getCreateDate()));
		assertEquals(existingPasswordTracker.getPassword(),
			newPasswordTracker.getPassword());
	}

	public void testFindByPrimaryKeyExisting() throws Exception {
		PasswordTracker newPasswordTracker = addPasswordTracker();

		PasswordTracker existingPasswordTracker = _persistence.findByPrimaryKey(newPasswordTracker.getPrimaryKey());

		assertEquals(existingPasswordTracker, newPasswordTracker);
	}

	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = nextLong();

		try {
			_persistence.findByPrimaryKey(pk);

			fail("Missing entity did not throw NoSuchPasswordTrackerException");
		}
		catch (NoSuchPasswordTrackerException nsee) {
		}
	}

	public void testFetchByPrimaryKeyExisting() throws Exception {
		PasswordTracker newPasswordTracker = addPasswordTracker();

		PasswordTracker existingPasswordTracker = _persistence.fetchByPrimaryKey(newPasswordTracker.getPrimaryKey());

		assertEquals(existingPasswordTracker, newPasswordTracker);
	}

	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = nextLong();

		PasswordTracker missingPasswordTracker = _persistence.fetchByPrimaryKey(pk);

		assertNull(missingPasswordTracker);
	}

	public void testDynamicQueryByPrimaryKeyExisting()
		throws Exception {
		PasswordTracker newPasswordTracker = addPasswordTracker();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(PasswordTracker.class,
				PasswordTracker.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("passwordTrackerId",
				newPasswordTracker.getPasswordTrackerId()));

		List<PasswordTracker> result = _persistence.findWithDynamicQuery(dynamicQuery);

		assertEquals(1, result.size());

		PasswordTracker existingPasswordTracker = result.get(0);

		assertEquals(existingPasswordTracker, newPasswordTracker);
	}

	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(PasswordTracker.class,
				PasswordTracker.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("passwordTrackerId",
				nextLong()));

		List<PasswordTracker> result = _persistence.findWithDynamicQuery(dynamicQuery);

		assertEquals(0, result.size());
	}

	public void testDynamicQueryByProjectionExisting()
		throws Exception {
		PasswordTracker newPasswordTracker = addPasswordTracker();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(PasswordTracker.class,
				PasswordTracker.class.getClassLoader());

		dynamicQuery.setProjection(ProjectionFactoryUtil.property(
				"passwordTrackerId"));

		Object newPasswordTrackerId = newPasswordTracker.getPasswordTrackerId();

		dynamicQuery.add(RestrictionsFactoryUtil.in("passwordTrackerId",
				new Object[] { newPasswordTrackerId }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		assertEquals(1, result.size());

		Object existingPasswordTrackerId = result.get(0);

		assertEquals(existingPasswordTrackerId, newPasswordTrackerId);
	}

	public void testDynamicQueryByProjectionMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(PasswordTracker.class,
				PasswordTracker.class.getClassLoader());

		dynamicQuery.setProjection(ProjectionFactoryUtil.property(
				"passwordTrackerId"));

		dynamicQuery.add(RestrictionsFactoryUtil.in("passwordTrackerId",
				new Object[] { nextLong() }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		assertEquals(0, result.size());
	}

	protected PasswordTracker addPasswordTracker() throws Exception {
		long pk = nextLong();

		PasswordTracker passwordTracker = _persistence.create(pk);

		passwordTracker.setUserId(nextLong());

		passwordTracker.setCreateDate(nextDate());

		passwordTracker.setPassword(randomString());

		_persistence.update(passwordTracker, false);

		return passwordTracker;
	}

	private PasswordTrackerPersistence _persistence;
}