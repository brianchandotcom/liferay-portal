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

import com.liferay.portal.NoSuchSubscriptionException;
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
import com.liferay.portal.model.Subscription;
import com.liferay.portal.model.impl.SubscriptionModelImpl;
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
public class SubscriptionPersistenceTest {
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

		Subscription subscription = _persistence.create(pk);

		Assert.assertNotNull(subscription);

		Assert.assertEquals(subscription.getPrimaryKey(), pk);
	}

	@Test
	public void testRemove() throws Exception {
		Subscription newSubscription = addSubscription();

		_persistence.remove(newSubscription);

		Subscription existingSubscription = _persistence.fetchByPrimaryKey(newSubscription.getPrimaryKey());

		Assert.assertNull(existingSubscription);
	}

	@Test
	public void testUpdateNew() throws Exception {
		addSubscription();
	}

	@Test
	public void testUpdateExisting() throws Exception {
		long pk = ServiceTestUtil.nextLong();

		Subscription newSubscription = _persistence.create(pk);

		newSubscription.setCompanyId(ServiceTestUtil.nextLong());

		newSubscription.setUserId(ServiceTestUtil.nextLong());

		newSubscription.setUserName(ServiceTestUtil.randomString());

		newSubscription.setCreateDate(ServiceTestUtil.nextDate());

		newSubscription.setModifiedDate(ServiceTestUtil.nextDate());

		newSubscription.setClassNameId(ServiceTestUtil.nextLong());

		newSubscription.setClassPK(ServiceTestUtil.nextLong());

		newSubscription.setFrequency(ServiceTestUtil.randomString());

		newSubscription.setMvccVersion(ServiceTestUtil.nextLong());

		_persistence.update(newSubscription);

		Subscription existingSubscription = _persistence.findByPrimaryKey(newSubscription.getPrimaryKey());

		Assert.assertEquals(existingSubscription.getSubscriptionId(),
			newSubscription.getSubscriptionId());
		Assert.assertEquals(existingSubscription.getCompanyId(),
			newSubscription.getCompanyId());
		Assert.assertEquals(existingSubscription.getUserId(),
			newSubscription.getUserId());
		Assert.assertEquals(existingSubscription.getUserName(),
			newSubscription.getUserName());
		Assert.assertEquals(Time.getShortTimestamp(
				existingSubscription.getCreateDate()),
			Time.getShortTimestamp(newSubscription.getCreateDate()));
		Assert.assertEquals(Time.getShortTimestamp(
				existingSubscription.getModifiedDate()),
			Time.getShortTimestamp(newSubscription.getModifiedDate()));
		Assert.assertEquals(existingSubscription.getClassNameId(),
			newSubscription.getClassNameId());
		Assert.assertEquals(existingSubscription.getClassPK(),
			newSubscription.getClassPK());
		Assert.assertEquals(existingSubscription.getFrequency(),
			newSubscription.getFrequency());
		Assert.assertEquals(existingSubscription.getMvccVersion(),
			newSubscription.getMvccVersion());
	}

	@Test
	public void testFindByPrimaryKeyExisting() throws Exception {
		Subscription newSubscription = addSubscription();

		Subscription existingSubscription = _persistence.findByPrimaryKey(newSubscription.getPrimaryKey());

		Assert.assertEquals(existingSubscription, newSubscription);
	}

	@Test
	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = ServiceTestUtil.nextLong();

		try {
			_persistence.findByPrimaryKey(pk);

			Assert.fail(
				"Missing entity did not throw NoSuchSubscriptionException");
		}
		catch (NoSuchSubscriptionException nsee) {
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
		return OrderByComparatorFactoryUtil.create("Subscription",
			"subscriptionId", true, "companyId", true, "userId", true,
			"userName", true, "createDate", true, "modifiedDate", true,
			"classNameId", true, "classPK", true, "frequency", true,
			"mvccVersion", true);
	}

	@Test
	public void testFetchByPrimaryKeyExisting() throws Exception {
		Subscription newSubscription = addSubscription();

		Subscription existingSubscription = _persistence.fetchByPrimaryKey(newSubscription.getPrimaryKey());

		Assert.assertEquals(existingSubscription, newSubscription);
	}

	@Test
	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = ServiceTestUtil.nextLong();

		Subscription missingSubscription = _persistence.fetchByPrimaryKey(pk);

		Assert.assertNull(missingSubscription);
	}

	@Test
	public void testActionableDynamicQuery() throws Exception {
		final IntegerWrapper count = new IntegerWrapper();

		ActionableDynamicQuery actionableDynamicQuery = new SubscriptionActionableDynamicQuery() {
				@Override
				protected void performAction(Object object) {
					Subscription subscription = (Subscription)object;

					Assert.assertNotNull(subscription);

					count.increment();
				}
			};

		actionableDynamicQuery.performActions();

		Assert.assertEquals(count.getValue(), _persistence.countAll());
	}

	@Test
	public void testDynamicQueryByPrimaryKeyExisting()
		throws Exception {
		Subscription newSubscription = addSubscription();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(Subscription.class,
				Subscription.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("subscriptionId",
				newSubscription.getSubscriptionId()));

		List<Subscription> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		Subscription existingSubscription = result.get(0);

		Assert.assertEquals(existingSubscription, newSubscription);
	}

	@Test
	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(Subscription.class,
				Subscription.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("subscriptionId",
				ServiceTestUtil.nextLong()));

		List<Subscription> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testDynamicQueryByProjectionExisting()
		throws Exception {
		Subscription newSubscription = addSubscription();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(Subscription.class,
				Subscription.class.getClassLoader());

		dynamicQuery.setProjection(ProjectionFactoryUtil.property(
				"subscriptionId"));

		Object newSubscriptionId = newSubscription.getSubscriptionId();

		dynamicQuery.add(RestrictionsFactoryUtil.in("subscriptionId",
				new Object[] { newSubscriptionId }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		Object existingSubscriptionId = result.get(0);

		Assert.assertEquals(existingSubscriptionId, newSubscriptionId);
	}

	@Test
	public void testDynamicQueryByProjectionMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(Subscription.class,
				Subscription.class.getClassLoader());

		dynamicQuery.setProjection(ProjectionFactoryUtil.property(
				"subscriptionId"));

		dynamicQuery.add(RestrictionsFactoryUtil.in("subscriptionId",
				new Object[] { ServiceTestUtil.nextLong() }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testResetOriginalValues() throws Exception {
		if (!PropsValues.HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE) {
			return;
		}

		Subscription newSubscription = addSubscription();

		_persistence.clearCache();

		SubscriptionModelImpl existingSubscriptionModelImpl = (SubscriptionModelImpl)_persistence.findByPrimaryKey(newSubscription.getPrimaryKey());

		Assert.assertEquals(existingSubscriptionModelImpl.getCompanyId(),
			existingSubscriptionModelImpl.getOriginalCompanyId());
		Assert.assertEquals(existingSubscriptionModelImpl.getUserId(),
			existingSubscriptionModelImpl.getOriginalUserId());
		Assert.assertEquals(existingSubscriptionModelImpl.getClassNameId(),
			existingSubscriptionModelImpl.getOriginalClassNameId());
		Assert.assertEquals(existingSubscriptionModelImpl.getClassPK(),
			existingSubscriptionModelImpl.getOriginalClassPK());
	}

	protected Subscription addSubscription() throws Exception {
		long pk = ServiceTestUtil.nextLong();

		Subscription subscription = _persistence.create(pk);

		subscription.setCompanyId(ServiceTestUtil.nextLong());

		subscription.setUserId(ServiceTestUtil.nextLong());

		subscription.setUserName(ServiceTestUtil.randomString());

		subscription.setCreateDate(ServiceTestUtil.nextDate());

		subscription.setModifiedDate(ServiceTestUtil.nextDate());

		subscription.setClassNameId(ServiceTestUtil.nextLong());

		subscription.setClassPK(ServiceTestUtil.nextLong());

		subscription.setFrequency(ServiceTestUtil.randomString());

		subscription.setMvccVersion(ServiceTestUtil.nextLong());

		_persistence.update(subscription);

		return subscription;
	}

	private static Log _log = LogFactoryUtil.getLog(SubscriptionPersistenceTest.class);
	private SubscriptionPersistence _persistence = (SubscriptionPersistence)PortalBeanLocatorUtil.locate(SubscriptionPersistence.class.getName());
	private TransactionalPersistenceAdvice _transactionalPersistenceAdvice = (TransactionalPersistenceAdvice)PortalBeanLocatorUtil.locate(TransactionalPersistenceAdvice.class.getName());
}