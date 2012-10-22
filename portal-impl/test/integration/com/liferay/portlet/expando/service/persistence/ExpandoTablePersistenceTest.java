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

import com.liferay.portlet.expando.NoSuchTableException;
import com.liferay.portlet.expando.model.ExpandoTable;
import com.liferay.portlet.expando.model.impl.ExpandoTableModelImpl;

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
public class ExpandoTablePersistenceTest {
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

		ExpandoTable expandoTable = _persistence.create(pk);

		Assert.assertNotNull(expandoTable);

		Assert.assertEquals(expandoTable.getPrimaryKey(), pk);
	}

	@Test
	public void testRemove() throws Exception {
		ExpandoTable newExpandoTable = addExpandoTable();

		_persistence.remove(newExpandoTable);

		ExpandoTable existingExpandoTable = _persistence.fetchByPrimaryKey(newExpandoTable.getPrimaryKey());

		Assert.assertNull(existingExpandoTable);
	}

	@Test
	public void testUpdateNew() throws Exception {
		addExpandoTable();
	}

	@Test
	public void testUpdateExisting() throws Exception {
		long pk = ServiceTestUtil.nextLong();

		ExpandoTable newExpandoTable = _persistence.create(pk);

		newExpandoTable.setCompanyId(ServiceTestUtil.nextLong());

		newExpandoTable.setClassNameId(ServiceTestUtil.nextLong());

		newExpandoTable.setName(ServiceTestUtil.randomString());

		_persistence.update(newExpandoTable);

		ExpandoTable existingExpandoTable = _persistence.findByPrimaryKey(newExpandoTable.getPrimaryKey());

		Assert.assertEquals(existingExpandoTable.getTableId(),
			newExpandoTable.getTableId());
		Assert.assertEquals(existingExpandoTable.getCompanyId(),
			newExpandoTable.getCompanyId());
		Assert.assertEquals(existingExpandoTable.getClassNameId(),
			newExpandoTable.getClassNameId());
		Assert.assertEquals(existingExpandoTable.getName(),
			newExpandoTable.getName());
	}

	@Test
	public void testFindByPrimaryKeyExisting() throws Exception {
		ExpandoTable newExpandoTable = addExpandoTable();

		ExpandoTable existingExpandoTable = _persistence.findByPrimaryKey(newExpandoTable.getPrimaryKey());

		Assert.assertEquals(existingExpandoTable, newExpandoTable);
	}

	@Test
	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = ServiceTestUtil.nextLong();

		try {
			_persistence.findByPrimaryKey(pk);

			Assert.fail("Missing entity did not throw NoSuchTableException");
		}
		catch (NoSuchTableException nsee) {
		}
	}

	@Test
	public void testFetchByPrimaryKeyExisting() throws Exception {
		ExpandoTable newExpandoTable = addExpandoTable();

		ExpandoTable existingExpandoTable = _persistence.fetchByPrimaryKey(newExpandoTable.getPrimaryKey());

		Assert.assertEquals(existingExpandoTable, newExpandoTable);
	}

	@Test
	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = ServiceTestUtil.nextLong();

		ExpandoTable missingExpandoTable = _persistence.fetchByPrimaryKey(pk);

		Assert.assertNull(missingExpandoTable);
	}

	@Test
	public void testDynamicQueryByPrimaryKeyExisting()
		throws Exception {
		ExpandoTable newExpandoTable = addExpandoTable();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(ExpandoTable.class,
				ExpandoTable.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("tableId",
				newExpandoTable.getTableId()));

		List<ExpandoTable> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		ExpandoTable existingExpandoTable = result.get(0);

		Assert.assertEquals(existingExpandoTable, newExpandoTable);
	}

	@Test
	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(ExpandoTable.class,
				ExpandoTable.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("tableId",
				ServiceTestUtil.nextLong()));

		List<ExpandoTable> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
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

		Assert.assertEquals(1, result.size());

		Object existingTableId = result.get(0);

		Assert.assertEquals(existingTableId, newTableId);
	}

	@Test
	public void testDynamicQueryByProjectionMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(ExpandoTable.class,
				ExpandoTable.class.getClassLoader());

		dynamicQuery.setProjection(ProjectionFactoryUtil.property("tableId"));

		dynamicQuery.add(RestrictionsFactoryUtil.in("tableId",
				new Object[] { ServiceTestUtil.nextLong() }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testResetOriginalValues() throws Exception {
		if (!PropsValues.HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE) {
			return;
		}

		ExpandoTable newExpandoTable = addExpandoTable();

		_persistence.clearCache();

		ExpandoTableModelImpl existingExpandoTableModelImpl = (ExpandoTableModelImpl)_persistence.findByPrimaryKey(newExpandoTable.getPrimaryKey());

		Assert.assertEquals(existingExpandoTableModelImpl.getCompanyId(),
			existingExpandoTableModelImpl.getOriginalCompanyId());
		Assert.assertEquals(existingExpandoTableModelImpl.getClassNameId(),
			existingExpandoTableModelImpl.getOriginalClassNameId());
		Assert.assertTrue(Validator.equals(
				existingExpandoTableModelImpl.getName(),
				existingExpandoTableModelImpl.getOriginalName()));
	}

	protected ExpandoTable addExpandoTable() throws Exception {
		long pk = ServiceTestUtil.nextLong();

		ExpandoTable expandoTable = _persistence.create(pk);

		expandoTable.setCompanyId(ServiceTestUtil.nextLong());

		expandoTable.setClassNameId(ServiceTestUtil.nextLong());

		expandoTable.setName(ServiceTestUtil.randomString());

		_persistence.update(expandoTable);

		return expandoTable;
	}

	private static Log _log = LogFactoryUtil.getLog(ExpandoTablePersistenceTest.class);
	private ExpandoTablePersistence _persistence = (ExpandoTablePersistence)PortalBeanLocatorUtil.locate(ExpandoTablePersistence.class.getName());
	private TransactionalPersistenceAdvice _transactionalPersistenceAdvice = (TransactionalPersistenceAdvice)PortalBeanLocatorUtil.locate(TransactionalPersistenceAdvice.class.getName());
}