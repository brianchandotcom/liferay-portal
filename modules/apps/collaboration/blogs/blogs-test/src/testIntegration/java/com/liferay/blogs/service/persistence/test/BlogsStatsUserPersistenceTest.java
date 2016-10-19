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

package com.liferay.blogs.service.persistence.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;

import com.liferay.blogs.exception.NoSuchStatsUserException;
import com.liferay.blogs.model.BlogsStatsUser;
import com.liferay.blogs.service.BlogsStatsUserLocalServiceUtil;
import com.liferay.blogs.service.persistence.BlogsStatsUserPersistence;
import com.liferay.blogs.service.persistence.BlogsStatsUserUtil;

import com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQueryFactoryUtil;
import com.liferay.portal.kernel.dao.orm.ProjectionFactoryUtil;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.RestrictionsFactoryUtil;
import com.liferay.portal.kernel.test.AssertUtils;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.TransactionalTestRule;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.transaction.Propagation;
import com.liferay.portal.kernel.util.IntegerWrapper;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.OrderByComparatorFactoryUtil;
import com.liferay.portal.kernel.util.Time;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PersistenceTestRule;

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
import java.util.Set;

/**
 * @generated
 */
@RunWith(Arquillian.class)
public class BlogsStatsUserPersistenceTest {
	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule = new AggregateTestRule(new LiferayIntegrationTestRule(),
			PersistenceTestRule.INSTANCE,
			new TransactionalTestRule(Propagation.REQUIRED));

	@Before
	public void setUp() {
		_persistence = BlogsStatsUserUtil.getPersistence();

		Class<?> clazz = _persistence.getClass();

		_dynamicQueryClassLoader = clazz.getClassLoader();
	}

	@After
	public void tearDown() throws Exception {
		Iterator<BlogsStatsUser> iterator = _blogsStatsUsers.iterator();

		while (iterator.hasNext()) {
			_persistence.remove(iterator.next());

			iterator.remove();
		}
	}

	@Test
	public void testCreate() throws Exception {
		long pk = RandomTestUtil.nextLong();

		BlogsStatsUser blogsStatsUser = _persistence.create(pk);

		Assert.assertNotNull(blogsStatsUser);

		Assert.assertEquals(blogsStatsUser.getPrimaryKey(), pk);
	}

	@Test
	public void testRemove() throws Exception {
		BlogsStatsUser newBlogsStatsUser = addBlogsStatsUser();

		_persistence.remove(newBlogsStatsUser);

		BlogsStatsUser existingBlogsStatsUser = _persistence.fetchByPrimaryKey(newBlogsStatsUser.getPrimaryKey());

		Assert.assertNull(existingBlogsStatsUser);
	}

	@Test
	public void testUpdateNew() throws Exception {
		addBlogsStatsUser();
	}

	@Test
	public void testUpdateExisting() throws Exception {
		long pk = RandomTestUtil.nextLong();

		BlogsStatsUser newBlogsStatsUser = _persistence.create(pk);

		newBlogsStatsUser.setGroupId(RandomTestUtil.nextLong());

		newBlogsStatsUser.setCompanyId(RandomTestUtil.nextLong());

		newBlogsStatsUser.setUserId(RandomTestUtil.nextLong());

		newBlogsStatsUser.setEntryCount(RandomTestUtil.nextInt());

		newBlogsStatsUser.setLastPostDate(RandomTestUtil.nextDate());

		newBlogsStatsUser.setRatingsTotalEntries(RandomTestUtil.nextInt());

		newBlogsStatsUser.setRatingsTotalScore(RandomTestUtil.nextDouble());

		newBlogsStatsUser.setRatingsAverageScore(RandomTestUtil.nextDouble());

		_blogsStatsUsers.add(_persistence.update(newBlogsStatsUser));

		BlogsStatsUser existingBlogsStatsUser = _persistence.findByPrimaryKey(newBlogsStatsUser.getPrimaryKey());

		Assert.assertEquals(existingBlogsStatsUser.getStatsUserId(),
			newBlogsStatsUser.getStatsUserId());
		Assert.assertEquals(existingBlogsStatsUser.getGroupId(),
			newBlogsStatsUser.getGroupId());
		Assert.assertEquals(existingBlogsStatsUser.getCompanyId(),
			newBlogsStatsUser.getCompanyId());
		Assert.assertEquals(existingBlogsStatsUser.getUserId(),
			newBlogsStatsUser.getUserId());
		Assert.assertEquals(existingBlogsStatsUser.getEntryCount(),
			newBlogsStatsUser.getEntryCount());
		Assert.assertEquals(Time.getShortTimestamp(
				existingBlogsStatsUser.getLastPostDate()),
			Time.getShortTimestamp(newBlogsStatsUser.getLastPostDate()));
		Assert.assertEquals(existingBlogsStatsUser.getRatingsTotalEntries(),
			newBlogsStatsUser.getRatingsTotalEntries());
		AssertUtils.assertEquals(existingBlogsStatsUser.getRatingsTotalScore(),
			newBlogsStatsUser.getRatingsTotalScore());
		AssertUtils.assertEquals(existingBlogsStatsUser.getRatingsAverageScore(),
			newBlogsStatsUser.getRatingsAverageScore());
	}

	@Test
	public void testCountByGroupId() throws Exception {
		_persistence.countByGroupId(RandomTestUtil.nextLong());

		_persistence.countByGroupId(0L);
	}

	@Test
	public void testCountByUserId() throws Exception {
		_persistence.countByUserId(RandomTestUtil.nextLong());

		_persistence.countByUserId(0L);
	}

	@Test
	public void testCountByG_U() throws Exception {
		_persistence.countByG_U(RandomTestUtil.nextLong(),
			RandomTestUtil.nextLong());

		_persistence.countByG_U(0L, 0L);
	}

	@Test
	public void testCountByG_NotE() throws Exception {
		_persistence.countByG_NotE(RandomTestUtil.nextLong(),
			RandomTestUtil.nextInt());

		_persistence.countByG_NotE(0L, 0);
	}

	@Test
	public void testCountByC_NotE() throws Exception {
		_persistence.countByC_NotE(RandomTestUtil.nextLong(),
			RandomTestUtil.nextInt());

		_persistence.countByC_NotE(0L, 0);
	}

	@Test
	public void testCountByU_L() throws Exception {
		_persistence.countByU_L(RandomTestUtil.nextLong(),
			RandomTestUtil.nextDate());

		_persistence.countByU_L(0L, RandomTestUtil.nextDate());
	}

	@Test
	public void testFindByPrimaryKeyExisting() throws Exception {
		BlogsStatsUser newBlogsStatsUser = addBlogsStatsUser();

		BlogsStatsUser existingBlogsStatsUser = _persistence.findByPrimaryKey(newBlogsStatsUser.getPrimaryKey());

		Assert.assertEquals(existingBlogsStatsUser, newBlogsStatsUser);
	}

	@Test(expected = NoSuchStatsUserException.class)
	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		_persistence.findByPrimaryKey(pk);
	}

	@Test
	public void testFindAll() throws Exception {
		_persistence.findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			getOrderByComparator());
	}

	protected OrderByComparator<BlogsStatsUser> getOrderByComparator() {
		return OrderByComparatorFactoryUtil.create("BlogsStatsUser",
			"statsUserId", true, "groupId", true, "companyId", true, "userId",
			true, "entryCount", true, "lastPostDate", true,
			"ratingsTotalEntries", true, "ratingsTotalScore", true,
			"ratingsAverageScore", true);
	}

	@Test
	public void testFetchByPrimaryKeyExisting() throws Exception {
		BlogsStatsUser newBlogsStatsUser = addBlogsStatsUser();

		BlogsStatsUser existingBlogsStatsUser = _persistence.fetchByPrimaryKey(newBlogsStatsUser.getPrimaryKey());

		Assert.assertEquals(existingBlogsStatsUser, newBlogsStatsUser);
	}

	@Test
	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		BlogsStatsUser missingBlogsStatsUser = _persistence.fetchByPrimaryKey(pk);

		Assert.assertNull(missingBlogsStatsUser);
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereAllPrimaryKeysExist()
		throws Exception {
		BlogsStatsUser newBlogsStatsUser1 = addBlogsStatsUser();
		BlogsStatsUser newBlogsStatsUser2 = addBlogsStatsUser();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newBlogsStatsUser1.getPrimaryKey());
		primaryKeys.add(newBlogsStatsUser2.getPrimaryKey());

		Map<Serializable, BlogsStatsUser> blogsStatsUsers = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(2, blogsStatsUsers.size());
		Assert.assertEquals(newBlogsStatsUser1,
			blogsStatsUsers.get(newBlogsStatsUser1.getPrimaryKey()));
		Assert.assertEquals(newBlogsStatsUser2,
			blogsStatsUsers.get(newBlogsStatsUser2.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereNoPrimaryKeysExist()
		throws Exception {
		long pk1 = RandomTestUtil.nextLong();

		long pk2 = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(pk1);
		primaryKeys.add(pk2);

		Map<Serializable, BlogsStatsUser> blogsStatsUsers = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(blogsStatsUsers.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereSomePrimaryKeysExist()
		throws Exception {
		BlogsStatsUser newBlogsStatsUser = addBlogsStatsUser();

		long pk = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newBlogsStatsUser.getPrimaryKey());
		primaryKeys.add(pk);

		Map<Serializable, BlogsStatsUser> blogsStatsUsers = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, blogsStatsUsers.size());
		Assert.assertEquals(newBlogsStatsUser,
			blogsStatsUsers.get(newBlogsStatsUser.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithNoPrimaryKeys()
		throws Exception {
		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		Map<Serializable, BlogsStatsUser> blogsStatsUsers = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(blogsStatsUsers.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithOnePrimaryKey()
		throws Exception {
		BlogsStatsUser newBlogsStatsUser = addBlogsStatsUser();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newBlogsStatsUser.getPrimaryKey());

		Map<Serializable, BlogsStatsUser> blogsStatsUsers = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, blogsStatsUsers.size());
		Assert.assertEquals(newBlogsStatsUser,
			blogsStatsUsers.get(newBlogsStatsUser.getPrimaryKey()));
	}

	@Test
	public void testActionableDynamicQuery() throws Exception {
		final IntegerWrapper count = new IntegerWrapper();

		ActionableDynamicQuery actionableDynamicQuery = BlogsStatsUserLocalServiceUtil.getActionableDynamicQuery();

		actionableDynamicQuery.setPerformActionMethod(new ActionableDynamicQuery.PerformActionMethod<BlogsStatsUser>() {
				@Override
				public void performAction(BlogsStatsUser blogsStatsUser) {
					Assert.assertNotNull(blogsStatsUser);

					count.increment();
				}
			});

		actionableDynamicQuery.performActions();

		Assert.assertEquals(count.getValue(), _persistence.countAll());
	}

	@Test
	public void testDynamicQueryByPrimaryKeyExisting()
		throws Exception {
		BlogsStatsUser newBlogsStatsUser = addBlogsStatsUser();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(BlogsStatsUser.class,
				_dynamicQueryClassLoader);

		dynamicQuery.add(RestrictionsFactoryUtil.eq("statsUserId",
				newBlogsStatsUser.getStatsUserId()));

		List<BlogsStatsUser> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		BlogsStatsUser existingBlogsStatsUser = result.get(0);

		Assert.assertEquals(existingBlogsStatsUser, newBlogsStatsUser);
	}

	@Test
	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(BlogsStatsUser.class,
				_dynamicQueryClassLoader);

		dynamicQuery.add(RestrictionsFactoryUtil.eq("statsUserId",
				RandomTestUtil.nextLong()));

		List<BlogsStatsUser> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testDynamicQueryByProjectionExisting()
		throws Exception {
		BlogsStatsUser newBlogsStatsUser = addBlogsStatsUser();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(BlogsStatsUser.class,
				_dynamicQueryClassLoader);

		dynamicQuery.setProjection(ProjectionFactoryUtil.property("statsUserId"));

		Object newStatsUserId = newBlogsStatsUser.getStatsUserId();

		dynamicQuery.add(RestrictionsFactoryUtil.in("statsUserId",
				new Object[] { newStatsUserId }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		Object existingStatsUserId = result.get(0);

		Assert.assertEquals(existingStatsUserId, newStatsUserId);
	}

	@Test
	public void testDynamicQueryByProjectionMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(BlogsStatsUser.class,
				_dynamicQueryClassLoader);

		dynamicQuery.setProjection(ProjectionFactoryUtil.property("statsUserId"));

		dynamicQuery.add(RestrictionsFactoryUtil.in("statsUserId",
				new Object[] { RandomTestUtil.nextLong() }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testResetOriginalValues() throws Exception {
		BlogsStatsUser newBlogsStatsUser = addBlogsStatsUser();

		_persistence.clearCache();

		BlogsStatsUser existingBlogsStatsUser = _persistence.findByPrimaryKey(newBlogsStatsUser.getPrimaryKey());

		Assert.assertEquals(Long.valueOf(existingBlogsStatsUser.getGroupId()),
			ReflectionTestUtil.<Long>invoke(existingBlogsStatsUser,
				"getOriginalGroupId", new Class<?>[0]));
		Assert.assertEquals(Long.valueOf(existingBlogsStatsUser.getUserId()),
			ReflectionTestUtil.<Long>invoke(existingBlogsStatsUser,
				"getOriginalUserId", new Class<?>[0]));
	}

	protected BlogsStatsUser addBlogsStatsUser() throws Exception {
		long pk = RandomTestUtil.nextLong();

		BlogsStatsUser blogsStatsUser = _persistence.create(pk);

		blogsStatsUser.setGroupId(RandomTestUtil.nextLong());

		blogsStatsUser.setCompanyId(RandomTestUtil.nextLong());

		blogsStatsUser.setUserId(RandomTestUtil.nextLong());

		blogsStatsUser.setEntryCount(RandomTestUtil.nextInt());

		blogsStatsUser.setLastPostDate(RandomTestUtil.nextDate());

		blogsStatsUser.setRatingsTotalEntries(RandomTestUtil.nextInt());

		blogsStatsUser.setRatingsTotalScore(RandomTestUtil.nextDouble());

		blogsStatsUser.setRatingsAverageScore(RandomTestUtil.nextDouble());

		_blogsStatsUsers.add(_persistence.update(blogsStatsUser));

		return blogsStatsUser;
	}

	private List<BlogsStatsUser> _blogsStatsUsers = new ArrayList<BlogsStatsUser>();
	private BlogsStatsUserPersistence _persistence;
	private ClassLoader _dynamicQueryClassLoader;
}