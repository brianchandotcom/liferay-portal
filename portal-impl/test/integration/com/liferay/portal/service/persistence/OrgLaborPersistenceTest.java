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
import com.liferay.portal.model.ModelListener;
import com.liferay.portal.model.OrgLabor;
import com.liferay.portal.service.OrgLaborLocalServiceUtil;
import com.liferay.portal.service.persistence.BasePersistence;
import com.liferay.portal.service.persistence.PersistenceExecutionTestListener;
import com.liferay.portal.test.LiferayPersistenceIntegrationJUnitTestRunner;
import com.liferay.portal.test.persistence.test.TransactionalPersistenceAdvice;
import com.liferay.portal.util.test.RandomTestUtil;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import org.junit.runner.RunWith;

import java.io.Serializable;

import java.util.HashSet;
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
	@Before
	public void setUp() {
		_modelListeners = _persistence.getListeners();

		for (ModelListener<OrgLabor> modelListener : _modelListeners) {
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

		for (ModelListener<OrgLabor> modelListener : _modelListeners) {
			_persistence.registerListener(modelListener);
		}
	}

	@Test
	public void testCreate() throws Exception {
		long pk = RandomTestUtil.nextLong();

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
		long pk = RandomTestUtil.nextLong();

		OrgLabor newOrgLabor = _persistence.create(pk);

		newOrgLabor.setMvccVersion(RandomTestUtil.nextLong());

		newOrgLabor.setOrganizationId(RandomTestUtil.nextLong());

		newOrgLabor.setTypeId(RandomTestUtil.nextInt());

		newOrgLabor.setSunOpen(RandomTestUtil.nextInt());

		newOrgLabor.setSunClose(RandomTestUtil.nextInt());

		newOrgLabor.setMonOpen(RandomTestUtil.nextInt());

		newOrgLabor.setMonClose(RandomTestUtil.nextInt());

		newOrgLabor.setTueOpen(RandomTestUtil.nextInt());

		newOrgLabor.setTueClose(RandomTestUtil.nextInt());

		newOrgLabor.setWedOpen(RandomTestUtil.nextInt());

		newOrgLabor.setWedClose(RandomTestUtil.nextInt());

		newOrgLabor.setThuOpen(RandomTestUtil.nextInt());

		newOrgLabor.setThuClose(RandomTestUtil.nextInt());

		newOrgLabor.setFriOpen(RandomTestUtil.nextInt());

		newOrgLabor.setFriClose(RandomTestUtil.nextInt());

		newOrgLabor.setSatOpen(RandomTestUtil.nextInt());

		newOrgLabor.setSatClose(RandomTestUtil.nextInt());

		_persistence.update(newOrgLabor);

		OrgLabor existingOrgLabor = _persistence.findByPrimaryKey(newOrgLabor.getPrimaryKey());

		Assert.assertEquals(existingOrgLabor.getMvccVersion(),
			newOrgLabor.getMvccVersion());
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
	}

	@Test
	public void testCountByOrganizationId() {
		try {
			_persistence.countByOrganizationId(RandomTestUtil.nextLong());

			_persistence.countByOrganizationId(0L);
		}
		catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void testFindByPrimaryKeyExisting() throws Exception {
		OrgLabor newOrgLabor = addOrgLabor();

		OrgLabor existingOrgLabor = _persistence.findByPrimaryKey(newOrgLabor.getPrimaryKey());

		Assert.assertEquals(existingOrgLabor, newOrgLabor);
	}

	@Test
	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

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
		return OrderByComparatorFactoryUtil.create("OrgLabor", "mvccVersion",
			true, "orgLaborId", true, "organizationId", true, "typeId", true,
			"sunOpen", true, "sunClose", true, "monOpen", true, "monClose",
			true, "tueOpen", true, "tueClose", true, "wedOpen", true,
			"wedClose", true, "thuOpen", true, "thuClose", true, "friOpen",
			true, "friClose", true, "satOpen", true, "satClose", true);
	}

	@Test
	public void testFetchByPrimaryKeyExisting() throws Exception {
		OrgLabor newOrgLabor = addOrgLabor();

		OrgLabor existingOrgLabor = _persistence.fetchByPrimaryKey(newOrgLabor.getPrimaryKey());

		Assert.assertEquals(existingOrgLabor, newOrgLabor);
	}

	@Test
	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		OrgLabor missingOrgLabor = _persistence.fetchByPrimaryKey(pk);

		Assert.assertNull(missingOrgLabor);
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereAllPrimaryKeysExist()
		throws Exception {
		OrgLabor newOrgLabor1 = addOrgLabor();
		OrgLabor newOrgLabor2 = addOrgLabor();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newOrgLabor1.getPrimaryKey());
		primaryKeys.add(newOrgLabor2.getPrimaryKey());

		Map<Serializable, OrgLabor> orgLabors = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(2, orgLabors.size());
		Assert.assertEquals(newOrgLabor1,
			orgLabors.get(newOrgLabor1.getPrimaryKey()));
		Assert.assertEquals(newOrgLabor2,
			orgLabors.get(newOrgLabor2.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereNoPrimaryKeysExist()
		throws Exception {
		long pk1 = RandomTestUtil.nextLong();

		long pk2 = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(pk1);
		primaryKeys.add(pk2);

		Map<Serializable, OrgLabor> orgLabors = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(orgLabors.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereSomePrimaryKeysExist()
		throws Exception {
		OrgLabor newOrgLabor = addOrgLabor();

		long pk = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newOrgLabor.getPrimaryKey());
		primaryKeys.add(pk);

		Map<Serializable, OrgLabor> orgLabors = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, orgLabors.size());
		Assert.assertEquals(newOrgLabor,
			orgLabors.get(newOrgLabor.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithNoPrimaryKeys()
		throws Exception {
		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		Map<Serializable, OrgLabor> orgLabors = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(orgLabors.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithOnePrimaryKey()
		throws Exception {
		OrgLabor newOrgLabor = addOrgLabor();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newOrgLabor.getPrimaryKey());

		Map<Serializable, OrgLabor> orgLabors = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, orgLabors.size());
		Assert.assertEquals(newOrgLabor,
			orgLabors.get(newOrgLabor.getPrimaryKey()));
	}

	@Test
	public void testActionableDynamicQuery() throws Exception {
		final IntegerWrapper count = new IntegerWrapper();

		ActionableDynamicQuery actionableDynamicQuery = OrgLaborLocalServiceUtil.getActionableDynamicQuery();

		actionableDynamicQuery.setPerformActionMethod(new ActionableDynamicQuery.PerformActionMethod() {
				@Override
				public void performAction(Object object) {
					OrgLabor orgLabor = (OrgLabor)object;

					Assert.assertNotNull(orgLabor);

					count.increment();
				}
			});

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
				RandomTestUtil.nextLong()));

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
				new Object[] { RandomTestUtil.nextLong() }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	protected OrgLabor addOrgLabor() throws Exception {
		long pk = RandomTestUtil.nextLong();

		OrgLabor orgLabor = _persistence.create(pk);

		orgLabor.setMvccVersion(RandomTestUtil.nextLong());

		orgLabor.setOrganizationId(RandomTestUtil.nextLong());

		orgLabor.setTypeId(RandomTestUtil.nextInt());

		orgLabor.setSunOpen(RandomTestUtil.nextInt());

		orgLabor.setSunClose(RandomTestUtil.nextInt());

		orgLabor.setMonOpen(RandomTestUtil.nextInt());

		orgLabor.setMonClose(RandomTestUtil.nextInt());

		orgLabor.setTueOpen(RandomTestUtil.nextInt());

		orgLabor.setTueClose(RandomTestUtil.nextInt());

		orgLabor.setWedOpen(RandomTestUtil.nextInt());

		orgLabor.setWedClose(RandomTestUtil.nextInt());

		orgLabor.setThuOpen(RandomTestUtil.nextInt());

		orgLabor.setThuClose(RandomTestUtil.nextInt());

		orgLabor.setFriOpen(RandomTestUtil.nextInt());

		orgLabor.setFriClose(RandomTestUtil.nextInt());

		orgLabor.setSatOpen(RandomTestUtil.nextInt());

		orgLabor.setSatClose(RandomTestUtil.nextInt());

		_persistence.update(orgLabor);

		return orgLabor;
	}

	private static Log _log = LogFactoryUtil.getLog(OrgLaborPersistenceTest.class);
	private ModelListener<OrgLabor>[] _modelListeners;
	private OrgLaborPersistence _persistence = (OrgLaborPersistence)PortalBeanLocatorUtil.locate(OrgLaborPersistence.class.getName());
	private TransactionalPersistenceAdvice _transactionalPersistenceAdvice = (TransactionalPersistenceAdvice)PortalBeanLocatorUtil.locate(TransactionalPersistenceAdvice.class.getName());
}