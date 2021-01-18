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
import com.liferay.portal.tools.service.builder.test.exception.NoSuchERCCompanyEntityException;
import com.liferay.portal.tools.service.builder.test.model.ERCCompanyEntity;
import com.liferay.portal.tools.service.builder.test.service.ERCCompanyEntityLocalServiceUtil;
import com.liferay.portal.tools.service.builder.test.service.persistence.ERCCompanyEntityPersistence;
import com.liferay.portal.tools.service.builder.test.service.persistence.ERCCompanyEntityUtil;

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
public class ERCCompanyEntityPersistenceTest {

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
		_persistence = ERCCompanyEntityUtil.getPersistence();

		Class<?> clazz = _persistence.getClass();

		_dynamicQueryClassLoader = clazz.getClassLoader();
	}

	@After
	public void tearDown() throws Exception {
		Iterator<ERCCompanyEntity> iterator = _ercCompanyEntities.iterator();

		while (iterator.hasNext()) {
			_persistence.remove(iterator.next());

			iterator.remove();
		}
	}

	@Test
	public void testCreate() throws Exception {
		long pk = RandomTestUtil.nextLong();

		ERCCompanyEntity ercCompanyEntity = _persistence.create(pk);

		Assert.assertNotNull(ercCompanyEntity);

		Assert.assertEquals(ercCompanyEntity.getPrimaryKey(), pk);
	}

	@Test
	public void testRemove() throws Exception {
		ERCCompanyEntity newERCCompanyEntity = addERCCompanyEntity();

		_persistence.remove(newERCCompanyEntity);

		ERCCompanyEntity existingERCCompanyEntity =
			_persistence.fetchByPrimaryKey(newERCCompanyEntity.getPrimaryKey());

		Assert.assertNull(existingERCCompanyEntity);
	}

	@Test
	public void testUpdateNew() throws Exception {
		addERCCompanyEntity();
	}

	@Test
	public void testUpdateExisting() throws Exception {
		long pk = RandomTestUtil.nextLong();

		ERCCompanyEntity newERCCompanyEntity = _persistence.create(pk);

		newERCCompanyEntity.setExternalReferenceCode(
			RandomTestUtil.randomString());

		newERCCompanyEntity.setCompanyId(RandomTestUtil.nextLong());

		_ercCompanyEntities.add(_persistence.update(newERCCompanyEntity));

		ERCCompanyEntity existingERCCompanyEntity =
			_persistence.findByPrimaryKey(newERCCompanyEntity.getPrimaryKey());

		Assert.assertEquals(
			existingERCCompanyEntity.getExternalReferenceCode(),
			newERCCompanyEntity.getExternalReferenceCode());
		Assert.assertEquals(
			existingERCCompanyEntity.getErcCompanyEntityId(),
			newERCCompanyEntity.getErcCompanyEntityId());
		Assert.assertEquals(
			existingERCCompanyEntity.getCompanyId(),
			newERCCompanyEntity.getCompanyId());
	}

	@Test
	public void testCountByC_ERC() throws Exception {
		_persistence.countByC_ERC(RandomTestUtil.nextLong(), "");

		_persistence.countByC_ERC(0L, "null");

		_persistence.countByC_ERC(0L, (String)null);
	}

	@Test
	public void testFindByPrimaryKeyExisting() throws Exception {
		ERCCompanyEntity newERCCompanyEntity = addERCCompanyEntity();

		ERCCompanyEntity existingERCCompanyEntity =
			_persistence.findByPrimaryKey(newERCCompanyEntity.getPrimaryKey());

		Assert.assertEquals(existingERCCompanyEntity, newERCCompanyEntity);
	}

	@Test(expected = NoSuchERCCompanyEntityException.class)
	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		_persistence.findByPrimaryKey(pk);
	}

	@Test
	public void testFindAll() throws Exception {
		_persistence.findAll(
			QueryUtil.ALL_POS, QueryUtil.ALL_POS, getOrderByComparator());
	}

	protected OrderByComparator<ERCCompanyEntity> getOrderByComparator() {
		return OrderByComparatorFactoryUtil.create(
			"ERCCompanyEntity", "externalReferenceCode", true,
			"ercCompanyEntityId", true, "companyId", true);
	}

	@Test
	public void testFetchByPrimaryKeyExisting() throws Exception {
		ERCCompanyEntity newERCCompanyEntity = addERCCompanyEntity();

		ERCCompanyEntity existingERCCompanyEntity =
			_persistence.fetchByPrimaryKey(newERCCompanyEntity.getPrimaryKey());

		Assert.assertEquals(existingERCCompanyEntity, newERCCompanyEntity);
	}

	@Test
	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		ERCCompanyEntity missingERCCompanyEntity =
			_persistence.fetchByPrimaryKey(pk);

		Assert.assertNull(missingERCCompanyEntity);
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereAllPrimaryKeysExist()
		throws Exception {

		ERCCompanyEntity newERCCompanyEntity1 = addERCCompanyEntity();
		ERCCompanyEntity newERCCompanyEntity2 = addERCCompanyEntity();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newERCCompanyEntity1.getPrimaryKey());
		primaryKeys.add(newERCCompanyEntity2.getPrimaryKey());

		Map<Serializable, ERCCompanyEntity> ercCompanyEntities =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(2, ercCompanyEntities.size());
		Assert.assertEquals(
			newERCCompanyEntity1,
			ercCompanyEntities.get(newERCCompanyEntity1.getPrimaryKey()));
		Assert.assertEquals(
			newERCCompanyEntity2,
			ercCompanyEntities.get(newERCCompanyEntity2.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereNoPrimaryKeysExist()
		throws Exception {

		long pk1 = RandomTestUtil.nextLong();

		long pk2 = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(pk1);
		primaryKeys.add(pk2);

		Map<Serializable, ERCCompanyEntity> ercCompanyEntities =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(ercCompanyEntities.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereSomePrimaryKeysExist()
		throws Exception {

		ERCCompanyEntity newERCCompanyEntity = addERCCompanyEntity();

		long pk = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newERCCompanyEntity.getPrimaryKey());
		primaryKeys.add(pk);

		Map<Serializable, ERCCompanyEntity> ercCompanyEntities =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, ercCompanyEntities.size());
		Assert.assertEquals(
			newERCCompanyEntity,
			ercCompanyEntities.get(newERCCompanyEntity.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithNoPrimaryKeys() throws Exception {
		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		Map<Serializable, ERCCompanyEntity> ercCompanyEntities =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(ercCompanyEntities.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithOnePrimaryKey() throws Exception {
		ERCCompanyEntity newERCCompanyEntity = addERCCompanyEntity();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newERCCompanyEntity.getPrimaryKey());

		Map<Serializable, ERCCompanyEntity> ercCompanyEntities =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, ercCompanyEntities.size());
		Assert.assertEquals(
			newERCCompanyEntity,
			ercCompanyEntities.get(newERCCompanyEntity.getPrimaryKey()));
	}

	@Test
	public void testActionableDynamicQuery() throws Exception {
		final IntegerWrapper count = new IntegerWrapper();

		ActionableDynamicQuery actionableDynamicQuery =
			ERCCompanyEntityLocalServiceUtil.getActionableDynamicQuery();

		actionableDynamicQuery.setPerformActionMethod(
			new ActionableDynamicQuery.PerformActionMethod<ERCCompanyEntity>() {

				@Override
				public void performAction(ERCCompanyEntity ercCompanyEntity) {
					Assert.assertNotNull(ercCompanyEntity);

					count.increment();
				}

			});

		actionableDynamicQuery.performActions();

		Assert.assertEquals(count.getValue(), _persistence.countAll());
	}

	@Test
	public void testDynamicQueryByPrimaryKeyExisting() throws Exception {
		ERCCompanyEntity newERCCompanyEntity = addERCCompanyEntity();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			ERCCompanyEntity.class, _dynamicQueryClassLoader);

		dynamicQuery.add(
			RestrictionsFactoryUtil.eq(
				"ercCompanyEntityId",
				newERCCompanyEntity.getErcCompanyEntityId()));

		List<ERCCompanyEntity> result = _persistence.findWithDynamicQuery(
			dynamicQuery);

		Assert.assertEquals(1, result.size());

		ERCCompanyEntity existingERCCompanyEntity = result.get(0);

		Assert.assertEquals(existingERCCompanyEntity, newERCCompanyEntity);
	}

	@Test
	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			ERCCompanyEntity.class, _dynamicQueryClassLoader);

		dynamicQuery.add(
			RestrictionsFactoryUtil.eq(
				"ercCompanyEntityId", RandomTestUtil.nextLong()));

		List<ERCCompanyEntity> result = _persistence.findWithDynamicQuery(
			dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testDynamicQueryByProjectionExisting() throws Exception {
		ERCCompanyEntity newERCCompanyEntity = addERCCompanyEntity();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			ERCCompanyEntity.class, _dynamicQueryClassLoader);

		dynamicQuery.setProjection(
			ProjectionFactoryUtil.property("ercCompanyEntityId"));

		Object newErcCompanyEntityId =
			newERCCompanyEntity.getErcCompanyEntityId();

		dynamicQuery.add(
			RestrictionsFactoryUtil.in(
				"ercCompanyEntityId", new Object[] {newErcCompanyEntityId}));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		Object existingErcCompanyEntityId = result.get(0);

		Assert.assertEquals(existingErcCompanyEntityId, newErcCompanyEntityId);
	}

	@Test
	public void testDynamicQueryByProjectionMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			ERCCompanyEntity.class, _dynamicQueryClassLoader);

		dynamicQuery.setProjection(
			ProjectionFactoryUtil.property("ercCompanyEntityId"));

		dynamicQuery.add(
			RestrictionsFactoryUtil.in(
				"ercCompanyEntityId",
				new Object[] {RandomTestUtil.nextLong()}));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testResetOriginalValues() throws Exception {
		ERCCompanyEntity newERCCompanyEntity = addERCCompanyEntity();

		_persistence.clearCache();

		_assertOriginalValues(
			_persistence.findByPrimaryKey(newERCCompanyEntity.getPrimaryKey()));
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

		ERCCompanyEntity newERCCompanyEntity = addERCCompanyEntity();

		if (clearSession) {
			Session session = _persistence.openSession();

			session.flush();

			session.clear();
		}

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			ERCCompanyEntity.class, _dynamicQueryClassLoader);

		dynamicQuery.add(
			RestrictionsFactoryUtil.eq(
				"ercCompanyEntityId",
				newERCCompanyEntity.getErcCompanyEntityId()));

		List<ERCCompanyEntity> result = _persistence.findWithDynamicQuery(
			dynamicQuery);

		_assertOriginalValues(result.get(0));
	}

	private void _assertOriginalValues(ERCCompanyEntity ercCompanyEntity) {
		Assert.assertEquals(
			Long.valueOf(ercCompanyEntity.getCompanyId()),
			ReflectionTestUtil.<Long>invoke(
				ercCompanyEntity, "getColumnOriginalValue",
				new Class<?>[] {String.class}, "companyId"));
		Assert.assertEquals(
			ercCompanyEntity.getExternalReferenceCode(),
			ReflectionTestUtil.invoke(
				ercCompanyEntity, "getColumnOriginalValue",
				new Class<?>[] {String.class}, "externalReferenceCode"));
	}

	protected ERCCompanyEntity addERCCompanyEntity() throws Exception {
		long pk = RandomTestUtil.nextLong();

		ERCCompanyEntity ercCompanyEntity = _persistence.create(pk);

		ercCompanyEntity.setExternalReferenceCode(
			RandomTestUtil.randomString());

		ercCompanyEntity.setCompanyId(RandomTestUtil.nextLong());

		_ercCompanyEntities.add(_persistence.update(ercCompanyEntity));

		return ercCompanyEntity;
	}

	private List<ERCCompanyEntity> _ercCompanyEntities =
		new ArrayList<ERCCompanyEntity>();
	private ERCCompanyEntityPersistence _persistence;
	private ClassLoader _dynamicQueryClassLoader;

}