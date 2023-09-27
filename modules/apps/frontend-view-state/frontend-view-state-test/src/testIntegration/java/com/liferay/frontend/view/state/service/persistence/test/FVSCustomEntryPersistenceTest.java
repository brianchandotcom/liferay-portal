/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.frontend.view.state.service.persistence.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.frontend.view.state.exception.NoSuchCustomEntryException;
import com.liferay.frontend.view.state.model.FVSCustomEntry;
import com.liferay.frontend.view.state.service.FVSCustomEntryLocalServiceUtil;
import com.liferay.frontend.view.state.service.persistence.FVSCustomEntryPersistence;
import com.liferay.frontend.view.state.service.persistence.FVSCustomEntryUtil;
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
public class FVSCustomEntryPersistenceTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(), PersistenceTestRule.INSTANCE,
			new TransactionalTestRule(
				Propagation.REQUIRED,
				"com.liferay.frontend.view.state.service"));

	@Before
	public void setUp() {
		_persistence = FVSCustomEntryUtil.getPersistence();

		Class<?> clazz = _persistence.getClass();

		_dynamicQueryClassLoader = clazz.getClassLoader();
	}

	@After
	public void tearDown() throws Exception {
		Iterator<FVSCustomEntry> iterator = _fvsCustomEntries.iterator();

		while (iterator.hasNext()) {
			_persistence.remove(iterator.next());

			iterator.remove();
		}
	}

	@Test
	public void testCreate() throws Exception {
		long pk = RandomTestUtil.nextLong();

		FVSCustomEntry fvsCustomEntry = _persistence.create(pk);

		Assert.assertNotNull(fvsCustomEntry);

		Assert.assertEquals(fvsCustomEntry.getPrimaryKey(), pk);
	}

	@Test
	public void testRemove() throws Exception {
		FVSCustomEntry newFVSCustomEntry = addFVSCustomEntry();

		_persistence.remove(newFVSCustomEntry);

		FVSCustomEntry existingFVSCustomEntry = _persistence.fetchByPrimaryKey(
			newFVSCustomEntry.getPrimaryKey());

		Assert.assertNull(existingFVSCustomEntry);
	}

	@Test
	public void testUpdateNew() throws Exception {
		addFVSCustomEntry();
	}

	@Test
	public void testUpdateExisting() throws Exception {
		long pk = RandomTestUtil.nextLong();

		FVSCustomEntry newFVSCustomEntry = _persistence.create(pk);

		newFVSCustomEntry.setMvccVersion(RandomTestUtil.nextLong());

		newFVSCustomEntry.setUuid(RandomTestUtil.randomString());

		newFVSCustomEntry.setCompanyId(RandomTestUtil.nextLong());

		newFVSCustomEntry.setUserId(RandomTestUtil.nextLong());

		newFVSCustomEntry.setUserName(RandomTestUtil.randomString());

		newFVSCustomEntry.setCreateDate(RandomTestUtil.nextDate());

		newFVSCustomEntry.setModifiedDate(RandomTestUtil.nextDate());

		newFVSCustomEntry.setFvsEntryId(RandomTestUtil.nextLong());

		newFVSCustomEntry.setName(RandomTestUtil.randomString());

		_fvsCustomEntries.add(_persistence.update(newFVSCustomEntry));

		FVSCustomEntry existingFVSCustomEntry = _persistence.findByPrimaryKey(
			newFVSCustomEntry.getPrimaryKey());

		Assert.assertEquals(
			existingFVSCustomEntry.getMvccVersion(),
			newFVSCustomEntry.getMvccVersion());
		Assert.assertEquals(
			existingFVSCustomEntry.getUuid(), newFVSCustomEntry.getUuid());
		Assert.assertEquals(
			existingFVSCustomEntry.getFvsCustomEntryId(),
			newFVSCustomEntry.getFvsCustomEntryId());
		Assert.assertEquals(
			existingFVSCustomEntry.getCompanyId(),
			newFVSCustomEntry.getCompanyId());
		Assert.assertEquals(
			existingFVSCustomEntry.getUserId(), newFVSCustomEntry.getUserId());
		Assert.assertEquals(
			existingFVSCustomEntry.getUserName(),
			newFVSCustomEntry.getUserName());
		Assert.assertEquals(
			Time.getShortTimestamp(existingFVSCustomEntry.getCreateDate()),
			Time.getShortTimestamp(newFVSCustomEntry.getCreateDate()));
		Assert.assertEquals(
			Time.getShortTimestamp(existingFVSCustomEntry.getModifiedDate()),
			Time.getShortTimestamp(newFVSCustomEntry.getModifiedDate()));
		Assert.assertEquals(
			existingFVSCustomEntry.getFvsEntryId(),
			newFVSCustomEntry.getFvsEntryId());
		Assert.assertEquals(
			existingFVSCustomEntry.getName(), newFVSCustomEntry.getName());
	}

	@Test
	public void testCountByUuid() throws Exception {
		_persistence.countByUuid("");

		_persistence.countByUuid("null");

		_persistence.countByUuid((String)null);
	}

	@Test
	public void testCountByUuid_C() throws Exception {
		_persistence.countByUuid_C("", RandomTestUtil.nextLong());

		_persistence.countByUuid_C("null", 0L);

		_persistence.countByUuid_C((String)null, 0L);
	}

	@Test
	public void testFindByPrimaryKeyExisting() throws Exception {
		FVSCustomEntry newFVSCustomEntry = addFVSCustomEntry();

		FVSCustomEntry existingFVSCustomEntry = _persistence.findByPrimaryKey(
			newFVSCustomEntry.getPrimaryKey());

		Assert.assertEquals(existingFVSCustomEntry, newFVSCustomEntry);
	}

	@Test(expected = NoSuchCustomEntryException.class)
	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		_persistence.findByPrimaryKey(pk);
	}

	@Test
	public void testFindAll() throws Exception {
		_persistence.findAll(
			QueryUtil.ALL_POS, QueryUtil.ALL_POS, getOrderByComparator());
	}

	protected OrderByComparator<FVSCustomEntry> getOrderByComparator() {
		return OrderByComparatorFactoryUtil.create(
			"FVSCustomEntry", "mvccVersion", true, "uuid", true,
			"fvsCustomEntryId", true, "companyId", true, "userId", true,
			"userName", true, "createDate", true, "modifiedDate", true,
			"fvsEntryId", true, "name", true);
	}

	@Test
	public void testFetchByPrimaryKeyExisting() throws Exception {
		FVSCustomEntry newFVSCustomEntry = addFVSCustomEntry();

		FVSCustomEntry existingFVSCustomEntry = _persistence.fetchByPrimaryKey(
			newFVSCustomEntry.getPrimaryKey());

		Assert.assertEquals(existingFVSCustomEntry, newFVSCustomEntry);
	}

	@Test
	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		FVSCustomEntry missingFVSCustomEntry = _persistence.fetchByPrimaryKey(
			pk);

		Assert.assertNull(missingFVSCustomEntry);
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereAllPrimaryKeysExist()
		throws Exception {

		FVSCustomEntry newFVSCustomEntry1 = addFVSCustomEntry();
		FVSCustomEntry newFVSCustomEntry2 = addFVSCustomEntry();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newFVSCustomEntry1.getPrimaryKey());
		primaryKeys.add(newFVSCustomEntry2.getPrimaryKey());

		Map<Serializable, FVSCustomEntry> fvsCustomEntries =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(2, fvsCustomEntries.size());
		Assert.assertEquals(
			newFVSCustomEntry1,
			fvsCustomEntries.get(newFVSCustomEntry1.getPrimaryKey()));
		Assert.assertEquals(
			newFVSCustomEntry2,
			fvsCustomEntries.get(newFVSCustomEntry2.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereNoPrimaryKeysExist()
		throws Exception {

		long pk1 = RandomTestUtil.nextLong();

		long pk2 = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(pk1);
		primaryKeys.add(pk2);

		Map<Serializable, FVSCustomEntry> fvsCustomEntries =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(fvsCustomEntries.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereSomePrimaryKeysExist()
		throws Exception {

		FVSCustomEntry newFVSCustomEntry = addFVSCustomEntry();

		long pk = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newFVSCustomEntry.getPrimaryKey());
		primaryKeys.add(pk);

		Map<Serializable, FVSCustomEntry> fvsCustomEntries =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, fvsCustomEntries.size());
		Assert.assertEquals(
			newFVSCustomEntry,
			fvsCustomEntries.get(newFVSCustomEntry.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithNoPrimaryKeys() throws Exception {
		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		Map<Serializable, FVSCustomEntry> fvsCustomEntries =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(fvsCustomEntries.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithOnePrimaryKey() throws Exception {
		FVSCustomEntry newFVSCustomEntry = addFVSCustomEntry();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newFVSCustomEntry.getPrimaryKey());

		Map<Serializable, FVSCustomEntry> fvsCustomEntries =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, fvsCustomEntries.size());
		Assert.assertEquals(
			newFVSCustomEntry,
			fvsCustomEntries.get(newFVSCustomEntry.getPrimaryKey()));
	}

	@Test
	public void testActionableDynamicQuery() throws Exception {
		final IntegerWrapper count = new IntegerWrapper();

		ActionableDynamicQuery actionableDynamicQuery =
			FVSCustomEntryLocalServiceUtil.getActionableDynamicQuery();

		actionableDynamicQuery.setPerformActionMethod(
			new ActionableDynamicQuery.PerformActionMethod<FVSCustomEntry>() {

				@Override
				public void performAction(FVSCustomEntry fvsCustomEntry) {
					Assert.assertNotNull(fvsCustomEntry);

					count.increment();
				}

			});

		actionableDynamicQuery.performActions();

		Assert.assertEquals(count.getValue(), _persistence.countAll());
	}

	@Test
	public void testDynamicQueryByPrimaryKeyExisting() throws Exception {
		FVSCustomEntry newFVSCustomEntry = addFVSCustomEntry();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			FVSCustomEntry.class, _dynamicQueryClassLoader);

		dynamicQuery.add(
			RestrictionsFactoryUtil.eq(
				"fvsCustomEntryId", newFVSCustomEntry.getFvsCustomEntryId()));

		List<FVSCustomEntry> result = _persistence.findWithDynamicQuery(
			dynamicQuery);

		Assert.assertEquals(1, result.size());

		FVSCustomEntry existingFVSCustomEntry = result.get(0);

		Assert.assertEquals(existingFVSCustomEntry, newFVSCustomEntry);
	}

	@Test
	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			FVSCustomEntry.class, _dynamicQueryClassLoader);

		dynamicQuery.add(
			RestrictionsFactoryUtil.eq(
				"fvsCustomEntryId", RandomTestUtil.nextLong()));

		List<FVSCustomEntry> result = _persistence.findWithDynamicQuery(
			dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testDynamicQueryByProjectionExisting() throws Exception {
		FVSCustomEntry newFVSCustomEntry = addFVSCustomEntry();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			FVSCustomEntry.class, _dynamicQueryClassLoader);

		dynamicQuery.setProjection(
			ProjectionFactoryUtil.property("fvsCustomEntryId"));

		Object newFvsCustomEntryId = newFVSCustomEntry.getFvsCustomEntryId();

		dynamicQuery.add(
			RestrictionsFactoryUtil.in(
				"fvsCustomEntryId", new Object[] {newFvsCustomEntryId}));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		Object existingFvsCustomEntryId = result.get(0);

		Assert.assertEquals(existingFvsCustomEntryId, newFvsCustomEntryId);
	}

	@Test
	public void testDynamicQueryByProjectionMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			FVSCustomEntry.class, _dynamicQueryClassLoader);

		dynamicQuery.setProjection(
			ProjectionFactoryUtil.property("fvsCustomEntryId"));

		dynamicQuery.add(
			RestrictionsFactoryUtil.in(
				"fvsCustomEntryId", new Object[] {RandomTestUtil.nextLong()}));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	protected FVSCustomEntry addFVSCustomEntry() throws Exception {
		long pk = RandomTestUtil.nextLong();

		FVSCustomEntry fvsCustomEntry = _persistence.create(pk);

		fvsCustomEntry.setMvccVersion(RandomTestUtil.nextLong());

		fvsCustomEntry.setUuid(RandomTestUtil.randomString());

		fvsCustomEntry.setCompanyId(RandomTestUtil.nextLong());

		fvsCustomEntry.setUserId(RandomTestUtil.nextLong());

		fvsCustomEntry.setUserName(RandomTestUtil.randomString());

		fvsCustomEntry.setCreateDate(RandomTestUtil.nextDate());

		fvsCustomEntry.setModifiedDate(RandomTestUtil.nextDate());

		fvsCustomEntry.setFvsEntryId(RandomTestUtil.nextLong());

		fvsCustomEntry.setName(RandomTestUtil.randomString());

		_fvsCustomEntries.add(_persistence.update(fvsCustomEntry));

		return fvsCustomEntry;
	}

	private List<FVSCustomEntry> _fvsCustomEntries =
		new ArrayList<FVSCustomEntry>();
	private FVSCustomEntryPersistence _persistence;
	private ClassLoader _dynamicQueryClassLoader;

}