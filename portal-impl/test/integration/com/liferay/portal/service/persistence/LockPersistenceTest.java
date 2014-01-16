/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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
import com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQueryFactoryUtil;
import com.liferay.portal.kernel.dao.orm.ProjectionFactoryUtil;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.RestrictionsFactoryUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.test.ExecutionTestListeners;
import com.liferay.portal.kernel.util.IntegerWrapper;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.OrderByComparatorFactoryUtil;
import com.liferay.portal.kernel.util.Time;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.Lock;
import com.liferay.portal.model.impl.LockModelImpl;
import com.liferay.portal.service.ServiceTestUtil;
import com.liferay.portal.service.persistence.BasePersistence;
import com.liferay.portal.service.persistence.PersistenceExecutionTestListener;
import com.liferay.portal.test.LiferayPersistenceIntegrationJUnitTestRunner;
import com.liferay.portal.test.persistence.TransactionalPersistenceAdvice;
import com.liferay.portal.util.PropsValues;

import org.junit.After;
import org.junit.Assert;
import org.junit.Test;

import org.junit.runner.RunWith;

import java.io.Serializable;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author Brian Wing Shun Chan
 */
@ExecutionTestListeners(listeners =  {
	PersistenceExecutionTestListener.class})
@RunWith(LiferayPersistenceIntegrationJUnitTestRunner.class)
public class LockPersistenceTest {
	@After
	public void tearDown() throws Exception {
		Map<Serializable, BasePersistence<?>> basePersistences = _transactionalPersistenceAdvice.getBasePersistences();

		Set<Serializable> primaryKeys = basePersistences.keySet();

		for (Serializable primaryKey : primaryKeys) {
			BasePersistence<?> basePersistence = basePersistences.get(primaryKey);

			try {
				basePersistence.remove(primaryKey);
			}
			catch (Exception e) {
				if (_log.isDebugEnabled()) {
					_log.debug("The model with primary key " + primaryKey +
						" was already deleted");
				}
			}
		}

		_transactionalPersistenceAdvice.reset();
	}

	@Test
	public void testCreate() throws Exception {
		long pk = ServiceTestUtil.nextLong();

		Lock lock = _persistence.create(pk);

		Assert.assertNotNull(lock);

		Assert.assertEquals(lock.getPrimaryKey(), pk);
	}

	@Test
	public void testRemove() throws Exception {
		Lock newLock = addLock();

		_persistence.remove(newLock);

		Lock existingLock = _persistence.fetchByPrimaryKey(newLock.getPrimaryKey());

		Assert.assertNull(existingLock);
	}

	@Test
	public void testUpdateNew() throws Exception {
		addLock();
	}

	@Test
	public void testUpdateExisting() throws Exception {
		long pk = ServiceTestUtil.nextLong();

		Lock newLock = _persistence.create(pk);

		newLock.setUuid(ServiceTestUtil.randomString());

		newLock.setCompanyId(ServiceTestUtil.nextLong());

		newLock.setUserId(ServiceTestUtil.nextLong());

		newLock.setUserName(ServiceTestUtil.randomString());

		newLock.setCreateDate(ServiceTestUtil.nextDate());

		newLock.setClassName(ServiceTestUtil.randomString());

		newLock.setKey(ServiceTestUtil.randomString());

		newLock.setOwner(ServiceTestUtil.randomString());

		newLock.setInheritable(ServiceTestUtil.randomBoolean());

		newLock.setExpirationDate(ServiceTestUtil.nextDate());

		newLock.setMvccVersion(ServiceTestUtil.nextLong());

		_persistence.update(newLock);

		Lock existingLock = _persistence.findByPrimaryKey(newLock.getPrimaryKey());

		Assert.assertEquals(existingLock.getUuid(), newLock.getUuid());
		Assert.assertEquals(existingLock.getLockId(), newLock.getLockId());
		Assert.assertEquals(existingLock.getCompanyId(), newLock.getCompanyId());
		Assert.assertEquals(existingLock.getUserId(), newLock.getUserId());
		Assert.assertEquals(existingLock.getUserName(), newLock.getUserName());
		Assert.assertEquals(Time.getShortTimestamp(existingLock.getCreateDate()),
			Time.getShortTimestamp(newLock.getCreateDate()));
		Assert.assertEquals(existingLock.getClassName(), newLock.getClassName());
		Assert.assertEquals(existingLock.getKey(), newLock.getKey());
		Assert.assertEquals(existingLock.getOwner(), newLock.getOwner());
		Assert.assertEquals(existingLock.getInheritable(),
			newLock.getInheritable());
		Assert.assertEquals(Time.getShortTimestamp(
				existingLock.getExpirationDate()),
			Time.getShortTimestamp(newLock.getExpirationDate()));
		Assert.assertEquals(existingLock.getMvccVersion(),
			newLock.getMvccVersion());
	}

	@Test
	public void testFindByPrimaryKeyExisting() throws Exception {
		Lock newLock = addLock();

		Lock existingLock = _persistence.findByPrimaryKey(newLock.getPrimaryKey());

		Assert.assertEquals(existingLock, newLock);
	}

	@Test
	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = ServiceTestUtil.nextLong();

		try {
			_persistence.findByPrimaryKey(pk);

			Assert.fail("Missing entity did not throw NoSuchLockException");
		}
		catch (NoSuchLockException nsee) {
		}
	}

	@Test
	public void testFindAll() throws Exception {
		try {
			_persistence.findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS,
				getOrderByComparator());
		}
		catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	protected OrderByComparator getOrderByComparator() {
		return OrderByComparatorFactoryUtil.create("Lock_", "uuid", true,
			"lockId", true, "companyId", true, "userId", true, "userName",
			true, "createDate", true, "className", true, "key", true, "owner",
			true, "inheritable", true, "expirationDate", true, "mvccVersion",
			true);
	}

	@Test
	public void testFetchByPrimaryKeyExisting() throws Exception {
		Lock newLock = addLock();

		Lock existingLock = _persistence.fetchByPrimaryKey(newLock.getPrimaryKey());

		Assert.assertEquals(existingLock, newLock);
	}

	@Test
	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = ServiceTestUtil.nextLong();

		Lock missingLock = _persistence.fetchByPrimaryKey(pk);

		Assert.assertNull(missingLock);
	}

	@Test
	public void testActionableDynamicQuery() throws Exception {
		final IntegerWrapper count = new IntegerWrapper();

		ActionableDynamicQuery actionableDynamicQuery = new LockActionableDynamicQuery() {
				@Override
				protected void performAction(Object object) {
					Lock lock = (Lock)object;

					Assert.assertNotNull(lock);

					count.increment();
				}
			};

		actionableDynamicQuery.performActions();

		Assert.assertEquals(count.getValue(), _persistence.countAll());
	}

	@Test
	public void testDynamicQueryByPrimaryKeyExisting()
		throws Exception {
		Lock newLock = addLock();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(Lock.class,
				Lock.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("lockId",
				newLock.getLockId()));

		List<Lock> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		Lock existingLock = result.get(0);

		Assert.assertEquals(existingLock, newLock);
	}

	@Test
	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(Lock.class,
				Lock.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("lockId",
				ServiceTestUtil.nextLong()));

		List<Lock> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
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

		Assert.assertEquals(1, result.size());

		Object existingLockId = result.get(0);

		Assert.assertEquals(existingLockId, newLockId);
	}

	@Test
	public void testDynamicQueryByProjectionMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(Lock.class,
				Lock.class.getClassLoader());

		dynamicQuery.setProjection(ProjectionFactoryUtil.property("lockId"));

		dynamicQuery.add(RestrictionsFactoryUtil.in("lockId",
				new Object[] { ServiceTestUtil.nextLong() }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testResetOriginalValues() throws Exception {
		if (!PropsValues.HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE) {
			return;
		}

		Lock newLock = addLock();

		_persistence.clearCache();

		LockModelImpl existingLockModelImpl = (LockModelImpl)_persistence.findByPrimaryKey(newLock.getPrimaryKey());

		Assert.assertTrue(Validator.equals(
				existingLockModelImpl.getClassName(),
				existingLockModelImpl.getOriginalClassName()));
		Assert.assertTrue(Validator.equals(existingLockModelImpl.getKey(),
				existingLockModelImpl.getOriginalKey()));
	}

	protected Lock addLock() throws Exception {
		long pk = ServiceTestUtil.nextLong();

		Lock lock = _persistence.create(pk);

		lock.setUuid(ServiceTestUtil.randomString());

		lock.setCompanyId(ServiceTestUtil.nextLong());

		lock.setUserId(ServiceTestUtil.nextLong());

		lock.setUserName(ServiceTestUtil.randomString());

		lock.setCreateDate(ServiceTestUtil.nextDate());

		lock.setClassName(ServiceTestUtil.randomString());

		lock.setKey(ServiceTestUtil.randomString());

		lock.setOwner(ServiceTestUtil.randomString());

		lock.setInheritable(ServiceTestUtil.randomBoolean());

		lock.setExpirationDate(ServiceTestUtil.nextDate());

		lock.setMvccVersion(ServiceTestUtil.nextLong());

		_persistence.update(lock);

		return lock;
	}

	private static Log _log = LogFactoryUtil.getLog(LockPersistenceTest.class);
	private LockPersistence _persistence = (LockPersistence)PortalBeanLocatorUtil.locate(LockPersistence.class.getName());
	private TransactionalPersistenceAdvice _transactionalPersistenceAdvice = (TransactionalPersistenceAdvice)PortalBeanLocatorUtil.locate(TransactionalPersistenceAdvice.class.getName());
}