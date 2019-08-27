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
import com.liferay.layout.seo.model.LayoutCanonicalURL;
import com.liferay.layout.seo.service.LayoutCanonicalURLLocalServiceUtil;
import com.liferay.layout.seo.service.persistence.LayoutCanonicalURLPersistence;
import com.liferay.layout.seo.service.persistence.LayoutCanonicalURLUtil;
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
public class LayoutCanonicalURLPersistenceTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(), PersistenceTestRule.INSTANCE,
			new TransactionalTestRule(
				Propagation.REQUIRED, "com.liferay.layout.seo.service"));

	@Before
	public void setUp() {
		_persistence = LayoutCanonicalURLUtil.getPersistence();

		Class<?> clazz = _persistence.getClass();

		_dynamicQueryClassLoader = clazz.getClassLoader();
	}

	@After
	public void tearDown() throws Exception {
		Iterator<LayoutCanonicalURL> iterator = _layoutCanonicalURLs.iterator();

		while (iterator.hasNext()) {
			_persistence.remove(iterator.next());

			iterator.remove();
		}
	}

	@Test
	public void testCreate() throws Exception {
		long pk = RandomTestUtil.nextLong();

		LayoutCanonicalURL layoutCanonicalURL = _persistence.create(pk);

		Assert.assertNotNull(layoutCanonicalURL);

		Assert.assertEquals(layoutCanonicalURL.getPrimaryKey(), pk);
	}

	@Test
	public void testRemove() throws Exception {
		LayoutCanonicalURL newLayoutCanonicalURL = addLayoutCanonicalURL();

		_persistence.remove(newLayoutCanonicalURL);

		LayoutCanonicalURL existingLayoutCanonicalURL =
			_persistence.fetchByPrimaryKey(
				newLayoutCanonicalURL.getPrimaryKey());

		Assert.assertNull(existingLayoutCanonicalURL);
	}

	@Test
	public void testUpdateNew() throws Exception {
		addLayoutCanonicalURL();
	}

	@Test
	public void testUpdateExisting() throws Exception {
		long pk = RandomTestUtil.nextLong();

		LayoutCanonicalURL newLayoutCanonicalURL = _persistence.create(pk);

		newLayoutCanonicalURL.setUuid(RandomTestUtil.randomString());

		newLayoutCanonicalURL.setGroupId(RandomTestUtil.nextLong());

		newLayoutCanonicalURL.setCompanyId(RandomTestUtil.nextLong());

		newLayoutCanonicalURL.setUserId(RandomTestUtil.nextLong());

		newLayoutCanonicalURL.setUserName(RandomTestUtil.randomString());

		newLayoutCanonicalURL.setCreateDate(RandomTestUtil.nextDate());

		newLayoutCanonicalURL.setModifiedDate(RandomTestUtil.nextDate());

		newLayoutCanonicalURL.setCanonicalURL(RandomTestUtil.randomString());

		newLayoutCanonicalURL.setEnabled(RandomTestUtil.randomBoolean());

		newLayoutCanonicalURL.setPrivateLayout(RandomTestUtil.randomBoolean());

		newLayoutCanonicalURL.setLastPublishDate(RandomTestUtil.nextDate());

		newLayoutCanonicalURL.setLayoutId(RandomTestUtil.nextLong());

		_layoutCanonicalURLs.add(_persistence.update(newLayoutCanonicalURL));

		LayoutCanonicalURL existingLayoutCanonicalURL =
			_persistence.findByPrimaryKey(
				newLayoutCanonicalURL.getPrimaryKey());

		Assert.assertEquals(
			existingLayoutCanonicalURL.getUuid(),
			newLayoutCanonicalURL.getUuid());
		Assert.assertEquals(
			existingLayoutCanonicalURL.getLayoutCanonicalURLId(),
			newLayoutCanonicalURL.getLayoutCanonicalURLId());
		Assert.assertEquals(
			existingLayoutCanonicalURL.getGroupId(),
			newLayoutCanonicalURL.getGroupId());
		Assert.assertEquals(
			existingLayoutCanonicalURL.getCompanyId(),
			newLayoutCanonicalURL.getCompanyId());
		Assert.assertEquals(
			existingLayoutCanonicalURL.getUserId(),
			newLayoutCanonicalURL.getUserId());
		Assert.assertEquals(
			existingLayoutCanonicalURL.getUserName(),
			newLayoutCanonicalURL.getUserName());
		Assert.assertEquals(
			Time.getShortTimestamp(existingLayoutCanonicalURL.getCreateDate()),
			Time.getShortTimestamp(newLayoutCanonicalURL.getCreateDate()));
		Assert.assertEquals(
			Time.getShortTimestamp(
				existingLayoutCanonicalURL.getModifiedDate()),
			Time.getShortTimestamp(newLayoutCanonicalURL.getModifiedDate()));
		Assert.assertEquals(
			existingLayoutCanonicalURL.getCanonicalURL(),
			newLayoutCanonicalURL.getCanonicalURL());
		Assert.assertEquals(
			existingLayoutCanonicalURL.isEnabled(),
			newLayoutCanonicalURL.isEnabled());
		Assert.assertEquals(
			existingLayoutCanonicalURL.isPrivateLayout(),
			newLayoutCanonicalURL.isPrivateLayout());
		Assert.assertEquals(
			Time.getShortTimestamp(
				existingLayoutCanonicalURL.getLastPublishDate()),
			Time.getShortTimestamp(newLayoutCanonicalURL.getLastPublishDate()));
		Assert.assertEquals(
			existingLayoutCanonicalURL.getLayoutId(),
			newLayoutCanonicalURL.getLayoutId());
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
		LayoutCanonicalURL newLayoutCanonicalURL = addLayoutCanonicalURL();

		LayoutCanonicalURL existingLayoutCanonicalURL =
			_persistence.findByPrimaryKey(
				newLayoutCanonicalURL.getPrimaryKey());

		Assert.assertEquals(existingLayoutCanonicalURL, newLayoutCanonicalURL);
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

	protected OrderByComparator<LayoutCanonicalURL> getOrderByComparator() {
		return OrderByComparatorFactoryUtil.create(
			"LayoutCanonicalURL", "uuid", true, "layoutCanonicalURLId", true,
			"groupId", true, "companyId", true, "userId", true, "userName",
			true, "createDate", true, "modifiedDate", true, "canonicalURL",
			true, "enabled", true, "privateLayout", true, "lastPublishDate",
			true, "layoutId", true);
	}

	@Test
	public void testFetchByPrimaryKeyExisting() throws Exception {
		LayoutCanonicalURL newLayoutCanonicalURL = addLayoutCanonicalURL();

		LayoutCanonicalURL existingLayoutCanonicalURL =
			_persistence.fetchByPrimaryKey(
				newLayoutCanonicalURL.getPrimaryKey());

		Assert.assertEquals(existingLayoutCanonicalURL, newLayoutCanonicalURL);
	}

	@Test
	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		LayoutCanonicalURL missingLayoutCanonicalURL =
			_persistence.fetchByPrimaryKey(pk);

		Assert.assertNull(missingLayoutCanonicalURL);
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereAllPrimaryKeysExist()
		throws Exception {

		LayoutCanonicalURL newLayoutCanonicalURL1 = addLayoutCanonicalURL();
		LayoutCanonicalURL newLayoutCanonicalURL2 = addLayoutCanonicalURL();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newLayoutCanonicalURL1.getPrimaryKey());
		primaryKeys.add(newLayoutCanonicalURL2.getPrimaryKey());

		Map<Serializable, LayoutCanonicalURL> layoutCanonicalURLs =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(2, layoutCanonicalURLs.size());
		Assert.assertEquals(
			newLayoutCanonicalURL1,
			layoutCanonicalURLs.get(newLayoutCanonicalURL1.getPrimaryKey()));
		Assert.assertEquals(
			newLayoutCanonicalURL2,
			layoutCanonicalURLs.get(newLayoutCanonicalURL2.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereNoPrimaryKeysExist()
		throws Exception {

		long pk1 = RandomTestUtil.nextLong();

		long pk2 = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(pk1);
		primaryKeys.add(pk2);

		Map<Serializable, LayoutCanonicalURL> layoutCanonicalURLs =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(layoutCanonicalURLs.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereSomePrimaryKeysExist()
		throws Exception {

		LayoutCanonicalURL newLayoutCanonicalURL = addLayoutCanonicalURL();

		long pk = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newLayoutCanonicalURL.getPrimaryKey());
		primaryKeys.add(pk);

		Map<Serializable, LayoutCanonicalURL> layoutCanonicalURLs =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, layoutCanonicalURLs.size());
		Assert.assertEquals(
			newLayoutCanonicalURL,
			layoutCanonicalURLs.get(newLayoutCanonicalURL.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithNoPrimaryKeys() throws Exception {
		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		Map<Serializable, LayoutCanonicalURL> layoutCanonicalURLs =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(layoutCanonicalURLs.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithOnePrimaryKey() throws Exception {
		LayoutCanonicalURL newLayoutCanonicalURL = addLayoutCanonicalURL();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newLayoutCanonicalURL.getPrimaryKey());

		Map<Serializable, LayoutCanonicalURL> layoutCanonicalURLs =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, layoutCanonicalURLs.size());
		Assert.assertEquals(
			newLayoutCanonicalURL,
			layoutCanonicalURLs.get(newLayoutCanonicalURL.getPrimaryKey()));
	}

	@Test
	public void testActionableDynamicQuery() throws Exception {
		final IntegerWrapper count = new IntegerWrapper();

		ActionableDynamicQuery actionableDynamicQuery =
			LayoutCanonicalURLLocalServiceUtil.getActionableDynamicQuery();

		actionableDynamicQuery.setPerformActionMethod(
			new ActionableDynamicQuery.PerformActionMethod
				<LayoutCanonicalURL>() {

				@Override
				public void performAction(
					LayoutCanonicalURL layoutCanonicalURL) {

					Assert.assertNotNull(layoutCanonicalURL);

					count.increment();
				}

			});

		actionableDynamicQuery.performActions();

		Assert.assertEquals(count.getValue(), _persistence.countAll());
	}

	@Test
	public void testDynamicQueryByPrimaryKeyExisting() throws Exception {
		LayoutCanonicalURL newLayoutCanonicalURL = addLayoutCanonicalURL();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			LayoutCanonicalURL.class, _dynamicQueryClassLoader);

		dynamicQuery.add(
			RestrictionsFactoryUtil.eq(
				"layoutCanonicalURLId",
				newLayoutCanonicalURL.getLayoutCanonicalURLId()));

		List<LayoutCanonicalURL> result = _persistence.findWithDynamicQuery(
			dynamicQuery);

		Assert.assertEquals(1, result.size());

		LayoutCanonicalURL existingLayoutCanonicalURL = result.get(0);

		Assert.assertEquals(existingLayoutCanonicalURL, newLayoutCanonicalURL);
	}

	@Test
	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			LayoutCanonicalURL.class, _dynamicQueryClassLoader);

		dynamicQuery.add(
			RestrictionsFactoryUtil.eq(
				"layoutCanonicalURLId", RandomTestUtil.nextLong()));

		List<LayoutCanonicalURL> result = _persistence.findWithDynamicQuery(
			dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testDynamicQueryByProjectionExisting() throws Exception {
		LayoutCanonicalURL newLayoutCanonicalURL = addLayoutCanonicalURL();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			LayoutCanonicalURL.class, _dynamicQueryClassLoader);

		dynamicQuery.setProjection(
			ProjectionFactoryUtil.property("layoutCanonicalURLId"));

		Object newLayoutCanonicalURLId =
			newLayoutCanonicalURL.getLayoutCanonicalURLId();

		dynamicQuery.add(
			RestrictionsFactoryUtil.in(
				"layoutCanonicalURLId",
				new Object[] {newLayoutCanonicalURLId}));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		Object existingLayoutCanonicalURLId = result.get(0);

		Assert.assertEquals(
			existingLayoutCanonicalURLId, newLayoutCanonicalURLId);
	}

	@Test
	public void testDynamicQueryByProjectionMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			LayoutCanonicalURL.class, _dynamicQueryClassLoader);

		dynamicQuery.setProjection(
			ProjectionFactoryUtil.property("layoutCanonicalURLId"));

		dynamicQuery.add(
			RestrictionsFactoryUtil.in(
				"layoutCanonicalURLId",
				new Object[] {RandomTestUtil.nextLong()}));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testResetOriginalValues() throws Exception {
		LayoutCanonicalURL newLayoutCanonicalURL = addLayoutCanonicalURL();

		_persistence.clearCache();

		LayoutCanonicalURL existingLayoutCanonicalURL =
			_persistence.findByPrimaryKey(
				newLayoutCanonicalURL.getPrimaryKey());

		Assert.assertTrue(
			Objects.equals(
				existingLayoutCanonicalURL.getUuid(),
				ReflectionTestUtil.invoke(
					existingLayoutCanonicalURL, "getOriginalUuid",
					new Class<?>[0])));
		Assert.assertEquals(
			Long.valueOf(existingLayoutCanonicalURL.getGroupId()),
			ReflectionTestUtil.<Long>invoke(
				existingLayoutCanonicalURL, "getOriginalGroupId",
				new Class<?>[0]));

		Assert.assertEquals(
			Long.valueOf(existingLayoutCanonicalURL.getGroupId()),
			ReflectionTestUtil.<Long>invoke(
				existingLayoutCanonicalURL, "getOriginalGroupId",
				new Class<?>[0]));
		Assert.assertEquals(
			Boolean.valueOf(existingLayoutCanonicalURL.getPrivateLayout()),
			ReflectionTestUtil.<Boolean>invoke(
				existingLayoutCanonicalURL, "getOriginalPrivateLayout",
				new Class<?>[0]));
		Assert.assertEquals(
			Long.valueOf(existingLayoutCanonicalURL.getLayoutId()),
			ReflectionTestUtil.<Long>invoke(
				existingLayoutCanonicalURL, "getOriginalLayoutId",
				new Class<?>[0]));
	}

	protected LayoutCanonicalURL addLayoutCanonicalURL() throws Exception {
		long pk = RandomTestUtil.nextLong();

		LayoutCanonicalURL layoutCanonicalURL = _persistence.create(pk);

		layoutCanonicalURL.setUuid(RandomTestUtil.randomString());

		layoutCanonicalURL.setGroupId(RandomTestUtil.nextLong());

		layoutCanonicalURL.setCompanyId(RandomTestUtil.nextLong());

		layoutCanonicalURL.setUserId(RandomTestUtil.nextLong());

		layoutCanonicalURL.setUserName(RandomTestUtil.randomString());

		layoutCanonicalURL.setCreateDate(RandomTestUtil.nextDate());

		layoutCanonicalURL.setModifiedDate(RandomTestUtil.nextDate());

		layoutCanonicalURL.setCanonicalURL(RandomTestUtil.randomString());

		layoutCanonicalURL.setEnabled(RandomTestUtil.randomBoolean());

		layoutCanonicalURL.setPrivateLayout(RandomTestUtil.randomBoolean());

		layoutCanonicalURL.setLastPublishDate(RandomTestUtil.nextDate());

		layoutCanonicalURL.setLayoutId(RandomTestUtil.nextLong());

		_layoutCanonicalURLs.add(_persistence.update(layoutCanonicalURL));

		return layoutCanonicalURL;
	}

	private List<LayoutCanonicalURL> _layoutCanonicalURLs =
		new ArrayList<LayoutCanonicalURL>();
	private LayoutCanonicalURLPersistence _persistence;
	private ClassLoader _dynamicQueryClassLoader;

}