/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
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
import com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQueryFactoryUtil;
import com.liferay.portal.kernel.dao.orm.ProjectionFactoryUtil;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.RestrictionsFactoryUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.test.ExecutionTestListeners;
import com.liferay.portal.kernel.util.IntegerWrapper;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.OrderByComparatorFactoryUtil;
import com.liferay.portal.kernel.util.Time;
import com.liferay.portal.model.ModelListener;
import com.liferay.portal.service.ServiceTestUtil;
import com.liferay.portal.service.persistence.BasePersistence;
import com.liferay.portal.service.persistence.PersistenceExecutionTestListener;
import com.liferay.portal.test.LiferayPersistenceIntegrationJUnitTestRunner;
import com.liferay.portal.test.persistence.TransactionalPersistenceAdvice;
import com.liferay.portal.util.PropsValues;

import com.liferay.portlet.expando.NoSuchRowException;
import com.liferay.portlet.expando.model.ExpandoRow;
import com.liferay.portlet.expando.model.impl.ExpandoRowModelImpl;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
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
public class ExpandoRowPersistenceTest {
	@Before
	public void setUp() {
		_modelListeners = _persistence.getListeners();

		for (ModelListener<ExpandoRow> modelListener : _modelListeners) {
			_persistence.unregisterListener(modelListener);
		}
	}

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

		for (ModelListener<ExpandoRow> modelListener : _modelListeners) {
			_persistence.registerListener(modelListener);
		}
	}

	@Test
	public void testCreate() throws Exception {
		long pk = ServiceTestUtil.nextLong();

		ExpandoRow expandoRow = _persistence.create(pk);

		Assert.assertNotNull(expandoRow);

		Assert.assertEquals(expandoRow.getPrimaryKey(), pk);
	}

	@Test
	public void testRemove() throws Exception {
		ExpandoRow newExpandoRow = addExpandoRow();

		_persistence.remove(newExpandoRow);

		ExpandoRow existingExpandoRow = _persistence.fetchByPrimaryKey(newExpandoRow.getPrimaryKey());

		Assert.assertNull(existingExpandoRow);
	}

	@Test
	public void testUpdateNew() throws Exception {
		addExpandoRow();
	}

	@Test
	public void testUpdateExisting() throws Exception {
		long pk = ServiceTestUtil.nextLong();

		ExpandoRow newExpandoRow = _persistence.create(pk);

		newExpandoRow.setCompanyId(ServiceTestUtil.nextLong());

		newExpandoRow.setModifiedDate(ServiceTestUtil.nextDate());

		newExpandoRow.setTableId(ServiceTestUtil.nextLong());

		newExpandoRow.setClassPK(ServiceTestUtil.nextLong());

		_persistence.update(newExpandoRow);

		ExpandoRow existingExpandoRow = _persistence.findByPrimaryKey(newExpandoRow.getPrimaryKey());

		Assert.assertEquals(existingExpandoRow.getRowId(),
			newExpandoRow.getRowId());
		Assert.assertEquals(existingExpandoRow.getCompanyId(),
			newExpandoRow.getCompanyId());
		Assert.assertEquals(Time.getShortTimestamp(
				existingExpandoRow.getModifiedDate()),
			Time.getShortTimestamp(newExpandoRow.getModifiedDate()));
		Assert.assertEquals(existingExpandoRow.getTableId(),
			newExpandoRow.getTableId());
		Assert.assertEquals(existingExpandoRow.getClassPK(),
			newExpandoRow.getClassPK());
	}

	@Test
	public void testCountByTableId() {
		try {
			_persistence.countByTableId(ServiceTestUtil.nextLong());

			_persistence.countByTableId(0L);
		}
		catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void testCountByClassPK() {
		try {
			_persistence.countByClassPK(ServiceTestUtil.nextLong());

			_persistence.countByClassPK(0L);
		}
		catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void testCountByT_C() {
		try {
			_persistence.countByT_C(ServiceTestUtil.nextLong(),
				ServiceTestUtil.nextLong());

			_persistence.countByT_C(0L, 0L);
		}
		catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void testFindByPrimaryKeyExisting() throws Exception {
		ExpandoRow newExpandoRow = addExpandoRow();

		ExpandoRow existingExpandoRow = _persistence.findByPrimaryKey(newExpandoRow.getPrimaryKey());

		Assert.assertEquals(existingExpandoRow, newExpandoRow);
	}

	@Test
	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = ServiceTestUtil.nextLong();

		try {
			_persistence.findByPrimaryKey(pk);

			Assert.fail("Missing entity did not throw NoSuchRowException");
		}
		catch (NoSuchRowException nsee) {
		}
	}

	@Test
	public void testFindAll() throws Exception {
		try {
			_persistence.findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS,
				getOrderByComparator());
		}
		catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	protected OrderByComparator getOrderByComparator() {
		return OrderByComparatorFactoryUtil.create("ExpandoRow", "rowId", true,
			"companyId", true, "modifiedDate", true, "tableId", true,
			"classPK", true);
	}

	@Test
	public void testFetchByPrimaryKeyExisting() throws Exception {
		ExpandoRow newExpandoRow = addExpandoRow();

		ExpandoRow existingExpandoRow = _persistence.fetchByPrimaryKey(newExpandoRow.getPrimaryKey());

		Assert.assertEquals(existingExpandoRow, newExpandoRow);
	}

	@Test
	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = ServiceTestUtil.nextLong();

		ExpandoRow missingExpandoRow = _persistence.fetchByPrimaryKey(pk);

		Assert.assertNull(missingExpandoRow);
	}

	@Test
	public void testActionableDynamicQuery() throws Exception {
		final IntegerWrapper count = new IntegerWrapper();

		ActionableDynamicQuery actionableDynamicQuery = new ExpandoRowActionableDynamicQuery() {
				@Override
				protected void performAction(Object object) {
					ExpandoRow expandoRow = (ExpandoRow)object;

					Assert.assertNotNull(expandoRow);

					count.increment();
				}
			};

		actionableDynamicQuery.performActions();

		Assert.assertEquals(count.getValue(), _persistence.countAll());
	}

	@Test
	public void testDynamicQueryByPrimaryKeyExisting()
		throws Exception {
		ExpandoRow newExpandoRow = addExpandoRow();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(ExpandoRow.class,
				ExpandoRow.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("rowId",
				newExpandoRow.getRowId()));

		List<ExpandoRow> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		ExpandoRow existingExpandoRow = result.get(0);

		Assert.assertEquals(existingExpandoRow, newExpandoRow);
	}

	@Test
	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(ExpandoRow.class,
				ExpandoRow.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("rowId",
				ServiceTestUtil.nextLong()));

		List<ExpandoRow> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
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

		Assert.assertEquals(1, result.size());

		Object existingRowId = result.get(0);

		Assert.assertEquals(existingRowId, newRowId);
	}

	@Test
	public void testDynamicQueryByProjectionMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(ExpandoRow.class,
				ExpandoRow.class.getClassLoader());

		dynamicQuery.setProjection(ProjectionFactoryUtil.property("rowId"));

		dynamicQuery.add(RestrictionsFactoryUtil.in("rowId",
				new Object[] { ServiceTestUtil.nextLong() }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testResetOriginalValues() throws Exception {
		if (!PropsValues.HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE) {
			return;
		}

		ExpandoRow newExpandoRow = addExpandoRow();

		_persistence.clearCache();

		ExpandoRowModelImpl existingExpandoRowModelImpl = (ExpandoRowModelImpl)_persistence.findByPrimaryKey(newExpandoRow.getPrimaryKey());

		Assert.assertEquals(existingExpandoRowModelImpl.getTableId(),
			existingExpandoRowModelImpl.getOriginalTableId());
		Assert.assertEquals(existingExpandoRowModelImpl.getClassPK(),
			existingExpandoRowModelImpl.getOriginalClassPK());
	}

	protected ExpandoRow addExpandoRow() throws Exception {
		long pk = ServiceTestUtil.nextLong();

		ExpandoRow expandoRow = _persistence.create(pk);

		expandoRow.setCompanyId(ServiceTestUtil.nextLong());

		expandoRow.setModifiedDate(ServiceTestUtil.nextDate());

		expandoRow.setTableId(ServiceTestUtil.nextLong());

		expandoRow.setClassPK(ServiceTestUtil.nextLong());

		_persistence.update(expandoRow);

		return expandoRow;
	}

	private static Log _log = LogFactoryUtil.getLog(ExpandoRowPersistenceTest.class);
	private ModelListener<ExpandoRow>[] _modelListeners;
	private ExpandoRowPersistence _persistence = (ExpandoRowPersistence)PortalBeanLocatorUtil.locate(ExpandoRowPersistence.class.getName());
	private TransactionalPersistenceAdvice _transactionalPersistenceAdvice = (TransactionalPersistenceAdvice)PortalBeanLocatorUtil.locate(TransactionalPersistenceAdvice.class.getName());
}