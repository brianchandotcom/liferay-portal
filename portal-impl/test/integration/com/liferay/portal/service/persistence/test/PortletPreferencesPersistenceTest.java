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

import com.liferay.portal.NoSuchPortletPreferencesException;
import com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQueryFactoryUtil;
import com.liferay.portal.kernel.dao.orm.ProjectionFactoryUtil;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.RestrictionsFactoryUtil;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.TransactionalTestRule;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.transaction.Propagation;
import com.liferay.portal.kernel.util.IntegerWrapper;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.OrderByComparatorFactoryUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.PortletPreferences;
import com.liferay.portal.service.PortletPreferencesLocalServiceUtil;
import com.liferay.portal.service.persistence.PortletPreferencesPersistence;
import com.liferay.portal.service.persistence.PortletPreferencesUtil;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PersistenceTestRule;

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
public class PortletPreferencesPersistenceTest {
	@Rule
	public final AggregateTestRule aggregateTestRule = new AggregateTestRule(new LiferayIntegrationTestRule(),
			PersistenceTestRule.INSTANCE,
			new TransactionalTestRule(Propagation.REQUIRED));

	@Before
	public void setUp() {
		_persistence = PortletPreferencesUtil.getPersistence();

		Class<?> clazz = _persistence.getClass();

		_dynamicQueryClassLoader = clazz.getClassLoader();
	}

	@After
	public void tearDown() throws Exception {
		Iterator<PortletPreferences> iterator = _portletPreferenceses.iterator();

		while (iterator.hasNext()) {
			_persistence.remove(iterator.next());

			iterator.remove();
		}
	}

	@Test
	public void testCreate() throws Exception {
		long pk = RandomTestUtil.nextLong();

		PortletPreferences portletPreferences = _persistence.create(pk);

		Assert.assertNotNull(portletPreferences);

		Assert.assertEquals(portletPreferences.getPrimaryKey(), pk);
	}

	@Test
	public void testRemove() throws Exception {
		PortletPreferences newPortletPreferences = addPortletPreferences();

		_persistence.remove(newPortletPreferences);

		PortletPreferences existingPortletPreferences = _persistence.fetchByPrimaryKey(newPortletPreferences.getPrimaryKey());

		Assert.assertNull(existingPortletPreferences);
	}

	@Test
	public void testUpdateNew() throws Exception {
		addPortletPreferences();
	}

	@Test
	public void testUpdateExisting() throws Exception {
		long pk = RandomTestUtil.nextLong();

		PortletPreferences newPortletPreferences = _persistence.create(pk);

		newPortletPreferences.setMvccVersion(RandomTestUtil.nextLong());

		newPortletPreferences.setOwnerId(RandomTestUtil.nextLong());

		newPortletPreferences.setOwnerType(RandomTestUtil.nextInt());

		newPortletPreferences.setPlid(RandomTestUtil.nextLong());

		newPortletPreferences.setPortletId(RandomTestUtil.randomString());

		newPortletPreferences.setPreferences(RandomTestUtil.randomString());

		_portletPreferenceses.add(_persistence.update(newPortletPreferences));

		PortletPreferences existingPortletPreferences = _persistence.findByPrimaryKey(newPortletPreferences.getPrimaryKey());

		Assert.assertEquals(existingPortletPreferences.getMvccVersion(),
			newPortletPreferences.getMvccVersion());
		Assert.assertEquals(existingPortletPreferences.getPortletPreferencesId(),
			newPortletPreferences.getPortletPreferencesId());
		Assert.assertEquals(existingPortletPreferences.getOwnerId(),
			newPortletPreferences.getOwnerId());
		Assert.assertEquals(existingPortletPreferences.getOwnerType(),
			newPortletPreferences.getOwnerType());
		Assert.assertEquals(existingPortletPreferences.getPlid(),
			newPortletPreferences.getPlid());
		Assert.assertEquals(existingPortletPreferences.getPortletId(),
			newPortletPreferences.getPortletId());
		Assert.assertEquals(existingPortletPreferences.getPreferences(),
			newPortletPreferences.getPreferences());
	}

	@Test
	public void testCountByPlid() throws Exception {
		_persistence.countByPlid(RandomTestUtil.nextLong());

		_persistence.countByPlid(0L);
	}

	@Test
	public void testCountByPortletId() throws Exception {
		_persistence.countByPortletId(StringPool.BLANK);

		_persistence.countByPortletId(StringPool.NULL);

		_persistence.countByPortletId((String)null);
	}

	@Test
	public void testCountByO_P() throws Exception {
		_persistence.countByO_P(RandomTestUtil.nextInt(), StringPool.BLANK);

		_persistence.countByO_P(0, StringPool.NULL);

		_persistence.countByO_P(0, (String)null);
	}

	@Test
	public void testCountByP_P() throws Exception {
		_persistence.countByP_P(RandomTestUtil.nextLong(), StringPool.BLANK);

		_persistence.countByP_P(0L, StringPool.NULL);

		_persistence.countByP_P(0L, (String)null);
	}

	@Test
	public void testCountByO_O_P() throws Exception {
		_persistence.countByO_O_P(RandomTestUtil.nextLong(),
			RandomTestUtil.nextInt(), RandomTestUtil.nextLong());

		_persistence.countByO_O_P(0L, 0, 0L);
	}

	@Test
	public void testCountByO_O_PI() throws Exception {
		_persistence.countByO_O_PI(RandomTestUtil.nextLong(),
			RandomTestUtil.nextInt(), StringPool.BLANK);

		_persistence.countByO_O_PI(0L, 0, StringPool.NULL);

		_persistence.countByO_O_PI(0L, 0, (String)null);
	}

	@Test
	public void testCountByO_P_P() throws Exception {
		_persistence.countByO_P_P(RandomTestUtil.nextInt(),
			RandomTestUtil.nextLong(), StringPool.BLANK);

		_persistence.countByO_P_P(0, 0L, StringPool.NULL);

		_persistence.countByO_P_P(0, 0L, (String)null);
	}

	@Test
	public void testCountByO_O_P_P() throws Exception {
		_persistence.countByO_O_P_P(RandomTestUtil.nextLong(),
			RandomTestUtil.nextInt(), RandomTestUtil.nextLong(),
			StringPool.BLANK);

		_persistence.countByO_O_P_P(0L, 0, 0L, StringPool.NULL);

		_persistence.countByO_O_P_P(0L, 0, 0L, (String)null);
	}

	@Test
	public void testFindByPrimaryKeyExisting() throws Exception {
		PortletPreferences newPortletPreferences = addPortletPreferences();

		PortletPreferences existingPortletPreferences = _persistence.findByPrimaryKey(newPortletPreferences.getPrimaryKey());

		Assert.assertEquals(existingPortletPreferences, newPortletPreferences);
	}

	@Test(expected = NoSuchPortletPreferencesException.class)
	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		_persistence.findByPrimaryKey(pk);
	}

	@Test
	public void testFindAll() throws Exception {
		_persistence.findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			getOrderByComparator());
	}

	protected OrderByComparator<PortletPreferences> getOrderByComparator() {
		return OrderByComparatorFactoryUtil.create("PortletPreferences",
			"mvccVersion", true, "portletPreferencesId", true, "ownerId", true,
			"ownerType", true, "plid", true, "portletId", true, "preferences",
			true);
	}

	@Test
	public void testFetchByPrimaryKeyExisting() throws Exception {
		PortletPreferences newPortletPreferences = addPortletPreferences();

		PortletPreferences existingPortletPreferences = _persistence.fetchByPrimaryKey(newPortletPreferences.getPrimaryKey());

		Assert.assertEquals(existingPortletPreferences, newPortletPreferences);
	}

	@Test
	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		PortletPreferences missingPortletPreferences = _persistence.fetchByPrimaryKey(pk);

		Assert.assertNull(missingPortletPreferences);
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereAllPrimaryKeysExist()
		throws Exception {
		PortletPreferences newPortletPreferences1 = addPortletPreferences();
		PortletPreferences newPortletPreferences2 = addPortletPreferences();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newPortletPreferences1.getPrimaryKey());
		primaryKeys.add(newPortletPreferences2.getPrimaryKey());

		Map<Serializable, PortletPreferences> portletPreferenceses = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(2, portletPreferenceses.size());
		Assert.assertEquals(newPortletPreferences1,
			portletPreferenceses.get(newPortletPreferences1.getPrimaryKey()));
		Assert.assertEquals(newPortletPreferences2,
			portletPreferenceses.get(newPortletPreferences2.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereNoPrimaryKeysExist()
		throws Exception {
		long pk1 = RandomTestUtil.nextLong();

		long pk2 = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(pk1);
		primaryKeys.add(pk2);

		Map<Serializable, PortletPreferences> portletPreferenceses = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(portletPreferenceses.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereSomePrimaryKeysExist()
		throws Exception {
		PortletPreferences newPortletPreferences = addPortletPreferences();

		long pk = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newPortletPreferences.getPrimaryKey());
		primaryKeys.add(pk);

		Map<Serializable, PortletPreferences> portletPreferenceses = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, portletPreferenceses.size());
		Assert.assertEquals(newPortletPreferences,
			portletPreferenceses.get(newPortletPreferences.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithNoPrimaryKeys()
		throws Exception {
		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		Map<Serializable, PortletPreferences> portletPreferenceses = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(portletPreferenceses.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithOnePrimaryKey()
		throws Exception {
		PortletPreferences newPortletPreferences = addPortletPreferences();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newPortletPreferences.getPrimaryKey());

		Map<Serializable, PortletPreferences> portletPreferenceses = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, portletPreferenceses.size());
		Assert.assertEquals(newPortletPreferences,
			portletPreferenceses.get(newPortletPreferences.getPrimaryKey()));
	}

	@Test
	public void testActionableDynamicQuery() throws Exception {
		final IntegerWrapper count = new IntegerWrapper();

		ActionableDynamicQuery actionableDynamicQuery = PortletPreferencesLocalServiceUtil.getActionableDynamicQuery();

		actionableDynamicQuery.setPerformActionMethod(new ActionableDynamicQuery.PerformActionMethod() {
				@Override
				public void performAction(Object object) {
					PortletPreferences portletPreferences = (PortletPreferences)object;

					Assert.assertNotNull(portletPreferences);

					count.increment();
				}
			});

		actionableDynamicQuery.performActions();

		Assert.assertEquals(count.getValue(), _persistence.countAll());
	}

	@Test
	public void testDynamicQueryByPrimaryKeyExisting()
		throws Exception {
		PortletPreferences newPortletPreferences = addPortletPreferences();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(PortletPreferences.class,
				_dynamicQueryClassLoader);

		dynamicQuery.add(RestrictionsFactoryUtil.eq("portletPreferencesId",
				newPortletPreferences.getPortletPreferencesId()));

		List<PortletPreferences> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		PortletPreferences existingPortletPreferences = result.get(0);

		Assert.assertEquals(existingPortletPreferences, newPortletPreferences);
	}

	@Test
	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(PortletPreferences.class,
				_dynamicQueryClassLoader);

		dynamicQuery.add(RestrictionsFactoryUtil.eq("portletPreferencesId",
				RandomTestUtil.nextLong()));

		List<PortletPreferences> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testDynamicQueryByProjectionExisting()
		throws Exception {
		PortletPreferences newPortletPreferences = addPortletPreferences();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(PortletPreferences.class,
				_dynamicQueryClassLoader);

		dynamicQuery.setProjection(ProjectionFactoryUtil.property(
				"portletPreferencesId"));

		Object newPortletPreferencesId = newPortletPreferences.getPortletPreferencesId();

		dynamicQuery.add(RestrictionsFactoryUtil.in("portletPreferencesId",
				new Object[] { newPortletPreferencesId }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		Object existingPortletPreferencesId = result.get(0);

		Assert.assertEquals(existingPortletPreferencesId,
			newPortletPreferencesId);
	}

	@Test
	public void testDynamicQueryByProjectionMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(PortletPreferences.class,
				_dynamicQueryClassLoader);

		dynamicQuery.setProjection(ProjectionFactoryUtil.property(
				"portletPreferencesId"));

		dynamicQuery.add(RestrictionsFactoryUtil.in("portletPreferencesId",
				new Object[] { RandomTestUtil.nextLong() }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testResetOriginalValues() throws Exception {
		PortletPreferences newPortletPreferences = addPortletPreferences();

		_persistence.clearCache();

		PortletPreferences existingPortletPreferences = _persistence.findByPrimaryKey(newPortletPreferences.getPrimaryKey());

		Assert.assertEquals(existingPortletPreferences.getOwnerId(),
			ReflectionTestUtil.invoke(existingPortletPreferences,
				"getOriginalOwnerId", new Class<?>[0]));
		Assert.assertEquals(existingPortletPreferences.getOwnerType(),
			ReflectionTestUtil.invoke(existingPortletPreferences,
				"getOriginalOwnerType", new Class<?>[0]));
		Assert.assertEquals(existingPortletPreferences.getPlid(),
			ReflectionTestUtil.invoke(existingPortletPreferences,
				"getOriginalPlid", new Class<?>[0]));
		Assert.assertTrue(Validator.equals(
				existingPortletPreferences.getPortletId(),
				ReflectionTestUtil.invoke(existingPortletPreferences,
					"getOriginalPortletId", new Class<?>[0])));
	}

	protected PortletPreferences addPortletPreferences()
		throws Exception {
		long pk = RandomTestUtil.nextLong();

		PortletPreferences portletPreferences = _persistence.create(pk);

		portletPreferences.setMvccVersion(RandomTestUtil.nextLong());

		portletPreferences.setOwnerId(RandomTestUtil.nextLong());

		portletPreferences.setOwnerType(RandomTestUtil.nextInt());

		portletPreferences.setPlid(RandomTestUtil.nextLong());

		portletPreferences.setPortletId(RandomTestUtil.randomString());

		portletPreferences.setPreferences(RandomTestUtil.randomString());

		_portletPreferenceses.add(_persistence.update(portletPreferences));

		return portletPreferences;
	}

	private List<PortletPreferences> _portletPreferenceses = new ArrayList<PortletPreferences>();
	private PortletPreferencesPersistence _persistence;
	private ClassLoader _dynamicQueryClassLoader;
}