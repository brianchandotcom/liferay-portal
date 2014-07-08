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

package com.liferay.portal.service.persistence;

import com.liferay.portal.NoSuchListTypeException;
import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQueryFactoryUtil;
import com.liferay.portal.kernel.dao.orm.ProjectionFactoryUtil;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.RestrictionsFactoryUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.test.ExecutionTestListeners;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.OrderByComparatorFactoryUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.model.ListType;
import com.liferay.portal.model.ModelListener;
import com.liferay.portal.service.persistence.BasePersistence;
import com.liferay.portal.service.persistence.PersistenceExecutionTestListener;
import com.liferay.portal.test.LiferayPersistenceIntegrationJUnitTestRunner;
import com.liferay.portal.test.persistence.test.TransactionalPersistenceAdvice;
import com.liferay.portal.util.test.RandomTestUtil;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import org.junit.runner.RunWith;

import java.io.Serializable;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author Brian Wing Shun Chan
 */
@ExecutionTestListeners(listeners =  {
	PersistenceExecutionTestListener.class})
@RunWith(LiferayPersistenceIntegrationJUnitTestRunner.class)
public class ListTypePersistenceTest {
	@Before
	public void setUp() {
		_modelListeners = _persistence.getListeners();

		for (ModelListener<ListType> modelListener : _modelListeners) {
			_persistence.unregisterListener(modelListener);
		}
	}

	@After
	public void tearDown() throws Exception {
		Map<Serializable, BasePersistence<?>> basePersistences = _transactionalPersistenceAdvice.getBasePersistences();

		Set<Serializable> primaryKeys = basePersistences.keySet();

		for (Serializable primaryKey : primaryKeys) {
			BasePersistence<?> basePersistence = basePersistences.get(primaryKey);

			try {
				basePersistence.remove(primaryKey);
			}
			catch (Exception e) {
				if (_log.isDebugEnabled()) {
					_log.debug("The model with primary key " + primaryKey +
						" was already deleted");
				}
			}
		}

		_transactionalPersistenceAdvice.reset();

		for (ModelListener<ListType> modelListener : _modelListeners) {
			_persistence.registerListener(modelListener);
		}
	}

	@Test
	public void testCreate() throws Exception {
		int pk = RandomTestUtil.nextInt();

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
		int pk = RandomTestUtil.nextInt();

		ListType newListType = _persistence.create(pk);

		newListType.setMvccVersion(RandomTestUtil.nextLong());

		newListType.setName(RandomTestUtil.randomString());

		newListType.setType(RandomTestUtil.randomString());

		_persistence.update(newListType);

		ListType existingListType = _persistence.findByPrimaryKey(newListType.getPrimaryKey());

		Assert.assertEquals(existingListType.getMvccVersion(),
			newListType.getMvccVersion());
		Assert.assertEquals(existingListType.getListTypeId(),
			newListType.getListTypeId());
		Assert.assertEquals(existingListType.getName(), newListType.getName());
		Assert.assertEquals(existingListType.getType(), newListType.getType());
	}

	@Test
	public void testCountByType() {
		try {
			_persistence.countByType(StringPool.BLANK);

			_persistence.countByType(StringPool.NULL);

			_persistence.countByType((String)null);
		}
		catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void testFindByPrimaryKeyExisting() throws Exception {
		ListType newListType = addListType();

		ListType existingListType = _persistence.findByPrimaryKey(newListType.getPrimaryKey());

		Assert.assertEquals(existingListType, newListType);
	}

	@Test
	public void testFindByPrimaryKeyMissing() throws Exception {
		int pk = RandomTestUtil.nextInt();

		try {
			_persistence.findByPrimaryKey(pk);

			Assert.fail("Missing entity did not throw NoSuchListTypeException");
		}
		catch (NoSuchListTypeException nsee) {
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

	protected OrderByComparator getOrderByComparator() {
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
		int pk = RandomTestUtil.nextInt();

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
		int pk1 = RandomTestUtil.nextInt();

		int pk2 = RandomTestUtil.nextInt();

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

		int pk = RandomTestUtil.nextInt();

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
	public void testDynamicQueryByPrimaryKeyExisting()
		throws Exception {
		ListType newListType = addListType();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(ListType.class,
				ListType.class.getClassLoader());

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
				ListType.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("listTypeId",
				RandomTestUtil.nextInt()));

		List<ListType> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testDynamicQueryByProjectionExisting()
		throws Exception {
		ListType newListType = addListType();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(ListType.class,
				ListType.class.getClassLoader());

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
				ListType.class.getClassLoader());

		dynamicQuery.setProjection(ProjectionFactoryUtil.property("listTypeId"));

		dynamicQuery.add(RestrictionsFactoryUtil.in("listTypeId",
				new Object[] { RandomTestUtil.nextInt() }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	protected ListType addListType() throws Exception {
		int pk = RandomTestUtil.nextInt();

		ListType listType = _persistence.create(pk);

		listType.setMvccVersion(RandomTestUtil.nextLong());

		listType.setName(RandomTestUtil.randomString());

		listType.setType(RandomTestUtil.randomString());

		_persistence.update(listType);

		return listType;
	}

	private static Log _log = LogFactoryUtil.getLog(ListTypePersistenceTest.class);
	private ModelListener<ListType>[] _modelListeners;
	private ListTypePersistence _persistence = (ListTypePersistence)PortalBeanLocatorUtil.locate(ListTypePersistence.class.getName());
	private TransactionalPersistenceAdvice _transactionalPersistenceAdvice = (TransactionalPersistenceAdvice)PortalBeanLocatorUtil.locate(TransactionalPersistenceAdvice.class.getName());
}