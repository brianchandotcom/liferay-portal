/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQueryFactoryUtil;
import com.liferay.portal.kernel.dao.orm.ProjectionFactoryUtil;
import com.liferay.portal.kernel.dao.orm.RestrictionsFactoryUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.test.ExecutionTestListeners;
import com.liferay.portal.kernel.util.Time;
import com.liferay.portal.service.ServiceTestUtil;
import com.liferay.portal.service.persistence.BasePersistence;
import com.liferay.portal.service.persistence.PersistenceExecutionTestListener;
import com.liferay.portal.test.LiferayPersistenceIntegrationJUnitTestRunner;
import com.liferay.portal.test.persistence.TransactionalPersistenceAdvice;
import com.liferay.portal.util.PropsValues;

import com.liferay.portlet.messageboards.NoSuchBanException;
import com.liferay.portlet.messageboards.model.MBBan;
import com.liferay.portlet.messageboards.model.impl.MBBanModelImpl;

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
public class MBBanPersistenceTest {
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
		long pk = ServiceTestUtil.nextLong();

		MBBan newMBBan = _persistence.create(pk);

		newMBBan.setGroupId(ServiceTestUtil.nextLong());

		newMBBan.setCompanyId(ServiceTestUtil.nextLong());

		newMBBan.setUserId(ServiceTestUtil.nextLong());

		newMBBan.setUserName(ServiceTestUtil.randomString());

		newMBBan.setCreateDate(ServiceTestUtil.nextDate());

		newMBBan.setModifiedDate(ServiceTestUtil.nextDate());

		newMBBan.setBanUserId(ServiceTestUtil.nextLong());

		_persistence.update(newMBBan);

		MBBan existingMBBan = _persistence.findByPrimaryKey(newMBBan.getPrimaryKey());

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
	public void testFindByPrimaryKeyExisting() throws Exception {
		MBBan newMBBan = addMBBan();

		MBBan existingMBBan = _persistence.findByPrimaryKey(newMBBan.getPrimaryKey());

		Assert.assertEquals(existingMBBan, newMBBan);
	}

	@Test
	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = ServiceTestUtil.nextLong();

		try {
			_persistence.findByPrimaryKey(pk);

			Assert.fail("Missing entity did not throw NoSuchBanException");
		}
		catch (NoSuchBanException nsee) {
		}
	}

	@Test
	public void testFetchByPrimaryKeyExisting() throws Exception {
		MBBan newMBBan = addMBBan();

		MBBan existingMBBan = _persistence.fetchByPrimaryKey(newMBBan.getPrimaryKey());

		Assert.assertEquals(existingMBBan, newMBBan);
	}

	@Test
	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = ServiceTestUtil.nextLong();

		MBBan missingMBBan = _persistence.fetchByPrimaryKey(pk);

		Assert.assertNull(missingMBBan);
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
				ServiceTestUtil.nextLong()));

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
				new Object[] { ServiceTestUtil.nextLong() }));

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

		Assert.assertEquals(existingMBBanModelImpl.getGroupId(),
			existingMBBanModelImpl.getOriginalGroupId());
		Assert.assertEquals(existingMBBanModelImpl.getBanUserId(),
			existingMBBanModelImpl.getOriginalBanUserId());
	}

	protected MBBan addMBBan() throws Exception {
		long pk = ServiceTestUtil.nextLong();

		MBBan mbBan = _persistence.create(pk);

		mbBan.setGroupId(ServiceTestUtil.nextLong());

		mbBan.setCompanyId(ServiceTestUtil.nextLong());

		mbBan.setUserId(ServiceTestUtil.nextLong());

		mbBan.setUserName(ServiceTestUtil.randomString());

		mbBan.setCreateDate(ServiceTestUtil.nextDate());

		mbBan.setModifiedDate(ServiceTestUtil.nextDate());

		mbBan.setBanUserId(ServiceTestUtil.nextLong());

		_persistence.update(mbBan);

		return mbBan;
	}

	private static Log _log = LogFactoryUtil.getLog(MBBanPersistenceTest.class);
	private MBBanPersistence _persistence = (MBBanPersistence)PortalBeanLocatorUtil.locate(MBBanPersistence.class.getName());
	private TransactionalPersistenceAdvice _transactionalPersistenceAdvice = (TransactionalPersistenceAdvice)PortalBeanLocatorUtil.locate(TransactionalPersistenceAdvice.class.getName());
}