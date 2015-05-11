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

import com.liferay.portlet.dynamicdatamapping.NoSuchStructureLayoutException;
import com.liferay.portlet.dynamicdatamapping.model.DDMStructureLayout;
import com.liferay.portlet.dynamicdatamapping.service.DDMStructureLayoutLocalServiceUtil;
import com.liferay.portlet.dynamicdatamapping.service.persistence.DDMStructureLayoutPersistence;
import com.liferay.portlet.dynamicdatamapping.service.persistence.DDMStructureLayoutUtil;

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
public class DDMStructureLayoutPersistenceTest {
	@Rule
	public final AggregateTestRule aggregateTestRule = new AggregateTestRule(new LiferayIntegrationTestRule(),
			PersistenceTestRule.INSTANCE,
			new TransactionalTestRule(Propagation.REQUIRED));

	@Before
	public void setUp() {
		_persistence = DDMStructureLayoutUtil.getPersistence();

		Class<?> clazz = _persistence.getClass();

		_dynamicQueryClassLoader = clazz.getClassLoader();
	}

	@After
	public void tearDown() throws Exception {
		Iterator<DDMStructureLayout> iterator = _ddmStructureLayouts.iterator();

		while (iterator.hasNext()) {
			_persistence.remove(iterator.next());

			iterator.remove();
		}
	}

	@Test
	public void testCreate() throws Exception {
		long pk = RandomTestUtil.nextLong();

		DDMStructureLayout ddmStructureLayout = _persistence.create(pk);

		Assert.assertNotNull(ddmStructureLayout);

		Assert.assertEquals(ddmStructureLayout.getPrimaryKey(), pk);
	}

	@Test
	public void testRemove() throws Exception {
		DDMStructureLayout newDDMStructureLayout = addDDMStructureLayout();

		_persistence.remove(newDDMStructureLayout);

		DDMStructureLayout existingDDMStructureLayout = _persistence.fetchByPrimaryKey(newDDMStructureLayout.getPrimaryKey());

		Assert.assertNull(existingDDMStructureLayout);
	}

	@Test
	public void testUpdateNew() throws Exception {
		addDDMStructureLayout();
	}

	@Test
	public void testUpdateExisting() throws Exception {
		long pk = RandomTestUtil.nextLong();

		DDMStructureLayout newDDMStructureLayout = _persistence.create(pk);

		newDDMStructureLayout.setUuid(RandomTestUtil.randomString());

		newDDMStructureLayout.setGroupId(RandomTestUtil.nextLong());

		newDDMStructureLayout.setCompanyId(RandomTestUtil.nextLong());

		newDDMStructureLayout.setUserId(RandomTestUtil.nextLong());

		newDDMStructureLayout.setUserName(RandomTestUtil.randomString());

		newDDMStructureLayout.setCreateDate(RandomTestUtil.nextDate());

		newDDMStructureLayout.setModifiedDate(RandomTestUtil.nextDate());

		newDDMStructureLayout.setStructureVersionId(RandomTestUtil.nextLong());

		newDDMStructureLayout.setDefinition(RandomTestUtil.randomString());

		_ddmStructureLayouts.add(_persistence.update(newDDMStructureLayout));

		DDMStructureLayout existingDDMStructureLayout = _persistence.findByPrimaryKey(newDDMStructureLayout.getPrimaryKey());

		Assert.assertEquals(existingDDMStructureLayout.getUuid(),
			newDDMStructureLayout.getUuid());
		Assert.assertEquals(existingDDMStructureLayout.getStructureLayoutId(),
			newDDMStructureLayout.getStructureLayoutId());
		Assert.assertEquals(existingDDMStructureLayout.getGroupId(),
			newDDMStructureLayout.getGroupId());
		Assert.assertEquals(existingDDMStructureLayout.getCompanyId(),
			newDDMStructureLayout.getCompanyId());
		Assert.assertEquals(existingDDMStructureLayout.getUserId(),
			newDDMStructureLayout.getUserId());
		Assert.assertEquals(existingDDMStructureLayout.getUserName(),
			newDDMStructureLayout.getUserName());
		Assert.assertEquals(Time.getShortTimestamp(
				existingDDMStructureLayout.getCreateDate()),
			Time.getShortTimestamp(newDDMStructureLayout.getCreateDate()));
		Assert.assertEquals(Time.getShortTimestamp(
				existingDDMStructureLayout.getModifiedDate()),
			Time.getShortTimestamp(newDDMStructureLayout.getModifiedDate()));
		Assert.assertEquals(existingDDMStructureLayout.getStructureVersionId(),
			newDDMStructureLayout.getStructureVersionId());
		Assert.assertEquals(existingDDMStructureLayout.getDefinition(),
			newDDMStructureLayout.getDefinition());
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
	public void testCountByStructureVersionId() throws Exception {
		_persistence.countByStructureVersionId(RandomTestUtil.nextLong());

		_persistence.countByStructureVersionId(0L);
	}

	@Test
	public void testFindByPrimaryKeyExisting() throws Exception {
		DDMStructureLayout newDDMStructureLayout = addDDMStructureLayout();

		DDMStructureLayout existingDDMStructureLayout = _persistence.findByPrimaryKey(newDDMStructureLayout.getPrimaryKey());

		Assert.assertEquals(existingDDMStructureLayout, newDDMStructureLayout);
	}

	@Test(expected = NoSuchStructureLayoutException.class)
	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		_persistence.findByPrimaryKey(pk);
	}

	@Test
	public void testFindAll() throws Exception {
		_persistence.findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			getOrderByComparator());
	}

	protected OrderByComparator<DDMStructureLayout> getOrderByComparator() {
		return OrderByComparatorFactoryUtil.create("DDMStructureLayout",
			"uuid", true, "structureLayoutId", true, "groupId", true,
			"companyId", true, "userId", true, "userName", true, "createDate",
			true, "modifiedDate", true, "structureVersionId", true,
			"definition", true);
	}

	@Test
	public void testFetchByPrimaryKeyExisting() throws Exception {
		DDMStructureLayout newDDMStructureLayout = addDDMStructureLayout();

		DDMStructureLayout existingDDMStructureLayout = _persistence.fetchByPrimaryKey(newDDMStructureLayout.getPrimaryKey());

		Assert.assertEquals(existingDDMStructureLayout, newDDMStructureLayout);
	}

	@Test
	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		DDMStructureLayout missingDDMStructureLayout = _persistence.fetchByPrimaryKey(pk);

		Assert.assertNull(missingDDMStructureLayout);
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereAllPrimaryKeysExist()
		throws Exception {
		DDMStructureLayout newDDMStructureLayout1 = addDDMStructureLayout();
		DDMStructureLayout newDDMStructureLayout2 = addDDMStructureLayout();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newDDMStructureLayout1.getPrimaryKey());
		primaryKeys.add(newDDMStructureLayout2.getPrimaryKey());

		Map<Serializable, DDMStructureLayout> ddmStructureLayouts = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(2, ddmStructureLayouts.size());
		Assert.assertEquals(newDDMStructureLayout1,
			ddmStructureLayouts.get(newDDMStructureLayout1.getPrimaryKey()));
		Assert.assertEquals(newDDMStructureLayout2,
			ddmStructureLayouts.get(newDDMStructureLayout2.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereNoPrimaryKeysExist()
		throws Exception {
		long pk1 = RandomTestUtil.nextLong();

		long pk2 = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(pk1);
		primaryKeys.add(pk2);

		Map<Serializable, DDMStructureLayout> ddmStructureLayouts = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(ddmStructureLayouts.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereSomePrimaryKeysExist()
		throws Exception {
		DDMStructureLayout newDDMStructureLayout = addDDMStructureLayout();

		long pk = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newDDMStructureLayout.getPrimaryKey());
		primaryKeys.add(pk);

		Map<Serializable, DDMStructureLayout> ddmStructureLayouts = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, ddmStructureLayouts.size());
		Assert.assertEquals(newDDMStructureLayout,
			ddmStructureLayouts.get(newDDMStructureLayout.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithNoPrimaryKeys()
		throws Exception {
		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		Map<Serializable, DDMStructureLayout> ddmStructureLayouts = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(ddmStructureLayouts.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithOnePrimaryKey()
		throws Exception {
		DDMStructureLayout newDDMStructureLayout = addDDMStructureLayout();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newDDMStructureLayout.getPrimaryKey());

		Map<Serializable, DDMStructureLayout> ddmStructureLayouts = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, ddmStructureLayouts.size());
		Assert.assertEquals(newDDMStructureLayout,
			ddmStructureLayouts.get(newDDMStructureLayout.getPrimaryKey()));
	}

	@Test
	public void testActionableDynamicQuery() throws Exception {
		final IntegerWrapper count = new IntegerWrapper();

		ActionableDynamicQuery actionableDynamicQuery = DDMStructureLayoutLocalServiceUtil.getActionableDynamicQuery();

		actionableDynamicQuery.setPerformActionMethod(new ActionableDynamicQuery.PerformActionMethod() {
				@Override
				public void performAction(Object object) {
					DDMStructureLayout ddmStructureLayout = (DDMStructureLayout)object;

					Assert.assertNotNull(ddmStructureLayout);

					count.increment();
				}
			});

		actionableDynamicQuery.performActions();

		Assert.assertEquals(count.getValue(), _persistence.countAll());
	}

	@Test
	public void testDynamicQueryByPrimaryKeyExisting()
		throws Exception {
		DDMStructureLayout newDDMStructureLayout = addDDMStructureLayout();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(DDMStructureLayout.class,
				_dynamicQueryClassLoader);

		dynamicQuery.add(RestrictionsFactoryUtil.eq("structureLayoutId",
				newDDMStructureLayout.getStructureLayoutId()));

		List<DDMStructureLayout> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		DDMStructureLayout existingDDMStructureLayout = result.get(0);

		Assert.assertEquals(existingDDMStructureLayout, newDDMStructureLayout);
	}

	@Test
	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(DDMStructureLayout.class,
				_dynamicQueryClassLoader);

		dynamicQuery.add(RestrictionsFactoryUtil.eq("structureLayoutId",
				RandomTestUtil.nextLong()));

		List<DDMStructureLayout> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testDynamicQueryByProjectionExisting()
		throws Exception {
		DDMStructureLayout newDDMStructureLayout = addDDMStructureLayout();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(DDMStructureLayout.class,
				_dynamicQueryClassLoader);

		dynamicQuery.setProjection(ProjectionFactoryUtil.property(
				"structureLayoutId"));

		Object newStructureLayoutId = newDDMStructureLayout.getStructureLayoutId();

		dynamicQuery.add(RestrictionsFactoryUtil.in("structureLayoutId",
				new Object[] { newStructureLayoutId }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		Object existingStructureLayoutId = result.get(0);

		Assert.assertEquals(existingStructureLayoutId, newStructureLayoutId);
	}

	@Test
	public void testDynamicQueryByProjectionMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(DDMStructureLayout.class,
				_dynamicQueryClassLoader);

		dynamicQuery.setProjection(ProjectionFactoryUtil.property(
				"structureLayoutId"));

		dynamicQuery.add(RestrictionsFactoryUtil.in("structureLayoutId",
				new Object[] { RandomTestUtil.nextLong() }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testResetOriginalValues() throws Exception {
		DDMStructureLayout newDDMStructureLayout = addDDMStructureLayout();

		_persistence.clearCache();

		DDMStructureLayout existingDDMStructureLayout = _persistence.findByPrimaryKey(newDDMStructureLayout.getPrimaryKey());

		Assert.assertTrue(Validator.equals(
				existingDDMStructureLayout.getUuid(),
				ReflectionTestUtil.invoke(existingDDMStructureLayout,
					"getOriginalUuid", new Class<?>[0])));
		Assert.assertEquals(existingDDMStructureLayout.getGroupId(),
			ReflectionTestUtil.invoke(existingDDMStructureLayout,
				"getOriginalGroupId", new Class<?>[0]));

		Assert.assertEquals(existingDDMStructureLayout.getStructureVersionId(),
			ReflectionTestUtil.invoke(existingDDMStructureLayout,
				"getOriginalStructureVersionId", new Class<?>[0]));
	}

	protected DDMStructureLayout addDDMStructureLayout()
		throws Exception {
		long pk = RandomTestUtil.nextLong();

		DDMStructureLayout ddmStructureLayout = _persistence.create(pk);

		ddmStructureLayout.setUuid(RandomTestUtil.randomString());

		ddmStructureLayout.setGroupId(RandomTestUtil.nextLong());

		ddmStructureLayout.setCompanyId(RandomTestUtil.nextLong());

		ddmStructureLayout.setUserId(RandomTestUtil.nextLong());

		ddmStructureLayout.setUserName(RandomTestUtil.randomString());

		ddmStructureLayout.setCreateDate(RandomTestUtil.nextDate());

		ddmStructureLayout.setModifiedDate(RandomTestUtil.nextDate());

		ddmStructureLayout.setStructureVersionId(RandomTestUtil.nextLong());

		ddmStructureLayout.setDefinition(RandomTestUtil.randomString());

		_ddmStructureLayouts.add(_persistence.update(ddmStructureLayout));

		return ddmStructureLayout;
	}

	private List<DDMStructureLayout> _ddmStructureLayouts = new ArrayList<DDMStructureLayout>();
	private DDMStructureLayoutPersistence _persistence;
	private ClassLoader _dynamicQueryClassLoader;
}