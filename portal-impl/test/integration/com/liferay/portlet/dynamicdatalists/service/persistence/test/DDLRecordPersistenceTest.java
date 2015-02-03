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

package com.liferay.portlet.dynamicdatalists.service.persistence.test;

import com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQueryFactoryUtil;
import com.liferay.portal.kernel.dao.orm.ProjectionFactoryUtil;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.RestrictionsFactoryUtil;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.TransactionalTestRule;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.transaction.Propagation;
import com.liferay.portal.kernel.util.IntegerWrapper;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.OrderByComparatorFactoryUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Time;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PersistenceTestRule;
import com.liferay.portal.util.PropsValues;

import com.liferay.portlet.dynamicdatalists.NoSuchRecordException;
import com.liferay.portlet.dynamicdatalists.model.DDLRecord;
import com.liferay.portlet.dynamicdatalists.service.DDLRecordLocalServiceUtil;
import com.liferay.portlet.dynamicdatalists.service.persistence.DDLRecordPersistence;
import com.liferay.portlet.dynamicdatalists.service.persistence.DDLRecordUtil;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @generated
 */
public class DDLRecordPersistenceTest {
	@Rule
	public final AggregateTestRule aggregateTestRule = new AggregateTestRule(new LiferayIntegrationTestRule(),
			PersistenceTestRule.INSTANCE,
			new TransactionalTestRule(Propagation.REQUIRED));

	@Before
	public void setUp() {
		_persistence = DDLRecordUtil.getPersistence();

		Class<?> clazz = _persistence.getClass();

		_dynamicQueryClassLoader = clazz.getClassLoader();
	}

	@After
	public void tearDown() throws Exception {
		Iterator<DDLRecord> iterator = _ddlRecords.iterator();

		while (iterator.hasNext()) {
			_persistence.remove(iterator.next());

			iterator.remove();
		}
	}

	@Test
	public void testCreate() throws Exception {
		long pk = RandomTestUtil.nextLong();

		DDLRecord ddlRecord = _persistence.create(pk);

		Assert.assertNotNull(ddlRecord);

		Assert.assertEquals(ddlRecord.getPrimaryKey(), pk);
	}

	@Test
	public void testRemove() throws Exception {
		DDLRecord newDDLRecord = addDDLRecord();

		_persistence.remove(newDDLRecord);

		DDLRecord existingDDLRecord = _persistence.fetchByPrimaryKey(newDDLRecord.getPrimaryKey());

		Assert.assertNull(existingDDLRecord);
	}

	@Test
	public void testUpdateNew() throws Exception {
		addDDLRecord();
	}

	@Test
	public void testUpdateExisting() throws Exception {
		long pk = RandomTestUtil.nextLong();

		DDLRecord newDDLRecord = _persistence.create(pk);

		newDDLRecord.setUuid(RandomTestUtil.randomString());

		newDDLRecord.setGroupId(RandomTestUtil.nextLong());

		newDDLRecord.setCompanyId(RandomTestUtil.nextLong());

		newDDLRecord.setUserId(RandomTestUtil.nextLong());

		newDDLRecord.setUserName(RandomTestUtil.randomString());

		newDDLRecord.setVersionUserId(RandomTestUtil.nextLong());

		newDDLRecord.setVersionUserName(RandomTestUtil.randomString());

		newDDLRecord.setCreateDate(RandomTestUtil.nextDate());

		newDDLRecord.setModifiedDate(RandomTestUtil.nextDate());

		newDDLRecord.setDDMStorageId(RandomTestUtil.nextLong());

		newDDLRecord.setRecordSetId(RandomTestUtil.nextLong());

		newDDLRecord.setVersion(RandomTestUtil.randomString());

		newDDLRecord.setDisplayIndex(RandomTestUtil.nextInt());

		_ddlRecords.add(_persistence.update(newDDLRecord));

		DDLRecord existingDDLRecord = _persistence.findByPrimaryKey(newDDLRecord.getPrimaryKey());

		Assert.assertEquals(existingDDLRecord.getUuid(), newDDLRecord.getUuid());
		Assert.assertEquals(existingDDLRecord.getRecordId(),
			newDDLRecord.getRecordId());
		Assert.assertEquals(existingDDLRecord.getGroupId(),
			newDDLRecord.getGroupId());
		Assert.assertEquals(existingDDLRecord.getCompanyId(),
			newDDLRecord.getCompanyId());
		Assert.assertEquals(existingDDLRecord.getUserId(),
			newDDLRecord.getUserId());
		Assert.assertEquals(existingDDLRecord.getUserName(),
			newDDLRecord.getUserName());
		Assert.assertEquals(existingDDLRecord.getVersionUserId(),
			newDDLRecord.getVersionUserId());
		Assert.assertEquals(existingDDLRecord.getVersionUserName(),
			newDDLRecord.getVersionUserName());
		Assert.assertEquals(Time.getShortTimestamp(
				existingDDLRecord.getCreateDate()),
			Time.getShortTimestamp(newDDLRecord.getCreateDate()));
		Assert.assertEquals(Time.getShortTimestamp(
				existingDDLRecord.getModifiedDate()),
			Time.getShortTimestamp(newDDLRecord.getModifiedDate()));
		Assert.assertEquals(existingDDLRecord.getDDMStorageId(),
			newDDLRecord.getDDMStorageId());
		Assert.assertEquals(existingDDLRecord.getRecordSetId(),
			newDDLRecord.getRecordSetId());
		Assert.assertEquals(existingDDLRecord.getVersion(),
			newDDLRecord.getVersion());
		Assert.assertEquals(existingDDLRecord.getDisplayIndex(),
			newDDLRecord.getDisplayIndex());
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
	public void testCountByCompanyId() {
		try {
			_persistence.countByCompanyId(RandomTestUtil.nextLong());

			_persistence.countByCompanyId(0L);
		}
		catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void testCountByRecordSetId() {
		try {
			_persistence.countByRecordSetId(RandomTestUtil.nextLong());

			_persistence.countByRecordSetId(0L);
		}
		catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void testCountByR_U() {
		try {
			_persistence.countByR_U(RandomTestUtil.nextLong(),
				RandomTestUtil.nextLong());

			_persistence.countByR_U(0L, 0L);
		}
		catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void testFindByPrimaryKeyExisting() throws Exception {
		DDLRecord newDDLRecord = addDDLRecord();

		DDLRecord existingDDLRecord = _persistence.findByPrimaryKey(newDDLRecord.getPrimaryKey());

		Assert.assertEquals(existingDDLRecord, newDDLRecord);
	}

	@Test
	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		try {
			_persistence.findByPrimaryKey(pk);

			Assert.fail("Missing entity did not throw NoSuchRecordException");
		}
		catch (NoSuchRecordException nsee) {
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

	protected OrderByComparator<DDLRecord> getOrderByComparator() {
		return OrderByComparatorFactoryUtil.create("DDLRecord", "uuid", true,
			"recordId", true, "groupId", true, "companyId", true, "userId",
			true, "userName", true, "versionUserId", true, "versionUserName",
			true, "createDate", true, "modifiedDate", true, "DDMStorageId",
			true, "recordSetId", true, "version", true, "displayIndex", true);
	}

	@Test
	public void testFetchByPrimaryKeyExisting() throws Exception {
		DDLRecord newDDLRecord = addDDLRecord();

		DDLRecord existingDDLRecord = _persistence.fetchByPrimaryKey(newDDLRecord.getPrimaryKey());

		Assert.assertEquals(existingDDLRecord, newDDLRecord);
	}

	@Test
	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		DDLRecord missingDDLRecord = _persistence.fetchByPrimaryKey(pk);

		Assert.assertNull(missingDDLRecord);
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereAllPrimaryKeysExist()
		throws Exception {
		DDLRecord newDDLRecord1 = addDDLRecord();
		DDLRecord newDDLRecord2 = addDDLRecord();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newDDLRecord1.getPrimaryKey());
		primaryKeys.add(newDDLRecord2.getPrimaryKey());

		Map<Serializable, DDLRecord> ddlRecords = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(2, ddlRecords.size());
		Assert.assertEquals(newDDLRecord1,
			ddlRecords.get(newDDLRecord1.getPrimaryKey()));
		Assert.assertEquals(newDDLRecord2,
			ddlRecords.get(newDDLRecord2.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereNoPrimaryKeysExist()
		throws Exception {
		long pk1 = RandomTestUtil.nextLong();

		long pk2 = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(pk1);
		primaryKeys.add(pk2);

		Map<Serializable, DDLRecord> ddlRecords = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(ddlRecords.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereSomePrimaryKeysExist()
		throws Exception {
		DDLRecord newDDLRecord = addDDLRecord();

		long pk = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newDDLRecord.getPrimaryKey());
		primaryKeys.add(pk);

		Map<Serializable, DDLRecord> ddlRecords = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, ddlRecords.size());
		Assert.assertEquals(newDDLRecord,
			ddlRecords.get(newDDLRecord.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithNoPrimaryKeys()
		throws Exception {
		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		Map<Serializable, DDLRecord> ddlRecords = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(ddlRecords.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithOnePrimaryKey()
		throws Exception {
		DDLRecord newDDLRecord = addDDLRecord();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newDDLRecord.getPrimaryKey());

		Map<Serializable, DDLRecord> ddlRecords = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, ddlRecords.size());
		Assert.assertEquals(newDDLRecord,
			ddlRecords.get(newDDLRecord.getPrimaryKey()));
	}

	@Test
	public void testActionableDynamicQuery() throws Exception {
		final IntegerWrapper count = new IntegerWrapper();

		ActionableDynamicQuery actionableDynamicQuery = DDLRecordLocalServiceUtil.getActionableDynamicQuery();

		actionableDynamicQuery.setPerformActionMethod(new ActionableDynamicQuery.PerformActionMethod() {
				@Override
				public void performAction(Object object) {
					DDLRecord ddlRecord = (DDLRecord)object;

					Assert.assertNotNull(ddlRecord);

					count.increment();
				}
			});

		actionableDynamicQuery.performActions();

		Assert.assertEquals(count.getValue(), _persistence.countAll());
	}

	@Test
	public void testDynamicQueryByPrimaryKeyExisting()
		throws Exception {
		DDLRecord newDDLRecord = addDDLRecord();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(DDLRecord.class,
				_dynamicQueryClassLoader);

		dynamicQuery.add(RestrictionsFactoryUtil.eq("recordId",
				newDDLRecord.getRecordId()));

		List<DDLRecord> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		DDLRecord existingDDLRecord = result.get(0);

		Assert.assertEquals(existingDDLRecord, newDDLRecord);
	}

	@Test
	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(DDLRecord.class,
				_dynamicQueryClassLoader);

		dynamicQuery.add(RestrictionsFactoryUtil.eq("recordId",
				RandomTestUtil.nextLong()));

		List<DDLRecord> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testDynamicQueryByProjectionExisting()
		throws Exception {
		DDLRecord newDDLRecord = addDDLRecord();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(DDLRecord.class,
				_dynamicQueryClassLoader);

		dynamicQuery.setProjection(ProjectionFactoryUtil.property("recordId"));

		Object newRecordId = newDDLRecord.getRecordId();

		dynamicQuery.add(RestrictionsFactoryUtil.in("recordId",
				new Object[] { newRecordId }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		Object existingRecordId = result.get(0);

		Assert.assertEquals(existingRecordId, newRecordId);
	}

	@Test
	public void testDynamicQueryByProjectionMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(DDLRecord.class,
				_dynamicQueryClassLoader);

		dynamicQuery.setProjection(ProjectionFactoryUtil.property("recordId"));

		dynamicQuery.add(RestrictionsFactoryUtil.in("recordId",
				new Object[] { RandomTestUtil.nextLong() }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testResetOriginalValues() throws Exception {
		if (!PropsValues.HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE) {
			return;
		}

		DDLRecord newDDLRecord = addDDLRecord();

		_persistence.clearCache();

		DDLRecord existingDDLRecord = _persistence.findByPrimaryKey(newDDLRecord.getPrimaryKey());

		Assert.assertTrue(Validator.equals(existingDDLRecord.getUuid(),
				ReflectionTestUtil.invoke(existingDDLRecord, "getOriginalUuid",
					new Class<?>[0])));
		Assert.assertEquals(existingDDLRecord.getGroupId(),
			ReflectionTestUtil.invoke(existingDDLRecord, "getOriginalGroupId",
				new Class<?>[0]));
	}

	protected DDLRecord addDDLRecord() throws Exception {
		long pk = RandomTestUtil.nextLong();

		DDLRecord ddlRecord = _persistence.create(pk);

		ddlRecord.setUuid(RandomTestUtil.randomString());

		ddlRecord.setGroupId(RandomTestUtil.nextLong());

		ddlRecord.setCompanyId(RandomTestUtil.nextLong());

		ddlRecord.setUserId(RandomTestUtil.nextLong());

		ddlRecord.setUserName(RandomTestUtil.randomString());

		ddlRecord.setVersionUserId(RandomTestUtil.nextLong());

		ddlRecord.setVersionUserName(RandomTestUtil.randomString());

		ddlRecord.setCreateDate(RandomTestUtil.nextDate());

		ddlRecord.setModifiedDate(RandomTestUtil.nextDate());

		ddlRecord.setDDMStorageId(RandomTestUtil.nextLong());

		ddlRecord.setRecordSetId(RandomTestUtil.nextLong());

		ddlRecord.setVersion(RandomTestUtil.randomString());

		ddlRecord.setDisplayIndex(RandomTestUtil.nextInt());

		_ddlRecords.add(_persistence.update(ddlRecord));

		return ddlRecord;
	}

	private List<DDLRecord> _ddlRecords = new ArrayList<DDLRecord>();
	private DDLRecordPersistence _persistence;
	private ClassLoader _dynamicQueryClassLoader;
}