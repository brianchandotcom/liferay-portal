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

import com.liferay.portal.NoSuchEmailAddressException;
import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQueryFactoryUtil;
import com.liferay.portal.kernel.dao.orm.ProjectionFactoryUtil;
import com.liferay.portal.kernel.dao.orm.RestrictionsFactoryUtil;
import com.liferay.portal.kernel.util.Time;
import com.liferay.portal.model.EmailAddress;
import com.liferay.portal.service.persistence.BasePersistenceTestCase;

import java.util.List;

/**
 * @author Brian Wing Shun Chan
 */
public class EmailAddressPersistenceTest extends BasePersistenceTestCase {
	@Override
	public void setUp() throws Exception {
		super.setUp();

		_persistence = (EmailAddressPersistence)PortalBeanLocatorUtil.locate(EmailAddressPersistence.class.getName());
	}

	public void testCreate() throws Exception {
		long pk = nextLong();

		EmailAddress emailAddress = _persistence.create(pk);

		assertNotNull(emailAddress);

		assertEquals(emailAddress.getPrimaryKey(), pk);
	}

	public void testRemove() throws Exception {
		EmailAddress newEmailAddress = addEmailAddress();

		_persistence.remove(newEmailAddress);

		EmailAddress existingEmailAddress = _persistence.fetchByPrimaryKey(newEmailAddress.getPrimaryKey());

		assertNull(existingEmailAddress);
	}

	public void testUpdateNew() throws Exception {
		addEmailAddress();
	}

	public void testUpdateExisting() throws Exception {
		long pk = nextLong();

		EmailAddress newEmailAddress = _persistence.create(pk);

		newEmailAddress.setCompanyId(nextLong());

		newEmailAddress.setUserId(nextLong());

		newEmailAddress.setUserName(randomString());

		newEmailAddress.setCreateDate(nextDate());

		newEmailAddress.setModifiedDate(nextDate());

		newEmailAddress.setClassNameId(nextLong());

		newEmailAddress.setClassPK(nextLong());

		newEmailAddress.setAddress(randomString());

		newEmailAddress.setTypeId(nextInt());

		newEmailAddress.setPrimary(randomBoolean());

		_persistence.update(newEmailAddress, false);

		EmailAddress existingEmailAddress = _persistence.findByPrimaryKey(newEmailAddress.getPrimaryKey());

		assertEquals(existingEmailAddress.getEmailAddressId(),
			newEmailAddress.getEmailAddressId());
		assertEquals(existingEmailAddress.getCompanyId(),
			newEmailAddress.getCompanyId());
		assertEquals(existingEmailAddress.getUserId(),
			newEmailAddress.getUserId());
		assertEquals(existingEmailAddress.getUserName(),
			newEmailAddress.getUserName());
		assertEquals(Time.getShortTimestamp(
				existingEmailAddress.getCreateDate()),
			Time.getShortTimestamp(newEmailAddress.getCreateDate()));
		assertEquals(Time.getShortTimestamp(
				existingEmailAddress.getModifiedDate()),
			Time.getShortTimestamp(newEmailAddress.getModifiedDate()));
		assertEquals(existingEmailAddress.getClassNameId(),
			newEmailAddress.getClassNameId());
		assertEquals(existingEmailAddress.getClassPK(),
			newEmailAddress.getClassPK());
		assertEquals(existingEmailAddress.getAddress(),
			newEmailAddress.getAddress());
		assertEquals(existingEmailAddress.getTypeId(),
			newEmailAddress.getTypeId());
		assertEquals(existingEmailAddress.getPrimary(),
			newEmailAddress.getPrimary());
	}

	public void testFindByPrimaryKeyExisting() throws Exception {
		EmailAddress newEmailAddress = addEmailAddress();

		EmailAddress existingEmailAddress = _persistence.findByPrimaryKey(newEmailAddress.getPrimaryKey());

		assertEquals(existingEmailAddress, newEmailAddress);
	}

	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = nextLong();

		try {
			_persistence.findByPrimaryKey(pk);

			fail("Missing entity did not throw NoSuchEmailAddressException");
		}
		catch (NoSuchEmailAddressException nsee) {
		}
	}

	public void testFetchByPrimaryKeyExisting() throws Exception {
		EmailAddress newEmailAddress = addEmailAddress();

		EmailAddress existingEmailAddress = _persistence.fetchByPrimaryKey(newEmailAddress.getPrimaryKey());

		assertEquals(existingEmailAddress, newEmailAddress);
	}

	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = nextLong();

		EmailAddress missingEmailAddress = _persistence.fetchByPrimaryKey(pk);

		assertNull(missingEmailAddress);
	}

	public void testDynamicQueryByPrimaryKeyExisting()
		throws Exception {
		EmailAddress newEmailAddress = addEmailAddress();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(EmailAddress.class,
				EmailAddress.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("emailAddressId",
				newEmailAddress.getEmailAddressId()));

		List<EmailAddress> result = _persistence.findWithDynamicQuery(dynamicQuery);

		assertEquals(1, result.size());

		EmailAddress existingEmailAddress = result.get(0);

		assertEquals(existingEmailAddress, newEmailAddress);
	}

	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(EmailAddress.class,
				EmailAddress.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("emailAddressId", nextLong()));

		List<EmailAddress> result = _persistence.findWithDynamicQuery(dynamicQuery);

		assertEquals(0, result.size());
	}

	public void testDynamicQueryByProjectionExisting()
		throws Exception {
		EmailAddress newEmailAddress = addEmailAddress();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(EmailAddress.class,
				EmailAddress.class.getClassLoader());

		dynamicQuery.setProjection(ProjectionFactoryUtil.property(
				"emailAddressId"));

		Object newEmailAddressId = newEmailAddress.getEmailAddressId();

		dynamicQuery.add(RestrictionsFactoryUtil.in("emailAddressId",
				new Object[] { newEmailAddressId }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		assertEquals(1, result.size());

		Object existingEmailAddressId = result.get(0);

		assertEquals(existingEmailAddressId, newEmailAddressId);
	}

	public void testDynamicQueryByProjectionMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(EmailAddress.class,
				EmailAddress.class.getClassLoader());

		dynamicQuery.setProjection(ProjectionFactoryUtil.property(
				"emailAddressId"));

		dynamicQuery.add(RestrictionsFactoryUtil.in("emailAddressId",
				new Object[] { nextLong() }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		assertEquals(0, result.size());
	}

	protected EmailAddress addEmailAddress() throws Exception {
		long pk = nextLong();

		EmailAddress emailAddress = _persistence.create(pk);

		emailAddress.setCompanyId(nextLong());

		emailAddress.setUserId(nextLong());

		emailAddress.setUserName(randomString());

		emailAddress.setCreateDate(nextDate());

		emailAddress.setModifiedDate(nextDate());

		emailAddress.setClassNameId(nextLong());

		emailAddress.setClassPK(nextLong());

		emailAddress.setAddress(randomString());

		emailAddress.setTypeId(nextInt());

		emailAddress.setPrimary(randomBoolean());

		_persistence.update(emailAddress, false);

		return emailAddress;
	}

	private EmailAddressPersistence _persistence;
}