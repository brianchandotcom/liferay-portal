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
import com.liferay.portal.service.persistence.BasePersistence;
import com.liferay.portal.service.persistence.PersistenceExecutionTestListener;
import com.liferay.portal.test.LiferayPersistenceIntegrationJUnitTestRunner;
import com.liferay.portal.test.persistence.test.TransactionalPersistenceAdvice;
import com.liferay.portal.util.PropsValues;
import com.liferay.portal.util.test.RandomTestUtil;

import com.liferay.portlet.messageboards.NoSuchBanException;
import com.liferay.portlet.messageboards.model.MBBan;
import com.liferay.portlet.messageboards.model.impl.MBBanModelImpl;
import com.liferay.portlet.messageboards.service.MBBanLocalServiceUtil;

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
public class MBBanPersistenceTest {
	@Before
	public void setUp() {
		_modelListeners = _persistence.getListeners();

		for (ModelListener<MBBan> modelListener : _modelListeners) {
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

		for (ModelListener<MBBan> modelListener : _modelListeners) {
			_persistence.registerListener(modelListener);
		}
	}

	@Test
	public void testCreate() throws Exception {
		long pk = RandomTestUtil.nextLong();

		MBBan mbBan = _persistence.create(pk);

		Assert.assertNotNull(mbBan);

		Assert.assertEquals(mbBan.getPrimaryKey(), pk);
	}

	@Test
	public void testRemove() throws Exception {
		MBBan newMBBan = addMBBan();

		_persistence.remove(newMBBan);

		MBBan existingMBBan = _persistence.fetchByPrimaryKey(newMBBan.getPrimaryKey());

		Assert.assertNull(existingMBBan);
	}

	@Test
	public void testUpdateNew() throws Exception {
		addMBBan();
	}

	@Test
	public void testUpdateExisting() throws Exception {
		long pk = RandomTestUtil.nextLong();

		MBBan newMBBan = _persistence.create(pk);

		newMBBan.setUuid(RandomTestUtil.randomString());

		newMBBan.setGroupId(RandomTestUtil.nextLong());

		newMBBan.setCompanyId(RandomTestUtil.nextLong());

		newMBBan.setUserId(RandomTestUtil.nextLong());

		newMBBan.setUserName(RandomTestUtil.randomString());

		newMBBan.setCreateDate(RandomTestUtil.nextDate());

		newMBBan.setModifiedDate(RandomTestUtil.nextDate());

		newMBBan.setBanUserId(RandomTestUtil.nextLong());

		_persistence.update(newMBBan);

		MBBan existingMBBan = _persistence.findByPrimaryKey(newMBBan.getPrimaryKey());

		Assert.assertEquals(existingMBBan.getUuid(), newMBBan.getUuid());
		Assert.assertEquals(existingMBBan.getBanId(), newMBBan.getBanId());
		Assert.assertEquals(existingMBBan.getGroupId(), newMBBan.getGroupId());
		Assert.assertEquals(existingMBBan.getCompanyId(),
			newMBBan.getCompanyId());
		Assert.assertEquals(existingMBBan.getUserId(), newMBBan.getUserId());
		Assert.assertEquals(existingMBBan.getUserName(), newMBBan.getUserName());
		Assert.assertEquals(Time.getShortTimestamp(
				existingMBBan.getCreateDate()),
			Time.getShortTimestamp(newMBBan.getCreateDate()));
		Assert.assertEquals(Time.getShortTimestamp(
				existingMBBan.getModifiedDate()),
			Time.getShortTimestamp(newMBBan.getModifiedDate()));
		Assert.assertEquals(existingMBBan.getBanUserId(),
			newMBBan.getBanUserId());
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
				RandomTestUtil.nextLong());

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
				RandomTestUtil.nextLong());

			_persistence.countByUuid_C(StringPool.NULL, 0L);

			_persistence.countByUuid_C((String)null, 0L);
		}
		catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void testCountByGroupId() {
		try {
			_persistence.countByGroupId(RandomTestUtil.nextLong());

			_persistence.countByGroupId(0L);
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
	public void testCountByBanUserId() {
		try {
			_persistence.countByBanUserId(RandomTestUtil.nextLong());

			_persistence.countByBanUserId(0L);
		}
		catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void testCountByG_B() {
		try {
			_persistence.countByG_B(RandomTestUtil.nextLong(),
				RandomTestUtil.nextLong());

			_persistence.countByG_B(0L, 0L);
		}
		catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void testFindByPrimaryKeyExisting() throws Exception {
		MBBan newMBBan = addMBBan();

		MBBan existingMBBan = _persistence.findByPrimaryKey(newMBBan.getPrimaryKey());

		Assert.assertEquals(existingMBBan, newMBBan);
	}

	@Test
	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		try {
			_persistence.findByPrimaryKey(pk);

			Assert.fail("Missing entity did not throw NoSuchBanException");
		}
		catch (NoSuchBanException nsee) {
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
		return OrderByComparatorFactoryUtil.create("MBBan", "uuid", true,
			"banId", true, "groupId", true, "companyId", true, "userId", true,
			"userName", true, "createDate", true, "modifiedDate", true,
			"banUserId", true);
	}

	@Test
	public void testFetchByPrimaryKeyExisting() throws Exception {
		MBBan newMBBan = addMBBan();

		MBBan existingMBBan = _persistence.fetchByPrimaryKey(newMBBan.getPrimaryKey());

		Assert.assertEquals(existingMBBan, newMBBan);
	}

	@Test
	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		MBBan missingMBBan = _persistence.fetchByPrimaryKey(pk);

		Assert.assertNull(missingMBBan);
	}

	@Test
	public void testActionableDynamicQuery() throws Exception {
		final IntegerWrapper count = new IntegerWrapper();

		ActionableDynamicQuery actionableDynamicQuery = MBBanLocalServiceUtil.getActionableDynamicQuery();

		actionableDynamicQuery.setPerformActionMethod(new ActionableDynamicQuery.PerformActionMethod() {
				@Override
				public void performAction(Object object) {
					MBBan mbBan = (MBBan)object;

					Assert.assertNotNull(mbBan);

					count.increment();
				}
			});

		actionableDynamicQuery.performActions();

		Assert.assertEquals(count.getValue(), _persistence.countAll());
	}

	@Test
	public void testDynamicQueryByPrimaryKeyExisting()
		throws Exception {
		MBBan newMBBan = addMBBan();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(MBBan.class,
				MBBan.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("banId", newMBBan.getBanId()));

		List<MBBan> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		MBBan existingMBBan = result.get(0);

		Assert.assertEquals(existingMBBan, newMBBan);
	}

	@Test
	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(MBBan.class,
				MBBan.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("banId",
				RandomTestUtil.nextLong()));

		List<MBBan> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testDynamicQueryByProjectionExisting()
		throws Exception {
		MBBan newMBBan = addMBBan();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(MBBan.class,
				MBBan.class.getClassLoader());

		dynamicQuery.setProjection(ProjectionFactoryUtil.property("banId"));

		Object newBanId = newMBBan.getBanId();

		dynamicQuery.add(RestrictionsFactoryUtil.in("banId",
				new Object[] { newBanId }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		Object existingBanId = result.get(0);

		Assert.assertEquals(existingBanId, newBanId);
	}

	@Test
	public void testDynamicQueryByProjectionMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(MBBan.class,
				MBBan.class.getClassLoader());

		dynamicQuery.setProjection(ProjectionFactoryUtil.property("banId"));

		dynamicQuery.add(RestrictionsFactoryUtil.in("banId",
				new Object[] { RandomTestUtil.nextLong() }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testResetOriginalValues() throws Exception {
		if (!PropsValues.HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE) {
			return;
		}

		MBBan newMBBan = addMBBan();

		_persistence.clearCache();

		MBBanModelImpl existingMBBanModelImpl = (MBBanModelImpl)_persistence.findByPrimaryKey(newMBBan.getPrimaryKey());

		Assert.assertTrue(Validator.equals(existingMBBanModelImpl.getUuid(),
				existingMBBanModelImpl.getOriginalUuid()));
		Assert.assertEquals(existingMBBanModelImpl.getGroupId(),
			existingMBBanModelImpl.getOriginalGroupId());

		Assert.assertEquals(existingMBBanModelImpl.getGroupId(),
			existingMBBanModelImpl.getOriginalGroupId());
		Assert.assertEquals(existingMBBanModelImpl.getBanUserId(),
			existingMBBanModelImpl.getOriginalBanUserId());
	}

	protected MBBan addMBBan() throws Exception {
		long pk = RandomTestUtil.nextLong();

		MBBan mbBan = _persistence.create(pk);

		mbBan.setUuid(RandomTestUtil.randomString());

		mbBan.setGroupId(RandomTestUtil.nextLong());

		mbBan.setCompanyId(RandomTestUtil.nextLong());

		mbBan.setUserId(RandomTestUtil.nextLong());

		mbBan.setUserName(RandomTestUtil.randomString());

		mbBan.setCreateDate(RandomTestUtil.nextDate());

		mbBan.setModifiedDate(RandomTestUtil.nextDate());

		mbBan.setBanUserId(RandomTestUtil.nextLong());

		_persistence.update(mbBan);

		return mbBan;
	}

	private static Log _log = LogFactoryUtil.getLog(MBBanPersistenceTest.class);
	private ModelListener<MBBan>[] _modelListeners;
	private MBBanPersistence _persistence = (MBBanPersistence)PortalBeanLocatorUtil.locate(MBBanPersistence.class.getName());
	private TransactionalPersistenceAdvice _transactionalPersistenceAdvice = (TransactionalPersistenceAdvice)PortalBeanLocatorUtil.locate(TransactionalPersistenceAdvice.class.getName());
}