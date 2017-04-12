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

import com.liferay.commerce.product.exception.NoSuchProductDefinitionOptionRelException;
import com.liferay.commerce.product.model.CommerceProductDefinitionOptionRel;
import com.liferay.commerce.product.service.CommerceProductDefinitionOptionRelLocalServiceUtil;
import com.liferay.commerce.product.service.persistence.CommerceProductDefinitionOptionRelPersistence;
import com.liferay.commerce.product.service.persistence.CommerceProductDefinitionOptionRelUtil;

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
public class CommerceProductDefinitionOptionRelPersistenceTest {
	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule = new AggregateTestRule(new LiferayIntegrationTestRule(),
			PersistenceTestRule.INSTANCE,
			new TransactionalTestRule(Propagation.REQUIRED,
				"com.liferay.commerce.product.service"));

	@Before
	public void setUp() {
		_persistence = CommerceProductDefinitionOptionRelUtil.getPersistence();

		Class<?> clazz = _persistence.getClass();

		_dynamicQueryClassLoader = clazz.getClassLoader();
	}

	@After
	public void tearDown() throws Exception {
		Iterator<CommerceProductDefinitionOptionRel> iterator = _commerceProductDefinitionOptionRels.iterator();

		while (iterator.hasNext()) {
			_persistence.remove(iterator.next());

			iterator.remove();
		}
	}

	@Test
	public void testCreate() throws Exception {
		long pk = RandomTestUtil.nextLong();

		CommerceProductDefinitionOptionRel commerceProductDefinitionOptionRel = _persistence.create(pk);

		Assert.assertNotNull(commerceProductDefinitionOptionRel);

		Assert.assertEquals(commerceProductDefinitionOptionRel.getPrimaryKey(),
			pk);
	}

	@Test
	public void testRemove() throws Exception {
		CommerceProductDefinitionOptionRel newCommerceProductDefinitionOptionRel =
			addCommerceProductDefinitionOptionRel();

		_persistence.remove(newCommerceProductDefinitionOptionRel);

		CommerceProductDefinitionOptionRel existingCommerceProductDefinitionOptionRel =
			_persistence.fetchByPrimaryKey(newCommerceProductDefinitionOptionRel.getPrimaryKey());

		Assert.assertNull(existingCommerceProductDefinitionOptionRel);
	}

	@Test
	public void testUpdateNew() throws Exception {
		addCommerceProductDefinitionOptionRel();
	}

	@Test
	public void testUpdateExisting() throws Exception {
		long pk = RandomTestUtil.nextLong();

		CommerceProductDefinitionOptionRel newCommerceProductDefinitionOptionRel =
			_persistence.create(pk);

		newCommerceProductDefinitionOptionRel.setGroupId(RandomTestUtil.nextLong());

		newCommerceProductDefinitionOptionRel.setCompanyId(RandomTestUtil.nextLong());

		newCommerceProductDefinitionOptionRel.setUserId(RandomTestUtil.nextLong());

		newCommerceProductDefinitionOptionRel.setUserName(RandomTestUtil.randomString());

		newCommerceProductDefinitionOptionRel.setCreateDate(RandomTestUtil.nextDate());

		newCommerceProductDefinitionOptionRel.setModifiedDate(RandomTestUtil.nextDate());

		newCommerceProductDefinitionOptionRel.setCommerceProductDefinitionId(RandomTestUtil.nextLong());

		newCommerceProductDefinitionOptionRel.setCommerceProductOptionId(RandomTestUtil.nextLong());

		newCommerceProductDefinitionOptionRel.setName(RandomTestUtil.randomString());

		newCommerceProductDefinitionOptionRel.setDescription(RandomTestUtil.randomString());

		newCommerceProductDefinitionOptionRel.setDDMFormFieldTypeName(RandomTestUtil.randomString());

		newCommerceProductDefinitionOptionRel.setPriority(RandomTestUtil.nextInt());

		_commerceProductDefinitionOptionRels.add(_persistence.update(
				newCommerceProductDefinitionOptionRel));

		CommerceProductDefinitionOptionRel existingCommerceProductDefinitionOptionRel =
			_persistence.findByPrimaryKey(newCommerceProductDefinitionOptionRel.getPrimaryKey());

		Assert.assertEquals(existingCommerceProductDefinitionOptionRel.getCommerceProductDefinitionOptionRelId(),
			newCommerceProductDefinitionOptionRel.getCommerceProductDefinitionOptionRelId());
		Assert.assertEquals(existingCommerceProductDefinitionOptionRel.getGroupId(),
			newCommerceProductDefinitionOptionRel.getGroupId());
		Assert.assertEquals(existingCommerceProductDefinitionOptionRel.getCompanyId(),
			newCommerceProductDefinitionOptionRel.getCompanyId());
		Assert.assertEquals(existingCommerceProductDefinitionOptionRel.getUserId(),
			newCommerceProductDefinitionOptionRel.getUserId());
		Assert.assertEquals(existingCommerceProductDefinitionOptionRel.getUserName(),
			newCommerceProductDefinitionOptionRel.getUserName());
		Assert.assertEquals(Time.getShortTimestamp(
				existingCommerceProductDefinitionOptionRel.getCreateDate()),
			Time.getShortTimestamp(
				newCommerceProductDefinitionOptionRel.getCreateDate()));
		Assert.assertEquals(Time.getShortTimestamp(
				existingCommerceProductDefinitionOptionRel.getModifiedDate()),
			Time.getShortTimestamp(
				newCommerceProductDefinitionOptionRel.getModifiedDate()));
		Assert.assertEquals(existingCommerceProductDefinitionOptionRel.getCommerceProductDefinitionId(),
			newCommerceProductDefinitionOptionRel.getCommerceProductDefinitionId());
		Assert.assertEquals(existingCommerceProductDefinitionOptionRel.getCommerceProductOptionId(),
			newCommerceProductDefinitionOptionRel.getCommerceProductOptionId());
		Assert.assertEquals(existingCommerceProductDefinitionOptionRel.getName(),
			newCommerceProductDefinitionOptionRel.getName());
		Assert.assertEquals(existingCommerceProductDefinitionOptionRel.getDescription(),
			newCommerceProductDefinitionOptionRel.getDescription());
		Assert.assertEquals(existingCommerceProductDefinitionOptionRel.getDDMFormFieldTypeName(),
			newCommerceProductDefinitionOptionRel.getDDMFormFieldTypeName());
		Assert.assertEquals(existingCommerceProductDefinitionOptionRel.getPriority(),
			newCommerceProductDefinitionOptionRel.getPriority());
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
		CommerceProductDefinitionOptionRel newCommerceProductDefinitionOptionRel =
			addCommerceProductDefinitionOptionRel();

		CommerceProductDefinitionOptionRel existingCommerceProductDefinitionOptionRel =
			_persistence.findByPrimaryKey(newCommerceProductDefinitionOptionRel.getPrimaryKey());

		Assert.assertEquals(existingCommerceProductDefinitionOptionRel,
			newCommerceProductDefinitionOptionRel);
	}

	@Test(expected = NoSuchProductDefinitionOptionRelException.class)
	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		_persistence.findByPrimaryKey(pk);
	}

	@Test
	public void testFindAll() throws Exception {
		_persistence.findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			getOrderByComparator());
	}

	protected OrderByComparator<CommerceProductDefinitionOptionRel> getOrderByComparator() {
		return OrderByComparatorFactoryUtil.create("CommerceProductDefinitionOptionRel",
			"commerceProductDefinitionOptionRelId", true, "groupId", true,
			"companyId", true, "userId", true, "userName", true, "createDate",
			true, "modifiedDate", true, "commerceProductDefinitionId", true,
			"commerceProductOptionId", true, "name", true, "description", true,
			"DDMFormFieldTypeName", true, "priority", true);
	}

	@Test
	public void testFetchByPrimaryKeyExisting() throws Exception {
		CommerceProductDefinitionOptionRel newCommerceProductDefinitionOptionRel =
			addCommerceProductDefinitionOptionRel();

		CommerceProductDefinitionOptionRel existingCommerceProductDefinitionOptionRel =
			_persistence.fetchByPrimaryKey(newCommerceProductDefinitionOptionRel.getPrimaryKey());

		Assert.assertEquals(existingCommerceProductDefinitionOptionRel,
			newCommerceProductDefinitionOptionRel);
	}

	@Test
	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		CommerceProductDefinitionOptionRel missingCommerceProductDefinitionOptionRel =
			_persistence.fetchByPrimaryKey(pk);

		Assert.assertNull(missingCommerceProductDefinitionOptionRel);
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereAllPrimaryKeysExist()
		throws Exception {
		CommerceProductDefinitionOptionRel newCommerceProductDefinitionOptionRel1 =
			addCommerceProductDefinitionOptionRel();
		CommerceProductDefinitionOptionRel newCommerceProductDefinitionOptionRel2 =
			addCommerceProductDefinitionOptionRel();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newCommerceProductDefinitionOptionRel1.getPrimaryKey());
		primaryKeys.add(newCommerceProductDefinitionOptionRel2.getPrimaryKey());

		Map<Serializable, CommerceProductDefinitionOptionRel> commerceProductDefinitionOptionRels =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(2, commerceProductDefinitionOptionRels.size());
		Assert.assertEquals(newCommerceProductDefinitionOptionRel1,
			commerceProductDefinitionOptionRels.get(
				newCommerceProductDefinitionOptionRel1.getPrimaryKey()));
		Assert.assertEquals(newCommerceProductDefinitionOptionRel2,
			commerceProductDefinitionOptionRels.get(
				newCommerceProductDefinitionOptionRel2.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereNoPrimaryKeysExist()
		throws Exception {
		long pk1 = RandomTestUtil.nextLong();

		long pk2 = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(pk1);
		primaryKeys.add(pk2);

		Map<Serializable, CommerceProductDefinitionOptionRel> commerceProductDefinitionOptionRels =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(commerceProductDefinitionOptionRels.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereSomePrimaryKeysExist()
		throws Exception {
		CommerceProductDefinitionOptionRel newCommerceProductDefinitionOptionRel =
			addCommerceProductDefinitionOptionRel();

		long pk = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newCommerceProductDefinitionOptionRel.getPrimaryKey());
		primaryKeys.add(pk);

		Map<Serializable, CommerceProductDefinitionOptionRel> commerceProductDefinitionOptionRels =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, commerceProductDefinitionOptionRels.size());
		Assert.assertEquals(newCommerceProductDefinitionOptionRel,
			commerceProductDefinitionOptionRels.get(
				newCommerceProductDefinitionOptionRel.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithNoPrimaryKeys()
		throws Exception {
		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		Map<Serializable, CommerceProductDefinitionOptionRel> commerceProductDefinitionOptionRels =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(commerceProductDefinitionOptionRels.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithOnePrimaryKey()
		throws Exception {
		CommerceProductDefinitionOptionRel newCommerceProductDefinitionOptionRel =
			addCommerceProductDefinitionOptionRel();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newCommerceProductDefinitionOptionRel.getPrimaryKey());

		Map<Serializable, CommerceProductDefinitionOptionRel> commerceProductDefinitionOptionRels =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, commerceProductDefinitionOptionRels.size());
		Assert.assertEquals(newCommerceProductDefinitionOptionRel,
			commerceProductDefinitionOptionRels.get(
				newCommerceProductDefinitionOptionRel.getPrimaryKey()));
	}

	@Test
	public void testActionableDynamicQuery() throws Exception {
		final IntegerWrapper count = new IntegerWrapper();

		ActionableDynamicQuery actionableDynamicQuery = CommerceProductDefinitionOptionRelLocalServiceUtil.getActionableDynamicQuery();

		actionableDynamicQuery.setPerformActionMethod(new ActionableDynamicQuery.PerformActionMethod<CommerceProductDefinitionOptionRel>() {
				@Override
				public void performAction(
					CommerceProductDefinitionOptionRel commerceProductDefinitionOptionRel) {
					Assert.assertNotNull(commerceProductDefinitionOptionRel);

					count.increment();
				}
			});

		actionableDynamicQuery.performActions();

		Assert.assertEquals(count.getValue(), _persistence.countAll());
	}

	@Test
	public void testDynamicQueryByPrimaryKeyExisting()
		throws Exception {
		CommerceProductDefinitionOptionRel newCommerceProductDefinitionOptionRel =
			addCommerceProductDefinitionOptionRel();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(CommerceProductDefinitionOptionRel.class,
				_dynamicQueryClassLoader);

		dynamicQuery.add(RestrictionsFactoryUtil.eq(
				"commerceProductDefinitionOptionRelId",
				newCommerceProductDefinitionOptionRel.getCommerceProductDefinitionOptionRelId()));

		List<CommerceProductDefinitionOptionRel> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		CommerceProductDefinitionOptionRel existingCommerceProductDefinitionOptionRel =
			result.get(0);

		Assert.assertEquals(existingCommerceProductDefinitionOptionRel,
			newCommerceProductDefinitionOptionRel);
	}

	@Test
	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(CommerceProductDefinitionOptionRel.class,
				_dynamicQueryClassLoader);

		dynamicQuery.add(RestrictionsFactoryUtil.eq(
				"commerceProductDefinitionOptionRelId",
				RandomTestUtil.nextLong()));

		List<CommerceProductDefinitionOptionRel> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testDynamicQueryByProjectionExisting()
		throws Exception {
		CommerceProductDefinitionOptionRel newCommerceProductDefinitionOptionRel =
			addCommerceProductDefinitionOptionRel();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(CommerceProductDefinitionOptionRel.class,
				_dynamicQueryClassLoader);

		dynamicQuery.setProjection(ProjectionFactoryUtil.property(
				"commerceProductDefinitionOptionRelId"));

		Object newCommerceProductDefinitionOptionRelId = newCommerceProductDefinitionOptionRel.getCommerceProductDefinitionOptionRelId();

		dynamicQuery.add(RestrictionsFactoryUtil.in(
				"commerceProductDefinitionOptionRelId",
				new Object[] { newCommerceProductDefinitionOptionRelId }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		Object existingCommerceProductDefinitionOptionRelId = result.get(0);

		Assert.assertEquals(existingCommerceProductDefinitionOptionRelId,
			newCommerceProductDefinitionOptionRelId);
	}

	@Test
	public void testDynamicQueryByProjectionMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(CommerceProductDefinitionOptionRel.class,
				_dynamicQueryClassLoader);

		dynamicQuery.setProjection(ProjectionFactoryUtil.property(
				"commerceProductDefinitionOptionRelId"));

		dynamicQuery.add(RestrictionsFactoryUtil.in(
				"commerceProductDefinitionOptionRelId",
				new Object[] { RandomTestUtil.nextLong() }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	protected CommerceProductDefinitionOptionRel addCommerceProductDefinitionOptionRel()
		throws Exception {
		long pk = RandomTestUtil.nextLong();

		CommerceProductDefinitionOptionRel commerceProductDefinitionOptionRel = _persistence.create(pk);

		commerceProductDefinitionOptionRel.setGroupId(RandomTestUtil.nextLong());

		commerceProductDefinitionOptionRel.setCompanyId(RandomTestUtil.nextLong());

		commerceProductDefinitionOptionRel.setUserId(RandomTestUtil.nextLong());

		commerceProductDefinitionOptionRel.setUserName(RandomTestUtil.randomString());

		commerceProductDefinitionOptionRel.setCreateDate(RandomTestUtil.nextDate());

		commerceProductDefinitionOptionRel.setModifiedDate(RandomTestUtil.nextDate());

		commerceProductDefinitionOptionRel.setCommerceProductDefinitionId(RandomTestUtil.nextLong());

		commerceProductDefinitionOptionRel.setCommerceProductOptionId(RandomTestUtil.nextLong());

		commerceProductDefinitionOptionRel.setName(RandomTestUtil.randomString());

		commerceProductDefinitionOptionRel.setDescription(RandomTestUtil.randomString());

		commerceProductDefinitionOptionRel.setDDMFormFieldTypeName(RandomTestUtil.randomString());

		commerceProductDefinitionOptionRel.setPriority(RandomTestUtil.nextInt());

		_commerceProductDefinitionOptionRels.add(_persistence.update(
				commerceProductDefinitionOptionRel));

		return commerceProductDefinitionOptionRel;
	}

	private List<CommerceProductDefinitionOptionRel> _commerceProductDefinitionOptionRels =
		new ArrayList<CommerceProductDefinitionOptionRel>();
	private CommerceProductDefinitionOptionRelPersistence _persistence;
	private ClassLoader _dynamicQueryClassLoader;
}