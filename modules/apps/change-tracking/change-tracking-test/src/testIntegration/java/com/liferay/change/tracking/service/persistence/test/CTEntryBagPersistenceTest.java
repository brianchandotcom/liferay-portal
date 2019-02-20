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

import com.liferay.change.tracking.exception.NoSuchEntryBagException;
import com.liferay.change.tracking.model.CTEntryBag;
import com.liferay.change.tracking.service.CTEntryBagLocalServiceUtil;
import com.liferay.change.tracking.service.persistence.CTEntryBagPersistence;
import com.liferay.change.tracking.service.persistence.CTEntryBagUtil;

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

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import org.junit.runner.RunWith;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @generated
 */
@RunWith(Arquillian.class)
public class CTEntryBagPersistenceTest {
	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule = new AggregateTestRule(new LiferayIntegrationTestRule(),
			PersistenceTestRule.INSTANCE,
			new TransactionalTestRule(Propagation.REQUIRED,
				"com.liferay.change.tracking.service"));

	@Before
	public void setUp() {
		_persistence = CTEntryBagUtil.getPersistence();

		Class<?> clazz = _persistence.getClass();

		_dynamicQueryClassLoader = clazz.getClassLoader();
	}

	@After
	public void tearDown() throws Exception {
		Iterator<CTEntryBag> iterator = _ctEntryBags.iterator();

		while (iterator.hasNext()) {
			_persistence.remove(iterator.next());

			iterator.remove();
		}
	}

	@Test
	public void testCreate() throws Exception {
		long pk = RandomTestUtil.nextLong();

		CTEntryBag ctEntryBag = _persistence.create(pk);

		Assert.assertNotNull(ctEntryBag);

		Assert.assertEquals(ctEntryBag.getPrimaryKey(), pk);
	}

	@Test
	public void testRemove() throws Exception {
		CTEntryBag newCTEntryBag = addCTEntryBag();

		_persistence.remove(newCTEntryBag);

		CTEntryBag existingCTEntryBag = _persistence.fetchByPrimaryKey(newCTEntryBag.getPrimaryKey());

		Assert.assertNull(existingCTEntryBag);
	}

	@Test
	public void testUpdateNew() throws Exception {
		addCTEntryBag();
	}

	@Test
	public void testUpdateExisting() throws Exception {
		long pk = RandomTestUtil.nextLong();

		CTEntryBag newCTEntryBag = _persistence.create(pk);

		newCTEntryBag.setCompanyId(RandomTestUtil.nextLong());

		newCTEntryBag.setUserId(RandomTestUtil.nextLong());

		newCTEntryBag.setUserName(RandomTestUtil.randomString());

		newCTEntryBag.setCreateDate(RandomTestUtil.nextDate());

		newCTEntryBag.setModifiedDate(RandomTestUtil.nextDate());

		newCTEntryBag.setOwnerCTEntryId(RandomTestUtil.nextLong());

		newCTEntryBag.setCtCollectionId(RandomTestUtil.nextLong());

		_ctEntryBags.add(_persistence.update(newCTEntryBag));

		CTEntryBag existingCTEntryBag = _persistence.findByPrimaryKey(newCTEntryBag.getPrimaryKey());

		Assert.assertEquals(existingCTEntryBag.getCtEntryBagId(),
			newCTEntryBag.getCtEntryBagId());
		Assert.assertEquals(existingCTEntryBag.getCompanyId(),
			newCTEntryBag.getCompanyId());
		Assert.assertEquals(existingCTEntryBag.getUserId(),
			newCTEntryBag.getUserId());
		Assert.assertEquals(existingCTEntryBag.getUserName(),
			newCTEntryBag.getUserName());
		Assert.assertEquals(Time.getShortTimestamp(
				existingCTEntryBag.getCreateDate()),
			Time.getShortTimestamp(newCTEntryBag.getCreateDate()));
		Assert.assertEquals(Time.getShortTimestamp(
				existingCTEntryBag.getModifiedDate()),
			Time.getShortTimestamp(newCTEntryBag.getModifiedDate()));
		Assert.assertEquals(existingCTEntryBag.getOwnerCTEntryId(),
			newCTEntryBag.getOwnerCTEntryId());
		Assert.assertEquals(existingCTEntryBag.getCtCollectionId(),
			newCTEntryBag.getCtCollectionId());
	}

	@Test
	public void testCountByO_C() throws Exception {
		_persistence.countByO_C(RandomTestUtil.nextLong(),
			RandomTestUtil.nextLong());

		_persistence.countByO_C(0L, 0L);
	}

	@Test
	public void testFindByPrimaryKeyExisting() throws Exception {
		CTEntryBag newCTEntryBag = addCTEntryBag();

		CTEntryBag existingCTEntryBag = _persistence.findByPrimaryKey(newCTEntryBag.getPrimaryKey());

		Assert.assertEquals(existingCTEntryBag, newCTEntryBag);
	}

	@Test(expected = NoSuchEntryBagException.class)
	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		_persistence.findByPrimaryKey(pk);
	}

	@Test
	public void testFindAll() throws Exception {
		_persistence.findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			getOrderByComparator());
	}

	protected OrderByComparator<CTEntryBag> getOrderByComparator() {
		return OrderByComparatorFactoryUtil.create("CTEntryBag",
			"ctEntryBagId", true, "companyId", true, "userId", true,
			"userName", true, "createDate", true, "modifiedDate", true,
			"ownerCTEntryId", true, "ctCollectionId", true);
	}

	@Test
	public void testFetchByPrimaryKeyExisting() throws Exception {
		CTEntryBag newCTEntryBag = addCTEntryBag();

		CTEntryBag existingCTEntryBag = _persistence.fetchByPrimaryKey(newCTEntryBag.getPrimaryKey());

		Assert.assertEquals(existingCTEntryBag, newCTEntryBag);
	}

	@Test
	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		CTEntryBag missingCTEntryBag = _persistence.fetchByPrimaryKey(pk);

		Assert.assertNull(missingCTEntryBag);
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereAllPrimaryKeysExist()
		throws Exception {
		CTEntryBag newCTEntryBag1 = addCTEntryBag();
		CTEntryBag newCTEntryBag2 = addCTEntryBag();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newCTEntryBag1.getPrimaryKey());
		primaryKeys.add(newCTEntryBag2.getPrimaryKey());

		Map<Serializable, CTEntryBag> ctEntryBags = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(2, ctEntryBags.size());
		Assert.assertEquals(newCTEntryBag1,
			ctEntryBags.get(newCTEntryBag1.getPrimaryKey()));
		Assert.assertEquals(newCTEntryBag2,
			ctEntryBags.get(newCTEntryBag2.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereNoPrimaryKeysExist()
		throws Exception {
		long pk1 = RandomTestUtil.nextLong();

		long pk2 = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(pk1);
		primaryKeys.add(pk2);

		Map<Serializable, CTEntryBag> ctEntryBags = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(ctEntryBags.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereSomePrimaryKeysExist()
		throws Exception {
		CTEntryBag newCTEntryBag = addCTEntryBag();

		long pk = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newCTEntryBag.getPrimaryKey());
		primaryKeys.add(pk);

		Map<Serializable, CTEntryBag> ctEntryBags = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, ctEntryBags.size());
		Assert.assertEquals(newCTEntryBag,
			ctEntryBags.get(newCTEntryBag.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithNoPrimaryKeys()
		throws Exception {
		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		Map<Serializable, CTEntryBag> ctEntryBags = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(ctEntryBags.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithOnePrimaryKey()
		throws Exception {
		CTEntryBag newCTEntryBag = addCTEntryBag();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newCTEntryBag.getPrimaryKey());

		Map<Serializable, CTEntryBag> ctEntryBags = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, ctEntryBags.size());
		Assert.assertEquals(newCTEntryBag,
			ctEntryBags.get(newCTEntryBag.getPrimaryKey()));
	}

	@Test
	public void testActionableDynamicQuery() throws Exception {
		final IntegerWrapper count = new IntegerWrapper();

		ActionableDynamicQuery actionableDynamicQuery = CTEntryBagLocalServiceUtil.getActionableDynamicQuery();

		actionableDynamicQuery.setPerformActionMethod(new ActionableDynamicQuery.PerformActionMethod<CTEntryBag>() {
				@Override
				public void performAction(CTEntryBag ctEntryBag) {
					Assert.assertNotNull(ctEntryBag);

					count.increment();
				}
			});

		actionableDynamicQuery.performActions();

		Assert.assertEquals(count.getValue(), _persistence.countAll());
	}

	@Test
	public void testDynamicQueryByPrimaryKeyExisting()
		throws Exception {
		CTEntryBag newCTEntryBag = addCTEntryBag();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(CTEntryBag.class,
				_dynamicQueryClassLoader);

		dynamicQuery.add(RestrictionsFactoryUtil.eq("ctEntryBagId",
				newCTEntryBag.getCtEntryBagId()));

		List<CTEntryBag> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		CTEntryBag existingCTEntryBag = result.get(0);

		Assert.assertEquals(existingCTEntryBag, newCTEntryBag);
	}

	@Test
	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(CTEntryBag.class,
				_dynamicQueryClassLoader);

		dynamicQuery.add(RestrictionsFactoryUtil.eq("ctEntryBagId",
				RandomTestUtil.nextLong()));

		List<CTEntryBag> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testDynamicQueryByProjectionExisting()
		throws Exception {
		CTEntryBag newCTEntryBag = addCTEntryBag();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(CTEntryBag.class,
				_dynamicQueryClassLoader);

		dynamicQuery.setProjection(ProjectionFactoryUtil.property(
				"ctEntryBagId"));

		Object newCtEntryBagId = newCTEntryBag.getCtEntryBagId();

		dynamicQuery.add(RestrictionsFactoryUtil.in("ctEntryBagId",
				new Object[] { newCtEntryBagId }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		Object existingCtEntryBagId = result.get(0);

		Assert.assertEquals(existingCtEntryBagId, newCtEntryBagId);
	}

	@Test
	public void testDynamicQueryByProjectionMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(CTEntryBag.class,
				_dynamicQueryClassLoader);

		dynamicQuery.setProjection(ProjectionFactoryUtil.property(
				"ctEntryBagId"));

		dynamicQuery.add(RestrictionsFactoryUtil.in("ctEntryBagId",
				new Object[] { RandomTestUtil.nextLong() }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	protected CTEntryBag addCTEntryBag() throws Exception {
		long pk = RandomTestUtil.nextLong();

		CTEntryBag ctEntryBag = _persistence.create(pk);

		ctEntryBag.setCompanyId(RandomTestUtil.nextLong());

		ctEntryBag.setUserId(RandomTestUtil.nextLong());

		ctEntryBag.setUserName(RandomTestUtil.randomString());

		ctEntryBag.setCreateDate(RandomTestUtil.nextDate());

		ctEntryBag.setModifiedDate(RandomTestUtil.nextDate());

		ctEntryBag.setOwnerCTEntryId(RandomTestUtil.nextLong());

		ctEntryBag.setCtCollectionId(RandomTestUtil.nextLong());

		_ctEntryBags.add(_persistence.update(ctEntryBag));

		return ctEntryBag;
	}

	private List<CTEntryBag> _ctEntryBags = new ArrayList<CTEntryBag>();
	private CTEntryBagPersistence _persistence;
	private ClassLoader _dynamicQueryClassLoader;
}