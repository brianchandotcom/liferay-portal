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

package com.liferay.commerce.order.rule.service.persistence.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.commerce.order.rule.exception.NoSuchOrderRuleEntryException;
import com.liferay.commerce.order.rule.model.CommerceOrderRuleEntry;
import com.liferay.commerce.order.rule.service.CommerceOrderRuleEntryLocalServiceUtil;
import com.liferay.commerce.order.rule.service.persistence.CommerceOrderRuleEntryPersistence;
import com.liferay.commerce.order.rule.service.persistence.CommerceOrderRuleEntryUtil;
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
public class CommerceOrderRuleEntryPersistenceTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(), PersistenceTestRule.INSTANCE,
			new TransactionalTestRule(
				Propagation.REQUIRED,
				"com.liferay.commerce.order.rule.service"));

	@Before
	public void setUp() {
		_persistence = CommerceOrderRuleEntryUtil.getPersistence();

		Class<?> clazz = _persistence.getClass();

		_dynamicQueryClassLoader = clazz.getClassLoader();
	}

	@After
	public void tearDown() throws Exception {
		Iterator<CommerceOrderRuleEntry> iterator =
			_commerceOrderRuleEntries.iterator();

		while (iterator.hasNext()) {
			_persistence.remove(iterator.next());

			iterator.remove();
		}
	}

	@Test
	public void testCreate() throws Exception {
		long pk = RandomTestUtil.nextLong();

		CommerceOrderRuleEntry commerceOrderRuleEntry = _persistence.create(pk);

		Assert.assertNotNull(commerceOrderRuleEntry);

		Assert.assertEquals(commerceOrderRuleEntry.getPrimaryKey(), pk);
	}

	@Test
	public void testRemove() throws Exception {
		CommerceOrderRuleEntry newCommerceOrderRuleEntry =
			addCommerceOrderRuleEntry();

		_persistence.remove(newCommerceOrderRuleEntry);

		CommerceOrderRuleEntry existingCommerceOrderRuleEntry =
			_persistence.fetchByPrimaryKey(
				newCommerceOrderRuleEntry.getPrimaryKey());

		Assert.assertNull(existingCommerceOrderRuleEntry);
	}

	@Test
	public void testUpdateNew() throws Exception {
		addCommerceOrderRuleEntry();
	}

	@Test
	public void testUpdateExisting() throws Exception {
		long pk = RandomTestUtil.nextLong();

		CommerceOrderRuleEntry newCommerceOrderRuleEntry = _persistence.create(
			pk);

		newCommerceOrderRuleEntry.setExternalReferenceCode(
			RandomTestUtil.randomString());

		newCommerceOrderRuleEntry.setCompanyId(RandomTestUtil.nextLong());

		newCommerceOrderRuleEntry.setUserId(RandomTestUtil.nextLong());

		newCommerceOrderRuleEntry.setUserName(RandomTestUtil.randomString());

		newCommerceOrderRuleEntry.setCreateDate(RandomTestUtil.nextDate());

		newCommerceOrderRuleEntry.setModifiedDate(RandomTestUtil.nextDate());

		newCommerceOrderRuleEntry.setActive(RandomTestUtil.randomBoolean());

		newCommerceOrderRuleEntry.setDescription(RandomTestUtil.randomString());

		newCommerceOrderRuleEntry.setDisplayDate(RandomTestUtil.nextDate());

		newCommerceOrderRuleEntry.setExpirationDate(RandomTestUtil.nextDate());

		newCommerceOrderRuleEntry.setName(RandomTestUtil.randomString());

		newCommerceOrderRuleEntry.setPriority(RandomTestUtil.nextInt());

		newCommerceOrderRuleEntry.setType(RandomTestUtil.randomString());

		newCommerceOrderRuleEntry.setTypeSettings(
			RandomTestUtil.randomString());

		newCommerceOrderRuleEntry.setLastPublishDate(RandomTestUtil.nextDate());

		newCommerceOrderRuleEntry.setStatus(RandomTestUtil.nextInt());

		newCommerceOrderRuleEntry.setStatusByUserId(RandomTestUtil.nextLong());

		newCommerceOrderRuleEntry.setStatusByUserName(
			RandomTestUtil.randomString());

		newCommerceOrderRuleEntry.setStatusDate(RandomTestUtil.nextDate());

		_commerceOrderRuleEntries.add(
			_persistence.update(newCommerceOrderRuleEntry));

		CommerceOrderRuleEntry existingCommerceOrderRuleEntry =
			_persistence.findByPrimaryKey(
				newCommerceOrderRuleEntry.getPrimaryKey());

		Assert.assertEquals(
			existingCommerceOrderRuleEntry.getExternalReferenceCode(),
			newCommerceOrderRuleEntry.getExternalReferenceCode());
		Assert.assertEquals(
			existingCommerceOrderRuleEntry.getCommerceOrderRuleEntryId(),
			newCommerceOrderRuleEntry.getCommerceOrderRuleEntryId());
		Assert.assertEquals(
			existingCommerceOrderRuleEntry.getCompanyId(),
			newCommerceOrderRuleEntry.getCompanyId());
		Assert.assertEquals(
			existingCommerceOrderRuleEntry.getUserId(),
			newCommerceOrderRuleEntry.getUserId());
		Assert.assertEquals(
			existingCommerceOrderRuleEntry.getUserName(),
			newCommerceOrderRuleEntry.getUserName());
		Assert.assertEquals(
			Time.getShortTimestamp(
				existingCommerceOrderRuleEntry.getCreateDate()),
			Time.getShortTimestamp(newCommerceOrderRuleEntry.getCreateDate()));
		Assert.assertEquals(
			Time.getShortTimestamp(
				existingCommerceOrderRuleEntry.getModifiedDate()),
			Time.getShortTimestamp(
				newCommerceOrderRuleEntry.getModifiedDate()));
		Assert.assertEquals(
			existingCommerceOrderRuleEntry.isActive(),
			newCommerceOrderRuleEntry.isActive());
		Assert.assertEquals(
			existingCommerceOrderRuleEntry.getDescription(),
			newCommerceOrderRuleEntry.getDescription());
		Assert.assertEquals(
			Time.getShortTimestamp(
				existingCommerceOrderRuleEntry.getDisplayDate()),
			Time.getShortTimestamp(newCommerceOrderRuleEntry.getDisplayDate()));
		Assert.assertEquals(
			Time.getShortTimestamp(
				existingCommerceOrderRuleEntry.getExpirationDate()),
			Time.getShortTimestamp(
				newCommerceOrderRuleEntry.getExpirationDate()));
		Assert.assertEquals(
			existingCommerceOrderRuleEntry.getName(),
			newCommerceOrderRuleEntry.getName());
		Assert.assertEquals(
			existingCommerceOrderRuleEntry.getPriority(),
			newCommerceOrderRuleEntry.getPriority());
		Assert.assertEquals(
			existingCommerceOrderRuleEntry.getType(),
			newCommerceOrderRuleEntry.getType());
		Assert.assertEquals(
			existingCommerceOrderRuleEntry.getTypeSettings(),
			newCommerceOrderRuleEntry.getTypeSettings());
		Assert.assertEquals(
			Time.getShortTimestamp(
				existingCommerceOrderRuleEntry.getLastPublishDate()),
			Time.getShortTimestamp(
				newCommerceOrderRuleEntry.getLastPublishDate()));
		Assert.assertEquals(
			existingCommerceOrderRuleEntry.getStatus(),
			newCommerceOrderRuleEntry.getStatus());
		Assert.assertEquals(
			existingCommerceOrderRuleEntry.getStatusByUserId(),
			newCommerceOrderRuleEntry.getStatusByUserId());
		Assert.assertEquals(
			existingCommerceOrderRuleEntry.getStatusByUserName(),
			newCommerceOrderRuleEntry.getStatusByUserName());
		Assert.assertEquals(
			Time.getShortTimestamp(
				existingCommerceOrderRuleEntry.getStatusDate()),
			Time.getShortTimestamp(newCommerceOrderRuleEntry.getStatusDate()));
	}

	@Test
	public void testCountByC_A() throws Exception {
		_persistence.countByC_A(
			RandomTestUtil.nextLong(), RandomTestUtil.randomBoolean());

		_persistence.countByC_A(0L, RandomTestUtil.randomBoolean());
	}

	@Test
	public void testCountByC_LikeType() throws Exception {
		_persistence.countByC_LikeType(RandomTestUtil.nextLong(), "");

		_persistence.countByC_LikeType(0L, "null");

		_persistence.countByC_LikeType(0L, (String)null);
	}

	@Test
	public void testCountByLtD_S() throws Exception {
		_persistence.countByLtD_S(
			RandomTestUtil.nextDate(), RandomTestUtil.nextInt());

		_persistence.countByLtD_S(RandomTestUtil.nextDate(), 0);
	}

	@Test
	public void testCountByLtE_S() throws Exception {
		_persistence.countByLtE_S(
			RandomTestUtil.nextDate(), RandomTestUtil.nextInt());

		_persistence.countByLtE_S(RandomTestUtil.nextDate(), 0);
	}

	@Test
	public void testCountByC_A_LikeType() throws Exception {
		_persistence.countByC_A_LikeType(
			RandomTestUtil.nextLong(), RandomTestUtil.randomBoolean(), "");

		_persistence.countByC_A_LikeType(
			0L, RandomTestUtil.randomBoolean(), "null");

		_persistence.countByC_A_LikeType(
			0L, RandomTestUtil.randomBoolean(), (String)null);
	}

	@Test
	public void testCountByC_ERC() throws Exception {
		_persistence.countByC_ERC(RandomTestUtil.nextLong(), "");

		_persistence.countByC_ERC(0L, "null");

		_persistence.countByC_ERC(0L, (String)null);
	}

	@Test
	public void testFindByPrimaryKeyExisting() throws Exception {
		CommerceOrderRuleEntry newCommerceOrderRuleEntry =
			addCommerceOrderRuleEntry();

		CommerceOrderRuleEntry existingCommerceOrderRuleEntry =
			_persistence.findByPrimaryKey(
				newCommerceOrderRuleEntry.getPrimaryKey());

		Assert.assertEquals(
			existingCommerceOrderRuleEntry, newCommerceOrderRuleEntry);
	}

	@Test(expected = NoSuchOrderRuleEntryException.class)
	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		_persistence.findByPrimaryKey(pk);
	}

	@Test
	public void testFindAll() throws Exception {
		_persistence.findAll(
			QueryUtil.ALL_POS, QueryUtil.ALL_POS, getOrderByComparator());
	}

	protected OrderByComparator<CommerceOrderRuleEntry> getOrderByComparator() {
		return OrderByComparatorFactoryUtil.create(
			"CommerceOrderRuleEntry", "externalReferenceCode", true,
			"commerceOrderRuleEntryId", true, "companyId", true, "userId", true,
			"userName", true, "createDate", true, "modifiedDate", true,
			"active", true, "description", true, "displayDate", true,
			"expirationDate", true, "name", true, "priority", true, "type",
			true, "typeSettings", true, "lastPublishDate", true, "status", true,
			"statusByUserId", true, "statusByUserName", true, "statusDate",
			true);
	}

	@Test
	public void testFetchByPrimaryKeyExisting() throws Exception {
		CommerceOrderRuleEntry newCommerceOrderRuleEntry =
			addCommerceOrderRuleEntry();

		CommerceOrderRuleEntry existingCommerceOrderRuleEntry =
			_persistence.fetchByPrimaryKey(
				newCommerceOrderRuleEntry.getPrimaryKey());

		Assert.assertEquals(
			existingCommerceOrderRuleEntry, newCommerceOrderRuleEntry);
	}

	@Test
	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		CommerceOrderRuleEntry missingCommerceOrderRuleEntry =
			_persistence.fetchByPrimaryKey(pk);

		Assert.assertNull(missingCommerceOrderRuleEntry);
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereAllPrimaryKeysExist()
		throws Exception {

		CommerceOrderRuleEntry newCommerceOrderRuleEntry1 =
			addCommerceOrderRuleEntry();
		CommerceOrderRuleEntry newCommerceOrderRuleEntry2 =
			addCommerceOrderRuleEntry();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newCommerceOrderRuleEntry1.getPrimaryKey());
		primaryKeys.add(newCommerceOrderRuleEntry2.getPrimaryKey());

		Map<Serializable, CommerceOrderRuleEntry> commerceOrderRuleEntries =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(2, commerceOrderRuleEntries.size());
		Assert.assertEquals(
			newCommerceOrderRuleEntry1,
			commerceOrderRuleEntries.get(
				newCommerceOrderRuleEntry1.getPrimaryKey()));
		Assert.assertEquals(
			newCommerceOrderRuleEntry2,
			commerceOrderRuleEntries.get(
				newCommerceOrderRuleEntry2.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereNoPrimaryKeysExist()
		throws Exception {

		long pk1 = RandomTestUtil.nextLong();

		long pk2 = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(pk1);
		primaryKeys.add(pk2);

		Map<Serializable, CommerceOrderRuleEntry> commerceOrderRuleEntries =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(commerceOrderRuleEntries.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereSomePrimaryKeysExist()
		throws Exception {

		CommerceOrderRuleEntry newCommerceOrderRuleEntry =
			addCommerceOrderRuleEntry();

		long pk = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newCommerceOrderRuleEntry.getPrimaryKey());
		primaryKeys.add(pk);

		Map<Serializable, CommerceOrderRuleEntry> commerceOrderRuleEntries =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, commerceOrderRuleEntries.size());
		Assert.assertEquals(
			newCommerceOrderRuleEntry,
			commerceOrderRuleEntries.get(
				newCommerceOrderRuleEntry.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithNoPrimaryKeys() throws Exception {
		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		Map<Serializable, CommerceOrderRuleEntry> commerceOrderRuleEntries =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(commerceOrderRuleEntries.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithOnePrimaryKey() throws Exception {
		CommerceOrderRuleEntry newCommerceOrderRuleEntry =
			addCommerceOrderRuleEntry();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newCommerceOrderRuleEntry.getPrimaryKey());

		Map<Serializable, CommerceOrderRuleEntry> commerceOrderRuleEntries =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, commerceOrderRuleEntries.size());
		Assert.assertEquals(
			newCommerceOrderRuleEntry,
			commerceOrderRuleEntries.get(
				newCommerceOrderRuleEntry.getPrimaryKey()));
	}

	@Test
	public void testActionableDynamicQuery() throws Exception {
		final IntegerWrapper count = new IntegerWrapper();

		ActionableDynamicQuery actionableDynamicQuery =
			CommerceOrderRuleEntryLocalServiceUtil.getActionableDynamicQuery();

		actionableDynamicQuery.setPerformActionMethod(
			new ActionableDynamicQuery.PerformActionMethod
				<CommerceOrderRuleEntry>() {

				@Override
				public void performAction(
					CommerceOrderRuleEntry commerceOrderRuleEntry) {

					Assert.assertNotNull(commerceOrderRuleEntry);

					count.increment();
				}

			});

		actionableDynamicQuery.performActions();

		Assert.assertEquals(count.getValue(), _persistence.countAll());
	}

	@Test
	public void testDynamicQueryByPrimaryKeyExisting() throws Exception {
		CommerceOrderRuleEntry newCommerceOrderRuleEntry =
			addCommerceOrderRuleEntry();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			CommerceOrderRuleEntry.class, _dynamicQueryClassLoader);

		dynamicQuery.add(
			RestrictionsFactoryUtil.eq(
				"commerceOrderRuleEntryId",
				newCommerceOrderRuleEntry.getCommerceOrderRuleEntryId()));

		List<CommerceOrderRuleEntry> result = _persistence.findWithDynamicQuery(
			dynamicQuery);

		Assert.assertEquals(1, result.size());

		CommerceOrderRuleEntry existingCommerceOrderRuleEntry = result.get(0);

		Assert.assertEquals(
			existingCommerceOrderRuleEntry, newCommerceOrderRuleEntry);
	}

	@Test
	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			CommerceOrderRuleEntry.class, _dynamicQueryClassLoader);

		dynamicQuery.add(
			RestrictionsFactoryUtil.eq(
				"commerceOrderRuleEntryId", RandomTestUtil.nextLong()));

		List<CommerceOrderRuleEntry> result = _persistence.findWithDynamicQuery(
			dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testDynamicQueryByProjectionExisting() throws Exception {
		CommerceOrderRuleEntry newCommerceOrderRuleEntry =
			addCommerceOrderRuleEntry();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			CommerceOrderRuleEntry.class, _dynamicQueryClassLoader);

		dynamicQuery.setProjection(
			ProjectionFactoryUtil.property("commerceOrderRuleEntryId"));

		Object newCommerceOrderRuleEntryId =
			newCommerceOrderRuleEntry.getCommerceOrderRuleEntryId();

		dynamicQuery.add(
			RestrictionsFactoryUtil.in(
				"commerceOrderRuleEntryId",
				new Object[] {newCommerceOrderRuleEntryId}));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		Object existingCommerceOrderRuleEntryId = result.get(0);

		Assert.assertEquals(
			existingCommerceOrderRuleEntryId, newCommerceOrderRuleEntryId);
	}

	@Test
	public void testDynamicQueryByProjectionMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			CommerceOrderRuleEntry.class, _dynamicQueryClassLoader);

		dynamicQuery.setProjection(
			ProjectionFactoryUtil.property("commerceOrderRuleEntryId"));

		dynamicQuery.add(
			RestrictionsFactoryUtil.in(
				"commerceOrderRuleEntryId",
				new Object[] {RandomTestUtil.nextLong()}));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testResetOriginalValues() throws Exception {
		CommerceOrderRuleEntry newCommerceOrderRuleEntry =
			addCommerceOrderRuleEntry();

		_persistence.clearCache();

		_assertOriginalValues(
			_persistence.findByPrimaryKey(
				newCommerceOrderRuleEntry.getPrimaryKey()));
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

		CommerceOrderRuleEntry newCommerceOrderRuleEntry =
			addCommerceOrderRuleEntry();

		if (clearSession) {
			Session session = _persistence.openSession();

			session.flush();

			session.clear();
		}

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			CommerceOrderRuleEntry.class, _dynamicQueryClassLoader);

		dynamicQuery.add(
			RestrictionsFactoryUtil.eq(
				"commerceOrderRuleEntryId",
				newCommerceOrderRuleEntry.getCommerceOrderRuleEntryId()));

		List<CommerceOrderRuleEntry> result = _persistence.findWithDynamicQuery(
			dynamicQuery);

		_assertOriginalValues(result.get(0));
	}

	private void _assertOriginalValues(
		CommerceOrderRuleEntry commerceOrderRuleEntry) {

		Assert.assertEquals(
			Long.valueOf(commerceOrderRuleEntry.getCompanyId()),
			ReflectionTestUtil.<Long>invoke(
				commerceOrderRuleEntry, "getColumnOriginalValue",
				new Class<?>[] {String.class}, "companyId"));
		Assert.assertEquals(
			commerceOrderRuleEntry.getExternalReferenceCode(),
			ReflectionTestUtil.invoke(
				commerceOrderRuleEntry, "getColumnOriginalValue",
				new Class<?>[] {String.class}, "externalReferenceCode"));
	}

	protected CommerceOrderRuleEntry addCommerceOrderRuleEntry()
		throws Exception {

		long pk = RandomTestUtil.nextLong();

		CommerceOrderRuleEntry commerceOrderRuleEntry = _persistence.create(pk);

		commerceOrderRuleEntry.setExternalReferenceCode(
			RandomTestUtil.randomString());

		commerceOrderRuleEntry.setCompanyId(RandomTestUtil.nextLong());

		commerceOrderRuleEntry.setUserId(RandomTestUtil.nextLong());

		commerceOrderRuleEntry.setUserName(RandomTestUtil.randomString());

		commerceOrderRuleEntry.setCreateDate(RandomTestUtil.nextDate());

		commerceOrderRuleEntry.setModifiedDate(RandomTestUtil.nextDate());

		commerceOrderRuleEntry.setActive(RandomTestUtil.randomBoolean());

		commerceOrderRuleEntry.setDescription(RandomTestUtil.randomString());

		commerceOrderRuleEntry.setDisplayDate(RandomTestUtil.nextDate());

		commerceOrderRuleEntry.setExpirationDate(RandomTestUtil.nextDate());

		commerceOrderRuleEntry.setName(RandomTestUtil.randomString());

		commerceOrderRuleEntry.setPriority(RandomTestUtil.nextInt());

		commerceOrderRuleEntry.setType(RandomTestUtil.randomString());

		commerceOrderRuleEntry.setTypeSettings(RandomTestUtil.randomString());

		commerceOrderRuleEntry.setLastPublishDate(RandomTestUtil.nextDate());

		commerceOrderRuleEntry.setStatus(RandomTestUtil.nextInt());

		commerceOrderRuleEntry.setStatusByUserId(RandomTestUtil.nextLong());

		commerceOrderRuleEntry.setStatusByUserName(
			RandomTestUtil.randomString());

		commerceOrderRuleEntry.setStatusDate(RandomTestUtil.nextDate());

		_commerceOrderRuleEntries.add(
			_persistence.update(commerceOrderRuleEntry));

		return commerceOrderRuleEntry;
	}

	private List<CommerceOrderRuleEntry> _commerceOrderRuleEntries =
		new ArrayList<CommerceOrderRuleEntry>();
	private CommerceOrderRuleEntryPersistence _persistence;
	private ClassLoader _dynamicQueryClassLoader;

}