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
import com.liferay.portal.kernel.exception.NoSuchLayoutSetException;
import com.liferay.portal.kernel.model.LayoutSet;
import com.liferay.portal.kernel.service.LayoutSetLocalServiceUtil;
import com.liferay.portal.kernel.service.persistence.LayoutSetPersistence;
import com.liferay.portal.kernel.service.persistence.LayoutSetUtil;
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
public class LayoutSetPersistenceTest {
	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule = new AggregateTestRule(new LiferayIntegrationTestRule(),
			PersistenceTestRule.INSTANCE,
			new TransactionalTestRule(Propagation.REQUIRED));

	@Before
	public void setUp() {
		_persistence = LayoutSetUtil.getPersistence();

		Class<?> clazz = _persistence.getClass();

		_dynamicQueryClassLoader = clazz.getClassLoader();
	}

	@After
	public void tearDown() throws Exception {
		Iterator<LayoutSet> iterator = _layoutSets.iterator();

		while (iterator.hasNext()) {
			_persistence.remove(iterator.next());

			iterator.remove();
		}
	}

	@Test
	public void testCreate() throws Exception {
		long pk = RandomTestUtil.nextLong();

		LayoutSet layoutSet = _persistence.create(pk);

		Assert.assertNotNull(layoutSet);

		Assert.assertEquals(layoutSet.getPrimaryKey(), pk);
	}

	@Test
	public void testRemove() throws Exception {
		LayoutSet newLayoutSet = addLayoutSet();

		_persistence.remove(newLayoutSet);

		LayoutSet existingLayoutSet = _persistence.fetchByPrimaryKey(newLayoutSet.getPrimaryKey());

		Assert.assertNull(existingLayoutSet);
	}

	@Test
	public void testUpdateNew() throws Exception {
		addLayoutSet();
	}

	@Test
	public void testUpdateExisting() throws Exception {
		long pk = RandomTestUtil.nextLong();

		LayoutSet newLayoutSet = _persistence.create(pk);

		newLayoutSet.setMvccVersion(RandomTestUtil.nextLong());

		newLayoutSet.setGroupId(RandomTestUtil.nextLong());

		newLayoutSet.setCompanyId(RandomTestUtil.nextLong());

		newLayoutSet.setCreateDate(RandomTestUtil.nextDate());

		newLayoutSet.setModifiedDate(RandomTestUtil.nextDate());

		newLayoutSet.setPrivateLayout(RandomTestUtil.randomBoolean());

		newLayoutSet.setLogoId(RandomTestUtil.nextLong());

		newLayoutSet.setThemeId(RandomTestUtil.randomString());

		newLayoutSet.setColorSchemeId(RandomTestUtil.randomString());

		newLayoutSet.setCss(RandomTestUtil.randomString());

		newLayoutSet.setPageCount(RandomTestUtil.nextInt());

		newLayoutSet.setSettings(RandomTestUtil.randomString());

		newLayoutSet.setLayoutSetPrototypeUuid(RandomTestUtil.randomString());

		newLayoutSet.setLayoutSetPrototypeLinkEnabled(RandomTestUtil.randomBoolean());

		_layoutSets.add(_persistence.update(newLayoutSet));

		LayoutSet existingLayoutSet = _persistence.findByPrimaryKey(newLayoutSet.getPrimaryKey());

		Assert.assertEquals(existingLayoutSet.getMvccVersion(),
			newLayoutSet.getMvccVersion());
		Assert.assertEquals(existingLayoutSet.getLayoutSetId(),
			newLayoutSet.getLayoutSetId());
		Assert.assertEquals(existingLayoutSet.getGroupId(),
			newLayoutSet.getGroupId());
		Assert.assertEquals(existingLayoutSet.getCompanyId(),
			newLayoutSet.getCompanyId());
		Assert.assertEquals(Time.getShortTimestamp(
				existingLayoutSet.getCreateDate()),
			Time.getShortTimestamp(newLayoutSet.getCreateDate()));
		Assert.assertEquals(Time.getShortTimestamp(
				existingLayoutSet.getModifiedDate()),
			Time.getShortTimestamp(newLayoutSet.getModifiedDate()));
		Assert.assertEquals(existingLayoutSet.getPrivateLayout(),
			newLayoutSet.getPrivateLayout());
		Assert.assertEquals(existingLayoutSet.getLogoId(),
			newLayoutSet.getLogoId());
		Assert.assertEquals(existingLayoutSet.getThemeId(),
			newLayoutSet.getThemeId());
		Assert.assertEquals(existingLayoutSet.getColorSchemeId(),
			newLayoutSet.getColorSchemeId());
		Assert.assertEquals(existingLayoutSet.getCss(), newLayoutSet.getCss());
		Assert.assertEquals(existingLayoutSet.getPageCount(),
			newLayoutSet.getPageCount());
		Assert.assertEquals(existingLayoutSet.getSettings(),
			newLayoutSet.getSettings());
		Assert.assertEquals(existingLayoutSet.getLayoutSetPrototypeUuid(),
			newLayoutSet.getLayoutSetPrototypeUuid());
		Assert.assertEquals(existingLayoutSet.getLayoutSetPrototypeLinkEnabled(),
			newLayoutSet.getLayoutSetPrototypeLinkEnabled());
	}

	@Test
	public void testCountByGroupId() throws Exception {
		_persistence.countByGroupId(RandomTestUtil.nextLong());

		_persistence.countByGroupId(0L);
	}

	@Test
	public void testCountByLayoutSetPrototypeUuid() throws Exception {
		_persistence.countByLayoutSetPrototypeUuid(StringPool.BLANK);

		_persistence.countByLayoutSetPrototypeUuid(StringPool.NULL);

		_persistence.countByLayoutSetPrototypeUuid((String)null);
	}

	@Test
	public void testCountByG_P() throws Exception {
		_persistence.countByG_P(RandomTestUtil.nextLong(),
			RandomTestUtil.randomBoolean());

		_persistence.countByG_P(0L, RandomTestUtil.randomBoolean());
	}

	@Test
	public void testCountByP_L() throws Exception {
		_persistence.countByP_L(RandomTestUtil.randomBoolean(),
			RandomTestUtil.nextLong());

		_persistence.countByP_L(RandomTestUtil.randomBoolean(), 0L);
	}

	@Test
	public void testFindByPrimaryKeyExisting() throws Exception {
		LayoutSet newLayoutSet = addLayoutSet();

		LayoutSet existingLayoutSet = _persistence.findByPrimaryKey(newLayoutSet.getPrimaryKey());

		Assert.assertEquals(existingLayoutSet, newLayoutSet);
	}

	@Test(expected = NoSuchLayoutSetException.class)
	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		_persistence.findByPrimaryKey(pk);
	}

	@Test
	public void testFindAll() throws Exception {
		_persistence.findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			getOrderByComparator());
	}

	protected OrderByComparator<LayoutSet> getOrderByComparator() {
		return OrderByComparatorFactoryUtil.create("LayoutSet", "mvccVersion",
			true, "layoutSetId", true, "groupId", true, "companyId", true,
			"createDate", true, "modifiedDate", true, "privateLayout", true,
			"logoId", true, "themeId", true, "colorSchemeId", true,
			"pageCount", true, "layoutSetPrototypeUuid", true,
			"layoutSetPrototypeLinkEnabled", true);
	}

	@Test
	public void testFetchByPrimaryKeyExisting() throws Exception {
		LayoutSet newLayoutSet = addLayoutSet();

		LayoutSet existingLayoutSet = _persistence.fetchByPrimaryKey(newLayoutSet.getPrimaryKey());

		Assert.assertEquals(existingLayoutSet, newLayoutSet);
	}

	@Test
	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		LayoutSet missingLayoutSet = _persistence.fetchByPrimaryKey(pk);

		Assert.assertNull(missingLayoutSet);
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereAllPrimaryKeysExist()
		throws Exception {
		LayoutSet newLayoutSet1 = addLayoutSet();
		LayoutSet newLayoutSet2 = addLayoutSet();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newLayoutSet1.getPrimaryKey());
		primaryKeys.add(newLayoutSet2.getPrimaryKey());

		Map<Serializable, LayoutSet> layoutSets = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(2, layoutSets.size());
		Assert.assertEquals(newLayoutSet1,
			layoutSets.get(newLayoutSet1.getPrimaryKey()));
		Assert.assertEquals(newLayoutSet2,
			layoutSets.get(newLayoutSet2.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereNoPrimaryKeysExist()
		throws Exception {
		long pk1 = RandomTestUtil.nextLong();

		long pk2 = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(pk1);
		primaryKeys.add(pk2);

		Map<Serializable, LayoutSet> layoutSets = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(layoutSets.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereSomePrimaryKeysExist()
		throws Exception {
		LayoutSet newLayoutSet = addLayoutSet();

		long pk = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newLayoutSet.getPrimaryKey());
		primaryKeys.add(pk);

		Map<Serializable, LayoutSet> layoutSets = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, layoutSets.size());
		Assert.assertEquals(newLayoutSet,
			layoutSets.get(newLayoutSet.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithNoPrimaryKeys()
		throws Exception {
		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		Map<Serializable, LayoutSet> layoutSets = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(layoutSets.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithOnePrimaryKey()
		throws Exception {
		LayoutSet newLayoutSet = addLayoutSet();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newLayoutSet.getPrimaryKey());

		Map<Serializable, LayoutSet> layoutSets = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, layoutSets.size());
		Assert.assertEquals(newLayoutSet,
			layoutSets.get(newLayoutSet.getPrimaryKey()));
	}

	@Test
	public void testActionableDynamicQuery() throws Exception {
		final IntegerWrapper count = new IntegerWrapper();

		ActionableDynamicQuery actionableDynamicQuery = LayoutSetLocalServiceUtil.getActionableDynamicQuery();

		actionableDynamicQuery.setPerformActionMethod(new ActionableDynamicQuery.PerformActionMethod<LayoutSet>() {
				@Override
				public void performAction(LayoutSet layoutSet) {
					Assert.assertNotNull(layoutSet);

					count.increment();
				}
			});

		actionableDynamicQuery.performActions();

		Assert.assertEquals(count.getValue(), _persistence.countAll());
	}

	@Test
	public void testDynamicQueryByPrimaryKeyExisting()
		throws Exception {
		LayoutSet newLayoutSet = addLayoutSet();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(LayoutSet.class,
				_dynamicQueryClassLoader);

		dynamicQuery.add(RestrictionsFactoryUtil.eq("layoutSetId",
				newLayoutSet.getLayoutSetId()));

		List<LayoutSet> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		LayoutSet existingLayoutSet = result.get(0);

		Assert.assertEquals(existingLayoutSet, newLayoutSet);
	}

	@Test
	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(LayoutSet.class,
				_dynamicQueryClassLoader);

		dynamicQuery.add(RestrictionsFactoryUtil.eq("layoutSetId",
				RandomTestUtil.nextLong()));

		List<LayoutSet> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testDynamicQueryByProjectionExisting()
		throws Exception {
		LayoutSet newLayoutSet = addLayoutSet();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(LayoutSet.class,
				_dynamicQueryClassLoader);

		dynamicQuery.setProjection(ProjectionFactoryUtil.property("layoutSetId"));

		Object newLayoutSetId = newLayoutSet.getLayoutSetId();

		dynamicQuery.add(RestrictionsFactoryUtil.in("layoutSetId",
				new Object[] { newLayoutSetId }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		Object existingLayoutSetId = result.get(0);

		Assert.assertEquals(existingLayoutSetId, newLayoutSetId);
	}

	@Test
	public void testDynamicQueryByProjectionMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(LayoutSet.class,
				_dynamicQueryClassLoader);

		dynamicQuery.setProjection(ProjectionFactoryUtil.property("layoutSetId"));

		dynamicQuery.add(RestrictionsFactoryUtil.in("layoutSetId",
				new Object[] { RandomTestUtil.nextLong() }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testResetOriginalValues() throws Exception {
		LayoutSet newLayoutSet = addLayoutSet();

		_persistence.clearCache();

		LayoutSet existingLayoutSet = _persistence.findByPrimaryKey(newLayoutSet.getPrimaryKey());

		Assert.assertEquals(Long.valueOf(existingLayoutSet.getGroupId()),
			ReflectionTestUtil.<Long>invoke(existingLayoutSet,
				"getOriginalGroupId", new Class<?>[0]));
		Assert.assertEquals(Boolean.valueOf(
				existingLayoutSet.getPrivateLayout()),
			ReflectionTestUtil.<Boolean>invoke(existingLayoutSet,
				"getOriginalPrivateLayout", new Class<?>[0]));

		Assert.assertEquals(Boolean.valueOf(
				existingLayoutSet.getPrivateLayout()),
			ReflectionTestUtil.<Boolean>invoke(existingLayoutSet,
				"getOriginalPrivateLayout", new Class<?>[0]));
		Assert.assertEquals(Long.valueOf(existingLayoutSet.getLogoId()),
			ReflectionTestUtil.<Long>invoke(existingLayoutSet,
				"getOriginalLogoId", new Class<?>[0]));
	}

	protected LayoutSet addLayoutSet() throws Exception {
		long pk = RandomTestUtil.nextLong();

		LayoutSet layoutSet = _persistence.create(pk);

		layoutSet.setMvccVersion(RandomTestUtil.nextLong());

		layoutSet.setGroupId(RandomTestUtil.nextLong());

		layoutSet.setCompanyId(RandomTestUtil.nextLong());

		layoutSet.setCreateDate(RandomTestUtil.nextDate());

		layoutSet.setModifiedDate(RandomTestUtil.nextDate());

		layoutSet.setPrivateLayout(RandomTestUtil.randomBoolean());

		layoutSet.setLogoId(RandomTestUtil.nextLong());

		layoutSet.setThemeId(RandomTestUtil.randomString());

		layoutSet.setColorSchemeId(RandomTestUtil.randomString());

		layoutSet.setCss(RandomTestUtil.randomString());

		layoutSet.setPageCount(RandomTestUtil.nextInt());

		layoutSet.setSettings(RandomTestUtil.randomString());

		layoutSet.setLayoutSetPrototypeUuid(RandomTestUtil.randomString());

		layoutSet.setLayoutSetPrototypeLinkEnabled(RandomTestUtil.randomBoolean());

		_layoutSets.add(_persistence.update(layoutSet));

		return layoutSet;
	}

	private List<LayoutSet> _layoutSets = new ArrayList<LayoutSet>();
	private LayoutSetPersistence _persistence;
	private ClassLoader _dynamicQueryClassLoader;
}