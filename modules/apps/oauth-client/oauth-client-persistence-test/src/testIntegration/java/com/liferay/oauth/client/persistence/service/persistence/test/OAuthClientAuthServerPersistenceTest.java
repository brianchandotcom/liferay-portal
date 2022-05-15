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

package com.liferay.oauth.client.persistence.service.persistence.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.oauth.client.persistence.exception.NoSuchOAuthClientAuthServerException;
import com.liferay.oauth.client.persistence.model.OAuthClientAuthServer;
import com.liferay.oauth.client.persistence.service.OAuthClientAuthServerLocalServiceUtil;
import com.liferay.oauth.client.persistence.service.persistence.OAuthClientAuthServerPersistence;
import com.liferay.oauth.client.persistence.service.persistence.OAuthClientAuthServerUtil;
import com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQueryFactoryUtil;
import com.liferay.portal.kernel.dao.orm.ProjectionFactoryUtil;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.RestrictionsFactoryUtil;
import com.liferay.portal.kernel.dao.orm.Session;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.transaction.Propagation;
import com.liferay.portal.kernel.util.IntegerWrapper;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.OrderByComparatorFactoryUtil;
import com.liferay.portal.kernel.util.Time;
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
public class OAuthClientAuthServerPersistenceTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(), PersistenceTestRule.INSTANCE,
			new TransactionalTestRule(
				Propagation.REQUIRED,
				"com.liferay.oauth.client.persistence.service"));

	@Before
	public void setUp() {
		_persistence = OAuthClientAuthServerUtil.getPersistence();

		Class<?> clazz = _persistence.getClass();

		_dynamicQueryClassLoader = clazz.getClassLoader();
	}

	@After
	public void tearDown() throws Exception {
		Iterator<OAuthClientAuthServer> iterator =
			_oAuthClientAuthServers.iterator();

		while (iterator.hasNext()) {
			_persistence.remove(iterator.next());

			iterator.remove();
		}
	}

	@Test
	public void testCreate() throws Exception {
		long pk = RandomTestUtil.nextLong();

		OAuthClientAuthServer oAuthClientAuthServer = _persistence.create(pk);

		Assert.assertNotNull(oAuthClientAuthServer);

		Assert.assertEquals(oAuthClientAuthServer.getPrimaryKey(), pk);
	}

	@Test
	public void testRemove() throws Exception {
		OAuthClientAuthServer newOAuthClientAuthServer =
			addOAuthClientAuthServer();

		_persistence.remove(newOAuthClientAuthServer);

		OAuthClientAuthServer existingOAuthClientAuthServer =
			_persistence.fetchByPrimaryKey(
				newOAuthClientAuthServer.getPrimaryKey());

		Assert.assertNull(existingOAuthClientAuthServer);
	}

	@Test
	public void testUpdateNew() throws Exception {
		addOAuthClientAuthServer();
	}

	@Test
	public void testUpdateExisting() throws Exception {
		long pk = RandomTestUtil.nextLong();

		OAuthClientAuthServer newOAuthClientAuthServer = _persistence.create(
			pk);

		newOAuthClientAuthServer.setMvccVersion(RandomTestUtil.nextLong());

		newOAuthClientAuthServer.setCompanyId(RandomTestUtil.nextLong());

		newOAuthClientAuthServer.setUserId(RandomTestUtil.nextLong());

		newOAuthClientAuthServer.setUserName(RandomTestUtil.randomString());

		newOAuthClientAuthServer.setCreateDate(RandomTestUtil.nextDate());

		newOAuthClientAuthServer.setModifiedDate(RandomTestUtil.nextDate());

		newOAuthClientAuthServer.setDiscoveryEndpoint(
			RandomTestUtil.randomString());

		newOAuthClientAuthServer.setIssuer(RandomTestUtil.randomString());

		newOAuthClientAuthServer.setMetadataJSON(RandomTestUtil.randomString());

		newOAuthClientAuthServer.setType(RandomTestUtil.randomString());

		_oAuthClientAuthServers.add(
			_persistence.update(newOAuthClientAuthServer));

		OAuthClientAuthServer existingOAuthClientAuthServer =
			_persistence.findByPrimaryKey(
				newOAuthClientAuthServer.getPrimaryKey());

		Assert.assertEquals(
			existingOAuthClientAuthServer.getMvccVersion(),
			newOAuthClientAuthServer.getMvccVersion());
		Assert.assertEquals(
			existingOAuthClientAuthServer.getOAuthClientAuthServerId(),
			newOAuthClientAuthServer.getOAuthClientAuthServerId());
		Assert.assertEquals(
			existingOAuthClientAuthServer.getCompanyId(),
			newOAuthClientAuthServer.getCompanyId());
		Assert.assertEquals(
			existingOAuthClientAuthServer.getUserId(),
			newOAuthClientAuthServer.getUserId());
		Assert.assertEquals(
			existingOAuthClientAuthServer.getUserName(),
			newOAuthClientAuthServer.getUserName());
		Assert.assertEquals(
			Time.getShortTimestamp(
				existingOAuthClientAuthServer.getCreateDate()),
			Time.getShortTimestamp(newOAuthClientAuthServer.getCreateDate()));
		Assert.assertEquals(
			Time.getShortTimestamp(
				existingOAuthClientAuthServer.getModifiedDate()),
			Time.getShortTimestamp(newOAuthClientAuthServer.getModifiedDate()));
		Assert.assertEquals(
			existingOAuthClientAuthServer.getDiscoveryEndpoint(),
			newOAuthClientAuthServer.getDiscoveryEndpoint());
		Assert.assertEquals(
			existingOAuthClientAuthServer.getIssuer(),
			newOAuthClientAuthServer.getIssuer());
		Assert.assertEquals(
			existingOAuthClientAuthServer.getMetadataJSON(),
			newOAuthClientAuthServer.getMetadataJSON());
		Assert.assertEquals(
			existingOAuthClientAuthServer.getType(),
			newOAuthClientAuthServer.getType());
	}

	@Test
	public void testCountByCompanyId() throws Exception {
		_persistence.countByCompanyId(RandomTestUtil.nextLong());

		_persistence.countByCompanyId(0L);
	}

	@Test
	public void testCountByUserId() throws Exception {
		_persistence.countByUserId(RandomTestUtil.nextLong());

		_persistence.countByUserId(0L);
	}

	@Test
	public void testCountByC_I() throws Exception {
		_persistence.countByC_I(RandomTestUtil.nextLong(), "");

		_persistence.countByC_I(0L, "null");

		_persistence.countByC_I(0L, (String)null);
	}

	@Test
	public void testCountByC_T() throws Exception {
		_persistence.countByC_T(RandomTestUtil.nextLong(), "");

		_persistence.countByC_T(0L, "null");

		_persistence.countByC_T(0L, (String)null);
	}

	@Test
	public void testFindByPrimaryKeyExisting() throws Exception {
		OAuthClientAuthServer newOAuthClientAuthServer =
			addOAuthClientAuthServer();

		OAuthClientAuthServer existingOAuthClientAuthServer =
			_persistence.findByPrimaryKey(
				newOAuthClientAuthServer.getPrimaryKey());

		Assert.assertEquals(
			existingOAuthClientAuthServer, newOAuthClientAuthServer);
	}

	@Test(expected = NoSuchOAuthClientAuthServerException.class)
	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		_persistence.findByPrimaryKey(pk);
	}

	@Test
	public void testFindAll() throws Exception {
		_persistence.findAll(
			QueryUtil.ALL_POS, QueryUtil.ALL_POS, getOrderByComparator());
	}

	protected OrderByComparator<OAuthClientAuthServer> getOrderByComparator() {
		return OrderByComparatorFactoryUtil.create(
			"OAuthClientAuthServer", "mvccVersion", true,
			"oAuthClientAuthServerId", true, "companyId", true, "userId", true,
			"userName", true, "createDate", true, "modifiedDate", true,
			"discoveryEndpoint", true, "issuer", true, "type", true);
	}

	@Test
	public void testFetchByPrimaryKeyExisting() throws Exception {
		OAuthClientAuthServer newOAuthClientAuthServer =
			addOAuthClientAuthServer();

		OAuthClientAuthServer existingOAuthClientAuthServer =
			_persistence.fetchByPrimaryKey(
				newOAuthClientAuthServer.getPrimaryKey());

		Assert.assertEquals(
			existingOAuthClientAuthServer, newOAuthClientAuthServer);
	}

	@Test
	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		OAuthClientAuthServer missingOAuthClientAuthServer =
			_persistence.fetchByPrimaryKey(pk);

		Assert.assertNull(missingOAuthClientAuthServer);
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereAllPrimaryKeysExist()
		throws Exception {

		OAuthClientAuthServer newOAuthClientAuthServer1 =
			addOAuthClientAuthServer();
		OAuthClientAuthServer newOAuthClientAuthServer2 =
			addOAuthClientAuthServer();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newOAuthClientAuthServer1.getPrimaryKey());
		primaryKeys.add(newOAuthClientAuthServer2.getPrimaryKey());

		Map<Serializable, OAuthClientAuthServer> oAuthClientAuthServers =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(2, oAuthClientAuthServers.size());
		Assert.assertEquals(
			newOAuthClientAuthServer1,
			oAuthClientAuthServers.get(
				newOAuthClientAuthServer1.getPrimaryKey()));
		Assert.assertEquals(
			newOAuthClientAuthServer2,
			oAuthClientAuthServers.get(
				newOAuthClientAuthServer2.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereNoPrimaryKeysExist()
		throws Exception {

		long pk1 = RandomTestUtil.nextLong();

		long pk2 = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(pk1);
		primaryKeys.add(pk2);

		Map<Serializable, OAuthClientAuthServer> oAuthClientAuthServers =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(oAuthClientAuthServers.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereSomePrimaryKeysExist()
		throws Exception {

		OAuthClientAuthServer newOAuthClientAuthServer =
			addOAuthClientAuthServer();

		long pk = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newOAuthClientAuthServer.getPrimaryKey());
		primaryKeys.add(pk);

		Map<Serializable, OAuthClientAuthServer> oAuthClientAuthServers =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, oAuthClientAuthServers.size());
		Assert.assertEquals(
			newOAuthClientAuthServer,
			oAuthClientAuthServers.get(
				newOAuthClientAuthServer.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithNoPrimaryKeys() throws Exception {
		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		Map<Serializable, OAuthClientAuthServer> oAuthClientAuthServers =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(oAuthClientAuthServers.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithOnePrimaryKey() throws Exception {
		OAuthClientAuthServer newOAuthClientAuthServer =
			addOAuthClientAuthServer();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newOAuthClientAuthServer.getPrimaryKey());

		Map<Serializable, OAuthClientAuthServer> oAuthClientAuthServers =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, oAuthClientAuthServers.size());
		Assert.assertEquals(
			newOAuthClientAuthServer,
			oAuthClientAuthServers.get(
				newOAuthClientAuthServer.getPrimaryKey()));
	}

	@Test
	public void testActionableDynamicQuery() throws Exception {
		final IntegerWrapper count = new IntegerWrapper();

		ActionableDynamicQuery actionableDynamicQuery =
			OAuthClientAuthServerLocalServiceUtil.getActionableDynamicQuery();

		actionableDynamicQuery.setPerformActionMethod(
			new ActionableDynamicQuery.PerformActionMethod
				<OAuthClientAuthServer>() {

				@Override
				public void performAction(
					OAuthClientAuthServer oAuthClientAuthServer) {

					Assert.assertNotNull(oAuthClientAuthServer);

					count.increment();
				}

			});

		actionableDynamicQuery.performActions();

		Assert.assertEquals(count.getValue(), _persistence.countAll());
	}

	@Test
	public void testDynamicQueryByPrimaryKeyExisting() throws Exception {
		OAuthClientAuthServer newOAuthClientAuthServer =
			addOAuthClientAuthServer();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			OAuthClientAuthServer.class, _dynamicQueryClassLoader);

		dynamicQuery.add(
			RestrictionsFactoryUtil.eq(
				"oAuthClientAuthServerId",
				newOAuthClientAuthServer.getOAuthClientAuthServerId()));

		List<OAuthClientAuthServer> result = _persistence.findWithDynamicQuery(
			dynamicQuery);

		Assert.assertEquals(1, result.size());

		OAuthClientAuthServer existingOAuthClientAuthServer = result.get(0);

		Assert.assertEquals(
			existingOAuthClientAuthServer, newOAuthClientAuthServer);
	}

	@Test
	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			OAuthClientAuthServer.class, _dynamicQueryClassLoader);

		dynamicQuery.add(
			RestrictionsFactoryUtil.eq(
				"oAuthClientAuthServerId", RandomTestUtil.nextLong()));

		List<OAuthClientAuthServer> result = _persistence.findWithDynamicQuery(
			dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testDynamicQueryByProjectionExisting() throws Exception {
		OAuthClientAuthServer newOAuthClientAuthServer =
			addOAuthClientAuthServer();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			OAuthClientAuthServer.class, _dynamicQueryClassLoader);

		dynamicQuery.setProjection(
			ProjectionFactoryUtil.property("oAuthClientAuthServerId"));

		Object newOAuthClientAuthServerId =
			newOAuthClientAuthServer.getOAuthClientAuthServerId();

		dynamicQuery.add(
			RestrictionsFactoryUtil.in(
				"oAuthClientAuthServerId",
				new Object[] {newOAuthClientAuthServerId}));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		Object existingOAuthClientAuthServerId = result.get(0);

		Assert.assertEquals(
			existingOAuthClientAuthServerId, newOAuthClientAuthServerId);
	}

	@Test
	public void testDynamicQueryByProjectionMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			OAuthClientAuthServer.class, _dynamicQueryClassLoader);

		dynamicQuery.setProjection(
			ProjectionFactoryUtil.property("oAuthClientAuthServerId"));

		dynamicQuery.add(
			RestrictionsFactoryUtil.in(
				"oAuthClientAuthServerId",
				new Object[] {RandomTestUtil.nextLong()}));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testResetOriginalValues() throws Exception {
		OAuthClientAuthServer newOAuthClientAuthServer =
			addOAuthClientAuthServer();

		_persistence.clearCache();

		_assertOriginalValues(
			_persistence.findByPrimaryKey(
				newOAuthClientAuthServer.getPrimaryKey()));
	}

	@Test
	public void testResetOriginalValuesWithDynamicQueryLoadFromDatabase()
		throws Exception {

		_testResetOriginalValuesWithDynamicQuery(true);
	}

	@Test
	public void testResetOriginalValuesWithDynamicQueryLoadFromSession()
		throws Exception {

		_testResetOriginalValuesWithDynamicQuery(false);
	}

	private void _testResetOriginalValuesWithDynamicQuery(boolean clearSession)
		throws Exception {

		OAuthClientAuthServer newOAuthClientAuthServer =
			addOAuthClientAuthServer();

		if (clearSession) {
			Session session = _persistence.openSession();

			session.flush();

			session.clear();
		}

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			OAuthClientAuthServer.class, _dynamicQueryClassLoader);

		dynamicQuery.add(
			RestrictionsFactoryUtil.eq(
				"oAuthClientAuthServerId",
				newOAuthClientAuthServer.getOAuthClientAuthServerId()));

		List<OAuthClientAuthServer> result = _persistence.findWithDynamicQuery(
			dynamicQuery);

		_assertOriginalValues(result.get(0));
	}

	private void _assertOriginalValues(
		OAuthClientAuthServer oAuthClientAuthServer) {

		Assert.assertEquals(
			Long.valueOf(oAuthClientAuthServer.getCompanyId()),
			ReflectionTestUtil.<Long>invoke(
				oAuthClientAuthServer, "getColumnOriginalValue",
				new Class<?>[] {String.class}, "companyId"));
		Assert.assertEquals(
			oAuthClientAuthServer.getIssuer(),
			ReflectionTestUtil.invoke(
				oAuthClientAuthServer, "getColumnOriginalValue",
				new Class<?>[] {String.class}, "issuer"));
	}

	protected OAuthClientAuthServer addOAuthClientAuthServer()
		throws Exception {

		long pk = RandomTestUtil.nextLong();

		OAuthClientAuthServer oAuthClientAuthServer = _persistence.create(pk);

		oAuthClientAuthServer.setMvccVersion(RandomTestUtil.nextLong());

		oAuthClientAuthServer.setCompanyId(RandomTestUtil.nextLong());

		oAuthClientAuthServer.setUserId(RandomTestUtil.nextLong());

		oAuthClientAuthServer.setUserName(RandomTestUtil.randomString());

		oAuthClientAuthServer.setCreateDate(RandomTestUtil.nextDate());

		oAuthClientAuthServer.setModifiedDate(RandomTestUtil.nextDate());

		oAuthClientAuthServer.setDiscoveryEndpoint(
			RandomTestUtil.randomString());

		oAuthClientAuthServer.setIssuer(RandomTestUtil.randomString());

		oAuthClientAuthServer.setMetadataJSON(RandomTestUtil.randomString());

		oAuthClientAuthServer.setType(RandomTestUtil.randomString());

		_oAuthClientAuthServers.add(_persistence.update(oAuthClientAuthServer));

		return oAuthClientAuthServer;
	}

	private List<OAuthClientAuthServer> _oAuthClientAuthServers =
		new ArrayList<OAuthClientAuthServer>();
	private OAuthClientAuthServerPersistence _persistence;
	private ClassLoader _dynamicQueryClassLoader;

}