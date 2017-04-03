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

import com.liferay.commerce.product.exception.NoSuchProductDefintionOptionRelException;
import com.liferay.commerce.product.model.CommerceProductDefintionOptionRel;
import com.liferay.commerce.product.service.CommerceProductDefintionOptionRelLocalServiceUtil;
import com.liferay.commerce.product.service.persistence.CommerceProductDefintionOptionRelPersistence;
import com.liferay.commerce.product.service.persistence.CommerceProductDefintionOptionRelUtil;

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
public class CommerceProductDefintionOptionRelPersistenceTest {
	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule = new AggregateTestRule(new LiferayIntegrationTestRule(),
			PersistenceTestRule.INSTANCE,
			new TransactionalTestRule(Propagation.REQUIRED,
				"com.liferay.commerce.product.service"));

	@Before
	public void setUp() {
		_persistence = CommerceProductDefintionOptionRelUtil.getPersistence();

		Class<?> clazz = _persistence.getClass();

		_dynamicQueryClassLoader = clazz.getClassLoader();
	}

	@After
	public void tearDown() throws Exception {
		Iterator<CommerceProductDefintionOptionRel> iterator = _commerceProductDefintionOptionRels.iterator();

		while (iterator.hasNext()) {
			_persistence.remove(iterator.next());

			iterator.remove();
		}
	}

	@Test
	public void testCreate() throws Exception {
		long pk = RandomTestUtil.nextLong();

		CommerceProductDefintionOptionRel commerceProductDefintionOptionRel = _persistence.create(pk);

		Assert.assertNotNull(commerceProductDefintionOptionRel);

		Assert.assertEquals(commerceProductDefintionOptionRel.getPrimaryKey(),
			pk);
	}

	@Test
	public void testRemove() throws Exception {
		CommerceProductDefintionOptionRel newCommerceProductDefintionOptionRel = addCommerceProductDefintionOptionRel();

		_persistence.remove(newCommerceProductDefintionOptionRel);

		CommerceProductDefintionOptionRel existingCommerceProductDefintionOptionRel =
			_persistence.fetchByPrimaryKey(newCommerceProductDefintionOptionRel.getPrimaryKey());

		Assert.assertNull(existingCommerceProductDefintionOptionRel);
	}

	@Test
	public void testUpdateNew() throws Exception {
		addCommerceProductDefintionOptionRel();
	}

	@Test
	public void testUpdateExisting() throws Exception {
		long pk = RandomTestUtil.nextLong();

		CommerceProductDefintionOptionRel newCommerceProductDefintionOptionRel = _persistence.create(pk);

		newCommerceProductDefintionOptionRel.setGroupId(RandomTestUtil.nextLong());

		newCommerceProductDefintionOptionRel.setCompanyId(RandomTestUtil.nextLong());

		newCommerceProductDefintionOptionRel.setUserId(RandomTestUtil.nextLong());

		newCommerceProductDefintionOptionRel.setUserName(RandomTestUtil.randomString());

		newCommerceProductDefintionOptionRel.setCreateDate(RandomTestUtil.nextDate());

		newCommerceProductDefintionOptionRel.setModifiedDate(RandomTestUtil.nextDate());

		newCommerceProductDefintionOptionRel.setCommerceProductOptionId(RandomTestUtil.nextLong());

		newCommerceProductDefintionOptionRel.setCommerceProductDefinitionId(RandomTestUtil.nextLong());

		newCommerceProductDefintionOptionRel.setName(RandomTestUtil.randomString());

		newCommerceProductDefintionOptionRel.setDescription(RandomTestUtil.randomString());

		newCommerceProductDefintionOptionRel.setDDMFormFieldTypeName(RandomTestUtil.randomString());

		newCommerceProductDefintionOptionRel.setPriority(RandomTestUtil.randomString());

		_commerceProductDefintionOptionRels.add(_persistence.update(
				newCommerceProductDefintionOptionRel));

		CommerceProductDefintionOptionRel existingCommerceProductDefintionOptionRel =
			_persistence.findByPrimaryKey(newCommerceProductDefintionOptionRel.getPrimaryKey());

		Assert.assertEquals(existingCommerceProductDefintionOptionRel.getCommerceProductDefintionOptionRelId(),
			newCommerceProductDefintionOptionRel.getCommerceProductDefintionOptionRelId());
		Assert.assertEquals(existingCommerceProductDefintionOptionRel.getGroupId(),
			newCommerceProductDefintionOptionRel.getGroupId());
		Assert.assertEquals(existingCommerceProductDefintionOptionRel.getCompanyId(),
			newCommerceProductDefintionOptionRel.getCompanyId());
		Assert.assertEquals(existingCommerceProductDefintionOptionRel.getUserId(),
			newCommerceProductDefintionOptionRel.getUserId());
		Assert.assertEquals(existingCommerceProductDefintionOptionRel.getUserName(),
			newCommerceProductDefintionOptionRel.getUserName());
		Assert.assertEquals(Time.getShortTimestamp(
				existingCommerceProductDefintionOptionRel.getCreateDate()),
			Time.getShortTimestamp(
				newCommerceProductDefintionOptionRel.getCreateDate()));
		Assert.assertEquals(Time.getShortTimestamp(
				existingCommerceProductDefintionOptionRel.getModifiedDate()),
			Time.getShortTimestamp(
				newCommerceProductDefintionOptionRel.getModifiedDate()));
		Assert.assertEquals(existingCommerceProductDefintionOptionRel.getCommerceProductOptionId(),
			newCommerceProductDefintionOptionRel.getCommerceProductOptionId());
		Assert.assertEquals(existingCommerceProductDefintionOptionRel.getCommerceProductDefinitionId(),
			newCommerceProductDefintionOptionRel.getCommerceProductDefinitionId());
		Assert.assertEquals(existingCommerceProductDefintionOptionRel.getName(),
			newCommerceProductDefintionOptionRel.getName());
		Assert.assertEquals(existingCommerceProductDefintionOptionRel.getDescription(),
			newCommerceProductDefintionOptionRel.getDescription());
		Assert.assertEquals(existingCommerceProductDefintionOptionRel.getDDMFormFieldTypeName(),
			newCommerceProductDefintionOptionRel.getDDMFormFieldTypeName());
		Assert.assertEquals(existingCommerceProductDefintionOptionRel.getPriority(),
			newCommerceProductDefintionOptionRel.getPriority());
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
		CommerceProductDefintionOptionRel newCommerceProductDefintionOptionRel = addCommerceProductDefintionOptionRel();

		CommerceProductDefintionOptionRel existingCommerceProductDefintionOptionRel =
			_persistence.findByPrimaryKey(newCommerceProductDefintionOptionRel.getPrimaryKey());

		Assert.assertEquals(existingCommerceProductDefintionOptionRel,
			newCommerceProductDefintionOptionRel);
	}

	@Test(expected = NoSuchProductDefintionOptionRelException.class)
	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		_persistence.findByPrimaryKey(pk);
	}

	@Test
	public void testFindAll() throws Exception {
		_persistence.findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			getOrderByComparator());
	}

	protected OrderByComparator<CommerceProductDefintionOptionRel> getOrderByComparator() {
		return OrderByComparatorFactoryUtil.create("CommerceProductDefintionOptionRel",
			"commerceProductDefintionOptionRelId", true, "groupId", true,
			"companyId", true, "userId", true, "userName", true, "createDate",
			true, "modifiedDate", true, "commerceProductOptionId", true,
			"commerceProductDefinitionId", true, "name", true, "description",
			true, "DDMFormFieldTypeName", true, "priority", true);
	}

	@Test
	public void testFetchByPrimaryKeyExisting() throws Exception {
		CommerceProductDefintionOptionRel newCommerceProductDefintionOptionRel = addCommerceProductDefintionOptionRel();

		CommerceProductDefintionOptionRel existingCommerceProductDefintionOptionRel =
			_persistence.fetchByPrimaryKey(newCommerceProductDefintionOptionRel.getPrimaryKey());

		Assert.assertEquals(existingCommerceProductDefintionOptionRel,
			newCommerceProductDefintionOptionRel);
	}

	@Test
	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		CommerceProductDefintionOptionRel missingCommerceProductDefintionOptionRel =
			_persistence.fetchByPrimaryKey(pk);

		Assert.assertNull(missingCommerceProductDefintionOptionRel);
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereAllPrimaryKeysExist()
		throws Exception {
		CommerceProductDefintionOptionRel newCommerceProductDefintionOptionRel1 = addCommerceProductDefintionOptionRel();
		CommerceProductDefintionOptionRel newCommerceProductDefintionOptionRel2 = addCommerceProductDefintionOptionRel();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newCommerceProductDefintionOptionRel1.getPrimaryKey());
		primaryKeys.add(newCommerceProductDefintionOptionRel2.getPrimaryKey());

		Map<Serializable, CommerceProductDefintionOptionRel> commerceProductDefintionOptionRels =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(2, commerceProductDefintionOptionRels.size());
		Assert.assertEquals(newCommerceProductDefintionOptionRel1,
			commerceProductDefintionOptionRels.get(
				newCommerceProductDefintionOptionRel1.getPrimaryKey()));
		Assert.assertEquals(newCommerceProductDefintionOptionRel2,
			commerceProductDefintionOptionRels.get(
				newCommerceProductDefintionOptionRel2.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereNoPrimaryKeysExist()
		throws Exception {
		long pk1 = RandomTestUtil.nextLong();

		long pk2 = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(pk1);
		primaryKeys.add(pk2);

		Map<Serializable, CommerceProductDefintionOptionRel> commerceProductDefintionOptionRels =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(commerceProductDefintionOptionRels.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereSomePrimaryKeysExist()
		throws Exception {
		CommerceProductDefintionOptionRel newCommerceProductDefintionOptionRel = addCommerceProductDefintionOptionRel();

		long pk = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newCommerceProductDefintionOptionRel.getPrimaryKey());
		primaryKeys.add(pk);

		Map<Serializable, CommerceProductDefintionOptionRel> commerceProductDefintionOptionRels =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, commerceProductDefintionOptionRels.size());
		Assert.assertEquals(newCommerceProductDefintionOptionRel,
			commerceProductDefintionOptionRels.get(
				newCommerceProductDefintionOptionRel.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithNoPrimaryKeys()
		throws Exception {
		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		Map<Serializable, CommerceProductDefintionOptionRel> commerceProductDefintionOptionRels =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(commerceProductDefintionOptionRels.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithOnePrimaryKey()
		throws Exception {
		CommerceProductDefintionOptionRel newCommerceProductDefintionOptionRel = addCommerceProductDefintionOptionRel();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newCommerceProductDefintionOptionRel.getPrimaryKey());

		Map<Serializable, CommerceProductDefintionOptionRel> commerceProductDefintionOptionRels =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, commerceProductDefintionOptionRels.size());
		Assert.assertEquals(newCommerceProductDefintionOptionRel,
			commerceProductDefintionOptionRels.get(
				newCommerceProductDefintionOptionRel.getPrimaryKey()));
	}

	@Test
	public void testActionableDynamicQuery() throws Exception {
		final IntegerWrapper count = new IntegerWrapper();

		ActionableDynamicQuery actionableDynamicQuery = CommerceProductDefintionOptionRelLocalServiceUtil.getActionableDynamicQuery();

		actionableDynamicQuery.setPerformActionMethod(new ActionableDynamicQuery.PerformActionMethod<CommerceProductDefintionOptionRel>() {
				@Override
				public void performAction(
					CommerceProductDefintionOptionRel commerceProductDefintionOptionRel) {
					Assert.assertNotNull(commerceProductDefintionOptionRel);

					count.increment();
				}
			});

		actionableDynamicQuery.performActions();

		Assert.assertEquals(count.getValue(), _persistence.countAll());
	}

	@Test
	public void testDynamicQueryByPrimaryKeyExisting()
		throws Exception {
		CommerceProductDefintionOptionRel newCommerceProductDefintionOptionRel = addCommerceProductDefintionOptionRel();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(CommerceProductDefintionOptionRel.class,
				_dynamicQueryClassLoader);

		dynamicQuery.add(RestrictionsFactoryUtil.eq(
				"commerceProductDefintionOptionRelId",
				newCommerceProductDefintionOptionRel.getCommerceProductDefintionOptionRelId()));

		List<CommerceProductDefintionOptionRel> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		CommerceProductDefintionOptionRel existingCommerceProductDefintionOptionRel =
			result.get(0);

		Assert.assertEquals(existingCommerceProductDefintionOptionRel,
			newCommerceProductDefintionOptionRel);
	}

	@Test
	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(CommerceProductDefintionOptionRel.class,
				_dynamicQueryClassLoader);

		dynamicQuery.add(RestrictionsFactoryUtil.eq(
				"commerceProductDefintionOptionRelId", RandomTestUtil.nextLong()));

		List<CommerceProductDefintionOptionRel> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testDynamicQueryByProjectionExisting()
		throws Exception {
		CommerceProductDefintionOptionRel newCommerceProductDefintionOptionRel = addCommerceProductDefintionOptionRel();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(CommerceProductDefintionOptionRel.class,
				_dynamicQueryClassLoader);

		dynamicQuery.setProjection(ProjectionFactoryUtil.property(
				"commerceProductDefintionOptionRelId"));

		Object newCommerceProductDefintionOptionRelId = newCommerceProductDefintionOptionRel.getCommerceProductDefintionOptionRelId();

		dynamicQuery.add(RestrictionsFactoryUtil.in(
				"commerceProductDefintionOptionRelId",
				new Object[] { newCommerceProductDefintionOptionRelId }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		Object existingCommerceProductDefintionOptionRelId = result.get(0);

		Assert.assertEquals(existingCommerceProductDefintionOptionRelId,
			newCommerceProductDefintionOptionRelId);
	}

	@Test
	public void testDynamicQueryByProjectionMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(CommerceProductDefintionOptionRel.class,
				_dynamicQueryClassLoader);

		dynamicQuery.setProjection(ProjectionFactoryUtil.property(
				"commerceProductDefintionOptionRelId"));

		dynamicQuery.add(RestrictionsFactoryUtil.in(
				"commerceProductDefintionOptionRelId",
				new Object[] { RandomTestUtil.nextLong() }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	protected CommerceProductDefintionOptionRel addCommerceProductDefintionOptionRel()
		throws Exception {
		long pk = RandomTestUtil.nextLong();

		CommerceProductDefintionOptionRel commerceProductDefintionOptionRel = _persistence.create(pk);

		commerceProductDefintionOptionRel.setGroupId(RandomTestUtil.nextLong());

		commerceProductDefintionOptionRel.setCompanyId(RandomTestUtil.nextLong());

		commerceProductDefintionOptionRel.setUserId(RandomTestUtil.nextLong());

		commerceProductDefintionOptionRel.setUserName(RandomTestUtil.randomString());

		commerceProductDefintionOptionRel.setCreateDate(RandomTestUtil.nextDate());

		commerceProductDefintionOptionRel.setModifiedDate(RandomTestUtil.nextDate());

		commerceProductDefintionOptionRel.setCommerceProductOptionId(RandomTestUtil.nextLong());

		commerceProductDefintionOptionRel.setCommerceProductDefinitionId(RandomTestUtil.nextLong());

		commerceProductDefintionOptionRel.setName(RandomTestUtil.randomString());

		commerceProductDefintionOptionRel.setDescription(RandomTestUtil.randomString());

		commerceProductDefintionOptionRel.setDDMFormFieldTypeName(RandomTestUtil.randomString());

		commerceProductDefintionOptionRel.setPriority(RandomTestUtil.randomString());

		_commerceProductDefintionOptionRels.add(_persistence.update(
				commerceProductDefintionOptionRel));

		return commerceProductDefintionOptionRel;
	}

	private List<CommerceProductDefintionOptionRel> _commerceProductDefintionOptionRels =
		new ArrayList<CommerceProductDefintionOptionRel>();
	private CommerceProductDefintionOptionRelPersistence _persistence;
	private ClassLoader _dynamicQueryClassLoader;
}