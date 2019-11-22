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

package com.liferay.layout.seo.service.persistence.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.layout.seo.exception.NoSuchSiteSEOEntryException;
import com.liferay.layout.seo.model.SiteSEOEntry;
import com.liferay.layout.seo.service.SiteSEOEntryLocalServiceUtil;
import com.liferay.layout.seo.service.persistence.SiteSEOEntryPersistence;
import com.liferay.layout.seo.service.persistence.SiteSEOEntryUtil;
import com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQueryFactoryUtil;
import com.liferay.portal.kernel.dao.orm.ProjectionFactoryUtil;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.RestrictionsFactoryUtil;
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
import java.util.Objects;
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
public class SiteSEOEntryPersistenceTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(), PersistenceTestRule.INSTANCE,
			new TransactionalTestRule(
				Propagation.REQUIRED, "com.liferay.layout.seo.service"));

	@Before
	public void setUp() {
		_persistence = SiteSEOEntryUtil.getPersistence();

		Class<?> clazz = _persistence.getClass();

		_dynamicQueryClassLoader = clazz.getClassLoader();
	}

	@After
	public void tearDown() throws Exception {
		Iterator<SiteSEOEntry> iterator = _siteSEOEntries.iterator();

		while (iterator.hasNext()) {
			_persistence.remove(iterator.next());

			iterator.remove();
		}
	}

	@Test
	public void testCreate() throws Exception {
		long pk = RandomTestUtil.nextLong();

		SiteSEOEntry siteSEOEntry = _persistence.create(pk);

		Assert.assertNotNull(siteSEOEntry);

		Assert.assertEquals(siteSEOEntry.getPrimaryKey(), pk);
	}

	@Test
	public void testRemove() throws Exception {
		SiteSEOEntry newSiteSEOEntry = addSiteSEOEntry();

		_persistence.remove(newSiteSEOEntry);

		SiteSEOEntry existingSiteSEOEntry = _persistence.fetchByPrimaryKey(
			newSiteSEOEntry.getPrimaryKey());

		Assert.assertNull(existingSiteSEOEntry);
	}

	@Test
	public void testUpdateNew() throws Exception {
		addSiteSEOEntry();
	}

	@Test
	public void testUpdateExisting() throws Exception {
		long pk = RandomTestUtil.nextLong();

		SiteSEOEntry newSiteSEOEntry = _persistence.create(pk);

		newSiteSEOEntry.setMvccVersion(RandomTestUtil.nextLong());

		newSiteSEOEntry.setUuid(RandomTestUtil.randomString());

		newSiteSEOEntry.setGroupId(RandomTestUtil.nextLong());

		newSiteSEOEntry.setCompanyId(RandomTestUtil.nextLong());

		newSiteSEOEntry.setUserId(RandomTestUtil.nextLong());

		newSiteSEOEntry.setUserName(RandomTestUtil.randomString());

		newSiteSEOEntry.setCreateDate(RandomTestUtil.nextDate());

		newSiteSEOEntry.setModifiedDate(RandomTestUtil.nextDate());

		newSiteSEOEntry.setOpenGraphImageFileEntryId(RandomTestUtil.nextLong());

		newSiteSEOEntry.setOpenGraphEnabled(RandomTestUtil.randomBoolean());

		_siteSEOEntries.add(_persistence.update(newSiteSEOEntry));

		SiteSEOEntry existingSiteSEOEntry = _persistence.findByPrimaryKey(
			newSiteSEOEntry.getPrimaryKey());

		Assert.assertEquals(
			existingSiteSEOEntry.getMvccVersion(),
			newSiteSEOEntry.getMvccVersion());
		Assert.assertEquals(
			existingSiteSEOEntry.getUuid(), newSiteSEOEntry.getUuid());
		Assert.assertEquals(
			existingSiteSEOEntry.getSiteSEOEntryId(),
			newSiteSEOEntry.getSiteSEOEntryId());
		Assert.assertEquals(
			existingSiteSEOEntry.getGroupId(), newSiteSEOEntry.getGroupId());
		Assert.assertEquals(
			existingSiteSEOEntry.getCompanyId(),
			newSiteSEOEntry.getCompanyId());
		Assert.assertEquals(
			existingSiteSEOEntry.getUserId(), newSiteSEOEntry.getUserId());
		Assert.assertEquals(
			existingSiteSEOEntry.getUserName(), newSiteSEOEntry.getUserName());
		Assert.assertEquals(
			Time.getShortTimestamp(existingSiteSEOEntry.getCreateDate()),
			Time.getShortTimestamp(newSiteSEOEntry.getCreateDate()));
		Assert.assertEquals(
			Time.getShortTimestamp(existingSiteSEOEntry.getModifiedDate()),
			Time.getShortTimestamp(newSiteSEOEntry.getModifiedDate()));
		Assert.assertEquals(
			existingSiteSEOEntry.getOpenGraphImageFileEntryId(),
			newSiteSEOEntry.getOpenGraphImageFileEntryId());
		Assert.assertEquals(
			existingSiteSEOEntry.isOpenGraphEnabled(),
			newSiteSEOEntry.isOpenGraphEnabled());
	}

	@Test
	public void testCountByUuid() throws Exception {
		_persistence.countByUuid("");

		_persistence.countByUuid("null");

		_persistence.countByUuid((String)null);
	}

	@Test
	public void testCountByUUID_G() throws Exception {
		_persistence.countByUUID_G("", RandomTestUtil.nextLong());

		_persistence.countByUUID_G("null", 0L);

		_persistence.countByUUID_G((String)null, 0L);
	}

	@Test
	public void testCountByUuid_C() throws Exception {
		_persistence.countByUuid_C("", RandomTestUtil.nextLong());

		_persistence.countByUuid_C("null", 0L);

		_persistence.countByUuid_C((String)null, 0L);
	}

	@Test
	public void testCountByGroupId() throws Exception {
		_persistence.countByGroupId(RandomTestUtil.nextLong());

		_persistence.countByGroupId(0L);
	}

	@Test
	public void testFindByPrimaryKeyExisting() throws Exception {
		SiteSEOEntry newSiteSEOEntry = addSiteSEOEntry();

		SiteSEOEntry existingSiteSEOEntry = _persistence.findByPrimaryKey(
			newSiteSEOEntry.getPrimaryKey());

		Assert.assertEquals(existingSiteSEOEntry, newSiteSEOEntry);
	}

	@Test(expected = NoSuchSiteSEOEntryException.class)
	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		_persistence.findByPrimaryKey(pk);
	}

	@Test
	public void testFindAll() throws Exception {
		_persistence.findAll(
			QueryUtil.ALL_POS, QueryUtil.ALL_POS, getOrderByComparator());
	}

	protected OrderByComparator<SiteSEOEntry> getOrderByComparator() {
		return OrderByComparatorFactoryUtil.create(
			"SiteSEOEntry", "mvccVersion", true, "uuid", true, "siteSEOEntryId",
			true, "groupId", true, "companyId", true, "userId", true,
			"userName", true, "createDate", true, "modifiedDate", true,
			"openGraphImageFileEntryId", true, "openGraphEnabled", true);
	}

	@Test
	public void testFetchByPrimaryKeyExisting() throws Exception {
		SiteSEOEntry newSiteSEOEntry = addSiteSEOEntry();

		SiteSEOEntry existingSiteSEOEntry = _persistence.fetchByPrimaryKey(
			newSiteSEOEntry.getPrimaryKey());

		Assert.assertEquals(existingSiteSEOEntry, newSiteSEOEntry);
	}

	@Test
	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		SiteSEOEntry missingSiteSEOEntry = _persistence.fetchByPrimaryKey(pk);

		Assert.assertNull(missingSiteSEOEntry);
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereAllPrimaryKeysExist()
		throws Exception {

		SiteSEOEntry newSiteSEOEntry1 = addSiteSEOEntry();
		SiteSEOEntry newSiteSEOEntry2 = addSiteSEOEntry();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newSiteSEOEntry1.getPrimaryKey());
		primaryKeys.add(newSiteSEOEntry2.getPrimaryKey());

		Map<Serializable, SiteSEOEntry> siteSEOEntries =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(2, siteSEOEntries.size());
		Assert.assertEquals(
			newSiteSEOEntry1,
			siteSEOEntries.get(newSiteSEOEntry1.getPrimaryKey()));
		Assert.assertEquals(
			newSiteSEOEntry2,
			siteSEOEntries.get(newSiteSEOEntry2.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereNoPrimaryKeysExist()
		throws Exception {

		long pk1 = RandomTestUtil.nextLong();

		long pk2 = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(pk1);
		primaryKeys.add(pk2);

		Map<Serializable, SiteSEOEntry> siteSEOEntries =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(siteSEOEntries.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereSomePrimaryKeysExist()
		throws Exception {

		SiteSEOEntry newSiteSEOEntry = addSiteSEOEntry();

		long pk = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newSiteSEOEntry.getPrimaryKey());
		primaryKeys.add(pk);

		Map<Serializable, SiteSEOEntry> siteSEOEntries =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, siteSEOEntries.size());
		Assert.assertEquals(
			newSiteSEOEntry,
			siteSEOEntries.get(newSiteSEOEntry.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithNoPrimaryKeys() throws Exception {
		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		Map<Serializable, SiteSEOEntry> siteSEOEntries =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(siteSEOEntries.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithOnePrimaryKey() throws Exception {
		SiteSEOEntry newSiteSEOEntry = addSiteSEOEntry();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newSiteSEOEntry.getPrimaryKey());

		Map<Serializable, SiteSEOEntry> siteSEOEntries =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, siteSEOEntries.size());
		Assert.assertEquals(
			newSiteSEOEntry,
			siteSEOEntries.get(newSiteSEOEntry.getPrimaryKey()));
	}

	@Test
	public void testActionableDynamicQuery() throws Exception {
		final IntegerWrapper count = new IntegerWrapper();

		ActionableDynamicQuery actionableDynamicQuery =
			SiteSEOEntryLocalServiceUtil.getActionableDynamicQuery();

		actionableDynamicQuery.setPerformActionMethod(
			new ActionableDynamicQuery.PerformActionMethod<SiteSEOEntry>() {

				@Override
				public void performAction(SiteSEOEntry siteSEOEntry) {
					Assert.assertNotNull(siteSEOEntry);

					count.increment();
				}

			});

		actionableDynamicQuery.performActions();

		Assert.assertEquals(count.getValue(), _persistence.countAll());
	}

	@Test
	public void testDynamicQueryByPrimaryKeyExisting() throws Exception {
		SiteSEOEntry newSiteSEOEntry = addSiteSEOEntry();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			SiteSEOEntry.class, _dynamicQueryClassLoader);

		dynamicQuery.add(
			RestrictionsFactoryUtil.eq(
				"siteSEOEntryId", newSiteSEOEntry.getSiteSEOEntryId()));

		List<SiteSEOEntry> result = _persistence.findWithDynamicQuery(
			dynamicQuery);

		Assert.assertEquals(1, result.size());

		SiteSEOEntry existingSiteSEOEntry = result.get(0);

		Assert.assertEquals(existingSiteSEOEntry, newSiteSEOEntry);
	}

	@Test
	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			SiteSEOEntry.class, _dynamicQueryClassLoader);

		dynamicQuery.add(
			RestrictionsFactoryUtil.eq(
				"siteSEOEntryId", RandomTestUtil.nextLong()));

		List<SiteSEOEntry> result = _persistence.findWithDynamicQuery(
			dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testDynamicQueryByProjectionExisting() throws Exception {
		SiteSEOEntry newSiteSEOEntry = addSiteSEOEntry();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			SiteSEOEntry.class, _dynamicQueryClassLoader);

		dynamicQuery.setProjection(
			ProjectionFactoryUtil.property("siteSEOEntryId"));

		Object newSiteSEOEntryId = newSiteSEOEntry.getSiteSEOEntryId();

		dynamicQuery.add(
			RestrictionsFactoryUtil.in(
				"siteSEOEntryId", new Object[] {newSiteSEOEntryId}));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		Object existingSiteSEOEntryId = result.get(0);

		Assert.assertEquals(existingSiteSEOEntryId, newSiteSEOEntryId);
	}

	@Test
	public void testDynamicQueryByProjectionMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			SiteSEOEntry.class, _dynamicQueryClassLoader);

		dynamicQuery.setProjection(
			ProjectionFactoryUtil.property("siteSEOEntryId"));

		dynamicQuery.add(
			RestrictionsFactoryUtil.in(
				"siteSEOEntryId", new Object[] {RandomTestUtil.nextLong()}));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testResetOriginalValues() throws Exception {
		SiteSEOEntry newSiteSEOEntry = addSiteSEOEntry();

		_persistence.clearCache();

		SiteSEOEntry existingSiteSEOEntry = _persistence.findByPrimaryKey(
			newSiteSEOEntry.getPrimaryKey());

		Assert.assertTrue(
			Objects.equals(
				existingSiteSEOEntry.getUuid(),
				ReflectionTestUtil.invoke(
					existingSiteSEOEntry, "getOriginalUuid", new Class<?>[0])));
		Assert.assertEquals(
			Long.valueOf(existingSiteSEOEntry.getGroupId()),
			ReflectionTestUtil.<Long>invoke(
				existingSiteSEOEntry, "getOriginalGroupId", new Class<?>[0]));

		Assert.assertEquals(
			Long.valueOf(existingSiteSEOEntry.getGroupId()),
			ReflectionTestUtil.<Long>invoke(
				existingSiteSEOEntry, "getOriginalGroupId", new Class<?>[0]));
	}

	protected SiteSEOEntry addSiteSEOEntry() throws Exception {
		long pk = RandomTestUtil.nextLong();

		SiteSEOEntry siteSEOEntry = _persistence.create(pk);

		siteSEOEntry.setMvccVersion(RandomTestUtil.nextLong());

		siteSEOEntry.setUuid(RandomTestUtil.randomString());

		siteSEOEntry.setGroupId(RandomTestUtil.nextLong());

		siteSEOEntry.setCompanyId(RandomTestUtil.nextLong());

		siteSEOEntry.setUserId(RandomTestUtil.nextLong());

		siteSEOEntry.setUserName(RandomTestUtil.randomString());

		siteSEOEntry.setCreateDate(RandomTestUtil.nextDate());

		siteSEOEntry.setModifiedDate(RandomTestUtil.nextDate());

		siteSEOEntry.setOpenGraphImageFileEntryId(RandomTestUtil.nextLong());

		siteSEOEntry.setOpenGraphEnabled(RandomTestUtil.randomBoolean());

		_siteSEOEntries.add(_persistence.update(siteSEOEntry));

		return siteSEOEntry;
	}

	private List<SiteSEOEntry> _siteSEOEntries = new ArrayList<SiteSEOEntry>();
	private SiteSEOEntryPersistence _persistence;
	private ClassLoader _dynamicQueryClassLoader;

}