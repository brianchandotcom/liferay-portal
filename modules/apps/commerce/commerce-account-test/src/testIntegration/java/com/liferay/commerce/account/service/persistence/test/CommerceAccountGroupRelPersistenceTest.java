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

package com.liferay.commerce.account.service.persistence.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.commerce.account.exception.NoSuchAccountGroupRelException;
import com.liferay.commerce.account.model.CommerceAccountGroupRel;
import com.liferay.commerce.account.service.CommerceAccountGroupRelLocalServiceUtil;
import com.liferay.commerce.account.service.persistence.CommerceAccountGroupRelPersistence;
import com.liferay.commerce.account.service.persistence.CommerceAccountGroupRelUtil;
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
public class CommerceAccountGroupRelPersistenceTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(), PersistenceTestRule.INSTANCE,
			new TransactionalTestRule(
				Propagation.REQUIRED, "com.liferay.commerce.account.service"));

	@Before
	public void setUp() {
		_persistence = CommerceAccountGroupRelUtil.getPersistence();

		Class<?> clazz = _persistence.getClass();

		_dynamicQueryClassLoader = clazz.getClassLoader();
	}

	@After
	public void tearDown() throws Exception {
		Iterator<CommerceAccountGroupRel> iterator =
			_commerceAccountGroupRels.iterator();

		while (iterator.hasNext()) {
			_persistence.remove(iterator.next());

			iterator.remove();
		}
	}

	@Test
	public void testCreate() throws Exception {
		long pk = RandomTestUtil.nextLong();

		CommerceAccountGroupRel commerceAccountGroupRel = _persistence.create(
			pk);

		Assert.assertNotNull(commerceAccountGroupRel);

		Assert.assertEquals(commerceAccountGroupRel.getPrimaryKey(), pk);
	}

	@Test
	public void testRemove() throws Exception {
		CommerceAccountGroupRel newCommerceAccountGroupRel =
			addCommerceAccountGroupRel();

		_persistence.remove(newCommerceAccountGroupRel);

		CommerceAccountGroupRel existingCommerceAccountGroupRel =
			_persistence.fetchByPrimaryKey(
				newCommerceAccountGroupRel.getPrimaryKey());

		Assert.assertNull(existingCommerceAccountGroupRel);
	}

	@Test
	public void testUpdateNew() throws Exception {
		addCommerceAccountGroupRel();
	}

	@Test
	public void testUpdateExisting() throws Exception {
		long pk = RandomTestUtil.nextLong();

		CommerceAccountGroupRel newCommerceAccountGroupRel =
			_persistence.create(pk);

		newCommerceAccountGroupRel.setCompanyId(RandomTestUtil.nextLong());

		newCommerceAccountGroupRel.setUserId(RandomTestUtil.nextLong());

		newCommerceAccountGroupRel.setUserName(RandomTestUtil.randomString());

		newCommerceAccountGroupRel.setCreateDate(RandomTestUtil.nextDate());

		newCommerceAccountGroupRel.setModifiedDate(RandomTestUtil.nextDate());

		newCommerceAccountGroupRel.setClassNameId(RandomTestUtil.nextLong());

		newCommerceAccountGroupRel.setClassPK(RandomTestUtil.nextLong());

		newCommerceAccountGroupRel.setCommerceAccountGroupId(
			RandomTestUtil.nextLong());

		_commerceAccountGroupRels.add(
			_persistence.update(newCommerceAccountGroupRel));

		CommerceAccountGroupRel existingCommerceAccountGroupRel =
			_persistence.findByPrimaryKey(
				newCommerceAccountGroupRel.getPrimaryKey());

		Assert.assertEquals(
			existingCommerceAccountGroupRel.getCommerceAccountGroupRelId(),
			newCommerceAccountGroupRel.getCommerceAccountGroupRelId());
		Assert.assertEquals(
			existingCommerceAccountGroupRel.getCompanyId(),
			newCommerceAccountGroupRel.getCompanyId());
		Assert.assertEquals(
			existingCommerceAccountGroupRel.getUserId(),
			newCommerceAccountGroupRel.getUserId());
		Assert.assertEquals(
			existingCommerceAccountGroupRel.getUserName(),
			newCommerceAccountGroupRel.getUserName());
		Assert.assertEquals(
			Time.getShortTimestamp(
				existingCommerceAccountGroupRel.getCreateDate()),
			Time.getShortTimestamp(newCommerceAccountGroupRel.getCreateDate()));
		Assert.assertEquals(
			Time.getShortTimestamp(
				existingCommerceAccountGroupRel.getModifiedDate()),
			Time.getShortTimestamp(
				newCommerceAccountGroupRel.getModifiedDate()));
		Assert.assertEquals(
			existingCommerceAccountGroupRel.getClassNameId(),
			newCommerceAccountGroupRel.getClassNameId());
		Assert.assertEquals(
			existingCommerceAccountGroupRel.getClassPK(),
			newCommerceAccountGroupRel.getClassPK());
		Assert.assertEquals(
			existingCommerceAccountGroupRel.getCommerceAccountGroupId(),
			newCommerceAccountGroupRel.getCommerceAccountGroupId());
	}

	@Test
	public void testCountByCommerceAccountGroupId() throws Exception {
		_persistence.countByCommerceAccountGroupId(RandomTestUtil.nextLong());

		_persistence.countByCommerceAccountGroupId(0L);
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
		CommerceAccountGroupRel newCommerceAccountGroupRel =
			addCommerceAccountGroupRel();

		CommerceAccountGroupRel existingCommerceAccountGroupRel =
			_persistence.findByPrimaryKey(
				newCommerceAccountGroupRel.getPrimaryKey());

		Assert.assertEquals(
			existingCommerceAccountGroupRel, newCommerceAccountGroupRel);
	}

	@Test(expected = NoSuchAccountGroupRelException.class)
	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		_persistence.findByPrimaryKey(pk);
	}

	@Test
	public void testFindAll() throws Exception {
		_persistence.findAll(
			QueryUtil.ALL_POS, QueryUtil.ALL_POS, getOrderByComparator());
	}

	protected OrderByComparator<CommerceAccountGroupRel>
		getOrderByComparator() {

		return OrderByComparatorFactoryUtil.create(
			"CommerceAccountGroupRel", "commerceAccountGroupRelId", true,
			"companyId", true, "userId", true, "userName", true, "createDate",
			true, "modifiedDate", true, "classNameId", true, "classPK", true,
			"commerceAccountGroupId", true);
	}

	@Test
	public void testFetchByPrimaryKeyExisting() throws Exception {
		CommerceAccountGroupRel newCommerceAccountGroupRel =
			addCommerceAccountGroupRel();

		CommerceAccountGroupRel existingCommerceAccountGroupRel =
			_persistence.fetchByPrimaryKey(
				newCommerceAccountGroupRel.getPrimaryKey());

		Assert.assertEquals(
			existingCommerceAccountGroupRel, newCommerceAccountGroupRel);
	}

	@Test
	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		CommerceAccountGroupRel missingCommerceAccountGroupRel =
			_persistence.fetchByPrimaryKey(pk);

		Assert.assertNull(missingCommerceAccountGroupRel);
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereAllPrimaryKeysExist()
		throws Exception {

		CommerceAccountGroupRel newCommerceAccountGroupRel1 =
			addCommerceAccountGroupRel();
		CommerceAccountGroupRel newCommerceAccountGroupRel2 =
			addCommerceAccountGroupRel();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newCommerceAccountGroupRel1.getPrimaryKey());
		primaryKeys.add(newCommerceAccountGroupRel2.getPrimaryKey());

		Map<Serializable, CommerceAccountGroupRel> commerceAccountGroupRels =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(2, commerceAccountGroupRels.size());
		Assert.assertEquals(
			newCommerceAccountGroupRel1,
			commerceAccountGroupRels.get(
				newCommerceAccountGroupRel1.getPrimaryKey()));
		Assert.assertEquals(
			newCommerceAccountGroupRel2,
			commerceAccountGroupRels.get(
				newCommerceAccountGroupRel2.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereNoPrimaryKeysExist()
		throws Exception {

		long pk1 = RandomTestUtil.nextLong();

		long pk2 = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(pk1);
		primaryKeys.add(pk2);

		Map<Serializable, CommerceAccountGroupRel> commerceAccountGroupRels =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(commerceAccountGroupRels.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereSomePrimaryKeysExist()
		throws Exception {

		CommerceAccountGroupRel newCommerceAccountGroupRel =
			addCommerceAccountGroupRel();

		long pk = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newCommerceAccountGroupRel.getPrimaryKey());
		primaryKeys.add(pk);

		Map<Serializable, CommerceAccountGroupRel> commerceAccountGroupRels =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, commerceAccountGroupRels.size());
		Assert.assertEquals(
			newCommerceAccountGroupRel,
			commerceAccountGroupRels.get(
				newCommerceAccountGroupRel.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithNoPrimaryKeys() throws Exception {
		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		Map<Serializable, CommerceAccountGroupRel> commerceAccountGroupRels =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(commerceAccountGroupRels.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithOnePrimaryKey() throws Exception {
		CommerceAccountGroupRel newCommerceAccountGroupRel =
			addCommerceAccountGroupRel();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newCommerceAccountGroupRel.getPrimaryKey());

		Map<Serializable, CommerceAccountGroupRel> commerceAccountGroupRels =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, commerceAccountGroupRels.size());
		Assert.assertEquals(
			newCommerceAccountGroupRel,
			commerceAccountGroupRels.get(
				newCommerceAccountGroupRel.getPrimaryKey()));
	}

	@Test
	public void testActionableDynamicQuery() throws Exception {
		final IntegerWrapper count = new IntegerWrapper();

		ActionableDynamicQuery actionableDynamicQuery =
			CommerceAccountGroupRelLocalServiceUtil.getActionableDynamicQuery();

		actionableDynamicQuery.setPerformActionMethod(
			new ActionableDynamicQuery.PerformActionMethod
				<CommerceAccountGroupRel>() {

				@Override
				public void performAction(
					CommerceAccountGroupRel commerceAccountGroupRel) {

					Assert.assertNotNull(commerceAccountGroupRel);

					count.increment();
				}

			});

		actionableDynamicQuery.performActions();

		Assert.assertEquals(count.getValue(), _persistence.countAll());
	}

	@Test
	public void testDynamicQueryByPrimaryKeyExisting() throws Exception {
		CommerceAccountGroupRel newCommerceAccountGroupRel =
			addCommerceAccountGroupRel();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			CommerceAccountGroupRel.class, _dynamicQueryClassLoader);

		dynamicQuery.add(
			RestrictionsFactoryUtil.eq(
				"commerceAccountGroupRelId",
				newCommerceAccountGroupRel.getCommerceAccountGroupRelId()));

		List<CommerceAccountGroupRel> result =
			_persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		CommerceAccountGroupRel existingCommerceAccountGroupRel = result.get(0);

		Assert.assertEquals(
			existingCommerceAccountGroupRel, newCommerceAccountGroupRel);
	}

	@Test
	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			CommerceAccountGroupRel.class, _dynamicQueryClassLoader);

		dynamicQuery.add(
			RestrictionsFactoryUtil.eq(
				"commerceAccountGroupRelId", RandomTestUtil.nextLong()));

		List<CommerceAccountGroupRel> result =
			_persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testDynamicQueryByProjectionExisting() throws Exception {
		CommerceAccountGroupRel newCommerceAccountGroupRel =
			addCommerceAccountGroupRel();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			CommerceAccountGroupRel.class, _dynamicQueryClassLoader);

		dynamicQuery.setProjection(
			ProjectionFactoryUtil.property("commerceAccountGroupRelId"));

		Object newCommerceAccountGroupRelId =
			newCommerceAccountGroupRel.getCommerceAccountGroupRelId();

		dynamicQuery.add(
			RestrictionsFactoryUtil.in(
				"commerceAccountGroupRelId",
				new Object[] {newCommerceAccountGroupRelId}));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		Object existingCommerceAccountGroupRelId = result.get(0);

		Assert.assertEquals(
			existingCommerceAccountGroupRelId, newCommerceAccountGroupRelId);
	}

	@Test
	public void testDynamicQueryByProjectionMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			CommerceAccountGroupRel.class, _dynamicQueryClassLoader);

		dynamicQuery.setProjection(
			ProjectionFactoryUtil.property("commerceAccountGroupRelId"));

		dynamicQuery.add(
			RestrictionsFactoryUtil.in(
				"commerceAccountGroupRelId",
				new Object[] {RandomTestUtil.nextLong()}));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testResetOriginalValues() throws Exception {
		CommerceAccountGroupRel newCommerceAccountGroupRel =
			addCommerceAccountGroupRel();

		_persistence.clearCache();

		CommerceAccountGroupRel existingCommerceAccountGroupRel =
			_persistence.findByPrimaryKey(
				newCommerceAccountGroupRel.getPrimaryKey());

		Assert.assertEquals(
			Long.valueOf(existingCommerceAccountGroupRel.getClassNameId()),
			ReflectionTestUtil.<Long>invoke(
				existingCommerceAccountGroupRel, "getOriginalClassNameId",
				new Class<?>[0]));
		Assert.assertEquals(
			Long.valueOf(existingCommerceAccountGroupRel.getClassPK()),
			ReflectionTestUtil.<Long>invoke(
				existingCommerceAccountGroupRel, "getOriginalClassPK",
				new Class<?>[0]));
		Assert.assertEquals(
			Long.valueOf(
				existingCommerceAccountGroupRel.getCommerceAccountGroupId()),
			ReflectionTestUtil.<Long>invoke(
				existingCommerceAccountGroupRel,
				"getOriginalCommerceAccountGroupId", new Class<?>[0]));
	}

	protected CommerceAccountGroupRel addCommerceAccountGroupRel()
		throws Exception {

		long pk = RandomTestUtil.nextLong();

		CommerceAccountGroupRel commerceAccountGroupRel = _persistence.create(
			pk);

		commerceAccountGroupRel.setCompanyId(RandomTestUtil.nextLong());

		commerceAccountGroupRel.setUserId(RandomTestUtil.nextLong());

		commerceAccountGroupRel.setUserName(RandomTestUtil.randomString());

		commerceAccountGroupRel.setCreateDate(RandomTestUtil.nextDate());

		commerceAccountGroupRel.setModifiedDate(RandomTestUtil.nextDate());

		commerceAccountGroupRel.setClassNameId(RandomTestUtil.nextLong());

		commerceAccountGroupRel.setClassPK(RandomTestUtil.nextLong());

		commerceAccountGroupRel.setCommerceAccountGroupId(
			RandomTestUtil.nextLong());

		_commerceAccountGroupRels.add(
			_persistence.update(commerceAccountGroupRel));

		return commerceAccountGroupRel;
	}

	private List<CommerceAccountGroupRel> _commerceAccountGroupRels =
		new ArrayList<CommerceAccountGroupRel>();
	private CommerceAccountGroupRelPersistence _persistence;
	private ClassLoader _dynamicQueryClassLoader;

}