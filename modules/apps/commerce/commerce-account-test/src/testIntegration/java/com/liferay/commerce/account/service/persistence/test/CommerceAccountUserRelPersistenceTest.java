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
import com.liferay.commerce.account.exception.NoSuchAccountUserRelException;
import com.liferay.commerce.account.model.CommerceAccountUserRel;
import com.liferay.commerce.account.service.CommerceAccountUserRelLocalServiceUtil;
import com.liferay.commerce.account.service.persistence.CommerceAccountUserRelPK;
import com.liferay.commerce.account.service.persistence.CommerceAccountUserRelPersistence;
import com.liferay.commerce.account.service.persistence.CommerceAccountUserRelUtil;
import com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQueryFactoryUtil;
import com.liferay.portal.kernel.dao.orm.ProjectionFactoryUtil;
import com.liferay.portal.kernel.dao.orm.RestrictionsFactoryUtil;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.transaction.Propagation;
import com.liferay.portal.kernel.util.IntegerWrapper;
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
public class CommerceAccountUserRelPersistenceTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(), PersistenceTestRule.INSTANCE,
			new TransactionalTestRule(
				Propagation.REQUIRED, "com.liferay.commerce.account.service"));

	@Before
	public void setUp() {
		_persistence = CommerceAccountUserRelUtil.getPersistence();

		Class<?> clazz = _persistence.getClass();

		_dynamicQueryClassLoader = clazz.getClassLoader();
	}

	@After
	public void tearDown() throws Exception {
		Iterator<CommerceAccountUserRel> iterator =
			_commerceAccountUserRels.iterator();

		while (iterator.hasNext()) {
			_persistence.remove(iterator.next());

			iterator.remove();
		}
	}

	@Test
	public void testCreate() throws Exception {
		CommerceAccountUserRelPK pk = new CommerceAccountUserRelPK(
			RandomTestUtil.nextLong(), RandomTestUtil.nextLong());

		CommerceAccountUserRel commerceAccountUserRel = _persistence.create(pk);

		Assert.assertNotNull(commerceAccountUserRel);

		Assert.assertEquals(commerceAccountUserRel.getPrimaryKey(), pk);
	}

	@Test
	public void testRemove() throws Exception {
		CommerceAccountUserRel newCommerceAccountUserRel =
			addCommerceAccountUserRel();

		_persistence.remove(newCommerceAccountUserRel);

		CommerceAccountUserRel existingCommerceAccountUserRel =
			_persistence.fetchByPrimaryKey(
				newCommerceAccountUserRel.getPrimaryKey());

		Assert.assertNull(existingCommerceAccountUserRel);
	}

	@Test
	public void testUpdateNew() throws Exception {
		addCommerceAccountUserRel();
	}

	@Test
	public void testUpdateExisting() throws Exception {
		CommerceAccountUserRelPK pk = new CommerceAccountUserRelPK(
			RandomTestUtil.nextLong(), RandomTestUtil.nextLong());

		CommerceAccountUserRel newCommerceAccountUserRel = _persistence.create(
			pk);

		newCommerceAccountUserRel.setCompanyId(RandomTestUtil.nextLong());

		newCommerceAccountUserRel.setUserId(RandomTestUtil.nextLong());

		newCommerceAccountUserRel.setUserName(RandomTestUtil.randomString());

		newCommerceAccountUserRel.setCreateDate(RandomTestUtil.nextDate());

		newCommerceAccountUserRel.setModifiedDate(RandomTestUtil.nextDate());

		_commerceAccountUserRels.add(
			_persistence.update(newCommerceAccountUserRel));

		CommerceAccountUserRel existingCommerceAccountUserRel =
			_persistence.findByPrimaryKey(
				newCommerceAccountUserRel.getPrimaryKey());

		Assert.assertEquals(
			existingCommerceAccountUserRel.getCommerceAccountId(),
			newCommerceAccountUserRel.getCommerceAccountId());
		Assert.assertEquals(
			existingCommerceAccountUserRel.getCommerceAccountUserId(),
			newCommerceAccountUserRel.getCommerceAccountUserId());
		Assert.assertEquals(
			existingCommerceAccountUserRel.getCompanyId(),
			newCommerceAccountUserRel.getCompanyId());
		Assert.assertEquals(
			existingCommerceAccountUserRel.getUserId(),
			newCommerceAccountUserRel.getUserId());
		Assert.assertEquals(
			existingCommerceAccountUserRel.getUserName(),
			newCommerceAccountUserRel.getUserName());
		Assert.assertEquals(
			Time.getShortTimestamp(
				existingCommerceAccountUserRel.getCreateDate()),
			Time.getShortTimestamp(newCommerceAccountUserRel.getCreateDate()));
		Assert.assertEquals(
			Time.getShortTimestamp(
				existingCommerceAccountUserRel.getModifiedDate()),
			Time.getShortTimestamp(
				newCommerceAccountUserRel.getModifiedDate()));
	}

	@Test
	public void testCountByCommerceAccountId() throws Exception {
		_persistence.countByCommerceAccountId(RandomTestUtil.nextLong());

		_persistence.countByCommerceAccountId(0L);
	}

	@Test
	public void testCountByCommerceAccountUserId() throws Exception {
		_persistence.countByCommerceAccountUserId(RandomTestUtil.nextLong());

		_persistence.countByCommerceAccountUserId(0L);
	}

	@Test
	public void testFindByPrimaryKeyExisting() throws Exception {
		CommerceAccountUserRel newCommerceAccountUserRel =
			addCommerceAccountUserRel();

		CommerceAccountUserRel existingCommerceAccountUserRel =
			_persistence.findByPrimaryKey(
				newCommerceAccountUserRel.getPrimaryKey());

		Assert.assertEquals(
			existingCommerceAccountUserRel, newCommerceAccountUserRel);
	}

	@Test(expected = NoSuchAccountUserRelException.class)
	public void testFindByPrimaryKeyMissing() throws Exception {
		CommerceAccountUserRelPK pk = new CommerceAccountUserRelPK(
			RandomTestUtil.nextLong(), RandomTestUtil.nextLong());

		_persistence.findByPrimaryKey(pk);
	}

	@Test
	public void testFetchByPrimaryKeyExisting() throws Exception {
		CommerceAccountUserRel newCommerceAccountUserRel =
			addCommerceAccountUserRel();

		CommerceAccountUserRel existingCommerceAccountUserRel =
			_persistence.fetchByPrimaryKey(
				newCommerceAccountUserRel.getPrimaryKey());

		Assert.assertEquals(
			existingCommerceAccountUserRel, newCommerceAccountUserRel);
	}

	@Test
	public void testFetchByPrimaryKeyMissing() throws Exception {
		CommerceAccountUserRelPK pk = new CommerceAccountUserRelPK(
			RandomTestUtil.nextLong(), RandomTestUtil.nextLong());

		CommerceAccountUserRel missingCommerceAccountUserRel =
			_persistence.fetchByPrimaryKey(pk);

		Assert.assertNull(missingCommerceAccountUserRel);
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereAllPrimaryKeysExist()
		throws Exception {

		CommerceAccountUserRel newCommerceAccountUserRel1 =
			addCommerceAccountUserRel();
		CommerceAccountUserRel newCommerceAccountUserRel2 =
			addCommerceAccountUserRel();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newCommerceAccountUserRel1.getPrimaryKey());
		primaryKeys.add(newCommerceAccountUserRel2.getPrimaryKey());

		Map<Serializable, CommerceAccountUserRel> commerceAccountUserRels =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(2, commerceAccountUserRels.size());
		Assert.assertEquals(
			newCommerceAccountUserRel1,
			commerceAccountUserRels.get(
				newCommerceAccountUserRel1.getPrimaryKey()));
		Assert.assertEquals(
			newCommerceAccountUserRel2,
			commerceAccountUserRels.get(
				newCommerceAccountUserRel2.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereNoPrimaryKeysExist()
		throws Exception {

		CommerceAccountUserRelPK pk1 = new CommerceAccountUserRelPK(
			RandomTestUtil.nextLong(), RandomTestUtil.nextLong());

		CommerceAccountUserRelPK pk2 = new CommerceAccountUserRelPK(
			RandomTestUtil.nextLong(), RandomTestUtil.nextLong());

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(pk1);
		primaryKeys.add(pk2);

		Map<Serializable, CommerceAccountUserRel> commerceAccountUserRels =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(commerceAccountUserRels.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereSomePrimaryKeysExist()
		throws Exception {

		CommerceAccountUserRel newCommerceAccountUserRel =
			addCommerceAccountUserRel();

		CommerceAccountUserRelPK pk = new CommerceAccountUserRelPK(
			RandomTestUtil.nextLong(), RandomTestUtil.nextLong());

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newCommerceAccountUserRel.getPrimaryKey());
		primaryKeys.add(pk);

		Map<Serializable, CommerceAccountUserRel> commerceAccountUserRels =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, commerceAccountUserRels.size());
		Assert.assertEquals(
			newCommerceAccountUserRel,
			commerceAccountUserRels.get(
				newCommerceAccountUserRel.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithNoPrimaryKeys() throws Exception {
		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		Map<Serializable, CommerceAccountUserRel> commerceAccountUserRels =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(commerceAccountUserRels.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithOnePrimaryKey() throws Exception {
		CommerceAccountUserRel newCommerceAccountUserRel =
			addCommerceAccountUserRel();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newCommerceAccountUserRel.getPrimaryKey());

		Map<Serializable, CommerceAccountUserRel> commerceAccountUserRels =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, commerceAccountUserRels.size());
		Assert.assertEquals(
			newCommerceAccountUserRel,
			commerceAccountUserRels.get(
				newCommerceAccountUserRel.getPrimaryKey()));
	}

	@Test
	public void testActionableDynamicQuery() throws Exception {
		final IntegerWrapper count = new IntegerWrapper();

		ActionableDynamicQuery actionableDynamicQuery =
			CommerceAccountUserRelLocalServiceUtil.getActionableDynamicQuery();

		actionableDynamicQuery.setPerformActionMethod(
			new ActionableDynamicQuery.PerformActionMethod
				<CommerceAccountUserRel>() {

				@Override
				public void performAction(
					CommerceAccountUserRel commerceAccountUserRel) {

					Assert.assertNotNull(commerceAccountUserRel);

					count.increment();
				}

			});

		actionableDynamicQuery.performActions();

		Assert.assertEquals(count.getValue(), _persistence.countAll());
	}

	@Test
	public void testDynamicQueryByPrimaryKeyExisting() throws Exception {
		CommerceAccountUserRel newCommerceAccountUserRel =
			addCommerceAccountUserRel();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			CommerceAccountUserRel.class, _dynamicQueryClassLoader);

		dynamicQuery.add(
			RestrictionsFactoryUtil.eq(
				"id.commerceAccountId",
				newCommerceAccountUserRel.getCommerceAccountId()));
		dynamicQuery.add(
			RestrictionsFactoryUtil.eq(
				"id.commerceAccountUserId",
				newCommerceAccountUserRel.getCommerceAccountUserId()));

		List<CommerceAccountUserRel> result = _persistence.findWithDynamicQuery(
			dynamicQuery);

		Assert.assertEquals(1, result.size());

		CommerceAccountUserRel existingCommerceAccountUserRel = result.get(0);

		Assert.assertEquals(
			existingCommerceAccountUserRel, newCommerceAccountUserRel);
	}

	@Test
	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			CommerceAccountUserRel.class, _dynamicQueryClassLoader);

		dynamicQuery.add(
			RestrictionsFactoryUtil.eq(
				"id.commerceAccountId", RandomTestUtil.nextLong()));
		dynamicQuery.add(
			RestrictionsFactoryUtil.eq(
				"id.commerceAccountUserId", RandomTestUtil.nextLong()));

		List<CommerceAccountUserRel> result = _persistence.findWithDynamicQuery(
			dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testDynamicQueryByProjectionExisting() throws Exception {
		CommerceAccountUserRel newCommerceAccountUserRel =
			addCommerceAccountUserRel();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			CommerceAccountUserRel.class, _dynamicQueryClassLoader);

		dynamicQuery.setProjection(
			ProjectionFactoryUtil.property("id.commerceAccountId"));

		Object newCommerceAccountId =
			newCommerceAccountUserRel.getCommerceAccountId();

		dynamicQuery.add(
			RestrictionsFactoryUtil.in(
				"id.commerceAccountId", new Object[] {newCommerceAccountId}));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		Object existingCommerceAccountId = result.get(0);

		Assert.assertEquals(existingCommerceAccountId, newCommerceAccountId);
	}

	@Test
	public void testDynamicQueryByProjectionMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			CommerceAccountUserRel.class, _dynamicQueryClassLoader);

		dynamicQuery.setProjection(
			ProjectionFactoryUtil.property("id.commerceAccountId"));

		dynamicQuery.add(
			RestrictionsFactoryUtil.in(
				"id.commerceAccountId",
				new Object[] {RandomTestUtil.nextLong()}));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	protected CommerceAccountUserRel addCommerceAccountUserRel()
		throws Exception {

		CommerceAccountUserRelPK pk = new CommerceAccountUserRelPK(
			RandomTestUtil.nextLong(), RandomTestUtil.nextLong());

		CommerceAccountUserRel commerceAccountUserRel = _persistence.create(pk);

		commerceAccountUserRel.setCompanyId(RandomTestUtil.nextLong());

		commerceAccountUserRel.setUserId(RandomTestUtil.nextLong());

		commerceAccountUserRel.setUserName(RandomTestUtil.randomString());

		commerceAccountUserRel.setCreateDate(RandomTestUtil.nextDate());

		commerceAccountUserRel.setModifiedDate(RandomTestUtil.nextDate());

		_commerceAccountUserRels.add(
			_persistence.update(commerceAccountUserRel));

		return commerceAccountUserRel;
	}

	private List<CommerceAccountUserRel> _commerceAccountUserRels =
		new ArrayList<CommerceAccountUserRel>();
	private CommerceAccountUserRelPersistence _persistence;
	private ClassLoader _dynamicQueryClassLoader;

}