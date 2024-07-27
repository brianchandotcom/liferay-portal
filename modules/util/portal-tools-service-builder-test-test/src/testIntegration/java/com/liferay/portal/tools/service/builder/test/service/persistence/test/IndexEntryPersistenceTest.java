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
import com.liferay.portal.tools.service.builder.test.exception.NoSuchIndexEntryException;
import com.liferay.portal.tools.service.builder.test.model.IndexEntry;
import com.liferay.portal.tools.service.builder.test.service.IndexEntryLocalServiceUtil;
import com.liferay.portal.tools.service.builder.test.service.persistence.IndexEntryPersistence;
import com.liferay.portal.tools.service.builder.test.service.persistence.IndexEntryUtil;

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
public class IndexEntryPersistenceTest {

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
		_persistence = IndexEntryUtil.getPersistence();

		Class<?> clazz = _persistence.getClass();

		_dynamicQueryClassLoader = clazz.getClassLoader();
	}

	@After
	public void tearDown() throws Exception {
		Iterator<IndexEntry> iterator = _indexEntries.iterator();

		while (iterator.hasNext()) {
			_persistence.remove(iterator.next());

			iterator.remove();
		}
	}

	@Test
	public void testCreate() throws Exception {
		long pk = RandomTestUtil.nextLong();

		IndexEntry indexEntry = _persistence.create(pk);

		Assert.assertNotNull(indexEntry);

		Assert.assertEquals(indexEntry.getPrimaryKey(), pk);
	}

	@Test
	public void testRemove() throws Exception {
		IndexEntry newIndexEntry = addIndexEntry();

		_persistence.remove(newIndexEntry);

		IndexEntry existingIndexEntry = _persistence.fetchByPrimaryKey(
			newIndexEntry.getPrimaryKey());

		Assert.assertNull(existingIndexEntry);
	}

	@Test
	public void testUpdateNew() throws Exception {
		addIndexEntry();
	}

	@Test
	public void testUpdateExisting() throws Exception {
		long pk = RandomTestUtil.nextLong();

		IndexEntry newIndexEntry = _persistence.create(pk);

		newIndexEntry.setCompanyId(RandomTestUtil.nextLong());

		newIndexEntry.setName(RandomTestUtil.randomString());

		_indexEntries.add(_persistence.update(newIndexEntry));

		IndexEntry existingIndexEntry = _persistence.findByPrimaryKey(
			newIndexEntry.getPrimaryKey());

		Assert.assertEquals(
			existingIndexEntry.getIndexEntryId(),
			newIndexEntry.getIndexEntryId());
		Assert.assertEquals(
			existingIndexEntry.getCompanyId(), newIndexEntry.getCompanyId());
		Assert.assertEquals(
			existingIndexEntry.getName(), newIndexEntry.getName());
	}

	@Test
	public void testFindByPrimaryKeyExisting() throws Exception {
		IndexEntry newIndexEntry = addIndexEntry();

		IndexEntry existingIndexEntry = _persistence.findByPrimaryKey(
			newIndexEntry.getPrimaryKey());

		Assert.assertEquals(existingIndexEntry, newIndexEntry);
	}

	@Test(expected = NoSuchIndexEntryException.class)
	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		_persistence.findByPrimaryKey(pk);
	}

	@Test
	public void testFindAll() throws Exception {
		_persistence.findAll(
			QueryUtil.ALL_POS, QueryUtil.ALL_POS, getOrderByComparator());
	}

	protected OrderByComparator<IndexEntry> getOrderByComparator() {
		return OrderByComparatorFactoryUtil.create(
			"IndexEntry", "indexEntryId", true, "companyId", true, "name",
			true);
	}

	@Test
	public void testFetchByPrimaryKeyExisting() throws Exception {
		IndexEntry newIndexEntry = addIndexEntry();

		IndexEntry existingIndexEntry = _persistence.fetchByPrimaryKey(
			newIndexEntry.getPrimaryKey());

		Assert.assertEquals(existingIndexEntry, newIndexEntry);
	}

	@Test
	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		IndexEntry missingIndexEntry = _persistence.fetchByPrimaryKey(pk);

		Assert.assertNull(missingIndexEntry);
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereAllPrimaryKeysExist()
		throws Exception {

		IndexEntry newIndexEntry1 = addIndexEntry();
		IndexEntry newIndexEntry2 = addIndexEntry();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newIndexEntry1.getPrimaryKey());
		primaryKeys.add(newIndexEntry2.getPrimaryKey());

		Map<Serializable, IndexEntry> indexEntries =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(2, indexEntries.size());
		Assert.assertEquals(
			newIndexEntry1, indexEntries.get(newIndexEntry1.getPrimaryKey()));
		Assert.assertEquals(
			newIndexEntry2, indexEntries.get(newIndexEntry2.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereNoPrimaryKeysExist()
		throws Exception {

		long pk1 = RandomTestUtil.nextLong();

		long pk2 = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(pk1);
		primaryKeys.add(pk2);

		Map<Serializable, IndexEntry> indexEntries =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(indexEntries.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereSomePrimaryKeysExist()
		throws Exception {

		IndexEntry newIndexEntry = addIndexEntry();

		long pk = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newIndexEntry.getPrimaryKey());
		primaryKeys.add(pk);

		Map<Serializable, IndexEntry> indexEntries =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, indexEntries.size());
		Assert.assertEquals(
			newIndexEntry, indexEntries.get(newIndexEntry.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithNoPrimaryKeys() throws Exception {
		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		Map<Serializable, IndexEntry> indexEntries =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(indexEntries.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithOnePrimaryKey() throws Exception {
		IndexEntry newIndexEntry = addIndexEntry();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newIndexEntry.getPrimaryKey());

		Map<Serializable, IndexEntry> indexEntries =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, indexEntries.size());
		Assert.assertEquals(
			newIndexEntry, indexEntries.get(newIndexEntry.getPrimaryKey()));
	}

	@Test
	public void testActionableDynamicQuery() throws Exception {
		final IntegerWrapper count = new IntegerWrapper();

		ActionableDynamicQuery actionableDynamicQuery =
			IndexEntryLocalServiceUtil.getActionableDynamicQuery();

		actionableDynamicQuery.setPerformActionMethod(
			new ActionableDynamicQuery.PerformActionMethod<IndexEntry>() {

				@Override
				public void performAction(IndexEntry indexEntry) {
					Assert.assertNotNull(indexEntry);

					count.increment();
				}

			});

		actionableDynamicQuery.performActions();

		Assert.assertEquals(count.getValue(), _persistence.countAll());
	}

	@Test
	public void testDynamicQueryByPrimaryKeyExisting() throws Exception {
		IndexEntry newIndexEntry = addIndexEntry();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			IndexEntry.class, _dynamicQueryClassLoader);

		dynamicQuery.add(
			RestrictionsFactoryUtil.eq(
				"indexEntryId", newIndexEntry.getIndexEntryId()));

		List<IndexEntry> result = _persistence.findWithDynamicQuery(
			dynamicQuery);

		Assert.assertEquals(1, result.size());

		IndexEntry existingIndexEntry = result.get(0);

		Assert.assertEquals(existingIndexEntry, newIndexEntry);
	}

	@Test
	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			IndexEntry.class, _dynamicQueryClassLoader);

		dynamicQuery.add(
			RestrictionsFactoryUtil.eq(
				"indexEntryId", RandomTestUtil.nextLong()));

		List<IndexEntry> result = _persistence.findWithDynamicQuery(
			dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testDynamicQueryByProjectionExisting() throws Exception {
		IndexEntry newIndexEntry = addIndexEntry();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			IndexEntry.class, _dynamicQueryClassLoader);

		dynamicQuery.setProjection(
			ProjectionFactoryUtil.property("indexEntryId"));

		Object newIndexEntryId = newIndexEntry.getIndexEntryId();

		dynamicQuery.add(
			RestrictionsFactoryUtil.in(
				"indexEntryId", new Object[] {newIndexEntryId}));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		Object existingIndexEntryId = result.get(0);

		Assert.assertEquals(existingIndexEntryId, newIndexEntryId);
	}

	@Test
	public void testDynamicQueryByProjectionMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			IndexEntry.class, _dynamicQueryClassLoader);

		dynamicQuery.setProjection(
			ProjectionFactoryUtil.property("indexEntryId"));

		dynamicQuery.add(
			RestrictionsFactoryUtil.in(
				"indexEntryId", new Object[] {RandomTestUtil.nextLong()}));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	protected IndexEntry addIndexEntry() throws Exception {
		long pk = RandomTestUtil.nextLong();

		IndexEntry indexEntry = _persistence.create(pk);

		indexEntry.setCompanyId(RandomTestUtil.nextLong());

		indexEntry.setName(RandomTestUtil.randomString());

		_indexEntries.add(_persistence.update(indexEntry));

		return indexEntry;
	}

	private List<IndexEntry> _indexEntries = new ArrayList<IndexEntry>();
	private IndexEntryPersistence _persistence;
	private ClassLoader _dynamicQueryClassLoader;

}