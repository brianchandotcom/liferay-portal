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

import com.liferay.portal.NoSuchResourceBlockException;
import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQueryFactoryUtil;
import com.liferay.portal.kernel.dao.orm.ProjectionFactoryUtil;
import com.liferay.portal.kernel.dao.orm.RestrictionsFactoryUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.ResourceBlock;
import com.liferay.portal.model.impl.ResourceBlockModelImpl;
import com.liferay.portal.service.persistence.BasePersistenceTestCase;
import com.liferay.portal.util.PropsValues;

import java.util.List;

/**
 * @author Brian Wing Shun Chan
 */
public class ResourceBlockPersistenceTest extends BasePersistenceTestCase {
	@Override
	public void setUp() throws Exception {
		super.setUp();

		_persistence = (ResourceBlockPersistence)PortalBeanLocatorUtil.locate(ResourceBlockPersistence.class.getName());
	}

	public void testCreate() throws Exception {
		long pk = nextLong();

		ResourceBlock resourceBlock = _persistence.create(pk);

		assertNotNull(resourceBlock);

		assertEquals(resourceBlock.getPrimaryKey(), pk);
	}

	public void testRemove() throws Exception {
		ResourceBlock newResourceBlock = addResourceBlock();

		_persistence.remove(newResourceBlock);

		ResourceBlock existingResourceBlock = _persistence.fetchByPrimaryKey(newResourceBlock.getPrimaryKey());

		assertNull(existingResourceBlock);
	}

	public void testUpdateNew() throws Exception {
		addResourceBlock();
	}

	public void testUpdateExisting() throws Exception {
		long pk = nextLong();

		ResourceBlock newResourceBlock = _persistence.create(pk);

		newResourceBlock.setCompanyId(nextLong());

		newResourceBlock.setGroupId(nextLong());

		newResourceBlock.setName(randomString());

		newResourceBlock.setPermissionsHash(randomString());

		newResourceBlock.setReferenceCount(nextLong());

		_persistence.update(newResourceBlock, false);

		ResourceBlock existingResourceBlock = _persistence.findByPrimaryKey(newResourceBlock.getPrimaryKey());

		assertEquals(existingResourceBlock.getResourceBlockId(),
			newResourceBlock.getResourceBlockId());
		assertEquals(existingResourceBlock.getCompanyId(),
			newResourceBlock.getCompanyId());
		assertEquals(existingResourceBlock.getGroupId(),
			newResourceBlock.getGroupId());
		assertEquals(existingResourceBlock.getName(), newResourceBlock.getName());
		assertEquals(existingResourceBlock.getPermissionsHash(),
			newResourceBlock.getPermissionsHash());
		assertEquals(existingResourceBlock.getReferenceCount(),
			newResourceBlock.getReferenceCount());
	}

	public void testFindByPrimaryKeyExisting() throws Exception {
		ResourceBlock newResourceBlock = addResourceBlock();

		ResourceBlock existingResourceBlock = _persistence.findByPrimaryKey(newResourceBlock.getPrimaryKey());

		assertEquals(existingResourceBlock, newResourceBlock);
	}

	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = nextLong();

		try {
			_persistence.findByPrimaryKey(pk);

			fail("Missing entity did not throw NoSuchResourceBlockException");
		}
		catch (NoSuchResourceBlockException nsee) {
		}
	}

	public void testFetchByPrimaryKeyExisting() throws Exception {
		ResourceBlock newResourceBlock = addResourceBlock();

		ResourceBlock existingResourceBlock = _persistence.fetchByPrimaryKey(newResourceBlock.getPrimaryKey());

		assertEquals(existingResourceBlock, newResourceBlock);
	}

	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = nextLong();

		ResourceBlock missingResourceBlock = _persistence.fetchByPrimaryKey(pk);

		assertNull(missingResourceBlock);
	}

	public void testDynamicQueryByPrimaryKeyExisting()
		throws Exception {
		ResourceBlock newResourceBlock = addResourceBlock();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(ResourceBlock.class,
				ResourceBlock.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("resourceBlockId",
				newResourceBlock.getResourceBlockId()));

		List<ResourceBlock> result = _persistence.findWithDynamicQuery(dynamicQuery);

		assertEquals(1, result.size());

		ResourceBlock existingResourceBlock = result.get(0);

		assertEquals(existingResourceBlock, newResourceBlock);
	}

	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(ResourceBlock.class,
				ResourceBlock.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("resourceBlockId",
				nextLong()));

		List<ResourceBlock> result = _persistence.findWithDynamicQuery(dynamicQuery);

		assertEquals(0, result.size());
	}

	public void testDynamicQueryByProjectionExisting()
		throws Exception {
		ResourceBlock newResourceBlock = addResourceBlock();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(ResourceBlock.class,
				ResourceBlock.class.getClassLoader());

		dynamicQuery.setProjection(ProjectionFactoryUtil.property(
				"resourceBlockId"));

		Object newResourceBlockId = newResourceBlock.getResourceBlockId();

		dynamicQuery.add(RestrictionsFactoryUtil.in("resourceBlockId",
				new Object[] { newResourceBlockId }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		assertEquals(1, result.size());

		Object existingResourceBlockId = result.get(0);

		assertEquals(existingResourceBlockId, newResourceBlockId);
	}

	public void testDynamicQueryByProjectionMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(ResourceBlock.class,
				ResourceBlock.class.getClassLoader());

		dynamicQuery.setProjection(ProjectionFactoryUtil.property(
				"resourceBlockId"));

		dynamicQuery.add(RestrictionsFactoryUtil.in("resourceBlockId",
				new Object[] { nextLong() }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		assertEquals(0, result.size());
	}

	public void testResetOriginalValues() throws Exception {
		if (!PropsValues.HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE) {
			return;
		}

		ResourceBlock newResourceBlock = addResourceBlock();

		_persistence.clearCache();

		ResourceBlockModelImpl existingResourceBlockModelImpl = (ResourceBlockModelImpl)_persistence.findByPrimaryKey(newResourceBlock.getPrimaryKey());

		assertEquals(existingResourceBlockModelImpl.getCompanyId(),
			existingResourceBlockModelImpl.getOriginalCompanyId());
		assertEquals(existingResourceBlockModelImpl.getGroupId(),
			existingResourceBlockModelImpl.getOriginalGroupId());
		assertTrue(Validator.equals(existingResourceBlockModelImpl.getName(),
				existingResourceBlockModelImpl.getOriginalName()));
		assertTrue(Validator.equals(
				existingResourceBlockModelImpl.getPermissionsHash(),
				existingResourceBlockModelImpl.getOriginalPermissionsHash()));
	}

	protected ResourceBlock addResourceBlock() throws Exception {
		long pk = nextLong();

		ResourceBlock resourceBlock = _persistence.create(pk);

		resourceBlock.setCompanyId(nextLong());

		resourceBlock.setGroupId(nextLong());

		resourceBlock.setName(randomString());

		resourceBlock.setPermissionsHash(randomString());

		resourceBlock.setReferenceCount(nextLong());

		_persistence.update(resourceBlock, false);

		return resourceBlock;
	}

	private ResourceBlockPersistence _persistence;
}