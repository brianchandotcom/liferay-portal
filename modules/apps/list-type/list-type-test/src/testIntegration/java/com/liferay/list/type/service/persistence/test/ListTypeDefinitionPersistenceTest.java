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

package com.liferay.list.type.service.persistence.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.list.type.exception.NoSuchListTypeDefinitionException;
import com.liferay.list.type.model.ListTypeDefinition;
import com.liferay.list.type.service.ListTypeDefinitionLocalServiceUtil;
import com.liferay.list.type.service.persistence.ListTypeDefinitionPersistence;
import com.liferay.list.type.service.persistence.ListTypeDefinitionUtil;
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
import com.liferay.portal.kernel.util.Time;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PersistenceTestRule;
import com.liferay.portal.test.rule.TransactionalTestRule;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @generated
 */
@RunWith(Arquillian.class)
public class ListTypeDefinitionPersistenceTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(), PersistenceTestRule.INSTANCE,
			new TransactionalTestRule(
				Propagation.REQUIRED, "com.liferay.list.type.service"));

	@Before
	public void setUp() {
		_persistence = ListTypeDefinitionUtil.getPersistence();

		Class<?> clazz = _persistence.getClass();

		_dynamicQueryClassLoader = clazz.getClassLoader();
	}

	@After
	public void tearDown() throws Exception {
		Iterator<ListTypeDefinition> iterator = _listTypeDefinitions.iterator();

		while (iterator.hasNext()) {
			_persistence.remove(iterator.next());

			iterator.remove();
		}
	}

	@Test
	public void testCreate() throws Exception {
		long pk = RandomTestUtil.nextLong();

		ListTypeDefinition listTypeDefinition = _persistence.create(pk);

		Assert.assertNotNull(listTypeDefinition);

		Assert.assertEquals(listTypeDefinition.getPrimaryKey(), pk);
	}

	@Test
	public void testRemove() throws Exception {
		ListTypeDefinition newListTypeDefinition = addListTypeDefinition();

		_persistence.remove(newListTypeDefinition);

		ListTypeDefinition existingListTypeDefinition =
			_persistence.fetchByPrimaryKey(
				newListTypeDefinition.getPrimaryKey());

		Assert.assertNull(existingListTypeDefinition);
	}

	@Test
	public void testUpdateNew() throws Exception {
		addListTypeDefinition();
	}

	@Test
	public void testUpdateExisting() throws Exception {
		long pk = RandomTestUtil.nextLong();

		ListTypeDefinition newListTypeDefinition = _persistence.create(pk);

		newListTypeDefinition.setMvccVersion(RandomTestUtil.nextLong());

		newListTypeDefinition.setUuid(RandomTestUtil.randomString());

		newListTypeDefinition.setCompanyId(RandomTestUtil.nextLong());

		newListTypeDefinition.setUserId(RandomTestUtil.nextLong());

		newListTypeDefinition.setUserName(RandomTestUtil.randomString());

		newListTypeDefinition.setCreateDate(RandomTestUtil.nextDate());

		newListTypeDefinition.setModifiedDate(RandomTestUtil.nextDate());

		newListTypeDefinition.setName(RandomTestUtil.randomString());

		_listTypeDefinitions.add(_persistence.update(newListTypeDefinition));

		ListTypeDefinition existingListTypeDefinition =
			_persistence.findByPrimaryKey(
				newListTypeDefinition.getPrimaryKey());

		Assert.assertEquals(
			existingListTypeDefinition.getMvccVersion(),
			newListTypeDefinition.getMvccVersion());
		Assert.assertEquals(
			existingListTypeDefinition.getUuid(),
			newListTypeDefinition.getUuid());
		Assert.assertEquals(
			existingListTypeDefinition.getListTypeDefinitionId(),
			newListTypeDefinition.getListTypeDefinitionId());
		Assert.assertEquals(
			existingListTypeDefinition.getCompanyId(),
			newListTypeDefinition.getCompanyId());
		Assert.assertEquals(
			existingListTypeDefinition.getUserId(),
			newListTypeDefinition.getUserId());
		Assert.assertEquals(
			existingListTypeDefinition.getUserName(),
			newListTypeDefinition.getUserName());
		Assert.assertEquals(
			Time.getShortTimestamp(existingListTypeDefinition.getCreateDate()),
			Time.getShortTimestamp(newListTypeDefinition.getCreateDate()));
		Assert.assertEquals(
			Time.getShortTimestamp(
				existingListTypeDefinition.getModifiedDate()),
			Time.getShortTimestamp(newListTypeDefinition.getModifiedDate()));
		Assert.assertEquals(
			existingListTypeDefinition.getName(),
			newListTypeDefinition.getName());
	}

	@Test
	public void testCountByUuid() throws Exception {
		_persistence.countByUuid("");

		_persistence.countByUuid("null");

		_persistence.countByUuid((String)null);
	}

	@Test
	public void testCountByUuid_C() throws Exception {
		_persistence.countByUuid_C("", RandomTestUtil.nextLong());

		_persistence.countByUuid_C("null", 0L);

		_persistence.countByUuid_C((String)null, 0L);
	}

	@Test
	public void testFindByPrimaryKeyExisting() throws Exception {
		ListTypeDefinition newListTypeDefinition = addListTypeDefinition();

		ListTypeDefinition existingListTypeDefinition =
			_persistence.findByPrimaryKey(
				newListTypeDefinition.getPrimaryKey());

		Assert.assertEquals(existingListTypeDefinition, newListTypeDefinition);
	}

	@Test(expected = NoSuchListTypeDefinitionException.class)
	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		_persistence.findByPrimaryKey(pk);
	}

	@Test
	public void testFindAll() throws Exception {
		_persistence.findAll(
			QueryUtil.ALL_POS, QueryUtil.ALL_POS, getOrderByComparator());
	}

	protected OrderByComparator<ListTypeDefinition> getOrderByComparator() {
		return OrderByComparatorFactoryUtil.create(
			"ListTypeDefinition", "mvccVersion", true, "uuid", true,
			"listTypeDefinitionId", true, "companyId", true, "userId", true,
			"userName", true, "createDate", true, "modifiedDate", true, "name",
			true);
	}

	@Test
	public void testFetchByPrimaryKeyExisting() throws Exception {
		ListTypeDefinition newListTypeDefinition = addListTypeDefinition();

		ListTypeDefinition existingListTypeDefinition =
			_persistence.fetchByPrimaryKey(
				newListTypeDefinition.getPrimaryKey());

		Assert.assertEquals(existingListTypeDefinition, newListTypeDefinition);
	}

	@Test
	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		ListTypeDefinition missingListTypeDefinition =
			_persistence.fetchByPrimaryKey(pk);

		Assert.assertNull(missingListTypeDefinition);
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereAllPrimaryKeysExist()
		throws Exception {

		ListTypeDefinition newListTypeDefinition1 = addListTypeDefinition();
		ListTypeDefinition newListTypeDefinition2 = addListTypeDefinition();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newListTypeDefinition1.getPrimaryKey());
		primaryKeys.add(newListTypeDefinition2.getPrimaryKey());

		Map<Serializable, ListTypeDefinition> listTypeDefinitions =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(2, listTypeDefinitions.size());
		Assert.assertEquals(
			newListTypeDefinition1,
			listTypeDefinitions.get(newListTypeDefinition1.getPrimaryKey()));
		Assert.assertEquals(
			newListTypeDefinition2,
			listTypeDefinitions.get(newListTypeDefinition2.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereNoPrimaryKeysExist()
		throws Exception {

		long pk1 = RandomTestUtil.nextLong();

		long pk2 = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(pk1);
		primaryKeys.add(pk2);

		Map<Serializable, ListTypeDefinition> listTypeDefinitions =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(listTypeDefinitions.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereSomePrimaryKeysExist()
		throws Exception {

		ListTypeDefinition newListTypeDefinition = addListTypeDefinition();

		long pk = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newListTypeDefinition.getPrimaryKey());
		primaryKeys.add(pk);

		Map<Serializable, ListTypeDefinition> listTypeDefinitions =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, listTypeDefinitions.size());
		Assert.assertEquals(
			newListTypeDefinition,
			listTypeDefinitions.get(newListTypeDefinition.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithNoPrimaryKeys() throws Exception {
		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		Map<Serializable, ListTypeDefinition> listTypeDefinitions =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(listTypeDefinitions.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithOnePrimaryKey() throws Exception {
		ListTypeDefinition newListTypeDefinition = addListTypeDefinition();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newListTypeDefinition.getPrimaryKey());

		Map<Serializable, ListTypeDefinition> listTypeDefinitions =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, listTypeDefinitions.size());
		Assert.assertEquals(
			newListTypeDefinition,
			listTypeDefinitions.get(newListTypeDefinition.getPrimaryKey()));
	}

	@Test
	public void testActionableDynamicQuery() throws Exception {
		final IntegerWrapper count = new IntegerWrapper();

		ActionableDynamicQuery actionableDynamicQuery =
			ListTypeDefinitionLocalServiceUtil.getActionableDynamicQuery();

		actionableDynamicQuery.setPerformActionMethod(
			new ActionableDynamicQuery.PerformActionMethod
				<ListTypeDefinition>() {

				@Override
				public void performAction(
					ListTypeDefinition listTypeDefinition) {

					Assert.assertNotNull(listTypeDefinition);

					count.increment();
				}

			});

		actionableDynamicQuery.performActions();

		Assert.assertEquals(count.getValue(), _persistence.countAll());
	}

	@Test
	public void testDynamicQueryByPrimaryKeyExisting() throws Exception {
		ListTypeDefinition newListTypeDefinition = addListTypeDefinition();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			ListTypeDefinition.class, _dynamicQueryClassLoader);

		dynamicQuery.add(
			RestrictionsFactoryUtil.eq(
				"listTypeDefinitionId",
				newListTypeDefinition.getListTypeDefinitionId()));

		List<ListTypeDefinition> result = _persistence.findWithDynamicQuery(
			dynamicQuery);

		Assert.assertEquals(1, result.size());

		ListTypeDefinition existingListTypeDefinition = result.get(0);

		Assert.assertEquals(existingListTypeDefinition, newListTypeDefinition);
	}

	@Test
	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			ListTypeDefinition.class, _dynamicQueryClassLoader);

		dynamicQuery.add(
			RestrictionsFactoryUtil.eq(
				"listTypeDefinitionId", RandomTestUtil.nextLong()));

		List<ListTypeDefinition> result = _persistence.findWithDynamicQuery(
			dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testDynamicQueryByProjectionExisting() throws Exception {
		ListTypeDefinition newListTypeDefinition = addListTypeDefinition();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			ListTypeDefinition.class, _dynamicQueryClassLoader);

		dynamicQuery.setProjection(
			ProjectionFactoryUtil.property("listTypeDefinitionId"));

		Object newListTypeDefinitionId =
			newListTypeDefinition.getListTypeDefinitionId();

		dynamicQuery.add(
			RestrictionsFactoryUtil.in(
				"listTypeDefinitionId",
				new Object[] {newListTypeDefinitionId}));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		Object existingListTypeDefinitionId = result.get(0);

		Assert.assertEquals(
			existingListTypeDefinitionId, newListTypeDefinitionId);
	}

	@Test
	public void testDynamicQueryByProjectionMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			ListTypeDefinition.class, _dynamicQueryClassLoader);

		dynamicQuery.setProjection(
			ProjectionFactoryUtil.property("listTypeDefinitionId"));

		dynamicQuery.add(
			RestrictionsFactoryUtil.in(
				"listTypeDefinitionId",
				new Object[] {RandomTestUtil.nextLong()}));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	protected ListTypeDefinition addListTypeDefinition() throws Exception {
		long pk = RandomTestUtil.nextLong();

		ListTypeDefinition listTypeDefinition = _persistence.create(pk);

		listTypeDefinition.setMvccVersion(RandomTestUtil.nextLong());

		listTypeDefinition.setUuid(RandomTestUtil.randomString());

		listTypeDefinition.setCompanyId(RandomTestUtil.nextLong());

		listTypeDefinition.setUserId(RandomTestUtil.nextLong());

		listTypeDefinition.setUserName(RandomTestUtil.randomString());

		listTypeDefinition.setCreateDate(RandomTestUtil.nextDate());

		listTypeDefinition.setModifiedDate(RandomTestUtil.nextDate());

		listTypeDefinition.setName(RandomTestUtil.randomString());

		_listTypeDefinitions.add(_persistence.update(listTypeDefinition));

		return listTypeDefinition;
	}

	private List<ListTypeDefinition> _listTypeDefinitions =
		new ArrayList<ListTypeDefinition>();
	private ListTypeDefinitionPersistence _persistence;
	private ClassLoader _dynamicQueryClassLoader;

}