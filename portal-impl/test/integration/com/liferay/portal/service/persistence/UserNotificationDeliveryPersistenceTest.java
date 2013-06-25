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

import com.liferay.portal.NoSuchUserNotificationDeliveryException;
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
import com.liferay.portal.model.UserNotificationDelivery;
import com.liferay.portal.model.impl.UserNotificationDeliveryModelImpl;
import com.liferay.portal.service.ServiceTestUtil;
import com.liferay.portal.service.persistence.BasePersistence;
import com.liferay.portal.service.persistence.PersistenceExecutionTestListener;
import com.liferay.portal.test.LiferayPersistenceIntegrationJUnitTestRunner;
import com.liferay.portal.test.persistence.TransactionalPersistenceAdvice;
import com.liferay.portal.util.PropsValues;

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
public class UserNotificationDeliveryPersistenceTest {
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

		UserNotificationDelivery userNotificationDelivery = _persistence.create(pk);

		Assert.assertNotNull(userNotificationDelivery);

		Assert.assertEquals(userNotificationDelivery.getPrimaryKey(), pk);
	}

	@Test
	public void testRemove() throws Exception {
		UserNotificationDelivery newUserNotificationDelivery = addUserNotificationDelivery();

		_persistence.remove(newUserNotificationDelivery);

		UserNotificationDelivery existingUserNotificationDelivery = _persistence.fetchByPrimaryKey(newUserNotificationDelivery.getPrimaryKey());

		Assert.assertNull(existingUserNotificationDelivery);
	}

	@Test
	public void testUpdateNew() throws Exception {
		addUserNotificationDelivery();
	}

	@Test
	public void testUpdateExisting() throws Exception {
		long pk = ServiceTestUtil.nextLong();

		UserNotificationDelivery newUserNotificationDelivery = _persistence.create(pk);

		newUserNotificationDelivery.setCompanyId(ServiceTestUtil.nextLong());

		newUserNotificationDelivery.setUserId(ServiceTestUtil.nextLong());

		newUserNotificationDelivery.setClassNameId(ServiceTestUtil.nextLong());

		newUserNotificationDelivery.setType(ServiceTestUtil.nextInt());

		newUserNotificationDelivery.setEmail(ServiceTestUtil.randomBoolean());

		newUserNotificationDelivery.setSms(ServiceTestUtil.randomBoolean());

		newUserNotificationDelivery.setWebsite(ServiceTestUtil.randomBoolean());

		_persistence.update(newUserNotificationDelivery);

		UserNotificationDelivery existingUserNotificationDelivery = _persistence.findByPrimaryKey(newUserNotificationDelivery.getPrimaryKey());

		Assert.assertEquals(existingUserNotificationDelivery.getUserNotificationDeliveryId(),
			newUserNotificationDelivery.getUserNotificationDeliveryId());
		Assert.assertEquals(existingUserNotificationDelivery.getCompanyId(),
			newUserNotificationDelivery.getCompanyId());
		Assert.assertEquals(existingUserNotificationDelivery.getUserId(),
			newUserNotificationDelivery.getUserId());
		Assert.assertEquals(existingUserNotificationDelivery.getClassNameId(),
			newUserNotificationDelivery.getClassNameId());
		Assert.assertEquals(existingUserNotificationDelivery.getType(),
			newUserNotificationDelivery.getType());
		Assert.assertEquals(existingUserNotificationDelivery.getEmail(),
			newUserNotificationDelivery.getEmail());
		Assert.assertEquals(existingUserNotificationDelivery.getSms(),
			newUserNotificationDelivery.getSms());
		Assert.assertEquals(existingUserNotificationDelivery.getWebsite(),
			newUserNotificationDelivery.getWebsite());
	}

	@Test
	public void testFindByPrimaryKeyExisting() throws Exception {
		UserNotificationDelivery newUserNotificationDelivery = addUserNotificationDelivery();

		UserNotificationDelivery existingUserNotificationDelivery = _persistence.findByPrimaryKey(newUserNotificationDelivery.getPrimaryKey());

		Assert.assertEquals(existingUserNotificationDelivery,
			newUserNotificationDelivery);
	}

	@Test
	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = ServiceTestUtil.nextLong();

		try {
			_persistence.findByPrimaryKey(pk);

			Assert.fail(
				"Missing entity did not throw NoSuchUserNotificationDeliveryException");
		}
		catch (NoSuchUserNotificationDeliveryException nsee) {
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
		return OrderByComparatorFactoryUtil.create("UserNotificationDelivery",
			"userNotificationDeliveryId", true, "companyId", true, "userId",
			true, "classNameId", true, "type", true, "email", true, "sms",
			true, "website", true);
	}

	@Test
	public void testFetchByPrimaryKeyExisting() throws Exception {
		UserNotificationDelivery newUserNotificationDelivery = addUserNotificationDelivery();

		UserNotificationDelivery existingUserNotificationDelivery = _persistence.fetchByPrimaryKey(newUserNotificationDelivery.getPrimaryKey());

		Assert.assertEquals(existingUserNotificationDelivery,
			newUserNotificationDelivery);
	}

	@Test
	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = ServiceTestUtil.nextLong();

		UserNotificationDelivery missingUserNotificationDelivery = _persistence.fetchByPrimaryKey(pk);

		Assert.assertNull(missingUserNotificationDelivery);
	}

	@Test
	public void testActionableDynamicQuery() throws Exception {
		final IntegerWrapper count = new IntegerWrapper();

		ActionableDynamicQuery actionableDynamicQuery = new UserNotificationDeliveryActionableDynamicQuery() {
				@Override
				protected void performAction(Object object) {
					UserNotificationDelivery userNotificationDelivery = (UserNotificationDelivery)object;

					Assert.assertNotNull(userNotificationDelivery);

					count.increment();
				}
			};

		actionableDynamicQuery.performActions();

		Assert.assertEquals(count.getValue(), _persistence.countAll());
	}

	@Test
	public void testDynamicQueryByPrimaryKeyExisting()
		throws Exception {
		UserNotificationDelivery newUserNotificationDelivery = addUserNotificationDelivery();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(UserNotificationDelivery.class,
				UserNotificationDelivery.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq(
				"userNotificationDeliveryId",
				newUserNotificationDelivery.getUserNotificationDeliveryId()));

		List<UserNotificationDelivery> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		UserNotificationDelivery existingUserNotificationDelivery = result.get(0);

		Assert.assertEquals(existingUserNotificationDelivery,
			newUserNotificationDelivery);
	}

	@Test
	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(UserNotificationDelivery.class,
				UserNotificationDelivery.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq(
				"userNotificationDeliveryId", ServiceTestUtil.nextLong()));

		List<UserNotificationDelivery> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testDynamicQueryByProjectionExisting()
		throws Exception {
		UserNotificationDelivery newUserNotificationDelivery = addUserNotificationDelivery();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(UserNotificationDelivery.class,
				UserNotificationDelivery.class.getClassLoader());

		dynamicQuery.setProjection(ProjectionFactoryUtil.property(
				"userNotificationDeliveryId"));

		Object newUserNotificationDeliveryId = newUserNotificationDelivery.getUserNotificationDeliveryId();

		dynamicQuery.add(RestrictionsFactoryUtil.in(
				"userNotificationDeliveryId",
				new Object[] { newUserNotificationDeliveryId }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		Object existingUserNotificationDeliveryId = result.get(0);

		Assert.assertEquals(existingUserNotificationDeliveryId,
			newUserNotificationDeliveryId);
	}

	@Test
	public void testDynamicQueryByProjectionMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(UserNotificationDelivery.class,
				UserNotificationDelivery.class.getClassLoader());

		dynamicQuery.setProjection(ProjectionFactoryUtil.property(
				"userNotificationDeliveryId"));

		dynamicQuery.add(RestrictionsFactoryUtil.in(
				"userNotificationDeliveryId",
				new Object[] { ServiceTestUtil.nextLong() }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testResetOriginalValues() throws Exception {
		if (!PropsValues.HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE) {
			return;
		}

		UserNotificationDelivery newUserNotificationDelivery = addUserNotificationDelivery();

		_persistence.clearCache();

		UserNotificationDeliveryModelImpl existingUserNotificationDeliveryModelImpl =
			(UserNotificationDeliveryModelImpl)_persistence.findByPrimaryKey(newUserNotificationDelivery.getPrimaryKey());

		Assert.assertEquals(existingUserNotificationDeliveryModelImpl.getUserId(),
			existingUserNotificationDeliveryModelImpl.getOriginalUserId());
		Assert.assertEquals(existingUserNotificationDeliveryModelImpl.getClassNameId(),
			existingUserNotificationDeliveryModelImpl.getOriginalClassNameId());
		Assert.assertEquals(existingUserNotificationDeliveryModelImpl.getType(),
			existingUserNotificationDeliveryModelImpl.getOriginalType());
	}

	protected UserNotificationDelivery addUserNotificationDelivery()
		throws Exception {
		long pk = ServiceTestUtil.nextLong();

		UserNotificationDelivery userNotificationDelivery = _persistence.create(pk);

		userNotificationDelivery.setCompanyId(ServiceTestUtil.nextLong());

		userNotificationDelivery.setUserId(ServiceTestUtil.nextLong());

		userNotificationDelivery.setClassNameId(ServiceTestUtil.nextLong());

		userNotificationDelivery.setType(ServiceTestUtil.nextInt());

		userNotificationDelivery.setEmail(ServiceTestUtil.randomBoolean());

		userNotificationDelivery.setSms(ServiceTestUtil.randomBoolean());

		userNotificationDelivery.setWebsite(ServiceTestUtil.randomBoolean());

		_persistence.update(userNotificationDelivery);

		return userNotificationDelivery;
	}

	private static Log _log = LogFactoryUtil.getLog(UserNotificationDeliveryPersistenceTest.class);
	private UserNotificationDeliveryPersistence _persistence = (UserNotificationDeliveryPersistence)PortalBeanLocatorUtil.locate(UserNotificationDeliveryPersistence.class.getName());
	private TransactionalPersistenceAdvice _transactionalPersistenceAdvice = (TransactionalPersistenceAdvice)PortalBeanLocatorUtil.locate(TransactionalPersistenceAdvice.class.getName());
}