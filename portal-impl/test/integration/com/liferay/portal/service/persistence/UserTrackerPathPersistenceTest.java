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

import com.liferay.portal.NoSuchUserTrackerPathException;
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
import com.liferay.portal.model.ModelListener;
import com.liferay.portal.model.UserTrackerPath;
import com.liferay.portal.service.UserTrackerPathLocalServiceUtil;
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
public class UserTrackerPathPersistenceTest {
	@Before
	public void setUp() {
		_modelListeners = _persistence.getListeners();

		for (ModelListener<UserTrackerPath> modelListener : _modelListeners) {
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

		for (ModelListener<UserTrackerPath> modelListener : _modelListeners) {
			_persistence.registerListener(modelListener);
		}
	}

	@Test
	public void testCreate() throws Exception {
		long pk = RandomTestUtil.nextLong();

		UserTrackerPath userTrackerPath = _persistence.create(pk);

		Assert.assertNotNull(userTrackerPath);

		Assert.assertEquals(userTrackerPath.getPrimaryKey(), pk);
	}

	@Test
	public void testRemove() throws Exception {
		UserTrackerPath newUserTrackerPath = addUserTrackerPath();

		_persistence.remove(newUserTrackerPath);

		UserTrackerPath existingUserTrackerPath = _persistence.fetchByPrimaryKey(newUserTrackerPath.getPrimaryKey());

		Assert.assertNull(existingUserTrackerPath);
	}

	@Test
	public void testUpdateNew() throws Exception {
		addUserTrackerPath();
	}

	@Test
	public void testUpdateExisting() throws Exception {
		long pk = RandomTestUtil.nextLong();

		UserTrackerPath newUserTrackerPath = _persistence.create(pk);

		newUserTrackerPath.setMvccVersion(RandomTestUtil.nextLong());

		newUserTrackerPath.setUserTrackerId(RandomTestUtil.nextLong());

		newUserTrackerPath.setPath(RandomTestUtil.randomString());

		newUserTrackerPath.setPathDate(RandomTestUtil.nextDate());

		_persistence.update(newUserTrackerPath);

		UserTrackerPath existingUserTrackerPath = _persistence.findByPrimaryKey(newUserTrackerPath.getPrimaryKey());

		Assert.assertEquals(existingUserTrackerPath.getMvccVersion(),
			newUserTrackerPath.getMvccVersion());
		Assert.assertEquals(existingUserTrackerPath.getUserTrackerPathId(),
			newUserTrackerPath.getUserTrackerPathId());
		Assert.assertEquals(existingUserTrackerPath.getUserTrackerId(),
			newUserTrackerPath.getUserTrackerId());
		Assert.assertEquals(existingUserTrackerPath.getPath(),
			newUserTrackerPath.getPath());
		Assert.assertEquals(Time.getShortTimestamp(
				existingUserTrackerPath.getPathDate()),
			Time.getShortTimestamp(newUserTrackerPath.getPathDate()));
	}

	@Test
	public void testCountByUserTrackerId() {
		try {
			_persistence.countByUserTrackerId(RandomTestUtil.nextLong());

			_persistence.countByUserTrackerId(0L);
		}
		catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void testFindByPrimaryKeyExisting() throws Exception {
		UserTrackerPath newUserTrackerPath = addUserTrackerPath();

		UserTrackerPath existingUserTrackerPath = _persistence.findByPrimaryKey(newUserTrackerPath.getPrimaryKey());

		Assert.assertEquals(existingUserTrackerPath, newUserTrackerPath);
	}

	@Test
	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		try {
			_persistence.findByPrimaryKey(pk);

			Assert.fail(
				"Missing entity did not throw NoSuchUserTrackerPathException");
		}
		catch (NoSuchUserTrackerPathException nsee) {
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
		return OrderByComparatorFactoryUtil.create("UserTrackerPath",
			"mvccVersion", true, "userTrackerPathId", true, "userTrackerId",
			true, "path", true, "pathDate", true);
	}

	@Test
	public void testFetchByPrimaryKeyExisting() throws Exception {
		UserTrackerPath newUserTrackerPath = addUserTrackerPath();

		UserTrackerPath existingUserTrackerPath = _persistence.fetchByPrimaryKey(newUserTrackerPath.getPrimaryKey());

		Assert.assertEquals(existingUserTrackerPath, newUserTrackerPath);
	}

	@Test
	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		UserTrackerPath missingUserTrackerPath = _persistence.fetchByPrimaryKey(pk);

		Assert.assertNull(missingUserTrackerPath);
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereAllPrimaryKeysExist()
		throws Exception {
		UserTrackerPath newUserTrackerPath1 = addUserTrackerPath();
		UserTrackerPath newUserTrackerPath2 = addUserTrackerPath();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newUserTrackerPath1.getPrimaryKey());
		primaryKeys.add(newUserTrackerPath2.getPrimaryKey());

		Map<Serializable, UserTrackerPath> userTrackerPaths = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(2, userTrackerPaths.size());
		Assert.assertEquals(newUserTrackerPath1,
			userTrackerPaths.get(newUserTrackerPath1.getPrimaryKey()));
		Assert.assertEquals(newUserTrackerPath2,
			userTrackerPaths.get(newUserTrackerPath2.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereNoPrimaryKeysExist()
		throws Exception {
		long pk1 = RandomTestUtil.nextLong();

		long pk2 = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(pk1);
		primaryKeys.add(pk2);

		Map<Serializable, UserTrackerPath> userTrackerPaths = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(userTrackerPaths.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereSomePrimaryKeysExist()
		throws Exception {
		UserTrackerPath newUserTrackerPath = addUserTrackerPath();

		long pk = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newUserTrackerPath.getPrimaryKey());
		primaryKeys.add(pk);

		Map<Serializable, UserTrackerPath> userTrackerPaths = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, userTrackerPaths.size());
		Assert.assertEquals(newUserTrackerPath,
			userTrackerPaths.get(newUserTrackerPath.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithNoPrimaryKeys()
		throws Exception {
		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		Map<Serializable, UserTrackerPath> userTrackerPaths = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(userTrackerPaths.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithOnePrimaryKey()
		throws Exception {
		UserTrackerPath newUserTrackerPath = addUserTrackerPath();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newUserTrackerPath.getPrimaryKey());

		Map<Serializable, UserTrackerPath> userTrackerPaths = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, userTrackerPaths.size());
		Assert.assertEquals(newUserTrackerPath,
			userTrackerPaths.get(newUserTrackerPath.getPrimaryKey()));
	}

	@Test
	public void testActionableDynamicQuery() throws Exception {
		final IntegerWrapper count = new IntegerWrapper();

		ActionableDynamicQuery actionableDynamicQuery = UserTrackerPathLocalServiceUtil.getActionableDynamicQuery();

		actionableDynamicQuery.setPerformActionMethod(new ActionableDynamicQuery.PerformActionMethod() {
				@Override
				public void performAction(Object object) {
					UserTrackerPath userTrackerPath = (UserTrackerPath)object;

					Assert.assertNotNull(userTrackerPath);

					count.increment();
				}
			});

		actionableDynamicQuery.performActions();

		Assert.assertEquals(count.getValue(), _persistence.countAll());
	}

	@Test
	public void testDynamicQueryByPrimaryKeyExisting()
		throws Exception {
		UserTrackerPath newUserTrackerPath = addUserTrackerPath();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(UserTrackerPath.class,
				UserTrackerPath.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("userTrackerPathId",
				newUserTrackerPath.getUserTrackerPathId()));

		List<UserTrackerPath> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		UserTrackerPath existingUserTrackerPath = result.get(0);

		Assert.assertEquals(existingUserTrackerPath, newUserTrackerPath);
	}

	@Test
	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(UserTrackerPath.class,
				UserTrackerPath.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("userTrackerPathId",
				RandomTestUtil.nextLong()));

		List<UserTrackerPath> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
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

		Assert.assertEquals(1, result.size());

		Object existingUserTrackerPathId = result.get(0);

		Assert.assertEquals(existingUserTrackerPathId, newUserTrackerPathId);
	}

	@Test
	public void testDynamicQueryByProjectionMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(UserTrackerPath.class,
				UserTrackerPath.class.getClassLoader());

		dynamicQuery.setProjection(ProjectionFactoryUtil.property(
				"userTrackerPathId"));

		dynamicQuery.add(RestrictionsFactoryUtil.in("userTrackerPathId",
				new Object[] { RandomTestUtil.nextLong() }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	protected UserTrackerPath addUserTrackerPath() throws Exception {
		long pk = RandomTestUtil.nextLong();

		UserTrackerPath userTrackerPath = _persistence.create(pk);

		userTrackerPath.setMvccVersion(RandomTestUtil.nextLong());

		userTrackerPath.setUserTrackerId(RandomTestUtil.nextLong());

		userTrackerPath.setPath(RandomTestUtil.randomString());

		userTrackerPath.setPathDate(RandomTestUtil.nextDate());

		_persistence.update(userTrackerPath);

		return userTrackerPath;
	}

	private static Log _log = LogFactoryUtil.getLog(UserTrackerPathPersistenceTest.class);
	private ModelListener<UserTrackerPath>[] _modelListeners;
	private UserTrackerPathPersistence _persistence = (UserTrackerPathPersistence)PortalBeanLocatorUtil.locate(UserTrackerPathPersistence.class.getName());
	private TransactionalPersistenceAdvice _transactionalPersistenceAdvice = (TransactionalPersistenceAdvice)PortalBeanLocatorUtil.locate(TransactionalPersistenceAdvice.class.getName());
}