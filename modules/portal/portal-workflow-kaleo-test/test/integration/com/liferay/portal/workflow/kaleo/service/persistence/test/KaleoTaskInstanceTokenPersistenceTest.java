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

package com.liferay.portal.workflow.kaleo.service.persistence.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;

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
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PersistenceTestRule;
import com.liferay.portal.workflow.kaleo.exception.NoSuchTaskInstanceTokenException;
import com.liferay.portal.workflow.kaleo.model.KaleoTaskInstanceToken;
import com.liferay.portal.workflow.kaleo.service.KaleoTaskInstanceTokenLocalServiceUtil;
import com.liferay.portal.workflow.kaleo.service.persistence.KaleoTaskInstanceTokenPersistence;
import com.liferay.portal.workflow.kaleo.service.persistence.KaleoTaskInstanceTokenUtil;

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
public class KaleoTaskInstanceTokenPersistenceTest {
	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule = new AggregateTestRule(new LiferayIntegrationTestRule(),
			PersistenceTestRule.INSTANCE,
			new TransactionalTestRule(Propagation.REQUIRED));

	@Before
	public void setUp() {
		_persistence = KaleoTaskInstanceTokenUtil.getPersistence();

		Class<?> clazz = _persistence.getClass();

		_dynamicQueryClassLoader = clazz.getClassLoader();
	}

	@After
	public void tearDown() throws Exception {
		Iterator<KaleoTaskInstanceToken> iterator = _kaleoTaskInstanceTokens.iterator();

		while (iterator.hasNext()) {
			_persistence.remove(iterator.next());

			iterator.remove();
		}
	}

	@Test
	public void testCreate() throws Exception {
		long pk = RandomTestUtil.nextLong();

		KaleoTaskInstanceToken kaleoTaskInstanceToken = _persistence.create(pk);

		Assert.assertNotNull(kaleoTaskInstanceToken);

		Assert.assertEquals(kaleoTaskInstanceToken.getPrimaryKey(), pk);
	}

	@Test
	public void testRemove() throws Exception {
		KaleoTaskInstanceToken newKaleoTaskInstanceToken = addKaleoTaskInstanceToken();

		_persistence.remove(newKaleoTaskInstanceToken);

		KaleoTaskInstanceToken existingKaleoTaskInstanceToken = _persistence.fetchByPrimaryKey(newKaleoTaskInstanceToken.getPrimaryKey());

		Assert.assertNull(existingKaleoTaskInstanceToken);
	}

	@Test
	public void testUpdateNew() throws Exception {
		addKaleoTaskInstanceToken();
	}

	@Test
	public void testUpdateExisting() throws Exception {
		long pk = RandomTestUtil.nextLong();

		KaleoTaskInstanceToken newKaleoTaskInstanceToken = _persistence.create(pk);

		newKaleoTaskInstanceToken.setGroupId(RandomTestUtil.nextLong());

		newKaleoTaskInstanceToken.setCompanyId(RandomTestUtil.nextLong());

		newKaleoTaskInstanceToken.setUserId(RandomTestUtil.nextLong());

		newKaleoTaskInstanceToken.setUserName(RandomTestUtil.randomString());

		newKaleoTaskInstanceToken.setCreateDate(RandomTestUtil.nextDate());

		newKaleoTaskInstanceToken.setModifiedDate(RandomTestUtil.nextDate());

		newKaleoTaskInstanceToken.setKaleoDefinitionId(RandomTestUtil.nextLong());

		newKaleoTaskInstanceToken.setKaleoInstanceId(RandomTestUtil.nextLong());

		newKaleoTaskInstanceToken.setKaleoInstanceTokenId(RandomTestUtil.nextLong());

		newKaleoTaskInstanceToken.setKaleoTaskId(RandomTestUtil.nextLong());

		newKaleoTaskInstanceToken.setKaleoTaskName(RandomTestUtil.randomString());

		newKaleoTaskInstanceToken.setClassName(RandomTestUtil.randomString());

		newKaleoTaskInstanceToken.setClassPK(RandomTestUtil.nextLong());

		newKaleoTaskInstanceToken.setCompletionUserId(RandomTestUtil.nextLong());

		newKaleoTaskInstanceToken.setCompleted(RandomTestUtil.randomBoolean());

		newKaleoTaskInstanceToken.setCompletionDate(RandomTestUtil.nextDate());

		newKaleoTaskInstanceToken.setDueDate(RandomTestUtil.nextDate());

		newKaleoTaskInstanceToken.setWorkflowContext(RandomTestUtil.randomString());

		_kaleoTaskInstanceTokens.add(_persistence.update(
				newKaleoTaskInstanceToken));

		KaleoTaskInstanceToken existingKaleoTaskInstanceToken = _persistence.findByPrimaryKey(newKaleoTaskInstanceToken.getPrimaryKey());

		Assert.assertEquals(existingKaleoTaskInstanceToken.getKaleoTaskInstanceTokenId(),
			newKaleoTaskInstanceToken.getKaleoTaskInstanceTokenId());
		Assert.assertEquals(existingKaleoTaskInstanceToken.getGroupId(),
			newKaleoTaskInstanceToken.getGroupId());
		Assert.assertEquals(existingKaleoTaskInstanceToken.getCompanyId(),
			newKaleoTaskInstanceToken.getCompanyId());
		Assert.assertEquals(existingKaleoTaskInstanceToken.getUserId(),
			newKaleoTaskInstanceToken.getUserId());
		Assert.assertEquals(existingKaleoTaskInstanceToken.getUserName(),
			newKaleoTaskInstanceToken.getUserName());
		Assert.assertEquals(Time.getShortTimestamp(
				existingKaleoTaskInstanceToken.getCreateDate()),
			Time.getShortTimestamp(newKaleoTaskInstanceToken.getCreateDate()));
		Assert.assertEquals(Time.getShortTimestamp(
				existingKaleoTaskInstanceToken.getModifiedDate()),
			Time.getShortTimestamp(newKaleoTaskInstanceToken.getModifiedDate()));
		Assert.assertEquals(existingKaleoTaskInstanceToken.getKaleoDefinitionId(),
			newKaleoTaskInstanceToken.getKaleoDefinitionId());
		Assert.assertEquals(existingKaleoTaskInstanceToken.getKaleoInstanceId(),
			newKaleoTaskInstanceToken.getKaleoInstanceId());
		Assert.assertEquals(existingKaleoTaskInstanceToken.getKaleoInstanceTokenId(),
			newKaleoTaskInstanceToken.getKaleoInstanceTokenId());
		Assert.assertEquals(existingKaleoTaskInstanceToken.getKaleoTaskId(),
			newKaleoTaskInstanceToken.getKaleoTaskId());
		Assert.assertEquals(existingKaleoTaskInstanceToken.getKaleoTaskName(),
			newKaleoTaskInstanceToken.getKaleoTaskName());
		Assert.assertEquals(existingKaleoTaskInstanceToken.getClassName(),
			newKaleoTaskInstanceToken.getClassName());
		Assert.assertEquals(existingKaleoTaskInstanceToken.getClassPK(),
			newKaleoTaskInstanceToken.getClassPK());
		Assert.assertEquals(existingKaleoTaskInstanceToken.getCompletionUserId(),
			newKaleoTaskInstanceToken.getCompletionUserId());
		Assert.assertEquals(existingKaleoTaskInstanceToken.getCompleted(),
			newKaleoTaskInstanceToken.getCompleted());
		Assert.assertEquals(Time.getShortTimestamp(
				existingKaleoTaskInstanceToken.getCompletionDate()),
			Time.getShortTimestamp(
				newKaleoTaskInstanceToken.getCompletionDate()));
		Assert.assertEquals(Time.getShortTimestamp(
				existingKaleoTaskInstanceToken.getDueDate()),
			Time.getShortTimestamp(newKaleoTaskInstanceToken.getDueDate()));
		Assert.assertEquals(existingKaleoTaskInstanceToken.getWorkflowContext(),
			newKaleoTaskInstanceToken.getWorkflowContext());
	}

	@Test
	public void testCountByCompanyId() throws Exception {
		_persistence.countByCompanyId(RandomTestUtil.nextLong());

		_persistence.countByCompanyId(0L);
	}

	@Test
	public void testCountByKaleoDefinitionId() throws Exception {
		_persistence.countByKaleoDefinitionId(RandomTestUtil.nextLong());

		_persistence.countByKaleoDefinitionId(0L);
	}

	@Test
	public void testCountByKaleoInstanceId() throws Exception {
		_persistence.countByKaleoInstanceId(RandomTestUtil.nextLong());

		_persistence.countByKaleoInstanceId(0L);
	}

	@Test
	public void testCountByKII_KTI() throws Exception {
		_persistence.countByKII_KTI(RandomTestUtil.nextLong(),
			RandomTestUtil.nextLong());

		_persistence.countByKII_KTI(0L, 0L);
	}

	@Test
	public void testCountByCN_CPK() throws Exception {
		_persistence.countByCN_CPK(StringPool.BLANK, RandomTestUtil.nextLong());

		_persistence.countByCN_CPK(StringPool.NULL, 0L);

		_persistence.countByCN_CPK((String)null, 0L);
	}

	@Test
	public void testFindByPrimaryKeyExisting() throws Exception {
		KaleoTaskInstanceToken newKaleoTaskInstanceToken = addKaleoTaskInstanceToken();

		KaleoTaskInstanceToken existingKaleoTaskInstanceToken = _persistence.findByPrimaryKey(newKaleoTaskInstanceToken.getPrimaryKey());

		Assert.assertEquals(existingKaleoTaskInstanceToken,
			newKaleoTaskInstanceToken);
	}

	@Test(expected = NoSuchTaskInstanceTokenException.class)
	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		_persistence.findByPrimaryKey(pk);
	}

	@Test
	public void testFindAll() throws Exception {
		_persistence.findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			getOrderByComparator());
	}

	protected OrderByComparator<KaleoTaskInstanceToken> getOrderByComparator() {
		return OrderByComparatorFactoryUtil.create("KaleoTaskInstanceToken",
			"kaleoTaskInstanceTokenId", true, "groupId", true, "companyId",
			true, "userId", true, "userName", true, "createDate", true,
			"modifiedDate", true, "kaleoDefinitionId", true, "kaleoInstanceId",
			true, "kaleoInstanceTokenId", true, "kaleoTaskId", true,
			"kaleoTaskName", true, "className", true, "classPK", true,
			"completionUserId", true, "completed", true, "completionDate",
			true, "dueDate", true);
	}

	@Test
	public void testFetchByPrimaryKeyExisting() throws Exception {
		KaleoTaskInstanceToken newKaleoTaskInstanceToken = addKaleoTaskInstanceToken();

		KaleoTaskInstanceToken existingKaleoTaskInstanceToken = _persistence.fetchByPrimaryKey(newKaleoTaskInstanceToken.getPrimaryKey());

		Assert.assertEquals(existingKaleoTaskInstanceToken,
			newKaleoTaskInstanceToken);
	}

	@Test
	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		KaleoTaskInstanceToken missingKaleoTaskInstanceToken = _persistence.fetchByPrimaryKey(pk);

		Assert.assertNull(missingKaleoTaskInstanceToken);
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereAllPrimaryKeysExist()
		throws Exception {
		KaleoTaskInstanceToken newKaleoTaskInstanceToken1 = addKaleoTaskInstanceToken();
		KaleoTaskInstanceToken newKaleoTaskInstanceToken2 = addKaleoTaskInstanceToken();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newKaleoTaskInstanceToken1.getPrimaryKey());
		primaryKeys.add(newKaleoTaskInstanceToken2.getPrimaryKey());

		Map<Serializable, KaleoTaskInstanceToken> kaleoTaskInstanceTokens = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(2, kaleoTaskInstanceTokens.size());
		Assert.assertEquals(newKaleoTaskInstanceToken1,
			kaleoTaskInstanceTokens.get(
				newKaleoTaskInstanceToken1.getPrimaryKey()));
		Assert.assertEquals(newKaleoTaskInstanceToken2,
			kaleoTaskInstanceTokens.get(
				newKaleoTaskInstanceToken2.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereNoPrimaryKeysExist()
		throws Exception {
		long pk1 = RandomTestUtil.nextLong();

		long pk2 = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(pk1);
		primaryKeys.add(pk2);

		Map<Serializable, KaleoTaskInstanceToken> kaleoTaskInstanceTokens = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(kaleoTaskInstanceTokens.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereSomePrimaryKeysExist()
		throws Exception {
		KaleoTaskInstanceToken newKaleoTaskInstanceToken = addKaleoTaskInstanceToken();

		long pk = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newKaleoTaskInstanceToken.getPrimaryKey());
		primaryKeys.add(pk);

		Map<Serializable, KaleoTaskInstanceToken> kaleoTaskInstanceTokens = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, kaleoTaskInstanceTokens.size());
		Assert.assertEquals(newKaleoTaskInstanceToken,
			kaleoTaskInstanceTokens.get(
				newKaleoTaskInstanceToken.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithNoPrimaryKeys()
		throws Exception {
		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		Map<Serializable, KaleoTaskInstanceToken> kaleoTaskInstanceTokens = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(kaleoTaskInstanceTokens.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithOnePrimaryKey()
		throws Exception {
		KaleoTaskInstanceToken newKaleoTaskInstanceToken = addKaleoTaskInstanceToken();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newKaleoTaskInstanceToken.getPrimaryKey());

		Map<Serializable, KaleoTaskInstanceToken> kaleoTaskInstanceTokens = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, kaleoTaskInstanceTokens.size());
		Assert.assertEquals(newKaleoTaskInstanceToken,
			kaleoTaskInstanceTokens.get(
				newKaleoTaskInstanceToken.getPrimaryKey()));
	}

	@Test
	public void testActionableDynamicQuery() throws Exception {
		final IntegerWrapper count = new IntegerWrapper();

		ActionableDynamicQuery actionableDynamicQuery = KaleoTaskInstanceTokenLocalServiceUtil.getActionableDynamicQuery();

		actionableDynamicQuery.setPerformActionMethod(new ActionableDynamicQuery.PerformActionMethod<KaleoTaskInstanceToken>() {
				@Override
				public void performAction(
					KaleoTaskInstanceToken kaleoTaskInstanceToken) {
					Assert.assertNotNull(kaleoTaskInstanceToken);

					count.increment();
				}
			});

		actionableDynamicQuery.performActions();

		Assert.assertEquals(count.getValue(), _persistence.countAll());
	}

	@Test
	public void testDynamicQueryByPrimaryKeyExisting()
		throws Exception {
		KaleoTaskInstanceToken newKaleoTaskInstanceToken = addKaleoTaskInstanceToken();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(KaleoTaskInstanceToken.class,
				_dynamicQueryClassLoader);

		dynamicQuery.add(RestrictionsFactoryUtil.eq(
				"kaleoTaskInstanceTokenId",
				newKaleoTaskInstanceToken.getKaleoTaskInstanceTokenId()));

		List<KaleoTaskInstanceToken> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		KaleoTaskInstanceToken existingKaleoTaskInstanceToken = result.get(0);

		Assert.assertEquals(existingKaleoTaskInstanceToken,
			newKaleoTaskInstanceToken);
	}

	@Test
	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(KaleoTaskInstanceToken.class,
				_dynamicQueryClassLoader);

		dynamicQuery.add(RestrictionsFactoryUtil.eq(
				"kaleoTaskInstanceTokenId", RandomTestUtil.nextLong()));

		List<KaleoTaskInstanceToken> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testDynamicQueryByProjectionExisting()
		throws Exception {
		KaleoTaskInstanceToken newKaleoTaskInstanceToken = addKaleoTaskInstanceToken();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(KaleoTaskInstanceToken.class,
				_dynamicQueryClassLoader);

		dynamicQuery.setProjection(ProjectionFactoryUtil.property(
				"kaleoTaskInstanceTokenId"));

		Object newKaleoTaskInstanceTokenId = newKaleoTaskInstanceToken.getKaleoTaskInstanceTokenId();

		dynamicQuery.add(RestrictionsFactoryUtil.in(
				"kaleoTaskInstanceTokenId",
				new Object[] { newKaleoTaskInstanceTokenId }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		Object existingKaleoTaskInstanceTokenId = result.get(0);

		Assert.assertEquals(existingKaleoTaskInstanceTokenId,
			newKaleoTaskInstanceTokenId);
	}

	@Test
	public void testDynamicQueryByProjectionMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(KaleoTaskInstanceToken.class,
				_dynamicQueryClassLoader);

		dynamicQuery.setProjection(ProjectionFactoryUtil.property(
				"kaleoTaskInstanceTokenId"));

		dynamicQuery.add(RestrictionsFactoryUtil.in(
				"kaleoTaskInstanceTokenId",
				new Object[] { RandomTestUtil.nextLong() }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testResetOriginalValues() throws Exception {
		KaleoTaskInstanceToken newKaleoTaskInstanceToken = addKaleoTaskInstanceToken();

		_persistence.clearCache();

		KaleoTaskInstanceToken existingKaleoTaskInstanceToken = _persistence.findByPrimaryKey(newKaleoTaskInstanceToken.getPrimaryKey());

		Assert.assertEquals(Long.valueOf(
				existingKaleoTaskInstanceToken.getKaleoInstanceId()),
			ReflectionTestUtil.<Long>invoke(existingKaleoTaskInstanceToken,
				"getOriginalKaleoInstanceId", new Class<?>[0]));
		Assert.assertEquals(Long.valueOf(
				existingKaleoTaskInstanceToken.getKaleoTaskId()),
			ReflectionTestUtil.<Long>invoke(existingKaleoTaskInstanceToken,
				"getOriginalKaleoTaskId", new Class<?>[0]));
	}

	protected KaleoTaskInstanceToken addKaleoTaskInstanceToken()
		throws Exception {
		long pk = RandomTestUtil.nextLong();

		KaleoTaskInstanceToken kaleoTaskInstanceToken = _persistence.create(pk);

		kaleoTaskInstanceToken.setGroupId(RandomTestUtil.nextLong());

		kaleoTaskInstanceToken.setCompanyId(RandomTestUtil.nextLong());

		kaleoTaskInstanceToken.setUserId(RandomTestUtil.nextLong());

		kaleoTaskInstanceToken.setUserName(RandomTestUtil.randomString());

		kaleoTaskInstanceToken.setCreateDate(RandomTestUtil.nextDate());

		kaleoTaskInstanceToken.setModifiedDate(RandomTestUtil.nextDate());

		kaleoTaskInstanceToken.setKaleoDefinitionId(RandomTestUtil.nextLong());

		kaleoTaskInstanceToken.setKaleoInstanceId(RandomTestUtil.nextLong());

		kaleoTaskInstanceToken.setKaleoInstanceTokenId(RandomTestUtil.nextLong());

		kaleoTaskInstanceToken.setKaleoTaskId(RandomTestUtil.nextLong());

		kaleoTaskInstanceToken.setKaleoTaskName(RandomTestUtil.randomString());

		kaleoTaskInstanceToken.setClassName(RandomTestUtil.randomString());

		kaleoTaskInstanceToken.setClassPK(RandomTestUtil.nextLong());

		kaleoTaskInstanceToken.setCompletionUserId(RandomTestUtil.nextLong());

		kaleoTaskInstanceToken.setCompleted(RandomTestUtil.randomBoolean());

		kaleoTaskInstanceToken.setCompletionDate(RandomTestUtil.nextDate());

		kaleoTaskInstanceToken.setDueDate(RandomTestUtil.nextDate());

		kaleoTaskInstanceToken.setWorkflowContext(RandomTestUtil.randomString());

		_kaleoTaskInstanceTokens.add(_persistence.update(kaleoTaskInstanceToken));

		return kaleoTaskInstanceToken;
	}

	private List<KaleoTaskInstanceToken> _kaleoTaskInstanceTokens = new ArrayList<KaleoTaskInstanceToken>();
	private KaleoTaskInstanceTokenPersistence _persistence;
	private ClassLoader _dynamicQueryClassLoader;
}