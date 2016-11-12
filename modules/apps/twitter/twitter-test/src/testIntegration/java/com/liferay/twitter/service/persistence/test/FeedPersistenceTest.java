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

package com.liferay.twitter.service.persistence.test;

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

import com.liferay.twitter.exception.NoSuchFeedException;
import com.liferay.twitter.model.Feed;
import com.liferay.twitter.service.FeedLocalServiceUtil;
import com.liferay.twitter.service.persistence.FeedPersistence;
import com.liferay.twitter.service.persistence.FeedUtil;

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
public class FeedPersistenceTest {
	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule = new AggregateTestRule(new LiferayIntegrationTestRule(),
			PersistenceTestRule.INSTANCE,
			new TransactionalTestRule(Propagation.REQUIRED,
				"com.liferay.twitter.service"));

	@Before
	public void setUp() {
		_persistence = FeedUtil.getPersistence();

		Class<?> clazz = _persistence.getClass();

		_dynamicQueryClassLoader = clazz.getClassLoader();
	}

	@After
	public void tearDown() throws Exception {
		Iterator<Feed> iterator = _feeds.iterator();

		while (iterator.hasNext()) {
			_persistence.remove(iterator.next());

			iterator.remove();
		}
	}

	@Test
	public void testCreate() throws Exception {
		long pk = RandomTestUtil.nextLong();

		Feed feed = _persistence.create(pk);

		Assert.assertNotNull(feed);

		Assert.assertEquals(feed.getPrimaryKey(), pk);
	}

	@Test
	public void testRemove() throws Exception {
		Feed newFeed = addFeed();

		_persistence.remove(newFeed);

		Feed existingFeed = _persistence.fetchByPrimaryKey(newFeed.getPrimaryKey());

		Assert.assertNull(existingFeed);
	}

	@Test
	public void testUpdateNew() throws Exception {
		addFeed();
	}

	@Test
	public void testUpdateExisting() throws Exception {
		long pk = RandomTestUtil.nextLong();

		Feed newFeed = _persistence.create(pk);

		newFeed.setCompanyId(RandomTestUtil.nextLong());

		newFeed.setUserId(RandomTestUtil.nextLong());

		newFeed.setUserName(RandomTestUtil.randomString());

		newFeed.setCreateDate(RandomTestUtil.nextDate());

		newFeed.setModifiedDate(RandomTestUtil.nextDate());

		newFeed.setTwitterUserId(RandomTestUtil.nextLong());

		newFeed.setTwitterScreenName(RandomTestUtil.randomString());

		newFeed.setLastStatusId(RandomTestUtil.nextLong());

		_feeds.add(_persistence.update(newFeed));

		Feed existingFeed = _persistence.findByPrimaryKey(newFeed.getPrimaryKey());

		Assert.assertEquals(existingFeed.getFeedId(), newFeed.getFeedId());
		Assert.assertEquals(existingFeed.getCompanyId(), newFeed.getCompanyId());
		Assert.assertEquals(existingFeed.getUserId(), newFeed.getUserId());
		Assert.assertEquals(existingFeed.getUserName(), newFeed.getUserName());
		Assert.assertEquals(Time.getShortTimestamp(existingFeed.getCreateDate()),
			Time.getShortTimestamp(newFeed.getCreateDate()));
		Assert.assertEquals(Time.getShortTimestamp(
				existingFeed.getModifiedDate()),
			Time.getShortTimestamp(newFeed.getModifiedDate()));
		Assert.assertEquals(existingFeed.getTwitterUserId(),
			newFeed.getTwitterUserId());
		Assert.assertEquals(existingFeed.getTwitterScreenName(),
			newFeed.getTwitterScreenName());
		Assert.assertEquals(existingFeed.getLastStatusId(),
			newFeed.getLastStatusId());
	}

	@Test
	public void testCountByU_TSN() throws Exception {
		_persistence.countByU_TSN(RandomTestUtil.nextLong(), StringPool.BLANK);

		_persistence.countByU_TSN(0L, StringPool.NULL);

		_persistence.countByU_TSN(0L, (String)null);
	}

	@Test
	public void testFindByPrimaryKeyExisting() throws Exception {
		Feed newFeed = addFeed();

		Feed existingFeed = _persistence.findByPrimaryKey(newFeed.getPrimaryKey());

		Assert.assertEquals(existingFeed, newFeed);
	}

	@Test(expected = NoSuchFeedException.class)
	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		_persistence.findByPrimaryKey(pk);
	}

	@Test
	public void testFindAll() throws Exception {
		_persistence.findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			getOrderByComparator());
	}

	protected OrderByComparator<Feed> getOrderByComparator() {
		return OrderByComparatorFactoryUtil.create("Twitter_Feed", "feedId",
			true, "companyId", true, "userId", true, "userName", true,
			"createDate", true, "modifiedDate", true, "twitterUserId", true,
			"twitterScreenName", true, "lastStatusId", true);
	}

	@Test
	public void testFetchByPrimaryKeyExisting() throws Exception {
		Feed newFeed = addFeed();

		Feed existingFeed = _persistence.fetchByPrimaryKey(newFeed.getPrimaryKey());

		Assert.assertEquals(existingFeed, newFeed);
	}

	@Test
	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		Feed missingFeed = _persistence.fetchByPrimaryKey(pk);

		Assert.assertNull(missingFeed);
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereAllPrimaryKeysExist()
		throws Exception {
		Feed newFeed1 = addFeed();
		Feed newFeed2 = addFeed();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newFeed1.getPrimaryKey());
		primaryKeys.add(newFeed2.getPrimaryKey());

		Map<Serializable, Feed> feeds = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(2, feeds.size());
		Assert.assertEquals(newFeed1, feeds.get(newFeed1.getPrimaryKey()));
		Assert.assertEquals(newFeed2, feeds.get(newFeed2.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereNoPrimaryKeysExist()
		throws Exception {
		long pk1 = RandomTestUtil.nextLong();

		long pk2 = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(pk1);
		primaryKeys.add(pk2);

		Map<Serializable, Feed> feeds = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(feeds.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereSomePrimaryKeysExist()
		throws Exception {
		Feed newFeed = addFeed();

		long pk = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newFeed.getPrimaryKey());
		primaryKeys.add(pk);

		Map<Serializable, Feed> feeds = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, feeds.size());
		Assert.assertEquals(newFeed, feeds.get(newFeed.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithNoPrimaryKeys()
		throws Exception {
		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		Map<Serializable, Feed> feeds = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(feeds.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithOnePrimaryKey()
		throws Exception {
		Feed newFeed = addFeed();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newFeed.getPrimaryKey());

		Map<Serializable, Feed> feeds = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, feeds.size());
		Assert.assertEquals(newFeed, feeds.get(newFeed.getPrimaryKey()));
	}

	@Test
	public void testActionableDynamicQuery() throws Exception {
		final IntegerWrapper count = new IntegerWrapper();

		ActionableDynamicQuery actionableDynamicQuery = FeedLocalServiceUtil.getActionableDynamicQuery();

		actionableDynamicQuery.setPerformActionMethod(new ActionableDynamicQuery.PerformActionMethod<Feed>() {
				@Override
				public void performAction(Feed feed) {
					Assert.assertNotNull(feed);

					count.increment();
				}
			});

		actionableDynamicQuery.performActions();

		Assert.assertEquals(count.getValue(), _persistence.countAll());
	}

	@Test
	public void testDynamicQueryByPrimaryKeyExisting()
		throws Exception {
		Feed newFeed = addFeed();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(Feed.class,
				_dynamicQueryClassLoader);

		dynamicQuery.add(RestrictionsFactoryUtil.eq("feedId",
				newFeed.getFeedId()));

		List<Feed> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		Feed existingFeed = result.get(0);

		Assert.assertEquals(existingFeed, newFeed);
	}

	@Test
	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(Feed.class,
				_dynamicQueryClassLoader);

		dynamicQuery.add(RestrictionsFactoryUtil.eq("feedId",
				RandomTestUtil.nextLong()));

		List<Feed> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testDynamicQueryByProjectionExisting()
		throws Exception {
		Feed newFeed = addFeed();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(Feed.class,
				_dynamicQueryClassLoader);

		dynamicQuery.setProjection(ProjectionFactoryUtil.property("feedId"));

		Object newFeedId = newFeed.getFeedId();

		dynamicQuery.add(RestrictionsFactoryUtil.in("feedId",
				new Object[] { newFeedId }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		Object existingFeedId = result.get(0);

		Assert.assertEquals(existingFeedId, newFeedId);
	}

	@Test
	public void testDynamicQueryByProjectionMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(Feed.class,
				_dynamicQueryClassLoader);

		dynamicQuery.setProjection(ProjectionFactoryUtil.property("feedId"));

		dynamicQuery.add(RestrictionsFactoryUtil.in("feedId",
				new Object[] { RandomTestUtil.nextLong() }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testResetOriginalValues() throws Exception {
		Feed newFeed = addFeed();

		_persistence.clearCache();

		Feed existingFeed = _persistence.findByPrimaryKey(newFeed.getPrimaryKey());

		Assert.assertEquals(Long.valueOf(existingFeed.getUserId()),
			ReflectionTestUtil.<Long>invoke(existingFeed, "getOriginalUserId",
				new Class<?>[0]));
		Assert.assertTrue(Objects.equals(existingFeed.getTwitterScreenName(),
				ReflectionTestUtil.invoke(existingFeed,
					"getOriginalTwitterScreenName", new Class<?>[0])));
	}

	protected Feed addFeed() throws Exception {
		long pk = RandomTestUtil.nextLong();

		Feed feed = _persistence.create(pk);

		feed.setCompanyId(RandomTestUtil.nextLong());

		feed.setUserId(RandomTestUtil.nextLong());

		feed.setUserName(RandomTestUtil.randomString());

		feed.setCreateDate(RandomTestUtil.nextDate());

		feed.setModifiedDate(RandomTestUtil.nextDate());

		feed.setTwitterUserId(RandomTestUtil.nextLong());

		feed.setTwitterScreenName(RandomTestUtil.randomString());

		feed.setLastStatusId(RandomTestUtil.nextLong());

		_feeds.add(_persistence.update(feed));

		return feed;
	}

	private List<Feed> _feeds = new ArrayList<Feed>();
	private FeedPersistence _persistence;
	private ClassLoader _dynamicQueryClassLoader;
}