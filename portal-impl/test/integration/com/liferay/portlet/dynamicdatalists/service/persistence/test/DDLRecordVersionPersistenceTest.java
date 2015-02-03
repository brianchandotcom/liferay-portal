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

import com.liferay.portlet.dynamicdatalists.NoSuchRecordVersionException;
import com.liferay.portlet.dynamicdatalists.model.DDLRecordVersion;
import com.liferay.portlet.dynamicdatalists.service.DDLRecordVersionLocalServiceUtil;
import com.liferay.portlet.dynamicdatalists.service.persistence.DDLRecordVersionPersistence;
import com.liferay.portlet.dynamicdatalists.service.persistence.DDLRecordVersionUtil;

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
public class DDLRecordVersionPersistenceTest {
	@Rule
	public final AggregateTestRule aggregateTestRule = new AggregateTestRule(new LiferayIntegrationTestRule(),
			PersistenceTestRule.INSTANCE,
			new TransactionalTestRule(Propagation.REQUIRED));

	@Before
	public void setUp() {
		_persistence = DDLRecordVersionUtil.getPersistence();

		Class<?> clazz = _persistence.getClass();

		_dynamicQueryClassLoader = clazz.getClassLoader();
	}

	@After
	public void tearDown() throws Exception {
		Iterator<DDLRecordVersion> iterator = _ddlRecordVersions.iterator();

		while (iterator.hasNext()) {
			_persistence.remove(iterator.next());

			iterator.remove();
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

		_ddlRecordVersions.add(_persistence.update(newDDLRecordVersion));

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

	protected OrderByComparator<DDLRecordVersion> getOrderByComparator() {
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
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereAllPrimaryKeysExist()
		throws Exception {
		DDLRecordVersion newDDLRecordVersion1 = addDDLRecordVersion();
		DDLRecordVersion newDDLRecordVersion2 = addDDLRecordVersion();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newDDLRecordVersion1.getPrimaryKey());
		primaryKeys.add(newDDLRecordVersion2.getPrimaryKey());

		Map<Serializable, DDLRecordVersion> ddlRecordVersions = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(2, ddlRecordVersions.size());
		Assert.assertEquals(newDDLRecordVersion1,
			ddlRecordVersions.get(newDDLRecordVersion1.getPrimaryKey()));
		Assert.assertEquals(newDDLRecordVersion2,
			ddlRecordVersions.get(newDDLRecordVersion2.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereNoPrimaryKeysExist()
		throws Exception {
		long pk1 = RandomTestUtil.nextLong();

		long pk2 = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(pk1);
		primaryKeys.add(pk2);

		Map<Serializable, DDLRecordVersion> ddlRecordVersions = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(ddlRecordVersions.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereSomePrimaryKeysExist()
		throws Exception {
		DDLRecordVersion newDDLRecordVersion = addDDLRecordVersion();

		long pk = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newDDLRecordVersion.getPrimaryKey());
		primaryKeys.add(pk);

		Map<Serializable, DDLRecordVersion> ddlRecordVersions = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, ddlRecordVersions.size());
		Assert.assertEquals(newDDLRecordVersion,
			ddlRecordVersions.get(newDDLRecordVersion.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithNoPrimaryKeys()
		throws Exception {
		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		Map<Serializable, DDLRecordVersion> ddlRecordVersions = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(ddlRecordVersions.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithOnePrimaryKey()
		throws Exception {
		DDLRecordVersion newDDLRecordVersion = addDDLRecordVersion();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newDDLRecordVersion.getPrimaryKey());

		Map<Serializable, DDLRecordVersion> ddlRecordVersions = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, ddlRecordVersions.size());
		Assert.assertEquals(newDDLRecordVersion,
			ddlRecordVersions.get(newDDLRecordVersion.getPrimaryKey()));
	}

	@Test
	public void testActionableDynamicQuery() throws Exception {
		final IntegerWrapper count = new IntegerWrapper();

		ActionableDynamicQuery actionableDynamicQuery = DDLRecordVersionLocalServiceUtil.getActionableDynamicQuery();

		actionableDynamicQuery.setPerformActionMethod(new ActionableDynamicQuery.PerformActionMethod() {
				@Override
				public void performAction(Object object) {
					DDLRecordVersion ddlRecordVersion = (DDLRecordVersion)object;

					Assert.assertNotNull(ddlRecordVersion);

					count.increment();
				}
			});

		actionableDynamicQuery.performActions();

		Assert.assertEquals(count.getValue(), _persistence.countAll());
	}

	@Test
	public void testDynamicQueryByPrimaryKeyExisting()
		throws Exception {
		DDLRecordVersion newDDLRecordVersion = addDDLRecordVersion();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(DDLRecordVersion.class,
				_dynamicQueryClassLoader);

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
				_dynamicQueryClassLoader);

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
				_dynamicQueryClassLoader);

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
				_dynamicQueryClassLoader);

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

		DDLRecordVersion existingDDLRecordVersion = _persistence.findByPrimaryKey(newDDLRecordVersion.getPrimaryKey());

		Assert.assertEquals(existingDDLRecordVersion.getRecordId(),
			ReflectionTestUtil.invoke(existingDDLRecordVersion,
				"getOriginalRecordId", new Class<?>[0]));
		Assert.assertTrue(Validator.equals(
				existingDDLRecordVersion.getVersion(),
				ReflectionTestUtil.invoke(existingDDLRecordVersion,
					"getOriginalVersion", new Class<?>[0])));
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

		_ddlRecordVersions.add(_persistence.update(ddlRecordVersion));

		return ddlRecordVersion;
	}

	private List<DDLRecordVersion> _ddlRecordVersions = new ArrayList<DDLRecordVersion>();
	private DDLRecordVersionPersistence _persistence;
	private ClassLoader _dynamicQueryClassLoader;
}