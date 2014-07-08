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

import com.liferay.portal.NoSuchClusterGroupException;
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
import com.liferay.portal.model.ClusterGroup;
import com.liferay.portal.model.ModelListener;
import com.liferay.portal.service.ClusterGroupLocalServiceUtil;
import com.liferay.portal.service.persistence.BasePersistence;
import com.liferay.portal.service.persistence.PersistenceExecutionTestListener;
import com.liferay.portal.test.LiferayPersistenceIntegrationJUnitTestRunner;
import com.liferay.portal.test.persistence.test.TransactionalPersistenceAdvice;
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
public class ClusterGroupPersistenceTest {
	@Before
	public void setUp() {
		_modelListeners = _persistence.getListeners();

		for (ModelListener<ClusterGroup> modelListener : _modelListeners) {
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

		for (ModelListener<ClusterGroup> modelListener : _modelListeners) {
			_persistence.registerListener(modelListener);
		}
	}

	@Test
	public void testCreate() throws Exception {
		long pk = RandomTestUtil.nextLong();

		ClusterGroup clusterGroup = _persistence.create(pk);

		Assert.assertNotNull(clusterGroup);

		Assert.assertEquals(clusterGroup.getPrimaryKey(), pk);
	}

	@Test
	public void testRemove() throws Exception {
		ClusterGroup newClusterGroup = addClusterGroup();

		_persistence.remove(newClusterGroup);

		ClusterGroup existingClusterGroup = _persistence.fetchByPrimaryKey(newClusterGroup.getPrimaryKey());

		Assert.assertNull(existingClusterGroup);
	}

	@Test
	public void testUpdateNew() throws Exception {
		addClusterGroup();
	}

	@Test
	public void testUpdateExisting() throws Exception {
		long pk = RandomTestUtil.nextLong();

		ClusterGroup newClusterGroup = _persistence.create(pk);

		newClusterGroup.setMvccVersion(RandomTestUtil.nextLong());

		newClusterGroup.setName(RandomTestUtil.randomString());

		newClusterGroup.setClusterNodeIds(RandomTestUtil.randomString());

		newClusterGroup.setWholeCluster(RandomTestUtil.randomBoolean());

		_persistence.update(newClusterGroup);

		ClusterGroup existingClusterGroup = _persistence.findByPrimaryKey(newClusterGroup.getPrimaryKey());

		Assert.assertEquals(existingClusterGroup.getMvccVersion(),
			newClusterGroup.getMvccVersion());
		Assert.assertEquals(existingClusterGroup.getClusterGroupId(),
			newClusterGroup.getClusterGroupId());
		Assert.assertEquals(existingClusterGroup.getName(),
			newClusterGroup.getName());
		Assert.assertEquals(existingClusterGroup.getClusterNodeIds(),
			newClusterGroup.getClusterNodeIds());
		Assert.assertEquals(existingClusterGroup.getWholeCluster(),
			newClusterGroup.getWholeCluster());
	}

	@Test
	public void testFindByPrimaryKeyExisting() throws Exception {
		ClusterGroup newClusterGroup = addClusterGroup();

		ClusterGroup existingClusterGroup = _persistence.findByPrimaryKey(newClusterGroup.getPrimaryKey());

		Assert.assertEquals(existingClusterGroup, newClusterGroup);
	}

	@Test
	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		try {
			_persistence.findByPrimaryKey(pk);

			Assert.fail(
				"Missing entity did not throw NoSuchClusterGroupException");
		}
		catch (NoSuchClusterGroupException nsee) {
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
		return OrderByComparatorFactoryUtil.create("ClusterGroup",
			"mvccVersion", true, "clusterGroupId", true, "name", true,
			"clusterNodeIds", true, "wholeCluster", true);
	}

	@Test
	public void testFetchByPrimaryKeyExisting() throws Exception {
		ClusterGroup newClusterGroup = addClusterGroup();

		ClusterGroup existingClusterGroup = _persistence.fetchByPrimaryKey(newClusterGroup.getPrimaryKey());

		Assert.assertEquals(existingClusterGroup, newClusterGroup);
	}

	@Test
	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		ClusterGroup missingClusterGroup = _persistence.fetchByPrimaryKey(pk);

		Assert.assertNull(missingClusterGroup);
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereAllPrimaryKeysExist()
		throws Exception {
		ClusterGroup newClusterGroup1 = addClusterGroup();
		ClusterGroup newClusterGroup2 = addClusterGroup();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newClusterGroup1.getPrimaryKey());
		primaryKeys.add(newClusterGroup2.getPrimaryKey());

		Map<Serializable, ClusterGroup> clusterGroups = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(2, clusterGroups.size());
		Assert.assertEquals(newClusterGroup1,
			clusterGroups.get(newClusterGroup1.getPrimaryKey()));
		Assert.assertEquals(newClusterGroup2,
			clusterGroups.get(newClusterGroup2.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereNoPrimaryKeysExist()
		throws Exception {
		long pk1 = RandomTestUtil.nextLong();

		long pk2 = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(pk1);
		primaryKeys.add(pk2);

		Map<Serializable, ClusterGroup> clusterGroups = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(clusterGroups.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereSomePrimaryKeysExist()
		throws Exception {
		ClusterGroup newClusterGroup = addClusterGroup();

		long pk = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newClusterGroup.getPrimaryKey());
		primaryKeys.add(pk);

		Map<Serializable, ClusterGroup> clusterGroups = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, clusterGroups.size());
		Assert.assertEquals(newClusterGroup,
			clusterGroups.get(newClusterGroup.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithNoPrimaryKeys()
		throws Exception {
		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		Map<Serializable, ClusterGroup> clusterGroups = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(clusterGroups.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithOnePrimaryKey()
		throws Exception {
		ClusterGroup newClusterGroup = addClusterGroup();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newClusterGroup.getPrimaryKey());

		Map<Serializable, ClusterGroup> clusterGroups = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, clusterGroups.size());
		Assert.assertEquals(newClusterGroup,
			clusterGroups.get(newClusterGroup.getPrimaryKey()));
	}

	@Test
	public void testActionableDynamicQuery() throws Exception {
		final IntegerWrapper count = new IntegerWrapper();

		ActionableDynamicQuery actionableDynamicQuery = ClusterGroupLocalServiceUtil.getActionableDynamicQuery();

		actionableDynamicQuery.setPerformActionMethod(new ActionableDynamicQuery.PerformActionMethod() {
				@Override
				public void performAction(Object object) {
					ClusterGroup clusterGroup = (ClusterGroup)object;

					Assert.assertNotNull(clusterGroup);

					count.increment();
				}
			});

		actionableDynamicQuery.performActions();

		Assert.assertEquals(count.getValue(), _persistence.countAll());
	}

	@Test
	public void testDynamicQueryByPrimaryKeyExisting()
		throws Exception {
		ClusterGroup newClusterGroup = addClusterGroup();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(ClusterGroup.class,
				ClusterGroup.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("clusterGroupId",
				newClusterGroup.getClusterGroupId()));

		List<ClusterGroup> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		ClusterGroup existingClusterGroup = result.get(0);

		Assert.assertEquals(existingClusterGroup, newClusterGroup);
	}

	@Test
	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(ClusterGroup.class,
				ClusterGroup.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("clusterGroupId",
				RandomTestUtil.nextLong()));

		List<ClusterGroup> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testDynamicQueryByProjectionExisting()
		throws Exception {
		ClusterGroup newClusterGroup = addClusterGroup();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(ClusterGroup.class,
				ClusterGroup.class.getClassLoader());

		dynamicQuery.setProjection(ProjectionFactoryUtil.property(
				"clusterGroupId"));

		Object newClusterGroupId = newClusterGroup.getClusterGroupId();

		dynamicQuery.add(RestrictionsFactoryUtil.in("clusterGroupId",
				new Object[] { newClusterGroupId }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		Object existingClusterGroupId = result.get(0);

		Assert.assertEquals(existingClusterGroupId, newClusterGroupId);
	}

	@Test
	public void testDynamicQueryByProjectionMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(ClusterGroup.class,
				ClusterGroup.class.getClassLoader());

		dynamicQuery.setProjection(ProjectionFactoryUtil.property(
				"clusterGroupId"));

		dynamicQuery.add(RestrictionsFactoryUtil.in("clusterGroupId",
				new Object[] { RandomTestUtil.nextLong() }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	protected ClusterGroup addClusterGroup() throws Exception {
		long pk = RandomTestUtil.nextLong();

		ClusterGroup clusterGroup = _persistence.create(pk);

		clusterGroup.setMvccVersion(RandomTestUtil.nextLong());

		clusterGroup.setName(RandomTestUtil.randomString());

		clusterGroup.setClusterNodeIds(RandomTestUtil.randomString());

		clusterGroup.setWholeCluster(RandomTestUtil.randomBoolean());

		_persistence.update(clusterGroup);

		return clusterGroup;
	}

	private static Log _log = LogFactoryUtil.getLog(ClusterGroupPersistenceTest.class);
	private ModelListener<ClusterGroup>[] _modelListeners;
	private ClusterGroupPersistence _persistence = (ClusterGroupPersistence)PortalBeanLocatorUtil.locate(ClusterGroupPersistence.class.getName());
	private TransactionalPersistenceAdvice _transactionalPersistenceAdvice = (TransactionalPersistenceAdvice)PortalBeanLocatorUtil.locate(TransactionalPersistenceAdvice.class.getName());
}