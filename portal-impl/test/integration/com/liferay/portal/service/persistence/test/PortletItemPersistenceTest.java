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
import com.liferay.portal.kernel.exception.NoSuchPortletItemException;
import com.liferay.portal.kernel.model.PortletItem;
import com.liferay.portal.kernel.service.PortletItemLocalServiceUtil;
import com.liferay.portal.kernel.service.persistence.PortletItemPersistence;
import com.liferay.portal.kernel.service.persistence.PortletItemUtil;
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
import java.util.Objects;
import java.util.Set;

/**
 * @generated
 */
public class PortletItemPersistenceTest {
	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule = new AggregateTestRule(new LiferayIntegrationTestRule(),
			PersistenceTestRule.INSTANCE,
			new TransactionalTestRule(Propagation.REQUIRED));

	@Before
	public void setUp() {
		_persistence = PortletItemUtil.getPersistence();

		Class<?> clazz = _persistence.getClass();

		_dynamicQueryClassLoader = clazz.getClassLoader();
	}

	@After
	public void tearDown() throws Exception {
		Iterator<PortletItem> iterator = _portletItems.iterator();

		while (iterator.hasNext()) {
			_persistence.remove(iterator.next());

			iterator.remove();
		}
	}

	@Test
	public void testCreate() throws Exception {
		long pk = RandomTestUtil.nextLong();

		PortletItem portletItem = _persistence.create(pk);

		Assert.assertNotNull(portletItem);

		Assert.assertEquals(portletItem.getPrimaryKey(), pk);
	}

	@Test
	public void testRemove() throws Exception {
		PortletItem newPortletItem = addPortletItem();

		_persistence.remove(newPortletItem);

		PortletItem existingPortletItem = _persistence.fetchByPrimaryKey(newPortletItem.getPrimaryKey());

		Assert.assertNull(existingPortletItem);
	}

	@Test
	public void testUpdateNew() throws Exception {
		addPortletItem();
	}

	@Test
	public void testUpdateExisting() throws Exception {
		long pk = RandomTestUtil.nextLong();

		PortletItem newPortletItem = _persistence.create(pk);

		newPortletItem.setMvccVersion(RandomTestUtil.nextLong());

		newPortletItem.setGroupId(RandomTestUtil.nextLong());

		newPortletItem.setCompanyId(RandomTestUtil.nextLong());

		newPortletItem.setUserId(RandomTestUtil.nextLong());

		newPortletItem.setUserName(RandomTestUtil.randomString());

		newPortletItem.setCreateDate(RandomTestUtil.nextDate());

		newPortletItem.setModifiedDate(RandomTestUtil.nextDate());

		newPortletItem.setName(RandomTestUtil.randomString());

		newPortletItem.setPortletId(RandomTestUtil.randomString());

		newPortletItem.setClassNameId(RandomTestUtil.nextLong());

		_portletItems.add(_persistence.update(newPortletItem));

		PortletItem existingPortletItem = _persistence.findByPrimaryKey(newPortletItem.getPrimaryKey());

		Assert.assertEquals(existingPortletItem.getMvccVersion(),
			newPortletItem.getMvccVersion());
		Assert.assertEquals(existingPortletItem.getPortletItemId(),
			newPortletItem.getPortletItemId());
		Assert.assertEquals(existingPortletItem.getGroupId(),
			newPortletItem.getGroupId());
		Assert.assertEquals(existingPortletItem.getCompanyId(),
			newPortletItem.getCompanyId());
		Assert.assertEquals(existingPortletItem.getUserId(),
			newPortletItem.getUserId());
		Assert.assertEquals(existingPortletItem.getUserName(),
			newPortletItem.getUserName());
		Assert.assertEquals(Time.getShortTimestamp(
				existingPortletItem.getCreateDate()),
			Time.getShortTimestamp(newPortletItem.getCreateDate()));
		Assert.assertEquals(Time.getShortTimestamp(
				existingPortletItem.getModifiedDate()),
			Time.getShortTimestamp(newPortletItem.getModifiedDate()));
		Assert.assertEquals(existingPortletItem.getName(),
			newPortletItem.getName());
		Assert.assertEquals(existingPortletItem.getPortletId(),
			newPortletItem.getPortletId());
		Assert.assertEquals(existingPortletItem.getClassNameId(),
			newPortletItem.getClassNameId());
	}

	@Test
	public void testCountByG_C() throws Exception {
		_persistence.countByG_C(RandomTestUtil.nextLong(),
			RandomTestUtil.nextLong());

		_persistence.countByG_C(0L, 0L);
	}

	@Test
	public void testCountByG_P_C() throws Exception {
		_persistence.countByG_P_C(RandomTestUtil.nextLong(), "",
			RandomTestUtil.nextLong());

		_persistence.countByG_P_C(0L, "null", 0L);

		_persistence.countByG_P_C(0L, (String)null, 0L);
	}

	@Test
	public void testCountByG_N_P_C() throws Exception {
		_persistence.countByG_N_P_C(RandomTestUtil.nextLong(), "", "",
			RandomTestUtil.nextLong());

		_persistence.countByG_N_P_C(0L, "null", "null", 0L);

		_persistence.countByG_N_P_C(0L, (String)null, (String)null, 0L);
	}

	@Test
	public void testFindByPrimaryKeyExisting() throws Exception {
		PortletItem newPortletItem = addPortletItem();

		PortletItem existingPortletItem = _persistence.findByPrimaryKey(newPortletItem.getPrimaryKey());

		Assert.assertEquals(existingPortletItem, newPortletItem);
	}

	@Test(expected = NoSuchPortletItemException.class)
	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		_persistence.findByPrimaryKey(pk);
	}

	@Test
	public void testFindAll() throws Exception {
		_persistence.findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			getOrderByComparator());
	}

	protected OrderByComparator<PortletItem> getOrderByComparator() {
		return OrderByComparatorFactoryUtil.create("PortletItem",
			"mvccVersion", true, "portletItemId", true, "groupId", true,
			"companyId", true, "userId", true, "userName", true, "createDate",
			true, "modifiedDate", true, "name", true, "portletId", true,
			"classNameId", true);
	}

	@Test
	public void testFetchByPrimaryKeyExisting() throws Exception {
		PortletItem newPortletItem = addPortletItem();

		PortletItem existingPortletItem = _persistence.fetchByPrimaryKey(newPortletItem.getPrimaryKey());

		Assert.assertEquals(existingPortletItem, newPortletItem);
	}

	@Test
	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		PortletItem missingPortletItem = _persistence.fetchByPrimaryKey(pk);

		Assert.assertNull(missingPortletItem);
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereAllPrimaryKeysExist()
		throws Exception {
		PortletItem newPortletItem1 = addPortletItem();
		PortletItem newPortletItem2 = addPortletItem();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newPortletItem1.getPrimaryKey());
		primaryKeys.add(newPortletItem2.getPrimaryKey());

		Map<Serializable, PortletItem> portletItems = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(2, portletItems.size());
		Assert.assertEquals(newPortletItem1,
			portletItems.get(newPortletItem1.getPrimaryKey()));
		Assert.assertEquals(newPortletItem2,
			portletItems.get(newPortletItem2.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereNoPrimaryKeysExist()
		throws Exception {
		long pk1 = RandomTestUtil.nextLong();

		long pk2 = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(pk1);
		primaryKeys.add(pk2);

		Map<Serializable, PortletItem> portletItems = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(portletItems.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereSomePrimaryKeysExist()
		throws Exception {
		PortletItem newPortletItem = addPortletItem();

		long pk = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newPortletItem.getPrimaryKey());
		primaryKeys.add(pk);

		Map<Serializable, PortletItem> portletItems = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, portletItems.size());
		Assert.assertEquals(newPortletItem,
			portletItems.get(newPortletItem.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithNoPrimaryKeys()
		throws Exception {
		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		Map<Serializable, PortletItem> portletItems = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(portletItems.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithOnePrimaryKey()
		throws Exception {
		PortletItem newPortletItem = addPortletItem();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newPortletItem.getPrimaryKey());

		Map<Serializable, PortletItem> portletItems = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, portletItems.size());
		Assert.assertEquals(newPortletItem,
			portletItems.get(newPortletItem.getPrimaryKey()));
	}

	@Test
	public void testActionableDynamicQuery() throws Exception {
		final IntegerWrapper count = new IntegerWrapper();

		ActionableDynamicQuery actionableDynamicQuery = PortletItemLocalServiceUtil.getActionableDynamicQuery();

		actionableDynamicQuery.setPerformActionMethod(new ActionableDynamicQuery.PerformActionMethod<PortletItem>() {
				@Override
				public void performAction(PortletItem portletItem) {
					Assert.assertNotNull(portletItem);

					count.increment();
				}
			});

		actionableDynamicQuery.performActions();

		Assert.assertEquals(count.getValue(), _persistence.countAll());
	}

	@Test
	public void testDynamicQueryByPrimaryKeyExisting()
		throws Exception {
		PortletItem newPortletItem = addPortletItem();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(PortletItem.class,
				_dynamicQueryClassLoader);

		dynamicQuery.add(RestrictionsFactoryUtil.eq("portletItemId",
				newPortletItem.getPortletItemId()));

		List<PortletItem> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		PortletItem existingPortletItem = result.get(0);

		Assert.assertEquals(existingPortletItem, newPortletItem);
	}

	@Test
	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(PortletItem.class,
				_dynamicQueryClassLoader);

		dynamicQuery.add(RestrictionsFactoryUtil.eq("portletItemId",
				RandomTestUtil.nextLong()));

		List<PortletItem> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testDynamicQueryByProjectionExisting()
		throws Exception {
		PortletItem newPortletItem = addPortletItem();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(PortletItem.class,
				_dynamicQueryClassLoader);

		dynamicQuery.setProjection(ProjectionFactoryUtil.property(
				"portletItemId"));

		Object newPortletItemId = newPortletItem.getPortletItemId();

		dynamicQuery.add(RestrictionsFactoryUtil.in("portletItemId",
				new Object[] { newPortletItemId }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		Object existingPortletItemId = result.get(0);

		Assert.assertEquals(existingPortletItemId, newPortletItemId);
	}

	@Test
	public void testDynamicQueryByProjectionMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(PortletItem.class,
				_dynamicQueryClassLoader);

		dynamicQuery.setProjection(ProjectionFactoryUtil.property(
				"portletItemId"));

		dynamicQuery.add(RestrictionsFactoryUtil.in("portletItemId",
				new Object[] { RandomTestUtil.nextLong() }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testResetOriginalValues() throws Exception {
		PortletItem newPortletItem = addPortletItem();

		_persistence.clearCache();

		PortletItem existingPortletItem = _persistence.findByPrimaryKey(newPortletItem.getPrimaryKey());

		Assert.assertEquals(Long.valueOf(existingPortletItem.getGroupId()),
			ReflectionTestUtil.<Long>invoke(existingPortletItem,
				"getOriginalGroupId", new Class<?>[0]));
		Assert.assertTrue(Objects.equals(existingPortletItem.getName(),
				ReflectionTestUtil.invoke(existingPortletItem,
					"getOriginalName", new Class<?>[0])));
		Assert.assertTrue(Objects.equals(existingPortletItem.getPortletId(),
				ReflectionTestUtil.invoke(existingPortletItem,
					"getOriginalPortletId", new Class<?>[0])));
		Assert.assertEquals(Long.valueOf(existingPortletItem.getClassNameId()),
			ReflectionTestUtil.<Long>invoke(existingPortletItem,
				"getOriginalClassNameId", new Class<?>[0]));
	}

	protected PortletItem addPortletItem() throws Exception {
		long pk = RandomTestUtil.nextLong();

		PortletItem portletItem = _persistence.create(pk);

		portletItem.setMvccVersion(RandomTestUtil.nextLong());

		portletItem.setGroupId(RandomTestUtil.nextLong());

		portletItem.setCompanyId(RandomTestUtil.nextLong());

		portletItem.setUserId(RandomTestUtil.nextLong());

		portletItem.setUserName(RandomTestUtil.randomString());

		portletItem.setCreateDate(RandomTestUtil.nextDate());

		portletItem.setModifiedDate(RandomTestUtil.nextDate());

		portletItem.setName(RandomTestUtil.randomString());

		portletItem.setPortletId(RandomTestUtil.randomString());

		portletItem.setClassNameId(RandomTestUtil.nextLong());

		_portletItems.add(_persistence.update(portletItem));

		return portletItem;
	}

	private List<PortletItem> _portletItems = new ArrayList<PortletItem>();
	private PortletItemPersistence _persistence;
	private ClassLoader _dynamicQueryClassLoader;
}