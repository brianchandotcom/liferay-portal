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
import com.liferay.portal.tools.service.builder.test.exception.NoSuchLargeAmountColumnEntityException;
import com.liferay.portal.tools.service.builder.test.model.LargeAmountColumnEntity;
import com.liferay.portal.tools.service.builder.test.service.LargeAmountColumnEntityLocalServiceUtil;
import com.liferay.portal.tools.service.builder.test.service.persistence.LargeAmountColumnEntityPersistence;
import com.liferay.portal.tools.service.builder.test.service.persistence.LargeAmountColumnEntityUtil;

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
public class LargeAmountColumnEntityPersistenceTest {

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
		_persistence = LargeAmountColumnEntityUtil.getPersistence();

		Class<?> clazz = _persistence.getClass();

		_dynamicQueryClassLoader = clazz.getClassLoader();
	}

	@After
	public void tearDown() throws Exception {
		Iterator<LargeAmountColumnEntity> iterator =
			_largeAmountColumnEntities.iterator();

		while (iterator.hasNext()) {
			_persistence.remove(iterator.next());

			iterator.remove();
		}
	}

	@Test
	public void testCreate() throws Exception {
		long pk = RandomTestUtil.nextLong();

		LargeAmountColumnEntity largeAmountColumnEntity = _persistence.create(
			pk);

		Assert.assertNotNull(largeAmountColumnEntity);

		Assert.assertEquals(largeAmountColumnEntity.getPrimaryKey(), pk);
	}

	@Test
	public void testRemove() throws Exception {
		LargeAmountColumnEntity newLargeAmountColumnEntity =
			addLargeAmountColumnEntity();

		_persistence.remove(newLargeAmountColumnEntity);

		LargeAmountColumnEntity existingLargeAmountColumnEntity =
			_persistence.fetchByPrimaryKey(
				newLargeAmountColumnEntity.getPrimaryKey());

		Assert.assertNull(existingLargeAmountColumnEntity);
	}

	@Test
	public void testUpdateNew() throws Exception {
		addLargeAmountColumnEntity();
	}

	@Test
	public void testUpdateExisting() throws Exception {
		long pk = RandomTestUtil.nextLong();

		LargeAmountColumnEntity newLargeAmountColumnEntity =
			_persistence.create(pk);

		newLargeAmountColumnEntity.setColumn1(RandomTestUtil.nextInt());

		newLargeAmountColumnEntity.setColumn2(RandomTestUtil.nextInt());

		newLargeAmountColumnEntity.setColumn3(RandomTestUtil.nextInt());

		newLargeAmountColumnEntity.setColumn4(RandomTestUtil.nextInt());

		newLargeAmountColumnEntity.setColumn5(RandomTestUtil.nextInt());

		newLargeAmountColumnEntity.setColumn6(RandomTestUtil.nextInt());

		newLargeAmountColumnEntity.setColumn7(RandomTestUtil.nextInt());

		newLargeAmountColumnEntity.setColumn8(RandomTestUtil.nextInt());

		newLargeAmountColumnEntity.setColumn9(RandomTestUtil.nextInt());

		newLargeAmountColumnEntity.setColumn10(RandomTestUtil.nextInt());

		newLargeAmountColumnEntity.setColumn11(RandomTestUtil.nextInt());

		newLargeAmountColumnEntity.setColumn12(RandomTestUtil.nextInt());

		newLargeAmountColumnEntity.setColumn13(RandomTestUtil.nextInt());

		newLargeAmountColumnEntity.setColumn14(RandomTestUtil.nextInt());

		newLargeAmountColumnEntity.setColumn15(RandomTestUtil.nextInt());

		newLargeAmountColumnEntity.setColumn16(RandomTestUtil.nextInt());

		newLargeAmountColumnEntity.setColumn17(RandomTestUtil.nextInt());

		newLargeAmountColumnEntity.setColumn18(RandomTestUtil.nextInt());

		newLargeAmountColumnEntity.setColumn19(RandomTestUtil.nextInt());

		newLargeAmountColumnEntity.setColumn20(RandomTestUtil.nextInt());

		newLargeAmountColumnEntity.setColumn21(RandomTestUtil.nextInt());

		newLargeAmountColumnEntity.setColumn22(RandomTestUtil.nextInt());

		newLargeAmountColumnEntity.setColumn23(RandomTestUtil.nextInt());

		newLargeAmountColumnEntity.setColumn24(RandomTestUtil.nextInt());

		newLargeAmountColumnEntity.setColumn25(RandomTestUtil.nextInt());

		newLargeAmountColumnEntity.setColumn26(RandomTestUtil.nextInt());

		newLargeAmountColumnEntity.setColumn27(RandomTestUtil.nextInt());

		newLargeAmountColumnEntity.setColumn28(RandomTestUtil.nextInt());

		newLargeAmountColumnEntity.setColumn29(RandomTestUtil.nextInt());

		newLargeAmountColumnEntity.setColumn30(RandomTestUtil.nextInt());

		newLargeAmountColumnEntity.setColumn31(RandomTestUtil.nextInt());

		newLargeAmountColumnEntity.setColumn32(RandomTestUtil.nextInt());

		newLargeAmountColumnEntity.setColumn33(RandomTestUtil.nextInt());

		newLargeAmountColumnEntity.setColumn34(RandomTestUtil.nextInt());

		newLargeAmountColumnEntity.setColumn35(RandomTestUtil.nextInt());

		newLargeAmountColumnEntity.setColumn36(RandomTestUtil.nextInt());

		newLargeAmountColumnEntity.setColumn37(RandomTestUtil.nextInt());

		newLargeAmountColumnEntity.setColumn38(RandomTestUtil.nextInt());

		newLargeAmountColumnEntity.setColumn39(RandomTestUtil.nextInt());

		newLargeAmountColumnEntity.setColumn40(RandomTestUtil.nextInt());

		newLargeAmountColumnEntity.setColumn41(RandomTestUtil.nextInt());

		newLargeAmountColumnEntity.setColumn42(RandomTestUtil.nextInt());

		newLargeAmountColumnEntity.setColumn43(RandomTestUtil.nextInt());

		newLargeAmountColumnEntity.setColumn44(RandomTestUtil.nextInt());

		newLargeAmountColumnEntity.setColumn45(RandomTestUtil.nextInt());

		newLargeAmountColumnEntity.setColumn46(RandomTestUtil.nextInt());

		newLargeAmountColumnEntity.setColumn47(RandomTestUtil.nextInt());

		newLargeAmountColumnEntity.setColumn48(RandomTestUtil.nextInt());

		newLargeAmountColumnEntity.setColumn49(RandomTestUtil.nextInt());

		newLargeAmountColumnEntity.setColumn50(RandomTestUtil.nextInt());

		newLargeAmountColumnEntity.setColumn51(RandomTestUtil.nextInt());

		newLargeAmountColumnEntity.setColumn52(RandomTestUtil.nextInt());

		newLargeAmountColumnEntity.setColumn53(RandomTestUtil.nextInt());

		newLargeAmountColumnEntity.setColumn54(RandomTestUtil.nextInt());

		newLargeAmountColumnEntity.setColumn55(RandomTestUtil.nextInt());

		newLargeAmountColumnEntity.setColumn56(RandomTestUtil.nextInt());

		newLargeAmountColumnEntity.setColumn57(RandomTestUtil.nextInt());

		newLargeAmountColumnEntity.setColumn58(RandomTestUtil.nextInt());

		newLargeAmountColumnEntity.setColumn59(RandomTestUtil.nextInt());

		newLargeAmountColumnEntity.setColumn60(RandomTestUtil.nextInt());

		newLargeAmountColumnEntity.setColumn61(RandomTestUtil.nextInt());

		newLargeAmountColumnEntity.setColumn62(RandomTestUtil.nextInt());

		newLargeAmountColumnEntity.setColumn63(RandomTestUtil.nextInt());

		newLargeAmountColumnEntity.setColumn64(RandomTestUtil.nextInt());

		_largeAmountColumnEntities.add(
			_persistence.update(newLargeAmountColumnEntity));

		LargeAmountColumnEntity existingLargeAmountColumnEntity =
			_persistence.findByPrimaryKey(
				newLargeAmountColumnEntity.getPrimaryKey());

		Assert.assertEquals(
			existingLargeAmountColumnEntity.getLargeAmountColumnEntityId(),
			newLargeAmountColumnEntity.getLargeAmountColumnEntityId());
		Assert.assertEquals(
			existingLargeAmountColumnEntity.getColumn1(),
			newLargeAmountColumnEntity.getColumn1());
		Assert.assertEquals(
			existingLargeAmountColumnEntity.getColumn2(),
			newLargeAmountColumnEntity.getColumn2());
		Assert.assertEquals(
			existingLargeAmountColumnEntity.getColumn3(),
			newLargeAmountColumnEntity.getColumn3());
		Assert.assertEquals(
			existingLargeAmountColumnEntity.getColumn4(),
			newLargeAmountColumnEntity.getColumn4());
		Assert.assertEquals(
			existingLargeAmountColumnEntity.getColumn5(),
			newLargeAmountColumnEntity.getColumn5());
		Assert.assertEquals(
			existingLargeAmountColumnEntity.getColumn6(),
			newLargeAmountColumnEntity.getColumn6());
		Assert.assertEquals(
			existingLargeAmountColumnEntity.getColumn7(),
			newLargeAmountColumnEntity.getColumn7());
		Assert.assertEquals(
			existingLargeAmountColumnEntity.getColumn8(),
			newLargeAmountColumnEntity.getColumn8());
		Assert.assertEquals(
			existingLargeAmountColumnEntity.getColumn9(),
			newLargeAmountColumnEntity.getColumn9());
		Assert.assertEquals(
			existingLargeAmountColumnEntity.getColumn10(),
			newLargeAmountColumnEntity.getColumn10());
		Assert.assertEquals(
			existingLargeAmountColumnEntity.getColumn11(),
			newLargeAmountColumnEntity.getColumn11());
		Assert.assertEquals(
			existingLargeAmountColumnEntity.getColumn12(),
			newLargeAmountColumnEntity.getColumn12());
		Assert.assertEquals(
			existingLargeAmountColumnEntity.getColumn13(),
			newLargeAmountColumnEntity.getColumn13());
		Assert.assertEquals(
			existingLargeAmountColumnEntity.getColumn14(),
			newLargeAmountColumnEntity.getColumn14());
		Assert.assertEquals(
			existingLargeAmountColumnEntity.getColumn15(),
			newLargeAmountColumnEntity.getColumn15());
		Assert.assertEquals(
			existingLargeAmountColumnEntity.getColumn16(),
			newLargeAmountColumnEntity.getColumn16());
		Assert.assertEquals(
			existingLargeAmountColumnEntity.getColumn17(),
			newLargeAmountColumnEntity.getColumn17());
		Assert.assertEquals(
			existingLargeAmountColumnEntity.getColumn18(),
			newLargeAmountColumnEntity.getColumn18());
		Assert.assertEquals(
			existingLargeAmountColumnEntity.getColumn19(),
			newLargeAmountColumnEntity.getColumn19());
		Assert.assertEquals(
			existingLargeAmountColumnEntity.getColumn20(),
			newLargeAmountColumnEntity.getColumn20());
		Assert.assertEquals(
			existingLargeAmountColumnEntity.getColumn21(),
			newLargeAmountColumnEntity.getColumn21());
		Assert.assertEquals(
			existingLargeAmountColumnEntity.getColumn22(),
			newLargeAmountColumnEntity.getColumn22());
		Assert.assertEquals(
			existingLargeAmountColumnEntity.getColumn23(),
			newLargeAmountColumnEntity.getColumn23());
		Assert.assertEquals(
			existingLargeAmountColumnEntity.getColumn24(),
			newLargeAmountColumnEntity.getColumn24());
		Assert.assertEquals(
			existingLargeAmountColumnEntity.getColumn25(),
			newLargeAmountColumnEntity.getColumn25());
		Assert.assertEquals(
			existingLargeAmountColumnEntity.getColumn26(),
			newLargeAmountColumnEntity.getColumn26());
		Assert.assertEquals(
			existingLargeAmountColumnEntity.getColumn27(),
			newLargeAmountColumnEntity.getColumn27());
		Assert.assertEquals(
			existingLargeAmountColumnEntity.getColumn28(),
			newLargeAmountColumnEntity.getColumn28());
		Assert.assertEquals(
			existingLargeAmountColumnEntity.getColumn29(),
			newLargeAmountColumnEntity.getColumn29());
		Assert.assertEquals(
			existingLargeAmountColumnEntity.getColumn30(),
			newLargeAmountColumnEntity.getColumn30());
		Assert.assertEquals(
			existingLargeAmountColumnEntity.getColumn31(),
			newLargeAmountColumnEntity.getColumn31());
		Assert.assertEquals(
			existingLargeAmountColumnEntity.getColumn32(),
			newLargeAmountColumnEntity.getColumn32());
		Assert.assertEquals(
			existingLargeAmountColumnEntity.getColumn33(),
			newLargeAmountColumnEntity.getColumn33());
		Assert.assertEquals(
			existingLargeAmountColumnEntity.getColumn34(),
			newLargeAmountColumnEntity.getColumn34());
		Assert.assertEquals(
			existingLargeAmountColumnEntity.getColumn35(),
			newLargeAmountColumnEntity.getColumn35());
		Assert.assertEquals(
			existingLargeAmountColumnEntity.getColumn36(),
			newLargeAmountColumnEntity.getColumn36());
		Assert.assertEquals(
			existingLargeAmountColumnEntity.getColumn37(),
			newLargeAmountColumnEntity.getColumn37());
		Assert.assertEquals(
			existingLargeAmountColumnEntity.getColumn38(),
			newLargeAmountColumnEntity.getColumn38());
		Assert.assertEquals(
			existingLargeAmountColumnEntity.getColumn39(),
			newLargeAmountColumnEntity.getColumn39());
		Assert.assertEquals(
			existingLargeAmountColumnEntity.getColumn40(),
			newLargeAmountColumnEntity.getColumn40());
		Assert.assertEquals(
			existingLargeAmountColumnEntity.getColumn41(),
			newLargeAmountColumnEntity.getColumn41());
		Assert.assertEquals(
			existingLargeAmountColumnEntity.getColumn42(),
			newLargeAmountColumnEntity.getColumn42());
		Assert.assertEquals(
			existingLargeAmountColumnEntity.getColumn43(),
			newLargeAmountColumnEntity.getColumn43());
		Assert.assertEquals(
			existingLargeAmountColumnEntity.getColumn44(),
			newLargeAmountColumnEntity.getColumn44());
		Assert.assertEquals(
			existingLargeAmountColumnEntity.getColumn45(),
			newLargeAmountColumnEntity.getColumn45());
		Assert.assertEquals(
			existingLargeAmountColumnEntity.getColumn46(),
			newLargeAmountColumnEntity.getColumn46());
		Assert.assertEquals(
			existingLargeAmountColumnEntity.getColumn47(),
			newLargeAmountColumnEntity.getColumn47());
		Assert.assertEquals(
			existingLargeAmountColumnEntity.getColumn48(),
			newLargeAmountColumnEntity.getColumn48());
		Assert.assertEquals(
			existingLargeAmountColumnEntity.getColumn49(),
			newLargeAmountColumnEntity.getColumn49());
		Assert.assertEquals(
			existingLargeAmountColumnEntity.getColumn50(),
			newLargeAmountColumnEntity.getColumn50());
		Assert.assertEquals(
			existingLargeAmountColumnEntity.getColumn51(),
			newLargeAmountColumnEntity.getColumn51());
		Assert.assertEquals(
			existingLargeAmountColumnEntity.getColumn52(),
			newLargeAmountColumnEntity.getColumn52());
		Assert.assertEquals(
			existingLargeAmountColumnEntity.getColumn53(),
			newLargeAmountColumnEntity.getColumn53());
		Assert.assertEquals(
			existingLargeAmountColumnEntity.getColumn54(),
			newLargeAmountColumnEntity.getColumn54());
		Assert.assertEquals(
			existingLargeAmountColumnEntity.getColumn55(),
			newLargeAmountColumnEntity.getColumn55());
		Assert.assertEquals(
			existingLargeAmountColumnEntity.getColumn56(),
			newLargeAmountColumnEntity.getColumn56());
		Assert.assertEquals(
			existingLargeAmountColumnEntity.getColumn57(),
			newLargeAmountColumnEntity.getColumn57());
		Assert.assertEquals(
			existingLargeAmountColumnEntity.getColumn58(),
			newLargeAmountColumnEntity.getColumn58());
		Assert.assertEquals(
			existingLargeAmountColumnEntity.getColumn59(),
			newLargeAmountColumnEntity.getColumn59());
		Assert.assertEquals(
			existingLargeAmountColumnEntity.getColumn60(),
			newLargeAmountColumnEntity.getColumn60());
		Assert.assertEquals(
			existingLargeAmountColumnEntity.getColumn61(),
			newLargeAmountColumnEntity.getColumn61());
		Assert.assertEquals(
			existingLargeAmountColumnEntity.getColumn62(),
			newLargeAmountColumnEntity.getColumn62());
		Assert.assertEquals(
			existingLargeAmountColumnEntity.getColumn63(),
			newLargeAmountColumnEntity.getColumn63());
		Assert.assertEquals(
			existingLargeAmountColumnEntity.getColumn64(),
			newLargeAmountColumnEntity.getColumn64());
	}

	@Test
	public void testFindByPrimaryKeyExisting() throws Exception {
		LargeAmountColumnEntity newLargeAmountColumnEntity =
			addLargeAmountColumnEntity();

		LargeAmountColumnEntity existingLargeAmountColumnEntity =
			_persistence.findByPrimaryKey(
				newLargeAmountColumnEntity.getPrimaryKey());

		Assert.assertEquals(
			existingLargeAmountColumnEntity, newLargeAmountColumnEntity);
	}

	@Test(expected = NoSuchLargeAmountColumnEntityException.class)
	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		_persistence.findByPrimaryKey(pk);
	}

	@Test
	public void testFindAll() throws Exception {
		_persistence.findAll(
			QueryUtil.ALL_POS, QueryUtil.ALL_POS, getOrderByComparator());
	}

	protected OrderByComparator<LargeAmountColumnEntity>
		getOrderByComparator() {

		return OrderByComparatorFactoryUtil.create(
			"LargeAmountColumnEntity", "largeAmountColumnEntityId", true,
			"column1", true, "column2", true, "column3", true, "column4", true,
			"column5", true, "column6", true, "column7", true, "column8", true,
			"column9", true, "column10", true, "column11", true, "column12",
			true, "column13", true, "column14", true, "column15", true,
			"column16", true, "column17", true, "column18", true, "column19",
			true, "column20", true, "column21", true, "column22", true,
			"column23", true, "column24", true, "column25", true, "column26",
			true, "column27", true, "column28", true, "column29", true,
			"column30", true, "column31", true, "column32", true, "column33",
			true, "column34", true, "column35", true, "column36", true,
			"column37", true, "column38", true, "column39", true, "column40",
			true, "column41", true, "column42", true, "column43", true,
			"column44", true, "column45", true, "column46", true, "column47",
			true, "column48", true, "column49", true, "column50", true,
			"column51", true, "column52", true, "column53", true, "column54",
			true, "column55", true, "column56", true, "column57", true,
			"column58", true, "column59", true, "column60", true, "column61",
			true, "column62", true, "column63", true, "column64", true);
	}

	@Test
	public void testFetchByPrimaryKeyExisting() throws Exception {
		LargeAmountColumnEntity newLargeAmountColumnEntity =
			addLargeAmountColumnEntity();

		LargeAmountColumnEntity existingLargeAmountColumnEntity =
			_persistence.fetchByPrimaryKey(
				newLargeAmountColumnEntity.getPrimaryKey());

		Assert.assertEquals(
			existingLargeAmountColumnEntity, newLargeAmountColumnEntity);
	}

	@Test
	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		LargeAmountColumnEntity missingLargeAmountColumnEntity =
			_persistence.fetchByPrimaryKey(pk);

		Assert.assertNull(missingLargeAmountColumnEntity);
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereAllPrimaryKeysExist()
		throws Exception {

		LargeAmountColumnEntity newLargeAmountColumnEntity1 =
			addLargeAmountColumnEntity();
		LargeAmountColumnEntity newLargeAmountColumnEntity2 =
			addLargeAmountColumnEntity();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newLargeAmountColumnEntity1.getPrimaryKey());
		primaryKeys.add(newLargeAmountColumnEntity2.getPrimaryKey());

		Map<Serializable, LargeAmountColumnEntity> largeAmountColumnEntities =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(2, largeAmountColumnEntities.size());
		Assert.assertEquals(
			newLargeAmountColumnEntity1,
			largeAmountColumnEntities.get(
				newLargeAmountColumnEntity1.getPrimaryKey()));
		Assert.assertEquals(
			newLargeAmountColumnEntity2,
			largeAmountColumnEntities.get(
				newLargeAmountColumnEntity2.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereNoPrimaryKeysExist()
		throws Exception {

		long pk1 = RandomTestUtil.nextLong();

		long pk2 = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(pk1);
		primaryKeys.add(pk2);

		Map<Serializable, LargeAmountColumnEntity> largeAmountColumnEntities =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(largeAmountColumnEntities.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereSomePrimaryKeysExist()
		throws Exception {

		LargeAmountColumnEntity newLargeAmountColumnEntity =
			addLargeAmountColumnEntity();

		long pk = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newLargeAmountColumnEntity.getPrimaryKey());
		primaryKeys.add(pk);

		Map<Serializable, LargeAmountColumnEntity> largeAmountColumnEntities =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, largeAmountColumnEntities.size());
		Assert.assertEquals(
			newLargeAmountColumnEntity,
			largeAmountColumnEntities.get(
				newLargeAmountColumnEntity.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithNoPrimaryKeys() throws Exception {
		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		Map<Serializable, LargeAmountColumnEntity> largeAmountColumnEntities =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(largeAmountColumnEntities.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithOnePrimaryKey() throws Exception {
		LargeAmountColumnEntity newLargeAmountColumnEntity =
			addLargeAmountColumnEntity();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newLargeAmountColumnEntity.getPrimaryKey());

		Map<Serializable, LargeAmountColumnEntity> largeAmountColumnEntities =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, largeAmountColumnEntities.size());
		Assert.assertEquals(
			newLargeAmountColumnEntity,
			largeAmountColumnEntities.get(
				newLargeAmountColumnEntity.getPrimaryKey()));
	}

	@Test
	public void testActionableDynamicQuery() throws Exception {
		final IntegerWrapper count = new IntegerWrapper();

		ActionableDynamicQuery actionableDynamicQuery =
			LargeAmountColumnEntityLocalServiceUtil.getActionableDynamicQuery();

		actionableDynamicQuery.setPerformActionMethod(
			new ActionableDynamicQuery.PerformActionMethod
				<LargeAmountColumnEntity>() {

				@Override
				public void performAction(
					LargeAmountColumnEntity largeAmountColumnEntity) {

					Assert.assertNotNull(largeAmountColumnEntity);

					count.increment();
				}

			});

		actionableDynamicQuery.performActions();

		Assert.assertEquals(count.getValue(), _persistence.countAll());
	}

	@Test
	public void testDynamicQueryByPrimaryKeyExisting() throws Exception {
		LargeAmountColumnEntity newLargeAmountColumnEntity =
			addLargeAmountColumnEntity();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			LargeAmountColumnEntity.class, _dynamicQueryClassLoader);

		dynamicQuery.add(
			RestrictionsFactoryUtil.eq(
				"largeAmountColumnEntityId",
				newLargeAmountColumnEntity.getLargeAmountColumnEntityId()));

		List<LargeAmountColumnEntity> result =
			_persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		LargeAmountColumnEntity existingLargeAmountColumnEntity = result.get(0);

		Assert.assertEquals(
			existingLargeAmountColumnEntity, newLargeAmountColumnEntity);
	}

	@Test
	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			LargeAmountColumnEntity.class, _dynamicQueryClassLoader);

		dynamicQuery.add(
			RestrictionsFactoryUtil.eq(
				"largeAmountColumnEntityId", RandomTestUtil.nextLong()));

		List<LargeAmountColumnEntity> result =
			_persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testDynamicQueryByProjectionExisting() throws Exception {
		LargeAmountColumnEntity newLargeAmountColumnEntity =
			addLargeAmountColumnEntity();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			LargeAmountColumnEntity.class, _dynamicQueryClassLoader);

		dynamicQuery.setProjection(
			ProjectionFactoryUtil.property("largeAmountColumnEntityId"));

		Object newLargeAmountColumnEntityId =
			newLargeAmountColumnEntity.getLargeAmountColumnEntityId();

		dynamicQuery.add(
			RestrictionsFactoryUtil.in(
				"largeAmountColumnEntityId",
				new Object[] {newLargeAmountColumnEntityId}));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		Object existingLargeAmountColumnEntityId = result.get(0);

		Assert.assertEquals(
			existingLargeAmountColumnEntityId, newLargeAmountColumnEntityId);
	}

	@Test
	public void testDynamicQueryByProjectionMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			LargeAmountColumnEntity.class, _dynamicQueryClassLoader);

		dynamicQuery.setProjection(
			ProjectionFactoryUtil.property("largeAmountColumnEntityId"));

		dynamicQuery.add(
			RestrictionsFactoryUtil.in(
				"largeAmountColumnEntityId",
				new Object[] {RandomTestUtil.nextLong()}));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	protected LargeAmountColumnEntity addLargeAmountColumnEntity()
		throws Exception {

		long pk = RandomTestUtil.nextLong();

		LargeAmountColumnEntity largeAmountColumnEntity = _persistence.create(
			pk);

		largeAmountColumnEntity.setColumn1(RandomTestUtil.nextInt());

		largeAmountColumnEntity.setColumn2(RandomTestUtil.nextInt());

		largeAmountColumnEntity.setColumn3(RandomTestUtil.nextInt());

		largeAmountColumnEntity.setColumn4(RandomTestUtil.nextInt());

		largeAmountColumnEntity.setColumn5(RandomTestUtil.nextInt());

		largeAmountColumnEntity.setColumn6(RandomTestUtil.nextInt());

		largeAmountColumnEntity.setColumn7(RandomTestUtil.nextInt());

		largeAmountColumnEntity.setColumn8(RandomTestUtil.nextInt());

		largeAmountColumnEntity.setColumn9(RandomTestUtil.nextInt());

		largeAmountColumnEntity.setColumn10(RandomTestUtil.nextInt());

		largeAmountColumnEntity.setColumn11(RandomTestUtil.nextInt());

		largeAmountColumnEntity.setColumn12(RandomTestUtil.nextInt());

		largeAmountColumnEntity.setColumn13(RandomTestUtil.nextInt());

		largeAmountColumnEntity.setColumn14(RandomTestUtil.nextInt());

		largeAmountColumnEntity.setColumn15(RandomTestUtil.nextInt());

		largeAmountColumnEntity.setColumn16(RandomTestUtil.nextInt());

		largeAmountColumnEntity.setColumn17(RandomTestUtil.nextInt());

		largeAmountColumnEntity.setColumn18(RandomTestUtil.nextInt());

		largeAmountColumnEntity.setColumn19(RandomTestUtil.nextInt());

		largeAmountColumnEntity.setColumn20(RandomTestUtil.nextInt());

		largeAmountColumnEntity.setColumn21(RandomTestUtil.nextInt());

		largeAmountColumnEntity.setColumn22(RandomTestUtil.nextInt());

		largeAmountColumnEntity.setColumn23(RandomTestUtil.nextInt());

		largeAmountColumnEntity.setColumn24(RandomTestUtil.nextInt());

		largeAmountColumnEntity.setColumn25(RandomTestUtil.nextInt());

		largeAmountColumnEntity.setColumn26(RandomTestUtil.nextInt());

		largeAmountColumnEntity.setColumn27(RandomTestUtil.nextInt());

		largeAmountColumnEntity.setColumn28(RandomTestUtil.nextInt());

		largeAmountColumnEntity.setColumn29(RandomTestUtil.nextInt());

		largeAmountColumnEntity.setColumn30(RandomTestUtil.nextInt());

		largeAmountColumnEntity.setColumn31(RandomTestUtil.nextInt());

		largeAmountColumnEntity.setColumn32(RandomTestUtil.nextInt());

		largeAmountColumnEntity.setColumn33(RandomTestUtil.nextInt());

		largeAmountColumnEntity.setColumn34(RandomTestUtil.nextInt());

		largeAmountColumnEntity.setColumn35(RandomTestUtil.nextInt());

		largeAmountColumnEntity.setColumn36(RandomTestUtil.nextInt());

		largeAmountColumnEntity.setColumn37(RandomTestUtil.nextInt());

		largeAmountColumnEntity.setColumn38(RandomTestUtil.nextInt());

		largeAmountColumnEntity.setColumn39(RandomTestUtil.nextInt());

		largeAmountColumnEntity.setColumn40(RandomTestUtil.nextInt());

		largeAmountColumnEntity.setColumn41(RandomTestUtil.nextInt());

		largeAmountColumnEntity.setColumn42(RandomTestUtil.nextInt());

		largeAmountColumnEntity.setColumn43(RandomTestUtil.nextInt());

		largeAmountColumnEntity.setColumn44(RandomTestUtil.nextInt());

		largeAmountColumnEntity.setColumn45(RandomTestUtil.nextInt());

		largeAmountColumnEntity.setColumn46(RandomTestUtil.nextInt());

		largeAmountColumnEntity.setColumn47(RandomTestUtil.nextInt());

		largeAmountColumnEntity.setColumn48(RandomTestUtil.nextInt());

		largeAmountColumnEntity.setColumn49(RandomTestUtil.nextInt());

		largeAmountColumnEntity.setColumn50(RandomTestUtil.nextInt());

		largeAmountColumnEntity.setColumn51(RandomTestUtil.nextInt());

		largeAmountColumnEntity.setColumn52(RandomTestUtil.nextInt());

		largeAmountColumnEntity.setColumn53(RandomTestUtil.nextInt());

		largeAmountColumnEntity.setColumn54(RandomTestUtil.nextInt());

		largeAmountColumnEntity.setColumn55(RandomTestUtil.nextInt());

		largeAmountColumnEntity.setColumn56(RandomTestUtil.nextInt());

		largeAmountColumnEntity.setColumn57(RandomTestUtil.nextInt());

		largeAmountColumnEntity.setColumn58(RandomTestUtil.nextInt());

		largeAmountColumnEntity.setColumn59(RandomTestUtil.nextInt());

		largeAmountColumnEntity.setColumn60(RandomTestUtil.nextInt());

		largeAmountColumnEntity.setColumn61(RandomTestUtil.nextInt());

		largeAmountColumnEntity.setColumn62(RandomTestUtil.nextInt());

		largeAmountColumnEntity.setColumn63(RandomTestUtil.nextInt());

		largeAmountColumnEntity.setColumn64(RandomTestUtil.nextInt());

		_largeAmountColumnEntities.add(
			_persistence.update(largeAmountColumnEntity));

		return largeAmountColumnEntity;
	}

	private List<LargeAmountColumnEntity> _largeAmountColumnEntities =
		new ArrayList<LargeAmountColumnEntity>();
	private LargeAmountColumnEntityPersistence _persistence;
	private ClassLoader _dynamicQueryClassLoader;

}