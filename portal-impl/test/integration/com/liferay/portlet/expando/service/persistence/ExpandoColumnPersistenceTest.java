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
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.test.ExecutionTestListeners;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.service.ServiceTestUtil;
import com.liferay.portal.service.persistence.BasePersistence;
import com.liferay.portal.service.persistence.PersistenceExecutionTestListener;
import com.liferay.portal.test.LiferayPersistenceIntegrationJUnitTestRunner;
import com.liferay.portal.test.persistence.TransactionalPersistenceAdvice;
import com.liferay.portal.util.PropsValues;

import com.liferay.portlet.expando.NoSuchColumnException;
import com.liferay.portlet.expando.model.ExpandoColumn;
import com.liferay.portlet.expando.model.impl.ExpandoColumnModelImpl;

import org.junit.After;
import org.junit.Assert;
import org.junit.Test;

import org.junit.runner.RunWith;

import java.io.Serializable;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author Brian Wing Shun Chan
 */
@ExecutionTestListeners(listeners =  {
	PersistenceExecutionTestListener.class})
@RunWith(LiferayPersistenceIntegrationJUnitTestRunner.class)
public class ExpandoColumnPersistenceTest {
	@After
	public void tearDown() throws Exception {
		Map<Serializable, BasePersistence<?>> basePersistences = _transactionalPersistenceAdvice.getBasePersistences();

		Set<Serializable> primaryKeys = basePersistences.keySet();

		for (Serializable primaryKey : primaryKeys) {
			BasePersistence<?> basePersistence = basePersistences.get(primaryKey);

			try {
				basePersistence.remove(primaryKey);
			}
			catch (Exception e) {
				if (_log.isDebugEnabled()) {
					_log.debug("The model with primary key " + primaryKey +
						" was already deleted");
				}
			}
		}

		_transactionalPersistenceAdvice.reset();
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

		_persistence.update(newExpandoColumn);

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

	@Test
	public void testResetOriginalValues() throws Exception {
		if (!PropsValues.HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE) {
			return;
		}

		ExpandoColumn newExpandoColumn = addExpandoColumn();

		_persistence.clearCache();

		ExpandoColumnModelImpl existingExpandoColumnModelImpl = (ExpandoColumnModelImpl)_persistence.findByPrimaryKey(newExpandoColumn.getPrimaryKey());

		Assert.assertEquals(existingExpandoColumnModelImpl.getTableId(),
			existingExpandoColumnModelImpl.getOriginalTableId());
		Assert.assertTrue(Validator.equals(
				existingExpandoColumnModelImpl.getName(),
				existingExpandoColumnModelImpl.getOriginalName()));
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

		_persistence.update(expandoColumn);

		return expandoColumn;
	}

	private static Log _log = LogFactoryUtil.getLog(ExpandoColumnPersistenceTest.class);
	private ExpandoColumnPersistence _persistence = (ExpandoColumnPersistence)PortalBeanLocatorUtil.locate(ExpandoColumnPersistence.class.getName());
	private TransactionalPersistenceAdvice _transactionalPersistenceAdvice = (TransactionalPersistenceAdvice)PortalBeanLocatorUtil.locate(TransactionalPersistenceAdvice.class.getName());
}