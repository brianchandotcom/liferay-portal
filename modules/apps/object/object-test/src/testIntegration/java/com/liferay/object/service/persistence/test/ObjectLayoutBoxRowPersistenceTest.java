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

package com.liferay.object.service.persistence.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.object.exception.NoSuchObjectLayoutBoxRowException;
import com.liferay.object.model.ObjectLayoutBoxRow;
import com.liferay.object.service.ObjectLayoutBoxRowLocalServiceUtil;
import com.liferay.object.service.persistence.ObjectLayoutBoxRowPersistence;
import com.liferay.object.service.persistence.ObjectLayoutBoxRowUtil;
import com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQueryFactoryUtil;
import com.liferay.portal.kernel.dao.orm.ProjectionFactoryUtil;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.RestrictionsFactoryUtil;
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
public class ObjectLayoutBoxRowPersistenceTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(), PersistenceTestRule.INSTANCE,
			new TransactionalTestRule(
				Propagation.REQUIRED, "com.liferay.object.service"));

	@Before
	public void setUp() {
		_persistence = ObjectLayoutBoxRowUtil.getPersistence();

		Class<?> clazz = _persistence.getClass();

		_dynamicQueryClassLoader = clazz.getClassLoader();
	}

	@After
	public void tearDown() throws Exception {
		Iterator<ObjectLayoutBoxRow> iterator = _objectLayoutBoxRows.iterator();

		while (iterator.hasNext()) {
			_persistence.remove(iterator.next());

			iterator.remove();
		}
	}

	@Test
	public void testCreate() throws Exception {
		long pk = RandomTestUtil.nextLong();

		ObjectLayoutBoxRow objectLayoutBoxRow = _persistence.create(pk);

		Assert.assertNotNull(objectLayoutBoxRow);

		Assert.assertEquals(objectLayoutBoxRow.getPrimaryKey(), pk);
	}

	@Test
	public void testRemove() throws Exception {
		ObjectLayoutBoxRow newObjectLayoutBoxRow = addObjectLayoutBoxRow();

		_persistence.remove(newObjectLayoutBoxRow);

		ObjectLayoutBoxRow existingObjectLayoutBoxRow =
			_persistence.fetchByPrimaryKey(
				newObjectLayoutBoxRow.getPrimaryKey());

		Assert.assertNull(existingObjectLayoutBoxRow);
	}

	@Test
	public void testUpdateNew() throws Exception {
		addObjectLayoutBoxRow();
	}

	@Test
	public void testUpdateExisting() throws Exception {
		long pk = RandomTestUtil.nextLong();

		ObjectLayoutBoxRow newObjectLayoutBoxRow = _persistence.create(pk);

		newObjectLayoutBoxRow.setMvccVersion(RandomTestUtil.nextLong());

		newObjectLayoutBoxRow.setUuid(RandomTestUtil.randomString());

		newObjectLayoutBoxRow.setCompanyId(RandomTestUtil.nextLong());

		newObjectLayoutBoxRow.setUserId(RandomTestUtil.nextLong());

		newObjectLayoutBoxRow.setUserName(RandomTestUtil.randomString());

		newObjectLayoutBoxRow.setCreateDate(RandomTestUtil.nextDate());

		newObjectLayoutBoxRow.setModifiedDate(RandomTestUtil.nextDate());

		newObjectLayoutBoxRow.setObjectLayoutBoxId(RandomTestUtil.nextLong());

		newObjectLayoutBoxRow.setPriority(RandomTestUtil.nextInt());

		_objectLayoutBoxRows.add(_persistence.update(newObjectLayoutBoxRow));

		ObjectLayoutBoxRow existingObjectLayoutBoxRow =
			_persistence.findByPrimaryKey(
				newObjectLayoutBoxRow.getPrimaryKey());

		Assert.assertEquals(
			existingObjectLayoutBoxRow.getMvccVersion(),
			newObjectLayoutBoxRow.getMvccVersion());
		Assert.assertEquals(
			existingObjectLayoutBoxRow.getUuid(),
			newObjectLayoutBoxRow.getUuid());
		Assert.assertEquals(
			existingObjectLayoutBoxRow.getObjectLayoutBoxRowId(),
			newObjectLayoutBoxRow.getObjectLayoutBoxRowId());
		Assert.assertEquals(
			existingObjectLayoutBoxRow.getCompanyId(),
			newObjectLayoutBoxRow.getCompanyId());
		Assert.assertEquals(
			existingObjectLayoutBoxRow.getUserId(),
			newObjectLayoutBoxRow.getUserId());
		Assert.assertEquals(
			existingObjectLayoutBoxRow.getUserName(),
			newObjectLayoutBoxRow.getUserName());
		Assert.assertEquals(
			Time.getShortTimestamp(existingObjectLayoutBoxRow.getCreateDate()),
			Time.getShortTimestamp(newObjectLayoutBoxRow.getCreateDate()));
		Assert.assertEquals(
			Time.getShortTimestamp(
				existingObjectLayoutBoxRow.getModifiedDate()),
			Time.getShortTimestamp(newObjectLayoutBoxRow.getModifiedDate()));
		Assert.assertEquals(
			existingObjectLayoutBoxRow.getObjectLayoutBoxId(),
			newObjectLayoutBoxRow.getObjectLayoutBoxId());
		Assert.assertEquals(
			existingObjectLayoutBoxRow.getPriority(),
			newObjectLayoutBoxRow.getPriority());
	}

	@Test
	public void testCountByUuid() throws Exception {
		_persistence.countByUuid("");

		_persistence.countByUuid("null");

		_persistence.countByUuid((String)null);
	}

	@Test
	public void testCountByUuid_C() throws Exception {
		_persistence.countByUuid_C("", RandomTestUtil.nextLong());

		_persistence.countByUuid_C("null", 0L);

		_persistence.countByUuid_C((String)null, 0L);
	}

	@Test
	public void testCountByObjectLayoutBoxId() throws Exception {
		_persistence.countByObjectLayoutBoxId(RandomTestUtil.nextLong());

		_persistence.countByObjectLayoutBoxId(0L);
	}

	@Test
	public void testFindByPrimaryKeyExisting() throws Exception {
		ObjectLayoutBoxRow newObjectLayoutBoxRow = addObjectLayoutBoxRow();

		ObjectLayoutBoxRow existingObjectLayoutBoxRow =
			_persistence.findByPrimaryKey(
				newObjectLayoutBoxRow.getPrimaryKey());

		Assert.assertEquals(existingObjectLayoutBoxRow, newObjectLayoutBoxRow);
	}

	@Test(expected = NoSuchObjectLayoutBoxRowException.class)
	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		_persistence.findByPrimaryKey(pk);
	}

	@Test
	public void testFindAll() throws Exception {
		_persistence.findAll(
			QueryUtil.ALL_POS, QueryUtil.ALL_POS, getOrderByComparator());
	}

	protected OrderByComparator<ObjectLayoutBoxRow> getOrderByComparator() {
		return OrderByComparatorFactoryUtil.create(
			"ObjectLayoutBoxRow", "mvccVersion", true, "uuid", true,
			"objectLayoutBoxRowId", true, "companyId", true, "userId", true,
			"userName", true, "createDate", true, "modifiedDate", true,
			"objectLayoutBoxId", true, "priority", true);
	}

	@Test
	public void testFetchByPrimaryKeyExisting() throws Exception {
		ObjectLayoutBoxRow newObjectLayoutBoxRow = addObjectLayoutBoxRow();

		ObjectLayoutBoxRow existingObjectLayoutBoxRow =
			_persistence.fetchByPrimaryKey(
				newObjectLayoutBoxRow.getPrimaryKey());

		Assert.assertEquals(existingObjectLayoutBoxRow, newObjectLayoutBoxRow);
	}

	@Test
	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		ObjectLayoutBoxRow missingObjectLayoutBoxRow =
			_persistence.fetchByPrimaryKey(pk);

		Assert.assertNull(missingObjectLayoutBoxRow);
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereAllPrimaryKeysExist()
		throws Exception {

		ObjectLayoutBoxRow newObjectLayoutBoxRow1 = addObjectLayoutBoxRow();
		ObjectLayoutBoxRow newObjectLayoutBoxRow2 = addObjectLayoutBoxRow();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newObjectLayoutBoxRow1.getPrimaryKey());
		primaryKeys.add(newObjectLayoutBoxRow2.getPrimaryKey());

		Map<Serializable, ObjectLayoutBoxRow> objectLayoutBoxRows =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(2, objectLayoutBoxRows.size());
		Assert.assertEquals(
			newObjectLayoutBoxRow1,
			objectLayoutBoxRows.get(newObjectLayoutBoxRow1.getPrimaryKey()));
		Assert.assertEquals(
			newObjectLayoutBoxRow2,
			objectLayoutBoxRows.get(newObjectLayoutBoxRow2.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereNoPrimaryKeysExist()
		throws Exception {

		long pk1 = RandomTestUtil.nextLong();

		long pk2 = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(pk1);
		primaryKeys.add(pk2);

		Map<Serializable, ObjectLayoutBoxRow> objectLayoutBoxRows =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(objectLayoutBoxRows.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereSomePrimaryKeysExist()
		throws Exception {

		ObjectLayoutBoxRow newObjectLayoutBoxRow = addObjectLayoutBoxRow();

		long pk = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newObjectLayoutBoxRow.getPrimaryKey());
		primaryKeys.add(pk);

		Map<Serializable, ObjectLayoutBoxRow> objectLayoutBoxRows =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, objectLayoutBoxRows.size());
		Assert.assertEquals(
			newObjectLayoutBoxRow,
			objectLayoutBoxRows.get(newObjectLayoutBoxRow.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithNoPrimaryKeys() throws Exception {
		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		Map<Serializable, ObjectLayoutBoxRow> objectLayoutBoxRows =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(objectLayoutBoxRows.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithOnePrimaryKey() throws Exception {
		ObjectLayoutBoxRow newObjectLayoutBoxRow = addObjectLayoutBoxRow();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newObjectLayoutBoxRow.getPrimaryKey());

		Map<Serializable, ObjectLayoutBoxRow> objectLayoutBoxRows =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, objectLayoutBoxRows.size());
		Assert.assertEquals(
			newObjectLayoutBoxRow,
			objectLayoutBoxRows.get(newObjectLayoutBoxRow.getPrimaryKey()));
	}

	@Test
	public void testActionableDynamicQuery() throws Exception {
		final IntegerWrapper count = new IntegerWrapper();

		ActionableDynamicQuery actionableDynamicQuery =
			ObjectLayoutBoxRowLocalServiceUtil.getActionableDynamicQuery();

		actionableDynamicQuery.setPerformActionMethod(
			new ActionableDynamicQuery.PerformActionMethod
				<ObjectLayoutBoxRow>() {

				@Override
				public void performAction(
					ObjectLayoutBoxRow objectLayoutBoxRow) {

					Assert.assertNotNull(objectLayoutBoxRow);

					count.increment();
				}

			});

		actionableDynamicQuery.performActions();

		Assert.assertEquals(count.getValue(), _persistence.countAll());
	}

	@Test
	public void testDynamicQueryByPrimaryKeyExisting() throws Exception {
		ObjectLayoutBoxRow newObjectLayoutBoxRow = addObjectLayoutBoxRow();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			ObjectLayoutBoxRow.class, _dynamicQueryClassLoader);

		dynamicQuery.add(
			RestrictionsFactoryUtil.eq(
				"objectLayoutBoxRowId",
				newObjectLayoutBoxRow.getObjectLayoutBoxRowId()));

		List<ObjectLayoutBoxRow> result = _persistence.findWithDynamicQuery(
			dynamicQuery);

		Assert.assertEquals(1, result.size());

		ObjectLayoutBoxRow existingObjectLayoutBoxRow = result.get(0);

		Assert.assertEquals(existingObjectLayoutBoxRow, newObjectLayoutBoxRow);
	}

	@Test
	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			ObjectLayoutBoxRow.class, _dynamicQueryClassLoader);

		dynamicQuery.add(
			RestrictionsFactoryUtil.eq(
				"objectLayoutBoxRowId", RandomTestUtil.nextLong()));

		List<ObjectLayoutBoxRow> result = _persistence.findWithDynamicQuery(
			dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testDynamicQueryByProjectionExisting() throws Exception {
		ObjectLayoutBoxRow newObjectLayoutBoxRow = addObjectLayoutBoxRow();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			ObjectLayoutBoxRow.class, _dynamicQueryClassLoader);

		dynamicQuery.setProjection(
			ProjectionFactoryUtil.property("objectLayoutBoxRowId"));

		Object newObjectLayoutBoxRowId =
			newObjectLayoutBoxRow.getObjectLayoutBoxRowId();

		dynamicQuery.add(
			RestrictionsFactoryUtil.in(
				"objectLayoutBoxRowId",
				new Object[] {newObjectLayoutBoxRowId}));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		Object existingObjectLayoutBoxRowId = result.get(0);

		Assert.assertEquals(
			existingObjectLayoutBoxRowId, newObjectLayoutBoxRowId);
	}

	@Test
	public void testDynamicQueryByProjectionMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			ObjectLayoutBoxRow.class, _dynamicQueryClassLoader);

		dynamicQuery.setProjection(
			ProjectionFactoryUtil.property("objectLayoutBoxRowId"));

		dynamicQuery.add(
			RestrictionsFactoryUtil.in(
				"objectLayoutBoxRowId",
				new Object[] {RandomTestUtil.nextLong()}));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	protected ObjectLayoutBoxRow addObjectLayoutBoxRow() throws Exception {
		long pk = RandomTestUtil.nextLong();

		ObjectLayoutBoxRow objectLayoutBoxRow = _persistence.create(pk);

		objectLayoutBoxRow.setMvccVersion(RandomTestUtil.nextLong());

		objectLayoutBoxRow.setUuid(RandomTestUtil.randomString());

		objectLayoutBoxRow.setCompanyId(RandomTestUtil.nextLong());

		objectLayoutBoxRow.setUserId(RandomTestUtil.nextLong());

		objectLayoutBoxRow.setUserName(RandomTestUtil.randomString());

		objectLayoutBoxRow.setCreateDate(RandomTestUtil.nextDate());

		objectLayoutBoxRow.setModifiedDate(RandomTestUtil.nextDate());

		objectLayoutBoxRow.setObjectLayoutBoxId(RandomTestUtil.nextLong());

		objectLayoutBoxRow.setPriority(RandomTestUtil.nextInt());

		_objectLayoutBoxRows.add(_persistence.update(objectLayoutBoxRow));

		return objectLayoutBoxRow;
	}

	private List<ObjectLayoutBoxRow> _objectLayoutBoxRows =
		new ArrayList<ObjectLayoutBoxRow>();
	private ObjectLayoutBoxRowPersistence _persistence;
	private ClassLoader _dynamicQueryClassLoader;

}