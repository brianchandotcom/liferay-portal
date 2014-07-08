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

import com.liferay.portal.NoSuchLayoutSetPrototypeException;
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
import com.liferay.portal.model.LayoutSetPrototype;
import com.liferay.portal.model.ModelListener;
import com.liferay.portal.service.LayoutSetPrototypeLocalServiceUtil;
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
public class LayoutSetPrototypePersistenceTest {
	@Before
	public void setUp() {
		_modelListeners = _persistence.getListeners();

		for (ModelListener<LayoutSetPrototype> modelListener : _modelListeners) {
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

		for (ModelListener<LayoutSetPrototype> modelListener : _modelListeners) {
			_persistence.registerListener(modelListener);
		}
	}

	@Test
	public void testCreate() throws Exception {
		long pk = RandomTestUtil.nextLong();

		LayoutSetPrototype layoutSetPrototype = _persistence.create(pk);

		Assert.assertNotNull(layoutSetPrototype);

		Assert.assertEquals(layoutSetPrototype.getPrimaryKey(), pk);
	}

	@Test
	public void testRemove() throws Exception {
		LayoutSetPrototype newLayoutSetPrototype = addLayoutSetPrototype();

		_persistence.remove(newLayoutSetPrototype);

		LayoutSetPrototype existingLayoutSetPrototype = _persistence.fetchByPrimaryKey(newLayoutSetPrototype.getPrimaryKey());

		Assert.assertNull(existingLayoutSetPrototype);
	}

	@Test
	public void testUpdateNew() throws Exception {
		addLayoutSetPrototype();
	}

	@Test
	public void testUpdateExisting() throws Exception {
		long pk = RandomTestUtil.nextLong();

		LayoutSetPrototype newLayoutSetPrototype = _persistence.create(pk);

		newLayoutSetPrototype.setMvccVersion(RandomTestUtil.nextLong());

		newLayoutSetPrototype.setUuid(RandomTestUtil.randomString());

		newLayoutSetPrototype.setCompanyId(RandomTestUtil.nextLong());

		newLayoutSetPrototype.setUserId(RandomTestUtil.nextLong());

		newLayoutSetPrototype.setUserName(RandomTestUtil.randomString());

		newLayoutSetPrototype.setCreateDate(RandomTestUtil.nextDate());

		newLayoutSetPrototype.setModifiedDate(RandomTestUtil.nextDate());

		newLayoutSetPrototype.setName(RandomTestUtil.randomString());

		newLayoutSetPrototype.setDescription(RandomTestUtil.randomString());

		newLayoutSetPrototype.setSettings(RandomTestUtil.randomString());

		newLayoutSetPrototype.setActive(RandomTestUtil.randomBoolean());

		_persistence.update(newLayoutSetPrototype);

		LayoutSetPrototype existingLayoutSetPrototype = _persistence.findByPrimaryKey(newLayoutSetPrototype.getPrimaryKey());

		Assert.assertEquals(existingLayoutSetPrototype.getMvccVersion(),
			newLayoutSetPrototype.getMvccVersion());
		Assert.assertEquals(existingLayoutSetPrototype.getUuid(),
			newLayoutSetPrototype.getUuid());
		Assert.assertEquals(existingLayoutSetPrototype.getLayoutSetPrototypeId(),
			newLayoutSetPrototype.getLayoutSetPrototypeId());
		Assert.assertEquals(existingLayoutSetPrototype.getCompanyId(),
			newLayoutSetPrototype.getCompanyId());
		Assert.assertEquals(existingLayoutSetPrototype.getUserId(),
			newLayoutSetPrototype.getUserId());
		Assert.assertEquals(existingLayoutSetPrototype.getUserName(),
			newLayoutSetPrototype.getUserName());
		Assert.assertEquals(Time.getShortTimestamp(
				existingLayoutSetPrototype.getCreateDate()),
			Time.getShortTimestamp(newLayoutSetPrototype.getCreateDate()));
		Assert.assertEquals(Time.getShortTimestamp(
				existingLayoutSetPrototype.getModifiedDate()),
			Time.getShortTimestamp(newLayoutSetPrototype.getModifiedDate()));
		Assert.assertEquals(existingLayoutSetPrototype.getName(),
			newLayoutSetPrototype.getName());
		Assert.assertEquals(existingLayoutSetPrototype.getDescription(),
			newLayoutSetPrototype.getDescription());
		Assert.assertEquals(existingLayoutSetPrototype.getSettings(),
			newLayoutSetPrototype.getSettings());
		Assert.assertEquals(existingLayoutSetPrototype.getActive(),
			newLayoutSetPrototype.getActive());
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
	public void testCountByC_A() {
		try {
			_persistence.countByC_A(RandomTestUtil.nextLong(),
				RandomTestUtil.randomBoolean());

			_persistence.countByC_A(0L, RandomTestUtil.randomBoolean());
		}
		catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void testFindByPrimaryKeyExisting() throws Exception {
		LayoutSetPrototype newLayoutSetPrototype = addLayoutSetPrototype();

		LayoutSetPrototype existingLayoutSetPrototype = _persistence.findByPrimaryKey(newLayoutSetPrototype.getPrimaryKey());

		Assert.assertEquals(existingLayoutSetPrototype, newLayoutSetPrototype);
	}

	@Test
	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		try {
			_persistence.findByPrimaryKey(pk);

			Assert.fail(
				"Missing entity did not throw NoSuchLayoutSetPrototypeException");
		}
		catch (NoSuchLayoutSetPrototypeException nsee) {
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
		return OrderByComparatorFactoryUtil.create("LayoutSetPrototype",
			"mvccVersion", true, "uuid", true, "layoutSetPrototypeId", true,
			"companyId", true, "userId", true, "userName", true, "createDate",
			true, "modifiedDate", true, "name", true, "description", true,
			"settings", true, "active", true);
	}

	@Test
	public void testFetchByPrimaryKeyExisting() throws Exception {
		LayoutSetPrototype newLayoutSetPrototype = addLayoutSetPrototype();

		LayoutSetPrototype existingLayoutSetPrototype = _persistence.fetchByPrimaryKey(newLayoutSetPrototype.getPrimaryKey());

		Assert.assertEquals(existingLayoutSetPrototype, newLayoutSetPrototype);
	}

	@Test
	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		LayoutSetPrototype missingLayoutSetPrototype = _persistence.fetchByPrimaryKey(pk);

		Assert.assertNull(missingLayoutSetPrototype);
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereAllPrimaryKeysExist()
		throws Exception {
		LayoutSetPrototype newLayoutSetPrototype1 = addLayoutSetPrototype();
		LayoutSetPrototype newLayoutSetPrototype2 = addLayoutSetPrototype();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newLayoutSetPrototype1.getPrimaryKey());
		primaryKeys.add(newLayoutSetPrototype2.getPrimaryKey());

		Map<Serializable, LayoutSetPrototype> layoutSetPrototypes = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(2, layoutSetPrototypes.size());
		Assert.assertEquals(newLayoutSetPrototype1,
			layoutSetPrototypes.get(newLayoutSetPrototype1.getPrimaryKey()));
		Assert.assertEquals(newLayoutSetPrototype2,
			layoutSetPrototypes.get(newLayoutSetPrototype2.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereNoPrimaryKeysExist()
		throws Exception {
		long pk1 = RandomTestUtil.nextLong();

		long pk2 = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(pk1);
		primaryKeys.add(pk2);

		Map<Serializable, LayoutSetPrototype> layoutSetPrototypes = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(layoutSetPrototypes.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereSomePrimaryKeysExist()
		throws Exception {
		LayoutSetPrototype newLayoutSetPrototype = addLayoutSetPrototype();

		long pk = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newLayoutSetPrototype.getPrimaryKey());
		primaryKeys.add(pk);

		Map<Serializable, LayoutSetPrototype> layoutSetPrototypes = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, layoutSetPrototypes.size());
		Assert.assertEquals(newLayoutSetPrototype,
			layoutSetPrototypes.get(newLayoutSetPrototype.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithNoPrimaryKeys()
		throws Exception {
		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		Map<Serializable, LayoutSetPrototype> layoutSetPrototypes = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(layoutSetPrototypes.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithOnePrimaryKey()
		throws Exception {
		LayoutSetPrototype newLayoutSetPrototype = addLayoutSetPrototype();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newLayoutSetPrototype.getPrimaryKey());

		Map<Serializable, LayoutSetPrototype> layoutSetPrototypes = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, layoutSetPrototypes.size());
		Assert.assertEquals(newLayoutSetPrototype,
			layoutSetPrototypes.get(newLayoutSetPrototype.getPrimaryKey()));
	}

	@Test
	public void testActionableDynamicQuery() throws Exception {
		final IntegerWrapper count = new IntegerWrapper();

		ActionableDynamicQuery actionableDynamicQuery = LayoutSetPrototypeLocalServiceUtil.getActionableDynamicQuery();

		actionableDynamicQuery.setPerformActionMethod(new ActionableDynamicQuery.PerformActionMethod() {
				@Override
				public void performAction(Object object) {
					LayoutSetPrototype layoutSetPrototype = (LayoutSetPrototype)object;

					Assert.assertNotNull(layoutSetPrototype);

					count.increment();
				}
			});

		actionableDynamicQuery.performActions();

		Assert.assertEquals(count.getValue(), _persistence.countAll());
	}

	@Test
	public void testDynamicQueryByPrimaryKeyExisting()
		throws Exception {
		LayoutSetPrototype newLayoutSetPrototype = addLayoutSetPrototype();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(LayoutSetPrototype.class,
				LayoutSetPrototype.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("layoutSetPrototypeId",
				newLayoutSetPrototype.getLayoutSetPrototypeId()));

		List<LayoutSetPrototype> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		LayoutSetPrototype existingLayoutSetPrototype = result.get(0);

		Assert.assertEquals(existingLayoutSetPrototype, newLayoutSetPrototype);
	}

	@Test
	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(LayoutSetPrototype.class,
				LayoutSetPrototype.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("layoutSetPrototypeId",
				RandomTestUtil.nextLong()));

		List<LayoutSetPrototype> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testDynamicQueryByProjectionExisting()
		throws Exception {
		LayoutSetPrototype newLayoutSetPrototype = addLayoutSetPrototype();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(LayoutSetPrototype.class,
				LayoutSetPrototype.class.getClassLoader());

		dynamicQuery.setProjection(ProjectionFactoryUtil.property(
				"layoutSetPrototypeId"));

		Object newLayoutSetPrototypeId = newLayoutSetPrototype.getLayoutSetPrototypeId();

		dynamicQuery.add(RestrictionsFactoryUtil.in("layoutSetPrototypeId",
				new Object[] { newLayoutSetPrototypeId }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		Object existingLayoutSetPrototypeId = result.get(0);

		Assert.assertEquals(existingLayoutSetPrototypeId,
			newLayoutSetPrototypeId);
	}

	@Test
	public void testDynamicQueryByProjectionMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(LayoutSetPrototype.class,
				LayoutSetPrototype.class.getClassLoader());

		dynamicQuery.setProjection(ProjectionFactoryUtil.property(
				"layoutSetPrototypeId"));

		dynamicQuery.add(RestrictionsFactoryUtil.in("layoutSetPrototypeId",
				new Object[] { RandomTestUtil.nextLong() }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	protected LayoutSetPrototype addLayoutSetPrototype()
		throws Exception {
		long pk = RandomTestUtil.nextLong();

		LayoutSetPrototype layoutSetPrototype = _persistence.create(pk);

		layoutSetPrototype.setMvccVersion(RandomTestUtil.nextLong());

		layoutSetPrototype.setUuid(RandomTestUtil.randomString());

		layoutSetPrototype.setCompanyId(RandomTestUtil.nextLong());

		layoutSetPrototype.setUserId(RandomTestUtil.nextLong());

		layoutSetPrototype.setUserName(RandomTestUtil.randomString());

		layoutSetPrototype.setCreateDate(RandomTestUtil.nextDate());

		layoutSetPrototype.setModifiedDate(RandomTestUtil.nextDate());

		layoutSetPrototype.setName(RandomTestUtil.randomString());

		layoutSetPrototype.setDescription(RandomTestUtil.randomString());

		layoutSetPrototype.setSettings(RandomTestUtil.randomString());

		layoutSetPrototype.setActive(RandomTestUtil.randomBoolean());

		_persistence.update(layoutSetPrototype);

		return layoutSetPrototype;
	}

	private static Log _log = LogFactoryUtil.getLog(LayoutSetPrototypePersistenceTest.class);
	private ModelListener<LayoutSetPrototype>[] _modelListeners;
	private LayoutSetPrototypePersistence _persistence = (LayoutSetPrototypePersistence)PortalBeanLocatorUtil.locate(LayoutSetPrototypePersistence.class.getName());
	private TransactionalPersistenceAdvice _transactionalPersistenceAdvice = (TransactionalPersistenceAdvice)PortalBeanLocatorUtil.locate(TransactionalPersistenceAdvice.class.getName());
}