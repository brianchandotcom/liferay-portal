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
import com.liferay.layout.seo.exception.NoSuchCanonicalURLException;
import com.liferay.layout.seo.model.LayoutSEOCanonicalURL;
import com.liferay.layout.seo.service.LayoutSEOCanonicalURLLocalServiceUtil;
import com.liferay.layout.seo.service.persistence.LayoutSEOCanonicalURLPersistence;
import com.liferay.layout.seo.service.persistence.LayoutSEOCanonicalURLUtil;
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
public class LayoutSEOCanonicalURLPersistenceTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(), PersistenceTestRule.INSTANCE,
			new TransactionalTestRule(
				Propagation.REQUIRED, "com.liferay.layout.seo.service"));

	@Before
	public void setUp() {
		_persistence = LayoutSEOCanonicalURLUtil.getPersistence();

		Class<?> clazz = _persistence.getClass();

		_dynamicQueryClassLoader = clazz.getClassLoader();
	}

	@After
	public void tearDown() throws Exception {
		Iterator<LayoutSEOCanonicalURL> iterator =
			_layoutSEOCanonicalURLs.iterator();

		while (iterator.hasNext()) {
			_persistence.remove(iterator.next());

			iterator.remove();
		}
	}

	@Test
	public void testCreate() throws Exception {
		long pk = RandomTestUtil.nextLong();

		LayoutSEOCanonicalURL layoutSEOCanonicalURL = _persistence.create(pk);

		Assert.assertNotNull(layoutSEOCanonicalURL);

		Assert.assertEquals(layoutSEOCanonicalURL.getPrimaryKey(), pk);
	}

	@Test
	public void testRemove() throws Exception {
		LayoutSEOCanonicalURL newLayoutSEOCanonicalURL =
			addLayoutSEOCanonicalURL();

		_persistence.remove(newLayoutSEOCanonicalURL);

		LayoutSEOCanonicalURL existingLayoutSEOCanonicalURL =
			_persistence.fetchByPrimaryKey(
				newLayoutSEOCanonicalURL.getPrimaryKey());

		Assert.assertNull(existingLayoutSEOCanonicalURL);
	}

	@Test
	public void testUpdateNew() throws Exception {
		addLayoutSEOCanonicalURL();
	}

	@Test
	public void testUpdateExisting() throws Exception {
		long pk = RandomTestUtil.nextLong();

		LayoutSEOCanonicalURL newLayoutSEOCanonicalURL = _persistence.create(
			pk);

		newLayoutSEOCanonicalURL.setUuid(RandomTestUtil.randomString());

		newLayoutSEOCanonicalURL.setGroupId(RandomTestUtil.nextLong());

		newLayoutSEOCanonicalURL.setCompanyId(RandomTestUtil.nextLong());

		newLayoutSEOCanonicalURL.setUserId(RandomTestUtil.nextLong());

		newLayoutSEOCanonicalURL.setUserName(RandomTestUtil.randomString());

		newLayoutSEOCanonicalURL.setCreateDate(RandomTestUtil.nextDate());

		newLayoutSEOCanonicalURL.setModifiedDate(RandomTestUtil.nextDate());

		newLayoutSEOCanonicalURL.setCanonicalURL(RandomTestUtil.randomString());

		newLayoutSEOCanonicalURL.setEnabled(RandomTestUtil.randomBoolean());

		newLayoutSEOCanonicalURL.setPrivateLayout(
			RandomTestUtil.randomBoolean());

		newLayoutSEOCanonicalURL.setLastPublishDate(RandomTestUtil.nextDate());

		newLayoutSEOCanonicalURL.setLayoutId(RandomTestUtil.nextLong());

		_layoutSEOCanonicalURLs.add(
			_persistence.update(newLayoutSEOCanonicalURL));

		LayoutSEOCanonicalURL existingLayoutSEOCanonicalURL =
			_persistence.findByPrimaryKey(
				newLayoutSEOCanonicalURL.getPrimaryKey());

		Assert.assertEquals(
			existingLayoutSEOCanonicalURL.getUuid(),
			newLayoutSEOCanonicalURL.getUuid());
		Assert.assertEquals(
			existingLayoutSEOCanonicalURL.getLayoutSEOCanonicalURLId(),
			newLayoutSEOCanonicalURL.getLayoutSEOCanonicalURLId());
		Assert.assertEquals(
			existingLayoutSEOCanonicalURL.getGroupId(),
			newLayoutSEOCanonicalURL.getGroupId());
		Assert.assertEquals(
			existingLayoutSEOCanonicalURL.getCompanyId(),
			newLayoutSEOCanonicalURL.getCompanyId());
		Assert.assertEquals(
			existingLayoutSEOCanonicalURL.getUserId(),
			newLayoutSEOCanonicalURL.getUserId());
		Assert.assertEquals(
			existingLayoutSEOCanonicalURL.getUserName(),
			newLayoutSEOCanonicalURL.getUserName());
		Assert.assertEquals(
			Time.getShortTimestamp(
				existingLayoutSEOCanonicalURL.getCreateDate()),
			Time.getShortTimestamp(newLayoutSEOCanonicalURL.getCreateDate()));
		Assert.assertEquals(
			Time.getShortTimestamp(
				existingLayoutSEOCanonicalURL.getModifiedDate()),
			Time.getShortTimestamp(newLayoutSEOCanonicalURL.getModifiedDate()));
		Assert.assertEquals(
			existingLayoutSEOCanonicalURL.getCanonicalURL(),
			newLayoutSEOCanonicalURL.getCanonicalURL());
		Assert.assertEquals(
			existingLayoutSEOCanonicalURL.isEnabled(),
			newLayoutSEOCanonicalURL.isEnabled());
		Assert.assertEquals(
			existingLayoutSEOCanonicalURL.isPrivateLayout(),
			newLayoutSEOCanonicalURL.isPrivateLayout());
		Assert.assertEquals(
			Time.getShortTimestamp(
				existingLayoutSEOCanonicalURL.getLastPublishDate()),
			Time.getShortTimestamp(
				newLayoutSEOCanonicalURL.getLastPublishDate()));
		Assert.assertEquals(
			existingLayoutSEOCanonicalURL.getLayoutId(),
			newLayoutSEOCanonicalURL.getLayoutId());
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
	public void testCountByG_P_L() throws Exception {
		_persistence.countByG_P_L(
			RandomTestUtil.nextLong(), RandomTestUtil.randomBoolean(),
			RandomTestUtil.nextLong());

		_persistence.countByG_P_L(0L, RandomTestUtil.randomBoolean(), 0L);
	}

	@Test
	public void testFindByPrimaryKeyExisting() throws Exception {
		LayoutSEOCanonicalURL newLayoutSEOCanonicalURL =
			addLayoutSEOCanonicalURL();

		LayoutSEOCanonicalURL existingLayoutSEOCanonicalURL =
			_persistence.findByPrimaryKey(
				newLayoutSEOCanonicalURL.getPrimaryKey());

		Assert.assertEquals(
			existingLayoutSEOCanonicalURL, newLayoutSEOCanonicalURL);
	}

	@Test(expected = NoSuchCanonicalURLException.class)
	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		_persistence.findByPrimaryKey(pk);
	}

	@Test
	public void testFindAll() throws Exception {
		_persistence.findAll(
			QueryUtil.ALL_POS, QueryUtil.ALL_POS, getOrderByComparator());
	}

	protected OrderByComparator<LayoutSEOCanonicalURL> getOrderByComparator() {
		return OrderByComparatorFactoryUtil.create(
			"LayoutSEOCanonicalURL", "uuid", true, "layoutSEOCanonicalURLId",
			true, "groupId", true, "companyId", true, "userId", true,
			"userName", true, "createDate", true, "modifiedDate", true,
			"canonicalURL", true, "enabled", true, "privateLayout", true,
			"lastPublishDate", true, "layoutId", true);
	}

	@Test
	public void testFetchByPrimaryKeyExisting() throws Exception {
		LayoutSEOCanonicalURL newLayoutSEOCanonicalURL =
			addLayoutSEOCanonicalURL();

		LayoutSEOCanonicalURL existingLayoutSEOCanonicalURL =
			_persistence.fetchByPrimaryKey(
				newLayoutSEOCanonicalURL.getPrimaryKey());

		Assert.assertEquals(
			existingLayoutSEOCanonicalURL, newLayoutSEOCanonicalURL);
	}

	@Test
	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		LayoutSEOCanonicalURL missingLayoutSEOCanonicalURL =
			_persistence.fetchByPrimaryKey(pk);

		Assert.assertNull(missingLayoutSEOCanonicalURL);
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereAllPrimaryKeysExist()
		throws Exception {

		LayoutSEOCanonicalURL newLayoutSEOCanonicalURL1 =
			addLayoutSEOCanonicalURL();
		LayoutSEOCanonicalURL newLayoutSEOCanonicalURL2 =
			addLayoutSEOCanonicalURL();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newLayoutSEOCanonicalURL1.getPrimaryKey());
		primaryKeys.add(newLayoutSEOCanonicalURL2.getPrimaryKey());

		Map<Serializable, LayoutSEOCanonicalURL> layoutSEOCanonicalURLs =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(2, layoutSEOCanonicalURLs.size());
		Assert.assertEquals(
			newLayoutSEOCanonicalURL1,
			layoutSEOCanonicalURLs.get(
				newLayoutSEOCanonicalURL1.getPrimaryKey()));
		Assert.assertEquals(
			newLayoutSEOCanonicalURL2,
			layoutSEOCanonicalURLs.get(
				newLayoutSEOCanonicalURL2.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereNoPrimaryKeysExist()
		throws Exception {

		long pk1 = RandomTestUtil.nextLong();

		long pk2 = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(pk1);
		primaryKeys.add(pk2);

		Map<Serializable, LayoutSEOCanonicalURL> layoutSEOCanonicalURLs =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(layoutSEOCanonicalURLs.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereSomePrimaryKeysExist()
		throws Exception {

		LayoutSEOCanonicalURL newLayoutSEOCanonicalURL =
			addLayoutSEOCanonicalURL();

		long pk = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newLayoutSEOCanonicalURL.getPrimaryKey());
		primaryKeys.add(pk);

		Map<Serializable, LayoutSEOCanonicalURL> layoutSEOCanonicalURLs =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, layoutSEOCanonicalURLs.size());
		Assert.assertEquals(
			newLayoutSEOCanonicalURL,
			layoutSEOCanonicalURLs.get(
				newLayoutSEOCanonicalURL.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithNoPrimaryKeys() throws Exception {
		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		Map<Serializable, LayoutSEOCanonicalURL> layoutSEOCanonicalURLs =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(layoutSEOCanonicalURLs.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithOnePrimaryKey() throws Exception {
		LayoutSEOCanonicalURL newLayoutSEOCanonicalURL =
			addLayoutSEOCanonicalURL();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newLayoutSEOCanonicalURL.getPrimaryKey());

		Map<Serializable, LayoutSEOCanonicalURL> layoutSEOCanonicalURLs =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, layoutSEOCanonicalURLs.size());
		Assert.assertEquals(
			newLayoutSEOCanonicalURL,
			layoutSEOCanonicalURLs.get(
				newLayoutSEOCanonicalURL.getPrimaryKey()));
	}

	@Test
	public void testActionableDynamicQuery() throws Exception {
		final IntegerWrapper count = new IntegerWrapper();

		ActionableDynamicQuery actionableDynamicQuery =
			LayoutSEOCanonicalURLLocalServiceUtil.getActionableDynamicQuery();

		actionableDynamicQuery.setPerformActionMethod(
			new ActionableDynamicQuery.PerformActionMethod
				<LayoutSEOCanonicalURL>() {

				@Override
				public void performAction(
					LayoutSEOCanonicalURL layoutSEOCanonicalURL) {

					Assert.assertNotNull(layoutSEOCanonicalURL);

					count.increment();
				}

			});

		actionableDynamicQuery.performActions();

		Assert.assertEquals(count.getValue(), _persistence.countAll());
	}

	@Test
	public void testDynamicQueryByPrimaryKeyExisting() throws Exception {
		LayoutSEOCanonicalURL newLayoutSEOCanonicalURL =
			addLayoutSEOCanonicalURL();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			LayoutSEOCanonicalURL.class, _dynamicQueryClassLoader);

		dynamicQuery.add(
			RestrictionsFactoryUtil.eq(
				"layoutSEOCanonicalURLId",
				newLayoutSEOCanonicalURL.getLayoutSEOCanonicalURLId()));

		List<LayoutSEOCanonicalURL> result = _persistence.findWithDynamicQuery(
			dynamicQuery);

		Assert.assertEquals(1, result.size());

		LayoutSEOCanonicalURL existingLayoutSEOCanonicalURL = result.get(0);

		Assert.assertEquals(
			existingLayoutSEOCanonicalURL, newLayoutSEOCanonicalURL);
	}

	@Test
	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			LayoutSEOCanonicalURL.class, _dynamicQueryClassLoader);

		dynamicQuery.add(
			RestrictionsFactoryUtil.eq(
				"layoutSEOCanonicalURLId", RandomTestUtil.nextLong()));

		List<LayoutSEOCanonicalURL> result = _persistence.findWithDynamicQuery(
			dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testDynamicQueryByProjectionExisting() throws Exception {
		LayoutSEOCanonicalURL newLayoutSEOCanonicalURL =
			addLayoutSEOCanonicalURL();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			LayoutSEOCanonicalURL.class, _dynamicQueryClassLoader);

		dynamicQuery.setProjection(
			ProjectionFactoryUtil.property("layoutSEOCanonicalURLId"));

		Object newLayoutSEOCanonicalURLId =
			newLayoutSEOCanonicalURL.getLayoutSEOCanonicalURLId();

		dynamicQuery.add(
			RestrictionsFactoryUtil.in(
				"layoutSEOCanonicalURLId",
				new Object[] {newLayoutSEOCanonicalURLId}));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		Object existingLayoutSEOCanonicalURLId = result.get(0);

		Assert.assertEquals(
			existingLayoutSEOCanonicalURLId, newLayoutSEOCanonicalURLId);
	}

	@Test
	public void testDynamicQueryByProjectionMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			LayoutSEOCanonicalURL.class, _dynamicQueryClassLoader);

		dynamicQuery.setProjection(
			ProjectionFactoryUtil.property("layoutSEOCanonicalURLId"));

		dynamicQuery.add(
			RestrictionsFactoryUtil.in(
				"layoutSEOCanonicalURLId",
				new Object[] {RandomTestUtil.nextLong()}));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testResetOriginalValues() throws Exception {
		LayoutSEOCanonicalURL newLayoutSEOCanonicalURL =
			addLayoutSEOCanonicalURL();

		_persistence.clearCache();

		LayoutSEOCanonicalURL existingLayoutSEOCanonicalURL =
			_persistence.findByPrimaryKey(
				newLayoutSEOCanonicalURL.getPrimaryKey());

		Assert.assertTrue(
			Objects.equals(
				existingLayoutSEOCanonicalURL.getUuid(),
				ReflectionTestUtil.invoke(
					existingLayoutSEOCanonicalURL, "getOriginalUuid",
					new Class<?>[0])));
		Assert.assertEquals(
			Long.valueOf(existingLayoutSEOCanonicalURL.getGroupId()),
			ReflectionTestUtil.<Long>invoke(
				existingLayoutSEOCanonicalURL, "getOriginalGroupId",
				new Class<?>[0]));

		Assert.assertEquals(
			Long.valueOf(existingLayoutSEOCanonicalURL.getGroupId()),
			ReflectionTestUtil.<Long>invoke(
				existingLayoutSEOCanonicalURL, "getOriginalGroupId",
				new Class<?>[0]));
		Assert.assertEquals(
			Boolean.valueOf(existingLayoutSEOCanonicalURL.getPrivateLayout()),
			ReflectionTestUtil.<Boolean>invoke(
				existingLayoutSEOCanonicalURL, "getOriginalPrivateLayout",
				new Class<?>[0]));
		Assert.assertEquals(
			Long.valueOf(existingLayoutSEOCanonicalURL.getLayoutId()),
			ReflectionTestUtil.<Long>invoke(
				existingLayoutSEOCanonicalURL, "getOriginalLayoutId",
				new Class<?>[0]));
	}

	protected LayoutSEOCanonicalURL addLayoutSEOCanonicalURL()
		throws Exception {

		long pk = RandomTestUtil.nextLong();

		LayoutSEOCanonicalURL layoutSEOCanonicalURL = _persistence.create(pk);

		layoutSEOCanonicalURL.setUuid(RandomTestUtil.randomString());

		layoutSEOCanonicalURL.setGroupId(RandomTestUtil.nextLong());

		layoutSEOCanonicalURL.setCompanyId(RandomTestUtil.nextLong());

		layoutSEOCanonicalURL.setUserId(RandomTestUtil.nextLong());

		layoutSEOCanonicalURL.setUserName(RandomTestUtil.randomString());

		layoutSEOCanonicalURL.setCreateDate(RandomTestUtil.nextDate());

		layoutSEOCanonicalURL.setModifiedDate(RandomTestUtil.nextDate());

		layoutSEOCanonicalURL.setCanonicalURL(RandomTestUtil.randomString());

		layoutSEOCanonicalURL.setEnabled(RandomTestUtil.randomBoolean());

		layoutSEOCanonicalURL.setPrivateLayout(RandomTestUtil.randomBoolean());

		layoutSEOCanonicalURL.setLastPublishDate(RandomTestUtil.nextDate());

		layoutSEOCanonicalURL.setLayoutId(RandomTestUtil.nextLong());

		_layoutSEOCanonicalURLs.add(_persistence.update(layoutSEOCanonicalURL));

		return layoutSEOCanonicalURL;
	}

	private List<LayoutSEOCanonicalURL> _layoutSEOCanonicalURLs =
		new ArrayList<LayoutSEOCanonicalURL>();
	private LayoutSEOCanonicalURLPersistence _persistence;
	private ClassLoader _dynamicQueryClassLoader;

}