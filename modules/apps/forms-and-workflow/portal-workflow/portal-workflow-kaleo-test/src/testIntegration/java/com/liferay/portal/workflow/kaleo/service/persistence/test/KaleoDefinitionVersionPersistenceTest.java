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

package com.liferay.portal.workflow.kaleo.service.persistence.test;

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
import com.liferay.portal.kernel.util.Time;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PersistenceTestRule;
import com.liferay.portal.test.rule.TransactionalTestRule;
import com.liferay.portal.workflow.kaleo.exception.NoSuchDefinitionVersionException;
import com.liferay.portal.workflow.kaleo.model.KaleoDefinitionVersion;
import com.liferay.portal.workflow.kaleo.service.KaleoDefinitionVersionLocalServiceUtil;
import com.liferay.portal.workflow.kaleo.service.persistence.KaleoDefinitionVersionPersistence;
import com.liferay.portal.workflow.kaleo.service.persistence.KaleoDefinitionVersionUtil;

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
public class KaleoDefinitionVersionPersistenceTest {
	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule = new AggregateTestRule(new LiferayIntegrationTestRule(),
			PersistenceTestRule.INSTANCE,
			new TransactionalTestRule(Propagation.REQUIRED,
				"com.liferay.portal.workflow.kaleo.service"));

	@Before
	public void setUp() {
		_persistence = KaleoDefinitionVersionUtil.getPersistence();

		Class<?> clazz = _persistence.getClass();

		_dynamicQueryClassLoader = clazz.getClassLoader();
	}

	@After
	public void tearDown() throws Exception {
		Iterator<KaleoDefinitionVersion> iterator = _kaleoDefinitionVersions.iterator();

		while (iterator.hasNext()) {
			_persistence.remove(iterator.next());

			iterator.remove();
		}
	}

	@Test
	public void testCreate() throws Exception {
		long pk = RandomTestUtil.nextLong();

		KaleoDefinitionVersion kaleoDefinitionVersion = _persistence.create(pk);

		Assert.assertNotNull(kaleoDefinitionVersion);

		Assert.assertEquals(kaleoDefinitionVersion.getPrimaryKey(), pk);
	}

	@Test
	public void testRemove() throws Exception {
		KaleoDefinitionVersion newKaleoDefinitionVersion = addKaleoDefinitionVersion();

		_persistence.remove(newKaleoDefinitionVersion);

		KaleoDefinitionVersion existingKaleoDefinitionVersion = _persistence.fetchByPrimaryKey(newKaleoDefinitionVersion.getPrimaryKey());

		Assert.assertNull(existingKaleoDefinitionVersion);
	}

	@Test
	public void testUpdateNew() throws Exception {
		addKaleoDefinitionVersion();
	}

	@Test
	public void testUpdateExisting() throws Exception {
		long pk = RandomTestUtil.nextLong();

		KaleoDefinitionVersion newKaleoDefinitionVersion = _persistence.create(pk);

		newKaleoDefinitionVersion.setGroupId(RandomTestUtil.nextLong());

		newKaleoDefinitionVersion.setCompanyId(RandomTestUtil.nextLong());

		newKaleoDefinitionVersion.setUserId(RandomTestUtil.nextLong());

		newKaleoDefinitionVersion.setUserName(RandomTestUtil.randomString());

		newKaleoDefinitionVersion.setStatusByUserId(RandomTestUtil.nextLong());

		newKaleoDefinitionVersion.setStatusByUserName(RandomTestUtil.randomString());

		newKaleoDefinitionVersion.setStatusDate(RandomTestUtil.nextDate());

		newKaleoDefinitionVersion.setCreateDate(RandomTestUtil.nextDate());

		newKaleoDefinitionVersion.setModifiedDate(RandomTestUtil.nextDate());

		newKaleoDefinitionVersion.setName(RandomTestUtil.randomString());

		newKaleoDefinitionVersion.setTitle(RandomTestUtil.randomString());

		newKaleoDefinitionVersion.setDescription(RandomTestUtil.randomString());

		newKaleoDefinitionVersion.setContent(RandomTestUtil.randomString());

		newKaleoDefinitionVersion.setVersion(RandomTestUtil.randomString());

		newKaleoDefinitionVersion.setStartKaleoNodeId(RandomTestUtil.nextLong());

		newKaleoDefinitionVersion.setStatus(RandomTestUtil.nextInt());

		_kaleoDefinitionVersions.add(_persistence.update(
				newKaleoDefinitionVersion));

		KaleoDefinitionVersion existingKaleoDefinitionVersion = _persistence.findByPrimaryKey(newKaleoDefinitionVersion.getPrimaryKey());

		Assert.assertEquals(existingKaleoDefinitionVersion.getKaleoDefinitionVersionId(),
			newKaleoDefinitionVersion.getKaleoDefinitionVersionId());
		Assert.assertEquals(existingKaleoDefinitionVersion.getGroupId(),
			newKaleoDefinitionVersion.getGroupId());
		Assert.assertEquals(existingKaleoDefinitionVersion.getCompanyId(),
			newKaleoDefinitionVersion.getCompanyId());
		Assert.assertEquals(existingKaleoDefinitionVersion.getUserId(),
			newKaleoDefinitionVersion.getUserId());
		Assert.assertEquals(existingKaleoDefinitionVersion.getUserName(),
			newKaleoDefinitionVersion.getUserName());
		Assert.assertEquals(existingKaleoDefinitionVersion.getStatusByUserId(),
			newKaleoDefinitionVersion.getStatusByUserId());
		Assert.assertEquals(existingKaleoDefinitionVersion.getStatusByUserName(),
			newKaleoDefinitionVersion.getStatusByUserName());
		Assert.assertEquals(Time.getShortTimestamp(
				existingKaleoDefinitionVersion.getStatusDate()),
			Time.getShortTimestamp(newKaleoDefinitionVersion.getStatusDate()));
		Assert.assertEquals(Time.getShortTimestamp(
				existingKaleoDefinitionVersion.getCreateDate()),
			Time.getShortTimestamp(newKaleoDefinitionVersion.getCreateDate()));
		Assert.assertEquals(Time.getShortTimestamp(
				existingKaleoDefinitionVersion.getModifiedDate()),
			Time.getShortTimestamp(newKaleoDefinitionVersion.getModifiedDate()));
		Assert.assertEquals(existingKaleoDefinitionVersion.getName(),
			newKaleoDefinitionVersion.getName());
		Assert.assertEquals(existingKaleoDefinitionVersion.getTitle(),
			newKaleoDefinitionVersion.getTitle());
		Assert.assertEquals(existingKaleoDefinitionVersion.getDescription(),
			newKaleoDefinitionVersion.getDescription());
		Assert.assertEquals(existingKaleoDefinitionVersion.getContent(),
			newKaleoDefinitionVersion.getContent());
		Assert.assertEquals(existingKaleoDefinitionVersion.getVersion(),
			newKaleoDefinitionVersion.getVersion());
		Assert.assertEquals(existingKaleoDefinitionVersion.getStartKaleoNodeId(),
			newKaleoDefinitionVersion.getStartKaleoNodeId());
		Assert.assertEquals(existingKaleoDefinitionVersion.getStatus(),
			newKaleoDefinitionVersion.getStatus());
	}

	@Test
	public void testCountByCompanyId() throws Exception {
		_persistence.countByCompanyId(RandomTestUtil.nextLong());

		_persistence.countByCompanyId(0L);
	}

	@Test
	public void testCountByC_N() throws Exception {
		_persistence.countByC_N(RandomTestUtil.nextLong(), "");

		_persistence.countByC_N(0L, "null");

		_persistence.countByC_N(0L, (String)null);
	}

	@Test
	public void testCountByC_N_V() throws Exception {
		_persistence.countByC_N_V(RandomTestUtil.nextLong(), "", "");

		_persistence.countByC_N_V(0L, "null", "null");

		_persistence.countByC_N_V(0L, (String)null, (String)null);
	}

	@Test
	public void testFindByPrimaryKeyExisting() throws Exception {
		KaleoDefinitionVersion newKaleoDefinitionVersion = addKaleoDefinitionVersion();

		KaleoDefinitionVersion existingKaleoDefinitionVersion = _persistence.findByPrimaryKey(newKaleoDefinitionVersion.getPrimaryKey());

		Assert.assertEquals(existingKaleoDefinitionVersion,
			newKaleoDefinitionVersion);
	}

	@Test(expected = NoSuchDefinitionVersionException.class)
	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		_persistence.findByPrimaryKey(pk);
	}

	@Test
	public void testFindAll() throws Exception {
		_persistence.findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			getOrderByComparator());
	}

	protected OrderByComparator<KaleoDefinitionVersion> getOrderByComparator() {
		return OrderByComparatorFactoryUtil.create("KaleoDefinitionVersion",
			"kaleoDefinitionVersionId", true, "groupId", true, "companyId",
			true, "userId", true, "userName", true, "statusByUserId", true,
			"statusByUserName", true, "statusDate", true, "createDate", true,
			"modifiedDate", true, "name", true, "title", true, "description",
			true, "version", true, "startKaleoNodeId", true, "status", true);
	}

	@Test
	public void testFetchByPrimaryKeyExisting() throws Exception {
		KaleoDefinitionVersion newKaleoDefinitionVersion = addKaleoDefinitionVersion();

		KaleoDefinitionVersion existingKaleoDefinitionVersion = _persistence.fetchByPrimaryKey(newKaleoDefinitionVersion.getPrimaryKey());

		Assert.assertEquals(existingKaleoDefinitionVersion,
			newKaleoDefinitionVersion);
	}

	@Test
	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		KaleoDefinitionVersion missingKaleoDefinitionVersion = _persistence.fetchByPrimaryKey(pk);

		Assert.assertNull(missingKaleoDefinitionVersion);
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereAllPrimaryKeysExist()
		throws Exception {
		KaleoDefinitionVersion newKaleoDefinitionVersion1 = addKaleoDefinitionVersion();
		KaleoDefinitionVersion newKaleoDefinitionVersion2 = addKaleoDefinitionVersion();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newKaleoDefinitionVersion1.getPrimaryKey());
		primaryKeys.add(newKaleoDefinitionVersion2.getPrimaryKey());

		Map<Serializable, KaleoDefinitionVersion> kaleoDefinitionVersions = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(2, kaleoDefinitionVersions.size());
		Assert.assertEquals(newKaleoDefinitionVersion1,
			kaleoDefinitionVersions.get(
				newKaleoDefinitionVersion1.getPrimaryKey()));
		Assert.assertEquals(newKaleoDefinitionVersion2,
			kaleoDefinitionVersions.get(
				newKaleoDefinitionVersion2.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereNoPrimaryKeysExist()
		throws Exception {
		long pk1 = RandomTestUtil.nextLong();

		long pk2 = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(pk1);
		primaryKeys.add(pk2);

		Map<Serializable, KaleoDefinitionVersion> kaleoDefinitionVersions = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(kaleoDefinitionVersions.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereSomePrimaryKeysExist()
		throws Exception {
		KaleoDefinitionVersion newKaleoDefinitionVersion = addKaleoDefinitionVersion();

		long pk = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newKaleoDefinitionVersion.getPrimaryKey());
		primaryKeys.add(pk);

		Map<Serializable, KaleoDefinitionVersion> kaleoDefinitionVersions = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, kaleoDefinitionVersions.size());
		Assert.assertEquals(newKaleoDefinitionVersion,
			kaleoDefinitionVersions.get(
				newKaleoDefinitionVersion.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithNoPrimaryKeys()
		throws Exception {
		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		Map<Serializable, KaleoDefinitionVersion> kaleoDefinitionVersions = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(kaleoDefinitionVersions.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithOnePrimaryKey()
		throws Exception {
		KaleoDefinitionVersion newKaleoDefinitionVersion = addKaleoDefinitionVersion();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newKaleoDefinitionVersion.getPrimaryKey());

		Map<Serializable, KaleoDefinitionVersion> kaleoDefinitionVersions = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, kaleoDefinitionVersions.size());
		Assert.assertEquals(newKaleoDefinitionVersion,
			kaleoDefinitionVersions.get(
				newKaleoDefinitionVersion.getPrimaryKey()));
	}

	@Test
	public void testActionableDynamicQuery() throws Exception {
		final IntegerWrapper count = new IntegerWrapper();

		ActionableDynamicQuery actionableDynamicQuery = KaleoDefinitionVersionLocalServiceUtil.getActionableDynamicQuery();

		actionableDynamicQuery.setPerformActionMethod(new ActionableDynamicQuery.PerformActionMethod<KaleoDefinitionVersion>() {
				@Override
				public void performAction(
					KaleoDefinitionVersion kaleoDefinitionVersion) {
					Assert.assertNotNull(kaleoDefinitionVersion);

					count.increment();
				}
			});

		actionableDynamicQuery.performActions();

		Assert.assertEquals(count.getValue(), _persistence.countAll());
	}

	@Test
	public void testDynamicQueryByPrimaryKeyExisting()
		throws Exception {
		KaleoDefinitionVersion newKaleoDefinitionVersion = addKaleoDefinitionVersion();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(KaleoDefinitionVersion.class,
				_dynamicQueryClassLoader);

		dynamicQuery.add(RestrictionsFactoryUtil.eq(
				"kaleoDefinitionVersionId",
				newKaleoDefinitionVersion.getKaleoDefinitionVersionId()));

		List<KaleoDefinitionVersion> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		KaleoDefinitionVersion existingKaleoDefinitionVersion = result.get(0);

		Assert.assertEquals(existingKaleoDefinitionVersion,
			newKaleoDefinitionVersion);
	}

	@Test
	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(KaleoDefinitionVersion.class,
				_dynamicQueryClassLoader);

		dynamicQuery.add(RestrictionsFactoryUtil.eq(
				"kaleoDefinitionVersionId", RandomTestUtil.nextLong()));

		List<KaleoDefinitionVersion> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testDynamicQueryByProjectionExisting()
		throws Exception {
		KaleoDefinitionVersion newKaleoDefinitionVersion = addKaleoDefinitionVersion();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(KaleoDefinitionVersion.class,
				_dynamicQueryClassLoader);

		dynamicQuery.setProjection(ProjectionFactoryUtil.property(
				"kaleoDefinitionVersionId"));

		Object newKaleoDefinitionVersionId = newKaleoDefinitionVersion.getKaleoDefinitionVersionId();

		dynamicQuery.add(RestrictionsFactoryUtil.in(
				"kaleoDefinitionVersionId",
				new Object[] { newKaleoDefinitionVersionId }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		Object existingKaleoDefinitionVersionId = result.get(0);

		Assert.assertEquals(existingKaleoDefinitionVersionId,
			newKaleoDefinitionVersionId);
	}

	@Test
	public void testDynamicQueryByProjectionMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(KaleoDefinitionVersion.class,
				_dynamicQueryClassLoader);

		dynamicQuery.setProjection(ProjectionFactoryUtil.property(
				"kaleoDefinitionVersionId"));

		dynamicQuery.add(RestrictionsFactoryUtil.in(
				"kaleoDefinitionVersionId",
				new Object[] { RandomTestUtil.nextLong() }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testResetOriginalValues() throws Exception {
		KaleoDefinitionVersion newKaleoDefinitionVersion = addKaleoDefinitionVersion();

		_persistence.clearCache();

		KaleoDefinitionVersion existingKaleoDefinitionVersion = _persistence.findByPrimaryKey(newKaleoDefinitionVersion.getPrimaryKey());

		Assert.assertEquals(Long.valueOf(
				existingKaleoDefinitionVersion.getCompanyId()),
			ReflectionTestUtil.<Long>invoke(existingKaleoDefinitionVersion,
				"getOriginalCompanyId", new Class<?>[0]));
		Assert.assertTrue(Objects.equals(
				existingKaleoDefinitionVersion.getName(),
				ReflectionTestUtil.invoke(existingKaleoDefinitionVersion,
					"getOriginalName", new Class<?>[0])));
		Assert.assertTrue(Objects.equals(
				existingKaleoDefinitionVersion.getVersion(),
				ReflectionTestUtil.invoke(existingKaleoDefinitionVersion,
					"getOriginalVersion", new Class<?>[0])));
	}

	protected KaleoDefinitionVersion addKaleoDefinitionVersion()
		throws Exception {
		long pk = RandomTestUtil.nextLong();

		KaleoDefinitionVersion kaleoDefinitionVersion = _persistence.create(pk);

		kaleoDefinitionVersion.setGroupId(RandomTestUtil.nextLong());

		kaleoDefinitionVersion.setCompanyId(RandomTestUtil.nextLong());

		kaleoDefinitionVersion.setUserId(RandomTestUtil.nextLong());

		kaleoDefinitionVersion.setUserName(RandomTestUtil.randomString());

		kaleoDefinitionVersion.setStatusByUserId(RandomTestUtil.nextLong());

		kaleoDefinitionVersion.setStatusByUserName(RandomTestUtil.randomString());

		kaleoDefinitionVersion.setStatusDate(RandomTestUtil.nextDate());

		kaleoDefinitionVersion.setCreateDate(RandomTestUtil.nextDate());

		kaleoDefinitionVersion.setModifiedDate(RandomTestUtil.nextDate());

		kaleoDefinitionVersion.setName(RandomTestUtil.randomString());

		kaleoDefinitionVersion.setTitle(RandomTestUtil.randomString());

		kaleoDefinitionVersion.setDescription(RandomTestUtil.randomString());

		kaleoDefinitionVersion.setContent(RandomTestUtil.randomString());

		kaleoDefinitionVersion.setVersion(RandomTestUtil.randomString());

		kaleoDefinitionVersion.setStartKaleoNodeId(RandomTestUtil.nextLong());

		kaleoDefinitionVersion.setStatus(RandomTestUtil.nextInt());

		_kaleoDefinitionVersions.add(_persistence.update(kaleoDefinitionVersion));

		return kaleoDefinitionVersion;
	}

	private List<KaleoDefinitionVersion> _kaleoDefinitionVersions = new ArrayList<KaleoDefinitionVersion>();
	private KaleoDefinitionVersionPersistence _persistence;
	private ClassLoader _dynamicQueryClassLoader;
}