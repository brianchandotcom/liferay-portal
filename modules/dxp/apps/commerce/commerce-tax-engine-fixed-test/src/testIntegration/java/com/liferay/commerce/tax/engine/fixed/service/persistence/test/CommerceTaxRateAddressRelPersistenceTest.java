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

package com.liferay.commerce.tax.engine.fixed.service.persistence.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;

import com.liferay.commerce.tax.engine.fixed.exception.NoSuchTaxRateAddressRelException;
import com.liferay.commerce.tax.engine.fixed.model.CommerceTaxRateAddressRel;
import com.liferay.commerce.tax.engine.fixed.service.CommerceTaxRateAddressRelLocalServiceUtil;
import com.liferay.commerce.tax.engine.fixed.service.persistence.CommerceTaxRateAddressRelPersistence;
import com.liferay.commerce.tax.engine.fixed.service.persistence.CommerceTaxRateAddressRelUtil;

import com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQueryFactoryUtil;
import com.liferay.portal.kernel.dao.orm.ProjectionFactoryUtil;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.RestrictionsFactoryUtil;
import com.liferay.portal.kernel.test.AssertUtils;
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
public class CommerceTaxRateAddressRelPersistenceTest {
	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule = new AggregateTestRule(new LiferayIntegrationTestRule(),
			PersistenceTestRule.INSTANCE,
			new TransactionalTestRule(Propagation.REQUIRED,
				"com.liferay.commerce.tax.engine.fixed.service"));

	@Before
	public void setUp() {
		_persistence = CommerceTaxRateAddressRelUtil.getPersistence();

		Class<?> clazz = _persistence.getClass();

		_dynamicQueryClassLoader = clazz.getClassLoader();
	}

	@After
	public void tearDown() throws Exception {
		Iterator<CommerceTaxRateAddressRel> iterator = _commerceTaxRateAddressRels.iterator();

		while (iterator.hasNext()) {
			_persistence.remove(iterator.next());

			iterator.remove();
		}
	}

	@Test
	public void testCreate() throws Exception {
		long pk = RandomTestUtil.nextLong();

		CommerceTaxRateAddressRel commerceTaxRateAddressRel = _persistence.create(pk);

		Assert.assertNotNull(commerceTaxRateAddressRel);

		Assert.assertEquals(commerceTaxRateAddressRel.getPrimaryKey(), pk);
	}

	@Test
	public void testRemove() throws Exception {
		CommerceTaxRateAddressRel newCommerceTaxRateAddressRel = addCommerceTaxRateAddressRel();

		_persistence.remove(newCommerceTaxRateAddressRel);

		CommerceTaxRateAddressRel existingCommerceTaxRateAddressRel = _persistence.fetchByPrimaryKey(newCommerceTaxRateAddressRel.getPrimaryKey());

		Assert.assertNull(existingCommerceTaxRateAddressRel);
	}

	@Test
	public void testUpdateNew() throws Exception {
		addCommerceTaxRateAddressRel();
	}

	@Test
	public void testUpdateExisting() throws Exception {
		long pk = RandomTestUtil.nextLong();

		CommerceTaxRateAddressRel newCommerceTaxRateAddressRel = _persistence.create(pk);

		newCommerceTaxRateAddressRel.setGroupId(RandomTestUtil.nextLong());

		newCommerceTaxRateAddressRel.setCompanyId(RandomTestUtil.nextLong());

		newCommerceTaxRateAddressRel.setUserId(RandomTestUtil.nextLong());

		newCommerceTaxRateAddressRel.setUserName(RandomTestUtil.randomString());

		newCommerceTaxRateAddressRel.setCreateDate(RandomTestUtil.nextDate());

		newCommerceTaxRateAddressRel.setModifiedDate(RandomTestUtil.nextDate());

		newCommerceTaxRateAddressRel.setCommerceTaxMethodId(RandomTestUtil.nextLong());

		newCommerceTaxRateAddressRel.setCommerceTaxFixedRateId(RandomTestUtil.nextLong());

		newCommerceTaxRateAddressRel.setCommerceCountryId(RandomTestUtil.nextLong());

		newCommerceTaxRateAddressRel.setCommerceRegionId(RandomTestUtil.nextLong());

		newCommerceTaxRateAddressRel.setZip(RandomTestUtil.randomString());

		newCommerceTaxRateAddressRel.setRate(RandomTestUtil.nextDouble());

		_commerceTaxRateAddressRels.add(_persistence.update(
				newCommerceTaxRateAddressRel));

		CommerceTaxRateAddressRel existingCommerceTaxRateAddressRel = _persistence.findByPrimaryKey(newCommerceTaxRateAddressRel.getPrimaryKey());

		Assert.assertEquals(existingCommerceTaxRateAddressRel.getCommerceTaxRateAddressRelId(),
			newCommerceTaxRateAddressRel.getCommerceTaxRateAddressRelId());
		Assert.assertEquals(existingCommerceTaxRateAddressRel.getGroupId(),
			newCommerceTaxRateAddressRel.getGroupId());
		Assert.assertEquals(existingCommerceTaxRateAddressRel.getCompanyId(),
			newCommerceTaxRateAddressRel.getCompanyId());
		Assert.assertEquals(existingCommerceTaxRateAddressRel.getUserId(),
			newCommerceTaxRateAddressRel.getUserId());
		Assert.assertEquals(existingCommerceTaxRateAddressRel.getUserName(),
			newCommerceTaxRateAddressRel.getUserName());
		Assert.assertEquals(Time.getShortTimestamp(
				existingCommerceTaxRateAddressRel.getCreateDate()),
			Time.getShortTimestamp(newCommerceTaxRateAddressRel.getCreateDate()));
		Assert.assertEquals(Time.getShortTimestamp(
				existingCommerceTaxRateAddressRel.getModifiedDate()),
			Time.getShortTimestamp(
				newCommerceTaxRateAddressRel.getModifiedDate()));
		Assert.assertEquals(existingCommerceTaxRateAddressRel.getCommerceTaxMethodId(),
			newCommerceTaxRateAddressRel.getCommerceTaxMethodId());
		Assert.assertEquals(existingCommerceTaxRateAddressRel.getCommerceTaxFixedRateId(),
			newCommerceTaxRateAddressRel.getCommerceTaxFixedRateId());
		Assert.assertEquals(existingCommerceTaxRateAddressRel.getCommerceCountryId(),
			newCommerceTaxRateAddressRel.getCommerceCountryId());
		Assert.assertEquals(existingCommerceTaxRateAddressRel.getCommerceRegionId(),
			newCommerceTaxRateAddressRel.getCommerceRegionId());
		Assert.assertEquals(existingCommerceTaxRateAddressRel.getZip(),
			newCommerceTaxRateAddressRel.getZip());
		AssertUtils.assertEquals(existingCommerceTaxRateAddressRel.getRate(),
			newCommerceTaxRateAddressRel.getRate());
	}

	@Test
	public void testCountByCommerceTaxMethodId() throws Exception {
		_persistence.countByCommerceTaxMethodId(RandomTestUtil.nextLong());

		_persistence.countByCommerceTaxMethodId(0L);
	}

	@Test
	public void testCountBycommerceTaxFixedRateId() throws Exception {
		_persistence.countBycommerceTaxFixedRateId(RandomTestUtil.nextLong());

		_persistence.countBycommerceTaxFixedRateId(0L);
	}

	@Test
	public void testFindByPrimaryKeyExisting() throws Exception {
		CommerceTaxRateAddressRel newCommerceTaxRateAddressRel = addCommerceTaxRateAddressRel();

		CommerceTaxRateAddressRel existingCommerceTaxRateAddressRel = _persistence.findByPrimaryKey(newCommerceTaxRateAddressRel.getPrimaryKey());

		Assert.assertEquals(existingCommerceTaxRateAddressRel,
			newCommerceTaxRateAddressRel);
	}

	@Test(expected = NoSuchTaxRateAddressRelException.class)
	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		_persistence.findByPrimaryKey(pk);
	}

	@Test
	public void testFindAll() throws Exception {
		_persistence.findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			getOrderByComparator());
	}

	protected OrderByComparator<CommerceTaxRateAddressRel> getOrderByComparator() {
		return OrderByComparatorFactoryUtil.create("CommerceTaxRateAddressRel",
			"commerceTaxRateAddressRelId", true, "groupId", true, "companyId",
			true, "userId", true, "userName", true, "createDate", true,
			"modifiedDate", true, "commerceTaxMethodId", true,
			"commerceTaxFixedRateId", true, "commerceCountryId", true,
			"commerceRegionId", true, "zip", true, "rate", true);
	}

	@Test
	public void testFetchByPrimaryKeyExisting() throws Exception {
		CommerceTaxRateAddressRel newCommerceTaxRateAddressRel = addCommerceTaxRateAddressRel();

		CommerceTaxRateAddressRel existingCommerceTaxRateAddressRel = _persistence.fetchByPrimaryKey(newCommerceTaxRateAddressRel.getPrimaryKey());

		Assert.assertEquals(existingCommerceTaxRateAddressRel,
			newCommerceTaxRateAddressRel);
	}

	@Test
	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		CommerceTaxRateAddressRel missingCommerceTaxRateAddressRel = _persistence.fetchByPrimaryKey(pk);

		Assert.assertNull(missingCommerceTaxRateAddressRel);
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereAllPrimaryKeysExist()
		throws Exception {
		CommerceTaxRateAddressRel newCommerceTaxRateAddressRel1 = addCommerceTaxRateAddressRel();
		CommerceTaxRateAddressRel newCommerceTaxRateAddressRel2 = addCommerceTaxRateAddressRel();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newCommerceTaxRateAddressRel1.getPrimaryKey());
		primaryKeys.add(newCommerceTaxRateAddressRel2.getPrimaryKey());

		Map<Serializable, CommerceTaxRateAddressRel> commerceTaxRateAddressRels = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(2, commerceTaxRateAddressRels.size());
		Assert.assertEquals(newCommerceTaxRateAddressRel1,
			commerceTaxRateAddressRels.get(
				newCommerceTaxRateAddressRel1.getPrimaryKey()));
		Assert.assertEquals(newCommerceTaxRateAddressRel2,
			commerceTaxRateAddressRels.get(
				newCommerceTaxRateAddressRel2.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereNoPrimaryKeysExist()
		throws Exception {
		long pk1 = RandomTestUtil.nextLong();

		long pk2 = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(pk1);
		primaryKeys.add(pk2);

		Map<Serializable, CommerceTaxRateAddressRel> commerceTaxRateAddressRels = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(commerceTaxRateAddressRels.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereSomePrimaryKeysExist()
		throws Exception {
		CommerceTaxRateAddressRel newCommerceTaxRateAddressRel = addCommerceTaxRateAddressRel();

		long pk = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newCommerceTaxRateAddressRel.getPrimaryKey());
		primaryKeys.add(pk);

		Map<Serializable, CommerceTaxRateAddressRel> commerceTaxRateAddressRels = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, commerceTaxRateAddressRels.size());
		Assert.assertEquals(newCommerceTaxRateAddressRel,
			commerceTaxRateAddressRels.get(
				newCommerceTaxRateAddressRel.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithNoPrimaryKeys()
		throws Exception {
		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		Map<Serializable, CommerceTaxRateAddressRel> commerceTaxRateAddressRels = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(commerceTaxRateAddressRels.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithOnePrimaryKey()
		throws Exception {
		CommerceTaxRateAddressRel newCommerceTaxRateAddressRel = addCommerceTaxRateAddressRel();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newCommerceTaxRateAddressRel.getPrimaryKey());

		Map<Serializable, CommerceTaxRateAddressRel> commerceTaxRateAddressRels = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, commerceTaxRateAddressRels.size());
		Assert.assertEquals(newCommerceTaxRateAddressRel,
			commerceTaxRateAddressRels.get(
				newCommerceTaxRateAddressRel.getPrimaryKey()));
	}

	@Test
	public void testActionableDynamicQuery() throws Exception {
		final IntegerWrapper count = new IntegerWrapper();

		ActionableDynamicQuery actionableDynamicQuery = CommerceTaxRateAddressRelLocalServiceUtil.getActionableDynamicQuery();

		actionableDynamicQuery.setPerformActionMethod(new ActionableDynamicQuery.PerformActionMethod<CommerceTaxRateAddressRel>() {
				@Override
				public void performAction(
					CommerceTaxRateAddressRel commerceTaxRateAddressRel) {
					Assert.assertNotNull(commerceTaxRateAddressRel);

					count.increment();
				}
			});

		actionableDynamicQuery.performActions();

		Assert.assertEquals(count.getValue(), _persistence.countAll());
	}

	@Test
	public void testDynamicQueryByPrimaryKeyExisting()
		throws Exception {
		CommerceTaxRateAddressRel newCommerceTaxRateAddressRel = addCommerceTaxRateAddressRel();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(CommerceTaxRateAddressRel.class,
				_dynamicQueryClassLoader);

		dynamicQuery.add(RestrictionsFactoryUtil.eq(
				"commerceTaxRateAddressRelId",
				newCommerceTaxRateAddressRel.getCommerceTaxRateAddressRelId()));

		List<CommerceTaxRateAddressRel> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		CommerceTaxRateAddressRel existingCommerceTaxRateAddressRel = result.get(0);

		Assert.assertEquals(existingCommerceTaxRateAddressRel,
			newCommerceTaxRateAddressRel);
	}

	@Test
	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(CommerceTaxRateAddressRel.class,
				_dynamicQueryClassLoader);

		dynamicQuery.add(RestrictionsFactoryUtil.eq(
				"commerceTaxRateAddressRelId", RandomTestUtil.nextLong()));

		List<CommerceTaxRateAddressRel> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testDynamicQueryByProjectionExisting()
		throws Exception {
		CommerceTaxRateAddressRel newCommerceTaxRateAddressRel = addCommerceTaxRateAddressRel();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(CommerceTaxRateAddressRel.class,
				_dynamicQueryClassLoader);

		dynamicQuery.setProjection(ProjectionFactoryUtil.property(
				"commerceTaxRateAddressRelId"));

		Object newCommerceTaxRateAddressRelId = newCommerceTaxRateAddressRel.getCommerceTaxRateAddressRelId();

		dynamicQuery.add(RestrictionsFactoryUtil.in(
				"commerceTaxRateAddressRelId",
				new Object[] { newCommerceTaxRateAddressRelId }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		Object existingCommerceTaxRateAddressRelId = result.get(0);

		Assert.assertEquals(existingCommerceTaxRateAddressRelId,
			newCommerceTaxRateAddressRelId);
	}

	@Test
	public void testDynamicQueryByProjectionMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(CommerceTaxRateAddressRel.class,
				_dynamicQueryClassLoader);

		dynamicQuery.setProjection(ProjectionFactoryUtil.property(
				"commerceTaxRateAddressRelId"));

		dynamicQuery.add(RestrictionsFactoryUtil.in(
				"commerceTaxRateAddressRelId",
				new Object[] { RandomTestUtil.nextLong() }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	protected CommerceTaxRateAddressRel addCommerceTaxRateAddressRel()
		throws Exception {
		long pk = RandomTestUtil.nextLong();

		CommerceTaxRateAddressRel commerceTaxRateAddressRel = _persistence.create(pk);

		commerceTaxRateAddressRel.setGroupId(RandomTestUtil.nextLong());

		commerceTaxRateAddressRel.setCompanyId(RandomTestUtil.nextLong());

		commerceTaxRateAddressRel.setUserId(RandomTestUtil.nextLong());

		commerceTaxRateAddressRel.setUserName(RandomTestUtil.randomString());

		commerceTaxRateAddressRel.setCreateDate(RandomTestUtil.nextDate());

		commerceTaxRateAddressRel.setModifiedDate(RandomTestUtil.nextDate());

		commerceTaxRateAddressRel.setCommerceTaxMethodId(RandomTestUtil.nextLong());

		commerceTaxRateAddressRel.setCommerceTaxFixedRateId(RandomTestUtil.nextLong());

		commerceTaxRateAddressRel.setCommerceCountryId(RandomTestUtil.nextLong());

		commerceTaxRateAddressRel.setCommerceRegionId(RandomTestUtil.nextLong());

		commerceTaxRateAddressRel.setZip(RandomTestUtil.randomString());

		commerceTaxRateAddressRel.setRate(RandomTestUtil.nextDouble());

		_commerceTaxRateAddressRels.add(_persistence.update(
				commerceTaxRateAddressRel));

		return commerceTaxRateAddressRel;
	}

	private List<CommerceTaxRateAddressRel> _commerceTaxRateAddressRels = new ArrayList<CommerceTaxRateAddressRel>();
	private CommerceTaxRateAddressRelPersistence _persistence;
	private ClassLoader _dynamicQueryClassLoader;
}