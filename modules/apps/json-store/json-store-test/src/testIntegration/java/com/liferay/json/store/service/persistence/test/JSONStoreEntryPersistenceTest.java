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

package com.liferay.json.store.service.persistence.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.json.store.exception.NoSuchEntryException;
import com.liferay.json.store.model.JSONStoreEntry;
import com.liferay.json.store.service.JSONStoreEntryLocalServiceUtil;
import com.liferay.json.store.service.persistence.JSONStoreEntryPersistence;
import com.liferay.json.store.service.persistence.JSONStoreEntryUtil;
import com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQueryFactoryUtil;
import com.liferay.portal.kernel.dao.orm.ProjectionFactoryUtil;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.RestrictionsFactoryUtil;
import com.liferay.portal.kernel.dao.orm.Session;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.transaction.Propagation;
import com.liferay.portal.kernel.util.IntegerWrapper;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.OrderByComparatorFactoryUtil;
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
public class JSONStoreEntryPersistenceTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(), PersistenceTestRule.INSTANCE,
			new TransactionalTestRule(
				Propagation.REQUIRED, "com.liferay.json.store.service"));

	@Before
	public void setUp() {
		_persistence = JSONStoreEntryUtil.getPersistence();

		Class<?> clazz = _persistence.getClass();

		_dynamicQueryClassLoader = clazz.getClassLoader();
	}

	@After
	public void tearDown() throws Exception {
		Iterator<JSONStoreEntry> iterator = _jsonStoreEntries.iterator();

		while (iterator.hasNext()) {
			_persistence.remove(iterator.next());

			iterator.remove();
		}
	}

	@Test
	public void testCreate() throws Exception {
		long pk = RandomTestUtil.nextLong();

		JSONStoreEntry jsonStoreEntry = _persistence.create(pk);

		Assert.assertNotNull(jsonStoreEntry);

		Assert.assertEquals(jsonStoreEntry.getPrimaryKey(), pk);
	}

	@Test
	public void testRemove() throws Exception {
		JSONStoreEntry newJSONStoreEntry = addJSONStoreEntry();

		_persistence.remove(newJSONStoreEntry);

		JSONStoreEntry existingJSONStoreEntry = _persistence.fetchByPrimaryKey(
			newJSONStoreEntry.getPrimaryKey());

		Assert.assertNull(existingJSONStoreEntry);
	}

	@Test
	public void testUpdateNew() throws Exception {
		addJSONStoreEntry();
	}

	@Test
	public void testUpdateExisting() throws Exception {
		long pk = RandomTestUtil.nextLong();

		JSONStoreEntry newJSONStoreEntry = _persistence.create(pk);

		newJSONStoreEntry.setMvccVersion(RandomTestUtil.nextLong());

		newJSONStoreEntry.setCtCollectionId(RandomTestUtil.nextLong());

		newJSONStoreEntry.setCompanyId(RandomTestUtil.nextLong());

		newJSONStoreEntry.setClassNameId(RandomTestUtil.nextLong());

		newJSONStoreEntry.setClassPK(RandomTestUtil.nextLong());

		newJSONStoreEntry.setParentJSONStoreEntryId(RandomTestUtil.nextLong());

		newJSONStoreEntry.setIndex(RandomTestUtil.nextInt());

		newJSONStoreEntry.setKey(RandomTestUtil.randomString());

		newJSONStoreEntry.setType(RandomTestUtil.nextInt());

		newJSONStoreEntry.setValueLong(RandomTestUtil.nextLong());

		newJSONStoreEntry.setValueString(RandomTestUtil.randomString());

		_jsonStoreEntries.add(_persistence.update(newJSONStoreEntry));

		JSONStoreEntry existingJSONStoreEntry = _persistence.findByPrimaryKey(
			newJSONStoreEntry.getPrimaryKey());

		Assert.assertEquals(
			existingJSONStoreEntry.getMvccVersion(),
			newJSONStoreEntry.getMvccVersion());
		Assert.assertEquals(
			existingJSONStoreEntry.getCtCollectionId(),
			newJSONStoreEntry.getCtCollectionId());
		Assert.assertEquals(
			existingJSONStoreEntry.getJsonStoreEntryId(),
			newJSONStoreEntry.getJsonStoreEntryId());
		Assert.assertEquals(
			existingJSONStoreEntry.getCompanyId(),
			newJSONStoreEntry.getCompanyId());
		Assert.assertEquals(
			existingJSONStoreEntry.getClassNameId(),
			newJSONStoreEntry.getClassNameId());
		Assert.assertEquals(
			existingJSONStoreEntry.getClassPK(),
			newJSONStoreEntry.getClassPK());
		Assert.assertEquals(
			existingJSONStoreEntry.getParentJSONStoreEntryId(),
			newJSONStoreEntry.getParentJSONStoreEntryId());
		Assert.assertEquals(
			existingJSONStoreEntry.getIndex(), newJSONStoreEntry.getIndex());
		Assert.assertEquals(
			existingJSONStoreEntry.getKey(), newJSONStoreEntry.getKey());
		Assert.assertEquals(
			existingJSONStoreEntry.getType(), newJSONStoreEntry.getType());
		Assert.assertEquals(
			existingJSONStoreEntry.getValueLong(),
			newJSONStoreEntry.getValueLong());
		Assert.assertEquals(
			existingJSONStoreEntry.getValueString(),
			newJSONStoreEntry.getValueString());
	}

	@Test
	public void testCountByCN_CPK() throws Exception {
		_persistence.countByCN_CPK(
			RandomTestUtil.nextLong(), RandomTestUtil.nextLong());

		_persistence.countByCN_CPK(0L, 0L);
	}

	@Test
	public void testCountByC_CN_I_T_VL() throws Exception {
		_persistence.countByC_CN_I_T_VL(
			RandomTestUtil.nextLong(), RandomTestUtil.nextLong(),
			RandomTestUtil.nextInt(), RandomTestUtil.nextInt(),
			RandomTestUtil.nextLong());

		_persistence.countByC_CN_I_T_VL(0L, 0L, 0, 0, 0L);
	}

	@Test
	public void testCountByC_CN_K_T_VL() throws Exception {
		_persistence.countByC_CN_K_T_VL(
			RandomTestUtil.nextLong(), RandomTestUtil.nextLong(), "",
			RandomTestUtil.nextInt(), RandomTestUtil.nextLong());

		_persistence.countByC_CN_K_T_VL(0L, 0L, "null", 0, 0L);

		_persistence.countByC_CN_K_T_VL(0L, 0L, (String)null, 0, 0L);
	}

	@Test
	public void testCountByCN_CPK_P_I_K() throws Exception {
		_persistence.countByCN_CPK_P_I_K(
			RandomTestUtil.nextLong(), RandomTestUtil.nextLong(),
			RandomTestUtil.nextLong(), RandomTestUtil.nextInt(), "");

		_persistence.countByCN_CPK_P_I_K(0L, 0L, 0L, 0, "null");

		_persistence.countByCN_CPK_P_I_K(0L, 0L, 0L, 0, (String)null);
	}

	@Test
	public void testFindByPrimaryKeyExisting() throws Exception {
		JSONStoreEntry newJSONStoreEntry = addJSONStoreEntry();

		JSONStoreEntry existingJSONStoreEntry = _persistence.findByPrimaryKey(
			newJSONStoreEntry.getPrimaryKey());

		Assert.assertEquals(existingJSONStoreEntry, newJSONStoreEntry);
	}

	@Test(expected = NoSuchEntryException.class)
	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		_persistence.findByPrimaryKey(pk);
	}

	@Test
	public void testFindAll() throws Exception {
		_persistence.findAll(
			QueryUtil.ALL_POS, QueryUtil.ALL_POS, getOrderByComparator());
	}

	protected OrderByComparator<JSONStoreEntry> getOrderByComparator() {
		return OrderByComparatorFactoryUtil.create(
			"JSONStoreEntry", "mvccVersion", true, "ctCollectionId", true,
			"jsonStoreEntryId", true, "companyId", true, "classNameId", true,
			"classPK", true, "parentJSONStoreEntryId", true, "index", true,
			"key", true, "type", true, "valueLong", true);
	}

	@Test
	public void testFetchByPrimaryKeyExisting() throws Exception {
		JSONStoreEntry newJSONStoreEntry = addJSONStoreEntry();

		JSONStoreEntry existingJSONStoreEntry = _persistence.fetchByPrimaryKey(
			newJSONStoreEntry.getPrimaryKey());

		Assert.assertEquals(existingJSONStoreEntry, newJSONStoreEntry);
	}

	@Test
	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		JSONStoreEntry missingJSONStoreEntry = _persistence.fetchByPrimaryKey(
			pk);

		Assert.assertNull(missingJSONStoreEntry);
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereAllPrimaryKeysExist()
		throws Exception {

		JSONStoreEntry newJSONStoreEntry1 = addJSONStoreEntry();
		JSONStoreEntry newJSONStoreEntry2 = addJSONStoreEntry();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newJSONStoreEntry1.getPrimaryKey());
		primaryKeys.add(newJSONStoreEntry2.getPrimaryKey());

		Map<Serializable, JSONStoreEntry> jsonStoreEntries =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(2, jsonStoreEntries.size());
		Assert.assertEquals(
			newJSONStoreEntry1,
			jsonStoreEntries.get(newJSONStoreEntry1.getPrimaryKey()));
		Assert.assertEquals(
			newJSONStoreEntry2,
			jsonStoreEntries.get(newJSONStoreEntry2.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereNoPrimaryKeysExist()
		throws Exception {

		long pk1 = RandomTestUtil.nextLong();

		long pk2 = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(pk1);
		primaryKeys.add(pk2);

		Map<Serializable, JSONStoreEntry> jsonStoreEntries =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(jsonStoreEntries.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereSomePrimaryKeysExist()
		throws Exception {

		JSONStoreEntry newJSONStoreEntry = addJSONStoreEntry();

		long pk = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newJSONStoreEntry.getPrimaryKey());
		primaryKeys.add(pk);

		Map<Serializable, JSONStoreEntry> jsonStoreEntries =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, jsonStoreEntries.size());
		Assert.assertEquals(
			newJSONStoreEntry,
			jsonStoreEntries.get(newJSONStoreEntry.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithNoPrimaryKeys() throws Exception {
		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		Map<Serializable, JSONStoreEntry> jsonStoreEntries =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(jsonStoreEntries.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithOnePrimaryKey() throws Exception {
		JSONStoreEntry newJSONStoreEntry = addJSONStoreEntry();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newJSONStoreEntry.getPrimaryKey());

		Map<Serializable, JSONStoreEntry> jsonStoreEntries =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, jsonStoreEntries.size());
		Assert.assertEquals(
			newJSONStoreEntry,
			jsonStoreEntries.get(newJSONStoreEntry.getPrimaryKey()));
	}

	@Test
	public void testActionableDynamicQuery() throws Exception {
		final IntegerWrapper count = new IntegerWrapper();

		ActionableDynamicQuery actionableDynamicQuery =
			JSONStoreEntryLocalServiceUtil.getActionableDynamicQuery();

		actionableDynamicQuery.setPerformActionMethod(
			new ActionableDynamicQuery.PerformActionMethod<JSONStoreEntry>() {

				@Override
				public void performAction(JSONStoreEntry jsonStoreEntry) {
					Assert.assertNotNull(jsonStoreEntry);

					count.increment();
				}

			});

		actionableDynamicQuery.performActions();

		Assert.assertEquals(count.getValue(), _persistence.countAll());
	}

	@Test
	public void testDynamicQueryByPrimaryKeyExisting() throws Exception {
		JSONStoreEntry newJSONStoreEntry = addJSONStoreEntry();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			JSONStoreEntry.class, _dynamicQueryClassLoader);

		dynamicQuery.add(
			RestrictionsFactoryUtil.eq(
				"jsonStoreEntryId", newJSONStoreEntry.getJsonStoreEntryId()));

		List<JSONStoreEntry> result = _persistence.findWithDynamicQuery(
			dynamicQuery);

		Assert.assertEquals(1, result.size());

		JSONStoreEntry existingJSONStoreEntry = result.get(0);

		Assert.assertEquals(existingJSONStoreEntry, newJSONStoreEntry);
	}

	@Test
	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			JSONStoreEntry.class, _dynamicQueryClassLoader);

		dynamicQuery.add(
			RestrictionsFactoryUtil.eq(
				"jsonStoreEntryId", RandomTestUtil.nextLong()));

		List<JSONStoreEntry> result = _persistence.findWithDynamicQuery(
			dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testDynamicQueryByProjectionExisting() throws Exception {
		JSONStoreEntry newJSONStoreEntry = addJSONStoreEntry();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			JSONStoreEntry.class, _dynamicQueryClassLoader);

		dynamicQuery.setProjection(
			ProjectionFactoryUtil.property("jsonStoreEntryId"));

		Object newJsonStoreEntryId = newJSONStoreEntry.getJsonStoreEntryId();

		dynamicQuery.add(
			RestrictionsFactoryUtil.in(
				"jsonStoreEntryId", new Object[] {newJsonStoreEntryId}));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		Object existingJsonStoreEntryId = result.get(0);

		Assert.assertEquals(existingJsonStoreEntryId, newJsonStoreEntryId);
	}

	@Test
	public void testDynamicQueryByProjectionMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			JSONStoreEntry.class, _dynamicQueryClassLoader);

		dynamicQuery.setProjection(
			ProjectionFactoryUtil.property("jsonStoreEntryId"));

		dynamicQuery.add(
			RestrictionsFactoryUtil.in(
				"jsonStoreEntryId", new Object[] {RandomTestUtil.nextLong()}));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testResetOriginalValues() throws Exception {
		JSONStoreEntry newJSONStoreEntry = addJSONStoreEntry();

		_persistence.clearCache();

		_assertOriginalValues(
			_persistence.findByPrimaryKey(newJSONStoreEntry.getPrimaryKey()));
	}

	@Test
	public void testResetOriginalValuesWithDynamicQueryLoadFromDatabase()
		throws Exception {

		_testResetOriginalValuesWithDynamicQuery(true);
	}

	@Test
	public void testResetOriginalValuesWithDynamicQueryLoadFromSession()
		throws Exception {

		_testResetOriginalValuesWithDynamicQuery(false);
	}

	private void _testResetOriginalValuesWithDynamicQuery(boolean clearSession)
		throws Exception {

		JSONStoreEntry newJSONStoreEntry = addJSONStoreEntry();

		if (clearSession) {
			Session session = _persistence.openSession();

			session.flush();

			session.clear();
		}

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			JSONStoreEntry.class, _dynamicQueryClassLoader);

		dynamicQuery.add(
			RestrictionsFactoryUtil.eq(
				"jsonStoreEntryId", newJSONStoreEntry.getJsonStoreEntryId()));

		List<JSONStoreEntry> result = _persistence.findWithDynamicQuery(
			dynamicQuery);

		_assertOriginalValues(result.get(0));
	}

	private void _assertOriginalValues(JSONStoreEntry jsonStoreEntry) {
		Assert.assertEquals(
			Long.valueOf(jsonStoreEntry.getClassNameId()),
			ReflectionTestUtil.<Long>invoke(
				jsonStoreEntry, "getColumnOriginalValue",
				new Class<?>[] {String.class}, "classNameId"));
		Assert.assertEquals(
			Long.valueOf(jsonStoreEntry.getClassPK()),
			ReflectionTestUtil.<Long>invoke(
				jsonStoreEntry, "getColumnOriginalValue",
				new Class<?>[] {String.class}, "classPK"));
		Assert.assertEquals(
			Long.valueOf(jsonStoreEntry.getParentJSONStoreEntryId()),
			ReflectionTestUtil.<Long>invoke(
				jsonStoreEntry, "getColumnOriginalValue",
				new Class<?>[] {String.class}, "parentJSONStoreEntryId"));
		Assert.assertEquals(
			Integer.valueOf(jsonStoreEntry.getIndex()),
			ReflectionTestUtil.<Integer>invoke(
				jsonStoreEntry, "getColumnOriginalValue",
				new Class<?>[] {String.class}, "index_"));
		Assert.assertEquals(
			jsonStoreEntry.getKey(),
			ReflectionTestUtil.invoke(
				jsonStoreEntry, "getColumnOriginalValue",
				new Class<?>[] {String.class}, "key_"));
	}

	protected JSONStoreEntry addJSONStoreEntry() throws Exception {
		long pk = RandomTestUtil.nextLong();

		JSONStoreEntry jsonStoreEntry = _persistence.create(pk);

		jsonStoreEntry.setMvccVersion(RandomTestUtil.nextLong());

		jsonStoreEntry.setCtCollectionId(RandomTestUtil.nextLong());

		jsonStoreEntry.setCompanyId(RandomTestUtil.nextLong());

		jsonStoreEntry.setClassNameId(RandomTestUtil.nextLong());

		jsonStoreEntry.setClassPK(RandomTestUtil.nextLong());

		jsonStoreEntry.setParentJSONStoreEntryId(RandomTestUtil.nextLong());

		jsonStoreEntry.setIndex(RandomTestUtil.nextInt());

		jsonStoreEntry.setKey(RandomTestUtil.randomString());

		jsonStoreEntry.setType(RandomTestUtil.nextInt());

		jsonStoreEntry.setValueLong(RandomTestUtil.nextLong());

		jsonStoreEntry.setValueString(RandomTestUtil.randomString());

		_jsonStoreEntries.add(_persistence.update(jsonStoreEntry));

		return jsonStoreEntry;
	}

	private List<JSONStoreEntry> _jsonStoreEntries =
		new ArrayList<JSONStoreEntry>();
	private JSONStoreEntryPersistence _persistence;
	private ClassLoader _dynamicQueryClassLoader;

}