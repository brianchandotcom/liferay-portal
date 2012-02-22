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

import com.liferay.portal.NoSuchClusterGroupException;
import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQueryFactoryUtil;
import com.liferay.portal.kernel.dao.orm.ProjectionFactoryUtil;
import com.liferay.portal.kernel.dao.orm.RestrictionsFactoryUtil;
import com.liferay.portal.model.ClusterGroup;
import com.liferay.portal.service.persistence.BasePersistenceTestCase;

import java.util.List;

/**
 * @author Brian Wing Shun Chan
 */
public class ClusterGroupPersistenceTest extends BasePersistenceTestCase {
	@Override
	public void setUp() throws Exception {
		super.setUp();

		_persistence = (ClusterGroupPersistence)PortalBeanLocatorUtil.locate(ClusterGroupPersistence.class.getName());
	}

	public void testCreate() throws Exception {
		long pk = nextLong();

		ClusterGroup clusterGroup = _persistence.create(pk);

		assertNotNull(clusterGroup);

		assertEquals(clusterGroup.getPrimaryKey(), pk);
	}

	public void testRemove() throws Exception {
		ClusterGroup newClusterGroup = addClusterGroup();

		_persistence.remove(newClusterGroup);

		ClusterGroup existingClusterGroup = _persistence.fetchByPrimaryKey(newClusterGroup.getPrimaryKey());

		assertNull(existingClusterGroup);
	}

	public void testUpdateNew() throws Exception {
		addClusterGroup();
	}

	public void testUpdateExisting() throws Exception {
		long pk = nextLong();

		ClusterGroup newClusterGroup = _persistence.create(pk);

		newClusterGroup.setName(randomString());

		newClusterGroup.setClusterNodeIds(randomString());

		newClusterGroup.setWholeCluster(randomBoolean());

		_persistence.update(newClusterGroup, false);

		ClusterGroup existingClusterGroup = _persistence.findByPrimaryKey(newClusterGroup.getPrimaryKey());

		assertEquals(existingClusterGroup.getClusterGroupId(),
			newClusterGroup.getClusterGroupId());
		assertEquals(existingClusterGroup.getName(), newClusterGroup.getName());
		assertEquals(existingClusterGroup.getClusterNodeIds(),
			newClusterGroup.getClusterNodeIds());
		assertEquals(existingClusterGroup.getWholeCluster(),
			newClusterGroup.getWholeCluster());
	}

	public void testFindByPrimaryKeyExisting() throws Exception {
		ClusterGroup newClusterGroup = addClusterGroup();

		ClusterGroup existingClusterGroup = _persistence.findByPrimaryKey(newClusterGroup.getPrimaryKey());

		assertEquals(existingClusterGroup, newClusterGroup);
	}

	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = nextLong();

		try {
			_persistence.findByPrimaryKey(pk);

			fail("Missing entity did not throw NoSuchClusterGroupException");
		}
		catch (NoSuchClusterGroupException nsee) {
		}
	}

	public void testFetchByPrimaryKeyExisting() throws Exception {
		ClusterGroup newClusterGroup = addClusterGroup();

		ClusterGroup existingClusterGroup = _persistence.fetchByPrimaryKey(newClusterGroup.getPrimaryKey());

		assertEquals(existingClusterGroup, newClusterGroup);
	}

	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = nextLong();

		ClusterGroup missingClusterGroup = _persistence.fetchByPrimaryKey(pk);

		assertNull(missingClusterGroup);
	}

	public void testDynamicQueryByPrimaryKeyExisting()
		throws Exception {
		ClusterGroup newClusterGroup = addClusterGroup();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(ClusterGroup.class,
				ClusterGroup.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("clusterGroupId",
				newClusterGroup.getClusterGroupId()));

		List<ClusterGroup> result = _persistence.findWithDynamicQuery(dynamicQuery);

		assertEquals(1, result.size());

		ClusterGroup existingClusterGroup = result.get(0);

		assertEquals(existingClusterGroup, newClusterGroup);
	}

	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(ClusterGroup.class,
				ClusterGroup.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("clusterGroupId", nextLong()));

		List<ClusterGroup> result = _persistence.findWithDynamicQuery(dynamicQuery);

		assertEquals(0, result.size());
	}

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

		assertEquals(1, result.size());

		Object existingClusterGroupId = result.get(0);

		assertEquals(existingClusterGroupId, newClusterGroupId);
	}

	public void testDynamicQueryByProjectionMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(ClusterGroup.class,
				ClusterGroup.class.getClassLoader());

		dynamicQuery.setProjection(ProjectionFactoryUtil.property(
				"clusterGroupId"));

		dynamicQuery.add(RestrictionsFactoryUtil.in("clusterGroupId",
				new Object[] { nextLong() }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		assertEquals(0, result.size());
	}

	protected ClusterGroup addClusterGroup() throws Exception {
		long pk = nextLong();

		ClusterGroup clusterGroup = _persistence.create(pk);

		clusterGroup.setName(randomString());

		clusterGroup.setClusterNodeIds(randomString());

		clusterGroup.setWholeCluster(randomBoolean());

		_persistence.update(clusterGroup, false);

		return clusterGroup;
	}

	private ClusterGroupPersistence _persistence;
}