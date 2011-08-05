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
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.service.persistence.BasePersistenceTestCase;
import com.liferay.portal.util.PropsValues;

import com.liferay.portlet.marketplace.NoSuchModuleException;
import com.liferay.portlet.marketplace.model.Module;
import com.liferay.portlet.marketplace.model.impl.ModuleModelImpl;

import java.util.List;

/**
 * @author Brian Wing Shun Chan
 */
public class ModulePersistenceTest extends BasePersistenceTestCase {
	@Override
	public void setUp() throws Exception {
		super.setUp();

		_persistence = (ModulePersistence)PortalBeanLocatorUtil.locate(ModulePersistence.class.getName());
	}

	public void testCreate() throws Exception {
		long pk = nextLong();

		Module module = _persistence.create(pk);

		assertNotNull(module);

		assertEquals(module.getPrimaryKey(), pk);
	}

	public void testRemove() throws Exception {
		Module newModule = addModule();

		_persistence.remove(newModule);

		Module existingModule = _persistence.fetchByPrimaryKey(newModule.getPrimaryKey());

		assertNull(existingModule);
	}

	public void testUpdateNew() throws Exception {
		addModule();
	}

	public void testUpdateExisting() throws Exception {
		long pk = nextLong();

		Module newModule = _persistence.create(pk);

		newModule.setUuid(randomString());

		newModule.setAppId(nextLong());

		newModule.setContextName(randomString());

		_persistence.update(newModule, false);

		Module existingModule = _persistence.findByPrimaryKey(newModule.getPrimaryKey());

		assertEquals(existingModule.getUuid(), newModule.getUuid());
		assertEquals(existingModule.getModuleId(), newModule.getModuleId());
		assertEquals(existingModule.getAppId(), newModule.getAppId());
		assertEquals(existingModule.getContextName(), newModule.getContextName());
	}

	public void testFindByPrimaryKeyExisting() throws Exception {
		Module newModule = addModule();

		Module existingModule = _persistence.findByPrimaryKey(newModule.getPrimaryKey());

		assertEquals(existingModule, newModule);
	}

	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = nextLong();

		try {
			_persistence.findByPrimaryKey(pk);

			fail("Missing entity did not throw NoSuchModuleException");
		}
		catch (NoSuchModuleException nsee) {
		}
	}

	public void testFetchByPrimaryKeyExisting() throws Exception {
		Module newModule = addModule();

		Module existingModule = _persistence.fetchByPrimaryKey(newModule.getPrimaryKey());

		assertEquals(existingModule, newModule);
	}

	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = nextLong();

		Module missingModule = _persistence.fetchByPrimaryKey(pk);

		assertNull(missingModule);
	}

	public void testDynamicQueryByPrimaryKeyExisting()
		throws Exception {
		Module newModule = addModule();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(Module.class,
				Module.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("moduleId",
				newModule.getModuleId()));

		List<Module> result = _persistence.findWithDynamicQuery(dynamicQuery);

		assertEquals(1, result.size());

		Module existingModule = result.get(0);

		assertEquals(existingModule, newModule);
	}

	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(Module.class,
				Module.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("moduleId", nextLong()));

		List<Module> result = _persistence.findWithDynamicQuery(dynamicQuery);

		assertEquals(0, result.size());
	}

	public void testDynamicQueryByProjectionExisting()
		throws Exception {
		Module newModule = addModule();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(Module.class,
				Module.class.getClassLoader());

		dynamicQuery.setProjection(ProjectionFactoryUtil.property("moduleId"));

		Object newModuleId = newModule.getModuleId();

		dynamicQuery.add(RestrictionsFactoryUtil.in("moduleId",
				new Object[] { newModuleId }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		assertEquals(1, result.size());

		Object existingModuleId = result.get(0);

		assertEquals(existingModuleId, newModuleId);
	}

	public void testDynamicQueryByProjectionMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(Module.class,
				Module.class.getClassLoader());

		dynamicQuery.setProjection(ProjectionFactoryUtil.property("moduleId"));

		dynamicQuery.add(RestrictionsFactoryUtil.in("moduleId",
				new Object[] { nextLong() }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		assertEquals(0, result.size());
	}

	public void testResetOriginalValues() throws Exception {
		if (!PropsValues.HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE) {
			return;
		}

		Module newModule = addModule();

		_persistence.clearCache();

		ModuleModelImpl existingModuleModelImpl = (ModuleModelImpl)_persistence.findByPrimaryKey(newModule.getPrimaryKey());

		assertEquals(existingModuleModelImpl.getAppId(),
			existingModuleModelImpl.getOriginalAppId());
		assertTrue(Validator.equals(existingModuleModelImpl.getContextName(),
				existingModuleModelImpl.getOriginalContextName()));
	}

	protected Module addModule() throws Exception {
		long pk = nextLong();

		Module module = _persistence.create(pk);

		module.setUuid(randomString());

		module.setAppId(nextLong());

		module.setContextName(randomString());

		_persistence.update(module, false);

		return module;
	}

	private ModulePersistence _persistence;
}