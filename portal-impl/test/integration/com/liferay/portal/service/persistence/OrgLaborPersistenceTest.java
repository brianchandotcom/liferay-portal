/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

import com.liferay.portal.NoSuchOrgLaborException;
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
import com.liferay.portal.model.OrgLabor;
import com.liferay.portal.service.ServiceTestUtil;
import com.liferay.portal.service.persistence.BasePersistence;
import com.liferay.portal.service.persistence.PersistenceExecutionTestListener;
import com.liferay.portal.test.LiferayPersistenceIntegrationJUnitTestRunner;
import com.liferay.portal.test.persistence.TransactionalPersistenceAdvice;

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
public class OrgLaborPersistenceTest {
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

		OrgLabor orgLabor = _persistence.create(pk);

		Assert.assertNotNull(orgLabor);

		Assert.assertEquals(orgLabor.getPrimaryKey(), pk);
	}

	@Test
	public void testRemove() throws Exception {
		OrgLabor newOrgLabor = addOrgLabor();

		_persistence.remove(newOrgLabor);

		OrgLabor existingOrgLabor = _persistence.fetchByPrimaryKey(newOrgLabor.getPrimaryKey());

		Assert.assertNull(existingOrgLabor);
	}

	@Test
	public void testUpdateNew() throws Exception {
		addOrgLabor();
	}

	@Test
	public void testUpdateExisting() throws Exception {
		long pk = ServiceTestUtil.nextLong();

		OrgLabor newOrgLabor = _persistence.create(pk);

		newOrgLabor.setOrganizationId(ServiceTestUtil.nextLong());

		newOrgLabor.setTypeId(ServiceTestUtil.nextInt());

		newOrgLabor.setSunOpen(ServiceTestUtil.nextInt());

		newOrgLabor.setSunClose(ServiceTestUtil.nextInt());

		newOrgLabor.setMonOpen(ServiceTestUtil.nextInt());

		newOrgLabor.setMonClose(ServiceTestUtil.nextInt());

		newOrgLabor.setTueOpen(ServiceTestUtil.nextInt());

		newOrgLabor.setTueClose(ServiceTestUtil.nextInt());

		newOrgLabor.setWedOpen(ServiceTestUtil.nextInt());

		newOrgLabor.setWedClose(ServiceTestUtil.nextInt());

		newOrgLabor.setThuOpen(ServiceTestUtil.nextInt());

		newOrgLabor.setThuClose(ServiceTestUtil.nextInt());

		newOrgLabor.setFriOpen(ServiceTestUtil.nextInt());

		newOrgLabor.setFriClose(ServiceTestUtil.nextInt());

		newOrgLabor.setSatOpen(ServiceTestUtil.nextInt());

		newOrgLabor.setSatClose(ServiceTestUtil.nextInt());

		newOrgLabor.setMvccVersion(ServiceTestUtil.nextLong());

		_persistence.update(newOrgLabor);

		OrgLabor existingOrgLabor = _persistence.findByPrimaryKey(newOrgLabor.getPrimaryKey());

		Assert.assertEquals(existingOrgLabor.getOrgLaborId(),
			newOrgLabor.getOrgLaborId());
		Assert.assertEquals(existingOrgLabor.getOrganizationId(),
			newOrgLabor.getOrganizationId());
		Assert.assertEquals(existingOrgLabor.getTypeId(),
			newOrgLabor.getTypeId());
		Assert.assertEquals(existingOrgLabor.getSunOpen(),
			newOrgLabor.getSunOpen());
		Assert.assertEquals(existingOrgLabor.getSunClose(),
			newOrgLabor.getSunClose());
		Assert.assertEquals(existingOrgLabor.getMonOpen(),
			newOrgLabor.getMonOpen());
		Assert.assertEquals(existingOrgLabor.getMonClose(),
			newOrgLabor.getMonClose());
		Assert.assertEquals(existingOrgLabor.getTueOpen(),
			newOrgLabor.getTueOpen());
		Assert.assertEquals(existingOrgLabor.getTueClose(),
			newOrgLabor.getTueClose());
		Assert.assertEquals(existingOrgLabor.getWedOpen(),
			newOrgLabor.getWedOpen());
		Assert.assertEquals(existingOrgLabor.getWedClose(),
			newOrgLabor.getWedClose());
		Assert.assertEquals(existingOrgLabor.getThuOpen(),
			newOrgLabor.getThuOpen());
		Assert.assertEquals(existingOrgLabor.getThuClose(),
			newOrgLabor.getThuClose());
		Assert.assertEquals(existingOrgLabor.getFriOpen(),
			newOrgLabor.getFriOpen());
		Assert.assertEquals(existingOrgLabor.getFriClose(),
			newOrgLabor.getFriClose());
		Assert.assertEquals(existingOrgLabor.getSatOpen(),
			newOrgLabor.getSatOpen());
		Assert.assertEquals(existingOrgLabor.getSatClose(),
			newOrgLabor.getSatClose());
		Assert.assertEquals(existingOrgLabor.getMvccVersion(),
			newOrgLabor.getMvccVersion());
	}

	@Test
	public void testFindByPrimaryKeyExisting() throws Exception {
		OrgLabor newOrgLabor = addOrgLabor();

		OrgLabor existingOrgLabor = _persistence.findByPrimaryKey(newOrgLabor.getPrimaryKey());

		Assert.assertEquals(existingOrgLabor, newOrgLabor);
	}

	@Test
	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = ServiceTestUtil.nextLong();

		try {
			_persistence.findByPrimaryKey(pk);

			Assert.fail("Missing entity did not throw NoSuchOrgLaborException");
		}
		catch (NoSuchOrgLaborException nsee) {
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
		return OrderByComparatorFactoryUtil.create("OrgLabor", "orgLaborId",
			true, "organizationId", true, "typeId", true, "sunOpen", true,
			"sunClose", true, "monOpen", true, "monClose", true, "tueOpen",
			true, "tueClose", true, "wedOpen", true, "wedClose", true,
			"thuOpen", true, "thuClose", true, "friOpen", true, "friClose",
			true, "satOpen", true, "satClose", true, "mvccVersion", true);
	}

	@Test
	public void testFetchByPrimaryKeyExisting() throws Exception {
		OrgLabor newOrgLabor = addOrgLabor();

		OrgLabor existingOrgLabor = _persistence.fetchByPrimaryKey(newOrgLabor.getPrimaryKey());

		Assert.assertEquals(existingOrgLabor, newOrgLabor);
	}

	@Test
	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = ServiceTestUtil.nextLong();

		OrgLabor missingOrgLabor = _persistence.fetchByPrimaryKey(pk);

		Assert.assertNull(missingOrgLabor);
	}

	@Test
	public void testActionableDynamicQuery() throws Exception {
		final IntegerWrapper count = new IntegerWrapper();

		ActionableDynamicQuery actionableDynamicQuery = new OrgLaborActionableDynamicQuery() {
				@Override
				protected void performAction(Object object) {
					OrgLabor orgLabor = (OrgLabor)object;

					Assert.assertNotNull(orgLabor);

					count.increment();
				}
			};

		actionableDynamicQuery.performActions();

		Assert.assertEquals(count.getValue(), _persistence.countAll());
	}

	@Test
	public void testDynamicQueryByPrimaryKeyExisting()
		throws Exception {
		OrgLabor newOrgLabor = addOrgLabor();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(OrgLabor.class,
				OrgLabor.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("orgLaborId",
				newOrgLabor.getOrgLaborId()));

		List<OrgLabor> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		OrgLabor existingOrgLabor = result.get(0);

		Assert.assertEquals(existingOrgLabor, newOrgLabor);
	}

	@Test
	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(OrgLabor.class,
				OrgLabor.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("orgLaborId",
				ServiceTestUtil.nextLong()));

		List<OrgLabor> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testDynamicQueryByProjectionExisting()
		throws Exception {
		OrgLabor newOrgLabor = addOrgLabor();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(OrgLabor.class,
				OrgLabor.class.getClassLoader());

		dynamicQuery.setProjection(ProjectionFactoryUtil.property("orgLaborId"));

		Object newOrgLaborId = newOrgLabor.getOrgLaborId();

		dynamicQuery.add(RestrictionsFactoryUtil.in("orgLaborId",
				new Object[] { newOrgLaborId }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		Object existingOrgLaborId = result.get(0);

		Assert.assertEquals(existingOrgLaborId, newOrgLaborId);
	}

	@Test
	public void testDynamicQueryByProjectionMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(OrgLabor.class,
				OrgLabor.class.getClassLoader());

		dynamicQuery.setProjection(ProjectionFactoryUtil.property("orgLaborId"));

		dynamicQuery.add(RestrictionsFactoryUtil.in("orgLaborId",
				new Object[] { ServiceTestUtil.nextLong() }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	protected OrgLabor addOrgLabor() throws Exception {
		long pk = ServiceTestUtil.nextLong();

		OrgLabor orgLabor = _persistence.create(pk);

		orgLabor.setOrganizationId(ServiceTestUtil.nextLong());

		orgLabor.setTypeId(ServiceTestUtil.nextInt());

		orgLabor.setSunOpen(ServiceTestUtil.nextInt());

		orgLabor.setSunClose(ServiceTestUtil.nextInt());

		orgLabor.setMonOpen(ServiceTestUtil.nextInt());

		orgLabor.setMonClose(ServiceTestUtil.nextInt());

		orgLabor.setTueOpen(ServiceTestUtil.nextInt());

		orgLabor.setTueClose(ServiceTestUtil.nextInt());

		orgLabor.setWedOpen(ServiceTestUtil.nextInt());

		orgLabor.setWedClose(ServiceTestUtil.nextInt());

		orgLabor.setThuOpen(ServiceTestUtil.nextInt());

		orgLabor.setThuClose(ServiceTestUtil.nextInt());

		orgLabor.setFriOpen(ServiceTestUtil.nextInt());

		orgLabor.setFriClose(ServiceTestUtil.nextInt());

		orgLabor.setSatOpen(ServiceTestUtil.nextInt());

		orgLabor.setSatClose(ServiceTestUtil.nextInt());

		orgLabor.setMvccVersion(ServiceTestUtil.nextLong());

		_persistence.update(orgLabor);

		return orgLabor;
	}

	private static Log _log = LogFactoryUtil.getLog(OrgLaborPersistenceTest.class);
	private OrgLaborPersistence _persistence = (OrgLaborPersistence)PortalBeanLocatorUtil.locate(OrgLaborPersistence.class.getName());
	private TransactionalPersistenceAdvice _transactionalPersistenceAdvice = (TransactionalPersistenceAdvice)PortalBeanLocatorUtil.locate(TransactionalPersistenceAdvice.class.getName());
}