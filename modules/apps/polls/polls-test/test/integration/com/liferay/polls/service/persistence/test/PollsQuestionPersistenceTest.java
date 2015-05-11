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

import com.liferay.polls.exception.NoSuchQuestionException;
import com.liferay.polls.model.PollsQuestion;
import com.liferay.polls.service.PollsQuestionLocalServiceUtil;
import com.liferay.polls.service.persistence.PollsQuestionPersistence;
import com.liferay.polls.service.persistence.PollsQuestionUtil;

import com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQueryFactoryUtil;
import com.liferay.portal.kernel.dao.orm.ProjectionFactoryUtil;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.RestrictionsFactoryUtil;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.TransactionalTestRule;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.transaction.Propagation;
import com.liferay.portal.kernel.util.IntegerWrapper;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.OrderByComparatorFactoryUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Time;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PersistenceTestRule;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
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
public class PollsQuestionPersistenceTest {
	@Rule
	public final AggregateTestRule aggregateTestRule = new AggregateTestRule(new LiferayIntegrationTestRule(),
			PersistenceTestRule.INSTANCE,
			new TransactionalTestRule(Propagation.REQUIRED));

	@Before
	public void setUp() {
		_persistence = PollsQuestionUtil.getPersistence();

		Class<?> clazz = _persistence.getClass();

		_dynamicQueryClassLoader = clazz.getClassLoader();
	}

	@After
	public void tearDown() throws Exception {
		Iterator<PollsQuestion> iterator = _pollsQuestions.iterator();

		while (iterator.hasNext()) {
			_persistence.remove(iterator.next());

			iterator.remove();
		}
	}

	@Test
	public void testCreate() throws Exception {
		long pk = RandomTestUtil.nextLong();

		PollsQuestion pollsQuestion = _persistence.create(pk);

		Assert.assertNotNull(pollsQuestion);

		Assert.assertEquals(pollsQuestion.getPrimaryKey(), pk);
	}

	@Test
	public void testRemove() throws Exception {
		PollsQuestion newPollsQuestion = addPollsQuestion();

		_persistence.remove(newPollsQuestion);

		PollsQuestion existingPollsQuestion = _persistence.fetchByPrimaryKey(newPollsQuestion.getPrimaryKey());

		Assert.assertNull(existingPollsQuestion);
	}

	@Test
	public void testUpdateNew() throws Exception {
		addPollsQuestion();
	}

	@Test
	public void testUpdateExisting() throws Exception {
		long pk = RandomTestUtil.nextLong();

		PollsQuestion newPollsQuestion = _persistence.create(pk);

		newPollsQuestion.setUuid(RandomTestUtil.randomString());

		newPollsQuestion.setGroupId(RandomTestUtil.nextLong());

		newPollsQuestion.setCompanyId(RandomTestUtil.nextLong());

		newPollsQuestion.setUserId(RandomTestUtil.nextLong());

		newPollsQuestion.setUserName(RandomTestUtil.randomString());

		newPollsQuestion.setCreateDate(RandomTestUtil.nextDate());

		newPollsQuestion.setModifiedDate(RandomTestUtil.nextDate());

		newPollsQuestion.setTitle(RandomTestUtil.randomString());

		newPollsQuestion.setDescription(RandomTestUtil.randomString());

		newPollsQuestion.setExpirationDate(RandomTestUtil.nextDate());

		newPollsQuestion.setLastVoteDate(RandomTestUtil.nextDate());

		_pollsQuestions.add(_persistence.update(newPollsQuestion));

		PollsQuestion existingPollsQuestion = _persistence.findByPrimaryKey(newPollsQuestion.getPrimaryKey());

		Assert.assertEquals(existingPollsQuestion.getUuid(),
			newPollsQuestion.getUuid());
		Assert.assertEquals(existingPollsQuestion.getQuestionId(),
			newPollsQuestion.getQuestionId());
		Assert.assertEquals(existingPollsQuestion.getGroupId(),
			newPollsQuestion.getGroupId());
		Assert.assertEquals(existingPollsQuestion.getCompanyId(),
			newPollsQuestion.getCompanyId());
		Assert.assertEquals(existingPollsQuestion.getUserId(),
			newPollsQuestion.getUserId());
		Assert.assertEquals(existingPollsQuestion.getUserName(),
			newPollsQuestion.getUserName());
		Assert.assertEquals(Time.getShortTimestamp(
				existingPollsQuestion.getCreateDate()),
			Time.getShortTimestamp(newPollsQuestion.getCreateDate()));
		Assert.assertEquals(Time.getShortTimestamp(
				existingPollsQuestion.getModifiedDate()),
			Time.getShortTimestamp(newPollsQuestion.getModifiedDate()));
		Assert.assertEquals(existingPollsQuestion.getTitle(),
			newPollsQuestion.getTitle());
		Assert.assertEquals(existingPollsQuestion.getDescription(),
			newPollsQuestion.getDescription());
		Assert.assertEquals(Time.getShortTimestamp(
				existingPollsQuestion.getExpirationDate()),
			Time.getShortTimestamp(newPollsQuestion.getExpirationDate()));
		Assert.assertEquals(Time.getShortTimestamp(
				existingPollsQuestion.getLastVoteDate()),
			Time.getShortTimestamp(newPollsQuestion.getLastVoteDate()));
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
	public void testCountByGroupId() throws Exception {
		_persistence.countByGroupId(RandomTestUtil.nextLong());

		_persistence.countByGroupId(0L);
	}

	@Test
	public void testFindByPrimaryKeyExisting() throws Exception {
		PollsQuestion newPollsQuestion = addPollsQuestion();

		PollsQuestion existingPollsQuestion = _persistence.findByPrimaryKey(newPollsQuestion.getPrimaryKey());

		Assert.assertEquals(existingPollsQuestion, newPollsQuestion);
	}

	@Test(expected = NoSuchQuestionException.class)
	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		_persistence.findByPrimaryKey(pk);
	}

	@Test
	public void testFindAll() throws Exception {
		_persistence.findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			getOrderByComparator());
	}

	@Test
	public void testFilterFindByGroupId() throws Exception {
		_persistence.filterFindByGroupId(0, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, getOrderByComparator());
	}

	protected OrderByComparator<PollsQuestion> getOrderByComparator() {
		return OrderByComparatorFactoryUtil.create("PollsQuestion", "uuid",
			true, "questionId", true, "groupId", true, "companyId", true,
			"userId", true, "userName", true, "createDate", true,
			"modifiedDate", true, "title", true, "description", true,
			"expirationDate", true, "lastVoteDate", true);
	}

	@Test
	public void testFetchByPrimaryKeyExisting() throws Exception {
		PollsQuestion newPollsQuestion = addPollsQuestion();

		PollsQuestion existingPollsQuestion = _persistence.fetchByPrimaryKey(newPollsQuestion.getPrimaryKey());

		Assert.assertEquals(existingPollsQuestion, newPollsQuestion);
	}

	@Test
	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		PollsQuestion missingPollsQuestion = _persistence.fetchByPrimaryKey(pk);

		Assert.assertNull(missingPollsQuestion);
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereAllPrimaryKeysExist()
		throws Exception {
		PollsQuestion newPollsQuestion1 = addPollsQuestion();
		PollsQuestion newPollsQuestion2 = addPollsQuestion();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newPollsQuestion1.getPrimaryKey());
		primaryKeys.add(newPollsQuestion2.getPrimaryKey());

		Map<Serializable, PollsQuestion> pollsQuestions = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(2, pollsQuestions.size());
		Assert.assertEquals(newPollsQuestion1,
			pollsQuestions.get(newPollsQuestion1.getPrimaryKey()));
		Assert.assertEquals(newPollsQuestion2,
			pollsQuestions.get(newPollsQuestion2.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereNoPrimaryKeysExist()
		throws Exception {
		long pk1 = RandomTestUtil.nextLong();

		long pk2 = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(pk1);
		primaryKeys.add(pk2);

		Map<Serializable, PollsQuestion> pollsQuestions = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(pollsQuestions.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereSomePrimaryKeysExist()
		throws Exception {
		PollsQuestion newPollsQuestion = addPollsQuestion();

		long pk = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newPollsQuestion.getPrimaryKey());
		primaryKeys.add(pk);

		Map<Serializable, PollsQuestion> pollsQuestions = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, pollsQuestions.size());
		Assert.assertEquals(newPollsQuestion,
			pollsQuestions.get(newPollsQuestion.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithNoPrimaryKeys()
		throws Exception {
		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		Map<Serializable, PollsQuestion> pollsQuestions = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(pollsQuestions.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithOnePrimaryKey()
		throws Exception {
		PollsQuestion newPollsQuestion = addPollsQuestion();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newPollsQuestion.getPrimaryKey());

		Map<Serializable, PollsQuestion> pollsQuestions = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, pollsQuestions.size());
		Assert.assertEquals(newPollsQuestion,
			pollsQuestions.get(newPollsQuestion.getPrimaryKey()));
	}

	@Test
	public void testActionableDynamicQuery() throws Exception {
		final IntegerWrapper count = new IntegerWrapper();

		ActionableDynamicQuery actionableDynamicQuery = PollsQuestionLocalServiceUtil.getActionableDynamicQuery();

		actionableDynamicQuery.setPerformActionMethod(new ActionableDynamicQuery.PerformActionMethod() {
				@Override
				public void performAction(Object object) {
					PollsQuestion pollsQuestion = (PollsQuestion)object;

					Assert.assertNotNull(pollsQuestion);

					count.increment();
				}
			});

		actionableDynamicQuery.performActions();

		Assert.assertEquals(count.getValue(), _persistence.countAll());
	}

	@Test
	public void testDynamicQueryByPrimaryKeyExisting()
		throws Exception {
		PollsQuestion newPollsQuestion = addPollsQuestion();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(PollsQuestion.class,
				_dynamicQueryClassLoader);

		dynamicQuery.add(RestrictionsFactoryUtil.eq("questionId",
				newPollsQuestion.getQuestionId()));

		List<PollsQuestion> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		PollsQuestion existingPollsQuestion = result.get(0);

		Assert.assertEquals(existingPollsQuestion, newPollsQuestion);
	}

	@Test
	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(PollsQuestion.class,
				_dynamicQueryClassLoader);

		dynamicQuery.add(RestrictionsFactoryUtil.eq("questionId",
				RandomTestUtil.nextLong()));

		List<PollsQuestion> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testDynamicQueryByProjectionExisting()
		throws Exception {
		PollsQuestion newPollsQuestion = addPollsQuestion();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(PollsQuestion.class,
				_dynamicQueryClassLoader);

		dynamicQuery.setProjection(ProjectionFactoryUtil.property("questionId"));

		Object newQuestionId = newPollsQuestion.getQuestionId();

		dynamicQuery.add(RestrictionsFactoryUtil.in("questionId",
				new Object[] { newQuestionId }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		Object existingQuestionId = result.get(0);

		Assert.assertEquals(existingQuestionId, newQuestionId);
	}

	@Test
	public void testDynamicQueryByProjectionMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(PollsQuestion.class,
				_dynamicQueryClassLoader);

		dynamicQuery.setProjection(ProjectionFactoryUtil.property("questionId"));

		dynamicQuery.add(RestrictionsFactoryUtil.in("questionId",
				new Object[] { RandomTestUtil.nextLong() }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testResetOriginalValues() throws Exception {
		PollsQuestion newPollsQuestion = addPollsQuestion();

		_persistence.clearCache();

		PollsQuestion existingPollsQuestion = _persistence.findByPrimaryKey(newPollsQuestion.getPrimaryKey());

		Assert.assertTrue(Validator.equals(existingPollsQuestion.getUuid(),
				ReflectionTestUtil.invoke(existingPollsQuestion,
					"getOriginalUuid", new Class<?>[0])));
		Assert.assertEquals(existingPollsQuestion.getGroupId(),
			ReflectionTestUtil.invoke(existingPollsQuestion,
				"getOriginalGroupId", new Class<?>[0]));
	}

	protected PollsQuestion addPollsQuestion() throws Exception {
		long pk = RandomTestUtil.nextLong();

		PollsQuestion pollsQuestion = _persistence.create(pk);

		pollsQuestion.setUuid(RandomTestUtil.randomString());

		pollsQuestion.setGroupId(RandomTestUtil.nextLong());

		pollsQuestion.setCompanyId(RandomTestUtil.nextLong());

		pollsQuestion.setUserId(RandomTestUtil.nextLong());

		pollsQuestion.setUserName(RandomTestUtil.randomString());

		pollsQuestion.setCreateDate(RandomTestUtil.nextDate());

		pollsQuestion.setModifiedDate(RandomTestUtil.nextDate());

		pollsQuestion.setTitle(RandomTestUtil.randomString());

		pollsQuestion.setDescription(RandomTestUtil.randomString());

		pollsQuestion.setExpirationDate(RandomTestUtil.nextDate());

		pollsQuestion.setLastVoteDate(RandomTestUtil.nextDate());

		_pollsQuestions.add(_persistence.update(pollsQuestion));

		return pollsQuestion;
	}

	private List<PollsQuestion> _pollsQuestions = new ArrayList<PollsQuestion>();
	private PollsQuestionPersistence _persistence;
	private ClassLoader _dynamicQueryClassLoader;
}