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

package com.liferay.mail.reader.service.persistence.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.mail.reader.exception.NoSuchAccountException;
import com.liferay.mail.reader.model.Account;
import com.liferay.mail.reader.service.AccountLocalServiceUtil;
import com.liferay.mail.reader.service.persistence.AccountPersistence;
import com.liferay.mail.reader.service.persistence.AccountUtil;
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

import java.io.Serializable;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
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
public class AccountPersistenceTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(), PersistenceTestRule.INSTANCE,
			new TransactionalTestRule(
				Propagation.REQUIRED, "com.liferay.mail.reader.service"));

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

		Account existingAccount = _persistence.fetchByPrimaryKey(
			newAccount.getPrimaryKey());

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

		newAccount.setCompanyId(RandomTestUtil.nextLong());

		newAccount.setUserId(RandomTestUtil.nextLong());

		newAccount.setUserName(RandomTestUtil.randomString());

		newAccount.setCreateDate(RandomTestUtil.nextDate());

		newAccount.setModifiedDate(RandomTestUtil.nextDate());

		newAccount.setAddress(RandomTestUtil.randomString());

		newAccount.setPersonalName(RandomTestUtil.randomString());

		newAccount.setProtocol(RandomTestUtil.randomString());

		newAccount.setIncomingHostName(RandomTestUtil.randomString());

		newAccount.setIncomingPort(RandomTestUtil.nextInt());

		newAccount.setIncomingSecure(RandomTestUtil.randomBoolean());

		newAccount.setOutgoingHostName(RandomTestUtil.randomString());

		newAccount.setOutgoingPort(RandomTestUtil.nextInt());

		newAccount.setOutgoingSecure(RandomTestUtil.randomBoolean());

		newAccount.setLogin(RandomTestUtil.randomString());

		newAccount.setPassword(RandomTestUtil.randomString());

		newAccount.setSavePassword(RandomTestUtil.randomBoolean());

		newAccount.setSignature(RandomTestUtil.randomString());

		newAccount.setUseSignature(RandomTestUtil.randomBoolean());

		newAccount.setFolderPrefix(RandomTestUtil.randomString());

		newAccount.setInboxFolderId(RandomTestUtil.nextLong());

		newAccount.setDraftFolderId(RandomTestUtil.nextLong());

		newAccount.setSentFolderId(RandomTestUtil.nextLong());

		newAccount.setTrashFolderId(RandomTestUtil.nextLong());

		newAccount.setDefaultSender(RandomTestUtil.randomBoolean());

		_accounts.add(_persistence.update(newAccount));

		Account existingAccount = _persistence.findByPrimaryKey(
			newAccount.getPrimaryKey());

		Assert.assertEquals(
			existingAccount.getAccountId(), newAccount.getAccountId());
		Assert.assertEquals(
			existingAccount.getCompanyId(), newAccount.getCompanyId());
		Assert.assertEquals(
			existingAccount.getUserId(), newAccount.getUserId());
		Assert.assertEquals(
			existingAccount.getUserName(), newAccount.getUserName());
		Assert.assertEquals(
			Time.getShortTimestamp(existingAccount.getCreateDate()),
			Time.getShortTimestamp(newAccount.getCreateDate()));
		Assert.assertEquals(
			Time.getShortTimestamp(existingAccount.getModifiedDate()),
			Time.getShortTimestamp(newAccount.getModifiedDate()));
		Assert.assertEquals(
			existingAccount.getAddress(), newAccount.getAddress());
		Assert.assertEquals(
			existingAccount.getPersonalName(), newAccount.getPersonalName());
		Assert.assertEquals(
			existingAccount.getProtocol(), newAccount.getProtocol());
		Assert.assertEquals(
			existingAccount.getIncomingHostName(),
			newAccount.getIncomingHostName());
		Assert.assertEquals(
			existingAccount.getIncomingPort(), newAccount.getIncomingPort());
		Assert.assertEquals(
			existingAccount.isIncomingSecure(), newAccount.isIncomingSecure());
		Assert.assertEquals(
			existingAccount.getOutgoingHostName(),
			newAccount.getOutgoingHostName());
		Assert.assertEquals(
			existingAccount.getOutgoingPort(), newAccount.getOutgoingPort());
		Assert.assertEquals(
			existingAccount.isOutgoingSecure(), newAccount.isOutgoingSecure());
		Assert.assertEquals(existingAccount.getLogin(), newAccount.getLogin());
		Assert.assertEquals(
			existingAccount.getPassword(), newAccount.getPassword());
		Assert.assertEquals(
			existingAccount.isSavePassword(), newAccount.isSavePassword());
		Assert.assertEquals(
			existingAccount.getSignature(), newAccount.getSignature());
		Assert.assertEquals(
			existingAccount.isUseSignature(), newAccount.isUseSignature());
		Assert.assertEquals(
			existingAccount.getFolderPrefix(), newAccount.getFolderPrefix());
		Assert.assertEquals(
			existingAccount.getInboxFolderId(), newAccount.getInboxFolderId());
		Assert.assertEquals(
			existingAccount.getDraftFolderId(), newAccount.getDraftFolderId());
		Assert.assertEquals(
			existingAccount.getSentFolderId(), newAccount.getSentFolderId());
		Assert.assertEquals(
			existingAccount.getTrashFolderId(), newAccount.getTrashFolderId());
		Assert.assertEquals(
			existingAccount.isDefaultSender(), newAccount.isDefaultSender());
	}

	@Test
	public void testCountByUserId() throws Exception {
		_persistence.countByUserId(RandomTestUtil.nextLong());

		_persistence.countByUserId(0L);
	}

	@Test
	public void testCountByU_A() throws Exception {
		_persistence.countByU_A(RandomTestUtil.nextLong(), "");

		_persistence.countByU_A(0L, "null");

		_persistence.countByU_A(0L, (String)null);
	}

	@Test
	public void testFindByPrimaryKeyExisting() throws Exception {
		Account newAccount = addAccount();

		Account existingAccount = _persistence.findByPrimaryKey(
			newAccount.getPrimaryKey());

		Assert.assertEquals(existingAccount, newAccount);
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

	protected OrderByComparator<Account> getOrderByComparator() {
		return OrderByComparatorFactoryUtil.create(
			"Mail_Account", "accountId", true, "companyId", true, "userId",
			true, "userName", true, "createDate", true, "modifiedDate", true,
			"address", true, "personalName", true, "protocol", true,
			"incomingHostName", true, "incomingPort", true, "incomingSecure",
			true, "outgoingHostName", true, "outgoingPort", true,
			"outgoingSecure", true, "login", true, "password", true,
			"savePassword", true, "signature", true, "useSignature", true,
			"folderPrefix", true, "inboxFolderId", true, "draftFolderId", true,
			"sentFolderId", true, "trashFolderId", true, "defaultSender", true);
	}

	@Test
	public void testFetchByPrimaryKeyExisting() throws Exception {
		Account newAccount = addAccount();

		Account existingAccount = _persistence.fetchByPrimaryKey(
			newAccount.getPrimaryKey());

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

		Map<Serializable, Account> accounts = _persistence.fetchByPrimaryKeys(
			primaryKeys);

		Assert.assertEquals(2, accounts.size());
		Assert.assertEquals(
			newAccount1, accounts.get(newAccount1.getPrimaryKey()));
		Assert.assertEquals(
			newAccount2, accounts.get(newAccount2.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereNoPrimaryKeysExist()
		throws Exception {

		long pk1 = RandomTestUtil.nextLong();

		long pk2 = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(pk1);
		primaryKeys.add(pk2);

		Map<Serializable, Account> accounts = _persistence.fetchByPrimaryKeys(
			primaryKeys);

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

		Map<Serializable, Account> accounts = _persistence.fetchByPrimaryKeys(
			primaryKeys);

		Assert.assertEquals(1, accounts.size());
		Assert.assertEquals(
			newAccount, accounts.get(newAccount.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithNoPrimaryKeys() throws Exception {
		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		Map<Serializable, Account> accounts = _persistence.fetchByPrimaryKeys(
			primaryKeys);

		Assert.assertTrue(accounts.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithOnePrimaryKey() throws Exception {
		Account newAccount = addAccount();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newAccount.getPrimaryKey());

		Map<Serializable, Account> accounts = _persistence.fetchByPrimaryKeys(
			primaryKeys);

		Assert.assertEquals(1, accounts.size());
		Assert.assertEquals(
			newAccount, accounts.get(newAccount.getPrimaryKey()));
	}

	@Test
	public void testActionableDynamicQuery() throws Exception {
		final IntegerWrapper count = new IntegerWrapper();

		ActionableDynamicQuery actionableDynamicQuery =
			AccountLocalServiceUtil.getActionableDynamicQuery();

		actionableDynamicQuery.setPerformActionMethod(
			new ActionableDynamicQuery.PerformActionMethod<Account>() {

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
	public void testDynamicQueryByPrimaryKeyExisting() throws Exception {
		Account newAccount = addAccount();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			Account.class, _dynamicQueryClassLoader);

		dynamicQuery.add(
			RestrictionsFactoryUtil.eq("accountId", newAccount.getAccountId()));

		List<Account> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		Account existingAccount = result.get(0);

		Assert.assertEquals(existingAccount, newAccount);
	}

	@Test
	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			Account.class, _dynamicQueryClassLoader);

		dynamicQuery.add(
			RestrictionsFactoryUtil.eq("accountId", RandomTestUtil.nextLong()));

		List<Account> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testDynamicQueryByProjectionExisting() throws Exception {
		Account newAccount = addAccount();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			Account.class, _dynamicQueryClassLoader);

		dynamicQuery.setProjection(ProjectionFactoryUtil.property("accountId"));

		Object newAccountId = newAccount.getAccountId();

		dynamicQuery.add(
			RestrictionsFactoryUtil.in(
				"accountId", new Object[] {newAccountId}));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		Object existingAccountId = result.get(0);

		Assert.assertEquals(existingAccountId, newAccountId);
	}

	@Test
	public void testDynamicQueryByProjectionMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			Account.class, _dynamicQueryClassLoader);

		dynamicQuery.setProjection(ProjectionFactoryUtil.property("accountId"));

		dynamicQuery.add(
			RestrictionsFactoryUtil.in(
				"accountId", new Object[] {RandomTestUtil.nextLong()}));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testResetOriginalValues() throws Exception {
		Account newAccount = addAccount();

		_persistence.clearCache();

		Account existingAccount = _persistence.findByPrimaryKey(
			newAccount.getPrimaryKey());

		Assert.assertEquals(
			Long.valueOf(existingAccount.getUserId()),
			ReflectionTestUtil.<Long>invoke(
				existingAccount, "getOriginalUserId", new Class<?>[0]));
		Assert.assertTrue(
			Objects.equals(
				existingAccount.getAddress(),
				ReflectionTestUtil.invoke(
					existingAccount, "getOriginalAddress", new Class<?>[0])));
	}

	protected Account addAccount() throws Exception {
		long pk = RandomTestUtil.nextLong();

		Account account = _persistence.create(pk);

		account.setCompanyId(RandomTestUtil.nextLong());

		account.setUserId(RandomTestUtil.nextLong());

		account.setUserName(RandomTestUtil.randomString());

		account.setCreateDate(RandomTestUtil.nextDate());

		account.setModifiedDate(RandomTestUtil.nextDate());

		account.setAddress(RandomTestUtil.randomString());

		account.setPersonalName(RandomTestUtil.randomString());

		account.setProtocol(RandomTestUtil.randomString());

		account.setIncomingHostName(RandomTestUtil.randomString());

		account.setIncomingPort(RandomTestUtil.nextInt());

		account.setIncomingSecure(RandomTestUtil.randomBoolean());

		account.setOutgoingHostName(RandomTestUtil.randomString());

		account.setOutgoingPort(RandomTestUtil.nextInt());

		account.setOutgoingSecure(RandomTestUtil.randomBoolean());

		account.setLogin(RandomTestUtil.randomString());

		account.setPassword(RandomTestUtil.randomString());

		account.setSavePassword(RandomTestUtil.randomBoolean());

		account.setSignature(RandomTestUtil.randomString());

		account.setUseSignature(RandomTestUtil.randomBoolean());

		account.setFolderPrefix(RandomTestUtil.randomString());

		account.setInboxFolderId(RandomTestUtil.nextLong());

		account.setDraftFolderId(RandomTestUtil.nextLong());

		account.setSentFolderId(RandomTestUtil.nextLong());

		account.setTrashFolderId(RandomTestUtil.nextLong());

		account.setDefaultSender(RandomTestUtil.randomBoolean());

		_accounts.add(_persistence.update(account));

		return account;
	}

	private List<Account> _accounts = new ArrayList<Account>();
	private AccountPersistence _persistence;
	private ClassLoader _dynamicQueryClassLoader;

}