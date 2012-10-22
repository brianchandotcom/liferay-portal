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

package com.liferay.portlet.dynamicdatamapping.service.persistence;

import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQueryFactoryUtil;
import com.liferay.portal.kernel.dao.orm.ProjectionFactoryUtil;
import com.liferay.portal.kernel.dao.orm.RestrictionsFactoryUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.test.ExecutionTestListeners;
import com.liferay.portal.kernel.util.Time;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.service.ServiceTestUtil;
import com.liferay.portal.service.persistence.BasePersistence;
import com.liferay.portal.service.persistence.PersistenceExecutionTestListener;
import com.liferay.portal.test.LiferayPersistenceIntegrationJUnitTestRunner;
import com.liferay.portal.test.persistence.TransactionalPersistenceAdvice;
import com.liferay.portal.util.PropsValues;

import com.liferay.portlet.dynamicdatamapping.NoSuchStructureException;
import com.liferay.portlet.dynamicdatamapping.model.DDMStructure;
import com.liferay.portlet.dynamicdatamapping.model.impl.DDMStructureModelImpl;

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
public class DDMStructurePersistenceTest {
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

		DDMStructure ddmStructure = _persistence.create(pk);

		Assert.assertNotNull(ddmStructure);

		Assert.assertEquals(ddmStructure.getPrimaryKey(), pk);
	}

	@Test
	public void testRemove() throws Exception {
		DDMStructure newDDMStructure = addDDMStructure();

		_persistence.remove(newDDMStructure);

		DDMStructure existingDDMStructure = _persistence.fetchByPrimaryKey(newDDMStructure.getPrimaryKey());

		Assert.assertNull(existingDDMStructure);
	}

	@Test
	public void testUpdateNew() throws Exception {
		addDDMStructure();
	}

	@Test
	public void testUpdateExisting() throws Exception {
		long pk = ServiceTestUtil.nextLong();

		DDMStructure newDDMStructure = _persistence.create(pk);

		newDDMStructure.setUuid(ServiceTestUtil.randomString());

		newDDMStructure.setGroupId(ServiceTestUtil.nextLong());

		newDDMStructure.setCompanyId(ServiceTestUtil.nextLong());

		newDDMStructure.setUserId(ServiceTestUtil.nextLong());

		newDDMStructure.setUserName(ServiceTestUtil.randomString());

		newDDMStructure.setCreateDate(ServiceTestUtil.nextDate());

		newDDMStructure.setModifiedDate(ServiceTestUtil.nextDate());

		newDDMStructure.setParentStructureId(ServiceTestUtil.nextLong());

		newDDMStructure.setClassNameId(ServiceTestUtil.nextLong());

		newDDMStructure.setStructureKey(ServiceTestUtil.randomString());

		newDDMStructure.setName(ServiceTestUtil.randomString());

		newDDMStructure.setDescription(ServiceTestUtil.randomString());

		newDDMStructure.setXsd(ServiceTestUtil.randomString());

		newDDMStructure.setStorageType(ServiceTestUtil.randomString());

		newDDMStructure.setType(ServiceTestUtil.nextInt());

		_persistence.update(newDDMStructure);

		DDMStructure existingDDMStructure = _persistence.findByPrimaryKey(newDDMStructure.getPrimaryKey());

		Assert.assertEquals(existingDDMStructure.getUuid(),
			newDDMStructure.getUuid());
		Assert.assertEquals(existingDDMStructure.getStructureId(),
			newDDMStructure.getStructureId());
		Assert.assertEquals(existingDDMStructure.getGroupId(),
			newDDMStructure.getGroupId());
		Assert.assertEquals(existingDDMStructure.getCompanyId(),
			newDDMStructure.getCompanyId());
		Assert.assertEquals(existingDDMStructure.getUserId(),
			newDDMStructure.getUserId());
		Assert.assertEquals(existingDDMStructure.getUserName(),
			newDDMStructure.getUserName());
		Assert.assertEquals(Time.getShortTimestamp(
				existingDDMStructure.getCreateDate()),
			Time.getShortTimestamp(newDDMStructure.getCreateDate()));
		Assert.assertEquals(Time.getShortTimestamp(
				existingDDMStructure.getModifiedDate()),
			Time.getShortTimestamp(newDDMStructure.getModifiedDate()));
		Assert.assertEquals(existingDDMStructure.getParentStructureId(),
			newDDMStructure.getParentStructureId());
		Assert.assertEquals(existingDDMStructure.getClassNameId(),
			newDDMStructure.getClassNameId());
		Assert.assertEquals(existingDDMStructure.getStructureKey(),
			newDDMStructure.getStructureKey());
		Assert.assertEquals(existingDDMStructure.getName(),
			newDDMStructure.getName());
		Assert.assertEquals(existingDDMStructure.getDescription(),
			newDDMStructure.getDescription());
		Assert.assertEquals(existingDDMStructure.getXsd(),
			newDDMStructure.getXsd());
		Assert.assertEquals(existingDDMStructure.getStorageType(),
			newDDMStructure.getStorageType());
		Assert.assertEquals(existingDDMStructure.getType(),
			newDDMStructure.getType());
	}

	@Test
	public void testFindByPrimaryKeyExisting() throws Exception {
		DDMStructure newDDMStructure = addDDMStructure();

		DDMStructure existingDDMStructure = _persistence.findByPrimaryKey(newDDMStructure.getPrimaryKey());

		Assert.assertEquals(existingDDMStructure, newDDMStructure);
	}

	@Test
	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = ServiceTestUtil.nextLong();

		try {
			_persistence.findByPrimaryKey(pk);

			Assert.fail("Missing entity did not throw NoSuchStructureException");
		}
		catch (NoSuchStructureException nsee) {
		}
	}

	@Test
	public void testFetchByPrimaryKeyExisting() throws Exception {
		DDMStructure newDDMStructure = addDDMStructure();

		DDMStructure existingDDMStructure = _persistence.fetchByPrimaryKey(newDDMStructure.getPrimaryKey());

		Assert.assertEquals(existingDDMStructure, newDDMStructure);
	}

	@Test
	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = ServiceTestUtil.nextLong();

		DDMStructure missingDDMStructure = _persistence.fetchByPrimaryKey(pk);

		Assert.assertNull(missingDDMStructure);
	}

	@Test
	public void testDynamicQueryByPrimaryKeyExisting()
		throws Exception {
		DDMStructure newDDMStructure = addDDMStructure();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(DDMStructure.class,
				DDMStructure.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("structureId",
				newDDMStructure.getStructureId()));

		List<DDMStructure> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		DDMStructure existingDDMStructure = result.get(0);

		Assert.assertEquals(existingDDMStructure, newDDMStructure);
	}

	@Test
	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(DDMStructure.class,
				DDMStructure.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("structureId",
				ServiceTestUtil.nextLong()));

		List<DDMStructure> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testDynamicQueryByProjectionExisting()
		throws Exception {
		DDMStructure newDDMStructure = addDDMStructure();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(DDMStructure.class,
				DDMStructure.class.getClassLoader());

		dynamicQuery.setProjection(ProjectionFactoryUtil.property("structureId"));

		Object newStructureId = newDDMStructure.getStructureId();

		dynamicQuery.add(RestrictionsFactoryUtil.in("structureId",
				new Object[] { newStructureId }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		Object existingStructureId = result.get(0);

		Assert.assertEquals(existingStructureId, newStructureId);
	}

	@Test
	public void testDynamicQueryByProjectionMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(DDMStructure.class,
				DDMStructure.class.getClassLoader());

		dynamicQuery.setProjection(ProjectionFactoryUtil.property("structureId"));

		dynamicQuery.add(RestrictionsFactoryUtil.in("structureId",
				new Object[] { ServiceTestUtil.nextLong() }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testResetOriginalValues() throws Exception {
		if (!PropsValues.HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE) {
			return;
		}

		DDMStructure newDDMStructure = addDDMStructure();

		_persistence.clearCache();

		DDMStructureModelImpl existingDDMStructureModelImpl = (DDMStructureModelImpl)_persistence.findByPrimaryKey(newDDMStructure.getPrimaryKey());

		Assert.assertTrue(Validator.equals(
				existingDDMStructureModelImpl.getUuid(),
				existingDDMStructureModelImpl.getOriginalUuid()));
		Assert.assertEquals(existingDDMStructureModelImpl.getGroupId(),
			existingDDMStructureModelImpl.getOriginalGroupId());

		Assert.assertEquals(existingDDMStructureModelImpl.getGroupId(),
			existingDDMStructureModelImpl.getOriginalGroupId());
		Assert.assertTrue(Validator.equals(
				existingDDMStructureModelImpl.getStructureKey(),
				existingDDMStructureModelImpl.getOriginalStructureKey()));
	}

	protected DDMStructure addDDMStructure() throws Exception {
		long pk = ServiceTestUtil.nextLong();

		DDMStructure ddmStructure = _persistence.create(pk);

		ddmStructure.setUuid(ServiceTestUtil.randomString());

		ddmStructure.setGroupId(ServiceTestUtil.nextLong());

		ddmStructure.setCompanyId(ServiceTestUtil.nextLong());

		ddmStructure.setUserId(ServiceTestUtil.nextLong());

		ddmStructure.setUserName(ServiceTestUtil.randomString());

		ddmStructure.setCreateDate(ServiceTestUtil.nextDate());

		ddmStructure.setModifiedDate(ServiceTestUtil.nextDate());

		ddmStructure.setParentStructureId(ServiceTestUtil.nextLong());

		ddmStructure.setClassNameId(ServiceTestUtil.nextLong());

		ddmStructure.setStructureKey(ServiceTestUtil.randomString());

		ddmStructure.setName(ServiceTestUtil.randomString());

		ddmStructure.setDescription(ServiceTestUtil.randomString());

		ddmStructure.setXsd(ServiceTestUtil.randomString());

		ddmStructure.setStorageType(ServiceTestUtil.randomString());

		ddmStructure.setType(ServiceTestUtil.nextInt());

		_persistence.update(ddmStructure);

		return ddmStructure;
	}

	private static Log _log = LogFactoryUtil.getLog(DDMStructurePersistenceTest.class);
	private DDMStructurePersistence _persistence = (DDMStructurePersistence)PortalBeanLocatorUtil.locate(DDMStructurePersistence.class.getName());
	private TransactionalPersistenceAdvice _transactionalPersistenceAdvice = (TransactionalPersistenceAdvice)PortalBeanLocatorUtil.locate(TransactionalPersistenceAdvice.class.getName());
}