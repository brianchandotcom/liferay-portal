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

import com.liferay.portlet.dynamicdatalists.NoSuchRecordSetException;
import com.liferay.portlet.dynamicdatalists.model.DDLRecordSet;
import com.liferay.portlet.dynamicdatalists.service.DDLRecordSetLocalServiceUtil;
import com.liferay.portlet.dynamicdatalists.service.persistence.DDLRecordSetPersistence;
import com.liferay.portlet.dynamicdatalists.service.persistence.DDLRecordSetUtil;

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
public class DDLRecordSetPersistenceTest {
	@Rule
	public final AggregateTestRule aggregateTestRule = new AggregateTestRule(new LiferayIntegrationTestRule(),
			PersistenceTestRule.INSTANCE,
			new TransactionalTestRule(Propagation.REQUIRED));

	@Before
	public void setUp() {
		_persistence = DDLRecordSetUtil.getPersistence();

		Class<?> clazz = _persistence.getClass();

		_dynamicQueryClassLoader = clazz.getClassLoader();
	}

	@After
	public void tearDown() throws Exception {
		Iterator<DDLRecordSet> iterator = _ddlRecordSets.iterator();

		while (iterator.hasNext()) {
			_persistence.remove(iterator.next());

			iterator.remove();
		}
	}

	@Test
	public void testCreate() throws Exception {
		long pk = RandomTestUtil.nextLong();

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
		long pk = RandomTestUtil.nextLong();

		DDLRecordSet newDDLRecordSet = _persistence.create(pk);

		newDDLRecordSet.setUuid(RandomTestUtil.randomString());

		newDDLRecordSet.setGroupId(RandomTestUtil.nextLong());

		newDDLRecordSet.setCompanyId(RandomTestUtil.nextLong());

		newDDLRecordSet.setUserId(RandomTestUtil.nextLong());

		newDDLRecordSet.setUserName(RandomTestUtil.randomString());

		newDDLRecordSet.setCreateDate(RandomTestUtil.nextDate());

		newDDLRecordSet.setModifiedDate(RandomTestUtil.nextDate());

		newDDLRecordSet.setDDMStructureId(RandomTestUtil.nextLong());

		newDDLRecordSet.setRecordSetKey(RandomTestUtil.randomString());

		newDDLRecordSet.setName(RandomTestUtil.randomString());

		newDDLRecordSet.setDescription(RandomTestUtil.randomString());

		newDDLRecordSet.setMinDisplayRows(RandomTestUtil.nextInt());

		newDDLRecordSet.setScope(RandomTestUtil.nextInt());

		_ddlRecordSets.add(_persistence.update(newDDLRecordSet));

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
	public void testCountByGroupIdArrayable() {
		try {
			_persistence.countByGroupId(new long[] { RandomTestUtil.nextLong(), 0L });
		}
		catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void testCountByG_R() {
		try {
			_persistence.countByG_R(RandomTestUtil.nextLong(), StringPool.BLANK);

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
		long pk = RandomTestUtil.nextLong();

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

	protected OrderByComparator<DDLRecordSet> getOrderByComparator() {
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
		long pk = RandomTestUtil.nextLong();

		DDLRecordSet missingDDLRecordSet = _persistence.fetchByPrimaryKey(pk);

		Assert.assertNull(missingDDLRecordSet);
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereAllPrimaryKeysExist()
		throws Exception {
		DDLRecordSet newDDLRecordSet1 = addDDLRecordSet();
		DDLRecordSet newDDLRecordSet2 = addDDLRecordSet();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newDDLRecordSet1.getPrimaryKey());
		primaryKeys.add(newDDLRecordSet2.getPrimaryKey());

		Map<Serializable, DDLRecordSet> ddlRecordSets = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(2, ddlRecordSets.size());
		Assert.assertEquals(newDDLRecordSet1,
			ddlRecordSets.get(newDDLRecordSet1.getPrimaryKey()));
		Assert.assertEquals(newDDLRecordSet2,
			ddlRecordSets.get(newDDLRecordSet2.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereNoPrimaryKeysExist()
		throws Exception {
		long pk1 = RandomTestUtil.nextLong();

		long pk2 = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(pk1);
		primaryKeys.add(pk2);

		Map<Serializable, DDLRecordSet> ddlRecordSets = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(ddlRecordSets.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereSomePrimaryKeysExist()
		throws Exception {
		DDLRecordSet newDDLRecordSet = addDDLRecordSet();

		long pk = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newDDLRecordSet.getPrimaryKey());
		primaryKeys.add(pk);

		Map<Serializable, DDLRecordSet> ddlRecordSets = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, ddlRecordSets.size());
		Assert.assertEquals(newDDLRecordSet,
			ddlRecordSets.get(newDDLRecordSet.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithNoPrimaryKeys()
		throws Exception {
		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		Map<Serializable, DDLRecordSet> ddlRecordSets = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(ddlRecordSets.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithOnePrimaryKey()
		throws Exception {
		DDLRecordSet newDDLRecordSet = addDDLRecordSet();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newDDLRecordSet.getPrimaryKey());

		Map<Serializable, DDLRecordSet> ddlRecordSets = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, ddlRecordSets.size());
		Assert.assertEquals(newDDLRecordSet,
			ddlRecordSets.get(newDDLRecordSet.getPrimaryKey()));
	}

	@Test
	public void testActionableDynamicQuery() throws Exception {
		final IntegerWrapper count = new IntegerWrapper();

		ActionableDynamicQuery actionableDynamicQuery = DDLRecordSetLocalServiceUtil.getActionableDynamicQuery();

		actionableDynamicQuery.setPerformActionMethod(new ActionableDynamicQuery.PerformActionMethod() {
				@Override
				public void performAction(Object object) {
					DDLRecordSet ddlRecordSet = (DDLRecordSet)object;

					Assert.assertNotNull(ddlRecordSet);

					count.increment();
				}
			});

		actionableDynamicQuery.performActions();

		Assert.assertEquals(count.getValue(), _persistence.countAll());
	}

	@Test
	public void testDynamicQueryByPrimaryKeyExisting()
		throws Exception {
		DDLRecordSet newDDLRecordSet = addDDLRecordSet();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(DDLRecordSet.class,
				_dynamicQueryClassLoader);

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
				_dynamicQueryClassLoader);

		dynamicQuery.add(RestrictionsFactoryUtil.eq("recordSetId",
				RandomTestUtil.nextLong()));

		List<DDLRecordSet> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testDynamicQueryByProjectionExisting()
		throws Exception {
		DDLRecordSet newDDLRecordSet = addDDLRecordSet();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(DDLRecordSet.class,
				_dynamicQueryClassLoader);

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
				_dynamicQueryClassLoader);

		dynamicQuery.setProjection(ProjectionFactoryUtil.property("recordSetId"));

		dynamicQuery.add(RestrictionsFactoryUtil.in("recordSetId",
				new Object[] { RandomTestUtil.nextLong() }));

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

		DDLRecordSet existingDDLRecordSet = _persistence.findByPrimaryKey(newDDLRecordSet.getPrimaryKey());

		Assert.assertTrue(Validator.equals(existingDDLRecordSet.getUuid(),
				ReflectionTestUtil.invoke(existingDDLRecordSet,
					"getOriginalUuid", new Class<?>[0])));
		Assert.assertEquals(existingDDLRecordSet.getGroupId(),
			ReflectionTestUtil.invoke(existingDDLRecordSet,
				"getOriginalGroupId", new Class<?>[0]));

		Assert.assertEquals(existingDDLRecordSet.getGroupId(),
			ReflectionTestUtil.invoke(existingDDLRecordSet,
				"getOriginalGroupId", new Class<?>[0]));
		Assert.assertTrue(Validator.equals(
				existingDDLRecordSet.getRecordSetKey(),
				ReflectionTestUtil.invoke(existingDDLRecordSet,
					"getOriginalRecordSetKey", new Class<?>[0])));
	}

	protected DDLRecordSet addDDLRecordSet() throws Exception {
		long pk = RandomTestUtil.nextLong();

		DDLRecordSet ddlRecordSet = _persistence.create(pk);

		ddlRecordSet.setUuid(RandomTestUtil.randomString());

		ddlRecordSet.setGroupId(RandomTestUtil.nextLong());

		ddlRecordSet.setCompanyId(RandomTestUtil.nextLong());

		ddlRecordSet.setUserId(RandomTestUtil.nextLong());

		ddlRecordSet.setUserName(RandomTestUtil.randomString());

		ddlRecordSet.setCreateDate(RandomTestUtil.nextDate());

		ddlRecordSet.setModifiedDate(RandomTestUtil.nextDate());

		ddlRecordSet.setDDMStructureId(RandomTestUtil.nextLong());

		ddlRecordSet.setRecordSetKey(RandomTestUtil.randomString());

		ddlRecordSet.setName(RandomTestUtil.randomString());

		ddlRecordSet.setDescription(RandomTestUtil.randomString());

		ddlRecordSet.setMinDisplayRows(RandomTestUtil.nextInt());

		ddlRecordSet.setScope(RandomTestUtil.nextInt());

		_ddlRecordSets.add(_persistence.update(ddlRecordSet));

		return ddlRecordSet;
	}

	private List<DDLRecordSet> _ddlRecordSets = new ArrayList<DDLRecordSet>();
	private DDLRecordSetPersistence _persistence;
	private ClassLoader _dynamicQueryClassLoader;
}