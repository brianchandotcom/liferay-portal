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

import com.liferay.portal.NoSuchUserTrackerPathException;
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
import com.liferay.portal.model.UserTrackerPath;
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
public class UserTrackerPathPersistenceTest {
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

		UserTrackerPath userTrackerPath = _persistence.create(pk);

		Assert.assertNotNull(userTrackerPath);

		Assert.assertEquals(userTrackerPath.getPrimaryKey(), pk);
	}

	@Test
	public void testRemove() throws Exception {
		UserTrackerPath newUserTrackerPath = addUserTrackerPath();

		_persistence.remove(newUserTrackerPath);

		UserTrackerPath existingUserTrackerPath = _persistence.fetchByPrimaryKey(newUserTrackerPath.getPrimaryKey());

		Assert.assertNull(existingUserTrackerPath);
	}

	@Test
	public void testUpdateNew() throws Exception {
		addUserTrackerPath();
	}

	@Test
	public void testUpdateExisting() throws Exception {
		long pk = ServiceTestUtil.nextLong();

		UserTrackerPath newUserTrackerPath = _persistence.create(pk);

		newUserTrackerPath.setUserTrackerId(ServiceTestUtil.nextLong());

		newUserTrackerPath.setPath(ServiceTestUtil.randomString());

		newUserTrackerPath.setPathDate(ServiceTestUtil.nextDate());

		newUserTrackerPath.setMvccVersion(ServiceTestUtil.nextLong());

		_persistence.update(newUserTrackerPath);

		UserTrackerPath existingUserTrackerPath = _persistence.findByPrimaryKey(newUserTrackerPath.getPrimaryKey());

		Assert.assertEquals(existingUserTrackerPath.getUserTrackerPathId(),
			newUserTrackerPath.getUserTrackerPathId());
		Assert.assertEquals(existingUserTrackerPath.getUserTrackerId(),
			newUserTrackerPath.getUserTrackerId());
		Assert.assertEquals(existingUserTrackerPath.getPath(),
			newUserTrackerPath.getPath());
		Assert.assertEquals(Time.getShortTimestamp(
				existingUserTrackerPath.getPathDate()),
			Time.getShortTimestamp(newUserTrackerPath.getPathDate()));
		Assert.assertEquals(existingUserTrackerPath.getMvccVersion(),
			newUserTrackerPath.getMvccVersion());
	}

	@Test
	public void testFindByPrimaryKeyExisting() throws Exception {
		UserTrackerPath newUserTrackerPath = addUserTrackerPath();

		UserTrackerPath existingUserTrackerPath = _persistence.findByPrimaryKey(newUserTrackerPath.getPrimaryKey());

		Assert.assertEquals(existingUserTrackerPath, newUserTrackerPath);
	}

	@Test
	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = ServiceTestUtil.nextLong();

		try {
			_persistence.findByPrimaryKey(pk);

			Assert.fail(
				"Missing entity did not throw NoSuchUserTrackerPathException");
		}
		catch (NoSuchUserTrackerPathException nsee) {
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
		return OrderByComparatorFactoryUtil.create("UserTrackerPath",
			"userTrackerPathId", true, "userTrackerId", true, "path", true,
			"pathDate", true, "mvccVersion", true);
	}

	@Test
	public void testFetchByPrimaryKeyExisting() throws Exception {
		UserTrackerPath newUserTrackerPath = addUserTrackerPath();

		UserTrackerPath existingUserTrackerPath = _persistence.fetchByPrimaryKey(newUserTrackerPath.getPrimaryKey());

		Assert.assertEquals(existingUserTrackerPath, newUserTrackerPath);
	}

	@Test
	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = ServiceTestUtil.nextLong();

		UserTrackerPath missingUserTrackerPath = _persistence.fetchByPrimaryKey(pk);

		Assert.assertNull(missingUserTrackerPath);
	}

	@Test
	public void testActionableDynamicQuery() throws Exception {
		final IntegerWrapper count = new IntegerWrapper();

		ActionableDynamicQuery actionableDynamicQuery = new UserTrackerPathActionableDynamicQuery() {
				@Override
				protected void performAction(Object object) {
					UserTrackerPath userTrackerPath = (UserTrackerPath)object;

					Assert.assertNotNull(userTrackerPath);

					count.increment();
				}
			};

		actionableDynamicQuery.performActions();

		Assert.assertEquals(count.getValue(), _persistence.countAll());
	}

	@Test
	public void testDynamicQueryByPrimaryKeyExisting()
		throws Exception {
		UserTrackerPath newUserTrackerPath = addUserTrackerPath();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(UserTrackerPath.class,
				UserTrackerPath.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("userTrackerPathId",
				newUserTrackerPath.getUserTrackerPathId()));

		List<UserTrackerPath> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		UserTrackerPath existingUserTrackerPath = result.get(0);

		Assert.assertEquals(existingUserTrackerPath, newUserTrackerPath);
	}

	@Test
	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(UserTrackerPath.class,
				UserTrackerPath.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("userTrackerPathId",
				ServiceTestUtil.nextLong()));

		List<UserTrackerPath> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testDynamicQueryByProjectionExisting()
		throws Exception {
		UserTrackerPath newUserTrackerPath = addUserTrackerPath();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(UserTrackerPath.class,
				UserTrackerPath.class.getClassLoader());

		dynamicQuery.setProjection(ProjectionFactoryUtil.property(
				"userTrackerPathId"));

		Object newUserTrackerPathId = newUserTrackerPath.getUserTrackerPathId();

		dynamicQuery.add(RestrictionsFactoryUtil.in("userTrackerPathId",
				new Object[] { newUserTrackerPathId }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		Object existingUserTrackerPathId = result.get(0);

		Assert.assertEquals(existingUserTrackerPathId, newUserTrackerPathId);
	}

	@Test
	public void testDynamicQueryByProjectionMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(UserTrackerPath.class,
				UserTrackerPath.class.getClassLoader());

		dynamicQuery.setProjection(ProjectionFactoryUtil.property(
				"userTrackerPathId"));

		dynamicQuery.add(RestrictionsFactoryUtil.in("userTrackerPathId",
				new Object[] { ServiceTestUtil.nextLong() }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	protected UserTrackerPath addUserTrackerPath() throws Exception {
		long pk = ServiceTestUtil.nextLong();

		UserTrackerPath userTrackerPath = _persistence.create(pk);

		userTrackerPath.setUserTrackerId(ServiceTestUtil.nextLong());

		userTrackerPath.setPath(ServiceTestUtil.randomString());

		userTrackerPath.setPathDate(ServiceTestUtil.nextDate());

		userTrackerPath.setMvccVersion(ServiceTestUtil.nextLong());

		_persistence.update(userTrackerPath);

		return userTrackerPath;
	}

	private static Log _log = LogFactoryUtil.getLog(UserTrackerPathPersistenceTest.class);
	private UserTrackerPathPersistence _persistence = (UserTrackerPathPersistence)PortalBeanLocatorUtil.locate(UserTrackerPathPersistence.class.getName());
	private TransactionalPersistenceAdvice _transactionalPersistenceAdvice = (TransactionalPersistenceAdvice)PortalBeanLocatorUtil.locate(TransactionalPersistenceAdvice.class.getName());
}