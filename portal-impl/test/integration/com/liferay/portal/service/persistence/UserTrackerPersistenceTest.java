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

import com.liferay.portal.NoSuchUserTrackerException;
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
import com.liferay.portal.model.UserTracker;
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
public class UserTrackerPersistenceTest {
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

		UserTracker userTracker = _persistence.create(pk);

		Assert.assertNotNull(userTracker);

		Assert.assertEquals(userTracker.getPrimaryKey(), pk);
	}

	@Test
	public void testRemove() throws Exception {
		UserTracker newUserTracker = addUserTracker();

		_persistence.remove(newUserTracker);

		UserTracker existingUserTracker = _persistence.fetchByPrimaryKey(newUserTracker.getPrimaryKey());

		Assert.assertNull(existingUserTracker);
	}

	@Test
	public void testUpdateNew() throws Exception {
		addUserTracker();
	}

	@Test
	public void testUpdateExisting() throws Exception {
		long pk = ServiceTestUtil.nextLong();

		UserTracker newUserTracker = _persistence.create(pk);

		newUserTracker.setCompanyId(ServiceTestUtil.nextLong());

		newUserTracker.setUserId(ServiceTestUtil.nextLong());

		newUserTracker.setModifiedDate(ServiceTestUtil.nextDate());

		newUserTracker.setSessionId(ServiceTestUtil.randomString());

		newUserTracker.setRemoteAddr(ServiceTestUtil.randomString());

		newUserTracker.setRemoteHost(ServiceTestUtil.randomString());

		newUserTracker.setUserAgent(ServiceTestUtil.randomString());

		newUserTracker.setMvccVersion(ServiceTestUtil.nextLong());

		_persistence.update(newUserTracker);

		UserTracker existingUserTracker = _persistence.findByPrimaryKey(newUserTracker.getPrimaryKey());

		Assert.assertEquals(existingUserTracker.getUserTrackerId(),
			newUserTracker.getUserTrackerId());
		Assert.assertEquals(existingUserTracker.getCompanyId(),
			newUserTracker.getCompanyId());
		Assert.assertEquals(existingUserTracker.getUserId(),
			newUserTracker.getUserId());
		Assert.assertEquals(Time.getShortTimestamp(
				existingUserTracker.getModifiedDate()),
			Time.getShortTimestamp(newUserTracker.getModifiedDate()));
		Assert.assertEquals(existingUserTracker.getSessionId(),
			newUserTracker.getSessionId());
		Assert.assertEquals(existingUserTracker.getRemoteAddr(),
			newUserTracker.getRemoteAddr());
		Assert.assertEquals(existingUserTracker.getRemoteHost(),
			newUserTracker.getRemoteHost());
		Assert.assertEquals(existingUserTracker.getUserAgent(),
			newUserTracker.getUserAgent());
		Assert.assertEquals(existingUserTracker.getMvccVersion(),
			newUserTracker.getMvccVersion());
	}

	@Test
	public void testFindByPrimaryKeyExisting() throws Exception {
		UserTracker newUserTracker = addUserTracker();

		UserTracker existingUserTracker = _persistence.findByPrimaryKey(newUserTracker.getPrimaryKey());

		Assert.assertEquals(existingUserTracker, newUserTracker);
	}

	@Test
	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = ServiceTestUtil.nextLong();

		try {
			_persistence.findByPrimaryKey(pk);

			Assert.fail(
				"Missing entity did not throw NoSuchUserTrackerException");
		}
		catch (NoSuchUserTrackerException nsee) {
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
		return OrderByComparatorFactoryUtil.create("UserTracker",
			"userTrackerId", true, "companyId", true, "userId", true,
			"modifiedDate", true, "sessionId", true, "remoteAddr", true,
			"remoteHost", true, "userAgent", true, "mvccVersion", true);
	}

	@Test
	public void testFetchByPrimaryKeyExisting() throws Exception {
		UserTracker newUserTracker = addUserTracker();

		UserTracker existingUserTracker = _persistence.fetchByPrimaryKey(newUserTracker.getPrimaryKey());

		Assert.assertEquals(existingUserTracker, newUserTracker);
	}

	@Test
	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = ServiceTestUtil.nextLong();

		UserTracker missingUserTracker = _persistence.fetchByPrimaryKey(pk);

		Assert.assertNull(missingUserTracker);
	}

	@Test
	public void testActionableDynamicQuery() throws Exception {
		final IntegerWrapper count = new IntegerWrapper();

		ActionableDynamicQuery actionableDynamicQuery = new UserTrackerActionableDynamicQuery() {
				@Override
				protected void performAction(Object object) {
					UserTracker userTracker = (UserTracker)object;

					Assert.assertNotNull(userTracker);

					count.increment();
				}
			};

		actionableDynamicQuery.performActions();

		Assert.assertEquals(count.getValue(), _persistence.countAll());
	}

	@Test
	public void testDynamicQueryByPrimaryKeyExisting()
		throws Exception {
		UserTracker newUserTracker = addUserTracker();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(UserTracker.class,
				UserTracker.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("userTrackerId",
				newUserTracker.getUserTrackerId()));

		List<UserTracker> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		UserTracker existingUserTracker = result.get(0);

		Assert.assertEquals(existingUserTracker, newUserTracker);
	}

	@Test
	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(UserTracker.class,
				UserTracker.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("userTrackerId",
				ServiceTestUtil.nextLong()));

		List<UserTracker> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testDynamicQueryByProjectionExisting()
		throws Exception {
		UserTracker newUserTracker = addUserTracker();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(UserTracker.class,
				UserTracker.class.getClassLoader());

		dynamicQuery.setProjection(ProjectionFactoryUtil.property(
				"userTrackerId"));

		Object newUserTrackerId = newUserTracker.getUserTrackerId();

		dynamicQuery.add(RestrictionsFactoryUtil.in("userTrackerId",
				new Object[] { newUserTrackerId }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		Object existingUserTrackerId = result.get(0);

		Assert.assertEquals(existingUserTrackerId, newUserTrackerId);
	}

	@Test
	public void testDynamicQueryByProjectionMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(UserTracker.class,
				UserTracker.class.getClassLoader());

		dynamicQuery.setProjection(ProjectionFactoryUtil.property(
				"userTrackerId"));

		dynamicQuery.add(RestrictionsFactoryUtil.in("userTrackerId",
				new Object[] { ServiceTestUtil.nextLong() }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	protected UserTracker addUserTracker() throws Exception {
		long pk = ServiceTestUtil.nextLong();

		UserTracker userTracker = _persistence.create(pk);

		userTracker.setCompanyId(ServiceTestUtil.nextLong());

		userTracker.setUserId(ServiceTestUtil.nextLong());

		userTracker.setModifiedDate(ServiceTestUtil.nextDate());

		userTracker.setSessionId(ServiceTestUtil.randomString());

		userTracker.setRemoteAddr(ServiceTestUtil.randomString());

		userTracker.setRemoteHost(ServiceTestUtil.randomString());

		userTracker.setUserAgent(ServiceTestUtil.randomString());

		userTracker.setMvccVersion(ServiceTestUtil.nextLong());

		_persistence.update(userTracker);

		return userTracker;
	}

	private static Log _log = LogFactoryUtil.getLog(UserTrackerPersistenceTest.class);
	private UserTrackerPersistence _persistence = (UserTrackerPersistence)PortalBeanLocatorUtil.locate(UserTrackerPersistence.class.getName());
	private TransactionalPersistenceAdvice _transactionalPersistenceAdvice = (TransactionalPersistenceAdvice)PortalBeanLocatorUtil.locate(TransactionalPersistenceAdvice.class.getName());
}