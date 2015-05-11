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

package com.liferay.portlet.social.service.persistence.test;

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
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PersistenceTestRule;

import com.liferay.portlet.social.NoSuchRelationException;
import com.liferay.portlet.social.model.SocialRelation;
import com.liferay.portlet.social.service.SocialRelationLocalServiceUtil;
import com.liferay.portlet.social.service.persistence.SocialRelationPersistence;
import com.liferay.portlet.social.service.persistence.SocialRelationUtil;

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
public class SocialRelationPersistenceTest {
	@Rule
	public final AggregateTestRule aggregateTestRule = new AggregateTestRule(new LiferayIntegrationTestRule(),
			PersistenceTestRule.INSTANCE,
			new TransactionalTestRule(Propagation.REQUIRED));

	@Before
	public void setUp() {
		_persistence = SocialRelationUtil.getPersistence();

		Class<?> clazz = _persistence.getClass();

		_dynamicQueryClassLoader = clazz.getClassLoader();
	}

	@After
	public void tearDown() throws Exception {
		Iterator<SocialRelation> iterator = _socialRelations.iterator();

		while (iterator.hasNext()) {
			_persistence.remove(iterator.next());

			iterator.remove();
		}
	}

	@Test
	public void testCreate() throws Exception {
		long pk = RandomTestUtil.nextLong();

		SocialRelation socialRelation = _persistence.create(pk);

		Assert.assertNotNull(socialRelation);

		Assert.assertEquals(socialRelation.getPrimaryKey(), pk);
	}

	@Test
	public void testRemove() throws Exception {
		SocialRelation newSocialRelation = addSocialRelation();

		_persistence.remove(newSocialRelation);

		SocialRelation existingSocialRelation = _persistence.fetchByPrimaryKey(newSocialRelation.getPrimaryKey());

		Assert.assertNull(existingSocialRelation);
	}

	@Test
	public void testUpdateNew() throws Exception {
		addSocialRelation();
	}

	@Test
	public void testUpdateExisting() throws Exception {
		long pk = RandomTestUtil.nextLong();

		SocialRelation newSocialRelation = _persistence.create(pk);

		newSocialRelation.setUuid(RandomTestUtil.randomString());

		newSocialRelation.setCompanyId(RandomTestUtil.nextLong());

		newSocialRelation.setCreateDate(RandomTestUtil.nextLong());

		newSocialRelation.setUserId1(RandomTestUtil.nextLong());

		newSocialRelation.setUserId2(RandomTestUtil.nextLong());

		newSocialRelation.setType(RandomTestUtil.nextInt());

		_socialRelations.add(_persistence.update(newSocialRelation));

		SocialRelation existingSocialRelation = _persistence.findByPrimaryKey(newSocialRelation.getPrimaryKey());

		Assert.assertEquals(existingSocialRelation.getUuid(),
			newSocialRelation.getUuid());
		Assert.assertEquals(existingSocialRelation.getRelationId(),
			newSocialRelation.getRelationId());
		Assert.assertEquals(existingSocialRelation.getCompanyId(),
			newSocialRelation.getCompanyId());
		Assert.assertEquals(existingSocialRelation.getCreateDate(),
			newSocialRelation.getCreateDate());
		Assert.assertEquals(existingSocialRelation.getUserId1(),
			newSocialRelation.getUserId1());
		Assert.assertEquals(existingSocialRelation.getUserId2(),
			newSocialRelation.getUserId2());
		Assert.assertEquals(existingSocialRelation.getType(),
			newSocialRelation.getType());
	}

	@Test
	public void testCountByUuid() throws Exception {
		_persistence.countByUuid(StringPool.BLANK);

		_persistence.countByUuid(StringPool.NULL);

		_persistence.countByUuid((String)null);
	}

	@Test
	public void testCountByUuid_C() throws Exception {
		_persistence.countByUuid_C(StringPool.BLANK, RandomTestUtil.nextLong());

		_persistence.countByUuid_C(StringPool.NULL, 0L);

		_persistence.countByUuid_C((String)null, 0L);
	}

	@Test
	public void testCountByCompanyId() throws Exception {
		_persistence.countByCompanyId(RandomTestUtil.nextLong());

		_persistence.countByCompanyId(0L);
	}

	@Test
	public void testCountByUserId1() throws Exception {
		_persistence.countByUserId1(RandomTestUtil.nextLong());

		_persistence.countByUserId1(0L);
	}

	@Test
	public void testCountByUserId2() throws Exception {
		_persistence.countByUserId2(RandomTestUtil.nextLong());

		_persistence.countByUserId2(0L);
	}

	@Test
	public void testCountByType() throws Exception {
		_persistence.countByType(RandomTestUtil.nextInt());

		_persistence.countByType(0);
	}

	@Test
	public void testCountByC_T() throws Exception {
		_persistence.countByC_T(RandomTestUtil.nextLong(),
			RandomTestUtil.nextInt());

		_persistence.countByC_T(0L, 0);
	}

	@Test
	public void testCountByU1_U2() throws Exception {
		_persistence.countByU1_U2(RandomTestUtil.nextLong(),
			RandomTestUtil.nextLong());

		_persistence.countByU1_U2(0L, 0L);
	}

	@Test
	public void testCountByU1_T() throws Exception {
		_persistence.countByU1_T(RandomTestUtil.nextLong(),
			RandomTestUtil.nextInt());

		_persistence.countByU1_T(0L, 0);
	}

	@Test
	public void testCountByU2_T() throws Exception {
		_persistence.countByU2_T(RandomTestUtil.nextLong(),
			RandomTestUtil.nextInt());

		_persistence.countByU2_T(0L, 0);
	}

	@Test
	public void testCountByU1_U2_T() throws Exception {
		_persistence.countByU1_U2_T(RandomTestUtil.nextLong(),
			RandomTestUtil.nextLong(), RandomTestUtil.nextInt());

		_persistence.countByU1_U2_T(0L, 0L, 0);
	}

	@Test
	public void testFindByPrimaryKeyExisting() throws Exception {
		SocialRelation newSocialRelation = addSocialRelation();

		SocialRelation existingSocialRelation = _persistence.findByPrimaryKey(newSocialRelation.getPrimaryKey());

		Assert.assertEquals(existingSocialRelation, newSocialRelation);
	}

	@Test(expected = NoSuchRelationException.class)
	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		_persistence.findByPrimaryKey(pk);
	}

	@Test
	public void testFindAll() throws Exception {
		_persistence.findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			getOrderByComparator());
	}

	protected OrderByComparator<SocialRelation> getOrderByComparator() {
		return OrderByComparatorFactoryUtil.create("SocialRelation", "uuid",
			true, "relationId", true, "companyId", true, "createDate", true,
			"userId1", true, "userId2", true, "type", true);
	}

	@Test
	public void testFetchByPrimaryKeyExisting() throws Exception {
		SocialRelation newSocialRelation = addSocialRelation();

		SocialRelation existingSocialRelation = _persistence.fetchByPrimaryKey(newSocialRelation.getPrimaryKey());

		Assert.assertEquals(existingSocialRelation, newSocialRelation);
	}

	@Test
	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		SocialRelation missingSocialRelation = _persistence.fetchByPrimaryKey(pk);

		Assert.assertNull(missingSocialRelation);
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereAllPrimaryKeysExist()
		throws Exception {
		SocialRelation newSocialRelation1 = addSocialRelation();
		SocialRelation newSocialRelation2 = addSocialRelation();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newSocialRelation1.getPrimaryKey());
		primaryKeys.add(newSocialRelation2.getPrimaryKey());

		Map<Serializable, SocialRelation> socialRelations = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(2, socialRelations.size());
		Assert.assertEquals(newSocialRelation1,
			socialRelations.get(newSocialRelation1.getPrimaryKey()));
		Assert.assertEquals(newSocialRelation2,
			socialRelations.get(newSocialRelation2.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereNoPrimaryKeysExist()
		throws Exception {
		long pk1 = RandomTestUtil.nextLong();

		long pk2 = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(pk1);
		primaryKeys.add(pk2);

		Map<Serializable, SocialRelation> socialRelations = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(socialRelations.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereSomePrimaryKeysExist()
		throws Exception {
		SocialRelation newSocialRelation = addSocialRelation();

		long pk = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newSocialRelation.getPrimaryKey());
		primaryKeys.add(pk);

		Map<Serializable, SocialRelation> socialRelations = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, socialRelations.size());
		Assert.assertEquals(newSocialRelation,
			socialRelations.get(newSocialRelation.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithNoPrimaryKeys()
		throws Exception {
		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		Map<Serializable, SocialRelation> socialRelations = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(socialRelations.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithOnePrimaryKey()
		throws Exception {
		SocialRelation newSocialRelation = addSocialRelation();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newSocialRelation.getPrimaryKey());

		Map<Serializable, SocialRelation> socialRelations = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, socialRelations.size());
		Assert.assertEquals(newSocialRelation,
			socialRelations.get(newSocialRelation.getPrimaryKey()));
	}

	@Test
	public void testActionableDynamicQuery() throws Exception {
		final IntegerWrapper count = new IntegerWrapper();

		ActionableDynamicQuery actionableDynamicQuery = SocialRelationLocalServiceUtil.getActionableDynamicQuery();

		actionableDynamicQuery.setPerformActionMethod(new ActionableDynamicQuery.PerformActionMethod() {
				@Override
				public void performAction(Object object) {
					SocialRelation socialRelation = (SocialRelation)object;

					Assert.assertNotNull(socialRelation);

					count.increment();
				}
			});

		actionableDynamicQuery.performActions();

		Assert.assertEquals(count.getValue(), _persistence.countAll());
	}

	@Test
	public void testDynamicQueryByPrimaryKeyExisting()
		throws Exception {
		SocialRelation newSocialRelation = addSocialRelation();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(SocialRelation.class,
				_dynamicQueryClassLoader);

		dynamicQuery.add(RestrictionsFactoryUtil.eq("relationId",
				newSocialRelation.getRelationId()));

		List<SocialRelation> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		SocialRelation existingSocialRelation = result.get(0);

		Assert.assertEquals(existingSocialRelation, newSocialRelation);
	}

	@Test
	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(SocialRelation.class,
				_dynamicQueryClassLoader);

		dynamicQuery.add(RestrictionsFactoryUtil.eq("relationId",
				RandomTestUtil.nextLong()));

		List<SocialRelation> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testDynamicQueryByProjectionExisting()
		throws Exception {
		SocialRelation newSocialRelation = addSocialRelation();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(SocialRelation.class,
				_dynamicQueryClassLoader);

		dynamicQuery.setProjection(ProjectionFactoryUtil.property("relationId"));

		Object newRelationId = newSocialRelation.getRelationId();

		dynamicQuery.add(RestrictionsFactoryUtil.in("relationId",
				new Object[] { newRelationId }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		Object existingRelationId = result.get(0);

		Assert.assertEquals(existingRelationId, newRelationId);
	}

	@Test
	public void testDynamicQueryByProjectionMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(SocialRelation.class,
				_dynamicQueryClassLoader);

		dynamicQuery.setProjection(ProjectionFactoryUtil.property("relationId"));

		dynamicQuery.add(RestrictionsFactoryUtil.in("relationId",
				new Object[] { RandomTestUtil.nextLong() }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testResetOriginalValues() throws Exception {
		SocialRelation newSocialRelation = addSocialRelation();

		_persistence.clearCache();

		SocialRelation existingSocialRelation = _persistence.findByPrimaryKey(newSocialRelation.getPrimaryKey());

		Assert.assertEquals(existingSocialRelation.getUserId1(),
			ReflectionTestUtil.invoke(existingSocialRelation,
				"getOriginalUserId1", new Class<?>[0]));
		Assert.assertEquals(existingSocialRelation.getUserId2(),
			ReflectionTestUtil.invoke(existingSocialRelation,
				"getOriginalUserId2", new Class<?>[0]));
		Assert.assertEquals(existingSocialRelation.getType(),
			ReflectionTestUtil.invoke(existingSocialRelation,
				"getOriginalType", new Class<?>[0]));
	}

	protected SocialRelation addSocialRelation() throws Exception {
		long pk = RandomTestUtil.nextLong();

		SocialRelation socialRelation = _persistence.create(pk);

		socialRelation.setUuid(RandomTestUtil.randomString());

		socialRelation.setCompanyId(RandomTestUtil.nextLong());

		socialRelation.setCreateDate(RandomTestUtil.nextLong());

		socialRelation.setUserId1(RandomTestUtil.nextLong());

		socialRelation.setUserId2(RandomTestUtil.nextLong());

		socialRelation.setType(RandomTestUtil.nextInt());

		_socialRelations.add(_persistence.update(socialRelation));

		return socialRelation;
	}

	private List<SocialRelation> _socialRelations = new ArrayList<SocialRelation>();
	private SocialRelationPersistence _persistence;
	private ClassLoader _dynamicQueryClassLoader;
}