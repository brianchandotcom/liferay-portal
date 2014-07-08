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

import com.liferay.portal.NoSuchPortletPreferencesException;
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
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.ModelListener;
import com.liferay.portal.model.PortletPreferences;
import com.liferay.portal.model.impl.PortletPreferencesModelImpl;
import com.liferay.portal.service.PortletPreferencesLocalServiceUtil;
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
public class PortletPreferencesPersistenceTest {
	@Before
	public void setUp() {
		_modelListeners = _persistence.getListeners();

		for (ModelListener<PortletPreferences> modelListener : _modelListeners) {
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

		for (ModelListener<PortletPreferences> modelListener : _modelListeners) {
			_persistence.registerListener(modelListener);
		}
	}

	@Test
	public void testCreate() throws Exception {
		long pk = RandomTestUtil.nextLong();

		PortletPreferences portletPreferences = _persistence.create(pk);

		Assert.assertNotNull(portletPreferences);

		Assert.assertEquals(portletPreferences.getPrimaryKey(), pk);
	}

	@Test
	public void testRemove() throws Exception {
		PortletPreferences newPortletPreferences = addPortletPreferences();

		_persistence.remove(newPortletPreferences);

		PortletPreferences existingPortletPreferences = _persistence.fetchByPrimaryKey(newPortletPreferences.getPrimaryKey());

		Assert.assertNull(existingPortletPreferences);
	}

	@Test
	public void testUpdateNew() throws Exception {
		addPortletPreferences();
	}

	@Test
	public void testUpdateExisting() throws Exception {
		long pk = RandomTestUtil.nextLong();

		PortletPreferences newPortletPreferences = _persistence.create(pk);

		newPortletPreferences.setMvccVersion(RandomTestUtil.nextLong());

		newPortletPreferences.setOwnerId(RandomTestUtil.nextLong());

		newPortletPreferences.setOwnerType(RandomTestUtil.nextInt());

		newPortletPreferences.setPlid(RandomTestUtil.nextLong());

		newPortletPreferences.setPortletId(RandomTestUtil.randomString());

		newPortletPreferences.setPreferences(RandomTestUtil.randomString());

		_persistence.update(newPortletPreferences);

		PortletPreferences existingPortletPreferences = _persistence.findByPrimaryKey(newPortletPreferences.getPrimaryKey());

		Assert.assertEquals(existingPortletPreferences.getMvccVersion(),
			newPortletPreferences.getMvccVersion());
		Assert.assertEquals(existingPortletPreferences.getPortletPreferencesId(),
			newPortletPreferences.getPortletPreferencesId());
		Assert.assertEquals(existingPortletPreferences.getOwnerId(),
			newPortletPreferences.getOwnerId());
		Assert.assertEquals(existingPortletPreferences.getOwnerType(),
			newPortletPreferences.getOwnerType());
		Assert.assertEquals(existingPortletPreferences.getPlid(),
			newPortletPreferences.getPlid());
		Assert.assertEquals(existingPortletPreferences.getPortletId(),
			newPortletPreferences.getPortletId());
		Assert.assertEquals(existingPortletPreferences.getPreferences(),
			newPortletPreferences.getPreferences());
	}

	@Test
	public void testCountByPlid() {
		try {
			_persistence.countByPlid(RandomTestUtil.nextLong());

			_persistence.countByPlid(0L);
		}
		catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void testCountByPortletId() {
		try {
			_persistence.countByPortletId(StringPool.BLANK);

			_persistence.countByPortletId(StringPool.NULL);

			_persistence.countByPortletId((String)null);
		}
		catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void testCountByO_P() {
		try {
			_persistence.countByO_P(RandomTestUtil.nextInt(), StringPool.BLANK);

			_persistence.countByO_P(0, StringPool.NULL);

			_persistence.countByO_P(0, (String)null);
		}
		catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void testCountByP_P() {
		try {
			_persistence.countByP_P(RandomTestUtil.nextLong(), StringPool.BLANK);

			_persistence.countByP_P(0L, StringPool.NULL);

			_persistence.countByP_P(0L, (String)null);
		}
		catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void testCountByO_O_P() {
		try {
			_persistence.countByO_O_P(RandomTestUtil.nextLong(),
				RandomTestUtil.nextInt(), RandomTestUtil.nextLong());

			_persistence.countByO_O_P(0L, 0, 0L);
		}
		catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void testCountByO_O_PI() {
		try {
			_persistence.countByO_O_PI(RandomTestUtil.nextLong(),
				RandomTestUtil.nextInt(), StringPool.BLANK);

			_persistence.countByO_O_PI(0L, 0, StringPool.NULL);

			_persistence.countByO_O_PI(0L, 0, (String)null);
		}
		catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void testCountByO_P_P() {
		try {
			_persistence.countByO_P_P(RandomTestUtil.nextInt(),
				RandomTestUtil.nextLong(), StringPool.BLANK);

			_persistence.countByO_P_P(0, 0L, StringPool.NULL);

			_persistence.countByO_P_P(0, 0L, (String)null);
		}
		catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void testCountByO_O_P_P() {
		try {
			_persistence.countByO_O_P_P(RandomTestUtil.nextLong(),
				RandomTestUtil.nextInt(), RandomTestUtil.nextLong(),
				StringPool.BLANK);

			_persistence.countByO_O_P_P(0L, 0, 0L, StringPool.NULL);

			_persistence.countByO_O_P_P(0L, 0, 0L, (String)null);
		}
		catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void testFindByPrimaryKeyExisting() throws Exception {
		PortletPreferences newPortletPreferences = addPortletPreferences();

		PortletPreferences existingPortletPreferences = _persistence.findByPrimaryKey(newPortletPreferences.getPrimaryKey());

		Assert.assertEquals(existingPortletPreferences, newPortletPreferences);
	}

	@Test
	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		try {
			_persistence.findByPrimaryKey(pk);

			Assert.fail(
				"Missing entity did not throw NoSuchPortletPreferencesException");
		}
		catch (NoSuchPortletPreferencesException nsee) {
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
		return OrderByComparatorFactoryUtil.create("PortletPreferences",
			"mvccVersion", true, "portletPreferencesId", true, "ownerId", true,
			"ownerType", true, "plid", true, "portletId", true, "preferences",
			true);
	}

	@Test
	public void testFetchByPrimaryKeyExisting() throws Exception {
		PortletPreferences newPortletPreferences = addPortletPreferences();

		PortletPreferences existingPortletPreferences = _persistence.fetchByPrimaryKey(newPortletPreferences.getPrimaryKey());

		Assert.assertEquals(existingPortletPreferences, newPortletPreferences);
	}

	@Test
	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		PortletPreferences missingPortletPreferences = _persistence.fetchByPrimaryKey(pk);

		Assert.assertNull(missingPortletPreferences);
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereAllPrimaryKeysExist()
		throws Exception {
		PortletPreferences newPortletPreferences1 = addPortletPreferences();
		PortletPreferences newPortletPreferences2 = addPortletPreferences();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newPortletPreferences1.getPrimaryKey());
		primaryKeys.add(newPortletPreferences2.getPrimaryKey());

		Map<Serializable, PortletPreferences> portletPreferenceses = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(2, portletPreferenceses.size());
		Assert.assertEquals(newPortletPreferences1,
			portletPreferenceses.get(newPortletPreferences1.getPrimaryKey()));
		Assert.assertEquals(newPortletPreferences2,
			portletPreferenceses.get(newPortletPreferences2.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereNoPrimaryKeysExist()
		throws Exception {
		long pk1 = RandomTestUtil.nextLong();

		long pk2 = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(pk1);
		primaryKeys.add(pk2);

		Map<Serializable, PortletPreferences> portletPreferenceses = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(portletPreferenceses.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereSomePrimaryKeysExist()
		throws Exception {
		PortletPreferences newPortletPreferences = addPortletPreferences();

		long pk = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newPortletPreferences.getPrimaryKey());
		primaryKeys.add(pk);

		Map<Serializable, PortletPreferences> portletPreferenceses = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, portletPreferenceses.size());
		Assert.assertEquals(newPortletPreferences,
			portletPreferenceses.get(newPortletPreferences.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithNoPrimaryKeys()
		throws Exception {
		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		Map<Serializable, PortletPreferences> portletPreferenceses = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(portletPreferenceses.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithOnePrimaryKey()
		throws Exception {
		PortletPreferences newPortletPreferences = addPortletPreferences();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newPortletPreferences.getPrimaryKey());

		Map<Serializable, PortletPreferences> portletPreferenceses = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, portletPreferenceses.size());
		Assert.assertEquals(newPortletPreferences,
			portletPreferenceses.get(newPortletPreferences.getPrimaryKey()));
	}

	@Test
	public void testActionableDynamicQuery() throws Exception {
		final IntegerWrapper count = new IntegerWrapper();

		ActionableDynamicQuery actionableDynamicQuery = PortletPreferencesLocalServiceUtil.getActionableDynamicQuery();

		actionableDynamicQuery.setPerformActionMethod(new ActionableDynamicQuery.PerformActionMethod() {
				@Override
				public void performAction(Object object) {
					PortletPreferences portletPreferences = (PortletPreferences)object;

					Assert.assertNotNull(portletPreferences);

					count.increment();
				}
			});

		actionableDynamicQuery.performActions();

		Assert.assertEquals(count.getValue(), _persistence.countAll());
	}

	@Test
	public void testDynamicQueryByPrimaryKeyExisting()
		throws Exception {
		PortletPreferences newPortletPreferences = addPortletPreferences();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(PortletPreferences.class,
				PortletPreferences.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("portletPreferencesId",
				newPortletPreferences.getPortletPreferencesId()));

		List<PortletPreferences> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		PortletPreferences existingPortletPreferences = result.get(0);

		Assert.assertEquals(existingPortletPreferences, newPortletPreferences);
	}

	@Test
	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(PortletPreferences.class,
				PortletPreferences.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("portletPreferencesId",
				RandomTestUtil.nextLong()));

		List<PortletPreferences> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testDynamicQueryByProjectionExisting()
		throws Exception {
		PortletPreferences newPortletPreferences = addPortletPreferences();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(PortletPreferences.class,
				PortletPreferences.class.getClassLoader());

		dynamicQuery.setProjection(ProjectionFactoryUtil.property(
				"portletPreferencesId"));

		Object newPortletPreferencesId = newPortletPreferences.getPortletPreferencesId();

		dynamicQuery.add(RestrictionsFactoryUtil.in("portletPreferencesId",
				new Object[] { newPortletPreferencesId }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		Object existingPortletPreferencesId = result.get(0);

		Assert.assertEquals(existingPortletPreferencesId,
			newPortletPreferencesId);
	}

	@Test
	public void testDynamicQueryByProjectionMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(PortletPreferences.class,
				PortletPreferences.class.getClassLoader());

		dynamicQuery.setProjection(ProjectionFactoryUtil.property(
				"portletPreferencesId"));

		dynamicQuery.add(RestrictionsFactoryUtil.in("portletPreferencesId",
				new Object[] { RandomTestUtil.nextLong() }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testResetOriginalValues() throws Exception {
		if (!PropsValues.HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE) {
			return;
		}

		PortletPreferences newPortletPreferences = addPortletPreferences();

		_persistence.clearCache();

		PortletPreferencesModelImpl existingPortletPreferencesModelImpl = (PortletPreferencesModelImpl)_persistence.findByPrimaryKey(newPortletPreferences.getPrimaryKey());

		Assert.assertEquals(existingPortletPreferencesModelImpl.getOwnerId(),
			existingPortletPreferencesModelImpl.getOriginalOwnerId());
		Assert.assertEquals(existingPortletPreferencesModelImpl.getOwnerType(),
			existingPortletPreferencesModelImpl.getOriginalOwnerType());
		Assert.assertEquals(existingPortletPreferencesModelImpl.getPlid(),
			existingPortletPreferencesModelImpl.getOriginalPlid());
		Assert.assertTrue(Validator.equals(
				existingPortletPreferencesModelImpl.getPortletId(),
				existingPortletPreferencesModelImpl.getOriginalPortletId()));
	}

	protected PortletPreferences addPortletPreferences()
		throws Exception {
		long pk = RandomTestUtil.nextLong();

		PortletPreferences portletPreferences = _persistence.create(pk);

		portletPreferences.setMvccVersion(RandomTestUtil.nextLong());

		portletPreferences.setOwnerId(RandomTestUtil.nextLong());

		portletPreferences.setOwnerType(RandomTestUtil.nextInt());

		portletPreferences.setPlid(RandomTestUtil.nextLong());

		portletPreferences.setPortletId(RandomTestUtil.randomString());

		portletPreferences.setPreferences(RandomTestUtil.randomString());

		_persistence.update(portletPreferences);

		return portletPreferences;
	}

	private static Log _log = LogFactoryUtil.getLog(PortletPreferencesPersistenceTest.class);
	private ModelListener<PortletPreferences>[] _modelListeners;
	private PortletPreferencesPersistence _persistence = (PortletPreferencesPersistence)PortalBeanLocatorUtil.locate(PortletPreferencesPersistence.class.getName());
	private TransactionalPersistenceAdvice _transactionalPersistenceAdvice = (TransactionalPersistenceAdvice)PortalBeanLocatorUtil.locate(TransactionalPersistenceAdvice.class.getName());
}