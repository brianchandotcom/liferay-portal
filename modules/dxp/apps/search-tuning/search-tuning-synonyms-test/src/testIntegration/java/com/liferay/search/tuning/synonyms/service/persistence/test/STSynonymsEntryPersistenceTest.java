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

package com.liferay.search.tuning.synonyms.service.persistence.test;

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
import com.liferay.search.tuning.synonyms.exception.NoSuchSTSynonymsEntryException;
import com.liferay.search.tuning.synonyms.model.STSynonymsEntry;
import com.liferay.search.tuning.synonyms.service.STSynonymsEntryLocalServiceUtil;
import com.liferay.search.tuning.synonyms.service.persistence.STSynonymsEntryPersistence;
import com.liferay.search.tuning.synonyms.service.persistence.STSynonymsEntryUtil;

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
public class STSynonymsEntryPersistenceTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(), PersistenceTestRule.INSTANCE,
			new TransactionalTestRule(
				Propagation.REQUIRED,
				"com.liferay.search.tuning.synonyms.service"));

	@Before
	public void setUp() {
		_persistence = STSynonymsEntryUtil.getPersistence();

		Class<?> clazz = _persistence.getClass();

		_dynamicQueryClassLoader = clazz.getClassLoader();
	}

	@After
	public void tearDown() throws Exception {
		Iterator<STSynonymsEntry> iterator = _stSynonymsEntries.iterator();

		while (iterator.hasNext()) {
			_persistence.remove(iterator.next());

			iterator.remove();
		}
	}

	@Test
	public void testCreate() throws Exception {
		long pk = RandomTestUtil.nextLong();

		STSynonymsEntry stSynonymsEntry = _persistence.create(pk);

		Assert.assertNotNull(stSynonymsEntry);

		Assert.assertEquals(stSynonymsEntry.getPrimaryKey(), pk);
	}

	@Test
	public void testRemove() throws Exception {
		STSynonymsEntry newSTSynonymsEntry = addSTSynonymsEntry();

		_persistence.remove(newSTSynonymsEntry);

		STSynonymsEntry existingSTSynonymsEntry =
			_persistence.fetchByPrimaryKey(newSTSynonymsEntry.getPrimaryKey());

		Assert.assertNull(existingSTSynonymsEntry);
	}

	@Test
	public void testUpdateNew() throws Exception {
		addSTSynonymsEntry();
	}

	@Test
	public void testUpdateExisting() throws Exception {
		long pk = RandomTestUtil.nextLong();

		STSynonymsEntry newSTSynonymsEntry = _persistence.create(pk);

		newSTSynonymsEntry.setMvccVersion(RandomTestUtil.nextLong());

		newSTSynonymsEntry.setCompanyId(RandomTestUtil.nextLong());

		_stSynonymsEntries.add(_persistence.update(newSTSynonymsEntry));

		STSynonymsEntry existingSTSynonymsEntry = _persistence.findByPrimaryKey(
			newSTSynonymsEntry.getPrimaryKey());

		Assert.assertEquals(
			existingSTSynonymsEntry.getMvccVersion(),
			newSTSynonymsEntry.getMvccVersion());
		Assert.assertEquals(
			existingSTSynonymsEntry.getSTSynonymsEntryId(),
			newSTSynonymsEntry.getSTSynonymsEntryId());
		Assert.assertEquals(
			existingSTSynonymsEntry.getCompanyId(),
			newSTSynonymsEntry.getCompanyId());
	}

	@Test
	public void testCountBycompanyId() throws Exception {
		_persistence.countBycompanyId(RandomTestUtil.nextLong());

		_persistence.countBycompanyId(0L);
	}

	@Test
	public void testFindByPrimaryKeyExisting() throws Exception {
		STSynonymsEntry newSTSynonymsEntry = addSTSynonymsEntry();

		STSynonymsEntry existingSTSynonymsEntry = _persistence.findByPrimaryKey(
			newSTSynonymsEntry.getPrimaryKey());

		Assert.assertEquals(existingSTSynonymsEntry, newSTSynonymsEntry);
	}

	@Test(expected = NoSuchSTSynonymsEntryException.class)
	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		_persistence.findByPrimaryKey(pk);
	}

	@Test
	public void testFindAll() throws Exception {
		_persistence.findAll(
			QueryUtil.ALL_POS, QueryUtil.ALL_POS, getOrderByComparator());
	}

	protected OrderByComparator<STSynonymsEntry> getOrderByComparator() {
		return OrderByComparatorFactoryUtil.create(
			"STSynonymsEntry", "mvccVersion", true, "STSynonymsEntryId", true,
			"companyId", true);
	}

	@Test
	public void testFetchByPrimaryKeyExisting() throws Exception {
		STSynonymsEntry newSTSynonymsEntry = addSTSynonymsEntry();

		STSynonymsEntry existingSTSynonymsEntry =
			_persistence.fetchByPrimaryKey(newSTSynonymsEntry.getPrimaryKey());

		Assert.assertEquals(existingSTSynonymsEntry, newSTSynonymsEntry);
	}

	@Test
	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		STSynonymsEntry missingSTSynonymsEntry = _persistence.fetchByPrimaryKey(
			pk);

		Assert.assertNull(missingSTSynonymsEntry);
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereAllPrimaryKeysExist()
		throws Exception {

		STSynonymsEntry newSTSynonymsEntry1 = addSTSynonymsEntry();
		STSynonymsEntry newSTSynonymsEntry2 = addSTSynonymsEntry();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newSTSynonymsEntry1.getPrimaryKey());
		primaryKeys.add(newSTSynonymsEntry2.getPrimaryKey());

		Map<Serializable, STSynonymsEntry> stSynonymsEntries =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(2, stSynonymsEntries.size());
		Assert.assertEquals(
			newSTSynonymsEntry1,
			stSynonymsEntries.get(newSTSynonymsEntry1.getPrimaryKey()));
		Assert.assertEquals(
			newSTSynonymsEntry2,
			stSynonymsEntries.get(newSTSynonymsEntry2.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereNoPrimaryKeysExist()
		throws Exception {

		long pk1 = RandomTestUtil.nextLong();

		long pk2 = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(pk1);
		primaryKeys.add(pk2);

		Map<Serializable, STSynonymsEntry> stSynonymsEntries =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(stSynonymsEntries.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereSomePrimaryKeysExist()
		throws Exception {

		STSynonymsEntry newSTSynonymsEntry = addSTSynonymsEntry();

		long pk = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newSTSynonymsEntry.getPrimaryKey());
		primaryKeys.add(pk);

		Map<Serializable, STSynonymsEntry> stSynonymsEntries =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, stSynonymsEntries.size());
		Assert.assertEquals(
			newSTSynonymsEntry,
			stSynonymsEntries.get(newSTSynonymsEntry.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithNoPrimaryKeys() throws Exception {
		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		Map<Serializable, STSynonymsEntry> stSynonymsEntries =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(stSynonymsEntries.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithOnePrimaryKey() throws Exception {
		STSynonymsEntry newSTSynonymsEntry = addSTSynonymsEntry();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newSTSynonymsEntry.getPrimaryKey());

		Map<Serializable, STSynonymsEntry> stSynonymsEntries =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, stSynonymsEntries.size());
		Assert.assertEquals(
			newSTSynonymsEntry,
			stSynonymsEntries.get(newSTSynonymsEntry.getPrimaryKey()));
	}

	@Test
	public void testActionableDynamicQuery() throws Exception {
		final IntegerWrapper count = new IntegerWrapper();

		ActionableDynamicQuery actionableDynamicQuery =
			STSynonymsEntryLocalServiceUtil.getActionableDynamicQuery();

		actionableDynamicQuery.setPerformActionMethod(
			new ActionableDynamicQuery.PerformActionMethod<STSynonymsEntry>() {

				@Override
				public void performAction(STSynonymsEntry stSynonymsEntry) {
					Assert.assertNotNull(stSynonymsEntry);

					count.increment();
				}

			});

		actionableDynamicQuery.performActions();

		Assert.assertEquals(count.getValue(), _persistence.countAll());
	}

	@Test
	public void testDynamicQueryByPrimaryKeyExisting() throws Exception {
		STSynonymsEntry newSTSynonymsEntry = addSTSynonymsEntry();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			STSynonymsEntry.class, _dynamicQueryClassLoader);

		dynamicQuery.add(
			RestrictionsFactoryUtil.eq(
				"STSynonymsEntryId",
				newSTSynonymsEntry.getSTSynonymsEntryId()));

		List<STSynonymsEntry> result = _persistence.findWithDynamicQuery(
			dynamicQuery);

		Assert.assertEquals(1, result.size());

		STSynonymsEntry existingSTSynonymsEntry = result.get(0);

		Assert.assertEquals(existingSTSynonymsEntry, newSTSynonymsEntry);
	}

	@Test
	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			STSynonymsEntry.class, _dynamicQueryClassLoader);

		dynamicQuery.add(
			RestrictionsFactoryUtil.eq(
				"STSynonymsEntryId", RandomTestUtil.nextLong()));

		List<STSynonymsEntry> result = _persistence.findWithDynamicQuery(
			dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testDynamicQueryByProjectionExisting() throws Exception {
		STSynonymsEntry newSTSynonymsEntry = addSTSynonymsEntry();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			STSynonymsEntry.class, _dynamicQueryClassLoader);

		dynamicQuery.setProjection(
			ProjectionFactoryUtil.property("STSynonymsEntryId"));

		Object newSTSynonymsEntryId = newSTSynonymsEntry.getSTSynonymsEntryId();

		dynamicQuery.add(
			RestrictionsFactoryUtil.in(
				"STSynonymsEntryId", new Object[] {newSTSynonymsEntryId}));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		Object existingSTSynonymsEntryId = result.get(0);

		Assert.assertEquals(existingSTSynonymsEntryId, newSTSynonymsEntryId);
	}

	@Test
	public void testDynamicQueryByProjectionMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			STSynonymsEntry.class, _dynamicQueryClassLoader);

		dynamicQuery.setProjection(
			ProjectionFactoryUtil.property("STSynonymsEntryId"));

		dynamicQuery.add(
			RestrictionsFactoryUtil.in(
				"STSynonymsEntryId", new Object[] {RandomTestUtil.nextLong()}));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	protected STSynonymsEntry addSTSynonymsEntry() throws Exception {
		long pk = RandomTestUtil.nextLong();

		STSynonymsEntry stSynonymsEntry = _persistence.create(pk);

		stSynonymsEntry.setMvccVersion(RandomTestUtil.nextLong());

		stSynonymsEntry.setCompanyId(RandomTestUtil.nextLong());

		_stSynonymsEntries.add(_persistence.update(stSynonymsEntry));

		return stSynonymsEntry;
	}

	private List<STSynonymsEntry> _stSynonymsEntries =
		new ArrayList<STSynonymsEntry>();
	private STSynonymsEntryPersistence _persistence;
	private ClassLoader _dynamicQueryClassLoader;

}