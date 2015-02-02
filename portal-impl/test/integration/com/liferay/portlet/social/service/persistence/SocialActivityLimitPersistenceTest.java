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

package com.liferay.portlet.social.service.persistence;

import com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQueryFactoryUtil;
import com.liferay.portal.kernel.dao.orm.ProjectionFactoryUtil;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.RestrictionsFactoryUtil;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.TransactionalTestRule;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.transaction.Propagation;
import com.liferay.portal.kernel.util.IntegerWrapper;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.OrderByComparatorFactoryUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PersistenceTestRule;
import com.liferay.portal.util.PropsValues;

import com.liferay.portlet.social.NoSuchActivityLimitException;
import com.liferay.portlet.social.model.SocialActivityLimit;
import com.liferay.portlet.social.model.impl.SocialActivityLimitModelImpl;
import com.liferay.portlet.social.service.SocialActivityLimitLocalServiceUtil;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
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
public class SocialActivityLimitPersistenceTest {
	@Rule
	public final AggregateTestRule aggregateTestRule = new AggregateTestRule(new LiferayIntegrationTestRule(),
			PersistenceTestRule.INSTANCE,
			new TransactionalTestRule(Propagation.REQUIRED));

	@Before
	public void setUp() {
		_persistence = SocialActivityLimitUtil.getPersistence();
	}

	@After
	public void tearDown() throws Exception {
		Iterator<SocialActivityLimit> iterator = _socialActivityLimits.iterator();

		while (iterator.hasNext()) {
			_persistence.remove(iterator.next());

			iterator.remove();
		}
	}

	@Test
	public void testCreate() throws Exception {
		long pk = RandomTestUtil.nextLong();

		SocialActivityLimit socialActivityLimit = _persistence.create(pk);

		Assert.assertNotNull(socialActivityLimit);

		Assert.assertEquals(socialActivityLimit.getPrimaryKey(), pk);
	}

	@Test
	public void testRemove() throws Exception {
		SocialActivityLimit newSocialActivityLimit = addSocialActivityLimit();

		_persistence.remove(newSocialActivityLimit);

		SocialActivityLimit existingSocialActivityLimit = _persistence.fetchByPrimaryKey(newSocialActivityLimit.getPrimaryKey());

		Assert.assertNull(existingSocialActivityLimit);
	}

	@Test
	public void testUpdateNew() throws Exception {
		addSocialActivityLimit();
	}

	@Test
	public void testUpdateExisting() throws Exception {
		long pk = RandomTestUtil.nextLong();

		SocialActivityLimit newSocialActivityLimit = _persistence.create(pk);

		newSocialActivityLimit.setGroupId(RandomTestUtil.nextLong());

		newSocialActivityLimit.setCompanyId(RandomTestUtil.nextLong());

		newSocialActivityLimit.setUserId(RandomTestUtil.nextLong());

		newSocialActivityLimit.setClassNameId(RandomTestUtil.nextLong());

		newSocialActivityLimit.setClassPK(RandomTestUtil.nextLong());

		newSocialActivityLimit.setActivityType(RandomTestUtil.nextInt());

		newSocialActivityLimit.setActivityCounterName(RandomTestUtil.randomString());

		newSocialActivityLimit.setValue(RandomTestUtil.randomString());

		_socialActivityLimits.add(_persistence.update(newSocialActivityLimit));

		SocialActivityLimit existingSocialActivityLimit = _persistence.findByPrimaryKey(newSocialActivityLimit.getPrimaryKey());

		Assert.assertEquals(existingSocialActivityLimit.getActivityLimitId(),
			newSocialActivityLimit.getActivityLimitId());
		Assert.assertEquals(existingSocialActivityLimit.getGroupId(),
			newSocialActivityLimit.getGroupId());
		Assert.assertEquals(existingSocialActivityLimit.getCompanyId(),
			newSocialActivityLimit.getCompanyId());
		Assert.assertEquals(existingSocialActivityLimit.getUserId(),
			newSocialActivityLimit.getUserId());
		Assert.assertEquals(existingSocialActivityLimit.getClassNameId(),
			newSocialActivityLimit.getClassNameId());
		Assert.assertEquals(existingSocialActivityLimit.getClassPK(),
			newSocialActivityLimit.getClassPK());
		Assert.assertEquals(existingSocialActivityLimit.getActivityType(),
			newSocialActivityLimit.getActivityType());
		Assert.assertEquals(existingSocialActivityLimit.getActivityCounterName(),
			newSocialActivityLimit.getActivityCounterName());
		Assert.assertEquals(existingSocialActivityLimit.getValue(),
			newSocialActivityLimit.getValue());
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
	public void testCountByUserId() {
		try {
			_persistence.countByUserId(RandomTestUtil.nextLong());

			_persistence.countByUserId(0L);
		}
		catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void testCountByC_C() {
		try {
			_persistence.countByC_C(RandomTestUtil.nextLong(),
				RandomTestUtil.nextLong());

			_persistence.countByC_C(0L, 0L);
		}
		catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void testCountByG_U_C_C_A_A() {
		try {
			_persistence.countByG_U_C_C_A_A(RandomTestUtil.nextLong(),
				RandomTestUtil.nextLong(), RandomTestUtil.nextLong(),
				RandomTestUtil.nextLong(), RandomTestUtil.nextInt(),
				StringPool.BLANK);

			_persistence.countByG_U_C_C_A_A(0L, 0L, 0L, 0L, 0, StringPool.NULL);

			_persistence.countByG_U_C_C_A_A(0L, 0L, 0L, 0L, 0, (String)null);
		}
		catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void testFindByPrimaryKeyExisting() throws Exception {
		SocialActivityLimit newSocialActivityLimit = addSocialActivityLimit();

		SocialActivityLimit existingSocialActivityLimit = _persistence.findByPrimaryKey(newSocialActivityLimit.getPrimaryKey());

		Assert.assertEquals(existingSocialActivityLimit, newSocialActivityLimit);
	}

	@Test
	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		try {
			_persistence.findByPrimaryKey(pk);

			Assert.fail(
				"Missing entity did not throw NoSuchActivityLimitException");
		}
		catch (NoSuchActivityLimitException nsee) {
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

	protected OrderByComparator<SocialActivityLimit> getOrderByComparator() {
		return OrderByComparatorFactoryUtil.create("SocialActivityLimit",
			"activityLimitId", true, "groupId", true, "companyId", true,
			"userId", true, "classNameId", true, "classPK", true,
			"activityType", true, "activityCounterName", true, "value", true);
	}

	@Test
	public void testFetchByPrimaryKeyExisting() throws Exception {
		SocialActivityLimit newSocialActivityLimit = addSocialActivityLimit();

		SocialActivityLimit existingSocialActivityLimit = _persistence.fetchByPrimaryKey(newSocialActivityLimit.getPrimaryKey());

		Assert.assertEquals(existingSocialActivityLimit, newSocialActivityLimit);
	}

	@Test
	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		SocialActivityLimit missingSocialActivityLimit = _persistence.fetchByPrimaryKey(pk);

		Assert.assertNull(missingSocialActivityLimit);
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereAllPrimaryKeysExist()
		throws Exception {
		SocialActivityLimit newSocialActivityLimit1 = addSocialActivityLimit();
		SocialActivityLimit newSocialActivityLimit2 = addSocialActivityLimit();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newSocialActivityLimit1.getPrimaryKey());
		primaryKeys.add(newSocialActivityLimit2.getPrimaryKey());

		Map<Serializable, SocialActivityLimit> socialActivityLimits = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(2, socialActivityLimits.size());
		Assert.assertEquals(newSocialActivityLimit1,
			socialActivityLimits.get(newSocialActivityLimit1.getPrimaryKey()));
		Assert.assertEquals(newSocialActivityLimit2,
			socialActivityLimits.get(newSocialActivityLimit2.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereNoPrimaryKeysExist()
		throws Exception {
		long pk1 = RandomTestUtil.nextLong();

		long pk2 = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(pk1);
		primaryKeys.add(pk2);

		Map<Serializable, SocialActivityLimit> socialActivityLimits = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(socialActivityLimits.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereSomePrimaryKeysExist()
		throws Exception {
		SocialActivityLimit newSocialActivityLimit = addSocialActivityLimit();

		long pk = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newSocialActivityLimit.getPrimaryKey());
		primaryKeys.add(pk);

		Map<Serializable, SocialActivityLimit> socialActivityLimits = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, socialActivityLimits.size());
		Assert.assertEquals(newSocialActivityLimit,
			socialActivityLimits.get(newSocialActivityLimit.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithNoPrimaryKeys()
		throws Exception {
		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		Map<Serializable, SocialActivityLimit> socialActivityLimits = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(socialActivityLimits.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithOnePrimaryKey()
		throws Exception {
		SocialActivityLimit newSocialActivityLimit = addSocialActivityLimit();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newSocialActivityLimit.getPrimaryKey());

		Map<Serializable, SocialActivityLimit> socialActivityLimits = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, socialActivityLimits.size());
		Assert.assertEquals(newSocialActivityLimit,
			socialActivityLimits.get(newSocialActivityLimit.getPrimaryKey()));
	}

	@Test
	public void testActionableDynamicQuery() throws Exception {
		final IntegerWrapper count = new IntegerWrapper();

		ActionableDynamicQuery actionableDynamicQuery = SocialActivityLimitLocalServiceUtil.getActionableDynamicQuery();

		actionableDynamicQuery.setPerformActionMethod(new ActionableDynamicQuery.PerformActionMethod() {
				@Override
				public void performAction(Object object) {
					SocialActivityLimit socialActivityLimit = (SocialActivityLimit)object;

					Assert.assertNotNull(socialActivityLimit);

					count.increment();
				}
			});

		actionableDynamicQuery.performActions();

		Assert.assertEquals(count.getValue(), _persistence.countAll());
	}

	@Test
	public void testDynamicQueryByPrimaryKeyExisting()
		throws Exception {
		SocialActivityLimit newSocialActivityLimit = addSocialActivityLimit();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(SocialActivityLimit.class,
				SocialActivityLimit.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("activityLimitId",
				newSocialActivityLimit.getActivityLimitId()));

		List<SocialActivityLimit> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		SocialActivityLimit existingSocialActivityLimit = result.get(0);

		Assert.assertEquals(existingSocialActivityLimit, newSocialActivityLimit);
	}

	@Test
	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(SocialActivityLimit.class,
				SocialActivityLimit.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("activityLimitId",
				RandomTestUtil.nextLong()));

		List<SocialActivityLimit> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testDynamicQueryByProjectionExisting()
		throws Exception {
		SocialActivityLimit newSocialActivityLimit = addSocialActivityLimit();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(SocialActivityLimit.class,
				SocialActivityLimit.class.getClassLoader());

		dynamicQuery.setProjection(ProjectionFactoryUtil.property(
				"activityLimitId"));

		Object newActivityLimitId = newSocialActivityLimit.getActivityLimitId();

		dynamicQuery.add(RestrictionsFactoryUtil.in("activityLimitId",
				new Object[] { newActivityLimitId }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		Object existingActivityLimitId = result.get(0);

		Assert.assertEquals(existingActivityLimitId, newActivityLimitId);
	}

	@Test
	public void testDynamicQueryByProjectionMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(SocialActivityLimit.class,
				SocialActivityLimit.class.getClassLoader());

		dynamicQuery.setProjection(ProjectionFactoryUtil.property(
				"activityLimitId"));

		dynamicQuery.add(RestrictionsFactoryUtil.in("activityLimitId",
				new Object[] { RandomTestUtil.nextLong() }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testResetOriginalValues() throws Exception {
		if (!PropsValues.HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE) {
			return;
		}

		SocialActivityLimit newSocialActivityLimit = addSocialActivityLimit();

		_persistence.clearCache();

		SocialActivityLimitModelImpl existingSocialActivityLimitModelImpl = (SocialActivityLimitModelImpl)_persistence.findByPrimaryKey(newSocialActivityLimit.getPrimaryKey());

		Assert.assertEquals(existingSocialActivityLimitModelImpl.getGroupId(),
			existingSocialActivityLimitModelImpl.getOriginalGroupId());
		Assert.assertEquals(existingSocialActivityLimitModelImpl.getUserId(),
			existingSocialActivityLimitModelImpl.getOriginalUserId());
		Assert.assertEquals(existingSocialActivityLimitModelImpl.getClassNameId(),
			existingSocialActivityLimitModelImpl.getOriginalClassNameId());
		Assert.assertEquals(existingSocialActivityLimitModelImpl.getClassPK(),
			existingSocialActivityLimitModelImpl.getOriginalClassPK());
		Assert.assertEquals(existingSocialActivityLimitModelImpl.getActivityType(),
			existingSocialActivityLimitModelImpl.getOriginalActivityType());
		Assert.assertTrue(Validator.equals(
				existingSocialActivityLimitModelImpl.getActivityCounterName(),
				existingSocialActivityLimitModelImpl.getOriginalActivityCounterName()));
	}

	protected SocialActivityLimit addSocialActivityLimit()
		throws Exception {
		long pk = RandomTestUtil.nextLong();

		SocialActivityLimit socialActivityLimit = _persistence.create(pk);

		socialActivityLimit.setGroupId(RandomTestUtil.nextLong());

		socialActivityLimit.setCompanyId(RandomTestUtil.nextLong());

		socialActivityLimit.setUserId(RandomTestUtil.nextLong());

		socialActivityLimit.setClassNameId(RandomTestUtil.nextLong());

		socialActivityLimit.setClassPK(RandomTestUtil.nextLong());

		socialActivityLimit.setActivityType(RandomTestUtil.nextInt());

		socialActivityLimit.setActivityCounterName(RandomTestUtil.randomString());

		socialActivityLimit.setValue(RandomTestUtil.randomString());

		_socialActivityLimits.add(_persistence.update(socialActivityLimit));

		return socialActivityLimit;
	}

	private List<SocialActivityLimit> _socialActivityLimits = new ArrayList<SocialActivityLimit>();
	private SocialActivityLimitPersistence _persistence;
}