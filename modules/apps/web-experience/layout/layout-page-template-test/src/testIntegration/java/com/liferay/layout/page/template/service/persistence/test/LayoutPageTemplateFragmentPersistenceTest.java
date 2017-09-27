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

package com.liferay.layout.page.template.service.persistence.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;

import com.liferay.layout.page.template.exception.NoSuchPageTemplateFragmentException;
import com.liferay.layout.page.template.model.LayoutPageTemplateFragment;
import com.liferay.layout.page.template.service.LayoutPageTemplateFragmentLocalServiceUtil;
import com.liferay.layout.page.template.service.persistence.LayoutPageTemplateFragmentPK;
import com.liferay.layout.page.template.service.persistence.LayoutPageTemplateFragmentPersistence;
import com.liferay.layout.page.template.service.persistence.LayoutPageTemplateFragmentUtil;

import com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQueryFactoryUtil;
import com.liferay.portal.kernel.dao.orm.ProjectionFactoryUtil;
import com.liferay.portal.kernel.dao.orm.RestrictionsFactoryUtil;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.transaction.Propagation;
import com.liferay.portal.kernel.util.IntegerWrapper;
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
public class LayoutPageTemplateFragmentPersistenceTest {
	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule = new AggregateTestRule(new LiferayIntegrationTestRule(),
			PersistenceTestRule.INSTANCE,
			new TransactionalTestRule(Propagation.REQUIRED,
				"com.liferay.layout.page.template.service"));

	@Before
	public void setUp() {
		_persistence = LayoutPageTemplateFragmentUtil.getPersistence();

		Class<?> clazz = _persistence.getClass();

		_dynamicQueryClassLoader = clazz.getClassLoader();
	}

	@After
	public void tearDown() throws Exception {
		Iterator<LayoutPageTemplateFragment> iterator = _layoutPageTemplateFragments.iterator();

		while (iterator.hasNext()) {
			_persistence.remove(iterator.next());

			iterator.remove();
		}
	}

	@Test
	public void testCreate() throws Exception {
		LayoutPageTemplateFragmentPK pk = new LayoutPageTemplateFragmentPK(RandomTestUtil.nextLong(),
				RandomTestUtil.nextLong(), RandomTestUtil.nextLong());

		LayoutPageTemplateFragment layoutPageTemplateFragment = _persistence.create(pk);

		Assert.assertNotNull(layoutPageTemplateFragment);

		Assert.assertEquals(layoutPageTemplateFragment.getPrimaryKey(), pk);
	}

	@Test
	public void testRemove() throws Exception {
		LayoutPageTemplateFragment newLayoutPageTemplateFragment = addLayoutPageTemplateFragment();

		_persistence.remove(newLayoutPageTemplateFragment);

		LayoutPageTemplateFragment existingLayoutPageTemplateFragment = _persistence.fetchByPrimaryKey(newLayoutPageTemplateFragment.getPrimaryKey());

		Assert.assertNull(existingLayoutPageTemplateFragment);
	}

	@Test
	public void testUpdateNew() throws Exception {
		addLayoutPageTemplateFragment();
	}

	@Test
	public void testUpdateExisting() throws Exception {
		LayoutPageTemplateFragmentPK pk = new LayoutPageTemplateFragmentPK(RandomTestUtil.nextLong(),
				RandomTestUtil.nextLong(), RandomTestUtil.nextLong());

		LayoutPageTemplateFragment newLayoutPageTemplateFragment = _persistence.create(pk);

		newLayoutPageTemplateFragment.setCompanyId(RandomTestUtil.nextLong());

		newLayoutPageTemplateFragment.setUserId(RandomTestUtil.nextLong());

		newLayoutPageTemplateFragment.setUserName(RandomTestUtil.randomString());

		newLayoutPageTemplateFragment.setCreateDate(RandomTestUtil.nextDate());

		newLayoutPageTemplateFragment.setModifiedDate(RandomTestUtil.nextDate());

		newLayoutPageTemplateFragment.setPosition(RandomTestUtil.nextInt());

		_layoutPageTemplateFragments.add(_persistence.update(
				newLayoutPageTemplateFragment));

		LayoutPageTemplateFragment existingLayoutPageTemplateFragment = _persistence.findByPrimaryKey(newLayoutPageTemplateFragment.getPrimaryKey());

		Assert.assertEquals(existingLayoutPageTemplateFragment.getGroupId(),
			newLayoutPageTemplateFragment.getGroupId());
		Assert.assertEquals(existingLayoutPageTemplateFragment.getLayoutPageTemplateId(),
			newLayoutPageTemplateFragment.getLayoutPageTemplateId());
		Assert.assertEquals(existingLayoutPageTemplateFragment.getFragmentEntryId(),
			newLayoutPageTemplateFragment.getFragmentEntryId());
		Assert.assertEquals(existingLayoutPageTemplateFragment.getCompanyId(),
			newLayoutPageTemplateFragment.getCompanyId());
		Assert.assertEquals(existingLayoutPageTemplateFragment.getUserId(),
			newLayoutPageTemplateFragment.getUserId());
		Assert.assertEquals(existingLayoutPageTemplateFragment.getUserName(),
			newLayoutPageTemplateFragment.getUserName());
		Assert.assertEquals(Time.getShortTimestamp(
				existingLayoutPageTemplateFragment.getCreateDate()),
			Time.getShortTimestamp(
				newLayoutPageTemplateFragment.getCreateDate()));
		Assert.assertEquals(Time.getShortTimestamp(
				existingLayoutPageTemplateFragment.getModifiedDate()),
			Time.getShortTimestamp(
				newLayoutPageTemplateFragment.getModifiedDate()));
		Assert.assertEquals(existingLayoutPageTemplateFragment.getPosition(),
			newLayoutPageTemplateFragment.getPosition());
	}

	@Test
	public void testCountByGroupId() throws Exception {
		_persistence.countByGroupId(RandomTestUtil.nextLong());

		_persistence.countByGroupId(0L);
	}

	@Test
	public void testCountByG_L() throws Exception {
		_persistence.countByG_L(RandomTestUtil.nextLong(),
			RandomTestUtil.nextLong());

		_persistence.countByG_L(0L, 0L);
	}

	@Test
	public void testCountByG_F() throws Exception {
		_persistence.countByG_F(RandomTestUtil.nextLong(),
			RandomTestUtil.nextLong());

		_persistence.countByG_F(0L, 0L);
	}

	@Test
	public void testFindByPrimaryKeyExisting() throws Exception {
		LayoutPageTemplateFragment newLayoutPageTemplateFragment = addLayoutPageTemplateFragment();

		LayoutPageTemplateFragment existingLayoutPageTemplateFragment = _persistence.findByPrimaryKey(newLayoutPageTemplateFragment.getPrimaryKey());

		Assert.assertEquals(existingLayoutPageTemplateFragment,
			newLayoutPageTemplateFragment);
	}

	@Test(expected = NoSuchPageTemplateFragmentException.class)
	public void testFindByPrimaryKeyMissing() throws Exception {
		LayoutPageTemplateFragmentPK pk = new LayoutPageTemplateFragmentPK(RandomTestUtil.nextLong(),
				RandomTestUtil.nextLong(), RandomTestUtil.nextLong());

		_persistence.findByPrimaryKey(pk);
	}

	@Test
	public void testFetchByPrimaryKeyExisting() throws Exception {
		LayoutPageTemplateFragment newLayoutPageTemplateFragment = addLayoutPageTemplateFragment();

		LayoutPageTemplateFragment existingLayoutPageTemplateFragment = _persistence.fetchByPrimaryKey(newLayoutPageTemplateFragment.getPrimaryKey());

		Assert.assertEquals(existingLayoutPageTemplateFragment,
			newLayoutPageTemplateFragment);
	}

	@Test
	public void testFetchByPrimaryKeyMissing() throws Exception {
		LayoutPageTemplateFragmentPK pk = new LayoutPageTemplateFragmentPK(RandomTestUtil.nextLong(),
				RandomTestUtil.nextLong(), RandomTestUtil.nextLong());

		LayoutPageTemplateFragment missingLayoutPageTemplateFragment = _persistence.fetchByPrimaryKey(pk);

		Assert.assertNull(missingLayoutPageTemplateFragment);
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereAllPrimaryKeysExist()
		throws Exception {
		LayoutPageTemplateFragment newLayoutPageTemplateFragment1 = addLayoutPageTemplateFragment();
		LayoutPageTemplateFragment newLayoutPageTemplateFragment2 = addLayoutPageTemplateFragment();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newLayoutPageTemplateFragment1.getPrimaryKey());
		primaryKeys.add(newLayoutPageTemplateFragment2.getPrimaryKey());

		Map<Serializable, LayoutPageTemplateFragment> layoutPageTemplateFragments =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(2, layoutPageTemplateFragments.size());
		Assert.assertEquals(newLayoutPageTemplateFragment1,
			layoutPageTemplateFragments.get(
				newLayoutPageTemplateFragment1.getPrimaryKey()));
		Assert.assertEquals(newLayoutPageTemplateFragment2,
			layoutPageTemplateFragments.get(
				newLayoutPageTemplateFragment2.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereNoPrimaryKeysExist()
		throws Exception {
		LayoutPageTemplateFragmentPK pk1 = new LayoutPageTemplateFragmentPK(RandomTestUtil.nextLong(),
				RandomTestUtil.nextLong(), RandomTestUtil.nextLong());

		LayoutPageTemplateFragmentPK pk2 = new LayoutPageTemplateFragmentPK(RandomTestUtil.nextLong(),
				RandomTestUtil.nextLong(), RandomTestUtil.nextLong());

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(pk1);
		primaryKeys.add(pk2);

		Map<Serializable, LayoutPageTemplateFragment> layoutPageTemplateFragments =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(layoutPageTemplateFragments.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereSomePrimaryKeysExist()
		throws Exception {
		LayoutPageTemplateFragment newLayoutPageTemplateFragment = addLayoutPageTemplateFragment();

		LayoutPageTemplateFragmentPK pk = new LayoutPageTemplateFragmentPK(RandomTestUtil.nextLong(),
				RandomTestUtil.nextLong(), RandomTestUtil.nextLong());

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newLayoutPageTemplateFragment.getPrimaryKey());
		primaryKeys.add(pk);

		Map<Serializable, LayoutPageTemplateFragment> layoutPageTemplateFragments =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, layoutPageTemplateFragments.size());
		Assert.assertEquals(newLayoutPageTemplateFragment,
			layoutPageTemplateFragments.get(
				newLayoutPageTemplateFragment.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithNoPrimaryKeys()
		throws Exception {
		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		Map<Serializable, LayoutPageTemplateFragment> layoutPageTemplateFragments =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(layoutPageTemplateFragments.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithOnePrimaryKey()
		throws Exception {
		LayoutPageTemplateFragment newLayoutPageTemplateFragment = addLayoutPageTemplateFragment();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newLayoutPageTemplateFragment.getPrimaryKey());

		Map<Serializable, LayoutPageTemplateFragment> layoutPageTemplateFragments =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, layoutPageTemplateFragments.size());
		Assert.assertEquals(newLayoutPageTemplateFragment,
			layoutPageTemplateFragments.get(
				newLayoutPageTemplateFragment.getPrimaryKey()));
	}

	@Test
	public void testActionableDynamicQuery() throws Exception {
		final IntegerWrapper count = new IntegerWrapper();

		ActionableDynamicQuery actionableDynamicQuery = LayoutPageTemplateFragmentLocalServiceUtil.getActionableDynamicQuery();

		actionableDynamicQuery.setPerformActionMethod(new ActionableDynamicQuery.PerformActionMethod<LayoutPageTemplateFragment>() {
				@Override
				public void performAction(
					LayoutPageTemplateFragment layoutPageTemplateFragment) {
					Assert.assertNotNull(layoutPageTemplateFragment);

					count.increment();
				}
			});

		actionableDynamicQuery.performActions();

		Assert.assertEquals(count.getValue(), _persistence.countAll());
	}

	@Test
	public void testDynamicQueryByPrimaryKeyExisting()
		throws Exception {
		LayoutPageTemplateFragment newLayoutPageTemplateFragment = addLayoutPageTemplateFragment();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(LayoutPageTemplateFragment.class,
				_dynamicQueryClassLoader);

		dynamicQuery.add(RestrictionsFactoryUtil.eq("id.groupId",
				newLayoutPageTemplateFragment.getGroupId()));
		dynamicQuery.add(RestrictionsFactoryUtil.eq("id.layoutPageTemplateId",
				newLayoutPageTemplateFragment.getLayoutPageTemplateId()));
		dynamicQuery.add(RestrictionsFactoryUtil.eq("id.fragmentEntryId",
				newLayoutPageTemplateFragment.getFragmentEntryId()));

		List<LayoutPageTemplateFragment> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		LayoutPageTemplateFragment existingLayoutPageTemplateFragment = result.get(0);

		Assert.assertEquals(existingLayoutPageTemplateFragment,
			newLayoutPageTemplateFragment);
	}

	@Test
	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(LayoutPageTemplateFragment.class,
				_dynamicQueryClassLoader);

		dynamicQuery.add(RestrictionsFactoryUtil.eq("id.groupId",
				RandomTestUtil.nextLong()));
		dynamicQuery.add(RestrictionsFactoryUtil.eq("id.layoutPageTemplateId",
				RandomTestUtil.nextLong()));
		dynamicQuery.add(RestrictionsFactoryUtil.eq("id.fragmentEntryId",
				RandomTestUtil.nextLong()));

		List<LayoutPageTemplateFragment> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testDynamicQueryByProjectionExisting()
		throws Exception {
		LayoutPageTemplateFragment newLayoutPageTemplateFragment = addLayoutPageTemplateFragment();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(LayoutPageTemplateFragment.class,
				_dynamicQueryClassLoader);

		dynamicQuery.setProjection(ProjectionFactoryUtil.property("id.groupId"));

		Object newGroupId = newLayoutPageTemplateFragment.getGroupId();

		dynamicQuery.add(RestrictionsFactoryUtil.in("id.groupId",
				new Object[] { newGroupId }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		Object existingGroupId = result.get(0);

		Assert.assertEquals(existingGroupId, newGroupId);
	}

	@Test
	public void testDynamicQueryByProjectionMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(LayoutPageTemplateFragment.class,
				_dynamicQueryClassLoader);

		dynamicQuery.setProjection(ProjectionFactoryUtil.property("id.groupId"));

		dynamicQuery.add(RestrictionsFactoryUtil.in("id.groupId",
				new Object[] { RandomTestUtil.nextLong() }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	protected LayoutPageTemplateFragment addLayoutPageTemplateFragment()
		throws Exception {
		LayoutPageTemplateFragmentPK pk = new LayoutPageTemplateFragmentPK(RandomTestUtil.nextLong(),
				RandomTestUtil.nextLong(), RandomTestUtil.nextLong());

		LayoutPageTemplateFragment layoutPageTemplateFragment = _persistence.create(pk);

		layoutPageTemplateFragment.setCompanyId(RandomTestUtil.nextLong());

		layoutPageTemplateFragment.setUserId(RandomTestUtil.nextLong());

		layoutPageTemplateFragment.setUserName(RandomTestUtil.randomString());

		layoutPageTemplateFragment.setCreateDate(RandomTestUtil.nextDate());

		layoutPageTemplateFragment.setModifiedDate(RandomTestUtil.nextDate());

		layoutPageTemplateFragment.setPosition(RandomTestUtil.nextInt());

		_layoutPageTemplateFragments.add(_persistence.update(
				layoutPageTemplateFragment));

		return layoutPageTemplateFragment;
	}

	private List<LayoutPageTemplateFragment> _layoutPageTemplateFragments = new ArrayList<LayoutPageTemplateFragment>();
	private LayoutPageTemplateFragmentPersistence _persistence;
	private ClassLoader _dynamicQueryClassLoader;
}