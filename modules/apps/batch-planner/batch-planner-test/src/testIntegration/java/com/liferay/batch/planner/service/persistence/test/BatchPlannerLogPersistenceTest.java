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

package com.liferay.batch.planner.service.persistence.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.batch.planner.exception.NoSuchLogException;
import com.liferay.batch.planner.model.BatchPlannerLog;
import com.liferay.batch.planner.service.BatchPlannerLogLocalServiceUtil;
import com.liferay.batch.planner.service.persistence.BatchPlannerLogPersistence;
import com.liferay.batch.planner.service.persistence.BatchPlannerLogUtil;
import com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQueryFactoryUtil;
import com.liferay.portal.kernel.dao.orm.ProjectionFactoryUtil;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.RestrictionsFactoryUtil;
import com.liferay.portal.kernel.dao.orm.Session;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.transaction.Propagation;
import com.liferay.portal.kernel.util.IntegerWrapper;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.OrderByComparatorFactoryUtil;
import com.liferay.portal.kernel.util.Time;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PersistenceTestRule;
import com.liferay.portal.test.rule.TransactionalTestRule;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @generated
 */
@RunWith(Arquillian.class)
public class BatchPlannerLogPersistenceTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(), PersistenceTestRule.INSTANCE,
			new TransactionalTestRule(
				Propagation.REQUIRED, "com.liferay.batch.planner.service"));

	@Before
	public void setUp() {
		_persistence = BatchPlannerLogUtil.getPersistence();

		Class<?> clazz = _persistence.getClass();

		_dynamicQueryClassLoader = clazz.getClassLoader();
	}

	@After
	public void tearDown() throws Exception {
		Iterator<BatchPlannerLog> iterator = _batchPlannerLogs.iterator();

		while (iterator.hasNext()) {
			_persistence.remove(iterator.next());

			iterator.remove();
		}
	}

	@Test
	public void testCreate() throws Exception {
		long pk = RandomTestUtil.nextLong();

		BatchPlannerLog batchPlannerLog = _persistence.create(pk);

		Assert.assertNotNull(batchPlannerLog);

		Assert.assertEquals(batchPlannerLog.getPrimaryKey(), pk);
	}

	@Test
	public void testRemove() throws Exception {
		BatchPlannerLog newBatchPlannerLog = addBatchPlannerLog();

		_persistence.remove(newBatchPlannerLog);

		BatchPlannerLog existingBatchPlannerLog =
			_persistence.fetchByPrimaryKey(newBatchPlannerLog.getPrimaryKey());

		Assert.assertNull(existingBatchPlannerLog);
	}

	@Test
	public void testUpdateNew() throws Exception {
		addBatchPlannerLog();
	}

	@Test
	public void testUpdateExisting() throws Exception {
		long pk = RandomTestUtil.nextLong();

		BatchPlannerLog newBatchPlannerLog = _persistence.create(pk);

		newBatchPlannerLog.setMvccVersion(RandomTestUtil.nextLong());

		newBatchPlannerLog.setCompanyId(RandomTestUtil.nextLong());

		newBatchPlannerLog.setUserId(RandomTestUtil.nextLong());

		newBatchPlannerLog.setUserName(RandomTestUtil.randomString());

		newBatchPlannerLog.setCreateDate(RandomTestUtil.nextDate());

		newBatchPlannerLog.setModifiedDate(RandomTestUtil.nextDate());

		newBatchPlannerLog.setBatchPlannerPlanId(RandomTestUtil.nextLong());

		newBatchPlannerLog.setBatchEngineExportTaskERC(
			RandomTestUtil.randomString());

		newBatchPlannerLog.setBatchEngineImportTaskERC(
			RandomTestUtil.randomString());

		newBatchPlannerLog.setDispatchTriggerERC(RandomTestUtil.randomString());

		newBatchPlannerLog.setSize(RandomTestUtil.nextInt());

		newBatchPlannerLog.setTotal(RandomTestUtil.nextInt());

		newBatchPlannerLog.setStatus(RandomTestUtil.nextInt());

		_batchPlannerLogs.add(_persistence.update(newBatchPlannerLog));

		BatchPlannerLog existingBatchPlannerLog = _persistence.findByPrimaryKey(
			newBatchPlannerLog.getPrimaryKey());

		Assert.assertEquals(
			existingBatchPlannerLog.getMvccVersion(),
			newBatchPlannerLog.getMvccVersion());
		Assert.assertEquals(
			existingBatchPlannerLog.getBatchPlannerLogId(),
			newBatchPlannerLog.getBatchPlannerLogId());
		Assert.assertEquals(
			existingBatchPlannerLog.getCompanyId(),
			newBatchPlannerLog.getCompanyId());
		Assert.assertEquals(
			existingBatchPlannerLog.getUserId(),
			newBatchPlannerLog.getUserId());
		Assert.assertEquals(
			existingBatchPlannerLog.getUserName(),
			newBatchPlannerLog.getUserName());
		Assert.assertEquals(
			Time.getShortTimestamp(existingBatchPlannerLog.getCreateDate()),
			Time.getShortTimestamp(newBatchPlannerLog.getCreateDate()));
		Assert.assertEquals(
			Time.getShortTimestamp(existingBatchPlannerLog.getModifiedDate()),
			Time.getShortTimestamp(newBatchPlannerLog.getModifiedDate()));
		Assert.assertEquals(
			existingBatchPlannerLog.getBatchPlannerPlanId(),
			newBatchPlannerLog.getBatchPlannerPlanId());
		Assert.assertEquals(
			existingBatchPlannerLog.getBatchEngineExportTaskERC(),
			newBatchPlannerLog.getBatchEngineExportTaskERC());
		Assert.assertEquals(
			existingBatchPlannerLog.getBatchEngineImportTaskERC(),
			newBatchPlannerLog.getBatchEngineImportTaskERC());
		Assert.assertEquals(
			existingBatchPlannerLog.getDispatchTriggerERC(),
			newBatchPlannerLog.getDispatchTriggerERC());
		Assert.assertEquals(
			existingBatchPlannerLog.getSize(), newBatchPlannerLog.getSize());
		Assert.assertEquals(
			existingBatchPlannerLog.getTotal(), newBatchPlannerLog.getTotal());
		Assert.assertEquals(
			existingBatchPlannerLog.getStatus(),
			newBatchPlannerLog.getStatus());
	}

	@Test
	public void testCountByCompanyId() throws Exception {
		_persistence.countByCompanyId(RandomTestUtil.nextLong());

		_persistence.countByCompanyId(0L);
	}

	@Test
	public void testCountByBatchPlannerPlanId() throws Exception {
		_persistence.countByBatchPlannerPlanId(RandomTestUtil.nextLong());

		_persistence.countByBatchPlannerPlanId(0L);
	}

	@Test
	public void testCountByBPPI_BEETERC() throws Exception {
		_persistence.countByBPPI_BEETERC(RandomTestUtil.nextLong(), "");

		_persistence.countByBPPI_BEETERC(0L, "null");

		_persistence.countByBPPI_BEETERC(0L, (String)null);
	}

	@Test
	public void testCountByBPPI_BEITERC() throws Exception {
		_persistence.countByBPPI_BEITERC(RandomTestUtil.nextLong(), "");

		_persistence.countByBPPI_BEITERC(0L, "null");

		_persistence.countByBPPI_BEITERC(0L, (String)null);
	}

	@Test
	public void testCountByBPPI_DTERC() throws Exception {
		_persistence.countByBPPI_DTERC(RandomTestUtil.nextLong(), "");

		_persistence.countByBPPI_DTERC(0L, "null");

		_persistence.countByBPPI_DTERC(0L, (String)null);
	}

	@Test
	public void testFindByPrimaryKeyExisting() throws Exception {
		BatchPlannerLog newBatchPlannerLog = addBatchPlannerLog();

		BatchPlannerLog existingBatchPlannerLog = _persistence.findByPrimaryKey(
			newBatchPlannerLog.getPrimaryKey());

		Assert.assertEquals(existingBatchPlannerLog, newBatchPlannerLog);
	}

	@Test(expected = NoSuchLogException.class)
	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		_persistence.findByPrimaryKey(pk);
	}

	@Test
	public void testFindAll() throws Exception {
		_persistence.findAll(
			QueryUtil.ALL_POS, QueryUtil.ALL_POS, getOrderByComparator());
	}

	protected OrderByComparator<BatchPlannerLog> getOrderByComparator() {
		return OrderByComparatorFactoryUtil.create(
			"BatchPlannerLog", "mvccVersion", true, "batchPlannerLogId", true,
			"companyId", true, "userId", true, "userName", true, "createDate",
			true, "modifiedDate", true, "batchPlannerPlanId", true,
			"batchEngineExportTaskERC", true, "batchEngineImportTaskERC", true,
			"dispatchTriggerERC", true, "size", true, "total", true, "status",
			true);
	}

	@Test
	public void testFetchByPrimaryKeyExisting() throws Exception {
		BatchPlannerLog newBatchPlannerLog = addBatchPlannerLog();

		BatchPlannerLog existingBatchPlannerLog =
			_persistence.fetchByPrimaryKey(newBatchPlannerLog.getPrimaryKey());

		Assert.assertEquals(existingBatchPlannerLog, newBatchPlannerLog);
	}

	@Test
	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		BatchPlannerLog missingBatchPlannerLog = _persistence.fetchByPrimaryKey(
			pk);

		Assert.assertNull(missingBatchPlannerLog);
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereAllPrimaryKeysExist()
		throws Exception {

		BatchPlannerLog newBatchPlannerLog1 = addBatchPlannerLog();
		BatchPlannerLog newBatchPlannerLog2 = addBatchPlannerLog();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newBatchPlannerLog1.getPrimaryKey());
		primaryKeys.add(newBatchPlannerLog2.getPrimaryKey());

		Map<Serializable, BatchPlannerLog> batchPlannerLogs =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(2, batchPlannerLogs.size());
		Assert.assertEquals(
			newBatchPlannerLog1,
			batchPlannerLogs.get(newBatchPlannerLog1.getPrimaryKey()));
		Assert.assertEquals(
			newBatchPlannerLog2,
			batchPlannerLogs.get(newBatchPlannerLog2.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereNoPrimaryKeysExist()
		throws Exception {

		long pk1 = RandomTestUtil.nextLong();

		long pk2 = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(pk1);
		primaryKeys.add(pk2);

		Map<Serializable, BatchPlannerLog> batchPlannerLogs =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(batchPlannerLogs.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereSomePrimaryKeysExist()
		throws Exception {

		BatchPlannerLog newBatchPlannerLog = addBatchPlannerLog();

		long pk = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newBatchPlannerLog.getPrimaryKey());
		primaryKeys.add(pk);

		Map<Serializable, BatchPlannerLog> batchPlannerLogs =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, batchPlannerLogs.size());
		Assert.assertEquals(
			newBatchPlannerLog,
			batchPlannerLogs.get(newBatchPlannerLog.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithNoPrimaryKeys() throws Exception {
		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		Map<Serializable, BatchPlannerLog> batchPlannerLogs =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(batchPlannerLogs.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithOnePrimaryKey() throws Exception {
		BatchPlannerLog newBatchPlannerLog = addBatchPlannerLog();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newBatchPlannerLog.getPrimaryKey());

		Map<Serializable, BatchPlannerLog> batchPlannerLogs =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, batchPlannerLogs.size());
		Assert.assertEquals(
			newBatchPlannerLog,
			batchPlannerLogs.get(newBatchPlannerLog.getPrimaryKey()));
	}

	@Test
	public void testActionableDynamicQuery() throws Exception {
		final IntegerWrapper count = new IntegerWrapper();

		ActionableDynamicQuery actionableDynamicQuery =
			BatchPlannerLogLocalServiceUtil.getActionableDynamicQuery();

		actionableDynamicQuery.setPerformActionMethod(
			new ActionableDynamicQuery.PerformActionMethod<BatchPlannerLog>() {

				@Override
				public void performAction(BatchPlannerLog batchPlannerLog) {
					Assert.assertNotNull(batchPlannerLog);

					count.increment();
				}

			});

		actionableDynamicQuery.performActions();

		Assert.assertEquals(count.getValue(), _persistence.countAll());
	}

	@Test
	public void testDynamicQueryByPrimaryKeyExisting() throws Exception {
		BatchPlannerLog newBatchPlannerLog = addBatchPlannerLog();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			BatchPlannerLog.class, _dynamicQueryClassLoader);

		dynamicQuery.add(
			RestrictionsFactoryUtil.eq(
				"batchPlannerLogId",
				newBatchPlannerLog.getBatchPlannerLogId()));

		List<BatchPlannerLog> result = _persistence.findWithDynamicQuery(
			dynamicQuery);

		Assert.assertEquals(1, result.size());

		BatchPlannerLog existingBatchPlannerLog = result.get(0);

		Assert.assertEquals(existingBatchPlannerLog, newBatchPlannerLog);
	}

	@Test
	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			BatchPlannerLog.class, _dynamicQueryClassLoader);

		dynamicQuery.add(
			RestrictionsFactoryUtil.eq(
				"batchPlannerLogId", RandomTestUtil.nextLong()));

		List<BatchPlannerLog> result = _persistence.findWithDynamicQuery(
			dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testDynamicQueryByProjectionExisting() throws Exception {
		BatchPlannerLog newBatchPlannerLog = addBatchPlannerLog();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			BatchPlannerLog.class, _dynamicQueryClassLoader);

		dynamicQuery.setProjection(
			ProjectionFactoryUtil.property("batchPlannerLogId"));

		Object newBatchPlannerLogId = newBatchPlannerLog.getBatchPlannerLogId();

		dynamicQuery.add(
			RestrictionsFactoryUtil.in(
				"batchPlannerLogId", new Object[] {newBatchPlannerLogId}));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		Object existingBatchPlannerLogId = result.get(0);

		Assert.assertEquals(existingBatchPlannerLogId, newBatchPlannerLogId);
	}

	@Test
	public void testDynamicQueryByProjectionMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			BatchPlannerLog.class, _dynamicQueryClassLoader);

		dynamicQuery.setProjection(
			ProjectionFactoryUtil.property("batchPlannerLogId"));

		dynamicQuery.add(
			RestrictionsFactoryUtil.in(
				"batchPlannerLogId", new Object[] {RandomTestUtil.nextLong()}));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testResetOriginalValues() throws Exception {
		BatchPlannerLog newBatchPlannerLog = addBatchPlannerLog();

		_persistence.clearCache();

		_assertOriginalValues(
			_persistence.findByPrimaryKey(newBatchPlannerLog.getPrimaryKey()));
	}

	@Test
	public void testResetOriginalValuesWithDynamicQueryLoadFromDatabase()
		throws Exception {

		_testResetOriginalValuesWithDynamicQuery(true);
	}

	@Test
	public void testResetOriginalValuesWithDynamicQueryLoadFromSession()
		throws Exception {

		_testResetOriginalValuesWithDynamicQuery(false);
	}

	private void _testResetOriginalValuesWithDynamicQuery(boolean clearSession)
		throws Exception {

		BatchPlannerLog newBatchPlannerLog = addBatchPlannerLog();

		if (clearSession) {
			Session session = _persistence.openSession();

			session.flush();

			session.clear();
		}

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			BatchPlannerLog.class, _dynamicQueryClassLoader);

		dynamicQuery.add(
			RestrictionsFactoryUtil.eq(
				"batchPlannerLogId",
				newBatchPlannerLog.getBatchPlannerLogId()));

		List<BatchPlannerLog> result = _persistence.findWithDynamicQuery(
			dynamicQuery);

		_assertOriginalValues(result.get(0));
	}

	private void _assertOriginalValues(BatchPlannerLog batchPlannerLog) {
		Assert.assertEquals(
			Long.valueOf(batchPlannerLog.getBatchPlannerPlanId()),
			ReflectionTestUtil.<Long>invoke(
				batchPlannerLog, "getColumnOriginalValue",
				new Class<?>[] {String.class}, "batchPlannerPlanId"));

		Assert.assertEquals(
			Long.valueOf(batchPlannerLog.getBatchPlannerPlanId()),
			ReflectionTestUtil.<Long>invoke(
				batchPlannerLog, "getColumnOriginalValue",
				new Class<?>[] {String.class}, "batchPlannerPlanId"));
		Assert.assertEquals(
			batchPlannerLog.getBatchEngineExportTaskERC(),
			ReflectionTestUtil.invoke(
				batchPlannerLog, "getColumnOriginalValue",
				new Class<?>[] {String.class}, "batchEngineExportTaskERC"));

		Assert.assertEquals(
			Long.valueOf(batchPlannerLog.getBatchPlannerPlanId()),
			ReflectionTestUtil.<Long>invoke(
				batchPlannerLog, "getColumnOriginalValue",
				new Class<?>[] {String.class}, "batchPlannerPlanId"));
		Assert.assertEquals(
			batchPlannerLog.getBatchEngineImportTaskERC(),
			ReflectionTestUtil.invoke(
				batchPlannerLog, "getColumnOriginalValue",
				new Class<?>[] {String.class}, "batchEngineImportTaskERC"));

		Assert.assertEquals(
			Long.valueOf(batchPlannerLog.getBatchPlannerPlanId()),
			ReflectionTestUtil.<Long>invoke(
				batchPlannerLog, "getColumnOriginalValue",
				new Class<?>[] {String.class}, "batchPlannerPlanId"));
		Assert.assertEquals(
			batchPlannerLog.getDispatchTriggerERC(),
			ReflectionTestUtil.invoke(
				batchPlannerLog, "getColumnOriginalValue",
				new Class<?>[] {String.class}, "dispatchTriggerERC"));
	}

	protected BatchPlannerLog addBatchPlannerLog() throws Exception {
		long pk = RandomTestUtil.nextLong();

		BatchPlannerLog batchPlannerLog = _persistence.create(pk);

		batchPlannerLog.setMvccVersion(RandomTestUtil.nextLong());

		batchPlannerLog.setCompanyId(RandomTestUtil.nextLong());

		batchPlannerLog.setUserId(RandomTestUtil.nextLong());

		batchPlannerLog.setUserName(RandomTestUtil.randomString());

		batchPlannerLog.setCreateDate(RandomTestUtil.nextDate());

		batchPlannerLog.setModifiedDate(RandomTestUtil.nextDate());

		batchPlannerLog.setBatchPlannerPlanId(RandomTestUtil.nextLong());

		batchPlannerLog.setBatchEngineExportTaskERC(
			RandomTestUtil.randomString());

		batchPlannerLog.setBatchEngineImportTaskERC(
			RandomTestUtil.randomString());

		batchPlannerLog.setDispatchTriggerERC(RandomTestUtil.randomString());

		batchPlannerLog.setSize(RandomTestUtil.nextInt());

		batchPlannerLog.setTotal(RandomTestUtil.nextInt());

		batchPlannerLog.setStatus(RandomTestUtil.nextInt());

		_batchPlannerLogs.add(_persistence.update(batchPlannerLog));

		return batchPlannerLog;
	}

	private List<BatchPlannerLog> _batchPlannerLogs =
		new ArrayList<BatchPlannerLog>();
	private BatchPlannerLogPersistence _persistence;
	private ClassLoader _dynamicQueryClassLoader;

}