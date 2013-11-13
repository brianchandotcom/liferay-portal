/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.service.persistence;

import com.liferay.portal.NoSuchAddressException;
import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQueryFactoryUtil;
import com.liferay.portal.kernel.dao.orm.ProjectionFactoryUtil;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.RestrictionsFactoryUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.test.ExecutionTestListeners;
import com.liferay.portal.kernel.util.IntegerWrapper;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.OrderByComparatorFactoryUtil;
import com.liferay.portal.kernel.util.Time;
import com.liferay.portal.model.Address;
import com.liferay.portal.service.ServiceTestUtil;
import com.liferay.portal.service.persistence.BasePersistence;
import com.liferay.portal.service.persistence.PersistenceExecutionTestListener;
import com.liferay.portal.test.LiferayPersistenceIntegrationJUnitTestRunner;
import com.liferay.portal.test.persistence.TransactionalPersistenceAdvice;

import org.junit.After;
import org.junit.Assert;
import org.junit.Test;

import org.junit.runner.RunWith;

import java.io.Serializable;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author Brian Wing Shun Chan
 */
@ExecutionTestListeners(listeners =  {
	PersistenceExecutionTestListener.class})
@RunWith(LiferayPersistenceIntegrationJUnitTestRunner.class)
public class AddressPersistenceTest {
	@After
	public void tearDown() throws Exception {
		Map<Serializable, BasePersistence<?>> basePersistences = _transactionalPersistenceAdvice.getBasePersistences();

		Set<Serializable> primaryKeys = basePersistences.keySet();

		for (Serializable primaryKey : primaryKeys) {
			BasePersistence<?> basePersistence = basePersistences.get(primaryKey);

			try {
				basePersistence.remove(primaryKey);
			}
			catch (Exception e) {
				if (_log.isDebugEnabled()) {
					_log.debug("The model with primary key " + primaryKey +
						" was already deleted");
				}
			}
		}

		_transactionalPersistenceAdvice.reset();
	}

	@Test
	public void testCreate() throws Exception {
		long pk = ServiceTestUtil.nextLong();

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
		long pk = ServiceTestUtil.nextLong();

		Address newAddress = _persistence.create(pk);

		newAddress.setUuid(ServiceTestUtil.randomString());

		newAddress.setCompanyId(ServiceTestUtil.nextLong());

		newAddress.setUserId(ServiceTestUtil.nextLong());

		newAddress.setUserName(ServiceTestUtil.randomString());

		newAddress.setCreateDate(ServiceTestUtil.nextDate());

		newAddress.setModifiedDate(ServiceTestUtil.nextDate());

		newAddress.setClassNameId(ServiceTestUtil.nextLong());

		newAddress.setClassPK(ServiceTestUtil.nextLong());

		newAddress.setStreet1(ServiceTestUtil.randomString());

		newAddress.setStreet2(ServiceTestUtil.randomString());

		newAddress.setStreet3(ServiceTestUtil.randomString());

		newAddress.setCity(ServiceTestUtil.randomString());

		newAddress.setZip(ServiceTestUtil.randomString());

		newAddress.setRegionId(ServiceTestUtil.nextLong());

		newAddress.setCountryId(ServiceTestUtil.nextLong());

		newAddress.setTypeId(ServiceTestUtil.nextInt());

		newAddress.setMailing(ServiceTestUtil.randomBoolean());

		newAddress.setPrimary(ServiceTestUtil.randomBoolean());

		_persistence.update(newAddress);

		Address existingAddress = _persistence.findByPrimaryKey(newAddress.getPrimaryKey());

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
	public void testFindByPrimaryKeyExisting() throws Exception {
		Address newAddress = addAddress();

		Address existingAddress = _persistence.findByPrimaryKey(newAddress.getPrimaryKey());

		Assert.assertEquals(existingAddress, newAddress);
	}

	@Test
	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = ServiceTestUtil.nextLong();

		try {
			_persistence.findByPrimaryKey(pk);

			Assert.fail("Missing entity did not throw NoSuchAddressException");
		}
		catch (NoSuchAddressException nsee) {
		}
	}

	@Test
	public void testFindAll() throws Exception {
		try {
			_persistence.findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS,
				getOrderByComparator());
		}
		catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	protected OrderByComparator getOrderByComparator() {
		return OrderByComparatorFactoryUtil.create("Address", "uuid", true,
			"addressId", true, "companyId", true, "userId", true, "userName",
			true, "createDate", true, "modifiedDate", true, "classNameId",
			true, "classPK", true, "street1", true, "street2", true, "street3",
			true, "city", true, "zip", true, "regionId", true, "countryId",
			true, "typeId", true, "mailing", true, "primary", true);
	}

	@Test
	public void testFetchByPrimaryKeyExisting() throws Exception {
		Address newAddress = addAddress();

		Address existingAddress = _persistence.fetchByPrimaryKey(newAddress.getPrimaryKey());

		Assert.assertEquals(existingAddress, newAddress);
	}

	@Test
	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = ServiceTestUtil.nextLong();

		Address missingAddress = _persistence.fetchByPrimaryKey(pk);

		Assert.assertNull(missingAddress);
	}

	@Test
	public void testActionableDynamicQuery() throws Exception {
		final IntegerWrapper count = new IntegerWrapper();

		ActionableDynamicQuery actionableDynamicQuery = new AddressActionableDynamicQuery() {
				@Override
				protected void performAction(Object object) {
					Address address = (Address)object;

					Assert.assertNotNull(address);

					count.increment();
				}
			};

		actionableDynamicQuery.performActions();

		Assert.assertEquals(count.getValue(), _persistence.countAll());
	}

	@Test
	public void testDynamicQueryByPrimaryKeyExisting()
		throws Exception {
		Address newAddress = addAddress();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(Address.class,
				Address.class.getClassLoader());

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
				Address.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("addressId",
				ServiceTestUtil.nextLong()));

		List<Address> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testDynamicQueryByProjectionExisting()
		throws Exception {
		Address newAddress = addAddress();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(Address.class,
				Address.class.getClassLoader());

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
				Address.class.getClassLoader());

		dynamicQuery.setProjection(ProjectionFactoryUtil.property("addressId"));

		dynamicQuery.add(RestrictionsFactoryUtil.in("addressId",
				new Object[] { ServiceTestUtil.nextLong() }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	protected Address addAddress() throws Exception {
		long pk = ServiceTestUtil.nextLong();

		Address address = _persistence.create(pk);

		address.setUuid(ServiceTestUtil.randomString());

		address.setCompanyId(ServiceTestUtil.nextLong());

		address.setUserId(ServiceTestUtil.nextLong());

		address.setUserName(ServiceTestUtil.randomString());

		address.setCreateDate(ServiceTestUtil.nextDate());

		address.setModifiedDate(ServiceTestUtil.nextDate());

		address.setClassNameId(ServiceTestUtil.nextLong());

		address.setClassPK(ServiceTestUtil.nextLong());

		address.setStreet1(ServiceTestUtil.randomString());

		address.setStreet2(ServiceTestUtil.randomString());

		address.setStreet3(ServiceTestUtil.randomString());

		address.setCity(ServiceTestUtil.randomString());

		address.setZip(ServiceTestUtil.randomString());

		address.setRegionId(ServiceTestUtil.nextLong());

		address.setCountryId(ServiceTestUtil.nextLong());

		address.setTypeId(ServiceTestUtil.nextInt());

		address.setMailing(ServiceTestUtil.randomBoolean());

		address.setPrimary(ServiceTestUtil.randomBoolean());

		_persistence.update(address);

		return address;
	}

	private static Log _log = LogFactoryUtil.getLog(AddressPersistenceTest.class);
	private AddressPersistence _persistence = (AddressPersistence)PortalBeanLocatorUtil.locate(AddressPersistence.class.getName());
	private TransactionalPersistenceAdvice _transactionalPersistenceAdvice = (TransactionalPersistenceAdvice)PortalBeanLocatorUtil.locate(TransactionalPersistenceAdvice.class.getName());
}