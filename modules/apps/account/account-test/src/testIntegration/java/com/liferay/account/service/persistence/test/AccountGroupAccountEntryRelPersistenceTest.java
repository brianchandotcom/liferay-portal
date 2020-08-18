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

package com.liferay.account.service.persistence.test;

import com.liferay.account.exception.NoSuchGroupAccountEntryRelException;
import com.liferay.account.model.AccountGroupAccountEntryRel;
import com.liferay.account.service.AccountGroupAccountEntryRelLocalServiceUtil;
import com.liferay.account.service.persistence.AccountGroupAccountEntryRelPersistence;
import com.liferay.account.service.persistence.AccountGroupAccountEntryRelUtil;
import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
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
public class AccountGroupAccountEntryRelPersistenceTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(), PersistenceTestRule.INSTANCE,
			new TransactionalTestRule(
				Propagation.REQUIRED, "com.liferay.account.service"));

	@Before
	public void setUp() {
		_persistence = AccountGroupAccountEntryRelUtil.getPersistence();

		Class<?> clazz = _persistence.getClass();

		_dynamicQueryClassLoader = clazz.getClassLoader();
	}

	@After
	public void tearDown() throws Exception {
		Iterator<AccountGroupAccountEntryRel> iterator =
			_accountGroupAccountEntryRels.iterator();

		while (iterator.hasNext()) {
			_persistence.remove(iterator.next());

			iterator.remove();
		}
	}

	@Test
	public void testCreate() throws Exception {
		long pk = RandomTestUtil.nextLong();

		AccountGroupAccountEntryRel accountGroupAccountEntryRel =
			_persistence.create(pk);

		Assert.assertNotNull(accountGroupAccountEntryRel);

		Assert.assertEquals(accountGroupAccountEntryRel.getPrimaryKey(), pk);
	}

	@Test
	public void testRemove() throws Exception {
		AccountGroupAccountEntryRel newAccountGroupAccountEntryRel =
			addAccountGroupAccountEntryRel();

		_persistence.remove(newAccountGroupAccountEntryRel);

		AccountGroupAccountEntryRel existingAccountGroupAccountEntryRel =
			_persistence.fetchByPrimaryKey(
				newAccountGroupAccountEntryRel.getPrimaryKey());

		Assert.assertNull(existingAccountGroupAccountEntryRel);
	}

	@Test
	public void testUpdateNew() throws Exception {
		addAccountGroupAccountEntryRel();
	}

	@Test
	public void testUpdateExisting() throws Exception {
		long pk = RandomTestUtil.nextLong();

		AccountGroupAccountEntryRel newAccountGroupAccountEntryRel =
			_persistence.create(pk);

		newAccountGroupAccountEntryRel.setMvccVersion(
			RandomTestUtil.nextLong());

		newAccountGroupAccountEntryRel.setCompanyId(RandomTestUtil.nextLong());

		newAccountGroupAccountEntryRel.setAccountGroupId(
			RandomTestUtil.nextLong());

		newAccountGroupAccountEntryRel.setAccountEntryId(
			RandomTestUtil.nextLong());

		_accountGroupAccountEntryRels.add(
			_persistence.update(newAccountGroupAccountEntryRel));

		AccountGroupAccountEntryRel existingAccountGroupAccountEntryRel =
			_persistence.findByPrimaryKey(
				newAccountGroupAccountEntryRel.getPrimaryKey());

		Assert.assertEquals(
			existingAccountGroupAccountEntryRel.getMvccVersion(),
			newAccountGroupAccountEntryRel.getMvccVersion());
		Assert.assertEquals(
			existingAccountGroupAccountEntryRel.
				getAccountGroupAccountEntryRelId(),
			newAccountGroupAccountEntryRel.getAccountGroupAccountEntryRelId());
		Assert.assertEquals(
			existingAccountGroupAccountEntryRel.getCompanyId(),
			newAccountGroupAccountEntryRel.getCompanyId());
		Assert.assertEquals(
			existingAccountGroupAccountEntryRel.getAccountGroupId(),
			newAccountGroupAccountEntryRel.getAccountGroupId());
		Assert.assertEquals(
			existingAccountGroupAccountEntryRel.getAccountEntryId(),
			newAccountGroupAccountEntryRel.getAccountEntryId());
	}

	@Test
	public void testCountByAccountGroupId() throws Exception {
		_persistence.countByAccountGroupId(RandomTestUtil.nextLong());

		_persistence.countByAccountGroupId(0L);
	}

	@Test
	public void testCountByAccountEntryId() throws Exception {
		_persistence.countByAccountEntryId(RandomTestUtil.nextLong());

		_persistence.countByAccountEntryId(0L);
	}

	@Test
	public void testCountByAGI_AEI() throws Exception {
		_persistence.countByAGI_AEI(
			RandomTestUtil.nextLong(), RandomTestUtil.nextLong());

		_persistence.countByAGI_AEI(0L, 0L);
	}

	@Test
	public void testFindByPrimaryKeyExisting() throws Exception {
		AccountGroupAccountEntryRel newAccountGroupAccountEntryRel =
			addAccountGroupAccountEntryRel();

		AccountGroupAccountEntryRel existingAccountGroupAccountEntryRel =
			_persistence.findByPrimaryKey(
				newAccountGroupAccountEntryRel.getPrimaryKey());

		Assert.assertEquals(
			existingAccountGroupAccountEntryRel,
			newAccountGroupAccountEntryRel);
	}

	@Test(expected = NoSuchGroupAccountEntryRelException.class)
	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		_persistence.findByPrimaryKey(pk);
	}

	@Test
	public void testFindAll() throws Exception {
		_persistence.findAll(
			QueryUtil.ALL_POS, QueryUtil.ALL_POS, getOrderByComparator());
	}

	protected OrderByComparator<AccountGroupAccountEntryRel>
		getOrderByComparator() {

		return OrderByComparatorFactoryUtil.create(
			"AccountGroupAccountEntryRel", "mvccVersion", true,
			"AccountGroupAccountEntryRelId", true, "companyId", true,
			"accountGroupId", true, "accountEntryId", true);
	}

	@Test
	public void testFetchByPrimaryKeyExisting() throws Exception {
		AccountGroupAccountEntryRel newAccountGroupAccountEntryRel =
			addAccountGroupAccountEntryRel();

		AccountGroupAccountEntryRel existingAccountGroupAccountEntryRel =
			_persistence.fetchByPrimaryKey(
				newAccountGroupAccountEntryRel.getPrimaryKey());

		Assert.assertEquals(
			existingAccountGroupAccountEntryRel,
			newAccountGroupAccountEntryRel);
	}

	@Test
	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		AccountGroupAccountEntryRel missingAccountGroupAccountEntryRel =
			_persistence.fetchByPrimaryKey(pk);

		Assert.assertNull(missingAccountGroupAccountEntryRel);
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereAllPrimaryKeysExist()
		throws Exception {

		AccountGroupAccountEntryRel newAccountGroupAccountEntryRel1 =
			addAccountGroupAccountEntryRel();
		AccountGroupAccountEntryRel newAccountGroupAccountEntryRel2 =
			addAccountGroupAccountEntryRel();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newAccountGroupAccountEntryRel1.getPrimaryKey());
		primaryKeys.add(newAccountGroupAccountEntryRel2.getPrimaryKey());

		Map<Serializable, AccountGroupAccountEntryRel>
			accountGroupAccountEntryRels = _persistence.fetchByPrimaryKeys(
				primaryKeys);

		Assert.assertEquals(2, accountGroupAccountEntryRels.size());
		Assert.assertEquals(
			newAccountGroupAccountEntryRel1,
			accountGroupAccountEntryRels.get(
				newAccountGroupAccountEntryRel1.getPrimaryKey()));
		Assert.assertEquals(
			newAccountGroupAccountEntryRel2,
			accountGroupAccountEntryRels.get(
				newAccountGroupAccountEntryRel2.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereNoPrimaryKeysExist()
		throws Exception {

		long pk1 = RandomTestUtil.nextLong();

		long pk2 = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(pk1);
		primaryKeys.add(pk2);

		Map<Serializable, AccountGroupAccountEntryRel>
			accountGroupAccountEntryRels = _persistence.fetchByPrimaryKeys(
				primaryKeys);

		Assert.assertTrue(accountGroupAccountEntryRels.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereSomePrimaryKeysExist()
		throws Exception {

		AccountGroupAccountEntryRel newAccountGroupAccountEntryRel =
			addAccountGroupAccountEntryRel();

		long pk = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newAccountGroupAccountEntryRel.getPrimaryKey());
		primaryKeys.add(pk);

		Map<Serializable, AccountGroupAccountEntryRel>
			accountGroupAccountEntryRels = _persistence.fetchByPrimaryKeys(
				primaryKeys);

		Assert.assertEquals(1, accountGroupAccountEntryRels.size());
		Assert.assertEquals(
			newAccountGroupAccountEntryRel,
			accountGroupAccountEntryRels.get(
				newAccountGroupAccountEntryRel.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithNoPrimaryKeys() throws Exception {
		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		Map<Serializable, AccountGroupAccountEntryRel>
			accountGroupAccountEntryRels = _persistence.fetchByPrimaryKeys(
				primaryKeys);

		Assert.assertTrue(accountGroupAccountEntryRels.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithOnePrimaryKey() throws Exception {
		AccountGroupAccountEntryRel newAccountGroupAccountEntryRel =
			addAccountGroupAccountEntryRel();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newAccountGroupAccountEntryRel.getPrimaryKey());

		Map<Serializable, AccountGroupAccountEntryRel>
			accountGroupAccountEntryRels = _persistence.fetchByPrimaryKeys(
				primaryKeys);

		Assert.assertEquals(1, accountGroupAccountEntryRels.size());
		Assert.assertEquals(
			newAccountGroupAccountEntryRel,
			accountGroupAccountEntryRels.get(
				newAccountGroupAccountEntryRel.getPrimaryKey()));
	}

	@Test
	public void testActionableDynamicQuery() throws Exception {
		final IntegerWrapper count = new IntegerWrapper();

		ActionableDynamicQuery actionableDynamicQuery =
			AccountGroupAccountEntryRelLocalServiceUtil.
				getActionableDynamicQuery();

		actionableDynamicQuery.setPerformActionMethod(
			new ActionableDynamicQuery.PerformActionMethod
				<AccountGroupAccountEntryRel>() {

				@Override
				public void performAction(
					AccountGroupAccountEntryRel accountGroupAccountEntryRel) {

					Assert.assertNotNull(accountGroupAccountEntryRel);

					count.increment();
				}

			});

		actionableDynamicQuery.performActions();

		Assert.assertEquals(count.getValue(), _persistence.countAll());
	}

	@Test
	public void testDynamicQueryByPrimaryKeyExisting() throws Exception {
		AccountGroupAccountEntryRel newAccountGroupAccountEntryRel =
			addAccountGroupAccountEntryRel();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			AccountGroupAccountEntryRel.class, _dynamicQueryClassLoader);

		dynamicQuery.add(
			RestrictionsFactoryUtil.eq(
				"AccountGroupAccountEntryRelId",
				newAccountGroupAccountEntryRel.
					getAccountGroupAccountEntryRelId()));

		List<AccountGroupAccountEntryRel> result =
			_persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		AccountGroupAccountEntryRel existingAccountGroupAccountEntryRel =
			result.get(0);

		Assert.assertEquals(
			existingAccountGroupAccountEntryRel,
			newAccountGroupAccountEntryRel);
	}

	@Test
	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			AccountGroupAccountEntryRel.class, _dynamicQueryClassLoader);

		dynamicQuery.add(
			RestrictionsFactoryUtil.eq(
				"AccountGroupAccountEntryRelId", RandomTestUtil.nextLong()));

		List<AccountGroupAccountEntryRel> result =
			_persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testDynamicQueryByProjectionExisting() throws Exception {
		AccountGroupAccountEntryRel newAccountGroupAccountEntryRel =
			addAccountGroupAccountEntryRel();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			AccountGroupAccountEntryRel.class, _dynamicQueryClassLoader);

		dynamicQuery.setProjection(
			ProjectionFactoryUtil.property("AccountGroupAccountEntryRelId"));

		Object newAccountGroupAccountEntryRelId =
			newAccountGroupAccountEntryRel.getAccountGroupAccountEntryRelId();

		dynamicQuery.add(
			RestrictionsFactoryUtil.in(
				"AccountGroupAccountEntryRelId",
				new Object[] {newAccountGroupAccountEntryRelId}));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		Object existingAccountGroupAccountEntryRelId = result.get(0);

		Assert.assertEquals(
			existingAccountGroupAccountEntryRelId,
			newAccountGroupAccountEntryRelId);
	}

	@Test
	public void testDynamicQueryByProjectionMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			AccountGroupAccountEntryRel.class, _dynamicQueryClassLoader);

		dynamicQuery.setProjection(
			ProjectionFactoryUtil.property("AccountGroupAccountEntryRelId"));

		dynamicQuery.add(
			RestrictionsFactoryUtil.in(
				"AccountGroupAccountEntryRelId",
				new Object[] {RandomTestUtil.nextLong()}));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testResetOriginalValues() throws Exception {
		AccountGroupAccountEntryRel newAccountGroupAccountEntryRel =
			addAccountGroupAccountEntryRel();

		_persistence.clearCache();

		_assertOriginalValues(
			_persistence.findByPrimaryKey(
				newAccountGroupAccountEntryRel.getPrimaryKey()));
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

		AccountGroupAccountEntryRel newAccountGroupAccountEntryRel =
			addAccountGroupAccountEntryRel();

		if (clearSession) {
			Session session = _persistence.openSession();

			session.flush();

			session.clear();
		}

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			AccountGroupAccountEntryRel.class, _dynamicQueryClassLoader);

		dynamicQuery.add(
			RestrictionsFactoryUtil.eq(
				"AccountGroupAccountEntryRelId",
				newAccountGroupAccountEntryRel.
					getAccountGroupAccountEntryRelId()));

		List<AccountGroupAccountEntryRel> result =
			_persistence.findWithDynamicQuery(dynamicQuery);

		_assertOriginalValues(result.get(0));
	}

	private void _assertOriginalValues(
		AccountGroupAccountEntryRel accountGroupAccountEntryRel) {

		Assert.assertEquals(
			Long.valueOf(accountGroupAccountEntryRel.getAccountGroupId()),
			ReflectionTestUtil.<Long>invoke(
				accountGroupAccountEntryRel, "getColumnOriginalValue",
				new Class<?>[] {String.class}, "accountGroupId"));
		Assert.assertEquals(
			Long.valueOf(accountGroupAccountEntryRel.getAccountEntryId()),
			ReflectionTestUtil.<Long>invoke(
				accountGroupAccountEntryRel, "getColumnOriginalValue",
				new Class<?>[] {String.class}, "accountEntryId"));
	}

	protected AccountGroupAccountEntryRel addAccountGroupAccountEntryRel()
		throws Exception {

		long pk = RandomTestUtil.nextLong();

		AccountGroupAccountEntryRel accountGroupAccountEntryRel =
			_persistence.create(pk);

		accountGroupAccountEntryRel.setMvccVersion(RandomTestUtil.nextLong());

		accountGroupAccountEntryRel.setCompanyId(RandomTestUtil.nextLong());

		accountGroupAccountEntryRel.setAccountGroupId(
			RandomTestUtil.nextLong());

		accountGroupAccountEntryRel.setAccountEntryId(
			RandomTestUtil.nextLong());

		_accountGroupAccountEntryRels.add(
			_persistence.update(accountGroupAccountEntryRel));

		return accountGroupAccountEntryRel;
	}

	private List<AccountGroupAccountEntryRel> _accountGroupAccountEntryRels =
		new ArrayList<AccountGroupAccountEntryRel>();
	private AccountGroupAccountEntryRelPersistence _persistence;
	private ClassLoader _dynamicQueryClassLoader;

}