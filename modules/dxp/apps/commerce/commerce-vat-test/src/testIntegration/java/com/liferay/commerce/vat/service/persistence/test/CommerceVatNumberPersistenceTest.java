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

package com.liferay.commerce.vat.service.persistence.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;

import com.liferay.commerce.vat.exception.NoSuchVatNumberException;
import com.liferay.commerce.vat.model.CommerceVatNumber;
import com.liferay.commerce.vat.service.CommerceVatNumberLocalServiceUtil;
import com.liferay.commerce.vat.service.persistence.CommerceVatNumberPersistence;
import com.liferay.commerce.vat.service.persistence.CommerceVatNumberUtil;

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
public class CommerceVatNumberPersistenceTest {
	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule = new AggregateTestRule(new LiferayIntegrationTestRule(),
			PersistenceTestRule.INSTANCE,
			new TransactionalTestRule(Propagation.REQUIRED,
				"com.liferay.commerce.vat.service"));

	@Before
	public void setUp() {
		_persistence = CommerceVatNumberUtil.getPersistence();

		Class<?> clazz = _persistence.getClass();

		_dynamicQueryClassLoader = clazz.getClassLoader();
	}

	@After
	public void tearDown() throws Exception {
		Iterator<CommerceVatNumber> iterator = _commerceVatNumbers.iterator();

		while (iterator.hasNext()) {
			_persistence.remove(iterator.next());

			iterator.remove();
		}
	}

	@Test
	public void testCreate() throws Exception {
		long pk = RandomTestUtil.nextLong();

		CommerceVatNumber commerceVatNumber = _persistence.create(pk);

		Assert.assertNotNull(commerceVatNumber);

		Assert.assertEquals(commerceVatNumber.getPrimaryKey(), pk);
	}

	@Test
	public void testRemove() throws Exception {
		CommerceVatNumber newCommerceVatNumber = addCommerceVatNumber();

		_persistence.remove(newCommerceVatNumber);

		CommerceVatNumber existingCommerceVatNumber = _persistence.fetchByPrimaryKey(newCommerceVatNumber.getPrimaryKey());

		Assert.assertNull(existingCommerceVatNumber);
	}

	@Test
	public void testUpdateNew() throws Exception {
		addCommerceVatNumber();
	}

	@Test
	public void testUpdateExisting() throws Exception {
		long pk = RandomTestUtil.nextLong();

		CommerceVatNumber newCommerceVatNumber = _persistence.create(pk);

		newCommerceVatNumber.setGroupId(RandomTestUtil.nextLong());

		newCommerceVatNumber.setCompanyId(RandomTestUtil.nextLong());

		newCommerceVatNumber.setUserId(RandomTestUtil.nextLong());

		newCommerceVatNumber.setUserName(RandomTestUtil.randomString());

		newCommerceVatNumber.setCreateDate(RandomTestUtil.nextDate());

		newCommerceVatNumber.setModifiedDate(RandomTestUtil.nextDate());

		newCommerceVatNumber.setClassNameId(RandomTestUtil.nextLong());

		newCommerceVatNumber.setClassPK(RandomTestUtil.nextLong());

		newCommerceVatNumber.setVatNumber(RandomTestUtil.randomString());

		newCommerceVatNumber.setValid(RandomTestUtil.randomBoolean());

		_commerceVatNumbers.add(_persistence.update(newCommerceVatNumber));

		CommerceVatNumber existingCommerceVatNumber = _persistence.findByPrimaryKey(newCommerceVatNumber.getPrimaryKey());

		Assert.assertEquals(existingCommerceVatNumber.getCommerceVatNumberId(),
			newCommerceVatNumber.getCommerceVatNumberId());
		Assert.assertEquals(existingCommerceVatNumber.getGroupId(),
			newCommerceVatNumber.getGroupId());
		Assert.assertEquals(existingCommerceVatNumber.getCompanyId(),
			newCommerceVatNumber.getCompanyId());
		Assert.assertEquals(existingCommerceVatNumber.getUserId(),
			newCommerceVatNumber.getUserId());
		Assert.assertEquals(existingCommerceVatNumber.getUserName(),
			newCommerceVatNumber.getUserName());
		Assert.assertEquals(Time.getShortTimestamp(
				existingCommerceVatNumber.getCreateDate()),
			Time.getShortTimestamp(newCommerceVatNumber.getCreateDate()));
		Assert.assertEquals(Time.getShortTimestamp(
				existingCommerceVatNumber.getModifiedDate()),
			Time.getShortTimestamp(newCommerceVatNumber.getModifiedDate()));
		Assert.assertEquals(existingCommerceVatNumber.getClassNameId(),
			newCommerceVatNumber.getClassNameId());
		Assert.assertEquals(existingCommerceVatNumber.getClassPK(),
			newCommerceVatNumber.getClassPK());
		Assert.assertEquals(existingCommerceVatNumber.getVatNumber(),
			newCommerceVatNumber.getVatNumber());
		Assert.assertEquals(existingCommerceVatNumber.getValid(),
			newCommerceVatNumber.getValid());
	}

	@Test
	public void testCountByGroupId() throws Exception {
		_persistence.countByGroupId(RandomTestUtil.nextLong());

		_persistence.countByGroupId(0L);
	}

	@Test
	public void testCountByC_C() throws Exception {
		_persistence.countByC_C(RandomTestUtil.nextLong(),
			RandomTestUtil.nextLong());

		_persistence.countByC_C(0L, 0L);
	}

	@Test
	public void testCountByG_C_C() throws Exception {
		_persistence.countByG_C_C(RandomTestUtil.nextLong(),
			RandomTestUtil.nextLong(), RandomTestUtil.nextLong());

		_persistence.countByG_C_C(0L, 0L, 0L);
	}

	@Test
	public void testFindByPrimaryKeyExisting() throws Exception {
		CommerceVatNumber newCommerceVatNumber = addCommerceVatNumber();

		CommerceVatNumber existingCommerceVatNumber = _persistence.findByPrimaryKey(newCommerceVatNumber.getPrimaryKey());

		Assert.assertEquals(existingCommerceVatNumber, newCommerceVatNumber);
	}

	@Test(expected = NoSuchVatNumberException.class)
	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		_persistence.findByPrimaryKey(pk);
	}

	@Test
	public void testFindAll() throws Exception {
		_persistence.findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			getOrderByComparator());
	}

	protected OrderByComparator<CommerceVatNumber> getOrderByComparator() {
		return OrderByComparatorFactoryUtil.create("CommerceVatNumber",
			"commerceVatNumberId", true, "groupId", true, "companyId", true,
			"userId", true, "userName", true, "createDate", true,
			"modifiedDate", true, "classNameId", true, "classPK", true,
			"vatNumber", true, "valid", true);
	}

	@Test
	public void testFetchByPrimaryKeyExisting() throws Exception {
		CommerceVatNumber newCommerceVatNumber = addCommerceVatNumber();

		CommerceVatNumber existingCommerceVatNumber = _persistence.fetchByPrimaryKey(newCommerceVatNumber.getPrimaryKey());

		Assert.assertEquals(existingCommerceVatNumber, newCommerceVatNumber);
	}

	@Test
	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		CommerceVatNumber missingCommerceVatNumber = _persistence.fetchByPrimaryKey(pk);

		Assert.assertNull(missingCommerceVatNumber);
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereAllPrimaryKeysExist()
		throws Exception {
		CommerceVatNumber newCommerceVatNumber1 = addCommerceVatNumber();
		CommerceVatNumber newCommerceVatNumber2 = addCommerceVatNumber();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newCommerceVatNumber1.getPrimaryKey());
		primaryKeys.add(newCommerceVatNumber2.getPrimaryKey());

		Map<Serializable, CommerceVatNumber> commerceVatNumbers = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(2, commerceVatNumbers.size());
		Assert.assertEquals(newCommerceVatNumber1,
			commerceVatNumbers.get(newCommerceVatNumber1.getPrimaryKey()));
		Assert.assertEquals(newCommerceVatNumber2,
			commerceVatNumbers.get(newCommerceVatNumber2.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereNoPrimaryKeysExist()
		throws Exception {
		long pk1 = RandomTestUtil.nextLong();

		long pk2 = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(pk1);
		primaryKeys.add(pk2);

		Map<Serializable, CommerceVatNumber> commerceVatNumbers = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(commerceVatNumbers.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereSomePrimaryKeysExist()
		throws Exception {
		CommerceVatNumber newCommerceVatNumber = addCommerceVatNumber();

		long pk = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newCommerceVatNumber.getPrimaryKey());
		primaryKeys.add(pk);

		Map<Serializable, CommerceVatNumber> commerceVatNumbers = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, commerceVatNumbers.size());
		Assert.assertEquals(newCommerceVatNumber,
			commerceVatNumbers.get(newCommerceVatNumber.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithNoPrimaryKeys()
		throws Exception {
		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		Map<Serializable, CommerceVatNumber> commerceVatNumbers = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(commerceVatNumbers.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithOnePrimaryKey()
		throws Exception {
		CommerceVatNumber newCommerceVatNumber = addCommerceVatNumber();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newCommerceVatNumber.getPrimaryKey());

		Map<Serializable, CommerceVatNumber> commerceVatNumbers = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, commerceVatNumbers.size());
		Assert.assertEquals(newCommerceVatNumber,
			commerceVatNumbers.get(newCommerceVatNumber.getPrimaryKey()));
	}

	@Test
	public void testActionableDynamicQuery() throws Exception {
		final IntegerWrapper count = new IntegerWrapper();

		ActionableDynamicQuery actionableDynamicQuery = CommerceVatNumberLocalServiceUtil.getActionableDynamicQuery();

		actionableDynamicQuery.setPerformActionMethod(new ActionableDynamicQuery.PerformActionMethod<CommerceVatNumber>() {
				@Override
				public void performAction(CommerceVatNumber commerceVatNumber) {
					Assert.assertNotNull(commerceVatNumber);

					count.increment();
				}
			});

		actionableDynamicQuery.performActions();

		Assert.assertEquals(count.getValue(), _persistence.countAll());
	}

	@Test
	public void testDynamicQueryByPrimaryKeyExisting()
		throws Exception {
		CommerceVatNumber newCommerceVatNumber = addCommerceVatNumber();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(CommerceVatNumber.class,
				_dynamicQueryClassLoader);

		dynamicQuery.add(RestrictionsFactoryUtil.eq("commerceVatNumberId",
				newCommerceVatNumber.getCommerceVatNumberId()));

		List<CommerceVatNumber> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		CommerceVatNumber existingCommerceVatNumber = result.get(0);

		Assert.assertEquals(existingCommerceVatNumber, newCommerceVatNumber);
	}

	@Test
	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(CommerceVatNumber.class,
				_dynamicQueryClassLoader);

		dynamicQuery.add(RestrictionsFactoryUtil.eq("commerceVatNumberId",
				RandomTestUtil.nextLong()));

		List<CommerceVatNumber> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testDynamicQueryByProjectionExisting()
		throws Exception {
		CommerceVatNumber newCommerceVatNumber = addCommerceVatNumber();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(CommerceVatNumber.class,
				_dynamicQueryClassLoader);

		dynamicQuery.setProjection(ProjectionFactoryUtil.property(
				"commerceVatNumberId"));

		Object newCommerceVatNumberId = newCommerceVatNumber.getCommerceVatNumberId();

		dynamicQuery.add(RestrictionsFactoryUtil.in("commerceVatNumberId",
				new Object[] { newCommerceVatNumberId }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		Object existingCommerceVatNumberId = result.get(0);

		Assert.assertEquals(existingCommerceVatNumberId, newCommerceVatNumberId);
	}

	@Test
	public void testDynamicQueryByProjectionMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(CommerceVatNumber.class,
				_dynamicQueryClassLoader);

		dynamicQuery.setProjection(ProjectionFactoryUtil.property(
				"commerceVatNumberId"));

		dynamicQuery.add(RestrictionsFactoryUtil.in("commerceVatNumberId",
				new Object[] { RandomTestUtil.nextLong() }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testResetOriginalValues() throws Exception {
		CommerceVatNumber newCommerceVatNumber = addCommerceVatNumber();

		_persistence.clearCache();

		CommerceVatNumber existingCommerceVatNumber = _persistence.findByPrimaryKey(newCommerceVatNumber.getPrimaryKey());

		Assert.assertEquals(Long.valueOf(existingCommerceVatNumber.getGroupId()),
			ReflectionTestUtil.<Long>invoke(existingCommerceVatNumber,
				"getOriginalGroupId", new Class<?>[0]));
		Assert.assertEquals(Long.valueOf(
				existingCommerceVatNumber.getClassNameId()),
			ReflectionTestUtil.<Long>invoke(existingCommerceVatNumber,
				"getOriginalClassNameId", new Class<?>[0]));
		Assert.assertEquals(Long.valueOf(existingCommerceVatNumber.getClassPK()),
			ReflectionTestUtil.<Long>invoke(existingCommerceVatNumber,
				"getOriginalClassPK", new Class<?>[0]));
	}

	protected CommerceVatNumber addCommerceVatNumber()
		throws Exception {
		long pk = RandomTestUtil.nextLong();

		CommerceVatNumber commerceVatNumber = _persistence.create(pk);

		commerceVatNumber.setGroupId(RandomTestUtil.nextLong());

		commerceVatNumber.setCompanyId(RandomTestUtil.nextLong());

		commerceVatNumber.setUserId(RandomTestUtil.nextLong());

		commerceVatNumber.setUserName(RandomTestUtil.randomString());

		commerceVatNumber.setCreateDate(RandomTestUtil.nextDate());

		commerceVatNumber.setModifiedDate(RandomTestUtil.nextDate());

		commerceVatNumber.setClassNameId(RandomTestUtil.nextLong());

		commerceVatNumber.setClassPK(RandomTestUtil.nextLong());

		commerceVatNumber.setVatNumber(RandomTestUtil.randomString());

		commerceVatNumber.setValid(RandomTestUtil.randomBoolean());

		_commerceVatNumbers.add(_persistence.update(commerceVatNumber));

		return commerceVatNumber;
	}

	private List<CommerceVatNumber> _commerceVatNumbers = new ArrayList<CommerceVatNumber>();
	private CommerceVatNumberPersistence _persistence;
	private ClassLoader _dynamicQueryClassLoader;
}