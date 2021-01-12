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

package com.liferay.portal.tools.service.builder.test.service.persistence.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQueryFactoryUtil;
import com.liferay.portal.kernel.dao.orm.ProjectionFactoryUtil;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.RestrictionsFactoryUtil;
import com.liferay.portal.kernel.dao.orm.Session;
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
import com.liferay.portal.tools.service.builder.test.exception.NoSuchERCGroupEntityException;
import com.liferay.portal.tools.service.builder.test.model.ERCGroupEntity;
import com.liferay.portal.tools.service.builder.test.service.ERCGroupEntityLocalServiceUtil;
import com.liferay.portal.tools.service.builder.test.service.persistence.ERCGroupEntityPersistence;
import com.liferay.portal.tools.service.builder.test.service.persistence.ERCGroupEntityUtil;

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
public class ERCGroupEntityPersistenceTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(), PersistenceTestRule.INSTANCE,
			new TransactionalTestRule(
				Propagation.REQUIRED,
				"com.liferay.portal.tools.service.builder.test.service"));

	@Before
	public void setUp() {
		_persistence = ERCGroupEntityUtil.getPersistence();

		Class<?> clazz = _persistence.getClass();

		_dynamicQueryClassLoader = clazz.getClassLoader();
	}

	@After
	public void tearDown() throws Exception {
		Iterator<ERCGroupEntity> iterator = _ercGroupEntities.iterator();

		while (iterator.hasNext()) {
			_persistence.remove(iterator.next());

			iterator.remove();
		}
	}

	@Test
	public void testCreate() throws Exception {
		long pk = RandomTestUtil.nextLong();

		ERCGroupEntity ercGroupEntity = _persistence.create(pk);

		Assert.assertNotNull(ercGroupEntity);

		Assert.assertEquals(ercGroupEntity.getPrimaryKey(), pk);
	}

	@Test
	public void testRemove() throws Exception {
		ERCGroupEntity newERCGroupEntity = addERCGroupEntity();

		_persistence.remove(newERCGroupEntity);

		ERCGroupEntity existingERCGroupEntity = _persistence.fetchByPrimaryKey(
			newERCGroupEntity.getPrimaryKey());

		Assert.assertNull(existingERCGroupEntity);
	}

	@Test
	public void testUpdateNew() throws Exception {
		addERCGroupEntity();
	}

	@Test
	public void testUpdateExisting() throws Exception {
		long pk = RandomTestUtil.nextLong();

		ERCGroupEntity newERCGroupEntity = _persistence.create(pk);

		newERCGroupEntity.setExternalReferenceCode(
			RandomTestUtil.randomString());

		newERCGroupEntity.setCompanyId(RandomTestUtil.nextLong());

		newERCGroupEntity.setGroupId(RandomTestUtil.nextLong());

		_ercGroupEntities.add(_persistence.update(newERCGroupEntity));

		ERCGroupEntity existingERCGroupEntity = _persistence.findByPrimaryKey(
			newERCGroupEntity.getPrimaryKey());

		Assert.assertEquals(
			existingERCGroupEntity.getExternalReferenceCode(),
			newERCGroupEntity.getExternalReferenceCode());
		Assert.assertEquals(
			existingERCGroupEntity.getErcGroupEntityId(),
			newERCGroupEntity.getErcGroupEntityId());
		Assert.assertEquals(
			existingERCGroupEntity.getCompanyId(),
			newERCGroupEntity.getCompanyId());
		Assert.assertEquals(
			existingERCGroupEntity.getGroupId(),
			newERCGroupEntity.getGroupId());
	}

	@Test
	public void testCountByG_ERC() throws Exception {
		_persistence.countByG_ERC(RandomTestUtil.nextLong(), "");

		_persistence.countByG_ERC(0L, "null");

		_persistence.countByG_ERC(0L, (String)null);
	}

	@Test
	public void testFindByPrimaryKeyExisting() throws Exception {
		ERCGroupEntity newERCGroupEntity = addERCGroupEntity();

		ERCGroupEntity existingERCGroupEntity = _persistence.findByPrimaryKey(
			newERCGroupEntity.getPrimaryKey());

		Assert.assertEquals(existingERCGroupEntity, newERCGroupEntity);
	}

	@Test(expected = NoSuchERCGroupEntityException.class)
	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		_persistence.findByPrimaryKey(pk);
	}

	@Test
	public void testFindAll() throws Exception {
		_persistence.findAll(
			QueryUtil.ALL_POS, QueryUtil.ALL_POS, getOrderByComparator());
	}

	protected OrderByComparator<ERCGroupEntity> getOrderByComparator() {
		return OrderByComparatorFactoryUtil.create(
			"ERCGroupEntity", "externalReferenceCode", true, "ercGroupEntityId",
			true, "companyId", true, "groupId", true);
	}

	@Test
	public void testFetchByPrimaryKeyExisting() throws Exception {
		ERCGroupEntity newERCGroupEntity = addERCGroupEntity();

		ERCGroupEntity existingERCGroupEntity = _persistence.fetchByPrimaryKey(
			newERCGroupEntity.getPrimaryKey());

		Assert.assertEquals(existingERCGroupEntity, newERCGroupEntity);
	}

	@Test
	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		ERCGroupEntity missingERCGroupEntity = _persistence.fetchByPrimaryKey(
			pk);

		Assert.assertNull(missingERCGroupEntity);
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereAllPrimaryKeysExist()
		throws Exception {

		ERCGroupEntity newERCGroupEntity1 = addERCGroupEntity();
		ERCGroupEntity newERCGroupEntity2 = addERCGroupEntity();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newERCGroupEntity1.getPrimaryKey());
		primaryKeys.add(newERCGroupEntity2.getPrimaryKey());

		Map<Serializable, ERCGroupEntity> ercGroupEntities =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(2, ercGroupEntities.size());
		Assert.assertEquals(
			newERCGroupEntity1,
			ercGroupEntities.get(newERCGroupEntity1.getPrimaryKey()));
		Assert.assertEquals(
			newERCGroupEntity2,
			ercGroupEntities.get(newERCGroupEntity2.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereNoPrimaryKeysExist()
		throws Exception {

		long pk1 = RandomTestUtil.nextLong();

		long pk2 = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(pk1);
		primaryKeys.add(pk2);

		Map<Serializable, ERCGroupEntity> ercGroupEntities =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(ercGroupEntities.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereSomePrimaryKeysExist()
		throws Exception {

		ERCGroupEntity newERCGroupEntity = addERCGroupEntity();

		long pk = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newERCGroupEntity.getPrimaryKey());
		primaryKeys.add(pk);

		Map<Serializable, ERCGroupEntity> ercGroupEntities =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, ercGroupEntities.size());
		Assert.assertEquals(
			newERCGroupEntity,
			ercGroupEntities.get(newERCGroupEntity.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithNoPrimaryKeys() throws Exception {
		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		Map<Serializable, ERCGroupEntity> ercGroupEntities =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(ercGroupEntities.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithOnePrimaryKey() throws Exception {
		ERCGroupEntity newERCGroupEntity = addERCGroupEntity();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newERCGroupEntity.getPrimaryKey());

		Map<Serializable, ERCGroupEntity> ercGroupEntities =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, ercGroupEntities.size());
		Assert.assertEquals(
			newERCGroupEntity,
			ercGroupEntities.get(newERCGroupEntity.getPrimaryKey()));
	}

	@Test
	public void testActionableDynamicQuery() throws Exception {
		final IntegerWrapper count = new IntegerWrapper();

		ActionableDynamicQuery actionableDynamicQuery =
			ERCGroupEntityLocalServiceUtil.getActionableDynamicQuery();

		actionableDynamicQuery.setPerformActionMethod(
			new ActionableDynamicQuery.PerformActionMethod<ERCGroupEntity>() {

				@Override
				public void performAction(ERCGroupEntity ercGroupEntity) {
					Assert.assertNotNull(ercGroupEntity);

					count.increment();
				}

			});

		actionableDynamicQuery.performActions();

		Assert.assertEquals(count.getValue(), _persistence.countAll());
	}

	@Test
	public void testDynamicQueryByPrimaryKeyExisting() throws Exception {
		ERCGroupEntity newERCGroupEntity = addERCGroupEntity();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			ERCGroupEntity.class, _dynamicQueryClassLoader);

		dynamicQuery.add(
			RestrictionsFactoryUtil.eq(
				"ercGroupEntityId", newERCGroupEntity.getErcGroupEntityId()));

		List<ERCGroupEntity> result = _persistence.findWithDynamicQuery(
			dynamicQuery);

		Assert.assertEquals(1, result.size());

		ERCGroupEntity existingERCGroupEntity = result.get(0);

		Assert.assertEquals(existingERCGroupEntity, newERCGroupEntity);
	}

	@Test
	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			ERCGroupEntity.class, _dynamicQueryClassLoader);

		dynamicQuery.add(
			RestrictionsFactoryUtil.eq(
				"ercGroupEntityId", RandomTestUtil.nextLong()));

		List<ERCGroupEntity> result = _persistence.findWithDynamicQuery(
			dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testDynamicQueryByProjectionExisting() throws Exception {
		ERCGroupEntity newERCGroupEntity = addERCGroupEntity();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			ERCGroupEntity.class, _dynamicQueryClassLoader);

		dynamicQuery.setProjection(
			ProjectionFactoryUtil.property("ercGroupEntityId"));

		Object newErcGroupEntityId = newERCGroupEntity.getErcGroupEntityId();

		dynamicQuery.add(
			RestrictionsFactoryUtil.in(
				"ercGroupEntityId", new Object[] {newErcGroupEntityId}));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		Object existingErcGroupEntityId = result.get(0);

		Assert.assertEquals(existingErcGroupEntityId, newErcGroupEntityId);
	}

	@Test
	public void testDynamicQueryByProjectionMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			ERCGroupEntity.class, _dynamicQueryClassLoader);

		dynamicQuery.setProjection(
			ProjectionFactoryUtil.property("ercGroupEntityId"));

		dynamicQuery.add(
			RestrictionsFactoryUtil.in(
				"ercGroupEntityId", new Object[] {RandomTestUtil.nextLong()}));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testResetOriginalValues() throws Exception {
		ERCGroupEntity newERCGroupEntity = addERCGroupEntity();

		_persistence.clearCache();

		_assertOriginalValues(
			_persistence.findByPrimaryKey(newERCGroupEntity.getPrimaryKey()));
	}

	@Test
	public void testResetOriginalValuesWithDynamicQueryLoadFromDatabase()
		throws Exception {

		_testResetOriginalValuesWithDynamicQuery(true);
	}

	@Test
	public void testResetOriginalValuesWithDynamicQueryLoadFromSession()
		throws Exception {

		_testResetOriginalValuesWithDynamicQuery(false);
	}

	private void _testResetOriginalValuesWithDynamicQuery(boolean clearSession)
		throws Exception {

		ERCGroupEntity newERCGroupEntity = addERCGroupEntity();

		if (clearSession) {
			Session session = _persistence.openSession();

			session.flush();

			session.clear();
		}

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			ERCGroupEntity.class, _dynamicQueryClassLoader);

		dynamicQuery.add(
			RestrictionsFactoryUtil.eq(
				"ercGroupEntityId", newERCGroupEntity.getErcGroupEntityId()));

		List<ERCGroupEntity> result = _persistence.findWithDynamicQuery(
			dynamicQuery);

		_assertOriginalValues(result.get(0));
	}

	private void _assertOriginalValues(ERCGroupEntity ercGroupEntity) {
		Assert.assertEquals(
			Long.valueOf(ercGroupEntity.getGroupId()),
			ReflectionTestUtil.<Long>invoke(
				ercGroupEntity, "getColumnOriginalValue",
				new Class<?>[] {String.class}, "groupId"));
		Assert.assertEquals(
			ercGroupEntity.getExternalReferenceCode(),
			ReflectionTestUtil.invoke(
				ercGroupEntity, "getColumnOriginalValue",
				new Class<?>[] {String.class}, "externalReferenceCode"));
	}

	protected ERCGroupEntity addERCGroupEntity() throws Exception {
		long pk = RandomTestUtil.nextLong();

		ERCGroupEntity ercGroupEntity = _persistence.create(pk);

		ercGroupEntity.setExternalReferenceCode(RandomTestUtil.randomString());

		ercGroupEntity.setCompanyId(RandomTestUtil.nextLong());

		ercGroupEntity.setGroupId(RandomTestUtil.nextLong());

		_ercGroupEntities.add(_persistence.update(ercGroupEntity));

		return ercGroupEntity;
	}

	private List<ERCGroupEntity> _ercGroupEntities =
		new ArrayList<ERCGroupEntity>();
	private ERCGroupEntityPersistence _persistence;
	private ClassLoader _dynamicQueryClassLoader;

}