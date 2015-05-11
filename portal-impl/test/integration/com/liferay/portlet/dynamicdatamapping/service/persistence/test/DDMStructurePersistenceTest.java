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

package com.liferay.portlet.dynamicdatamapping.service.persistence.test;

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

import com.liferay.portlet.dynamicdatamapping.NoSuchStructureException;
import com.liferay.portlet.dynamicdatamapping.model.DDMStructure;
import com.liferay.portlet.dynamicdatamapping.service.DDMStructureLocalServiceUtil;
import com.liferay.portlet.dynamicdatamapping.service.persistence.DDMStructurePersistence;
import com.liferay.portlet.dynamicdatamapping.service.persistence.DDMStructureUtil;

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
public class DDMStructurePersistenceTest {
	@Rule
	public final AggregateTestRule aggregateTestRule = new AggregateTestRule(new LiferayIntegrationTestRule(),
			PersistenceTestRule.INSTANCE,
			new TransactionalTestRule(Propagation.REQUIRED));

	@Before
	public void setUp() {
		_persistence = DDMStructureUtil.getPersistence();

		Class<?> clazz = _persistence.getClass();

		_dynamicQueryClassLoader = clazz.getClassLoader();
	}

	@After
	public void tearDown() throws Exception {
		Iterator<DDMStructure> iterator = _ddmStructures.iterator();

		while (iterator.hasNext()) {
			_persistence.remove(iterator.next());

			iterator.remove();
		}
	}

	@Test
	public void testCreate() throws Exception {
		long pk = RandomTestUtil.nextLong();

		DDMStructure ddmStructure = _persistence.create(pk);

		Assert.assertNotNull(ddmStructure);

		Assert.assertEquals(ddmStructure.getPrimaryKey(), pk);
	}

	@Test
	public void testRemove() throws Exception {
		DDMStructure newDDMStructure = addDDMStructure();

		_persistence.remove(newDDMStructure);

		DDMStructure existingDDMStructure = _persistence.fetchByPrimaryKey(newDDMStructure.getPrimaryKey());

		Assert.assertNull(existingDDMStructure);
	}

	@Test
	public void testUpdateNew() throws Exception {
		addDDMStructure();
	}

	@Test
	public void testUpdateExisting() throws Exception {
		long pk = RandomTestUtil.nextLong();

		DDMStructure newDDMStructure = _persistence.create(pk);

		newDDMStructure.setUuid(RandomTestUtil.randomString());

		newDDMStructure.setGroupId(RandomTestUtil.nextLong());

		newDDMStructure.setCompanyId(RandomTestUtil.nextLong());

		newDDMStructure.setUserId(RandomTestUtil.nextLong());

		newDDMStructure.setUserName(RandomTestUtil.randomString());

		newDDMStructure.setCreateDate(RandomTestUtil.nextDate());

		newDDMStructure.setModifiedDate(RandomTestUtil.nextDate());

		newDDMStructure.setParentStructureId(RandomTestUtil.nextLong());

		newDDMStructure.setClassNameId(RandomTestUtil.nextLong());

		newDDMStructure.setStructureKey(RandomTestUtil.randomString());

		newDDMStructure.setVersion(RandomTestUtil.randomString());

		newDDMStructure.setName(RandomTestUtil.randomString());

		newDDMStructure.setDescription(RandomTestUtil.randomString());

		newDDMStructure.setDefinition(RandomTestUtil.randomString());

		newDDMStructure.setStorageType(RandomTestUtil.randomString());

		newDDMStructure.setType(RandomTestUtil.nextInt());

		_ddmStructures.add(_persistence.update(newDDMStructure));

		DDMStructure existingDDMStructure = _persistence.findByPrimaryKey(newDDMStructure.getPrimaryKey());

		Assert.assertEquals(existingDDMStructure.getUuid(),
			newDDMStructure.getUuid());
		Assert.assertEquals(existingDDMStructure.getStructureId(),
			newDDMStructure.getStructureId());
		Assert.assertEquals(existingDDMStructure.getGroupId(),
			newDDMStructure.getGroupId());
		Assert.assertEquals(existingDDMStructure.getCompanyId(),
			newDDMStructure.getCompanyId());
		Assert.assertEquals(existingDDMStructure.getUserId(),
			newDDMStructure.getUserId());
		Assert.assertEquals(existingDDMStructure.getUserName(),
			newDDMStructure.getUserName());
		Assert.assertEquals(Time.getShortTimestamp(
				existingDDMStructure.getCreateDate()),
			Time.getShortTimestamp(newDDMStructure.getCreateDate()));
		Assert.assertEquals(Time.getShortTimestamp(
				existingDDMStructure.getModifiedDate()),
			Time.getShortTimestamp(newDDMStructure.getModifiedDate()));
		Assert.assertEquals(existingDDMStructure.getParentStructureId(),
			newDDMStructure.getParentStructureId());
		Assert.assertEquals(existingDDMStructure.getClassNameId(),
			newDDMStructure.getClassNameId());
		Assert.assertEquals(existingDDMStructure.getStructureKey(),
			newDDMStructure.getStructureKey());
		Assert.assertEquals(existingDDMStructure.getVersion(),
			newDDMStructure.getVersion());
		Assert.assertEquals(existingDDMStructure.getName(),
			newDDMStructure.getName());
		Assert.assertEquals(existingDDMStructure.getDescription(),
			newDDMStructure.getDescription());
		Assert.assertEquals(existingDDMStructure.getDefinition(),
			newDDMStructure.getDefinition());
		Assert.assertEquals(existingDDMStructure.getStorageType(),
			newDDMStructure.getStorageType());
		Assert.assertEquals(existingDDMStructure.getType(),
			newDDMStructure.getType());
	}

	@Test
	public void testCountByUuid() throws Exception {
		_persistence.countByUuid(StringPool.BLANK);

		_persistence.countByUuid(StringPool.NULL);

		_persistence.countByUuid((String)null);
	}

	@Test
	public void testCountByUUID_G() throws Exception {
		_persistence.countByUUID_G(StringPool.BLANK, RandomTestUtil.nextLong());

		_persistence.countByUUID_G(StringPool.NULL, 0L);

		_persistence.countByUUID_G((String)null, 0L);
	}

	@Test
	public void testCountByUuid_C() throws Exception {
		_persistence.countByUuid_C(StringPool.BLANK, RandomTestUtil.nextLong());

		_persistence.countByUuid_C(StringPool.NULL, 0L);

		_persistence.countByUuid_C((String)null, 0L);
	}

	@Test
	public void testCountByGroupId() throws Exception {
		_persistence.countByGroupId(RandomTestUtil.nextLong());

		_persistence.countByGroupId(0L);
	}

	@Test
	public void testCountByGroupIdArrayable() throws Exception {
		_persistence.countByGroupId(new long[] { RandomTestUtil.nextLong(), 0L });
	}

	@Test
	public void testCountByParentStructureId() throws Exception {
		_persistence.countByParentStructureId(RandomTestUtil.nextLong());

		_persistence.countByParentStructureId(0L);
	}

	@Test
	public void testCountByClassNameId() throws Exception {
		_persistence.countByClassNameId(RandomTestUtil.nextLong());

		_persistence.countByClassNameId(0L);
	}

	@Test
	public void testCountByStructureKey() throws Exception {
		_persistence.countByStructureKey(StringPool.BLANK);

		_persistence.countByStructureKey(StringPool.NULL);

		_persistence.countByStructureKey((String)null);
	}

	@Test
	public void testCountByG_P() throws Exception {
		_persistence.countByG_P(RandomTestUtil.nextLong(),
			RandomTestUtil.nextLong());

		_persistence.countByG_P(0L, 0L);
	}

	@Test
	public void testCountByG_C() throws Exception {
		_persistence.countByG_C(RandomTestUtil.nextLong(),
			RandomTestUtil.nextLong());

		_persistence.countByG_C(0L, 0L);
	}

	@Test
	public void testCountByG_CArrayable() throws Exception {
		_persistence.countByG_C(new long[] { RandomTestUtil.nextLong(), 0L },
			RandomTestUtil.nextLong());
	}

	@Test
	public void testCountByC_C() throws Exception {
		_persistence.countByC_C(RandomTestUtil.nextLong(),
			RandomTestUtil.nextLong());

		_persistence.countByC_C(0L, 0L);
	}

	@Test
	public void testCountByG_C_S() throws Exception {
		_persistence.countByG_C_S(RandomTestUtil.nextLong(),
			RandomTestUtil.nextLong(), StringPool.BLANK);

		_persistence.countByG_C_S(0L, 0L, StringPool.NULL);

		_persistence.countByG_C_S(0L, 0L, (String)null);
	}

	@Test
	public void testCountByG_N_D() throws Exception {
		_persistence.countByG_N_D(RandomTestUtil.nextLong(), StringPool.BLANK,
			StringPool.BLANK);

		_persistence.countByG_N_D(0L, StringPool.NULL, StringPool.NULL);

		_persistence.countByG_N_D(0L, (String)null, (String)null);
	}

	@Test
	public void testFindByPrimaryKeyExisting() throws Exception {
		DDMStructure newDDMStructure = addDDMStructure();

		DDMStructure existingDDMStructure = _persistence.findByPrimaryKey(newDDMStructure.getPrimaryKey());

		Assert.assertEquals(existingDDMStructure, newDDMStructure);
	}

	@Test(expected = NoSuchStructureException.class)
	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		_persistence.findByPrimaryKey(pk);
	}

	@Test
	public void testFindAll() throws Exception {
		_persistence.findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			getOrderByComparator());
	}

	@Test
	public void testFilterFindByGroupId() throws Exception {
		_persistence.filterFindByGroupId(0, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, getOrderByComparator());
	}

	protected OrderByComparator<DDMStructure> getOrderByComparator() {
		return OrderByComparatorFactoryUtil.create("DDMStructure", "uuid",
			true, "structureId", true, "groupId", true, "companyId", true,
			"userId", true, "userName", true, "createDate", true,
			"modifiedDate", true, "parentStructureId", true, "classNameId",
			true, "structureKey", true, "version", true, "name", true,
			"description", true, "definition", true, "storageType", true,
			"type", true);
	}

	@Test
	public void testFetchByPrimaryKeyExisting() throws Exception {
		DDMStructure newDDMStructure = addDDMStructure();

		DDMStructure existingDDMStructure = _persistence.fetchByPrimaryKey(newDDMStructure.getPrimaryKey());

		Assert.assertEquals(existingDDMStructure, newDDMStructure);
	}

	@Test
	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		DDMStructure missingDDMStructure = _persistence.fetchByPrimaryKey(pk);

		Assert.assertNull(missingDDMStructure);
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereAllPrimaryKeysExist()
		throws Exception {
		DDMStructure newDDMStructure1 = addDDMStructure();
		DDMStructure newDDMStructure2 = addDDMStructure();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newDDMStructure1.getPrimaryKey());
		primaryKeys.add(newDDMStructure2.getPrimaryKey());

		Map<Serializable, DDMStructure> ddmStructures = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(2, ddmStructures.size());
		Assert.assertEquals(newDDMStructure1,
			ddmStructures.get(newDDMStructure1.getPrimaryKey()));
		Assert.assertEquals(newDDMStructure2,
			ddmStructures.get(newDDMStructure2.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereNoPrimaryKeysExist()
		throws Exception {
		long pk1 = RandomTestUtil.nextLong();

		long pk2 = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(pk1);
		primaryKeys.add(pk2);

		Map<Serializable, DDMStructure> ddmStructures = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(ddmStructures.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereSomePrimaryKeysExist()
		throws Exception {
		DDMStructure newDDMStructure = addDDMStructure();

		long pk = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newDDMStructure.getPrimaryKey());
		primaryKeys.add(pk);

		Map<Serializable, DDMStructure> ddmStructures = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, ddmStructures.size());
		Assert.assertEquals(newDDMStructure,
			ddmStructures.get(newDDMStructure.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithNoPrimaryKeys()
		throws Exception {
		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		Map<Serializable, DDMStructure> ddmStructures = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(ddmStructures.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithOnePrimaryKey()
		throws Exception {
		DDMStructure newDDMStructure = addDDMStructure();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newDDMStructure.getPrimaryKey());

		Map<Serializable, DDMStructure> ddmStructures = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, ddmStructures.size());
		Assert.assertEquals(newDDMStructure,
			ddmStructures.get(newDDMStructure.getPrimaryKey()));
	}

	@Test
	public void testActionableDynamicQuery() throws Exception {
		final IntegerWrapper count = new IntegerWrapper();

		ActionableDynamicQuery actionableDynamicQuery = DDMStructureLocalServiceUtil.getActionableDynamicQuery();

		actionableDynamicQuery.setPerformActionMethod(new ActionableDynamicQuery.PerformActionMethod() {
				@Override
				public void performAction(Object object) {
					DDMStructure ddmStructure = (DDMStructure)object;

					Assert.assertNotNull(ddmStructure);

					count.increment();
				}
			});

		actionableDynamicQuery.performActions();

		Assert.assertEquals(count.getValue(), _persistence.countAll());
	}

	@Test
	public void testDynamicQueryByPrimaryKeyExisting()
		throws Exception {
		DDMStructure newDDMStructure = addDDMStructure();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(DDMStructure.class,
				_dynamicQueryClassLoader);

		dynamicQuery.add(RestrictionsFactoryUtil.eq("structureId",
				newDDMStructure.getStructureId()));

		List<DDMStructure> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		DDMStructure existingDDMStructure = result.get(0);

		Assert.assertEquals(existingDDMStructure, newDDMStructure);
	}

	@Test
	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(DDMStructure.class,
				_dynamicQueryClassLoader);

		dynamicQuery.add(RestrictionsFactoryUtil.eq("structureId",
				RandomTestUtil.nextLong()));

		List<DDMStructure> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testDynamicQueryByProjectionExisting()
		throws Exception {
		DDMStructure newDDMStructure = addDDMStructure();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(DDMStructure.class,
				_dynamicQueryClassLoader);

		dynamicQuery.setProjection(ProjectionFactoryUtil.property("structureId"));

		Object newStructureId = newDDMStructure.getStructureId();

		dynamicQuery.add(RestrictionsFactoryUtil.in("structureId",
				new Object[] { newStructureId }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		Object existingStructureId = result.get(0);

		Assert.assertEquals(existingStructureId, newStructureId);
	}

	@Test
	public void testDynamicQueryByProjectionMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(DDMStructure.class,
				_dynamicQueryClassLoader);

		dynamicQuery.setProjection(ProjectionFactoryUtil.property("structureId"));

		dynamicQuery.add(RestrictionsFactoryUtil.in("structureId",
				new Object[] { RandomTestUtil.nextLong() }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testResetOriginalValues() throws Exception {
		DDMStructure newDDMStructure = addDDMStructure();

		_persistence.clearCache();

		DDMStructure existingDDMStructure = _persistence.findByPrimaryKey(newDDMStructure.getPrimaryKey());

		Assert.assertTrue(Validator.equals(existingDDMStructure.getUuid(),
				ReflectionTestUtil.invoke(existingDDMStructure,
					"getOriginalUuid", new Class<?>[0])));
		Assert.assertEquals(existingDDMStructure.getGroupId(),
			ReflectionTestUtil.invoke(existingDDMStructure,
				"getOriginalGroupId", new Class<?>[0]));

		Assert.assertEquals(existingDDMStructure.getGroupId(),
			ReflectionTestUtil.invoke(existingDDMStructure,
				"getOriginalGroupId", new Class<?>[0]));
		Assert.assertEquals(existingDDMStructure.getClassNameId(),
			ReflectionTestUtil.invoke(existingDDMStructure,
				"getOriginalClassNameId", new Class<?>[0]));
		Assert.assertTrue(Validator.equals(
				existingDDMStructure.getStructureKey(),
				ReflectionTestUtil.invoke(existingDDMStructure,
					"getOriginalStructureKey", new Class<?>[0])));
	}

	protected DDMStructure addDDMStructure() throws Exception {
		long pk = RandomTestUtil.nextLong();

		DDMStructure ddmStructure = _persistence.create(pk);

		ddmStructure.setUuid(RandomTestUtil.randomString());

		ddmStructure.setGroupId(RandomTestUtil.nextLong());

		ddmStructure.setCompanyId(RandomTestUtil.nextLong());

		ddmStructure.setUserId(RandomTestUtil.nextLong());

		ddmStructure.setUserName(RandomTestUtil.randomString());

		ddmStructure.setCreateDate(RandomTestUtil.nextDate());

		ddmStructure.setModifiedDate(RandomTestUtil.nextDate());

		ddmStructure.setParentStructureId(RandomTestUtil.nextLong());

		ddmStructure.setClassNameId(RandomTestUtil.nextLong());

		ddmStructure.setStructureKey(RandomTestUtil.randomString());

		ddmStructure.setVersion(RandomTestUtil.randomString());

		ddmStructure.setName(RandomTestUtil.randomString());

		ddmStructure.setDescription(RandomTestUtil.randomString());

		ddmStructure.setDefinition(RandomTestUtil.randomString());

		ddmStructure.setStorageType(RandomTestUtil.randomString());

		ddmStructure.setType(RandomTestUtil.nextInt());

		_ddmStructures.add(_persistence.update(ddmStructure));

		return ddmStructure;
	}

	private List<DDMStructure> _ddmStructures = new ArrayList<DDMStructure>();
	private DDMStructurePersistence _persistence;
	private ClassLoader _dynamicQueryClassLoader;
}