/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.tools.service.builder.test.service.persistence.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
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
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PersistenceTestRule;
import com.liferay.portal.test.rule.TransactionalTestRule;
import com.liferay.portal.tools.service.builder.test.exception.NoSuchKeywordsEntryException;
import com.liferay.portal.tools.service.builder.test.model.KeywordsEntry;
import com.liferay.portal.tools.service.builder.test.service.KeywordsEntryLocalServiceUtil;
import com.liferay.portal.tools.service.builder.test.service.persistence.KeywordsEntryPersistence;
import com.liferay.portal.tools.service.builder.test.service.persistence.KeywordsEntryUtil;

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
public class KeywordsEntryPersistenceTest {

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
		_persistence = KeywordsEntryUtil.getPersistence();

		Class<?> clazz = _persistence.getClass();

		_dynamicQueryClassLoader = clazz.getClassLoader();
	}

	@After
	public void tearDown() throws Exception {
		Iterator<KeywordsEntry> iterator = _keywordsEntries.iterator();

		while (iterator.hasNext()) {
			_persistence.remove(iterator.next());

			iterator.remove();
		}
	}

	@Test
	public void testCreate() throws Exception {
		long pk = RandomTestUtil.nextLong();

		KeywordsEntry keywordsEntry = _persistence.create(pk);

		Assert.assertNotNull(keywordsEntry);

		Assert.assertEquals(keywordsEntry.getPrimaryKey(), pk);
	}

	@Test
	public void testRemove() throws Exception {
		KeywordsEntry newKeywordsEntry = addKeywordsEntry();

		_persistence.remove(newKeywordsEntry);

		KeywordsEntry existingKeywordsEntry = _persistence.fetchByPrimaryKey(
			newKeywordsEntry.getPrimaryKey());

		Assert.assertNull(existingKeywordsEntry);
	}

	@Test
	public void testUpdateNew() throws Exception {
		addKeywordsEntry();
	}

	@Test
	public void testUpdateExisting() throws Exception {
		long pk = RandomTestUtil.nextLong();

		KeywordsEntry newKeywordsEntry = _persistence.create(pk);

		newKeywordsEntry.setCompanyId(RandomTestUtil.nextLong());

		newKeywordsEntry.setName(RandomTestUtil.randomString());

		_keywordsEntries.add(_persistence.update(newKeywordsEntry));

		KeywordsEntry existingKeywordsEntry = _persistence.findByPrimaryKey(
			newKeywordsEntry.getPrimaryKey());

		Assert.assertEquals(
			existingKeywordsEntry.getKeywordsEntryId(),
			newKeywordsEntry.getKeywordsEntryId());
		Assert.assertEquals(
			existingKeywordsEntry.getCompanyId(),
			newKeywordsEntry.getCompanyId());
		Assert.assertEquals(
			existingKeywordsEntry.getName(), newKeywordsEntry.getName());
	}

	@Test
	public void testFindByPrimaryKeyExisting() throws Exception {
		KeywordsEntry newKeywordsEntry = addKeywordsEntry();

		KeywordsEntry existingKeywordsEntry = _persistence.findByPrimaryKey(
			newKeywordsEntry.getPrimaryKey());

		Assert.assertEquals(existingKeywordsEntry, newKeywordsEntry);
	}

	@Test(expected = NoSuchKeywordsEntryException.class)
	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		_persistence.findByPrimaryKey(pk);
	}

	@Test
	public void testFindAll() throws Exception {
		_persistence.findAll(
			QueryUtil.ALL_POS, QueryUtil.ALL_POS, getOrderByComparator());
	}

	protected OrderByComparator<KeywordsEntry> getOrderByComparator() {
		return OrderByComparatorFactoryUtil.create(
			"KeywordsEntry", "keywordsEntryId", true, "companyId", true, "name",
			true);
	}

	@Test
	public void testFetchByPrimaryKeyExisting() throws Exception {
		KeywordsEntry newKeywordsEntry = addKeywordsEntry();

		KeywordsEntry existingKeywordsEntry = _persistence.fetchByPrimaryKey(
			newKeywordsEntry.getPrimaryKey());

		Assert.assertEquals(existingKeywordsEntry, newKeywordsEntry);
	}

	@Test
	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		KeywordsEntry missingKeywordsEntry = _persistence.fetchByPrimaryKey(pk);

		Assert.assertNull(missingKeywordsEntry);
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereAllPrimaryKeysExist()
		throws Exception {

		KeywordsEntry newKeywordsEntry1 = addKeywordsEntry();
		KeywordsEntry newKeywordsEntry2 = addKeywordsEntry();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newKeywordsEntry1.getPrimaryKey());
		primaryKeys.add(newKeywordsEntry2.getPrimaryKey());

		Map<Serializable, KeywordsEntry> keywordsEntries =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(2, keywordsEntries.size());
		Assert.assertEquals(
			newKeywordsEntry1,
			keywordsEntries.get(newKeywordsEntry1.getPrimaryKey()));
		Assert.assertEquals(
			newKeywordsEntry2,
			keywordsEntries.get(newKeywordsEntry2.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereNoPrimaryKeysExist()
		throws Exception {

		long pk1 = RandomTestUtil.nextLong();

		long pk2 = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(pk1);
		primaryKeys.add(pk2);

		Map<Serializable, KeywordsEntry> keywordsEntries =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(keywordsEntries.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereSomePrimaryKeysExist()
		throws Exception {

		KeywordsEntry newKeywordsEntry = addKeywordsEntry();

		long pk = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newKeywordsEntry.getPrimaryKey());
		primaryKeys.add(pk);

		Map<Serializable, KeywordsEntry> keywordsEntries =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, keywordsEntries.size());
		Assert.assertEquals(
			newKeywordsEntry,
			keywordsEntries.get(newKeywordsEntry.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithNoPrimaryKeys() throws Exception {
		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		Map<Serializable, KeywordsEntry> keywordsEntries =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(keywordsEntries.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithOnePrimaryKey() throws Exception {
		KeywordsEntry newKeywordsEntry = addKeywordsEntry();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newKeywordsEntry.getPrimaryKey());

		Map<Serializable, KeywordsEntry> keywordsEntries =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, keywordsEntries.size());
		Assert.assertEquals(
			newKeywordsEntry,
			keywordsEntries.get(newKeywordsEntry.getPrimaryKey()));
	}

	@Test
	public void testActionableDynamicQuery() throws Exception {
		final IntegerWrapper count = new IntegerWrapper();

		ActionableDynamicQuery actionableDynamicQuery =
			KeywordsEntryLocalServiceUtil.getActionableDynamicQuery();

		actionableDynamicQuery.setPerformActionMethod(
			new ActionableDynamicQuery.PerformActionMethod<KeywordsEntry>() {

				@Override
				public void performAction(KeywordsEntry keywordsEntry) {
					Assert.assertNotNull(keywordsEntry);

					count.increment();
				}

			});

		actionableDynamicQuery.performActions();

		Assert.assertEquals(count.getValue(), _persistence.countAll());
	}

	@Test
	public void testDynamicQueryByPrimaryKeyExisting() throws Exception {
		KeywordsEntry newKeywordsEntry = addKeywordsEntry();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			KeywordsEntry.class, _dynamicQueryClassLoader);

		dynamicQuery.add(
			RestrictionsFactoryUtil.eq(
				"keywordsEntryId", newKeywordsEntry.getKeywordsEntryId()));

		List<KeywordsEntry> result = _persistence.findWithDynamicQuery(
			dynamicQuery);

		Assert.assertEquals(1, result.size());

		KeywordsEntry existingKeywordsEntry = result.get(0);

		Assert.assertEquals(existingKeywordsEntry, newKeywordsEntry);
	}

	@Test
	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			KeywordsEntry.class, _dynamicQueryClassLoader);

		dynamicQuery.add(
			RestrictionsFactoryUtil.eq(
				"keywordsEntryId", RandomTestUtil.nextLong()));

		List<KeywordsEntry> result = _persistence.findWithDynamicQuery(
			dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testDynamicQueryByProjectionExisting() throws Exception {
		KeywordsEntry newKeywordsEntry = addKeywordsEntry();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			KeywordsEntry.class, _dynamicQueryClassLoader);

		dynamicQuery.setProjection(
			ProjectionFactoryUtil.property("keywordsEntryId"));

		Object newKeywordsEntryId = newKeywordsEntry.getKeywordsEntryId();

		dynamicQuery.add(
			RestrictionsFactoryUtil.in(
				"keywordsEntryId", new Object[] {newKeywordsEntryId}));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		Object existingKeywordsEntryId = result.get(0);

		Assert.assertEquals(existingKeywordsEntryId, newKeywordsEntryId);
	}

	@Test
	public void testDynamicQueryByProjectionMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			KeywordsEntry.class, _dynamicQueryClassLoader);

		dynamicQuery.setProjection(
			ProjectionFactoryUtil.property("keywordsEntryId"));

		dynamicQuery.add(
			RestrictionsFactoryUtil.in(
				"keywordsEntryId", new Object[] {RandomTestUtil.nextLong()}));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	protected KeywordsEntry addKeywordsEntry() throws Exception {
		long pk = RandomTestUtil.nextLong();

		KeywordsEntry keywordsEntry = _persistence.create(pk);

		keywordsEntry.setCompanyId(RandomTestUtil.nextLong());

		keywordsEntry.setName(RandomTestUtil.randomString());

		_keywordsEntries.add(_persistence.update(keywordsEntry));

		return keywordsEntry;
	}

	private List<KeywordsEntry> _keywordsEntries =
		new ArrayList<KeywordsEntry>();
	private KeywordsEntryPersistence _persistence;
	private ClassLoader _dynamicQueryClassLoader;

}