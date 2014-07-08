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

import com.liferay.portal.NoSuchUserTrackerException;
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
import com.liferay.portal.model.ModelListener;
import com.liferay.portal.model.UserTracker;
import com.liferay.portal.service.UserTrackerLocalServiceUtil;
import com.liferay.portal.service.persistence.BasePersistence;
import com.liferay.portal.service.persistence.PersistenceExecutionTestListener;
import com.liferay.portal.test.LiferayPersistenceIntegrationJUnitTestRunner;
import com.liferay.portal.test.persistence.test.TransactionalPersistenceAdvice;
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
public class UserTrackerPersistenceTest {
	@Before
	public void setUp() {
		_modelListeners = _persistence.getListeners();

		for (ModelListener<UserTracker> modelListener : _modelListeners) {
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

		for (ModelListener<UserTracker> modelListener : _modelListeners) {
			_persistence.registerListener(modelListener);
		}
	}

	@Test
	public void testCreate() throws Exception {
		long pk = RandomTestUtil.nextLong();

		UserTracker userTracker = _persistence.create(pk);

		Assert.assertNotNull(userTracker);

		Assert.assertEquals(userTracker.getPrimaryKey(), pk);
	}

	@Test
	public void testRemove() throws Exception {
		UserTracker newUserTracker = addUserTracker();

		_persistence.remove(newUserTracker);

		UserTracker existingUserTracker = _persistence.fetchByPrimaryKey(newUserTracker.getPrimaryKey());

		Assert.assertNull(existingUserTracker);
	}

	@Test
	public void testUpdateNew() throws Exception {
		addUserTracker();
	}

	@Test
	public void testUpdateExisting() throws Exception {
		long pk = RandomTestUtil.nextLong();

		UserTracker newUserTracker = _persistence.create(pk);

		newUserTracker.setMvccVersion(RandomTestUtil.nextLong());

		newUserTracker.setCompanyId(RandomTestUtil.nextLong());

		newUserTracker.setUserId(RandomTestUtil.nextLong());

		newUserTracker.setModifiedDate(RandomTestUtil.nextDate());

		newUserTracker.setSessionId(RandomTestUtil.randomString());

		newUserTracker.setRemoteAddr(RandomTestUtil.randomString());

		newUserTracker.setRemoteHost(RandomTestUtil.randomString());

		newUserTracker.setUserAgent(RandomTestUtil.randomString());

		_persistence.update(newUserTracker);

		UserTracker existingUserTracker = _persistence.findByPrimaryKey(newUserTracker.getPrimaryKey());

		Assert.assertEquals(existingUserTracker.getMvccVersion(),
			newUserTracker.getMvccVersion());
		Assert.assertEquals(existingUserTracker.getUserTrackerId(),
			newUserTracker.getUserTrackerId());
		Assert.assertEquals(existingUserTracker.getCompanyId(),
			newUserTracker.getCompanyId());
		Assert.assertEquals(existingUserTracker.getUserId(),
			newUserTracker.getUserId());
		Assert.assertEquals(Time.getShortTimestamp(
				existingUserTracker.getModifiedDate()),
			Time.getShortTimestamp(newUserTracker.getModifiedDate()));
		Assert.assertEquals(existingUserTracker.getSessionId(),
			newUserTracker.getSessionId());
		Assert.assertEquals(existingUserTracker.getRemoteAddr(),
			newUserTracker.getRemoteAddr());
		Assert.assertEquals(existingUserTracker.getRemoteHost(),
			newUserTracker.getRemoteHost());
		Assert.assertEquals(existingUserTracker.getUserAgent(),
			newUserTracker.getUserAgent());
	}

	@Test
	public void testCountByCompanyId() {
		try {
			_persistence.countByCompanyId(RandomTestUtil.nextLong());

			_persistence.countByCompanyId(0L);
		}
		catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void testCountByUserId() {
		try {
			_persistence.countByUserId(RandomTestUtil.nextLong());

			_persistence.countByUserId(0L);
		}
		catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void testCountBySessionId() {
		try {
			_persistence.countBySessionId(StringPool.BLANK);

			_persistence.countBySessionId(StringPool.NULL);

			_persistence.countBySessionId((String)null);
		}
		catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void testFindByPrimaryKeyExisting() throws Exception {
		UserTracker newUserTracker = addUserTracker();

		UserTracker existingUserTracker = _persistence.findByPrimaryKey(newUserTracker.getPrimaryKey());

		Assert.assertEquals(existingUserTracker, newUserTracker);
	}

	@Test
	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		try {
			_persistence.findByPrimaryKey(pk);

			Assert.fail(
				"Missing entity did not throw NoSuchUserTrackerException");
		}
		catch (NoSuchUserTrackerException nsee) {
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
		return OrderByComparatorFactoryUtil.create("UserTracker",
			"mvccVersion", true, "userTrackerId", true, "companyId", true,
			"userId", true, "modifiedDate", true, "sessionId", true,
			"remoteAddr", true, "remoteHost", true, "userAgent", true);
	}

	@Test
	public void testFetchByPrimaryKeyExisting() throws Exception {
		UserTracker newUserTracker = addUserTracker();

		UserTracker existingUserTracker = _persistence.fetchByPrimaryKey(newUserTracker.getPrimaryKey());

		Assert.assertEquals(existingUserTracker, newUserTracker);
	}

	@Test
	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		UserTracker missingUserTracker = _persistence.fetchByPrimaryKey(pk);

		Assert.assertNull(missingUserTracker);
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereAllPrimaryKeysExist()
		throws Exception {
		UserTracker newUserTracker1 = addUserTracker();
		UserTracker newUserTracker2 = addUserTracker();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newUserTracker1.getPrimaryKey());
		primaryKeys.add(newUserTracker2.getPrimaryKey());

		Map<Serializable, UserTracker> userTrackers = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(2, userTrackers.size());
		Assert.assertEquals(newUserTracker1,
			userTrackers.get(newUserTracker1.getPrimaryKey()));
		Assert.assertEquals(newUserTracker2,
			userTrackers.get(newUserTracker2.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereNoPrimaryKeysExist()
		throws Exception {
		long pk1 = RandomTestUtil.nextLong();

		long pk2 = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(pk1);
		primaryKeys.add(pk2);

		Map<Serializable, UserTracker> userTrackers = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(userTrackers.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereSomePrimaryKeysExist()
		throws Exception {
		UserTracker newUserTracker = addUserTracker();

		long pk = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newUserTracker.getPrimaryKey());
		primaryKeys.add(pk);

		Map<Serializable, UserTracker> userTrackers = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, userTrackers.size());
		Assert.assertEquals(newUserTracker,
			userTrackers.get(newUserTracker.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithNoPrimaryKeys()
		throws Exception {
		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		Map<Serializable, UserTracker> userTrackers = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(userTrackers.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithOnePrimaryKey()
		throws Exception {
		UserTracker newUserTracker = addUserTracker();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newUserTracker.getPrimaryKey());

		Map<Serializable, UserTracker> userTrackers = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, userTrackers.size());
		Assert.assertEquals(newUserTracker,
			userTrackers.get(newUserTracker.getPrimaryKey()));
	}

	@Test
	public void testActionableDynamicQuery() throws Exception {
		final IntegerWrapper count = new IntegerWrapper();

		ActionableDynamicQuery actionableDynamicQuery = UserTrackerLocalServiceUtil.getActionableDynamicQuery();

		actionableDynamicQuery.setPerformActionMethod(new ActionableDynamicQuery.PerformActionMethod() {
				@Override
				public void performAction(Object object) {
					UserTracker userTracker = (UserTracker)object;

					Assert.assertNotNull(userTracker);

					count.increment();
				}
			});

		actionableDynamicQuery.performActions();

		Assert.assertEquals(count.getValue(), _persistence.countAll());
	}

	@Test
	public void testDynamicQueryByPrimaryKeyExisting()
		throws Exception {
		UserTracker newUserTracker = addUserTracker();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(UserTracker.class,
				UserTracker.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("userTrackerId",
				newUserTracker.getUserTrackerId()));

		List<UserTracker> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		UserTracker existingUserTracker = result.get(0);

		Assert.assertEquals(existingUserTracker, newUserTracker);
	}

	@Test
	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(UserTracker.class,
				UserTracker.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("userTrackerId",
				RandomTestUtil.nextLong()));

		List<UserTracker> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testDynamicQueryByProjectionExisting()
		throws Exception {
		UserTracker newUserTracker = addUserTracker();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(UserTracker.class,
				UserTracker.class.getClassLoader());

		dynamicQuery.setProjection(ProjectionFactoryUtil.property(
				"userTrackerId"));

		Object newUserTrackerId = newUserTracker.getUserTrackerId();

		dynamicQuery.add(RestrictionsFactoryUtil.in("userTrackerId",
				new Object[] { newUserTrackerId }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		Object existingUserTrackerId = result.get(0);

		Assert.assertEquals(existingUserTrackerId, newUserTrackerId);
	}

	@Test
	public void testDynamicQueryByProjectionMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(UserTracker.class,
				UserTracker.class.getClassLoader());

		dynamicQuery.setProjection(ProjectionFactoryUtil.property(
				"userTrackerId"));

		dynamicQuery.add(RestrictionsFactoryUtil.in("userTrackerId",
				new Object[] { RandomTestUtil.nextLong() }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	protected UserTracker addUserTracker() throws Exception {
		long pk = RandomTestUtil.nextLong();

		UserTracker userTracker = _persistence.create(pk);

		userTracker.setMvccVersion(RandomTestUtil.nextLong());

		userTracker.setCompanyId(RandomTestUtil.nextLong());

		userTracker.setUserId(RandomTestUtil.nextLong());

		userTracker.setModifiedDate(RandomTestUtil.nextDate());

		userTracker.setSessionId(RandomTestUtil.randomString());

		userTracker.setRemoteAddr(RandomTestUtil.randomString());

		userTracker.setRemoteHost(RandomTestUtil.randomString());

		userTracker.setUserAgent(RandomTestUtil.randomString());

		_persistence.update(userTracker);

		return userTracker;
	}

	private static Log _log = LogFactoryUtil.getLog(UserTrackerPersistenceTest.class);
	private ModelListener<UserTracker>[] _modelListeners;
	private UserTrackerPersistence _persistence = (UserTrackerPersistence)PortalBeanLocatorUtil.locate(UserTrackerPersistence.class.getName());
	private TransactionalPersistenceAdvice _transactionalPersistenceAdvice = (TransactionalPersistenceAdvice)PortalBeanLocatorUtil.locate(TransactionalPersistenceAdvice.class.getName());
}