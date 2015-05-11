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

package com.liferay.portlet.softwarecatalog.service.persistence.test;

import com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQueryFactoryUtil;
import com.liferay.portal.kernel.dao.orm.ProjectionFactoryUtil;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.RestrictionsFactoryUtil;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.TransactionalTestRule;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.transaction.Propagation;
import com.liferay.portal.kernel.util.IntegerWrapper;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.OrderByComparatorFactoryUtil;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PersistenceTestRule;

import com.liferay.portlet.softwarecatalog.NoSuchLicenseException;
import com.liferay.portlet.softwarecatalog.model.SCLicense;
import com.liferay.portlet.softwarecatalog.service.SCLicenseLocalServiceUtil;
import com.liferay.portlet.softwarecatalog.service.persistence.SCLicensePersistence;
import com.liferay.portlet.softwarecatalog.service.persistence.SCLicenseUtil;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @generated
 */
public class SCLicensePersistenceTest {
	@Rule
	public final AggregateTestRule aggregateTestRule = new AggregateTestRule(new LiferayIntegrationTestRule(),
			PersistenceTestRule.INSTANCE,
			new TransactionalTestRule(Propagation.REQUIRED));

	@Before
	public void setUp() {
		_persistence = SCLicenseUtil.getPersistence();

		Class<?> clazz = _persistence.getClass();

		_dynamicQueryClassLoader = clazz.getClassLoader();
	}

	@After
	public void tearDown() throws Exception {
		Iterator<SCLicense> iterator = _scLicenses.iterator();

		while (iterator.hasNext()) {
			_persistence.remove(iterator.next());

			iterator.remove();
		}
	}

	@Test
	public void testCreate() throws Exception {
		long pk = RandomTestUtil.nextLong();

		SCLicense scLicense = _persistence.create(pk);

		Assert.assertNotNull(scLicense);

		Assert.assertEquals(scLicense.getPrimaryKey(), pk);
	}

	@Test
	public void testRemove() throws Exception {
		SCLicense newSCLicense = addSCLicense();

		_persistence.remove(newSCLicense);

		SCLicense existingSCLicense = _persistence.fetchByPrimaryKey(newSCLicense.getPrimaryKey());

		Assert.assertNull(existingSCLicense);
	}

	@Test
	public void testUpdateNew() throws Exception {
		addSCLicense();
	}

	@Test
	public void testUpdateExisting() throws Exception {
		long pk = RandomTestUtil.nextLong();

		SCLicense newSCLicense = _persistence.create(pk);

		newSCLicense.setName(RandomTestUtil.randomString());

		newSCLicense.setUrl(RandomTestUtil.randomString());

		newSCLicense.setOpenSource(RandomTestUtil.randomBoolean());

		newSCLicense.setActive(RandomTestUtil.randomBoolean());

		newSCLicense.setRecommended(RandomTestUtil.randomBoolean());

		_scLicenses.add(_persistence.update(newSCLicense));

		SCLicense existingSCLicense = _persistence.findByPrimaryKey(newSCLicense.getPrimaryKey());

		Assert.assertEquals(existingSCLicense.getLicenseId(),
			newSCLicense.getLicenseId());
		Assert.assertEquals(existingSCLicense.getName(), newSCLicense.getName());
		Assert.assertEquals(existingSCLicense.getUrl(), newSCLicense.getUrl());
		Assert.assertEquals(existingSCLicense.getOpenSource(),
			newSCLicense.getOpenSource());
		Assert.assertEquals(existingSCLicense.getActive(),
			newSCLicense.getActive());
		Assert.assertEquals(existingSCLicense.getRecommended(),
			newSCLicense.getRecommended());
	}

	@Test
	public void testCountByActive() throws Exception {
		_persistence.countByActive(RandomTestUtil.randomBoolean());

		_persistence.countByActive(RandomTestUtil.randomBoolean());
	}

	@Test
	public void testCountByA_R() throws Exception {
		_persistence.countByA_R(RandomTestUtil.randomBoolean(),
			RandomTestUtil.randomBoolean());

		_persistence.countByA_R(RandomTestUtil.randomBoolean(),
			RandomTestUtil.randomBoolean());
	}

	@Test
	public void testFindByPrimaryKeyExisting() throws Exception {
		SCLicense newSCLicense = addSCLicense();

		SCLicense existingSCLicense = _persistence.findByPrimaryKey(newSCLicense.getPrimaryKey());

		Assert.assertEquals(existingSCLicense, newSCLicense);
	}

	@Test(expected = NoSuchLicenseException.class)
	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		_persistence.findByPrimaryKey(pk);
	}

	@Test
	public void testFindAll() throws Exception {
		_persistence.findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			getOrderByComparator());
	}

	protected OrderByComparator<SCLicense> getOrderByComparator() {
		return OrderByComparatorFactoryUtil.create("SCLicense", "licenseId",
			true, "name", true, "url", true, "openSource", true, "active",
			true, "recommended", true);
	}

	@Test
	public void testFetchByPrimaryKeyExisting() throws Exception {
		SCLicense newSCLicense = addSCLicense();

		SCLicense existingSCLicense = _persistence.fetchByPrimaryKey(newSCLicense.getPrimaryKey());

		Assert.assertEquals(existingSCLicense, newSCLicense);
	}

	@Test
	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		SCLicense missingSCLicense = _persistence.fetchByPrimaryKey(pk);

		Assert.assertNull(missingSCLicense);
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereAllPrimaryKeysExist()
		throws Exception {
		SCLicense newSCLicense1 = addSCLicense();
		SCLicense newSCLicense2 = addSCLicense();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newSCLicense1.getPrimaryKey());
		primaryKeys.add(newSCLicense2.getPrimaryKey());

		Map<Serializable, SCLicense> scLicenses = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(2, scLicenses.size());
		Assert.assertEquals(newSCLicense1,
			scLicenses.get(newSCLicense1.getPrimaryKey()));
		Assert.assertEquals(newSCLicense2,
			scLicenses.get(newSCLicense2.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereNoPrimaryKeysExist()
		throws Exception {
		long pk1 = RandomTestUtil.nextLong();

		long pk2 = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(pk1);
		primaryKeys.add(pk2);

		Map<Serializable, SCLicense> scLicenses = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(scLicenses.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereSomePrimaryKeysExist()
		throws Exception {
		SCLicense newSCLicense = addSCLicense();

		long pk = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newSCLicense.getPrimaryKey());
		primaryKeys.add(pk);

		Map<Serializable, SCLicense> scLicenses = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, scLicenses.size());
		Assert.assertEquals(newSCLicense,
			scLicenses.get(newSCLicense.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithNoPrimaryKeys()
		throws Exception {
		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		Map<Serializable, SCLicense> scLicenses = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(scLicenses.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithOnePrimaryKey()
		throws Exception {
		SCLicense newSCLicense = addSCLicense();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newSCLicense.getPrimaryKey());

		Map<Serializable, SCLicense> scLicenses = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, scLicenses.size());
		Assert.assertEquals(newSCLicense,
			scLicenses.get(newSCLicense.getPrimaryKey()));
	}

	@Test
	public void testActionableDynamicQuery() throws Exception {
		final IntegerWrapper count = new IntegerWrapper();

		ActionableDynamicQuery actionableDynamicQuery = SCLicenseLocalServiceUtil.getActionableDynamicQuery();

		actionableDynamicQuery.setPerformActionMethod(new ActionableDynamicQuery.PerformActionMethod() {
				@Override
				public void performAction(Object object) {
					SCLicense scLicense = (SCLicense)object;

					Assert.assertNotNull(scLicense);

					count.increment();
				}
			});

		actionableDynamicQuery.performActions();

		Assert.assertEquals(count.getValue(), _persistence.countAll());
	}

	@Test
	public void testDynamicQueryByPrimaryKeyExisting()
		throws Exception {
		SCLicense newSCLicense = addSCLicense();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(SCLicense.class,
				_dynamicQueryClassLoader);

		dynamicQuery.add(RestrictionsFactoryUtil.eq("licenseId",
				newSCLicense.getLicenseId()));

		List<SCLicense> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		SCLicense existingSCLicense = result.get(0);

		Assert.assertEquals(existingSCLicense, newSCLicense);
	}

	@Test
	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(SCLicense.class,
				_dynamicQueryClassLoader);

		dynamicQuery.add(RestrictionsFactoryUtil.eq("licenseId",
				RandomTestUtil.nextLong()));

		List<SCLicense> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testDynamicQueryByProjectionExisting()
		throws Exception {
		SCLicense newSCLicense = addSCLicense();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(SCLicense.class,
				_dynamicQueryClassLoader);

		dynamicQuery.setProjection(ProjectionFactoryUtil.property("licenseId"));

		Object newLicenseId = newSCLicense.getLicenseId();

		dynamicQuery.add(RestrictionsFactoryUtil.in("licenseId",
				new Object[] { newLicenseId }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		Object existingLicenseId = result.get(0);

		Assert.assertEquals(existingLicenseId, newLicenseId);
	}

	@Test
	public void testDynamicQueryByProjectionMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(SCLicense.class,
				_dynamicQueryClassLoader);

		dynamicQuery.setProjection(ProjectionFactoryUtil.property("licenseId"));

		dynamicQuery.add(RestrictionsFactoryUtil.in("licenseId",
				new Object[] { RandomTestUtil.nextLong() }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	protected SCLicense addSCLicense() throws Exception {
		long pk = RandomTestUtil.nextLong();

		SCLicense scLicense = _persistence.create(pk);

		scLicense.setName(RandomTestUtil.randomString());

		scLicense.setUrl(RandomTestUtil.randomString());

		scLicense.setOpenSource(RandomTestUtil.randomBoolean());

		scLicense.setActive(RandomTestUtil.randomBoolean());

		scLicense.setRecommended(RandomTestUtil.randomBoolean());

		_scLicenses.add(_persistence.update(scLicense));

		return scLicense;
	}

	private List<SCLicense> _scLicenses = new ArrayList<SCLicense>();
	private SCLicensePersistence _persistence;
	private ClassLoader _dynamicQueryClassLoader;
}