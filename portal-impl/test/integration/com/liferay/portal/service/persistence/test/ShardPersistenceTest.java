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

import com.liferay.portal.NoSuchShardException;
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
import com.liferay.portal.model.Shard;
import com.liferay.portal.service.ShardLocalServiceUtil;
import com.liferay.portal.service.persistence.ShardPersistence;
import com.liferay.portal.service.persistence.ShardUtil;
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
public class ShardPersistenceTest {
	@Rule
	public final AggregateTestRule aggregateTestRule = new AggregateTestRule(new LiferayIntegrationTestRule(),
			PersistenceTestRule.INSTANCE,
			new TransactionalTestRule(Propagation.REQUIRED));

	@Before
	public void setUp() {
		_persistence = ShardUtil.getPersistence();

		Class<?> clazz = _persistence.getClass();

		_dynamicQueryClassLoader = clazz.getClassLoader();
	}

	@After
	public void tearDown() throws Exception {
		Iterator<Shard> iterator = _shards.iterator();

		while (iterator.hasNext()) {
			_persistence.remove(iterator.next());

			iterator.remove();
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

		_shards.add(_persistence.update(newShard));

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
	public void testCountByName() throws Exception {
		_persistence.countByName(StringPool.BLANK);

		_persistence.countByName(StringPool.NULL);

		_persistence.countByName((String)null);
	}

	@Test
	public void testCountByC_C() throws Exception {
		_persistence.countByC_C(RandomTestUtil.nextLong(),
			RandomTestUtil.nextLong());

		_persistence.countByC_C(0L, 0L);
	}

	@Test
	public void testFindByPrimaryKeyExisting() throws Exception {
		Shard newShard = addShard();

		Shard existingShard = _persistence.findByPrimaryKey(newShard.getPrimaryKey());

		Assert.assertEquals(existingShard, newShard);
	}

	@Test(expected = NoSuchShardException.class)
	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		_persistence.findByPrimaryKey(pk);
	}

	@Test
	public void testFindAll() throws Exception {
		_persistence.findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			getOrderByComparator());
	}

	protected OrderByComparator<Shard> getOrderByComparator() {
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
				_dynamicQueryClassLoader);

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
				_dynamicQueryClassLoader);

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
				_dynamicQueryClassLoader);

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
				_dynamicQueryClassLoader);

		dynamicQuery.setProjection(ProjectionFactoryUtil.property("shardId"));

		dynamicQuery.add(RestrictionsFactoryUtil.in("shardId",
				new Object[] { RandomTestUtil.nextLong() }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testResetOriginalValues() throws Exception {
		Shard newShard = addShard();

		_persistence.clearCache();

		Shard existingShard = _persistence.findByPrimaryKey(newShard.getPrimaryKey());

		Assert.assertTrue(Validator.equals(existingShard.getName(),
				ReflectionTestUtil.invoke(existingShard, "getOriginalName",
					new Class<?>[0])));

		Assert.assertEquals(existingShard.getClassNameId(),
			ReflectionTestUtil.invoke(existingShard, "getOriginalClassNameId",
				new Class<?>[0]));
		Assert.assertEquals(existingShard.getClassPK(),
			ReflectionTestUtil.invoke(existingShard, "getOriginalClassPK",
				new Class<?>[0]));
	}

	protected Shard addShard() throws Exception {
		long pk = RandomTestUtil.nextLong();

		Shard shard = _persistence.create(pk);

		shard.setMvccVersion(RandomTestUtil.nextLong());

		shard.setClassNameId(RandomTestUtil.nextLong());

		shard.setClassPK(RandomTestUtil.nextLong());

		shard.setName(RandomTestUtil.randomString());

		_shards.add(_persistence.update(shard));

		return shard;
	}

	private List<Shard> _shards = new ArrayList<Shard>();
	private ShardPersistence _persistence;
	private ClassLoader _dynamicQueryClassLoader;
}