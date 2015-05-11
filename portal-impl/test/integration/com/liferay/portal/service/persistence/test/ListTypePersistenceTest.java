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

package com.liferay.portal.service.persistence.test;

import com.liferay.portal.NoSuchListTypeException;
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
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.ListType;
import com.liferay.portal.service.ListTypeLocalServiceUtil;
import com.liferay.portal.service.persistence.ListTypePersistence;
import com.liferay.portal.service.persistence.ListTypeUtil;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PersistenceTestRule;

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
public class ListTypePersistenceTest {
	@Rule
	public final AggregateTestRule aggregateTestRule = new AggregateTestRule(new LiferayIntegrationTestRule(),
			PersistenceTestRule.INSTANCE,
			new TransactionalTestRule(Propagation.REQUIRED));

	@Before
	public void setUp() {
		_persistence = ListTypeUtil.getPersistence();

		Class<?> clazz = _persistence.getClass();

		_dynamicQueryClassLoader = clazz.getClassLoader();
	}

	@After
	public void tearDown() throws Exception {
		Iterator<ListType> iterator = _listTypes.iterator();

		while (iterator.hasNext()) {
			_persistence.remove(iterator.next());

			iterator.remove();
		}
	}

	@Test
	public void testCreate() throws Exception {
		long pk = RandomTestUtil.nextLong();

		ListType listType = _persistence.create(pk);

		Assert.assertNotNull(listType);

		Assert.assertEquals(listType.getPrimaryKey(), pk);
	}

	@Test
	public void testRemove() throws Exception {
		ListType newListType = addListType();

		_persistence.remove(newListType);

		ListType existingListType = _persistence.fetchByPrimaryKey(newListType.getPrimaryKey());

		Assert.assertNull(existingListType);
	}

	@Test
	public void testUpdateNew() throws Exception {
		addListType();
	}

	@Test
	public void testUpdateExisting() throws Exception {
		long pk = RandomTestUtil.nextLong();

		ListType newListType = _persistence.create(pk);

		newListType.setMvccVersion(RandomTestUtil.nextLong());

		newListType.setName(RandomTestUtil.randomString());

		newListType.setType(RandomTestUtil.randomString());

		_listTypes.add(_persistence.update(newListType));

		ListType existingListType = _persistence.findByPrimaryKey(newListType.getPrimaryKey());

		Assert.assertEquals(existingListType.getMvccVersion(),
			newListType.getMvccVersion());
		Assert.assertEquals(existingListType.getListTypeId(),
			newListType.getListTypeId());
		Assert.assertEquals(existingListType.getName(), newListType.getName());
		Assert.assertEquals(existingListType.getType(), newListType.getType());
	}

	@Test
	public void testCountByType() throws Exception {
		_persistence.countByType(StringPool.BLANK);

		_persistence.countByType(StringPool.NULL);

		_persistence.countByType((String)null);
	}

	@Test
	public void testCountByN_T() throws Exception {
		_persistence.countByN_T(StringPool.BLANK, StringPool.BLANK);

		_persistence.countByN_T(StringPool.NULL, StringPool.NULL);

		_persistence.countByN_T((String)null, (String)null);
	}

	@Test
	public void testFindByPrimaryKeyExisting() throws Exception {
		ListType newListType = addListType();

		ListType existingListType = _persistence.findByPrimaryKey(newListType.getPrimaryKey());

		Assert.assertEquals(existingListType, newListType);
	}

	@Test(expected = NoSuchListTypeException.class)
	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		_persistence.findByPrimaryKey(pk);
	}

	@Test
	public void testFindAll() throws Exception {
		_persistence.findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			getOrderByComparator());
	}

	protected OrderByComparator<ListType> getOrderByComparator() {
		return OrderByComparatorFactoryUtil.create("ListType", "mvccVersion",
			true, "listTypeId", true, "name", true, "type", true);
	}

	@Test
	public void testFetchByPrimaryKeyExisting() throws Exception {
		ListType newListType = addListType();

		ListType existingListType = _persistence.fetchByPrimaryKey(newListType.getPrimaryKey());

		Assert.assertEquals(existingListType, newListType);
	}

	@Test
	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		ListType missingListType = _persistence.fetchByPrimaryKey(pk);

		Assert.assertNull(missingListType);
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereAllPrimaryKeysExist()
		throws Exception {
		ListType newListType1 = addListType();
		ListType newListType2 = addListType();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newListType1.getPrimaryKey());
		primaryKeys.add(newListType2.getPrimaryKey());

		Map<Serializable, ListType> listTypes = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(2, listTypes.size());
		Assert.assertEquals(newListType1,
			listTypes.get(newListType1.getPrimaryKey()));
		Assert.assertEquals(newListType2,
			listTypes.get(newListType2.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereNoPrimaryKeysExist()
		throws Exception {
		long pk1 = RandomTestUtil.nextLong();

		long pk2 = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(pk1);
		primaryKeys.add(pk2);

		Map<Serializable, ListType> listTypes = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(listTypes.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereSomePrimaryKeysExist()
		throws Exception {
		ListType newListType = addListType();

		long pk = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newListType.getPrimaryKey());
		primaryKeys.add(pk);

		Map<Serializable, ListType> listTypes = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, listTypes.size());
		Assert.assertEquals(newListType,
			listTypes.get(newListType.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithNoPrimaryKeys()
		throws Exception {
		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		Map<Serializable, ListType> listTypes = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(listTypes.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithOnePrimaryKey()
		throws Exception {
		ListType newListType = addListType();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newListType.getPrimaryKey());

		Map<Serializable, ListType> listTypes = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, listTypes.size());
		Assert.assertEquals(newListType,
			listTypes.get(newListType.getPrimaryKey()));
	}

	@Test
	public void testActionableDynamicQuery() throws Exception {
		final IntegerWrapper count = new IntegerWrapper();

		ActionableDynamicQuery actionableDynamicQuery = ListTypeLocalServiceUtil.getActionableDynamicQuery();

		actionableDynamicQuery.setPerformActionMethod(new ActionableDynamicQuery.PerformActionMethod() {
				@Override
				public void performAction(Object object) {
					ListType listType = (ListType)object;

					Assert.assertNotNull(listType);

					count.increment();
				}
			});

		actionableDynamicQuery.performActions();

		Assert.assertEquals(count.getValue(), _persistence.countAll());
	}

	@Test
	public void testDynamicQueryByPrimaryKeyExisting()
		throws Exception {
		ListType newListType = addListType();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(ListType.class,
				_dynamicQueryClassLoader);

		dynamicQuery.add(RestrictionsFactoryUtil.eq("listTypeId",
				newListType.getListTypeId()));

		List<ListType> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		ListType existingListType = result.get(0);

		Assert.assertEquals(existingListType, newListType);
	}

	@Test
	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(ListType.class,
				_dynamicQueryClassLoader);

		dynamicQuery.add(RestrictionsFactoryUtil.eq("listTypeId",
				RandomTestUtil.nextLong()));

		List<ListType> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testDynamicQueryByProjectionExisting()
		throws Exception {
		ListType newListType = addListType();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(ListType.class,
				_dynamicQueryClassLoader);

		dynamicQuery.setProjection(ProjectionFactoryUtil.property("listTypeId"));

		Object newListTypeId = newListType.getListTypeId();

		dynamicQuery.add(RestrictionsFactoryUtil.in("listTypeId",
				new Object[] { newListTypeId }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		Object existingListTypeId = result.get(0);

		Assert.assertEquals(existingListTypeId, newListTypeId);
	}

	@Test
	public void testDynamicQueryByProjectionMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(ListType.class,
				_dynamicQueryClassLoader);

		dynamicQuery.setProjection(ProjectionFactoryUtil.property("listTypeId"));

		dynamicQuery.add(RestrictionsFactoryUtil.in("listTypeId",
				new Object[] { RandomTestUtil.nextLong() }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testResetOriginalValues() throws Exception {
		ListType newListType = addListType();

		_persistence.clearCache();

		ListType existingListType = _persistence.findByPrimaryKey(newListType.getPrimaryKey());

		Assert.assertTrue(Validator.equals(existingListType.getName(),
				ReflectionTestUtil.invoke(existingListType, "getOriginalName",
					new Class<?>[0])));
		Assert.assertTrue(Validator.equals(existingListType.getType(),
				ReflectionTestUtil.invoke(existingListType, "getOriginalType",
					new Class<?>[0])));
	}

	protected ListType addListType() throws Exception {
		long pk = RandomTestUtil.nextLong();

		ListType listType = _persistence.create(pk);

		listType.setMvccVersion(RandomTestUtil.nextLong());

		listType.setName(RandomTestUtil.randomString());

		listType.setType(RandomTestUtil.randomString());

		_listTypes.add(_persistence.update(listType));

		return listType;
	}

	private List<ListType> _listTypes = new ArrayList<ListType>();
	private ListTypePersistence _persistence;
	private ClassLoader _dynamicQueryClassLoader;
}