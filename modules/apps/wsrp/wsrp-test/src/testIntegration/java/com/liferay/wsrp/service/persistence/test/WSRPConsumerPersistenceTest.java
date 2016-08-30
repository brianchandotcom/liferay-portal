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

package com.liferay.wsrp.service.persistence.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;

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
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Time;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PersistenceTestRule;

import com.liferay.wsrp.exception.NoSuchConsumerException;
import com.liferay.wsrp.model.WSRPConsumer;
import com.liferay.wsrp.service.WSRPConsumerLocalServiceUtil;
import com.liferay.wsrp.service.persistence.WSRPConsumerPersistence;
import com.liferay.wsrp.service.persistence.WSRPConsumerUtil;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;

import org.junit.runner.RunWith;

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
@Ignore
@RunWith(Arquillian.class)
public class WSRPConsumerPersistenceTest {
	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule = new AggregateTestRule(new LiferayIntegrationTestRule(),
			PersistenceTestRule.INSTANCE,
			new TransactionalTestRule(Propagation.REQUIRED));

	@Before
	public void setUp() {
		_persistence = WSRPConsumerUtil.getPersistence();

		Class<?> clazz = _persistence.getClass();

		_dynamicQueryClassLoader = clazz.getClassLoader();
	}

	@After
	public void tearDown() throws Exception {
		Iterator<WSRPConsumer> iterator = _wsrpConsumers.iterator();

		while (iterator.hasNext()) {
			_persistence.remove(iterator.next());

			iterator.remove();
		}
	}

	@Test
	public void testCreate() throws Exception {
		long pk = RandomTestUtil.nextLong();

		WSRPConsumer wsrpConsumer = _persistence.create(pk);

		Assert.assertNotNull(wsrpConsumer);

		Assert.assertEquals(wsrpConsumer.getPrimaryKey(), pk);
	}

	@Test
	public void testRemove() throws Exception {
		WSRPConsumer newWSRPConsumer = addWSRPConsumer();

		_persistence.remove(newWSRPConsumer);

		WSRPConsumer existingWSRPConsumer = _persistence.fetchByPrimaryKey(newWSRPConsumer.getPrimaryKey());

		Assert.assertNull(existingWSRPConsumer);
	}

	@Test
	public void testUpdateNew() throws Exception {
		addWSRPConsumer();
	}

	@Test
	public void testUpdateExisting() throws Exception {
		long pk = RandomTestUtil.nextLong();

		WSRPConsumer newWSRPConsumer = _persistence.create(pk);

		newWSRPConsumer.setUuid(RandomTestUtil.randomString());

		newWSRPConsumer.setCompanyId(RandomTestUtil.nextLong());

		newWSRPConsumer.setCreateDate(RandomTestUtil.nextDate());

		newWSRPConsumer.setModifiedDate(RandomTestUtil.nextDate());

		newWSRPConsumer.setName(RandomTestUtil.randomString());

		newWSRPConsumer.setUrl(RandomTestUtil.randomString());

		newWSRPConsumer.setWsdl(RandomTestUtil.randomString());

		newWSRPConsumer.setRegistrationContextString(RandomTestUtil.randomString());

		newWSRPConsumer.setRegistrationPropertiesString(RandomTestUtil.randomString());

		newWSRPConsumer.setForwardCookies(RandomTestUtil.randomString());

		newWSRPConsumer.setForwardHeaders(RandomTestUtil.randomString());

		newWSRPConsumer.setMarkupCharacterSets(RandomTestUtil.randomString());

		newWSRPConsumer.setLastPublishDate(RandomTestUtil.nextDate());

		_wsrpConsumers.add(_persistence.update(newWSRPConsumer));

		WSRPConsumer existingWSRPConsumer = _persistence.findByPrimaryKey(newWSRPConsumer.getPrimaryKey());

		Assert.assertEquals(existingWSRPConsumer.getUuid(),
			newWSRPConsumer.getUuid());
		Assert.assertEquals(existingWSRPConsumer.getWsrpConsumerId(),
			newWSRPConsumer.getWsrpConsumerId());
		Assert.assertEquals(existingWSRPConsumer.getCompanyId(),
			newWSRPConsumer.getCompanyId());
		Assert.assertEquals(Time.getShortTimestamp(
				existingWSRPConsumer.getCreateDate()),
			Time.getShortTimestamp(newWSRPConsumer.getCreateDate()));
		Assert.assertEquals(Time.getShortTimestamp(
				existingWSRPConsumer.getModifiedDate()),
			Time.getShortTimestamp(newWSRPConsumer.getModifiedDate()));
		Assert.assertEquals(existingWSRPConsumer.getName(),
			newWSRPConsumer.getName());
		Assert.assertEquals(existingWSRPConsumer.getUrl(),
			newWSRPConsumer.getUrl());
		Assert.assertEquals(existingWSRPConsumer.getWsdl(),
			newWSRPConsumer.getWsdl());
		Assert.assertEquals(existingWSRPConsumer.getRegistrationContextString(),
			newWSRPConsumer.getRegistrationContextString());
		Assert.assertEquals(existingWSRPConsumer.getRegistrationPropertiesString(),
			newWSRPConsumer.getRegistrationPropertiesString());
		Assert.assertEquals(existingWSRPConsumer.getForwardCookies(),
			newWSRPConsumer.getForwardCookies());
		Assert.assertEquals(existingWSRPConsumer.getForwardHeaders(),
			newWSRPConsumer.getForwardHeaders());
		Assert.assertEquals(existingWSRPConsumer.getMarkupCharacterSets(),
			newWSRPConsumer.getMarkupCharacterSets());
		Assert.assertEquals(Time.getShortTimestamp(
				existingWSRPConsumer.getLastPublishDate()),
			Time.getShortTimestamp(newWSRPConsumer.getLastPublishDate()));
	}

	@Test
	public void testCountByUuid() throws Exception {
		_persistence.countByUuid(StringPool.BLANK);

		_persistence.countByUuid(StringPool.NULL);

		_persistence.countByUuid((String)null);
	}

	@Test
	public void testCountByUuid_C() throws Exception {
		_persistence.countByUuid_C(StringPool.BLANK, RandomTestUtil.nextLong());

		_persistence.countByUuid_C(StringPool.NULL, 0L);

		_persistence.countByUuid_C((String)null, 0L);
	}

	@Test
	public void testCountByCompanyId() throws Exception {
		_persistence.countByCompanyId(RandomTestUtil.nextLong());

		_persistence.countByCompanyId(0L);
	}

	@Test
	public void testFindByPrimaryKeyExisting() throws Exception {
		WSRPConsumer newWSRPConsumer = addWSRPConsumer();

		WSRPConsumer existingWSRPConsumer = _persistence.findByPrimaryKey(newWSRPConsumer.getPrimaryKey());

		Assert.assertEquals(existingWSRPConsumer, newWSRPConsumer);
	}

	@Test(expected = NoSuchConsumerException.class)
	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		_persistence.findByPrimaryKey(pk);
	}

	@Test
	public void testFindAll() throws Exception {
		_persistence.findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			getOrderByComparator());
	}

	protected OrderByComparator<WSRPConsumer> getOrderByComparator() {
		return OrderByComparatorFactoryUtil.create("WSRP_WSRPConsumer", "uuid",
			true, "wsrpConsumerId", true, "companyId", true, "createDate",
			true, "modifiedDate", true, "name", true, "url", true,
			"registrationPropertiesString", true, "forwardCookies", true,
			"forwardHeaders", true, "markupCharacterSets", true,
			"lastPublishDate", true);
	}

	@Test
	public void testFetchByPrimaryKeyExisting() throws Exception {
		WSRPConsumer newWSRPConsumer = addWSRPConsumer();

		WSRPConsumer existingWSRPConsumer = _persistence.fetchByPrimaryKey(newWSRPConsumer.getPrimaryKey());

		Assert.assertEquals(existingWSRPConsumer, newWSRPConsumer);
	}

	@Test
	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		WSRPConsumer missingWSRPConsumer = _persistence.fetchByPrimaryKey(pk);

		Assert.assertNull(missingWSRPConsumer);
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereAllPrimaryKeysExist()
		throws Exception {
		WSRPConsumer newWSRPConsumer1 = addWSRPConsumer();
		WSRPConsumer newWSRPConsumer2 = addWSRPConsumer();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newWSRPConsumer1.getPrimaryKey());
		primaryKeys.add(newWSRPConsumer2.getPrimaryKey());

		Map<Serializable, WSRPConsumer> wsrpConsumers = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(2, wsrpConsumers.size());
		Assert.assertEquals(newWSRPConsumer1,
			wsrpConsumers.get(newWSRPConsumer1.getPrimaryKey()));
		Assert.assertEquals(newWSRPConsumer2,
			wsrpConsumers.get(newWSRPConsumer2.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereNoPrimaryKeysExist()
		throws Exception {
		long pk1 = RandomTestUtil.nextLong();

		long pk2 = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(pk1);
		primaryKeys.add(pk2);

		Map<Serializable, WSRPConsumer> wsrpConsumers = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(wsrpConsumers.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereSomePrimaryKeysExist()
		throws Exception {
		WSRPConsumer newWSRPConsumer = addWSRPConsumer();

		long pk = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newWSRPConsumer.getPrimaryKey());
		primaryKeys.add(pk);

		Map<Serializable, WSRPConsumer> wsrpConsumers = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, wsrpConsumers.size());
		Assert.assertEquals(newWSRPConsumer,
			wsrpConsumers.get(newWSRPConsumer.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithNoPrimaryKeys()
		throws Exception {
		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		Map<Serializable, WSRPConsumer> wsrpConsumers = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(wsrpConsumers.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithOnePrimaryKey()
		throws Exception {
		WSRPConsumer newWSRPConsumer = addWSRPConsumer();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newWSRPConsumer.getPrimaryKey());

		Map<Serializable, WSRPConsumer> wsrpConsumers = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, wsrpConsumers.size());
		Assert.assertEquals(newWSRPConsumer,
			wsrpConsumers.get(newWSRPConsumer.getPrimaryKey()));
	}

	@Test
	public void testActionableDynamicQuery() throws Exception {
		final IntegerWrapper count = new IntegerWrapper();

		ActionableDynamicQuery actionableDynamicQuery = WSRPConsumerLocalServiceUtil.getActionableDynamicQuery();

		actionableDynamicQuery.setPerformActionMethod(new ActionableDynamicQuery.PerformActionMethod<WSRPConsumer>() {
				@Override
				public void performAction(WSRPConsumer wsrpConsumer) {
					Assert.assertNotNull(wsrpConsumer);

					count.increment();
				}
			});

		actionableDynamicQuery.performActions();

		Assert.assertEquals(count.getValue(), _persistence.countAll());
	}

	@Test
	public void testDynamicQueryByPrimaryKeyExisting()
		throws Exception {
		WSRPConsumer newWSRPConsumer = addWSRPConsumer();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(WSRPConsumer.class,
				_dynamicQueryClassLoader);

		dynamicQuery.add(RestrictionsFactoryUtil.eq("wsrpConsumerId",
				newWSRPConsumer.getWsrpConsumerId()));

		List<WSRPConsumer> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		WSRPConsumer existingWSRPConsumer = result.get(0);

		Assert.assertEquals(existingWSRPConsumer, newWSRPConsumer);
	}

	@Test
	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(WSRPConsumer.class,
				_dynamicQueryClassLoader);

		dynamicQuery.add(RestrictionsFactoryUtil.eq("wsrpConsumerId",
				RandomTestUtil.nextLong()));

		List<WSRPConsumer> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testDynamicQueryByProjectionExisting()
		throws Exception {
		WSRPConsumer newWSRPConsumer = addWSRPConsumer();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(WSRPConsumer.class,
				_dynamicQueryClassLoader);

		dynamicQuery.setProjection(ProjectionFactoryUtil.property(
				"wsrpConsumerId"));

		Object newWsrpConsumerId = newWSRPConsumer.getWsrpConsumerId();

		dynamicQuery.add(RestrictionsFactoryUtil.in("wsrpConsumerId",
				new Object[] { newWsrpConsumerId }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		Object existingWsrpConsumerId = result.get(0);

		Assert.assertEquals(existingWsrpConsumerId, newWsrpConsumerId);
	}

	@Test
	public void testDynamicQueryByProjectionMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(WSRPConsumer.class,
				_dynamicQueryClassLoader);

		dynamicQuery.setProjection(ProjectionFactoryUtil.property(
				"wsrpConsumerId"));

		dynamicQuery.add(RestrictionsFactoryUtil.in("wsrpConsumerId",
				new Object[] { RandomTestUtil.nextLong() }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	protected WSRPConsumer addWSRPConsumer() throws Exception {
		long pk = RandomTestUtil.nextLong();

		WSRPConsumer wsrpConsumer = _persistence.create(pk);

		wsrpConsumer.setUuid(RandomTestUtil.randomString());

		wsrpConsumer.setCompanyId(RandomTestUtil.nextLong());

		wsrpConsumer.setCreateDate(RandomTestUtil.nextDate());

		wsrpConsumer.setModifiedDate(RandomTestUtil.nextDate());

		wsrpConsumer.setName(RandomTestUtil.randomString());

		wsrpConsumer.setUrl(RandomTestUtil.randomString());

		wsrpConsumer.setWsdl(RandomTestUtil.randomString());

		wsrpConsumer.setRegistrationContextString(RandomTestUtil.randomString());

		wsrpConsumer.setRegistrationPropertiesString(RandomTestUtil.randomString());

		wsrpConsumer.setForwardCookies(RandomTestUtil.randomString());

		wsrpConsumer.setForwardHeaders(RandomTestUtil.randomString());

		wsrpConsumer.setMarkupCharacterSets(RandomTestUtil.randomString());

		wsrpConsumer.setLastPublishDate(RandomTestUtil.nextDate());

		_wsrpConsumers.add(_persistence.update(wsrpConsumer));

		return wsrpConsumer;
	}

	private List<WSRPConsumer> _wsrpConsumers = new ArrayList<WSRPConsumer>();
	private WSRPConsumerPersistence _persistence;
	private ClassLoader _dynamicQueryClassLoader;
}