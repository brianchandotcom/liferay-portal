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

package com.liferay.portlet.documentlibrary.service.persistence;

import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQueryFactoryUtil;
import com.liferay.portal.kernel.dao.orm.ProjectionFactoryUtil;
import com.liferay.portal.kernel.dao.orm.RestrictionsFactoryUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.test.ExecutionTestListeners;
import com.liferay.portal.kernel.util.Time;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.service.ServiceTestUtil;
import com.liferay.portal.service.persistence.BasePersistence;
import com.liferay.portal.service.persistence.PersistenceExecutionTestListener;
import com.liferay.portal.test.LiferayPersistenceIntegrationJUnitTestRunner;
import com.liferay.portal.test.persistence.TransactionalPersistenceAdvice;
import com.liferay.portal.util.PropsValues;

import com.liferay.portlet.documentlibrary.NoSuchFileRankException;
import com.liferay.portlet.documentlibrary.model.DLFileRank;
import com.liferay.portlet.documentlibrary.model.impl.DLFileRankModelImpl;

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
public class DLFileRankPersistenceTest {
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

		DLFileRank dlFileRank = _persistence.create(pk);

		Assert.assertNotNull(dlFileRank);

		Assert.assertEquals(dlFileRank.getPrimaryKey(), pk);
	}

	@Test
	public void testRemove() throws Exception {
		DLFileRank newDLFileRank = addDLFileRank();

		_persistence.remove(newDLFileRank);

		DLFileRank existingDLFileRank = _persistence.fetchByPrimaryKey(newDLFileRank.getPrimaryKey());

		Assert.assertNull(existingDLFileRank);
	}

	@Test
	public void testUpdateNew() throws Exception {
		addDLFileRank();
	}

	@Test
	public void testUpdateExisting() throws Exception {
		long pk = ServiceTestUtil.nextLong();

		DLFileRank newDLFileRank = _persistence.create(pk);

		newDLFileRank.setUuid(ServiceTestUtil.randomString());

		newDLFileRank.setGroupId(ServiceTestUtil.nextLong());

		newDLFileRank.setCompanyId(ServiceTestUtil.nextLong());

		newDLFileRank.setUserId(ServiceTestUtil.nextLong());

		newDLFileRank.setUserName(ServiceTestUtil.randomString());

		newDLFileRank.setCreateDate(ServiceTestUtil.nextDate());

		newDLFileRank.setModifiedDate(ServiceTestUtil.nextDate());

		newDLFileRank.setFileEntryId(ServiceTestUtil.nextLong());

		newDLFileRank.setActive(ServiceTestUtil.randomBoolean());

		_persistence.update(newDLFileRank);

		DLFileRank existingDLFileRank = _persistence.findByPrimaryKey(newDLFileRank.getPrimaryKey());

		Assert.assertEquals(existingDLFileRank.getUuid(),
			newDLFileRank.getUuid());
		Assert.assertEquals(existingDLFileRank.getFileRankId(),
			newDLFileRank.getFileRankId());
		Assert.assertEquals(existingDLFileRank.getGroupId(),
			newDLFileRank.getGroupId());
		Assert.assertEquals(existingDLFileRank.getCompanyId(),
			newDLFileRank.getCompanyId());
		Assert.assertEquals(existingDLFileRank.getUserId(),
			newDLFileRank.getUserId());
		Assert.assertEquals(existingDLFileRank.getUserName(),
			newDLFileRank.getUserName());
		Assert.assertEquals(Time.getShortTimestamp(
				existingDLFileRank.getCreateDate()),
			Time.getShortTimestamp(newDLFileRank.getCreateDate()));
		Assert.assertEquals(Time.getShortTimestamp(
				existingDLFileRank.getModifiedDate()),
			Time.getShortTimestamp(newDLFileRank.getModifiedDate()));
		Assert.assertEquals(existingDLFileRank.getFileEntryId(),
			newDLFileRank.getFileEntryId());
		Assert.assertEquals(existingDLFileRank.getActive(),
			newDLFileRank.getActive());
	}

	@Test
	public void testFindByPrimaryKeyExisting() throws Exception {
		DLFileRank newDLFileRank = addDLFileRank();

		DLFileRank existingDLFileRank = _persistence.findByPrimaryKey(newDLFileRank.getPrimaryKey());

		Assert.assertEquals(existingDLFileRank, newDLFileRank);
	}

	@Test
	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = ServiceTestUtil.nextLong();

		try {
			_persistence.findByPrimaryKey(pk);

			Assert.fail("Missing entity did not throw NoSuchFileRankException");
		}
		catch (NoSuchFileRankException nsee) {
		}
	}

	@Test
	public void testFetchByPrimaryKeyExisting() throws Exception {
		DLFileRank newDLFileRank = addDLFileRank();

		DLFileRank existingDLFileRank = _persistence.fetchByPrimaryKey(newDLFileRank.getPrimaryKey());

		Assert.assertEquals(existingDLFileRank, newDLFileRank);
	}

	@Test
	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = ServiceTestUtil.nextLong();

		DLFileRank missingDLFileRank = _persistence.fetchByPrimaryKey(pk);

		Assert.assertNull(missingDLFileRank);
	}

	@Test
	public void testDynamicQueryByPrimaryKeyExisting()
		throws Exception {
		DLFileRank newDLFileRank = addDLFileRank();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(DLFileRank.class,
				DLFileRank.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("fileRankId",
				newDLFileRank.getFileRankId()));

		List<DLFileRank> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		DLFileRank existingDLFileRank = result.get(0);

		Assert.assertEquals(existingDLFileRank, newDLFileRank);
	}

	@Test
	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(DLFileRank.class,
				DLFileRank.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("fileRankId",
				ServiceTestUtil.nextLong()));

		List<DLFileRank> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testDynamicQueryByProjectionExisting()
		throws Exception {
		DLFileRank newDLFileRank = addDLFileRank();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(DLFileRank.class,
				DLFileRank.class.getClassLoader());

		dynamicQuery.setProjection(ProjectionFactoryUtil.property("fileRankId"));

		Object newFileRankId = newDLFileRank.getFileRankId();

		dynamicQuery.add(RestrictionsFactoryUtil.in("fileRankId",
				new Object[] { newFileRankId }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		Object existingFileRankId = result.get(0);

		Assert.assertEquals(existingFileRankId, newFileRankId);
	}

	@Test
	public void testDynamicQueryByProjectionMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(DLFileRank.class,
				DLFileRank.class.getClassLoader());

		dynamicQuery.setProjection(ProjectionFactoryUtil.property("fileRankId"));

		dynamicQuery.add(RestrictionsFactoryUtil.in("fileRankId",
				new Object[] { ServiceTestUtil.nextLong() }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testResetOriginalValues() throws Exception {
		if (!PropsValues.HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE) {
			return;
		}

		DLFileRank newDLFileRank = addDLFileRank();

		_persistence.clearCache();

		DLFileRankModelImpl existingDLFileRankModelImpl = (DLFileRankModelImpl)_persistence.findByPrimaryKey(newDLFileRank.getPrimaryKey());

		Assert.assertTrue(Validator.equals(
				existingDLFileRankModelImpl.getUuid(),
				existingDLFileRankModelImpl.getOriginalUuid()));
		Assert.assertEquals(existingDLFileRankModelImpl.getGroupId(),
			existingDLFileRankModelImpl.getOriginalGroupId());

		Assert.assertEquals(existingDLFileRankModelImpl.getCompanyId(),
			existingDLFileRankModelImpl.getOriginalCompanyId());
		Assert.assertEquals(existingDLFileRankModelImpl.getUserId(),
			existingDLFileRankModelImpl.getOriginalUserId());
		Assert.assertEquals(existingDLFileRankModelImpl.getFileEntryId(),
			existingDLFileRankModelImpl.getOriginalFileEntryId());
	}

	protected DLFileRank addDLFileRank() throws Exception {
		long pk = ServiceTestUtil.nextLong();

		DLFileRank dlFileRank = _persistence.create(pk);

		dlFileRank.setUuid(ServiceTestUtil.randomString());

		dlFileRank.setGroupId(ServiceTestUtil.nextLong());

		dlFileRank.setCompanyId(ServiceTestUtil.nextLong());

		dlFileRank.setUserId(ServiceTestUtil.nextLong());

		dlFileRank.setUserName(ServiceTestUtil.randomString());

		dlFileRank.setCreateDate(ServiceTestUtil.nextDate());

		dlFileRank.setModifiedDate(ServiceTestUtil.nextDate());

		dlFileRank.setFileEntryId(ServiceTestUtil.nextLong());

		dlFileRank.setActive(ServiceTestUtil.randomBoolean());

		_persistence.update(dlFileRank);

		return dlFileRank;
	}

	private static Log _log = LogFactoryUtil.getLog(DLFileRankPersistenceTest.class);
	private DLFileRankPersistence _persistence = (DLFileRankPersistence)PortalBeanLocatorUtil.locate(DLFileRankPersistence.class.getName());
	private TransactionalPersistenceAdvice _transactionalPersistenceAdvice = (TransactionalPersistenceAdvice)PortalBeanLocatorUtil.locate(TransactionalPersistenceAdvice.class.getName());
}