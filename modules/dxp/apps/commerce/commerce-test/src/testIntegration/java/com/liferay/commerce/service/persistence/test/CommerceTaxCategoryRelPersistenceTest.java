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

package com.liferay.commerce.service.persistence.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;

import com.liferay.commerce.exception.NoSuchTaxCategoryRelException;
import com.liferay.commerce.model.CommerceTaxCategoryRel;
import com.liferay.commerce.service.CommerceTaxCategoryRelLocalServiceUtil;
import com.liferay.commerce.service.persistence.CommerceTaxCategoryRelPersistence;
import com.liferay.commerce.service.persistence.CommerceTaxCategoryRelUtil;

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
public class CommerceTaxCategoryRelPersistenceTest {
	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule = new AggregateTestRule(new LiferayIntegrationTestRule(),
			PersistenceTestRule.INSTANCE,
			new TransactionalTestRule(Propagation.REQUIRED,
				"com.liferay.commerce.service"));

	@Before
	public void setUp() {
		_persistence = CommerceTaxCategoryRelUtil.getPersistence();

		Class<?> clazz = _persistence.getClass();

		_dynamicQueryClassLoader = clazz.getClassLoader();
	}

	@After
	public void tearDown() throws Exception {
		Iterator<CommerceTaxCategoryRel> iterator = _commerceTaxCategoryRels.iterator();

		while (iterator.hasNext()) {
			_persistence.remove(iterator.next());

			iterator.remove();
		}
	}

	@Test
	public void testCreate() throws Exception {
		long pk = RandomTestUtil.nextLong();

		CommerceTaxCategoryRel commerceTaxCategoryRel = _persistence.create(pk);

		Assert.assertNotNull(commerceTaxCategoryRel);

		Assert.assertEquals(commerceTaxCategoryRel.getPrimaryKey(), pk);
	}

	@Test
	public void testRemove() throws Exception {
		CommerceTaxCategoryRel newCommerceTaxCategoryRel = addCommerceTaxCategoryRel();

		_persistence.remove(newCommerceTaxCategoryRel);

		CommerceTaxCategoryRel existingCommerceTaxCategoryRel = _persistence.fetchByPrimaryKey(newCommerceTaxCategoryRel.getPrimaryKey());

		Assert.assertNull(existingCommerceTaxCategoryRel);
	}

	@Test
	public void testUpdateNew() throws Exception {
		addCommerceTaxCategoryRel();
	}

	@Test
	public void testUpdateExisting() throws Exception {
		long pk = RandomTestUtil.nextLong();

		CommerceTaxCategoryRel newCommerceTaxCategoryRel = _persistence.create(pk);

		newCommerceTaxCategoryRel.setGroupId(RandomTestUtil.nextLong());

		newCommerceTaxCategoryRel.setCompanyId(RandomTestUtil.nextLong());

		newCommerceTaxCategoryRel.setUserId(RandomTestUtil.nextLong());

		newCommerceTaxCategoryRel.setUserName(RandomTestUtil.randomString());

		newCommerceTaxCategoryRel.setCreateDate(RandomTestUtil.nextDate());

		newCommerceTaxCategoryRel.setModifiedDate(RandomTestUtil.nextDate());

		newCommerceTaxCategoryRel.setCommerceTaxCategoryId(RandomTestUtil.nextLong());

		newCommerceTaxCategoryRel.setClassNameId(RandomTestUtil.nextLong());

		newCommerceTaxCategoryRel.setClassPK(RandomTestUtil.nextLong());

		_commerceTaxCategoryRels.add(_persistence.update(
				newCommerceTaxCategoryRel));

		CommerceTaxCategoryRel existingCommerceTaxCategoryRel = _persistence.findByPrimaryKey(newCommerceTaxCategoryRel.getPrimaryKey());

		Assert.assertEquals(existingCommerceTaxCategoryRel.getCommerceTaxCategoryRelId(),
			newCommerceTaxCategoryRel.getCommerceTaxCategoryRelId());
		Assert.assertEquals(existingCommerceTaxCategoryRel.getGroupId(),
			newCommerceTaxCategoryRel.getGroupId());
		Assert.assertEquals(existingCommerceTaxCategoryRel.getCompanyId(),
			newCommerceTaxCategoryRel.getCompanyId());
		Assert.assertEquals(existingCommerceTaxCategoryRel.getUserId(),
			newCommerceTaxCategoryRel.getUserId());
		Assert.assertEquals(existingCommerceTaxCategoryRel.getUserName(),
			newCommerceTaxCategoryRel.getUserName());
		Assert.assertEquals(Time.getShortTimestamp(
				existingCommerceTaxCategoryRel.getCreateDate()),
			Time.getShortTimestamp(newCommerceTaxCategoryRel.getCreateDate()));
		Assert.assertEquals(Time.getShortTimestamp(
				existingCommerceTaxCategoryRel.getModifiedDate()),
			Time.getShortTimestamp(newCommerceTaxCategoryRel.getModifiedDate()));
		Assert.assertEquals(existingCommerceTaxCategoryRel.getCommerceTaxCategoryId(),
			newCommerceTaxCategoryRel.getCommerceTaxCategoryId());
		Assert.assertEquals(existingCommerceTaxCategoryRel.getClassNameId(),
			newCommerceTaxCategoryRel.getClassNameId());
		Assert.assertEquals(existingCommerceTaxCategoryRel.getClassPK(),
			newCommerceTaxCategoryRel.getClassPK());
	}

	@Test
	public void testCountByCommerceTaxCategoryId() throws Exception {
		_persistence.countByCommerceTaxCategoryId(RandomTestUtil.nextLong());

		_persistence.countByCommerceTaxCategoryId(0L);
	}

	@Test
	public void testCountByC_C() throws Exception {
		_persistence.countByC_C(RandomTestUtil.nextLong(),
			RandomTestUtil.nextLong());

		_persistence.countByC_C(0L, 0L);
	}

	@Test
	public void testCountByC_C_C() throws Exception {
		_persistence.countByC_C_C(RandomTestUtil.nextLong(),
			RandomTestUtil.nextLong(), RandomTestUtil.nextLong());

		_persistence.countByC_C_C(0L, 0L, 0L);
	}

	@Test
	public void testFindByPrimaryKeyExisting() throws Exception {
		CommerceTaxCategoryRel newCommerceTaxCategoryRel = addCommerceTaxCategoryRel();

		CommerceTaxCategoryRel existingCommerceTaxCategoryRel = _persistence.findByPrimaryKey(newCommerceTaxCategoryRel.getPrimaryKey());

		Assert.assertEquals(existingCommerceTaxCategoryRel,
			newCommerceTaxCategoryRel);
	}

	@Test(expected = NoSuchTaxCategoryRelException.class)
	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		_persistence.findByPrimaryKey(pk);
	}

	@Test
	public void testFindAll() throws Exception {
		_persistence.findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			getOrderByComparator());
	}

	protected OrderByComparator<CommerceTaxCategoryRel> getOrderByComparator() {
		return OrderByComparatorFactoryUtil.create("CommerceTaxCategoryRel",
			"commerceTaxCategoryRelId", true, "groupId", true, "companyId",
			true, "userId", true, "userName", true, "createDate", true,
			"modifiedDate", true, "commerceTaxCategoryId", true, "classNameId",
			true, "classPK", true);
	}

	@Test
	public void testFetchByPrimaryKeyExisting() throws Exception {
		CommerceTaxCategoryRel newCommerceTaxCategoryRel = addCommerceTaxCategoryRel();

		CommerceTaxCategoryRel existingCommerceTaxCategoryRel = _persistence.fetchByPrimaryKey(newCommerceTaxCategoryRel.getPrimaryKey());

		Assert.assertEquals(existingCommerceTaxCategoryRel,
			newCommerceTaxCategoryRel);
	}

	@Test
	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		CommerceTaxCategoryRel missingCommerceTaxCategoryRel = _persistence.fetchByPrimaryKey(pk);

		Assert.assertNull(missingCommerceTaxCategoryRel);
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereAllPrimaryKeysExist()
		throws Exception {
		CommerceTaxCategoryRel newCommerceTaxCategoryRel1 = addCommerceTaxCategoryRel();
		CommerceTaxCategoryRel newCommerceTaxCategoryRel2 = addCommerceTaxCategoryRel();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newCommerceTaxCategoryRel1.getPrimaryKey());
		primaryKeys.add(newCommerceTaxCategoryRel2.getPrimaryKey());

		Map<Serializable, CommerceTaxCategoryRel> commerceTaxCategoryRels = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(2, commerceTaxCategoryRels.size());
		Assert.assertEquals(newCommerceTaxCategoryRel1,
			commerceTaxCategoryRels.get(
				newCommerceTaxCategoryRel1.getPrimaryKey()));
		Assert.assertEquals(newCommerceTaxCategoryRel2,
			commerceTaxCategoryRels.get(
				newCommerceTaxCategoryRel2.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereNoPrimaryKeysExist()
		throws Exception {
		long pk1 = RandomTestUtil.nextLong();

		long pk2 = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(pk1);
		primaryKeys.add(pk2);

		Map<Serializable, CommerceTaxCategoryRel> commerceTaxCategoryRels = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(commerceTaxCategoryRels.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereSomePrimaryKeysExist()
		throws Exception {
		CommerceTaxCategoryRel newCommerceTaxCategoryRel = addCommerceTaxCategoryRel();

		long pk = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newCommerceTaxCategoryRel.getPrimaryKey());
		primaryKeys.add(pk);

		Map<Serializable, CommerceTaxCategoryRel> commerceTaxCategoryRels = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, commerceTaxCategoryRels.size());
		Assert.assertEquals(newCommerceTaxCategoryRel,
			commerceTaxCategoryRels.get(
				newCommerceTaxCategoryRel.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithNoPrimaryKeys()
		throws Exception {
		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		Map<Serializable, CommerceTaxCategoryRel> commerceTaxCategoryRels = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(commerceTaxCategoryRels.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithOnePrimaryKey()
		throws Exception {
		CommerceTaxCategoryRel newCommerceTaxCategoryRel = addCommerceTaxCategoryRel();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newCommerceTaxCategoryRel.getPrimaryKey());

		Map<Serializable, CommerceTaxCategoryRel> commerceTaxCategoryRels = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, commerceTaxCategoryRels.size());
		Assert.assertEquals(newCommerceTaxCategoryRel,
			commerceTaxCategoryRels.get(
				newCommerceTaxCategoryRel.getPrimaryKey()));
	}

	@Test
	public void testActionableDynamicQuery() throws Exception {
		final IntegerWrapper count = new IntegerWrapper();

		ActionableDynamicQuery actionableDynamicQuery = CommerceTaxCategoryRelLocalServiceUtil.getActionableDynamicQuery();

		actionableDynamicQuery.setPerformActionMethod(new ActionableDynamicQuery.PerformActionMethod<CommerceTaxCategoryRel>() {
				@Override
				public void performAction(
					CommerceTaxCategoryRel commerceTaxCategoryRel) {
					Assert.assertNotNull(commerceTaxCategoryRel);

					count.increment();
				}
			});

		actionableDynamicQuery.performActions();

		Assert.assertEquals(count.getValue(), _persistence.countAll());
	}

	@Test
	public void testDynamicQueryByPrimaryKeyExisting()
		throws Exception {
		CommerceTaxCategoryRel newCommerceTaxCategoryRel = addCommerceTaxCategoryRel();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(CommerceTaxCategoryRel.class,
				_dynamicQueryClassLoader);

		dynamicQuery.add(RestrictionsFactoryUtil.eq(
				"commerceTaxCategoryRelId",
				newCommerceTaxCategoryRel.getCommerceTaxCategoryRelId()));

		List<CommerceTaxCategoryRel> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		CommerceTaxCategoryRel existingCommerceTaxCategoryRel = result.get(0);

		Assert.assertEquals(existingCommerceTaxCategoryRel,
			newCommerceTaxCategoryRel);
	}

	@Test
	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(CommerceTaxCategoryRel.class,
				_dynamicQueryClassLoader);

		dynamicQuery.add(RestrictionsFactoryUtil.eq(
				"commerceTaxCategoryRelId", RandomTestUtil.nextLong()));

		List<CommerceTaxCategoryRel> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testDynamicQueryByProjectionExisting()
		throws Exception {
		CommerceTaxCategoryRel newCommerceTaxCategoryRel = addCommerceTaxCategoryRel();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(CommerceTaxCategoryRel.class,
				_dynamicQueryClassLoader);

		dynamicQuery.setProjection(ProjectionFactoryUtil.property(
				"commerceTaxCategoryRelId"));

		Object newCommerceTaxCategoryRelId = newCommerceTaxCategoryRel.getCommerceTaxCategoryRelId();

		dynamicQuery.add(RestrictionsFactoryUtil.in(
				"commerceTaxCategoryRelId",
				new Object[] { newCommerceTaxCategoryRelId }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		Object existingCommerceTaxCategoryRelId = result.get(0);

		Assert.assertEquals(existingCommerceTaxCategoryRelId,
			newCommerceTaxCategoryRelId);
	}

	@Test
	public void testDynamicQueryByProjectionMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(CommerceTaxCategoryRel.class,
				_dynamicQueryClassLoader);

		dynamicQuery.setProjection(ProjectionFactoryUtil.property(
				"commerceTaxCategoryRelId"));

		dynamicQuery.add(RestrictionsFactoryUtil.in(
				"commerceTaxCategoryRelId",
				new Object[] { RandomTestUtil.nextLong() }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testResetOriginalValues() throws Exception {
		CommerceTaxCategoryRel newCommerceTaxCategoryRel = addCommerceTaxCategoryRel();

		_persistence.clearCache();

		CommerceTaxCategoryRel existingCommerceTaxCategoryRel = _persistence.findByPrimaryKey(newCommerceTaxCategoryRel.getPrimaryKey());

		Assert.assertEquals(Long.valueOf(
				existingCommerceTaxCategoryRel.getCommerceTaxCategoryId()),
			ReflectionTestUtil.<Long>invoke(existingCommerceTaxCategoryRel,
				"getOriginalCommerceTaxCategoryId", new Class<?>[0]));
		Assert.assertEquals(Long.valueOf(
				existingCommerceTaxCategoryRel.getClassNameId()),
			ReflectionTestUtil.<Long>invoke(existingCommerceTaxCategoryRel,
				"getOriginalClassNameId", new Class<?>[0]));
		Assert.assertEquals(Long.valueOf(
				existingCommerceTaxCategoryRel.getClassPK()),
			ReflectionTestUtil.<Long>invoke(existingCommerceTaxCategoryRel,
				"getOriginalClassPK", new Class<?>[0]));
	}

	protected CommerceTaxCategoryRel addCommerceTaxCategoryRel()
		throws Exception {
		long pk = RandomTestUtil.nextLong();

		CommerceTaxCategoryRel commerceTaxCategoryRel = _persistence.create(pk);

		commerceTaxCategoryRel.setGroupId(RandomTestUtil.nextLong());

		commerceTaxCategoryRel.setCompanyId(RandomTestUtil.nextLong());

		commerceTaxCategoryRel.setUserId(RandomTestUtil.nextLong());

		commerceTaxCategoryRel.setUserName(RandomTestUtil.randomString());

		commerceTaxCategoryRel.setCreateDate(RandomTestUtil.nextDate());

		commerceTaxCategoryRel.setModifiedDate(RandomTestUtil.nextDate());

		commerceTaxCategoryRel.setCommerceTaxCategoryId(RandomTestUtil.nextLong());

		commerceTaxCategoryRel.setClassNameId(RandomTestUtil.nextLong());

		commerceTaxCategoryRel.setClassPK(RandomTestUtil.nextLong());

		_commerceTaxCategoryRels.add(_persistence.update(commerceTaxCategoryRel));

		return commerceTaxCategoryRel;
	}

	private List<CommerceTaxCategoryRel> _commerceTaxCategoryRels = new ArrayList<CommerceTaxCategoryRel>();
	private CommerceTaxCategoryRelPersistence _persistence;
	private ClassLoader _dynamicQueryClassLoader;
}