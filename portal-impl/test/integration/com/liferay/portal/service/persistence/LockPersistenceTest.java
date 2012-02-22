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

import com.liferay.portal.NoSuchLockException;
import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQueryFactoryUtil;
import com.liferay.portal.kernel.dao.orm.ProjectionFactoryUtil;
import com.liferay.portal.kernel.dao.orm.RestrictionsFactoryUtil;
import com.liferay.portal.kernel.util.Time;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.Lock;
import com.liferay.portal.model.impl.LockModelImpl;
import com.liferay.portal.service.persistence.BasePersistenceTestCase;
import com.liferay.portal.util.PropsValues;

import java.util.List;

/**
 * @author Brian Wing Shun Chan
 */
public class LockPersistenceTest extends BasePersistenceTestCase {
	@Override
	public void setUp() throws Exception {
		super.setUp();

		_persistence = (LockPersistence)PortalBeanLocatorUtil.locate(LockPersistence.class.getName());
	}

	public void testCreate() throws Exception {
		long pk = nextLong();

		Lock lock = _persistence.create(pk);

		assertNotNull(lock);

		assertEquals(lock.getPrimaryKey(), pk);
	}

	public void testRemove() throws Exception {
		Lock newLock = addLock();

		_persistence.remove(newLock);

		Lock existingLock = _persistence.fetchByPrimaryKey(newLock.getPrimaryKey());

		assertNull(existingLock);
	}

	public void testUpdateNew() throws Exception {
		addLock();
	}

	public void testUpdateExisting() throws Exception {
		long pk = nextLong();

		Lock newLock = _persistence.create(pk);

		newLock.setUuid(randomString());

		newLock.setCompanyId(nextLong());

		newLock.setUserId(nextLong());

		newLock.setUserName(randomString());

		newLock.setCreateDate(nextDate());

		newLock.setClassName(randomString());

		newLock.setKey(randomString());

		newLock.setOwner(randomString());

		newLock.setInheritable(randomBoolean());

		newLock.setExpirationDate(nextDate());

		_persistence.update(newLock, false);

		Lock existingLock = _persistence.findByPrimaryKey(newLock.getPrimaryKey());

		assertEquals(existingLock.getUuid(), newLock.getUuid());
		assertEquals(existingLock.getLockId(), newLock.getLockId());
		assertEquals(existingLock.getCompanyId(), newLock.getCompanyId());
		assertEquals(existingLock.getUserId(), newLock.getUserId());
		assertEquals(existingLock.getUserName(), newLock.getUserName());
		assertEquals(Time.getShortTimestamp(existingLock.getCreateDate()),
			Time.getShortTimestamp(newLock.getCreateDate()));
		assertEquals(existingLock.getClassName(), newLock.getClassName());
		assertEquals(existingLock.getKey(), newLock.getKey());
		assertEquals(existingLock.getOwner(), newLock.getOwner());
		assertEquals(existingLock.getInheritable(), newLock.getInheritable());
		assertEquals(Time.getShortTimestamp(existingLock.getExpirationDate()),
			Time.getShortTimestamp(newLock.getExpirationDate()));
	}

	public void testFindByPrimaryKeyExisting() throws Exception {
		Lock newLock = addLock();

		Lock existingLock = _persistence.findByPrimaryKey(newLock.getPrimaryKey());

		assertEquals(existingLock, newLock);
	}

	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = nextLong();

		try {
			_persistence.findByPrimaryKey(pk);

			fail("Missing entity did not throw NoSuchLockException");
		}
		catch (NoSuchLockException nsee) {
		}
	}

	public void testFetchByPrimaryKeyExisting() throws Exception {
		Lock newLock = addLock();

		Lock existingLock = _persistence.fetchByPrimaryKey(newLock.getPrimaryKey());

		assertEquals(existingLock, newLock);
	}

	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = nextLong();

		Lock missingLock = _persistence.fetchByPrimaryKey(pk);

		assertNull(missingLock);
	}

	public void testDynamicQueryByPrimaryKeyExisting()
		throws Exception {
		Lock newLock = addLock();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(Lock.class,
				Lock.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("lockId",
				newLock.getLockId()));

		List<Lock> result = _persistence.findWithDynamicQuery(dynamicQuery);

		assertEquals(1, result.size());

		Lock existingLock = result.get(0);

		assertEquals(existingLock, newLock);
	}

	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(Lock.class,
				Lock.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("lockId", nextLong()));

		List<Lock> result = _persistence.findWithDynamicQuery(dynamicQuery);

		assertEquals(0, result.size());
	}

	public void testDynamicQueryByProjectionExisting()
		throws Exception {
		Lock newLock = addLock();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(Lock.class,
				Lock.class.getClassLoader());

		dynamicQuery.setProjection(ProjectionFactoryUtil.property("lockId"));

		Object newLockId = newLock.getLockId();

		dynamicQuery.add(RestrictionsFactoryUtil.in("lockId",
				new Object[] { newLockId }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		assertEquals(1, result.size());

		Object existingLockId = result.get(0);

		assertEquals(existingLockId, newLockId);
	}

	public void testDynamicQueryByProjectionMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(Lock.class,
				Lock.class.getClassLoader());

		dynamicQuery.setProjection(ProjectionFactoryUtil.property("lockId"));

		dynamicQuery.add(RestrictionsFactoryUtil.in("lockId",
				new Object[] { nextLong() }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		assertEquals(0, result.size());
	}

	public void testResetOriginalValues() throws Exception {
		if (!PropsValues.HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE) {
			return;
		}

		Lock newLock = addLock();

		_persistence.clearCache();

		LockModelImpl existingLockModelImpl = (LockModelImpl)_persistence.findByPrimaryKey(newLock.getPrimaryKey());

		assertTrue(Validator.equals(existingLockModelImpl.getClassName(),
				existingLockModelImpl.getOriginalClassName()));
		assertTrue(Validator.equals(existingLockModelImpl.getKey(),
				existingLockModelImpl.getOriginalKey()));

		assertTrue(Validator.equals(existingLockModelImpl.getClassName(),
				existingLockModelImpl.getOriginalClassName()));
		assertTrue(Validator.equals(existingLockModelImpl.getKey(),
				existingLockModelImpl.getOriginalKey()));
		assertTrue(Validator.equals(existingLockModelImpl.getOwner(),
				existingLockModelImpl.getOriginalOwner()));
	}

	protected Lock addLock() throws Exception {
		long pk = nextLong();

		Lock lock = _persistence.create(pk);

		lock.setUuid(randomString());

		lock.setCompanyId(nextLong());

		lock.setUserId(nextLong());

		lock.setUserName(randomString());

		lock.setCreateDate(nextDate());

		lock.setClassName(randomString());

		lock.setKey(randomString());

		lock.setOwner(randomString());

		lock.setInheritable(randomBoolean());

		lock.setExpirationDate(nextDate());

		_persistence.update(lock, false);

		return lock;
	}

	private LockPersistence _persistence;
}