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
import com.liferay.commerce.product.exception.NoSuchCPDefinitionSpecificationOptionValueException;
import com.liferay.commerce.product.model.CPDefinitionSpecificationOptionValue;
import com.liferay.commerce.product.service.CPDefinitionSpecificationOptionValueLocalServiceUtil;
import com.liferay.commerce.product.service.persistence.CPDefinitionSpecificationOptionValuePersistence;
import com.liferay.commerce.product.service.persistence.CPDefinitionSpecificationOptionValueUtil;
import com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQueryFactoryUtil;
import com.liferay.portal.kernel.dao.orm.ProjectionFactoryUtil;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.RestrictionsFactoryUtil;
import com.liferay.portal.kernel.dao.orm.Session;
import com.liferay.portal.kernel.test.AssertUtils;
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
public class CPDefinitionSpecificationOptionValuePersistenceTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(), PersistenceTestRule.INSTANCE,
			new TransactionalTestRule(
				Propagation.REQUIRED, "com.liferay.commerce.product.service"));

	@Before
	public void setUp() {
		_persistence =
			CPDefinitionSpecificationOptionValueUtil.getPersistence();

		Class<?> clazz = _persistence.getClass();

		_dynamicQueryClassLoader = clazz.getClassLoader();
	}

	@After
	public void tearDown() throws Exception {
		Iterator<CPDefinitionSpecificationOptionValue> iterator =
			_cpDefinitionSpecificationOptionValues.iterator();

		while (iterator.hasNext()) {
			_persistence.remove(iterator.next());

			iterator.remove();
		}
	}

	@Test
	public void testCreate() throws Exception {
		long pk = RandomTestUtil.nextLong();

		CPDefinitionSpecificationOptionValue
			cpDefinitionSpecificationOptionValue = _persistence.create(pk);

		Assert.assertNotNull(cpDefinitionSpecificationOptionValue);

		Assert.assertEquals(
			cpDefinitionSpecificationOptionValue.getPrimaryKey(), pk);
	}

	@Test
	public void testRemove() throws Exception {
		CPDefinitionSpecificationOptionValue
			newCPDefinitionSpecificationOptionValue =
				addCPDefinitionSpecificationOptionValue();

		_persistence.remove(newCPDefinitionSpecificationOptionValue);

		CPDefinitionSpecificationOptionValue
			existingCPDefinitionSpecificationOptionValue =
				_persistence.fetchByPrimaryKey(
					newCPDefinitionSpecificationOptionValue.getPrimaryKey());

		Assert.assertNull(existingCPDefinitionSpecificationOptionValue);
	}

	@Test
	public void testUpdateNew() throws Exception {
		addCPDefinitionSpecificationOptionValue();
	}

	@Test
	public void testUpdateExisting() throws Exception {
		long pk = RandomTestUtil.nextLong();

		CPDefinitionSpecificationOptionValue
			newCPDefinitionSpecificationOptionValue = _persistence.create(pk);

		newCPDefinitionSpecificationOptionValue.setMvccVersion(
			RandomTestUtil.nextLong());

		newCPDefinitionSpecificationOptionValue.setCtCollectionId(
			RandomTestUtil.nextLong());

		newCPDefinitionSpecificationOptionValue.setUuid(
			RandomTestUtil.randomString());

		newCPDefinitionSpecificationOptionValue.setGroupId(
			RandomTestUtil.nextLong());

		newCPDefinitionSpecificationOptionValue.setCompanyId(
			RandomTestUtil.nextLong());

		newCPDefinitionSpecificationOptionValue.setUserId(
			RandomTestUtil.nextLong());

		newCPDefinitionSpecificationOptionValue.setUserName(
			RandomTestUtil.randomString());

		newCPDefinitionSpecificationOptionValue.setCreateDate(
			RandomTestUtil.nextDate());

		newCPDefinitionSpecificationOptionValue.setModifiedDate(
			RandomTestUtil.nextDate());

		newCPDefinitionSpecificationOptionValue.setCPDefinitionId(
			RandomTestUtil.nextLong());

		newCPDefinitionSpecificationOptionValue.setCPSpecificationOptionId(
			RandomTestUtil.nextLong());

		newCPDefinitionSpecificationOptionValue.setCPOptionCategoryId(
			RandomTestUtil.nextLong());

		newCPDefinitionSpecificationOptionValue.setValue(
			RandomTestUtil.randomString());

		newCPDefinitionSpecificationOptionValue.setPriority(
			RandomTestUtil.nextDouble());

		newCPDefinitionSpecificationOptionValue.setLastPublishDate(
			RandomTestUtil.nextDate());

		_cpDefinitionSpecificationOptionValues.add(
			_persistence.update(newCPDefinitionSpecificationOptionValue));

		CPDefinitionSpecificationOptionValue
			existingCPDefinitionSpecificationOptionValue =
				_persistence.findByPrimaryKey(
					newCPDefinitionSpecificationOptionValue.getPrimaryKey());

		Assert.assertEquals(
			existingCPDefinitionSpecificationOptionValue.getMvccVersion(),
			newCPDefinitionSpecificationOptionValue.getMvccVersion());
		Assert.assertEquals(
			existingCPDefinitionSpecificationOptionValue.getCtCollectionId(),
			newCPDefinitionSpecificationOptionValue.getCtCollectionId());
		Assert.assertEquals(
			existingCPDefinitionSpecificationOptionValue.getUuid(),
			newCPDefinitionSpecificationOptionValue.getUuid());
		Assert.assertEquals(
			existingCPDefinitionSpecificationOptionValue.
				getCPDefinitionSpecificationOptionValueId(),
			newCPDefinitionSpecificationOptionValue.
				getCPDefinitionSpecificationOptionValueId());
		Assert.assertEquals(
			existingCPDefinitionSpecificationOptionValue.getGroupId(),
			newCPDefinitionSpecificationOptionValue.getGroupId());
		Assert.assertEquals(
			existingCPDefinitionSpecificationOptionValue.getCompanyId(),
			newCPDefinitionSpecificationOptionValue.getCompanyId());
		Assert.assertEquals(
			existingCPDefinitionSpecificationOptionValue.getUserId(),
			newCPDefinitionSpecificationOptionValue.getUserId());
		Assert.assertEquals(
			existingCPDefinitionSpecificationOptionValue.getUserName(),
			newCPDefinitionSpecificationOptionValue.getUserName());
		Assert.assertEquals(
			Time.getShortTimestamp(
				existingCPDefinitionSpecificationOptionValue.getCreateDate()),
			Time.getShortTimestamp(
				newCPDefinitionSpecificationOptionValue.getCreateDate()));
		Assert.assertEquals(
			Time.getShortTimestamp(
				existingCPDefinitionSpecificationOptionValue.getModifiedDate()),
			Time.getShortTimestamp(
				newCPDefinitionSpecificationOptionValue.getModifiedDate()));
		Assert.assertEquals(
			existingCPDefinitionSpecificationOptionValue.getCPDefinitionId(),
			newCPDefinitionSpecificationOptionValue.getCPDefinitionId());
		Assert.assertEquals(
			existingCPDefinitionSpecificationOptionValue.
				getCPSpecificationOptionId(),
			newCPDefinitionSpecificationOptionValue.
				getCPSpecificationOptionId());
		Assert.assertEquals(
			existingCPDefinitionSpecificationOptionValue.
				getCPOptionCategoryId(),
			newCPDefinitionSpecificationOptionValue.getCPOptionCategoryId());
		Assert.assertEquals(
			existingCPDefinitionSpecificationOptionValue.getValue(),
			newCPDefinitionSpecificationOptionValue.getValue());
		AssertUtils.assertEquals(
			existingCPDefinitionSpecificationOptionValue.getPriority(),
			newCPDefinitionSpecificationOptionValue.getPriority());
		Assert.assertEquals(
			Time.getShortTimestamp(
				existingCPDefinitionSpecificationOptionValue.
					getLastPublishDate()),
			Time.getShortTimestamp(
				newCPDefinitionSpecificationOptionValue.getLastPublishDate()));
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
	public void testCountByCPDefinitionId() throws Exception {
		_persistence.countByCPDefinitionId(RandomTestUtil.nextLong());

		_persistence.countByCPDefinitionId(0L);
	}

	@Test
	public void testCountByCPSpecificationOptionId() throws Exception {
		_persistence.countByCPSpecificationOptionId(RandomTestUtil.nextLong());

		_persistence.countByCPSpecificationOptionId(0L);
	}

	@Test
	public void testCountByCPOptionCategoryId() throws Exception {
		_persistence.countByCPOptionCategoryId(RandomTestUtil.nextLong());

		_persistence.countByCPOptionCategoryId(0L);
	}

	@Test
	public void testCountByC_CSOVI() throws Exception {
		_persistence.countByC_CSOVI(
			RandomTestUtil.nextLong(), RandomTestUtil.nextLong());

		_persistence.countByC_CSOVI(0L, 0L);
	}

	@Test
	public void testCountByC_CSO() throws Exception {
		_persistence.countByC_CSO(
			RandomTestUtil.nextLong(), RandomTestUtil.nextLong());

		_persistence.countByC_CSO(0L, 0L);
	}

	@Test
	public void testCountByC_COC() throws Exception {
		_persistence.countByC_COC(
			RandomTestUtil.nextLong(), RandomTestUtil.nextLong());

		_persistence.countByC_COC(0L, 0L);
	}

	@Test
	public void testFindByPrimaryKeyExisting() throws Exception {
		CPDefinitionSpecificationOptionValue
			newCPDefinitionSpecificationOptionValue =
				addCPDefinitionSpecificationOptionValue();

		CPDefinitionSpecificationOptionValue
			existingCPDefinitionSpecificationOptionValue =
				_persistence.findByPrimaryKey(
					newCPDefinitionSpecificationOptionValue.getPrimaryKey());

		Assert.assertEquals(
			existingCPDefinitionSpecificationOptionValue,
			newCPDefinitionSpecificationOptionValue);
	}

	@Test(expected = NoSuchCPDefinitionSpecificationOptionValueException.class)
	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		_persistence.findByPrimaryKey(pk);
	}

	@Test
	public void testFindAll() throws Exception {
		_persistence.findAll(
			QueryUtil.ALL_POS, QueryUtil.ALL_POS, getOrderByComparator());
	}

	protected OrderByComparator<CPDefinitionSpecificationOptionValue>
		getOrderByComparator() {

		return OrderByComparatorFactoryUtil.create(
			"CPDSpecificationOptionValue", "mvccVersion", true,
			"ctCollectionId", true, "uuid", true,
			"CPDefinitionSpecificationOptionValueId", true, "groupId", true,
			"companyId", true, "userId", true, "userName", true, "createDate",
			true, "modifiedDate", true, "CPDefinitionId", true,
			"CPSpecificationOptionId", true, "CPOptionCategoryId", true,
			"value", true, "priority", true, "lastPublishDate", true);
	}

	@Test
	public void testFetchByPrimaryKeyExisting() throws Exception {
		CPDefinitionSpecificationOptionValue
			newCPDefinitionSpecificationOptionValue =
				addCPDefinitionSpecificationOptionValue();

		CPDefinitionSpecificationOptionValue
			existingCPDefinitionSpecificationOptionValue =
				_persistence.fetchByPrimaryKey(
					newCPDefinitionSpecificationOptionValue.getPrimaryKey());

		Assert.assertEquals(
			existingCPDefinitionSpecificationOptionValue,
			newCPDefinitionSpecificationOptionValue);
	}

	@Test
	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		CPDefinitionSpecificationOptionValue
			missingCPDefinitionSpecificationOptionValue =
				_persistence.fetchByPrimaryKey(pk);

		Assert.assertNull(missingCPDefinitionSpecificationOptionValue);
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereAllPrimaryKeysExist()
		throws Exception {

		CPDefinitionSpecificationOptionValue
			newCPDefinitionSpecificationOptionValue1 =
				addCPDefinitionSpecificationOptionValue();
		CPDefinitionSpecificationOptionValue
			newCPDefinitionSpecificationOptionValue2 =
				addCPDefinitionSpecificationOptionValue();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(
			newCPDefinitionSpecificationOptionValue1.getPrimaryKey());
		primaryKeys.add(
			newCPDefinitionSpecificationOptionValue2.getPrimaryKey());

		Map<Serializable, CPDefinitionSpecificationOptionValue>
			cpDefinitionSpecificationOptionValues =
				_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(2, cpDefinitionSpecificationOptionValues.size());
		Assert.assertEquals(
			newCPDefinitionSpecificationOptionValue1,
			cpDefinitionSpecificationOptionValues.get(
				newCPDefinitionSpecificationOptionValue1.getPrimaryKey()));
		Assert.assertEquals(
			newCPDefinitionSpecificationOptionValue2,
			cpDefinitionSpecificationOptionValues.get(
				newCPDefinitionSpecificationOptionValue2.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereNoPrimaryKeysExist()
		throws Exception {

		long pk1 = RandomTestUtil.nextLong();

		long pk2 = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(pk1);
		primaryKeys.add(pk2);

		Map<Serializable, CPDefinitionSpecificationOptionValue>
			cpDefinitionSpecificationOptionValues =
				_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(cpDefinitionSpecificationOptionValues.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereSomePrimaryKeysExist()
		throws Exception {

		CPDefinitionSpecificationOptionValue
			newCPDefinitionSpecificationOptionValue =
				addCPDefinitionSpecificationOptionValue();

		long pk = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(
			newCPDefinitionSpecificationOptionValue.getPrimaryKey());
		primaryKeys.add(pk);

		Map<Serializable, CPDefinitionSpecificationOptionValue>
			cpDefinitionSpecificationOptionValues =
				_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, cpDefinitionSpecificationOptionValues.size());
		Assert.assertEquals(
			newCPDefinitionSpecificationOptionValue,
			cpDefinitionSpecificationOptionValues.get(
				newCPDefinitionSpecificationOptionValue.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithNoPrimaryKeys() throws Exception {
		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		Map<Serializable, CPDefinitionSpecificationOptionValue>
			cpDefinitionSpecificationOptionValues =
				_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(cpDefinitionSpecificationOptionValues.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithOnePrimaryKey() throws Exception {
		CPDefinitionSpecificationOptionValue
			newCPDefinitionSpecificationOptionValue =
				addCPDefinitionSpecificationOptionValue();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(
			newCPDefinitionSpecificationOptionValue.getPrimaryKey());

		Map<Serializable, CPDefinitionSpecificationOptionValue>
			cpDefinitionSpecificationOptionValues =
				_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, cpDefinitionSpecificationOptionValues.size());
		Assert.assertEquals(
			newCPDefinitionSpecificationOptionValue,
			cpDefinitionSpecificationOptionValues.get(
				newCPDefinitionSpecificationOptionValue.getPrimaryKey()));
	}

	@Test
	public void testActionableDynamicQuery() throws Exception {
		final IntegerWrapper count = new IntegerWrapper();

		ActionableDynamicQuery actionableDynamicQuery =
			CPDefinitionSpecificationOptionValueLocalServiceUtil.
				getActionableDynamicQuery();

		actionableDynamicQuery.setPerformActionMethod(
			new ActionableDynamicQuery.PerformActionMethod
				<CPDefinitionSpecificationOptionValue>() {

				@Override
				public void performAction(
					CPDefinitionSpecificationOptionValue
						cpDefinitionSpecificationOptionValue) {

					Assert.assertNotNull(cpDefinitionSpecificationOptionValue);

					count.increment();
				}

			});

		actionableDynamicQuery.performActions();

		Assert.assertEquals(count.getValue(), _persistence.countAll());
	}

	@Test
	public void testDynamicQueryByPrimaryKeyExisting() throws Exception {
		CPDefinitionSpecificationOptionValue
			newCPDefinitionSpecificationOptionValue =
				addCPDefinitionSpecificationOptionValue();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			CPDefinitionSpecificationOptionValue.class,
			_dynamicQueryClassLoader);

		dynamicQuery.add(
			RestrictionsFactoryUtil.eq(
				"CPDefinitionSpecificationOptionValueId",
				newCPDefinitionSpecificationOptionValue.
					getCPDefinitionSpecificationOptionValueId()));

		List<CPDefinitionSpecificationOptionValue> result =
			_persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		CPDefinitionSpecificationOptionValue
			existingCPDefinitionSpecificationOptionValue = result.get(0);

		Assert.assertEquals(
			existingCPDefinitionSpecificationOptionValue,
			newCPDefinitionSpecificationOptionValue);
	}

	@Test
	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			CPDefinitionSpecificationOptionValue.class,
			_dynamicQueryClassLoader);

		dynamicQuery.add(
			RestrictionsFactoryUtil.eq(
				"CPDefinitionSpecificationOptionValueId",
				RandomTestUtil.nextLong()));

		List<CPDefinitionSpecificationOptionValue> result =
			_persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testDynamicQueryByProjectionExisting() throws Exception {
		CPDefinitionSpecificationOptionValue
			newCPDefinitionSpecificationOptionValue =
				addCPDefinitionSpecificationOptionValue();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			CPDefinitionSpecificationOptionValue.class,
			_dynamicQueryClassLoader);

		dynamicQuery.setProjection(
			ProjectionFactoryUtil.property(
				"CPDefinitionSpecificationOptionValueId"));

		Object newCPDefinitionSpecificationOptionValueId =
			newCPDefinitionSpecificationOptionValue.
				getCPDefinitionSpecificationOptionValueId();

		dynamicQuery.add(
			RestrictionsFactoryUtil.in(
				"CPDefinitionSpecificationOptionValueId",
				new Object[] {newCPDefinitionSpecificationOptionValueId}));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		Object existingCPDefinitionSpecificationOptionValueId = result.get(0);

		Assert.assertEquals(
			existingCPDefinitionSpecificationOptionValueId,
			newCPDefinitionSpecificationOptionValueId);
	}

	@Test
	public void testDynamicQueryByProjectionMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			CPDefinitionSpecificationOptionValue.class,
			_dynamicQueryClassLoader);

		dynamicQuery.setProjection(
			ProjectionFactoryUtil.property(
				"CPDefinitionSpecificationOptionValueId"));

		dynamicQuery.add(
			RestrictionsFactoryUtil.in(
				"CPDefinitionSpecificationOptionValueId",
				new Object[] {RandomTestUtil.nextLong()}));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testResetOriginalValues() throws Exception {
		CPDefinitionSpecificationOptionValue
			newCPDefinitionSpecificationOptionValue =
				addCPDefinitionSpecificationOptionValue();

		_persistence.clearCache();

		_assertOriginalValues(
			_persistence.findByPrimaryKey(
				newCPDefinitionSpecificationOptionValue.getPrimaryKey()));
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

		CPDefinitionSpecificationOptionValue
			newCPDefinitionSpecificationOptionValue =
				addCPDefinitionSpecificationOptionValue();

		if (clearSession) {
			Session session = _persistence.openSession();

			session.flush();

			session.clear();
		}

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			CPDefinitionSpecificationOptionValue.class,
			_dynamicQueryClassLoader);

		dynamicQuery.add(
			RestrictionsFactoryUtil.eq(
				"CPDefinitionSpecificationOptionValueId",
				newCPDefinitionSpecificationOptionValue.
					getCPDefinitionSpecificationOptionValueId()));

		List<CPDefinitionSpecificationOptionValue> result =
			_persistence.findWithDynamicQuery(dynamicQuery);

		_assertOriginalValues(result.get(0));
	}

	private void _assertOriginalValues(
		CPDefinitionSpecificationOptionValue
			cpDefinitionSpecificationOptionValue) {

		Assert.assertEquals(
			cpDefinitionSpecificationOptionValue.getUuid(),
			ReflectionTestUtil.invoke(
				cpDefinitionSpecificationOptionValue, "getColumnOriginalValue",
				new Class<?>[] {String.class}, "uuid_"));
		Assert.assertEquals(
			Long.valueOf(cpDefinitionSpecificationOptionValue.getGroupId()),
			ReflectionTestUtil.<Long>invoke(
				cpDefinitionSpecificationOptionValue, "getColumnOriginalValue",
				new Class<?>[] {String.class}, "groupId"));

		Assert.assertEquals(
			Long.valueOf(
				cpDefinitionSpecificationOptionValue.
					getCPDefinitionSpecificationOptionValueId()),
			ReflectionTestUtil.<Long>invoke(
				cpDefinitionSpecificationOptionValue, "getColumnOriginalValue",
				new Class<?>[] {String.class},
				"CPDSpecificationOptionValueId"));
		Assert.assertEquals(
			Long.valueOf(
				cpDefinitionSpecificationOptionValue.getCPDefinitionId()),
			ReflectionTestUtil.<Long>invoke(
				cpDefinitionSpecificationOptionValue, "getColumnOriginalValue",
				new Class<?>[] {String.class}, "CPDefinitionId"));
	}

	protected CPDefinitionSpecificationOptionValue
			addCPDefinitionSpecificationOptionValue()
		throws Exception {

		long pk = RandomTestUtil.nextLong();

		CPDefinitionSpecificationOptionValue
			cpDefinitionSpecificationOptionValue = _persistence.create(pk);

		cpDefinitionSpecificationOptionValue.setMvccVersion(
			RandomTestUtil.nextLong());

		cpDefinitionSpecificationOptionValue.setCtCollectionId(
			RandomTestUtil.nextLong());

		cpDefinitionSpecificationOptionValue.setUuid(
			RandomTestUtil.randomString());

		cpDefinitionSpecificationOptionValue.setGroupId(
			RandomTestUtil.nextLong());

		cpDefinitionSpecificationOptionValue.setCompanyId(
			RandomTestUtil.nextLong());

		cpDefinitionSpecificationOptionValue.setUserId(
			RandomTestUtil.nextLong());

		cpDefinitionSpecificationOptionValue.setUserName(
			RandomTestUtil.randomString());

		cpDefinitionSpecificationOptionValue.setCreateDate(
			RandomTestUtil.nextDate());

		cpDefinitionSpecificationOptionValue.setModifiedDate(
			RandomTestUtil.nextDate());

		cpDefinitionSpecificationOptionValue.setCPDefinitionId(
			RandomTestUtil.nextLong());

		cpDefinitionSpecificationOptionValue.setCPSpecificationOptionId(
			RandomTestUtil.nextLong());

		cpDefinitionSpecificationOptionValue.setCPOptionCategoryId(
			RandomTestUtil.nextLong());

		cpDefinitionSpecificationOptionValue.setValue(
			RandomTestUtil.randomString());

		cpDefinitionSpecificationOptionValue.setPriority(
			RandomTestUtil.nextDouble());

		cpDefinitionSpecificationOptionValue.setLastPublishDate(
			RandomTestUtil.nextDate());

		_cpDefinitionSpecificationOptionValues.add(
			_persistence.update(cpDefinitionSpecificationOptionValue));

		return cpDefinitionSpecificationOptionValue;
	}

	private List<CPDefinitionSpecificationOptionValue>
		_cpDefinitionSpecificationOptionValues =
			new ArrayList<CPDefinitionSpecificationOptionValue>();
	private CPDefinitionSpecificationOptionValuePersistence _persistence;
	private ClassLoader _dynamicQueryClassLoader;

}