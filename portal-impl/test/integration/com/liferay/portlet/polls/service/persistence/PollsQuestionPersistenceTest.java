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

package com.liferay.portlet.polls.service.persistence;

import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQueryFactoryUtil;
import com.liferay.portal.kernel.dao.orm.ProjectionFactoryUtil;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.RestrictionsFactoryUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.test.ExecutionTestListeners;
import com.liferay.portal.kernel.util.IntegerWrapper;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.OrderByComparatorFactoryUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Time;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.ModelListener;
import com.liferay.portal.service.persistence.BasePersistence;
import com.liferay.portal.service.persistence.PersistenceExecutionTestListener;
import com.liferay.portal.test.LiferayPersistenceIntegrationJUnitTestRunner;
import com.liferay.portal.test.persistence.test.TransactionalPersistenceAdvice;
import com.liferay.portal.util.PropsValues;
import com.liferay.portal.util.test.RandomTestUtil;

import com.liferay.portlet.polls.NoSuchQuestionException;
import com.liferay.portlet.polls.model.PollsQuestion;
import com.liferay.portlet.polls.model.impl.PollsQuestionModelImpl;
import com.liferay.portlet.polls.service.PollsQuestionLocalServiceUtil;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import org.junit.runner.RunWith;

import java.io.Serializable;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author Brian Wing Shun Chan
 */
@ExecutionTestListeners(listeners =  {
	PersistenceExecutionTestListener.class})
@RunWith(LiferayPersistenceIntegrationJUnitTestRunner.class)
public class PollsQuestionPersistenceTest {
	@Before
	public void setUp() {
		_modelListeners = _persistence.getListeners();

		for (ModelListener<PollsQuestion> modelListener : _modelListeners) {
			_persistence.unregisterListener(modelListener);
		}
	}

	@After
	public void tearDown() throws Exception {
		Map<Serializable, BasePersistence<?>> basePersistences = _transactionalPersistenceAdvice.getBasePersistences();

		Set<Serializable> primaryKeys = basePersistences.keySet();

		for (Serializable primaryKey : primaryKeys) {
			BasePersistence<?> basePersistence = basePersistences.get(primaryKey);

			try {
				basePersistence.remove(primaryKey);
			}
			catch (Exception e) {
				if (_log.isDebugEnabled()) {
					_log.debug("The model with primary key " + primaryKey +
						" was already deleted");
				}
			}
		}

		_transactionalPersistenceAdvice.reset();

		for (ModelListener<PollsQuestion> modelListener : _modelListeners) {
			_persistence.registerListener(modelListener);
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

		_persistence.update(newPollsQuestion);

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
	public void testCountByUuid() {
		try {
			_persistence.countByUuid(StringPool.BLANK);

			_persistence.countByUuid(StringPool.NULL);

			_persistence.countByUuid((String)null);
		}
		catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void testCountByUUID_G() {
		try {
			_persistence.countByUUID_G(StringPool.BLANK,
				RandomTestUtil.nextLong());

			_persistence.countByUUID_G(StringPool.NULL, 0L);

			_persistence.countByUUID_G((String)null, 0L);
		}
		catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void testCountByUuid_C() {
		try {
			_persistence.countByUuid_C(StringPool.BLANK,
				RandomTestUtil.nextLong());

			_persistence.countByUuid_C(StringPool.NULL, 0L);

			_persistence.countByUuid_C((String)null, 0L);
		}
		catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void testCountByGroupId() {
		try {
			_persistence.countByGroupId(RandomTestUtil.nextLong());

			_persistence.countByGroupId(0L);
		}
		catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void testFindByPrimaryKeyExisting() throws Exception {
		PollsQuestion newPollsQuestion = addPollsQuestion();

		PollsQuestion existingPollsQuestion = _persistence.findByPrimaryKey(newPollsQuestion.getPrimaryKey());

		Assert.assertEquals(existingPollsQuestion, newPollsQuestion);
	}

	@Test
	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		try {
			_persistence.findByPrimaryKey(pk);

			Assert.fail("Missing entity did not throw NoSuchQuestionException");
		}
		catch (NoSuchQuestionException nsee) {
		}
	}

	@Test
	public void testFindAll() throws Exception {
		try {
			_persistence.findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS,
				getOrderByComparator());
		}
		catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void testFilterFindByGroupId() throws Exception {
		try {
			_persistence.filterFindByGroupId(0, QueryUtil.ALL_POS,
				QueryUtil.ALL_POS, getOrderByComparator());
		}
		catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	protected OrderByComparator getOrderByComparator() {
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
				PollsQuestion.class.getClassLoader());

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
				PollsQuestion.class.getClassLoader());

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
				PollsQuestion.class.getClassLoader());

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
				PollsQuestion.class.getClassLoader());

		dynamicQuery.setProjection(ProjectionFactoryUtil.property("questionId"));

		dynamicQuery.add(RestrictionsFactoryUtil.in("questionId",
				new Object[] { RandomTestUtil.nextLong() }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testResetOriginalValues() throws Exception {
		if (!PropsValues.HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE) {
			return;
		}

		PollsQuestion newPollsQuestion = addPollsQuestion();

		_persistence.clearCache();

		PollsQuestionModelImpl existingPollsQuestionModelImpl = (PollsQuestionModelImpl)_persistence.findByPrimaryKey(newPollsQuestion.getPrimaryKey());

		Assert.assertTrue(Validator.equals(
				existingPollsQuestionModelImpl.getUuid(),
				existingPollsQuestionModelImpl.getOriginalUuid()));
		Assert.assertEquals(existingPollsQuestionModelImpl.getGroupId(),
			existingPollsQuestionModelImpl.getOriginalGroupId());
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

		_persistence.update(pollsQuestion);

		return pollsQuestion;
	}

	private static Log _log = LogFactoryUtil.getLog(PollsQuestionPersistenceTest.class);
	private ModelListener<PollsQuestion>[] _modelListeners;
	private PollsQuestionPersistence _persistence = (PollsQuestionPersistence)PortalBeanLocatorUtil.locate(PollsQuestionPersistence.class.getName());
	private TransactionalPersistenceAdvice _transactionalPersistenceAdvice = (TransactionalPersistenceAdvice)PortalBeanLocatorUtil.locate(TransactionalPersistenceAdvice.class.getName());
}