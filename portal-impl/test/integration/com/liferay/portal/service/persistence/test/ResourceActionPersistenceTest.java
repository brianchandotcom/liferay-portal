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

import com.liferay.portal.NoSuchResourceActionException;
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
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.ResourceAction;
import com.liferay.portal.service.ResourceActionLocalServiceUtil;
import com.liferay.portal.service.persistence.ResourceActionPersistence;
import com.liferay.portal.service.persistence.ResourceActionUtil;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PersistenceTestRule;

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
public class ResourceActionPersistenceTest {
	@Rule
	public final AggregateTestRule aggregateTestRule = new AggregateTestRule(new LiferayIntegrationTestRule(),
			PersistenceTestRule.INSTANCE,
			new TransactionalTestRule(Propagation.REQUIRED));

	@Before
	public void setUp() {
		_persistence = ResourceActionUtil.getPersistence();

		Class<?> clazz = _persistence.getClass();

		_dynamicQueryClassLoader = clazz.getClassLoader();
	}

	@After
	public void tearDown() throws Exception {
		Iterator<ResourceAction> iterator = _resourceActions.iterator();

		while (iterator.hasNext()) {
			_persistence.remove(iterator.next());

			iterator.remove();
		}
	}

	@Test
	public void testCreate() throws Exception {
		long pk = RandomTestUtil.nextLong();

		ResourceAction resourceAction = _persistence.create(pk);

		Assert.assertNotNull(resourceAction);

		Assert.assertEquals(resourceAction.getPrimaryKey(), pk);
	}

	@Test
	public void testRemove() throws Exception {
		ResourceAction newResourceAction = addResourceAction();

		_persistence.remove(newResourceAction);

		ResourceAction existingResourceAction = _persistence.fetchByPrimaryKey(newResourceAction.getPrimaryKey());

		Assert.assertNull(existingResourceAction);
	}

	@Test
	public void testUpdateNew() throws Exception {
		addResourceAction();
	}

	@Test
	public void testUpdateExisting() throws Exception {
		long pk = RandomTestUtil.nextLong();

		ResourceAction newResourceAction = _persistence.create(pk);

		newResourceAction.setMvccVersion(RandomTestUtil.nextLong());

		newResourceAction.setName(RandomTestUtil.randomString());

		newResourceAction.setActionId(RandomTestUtil.randomString());

		newResourceAction.setBitwiseValue(RandomTestUtil.nextLong());

		_resourceActions.add(_persistence.update(newResourceAction));

		ResourceAction existingResourceAction = _persistence.findByPrimaryKey(newResourceAction.getPrimaryKey());

		Assert.assertEquals(existingResourceAction.getMvccVersion(),
			newResourceAction.getMvccVersion());
		Assert.assertEquals(existingResourceAction.getResourceActionId(),
			newResourceAction.getResourceActionId());
		Assert.assertEquals(existingResourceAction.getName(),
			newResourceAction.getName());
		Assert.assertEquals(existingResourceAction.getActionId(),
			newResourceAction.getActionId());
		Assert.assertEquals(existingResourceAction.getBitwiseValue(),
			newResourceAction.getBitwiseValue());
	}

	@Test
	public void testCountByName() throws Exception {
		_persistence.countByName(StringPool.BLANK);

		_persistence.countByName(StringPool.NULL);

		_persistence.countByName((String)null);
	}

	@Test
	public void testCountByN_A() throws Exception {
		_persistence.countByN_A(StringPool.BLANK, StringPool.BLANK);

		_persistence.countByN_A(StringPool.NULL, StringPool.NULL);

		_persistence.countByN_A((String)null, (String)null);
	}

	@Test
	public void testFindByPrimaryKeyExisting() throws Exception {
		ResourceAction newResourceAction = addResourceAction();

		ResourceAction existingResourceAction = _persistence.findByPrimaryKey(newResourceAction.getPrimaryKey());

		Assert.assertEquals(existingResourceAction, newResourceAction);
	}

	@Test(expected = NoSuchResourceActionException.class)
	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		_persistence.findByPrimaryKey(pk);
	}

	@Test
	public void testFindAll() throws Exception {
		_persistence.findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			getOrderByComparator());
	}

	protected OrderByComparator<ResourceAction> getOrderByComparator() {
		return OrderByComparatorFactoryUtil.create("ResourceAction",
			"mvccVersion", true, "resourceActionId", true, "name", true,
			"actionId", true, "bitwiseValue", true);
	}

	@Test
	public void testFetchByPrimaryKeyExisting() throws Exception {
		ResourceAction newResourceAction = addResourceAction();

		ResourceAction existingResourceAction = _persistence.fetchByPrimaryKey(newResourceAction.getPrimaryKey());

		Assert.assertEquals(existingResourceAction, newResourceAction);
	}

	@Test
	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		ResourceAction missingResourceAction = _persistence.fetchByPrimaryKey(pk);

		Assert.assertNull(missingResourceAction);
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereAllPrimaryKeysExist()
		throws Exception {
		ResourceAction newResourceAction1 = addResourceAction();
		ResourceAction newResourceAction2 = addResourceAction();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newResourceAction1.getPrimaryKey());
		primaryKeys.add(newResourceAction2.getPrimaryKey());

		Map<Serializable, ResourceAction> resourceActions = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(2, resourceActions.size());
		Assert.assertEquals(newResourceAction1,
			resourceActions.get(newResourceAction1.getPrimaryKey()));
		Assert.assertEquals(newResourceAction2,
			resourceActions.get(newResourceAction2.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereNoPrimaryKeysExist()
		throws Exception {
		long pk1 = RandomTestUtil.nextLong();

		long pk2 = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(pk1);
		primaryKeys.add(pk2);

		Map<Serializable, ResourceAction> resourceActions = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(resourceActions.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereSomePrimaryKeysExist()
		throws Exception {
		ResourceAction newResourceAction = addResourceAction();

		long pk = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newResourceAction.getPrimaryKey());
		primaryKeys.add(pk);

		Map<Serializable, ResourceAction> resourceActions = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, resourceActions.size());
		Assert.assertEquals(newResourceAction,
			resourceActions.get(newResourceAction.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithNoPrimaryKeys()
		throws Exception {
		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		Map<Serializable, ResourceAction> resourceActions = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(resourceActions.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithOnePrimaryKey()
		throws Exception {
		ResourceAction newResourceAction = addResourceAction();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newResourceAction.getPrimaryKey());

		Map<Serializable, ResourceAction> resourceActions = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, resourceActions.size());
		Assert.assertEquals(newResourceAction,
			resourceActions.get(newResourceAction.getPrimaryKey()));
	}

	@Test
	public void testActionableDynamicQuery() throws Exception {
		final IntegerWrapper count = new IntegerWrapper();

		ActionableDynamicQuery actionableDynamicQuery = ResourceActionLocalServiceUtil.getActionableDynamicQuery();

		actionableDynamicQuery.setPerformActionMethod(new ActionableDynamicQuery.PerformActionMethod() {
				@Override
				public void performAction(Object object) {
					ResourceAction resourceAction = (ResourceAction)object;

					Assert.assertNotNull(resourceAction);

					count.increment();
				}
			});

		actionableDynamicQuery.performActions();

		Assert.assertEquals(count.getValue(), _persistence.countAll());
	}

	@Test
	public void testDynamicQueryByPrimaryKeyExisting()
		throws Exception {
		ResourceAction newResourceAction = addResourceAction();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(ResourceAction.class,
				_dynamicQueryClassLoader);

		dynamicQuery.add(RestrictionsFactoryUtil.eq("resourceActionId",
				newResourceAction.getResourceActionId()));

		List<ResourceAction> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		ResourceAction existingResourceAction = result.get(0);

		Assert.assertEquals(existingResourceAction, newResourceAction);
	}

	@Test
	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(ResourceAction.class,
				_dynamicQueryClassLoader);

		dynamicQuery.add(RestrictionsFactoryUtil.eq("resourceActionId",
				RandomTestUtil.nextLong()));

		List<ResourceAction> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testDynamicQueryByProjectionExisting()
		throws Exception {
		ResourceAction newResourceAction = addResourceAction();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(ResourceAction.class,
				_dynamicQueryClassLoader);

		dynamicQuery.setProjection(ProjectionFactoryUtil.property(
				"resourceActionId"));

		Object newResourceActionId = newResourceAction.getResourceActionId();

		dynamicQuery.add(RestrictionsFactoryUtil.in("resourceActionId",
				new Object[] { newResourceActionId }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		Object existingResourceActionId = result.get(0);

		Assert.assertEquals(existingResourceActionId, newResourceActionId);
	}

	@Test
	public void testDynamicQueryByProjectionMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(ResourceAction.class,
				_dynamicQueryClassLoader);

		dynamicQuery.setProjection(ProjectionFactoryUtil.property(
				"resourceActionId"));

		dynamicQuery.add(RestrictionsFactoryUtil.in("resourceActionId",
				new Object[] { RandomTestUtil.nextLong() }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testResetOriginalValues() throws Exception {
		ResourceAction newResourceAction = addResourceAction();

		_persistence.clearCache();

		ResourceAction existingResourceAction = _persistence.findByPrimaryKey(newResourceAction.getPrimaryKey());

		Assert.assertTrue(Validator.equals(existingResourceAction.getName(),
				ReflectionTestUtil.invoke(existingResourceAction,
					"getOriginalName", new Class<?>[0])));
		Assert.assertTrue(Validator.equals(
				existingResourceAction.getActionId(),
				ReflectionTestUtil.invoke(existingResourceAction,
					"getOriginalActionId", new Class<?>[0])));
	}

	protected ResourceAction addResourceAction() throws Exception {
		long pk = RandomTestUtil.nextLong();

		ResourceAction resourceAction = _persistence.create(pk);

		resourceAction.setMvccVersion(RandomTestUtil.nextLong());

		resourceAction.setName(RandomTestUtil.randomString());

		resourceAction.setActionId(RandomTestUtil.randomString());

		resourceAction.setBitwiseValue(RandomTestUtil.nextLong());

		_resourceActions.add(_persistence.update(resourceAction));

		return resourceAction;
	}

	private List<ResourceAction> _resourceActions = new ArrayList<ResourceAction>();
	private ResourceActionPersistence _persistence;
	private ClassLoader _dynamicQueryClassLoader;
}