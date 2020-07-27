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

package com.liferay.app.builder.service.persistence.test;

import com.liferay.app.builder.exception.NoSuchAppDeploymentException;
import com.liferay.app.builder.model.AppBuilderAppDeployment;
import com.liferay.app.builder.service.AppBuilderAppDeploymentLocalServiceUtil;
import com.liferay.app.builder.service.persistence.AppBuilderAppDeploymentPersistence;
import com.liferay.app.builder.service.persistence.AppBuilderAppDeploymentUtil;
import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQueryFactoryUtil;
import com.liferay.portal.kernel.dao.orm.ProjectionFactoryUtil;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.RestrictionsFactoryUtil;
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
import java.util.Objects;
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
public class AppBuilderAppDeploymentPersistenceTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(), PersistenceTestRule.INSTANCE,
			new TransactionalTestRule(
				Propagation.REQUIRED, "com.liferay.app.builder.service"));

	@Before
	public void setUp() {
		_persistence = AppBuilderAppDeploymentUtil.getPersistence();

		Class<?> clazz = _persistence.getClass();

		_dynamicQueryClassLoader = clazz.getClassLoader();
	}

	@After
	public void tearDown() throws Exception {
		Iterator<AppBuilderAppDeployment> iterator =
			_appBuilderAppDeployments.iterator();

		while (iterator.hasNext()) {
			_persistence.remove(iterator.next());

			iterator.remove();
		}
	}

	@Test
	public void testCreate() throws Exception {
		long pk = RandomTestUtil.nextLong();

		AppBuilderAppDeployment appBuilderAppDeployment = _persistence.create(
			pk);

		Assert.assertNotNull(appBuilderAppDeployment);

		Assert.assertEquals(appBuilderAppDeployment.getPrimaryKey(), pk);
	}

	@Test
	public void testRemove() throws Exception {
		AppBuilderAppDeployment newAppBuilderAppDeployment =
			addAppBuilderAppDeployment();

		_persistence.remove(newAppBuilderAppDeployment);

		AppBuilderAppDeployment existingAppBuilderAppDeployment =
			_persistence.fetchByPrimaryKey(
				newAppBuilderAppDeployment.getPrimaryKey());

		Assert.assertNull(existingAppBuilderAppDeployment);
	}

	@Test
	public void testUpdateNew() throws Exception {
		addAppBuilderAppDeployment();
	}

	@Test
	public void testUpdateExisting() throws Exception {
		long pk = RandomTestUtil.nextLong();

		AppBuilderAppDeployment newAppBuilderAppDeployment =
			_persistence.create(pk);

		newAppBuilderAppDeployment.setCompanyId(RandomTestUtil.nextLong());

		newAppBuilderAppDeployment.setAppBuilderAppId(
			RandomTestUtil.nextLong());

		newAppBuilderAppDeployment.setSettings(RandomTestUtil.randomString());

		newAppBuilderAppDeployment.setType(RandomTestUtil.randomString());

		_appBuilderAppDeployments.add(
			_persistence.update(newAppBuilderAppDeployment));

		AppBuilderAppDeployment existingAppBuilderAppDeployment =
			_persistence.findByPrimaryKey(
				newAppBuilderAppDeployment.getPrimaryKey());

		Assert.assertEquals(
			existingAppBuilderAppDeployment.getAppBuilderAppDeploymentId(),
			newAppBuilderAppDeployment.getAppBuilderAppDeploymentId());
		Assert.assertEquals(
			existingAppBuilderAppDeployment.getCompanyId(),
			newAppBuilderAppDeployment.getCompanyId());
		Assert.assertEquals(
			existingAppBuilderAppDeployment.getAppBuilderAppId(),
			newAppBuilderAppDeployment.getAppBuilderAppId());
		Assert.assertEquals(
			existingAppBuilderAppDeployment.getSettings(),
			newAppBuilderAppDeployment.getSettings());
		Assert.assertEquals(
			existingAppBuilderAppDeployment.getType(),
			newAppBuilderAppDeployment.getType());
	}

	@Test
	public void testCountByAppBuilderAppId() throws Exception {
		_persistence.countByAppBuilderAppId(RandomTestUtil.nextLong());

		_persistence.countByAppBuilderAppId(0L);
	}

	@Test
	public void testCountByA_T() throws Exception {
		_persistence.countByA_T(RandomTestUtil.nextLong(), "");

		_persistence.countByA_T(0L, "null");

		_persistence.countByA_T(0L, (String)null);
	}

	@Test
	public void testFindByPrimaryKeyExisting() throws Exception {
		AppBuilderAppDeployment newAppBuilderAppDeployment =
			addAppBuilderAppDeployment();

		AppBuilderAppDeployment existingAppBuilderAppDeployment =
			_persistence.findByPrimaryKey(
				newAppBuilderAppDeployment.getPrimaryKey());

		Assert.assertEquals(
			existingAppBuilderAppDeployment, newAppBuilderAppDeployment);
	}

	@Test(expected = NoSuchAppDeploymentException.class)
	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		_persistence.findByPrimaryKey(pk);
	}

	@Test
	public void testFindAll() throws Exception {
		_persistence.findAll(
			QueryUtil.ALL_POS, QueryUtil.ALL_POS, getOrderByComparator());
	}

	protected OrderByComparator<AppBuilderAppDeployment>
		getOrderByComparator() {

		return OrderByComparatorFactoryUtil.create(
			"AppBuilderAppDeployment", "appBuilderAppDeploymentId", true,
			"companyId", true, "appBuilderAppId", true, "type", true);
	}

	@Test
	public void testFetchByPrimaryKeyExisting() throws Exception {
		AppBuilderAppDeployment newAppBuilderAppDeployment =
			addAppBuilderAppDeployment();

		AppBuilderAppDeployment existingAppBuilderAppDeployment =
			_persistence.fetchByPrimaryKey(
				newAppBuilderAppDeployment.getPrimaryKey());

		Assert.assertEquals(
			existingAppBuilderAppDeployment, newAppBuilderAppDeployment);
	}

	@Test
	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		AppBuilderAppDeployment missingAppBuilderAppDeployment =
			_persistence.fetchByPrimaryKey(pk);

		Assert.assertNull(missingAppBuilderAppDeployment);
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereAllPrimaryKeysExist()
		throws Exception {

		AppBuilderAppDeployment newAppBuilderAppDeployment1 =
			addAppBuilderAppDeployment();
		AppBuilderAppDeployment newAppBuilderAppDeployment2 =
			addAppBuilderAppDeployment();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newAppBuilderAppDeployment1.getPrimaryKey());
		primaryKeys.add(newAppBuilderAppDeployment2.getPrimaryKey());

		Map<Serializable, AppBuilderAppDeployment> appBuilderAppDeployments =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(2, appBuilderAppDeployments.size());
		Assert.assertEquals(
			newAppBuilderAppDeployment1,
			appBuilderAppDeployments.get(
				newAppBuilderAppDeployment1.getPrimaryKey()));
		Assert.assertEquals(
			newAppBuilderAppDeployment2,
			appBuilderAppDeployments.get(
				newAppBuilderAppDeployment2.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereNoPrimaryKeysExist()
		throws Exception {

		long pk1 = RandomTestUtil.nextLong();

		long pk2 = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(pk1);
		primaryKeys.add(pk2);

		Map<Serializable, AppBuilderAppDeployment> appBuilderAppDeployments =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(appBuilderAppDeployments.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereSomePrimaryKeysExist()
		throws Exception {

		AppBuilderAppDeployment newAppBuilderAppDeployment =
			addAppBuilderAppDeployment();

		long pk = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newAppBuilderAppDeployment.getPrimaryKey());
		primaryKeys.add(pk);

		Map<Serializable, AppBuilderAppDeployment> appBuilderAppDeployments =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, appBuilderAppDeployments.size());
		Assert.assertEquals(
			newAppBuilderAppDeployment,
			appBuilderAppDeployments.get(
				newAppBuilderAppDeployment.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithNoPrimaryKeys() throws Exception {
		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		Map<Serializable, AppBuilderAppDeployment> appBuilderAppDeployments =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(appBuilderAppDeployments.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithOnePrimaryKey() throws Exception {
		AppBuilderAppDeployment newAppBuilderAppDeployment =
			addAppBuilderAppDeployment();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newAppBuilderAppDeployment.getPrimaryKey());

		Map<Serializable, AppBuilderAppDeployment> appBuilderAppDeployments =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, appBuilderAppDeployments.size());
		Assert.assertEquals(
			newAppBuilderAppDeployment,
			appBuilderAppDeployments.get(
				newAppBuilderAppDeployment.getPrimaryKey()));
	}

	@Test
	public void testActionableDynamicQuery() throws Exception {
		final IntegerWrapper count = new IntegerWrapper();

		ActionableDynamicQuery actionableDynamicQuery =
			AppBuilderAppDeploymentLocalServiceUtil.getActionableDynamicQuery();

		actionableDynamicQuery.setPerformActionMethod(
			new ActionableDynamicQuery.PerformActionMethod
				<AppBuilderAppDeployment>() {

				@Override
				public void performAction(
					AppBuilderAppDeployment appBuilderAppDeployment) {

					Assert.assertNotNull(appBuilderAppDeployment);

					count.increment();
				}

			});

		actionableDynamicQuery.performActions();

		Assert.assertEquals(count.getValue(), _persistence.countAll());
	}

	@Test
	public void testDynamicQueryByPrimaryKeyExisting() throws Exception {
		AppBuilderAppDeployment newAppBuilderAppDeployment =
			addAppBuilderAppDeployment();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			AppBuilderAppDeployment.class, _dynamicQueryClassLoader);

		dynamicQuery.add(
			RestrictionsFactoryUtil.eq(
				"appBuilderAppDeploymentId",
				newAppBuilderAppDeployment.getAppBuilderAppDeploymentId()));

		List<AppBuilderAppDeployment> result =
			_persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		AppBuilderAppDeployment existingAppBuilderAppDeployment = result.get(0);

		Assert.assertEquals(
			existingAppBuilderAppDeployment, newAppBuilderAppDeployment);
	}

	@Test
	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			AppBuilderAppDeployment.class, _dynamicQueryClassLoader);

		dynamicQuery.add(
			RestrictionsFactoryUtil.eq(
				"appBuilderAppDeploymentId", RandomTestUtil.nextLong()));

		List<AppBuilderAppDeployment> result =
			_persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testDynamicQueryByProjectionExisting() throws Exception {
		AppBuilderAppDeployment newAppBuilderAppDeployment =
			addAppBuilderAppDeployment();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			AppBuilderAppDeployment.class, _dynamicQueryClassLoader);

		dynamicQuery.setProjection(
			ProjectionFactoryUtil.property("appBuilderAppDeploymentId"));

		Object newAppBuilderAppDeploymentId =
			newAppBuilderAppDeployment.getAppBuilderAppDeploymentId();

		dynamicQuery.add(
			RestrictionsFactoryUtil.in(
				"appBuilderAppDeploymentId",
				new Object[] {newAppBuilderAppDeploymentId}));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		Object existingAppBuilderAppDeploymentId = result.get(0);

		Assert.assertEquals(
			existingAppBuilderAppDeploymentId, newAppBuilderAppDeploymentId);
	}

	@Test
	public void testDynamicQueryByProjectionMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			AppBuilderAppDeployment.class, _dynamicQueryClassLoader);

		dynamicQuery.setProjection(
			ProjectionFactoryUtil.property("appBuilderAppDeploymentId"));

		dynamicQuery.add(
			RestrictionsFactoryUtil.in(
				"appBuilderAppDeploymentId",
				new Object[] {RandomTestUtil.nextLong()}));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testResetOriginalValues() throws Exception {
		AppBuilderAppDeployment newAppBuilderAppDeployment =
			addAppBuilderAppDeployment();

		_persistence.clearCache();

		AppBuilderAppDeployment existingAppBuilderAppDeployment =
			_persistence.findByPrimaryKey(
				newAppBuilderAppDeployment.getPrimaryKey());

		Assert.assertEquals(
			Long.valueOf(existingAppBuilderAppDeployment.getAppBuilderAppId()),
			ReflectionTestUtil.<Long>invoke(
				existingAppBuilderAppDeployment, "getOriginalAppBuilderAppId",
				new Class<?>[0]));
		Assert.assertTrue(
			Objects.equals(
				existingAppBuilderAppDeployment.getType(),
				ReflectionTestUtil.invoke(
					existingAppBuilderAppDeployment, "getOriginalType",
					new Class<?>[0])));
	}

	protected AppBuilderAppDeployment addAppBuilderAppDeployment()
		throws Exception {

		long pk = RandomTestUtil.nextLong();

		AppBuilderAppDeployment appBuilderAppDeployment = _persistence.create(
			pk);

		appBuilderAppDeployment.setCompanyId(RandomTestUtil.nextLong());

		appBuilderAppDeployment.setAppBuilderAppId(RandomTestUtil.nextLong());

		appBuilderAppDeployment.setSettings(RandomTestUtil.randomString());

		appBuilderAppDeployment.setType(RandomTestUtil.randomString());

		_appBuilderAppDeployments.add(
			_persistence.update(appBuilderAppDeployment));

		return appBuilderAppDeployment;
	}

	private List<AppBuilderAppDeployment> _appBuilderAppDeployments =
		new ArrayList<AppBuilderAppDeployment>();
	private AppBuilderAppDeploymentPersistence _persistence;
	private ClassLoader _dynamicQueryClassLoader;

}