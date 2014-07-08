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

import com.liferay.portal.NoSuchEmailAddressException;
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
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Time;
import com.liferay.portal.model.EmailAddress;
import com.liferay.portal.model.ModelListener;
import com.liferay.portal.service.EmailAddressLocalServiceUtil;
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
public class EmailAddressPersistenceTest {
	@Before
	public void setUp() {
		_modelListeners = _persistence.getListeners();

		for (ModelListener<EmailAddress> modelListener : _modelListeners) {
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

		for (ModelListener<EmailAddress> modelListener : _modelListeners) {
			_persistence.registerListener(modelListener);
		}
	}

	@Test
	public void testCreate() throws Exception {
		long pk = RandomTestUtil.nextLong();

		EmailAddress emailAddress = _persistence.create(pk);

		Assert.assertNotNull(emailAddress);

		Assert.assertEquals(emailAddress.getPrimaryKey(), pk);
	}

	@Test
	public void testRemove() throws Exception {
		EmailAddress newEmailAddress = addEmailAddress();

		_persistence.remove(newEmailAddress);

		EmailAddress existingEmailAddress = _persistence.fetchByPrimaryKey(newEmailAddress.getPrimaryKey());

		Assert.assertNull(existingEmailAddress);
	}

	@Test
	public void testUpdateNew() throws Exception {
		addEmailAddress();
	}

	@Test
	public void testUpdateExisting() throws Exception {
		long pk = RandomTestUtil.nextLong();

		EmailAddress newEmailAddress = _persistence.create(pk);

		newEmailAddress.setMvccVersion(RandomTestUtil.nextLong());

		newEmailAddress.setUuid(RandomTestUtil.randomString());

		newEmailAddress.setCompanyId(RandomTestUtil.nextLong());

		newEmailAddress.setUserId(RandomTestUtil.nextLong());

		newEmailAddress.setUserName(RandomTestUtil.randomString());

		newEmailAddress.setCreateDate(RandomTestUtil.nextDate());

		newEmailAddress.setModifiedDate(RandomTestUtil.nextDate());

		newEmailAddress.setClassNameId(RandomTestUtil.nextLong());

		newEmailAddress.setClassPK(RandomTestUtil.nextLong());

		newEmailAddress.setAddress(RandomTestUtil.randomString());

		newEmailAddress.setTypeId(RandomTestUtil.nextInt());

		newEmailAddress.setPrimary(RandomTestUtil.randomBoolean());

		_persistence.update(newEmailAddress);

		EmailAddress existingEmailAddress = _persistence.findByPrimaryKey(newEmailAddress.getPrimaryKey());

		Assert.assertEquals(existingEmailAddress.getMvccVersion(),
			newEmailAddress.getMvccVersion());
		Assert.assertEquals(existingEmailAddress.getUuid(),
			newEmailAddress.getUuid());
		Assert.assertEquals(existingEmailAddress.getEmailAddressId(),
			newEmailAddress.getEmailAddressId());
		Assert.assertEquals(existingEmailAddress.getCompanyId(),
			newEmailAddress.getCompanyId());
		Assert.assertEquals(existingEmailAddress.getUserId(),
			newEmailAddress.getUserId());
		Assert.assertEquals(existingEmailAddress.getUserName(),
			newEmailAddress.getUserName());
		Assert.assertEquals(Time.getShortTimestamp(
				existingEmailAddress.getCreateDate()),
			Time.getShortTimestamp(newEmailAddress.getCreateDate()));
		Assert.assertEquals(Time.getShortTimestamp(
				existingEmailAddress.getModifiedDate()),
			Time.getShortTimestamp(newEmailAddress.getModifiedDate()));
		Assert.assertEquals(existingEmailAddress.getClassNameId(),
			newEmailAddress.getClassNameId());
		Assert.assertEquals(existingEmailAddress.getClassPK(),
			newEmailAddress.getClassPK());
		Assert.assertEquals(existingEmailAddress.getAddress(),
			newEmailAddress.getAddress());
		Assert.assertEquals(existingEmailAddress.getTypeId(),
			newEmailAddress.getTypeId());
		Assert.assertEquals(existingEmailAddress.getPrimary(),
			newEmailAddress.getPrimary());
	}

	@Test
	public void testCountByUuid() {
		try {
			_persistence.countByUuid(StringPool.BLANK);

			_persistence.countByUuid(StringPool.NULL);

			_persistence.countByUuid((String)null);
		}
		catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void testCountByUuid_C() {
		try {
			_persistence.countByUuid_C(StringPool.BLANK,
				RandomTestUtil.nextLong());

			_persistence.countByUuid_C(StringPool.NULL, 0L);

			_persistence.countByUuid_C((String)null, 0L);
		}
		catch (Exception e) {
			Assert.fail(e.getMessage());
		}
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
	public void testCountByUserId() {
		try {
			_persistence.countByUserId(RandomTestUtil.nextLong());

			_persistence.countByUserId(0L);
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
	public void testCountByC_C_C() {
		try {
			_persistence.countByC_C_C(RandomTestUtil.nextLong(),
				RandomTestUtil.nextLong(), RandomTestUtil.nextLong());

			_persistence.countByC_C_C(0L, 0L, 0L);
		}
		catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void testCountByC_C_C_P() {
		try {
			_persistence.countByC_C_C_P(RandomTestUtil.nextLong(),
				RandomTestUtil.nextLong(), RandomTestUtil.nextLong(),
				RandomTestUtil.randomBoolean());

			_persistence.countByC_C_C_P(0L, 0L, 0L,
				RandomTestUtil.randomBoolean());
		}
		catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void testFindByPrimaryKeyExisting() throws Exception {
		EmailAddress newEmailAddress = addEmailAddress();

		EmailAddress existingEmailAddress = _persistence.findByPrimaryKey(newEmailAddress.getPrimaryKey());

		Assert.assertEquals(existingEmailAddress, newEmailAddress);
	}

	@Test
	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		try {
			_persistence.findByPrimaryKey(pk);

			Assert.fail(
				"Missing entity did not throw NoSuchEmailAddressException");
		}
		catch (NoSuchEmailAddressException nsee) {
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
		return OrderByComparatorFactoryUtil.create("EmailAddress",
			"mvccVersion", true, "uuid", true, "emailAddressId", true,
			"companyId", true, "userId", true, "userName", true, "createDate",
			true, "modifiedDate", true, "classNameId", true, "classPK", true,
			"address", true, "typeId", true, "primary", true);
	}

	@Test
	public void testFetchByPrimaryKeyExisting() throws Exception {
		EmailAddress newEmailAddress = addEmailAddress();

		EmailAddress existingEmailAddress = _persistence.fetchByPrimaryKey(newEmailAddress.getPrimaryKey());

		Assert.assertEquals(existingEmailAddress, newEmailAddress);
	}

	@Test
	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		EmailAddress missingEmailAddress = _persistence.fetchByPrimaryKey(pk);

		Assert.assertNull(missingEmailAddress);
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereAllPrimaryKeysExist()
		throws Exception {
		EmailAddress newEmailAddress1 = addEmailAddress();
		EmailAddress newEmailAddress2 = addEmailAddress();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newEmailAddress1.getPrimaryKey());
		primaryKeys.add(newEmailAddress2.getPrimaryKey());

		Map<Serializable, EmailAddress> emailAddresses = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(2, emailAddresses.size());
		Assert.assertEquals(newEmailAddress1,
			emailAddresses.get(newEmailAddress1.getPrimaryKey()));
		Assert.assertEquals(newEmailAddress2,
			emailAddresses.get(newEmailAddress2.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereNoPrimaryKeysExist()
		throws Exception {
		long pk1 = RandomTestUtil.nextLong();

		long pk2 = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(pk1);
		primaryKeys.add(pk2);

		Map<Serializable, EmailAddress> emailAddresses = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(emailAddresses.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereSomePrimaryKeysExist()
		throws Exception {
		EmailAddress newEmailAddress = addEmailAddress();

		long pk = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newEmailAddress.getPrimaryKey());
		primaryKeys.add(pk);

		Map<Serializable, EmailAddress> emailAddresses = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, emailAddresses.size());
		Assert.assertEquals(newEmailAddress,
			emailAddresses.get(newEmailAddress.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithNoPrimaryKeys()
		throws Exception {
		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		Map<Serializable, EmailAddress> emailAddresses = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(emailAddresses.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithOnePrimaryKey()
		throws Exception {
		EmailAddress newEmailAddress = addEmailAddress();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newEmailAddress.getPrimaryKey());

		Map<Serializable, EmailAddress> emailAddresses = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, emailAddresses.size());
		Assert.assertEquals(newEmailAddress,
			emailAddresses.get(newEmailAddress.getPrimaryKey()));
	}

	@Test
	public void testActionableDynamicQuery() throws Exception {
		final IntegerWrapper count = new IntegerWrapper();

		ActionableDynamicQuery actionableDynamicQuery = EmailAddressLocalServiceUtil.getActionableDynamicQuery();

		actionableDynamicQuery.setPerformActionMethod(new ActionableDynamicQuery.PerformActionMethod() {
				@Override
				public void performAction(Object object) {
					EmailAddress emailAddress = (EmailAddress)object;

					Assert.assertNotNull(emailAddress);

					count.increment();
				}
			});

		actionableDynamicQuery.performActions();

		Assert.assertEquals(count.getValue(), _persistence.countAll());
	}

	@Test
	public void testDynamicQueryByPrimaryKeyExisting()
		throws Exception {
		EmailAddress newEmailAddress = addEmailAddress();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(EmailAddress.class,
				EmailAddress.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("emailAddressId",
				newEmailAddress.getEmailAddressId()));

		List<EmailAddress> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		EmailAddress existingEmailAddress = result.get(0);

		Assert.assertEquals(existingEmailAddress, newEmailAddress);
	}

	@Test
	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(EmailAddress.class,
				EmailAddress.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("emailAddressId",
				RandomTestUtil.nextLong()));

		List<EmailAddress> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
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

		Assert.assertEquals(1, result.size());

		Object existingEmailAddressId = result.get(0);

		Assert.assertEquals(existingEmailAddressId, newEmailAddressId);
	}

	@Test
	public void testDynamicQueryByProjectionMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(EmailAddress.class,
				EmailAddress.class.getClassLoader());

		dynamicQuery.setProjection(ProjectionFactoryUtil.property(
				"emailAddressId"));

		dynamicQuery.add(RestrictionsFactoryUtil.in("emailAddressId",
				new Object[] { RandomTestUtil.nextLong() }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	protected EmailAddress addEmailAddress() throws Exception {
		long pk = RandomTestUtil.nextLong();

		EmailAddress emailAddress = _persistence.create(pk);

		emailAddress.setMvccVersion(RandomTestUtil.nextLong());

		emailAddress.setUuid(RandomTestUtil.randomString());

		emailAddress.setCompanyId(RandomTestUtil.nextLong());

		emailAddress.setUserId(RandomTestUtil.nextLong());

		emailAddress.setUserName(RandomTestUtil.randomString());

		emailAddress.setCreateDate(RandomTestUtil.nextDate());

		emailAddress.setModifiedDate(RandomTestUtil.nextDate());

		emailAddress.setClassNameId(RandomTestUtil.nextLong());

		emailAddress.setClassPK(RandomTestUtil.nextLong());

		emailAddress.setAddress(RandomTestUtil.randomString());

		emailAddress.setTypeId(RandomTestUtil.nextInt());

		emailAddress.setPrimary(RandomTestUtil.randomBoolean());

		_persistence.update(emailAddress);

		return emailAddress;
	}

	private static Log _log = LogFactoryUtil.getLog(EmailAddressPersistenceTest.class);
	private ModelListener<EmailAddress>[] _modelListeners;
	private EmailAddressPersistence _persistence = (EmailAddressPersistence)PortalBeanLocatorUtil.locate(EmailAddressPersistence.class.getName());
	private TransactionalPersistenceAdvice _transactionalPersistenceAdvice = (TransactionalPersistenceAdvice)PortalBeanLocatorUtil.locate(TransactionalPersistenceAdvice.class.getName());
}