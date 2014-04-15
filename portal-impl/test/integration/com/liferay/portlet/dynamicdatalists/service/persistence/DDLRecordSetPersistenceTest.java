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

import com.liferay.portlet.dynamicdatalists.NoSuchRecordSetException;
import com.liferay.portlet.dynamicdatalists.model.DDLRecordSet;
import com.liferay.portlet.dynamicdatalists.model.impl.DDLRecordSetModelImpl;

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
public class DDLRecordSetPersistenceTest {
	@Before
	public void setUp() {
		_modelListeners = _persistence.getListeners();

		for (ModelListener<DDLRecordSet> modelListener : _modelListeners) {
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

		for (ModelListener<DDLRecordSet> modelListener : _modelListeners) {
			_persistence.registerListener(modelListener);
		}
	}

	@Test
	public void testCreate() throws Exception {
		long pk = ServiceTestUtil.nextLong();

		DDLRecordSet ddlRecordSet = _persistence.create(pk);

		Assert.assertNotNull(ddlRecordSet);

		Assert.assertEquals(ddlRecordSet.getPrimaryKey(), pk);
	}

	@Test
	public void testRemove() throws Exception {
		DDLRecordSet newDDLRecordSet = addDDLRecordSet();

		_persistence.remove(newDDLRecordSet);

		DDLRecordSet existingDDLRecordSet = _persistence.fetchByPrimaryKey(newDDLRecordSet.getPrimaryKey());

		Assert.assertNull(existingDDLRecordSet);
	}

	@Test
	public void testUpdateNew() throws Exception {
		addDDLRecordSet();
	}

	@Test
	public void testUpdateExisting() throws Exception {
		long pk = ServiceTestUtil.nextLong();

		DDLRecordSet newDDLRecordSet = _persistence.create(pk);

		newDDLRecordSet.setUuid(ServiceTestUtil.randomString());

		newDDLRecordSet.setGroupId(ServiceTestUtil.nextLong());

		newDDLRecordSet.setCompanyId(ServiceTestUtil.nextLong());

		newDDLRecordSet.setUserId(ServiceTestUtil.nextLong());

		newDDLRecordSet.setUserName(ServiceTestUtil.randomString());

		newDDLRecordSet.setCreateDate(ServiceTestUtil.nextDate());

		newDDLRecordSet.setModifiedDate(ServiceTestUtil.nextDate());

		newDDLRecordSet.setDDMStructureId(ServiceTestUtil.nextLong());

		newDDLRecordSet.setRecordSetKey(ServiceTestUtil.randomString());

		newDDLRecordSet.setName(ServiceTestUtil.randomString());

		newDDLRecordSet.setDescription(ServiceTestUtil.randomString());

		newDDLRecordSet.setMinDisplayRows(ServiceTestUtil.nextInt());

		newDDLRecordSet.setScope(ServiceTestUtil.nextInt());

		_persistence.update(newDDLRecordSet);

		DDLRecordSet existingDDLRecordSet = _persistence.findByPrimaryKey(newDDLRecordSet.getPrimaryKey());

		Assert.assertEquals(existingDDLRecordSet.getUuid(),
			newDDLRecordSet.getUuid());
		Assert.assertEquals(existingDDLRecordSet.getRecordSetId(),
			newDDLRecordSet.getRecordSetId());
		Assert.assertEquals(existingDDLRecordSet.getGroupId(),
			newDDLRecordSet.getGroupId());
		Assert.assertEquals(existingDDLRecordSet.getCompanyId(),
			newDDLRecordSet.getCompanyId());
		Assert.assertEquals(existingDDLRecordSet.getUserId(),
			newDDLRecordSet.getUserId());
		Assert.assertEquals(existingDDLRecordSet.getUserName(),
			newDDLRecordSet.getUserName());
		Assert.assertEquals(Time.getShortTimestamp(
				existingDDLRecordSet.getCreateDate()),
			Time.getShortTimestamp(newDDLRecordSet.getCreateDate()));
		Assert.assertEquals(Time.getShortTimestamp(
				existingDDLRecordSet.getModifiedDate()),
			Time.getShortTimestamp(newDDLRecordSet.getModifiedDate()));
		Assert.assertEquals(existingDDLRecordSet.getDDMStructureId(),
			newDDLRecordSet.getDDMStructureId());
		Assert.assertEquals(existingDDLRecordSet.getRecordSetKey(),
			newDDLRecordSet.getRecordSetKey());
		Assert.assertEquals(existingDDLRecordSet.getName(),
			newDDLRecordSet.getName());
		Assert.assertEquals(existingDDLRecordSet.getDescription(),
			newDDLRecordSet.getDescription());
		Assert.assertEquals(existingDDLRecordSet.getMinDisplayRows(),
			newDDLRecordSet.getMinDisplayRows());
		Assert.assertEquals(existingDDLRecordSet.getScope(),
			newDDLRecordSet.getScope());
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
	public void testCountByG_R() {
		try {
			_persistence.countByG_R(ServiceTestUtil.nextLong(), StringPool.BLANK);

			_persistence.countByG_R(0L, StringPool.NULL);

			_persistence.countByG_R(0L, (String)null);
		}
		catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void testFindByPrimaryKeyExisting() throws Exception {
		DDLRecordSet newDDLRecordSet = addDDLRecordSet();

		DDLRecordSet existingDDLRecordSet = _persistence.findByPrimaryKey(newDDLRecordSet.getPrimaryKey());

		Assert.assertEquals(existingDDLRecordSet, newDDLRecordSet);
	}

	@Test
	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = ServiceTestUtil.nextLong();

		try {
			_persistence.findByPrimaryKey(pk);

			Assert.fail("Missing entity did not throw NoSuchRecordSetException");
		}
		catch (NoSuchRecordSetException nsee) {
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
		return OrderByComparatorFactoryUtil.create("DDLRecordSet", "uuid",
			true, "recordSetId", true, "groupId", true, "companyId", true,
			"userId", true, "userName", true, "createDate", true,
			"modifiedDate", true, "DDMStructureId", true, "recordSetKey", true,
			"name", true, "description", true, "minDisplayRows", true, "scope",
			true);
	}

	@Test
	public void testFetchByPrimaryKeyExisting() throws Exception {
		DDLRecordSet newDDLRecordSet = addDDLRecordSet();

		DDLRecordSet existingDDLRecordSet = _persistence.fetchByPrimaryKey(newDDLRecordSet.getPrimaryKey());

		Assert.assertEquals(existingDDLRecordSet, newDDLRecordSet);
	}

	@Test
	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = ServiceTestUtil.nextLong();

		DDLRecordSet missingDDLRecordSet = _persistence.fetchByPrimaryKey(pk);

		Assert.assertNull(missingDDLRecordSet);
	}

	@Test
	public void testActionableDynamicQuery() throws Exception {
		final IntegerWrapper count = new IntegerWrapper();

		ActionableDynamicQuery actionableDynamicQuery = new DDLRecordSetActionableDynamicQuery() {
				@Override
				protected void performAction(Object object) {
					DDLRecordSet ddlRecordSet = (DDLRecordSet)object;

					Assert.assertNotNull(ddlRecordSet);

					count.increment();
				}
			};

		actionableDynamicQuery.performActions();

		Assert.assertEquals(count.getValue(), _persistence.countAll());
	}

	@Test
	public void testDynamicQueryByPrimaryKeyExisting()
		throws Exception {
		DDLRecordSet newDDLRecordSet = addDDLRecordSet();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(DDLRecordSet.class,
				DDLRecordSet.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("recordSetId",
				newDDLRecordSet.getRecordSetId()));

		List<DDLRecordSet> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		DDLRecordSet existingDDLRecordSet = result.get(0);

		Assert.assertEquals(existingDDLRecordSet, newDDLRecordSet);
	}

	@Test
	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(DDLRecordSet.class,
				DDLRecordSet.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("recordSetId",
				ServiceTestUtil.nextLong()));

		List<DDLRecordSet> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testDynamicQueryByProjectionExisting()
		throws Exception {
		DDLRecordSet newDDLRecordSet = addDDLRecordSet();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(DDLRecordSet.class,
				DDLRecordSet.class.getClassLoader());

		dynamicQuery.setProjection(ProjectionFactoryUtil.property("recordSetId"));

		Object newRecordSetId = newDDLRecordSet.getRecordSetId();

		dynamicQuery.add(RestrictionsFactoryUtil.in("recordSetId",
				new Object[] { newRecordSetId }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		Object existingRecordSetId = result.get(0);

		Assert.assertEquals(existingRecordSetId, newRecordSetId);
	}

	@Test
	public void testDynamicQueryByProjectionMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(DDLRecordSet.class,
				DDLRecordSet.class.getClassLoader());

		dynamicQuery.setProjection(ProjectionFactoryUtil.property("recordSetId"));

		dynamicQuery.add(RestrictionsFactoryUtil.in("recordSetId",
				new Object[] { ServiceTestUtil.nextLong() }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testResetOriginalValues() throws Exception {
		if (!PropsValues.HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE) {
			return;
		}

		DDLRecordSet newDDLRecordSet = addDDLRecordSet();

		_persistence.clearCache();

		DDLRecordSetModelImpl existingDDLRecordSetModelImpl = (DDLRecordSetModelImpl)_persistence.findByPrimaryKey(newDDLRecordSet.getPrimaryKey());

		Assert.assertTrue(Validator.equals(
				existingDDLRecordSetModelImpl.getUuid(),
				existingDDLRecordSetModelImpl.getOriginalUuid()));
		Assert.assertEquals(existingDDLRecordSetModelImpl.getGroupId(),
			existingDDLRecordSetModelImpl.getOriginalGroupId());

		Assert.assertEquals(existingDDLRecordSetModelImpl.getGroupId(),
			existingDDLRecordSetModelImpl.getOriginalGroupId());
		Assert.assertTrue(Validator.equals(
				existingDDLRecordSetModelImpl.getRecordSetKey(),
				existingDDLRecordSetModelImpl.getOriginalRecordSetKey()));
	}

	protected DDLRecordSet addDDLRecordSet() throws Exception {
		long pk = ServiceTestUtil.nextLong();

		DDLRecordSet ddlRecordSet = _persistence.create(pk);

		ddlRecordSet.setUuid(ServiceTestUtil.randomString());

		ddlRecordSet.setGroupId(ServiceTestUtil.nextLong());

		ddlRecordSet.setCompanyId(ServiceTestUtil.nextLong());

		ddlRecordSet.setUserId(ServiceTestUtil.nextLong());

		ddlRecordSet.setUserName(ServiceTestUtil.randomString());

		ddlRecordSet.setCreateDate(ServiceTestUtil.nextDate());

		ddlRecordSet.setModifiedDate(ServiceTestUtil.nextDate());

		ddlRecordSet.setDDMStructureId(ServiceTestUtil.nextLong());

		ddlRecordSet.setRecordSetKey(ServiceTestUtil.randomString());

		ddlRecordSet.setName(ServiceTestUtil.randomString());

		ddlRecordSet.setDescription(ServiceTestUtil.randomString());

		ddlRecordSet.setMinDisplayRows(ServiceTestUtil.nextInt());

		ddlRecordSet.setScope(ServiceTestUtil.nextInt());

		_persistence.update(ddlRecordSet);

		return ddlRecordSet;
	}

	private static Log _log = LogFactoryUtil.getLog(DDLRecordSetPersistenceTest.class);
	private ModelListener<DDLRecordSet>[] _modelListeners;
	private DDLRecordSetPersistence _persistence = (DDLRecordSetPersistence)PortalBeanLocatorUtil.locate(DDLRecordSetPersistence.class.getName());
	private TransactionalPersistenceAdvice _transactionalPersistenceAdvice = (TransactionalPersistenceAdvice)PortalBeanLocatorUtil.locate(TransactionalPersistenceAdvice.class.getName());
}