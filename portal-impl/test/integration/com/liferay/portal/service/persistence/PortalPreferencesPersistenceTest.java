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

import com.liferay.portal.NoSuchPreferencesException;
import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQueryFactoryUtil;
import com.liferay.portal.kernel.dao.orm.ProjectionFactoryUtil;
import com.liferay.portal.kernel.dao.orm.RestrictionsFactoryUtil;
import com.liferay.portal.model.PortalPreferences;
import com.liferay.portal.model.impl.PortalPreferencesModelImpl;
import com.liferay.portal.service.persistence.BasePersistenceTestCase;
import com.liferay.portal.util.PropsValues;

import java.util.List;

/**
 * @author Brian Wing Shun Chan
 */
public class PortalPreferencesPersistenceTest extends BasePersistenceTestCase {
	@Override
	public void setUp() throws Exception {
		super.setUp();

		_persistence = (PortalPreferencesPersistence)PortalBeanLocatorUtil.locate(PortalPreferencesPersistence.class.getName());
	}

	public void testCreate() throws Exception {
		long pk = nextLong();

		PortalPreferences portalPreferences = _persistence.create(pk);

		assertNotNull(portalPreferences);

		assertEquals(portalPreferences.getPrimaryKey(), pk);
	}

	public void testRemove() throws Exception {
		PortalPreferences newPortalPreferences = addPortalPreferences();

		_persistence.remove(newPortalPreferences);

		PortalPreferences existingPortalPreferences = _persistence.fetchByPrimaryKey(newPortalPreferences.getPrimaryKey());

		assertNull(existingPortalPreferences);
	}

	public void testUpdateNew() throws Exception {
		addPortalPreferences();
	}

	public void testUpdateExisting() throws Exception {
		long pk = nextLong();

		PortalPreferences newPortalPreferences = _persistence.create(pk);

		newPortalPreferences.setOwnerId(nextLong());

		newPortalPreferences.setOwnerType(nextInt());

		newPortalPreferences.setPreferences(randomString());

		_persistence.update(newPortalPreferences, false);

		PortalPreferences existingPortalPreferences = _persistence.findByPrimaryKey(newPortalPreferences.getPrimaryKey());

		assertEquals(existingPortalPreferences.getPortalPreferencesId(),
			newPortalPreferences.getPortalPreferencesId());
		assertEquals(existingPortalPreferences.getOwnerId(),
			newPortalPreferences.getOwnerId());
		assertEquals(existingPortalPreferences.getOwnerType(),
			newPortalPreferences.getOwnerType());
		assertEquals(existingPortalPreferences.getPreferences(),
			newPortalPreferences.getPreferences());
	}

	public void testFindByPrimaryKeyExisting() throws Exception {
		PortalPreferences newPortalPreferences = addPortalPreferences();

		PortalPreferences existingPortalPreferences = _persistence.findByPrimaryKey(newPortalPreferences.getPrimaryKey());

		assertEquals(existingPortalPreferences, newPortalPreferences);
	}

	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = nextLong();

		try {
			_persistence.findByPrimaryKey(pk);

			fail("Missing entity did not throw NoSuchPreferencesException");
		}
		catch (NoSuchPreferencesException nsee) {
		}
	}

	public void testFetchByPrimaryKeyExisting() throws Exception {
		PortalPreferences newPortalPreferences = addPortalPreferences();

		PortalPreferences existingPortalPreferences = _persistence.fetchByPrimaryKey(newPortalPreferences.getPrimaryKey());

		assertEquals(existingPortalPreferences, newPortalPreferences);
	}

	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = nextLong();

		PortalPreferences missingPortalPreferences = _persistence.fetchByPrimaryKey(pk);

		assertNull(missingPortalPreferences);
	}

	public void testDynamicQueryByPrimaryKeyExisting()
		throws Exception {
		PortalPreferences newPortalPreferences = addPortalPreferences();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(PortalPreferences.class,
				PortalPreferences.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("portalPreferencesId",
				newPortalPreferences.getPortalPreferencesId()));

		List<PortalPreferences> result = _persistence.findWithDynamicQuery(dynamicQuery);

		assertEquals(1, result.size());

		PortalPreferences existingPortalPreferences = result.get(0);

		assertEquals(existingPortalPreferences, newPortalPreferences);
	}

	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(PortalPreferences.class,
				PortalPreferences.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("portalPreferencesId",
				nextLong()));

		List<PortalPreferences> result = _persistence.findWithDynamicQuery(dynamicQuery);

		assertEquals(0, result.size());
	}

	public void testDynamicQueryByProjectionExisting()
		throws Exception {
		PortalPreferences newPortalPreferences = addPortalPreferences();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(PortalPreferences.class,
				PortalPreferences.class.getClassLoader());

		dynamicQuery.setProjection(ProjectionFactoryUtil.property(
				"portalPreferencesId"));

		Object newPortalPreferencesId = newPortalPreferences.getPortalPreferencesId();

		dynamicQuery.add(RestrictionsFactoryUtil.in("portalPreferencesId",
				new Object[] { newPortalPreferencesId }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		assertEquals(1, result.size());

		Object existingPortalPreferencesId = result.get(0);

		assertEquals(existingPortalPreferencesId, newPortalPreferencesId);
	}

	public void testDynamicQueryByProjectionMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(PortalPreferences.class,
				PortalPreferences.class.getClassLoader());

		dynamicQuery.setProjection(ProjectionFactoryUtil.property(
				"portalPreferencesId"));

		dynamicQuery.add(RestrictionsFactoryUtil.in("portalPreferencesId",
				new Object[] { nextLong() }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		assertEquals(0, result.size());
	}

	public void testResetOriginalValues() throws Exception {
		if (!PropsValues.HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE) {
			return;
		}

		PortalPreferences newPortalPreferences = addPortalPreferences();

		_persistence.clearCache();

		PortalPreferencesModelImpl existingPortalPreferencesModelImpl = (PortalPreferencesModelImpl)_persistence.findByPrimaryKey(newPortalPreferences.getPrimaryKey());

		assertEquals(existingPortalPreferencesModelImpl.getOwnerId(),
			existingPortalPreferencesModelImpl.getOriginalOwnerId());
		assertEquals(existingPortalPreferencesModelImpl.getOwnerType(),
			existingPortalPreferencesModelImpl.getOriginalOwnerType());
	}

	protected PortalPreferences addPortalPreferences()
		throws Exception {
		long pk = nextLong();

		PortalPreferences portalPreferences = _persistence.create(pk);

		portalPreferences.setOwnerId(nextLong());

		portalPreferences.setOwnerType(nextInt());

		portalPreferences.setPreferences(randomString());

		_persistence.update(portalPreferences, false);

		return portalPreferences;
	}

	private PortalPreferencesPersistence _persistence;
}