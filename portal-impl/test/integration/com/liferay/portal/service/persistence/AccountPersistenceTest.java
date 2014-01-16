/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.service.persistence;

import com.liferay.portal.NoSuchAccountException;
import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQueryFactoryUtil;
import com.liferay.portal.kernel.dao.orm.ProjectionFactoryUtil;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.RestrictionsFactoryUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.test.ExecutionTestListeners;
import com.liferay.portal.kernel.util.IntegerWrapper;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.OrderByComparatorFactoryUtil;
import com.liferay.portal.kernel.util.Time;
import com.liferay.portal.model.Account;
import com.liferay.portal.service.ServiceTestUtil;
import com.liferay.portal.service.persistence.BasePersistence;
import com.liferay.portal.service.persistence.PersistenceExecutionTestListener;
import com.liferay.portal.test.LiferayPersistenceIntegrationJUnitTestRunner;
import com.liferay.portal.test.persistence.TransactionalPersistenceAdvice;

import org.junit.After;
import org.junit.Assert;
import org.junit.Test;

import org.junit.runner.RunWith;

import java.io.Serializable;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author Brian Wing Shun Chan
 */
@ExecutionTestListeners(listeners =  {
	PersistenceExecutionTestListener.class})
@RunWith(LiferayPersistenceIntegrationJUnitTestRunner.class)
public class AccountPersistenceTest {
	@After
	public void tearDown() throws Exception {
		Map<Serializable, BasePersistence<?>> basePersistences = _transactionalPersistenceAdvice.getBasePersistences();

		Set<Serializable> primaryKeys = basePersistences.keySet();

		for (Serializable primaryKey : primaryKeys) {
			BasePersistence<?> basePersistence = basePersistences.get(primaryKey);

			try {
				basePersistence.remove(primaryKey);
			}
			catch (Exception e) {
				if (_log.isDebugEnabled()) {
					_log.debug("The model with primary key " + primaryKey +
						" was already deleted");
				}
			}
		}

		_transactionalPersistenceAdvice.reset();
	}

	@Test
	public void testCreate() throws Exception {
		long pk = ServiceTestUtil.nextLong();

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
		long pk = ServiceTestUtil.nextLong();

		Account newAccount = _persistence.create(pk);

		newAccount.setCompanyId(ServiceTestUtil.nextLong());

		newAccount.setUserId(ServiceTestUtil.nextLong());

		newAccount.setUserName(ServiceTestUtil.randomString());

		newAccount.setCreateDate(ServiceTestUtil.nextDate());

		newAccount.setModifiedDate(ServiceTestUtil.nextDate());

		newAccount.setParentAccountId(ServiceTestUtil.nextLong());

		newAccount.setName(ServiceTestUtil.randomString());

		newAccount.setLegalName(ServiceTestUtil.randomString());

		newAccount.setLegalId(ServiceTestUtil.randomString());

		newAccount.setLegalType(ServiceTestUtil.randomString());

		newAccount.setSicCode(ServiceTestUtil.randomString());

		newAccount.setTickerSymbol(ServiceTestUtil.randomString());

		newAccount.setIndustry(ServiceTestUtil.randomString());

		newAccount.setType(ServiceTestUtil.randomString());

		newAccount.setSize(ServiceTestUtil.randomString());

		newAccount.setMvccVersion(ServiceTestUtil.nextLong());

		_persistence.update(newAccount);

		Account existingAccount = _persistence.findByPrimaryKey(newAccount.getPrimaryKey());

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
		Assert.assertEquals(existingAccount.getMvccVersion(),
			newAccount.getMvccVersion());
	}

	@Test
	public void testFindByPrimaryKeyExisting() throws Exception {
		Account newAccount = addAccount();

		Account existingAccount = _persistence.findByPrimaryKey(newAccount.getPrimaryKey());

		Assert.assertEquals(existingAccount, newAccount);
	}

	@Test
	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = ServiceTestUtil.nextLong();

		try {
			_persistence.findByPrimaryKey(pk);

			Assert.fail("Missing entity did not throw NoSuchAccountException");
		}
		catch (NoSuchAccountException nsee) {
		}
	}

	@Test
	public void testFindAll() throws Exception {
		try {
			_persistence.findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS,
				getOrderByComparator());
		}
		catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	protected OrderByComparator getOrderByComparator() {
		return OrderByComparatorFactoryUtil.create("Account_", "accountId",
			true, "companyId", true, "userId", true, "userName", true,
			"createDate", true, "modifiedDate", true, "parentAccountId", true,
			"name", true, "legalName", true, "legalId", true, "legalType",
			true, "sicCode", true, "tickerSymbol", true, "industry", true,
			"type", true, "size", true, "mvccVersion", true);
	}

	@Test
	public void testFetchByPrimaryKeyExisting() throws Exception {
		Account newAccount = addAccount();

		Account existingAccount = _persistence.fetchByPrimaryKey(newAccount.getPrimaryKey());

		Assert.assertEquals(existingAccount, newAccount);
	}

	@Test
	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = ServiceTestUtil.nextLong();

		Account missingAccount = _persistence.fetchByPrimaryKey(pk);

		Assert.assertNull(missingAccount);
	}

	@Test
	public void testActionableDynamicQuery() throws Exception {
		final IntegerWrapper count = new IntegerWrapper();

		ActionableDynamicQuery actionableDynamicQuery = new AccountActionableDynamicQuery() {
				@Override
				protected void performAction(Object object) {
					Account account = (Account)object;

					Assert.assertNotNull(account);

					count.increment();
				}
			};

		actionableDynamicQuery.performActions();

		Assert.assertEquals(count.getValue(), _persistence.countAll());
	}

	@Test
	public void testDynamicQueryByPrimaryKeyExisting()
		throws Exception {
		Account newAccount = addAccount();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(Account.class,
				Account.class.getClassLoader());

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
				Account.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("accountId",
				ServiceTestUtil.nextLong()));

		List<Account> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testDynamicQueryByProjectionExisting()
		throws Exception {
		Account newAccount = addAccount();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(Account.class,
				Account.class.getClassLoader());

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
				Account.class.getClassLoader());

		dynamicQuery.setProjection(ProjectionFactoryUtil.property("accountId"));

		dynamicQuery.add(RestrictionsFactoryUtil.in("accountId",
				new Object[] { ServiceTestUtil.nextLong() }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	protected Account addAccount() throws Exception {
		long pk = ServiceTestUtil.nextLong();

		Account account = _persistence.create(pk);

		account.setCompanyId(ServiceTestUtil.nextLong());

		account.setUserId(ServiceTestUtil.nextLong());

		account.setUserName(ServiceTestUtil.randomString());

		account.setCreateDate(ServiceTestUtil.nextDate());

		account.setModifiedDate(ServiceTestUtil.nextDate());

		account.setParentAccountId(ServiceTestUtil.nextLong());

		account.setName(ServiceTestUtil.randomString());

		account.setLegalName(ServiceTestUtil.randomString());

		account.setLegalId(ServiceTestUtil.randomString());

		account.setLegalType(ServiceTestUtil.randomString());

		account.setSicCode(ServiceTestUtil.randomString());

		account.setTickerSymbol(ServiceTestUtil.randomString());

		account.setIndustry(ServiceTestUtil.randomString());

		account.setType(ServiceTestUtil.randomString());

		account.setSize(ServiceTestUtil.randomString());

		account.setMvccVersion(ServiceTestUtil.nextLong());

		_persistence.update(account);

		return account;
	}

	private static Log _log = LogFactoryUtil.getLog(AccountPersistenceTest.class);
	private AccountPersistence _persistence = (AccountPersistence)PortalBeanLocatorUtil.locate(AccountPersistence.class.getName());
	private TransactionalPersistenceAdvice _transactionalPersistenceAdvice = (TransactionalPersistenceAdvice)PortalBeanLocatorUtil.locate(TransactionalPersistenceAdvice.class.getName());
}