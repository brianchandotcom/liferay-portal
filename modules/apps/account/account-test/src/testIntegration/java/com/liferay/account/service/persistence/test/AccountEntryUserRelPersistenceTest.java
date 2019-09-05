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

import com.liferay.account.exception.NoSuchEntryUserRelException;
import com.liferay.account.model.AccountEntryUserRel;
import com.liferay.account.service.AccountEntryUserRelLocalServiceUtil;
import com.liferay.account.service.persistence.AccountEntryUserRelPK;
import com.liferay.account.service.persistence.AccountEntryUserRelPersistence;
import com.liferay.account.service.persistence.AccountEntryUserRelUtil;
import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQueryFactoryUtil;
import com.liferay.portal.kernel.dao.orm.ProjectionFactoryUtil;
import com.liferay.portal.kernel.dao.orm.RestrictionsFactoryUtil;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.transaction.Propagation;
import com.liferay.portal.kernel.util.IntegerWrapper;
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
public class AccountEntryUserRelPersistenceTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(), PersistenceTestRule.INSTANCE,
			new TransactionalTestRule(
				Propagation.REQUIRED, "com.liferay.account.service"));

	@Before
	public void setUp() {
		_persistence = AccountEntryUserRelUtil.getPersistence();

		Class<?> clazz = _persistence.getClass();

		_dynamicQueryClassLoader = clazz.getClassLoader();
	}

	@After
	public void tearDown() throws Exception {
		Iterator<AccountEntryUserRel> iterator =
			_accountEntryUserRels.iterator();

		while (iterator.hasNext()) {
			_persistence.remove(iterator.next());

			iterator.remove();
		}
	}

	@Test
	public void testCreate() throws Exception {
		AccountEntryUserRelPK pk = new AccountEntryUserRelPK(
			RandomTestUtil.nextLong(), RandomTestUtil.nextLong());

		AccountEntryUserRel accountEntryUserRel = _persistence.create(pk);

		Assert.assertNotNull(accountEntryUserRel);

		Assert.assertEquals(accountEntryUserRel.getPrimaryKey(), pk);
	}

	@Test
	public void testRemove() throws Exception {
		AccountEntryUserRel newAccountEntryUserRel = addAccountEntryUserRel();

		_persistence.remove(newAccountEntryUserRel);

		AccountEntryUserRel existingAccountEntryUserRel =
			_persistence.fetchByPrimaryKey(
				newAccountEntryUserRel.getPrimaryKey());

		Assert.assertNull(existingAccountEntryUserRel);
	}

	@Test
	public void testUpdateNew() throws Exception {
		addAccountEntryUserRel();
	}

	@Test
	public void testUpdateExisting() throws Exception {
		AccountEntryUserRelPK pk = new AccountEntryUserRelPK(
			RandomTestUtil.nextLong(), RandomTestUtil.nextLong());

		AccountEntryUserRel newAccountEntryUserRel = _persistence.create(pk);

		newAccountEntryUserRel.setCompanyId(RandomTestUtil.nextLong());

		_accountEntryUserRels.add(_persistence.update(newAccountEntryUserRel));

		AccountEntryUserRel existingAccountEntryUserRel =
			_persistence.findByPrimaryKey(
				newAccountEntryUserRel.getPrimaryKey());

		Assert.assertEquals(
			existingAccountEntryUserRel.getAccountEntryId(),
			newAccountEntryUserRel.getAccountEntryId());
		Assert.assertEquals(
			existingAccountEntryUserRel.getUserId(),
			newAccountEntryUserRel.getUserId());
		Assert.assertEquals(
			existingAccountEntryUserRel.getCompanyId(),
			newAccountEntryUserRel.getCompanyId());
	}

	@Test
	public void testFindByPrimaryKeyExisting() throws Exception {
		AccountEntryUserRel newAccountEntryUserRel = addAccountEntryUserRel();

		AccountEntryUserRel existingAccountEntryUserRel =
			_persistence.findByPrimaryKey(
				newAccountEntryUserRel.getPrimaryKey());

		Assert.assertEquals(
			existingAccountEntryUserRel, newAccountEntryUserRel);
	}

	@Test(expected = NoSuchEntryUserRelException.class)
	public void testFindByPrimaryKeyMissing() throws Exception {
		AccountEntryUserRelPK pk = new AccountEntryUserRelPK(
			RandomTestUtil.nextLong(), RandomTestUtil.nextLong());

		_persistence.findByPrimaryKey(pk);
	}

	@Test
	public void testFetchByPrimaryKeyExisting() throws Exception {
		AccountEntryUserRel newAccountEntryUserRel = addAccountEntryUserRel();

		AccountEntryUserRel existingAccountEntryUserRel =
			_persistence.fetchByPrimaryKey(
				newAccountEntryUserRel.getPrimaryKey());

		Assert.assertEquals(
			existingAccountEntryUserRel, newAccountEntryUserRel);
	}

	@Test
	public void testFetchByPrimaryKeyMissing() throws Exception {
		AccountEntryUserRelPK pk = new AccountEntryUserRelPK(
			RandomTestUtil.nextLong(), RandomTestUtil.nextLong());

		AccountEntryUserRel missingAccountEntryUserRel =
			_persistence.fetchByPrimaryKey(pk);

		Assert.assertNull(missingAccountEntryUserRel);
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereAllPrimaryKeysExist()
		throws Exception {

		AccountEntryUserRel newAccountEntryUserRel1 = addAccountEntryUserRel();
		AccountEntryUserRel newAccountEntryUserRel2 = addAccountEntryUserRel();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newAccountEntryUserRel1.getPrimaryKey());
		primaryKeys.add(newAccountEntryUserRel2.getPrimaryKey());

		Map<Serializable, AccountEntryUserRel> accountEntryUserRels =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(2, accountEntryUserRels.size());
		Assert.assertEquals(
			newAccountEntryUserRel1,
			accountEntryUserRels.get(newAccountEntryUserRel1.getPrimaryKey()));
		Assert.assertEquals(
			newAccountEntryUserRel2,
			accountEntryUserRels.get(newAccountEntryUserRel2.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereNoPrimaryKeysExist()
		throws Exception {

		AccountEntryUserRelPK pk1 = new AccountEntryUserRelPK(
			RandomTestUtil.nextLong(), RandomTestUtil.nextLong());

		AccountEntryUserRelPK pk2 = new AccountEntryUserRelPK(
			RandomTestUtil.nextLong(), RandomTestUtil.nextLong());

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(pk1);
		primaryKeys.add(pk2);

		Map<Serializable, AccountEntryUserRel> accountEntryUserRels =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(accountEntryUserRels.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereSomePrimaryKeysExist()
		throws Exception {

		AccountEntryUserRel newAccountEntryUserRel = addAccountEntryUserRel();

		AccountEntryUserRelPK pk = new AccountEntryUserRelPK(
			RandomTestUtil.nextLong(), RandomTestUtil.nextLong());

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newAccountEntryUserRel.getPrimaryKey());
		primaryKeys.add(pk);

		Map<Serializable, AccountEntryUserRel> accountEntryUserRels =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, accountEntryUserRels.size());
		Assert.assertEquals(
			newAccountEntryUserRel,
			accountEntryUserRels.get(newAccountEntryUserRel.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithNoPrimaryKeys() throws Exception {
		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		Map<Serializable, AccountEntryUserRel> accountEntryUserRels =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(accountEntryUserRels.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithOnePrimaryKey() throws Exception {
		AccountEntryUserRel newAccountEntryUserRel = addAccountEntryUserRel();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newAccountEntryUserRel.getPrimaryKey());

		Map<Serializable, AccountEntryUserRel> accountEntryUserRels =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, accountEntryUserRels.size());
		Assert.assertEquals(
			newAccountEntryUserRel,
			accountEntryUserRels.get(newAccountEntryUserRel.getPrimaryKey()));
	}

	@Test
	public void testActionableDynamicQuery() throws Exception {
		final IntegerWrapper count = new IntegerWrapper();

		ActionableDynamicQuery actionableDynamicQuery =
			AccountEntryUserRelLocalServiceUtil.getActionableDynamicQuery();

		actionableDynamicQuery.setPerformActionMethod(
			new ActionableDynamicQuery.PerformActionMethod
				<AccountEntryUserRel>() {

				@Override
				public void performAction(
					AccountEntryUserRel accountEntryUserRel) {

					Assert.assertNotNull(accountEntryUserRel);

					count.increment();
				}

			});

		actionableDynamicQuery.performActions();

		Assert.assertEquals(count.getValue(), _persistence.countAll());
	}

	@Test
	public void testDynamicQueryByPrimaryKeyExisting() throws Exception {
		AccountEntryUserRel newAccountEntryUserRel = addAccountEntryUserRel();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			AccountEntryUserRel.class, _dynamicQueryClassLoader);

		dynamicQuery.add(
			RestrictionsFactoryUtil.eq(
				"id.accountEntryId",
				newAccountEntryUserRel.getAccountEntryId()));
		dynamicQuery.add(
			RestrictionsFactoryUtil.eq(
				"id.userId", newAccountEntryUserRel.getUserId()));

		List<AccountEntryUserRel> result = _persistence.findWithDynamicQuery(
			dynamicQuery);

		Assert.assertEquals(1, result.size());

		AccountEntryUserRel existingAccountEntryUserRel = result.get(0);

		Assert.assertEquals(
			existingAccountEntryUserRel, newAccountEntryUserRel);
	}

	@Test
	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			AccountEntryUserRel.class, _dynamicQueryClassLoader);

		dynamicQuery.add(
			RestrictionsFactoryUtil.eq(
				"id.accountEntryId", RandomTestUtil.nextLong()));
		dynamicQuery.add(
			RestrictionsFactoryUtil.eq("id.userId", RandomTestUtil.nextLong()));

		List<AccountEntryUserRel> result = _persistence.findWithDynamicQuery(
			dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testDynamicQueryByProjectionExisting() throws Exception {
		AccountEntryUserRel newAccountEntryUserRel = addAccountEntryUserRel();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			AccountEntryUserRel.class, _dynamicQueryClassLoader);

		dynamicQuery.setProjection(
			ProjectionFactoryUtil.property("id.accountEntryId"));

		Object newAccountEntryId = newAccountEntryUserRel.getAccountEntryId();

		dynamicQuery.add(
			RestrictionsFactoryUtil.in(
				"id.accountEntryId", new Object[] {newAccountEntryId}));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		Object existingAccountEntryId = result.get(0);

		Assert.assertEquals(existingAccountEntryId, newAccountEntryId);
	}

	@Test
	public void testDynamicQueryByProjectionMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			AccountEntryUserRel.class, _dynamicQueryClassLoader);

		dynamicQuery.setProjection(
			ProjectionFactoryUtil.property("id.accountEntryId"));

		dynamicQuery.add(
			RestrictionsFactoryUtil.in(
				"id.accountEntryId", new Object[] {RandomTestUtil.nextLong()}));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	protected AccountEntryUserRel addAccountEntryUserRel() throws Exception {
		AccountEntryUserRelPK pk = new AccountEntryUserRelPK(
			RandomTestUtil.nextLong(), RandomTestUtil.nextLong());

		AccountEntryUserRel accountEntryUserRel = _persistence.create(pk);

		accountEntryUserRel.setCompanyId(RandomTestUtil.nextLong());

		_accountEntryUserRels.add(_persistence.update(accountEntryUserRel));

		return accountEntryUserRel;
	}

	private List<AccountEntryUserRel> _accountEntryUserRels =
		new ArrayList<AccountEntryUserRel>();
	private AccountEntryUserRelPersistence _persistence;
	private ClassLoader _dynamicQueryClassLoader;

}