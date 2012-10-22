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
import com.liferay.portal.service.ServiceTestUtil;
import com.liferay.portal.service.persistence.BasePersistence;
import com.liferay.portal.service.persistence.PersistenceExecutionTestListener;
import com.liferay.portal.test.LiferayPersistenceIntegrationJUnitTestRunner;
import com.liferay.portal.test.persistence.TransactionalPersistenceAdvice;
import com.liferay.portal.util.PropsValues;

import com.liferay.portlet.expando.NoSuchValueException;
import com.liferay.portlet.expando.model.ExpandoValue;
import com.liferay.portlet.expando.model.impl.ExpandoValueModelImpl;

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
public class ExpandoValuePersistenceTest {
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

		ExpandoValue expandoValue = _persistence.create(pk);

		Assert.assertNotNull(expandoValue);

		Assert.assertEquals(expandoValue.getPrimaryKey(), pk);
	}

	@Test
	public void testRemove() throws Exception {
		ExpandoValue newExpandoValue = addExpandoValue();

		_persistence.remove(newExpandoValue);

		ExpandoValue existingExpandoValue = _persistence.fetchByPrimaryKey(newExpandoValue.getPrimaryKey());

		Assert.assertNull(existingExpandoValue);
	}

	@Test
	public void testUpdateNew() throws Exception {
		addExpandoValue();
	}

	@Test
	public void testUpdateExisting() throws Exception {
		long pk = ServiceTestUtil.nextLong();

		ExpandoValue newExpandoValue = _persistence.create(pk);

		newExpandoValue.setCompanyId(ServiceTestUtil.nextLong());

		newExpandoValue.setTableId(ServiceTestUtil.nextLong());

		newExpandoValue.setColumnId(ServiceTestUtil.nextLong());

		newExpandoValue.setRowId(ServiceTestUtil.nextLong());

		newExpandoValue.setClassNameId(ServiceTestUtil.nextLong());

		newExpandoValue.setClassPK(ServiceTestUtil.nextLong());

		newExpandoValue.setData(ServiceTestUtil.randomString());

		_persistence.update(newExpandoValue);

		ExpandoValue existingExpandoValue = _persistence.findByPrimaryKey(newExpandoValue.getPrimaryKey());

		Assert.assertEquals(existingExpandoValue.getValueId(),
			newExpandoValue.getValueId());
		Assert.assertEquals(existingExpandoValue.getCompanyId(),
			newExpandoValue.getCompanyId());
		Assert.assertEquals(existingExpandoValue.getTableId(),
			newExpandoValue.getTableId());
		Assert.assertEquals(existingExpandoValue.getColumnId(),
			newExpandoValue.getColumnId());
		Assert.assertEquals(existingExpandoValue.getRowId(),
			newExpandoValue.getRowId());
		Assert.assertEquals(existingExpandoValue.getClassNameId(),
			newExpandoValue.getClassNameId());
		Assert.assertEquals(existingExpandoValue.getClassPK(),
			newExpandoValue.getClassPK());
		Assert.assertEquals(existingExpandoValue.getData(),
			newExpandoValue.getData());
	}

	@Test
	public void testFindByPrimaryKeyExisting() throws Exception {
		ExpandoValue newExpandoValue = addExpandoValue();

		ExpandoValue existingExpandoValue = _persistence.findByPrimaryKey(newExpandoValue.getPrimaryKey());

		Assert.assertEquals(existingExpandoValue, newExpandoValue);
	}

	@Test
	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = ServiceTestUtil.nextLong();

		try {
			_persistence.findByPrimaryKey(pk);

			Assert.fail("Missing entity did not throw NoSuchValueException");
		}
		catch (NoSuchValueException nsee) {
		}
	}

	@Test
	public void testFetchByPrimaryKeyExisting() throws Exception {
		ExpandoValue newExpandoValue = addExpandoValue();

		ExpandoValue existingExpandoValue = _persistence.fetchByPrimaryKey(newExpandoValue.getPrimaryKey());

		Assert.assertEquals(existingExpandoValue, newExpandoValue);
	}

	@Test
	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = ServiceTestUtil.nextLong();

		ExpandoValue missingExpandoValue = _persistence.fetchByPrimaryKey(pk);

		Assert.assertNull(missingExpandoValue);
	}

	@Test
	public void testDynamicQueryByPrimaryKeyExisting()
		throws Exception {
		ExpandoValue newExpandoValue = addExpandoValue();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(ExpandoValue.class,
				ExpandoValue.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("valueId",
				newExpandoValue.getValueId()));

		List<ExpandoValue> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		ExpandoValue existingExpandoValue = result.get(0);

		Assert.assertEquals(existingExpandoValue, newExpandoValue);
	}

	@Test
	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(ExpandoValue.class,
				ExpandoValue.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("valueId",
				ServiceTestUtil.nextLong()));

		List<ExpandoValue> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testDynamicQueryByProjectionExisting()
		throws Exception {
		ExpandoValue newExpandoValue = addExpandoValue();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(ExpandoValue.class,
				ExpandoValue.class.getClassLoader());

		dynamicQuery.setProjection(ProjectionFactoryUtil.property("valueId"));

		Object newValueId = newExpandoValue.getValueId();

		dynamicQuery.add(RestrictionsFactoryUtil.in("valueId",
				new Object[] { newValueId }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		Object existingValueId = result.get(0);

		Assert.assertEquals(existingValueId, newValueId);
	}

	@Test
	public void testDynamicQueryByProjectionMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(ExpandoValue.class,
				ExpandoValue.class.getClassLoader());

		dynamicQuery.setProjection(ProjectionFactoryUtil.property("valueId"));

		dynamicQuery.add(RestrictionsFactoryUtil.in("valueId",
				new Object[] { ServiceTestUtil.nextLong() }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testResetOriginalValues() throws Exception {
		if (!PropsValues.HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE) {
			return;
		}

		ExpandoValue newExpandoValue = addExpandoValue();

		_persistence.clearCache();

		ExpandoValueModelImpl existingExpandoValueModelImpl = (ExpandoValueModelImpl)_persistence.findByPrimaryKey(newExpandoValue.getPrimaryKey());

		Assert.assertEquals(existingExpandoValueModelImpl.getColumnId(),
			existingExpandoValueModelImpl.getOriginalColumnId());
		Assert.assertEquals(existingExpandoValueModelImpl.getRowId(),
			existingExpandoValueModelImpl.getOriginalRowId());

		Assert.assertEquals(existingExpandoValueModelImpl.getTableId(),
			existingExpandoValueModelImpl.getOriginalTableId());
		Assert.assertEquals(existingExpandoValueModelImpl.getColumnId(),
			existingExpandoValueModelImpl.getOriginalColumnId());
		Assert.assertEquals(existingExpandoValueModelImpl.getClassPK(),
			existingExpandoValueModelImpl.getOriginalClassPK());
	}

	protected ExpandoValue addExpandoValue() throws Exception {
		long pk = ServiceTestUtil.nextLong();

		ExpandoValue expandoValue = _persistence.create(pk);

		expandoValue.setCompanyId(ServiceTestUtil.nextLong());

		expandoValue.setTableId(ServiceTestUtil.nextLong());

		expandoValue.setColumnId(ServiceTestUtil.nextLong());

		expandoValue.setRowId(ServiceTestUtil.nextLong());

		expandoValue.setClassNameId(ServiceTestUtil.nextLong());

		expandoValue.setClassPK(ServiceTestUtil.nextLong());

		expandoValue.setData(ServiceTestUtil.randomString());

		_persistence.update(expandoValue);

		return expandoValue;
	}

	private static Log _log = LogFactoryUtil.getLog(ExpandoValuePersistenceTest.class);
	private ExpandoValuePersistence _persistence = (ExpandoValuePersistence)PortalBeanLocatorUtil.locate(ExpandoValuePersistence.class.getName());
	private TransactionalPersistenceAdvice _transactionalPersistenceAdvice = (TransactionalPersistenceAdvice)PortalBeanLocatorUtil.locate(TransactionalPersistenceAdvice.class.getName());
}