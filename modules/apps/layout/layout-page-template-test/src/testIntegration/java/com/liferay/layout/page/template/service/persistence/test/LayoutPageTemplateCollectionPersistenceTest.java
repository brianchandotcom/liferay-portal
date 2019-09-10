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

package com.liferay.layout.page.template.service.persistence.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.layout.page.template.exception.NoSuchPageTemplateCollectionException;
import com.liferay.layout.page.template.model.LayoutPageTemplateCollection;
import com.liferay.layout.page.template.service.LayoutPageTemplateCollectionLocalServiceUtil;
import com.liferay.layout.page.template.service.persistence.LayoutPageTemplateCollectionPersistence;
import com.liferay.layout.page.template.service.persistence.LayoutPageTemplateCollectionUtil;
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
public class LayoutPageTemplateCollectionPersistenceTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(), PersistenceTestRule.INSTANCE,
			new TransactionalTestRule(
				Propagation.REQUIRED,
				"com.liferay.layout.page.template.service"));

	@Before
	public void setUp() {
		_persistence = LayoutPageTemplateCollectionUtil.getPersistence();

		Class<?> clazz = _persistence.getClass();

		_dynamicQueryClassLoader = clazz.getClassLoader();
	}

	@After
	public void tearDown() throws Exception {
		Iterator<LayoutPageTemplateCollection> iterator =
			_layoutPageTemplateCollections.iterator();

		while (iterator.hasNext()) {
			_persistence.remove(iterator.next());

			iterator.remove();
		}
	}

	@Test
	public void testCreate() throws Exception {
		long pk = RandomTestUtil.nextLong();

		LayoutPageTemplateCollection layoutPageTemplateCollection =
			_persistence.create(pk);

		Assert.assertNotNull(layoutPageTemplateCollection);

		Assert.assertEquals(layoutPageTemplateCollection.getPrimaryKey(), pk);
	}

	@Test
	public void testRemove() throws Exception {
		LayoutPageTemplateCollection newLayoutPageTemplateCollection =
			addLayoutPageTemplateCollection();

		_persistence.remove(newLayoutPageTemplateCollection);

		LayoutPageTemplateCollection existingLayoutPageTemplateCollection =
			_persistence.fetchByPrimaryKey(
				newLayoutPageTemplateCollection.getPrimaryKey());

		Assert.assertNull(existingLayoutPageTemplateCollection);
	}

	@Test
	public void testUpdateNew() throws Exception {
		addLayoutPageTemplateCollection();
	}

	@Test
	public void testUpdateExisting() throws Exception {
		long pk = RandomTestUtil.nextLong();

		LayoutPageTemplateCollection newLayoutPageTemplateCollection =
			_persistence.create(pk);

		newLayoutPageTemplateCollection.setMvccVersion(
			RandomTestUtil.nextLong());

		newLayoutPageTemplateCollection.setUuid(RandomTestUtil.randomString());

		newLayoutPageTemplateCollection.setGroupId(RandomTestUtil.nextLong());

		newLayoutPageTemplateCollection.setCompanyId(RandomTestUtil.nextLong());

		newLayoutPageTemplateCollection.setUserId(RandomTestUtil.nextLong());

		newLayoutPageTemplateCollection.setUserName(
			RandomTestUtil.randomString());

		newLayoutPageTemplateCollection.setCreateDate(
			RandomTestUtil.nextDate());

		newLayoutPageTemplateCollection.setModifiedDate(
			RandomTestUtil.nextDate());

		newLayoutPageTemplateCollection.setName(RandomTestUtil.randomString());

		newLayoutPageTemplateCollection.setDescription(
			RandomTestUtil.randomString());

		newLayoutPageTemplateCollection.setLastPublishDate(
			RandomTestUtil.nextDate());

		_layoutPageTemplateCollections.add(
			_persistence.update(newLayoutPageTemplateCollection));

		LayoutPageTemplateCollection existingLayoutPageTemplateCollection =
			_persistence.findByPrimaryKey(
				newLayoutPageTemplateCollection.getPrimaryKey());

		Assert.assertEquals(
			existingLayoutPageTemplateCollection.getMvccVersion(),
			newLayoutPageTemplateCollection.getMvccVersion());
		Assert.assertEquals(
			existingLayoutPageTemplateCollection.getUuid(),
			newLayoutPageTemplateCollection.getUuid());
		Assert.assertEquals(
			existingLayoutPageTemplateCollection.
				getLayoutPageTemplateCollectionId(),
			newLayoutPageTemplateCollection.
				getLayoutPageTemplateCollectionId());
		Assert.assertEquals(
			existingLayoutPageTemplateCollection.getGroupId(),
			newLayoutPageTemplateCollection.getGroupId());
		Assert.assertEquals(
			existingLayoutPageTemplateCollection.getCompanyId(),
			newLayoutPageTemplateCollection.getCompanyId());
		Assert.assertEquals(
			existingLayoutPageTemplateCollection.getUserId(),
			newLayoutPageTemplateCollection.getUserId());
		Assert.assertEquals(
			existingLayoutPageTemplateCollection.getUserName(),
			newLayoutPageTemplateCollection.getUserName());
		Assert.assertEquals(
			Time.getShortTimestamp(
				existingLayoutPageTemplateCollection.getCreateDate()),
			Time.getShortTimestamp(
				newLayoutPageTemplateCollection.getCreateDate()));
		Assert.assertEquals(
			Time.getShortTimestamp(
				existingLayoutPageTemplateCollection.getModifiedDate()),
			Time.getShortTimestamp(
				newLayoutPageTemplateCollection.getModifiedDate()));
		Assert.assertEquals(
			existingLayoutPageTemplateCollection.getName(),
			newLayoutPageTemplateCollection.getName());
		Assert.assertEquals(
			existingLayoutPageTemplateCollection.getDescription(),
			newLayoutPageTemplateCollection.getDescription());
		Assert.assertEquals(
			Time.getShortTimestamp(
				existingLayoutPageTemplateCollection.getLastPublishDate()),
			Time.getShortTimestamp(
				newLayoutPageTemplateCollection.getLastPublishDate()));
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
	public void testCountByG_N() throws Exception {
		_persistence.countByG_N(RandomTestUtil.nextLong(), "");

		_persistence.countByG_N(0L, "null");

		_persistence.countByG_N(0L, (String)null);
	}

	@Test
	public void testCountByG_LikeN() throws Exception {
		_persistence.countByG_LikeN(RandomTestUtil.nextLong(), "");

		_persistence.countByG_LikeN(0L, "null");

		_persistence.countByG_LikeN(0L, (String)null);
	}

	@Test
	public void testFindByPrimaryKeyExisting() throws Exception {
		LayoutPageTemplateCollection newLayoutPageTemplateCollection =
			addLayoutPageTemplateCollection();

		LayoutPageTemplateCollection existingLayoutPageTemplateCollection =
			_persistence.findByPrimaryKey(
				newLayoutPageTemplateCollection.getPrimaryKey());

		Assert.assertEquals(
			existingLayoutPageTemplateCollection,
			newLayoutPageTemplateCollection);
	}

	@Test(expected = NoSuchPageTemplateCollectionException.class)
	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		_persistence.findByPrimaryKey(pk);
	}

	@Test
	public void testFindAll() throws Exception {
		_persistence.findAll(
			QueryUtil.ALL_POS, QueryUtil.ALL_POS, getOrderByComparator());
	}

	@Test
	public void testFilterFindByGroupId() throws Exception {
		_persistence.filterFindByGroupId(
			0, QueryUtil.ALL_POS, QueryUtil.ALL_POS, getOrderByComparator());
	}

	protected OrderByComparator<LayoutPageTemplateCollection>
		getOrderByComparator() {

		return OrderByComparatorFactoryUtil.create(
			"LayoutPageTemplateCollection", "mvccVersion", true, "uuid", true,
			"layoutPageTemplateCollectionId", true, "groupId", true,
			"companyId", true, "userId", true, "userName", true, "createDate",
			true, "modifiedDate", true, "name", true, "description", true,
			"lastPublishDate", true);
	}

	@Test
	public void testFetchByPrimaryKeyExisting() throws Exception {
		LayoutPageTemplateCollection newLayoutPageTemplateCollection =
			addLayoutPageTemplateCollection();

		LayoutPageTemplateCollection existingLayoutPageTemplateCollection =
			_persistence.fetchByPrimaryKey(
				newLayoutPageTemplateCollection.getPrimaryKey());

		Assert.assertEquals(
			existingLayoutPageTemplateCollection,
			newLayoutPageTemplateCollection);
	}

	@Test
	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		LayoutPageTemplateCollection missingLayoutPageTemplateCollection =
			_persistence.fetchByPrimaryKey(pk);

		Assert.assertNull(missingLayoutPageTemplateCollection);
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereAllPrimaryKeysExist()
		throws Exception {

		LayoutPageTemplateCollection newLayoutPageTemplateCollection1 =
			addLayoutPageTemplateCollection();
		LayoutPageTemplateCollection newLayoutPageTemplateCollection2 =
			addLayoutPageTemplateCollection();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newLayoutPageTemplateCollection1.getPrimaryKey());
		primaryKeys.add(newLayoutPageTemplateCollection2.getPrimaryKey());

		Map<Serializable, LayoutPageTemplateCollection>
			layoutPageTemplateCollections = _persistence.fetchByPrimaryKeys(
				primaryKeys);

		Assert.assertEquals(2, layoutPageTemplateCollections.size());
		Assert.assertEquals(
			newLayoutPageTemplateCollection1,
			layoutPageTemplateCollections.get(
				newLayoutPageTemplateCollection1.getPrimaryKey()));
		Assert.assertEquals(
			newLayoutPageTemplateCollection2,
			layoutPageTemplateCollections.get(
				newLayoutPageTemplateCollection2.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereNoPrimaryKeysExist()
		throws Exception {

		long pk1 = RandomTestUtil.nextLong();

		long pk2 = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(pk1);
		primaryKeys.add(pk2);

		Map<Serializable, LayoutPageTemplateCollection>
			layoutPageTemplateCollections = _persistence.fetchByPrimaryKeys(
				primaryKeys);

		Assert.assertTrue(layoutPageTemplateCollections.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereSomePrimaryKeysExist()
		throws Exception {

		LayoutPageTemplateCollection newLayoutPageTemplateCollection =
			addLayoutPageTemplateCollection();

		long pk = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newLayoutPageTemplateCollection.getPrimaryKey());
		primaryKeys.add(pk);

		Map<Serializable, LayoutPageTemplateCollection>
			layoutPageTemplateCollections = _persistence.fetchByPrimaryKeys(
				primaryKeys);

		Assert.assertEquals(1, layoutPageTemplateCollections.size());
		Assert.assertEquals(
			newLayoutPageTemplateCollection,
			layoutPageTemplateCollections.get(
				newLayoutPageTemplateCollection.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithNoPrimaryKeys() throws Exception {
		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		Map<Serializable, LayoutPageTemplateCollection>
			layoutPageTemplateCollections = _persistence.fetchByPrimaryKeys(
				primaryKeys);

		Assert.assertTrue(layoutPageTemplateCollections.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithOnePrimaryKey() throws Exception {
		LayoutPageTemplateCollection newLayoutPageTemplateCollection =
			addLayoutPageTemplateCollection();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newLayoutPageTemplateCollection.getPrimaryKey());

		Map<Serializable, LayoutPageTemplateCollection>
			layoutPageTemplateCollections = _persistence.fetchByPrimaryKeys(
				primaryKeys);

		Assert.assertEquals(1, layoutPageTemplateCollections.size());
		Assert.assertEquals(
			newLayoutPageTemplateCollection,
			layoutPageTemplateCollections.get(
				newLayoutPageTemplateCollection.getPrimaryKey()));
	}

	@Test
	public void testActionableDynamicQuery() throws Exception {
		final IntegerWrapper count = new IntegerWrapper();

		ActionableDynamicQuery actionableDynamicQuery =
			LayoutPageTemplateCollectionLocalServiceUtil.
				getActionableDynamicQuery();

		actionableDynamicQuery.setPerformActionMethod(
			new ActionableDynamicQuery.PerformActionMethod
				<LayoutPageTemplateCollection>() {

				@Override
				public void performAction(
					LayoutPageTemplateCollection layoutPageTemplateCollection) {

					Assert.assertNotNull(layoutPageTemplateCollection);

					count.increment();
				}

			});

		actionableDynamicQuery.performActions();

		Assert.assertEquals(count.getValue(), _persistence.countAll());
	}

	@Test
	public void testDynamicQueryByPrimaryKeyExisting() throws Exception {
		LayoutPageTemplateCollection newLayoutPageTemplateCollection =
			addLayoutPageTemplateCollection();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			LayoutPageTemplateCollection.class, _dynamicQueryClassLoader);

		dynamicQuery.add(
			RestrictionsFactoryUtil.eq(
				"layoutPageTemplateCollectionId",
				newLayoutPageTemplateCollection.
					getLayoutPageTemplateCollectionId()));

		List<LayoutPageTemplateCollection> result =
			_persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		LayoutPageTemplateCollection existingLayoutPageTemplateCollection =
			result.get(0);

		Assert.assertEquals(
			existingLayoutPageTemplateCollection,
			newLayoutPageTemplateCollection);
	}

	@Test
	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			LayoutPageTemplateCollection.class, _dynamicQueryClassLoader);

		dynamicQuery.add(
			RestrictionsFactoryUtil.eq(
				"layoutPageTemplateCollectionId", RandomTestUtil.nextLong()));

		List<LayoutPageTemplateCollection> result =
			_persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testDynamicQueryByProjectionExisting() throws Exception {
		LayoutPageTemplateCollection newLayoutPageTemplateCollection =
			addLayoutPageTemplateCollection();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			LayoutPageTemplateCollection.class, _dynamicQueryClassLoader);

		dynamicQuery.setProjection(
			ProjectionFactoryUtil.property("layoutPageTemplateCollectionId"));

		Object newLayoutPageTemplateCollectionId =
			newLayoutPageTemplateCollection.getLayoutPageTemplateCollectionId();

		dynamicQuery.add(
			RestrictionsFactoryUtil.in(
				"layoutPageTemplateCollectionId",
				new Object[] {newLayoutPageTemplateCollectionId}));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		Object existingLayoutPageTemplateCollectionId = result.get(0);

		Assert.assertEquals(
			existingLayoutPageTemplateCollectionId,
			newLayoutPageTemplateCollectionId);
	}

	@Test
	public void testDynamicQueryByProjectionMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			LayoutPageTemplateCollection.class, _dynamicQueryClassLoader);

		dynamicQuery.setProjection(
			ProjectionFactoryUtil.property("layoutPageTemplateCollectionId"));

		dynamicQuery.add(
			RestrictionsFactoryUtil.in(
				"layoutPageTemplateCollectionId",
				new Object[] {RandomTestUtil.nextLong()}));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testResetOriginalValues() throws Exception {
		LayoutPageTemplateCollection newLayoutPageTemplateCollection =
			addLayoutPageTemplateCollection();

		_persistence.clearCache();

		LayoutPageTemplateCollection existingLayoutPageTemplateCollection =
			_persistence.findByPrimaryKey(
				newLayoutPageTemplateCollection.getPrimaryKey());

		Assert.assertTrue(
			Objects.equals(
				existingLayoutPageTemplateCollection.getUuid(),
				ReflectionTestUtil.invoke(
					existingLayoutPageTemplateCollection, "getOriginalUuid",
					new Class<?>[0])));
		Assert.assertEquals(
			Long.valueOf(existingLayoutPageTemplateCollection.getGroupId()),
			ReflectionTestUtil.<Long>invoke(
				existingLayoutPageTemplateCollection, "getOriginalGroupId",
				new Class<?>[0]));

		Assert.assertEquals(
			Long.valueOf(existingLayoutPageTemplateCollection.getGroupId()),
			ReflectionTestUtil.<Long>invoke(
				existingLayoutPageTemplateCollection, "getOriginalGroupId",
				new Class<?>[0]));
		Assert.assertTrue(
			Objects.equals(
				existingLayoutPageTemplateCollection.getName(),
				ReflectionTestUtil.invoke(
					existingLayoutPageTemplateCollection, "getOriginalName",
					new Class<?>[0])));
	}

	protected LayoutPageTemplateCollection addLayoutPageTemplateCollection()
		throws Exception {

		long pk = RandomTestUtil.nextLong();

		LayoutPageTemplateCollection layoutPageTemplateCollection =
			_persistence.create(pk);

		layoutPageTemplateCollection.setMvccVersion(RandomTestUtil.nextLong());

		layoutPageTemplateCollection.setUuid(RandomTestUtil.randomString());

		layoutPageTemplateCollection.setGroupId(RandomTestUtil.nextLong());

		layoutPageTemplateCollection.setCompanyId(RandomTestUtil.nextLong());

		layoutPageTemplateCollection.setUserId(RandomTestUtil.nextLong());

		layoutPageTemplateCollection.setUserName(RandomTestUtil.randomString());

		layoutPageTemplateCollection.setCreateDate(RandomTestUtil.nextDate());

		layoutPageTemplateCollection.setModifiedDate(RandomTestUtil.nextDate());

		layoutPageTemplateCollection.setName(RandomTestUtil.randomString());

		layoutPageTemplateCollection.setDescription(
			RandomTestUtil.randomString());

		layoutPageTemplateCollection.setLastPublishDate(
			RandomTestUtil.nextDate());

		_layoutPageTemplateCollections.add(
			_persistence.update(layoutPageTemplateCollection));

		return layoutPageTemplateCollection;
	}

	private List<LayoutPageTemplateCollection> _layoutPageTemplateCollections =
		new ArrayList<LayoutPageTemplateCollection>();
	private LayoutPageTemplateCollectionPersistence _persistence;
	private ClassLoader _dynamicQueryClassLoader;

}