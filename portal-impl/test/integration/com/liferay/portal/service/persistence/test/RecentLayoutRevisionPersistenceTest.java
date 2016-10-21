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

package com.liferay.portal.service.persistence.test;

import com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQueryFactoryUtil;
import com.liferay.portal.kernel.dao.orm.ProjectionFactoryUtil;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.RestrictionsFactoryUtil;
import com.liferay.portal.kernel.exception.NoSuchRecentLayoutRevisionException;
import com.liferay.portal.kernel.model.RecentLayoutRevision;
import com.liferay.portal.kernel.service.RecentLayoutRevisionLocalServiceUtil;
import com.liferay.portal.kernel.service.persistence.RecentLayoutRevisionPersistence;
import com.liferay.portal.kernel.service.persistence.RecentLayoutRevisionUtil;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.transaction.Propagation;
import com.liferay.portal.kernel.util.IntegerWrapper;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.OrderByComparatorFactoryUtil;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PersistenceTestRule;
import com.liferay.portal.test.rule.TransactionalTestRule;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

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
public class RecentLayoutRevisionPersistenceTest {
	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule = new AggregateTestRule(new LiferayIntegrationTestRule(),
			PersistenceTestRule.INSTANCE,
			new TransactionalTestRule(Propagation.REQUIRED));

	@Before
	public void setUp() {
		_persistence = RecentLayoutRevisionUtil.getPersistence();

		Class<?> clazz = _persistence.getClass();

		_dynamicQueryClassLoader = clazz.getClassLoader();
	}

	@After
	public void tearDown() throws Exception {
		Iterator<RecentLayoutRevision> iterator = _recentLayoutRevisions.iterator();

		while (iterator.hasNext()) {
			_persistence.remove(iterator.next());

			iterator.remove();
		}
	}

	@Test
	public void testCreate() throws Exception {
		long pk = RandomTestUtil.nextLong();

		RecentLayoutRevision recentLayoutRevision = _persistence.create(pk);

		Assert.assertNotNull(recentLayoutRevision);

		Assert.assertEquals(recentLayoutRevision.getPrimaryKey(), pk);
	}

	@Test
	public void testRemove() throws Exception {
		RecentLayoutRevision newRecentLayoutRevision = addRecentLayoutRevision();

		_persistence.remove(newRecentLayoutRevision);

		RecentLayoutRevision existingRecentLayoutRevision = _persistence.fetchByPrimaryKey(newRecentLayoutRevision.getPrimaryKey());

		Assert.assertNull(existingRecentLayoutRevision);
	}

	@Test
	public void testUpdateNew() throws Exception {
		addRecentLayoutRevision();
	}

	@Test
	public void testUpdateExisting() throws Exception {
		long pk = RandomTestUtil.nextLong();

		RecentLayoutRevision newRecentLayoutRevision = _persistence.create(pk);

		newRecentLayoutRevision.setMvccVersion(RandomTestUtil.nextLong());

		newRecentLayoutRevision.setGroupId(RandomTestUtil.nextLong());

		newRecentLayoutRevision.setCompanyId(RandomTestUtil.nextLong());

		newRecentLayoutRevision.setUserId(RandomTestUtil.nextLong());

		newRecentLayoutRevision.setLayoutRevisionId(RandomTestUtil.nextLong());

		newRecentLayoutRevision.setLayoutSetBranchId(RandomTestUtil.nextLong());

		newRecentLayoutRevision.setPlid(RandomTestUtil.nextLong());

		_recentLayoutRevisions.add(_persistence.update(newRecentLayoutRevision));

		RecentLayoutRevision existingRecentLayoutRevision = _persistence.findByPrimaryKey(newRecentLayoutRevision.getPrimaryKey());

		Assert.assertEquals(existingRecentLayoutRevision.getMvccVersion(),
			newRecentLayoutRevision.getMvccVersion());
		Assert.assertEquals(existingRecentLayoutRevision.getRecentLayoutRevisionId(),
			newRecentLayoutRevision.getRecentLayoutRevisionId());
		Assert.assertEquals(existingRecentLayoutRevision.getGroupId(),
			newRecentLayoutRevision.getGroupId());
		Assert.assertEquals(existingRecentLayoutRevision.getCompanyId(),
			newRecentLayoutRevision.getCompanyId());
		Assert.assertEquals(existingRecentLayoutRevision.getUserId(),
			newRecentLayoutRevision.getUserId());
		Assert.assertEquals(existingRecentLayoutRevision.getLayoutRevisionId(),
			newRecentLayoutRevision.getLayoutRevisionId());
		Assert.assertEquals(existingRecentLayoutRevision.getLayoutSetBranchId(),
			newRecentLayoutRevision.getLayoutSetBranchId());
		Assert.assertEquals(existingRecentLayoutRevision.getPlid(),
			newRecentLayoutRevision.getPlid());
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
	public void testCountByLayoutRevisionId() throws Exception {
		_persistence.countByLayoutRevisionId(RandomTestUtil.nextLong());

		_persistence.countByLayoutRevisionId(0L);
	}

	@Test
	public void testCountByU_L_P() throws Exception {
		_persistence.countByU_L_P(RandomTestUtil.nextLong(),
			RandomTestUtil.nextLong(), RandomTestUtil.nextLong());

		_persistence.countByU_L_P(0L, 0L, 0L);
	}

	@Test
	public void testFindByPrimaryKeyExisting() throws Exception {
		RecentLayoutRevision newRecentLayoutRevision = addRecentLayoutRevision();

		RecentLayoutRevision existingRecentLayoutRevision = _persistence.findByPrimaryKey(newRecentLayoutRevision.getPrimaryKey());

		Assert.assertEquals(existingRecentLayoutRevision,
			newRecentLayoutRevision);
	}

	@Test(expected = NoSuchRecentLayoutRevisionException.class)
	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		_persistence.findByPrimaryKey(pk);
	}

	@Test
	public void testFindAll() throws Exception {
		_persistence.findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			getOrderByComparator());
	}

	protected OrderByComparator<RecentLayoutRevision> getOrderByComparator() {
		return OrderByComparatorFactoryUtil.create("RecentLayoutRevision",
			"mvccVersion", true, "recentLayoutRevisionId", true, "groupId",
			true, "companyId", true, "userId", true, "layoutRevisionId", true,
			"layoutSetBranchId", true, "plid", true);
	}

	@Test
	public void testFetchByPrimaryKeyExisting() throws Exception {
		RecentLayoutRevision newRecentLayoutRevision = addRecentLayoutRevision();

		RecentLayoutRevision existingRecentLayoutRevision = _persistence.fetchByPrimaryKey(newRecentLayoutRevision.getPrimaryKey());

		Assert.assertEquals(existingRecentLayoutRevision,
			newRecentLayoutRevision);
	}

	@Test
	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		RecentLayoutRevision missingRecentLayoutRevision = _persistence.fetchByPrimaryKey(pk);

		Assert.assertNull(missingRecentLayoutRevision);
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereAllPrimaryKeysExist()
		throws Exception {
		RecentLayoutRevision newRecentLayoutRevision1 = addRecentLayoutRevision();
		RecentLayoutRevision newRecentLayoutRevision2 = addRecentLayoutRevision();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newRecentLayoutRevision1.getPrimaryKey());
		primaryKeys.add(newRecentLayoutRevision2.getPrimaryKey());

		Map<Serializable, RecentLayoutRevision> recentLayoutRevisions = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(2, recentLayoutRevisions.size());
		Assert.assertEquals(newRecentLayoutRevision1,
			recentLayoutRevisions.get(newRecentLayoutRevision1.getPrimaryKey()));
		Assert.assertEquals(newRecentLayoutRevision2,
			recentLayoutRevisions.get(newRecentLayoutRevision2.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereNoPrimaryKeysExist()
		throws Exception {
		long pk1 = RandomTestUtil.nextLong();

		long pk2 = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(pk1);
		primaryKeys.add(pk2);

		Map<Serializable, RecentLayoutRevision> recentLayoutRevisions = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(recentLayoutRevisions.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereSomePrimaryKeysExist()
		throws Exception {
		RecentLayoutRevision newRecentLayoutRevision = addRecentLayoutRevision();

		long pk = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newRecentLayoutRevision.getPrimaryKey());
		primaryKeys.add(pk);

		Map<Serializable, RecentLayoutRevision> recentLayoutRevisions = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, recentLayoutRevisions.size());
		Assert.assertEquals(newRecentLayoutRevision,
			recentLayoutRevisions.get(newRecentLayoutRevision.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithNoPrimaryKeys()
		throws Exception {
		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		Map<Serializable, RecentLayoutRevision> recentLayoutRevisions = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(recentLayoutRevisions.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithOnePrimaryKey()
		throws Exception {
		RecentLayoutRevision newRecentLayoutRevision = addRecentLayoutRevision();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newRecentLayoutRevision.getPrimaryKey());

		Map<Serializable, RecentLayoutRevision> recentLayoutRevisions = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, recentLayoutRevisions.size());
		Assert.assertEquals(newRecentLayoutRevision,
			recentLayoutRevisions.get(newRecentLayoutRevision.getPrimaryKey()));
	}

	@Test
	public void testActionableDynamicQuery() throws Exception {
		final IntegerWrapper count = new IntegerWrapper();

		ActionableDynamicQuery actionableDynamicQuery = RecentLayoutRevisionLocalServiceUtil.getActionableDynamicQuery();

		actionableDynamicQuery.setPerformActionMethod(new ActionableDynamicQuery.PerformActionMethod<RecentLayoutRevision>() {
				@Override
				public void performAction(
					RecentLayoutRevision recentLayoutRevision) {
					Assert.assertNotNull(recentLayoutRevision);

					count.increment();
				}
			});

		actionableDynamicQuery.performActions();

		Assert.assertEquals(count.getValue(), _persistence.countAll());
	}

	@Test
	public void testDynamicQueryByPrimaryKeyExisting()
		throws Exception {
		RecentLayoutRevision newRecentLayoutRevision = addRecentLayoutRevision();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(RecentLayoutRevision.class,
				_dynamicQueryClassLoader);

		dynamicQuery.add(RestrictionsFactoryUtil.eq("recentLayoutRevisionId",
				newRecentLayoutRevision.getRecentLayoutRevisionId()));

		List<RecentLayoutRevision> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		RecentLayoutRevision existingRecentLayoutRevision = result.get(0);

		Assert.assertEquals(existingRecentLayoutRevision,
			newRecentLayoutRevision);
	}

	@Test
	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(RecentLayoutRevision.class,
				_dynamicQueryClassLoader);

		dynamicQuery.add(RestrictionsFactoryUtil.eq("recentLayoutRevisionId",
				RandomTestUtil.nextLong()));

		List<RecentLayoutRevision> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testDynamicQueryByProjectionExisting()
		throws Exception {
		RecentLayoutRevision newRecentLayoutRevision = addRecentLayoutRevision();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(RecentLayoutRevision.class,
				_dynamicQueryClassLoader);

		dynamicQuery.setProjection(ProjectionFactoryUtil.property(
				"recentLayoutRevisionId"));

		Object newRecentLayoutRevisionId = newRecentLayoutRevision.getRecentLayoutRevisionId();

		dynamicQuery.add(RestrictionsFactoryUtil.in("recentLayoutRevisionId",
				new Object[] { newRecentLayoutRevisionId }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		Object existingRecentLayoutRevisionId = result.get(0);

		Assert.assertEquals(existingRecentLayoutRevisionId,
			newRecentLayoutRevisionId);
	}

	@Test
	public void testDynamicQueryByProjectionMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(RecentLayoutRevision.class,
				_dynamicQueryClassLoader);

		dynamicQuery.setProjection(ProjectionFactoryUtil.property(
				"recentLayoutRevisionId"));

		dynamicQuery.add(RestrictionsFactoryUtil.in("recentLayoutRevisionId",
				new Object[] { RandomTestUtil.nextLong() }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testResetOriginalValues() throws Exception {
		RecentLayoutRevision newRecentLayoutRevision = addRecentLayoutRevision();

		_persistence.clearCache();

		RecentLayoutRevision existingRecentLayoutRevision = _persistence.findByPrimaryKey(newRecentLayoutRevision.getPrimaryKey());

		Assert.assertEquals(Long.valueOf(
				existingRecentLayoutRevision.getUserId()),
			ReflectionTestUtil.<Long>invoke(existingRecentLayoutRevision,
				"getOriginalUserId", new Class<?>[0]));
		Assert.assertEquals(Long.valueOf(
				existingRecentLayoutRevision.getLayoutSetBranchId()),
			ReflectionTestUtil.<Long>invoke(existingRecentLayoutRevision,
				"getOriginalLayoutSetBranchId", new Class<?>[0]));
		Assert.assertEquals(Long.valueOf(existingRecentLayoutRevision.getPlid()),
			ReflectionTestUtil.<Long>invoke(existingRecentLayoutRevision,
				"getOriginalPlid", new Class<?>[0]));
	}

	protected RecentLayoutRevision addRecentLayoutRevision()
		throws Exception {
		long pk = RandomTestUtil.nextLong();

		RecentLayoutRevision recentLayoutRevision = _persistence.create(pk);

		recentLayoutRevision.setMvccVersion(RandomTestUtil.nextLong());

		recentLayoutRevision.setGroupId(RandomTestUtil.nextLong());

		recentLayoutRevision.setCompanyId(RandomTestUtil.nextLong());

		recentLayoutRevision.setUserId(RandomTestUtil.nextLong());

		recentLayoutRevision.setLayoutRevisionId(RandomTestUtil.nextLong());

		recentLayoutRevision.setLayoutSetBranchId(RandomTestUtil.nextLong());

		recentLayoutRevision.setPlid(RandomTestUtil.nextLong());

		_recentLayoutRevisions.add(_persistence.update(recentLayoutRevision));

		return recentLayoutRevision;
	}

	private List<RecentLayoutRevision> _recentLayoutRevisions = new ArrayList<RecentLayoutRevision>();
	private RecentLayoutRevisionPersistence _persistence;
	private ClassLoader _dynamicQueryClassLoader;
}