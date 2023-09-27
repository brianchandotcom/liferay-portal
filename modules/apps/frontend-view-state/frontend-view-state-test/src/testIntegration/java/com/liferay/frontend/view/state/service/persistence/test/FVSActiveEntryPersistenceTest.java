/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.frontend.view.state.service.persistence.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.frontend.view.state.exception.NoSuchActiveEntryException;
import com.liferay.frontend.view.state.model.FVSActiveEntry;
import com.liferay.frontend.view.state.service.FVSActiveEntryLocalServiceUtil;
import com.liferay.frontend.view.state.service.persistence.FVSActiveEntryPersistence;
import com.liferay.frontend.view.state.service.persistence.FVSActiveEntryUtil;
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
public class FVSActiveEntryPersistenceTest {

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
		_persistence = FVSActiveEntryUtil.getPersistence();

		Class<?> clazz = _persistence.getClass();

		_dynamicQueryClassLoader = clazz.getClassLoader();
	}

	@After
	public void tearDown() throws Exception {
		Iterator<FVSActiveEntry> iterator = _fvsActiveEntries.iterator();

		while (iterator.hasNext()) {
			_persistence.remove(iterator.next());

			iterator.remove();
		}
	}

	@Test
	public void testCreate() throws Exception {
		long pk = RandomTestUtil.nextLong();

		FVSActiveEntry fvsActiveEntry = _persistence.create(pk);

		Assert.assertNotNull(fvsActiveEntry);

		Assert.assertEquals(fvsActiveEntry.getPrimaryKey(), pk);
	}

	@Test
	public void testRemove() throws Exception {
		FVSActiveEntry newFVSActiveEntry = addFVSActiveEntry();

		_persistence.remove(newFVSActiveEntry);

		FVSActiveEntry existingFVSActiveEntry = _persistence.fetchByPrimaryKey(
			newFVSActiveEntry.getPrimaryKey());

		Assert.assertNull(existingFVSActiveEntry);
	}

	@Test
	public void testUpdateNew() throws Exception {
		addFVSActiveEntry();
	}

	@Test
	public void testUpdateExisting() throws Exception {
		long pk = RandomTestUtil.nextLong();

		FVSActiveEntry newFVSActiveEntry = _persistence.create(pk);

		newFVSActiveEntry.setMvccVersion(RandomTestUtil.nextLong());

		newFVSActiveEntry.setUuid(RandomTestUtil.randomString());

		newFVSActiveEntry.setCompanyId(RandomTestUtil.nextLong());

		newFVSActiveEntry.setUserId(RandomTestUtil.nextLong());

		newFVSActiveEntry.setUserName(RandomTestUtil.randomString());

		newFVSActiveEntry.setCreateDate(RandomTestUtil.nextDate());

		newFVSActiveEntry.setModifiedDate(RandomTestUtil.nextDate());

		newFVSActiveEntry.setFvsEntryId(RandomTestUtil.nextLong());

		newFVSActiveEntry.setClayDataSetDisplayId(
			RandomTestUtil.randomString());

		newFVSActiveEntry.setPlid(RandomTestUtil.nextLong());

		newFVSActiveEntry.setPortletId(RandomTestUtil.randomString());

		_fvsActiveEntries.add(_persistence.update(newFVSActiveEntry));

		FVSActiveEntry existingFVSActiveEntry = _persistence.findByPrimaryKey(
			newFVSActiveEntry.getPrimaryKey());

		Assert.assertEquals(
			existingFVSActiveEntry.getMvccVersion(),
			newFVSActiveEntry.getMvccVersion());
		Assert.assertEquals(
			existingFVSActiveEntry.getUuid(), newFVSActiveEntry.getUuid());
		Assert.assertEquals(
			existingFVSActiveEntry.getFvsActiveEntryId(),
			newFVSActiveEntry.getFvsActiveEntryId());
		Assert.assertEquals(
			existingFVSActiveEntry.getCompanyId(),
			newFVSActiveEntry.getCompanyId());
		Assert.assertEquals(
			existingFVSActiveEntry.getUserId(), newFVSActiveEntry.getUserId());
		Assert.assertEquals(
			existingFVSActiveEntry.getUserName(),
			newFVSActiveEntry.getUserName());
		Assert.assertEquals(
			Time.getShortTimestamp(existingFVSActiveEntry.getCreateDate()),
			Time.getShortTimestamp(newFVSActiveEntry.getCreateDate()));
		Assert.assertEquals(
			Time.getShortTimestamp(existingFVSActiveEntry.getModifiedDate()),
			Time.getShortTimestamp(newFVSActiveEntry.getModifiedDate()));
		Assert.assertEquals(
			existingFVSActiveEntry.getFvsEntryId(),
			newFVSActiveEntry.getFvsEntryId());
		Assert.assertEquals(
			existingFVSActiveEntry.getClayDataSetDisplayId(),
			newFVSActiveEntry.getClayDataSetDisplayId());
		Assert.assertEquals(
			existingFVSActiveEntry.getPlid(), newFVSActiveEntry.getPlid());
		Assert.assertEquals(
			existingFVSActiveEntry.getPortletId(),
			newFVSActiveEntry.getPortletId());
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
	public void testCountByU_CDSDI_P_P() throws Exception {
		_persistence.countByU_CDSDI_P_P(
			RandomTestUtil.nextLong(), "", RandomTestUtil.nextLong(), "");

		_persistence.countByU_CDSDI_P_P(0L, "null", 0L, "null");

		_persistence.countByU_CDSDI_P_P(0L, (String)null, 0L, (String)null);
	}

	@Test
	public void testFindByPrimaryKeyExisting() throws Exception {
		FVSActiveEntry newFVSActiveEntry = addFVSActiveEntry();

		FVSActiveEntry existingFVSActiveEntry = _persistence.findByPrimaryKey(
			newFVSActiveEntry.getPrimaryKey());

		Assert.assertEquals(existingFVSActiveEntry, newFVSActiveEntry);
	}

	@Test(expected = NoSuchActiveEntryException.class)
	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		_persistence.findByPrimaryKey(pk);
	}

	@Test
	public void testFindAll() throws Exception {
		_persistence.findAll(
			QueryUtil.ALL_POS, QueryUtil.ALL_POS, getOrderByComparator());
	}

	protected OrderByComparator<FVSActiveEntry> getOrderByComparator() {
		return OrderByComparatorFactoryUtil.create(
			"FVSActiveEntry", "mvccVersion", true, "uuid", true,
			"fvsActiveEntryId", true, "companyId", true, "userId", true,
			"userName", true, "createDate", true, "modifiedDate", true,
			"fvsEntryId", true, "clayDataSetDisplayId", true, "plid", true,
			"portletId", true);
	}

	@Test
	public void testFetchByPrimaryKeyExisting() throws Exception {
		FVSActiveEntry newFVSActiveEntry = addFVSActiveEntry();

		FVSActiveEntry existingFVSActiveEntry = _persistence.fetchByPrimaryKey(
			newFVSActiveEntry.getPrimaryKey());

		Assert.assertEquals(existingFVSActiveEntry, newFVSActiveEntry);
	}

	@Test
	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		FVSActiveEntry missingFVSActiveEntry = _persistence.fetchByPrimaryKey(
			pk);

		Assert.assertNull(missingFVSActiveEntry);
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereAllPrimaryKeysExist()
		throws Exception {

		FVSActiveEntry newFVSActiveEntry1 = addFVSActiveEntry();
		FVSActiveEntry newFVSActiveEntry2 = addFVSActiveEntry();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newFVSActiveEntry1.getPrimaryKey());
		primaryKeys.add(newFVSActiveEntry2.getPrimaryKey());

		Map<Serializable, FVSActiveEntry> fvsActiveEntries =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(2, fvsActiveEntries.size());
		Assert.assertEquals(
			newFVSActiveEntry1,
			fvsActiveEntries.get(newFVSActiveEntry1.getPrimaryKey()));
		Assert.assertEquals(
			newFVSActiveEntry2,
			fvsActiveEntries.get(newFVSActiveEntry2.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereNoPrimaryKeysExist()
		throws Exception {

		long pk1 = RandomTestUtil.nextLong();

		long pk2 = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(pk1);
		primaryKeys.add(pk2);

		Map<Serializable, FVSActiveEntry> fvsActiveEntries =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(fvsActiveEntries.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereSomePrimaryKeysExist()
		throws Exception {

		FVSActiveEntry newFVSActiveEntry = addFVSActiveEntry();

		long pk = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newFVSActiveEntry.getPrimaryKey());
		primaryKeys.add(pk);

		Map<Serializable, FVSActiveEntry> fvsActiveEntries =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, fvsActiveEntries.size());
		Assert.assertEquals(
			newFVSActiveEntry,
			fvsActiveEntries.get(newFVSActiveEntry.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithNoPrimaryKeys() throws Exception {
		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		Map<Serializable, FVSActiveEntry> fvsActiveEntries =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(fvsActiveEntries.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithOnePrimaryKey() throws Exception {
		FVSActiveEntry newFVSActiveEntry = addFVSActiveEntry();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newFVSActiveEntry.getPrimaryKey());

		Map<Serializable, FVSActiveEntry> fvsActiveEntries =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, fvsActiveEntries.size());
		Assert.assertEquals(
			newFVSActiveEntry,
			fvsActiveEntries.get(newFVSActiveEntry.getPrimaryKey()));
	}

	@Test
	public void testActionableDynamicQuery() throws Exception {
		final IntegerWrapper count = new IntegerWrapper();

		ActionableDynamicQuery actionableDynamicQuery =
			FVSActiveEntryLocalServiceUtil.getActionableDynamicQuery();

		actionableDynamicQuery.setPerformActionMethod(
			new ActionableDynamicQuery.PerformActionMethod<FVSActiveEntry>() {

				@Override
				public void performAction(FVSActiveEntry fvsActiveEntry) {
					Assert.assertNotNull(fvsActiveEntry);

					count.increment();
				}

			});

		actionableDynamicQuery.performActions();

		Assert.assertEquals(count.getValue(), _persistence.countAll());
	}

	@Test
	public void testDynamicQueryByPrimaryKeyExisting() throws Exception {
		FVSActiveEntry newFVSActiveEntry = addFVSActiveEntry();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			FVSActiveEntry.class, _dynamicQueryClassLoader);

		dynamicQuery.add(
			RestrictionsFactoryUtil.eq(
				"fvsActiveEntryId", newFVSActiveEntry.getFvsActiveEntryId()));

		List<FVSActiveEntry> result = _persistence.findWithDynamicQuery(
			dynamicQuery);

		Assert.assertEquals(1, result.size());

		FVSActiveEntry existingFVSActiveEntry = result.get(0);

		Assert.assertEquals(existingFVSActiveEntry, newFVSActiveEntry);
	}

	@Test
	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			FVSActiveEntry.class, _dynamicQueryClassLoader);

		dynamicQuery.add(
			RestrictionsFactoryUtil.eq(
				"fvsActiveEntryId", RandomTestUtil.nextLong()));

		List<FVSActiveEntry> result = _persistence.findWithDynamicQuery(
			dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testDynamicQueryByProjectionExisting() throws Exception {
		FVSActiveEntry newFVSActiveEntry = addFVSActiveEntry();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			FVSActiveEntry.class, _dynamicQueryClassLoader);

		dynamicQuery.setProjection(
			ProjectionFactoryUtil.property("fvsActiveEntryId"));

		Object newFvsActiveEntryId = newFVSActiveEntry.getFvsActiveEntryId();

		dynamicQuery.add(
			RestrictionsFactoryUtil.in(
				"fvsActiveEntryId", new Object[] {newFvsActiveEntryId}));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		Object existingFvsActiveEntryId = result.get(0);

		Assert.assertEquals(existingFvsActiveEntryId, newFvsActiveEntryId);
	}

	@Test
	public void testDynamicQueryByProjectionMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			FVSActiveEntry.class, _dynamicQueryClassLoader);

		dynamicQuery.setProjection(
			ProjectionFactoryUtil.property("fvsActiveEntryId"));

		dynamicQuery.add(
			RestrictionsFactoryUtil.in(
				"fvsActiveEntryId", new Object[] {RandomTestUtil.nextLong()}));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testResetOriginalValues() throws Exception {
		FVSActiveEntry newFVSActiveEntry = addFVSActiveEntry();

		_persistence.clearCache();

		_assertOriginalValues(
			_persistence.findByPrimaryKey(newFVSActiveEntry.getPrimaryKey()));
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

		FVSActiveEntry newFVSActiveEntry = addFVSActiveEntry();

		if (clearSession) {
			Session session = _persistence.openSession();

			session.flush();

			session.clear();
		}

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			FVSActiveEntry.class, _dynamicQueryClassLoader);

		dynamicQuery.add(
			RestrictionsFactoryUtil.eq(
				"fvsActiveEntryId", newFVSActiveEntry.getFvsActiveEntryId()));

		List<FVSActiveEntry> result = _persistence.findWithDynamicQuery(
			dynamicQuery);

		_assertOriginalValues(result.get(0));
	}

	private void _assertOriginalValues(FVSActiveEntry fvsActiveEntry) {
		Assert.assertEquals(
			Long.valueOf(fvsActiveEntry.getUserId()),
			ReflectionTestUtil.<Long>invoke(
				fvsActiveEntry, "getColumnOriginalValue",
				new Class<?>[] {String.class}, "userId"));
		Assert.assertEquals(
			fvsActiveEntry.getClayDataSetDisplayId(),
			ReflectionTestUtil.invoke(
				fvsActiveEntry, "getColumnOriginalValue",
				new Class<?>[] {String.class}, "clayDataSetDisplayId"));
		Assert.assertEquals(
			Long.valueOf(fvsActiveEntry.getPlid()),
			ReflectionTestUtil.<Long>invoke(
				fvsActiveEntry, "getColumnOriginalValue",
				new Class<?>[] {String.class}, "plid"));
		Assert.assertEquals(
			fvsActiveEntry.getPortletId(),
			ReflectionTestUtil.invoke(
				fvsActiveEntry, "getColumnOriginalValue",
				new Class<?>[] {String.class}, "portletId"));
	}

	protected FVSActiveEntry addFVSActiveEntry() throws Exception {
		long pk = RandomTestUtil.nextLong();

		FVSActiveEntry fvsActiveEntry = _persistence.create(pk);

		fvsActiveEntry.setMvccVersion(RandomTestUtil.nextLong());

		fvsActiveEntry.setUuid(RandomTestUtil.randomString());

		fvsActiveEntry.setCompanyId(RandomTestUtil.nextLong());

		fvsActiveEntry.setUserId(RandomTestUtil.nextLong());

		fvsActiveEntry.setUserName(RandomTestUtil.randomString());

		fvsActiveEntry.setCreateDate(RandomTestUtil.nextDate());

		fvsActiveEntry.setModifiedDate(RandomTestUtil.nextDate());

		fvsActiveEntry.setFvsEntryId(RandomTestUtil.nextLong());

		fvsActiveEntry.setClayDataSetDisplayId(RandomTestUtil.randomString());

		fvsActiveEntry.setPlid(RandomTestUtil.nextLong());

		fvsActiveEntry.setPortletId(RandomTestUtil.randomString());

		_fvsActiveEntries.add(_persistence.update(fvsActiveEntry));

		return fvsActiveEntry;
	}

	private List<FVSActiveEntry> _fvsActiveEntries =
		new ArrayList<FVSActiveEntry>();
	private FVSActiveEntryPersistence _persistence;
	private ClassLoader _dynamicQueryClassLoader;

}