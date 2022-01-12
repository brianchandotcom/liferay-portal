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
import com.liferay.commerce.product.exception.NoSuchCPDefinitionOptionValueRelException;
import com.liferay.commerce.product.model.CPDefinitionOptionValueRel;
import com.liferay.commerce.product.service.CPDefinitionOptionValueRelLocalServiceUtil;
import com.liferay.commerce.product.service.persistence.CPDefinitionOptionValueRelPersistence;
import com.liferay.commerce.product.service.persistence.CPDefinitionOptionValueRelUtil;
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

import java.math.BigDecimal;

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
public class CPDefinitionOptionValueRelPersistenceTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(), PersistenceTestRule.INSTANCE,
			new TransactionalTestRule(
				Propagation.REQUIRED, "com.liferay.commerce.product.service"));

	@Before
	public void setUp() {
		_persistence = CPDefinitionOptionValueRelUtil.getPersistence();

		Class<?> clazz = _persistence.getClass();

		_dynamicQueryClassLoader = clazz.getClassLoader();
	}

	@After
	public void tearDown() throws Exception {
		Iterator<CPDefinitionOptionValueRel> iterator =
			_cpDefinitionOptionValueRels.iterator();

		while (iterator.hasNext()) {
			_persistence.remove(iterator.next());

			iterator.remove();
		}
	}

	@Test
	public void testCreate() throws Exception {
		long pk = RandomTestUtil.nextLong();

		CPDefinitionOptionValueRel cpDefinitionOptionValueRel =
			_persistence.create(pk);

		Assert.assertNotNull(cpDefinitionOptionValueRel);

		Assert.assertEquals(cpDefinitionOptionValueRel.getPrimaryKey(), pk);
	}

	@Test
	public void testRemove() throws Exception {
		CPDefinitionOptionValueRel newCPDefinitionOptionValueRel =
			addCPDefinitionOptionValueRel();

		_persistence.remove(newCPDefinitionOptionValueRel);

		CPDefinitionOptionValueRel existingCPDefinitionOptionValueRel =
			_persistence.fetchByPrimaryKey(
				newCPDefinitionOptionValueRel.getPrimaryKey());

		Assert.assertNull(existingCPDefinitionOptionValueRel);
	}

	@Test
	public void testUpdateNew() throws Exception {
		addCPDefinitionOptionValueRel();
	}

	@Test
	public void testUpdateExisting() throws Exception {
		long pk = RandomTestUtil.nextLong();

		CPDefinitionOptionValueRel newCPDefinitionOptionValueRel =
			_persistence.create(pk);

		newCPDefinitionOptionValueRel.setMvccVersion(RandomTestUtil.nextLong());

		newCPDefinitionOptionValueRel.setCtCollectionId(
			RandomTestUtil.nextLong());

		newCPDefinitionOptionValueRel.setUuid(RandomTestUtil.randomString());

		newCPDefinitionOptionValueRel.setGroupId(RandomTestUtil.nextLong());

		newCPDefinitionOptionValueRel.setCompanyId(RandomTestUtil.nextLong());

		newCPDefinitionOptionValueRel.setUserId(RandomTestUtil.nextLong());

		newCPDefinitionOptionValueRel.setUserName(
			RandomTestUtil.randomString());

		newCPDefinitionOptionValueRel.setCreateDate(RandomTestUtil.nextDate());

		newCPDefinitionOptionValueRel.setModifiedDate(
			RandomTestUtil.nextDate());

		newCPDefinitionOptionValueRel.setCPDefinitionOptionRelId(
			RandomTestUtil.nextLong());

		newCPDefinitionOptionValueRel.setCPInstanceUuid(
			RandomTestUtil.randomString());

		newCPDefinitionOptionValueRel.setCProductId(RandomTestUtil.nextLong());

		newCPDefinitionOptionValueRel.setName(RandomTestUtil.randomString());

		newCPDefinitionOptionValueRel.setPriority(RandomTestUtil.nextDouble());

		newCPDefinitionOptionValueRel.setKey(RandomTestUtil.randomString());

		newCPDefinitionOptionValueRel.setQuantity(RandomTestUtil.nextInt());

		newCPDefinitionOptionValueRel.setPreselected(
			RandomTestUtil.randomBoolean());

		newCPDefinitionOptionValueRel.setPrice(
			new BigDecimal(RandomTestUtil.nextDouble()));

		_cpDefinitionOptionValueRels.add(
			_persistence.update(newCPDefinitionOptionValueRel));

		CPDefinitionOptionValueRel existingCPDefinitionOptionValueRel =
			_persistence.findByPrimaryKey(
				newCPDefinitionOptionValueRel.getPrimaryKey());

		Assert.assertEquals(
			existingCPDefinitionOptionValueRel.getMvccVersion(),
			newCPDefinitionOptionValueRel.getMvccVersion());
		Assert.assertEquals(
			existingCPDefinitionOptionValueRel.getCtCollectionId(),
			newCPDefinitionOptionValueRel.getCtCollectionId());
		Assert.assertEquals(
			existingCPDefinitionOptionValueRel.getUuid(),
			newCPDefinitionOptionValueRel.getUuid());
		Assert.assertEquals(
			existingCPDefinitionOptionValueRel.
				getCPDefinitionOptionValueRelId(),
			newCPDefinitionOptionValueRel.getCPDefinitionOptionValueRelId());
		Assert.assertEquals(
			existingCPDefinitionOptionValueRel.getGroupId(),
			newCPDefinitionOptionValueRel.getGroupId());
		Assert.assertEquals(
			existingCPDefinitionOptionValueRel.getCompanyId(),
			newCPDefinitionOptionValueRel.getCompanyId());
		Assert.assertEquals(
			existingCPDefinitionOptionValueRel.getUserId(),
			newCPDefinitionOptionValueRel.getUserId());
		Assert.assertEquals(
			existingCPDefinitionOptionValueRel.getUserName(),
			newCPDefinitionOptionValueRel.getUserName());
		Assert.assertEquals(
			Time.getShortTimestamp(
				existingCPDefinitionOptionValueRel.getCreateDate()),
			Time.getShortTimestamp(
				newCPDefinitionOptionValueRel.getCreateDate()));
		Assert.assertEquals(
			Time.getShortTimestamp(
				existingCPDefinitionOptionValueRel.getModifiedDate()),
			Time.getShortTimestamp(
				newCPDefinitionOptionValueRel.getModifiedDate()));
		Assert.assertEquals(
			existingCPDefinitionOptionValueRel.getCPDefinitionOptionRelId(),
			newCPDefinitionOptionValueRel.getCPDefinitionOptionRelId());
		Assert.assertEquals(
			existingCPDefinitionOptionValueRel.getCPInstanceUuid(),
			newCPDefinitionOptionValueRel.getCPInstanceUuid());
		Assert.assertEquals(
			existingCPDefinitionOptionValueRel.getCProductId(),
			newCPDefinitionOptionValueRel.getCProductId());
		Assert.assertEquals(
			existingCPDefinitionOptionValueRel.getName(),
			newCPDefinitionOptionValueRel.getName());
		AssertUtils.assertEquals(
			existingCPDefinitionOptionValueRel.getPriority(),
			newCPDefinitionOptionValueRel.getPriority());
		Assert.assertEquals(
			existingCPDefinitionOptionValueRel.getKey(),
			newCPDefinitionOptionValueRel.getKey());
		Assert.assertEquals(
			existingCPDefinitionOptionValueRel.getQuantity(),
			newCPDefinitionOptionValueRel.getQuantity());
		Assert.assertEquals(
			existingCPDefinitionOptionValueRel.isPreselected(),
			newCPDefinitionOptionValueRel.isPreselected());
		Assert.assertEquals(
			existingCPDefinitionOptionValueRel.getPrice(),
			newCPDefinitionOptionValueRel.getPrice());
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
	public void testCountByCompanyId() throws Exception {
		_persistence.countByCompanyId(RandomTestUtil.nextLong());

		_persistence.countByCompanyId(0L);
	}

	@Test
	public void testCountByCPDefinitionOptionRelId() throws Exception {
		_persistence.countByCPDefinitionOptionRelId(RandomTestUtil.nextLong());

		_persistence.countByCPDefinitionOptionRelId(0L);
	}

	@Test
	public void testCountByCPInstanceUuid() throws Exception {
		_persistence.countByCPInstanceUuid("");

		_persistence.countByCPInstanceUuid("null");

		_persistence.countByCPInstanceUuid((String)null);
	}

	@Test
	public void testCountByKey() throws Exception {
		_persistence.countByKey("");

		_persistence.countByKey("null");

		_persistence.countByKey((String)null);
	}

	@Test
	public void testCountByC_K() throws Exception {
		_persistence.countByC_K(RandomTestUtil.nextLong(), "");

		_persistence.countByC_K(0L, "null");

		_persistence.countByC_K(0L, (String)null);
	}

	@Test
	public void testCountByCDORI_P() throws Exception {
		_persistence.countByCDORI_P(
			RandomTestUtil.nextLong(), RandomTestUtil.randomBoolean());

		_persistence.countByCDORI_P(0L, RandomTestUtil.randomBoolean());
	}

	@Test
	public void testFindByPrimaryKeyExisting() throws Exception {
		CPDefinitionOptionValueRel newCPDefinitionOptionValueRel =
			addCPDefinitionOptionValueRel();

		CPDefinitionOptionValueRel existingCPDefinitionOptionValueRel =
			_persistence.findByPrimaryKey(
				newCPDefinitionOptionValueRel.getPrimaryKey());

		Assert.assertEquals(
			existingCPDefinitionOptionValueRel, newCPDefinitionOptionValueRel);
	}

	@Test(expected = NoSuchCPDefinitionOptionValueRelException.class)
	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		_persistence.findByPrimaryKey(pk);
	}

	@Test
	public void testFindAll() throws Exception {
		_persistence.findAll(
			QueryUtil.ALL_POS, QueryUtil.ALL_POS, getOrderByComparator());
	}

	protected OrderByComparator<CPDefinitionOptionValueRel>
		getOrderByComparator() {

		return OrderByComparatorFactoryUtil.create(
			"CPDefinitionOptionValueRel", "mvccVersion", true, "ctCollectionId",
			true, "uuid", true, "CPDefinitionOptionValueRelId", true, "groupId",
			true, "companyId", true, "userId", true, "userName", true,
			"createDate", true, "modifiedDate", true, "CPDefinitionOptionRelId",
			true, "CPInstanceUuid", true, "CProductId", true, "name", true,
			"priority", true, "key", true, "quantity", true, "preselected",
			true, "price", true);
	}

	@Test
	public void testFetchByPrimaryKeyExisting() throws Exception {
		CPDefinitionOptionValueRel newCPDefinitionOptionValueRel =
			addCPDefinitionOptionValueRel();

		CPDefinitionOptionValueRel existingCPDefinitionOptionValueRel =
			_persistence.fetchByPrimaryKey(
				newCPDefinitionOptionValueRel.getPrimaryKey());

		Assert.assertEquals(
			existingCPDefinitionOptionValueRel, newCPDefinitionOptionValueRel);
	}

	@Test
	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		CPDefinitionOptionValueRel missingCPDefinitionOptionValueRel =
			_persistence.fetchByPrimaryKey(pk);

		Assert.assertNull(missingCPDefinitionOptionValueRel);
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereAllPrimaryKeysExist()
		throws Exception {

		CPDefinitionOptionValueRel newCPDefinitionOptionValueRel1 =
			addCPDefinitionOptionValueRel();
		CPDefinitionOptionValueRel newCPDefinitionOptionValueRel2 =
			addCPDefinitionOptionValueRel();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newCPDefinitionOptionValueRel1.getPrimaryKey());
		primaryKeys.add(newCPDefinitionOptionValueRel2.getPrimaryKey());

		Map<Serializable, CPDefinitionOptionValueRel>
			cpDefinitionOptionValueRels = _persistence.fetchByPrimaryKeys(
				primaryKeys);

		Assert.assertEquals(2, cpDefinitionOptionValueRels.size());
		Assert.assertEquals(
			newCPDefinitionOptionValueRel1,
			cpDefinitionOptionValueRels.get(
				newCPDefinitionOptionValueRel1.getPrimaryKey()));
		Assert.assertEquals(
			newCPDefinitionOptionValueRel2,
			cpDefinitionOptionValueRels.get(
				newCPDefinitionOptionValueRel2.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereNoPrimaryKeysExist()
		throws Exception {

		long pk1 = RandomTestUtil.nextLong();

		long pk2 = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(pk1);
		primaryKeys.add(pk2);

		Map<Serializable, CPDefinitionOptionValueRel>
			cpDefinitionOptionValueRels = _persistence.fetchByPrimaryKeys(
				primaryKeys);

		Assert.assertTrue(cpDefinitionOptionValueRels.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereSomePrimaryKeysExist()
		throws Exception {

		CPDefinitionOptionValueRel newCPDefinitionOptionValueRel =
			addCPDefinitionOptionValueRel();

		long pk = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newCPDefinitionOptionValueRel.getPrimaryKey());
		primaryKeys.add(pk);

		Map<Serializable, CPDefinitionOptionValueRel>
			cpDefinitionOptionValueRels = _persistence.fetchByPrimaryKeys(
				primaryKeys);

		Assert.assertEquals(1, cpDefinitionOptionValueRels.size());
		Assert.assertEquals(
			newCPDefinitionOptionValueRel,
			cpDefinitionOptionValueRels.get(
				newCPDefinitionOptionValueRel.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithNoPrimaryKeys() throws Exception {
		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		Map<Serializable, CPDefinitionOptionValueRel>
			cpDefinitionOptionValueRels = _persistence.fetchByPrimaryKeys(
				primaryKeys);

		Assert.assertTrue(cpDefinitionOptionValueRels.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithOnePrimaryKey() throws Exception {
		CPDefinitionOptionValueRel newCPDefinitionOptionValueRel =
			addCPDefinitionOptionValueRel();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newCPDefinitionOptionValueRel.getPrimaryKey());

		Map<Serializable, CPDefinitionOptionValueRel>
			cpDefinitionOptionValueRels = _persistence.fetchByPrimaryKeys(
				primaryKeys);

		Assert.assertEquals(1, cpDefinitionOptionValueRels.size());
		Assert.assertEquals(
			newCPDefinitionOptionValueRel,
			cpDefinitionOptionValueRels.get(
				newCPDefinitionOptionValueRel.getPrimaryKey()));
	}

	@Test
	public void testActionableDynamicQuery() throws Exception {
		final IntegerWrapper count = new IntegerWrapper();

		ActionableDynamicQuery actionableDynamicQuery =
			CPDefinitionOptionValueRelLocalServiceUtil.
				getActionableDynamicQuery();

		actionableDynamicQuery.setPerformActionMethod(
			new ActionableDynamicQuery.PerformActionMethod
				<CPDefinitionOptionValueRel>() {

				@Override
				public void performAction(
					CPDefinitionOptionValueRel cpDefinitionOptionValueRel) {

					Assert.assertNotNull(cpDefinitionOptionValueRel);

					count.increment();
				}

			});

		actionableDynamicQuery.performActions();

		Assert.assertEquals(count.getValue(), _persistence.countAll());
	}

	@Test
	public void testDynamicQueryByPrimaryKeyExisting() throws Exception {
		CPDefinitionOptionValueRel newCPDefinitionOptionValueRel =
			addCPDefinitionOptionValueRel();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			CPDefinitionOptionValueRel.class, _dynamicQueryClassLoader);

		dynamicQuery.add(
			RestrictionsFactoryUtil.eq(
				"CPDefinitionOptionValueRelId",
				newCPDefinitionOptionValueRel.
					getCPDefinitionOptionValueRelId()));

		List<CPDefinitionOptionValueRel> result =
			_persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		CPDefinitionOptionValueRel existingCPDefinitionOptionValueRel =
			result.get(0);

		Assert.assertEquals(
			existingCPDefinitionOptionValueRel, newCPDefinitionOptionValueRel);
	}

	@Test
	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			CPDefinitionOptionValueRel.class, _dynamicQueryClassLoader);

		dynamicQuery.add(
			RestrictionsFactoryUtil.eq(
				"CPDefinitionOptionValueRelId", RandomTestUtil.nextLong()));

		List<CPDefinitionOptionValueRel> result =
			_persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testDynamicQueryByProjectionExisting() throws Exception {
		CPDefinitionOptionValueRel newCPDefinitionOptionValueRel =
			addCPDefinitionOptionValueRel();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			CPDefinitionOptionValueRel.class, _dynamicQueryClassLoader);

		dynamicQuery.setProjection(
			ProjectionFactoryUtil.property("CPDefinitionOptionValueRelId"));

		Object newCPDefinitionOptionValueRelId =
			newCPDefinitionOptionValueRel.getCPDefinitionOptionValueRelId();

		dynamicQuery.add(
			RestrictionsFactoryUtil.in(
				"CPDefinitionOptionValueRelId",
				new Object[] {newCPDefinitionOptionValueRelId}));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		Object existingCPDefinitionOptionValueRelId = result.get(0);

		Assert.assertEquals(
			existingCPDefinitionOptionValueRelId,
			newCPDefinitionOptionValueRelId);
	}

	@Test
	public void testDynamicQueryByProjectionMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			CPDefinitionOptionValueRel.class, _dynamicQueryClassLoader);

		dynamicQuery.setProjection(
			ProjectionFactoryUtil.property("CPDefinitionOptionValueRelId"));

		dynamicQuery.add(
			RestrictionsFactoryUtil.in(
				"CPDefinitionOptionValueRelId",
				new Object[] {RandomTestUtil.nextLong()}));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testResetOriginalValues() throws Exception {
		CPDefinitionOptionValueRel newCPDefinitionOptionValueRel =
			addCPDefinitionOptionValueRel();

		_persistence.clearCache();

		_assertOriginalValues(
			_persistence.findByPrimaryKey(
				newCPDefinitionOptionValueRel.getPrimaryKey()));
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

		CPDefinitionOptionValueRel newCPDefinitionOptionValueRel =
			addCPDefinitionOptionValueRel();

		if (clearSession) {
			Session session = _persistence.openSession();

			session.flush();

			session.clear();
		}

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			CPDefinitionOptionValueRel.class, _dynamicQueryClassLoader);

		dynamicQuery.add(
			RestrictionsFactoryUtil.eq(
				"CPDefinitionOptionValueRelId",
				newCPDefinitionOptionValueRel.
					getCPDefinitionOptionValueRelId()));

		List<CPDefinitionOptionValueRel> result =
			_persistence.findWithDynamicQuery(dynamicQuery);

		_assertOriginalValues(result.get(0));
	}

	private void _assertOriginalValues(
		CPDefinitionOptionValueRel cpDefinitionOptionValueRel) {

		Assert.assertEquals(
			cpDefinitionOptionValueRel.getUuid(),
			ReflectionTestUtil.invoke(
				cpDefinitionOptionValueRel, "getColumnOriginalValue",
				new Class<?>[] {String.class}, "uuid_"));
		Assert.assertEquals(
			Long.valueOf(cpDefinitionOptionValueRel.getGroupId()),
			ReflectionTestUtil.<Long>invoke(
				cpDefinitionOptionValueRel, "getColumnOriginalValue",
				new Class<?>[] {String.class}, "groupId"));

		Assert.assertEquals(
			Long.valueOf(
				cpDefinitionOptionValueRel.getCPDefinitionOptionRelId()),
			ReflectionTestUtil.<Long>invoke(
				cpDefinitionOptionValueRel, "getColumnOriginalValue",
				new Class<?>[] {String.class}, "CPDefinitionOptionRelId"));
		Assert.assertEquals(
			cpDefinitionOptionValueRel.getKey(),
			ReflectionTestUtil.invoke(
				cpDefinitionOptionValueRel, "getColumnOriginalValue",
				new Class<?>[] {String.class}, "key_"));
	}

	protected CPDefinitionOptionValueRel addCPDefinitionOptionValueRel()
		throws Exception {

		long pk = RandomTestUtil.nextLong();

		CPDefinitionOptionValueRel cpDefinitionOptionValueRel =
			_persistence.create(pk);

		cpDefinitionOptionValueRel.setMvccVersion(RandomTestUtil.nextLong());

		cpDefinitionOptionValueRel.setCtCollectionId(RandomTestUtil.nextLong());

		cpDefinitionOptionValueRel.setUuid(RandomTestUtil.randomString());

		cpDefinitionOptionValueRel.setGroupId(RandomTestUtil.nextLong());

		cpDefinitionOptionValueRel.setCompanyId(RandomTestUtil.nextLong());

		cpDefinitionOptionValueRel.setUserId(RandomTestUtil.nextLong());

		cpDefinitionOptionValueRel.setUserName(RandomTestUtil.randomString());

		cpDefinitionOptionValueRel.setCreateDate(RandomTestUtil.nextDate());

		cpDefinitionOptionValueRel.setModifiedDate(RandomTestUtil.nextDate());

		cpDefinitionOptionValueRel.setCPDefinitionOptionRelId(
			RandomTestUtil.nextLong());

		cpDefinitionOptionValueRel.setCPInstanceUuid(
			RandomTestUtil.randomString());

		cpDefinitionOptionValueRel.setCProductId(RandomTestUtil.nextLong());

		cpDefinitionOptionValueRel.setName(RandomTestUtil.randomString());

		cpDefinitionOptionValueRel.setPriority(RandomTestUtil.nextDouble());

		cpDefinitionOptionValueRel.setKey(RandomTestUtil.randomString());

		cpDefinitionOptionValueRel.setQuantity(RandomTestUtil.nextInt());

		cpDefinitionOptionValueRel.setPreselected(
			RandomTestUtil.randomBoolean());

		cpDefinitionOptionValueRel.setPrice(
			new BigDecimal(RandomTestUtil.nextDouble()));

		_cpDefinitionOptionValueRels.add(
			_persistence.update(cpDefinitionOptionValueRel));

		return cpDefinitionOptionValueRel;
	}

	private List<CPDefinitionOptionValueRel> _cpDefinitionOptionValueRels =
		new ArrayList<CPDefinitionOptionValueRel>();
	private CPDefinitionOptionValueRelPersistence _persistence;
	private ClassLoader _dynamicQueryClassLoader;

}