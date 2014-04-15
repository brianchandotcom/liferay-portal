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
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Time;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.ModelListener;
import com.liferay.portal.service.ServiceTestUtil;
import com.liferay.portal.service.persistence.BasePersistence;
import com.liferay.portal.service.persistence.PersistenceExecutionTestListener;
import com.liferay.portal.test.LiferayPersistenceIntegrationJUnitTestRunner;
import com.liferay.portal.test.persistence.TransactionalPersistenceAdvice;
import com.liferay.portal.util.PropsValues;

import com.liferay.portlet.messageboards.NoSuchThreadFlagException;
import com.liferay.portlet.messageboards.model.MBThreadFlag;
import com.liferay.portlet.messageboards.model.impl.MBThreadFlagModelImpl;

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
public class MBThreadFlagPersistenceTest {
	@Before
	public void setUp() {
		_modelListeners = _persistence.getListeners();

		for (ModelListener<MBThreadFlag> modelListener : _modelListeners) {
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

		for (ModelListener<MBThreadFlag> modelListener : _modelListeners) {
			_persistence.registerListener(modelListener);
		}
	}

	@Test
	public void testCreate() throws Exception {
		long pk = ServiceTestUtil.nextLong();

		MBThreadFlag mbThreadFlag = _persistence.create(pk);

		Assert.assertNotNull(mbThreadFlag);

		Assert.assertEquals(mbThreadFlag.getPrimaryKey(), pk);
	}

	@Test
	public void testRemove() throws Exception {
		MBThreadFlag newMBThreadFlag = addMBThreadFlag();

		_persistence.remove(newMBThreadFlag);

		MBThreadFlag existingMBThreadFlag = _persistence.fetchByPrimaryKey(newMBThreadFlag.getPrimaryKey());

		Assert.assertNull(existingMBThreadFlag);
	}

	@Test
	public void testUpdateNew() throws Exception {
		addMBThreadFlag();
	}

	@Test
	public void testUpdateExisting() throws Exception {
		long pk = ServiceTestUtil.nextLong();

		MBThreadFlag newMBThreadFlag = _persistence.create(pk);

		newMBThreadFlag.setUuid(ServiceTestUtil.randomString());

		newMBThreadFlag.setGroupId(ServiceTestUtil.nextLong());

		newMBThreadFlag.setCompanyId(ServiceTestUtil.nextLong());

		newMBThreadFlag.setUserId(ServiceTestUtil.nextLong());

		newMBThreadFlag.setUserName(ServiceTestUtil.randomString());

		newMBThreadFlag.setCreateDate(ServiceTestUtil.nextDate());

		newMBThreadFlag.setModifiedDate(ServiceTestUtil.nextDate());

		newMBThreadFlag.setThreadId(ServiceTestUtil.nextLong());

		_persistence.update(newMBThreadFlag);

		MBThreadFlag existingMBThreadFlag = _persistence.findByPrimaryKey(newMBThreadFlag.getPrimaryKey());

		Assert.assertEquals(existingMBThreadFlag.getUuid(),
			newMBThreadFlag.getUuid());
		Assert.assertEquals(existingMBThreadFlag.getThreadFlagId(),
			newMBThreadFlag.getThreadFlagId());
		Assert.assertEquals(existingMBThreadFlag.getGroupId(),
			newMBThreadFlag.getGroupId());
		Assert.assertEquals(existingMBThreadFlag.getCompanyId(),
			newMBThreadFlag.getCompanyId());
		Assert.assertEquals(existingMBThreadFlag.getUserId(),
			newMBThreadFlag.getUserId());
		Assert.assertEquals(existingMBThreadFlag.getUserName(),
			newMBThreadFlag.getUserName());
		Assert.assertEquals(Time.getShortTimestamp(
				existingMBThreadFlag.getCreateDate()),
			Time.getShortTimestamp(newMBThreadFlag.getCreateDate()));
		Assert.assertEquals(Time.getShortTimestamp(
				existingMBThreadFlag.getModifiedDate()),
			Time.getShortTimestamp(newMBThreadFlag.getModifiedDate()));
		Assert.assertEquals(existingMBThreadFlag.getThreadId(),
			newMBThreadFlag.getThreadId());
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
	public void testCountByUUID_G() {
		try {
			_persistence.countByUUID_G(StringPool.BLANK,
				ServiceTestUtil.nextLong());

			_persistence.countByUUID_G(StringPool.NULL, 0L);

			_persistence.countByUUID_G((String)null, 0L);
		}
		catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void testCountByUuid_C() {
		try {
			_persistence.countByUuid_C(StringPool.BLANK,
				ServiceTestUtil.nextLong());

			_persistence.countByUuid_C(StringPool.NULL, 0L);

			_persistence.countByUuid_C((String)null, 0L);
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
	public void testCountByThreadId() {
		try {
			_persistence.countByThreadId(ServiceTestUtil.nextLong());

			_persistence.countByThreadId(0L);
		}
		catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void testCountByU_T() {
		try {
			_persistence.countByU_T(ServiceTestUtil.nextLong(),
				ServiceTestUtil.nextLong());

			_persistence.countByU_T(0L, 0L);
		}
		catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void testFindByPrimaryKeyExisting() throws Exception {
		MBThreadFlag newMBThreadFlag = addMBThreadFlag();

		MBThreadFlag existingMBThreadFlag = _persistence.findByPrimaryKey(newMBThreadFlag.getPrimaryKey());

		Assert.assertEquals(existingMBThreadFlag, newMBThreadFlag);
	}

	@Test
	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = ServiceTestUtil.nextLong();

		try {
			_persistence.findByPrimaryKey(pk);

			Assert.fail(
				"Missing entity did not throw NoSuchThreadFlagException");
		}
		catch (NoSuchThreadFlagException nsee) {
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
		return OrderByComparatorFactoryUtil.create("MBThreadFlag", "uuid",
			true, "threadFlagId", true, "groupId", true, "companyId", true,
			"userId", true, "userName", true, "createDate", true,
			"modifiedDate", true, "threadId", true);
	}

	@Test
	public void testFetchByPrimaryKeyExisting() throws Exception {
		MBThreadFlag newMBThreadFlag = addMBThreadFlag();

		MBThreadFlag existingMBThreadFlag = _persistence.fetchByPrimaryKey(newMBThreadFlag.getPrimaryKey());

		Assert.assertEquals(existingMBThreadFlag, newMBThreadFlag);
	}

	@Test
	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = ServiceTestUtil.nextLong();

		MBThreadFlag missingMBThreadFlag = _persistence.fetchByPrimaryKey(pk);

		Assert.assertNull(missingMBThreadFlag);
	}

	@Test
	public void testActionableDynamicQuery() throws Exception {
		final IntegerWrapper count = new IntegerWrapper();

		ActionableDynamicQuery actionableDynamicQuery = new MBThreadFlagActionableDynamicQuery() {
				@Override
				protected void performAction(Object object) {
					MBThreadFlag mbThreadFlag = (MBThreadFlag)object;

					Assert.assertNotNull(mbThreadFlag);

					count.increment();
				}
			};

		actionableDynamicQuery.performActions();

		Assert.assertEquals(count.getValue(), _persistence.countAll());
	}

	@Test
	public void testDynamicQueryByPrimaryKeyExisting()
		throws Exception {
		MBThreadFlag newMBThreadFlag = addMBThreadFlag();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(MBThreadFlag.class,
				MBThreadFlag.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("threadFlagId",
				newMBThreadFlag.getThreadFlagId()));

		List<MBThreadFlag> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		MBThreadFlag existingMBThreadFlag = result.get(0);

		Assert.assertEquals(existingMBThreadFlag, newMBThreadFlag);
	}

	@Test
	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(MBThreadFlag.class,
				MBThreadFlag.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("threadFlagId",
				ServiceTestUtil.nextLong()));

		List<MBThreadFlag> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testDynamicQueryByProjectionExisting()
		throws Exception {
		MBThreadFlag newMBThreadFlag = addMBThreadFlag();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(MBThreadFlag.class,
				MBThreadFlag.class.getClassLoader());

		dynamicQuery.setProjection(ProjectionFactoryUtil.property(
				"threadFlagId"));

		Object newThreadFlagId = newMBThreadFlag.getThreadFlagId();

		dynamicQuery.add(RestrictionsFactoryUtil.in("threadFlagId",
				new Object[] { newThreadFlagId }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		Object existingThreadFlagId = result.get(0);

		Assert.assertEquals(existingThreadFlagId, newThreadFlagId);
	}

	@Test
	public void testDynamicQueryByProjectionMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(MBThreadFlag.class,
				MBThreadFlag.class.getClassLoader());

		dynamicQuery.setProjection(ProjectionFactoryUtil.property(
				"threadFlagId"));

		dynamicQuery.add(RestrictionsFactoryUtil.in("threadFlagId",
				new Object[] { ServiceTestUtil.nextLong() }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testResetOriginalValues() throws Exception {
		if (!PropsValues.HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE) {
			return;
		}

		MBThreadFlag newMBThreadFlag = addMBThreadFlag();

		_persistence.clearCache();

		MBThreadFlagModelImpl existingMBThreadFlagModelImpl = (MBThreadFlagModelImpl)_persistence.findByPrimaryKey(newMBThreadFlag.getPrimaryKey());

		Assert.assertTrue(Validator.equals(
				existingMBThreadFlagModelImpl.getUuid(),
				existingMBThreadFlagModelImpl.getOriginalUuid()));
		Assert.assertEquals(existingMBThreadFlagModelImpl.getGroupId(),
			existingMBThreadFlagModelImpl.getOriginalGroupId());

		Assert.assertEquals(existingMBThreadFlagModelImpl.getUserId(),
			existingMBThreadFlagModelImpl.getOriginalUserId());
		Assert.assertEquals(existingMBThreadFlagModelImpl.getThreadId(),
			existingMBThreadFlagModelImpl.getOriginalThreadId());
	}

	protected MBThreadFlag addMBThreadFlag() throws Exception {
		long pk = ServiceTestUtil.nextLong();

		MBThreadFlag mbThreadFlag = _persistence.create(pk);

		mbThreadFlag.setUuid(ServiceTestUtil.randomString());

		mbThreadFlag.setGroupId(ServiceTestUtil.nextLong());

		mbThreadFlag.setCompanyId(ServiceTestUtil.nextLong());

		mbThreadFlag.setUserId(ServiceTestUtil.nextLong());

		mbThreadFlag.setUserName(ServiceTestUtil.randomString());

		mbThreadFlag.setCreateDate(ServiceTestUtil.nextDate());

		mbThreadFlag.setModifiedDate(ServiceTestUtil.nextDate());

		mbThreadFlag.setThreadId(ServiceTestUtil.nextLong());

		_persistence.update(mbThreadFlag);

		return mbThreadFlag;
	}

	private static Log _log = LogFactoryUtil.getLog(MBThreadFlagPersistenceTest.class);
	private ModelListener<MBThreadFlag>[] _modelListeners;
	private MBThreadFlagPersistence _persistence = (MBThreadFlagPersistence)PortalBeanLocatorUtil.locate(MBThreadFlagPersistence.class.getName());
	private TransactionalPersistenceAdvice _transactionalPersistenceAdvice = (TransactionalPersistenceAdvice)PortalBeanLocatorUtil.locate(TransactionalPersistenceAdvice.class.getName());
}