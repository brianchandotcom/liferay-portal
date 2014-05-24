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

package com.liferay.portlet.dynamicdatalists.service.persistence;

import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQueryFactoryUtil;
import com.liferay.portal.kernel.dao.orm.ProjectionFactoryUtil;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.RestrictionsFactoryUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.test.ExecutionTestListeners;
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

import com.liferay.portlet.dynamicdatalists.NoSuchRecordVersionException;
import com.liferay.portlet.dynamicdatalists.model.DDLRecordVersion;
import com.liferay.portlet.dynamicdatalists.model.impl.DDLRecordVersionModelImpl;

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
public class DDLRecordVersionPersistenceTest {
	@Before
	public void setUp() {
		_modelListeners = _persistence.getListeners();

		for (ModelListener<DDLRecordVersion> modelListener : _modelListeners) {
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

		for (ModelListener<DDLRecordVersion> modelListener : _modelListeners) {
			_persistence.registerListener(modelListener);
		}
	}

	@Test
	public void testCreate() throws Exception {
		long pk = RandomTestUtil.nextLong();

		DDLRecordVersion ddlRecordVersion = _persistence.create(pk);

		Assert.assertNotNull(ddlRecordVersion);

		Assert.assertEquals(ddlRecordVersion.getPrimaryKey(), pk);
	}

	@Test
	public void testRemove() throws Exception {
		DDLRecordVersion newDDLRecordVersion = addDDLRecordVersion();

		_persistence.remove(newDDLRecordVersion);

		DDLRecordVersion existingDDLRecordVersion = _persistence.fetchByPrimaryKey(newDDLRecordVersion.getPrimaryKey());

		Assert.assertNull(existingDDLRecordVersion);
	}

	@Test
	public void testUpdateNew() throws Exception {
		addDDLRecordVersion();
	}

	@Test
	public void testUpdateExisting() throws Exception {
		long pk = RandomTestUtil.nextLong();

		DDLRecordVersion newDDLRecordVersion = _persistence.create(pk);

		newDDLRecordVersion.setGroupId(RandomTestUtil.nextLong());

		newDDLRecordVersion.setCompanyId(RandomTestUtil.nextLong());

		newDDLRecordVersion.setUserId(RandomTestUtil.nextLong());

		newDDLRecordVersion.setUserName(RandomTestUtil.randomString());

		newDDLRecordVersion.setCreateDate(RandomTestUtil.nextDate());

		newDDLRecordVersion.setDDMStorageId(RandomTestUtil.nextLong());

		newDDLRecordVersion.setRecordSetId(RandomTestUtil.nextLong());

		newDDLRecordVersion.setRecordId(RandomTestUtil.nextLong());

		newDDLRecordVersion.setVersion(RandomTestUtil.randomString());

		newDDLRecordVersion.setDisplayIndex(RandomTestUtil.nextInt());

		newDDLRecordVersion.setStatus(RandomTestUtil.nextInt());

		newDDLRecordVersion.setStatusByUserId(RandomTestUtil.nextLong());

		newDDLRecordVersion.setStatusByUserName(RandomTestUtil.randomString());

		newDDLRecordVersion.setStatusDate(RandomTestUtil.nextDate());

		_persistence.update(newDDLRecordVersion);

		DDLRecordVersion existingDDLRecordVersion = _persistence.findByPrimaryKey(newDDLRecordVersion.getPrimaryKey());

		Assert.assertEquals(existingDDLRecordVersion.getRecordVersionId(),
			newDDLRecordVersion.getRecordVersionId());
		Assert.assertEquals(existingDDLRecordVersion.getGroupId(),
			newDDLRecordVersion.getGroupId());
		Assert.assertEquals(existingDDLRecordVersion.getCompanyId(),
			newDDLRecordVersion.getCompanyId());
		Assert.assertEquals(existingDDLRecordVersion.getUserId(),
			newDDLRecordVersion.getUserId());
		Assert.assertEquals(existingDDLRecordVersion.getUserName(),
			newDDLRecordVersion.getUserName());
		Assert.assertEquals(Time.getShortTimestamp(
				existingDDLRecordVersion.getCreateDate()),
			Time.getShortTimestamp(newDDLRecordVersion.getCreateDate()));
		Assert.assertEquals(existingDDLRecordVersion.getDDMStorageId(),
			newDDLRecordVersion.getDDMStorageId());
		Assert.assertEquals(existingDDLRecordVersion.getRecordSetId(),
			newDDLRecordVersion.getRecordSetId());
		Assert.assertEquals(existingDDLRecordVersion.getRecordId(),
			newDDLRecordVersion.getRecordId());
		Assert.assertEquals(existingDDLRecordVersion.getVersion(),
			newDDLRecordVersion.getVersion());
		Assert.assertEquals(existingDDLRecordVersion.getDisplayIndex(),
			newDDLRecordVersion.getDisplayIndex());
		Assert.assertEquals(existingDDLRecordVersion.getStatus(),
			newDDLRecordVersion.getStatus());
		Assert.assertEquals(existingDDLRecordVersion.getStatusByUserId(),
			newDDLRecordVersion.getStatusByUserId());
		Assert.assertEquals(existingDDLRecordVersion.getStatusByUserName(),
			newDDLRecordVersion.getStatusByUserName());
		Assert.assertEquals(Time.getShortTimestamp(
				existingDDLRecordVersion.getStatusDate()),
			Time.getShortTimestamp(newDDLRecordVersion.getStatusDate()));
	}

	@Test
	public void testCountByRecordId() {
		try {
			_persistence.countByRecordId(RandomTestUtil.nextLong());

			_persistence.countByRecordId(0L);
		}
		catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void testCountByR_V() {
		try {
			_persistence.countByR_V(RandomTestUtil.nextLong(), StringPool.BLANK);

			_persistence.countByR_V(0L, StringPool.NULL);

			_persistence.countByR_V(0L, (String)null);
		}
		catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void testCountByR_S() {
		try {
			_persistence.countByR_S(RandomTestUtil.nextLong(),
				RandomTestUtil.nextInt());

			_persistence.countByR_S(0L, 0);
		}
		catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void testFindByPrimaryKeyExisting() throws Exception {
		DDLRecordVersion newDDLRecordVersion = addDDLRecordVersion();

		DDLRecordVersion existingDDLRecordVersion = _persistence.findByPrimaryKey(newDDLRecordVersion.getPrimaryKey());

		Assert.assertEquals(existingDDLRecordVersion, newDDLRecordVersion);
	}

	@Test
	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		try {
			_persistence.findByPrimaryKey(pk);

			Assert.fail(
				"Missing entity did not throw NoSuchRecordVersionException");
		}
		catch (NoSuchRecordVersionException nsee) {
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
		return OrderByComparatorFactoryUtil.create("DDLRecordVersion",
			"recordVersionId", true, "groupId", true, "companyId", true,
			"userId", true, "userName", true, "createDate", true,
			"DDMStorageId", true, "recordSetId", true, "recordId", true,
			"version", true, "displayIndex", true, "status", true,
			"statusByUserId", true, "statusByUserName", true, "statusDate", true);
	}

	@Test
	public void testFetchByPrimaryKeyExisting() throws Exception {
		DDLRecordVersion newDDLRecordVersion = addDDLRecordVersion();

		DDLRecordVersion existingDDLRecordVersion = _persistence.fetchByPrimaryKey(newDDLRecordVersion.getPrimaryKey());

		Assert.assertEquals(existingDDLRecordVersion, newDDLRecordVersion);
	}

	@Test
	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		DDLRecordVersion missingDDLRecordVersion = _persistence.fetchByPrimaryKey(pk);

		Assert.assertNull(missingDDLRecordVersion);
	}

	@Test
	public void testDynamicQueryByPrimaryKeyExisting()
		throws Exception {
		DDLRecordVersion newDDLRecordVersion = addDDLRecordVersion();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(DDLRecordVersion.class,
				DDLRecordVersion.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("recordVersionId",
				newDDLRecordVersion.getRecordVersionId()));

		List<DDLRecordVersion> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		DDLRecordVersion existingDDLRecordVersion = result.get(0);

		Assert.assertEquals(existingDDLRecordVersion, newDDLRecordVersion);
	}

	@Test
	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(DDLRecordVersion.class,
				DDLRecordVersion.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("recordVersionId",
				RandomTestUtil.nextLong()));

		List<DDLRecordVersion> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testDynamicQueryByProjectionExisting()
		throws Exception {
		DDLRecordVersion newDDLRecordVersion = addDDLRecordVersion();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(DDLRecordVersion.class,
				DDLRecordVersion.class.getClassLoader());

		dynamicQuery.setProjection(ProjectionFactoryUtil.property(
				"recordVersionId"));

		Object newRecordVersionId = newDDLRecordVersion.getRecordVersionId();

		dynamicQuery.add(RestrictionsFactoryUtil.in("recordVersionId",
				new Object[] { newRecordVersionId }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		Object existingRecordVersionId = result.get(0);

		Assert.assertEquals(existingRecordVersionId, newRecordVersionId);
	}

	@Test
	public void testDynamicQueryByProjectionMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(DDLRecordVersion.class,
				DDLRecordVersion.class.getClassLoader());

		dynamicQuery.setProjection(ProjectionFactoryUtil.property(
				"recordVersionId"));

		dynamicQuery.add(RestrictionsFactoryUtil.in("recordVersionId",
				new Object[] { RandomTestUtil.nextLong() }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testResetOriginalValues() throws Exception {
		if (!PropsValues.HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE) {
			return;
		}

		DDLRecordVersion newDDLRecordVersion = addDDLRecordVersion();

		_persistence.clearCache();

		DDLRecordVersionModelImpl existingDDLRecordVersionModelImpl = (DDLRecordVersionModelImpl)_persistence.findByPrimaryKey(newDDLRecordVersion.getPrimaryKey());

		Assert.assertEquals(existingDDLRecordVersionModelImpl.getRecordId(),
			existingDDLRecordVersionModelImpl.getOriginalRecordId());
		Assert.assertTrue(Validator.equals(
				existingDDLRecordVersionModelImpl.getVersion(),
				existingDDLRecordVersionModelImpl.getOriginalVersion()));
	}

	protected DDLRecordVersion addDDLRecordVersion() throws Exception {
		long pk = RandomTestUtil.nextLong();

		DDLRecordVersion ddlRecordVersion = _persistence.create(pk);

		ddlRecordVersion.setGroupId(RandomTestUtil.nextLong());

		ddlRecordVersion.setCompanyId(RandomTestUtil.nextLong());

		ddlRecordVersion.setUserId(RandomTestUtil.nextLong());

		ddlRecordVersion.setUserName(RandomTestUtil.randomString());

		ddlRecordVersion.setCreateDate(RandomTestUtil.nextDate());

		ddlRecordVersion.setDDMStorageId(RandomTestUtil.nextLong());

		ddlRecordVersion.setRecordSetId(RandomTestUtil.nextLong());

		ddlRecordVersion.setRecordId(RandomTestUtil.nextLong());

		ddlRecordVersion.setVersion(RandomTestUtil.randomString());

		ddlRecordVersion.setDisplayIndex(RandomTestUtil.nextInt());

		ddlRecordVersion.setStatus(RandomTestUtil.nextInt());

		ddlRecordVersion.setStatusByUserId(RandomTestUtil.nextLong());

		ddlRecordVersion.setStatusByUserName(RandomTestUtil.randomString());

		ddlRecordVersion.setStatusDate(RandomTestUtil.nextDate());

		_persistence.update(ddlRecordVersion);

		return ddlRecordVersion;
	}

	private static Log _log = LogFactoryUtil.getLog(DDLRecordVersionPersistenceTest.class);
	private ModelListener<DDLRecordVersion>[] _modelListeners;
	private DDLRecordVersionPersistence _persistence = (DDLRecordVersionPersistence)PortalBeanLocatorUtil.locate(DDLRecordVersionPersistence.class.getName());
	private TransactionalPersistenceAdvice _transactionalPersistenceAdvice = (TransactionalPersistenceAdvice)PortalBeanLocatorUtil.locate(TransactionalPersistenceAdvice.class.getName());
}