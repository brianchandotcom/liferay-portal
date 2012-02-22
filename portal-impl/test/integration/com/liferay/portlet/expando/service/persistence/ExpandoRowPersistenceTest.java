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

package com.liferay.portlet.expando.service.persistence;

import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQueryFactoryUtil;
import com.liferay.portal.kernel.dao.orm.ProjectionFactoryUtil;
import com.liferay.portal.kernel.dao.orm.RestrictionsFactoryUtil;
import com.liferay.portal.service.persistence.BasePersistenceTestCase;
import com.liferay.portal.util.PropsValues;

import com.liferay.portlet.expando.NoSuchRowException;
import com.liferay.portlet.expando.model.ExpandoRow;
import com.liferay.portlet.expando.model.impl.ExpandoRowModelImpl;

import java.util.List;

/**
 * @author Brian Wing Shun Chan
 */
public class ExpandoRowPersistenceTest extends BasePersistenceTestCase {
	@Override
	public void setUp() throws Exception {
		super.setUp();

		_persistence = (ExpandoRowPersistence)PortalBeanLocatorUtil.locate(ExpandoRowPersistence.class.getName());
	}

	public void testCreate() throws Exception {
		long pk = nextLong();

		ExpandoRow expandoRow = _persistence.create(pk);

		assertNotNull(expandoRow);

		assertEquals(expandoRow.getPrimaryKey(), pk);
	}

	public void testRemove() throws Exception {
		ExpandoRow newExpandoRow = addExpandoRow();

		_persistence.remove(newExpandoRow);

		ExpandoRow existingExpandoRow = _persistence.fetchByPrimaryKey(newExpandoRow.getPrimaryKey());

		assertNull(existingExpandoRow);
	}

	public void testUpdateNew() throws Exception {
		addExpandoRow();
	}

	public void testUpdateExisting() throws Exception {
		long pk = nextLong();

		ExpandoRow newExpandoRow = _persistence.create(pk);

		newExpandoRow.setCompanyId(nextLong());

		newExpandoRow.setTableId(nextLong());

		newExpandoRow.setClassPK(nextLong());

		_persistence.update(newExpandoRow, false);

		ExpandoRow existingExpandoRow = _persistence.findByPrimaryKey(newExpandoRow.getPrimaryKey());

		assertEquals(existingExpandoRow.getRowId(), newExpandoRow.getRowId());
		assertEquals(existingExpandoRow.getCompanyId(),
			newExpandoRow.getCompanyId());
		assertEquals(existingExpandoRow.getTableId(), newExpandoRow.getTableId());
		assertEquals(existingExpandoRow.getClassPK(), newExpandoRow.getClassPK());
	}

	public void testFindByPrimaryKeyExisting() throws Exception {
		ExpandoRow newExpandoRow = addExpandoRow();

		ExpandoRow existingExpandoRow = _persistence.findByPrimaryKey(newExpandoRow.getPrimaryKey());

		assertEquals(existingExpandoRow, newExpandoRow);
	}

	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = nextLong();

		try {
			_persistence.findByPrimaryKey(pk);

			fail("Missing entity did not throw NoSuchRowException");
		}
		catch (NoSuchRowException nsee) {
		}
	}

	public void testFetchByPrimaryKeyExisting() throws Exception {
		ExpandoRow newExpandoRow = addExpandoRow();

		ExpandoRow existingExpandoRow = _persistence.fetchByPrimaryKey(newExpandoRow.getPrimaryKey());

		assertEquals(existingExpandoRow, newExpandoRow);
	}

	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = nextLong();

		ExpandoRow missingExpandoRow = _persistence.fetchByPrimaryKey(pk);

		assertNull(missingExpandoRow);
	}

	public void testDynamicQueryByPrimaryKeyExisting()
		throws Exception {
		ExpandoRow newExpandoRow = addExpandoRow();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(ExpandoRow.class,
				ExpandoRow.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("rowId",
				newExpandoRow.getRowId()));

		List<ExpandoRow> result = _persistence.findWithDynamicQuery(dynamicQuery);

		assertEquals(1, result.size());

		ExpandoRow existingExpandoRow = result.get(0);

		assertEquals(existingExpandoRow, newExpandoRow);
	}

	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(ExpandoRow.class,
				ExpandoRow.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("rowId", nextLong()));

		List<ExpandoRow> result = _persistence.findWithDynamicQuery(dynamicQuery);

		assertEquals(0, result.size());
	}

	public void testDynamicQueryByProjectionExisting()
		throws Exception {
		ExpandoRow newExpandoRow = addExpandoRow();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(ExpandoRow.class,
				ExpandoRow.class.getClassLoader());

		dynamicQuery.setProjection(ProjectionFactoryUtil.property("rowId"));

		Object newRowId = newExpandoRow.getRowId();

		dynamicQuery.add(RestrictionsFactoryUtil.in("rowId",
				new Object[] { newRowId }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		assertEquals(1, result.size());

		Object existingRowId = result.get(0);

		assertEquals(existingRowId, newRowId);
	}

	public void testDynamicQueryByProjectionMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(ExpandoRow.class,
				ExpandoRow.class.getClassLoader());

		dynamicQuery.setProjection(ProjectionFactoryUtil.property("rowId"));

		dynamicQuery.add(RestrictionsFactoryUtil.in("rowId",
				new Object[] { nextLong() }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		assertEquals(0, result.size());
	}

	public void testResetOriginalValues() throws Exception {
		if (!PropsValues.HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE) {
			return;
		}

		ExpandoRow newExpandoRow = addExpandoRow();

		_persistence.clearCache();

		ExpandoRowModelImpl existingExpandoRowModelImpl = (ExpandoRowModelImpl)_persistence.findByPrimaryKey(newExpandoRow.getPrimaryKey());

		assertEquals(existingExpandoRowModelImpl.getTableId(),
			existingExpandoRowModelImpl.getOriginalTableId());
		assertEquals(existingExpandoRowModelImpl.getClassPK(),
			existingExpandoRowModelImpl.getOriginalClassPK());
	}

	protected ExpandoRow addExpandoRow() throws Exception {
		long pk = nextLong();

		ExpandoRow expandoRow = _persistence.create(pk);

		expandoRow.setCompanyId(nextLong());

		expandoRow.setTableId(nextLong());

		expandoRow.setClassPK(nextLong());

		_persistence.update(expandoRow, false);

		return expandoRow;
	}

	private ExpandoRowPersistence _persistence;
}