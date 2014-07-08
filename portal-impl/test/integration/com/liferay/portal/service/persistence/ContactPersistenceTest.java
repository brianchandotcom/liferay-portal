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
import com.liferay.portal.model.ModelListener;
import com.liferay.portal.service.ContactLocalServiceUtil;
import com.liferay.portal.service.persistence.BasePersistence;
import com.liferay.portal.service.persistence.PersistenceExecutionTestListener;
import com.liferay.portal.test.LiferayPersistenceIntegrationJUnitTestRunner;
import com.liferay.portal.test.persistence.test.TransactionalPersistenceAdvice;
import com.liferay.portal.util.test.RandomTestUtil;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import org.junit.runner.RunWith;

import java.io.Serializable;

import java.util.HashSet;
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
	@Before
	public void setUp() {
		_modelListeners = _persistence.getListeners();

		for (ModelListener<Contact> modelListener : _modelListeners) {
			_persistence.unregisterListener(modelListener);
		}
	}

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

		for (ModelListener<Contact> modelListener : _modelListeners) {
			_persistence.registerListener(modelListener);
		}
	}

	@Test
	public void testCreate() throws Exception {
		long pk = RandomTestUtil.nextLong();

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
		long pk = RandomTestUtil.nextLong();

		Contact newContact = _persistence.create(pk);

		newContact.setMvccVersion(RandomTestUtil.nextLong());

		newContact.setCompanyId(RandomTestUtil.nextLong());

		newContact.setUserId(RandomTestUtil.nextLong());

		newContact.setUserName(RandomTestUtil.randomString());

		newContact.setCreateDate(RandomTestUtil.nextDate());

		newContact.setModifiedDate(RandomTestUtil.nextDate());

		newContact.setClassNameId(RandomTestUtil.nextLong());

		newContact.setClassPK(RandomTestUtil.nextLong());

		newContact.setAccountId(RandomTestUtil.nextLong());

		newContact.setParentContactId(RandomTestUtil.nextLong());

		newContact.setEmailAddress(RandomTestUtil.randomString());

		newContact.setFirstName(RandomTestUtil.randomString());

		newContact.setMiddleName(RandomTestUtil.randomString());

		newContact.setLastName(RandomTestUtil.randomString());

		newContact.setPrefixId(RandomTestUtil.nextInt());

		newContact.setSuffixId(RandomTestUtil.nextInt());

		newContact.setMale(RandomTestUtil.randomBoolean());

		newContact.setBirthday(RandomTestUtil.nextDate());

		newContact.setSmsSn(RandomTestUtil.randomString());

		newContact.setAimSn(RandomTestUtil.randomString());

		newContact.setFacebookSn(RandomTestUtil.randomString());

		newContact.setIcqSn(RandomTestUtil.randomString());

		newContact.setJabberSn(RandomTestUtil.randomString());

		newContact.setMsnSn(RandomTestUtil.randomString());

		newContact.setMySpaceSn(RandomTestUtil.randomString());

		newContact.setSkypeSn(RandomTestUtil.randomString());

		newContact.setTwitterSn(RandomTestUtil.randomString());

		newContact.setYmSn(RandomTestUtil.randomString());

		newContact.setEmployeeStatusId(RandomTestUtil.randomString());

		newContact.setEmployeeNumber(RandomTestUtil.randomString());

		newContact.setJobTitle(RandomTestUtil.randomString());

		newContact.setJobClass(RandomTestUtil.randomString());

		newContact.setHoursOfOperation(RandomTestUtil.randomString());

		_persistence.update(newContact);

		Contact existingContact = _persistence.findByPrimaryKey(newContact.getPrimaryKey());

		Assert.assertEquals(existingContact.getMvccVersion(),
			newContact.getMvccVersion());
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
	}

	@Test
	public void testCountByCompanyId() {
		try {
			_persistence.countByCompanyId(RandomTestUtil.nextLong());

			_persistence.countByCompanyId(0L);
		}
		catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void testCountByAccountId() {
		try {
			_persistence.countByAccountId(RandomTestUtil.nextLong());

			_persistence.countByAccountId(0L);
		}
		catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void testCountByC_C() {
		try {
			_persistence.countByC_C(RandomTestUtil.nextLong(),
				RandomTestUtil.nextLong());

			_persistence.countByC_C(0L, 0L);
		}
		catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void testFindByPrimaryKeyExisting() throws Exception {
		Contact newContact = addContact();

		Contact existingContact = _persistence.findByPrimaryKey(newContact.getPrimaryKey());

		Assert.assertEquals(existingContact, newContact);
	}

	@Test
	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

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
		return OrderByComparatorFactoryUtil.create("Contact_", "mvccVersion",
			true, "contactId", true, "companyId", true, "userId", true,
			"userName", true, "createDate", true, "modifiedDate", true,
			"classNameId", true, "classPK", true, "accountId", true,
			"parentContactId", true, "emailAddress", true, "firstName", true,
			"middleName", true, "lastName", true, "prefixId", true, "suffixId",
			true, "male", true, "birthday", true, "smsSn", true, "aimSn", true,
			"facebookSn", true, "icqSn", true, "jabberSn", true, "msnSn", true,
			"mySpaceSn", true, "skypeSn", true, "twitterSn", true, "ymSn",
			true, "employeeStatusId", true, "employeeNumber", true, "jobTitle",
			true, "jobClass", true, "hoursOfOperation", true);
	}

	@Test
	public void testFetchByPrimaryKeyExisting() throws Exception {
		Contact newContact = addContact();

		Contact existingContact = _persistence.fetchByPrimaryKey(newContact.getPrimaryKey());

		Assert.assertEquals(existingContact, newContact);
	}

	@Test
	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		Contact missingContact = _persistence.fetchByPrimaryKey(pk);

		Assert.assertNull(missingContact);
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereAllPrimaryKeysExist()
		throws Exception {
		Contact newContact1 = addContact();
		Contact newContact2 = addContact();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newContact1.getPrimaryKey());
		primaryKeys.add(newContact2.getPrimaryKey());

		Map<Serializable, Contact> contacts = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(2, contacts.size());
		Assert.assertEquals(newContact1,
			contacts.get(newContact1.getPrimaryKey()));
		Assert.assertEquals(newContact2,
			contacts.get(newContact2.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereNoPrimaryKeysExist()
		throws Exception {
		long pk1 = RandomTestUtil.nextLong();

		long pk2 = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(pk1);
		primaryKeys.add(pk2);

		Map<Serializable, Contact> contacts = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(contacts.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereSomePrimaryKeysExist()
		throws Exception {
		Contact newContact = addContact();

		long pk = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newContact.getPrimaryKey());
		primaryKeys.add(pk);

		Map<Serializable, Contact> contacts = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, contacts.size());
		Assert.assertEquals(newContact, contacts.get(newContact.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithNoPrimaryKeys()
		throws Exception {
		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		Map<Serializable, Contact> contacts = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(contacts.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithOnePrimaryKey()
		throws Exception {
		Contact newContact = addContact();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newContact.getPrimaryKey());

		Map<Serializable, Contact> contacts = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, contacts.size());
		Assert.assertEquals(newContact, contacts.get(newContact.getPrimaryKey()));
	}

	@Test
	public void testActionableDynamicQuery() throws Exception {
		final IntegerWrapper count = new IntegerWrapper();

		ActionableDynamicQuery actionableDynamicQuery = ContactLocalServiceUtil.getActionableDynamicQuery();

		actionableDynamicQuery.setPerformActionMethod(new ActionableDynamicQuery.PerformActionMethod() {
				@Override
				public void performAction(Object object) {
					Contact contact = (Contact)object;

					Assert.assertNotNull(contact);

					count.increment();
				}
			});

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
				RandomTestUtil.nextLong()));

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
				new Object[] { RandomTestUtil.nextLong() }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	protected Contact addContact() throws Exception {
		long pk = RandomTestUtil.nextLong();

		Contact contact = _persistence.create(pk);

		contact.setMvccVersion(RandomTestUtil.nextLong());

		contact.setCompanyId(RandomTestUtil.nextLong());

		contact.setUserId(RandomTestUtil.nextLong());

		contact.setUserName(RandomTestUtil.randomString());

		contact.setCreateDate(RandomTestUtil.nextDate());

		contact.setModifiedDate(RandomTestUtil.nextDate());

		contact.setClassNameId(RandomTestUtil.nextLong());

		contact.setClassPK(RandomTestUtil.nextLong());

		contact.setAccountId(RandomTestUtil.nextLong());

		contact.setParentContactId(RandomTestUtil.nextLong());

		contact.setEmailAddress(RandomTestUtil.randomString());

		contact.setFirstName(RandomTestUtil.randomString());

		contact.setMiddleName(RandomTestUtil.randomString());

		contact.setLastName(RandomTestUtil.randomString());

		contact.setPrefixId(RandomTestUtil.nextInt());

		contact.setSuffixId(RandomTestUtil.nextInt());

		contact.setMale(RandomTestUtil.randomBoolean());

		contact.setBirthday(RandomTestUtil.nextDate());

		contact.setSmsSn(RandomTestUtil.randomString());

		contact.setAimSn(RandomTestUtil.randomString());

		contact.setFacebookSn(RandomTestUtil.randomString());

		contact.setIcqSn(RandomTestUtil.randomString());

		contact.setJabberSn(RandomTestUtil.randomString());

		contact.setMsnSn(RandomTestUtil.randomString());

		contact.setMySpaceSn(RandomTestUtil.randomString());

		contact.setSkypeSn(RandomTestUtil.randomString());

		contact.setTwitterSn(RandomTestUtil.randomString());

		contact.setYmSn(RandomTestUtil.randomString());

		contact.setEmployeeStatusId(RandomTestUtil.randomString());

		contact.setEmployeeNumber(RandomTestUtil.randomString());

		contact.setJobTitle(RandomTestUtil.randomString());

		contact.setJobClass(RandomTestUtil.randomString());

		contact.setHoursOfOperation(RandomTestUtil.randomString());

		_persistence.update(contact);

		return contact;
	}

	private static Log _log = LogFactoryUtil.getLog(ContactPersistenceTest.class);
	private ModelListener<Contact>[] _modelListeners;
	private ContactPersistence _persistence = (ContactPersistence)PortalBeanLocatorUtil.locate(ContactPersistence.class.getName());
	private TransactionalPersistenceAdvice _transactionalPersistenceAdvice = (TransactionalPersistenceAdvice)PortalBeanLocatorUtil.locate(TransactionalPersistenceAdvice.class.getName());
}