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

package com.liferay.portlet.softwarecatalog.service.persistence;

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

import com.liferay.portlet.softwarecatalog.NoSuchFrameworkVersionException;
import com.liferay.portlet.softwarecatalog.model.SCFrameworkVersion;

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
public class SCFrameworkVersionPersistenceTest {
	@Before
	public void setUp() {
		_modelListeners = _persistence.getListeners();

		for (ModelListener<SCFrameworkVersion> modelListener : _modelListeners) {
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

		for (ModelListener<SCFrameworkVersion> modelListener : _modelListeners) {
			_persistence.registerListener(modelListener);
		}
	}

	@Test
	public void testCreate() throws Exception {
		long pk = ServiceTestUtil.nextLong();

		SCFrameworkVersion scFrameworkVersion = _persistence.create(pk);

		Assert.assertNotNull(scFrameworkVersion);

		Assert.assertEquals(scFrameworkVersion.getPrimaryKey(), pk);
	}

	@Test
	public void testRemove() throws Exception {
		SCFrameworkVersion newSCFrameworkVersion = addSCFrameworkVersion();

		_persistence.remove(newSCFrameworkVersion);

		SCFrameworkVersion existingSCFrameworkVersion = _persistence.fetchByPrimaryKey(newSCFrameworkVersion.getPrimaryKey());

		Assert.assertNull(existingSCFrameworkVersion);
	}

	@Test
	public void testUpdateNew() throws Exception {
		addSCFrameworkVersion();
	}

	@Test
	public void testUpdateExisting() throws Exception {
		long pk = ServiceTestUtil.nextLong();

		SCFrameworkVersion newSCFrameworkVersion = _persistence.create(pk);

		newSCFrameworkVersion.setGroupId(ServiceTestUtil.nextLong());

		newSCFrameworkVersion.setCompanyId(ServiceTestUtil.nextLong());

		newSCFrameworkVersion.setUserId(ServiceTestUtil.nextLong());

		newSCFrameworkVersion.setUserName(ServiceTestUtil.randomString());

		newSCFrameworkVersion.setCreateDate(ServiceTestUtil.nextDate());

		newSCFrameworkVersion.setModifiedDate(ServiceTestUtil.nextDate());

		newSCFrameworkVersion.setName(ServiceTestUtil.randomString());

		newSCFrameworkVersion.setUrl(ServiceTestUtil.randomString());

		newSCFrameworkVersion.setActive(ServiceTestUtil.randomBoolean());

		newSCFrameworkVersion.setPriority(ServiceTestUtil.nextInt());

		_persistence.update(newSCFrameworkVersion);

		SCFrameworkVersion existingSCFrameworkVersion = _persistence.findByPrimaryKey(newSCFrameworkVersion.getPrimaryKey());

		Assert.assertEquals(existingSCFrameworkVersion.getFrameworkVersionId(),
			newSCFrameworkVersion.getFrameworkVersionId());
		Assert.assertEquals(existingSCFrameworkVersion.getGroupId(),
			newSCFrameworkVersion.getGroupId());
		Assert.assertEquals(existingSCFrameworkVersion.getCompanyId(),
			newSCFrameworkVersion.getCompanyId());
		Assert.assertEquals(existingSCFrameworkVersion.getUserId(),
			newSCFrameworkVersion.getUserId());
		Assert.assertEquals(existingSCFrameworkVersion.getUserName(),
			newSCFrameworkVersion.getUserName());
		Assert.assertEquals(Time.getShortTimestamp(
				existingSCFrameworkVersion.getCreateDate()),
			Time.getShortTimestamp(newSCFrameworkVersion.getCreateDate()));
		Assert.assertEquals(Time.getShortTimestamp(
				existingSCFrameworkVersion.getModifiedDate()),
			Time.getShortTimestamp(newSCFrameworkVersion.getModifiedDate()));
		Assert.assertEquals(existingSCFrameworkVersion.getName(),
			newSCFrameworkVersion.getName());
		Assert.assertEquals(existingSCFrameworkVersion.getUrl(),
			newSCFrameworkVersion.getUrl());
		Assert.assertEquals(existingSCFrameworkVersion.getActive(),
			newSCFrameworkVersion.getActive());
		Assert.assertEquals(existingSCFrameworkVersion.getPriority(),
			newSCFrameworkVersion.getPriority());
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
	public void testCountByG_A() {
		try {
			_persistence.countByG_A(ServiceTestUtil.nextLong(),
				ServiceTestUtil.randomBoolean());

			_persistence.countByG_A(0L, ServiceTestUtil.randomBoolean());
		}
		catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void testFindByPrimaryKeyExisting() throws Exception {
		SCFrameworkVersion newSCFrameworkVersion = addSCFrameworkVersion();

		SCFrameworkVersion existingSCFrameworkVersion = _persistence.findByPrimaryKey(newSCFrameworkVersion.getPrimaryKey());

		Assert.assertEquals(existingSCFrameworkVersion, newSCFrameworkVersion);
	}

	@Test
	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = ServiceTestUtil.nextLong();

		try {
			_persistence.findByPrimaryKey(pk);

			Assert.fail(
				"Missing entity did not throw NoSuchFrameworkVersionException");
		}
		catch (NoSuchFrameworkVersionException nsee) {
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

	@Test
	public void testFilterFindByGroupId() throws Exception {
		try {
			_persistence.filterFindByGroupId(0, QueryUtil.ALL_POS,
				QueryUtil.ALL_POS, getOrderByComparator());
		}
		catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	protected OrderByComparator getOrderByComparator() {
		return OrderByComparatorFactoryUtil.create("SCFrameworkVersion",
			"frameworkVersionId", true, "groupId", true, "companyId", true,
			"userId", true, "userName", true, "createDate", true,
			"modifiedDate", true, "name", true, "url", true, "active", true,
			"priority", true);
	}

	@Test
	public void testFetchByPrimaryKeyExisting() throws Exception {
		SCFrameworkVersion newSCFrameworkVersion = addSCFrameworkVersion();

		SCFrameworkVersion existingSCFrameworkVersion = _persistence.fetchByPrimaryKey(newSCFrameworkVersion.getPrimaryKey());

		Assert.assertEquals(existingSCFrameworkVersion, newSCFrameworkVersion);
	}

	@Test
	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = ServiceTestUtil.nextLong();

		SCFrameworkVersion missingSCFrameworkVersion = _persistence.fetchByPrimaryKey(pk);

		Assert.assertNull(missingSCFrameworkVersion);
	}

	@Test
	public void testActionableDynamicQuery() throws Exception {
		final IntegerWrapper count = new IntegerWrapper();

		ActionableDynamicQuery actionableDynamicQuery = new SCFrameworkVersionActionableDynamicQuery() {
				@Override
				protected void performAction(Object object) {
					SCFrameworkVersion scFrameworkVersion = (SCFrameworkVersion)object;

					Assert.assertNotNull(scFrameworkVersion);

					count.increment();
				}
			};

		actionableDynamicQuery.performActions();

		Assert.assertEquals(count.getValue(), _persistence.countAll());
	}

	@Test
	public void testDynamicQueryByPrimaryKeyExisting()
		throws Exception {
		SCFrameworkVersion newSCFrameworkVersion = addSCFrameworkVersion();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(SCFrameworkVersion.class,
				SCFrameworkVersion.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("frameworkVersionId",
				newSCFrameworkVersion.getFrameworkVersionId()));

		List<SCFrameworkVersion> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		SCFrameworkVersion existingSCFrameworkVersion = result.get(0);

		Assert.assertEquals(existingSCFrameworkVersion, newSCFrameworkVersion);
	}

	@Test
	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(SCFrameworkVersion.class,
				SCFrameworkVersion.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("frameworkVersionId",
				ServiceTestUtil.nextLong()));

		List<SCFrameworkVersion> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testDynamicQueryByProjectionExisting()
		throws Exception {
		SCFrameworkVersion newSCFrameworkVersion = addSCFrameworkVersion();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(SCFrameworkVersion.class,
				SCFrameworkVersion.class.getClassLoader());

		dynamicQuery.setProjection(ProjectionFactoryUtil.property(
				"frameworkVersionId"));

		Object newFrameworkVersionId = newSCFrameworkVersion.getFrameworkVersionId();

		dynamicQuery.add(RestrictionsFactoryUtil.in("frameworkVersionId",
				new Object[] { newFrameworkVersionId }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		Object existingFrameworkVersionId = result.get(0);

		Assert.assertEquals(existingFrameworkVersionId, newFrameworkVersionId);
	}

	@Test
	public void testDynamicQueryByProjectionMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(SCFrameworkVersion.class,
				SCFrameworkVersion.class.getClassLoader());

		dynamicQuery.setProjection(ProjectionFactoryUtil.property(
				"frameworkVersionId"));

		dynamicQuery.add(RestrictionsFactoryUtil.in("frameworkVersionId",
				new Object[] { ServiceTestUtil.nextLong() }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	protected SCFrameworkVersion addSCFrameworkVersion()
		throws Exception {
		long pk = ServiceTestUtil.nextLong();

		SCFrameworkVersion scFrameworkVersion = _persistence.create(pk);

		scFrameworkVersion.setGroupId(ServiceTestUtil.nextLong());

		scFrameworkVersion.setCompanyId(ServiceTestUtil.nextLong());

		scFrameworkVersion.setUserId(ServiceTestUtil.nextLong());

		scFrameworkVersion.setUserName(ServiceTestUtil.randomString());

		scFrameworkVersion.setCreateDate(ServiceTestUtil.nextDate());

		scFrameworkVersion.setModifiedDate(ServiceTestUtil.nextDate());

		scFrameworkVersion.setName(ServiceTestUtil.randomString());

		scFrameworkVersion.setUrl(ServiceTestUtil.randomString());

		scFrameworkVersion.setActive(ServiceTestUtil.randomBoolean());

		scFrameworkVersion.setPriority(ServiceTestUtil.nextInt());

		_persistence.update(scFrameworkVersion);

		return scFrameworkVersion;
	}

	private static Log _log = LogFactoryUtil.getLog(SCFrameworkVersionPersistenceTest.class);
	private ModelListener<SCFrameworkVersion>[] _modelListeners;
	private SCFrameworkVersionPersistence _persistence = (SCFrameworkVersionPersistence)PortalBeanLocatorUtil.locate(SCFrameworkVersionPersistence.class.getName());
	private TransactionalPersistenceAdvice _transactionalPersistenceAdvice = (TransactionalPersistenceAdvice)PortalBeanLocatorUtil.locate(TransactionalPersistenceAdvice.class.getName());
}