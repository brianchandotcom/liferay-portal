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

package com.liferay.commerce.account.service.persistence.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.commerce.account.exception.NoSuchAccountException;
import com.liferay.commerce.account.model.CommerceAccount;
import com.liferay.commerce.account.service.CommerceAccountLocalServiceUtil;
import com.liferay.commerce.account.service.persistence.CommerceAccountPersistence;
import com.liferay.commerce.account.service.persistence.CommerceAccountUtil;
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
public class CommerceAccountPersistenceTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(), PersistenceTestRule.INSTANCE,
			new TransactionalTestRule(
				Propagation.REQUIRED, "com.liferay.commerce.account.service"));

	@Before
	public void setUp() {
		_persistence = CommerceAccountUtil.getPersistence();

		Class<?> clazz = _persistence.getClass();

		_dynamicQueryClassLoader = clazz.getClassLoader();
	}

	@After
	public void tearDown() throws Exception {
		Iterator<CommerceAccount> iterator = _commerceAccounts.iterator();

		while (iterator.hasNext()) {
			_persistence.remove(iterator.next());

			iterator.remove();
		}
	}

	@Test
	public void testCreate() throws Exception {
		long pk = RandomTestUtil.nextLong();

		CommerceAccount commerceAccount = _persistence.create(pk);

		Assert.assertNotNull(commerceAccount);

		Assert.assertEquals(commerceAccount.getPrimaryKey(), pk);
	}

	@Test
	public void testRemove() throws Exception {
		CommerceAccount newCommerceAccount = addCommerceAccount();

		_persistence.remove(newCommerceAccount);

		CommerceAccount existingCommerceAccount =
			_persistence.fetchByPrimaryKey(newCommerceAccount.getPrimaryKey());

		Assert.assertNull(existingCommerceAccount);
	}

	@Test
	public void testUpdateNew() throws Exception {
		addCommerceAccount();
	}

	@Test
	public void testUpdateExisting() throws Exception {
		long pk = RandomTestUtil.nextLong();

		CommerceAccount newCommerceAccount = _persistence.create(pk);

		newCommerceAccount.setExternalReferenceCode(
			RandomTestUtil.randomString());

		newCommerceAccount.setCompanyId(RandomTestUtil.nextLong());

		newCommerceAccount.setUserId(RandomTestUtil.nextLong());

		newCommerceAccount.setUserName(RandomTestUtil.randomString());

		newCommerceAccount.setCreateDate(RandomTestUtil.nextDate());

		newCommerceAccount.setModifiedDate(RandomTestUtil.nextDate());

		newCommerceAccount.setParentCommerceAccountId(
			RandomTestUtil.nextLong());

		newCommerceAccount.setName(RandomTestUtil.randomString());

		newCommerceAccount.setLogoId(RandomTestUtil.nextLong());

		newCommerceAccount.setEmail(RandomTestUtil.randomString());

		newCommerceAccount.setTaxId(RandomTestUtil.randomString());

		newCommerceAccount.setType(RandomTestUtil.nextInt());

		newCommerceAccount.setActive(RandomTestUtil.randomBoolean());

		newCommerceAccount.setDisplayDate(RandomTestUtil.nextDate());

		newCommerceAccount.setDefaultBillingAddressId(
			RandomTestUtil.nextLong());

		newCommerceAccount.setDefaultShippingAddressId(
			RandomTestUtil.nextLong());

		newCommerceAccount.setExpirationDate(RandomTestUtil.nextDate());

		newCommerceAccount.setLastPublishDate(RandomTestUtil.nextDate());

		newCommerceAccount.setStatus(RandomTestUtil.nextInt());

		newCommerceAccount.setStatusByUserId(RandomTestUtil.nextLong());

		newCommerceAccount.setStatusByUserName(RandomTestUtil.randomString());

		newCommerceAccount.setStatusDate(RandomTestUtil.nextDate());

		_commerceAccounts.add(_persistence.update(newCommerceAccount));

		CommerceAccount existingCommerceAccount = _persistence.findByPrimaryKey(
			newCommerceAccount.getPrimaryKey());

		Assert.assertEquals(
			existingCommerceAccount.getExternalReferenceCode(),
			newCommerceAccount.getExternalReferenceCode());
		Assert.assertEquals(
			existingCommerceAccount.getCommerceAccountId(),
			newCommerceAccount.getCommerceAccountId());
		Assert.assertEquals(
			existingCommerceAccount.getCompanyId(),
			newCommerceAccount.getCompanyId());
		Assert.assertEquals(
			existingCommerceAccount.getUserId(),
			newCommerceAccount.getUserId());
		Assert.assertEquals(
			existingCommerceAccount.getUserName(),
			newCommerceAccount.getUserName());
		Assert.assertEquals(
			Time.getShortTimestamp(existingCommerceAccount.getCreateDate()),
			Time.getShortTimestamp(newCommerceAccount.getCreateDate()));
		Assert.assertEquals(
			Time.getShortTimestamp(existingCommerceAccount.getModifiedDate()),
			Time.getShortTimestamp(newCommerceAccount.getModifiedDate()));
		Assert.assertEquals(
			existingCommerceAccount.getParentCommerceAccountId(),
			newCommerceAccount.getParentCommerceAccountId());
		Assert.assertEquals(
			existingCommerceAccount.getName(), newCommerceAccount.getName());
		Assert.assertEquals(
			existingCommerceAccount.getLogoId(),
			newCommerceAccount.getLogoId());
		Assert.assertEquals(
			existingCommerceAccount.getEmail(), newCommerceAccount.getEmail());
		Assert.assertEquals(
			existingCommerceAccount.getTaxId(), newCommerceAccount.getTaxId());
		Assert.assertEquals(
			existingCommerceAccount.getType(), newCommerceAccount.getType());
		Assert.assertEquals(
			existingCommerceAccount.isActive(), newCommerceAccount.isActive());
		Assert.assertEquals(
			Time.getShortTimestamp(existingCommerceAccount.getDisplayDate()),
			Time.getShortTimestamp(newCommerceAccount.getDisplayDate()));
		Assert.assertEquals(
			existingCommerceAccount.getDefaultBillingAddressId(),
			newCommerceAccount.getDefaultBillingAddressId());
		Assert.assertEquals(
			existingCommerceAccount.getDefaultShippingAddressId(),
			newCommerceAccount.getDefaultShippingAddressId());
		Assert.assertEquals(
			Time.getShortTimestamp(existingCommerceAccount.getExpirationDate()),
			Time.getShortTimestamp(newCommerceAccount.getExpirationDate()));
		Assert.assertEquals(
			Time.getShortTimestamp(
				existingCommerceAccount.getLastPublishDate()),
			Time.getShortTimestamp(newCommerceAccount.getLastPublishDate()));
		Assert.assertEquals(
			existingCommerceAccount.getStatus(),
			newCommerceAccount.getStatus());
		Assert.assertEquals(
			existingCommerceAccount.getStatusByUserId(),
			newCommerceAccount.getStatusByUserId());
		Assert.assertEquals(
			existingCommerceAccount.getStatusByUserName(),
			newCommerceAccount.getStatusByUserName());
		Assert.assertEquals(
			Time.getShortTimestamp(existingCommerceAccount.getStatusDate()),
			Time.getShortTimestamp(newCommerceAccount.getStatusDate()));
	}

	@Test
	public void testCountByCompanyId() throws Exception {
		_persistence.countByCompanyId(RandomTestUtil.nextLong());

		_persistence.countByCompanyId(0L);
	}

	@Test
	public void testCountByU_T() throws Exception {
		_persistence.countByU_T(
			RandomTestUtil.nextLong(), RandomTestUtil.nextInt());

		_persistence.countByU_T(0L, 0);
	}

	@Test
	public void testCountByC_ERC() throws Exception {
		_persistence.countByC_ERC(RandomTestUtil.nextLong(), "");

		_persistence.countByC_ERC(0L, "null");

		_persistence.countByC_ERC(0L, (String)null);
	}

	@Test
	public void testFindByPrimaryKeyExisting() throws Exception {
		CommerceAccount newCommerceAccount = addCommerceAccount();

		CommerceAccount existingCommerceAccount = _persistence.findByPrimaryKey(
			newCommerceAccount.getPrimaryKey());

		Assert.assertEquals(existingCommerceAccount, newCommerceAccount);
	}

	@Test(expected = NoSuchAccountException.class)
	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		_persistence.findByPrimaryKey(pk);
	}

	@Test
	public void testFindAll() throws Exception {
		_persistence.findAll(
			QueryUtil.ALL_POS, QueryUtil.ALL_POS, getOrderByComparator());
	}

	protected OrderByComparator<CommerceAccount> getOrderByComparator() {
		return OrderByComparatorFactoryUtil.create(
			"CommerceAccount", "externalReferenceCode", true,
			"commerceAccountId", true, "companyId", true, "userId", true,
			"userName", true, "createDate", true, "modifiedDate", true,
			"parentCommerceAccountId", true, "name", true, "logoId", true,
			"email", true, "taxId", true, "type", true, "active", true,
			"displayDate", true, "defaultBillingAddressId", true,
			"defaultShippingAddressId", true, "expirationDate", true,
			"lastPublishDate", true, "status", true, "statusByUserId", true,
			"statusByUserName", true, "statusDate", true);
	}

	@Test
	public void testFetchByPrimaryKeyExisting() throws Exception {
		CommerceAccount newCommerceAccount = addCommerceAccount();

		CommerceAccount existingCommerceAccount =
			_persistence.fetchByPrimaryKey(newCommerceAccount.getPrimaryKey());

		Assert.assertEquals(existingCommerceAccount, newCommerceAccount);
	}

	@Test
	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		CommerceAccount missingCommerceAccount = _persistence.fetchByPrimaryKey(
			pk);

		Assert.assertNull(missingCommerceAccount);
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereAllPrimaryKeysExist()
		throws Exception {

		CommerceAccount newCommerceAccount1 = addCommerceAccount();
		CommerceAccount newCommerceAccount2 = addCommerceAccount();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newCommerceAccount1.getPrimaryKey());
		primaryKeys.add(newCommerceAccount2.getPrimaryKey());

		Map<Serializable, CommerceAccount> commerceAccounts =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(2, commerceAccounts.size());
		Assert.assertEquals(
			newCommerceAccount1,
			commerceAccounts.get(newCommerceAccount1.getPrimaryKey()));
		Assert.assertEquals(
			newCommerceAccount2,
			commerceAccounts.get(newCommerceAccount2.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereNoPrimaryKeysExist()
		throws Exception {

		long pk1 = RandomTestUtil.nextLong();

		long pk2 = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(pk1);
		primaryKeys.add(pk2);

		Map<Serializable, CommerceAccount> commerceAccounts =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(commerceAccounts.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereSomePrimaryKeysExist()
		throws Exception {

		CommerceAccount newCommerceAccount = addCommerceAccount();

		long pk = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newCommerceAccount.getPrimaryKey());
		primaryKeys.add(pk);

		Map<Serializable, CommerceAccount> commerceAccounts =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, commerceAccounts.size());
		Assert.assertEquals(
			newCommerceAccount,
			commerceAccounts.get(newCommerceAccount.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithNoPrimaryKeys() throws Exception {
		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		Map<Serializable, CommerceAccount> commerceAccounts =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(commerceAccounts.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithOnePrimaryKey() throws Exception {
		CommerceAccount newCommerceAccount = addCommerceAccount();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newCommerceAccount.getPrimaryKey());

		Map<Serializable, CommerceAccount> commerceAccounts =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, commerceAccounts.size());
		Assert.assertEquals(
			newCommerceAccount,
			commerceAccounts.get(newCommerceAccount.getPrimaryKey()));
	}

	@Test
	public void testActionableDynamicQuery() throws Exception {
		final IntegerWrapper count = new IntegerWrapper();

		ActionableDynamicQuery actionableDynamicQuery =
			CommerceAccountLocalServiceUtil.getActionableDynamicQuery();

		actionableDynamicQuery.setPerformActionMethod(
			new ActionableDynamicQuery.PerformActionMethod<CommerceAccount>() {

				@Override
				public void performAction(CommerceAccount commerceAccount) {
					Assert.assertNotNull(commerceAccount);

					count.increment();
				}

			});

		actionableDynamicQuery.performActions();

		Assert.assertEquals(count.getValue(), _persistence.countAll());
	}

	@Test
	public void testDynamicQueryByPrimaryKeyExisting() throws Exception {
		CommerceAccount newCommerceAccount = addCommerceAccount();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			CommerceAccount.class, _dynamicQueryClassLoader);

		dynamicQuery.add(
			RestrictionsFactoryUtil.eq(
				"commerceAccountId",
				newCommerceAccount.getCommerceAccountId()));

		List<CommerceAccount> result = _persistence.findWithDynamicQuery(
			dynamicQuery);

		Assert.assertEquals(1, result.size());

		CommerceAccount existingCommerceAccount = result.get(0);

		Assert.assertEquals(existingCommerceAccount, newCommerceAccount);
	}

	@Test
	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			CommerceAccount.class, _dynamicQueryClassLoader);

		dynamicQuery.add(
			RestrictionsFactoryUtil.eq(
				"commerceAccountId", RandomTestUtil.nextLong()));

		List<CommerceAccount> result = _persistence.findWithDynamicQuery(
			dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testDynamicQueryByProjectionExisting() throws Exception {
		CommerceAccount newCommerceAccount = addCommerceAccount();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			CommerceAccount.class, _dynamicQueryClassLoader);

		dynamicQuery.setProjection(
			ProjectionFactoryUtil.property("commerceAccountId"));

		Object newCommerceAccountId = newCommerceAccount.getCommerceAccountId();

		dynamicQuery.add(
			RestrictionsFactoryUtil.in(
				"commerceAccountId", new Object[] {newCommerceAccountId}));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		Object existingCommerceAccountId = result.get(0);

		Assert.assertEquals(existingCommerceAccountId, newCommerceAccountId);
	}

	@Test
	public void testDynamicQueryByProjectionMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			CommerceAccount.class, _dynamicQueryClassLoader);

		dynamicQuery.setProjection(
			ProjectionFactoryUtil.property("commerceAccountId"));

		dynamicQuery.add(
			RestrictionsFactoryUtil.in(
				"commerceAccountId", new Object[] {RandomTestUtil.nextLong()}));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testResetOriginalValues() throws Exception {
		CommerceAccount newCommerceAccount = addCommerceAccount();

		_persistence.clearCache();

		_assertOriginalValues(
			_persistence.findByPrimaryKey(newCommerceAccount.getPrimaryKey()));
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

		CommerceAccount newCommerceAccount = addCommerceAccount();

		if (clearSession) {
			Session session = _persistence.openSession();

			session.flush();

			session.clear();
		}

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			CommerceAccount.class, _dynamicQueryClassLoader);

		dynamicQuery.add(
			RestrictionsFactoryUtil.eq(
				"commerceAccountId",
				newCommerceAccount.getCommerceAccountId()));

		List<CommerceAccount> result = _persistence.findWithDynamicQuery(
			dynamicQuery);

		_assertOriginalValues(result.get(0));
	}

	private void _assertOriginalValues(CommerceAccount commerceAccount) {
		Assert.assertEquals(
			Long.valueOf(commerceAccount.getCompanyId()),
			ReflectionTestUtil.<Long>invoke(
				commerceAccount, "getColumnOriginalValue",
				new Class<?>[] {String.class}, "companyId"));
		Assert.assertEquals(
			commerceAccount.getExternalReferenceCode(),
			ReflectionTestUtil.invoke(
				commerceAccount, "getColumnOriginalValue",
				new Class<?>[] {String.class}, "externalReferenceCode"));
	}

	protected CommerceAccount addCommerceAccount() throws Exception {
		long pk = RandomTestUtil.nextLong();

		CommerceAccount commerceAccount = _persistence.create(pk);

		commerceAccount.setExternalReferenceCode(RandomTestUtil.randomString());

		commerceAccount.setCompanyId(RandomTestUtil.nextLong());

		commerceAccount.setUserId(RandomTestUtil.nextLong());

		commerceAccount.setUserName(RandomTestUtil.randomString());

		commerceAccount.setCreateDate(RandomTestUtil.nextDate());

		commerceAccount.setModifiedDate(RandomTestUtil.nextDate());

		commerceAccount.setParentCommerceAccountId(RandomTestUtil.nextLong());

		commerceAccount.setName(RandomTestUtil.randomString());

		commerceAccount.setLogoId(RandomTestUtil.nextLong());

		commerceAccount.setEmail(RandomTestUtil.randomString());

		commerceAccount.setTaxId(RandomTestUtil.randomString());

		commerceAccount.setType(RandomTestUtil.nextInt());

		commerceAccount.setActive(RandomTestUtil.randomBoolean());

		commerceAccount.setDisplayDate(RandomTestUtil.nextDate());

		commerceAccount.setDefaultBillingAddressId(RandomTestUtil.nextLong());

		commerceAccount.setDefaultShippingAddressId(RandomTestUtil.nextLong());

		commerceAccount.setExpirationDate(RandomTestUtil.nextDate());

		commerceAccount.setLastPublishDate(RandomTestUtil.nextDate());

		commerceAccount.setStatus(RandomTestUtil.nextInt());

		commerceAccount.setStatusByUserId(RandomTestUtil.nextLong());

		commerceAccount.setStatusByUserName(RandomTestUtil.randomString());

		commerceAccount.setStatusDate(RandomTestUtil.nextDate());

		_commerceAccounts.add(_persistence.update(commerceAccount));

		return commerceAccount;
	}

	private List<CommerceAccount> _commerceAccounts =
		new ArrayList<CommerceAccount>();
	private CommerceAccountPersistence _persistence;
	private ClassLoader _dynamicQueryClassLoader;

}