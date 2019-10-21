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
import com.liferay.account.service.persistence.AccountEntryUserRelPersistence;
import com.liferay.account.service.persistence.AccountEntryUserRelUtil;
import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
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
		long pk = RandomTestUtil.nextLong();

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
		long pk = RandomTestUtil.nextLong();

		AccountEntryUserRel newAccountEntryUserRel = _persistence.create(pk);

		newAccountEntryUserRel.setMvccVersion(RandomTestUtil.nextLong());

		newAccountEntryUserRel.setCompanyId(RandomTestUtil.nextLong());

		newAccountEntryUserRel.setAccountEntryId(RandomTestUtil.nextLong());

		newAccountEntryUserRel.setAccountUserId(RandomTestUtil.nextLong());

		_accountEntryUserRels.add(_persistence.update(newAccountEntryUserRel));

		AccountEntryUserRel existingAccountEntryUserRel =
			_persistence.findByPrimaryKey(
				newAccountEntryUserRel.getPrimaryKey());

		Assert.assertEquals(
			existingAccountEntryUserRel.getMvccVersion(),
			newAccountEntryUserRel.getMvccVersion());
		Assert.assertEquals(
			existingAccountEntryUserRel.getAccountEntryUserRelId(),
			newAccountEntryUserRel.getAccountEntryUserRelId());
		Assert.assertEquals(
			existingAccountEntryUserRel.getCompanyId(),
			newAccountEntryUserRel.getCompanyId());
		Assert.assertEquals(
			existingAccountEntryUserRel.getAccountEntryId(),
			newAccountEntryUserRel.getAccountEntryId());
		Assert.assertEquals(
			existingAccountEntryUserRel.getAccountUserId(),
			newAccountEntryUserRel.getAccountUserId());
	}

	@Test
	public void testCountByAEI() throws Exception {
		_persistence.countByAEI(RandomTestUtil.nextLong());

		_persistence.countByAEI(0L);
	}

	@Test
	public void testCountByAUI() throws Exception {
		_persistence.countByAUI(RandomTestUtil.nextLong());

		_persistence.countByAUI(0L);
	}

	@Test
	public void testCountByAEI_AUI() throws Exception {
		_persistence.countByAEI_AUI(
			RandomTestUtil.nextLong(), RandomTestUtil.nextLong());

		_persistence.countByAEI_AUI(0L, 0L);
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
		long pk = RandomTestUtil.nextLong();

		_persistence.findByPrimaryKey(pk);
	}

	@Test
	public void testFindAll() throws Exception {
		_persistence.findAll(
			QueryUtil.ALL_POS, QueryUtil.ALL_POS, getOrderByComparator());
	}

	protected OrderByComparator<AccountEntryUserRel> getOrderByComparator() {
		return OrderByComparatorFactoryUtil.create(
			"AccountEntryUserRel", "mvccVersion", true, "accountEntryUserRelId",
			true, "companyId", true, "accountEntryId", true, "accountUserId",
			true);
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
		long pk = RandomTestUtil.nextLong();

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

		long pk1 = RandomTestUtil.nextLong();

		long pk2 = RandomTestUtil.nextLong();

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

		long pk = RandomTestUtil.nextLong();

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
				"accountEntryUserRelId",
				newAccountEntryUserRel.getAccountEntryUserRelId()));

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
				"accountEntryUserRelId", RandomTestUtil.nextLong()));

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
			ProjectionFactoryUtil.property("accountEntryUserRelId"));

		Object newAccountEntryUserRelId =
			newAccountEntryUserRel.getAccountEntryUserRelId();

		dynamicQuery.add(
			RestrictionsFactoryUtil.in(
				"accountEntryUserRelId",
				new Object[] {newAccountEntryUserRelId}));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		Object existingAccountEntryUserRelId = result.get(0);

		Assert.assertEquals(
			existingAccountEntryUserRelId, newAccountEntryUserRelId);
	}

	@Test
	public void testDynamicQueryByProjectionMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			AccountEntryUserRel.class, _dynamicQueryClassLoader);

		dynamicQuery.setProjection(
			ProjectionFactoryUtil.property("accountEntryUserRelId"));

		dynamicQuery.add(
			RestrictionsFactoryUtil.in(
				"accountEntryUserRelId",
				new Object[] {RandomTestUtil.nextLong()}));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testResetOriginalValues() throws Exception {
		AccountEntryUserRel newAccountEntryUserRel = addAccountEntryUserRel();

		_persistence.clearCache();

		AccountEntryUserRel existingAccountEntryUserRel =
			_persistence.findByPrimaryKey(
				newAccountEntryUserRel.getPrimaryKey());

		Assert.assertEquals(
			Long.valueOf(existingAccountEntryUserRel.getAccountEntryId()),
			ReflectionTestUtil.<Long>invoke(
				existingAccountEntryUserRel, "getOriginalAccountEntryId",
				new Class<?>[0]));
		Assert.assertEquals(
			Long.valueOf(existingAccountEntryUserRel.getAccountUserId()),
			ReflectionTestUtil.<Long>invoke(
				existingAccountEntryUserRel, "getOriginalAccountUserId",
				new Class<?>[0]));
	}

	protected AccountEntryUserRel addAccountEntryUserRel() throws Exception {
		long pk = RandomTestUtil.nextLong();

		AccountEntryUserRel accountEntryUserRel = _persistence.create(pk);

		accountEntryUserRel.setMvccVersion(RandomTestUtil.nextLong());

		accountEntryUserRel.setCompanyId(RandomTestUtil.nextLong());

		accountEntryUserRel.setAccountEntryId(RandomTestUtil.nextLong());

		accountEntryUserRel.setAccountUserId(RandomTestUtil.nextLong());

		_accountEntryUserRels.add(_persistence.update(accountEntryUserRel));

		return accountEntryUserRel;
	}

	private List<AccountEntryUserRel> _accountEntryUserRels =
		new ArrayList<AccountEntryUserRel>();
	private AccountEntryUserRelPersistence _persistence;
	private ClassLoader _dynamicQueryClassLoader;

}