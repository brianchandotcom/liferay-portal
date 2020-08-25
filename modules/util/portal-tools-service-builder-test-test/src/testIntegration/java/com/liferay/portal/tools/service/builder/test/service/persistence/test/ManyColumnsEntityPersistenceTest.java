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
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.transaction.Propagation;
import com.liferay.portal.kernel.util.IntegerWrapper;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.OrderByComparatorFactoryUtil;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PersistenceTestRule;
import com.liferay.portal.test.rule.TransactionalTestRule;
import com.liferay.portal.tools.service.builder.test.exception.NoSuchManyColumnsEntityException;
import com.liferay.portal.tools.service.builder.test.model.ManyColumnsEntity;
import com.liferay.portal.tools.service.builder.test.service.ManyColumnsEntityLocalServiceUtil;
import com.liferay.portal.tools.service.builder.test.service.persistence.ManyColumnsEntityPersistence;
import com.liferay.portal.tools.service.builder.test.service.persistence.ManyColumnsEntityUtil;

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
public class ManyColumnsEntityPersistenceTest {

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
		_persistence = ManyColumnsEntityUtil.getPersistence();

		Class<?> clazz = _persistence.getClass();

		_dynamicQueryClassLoader = clazz.getClassLoader();
	}

	@After
	public void tearDown() throws Exception {
		Iterator<ManyColumnsEntity> iterator = _manyColumnsEntities.iterator();

		while (iterator.hasNext()) {
			_persistence.remove(iterator.next());

			iterator.remove();
		}
	}

	@Test
	public void testCreate() throws Exception {
		long pk = RandomTestUtil.nextLong();

		ManyColumnsEntity manyColumnsEntity = _persistence.create(pk);

		Assert.assertNotNull(manyColumnsEntity);

		Assert.assertEquals(manyColumnsEntity.getPrimaryKey(), pk);
	}

	@Test
	public void testRemove() throws Exception {
		ManyColumnsEntity newManyColumnsEntity = addManyColumnsEntity();

		_persistence.remove(newManyColumnsEntity);

		ManyColumnsEntity existingManyColumnsEntity =
			_persistence.fetchByPrimaryKey(
				newManyColumnsEntity.getPrimaryKey());

		Assert.assertNull(existingManyColumnsEntity);
	}

	@Test
	public void testUpdateNew() throws Exception {
		addManyColumnsEntity();
	}

	@Test
	public void testUpdateExisting() throws Exception {
		long pk = RandomTestUtil.nextLong();

		ManyColumnsEntity newManyColumnsEntity = _persistence.create(pk);

		newManyColumnsEntity.setColumn1(RandomTestUtil.nextInt());

		newManyColumnsEntity.setColumn2(RandomTestUtil.nextInt());

		newManyColumnsEntity.setColumn3(RandomTestUtil.nextInt());

		newManyColumnsEntity.setColumn4(RandomTestUtil.nextInt());

		newManyColumnsEntity.setColumn5(RandomTestUtil.nextInt());

		newManyColumnsEntity.setColumn6(RandomTestUtil.nextInt());

		newManyColumnsEntity.setColumn7(RandomTestUtil.nextInt());

		newManyColumnsEntity.setColumn8(RandomTestUtil.nextInt());

		newManyColumnsEntity.setColumn9(RandomTestUtil.nextInt());

		newManyColumnsEntity.setColumn10(RandomTestUtil.nextInt());

		newManyColumnsEntity.setColumn11(RandomTestUtil.nextInt());

		newManyColumnsEntity.setColumn12(RandomTestUtil.nextInt());

		newManyColumnsEntity.setColumn13(RandomTestUtil.nextInt());

		newManyColumnsEntity.setColumn14(RandomTestUtil.nextInt());

		newManyColumnsEntity.setColumn15(RandomTestUtil.nextInt());

		newManyColumnsEntity.setColumn16(RandomTestUtil.nextInt());

		newManyColumnsEntity.setColumn17(RandomTestUtil.nextInt());

		newManyColumnsEntity.setColumn18(RandomTestUtil.nextInt());

		newManyColumnsEntity.setColumn19(RandomTestUtil.nextInt());

		newManyColumnsEntity.setColumn20(RandomTestUtil.nextInt());

		newManyColumnsEntity.setColumn21(RandomTestUtil.nextInt());

		newManyColumnsEntity.setColumn22(RandomTestUtil.nextInt());

		newManyColumnsEntity.setColumn23(RandomTestUtil.nextInt());

		newManyColumnsEntity.setColumn24(RandomTestUtil.nextInt());

		newManyColumnsEntity.setColumn25(RandomTestUtil.nextInt());

		newManyColumnsEntity.setColumn26(RandomTestUtil.nextInt());

		newManyColumnsEntity.setColumn27(RandomTestUtil.nextInt());

		newManyColumnsEntity.setColumn28(RandomTestUtil.nextInt());

		newManyColumnsEntity.setColumn29(RandomTestUtil.nextInt());

		newManyColumnsEntity.setColumn30(RandomTestUtil.nextInt());

		newManyColumnsEntity.setColumn31(RandomTestUtil.nextInt());

		newManyColumnsEntity.setColumn32(RandomTestUtil.nextInt());

		newManyColumnsEntity.setColumn33(RandomTestUtil.nextInt());

		newManyColumnsEntity.setColumn34(RandomTestUtil.nextInt());

		newManyColumnsEntity.setColumn35(RandomTestUtil.nextInt());

		newManyColumnsEntity.setColumn36(RandomTestUtil.nextInt());

		newManyColumnsEntity.setColumn37(RandomTestUtil.nextInt());

		newManyColumnsEntity.setColumn38(RandomTestUtil.nextInt());

		newManyColumnsEntity.setColumn39(RandomTestUtil.nextInt());

		newManyColumnsEntity.setColumn40(RandomTestUtil.nextInt());

		newManyColumnsEntity.setColumn41(RandomTestUtil.nextInt());

		newManyColumnsEntity.setColumn42(RandomTestUtil.nextInt());

		newManyColumnsEntity.setColumn43(RandomTestUtil.nextInt());

		newManyColumnsEntity.setColumn44(RandomTestUtil.nextInt());

		newManyColumnsEntity.setColumn45(RandomTestUtil.nextInt());

		newManyColumnsEntity.setColumn46(RandomTestUtil.nextInt());

		newManyColumnsEntity.setColumn47(RandomTestUtil.nextInt());

		newManyColumnsEntity.setColumn48(RandomTestUtil.nextInt());

		newManyColumnsEntity.setColumn49(RandomTestUtil.nextInt());

		newManyColumnsEntity.setColumn50(RandomTestUtil.nextInt());

		newManyColumnsEntity.setColumn51(RandomTestUtil.nextInt());

		newManyColumnsEntity.setColumn52(RandomTestUtil.nextInt());

		newManyColumnsEntity.setColumn53(RandomTestUtil.nextInt());

		newManyColumnsEntity.setColumn54(RandomTestUtil.nextInt());

		newManyColumnsEntity.setColumn55(RandomTestUtil.nextInt());

		newManyColumnsEntity.setColumn56(RandomTestUtil.nextInt());

		newManyColumnsEntity.setColumn57(RandomTestUtil.nextInt());

		newManyColumnsEntity.setColumn58(RandomTestUtil.nextInt());

		newManyColumnsEntity.setColumn59(RandomTestUtil.nextInt());

		newManyColumnsEntity.setColumn60(RandomTestUtil.nextInt());

		newManyColumnsEntity.setColumn61(RandomTestUtil.nextInt());

		newManyColumnsEntity.setColumn62(RandomTestUtil.nextInt());

		newManyColumnsEntity.setColumn63(RandomTestUtil.nextInt());

		newManyColumnsEntity.setColumn64(RandomTestUtil.nextInt());

		_manyColumnsEntities.add(_persistence.update(newManyColumnsEntity));

		ManyColumnsEntity existingManyColumnsEntity =
			_persistence.findByPrimaryKey(newManyColumnsEntity.getPrimaryKey());

		Assert.assertEquals(
			existingManyColumnsEntity.getManyColumnsEntityId(),
			newManyColumnsEntity.getManyColumnsEntityId());
		Assert.assertEquals(
			existingManyColumnsEntity.getColumn1(),
			newManyColumnsEntity.getColumn1());
		Assert.assertEquals(
			existingManyColumnsEntity.getColumn2(),
			newManyColumnsEntity.getColumn2());
		Assert.assertEquals(
			existingManyColumnsEntity.getColumn3(),
			newManyColumnsEntity.getColumn3());
		Assert.assertEquals(
			existingManyColumnsEntity.getColumn4(),
			newManyColumnsEntity.getColumn4());
		Assert.assertEquals(
			existingManyColumnsEntity.getColumn5(),
			newManyColumnsEntity.getColumn5());
		Assert.assertEquals(
			existingManyColumnsEntity.getColumn6(),
			newManyColumnsEntity.getColumn6());
		Assert.assertEquals(
			existingManyColumnsEntity.getColumn7(),
			newManyColumnsEntity.getColumn7());
		Assert.assertEquals(
			existingManyColumnsEntity.getColumn8(),
			newManyColumnsEntity.getColumn8());
		Assert.assertEquals(
			existingManyColumnsEntity.getColumn9(),
			newManyColumnsEntity.getColumn9());
		Assert.assertEquals(
			existingManyColumnsEntity.getColumn10(),
			newManyColumnsEntity.getColumn10());
		Assert.assertEquals(
			existingManyColumnsEntity.getColumn11(),
			newManyColumnsEntity.getColumn11());
		Assert.assertEquals(
			existingManyColumnsEntity.getColumn12(),
			newManyColumnsEntity.getColumn12());
		Assert.assertEquals(
			existingManyColumnsEntity.getColumn13(),
			newManyColumnsEntity.getColumn13());
		Assert.assertEquals(
			existingManyColumnsEntity.getColumn14(),
			newManyColumnsEntity.getColumn14());
		Assert.assertEquals(
			existingManyColumnsEntity.getColumn15(),
			newManyColumnsEntity.getColumn15());
		Assert.assertEquals(
			existingManyColumnsEntity.getColumn16(),
			newManyColumnsEntity.getColumn16());
		Assert.assertEquals(
			existingManyColumnsEntity.getColumn17(),
			newManyColumnsEntity.getColumn17());
		Assert.assertEquals(
			existingManyColumnsEntity.getColumn18(),
			newManyColumnsEntity.getColumn18());
		Assert.assertEquals(
			existingManyColumnsEntity.getColumn19(),
			newManyColumnsEntity.getColumn19());
		Assert.assertEquals(
			existingManyColumnsEntity.getColumn20(),
			newManyColumnsEntity.getColumn20());
		Assert.assertEquals(
			existingManyColumnsEntity.getColumn21(),
			newManyColumnsEntity.getColumn21());
		Assert.assertEquals(
			existingManyColumnsEntity.getColumn22(),
			newManyColumnsEntity.getColumn22());
		Assert.assertEquals(
			existingManyColumnsEntity.getColumn23(),
			newManyColumnsEntity.getColumn23());
		Assert.assertEquals(
			existingManyColumnsEntity.getColumn24(),
			newManyColumnsEntity.getColumn24());
		Assert.assertEquals(
			existingManyColumnsEntity.getColumn25(),
			newManyColumnsEntity.getColumn25());
		Assert.assertEquals(
			existingManyColumnsEntity.getColumn26(),
			newManyColumnsEntity.getColumn26());
		Assert.assertEquals(
			existingManyColumnsEntity.getColumn27(),
			newManyColumnsEntity.getColumn27());
		Assert.assertEquals(
			existingManyColumnsEntity.getColumn28(),
			newManyColumnsEntity.getColumn28());
		Assert.assertEquals(
			existingManyColumnsEntity.getColumn29(),
			newManyColumnsEntity.getColumn29());
		Assert.assertEquals(
			existingManyColumnsEntity.getColumn30(),
			newManyColumnsEntity.getColumn30());
		Assert.assertEquals(
			existingManyColumnsEntity.getColumn31(),
			newManyColumnsEntity.getColumn31());
		Assert.assertEquals(
			existingManyColumnsEntity.getColumn32(),
			newManyColumnsEntity.getColumn32());
		Assert.assertEquals(
			existingManyColumnsEntity.getColumn33(),
			newManyColumnsEntity.getColumn33());
		Assert.assertEquals(
			existingManyColumnsEntity.getColumn34(),
			newManyColumnsEntity.getColumn34());
		Assert.assertEquals(
			existingManyColumnsEntity.getColumn35(),
			newManyColumnsEntity.getColumn35());
		Assert.assertEquals(
			existingManyColumnsEntity.getColumn36(),
			newManyColumnsEntity.getColumn36());
		Assert.assertEquals(
			existingManyColumnsEntity.getColumn37(),
			newManyColumnsEntity.getColumn37());
		Assert.assertEquals(
			existingManyColumnsEntity.getColumn38(),
			newManyColumnsEntity.getColumn38());
		Assert.assertEquals(
			existingManyColumnsEntity.getColumn39(),
			newManyColumnsEntity.getColumn39());
		Assert.assertEquals(
			existingManyColumnsEntity.getColumn40(),
			newManyColumnsEntity.getColumn40());
		Assert.assertEquals(
			existingManyColumnsEntity.getColumn41(),
			newManyColumnsEntity.getColumn41());
		Assert.assertEquals(
			existingManyColumnsEntity.getColumn42(),
			newManyColumnsEntity.getColumn42());
		Assert.assertEquals(
			existingManyColumnsEntity.getColumn43(),
			newManyColumnsEntity.getColumn43());
		Assert.assertEquals(
			existingManyColumnsEntity.getColumn44(),
			newManyColumnsEntity.getColumn44());
		Assert.assertEquals(
			existingManyColumnsEntity.getColumn45(),
			newManyColumnsEntity.getColumn45());
		Assert.assertEquals(
			existingManyColumnsEntity.getColumn46(),
			newManyColumnsEntity.getColumn46());
		Assert.assertEquals(
			existingManyColumnsEntity.getColumn47(),
			newManyColumnsEntity.getColumn47());
		Assert.assertEquals(
			existingManyColumnsEntity.getColumn48(),
			newManyColumnsEntity.getColumn48());
		Assert.assertEquals(
			existingManyColumnsEntity.getColumn49(),
			newManyColumnsEntity.getColumn49());
		Assert.assertEquals(
			existingManyColumnsEntity.getColumn50(),
			newManyColumnsEntity.getColumn50());
		Assert.assertEquals(
			existingManyColumnsEntity.getColumn51(),
			newManyColumnsEntity.getColumn51());
		Assert.assertEquals(
			existingManyColumnsEntity.getColumn52(),
			newManyColumnsEntity.getColumn52());
		Assert.assertEquals(
			existingManyColumnsEntity.getColumn53(),
			newManyColumnsEntity.getColumn53());
		Assert.assertEquals(
			existingManyColumnsEntity.getColumn54(),
			newManyColumnsEntity.getColumn54());
		Assert.assertEquals(
			existingManyColumnsEntity.getColumn55(),
			newManyColumnsEntity.getColumn55());
		Assert.assertEquals(
			existingManyColumnsEntity.getColumn56(),
			newManyColumnsEntity.getColumn56());
		Assert.assertEquals(
			existingManyColumnsEntity.getColumn57(),
			newManyColumnsEntity.getColumn57());
		Assert.assertEquals(
			existingManyColumnsEntity.getColumn58(),
			newManyColumnsEntity.getColumn58());
		Assert.assertEquals(
			existingManyColumnsEntity.getColumn59(),
			newManyColumnsEntity.getColumn59());
		Assert.assertEquals(
			existingManyColumnsEntity.getColumn60(),
			newManyColumnsEntity.getColumn60());
		Assert.assertEquals(
			existingManyColumnsEntity.getColumn61(),
			newManyColumnsEntity.getColumn61());
		Assert.assertEquals(
			existingManyColumnsEntity.getColumn62(),
			newManyColumnsEntity.getColumn62());
		Assert.assertEquals(
			existingManyColumnsEntity.getColumn63(),
			newManyColumnsEntity.getColumn63());
		Assert.assertEquals(
			existingManyColumnsEntity.getColumn64(),
			newManyColumnsEntity.getColumn64());
	}

	@Test
	public void testFindByPrimaryKeyExisting() throws Exception {
		ManyColumnsEntity newManyColumnsEntity = addManyColumnsEntity();

		ManyColumnsEntity existingManyColumnsEntity =
			_persistence.findByPrimaryKey(newManyColumnsEntity.getPrimaryKey());

		Assert.assertEquals(existingManyColumnsEntity, newManyColumnsEntity);
	}

	@Test(expected = NoSuchManyColumnsEntityException.class)
	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		_persistence.findByPrimaryKey(pk);
	}

	@Test
	public void testFindAll() throws Exception {
		_persistence.findAll(
			QueryUtil.ALL_POS, QueryUtil.ALL_POS, getOrderByComparator());
	}

	protected OrderByComparator<ManyColumnsEntity> getOrderByComparator() {
		return OrderByComparatorFactoryUtil.create(
			"ManyColumnsEntity", "manyColumnsEntityId", true, "column1", true,
			"column2", true, "column3", true, "column4", true, "column5", true,
			"column6", true, "column7", true, "column8", true, "column9", true,
			"column10", true, "column11", true, "column12", true, "column13",
			true, "column14", true, "column15", true, "column16", true,
			"column17", true, "column18", true, "column19", true, "column20",
			true, "column21", true, "column22", true, "column23", true,
			"column24", true, "column25", true, "column26", true, "column27",
			true, "column28", true, "column29", true, "column30", true,
			"column31", true, "column32", true, "column33", true, "column34",
			true, "column35", true, "column36", true, "column37", true,
			"column38", true, "column39", true, "column40", true, "column41",
			true, "column42", true, "column43", true, "column44", true,
			"column45", true, "column46", true, "column47", true, "column48",
			true, "column49", true, "column50", true, "column51", true,
			"column52", true, "column53", true, "column54", true, "column55",
			true, "column56", true, "column57", true, "column58", true,
			"column59", true, "column60", true, "column61", true, "column62",
			true, "column63", true, "column64", true);
	}

	@Test
	public void testFetchByPrimaryKeyExisting() throws Exception {
		ManyColumnsEntity newManyColumnsEntity = addManyColumnsEntity();

		ManyColumnsEntity existingManyColumnsEntity =
			_persistence.fetchByPrimaryKey(
				newManyColumnsEntity.getPrimaryKey());

		Assert.assertEquals(existingManyColumnsEntity, newManyColumnsEntity);
	}

	@Test
	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		ManyColumnsEntity missingManyColumnsEntity =
			_persistence.fetchByPrimaryKey(pk);

		Assert.assertNull(missingManyColumnsEntity);
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereAllPrimaryKeysExist()
		throws Exception {

		ManyColumnsEntity newManyColumnsEntity1 = addManyColumnsEntity();
		ManyColumnsEntity newManyColumnsEntity2 = addManyColumnsEntity();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newManyColumnsEntity1.getPrimaryKey());
		primaryKeys.add(newManyColumnsEntity2.getPrimaryKey());

		Map<Serializable, ManyColumnsEntity> manyColumnsEntities =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(2, manyColumnsEntities.size());
		Assert.assertEquals(
			newManyColumnsEntity1,
			manyColumnsEntities.get(newManyColumnsEntity1.getPrimaryKey()));
		Assert.assertEquals(
			newManyColumnsEntity2,
			manyColumnsEntities.get(newManyColumnsEntity2.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereNoPrimaryKeysExist()
		throws Exception {

		long pk1 = RandomTestUtil.nextLong();

		long pk2 = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(pk1);
		primaryKeys.add(pk2);

		Map<Serializable, ManyColumnsEntity> manyColumnsEntities =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(manyColumnsEntities.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereSomePrimaryKeysExist()
		throws Exception {

		ManyColumnsEntity newManyColumnsEntity = addManyColumnsEntity();

		long pk = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newManyColumnsEntity.getPrimaryKey());
		primaryKeys.add(pk);

		Map<Serializable, ManyColumnsEntity> manyColumnsEntities =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, manyColumnsEntities.size());
		Assert.assertEquals(
			newManyColumnsEntity,
			manyColumnsEntities.get(newManyColumnsEntity.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithNoPrimaryKeys() throws Exception {
		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		Map<Serializable, ManyColumnsEntity> manyColumnsEntities =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(manyColumnsEntities.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithOnePrimaryKey() throws Exception {
		ManyColumnsEntity newManyColumnsEntity = addManyColumnsEntity();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newManyColumnsEntity.getPrimaryKey());

		Map<Serializable, ManyColumnsEntity> manyColumnsEntities =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, manyColumnsEntities.size());
		Assert.assertEquals(
			newManyColumnsEntity,
			manyColumnsEntities.get(newManyColumnsEntity.getPrimaryKey()));
	}

	@Test
	public void testActionableDynamicQuery() throws Exception {
		final IntegerWrapper count = new IntegerWrapper();

		ActionableDynamicQuery actionableDynamicQuery =
			ManyColumnsEntityLocalServiceUtil.getActionableDynamicQuery();

		actionableDynamicQuery.setPerformActionMethod(
			new ActionableDynamicQuery.PerformActionMethod
				<ManyColumnsEntity>() {

				@Override
				public void performAction(ManyColumnsEntity manyColumnsEntity) {
					Assert.assertNotNull(manyColumnsEntity);

					count.increment();
				}

			});

		actionableDynamicQuery.performActions();

		Assert.assertEquals(count.getValue(), _persistence.countAll());
	}

	@Test
	public void testDynamicQueryByPrimaryKeyExisting() throws Exception {
		ManyColumnsEntity newManyColumnsEntity = addManyColumnsEntity();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			ManyColumnsEntity.class, _dynamicQueryClassLoader);

		dynamicQuery.add(
			RestrictionsFactoryUtil.eq(
				"manyColumnsEntityId",
				newManyColumnsEntity.getManyColumnsEntityId()));

		List<ManyColumnsEntity> result = _persistence.findWithDynamicQuery(
			dynamicQuery);

		Assert.assertEquals(1, result.size());

		ManyColumnsEntity existingManyColumnsEntity = result.get(0);

		Assert.assertEquals(existingManyColumnsEntity, newManyColumnsEntity);
	}

	@Test
	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			ManyColumnsEntity.class, _dynamicQueryClassLoader);

		dynamicQuery.add(
			RestrictionsFactoryUtil.eq(
				"manyColumnsEntityId", RandomTestUtil.nextLong()));

		List<ManyColumnsEntity> result = _persistence.findWithDynamicQuery(
			dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testDynamicQueryByProjectionExisting() throws Exception {
		ManyColumnsEntity newManyColumnsEntity = addManyColumnsEntity();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			ManyColumnsEntity.class, _dynamicQueryClassLoader);

		dynamicQuery.setProjection(
			ProjectionFactoryUtil.property("manyColumnsEntityId"));

		Object newManyColumnsEntityId =
			newManyColumnsEntity.getManyColumnsEntityId();

		dynamicQuery.add(
			RestrictionsFactoryUtil.in(
				"manyColumnsEntityId", new Object[] {newManyColumnsEntityId}));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		Object existingManyColumnsEntityId = result.get(0);

		Assert.assertEquals(
			existingManyColumnsEntityId, newManyColumnsEntityId);
	}

	@Test
	public void testDynamicQueryByProjectionMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			ManyColumnsEntity.class, _dynamicQueryClassLoader);

		dynamicQuery.setProjection(
			ProjectionFactoryUtil.property("manyColumnsEntityId"));

		dynamicQuery.add(
			RestrictionsFactoryUtil.in(
				"manyColumnsEntityId",
				new Object[] {RandomTestUtil.nextLong()}));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	protected ManyColumnsEntity addManyColumnsEntity() throws Exception {
		long pk = RandomTestUtil.nextLong();

		ManyColumnsEntity manyColumnsEntity = _persistence.create(pk);

		manyColumnsEntity.setColumn1(RandomTestUtil.nextInt());

		manyColumnsEntity.setColumn2(RandomTestUtil.nextInt());

		manyColumnsEntity.setColumn3(RandomTestUtil.nextInt());

		manyColumnsEntity.setColumn4(RandomTestUtil.nextInt());

		manyColumnsEntity.setColumn5(RandomTestUtil.nextInt());

		manyColumnsEntity.setColumn6(RandomTestUtil.nextInt());

		manyColumnsEntity.setColumn7(RandomTestUtil.nextInt());

		manyColumnsEntity.setColumn8(RandomTestUtil.nextInt());

		manyColumnsEntity.setColumn9(RandomTestUtil.nextInt());

		manyColumnsEntity.setColumn10(RandomTestUtil.nextInt());

		manyColumnsEntity.setColumn11(RandomTestUtil.nextInt());

		manyColumnsEntity.setColumn12(RandomTestUtil.nextInt());

		manyColumnsEntity.setColumn13(RandomTestUtil.nextInt());

		manyColumnsEntity.setColumn14(RandomTestUtil.nextInt());

		manyColumnsEntity.setColumn15(RandomTestUtil.nextInt());

		manyColumnsEntity.setColumn16(RandomTestUtil.nextInt());

		manyColumnsEntity.setColumn17(RandomTestUtil.nextInt());

		manyColumnsEntity.setColumn18(RandomTestUtil.nextInt());

		manyColumnsEntity.setColumn19(RandomTestUtil.nextInt());

		manyColumnsEntity.setColumn20(RandomTestUtil.nextInt());

		manyColumnsEntity.setColumn21(RandomTestUtil.nextInt());

		manyColumnsEntity.setColumn22(RandomTestUtil.nextInt());

		manyColumnsEntity.setColumn23(RandomTestUtil.nextInt());

		manyColumnsEntity.setColumn24(RandomTestUtil.nextInt());

		manyColumnsEntity.setColumn25(RandomTestUtil.nextInt());

		manyColumnsEntity.setColumn26(RandomTestUtil.nextInt());

		manyColumnsEntity.setColumn27(RandomTestUtil.nextInt());

		manyColumnsEntity.setColumn28(RandomTestUtil.nextInt());

		manyColumnsEntity.setColumn29(RandomTestUtil.nextInt());

		manyColumnsEntity.setColumn30(RandomTestUtil.nextInt());

		manyColumnsEntity.setColumn31(RandomTestUtil.nextInt());

		manyColumnsEntity.setColumn32(RandomTestUtil.nextInt());

		manyColumnsEntity.setColumn33(RandomTestUtil.nextInt());

		manyColumnsEntity.setColumn34(RandomTestUtil.nextInt());

		manyColumnsEntity.setColumn35(RandomTestUtil.nextInt());

		manyColumnsEntity.setColumn36(RandomTestUtil.nextInt());

		manyColumnsEntity.setColumn37(RandomTestUtil.nextInt());

		manyColumnsEntity.setColumn38(RandomTestUtil.nextInt());

		manyColumnsEntity.setColumn39(RandomTestUtil.nextInt());

		manyColumnsEntity.setColumn40(RandomTestUtil.nextInt());

		manyColumnsEntity.setColumn41(RandomTestUtil.nextInt());

		manyColumnsEntity.setColumn42(RandomTestUtil.nextInt());

		manyColumnsEntity.setColumn43(RandomTestUtil.nextInt());

		manyColumnsEntity.setColumn44(RandomTestUtil.nextInt());

		manyColumnsEntity.setColumn45(RandomTestUtil.nextInt());

		manyColumnsEntity.setColumn46(RandomTestUtil.nextInt());

		manyColumnsEntity.setColumn47(RandomTestUtil.nextInt());

		manyColumnsEntity.setColumn48(RandomTestUtil.nextInt());

		manyColumnsEntity.setColumn49(RandomTestUtil.nextInt());

		manyColumnsEntity.setColumn50(RandomTestUtil.nextInt());

		manyColumnsEntity.setColumn51(RandomTestUtil.nextInt());

		manyColumnsEntity.setColumn52(RandomTestUtil.nextInt());

		manyColumnsEntity.setColumn53(RandomTestUtil.nextInt());

		manyColumnsEntity.setColumn54(RandomTestUtil.nextInt());

		manyColumnsEntity.setColumn55(RandomTestUtil.nextInt());

		manyColumnsEntity.setColumn56(RandomTestUtil.nextInt());

		manyColumnsEntity.setColumn57(RandomTestUtil.nextInt());

		manyColumnsEntity.setColumn58(RandomTestUtil.nextInt());

		manyColumnsEntity.setColumn59(RandomTestUtil.nextInt());

		manyColumnsEntity.setColumn60(RandomTestUtil.nextInt());

		manyColumnsEntity.setColumn61(RandomTestUtil.nextInt());

		manyColumnsEntity.setColumn62(RandomTestUtil.nextInt());

		manyColumnsEntity.setColumn63(RandomTestUtil.nextInt());

		manyColumnsEntity.setColumn64(RandomTestUtil.nextInt());

		_manyColumnsEntities.add(_persistence.update(manyColumnsEntity));

		return manyColumnsEntity;
	}

	private List<ManyColumnsEntity> _manyColumnsEntities =
		new ArrayList<ManyColumnsEntity>();
	private ManyColumnsEntityPersistence _persistence;
	private ClassLoader _dynamicQueryClassLoader;

}