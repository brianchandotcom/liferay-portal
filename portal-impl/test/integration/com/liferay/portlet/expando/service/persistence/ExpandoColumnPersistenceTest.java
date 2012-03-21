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
import com.liferay.portal.service.ServiceTestUtil;
import com.liferay.portal.test.ExecutionTestListeners;
import com.liferay.portal.test.LiferayIntegrationJUnitTestRunner;
import com.liferay.portal.test.persistence.PersistenceEnvConfigTestListener;

import com.liferay.portlet.expando.NoSuchColumnException;
import com.liferay.portlet.expando.model.ExpandoColumn;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import org.junit.runner.RunWith;

import java.util.List;

/**
 * @author Brian Wing Shun Chan
 */
@ExecutionTestListeners(listeners =  {
	PersistenceEnvConfigTestListener.class})
@RunWith(LiferayIntegrationJUnitTestRunner.class)
public class ExpandoColumnPersistenceTest {
	@Before
	public void setUp() throws Exception {
		_persistence = (ExpandoColumnPersistence)PortalBeanLocatorUtil.locate(ExpandoColumnPersistence.class.getName());
	}

	@Test
	public void testCreate() throws Exception {
		long pk = ServiceTestUtil.nextLong();

		ExpandoColumn expandoColumn = _persistence.create(pk);

		Assert.assertNotNull(expandoColumn);

		Assert.assertEquals(expandoColumn.getPrimaryKey(), pk);
	}

	@Test
	public void testRemove() throws Exception {
		ExpandoColumn newExpandoColumn = addExpandoColumn();

		_persistence.remove(newExpandoColumn);

		ExpandoColumn existingExpandoColumn = _persistence.fetchByPrimaryKey(newExpandoColumn.getPrimaryKey());

		Assert.assertNull(existingExpandoColumn);
	}

	@Test
	public void testUpdateNew() throws Exception {
		addExpandoColumn();
	}

	@Test
	public void testUpdateExisting() throws Exception {
		long pk = ServiceTestUtil.nextLong();

		ExpandoColumn newExpandoColumn = _persistence.create(pk);

		newExpandoColumn.setCompanyId(ServiceTestUtil.nextLong());

		newExpandoColumn.setTableId(ServiceTestUtil.nextLong());

		newExpandoColumn.setName(ServiceTestUtil.randomString());

		newExpandoColumn.setType(ServiceTestUtil.nextInt());

		newExpandoColumn.setDefaultData(ServiceTestUtil.randomString());

		newExpandoColumn.setTypeSettings(ServiceTestUtil.randomString());

		_persistence.update(newExpandoColumn, false);

		ExpandoColumn existingExpandoColumn = _persistence.findByPrimaryKey(newExpandoColumn.getPrimaryKey());

		Assert.assertEquals(existingExpandoColumn.getColumnId(),
			newExpandoColumn.getColumnId());
		Assert.assertEquals(existingExpandoColumn.getCompanyId(),
			newExpandoColumn.getCompanyId());
		Assert.assertEquals(existingExpandoColumn.getTableId(),
			newExpandoColumn.getTableId());
		Assert.assertEquals(existingExpandoColumn.getName(),
			newExpandoColumn.getName());
		Assert.assertEquals(existingExpandoColumn.getType(),
			newExpandoColumn.getType());
		Assert.assertEquals(existingExpandoColumn.getDefaultData(),
			newExpandoColumn.getDefaultData());
		Assert.assertEquals(existingExpandoColumn.getTypeSettings(),
			newExpandoColumn.getTypeSettings());
	}

	@Test
	public void testFindByPrimaryKeyExisting() throws Exception {
		ExpandoColumn newExpandoColumn = addExpandoColumn();

		ExpandoColumn existingExpandoColumn = _persistence.findByPrimaryKey(newExpandoColumn.getPrimaryKey());

		Assert.assertEquals(existingExpandoColumn, newExpandoColumn);
	}

	@Test
	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = ServiceTestUtil.nextLong();

		try {
			_persistence.findByPrimaryKey(pk);

			Assert.fail("Missing entity did not throw NoSuchColumnException");
		}
		catch (NoSuchColumnException nsee) {
		}
	}

	@Test
	public void testFetchByPrimaryKeyExisting() throws Exception {
		ExpandoColumn newExpandoColumn = addExpandoColumn();

		ExpandoColumn existingExpandoColumn = _persistence.fetchByPrimaryKey(newExpandoColumn.getPrimaryKey());

		Assert.assertEquals(existingExpandoColumn, newExpandoColumn);
	}

	@Test
	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = ServiceTestUtil.nextLong();

		ExpandoColumn missingExpandoColumn = _persistence.fetchByPrimaryKey(pk);

		Assert.assertNull(missingExpandoColumn);
	}

	@Test
	public void testDynamicQueryByPrimaryKeyExisting()
		throws Exception {
		ExpandoColumn newExpandoColumn = addExpandoColumn();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(ExpandoColumn.class,
				ExpandoColumn.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("columnId",
				newExpandoColumn.getColumnId()));

		List<ExpandoColumn> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		ExpandoColumn existingExpandoColumn = result.get(0);

		Assert.assertEquals(existingExpandoColumn, newExpandoColumn);
	}

	@Test
	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(ExpandoColumn.class,
				ExpandoColumn.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("columnId",
				ServiceTestUtil.nextLong()));

		List<ExpandoColumn> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
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

		Assert.assertEquals(1, result.size());

		Object existingColumnId = result.get(0);

		Assert.assertEquals(existingColumnId, newColumnId);
	}

	@Test
	public void testDynamicQueryByProjectionMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(ExpandoColumn.class,
				ExpandoColumn.class.getClassLoader());

		dynamicQuery.setProjection(ProjectionFactoryUtil.property("columnId"));

		dynamicQuery.add(RestrictionsFactoryUtil.in("columnId",
				new Object[] { ServiceTestUtil.nextLong() }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	protected ExpandoColumn addExpandoColumn() throws Exception {
		long pk = ServiceTestUtil.nextLong();

		ExpandoColumn expandoColumn = _persistence.create(pk);

		expandoColumn.setCompanyId(ServiceTestUtil.nextLong());

		expandoColumn.setTableId(ServiceTestUtil.nextLong());

		expandoColumn.setName(ServiceTestUtil.randomString());

		expandoColumn.setType(ServiceTestUtil.nextInt());

		expandoColumn.setDefaultData(ServiceTestUtil.randomString());

		expandoColumn.setTypeSettings(ServiceTestUtil.randomString());

		_persistence.update(expandoColumn, false);

		return expandoColumn;
	}

	private ExpandoColumnPersistence _persistence;
}