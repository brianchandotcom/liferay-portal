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

package com.liferay.portal.service.persistence;

import com.liferay.portal.NoSuchResourceActionException;
import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQueryFactoryUtil;
import com.liferay.portal.kernel.dao.orm.ProjectionFactoryUtil;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.RestrictionsFactoryUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.test.ExecutionTestListeners;
import com.liferay.portal.kernel.util.IntegerWrapper;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.OrderByComparatorFactoryUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.ModelListener;
import com.liferay.portal.model.ResourceAction;
import com.liferay.portal.model.impl.ResourceActionModelImpl;
import com.liferay.portal.service.ResourceActionLocalServiceUtil;
import com.liferay.portal.service.persistence.BasePersistence;
import com.liferay.portal.service.persistence.PersistenceExecutionTestListener;
import com.liferay.portal.test.LiferayPersistenceIntegrationJUnitTestRunner;
import com.liferay.portal.test.persistence.test.TransactionalPersistenceAdvice;
import com.liferay.portal.util.PropsValues;
import com.liferay.portal.util.test.RandomTestUtil;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import org.junit.runner.RunWith;

import java.io.Serializable;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author Brian Wing Shun Chan
 */
@ExecutionTestListeners(listeners =  {
	PersistenceExecutionTestListener.class})
@RunWith(LiferayPersistenceIntegrationJUnitTestRunner.class)
public class ResourceActionPersistenceTest {
	@Before
	public void setUp() {
		_modelListeners = _persistence.getListeners();

		for (ModelListener<ResourceAction> modelListener : _modelListeners) {
			_persistence.unregisterListener(modelListener);
		}
	}

	@After
	public void tearDown() throws Exception {
		Map<Serializable, BasePersistence<?>> basePersistences = _transactionalPersistenceAdvice.getBasePersistences();

		Set<Serializable> primaryKeys = basePersistences.keySet();

		for (Serializable primaryKey : primaryKeys) {
			BasePersistence<?> basePersistence = basePersistences.get(primaryKey);

			try {
				basePersistence.remove(primaryKey);
			}
			catch (Exception e) {
				if (_log.isDebugEnabled()) {
					_log.debug("The model with primary key " + primaryKey +
						" was already deleted");
				}
			}
		}

		_transactionalPersistenceAdvice.reset();

		for (ModelListener<ResourceAction> modelListener : _modelListeners) {
			_persistence.registerListener(modelListener);
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

		_persistence.update(newResourceAction);

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
	public void testCountByName() {
		try {
			_persistence.countByName(StringPool.BLANK);

			_persistence.countByName(StringPool.NULL);

			_persistence.countByName((String)null);
		}
		catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void testCountByN_A() {
		try {
			_persistence.countByN_A(StringPool.BLANK, StringPool.BLANK);

			_persistence.countByN_A(StringPool.NULL, StringPool.NULL);

			_persistence.countByN_A((String)null, (String)null);
		}
		catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void testFindByPrimaryKeyExisting() throws Exception {
		ResourceAction newResourceAction = addResourceAction();

		ResourceAction existingResourceAction = _persistence.findByPrimaryKey(newResourceAction.getPrimaryKey());

		Assert.assertEquals(existingResourceAction, newResourceAction);
	}

	@Test
	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		try {
			_persistence.findByPrimaryKey(pk);

			Assert.fail(
				"Missing entity did not throw NoSuchResourceActionException");
		}
		catch (NoSuchResourceActionException nsee) {
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

	protected OrderByComparator getOrderByComparator() {
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
				ResourceAction.class.getClassLoader());

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
				ResourceAction.class.getClassLoader());

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
				ResourceAction.class.getClassLoader());

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
				ResourceAction.class.getClassLoader());

		dynamicQuery.setProjection(ProjectionFactoryUtil.property(
				"resourceActionId"));

		dynamicQuery.add(RestrictionsFactoryUtil.in("resourceActionId",
				new Object[] { RandomTestUtil.nextLong() }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testResetOriginalValues() throws Exception {
		if (!PropsValues.HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE) {
			return;
		}

		ResourceAction newResourceAction = addResourceAction();

		_persistence.clearCache();

		ResourceActionModelImpl existingResourceActionModelImpl = (ResourceActionModelImpl)_persistence.findByPrimaryKey(newResourceAction.getPrimaryKey());

		Assert.assertTrue(Validator.equals(
				existingResourceActionModelImpl.getName(),
				existingResourceActionModelImpl.getOriginalName()));
		Assert.assertTrue(Validator.equals(
				existingResourceActionModelImpl.getActionId(),
				existingResourceActionModelImpl.getOriginalActionId()));
	}

	protected ResourceAction addResourceAction() throws Exception {
		long pk = RandomTestUtil.nextLong();

		ResourceAction resourceAction = _persistence.create(pk);

		resourceAction.setMvccVersion(RandomTestUtil.nextLong());

		resourceAction.setName(RandomTestUtil.randomString());

		resourceAction.setActionId(RandomTestUtil.randomString());

		resourceAction.setBitwiseValue(RandomTestUtil.nextLong());

		_persistence.update(resourceAction);

		return resourceAction;
	}

	private static Log _log = LogFactoryUtil.getLog(ResourceActionPersistenceTest.class);
	private ModelListener<ResourceAction>[] _modelListeners;
	private ResourceActionPersistence _persistence = (ResourceActionPersistence)PortalBeanLocatorUtil.locate(ResourceActionPersistence.class.getName());
	private TransactionalPersistenceAdvice _transactionalPersistenceAdvice = (TransactionalPersistenceAdvice)PortalBeanLocatorUtil.locate(TransactionalPersistenceAdvice.class.getName());
}