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

package com.liferay.portal.service.persistence.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQueryFactoryUtil;
import com.liferay.portal.kernel.dao.orm.ProjectionFactoryUtil;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.RestrictionsFactoryUtil;
import com.liferay.portal.kernel.exception.NoSuchPreferencesException;
import com.liferay.portal.kernel.model.PortalPreferences;
import com.liferay.portal.kernel.service.PortalPreferencesLocalServiceUtil;
import com.liferay.portal.kernel.service.persistence.PortalPreferencesPersistence;
import com.liferay.portal.kernel.service.persistence.PortalPreferencesUtil;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.transaction.Propagation;
import com.liferay.portal.kernel.util.IntegerWrapper;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.OrderByComparatorFactoryUtil;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PersistenceTestRule;
import com.liferay.portal.test.rule.TransactionalTestRule;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @generated
 */
@RunWith(Arquillian.class)
public class PortalPreferencesPersistenceTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(), PersistenceTestRule.INSTANCE,
			new TransactionalTestRule(Propagation.REQUIRED));

	@Before
	public void setUp() {
		_persistence = PortalPreferencesUtil.getPersistence();

		Class<?> clazz = _persistence.getClass();

		_dynamicQueryClassLoader = clazz.getClassLoader();
	}

	@After
	public void tearDown() throws Exception {
		Iterator<PortalPreferences> iterator = _portalPreferenceses.iterator();

		while (iterator.hasNext()) {
			_persistence.remove(iterator.next());

			iterator.remove();
		}
	}

	@Test
	public void testCreate() throws Exception {
		long pk = RandomTestUtil.nextLong();

		PortalPreferences portalPreferences = _persistence.create(pk);

		Assert.assertNotNull(portalPreferences);

		Assert.assertEquals(portalPreferences.getPrimaryKey(), pk);
	}

	@Test
	public void testRemove() throws Exception {
		PortalPreferences newPortalPreferences = addPortalPreferences();

		_persistence.remove(newPortalPreferences);

		PortalPreferences existingPortalPreferences =
			_persistence.fetchByPrimaryKey(
				newPortalPreferences.getPrimaryKey());

		Assert.assertNull(existingPortalPreferences);
	}

	@Test
	public void testUpdateNew() throws Exception {
		addPortalPreferences();
	}

	@Test
	public void testUpdateExisting() throws Exception {
		long pk = RandomTestUtil.nextLong();

		PortalPreferences newPortalPreferences = _persistence.create(pk);

		newPortalPreferences.setMvccVersion(RandomTestUtil.nextLong());

		newPortalPreferences.setOwnerId(RandomTestUtil.nextLong());

		newPortalPreferences.setOwnerType(RandomTestUtil.nextInt());

		newPortalPreferences.setPreferences(RandomTestUtil.randomString());

		_portalPreferenceses.add(_persistence.update(newPortalPreferences));

		PortalPreferences existingPortalPreferences =
			_persistence.findByPrimaryKey(newPortalPreferences.getPrimaryKey());

		Assert.assertEquals(
			existingPortalPreferences.getMvccVersion(),
			newPortalPreferences.getMvccVersion());
		Assert.assertEquals(
			existingPortalPreferences.getPortalPreferencesId(),
			newPortalPreferences.getPortalPreferencesId());
		Assert.assertEquals(
			existingPortalPreferences.getOwnerId(),
			newPortalPreferences.getOwnerId());
		Assert.assertEquals(
			existingPortalPreferences.getOwnerType(),
			newPortalPreferences.getOwnerType());
		Assert.assertEquals(
			existingPortalPreferences.getPreferences(),
			newPortalPreferences.getPreferences());
	}

	@Test
	public void testCountByO_O() throws Exception {
		_persistence.countByO_O(
			RandomTestUtil.nextLong(), RandomTestUtil.nextInt());

		_persistence.countByO_O(0L, 0);
	}

	@Test
	public void testFindByPrimaryKeyExisting() throws Exception {
		PortalPreferences newPortalPreferences = addPortalPreferences();

		PortalPreferences existingPortalPreferences =
			_persistence.findByPrimaryKey(newPortalPreferences.getPrimaryKey());

		Assert.assertEquals(existingPortalPreferences, newPortalPreferences);
	}

	@Test(expected = NoSuchPreferencesException.class)
	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		_persistence.findByPrimaryKey(pk);
	}

	@Test
	public void testFindAll() throws Exception {
		_persistence.findAll(
			QueryUtil.ALL_POS, QueryUtil.ALL_POS, getOrderByComparator());
	}

	protected OrderByComparator<PortalPreferences> getOrderByComparator() {
		return OrderByComparatorFactoryUtil.create(
			"PortalPreferences", "mvccVersion", true, "portalPreferencesId",
			true, "ownerId", true, "ownerType", true);
	}

	@Test
	public void testFetchByPrimaryKeyExisting() throws Exception {
		PortalPreferences newPortalPreferences = addPortalPreferences();

		PortalPreferences existingPortalPreferences =
			_persistence.fetchByPrimaryKey(
				newPortalPreferences.getPrimaryKey());

		Assert.assertEquals(existingPortalPreferences, newPortalPreferences);
	}

	@Test
	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		PortalPreferences missingPortalPreferences =
			_persistence.fetchByPrimaryKey(pk);

		Assert.assertNull(missingPortalPreferences);
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereAllPrimaryKeysExist()
		throws Exception {

		PortalPreferences newPortalPreferences1 = addPortalPreferences();
		PortalPreferences newPortalPreferences2 = addPortalPreferences();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newPortalPreferences1.getPrimaryKey());
		primaryKeys.add(newPortalPreferences2.getPrimaryKey());

		Map<Serializable, PortalPreferences> portalPreferenceses =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(2, portalPreferenceses.size());
		Assert.assertEquals(
			newPortalPreferences1,
			portalPreferenceses.get(newPortalPreferences1.getPrimaryKey()));
		Assert.assertEquals(
			newPortalPreferences2,
			portalPreferenceses.get(newPortalPreferences2.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereNoPrimaryKeysExist()
		throws Exception {

		long pk1 = RandomTestUtil.nextLong();

		long pk2 = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(pk1);
		primaryKeys.add(pk2);

		Map<Serializable, PortalPreferences> portalPreferenceses =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(portalPreferenceses.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereSomePrimaryKeysExist()
		throws Exception {

		PortalPreferences newPortalPreferences = addPortalPreferences();

		long pk = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newPortalPreferences.getPrimaryKey());
		primaryKeys.add(pk);

		Map<Serializable, PortalPreferences> portalPreferenceses =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, portalPreferenceses.size());
		Assert.assertEquals(
			newPortalPreferences,
			portalPreferenceses.get(newPortalPreferences.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithNoPrimaryKeys() throws Exception {
		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		Map<Serializable, PortalPreferences> portalPreferenceses =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(portalPreferenceses.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithOnePrimaryKey() throws Exception {
		PortalPreferences newPortalPreferences = addPortalPreferences();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newPortalPreferences.getPrimaryKey());

		Map<Serializable, PortalPreferences> portalPreferenceses =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, portalPreferenceses.size());
		Assert.assertEquals(
			newPortalPreferences,
			portalPreferenceses.get(newPortalPreferences.getPrimaryKey()));
	}

	@Test
	public void testActionableDynamicQuery() throws Exception {
		final IntegerWrapper count = new IntegerWrapper();

		ActionableDynamicQuery actionableDynamicQuery =
			PortalPreferencesLocalServiceUtil.getActionableDynamicQuery();

		actionableDynamicQuery.setPerformActionMethod(
			new ActionableDynamicQuery.PerformActionMethod
				<PortalPreferences>() {

				@Override
				public void performAction(PortalPreferences portalPreferences) {
					Assert.assertNotNull(portalPreferences);

					count.increment();
				}

			});

		actionableDynamicQuery.performActions();

		Assert.assertEquals(count.getValue(), _persistence.countAll());
	}

	@Test
	public void testDynamicQueryByPrimaryKeyExisting() throws Exception {
		PortalPreferences newPortalPreferences = addPortalPreferences();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			PortalPreferences.class, _dynamicQueryClassLoader);

		dynamicQuery.add(
			RestrictionsFactoryUtil.eq(
				"portalPreferencesId",
				newPortalPreferences.getPortalPreferencesId()));

		List<PortalPreferences> result = _persistence.findWithDynamicQuery(
			dynamicQuery);

		Assert.assertEquals(1, result.size());

		PortalPreferences existingPortalPreferences = result.get(0);

		Assert.assertEquals(existingPortalPreferences, newPortalPreferences);
	}

	@Test
	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			PortalPreferences.class, _dynamicQueryClassLoader);

		dynamicQuery.add(
			RestrictionsFactoryUtil.eq(
				"portalPreferencesId", RandomTestUtil.nextLong()));

		List<PortalPreferences> result = _persistence.findWithDynamicQuery(
			dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testDynamicQueryByProjectionExisting() throws Exception {
		PortalPreferences newPortalPreferences = addPortalPreferences();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			PortalPreferences.class, _dynamicQueryClassLoader);

		dynamicQuery.setProjection(
			ProjectionFactoryUtil.property("portalPreferencesId"));

		Object newPortalPreferencesId =
			newPortalPreferences.getPortalPreferencesId();

		dynamicQuery.add(
			RestrictionsFactoryUtil.in(
				"portalPreferencesId", new Object[] {newPortalPreferencesId}));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		Object existingPortalPreferencesId = result.get(0);

		Assert.assertEquals(
			existingPortalPreferencesId, newPortalPreferencesId);
	}

	@Test
	public void testDynamicQueryByProjectionMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			PortalPreferences.class, _dynamicQueryClassLoader);

		dynamicQuery.setProjection(
			ProjectionFactoryUtil.property("portalPreferencesId"));

		dynamicQuery.add(
			RestrictionsFactoryUtil.in(
				"portalPreferencesId",
				new Object[] {RandomTestUtil.nextLong()}));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testResetOriginalValues() throws Exception {
		PortalPreferences newPortalPreferences = addPortalPreferences();

		_persistence.clearCache();

		PortalPreferences existingPortalPreferences =
			_persistence.findByPrimaryKey(newPortalPreferences.getPrimaryKey());

		Assert.assertEquals(
			Long.valueOf(existingPortalPreferences.getOwnerId()),
			ReflectionTestUtil.<Long>invoke(
				existingPortalPreferences, "getOriginalOwnerId",
				new Class<?>[0]));
		Assert.assertEquals(
			Integer.valueOf(existingPortalPreferences.getOwnerType()),
			ReflectionTestUtil.<Integer>invoke(
				existingPortalPreferences, "getOriginalOwnerType",
				new Class<?>[0]));
	}

	protected PortalPreferences addPortalPreferences() throws Exception {
		long pk = RandomTestUtil.nextLong();

		PortalPreferences portalPreferences = _persistence.create(pk);

		portalPreferences.setMvccVersion(RandomTestUtil.nextLong());

		portalPreferences.setOwnerId(RandomTestUtil.nextLong());

		portalPreferences.setOwnerType(RandomTestUtil.nextInt());

		portalPreferences.setPreferences(RandomTestUtil.randomString());

		_portalPreferenceses.add(_persistence.update(portalPreferences));

		return portalPreferences;
	}

	private List<PortalPreferences> _portalPreferenceses =
		new ArrayList<PortalPreferences>();
	private PortalPreferencesPersistence _persistence;
	private ClassLoader _dynamicQueryClassLoader;

}