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

import com.liferay.portal.NoSuchResourceActionException;
import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQueryFactoryUtil;
import com.liferay.portal.kernel.dao.orm.ProjectionFactoryUtil;
import com.liferay.portal.kernel.dao.orm.RestrictionsFactoryUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.ResourceAction;
import com.liferay.portal.model.impl.ResourceActionModelImpl;
import com.liferay.portal.service.persistence.BasePersistenceTestCase;
import com.liferay.portal.util.PropsValues;

import java.util.List;

/**
 * @author Brian Wing Shun Chan
 */
public class ResourceActionPersistenceTest extends BasePersistenceTestCase {
	@Override
	public void setUp() throws Exception {
		super.setUp();

		_persistence = (ResourceActionPersistence)PortalBeanLocatorUtil.locate(ResourceActionPersistence.class.getName());
	}

	public void testCreate() throws Exception {
		long pk = nextLong();

		ResourceAction resourceAction = _persistence.create(pk);

		assertNotNull(resourceAction);

		assertEquals(resourceAction.getPrimaryKey(), pk);
	}

	public void testRemove() throws Exception {
		ResourceAction newResourceAction = addResourceAction();

		_persistence.remove(newResourceAction);

		ResourceAction existingResourceAction = _persistence.fetchByPrimaryKey(newResourceAction.getPrimaryKey());

		assertNull(existingResourceAction);
	}

	public void testUpdateNew() throws Exception {
		addResourceAction();
	}

	public void testUpdateExisting() throws Exception {
		long pk = nextLong();

		ResourceAction newResourceAction = _persistence.create(pk);

		newResourceAction.setName(randomString());

		newResourceAction.setActionId(randomString());

		newResourceAction.setBitwiseValue(nextLong());

		_persistence.update(newResourceAction, false);

		ResourceAction existingResourceAction = _persistence.findByPrimaryKey(newResourceAction.getPrimaryKey());

		assertEquals(existingResourceAction.getResourceActionId(),
			newResourceAction.getResourceActionId());
		assertEquals(existingResourceAction.getName(),
			newResourceAction.getName());
		assertEquals(existingResourceAction.getActionId(),
			newResourceAction.getActionId());
		assertEquals(existingResourceAction.getBitwiseValue(),
			newResourceAction.getBitwiseValue());
	}

	public void testFindByPrimaryKeyExisting() throws Exception {
		ResourceAction newResourceAction = addResourceAction();

		ResourceAction existingResourceAction = _persistence.findByPrimaryKey(newResourceAction.getPrimaryKey());

		assertEquals(existingResourceAction, newResourceAction);
	}

	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = nextLong();

		try {
			_persistence.findByPrimaryKey(pk);

			fail("Missing entity did not throw NoSuchResourceActionException");
		}
		catch (NoSuchResourceActionException nsee) {
		}
	}

	public void testFetchByPrimaryKeyExisting() throws Exception {
		ResourceAction newResourceAction = addResourceAction();

		ResourceAction existingResourceAction = _persistence.fetchByPrimaryKey(newResourceAction.getPrimaryKey());

		assertEquals(existingResourceAction, newResourceAction);
	}

	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = nextLong();

		ResourceAction missingResourceAction = _persistence.fetchByPrimaryKey(pk);

		assertNull(missingResourceAction);
	}

	public void testDynamicQueryByPrimaryKeyExisting()
		throws Exception {
		ResourceAction newResourceAction = addResourceAction();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(ResourceAction.class,
				ResourceAction.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("resourceActionId",
				newResourceAction.getResourceActionId()));

		List<ResourceAction> result = _persistence.findWithDynamicQuery(dynamicQuery);

		assertEquals(1, result.size());

		ResourceAction existingResourceAction = result.get(0);

		assertEquals(existingResourceAction, newResourceAction);
	}

	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(ResourceAction.class,
				ResourceAction.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("resourceActionId",
				nextLong()));

		List<ResourceAction> result = _persistence.findWithDynamicQuery(dynamicQuery);

		assertEquals(0, result.size());
	}

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

		assertEquals(1, result.size());

		Object existingResourceActionId = result.get(0);

		assertEquals(existingResourceActionId, newResourceActionId);
	}

	public void testDynamicQueryByProjectionMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(ResourceAction.class,
				ResourceAction.class.getClassLoader());

		dynamicQuery.setProjection(ProjectionFactoryUtil.property(
				"resourceActionId"));

		dynamicQuery.add(RestrictionsFactoryUtil.in("resourceActionId",
				new Object[] { nextLong() }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		assertEquals(0, result.size());
	}

	public void testResetOriginalValues() throws Exception {
		if (!PropsValues.HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE) {
			return;
		}

		ResourceAction newResourceAction = addResourceAction();

		_persistence.clearCache();

		ResourceActionModelImpl existingResourceActionModelImpl = (ResourceActionModelImpl)_persistence.findByPrimaryKey(newResourceAction.getPrimaryKey());

		assertTrue(Validator.equals(existingResourceActionModelImpl.getName(),
				existingResourceActionModelImpl.getOriginalName()));
		assertTrue(Validator.equals(
				existingResourceActionModelImpl.getActionId(),
				existingResourceActionModelImpl.getOriginalActionId()));
	}

	protected ResourceAction addResourceAction() throws Exception {
		long pk = nextLong();

		ResourceAction resourceAction = _persistence.create(pk);

		resourceAction.setName(randomString());

		resourceAction.setActionId(randomString());

		resourceAction.setBitwiseValue(nextLong());

		_persistence.update(resourceAction, false);

		return resourceAction;
	}

	private ResourceActionPersistence _persistence;
}