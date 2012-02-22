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

import com.liferay.portal.NoSuchBrowserTrackerException;
import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQueryFactoryUtil;
import com.liferay.portal.kernel.dao.orm.ProjectionFactoryUtil;
import com.liferay.portal.kernel.dao.orm.RestrictionsFactoryUtil;
import com.liferay.portal.model.BrowserTracker;
import com.liferay.portal.model.impl.BrowserTrackerModelImpl;
import com.liferay.portal.service.persistence.BasePersistenceTestCase;
import com.liferay.portal.util.PropsValues;

import java.util.List;

/**
 * @author Brian Wing Shun Chan
 */
public class BrowserTrackerPersistenceTest extends BasePersistenceTestCase {
	@Override
	public void setUp() throws Exception {
		super.setUp();

		_persistence = (BrowserTrackerPersistence)PortalBeanLocatorUtil.locate(BrowserTrackerPersistence.class.getName());
	}

	public void testCreate() throws Exception {
		long pk = nextLong();

		BrowserTracker browserTracker = _persistence.create(pk);

		assertNotNull(browserTracker);

		assertEquals(browserTracker.getPrimaryKey(), pk);
	}

	public void testRemove() throws Exception {
		BrowserTracker newBrowserTracker = addBrowserTracker();

		_persistence.remove(newBrowserTracker);

		BrowserTracker existingBrowserTracker = _persistence.fetchByPrimaryKey(newBrowserTracker.getPrimaryKey());

		assertNull(existingBrowserTracker);
	}

	public void testUpdateNew() throws Exception {
		addBrowserTracker();
	}

	public void testUpdateExisting() throws Exception {
		long pk = nextLong();

		BrowserTracker newBrowserTracker = _persistence.create(pk);

		newBrowserTracker.setUserId(nextLong());

		newBrowserTracker.setBrowserKey(nextLong());

		_persistence.update(newBrowserTracker, false);

		BrowserTracker existingBrowserTracker = _persistence.findByPrimaryKey(newBrowserTracker.getPrimaryKey());

		assertEquals(existingBrowserTracker.getBrowserTrackerId(),
			newBrowserTracker.getBrowserTrackerId());
		assertEquals(existingBrowserTracker.getUserId(),
			newBrowserTracker.getUserId());
		assertEquals(existingBrowserTracker.getBrowserKey(),
			newBrowserTracker.getBrowserKey());
	}

	public void testFindByPrimaryKeyExisting() throws Exception {
		BrowserTracker newBrowserTracker = addBrowserTracker();

		BrowserTracker existingBrowserTracker = _persistence.findByPrimaryKey(newBrowserTracker.getPrimaryKey());

		assertEquals(existingBrowserTracker, newBrowserTracker);
	}

	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = nextLong();

		try {
			_persistence.findByPrimaryKey(pk);

			fail("Missing entity did not throw NoSuchBrowserTrackerException");
		}
		catch (NoSuchBrowserTrackerException nsee) {
		}
	}

	public void testFetchByPrimaryKeyExisting() throws Exception {
		BrowserTracker newBrowserTracker = addBrowserTracker();

		BrowserTracker existingBrowserTracker = _persistence.fetchByPrimaryKey(newBrowserTracker.getPrimaryKey());

		assertEquals(existingBrowserTracker, newBrowserTracker);
	}

	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = nextLong();

		BrowserTracker missingBrowserTracker = _persistence.fetchByPrimaryKey(pk);

		assertNull(missingBrowserTracker);
	}

	public void testDynamicQueryByPrimaryKeyExisting()
		throws Exception {
		BrowserTracker newBrowserTracker = addBrowserTracker();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(BrowserTracker.class,
				BrowserTracker.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("browserTrackerId",
				newBrowserTracker.getBrowserTrackerId()));

		List<BrowserTracker> result = _persistence.findWithDynamicQuery(dynamicQuery);

		assertEquals(1, result.size());

		BrowserTracker existingBrowserTracker = result.get(0);

		assertEquals(existingBrowserTracker, newBrowserTracker);
	}

	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(BrowserTracker.class,
				BrowserTracker.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("browserTrackerId",
				nextLong()));

		List<BrowserTracker> result = _persistence.findWithDynamicQuery(dynamicQuery);

		assertEquals(0, result.size());
	}

	public void testDynamicQueryByProjectionExisting()
		throws Exception {
		BrowserTracker newBrowserTracker = addBrowserTracker();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(BrowserTracker.class,
				BrowserTracker.class.getClassLoader());

		dynamicQuery.setProjection(ProjectionFactoryUtil.property(
				"browserTrackerId"));

		Object newBrowserTrackerId = newBrowserTracker.getBrowserTrackerId();

		dynamicQuery.add(RestrictionsFactoryUtil.in("browserTrackerId",
				new Object[] { newBrowserTrackerId }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		assertEquals(1, result.size());

		Object existingBrowserTrackerId = result.get(0);

		assertEquals(existingBrowserTrackerId, newBrowserTrackerId);
	}

	public void testDynamicQueryByProjectionMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(BrowserTracker.class,
				BrowserTracker.class.getClassLoader());

		dynamicQuery.setProjection(ProjectionFactoryUtil.property(
				"browserTrackerId"));

		dynamicQuery.add(RestrictionsFactoryUtil.in("browserTrackerId",
				new Object[] { nextLong() }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		assertEquals(0, result.size());
	}

	public void testResetOriginalValues() throws Exception {
		if (!PropsValues.HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE) {
			return;
		}

		BrowserTracker newBrowserTracker = addBrowserTracker();

		_persistence.clearCache();

		BrowserTrackerModelImpl existingBrowserTrackerModelImpl = (BrowserTrackerModelImpl)_persistence.findByPrimaryKey(newBrowserTracker.getPrimaryKey());

		assertEquals(existingBrowserTrackerModelImpl.getUserId(),
			existingBrowserTrackerModelImpl.getOriginalUserId());
	}

	protected BrowserTracker addBrowserTracker() throws Exception {
		long pk = nextLong();

		BrowserTracker browserTracker = _persistence.create(pk);

		browserTracker.setUserId(nextLong());

		browserTracker.setBrowserKey(nextLong());

		_persistence.update(browserTracker, false);

		return browserTracker;
	}

	private BrowserTrackerPersistence _persistence;
}