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

package com.liferay.portlet.trash.service.persistence;

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
import com.liferay.portal.model.ModelListener;
import com.liferay.portal.service.ServiceTestUtil;
import com.liferay.portal.service.persistence.BasePersistence;
import com.liferay.portal.service.persistence.PersistenceExecutionTestListener;
import com.liferay.portal.test.LiferayPersistenceIntegrationJUnitTestRunner;
import com.liferay.portal.test.persistence.TransactionalPersistenceAdvice;
import com.liferay.portal.util.PropsValues;

import com.liferay.portlet.trash.NoSuchVersionException;
import com.liferay.portlet.trash.model.TrashVersion;
import com.liferay.portlet.trash.model.impl.TrashVersionModelImpl;

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
public class TrashVersionPersistenceTest {
	@Before
	public void setUp() {
		_modelListeners = _persistence.getListeners();

		for (ModelListener<TrashVersion> modelListener : _modelListeners) {
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

		for (ModelListener<TrashVersion> modelListener : _modelListeners) {
			_persistence.registerListener(modelListener);
		}
	}

	@Test
	public void testCreate() throws Exception {
		long pk = ServiceTestUtil.nextLong();

		TrashVersion trashVersion = _persistence.create(pk);

		Assert.assertNotNull(trashVersion);

		Assert.assertEquals(trashVersion.getPrimaryKey(), pk);
	}

	@Test
	public void testRemove() throws Exception {
		TrashVersion newTrashVersion = addTrashVersion();

		_persistence.remove(newTrashVersion);

		TrashVersion existingTrashVersion = _persistence.fetchByPrimaryKey(newTrashVersion.getPrimaryKey());

		Assert.assertNull(existingTrashVersion);
	}

	@Test
	public void testUpdateNew() throws Exception {
		addTrashVersion();
	}

	@Test
	public void testUpdateExisting() throws Exception {
		long pk = ServiceTestUtil.nextLong();

		TrashVersion newTrashVersion = _persistence.create(pk);

		newTrashVersion.setEntryId(ServiceTestUtil.nextLong());

		newTrashVersion.setClassNameId(ServiceTestUtil.nextLong());

		newTrashVersion.setClassPK(ServiceTestUtil.nextLong());

		newTrashVersion.setTypeSettings(ServiceTestUtil.randomString());

		newTrashVersion.setStatus(ServiceTestUtil.nextInt());

		_persistence.update(newTrashVersion);

		TrashVersion existingTrashVersion = _persistence.findByPrimaryKey(newTrashVersion.getPrimaryKey());

		Assert.assertEquals(existingTrashVersion.getVersionId(),
			newTrashVersion.getVersionId());
		Assert.assertEquals(existingTrashVersion.getEntryId(),
			newTrashVersion.getEntryId());
		Assert.assertEquals(existingTrashVersion.getClassNameId(),
			newTrashVersion.getClassNameId());
		Assert.assertEquals(existingTrashVersion.getClassPK(),
			newTrashVersion.getClassPK());
		Assert.assertEquals(existingTrashVersion.getTypeSettings(),
			newTrashVersion.getTypeSettings());
		Assert.assertEquals(existingTrashVersion.getStatus(),
			newTrashVersion.getStatus());
	}

	@Test
	public void testCountByEntryId() {
		try {
			_persistence.countByEntryId(ServiceTestUtil.nextLong());

			_persistence.countByEntryId(0L);
		}
		catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void testCountByE_C() {
		try {
			_persistence.countByE_C(ServiceTestUtil.nextLong(),
				ServiceTestUtil.nextLong());

			_persistence.countByE_C(0L, 0L);
		}
		catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void testCountByC_C() {
		try {
			_persistence.countByC_C(ServiceTestUtil.nextLong(),
				ServiceTestUtil.nextLong());

			_persistence.countByC_C(0L, 0L);
		}
		catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void testCountByE_C_C() {
		try {
			_persistence.countByE_C_C(ServiceTestUtil.nextLong(),
				ServiceTestUtil.nextLong(), ServiceTestUtil.nextLong());

			_persistence.countByE_C_C(0L, 0L, 0L);
		}
		catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void testFindByPrimaryKeyExisting() throws Exception {
		TrashVersion newTrashVersion = addTrashVersion();

		TrashVersion existingTrashVersion = _persistence.findByPrimaryKey(newTrashVersion.getPrimaryKey());

		Assert.assertEquals(existingTrashVersion, newTrashVersion);
	}

	@Test
	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = ServiceTestUtil.nextLong();

		try {
			_persistence.findByPrimaryKey(pk);

			Assert.fail("Missing entity did not throw NoSuchVersionException");
		}
		catch (NoSuchVersionException nsee) {
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
		return OrderByComparatorFactoryUtil.create("TrashVersion", "versionId",
			true, "entryId", true, "classNameId", true, "classPK", true,
			"typeSettings", true, "status", true);
	}

	@Test
	public void testFetchByPrimaryKeyExisting() throws Exception {
		TrashVersion newTrashVersion = addTrashVersion();

		TrashVersion existingTrashVersion = _persistence.fetchByPrimaryKey(newTrashVersion.getPrimaryKey());

		Assert.assertEquals(existingTrashVersion, newTrashVersion);
	}

	@Test
	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = ServiceTestUtil.nextLong();

		TrashVersion missingTrashVersion = _persistence.fetchByPrimaryKey(pk);

		Assert.assertNull(missingTrashVersion);
	}

	@Test
	public void testActionableDynamicQuery() throws Exception {
		final IntegerWrapper count = new IntegerWrapper();

		ActionableDynamicQuery actionableDynamicQuery = new TrashVersionActionableDynamicQuery() {
				@Override
				protected void performAction(Object object) {
					TrashVersion trashVersion = (TrashVersion)object;

					Assert.assertNotNull(trashVersion);

					count.increment();
				}
			};

		actionableDynamicQuery.performActions();

		Assert.assertEquals(count.getValue(), _persistence.countAll());
	}

	@Test
	public void testDynamicQueryByPrimaryKeyExisting()
		throws Exception {
		TrashVersion newTrashVersion = addTrashVersion();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(TrashVersion.class,
				TrashVersion.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("versionId",
				newTrashVersion.getVersionId()));

		List<TrashVersion> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		TrashVersion existingTrashVersion = result.get(0);

		Assert.assertEquals(existingTrashVersion, newTrashVersion);
	}

	@Test
	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(TrashVersion.class,
				TrashVersion.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("versionId",
				ServiceTestUtil.nextLong()));

		List<TrashVersion> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testDynamicQueryByProjectionExisting()
		throws Exception {
		TrashVersion newTrashVersion = addTrashVersion();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(TrashVersion.class,
				TrashVersion.class.getClassLoader());

		dynamicQuery.setProjection(ProjectionFactoryUtil.property("versionId"));

		Object newVersionId = newTrashVersion.getVersionId();

		dynamicQuery.add(RestrictionsFactoryUtil.in("versionId",
				new Object[] { newVersionId }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		Object existingVersionId = result.get(0);

		Assert.assertEquals(existingVersionId, newVersionId);
	}

	@Test
	public void testDynamicQueryByProjectionMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(TrashVersion.class,
				TrashVersion.class.getClassLoader());

		dynamicQuery.setProjection(ProjectionFactoryUtil.property("versionId"));

		dynamicQuery.add(RestrictionsFactoryUtil.in("versionId",
				new Object[] { ServiceTestUtil.nextLong() }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testResetOriginalValues() throws Exception {
		if (!PropsValues.HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE) {
			return;
		}

		TrashVersion newTrashVersion = addTrashVersion();

		_persistence.clearCache();

		TrashVersionModelImpl existingTrashVersionModelImpl = (TrashVersionModelImpl)_persistence.findByPrimaryKey(newTrashVersion.getPrimaryKey());

		Assert.assertEquals(existingTrashVersionModelImpl.getClassNameId(),
			existingTrashVersionModelImpl.getOriginalClassNameId());
		Assert.assertEquals(existingTrashVersionModelImpl.getClassPK(),
			existingTrashVersionModelImpl.getOriginalClassPK());

		Assert.assertEquals(existingTrashVersionModelImpl.getEntryId(),
			existingTrashVersionModelImpl.getOriginalEntryId());
		Assert.assertEquals(existingTrashVersionModelImpl.getClassNameId(),
			existingTrashVersionModelImpl.getOriginalClassNameId());
		Assert.assertEquals(existingTrashVersionModelImpl.getClassPK(),
			existingTrashVersionModelImpl.getOriginalClassPK());
	}

	protected TrashVersion addTrashVersion() throws Exception {
		long pk = ServiceTestUtil.nextLong();

		TrashVersion trashVersion = _persistence.create(pk);

		trashVersion.setEntryId(ServiceTestUtil.nextLong());

		trashVersion.setClassNameId(ServiceTestUtil.nextLong());

		trashVersion.setClassPK(ServiceTestUtil.nextLong());

		trashVersion.setTypeSettings(ServiceTestUtil.randomString());

		trashVersion.setStatus(ServiceTestUtil.nextInt());

		_persistence.update(trashVersion);

		return trashVersion;
	}

	private static Log _log = LogFactoryUtil.getLog(TrashVersionPersistenceTest.class);
	private ModelListener<TrashVersion>[] _modelListeners;
	private TrashVersionPersistence _persistence = (TrashVersionPersistence)PortalBeanLocatorUtil.locate(TrashVersionPersistence.class.getName());
	private TransactionalPersistenceAdvice _transactionalPersistenceAdvice = (TransactionalPersistenceAdvice)PortalBeanLocatorUtil.locate(TransactionalPersistenceAdvice.class.getName());
}