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

package com.liferay.commerce.order.rule.service.persistence.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.commerce.order.rule.exception.NoSuchOrderRuleEntryRelException;
import com.liferay.commerce.order.rule.model.CommerceOrderRuleEntryRel;
import com.liferay.commerce.order.rule.service.CommerceOrderRuleEntryRelLocalServiceUtil;
import com.liferay.commerce.order.rule.service.persistence.CommerceOrderRuleEntryRelPersistence;
import com.liferay.commerce.order.rule.service.persistence.CommerceOrderRuleEntryRelUtil;
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
public class CommerceOrderRuleEntryRelPersistenceTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(), PersistenceTestRule.INSTANCE,
			new TransactionalTestRule(
				Propagation.REQUIRED,
				"com.liferay.commerce.order.rule.service"));

	@Before
	public void setUp() {
		_persistence = CommerceOrderRuleEntryRelUtil.getPersistence();

		Class<?> clazz = _persistence.getClass();

		_dynamicQueryClassLoader = clazz.getClassLoader();
	}

	@After
	public void tearDown() throws Exception {
		Iterator<CommerceOrderRuleEntryRel> iterator =
			_commerceOrderRuleEntryRels.iterator();

		while (iterator.hasNext()) {
			_persistence.remove(iterator.next());

			iterator.remove();
		}
	}

	@Test
	public void testCreate() throws Exception {
		long pk = RandomTestUtil.nextLong();

		CommerceOrderRuleEntryRel commerceOrderRuleEntryRel =
			_persistence.create(pk);

		Assert.assertNotNull(commerceOrderRuleEntryRel);

		Assert.assertEquals(commerceOrderRuleEntryRel.getPrimaryKey(), pk);
	}

	@Test
	public void testRemove() throws Exception {
		CommerceOrderRuleEntryRel newCommerceOrderRuleEntryRel =
			addCommerceOrderRuleEntryRel();

		_persistence.remove(newCommerceOrderRuleEntryRel);

		CommerceOrderRuleEntryRel existingCommerceOrderRuleEntryRel =
			_persistence.fetchByPrimaryKey(
				newCommerceOrderRuleEntryRel.getPrimaryKey());

		Assert.assertNull(existingCommerceOrderRuleEntryRel);
	}

	@Test
	public void testUpdateNew() throws Exception {
		addCommerceOrderRuleEntryRel();
	}

	@Test
	public void testUpdateExisting() throws Exception {
		long pk = RandomTestUtil.nextLong();

		CommerceOrderRuleEntryRel newCommerceOrderRuleEntryRel =
			_persistence.create(pk);

		newCommerceOrderRuleEntryRel.setCompanyId(RandomTestUtil.nextLong());

		newCommerceOrderRuleEntryRel.setUserId(RandomTestUtil.nextLong());

		newCommerceOrderRuleEntryRel.setUserName(RandomTestUtil.randomString());

		newCommerceOrderRuleEntryRel.setCreateDate(RandomTestUtil.nextDate());

		newCommerceOrderRuleEntryRel.setModifiedDate(RandomTestUtil.nextDate());

		newCommerceOrderRuleEntryRel.setClassNameId(RandomTestUtil.nextLong());

		newCommerceOrderRuleEntryRel.setClassPK(RandomTestUtil.nextLong());

		newCommerceOrderRuleEntryRel.setCommerceOrderRuleEntryId(
			RandomTestUtil.nextLong());

		_commerceOrderRuleEntryRels.add(
			_persistence.update(newCommerceOrderRuleEntryRel));

		CommerceOrderRuleEntryRel existingCommerceOrderRuleEntryRel =
			_persistence.findByPrimaryKey(
				newCommerceOrderRuleEntryRel.getPrimaryKey());

		Assert.assertEquals(
			existingCommerceOrderRuleEntryRel.getCommerceOrderRuleEntryRelId(),
			newCommerceOrderRuleEntryRel.getCommerceOrderRuleEntryRelId());
		Assert.assertEquals(
			existingCommerceOrderRuleEntryRel.getCompanyId(),
			newCommerceOrderRuleEntryRel.getCompanyId());
		Assert.assertEquals(
			existingCommerceOrderRuleEntryRel.getUserId(),
			newCommerceOrderRuleEntryRel.getUserId());
		Assert.assertEquals(
			existingCommerceOrderRuleEntryRel.getUserName(),
			newCommerceOrderRuleEntryRel.getUserName());
		Assert.assertEquals(
			Time.getShortTimestamp(
				existingCommerceOrderRuleEntryRel.getCreateDate()),
			Time.getShortTimestamp(
				newCommerceOrderRuleEntryRel.getCreateDate()));
		Assert.assertEquals(
			Time.getShortTimestamp(
				existingCommerceOrderRuleEntryRel.getModifiedDate()),
			Time.getShortTimestamp(
				newCommerceOrderRuleEntryRel.getModifiedDate()));
		Assert.assertEquals(
			existingCommerceOrderRuleEntryRel.getClassNameId(),
			newCommerceOrderRuleEntryRel.getClassNameId());
		Assert.assertEquals(
			existingCommerceOrderRuleEntryRel.getClassPK(),
			newCommerceOrderRuleEntryRel.getClassPK());
		Assert.assertEquals(
			existingCommerceOrderRuleEntryRel.getCommerceOrderRuleEntryId(),
			newCommerceOrderRuleEntryRel.getCommerceOrderRuleEntryId());
	}

	@Test
	public void testCountByCommerceOrderRuleEntryId() throws Exception {
		_persistence.countByCommerceOrderRuleEntryId(RandomTestUtil.nextLong());

		_persistence.countByCommerceOrderRuleEntryId(0L);
	}

	@Test
	public void testCountByC_C() throws Exception {
		_persistence.countByC_C(
			RandomTestUtil.nextLong(), RandomTestUtil.nextLong());

		_persistence.countByC_C(0L, 0L);
	}

	@Test
	public void testCountByC_C_C() throws Exception {
		_persistence.countByC_C_C(
			RandomTestUtil.nextLong(), RandomTestUtil.nextLong(),
			RandomTestUtil.nextLong());

		_persistence.countByC_C_C(0L, 0L, 0L);
	}

	@Test
	public void testFindByPrimaryKeyExisting() throws Exception {
		CommerceOrderRuleEntryRel newCommerceOrderRuleEntryRel =
			addCommerceOrderRuleEntryRel();

		CommerceOrderRuleEntryRel existingCommerceOrderRuleEntryRel =
			_persistence.findByPrimaryKey(
				newCommerceOrderRuleEntryRel.getPrimaryKey());

		Assert.assertEquals(
			existingCommerceOrderRuleEntryRel, newCommerceOrderRuleEntryRel);
	}

	@Test(expected = NoSuchOrderRuleEntryRelException.class)
	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		_persistence.findByPrimaryKey(pk);
	}

	@Test
	public void testFindAll() throws Exception {
		_persistence.findAll(
			QueryUtil.ALL_POS, QueryUtil.ALL_POS, getOrderByComparator());
	}

	protected OrderByComparator<CommerceOrderRuleEntryRel>
		getOrderByComparator() {

		return OrderByComparatorFactoryUtil.create(
			"CommerceOrderRuleEntryRel", "commerceOrderRuleEntryRelId", true,
			"companyId", true, "userId", true, "userName", true, "createDate",
			true, "modifiedDate", true, "classNameId", true, "classPK", true,
			"commerceOrderRuleEntryId", true);
	}

	@Test
	public void testFetchByPrimaryKeyExisting() throws Exception {
		CommerceOrderRuleEntryRel newCommerceOrderRuleEntryRel =
			addCommerceOrderRuleEntryRel();

		CommerceOrderRuleEntryRel existingCommerceOrderRuleEntryRel =
			_persistence.fetchByPrimaryKey(
				newCommerceOrderRuleEntryRel.getPrimaryKey());

		Assert.assertEquals(
			existingCommerceOrderRuleEntryRel, newCommerceOrderRuleEntryRel);
	}

	@Test
	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		CommerceOrderRuleEntryRel missingCommerceOrderRuleEntryRel =
			_persistence.fetchByPrimaryKey(pk);

		Assert.assertNull(missingCommerceOrderRuleEntryRel);
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereAllPrimaryKeysExist()
		throws Exception {

		CommerceOrderRuleEntryRel newCommerceOrderRuleEntryRel1 =
			addCommerceOrderRuleEntryRel();
		CommerceOrderRuleEntryRel newCommerceOrderRuleEntryRel2 =
			addCommerceOrderRuleEntryRel();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newCommerceOrderRuleEntryRel1.getPrimaryKey());
		primaryKeys.add(newCommerceOrderRuleEntryRel2.getPrimaryKey());

		Map<Serializable, CommerceOrderRuleEntryRel>
			commerceOrderRuleEntryRels = _persistence.fetchByPrimaryKeys(
				primaryKeys);

		Assert.assertEquals(2, commerceOrderRuleEntryRels.size());
		Assert.assertEquals(
			newCommerceOrderRuleEntryRel1,
			commerceOrderRuleEntryRels.get(
				newCommerceOrderRuleEntryRel1.getPrimaryKey()));
		Assert.assertEquals(
			newCommerceOrderRuleEntryRel2,
			commerceOrderRuleEntryRels.get(
				newCommerceOrderRuleEntryRel2.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereNoPrimaryKeysExist()
		throws Exception {

		long pk1 = RandomTestUtil.nextLong();

		long pk2 = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(pk1);
		primaryKeys.add(pk2);

		Map<Serializable, CommerceOrderRuleEntryRel>
			commerceOrderRuleEntryRels = _persistence.fetchByPrimaryKeys(
				primaryKeys);

		Assert.assertTrue(commerceOrderRuleEntryRels.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereSomePrimaryKeysExist()
		throws Exception {

		CommerceOrderRuleEntryRel newCommerceOrderRuleEntryRel =
			addCommerceOrderRuleEntryRel();

		long pk = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newCommerceOrderRuleEntryRel.getPrimaryKey());
		primaryKeys.add(pk);

		Map<Serializable, CommerceOrderRuleEntryRel>
			commerceOrderRuleEntryRels = _persistence.fetchByPrimaryKeys(
				primaryKeys);

		Assert.assertEquals(1, commerceOrderRuleEntryRels.size());
		Assert.assertEquals(
			newCommerceOrderRuleEntryRel,
			commerceOrderRuleEntryRels.get(
				newCommerceOrderRuleEntryRel.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithNoPrimaryKeys() throws Exception {
		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		Map<Serializable, CommerceOrderRuleEntryRel>
			commerceOrderRuleEntryRels = _persistence.fetchByPrimaryKeys(
				primaryKeys);

		Assert.assertTrue(commerceOrderRuleEntryRels.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithOnePrimaryKey() throws Exception {
		CommerceOrderRuleEntryRel newCommerceOrderRuleEntryRel =
			addCommerceOrderRuleEntryRel();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newCommerceOrderRuleEntryRel.getPrimaryKey());

		Map<Serializable, CommerceOrderRuleEntryRel>
			commerceOrderRuleEntryRels = _persistence.fetchByPrimaryKeys(
				primaryKeys);

		Assert.assertEquals(1, commerceOrderRuleEntryRels.size());
		Assert.assertEquals(
			newCommerceOrderRuleEntryRel,
			commerceOrderRuleEntryRels.get(
				newCommerceOrderRuleEntryRel.getPrimaryKey()));
	}

	@Test
	public void testActionableDynamicQuery() throws Exception {
		final IntegerWrapper count = new IntegerWrapper();

		ActionableDynamicQuery actionableDynamicQuery =
			CommerceOrderRuleEntryRelLocalServiceUtil.
				getActionableDynamicQuery();

		actionableDynamicQuery.setPerformActionMethod(
			new ActionableDynamicQuery.PerformActionMethod
				<CommerceOrderRuleEntryRel>() {

				@Override
				public void performAction(
					CommerceOrderRuleEntryRel commerceOrderRuleEntryRel) {

					Assert.assertNotNull(commerceOrderRuleEntryRel);

					count.increment();
				}

			});

		actionableDynamicQuery.performActions();

		Assert.assertEquals(count.getValue(), _persistence.countAll());
	}

	@Test
	public void testDynamicQueryByPrimaryKeyExisting() throws Exception {
		CommerceOrderRuleEntryRel newCommerceOrderRuleEntryRel =
			addCommerceOrderRuleEntryRel();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			CommerceOrderRuleEntryRel.class, _dynamicQueryClassLoader);

		dynamicQuery.add(
			RestrictionsFactoryUtil.eq(
				"commerceOrderRuleEntryRelId",
				newCommerceOrderRuleEntryRel.getCommerceOrderRuleEntryRelId()));

		List<CommerceOrderRuleEntryRel> result =
			_persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		CommerceOrderRuleEntryRel existingCommerceOrderRuleEntryRel =
			result.get(0);

		Assert.assertEquals(
			existingCommerceOrderRuleEntryRel, newCommerceOrderRuleEntryRel);
	}

	@Test
	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			CommerceOrderRuleEntryRel.class, _dynamicQueryClassLoader);

		dynamicQuery.add(
			RestrictionsFactoryUtil.eq(
				"commerceOrderRuleEntryRelId", RandomTestUtil.nextLong()));

		List<CommerceOrderRuleEntryRel> result =
			_persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testDynamicQueryByProjectionExisting() throws Exception {
		CommerceOrderRuleEntryRel newCommerceOrderRuleEntryRel =
			addCommerceOrderRuleEntryRel();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			CommerceOrderRuleEntryRel.class, _dynamicQueryClassLoader);

		dynamicQuery.setProjection(
			ProjectionFactoryUtil.property("commerceOrderRuleEntryRelId"));

		Object newCommerceOrderRuleEntryRelId =
			newCommerceOrderRuleEntryRel.getCommerceOrderRuleEntryRelId();

		dynamicQuery.add(
			RestrictionsFactoryUtil.in(
				"commerceOrderRuleEntryRelId",
				new Object[] {newCommerceOrderRuleEntryRelId}));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		Object existingCommerceOrderRuleEntryRelId = result.get(0);

		Assert.assertEquals(
			existingCommerceOrderRuleEntryRelId,
			newCommerceOrderRuleEntryRelId);
	}

	@Test
	public void testDynamicQueryByProjectionMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			CommerceOrderRuleEntryRel.class, _dynamicQueryClassLoader);

		dynamicQuery.setProjection(
			ProjectionFactoryUtil.property("commerceOrderRuleEntryRelId"));

		dynamicQuery.add(
			RestrictionsFactoryUtil.in(
				"commerceOrderRuleEntryRelId",
				new Object[] {RandomTestUtil.nextLong()}));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testResetOriginalValues() throws Exception {
		CommerceOrderRuleEntryRel newCommerceOrderRuleEntryRel =
			addCommerceOrderRuleEntryRel();

		_persistence.clearCache();

		_assertOriginalValues(
			_persistence.findByPrimaryKey(
				newCommerceOrderRuleEntryRel.getPrimaryKey()));
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

		CommerceOrderRuleEntryRel newCommerceOrderRuleEntryRel =
			addCommerceOrderRuleEntryRel();

		if (clearSession) {
			Session session = _persistence.openSession();

			session.flush();

			session.clear();
		}

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			CommerceOrderRuleEntryRel.class, _dynamicQueryClassLoader);

		dynamicQuery.add(
			RestrictionsFactoryUtil.eq(
				"commerceOrderRuleEntryRelId",
				newCommerceOrderRuleEntryRel.getCommerceOrderRuleEntryRelId()));

		List<CommerceOrderRuleEntryRel> result =
			_persistence.findWithDynamicQuery(dynamicQuery);

		_assertOriginalValues(result.get(0));
	}

	private void _assertOriginalValues(
		CommerceOrderRuleEntryRel commerceOrderRuleEntryRel) {

		Assert.assertEquals(
			Long.valueOf(commerceOrderRuleEntryRel.getClassNameId()),
			ReflectionTestUtil.<Long>invoke(
				commerceOrderRuleEntryRel, "getColumnOriginalValue",
				new Class<?>[] {String.class}, "classNameId"));
		Assert.assertEquals(
			Long.valueOf(commerceOrderRuleEntryRel.getClassPK()),
			ReflectionTestUtil.<Long>invoke(
				commerceOrderRuleEntryRel, "getColumnOriginalValue",
				new Class<?>[] {String.class}, "classPK"));
		Assert.assertEquals(
			Long.valueOf(
				commerceOrderRuleEntryRel.getCommerceOrderRuleEntryId()),
			ReflectionTestUtil.<Long>invoke(
				commerceOrderRuleEntryRel, "getColumnOriginalValue",
				new Class<?>[] {String.class}, "commerceOrderRuleEntryId"));
	}

	protected CommerceOrderRuleEntryRel addCommerceOrderRuleEntryRel()
		throws Exception {

		long pk = RandomTestUtil.nextLong();

		CommerceOrderRuleEntryRel commerceOrderRuleEntryRel =
			_persistence.create(pk);

		commerceOrderRuleEntryRel.setCompanyId(RandomTestUtil.nextLong());

		commerceOrderRuleEntryRel.setUserId(RandomTestUtil.nextLong());

		commerceOrderRuleEntryRel.setUserName(RandomTestUtil.randomString());

		commerceOrderRuleEntryRel.setCreateDate(RandomTestUtil.nextDate());

		commerceOrderRuleEntryRel.setModifiedDate(RandomTestUtil.nextDate());

		commerceOrderRuleEntryRel.setClassNameId(RandomTestUtil.nextLong());

		commerceOrderRuleEntryRel.setClassPK(RandomTestUtil.nextLong());

		commerceOrderRuleEntryRel.setCommerceOrderRuleEntryId(
			RandomTestUtil.nextLong());

		_commerceOrderRuleEntryRels.add(
			_persistence.update(commerceOrderRuleEntryRel));

		return commerceOrderRuleEntryRel;
	}

	private List<CommerceOrderRuleEntryRel> _commerceOrderRuleEntryRels =
		new ArrayList<CommerceOrderRuleEntryRel>();
	private CommerceOrderRuleEntryRelPersistence _persistence;
	private ClassLoader _dynamicQueryClassLoader;

}