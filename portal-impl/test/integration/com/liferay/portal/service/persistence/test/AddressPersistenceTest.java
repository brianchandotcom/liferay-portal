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

package com.liferay.portal.service.persistence.test;

import com.liferay.portal.NoSuchAddressException;
import com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQueryFactoryUtil;
import com.liferay.portal.kernel.dao.orm.ProjectionFactoryUtil;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.RestrictionsFactoryUtil;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.TransactionalTestRule;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.transaction.Propagation;
import com.liferay.portal.kernel.util.IntegerWrapper;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.OrderByComparatorFactoryUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Time;
import com.liferay.portal.model.Address;
import com.liferay.portal.service.AddressLocalServiceUtil;
import com.liferay.portal.service.persistence.AddressPersistence;
import com.liferay.portal.service.persistence.AddressUtil;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PersistenceTestRule;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

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
public class AddressPersistenceTest {
	@Rule
	public final AggregateTestRule aggregateTestRule = new AggregateTestRule(new LiferayIntegrationTestRule(),
			PersistenceTestRule.INSTANCE,
			new TransactionalTestRule(Propagation.REQUIRED));

	@Before
	public void setUp() {
		_persistence = AddressUtil.getPersistence();

		Class<?> clazz = _persistence.getClass();

		_dynamicQueryClassLoader = clazz.getClassLoader();
	}

	@After
	public void tearDown() throws Exception {
		Iterator<Address> iterator = _addresses.iterator();

		while (iterator.hasNext()) {
			_persistence.remove(iterator.next());

			iterator.remove();
		}
	}

	@Test
	public void testCreate() throws Exception {
		long pk = RandomTestUtil.nextLong();

		Address address = _persistence.create(pk);

		Assert.assertNotNull(address);

		Assert.assertEquals(address.getPrimaryKey(), pk);
	}

	@Test
	public void testRemove() throws Exception {
		Address newAddress = addAddress();

		_persistence.remove(newAddress);

		Address existingAddress = _persistence.fetchByPrimaryKey(newAddress.getPrimaryKey());

		Assert.assertNull(existingAddress);
	}

	@Test
	public void testUpdateNew() throws Exception {
		addAddress();
	}

	@Test
	public void testUpdateExisting() throws Exception {
		long pk = RandomTestUtil.nextLong();

		Address newAddress = _persistence.create(pk);

		newAddress.setMvccVersion(RandomTestUtil.nextLong());

		newAddress.setUuid(RandomTestUtil.randomString());

		newAddress.setCompanyId(RandomTestUtil.nextLong());

		newAddress.setUserId(RandomTestUtil.nextLong());

		newAddress.setUserName(RandomTestUtil.randomString());

		newAddress.setCreateDate(RandomTestUtil.nextDate());

		newAddress.setModifiedDate(RandomTestUtil.nextDate());

		newAddress.setClassNameId(RandomTestUtil.nextLong());

		newAddress.setClassPK(RandomTestUtil.nextLong());

		newAddress.setStreet1(RandomTestUtil.randomString());

		newAddress.setStreet2(RandomTestUtil.randomString());

		newAddress.setStreet3(RandomTestUtil.randomString());

		newAddress.setCity(RandomTestUtil.randomString());

		newAddress.setZip(RandomTestUtil.randomString());

		newAddress.setRegionId(RandomTestUtil.nextLong());

		newAddress.setCountryId(RandomTestUtil.nextLong());

		newAddress.setTypeId(RandomTestUtil.nextLong());

		newAddress.setMailing(RandomTestUtil.randomBoolean());

		newAddress.setPrimary(RandomTestUtil.randomBoolean());

		_addresses.add(_persistence.update(newAddress));

		Address existingAddress = _persistence.findByPrimaryKey(newAddress.getPrimaryKey());

		Assert.assertEquals(existingAddress.getMvccVersion(),
			newAddress.getMvccVersion());
		Assert.assertEquals(existingAddress.getUuid(), newAddress.getUuid());
		Assert.assertEquals(existingAddress.getAddressId(),
			newAddress.getAddressId());
		Assert.assertEquals(existingAddress.getCompanyId(),
			newAddress.getCompanyId());
		Assert.assertEquals(existingAddress.getUserId(), newAddress.getUserId());
		Assert.assertEquals(existingAddress.getUserName(),
			newAddress.getUserName());
		Assert.assertEquals(Time.getShortTimestamp(
				existingAddress.getCreateDate()),
			Time.getShortTimestamp(newAddress.getCreateDate()));
		Assert.assertEquals(Time.getShortTimestamp(
				existingAddress.getModifiedDate()),
			Time.getShortTimestamp(newAddress.getModifiedDate()));
		Assert.assertEquals(existingAddress.getClassNameId(),
			newAddress.getClassNameId());
		Assert.assertEquals(existingAddress.getClassPK(),
			newAddress.getClassPK());
		Assert.assertEquals(existingAddress.getStreet1(),
			newAddress.getStreet1());
		Assert.assertEquals(existingAddress.getStreet2(),
			newAddress.getStreet2());
		Assert.assertEquals(existingAddress.getStreet3(),
			newAddress.getStreet3());
		Assert.assertEquals(existingAddress.getCity(), newAddress.getCity());
		Assert.assertEquals(existingAddress.getZip(), newAddress.getZip());
		Assert.assertEquals(existingAddress.getRegionId(),
			newAddress.getRegionId());
		Assert.assertEquals(existingAddress.getCountryId(),
			newAddress.getCountryId());
		Assert.assertEquals(existingAddress.getTypeId(), newAddress.getTypeId());
		Assert.assertEquals(existingAddress.getMailing(),
			newAddress.getMailing());
		Assert.assertEquals(existingAddress.getPrimary(),
			newAddress.getPrimary());
	}

	@Test
	public void testCountByUuid() throws Exception {
		_persistence.countByUuid(StringPool.BLANK);

		_persistence.countByUuid(StringPool.NULL);

		_persistence.countByUuid((String)null);
	}

	@Test
	public void testCountByUuid_C() throws Exception {
		_persistence.countByUuid_C(StringPool.BLANK, RandomTestUtil.nextLong());

		_persistence.countByUuid_C(StringPool.NULL, 0L);

		_persistence.countByUuid_C((String)null, 0L);
	}

	@Test
	public void testCountByCompanyId() throws Exception {
		_persistence.countByCompanyId(RandomTestUtil.nextLong());

		_persistence.countByCompanyId(0L);
	}

	@Test
	public void testCountByUserId() throws Exception {
		_persistence.countByUserId(RandomTestUtil.nextLong());

		_persistence.countByUserId(0L);
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
	public void testCountByC_C_C_M() throws Exception {
		_persistence.countByC_C_C_M(RandomTestUtil.nextLong(),
			RandomTestUtil.nextLong(), RandomTestUtil.nextLong(),
			RandomTestUtil.randomBoolean());

		_persistence.countByC_C_C_M(0L, 0L, 0L, RandomTestUtil.randomBoolean());
	}

	@Test
	public void testCountByC_C_C_P() throws Exception {
		_persistence.countByC_C_C_P(RandomTestUtil.nextLong(),
			RandomTestUtil.nextLong(), RandomTestUtil.nextLong(),
			RandomTestUtil.randomBoolean());

		_persistence.countByC_C_C_P(0L, 0L, 0L, RandomTestUtil.randomBoolean());
	}

	@Test
	public void testFindByPrimaryKeyExisting() throws Exception {
		Address newAddress = addAddress();

		Address existingAddress = _persistence.findByPrimaryKey(newAddress.getPrimaryKey());

		Assert.assertEquals(existingAddress, newAddress);
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

	protected OrderByComparator<Address> getOrderByComparator() {
		return OrderByComparatorFactoryUtil.create("Address", "mvccVersion",
			true, "uuid", true, "addressId", true, "companyId", true, "userId",
			true, "userName", true, "createDate", true, "modifiedDate", true,
			"classNameId", true, "classPK", true, "street1", true, "street2",
			true, "street3", true, "city", true, "zip", true, "regionId", true,
			"countryId", true, "typeId", true, "mailing", true, "primary", true);
	}

	@Test
	public void testFetchByPrimaryKeyExisting() throws Exception {
		Address newAddress = addAddress();

		Address existingAddress = _persistence.fetchByPrimaryKey(newAddress.getPrimaryKey());

		Assert.assertEquals(existingAddress, newAddress);
	}

	@Test
	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		Address missingAddress = _persistence.fetchByPrimaryKey(pk);

		Assert.assertNull(missingAddress);
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereAllPrimaryKeysExist()
		throws Exception {
		Address newAddress1 = addAddress();
		Address newAddress2 = addAddress();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newAddress1.getPrimaryKey());
		primaryKeys.add(newAddress2.getPrimaryKey());

		Map<Serializable, Address> addresses = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(2, addresses.size());
		Assert.assertEquals(newAddress1,
			addresses.get(newAddress1.getPrimaryKey()));
		Assert.assertEquals(newAddress2,
			addresses.get(newAddress2.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereNoPrimaryKeysExist()
		throws Exception {
		long pk1 = RandomTestUtil.nextLong();

		long pk2 = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(pk1);
		primaryKeys.add(pk2);

		Map<Serializable, Address> addresses = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(addresses.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereSomePrimaryKeysExist()
		throws Exception {
		Address newAddress = addAddress();

		long pk = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newAddress.getPrimaryKey());
		primaryKeys.add(pk);

		Map<Serializable, Address> addresses = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, addresses.size());
		Assert.assertEquals(newAddress,
			addresses.get(newAddress.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithNoPrimaryKeys()
		throws Exception {
		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		Map<Serializable, Address> addresses = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(addresses.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithOnePrimaryKey()
		throws Exception {
		Address newAddress = addAddress();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newAddress.getPrimaryKey());

		Map<Serializable, Address> addresses = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, addresses.size());
		Assert.assertEquals(newAddress,
			addresses.get(newAddress.getPrimaryKey()));
	}

	@Test
	public void testActionableDynamicQuery() throws Exception {
		final IntegerWrapper count = new IntegerWrapper();

		ActionableDynamicQuery actionableDynamicQuery = AddressLocalServiceUtil.getActionableDynamicQuery();

		actionableDynamicQuery.setPerformActionMethod(new ActionableDynamicQuery.PerformActionMethod() {
				@Override
				public void performAction(Object object) {
					Address address = (Address)object;

					Assert.assertNotNull(address);

					count.increment();
				}
			});

		actionableDynamicQuery.performActions();

		Assert.assertEquals(count.getValue(), _persistence.countAll());
	}

	@Test
	public void testDynamicQueryByPrimaryKeyExisting()
		throws Exception {
		Address newAddress = addAddress();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(Address.class,
				_dynamicQueryClassLoader);

		dynamicQuery.add(RestrictionsFactoryUtil.eq("addressId",
				newAddress.getAddressId()));

		List<Address> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		Address existingAddress = result.get(0);

		Assert.assertEquals(existingAddress, newAddress);
	}

	@Test
	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(Address.class,
				_dynamicQueryClassLoader);

		dynamicQuery.add(RestrictionsFactoryUtil.eq("addressId",
				RandomTestUtil.nextLong()));

		List<Address> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testDynamicQueryByProjectionExisting()
		throws Exception {
		Address newAddress = addAddress();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(Address.class,
				_dynamicQueryClassLoader);

		dynamicQuery.setProjection(ProjectionFactoryUtil.property("addressId"));

		Object newAddressId = newAddress.getAddressId();

		dynamicQuery.add(RestrictionsFactoryUtil.in("addressId",
				new Object[] { newAddressId }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		Object existingAddressId = result.get(0);

		Assert.assertEquals(existingAddressId, newAddressId);
	}

	@Test
	public void testDynamicQueryByProjectionMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(Address.class,
				_dynamicQueryClassLoader);

		dynamicQuery.setProjection(ProjectionFactoryUtil.property("addressId"));

		dynamicQuery.add(RestrictionsFactoryUtil.in("addressId",
				new Object[] { RandomTestUtil.nextLong() }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	protected Address addAddress() throws Exception {
		long pk = RandomTestUtil.nextLong();

		Address address = _persistence.create(pk);

		address.setMvccVersion(RandomTestUtil.nextLong());

		address.setUuid(RandomTestUtil.randomString());

		address.setCompanyId(RandomTestUtil.nextLong());

		address.setUserId(RandomTestUtil.nextLong());

		address.setUserName(RandomTestUtil.randomString());

		address.setCreateDate(RandomTestUtil.nextDate());

		address.setModifiedDate(RandomTestUtil.nextDate());

		address.setClassNameId(RandomTestUtil.nextLong());

		address.setClassPK(RandomTestUtil.nextLong());

		address.setStreet1(RandomTestUtil.randomString());

		address.setStreet2(RandomTestUtil.randomString());

		address.setStreet3(RandomTestUtil.randomString());

		address.setCity(RandomTestUtil.randomString());

		address.setZip(RandomTestUtil.randomString());

		address.setRegionId(RandomTestUtil.nextLong());

		address.setCountryId(RandomTestUtil.nextLong());

		address.setTypeId(RandomTestUtil.nextLong());

		address.setMailing(RandomTestUtil.randomBoolean());

		address.setPrimary(RandomTestUtil.randomBoolean());

		_addresses.add(_persistence.update(address));

		return address;
	}

	private List<Address> _addresses = new ArrayList<Address>();
	private AddressPersistence _persistence;
	private ClassLoader _dynamicQueryClassLoader;
}