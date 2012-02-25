/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQueryFactoryUtil;
import com.liferay.portal.kernel.dao.orm.ProjectionFactoryUtil;
import com.liferay.portal.kernel.dao.orm.RestrictionsFactoryUtil;
import com.liferay.portal.kernel.util.Time;
import com.liferay.portal.model.Address;
import com.liferay.portal.service.persistence.BasePersistenceTestCase;

import java.util.List;

/**
 * @author Brian Wing Shun Chan
 */
public class AddressPersistenceTest extends BasePersistenceTestCase {
	@Override
	public void setUp() throws Exception {
		super.setUp();

		_persistence = (AddressPersistence)PortalBeanLocatorUtil.locate(AddressPersistence.class.getName());
	}

	public void testCreate() throws Exception {
		long pk = nextLong();

		Address address = _persistence.create(pk);

		assertNotNull(address);

		assertEquals(address.getPrimaryKey(), pk);
	}

	public void testRemove() throws Exception {
		Address newAddress = addAddress();

		_persistence.remove(newAddress);

		Address existingAddress = _persistence.fetchByPrimaryKey(newAddress.getPrimaryKey());

		assertNull(existingAddress);
	}

	public void testUpdateNew() throws Exception {
		addAddress();
	}

	public void testUpdateExisting() throws Exception {
		long pk = nextLong();

		Address newAddress = _persistence.create(pk);

		newAddress.setCompanyId(nextLong());

		newAddress.setUserId(nextLong());

		newAddress.setUserName(randomString());

		newAddress.setCreateDate(nextDate());

		newAddress.setModifiedDate(nextDate());

		newAddress.setClassNameId(nextLong());

		newAddress.setClassPK(nextLong());

		newAddress.setStreet1(randomString());

		newAddress.setStreet2(randomString());

		newAddress.setStreet3(randomString());

		newAddress.setCity(randomString());

		newAddress.setZip(randomString());

		newAddress.setRegionId(nextLong());

		newAddress.setCountryId(nextLong());

		newAddress.setTypeId(nextInt());

		newAddress.setMailing(randomBoolean());

		newAddress.setPrimary(randomBoolean());

		_persistence.update(newAddress, false);

		Address existingAddress = _persistence.findByPrimaryKey(newAddress.getPrimaryKey());

		assertEquals(existingAddress.getAddressId(), newAddress.getAddressId());
		assertEquals(existingAddress.getCompanyId(), newAddress.getCompanyId());
		assertEquals(existingAddress.getUserId(), newAddress.getUserId());
		assertEquals(existingAddress.getUserName(), newAddress.getUserName());
		assertEquals(Time.getShortTimestamp(existingAddress.getCreateDate()),
			Time.getShortTimestamp(newAddress.getCreateDate()));
		assertEquals(Time.getShortTimestamp(existingAddress.getModifiedDate()),
			Time.getShortTimestamp(newAddress.getModifiedDate()));
		assertEquals(existingAddress.getClassNameId(),
			newAddress.getClassNameId());
		assertEquals(existingAddress.getClassPK(), newAddress.getClassPK());
		assertEquals(existingAddress.getStreet1(), newAddress.getStreet1());
		assertEquals(existingAddress.getStreet2(), newAddress.getStreet2());
		assertEquals(existingAddress.getStreet3(), newAddress.getStreet3());
		assertEquals(existingAddress.getCity(), newAddress.getCity());
		assertEquals(existingAddress.getZip(), newAddress.getZip());
		assertEquals(existingAddress.getRegionId(), newAddress.getRegionId());
		assertEquals(existingAddress.getCountryId(), newAddress.getCountryId());
		assertEquals(existingAddress.getTypeId(), newAddress.getTypeId());
		assertEquals(existingAddress.getMailing(), newAddress.getMailing());
		assertEquals(existingAddress.getPrimary(), newAddress.getPrimary());
	}

	public void testFindByPrimaryKeyExisting() throws Exception {
		Address newAddress = addAddress();

		Address existingAddress = _persistence.findByPrimaryKey(newAddress.getPrimaryKey());

		assertEquals(existingAddress, newAddress);
	}

	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = nextLong();

		try {
			_persistence.findByPrimaryKey(pk);

			fail("Missing entity did not throw NoSuchAddressException");
		}
		catch (NoSuchAddressException nsee) {
		}
	}

	public void testFetchByPrimaryKeyExisting() throws Exception {
		Address newAddress = addAddress();

		Address existingAddress = _persistence.fetchByPrimaryKey(newAddress.getPrimaryKey());

		assertEquals(existingAddress, newAddress);
	}

	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = nextLong();

		Address missingAddress = _persistence.fetchByPrimaryKey(pk);

		assertNull(missingAddress);
	}

	public void testDynamicQueryByPrimaryKeyExisting()
		throws Exception {
		Address newAddress = addAddress();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(Address.class,
				Address.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("addressId",
				newAddress.getAddressId()));

		List<Address> result = _persistence.findWithDynamicQuery(dynamicQuery);

		assertEquals(1, result.size());

		Address existingAddress = result.get(0);

		assertEquals(existingAddress, newAddress);
	}

	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(Address.class,
				Address.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("addressId", nextLong()));

		List<Address> result = _persistence.findWithDynamicQuery(dynamicQuery);

		assertEquals(0, result.size());
	}

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

		assertEquals(1, result.size());

		Object existingAddressId = result.get(0);

		assertEquals(existingAddressId, newAddressId);
	}

	public void testDynamicQueryByProjectionMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(Address.class,
				Address.class.getClassLoader());

		dynamicQuery.setProjection(ProjectionFactoryUtil.property("addressId"));

		dynamicQuery.add(RestrictionsFactoryUtil.in("addressId",
				new Object[] { nextLong() }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		assertEquals(0, result.size());
	}

	protected Address addAddress() throws Exception {
		long pk = nextLong();

		Address address = _persistence.create(pk);

		address.setCompanyId(nextLong());

		address.setUserId(nextLong());

		address.setUserName(randomString());

		address.setCreateDate(nextDate());

		address.setModifiedDate(nextDate());

		address.setClassNameId(nextLong());

		address.setClassPK(nextLong());

		address.setStreet1(randomString());

		address.setStreet2(randomString());

		address.setStreet3(randomString());

		address.setCity(randomString());

		address.setZip(randomString());

		address.setRegionId(nextLong());

		address.setCountryId(nextLong());

		address.setTypeId(nextInt());

		address.setMailing(randomBoolean());

		address.setPrimary(randomBoolean());

		_persistence.update(address, false);

		return address;
	}

	private AddressPersistence _persistence;
}