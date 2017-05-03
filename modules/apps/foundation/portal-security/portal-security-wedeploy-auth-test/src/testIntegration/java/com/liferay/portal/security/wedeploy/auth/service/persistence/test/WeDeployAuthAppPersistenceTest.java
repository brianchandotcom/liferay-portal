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

package com.liferay.portal.security.wedeploy.auth.service.persistence.test;

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
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Time;
import com.liferay.portal.security.wedeploy.auth.exception.NoSuchAppException;
import com.liferay.portal.security.wedeploy.auth.model.WeDeployAuthApp;
import com.liferay.portal.security.wedeploy.auth.service.WeDeployAuthAppLocalServiceUtil;
import com.liferay.portal.security.wedeploy.auth.service.persistence.WeDeployAuthAppPersistence;
import com.liferay.portal.security.wedeploy.auth.service.persistence.WeDeployAuthAppUtil;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PersistenceTestRule;
import com.liferay.portal.test.rule.TransactionalTestRule;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import org.junit.runner.RunWith;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/**
 * @generated
 */
@RunWith(Arquillian.class)
public class WeDeployAuthAppPersistenceTest {
	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule = new AggregateTestRule(new LiferayIntegrationTestRule(),
			PersistenceTestRule.INSTANCE,
			new TransactionalTestRule(Propagation.REQUIRED,
				"com.liferay.portal.security.wedeploy.auth.service"));

	@Before
	public void setUp() {
		_persistence = WeDeployAuthAppUtil.getPersistence();

		Class<?> clazz = _persistence.getClass();

		_dynamicQueryClassLoader = clazz.getClassLoader();
	}

	@After
	public void tearDown() throws Exception {
		Iterator<WeDeployAuthApp> iterator = _weDeployAuthApps.iterator();

		while (iterator.hasNext()) {
			_persistence.remove(iterator.next());

			iterator.remove();
		}
	}

	@Test
	public void testCreate() throws Exception {
		long pk = RandomTestUtil.nextLong();

		WeDeployAuthApp weDeployAuthApp = _persistence.create(pk);

		Assert.assertNotNull(weDeployAuthApp);

		Assert.assertEquals(weDeployAuthApp.getPrimaryKey(), pk);
	}

	@Test
	public void testRemove() throws Exception {
		WeDeployAuthApp newWeDeployAuthApp = addWeDeployAuthApp();

		_persistence.remove(newWeDeployAuthApp);

		WeDeployAuthApp existingWeDeployAuthApp = _persistence.fetchByPrimaryKey(newWeDeployAuthApp.getPrimaryKey());

		Assert.assertNull(existingWeDeployAuthApp);
	}

	@Test
	public void testUpdateNew() throws Exception {
		addWeDeployAuthApp();
	}

	@Test
	public void testUpdateExisting() throws Exception {
		long pk = RandomTestUtil.nextLong();

		WeDeployAuthApp newWeDeployAuthApp = _persistence.create(pk);

		newWeDeployAuthApp.setCompanyId(RandomTestUtil.nextLong());

		newWeDeployAuthApp.setUserId(RandomTestUtil.nextLong());

		newWeDeployAuthApp.setUserName(RandomTestUtil.randomString());

		newWeDeployAuthApp.setCreateDate(RandomTestUtil.nextDate());

		newWeDeployAuthApp.setModifiedDate(RandomTestUtil.nextDate());

		newWeDeployAuthApp.setName(RandomTestUtil.randomString());

		newWeDeployAuthApp.setClientId(RandomTestUtil.randomString());

		newWeDeployAuthApp.setClientSecret(RandomTestUtil.randomString());

		_weDeployAuthApps.add(_persistence.update(newWeDeployAuthApp));

		WeDeployAuthApp existingWeDeployAuthApp = _persistence.findByPrimaryKey(newWeDeployAuthApp.getPrimaryKey());

		Assert.assertEquals(existingWeDeployAuthApp.getWeDeployAuthAppId(),
			newWeDeployAuthApp.getWeDeployAuthAppId());
		Assert.assertEquals(existingWeDeployAuthApp.getCompanyId(),
			newWeDeployAuthApp.getCompanyId());
		Assert.assertEquals(existingWeDeployAuthApp.getUserId(),
			newWeDeployAuthApp.getUserId());
		Assert.assertEquals(existingWeDeployAuthApp.getUserName(),
			newWeDeployAuthApp.getUserName());
		Assert.assertEquals(Time.getShortTimestamp(
				existingWeDeployAuthApp.getCreateDate()),
			Time.getShortTimestamp(newWeDeployAuthApp.getCreateDate()));
		Assert.assertEquals(Time.getShortTimestamp(
				existingWeDeployAuthApp.getModifiedDate()),
			Time.getShortTimestamp(newWeDeployAuthApp.getModifiedDate()));
		Assert.assertEquals(existingWeDeployAuthApp.getName(),
			newWeDeployAuthApp.getName());
		Assert.assertEquals(existingWeDeployAuthApp.getClientId(),
			newWeDeployAuthApp.getClientId());
		Assert.assertEquals(existingWeDeployAuthApp.getClientSecret(),
			newWeDeployAuthApp.getClientSecret());
	}

	@Test
	public void testCountByCI_CS() throws Exception {
		_persistence.countByCI_CS(StringPool.BLANK, StringPool.BLANK);

		_persistence.countByCI_CS(StringPool.NULL, StringPool.NULL);

		_persistence.countByCI_CS((String)null, (String)null);
	}

	@Test
	public void testFindByPrimaryKeyExisting() throws Exception {
		WeDeployAuthApp newWeDeployAuthApp = addWeDeployAuthApp();

		WeDeployAuthApp existingWeDeployAuthApp = _persistence.findByPrimaryKey(newWeDeployAuthApp.getPrimaryKey());

		Assert.assertEquals(existingWeDeployAuthApp, newWeDeployAuthApp);
	}

	@Test(expected = NoSuchAppException.class)
	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		_persistence.findByPrimaryKey(pk);
	}

	@Test
	public void testFindAll() throws Exception {
		_persistence.findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			getOrderByComparator());
	}

	protected OrderByComparator<WeDeployAuthApp> getOrderByComparator() {
		return OrderByComparatorFactoryUtil.create("WeDeployAuth_WeDeployAuthApp",
			"weDeployAuthAppId", true, "companyId", true, "userId", true,
			"userName", true, "createDate", true, "modifiedDate", true, "name",
			true, "clientId", true, "clientSecret", true);
	}

	@Test
	public void testFetchByPrimaryKeyExisting() throws Exception {
		WeDeployAuthApp newWeDeployAuthApp = addWeDeployAuthApp();

		WeDeployAuthApp existingWeDeployAuthApp = _persistence.fetchByPrimaryKey(newWeDeployAuthApp.getPrimaryKey());

		Assert.assertEquals(existingWeDeployAuthApp, newWeDeployAuthApp);
	}

	@Test
	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		WeDeployAuthApp missingWeDeployAuthApp = _persistence.fetchByPrimaryKey(pk);

		Assert.assertNull(missingWeDeployAuthApp);
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereAllPrimaryKeysExist()
		throws Exception {
		WeDeployAuthApp newWeDeployAuthApp1 = addWeDeployAuthApp();
		WeDeployAuthApp newWeDeployAuthApp2 = addWeDeployAuthApp();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newWeDeployAuthApp1.getPrimaryKey());
		primaryKeys.add(newWeDeployAuthApp2.getPrimaryKey());

		Map<Serializable, WeDeployAuthApp> weDeployAuthApps = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(2, weDeployAuthApps.size());
		Assert.assertEquals(newWeDeployAuthApp1,
			weDeployAuthApps.get(newWeDeployAuthApp1.getPrimaryKey()));
		Assert.assertEquals(newWeDeployAuthApp2,
			weDeployAuthApps.get(newWeDeployAuthApp2.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereNoPrimaryKeysExist()
		throws Exception {
		long pk1 = RandomTestUtil.nextLong();

		long pk2 = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(pk1);
		primaryKeys.add(pk2);

		Map<Serializable, WeDeployAuthApp> weDeployAuthApps = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(weDeployAuthApps.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereSomePrimaryKeysExist()
		throws Exception {
		WeDeployAuthApp newWeDeployAuthApp = addWeDeployAuthApp();

		long pk = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newWeDeployAuthApp.getPrimaryKey());
		primaryKeys.add(pk);

		Map<Serializable, WeDeployAuthApp> weDeployAuthApps = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, weDeployAuthApps.size());
		Assert.assertEquals(newWeDeployAuthApp,
			weDeployAuthApps.get(newWeDeployAuthApp.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithNoPrimaryKeys()
		throws Exception {
		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		Map<Serializable, WeDeployAuthApp> weDeployAuthApps = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(weDeployAuthApps.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithOnePrimaryKey()
		throws Exception {
		WeDeployAuthApp newWeDeployAuthApp = addWeDeployAuthApp();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newWeDeployAuthApp.getPrimaryKey());

		Map<Serializable, WeDeployAuthApp> weDeployAuthApps = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, weDeployAuthApps.size());
		Assert.assertEquals(newWeDeployAuthApp,
			weDeployAuthApps.get(newWeDeployAuthApp.getPrimaryKey()));
	}

	@Test
	public void testActionableDynamicQuery() throws Exception {
		final IntegerWrapper count = new IntegerWrapper();

		ActionableDynamicQuery actionableDynamicQuery = WeDeployAuthAppLocalServiceUtil.getActionableDynamicQuery();

		actionableDynamicQuery.setPerformActionMethod(new ActionableDynamicQuery.PerformActionMethod<WeDeployAuthApp>() {
				@Override
				public void performAction(WeDeployAuthApp weDeployAuthApp) {
					Assert.assertNotNull(weDeployAuthApp);

					count.increment();
				}
			});

		actionableDynamicQuery.performActions();

		Assert.assertEquals(count.getValue(), _persistence.countAll());
	}

	@Test
	public void testDynamicQueryByPrimaryKeyExisting()
		throws Exception {
		WeDeployAuthApp newWeDeployAuthApp = addWeDeployAuthApp();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(WeDeployAuthApp.class,
				_dynamicQueryClassLoader);

		dynamicQuery.add(RestrictionsFactoryUtil.eq("weDeployAuthAppId",
				newWeDeployAuthApp.getWeDeployAuthAppId()));

		List<WeDeployAuthApp> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		WeDeployAuthApp existingWeDeployAuthApp = result.get(0);

		Assert.assertEquals(existingWeDeployAuthApp, newWeDeployAuthApp);
	}

	@Test
	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(WeDeployAuthApp.class,
				_dynamicQueryClassLoader);

		dynamicQuery.add(RestrictionsFactoryUtil.eq("weDeployAuthAppId",
				RandomTestUtil.nextLong()));

		List<WeDeployAuthApp> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testDynamicQueryByProjectionExisting()
		throws Exception {
		WeDeployAuthApp newWeDeployAuthApp = addWeDeployAuthApp();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(WeDeployAuthApp.class,
				_dynamicQueryClassLoader);

		dynamicQuery.setProjection(ProjectionFactoryUtil.property(
				"weDeployAuthAppId"));

		Object newWeDeployAuthAppId = newWeDeployAuthApp.getWeDeployAuthAppId();

		dynamicQuery.add(RestrictionsFactoryUtil.in("weDeployAuthAppId",
				new Object[] { newWeDeployAuthAppId }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		Object existingWeDeployAuthAppId = result.get(0);

		Assert.assertEquals(existingWeDeployAuthAppId, newWeDeployAuthAppId);
	}

	@Test
	public void testDynamicQueryByProjectionMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(WeDeployAuthApp.class,
				_dynamicQueryClassLoader);

		dynamicQuery.setProjection(ProjectionFactoryUtil.property(
				"weDeployAuthAppId"));

		dynamicQuery.add(RestrictionsFactoryUtil.in("weDeployAuthAppId",
				new Object[] { RandomTestUtil.nextLong() }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testResetOriginalValues() throws Exception {
		WeDeployAuthApp newWeDeployAuthApp = addWeDeployAuthApp();

		_persistence.clearCache();

		WeDeployAuthApp existingWeDeployAuthApp = _persistence.findByPrimaryKey(newWeDeployAuthApp.getPrimaryKey());

		Assert.assertTrue(Objects.equals(
				existingWeDeployAuthApp.getClientId(),
				ReflectionTestUtil.invoke(existingWeDeployAuthApp,
					"getOriginalClientId", new Class<?>[0])));
		Assert.assertTrue(Objects.equals(
				existingWeDeployAuthApp.getClientSecret(),
				ReflectionTestUtil.invoke(existingWeDeployAuthApp,
					"getOriginalClientSecret", new Class<?>[0])));
	}

	protected WeDeployAuthApp addWeDeployAuthApp() throws Exception {
		long pk = RandomTestUtil.nextLong();

		WeDeployAuthApp weDeployAuthApp = _persistence.create(pk);

		weDeployAuthApp.setCompanyId(RandomTestUtil.nextLong());

		weDeployAuthApp.setUserId(RandomTestUtil.nextLong());

		weDeployAuthApp.setUserName(RandomTestUtil.randomString());

		weDeployAuthApp.setCreateDate(RandomTestUtil.nextDate());

		weDeployAuthApp.setModifiedDate(RandomTestUtil.nextDate());

		weDeployAuthApp.setName(RandomTestUtil.randomString());

		weDeployAuthApp.setClientId(RandomTestUtil.randomString());

		weDeployAuthApp.setClientSecret(RandomTestUtil.randomString());

		_weDeployAuthApps.add(_persistence.update(weDeployAuthApp));

		return weDeployAuthApp;
	}

	private List<WeDeployAuthApp> _weDeployAuthApps = new ArrayList<WeDeployAuthApp>();
	private WeDeployAuthAppPersistence _persistence;
	private ClassLoader _dynamicQueryClassLoader;
}