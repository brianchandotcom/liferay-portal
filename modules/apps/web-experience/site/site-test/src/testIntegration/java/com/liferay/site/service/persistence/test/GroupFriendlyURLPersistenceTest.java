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

package com.liferay.site.service.persistence.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;

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
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Time;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PersistenceTestRule;
import com.liferay.portal.test.rule.TransactionalTestRule;

import com.liferay.site.exception.NoSuchGroupFriendlyURLException;
import com.liferay.site.model.GroupFriendlyURL;
import com.liferay.site.service.GroupFriendlyURLLocalServiceUtil;
import com.liferay.site.service.persistence.GroupFriendlyURLPersistence;
import com.liferay.site.service.persistence.GroupFriendlyURLUtil;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import org.junit.runner.RunWith;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/**
 * @generated
 */
@RunWith(Arquillian.class)
public class GroupFriendlyURLPersistenceTest {
	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule = new AggregateTestRule(new LiferayIntegrationTestRule(),
			PersistenceTestRule.INSTANCE,
			new TransactionalTestRule(Propagation.REQUIRED,
				"com.liferay.site.service"));

	@Before
	public void setUp() {
		_persistence = GroupFriendlyURLUtil.getPersistence();

		Class<?> clazz = _persistence.getClass();

		_dynamicQueryClassLoader = clazz.getClassLoader();
	}

	@After
	public void tearDown() throws Exception {
		Iterator<GroupFriendlyURL> iterator = _groupFriendlyURLs.iterator();

		while (iterator.hasNext()) {
			_persistence.remove(iterator.next());

			iterator.remove();
		}
	}

	@Test
	public void testCreate() throws Exception {
		long pk = RandomTestUtil.nextLong();

		GroupFriendlyURL groupFriendlyURL = _persistence.create(pk);

		Assert.assertNotNull(groupFriendlyURL);

		Assert.assertEquals(groupFriendlyURL.getPrimaryKey(), pk);
	}

	@Test
	public void testRemove() throws Exception {
		GroupFriendlyURL newGroupFriendlyURL = addGroupFriendlyURL();

		_persistence.remove(newGroupFriendlyURL);

		GroupFriendlyURL existingGroupFriendlyURL = _persistence.fetchByPrimaryKey(newGroupFriendlyURL.getPrimaryKey());

		Assert.assertNull(existingGroupFriendlyURL);
	}

	@Test
	public void testUpdateNew() throws Exception {
		addGroupFriendlyURL();
	}

	@Test
	public void testUpdateExisting() throws Exception {
		long pk = RandomTestUtil.nextLong();

		GroupFriendlyURL newGroupFriendlyURL = _persistence.create(pk);

		newGroupFriendlyURL.setUuid(RandomTestUtil.randomString());

		newGroupFriendlyURL.setCompanyId(RandomTestUtil.nextLong());

		newGroupFriendlyURL.setUserId(RandomTestUtil.nextLong());

		newGroupFriendlyURL.setUserName(RandomTestUtil.randomString());

		newGroupFriendlyURL.setCreateDate(RandomTestUtil.nextDate());

		newGroupFriendlyURL.setModifiedDate(RandomTestUtil.nextDate());

		newGroupFriendlyURL.setGroupId(RandomTestUtil.nextLong());

		newGroupFriendlyURL.setFriendlyURL(RandomTestUtil.randomString());

		newGroupFriendlyURL.setLanguageId(RandomTestUtil.randomString());

		newGroupFriendlyURL.setLastPublishDate(RandomTestUtil.nextDate());

		_groupFriendlyURLs.add(_persistence.update(newGroupFriendlyURL));

		GroupFriendlyURL existingGroupFriendlyURL = _persistence.findByPrimaryKey(newGroupFriendlyURL.getPrimaryKey());

		Assert.assertEquals(existingGroupFriendlyURL.getUuid(),
			newGroupFriendlyURL.getUuid());
		Assert.assertEquals(existingGroupFriendlyURL.getGroupFriendlyURLId(),
			newGroupFriendlyURL.getGroupFriendlyURLId());
		Assert.assertEquals(existingGroupFriendlyURL.getCompanyId(),
			newGroupFriendlyURL.getCompanyId());
		Assert.assertEquals(existingGroupFriendlyURL.getUserId(),
			newGroupFriendlyURL.getUserId());
		Assert.assertEquals(existingGroupFriendlyURL.getUserName(),
			newGroupFriendlyURL.getUserName());
		Assert.assertEquals(Time.getShortTimestamp(
				existingGroupFriendlyURL.getCreateDate()),
			Time.getShortTimestamp(newGroupFriendlyURL.getCreateDate()));
		Assert.assertEquals(Time.getShortTimestamp(
				existingGroupFriendlyURL.getModifiedDate()),
			Time.getShortTimestamp(newGroupFriendlyURL.getModifiedDate()));
		Assert.assertEquals(existingGroupFriendlyURL.getGroupId(),
			newGroupFriendlyURL.getGroupId());
		Assert.assertEquals(existingGroupFriendlyURL.getFriendlyURL(),
			newGroupFriendlyURL.getFriendlyURL());
		Assert.assertEquals(existingGroupFriendlyURL.getLanguageId(),
			newGroupFriendlyURL.getLanguageId());
		Assert.assertEquals(Time.getShortTimestamp(
				existingGroupFriendlyURL.getLastPublishDate()),
			Time.getShortTimestamp(newGroupFriendlyURL.getLastPublishDate()));
	}

	@Test
	public void testCountByUuid() throws Exception {
		_persistence.countByUuid(StringPool.BLANK);

		_persistence.countByUuid(StringPool.NULL);

		_persistence.countByUuid((String)null);
	}

	@Test
	public void testCountByUUID_G() throws Exception {
		_persistence.countByUUID_G(StringPool.BLANK, RandomTestUtil.nextLong());

		_persistence.countByUUID_G(StringPool.NULL, 0L);

		_persistence.countByUUID_G((String)null, 0L);
	}

	@Test
	public void testCountByUuid_C() throws Exception {
		_persistence.countByUuid_C(StringPool.BLANK, RandomTestUtil.nextLong());

		_persistence.countByUuid_C(StringPool.NULL, 0L);

		_persistence.countByUuid_C((String)null, 0L);
	}

	@Test
	public void testCountByC_G() throws Exception {
		_persistence.countByC_G(RandomTestUtil.nextLong(),
			RandomTestUtil.nextLong());

		_persistence.countByC_G(0L, 0L);
	}

	@Test
	public void testCountByC_F() throws Exception {
		_persistence.countByC_F(RandomTestUtil.nextLong(), StringPool.BLANK);

		_persistence.countByC_F(0L, StringPool.NULL);

		_persistence.countByC_F(0L, (String)null);
	}

	@Test
	public void testCountByC_G_L() throws Exception {
		_persistence.countByC_G_L(RandomTestUtil.nextLong(),
			RandomTestUtil.nextLong(), StringPool.BLANK);

		_persistence.countByC_G_L(0L, 0L, StringPool.NULL);

		_persistence.countByC_G_L(0L, 0L, (String)null);
	}

	@Test
	public void testCountByC_F_L() throws Exception {
		_persistence.countByC_F_L(RandomTestUtil.nextLong(), StringPool.BLANK,
			StringPool.BLANK);

		_persistence.countByC_F_L(0L, StringPool.NULL, StringPool.NULL);

		_persistence.countByC_F_L(0L, (String)null, (String)null);
	}

	@Test
	public void testFindByPrimaryKeyExisting() throws Exception {
		GroupFriendlyURL newGroupFriendlyURL = addGroupFriendlyURL();

		GroupFriendlyURL existingGroupFriendlyURL = _persistence.findByPrimaryKey(newGroupFriendlyURL.getPrimaryKey());

		Assert.assertEquals(existingGroupFriendlyURL, newGroupFriendlyURL);
	}

	@Test(expected = NoSuchGroupFriendlyURLException.class)
	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		_persistence.findByPrimaryKey(pk);
	}

	@Test
	public void testFindAll() throws Exception {
		_persistence.findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			getOrderByComparator());
	}

	protected OrderByComparator<GroupFriendlyURL> getOrderByComparator() {
		return OrderByComparatorFactoryUtil.create("GroupFriendlyURL", "uuid",
			true, "groupFriendlyURLId", true, "companyId", true, "userId",
			true, "userName", true, "createDate", true, "modifiedDate", true,
			"groupId", true, "friendlyURL", true, "languageId", true,
			"lastPublishDate", true);
	}

	@Test
	public void testFetchByPrimaryKeyExisting() throws Exception {
		GroupFriendlyURL newGroupFriendlyURL = addGroupFriendlyURL();

		GroupFriendlyURL existingGroupFriendlyURL = _persistence.fetchByPrimaryKey(newGroupFriendlyURL.getPrimaryKey());

		Assert.assertEquals(existingGroupFriendlyURL, newGroupFriendlyURL);
	}

	@Test
	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		GroupFriendlyURL missingGroupFriendlyURL = _persistence.fetchByPrimaryKey(pk);

		Assert.assertNull(missingGroupFriendlyURL);
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereAllPrimaryKeysExist()
		throws Exception {
		GroupFriendlyURL newGroupFriendlyURL1 = addGroupFriendlyURL();
		GroupFriendlyURL newGroupFriendlyURL2 = addGroupFriendlyURL();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newGroupFriendlyURL1.getPrimaryKey());
		primaryKeys.add(newGroupFriendlyURL2.getPrimaryKey());

		Map<Serializable, GroupFriendlyURL> groupFriendlyURLs = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(2, groupFriendlyURLs.size());
		Assert.assertEquals(newGroupFriendlyURL1,
			groupFriendlyURLs.get(newGroupFriendlyURL1.getPrimaryKey()));
		Assert.assertEquals(newGroupFriendlyURL2,
			groupFriendlyURLs.get(newGroupFriendlyURL2.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereNoPrimaryKeysExist()
		throws Exception {
		long pk1 = RandomTestUtil.nextLong();

		long pk2 = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(pk1);
		primaryKeys.add(pk2);

		Map<Serializable, GroupFriendlyURL> groupFriendlyURLs = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(groupFriendlyURLs.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereSomePrimaryKeysExist()
		throws Exception {
		GroupFriendlyURL newGroupFriendlyURL = addGroupFriendlyURL();

		long pk = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newGroupFriendlyURL.getPrimaryKey());
		primaryKeys.add(pk);

		Map<Serializable, GroupFriendlyURL> groupFriendlyURLs = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, groupFriendlyURLs.size());
		Assert.assertEquals(newGroupFriendlyURL,
			groupFriendlyURLs.get(newGroupFriendlyURL.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithNoPrimaryKeys()
		throws Exception {
		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		Map<Serializable, GroupFriendlyURL> groupFriendlyURLs = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(groupFriendlyURLs.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithOnePrimaryKey()
		throws Exception {
		GroupFriendlyURL newGroupFriendlyURL = addGroupFriendlyURL();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newGroupFriendlyURL.getPrimaryKey());

		Map<Serializable, GroupFriendlyURL> groupFriendlyURLs = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, groupFriendlyURLs.size());
		Assert.assertEquals(newGroupFriendlyURL,
			groupFriendlyURLs.get(newGroupFriendlyURL.getPrimaryKey()));
	}

	@Test
	public void testActionableDynamicQuery() throws Exception {
		final IntegerWrapper count = new IntegerWrapper();

		ActionableDynamicQuery actionableDynamicQuery = GroupFriendlyURLLocalServiceUtil.getActionableDynamicQuery();

		actionableDynamicQuery.setPerformActionMethod(new ActionableDynamicQuery.PerformActionMethod<GroupFriendlyURL>() {
				@Override
				public void performAction(GroupFriendlyURL groupFriendlyURL) {
					Assert.assertNotNull(groupFriendlyURL);

					count.increment();
				}
			});

		actionableDynamicQuery.performActions();

		Assert.assertEquals(count.getValue(), _persistence.countAll());
	}

	@Test
	public void testDynamicQueryByPrimaryKeyExisting()
		throws Exception {
		GroupFriendlyURL newGroupFriendlyURL = addGroupFriendlyURL();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(GroupFriendlyURL.class,
				_dynamicQueryClassLoader);

		dynamicQuery.add(RestrictionsFactoryUtil.eq("groupFriendlyURLId",
				newGroupFriendlyURL.getGroupFriendlyURLId()));

		List<GroupFriendlyURL> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		GroupFriendlyURL existingGroupFriendlyURL = result.get(0);

		Assert.assertEquals(existingGroupFriendlyURL, newGroupFriendlyURL);
	}

	@Test
	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(GroupFriendlyURL.class,
				_dynamicQueryClassLoader);

		dynamicQuery.add(RestrictionsFactoryUtil.eq("groupFriendlyURLId",
				RandomTestUtil.nextLong()));

		List<GroupFriendlyURL> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testDynamicQueryByProjectionExisting()
		throws Exception {
		GroupFriendlyURL newGroupFriendlyURL = addGroupFriendlyURL();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(GroupFriendlyURL.class,
				_dynamicQueryClassLoader);

		dynamicQuery.setProjection(ProjectionFactoryUtil.property(
				"groupFriendlyURLId"));

		Object newGroupFriendlyURLId = newGroupFriendlyURL.getGroupFriendlyURLId();

		dynamicQuery.add(RestrictionsFactoryUtil.in("groupFriendlyURLId",
				new Object[] { newGroupFriendlyURLId }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		Object existingGroupFriendlyURLId = result.get(0);

		Assert.assertEquals(existingGroupFriendlyURLId, newGroupFriendlyURLId);
	}

	@Test
	public void testDynamicQueryByProjectionMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(GroupFriendlyURL.class,
				_dynamicQueryClassLoader);

		dynamicQuery.setProjection(ProjectionFactoryUtil.property(
				"groupFriendlyURLId"));

		dynamicQuery.add(RestrictionsFactoryUtil.in("groupFriendlyURLId",
				new Object[] { RandomTestUtil.nextLong() }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testResetOriginalValues() throws Exception {
		GroupFriendlyURL newGroupFriendlyURL = addGroupFriendlyURL();

		_persistence.clearCache();

		GroupFriendlyURL existingGroupFriendlyURL = _persistence.findByPrimaryKey(newGroupFriendlyURL.getPrimaryKey());

		Assert.assertTrue(Objects.equals(existingGroupFriendlyURL.getUuid(),
				ReflectionTestUtil.invoke(existingGroupFriendlyURL,
					"getOriginalUuid", new Class<?>[0])));
		Assert.assertEquals(Long.valueOf(existingGroupFriendlyURL.getGroupId()),
			ReflectionTestUtil.<Long>invoke(existingGroupFriendlyURL,
				"getOriginalGroupId", new Class<?>[0]));

		Assert.assertEquals(Long.valueOf(
				existingGroupFriendlyURL.getCompanyId()),
			ReflectionTestUtil.<Long>invoke(existingGroupFriendlyURL,
				"getOriginalCompanyId", new Class<?>[0]));
		Assert.assertTrue(Objects.equals(
				existingGroupFriendlyURL.getFriendlyURL(),
				ReflectionTestUtil.invoke(existingGroupFriendlyURL,
					"getOriginalFriendlyURL", new Class<?>[0])));

		Assert.assertEquals(Long.valueOf(
				existingGroupFriendlyURL.getCompanyId()),
			ReflectionTestUtil.<Long>invoke(existingGroupFriendlyURL,
				"getOriginalCompanyId", new Class<?>[0]));
		Assert.assertEquals(Long.valueOf(existingGroupFriendlyURL.getGroupId()),
			ReflectionTestUtil.<Long>invoke(existingGroupFriendlyURL,
				"getOriginalGroupId", new Class<?>[0]));
		Assert.assertTrue(Objects.equals(
				existingGroupFriendlyURL.getLanguageId(),
				ReflectionTestUtil.invoke(existingGroupFriendlyURL,
					"getOriginalLanguageId", new Class<?>[0])));

		Assert.assertEquals(Long.valueOf(
				existingGroupFriendlyURL.getCompanyId()),
			ReflectionTestUtil.<Long>invoke(existingGroupFriendlyURL,
				"getOriginalCompanyId", new Class<?>[0]));
		Assert.assertTrue(Objects.equals(
				existingGroupFriendlyURL.getFriendlyURL(),
				ReflectionTestUtil.invoke(existingGroupFriendlyURL,
					"getOriginalFriendlyURL", new Class<?>[0])));
		Assert.assertTrue(Objects.equals(
				existingGroupFriendlyURL.getLanguageId(),
				ReflectionTestUtil.invoke(existingGroupFriendlyURL,
					"getOriginalLanguageId", new Class<?>[0])));
	}

	protected GroupFriendlyURL addGroupFriendlyURL() throws Exception {
		long pk = RandomTestUtil.nextLong();

		GroupFriendlyURL groupFriendlyURL = _persistence.create(pk);

		groupFriendlyURL.setUuid(RandomTestUtil.randomString());

		groupFriendlyURL.setCompanyId(RandomTestUtil.nextLong());

		groupFriendlyURL.setUserId(RandomTestUtil.nextLong());

		groupFriendlyURL.setUserName(RandomTestUtil.randomString());

		groupFriendlyURL.setCreateDate(RandomTestUtil.nextDate());

		groupFriendlyURL.setModifiedDate(RandomTestUtil.nextDate());

		groupFriendlyURL.setGroupId(RandomTestUtil.nextLong());

		groupFriendlyURL.setFriendlyURL(RandomTestUtil.randomString());

		groupFriendlyURL.setLanguageId(RandomTestUtil.randomString());

		groupFriendlyURL.setLastPublishDate(RandomTestUtil.nextDate());

		_groupFriendlyURLs.add(_persistence.update(groupFriendlyURL));

		return groupFriendlyURL;
	}

	private List<GroupFriendlyURL> _groupFriendlyURLs = new ArrayList<GroupFriendlyURL>();
	private GroupFriendlyURLPersistence _persistence;
	private ClassLoader _dynamicQueryClassLoader;
}