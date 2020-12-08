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

package com.liferay.change.tracking.service.persistence.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.change.tracking.exception.NoSuchCollectionException;
import com.liferay.change.tracking.model.CTCollection;
import com.liferay.change.tracking.service.CTCollectionLocalServiceUtil;
import com.liferay.change.tracking.service.persistence.CTCollectionPersistence;
import com.liferay.change.tracking.service.persistence.CTCollectionUtil;
import com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQueryFactoryUtil;
import com.liferay.portal.kernel.dao.orm.ProjectionFactoryUtil;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.RestrictionsFactoryUtil;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.transaction.Propagation;
import com.liferay.portal.kernel.util.IntegerWrapper;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.OrderByComparatorFactoryUtil;
import com.liferay.portal.kernel.util.Time;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PersistenceTestRule;
import com.liferay.portal.test.rule.TransactionalTestRule;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @generated
 */
@RunWith(Arquillian.class)
public class CTCollectionPersistenceTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(), PersistenceTestRule.INSTANCE,
			new TransactionalTestRule(
				Propagation.REQUIRED, "com.liferay.change.tracking.service"));

	@Before
	public void setUp() {
		_persistence = CTCollectionUtil.getPersistence();

		Class<?> clazz = _persistence.getClass();

		_dynamicQueryClassLoader = clazz.getClassLoader();
	}

	@After
	public void tearDown() throws Exception {
		Iterator<CTCollection> iterator = _ctCollections.iterator();

		while (iterator.hasNext()) {
			_persistence.remove(iterator.next());

			iterator.remove();
		}
	}

	@Test
	public void testCreate() throws Exception {
		long pk = RandomTestUtil.nextLong();

		CTCollection ctCollection = _persistence.create(pk);

		Assert.assertNotNull(ctCollection);

		Assert.assertEquals(ctCollection.getPrimaryKey(), pk);
	}

	@Test
	public void testRemove() throws Exception {
		CTCollection newCTCollection = addCTCollection();

		_persistence.remove(newCTCollection);

		CTCollection existingCTCollection = _persistence.fetchByPrimaryKey(
			newCTCollection.getPrimaryKey());

		Assert.assertNull(existingCTCollection);
	}

	@Test
	public void testUpdateNew() throws Exception {
		addCTCollection();
	}

	@Test
	public void testUpdateExisting() throws Exception {
		long pk = RandomTestUtil.nextLong();

		CTCollection newCTCollection = _persistence.create(pk);

		newCTCollection.setMvccVersion(RandomTestUtil.nextLong());

		newCTCollection.setCompanyId(RandomTestUtil.nextLong());

		newCTCollection.setUserId(RandomTestUtil.nextLong());

		newCTCollection.setCreateDate(RandomTestUtil.nextDate());

		newCTCollection.setModifiedDate(RandomTestUtil.nextDate());

		newCTCollection.setSchemaVersionId(RandomTestUtil.nextLong());

		newCTCollection.setName(RandomTestUtil.randomString());

		newCTCollection.setDescription(RandomTestUtil.randomString());

		newCTCollection.setStatus(RandomTestUtil.nextInt());

		newCTCollection.setStatusByUserId(RandomTestUtil.nextLong());

		newCTCollection.setStatusDate(RandomTestUtil.nextDate());

		_ctCollections.add(_persistence.update(newCTCollection));

		CTCollection existingCTCollection = _persistence.findByPrimaryKey(
			newCTCollection.getPrimaryKey());

		Assert.assertEquals(
			existingCTCollection.getMvccVersion(),
			newCTCollection.getMvccVersion());
		Assert.assertEquals(
			existingCTCollection.getCtCollectionId(),
			newCTCollection.getCtCollectionId());
		Assert.assertEquals(
			existingCTCollection.getCompanyId(),
			newCTCollection.getCompanyId());
		Assert.assertEquals(
			existingCTCollection.getUserId(), newCTCollection.getUserId());
		Assert.assertEquals(
			Time.getShortTimestamp(existingCTCollection.getCreateDate()),
			Time.getShortTimestamp(newCTCollection.getCreateDate()));
		Assert.assertEquals(
			Time.getShortTimestamp(existingCTCollection.getModifiedDate()),
			Time.getShortTimestamp(newCTCollection.getModifiedDate()));
		Assert.assertEquals(
			existingCTCollection.getSchemaVersionId(),
			newCTCollection.getSchemaVersionId());
		Assert.assertEquals(
			existingCTCollection.getName(), newCTCollection.getName());
		Assert.assertEquals(
			existingCTCollection.getDescription(),
			newCTCollection.getDescription());
		Assert.assertEquals(
			existingCTCollection.getStatus(), newCTCollection.getStatus());
		Assert.assertEquals(
			existingCTCollection.getStatusByUserId(),
			newCTCollection.getStatusByUserId());
		Assert.assertEquals(
			Time.getShortTimestamp(existingCTCollection.getStatusDate()),
			Time.getShortTimestamp(newCTCollection.getStatusDate()));
	}

	@Test
	public void testCountByCompanyId() throws Exception {
		_persistence.countByCompanyId(RandomTestUtil.nextLong());

		_persistence.countByCompanyId(0L);
	}

	@Test
	public void testCountBySchemaVersionId() throws Exception {
		_persistence.countBySchemaVersionId(RandomTestUtil.nextLong());

		_persistence.countBySchemaVersionId(0L);
	}

	@Test
	public void testCountByC_S() throws Exception {
		_persistence.countByC_S(
			RandomTestUtil.nextLong(), RandomTestUtil.nextInt());

		_persistence.countByC_S(0L, 0);
	}

	@Test
	public void testCountByC_SArrayable() throws Exception {
		_persistence.countByC_S(
			RandomTestUtil.nextLong(), new int[] {RandomTestUtil.nextInt(), 0});
	}

	@Test
	public void testFindByPrimaryKeyExisting() throws Exception {
		CTCollection newCTCollection = addCTCollection();

		CTCollection existingCTCollection = _persistence.findByPrimaryKey(
			newCTCollection.getPrimaryKey());

		Assert.assertEquals(existingCTCollection, newCTCollection);
	}

	@Test(expected = NoSuchCollectionException.class)
	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		_persistence.findByPrimaryKey(pk);
	}

	@Test
	public void testFindAll() throws Exception {
		_persistence.findAll(
			QueryUtil.ALL_POS, QueryUtil.ALL_POS, getOrderByComparator());
	}

	protected OrderByComparator<CTCollection> getOrderByComparator() {
		return OrderByComparatorFactoryUtil.create(
			"CTCollection", "mvccVersion", true, "ctCollectionId", true,
			"companyId", true, "userId", true, "createDate", true,
			"modifiedDate", true, "schemaVersionId", true, "name", true,
			"description", true, "status", true, "statusByUserId", true,
			"statusDate", true);
	}

	@Test
	public void testFetchByPrimaryKeyExisting() throws Exception {
		CTCollection newCTCollection = addCTCollection();

		CTCollection existingCTCollection = _persistence.fetchByPrimaryKey(
			newCTCollection.getPrimaryKey());

		Assert.assertEquals(existingCTCollection, newCTCollection);
	}

	@Test
	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		CTCollection missingCTCollection = _persistence.fetchByPrimaryKey(pk);

		Assert.assertNull(missingCTCollection);
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereAllPrimaryKeysExist()
		throws Exception {

		CTCollection newCTCollection1 = addCTCollection();
		CTCollection newCTCollection2 = addCTCollection();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newCTCollection1.getPrimaryKey());
		primaryKeys.add(newCTCollection2.getPrimaryKey());

		Map<Serializable, CTCollection> ctCollections =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(2, ctCollections.size());
		Assert.assertEquals(
			newCTCollection1,
			ctCollections.get(newCTCollection1.getPrimaryKey()));
		Assert.assertEquals(
			newCTCollection2,
			ctCollections.get(newCTCollection2.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereNoPrimaryKeysExist()
		throws Exception {

		long pk1 = RandomTestUtil.nextLong();

		long pk2 = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(pk1);
		primaryKeys.add(pk2);

		Map<Serializable, CTCollection> ctCollections =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(ctCollections.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereSomePrimaryKeysExist()
		throws Exception {

		CTCollection newCTCollection = addCTCollection();

		long pk = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newCTCollection.getPrimaryKey());
		primaryKeys.add(pk);

		Map<Serializable, CTCollection> ctCollections =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, ctCollections.size());
		Assert.assertEquals(
			newCTCollection,
			ctCollections.get(newCTCollection.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithNoPrimaryKeys() throws Exception {
		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		Map<Serializable, CTCollection> ctCollections =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(ctCollections.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithOnePrimaryKey() throws Exception {
		CTCollection newCTCollection = addCTCollection();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newCTCollection.getPrimaryKey());

		Map<Serializable, CTCollection> ctCollections =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, ctCollections.size());
		Assert.assertEquals(
			newCTCollection,
			ctCollections.get(newCTCollection.getPrimaryKey()));
	}

	@Test
	public void testActionableDynamicQuery() throws Exception {
		final IntegerWrapper count = new IntegerWrapper();

		ActionableDynamicQuery actionableDynamicQuery =
			CTCollectionLocalServiceUtil.getActionableDynamicQuery();

		actionableDynamicQuery.setPerformActionMethod(
			new ActionableDynamicQuery.PerformActionMethod<CTCollection>() {

				@Override
				public void performAction(CTCollection ctCollection) {
					Assert.assertNotNull(ctCollection);

					count.increment();
				}

			});

		actionableDynamicQuery.performActions();

		Assert.assertEquals(count.getValue(), _persistence.countAll());
	}

	@Test
	public void testDynamicQueryByPrimaryKeyExisting() throws Exception {
		CTCollection newCTCollection = addCTCollection();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			CTCollection.class, _dynamicQueryClassLoader);

		dynamicQuery.add(
			RestrictionsFactoryUtil.eq(
				"ctCollectionId", newCTCollection.getCtCollectionId()));

		List<CTCollection> result = _persistence.findWithDynamicQuery(
			dynamicQuery);

		Assert.assertEquals(1, result.size());

		CTCollection existingCTCollection = result.get(0);

		Assert.assertEquals(existingCTCollection, newCTCollection);
	}

	@Test
	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			CTCollection.class, _dynamicQueryClassLoader);

		dynamicQuery.add(
			RestrictionsFactoryUtil.eq(
				"ctCollectionId", RandomTestUtil.nextLong()));

		List<CTCollection> result = _persistence.findWithDynamicQuery(
			dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testDynamicQueryByProjectionExisting() throws Exception {
		CTCollection newCTCollection = addCTCollection();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			CTCollection.class, _dynamicQueryClassLoader);

		dynamicQuery.setProjection(
			ProjectionFactoryUtil.property("ctCollectionId"));

		Object newCtCollectionId = newCTCollection.getCtCollectionId();

		dynamicQuery.add(
			RestrictionsFactoryUtil.in(
				"ctCollectionId", new Object[] {newCtCollectionId}));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		Object existingCtCollectionId = result.get(0);

		Assert.assertEquals(existingCtCollectionId, newCtCollectionId);
	}

	@Test
	public void testDynamicQueryByProjectionMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			CTCollection.class, _dynamicQueryClassLoader);

		dynamicQuery.setProjection(
			ProjectionFactoryUtil.property("ctCollectionId"));

		dynamicQuery.add(
			RestrictionsFactoryUtil.in(
				"ctCollectionId", new Object[] {RandomTestUtil.nextLong()}));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	protected CTCollection addCTCollection() throws Exception {
		long pk = RandomTestUtil.nextLong();

		CTCollection ctCollection = _persistence.create(pk);

		ctCollection.setMvccVersion(RandomTestUtil.nextLong());

		ctCollection.setCompanyId(RandomTestUtil.nextLong());

		ctCollection.setUserId(RandomTestUtil.nextLong());

		ctCollection.setCreateDate(RandomTestUtil.nextDate());

		ctCollection.setModifiedDate(RandomTestUtil.nextDate());

		ctCollection.setSchemaVersionId(RandomTestUtil.nextLong());

		ctCollection.setName(RandomTestUtil.randomString());

		ctCollection.setDescription(RandomTestUtil.randomString());

		ctCollection.setStatus(RandomTestUtil.nextInt());

		ctCollection.setStatusByUserId(RandomTestUtil.nextLong());

		ctCollection.setStatusDate(RandomTestUtil.nextDate());

		_ctCollections.add(_persistence.update(ctCollection));

		return ctCollection;
	}

	private List<CTCollection> _ctCollections = new ArrayList<CTCollection>();
	private CTCollectionPersistence _persistence;
	private ClassLoader _dynamicQueryClassLoader;

}