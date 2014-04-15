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

package com.liferay.portlet.dynamicdatamapping.service.persistence;

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
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.model.ModelListener;
import com.liferay.portal.service.ServiceTestUtil;
import com.liferay.portal.service.persistence.BasePersistence;
import com.liferay.portal.service.persistence.PersistenceExecutionTestListener;
import com.liferay.portal.test.LiferayPersistenceIntegrationJUnitTestRunner;
import com.liferay.portal.test.persistence.TransactionalPersistenceAdvice;
import com.liferay.portal.util.PropsValues;

import com.liferay.portlet.dynamicdatamapping.NoSuchStorageLinkException;
import com.liferay.portlet.dynamicdatamapping.model.DDMStorageLink;
import com.liferay.portlet.dynamicdatamapping.model.impl.DDMStorageLinkModelImpl;

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
public class DDMStorageLinkPersistenceTest {
	@Before
	public void setUp() {
		_modelListeners = _persistence.getListeners();

		for (ModelListener<DDMStorageLink> modelListener : _modelListeners) {
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

		for (ModelListener<DDMStorageLink> modelListener : _modelListeners) {
			_persistence.registerListener(modelListener);
		}
	}

	@Test
	public void testCreate() throws Exception {
		long pk = ServiceTestUtil.nextLong();

		DDMStorageLink ddmStorageLink = _persistence.create(pk);

		Assert.assertNotNull(ddmStorageLink);

		Assert.assertEquals(ddmStorageLink.getPrimaryKey(), pk);
	}

	@Test
	public void testRemove() throws Exception {
		DDMStorageLink newDDMStorageLink = addDDMStorageLink();

		_persistence.remove(newDDMStorageLink);

		DDMStorageLink existingDDMStorageLink = _persistence.fetchByPrimaryKey(newDDMStorageLink.getPrimaryKey());

		Assert.assertNull(existingDDMStorageLink);
	}

	@Test
	public void testUpdateNew() throws Exception {
		addDDMStorageLink();
	}

	@Test
	public void testUpdateExisting() throws Exception {
		long pk = ServiceTestUtil.nextLong();

		DDMStorageLink newDDMStorageLink = _persistence.create(pk);

		newDDMStorageLink.setUuid(ServiceTestUtil.randomString());

		newDDMStorageLink.setClassNameId(ServiceTestUtil.nextLong());

		newDDMStorageLink.setClassPK(ServiceTestUtil.nextLong());

		newDDMStorageLink.setStructureId(ServiceTestUtil.nextLong());

		_persistence.update(newDDMStorageLink);

		DDMStorageLink existingDDMStorageLink = _persistence.findByPrimaryKey(newDDMStorageLink.getPrimaryKey());

		Assert.assertEquals(existingDDMStorageLink.getUuid(),
			newDDMStorageLink.getUuid());
		Assert.assertEquals(existingDDMStorageLink.getStorageLinkId(),
			newDDMStorageLink.getStorageLinkId());
		Assert.assertEquals(existingDDMStorageLink.getClassNameId(),
			newDDMStorageLink.getClassNameId());
		Assert.assertEquals(existingDDMStorageLink.getClassPK(),
			newDDMStorageLink.getClassPK());
		Assert.assertEquals(existingDDMStorageLink.getStructureId(),
			newDDMStorageLink.getStructureId());
	}

	@Test
	public void testCountByUuid() {
		try {
			_persistence.countByUuid(StringPool.BLANK);

			_persistence.countByUuid(StringPool.NULL);

			_persistence.countByUuid((String)null);
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
	public void testCountByStructureId() {
		try {
			_persistence.countByStructureId(ServiceTestUtil.nextLong());

			_persistence.countByStructureId(0L);
		}
		catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void testFindByPrimaryKeyExisting() throws Exception {
		DDMStorageLink newDDMStorageLink = addDDMStorageLink();

		DDMStorageLink existingDDMStorageLink = _persistence.findByPrimaryKey(newDDMStorageLink.getPrimaryKey());

		Assert.assertEquals(existingDDMStorageLink, newDDMStorageLink);
	}

	@Test
	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = ServiceTestUtil.nextLong();

		try {
			_persistence.findByPrimaryKey(pk);

			Assert.fail(
				"Missing entity did not throw NoSuchStorageLinkException");
		}
		catch (NoSuchStorageLinkException nsee) {
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
		return OrderByComparatorFactoryUtil.create("DDMStorageLink", "uuid",
			true, "storageLinkId", true, "classNameId", true, "classPK", true,
			"structureId", true);
	}

	@Test
	public void testFetchByPrimaryKeyExisting() throws Exception {
		DDMStorageLink newDDMStorageLink = addDDMStorageLink();

		DDMStorageLink existingDDMStorageLink = _persistence.fetchByPrimaryKey(newDDMStorageLink.getPrimaryKey());

		Assert.assertEquals(existingDDMStorageLink, newDDMStorageLink);
	}

	@Test
	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = ServiceTestUtil.nextLong();

		DDMStorageLink missingDDMStorageLink = _persistence.fetchByPrimaryKey(pk);

		Assert.assertNull(missingDDMStorageLink);
	}

	@Test
	public void testActionableDynamicQuery() throws Exception {
		final IntegerWrapper count = new IntegerWrapper();

		ActionableDynamicQuery actionableDynamicQuery = new DDMStorageLinkActionableDynamicQuery() {
				@Override
				protected void performAction(Object object) {
					DDMStorageLink ddmStorageLink = (DDMStorageLink)object;

					Assert.assertNotNull(ddmStorageLink);

					count.increment();
				}
			};

		actionableDynamicQuery.performActions();

		Assert.assertEquals(count.getValue(), _persistence.countAll());
	}

	@Test
	public void testDynamicQueryByPrimaryKeyExisting()
		throws Exception {
		DDMStorageLink newDDMStorageLink = addDDMStorageLink();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(DDMStorageLink.class,
				DDMStorageLink.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("storageLinkId",
				newDDMStorageLink.getStorageLinkId()));

		List<DDMStorageLink> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		DDMStorageLink existingDDMStorageLink = result.get(0);

		Assert.assertEquals(existingDDMStorageLink, newDDMStorageLink);
	}

	@Test
	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(DDMStorageLink.class,
				DDMStorageLink.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("storageLinkId",
				ServiceTestUtil.nextLong()));

		List<DDMStorageLink> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testDynamicQueryByProjectionExisting()
		throws Exception {
		DDMStorageLink newDDMStorageLink = addDDMStorageLink();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(DDMStorageLink.class,
				DDMStorageLink.class.getClassLoader());

		dynamicQuery.setProjection(ProjectionFactoryUtil.property(
				"storageLinkId"));

		Object newStorageLinkId = newDDMStorageLink.getStorageLinkId();

		dynamicQuery.add(RestrictionsFactoryUtil.in("storageLinkId",
				new Object[] { newStorageLinkId }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		Object existingStorageLinkId = result.get(0);

		Assert.assertEquals(existingStorageLinkId, newStorageLinkId);
	}

	@Test
	public void testDynamicQueryByProjectionMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(DDMStorageLink.class,
				DDMStorageLink.class.getClassLoader());

		dynamicQuery.setProjection(ProjectionFactoryUtil.property(
				"storageLinkId"));

		dynamicQuery.add(RestrictionsFactoryUtil.in("storageLinkId",
				new Object[] { ServiceTestUtil.nextLong() }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testResetOriginalValues() throws Exception {
		if (!PropsValues.HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE) {
			return;
		}

		DDMStorageLink newDDMStorageLink = addDDMStorageLink();

		_persistence.clearCache();

		DDMStorageLinkModelImpl existingDDMStorageLinkModelImpl = (DDMStorageLinkModelImpl)_persistence.findByPrimaryKey(newDDMStorageLink.getPrimaryKey());

		Assert.assertEquals(existingDDMStorageLinkModelImpl.getClassPK(),
			existingDDMStorageLinkModelImpl.getOriginalClassPK());
	}

	protected DDMStorageLink addDDMStorageLink() throws Exception {
		long pk = ServiceTestUtil.nextLong();

		DDMStorageLink ddmStorageLink = _persistence.create(pk);

		ddmStorageLink.setUuid(ServiceTestUtil.randomString());

		ddmStorageLink.setClassNameId(ServiceTestUtil.nextLong());

		ddmStorageLink.setClassPK(ServiceTestUtil.nextLong());

		ddmStorageLink.setStructureId(ServiceTestUtil.nextLong());

		_persistence.update(ddmStorageLink);

		return ddmStorageLink;
	}

	private static Log _log = LogFactoryUtil.getLog(DDMStorageLinkPersistenceTest.class);
	private ModelListener<DDMStorageLink>[] _modelListeners;
	private DDMStorageLinkPersistence _persistence = (DDMStorageLinkPersistence)PortalBeanLocatorUtil.locate(DDMStorageLinkPersistence.class.getName());
	private TransactionalPersistenceAdvice _transactionalPersistenceAdvice = (TransactionalPersistenceAdvice)PortalBeanLocatorUtil.locate(TransactionalPersistenceAdvice.class.getName());
}