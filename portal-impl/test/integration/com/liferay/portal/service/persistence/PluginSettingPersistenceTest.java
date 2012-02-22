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

import com.liferay.portal.NoSuchPluginSettingException;
import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQueryFactoryUtil;
import com.liferay.portal.kernel.dao.orm.ProjectionFactoryUtil;
import com.liferay.portal.kernel.dao.orm.RestrictionsFactoryUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.PluginSetting;
import com.liferay.portal.model.impl.PluginSettingModelImpl;
import com.liferay.portal.service.persistence.BasePersistenceTestCase;
import com.liferay.portal.util.PropsValues;

import java.util.List;

/**
 * @author Brian Wing Shun Chan
 */
public class PluginSettingPersistenceTest extends BasePersistenceTestCase {
	@Override
	public void setUp() throws Exception {
		super.setUp();

		_persistence = (PluginSettingPersistence)PortalBeanLocatorUtil.locate(PluginSettingPersistence.class.getName());
	}

	public void testCreate() throws Exception {
		long pk = nextLong();

		PluginSetting pluginSetting = _persistence.create(pk);

		assertNotNull(pluginSetting);

		assertEquals(pluginSetting.getPrimaryKey(), pk);
	}

	public void testRemove() throws Exception {
		PluginSetting newPluginSetting = addPluginSetting();

		_persistence.remove(newPluginSetting);

		PluginSetting existingPluginSetting = _persistence.fetchByPrimaryKey(newPluginSetting.getPrimaryKey());

		assertNull(existingPluginSetting);
	}

	public void testUpdateNew() throws Exception {
		addPluginSetting();
	}

	public void testUpdateExisting() throws Exception {
		long pk = nextLong();

		PluginSetting newPluginSetting = _persistence.create(pk);

		newPluginSetting.setCompanyId(nextLong());

		newPluginSetting.setPluginId(randomString());

		newPluginSetting.setPluginType(randomString());

		newPluginSetting.setRoles(randomString());

		newPluginSetting.setActive(randomBoolean());

		_persistence.update(newPluginSetting, false);

		PluginSetting existingPluginSetting = _persistence.findByPrimaryKey(newPluginSetting.getPrimaryKey());

		assertEquals(existingPluginSetting.getPluginSettingId(),
			newPluginSetting.getPluginSettingId());
		assertEquals(existingPluginSetting.getCompanyId(),
			newPluginSetting.getCompanyId());
		assertEquals(existingPluginSetting.getPluginId(),
			newPluginSetting.getPluginId());
		assertEquals(existingPluginSetting.getPluginType(),
			newPluginSetting.getPluginType());
		assertEquals(existingPluginSetting.getRoles(),
			newPluginSetting.getRoles());
		assertEquals(existingPluginSetting.getActive(),
			newPluginSetting.getActive());
	}

	public void testFindByPrimaryKeyExisting() throws Exception {
		PluginSetting newPluginSetting = addPluginSetting();

		PluginSetting existingPluginSetting = _persistence.findByPrimaryKey(newPluginSetting.getPrimaryKey());

		assertEquals(existingPluginSetting, newPluginSetting);
	}

	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = nextLong();

		try {
			_persistence.findByPrimaryKey(pk);

			fail("Missing entity did not throw NoSuchPluginSettingException");
		}
		catch (NoSuchPluginSettingException nsee) {
		}
	}

	public void testFetchByPrimaryKeyExisting() throws Exception {
		PluginSetting newPluginSetting = addPluginSetting();

		PluginSetting existingPluginSetting = _persistence.fetchByPrimaryKey(newPluginSetting.getPrimaryKey());

		assertEquals(existingPluginSetting, newPluginSetting);
	}

	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = nextLong();

		PluginSetting missingPluginSetting = _persistence.fetchByPrimaryKey(pk);

		assertNull(missingPluginSetting);
	}

	public void testDynamicQueryByPrimaryKeyExisting()
		throws Exception {
		PluginSetting newPluginSetting = addPluginSetting();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(PluginSetting.class,
				PluginSetting.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("pluginSettingId",
				newPluginSetting.getPluginSettingId()));

		List<PluginSetting> result = _persistence.findWithDynamicQuery(dynamicQuery);

		assertEquals(1, result.size());

		PluginSetting existingPluginSetting = result.get(0);

		assertEquals(existingPluginSetting, newPluginSetting);
	}

	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(PluginSetting.class,
				PluginSetting.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("pluginSettingId",
				nextLong()));

		List<PluginSetting> result = _persistence.findWithDynamicQuery(dynamicQuery);

		assertEquals(0, result.size());
	}

	public void testDynamicQueryByProjectionExisting()
		throws Exception {
		PluginSetting newPluginSetting = addPluginSetting();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(PluginSetting.class,
				PluginSetting.class.getClassLoader());

		dynamicQuery.setProjection(ProjectionFactoryUtil.property(
				"pluginSettingId"));

		Object newPluginSettingId = newPluginSetting.getPluginSettingId();

		dynamicQuery.add(RestrictionsFactoryUtil.in("pluginSettingId",
				new Object[] { newPluginSettingId }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		assertEquals(1, result.size());

		Object existingPluginSettingId = result.get(0);

		assertEquals(existingPluginSettingId, newPluginSettingId);
	}

	public void testDynamicQueryByProjectionMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(PluginSetting.class,
				PluginSetting.class.getClassLoader());

		dynamicQuery.setProjection(ProjectionFactoryUtil.property(
				"pluginSettingId"));

		dynamicQuery.add(RestrictionsFactoryUtil.in("pluginSettingId",
				new Object[] { nextLong() }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		assertEquals(0, result.size());
	}

	public void testResetOriginalValues() throws Exception {
		if (!PropsValues.HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE) {
			return;
		}

		PluginSetting newPluginSetting = addPluginSetting();

		_persistence.clearCache();

		PluginSettingModelImpl existingPluginSettingModelImpl = (PluginSettingModelImpl)_persistence.findByPrimaryKey(newPluginSetting.getPrimaryKey());

		assertEquals(existingPluginSettingModelImpl.getCompanyId(),
			existingPluginSettingModelImpl.getOriginalCompanyId());
		assertTrue(Validator.equals(
				existingPluginSettingModelImpl.getPluginId(),
				existingPluginSettingModelImpl.getOriginalPluginId()));
		assertTrue(Validator.equals(
				existingPluginSettingModelImpl.getPluginType(),
				existingPluginSettingModelImpl.getOriginalPluginType()));
	}

	protected PluginSetting addPluginSetting() throws Exception {
		long pk = nextLong();

		PluginSetting pluginSetting = _persistence.create(pk);

		pluginSetting.setCompanyId(nextLong());

		pluginSetting.setPluginId(randomString());

		pluginSetting.setPluginType(randomString());

		pluginSetting.setRoles(randomString());

		pluginSetting.setActive(randomBoolean());

		_persistence.update(pluginSetting, false);

		return pluginSetting;
	}

	private PluginSettingPersistence _persistence;
}