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

import com.liferay.portal.NoSuchBrowserTrackerException;
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
import com.liferay.portal.model.BrowserTracker;
import com.liferay.portal.model.ModelListener;
import com.liferay.portal.model.impl.BrowserTrackerModelImpl;
import com.liferay.portal.service.BrowserTrackerLocalServiceUtil;
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
public class BrowserTrackerPersistenceTest {
	@Before
	public void setUp() {
		_modelListeners = _persistence.getListeners();

		for (ModelListener<BrowserTracker> modelListener : _modelListeners) {
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

		for (ModelListener<BrowserTracker> modelListener : _modelListeners) {
			_persistence.registerListener(modelListener);
		}
	}

	@Test
	public void testCreate() throws Exception {
		long pk = RandomTestUtil.nextLong();

		BrowserTracker browserTracker = _persistence.create(pk);

		Assert.assertNotNull(browserTracker);

		Assert.assertEquals(browserTracker.getPrimaryKey(), pk);
	}

	@Test
	public void testRemove() throws Exception {
		BrowserTracker newBrowserTracker = addBrowserTracker();

		_persistence.remove(newBrowserTracker);

		BrowserTracker existingBrowserTracker = _persistence.fetchByPrimaryKey(newBrowserTracker.getPrimaryKey());

		Assert.assertNull(existingBrowserTracker);
	}

	@Test
	public void testUpdateNew() throws Exception {
		addBrowserTracker();
	}

	@Test
	public void testUpdateExisting() throws Exception {
		long pk = RandomTestUtil.nextLong();

		BrowserTracker newBrowserTracker = _persistence.create(pk);

		newBrowserTracker.setMvccVersion(RandomTestUtil.nextLong());

		newBrowserTracker.setUserId(RandomTestUtil.nextLong());

		newBrowserTracker.setBrowserKey(RandomTestUtil.nextLong());

		_persistence.update(newBrowserTracker);

		BrowserTracker existingBrowserTracker = _persistence.findByPrimaryKey(newBrowserTracker.getPrimaryKey());

		Assert.assertEquals(existingBrowserTracker.getMvccVersion(),
			newBrowserTracker.getMvccVersion());
		Assert.assertEquals(existingBrowserTracker.getBrowserTrackerId(),
			newBrowserTracker.getBrowserTrackerId());
		Assert.assertEquals(existingBrowserTracker.getUserId(),
			newBrowserTracker.getUserId());
		Assert.assertEquals(existingBrowserTracker.getBrowserKey(),
			newBrowserTracker.getBrowserKey());
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
		BrowserTracker newBrowserTracker = addBrowserTracker();

		BrowserTracker existingBrowserTracker = _persistence.findByPrimaryKey(newBrowserTracker.getPrimaryKey());

		Assert.assertEquals(existingBrowserTracker, newBrowserTracker);
	}

	@Test
	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		try {
			_persistence.findByPrimaryKey(pk);

			Assert.fail(
				"Missing entity did not throw NoSuchBrowserTrackerException");
		}
		catch (NoSuchBrowserTrackerException nsee) {
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
		return OrderByComparatorFactoryUtil.create("BrowserTracker",
			"mvccVersion", true, "browserTrackerId", true, "userId", true,
			"browserKey", true);
	}

	@Test
	public void testFetchByPrimaryKeyExisting() throws Exception {
		BrowserTracker newBrowserTracker = addBrowserTracker();

		BrowserTracker existingBrowserTracker = _persistence.fetchByPrimaryKey(newBrowserTracker.getPrimaryKey());

		Assert.assertEquals(existingBrowserTracker, newBrowserTracker);
	}

	@Test
	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		BrowserTracker missingBrowserTracker = _persistence.fetchByPrimaryKey(pk);

		Assert.assertNull(missingBrowserTracker);
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereAllPrimaryKeysExist()
		throws Exception {
		BrowserTracker newBrowserTracker1 = addBrowserTracker();
		BrowserTracker newBrowserTracker2 = addBrowserTracker();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newBrowserTracker1.getPrimaryKey());
		primaryKeys.add(newBrowserTracker2.getPrimaryKey());

		Map<Serializable, BrowserTracker> browserTrackers = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(2, browserTrackers.size());
		Assert.assertEquals(newBrowserTracker1,
			browserTrackers.get(newBrowserTracker1.getPrimaryKey()));
		Assert.assertEquals(newBrowserTracker2,
			browserTrackers.get(newBrowserTracker2.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereNoPrimaryKeysExist()
		throws Exception {
		long pk1 = RandomTestUtil.nextLong();

		long pk2 = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(pk1);
		primaryKeys.add(pk2);

		Map<Serializable, BrowserTracker> browserTrackers = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(browserTrackers.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereSomePrimaryKeysExist()
		throws Exception {
		BrowserTracker newBrowserTracker = addBrowserTracker();

		long pk = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newBrowserTracker.getPrimaryKey());
		primaryKeys.add(pk);

		Map<Serializable, BrowserTracker> browserTrackers = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, browserTrackers.size());
		Assert.assertEquals(newBrowserTracker,
			browserTrackers.get(newBrowserTracker.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithNoPrimaryKeys()
		throws Exception {
		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		Map<Serializable, BrowserTracker> browserTrackers = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(browserTrackers.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithOnePrimaryKey()
		throws Exception {
		BrowserTracker newBrowserTracker = addBrowserTracker();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newBrowserTracker.getPrimaryKey());

		Map<Serializable, BrowserTracker> browserTrackers = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, browserTrackers.size());
		Assert.assertEquals(newBrowserTracker,
			browserTrackers.get(newBrowserTracker.getPrimaryKey()));
	}

	@Test
	public void testActionableDynamicQuery() throws Exception {
		final IntegerWrapper count = new IntegerWrapper();

		ActionableDynamicQuery actionableDynamicQuery = BrowserTrackerLocalServiceUtil.getActionableDynamicQuery();

		actionableDynamicQuery.setPerformActionMethod(new ActionableDynamicQuery.PerformActionMethod() {
				@Override
				public void performAction(Object object) {
					BrowserTracker browserTracker = (BrowserTracker)object;

					Assert.assertNotNull(browserTracker);

					count.increment();
				}
			});

		actionableDynamicQuery.performActions();

		Assert.assertEquals(count.getValue(), _persistence.countAll());
	}

	@Test
	public void testDynamicQueryByPrimaryKeyExisting()
		throws Exception {
		BrowserTracker newBrowserTracker = addBrowserTracker();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(BrowserTracker.class,
				BrowserTracker.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("browserTrackerId",
				newBrowserTracker.getBrowserTrackerId()));

		List<BrowserTracker> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		BrowserTracker existingBrowserTracker = result.get(0);

		Assert.assertEquals(existingBrowserTracker, newBrowserTracker);
	}

	@Test
	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(BrowserTracker.class,
				BrowserTracker.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("browserTrackerId",
				RandomTestUtil.nextLong()));

		List<BrowserTracker> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testDynamicQueryByProjectionExisting()
		throws Exception {
		BrowserTracker newBrowserTracker = addBrowserTracker();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(BrowserTracker.class,
				BrowserTracker.class.getClassLoader());

		dynamicQuery.setProjection(ProjectionFactoryUtil.property(
				"browserTrackerId"));

		Object newBrowserTrackerId = newBrowserTracker.getBrowserTrackerId();

		dynamicQuery.add(RestrictionsFactoryUtil.in("browserTrackerId",
				new Object[] { newBrowserTrackerId }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		Object existingBrowserTrackerId = result.get(0);

		Assert.assertEquals(existingBrowserTrackerId, newBrowserTrackerId);
	}

	@Test
	public void testDynamicQueryByProjectionMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(BrowserTracker.class,
				BrowserTracker.class.getClassLoader());

		dynamicQuery.setProjection(ProjectionFactoryUtil.property(
				"browserTrackerId"));

		dynamicQuery.add(RestrictionsFactoryUtil.in("browserTrackerId",
				new Object[] { RandomTestUtil.nextLong() }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testResetOriginalValues() throws Exception {
		if (!PropsValues.HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE) {
			return;
		}

		BrowserTracker newBrowserTracker = addBrowserTracker();

		_persistence.clearCache();

		BrowserTrackerModelImpl existingBrowserTrackerModelImpl = (BrowserTrackerModelImpl)_persistence.findByPrimaryKey(newBrowserTracker.getPrimaryKey());

		Assert.assertEquals(existingBrowserTrackerModelImpl.getUserId(),
			existingBrowserTrackerModelImpl.getOriginalUserId());
	}

	protected BrowserTracker addBrowserTracker() throws Exception {
		long pk = RandomTestUtil.nextLong();

		BrowserTracker browserTracker = _persistence.create(pk);

		browserTracker.setMvccVersion(RandomTestUtil.nextLong());

		browserTracker.setUserId(RandomTestUtil.nextLong());

		browserTracker.setBrowserKey(RandomTestUtil.nextLong());

		_persistence.update(browserTracker);

		return browserTracker;
	}

	private static Log _log = LogFactoryUtil.getLog(BrowserTrackerPersistenceTest.class);
	private ModelListener<BrowserTracker>[] _modelListeners;
	private BrowserTrackerPersistence _persistence = (BrowserTrackerPersistence)PortalBeanLocatorUtil.locate(BrowserTrackerPersistence.class.getName());
	private TransactionalPersistenceAdvice _transactionalPersistenceAdvice = (TransactionalPersistenceAdvice)PortalBeanLocatorUtil.locate(TransactionalPersistenceAdvice.class.getName());
}