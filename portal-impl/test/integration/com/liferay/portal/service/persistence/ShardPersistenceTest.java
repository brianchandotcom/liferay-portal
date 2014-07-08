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

import com.liferay.portal.NoSuchShardException;
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
import com.liferay.portal.model.Shard;
import com.liferay.portal.model.impl.ShardModelImpl;
import com.liferay.portal.service.ShardLocalServiceUtil;
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
public class ShardPersistenceTest {
	@Before
	public void setUp() {
		_modelListeners = _persistence.getListeners();

		for (ModelListener<Shard> modelListener : _modelListeners) {
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

		for (ModelListener<Shard> modelListener : _modelListeners) {
			_persistence.registerListener(modelListener);
		}
	}

	@Test
	public void testCreate() throws Exception {
		long pk = RandomTestUtil.nextLong();

		Shard shard = _persistence.create(pk);

		Assert.assertNotNull(shard);

		Assert.assertEquals(shard.getPrimaryKey(), pk);
	}

	@Test
	public void testRemove() throws Exception {
		Shard newShard = addShard();

		_persistence.remove(newShard);

		Shard existingShard = _persistence.fetchByPrimaryKey(newShard.getPrimaryKey());

		Assert.assertNull(existingShard);
	}

	@Test
	public void testUpdateNew() throws Exception {
		addShard();
	}

	@Test
	public void testUpdateExisting() throws Exception {
		long pk = RandomTestUtil.nextLong();

		Shard newShard = _persistence.create(pk);

		newShard.setMvccVersion(RandomTestUtil.nextLong());

		newShard.setClassNameId(RandomTestUtil.nextLong());

		newShard.setClassPK(RandomTestUtil.nextLong());

		newShard.setName(RandomTestUtil.randomString());

		_persistence.update(newShard);

		Shard existingShard = _persistence.findByPrimaryKey(newShard.getPrimaryKey());

		Assert.assertEquals(existingShard.getMvccVersion(),
			newShard.getMvccVersion());
		Assert.assertEquals(existingShard.getShardId(), newShard.getShardId());
		Assert.assertEquals(existingShard.getClassNameId(),
			newShard.getClassNameId());
		Assert.assertEquals(existingShard.getClassPK(), newShard.getClassPK());
		Assert.assertEquals(existingShard.getName(), newShard.getName());
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
	public void testCountByC_C() {
		try {
			_persistence.countByC_C(RandomTestUtil.nextLong(),
				RandomTestUtil.nextLong());

			_persistence.countByC_C(0L, 0L);
		}
		catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void testFindByPrimaryKeyExisting() throws Exception {
		Shard newShard = addShard();

		Shard existingShard = _persistence.findByPrimaryKey(newShard.getPrimaryKey());

		Assert.assertEquals(existingShard, newShard);
	}

	@Test
	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		try {
			_persistence.findByPrimaryKey(pk);

			Assert.fail("Missing entity did not throw NoSuchShardException");
		}
		catch (NoSuchShardException nsee) {
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
		return OrderByComparatorFactoryUtil.create("Shard", "mvccVersion",
			true, "shardId", true, "classNameId", true, "classPK", true,
			"name", true);
	}

	@Test
	public void testFetchByPrimaryKeyExisting() throws Exception {
		Shard newShard = addShard();

		Shard existingShard = _persistence.fetchByPrimaryKey(newShard.getPrimaryKey());

		Assert.assertEquals(existingShard, newShard);
	}

	@Test
	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		Shard missingShard = _persistence.fetchByPrimaryKey(pk);

		Assert.assertNull(missingShard);
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereAllPrimaryKeysExist()
		throws Exception {
		Shard newShard1 = addShard();
		Shard newShard2 = addShard();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newShard1.getPrimaryKey());
		primaryKeys.add(newShard2.getPrimaryKey());

		Map<Serializable, Shard> shards = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(2, shards.size());
		Assert.assertEquals(newShard1, shards.get(newShard1.getPrimaryKey()));
		Assert.assertEquals(newShard2, shards.get(newShard2.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereNoPrimaryKeysExist()
		throws Exception {
		long pk1 = RandomTestUtil.nextLong();

		long pk2 = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(pk1);
		primaryKeys.add(pk2);

		Map<Serializable, Shard> shards = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(shards.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereSomePrimaryKeysExist()
		throws Exception {
		Shard newShard = addShard();

		long pk = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newShard.getPrimaryKey());
		primaryKeys.add(pk);

		Map<Serializable, Shard> shards = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, shards.size());
		Assert.assertEquals(newShard, shards.get(newShard.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithNoPrimaryKeys()
		throws Exception {
		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		Map<Serializable, Shard> shards = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(shards.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithOnePrimaryKey()
		throws Exception {
		Shard newShard = addShard();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newShard.getPrimaryKey());

		Map<Serializable, Shard> shards = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, shards.size());
		Assert.assertEquals(newShard, shards.get(newShard.getPrimaryKey()));
	}

	@Test
	public void testActionableDynamicQuery() throws Exception {
		final IntegerWrapper count = new IntegerWrapper();

		ActionableDynamicQuery actionableDynamicQuery = ShardLocalServiceUtil.getActionableDynamicQuery();

		actionableDynamicQuery.setPerformActionMethod(new ActionableDynamicQuery.PerformActionMethod() {
				@Override
				public void performAction(Object object) {
					Shard shard = (Shard)object;

					Assert.assertNotNull(shard);

					count.increment();
				}
			});

		actionableDynamicQuery.performActions();

		Assert.assertEquals(count.getValue(), _persistence.countAll());
	}

	@Test
	public void testDynamicQueryByPrimaryKeyExisting()
		throws Exception {
		Shard newShard = addShard();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(Shard.class,
				Shard.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("shardId",
				newShard.getShardId()));

		List<Shard> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		Shard existingShard = result.get(0);

		Assert.assertEquals(existingShard, newShard);
	}

	@Test
	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(Shard.class,
				Shard.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("shardId",
				RandomTestUtil.nextLong()));

		List<Shard> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testDynamicQueryByProjectionExisting()
		throws Exception {
		Shard newShard = addShard();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(Shard.class,
				Shard.class.getClassLoader());

		dynamicQuery.setProjection(ProjectionFactoryUtil.property("shardId"));

		Object newShardId = newShard.getShardId();

		dynamicQuery.add(RestrictionsFactoryUtil.in("shardId",
				new Object[] { newShardId }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		Object existingShardId = result.get(0);

		Assert.assertEquals(existingShardId, newShardId);
	}

	@Test
	public void testDynamicQueryByProjectionMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(Shard.class,
				Shard.class.getClassLoader());

		dynamicQuery.setProjection(ProjectionFactoryUtil.property("shardId"));

		dynamicQuery.add(RestrictionsFactoryUtil.in("shardId",
				new Object[] { RandomTestUtil.nextLong() }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testResetOriginalValues() throws Exception {
		if (!PropsValues.HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE) {
			return;
		}

		Shard newShard = addShard();

		_persistence.clearCache();

		ShardModelImpl existingShardModelImpl = (ShardModelImpl)_persistence.findByPrimaryKey(newShard.getPrimaryKey());

		Assert.assertTrue(Validator.equals(existingShardModelImpl.getName(),
				existingShardModelImpl.getOriginalName()));

		Assert.assertEquals(existingShardModelImpl.getClassNameId(),
			existingShardModelImpl.getOriginalClassNameId());
		Assert.assertEquals(existingShardModelImpl.getClassPK(),
			existingShardModelImpl.getOriginalClassPK());
	}

	protected Shard addShard() throws Exception {
		long pk = RandomTestUtil.nextLong();

		Shard shard = _persistence.create(pk);

		shard.setMvccVersion(RandomTestUtil.nextLong());

		shard.setClassNameId(RandomTestUtil.nextLong());

		shard.setClassPK(RandomTestUtil.nextLong());

		shard.setName(RandomTestUtil.randomString());

		_persistence.update(shard);

		return shard;
	}

	private static Log _log = LogFactoryUtil.getLog(ShardPersistenceTest.class);
	private ModelListener<Shard>[] _modelListeners;
	private ShardPersistence _persistence = (ShardPersistence)PortalBeanLocatorUtil.locate(ShardPersistence.class.getName());
	private TransactionalPersistenceAdvice _transactionalPersistenceAdvice = (TransactionalPersistenceAdvice)PortalBeanLocatorUtil.locate(TransactionalPersistenceAdvice.class.getName());
}