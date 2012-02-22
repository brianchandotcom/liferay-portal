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

import com.liferay.portal.NoSuchContactException;
import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQueryFactoryUtil;
import com.liferay.portal.kernel.dao.orm.ProjectionFactoryUtil;
import com.liferay.portal.kernel.dao.orm.RestrictionsFactoryUtil;
import com.liferay.portal.kernel.util.Time;
import com.liferay.portal.model.Contact;
import com.liferay.portal.service.persistence.BasePersistenceTestCase;

import java.util.List;

/**
 * @author Brian Wing Shun Chan
 */
public class ContactPersistenceTest extends BasePersistenceTestCase {
	@Override
	public void setUp() throws Exception {
		super.setUp();

		_persistence = (ContactPersistence)PortalBeanLocatorUtil.locate(ContactPersistence.class.getName());
	}

	public void testCreate() throws Exception {
		long pk = nextLong();

		Contact contact = _persistence.create(pk);

		assertNotNull(contact);

		assertEquals(contact.getPrimaryKey(), pk);
	}

	public void testRemove() throws Exception {
		Contact newContact = addContact();

		_persistence.remove(newContact);

		Contact existingContact = _persistence.fetchByPrimaryKey(newContact.getPrimaryKey());

		assertNull(existingContact);
	}

	public void testUpdateNew() throws Exception {
		addContact();
	}

	public void testUpdateExisting() throws Exception {
		long pk = nextLong();

		Contact newContact = _persistence.create(pk);

		newContact.setCompanyId(nextLong());

		newContact.setUserId(nextLong());

		newContact.setUserName(randomString());

		newContact.setCreateDate(nextDate());

		newContact.setModifiedDate(nextDate());

		newContact.setAccountId(nextLong());

		newContact.setParentContactId(nextLong());

		newContact.setFirstName(randomString());

		newContact.setMiddleName(randomString());

		newContact.setLastName(randomString());

		newContact.setPrefixId(nextInt());

		newContact.setSuffixId(nextInt());

		newContact.setMale(randomBoolean());

		newContact.setBirthday(nextDate());

		newContact.setSmsSn(randomString());

		newContact.setAimSn(randomString());

		newContact.setFacebookSn(randomString());

		newContact.setIcqSn(randomString());

		newContact.setJabberSn(randomString());

		newContact.setMsnSn(randomString());

		newContact.setMySpaceSn(randomString());

		newContact.setSkypeSn(randomString());

		newContact.setTwitterSn(randomString());

		newContact.setYmSn(randomString());

		newContact.setEmployeeStatusId(randomString());

		newContact.setEmployeeNumber(randomString());

		newContact.setJobTitle(randomString());

		newContact.setJobClass(randomString());

		newContact.setHoursOfOperation(randomString());

		_persistence.update(newContact, false);

		Contact existingContact = _persistence.findByPrimaryKey(newContact.getPrimaryKey());

		assertEquals(existingContact.getContactId(), newContact.getContactId());
		assertEquals(existingContact.getCompanyId(), newContact.getCompanyId());
		assertEquals(existingContact.getUserId(), newContact.getUserId());
		assertEquals(existingContact.getUserName(), newContact.getUserName());
		assertEquals(Time.getShortTimestamp(existingContact.getCreateDate()),
			Time.getShortTimestamp(newContact.getCreateDate()));
		assertEquals(Time.getShortTimestamp(existingContact.getModifiedDate()),
			Time.getShortTimestamp(newContact.getModifiedDate()));
		assertEquals(existingContact.getAccountId(), newContact.getAccountId());
		assertEquals(existingContact.getParentContactId(),
			newContact.getParentContactId());
		assertEquals(existingContact.getFirstName(), newContact.getFirstName());
		assertEquals(existingContact.getMiddleName(), newContact.getMiddleName());
		assertEquals(existingContact.getLastName(), newContact.getLastName());
		assertEquals(existingContact.getPrefixId(), newContact.getPrefixId());
		assertEquals(existingContact.getSuffixId(), newContact.getSuffixId());
		assertEquals(existingContact.getMale(), newContact.getMale());
		assertEquals(Time.getShortTimestamp(existingContact.getBirthday()),
			Time.getShortTimestamp(newContact.getBirthday()));
		assertEquals(existingContact.getSmsSn(), newContact.getSmsSn());
		assertEquals(existingContact.getAimSn(), newContact.getAimSn());
		assertEquals(existingContact.getFacebookSn(), newContact.getFacebookSn());
		assertEquals(existingContact.getIcqSn(), newContact.getIcqSn());
		assertEquals(existingContact.getJabberSn(), newContact.getJabberSn());
		assertEquals(existingContact.getMsnSn(), newContact.getMsnSn());
		assertEquals(existingContact.getMySpaceSn(), newContact.getMySpaceSn());
		assertEquals(existingContact.getSkypeSn(), newContact.getSkypeSn());
		assertEquals(existingContact.getTwitterSn(), newContact.getTwitterSn());
		assertEquals(existingContact.getYmSn(), newContact.getYmSn());
		assertEquals(existingContact.getEmployeeStatusId(),
			newContact.getEmployeeStatusId());
		assertEquals(existingContact.getEmployeeNumber(),
			newContact.getEmployeeNumber());
		assertEquals(existingContact.getJobTitle(), newContact.getJobTitle());
		assertEquals(existingContact.getJobClass(), newContact.getJobClass());
		assertEquals(existingContact.getHoursOfOperation(),
			newContact.getHoursOfOperation());
	}

	public void testFindByPrimaryKeyExisting() throws Exception {
		Contact newContact = addContact();

		Contact existingContact = _persistence.findByPrimaryKey(newContact.getPrimaryKey());

		assertEquals(existingContact, newContact);
	}

	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = nextLong();

		try {
			_persistence.findByPrimaryKey(pk);

			fail("Missing entity did not throw NoSuchContactException");
		}
		catch (NoSuchContactException nsee) {
		}
	}

	public void testFetchByPrimaryKeyExisting() throws Exception {
		Contact newContact = addContact();

		Contact existingContact = _persistence.fetchByPrimaryKey(newContact.getPrimaryKey());

		assertEquals(existingContact, newContact);
	}

	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = nextLong();

		Contact missingContact = _persistence.fetchByPrimaryKey(pk);

		assertNull(missingContact);
	}

	public void testDynamicQueryByPrimaryKeyExisting()
		throws Exception {
		Contact newContact = addContact();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(Contact.class,
				Contact.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("contactId",
				newContact.getContactId()));

		List<Contact> result = _persistence.findWithDynamicQuery(dynamicQuery);

		assertEquals(1, result.size());

		Contact existingContact = result.get(0);

		assertEquals(existingContact, newContact);
	}

	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(Contact.class,
				Contact.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("contactId", nextLong()));

		List<Contact> result = _persistence.findWithDynamicQuery(dynamicQuery);

		assertEquals(0, result.size());
	}

	public void testDynamicQueryByProjectionExisting()
		throws Exception {
		Contact newContact = addContact();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(Contact.class,
				Contact.class.getClassLoader());

		dynamicQuery.setProjection(ProjectionFactoryUtil.property("contactId"));

		Object newContactId = newContact.getContactId();

		dynamicQuery.add(RestrictionsFactoryUtil.in("contactId",
				new Object[] { newContactId }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		assertEquals(1, result.size());

		Object existingContactId = result.get(0);

		assertEquals(existingContactId, newContactId);
	}

	public void testDynamicQueryByProjectionMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(Contact.class,
				Contact.class.getClassLoader());

		dynamicQuery.setProjection(ProjectionFactoryUtil.property("contactId"));

		dynamicQuery.add(RestrictionsFactoryUtil.in("contactId",
				new Object[] { nextLong() }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		assertEquals(0, result.size());
	}

	protected Contact addContact() throws Exception {
		long pk = nextLong();

		Contact contact = _persistence.create(pk);

		contact.setCompanyId(nextLong());

		contact.setUserId(nextLong());

		contact.setUserName(randomString());

		contact.setCreateDate(nextDate());

		contact.setModifiedDate(nextDate());

		contact.setAccountId(nextLong());

		contact.setParentContactId(nextLong());

		contact.setFirstName(randomString());

		contact.setMiddleName(randomString());

		contact.setLastName(randomString());

		contact.setPrefixId(nextInt());

		contact.setSuffixId(nextInt());

		contact.setMale(randomBoolean());

		contact.setBirthday(nextDate());

		contact.setSmsSn(randomString());

		contact.setAimSn(randomString());

		contact.setFacebookSn(randomString());

		contact.setIcqSn(randomString());

		contact.setJabberSn(randomString());

		contact.setMsnSn(randomString());

		contact.setMySpaceSn(randomString());

		contact.setSkypeSn(randomString());

		contact.setTwitterSn(randomString());

		contact.setYmSn(randomString());

		contact.setEmployeeStatusId(randomString());

		contact.setEmployeeNumber(randomString());

		contact.setJobTitle(randomString());

		contact.setJobClass(randomString());

		contact.setHoursOfOperation(randomString());

		_persistence.update(contact, false);

		return contact;
	}

	private ContactPersistence _persistence;
}