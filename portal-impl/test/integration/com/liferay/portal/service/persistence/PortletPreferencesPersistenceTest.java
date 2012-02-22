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

import com.liferay.portal.NoSuchPortletPreferencesException;
import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQueryFactoryUtil;
import com.liferay.portal.kernel.dao.orm.ProjectionFactoryUtil;
import com.liferay.portal.kernel.dao.orm.RestrictionsFactoryUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.PortletPreferences;
import com.liferay.portal.model.impl.PortletPreferencesModelImpl;
import com.liferay.portal.service.persistence.BasePersistenceTestCase;
import com.liferay.portal.util.PropsValues;

import java.util.List;

/**
 * @author Brian Wing Shun Chan
 */
public class PortletPreferencesPersistenceTest extends BasePersistenceTestCase {
	@Override
	public void setUp() throws Exception {
		super.setUp();

		_persistence = (PortletPreferencesPersistence)PortalBeanLocatorUtil.locate(PortletPreferencesPersistence.class.getName());
	}

	public void testCreate() throws Exception {
		long pk = nextLong();

		PortletPreferences portletPreferences = _persistence.create(pk);

		assertNotNull(portletPreferences);

		assertEquals(portletPreferences.getPrimaryKey(), pk);
	}

	public void testRemove() throws Exception {
		PortletPreferences newPortletPreferences = addPortletPreferences();

		_persistence.remove(newPortletPreferences);

		PortletPreferences existingPortletPreferences = _persistence.fetchByPrimaryKey(newPortletPreferences.getPrimaryKey());

		assertNull(existingPortletPreferences);
	}

	public void testUpdateNew() throws Exception {
		addPortletPreferences();
	}

	public void testUpdateExisting() throws Exception {
		long pk = nextLong();

		PortletPreferences newPortletPreferences = _persistence.create(pk);

		newPortletPreferences.setOwnerId(nextLong());

		newPortletPreferences.setOwnerType(nextInt());

		newPortletPreferences.setPlid(nextLong());

		newPortletPreferences.setPortletId(randomString());

		newPortletPreferences.setPreferences(randomString());

		_persistence.update(newPortletPreferences, false);

		PortletPreferences existingPortletPreferences = _persistence.findByPrimaryKey(newPortletPreferences.getPrimaryKey());

		assertEquals(existingPortletPreferences.getPortletPreferencesId(),
			newPortletPreferences.getPortletPreferencesId());
		assertEquals(existingPortletPreferences.getOwnerId(),
			newPortletPreferences.getOwnerId());
		assertEquals(existingPortletPreferences.getOwnerType(),
			newPortletPreferences.getOwnerType());
		assertEquals(existingPortletPreferences.getPlid(),
			newPortletPreferences.getPlid());
		assertEquals(existingPortletPreferences.getPortletId(),
			newPortletPreferences.getPortletId());
		assertEquals(existingPortletPreferences.getPreferences(),
			newPortletPreferences.getPreferences());
	}

	public void testFindByPrimaryKeyExisting() throws Exception {
		PortletPreferences newPortletPreferences = addPortletPreferences();

		PortletPreferences existingPortletPreferences = _persistence.findByPrimaryKey(newPortletPreferences.getPrimaryKey());

		assertEquals(existingPortletPreferences, newPortletPreferences);
	}

	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = nextLong();

		try {
			_persistence.findByPrimaryKey(pk);

			fail(
				"Missing entity did not throw NoSuchPortletPreferencesException");
		}
		catch (NoSuchPortletPreferencesException nsee) {
		}
	}

	public void testFetchByPrimaryKeyExisting() throws Exception {
		PortletPreferences newPortletPreferences = addPortletPreferences();

		PortletPreferences existingPortletPreferences = _persistence.fetchByPrimaryKey(newPortletPreferences.getPrimaryKey());

		assertEquals(existingPortletPreferences, newPortletPreferences);
	}

	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = nextLong();

		PortletPreferences missingPortletPreferences = _persistence.fetchByPrimaryKey(pk);

		assertNull(missingPortletPreferences);
	}

	public void testDynamicQueryByPrimaryKeyExisting()
		throws Exception {
		PortletPreferences newPortletPreferences = addPortletPreferences();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(PortletPreferences.class,
				PortletPreferences.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("portletPreferencesId",
				newPortletPreferences.getPortletPreferencesId()));

		List<PortletPreferences> result = _persistence.findWithDynamicQuery(dynamicQuery);

		assertEquals(1, result.size());

		PortletPreferences existingPortletPreferences = result.get(0);

		assertEquals(existingPortletPreferences, newPortletPreferences);
	}

	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(PortletPreferences.class,
				PortletPreferences.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("portletPreferencesId",
				nextLong()));

		List<PortletPreferences> result = _persistence.findWithDynamicQuery(dynamicQuery);

		assertEquals(0, result.size());
	}

	public void testDynamicQueryByProjectionExisting()
		throws Exception {
		PortletPreferences newPortletPreferences = addPortletPreferences();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(PortletPreferences.class,
				PortletPreferences.class.getClassLoader());

		dynamicQuery.setProjection(ProjectionFactoryUtil.property(
				"portletPreferencesId"));

		Object newPortletPreferencesId = newPortletPreferences.getPortletPreferencesId();

		dynamicQuery.add(RestrictionsFactoryUtil.in("portletPreferencesId",
				new Object[] { newPortletPreferencesId }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		assertEquals(1, result.size());

		Object existingPortletPreferencesId = result.get(0);

		assertEquals(existingPortletPreferencesId, newPortletPreferencesId);
	}

	public void testDynamicQueryByProjectionMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(PortletPreferences.class,
				PortletPreferences.class.getClassLoader());

		dynamicQuery.setProjection(ProjectionFactoryUtil.property(
				"portletPreferencesId"));

		dynamicQuery.add(RestrictionsFactoryUtil.in("portletPreferencesId",
				new Object[] { nextLong() }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		assertEquals(0, result.size());
	}

	public void testResetOriginalValues() throws Exception {
		if (!PropsValues.HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE) {
			return;
		}

		PortletPreferences newPortletPreferences = addPortletPreferences();

		_persistence.clearCache();

		PortletPreferencesModelImpl existingPortletPreferencesModelImpl = (PortletPreferencesModelImpl)_persistence.findByPrimaryKey(newPortletPreferences.getPrimaryKey());

		assertEquals(existingPortletPreferencesModelImpl.getOwnerId(),
			existingPortletPreferencesModelImpl.getOriginalOwnerId());
		assertEquals(existingPortletPreferencesModelImpl.getOwnerType(),
			existingPortletPreferencesModelImpl.getOriginalOwnerType());
		assertEquals(existingPortletPreferencesModelImpl.getPlid(),
			existingPortletPreferencesModelImpl.getOriginalPlid());
		assertTrue(Validator.equals(
				existingPortletPreferencesModelImpl.getPortletId(),
				existingPortletPreferencesModelImpl.getOriginalPortletId()));
	}

	protected PortletPreferences addPortletPreferences()
		throws Exception {
		long pk = nextLong();

		PortletPreferences portletPreferences = _persistence.create(pk);

		portletPreferences.setOwnerId(nextLong());

		portletPreferences.setOwnerType(nextInt());

		portletPreferences.setPlid(nextLong());

		portletPreferences.setPortletId(randomString());

		portletPreferences.setPreferences(randomString());

		_persistence.update(portletPreferences, false);

		return portletPreferences;
	}

	private PortletPreferencesPersistence _persistence;
}