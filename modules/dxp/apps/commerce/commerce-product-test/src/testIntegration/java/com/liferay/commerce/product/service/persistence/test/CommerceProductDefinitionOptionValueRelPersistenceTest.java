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

import com.liferay.commerce.product.exception.NoSuchProductDefinitionOptionValueRelException;
import com.liferay.commerce.product.model.CommerceProductDefinitionOptionValueRel;
import com.liferay.commerce.product.service.CommerceProductDefinitionOptionValueRelLocalServiceUtil;
import com.liferay.commerce.product.service.persistence.CommerceProductDefinitionOptionValueRelPersistence;
import com.liferay.commerce.product.service.persistence.CommerceProductDefinitionOptionValueRelUtil;

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
public class CommerceProductDefinitionOptionValueRelPersistenceTest {
	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule = new AggregateTestRule(new LiferayIntegrationTestRule(),
			PersistenceTestRule.INSTANCE,
			new TransactionalTestRule(Propagation.REQUIRED,
				"com.liferay.commerce.product.service"));

	@Before
	public void setUp() {
		_persistence = CommerceProductDefinitionOptionValueRelUtil.getPersistence();

		Class<?> clazz = _persistence.getClass();

		_dynamicQueryClassLoader = clazz.getClassLoader();
	}

	@After
	public void tearDown() throws Exception {
		Iterator<CommerceProductDefinitionOptionValueRel> iterator = _commerceProductDefinitionOptionValueRels.iterator();

		while (iterator.hasNext()) {
			_persistence.remove(iterator.next());

			iterator.remove();
		}
	}

	@Test
	public void testCreate() throws Exception {
		long pk = RandomTestUtil.nextLong();

		CommerceProductDefinitionOptionValueRel commerceProductDefinitionOptionValueRel =
			_persistence.create(pk);

		Assert.assertNotNull(commerceProductDefinitionOptionValueRel);

		Assert.assertEquals(commerceProductDefinitionOptionValueRel.getPrimaryKey(),
			pk);
	}

	@Test
	public void testRemove() throws Exception {
		CommerceProductDefinitionOptionValueRel newCommerceProductDefinitionOptionValueRel =
			addCommerceProductDefinitionOptionValueRel();

		_persistence.remove(newCommerceProductDefinitionOptionValueRel);

		CommerceProductDefinitionOptionValueRel existingCommerceProductDefinitionOptionValueRel =
			_persistence.fetchByPrimaryKey(newCommerceProductDefinitionOptionValueRel.getPrimaryKey());

		Assert.assertNull(existingCommerceProductDefinitionOptionValueRel);
	}

	@Test
	public void testUpdateNew() throws Exception {
		addCommerceProductDefinitionOptionValueRel();
	}

	@Test
	public void testUpdateExisting() throws Exception {
		long pk = RandomTestUtil.nextLong();

		CommerceProductDefinitionOptionValueRel newCommerceProductDefinitionOptionValueRel =
			_persistence.create(pk);

		newCommerceProductDefinitionOptionValueRel.setGroupId(RandomTestUtil.nextLong());

		newCommerceProductDefinitionOptionValueRel.setCompanyId(RandomTestUtil.nextLong());

		newCommerceProductDefinitionOptionValueRel.setUserId(RandomTestUtil.nextLong());

		newCommerceProductDefinitionOptionValueRel.setUserName(RandomTestUtil.randomString());

		newCommerceProductDefinitionOptionValueRel.setCreateDate(RandomTestUtil.nextDate());

		newCommerceProductDefinitionOptionValueRel.setModifiedDate(RandomTestUtil.nextDate());

		newCommerceProductDefinitionOptionValueRel.setCommerceProductDefinitionOptionRelId(RandomTestUtil.nextLong());

		newCommerceProductDefinitionOptionValueRel.setTitle(RandomTestUtil.randomString());

		newCommerceProductDefinitionOptionValueRel.setPriority(RandomTestUtil.nextLong());

		_commerceProductDefinitionOptionValueRels.add(_persistence.update(
				newCommerceProductDefinitionOptionValueRel));

		CommerceProductDefinitionOptionValueRel existingCommerceProductDefinitionOptionValueRel =
			_persistence.findByPrimaryKey(newCommerceProductDefinitionOptionValueRel.getPrimaryKey());

		Assert.assertEquals(existingCommerceProductDefinitionOptionValueRel.getCommerceProductDefinitionOptionValueRelId(),
			newCommerceProductDefinitionOptionValueRel.getCommerceProductDefinitionOptionValueRelId());
		Assert.assertEquals(existingCommerceProductDefinitionOptionValueRel.getGroupId(),
			newCommerceProductDefinitionOptionValueRel.getGroupId());
		Assert.assertEquals(existingCommerceProductDefinitionOptionValueRel.getCompanyId(),
			newCommerceProductDefinitionOptionValueRel.getCompanyId());
		Assert.assertEquals(existingCommerceProductDefinitionOptionValueRel.getUserId(),
			newCommerceProductDefinitionOptionValueRel.getUserId());
		Assert.assertEquals(existingCommerceProductDefinitionOptionValueRel.getUserName(),
			newCommerceProductDefinitionOptionValueRel.getUserName());
		Assert.assertEquals(Time.getShortTimestamp(
				existingCommerceProductDefinitionOptionValueRel.getCreateDate()),
			Time.getShortTimestamp(
				newCommerceProductDefinitionOptionValueRel.getCreateDate()));
		Assert.assertEquals(Time.getShortTimestamp(
				existingCommerceProductDefinitionOptionValueRel.getModifiedDate()),
			Time.getShortTimestamp(
				newCommerceProductDefinitionOptionValueRel.getModifiedDate()));
		Assert.assertEquals(existingCommerceProductDefinitionOptionValueRel.getCommerceProductDefinitionOptionRelId(),
			newCommerceProductDefinitionOptionValueRel.getCommerceProductDefinitionOptionRelId());
		Assert.assertEquals(existingCommerceProductDefinitionOptionValueRel.getTitle(),
			newCommerceProductDefinitionOptionValueRel.getTitle());
		Assert.assertEquals(existingCommerceProductDefinitionOptionValueRel.getPriority(),
			newCommerceProductDefinitionOptionValueRel.getPriority());
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
		CommerceProductDefinitionOptionValueRel newCommerceProductDefinitionOptionValueRel =
			addCommerceProductDefinitionOptionValueRel();

		CommerceProductDefinitionOptionValueRel existingCommerceProductDefinitionOptionValueRel =
			_persistence.findByPrimaryKey(newCommerceProductDefinitionOptionValueRel.getPrimaryKey());

		Assert.assertEquals(existingCommerceProductDefinitionOptionValueRel,
			newCommerceProductDefinitionOptionValueRel);
	}

	@Test(expected = NoSuchProductDefinitionOptionValueRelException.class)
	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		_persistence.findByPrimaryKey(pk);
	}

	@Test
	public void testFindAll() throws Exception {
		_persistence.findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			getOrderByComparator());
	}

	protected OrderByComparator<CommerceProductDefinitionOptionValueRel> getOrderByComparator() {
		return OrderByComparatorFactoryUtil.create("CommerceProductDefinitionOptionValueRel",
			"commerceProductDefinitionOptionValueRelId", true, "groupId", true,
			"companyId", true, "userId", true, "userName", true, "createDate",
			true, "modifiedDate", true, "commerceProductDefinitionOptionRelId",
			true, "title", true, "priority", true);
	}

	@Test
	public void testFetchByPrimaryKeyExisting() throws Exception {
		CommerceProductDefinitionOptionValueRel newCommerceProductDefinitionOptionValueRel =
			addCommerceProductDefinitionOptionValueRel();

		CommerceProductDefinitionOptionValueRel existingCommerceProductDefinitionOptionValueRel =
			_persistence.fetchByPrimaryKey(newCommerceProductDefinitionOptionValueRel.getPrimaryKey());

		Assert.assertEquals(existingCommerceProductDefinitionOptionValueRel,
			newCommerceProductDefinitionOptionValueRel);
	}

	@Test
	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		CommerceProductDefinitionOptionValueRel missingCommerceProductDefinitionOptionValueRel =
			_persistence.fetchByPrimaryKey(pk);

		Assert.assertNull(missingCommerceProductDefinitionOptionValueRel);
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereAllPrimaryKeysExist()
		throws Exception {
		CommerceProductDefinitionOptionValueRel newCommerceProductDefinitionOptionValueRel1 =
			addCommerceProductDefinitionOptionValueRel();
		CommerceProductDefinitionOptionValueRel newCommerceProductDefinitionOptionValueRel2 =
			addCommerceProductDefinitionOptionValueRel();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newCommerceProductDefinitionOptionValueRel1.getPrimaryKey());
		primaryKeys.add(newCommerceProductDefinitionOptionValueRel2.getPrimaryKey());

		Map<Serializable, CommerceProductDefinitionOptionValueRel> commerceProductDefinitionOptionValueRels =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(2, commerceProductDefinitionOptionValueRels.size());
		Assert.assertEquals(newCommerceProductDefinitionOptionValueRel1,
			commerceProductDefinitionOptionValueRels.get(
				newCommerceProductDefinitionOptionValueRel1.getPrimaryKey()));
		Assert.assertEquals(newCommerceProductDefinitionOptionValueRel2,
			commerceProductDefinitionOptionValueRels.get(
				newCommerceProductDefinitionOptionValueRel2.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereNoPrimaryKeysExist()
		throws Exception {
		long pk1 = RandomTestUtil.nextLong();

		long pk2 = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(pk1);
		primaryKeys.add(pk2);

		Map<Serializable, CommerceProductDefinitionOptionValueRel> commerceProductDefinitionOptionValueRels =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(commerceProductDefinitionOptionValueRels.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereSomePrimaryKeysExist()
		throws Exception {
		CommerceProductDefinitionOptionValueRel newCommerceProductDefinitionOptionValueRel =
			addCommerceProductDefinitionOptionValueRel();

		long pk = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newCommerceProductDefinitionOptionValueRel.getPrimaryKey());
		primaryKeys.add(pk);

		Map<Serializable, CommerceProductDefinitionOptionValueRel> commerceProductDefinitionOptionValueRels =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, commerceProductDefinitionOptionValueRels.size());
		Assert.assertEquals(newCommerceProductDefinitionOptionValueRel,
			commerceProductDefinitionOptionValueRels.get(
				newCommerceProductDefinitionOptionValueRel.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithNoPrimaryKeys()
		throws Exception {
		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		Map<Serializable, CommerceProductDefinitionOptionValueRel> commerceProductDefinitionOptionValueRels =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(commerceProductDefinitionOptionValueRels.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithOnePrimaryKey()
		throws Exception {
		CommerceProductDefinitionOptionValueRel newCommerceProductDefinitionOptionValueRel =
			addCommerceProductDefinitionOptionValueRel();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newCommerceProductDefinitionOptionValueRel.getPrimaryKey());

		Map<Serializable, CommerceProductDefinitionOptionValueRel> commerceProductDefinitionOptionValueRels =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, commerceProductDefinitionOptionValueRels.size());
		Assert.assertEquals(newCommerceProductDefinitionOptionValueRel,
			commerceProductDefinitionOptionValueRels.get(
				newCommerceProductDefinitionOptionValueRel.getPrimaryKey()));
	}

	@Test
	public void testActionableDynamicQuery() throws Exception {
		final IntegerWrapper count = new IntegerWrapper();

		ActionableDynamicQuery actionableDynamicQuery = CommerceProductDefinitionOptionValueRelLocalServiceUtil.getActionableDynamicQuery();

		actionableDynamicQuery.setPerformActionMethod(new ActionableDynamicQuery.PerformActionMethod<CommerceProductDefinitionOptionValueRel>() {
				@Override
				public void performAction(
					CommerceProductDefinitionOptionValueRel commerceProductDefinitionOptionValueRel) {
					Assert.assertNotNull(commerceProductDefinitionOptionValueRel);

					count.increment();
				}
			});

		actionableDynamicQuery.performActions();

		Assert.assertEquals(count.getValue(), _persistence.countAll());
	}

	@Test
	public void testDynamicQueryByPrimaryKeyExisting()
		throws Exception {
		CommerceProductDefinitionOptionValueRel newCommerceProductDefinitionOptionValueRel =
			addCommerceProductDefinitionOptionValueRel();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(CommerceProductDefinitionOptionValueRel.class,
				_dynamicQueryClassLoader);

		dynamicQuery.add(RestrictionsFactoryUtil.eq(
				"commerceProductDefinitionOptionValueRelId",
				newCommerceProductDefinitionOptionValueRel.getCommerceProductDefinitionOptionValueRelId()));

		List<CommerceProductDefinitionOptionValueRel> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		CommerceProductDefinitionOptionValueRel existingCommerceProductDefinitionOptionValueRel =
			result.get(0);

		Assert.assertEquals(existingCommerceProductDefinitionOptionValueRel,
			newCommerceProductDefinitionOptionValueRel);
	}

	@Test
	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(CommerceProductDefinitionOptionValueRel.class,
				_dynamicQueryClassLoader);

		dynamicQuery.add(RestrictionsFactoryUtil.eq(
				"commerceProductDefinitionOptionValueRelId",
				RandomTestUtil.nextLong()));

		List<CommerceProductDefinitionOptionValueRel> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testDynamicQueryByProjectionExisting()
		throws Exception {
		CommerceProductDefinitionOptionValueRel newCommerceProductDefinitionOptionValueRel =
			addCommerceProductDefinitionOptionValueRel();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(CommerceProductDefinitionOptionValueRel.class,
				_dynamicQueryClassLoader);

		dynamicQuery.setProjection(ProjectionFactoryUtil.property(
				"commerceProductDefinitionOptionValueRelId"));

		Object newCommerceProductDefinitionOptionValueRelId = newCommerceProductDefinitionOptionValueRel.getCommerceProductDefinitionOptionValueRelId();

		dynamicQuery.add(RestrictionsFactoryUtil.in(
				"commerceProductDefinitionOptionValueRelId",
				new Object[] { newCommerceProductDefinitionOptionValueRelId }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		Object existingCommerceProductDefinitionOptionValueRelId = result.get(0);

		Assert.assertEquals(existingCommerceProductDefinitionOptionValueRelId,
			newCommerceProductDefinitionOptionValueRelId);
	}

	@Test
	public void testDynamicQueryByProjectionMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(CommerceProductDefinitionOptionValueRel.class,
				_dynamicQueryClassLoader);

		dynamicQuery.setProjection(ProjectionFactoryUtil.property(
				"commerceProductDefinitionOptionValueRelId"));

		dynamicQuery.add(RestrictionsFactoryUtil.in(
				"commerceProductDefinitionOptionValueRelId",
				new Object[] { RandomTestUtil.nextLong() }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	protected CommerceProductDefinitionOptionValueRel addCommerceProductDefinitionOptionValueRel()
		throws Exception {
		long pk = RandomTestUtil.nextLong();

		CommerceProductDefinitionOptionValueRel commerceProductDefinitionOptionValueRel =
			_persistence.create(pk);

		commerceProductDefinitionOptionValueRel.setGroupId(RandomTestUtil.nextLong());

		commerceProductDefinitionOptionValueRel.setCompanyId(RandomTestUtil.nextLong());

		commerceProductDefinitionOptionValueRel.setUserId(RandomTestUtil.nextLong());

		commerceProductDefinitionOptionValueRel.setUserName(RandomTestUtil.randomString());

		commerceProductDefinitionOptionValueRel.setCreateDate(RandomTestUtil.nextDate());

		commerceProductDefinitionOptionValueRel.setModifiedDate(RandomTestUtil.nextDate());

		commerceProductDefinitionOptionValueRel.setCommerceProductDefinitionOptionRelId(RandomTestUtil.nextLong());

		commerceProductDefinitionOptionValueRel.setTitle(RandomTestUtil.randomString());

		commerceProductDefinitionOptionValueRel.setPriority(RandomTestUtil.nextLong());

		_commerceProductDefinitionOptionValueRels.add(_persistence.update(
				commerceProductDefinitionOptionValueRel));

		return commerceProductDefinitionOptionValueRel;
	}

	private List<CommerceProductDefinitionOptionValueRel> _commerceProductDefinitionOptionValueRels =
		new ArrayList<CommerceProductDefinitionOptionValueRel>();
	private CommerceProductDefinitionOptionValueRelPersistence _persistence;
	private ClassLoader _dynamicQueryClassLoader;
}