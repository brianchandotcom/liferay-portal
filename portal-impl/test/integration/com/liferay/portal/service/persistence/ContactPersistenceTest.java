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

import com.liferay.portal.NoSuchContactException;
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
import com.liferay.portal.model.Contact;
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
public class ContactPersistenceTest {
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

		Contact contact = _persistence.create(pk);

		Assert.assertNotNull(contact);

		Assert.assertEquals(contact.getPrimaryKey(), pk);
	}

	@Test
	public void testRemove() throws Exception {
		Contact newContact = addContact();

		_persistence.remove(newContact);

		Contact existingContact = _persistence.fetchByPrimaryKey(newContact.getPrimaryKey());

		Assert.assertNull(existingContact);
	}

	@Test
	public void testUpdateNew() throws Exception {
		addContact();
	}

	@Test
	public void testUpdateExisting() throws Exception {
		long pk = ServiceTestUtil.nextLong();

		Contact newContact = _persistence.create(pk);

		newContact.setCompanyId(ServiceTestUtil.nextLong());

		newContact.setUserId(ServiceTestUtil.nextLong());

		newContact.setUserName(ServiceTestUtil.randomString());

		newContact.setCreateDate(ServiceTestUtil.nextDate());

		newContact.setModifiedDate(ServiceTestUtil.nextDate());

		newContact.setClassNameId(ServiceTestUtil.nextLong());

		newContact.setClassPK(ServiceTestUtil.nextLong());

		newContact.setAccountId(ServiceTestUtil.nextLong());

		newContact.setParentContactId(ServiceTestUtil.nextLong());

		newContact.setEmailAddress(ServiceTestUtil.randomString());

		newContact.setFirstName(ServiceTestUtil.randomString());

		newContact.setMiddleName(ServiceTestUtil.randomString());

		newContact.setLastName(ServiceTestUtil.randomString());

		newContact.setPrefixId(ServiceTestUtil.nextInt());

		newContact.setSuffixId(ServiceTestUtil.nextInt());

		newContact.setMale(ServiceTestUtil.randomBoolean());

		newContact.setBirthday(ServiceTestUtil.nextDate());

		newContact.setSmsSn(ServiceTestUtil.randomString());

		newContact.setAimSn(ServiceTestUtil.randomString());

		newContact.setFacebookSn(ServiceTestUtil.randomString());

		newContact.setIcqSn(ServiceTestUtil.randomString());

		newContact.setJabberSn(ServiceTestUtil.randomString());

		newContact.setMsnSn(ServiceTestUtil.randomString());

		newContact.setMySpaceSn(ServiceTestUtil.randomString());

		newContact.setSkypeSn(ServiceTestUtil.randomString());

		newContact.setTwitterSn(ServiceTestUtil.randomString());

		newContact.setYmSn(ServiceTestUtil.randomString());

		newContact.setEmployeeStatusId(ServiceTestUtil.randomString());

		newContact.setEmployeeNumber(ServiceTestUtil.randomString());

		newContact.setJobTitle(ServiceTestUtil.randomString());

		newContact.setJobClass(ServiceTestUtil.randomString());

		newContact.setHoursOfOperation(ServiceTestUtil.randomString());

		newContact.setMvccVersion(ServiceTestUtil.nextLong());

		_persistence.update(newContact);

		Contact existingContact = _persistence.findByPrimaryKey(newContact.getPrimaryKey());

		Assert.assertEquals(existingContact.getContactId(),
			newContact.getContactId());
		Assert.assertEquals(existingContact.getCompanyId(),
			newContact.getCompanyId());
		Assert.assertEquals(existingContact.getUserId(), newContact.getUserId());
		Assert.assertEquals(existingContact.getUserName(),
			newContact.getUserName());
		Assert.assertEquals(Time.getShortTimestamp(
				existingContact.getCreateDate()),
			Time.getShortTimestamp(newContact.getCreateDate()));
		Assert.assertEquals(Time.getShortTimestamp(
				existingContact.getModifiedDate()),
			Time.getShortTimestamp(newContact.getModifiedDate()));
		Assert.assertEquals(existingContact.getClassNameId(),
			newContact.getClassNameId());
		Assert.assertEquals(existingContact.getClassPK(),
			newContact.getClassPK());
		Assert.assertEquals(existingContact.getAccountId(),
			newContact.getAccountId());
		Assert.assertEquals(existingContact.getParentContactId(),
			newContact.getParentContactId());
		Assert.assertEquals(existingContact.getEmailAddress(),
			newContact.getEmailAddress());
		Assert.assertEquals(existingContact.getFirstName(),
			newContact.getFirstName());
		Assert.assertEquals(existingContact.getMiddleName(),
			newContact.getMiddleName());
		Assert.assertEquals(existingContact.getLastName(),
			newContact.getLastName());
		Assert.assertEquals(existingContact.getPrefixId(),
			newContact.getPrefixId());
		Assert.assertEquals(existingContact.getSuffixId(),
			newContact.getSuffixId());
		Assert.assertEquals(existingContact.getMale(), newContact.getMale());
		Assert.assertEquals(Time.getShortTimestamp(
				existingContact.getBirthday()),
			Time.getShortTimestamp(newContact.getBirthday()));
		Assert.assertEquals(existingContact.getSmsSn(), newContact.getSmsSn());
		Assert.assertEquals(existingContact.getAimSn(), newContact.getAimSn());
		Assert.assertEquals(existingContact.getFacebookSn(),
			newContact.getFacebookSn());
		Assert.assertEquals(existingContact.getIcqSn(), newContact.getIcqSn());
		Assert.assertEquals(existingContact.getJabberSn(),
			newContact.getJabberSn());
		Assert.assertEquals(existingContact.getMsnSn(), newContact.getMsnSn());
		Assert.assertEquals(existingContact.getMySpaceSn(),
			newContact.getMySpaceSn());
		Assert.assertEquals(existingContact.getSkypeSn(),
			newContact.getSkypeSn());
		Assert.assertEquals(existingContact.getTwitterSn(),
			newContact.getTwitterSn());
		Assert.assertEquals(existingContact.getYmSn(), newContact.getYmSn());
		Assert.assertEquals(existingContact.getEmployeeStatusId(),
			newContact.getEmployeeStatusId());
		Assert.assertEquals(existingContact.getEmployeeNumber(),
			newContact.getEmployeeNumber());
		Assert.assertEquals(existingContact.getJobTitle(),
			newContact.getJobTitle());
		Assert.assertEquals(existingContact.getJobClass(),
			newContact.getJobClass());
		Assert.assertEquals(existingContact.getHoursOfOperation(),
			newContact.getHoursOfOperation());
		Assert.assertEquals(existingContact.getMvccVersion(),
			newContact.getMvccVersion());
	}

	@Test
	public void testFindByPrimaryKeyExisting() throws Exception {
		Contact newContact = addContact();

		Contact existingContact = _persistence.findByPrimaryKey(newContact.getPrimaryKey());

		Assert.assertEquals(existingContact, newContact);
	}

	@Test
	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = ServiceTestUtil.nextLong();

		try {
			_persistence.findByPrimaryKey(pk);

			Assert.fail("Missing entity did not throw NoSuchContactException");
		}
		catch (NoSuchContactException nsee) {
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
		return OrderByComparatorFactoryUtil.create("Contact_", "contactId",
			true, "companyId", true, "userId", true, "userName", true,
			"createDate", true, "modifiedDate", true, "classNameId", true,
			"classPK", true, "accountId", true, "parentContactId", true,
			"emailAddress", true, "firstName", true, "middleName", true,
			"lastName", true, "prefixId", true, "suffixId", true, "male", true,
			"birthday", true, "smsSn", true, "aimSn", true, "facebookSn", true,
			"icqSn", true, "jabberSn", true, "msnSn", true, "mySpaceSn", true,
			"skypeSn", true, "twitterSn", true, "ymSn", true,
			"employeeStatusId", true, "employeeNumber", true, "jobTitle", true,
			"jobClass", true, "hoursOfOperation", true, "mvccVersion", true);
	}

	@Test
	public void testFetchByPrimaryKeyExisting() throws Exception {
		Contact newContact = addContact();

		Contact existingContact = _persistence.fetchByPrimaryKey(newContact.getPrimaryKey());

		Assert.assertEquals(existingContact, newContact);
	}

	@Test
	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = ServiceTestUtil.nextLong();

		Contact missingContact = _persistence.fetchByPrimaryKey(pk);

		Assert.assertNull(missingContact);
	}

	@Test
	public void testActionableDynamicQuery() throws Exception {
		final IntegerWrapper count = new IntegerWrapper();

		ActionableDynamicQuery actionableDynamicQuery = new ContactActionableDynamicQuery() {
				@Override
				protected void performAction(Object object) {
					Contact contact = (Contact)object;

					Assert.assertNotNull(contact);

					count.increment();
				}
			};

		actionableDynamicQuery.performActions();

		Assert.assertEquals(count.getValue(), _persistence.countAll());
	}

	@Test
	public void testDynamicQueryByPrimaryKeyExisting()
		throws Exception {
		Contact newContact = addContact();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(Contact.class,
				Contact.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("contactId",
				newContact.getContactId()));

		List<Contact> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		Contact existingContact = result.get(0);

		Assert.assertEquals(existingContact, newContact);
	}

	@Test
	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(Contact.class,
				Contact.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("contactId",
				ServiceTestUtil.nextLong()));

		List<Contact> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
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

		Assert.assertEquals(1, result.size());

		Object existingContactId = result.get(0);

		Assert.assertEquals(existingContactId, newContactId);
	}

	@Test
	public void testDynamicQueryByProjectionMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(Contact.class,
				Contact.class.getClassLoader());

		dynamicQuery.setProjection(ProjectionFactoryUtil.property("contactId"));

		dynamicQuery.add(RestrictionsFactoryUtil.in("contactId",
				new Object[] { ServiceTestUtil.nextLong() }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	protected Contact addContact() throws Exception {
		long pk = ServiceTestUtil.nextLong();

		Contact contact = _persistence.create(pk);

		contact.setCompanyId(ServiceTestUtil.nextLong());

		contact.setUserId(ServiceTestUtil.nextLong());

		contact.setUserName(ServiceTestUtil.randomString());

		contact.setCreateDate(ServiceTestUtil.nextDate());

		contact.setModifiedDate(ServiceTestUtil.nextDate());

		contact.setClassNameId(ServiceTestUtil.nextLong());

		contact.setClassPK(ServiceTestUtil.nextLong());

		contact.setAccountId(ServiceTestUtil.nextLong());

		contact.setParentContactId(ServiceTestUtil.nextLong());

		contact.setEmailAddress(ServiceTestUtil.randomString());

		contact.setFirstName(ServiceTestUtil.randomString());

		contact.setMiddleName(ServiceTestUtil.randomString());

		contact.setLastName(ServiceTestUtil.randomString());

		contact.setPrefixId(ServiceTestUtil.nextInt());

		contact.setSuffixId(ServiceTestUtil.nextInt());

		contact.setMale(ServiceTestUtil.randomBoolean());

		contact.setBirthday(ServiceTestUtil.nextDate());

		contact.setSmsSn(ServiceTestUtil.randomString());

		contact.setAimSn(ServiceTestUtil.randomString());

		contact.setFacebookSn(ServiceTestUtil.randomString());

		contact.setIcqSn(ServiceTestUtil.randomString());

		contact.setJabberSn(ServiceTestUtil.randomString());

		contact.setMsnSn(ServiceTestUtil.randomString());

		contact.setMySpaceSn(ServiceTestUtil.randomString());

		contact.setSkypeSn(ServiceTestUtil.randomString());

		contact.setTwitterSn(ServiceTestUtil.randomString());

		contact.setYmSn(ServiceTestUtil.randomString());

		contact.setEmployeeStatusId(ServiceTestUtil.randomString());

		contact.setEmployeeNumber(ServiceTestUtil.randomString());

		contact.setJobTitle(ServiceTestUtil.randomString());

		contact.setJobClass(ServiceTestUtil.randomString());

		contact.setHoursOfOperation(ServiceTestUtil.randomString());

		contact.setMvccVersion(ServiceTestUtil.nextLong());

		_persistence.update(contact);

		return contact;
	}

	private static Log _log = LogFactoryUtil.getLog(ContactPersistenceTest.class);
	private ContactPersistence _persistence = (ContactPersistence)PortalBeanLocatorUtil.locate(ContactPersistence.class.getName());
	private TransactionalPersistenceAdvice _transactionalPersistenceAdvice = (TransactionalPersistenceAdvice)PortalBeanLocatorUtil.locate(TransactionalPersistenceAdvice.class.getName());
}