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

package com.liferay.depot.service.persistence.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.depot.exception.NoSuchEntryGroupRelException;
import com.liferay.depot.model.DepotEntryGroupRel;
import com.liferay.depot.service.DepotEntryGroupRelLocalServiceUtil;
import com.liferay.depot.service.persistence.DepotEntryGroupRelPersistence;
import com.liferay.depot.service.persistence.DepotEntryGroupRelUtil;
import com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQueryFactoryUtil;
import com.liferay.portal.kernel.dao.orm.ProjectionFactoryUtil;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.RestrictionsFactoryUtil;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.transaction.Propagation;
import com.liferay.portal.kernel.util.IntegerWrapper;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.OrderByComparatorFactoryUtil;
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
public class DepotEntryGroupRelPersistenceTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(), PersistenceTestRule.INSTANCE,
			new TransactionalTestRule(
				Propagation.REQUIRED, "com.liferay.depot.service"));

	@Before
	public void setUp() {
		_persistence = DepotEntryGroupRelUtil.getPersistence();

		Class<?> clazz = _persistence.getClass();

		_dynamicQueryClassLoader = clazz.getClassLoader();
	}

	@After
	public void tearDown() throws Exception {
		Iterator<DepotEntryGroupRel> iterator = _depotEntryGroupRels.iterator();

		while (iterator.hasNext()) {
			_persistence.remove(iterator.next());

			iterator.remove();
		}
	}

	@Test
	public void testCreate() throws Exception {
		long pk = RandomTestUtil.nextLong();

		DepotEntryGroupRel depotEntryGroupRel = _persistence.create(pk);

		Assert.assertNotNull(depotEntryGroupRel);

		Assert.assertEquals(depotEntryGroupRel.getPrimaryKey(), pk);
	}

	@Test
	public void testRemove() throws Exception {
		DepotEntryGroupRel newDepotEntryGroupRel = addDepotEntryGroupRel();

		_persistence.remove(newDepotEntryGroupRel);

		DepotEntryGroupRel existingDepotEntryGroupRel =
			_persistence.fetchByPrimaryKey(
				newDepotEntryGroupRel.getPrimaryKey());

		Assert.assertNull(existingDepotEntryGroupRel);
	}

	@Test
	public void testUpdateNew() throws Exception {
		addDepotEntryGroupRel();
	}

	@Test
	public void testUpdateExisting() throws Exception {
		long pk = RandomTestUtil.nextLong();

		DepotEntryGroupRel newDepotEntryGroupRel = _persistence.create(pk);

		newDepotEntryGroupRel.setMvccVersion(RandomTestUtil.nextLong());

		newDepotEntryGroupRel.setDepotEntryId(RandomTestUtil.nextLong());

		newDepotEntryGroupRel.setToGroupId(RandomTestUtil.nextLong());

		_depotEntryGroupRels.add(_persistence.update(newDepotEntryGroupRel));

		DepotEntryGroupRel existingDepotEntryGroupRel =
			_persistence.findByPrimaryKey(
				newDepotEntryGroupRel.getPrimaryKey());

		Assert.assertEquals(
			existingDepotEntryGroupRel.getMvccVersion(),
			newDepotEntryGroupRel.getMvccVersion());
		Assert.assertEquals(
			existingDepotEntryGroupRel.getDepotEntryGroupRelId(),
			newDepotEntryGroupRel.getDepotEntryGroupRelId());
		Assert.assertEquals(
			existingDepotEntryGroupRel.getDepotEntryId(),
			newDepotEntryGroupRel.getDepotEntryId());
		Assert.assertEquals(
			existingDepotEntryGroupRel.getToGroupId(),
			newDepotEntryGroupRel.getToGroupId());
	}

	@Test
	public void testCountByDepotEntryId() throws Exception {
		_persistence.countByDepotEntryId(RandomTestUtil.nextLong());

		_persistence.countByDepotEntryId(0L);
	}

	@Test
	public void testCountByToGroupId() throws Exception {
		_persistence.countByToGroupId(RandomTestUtil.nextLong());

		_persistence.countByToGroupId(0L);
	}

	@Test
	public void testCountByD_TGI() throws Exception {
		_persistence.countByD_TGI(
			RandomTestUtil.nextLong(), RandomTestUtil.nextLong());

		_persistence.countByD_TGI(0L, 0L);
	}

	@Test
	public void testFindByPrimaryKeyExisting() throws Exception {
		DepotEntryGroupRel newDepotEntryGroupRel = addDepotEntryGroupRel();

		DepotEntryGroupRel existingDepotEntryGroupRel =
			_persistence.findByPrimaryKey(
				newDepotEntryGroupRel.getPrimaryKey());

		Assert.assertEquals(existingDepotEntryGroupRel, newDepotEntryGroupRel);
	}

	@Test(expected = NoSuchEntryGroupRelException.class)
	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		_persistence.findByPrimaryKey(pk);
	}

	@Test
	public void testFindAll() throws Exception {
		_persistence.findAll(
			QueryUtil.ALL_POS, QueryUtil.ALL_POS, getOrderByComparator());
	}

	protected OrderByComparator<DepotEntryGroupRel> getOrderByComparator() {
		return OrderByComparatorFactoryUtil.create(
			"DepotEntryGroupRel", "mvccVersion", true, "depotEntryGroupRelId",
			true, "depotEntryId", true, "toGroupId", true);
	}

	@Test
	public void testFetchByPrimaryKeyExisting() throws Exception {
		DepotEntryGroupRel newDepotEntryGroupRel = addDepotEntryGroupRel();

		DepotEntryGroupRel existingDepotEntryGroupRel =
			_persistence.fetchByPrimaryKey(
				newDepotEntryGroupRel.getPrimaryKey());

		Assert.assertEquals(existingDepotEntryGroupRel, newDepotEntryGroupRel);
	}

	@Test
	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		DepotEntryGroupRel missingDepotEntryGroupRel =
			_persistence.fetchByPrimaryKey(pk);

		Assert.assertNull(missingDepotEntryGroupRel);
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereAllPrimaryKeysExist()
		throws Exception {

		DepotEntryGroupRel newDepotEntryGroupRel1 = addDepotEntryGroupRel();
		DepotEntryGroupRel newDepotEntryGroupRel2 = addDepotEntryGroupRel();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newDepotEntryGroupRel1.getPrimaryKey());
		primaryKeys.add(newDepotEntryGroupRel2.getPrimaryKey());

		Map<Serializable, DepotEntryGroupRel> depotEntryGroupRels =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(2, depotEntryGroupRels.size());
		Assert.assertEquals(
			newDepotEntryGroupRel1,
			depotEntryGroupRels.get(newDepotEntryGroupRel1.getPrimaryKey()));
		Assert.assertEquals(
			newDepotEntryGroupRel2,
			depotEntryGroupRels.get(newDepotEntryGroupRel2.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereNoPrimaryKeysExist()
		throws Exception {

		long pk1 = RandomTestUtil.nextLong();

		long pk2 = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(pk1);
		primaryKeys.add(pk2);

		Map<Serializable, DepotEntryGroupRel> depotEntryGroupRels =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(depotEntryGroupRels.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereSomePrimaryKeysExist()
		throws Exception {

		DepotEntryGroupRel newDepotEntryGroupRel = addDepotEntryGroupRel();

		long pk = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newDepotEntryGroupRel.getPrimaryKey());
		primaryKeys.add(pk);

		Map<Serializable, DepotEntryGroupRel> depotEntryGroupRels =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, depotEntryGroupRels.size());
		Assert.assertEquals(
			newDepotEntryGroupRel,
			depotEntryGroupRels.get(newDepotEntryGroupRel.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithNoPrimaryKeys() throws Exception {
		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		Map<Serializable, DepotEntryGroupRel> depotEntryGroupRels =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(depotEntryGroupRels.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithOnePrimaryKey() throws Exception {
		DepotEntryGroupRel newDepotEntryGroupRel = addDepotEntryGroupRel();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newDepotEntryGroupRel.getPrimaryKey());

		Map<Serializable, DepotEntryGroupRel> depotEntryGroupRels =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, depotEntryGroupRels.size());
		Assert.assertEquals(
			newDepotEntryGroupRel,
			depotEntryGroupRels.get(newDepotEntryGroupRel.getPrimaryKey()));
	}

	@Test
	public void testActionableDynamicQuery() throws Exception {
		final IntegerWrapper count = new IntegerWrapper();

		ActionableDynamicQuery actionableDynamicQuery =
			DepotEntryGroupRelLocalServiceUtil.getActionableDynamicQuery();

		actionableDynamicQuery.setPerformActionMethod(
			new ActionableDynamicQuery.PerformActionMethod
				<DepotEntryGroupRel>() {

				@Override
				public void performAction(
					DepotEntryGroupRel depotEntryGroupRel) {

					Assert.assertNotNull(depotEntryGroupRel);

					count.increment();
				}

			});

		actionableDynamicQuery.performActions();

		Assert.assertEquals(count.getValue(), _persistence.countAll());
	}

	@Test
	public void testDynamicQueryByPrimaryKeyExisting() throws Exception {
		DepotEntryGroupRel newDepotEntryGroupRel = addDepotEntryGroupRel();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			DepotEntryGroupRel.class, _dynamicQueryClassLoader);

		dynamicQuery.add(
			RestrictionsFactoryUtil.eq(
				"depotEntryGroupRelId",
				newDepotEntryGroupRel.getDepotEntryGroupRelId()));

		List<DepotEntryGroupRel> result = _persistence.findWithDynamicQuery(
			dynamicQuery);

		Assert.assertEquals(1, result.size());

		DepotEntryGroupRel existingDepotEntryGroupRel = result.get(0);

		Assert.assertEquals(existingDepotEntryGroupRel, newDepotEntryGroupRel);
	}

	@Test
	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			DepotEntryGroupRel.class, _dynamicQueryClassLoader);

		dynamicQuery.add(
			RestrictionsFactoryUtil.eq(
				"depotEntryGroupRelId", RandomTestUtil.nextLong()));

		List<DepotEntryGroupRel> result = _persistence.findWithDynamicQuery(
			dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testDynamicQueryByProjectionExisting() throws Exception {
		DepotEntryGroupRel newDepotEntryGroupRel = addDepotEntryGroupRel();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			DepotEntryGroupRel.class, _dynamicQueryClassLoader);

		dynamicQuery.setProjection(
			ProjectionFactoryUtil.property("depotEntryGroupRelId"));

		Object newDepotEntryGroupRelId =
			newDepotEntryGroupRel.getDepotEntryGroupRelId();

		dynamicQuery.add(
			RestrictionsFactoryUtil.in(
				"depotEntryGroupRelId",
				new Object[] {newDepotEntryGroupRelId}));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		Object existingDepotEntryGroupRelId = result.get(0);

		Assert.assertEquals(
			existingDepotEntryGroupRelId, newDepotEntryGroupRelId);
	}

	@Test
	public void testDynamicQueryByProjectionMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			DepotEntryGroupRel.class, _dynamicQueryClassLoader);

		dynamicQuery.setProjection(
			ProjectionFactoryUtil.property("depotEntryGroupRelId"));

		dynamicQuery.add(
			RestrictionsFactoryUtil.in(
				"depotEntryGroupRelId",
				new Object[] {RandomTestUtil.nextLong()}));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testResetOriginalValues() throws Exception {
		DepotEntryGroupRel newDepotEntryGroupRel = addDepotEntryGroupRel();

		_persistence.clearCache();

		DepotEntryGroupRel existingDepotEntryGroupRel =
			_persistence.findByPrimaryKey(
				newDepotEntryGroupRel.getPrimaryKey());

		Assert.assertEquals(
			Long.valueOf(existingDepotEntryGroupRel.getDepotEntryId()),
			ReflectionTestUtil.<Long>invoke(
				existingDepotEntryGroupRel, "getOriginalDepotEntryId",
				new Class<?>[0]));
		Assert.assertEquals(
			Long.valueOf(existingDepotEntryGroupRel.getToGroupId()),
			ReflectionTestUtil.<Long>invoke(
				existingDepotEntryGroupRel, "getOriginalToGroupId",
				new Class<?>[0]));
	}

	protected DepotEntryGroupRel addDepotEntryGroupRel() throws Exception {
		long pk = RandomTestUtil.nextLong();

		DepotEntryGroupRel depotEntryGroupRel = _persistence.create(pk);

		depotEntryGroupRel.setMvccVersion(RandomTestUtil.nextLong());

		depotEntryGroupRel.setDepotEntryId(RandomTestUtil.nextLong());

		depotEntryGroupRel.setToGroupId(RandomTestUtil.nextLong());

		_depotEntryGroupRels.add(_persistence.update(depotEntryGroupRel));

		return depotEntryGroupRel;
	}

	private List<DepotEntryGroupRel> _depotEntryGroupRels =
		new ArrayList<DepotEntryGroupRel>();
	private DepotEntryGroupRelPersistence _persistence;
	private ClassLoader _dynamicQueryClassLoader;

}