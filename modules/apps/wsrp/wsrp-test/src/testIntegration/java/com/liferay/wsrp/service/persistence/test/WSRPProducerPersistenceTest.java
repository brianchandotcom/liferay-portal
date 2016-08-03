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
import com.liferay.portal.kernel.test.ReflectionTestUtil;
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

import com.liferay.wsrp.exception.NoSuchProducerException;
import com.liferay.wsrp.model.WSRPProducer;
import com.liferay.wsrp.service.WSRPProducerLocalServiceUtil;
import com.liferay.wsrp.service.persistence.WSRPProducerPersistence;
import com.liferay.wsrp.service.persistence.WSRPProducerUtil;

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
public class WSRPProducerPersistenceTest {
	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule = new AggregateTestRule(new LiferayIntegrationTestRule(),
			PersistenceTestRule.INSTANCE,
			new TransactionalTestRule(Propagation.REQUIRED));

	@Before
	public void setUp() {
		_persistence = WSRPProducerUtil.getPersistence();

		Class<?> clazz = _persistence.getClass();

		_dynamicQueryClassLoader = clazz.getClassLoader();
	}

	@After
	public void tearDown() throws Exception {
		Iterator<WSRPProducer> iterator = _wsrpProducers.iterator();

		while (iterator.hasNext()) {
			_persistence.remove(iterator.next());

			iterator.remove();
		}
	}

	@Test
	public void testCreate() throws Exception {
		long pk = RandomTestUtil.nextLong();

		WSRPProducer wsrpProducer = _persistence.create(pk);

		Assert.assertNotNull(wsrpProducer);

		Assert.assertEquals(wsrpProducer.getPrimaryKey(), pk);
	}

	@Test
	public void testRemove() throws Exception {
		WSRPProducer newWSRPProducer = addWSRPProducer();

		_persistence.remove(newWSRPProducer);

		WSRPProducer existingWSRPProducer = _persistence.fetchByPrimaryKey(newWSRPProducer.getPrimaryKey());

		Assert.assertNull(existingWSRPProducer);
	}

	@Test
	public void testUpdateNew() throws Exception {
		addWSRPProducer();
	}

	@Test
	public void testUpdateExisting() throws Exception {
		long pk = RandomTestUtil.nextLong();

		WSRPProducer newWSRPProducer = _persistence.create(pk);

		newWSRPProducer.setUuid(RandomTestUtil.randomString());

		newWSRPProducer.setGroupId(RandomTestUtil.nextLong());

		newWSRPProducer.setCompanyId(RandomTestUtil.nextLong());

		newWSRPProducer.setCreateDate(RandomTestUtil.nextDate());

		newWSRPProducer.setModifiedDate(RandomTestUtil.nextDate());

		newWSRPProducer.setName(RandomTestUtil.randomString());

		newWSRPProducer.setVersion(RandomTestUtil.randomString());

		newWSRPProducer.setPortletIds(RandomTestUtil.randomString());

		newWSRPProducer.setLastPublishDate(RandomTestUtil.nextDate());

		_wsrpProducers.add(_persistence.update(newWSRPProducer));

		WSRPProducer existingWSRPProducer = _persistence.findByPrimaryKey(newWSRPProducer.getPrimaryKey());

		Assert.assertEquals(existingWSRPProducer.getUuid(),
			newWSRPProducer.getUuid());
		Assert.assertEquals(existingWSRPProducer.getWsrpProducerId(),
			newWSRPProducer.getWsrpProducerId());
		Assert.assertEquals(existingWSRPProducer.getGroupId(),
			newWSRPProducer.getGroupId());
		Assert.assertEquals(existingWSRPProducer.getCompanyId(),
			newWSRPProducer.getCompanyId());
		Assert.assertEquals(Time.getShortTimestamp(
				existingWSRPProducer.getCreateDate()),
			Time.getShortTimestamp(newWSRPProducer.getCreateDate()));
		Assert.assertEquals(Time.getShortTimestamp(
				existingWSRPProducer.getModifiedDate()),
			Time.getShortTimestamp(newWSRPProducer.getModifiedDate()));
		Assert.assertEquals(existingWSRPProducer.getName(),
			newWSRPProducer.getName());
		Assert.assertEquals(existingWSRPProducer.getVersion(),
			newWSRPProducer.getVersion());
		Assert.assertEquals(existingWSRPProducer.getPortletIds(),
			newWSRPProducer.getPortletIds());
		Assert.assertEquals(Time.getShortTimestamp(
				existingWSRPProducer.getLastPublishDate()),
			Time.getShortTimestamp(newWSRPProducer.getLastPublishDate()));
	}

	@Test
	public void testCountByUuid() throws Exception {
		_persistence.countByUuid(StringPool.BLANK);

		_persistence.countByUuid(StringPool.NULL);

		_persistence.countByUuid((String)null);
	}

	@Test
	public void testCountByUUID_G() throws Exception {
		_persistence.countByUUID_G(StringPool.BLANK, RandomTestUtil.nextLong());

		_persistence.countByUUID_G(StringPool.NULL, 0L);

		_persistence.countByUUID_G((String)null, 0L);
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
		WSRPProducer newWSRPProducer = addWSRPProducer();

		WSRPProducer existingWSRPProducer = _persistence.findByPrimaryKey(newWSRPProducer.getPrimaryKey());

		Assert.assertEquals(existingWSRPProducer, newWSRPProducer);
	}

	@Test(expected = NoSuchProducerException.class)
	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		_persistence.findByPrimaryKey(pk);
	}

	@Test
	public void testFindAll() throws Exception {
		_persistence.findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			getOrderByComparator());
	}

	protected OrderByComparator<WSRPProducer> getOrderByComparator() {
		return OrderByComparatorFactoryUtil.create("WSRP_WSRPProducer", "uuid",
			true, "wsrpProducerId", true, "groupId", true, "companyId", true,
			"createDate", true, "modifiedDate", true, "name", true, "version",
			true, "portletIds", true, "lastPublishDate", true);
	}

	@Test
	public void testFetchByPrimaryKeyExisting() throws Exception {
		WSRPProducer newWSRPProducer = addWSRPProducer();

		WSRPProducer existingWSRPProducer = _persistence.fetchByPrimaryKey(newWSRPProducer.getPrimaryKey());

		Assert.assertEquals(existingWSRPProducer, newWSRPProducer);
	}

	@Test
	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		WSRPProducer missingWSRPProducer = _persistence.fetchByPrimaryKey(pk);

		Assert.assertNull(missingWSRPProducer);
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereAllPrimaryKeysExist()
		throws Exception {
		WSRPProducer newWSRPProducer1 = addWSRPProducer();
		WSRPProducer newWSRPProducer2 = addWSRPProducer();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newWSRPProducer1.getPrimaryKey());
		primaryKeys.add(newWSRPProducer2.getPrimaryKey());

		Map<Serializable, WSRPProducer> wsrpProducers = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(2, wsrpProducers.size());
		Assert.assertEquals(newWSRPProducer1,
			wsrpProducers.get(newWSRPProducer1.getPrimaryKey()));
		Assert.assertEquals(newWSRPProducer2,
			wsrpProducers.get(newWSRPProducer2.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereNoPrimaryKeysExist()
		throws Exception {
		long pk1 = RandomTestUtil.nextLong();

		long pk2 = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(pk1);
		primaryKeys.add(pk2);

		Map<Serializable, WSRPProducer> wsrpProducers = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(wsrpProducers.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereSomePrimaryKeysExist()
		throws Exception {
		WSRPProducer newWSRPProducer = addWSRPProducer();

		long pk = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newWSRPProducer.getPrimaryKey());
		primaryKeys.add(pk);

		Map<Serializable, WSRPProducer> wsrpProducers = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, wsrpProducers.size());
		Assert.assertEquals(newWSRPProducer,
			wsrpProducers.get(newWSRPProducer.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithNoPrimaryKeys()
		throws Exception {
		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		Map<Serializable, WSRPProducer> wsrpProducers = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(wsrpProducers.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithOnePrimaryKey()
		throws Exception {
		WSRPProducer newWSRPProducer = addWSRPProducer();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newWSRPProducer.getPrimaryKey());

		Map<Serializable, WSRPProducer> wsrpProducers = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, wsrpProducers.size());
		Assert.assertEquals(newWSRPProducer,
			wsrpProducers.get(newWSRPProducer.getPrimaryKey()));
	}

	@Test
	public void testActionableDynamicQuery() throws Exception {
		final IntegerWrapper count = new IntegerWrapper();

		ActionableDynamicQuery actionableDynamicQuery = WSRPProducerLocalServiceUtil.getActionableDynamicQuery();

		actionableDynamicQuery.setPerformActionMethod(new ActionableDynamicQuery.PerformActionMethod<WSRPProducer>() {
				@Override
				public void performAction(WSRPProducer wsrpProducer) {
					Assert.assertNotNull(wsrpProducer);

					count.increment();
				}
			});

		actionableDynamicQuery.performActions();

		Assert.assertEquals(count.getValue(), _persistence.countAll());
	}

	@Test
	public void testDynamicQueryByPrimaryKeyExisting()
		throws Exception {
		WSRPProducer newWSRPProducer = addWSRPProducer();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(WSRPProducer.class,
				_dynamicQueryClassLoader);

		dynamicQuery.add(RestrictionsFactoryUtil.eq("wsrpProducerId",
				newWSRPProducer.getWsrpProducerId()));

		List<WSRPProducer> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		WSRPProducer existingWSRPProducer = result.get(0);

		Assert.assertEquals(existingWSRPProducer, newWSRPProducer);
	}

	@Test
	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(WSRPProducer.class,
				_dynamicQueryClassLoader);

		dynamicQuery.add(RestrictionsFactoryUtil.eq("wsrpProducerId",
				RandomTestUtil.nextLong()));

		List<WSRPProducer> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testDynamicQueryByProjectionExisting()
		throws Exception {
		WSRPProducer newWSRPProducer = addWSRPProducer();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(WSRPProducer.class,
				_dynamicQueryClassLoader);

		dynamicQuery.setProjection(ProjectionFactoryUtil.property(
				"wsrpProducerId"));

		Object newWsrpProducerId = newWSRPProducer.getWsrpProducerId();

		dynamicQuery.add(RestrictionsFactoryUtil.in("wsrpProducerId",
				new Object[] { newWsrpProducerId }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		Object existingWsrpProducerId = result.get(0);

		Assert.assertEquals(existingWsrpProducerId, newWsrpProducerId);
	}

	@Test
	public void testDynamicQueryByProjectionMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(WSRPProducer.class,
				_dynamicQueryClassLoader);

		dynamicQuery.setProjection(ProjectionFactoryUtil.property(
				"wsrpProducerId"));

		dynamicQuery.add(RestrictionsFactoryUtil.in("wsrpProducerId",
				new Object[] { RandomTestUtil.nextLong() }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testResetOriginalValues() throws Exception {
		WSRPProducer newWSRPProducer = addWSRPProducer();

		_persistence.clearCache();

		WSRPProducer existingWSRPProducer = _persistence.findByPrimaryKey(newWSRPProducer.getPrimaryKey());

		Assert.assertTrue(Objects.equals(existingWSRPProducer.getUuid(),
				ReflectionTestUtil.invoke(existingWSRPProducer,
					"getOriginalUuid", new Class<?>[0])));
		Assert.assertEquals(Long.valueOf(existingWSRPProducer.getGroupId()),
			ReflectionTestUtil.<Long>invoke(existingWSRPProducer,
				"getOriginalGroupId", new Class<?>[0]));
	}

	protected WSRPProducer addWSRPProducer() throws Exception {
		long pk = RandomTestUtil.nextLong();

		WSRPProducer wsrpProducer = _persistence.create(pk);

		wsrpProducer.setUuid(RandomTestUtil.randomString());

		wsrpProducer.setGroupId(RandomTestUtil.nextLong());

		wsrpProducer.setCompanyId(RandomTestUtil.nextLong());

		wsrpProducer.setCreateDate(RandomTestUtil.nextDate());

		wsrpProducer.setModifiedDate(RandomTestUtil.nextDate());

		wsrpProducer.setName(RandomTestUtil.randomString());

		wsrpProducer.setVersion(RandomTestUtil.randomString());

		wsrpProducer.setPortletIds(RandomTestUtil.randomString());

		wsrpProducer.setLastPublishDate(RandomTestUtil.nextDate());

		_wsrpProducers.add(_persistence.update(wsrpProducer));

		return wsrpProducer;
	}

	private List<WSRPProducer> _wsrpProducers = new ArrayList<WSRPProducer>();
	private WSRPProducerPersistence _persistence;
	private ClassLoader _dynamicQueryClassLoader;
}