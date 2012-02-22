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
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.service.persistence.BasePersistenceTestCase;
import com.liferay.portal.util.PropsValues;

import com.liferay.portlet.expando.NoSuchTableException;
import com.liferay.portlet.expando.model.ExpandoTable;
import com.liferay.portlet.expando.model.impl.ExpandoTableModelImpl;

import java.util.List;

/**
 * @author Brian Wing Shun Chan
 */
public class ExpandoTablePersistenceTest extends BasePersistenceTestCase {
	@Override
	public void setUp() throws Exception {
		super.setUp();

		_persistence = (ExpandoTablePersistence)PortalBeanLocatorUtil.locate(ExpandoTablePersistence.class.getName());
	}

	public void testCreate() throws Exception {
		long pk = nextLong();

		ExpandoTable expandoTable = _persistence.create(pk);

		assertNotNull(expandoTable);

		assertEquals(expandoTable.getPrimaryKey(), pk);
	}

	public void testRemove() throws Exception {
		ExpandoTable newExpandoTable = addExpandoTable();

		_persistence.remove(newExpandoTable);

		ExpandoTable existingExpandoTable = _persistence.fetchByPrimaryKey(newExpandoTable.getPrimaryKey());

		assertNull(existingExpandoTable);
	}

	public void testUpdateNew() throws Exception {
		addExpandoTable();
	}

	public void testUpdateExisting() throws Exception {
		long pk = nextLong();

		ExpandoTable newExpandoTable = _persistence.create(pk);

		newExpandoTable.setCompanyId(nextLong());

		newExpandoTable.setClassNameId(nextLong());

		newExpandoTable.setName(randomString());

		_persistence.update(newExpandoTable, false);

		ExpandoTable existingExpandoTable = _persistence.findByPrimaryKey(newExpandoTable.getPrimaryKey());

		assertEquals(existingExpandoTable.getTableId(),
			newExpandoTable.getTableId());
		assertEquals(existingExpandoTable.getCompanyId(),
			newExpandoTable.getCompanyId());
		assertEquals(existingExpandoTable.getClassNameId(),
			newExpandoTable.getClassNameId());
		assertEquals(existingExpandoTable.getName(), newExpandoTable.getName());
	}

	public void testFindByPrimaryKeyExisting() throws Exception {
		ExpandoTable newExpandoTable = addExpandoTable();

		ExpandoTable existingExpandoTable = _persistence.findByPrimaryKey(newExpandoTable.getPrimaryKey());

		assertEquals(existingExpandoTable, newExpandoTable);
	}

	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = nextLong();

		try {
			_persistence.findByPrimaryKey(pk);

			fail("Missing entity did not throw NoSuchTableException");
		}
		catch (NoSuchTableException nsee) {
		}
	}

	public void testFetchByPrimaryKeyExisting() throws Exception {
		ExpandoTable newExpandoTable = addExpandoTable();

		ExpandoTable existingExpandoTable = _persistence.fetchByPrimaryKey(newExpandoTable.getPrimaryKey());

		assertEquals(existingExpandoTable, newExpandoTable);
	}

	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = nextLong();

		ExpandoTable missingExpandoTable = _persistence.fetchByPrimaryKey(pk);

		assertNull(missingExpandoTable);
	}

	public void testDynamicQueryByPrimaryKeyExisting()
		throws Exception {
		ExpandoTable newExpandoTable = addExpandoTable();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(ExpandoTable.class,
				ExpandoTable.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("tableId",
				newExpandoTable.getTableId()));

		List<ExpandoTable> result = _persistence.findWithDynamicQuery(dynamicQuery);

		assertEquals(1, result.size());

		ExpandoTable existingExpandoTable = result.get(0);

		assertEquals(existingExpandoTable, newExpandoTable);
	}

	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(ExpandoTable.class,
				ExpandoTable.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("tableId", nextLong()));

		List<ExpandoTable> result = _persistence.findWithDynamicQuery(dynamicQuery);

		assertEquals(0, result.size());
	}

	public void testDynamicQueryByProjectionExisting()
		throws Exception {
		ExpandoTable newExpandoTable = addExpandoTable();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(ExpandoTable.class,
				ExpandoTable.class.getClassLoader());

		dynamicQuery.setProjection(ProjectionFactoryUtil.property("tableId"));

		Object newTableId = newExpandoTable.getTableId();

		dynamicQuery.add(RestrictionsFactoryUtil.in("tableId",
				new Object[] { newTableId }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		assertEquals(1, result.size());

		Object existingTableId = result.get(0);

		assertEquals(existingTableId, newTableId);
	}

	public void testDynamicQueryByProjectionMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(ExpandoTable.class,
				ExpandoTable.class.getClassLoader());

		dynamicQuery.setProjection(ProjectionFactoryUtil.property("tableId"));

		dynamicQuery.add(RestrictionsFactoryUtil.in("tableId",
				new Object[] { nextLong() }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		assertEquals(0, result.size());
	}

	public void testResetOriginalValues() throws Exception {
		if (!PropsValues.HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE) {
			return;
		}

		ExpandoTable newExpandoTable = addExpandoTable();

		_persistence.clearCache();

		ExpandoTableModelImpl existingExpandoTableModelImpl = (ExpandoTableModelImpl)_persistence.findByPrimaryKey(newExpandoTable.getPrimaryKey());

		assertEquals(existingExpandoTableModelImpl.getCompanyId(),
			existingExpandoTableModelImpl.getOriginalCompanyId());
		assertEquals(existingExpandoTableModelImpl.getClassNameId(),
			existingExpandoTableModelImpl.getOriginalClassNameId());
		assertTrue(Validator.equals(existingExpandoTableModelImpl.getName(),
				existingExpandoTableModelImpl.getOriginalName()));
	}

	protected ExpandoTable addExpandoTable() throws Exception {
		long pk = nextLong();

		ExpandoTable expandoTable = _persistence.create(pk);

		expandoTable.setCompanyId(nextLong());

		expandoTable.setClassNameId(nextLong());

		expandoTable.setName(randomString());

		_persistence.update(expandoTable, false);

		return expandoTable;
	}

	private ExpandoTablePersistence _persistence;
}