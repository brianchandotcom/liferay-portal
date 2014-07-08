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
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Time;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.Lock;
import com.liferay.portal.model.ModelListener;
import com.liferay.portal.model.impl.LockModelImpl;
import com.liferay.portal.service.LockLocalServiceUtil;
import com.liferay.portal.service.persistence.BasePersistence;
import com.liferay.portal.service.persistence.PersistenceExecutionTestListener;
import com.liferay.portal.test.LiferayPersistenceIntegrationJUnitTestRunner;
import com.liferay.portal.test.persistence.test.TransactionalPersistenceAdvice;
import com.liferay.portal.util.PropsValues;
import com.liferay.portal.util.test.RandomTestUtil;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import org.junit.runner.RunWith;

import java.io.Serializable;

import java.util.HashSet;
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
	@Before
	public void setUp() {
		_modelListeners = _persistence.getListeners();

		for (ModelListener<Lock> modelListener : _modelListeners) {
			_persistence.unregisterListener(modelListener);
		}
	}

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

		for (ModelListener<Lock> modelListener : _modelListeners) {
			_persistence.registerListener(modelListener);
		}
	}

	@Test
	public void testCreate() throws Exception {
		long pk = RandomTestUtil.nextLong();

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
		long pk = RandomTestUtil.nextLong();

		Lock newLock = _persistence.create(pk);

		newLock.setMvccVersion(RandomTestUtil.nextLong());

		newLock.setUuid(RandomTestUtil.randomString());

		newLock.setCompanyId(RandomTestUtil.nextLong());

		newLock.setUserId(RandomTestUtil.nextLong());

		newLock.setUserName(RandomTestUtil.randomString());

		newLock.setCreateDate(RandomTestUtil.nextDate());

		newLock.setClassName(RandomTestUtil.randomString());

		newLock.setKey(RandomTestUtil.randomString());

		newLock.setOwner(RandomTestUtil.randomString());

		newLock.setInheritable(RandomTestUtil.randomBoolean());

		newLock.setExpirationDate(RandomTestUtil.nextDate());

		_persistence.update(newLock);

		Lock existingLock = _persistence.findByPrimaryKey(newLock.getPrimaryKey());

		Assert.assertEquals(existingLock.getMvccVersion(),
			newLock.getMvccVersion());
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
	}

	@Test
	public void testCountByUuid() {
		try {
			_persistence.countByUuid(StringPool.BLANK);

			_persistence.countByUuid(StringPool.NULL);

			_persistence.countByUuid((String)null);
		}
		catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void testCountByUuid_C() {
		try {
			_persistence.countByUuid_C(StringPool.BLANK,
				RandomTestUtil.nextLong());

			_persistence.countByUuid_C(StringPool.NULL, 0L);

			_persistence.countByUuid_C((String)null, 0L);
		}
		catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void testCountByLtExpirationDate() {
		try {
			_persistence.countByLtExpirationDate(RandomTestUtil.nextDate());

			_persistence.countByLtExpirationDate(RandomTestUtil.nextDate());
		}
		catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void testCountByC_K() {
		try {
			_persistence.countByC_K(StringPool.BLANK, StringPool.BLANK);

			_persistence.countByC_K(StringPool.NULL, StringPool.NULL);

			_persistence.countByC_K((String)null, (String)null);
		}
		catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void testFindByPrimaryKeyExisting() throws Exception {
		Lock newLock = addLock();

		Lock existingLock = _persistence.findByPrimaryKey(newLock.getPrimaryKey());

		Assert.assertEquals(existingLock, newLock);
	}

	@Test
	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

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
		return OrderByComparatorFactoryUtil.create("Lock_", "mvccVersion",
			true, "uuid", true, "lockId", true, "companyId", true, "userId",
			true, "userName", true, "createDate", true, "className", true,
			"key", true, "owner", true, "inheritable", true, "expirationDate",
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
		long pk = RandomTestUtil.nextLong();

		Lock missingLock = _persistence.fetchByPrimaryKey(pk);

		Assert.assertNull(missingLock);
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereAllPrimaryKeysExist()
		throws Exception {
		Lock newLock1 = addLock();
		Lock newLock2 = addLock();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newLock1.getPrimaryKey());
		primaryKeys.add(newLock2.getPrimaryKey());

		Map<Serializable, Lock> locks = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(2, locks.size());
		Assert.assertEquals(newLock1, locks.get(newLock1.getPrimaryKey()));
		Assert.assertEquals(newLock2, locks.get(newLock2.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereNoPrimaryKeysExist()
		throws Exception {
		long pk1 = RandomTestUtil.nextLong();

		long pk2 = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(pk1);
		primaryKeys.add(pk2);

		Map<Serializable, Lock> locks = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(locks.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereSomePrimaryKeysExist()
		throws Exception {
		Lock newLock = addLock();

		long pk = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newLock.getPrimaryKey());
		primaryKeys.add(pk);

		Map<Serializable, Lock> locks = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, locks.size());
		Assert.assertEquals(newLock, locks.get(newLock.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithNoPrimaryKeys()
		throws Exception {
		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		Map<Serializable, Lock> locks = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(locks.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithOnePrimaryKey()
		throws Exception {
		Lock newLock = addLock();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newLock.getPrimaryKey());

		Map<Serializable, Lock> locks = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, locks.size());
		Assert.assertEquals(newLock, locks.get(newLock.getPrimaryKey()));
	}

	@Test
	public void testActionableDynamicQuery() throws Exception {
		final IntegerWrapper count = new IntegerWrapper();

		ActionableDynamicQuery actionableDynamicQuery = LockLocalServiceUtil.getActionableDynamicQuery();

		actionableDynamicQuery.setPerformActionMethod(new ActionableDynamicQuery.PerformActionMethod() {
				@Override
				public void performAction(Object object) {
					Lock lock = (Lock)object;

					Assert.assertNotNull(lock);

					count.increment();
				}
			});

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
				RandomTestUtil.nextLong()));

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
				new Object[] { RandomTestUtil.nextLong() }));

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
		long pk = RandomTestUtil.nextLong();

		Lock lock = _persistence.create(pk);

		lock.setMvccVersion(RandomTestUtil.nextLong());

		lock.setUuid(RandomTestUtil.randomString());

		lock.setCompanyId(RandomTestUtil.nextLong());

		lock.setUserId(RandomTestUtil.nextLong());

		lock.setUserName(RandomTestUtil.randomString());

		lock.setCreateDate(RandomTestUtil.nextDate());

		lock.setClassName(RandomTestUtil.randomString());

		lock.setKey(RandomTestUtil.randomString());

		lock.setOwner(RandomTestUtil.randomString());

		lock.setInheritable(RandomTestUtil.randomBoolean());

		lock.setExpirationDate(RandomTestUtil.nextDate());

		_persistence.update(lock);

		return lock;
	}

	private static Log _log = LogFactoryUtil.getLog(LockPersistenceTest.class);
	private ModelListener<Lock>[] _modelListeners;
	private LockPersistence _persistence = (LockPersistence)PortalBeanLocatorUtil.locate(LockPersistence.class.getName());
	private TransactionalPersistenceAdvice _transactionalPersistenceAdvice = (TransactionalPersistenceAdvice)PortalBeanLocatorUtil.locate(TransactionalPersistenceAdvice.class.getName());
}