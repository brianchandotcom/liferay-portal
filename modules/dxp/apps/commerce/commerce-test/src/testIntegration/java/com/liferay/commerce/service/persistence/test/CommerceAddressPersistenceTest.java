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

import com.liferay.commerce.exception.NoSuchAddressException;
import com.liferay.commerce.model.CommerceAddress;
import com.liferay.commerce.service.CommerceAddressLocalServiceUtil;
import com.liferay.commerce.service.persistence.CommerceAddressPersistence;
import com.liferay.commerce.service.persistence.CommerceAddressUtil;

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
public class CommerceAddressPersistenceTest {
	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule = new AggregateTestRule(new LiferayIntegrationTestRule(),
			PersistenceTestRule.INSTANCE,
			new TransactionalTestRule(Propagation.REQUIRED,
				"com.liferay.commerce.service"));

	@Before
	public void setUp() {
		_persistence = CommerceAddressUtil.getPersistence();

		Class<?> clazz = _persistence.getClass();

		_dynamicQueryClassLoader = clazz.getClassLoader();
	}

	@After
	public void tearDown() throws Exception {
		Iterator<CommerceAddress> iterator = _commerceAddresses.iterator();

		while (iterator.hasNext()) {
			_persistence.remove(iterator.next());

			iterator.remove();
		}
	}

	@Test
	public void testCreate() throws Exception {
		long pk = RandomTestUtil.nextLong();

		CommerceAddress commerceAddress = _persistence.create(pk);

		Assert.assertNotNull(commerceAddress);

		Assert.assertEquals(commerceAddress.getPrimaryKey(), pk);
	}

	@Test
	public void testRemove() throws Exception {
		CommerceAddress newCommerceAddress = addCommerceAddress();

		_persistence.remove(newCommerceAddress);

		CommerceAddress existingCommerceAddress = _persistence.fetchByPrimaryKey(newCommerceAddress.getPrimaryKey());

		Assert.assertNull(existingCommerceAddress);
	}

	@Test
	public void testUpdateNew() throws Exception {
		addCommerceAddress();
	}

	@Test
	public void testUpdateExisting() throws Exception {
		long pk = RandomTestUtil.nextLong();

		CommerceAddress newCommerceAddress = _persistence.create(pk);

		newCommerceAddress.setGroupId(RandomTestUtil.nextLong());

		newCommerceAddress.setCompanyId(RandomTestUtil.nextLong());

		newCommerceAddress.setUserId(RandomTestUtil.nextLong());

		newCommerceAddress.setUserName(RandomTestUtil.randomString());

		newCommerceAddress.setCreateDate(RandomTestUtil.nextDate());

		newCommerceAddress.setModifiedDate(RandomTestUtil.nextDate());

		newCommerceAddress.setClassNameId(RandomTestUtil.nextLong());

		newCommerceAddress.setClassPK(RandomTestUtil.nextLong());

		newCommerceAddress.setName(RandomTestUtil.randomString());

		newCommerceAddress.setDescription(RandomTestUtil.randomString());

		newCommerceAddress.setStreet1(RandomTestUtil.randomString());

		newCommerceAddress.setStreet2(RandomTestUtil.randomString());

		newCommerceAddress.setStreet3(RandomTestUtil.randomString());

		newCommerceAddress.setCity(RandomTestUtil.randomString());

		newCommerceAddress.setZip(RandomTestUtil.randomString());

		newCommerceAddress.setCommerceRegionId(RandomTestUtil.nextLong());

		newCommerceAddress.setCommerceCountryId(RandomTestUtil.nextLong());

		newCommerceAddress.setLatitude(RandomTestUtil.nextDouble());

		newCommerceAddress.setLongitude(RandomTestUtil.nextDouble());

		newCommerceAddress.setPhoneNumber(RandomTestUtil.randomString());

		newCommerceAddress.setDefaultBilling(RandomTestUtil.randomBoolean());

		newCommerceAddress.setDefaultShipping(RandomTestUtil.randomBoolean());

		_commerceAddresses.add(_persistence.update(newCommerceAddress));

		CommerceAddress existingCommerceAddress = _persistence.findByPrimaryKey(newCommerceAddress.getPrimaryKey());

		Assert.assertEquals(existingCommerceAddress.getCommerceAddressId(),
			newCommerceAddress.getCommerceAddressId());
		Assert.assertEquals(existingCommerceAddress.getGroupId(),
			newCommerceAddress.getGroupId());
		Assert.assertEquals(existingCommerceAddress.getCompanyId(),
			newCommerceAddress.getCompanyId());
		Assert.assertEquals(existingCommerceAddress.getUserId(),
			newCommerceAddress.getUserId());
		Assert.assertEquals(existingCommerceAddress.getUserName(),
			newCommerceAddress.getUserName());
		Assert.assertEquals(Time.getShortTimestamp(
				existingCommerceAddress.getCreateDate()),
			Time.getShortTimestamp(newCommerceAddress.getCreateDate()));
		Assert.assertEquals(Time.getShortTimestamp(
				existingCommerceAddress.getModifiedDate()),
			Time.getShortTimestamp(newCommerceAddress.getModifiedDate()));
		Assert.assertEquals(existingCommerceAddress.getClassNameId(),
			newCommerceAddress.getClassNameId());
		Assert.assertEquals(existingCommerceAddress.getClassPK(),
			newCommerceAddress.getClassPK());
		Assert.assertEquals(existingCommerceAddress.getName(),
			newCommerceAddress.getName());
		Assert.assertEquals(existingCommerceAddress.getDescription(),
			newCommerceAddress.getDescription());
		Assert.assertEquals(existingCommerceAddress.getStreet1(),
			newCommerceAddress.getStreet1());
		Assert.assertEquals(existingCommerceAddress.getStreet2(),
			newCommerceAddress.getStreet2());
		Assert.assertEquals(existingCommerceAddress.getStreet3(),
			newCommerceAddress.getStreet3());
		Assert.assertEquals(existingCommerceAddress.getCity(),
			newCommerceAddress.getCity());
		Assert.assertEquals(existingCommerceAddress.getZip(),
			newCommerceAddress.getZip());
		Assert.assertEquals(existingCommerceAddress.getCommerceRegionId(),
			newCommerceAddress.getCommerceRegionId());
		Assert.assertEquals(existingCommerceAddress.getCommerceCountryId(),
			newCommerceAddress.getCommerceCountryId());
		AssertUtils.assertEquals(existingCommerceAddress.getLatitude(),
			newCommerceAddress.getLatitude());
		AssertUtils.assertEquals(existingCommerceAddress.getLongitude(),
			newCommerceAddress.getLongitude());
		Assert.assertEquals(existingCommerceAddress.getPhoneNumber(),
			newCommerceAddress.getPhoneNumber());
		Assert.assertEquals(existingCommerceAddress.isDefaultBilling(),
			newCommerceAddress.isDefaultBilling());
		Assert.assertEquals(existingCommerceAddress.isDefaultShipping(),
			newCommerceAddress.isDefaultShipping());
	}

	@Test
	public void testCountByCommerceRegionId() throws Exception {
		_persistence.countByCommerceRegionId(RandomTestUtil.nextLong());

		_persistence.countByCommerceRegionId(0L);
	}

	@Test
	public void testCountByCommerceCountryId() throws Exception {
		_persistence.countByCommerceCountryId(RandomTestUtil.nextLong());

		_persistence.countByCommerceCountryId(0L);
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
	public void testCountByG_C_C_DB() throws Exception {
		_persistence.countByG_C_C_DB(RandomTestUtil.nextLong(),
			RandomTestUtil.nextLong(), RandomTestUtil.nextLong(),
			RandomTestUtil.randomBoolean());

		_persistence.countByG_C_C_DB(0L, 0L, 0L, RandomTestUtil.randomBoolean());
	}

	@Test
	public void testCountByG_C_C_DS() throws Exception {
		_persistence.countByG_C_C_DS(RandomTestUtil.nextLong(),
			RandomTestUtil.nextLong(), RandomTestUtil.nextLong(),
			RandomTestUtil.randomBoolean());

		_persistence.countByG_C_C_DS(0L, 0L, 0L, RandomTestUtil.randomBoolean());
	}

	@Test
	public void testFindByPrimaryKeyExisting() throws Exception {
		CommerceAddress newCommerceAddress = addCommerceAddress();

		CommerceAddress existingCommerceAddress = _persistence.findByPrimaryKey(newCommerceAddress.getPrimaryKey());

		Assert.assertEquals(existingCommerceAddress, newCommerceAddress);
	}

	@Test(expected = NoSuchAddressException.class)
	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		_persistence.findByPrimaryKey(pk);
	}

	@Test
	public void testFindAll() throws Exception {
		_persistence.findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			getOrderByComparator());
	}

	protected OrderByComparator<CommerceAddress> getOrderByComparator() {
		return OrderByComparatorFactoryUtil.create("CommerceAddress",
			"commerceAddressId", true, "groupId", true, "companyId", true,
			"userId", true, "userName", true, "createDate", true,
			"modifiedDate", true, "classNameId", true, "classPK", true, "name",
			true, "description", true, "street1", true, "street2", true,
			"street3", true, "city", true, "zip", true, "commerceRegionId",
			true, "commerceCountryId", true, "latitude", true, "longitude",
			true, "phoneNumber", true, "defaultBilling", true,
			"defaultShipping", true);
	}

	@Test
	public void testFetchByPrimaryKeyExisting() throws Exception {
		CommerceAddress newCommerceAddress = addCommerceAddress();

		CommerceAddress existingCommerceAddress = _persistence.fetchByPrimaryKey(newCommerceAddress.getPrimaryKey());

		Assert.assertEquals(existingCommerceAddress, newCommerceAddress);
	}

	@Test
	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		CommerceAddress missingCommerceAddress = _persistence.fetchByPrimaryKey(pk);

		Assert.assertNull(missingCommerceAddress);
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereAllPrimaryKeysExist()
		throws Exception {
		CommerceAddress newCommerceAddress1 = addCommerceAddress();
		CommerceAddress newCommerceAddress2 = addCommerceAddress();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newCommerceAddress1.getPrimaryKey());
		primaryKeys.add(newCommerceAddress2.getPrimaryKey());

		Map<Serializable, CommerceAddress> commerceAddresses = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(2, commerceAddresses.size());
		Assert.assertEquals(newCommerceAddress1,
			commerceAddresses.get(newCommerceAddress1.getPrimaryKey()));
		Assert.assertEquals(newCommerceAddress2,
			commerceAddresses.get(newCommerceAddress2.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereNoPrimaryKeysExist()
		throws Exception {
		long pk1 = RandomTestUtil.nextLong();

		long pk2 = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(pk1);
		primaryKeys.add(pk2);

		Map<Serializable, CommerceAddress> commerceAddresses = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(commerceAddresses.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereSomePrimaryKeysExist()
		throws Exception {
		CommerceAddress newCommerceAddress = addCommerceAddress();

		long pk = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newCommerceAddress.getPrimaryKey());
		primaryKeys.add(pk);

		Map<Serializable, CommerceAddress> commerceAddresses = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, commerceAddresses.size());
		Assert.assertEquals(newCommerceAddress,
			commerceAddresses.get(newCommerceAddress.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithNoPrimaryKeys()
		throws Exception {
		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		Map<Serializable, CommerceAddress> commerceAddresses = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(commerceAddresses.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithOnePrimaryKey()
		throws Exception {
		CommerceAddress newCommerceAddress = addCommerceAddress();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newCommerceAddress.getPrimaryKey());

		Map<Serializable, CommerceAddress> commerceAddresses = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, commerceAddresses.size());
		Assert.assertEquals(newCommerceAddress,
			commerceAddresses.get(newCommerceAddress.getPrimaryKey()));
	}

	@Test
	public void testActionableDynamicQuery() throws Exception {
		final IntegerWrapper count = new IntegerWrapper();

		ActionableDynamicQuery actionableDynamicQuery = CommerceAddressLocalServiceUtil.getActionableDynamicQuery();

		actionableDynamicQuery.setPerformActionMethod(new ActionableDynamicQuery.PerformActionMethod<CommerceAddress>() {
				@Override
				public void performAction(CommerceAddress commerceAddress) {
					Assert.assertNotNull(commerceAddress);

					count.increment();
				}
			});

		actionableDynamicQuery.performActions();

		Assert.assertEquals(count.getValue(), _persistence.countAll());
	}

	@Test
	public void testDynamicQueryByPrimaryKeyExisting()
		throws Exception {
		CommerceAddress newCommerceAddress = addCommerceAddress();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(CommerceAddress.class,
				_dynamicQueryClassLoader);

		dynamicQuery.add(RestrictionsFactoryUtil.eq("commerceAddressId",
				newCommerceAddress.getCommerceAddressId()));

		List<CommerceAddress> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		CommerceAddress existingCommerceAddress = result.get(0);

		Assert.assertEquals(existingCommerceAddress, newCommerceAddress);
	}

	@Test
	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(CommerceAddress.class,
				_dynamicQueryClassLoader);

		dynamicQuery.add(RestrictionsFactoryUtil.eq("commerceAddressId",
				RandomTestUtil.nextLong()));

		List<CommerceAddress> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testDynamicQueryByProjectionExisting()
		throws Exception {
		CommerceAddress newCommerceAddress = addCommerceAddress();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(CommerceAddress.class,
				_dynamicQueryClassLoader);

		dynamicQuery.setProjection(ProjectionFactoryUtil.property(
				"commerceAddressId"));

		Object newCommerceAddressId = newCommerceAddress.getCommerceAddressId();

		dynamicQuery.add(RestrictionsFactoryUtil.in("commerceAddressId",
				new Object[] { newCommerceAddressId }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		Object existingCommerceAddressId = result.get(0);

		Assert.assertEquals(existingCommerceAddressId, newCommerceAddressId);
	}

	@Test
	public void testDynamicQueryByProjectionMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(CommerceAddress.class,
				_dynamicQueryClassLoader);

		dynamicQuery.setProjection(ProjectionFactoryUtil.property(
				"commerceAddressId"));

		dynamicQuery.add(RestrictionsFactoryUtil.in("commerceAddressId",
				new Object[] { RandomTestUtil.nextLong() }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	protected CommerceAddress addCommerceAddress() throws Exception {
		long pk = RandomTestUtil.nextLong();

		CommerceAddress commerceAddress = _persistence.create(pk);

		commerceAddress.setGroupId(RandomTestUtil.nextLong());

		commerceAddress.setCompanyId(RandomTestUtil.nextLong());

		commerceAddress.setUserId(RandomTestUtil.nextLong());

		commerceAddress.setUserName(RandomTestUtil.randomString());

		commerceAddress.setCreateDate(RandomTestUtil.nextDate());

		commerceAddress.setModifiedDate(RandomTestUtil.nextDate());

		commerceAddress.setClassNameId(RandomTestUtil.nextLong());

		commerceAddress.setClassPK(RandomTestUtil.nextLong());

		commerceAddress.setName(RandomTestUtil.randomString());

		commerceAddress.setDescription(RandomTestUtil.randomString());

		commerceAddress.setStreet1(RandomTestUtil.randomString());

		commerceAddress.setStreet2(RandomTestUtil.randomString());

		commerceAddress.setStreet3(RandomTestUtil.randomString());

		commerceAddress.setCity(RandomTestUtil.randomString());

		commerceAddress.setZip(RandomTestUtil.randomString());

		commerceAddress.setCommerceRegionId(RandomTestUtil.nextLong());

		commerceAddress.setCommerceCountryId(RandomTestUtil.nextLong());

		commerceAddress.setLatitude(RandomTestUtil.nextDouble());

		commerceAddress.setLongitude(RandomTestUtil.nextDouble());

		commerceAddress.setPhoneNumber(RandomTestUtil.randomString());

		commerceAddress.setDefaultBilling(RandomTestUtil.randomBoolean());

		commerceAddress.setDefaultShipping(RandomTestUtil.randomBoolean());

		_commerceAddresses.add(_persistence.update(commerceAddress));

		return commerceAddress;
	}

	private List<CommerceAddress> _commerceAddresses = new ArrayList<CommerceAddress>();
	private CommerceAddressPersistence _persistence;
	private ClassLoader _dynamicQueryClassLoader;
}