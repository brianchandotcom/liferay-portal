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

import com.liferay.portal.NoSuchPreferencesException;
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
import com.liferay.portal.model.ModelListener;
import com.liferay.portal.model.PortalPreferences;
import com.liferay.portal.model.impl.PortalPreferencesModelImpl;
import com.liferay.portal.service.PortalPreferencesLocalServiceUtil;
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
public class PortalPreferencesPersistenceTest {
	@Before
	public void setUp() {
		_modelListeners = _persistence.getListeners();

		for (ModelListener<PortalPreferences> modelListener : _modelListeners) {
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

		for (ModelListener<PortalPreferences> modelListener : _modelListeners) {
			_persistence.registerListener(modelListener);
		}
	}

	@Test
	public void testCreate() throws Exception {
		long pk = RandomTestUtil.nextLong();

		PortalPreferences portalPreferences = _persistence.create(pk);

		Assert.assertNotNull(portalPreferences);

		Assert.assertEquals(portalPreferences.getPrimaryKey(), pk);
	}

	@Test
	public void testRemove() throws Exception {
		PortalPreferences newPortalPreferences = addPortalPreferences();

		_persistence.remove(newPortalPreferences);

		PortalPreferences existingPortalPreferences = _persistence.fetchByPrimaryKey(newPortalPreferences.getPrimaryKey());

		Assert.assertNull(existingPortalPreferences);
	}

	@Test
	public void testUpdateNew() throws Exception {
		addPortalPreferences();
	}

	@Test
	public void testUpdateExisting() throws Exception {
		long pk = RandomTestUtil.nextLong();

		PortalPreferences newPortalPreferences = _persistence.create(pk);

		newPortalPreferences.setMvccVersion(RandomTestUtil.nextLong());

		newPortalPreferences.setOwnerId(RandomTestUtil.nextLong());

		newPortalPreferences.setOwnerType(RandomTestUtil.nextInt());

		newPortalPreferences.setPreferences(RandomTestUtil.randomString());

		_persistence.update(newPortalPreferences);

		PortalPreferences existingPortalPreferences = _persistence.findByPrimaryKey(newPortalPreferences.getPrimaryKey());

		Assert.assertEquals(existingPortalPreferences.getMvccVersion(),
			newPortalPreferences.getMvccVersion());
		Assert.assertEquals(existingPortalPreferences.getPortalPreferencesId(),
			newPortalPreferences.getPortalPreferencesId());
		Assert.assertEquals(existingPortalPreferences.getOwnerId(),
			newPortalPreferences.getOwnerId());
		Assert.assertEquals(existingPortalPreferences.getOwnerType(),
			newPortalPreferences.getOwnerType());
		Assert.assertEquals(existingPortalPreferences.getPreferences(),
			newPortalPreferences.getPreferences());
	}

	@Test
	public void testCountByO_O() {
		try {
			_persistence.countByO_O(RandomTestUtil.nextLong(),
				RandomTestUtil.nextInt());

			_persistence.countByO_O(0L, 0);
		}
		catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void testFindByPrimaryKeyExisting() throws Exception {
		PortalPreferences newPortalPreferences = addPortalPreferences();

		PortalPreferences existingPortalPreferences = _persistence.findByPrimaryKey(newPortalPreferences.getPrimaryKey());

		Assert.assertEquals(existingPortalPreferences, newPortalPreferences);
	}

	@Test
	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		try {
			_persistence.findByPrimaryKey(pk);

			Assert.fail(
				"Missing entity did not throw NoSuchPreferencesException");
		}
		catch (NoSuchPreferencesException nsee) {
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
		return OrderByComparatorFactoryUtil.create("PortalPreferences",
			"mvccVersion", true, "portalPreferencesId", true, "ownerId", true,
			"ownerType", true, "preferences", true);
	}

	@Test
	public void testFetchByPrimaryKeyExisting() throws Exception {
		PortalPreferences newPortalPreferences = addPortalPreferences();

		PortalPreferences existingPortalPreferences = _persistence.fetchByPrimaryKey(newPortalPreferences.getPrimaryKey());

		Assert.assertEquals(existingPortalPreferences, newPortalPreferences);
	}

	@Test
	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		PortalPreferences missingPortalPreferences = _persistence.fetchByPrimaryKey(pk);

		Assert.assertNull(missingPortalPreferences);
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereAllPrimaryKeysExist()
		throws Exception {
		PortalPreferences newPortalPreferences1 = addPortalPreferences();
		PortalPreferences newPortalPreferences2 = addPortalPreferences();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newPortalPreferences1.getPrimaryKey());
		primaryKeys.add(newPortalPreferences2.getPrimaryKey());

		Map<Serializable, PortalPreferences> portalPreferenceses = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(2, portalPreferenceses.size());
		Assert.assertEquals(newPortalPreferences1,
			portalPreferenceses.get(newPortalPreferences1.getPrimaryKey()));
		Assert.assertEquals(newPortalPreferences2,
			portalPreferenceses.get(newPortalPreferences2.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereNoPrimaryKeysExist()
		throws Exception {
		long pk1 = RandomTestUtil.nextLong();

		long pk2 = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(pk1);
		primaryKeys.add(pk2);

		Map<Serializable, PortalPreferences> portalPreferenceses = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(portalPreferenceses.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereSomePrimaryKeysExist()
		throws Exception {
		PortalPreferences newPortalPreferences = addPortalPreferences();

		long pk = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newPortalPreferences.getPrimaryKey());
		primaryKeys.add(pk);

		Map<Serializable, PortalPreferences> portalPreferenceses = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, portalPreferenceses.size());
		Assert.assertEquals(newPortalPreferences,
			portalPreferenceses.get(newPortalPreferences.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithNoPrimaryKeys()
		throws Exception {
		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		Map<Serializable, PortalPreferences> portalPreferenceses = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(portalPreferenceses.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithOnePrimaryKey()
		throws Exception {
		PortalPreferences newPortalPreferences = addPortalPreferences();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newPortalPreferences.getPrimaryKey());

		Map<Serializable, PortalPreferences> portalPreferenceses = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, portalPreferenceses.size());
		Assert.assertEquals(newPortalPreferences,
			portalPreferenceses.get(newPortalPreferences.getPrimaryKey()));
	}

	@Test
	public void testActionableDynamicQuery() throws Exception {
		final IntegerWrapper count = new IntegerWrapper();

		ActionableDynamicQuery actionableDynamicQuery = PortalPreferencesLocalServiceUtil.getActionableDynamicQuery();

		actionableDynamicQuery.setPerformActionMethod(new ActionableDynamicQuery.PerformActionMethod() {
				@Override
				public void performAction(Object object) {
					PortalPreferences portalPreferences = (PortalPreferences)object;

					Assert.assertNotNull(portalPreferences);

					count.increment();
				}
			});

		actionableDynamicQuery.performActions();

		Assert.assertEquals(count.getValue(), _persistence.countAll());
	}

	@Test
	public void testDynamicQueryByPrimaryKeyExisting()
		throws Exception {
		PortalPreferences newPortalPreferences = addPortalPreferences();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(PortalPreferences.class,
				PortalPreferences.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("portalPreferencesId",
				newPortalPreferences.getPortalPreferencesId()));

		List<PortalPreferences> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		PortalPreferences existingPortalPreferences = result.get(0);

		Assert.assertEquals(existingPortalPreferences, newPortalPreferences);
	}

	@Test
	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(PortalPreferences.class,
				PortalPreferences.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("portalPreferencesId",
				RandomTestUtil.nextLong()));

		List<PortalPreferences> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testDynamicQueryByProjectionExisting()
		throws Exception {
		PortalPreferences newPortalPreferences = addPortalPreferences();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(PortalPreferences.class,
				PortalPreferences.class.getClassLoader());

		dynamicQuery.setProjection(ProjectionFactoryUtil.property(
				"portalPreferencesId"));

		Object newPortalPreferencesId = newPortalPreferences.getPortalPreferencesId();

		dynamicQuery.add(RestrictionsFactoryUtil.in("portalPreferencesId",
				new Object[] { newPortalPreferencesId }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		Object existingPortalPreferencesId = result.get(0);

		Assert.assertEquals(existingPortalPreferencesId, newPortalPreferencesId);
	}

	@Test
	public void testDynamicQueryByProjectionMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(PortalPreferences.class,
				PortalPreferences.class.getClassLoader());

		dynamicQuery.setProjection(ProjectionFactoryUtil.property(
				"portalPreferencesId"));

		dynamicQuery.add(RestrictionsFactoryUtil.in("portalPreferencesId",
				new Object[] { RandomTestUtil.nextLong() }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testResetOriginalValues() throws Exception {
		if (!PropsValues.HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE) {
			return;
		}

		PortalPreferences newPortalPreferences = addPortalPreferences();

		_persistence.clearCache();

		PortalPreferencesModelImpl existingPortalPreferencesModelImpl = (PortalPreferencesModelImpl)_persistence.findByPrimaryKey(newPortalPreferences.getPrimaryKey());

		Assert.assertEquals(existingPortalPreferencesModelImpl.getOwnerId(),
			existingPortalPreferencesModelImpl.getOriginalOwnerId());
		Assert.assertEquals(existingPortalPreferencesModelImpl.getOwnerType(),
			existingPortalPreferencesModelImpl.getOriginalOwnerType());
	}

	protected PortalPreferences addPortalPreferences()
		throws Exception {
		long pk = RandomTestUtil.nextLong();

		PortalPreferences portalPreferences = _persistence.create(pk);

		portalPreferences.setMvccVersion(RandomTestUtil.nextLong());

		portalPreferences.setOwnerId(RandomTestUtil.nextLong());

		portalPreferences.setOwnerType(RandomTestUtil.nextInt());

		portalPreferences.setPreferences(RandomTestUtil.randomString());

		_persistence.update(portalPreferences);

		return portalPreferences;
	}

	private static Log _log = LogFactoryUtil.getLog(PortalPreferencesPersistenceTest.class);
	private ModelListener<PortalPreferences>[] _modelListeners;
	private PortalPreferencesPersistence _persistence = (PortalPreferencesPersistence)PortalBeanLocatorUtil.locate(PortalPreferencesPersistence.class.getName());
	private TransactionalPersistenceAdvice _transactionalPersistenceAdvice = (TransactionalPersistenceAdvice)PortalBeanLocatorUtil.locate(TransactionalPersistenceAdvice.class.getName());
}