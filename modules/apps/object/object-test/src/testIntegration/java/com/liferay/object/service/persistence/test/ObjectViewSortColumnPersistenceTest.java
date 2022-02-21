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
import com.liferay.object.exception.NoSuchObjectViewSortColumnException;
import com.liferay.object.model.ObjectViewSortColumn;
import com.liferay.object.service.persistence.ObjectViewSortColumnPersistence;
import com.liferay.object.service.persistence.ObjectViewSortColumnUtil;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQueryFactoryUtil;
import com.liferay.portal.kernel.dao.orm.ProjectionFactoryUtil;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.RestrictionsFactoryUtil;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.transaction.Propagation;
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
public class ObjectViewSortColumnPersistenceTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(), PersistenceTestRule.INSTANCE,
			new TransactionalTestRule(
				Propagation.REQUIRED, "com.liferay.object.service"));

	@Before
	public void setUp() {
		_persistence = ObjectViewSortColumnUtil.getPersistence();

		Class<?> clazz = _persistence.getClass();

		_dynamicQueryClassLoader = clazz.getClassLoader();
	}

	@After
	public void tearDown() throws Exception {
		Iterator<ObjectViewSortColumn> iterator =
			_objectViewSortColumns.iterator();

		while (iterator.hasNext()) {
			_persistence.remove(iterator.next());

			iterator.remove();
		}
	}

	@Test
	public void testCreate() throws Exception {
		long pk = RandomTestUtil.nextLong();

		ObjectViewSortColumn objectViewSortColumn = _persistence.create(pk);

		Assert.assertNotNull(objectViewSortColumn);

		Assert.assertEquals(objectViewSortColumn.getPrimaryKey(), pk);
	}

	@Test
	public void testRemove() throws Exception {
		ObjectViewSortColumn newObjectViewSortColumn =
			addObjectViewSortColumn();

		_persistence.remove(newObjectViewSortColumn);

		ObjectViewSortColumn existingObjectViewSortColumn =
			_persistence.fetchByPrimaryKey(
				newObjectViewSortColumn.getPrimaryKey());

		Assert.assertNull(existingObjectViewSortColumn);
	}

	@Test
	public void testUpdateNew() throws Exception {
		addObjectViewSortColumn();
	}

	@Test
	public void testUpdateExisting() throws Exception {
		long pk = RandomTestUtil.nextLong();

		ObjectViewSortColumn newObjectViewSortColumn = _persistence.create(pk);

		newObjectViewSortColumn.setMvccVersion(RandomTestUtil.nextLong());

		newObjectViewSortColumn.setUuid(RandomTestUtil.randomString());

		newObjectViewSortColumn.setCompanyId(RandomTestUtil.nextLong());

		newObjectViewSortColumn.setUserId(RandomTestUtil.nextLong());

		newObjectViewSortColumn.setUserName(RandomTestUtil.randomString());

		newObjectViewSortColumn.setCreateDate(RandomTestUtil.nextDate());

		newObjectViewSortColumn.setModifiedDate(RandomTestUtil.nextDate());

		newObjectViewSortColumn.setObjectViewId(RandomTestUtil.nextLong());

		newObjectViewSortColumn.setObjectFieldName(
			RandomTestUtil.randomString());

		newObjectViewSortColumn.setPriority(RandomTestUtil.nextInt());

		newObjectViewSortColumn.setSortOrder(RandomTestUtil.randomString());

		_objectViewSortColumns.add(
			_persistence.update(newObjectViewSortColumn));

		ObjectViewSortColumn existingObjectViewSortColumn =
			_persistence.findByPrimaryKey(
				newObjectViewSortColumn.getPrimaryKey());

		Assert.assertEquals(
			existingObjectViewSortColumn.getMvccVersion(),
			newObjectViewSortColumn.getMvccVersion());
		Assert.assertEquals(
			existingObjectViewSortColumn.getUuid(),
			newObjectViewSortColumn.getUuid());
		Assert.assertEquals(
			existingObjectViewSortColumn.getObjectViewSortColumnId(),
			newObjectViewSortColumn.getObjectViewSortColumnId());
		Assert.assertEquals(
			existingObjectViewSortColumn.getCompanyId(),
			newObjectViewSortColumn.getCompanyId());
		Assert.assertEquals(
			existingObjectViewSortColumn.getUserId(),
			newObjectViewSortColumn.getUserId());
		Assert.assertEquals(
			existingObjectViewSortColumn.getUserName(),
			newObjectViewSortColumn.getUserName());
		Assert.assertEquals(
			Time.getShortTimestamp(
				existingObjectViewSortColumn.getCreateDate()),
			Time.getShortTimestamp(newObjectViewSortColumn.getCreateDate()));
		Assert.assertEquals(
			Time.getShortTimestamp(
				existingObjectViewSortColumn.getModifiedDate()),
			Time.getShortTimestamp(newObjectViewSortColumn.getModifiedDate()));
		Assert.assertEquals(
			existingObjectViewSortColumn.getObjectViewId(),
			newObjectViewSortColumn.getObjectViewId());
		Assert.assertEquals(
			existingObjectViewSortColumn.getObjectFieldName(),
			newObjectViewSortColumn.getObjectFieldName());
		Assert.assertEquals(
			existingObjectViewSortColumn.getPriority(),
			newObjectViewSortColumn.getPriority());
		Assert.assertEquals(
			existingObjectViewSortColumn.getSortOrder(),
			newObjectViewSortColumn.getSortOrder());
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
	public void testCountByObjectViewId() throws Exception {
		_persistence.countByObjectViewId(RandomTestUtil.nextLong());

		_persistence.countByObjectViewId(0L);
	}

	@Test
	public void testCountByOVI_OFN() throws Exception {
		_persistence.countByOVI_OFN(RandomTestUtil.nextLong(), "");

		_persistence.countByOVI_OFN(0L, "null");

		_persistence.countByOVI_OFN(0L, (String)null);
	}

	@Test
	public void testFindByPrimaryKeyExisting() throws Exception {
		ObjectViewSortColumn newObjectViewSortColumn =
			addObjectViewSortColumn();

		ObjectViewSortColumn existingObjectViewSortColumn =
			_persistence.findByPrimaryKey(
				newObjectViewSortColumn.getPrimaryKey());

		Assert.assertEquals(
			existingObjectViewSortColumn, newObjectViewSortColumn);
	}

	@Test(expected = NoSuchObjectViewSortColumnException.class)
	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		_persistence.findByPrimaryKey(pk);
	}

	@Test
	public void testFindAll() throws Exception {
		_persistence.findAll(
			QueryUtil.ALL_POS, QueryUtil.ALL_POS, getOrderByComparator());
	}

	protected OrderByComparator<ObjectViewSortColumn> getOrderByComparator() {
		return OrderByComparatorFactoryUtil.create(
			"ObjectViewSortColumn", "mvccVersion", true, "uuid", true,
			"objectViewSortColumnId", true, "companyId", true, "userId", true,
			"userName", true, "createDate", true, "modifiedDate", true,
			"objectViewId", true, "objectFieldName", true, "priority", true,
			"sortOrder", true);
	}

	@Test
	public void testFetchByPrimaryKeyExisting() throws Exception {
		ObjectViewSortColumn newObjectViewSortColumn =
			addObjectViewSortColumn();

		ObjectViewSortColumn existingObjectViewSortColumn =
			_persistence.fetchByPrimaryKey(
				newObjectViewSortColumn.getPrimaryKey());

		Assert.assertEquals(
			existingObjectViewSortColumn, newObjectViewSortColumn);
	}

	@Test
	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		ObjectViewSortColumn missingObjectViewSortColumn =
			_persistence.fetchByPrimaryKey(pk);

		Assert.assertNull(missingObjectViewSortColumn);
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereAllPrimaryKeysExist()
		throws Exception {

		ObjectViewSortColumn newObjectViewSortColumn1 =
			addObjectViewSortColumn();
		ObjectViewSortColumn newObjectViewSortColumn2 =
			addObjectViewSortColumn();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newObjectViewSortColumn1.getPrimaryKey());
		primaryKeys.add(newObjectViewSortColumn2.getPrimaryKey());

		Map<Serializable, ObjectViewSortColumn> objectViewSortColumns =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(2, objectViewSortColumns.size());
		Assert.assertEquals(
			newObjectViewSortColumn1,
			objectViewSortColumns.get(
				newObjectViewSortColumn1.getPrimaryKey()));
		Assert.assertEquals(
			newObjectViewSortColumn2,
			objectViewSortColumns.get(
				newObjectViewSortColumn2.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereNoPrimaryKeysExist()
		throws Exception {

		long pk1 = RandomTestUtil.nextLong();

		long pk2 = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(pk1);
		primaryKeys.add(pk2);

		Map<Serializable, ObjectViewSortColumn> objectViewSortColumns =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(objectViewSortColumns.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereSomePrimaryKeysExist()
		throws Exception {

		ObjectViewSortColumn newObjectViewSortColumn =
			addObjectViewSortColumn();

		long pk = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newObjectViewSortColumn.getPrimaryKey());
		primaryKeys.add(pk);

		Map<Serializable, ObjectViewSortColumn> objectViewSortColumns =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, objectViewSortColumns.size());
		Assert.assertEquals(
			newObjectViewSortColumn,
			objectViewSortColumns.get(newObjectViewSortColumn.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithNoPrimaryKeys() throws Exception {
		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		Map<Serializable, ObjectViewSortColumn> objectViewSortColumns =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(objectViewSortColumns.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithOnePrimaryKey() throws Exception {
		ObjectViewSortColumn newObjectViewSortColumn =
			addObjectViewSortColumn();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newObjectViewSortColumn.getPrimaryKey());

		Map<Serializable, ObjectViewSortColumn> objectViewSortColumns =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, objectViewSortColumns.size());
		Assert.assertEquals(
			newObjectViewSortColumn,
			objectViewSortColumns.get(newObjectViewSortColumn.getPrimaryKey()));
	}

	@Test
	public void testDynamicQueryByPrimaryKeyExisting() throws Exception {
		ObjectViewSortColumn newObjectViewSortColumn =
			addObjectViewSortColumn();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			ObjectViewSortColumn.class, _dynamicQueryClassLoader);

		dynamicQuery.add(
			RestrictionsFactoryUtil.eq(
				"objectViewSortColumnId",
				newObjectViewSortColumn.getObjectViewSortColumnId()));

		List<ObjectViewSortColumn> result = _persistence.findWithDynamicQuery(
			dynamicQuery);

		Assert.assertEquals(1, result.size());

		ObjectViewSortColumn existingObjectViewSortColumn = result.get(0);

		Assert.assertEquals(
			existingObjectViewSortColumn, newObjectViewSortColumn);
	}

	@Test
	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			ObjectViewSortColumn.class, _dynamicQueryClassLoader);

		dynamicQuery.add(
			RestrictionsFactoryUtil.eq(
				"objectViewSortColumnId", RandomTestUtil.nextLong()));

		List<ObjectViewSortColumn> result = _persistence.findWithDynamicQuery(
			dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testDynamicQueryByProjectionExisting() throws Exception {
		ObjectViewSortColumn newObjectViewSortColumn =
			addObjectViewSortColumn();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			ObjectViewSortColumn.class, _dynamicQueryClassLoader);

		dynamicQuery.setProjection(
			ProjectionFactoryUtil.property("objectViewSortColumnId"));

		Object newObjectViewSortColumnId =
			newObjectViewSortColumn.getObjectViewSortColumnId();

		dynamicQuery.add(
			RestrictionsFactoryUtil.in(
				"objectViewSortColumnId",
				new Object[] {newObjectViewSortColumnId}));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		Object existingObjectViewSortColumnId = result.get(0);

		Assert.assertEquals(
			existingObjectViewSortColumnId, newObjectViewSortColumnId);
	}

	@Test
	public void testDynamicQueryByProjectionMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			ObjectViewSortColumn.class, _dynamicQueryClassLoader);

		dynamicQuery.setProjection(
			ProjectionFactoryUtil.property("objectViewSortColumnId"));

		dynamicQuery.add(
			RestrictionsFactoryUtil.in(
				"objectViewSortColumnId",
				new Object[] {RandomTestUtil.nextLong()}));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	protected ObjectViewSortColumn addObjectViewSortColumn() throws Exception {
		long pk = RandomTestUtil.nextLong();

		ObjectViewSortColumn objectViewSortColumn = _persistence.create(pk);

		objectViewSortColumn.setMvccVersion(RandomTestUtil.nextLong());

		objectViewSortColumn.setUuid(RandomTestUtil.randomString());

		objectViewSortColumn.setCompanyId(RandomTestUtil.nextLong());

		objectViewSortColumn.setUserId(RandomTestUtil.nextLong());

		objectViewSortColumn.setUserName(RandomTestUtil.randomString());

		objectViewSortColumn.setCreateDate(RandomTestUtil.nextDate());

		objectViewSortColumn.setModifiedDate(RandomTestUtil.nextDate());

		objectViewSortColumn.setObjectViewId(RandomTestUtil.nextLong());

		objectViewSortColumn.setObjectFieldName(RandomTestUtil.randomString());

		objectViewSortColumn.setPriority(RandomTestUtil.nextInt());

		objectViewSortColumn.setSortOrder(RandomTestUtil.randomString());

		_objectViewSortColumns.add(_persistence.update(objectViewSortColumn));

		return objectViewSortColumn;
	}

	private List<ObjectViewSortColumn> _objectViewSortColumns =
		new ArrayList<ObjectViewSortColumn>();
	private ObjectViewSortColumnPersistence _persistence;
	private ClassLoader _dynamicQueryClassLoader;

}