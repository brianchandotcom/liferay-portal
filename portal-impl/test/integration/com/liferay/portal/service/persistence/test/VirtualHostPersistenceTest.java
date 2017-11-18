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

import com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQueryFactoryUtil;
import com.liferay.portal.kernel.dao.orm.ProjectionFactoryUtil;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.RestrictionsFactoryUtil;
import com.liferay.portal.kernel.exception.NoSuchVirtualHostException;
import com.liferay.portal.kernel.model.VirtualHost;
import com.liferay.portal.kernel.service.VirtualHostLocalServiceUtil;
import com.liferay.portal.kernel.service.persistence.VirtualHostPersistence;
import com.liferay.portal.kernel.service.persistence.VirtualHostUtil;
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

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

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
public class VirtualHostPersistenceTest {
	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule = new AggregateTestRule(new LiferayIntegrationTestRule(),
			PersistenceTestRule.INSTANCE,
			new TransactionalTestRule(Propagation.REQUIRED));

	@Before
	public void setUp() {
		_persistence = VirtualHostUtil.getPersistence();

		Class<?> clazz = _persistence.getClass();

		_dynamicQueryClassLoader = clazz.getClassLoader();
	}

	@After
	public void tearDown() throws Exception {
		Iterator<VirtualHost> iterator = _virtualHosts.iterator();

		while (iterator.hasNext()) {
			_persistence.remove(iterator.next());

			iterator.remove();
		}
	}

	@Test
	public void testCreate() throws Exception {
		long pk = RandomTestUtil.nextLong();

		VirtualHost virtualHost = _persistence.create(pk);

		Assert.assertNotNull(virtualHost);

		Assert.assertEquals(virtualHost.getPrimaryKey(), pk);
	}

	@Test
	public void testRemove() throws Exception {
		VirtualHost newVirtualHost = addVirtualHost();

		_persistence.remove(newVirtualHost);

		VirtualHost existingVirtualHost = _persistence.fetchByPrimaryKey(newVirtualHost.getPrimaryKey());

		Assert.assertNull(existingVirtualHost);
	}

	@Test
	public void testUpdateNew() throws Exception {
		addVirtualHost();
	}

	@Test
	public void testUpdateExisting() throws Exception {
		long pk = RandomTestUtil.nextLong();

		VirtualHost newVirtualHost = _persistence.create(pk);

		newVirtualHost.setMvccVersion(RandomTestUtil.nextLong());

		newVirtualHost.setCompanyId(RandomTestUtil.nextLong());

		newVirtualHost.setLayoutSetId(RandomTestUtil.nextLong());

		newVirtualHost.setHostname(RandomTestUtil.randomString());

		_virtualHosts.add(_persistence.update(newVirtualHost));

		VirtualHost existingVirtualHost = _persistence.findByPrimaryKey(newVirtualHost.getPrimaryKey());

		Assert.assertEquals(existingVirtualHost.getMvccVersion(),
			newVirtualHost.getMvccVersion());
		Assert.assertEquals(existingVirtualHost.getVirtualHostId(),
			newVirtualHost.getVirtualHostId());
		Assert.assertEquals(existingVirtualHost.getCompanyId(),
			newVirtualHost.getCompanyId());
		Assert.assertEquals(existingVirtualHost.getLayoutSetId(),
			newVirtualHost.getLayoutSetId());
		Assert.assertEquals(existingVirtualHost.getHostname(),
			newVirtualHost.getHostname());
	}

	@Test
	public void testCountByHostname() throws Exception {
		_persistence.countByHostname("");

		_persistence.countByHostname("null");

		_persistence.countByHostname((String)null);
	}

	@Test
	public void testCountByC_L() throws Exception {
		_persistence.countByC_L(RandomTestUtil.nextLong(),
			RandomTestUtil.nextLong());

		_persistence.countByC_L(0L, 0L);
	}

	@Test
	public void testFindByPrimaryKeyExisting() throws Exception {
		VirtualHost newVirtualHost = addVirtualHost();

		VirtualHost existingVirtualHost = _persistence.findByPrimaryKey(newVirtualHost.getPrimaryKey());

		Assert.assertEquals(existingVirtualHost, newVirtualHost);
	}

	@Test(expected = NoSuchVirtualHostException.class)
	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		_persistence.findByPrimaryKey(pk);
	}

	@Test
	public void testFindAll() throws Exception {
		_persistence.findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			getOrderByComparator());
	}

	protected OrderByComparator<VirtualHost> getOrderByComparator() {
		return OrderByComparatorFactoryUtil.create("VirtualHost",
			"mvccVersion", true, "virtualHostId", true, "companyId", true,
			"layoutSetId", true, "hostname", true);
	}

	@Test
	public void testFetchByPrimaryKeyExisting() throws Exception {
		VirtualHost newVirtualHost = addVirtualHost();

		VirtualHost existingVirtualHost = _persistence.fetchByPrimaryKey(newVirtualHost.getPrimaryKey());

		Assert.assertEquals(existingVirtualHost, newVirtualHost);
	}

	@Test
	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		VirtualHost missingVirtualHost = _persistence.fetchByPrimaryKey(pk);

		Assert.assertNull(missingVirtualHost);
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereAllPrimaryKeysExist()
		throws Exception {
		VirtualHost newVirtualHost1 = addVirtualHost();
		VirtualHost newVirtualHost2 = addVirtualHost();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newVirtualHost1.getPrimaryKey());
		primaryKeys.add(newVirtualHost2.getPrimaryKey());

		Map<Serializable, VirtualHost> virtualHosts = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(2, virtualHosts.size());
		Assert.assertEquals(newVirtualHost1,
			virtualHosts.get(newVirtualHost1.getPrimaryKey()));
		Assert.assertEquals(newVirtualHost2,
			virtualHosts.get(newVirtualHost2.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereNoPrimaryKeysExist()
		throws Exception {
		long pk1 = RandomTestUtil.nextLong();

		long pk2 = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(pk1);
		primaryKeys.add(pk2);

		Map<Serializable, VirtualHost> virtualHosts = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(virtualHosts.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereSomePrimaryKeysExist()
		throws Exception {
		VirtualHost newVirtualHost = addVirtualHost();

		long pk = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newVirtualHost.getPrimaryKey());
		primaryKeys.add(pk);

		Map<Serializable, VirtualHost> virtualHosts = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, virtualHosts.size());
		Assert.assertEquals(newVirtualHost,
			virtualHosts.get(newVirtualHost.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithNoPrimaryKeys()
		throws Exception {
		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		Map<Serializable, VirtualHost> virtualHosts = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(virtualHosts.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithOnePrimaryKey()
		throws Exception {
		VirtualHost newVirtualHost = addVirtualHost();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newVirtualHost.getPrimaryKey());

		Map<Serializable, VirtualHost> virtualHosts = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, virtualHosts.size());
		Assert.assertEquals(newVirtualHost,
			virtualHosts.get(newVirtualHost.getPrimaryKey()));
	}

	@Test
	public void testActionableDynamicQuery() throws Exception {
		final IntegerWrapper count = new IntegerWrapper();

		ActionableDynamicQuery actionableDynamicQuery = VirtualHostLocalServiceUtil.getActionableDynamicQuery();

		actionableDynamicQuery.setPerformActionMethod(new ActionableDynamicQuery.PerformActionMethod<VirtualHost>() {
				@Override
				public void performAction(VirtualHost virtualHost) {
					Assert.assertNotNull(virtualHost);

					count.increment();
				}
			});

		actionableDynamicQuery.performActions();

		Assert.assertEquals(count.getValue(), _persistence.countAll());
	}

	@Test
	public void testDynamicQueryByPrimaryKeyExisting()
		throws Exception {
		VirtualHost newVirtualHost = addVirtualHost();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(VirtualHost.class,
				_dynamicQueryClassLoader);

		dynamicQuery.add(RestrictionsFactoryUtil.eq("virtualHostId",
				newVirtualHost.getVirtualHostId()));

		List<VirtualHost> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		VirtualHost existingVirtualHost = result.get(0);

		Assert.assertEquals(existingVirtualHost, newVirtualHost);
	}

	@Test
	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(VirtualHost.class,
				_dynamicQueryClassLoader);

		dynamicQuery.add(RestrictionsFactoryUtil.eq("virtualHostId",
				RandomTestUtil.nextLong()));

		List<VirtualHost> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testDynamicQueryByProjectionExisting()
		throws Exception {
		VirtualHost newVirtualHost = addVirtualHost();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(VirtualHost.class,
				_dynamicQueryClassLoader);

		dynamicQuery.setProjection(ProjectionFactoryUtil.property(
				"virtualHostId"));

		Object newVirtualHostId = newVirtualHost.getVirtualHostId();

		dynamicQuery.add(RestrictionsFactoryUtil.in("virtualHostId",
				new Object[] { newVirtualHostId }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		Object existingVirtualHostId = result.get(0);

		Assert.assertEquals(existingVirtualHostId, newVirtualHostId);
	}

	@Test
	public void testDynamicQueryByProjectionMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(VirtualHost.class,
				_dynamicQueryClassLoader);

		dynamicQuery.setProjection(ProjectionFactoryUtil.property(
				"virtualHostId"));

		dynamicQuery.add(RestrictionsFactoryUtil.in("virtualHostId",
				new Object[] { RandomTestUtil.nextLong() }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testResetOriginalValues() throws Exception {
		VirtualHost newVirtualHost = addVirtualHost();

		_persistence.clearCache();

		VirtualHost existingVirtualHost = _persistence.findByPrimaryKey(newVirtualHost.getPrimaryKey());

		Assert.assertTrue(Objects.equals(existingVirtualHost.getHostname(),
				ReflectionTestUtil.invoke(existingVirtualHost,
					"getOriginalHostname", new Class<?>[0])));

		Assert.assertEquals(Long.valueOf(existingVirtualHost.getCompanyId()),
			ReflectionTestUtil.<Long>invoke(existingVirtualHost,
				"getOriginalCompanyId", new Class<?>[0]));
		Assert.assertEquals(Long.valueOf(existingVirtualHost.getLayoutSetId()),
			ReflectionTestUtil.<Long>invoke(existingVirtualHost,
				"getOriginalLayoutSetId", new Class<?>[0]));
	}

	protected VirtualHost addVirtualHost() throws Exception {
		long pk = RandomTestUtil.nextLong();

		VirtualHost virtualHost = _persistence.create(pk);

		virtualHost.setMvccVersion(RandomTestUtil.nextLong());

		virtualHost.setCompanyId(RandomTestUtil.nextLong());

		virtualHost.setLayoutSetId(RandomTestUtil.nextLong());

		virtualHost.setHostname(RandomTestUtil.randomString());

		_virtualHosts.add(_persistence.update(virtualHost));

		return virtualHost;
	}

	private List<VirtualHost> _virtualHosts = new ArrayList<VirtualHost>();
	private VirtualHostPersistence _persistence;
	private ClassLoader _dynamicQueryClassLoader;
}