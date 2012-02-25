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

import com.liferay.portal.NoSuchUserTrackerPathException;
import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQueryFactoryUtil;
import com.liferay.portal.kernel.dao.orm.ProjectionFactoryUtil;
import com.liferay.portal.kernel.dao.orm.RestrictionsFactoryUtil;
import com.liferay.portal.kernel.util.Time;
import com.liferay.portal.model.UserTrackerPath;
import com.liferay.portal.service.persistence.BasePersistenceTestCase;

import java.util.List;

/**
 * @author Brian Wing Shun Chan
 */
public class UserTrackerPathPersistenceTest extends BasePersistenceTestCase {
	@Override
	public void setUp() throws Exception {
		super.setUp();

		_persistence = (UserTrackerPathPersistence)PortalBeanLocatorUtil.locate(UserTrackerPathPersistence.class.getName());
	}

	public void testCreate() throws Exception {
		long pk = nextLong();

		UserTrackerPath userTrackerPath = _persistence.create(pk);

		assertNotNull(userTrackerPath);

		assertEquals(userTrackerPath.getPrimaryKey(), pk);
	}

	public void testRemove() throws Exception {
		UserTrackerPath newUserTrackerPath = addUserTrackerPath();

		_persistence.remove(newUserTrackerPath);

		UserTrackerPath existingUserTrackerPath = _persistence.fetchByPrimaryKey(newUserTrackerPath.getPrimaryKey());

		assertNull(existingUserTrackerPath);
	}

	public void testUpdateNew() throws Exception {
		addUserTrackerPath();
	}

	public void testUpdateExisting() throws Exception {
		long pk = nextLong();

		UserTrackerPath newUserTrackerPath = _persistence.create(pk);

		newUserTrackerPath.setUserTrackerId(nextLong());

		newUserTrackerPath.setPath(randomString());

		newUserTrackerPath.setPathDate(nextDate());

		_persistence.update(newUserTrackerPath, false);

		UserTrackerPath existingUserTrackerPath = _persistence.findByPrimaryKey(newUserTrackerPath.getPrimaryKey());

		assertEquals(existingUserTrackerPath.getUserTrackerPathId(),
			newUserTrackerPath.getUserTrackerPathId());
		assertEquals(existingUserTrackerPath.getUserTrackerId(),
			newUserTrackerPath.getUserTrackerId());
		assertEquals(existingUserTrackerPath.getPath(),
			newUserTrackerPath.getPath());
		assertEquals(Time.getShortTimestamp(
				existingUserTrackerPath.getPathDate()),
			Time.getShortTimestamp(newUserTrackerPath.getPathDate()));
	}

	public void testFindByPrimaryKeyExisting() throws Exception {
		UserTrackerPath newUserTrackerPath = addUserTrackerPath();

		UserTrackerPath existingUserTrackerPath = _persistence.findByPrimaryKey(newUserTrackerPath.getPrimaryKey());

		assertEquals(existingUserTrackerPath, newUserTrackerPath);
	}

	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = nextLong();

		try {
			_persistence.findByPrimaryKey(pk);

			fail("Missing entity did not throw NoSuchUserTrackerPathException");
		}
		catch (NoSuchUserTrackerPathException nsee) {
		}
	}

	public void testFetchByPrimaryKeyExisting() throws Exception {
		UserTrackerPath newUserTrackerPath = addUserTrackerPath();

		UserTrackerPath existingUserTrackerPath = _persistence.fetchByPrimaryKey(newUserTrackerPath.getPrimaryKey());

		assertEquals(existingUserTrackerPath, newUserTrackerPath);
	}

	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = nextLong();

		UserTrackerPath missingUserTrackerPath = _persistence.fetchByPrimaryKey(pk);

		assertNull(missingUserTrackerPath);
	}

	public void testDynamicQueryByPrimaryKeyExisting()
		throws Exception {
		UserTrackerPath newUserTrackerPath = addUserTrackerPath();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(UserTrackerPath.class,
				UserTrackerPath.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("userTrackerPathId",
				newUserTrackerPath.getUserTrackerPathId()));

		List<UserTrackerPath> result = _persistence.findWithDynamicQuery(dynamicQuery);

		assertEquals(1, result.size());

		UserTrackerPath existingUserTrackerPath = result.get(0);

		assertEquals(existingUserTrackerPath, newUserTrackerPath);
	}

	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(UserTrackerPath.class,
				UserTrackerPath.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("userTrackerPathId",
				nextLong()));

		List<UserTrackerPath> result = _persistence.findWithDynamicQuery(dynamicQuery);

		assertEquals(0, result.size());
	}

	public void testDynamicQueryByProjectionExisting()
		throws Exception {
		UserTrackerPath newUserTrackerPath = addUserTrackerPath();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(UserTrackerPath.class,
				UserTrackerPath.class.getClassLoader());

		dynamicQuery.setProjection(ProjectionFactoryUtil.property(
				"userTrackerPathId"));

		Object newUserTrackerPathId = newUserTrackerPath.getUserTrackerPathId();

		dynamicQuery.add(RestrictionsFactoryUtil.in("userTrackerPathId",
				new Object[] { newUserTrackerPathId }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		assertEquals(1, result.size());

		Object existingUserTrackerPathId = result.get(0);

		assertEquals(existingUserTrackerPathId, newUserTrackerPathId);
	}

	public void testDynamicQueryByProjectionMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(UserTrackerPath.class,
				UserTrackerPath.class.getClassLoader());

		dynamicQuery.setProjection(ProjectionFactoryUtil.property(
				"userTrackerPathId"));

		dynamicQuery.add(RestrictionsFactoryUtil.in("userTrackerPathId",
				new Object[] { nextLong() }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		assertEquals(0, result.size());
	}

	protected UserTrackerPath addUserTrackerPath() throws Exception {
		long pk = nextLong();

		UserTrackerPath userTrackerPath = _persistence.create(pk);

		userTrackerPath.setUserTrackerId(nextLong());

		userTrackerPath.setPath(randomString());

		userTrackerPath.setPathDate(nextDate());

		_persistence.update(userTrackerPath, false);

		return userTrackerPath;
	}

	private UserTrackerPathPersistence _persistence;
}