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
import com.liferay.portal.model.ModelListener;
import com.liferay.portal.service.ServiceTestUtil;
import com.liferay.portal.service.persistence.BasePersistence;
import com.liferay.portal.service.persistence.PersistenceExecutionTestListener;
import com.liferay.portal.test.LiferayPersistenceIntegrationJUnitTestRunner;
import com.liferay.portal.test.persistence.TransactionalPersistenceAdvice;
import com.liferay.portal.util.PropsValues;

import com.liferay.portlet.dynamicdatamapping.NoSuchStructureLinkException;
import com.liferay.portlet.dynamicdatamapping.model.DDMStructureLink;
import com.liferay.portlet.dynamicdatamapping.model.impl.DDMStructureLinkModelImpl;

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
public class DDMStructureLinkPersistenceTest {
	@Before
	public void setUp() {
		_modelListeners = _persistence.getListeners();

		for (ModelListener<DDMStructureLink> modelListener : _modelListeners) {
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

		for (ModelListener<DDMStructureLink> modelListener : _modelListeners) {
			_persistence.registerListener(modelListener);
		}
	}

	@Test
	public void testCreate() throws Exception {
		long pk = ServiceTestUtil.nextLong();

		DDMStructureLink ddmStructureLink = _persistence.create(pk);

		Assert.assertNotNull(ddmStructureLink);

		Assert.assertEquals(ddmStructureLink.getPrimaryKey(), pk);
	}

	@Test
	public void testRemove() throws Exception {
		DDMStructureLink newDDMStructureLink = addDDMStructureLink();

		_persistence.remove(newDDMStructureLink);

		DDMStructureLink existingDDMStructureLink = _persistence.fetchByPrimaryKey(newDDMStructureLink.getPrimaryKey());

		Assert.assertNull(existingDDMStructureLink);
	}

	@Test
	public void testUpdateNew() throws Exception {
		addDDMStructureLink();
	}

	@Test
	public void testUpdateExisting() throws Exception {
		long pk = ServiceTestUtil.nextLong();

		DDMStructureLink newDDMStructureLink = _persistence.create(pk);

		newDDMStructureLink.setClassNameId(ServiceTestUtil.nextLong());

		newDDMStructureLink.setClassPK(ServiceTestUtil.nextLong());

		newDDMStructureLink.setStructureId(ServiceTestUtil.nextLong());

		_persistence.update(newDDMStructureLink);

		DDMStructureLink existingDDMStructureLink = _persistence.findByPrimaryKey(newDDMStructureLink.getPrimaryKey());

		Assert.assertEquals(existingDDMStructureLink.getStructureLinkId(),
			newDDMStructureLink.getStructureLinkId());
		Assert.assertEquals(existingDDMStructureLink.getClassNameId(),
			newDDMStructureLink.getClassNameId());
		Assert.assertEquals(existingDDMStructureLink.getClassPK(),
			newDDMStructureLink.getClassPK());
		Assert.assertEquals(existingDDMStructureLink.getStructureId(),
			newDDMStructureLink.getStructureId());
	}

	@Test
	public void testCountByClassNameId() {
		try {
			_persistence.countByClassNameId(ServiceTestUtil.nextLong());

			_persistence.countByClassNameId(0L);
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
		DDMStructureLink newDDMStructureLink = addDDMStructureLink();

		DDMStructureLink existingDDMStructureLink = _persistence.findByPrimaryKey(newDDMStructureLink.getPrimaryKey());

		Assert.assertEquals(existingDDMStructureLink, newDDMStructureLink);
	}

	@Test
	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = ServiceTestUtil.nextLong();

		try {
			_persistence.findByPrimaryKey(pk);

			Assert.fail(
				"Missing entity did not throw NoSuchStructureLinkException");
		}
		catch (NoSuchStructureLinkException nsee) {
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
		return OrderByComparatorFactoryUtil.create("DDMStructureLink",
			"structureLinkId", true, "classNameId", true, "classPK", true,
			"structureId", true);
	}

	@Test
	public void testFetchByPrimaryKeyExisting() throws Exception {
		DDMStructureLink newDDMStructureLink = addDDMStructureLink();

		DDMStructureLink existingDDMStructureLink = _persistence.fetchByPrimaryKey(newDDMStructureLink.getPrimaryKey());

		Assert.assertEquals(existingDDMStructureLink, newDDMStructureLink);
	}

	@Test
	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = ServiceTestUtil.nextLong();

		DDMStructureLink missingDDMStructureLink = _persistence.fetchByPrimaryKey(pk);

		Assert.assertNull(missingDDMStructureLink);
	}

	@Test
	public void testActionableDynamicQuery() throws Exception {
		final IntegerWrapper count = new IntegerWrapper();

		ActionableDynamicQuery actionableDynamicQuery = new DDMStructureLinkActionableDynamicQuery() {
				@Override
				protected void performAction(Object object) {
					DDMStructureLink ddmStructureLink = (DDMStructureLink)object;

					Assert.assertNotNull(ddmStructureLink);

					count.increment();
				}
			};

		actionableDynamicQuery.performActions();

		Assert.assertEquals(count.getValue(), _persistence.countAll());
	}

	@Test
	public void testDynamicQueryByPrimaryKeyExisting()
		throws Exception {
		DDMStructureLink newDDMStructureLink = addDDMStructureLink();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(DDMStructureLink.class,
				DDMStructureLink.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("structureLinkId",
				newDDMStructureLink.getStructureLinkId()));

		List<DDMStructureLink> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		DDMStructureLink existingDDMStructureLink = result.get(0);

		Assert.assertEquals(existingDDMStructureLink, newDDMStructureLink);
	}

	@Test
	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(DDMStructureLink.class,
				DDMStructureLink.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("structureLinkId",
				ServiceTestUtil.nextLong()));

		List<DDMStructureLink> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testDynamicQueryByProjectionExisting()
		throws Exception {
		DDMStructureLink newDDMStructureLink = addDDMStructureLink();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(DDMStructureLink.class,
				DDMStructureLink.class.getClassLoader());

		dynamicQuery.setProjection(ProjectionFactoryUtil.property(
				"structureLinkId"));

		Object newStructureLinkId = newDDMStructureLink.getStructureLinkId();

		dynamicQuery.add(RestrictionsFactoryUtil.in("structureLinkId",
				new Object[] { newStructureLinkId }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		Object existingStructureLinkId = result.get(0);

		Assert.assertEquals(existingStructureLinkId, newStructureLinkId);
	}

	@Test
	public void testDynamicQueryByProjectionMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(DDMStructureLink.class,
				DDMStructureLink.class.getClassLoader());

		dynamicQuery.setProjection(ProjectionFactoryUtil.property(
				"structureLinkId"));

		dynamicQuery.add(RestrictionsFactoryUtil.in("structureLinkId",
				new Object[] { ServiceTestUtil.nextLong() }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testResetOriginalValues() throws Exception {
		if (!PropsValues.HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE) {
			return;
		}

		DDMStructureLink newDDMStructureLink = addDDMStructureLink();

		_persistence.clearCache();

		DDMStructureLinkModelImpl existingDDMStructureLinkModelImpl = (DDMStructureLinkModelImpl)_persistence.findByPrimaryKey(newDDMStructureLink.getPrimaryKey());

		Assert.assertEquals(existingDDMStructureLinkModelImpl.getClassPK(),
			existingDDMStructureLinkModelImpl.getOriginalClassPK());
	}

	protected DDMStructureLink addDDMStructureLink() throws Exception {
		long pk = ServiceTestUtil.nextLong();

		DDMStructureLink ddmStructureLink = _persistence.create(pk);

		ddmStructureLink.setClassNameId(ServiceTestUtil.nextLong());

		ddmStructureLink.setClassPK(ServiceTestUtil.nextLong());

		ddmStructureLink.setStructureId(ServiceTestUtil.nextLong());

		_persistence.update(ddmStructureLink);

		return ddmStructureLink;
	}

	private static Log _log = LogFactoryUtil.getLog(DDMStructureLinkPersistenceTest.class);
	private ModelListener<DDMStructureLink>[] _modelListeners;
	private DDMStructureLinkPersistence _persistence = (DDMStructureLinkPersistence)PortalBeanLocatorUtil.locate(DDMStructureLinkPersistence.class.getName());
	private TransactionalPersistenceAdvice _transactionalPersistenceAdvice = (TransactionalPersistenceAdvice)PortalBeanLocatorUtil.locate(TransactionalPersistenceAdvice.class.getName());
}