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

package com.liferay.fragment.service.persistence.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;

import com.liferay.fragment.exception.NoSuchEntryInstanceLinkException;
import com.liferay.fragment.model.FragmentEntryInstanceLink;
import com.liferay.fragment.service.FragmentEntryInstanceLinkLocalServiceUtil;
import com.liferay.fragment.service.persistence.FragmentEntryInstanceLinkPersistence;
import com.liferay.fragment.service.persistence.FragmentEntryInstanceLinkUtil;

import com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQueryFactoryUtil;
import com.liferay.portal.kernel.dao.orm.ProjectionFactoryUtil;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.RestrictionsFactoryUtil;
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
public class FragmentEntryInstanceLinkPersistenceTest {
	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule = new AggregateTestRule(new LiferayIntegrationTestRule(),
			PersistenceTestRule.INSTANCE,
			new TransactionalTestRule(Propagation.REQUIRED,
				"com.liferay.fragment.service"));

	@Before
	public void setUp() {
		_persistence = FragmentEntryInstanceLinkUtil.getPersistence();

		Class<?> clazz = _persistence.getClass();

		_dynamicQueryClassLoader = clazz.getClassLoader();
	}

	@After
	public void tearDown() throws Exception {
		Iterator<FragmentEntryInstanceLink> iterator = _fragmentEntryInstanceLinks.iterator();

		while (iterator.hasNext()) {
			_persistence.remove(iterator.next());

			iterator.remove();
		}
	}

	@Test
	public void testCreate() throws Exception {
		long pk = RandomTestUtil.nextLong();

		FragmentEntryInstanceLink fragmentEntryInstanceLink = _persistence.create(pk);

		Assert.assertNotNull(fragmentEntryInstanceLink);

		Assert.assertEquals(fragmentEntryInstanceLink.getPrimaryKey(), pk);
	}

	@Test
	public void testRemove() throws Exception {
		FragmentEntryInstanceLink newFragmentEntryInstanceLink = addFragmentEntryInstanceLink();

		_persistence.remove(newFragmentEntryInstanceLink);

		FragmentEntryInstanceLink existingFragmentEntryInstanceLink = _persistence.fetchByPrimaryKey(newFragmentEntryInstanceLink.getPrimaryKey());

		Assert.assertNull(existingFragmentEntryInstanceLink);
	}

	@Test
	public void testUpdateNew() throws Exception {
		addFragmentEntryInstanceLink();
	}

	@Test
	public void testUpdateExisting() throws Exception {
		long pk = RandomTestUtil.nextLong();

		FragmentEntryInstanceLink newFragmentEntryInstanceLink = _persistence.create(pk);

		newFragmentEntryInstanceLink.setGroupId(RandomTestUtil.nextLong());

		newFragmentEntryInstanceLink.setFragmentEntryId(RandomTestUtil.nextLong());

		newFragmentEntryInstanceLink.setLayoutPageTemplateEntryId(RandomTestUtil.nextLong());

		newFragmentEntryInstanceLink.setPosition(RandomTestUtil.nextInt());

		_fragmentEntryInstanceLinks.add(_persistence.update(
				newFragmentEntryInstanceLink));

		FragmentEntryInstanceLink existingFragmentEntryInstanceLink = _persistence.findByPrimaryKey(newFragmentEntryInstanceLink.getPrimaryKey());

		Assert.assertEquals(existingFragmentEntryInstanceLink.getFragmentEntryInstanceLinkId(),
			newFragmentEntryInstanceLink.getFragmentEntryInstanceLinkId());
		Assert.assertEquals(existingFragmentEntryInstanceLink.getGroupId(),
			newFragmentEntryInstanceLink.getGroupId());
		Assert.assertEquals(existingFragmentEntryInstanceLink.getFragmentEntryId(),
			newFragmentEntryInstanceLink.getFragmentEntryId());
		Assert.assertEquals(existingFragmentEntryInstanceLink.getLayoutPageTemplateEntryId(),
			newFragmentEntryInstanceLink.getLayoutPageTemplateEntryId());
		Assert.assertEquals(existingFragmentEntryInstanceLink.getPosition(),
			newFragmentEntryInstanceLink.getPosition());
	}

	@Test
	public void testCountByGroupId() throws Exception {
		_persistence.countByGroupId(RandomTestUtil.nextLong());

		_persistence.countByGroupId(0L);
	}

	@Test
	public void testCountByG_F() throws Exception {
		_persistence.countByG_F(RandomTestUtil.nextLong(),
			RandomTestUtil.nextLong());

		_persistence.countByG_F(0L, 0L);
	}

	@Test
	public void testCountByG_L() throws Exception {
		_persistence.countByG_L(RandomTestUtil.nextLong(),
			RandomTestUtil.nextLong());

		_persistence.countByG_L(0L, 0L);
	}

	@Test
	public void testFindByPrimaryKeyExisting() throws Exception {
		FragmentEntryInstanceLink newFragmentEntryInstanceLink = addFragmentEntryInstanceLink();

		FragmentEntryInstanceLink existingFragmentEntryInstanceLink = _persistence.findByPrimaryKey(newFragmentEntryInstanceLink.getPrimaryKey());

		Assert.assertEquals(existingFragmentEntryInstanceLink,
			newFragmentEntryInstanceLink);
	}

	@Test(expected = NoSuchEntryInstanceLinkException.class)
	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		_persistence.findByPrimaryKey(pk);
	}

	@Test
	public void testFindAll() throws Exception {
		_persistence.findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			getOrderByComparator());
	}

	protected OrderByComparator<FragmentEntryInstanceLink> getOrderByComparator() {
		return OrderByComparatorFactoryUtil.create("FragmentEntryInstanceLink",
			"fragmentEntryInstanceLinkId", true, "groupId", true,
			"fragmentEntryId", true, "layoutPageTemplateEntryId", true,
			"position", true);
	}

	@Test
	public void testFetchByPrimaryKeyExisting() throws Exception {
		FragmentEntryInstanceLink newFragmentEntryInstanceLink = addFragmentEntryInstanceLink();

		FragmentEntryInstanceLink existingFragmentEntryInstanceLink = _persistence.fetchByPrimaryKey(newFragmentEntryInstanceLink.getPrimaryKey());

		Assert.assertEquals(existingFragmentEntryInstanceLink,
			newFragmentEntryInstanceLink);
	}

	@Test
	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		FragmentEntryInstanceLink missingFragmentEntryInstanceLink = _persistence.fetchByPrimaryKey(pk);

		Assert.assertNull(missingFragmentEntryInstanceLink);
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereAllPrimaryKeysExist()
		throws Exception {
		FragmentEntryInstanceLink newFragmentEntryInstanceLink1 = addFragmentEntryInstanceLink();
		FragmentEntryInstanceLink newFragmentEntryInstanceLink2 = addFragmentEntryInstanceLink();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newFragmentEntryInstanceLink1.getPrimaryKey());
		primaryKeys.add(newFragmentEntryInstanceLink2.getPrimaryKey());

		Map<Serializable, FragmentEntryInstanceLink> fragmentEntryInstanceLinks = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(2, fragmentEntryInstanceLinks.size());
		Assert.assertEquals(newFragmentEntryInstanceLink1,
			fragmentEntryInstanceLinks.get(
				newFragmentEntryInstanceLink1.getPrimaryKey()));
		Assert.assertEquals(newFragmentEntryInstanceLink2,
			fragmentEntryInstanceLinks.get(
				newFragmentEntryInstanceLink2.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereNoPrimaryKeysExist()
		throws Exception {
		long pk1 = RandomTestUtil.nextLong();

		long pk2 = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(pk1);
		primaryKeys.add(pk2);

		Map<Serializable, FragmentEntryInstanceLink> fragmentEntryInstanceLinks = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(fragmentEntryInstanceLinks.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereSomePrimaryKeysExist()
		throws Exception {
		FragmentEntryInstanceLink newFragmentEntryInstanceLink = addFragmentEntryInstanceLink();

		long pk = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newFragmentEntryInstanceLink.getPrimaryKey());
		primaryKeys.add(pk);

		Map<Serializable, FragmentEntryInstanceLink> fragmentEntryInstanceLinks = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, fragmentEntryInstanceLinks.size());
		Assert.assertEquals(newFragmentEntryInstanceLink,
			fragmentEntryInstanceLinks.get(
				newFragmentEntryInstanceLink.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithNoPrimaryKeys()
		throws Exception {
		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		Map<Serializable, FragmentEntryInstanceLink> fragmentEntryInstanceLinks = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(fragmentEntryInstanceLinks.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithOnePrimaryKey()
		throws Exception {
		FragmentEntryInstanceLink newFragmentEntryInstanceLink = addFragmentEntryInstanceLink();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newFragmentEntryInstanceLink.getPrimaryKey());

		Map<Serializable, FragmentEntryInstanceLink> fragmentEntryInstanceLinks = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, fragmentEntryInstanceLinks.size());
		Assert.assertEquals(newFragmentEntryInstanceLink,
			fragmentEntryInstanceLinks.get(
				newFragmentEntryInstanceLink.getPrimaryKey()));
	}

	@Test
	public void testActionableDynamicQuery() throws Exception {
		final IntegerWrapper count = new IntegerWrapper();

		ActionableDynamicQuery actionableDynamicQuery = FragmentEntryInstanceLinkLocalServiceUtil.getActionableDynamicQuery();

		actionableDynamicQuery.setPerformActionMethod(new ActionableDynamicQuery.PerformActionMethod<FragmentEntryInstanceLink>() {
				@Override
				public void performAction(
					FragmentEntryInstanceLink fragmentEntryInstanceLink) {
					Assert.assertNotNull(fragmentEntryInstanceLink);

					count.increment();
				}
			});

		actionableDynamicQuery.performActions();

		Assert.assertEquals(count.getValue(), _persistence.countAll());
	}

	@Test
	public void testDynamicQueryByPrimaryKeyExisting()
		throws Exception {
		FragmentEntryInstanceLink newFragmentEntryInstanceLink = addFragmentEntryInstanceLink();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(FragmentEntryInstanceLink.class,
				_dynamicQueryClassLoader);

		dynamicQuery.add(RestrictionsFactoryUtil.eq(
				"fragmentEntryInstanceLinkId",
				newFragmentEntryInstanceLink.getFragmentEntryInstanceLinkId()));

		List<FragmentEntryInstanceLink> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		FragmentEntryInstanceLink existingFragmentEntryInstanceLink = result.get(0);

		Assert.assertEquals(existingFragmentEntryInstanceLink,
			newFragmentEntryInstanceLink);
	}

	@Test
	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(FragmentEntryInstanceLink.class,
				_dynamicQueryClassLoader);

		dynamicQuery.add(RestrictionsFactoryUtil.eq(
				"fragmentEntryInstanceLinkId", RandomTestUtil.nextLong()));

		List<FragmentEntryInstanceLink> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testDynamicQueryByProjectionExisting()
		throws Exception {
		FragmentEntryInstanceLink newFragmentEntryInstanceLink = addFragmentEntryInstanceLink();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(FragmentEntryInstanceLink.class,
				_dynamicQueryClassLoader);

		dynamicQuery.setProjection(ProjectionFactoryUtil.property(
				"fragmentEntryInstanceLinkId"));

		Object newFragmentEntryInstanceLinkId = newFragmentEntryInstanceLink.getFragmentEntryInstanceLinkId();

		dynamicQuery.add(RestrictionsFactoryUtil.in(
				"fragmentEntryInstanceLinkId",
				new Object[] { newFragmentEntryInstanceLinkId }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		Object existingFragmentEntryInstanceLinkId = result.get(0);

		Assert.assertEquals(existingFragmentEntryInstanceLinkId,
			newFragmentEntryInstanceLinkId);
	}

	@Test
	public void testDynamicQueryByProjectionMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(FragmentEntryInstanceLink.class,
				_dynamicQueryClassLoader);

		dynamicQuery.setProjection(ProjectionFactoryUtil.property(
				"fragmentEntryInstanceLinkId"));

		dynamicQuery.add(RestrictionsFactoryUtil.in(
				"fragmentEntryInstanceLinkId",
				new Object[] { RandomTestUtil.nextLong() }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	protected FragmentEntryInstanceLink addFragmentEntryInstanceLink()
		throws Exception {
		long pk = RandomTestUtil.nextLong();

		FragmentEntryInstanceLink fragmentEntryInstanceLink = _persistence.create(pk);

		fragmentEntryInstanceLink.setGroupId(RandomTestUtil.nextLong());

		fragmentEntryInstanceLink.setFragmentEntryId(RandomTestUtil.nextLong());

		fragmentEntryInstanceLink.setLayoutPageTemplateEntryId(RandomTestUtil.nextLong());

		fragmentEntryInstanceLink.setPosition(RandomTestUtil.nextInt());

		_fragmentEntryInstanceLinks.add(_persistence.update(
				fragmentEntryInstanceLink));

		return fragmentEntryInstanceLink;
	}

	private List<FragmentEntryInstanceLink> _fragmentEntryInstanceLinks = new ArrayList<FragmentEntryInstanceLink>();
	private FragmentEntryInstanceLinkPersistence _persistence;
	private ClassLoader _dynamicQueryClassLoader;
}