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
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.service.ServiceTestUtil;
import com.liferay.portal.service.persistence.BasePersistence;
import com.liferay.portal.service.persistence.PersistenceExecutionTestListener;
import com.liferay.portal.test.LiferayPersistenceIntegrationJUnitTestRunner;
import com.liferay.portal.test.persistence.TransactionalPersistenceAdvice;
import com.liferay.portal.util.PropsValues;

import com.liferay.portlet.messageboards.NoSuchCategoryException;
import com.liferay.portlet.messageboards.model.MBCategory;
import com.liferay.portlet.messageboards.model.impl.MBCategoryModelImpl;

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
public class MBCategoryPersistenceTest {
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

		MBCategory mbCategory = _persistence.create(pk);

		Assert.assertNotNull(mbCategory);

		Assert.assertEquals(mbCategory.getPrimaryKey(), pk);
	}

	@Test
	public void testRemove() throws Exception {
		MBCategory newMBCategory = addMBCategory();

		_persistence.remove(newMBCategory);

		MBCategory existingMBCategory = _persistence.fetchByPrimaryKey(newMBCategory.getPrimaryKey());

		Assert.assertNull(existingMBCategory);
	}

	@Test
	public void testUpdateNew() throws Exception {
		addMBCategory();
	}

	@Test
	public void testUpdateExisting() throws Exception {
		long pk = ServiceTestUtil.nextLong();

		MBCategory newMBCategory = _persistence.create(pk);

		newMBCategory.setUuid(ServiceTestUtil.randomString());

		newMBCategory.setGroupId(ServiceTestUtil.nextLong());

		newMBCategory.setCompanyId(ServiceTestUtil.nextLong());

		newMBCategory.setUserId(ServiceTestUtil.nextLong());

		newMBCategory.setUserName(ServiceTestUtil.randomString());

		newMBCategory.setCreateDate(ServiceTestUtil.nextDate());

		newMBCategory.setModifiedDate(ServiceTestUtil.nextDate());

		newMBCategory.setParentCategoryId(ServiceTestUtil.nextLong());

		newMBCategory.setName(ServiceTestUtil.randomString());

		newMBCategory.setDescription(ServiceTestUtil.randomString());

		newMBCategory.setDisplayStyle(ServiceTestUtil.randomString());

		newMBCategory.setThreadCount(ServiceTestUtil.nextInt());

		newMBCategory.setMessageCount(ServiceTestUtil.nextInt());

		newMBCategory.setLastPostDate(ServiceTestUtil.nextDate());

		newMBCategory.setStatus(ServiceTestUtil.nextInt());

		newMBCategory.setStatusByUserId(ServiceTestUtil.nextLong());

		newMBCategory.setStatusByUserName(ServiceTestUtil.randomString());

		newMBCategory.setStatusDate(ServiceTestUtil.nextDate());

		_persistence.update(newMBCategory);

		MBCategory existingMBCategory = _persistence.findByPrimaryKey(newMBCategory.getPrimaryKey());

		Assert.assertEquals(existingMBCategory.getUuid(),
			newMBCategory.getUuid());
		Assert.assertEquals(existingMBCategory.getCategoryId(),
			newMBCategory.getCategoryId());
		Assert.assertEquals(existingMBCategory.getGroupId(),
			newMBCategory.getGroupId());
		Assert.assertEquals(existingMBCategory.getCompanyId(),
			newMBCategory.getCompanyId());
		Assert.assertEquals(existingMBCategory.getUserId(),
			newMBCategory.getUserId());
		Assert.assertEquals(existingMBCategory.getUserName(),
			newMBCategory.getUserName());
		Assert.assertEquals(Time.getShortTimestamp(
				existingMBCategory.getCreateDate()),
			Time.getShortTimestamp(newMBCategory.getCreateDate()));
		Assert.assertEquals(Time.getShortTimestamp(
				existingMBCategory.getModifiedDate()),
			Time.getShortTimestamp(newMBCategory.getModifiedDate()));
		Assert.assertEquals(existingMBCategory.getParentCategoryId(),
			newMBCategory.getParentCategoryId());
		Assert.assertEquals(existingMBCategory.getName(),
			newMBCategory.getName());
		Assert.assertEquals(existingMBCategory.getDescription(),
			newMBCategory.getDescription());
		Assert.assertEquals(existingMBCategory.getDisplayStyle(),
			newMBCategory.getDisplayStyle());
		Assert.assertEquals(existingMBCategory.getThreadCount(),
			newMBCategory.getThreadCount());
		Assert.assertEquals(existingMBCategory.getMessageCount(),
			newMBCategory.getMessageCount());
		Assert.assertEquals(Time.getShortTimestamp(
				existingMBCategory.getLastPostDate()),
			Time.getShortTimestamp(newMBCategory.getLastPostDate()));
		Assert.assertEquals(existingMBCategory.getStatus(),
			newMBCategory.getStatus());
		Assert.assertEquals(existingMBCategory.getStatusByUserId(),
			newMBCategory.getStatusByUserId());
		Assert.assertEquals(existingMBCategory.getStatusByUserName(),
			newMBCategory.getStatusByUserName());
		Assert.assertEquals(Time.getShortTimestamp(
				existingMBCategory.getStatusDate()),
			Time.getShortTimestamp(newMBCategory.getStatusDate()));
	}

	@Test
	public void testFindByPrimaryKeyExisting() throws Exception {
		MBCategory newMBCategory = addMBCategory();

		MBCategory existingMBCategory = _persistence.findByPrimaryKey(newMBCategory.getPrimaryKey());

		Assert.assertEquals(existingMBCategory, newMBCategory);
	}

	@Test
	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = ServiceTestUtil.nextLong();

		try {
			_persistence.findByPrimaryKey(pk);

			Assert.fail("Missing entity did not throw NoSuchCategoryException");
		}
		catch (NoSuchCategoryException nsee) {
		}
	}

	@Test
	public void testFetchByPrimaryKeyExisting() throws Exception {
		MBCategory newMBCategory = addMBCategory();

		MBCategory existingMBCategory = _persistence.fetchByPrimaryKey(newMBCategory.getPrimaryKey());

		Assert.assertEquals(existingMBCategory, newMBCategory);
	}

	@Test
	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = ServiceTestUtil.nextLong();

		MBCategory missingMBCategory = _persistence.fetchByPrimaryKey(pk);

		Assert.assertNull(missingMBCategory);
	}

	@Test
	public void testDynamicQueryByPrimaryKeyExisting()
		throws Exception {
		MBCategory newMBCategory = addMBCategory();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(MBCategory.class,
				MBCategory.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("categoryId",
				newMBCategory.getCategoryId()));

		List<MBCategory> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		MBCategory existingMBCategory = result.get(0);

		Assert.assertEquals(existingMBCategory, newMBCategory);
	}

	@Test
	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(MBCategory.class,
				MBCategory.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("categoryId",
				ServiceTestUtil.nextLong()));

		List<MBCategory> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testDynamicQueryByProjectionExisting()
		throws Exception {
		MBCategory newMBCategory = addMBCategory();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(MBCategory.class,
				MBCategory.class.getClassLoader());

		dynamicQuery.setProjection(ProjectionFactoryUtil.property("categoryId"));

		Object newCategoryId = newMBCategory.getCategoryId();

		dynamicQuery.add(RestrictionsFactoryUtil.in("categoryId",
				new Object[] { newCategoryId }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		Object existingCategoryId = result.get(0);

		Assert.assertEquals(existingCategoryId, newCategoryId);
	}

	@Test
	public void testDynamicQueryByProjectionMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(MBCategory.class,
				MBCategory.class.getClassLoader());

		dynamicQuery.setProjection(ProjectionFactoryUtil.property("categoryId"));

		dynamicQuery.add(RestrictionsFactoryUtil.in("categoryId",
				new Object[] { ServiceTestUtil.nextLong() }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testResetOriginalValues() throws Exception {
		if (!PropsValues.HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE) {
			return;
		}

		MBCategory newMBCategory = addMBCategory();

		_persistence.clearCache();

		MBCategoryModelImpl existingMBCategoryModelImpl = (MBCategoryModelImpl)_persistence.findByPrimaryKey(newMBCategory.getPrimaryKey());

		Assert.assertTrue(Validator.equals(
				existingMBCategoryModelImpl.getUuid(),
				existingMBCategoryModelImpl.getOriginalUuid()));
		Assert.assertEquals(existingMBCategoryModelImpl.getGroupId(),
			existingMBCategoryModelImpl.getOriginalGroupId());
	}

	protected MBCategory addMBCategory() throws Exception {
		long pk = ServiceTestUtil.nextLong();

		MBCategory mbCategory = _persistence.create(pk);

		mbCategory.setUuid(ServiceTestUtil.randomString());

		mbCategory.setGroupId(ServiceTestUtil.nextLong());

		mbCategory.setCompanyId(ServiceTestUtil.nextLong());

		mbCategory.setUserId(ServiceTestUtil.nextLong());

		mbCategory.setUserName(ServiceTestUtil.randomString());

		mbCategory.setCreateDate(ServiceTestUtil.nextDate());

		mbCategory.setModifiedDate(ServiceTestUtil.nextDate());

		mbCategory.setParentCategoryId(ServiceTestUtil.nextLong());

		mbCategory.setName(ServiceTestUtil.randomString());

		mbCategory.setDescription(ServiceTestUtil.randomString());

		mbCategory.setDisplayStyle(ServiceTestUtil.randomString());

		mbCategory.setThreadCount(ServiceTestUtil.nextInt());

		mbCategory.setMessageCount(ServiceTestUtil.nextInt());

		mbCategory.setLastPostDate(ServiceTestUtil.nextDate());

		mbCategory.setStatus(ServiceTestUtil.nextInt());

		mbCategory.setStatusByUserId(ServiceTestUtil.nextLong());

		mbCategory.setStatusByUserName(ServiceTestUtil.randomString());

		mbCategory.setStatusDate(ServiceTestUtil.nextDate());

		_persistence.update(mbCategory);

		return mbCategory;
	}

	private static Log _log = LogFactoryUtil.getLog(MBCategoryPersistenceTest.class);
	private MBCategoryPersistence _persistence = (MBCategoryPersistence)PortalBeanLocatorUtil.locate(MBCategoryPersistence.class.getName());
	private TransactionalPersistenceAdvice _transactionalPersistenceAdvice = (TransactionalPersistenceAdvice)PortalBeanLocatorUtil.locate(TransactionalPersistenceAdvice.class.getName());
}