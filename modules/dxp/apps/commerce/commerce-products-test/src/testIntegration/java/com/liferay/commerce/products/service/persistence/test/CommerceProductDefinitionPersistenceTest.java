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

package com.liferay.commerce.products.service.persistence.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;

import com.liferay.commerce.products.exception.NoSuchProductDefinitionException;
import com.liferay.commerce.products.model.CommerceProductDefinition;
import com.liferay.commerce.products.service.CommerceProductDefinitionLocalServiceUtil;
import com.liferay.commerce.products.service.persistence.CommerceProductDefinitionPersistence;
import com.liferay.commerce.products.service.persistence.CommerceProductDefinitionUtil;

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
public class CommerceProductDefinitionPersistenceTest {
	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule = new AggregateTestRule(new LiferayIntegrationTestRule(),
			PersistenceTestRule.INSTANCE,
			new TransactionalTestRule(Propagation.REQUIRED,
				"com.liferay.commerce.products.service"));

	@Before
	public void setUp() {
		_persistence = CommerceProductDefinitionUtil.getPersistence();

		Class<?> clazz = _persistence.getClass();

		_dynamicQueryClassLoader = clazz.getClassLoader();
	}

	@After
	public void tearDown() throws Exception {
		Iterator<CommerceProductDefinition> iterator = _commerceProductDefinitions.iterator();

		while (iterator.hasNext()) {
			_persistence.remove(iterator.next());

			iterator.remove();
		}
	}

	@Test
	public void testCreate() throws Exception {
		long pk = RandomTestUtil.nextLong();

		CommerceProductDefinition commerceProductDefinition = _persistence.create(pk);

		Assert.assertNotNull(commerceProductDefinition);

		Assert.assertEquals(commerceProductDefinition.getPrimaryKey(), pk);
	}

	@Test
	public void testRemove() throws Exception {
		CommerceProductDefinition newCommerceProductDefinition = addCommerceProductDefinition();

		_persistence.remove(newCommerceProductDefinition);

		CommerceProductDefinition existingCommerceProductDefinition = _persistence.fetchByPrimaryKey(newCommerceProductDefinition.getPrimaryKey());

		Assert.assertNull(existingCommerceProductDefinition);
	}

	@Test
	public void testUpdateNew() throws Exception {
		addCommerceProductDefinition();
	}

	@Test
	public void testUpdateExisting() throws Exception {
		long pk = RandomTestUtil.nextLong();

		CommerceProductDefinition newCommerceProductDefinition = _persistence.create(pk);

		newCommerceProductDefinition.setUuid(RandomTestUtil.randomString());

		newCommerceProductDefinition.setGroupId(RandomTestUtil.nextLong());

		newCommerceProductDefinition.setCompanyId(RandomTestUtil.nextLong());

		newCommerceProductDefinition.setUserId(RandomTestUtil.nextLong());

		newCommerceProductDefinition.setUserName(RandomTestUtil.randomString());

		newCommerceProductDefinition.setCreateDate(RandomTestUtil.nextDate());

		newCommerceProductDefinition.setModifiedDate(RandomTestUtil.nextDate());

		newCommerceProductDefinition.setTitle(RandomTestUtil.randomString());

		newCommerceProductDefinition.setUrlTitle(RandomTestUtil.randomString());

		newCommerceProductDefinition.setDescription(RandomTestUtil.randomString());

		newCommerceProductDefinition.setProductTypeName(RandomTestUtil.randomString());

		newCommerceProductDefinition.setAvailableIndividually(RandomTestUtil.randomBoolean());

		newCommerceProductDefinition.setDDMStructureKey(RandomTestUtil.randomString());

		newCommerceProductDefinition.setBaseSKU(RandomTestUtil.randomString());

		newCommerceProductDefinition.setDisplayDate(RandomTestUtil.nextDate());

		newCommerceProductDefinition.setExpirationDate(RandomTestUtil.nextDate());

		newCommerceProductDefinition.setLastPublishDate(RandomTestUtil.nextDate());

		newCommerceProductDefinition.setStatus(RandomTestUtil.nextInt());

		newCommerceProductDefinition.setStatusByUserId(RandomTestUtil.nextLong());

		newCommerceProductDefinition.setStatusByUserName(RandomTestUtil.randomString());

		newCommerceProductDefinition.setStatusDate(RandomTestUtil.nextDate());

		_commerceProductDefinitions.add(_persistence.update(
				newCommerceProductDefinition));

		CommerceProductDefinition existingCommerceProductDefinition = _persistence.findByPrimaryKey(newCommerceProductDefinition.getPrimaryKey());

		Assert.assertEquals(existingCommerceProductDefinition.getUuid(),
			newCommerceProductDefinition.getUuid());
		Assert.assertEquals(existingCommerceProductDefinition.getCommerceProductDefinitionId(),
			newCommerceProductDefinition.getCommerceProductDefinitionId());
		Assert.assertEquals(existingCommerceProductDefinition.getGroupId(),
			newCommerceProductDefinition.getGroupId());
		Assert.assertEquals(existingCommerceProductDefinition.getCompanyId(),
			newCommerceProductDefinition.getCompanyId());
		Assert.assertEquals(existingCommerceProductDefinition.getUserId(),
			newCommerceProductDefinition.getUserId());
		Assert.assertEquals(existingCommerceProductDefinition.getUserName(),
			newCommerceProductDefinition.getUserName());
		Assert.assertEquals(Time.getShortTimestamp(
				existingCommerceProductDefinition.getCreateDate()),
			Time.getShortTimestamp(newCommerceProductDefinition.getCreateDate()));
		Assert.assertEquals(Time.getShortTimestamp(
				existingCommerceProductDefinition.getModifiedDate()),
			Time.getShortTimestamp(
				newCommerceProductDefinition.getModifiedDate()));
		Assert.assertEquals(existingCommerceProductDefinition.getTitle(),
			newCommerceProductDefinition.getTitle());
		Assert.assertEquals(existingCommerceProductDefinition.getUrlTitle(),
			newCommerceProductDefinition.getUrlTitle());
		Assert.assertEquals(existingCommerceProductDefinition.getDescription(),
			newCommerceProductDefinition.getDescription());
		Assert.assertEquals(existingCommerceProductDefinition.getProductTypeName(),
			newCommerceProductDefinition.getProductTypeName());
		Assert.assertEquals(existingCommerceProductDefinition.getAvailableIndividually(),
			newCommerceProductDefinition.getAvailableIndividually());
		Assert.assertEquals(existingCommerceProductDefinition.getDDMStructureKey(),
			newCommerceProductDefinition.getDDMStructureKey());
		Assert.assertEquals(existingCommerceProductDefinition.getBaseSKU(),
			newCommerceProductDefinition.getBaseSKU());
		Assert.assertEquals(Time.getShortTimestamp(
				existingCommerceProductDefinition.getDisplayDate()),
			Time.getShortTimestamp(
				newCommerceProductDefinition.getDisplayDate()));
		Assert.assertEquals(Time.getShortTimestamp(
				existingCommerceProductDefinition.getExpirationDate()),
			Time.getShortTimestamp(
				newCommerceProductDefinition.getExpirationDate()));
		Assert.assertEquals(Time.getShortTimestamp(
				existingCommerceProductDefinition.getLastPublishDate()),
			Time.getShortTimestamp(
				newCommerceProductDefinition.getLastPublishDate()));
		Assert.assertEquals(existingCommerceProductDefinition.getStatus(),
			newCommerceProductDefinition.getStatus());
		Assert.assertEquals(existingCommerceProductDefinition.getStatusByUserId(),
			newCommerceProductDefinition.getStatusByUserId());
		Assert.assertEquals(existingCommerceProductDefinition.getStatusByUserName(),
			newCommerceProductDefinition.getStatusByUserName());
		Assert.assertEquals(Time.getShortTimestamp(
				existingCommerceProductDefinition.getStatusDate()),
			Time.getShortTimestamp(newCommerceProductDefinition.getStatusDate()));
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
	public void testCountByGroupId() throws Exception {
		_persistence.countByGroupId(RandomTestUtil.nextLong());

		_persistence.countByGroupId(0L);
	}

	@Test
	public void testCountByCompanyId() throws Exception {
		_persistence.countByCompanyId(RandomTestUtil.nextLong());

		_persistence.countByCompanyId(0L);
	}

	@Test
	public void testFindByPrimaryKeyExisting() throws Exception {
		CommerceProductDefinition newCommerceProductDefinition = addCommerceProductDefinition();

		CommerceProductDefinition existingCommerceProductDefinition = _persistence.findByPrimaryKey(newCommerceProductDefinition.getPrimaryKey());

		Assert.assertEquals(existingCommerceProductDefinition,
			newCommerceProductDefinition);
	}

	@Test(expected = NoSuchProductDefinitionException.class)
	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		_persistence.findByPrimaryKey(pk);
	}

	@Test
	public void testFindAll() throws Exception {
		_persistence.findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			getOrderByComparator());
	}

	protected OrderByComparator<CommerceProductDefinition> getOrderByComparator() {
		return OrderByComparatorFactoryUtil.create("CommerceProductDefinition",
			"uuid", true, "commerceProductDefinitionId", true, "groupId", true,
			"companyId", true, "userId", true, "userName", true, "createDate",
			true, "modifiedDate", true, "title", true, "urlTitle", true,
			"description", true, "productTypeName", true,
			"availableIndividually", true, "DDMStructureKey", true, "baseSKU",
			true, "displayDate", true, "expirationDate", true,
			"lastPublishDate", true, "status", true, "statusByUserId", true,
			"statusByUserName", true, "statusDate", true);
	}

	@Test
	public void testFetchByPrimaryKeyExisting() throws Exception {
		CommerceProductDefinition newCommerceProductDefinition = addCommerceProductDefinition();

		CommerceProductDefinition existingCommerceProductDefinition = _persistence.fetchByPrimaryKey(newCommerceProductDefinition.getPrimaryKey());

		Assert.assertEquals(existingCommerceProductDefinition,
			newCommerceProductDefinition);
	}

	@Test
	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		CommerceProductDefinition missingCommerceProductDefinition = _persistence.fetchByPrimaryKey(pk);

		Assert.assertNull(missingCommerceProductDefinition);
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereAllPrimaryKeysExist()
		throws Exception {
		CommerceProductDefinition newCommerceProductDefinition1 = addCommerceProductDefinition();
		CommerceProductDefinition newCommerceProductDefinition2 = addCommerceProductDefinition();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newCommerceProductDefinition1.getPrimaryKey());
		primaryKeys.add(newCommerceProductDefinition2.getPrimaryKey());

		Map<Serializable, CommerceProductDefinition> commerceProductDefinitions = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(2, commerceProductDefinitions.size());
		Assert.assertEquals(newCommerceProductDefinition1,
			commerceProductDefinitions.get(
				newCommerceProductDefinition1.getPrimaryKey()));
		Assert.assertEquals(newCommerceProductDefinition2,
			commerceProductDefinitions.get(
				newCommerceProductDefinition2.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereNoPrimaryKeysExist()
		throws Exception {
		long pk1 = RandomTestUtil.nextLong();

		long pk2 = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(pk1);
		primaryKeys.add(pk2);

		Map<Serializable, CommerceProductDefinition> commerceProductDefinitions = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(commerceProductDefinitions.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereSomePrimaryKeysExist()
		throws Exception {
		CommerceProductDefinition newCommerceProductDefinition = addCommerceProductDefinition();

		long pk = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newCommerceProductDefinition.getPrimaryKey());
		primaryKeys.add(pk);

		Map<Serializable, CommerceProductDefinition> commerceProductDefinitions = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, commerceProductDefinitions.size());
		Assert.assertEquals(newCommerceProductDefinition,
			commerceProductDefinitions.get(
				newCommerceProductDefinition.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithNoPrimaryKeys()
		throws Exception {
		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		Map<Serializable, CommerceProductDefinition> commerceProductDefinitions = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(commerceProductDefinitions.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithOnePrimaryKey()
		throws Exception {
		CommerceProductDefinition newCommerceProductDefinition = addCommerceProductDefinition();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newCommerceProductDefinition.getPrimaryKey());

		Map<Serializable, CommerceProductDefinition> commerceProductDefinitions = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, commerceProductDefinitions.size());
		Assert.assertEquals(newCommerceProductDefinition,
			commerceProductDefinitions.get(
				newCommerceProductDefinition.getPrimaryKey()));
	}

	@Test
	public void testActionableDynamicQuery() throws Exception {
		final IntegerWrapper count = new IntegerWrapper();

		ActionableDynamicQuery actionableDynamicQuery = CommerceProductDefinitionLocalServiceUtil.getActionableDynamicQuery();

		actionableDynamicQuery.setPerformActionMethod(new ActionableDynamicQuery.PerformActionMethod<CommerceProductDefinition>() {
				@Override
				public void performAction(
					CommerceProductDefinition commerceProductDefinition) {
					Assert.assertNotNull(commerceProductDefinition);

					count.increment();
				}
			});

		actionableDynamicQuery.performActions();

		Assert.assertEquals(count.getValue(), _persistence.countAll());
	}

	@Test
	public void testDynamicQueryByPrimaryKeyExisting()
		throws Exception {
		CommerceProductDefinition newCommerceProductDefinition = addCommerceProductDefinition();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(CommerceProductDefinition.class,
				_dynamicQueryClassLoader);

		dynamicQuery.add(RestrictionsFactoryUtil.eq(
				"commerceProductDefinitionId",
				newCommerceProductDefinition.getCommerceProductDefinitionId()));

		List<CommerceProductDefinition> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		CommerceProductDefinition existingCommerceProductDefinition = result.get(0);

		Assert.assertEquals(existingCommerceProductDefinition,
			newCommerceProductDefinition);
	}

	@Test
	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(CommerceProductDefinition.class,
				_dynamicQueryClassLoader);

		dynamicQuery.add(RestrictionsFactoryUtil.eq(
				"commerceProductDefinitionId", RandomTestUtil.nextLong()));

		List<CommerceProductDefinition> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testDynamicQueryByProjectionExisting()
		throws Exception {
		CommerceProductDefinition newCommerceProductDefinition = addCommerceProductDefinition();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(CommerceProductDefinition.class,
				_dynamicQueryClassLoader);

		dynamicQuery.setProjection(ProjectionFactoryUtil.property(
				"commerceProductDefinitionId"));

		Object newCommerceProductDefinitionId = newCommerceProductDefinition.getCommerceProductDefinitionId();

		dynamicQuery.add(RestrictionsFactoryUtil.in(
				"commerceProductDefinitionId",
				new Object[] { newCommerceProductDefinitionId }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		Object existingCommerceProductDefinitionId = result.get(0);

		Assert.assertEquals(existingCommerceProductDefinitionId,
			newCommerceProductDefinitionId);
	}

	@Test
	public void testDynamicQueryByProjectionMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(CommerceProductDefinition.class,
				_dynamicQueryClassLoader);

		dynamicQuery.setProjection(ProjectionFactoryUtil.property(
				"commerceProductDefinitionId"));

		dynamicQuery.add(RestrictionsFactoryUtil.in(
				"commerceProductDefinitionId",
				new Object[] { RandomTestUtil.nextLong() }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testResetOriginalValues() throws Exception {
		CommerceProductDefinition newCommerceProductDefinition = addCommerceProductDefinition();

		_persistence.clearCache();

		CommerceProductDefinition existingCommerceProductDefinition = _persistence.findByPrimaryKey(newCommerceProductDefinition.getPrimaryKey());

		Assert.assertTrue(Objects.equals(
				existingCommerceProductDefinition.getUuid(),
				ReflectionTestUtil.invoke(existingCommerceProductDefinition,
					"getOriginalUuid", new Class<?>[0])));
		Assert.assertEquals(Long.valueOf(
				existingCommerceProductDefinition.getGroupId()),
			ReflectionTestUtil.<Long>invoke(existingCommerceProductDefinition,
				"getOriginalGroupId", new Class<?>[0]));
	}

	protected CommerceProductDefinition addCommerceProductDefinition()
		throws Exception {
		long pk = RandomTestUtil.nextLong();

		CommerceProductDefinition commerceProductDefinition = _persistence.create(pk);

		commerceProductDefinition.setUuid(RandomTestUtil.randomString());

		commerceProductDefinition.setGroupId(RandomTestUtil.nextLong());

		commerceProductDefinition.setCompanyId(RandomTestUtil.nextLong());

		commerceProductDefinition.setUserId(RandomTestUtil.nextLong());

		commerceProductDefinition.setUserName(RandomTestUtil.randomString());

		commerceProductDefinition.setCreateDate(RandomTestUtil.nextDate());

		commerceProductDefinition.setModifiedDate(RandomTestUtil.nextDate());

		commerceProductDefinition.setTitle(RandomTestUtil.randomString());

		commerceProductDefinition.setUrlTitle(RandomTestUtil.randomString());

		commerceProductDefinition.setDescription(RandomTestUtil.randomString());

		commerceProductDefinition.setProductTypeName(RandomTestUtil.randomString());

		commerceProductDefinition.setAvailableIndividually(RandomTestUtil.randomBoolean());

		commerceProductDefinition.setDDMStructureKey(RandomTestUtil.randomString());

		commerceProductDefinition.setBaseSKU(RandomTestUtil.randomString());

		commerceProductDefinition.setDisplayDate(RandomTestUtil.nextDate());

		commerceProductDefinition.setExpirationDate(RandomTestUtil.nextDate());

		commerceProductDefinition.setLastPublishDate(RandomTestUtil.nextDate());

		commerceProductDefinition.setStatus(RandomTestUtil.nextInt());

		commerceProductDefinition.setStatusByUserId(RandomTestUtil.nextLong());

		commerceProductDefinition.setStatusByUserName(RandomTestUtil.randomString());

		commerceProductDefinition.setStatusDate(RandomTestUtil.nextDate());

		_commerceProductDefinitions.add(_persistence.update(
				commerceProductDefinition));

		return commerceProductDefinition;
	}

	private List<CommerceProductDefinition> _commerceProductDefinitions = new ArrayList<CommerceProductDefinition>();
	private CommerceProductDefinitionPersistence _persistence;
	private ClassLoader _dynamicQueryClassLoader;
}