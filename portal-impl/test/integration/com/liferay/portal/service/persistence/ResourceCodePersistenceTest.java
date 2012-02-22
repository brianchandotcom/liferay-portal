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

import com.liferay.portal.NoSuchResourceCodeException;
import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQueryFactoryUtil;
import com.liferay.portal.kernel.dao.orm.ProjectionFactoryUtil;
import com.liferay.portal.kernel.dao.orm.RestrictionsFactoryUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.ResourceCode;
import com.liferay.portal.model.impl.ResourceCodeModelImpl;
import com.liferay.portal.service.persistence.BasePersistenceTestCase;
import com.liferay.portal.util.PropsValues;

import java.util.List;

/**
 * @author Brian Wing Shun Chan
 */
public class ResourceCodePersistenceTest extends BasePersistenceTestCase {
	@Override
	public void setUp() throws Exception {
		super.setUp();

		_persistence = (ResourceCodePersistence)PortalBeanLocatorUtil.locate(ResourceCodePersistence.class.getName());
	}

	public void testCreate() throws Exception {
		long pk = nextLong();

		ResourceCode resourceCode = _persistence.create(pk);

		assertNotNull(resourceCode);

		assertEquals(resourceCode.getPrimaryKey(), pk);
	}

	public void testRemove() throws Exception {
		ResourceCode newResourceCode = addResourceCode();

		_persistence.remove(newResourceCode);

		ResourceCode existingResourceCode = _persistence.fetchByPrimaryKey(newResourceCode.getPrimaryKey());

		assertNull(existingResourceCode);
	}

	public void testUpdateNew() throws Exception {
		addResourceCode();
	}

	public void testUpdateExisting() throws Exception {
		long pk = nextLong();

		ResourceCode newResourceCode = _persistence.create(pk);

		newResourceCode.setCompanyId(nextLong());

		newResourceCode.setName(randomString());

		newResourceCode.setScope(nextInt());

		_persistence.update(newResourceCode, false);

		ResourceCode existingResourceCode = _persistence.findByPrimaryKey(newResourceCode.getPrimaryKey());

		assertEquals(existingResourceCode.getCodeId(),
			newResourceCode.getCodeId());
		assertEquals(existingResourceCode.getCompanyId(),
			newResourceCode.getCompanyId());
		assertEquals(existingResourceCode.getName(), newResourceCode.getName());
		assertEquals(existingResourceCode.getScope(), newResourceCode.getScope());
	}

	public void testFindByPrimaryKeyExisting() throws Exception {
		ResourceCode newResourceCode = addResourceCode();

		ResourceCode existingResourceCode = _persistence.findByPrimaryKey(newResourceCode.getPrimaryKey());

		assertEquals(existingResourceCode, newResourceCode);
	}

	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = nextLong();

		try {
			_persistence.findByPrimaryKey(pk);

			fail("Missing entity did not throw NoSuchResourceCodeException");
		}
		catch (NoSuchResourceCodeException nsee) {
		}
	}

	public void testFetchByPrimaryKeyExisting() throws Exception {
		ResourceCode newResourceCode = addResourceCode();

		ResourceCode existingResourceCode = _persistence.fetchByPrimaryKey(newResourceCode.getPrimaryKey());

		assertEquals(existingResourceCode, newResourceCode);
	}

	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = nextLong();

		ResourceCode missingResourceCode = _persistence.fetchByPrimaryKey(pk);

		assertNull(missingResourceCode);
	}

	public void testDynamicQueryByPrimaryKeyExisting()
		throws Exception {
		ResourceCode newResourceCode = addResourceCode();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(ResourceCode.class,
				ResourceCode.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("codeId",
				newResourceCode.getCodeId()));

		List<ResourceCode> result = _persistence.findWithDynamicQuery(dynamicQuery);

		assertEquals(1, result.size());

		ResourceCode existingResourceCode = result.get(0);

		assertEquals(existingResourceCode, newResourceCode);
	}

	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(ResourceCode.class,
				ResourceCode.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("codeId", nextLong()));

		List<ResourceCode> result = _persistence.findWithDynamicQuery(dynamicQuery);

		assertEquals(0, result.size());
	}

	public void testDynamicQueryByProjectionExisting()
		throws Exception {
		ResourceCode newResourceCode = addResourceCode();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(ResourceCode.class,
				ResourceCode.class.getClassLoader());

		dynamicQuery.setProjection(ProjectionFactoryUtil.property("codeId"));

		Object newCodeId = newResourceCode.getCodeId();

		dynamicQuery.add(RestrictionsFactoryUtil.in("codeId",
				new Object[] { newCodeId }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		assertEquals(1, result.size());

		Object existingCodeId = result.get(0);

		assertEquals(existingCodeId, newCodeId);
	}

	public void testDynamicQueryByProjectionMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(ResourceCode.class,
				ResourceCode.class.getClassLoader());

		dynamicQuery.setProjection(ProjectionFactoryUtil.property("codeId"));

		dynamicQuery.add(RestrictionsFactoryUtil.in("codeId",
				new Object[] { nextLong() }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		assertEquals(0, result.size());
	}

	public void testResetOriginalValues() throws Exception {
		if (!PropsValues.HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE) {
			return;
		}

		ResourceCode newResourceCode = addResourceCode();

		_persistence.clearCache();

		ResourceCodeModelImpl existingResourceCodeModelImpl = (ResourceCodeModelImpl)_persistence.findByPrimaryKey(newResourceCode.getPrimaryKey());

		assertEquals(existingResourceCodeModelImpl.getCompanyId(),
			existingResourceCodeModelImpl.getOriginalCompanyId());
		assertTrue(Validator.equals(existingResourceCodeModelImpl.getName(),
				existingResourceCodeModelImpl.getOriginalName()));
		assertEquals(existingResourceCodeModelImpl.getScope(),
			existingResourceCodeModelImpl.getOriginalScope());
	}

	protected ResourceCode addResourceCode() throws Exception {
		long pk = nextLong();

		ResourceCode resourceCode = _persistence.create(pk);

		resourceCode.setCompanyId(nextLong());

		resourceCode.setName(randomString());

		resourceCode.setScope(nextInt());

		_persistence.update(resourceCode, false);

		return resourceCode;
	}

	private ResourceCodePersistence _persistence;
}