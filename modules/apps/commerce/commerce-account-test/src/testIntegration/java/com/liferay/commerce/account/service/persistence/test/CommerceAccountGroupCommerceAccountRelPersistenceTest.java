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
import com.liferay.commerce.account.exception.NoSuchAccountGroupCommerceAccountRelException;
import com.liferay.commerce.account.model.CommerceAccountGroupCommerceAccountRel;
import com.liferay.commerce.account.service.CommerceAccountGroupCommerceAccountRelLocalServiceUtil;
import com.liferay.commerce.account.service.persistence.CommerceAccountGroupCommerceAccountRelPersistence;
import com.liferay.commerce.account.service.persistence.CommerceAccountGroupCommerceAccountRelUtil;
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
public class CommerceAccountGroupCommerceAccountRelPersistenceTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(), PersistenceTestRule.INSTANCE,
			new TransactionalTestRule(
				Propagation.REQUIRED, "com.liferay.commerce.account.service"));

	@Before
	public void setUp() {
		_persistence =
			CommerceAccountGroupCommerceAccountRelUtil.getPersistence();

		Class<?> clazz = _persistence.getClass();

		_dynamicQueryClassLoader = clazz.getClassLoader();
	}

	@After
	public void tearDown() throws Exception {
		Iterator<CommerceAccountGroupCommerceAccountRel> iterator =
			_commerceAccountGroupCommerceAccountRels.iterator();

		while (iterator.hasNext()) {
			_persistence.remove(iterator.next());

			iterator.remove();
		}
	}

	@Test
	public void testCreate() throws Exception {
		long pk = RandomTestUtil.nextLong();

		CommerceAccountGroupCommerceAccountRel
			commerceAccountGroupCommerceAccountRel = _persistence.create(pk);

		Assert.assertNotNull(commerceAccountGroupCommerceAccountRel);

		Assert.assertEquals(
			commerceAccountGroupCommerceAccountRel.getPrimaryKey(), pk);
	}

	@Test
	public void testRemove() throws Exception {
		CommerceAccountGroupCommerceAccountRel
			newCommerceAccountGroupCommerceAccountRel =
				addCommerceAccountGroupCommerceAccountRel();

		_persistence.remove(newCommerceAccountGroupCommerceAccountRel);

		CommerceAccountGroupCommerceAccountRel
			existingCommerceAccountGroupCommerceAccountRel =
				_persistence.fetchByPrimaryKey(
					newCommerceAccountGroupCommerceAccountRel.getPrimaryKey());

		Assert.assertNull(existingCommerceAccountGroupCommerceAccountRel);
	}

	@Test
	public void testUpdateNew() throws Exception {
		addCommerceAccountGroupCommerceAccountRel();
	}

	@Test
	public void testUpdateExisting() throws Exception {
		long pk = RandomTestUtil.nextLong();

		CommerceAccountGroupCommerceAccountRel
			newCommerceAccountGroupCommerceAccountRel = _persistence.create(pk);

		newCommerceAccountGroupCommerceAccountRel.setExternalReferenceCode(
			RandomTestUtil.randomString());

		newCommerceAccountGroupCommerceAccountRel.setCompanyId(
			RandomTestUtil.nextLong());

		newCommerceAccountGroupCommerceAccountRel.setUserId(
			RandomTestUtil.nextLong());

		newCommerceAccountGroupCommerceAccountRel.setUserName(
			RandomTestUtil.randomString());

		newCommerceAccountGroupCommerceAccountRel.setCreateDate(
			RandomTestUtil.nextDate());

		newCommerceAccountGroupCommerceAccountRel.setModifiedDate(
			RandomTestUtil.nextDate());

		newCommerceAccountGroupCommerceAccountRel.setCommerceAccountGroupId(
			RandomTestUtil.nextLong());

		newCommerceAccountGroupCommerceAccountRel.setCommerceAccountId(
			RandomTestUtil.nextLong());

		_commerceAccountGroupCommerceAccountRels.add(
			_persistence.update(newCommerceAccountGroupCommerceAccountRel));

		CommerceAccountGroupCommerceAccountRel
			existingCommerceAccountGroupCommerceAccountRel =
				_persistence.findByPrimaryKey(
					newCommerceAccountGroupCommerceAccountRel.getPrimaryKey());

		Assert.assertEquals(
			existingCommerceAccountGroupCommerceAccountRel.
				getExternalReferenceCode(),
			newCommerceAccountGroupCommerceAccountRel.
				getExternalReferenceCode());
		Assert.assertEquals(
			existingCommerceAccountGroupCommerceAccountRel.
				getCommerceAccountGroupCommerceAccountRelId(),
			newCommerceAccountGroupCommerceAccountRel.
				getCommerceAccountGroupCommerceAccountRelId());
		Assert.assertEquals(
			existingCommerceAccountGroupCommerceAccountRel.getCompanyId(),
			newCommerceAccountGroupCommerceAccountRel.getCompanyId());
		Assert.assertEquals(
			existingCommerceAccountGroupCommerceAccountRel.getUserId(),
			newCommerceAccountGroupCommerceAccountRel.getUserId());
		Assert.assertEquals(
			existingCommerceAccountGroupCommerceAccountRel.getUserName(),
			newCommerceAccountGroupCommerceAccountRel.getUserName());
		Assert.assertEquals(
			Time.getShortTimestamp(
				existingCommerceAccountGroupCommerceAccountRel.getCreateDate()),
			Time.getShortTimestamp(
				newCommerceAccountGroupCommerceAccountRel.getCreateDate()));
		Assert.assertEquals(
			Time.getShortTimestamp(
				existingCommerceAccountGroupCommerceAccountRel.
					getModifiedDate()),
			Time.getShortTimestamp(
				newCommerceAccountGroupCommerceAccountRel.getModifiedDate()));
		Assert.assertEquals(
			existingCommerceAccountGroupCommerceAccountRel.
				getCommerceAccountGroupId(),
			newCommerceAccountGroupCommerceAccountRel.
				getCommerceAccountGroupId());
		Assert.assertEquals(
			existingCommerceAccountGroupCommerceAccountRel.
				getCommerceAccountId(),
			newCommerceAccountGroupCommerceAccountRel.getCommerceAccountId());
	}

	@Test
	public void testCountByCommerceAccountGroupId() throws Exception {
		_persistence.countByCommerceAccountGroupId(RandomTestUtil.nextLong());

		_persistence.countByCommerceAccountGroupId(0L);
	}

	@Test
	public void testCountByCommerceAccountId() throws Exception {
		_persistence.countByCommerceAccountId(RandomTestUtil.nextLong());

		_persistence.countByCommerceAccountId(0L);
	}

	@Test
	public void testCountByC_C() throws Exception {
		_persistence.countByC_C(
			RandomTestUtil.nextLong(), RandomTestUtil.nextLong());

		_persistence.countByC_C(0L, 0L);
	}

	@Test
	public void testCountByC_ERC() throws Exception {
		_persistence.countByC_ERC(RandomTestUtil.nextLong(), "");

		_persistence.countByC_ERC(0L, "null");

		_persistence.countByC_ERC(0L, (String)null);
	}

	@Test
	public void testFindByPrimaryKeyExisting() throws Exception {
		CommerceAccountGroupCommerceAccountRel
			newCommerceAccountGroupCommerceAccountRel =
				addCommerceAccountGroupCommerceAccountRel();

		CommerceAccountGroupCommerceAccountRel
			existingCommerceAccountGroupCommerceAccountRel =
				_persistence.findByPrimaryKey(
					newCommerceAccountGroupCommerceAccountRel.getPrimaryKey());

		Assert.assertEquals(
			existingCommerceAccountGroupCommerceAccountRel,
			newCommerceAccountGroupCommerceAccountRel);
	}

	@Test(expected = NoSuchAccountGroupCommerceAccountRelException.class)
	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		_persistence.findByPrimaryKey(pk);
	}

	@Test
	public void testFindAll() throws Exception {
		_persistence.findAll(
			QueryUtil.ALL_POS, QueryUtil.ALL_POS, getOrderByComparator());
	}

	protected OrderByComparator<CommerceAccountGroupCommerceAccountRel>
		getOrderByComparator() {

		return OrderByComparatorFactoryUtil.create(
			"CAccountGroupCAccountRel", "externalReferenceCode", true,
			"commerceAccountGroupCommerceAccountRelId", true, "companyId", true,
			"userId", true, "userName", true, "createDate", true,
			"modifiedDate", true, "commerceAccountGroupId", true,
			"commerceAccountId", true);
	}

	@Test
	public void testFetchByPrimaryKeyExisting() throws Exception {
		CommerceAccountGroupCommerceAccountRel
			newCommerceAccountGroupCommerceAccountRel =
				addCommerceAccountGroupCommerceAccountRel();

		CommerceAccountGroupCommerceAccountRel
			existingCommerceAccountGroupCommerceAccountRel =
				_persistence.fetchByPrimaryKey(
					newCommerceAccountGroupCommerceAccountRel.getPrimaryKey());

		Assert.assertEquals(
			existingCommerceAccountGroupCommerceAccountRel,
			newCommerceAccountGroupCommerceAccountRel);
	}

	@Test
	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		CommerceAccountGroupCommerceAccountRel
			missingCommerceAccountGroupCommerceAccountRel =
				_persistence.fetchByPrimaryKey(pk);

		Assert.assertNull(missingCommerceAccountGroupCommerceAccountRel);
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereAllPrimaryKeysExist()
		throws Exception {

		CommerceAccountGroupCommerceAccountRel
			newCommerceAccountGroupCommerceAccountRel1 =
				addCommerceAccountGroupCommerceAccountRel();
		CommerceAccountGroupCommerceAccountRel
			newCommerceAccountGroupCommerceAccountRel2 =
				addCommerceAccountGroupCommerceAccountRel();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(
			newCommerceAccountGroupCommerceAccountRel1.getPrimaryKey());
		primaryKeys.add(
			newCommerceAccountGroupCommerceAccountRel2.getPrimaryKey());

		Map<Serializable, CommerceAccountGroupCommerceAccountRel>
			commerceAccountGroupCommerceAccountRels =
				_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(2, commerceAccountGroupCommerceAccountRels.size());
		Assert.assertEquals(
			newCommerceAccountGroupCommerceAccountRel1,
			commerceAccountGroupCommerceAccountRels.get(
				newCommerceAccountGroupCommerceAccountRel1.getPrimaryKey()));
		Assert.assertEquals(
			newCommerceAccountGroupCommerceAccountRel2,
			commerceAccountGroupCommerceAccountRels.get(
				newCommerceAccountGroupCommerceAccountRel2.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereNoPrimaryKeysExist()
		throws Exception {

		long pk1 = RandomTestUtil.nextLong();

		long pk2 = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(pk1);
		primaryKeys.add(pk2);

		Map<Serializable, CommerceAccountGroupCommerceAccountRel>
			commerceAccountGroupCommerceAccountRels =
				_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(commerceAccountGroupCommerceAccountRels.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereSomePrimaryKeysExist()
		throws Exception {

		CommerceAccountGroupCommerceAccountRel
			newCommerceAccountGroupCommerceAccountRel =
				addCommerceAccountGroupCommerceAccountRel();

		long pk = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(
			newCommerceAccountGroupCommerceAccountRel.getPrimaryKey());
		primaryKeys.add(pk);

		Map<Serializable, CommerceAccountGroupCommerceAccountRel>
			commerceAccountGroupCommerceAccountRels =
				_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, commerceAccountGroupCommerceAccountRels.size());
		Assert.assertEquals(
			newCommerceAccountGroupCommerceAccountRel,
			commerceAccountGroupCommerceAccountRels.get(
				newCommerceAccountGroupCommerceAccountRel.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithNoPrimaryKeys() throws Exception {
		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		Map<Serializable, CommerceAccountGroupCommerceAccountRel>
			commerceAccountGroupCommerceAccountRels =
				_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(commerceAccountGroupCommerceAccountRels.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithOnePrimaryKey() throws Exception {
		CommerceAccountGroupCommerceAccountRel
			newCommerceAccountGroupCommerceAccountRel =
				addCommerceAccountGroupCommerceAccountRel();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(
			newCommerceAccountGroupCommerceAccountRel.getPrimaryKey());

		Map<Serializable, CommerceAccountGroupCommerceAccountRel>
			commerceAccountGroupCommerceAccountRels =
				_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, commerceAccountGroupCommerceAccountRels.size());
		Assert.assertEquals(
			newCommerceAccountGroupCommerceAccountRel,
			commerceAccountGroupCommerceAccountRels.get(
				newCommerceAccountGroupCommerceAccountRel.getPrimaryKey()));
	}

	@Test
	public void testActionableDynamicQuery() throws Exception {
		final IntegerWrapper count = new IntegerWrapper();

		ActionableDynamicQuery actionableDynamicQuery =
			CommerceAccountGroupCommerceAccountRelLocalServiceUtil.
				getActionableDynamicQuery();

		actionableDynamicQuery.setPerformActionMethod(
			new ActionableDynamicQuery.PerformActionMethod
				<CommerceAccountGroupCommerceAccountRel>() {

				@Override
				public void performAction(
					CommerceAccountGroupCommerceAccountRel
						commerceAccountGroupCommerceAccountRel) {

					Assert.assertNotNull(
						commerceAccountGroupCommerceAccountRel);

					count.increment();
				}

			});

		actionableDynamicQuery.performActions();

		Assert.assertEquals(count.getValue(), _persistence.countAll());
	}

	@Test
	public void testDynamicQueryByPrimaryKeyExisting() throws Exception {
		CommerceAccountGroupCommerceAccountRel
			newCommerceAccountGroupCommerceAccountRel =
				addCommerceAccountGroupCommerceAccountRel();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			CommerceAccountGroupCommerceAccountRel.class,
			_dynamicQueryClassLoader);

		dynamicQuery.add(
			RestrictionsFactoryUtil.eq(
				"commerceAccountGroupCommerceAccountRelId",
				newCommerceAccountGroupCommerceAccountRel.
					getCommerceAccountGroupCommerceAccountRelId()));

		List<CommerceAccountGroupCommerceAccountRel> result =
			_persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		CommerceAccountGroupCommerceAccountRel
			existingCommerceAccountGroupCommerceAccountRel = result.get(0);

		Assert.assertEquals(
			existingCommerceAccountGroupCommerceAccountRel,
			newCommerceAccountGroupCommerceAccountRel);
	}

	@Test
	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			CommerceAccountGroupCommerceAccountRel.class,
			_dynamicQueryClassLoader);

		dynamicQuery.add(
			RestrictionsFactoryUtil.eq(
				"commerceAccountGroupCommerceAccountRelId",
				RandomTestUtil.nextLong()));

		List<CommerceAccountGroupCommerceAccountRel> result =
			_persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testDynamicQueryByProjectionExisting() throws Exception {
		CommerceAccountGroupCommerceAccountRel
			newCommerceAccountGroupCommerceAccountRel =
				addCommerceAccountGroupCommerceAccountRel();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			CommerceAccountGroupCommerceAccountRel.class,
			_dynamicQueryClassLoader);

		dynamicQuery.setProjection(
			ProjectionFactoryUtil.property(
				"commerceAccountGroupCommerceAccountRelId"));

		Object newCommerceAccountGroupCommerceAccountRelId =
			newCommerceAccountGroupCommerceAccountRel.
				getCommerceAccountGroupCommerceAccountRelId();

		dynamicQuery.add(
			RestrictionsFactoryUtil.in(
				"commerceAccountGroupCommerceAccountRelId",
				new Object[] {newCommerceAccountGroupCommerceAccountRelId}));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		Object existingCommerceAccountGroupCommerceAccountRelId = result.get(0);

		Assert.assertEquals(
			existingCommerceAccountGroupCommerceAccountRelId,
			newCommerceAccountGroupCommerceAccountRelId);
	}

	@Test
	public void testDynamicQueryByProjectionMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			CommerceAccountGroupCommerceAccountRel.class,
			_dynamicQueryClassLoader);

		dynamicQuery.setProjection(
			ProjectionFactoryUtil.property(
				"commerceAccountGroupCommerceAccountRelId"));

		dynamicQuery.add(
			RestrictionsFactoryUtil.in(
				"commerceAccountGroupCommerceAccountRelId",
				new Object[] {RandomTestUtil.nextLong()}));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testResetOriginalValues() throws Exception {
		CommerceAccountGroupCommerceAccountRel
			newCommerceAccountGroupCommerceAccountRel =
				addCommerceAccountGroupCommerceAccountRel();

		_persistence.clearCache();

		_assertOriginalValues(
			_persistence.findByPrimaryKey(
				newCommerceAccountGroupCommerceAccountRel.getPrimaryKey()));
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

		CommerceAccountGroupCommerceAccountRel
			newCommerceAccountGroupCommerceAccountRel =
				addCommerceAccountGroupCommerceAccountRel();

		if (clearSession) {
			Session session = _persistence.openSession();

			session.flush();

			session.clear();
		}

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			CommerceAccountGroupCommerceAccountRel.class,
			_dynamicQueryClassLoader);

		dynamicQuery.add(
			RestrictionsFactoryUtil.eq(
				"commerceAccountGroupCommerceAccountRelId",
				newCommerceAccountGroupCommerceAccountRel.
					getCommerceAccountGroupCommerceAccountRelId()));

		List<CommerceAccountGroupCommerceAccountRel> result =
			_persistence.findWithDynamicQuery(dynamicQuery);

		_assertOriginalValues(result.get(0));
	}

	private void _assertOriginalValues(
		CommerceAccountGroupCommerceAccountRel
			commerceAccountGroupCommerceAccountRel) {

		Assert.assertEquals(
			Long.valueOf(
				commerceAccountGroupCommerceAccountRel.
					getCommerceAccountGroupId()),
			ReflectionTestUtil.<Long>invoke(
				commerceAccountGroupCommerceAccountRel,
				"getColumnOriginalValue", new Class<?>[] {String.class},
				"commerceAccountGroupId"));
		Assert.assertEquals(
			Long.valueOf(
				commerceAccountGroupCommerceAccountRel.getCommerceAccountId()),
			ReflectionTestUtil.<Long>invoke(
				commerceAccountGroupCommerceAccountRel,
				"getColumnOriginalValue", new Class<?>[] {String.class},
				"commerceAccountId"));

		Assert.assertEquals(
			Long.valueOf(commerceAccountGroupCommerceAccountRel.getCompanyId()),
			ReflectionTestUtil.<Long>invoke(
				commerceAccountGroupCommerceAccountRel,
				"getColumnOriginalValue", new Class<?>[] {String.class},
				"companyId"));
		Assert.assertEquals(
			commerceAccountGroupCommerceAccountRel.getExternalReferenceCode(),
			ReflectionTestUtil.invoke(
				commerceAccountGroupCommerceAccountRel,
				"getColumnOriginalValue", new Class<?>[] {String.class},
				"externalReferenceCode"));
	}

	protected CommerceAccountGroupCommerceAccountRel
			addCommerceAccountGroupCommerceAccountRel()
		throws Exception {

		long pk = RandomTestUtil.nextLong();

		CommerceAccountGroupCommerceAccountRel
			commerceAccountGroupCommerceAccountRel = _persistence.create(pk);

		commerceAccountGroupCommerceAccountRel.setExternalReferenceCode(
			RandomTestUtil.randomString());

		commerceAccountGroupCommerceAccountRel.setCompanyId(
			RandomTestUtil.nextLong());

		commerceAccountGroupCommerceAccountRel.setUserId(
			RandomTestUtil.nextLong());

		commerceAccountGroupCommerceAccountRel.setUserName(
			RandomTestUtil.randomString());

		commerceAccountGroupCommerceAccountRel.setCreateDate(
			RandomTestUtil.nextDate());

		commerceAccountGroupCommerceAccountRel.setModifiedDate(
			RandomTestUtil.nextDate());

		commerceAccountGroupCommerceAccountRel.setCommerceAccountGroupId(
			RandomTestUtil.nextLong());

		commerceAccountGroupCommerceAccountRel.setCommerceAccountId(
			RandomTestUtil.nextLong());

		_commerceAccountGroupCommerceAccountRels.add(
			_persistence.update(commerceAccountGroupCommerceAccountRel));

		return commerceAccountGroupCommerceAccountRel;
	}

	private List<CommerceAccountGroupCommerceAccountRel>
		_commerceAccountGroupCommerceAccountRels =
			new ArrayList<CommerceAccountGroupCommerceAccountRel>();
	private CommerceAccountGroupCommerceAccountRelPersistence _persistence;
	private ClassLoader _dynamicQueryClassLoader;

}