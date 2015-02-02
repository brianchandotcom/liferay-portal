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

package com.liferay.portlet.expando.service.persistence;

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
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PersistenceTestRule;
import com.liferay.portal.util.PropsValues;

import com.liferay.portlet.expando.NoSuchValueException;
import com.liferay.portlet.expando.model.ExpandoValue;
import com.liferay.portlet.expando.service.ExpandoValueLocalServiceUtil;

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
public class ExpandoValuePersistenceTest {
	@Rule
	public final AggregateTestRule aggregateTestRule = new AggregateTestRule(new LiferayIntegrationTestRule(),
			PersistenceTestRule.INSTANCE,
			new TransactionalTestRule(Propagation.REQUIRED));

	@Before
	public void setUp() {
		_persistence = ExpandoValueUtil.getPersistence();
	}

	@After
	public void tearDown() throws Exception {
		Iterator<ExpandoValue> iterator = _expandoValues.iterator();

		while (iterator.hasNext()) {
			_persistence.remove(iterator.next());

			iterator.remove();
		}
	}

	@Test
	public void testCreate() throws Exception {
		long pk = RandomTestUtil.nextLong();

		ExpandoValue expandoValue = _persistence.create(pk);

		Assert.assertNotNull(expandoValue);

		Assert.assertEquals(expandoValue.getPrimaryKey(), pk);
	}

	@Test
	public void testRemove() throws Exception {
		ExpandoValue newExpandoValue = addExpandoValue();

		_persistence.remove(newExpandoValue);

		ExpandoValue existingExpandoValue = _persistence.fetchByPrimaryKey(newExpandoValue.getPrimaryKey());

		Assert.assertNull(existingExpandoValue);
	}

	@Test
	public void testUpdateNew() throws Exception {
		addExpandoValue();
	}

	@Test
	public void testUpdateExisting() throws Exception {
		long pk = RandomTestUtil.nextLong();

		ExpandoValue newExpandoValue = _persistence.create(pk);

		newExpandoValue.setCompanyId(RandomTestUtil.nextLong());

		newExpandoValue.setTableId(RandomTestUtil.nextLong());

		newExpandoValue.setColumnId(RandomTestUtil.nextLong());

		newExpandoValue.setRowId(RandomTestUtil.nextLong());

		newExpandoValue.setClassNameId(RandomTestUtil.nextLong());

		newExpandoValue.setClassPK(RandomTestUtil.nextLong());

		newExpandoValue.setData(RandomTestUtil.randomString());

		_expandoValues.add(_persistence.update(newExpandoValue));

		ExpandoValue existingExpandoValue = _persistence.findByPrimaryKey(newExpandoValue.getPrimaryKey());

		Assert.assertEquals(existingExpandoValue.getValueId(),
			newExpandoValue.getValueId());
		Assert.assertEquals(existingExpandoValue.getCompanyId(),
			newExpandoValue.getCompanyId());
		Assert.assertEquals(existingExpandoValue.getTableId(),
			newExpandoValue.getTableId());
		Assert.assertEquals(existingExpandoValue.getColumnId(),
			newExpandoValue.getColumnId());
		Assert.assertEquals(existingExpandoValue.getRowId(),
			newExpandoValue.getRowId());
		Assert.assertEquals(existingExpandoValue.getClassNameId(),
			newExpandoValue.getClassNameId());
		Assert.assertEquals(existingExpandoValue.getClassPK(),
			newExpandoValue.getClassPK());
		Assert.assertEquals(existingExpandoValue.getData(),
			newExpandoValue.getData());
	}

	@Test
	public void testCountByTableId() {
		try {
			_persistence.countByTableId(RandomTestUtil.nextLong());

			_persistence.countByTableId(0L);
		}
		catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void testCountByColumnId() {
		try {
			_persistence.countByColumnId(RandomTestUtil.nextLong());

			_persistence.countByColumnId(0L);
		}
		catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void testCountByRowId() {
		try {
			_persistence.countByRowId(RandomTestUtil.nextLong());

			_persistence.countByRowId(0L);
		}
		catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void testCountByT_C() {
		try {
			_persistence.countByT_C(RandomTestUtil.nextLong(),
				RandomTestUtil.nextLong());

			_persistence.countByT_C(0L, 0L);
		}
		catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void testCountByT_R() {
		try {
			_persistence.countByT_R(RandomTestUtil.nextLong(),
				RandomTestUtil.nextLong());

			_persistence.countByT_R(0L, 0L);
		}
		catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void testCountByT_CPK() {
		try {
			_persistence.countByT_CPK(RandomTestUtil.nextLong(),
				RandomTestUtil.nextLong());

			_persistence.countByT_CPK(0L, 0L);
		}
		catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void testCountByC_R() {
		try {
			_persistence.countByC_R(RandomTestUtil.nextLong(),
				RandomTestUtil.nextLong());

			_persistence.countByC_R(0L, 0L);
		}
		catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void testCountByC_C() {
		try {
			_persistence.countByC_C(RandomTestUtil.nextLong(),
				RandomTestUtil.nextLong());

			_persistence.countByC_C(0L, 0L);
		}
		catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void testCountByT_C_C() {
		try {
			_persistence.countByT_C_C(RandomTestUtil.nextLong(),
				RandomTestUtil.nextLong(), RandomTestUtil.nextLong());

			_persistence.countByT_C_C(0L, 0L, 0L);
		}
		catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void testCountByT_C_D() {
		try {
			_persistence.countByT_C_D(RandomTestUtil.nextLong(),
				RandomTestUtil.nextLong(), StringPool.BLANK);

			_persistence.countByT_C_D(0L, 0L, StringPool.NULL);

			_persistence.countByT_C_D(0L, 0L, (String)null);
		}
		catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void testFindByPrimaryKeyExisting() throws Exception {
		ExpandoValue newExpandoValue = addExpandoValue();

		ExpandoValue existingExpandoValue = _persistence.findByPrimaryKey(newExpandoValue.getPrimaryKey());

		Assert.assertEquals(existingExpandoValue, newExpandoValue);
	}

	@Test
	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		try {
			_persistence.findByPrimaryKey(pk);

			Assert.fail("Missing entity did not throw NoSuchValueException");
		}
		catch (NoSuchValueException nsee) {
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

	protected OrderByComparator<ExpandoValue> getOrderByComparator() {
		return OrderByComparatorFactoryUtil.create("ExpandoValue", "valueId",
			true, "companyId", true, "tableId", true, "columnId", true,
			"rowId", true, "classNameId", true, "classPK", true, "data", true);
	}

	@Test
	public void testFetchByPrimaryKeyExisting() throws Exception {
		ExpandoValue newExpandoValue = addExpandoValue();

		ExpandoValue existingExpandoValue = _persistence.fetchByPrimaryKey(newExpandoValue.getPrimaryKey());

		Assert.assertEquals(existingExpandoValue, newExpandoValue);
	}

	@Test
	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		ExpandoValue missingExpandoValue = _persistence.fetchByPrimaryKey(pk);

		Assert.assertNull(missingExpandoValue);
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereAllPrimaryKeysExist()
		throws Exception {
		ExpandoValue newExpandoValue1 = addExpandoValue();
		ExpandoValue newExpandoValue2 = addExpandoValue();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newExpandoValue1.getPrimaryKey());
		primaryKeys.add(newExpandoValue2.getPrimaryKey());

		Map<Serializable, ExpandoValue> expandoValues = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(2, expandoValues.size());
		Assert.assertEquals(newExpandoValue1,
			expandoValues.get(newExpandoValue1.getPrimaryKey()));
		Assert.assertEquals(newExpandoValue2,
			expandoValues.get(newExpandoValue2.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereNoPrimaryKeysExist()
		throws Exception {
		long pk1 = RandomTestUtil.nextLong();

		long pk2 = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(pk1);
		primaryKeys.add(pk2);

		Map<Serializable, ExpandoValue> expandoValues = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(expandoValues.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereSomePrimaryKeysExist()
		throws Exception {
		ExpandoValue newExpandoValue = addExpandoValue();

		long pk = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newExpandoValue.getPrimaryKey());
		primaryKeys.add(pk);

		Map<Serializable, ExpandoValue> expandoValues = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, expandoValues.size());
		Assert.assertEquals(newExpandoValue,
			expandoValues.get(newExpandoValue.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithNoPrimaryKeys()
		throws Exception {
		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		Map<Serializable, ExpandoValue> expandoValues = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(expandoValues.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithOnePrimaryKey()
		throws Exception {
		ExpandoValue newExpandoValue = addExpandoValue();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newExpandoValue.getPrimaryKey());

		Map<Serializable, ExpandoValue> expandoValues = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, expandoValues.size());
		Assert.assertEquals(newExpandoValue,
			expandoValues.get(newExpandoValue.getPrimaryKey()));
	}

	@Test
	public void testActionableDynamicQuery() throws Exception {
		final IntegerWrapper count = new IntegerWrapper();

		ActionableDynamicQuery actionableDynamicQuery = ExpandoValueLocalServiceUtil.getActionableDynamicQuery();

		actionableDynamicQuery.setPerformActionMethod(new ActionableDynamicQuery.PerformActionMethod() {
				@Override
				public void performAction(Object object) {
					ExpandoValue expandoValue = (ExpandoValue)object;

					Assert.assertNotNull(expandoValue);

					count.increment();
				}
			});

		actionableDynamicQuery.performActions();

		Assert.assertEquals(count.getValue(), _persistence.countAll());
	}

	@Test
	public void testDynamicQueryByPrimaryKeyExisting()
		throws Exception {
		ExpandoValue newExpandoValue = addExpandoValue();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(ExpandoValue.class,
				ExpandoValue.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("valueId",
				newExpandoValue.getValueId()));

		List<ExpandoValue> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		ExpandoValue existingExpandoValue = result.get(0);

		Assert.assertEquals(existingExpandoValue, newExpandoValue);
	}

	@Test
	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(ExpandoValue.class,
				ExpandoValue.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("valueId",
				RandomTestUtil.nextLong()));

		List<ExpandoValue> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testDynamicQueryByProjectionExisting()
		throws Exception {
		ExpandoValue newExpandoValue = addExpandoValue();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(ExpandoValue.class,
				ExpandoValue.class.getClassLoader());

		dynamicQuery.setProjection(ProjectionFactoryUtil.property("valueId"));

		Object newValueId = newExpandoValue.getValueId();

		dynamicQuery.add(RestrictionsFactoryUtil.in("valueId",
				new Object[] { newValueId }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		Object existingValueId = result.get(0);

		Assert.assertEquals(existingValueId, newValueId);
	}

	@Test
	public void testDynamicQueryByProjectionMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(ExpandoValue.class,
				ExpandoValue.class.getClassLoader());

		dynamicQuery.setProjection(ProjectionFactoryUtil.property("valueId"));

		dynamicQuery.add(RestrictionsFactoryUtil.in("valueId",
				new Object[] { RandomTestUtil.nextLong() }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testResetOriginalValues() throws Exception {
		if (!PropsValues.HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE) {
			return;
		}

		ExpandoValue newExpandoValue = addExpandoValue();

		_persistence.clearCache();

		ExpandoValue existingExpandoValue = _persistence.findByPrimaryKey(newExpandoValue.getPrimaryKey());

		Assert.assertEquals(existingExpandoValue.getColumnId(),
			ReflectionTestUtil.invoke(existingExpandoValue,
				"getOriginalColumnId", new Class<?>[0]));
		Assert.assertEquals(existingExpandoValue.getRowId(),
			ReflectionTestUtil.invoke(existingExpandoValue, "getOriginalRowId",
				new Class<?>[0]));

		Assert.assertEquals(existingExpandoValue.getTableId(),
			ReflectionTestUtil.invoke(existingExpandoValue,
				"getOriginalTableId", new Class<?>[0]));
		Assert.assertEquals(existingExpandoValue.getColumnId(),
			ReflectionTestUtil.invoke(existingExpandoValue,
				"getOriginalColumnId", new Class<?>[0]));
		Assert.assertEquals(existingExpandoValue.getClassPK(),
			ReflectionTestUtil.invoke(existingExpandoValue,
				"getOriginalClassPK", new Class<?>[0]));
	}

	protected ExpandoValue addExpandoValue() throws Exception {
		long pk = RandomTestUtil.nextLong();

		ExpandoValue expandoValue = _persistence.create(pk);

		expandoValue.setCompanyId(RandomTestUtil.nextLong());

		expandoValue.setTableId(RandomTestUtil.nextLong());

		expandoValue.setColumnId(RandomTestUtil.nextLong());

		expandoValue.setRowId(RandomTestUtil.nextLong());

		expandoValue.setClassNameId(RandomTestUtil.nextLong());

		expandoValue.setClassPK(RandomTestUtil.nextLong());

		expandoValue.setData(RandomTestUtil.randomString());

		_expandoValues.add(_persistence.update(expandoValue));

		return expandoValue;
	}

	private List<ExpandoValue> _expandoValues = new ArrayList<ExpandoValue>();
	private ExpandoValuePersistence _persistence;
}