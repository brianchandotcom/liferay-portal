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

package com.liferay.polls.service.persistence.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.polls.exception.NoSuchVoteException;
import com.liferay.polls.model.PollsVote;
import com.liferay.polls.service.PollsVoteLocalServiceUtil;
import com.liferay.polls.service.persistence.PollsVotePersistence;
import com.liferay.polls.service.persistence.PollsVoteUtil;
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
public class PollsVotePersistenceTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(), PersistenceTestRule.INSTANCE,
			new TransactionalTestRule(
				Propagation.REQUIRED, "com.liferay.polls.service"));

	@Before
	public void setUp() {
		_persistence = PollsVoteUtil.getPersistence();

		Class<?> clazz = _persistence.getClass();

		_dynamicQueryClassLoader = clazz.getClassLoader();
	}

	@After
	public void tearDown() throws Exception {
		Iterator<PollsVote> iterator = _pollsVotes.iterator();

		while (iterator.hasNext()) {
			_persistence.remove(iterator.next());

			iterator.remove();
		}
	}

	@Test
	public void testCreate() throws Exception {
		long pk = RandomTestUtil.nextLong();

		PollsVote pollsVote = _persistence.create(pk);

		Assert.assertNotNull(pollsVote);

		Assert.assertEquals(pollsVote.getPrimaryKey(), pk);
	}

	@Test
	public void testRemove() throws Exception {
		PollsVote newPollsVote = addPollsVote();

		_persistence.remove(newPollsVote);

		PollsVote existingPollsVote = _persistence.fetchByPrimaryKey(
			newPollsVote.getPrimaryKey());

		Assert.assertNull(existingPollsVote);
	}

	@Test
	public void testUpdateNew() throws Exception {
		addPollsVote();
	}

	@Test
	public void testUpdateExisting() throws Exception {
		long pk = RandomTestUtil.nextLong();

		PollsVote newPollsVote = _persistence.create(pk);

		newPollsVote.setMvccVersion(RandomTestUtil.nextLong());

		newPollsVote.setUuid(RandomTestUtil.randomString());

		newPollsVote.setGroupId(RandomTestUtil.nextLong());

		newPollsVote.setCompanyId(RandomTestUtil.nextLong());

		newPollsVote.setUserId(RandomTestUtil.nextLong());

		newPollsVote.setUserName(RandomTestUtil.randomString());

		newPollsVote.setCreateDate(RandomTestUtil.nextDate());

		newPollsVote.setModifiedDate(RandomTestUtil.nextDate());

		newPollsVote.setQuestionId(RandomTestUtil.nextLong());

		newPollsVote.setChoiceId(RandomTestUtil.nextLong());

		newPollsVote.setLastPublishDate(RandomTestUtil.nextDate());

		newPollsVote.setVoteDate(RandomTestUtil.nextDate());

		_pollsVotes.add(_persistence.update(newPollsVote));

		PollsVote existingPollsVote = _persistence.findByPrimaryKey(
			newPollsVote.getPrimaryKey());

		Assert.assertEquals(
			existingPollsVote.getMvccVersion(), newPollsVote.getMvccVersion());
		Assert.assertEquals(
			existingPollsVote.getUuid(), newPollsVote.getUuid());
		Assert.assertEquals(
			existingPollsVote.getVoteId(), newPollsVote.getVoteId());
		Assert.assertEquals(
			existingPollsVote.getGroupId(), newPollsVote.getGroupId());
		Assert.assertEquals(
			existingPollsVote.getCompanyId(), newPollsVote.getCompanyId());
		Assert.assertEquals(
			existingPollsVote.getUserId(), newPollsVote.getUserId());
		Assert.assertEquals(
			existingPollsVote.getUserName(), newPollsVote.getUserName());
		Assert.assertEquals(
			Time.getShortTimestamp(existingPollsVote.getCreateDate()),
			Time.getShortTimestamp(newPollsVote.getCreateDate()));
		Assert.assertEquals(
			Time.getShortTimestamp(existingPollsVote.getModifiedDate()),
			Time.getShortTimestamp(newPollsVote.getModifiedDate()));
		Assert.assertEquals(
			existingPollsVote.getQuestionId(), newPollsVote.getQuestionId());
		Assert.assertEquals(
			existingPollsVote.getChoiceId(), newPollsVote.getChoiceId());
		Assert.assertEquals(
			Time.getShortTimestamp(existingPollsVote.getLastPublishDate()),
			Time.getShortTimestamp(newPollsVote.getLastPublishDate()));
		Assert.assertEquals(
			Time.getShortTimestamp(existingPollsVote.getVoteDate()),
			Time.getShortTimestamp(newPollsVote.getVoteDate()));
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
	public void testCountByQuestionId() throws Exception {
		_persistence.countByQuestionId(RandomTestUtil.nextLong());

		_persistence.countByQuestionId(0L);
	}

	@Test
	public void testCountByChoiceId() throws Exception {
		_persistence.countByChoiceId(RandomTestUtil.nextLong());

		_persistence.countByChoiceId(0L);
	}

	@Test
	public void testCountByQ_U() throws Exception {
		_persistence.countByQ_U(
			RandomTestUtil.nextLong(), RandomTestUtil.nextLong());

		_persistence.countByQ_U(0L, 0L);
	}

	@Test
	public void testFindByPrimaryKeyExisting() throws Exception {
		PollsVote newPollsVote = addPollsVote();

		PollsVote existingPollsVote = _persistence.findByPrimaryKey(
			newPollsVote.getPrimaryKey());

		Assert.assertEquals(existingPollsVote, newPollsVote);
	}

	@Test(expected = NoSuchVoteException.class)
	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		_persistence.findByPrimaryKey(pk);
	}

	@Test
	public void testFindAll() throws Exception {
		_persistence.findAll(
			QueryUtil.ALL_POS, QueryUtil.ALL_POS, getOrderByComparator());
	}

	protected OrderByComparator<PollsVote> getOrderByComparator() {
		return OrderByComparatorFactoryUtil.create(
			"PollsVote", "mvccVersion", true, "uuid", true, "voteId", true,
			"groupId", true, "companyId", true, "userId", true, "userName",
			true, "createDate", true, "modifiedDate", true, "questionId", true,
			"choiceId", true, "lastPublishDate", true, "voteDate", true);
	}

	@Test
	public void testFetchByPrimaryKeyExisting() throws Exception {
		PollsVote newPollsVote = addPollsVote();

		PollsVote existingPollsVote = _persistence.fetchByPrimaryKey(
			newPollsVote.getPrimaryKey());

		Assert.assertEquals(existingPollsVote, newPollsVote);
	}

	@Test
	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		PollsVote missingPollsVote = _persistence.fetchByPrimaryKey(pk);

		Assert.assertNull(missingPollsVote);
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereAllPrimaryKeysExist()
		throws Exception {

		PollsVote newPollsVote1 = addPollsVote();
		PollsVote newPollsVote2 = addPollsVote();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newPollsVote1.getPrimaryKey());
		primaryKeys.add(newPollsVote2.getPrimaryKey());

		Map<Serializable, PollsVote> pollsVotes =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(2, pollsVotes.size());
		Assert.assertEquals(
			newPollsVote1, pollsVotes.get(newPollsVote1.getPrimaryKey()));
		Assert.assertEquals(
			newPollsVote2, pollsVotes.get(newPollsVote2.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereNoPrimaryKeysExist()
		throws Exception {

		long pk1 = RandomTestUtil.nextLong();

		long pk2 = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(pk1);
		primaryKeys.add(pk2);

		Map<Serializable, PollsVote> pollsVotes =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(pollsVotes.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereSomePrimaryKeysExist()
		throws Exception {

		PollsVote newPollsVote = addPollsVote();

		long pk = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newPollsVote.getPrimaryKey());
		primaryKeys.add(pk);

		Map<Serializable, PollsVote> pollsVotes =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, pollsVotes.size());
		Assert.assertEquals(
			newPollsVote, pollsVotes.get(newPollsVote.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithNoPrimaryKeys() throws Exception {
		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		Map<Serializable, PollsVote> pollsVotes =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(pollsVotes.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithOnePrimaryKey() throws Exception {
		PollsVote newPollsVote = addPollsVote();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newPollsVote.getPrimaryKey());

		Map<Serializable, PollsVote> pollsVotes =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, pollsVotes.size());
		Assert.assertEquals(
			newPollsVote, pollsVotes.get(newPollsVote.getPrimaryKey()));
	}

	@Test
	public void testActionableDynamicQuery() throws Exception {
		final IntegerWrapper count = new IntegerWrapper();

		ActionableDynamicQuery actionableDynamicQuery =
			PollsVoteLocalServiceUtil.getActionableDynamicQuery();

		actionableDynamicQuery.setPerformActionMethod(
			new ActionableDynamicQuery.PerformActionMethod<PollsVote>() {

				@Override
				public void performAction(PollsVote pollsVote) {
					Assert.assertNotNull(pollsVote);

					count.increment();
				}

			});

		actionableDynamicQuery.performActions();

		Assert.assertEquals(count.getValue(), _persistence.countAll());
	}

	@Test
	public void testDynamicQueryByPrimaryKeyExisting() throws Exception {
		PollsVote newPollsVote = addPollsVote();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			PollsVote.class, _dynamicQueryClassLoader);

		dynamicQuery.add(
			RestrictionsFactoryUtil.eq("voteId", newPollsVote.getVoteId()));

		List<PollsVote> result = _persistence.findWithDynamicQuery(
			dynamicQuery);

		Assert.assertEquals(1, result.size());

		PollsVote existingPollsVote = result.get(0);

		Assert.assertEquals(existingPollsVote, newPollsVote);
	}

	@Test
	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			PollsVote.class, _dynamicQueryClassLoader);

		dynamicQuery.add(
			RestrictionsFactoryUtil.eq("voteId", RandomTestUtil.nextLong()));

		List<PollsVote> result = _persistence.findWithDynamicQuery(
			dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testDynamicQueryByProjectionExisting() throws Exception {
		PollsVote newPollsVote = addPollsVote();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			PollsVote.class, _dynamicQueryClassLoader);

		dynamicQuery.setProjection(ProjectionFactoryUtil.property("voteId"));

		Object newVoteId = newPollsVote.getVoteId();

		dynamicQuery.add(
			RestrictionsFactoryUtil.in("voteId", new Object[] {newVoteId}));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		Object existingVoteId = result.get(0);

		Assert.assertEquals(existingVoteId, newVoteId);
	}

	@Test
	public void testDynamicQueryByProjectionMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			PollsVote.class, _dynamicQueryClassLoader);

		dynamicQuery.setProjection(ProjectionFactoryUtil.property("voteId"));

		dynamicQuery.add(
			RestrictionsFactoryUtil.in(
				"voteId", new Object[] {RandomTestUtil.nextLong()}));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testResetOriginalValues() throws Exception {
		PollsVote newPollsVote = addPollsVote();

		_persistence.clearCache();

		PollsVote existingPollsVote = _persistence.findByPrimaryKey(
			newPollsVote.getPrimaryKey());

		Assert.assertTrue(
			Objects.equals(
				existingPollsVote.getUuid(),
				ReflectionTestUtil.invoke(
					existingPollsVote, "getOriginalUuid", new Class<?>[0])));
		Assert.assertEquals(
			Long.valueOf(existingPollsVote.getGroupId()),
			ReflectionTestUtil.<Long>invoke(
				existingPollsVote, "getOriginalGroupId", new Class<?>[0]));
	}

	protected PollsVote addPollsVote() throws Exception {
		long pk = RandomTestUtil.nextLong();

		PollsVote pollsVote = _persistence.create(pk);

		pollsVote.setMvccVersion(RandomTestUtil.nextLong());

		pollsVote.setUuid(RandomTestUtil.randomString());

		pollsVote.setGroupId(RandomTestUtil.nextLong());

		pollsVote.setCompanyId(RandomTestUtil.nextLong());

		pollsVote.setUserId(RandomTestUtil.nextLong());

		pollsVote.setUserName(RandomTestUtil.randomString());

		pollsVote.setCreateDate(RandomTestUtil.nextDate());

		pollsVote.setModifiedDate(RandomTestUtil.nextDate());

		pollsVote.setQuestionId(RandomTestUtil.nextLong());

		pollsVote.setChoiceId(RandomTestUtil.nextLong());

		pollsVote.setLastPublishDate(RandomTestUtil.nextDate());

		pollsVote.setVoteDate(RandomTestUtil.nextDate());

		_pollsVotes.add(_persistence.update(pollsVote));

		return pollsVote;
	}

	private List<PollsVote> _pollsVotes = new ArrayList<PollsVote>();
	private PollsVotePersistence _persistence;
	private ClassLoader _dynamicQueryClassLoader;

}