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

package com.liferay.portlet.documentlibrary.service.persistence;

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
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.service.ServiceTestUtil;
import com.liferay.portal.service.persistence.BasePersistence;
import com.liferay.portal.service.persistence.PersistenceExecutionTestListener;
import com.liferay.portal.test.LiferayPersistenceIntegrationJUnitTestRunner;
import com.liferay.portal.test.persistence.TransactionalPersistenceAdvice;
import com.liferay.portal.util.PropsValues;

import com.liferay.portlet.documentlibrary.NoSuchFileEntryTypeException;
import com.liferay.portlet.documentlibrary.model.DLFileEntryType;
import com.liferay.portlet.documentlibrary.model.impl.DLFileEntryTypeModelImpl;

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
public class DLFileEntryTypePersistenceTest {
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

		DLFileEntryType dlFileEntryType = _persistence.create(pk);

		Assert.assertNotNull(dlFileEntryType);

		Assert.assertEquals(dlFileEntryType.getPrimaryKey(), pk);
	}

	@Test
	public void testRemove() throws Exception {
		DLFileEntryType newDLFileEntryType = addDLFileEntryType();

		_persistence.remove(newDLFileEntryType);

		DLFileEntryType existingDLFileEntryType = _persistence.fetchByPrimaryKey(newDLFileEntryType.getPrimaryKey());

		Assert.assertNull(existingDLFileEntryType);
	}

	@Test
	public void testUpdateNew() throws Exception {
		addDLFileEntryType();
	}

	@Test
	public void testUpdateExisting() throws Exception {
		long pk = ServiceTestUtil.nextLong();

		DLFileEntryType newDLFileEntryType = _persistence.create(pk);

		newDLFileEntryType.setUuid(ServiceTestUtil.randomString());

		newDLFileEntryType.setGroupId(ServiceTestUtil.nextLong());

		newDLFileEntryType.setCompanyId(ServiceTestUtil.nextLong());

		newDLFileEntryType.setUserId(ServiceTestUtil.nextLong());

		newDLFileEntryType.setUserName(ServiceTestUtil.randomString());

		newDLFileEntryType.setCreateDate(ServiceTestUtil.nextDate());

		newDLFileEntryType.setModifiedDate(ServiceTestUtil.nextDate());

		newDLFileEntryType.setFileEntryTypeKey(ServiceTestUtil.randomString());

		newDLFileEntryType.setName(ServiceTestUtil.randomString());

		newDLFileEntryType.setDescription(ServiceTestUtil.randomString());

		_persistence.update(newDLFileEntryType);

		DLFileEntryType existingDLFileEntryType = _persistence.findByPrimaryKey(newDLFileEntryType.getPrimaryKey());

		Assert.assertEquals(existingDLFileEntryType.getUuid(),
			newDLFileEntryType.getUuid());
		Assert.assertEquals(existingDLFileEntryType.getFileEntryTypeId(),
			newDLFileEntryType.getFileEntryTypeId());
		Assert.assertEquals(existingDLFileEntryType.getGroupId(),
			newDLFileEntryType.getGroupId());
		Assert.assertEquals(existingDLFileEntryType.getCompanyId(),
			newDLFileEntryType.getCompanyId());
		Assert.assertEquals(existingDLFileEntryType.getUserId(),
			newDLFileEntryType.getUserId());
		Assert.assertEquals(existingDLFileEntryType.getUserName(),
			newDLFileEntryType.getUserName());
		Assert.assertEquals(Time.getShortTimestamp(
				existingDLFileEntryType.getCreateDate()),
			Time.getShortTimestamp(newDLFileEntryType.getCreateDate()));
		Assert.assertEquals(Time.getShortTimestamp(
				existingDLFileEntryType.getModifiedDate()),
			Time.getShortTimestamp(newDLFileEntryType.getModifiedDate()));
		Assert.assertEquals(existingDLFileEntryType.getFileEntryTypeKey(),
			newDLFileEntryType.getFileEntryTypeKey());
		Assert.assertEquals(existingDLFileEntryType.getName(),
			newDLFileEntryType.getName());
		Assert.assertEquals(existingDLFileEntryType.getDescription(),
			newDLFileEntryType.getDescription());
	}

	@Test
	public void testFindByPrimaryKeyExisting() throws Exception {
		DLFileEntryType newDLFileEntryType = addDLFileEntryType();

		DLFileEntryType existingDLFileEntryType = _persistence.findByPrimaryKey(newDLFileEntryType.getPrimaryKey());

		Assert.assertEquals(existingDLFileEntryType, newDLFileEntryType);
	}

	@Test
	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = ServiceTestUtil.nextLong();

		try {
			_persistence.findByPrimaryKey(pk);

			Assert.fail(
				"Missing entity did not throw NoSuchFileEntryTypeException");
		}
		catch (NoSuchFileEntryTypeException nsee) {
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
		return OrderByComparatorFactoryUtil.create("DLFileEntryType", "uuid",
			true, "fileEntryTypeId", true, "groupId", true, "companyId", true,
			"userId", true, "userName", true, "createDate", true,
			"modifiedDate", true, "fileEntryTypeKey", true, "name", true,
			"description", true);
	}

	@Test
	public void testFetchByPrimaryKeyExisting() throws Exception {
		DLFileEntryType newDLFileEntryType = addDLFileEntryType();

		DLFileEntryType existingDLFileEntryType = _persistence.fetchByPrimaryKey(newDLFileEntryType.getPrimaryKey());

		Assert.assertEquals(existingDLFileEntryType, newDLFileEntryType);
	}

	@Test
	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = ServiceTestUtil.nextLong();

		DLFileEntryType missingDLFileEntryType = _persistence.fetchByPrimaryKey(pk);

		Assert.assertNull(missingDLFileEntryType);
	}

	@Test
	public void testActionableDynamicQuery() throws Exception {
		final IntegerWrapper count = new IntegerWrapper();

		ActionableDynamicQuery actionableDynamicQuery = new DLFileEntryTypeActionableDynamicQuery() {
				@Override
				protected void performAction(Object object) {
					DLFileEntryType dlFileEntryType = (DLFileEntryType)object;

					Assert.assertNotNull(dlFileEntryType);

					count.increment();
				}
			};

		actionableDynamicQuery.performActions();

		Assert.assertEquals(count.getValue(), _persistence.countAll());
	}

	@Test
	public void testDynamicQueryByPrimaryKeyExisting()
		throws Exception {
		DLFileEntryType newDLFileEntryType = addDLFileEntryType();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(DLFileEntryType.class,
				DLFileEntryType.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("fileEntryTypeId",
				newDLFileEntryType.getFileEntryTypeId()));

		List<DLFileEntryType> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		DLFileEntryType existingDLFileEntryType = result.get(0);

		Assert.assertEquals(existingDLFileEntryType, newDLFileEntryType);
	}

	@Test
	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(DLFileEntryType.class,
				DLFileEntryType.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("fileEntryTypeId",
				ServiceTestUtil.nextLong()));

		List<DLFileEntryType> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testDynamicQueryByProjectionExisting()
		throws Exception {
		DLFileEntryType newDLFileEntryType = addDLFileEntryType();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(DLFileEntryType.class,
				DLFileEntryType.class.getClassLoader());

		dynamicQuery.setProjection(ProjectionFactoryUtil.property(
				"fileEntryTypeId"));

		Object newFileEntryTypeId = newDLFileEntryType.getFileEntryTypeId();

		dynamicQuery.add(RestrictionsFactoryUtil.in("fileEntryTypeId",
				new Object[] { newFileEntryTypeId }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		Object existingFileEntryTypeId = result.get(0);

		Assert.assertEquals(existingFileEntryTypeId, newFileEntryTypeId);
	}

	@Test
	public void testDynamicQueryByProjectionMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(DLFileEntryType.class,
				DLFileEntryType.class.getClassLoader());

		dynamicQuery.setProjection(ProjectionFactoryUtil.property(
				"fileEntryTypeId"));

		dynamicQuery.add(RestrictionsFactoryUtil.in("fileEntryTypeId",
				new Object[] { ServiceTestUtil.nextLong() }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testResetOriginalValues() throws Exception {
		if (!PropsValues.HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE) {
			return;
		}

		DLFileEntryType newDLFileEntryType = addDLFileEntryType();

		_persistence.clearCache();

		DLFileEntryTypeModelImpl existingDLFileEntryTypeModelImpl = (DLFileEntryTypeModelImpl)_persistence.findByPrimaryKey(newDLFileEntryType.getPrimaryKey());

		Assert.assertTrue(Validator.equals(
				existingDLFileEntryTypeModelImpl.getUuid(),
				existingDLFileEntryTypeModelImpl.getOriginalUuid()));
		Assert.assertEquals(existingDLFileEntryTypeModelImpl.getGroupId(),
			existingDLFileEntryTypeModelImpl.getOriginalGroupId());

		Assert.assertEquals(existingDLFileEntryTypeModelImpl.getGroupId(),
			existingDLFileEntryTypeModelImpl.getOriginalGroupId());
		Assert.assertTrue(Validator.equals(
				existingDLFileEntryTypeModelImpl.getFileEntryTypeKey(),
				existingDLFileEntryTypeModelImpl.getOriginalFileEntryTypeKey()));
	}

	protected DLFileEntryType addDLFileEntryType() throws Exception {
		long pk = ServiceTestUtil.nextLong();

		DLFileEntryType dlFileEntryType = _persistence.create(pk);

		dlFileEntryType.setUuid(ServiceTestUtil.randomString());

		dlFileEntryType.setGroupId(ServiceTestUtil.nextLong());

		dlFileEntryType.setCompanyId(ServiceTestUtil.nextLong());

		dlFileEntryType.setUserId(ServiceTestUtil.nextLong());

		dlFileEntryType.setUserName(ServiceTestUtil.randomString());

		dlFileEntryType.setCreateDate(ServiceTestUtil.nextDate());

		dlFileEntryType.setModifiedDate(ServiceTestUtil.nextDate());

		dlFileEntryType.setFileEntryTypeKey(ServiceTestUtil.randomString());

		dlFileEntryType.setName(ServiceTestUtil.randomString());

		dlFileEntryType.setDescription(ServiceTestUtil.randomString());

		_persistence.update(dlFileEntryType);

		return dlFileEntryType;
	}

	private static Log _log = LogFactoryUtil.getLog(DLFileEntryTypePersistenceTest.class);
	private DLFileEntryTypePersistence _persistence = (DLFileEntryTypePersistence)PortalBeanLocatorUtil.locate(DLFileEntryTypePersistence.class.getName());
	private TransactionalPersistenceAdvice _transactionalPersistenceAdvice = (TransactionalPersistenceAdvice)PortalBeanLocatorUtil.locate(TransactionalPersistenceAdvice.class.getName());
}