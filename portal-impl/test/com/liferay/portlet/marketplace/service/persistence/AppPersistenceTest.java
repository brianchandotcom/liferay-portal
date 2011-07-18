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

package com.liferay.portlet.marketplace.service.persistence;

import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQueryFactoryUtil;
import com.liferay.portal.kernel.dao.orm.ProjectionFactoryUtil;
import com.liferay.portal.kernel.dao.orm.RestrictionsFactoryUtil;
import com.liferay.portal.kernel.util.Time;
import com.liferay.portal.service.persistence.BasePersistenceTestCase;
import com.liferay.portal.util.PropsValues;

import com.liferay.portlet.marketplace.NoSuchAppException;
import com.liferay.portlet.marketplace.model.App;
import com.liferay.portlet.marketplace.model.impl.AppModelImpl;

import java.util.List;

/**
 * @author Brian Wing Shun Chan
 */
public class AppPersistenceTest extends BasePersistenceTestCase {
	@Override
	public void setUp() throws Exception {
		super.setUp();

		_persistence = (AppPersistence)PortalBeanLocatorUtil.locate(AppPersistence.class.getName());
	}

	public void testCreate() throws Exception {
		long pk = nextLong();

		App app = _persistence.create(pk);

		assertNotNull(app);

		assertEquals(app.getPrimaryKey(), pk);
	}

	public void testRemove() throws Exception {
		App newApp = addApp();

		_persistence.remove(newApp);

		App existingApp = _persistence.fetchByPrimaryKey(newApp.getPrimaryKey());

		assertNull(existingApp);
	}

	public void testUpdateNew() throws Exception {
		addApp();
	}

	public void testUpdateExisting() throws Exception {
		long pk = nextLong();

		App newApp = _persistence.create(pk);

		newApp.setUuid(randomString());

		newApp.setCompanyId(nextLong());

		newApp.setUserId(nextLong());

		newApp.setUserName(randomString());

		newApp.setCreateDate(nextDate());

		newApp.setModifiedDate(nextDate());

		newApp.setMarketplaceAppId(nextLong());

		newApp.setVersion(randomString());

		_persistence.update(newApp, false);

		App existingApp = _persistence.findByPrimaryKey(newApp.getPrimaryKey());

		assertEquals(existingApp.getUuid(), newApp.getUuid());
		assertEquals(existingApp.getAppId(), newApp.getAppId());
		assertEquals(existingApp.getCompanyId(), newApp.getCompanyId());
		assertEquals(existingApp.getUserId(), newApp.getUserId());
		assertEquals(existingApp.getUserName(), newApp.getUserName());
		assertEquals(Time.getShortTimestamp(existingApp.getCreateDate()),
			Time.getShortTimestamp(newApp.getCreateDate()));
		assertEquals(Time.getShortTimestamp(existingApp.getModifiedDate()),
			Time.getShortTimestamp(newApp.getModifiedDate()));
		assertEquals(existingApp.getMarketplaceAppId(),
			newApp.getMarketplaceAppId());
		assertEquals(existingApp.getVersion(), newApp.getVersion());
	}

	public void testFindByPrimaryKeyExisting() throws Exception {
		App newApp = addApp();

		App existingApp = _persistence.findByPrimaryKey(newApp.getPrimaryKey());

		assertEquals(existingApp, newApp);
	}

	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = nextLong();

		try {
			_persistence.findByPrimaryKey(pk);

			fail("Missing entity did not throw NoSuchAppException");
		}
		catch (NoSuchAppException nsee) {
		}
	}

	public void testFetchByPrimaryKeyExisting() throws Exception {
		App newApp = addApp();

		App existingApp = _persistence.fetchByPrimaryKey(newApp.getPrimaryKey());

		assertEquals(existingApp, newApp);
	}

	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = nextLong();

		App missingApp = _persistence.fetchByPrimaryKey(pk);

		assertNull(missingApp);
	}

	public void testDynamicQueryByPrimaryKeyExisting()
		throws Exception {
		App newApp = addApp();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(App.class,
				App.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("appId", newApp.getAppId()));

		List<App> result = _persistence.findWithDynamicQuery(dynamicQuery);

		assertEquals(1, result.size());

		App existingApp = result.get(0);

		assertEquals(existingApp, newApp);
	}

	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(App.class,
				App.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("appId", nextLong()));

		List<App> result = _persistence.findWithDynamicQuery(dynamicQuery);

		assertEquals(0, result.size());
	}

	public void testDynamicQueryByProjectionExisting()
		throws Exception {
		App newApp = addApp();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(App.class,
				App.class.getClassLoader());

		dynamicQuery.setProjection(ProjectionFactoryUtil.property("appId"));

		Object newAppId = newApp.getAppId();

		dynamicQuery.add(RestrictionsFactoryUtil.in("appId",
				new Object[] { newAppId }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		assertEquals(1, result.size());

		Object existingAppId = result.get(0);

		assertEquals(existingAppId, newAppId);
	}

	public void testDynamicQueryByProjectionMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(App.class,
				App.class.getClassLoader());

		dynamicQuery.setProjection(ProjectionFactoryUtil.property("appId"));

		dynamicQuery.add(RestrictionsFactoryUtil.in("appId",
				new Object[] { nextLong() }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		assertEquals(0, result.size());
	}

	public void testResetOriginalValues() throws Exception {
		if (!PropsValues.HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE) {
			return;
		}

		App newApp = addApp();

		_persistence.clearCache();

		AppModelImpl existingAppModelImpl = (AppModelImpl)_persistence.findByPrimaryKey(newApp.getPrimaryKey());

		assertEquals(existingAppModelImpl.getMarketplaceAppId(),
			existingAppModelImpl.getOriginalMarketplaceAppId());
	}

	protected App addApp() throws Exception {
		long pk = nextLong();

		App app = _persistence.create(pk);

		app.setUuid(randomString());

		app.setCompanyId(nextLong());

		app.setUserId(nextLong());

		app.setUserName(randomString());

		app.setCreateDate(nextDate());

		app.setModifiedDate(nextDate());

		app.setMarketplaceAppId(nextLong());

		app.setVersion(randomString());

		_persistence.update(app, false);

		return app;
	}

	private AppPersistence _persistence;
}