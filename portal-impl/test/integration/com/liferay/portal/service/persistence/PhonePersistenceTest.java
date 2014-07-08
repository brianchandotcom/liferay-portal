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

import com.liferay.portal.NoSuchPhoneException;
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
import com.liferay.portal.model.ModelListener;
import com.liferay.portal.model.Phone;
import com.liferay.portal.service.PhoneLocalServiceUtil;
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
public class PhonePersistenceTest {
	@Before
	public void setUp() {
		_modelListeners = _persistence.getListeners();

		for (ModelListener<Phone> modelListener : _modelListeners) {
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

		for (ModelListener<Phone> modelListener : _modelListeners) {
			_persistence.registerListener(modelListener);
		}
	}

	@Test
	public void testCreate() throws Exception {
		long pk = RandomTestUtil.nextLong();

		Phone phone = _persistence.create(pk);

		Assert.assertNotNull(phone);

		Assert.assertEquals(phone.getPrimaryKey(), pk);
	}

	@Test
	public void testRemove() throws Exception {
		Phone newPhone = addPhone();

		_persistence.remove(newPhone);

		Phone existingPhone = _persistence.fetchByPrimaryKey(newPhone.getPrimaryKey());

		Assert.assertNull(existingPhone);
	}

	@Test
	public void testUpdateNew() throws Exception {
		addPhone();
	}

	@Test
	public void testUpdateExisting() throws Exception {
		long pk = RandomTestUtil.nextLong();

		Phone newPhone = _persistence.create(pk);

		newPhone.setMvccVersion(RandomTestUtil.nextLong());

		newPhone.setUuid(RandomTestUtil.randomString());

		newPhone.setCompanyId(RandomTestUtil.nextLong());

		newPhone.setUserId(RandomTestUtil.nextLong());

		newPhone.setUserName(RandomTestUtil.randomString());

		newPhone.setCreateDate(RandomTestUtil.nextDate());

		newPhone.setModifiedDate(RandomTestUtil.nextDate());

		newPhone.setClassNameId(RandomTestUtil.nextLong());

		newPhone.setClassPK(RandomTestUtil.nextLong());

		newPhone.setNumber(RandomTestUtil.randomString());

		newPhone.setExtension(RandomTestUtil.randomString());

		newPhone.setTypeId(RandomTestUtil.nextInt());

		newPhone.setPrimary(RandomTestUtil.randomBoolean());

		_persistence.update(newPhone);

		Phone existingPhone = _persistence.findByPrimaryKey(newPhone.getPrimaryKey());

		Assert.assertEquals(existingPhone.getMvccVersion(),
			newPhone.getMvccVersion());
		Assert.assertEquals(existingPhone.getUuid(), newPhone.getUuid());
		Assert.assertEquals(existingPhone.getPhoneId(), newPhone.getPhoneId());
		Assert.assertEquals(existingPhone.getCompanyId(),
			newPhone.getCompanyId());
		Assert.assertEquals(existingPhone.getUserId(), newPhone.getUserId());
		Assert.assertEquals(existingPhone.getUserName(), newPhone.getUserName());
		Assert.assertEquals(Time.getShortTimestamp(
				existingPhone.getCreateDate()),
			Time.getShortTimestamp(newPhone.getCreateDate()));
		Assert.assertEquals(Time.getShortTimestamp(
				existingPhone.getModifiedDate()),
			Time.getShortTimestamp(newPhone.getModifiedDate()));
		Assert.assertEquals(existingPhone.getClassNameId(),
			newPhone.getClassNameId());
		Assert.assertEquals(existingPhone.getClassPK(), newPhone.getClassPK());
		Assert.assertEquals(existingPhone.getNumber(), newPhone.getNumber());
		Assert.assertEquals(existingPhone.getExtension(),
			newPhone.getExtension());
		Assert.assertEquals(existingPhone.getTypeId(), newPhone.getTypeId());
		Assert.assertEquals(existingPhone.getPrimary(), newPhone.getPrimary());
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
		Phone newPhone = addPhone();

		Phone existingPhone = _persistence.findByPrimaryKey(newPhone.getPrimaryKey());

		Assert.assertEquals(existingPhone, newPhone);
	}

	@Test
	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		try {
			_persistence.findByPrimaryKey(pk);

			Assert.fail("Missing entity did not throw NoSuchPhoneException");
		}
		catch (NoSuchPhoneException nsee) {
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
		return OrderByComparatorFactoryUtil.create("Phone", "mvccVersion",
			true, "uuid", true, "phoneId", true, "companyId", true, "userId",
			true, "userName", true, "createDate", true, "modifiedDate", true,
			"classNameId", true, "classPK", true, "number", true, "extension",
			true, "typeId", true, "primary", true);
	}

	@Test
	public void testFetchByPrimaryKeyExisting() throws Exception {
		Phone newPhone = addPhone();

		Phone existingPhone = _persistence.fetchByPrimaryKey(newPhone.getPrimaryKey());

		Assert.assertEquals(existingPhone, newPhone);
	}

	@Test
	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		Phone missingPhone = _persistence.fetchByPrimaryKey(pk);

		Assert.assertNull(missingPhone);
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereAllPrimaryKeysExist()
		throws Exception {
		Phone newPhone1 = addPhone();
		Phone newPhone2 = addPhone();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newPhone1.getPrimaryKey());
		primaryKeys.add(newPhone2.getPrimaryKey());

		Map<Serializable, Phone> phones = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(2, phones.size());
		Assert.assertEquals(newPhone1, phones.get(newPhone1.getPrimaryKey()));
		Assert.assertEquals(newPhone2, phones.get(newPhone2.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereNoPrimaryKeysExist()
		throws Exception {
		long pk1 = RandomTestUtil.nextLong();

		long pk2 = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(pk1);
		primaryKeys.add(pk2);

		Map<Serializable, Phone> phones = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(phones.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereSomePrimaryKeysExist()
		throws Exception {
		Phone newPhone = addPhone();

		long pk = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newPhone.getPrimaryKey());
		primaryKeys.add(pk);

		Map<Serializable, Phone> phones = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, phones.size());
		Assert.assertEquals(newPhone, phones.get(newPhone.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithNoPrimaryKeys()
		throws Exception {
		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		Map<Serializable, Phone> phones = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(phones.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithOnePrimaryKey()
		throws Exception {
		Phone newPhone = addPhone();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newPhone.getPrimaryKey());

		Map<Serializable, Phone> phones = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, phones.size());
		Assert.assertEquals(newPhone, phones.get(newPhone.getPrimaryKey()));
	}

	@Test
	public void testActionableDynamicQuery() throws Exception {
		final IntegerWrapper count = new IntegerWrapper();

		ActionableDynamicQuery actionableDynamicQuery = PhoneLocalServiceUtil.getActionableDynamicQuery();

		actionableDynamicQuery.setPerformActionMethod(new ActionableDynamicQuery.PerformActionMethod() {
				@Override
				public void performAction(Object object) {
					Phone phone = (Phone)object;

					Assert.assertNotNull(phone);

					count.increment();
				}
			});

		actionableDynamicQuery.performActions();

		Assert.assertEquals(count.getValue(), _persistence.countAll());
	}

	@Test
	public void testDynamicQueryByPrimaryKeyExisting()
		throws Exception {
		Phone newPhone = addPhone();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(Phone.class,
				Phone.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("phoneId",
				newPhone.getPhoneId()));

		List<Phone> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		Phone existingPhone = result.get(0);

		Assert.assertEquals(existingPhone, newPhone);
	}

	@Test
	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(Phone.class,
				Phone.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("phoneId",
				RandomTestUtil.nextLong()));

		List<Phone> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
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

		Assert.assertEquals(1, result.size());

		Object existingPhoneId = result.get(0);

		Assert.assertEquals(existingPhoneId, newPhoneId);
	}

	@Test
	public void testDynamicQueryByProjectionMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(Phone.class,
				Phone.class.getClassLoader());

		dynamicQuery.setProjection(ProjectionFactoryUtil.property("phoneId"));

		dynamicQuery.add(RestrictionsFactoryUtil.in("phoneId",
				new Object[] { RandomTestUtil.nextLong() }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	protected Phone addPhone() throws Exception {
		long pk = RandomTestUtil.nextLong();

		Phone phone = _persistence.create(pk);

		phone.setMvccVersion(RandomTestUtil.nextLong());

		phone.setUuid(RandomTestUtil.randomString());

		phone.setCompanyId(RandomTestUtil.nextLong());

		phone.setUserId(RandomTestUtil.nextLong());

		phone.setUserName(RandomTestUtil.randomString());

		phone.setCreateDate(RandomTestUtil.nextDate());

		phone.setModifiedDate(RandomTestUtil.nextDate());

		phone.setClassNameId(RandomTestUtil.nextLong());

		phone.setClassPK(RandomTestUtil.nextLong());

		phone.setNumber(RandomTestUtil.randomString());

		phone.setExtension(RandomTestUtil.randomString());

		phone.setTypeId(RandomTestUtil.nextInt());

		phone.setPrimary(RandomTestUtil.randomBoolean());

		_persistence.update(phone);

		return phone;
	}

	private static Log _log = LogFactoryUtil.getLog(PhonePersistenceTest.class);
	private ModelListener<Phone>[] _modelListeners;
	private PhonePersistence _persistence = (PhonePersistence)PortalBeanLocatorUtil.locate(PhonePersistence.class.getName());
	private TransactionalPersistenceAdvice _transactionalPersistenceAdvice = (TransactionalPersistenceAdvice)PortalBeanLocatorUtil.locate(TransactionalPersistenceAdvice.class.getName());
}