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

import com.liferay.portal.NoSuchPhoneException;
import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQueryFactoryUtil;
import com.liferay.portal.kernel.dao.orm.ProjectionFactoryUtil;
import com.liferay.portal.kernel.dao.orm.RestrictionsFactoryUtil;
import com.liferay.portal.kernel.util.Time;
import com.liferay.portal.model.Phone;
import com.liferay.portal.service.persistence.BasePersistenceTestCase;

import java.util.List;

/**
 * @author Brian Wing Shun Chan
 */
public class PhonePersistenceTest extends BasePersistenceTestCase {
	@Override
	public void setUp() throws Exception {
		super.setUp();

		_persistence = (PhonePersistence)PortalBeanLocatorUtil.locate(PhonePersistence.class.getName());
	}

	public void testCreate() throws Exception {
		long pk = nextLong();

		Phone phone = _persistence.create(pk);

		assertNotNull(phone);

		assertEquals(phone.getPrimaryKey(), pk);
	}

	public void testRemove() throws Exception {
		Phone newPhone = addPhone();

		_persistence.remove(newPhone);

		Phone existingPhone = _persistence.fetchByPrimaryKey(newPhone.getPrimaryKey());

		assertNull(existingPhone);
	}

	public void testUpdateNew() throws Exception {
		addPhone();
	}

	public void testUpdateExisting() throws Exception {
		long pk = nextLong();

		Phone newPhone = _persistence.create(pk);

		newPhone.setCompanyId(nextLong());

		newPhone.setUserId(nextLong());

		newPhone.setUserName(randomString());

		newPhone.setCreateDate(nextDate());

		newPhone.setModifiedDate(nextDate());

		newPhone.setClassNameId(nextLong());

		newPhone.setClassPK(nextLong());

		newPhone.setNumber(randomString());

		newPhone.setExtension(randomString());

		newPhone.setTypeId(nextInt());

		newPhone.setPrimary(randomBoolean());

		_persistence.update(newPhone, false);

		Phone existingPhone = _persistence.findByPrimaryKey(newPhone.getPrimaryKey());

		assertEquals(existingPhone.getPhoneId(), newPhone.getPhoneId());
		assertEquals(existingPhone.getCompanyId(), newPhone.getCompanyId());
		assertEquals(existingPhone.getUserId(), newPhone.getUserId());
		assertEquals(existingPhone.getUserName(), newPhone.getUserName());
		assertEquals(Time.getShortTimestamp(existingPhone.getCreateDate()),
			Time.getShortTimestamp(newPhone.getCreateDate()));
		assertEquals(Time.getShortTimestamp(existingPhone.getModifiedDate()),
			Time.getShortTimestamp(newPhone.getModifiedDate()));
		assertEquals(existingPhone.getClassNameId(), newPhone.getClassNameId());
		assertEquals(existingPhone.getClassPK(), newPhone.getClassPK());
		assertEquals(existingPhone.getNumber(), newPhone.getNumber());
		assertEquals(existingPhone.getExtension(), newPhone.getExtension());
		assertEquals(existingPhone.getTypeId(), newPhone.getTypeId());
		assertEquals(existingPhone.getPrimary(), newPhone.getPrimary());
	}

	public void testFindByPrimaryKeyExisting() throws Exception {
		Phone newPhone = addPhone();

		Phone existingPhone = _persistence.findByPrimaryKey(newPhone.getPrimaryKey());

		assertEquals(existingPhone, newPhone);
	}

	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = nextLong();

		try {
			_persistence.findByPrimaryKey(pk);

			fail("Missing entity did not throw NoSuchPhoneException");
		}
		catch (NoSuchPhoneException nsee) {
		}
	}

	public void testFetchByPrimaryKeyExisting() throws Exception {
		Phone newPhone = addPhone();

		Phone existingPhone = _persistence.fetchByPrimaryKey(newPhone.getPrimaryKey());

		assertEquals(existingPhone, newPhone);
	}

	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = nextLong();

		Phone missingPhone = _persistence.fetchByPrimaryKey(pk);

		assertNull(missingPhone);
	}

	public void testDynamicQueryByPrimaryKeyExisting()
		throws Exception {
		Phone newPhone = addPhone();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(Phone.class,
				Phone.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("phoneId",
				newPhone.getPhoneId()));

		List<Phone> result = _persistence.findWithDynamicQuery(dynamicQuery);

		assertEquals(1, result.size());

		Phone existingPhone = result.get(0);

		assertEquals(existingPhone, newPhone);
	}

	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(Phone.class,
				Phone.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("phoneId", nextLong()));

		List<Phone> result = _persistence.findWithDynamicQuery(dynamicQuery);

		assertEquals(0, result.size());
	}

	public void testDynamicQueryByProjectionExisting()
		throws Exception {
		Phone newPhone = addPhone();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(Phone.class,
				Phone.class.getClassLoader());

		dynamicQuery.setProjection(ProjectionFactoryUtil.property("phoneId"));

		Object newPhoneId = newPhone.getPhoneId();

		dynamicQuery.add(RestrictionsFactoryUtil.in("phoneId",
				new Object[] { newPhoneId }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		assertEquals(1, result.size());

		Object existingPhoneId = result.get(0);

		assertEquals(existingPhoneId, newPhoneId);
	}

	public void testDynamicQueryByProjectionMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(Phone.class,
				Phone.class.getClassLoader());

		dynamicQuery.setProjection(ProjectionFactoryUtil.property("phoneId"));

		dynamicQuery.add(RestrictionsFactoryUtil.in("phoneId",
				new Object[] { nextLong() }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		assertEquals(0, result.size());
	}

	protected Phone addPhone() throws Exception {
		long pk = nextLong();

		Phone phone = _persistence.create(pk);

		phone.setCompanyId(nextLong());

		phone.setUserId(nextLong());

		phone.setUserName(randomString());

		phone.setCreateDate(nextDate());

		phone.setModifiedDate(nextDate());

		phone.setClassNameId(nextLong());

		phone.setClassPK(nextLong());

		phone.setNumber(randomString());

		phone.setExtension(randomString());

		phone.setTypeId(nextInt());

		phone.setPrimary(randomBoolean());

		_persistence.update(phone, false);

		return phone;
	}

	private PhonePersistence _persistence;
}