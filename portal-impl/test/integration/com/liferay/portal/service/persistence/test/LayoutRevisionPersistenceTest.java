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
import com.liferay.portal.kernel.exception.NoSuchLayoutRevisionException;
import com.liferay.portal.kernel.model.LayoutRevision;
import com.liferay.portal.kernel.service.LayoutRevisionLocalServiceUtil;
import com.liferay.portal.kernel.service.persistence.LayoutRevisionPersistence;
import com.liferay.portal.kernel.service.persistence.LayoutRevisionUtil;
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
public class LayoutRevisionPersistenceTest {
	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule = new AggregateTestRule(new LiferayIntegrationTestRule(),
			PersistenceTestRule.INSTANCE,
			new TransactionalTestRule(Propagation.REQUIRED));

	@Before
	public void setUp() {
		_persistence = LayoutRevisionUtil.getPersistence();

		Class<?> clazz = _persistence.getClass();

		_dynamicQueryClassLoader = clazz.getClassLoader();
	}

	@After
	public void tearDown() throws Exception {
		Iterator<LayoutRevision> iterator = _layoutRevisions.iterator();

		while (iterator.hasNext()) {
			_persistence.remove(iterator.next());

			iterator.remove();
		}
	}

	@Test
	public void testCreate() throws Exception {
		long pk = RandomTestUtil.nextLong();

		LayoutRevision layoutRevision = _persistence.create(pk);

		Assert.assertNotNull(layoutRevision);

		Assert.assertEquals(layoutRevision.getPrimaryKey(), pk);
	}

	@Test
	public void testRemove() throws Exception {
		LayoutRevision newLayoutRevision = addLayoutRevision();

		_persistence.remove(newLayoutRevision);

		LayoutRevision existingLayoutRevision = _persistence.fetchByPrimaryKey(newLayoutRevision.getPrimaryKey());

		Assert.assertNull(existingLayoutRevision);
	}

	@Test
	public void testUpdateNew() throws Exception {
		addLayoutRevision();
	}

	@Test
	public void testUpdateExisting() throws Exception {
		long pk = RandomTestUtil.nextLong();

		LayoutRevision newLayoutRevision = _persistence.create(pk);

		newLayoutRevision.setMvccVersion(RandomTestUtil.nextLong());

		newLayoutRevision.setGroupId(RandomTestUtil.nextLong());

		newLayoutRevision.setCompanyId(RandomTestUtil.nextLong());

		newLayoutRevision.setUserId(RandomTestUtil.nextLong());

		newLayoutRevision.setUserName(RandomTestUtil.randomString());

		newLayoutRevision.setCreateDate(RandomTestUtil.nextDate());

		newLayoutRevision.setModifiedDate(RandomTestUtil.nextDate());

		newLayoutRevision.setLayoutSetBranchId(RandomTestUtil.nextLong());

		newLayoutRevision.setLayoutBranchId(RandomTestUtil.nextLong());

		newLayoutRevision.setParentLayoutRevisionId(RandomTestUtil.nextLong());

		newLayoutRevision.setHead(RandomTestUtil.randomBoolean());

		newLayoutRevision.setMajor(RandomTestUtil.randomBoolean());

		newLayoutRevision.setPlid(RandomTestUtil.nextLong());

		newLayoutRevision.setPrivateLayout(RandomTestUtil.randomBoolean());

		newLayoutRevision.setName(RandomTestUtil.randomString());

		newLayoutRevision.setTitle(RandomTestUtil.randomString());

		newLayoutRevision.setDescription(RandomTestUtil.randomString());

		newLayoutRevision.setKeywords(RandomTestUtil.randomString());

		newLayoutRevision.setRobots(RandomTestUtil.randomString());

		newLayoutRevision.setTypeSettings(RandomTestUtil.randomString());

		newLayoutRevision.setIconImageId(RandomTestUtil.nextLong());

		newLayoutRevision.setThemeId(RandomTestUtil.randomString());

		newLayoutRevision.setColorSchemeId(RandomTestUtil.randomString());

		newLayoutRevision.setCss(RandomTestUtil.randomString());

		newLayoutRevision.setStatus(RandomTestUtil.nextInt());

		newLayoutRevision.setStatusByUserId(RandomTestUtil.nextLong());

		newLayoutRevision.setStatusByUserName(RandomTestUtil.randomString());

		newLayoutRevision.setStatusDate(RandomTestUtil.nextDate());

		_layoutRevisions.add(_persistence.update(newLayoutRevision));

		LayoutRevision existingLayoutRevision = _persistence.findByPrimaryKey(newLayoutRevision.getPrimaryKey());

		Assert.assertEquals(existingLayoutRevision.getMvccVersion(),
			newLayoutRevision.getMvccVersion());
		Assert.assertEquals(existingLayoutRevision.getLayoutRevisionId(),
			newLayoutRevision.getLayoutRevisionId());
		Assert.assertEquals(existingLayoutRevision.getGroupId(),
			newLayoutRevision.getGroupId());
		Assert.assertEquals(existingLayoutRevision.getCompanyId(),
			newLayoutRevision.getCompanyId());
		Assert.assertEquals(existingLayoutRevision.getUserId(),
			newLayoutRevision.getUserId());
		Assert.assertEquals(existingLayoutRevision.getUserName(),
			newLayoutRevision.getUserName());
		Assert.assertEquals(Time.getShortTimestamp(
				existingLayoutRevision.getCreateDate()),
			Time.getShortTimestamp(newLayoutRevision.getCreateDate()));
		Assert.assertEquals(Time.getShortTimestamp(
				existingLayoutRevision.getModifiedDate()),
			Time.getShortTimestamp(newLayoutRevision.getModifiedDate()));
		Assert.assertEquals(existingLayoutRevision.getLayoutSetBranchId(),
			newLayoutRevision.getLayoutSetBranchId());
		Assert.assertEquals(existingLayoutRevision.getLayoutBranchId(),
			newLayoutRevision.getLayoutBranchId());
		Assert.assertEquals(existingLayoutRevision.getParentLayoutRevisionId(),
			newLayoutRevision.getParentLayoutRevisionId());
		Assert.assertEquals(existingLayoutRevision.getHead(),
			newLayoutRevision.getHead());
		Assert.assertEquals(existingLayoutRevision.getMajor(),
			newLayoutRevision.getMajor());
		Assert.assertEquals(existingLayoutRevision.getPlid(),
			newLayoutRevision.getPlid());
		Assert.assertEquals(existingLayoutRevision.getPrivateLayout(),
			newLayoutRevision.getPrivateLayout());
		Assert.assertEquals(existingLayoutRevision.getName(),
			newLayoutRevision.getName());
		Assert.assertEquals(existingLayoutRevision.getTitle(),
			newLayoutRevision.getTitle());
		Assert.assertEquals(existingLayoutRevision.getDescription(),
			newLayoutRevision.getDescription());
		Assert.assertEquals(existingLayoutRevision.getKeywords(),
			newLayoutRevision.getKeywords());
		Assert.assertEquals(existingLayoutRevision.getRobots(),
			newLayoutRevision.getRobots());
		Assert.assertEquals(existingLayoutRevision.getTypeSettings(),
			newLayoutRevision.getTypeSettings());
		Assert.assertEquals(existingLayoutRevision.getIconImageId(),
			newLayoutRevision.getIconImageId());
		Assert.assertEquals(existingLayoutRevision.getThemeId(),
			newLayoutRevision.getThemeId());
		Assert.assertEquals(existingLayoutRevision.getColorSchemeId(),
			newLayoutRevision.getColorSchemeId());
		Assert.assertEquals(existingLayoutRevision.getCss(),
			newLayoutRevision.getCss());
		Assert.assertEquals(existingLayoutRevision.getStatus(),
			newLayoutRevision.getStatus());
		Assert.assertEquals(existingLayoutRevision.getStatusByUserId(),
			newLayoutRevision.getStatusByUserId());
		Assert.assertEquals(existingLayoutRevision.getStatusByUserName(),
			newLayoutRevision.getStatusByUserName());
		Assert.assertEquals(Time.getShortTimestamp(
				existingLayoutRevision.getStatusDate()),
			Time.getShortTimestamp(newLayoutRevision.getStatusDate()));
	}

	@Test
	public void testCountByLayoutSetBranchId() throws Exception {
		_persistence.countByLayoutSetBranchId(RandomTestUtil.nextLong());

		_persistence.countByLayoutSetBranchId(0L);
	}

	@Test
	public void testCountByPlid() throws Exception {
		_persistence.countByPlid(RandomTestUtil.nextLong());

		_persistence.countByPlid(0L);
	}

	@Test
	public void testCountByL_H() throws Exception {
		_persistence.countByL_H(RandomTestUtil.nextLong(),
			RandomTestUtil.randomBoolean());

		_persistence.countByL_H(0L, RandomTestUtil.randomBoolean());
	}

	@Test
	public void testCountByL_P() throws Exception {
		_persistence.countByL_P(RandomTestUtil.nextLong(),
			RandomTestUtil.nextLong());

		_persistence.countByL_P(0L, 0L);
	}

	@Test
	public void testCountByL_S() throws Exception {
		_persistence.countByL_S(RandomTestUtil.nextLong(),
			RandomTestUtil.nextInt());

		_persistence.countByL_S(0L, 0);
	}

	@Test
	public void testCountByH_P() throws Exception {
		_persistence.countByH_P(RandomTestUtil.randomBoolean(),
			RandomTestUtil.nextLong());

		_persistence.countByH_P(RandomTestUtil.randomBoolean(), 0L);
	}

	@Test
	public void testCountByP_NotS() throws Exception {
		_persistence.countByP_NotS(RandomTestUtil.nextLong(),
			RandomTestUtil.nextInt());

		_persistence.countByP_NotS(0L, 0);
	}

	@Test
	public void testCountByL_L_P() throws Exception {
		_persistence.countByL_L_P(RandomTestUtil.nextLong(),
			RandomTestUtil.nextLong(), RandomTestUtil.nextLong());

		_persistence.countByL_L_P(0L, 0L, 0L);
	}

	@Test
	public void testCountByL_P_P() throws Exception {
		_persistence.countByL_P_P(RandomTestUtil.nextLong(),
			RandomTestUtil.nextLong(), RandomTestUtil.nextLong());

		_persistence.countByL_P_P(0L, 0L, 0L);
	}

	@Test
	public void testCountByL_H_P() throws Exception {
		_persistence.countByL_H_P(RandomTestUtil.nextLong(),
			RandomTestUtil.randomBoolean(), RandomTestUtil.nextLong());

		_persistence.countByL_H_P(0L, RandomTestUtil.randomBoolean(), 0L);
	}

	@Test
	public void testCountByL_H_P_All() throws Exception {
		_persistence.countByL_H_P_All(RandomTestUtil.nextLong(),
			RandomTestUtil.randomBoolean(), RandomTestUtil.nextLong());

		_persistence.countByL_H_P_All(0L, RandomTestUtil.randomBoolean(), 0L);
	}

	@Test
	public void testCountByL_P_S() throws Exception {
		_persistence.countByL_P_S(RandomTestUtil.nextLong(),
			RandomTestUtil.nextLong(), RandomTestUtil.nextInt());

		_persistence.countByL_P_S(0L, 0L, 0);
	}

	@Test
	public void testCountByL_L_H_P() throws Exception {
		_persistence.countByL_L_H_P(RandomTestUtil.nextLong(),
			RandomTestUtil.nextLong(), RandomTestUtil.randomBoolean(),
			RandomTestUtil.nextLong());

		_persistence.countByL_L_H_P(0L, 0L, RandomTestUtil.randomBoolean(), 0L);
	}

	@Test
	public void testFindByPrimaryKeyExisting() throws Exception {
		LayoutRevision newLayoutRevision = addLayoutRevision();

		LayoutRevision existingLayoutRevision = _persistence.findByPrimaryKey(newLayoutRevision.getPrimaryKey());

		Assert.assertEquals(existingLayoutRevision, newLayoutRevision);
	}

	@Test(expected = NoSuchLayoutRevisionException.class)
	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		_persistence.findByPrimaryKey(pk);
	}

	@Test
	public void testFindAll() throws Exception {
		_persistence.findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			getOrderByComparator());
	}

	protected OrderByComparator<LayoutRevision> getOrderByComparator() {
		return OrderByComparatorFactoryUtil.create("LayoutRevision",
			"mvccVersion", true, "layoutRevisionId", true, "groupId", true,
			"companyId", true, "userId", true, "userName", true, "createDate",
			true, "modifiedDate", true, "layoutSetBranchId", true,
			"layoutBranchId", true, "parentLayoutRevisionId", true, "head",
			true, "major", true, "plid", true, "privateLayout", true, "name",
			true, "title", true, "description", true, "keywords", true,
			"robots", true, "iconImageId", true, "themeId", true,
			"colorSchemeId", true, "status", true, "statusByUserId", true,
			"statusByUserName", true, "statusDate", true);
	}

	@Test
	public void testFetchByPrimaryKeyExisting() throws Exception {
		LayoutRevision newLayoutRevision = addLayoutRevision();

		LayoutRevision existingLayoutRevision = _persistence.fetchByPrimaryKey(newLayoutRevision.getPrimaryKey());

		Assert.assertEquals(existingLayoutRevision, newLayoutRevision);
	}

	@Test
	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		LayoutRevision missingLayoutRevision = _persistence.fetchByPrimaryKey(pk);

		Assert.assertNull(missingLayoutRevision);
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereAllPrimaryKeysExist()
		throws Exception {
		LayoutRevision newLayoutRevision1 = addLayoutRevision();
		LayoutRevision newLayoutRevision2 = addLayoutRevision();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newLayoutRevision1.getPrimaryKey());
		primaryKeys.add(newLayoutRevision2.getPrimaryKey());

		Map<Serializable, LayoutRevision> layoutRevisions = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(2, layoutRevisions.size());
		Assert.assertEquals(newLayoutRevision1,
			layoutRevisions.get(newLayoutRevision1.getPrimaryKey()));
		Assert.assertEquals(newLayoutRevision2,
			layoutRevisions.get(newLayoutRevision2.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereNoPrimaryKeysExist()
		throws Exception {
		long pk1 = RandomTestUtil.nextLong();

		long pk2 = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(pk1);
		primaryKeys.add(pk2);

		Map<Serializable, LayoutRevision> layoutRevisions = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(layoutRevisions.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereSomePrimaryKeysExist()
		throws Exception {
		LayoutRevision newLayoutRevision = addLayoutRevision();

		long pk = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newLayoutRevision.getPrimaryKey());
		primaryKeys.add(pk);

		Map<Serializable, LayoutRevision> layoutRevisions = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, layoutRevisions.size());
		Assert.assertEquals(newLayoutRevision,
			layoutRevisions.get(newLayoutRevision.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithNoPrimaryKeys()
		throws Exception {
		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		Map<Serializable, LayoutRevision> layoutRevisions = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(layoutRevisions.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithOnePrimaryKey()
		throws Exception {
		LayoutRevision newLayoutRevision = addLayoutRevision();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newLayoutRevision.getPrimaryKey());

		Map<Serializable, LayoutRevision> layoutRevisions = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, layoutRevisions.size());
		Assert.assertEquals(newLayoutRevision,
			layoutRevisions.get(newLayoutRevision.getPrimaryKey()));
	}

	@Test
	public void testActionableDynamicQuery() throws Exception {
		final IntegerWrapper count = new IntegerWrapper();

		ActionableDynamicQuery actionableDynamicQuery = LayoutRevisionLocalServiceUtil.getActionableDynamicQuery();

		actionableDynamicQuery.setPerformActionMethod(new ActionableDynamicQuery.PerformActionMethod<LayoutRevision>() {
				@Override
				public void performAction(LayoutRevision layoutRevision) {
					Assert.assertNotNull(layoutRevision);

					count.increment();
				}
			});

		actionableDynamicQuery.performActions();

		Assert.assertEquals(count.getValue(), _persistence.countAll());
	}

	@Test
	public void testDynamicQueryByPrimaryKeyExisting()
		throws Exception {
		LayoutRevision newLayoutRevision = addLayoutRevision();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(LayoutRevision.class,
				_dynamicQueryClassLoader);

		dynamicQuery.add(RestrictionsFactoryUtil.eq("layoutRevisionId",
				newLayoutRevision.getLayoutRevisionId()));

		List<LayoutRevision> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		LayoutRevision existingLayoutRevision = result.get(0);

		Assert.assertEquals(existingLayoutRevision, newLayoutRevision);
	}

	@Test
	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(LayoutRevision.class,
				_dynamicQueryClassLoader);

		dynamicQuery.add(RestrictionsFactoryUtil.eq("layoutRevisionId",
				RandomTestUtil.nextLong()));

		List<LayoutRevision> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testDynamicQueryByProjectionExisting()
		throws Exception {
		LayoutRevision newLayoutRevision = addLayoutRevision();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(LayoutRevision.class,
				_dynamicQueryClassLoader);

		dynamicQuery.setProjection(ProjectionFactoryUtil.property(
				"layoutRevisionId"));

		Object newLayoutRevisionId = newLayoutRevision.getLayoutRevisionId();

		dynamicQuery.add(RestrictionsFactoryUtil.in("layoutRevisionId",
				new Object[] { newLayoutRevisionId }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		Object existingLayoutRevisionId = result.get(0);

		Assert.assertEquals(existingLayoutRevisionId, newLayoutRevisionId);
	}

	@Test
	public void testDynamicQueryByProjectionMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(LayoutRevision.class,
				_dynamicQueryClassLoader);

		dynamicQuery.setProjection(ProjectionFactoryUtil.property(
				"layoutRevisionId"));

		dynamicQuery.add(RestrictionsFactoryUtil.in("layoutRevisionId",
				new Object[] { RandomTestUtil.nextLong() }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testResetOriginalValues() throws Exception {
		LayoutRevision newLayoutRevision = addLayoutRevision();

		_persistence.clearCache();

		LayoutRevision existingLayoutRevision = _persistence.findByPrimaryKey(newLayoutRevision.getPrimaryKey());

		Assert.assertEquals(Long.valueOf(
				existingLayoutRevision.getLayoutSetBranchId()),
			ReflectionTestUtil.<Long>invoke(existingLayoutRevision,
				"getOriginalLayoutSetBranchId", new Class<?>[0]));
		Assert.assertEquals(Boolean.valueOf(existingLayoutRevision.getHead()),
			ReflectionTestUtil.<Boolean>invoke(existingLayoutRevision,
				"getOriginalHead", new Class<?>[0]));
		Assert.assertEquals(Long.valueOf(existingLayoutRevision.getPlid()),
			ReflectionTestUtil.<Long>invoke(existingLayoutRevision,
				"getOriginalPlid", new Class<?>[0]));

		Assert.assertEquals(Long.valueOf(
				existingLayoutRevision.getLayoutSetBranchId()),
			ReflectionTestUtil.<Long>invoke(existingLayoutRevision,
				"getOriginalLayoutSetBranchId", new Class<?>[0]));
		Assert.assertEquals(Long.valueOf(
				existingLayoutRevision.getLayoutBranchId()),
			ReflectionTestUtil.<Long>invoke(existingLayoutRevision,
				"getOriginalLayoutBranchId", new Class<?>[0]));
		Assert.assertEquals(Boolean.valueOf(existingLayoutRevision.getHead()),
			ReflectionTestUtil.<Boolean>invoke(existingLayoutRevision,
				"getOriginalHead", new Class<?>[0]));
		Assert.assertEquals(Long.valueOf(existingLayoutRevision.getPlid()),
			ReflectionTestUtil.<Long>invoke(existingLayoutRevision,
				"getOriginalPlid", new Class<?>[0]));
	}

	protected LayoutRevision addLayoutRevision() throws Exception {
		long pk = RandomTestUtil.nextLong();

		LayoutRevision layoutRevision = _persistence.create(pk);

		layoutRevision.setMvccVersion(RandomTestUtil.nextLong());

		layoutRevision.setGroupId(RandomTestUtil.nextLong());

		layoutRevision.setCompanyId(RandomTestUtil.nextLong());

		layoutRevision.setUserId(RandomTestUtil.nextLong());

		layoutRevision.setUserName(RandomTestUtil.randomString());

		layoutRevision.setCreateDate(RandomTestUtil.nextDate());

		layoutRevision.setModifiedDate(RandomTestUtil.nextDate());

		layoutRevision.setLayoutSetBranchId(RandomTestUtil.nextLong());

		layoutRevision.setLayoutBranchId(RandomTestUtil.nextLong());

		layoutRevision.setParentLayoutRevisionId(RandomTestUtil.nextLong());

		layoutRevision.setHead(RandomTestUtil.randomBoolean());

		layoutRevision.setMajor(RandomTestUtil.randomBoolean());

		layoutRevision.setPlid(RandomTestUtil.nextLong());

		layoutRevision.setPrivateLayout(RandomTestUtil.randomBoolean());

		layoutRevision.setName(RandomTestUtil.randomString());

		layoutRevision.setTitle(RandomTestUtil.randomString());

		layoutRevision.setDescription(RandomTestUtil.randomString());

		layoutRevision.setKeywords(RandomTestUtil.randomString());

		layoutRevision.setRobots(RandomTestUtil.randomString());

		layoutRevision.setTypeSettings(RandomTestUtil.randomString());

		layoutRevision.setIconImageId(RandomTestUtil.nextLong());

		layoutRevision.setThemeId(RandomTestUtil.randomString());

		layoutRevision.setColorSchemeId(RandomTestUtil.randomString());

		layoutRevision.setCss(RandomTestUtil.randomString());

		layoutRevision.setStatus(RandomTestUtil.nextInt());

		layoutRevision.setStatusByUserId(RandomTestUtil.nextLong());

		layoutRevision.setStatusByUserName(RandomTestUtil.randomString());

		layoutRevision.setStatusDate(RandomTestUtil.nextDate());

		_layoutRevisions.add(_persistence.update(layoutRevision));

		return layoutRevision;
	}

	private List<LayoutRevision> _layoutRevisions = new ArrayList<LayoutRevision>();
	private LayoutRevisionPersistence _persistence;
	private ClassLoader _dynamicQueryClassLoader;
}