/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
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
import com.liferay.portal.kernel.util.Time;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PersistenceTestRule;
import com.liferay.portal.test.rule.TransactionalTestRule;
import com.liferay.portal.tools.service.builder.test.exception.NoSuchTimestampTypeEntryException;
import com.liferay.portal.tools.service.builder.test.model.TimestampTypeEntry;
import com.liferay.portal.tools.service.builder.test.service.TimestampTypeEntryLocalServiceUtil;
import com.liferay.portal.tools.service.builder.test.service.persistence.TimestampTypeEntryPersistence;
import com.liferay.portal.tools.service.builder.test.service.persistence.TimestampTypeEntryUtil;

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
public class TimestampTypeEntryPersistenceTest {

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
		_persistence = TimestampTypeEntryUtil.getPersistence();

		Class<?> clazz = _persistence.getClass();

		_dynamicQueryClassLoader = clazz.getClassLoader();
	}

	@After
	public void tearDown() throws Exception {
		Iterator<TimestampTypeEntry> iterator =
			_timestampTypeEntries.iterator();

		while (iterator.hasNext()) {
			_persistence.remove(iterator.next());

			iterator.remove();
		}
	}

	@Test
	public void testCreate() throws Exception {
		long pk = RandomTestUtil.nextLong();

		TimestampTypeEntry timestampTypeEntry = _persistence.create(pk);

		Assert.assertNotNull(timestampTypeEntry);

		Assert.assertEquals(timestampTypeEntry.getPrimaryKey(), pk);
	}

	@Test
	public void testRemove() throws Exception {
		TimestampTypeEntry newTimestampTypeEntry = addTimestampTypeEntry();

		_persistence.remove(newTimestampTypeEntry);

		TimestampTypeEntry existingTimestampTypeEntry =
			_persistence.fetchByPrimaryKey(
				newTimestampTypeEntry.getPrimaryKey());

		Assert.assertNull(existingTimestampTypeEntry);
	}

	@Test
	public void testUpdateNew() throws Exception {
		addTimestampTypeEntry();
	}

	@Test
	public void testUpdateExisting() throws Exception {
		TimestampTypeEntry newTimestampTypeEntry = addTimestampTypeEntry();

		newTimestampTypeEntry.setDateValue(RandomTestUtil.nextDate());

		newTimestampTypeEntry = _persistence.update(newTimestampTypeEntry);

		_timestampTypeEntries.add(newTimestampTypeEntry);

		TimestampTypeEntry existingTimestampTypeEntry =
			_persistence.findByPrimaryKey(
				newTimestampTypeEntry.getPrimaryKey());

		Assert.assertEquals(
			existingTimestampTypeEntry.getTimestampTypeEntryId(),
			newTimestampTypeEntry.getTimestampTypeEntryId());
		Assert.assertEquals(
			Time.getShortTimestamp(existingTimestampTypeEntry.getDateValue()),
			Time.getShortTimestamp(newTimestampTypeEntry.getDateValue()));
	}

	@Test
	public void testFindByPrimaryKeyExisting() throws Exception {
		TimestampTypeEntry newTimestampTypeEntry = addTimestampTypeEntry();

		TimestampTypeEntry existingTimestampTypeEntry =
			_persistence.findByPrimaryKey(
				newTimestampTypeEntry.getPrimaryKey());

		Assert.assertEquals(existingTimestampTypeEntry, newTimestampTypeEntry);
	}

	@Test(expected = NoSuchTimestampTypeEntryException.class)
	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		_persistence.findByPrimaryKey(pk);
	}

	@Test
	public void testFindAll() throws Exception {
		_persistence.findAll(
			QueryUtil.ALL_POS, QueryUtil.ALL_POS, getOrderByComparator());
	}

	protected OrderByComparator<TimestampTypeEntry> getOrderByComparator() {
		return OrderByComparatorFactoryUtil.create(
			"TimestampTypeEntry", "timestampTypeEntryId", true, "dateValue",
			true);
	}

	@Test
	public void testFetchByPrimaryKeyExisting() throws Exception {
		TimestampTypeEntry newTimestampTypeEntry = addTimestampTypeEntry();

		TimestampTypeEntry existingTimestampTypeEntry =
			_persistence.fetchByPrimaryKey(
				newTimestampTypeEntry.getPrimaryKey());

		Assert.assertEquals(existingTimestampTypeEntry, newTimestampTypeEntry);
	}

	@Test
	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		TimestampTypeEntry missingTimestampTypeEntry =
			_persistence.fetchByPrimaryKey(pk);

		Assert.assertNull(missingTimestampTypeEntry);
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereAllPrimaryKeysExist()
		throws Exception {

		TimestampTypeEntry newTimestampTypeEntry1 = addTimestampTypeEntry();
		TimestampTypeEntry newTimestampTypeEntry2 = addTimestampTypeEntry();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newTimestampTypeEntry1.getPrimaryKey());
		primaryKeys.add(newTimestampTypeEntry2.getPrimaryKey());

		Map<Serializable, TimestampTypeEntry> timestampTypeEntries =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(2, timestampTypeEntries.size());
		Assert.assertEquals(
			newTimestampTypeEntry1,
			timestampTypeEntries.get(newTimestampTypeEntry1.getPrimaryKey()));
		Assert.assertEquals(
			newTimestampTypeEntry2,
			timestampTypeEntries.get(newTimestampTypeEntry2.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereNoPrimaryKeysExist()
		throws Exception {

		long pk1 = RandomTestUtil.nextLong();

		long pk2 = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(pk1);
		primaryKeys.add(pk2);

		Map<Serializable, TimestampTypeEntry> timestampTypeEntries =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(timestampTypeEntries.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereSomePrimaryKeysExist()
		throws Exception {

		TimestampTypeEntry newTimestampTypeEntry = addTimestampTypeEntry();

		long pk = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newTimestampTypeEntry.getPrimaryKey());
		primaryKeys.add(pk);

		Map<Serializable, TimestampTypeEntry> timestampTypeEntries =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, timestampTypeEntries.size());
		Assert.assertEquals(
			newTimestampTypeEntry,
			timestampTypeEntries.get(newTimestampTypeEntry.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithNoPrimaryKeys() throws Exception {
		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		Map<Serializable, TimestampTypeEntry> timestampTypeEntries =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(timestampTypeEntries.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithOnePrimaryKey() throws Exception {
		TimestampTypeEntry newTimestampTypeEntry = addTimestampTypeEntry();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newTimestampTypeEntry.getPrimaryKey());

		Map<Serializable, TimestampTypeEntry> timestampTypeEntries =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, timestampTypeEntries.size());
		Assert.assertEquals(
			newTimestampTypeEntry,
			timestampTypeEntries.get(newTimestampTypeEntry.getPrimaryKey()));
	}

	@Test
	public void testActionableDynamicQuery() throws Exception {
		final IntegerWrapper count = new IntegerWrapper();

		ActionableDynamicQuery actionableDynamicQuery =
			TimestampTypeEntryLocalServiceUtil.getActionableDynamicQuery();

		actionableDynamicQuery.setPerformActionMethod(
			new ActionableDynamicQuery.PerformActionMethod
				<TimestampTypeEntry>() {

				@Override
				public void performAction(
					TimestampTypeEntry timestampTypeEntry) {

					Assert.assertNotNull(timestampTypeEntry);

					count.increment();
				}

			});

		actionableDynamicQuery.performActions();

		Assert.assertEquals(count.getValue(), _persistence.countAll());
	}

	@Test
	public void testDynamicQueryByPrimaryKeyExisting() throws Exception {
		TimestampTypeEntry newTimestampTypeEntry = addTimestampTypeEntry();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			TimestampTypeEntry.class, _dynamicQueryClassLoader);

		dynamicQuery.add(
			RestrictionsFactoryUtil.eq(
				"timestampTypeEntryId",
				newTimestampTypeEntry.getTimestampTypeEntryId()));

		List<TimestampTypeEntry> result = _persistence.findWithDynamicQuery(
			dynamicQuery);

		Assert.assertEquals(1, result.size());

		TimestampTypeEntry existingTimestampTypeEntry = result.get(0);

		Assert.assertEquals(existingTimestampTypeEntry, newTimestampTypeEntry);
	}

	@Test
	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			TimestampTypeEntry.class, _dynamicQueryClassLoader);

		dynamicQuery.add(
			RestrictionsFactoryUtil.eq(
				"timestampTypeEntryId", RandomTestUtil.nextLong()));

		List<TimestampTypeEntry> result = _persistence.findWithDynamicQuery(
			dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testDynamicQueryByProjectionExisting() throws Exception {
		TimestampTypeEntry newTimestampTypeEntry = addTimestampTypeEntry();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			TimestampTypeEntry.class, _dynamicQueryClassLoader);

		dynamicQuery.setProjection(
			ProjectionFactoryUtil.property("timestampTypeEntryId"));

		Object newTimestampTypeEntryId =
			newTimestampTypeEntry.getTimestampTypeEntryId();

		dynamicQuery.add(
			RestrictionsFactoryUtil.in(
				"timestampTypeEntryId",
				new Object[] {newTimestampTypeEntryId}));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		Object existingTimestampTypeEntryId = result.get(0);

		Assert.assertEquals(
			existingTimestampTypeEntryId, newTimestampTypeEntryId);
	}

	@Test
	public void testDynamicQueryByProjectionMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			TimestampTypeEntry.class, _dynamicQueryClassLoader);

		dynamicQuery.setProjection(
			ProjectionFactoryUtil.property("timestampTypeEntryId"));

		dynamicQuery.add(
			RestrictionsFactoryUtil.in(
				"timestampTypeEntryId",
				new Object[] {RandomTestUtil.nextLong()}));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	protected TimestampTypeEntry addTimestampTypeEntry() throws Exception {
		long pk = RandomTestUtil.nextLong();

		TimestampTypeEntry timestampTypeEntry = _persistence.create(pk);

		timestampTypeEntry.setDateValue(RandomTestUtil.nextDate());

		_timestampTypeEntries.add(_persistence.update(timestampTypeEntry));

		return timestampTypeEntry;
	}

	private List<TimestampTypeEntry> _timestampTypeEntries =
		new ArrayList<TimestampTypeEntry>();
	private TimestampTypeEntryPersistence _persistence;
	private ClassLoader _dynamicQueryClassLoader;

}
// LIFERAY-SERVICE-BUILDER-HASH:-1601363679