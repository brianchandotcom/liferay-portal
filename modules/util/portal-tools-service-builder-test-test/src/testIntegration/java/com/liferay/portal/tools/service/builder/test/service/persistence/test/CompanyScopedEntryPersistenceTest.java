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
import com.liferay.portal.tools.service.builder.test.exception.NoSuchCompanyScopedEntryException;
import com.liferay.portal.tools.service.builder.test.model.CompanyScopedEntry;
import com.liferay.portal.tools.service.builder.test.service.CompanyScopedEntryLocalServiceUtil;
import com.liferay.portal.tools.service.builder.test.service.persistence.CompanyScopedEntryPersistence;
import com.liferay.portal.tools.service.builder.test.service.persistence.CompanyScopedEntryUtil;

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
public class CompanyScopedEntryPersistenceTest {

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
		_persistence = CompanyScopedEntryUtil.getPersistence();

		Class<?> clazz = _persistence.getClass();

		_dynamicQueryClassLoader = clazz.getClassLoader();
	}

	@After
	public void tearDown() throws Exception {
		Iterator<CompanyScopedEntry> iterator =
			_companyScopedEntries.iterator();

		while (iterator.hasNext()) {
			_persistence.remove(iterator.next());

			iterator.remove();
		}
	}

	@Test
	public void testCreate() throws Exception {
		long pk = RandomTestUtil.nextLong();

		CompanyScopedEntry companyScopedEntry = _persistence.create(pk);

		Assert.assertNotNull(companyScopedEntry);

		Assert.assertEquals(companyScopedEntry.getPrimaryKey(), pk);
	}

	@Test
	public void testRemove() throws Exception {
		CompanyScopedEntry newCompanyScopedEntry = addCompanyScopedEntry();

		_persistence.remove(newCompanyScopedEntry);

		CompanyScopedEntry existingCompanyScopedEntry =
			_persistence.fetchByPrimaryKey(
				newCompanyScopedEntry.getPrimaryKey());

		Assert.assertNull(existingCompanyScopedEntry);
	}

	@Test
	public void testUpdateNew() throws Exception {
		addCompanyScopedEntry();
	}

	@Test
	public void testUpdateExisting() throws Exception {
		long pk = RandomTestUtil.nextLong();

		CompanyScopedEntry newCompanyScopedEntry = _persistence.create(pk);

		newCompanyScopedEntry.setExternalReferenceCode(
			RandomTestUtil.randomString());

		newCompanyScopedEntry.setCompanyId(RandomTestUtil.nextLong());

		_companyScopedEntries.add(_persistence.update(newCompanyScopedEntry));

		CompanyScopedEntry existingCompanyScopedEntry =
			_persistence.findByPrimaryKey(
				newCompanyScopedEntry.getPrimaryKey());

		Assert.assertEquals(
			existingCompanyScopedEntry.getExternalReferenceCode(),
			newCompanyScopedEntry.getExternalReferenceCode());
		Assert.assertEquals(
			existingCompanyScopedEntry.getCompanyScopedEntryId(),
			newCompanyScopedEntry.getCompanyScopedEntryId());
		Assert.assertEquals(
			existingCompanyScopedEntry.getCompanyId(),
			newCompanyScopedEntry.getCompanyId());
	}

	@Test
	public void testCountByC_ERC() throws Exception {
		_persistence.countByC_ERC(RandomTestUtil.nextLong(), "");

		_persistence.countByC_ERC(0L, "null");

		_persistence.countByC_ERC(0L, (String)null);
	}

	@Test
	public void testFindByPrimaryKeyExisting() throws Exception {
		CompanyScopedEntry newCompanyScopedEntry = addCompanyScopedEntry();

		CompanyScopedEntry existingCompanyScopedEntry =
			_persistence.findByPrimaryKey(
				newCompanyScopedEntry.getPrimaryKey());

		Assert.assertEquals(existingCompanyScopedEntry, newCompanyScopedEntry);
	}

	@Test(expected = NoSuchCompanyScopedEntryException.class)
	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		_persistence.findByPrimaryKey(pk);
	}

	@Test
	public void testFindAll() throws Exception {
		_persistence.findAll(
			QueryUtil.ALL_POS, QueryUtil.ALL_POS, getOrderByComparator());
	}

	protected OrderByComparator<CompanyScopedEntry> getOrderByComparator() {
		return OrderByComparatorFactoryUtil.create(
			"CompanyScopedEntry", "externalReferenceCode", true,
			"CompanyScopedEntryId", true, "companyId", true);
	}

	@Test
	public void testFetchByPrimaryKeyExisting() throws Exception {
		CompanyScopedEntry newCompanyScopedEntry = addCompanyScopedEntry();

		CompanyScopedEntry existingCompanyScopedEntry =
			_persistence.fetchByPrimaryKey(
				newCompanyScopedEntry.getPrimaryKey());

		Assert.assertEquals(existingCompanyScopedEntry, newCompanyScopedEntry);
	}

	@Test
	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		CompanyScopedEntry missingCompanyScopedEntry =
			_persistence.fetchByPrimaryKey(pk);

		Assert.assertNull(missingCompanyScopedEntry);
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereAllPrimaryKeysExist()
		throws Exception {

		CompanyScopedEntry newCompanyScopedEntry1 = addCompanyScopedEntry();
		CompanyScopedEntry newCompanyScopedEntry2 = addCompanyScopedEntry();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newCompanyScopedEntry1.getPrimaryKey());
		primaryKeys.add(newCompanyScopedEntry2.getPrimaryKey());

		Map<Serializable, CompanyScopedEntry> companyScopedEntries =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(2, companyScopedEntries.size());
		Assert.assertEquals(
			newCompanyScopedEntry1,
			companyScopedEntries.get(newCompanyScopedEntry1.getPrimaryKey()));
		Assert.assertEquals(
			newCompanyScopedEntry2,
			companyScopedEntries.get(newCompanyScopedEntry2.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereNoPrimaryKeysExist()
		throws Exception {

		long pk1 = RandomTestUtil.nextLong();

		long pk2 = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(pk1);
		primaryKeys.add(pk2);

		Map<Serializable, CompanyScopedEntry> companyScopedEntries =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(companyScopedEntries.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereSomePrimaryKeysExist()
		throws Exception {

		CompanyScopedEntry newCompanyScopedEntry = addCompanyScopedEntry();

		long pk = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newCompanyScopedEntry.getPrimaryKey());
		primaryKeys.add(pk);

		Map<Serializable, CompanyScopedEntry> companyScopedEntries =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, companyScopedEntries.size());
		Assert.assertEquals(
			newCompanyScopedEntry,
			companyScopedEntries.get(newCompanyScopedEntry.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithNoPrimaryKeys() throws Exception {
		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		Map<Serializable, CompanyScopedEntry> companyScopedEntries =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(companyScopedEntries.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithOnePrimaryKey() throws Exception {
		CompanyScopedEntry newCompanyScopedEntry = addCompanyScopedEntry();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newCompanyScopedEntry.getPrimaryKey());

		Map<Serializable, CompanyScopedEntry> companyScopedEntries =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, companyScopedEntries.size());
		Assert.assertEquals(
			newCompanyScopedEntry,
			companyScopedEntries.get(newCompanyScopedEntry.getPrimaryKey()));
	}

	@Test
	public void testActionableDynamicQuery() throws Exception {
		final IntegerWrapper count = new IntegerWrapper();

		ActionableDynamicQuery actionableDynamicQuery =
			CompanyScopedEntryLocalServiceUtil.getActionableDynamicQuery();

		actionableDynamicQuery.setPerformActionMethod(
			new ActionableDynamicQuery.PerformActionMethod
				<CompanyScopedEntry>() {

				@Override
				public void performAction(
					CompanyScopedEntry companyScopedEntry) {

					Assert.assertNotNull(companyScopedEntry);

					count.increment();
				}

			});

		actionableDynamicQuery.performActions();

		Assert.assertEquals(count.getValue(), _persistence.countAll());
	}

	@Test
	public void testDynamicQueryByPrimaryKeyExisting() throws Exception {
		CompanyScopedEntry newCompanyScopedEntry = addCompanyScopedEntry();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			CompanyScopedEntry.class, _dynamicQueryClassLoader);

		dynamicQuery.add(
			RestrictionsFactoryUtil.eq(
				"CompanyScopedEntryId",
				newCompanyScopedEntry.getCompanyScopedEntryId()));

		List<CompanyScopedEntry> result = _persistence.findWithDynamicQuery(
			dynamicQuery);

		Assert.assertEquals(1, result.size());

		CompanyScopedEntry existingCompanyScopedEntry = result.get(0);

		Assert.assertEquals(existingCompanyScopedEntry, newCompanyScopedEntry);
	}

	@Test
	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			CompanyScopedEntry.class, _dynamicQueryClassLoader);

		dynamicQuery.add(
			RestrictionsFactoryUtil.eq(
				"CompanyScopedEntryId", RandomTestUtil.nextLong()));

		List<CompanyScopedEntry> result = _persistence.findWithDynamicQuery(
			dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testDynamicQueryByProjectionExisting() throws Exception {
		CompanyScopedEntry newCompanyScopedEntry = addCompanyScopedEntry();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			CompanyScopedEntry.class, _dynamicQueryClassLoader);

		dynamicQuery.setProjection(
			ProjectionFactoryUtil.property("CompanyScopedEntryId"));

		Object newCompanyScopedEntryId =
			newCompanyScopedEntry.getCompanyScopedEntryId();

		dynamicQuery.add(
			RestrictionsFactoryUtil.in(
				"CompanyScopedEntryId",
				new Object[] {newCompanyScopedEntryId}));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		Object existingCompanyScopedEntryId = result.get(0);

		Assert.assertEquals(
			existingCompanyScopedEntryId, newCompanyScopedEntryId);
	}

	@Test
	public void testDynamicQueryByProjectionMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			CompanyScopedEntry.class, _dynamicQueryClassLoader);

		dynamicQuery.setProjection(
			ProjectionFactoryUtil.property("CompanyScopedEntryId"));

		dynamicQuery.add(
			RestrictionsFactoryUtil.in(
				"CompanyScopedEntryId",
				new Object[] {RandomTestUtil.nextLong()}));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testResetOriginalValues() throws Exception {
		CompanyScopedEntry newCompanyScopedEntry = addCompanyScopedEntry();

		_persistence.clearCache();

		_assertOriginalValues(
			_persistence.findByPrimaryKey(
				newCompanyScopedEntry.getPrimaryKey()));
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

		CompanyScopedEntry newCompanyScopedEntry = addCompanyScopedEntry();

		if (clearSession) {
			Session session = _persistence.openSession();

			session.flush();

			session.clear();
		}

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			CompanyScopedEntry.class, _dynamicQueryClassLoader);

		dynamicQuery.add(
			RestrictionsFactoryUtil.eq(
				"CompanyScopedEntryId",
				newCompanyScopedEntry.getCompanyScopedEntryId()));

		List<CompanyScopedEntry> result = _persistence.findWithDynamicQuery(
			dynamicQuery);

		_assertOriginalValues(result.get(0));
	}

	private void _assertOriginalValues(CompanyScopedEntry companyScopedEntry) {
		Assert.assertEquals(
			Long.valueOf(companyScopedEntry.getCompanyId()),
			ReflectionTestUtil.<Long>invoke(
				companyScopedEntry, "getColumnOriginalValue",
				new Class<?>[] {String.class}, "companyId"));
		Assert.assertEquals(
			companyScopedEntry.getExternalReferenceCode(),
			ReflectionTestUtil.invoke(
				companyScopedEntry, "getColumnOriginalValue",
				new Class<?>[] {String.class}, "externalReferenceCode"));
	}

	protected CompanyScopedEntry addCompanyScopedEntry() throws Exception {
		long pk = RandomTestUtil.nextLong();

		CompanyScopedEntry companyScopedEntry = _persistence.create(pk);

		companyScopedEntry.setExternalReferenceCode(
			RandomTestUtil.randomString());

		companyScopedEntry.setCompanyId(RandomTestUtil.nextLong());

		_companyScopedEntries.add(_persistence.update(companyScopedEntry));

		return companyScopedEntry;
	}

	private List<CompanyScopedEntry> _companyScopedEntries =
		new ArrayList<CompanyScopedEntry>();
	private CompanyScopedEntryPersistence _persistence;
	private ClassLoader _dynamicQueryClassLoader;

}