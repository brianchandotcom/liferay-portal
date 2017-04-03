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

package com.liferay.commerce.product.service.persistence.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;

import com.liferay.commerce.product.exception.NoSuchProductDefintionOptionValueRelException;
import com.liferay.commerce.product.model.CommerceProductDefintionOptionValueRel;
import com.liferay.commerce.product.service.CommerceProductDefintionOptionValueRelLocalServiceUtil;
import com.liferay.commerce.product.service.persistence.CommerceProductDefintionOptionValueRelPersistence;
import com.liferay.commerce.product.service.persistence.CommerceProductDefintionOptionValueRelUtil;

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
public class CommerceProductDefintionOptionValueRelPersistenceTest {
	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule = new AggregateTestRule(new LiferayIntegrationTestRule(),
			PersistenceTestRule.INSTANCE,
			new TransactionalTestRule(Propagation.REQUIRED,
				"com.liferay.commerce.product.service"));

	@Before
	public void setUp() {
		_persistence = CommerceProductDefintionOptionValueRelUtil.getPersistence();

		Class<?> clazz = _persistence.getClass();

		_dynamicQueryClassLoader = clazz.getClassLoader();
	}

	@After
	public void tearDown() throws Exception {
		Iterator<CommerceProductDefintionOptionValueRel> iterator = _commerceProductDefintionOptionValueRels.iterator();

		while (iterator.hasNext()) {
			_persistence.remove(iterator.next());

			iterator.remove();
		}
	}

	@Test
	public void testCreate() throws Exception {
		long pk = RandomTestUtil.nextLong();

		CommerceProductDefintionOptionValueRel commerceProductDefintionOptionValueRel =
			_persistence.create(pk);

		Assert.assertNotNull(commerceProductDefintionOptionValueRel);

		Assert.assertEquals(commerceProductDefintionOptionValueRel.getPrimaryKey(),
			pk);
	}

	@Test
	public void testRemove() throws Exception {
		CommerceProductDefintionOptionValueRel newCommerceProductDefintionOptionValueRel =
			addCommerceProductDefintionOptionValueRel();

		_persistence.remove(newCommerceProductDefintionOptionValueRel);

		CommerceProductDefintionOptionValueRel existingCommerceProductDefintionOptionValueRel =
			_persistence.fetchByPrimaryKey(newCommerceProductDefintionOptionValueRel.getPrimaryKey());

		Assert.assertNull(existingCommerceProductDefintionOptionValueRel);
	}

	@Test
	public void testUpdateNew() throws Exception {
		addCommerceProductDefintionOptionValueRel();
	}

	@Test
	public void testUpdateExisting() throws Exception {
		long pk = RandomTestUtil.nextLong();

		CommerceProductDefintionOptionValueRel newCommerceProductDefintionOptionValueRel =
			_persistence.create(pk);

		newCommerceProductDefintionOptionValueRel.setGroupId(RandomTestUtil.nextLong());

		newCommerceProductDefintionOptionValueRel.setCompanyId(RandomTestUtil.nextLong());

		newCommerceProductDefintionOptionValueRel.setUserId(RandomTestUtil.nextLong());

		newCommerceProductDefintionOptionValueRel.setUserName(RandomTestUtil.randomString());

		newCommerceProductDefintionOptionValueRel.setCreateDate(RandomTestUtil.nextDate());

		newCommerceProductDefintionOptionValueRel.setModifiedDate(RandomTestUtil.nextDate());

		newCommerceProductDefintionOptionValueRel.setCommerceProductDefintionOptionRelId(RandomTestUtil.nextLong());

		newCommerceProductDefintionOptionValueRel.setTitle(RandomTestUtil.randomString());

		newCommerceProductDefintionOptionValueRel.setPriority(RandomTestUtil.nextLong());

		_commerceProductDefintionOptionValueRels.add(_persistence.update(
				newCommerceProductDefintionOptionValueRel));

		CommerceProductDefintionOptionValueRel existingCommerceProductDefintionOptionValueRel =
			_persistence.findByPrimaryKey(newCommerceProductDefintionOptionValueRel.getPrimaryKey());

		Assert.assertEquals(existingCommerceProductDefintionOptionValueRel.getCommerceProductDefintionOptionValueRelId(),
			newCommerceProductDefintionOptionValueRel.getCommerceProductDefintionOptionValueRelId());
		Assert.assertEquals(existingCommerceProductDefintionOptionValueRel.getGroupId(),
			newCommerceProductDefintionOptionValueRel.getGroupId());
		Assert.assertEquals(existingCommerceProductDefintionOptionValueRel.getCompanyId(),
			newCommerceProductDefintionOptionValueRel.getCompanyId());
		Assert.assertEquals(existingCommerceProductDefintionOptionValueRel.getUserId(),
			newCommerceProductDefintionOptionValueRel.getUserId());
		Assert.assertEquals(existingCommerceProductDefintionOptionValueRel.getUserName(),
			newCommerceProductDefintionOptionValueRel.getUserName());
		Assert.assertEquals(Time.getShortTimestamp(
				existingCommerceProductDefintionOptionValueRel.getCreateDate()),
			Time.getShortTimestamp(
				newCommerceProductDefintionOptionValueRel.getCreateDate()));
		Assert.assertEquals(Time.getShortTimestamp(
				existingCommerceProductDefintionOptionValueRel.getModifiedDate()),
			Time.getShortTimestamp(
				newCommerceProductDefintionOptionValueRel.getModifiedDate()));
		Assert.assertEquals(existingCommerceProductDefintionOptionValueRel.getCommerceProductDefintionOptionRelId(),
			newCommerceProductDefintionOptionValueRel.getCommerceProductDefintionOptionRelId());
		Assert.assertEquals(existingCommerceProductDefintionOptionValueRel.getTitle(),
			newCommerceProductDefintionOptionValueRel.getTitle());
		Assert.assertEquals(existingCommerceProductDefintionOptionValueRel.getPriority(),
			newCommerceProductDefintionOptionValueRel.getPriority());
	}

	@Test
	public void testCountByGroupId() throws Exception {
		_persistence.countByGroupId(RandomTestUtil.nextLong());

		_persistence.countByGroupId(0L);
	}

	@Test
	public void testCountByCompanyId() throws Exception {
		_persistence.countByCompanyId(RandomTestUtil.nextLong());

		_persistence.countByCompanyId(0L);
	}

	@Test
	public void testFindByPrimaryKeyExisting() throws Exception {
		CommerceProductDefintionOptionValueRel newCommerceProductDefintionOptionValueRel =
			addCommerceProductDefintionOptionValueRel();

		CommerceProductDefintionOptionValueRel existingCommerceProductDefintionOptionValueRel =
			_persistence.findByPrimaryKey(newCommerceProductDefintionOptionValueRel.getPrimaryKey());

		Assert.assertEquals(existingCommerceProductDefintionOptionValueRel,
			newCommerceProductDefintionOptionValueRel);
	}

	@Test(expected = NoSuchProductDefintionOptionValueRelException.class)
	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		_persistence.findByPrimaryKey(pk);
	}

	@Test
	public void testFindAll() throws Exception {
		_persistence.findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			getOrderByComparator());
	}

	protected OrderByComparator<CommerceProductDefintionOptionValueRel> getOrderByComparator() {
		return OrderByComparatorFactoryUtil.create("CommerceProductDefintionOptionValueRel",
			"commerceProductDefintionOptionValueRelId", true, "groupId", true,
			"companyId", true, "userId", true, "userName", true, "createDate",
			true, "modifiedDate", true, "commerceProductDefintionOptionRelId",
			true, "title", true, "priority", true);
	}

	@Test
	public void testFetchByPrimaryKeyExisting() throws Exception {
		CommerceProductDefintionOptionValueRel newCommerceProductDefintionOptionValueRel =
			addCommerceProductDefintionOptionValueRel();

		CommerceProductDefintionOptionValueRel existingCommerceProductDefintionOptionValueRel =
			_persistence.fetchByPrimaryKey(newCommerceProductDefintionOptionValueRel.getPrimaryKey());

		Assert.assertEquals(existingCommerceProductDefintionOptionValueRel,
			newCommerceProductDefintionOptionValueRel);
	}

	@Test
	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		CommerceProductDefintionOptionValueRel missingCommerceProductDefintionOptionValueRel =
			_persistence.fetchByPrimaryKey(pk);

		Assert.assertNull(missingCommerceProductDefintionOptionValueRel);
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereAllPrimaryKeysExist()
		throws Exception {
		CommerceProductDefintionOptionValueRel newCommerceProductDefintionOptionValueRel1 =
			addCommerceProductDefintionOptionValueRel();
		CommerceProductDefintionOptionValueRel newCommerceProductDefintionOptionValueRel2 =
			addCommerceProductDefintionOptionValueRel();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newCommerceProductDefintionOptionValueRel1.getPrimaryKey());
		primaryKeys.add(newCommerceProductDefintionOptionValueRel2.getPrimaryKey());

		Map<Serializable, CommerceProductDefintionOptionValueRel> commerceProductDefintionOptionValueRels =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(2, commerceProductDefintionOptionValueRels.size());
		Assert.assertEquals(newCommerceProductDefintionOptionValueRel1,
			commerceProductDefintionOptionValueRels.get(
				newCommerceProductDefintionOptionValueRel1.getPrimaryKey()));
		Assert.assertEquals(newCommerceProductDefintionOptionValueRel2,
			commerceProductDefintionOptionValueRels.get(
				newCommerceProductDefintionOptionValueRel2.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereNoPrimaryKeysExist()
		throws Exception {
		long pk1 = RandomTestUtil.nextLong();

		long pk2 = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(pk1);
		primaryKeys.add(pk2);

		Map<Serializable, CommerceProductDefintionOptionValueRel> commerceProductDefintionOptionValueRels =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(commerceProductDefintionOptionValueRels.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereSomePrimaryKeysExist()
		throws Exception {
		CommerceProductDefintionOptionValueRel newCommerceProductDefintionOptionValueRel =
			addCommerceProductDefintionOptionValueRel();

		long pk = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newCommerceProductDefintionOptionValueRel.getPrimaryKey());
		primaryKeys.add(pk);

		Map<Serializable, CommerceProductDefintionOptionValueRel> commerceProductDefintionOptionValueRels =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, commerceProductDefintionOptionValueRels.size());
		Assert.assertEquals(newCommerceProductDefintionOptionValueRel,
			commerceProductDefintionOptionValueRels.get(
				newCommerceProductDefintionOptionValueRel.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithNoPrimaryKeys()
		throws Exception {
		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		Map<Serializable, CommerceProductDefintionOptionValueRel> commerceProductDefintionOptionValueRels =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(commerceProductDefintionOptionValueRels.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithOnePrimaryKey()
		throws Exception {
		CommerceProductDefintionOptionValueRel newCommerceProductDefintionOptionValueRel =
			addCommerceProductDefintionOptionValueRel();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newCommerceProductDefintionOptionValueRel.getPrimaryKey());

		Map<Serializable, CommerceProductDefintionOptionValueRel> commerceProductDefintionOptionValueRels =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, commerceProductDefintionOptionValueRels.size());
		Assert.assertEquals(newCommerceProductDefintionOptionValueRel,
			commerceProductDefintionOptionValueRels.get(
				newCommerceProductDefintionOptionValueRel.getPrimaryKey()));
	}

	@Test
	public void testActionableDynamicQuery() throws Exception {
		final IntegerWrapper count = new IntegerWrapper();

		ActionableDynamicQuery actionableDynamicQuery = CommerceProductDefintionOptionValueRelLocalServiceUtil.getActionableDynamicQuery();

		actionableDynamicQuery.setPerformActionMethod(new ActionableDynamicQuery.PerformActionMethod<CommerceProductDefintionOptionValueRel>() {
				@Override
				public void performAction(
					CommerceProductDefintionOptionValueRel commerceProductDefintionOptionValueRel) {
					Assert.assertNotNull(commerceProductDefintionOptionValueRel);

					count.increment();
				}
			});

		actionableDynamicQuery.performActions();

		Assert.assertEquals(count.getValue(), _persistence.countAll());
	}

	@Test
	public void testDynamicQueryByPrimaryKeyExisting()
		throws Exception {
		CommerceProductDefintionOptionValueRel newCommerceProductDefintionOptionValueRel =
			addCommerceProductDefintionOptionValueRel();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(CommerceProductDefintionOptionValueRel.class,
				_dynamicQueryClassLoader);

		dynamicQuery.add(RestrictionsFactoryUtil.eq(
				"commerceProductDefintionOptionValueRelId",
				newCommerceProductDefintionOptionValueRel.getCommerceProductDefintionOptionValueRelId()));

		List<CommerceProductDefintionOptionValueRel> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		CommerceProductDefintionOptionValueRel existingCommerceProductDefintionOptionValueRel =
			result.get(0);

		Assert.assertEquals(existingCommerceProductDefintionOptionValueRel,
			newCommerceProductDefintionOptionValueRel);
	}

	@Test
	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(CommerceProductDefintionOptionValueRel.class,
				_dynamicQueryClassLoader);

		dynamicQuery.add(RestrictionsFactoryUtil.eq(
				"commerceProductDefintionOptionValueRelId",
				RandomTestUtil.nextLong()));

		List<CommerceProductDefintionOptionValueRel> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testDynamicQueryByProjectionExisting()
		throws Exception {
		CommerceProductDefintionOptionValueRel newCommerceProductDefintionOptionValueRel =
			addCommerceProductDefintionOptionValueRel();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(CommerceProductDefintionOptionValueRel.class,
				_dynamicQueryClassLoader);

		dynamicQuery.setProjection(ProjectionFactoryUtil.property(
				"commerceProductDefintionOptionValueRelId"));

		Object newCommerceProductDefintionOptionValueRelId = newCommerceProductDefintionOptionValueRel.getCommerceProductDefintionOptionValueRelId();

		dynamicQuery.add(RestrictionsFactoryUtil.in(
				"commerceProductDefintionOptionValueRelId",
				new Object[] { newCommerceProductDefintionOptionValueRelId }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		Object existingCommerceProductDefintionOptionValueRelId = result.get(0);

		Assert.assertEquals(existingCommerceProductDefintionOptionValueRelId,
			newCommerceProductDefintionOptionValueRelId);
	}

	@Test
	public void testDynamicQueryByProjectionMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(CommerceProductDefintionOptionValueRel.class,
				_dynamicQueryClassLoader);

		dynamicQuery.setProjection(ProjectionFactoryUtil.property(
				"commerceProductDefintionOptionValueRelId"));

		dynamicQuery.add(RestrictionsFactoryUtil.in(
				"commerceProductDefintionOptionValueRelId",
				new Object[] { RandomTestUtil.nextLong() }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	protected CommerceProductDefintionOptionValueRel addCommerceProductDefintionOptionValueRel()
		throws Exception {
		long pk = RandomTestUtil.nextLong();

		CommerceProductDefintionOptionValueRel commerceProductDefintionOptionValueRel =
			_persistence.create(pk);

		commerceProductDefintionOptionValueRel.setGroupId(RandomTestUtil.nextLong());

		commerceProductDefintionOptionValueRel.setCompanyId(RandomTestUtil.nextLong());

		commerceProductDefintionOptionValueRel.setUserId(RandomTestUtil.nextLong());

		commerceProductDefintionOptionValueRel.setUserName(RandomTestUtil.randomString());

		commerceProductDefintionOptionValueRel.setCreateDate(RandomTestUtil.nextDate());

		commerceProductDefintionOptionValueRel.setModifiedDate(RandomTestUtil.nextDate());

		commerceProductDefintionOptionValueRel.setCommerceProductDefintionOptionRelId(RandomTestUtil.nextLong());

		commerceProductDefintionOptionValueRel.setTitle(RandomTestUtil.randomString());

		commerceProductDefintionOptionValueRel.setPriority(RandomTestUtil.nextLong());

		_commerceProductDefintionOptionValueRels.add(_persistence.update(
				commerceProductDefintionOptionValueRel));

		return commerceProductDefintionOptionValueRel;
	}

	private List<CommerceProductDefintionOptionValueRel> _commerceProductDefintionOptionValueRels =
		new ArrayList<CommerceProductDefintionOptionValueRel>();
	private CommerceProductDefintionOptionValueRelPersistence _persistence;
	private ClassLoader _dynamicQueryClassLoader;
}