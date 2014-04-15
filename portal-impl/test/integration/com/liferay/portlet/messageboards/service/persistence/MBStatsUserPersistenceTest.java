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

package com.liferay.portlet.messageboards.service.persistence;

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
import com.liferay.portal.model.ModelListener;
import com.liferay.portal.service.ServiceTestUtil;
import com.liferay.portal.service.persistence.BasePersistence;
import com.liferay.portal.service.persistence.PersistenceExecutionTestListener;
import com.liferay.portal.test.LiferayPersistenceIntegrationJUnitTestRunner;
import com.liferay.portal.test.persistence.TransactionalPersistenceAdvice;
import com.liferay.portal.util.PropsValues;

import com.liferay.portlet.messageboards.NoSuchStatsUserException;
import com.liferay.portlet.messageboards.model.MBStatsUser;
import com.liferay.portlet.messageboards.model.impl.MBStatsUserModelImpl;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
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
public class MBStatsUserPersistenceTest {
	@Before
	public void setUp() {
		_modelListeners = _persistence.getListeners();

		for (ModelListener<MBStatsUser> modelListener : _modelListeners) {
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

		for (ModelListener<MBStatsUser> modelListener : _modelListeners) {
			_persistence.registerListener(modelListener);
		}
	}

	@Test
	public void testCreate() throws Exception {
		long pk = ServiceTestUtil.nextLong();

		MBStatsUser mbStatsUser = _persistence.create(pk);

		Assert.assertNotNull(mbStatsUser);

		Assert.assertEquals(mbStatsUser.getPrimaryKey(), pk);
	}

	@Test
	public void testRemove() throws Exception {
		MBStatsUser newMBStatsUser = addMBStatsUser();

		_persistence.remove(newMBStatsUser);

		MBStatsUser existingMBStatsUser = _persistence.fetchByPrimaryKey(newMBStatsUser.getPrimaryKey());

		Assert.assertNull(existingMBStatsUser);
	}

	@Test
	public void testUpdateNew() throws Exception {
		addMBStatsUser();
	}

	@Test
	public void testUpdateExisting() throws Exception {
		long pk = ServiceTestUtil.nextLong();

		MBStatsUser newMBStatsUser = _persistence.create(pk);

		newMBStatsUser.setGroupId(ServiceTestUtil.nextLong());

		newMBStatsUser.setUserId(ServiceTestUtil.nextLong());

		newMBStatsUser.setMessageCount(ServiceTestUtil.nextInt());

		newMBStatsUser.setLastPostDate(ServiceTestUtil.nextDate());

		_persistence.update(newMBStatsUser);

		MBStatsUser existingMBStatsUser = _persistence.findByPrimaryKey(newMBStatsUser.getPrimaryKey());

		Assert.assertEquals(existingMBStatsUser.getStatsUserId(),
			newMBStatsUser.getStatsUserId());
		Assert.assertEquals(existingMBStatsUser.getGroupId(),
			newMBStatsUser.getGroupId());
		Assert.assertEquals(existingMBStatsUser.getUserId(),
			newMBStatsUser.getUserId());
		Assert.assertEquals(existingMBStatsUser.getMessageCount(),
			newMBStatsUser.getMessageCount());
		Assert.assertEquals(Time.getShortTimestamp(
				existingMBStatsUser.getLastPostDate()),
			Time.getShortTimestamp(newMBStatsUser.getLastPostDate()));
	}

	@Test
	public void testCountByGroupId() {
		try {
			_persistence.countByGroupId(ServiceTestUtil.nextLong());

			_persistence.countByGroupId(0L);
		}
		catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void testCountByUserId() {
		try {
			_persistence.countByUserId(ServiceTestUtil.nextLong());

			_persistence.countByUserId(0L);
		}
		catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void testCountByG_U() {
		try {
			_persistence.countByG_U(ServiceTestUtil.nextLong(),
				ServiceTestUtil.nextLong());

			_persistence.countByG_U(0L, 0L);
		}
		catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void testCountByG_NotU_NotM() {
		try {
			_persistence.countByG_NotU_NotM(ServiceTestUtil.nextLong(),
				ServiceTestUtil.nextLong(), ServiceTestUtil.nextInt());

			_persistence.countByG_NotU_NotM(0L, 0L, 0);
		}
		catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void testFindByPrimaryKeyExisting() throws Exception {
		MBStatsUser newMBStatsUser = addMBStatsUser();

		MBStatsUser existingMBStatsUser = _persistence.findByPrimaryKey(newMBStatsUser.getPrimaryKey());

		Assert.assertEquals(existingMBStatsUser, newMBStatsUser);
	}

	@Test
	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = ServiceTestUtil.nextLong();

		try {
			_persistence.findByPrimaryKey(pk);

			Assert.fail("Missing entity did not throw NoSuchStatsUserException");
		}
		catch (NoSuchStatsUserException nsee) {
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
		return OrderByComparatorFactoryUtil.create("MBStatsUser",
			"statsUserId", true, "groupId", true, "userId", true,
			"messageCount", true, "lastPostDate", true);
	}

	@Test
	public void testFetchByPrimaryKeyExisting() throws Exception {
		MBStatsUser newMBStatsUser = addMBStatsUser();

		MBStatsUser existingMBStatsUser = _persistence.fetchByPrimaryKey(newMBStatsUser.getPrimaryKey());

		Assert.assertEquals(existingMBStatsUser, newMBStatsUser);
	}

	@Test
	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = ServiceTestUtil.nextLong();

		MBStatsUser missingMBStatsUser = _persistence.fetchByPrimaryKey(pk);

		Assert.assertNull(missingMBStatsUser);
	}

	@Test
	public void testActionableDynamicQuery() throws Exception {
		final IntegerWrapper count = new IntegerWrapper();

		ActionableDynamicQuery actionableDynamicQuery = new MBStatsUserActionableDynamicQuery() {
				@Override
				protected void performAction(Object object) {
					MBStatsUser mbStatsUser = (MBStatsUser)object;

					Assert.assertNotNull(mbStatsUser);

					count.increment();
				}
			};

		actionableDynamicQuery.performActions();

		Assert.assertEquals(count.getValue(), _persistence.countAll());
	}

	@Test
	public void testDynamicQueryByPrimaryKeyExisting()
		throws Exception {
		MBStatsUser newMBStatsUser = addMBStatsUser();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(MBStatsUser.class,
				MBStatsUser.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("statsUserId",
				newMBStatsUser.getStatsUserId()));

		List<MBStatsUser> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		MBStatsUser existingMBStatsUser = result.get(0);

		Assert.assertEquals(existingMBStatsUser, newMBStatsUser);
	}

	@Test
	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(MBStatsUser.class,
				MBStatsUser.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("statsUserId",
				ServiceTestUtil.nextLong()));

		List<MBStatsUser> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testDynamicQueryByProjectionExisting()
		throws Exception {
		MBStatsUser newMBStatsUser = addMBStatsUser();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(MBStatsUser.class,
				MBStatsUser.class.getClassLoader());

		dynamicQuery.setProjection(ProjectionFactoryUtil.property("statsUserId"));

		Object newStatsUserId = newMBStatsUser.getStatsUserId();

		dynamicQuery.add(RestrictionsFactoryUtil.in("statsUserId",
				new Object[] { newStatsUserId }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		Object existingStatsUserId = result.get(0);

		Assert.assertEquals(existingStatsUserId, newStatsUserId);
	}

	@Test
	public void testDynamicQueryByProjectionMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(MBStatsUser.class,
				MBStatsUser.class.getClassLoader());

		dynamicQuery.setProjection(ProjectionFactoryUtil.property("statsUserId"));

		dynamicQuery.add(RestrictionsFactoryUtil.in("statsUserId",
				new Object[] { ServiceTestUtil.nextLong() }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testResetOriginalValues() throws Exception {
		if (!PropsValues.HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE) {
			return;
		}

		MBStatsUser newMBStatsUser = addMBStatsUser();

		_persistence.clearCache();

		MBStatsUserModelImpl existingMBStatsUserModelImpl = (MBStatsUserModelImpl)_persistence.findByPrimaryKey(newMBStatsUser.getPrimaryKey());

		Assert.assertEquals(existingMBStatsUserModelImpl.getGroupId(),
			existingMBStatsUserModelImpl.getOriginalGroupId());
		Assert.assertEquals(existingMBStatsUserModelImpl.getUserId(),
			existingMBStatsUserModelImpl.getOriginalUserId());
	}

	protected MBStatsUser addMBStatsUser() throws Exception {
		long pk = ServiceTestUtil.nextLong();

		MBStatsUser mbStatsUser = _persistence.create(pk);

		mbStatsUser.setGroupId(ServiceTestUtil.nextLong());

		mbStatsUser.setUserId(ServiceTestUtil.nextLong());

		mbStatsUser.setMessageCount(ServiceTestUtil.nextInt());

		mbStatsUser.setLastPostDate(ServiceTestUtil.nextDate());

		_persistence.update(mbStatsUser);

		return mbStatsUser;
	}

	private static Log _log = LogFactoryUtil.getLog(MBStatsUserPersistenceTest.class);
	private ModelListener<MBStatsUser>[] _modelListeners;
	private MBStatsUserPersistence _persistence = (MBStatsUserPersistence)PortalBeanLocatorUtil.locate(MBStatsUserPersistence.class.getName());
	private TransactionalPersistenceAdvice _transactionalPersistenceAdvice = (TransactionalPersistenceAdvice)PortalBeanLocatorUtil.locate(TransactionalPersistenceAdvice.class.getName());
}