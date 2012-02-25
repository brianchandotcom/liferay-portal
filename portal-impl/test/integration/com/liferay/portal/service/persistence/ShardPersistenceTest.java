/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQueryFactoryUtil;
import com.liferay.portal.kernel.dao.orm.ProjectionFactoryUtil;
import com.liferay.portal.kernel.dao.orm.RestrictionsFactoryUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.Shard;
import com.liferay.portal.model.impl.ShardModelImpl;
import com.liferay.portal.service.persistence.BasePersistenceTestCase;
import com.liferay.portal.util.PropsValues;

import java.util.List;

/**
 * @author Brian Wing Shun Chan
 */
public class ShardPersistenceTest extends BasePersistenceTestCase {
	@Override
	public void setUp() throws Exception {
		super.setUp();

		_persistence = (ShardPersistence)PortalBeanLocatorUtil.locate(ShardPersistence.class.getName());
	}

	public void testCreate() throws Exception {
		long pk = nextLong();

		Shard shard = _persistence.create(pk);

		assertNotNull(shard);

		assertEquals(shard.getPrimaryKey(), pk);
	}

	public void testRemove() throws Exception {
		Shard newShard = addShard();

		_persistence.remove(newShard);

		Shard existingShard = _persistence.fetchByPrimaryKey(newShard.getPrimaryKey());

		assertNull(existingShard);
	}

	public void testUpdateNew() throws Exception {
		addShard();
	}

	public void testUpdateExisting() throws Exception {
		long pk = nextLong();

		Shard newShard = _persistence.create(pk);

		newShard.setClassNameId(nextLong());

		newShard.setClassPK(nextLong());

		newShard.setName(randomString());

		_persistence.update(newShard, false);

		Shard existingShard = _persistence.findByPrimaryKey(newShard.getPrimaryKey());

		assertEquals(existingShard.getShardId(), newShard.getShardId());
		assertEquals(existingShard.getClassNameId(), newShard.getClassNameId());
		assertEquals(existingShard.getClassPK(), newShard.getClassPK());
		assertEquals(existingShard.getName(), newShard.getName());
	}

	public void testFindByPrimaryKeyExisting() throws Exception {
		Shard newShard = addShard();

		Shard existingShard = _persistence.findByPrimaryKey(newShard.getPrimaryKey());

		assertEquals(existingShard, newShard);
	}

	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = nextLong();

		try {
			_persistence.findByPrimaryKey(pk);

			fail("Missing entity did not throw NoSuchShardException");
		}
		catch (NoSuchShardException nsee) {
		}
	}

	public void testFetchByPrimaryKeyExisting() throws Exception {
		Shard newShard = addShard();

		Shard existingShard = _persistence.fetchByPrimaryKey(newShard.getPrimaryKey());

		assertEquals(existingShard, newShard);
	}

	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = nextLong();

		Shard missingShard = _persistence.fetchByPrimaryKey(pk);

		assertNull(missingShard);
	}

	public void testDynamicQueryByPrimaryKeyExisting()
		throws Exception {
		Shard newShard = addShard();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(Shard.class,
				Shard.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("shardId",
				newShard.getShardId()));

		List<Shard> result = _persistence.findWithDynamicQuery(dynamicQuery);

		assertEquals(1, result.size());

		Shard existingShard = result.get(0);

		assertEquals(existingShard, newShard);
	}

	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(Shard.class,
				Shard.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("shardId", nextLong()));

		List<Shard> result = _persistence.findWithDynamicQuery(dynamicQuery);

		assertEquals(0, result.size());
	}

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

		assertEquals(1, result.size());

		Object existingShardId = result.get(0);

		assertEquals(existingShardId, newShardId);
	}

	public void testDynamicQueryByProjectionMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(Shard.class,
				Shard.class.getClassLoader());

		dynamicQuery.setProjection(ProjectionFactoryUtil.property("shardId"));

		dynamicQuery.add(RestrictionsFactoryUtil.in("shardId",
				new Object[] { nextLong() }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		assertEquals(0, result.size());
	}

	public void testResetOriginalValues() throws Exception {
		if (!PropsValues.HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE) {
			return;
		}

		Shard newShard = addShard();

		_persistence.clearCache();

		ShardModelImpl existingShardModelImpl = (ShardModelImpl)_persistence.findByPrimaryKey(newShard.getPrimaryKey());

		assertTrue(Validator.equals(existingShardModelImpl.getName(),
				existingShardModelImpl.getOriginalName()));

		assertEquals(existingShardModelImpl.getClassNameId(),
			existingShardModelImpl.getOriginalClassNameId());
		assertEquals(existingShardModelImpl.getClassPK(),
			existingShardModelImpl.getOriginalClassPK());
	}

	protected Shard addShard() throws Exception {
		long pk = nextLong();

		Shard shard = _persistence.create(pk);

		shard.setClassNameId(nextLong());

		shard.setClassPK(nextLong());

		shard.setName(randomString());

		_persistence.update(shard, false);

		return shard;
	}

	private ShardPersistence _persistence;
}