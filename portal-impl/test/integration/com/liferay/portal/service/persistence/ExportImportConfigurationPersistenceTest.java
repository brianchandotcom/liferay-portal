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

package com.liferay.portal.service.persistence;

import com.liferay.portal.NoSuchExportImportConfigurationException;
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
import com.liferay.portal.model.ExportImportConfiguration;
import com.liferay.portal.model.ModelListener;
import com.liferay.portal.service.ServiceTestUtil;
import com.liferay.portal.service.persistence.BasePersistence;
import com.liferay.portal.service.persistence.PersistenceExecutionTestListener;
import com.liferay.portal.test.LiferayPersistenceIntegrationJUnitTestRunner;
import com.liferay.portal.test.persistence.TransactionalPersistenceAdvice;

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
public class ExportImportConfigurationPersistenceTest {
	@Before
	public void setUp() {
		_modelListeners = _persistence.getListeners();

		for (ModelListener<ExportImportConfiguration> modelListener : _modelListeners) {
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

		for (ModelListener<ExportImportConfiguration> modelListener : _modelListeners) {
			_persistence.registerListener(modelListener);
		}
	}

	@Test
	public void testCreate() throws Exception {
		long pk = ServiceTestUtil.nextLong();

		ExportImportConfiguration exportImportConfiguration = _persistence.create(pk);

		Assert.assertNotNull(exportImportConfiguration);

		Assert.assertEquals(exportImportConfiguration.getPrimaryKey(), pk);
	}

	@Test
	public void testRemove() throws Exception {
		ExportImportConfiguration newExportImportConfiguration = addExportImportConfiguration();

		_persistence.remove(newExportImportConfiguration);

		ExportImportConfiguration existingExportImportConfiguration = _persistence.fetchByPrimaryKey(newExportImportConfiguration.getPrimaryKey());

		Assert.assertNull(existingExportImportConfiguration);
	}

	@Test
	public void testUpdateNew() throws Exception {
		addExportImportConfiguration();
	}

	@Test
	public void testUpdateExisting() throws Exception {
		long pk = ServiceTestUtil.nextLong();

		ExportImportConfiguration newExportImportConfiguration = _persistence.create(pk);

		newExportImportConfiguration.setMvccVersion(ServiceTestUtil.nextLong());

		newExportImportConfiguration.setGroupId(ServiceTestUtil.nextLong());

		newExportImportConfiguration.setCompanyId(ServiceTestUtil.nextLong());

		newExportImportConfiguration.setUserId(ServiceTestUtil.nextLong());

		newExportImportConfiguration.setUserName(ServiceTestUtil.randomString());

		newExportImportConfiguration.setCreateDate(ServiceTestUtil.nextDate());

		newExportImportConfiguration.setModifiedDate(ServiceTestUtil.nextDate());

		newExportImportConfiguration.setName(ServiceTestUtil.randomString());

		newExportImportConfiguration.setDescription(ServiceTestUtil.randomString());

		newExportImportConfiguration.setType(ServiceTestUtil.nextInt());

		newExportImportConfiguration.setSettings(ServiceTestUtil.randomString());

		newExportImportConfiguration.setStatus(ServiceTestUtil.nextInt());

		newExportImportConfiguration.setStatusByUserId(ServiceTestUtil.nextLong());

		newExportImportConfiguration.setStatusByUserName(ServiceTestUtil.randomString());

		newExportImportConfiguration.setStatusDate(ServiceTestUtil.nextDate());

		_persistence.update(newExportImportConfiguration);

		ExportImportConfiguration existingExportImportConfiguration = _persistence.findByPrimaryKey(newExportImportConfiguration.getPrimaryKey());

		Assert.assertEquals(existingExportImportConfiguration.getMvccVersion(),
			newExportImportConfiguration.getMvccVersion());
		Assert.assertEquals(existingExportImportConfiguration.getExportImportConfigurationId(),
			newExportImportConfiguration.getExportImportConfigurationId());
		Assert.assertEquals(existingExportImportConfiguration.getGroupId(),
			newExportImportConfiguration.getGroupId());
		Assert.assertEquals(existingExportImportConfiguration.getCompanyId(),
			newExportImportConfiguration.getCompanyId());
		Assert.assertEquals(existingExportImportConfiguration.getUserId(),
			newExportImportConfiguration.getUserId());
		Assert.assertEquals(existingExportImportConfiguration.getUserName(),
			newExportImportConfiguration.getUserName());
		Assert.assertEquals(Time.getShortTimestamp(
				existingExportImportConfiguration.getCreateDate()),
			Time.getShortTimestamp(newExportImportConfiguration.getCreateDate()));
		Assert.assertEquals(Time.getShortTimestamp(
				existingExportImportConfiguration.getModifiedDate()),
			Time.getShortTimestamp(
				newExportImportConfiguration.getModifiedDate()));
		Assert.assertEquals(existingExportImportConfiguration.getName(),
			newExportImportConfiguration.getName());
		Assert.assertEquals(existingExportImportConfiguration.getDescription(),
			newExportImportConfiguration.getDescription());
		Assert.assertEquals(existingExportImportConfiguration.getType(),
			newExportImportConfiguration.getType());
		Assert.assertEquals(existingExportImportConfiguration.getSettings(),
			newExportImportConfiguration.getSettings());
		Assert.assertEquals(existingExportImportConfiguration.getStatus(),
			newExportImportConfiguration.getStatus());
		Assert.assertEquals(existingExportImportConfiguration.getStatusByUserId(),
			newExportImportConfiguration.getStatusByUserId());
		Assert.assertEquals(existingExportImportConfiguration.getStatusByUserName(),
			newExportImportConfiguration.getStatusByUserName());
		Assert.assertEquals(Time.getShortTimestamp(
				existingExportImportConfiguration.getStatusDate()),
			Time.getShortTimestamp(newExportImportConfiguration.getStatusDate()));
	}

	@Test
	public void testCountByGroupId() {
		try {
			_persistence.countByGroupId(ServiceTestUtil.nextLong());

			_persistence.countByGroupId(0L);
		}
		catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void testCountByCompanyId() {
		try {
			_persistence.countByCompanyId(ServiceTestUtil.nextLong());

			_persistence.countByCompanyId(0L);
		}
		catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void testCountByG_T() {
		try {
			_persistence.countByG_T(ServiceTestUtil.nextLong(),
				ServiceTestUtil.nextInt());

			_persistence.countByG_T(0L, 0);
		}
		catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void testCountByG_S() {
		try {
			_persistence.countByG_S(ServiceTestUtil.nextLong(),
				ServiceTestUtil.nextInt());

			_persistence.countByG_S(0L, 0);
		}
		catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void testCountByG_T_S() {
		try {
			_persistence.countByG_T_S(ServiceTestUtil.nextLong(),
				ServiceTestUtil.nextInt(), ServiceTestUtil.nextInt());

			_persistence.countByG_T_S(0L, 0, 0);
		}
		catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void testFindByPrimaryKeyExisting() throws Exception {
		ExportImportConfiguration newExportImportConfiguration = addExportImportConfiguration();

		ExportImportConfiguration existingExportImportConfiguration = _persistence.findByPrimaryKey(newExportImportConfiguration.getPrimaryKey());

		Assert.assertEquals(existingExportImportConfiguration,
			newExportImportConfiguration);
	}

	@Test
	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = ServiceTestUtil.nextLong();

		try {
			_persistence.findByPrimaryKey(pk);

			Assert.fail(
				"Missing entity did not throw NoSuchExportImportConfigurationException");
		}
		catch (NoSuchExportImportConfigurationException nsee) {
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
		return OrderByComparatorFactoryUtil.create("ExportImportConfiguration",
			"mvccVersion", true, "exportImportConfigurationId", true,
			"groupId", true, "companyId", true, "userId", true, "userName",
			true, "createDate", true, "modifiedDate", true, "name", true,
			"description", true, "type", true, "settings", true, "status",
			true, "statusByUserId", true, "statusByUserName", true,
			"statusDate", true);
	}

	@Test
	public void testFetchByPrimaryKeyExisting() throws Exception {
		ExportImportConfiguration newExportImportConfiguration = addExportImportConfiguration();

		ExportImportConfiguration existingExportImportConfiguration = _persistence.fetchByPrimaryKey(newExportImportConfiguration.getPrimaryKey());

		Assert.assertEquals(existingExportImportConfiguration,
			newExportImportConfiguration);
	}

	@Test
	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = ServiceTestUtil.nextLong();

		ExportImportConfiguration missingExportImportConfiguration = _persistence.fetchByPrimaryKey(pk);

		Assert.assertNull(missingExportImportConfiguration);
	}

	@Test
	public void testActionableDynamicQuery() throws Exception {
		final IntegerWrapper count = new IntegerWrapper();

		ActionableDynamicQuery actionableDynamicQuery = new ExportImportConfigurationActionableDynamicQuery() {
				@Override
				protected void performAction(Object object) {
					ExportImportConfiguration exportImportConfiguration = (ExportImportConfiguration)object;

					Assert.assertNotNull(exportImportConfiguration);

					count.increment();
				}
			};

		actionableDynamicQuery.performActions();

		Assert.assertEquals(count.getValue(), _persistence.countAll());
	}

	@Test
	public void testDynamicQueryByPrimaryKeyExisting()
		throws Exception {
		ExportImportConfiguration newExportImportConfiguration = addExportImportConfiguration();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(ExportImportConfiguration.class,
				ExportImportConfiguration.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq(
				"exportImportConfigurationId",
				newExportImportConfiguration.getExportImportConfigurationId()));

		List<ExportImportConfiguration> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		ExportImportConfiguration existingExportImportConfiguration = result.get(0);

		Assert.assertEquals(existingExportImportConfiguration,
			newExportImportConfiguration);
	}

	@Test
	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(ExportImportConfiguration.class,
				ExportImportConfiguration.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq(
				"exportImportConfigurationId", ServiceTestUtil.nextLong()));

		List<ExportImportConfiguration> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testDynamicQueryByProjectionExisting()
		throws Exception {
		ExportImportConfiguration newExportImportConfiguration = addExportImportConfiguration();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(ExportImportConfiguration.class,
				ExportImportConfiguration.class.getClassLoader());

		dynamicQuery.setProjection(ProjectionFactoryUtil.property(
				"exportImportConfigurationId"));

		Object newExportImportConfigurationId = newExportImportConfiguration.getExportImportConfigurationId();

		dynamicQuery.add(RestrictionsFactoryUtil.in(
				"exportImportConfigurationId",
				new Object[] { newExportImportConfigurationId }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		Object existingExportImportConfigurationId = result.get(0);

		Assert.assertEquals(existingExportImportConfigurationId,
			newExportImportConfigurationId);
	}

	@Test
	public void testDynamicQueryByProjectionMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(ExportImportConfiguration.class,
				ExportImportConfiguration.class.getClassLoader());

		dynamicQuery.setProjection(ProjectionFactoryUtil.property(
				"exportImportConfigurationId"));

		dynamicQuery.add(RestrictionsFactoryUtil.in(
				"exportImportConfigurationId",
				new Object[] { ServiceTestUtil.nextLong() }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	protected ExportImportConfiguration addExportImportConfiguration()
		throws Exception {
		long pk = ServiceTestUtil.nextLong();

		ExportImportConfiguration exportImportConfiguration = _persistence.create(pk);

		exportImportConfiguration.setMvccVersion(ServiceTestUtil.nextLong());

		exportImportConfiguration.setGroupId(ServiceTestUtil.nextLong());

		exportImportConfiguration.setCompanyId(ServiceTestUtil.nextLong());

		exportImportConfiguration.setUserId(ServiceTestUtil.nextLong());

		exportImportConfiguration.setUserName(ServiceTestUtil.randomString());

		exportImportConfiguration.setCreateDate(ServiceTestUtil.nextDate());

		exportImportConfiguration.setModifiedDate(ServiceTestUtil.nextDate());

		exportImportConfiguration.setName(ServiceTestUtil.randomString());

		exportImportConfiguration.setDescription(ServiceTestUtil.randomString());

		exportImportConfiguration.setType(ServiceTestUtil.nextInt());

		exportImportConfiguration.setSettings(ServiceTestUtil.randomString());

		exportImportConfiguration.setStatus(ServiceTestUtil.nextInt());

		exportImportConfiguration.setStatusByUserId(ServiceTestUtil.nextLong());

		exportImportConfiguration.setStatusByUserName(ServiceTestUtil.randomString());

		exportImportConfiguration.setStatusDate(ServiceTestUtil.nextDate());

		_persistence.update(exportImportConfiguration);

		return exportImportConfiguration;
	}

	private static Log _log = LogFactoryUtil.getLog(ExportImportConfigurationPersistenceTest.class);
	private ModelListener<ExportImportConfiguration>[] _modelListeners;
	private ExportImportConfigurationPersistence _persistence = (ExportImportConfigurationPersistence)PortalBeanLocatorUtil.locate(ExportImportConfigurationPersistence.class.getName());
	private TransactionalPersistenceAdvice _transactionalPersistenceAdvice = (TransactionalPersistenceAdvice)PortalBeanLocatorUtil.locate(TransactionalPersistenceAdvice.class.getName());
}