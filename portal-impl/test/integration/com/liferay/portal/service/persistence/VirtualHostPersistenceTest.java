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

import com.liferay.portal.NoSuchVirtualHostException;
import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQueryFactoryUtil;
import com.liferay.portal.kernel.dao.orm.ProjectionFactoryUtil;
import com.liferay.portal.kernel.dao.orm.RestrictionsFactoryUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.VirtualHost;
import com.liferay.portal.model.impl.VirtualHostModelImpl;
import com.liferay.portal.service.persistence.BasePersistenceTestCase;
import com.liferay.portal.util.PropsValues;

import java.util.List;

/**
 * @author Brian Wing Shun Chan
 */
public class VirtualHostPersistenceTest extends BasePersistenceTestCase {
	@Override
	public void setUp() throws Exception {
		super.setUp();

		_persistence = (VirtualHostPersistence)PortalBeanLocatorUtil.locate(VirtualHostPersistence.class.getName());
	}

	public void testCreate() throws Exception {
		long pk = nextLong();

		VirtualHost virtualHost = _persistence.create(pk);

		assertNotNull(virtualHost);

		assertEquals(virtualHost.getPrimaryKey(), pk);
	}

	public void testRemove() throws Exception {
		VirtualHost newVirtualHost = addVirtualHost();

		_persistence.remove(newVirtualHost);

		VirtualHost existingVirtualHost = _persistence.fetchByPrimaryKey(newVirtualHost.getPrimaryKey());

		assertNull(existingVirtualHost);
	}

	public void testUpdateNew() throws Exception {
		addVirtualHost();
	}

	public void testUpdateExisting() throws Exception {
		long pk = nextLong();

		VirtualHost newVirtualHost = _persistence.create(pk);

		newVirtualHost.setCompanyId(nextLong());

		newVirtualHost.setLayoutSetId(nextLong());

		newVirtualHost.setHostname(randomString());

		_persistence.update(newVirtualHost, false);

		VirtualHost existingVirtualHost = _persistence.findByPrimaryKey(newVirtualHost.getPrimaryKey());

		assertEquals(existingVirtualHost.getVirtualHostId(),
			newVirtualHost.getVirtualHostId());
		assertEquals(existingVirtualHost.getCompanyId(),
			newVirtualHost.getCompanyId());
		assertEquals(existingVirtualHost.getLayoutSetId(),
			newVirtualHost.getLayoutSetId());
		assertEquals(existingVirtualHost.getHostname(),
			newVirtualHost.getHostname());
	}

	public void testFindByPrimaryKeyExisting() throws Exception {
		VirtualHost newVirtualHost = addVirtualHost();

		VirtualHost existingVirtualHost = _persistence.findByPrimaryKey(newVirtualHost.getPrimaryKey());

		assertEquals(existingVirtualHost, newVirtualHost);
	}

	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = nextLong();

		try {
			_persistence.findByPrimaryKey(pk);

			fail("Missing entity did not throw NoSuchVirtualHostException");
		}
		catch (NoSuchVirtualHostException nsee) {
		}
	}

	public void testFetchByPrimaryKeyExisting() throws Exception {
		VirtualHost newVirtualHost = addVirtualHost();

		VirtualHost existingVirtualHost = _persistence.fetchByPrimaryKey(newVirtualHost.getPrimaryKey());

		assertEquals(existingVirtualHost, newVirtualHost);
	}

	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = nextLong();

		VirtualHost missingVirtualHost = _persistence.fetchByPrimaryKey(pk);

		assertNull(missingVirtualHost);
	}

	public void testDynamicQueryByPrimaryKeyExisting()
		throws Exception {
		VirtualHost newVirtualHost = addVirtualHost();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(VirtualHost.class,
				VirtualHost.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("virtualHostId",
				newVirtualHost.getVirtualHostId()));

		List<VirtualHost> result = _persistence.findWithDynamicQuery(dynamicQuery);

		assertEquals(1, result.size());

		VirtualHost existingVirtualHost = result.get(0);

		assertEquals(existingVirtualHost, newVirtualHost);
	}

	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(VirtualHost.class,
				VirtualHost.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("virtualHostId", nextLong()));

		List<VirtualHost> result = _persistence.findWithDynamicQuery(dynamicQuery);

		assertEquals(0, result.size());
	}

	public void testDynamicQueryByProjectionExisting()
		throws Exception {
		VirtualHost newVirtualHost = addVirtualHost();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(VirtualHost.class,
				VirtualHost.class.getClassLoader());

		dynamicQuery.setProjection(ProjectionFactoryUtil.property(
				"virtualHostId"));

		Object newVirtualHostId = newVirtualHost.getVirtualHostId();

		dynamicQuery.add(RestrictionsFactoryUtil.in("virtualHostId",
				new Object[] { newVirtualHostId }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		assertEquals(1, result.size());

		Object existingVirtualHostId = result.get(0);

		assertEquals(existingVirtualHostId, newVirtualHostId);
	}

	public void testDynamicQueryByProjectionMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(VirtualHost.class,
				VirtualHost.class.getClassLoader());

		dynamicQuery.setProjection(ProjectionFactoryUtil.property(
				"virtualHostId"));

		dynamicQuery.add(RestrictionsFactoryUtil.in("virtualHostId",
				new Object[] { nextLong() }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		assertEquals(0, result.size());
	}

	public void testResetOriginalValues() throws Exception {
		if (!PropsValues.HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE) {
			return;
		}

		VirtualHost newVirtualHost = addVirtualHost();

		_persistence.clearCache();

		VirtualHostModelImpl existingVirtualHostModelImpl = (VirtualHostModelImpl)_persistence.findByPrimaryKey(newVirtualHost.getPrimaryKey());

		assertTrue(Validator.equals(
				existingVirtualHostModelImpl.getHostname(),
				existingVirtualHostModelImpl.getOriginalHostname()));

		assertEquals(existingVirtualHostModelImpl.getCompanyId(),
			existingVirtualHostModelImpl.getOriginalCompanyId());
		assertEquals(existingVirtualHostModelImpl.getLayoutSetId(),
			existingVirtualHostModelImpl.getOriginalLayoutSetId());
	}

	protected VirtualHost addVirtualHost() throws Exception {
		long pk = nextLong();

		VirtualHost virtualHost = _persistence.create(pk);

		virtualHost.setCompanyId(nextLong());

		virtualHost.setLayoutSetId(nextLong());

		virtualHost.setHostname(randomString());

		_persistence.update(virtualHost, false);

		return virtualHost;
	}

	private VirtualHostPersistence _persistence;
}