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
import com.liferay.portal.security.wedeploy.auth.exception.NoSuchTokenException;
import com.liferay.portal.security.wedeploy.auth.model.WeDeployAuthToken;
import com.liferay.portal.security.wedeploy.auth.service.WeDeployAuthTokenLocalServiceUtil;
import com.liferay.portal.security.wedeploy.auth.service.persistence.WeDeployAuthTokenPersistence;
import com.liferay.portal.security.wedeploy.auth.service.persistence.WeDeployAuthTokenUtil;
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
public class WeDeployAuthTokenPersistenceTest {
	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule = new AggregateTestRule(new LiferayIntegrationTestRule(),
			PersistenceTestRule.INSTANCE,
			new TransactionalTestRule(Propagation.REQUIRED,
				"com.liferay.portal.security.wedeploy.auth.service"));

	@Before
	public void setUp() {
		_persistence = WeDeployAuthTokenUtil.getPersistence();

		Class<?> clazz = _persistence.getClass();

		_dynamicQueryClassLoader = clazz.getClassLoader();
	}

	@After
	public void tearDown() throws Exception {
		Iterator<WeDeployAuthToken> iterator = _weDeployAuthTokens.iterator();

		while (iterator.hasNext()) {
			_persistence.remove(iterator.next());

			iterator.remove();
		}
	}

	@Test
	public void testCreate() throws Exception {
		long pk = RandomTestUtil.nextLong();

		WeDeployAuthToken weDeployAuthToken = _persistence.create(pk);

		Assert.assertNotNull(weDeployAuthToken);

		Assert.assertEquals(weDeployAuthToken.getPrimaryKey(), pk);
	}

	@Test
	public void testRemove() throws Exception {
		WeDeployAuthToken newWeDeployAuthToken = addWeDeployAuthToken();

		_persistence.remove(newWeDeployAuthToken);

		WeDeployAuthToken existingWeDeployAuthToken = _persistence.fetchByPrimaryKey(newWeDeployAuthToken.getPrimaryKey());

		Assert.assertNull(existingWeDeployAuthToken);
	}

	@Test
	public void testUpdateNew() throws Exception {
		addWeDeployAuthToken();
	}

	@Test
	public void testUpdateExisting() throws Exception {
		long pk = RandomTestUtil.nextLong();

		WeDeployAuthToken newWeDeployAuthToken = _persistence.create(pk);

		newWeDeployAuthToken.setCompanyId(RandomTestUtil.nextLong());

		newWeDeployAuthToken.setUserId(RandomTestUtil.nextLong());

		newWeDeployAuthToken.setUserName(RandomTestUtil.randomString());

		newWeDeployAuthToken.setCreateDate(RandomTestUtil.nextDate());

		newWeDeployAuthToken.setModifiedDate(RandomTestUtil.nextDate());

		newWeDeployAuthToken.setClientId(RandomTestUtil.randomString());

		newWeDeployAuthToken.setToken(RandomTestUtil.randomString());

		newWeDeployAuthToken.setType(RandomTestUtil.nextInt());

		_weDeployAuthTokens.add(_persistence.update(newWeDeployAuthToken));

		WeDeployAuthToken existingWeDeployAuthToken = _persistence.findByPrimaryKey(newWeDeployAuthToken.getPrimaryKey());

		Assert.assertEquals(existingWeDeployAuthToken.getWeDeployAuthTokenId(),
			newWeDeployAuthToken.getWeDeployAuthTokenId());
		Assert.assertEquals(existingWeDeployAuthToken.getCompanyId(),
			newWeDeployAuthToken.getCompanyId());
		Assert.assertEquals(existingWeDeployAuthToken.getUserId(),
			newWeDeployAuthToken.getUserId());
		Assert.assertEquals(existingWeDeployAuthToken.getUserName(),
			newWeDeployAuthToken.getUserName());
		Assert.assertEquals(Time.getShortTimestamp(
				existingWeDeployAuthToken.getCreateDate()),
			Time.getShortTimestamp(newWeDeployAuthToken.getCreateDate()));
		Assert.assertEquals(Time.getShortTimestamp(
				existingWeDeployAuthToken.getModifiedDate()),
			Time.getShortTimestamp(newWeDeployAuthToken.getModifiedDate()));
		Assert.assertEquals(existingWeDeployAuthToken.getClientId(),
			newWeDeployAuthToken.getClientId());
		Assert.assertEquals(existingWeDeployAuthToken.getToken(),
			newWeDeployAuthToken.getToken());
		Assert.assertEquals(existingWeDeployAuthToken.getType(),
			newWeDeployAuthToken.getType());
	}

	@Test
	public void testCountByT_T() throws Exception {
		_persistence.countByT_T(StringPool.BLANK, RandomTestUtil.nextInt());

		_persistence.countByT_T(StringPool.NULL, 0);

		_persistence.countByT_T((String)null, 0);
	}

	@Test
	public void testCountByCI_T_T() throws Exception {
		_persistence.countByCI_T_T(StringPool.BLANK, StringPool.BLANK,
			RandomTestUtil.nextInt());

		_persistence.countByCI_T_T(StringPool.NULL, StringPool.NULL, 0);

		_persistence.countByCI_T_T((String)null, (String)null, 0);
	}

	@Test
	public void testFindByPrimaryKeyExisting() throws Exception {
		WeDeployAuthToken newWeDeployAuthToken = addWeDeployAuthToken();

		WeDeployAuthToken existingWeDeployAuthToken = _persistence.findByPrimaryKey(newWeDeployAuthToken.getPrimaryKey());

		Assert.assertEquals(existingWeDeployAuthToken, newWeDeployAuthToken);
	}

	@Test(expected = NoSuchTokenException.class)
	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		_persistence.findByPrimaryKey(pk);
	}

	@Test
	public void testFindAll() throws Exception {
		_persistence.findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			getOrderByComparator());
	}

	protected OrderByComparator<WeDeployAuthToken> getOrderByComparator() {
		return OrderByComparatorFactoryUtil.create("WeDeployAuth_WeDeployAuthToken",
			"weDeployAuthTokenId", true, "companyId", true, "userId", true,
			"userName", true, "createDate", true, "modifiedDate", true,
			"clientId", true, "token", true, "type", true);
	}

	@Test
	public void testFetchByPrimaryKeyExisting() throws Exception {
		WeDeployAuthToken newWeDeployAuthToken = addWeDeployAuthToken();

		WeDeployAuthToken existingWeDeployAuthToken = _persistence.fetchByPrimaryKey(newWeDeployAuthToken.getPrimaryKey());

		Assert.assertEquals(existingWeDeployAuthToken, newWeDeployAuthToken);
	}

	@Test
	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		WeDeployAuthToken missingWeDeployAuthToken = _persistence.fetchByPrimaryKey(pk);

		Assert.assertNull(missingWeDeployAuthToken);
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereAllPrimaryKeysExist()
		throws Exception {
		WeDeployAuthToken newWeDeployAuthToken1 = addWeDeployAuthToken();
		WeDeployAuthToken newWeDeployAuthToken2 = addWeDeployAuthToken();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newWeDeployAuthToken1.getPrimaryKey());
		primaryKeys.add(newWeDeployAuthToken2.getPrimaryKey());

		Map<Serializable, WeDeployAuthToken> weDeployAuthTokens = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(2, weDeployAuthTokens.size());
		Assert.assertEquals(newWeDeployAuthToken1,
			weDeployAuthTokens.get(newWeDeployAuthToken1.getPrimaryKey()));
		Assert.assertEquals(newWeDeployAuthToken2,
			weDeployAuthTokens.get(newWeDeployAuthToken2.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereNoPrimaryKeysExist()
		throws Exception {
		long pk1 = RandomTestUtil.nextLong();

		long pk2 = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(pk1);
		primaryKeys.add(pk2);

		Map<Serializable, WeDeployAuthToken> weDeployAuthTokens = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(weDeployAuthTokens.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereSomePrimaryKeysExist()
		throws Exception {
		WeDeployAuthToken newWeDeployAuthToken = addWeDeployAuthToken();

		long pk = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newWeDeployAuthToken.getPrimaryKey());
		primaryKeys.add(pk);

		Map<Serializable, WeDeployAuthToken> weDeployAuthTokens = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, weDeployAuthTokens.size());
		Assert.assertEquals(newWeDeployAuthToken,
			weDeployAuthTokens.get(newWeDeployAuthToken.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithNoPrimaryKeys()
		throws Exception {
		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		Map<Serializable, WeDeployAuthToken> weDeployAuthTokens = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(weDeployAuthTokens.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithOnePrimaryKey()
		throws Exception {
		WeDeployAuthToken newWeDeployAuthToken = addWeDeployAuthToken();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newWeDeployAuthToken.getPrimaryKey());

		Map<Serializable, WeDeployAuthToken> weDeployAuthTokens = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, weDeployAuthTokens.size());
		Assert.assertEquals(newWeDeployAuthToken,
			weDeployAuthTokens.get(newWeDeployAuthToken.getPrimaryKey()));
	}

	@Test
	public void testActionableDynamicQuery() throws Exception {
		final IntegerWrapper count = new IntegerWrapper();

		ActionableDynamicQuery actionableDynamicQuery = WeDeployAuthTokenLocalServiceUtil.getActionableDynamicQuery();

		actionableDynamicQuery.setPerformActionMethod(new ActionableDynamicQuery.PerformActionMethod<WeDeployAuthToken>() {
				@Override
				public void performAction(WeDeployAuthToken weDeployAuthToken) {
					Assert.assertNotNull(weDeployAuthToken);

					count.increment();
				}
			});

		actionableDynamicQuery.performActions();

		Assert.assertEquals(count.getValue(), _persistence.countAll());
	}

	@Test
	public void testDynamicQueryByPrimaryKeyExisting()
		throws Exception {
		WeDeployAuthToken newWeDeployAuthToken = addWeDeployAuthToken();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(WeDeployAuthToken.class,
				_dynamicQueryClassLoader);

		dynamicQuery.add(RestrictionsFactoryUtil.eq("weDeployAuthTokenId",
				newWeDeployAuthToken.getWeDeployAuthTokenId()));

		List<WeDeployAuthToken> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		WeDeployAuthToken existingWeDeployAuthToken = result.get(0);

		Assert.assertEquals(existingWeDeployAuthToken, newWeDeployAuthToken);
	}

	@Test
	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(WeDeployAuthToken.class,
				_dynamicQueryClassLoader);

		dynamicQuery.add(RestrictionsFactoryUtil.eq("weDeployAuthTokenId",
				RandomTestUtil.nextLong()));

		List<WeDeployAuthToken> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testDynamicQueryByProjectionExisting()
		throws Exception {
		WeDeployAuthToken newWeDeployAuthToken = addWeDeployAuthToken();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(WeDeployAuthToken.class,
				_dynamicQueryClassLoader);

		dynamicQuery.setProjection(ProjectionFactoryUtil.property(
				"weDeployAuthTokenId"));

		Object newWeDeployAuthTokenId = newWeDeployAuthToken.getWeDeployAuthTokenId();

		dynamicQuery.add(RestrictionsFactoryUtil.in("weDeployAuthTokenId",
				new Object[] { newWeDeployAuthTokenId }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		Object existingWeDeployAuthTokenId = result.get(0);

		Assert.assertEquals(existingWeDeployAuthTokenId, newWeDeployAuthTokenId);
	}

	@Test
	public void testDynamicQueryByProjectionMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(WeDeployAuthToken.class,
				_dynamicQueryClassLoader);

		dynamicQuery.setProjection(ProjectionFactoryUtil.property(
				"weDeployAuthTokenId"));

		dynamicQuery.add(RestrictionsFactoryUtil.in("weDeployAuthTokenId",
				new Object[] { RandomTestUtil.nextLong() }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testResetOriginalValues() throws Exception {
		WeDeployAuthToken newWeDeployAuthToken = addWeDeployAuthToken();

		_persistence.clearCache();

		WeDeployAuthToken existingWeDeployAuthToken = _persistence.findByPrimaryKey(newWeDeployAuthToken.getPrimaryKey());

		Assert.assertTrue(Objects.equals(existingWeDeployAuthToken.getToken(),
				ReflectionTestUtil.invoke(existingWeDeployAuthToken,
					"getOriginalToken", new Class<?>[0])));
		Assert.assertEquals(Integer.valueOf(existingWeDeployAuthToken.getType()),
			ReflectionTestUtil.<Integer>invoke(existingWeDeployAuthToken,
				"getOriginalType", new Class<?>[0]));

		Assert.assertTrue(Objects.equals(
				existingWeDeployAuthToken.getClientId(),
				ReflectionTestUtil.invoke(existingWeDeployAuthToken,
					"getOriginalClientId", new Class<?>[0])));
		Assert.assertTrue(Objects.equals(existingWeDeployAuthToken.getToken(),
				ReflectionTestUtil.invoke(existingWeDeployAuthToken,
					"getOriginalToken", new Class<?>[0])));
		Assert.assertEquals(Integer.valueOf(existingWeDeployAuthToken.getType()),
			ReflectionTestUtil.<Integer>invoke(existingWeDeployAuthToken,
				"getOriginalType", new Class<?>[0]));
	}

	protected WeDeployAuthToken addWeDeployAuthToken()
		throws Exception {
		long pk = RandomTestUtil.nextLong();

		WeDeployAuthToken weDeployAuthToken = _persistence.create(pk);

		weDeployAuthToken.setCompanyId(RandomTestUtil.nextLong());

		weDeployAuthToken.setUserId(RandomTestUtil.nextLong());

		weDeployAuthToken.setUserName(RandomTestUtil.randomString());

		weDeployAuthToken.setCreateDate(RandomTestUtil.nextDate());

		weDeployAuthToken.setModifiedDate(RandomTestUtil.nextDate());

		weDeployAuthToken.setClientId(RandomTestUtil.randomString());

		weDeployAuthToken.setToken(RandomTestUtil.randomString());

		weDeployAuthToken.setType(RandomTestUtil.nextInt());

		_weDeployAuthTokens.add(_persistence.update(weDeployAuthToken));

		return weDeployAuthToken;
	}

	private List<WeDeployAuthToken> _weDeployAuthTokens = new ArrayList<WeDeployAuthToken>();
	private WeDeployAuthTokenPersistence _persistence;
	private ClassLoader _dynamicQueryClassLoader;
}