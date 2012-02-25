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

import com.liferay.portal.NoSuchLayoutPrototypeException;
import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQueryFactoryUtil;
import com.liferay.portal.kernel.dao.orm.ProjectionFactoryUtil;
import com.liferay.portal.kernel.dao.orm.RestrictionsFactoryUtil;
import com.liferay.portal.model.LayoutPrototype;
import com.liferay.portal.service.persistence.BasePersistenceTestCase;

import java.util.List;

/**
 * @author Brian Wing Shun Chan
 */
public class LayoutPrototypePersistenceTest extends BasePersistenceTestCase {
	@Override
	public void setUp() throws Exception {
		super.setUp();

		_persistence = (LayoutPrototypePersistence)PortalBeanLocatorUtil.locate(LayoutPrototypePersistence.class.getName());
	}

	public void testCreate() throws Exception {
		long pk = nextLong();

		LayoutPrototype layoutPrototype = _persistence.create(pk);

		assertNotNull(layoutPrototype);

		assertEquals(layoutPrototype.getPrimaryKey(), pk);
	}

	public void testRemove() throws Exception {
		LayoutPrototype newLayoutPrototype = addLayoutPrototype();

		_persistence.remove(newLayoutPrototype);

		LayoutPrototype existingLayoutPrototype = _persistence.fetchByPrimaryKey(newLayoutPrototype.getPrimaryKey());

		assertNull(existingLayoutPrototype);
	}

	public void testUpdateNew() throws Exception {
		addLayoutPrototype();
	}

	public void testUpdateExisting() throws Exception {
		long pk = nextLong();

		LayoutPrototype newLayoutPrototype = _persistence.create(pk);

		newLayoutPrototype.setUuid(randomString());

		newLayoutPrototype.setCompanyId(nextLong());

		newLayoutPrototype.setName(randomString());

		newLayoutPrototype.setDescription(randomString());

		newLayoutPrototype.setSettings(randomString());

		newLayoutPrototype.setActive(randomBoolean());

		_persistence.update(newLayoutPrototype, false);

		LayoutPrototype existingLayoutPrototype = _persistence.findByPrimaryKey(newLayoutPrototype.getPrimaryKey());

		assertEquals(existingLayoutPrototype.getUuid(),
			newLayoutPrototype.getUuid());
		assertEquals(existingLayoutPrototype.getLayoutPrototypeId(),
			newLayoutPrototype.getLayoutPrototypeId());
		assertEquals(existingLayoutPrototype.getCompanyId(),
			newLayoutPrototype.getCompanyId());
		assertEquals(existingLayoutPrototype.getName(),
			newLayoutPrototype.getName());
		assertEquals(existingLayoutPrototype.getDescription(),
			newLayoutPrototype.getDescription());
		assertEquals(existingLayoutPrototype.getSettings(),
			newLayoutPrototype.getSettings());
		assertEquals(existingLayoutPrototype.getActive(),
			newLayoutPrototype.getActive());
	}

	public void testFindByPrimaryKeyExisting() throws Exception {
		LayoutPrototype newLayoutPrototype = addLayoutPrototype();

		LayoutPrototype existingLayoutPrototype = _persistence.findByPrimaryKey(newLayoutPrototype.getPrimaryKey());

		assertEquals(existingLayoutPrototype, newLayoutPrototype);
	}

	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = nextLong();

		try {
			_persistence.findByPrimaryKey(pk);

			fail("Missing entity did not throw NoSuchLayoutPrototypeException");
		}
		catch (NoSuchLayoutPrototypeException nsee) {
		}
	}

	public void testFetchByPrimaryKeyExisting() throws Exception {
		LayoutPrototype newLayoutPrototype = addLayoutPrototype();

		LayoutPrototype existingLayoutPrototype = _persistence.fetchByPrimaryKey(newLayoutPrototype.getPrimaryKey());

		assertEquals(existingLayoutPrototype, newLayoutPrototype);
	}

	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = nextLong();

		LayoutPrototype missingLayoutPrototype = _persistence.fetchByPrimaryKey(pk);

		assertNull(missingLayoutPrototype);
	}

	public void testDynamicQueryByPrimaryKeyExisting()
		throws Exception {
		LayoutPrototype newLayoutPrototype = addLayoutPrototype();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(LayoutPrototype.class,
				LayoutPrototype.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("layoutPrototypeId",
				newLayoutPrototype.getLayoutPrototypeId()));

		List<LayoutPrototype> result = _persistence.findWithDynamicQuery(dynamicQuery);

		assertEquals(1, result.size());

		LayoutPrototype existingLayoutPrototype = result.get(0);

		assertEquals(existingLayoutPrototype, newLayoutPrototype);
	}

	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(LayoutPrototype.class,
				LayoutPrototype.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("layoutPrototypeId",
				nextLong()));

		List<LayoutPrototype> result = _persistence.findWithDynamicQuery(dynamicQuery);

		assertEquals(0, result.size());
	}

	public void testDynamicQueryByProjectionExisting()
		throws Exception {
		LayoutPrototype newLayoutPrototype = addLayoutPrototype();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(LayoutPrototype.class,
				LayoutPrototype.class.getClassLoader());

		dynamicQuery.setProjection(ProjectionFactoryUtil.property(
				"layoutPrototypeId"));

		Object newLayoutPrototypeId = newLayoutPrototype.getLayoutPrototypeId();

		dynamicQuery.add(RestrictionsFactoryUtil.in("layoutPrototypeId",
				new Object[] { newLayoutPrototypeId }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		assertEquals(1, result.size());

		Object existingLayoutPrototypeId = result.get(0);

		assertEquals(existingLayoutPrototypeId, newLayoutPrototypeId);
	}

	public void testDynamicQueryByProjectionMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(LayoutPrototype.class,
				LayoutPrototype.class.getClassLoader());

		dynamicQuery.setProjection(ProjectionFactoryUtil.property(
				"layoutPrototypeId"));

		dynamicQuery.add(RestrictionsFactoryUtil.in("layoutPrototypeId",
				new Object[] { nextLong() }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		assertEquals(0, result.size());
	}

	protected LayoutPrototype addLayoutPrototype() throws Exception {
		long pk = nextLong();

		LayoutPrototype layoutPrototype = _persistence.create(pk);

		layoutPrototype.setUuid(randomString());

		layoutPrototype.setCompanyId(nextLong());

		layoutPrototype.setName(randomString());

		layoutPrototype.setDescription(randomString());

		layoutPrototype.setSettings(randomString());

		layoutPrototype.setActive(randomBoolean());

		_persistence.update(layoutPrototype, false);

		return layoutPrototype;
	}

	private LayoutPrototypePersistence _persistence;
}