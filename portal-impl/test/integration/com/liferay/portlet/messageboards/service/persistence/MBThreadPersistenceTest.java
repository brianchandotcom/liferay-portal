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
import com.liferay.portal.kernel.test.AssertUtils;
import com.liferay.portal.kernel.test.ExecutionTestListeners;
import com.liferay.portal.kernel.util.IntegerWrapper;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.OrderByComparatorFactoryUtil;
import com.liferay.portal.kernel.util.Time;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.service.ServiceTestUtil;
import com.liferay.portal.service.persistence.BasePersistence;
import com.liferay.portal.service.persistence.PersistenceExecutionTestListener;
import com.liferay.portal.test.LiferayPersistenceIntegrationJUnitTestRunner;
import com.liferay.portal.test.persistence.TransactionalPersistenceAdvice;
import com.liferay.portal.util.PropsValues;

import com.liferay.portlet.messageboards.NoSuchThreadException;
import com.liferay.portlet.messageboards.model.MBThread;
import com.liferay.portlet.messageboards.model.impl.MBThreadModelImpl;

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
public class MBThreadPersistenceTest {
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

		MBThread mbThread = _persistence.create(pk);

		Assert.assertNotNull(mbThread);

		Assert.assertEquals(mbThread.getPrimaryKey(), pk);
	}

	@Test
	public void testRemove() throws Exception {
		MBThread newMBThread = addMBThread();

		_persistence.remove(newMBThread);

		MBThread existingMBThread = _persistence.fetchByPrimaryKey(newMBThread.getPrimaryKey());

		Assert.assertNull(existingMBThread);
	}

	@Test
	public void testUpdateNew() throws Exception {
		addMBThread();
	}

	@Test
	public void testUpdateExisting() throws Exception {
		long pk = ServiceTestUtil.nextLong();

		MBThread newMBThread = _persistence.create(pk);

		newMBThread.setUuid(ServiceTestUtil.randomString());

		newMBThread.setGroupId(ServiceTestUtil.nextLong());

		newMBThread.setCompanyId(ServiceTestUtil.nextLong());

		newMBThread.setUserId(ServiceTestUtil.nextLong());

		newMBThread.setUserName(ServiceTestUtil.randomString());

		newMBThread.setCreateDate(ServiceTestUtil.nextDate());

		newMBThread.setModifiedDate(ServiceTestUtil.nextDate());

		newMBThread.setCategoryId(ServiceTestUtil.nextLong());

		newMBThread.setRootMessageId(ServiceTestUtil.nextLong());

		newMBThread.setRootMessageUserId(ServiceTestUtil.nextLong());

		newMBThread.setMessageCount(ServiceTestUtil.nextInt());

		newMBThread.setViewCount(ServiceTestUtil.nextInt());

		newMBThread.setLastPostByUserId(ServiceTestUtil.nextLong());

		newMBThread.setLastPostDate(ServiceTestUtil.nextDate());

		newMBThread.setPriority(ServiceTestUtil.nextDouble());

		newMBThread.setQuestion(ServiceTestUtil.randomBoolean());

		newMBThread.setStatus(ServiceTestUtil.nextInt());

		newMBThread.setStatusByUserId(ServiceTestUtil.nextLong());

		newMBThread.setStatusByUserName(ServiceTestUtil.randomString());

		newMBThread.setStatusDate(ServiceTestUtil.nextDate());

		_persistence.update(newMBThread);

		MBThread existingMBThread = _persistence.findByPrimaryKey(newMBThread.getPrimaryKey());

		Assert.assertEquals(existingMBThread.getUuid(), newMBThread.getUuid());
		Assert.assertEquals(existingMBThread.getThreadId(),
			newMBThread.getThreadId());
		Assert.assertEquals(existingMBThread.getGroupId(),
			newMBThread.getGroupId());
		Assert.assertEquals(existingMBThread.getCompanyId(),
			newMBThread.getCompanyId());
		Assert.assertEquals(existingMBThread.getUserId(),
			newMBThread.getUserId());
		Assert.assertEquals(existingMBThread.getUserName(),
			newMBThread.getUserName());
		Assert.assertEquals(Time.getShortTimestamp(
				existingMBThread.getCreateDate()),
			Time.getShortTimestamp(newMBThread.getCreateDate()));
		Assert.assertEquals(Time.getShortTimestamp(
				existingMBThread.getModifiedDate()),
			Time.getShortTimestamp(newMBThread.getModifiedDate()));
		Assert.assertEquals(existingMBThread.getCategoryId(),
			newMBThread.getCategoryId());
		Assert.assertEquals(existingMBThread.getRootMessageId(),
			newMBThread.getRootMessageId());
		Assert.assertEquals(existingMBThread.getRootMessageUserId(),
			newMBThread.getRootMessageUserId());
		Assert.assertEquals(existingMBThread.getMessageCount(),
			newMBThread.getMessageCount());
		Assert.assertEquals(existingMBThread.getViewCount(),
			newMBThread.getViewCount());
		Assert.assertEquals(existingMBThread.getLastPostByUserId(),
			newMBThread.getLastPostByUserId());
		Assert.assertEquals(Time.getShortTimestamp(
				existingMBThread.getLastPostDate()),
			Time.getShortTimestamp(newMBThread.getLastPostDate()));
		AssertUtils.assertEquals(existingMBThread.getPriority(),
			newMBThread.getPriority());
		Assert.assertEquals(existingMBThread.getQuestion(),
			newMBThread.getQuestion());
		Assert.assertEquals(existingMBThread.getStatus(),
			newMBThread.getStatus());
		Assert.assertEquals(existingMBThread.getStatusByUserId(),
			newMBThread.getStatusByUserId());
		Assert.assertEquals(existingMBThread.getStatusByUserName(),
			newMBThread.getStatusByUserName());
		Assert.assertEquals(Time.getShortTimestamp(
				existingMBThread.getStatusDate()),
			Time.getShortTimestamp(newMBThread.getStatusDate()));
	}

	@Test
	public void testFindByPrimaryKeyExisting() throws Exception {
		MBThread newMBThread = addMBThread();

		MBThread existingMBThread = _persistence.findByPrimaryKey(newMBThread.getPrimaryKey());

		Assert.assertEquals(existingMBThread, newMBThread);
	}

	@Test
	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = ServiceTestUtil.nextLong();

		try {
			_persistence.findByPrimaryKey(pk);

			Assert.fail("Missing entity did not throw NoSuchThreadException");
		}
		catch (NoSuchThreadException nsee) {
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

	@Test
	public void testFilterFindByGroupId() throws Exception {
		try {
			_persistence.filterFindByGroupId(0, QueryUtil.ALL_POS,
				QueryUtil.ALL_POS, getOrderByComparator());
		}
		catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	protected OrderByComparator getOrderByComparator() {
		return OrderByComparatorFactoryUtil.create("MBThread", "uuid", true,
			"threadId", true, "groupId", true, "companyId", true, "userId",
			true, "userName", true, "createDate", true, "modifiedDate", true,
			"categoryId", true, "rootMessageId", true, "rootMessageUserId",
			true, "messageCount", true, "viewCount", true, "lastPostByUserId",
			true, "lastPostDate", true, "priority", true, "question", true,
			"status", true, "statusByUserId", true, "statusByUserName", true,
			"statusDate", true);
	}

	@Test
	public void testFetchByPrimaryKeyExisting() throws Exception {
		MBThread newMBThread = addMBThread();

		MBThread existingMBThread = _persistence.fetchByPrimaryKey(newMBThread.getPrimaryKey());

		Assert.assertEquals(existingMBThread, newMBThread);
	}

	@Test
	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = ServiceTestUtil.nextLong();

		MBThread missingMBThread = _persistence.fetchByPrimaryKey(pk);

		Assert.assertNull(missingMBThread);
	}

	@Test
	public void testActionableDynamicQuery() throws Exception {
		final IntegerWrapper count = new IntegerWrapper();

		ActionableDynamicQuery actionableDynamicQuery = new MBThreadActionableDynamicQuery() {
				@Override
				protected void performAction(Object object) {
					MBThread mbThread = (MBThread)object;

					Assert.assertNotNull(mbThread);

					count.increment();
				}
			};

		actionableDynamicQuery.performActions();

		Assert.assertEquals(count.getValue(), _persistence.countAll());
	}

	@Test
	public void testDynamicQueryByPrimaryKeyExisting()
		throws Exception {
		MBThread newMBThread = addMBThread();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(MBThread.class,
				MBThread.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("threadId",
				newMBThread.getThreadId()));

		List<MBThread> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		MBThread existingMBThread = result.get(0);

		Assert.assertEquals(existingMBThread, newMBThread);
	}

	@Test
	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(MBThread.class,
				MBThread.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("threadId",
				ServiceTestUtil.nextLong()));

		List<MBThread> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testDynamicQueryByProjectionExisting()
		throws Exception {
		MBThread newMBThread = addMBThread();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(MBThread.class,
				MBThread.class.getClassLoader());

		dynamicQuery.setProjection(ProjectionFactoryUtil.property("threadId"));

		Object newThreadId = newMBThread.getThreadId();

		dynamicQuery.add(RestrictionsFactoryUtil.in("threadId",
				new Object[] { newThreadId }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		Object existingThreadId = result.get(0);

		Assert.assertEquals(existingThreadId, newThreadId);
	}

	@Test
	public void testDynamicQueryByProjectionMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(MBThread.class,
				MBThread.class.getClassLoader());

		dynamicQuery.setProjection(ProjectionFactoryUtil.property("threadId"));

		dynamicQuery.add(RestrictionsFactoryUtil.in("threadId",
				new Object[] { ServiceTestUtil.nextLong() }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testResetOriginalValues() throws Exception {
		if (!PropsValues.HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE) {
			return;
		}

		MBThread newMBThread = addMBThread();

		_persistence.clearCache();

		MBThreadModelImpl existingMBThreadModelImpl = (MBThreadModelImpl)_persistence.findByPrimaryKey(newMBThread.getPrimaryKey());

		Assert.assertTrue(Validator.equals(
				existingMBThreadModelImpl.getUuid(),
				existingMBThreadModelImpl.getOriginalUuid()));
		Assert.assertEquals(existingMBThreadModelImpl.getGroupId(),
			existingMBThreadModelImpl.getOriginalGroupId());

		Assert.assertEquals(existingMBThreadModelImpl.getRootMessageId(),
			existingMBThreadModelImpl.getOriginalRootMessageId());
	}

	protected MBThread addMBThread() throws Exception {
		long pk = ServiceTestUtil.nextLong();

		MBThread mbThread = _persistence.create(pk);

		mbThread.setUuid(ServiceTestUtil.randomString());

		mbThread.setGroupId(ServiceTestUtil.nextLong());

		mbThread.setCompanyId(ServiceTestUtil.nextLong());

		mbThread.setUserId(ServiceTestUtil.nextLong());

		mbThread.setUserName(ServiceTestUtil.randomString());

		mbThread.setCreateDate(ServiceTestUtil.nextDate());

		mbThread.setModifiedDate(ServiceTestUtil.nextDate());

		mbThread.setCategoryId(ServiceTestUtil.nextLong());

		mbThread.setRootMessageId(ServiceTestUtil.nextLong());

		mbThread.setRootMessageUserId(ServiceTestUtil.nextLong());

		mbThread.setMessageCount(ServiceTestUtil.nextInt());

		mbThread.setViewCount(ServiceTestUtil.nextInt());

		mbThread.setLastPostByUserId(ServiceTestUtil.nextLong());

		mbThread.setLastPostDate(ServiceTestUtil.nextDate());

		mbThread.setPriority(ServiceTestUtil.nextDouble());

		mbThread.setQuestion(ServiceTestUtil.randomBoolean());

		mbThread.setStatus(ServiceTestUtil.nextInt());

		mbThread.setStatusByUserId(ServiceTestUtil.nextLong());

		mbThread.setStatusByUserName(ServiceTestUtil.randomString());

		mbThread.setStatusDate(ServiceTestUtil.nextDate());

		_persistence.update(mbThread);

		return mbThread;
	}

	private static Log _log = LogFactoryUtil.getLog(MBThreadPersistenceTest.class);
	private MBThreadPersistence _persistence = (MBThreadPersistence)PortalBeanLocatorUtil.locate(MBThreadPersistence.class.getName());
	private TransactionalPersistenceAdvice _transactionalPersistenceAdvice = (TransactionalPersistenceAdvice)PortalBeanLocatorUtil.locate(TransactionalPersistenceAdvice.class.getName());
}