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
import com.liferay.object.exception.NoSuchObjectLayoutBoxColumnException;
import com.liferay.object.model.ObjectLayoutBoxColumn;
import com.liferay.object.service.ObjectLayoutBoxColumnLocalServiceUtil;
import com.liferay.object.service.persistence.ObjectLayoutBoxColumnPersistence;
import com.liferay.object.service.persistence.ObjectLayoutBoxColumnUtil;
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
public class ObjectLayoutBoxColumnPersistenceTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(), PersistenceTestRule.INSTANCE,
			new TransactionalTestRule(
				Propagation.REQUIRED, "com.liferay.object.service"));

	@Before
	public void setUp() {
		_persistence = ObjectLayoutBoxColumnUtil.getPersistence();

		Class<?> clazz = _persistence.getClass();

		_dynamicQueryClassLoader = clazz.getClassLoader();
	}

	@After
	public void tearDown() throws Exception {
		Iterator<ObjectLayoutBoxColumn> iterator =
			_objectLayoutBoxColumns.iterator();

		while (iterator.hasNext()) {
			_persistence.remove(iterator.next());

			iterator.remove();
		}
	}

	@Test
	public void testCreate() throws Exception {
		long pk = RandomTestUtil.nextLong();

		ObjectLayoutBoxColumn objectLayoutBoxColumn = _persistence.create(pk);

		Assert.assertNotNull(objectLayoutBoxColumn);

		Assert.assertEquals(objectLayoutBoxColumn.getPrimaryKey(), pk);
	}

	@Test
	public void testRemove() throws Exception {
		ObjectLayoutBoxColumn newObjectLayoutBoxColumn =
			addObjectLayoutBoxColumn();

		_persistence.remove(newObjectLayoutBoxColumn);

		ObjectLayoutBoxColumn existingObjectLayoutBoxColumn =
			_persistence.fetchByPrimaryKey(
				newObjectLayoutBoxColumn.getPrimaryKey());

		Assert.assertNull(existingObjectLayoutBoxColumn);
	}

	@Test
	public void testUpdateNew() throws Exception {
		addObjectLayoutBoxColumn();
	}

	@Test
	public void testUpdateExisting() throws Exception {
		long pk = RandomTestUtil.nextLong();

		ObjectLayoutBoxColumn newObjectLayoutBoxColumn = _persistence.create(
			pk);

		newObjectLayoutBoxColumn.setMvccVersion(RandomTestUtil.nextLong());

		newObjectLayoutBoxColumn.setUuid(RandomTestUtil.randomString());

		newObjectLayoutBoxColumn.setCompanyId(RandomTestUtil.nextLong());

		newObjectLayoutBoxColumn.setUserId(RandomTestUtil.nextLong());

		newObjectLayoutBoxColumn.setUserName(RandomTestUtil.randomString());

		newObjectLayoutBoxColumn.setCreateDate(RandomTestUtil.nextDate());

		newObjectLayoutBoxColumn.setModifiedDate(RandomTestUtil.nextDate());

		newObjectLayoutBoxColumn.setObjectFieldId(RandomTestUtil.nextLong());

		newObjectLayoutBoxColumn.setObjectLayoutBoxRowId(
			RandomTestUtil.nextLong());

		newObjectLayoutBoxColumn.setOrder(RandomTestUtil.nextInt());

		_objectLayoutBoxColumns.add(
			_persistence.update(newObjectLayoutBoxColumn));

		ObjectLayoutBoxColumn existingObjectLayoutBoxColumn =
			_persistence.findByPrimaryKey(
				newObjectLayoutBoxColumn.getPrimaryKey());

		Assert.assertEquals(
			existingObjectLayoutBoxColumn.getMvccVersion(),
			newObjectLayoutBoxColumn.getMvccVersion());
		Assert.assertEquals(
			existingObjectLayoutBoxColumn.getUuid(),
			newObjectLayoutBoxColumn.getUuid());
		Assert.assertEquals(
			existingObjectLayoutBoxColumn.getObjectLayoutBoxColumnId(),
			newObjectLayoutBoxColumn.getObjectLayoutBoxColumnId());
		Assert.assertEquals(
			existingObjectLayoutBoxColumn.getCompanyId(),
			newObjectLayoutBoxColumn.getCompanyId());
		Assert.assertEquals(
			existingObjectLayoutBoxColumn.getUserId(),
			newObjectLayoutBoxColumn.getUserId());
		Assert.assertEquals(
			existingObjectLayoutBoxColumn.getUserName(),
			newObjectLayoutBoxColumn.getUserName());
		Assert.assertEquals(
			Time.getShortTimestamp(
				existingObjectLayoutBoxColumn.getCreateDate()),
			Time.getShortTimestamp(newObjectLayoutBoxColumn.getCreateDate()));
		Assert.assertEquals(
			Time.getShortTimestamp(
				existingObjectLayoutBoxColumn.getModifiedDate()),
			Time.getShortTimestamp(newObjectLayoutBoxColumn.getModifiedDate()));
		Assert.assertEquals(
			existingObjectLayoutBoxColumn.getObjectFieldId(),
			newObjectLayoutBoxColumn.getObjectFieldId());
		Assert.assertEquals(
			existingObjectLayoutBoxColumn.getObjectLayoutBoxRowId(),
			newObjectLayoutBoxColumn.getObjectLayoutBoxRowId());
		Assert.assertEquals(
			existingObjectLayoutBoxColumn.getOrder(),
			newObjectLayoutBoxColumn.getOrder());
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
	public void testFindByPrimaryKeyExisting() throws Exception {
		ObjectLayoutBoxColumn newObjectLayoutBoxColumn =
			addObjectLayoutBoxColumn();

		ObjectLayoutBoxColumn existingObjectLayoutBoxColumn =
			_persistence.findByPrimaryKey(
				newObjectLayoutBoxColumn.getPrimaryKey());

		Assert.assertEquals(
			existingObjectLayoutBoxColumn, newObjectLayoutBoxColumn);
	}

	@Test(expected = NoSuchObjectLayoutBoxColumnException.class)
	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		_persistence.findByPrimaryKey(pk);
	}

	@Test
	public void testFindAll() throws Exception {
		_persistence.findAll(
			QueryUtil.ALL_POS, QueryUtil.ALL_POS, getOrderByComparator());
	}

	protected OrderByComparator<ObjectLayoutBoxColumn> getOrderByComparator() {
		return OrderByComparatorFactoryUtil.create(
			"ObjectLayoutBoxColumn", "mvccVersion", true, "uuid", true,
			"objectLayoutBoxColumnId", true, "companyId", true, "userId", true,
			"userName", true, "createDate", true, "modifiedDate", true,
			"objectFieldId", true, "objectLayoutBoxRowId", true, "order", true);
	}

	@Test
	public void testFetchByPrimaryKeyExisting() throws Exception {
		ObjectLayoutBoxColumn newObjectLayoutBoxColumn =
			addObjectLayoutBoxColumn();

		ObjectLayoutBoxColumn existingObjectLayoutBoxColumn =
			_persistence.fetchByPrimaryKey(
				newObjectLayoutBoxColumn.getPrimaryKey());

		Assert.assertEquals(
			existingObjectLayoutBoxColumn, newObjectLayoutBoxColumn);
	}

	@Test
	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		ObjectLayoutBoxColumn missingObjectLayoutBoxColumn =
			_persistence.fetchByPrimaryKey(pk);

		Assert.assertNull(missingObjectLayoutBoxColumn);
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereAllPrimaryKeysExist()
		throws Exception {

		ObjectLayoutBoxColumn newObjectLayoutBoxColumn1 =
			addObjectLayoutBoxColumn();
		ObjectLayoutBoxColumn newObjectLayoutBoxColumn2 =
			addObjectLayoutBoxColumn();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newObjectLayoutBoxColumn1.getPrimaryKey());
		primaryKeys.add(newObjectLayoutBoxColumn2.getPrimaryKey());

		Map<Serializable, ObjectLayoutBoxColumn> objectLayoutBoxColumns =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(2, objectLayoutBoxColumns.size());
		Assert.assertEquals(
			newObjectLayoutBoxColumn1,
			objectLayoutBoxColumns.get(
				newObjectLayoutBoxColumn1.getPrimaryKey()));
		Assert.assertEquals(
			newObjectLayoutBoxColumn2,
			objectLayoutBoxColumns.get(
				newObjectLayoutBoxColumn2.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereNoPrimaryKeysExist()
		throws Exception {

		long pk1 = RandomTestUtil.nextLong();

		long pk2 = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(pk1);
		primaryKeys.add(pk2);

		Map<Serializable, ObjectLayoutBoxColumn> objectLayoutBoxColumns =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(objectLayoutBoxColumns.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereSomePrimaryKeysExist()
		throws Exception {

		ObjectLayoutBoxColumn newObjectLayoutBoxColumn =
			addObjectLayoutBoxColumn();

		long pk = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newObjectLayoutBoxColumn.getPrimaryKey());
		primaryKeys.add(pk);

		Map<Serializable, ObjectLayoutBoxColumn> objectLayoutBoxColumns =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, objectLayoutBoxColumns.size());
		Assert.assertEquals(
			newObjectLayoutBoxColumn,
			objectLayoutBoxColumns.get(
				newObjectLayoutBoxColumn.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithNoPrimaryKeys() throws Exception {
		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		Map<Serializable, ObjectLayoutBoxColumn> objectLayoutBoxColumns =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(objectLayoutBoxColumns.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithOnePrimaryKey() throws Exception {
		ObjectLayoutBoxColumn newObjectLayoutBoxColumn =
			addObjectLayoutBoxColumn();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newObjectLayoutBoxColumn.getPrimaryKey());

		Map<Serializable, ObjectLayoutBoxColumn> objectLayoutBoxColumns =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, objectLayoutBoxColumns.size());
		Assert.assertEquals(
			newObjectLayoutBoxColumn,
			objectLayoutBoxColumns.get(
				newObjectLayoutBoxColumn.getPrimaryKey()));
	}

	@Test
	public void testActionableDynamicQuery() throws Exception {
		final IntegerWrapper count = new IntegerWrapper();

		ActionableDynamicQuery actionableDynamicQuery =
			ObjectLayoutBoxColumnLocalServiceUtil.getActionableDynamicQuery();

		actionableDynamicQuery.setPerformActionMethod(
			new ActionableDynamicQuery.PerformActionMethod
				<ObjectLayoutBoxColumn>() {

				@Override
				public void performAction(
					ObjectLayoutBoxColumn objectLayoutBoxColumn) {

					Assert.assertNotNull(objectLayoutBoxColumn);

					count.increment();
				}

			});

		actionableDynamicQuery.performActions();

		Assert.assertEquals(count.getValue(), _persistence.countAll());
	}

	@Test
	public void testDynamicQueryByPrimaryKeyExisting() throws Exception {
		ObjectLayoutBoxColumn newObjectLayoutBoxColumn =
			addObjectLayoutBoxColumn();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			ObjectLayoutBoxColumn.class, _dynamicQueryClassLoader);

		dynamicQuery.add(
			RestrictionsFactoryUtil.eq(
				"objectLayoutBoxColumnId",
				newObjectLayoutBoxColumn.getObjectLayoutBoxColumnId()));

		List<ObjectLayoutBoxColumn> result = _persistence.findWithDynamicQuery(
			dynamicQuery);

		Assert.assertEquals(1, result.size());

		ObjectLayoutBoxColumn existingObjectLayoutBoxColumn = result.get(0);

		Assert.assertEquals(
			existingObjectLayoutBoxColumn, newObjectLayoutBoxColumn);
	}

	@Test
	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			ObjectLayoutBoxColumn.class, _dynamicQueryClassLoader);

		dynamicQuery.add(
			RestrictionsFactoryUtil.eq(
				"objectLayoutBoxColumnId", RandomTestUtil.nextLong()));

		List<ObjectLayoutBoxColumn> result = _persistence.findWithDynamicQuery(
			dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testDynamicQueryByProjectionExisting() throws Exception {
		ObjectLayoutBoxColumn newObjectLayoutBoxColumn =
			addObjectLayoutBoxColumn();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			ObjectLayoutBoxColumn.class, _dynamicQueryClassLoader);

		dynamicQuery.setProjection(
			ProjectionFactoryUtil.property("objectLayoutBoxColumnId"));

		Object newObjectLayoutBoxColumnId =
			newObjectLayoutBoxColumn.getObjectLayoutBoxColumnId();

		dynamicQuery.add(
			RestrictionsFactoryUtil.in(
				"objectLayoutBoxColumnId",
				new Object[] {newObjectLayoutBoxColumnId}));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		Object existingObjectLayoutBoxColumnId = result.get(0);

		Assert.assertEquals(
			existingObjectLayoutBoxColumnId, newObjectLayoutBoxColumnId);
	}

	@Test
	public void testDynamicQueryByProjectionMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			ObjectLayoutBoxColumn.class, _dynamicQueryClassLoader);

		dynamicQuery.setProjection(
			ProjectionFactoryUtil.property("objectLayoutBoxColumnId"));

		dynamicQuery.add(
			RestrictionsFactoryUtil.in(
				"objectLayoutBoxColumnId",
				new Object[] {RandomTestUtil.nextLong()}));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	protected ObjectLayoutBoxColumn addObjectLayoutBoxColumn()
		throws Exception {

		long pk = RandomTestUtil.nextLong();

		ObjectLayoutBoxColumn objectLayoutBoxColumn = _persistence.create(pk);

		objectLayoutBoxColumn.setMvccVersion(RandomTestUtil.nextLong());

		objectLayoutBoxColumn.setUuid(RandomTestUtil.randomString());

		objectLayoutBoxColumn.setCompanyId(RandomTestUtil.nextLong());

		objectLayoutBoxColumn.setUserId(RandomTestUtil.nextLong());

		objectLayoutBoxColumn.setUserName(RandomTestUtil.randomString());

		objectLayoutBoxColumn.setCreateDate(RandomTestUtil.nextDate());

		objectLayoutBoxColumn.setModifiedDate(RandomTestUtil.nextDate());

		objectLayoutBoxColumn.setObjectFieldId(RandomTestUtil.nextLong());

		objectLayoutBoxColumn.setObjectLayoutBoxRowId(
			RandomTestUtil.nextLong());

		objectLayoutBoxColumn.setOrder(RandomTestUtil.nextInt());

		_objectLayoutBoxColumns.add(_persistence.update(objectLayoutBoxColumn));

		return objectLayoutBoxColumn;
	}

	private List<ObjectLayoutBoxColumn> _objectLayoutBoxColumns =
		new ArrayList<ObjectLayoutBoxColumn>();
	private ObjectLayoutBoxColumnPersistence _persistence;
	private ClassLoader _dynamicQueryClassLoader;

}