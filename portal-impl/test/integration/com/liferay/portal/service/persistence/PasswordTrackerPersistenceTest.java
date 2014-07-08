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

import com.liferay.portal.NoSuchPasswordTrackerException;
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
import com.liferay.portal.model.PasswordTracker;
import com.liferay.portal.service.PasswordTrackerLocalServiceUtil;
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
public class PasswordTrackerPersistenceTest {
	@Before
	public void setUp() {
		_modelListeners = _persistence.getListeners();

		for (ModelListener<PasswordTracker> modelListener : _modelListeners) {
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

		for (ModelListener<PasswordTracker> modelListener : _modelListeners) {
			_persistence.registerListener(modelListener);
		}
	}

	@Test
	public void testCreate() throws Exception {
		long pk = RandomTestUtil.nextLong();

		PasswordTracker passwordTracker = _persistence.create(pk);

		Assert.assertNotNull(passwordTracker);

		Assert.assertEquals(passwordTracker.getPrimaryKey(), pk);
	}

	@Test
	public void testRemove() throws Exception {
		PasswordTracker newPasswordTracker = addPasswordTracker();

		_persistence.remove(newPasswordTracker);

		PasswordTracker existingPasswordTracker = _persistence.fetchByPrimaryKey(newPasswordTracker.getPrimaryKey());

		Assert.assertNull(existingPasswordTracker);
	}

	@Test
	public void testUpdateNew() throws Exception {
		addPasswordTracker();
	}

	@Test
	public void testUpdateExisting() throws Exception {
		long pk = RandomTestUtil.nextLong();

		PasswordTracker newPasswordTracker = _persistence.create(pk);

		newPasswordTracker.setMvccVersion(RandomTestUtil.nextLong());

		newPasswordTracker.setUserId(RandomTestUtil.nextLong());

		newPasswordTracker.setCreateDate(RandomTestUtil.nextDate());

		newPasswordTracker.setPassword(RandomTestUtil.randomString());

		_persistence.update(newPasswordTracker);

		PasswordTracker existingPasswordTracker = _persistence.findByPrimaryKey(newPasswordTracker.getPrimaryKey());

		Assert.assertEquals(existingPasswordTracker.getMvccVersion(),
			newPasswordTracker.getMvccVersion());
		Assert.assertEquals(existingPasswordTracker.getPasswordTrackerId(),
			newPasswordTracker.getPasswordTrackerId());
		Assert.assertEquals(existingPasswordTracker.getUserId(),
			newPasswordTracker.getUserId());
		Assert.assertEquals(Time.getShortTimestamp(
				existingPasswordTracker.getCreateDate()),
			Time.getShortTimestamp(newPasswordTracker.getCreateDate()));
		Assert.assertEquals(existingPasswordTracker.getPassword(),
			newPasswordTracker.getPassword());
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
	public void testFindByPrimaryKeyExisting() throws Exception {
		PasswordTracker newPasswordTracker = addPasswordTracker();

		PasswordTracker existingPasswordTracker = _persistence.findByPrimaryKey(newPasswordTracker.getPrimaryKey());

		Assert.assertEquals(existingPasswordTracker, newPasswordTracker);
	}

	@Test
	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		try {
			_persistence.findByPrimaryKey(pk);

			Assert.fail(
				"Missing entity did not throw NoSuchPasswordTrackerException");
		}
		catch (NoSuchPasswordTrackerException nsee) {
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
		return OrderByComparatorFactoryUtil.create("PasswordTracker",
			"mvccVersion", true, "passwordTrackerId", true, "userId", true,
			"createDate", true, "password", true);
	}

	@Test
	public void testFetchByPrimaryKeyExisting() throws Exception {
		PasswordTracker newPasswordTracker = addPasswordTracker();

		PasswordTracker existingPasswordTracker = _persistence.fetchByPrimaryKey(newPasswordTracker.getPrimaryKey());

		Assert.assertEquals(existingPasswordTracker, newPasswordTracker);
	}

	@Test
	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		PasswordTracker missingPasswordTracker = _persistence.fetchByPrimaryKey(pk);

		Assert.assertNull(missingPasswordTracker);
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereAllPrimaryKeysExist()
		throws Exception {
		PasswordTracker newPasswordTracker1 = addPasswordTracker();
		PasswordTracker newPasswordTracker2 = addPasswordTracker();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newPasswordTracker1.getPrimaryKey());
		primaryKeys.add(newPasswordTracker2.getPrimaryKey());

		Map<Serializable, PasswordTracker> passwordTrackers = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(2, passwordTrackers.size());
		Assert.assertEquals(newPasswordTracker1,
			passwordTrackers.get(newPasswordTracker1.getPrimaryKey()));
		Assert.assertEquals(newPasswordTracker2,
			passwordTrackers.get(newPasswordTracker2.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereNoPrimaryKeysExist()
		throws Exception {
		long pk1 = RandomTestUtil.nextLong();

		long pk2 = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(pk1);
		primaryKeys.add(pk2);

		Map<Serializable, PasswordTracker> passwordTrackers = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(passwordTrackers.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereSomePrimaryKeysExist()
		throws Exception {
		PasswordTracker newPasswordTracker = addPasswordTracker();

		long pk = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newPasswordTracker.getPrimaryKey());
		primaryKeys.add(pk);

		Map<Serializable, PasswordTracker> passwordTrackers = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, passwordTrackers.size());
		Assert.assertEquals(newPasswordTracker,
			passwordTrackers.get(newPasswordTracker.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithNoPrimaryKeys()
		throws Exception {
		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		Map<Serializable, PasswordTracker> passwordTrackers = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(passwordTrackers.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithOnePrimaryKey()
		throws Exception {
		PasswordTracker newPasswordTracker = addPasswordTracker();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newPasswordTracker.getPrimaryKey());

		Map<Serializable, PasswordTracker> passwordTrackers = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, passwordTrackers.size());
		Assert.assertEquals(newPasswordTracker,
			passwordTrackers.get(newPasswordTracker.getPrimaryKey()));
	}

	@Test
	public void testActionableDynamicQuery() throws Exception {
		final IntegerWrapper count = new IntegerWrapper();

		ActionableDynamicQuery actionableDynamicQuery = PasswordTrackerLocalServiceUtil.getActionableDynamicQuery();

		actionableDynamicQuery.setPerformActionMethod(new ActionableDynamicQuery.PerformActionMethod() {
				@Override
				public void performAction(Object object) {
					PasswordTracker passwordTracker = (PasswordTracker)object;

					Assert.assertNotNull(passwordTracker);

					count.increment();
				}
			});

		actionableDynamicQuery.performActions();

		Assert.assertEquals(count.getValue(), _persistence.countAll());
	}

	@Test
	public void testDynamicQueryByPrimaryKeyExisting()
		throws Exception {
		PasswordTracker newPasswordTracker = addPasswordTracker();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(PasswordTracker.class,
				PasswordTracker.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("passwordTrackerId",
				newPasswordTracker.getPasswordTrackerId()));

		List<PasswordTracker> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		PasswordTracker existingPasswordTracker = result.get(0);

		Assert.assertEquals(existingPasswordTracker, newPasswordTracker);
	}

	@Test
	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(PasswordTracker.class,
				PasswordTracker.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("passwordTrackerId",
				RandomTestUtil.nextLong()));

		List<PasswordTracker> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
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

		Assert.assertEquals(1, result.size());

		Object existingPasswordTrackerId = result.get(0);

		Assert.assertEquals(existingPasswordTrackerId, newPasswordTrackerId);
	}

	@Test
	public void testDynamicQueryByProjectionMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(PasswordTracker.class,
				PasswordTracker.class.getClassLoader());

		dynamicQuery.setProjection(ProjectionFactoryUtil.property(
				"passwordTrackerId"));

		dynamicQuery.add(RestrictionsFactoryUtil.in("passwordTrackerId",
				new Object[] { RandomTestUtil.nextLong() }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	protected PasswordTracker addPasswordTracker() throws Exception {
		long pk = RandomTestUtil.nextLong();

		PasswordTracker passwordTracker = _persistence.create(pk);

		passwordTracker.setMvccVersion(RandomTestUtil.nextLong());

		passwordTracker.setUserId(RandomTestUtil.nextLong());

		passwordTracker.setCreateDate(RandomTestUtil.nextDate());

		passwordTracker.setPassword(RandomTestUtil.randomString());

		_persistence.update(passwordTracker);

		return passwordTracker;
	}

	private static Log _log = LogFactoryUtil.getLog(PasswordTrackerPersistenceTest.class);
	private ModelListener<PasswordTracker>[] _modelListeners;
	private PasswordTrackerPersistence _persistence = (PasswordTrackerPersistence)PortalBeanLocatorUtil.locate(PasswordTrackerPersistence.class.getName());
	private TransactionalPersistenceAdvice _transactionalPersistenceAdvice = (TransactionalPersistenceAdvice)PortalBeanLocatorUtil.locate(TransactionalPersistenceAdvice.class.getName());
}