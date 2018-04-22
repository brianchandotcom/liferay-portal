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

package com.liferay.commerce.product.service.persistence.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;

import com.liferay.commerce.product.exception.NoSuchCPSpecificationOptionException;
import com.liferay.commerce.product.model.CPSpecificationOption;
import com.liferay.commerce.product.service.CPSpecificationOptionLocalServiceUtil;
import com.liferay.commerce.product.service.persistence.CPSpecificationOptionPersistence;
import com.liferay.commerce.product.service.persistence.CPSpecificationOptionUtil;

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
public class CPSpecificationOptionPersistenceTest {
	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule = new AggregateTestRule(new LiferayIntegrationTestRule(),
			PersistenceTestRule.INSTANCE,
			new TransactionalTestRule(Propagation.REQUIRED,
				"com.liferay.commerce.product.service"));

	@Before
	public void setUp() {
		_persistence = CPSpecificationOptionUtil.getPersistence();

		Class<?> clazz = _persistence.getClass();

		_dynamicQueryClassLoader = clazz.getClassLoader();
	}

	@After
	public void tearDown() throws Exception {
		Iterator<CPSpecificationOption> iterator = _cpSpecificationOptions.iterator();

		while (iterator.hasNext()) {
			_persistence.remove(iterator.next());

			iterator.remove();
		}
	}

	@Test
	public void testCreate() throws Exception {
		long pk = RandomTestUtil.nextLong();

		CPSpecificationOption cpSpecificationOption = _persistence.create(pk);

		Assert.assertNotNull(cpSpecificationOption);

		Assert.assertEquals(cpSpecificationOption.getPrimaryKey(), pk);
	}

	@Test
	public void testRemove() throws Exception {
		CPSpecificationOption newCPSpecificationOption = addCPSpecificationOption();

		_persistence.remove(newCPSpecificationOption);

		CPSpecificationOption existingCPSpecificationOption = _persistence.fetchByPrimaryKey(newCPSpecificationOption.getPrimaryKey());

		Assert.assertNull(existingCPSpecificationOption);
	}

	@Test
	public void testUpdateNew() throws Exception {
		addCPSpecificationOption();
	}

	@Test
	public void testUpdateExisting() throws Exception {
		long pk = RandomTestUtil.nextLong();

		CPSpecificationOption newCPSpecificationOption = _persistence.create(pk);

		newCPSpecificationOption.setUuid(RandomTestUtil.randomString());

		newCPSpecificationOption.setGroupId(RandomTestUtil.nextLong());

		newCPSpecificationOption.setCompanyId(RandomTestUtil.nextLong());

		newCPSpecificationOption.setUserId(RandomTestUtil.nextLong());

		newCPSpecificationOption.setUserName(RandomTestUtil.randomString());

		newCPSpecificationOption.setCreateDate(RandomTestUtil.nextDate());

		newCPSpecificationOption.setModifiedDate(RandomTestUtil.nextDate());

		newCPSpecificationOption.setCPOptionCategoryId(RandomTestUtil.nextLong());

		newCPSpecificationOption.setTitle(RandomTestUtil.randomString());

		newCPSpecificationOption.setDescription(RandomTestUtil.randomString());

		newCPSpecificationOption.setFacetable(RandomTestUtil.randomBoolean());

		newCPSpecificationOption.setKey(RandomTestUtil.randomString());

		newCPSpecificationOption.setLastPublishDate(RandomTestUtil.nextDate());

		_cpSpecificationOptions.add(_persistence.update(
				newCPSpecificationOption));

		CPSpecificationOption existingCPSpecificationOption = _persistence.findByPrimaryKey(newCPSpecificationOption.getPrimaryKey());

		Assert.assertEquals(existingCPSpecificationOption.getUuid(),
			newCPSpecificationOption.getUuid());
		Assert.assertEquals(existingCPSpecificationOption.getCPSpecificationOptionId(),
			newCPSpecificationOption.getCPSpecificationOptionId());
		Assert.assertEquals(existingCPSpecificationOption.getGroupId(),
			newCPSpecificationOption.getGroupId());
		Assert.assertEquals(existingCPSpecificationOption.getCompanyId(),
			newCPSpecificationOption.getCompanyId());
		Assert.assertEquals(existingCPSpecificationOption.getUserId(),
			newCPSpecificationOption.getUserId());
		Assert.assertEquals(existingCPSpecificationOption.getUserName(),
			newCPSpecificationOption.getUserName());
		Assert.assertEquals(Time.getShortTimestamp(
				existingCPSpecificationOption.getCreateDate()),
			Time.getShortTimestamp(newCPSpecificationOption.getCreateDate()));
		Assert.assertEquals(Time.getShortTimestamp(
				existingCPSpecificationOption.getModifiedDate()),
			Time.getShortTimestamp(newCPSpecificationOption.getModifiedDate()));
		Assert.assertEquals(existingCPSpecificationOption.getCPOptionCategoryId(),
			newCPSpecificationOption.getCPOptionCategoryId());
		Assert.assertEquals(existingCPSpecificationOption.getTitle(),
			newCPSpecificationOption.getTitle());
		Assert.assertEquals(existingCPSpecificationOption.getDescription(),
			newCPSpecificationOption.getDescription());
		Assert.assertEquals(existingCPSpecificationOption.isFacetable(),
			newCPSpecificationOption.isFacetable());
		Assert.assertEquals(existingCPSpecificationOption.getKey(),
			newCPSpecificationOption.getKey());
		Assert.assertEquals(Time.getShortTimestamp(
				existingCPSpecificationOption.getLastPublishDate()),
			Time.getShortTimestamp(
				newCPSpecificationOption.getLastPublishDate()));
	}

	@Test
	public void testCountByUuid() throws Exception {
		_persistence.countByUuid("");

		_persistence.countByUuid("null");

		_persistence.countByUuid((String)null);
	}

	@Test
	public void testCountByUUID_G() throws Exception {
		_persistence.countByUUID_G("", RandomTestUtil.nextLong());

		_persistence.countByUUID_G("null", 0L);

		_persistence.countByUUID_G((String)null, 0L);
	}

	@Test
	public void testCountByUuid_C() throws Exception {
		_persistence.countByUuid_C("", RandomTestUtil.nextLong());

		_persistence.countByUuid_C("null", 0L);

		_persistence.countByUuid_C((String)null, 0L);
	}

	@Test
	public void testCountByGroupId() throws Exception {
		_persistence.countByGroupId(RandomTestUtil.nextLong());

		_persistence.countByGroupId(0L);
	}

	@Test
	public void testCountByCPOptionCategoryId() throws Exception {
		_persistence.countByCPOptionCategoryId(RandomTestUtil.nextLong());

		_persistence.countByCPOptionCategoryId(0L);
	}

	@Test
	public void testCountByG_K() throws Exception {
		_persistence.countByG_K(RandomTestUtil.nextLong(), "");

		_persistence.countByG_K(0L, "null");

		_persistence.countByG_K(0L, (String)null);
	}

	@Test
	public void testFindByPrimaryKeyExisting() throws Exception {
		CPSpecificationOption newCPSpecificationOption = addCPSpecificationOption();

		CPSpecificationOption existingCPSpecificationOption = _persistence.findByPrimaryKey(newCPSpecificationOption.getPrimaryKey());

		Assert.assertEquals(existingCPSpecificationOption,
			newCPSpecificationOption);
	}

	@Test(expected = NoSuchCPSpecificationOptionException.class)
	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		_persistence.findByPrimaryKey(pk);
	}

	@Test
	public void testFindAll() throws Exception {
		_persistence.findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			getOrderByComparator());
	}

	@Test
	public void testFilterFindByGroupId() throws Exception {
		_persistence.filterFindByGroupId(0, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, getOrderByComparator());
	}

	protected OrderByComparator<CPSpecificationOption> getOrderByComparator() {
		return OrderByComparatorFactoryUtil.create("CPSpecificationOption",
			"uuid", true, "CPSpecificationOptionId", true, "groupId", true,
			"companyId", true, "userId", true, "userName", true, "createDate",
			true, "modifiedDate", true, "CPOptionCategoryId", true, "title",
			true, "description", true, "facetable", true, "key", true,
			"lastPublishDate", true);
	}

	@Test
	public void testFetchByPrimaryKeyExisting() throws Exception {
		CPSpecificationOption newCPSpecificationOption = addCPSpecificationOption();

		CPSpecificationOption existingCPSpecificationOption = _persistence.fetchByPrimaryKey(newCPSpecificationOption.getPrimaryKey());

		Assert.assertEquals(existingCPSpecificationOption,
			newCPSpecificationOption);
	}

	@Test
	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		CPSpecificationOption missingCPSpecificationOption = _persistence.fetchByPrimaryKey(pk);

		Assert.assertNull(missingCPSpecificationOption);
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereAllPrimaryKeysExist()
		throws Exception {
		CPSpecificationOption newCPSpecificationOption1 = addCPSpecificationOption();
		CPSpecificationOption newCPSpecificationOption2 = addCPSpecificationOption();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newCPSpecificationOption1.getPrimaryKey());
		primaryKeys.add(newCPSpecificationOption2.getPrimaryKey());

		Map<Serializable, CPSpecificationOption> cpSpecificationOptions = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(2, cpSpecificationOptions.size());
		Assert.assertEquals(newCPSpecificationOption1,
			cpSpecificationOptions.get(
				newCPSpecificationOption1.getPrimaryKey()));
		Assert.assertEquals(newCPSpecificationOption2,
			cpSpecificationOptions.get(
				newCPSpecificationOption2.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereNoPrimaryKeysExist()
		throws Exception {
		long pk1 = RandomTestUtil.nextLong();

		long pk2 = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(pk1);
		primaryKeys.add(pk2);

		Map<Serializable, CPSpecificationOption> cpSpecificationOptions = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(cpSpecificationOptions.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereSomePrimaryKeysExist()
		throws Exception {
		CPSpecificationOption newCPSpecificationOption = addCPSpecificationOption();

		long pk = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newCPSpecificationOption.getPrimaryKey());
		primaryKeys.add(pk);

		Map<Serializable, CPSpecificationOption> cpSpecificationOptions = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, cpSpecificationOptions.size());
		Assert.assertEquals(newCPSpecificationOption,
			cpSpecificationOptions.get(newCPSpecificationOption.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithNoPrimaryKeys()
		throws Exception {
		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		Map<Serializable, CPSpecificationOption> cpSpecificationOptions = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(cpSpecificationOptions.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithOnePrimaryKey()
		throws Exception {
		CPSpecificationOption newCPSpecificationOption = addCPSpecificationOption();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newCPSpecificationOption.getPrimaryKey());

		Map<Serializable, CPSpecificationOption> cpSpecificationOptions = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, cpSpecificationOptions.size());
		Assert.assertEquals(newCPSpecificationOption,
			cpSpecificationOptions.get(newCPSpecificationOption.getPrimaryKey()));
	}

	@Test
	public void testActionableDynamicQuery() throws Exception {
		final IntegerWrapper count = new IntegerWrapper();

		ActionableDynamicQuery actionableDynamicQuery = CPSpecificationOptionLocalServiceUtil.getActionableDynamicQuery();

		actionableDynamicQuery.setPerformActionMethod(new ActionableDynamicQuery.PerformActionMethod<CPSpecificationOption>() {
				@Override
				public void performAction(
					CPSpecificationOption cpSpecificationOption) {
					Assert.assertNotNull(cpSpecificationOption);

					count.increment();
				}
			});

		actionableDynamicQuery.performActions();

		Assert.assertEquals(count.getValue(), _persistence.countAll());
	}

	@Test
	public void testDynamicQueryByPrimaryKeyExisting()
		throws Exception {
		CPSpecificationOption newCPSpecificationOption = addCPSpecificationOption();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(CPSpecificationOption.class,
				_dynamicQueryClassLoader);

		dynamicQuery.add(RestrictionsFactoryUtil.eq("CPSpecificationOptionId",
				newCPSpecificationOption.getCPSpecificationOptionId()));

		List<CPSpecificationOption> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		CPSpecificationOption existingCPSpecificationOption = result.get(0);

		Assert.assertEquals(existingCPSpecificationOption,
			newCPSpecificationOption);
	}

	@Test
	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(CPSpecificationOption.class,
				_dynamicQueryClassLoader);

		dynamicQuery.add(RestrictionsFactoryUtil.eq("CPSpecificationOptionId",
				RandomTestUtil.nextLong()));

		List<CPSpecificationOption> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testDynamicQueryByProjectionExisting()
		throws Exception {
		CPSpecificationOption newCPSpecificationOption = addCPSpecificationOption();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(CPSpecificationOption.class,
				_dynamicQueryClassLoader);

		dynamicQuery.setProjection(ProjectionFactoryUtil.property(
				"CPSpecificationOptionId"));

		Object newCPSpecificationOptionId = newCPSpecificationOption.getCPSpecificationOptionId();

		dynamicQuery.add(RestrictionsFactoryUtil.in("CPSpecificationOptionId",
				new Object[] { newCPSpecificationOptionId }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		Object existingCPSpecificationOptionId = result.get(0);

		Assert.assertEquals(existingCPSpecificationOptionId,
			newCPSpecificationOptionId);
	}

	@Test
	public void testDynamicQueryByProjectionMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(CPSpecificationOption.class,
				_dynamicQueryClassLoader);

		dynamicQuery.setProjection(ProjectionFactoryUtil.property(
				"CPSpecificationOptionId"));

		dynamicQuery.add(RestrictionsFactoryUtil.in("CPSpecificationOptionId",
				new Object[] { RandomTestUtil.nextLong() }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testResetOriginalValues() throws Exception {
		CPSpecificationOption newCPSpecificationOption = addCPSpecificationOption();

		_persistence.clearCache();

		CPSpecificationOption existingCPSpecificationOption = _persistence.findByPrimaryKey(newCPSpecificationOption.getPrimaryKey());

		Assert.assertTrue(Objects.equals(
				existingCPSpecificationOption.getUuid(),
				ReflectionTestUtil.invoke(existingCPSpecificationOption,
					"getOriginalUuid", new Class<?>[0])));
		Assert.assertEquals(Long.valueOf(
				existingCPSpecificationOption.getGroupId()),
			ReflectionTestUtil.<Long>invoke(existingCPSpecificationOption,
				"getOriginalGroupId", new Class<?>[0]));

		Assert.assertEquals(Long.valueOf(
				existingCPSpecificationOption.getGroupId()),
			ReflectionTestUtil.<Long>invoke(existingCPSpecificationOption,
				"getOriginalGroupId", new Class<?>[0]));
		Assert.assertTrue(Objects.equals(
				existingCPSpecificationOption.getKey(),
				ReflectionTestUtil.invoke(existingCPSpecificationOption,
					"getOriginalKey", new Class<?>[0])));
	}

	protected CPSpecificationOption addCPSpecificationOption()
		throws Exception {
		long pk = RandomTestUtil.nextLong();

		CPSpecificationOption cpSpecificationOption = _persistence.create(pk);

		cpSpecificationOption.setUuid(RandomTestUtil.randomString());

		cpSpecificationOption.setGroupId(RandomTestUtil.nextLong());

		cpSpecificationOption.setCompanyId(RandomTestUtil.nextLong());

		cpSpecificationOption.setUserId(RandomTestUtil.nextLong());

		cpSpecificationOption.setUserName(RandomTestUtil.randomString());

		cpSpecificationOption.setCreateDate(RandomTestUtil.nextDate());

		cpSpecificationOption.setModifiedDate(RandomTestUtil.nextDate());

		cpSpecificationOption.setCPOptionCategoryId(RandomTestUtil.nextLong());

		cpSpecificationOption.setTitle(RandomTestUtil.randomString());

		cpSpecificationOption.setDescription(RandomTestUtil.randomString());

		cpSpecificationOption.setFacetable(RandomTestUtil.randomBoolean());

		cpSpecificationOption.setKey(RandomTestUtil.randomString());

		cpSpecificationOption.setLastPublishDate(RandomTestUtil.nextDate());

		_cpSpecificationOptions.add(_persistence.update(cpSpecificationOption));

		return cpSpecificationOption;
	}

	private List<CPSpecificationOption> _cpSpecificationOptions = new ArrayList<CPSpecificationOption>();
	private CPSpecificationOptionPersistence _persistence;
	private ClassLoader _dynamicQueryClassLoader;
}