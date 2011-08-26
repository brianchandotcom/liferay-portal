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

package com.liferay.portlet.expando.service.persistence;

import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQueryFactoryUtil;
import com.liferay.portal.kernel.dao.orm.ProjectionFactoryUtil;
import com.liferay.portal.kernel.dao.orm.RestrictionsFactoryUtil;
import com.liferay.portal.service.persistence.BasePersistenceTestCase;

import com.liferay.portlet.expando.NoSuchColumnException;
import com.liferay.portlet.expando.model.ExpandoColumn;

import java.util.List;

/**
 * @author Brian Wing Shun Chan
 */
public class ExpandoColumnPersistenceTest extends BasePersistenceTestCase {
	@Override
	public void setUp() throws Exception {
		super.setUp();

		_persistence = (ExpandoColumnPersistence)PortalBeanLocatorUtil.locate(ExpandoColumnPersistence.class.getName());
	}

	public void testCreate() throws Exception {
		long pk = nextLong();

		ExpandoColumn expandoColumn = _persistence.create(pk);

		assertNotNull(expandoColumn);

		assertEquals(expandoColumn.getPrimaryKey(), pk);
	}

	public void testRemove() throws Exception {
		ExpandoColumn newExpandoColumn = addExpandoColumn();

		_persistence.remove(newExpandoColumn);

		ExpandoColumn existingExpandoColumn = _persistence.fetchByPrimaryKey(newExpandoColumn.getPrimaryKey());

		assertNull(existingExpandoColumn);
	}

	public void testUpdateNew() throws Exception {
		addExpandoColumn();
	}

	public void testUpdateExisting() throws Exception {
		long pk = nextLong();

		ExpandoColumn newExpandoColumn = _persistence.create(pk);

		newExpandoColumn.setCompanyId(nextLong());

		newExpandoColumn.setTableId(nextLong());

		newExpandoColumn.setName(randomString());

		newExpandoColumn.setType(nextInt());

		newExpandoColumn.setDefaultData(randomString());

		newExpandoColumn.setTypeSettings(randomString());

		_persistence.update(newExpandoColumn, false);

		ExpandoColumn existingExpandoColumn = _persistence.findByPrimaryKey(newExpandoColumn.getPrimaryKey());

		assertEquals(existingExpandoColumn.getColumnId(),
			newExpandoColumn.getColumnId());
		assertEquals(existingExpandoColumn.getCompanyId(),
			newExpandoColumn.getCompanyId());
		assertEquals(existingExpandoColumn.getTableId(),
			newExpandoColumn.getTableId());
		assertEquals(existingExpandoColumn.getName(), newExpandoColumn.getName());
		assertEquals(existingExpandoColumn.getType(), newExpandoColumn.getType());
		assertEquals(existingExpandoColumn.getDefaultData(),
			newExpandoColumn.getDefaultData());
		assertEquals(existingExpandoColumn.getTypeSettings(),
			newExpandoColumn.getTypeSettings());
	}

	public void testFindByPrimaryKeyExisting() throws Exception {
		ExpandoColumn newExpandoColumn = addExpandoColumn();

		ExpandoColumn existingExpandoColumn = _persistence.findByPrimaryKey(newExpandoColumn.getPrimaryKey());

		assertEquals(existingExpandoColumn, newExpandoColumn);
	}

	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = nextLong();

		try {
			_persistence.findByPrimaryKey(pk);

			fail("Missing entity did not throw NoSuchColumnException");
		}
		catch (NoSuchColumnException nsee) {
		}
	}

	public void testFetchByPrimaryKeyExisting() throws Exception {
		ExpandoColumn newExpandoColumn = addExpandoColumn();

		ExpandoColumn existingExpandoColumn = _persistence.fetchByPrimaryKey(newExpandoColumn.getPrimaryKey());

		assertEquals(existingExpandoColumn, newExpandoColumn);
	}

	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = nextLong();

		ExpandoColumn missingExpandoColumn = _persistence.fetchByPrimaryKey(pk);

		assertNull(missingExpandoColumn);
	}

	public void testDynamicQueryByPrimaryKeyExisting()
		throws Exception {
		ExpandoColumn newExpandoColumn = addExpandoColumn();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(ExpandoColumn.class,
				ExpandoColumn.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("columnId",
				newExpandoColumn.getColumnId()));

		List<ExpandoColumn> result = _persistence.findWithDynamicQuery(dynamicQuery);

		assertEquals(1, result.size());

		ExpandoColumn existingExpandoColumn = result.get(0);

		assertEquals(existingExpandoColumn, newExpandoColumn);
	}

	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(ExpandoColumn.class,
				ExpandoColumn.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("columnId", nextLong()));

		List<ExpandoColumn> result = _persistence.findWithDynamicQuery(dynamicQuery);

		assertEquals(0, result.size());
	}

	public void testDynamicQueryByProjectionExisting()
		throws Exception {
		ExpandoColumn newExpandoColumn = addExpandoColumn();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(ExpandoColumn.class,
				ExpandoColumn.class.getClassLoader());

		dynamicQuery.setProjection(ProjectionFactoryUtil.property("columnId"));

		Object newColumnId = newExpandoColumn.getColumnId();

		dynamicQuery.add(RestrictionsFactoryUtil.in("columnId",
				new Object[] { newColumnId }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		assertEquals(1, result.size());

		Object existingColumnId = result.get(0);

		assertEquals(existingColumnId, newColumnId);
	}

	public void testDynamicQueryByProjectionMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(ExpandoColumn.class,
				ExpandoColumn.class.getClassLoader());

		dynamicQuery.setProjection(ProjectionFactoryUtil.property("columnId"));

		dynamicQuery.add(RestrictionsFactoryUtil.in("columnId",
				new Object[] { nextLong() }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		assertEquals(0, result.size());
	}

	protected ExpandoColumn addExpandoColumn() throws Exception {
		long pk = nextLong();

		ExpandoColumn expandoColumn = _persistence.create(pk);

		expandoColumn.setCompanyId(nextLong());

		expandoColumn.setTableId(nextLong());

		expandoColumn.setName(randomString());

		expandoColumn.setType(nextInt());

		expandoColumn.setDefaultData(randomString());

		expandoColumn.setTypeSettings(randomString());

		_persistence.update(expandoColumn, false);

		return expandoColumn;
	}

	private ExpandoColumnPersistence _persistence;
}