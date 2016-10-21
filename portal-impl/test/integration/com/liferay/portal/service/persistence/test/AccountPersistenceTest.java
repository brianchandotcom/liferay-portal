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
import com.liferay.portal.kernel.exception.NoSuchAccountException;
import com.liferay.portal.kernel.model.Account;
import com.liferay.portal.kernel.service.AccountLocalServiceUtil;
import com.liferay.portal.kernel.service.persistence.AccountPersistence;
import com.liferay.portal.kernel.service.persistence.AccountUtil;
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

import java.io.Serializable;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @generated
 */
public class AccountPersistenceTest {
	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule = new AggregateTestRule(new LiferayIntegrationTestRule(),
			PersistenceTestRule.INSTANCE,
			new TransactionalTestRule(Propagation.REQUIRED));

	@Before
	public void setUp() {
		_persistence = AccountUtil.getPersistence();

		Class<?> clazz = _persistence.getClass();

		_dynamicQueryClassLoader = clazz.getClassLoader();
	}

	@After
	public void tearDown() throws Exception {
		Iterator<Account> iterator = _accounts.iterator();

		while (iterator.hasNext()) {
			_persistence.remove(iterator.next());

			iterator.remove();
		}
	}

	@Test
	public void testCreate() throws Exception {
		long pk = RandomTestUtil.nextLong();

		Account account = _persistence.create(pk);

		Assert.assertNotNull(account);

		Assert.assertEquals(account.getPrimaryKey(), pk);
	}

	@Test
	public void testRemove() throws Exception {
		Account newAccount = addAccount();

		_persistence.remove(newAccount);

		Account existingAccount = _persistence.fetchByPrimaryKey(newAccount.getPrimaryKey());

		Assert.assertNull(existingAccount);
	}

	@Test
	public void testUpdateNew() throws Exception {
		addAccount();
	}

	@Test
	public void testUpdateExisting() throws Exception {
		long pk = RandomTestUtil.nextLong();

		Account newAccount = _persistence.create(pk);

		newAccount.setMvccVersion(RandomTestUtil.nextLong());

		newAccount.setCompanyId(RandomTestUtil.nextLong());

		newAccount.setUserId(RandomTestUtil.nextLong());

		newAccount.setUserName(RandomTestUtil.randomString());

		newAccount.setCreateDate(RandomTestUtil.nextDate());

		newAccount.setModifiedDate(RandomTestUtil.nextDate());

		newAccount.setParentAccountId(RandomTestUtil.nextLong());

		newAccount.setName(RandomTestUtil.randomString());

		newAccount.setLegalName(RandomTestUtil.randomString());

		newAccount.setLegalId(RandomTestUtil.randomString());

		newAccount.setLegalType(RandomTestUtil.randomString());

		newAccount.setSicCode(RandomTestUtil.randomString());

		newAccount.setTickerSymbol(RandomTestUtil.randomString());

		newAccount.setIndustry(RandomTestUtil.randomString());

		newAccount.setType(RandomTestUtil.randomString());

		newAccount.setSize(RandomTestUtil.randomString());

		_accounts.add(_persistence.update(newAccount));

		Account existingAccount = _persistence.findByPrimaryKey(newAccount.getPrimaryKey());

		Assert.assertEquals(existingAccount.getMvccVersion(),
			newAccount.getMvccVersion());
		Assert.assertEquals(existingAccount.getAccountId(),
			newAccount.getAccountId());
		Assert.assertEquals(existingAccount.getCompanyId(),
			newAccount.getCompanyId());
		Assert.assertEquals(existingAccount.getUserId(), newAccount.getUserId());
		Assert.assertEquals(existingAccount.getUserName(),
			newAccount.getUserName());
		Assert.assertEquals(Time.getShortTimestamp(
				existingAccount.getCreateDate()),
			Time.getShortTimestamp(newAccount.getCreateDate()));
		Assert.assertEquals(Time.getShortTimestamp(
				existingAccount.getModifiedDate()),
			Time.getShortTimestamp(newAccount.getModifiedDate()));
		Assert.assertEquals(existingAccount.getParentAccountId(),
			newAccount.getParentAccountId());
		Assert.assertEquals(existingAccount.getName(), newAccount.getName());
		Assert.assertEquals(existingAccount.getLegalName(),
			newAccount.getLegalName());
		Assert.assertEquals(existingAccount.getLegalId(),
			newAccount.getLegalId());
		Assert.assertEquals(existingAccount.getLegalType(),
			newAccount.getLegalType());
		Assert.assertEquals(existingAccount.getSicCode(),
			newAccount.getSicCode());
		Assert.assertEquals(existingAccount.getTickerSymbol(),
			newAccount.getTickerSymbol());
		Assert.assertEquals(existingAccount.getIndustry(),
			newAccount.getIndustry());
		Assert.assertEquals(existingAccount.getType(), newAccount.getType());
		Assert.assertEquals(existingAccount.getSize(), newAccount.getSize());
	}

	@Test
	public void testFindByPrimaryKeyExisting() throws Exception {
		Account newAccount = addAccount();

		Account existingAccount = _persistence.findByPrimaryKey(newAccount.getPrimaryKey());

		Assert.assertEquals(existingAccount, newAccount);
	}

	@Test(expected = NoSuchAccountException.class)
	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		_persistence.findByPrimaryKey(pk);
	}

	@Test
	public void testFindAll() throws Exception {
		_persistence.findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			getOrderByComparator());
	}

	protected OrderByComparator<Account> getOrderByComparator() {
		return OrderByComparatorFactoryUtil.create("Account_", "mvccVersion",
			true, "accountId", true, "companyId", true, "userId", true,
			"userName", true, "createDate", true, "modifiedDate", true,
			"parentAccountId", true, "name", true, "legalName", true,
			"legalId", true, "legalType", true, "sicCode", true,
			"tickerSymbol", true, "industry", true, "type", true, "size", true);
	}

	@Test
	public void testFetchByPrimaryKeyExisting() throws Exception {
		Account newAccount = addAccount();

		Account existingAccount = _persistence.fetchByPrimaryKey(newAccount.getPrimaryKey());

		Assert.assertEquals(existingAccount, newAccount);
	}

	@Test
	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		Account missingAccount = _persistence.fetchByPrimaryKey(pk);

		Assert.assertNull(missingAccount);
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereAllPrimaryKeysExist()
		throws Exception {
		Account newAccount1 = addAccount();
		Account newAccount2 = addAccount();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newAccount1.getPrimaryKey());
		primaryKeys.add(newAccount2.getPrimaryKey());

		Map<Serializable, Account> accounts = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(2, accounts.size());
		Assert.assertEquals(newAccount1,
			accounts.get(newAccount1.getPrimaryKey()));
		Assert.assertEquals(newAccount2,
			accounts.get(newAccount2.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereNoPrimaryKeysExist()
		throws Exception {
		long pk1 = RandomTestUtil.nextLong();

		long pk2 = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(pk1);
		primaryKeys.add(pk2);

		Map<Serializable, Account> accounts = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(accounts.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereSomePrimaryKeysExist()
		throws Exception {
		Account newAccount = addAccount();

		long pk = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newAccount.getPrimaryKey());
		primaryKeys.add(pk);

		Map<Serializable, Account> accounts = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, accounts.size());
		Assert.assertEquals(newAccount, accounts.get(newAccount.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithNoPrimaryKeys()
		throws Exception {
		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		Map<Serializable, Account> accounts = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(accounts.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithOnePrimaryKey()
		throws Exception {
		Account newAccount = addAccount();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newAccount.getPrimaryKey());

		Map<Serializable, Account> accounts = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, accounts.size());
		Assert.assertEquals(newAccount, accounts.get(newAccount.getPrimaryKey()));
	}

	@Test
	public void testActionableDynamicQuery() throws Exception {
		final IntegerWrapper count = new IntegerWrapper();

		ActionableDynamicQuery actionableDynamicQuery = AccountLocalServiceUtil.getActionableDynamicQuery();

		actionableDynamicQuery.setPerformActionMethod(new ActionableDynamicQuery.PerformActionMethod<Account>() {
				@Override
				public void performAction(Account account) {
					Assert.assertNotNull(account);

					count.increment();
				}
			});

		actionableDynamicQuery.performActions();

		Assert.assertEquals(count.getValue(), _persistence.countAll());
	}

	@Test
	public void testDynamicQueryByPrimaryKeyExisting()
		throws Exception {
		Account newAccount = addAccount();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(Account.class,
				_dynamicQueryClassLoader);

		dynamicQuery.add(RestrictionsFactoryUtil.eq("accountId",
				newAccount.getAccountId()));

		List<Account> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		Account existingAccount = result.get(0);

		Assert.assertEquals(existingAccount, newAccount);
	}

	@Test
	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(Account.class,
				_dynamicQueryClassLoader);

		dynamicQuery.add(RestrictionsFactoryUtil.eq("accountId",
				RandomTestUtil.nextLong()));

		List<Account> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testDynamicQueryByProjectionExisting()
		throws Exception {
		Account newAccount = addAccount();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(Account.class,
				_dynamicQueryClassLoader);

		dynamicQuery.setProjection(ProjectionFactoryUtil.property("accountId"));

		Object newAccountId = newAccount.getAccountId();

		dynamicQuery.add(RestrictionsFactoryUtil.in("accountId",
				new Object[] { newAccountId }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		Object existingAccountId = result.get(0);

		Assert.assertEquals(existingAccountId, newAccountId);
	}

	@Test
	public void testDynamicQueryByProjectionMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(Account.class,
				_dynamicQueryClassLoader);

		dynamicQuery.setProjection(ProjectionFactoryUtil.property("accountId"));

		dynamicQuery.add(RestrictionsFactoryUtil.in("accountId",
				new Object[] { RandomTestUtil.nextLong() }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	protected Account addAccount() throws Exception {
		long pk = RandomTestUtil.nextLong();

		Account account = _persistence.create(pk);

		account.setMvccVersion(RandomTestUtil.nextLong());

		account.setCompanyId(RandomTestUtil.nextLong());

		account.setUserId(RandomTestUtil.nextLong());

		account.setUserName(RandomTestUtil.randomString());

		account.setCreateDate(RandomTestUtil.nextDate());

		account.setModifiedDate(RandomTestUtil.nextDate());

		account.setParentAccountId(RandomTestUtil.nextLong());

		account.setName(RandomTestUtil.randomString());

		account.setLegalName(RandomTestUtil.randomString());

		account.setLegalId(RandomTestUtil.randomString());

		account.setLegalType(RandomTestUtil.randomString());

		account.setSicCode(RandomTestUtil.randomString());

		account.setTickerSymbol(RandomTestUtil.randomString());

		account.setIndustry(RandomTestUtil.randomString());

		account.setType(RandomTestUtil.randomString());

		account.setSize(RandomTestUtil.randomString());

		_accounts.add(_persistence.update(account));

		return account;
	}

	private List<Account> _accounts = new ArrayList<Account>();
	private AccountPersistence _persistence;
	private ClassLoader _dynamicQueryClassLoader;
}