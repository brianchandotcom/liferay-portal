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

package com.liferay.html.preview.service.persistence.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.html.preview.exception.NoSuchHtmlPreviewEntryException;
import com.liferay.html.preview.model.HtmlPreviewEntry;
import com.liferay.html.preview.service.HtmlPreviewEntryLocalServiceUtil;
import com.liferay.html.preview.service.persistence.HtmlPreviewEntryPersistence;
import com.liferay.html.preview.service.persistence.HtmlPreviewEntryUtil;
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
public class HtmlPreviewEntryPersistenceTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(), PersistenceTestRule.INSTANCE,
			new TransactionalTestRule(
				Propagation.REQUIRED, "com.liferay.html.preview.service"));

	@Before
	public void setUp() {
		_persistence = HtmlPreviewEntryUtil.getPersistence();

		Class<?> clazz = _persistence.getClass();

		_dynamicQueryClassLoader = clazz.getClassLoader();
	}

	@After
	public void tearDown() throws Exception {
		Iterator<HtmlPreviewEntry> iterator = _htmlPreviewEntries.iterator();

		while (iterator.hasNext()) {
			_persistence.remove(iterator.next());

			iterator.remove();
		}
	}

	@Test
	public void testCreate() throws Exception {
		long pk = RandomTestUtil.nextLong();

		HtmlPreviewEntry htmlPreviewEntry = _persistence.create(pk);

		Assert.assertNotNull(htmlPreviewEntry);

		Assert.assertEquals(htmlPreviewEntry.getPrimaryKey(), pk);
	}

	@Test
	public void testRemove() throws Exception {
		HtmlPreviewEntry newHtmlPreviewEntry = addHtmlPreviewEntry();

		_persistence.remove(newHtmlPreviewEntry);

		HtmlPreviewEntry existingHtmlPreviewEntry =
			_persistence.fetchByPrimaryKey(newHtmlPreviewEntry.getPrimaryKey());

		Assert.assertNull(existingHtmlPreviewEntry);
	}

	@Test
	public void testUpdateNew() throws Exception {
		addHtmlPreviewEntry();
	}

	@Test
	public void testUpdateExisting() throws Exception {
		long pk = RandomTestUtil.nextLong();

		HtmlPreviewEntry newHtmlPreviewEntry = _persistence.create(pk);

		newHtmlPreviewEntry.setGroupId(RandomTestUtil.nextLong());

		newHtmlPreviewEntry.setCompanyId(RandomTestUtil.nextLong());

		newHtmlPreviewEntry.setUserId(RandomTestUtil.nextLong());

		newHtmlPreviewEntry.setUserName(RandomTestUtil.randomString());

		newHtmlPreviewEntry.setCreateDate(RandomTestUtil.nextDate());

		newHtmlPreviewEntry.setModifiedDate(RandomTestUtil.nextDate());

		newHtmlPreviewEntry.setClassNameId(RandomTestUtil.nextLong());

		newHtmlPreviewEntry.setClassPK(RandomTestUtil.nextLong());

		newHtmlPreviewEntry.setFileEntryId(RandomTestUtil.nextLong());

		_htmlPreviewEntries.add(_persistence.update(newHtmlPreviewEntry));

		HtmlPreviewEntry existingHtmlPreviewEntry =
			_persistence.findByPrimaryKey(newHtmlPreviewEntry.getPrimaryKey());

		Assert.assertEquals(
			existingHtmlPreviewEntry.getHtmlPreviewEntryId(),
			newHtmlPreviewEntry.getHtmlPreviewEntryId());
		Assert.assertEquals(
			existingHtmlPreviewEntry.getGroupId(),
			newHtmlPreviewEntry.getGroupId());
		Assert.assertEquals(
			existingHtmlPreviewEntry.getCompanyId(),
			newHtmlPreviewEntry.getCompanyId());
		Assert.assertEquals(
			existingHtmlPreviewEntry.getUserId(),
			newHtmlPreviewEntry.getUserId());
		Assert.assertEquals(
			existingHtmlPreviewEntry.getUserName(),
			newHtmlPreviewEntry.getUserName());
		Assert.assertEquals(
			Time.getShortTimestamp(existingHtmlPreviewEntry.getCreateDate()),
			Time.getShortTimestamp(newHtmlPreviewEntry.getCreateDate()));
		Assert.assertEquals(
			Time.getShortTimestamp(existingHtmlPreviewEntry.getModifiedDate()),
			Time.getShortTimestamp(newHtmlPreviewEntry.getModifiedDate()));
		Assert.assertEquals(
			existingHtmlPreviewEntry.getClassNameId(),
			newHtmlPreviewEntry.getClassNameId());
		Assert.assertEquals(
			existingHtmlPreviewEntry.getClassPK(),
			newHtmlPreviewEntry.getClassPK());
		Assert.assertEquals(
			existingHtmlPreviewEntry.getFileEntryId(),
			newHtmlPreviewEntry.getFileEntryId());
	}

	@Test
	public void testCountByG_C_C() throws Exception {
		_persistence.countByG_C_C(
			RandomTestUtil.nextLong(), RandomTestUtil.nextLong(),
			RandomTestUtil.nextLong());

		_persistence.countByG_C_C(0L, 0L, 0L);
	}

	@Test
	public void testFindByPrimaryKeyExisting() throws Exception {
		HtmlPreviewEntry newHtmlPreviewEntry = addHtmlPreviewEntry();

		HtmlPreviewEntry existingHtmlPreviewEntry =
			_persistence.findByPrimaryKey(newHtmlPreviewEntry.getPrimaryKey());

		Assert.assertEquals(existingHtmlPreviewEntry, newHtmlPreviewEntry);
	}

	@Test(expected = NoSuchHtmlPreviewEntryException.class)
	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		_persistence.findByPrimaryKey(pk);
	}

	@Test
	public void testFindAll() throws Exception {
		_persistence.findAll(
			QueryUtil.ALL_POS, QueryUtil.ALL_POS, getOrderByComparator());
	}

	protected OrderByComparator<HtmlPreviewEntry> getOrderByComparator() {
		return OrderByComparatorFactoryUtil.create(
			"HtmlPreviewEntry", "htmlPreviewEntryId", true, "groupId", true,
			"companyId", true, "userId", true, "userName", true, "createDate",
			true, "modifiedDate", true, "classNameId", true, "classPK", true,
			"fileEntryId", true);
	}

	@Test
	public void testFetchByPrimaryKeyExisting() throws Exception {
		HtmlPreviewEntry newHtmlPreviewEntry = addHtmlPreviewEntry();

		HtmlPreviewEntry existingHtmlPreviewEntry =
			_persistence.fetchByPrimaryKey(newHtmlPreviewEntry.getPrimaryKey());

		Assert.assertEquals(existingHtmlPreviewEntry, newHtmlPreviewEntry);
	}

	@Test
	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		HtmlPreviewEntry missingHtmlPreviewEntry =
			_persistence.fetchByPrimaryKey(pk);

		Assert.assertNull(missingHtmlPreviewEntry);
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereAllPrimaryKeysExist()
		throws Exception {

		HtmlPreviewEntry newHtmlPreviewEntry1 = addHtmlPreviewEntry();
		HtmlPreviewEntry newHtmlPreviewEntry2 = addHtmlPreviewEntry();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newHtmlPreviewEntry1.getPrimaryKey());
		primaryKeys.add(newHtmlPreviewEntry2.getPrimaryKey());

		Map<Serializable, HtmlPreviewEntry> htmlPreviewEntries =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(2, htmlPreviewEntries.size());
		Assert.assertEquals(
			newHtmlPreviewEntry1,
			htmlPreviewEntries.get(newHtmlPreviewEntry1.getPrimaryKey()));
		Assert.assertEquals(
			newHtmlPreviewEntry2,
			htmlPreviewEntries.get(newHtmlPreviewEntry2.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereNoPrimaryKeysExist()
		throws Exception {

		long pk1 = RandomTestUtil.nextLong();

		long pk2 = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(pk1);
		primaryKeys.add(pk2);

		Map<Serializable, HtmlPreviewEntry> htmlPreviewEntries =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(htmlPreviewEntries.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereSomePrimaryKeysExist()
		throws Exception {

		HtmlPreviewEntry newHtmlPreviewEntry = addHtmlPreviewEntry();

		long pk = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newHtmlPreviewEntry.getPrimaryKey());
		primaryKeys.add(pk);

		Map<Serializable, HtmlPreviewEntry> htmlPreviewEntries =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, htmlPreviewEntries.size());
		Assert.assertEquals(
			newHtmlPreviewEntry,
			htmlPreviewEntries.get(newHtmlPreviewEntry.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithNoPrimaryKeys() throws Exception {
		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		Map<Serializable, HtmlPreviewEntry> htmlPreviewEntries =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(htmlPreviewEntries.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithOnePrimaryKey() throws Exception {
		HtmlPreviewEntry newHtmlPreviewEntry = addHtmlPreviewEntry();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newHtmlPreviewEntry.getPrimaryKey());

		Map<Serializable, HtmlPreviewEntry> htmlPreviewEntries =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, htmlPreviewEntries.size());
		Assert.assertEquals(
			newHtmlPreviewEntry,
			htmlPreviewEntries.get(newHtmlPreviewEntry.getPrimaryKey()));
	}

	@Test
	public void testActionableDynamicQuery() throws Exception {
		final IntegerWrapper count = new IntegerWrapper();

		ActionableDynamicQuery actionableDynamicQuery =
			HtmlPreviewEntryLocalServiceUtil.getActionableDynamicQuery();

		actionableDynamicQuery.setPerformActionMethod(
			new ActionableDynamicQuery.PerformActionMethod<HtmlPreviewEntry>() {

				@Override
				public void performAction(HtmlPreviewEntry htmlPreviewEntry) {
					Assert.assertNotNull(htmlPreviewEntry);

					count.increment();
				}

			});

		actionableDynamicQuery.performActions();

		Assert.assertEquals(count.getValue(), _persistence.countAll());
	}

	@Test
	public void testDynamicQueryByPrimaryKeyExisting() throws Exception {
		HtmlPreviewEntry newHtmlPreviewEntry = addHtmlPreviewEntry();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			HtmlPreviewEntry.class, _dynamicQueryClassLoader);

		dynamicQuery.add(
			RestrictionsFactoryUtil.eq(
				"htmlPreviewEntryId",
				newHtmlPreviewEntry.getHtmlPreviewEntryId()));

		List<HtmlPreviewEntry> result = _persistence.findWithDynamicQuery(
			dynamicQuery);

		Assert.assertEquals(1, result.size());

		HtmlPreviewEntry existingHtmlPreviewEntry = result.get(0);

		Assert.assertEquals(existingHtmlPreviewEntry, newHtmlPreviewEntry);
	}

	@Test
	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			HtmlPreviewEntry.class, _dynamicQueryClassLoader);

		dynamicQuery.add(
			RestrictionsFactoryUtil.eq(
				"htmlPreviewEntryId", RandomTestUtil.nextLong()));

		List<HtmlPreviewEntry> result = _persistence.findWithDynamicQuery(
			dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testDynamicQueryByProjectionExisting() throws Exception {
		HtmlPreviewEntry newHtmlPreviewEntry = addHtmlPreviewEntry();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			HtmlPreviewEntry.class, _dynamicQueryClassLoader);

		dynamicQuery.setProjection(
			ProjectionFactoryUtil.property("htmlPreviewEntryId"));

		Object newHtmlPreviewEntryId =
			newHtmlPreviewEntry.getHtmlPreviewEntryId();

		dynamicQuery.add(
			RestrictionsFactoryUtil.in(
				"htmlPreviewEntryId", new Object[] {newHtmlPreviewEntryId}));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		Object existingHtmlPreviewEntryId = result.get(0);

		Assert.assertEquals(existingHtmlPreviewEntryId, newHtmlPreviewEntryId);
	}

	@Test
	public void testDynamicQueryByProjectionMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			HtmlPreviewEntry.class, _dynamicQueryClassLoader);

		dynamicQuery.setProjection(
			ProjectionFactoryUtil.property("htmlPreviewEntryId"));

		dynamicQuery.add(
			RestrictionsFactoryUtil.in(
				"htmlPreviewEntryId",
				new Object[] {RandomTestUtil.nextLong()}));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testResetOriginalValues() throws Exception {
		HtmlPreviewEntry newHtmlPreviewEntry = addHtmlPreviewEntry();

		_persistence.clearCache();

		HtmlPreviewEntry existingHtmlPreviewEntry =
			_persistence.findByPrimaryKey(newHtmlPreviewEntry.getPrimaryKey());

		Assert.assertEquals(
			Long.valueOf(existingHtmlPreviewEntry.getGroupId()),
			ReflectionTestUtil.<Long>invoke(
				existingHtmlPreviewEntry, "getOriginalGroupId",
				new Class<?>[0]));
		Assert.assertEquals(
			Long.valueOf(existingHtmlPreviewEntry.getClassNameId()),
			ReflectionTestUtil.<Long>invoke(
				existingHtmlPreviewEntry, "getOriginalClassNameId",
				new Class<?>[0]));
		Assert.assertEquals(
			Long.valueOf(existingHtmlPreviewEntry.getClassPK()),
			ReflectionTestUtil.<Long>invoke(
				existingHtmlPreviewEntry, "getOriginalClassPK",
				new Class<?>[0]));
	}

	protected HtmlPreviewEntry addHtmlPreviewEntry() throws Exception {
		long pk = RandomTestUtil.nextLong();

		HtmlPreviewEntry htmlPreviewEntry = _persistence.create(pk);

		htmlPreviewEntry.setGroupId(RandomTestUtil.nextLong());

		htmlPreviewEntry.setCompanyId(RandomTestUtil.nextLong());

		htmlPreviewEntry.setUserId(RandomTestUtil.nextLong());

		htmlPreviewEntry.setUserName(RandomTestUtil.randomString());

		htmlPreviewEntry.setCreateDate(RandomTestUtil.nextDate());

		htmlPreviewEntry.setModifiedDate(RandomTestUtil.nextDate());

		htmlPreviewEntry.setClassNameId(RandomTestUtil.nextLong());

		htmlPreviewEntry.setClassPK(RandomTestUtil.nextLong());

		htmlPreviewEntry.setFileEntryId(RandomTestUtil.nextLong());

		_htmlPreviewEntries.add(_persistence.update(htmlPreviewEntry));

		return htmlPreviewEntry;
	}

	private List<HtmlPreviewEntry> _htmlPreviewEntries =
		new ArrayList<HtmlPreviewEntry>();
	private HtmlPreviewEntryPersistence _persistence;
	private ClassLoader _dynamicQueryClassLoader;

}