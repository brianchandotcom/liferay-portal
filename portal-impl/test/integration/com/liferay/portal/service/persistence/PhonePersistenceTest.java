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
import com.liferay.portal.kernel.util.Time;
import com.liferay.portal.model.Phone;
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
public class PhonePersistenceTest {
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
		long pk = ServiceTestUtil.nextLong();

		Phone newPhone = _persistence.create(pk);

		newPhone.setUuid(ServiceTestUtil.randomString());

		newPhone.setCompanyId(ServiceTestUtil.nextLong());

		newPhone.setUserId(ServiceTestUtil.nextLong());

		newPhone.setUserName(ServiceTestUtil.randomString());

		newPhone.setCreateDate(ServiceTestUtil.nextDate());

		newPhone.setModifiedDate(ServiceTestUtil.nextDate());

		newPhone.setClassNameId(ServiceTestUtil.nextLong());

		newPhone.setClassPK(ServiceTestUtil.nextLong());

		newPhone.setNumber(ServiceTestUtil.randomString());

		newPhone.setExtension(ServiceTestUtil.randomString());

		newPhone.setTypeId(ServiceTestUtil.nextInt());

		newPhone.setPrimary(ServiceTestUtil.randomBoolean());

		_persistence.update(newPhone);

		Phone existingPhone = _persistence.findByPrimaryKey(newPhone.getPrimaryKey());

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
	public void testFindByPrimaryKeyExisting() throws Exception {
		Phone newPhone = addPhone();

		Phone existingPhone = _persistence.findByPrimaryKey(newPhone.getPrimaryKey());

		Assert.assertEquals(existingPhone, newPhone);
	}

	@Test
	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = ServiceTestUtil.nextLong();

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
		return OrderByComparatorFactoryUtil.create("Phone", "uuid", true,
			"phoneId", true, "companyId", true, "userId", true, "userName",
			true, "createDate", true, "modifiedDate", true, "classNameId",
			true, "classPK", true, "number", true, "extension", true, "typeId",
			true, "primary", true);
	}

	@Test
	public void testFetchByPrimaryKeyExisting() throws Exception {
		Phone newPhone = addPhone();

		Phone existingPhone = _persistence.fetchByPrimaryKey(newPhone.getPrimaryKey());

		Assert.assertEquals(existingPhone, newPhone);
	}

	@Test
	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = ServiceTestUtil.nextLong();

		Phone missingPhone = _persistence.fetchByPrimaryKey(pk);

		Assert.assertNull(missingPhone);
	}

	@Test
	public void testActionableDynamicQuery() throws Exception {
		final IntegerWrapper count = new IntegerWrapper();

		ActionableDynamicQuery actionableDynamicQuery = new PhoneActionableDynamicQuery() {
				@Override
				protected void performAction(Object object) {
					Phone phone = (Phone)object;

					Assert.assertNotNull(phone);

					count.increment();
				}
			};

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
				ServiceTestUtil.nextLong()));

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
				new Object[] { ServiceTestUtil.nextLong() }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	protected Phone addPhone() throws Exception {
		long pk = ServiceTestUtil.nextLong();

		Phone phone = _persistence.create(pk);

		phone.setUuid(ServiceTestUtil.randomString());

		phone.setCompanyId(ServiceTestUtil.nextLong());

		phone.setUserId(ServiceTestUtil.nextLong());

		phone.setUserName(ServiceTestUtil.randomString());

		phone.setCreateDate(ServiceTestUtil.nextDate());

		phone.setModifiedDate(ServiceTestUtil.nextDate());

		phone.setClassNameId(ServiceTestUtil.nextLong());

		phone.setClassPK(ServiceTestUtil.nextLong());

		phone.setNumber(ServiceTestUtil.randomString());

		phone.setExtension(ServiceTestUtil.randomString());

		phone.setTypeId(ServiceTestUtil.nextInt());

		phone.setPrimary(ServiceTestUtil.randomBoolean());

		_persistence.update(phone);

		return phone;
	}

	private static Log _log = LogFactoryUtil.getLog(PhonePersistenceTest.class);
	private PhonePersistence _persistence = (PhonePersistence)PortalBeanLocatorUtil.locate(PhonePersistence.class.getName());
	private TransactionalPersistenceAdvice _transactionalPersistenceAdvice = (TransactionalPersistenceAdvice)PortalBeanLocatorUtil.locate(TransactionalPersistenceAdvice.class.getName());
}