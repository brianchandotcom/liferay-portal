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

package com.liferay.commerce.service.persistence.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.commerce.exception.NoSuchShipmentException;
import com.liferay.commerce.model.CommerceShipment;
import com.liferay.commerce.service.CommerceShipmentLocalServiceUtil;
import com.liferay.commerce.service.persistence.CommerceShipmentPersistence;
import com.liferay.commerce.service.persistence.CommerceShipmentUtil;
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
public class CommerceShipmentPersistenceTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(), PersistenceTestRule.INSTANCE,
			new TransactionalTestRule(
				Propagation.REQUIRED, "com.liferay.commerce.service"));

	@Before
	public void setUp() {
		_persistence = CommerceShipmentUtil.getPersistence();

		Class<?> clazz = _persistence.getClass();

		_dynamicQueryClassLoader = clazz.getClassLoader();
	}

	@After
	public void tearDown() throws Exception {
		Iterator<CommerceShipment> iterator = _commerceShipments.iterator();

		while (iterator.hasNext()) {
			_persistence.remove(iterator.next());

			iterator.remove();
		}
	}

	@Test
	public void testCreate() throws Exception {
		long pk = RandomTestUtil.nextLong();

		CommerceShipment commerceShipment = _persistence.create(pk);

		Assert.assertNotNull(commerceShipment);

		Assert.assertEquals(commerceShipment.getPrimaryKey(), pk);
	}

	@Test
	public void testRemove() throws Exception {
		CommerceShipment newCommerceShipment = addCommerceShipment();

		_persistence.remove(newCommerceShipment);

		CommerceShipment existingCommerceShipment =
			_persistence.fetchByPrimaryKey(newCommerceShipment.getPrimaryKey());

		Assert.assertNull(existingCommerceShipment);
	}

	@Test
	public void testUpdateNew() throws Exception {
		addCommerceShipment();
	}

	@Test
	public void testUpdateExisting() throws Exception {
		long pk = RandomTestUtil.nextLong();

		CommerceShipment newCommerceShipment = _persistence.create(pk);

		newCommerceShipment.setMvccVersion(RandomTestUtil.nextLong());

		newCommerceShipment.setExternalReferenceCode(
			RandomTestUtil.randomString());

		newCommerceShipment.setGroupId(RandomTestUtil.nextLong());

		newCommerceShipment.setCompanyId(RandomTestUtil.nextLong());

		newCommerceShipment.setUserId(RandomTestUtil.nextLong());

		newCommerceShipment.setUserName(RandomTestUtil.randomString());

		newCommerceShipment.setCreateDate(RandomTestUtil.nextDate());

		newCommerceShipment.setModifiedDate(RandomTestUtil.nextDate());

		newCommerceShipment.setCommerceAccountId(RandomTestUtil.nextLong());

		newCommerceShipment.setCommerceAddressId(RandomTestUtil.nextLong());

		newCommerceShipment.setCommerceShippingMethodId(
			RandomTestUtil.nextLong());

		newCommerceShipment.setShippingOptionName(
			RandomTestUtil.randomString());

		newCommerceShipment.setCarrier(RandomTestUtil.randomString());

		newCommerceShipment.setTrackingNumber(RandomTestUtil.randomString());

		newCommerceShipment.setShippingDate(RandomTestUtil.nextDate());

		newCommerceShipment.setExpectedDate(RandomTestUtil.nextDate());

		newCommerceShipment.setStatus(RandomTestUtil.nextInt());

		_commerceShipments.add(_persistence.update(newCommerceShipment));

		CommerceShipment existingCommerceShipment =
			_persistence.findByPrimaryKey(newCommerceShipment.getPrimaryKey());

		Assert.assertEquals(
			existingCommerceShipment.getMvccVersion(),
			newCommerceShipment.getMvccVersion());
		Assert.assertEquals(
			existingCommerceShipment.getExternalReferenceCode(),
			newCommerceShipment.getExternalReferenceCode());
		Assert.assertEquals(
			existingCommerceShipment.getCommerceShipmentId(),
			newCommerceShipment.getCommerceShipmentId());
		Assert.assertEquals(
			existingCommerceShipment.getGroupId(),
			newCommerceShipment.getGroupId());
		Assert.assertEquals(
			existingCommerceShipment.getCompanyId(),
			newCommerceShipment.getCompanyId());
		Assert.assertEquals(
			existingCommerceShipment.getUserId(),
			newCommerceShipment.getUserId());
		Assert.assertEquals(
			existingCommerceShipment.getUserName(),
			newCommerceShipment.getUserName());
		Assert.assertEquals(
			Time.getShortTimestamp(existingCommerceShipment.getCreateDate()),
			Time.getShortTimestamp(newCommerceShipment.getCreateDate()));
		Assert.assertEquals(
			Time.getShortTimestamp(existingCommerceShipment.getModifiedDate()),
			Time.getShortTimestamp(newCommerceShipment.getModifiedDate()));
		Assert.assertEquals(
			existingCommerceShipment.getCommerceAccountId(),
			newCommerceShipment.getCommerceAccountId());
		Assert.assertEquals(
			existingCommerceShipment.getCommerceAddressId(),
			newCommerceShipment.getCommerceAddressId());
		Assert.assertEquals(
			existingCommerceShipment.getCommerceShippingMethodId(),
			newCommerceShipment.getCommerceShippingMethodId());
		Assert.assertEquals(
			existingCommerceShipment.getShippingOptionName(),
			newCommerceShipment.getShippingOptionName());
		Assert.assertEquals(
			existingCommerceShipment.getCarrier(),
			newCommerceShipment.getCarrier());
		Assert.assertEquals(
			existingCommerceShipment.getTrackingNumber(),
			newCommerceShipment.getTrackingNumber());
		Assert.assertEquals(
			Time.getShortTimestamp(existingCommerceShipment.getShippingDate()),
			Time.getShortTimestamp(newCommerceShipment.getShippingDate()));
		Assert.assertEquals(
			Time.getShortTimestamp(existingCommerceShipment.getExpectedDate()),
			Time.getShortTimestamp(newCommerceShipment.getExpectedDate()));
		Assert.assertEquals(
			existingCommerceShipment.getStatus(),
			newCommerceShipment.getStatus());
	}

	@Test
	public void testCountByGroupId() throws Exception {
		_persistence.countByGroupId(RandomTestUtil.nextLong());

		_persistence.countByGroupId(0L);
	}

	@Test
	public void testCountByGroupIdArrayable() throws Exception {
		_persistence.countByGroupId(new long[] {RandomTestUtil.nextLong(), 0L});
	}

	@Test
	public void testCountByG_C() throws Exception {
		_persistence.countByG_C(
			RandomTestUtil.nextLong(), RandomTestUtil.nextLong());

		_persistence.countByG_C(0L, 0L);
	}

	@Test
	public void testCountByG_CArrayable() throws Exception {
		_persistence.countByG_C(
			new long[] {RandomTestUtil.nextLong(), 0L},
			RandomTestUtil.nextLong());
	}

	@Test
	public void testCountByG_S() throws Exception {
		_persistence.countByG_S(
			RandomTestUtil.nextLong(), RandomTestUtil.nextInt());

		_persistence.countByG_S(0L, 0);
	}

	@Test
	public void testCountByG_SArrayable() throws Exception {
		_persistence.countByG_S(
			new long[] {RandomTestUtil.nextLong(), 0L},
			RandomTestUtil.nextInt());
	}

	@Test
	public void testCountByC_ERC() throws Exception {
		_persistence.countByC_ERC(RandomTestUtil.nextLong(), "");

		_persistence.countByC_ERC(0L, "null");

		_persistence.countByC_ERC(0L, (String)null);
	}

	@Test
	public void testFindByPrimaryKeyExisting() throws Exception {
		CommerceShipment newCommerceShipment = addCommerceShipment();

		CommerceShipment existingCommerceShipment =
			_persistence.findByPrimaryKey(newCommerceShipment.getPrimaryKey());

		Assert.assertEquals(existingCommerceShipment, newCommerceShipment);
	}

	@Test(expected = NoSuchShipmentException.class)
	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		_persistence.findByPrimaryKey(pk);
	}

	@Test
	public void testFindAll() throws Exception {
		_persistence.findAll(
			QueryUtil.ALL_POS, QueryUtil.ALL_POS, getOrderByComparator());
	}

	protected OrderByComparator<CommerceShipment> getOrderByComparator() {
		return OrderByComparatorFactoryUtil.create(
			"CommerceShipment", "mvccVersion", true, "externalReferenceCode",
			true, "commerceShipmentId", true, "groupId", true, "companyId",
			true, "userId", true, "userName", true, "createDate", true,
			"modifiedDate", true, "commerceAccountId", true,
			"commerceAddressId", true, "commerceShippingMethodId", true,
			"carrier", true, "trackingNumber", true, "shippingDate", true,
			"expectedDate", true, "status", true);
	}

	@Test
	public void testFetchByPrimaryKeyExisting() throws Exception {
		CommerceShipment newCommerceShipment = addCommerceShipment();

		CommerceShipment existingCommerceShipment =
			_persistence.fetchByPrimaryKey(newCommerceShipment.getPrimaryKey());

		Assert.assertEquals(existingCommerceShipment, newCommerceShipment);
	}

	@Test
	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		CommerceShipment missingCommerceShipment =
			_persistence.fetchByPrimaryKey(pk);

		Assert.assertNull(missingCommerceShipment);
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereAllPrimaryKeysExist()
		throws Exception {

		CommerceShipment newCommerceShipment1 = addCommerceShipment();
		CommerceShipment newCommerceShipment2 = addCommerceShipment();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newCommerceShipment1.getPrimaryKey());
		primaryKeys.add(newCommerceShipment2.getPrimaryKey());

		Map<Serializable, CommerceShipment> commerceShipments =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(2, commerceShipments.size());
		Assert.assertEquals(
			newCommerceShipment1,
			commerceShipments.get(newCommerceShipment1.getPrimaryKey()));
		Assert.assertEquals(
			newCommerceShipment2,
			commerceShipments.get(newCommerceShipment2.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereNoPrimaryKeysExist()
		throws Exception {

		long pk1 = RandomTestUtil.nextLong();

		long pk2 = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(pk1);
		primaryKeys.add(pk2);

		Map<Serializable, CommerceShipment> commerceShipments =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(commerceShipments.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereSomePrimaryKeysExist()
		throws Exception {

		CommerceShipment newCommerceShipment = addCommerceShipment();

		long pk = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newCommerceShipment.getPrimaryKey());
		primaryKeys.add(pk);

		Map<Serializable, CommerceShipment> commerceShipments =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, commerceShipments.size());
		Assert.assertEquals(
			newCommerceShipment,
			commerceShipments.get(newCommerceShipment.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithNoPrimaryKeys() throws Exception {
		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		Map<Serializable, CommerceShipment> commerceShipments =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(commerceShipments.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithOnePrimaryKey() throws Exception {
		CommerceShipment newCommerceShipment = addCommerceShipment();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newCommerceShipment.getPrimaryKey());

		Map<Serializable, CommerceShipment> commerceShipments =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, commerceShipments.size());
		Assert.assertEquals(
			newCommerceShipment,
			commerceShipments.get(newCommerceShipment.getPrimaryKey()));
	}

	@Test
	public void testActionableDynamicQuery() throws Exception {
		final IntegerWrapper count = new IntegerWrapper();

		ActionableDynamicQuery actionableDynamicQuery =
			CommerceShipmentLocalServiceUtil.getActionableDynamicQuery();

		actionableDynamicQuery.setPerformActionMethod(
			new ActionableDynamicQuery.PerformActionMethod<CommerceShipment>() {

				@Override
				public void performAction(CommerceShipment commerceShipment) {
					Assert.assertNotNull(commerceShipment);

					count.increment();
				}

			});

		actionableDynamicQuery.performActions();

		Assert.assertEquals(count.getValue(), _persistence.countAll());
	}

	@Test
	public void testDynamicQueryByPrimaryKeyExisting() throws Exception {
		CommerceShipment newCommerceShipment = addCommerceShipment();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			CommerceShipment.class, _dynamicQueryClassLoader);

		dynamicQuery.add(
			RestrictionsFactoryUtil.eq(
				"commerceShipmentId",
				newCommerceShipment.getCommerceShipmentId()));

		List<CommerceShipment> result = _persistence.findWithDynamicQuery(
			dynamicQuery);

		Assert.assertEquals(1, result.size());

		CommerceShipment existingCommerceShipment = result.get(0);

		Assert.assertEquals(existingCommerceShipment, newCommerceShipment);
	}

	@Test
	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			CommerceShipment.class, _dynamicQueryClassLoader);

		dynamicQuery.add(
			RestrictionsFactoryUtil.eq(
				"commerceShipmentId", RandomTestUtil.nextLong()));

		List<CommerceShipment> result = _persistence.findWithDynamicQuery(
			dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testDynamicQueryByProjectionExisting() throws Exception {
		CommerceShipment newCommerceShipment = addCommerceShipment();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			CommerceShipment.class, _dynamicQueryClassLoader);

		dynamicQuery.setProjection(
			ProjectionFactoryUtil.property("commerceShipmentId"));

		Object newCommerceShipmentId =
			newCommerceShipment.getCommerceShipmentId();

		dynamicQuery.add(
			RestrictionsFactoryUtil.in(
				"commerceShipmentId", new Object[] {newCommerceShipmentId}));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		Object existingCommerceShipmentId = result.get(0);

		Assert.assertEquals(existingCommerceShipmentId, newCommerceShipmentId);
	}

	@Test
	public void testDynamicQueryByProjectionMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			CommerceShipment.class, _dynamicQueryClassLoader);

		dynamicQuery.setProjection(
			ProjectionFactoryUtil.property("commerceShipmentId"));

		dynamicQuery.add(
			RestrictionsFactoryUtil.in(
				"commerceShipmentId",
				new Object[] {RandomTestUtil.nextLong()}));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testResetOriginalValues() throws Exception {
		CommerceShipment newCommerceShipment = addCommerceShipment();

		_persistence.clearCache();

		_assertOriginalValues(
			_persistence.findByPrimaryKey(newCommerceShipment.getPrimaryKey()));
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

		CommerceShipment newCommerceShipment = addCommerceShipment();

		if (clearSession) {
			Session session = _persistence.openSession();

			session.flush();

			session.clear();
		}

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			CommerceShipment.class, _dynamicQueryClassLoader);

		dynamicQuery.add(
			RestrictionsFactoryUtil.eq(
				"commerceShipmentId",
				newCommerceShipment.getCommerceShipmentId()));

		List<CommerceShipment> result = _persistence.findWithDynamicQuery(
			dynamicQuery);

		_assertOriginalValues(result.get(0));
	}

	private void _assertOriginalValues(CommerceShipment commerceShipment) {
		Assert.assertEquals(
			Long.valueOf(commerceShipment.getCompanyId()),
			ReflectionTestUtil.<Long>invoke(
				commerceShipment, "getColumnOriginalValue",
				new Class<?>[] {String.class}, "companyId"));
		Assert.assertEquals(
			commerceShipment.getExternalReferenceCode(),
			ReflectionTestUtil.invoke(
				commerceShipment, "getColumnOriginalValue",
				new Class<?>[] {String.class}, "externalReferenceCode"));
	}

	protected CommerceShipment addCommerceShipment() throws Exception {
		long pk = RandomTestUtil.nextLong();

		CommerceShipment commerceShipment = _persistence.create(pk);

		commerceShipment.setMvccVersion(RandomTestUtil.nextLong());

		commerceShipment.setExternalReferenceCode(
			RandomTestUtil.randomString());

		commerceShipment.setGroupId(RandomTestUtil.nextLong());

		commerceShipment.setCompanyId(RandomTestUtil.nextLong());

		commerceShipment.setUserId(RandomTestUtil.nextLong());

		commerceShipment.setUserName(RandomTestUtil.randomString());

		commerceShipment.setCreateDate(RandomTestUtil.nextDate());

		commerceShipment.setModifiedDate(RandomTestUtil.nextDate());

		commerceShipment.setCommerceAccountId(RandomTestUtil.nextLong());

		commerceShipment.setCommerceAddressId(RandomTestUtil.nextLong());

		commerceShipment.setCommerceShippingMethodId(RandomTestUtil.nextLong());

		commerceShipment.setShippingOptionName(RandomTestUtil.randomString());

		commerceShipment.setCarrier(RandomTestUtil.randomString());

		commerceShipment.setTrackingNumber(RandomTestUtil.randomString());

		commerceShipment.setShippingDate(RandomTestUtil.nextDate());

		commerceShipment.setExpectedDate(RandomTestUtil.nextDate());

		commerceShipment.setStatus(RandomTestUtil.nextInt());

		_commerceShipments.add(_persistence.update(commerceShipment));

		return commerceShipment;
	}

	private List<CommerceShipment> _commerceShipments =
		new ArrayList<CommerceShipment>();
	private CommerceShipmentPersistence _persistence;
	private ClassLoader _dynamicQueryClassLoader;

}