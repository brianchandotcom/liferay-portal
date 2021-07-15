/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.search.tuning.rankings.service.persistence.test;

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
import com.liferay.search.tuning.rankings.exception.NoSuchSTRankingsEntryException;
import com.liferay.search.tuning.rankings.model.STRankingsEntry;
import com.liferay.search.tuning.rankings.service.STRankingsEntryLocalServiceUtil;
import com.liferay.search.tuning.rankings.service.persistence.STRankingsEntryPersistence;
import com.liferay.search.tuning.rankings.service.persistence.STRankingsEntryUtil;

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
public class STRankingsEntryPersistenceTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(), PersistenceTestRule.INSTANCE,
			new TransactionalTestRule(
				Propagation.REQUIRED,
				"com.liferay.search.tuning.rankings.service"));

	@Before
	public void setUp() {
		_persistence = STRankingsEntryUtil.getPersistence();

		Class<?> clazz = _persistence.getClass();

		_dynamicQueryClassLoader = clazz.getClassLoader();
	}

	@After
	public void tearDown() throws Exception {
		Iterator<STRankingsEntry> iterator = _stRankingsEntries.iterator();

		while (iterator.hasNext()) {
			_persistence.remove(iterator.next());

			iterator.remove();
		}
	}

	@Test
	public void testCreate() throws Exception {
		long pk = RandomTestUtil.nextLong();

		STRankingsEntry stRankingsEntry = _persistence.create(pk);

		Assert.assertNotNull(stRankingsEntry);

		Assert.assertEquals(stRankingsEntry.getPrimaryKey(), pk);
	}

	@Test
	public void testRemove() throws Exception {
		STRankingsEntry newSTRankingsEntry = addSTRankingsEntry();

		_persistence.remove(newSTRankingsEntry);

		STRankingsEntry existingSTRankingsEntry =
			_persistence.fetchByPrimaryKey(newSTRankingsEntry.getPrimaryKey());

		Assert.assertNull(existingSTRankingsEntry);
	}

	@Test
	public void testUpdateNew() throws Exception {
		addSTRankingsEntry();
	}

	@Test
	public void testUpdateExisting() throws Exception {
		long pk = RandomTestUtil.nextLong();

		STRankingsEntry newSTRankingsEntry = _persistence.create(pk);

		newSTRankingsEntry.setMvccVersion(RandomTestUtil.nextLong());

		newSTRankingsEntry.setCompanyId(RandomTestUtil.nextLong());

		_stRankingsEntries.add(_persistence.update(newSTRankingsEntry));

		STRankingsEntry existingSTRankingsEntry = _persistence.findByPrimaryKey(
			newSTRankingsEntry.getPrimaryKey());

		Assert.assertEquals(
			existingSTRankingsEntry.getMvccVersion(),
			newSTRankingsEntry.getMvccVersion());
		Assert.assertEquals(
			existingSTRankingsEntry.getSTRankingsEntryId(),
			newSTRankingsEntry.getSTRankingsEntryId());
		Assert.assertEquals(
			existingSTRankingsEntry.getCompanyId(),
			newSTRankingsEntry.getCompanyId());
	}

	@Test
	public void testCountBycompanyId() throws Exception {
		_persistence.countBycompanyId(RandomTestUtil.nextLong());

		_persistence.countBycompanyId(0L);
	}

	@Test
	public void testFindByPrimaryKeyExisting() throws Exception {
		STRankingsEntry newSTRankingsEntry = addSTRankingsEntry();

		STRankingsEntry existingSTRankingsEntry = _persistence.findByPrimaryKey(
			newSTRankingsEntry.getPrimaryKey());

		Assert.assertEquals(existingSTRankingsEntry, newSTRankingsEntry);
	}

	@Test(expected = NoSuchSTRankingsEntryException.class)
	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		_persistence.findByPrimaryKey(pk);
	}

	@Test
	public void testFindAll() throws Exception {
		_persistence.findAll(
			QueryUtil.ALL_POS, QueryUtil.ALL_POS, getOrderByComparator());
	}

	protected OrderByComparator<STRankingsEntry> getOrderByComparator() {
		return OrderByComparatorFactoryUtil.create(
			"STRankingsEntry", "mvccVersion", true, "STRankingsEntryId", true,
			"companyId", true);
	}

	@Test
	public void testFetchByPrimaryKeyExisting() throws Exception {
		STRankingsEntry newSTRankingsEntry = addSTRankingsEntry();

		STRankingsEntry existingSTRankingsEntry =
			_persistence.fetchByPrimaryKey(newSTRankingsEntry.getPrimaryKey());

		Assert.assertEquals(existingSTRankingsEntry, newSTRankingsEntry);
	}

	@Test
	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		STRankingsEntry missingSTRankingsEntry = _persistence.fetchByPrimaryKey(
			pk);

		Assert.assertNull(missingSTRankingsEntry);
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereAllPrimaryKeysExist()
		throws Exception {

		STRankingsEntry newSTRankingsEntry1 = addSTRankingsEntry();
		STRankingsEntry newSTRankingsEntry2 = addSTRankingsEntry();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newSTRankingsEntry1.getPrimaryKey());
		primaryKeys.add(newSTRankingsEntry2.getPrimaryKey());

		Map<Serializable, STRankingsEntry> stRankingsEntries =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(2, stRankingsEntries.size());
		Assert.assertEquals(
			newSTRankingsEntry1,
			stRankingsEntries.get(newSTRankingsEntry1.getPrimaryKey()));
		Assert.assertEquals(
			newSTRankingsEntry2,
			stRankingsEntries.get(newSTRankingsEntry2.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereNoPrimaryKeysExist()
		throws Exception {

		long pk1 = RandomTestUtil.nextLong();

		long pk2 = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(pk1);
		primaryKeys.add(pk2);

		Map<Serializable, STRankingsEntry> stRankingsEntries =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(stRankingsEntries.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereSomePrimaryKeysExist()
		throws Exception {

		STRankingsEntry newSTRankingsEntry = addSTRankingsEntry();

		long pk = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newSTRankingsEntry.getPrimaryKey());
		primaryKeys.add(pk);

		Map<Serializable, STRankingsEntry> stRankingsEntries =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, stRankingsEntries.size());
		Assert.assertEquals(
			newSTRankingsEntry,
			stRankingsEntries.get(newSTRankingsEntry.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithNoPrimaryKeys() throws Exception {
		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		Map<Serializable, STRankingsEntry> stRankingsEntries =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(stRankingsEntries.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithOnePrimaryKey() throws Exception {
		STRankingsEntry newSTRankingsEntry = addSTRankingsEntry();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newSTRankingsEntry.getPrimaryKey());

		Map<Serializable, STRankingsEntry> stRankingsEntries =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, stRankingsEntries.size());
		Assert.assertEquals(
			newSTRankingsEntry,
			stRankingsEntries.get(newSTRankingsEntry.getPrimaryKey()));
	}

	@Test
	public void testActionableDynamicQuery() throws Exception {
		final IntegerWrapper count = new IntegerWrapper();

		ActionableDynamicQuery actionableDynamicQuery =
			STRankingsEntryLocalServiceUtil.getActionableDynamicQuery();

		actionableDynamicQuery.setPerformActionMethod(
			new ActionableDynamicQuery.PerformActionMethod<STRankingsEntry>() {

				@Override
				public void performAction(STRankingsEntry stRankingsEntry) {
					Assert.assertNotNull(stRankingsEntry);

					count.increment();
				}

			});

		actionableDynamicQuery.performActions();

		Assert.assertEquals(count.getValue(), _persistence.countAll());
	}

	@Test
	public void testDynamicQueryByPrimaryKeyExisting() throws Exception {
		STRankingsEntry newSTRankingsEntry = addSTRankingsEntry();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			STRankingsEntry.class, _dynamicQueryClassLoader);

		dynamicQuery.add(
			RestrictionsFactoryUtil.eq(
				"STRankingsEntryId",
				newSTRankingsEntry.getSTRankingsEntryId()));

		List<STRankingsEntry> result = _persistence.findWithDynamicQuery(
			dynamicQuery);

		Assert.assertEquals(1, result.size());

		STRankingsEntry existingSTRankingsEntry = result.get(0);

		Assert.assertEquals(existingSTRankingsEntry, newSTRankingsEntry);
	}

	@Test
	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			STRankingsEntry.class, _dynamicQueryClassLoader);

		dynamicQuery.add(
			RestrictionsFactoryUtil.eq(
				"STRankingsEntryId", RandomTestUtil.nextLong()));

		List<STRankingsEntry> result = _persistence.findWithDynamicQuery(
			dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testDynamicQueryByProjectionExisting() throws Exception {
		STRankingsEntry newSTRankingsEntry = addSTRankingsEntry();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			STRankingsEntry.class, _dynamicQueryClassLoader);

		dynamicQuery.setProjection(
			ProjectionFactoryUtil.property("STRankingsEntryId"));

		Object newSTRankingsEntryId = newSTRankingsEntry.getSTRankingsEntryId();

		dynamicQuery.add(
			RestrictionsFactoryUtil.in(
				"STRankingsEntryId", new Object[] {newSTRankingsEntryId}));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		Object existingSTRankingsEntryId = result.get(0);

		Assert.assertEquals(existingSTRankingsEntryId, newSTRankingsEntryId);
	}

	@Test
	public void testDynamicQueryByProjectionMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			STRankingsEntry.class, _dynamicQueryClassLoader);

		dynamicQuery.setProjection(
			ProjectionFactoryUtil.property("STRankingsEntryId"));

		dynamicQuery.add(
			RestrictionsFactoryUtil.in(
				"STRankingsEntryId", new Object[] {RandomTestUtil.nextLong()}));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	protected STRankingsEntry addSTRankingsEntry() throws Exception {
		long pk = RandomTestUtil.nextLong();

		STRankingsEntry stRankingsEntry = _persistence.create(pk);

		stRankingsEntry.setMvccVersion(RandomTestUtil.nextLong());

		stRankingsEntry.setCompanyId(RandomTestUtil.nextLong());

		_stRankingsEntries.add(_persistence.update(stRankingsEntry));

		return stRankingsEntry;
	}

	private List<STRankingsEntry> _stRankingsEntries =
		new ArrayList<STRankingsEntry>();
	private STRankingsEntryPersistence _persistence;
	private ClassLoader _dynamicQueryClassLoader;

}