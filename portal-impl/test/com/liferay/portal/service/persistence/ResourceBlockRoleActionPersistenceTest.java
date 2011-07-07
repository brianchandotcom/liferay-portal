/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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

import com.liferay.portal.NoSuchResourceBlockRoleActionException;
import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQueryFactoryUtil;
import com.liferay.portal.kernel.dao.orm.ProjectionFactoryUtil;
import com.liferay.portal.kernel.dao.orm.RestrictionsFactoryUtil;
import com.liferay.portal.model.ResourceBlockRoleAction;
import com.liferay.portal.service.persistence.BasePersistenceTestCase;

import java.util.List;

/**
 * @author Brian Wing Shun Chan
 */
public class ResourceBlockRoleActionPersistenceTest
	extends BasePersistenceTestCase {
	@Override
	public void setUp() throws Exception {
		super.setUp();

		_persistence = (ResourceBlockRoleActionPersistence)PortalBeanLocatorUtil.locate(ResourceBlockRoleActionPersistence.class.getName());
	}

	public void testCreate() throws Exception {
		ResourceBlockRoleActionPK pk = new ResourceBlockRoleActionPK(nextLong(),
				nextLong(), nextLong());

		ResourceBlockRoleAction resourceBlockRoleAction = _persistence.create(pk);

		assertNotNull(resourceBlockRoleAction);

		assertEquals(resourceBlockRoleAction.getPrimaryKey(), pk);
	}

	public void testRemove() throws Exception {
		ResourceBlockRoleAction newResourceBlockRoleAction = addResourceBlockRoleAction();

		_persistence.remove(newResourceBlockRoleAction);

		ResourceBlockRoleAction existingResourceBlockRoleAction = _persistence.fetchByPrimaryKey(newResourceBlockRoleAction.getPrimaryKey());

		assertNull(existingResourceBlockRoleAction);
	}

	public void testUpdateNew() throws Exception {
		addResourceBlockRoleAction();
	}

	public void testUpdateExisting() throws Exception {
		ResourceBlockRoleActionPK pk = new ResourceBlockRoleActionPK(nextLong(),
				nextLong(), nextLong());

		ResourceBlockRoleAction newResourceBlockRoleAction = _persistence.create(pk);

		_persistence.update(newResourceBlockRoleAction, false);

		ResourceBlockRoleAction existingResourceBlockRoleAction = _persistence.findByPrimaryKey(newResourceBlockRoleAction.getPrimaryKey());

		assertEquals(existingResourceBlockRoleAction.getActionIds(),
			newResourceBlockRoleAction.getActionIds());
		assertEquals(existingResourceBlockRoleAction.getResourceBlockId(),
			newResourceBlockRoleAction.getResourceBlockId());
		assertEquals(existingResourceBlockRoleAction.getRoleId(),
			newResourceBlockRoleAction.getRoleId());
	}

	public void testFindByPrimaryKeyExisting() throws Exception {
		ResourceBlockRoleAction newResourceBlockRoleAction = addResourceBlockRoleAction();

		ResourceBlockRoleAction existingResourceBlockRoleAction = _persistence.findByPrimaryKey(newResourceBlockRoleAction.getPrimaryKey());

		assertEquals(existingResourceBlockRoleAction, newResourceBlockRoleAction);
	}

	public void testFindByPrimaryKeyMissing() throws Exception {
		ResourceBlockRoleActionPK pk = new ResourceBlockRoleActionPK(nextLong(),
				nextLong(), nextLong());

		try {
			_persistence.findByPrimaryKey(pk);

			fail(
				"Missing entity did not throw NoSuchResourceBlockRoleActionException");
		}
		catch (NoSuchResourceBlockRoleActionException nsee) {
		}
	}

	public void testFetchByPrimaryKeyExisting() throws Exception {
		ResourceBlockRoleAction newResourceBlockRoleAction = addResourceBlockRoleAction();

		ResourceBlockRoleAction existingResourceBlockRoleAction = _persistence.fetchByPrimaryKey(newResourceBlockRoleAction.getPrimaryKey());

		assertEquals(existingResourceBlockRoleAction, newResourceBlockRoleAction);
	}

	public void testFetchByPrimaryKeyMissing() throws Exception {
		ResourceBlockRoleActionPK pk = new ResourceBlockRoleActionPK(nextLong(),
				nextLong(), nextLong());

		ResourceBlockRoleAction missingResourceBlockRoleAction = _persistence.fetchByPrimaryKey(pk);

		assertNull(missingResourceBlockRoleAction);
	}

	public void testDynamicQueryByPrimaryKeyExisting()
		throws Exception {
		ResourceBlockRoleAction newResourceBlockRoleAction = addResourceBlockRoleAction();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(ResourceBlockRoleAction.class,
				ResourceBlockRoleAction.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("id.actionIds",
				newResourceBlockRoleAction.getActionIds()));
		dynamicQuery.add(RestrictionsFactoryUtil.eq("id.resourceBlockId",
				newResourceBlockRoleAction.getResourceBlockId()));
		dynamicQuery.add(RestrictionsFactoryUtil.eq("id.roleId",
				newResourceBlockRoleAction.getRoleId()));

		List<ResourceBlockRoleAction> result = _persistence.findWithDynamicQuery(dynamicQuery);

		assertEquals(1, result.size());

		ResourceBlockRoleAction existingResourceBlockRoleAction = result.get(0);

		assertEquals(existingResourceBlockRoleAction, newResourceBlockRoleAction);
	}

	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(ResourceBlockRoleAction.class,
				ResourceBlockRoleAction.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("id.actionIds", nextLong()));
		dynamicQuery.add(RestrictionsFactoryUtil.eq("id.resourceBlockId",
				nextLong()));
		dynamicQuery.add(RestrictionsFactoryUtil.eq("id.roleId", nextLong()));

		List<ResourceBlockRoleAction> result = _persistence.findWithDynamicQuery(dynamicQuery);

		assertEquals(0, result.size());
	}

	public void testDynamicQueryByProjectionExisting()
		throws Exception {
		ResourceBlockRoleAction newResourceBlockRoleAction = addResourceBlockRoleAction();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(ResourceBlockRoleAction.class,
				ResourceBlockRoleAction.class.getClassLoader());

		dynamicQuery.setProjection(ProjectionFactoryUtil.property(
				"id.actionIds"));

		Object newActionIds = newResourceBlockRoleAction.getActionIds();

		dynamicQuery.add(RestrictionsFactoryUtil.in("id.actionIds",
				new Object[] { newActionIds }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		assertEquals(1, result.size());

		Object existingActionIds = result.get(0);

		assertEquals(existingActionIds, newActionIds);
	}

	public void testDynamicQueryByProjectionMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(ResourceBlockRoleAction.class,
				ResourceBlockRoleAction.class.getClassLoader());

		dynamicQuery.setProjection(ProjectionFactoryUtil.property(
				"id.actionIds"));

		dynamicQuery.add(RestrictionsFactoryUtil.in("id.actionIds",
				new Object[] { nextLong() }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		assertEquals(0, result.size());
	}

	protected ResourceBlockRoleAction addResourceBlockRoleAction()
		throws Exception {
		ResourceBlockRoleActionPK pk = new ResourceBlockRoleActionPK(nextLong(),
				nextLong(), nextLong());

		ResourceBlockRoleAction resourceBlockRoleAction = _persistence.create(pk);

		_persistence.update(resourceBlockRoleAction, false);

		return resourceBlockRoleAction;
	}

	private ResourceBlockRoleActionPersistence _persistence;
}