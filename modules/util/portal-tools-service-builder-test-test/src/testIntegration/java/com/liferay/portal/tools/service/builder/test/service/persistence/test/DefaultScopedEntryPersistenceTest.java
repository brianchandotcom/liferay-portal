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

package com.liferay.portal.tools.service.builder.test.service.persistence.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
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
import com.liferay.portal.tools.service.builder.test.exception.NoSuchDefaultScopedEntryException;
import com.liferay.portal.tools.service.builder.test.model.DefaultScopedEntry;
import com.liferay.portal.tools.service.builder.test.service.DefaultScopedEntryLocalServiceUtil;
import com.liferay.portal.tools.service.builder.test.service.persistence.DefaultScopedEntryPersistence;
import com.liferay.portal.tools.service.builder.test.service.persistence.DefaultScopedEntryUtil;

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
public class DefaultScopedEntryPersistenceTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(), PersistenceTestRule.INSTANCE,
			new TransactionalTestRule(
				Propagation.REQUIRED,
				"com.liferay.portal.tools.service.builder.test.service"));

	@Before
	public void setUp() {
		_persistence = DefaultScopedEntryUtil.getPersistence();

		Class<?> clazz = _persistence.getClass();

		_dynamicQueryClassLoader = clazz.getClassLoader();
	}

	@After
	public void tearDown() throws Exception {
		Iterator<DefaultScopedEntry> iterator =
			_defaultScopedEntries.iterator();

		while (iterator.hasNext()) {
			_persistence.remove(iterator.next());

			iterator.remove();
		}
	}

	@Test
	public void testCreate() throws Exception {
		long pk = RandomTestUtil.nextLong();

		DefaultScopedEntry defaultScopedEntry = _persistence.create(pk);

		Assert.assertNotNull(defaultScopedEntry);

		Assert.assertEquals(defaultScopedEntry.getPrimaryKey(), pk);
	}

	@Test
	public void testRemove() throws Exception {
		DefaultScopedEntry newDefaultScopedEntry = addDefaultScopedEntry();

		_persistence.remove(newDefaultScopedEntry);

		DefaultScopedEntry existingDefaultScopedEntry =
			_persistence.fetchByPrimaryKey(
				newDefaultScopedEntry.getPrimaryKey());

		Assert.assertNull(existingDefaultScopedEntry);
	}

	@Test
	public void testUpdateNew() throws Exception {
		addDefaultScopedEntry();
	}

	@Test
	public void testUpdateExisting() throws Exception {
		long pk = RandomTestUtil.nextLong();

		DefaultScopedEntry newDefaultScopedEntry = _persistence.create(pk);

		newDefaultScopedEntry.setExternalReferenceCode(
			RandomTestUtil.randomString());

		newDefaultScopedEntry.setCompanyId(RandomTestUtil.nextLong());

		_defaultScopedEntries.add(_persistence.update(newDefaultScopedEntry));

		DefaultScopedEntry existingDefaultScopedEntry =
			_persistence.findByPrimaryKey(
				newDefaultScopedEntry.getPrimaryKey());

		Assert.assertEquals(
			existingDefaultScopedEntry.getExternalReferenceCode(),
			newDefaultScopedEntry.getExternalReferenceCode());
		Assert.assertEquals(
			existingDefaultScopedEntry.getDefaultScopedEntryId(),
			newDefaultScopedEntry.getDefaultScopedEntryId());
		Assert.assertEquals(
			existingDefaultScopedEntry.getCompanyId(),
			newDefaultScopedEntry.getCompanyId());
	}

	@Test
	public void testCountByC_ERC() throws Exception {
		_persistence.countByC_ERC(RandomTestUtil.nextLong(), "");

		_persistence.countByC_ERC(0L, "null");

		_persistence.countByC_ERC(0L, (String)null);
	}

	@Test
	public void testFindByPrimaryKeyExisting() throws Exception {
		DefaultScopedEntry newDefaultScopedEntry = addDefaultScopedEntry();

		DefaultScopedEntry existingDefaultScopedEntry =
			_persistence.findByPrimaryKey(
				newDefaultScopedEntry.getPrimaryKey());

		Assert.assertEquals(existingDefaultScopedEntry, newDefaultScopedEntry);
	}

	@Test(expected = NoSuchDefaultScopedEntryException.class)
	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		_persistence.findByPrimaryKey(pk);
	}

	@Test
	public void testFindAll() throws Exception {
		_persistence.findAll(
			QueryUtil.ALL_POS, QueryUtil.ALL_POS, getOrderByComparator());
	}

	protected OrderByComparator<DefaultScopedEntry> getOrderByComparator() {
		return OrderByComparatorFactoryUtil.create(
			"DefaultScopedEntry", "externalReferenceCode", true,
			"DefaultScopedEntryId", true, "companyId", true);
	}

	@Test
	public void testFetchByPrimaryKeyExisting() throws Exception {
		DefaultScopedEntry newDefaultScopedEntry = addDefaultScopedEntry();

		DefaultScopedEntry existingDefaultScopedEntry =
			_persistence.fetchByPrimaryKey(
				newDefaultScopedEntry.getPrimaryKey());

		Assert.assertEquals(existingDefaultScopedEntry, newDefaultScopedEntry);
	}

	@Test
	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		DefaultScopedEntry missingDefaultScopedEntry =
			_persistence.fetchByPrimaryKey(pk);

		Assert.assertNull(missingDefaultScopedEntry);
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereAllPrimaryKeysExist()
		throws Exception {

		DefaultScopedEntry newDefaultScopedEntry1 = addDefaultScopedEntry();
		DefaultScopedEntry newDefaultScopedEntry2 = addDefaultScopedEntry();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newDefaultScopedEntry1.getPrimaryKey());
		primaryKeys.add(newDefaultScopedEntry2.getPrimaryKey());

		Map<Serializable, DefaultScopedEntry> defaultScopedEntries =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(2, defaultScopedEntries.size());
		Assert.assertEquals(
			newDefaultScopedEntry1,
			defaultScopedEntries.get(newDefaultScopedEntry1.getPrimaryKey()));
		Assert.assertEquals(
			newDefaultScopedEntry2,
			defaultScopedEntries.get(newDefaultScopedEntry2.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereNoPrimaryKeysExist()
		throws Exception {

		long pk1 = RandomTestUtil.nextLong();

		long pk2 = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(pk1);
		primaryKeys.add(pk2);

		Map<Serializable, DefaultScopedEntry> defaultScopedEntries =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(defaultScopedEntries.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereSomePrimaryKeysExist()
		throws Exception {

		DefaultScopedEntry newDefaultScopedEntry = addDefaultScopedEntry();

		long pk = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newDefaultScopedEntry.getPrimaryKey());
		primaryKeys.add(pk);

		Map<Serializable, DefaultScopedEntry> defaultScopedEntries =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, defaultScopedEntries.size());
		Assert.assertEquals(
			newDefaultScopedEntry,
			defaultScopedEntries.get(newDefaultScopedEntry.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithNoPrimaryKeys() throws Exception {
		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		Map<Serializable, DefaultScopedEntry> defaultScopedEntries =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(defaultScopedEntries.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithOnePrimaryKey() throws Exception {
		DefaultScopedEntry newDefaultScopedEntry = addDefaultScopedEntry();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newDefaultScopedEntry.getPrimaryKey());

		Map<Serializable, DefaultScopedEntry> defaultScopedEntries =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, defaultScopedEntries.size());
		Assert.assertEquals(
			newDefaultScopedEntry,
			defaultScopedEntries.get(newDefaultScopedEntry.getPrimaryKey()));
	}

	@Test
	public void testActionableDynamicQuery() throws Exception {
		final IntegerWrapper count = new IntegerWrapper();

		ActionableDynamicQuery actionableDynamicQuery =
			DefaultScopedEntryLocalServiceUtil.getActionableDynamicQuery();

		actionableDynamicQuery.setPerformActionMethod(
			new ActionableDynamicQuery.PerformActionMethod
				<DefaultScopedEntry>() {

				@Override
				public void performAction(
					DefaultScopedEntry defaultScopedEntry) {

					Assert.assertNotNull(defaultScopedEntry);

					count.increment();
				}

			});

		actionableDynamicQuery.performActions();

		Assert.assertEquals(count.getValue(), _persistence.countAll());
	}

	@Test
	public void testDynamicQueryByPrimaryKeyExisting() throws Exception {
		DefaultScopedEntry newDefaultScopedEntry = addDefaultScopedEntry();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			DefaultScopedEntry.class, _dynamicQueryClassLoader);

		dynamicQuery.add(
			RestrictionsFactoryUtil.eq(
				"DefaultScopedEntryId",
				newDefaultScopedEntry.getDefaultScopedEntryId()));

		List<DefaultScopedEntry> result = _persistence.findWithDynamicQuery(
			dynamicQuery);

		Assert.assertEquals(1, result.size());

		DefaultScopedEntry existingDefaultScopedEntry = result.get(0);

		Assert.assertEquals(existingDefaultScopedEntry, newDefaultScopedEntry);
	}

	@Test
	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			DefaultScopedEntry.class, _dynamicQueryClassLoader);

		dynamicQuery.add(
			RestrictionsFactoryUtil.eq(
				"DefaultScopedEntryId", RandomTestUtil.nextLong()));

		List<DefaultScopedEntry> result = _persistence.findWithDynamicQuery(
			dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testDynamicQueryByProjectionExisting() throws Exception {
		DefaultScopedEntry newDefaultScopedEntry = addDefaultScopedEntry();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			DefaultScopedEntry.class, _dynamicQueryClassLoader);

		dynamicQuery.setProjection(
			ProjectionFactoryUtil.property("DefaultScopedEntryId"));

		Object newDefaultScopedEntryId =
			newDefaultScopedEntry.getDefaultScopedEntryId();

		dynamicQuery.add(
			RestrictionsFactoryUtil.in(
				"DefaultScopedEntryId",
				new Object[] {newDefaultScopedEntryId}));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		Object existingDefaultScopedEntryId = result.get(0);

		Assert.assertEquals(
			existingDefaultScopedEntryId, newDefaultScopedEntryId);
	}

	@Test
	public void testDynamicQueryByProjectionMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			DefaultScopedEntry.class, _dynamicQueryClassLoader);

		dynamicQuery.setProjection(
			ProjectionFactoryUtil.property("DefaultScopedEntryId"));

		dynamicQuery.add(
			RestrictionsFactoryUtil.in(
				"DefaultScopedEntryId",
				new Object[] {RandomTestUtil.nextLong()}));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testResetOriginalValues() throws Exception {
		DefaultScopedEntry newDefaultScopedEntry = addDefaultScopedEntry();

		_persistence.clearCache();

		_assertOriginalValues(
			_persistence.findByPrimaryKey(
				newDefaultScopedEntry.getPrimaryKey()));
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

		DefaultScopedEntry newDefaultScopedEntry = addDefaultScopedEntry();

		if (clearSession) {
			Session session = _persistence.openSession();

			session.flush();

			session.clear();
		}

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			DefaultScopedEntry.class, _dynamicQueryClassLoader);

		dynamicQuery.add(
			RestrictionsFactoryUtil.eq(
				"DefaultScopedEntryId",
				newDefaultScopedEntry.getDefaultScopedEntryId()));

		List<DefaultScopedEntry> result = _persistence.findWithDynamicQuery(
			dynamicQuery);

		_assertOriginalValues(result.get(0));
	}

	private void _assertOriginalValues(DefaultScopedEntry defaultScopedEntry) {
		Assert.assertEquals(
			Long.valueOf(defaultScopedEntry.getCompanyId()),
			ReflectionTestUtil.<Long>invoke(
				defaultScopedEntry, "getColumnOriginalValue",
				new Class<?>[] {String.class}, "companyId"));
		Assert.assertEquals(
			defaultScopedEntry.getExternalReferenceCode(),
			ReflectionTestUtil.invoke(
				defaultScopedEntry, "getColumnOriginalValue",
				new Class<?>[] {String.class}, "externalReferenceCode"));
	}

	protected DefaultScopedEntry addDefaultScopedEntry() throws Exception {
		long pk = RandomTestUtil.nextLong();

		DefaultScopedEntry defaultScopedEntry = _persistence.create(pk);

		defaultScopedEntry.setExternalReferenceCode(
			RandomTestUtil.randomString());

		defaultScopedEntry.setCompanyId(RandomTestUtil.nextLong());

		_defaultScopedEntries.add(_persistence.update(defaultScopedEntry));

		return defaultScopedEntry;
	}

	private List<DefaultScopedEntry> _defaultScopedEntries =
		new ArrayList<DefaultScopedEntry>();
	private DefaultScopedEntryPersistence _persistence;
	private ClassLoader _dynamicQueryClassLoader;

}