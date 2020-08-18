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
import com.liferay.commerce.account.exception.NoSuchAccountOrganizationRelException;
import com.liferay.commerce.account.model.CommerceAccountOrganizationRel;
import com.liferay.commerce.account.service.CommerceAccountOrganizationRelLocalServiceUtil;
import com.liferay.commerce.account.service.persistence.CommerceAccountOrganizationRelPK;
import com.liferay.commerce.account.service.persistence.CommerceAccountOrganizationRelPersistence;
import com.liferay.commerce.account.service.persistence.CommerceAccountOrganizationRelUtil;
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
public class CommerceAccountOrganizationRelPersistenceTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(), PersistenceTestRule.INSTANCE,
			new TransactionalTestRule(
				Propagation.REQUIRED, "com.liferay.commerce.account.service"));

	@Before
	public void setUp() {
		_persistence = CommerceAccountOrganizationRelUtil.getPersistence();

		Class<?> clazz = _persistence.getClass();

		_dynamicQueryClassLoader = clazz.getClassLoader();
	}

	@After
	public void tearDown() throws Exception {
		Iterator<CommerceAccountOrganizationRel> iterator =
			_commerceAccountOrganizationRels.iterator();

		while (iterator.hasNext()) {
			_persistence.remove(iterator.next());

			iterator.remove();
		}
	}

	@Test
	public void testCreate() throws Exception {
		CommerceAccountOrganizationRelPK pk =
			new CommerceAccountOrganizationRelPK(
				RandomTestUtil.nextLong(), RandomTestUtil.nextLong());

		CommerceAccountOrganizationRel commerceAccountOrganizationRel =
			_persistence.create(pk);

		Assert.assertNotNull(commerceAccountOrganizationRel);

		Assert.assertEquals(commerceAccountOrganizationRel.getPrimaryKey(), pk);
	}

	@Test
	public void testRemove() throws Exception {
		CommerceAccountOrganizationRel newCommerceAccountOrganizationRel =
			addCommerceAccountOrganizationRel();

		_persistence.remove(newCommerceAccountOrganizationRel);

		CommerceAccountOrganizationRel existingCommerceAccountOrganizationRel =
			_persistence.fetchByPrimaryKey(
				newCommerceAccountOrganizationRel.getPrimaryKey());

		Assert.assertNull(existingCommerceAccountOrganizationRel);
	}

	@Test
	public void testUpdateNew() throws Exception {
		addCommerceAccountOrganizationRel();
	}

	@Test
	public void testUpdateExisting() throws Exception {
		CommerceAccountOrganizationRelPK pk =
			new CommerceAccountOrganizationRelPK(
				RandomTestUtil.nextLong(), RandomTestUtil.nextLong());

		CommerceAccountOrganizationRel newCommerceAccountOrganizationRel =
			_persistence.create(pk);

		newCommerceAccountOrganizationRel.setCompanyId(
			RandomTestUtil.nextLong());

		newCommerceAccountOrganizationRel.setUserId(RandomTestUtil.nextLong());

		newCommerceAccountOrganizationRel.setUserName(
			RandomTestUtil.randomString());

		newCommerceAccountOrganizationRel.setCreateDate(
			RandomTestUtil.nextDate());

		newCommerceAccountOrganizationRel.setModifiedDate(
			RandomTestUtil.nextDate());

		_commerceAccountOrganizationRels.add(
			_persistence.update(newCommerceAccountOrganizationRel));

		CommerceAccountOrganizationRel existingCommerceAccountOrganizationRel =
			_persistence.findByPrimaryKey(
				newCommerceAccountOrganizationRel.getPrimaryKey());

		Assert.assertEquals(
			existingCommerceAccountOrganizationRel.getCommerceAccountId(),
			newCommerceAccountOrganizationRel.getCommerceAccountId());
		Assert.assertEquals(
			existingCommerceAccountOrganizationRel.getOrganizationId(),
			newCommerceAccountOrganizationRel.getOrganizationId());
		Assert.assertEquals(
			existingCommerceAccountOrganizationRel.getCompanyId(),
			newCommerceAccountOrganizationRel.getCompanyId());
		Assert.assertEquals(
			existingCommerceAccountOrganizationRel.getUserId(),
			newCommerceAccountOrganizationRel.getUserId());
		Assert.assertEquals(
			existingCommerceAccountOrganizationRel.getUserName(),
			newCommerceAccountOrganizationRel.getUserName());
		Assert.assertEquals(
			Time.getShortTimestamp(
				existingCommerceAccountOrganizationRel.getCreateDate()),
			Time.getShortTimestamp(
				newCommerceAccountOrganizationRel.getCreateDate()));
		Assert.assertEquals(
			Time.getShortTimestamp(
				existingCommerceAccountOrganizationRel.getModifiedDate()),
			Time.getShortTimestamp(
				newCommerceAccountOrganizationRel.getModifiedDate()));
	}

	@Test
	public void testCountByCommerceAccountId() throws Exception {
		_persistence.countByCommerceAccountId(RandomTestUtil.nextLong());

		_persistence.countByCommerceAccountId(0L);
	}

	@Test
	public void testCountByOrganizationId() throws Exception {
		_persistence.countByOrganizationId(RandomTestUtil.nextLong());

		_persistence.countByOrganizationId(0L);
	}

	@Test
	public void testFindByPrimaryKeyExisting() throws Exception {
		CommerceAccountOrganizationRel newCommerceAccountOrganizationRel =
			addCommerceAccountOrganizationRel();

		CommerceAccountOrganizationRel existingCommerceAccountOrganizationRel =
			_persistence.findByPrimaryKey(
				newCommerceAccountOrganizationRel.getPrimaryKey());

		Assert.assertEquals(
			existingCommerceAccountOrganizationRel,
			newCommerceAccountOrganizationRel);
	}

	@Test(expected = NoSuchAccountOrganizationRelException.class)
	public void testFindByPrimaryKeyMissing() throws Exception {
		CommerceAccountOrganizationRelPK pk =
			new CommerceAccountOrganizationRelPK(
				RandomTestUtil.nextLong(), RandomTestUtil.nextLong());

		_persistence.findByPrimaryKey(pk);
	}

	@Test
	public void testFetchByPrimaryKeyExisting() throws Exception {
		CommerceAccountOrganizationRel newCommerceAccountOrganizationRel =
			addCommerceAccountOrganizationRel();

		CommerceAccountOrganizationRel existingCommerceAccountOrganizationRel =
			_persistence.fetchByPrimaryKey(
				newCommerceAccountOrganizationRel.getPrimaryKey());

		Assert.assertEquals(
			existingCommerceAccountOrganizationRel,
			newCommerceAccountOrganizationRel);
	}

	@Test
	public void testFetchByPrimaryKeyMissing() throws Exception {
		CommerceAccountOrganizationRelPK pk =
			new CommerceAccountOrganizationRelPK(
				RandomTestUtil.nextLong(), RandomTestUtil.nextLong());

		CommerceAccountOrganizationRel missingCommerceAccountOrganizationRel =
			_persistence.fetchByPrimaryKey(pk);

		Assert.assertNull(missingCommerceAccountOrganizationRel);
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereAllPrimaryKeysExist()
		throws Exception {

		CommerceAccountOrganizationRel newCommerceAccountOrganizationRel1 =
			addCommerceAccountOrganizationRel();
		CommerceAccountOrganizationRel newCommerceAccountOrganizationRel2 =
			addCommerceAccountOrganizationRel();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newCommerceAccountOrganizationRel1.getPrimaryKey());
		primaryKeys.add(newCommerceAccountOrganizationRel2.getPrimaryKey());

		Map<Serializable, CommerceAccountOrganizationRel>
			commerceAccountOrganizationRels = _persistence.fetchByPrimaryKeys(
				primaryKeys);

		Assert.assertEquals(2, commerceAccountOrganizationRels.size());
		Assert.assertEquals(
			newCommerceAccountOrganizationRel1,
			commerceAccountOrganizationRels.get(
				newCommerceAccountOrganizationRel1.getPrimaryKey()));
		Assert.assertEquals(
			newCommerceAccountOrganizationRel2,
			commerceAccountOrganizationRels.get(
				newCommerceAccountOrganizationRel2.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereNoPrimaryKeysExist()
		throws Exception {

		CommerceAccountOrganizationRelPK pk1 =
			new CommerceAccountOrganizationRelPK(
				RandomTestUtil.nextLong(), RandomTestUtil.nextLong());

		CommerceAccountOrganizationRelPK pk2 =
			new CommerceAccountOrganizationRelPK(
				RandomTestUtil.nextLong(), RandomTestUtil.nextLong());

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(pk1);
		primaryKeys.add(pk2);

		Map<Serializable, CommerceAccountOrganizationRel>
			commerceAccountOrganizationRels = _persistence.fetchByPrimaryKeys(
				primaryKeys);

		Assert.assertTrue(commerceAccountOrganizationRels.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereSomePrimaryKeysExist()
		throws Exception {

		CommerceAccountOrganizationRel newCommerceAccountOrganizationRel =
			addCommerceAccountOrganizationRel();

		CommerceAccountOrganizationRelPK pk =
			new CommerceAccountOrganizationRelPK(
				RandomTestUtil.nextLong(), RandomTestUtil.nextLong());

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newCommerceAccountOrganizationRel.getPrimaryKey());
		primaryKeys.add(pk);

		Map<Serializable, CommerceAccountOrganizationRel>
			commerceAccountOrganizationRels = _persistence.fetchByPrimaryKeys(
				primaryKeys);

		Assert.assertEquals(1, commerceAccountOrganizationRels.size());
		Assert.assertEquals(
			newCommerceAccountOrganizationRel,
			commerceAccountOrganizationRels.get(
				newCommerceAccountOrganizationRel.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithNoPrimaryKeys() throws Exception {
		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		Map<Serializable, CommerceAccountOrganizationRel>
			commerceAccountOrganizationRels = _persistence.fetchByPrimaryKeys(
				primaryKeys);

		Assert.assertTrue(commerceAccountOrganizationRels.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithOnePrimaryKey() throws Exception {
		CommerceAccountOrganizationRel newCommerceAccountOrganizationRel =
			addCommerceAccountOrganizationRel();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newCommerceAccountOrganizationRel.getPrimaryKey());

		Map<Serializable, CommerceAccountOrganizationRel>
			commerceAccountOrganizationRels = _persistence.fetchByPrimaryKeys(
				primaryKeys);

		Assert.assertEquals(1, commerceAccountOrganizationRels.size());
		Assert.assertEquals(
			newCommerceAccountOrganizationRel,
			commerceAccountOrganizationRels.get(
				newCommerceAccountOrganizationRel.getPrimaryKey()));
	}

	@Test
	public void testActionableDynamicQuery() throws Exception {
		final IntegerWrapper count = new IntegerWrapper();

		ActionableDynamicQuery actionableDynamicQuery =
			CommerceAccountOrganizationRelLocalServiceUtil.
				getActionableDynamicQuery();

		actionableDynamicQuery.setPerformActionMethod(
			new ActionableDynamicQuery.PerformActionMethod
				<CommerceAccountOrganizationRel>() {

				@Override
				public void performAction(
					CommerceAccountOrganizationRel
						commerceAccountOrganizationRel) {

					Assert.assertNotNull(commerceAccountOrganizationRel);

					count.increment();
				}

			});

		actionableDynamicQuery.performActions();

		Assert.assertEquals(count.getValue(), _persistence.countAll());
	}

	@Test
	public void testDynamicQueryByPrimaryKeyExisting() throws Exception {
		CommerceAccountOrganizationRel newCommerceAccountOrganizationRel =
			addCommerceAccountOrganizationRel();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			CommerceAccountOrganizationRel.class, _dynamicQueryClassLoader);

		dynamicQuery.add(
			RestrictionsFactoryUtil.eq(
				"id.commerceAccountId",
				newCommerceAccountOrganizationRel.getCommerceAccountId()));
		dynamicQuery.add(
			RestrictionsFactoryUtil.eq(
				"id.organizationId",
				newCommerceAccountOrganizationRel.getOrganizationId()));

		List<CommerceAccountOrganizationRel> result =
			_persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		CommerceAccountOrganizationRel existingCommerceAccountOrganizationRel =
			result.get(0);

		Assert.assertEquals(
			existingCommerceAccountOrganizationRel,
			newCommerceAccountOrganizationRel);
	}

	@Test
	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			CommerceAccountOrganizationRel.class, _dynamicQueryClassLoader);

		dynamicQuery.add(
			RestrictionsFactoryUtil.eq(
				"id.commerceAccountId", RandomTestUtil.nextLong()));
		dynamicQuery.add(
			RestrictionsFactoryUtil.eq(
				"id.organizationId", RandomTestUtil.nextLong()));

		List<CommerceAccountOrganizationRel> result =
			_persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testDynamicQueryByProjectionExisting() throws Exception {
		CommerceAccountOrganizationRel newCommerceAccountOrganizationRel =
			addCommerceAccountOrganizationRel();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			CommerceAccountOrganizationRel.class, _dynamicQueryClassLoader);

		dynamicQuery.setProjection(
			ProjectionFactoryUtil.property("id.commerceAccountId"));

		Object newCommerceAccountId =
			newCommerceAccountOrganizationRel.getCommerceAccountId();

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
			CommerceAccountOrganizationRel.class, _dynamicQueryClassLoader);

		dynamicQuery.setProjection(
			ProjectionFactoryUtil.property("id.commerceAccountId"));

		dynamicQuery.add(
			RestrictionsFactoryUtil.in(
				"id.commerceAccountId",
				new Object[] {RandomTestUtil.nextLong()}));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	protected CommerceAccountOrganizationRel addCommerceAccountOrganizationRel()
		throws Exception {

		CommerceAccountOrganizationRelPK pk =
			new CommerceAccountOrganizationRelPK(
				RandomTestUtil.nextLong(), RandomTestUtil.nextLong());

		CommerceAccountOrganizationRel commerceAccountOrganizationRel =
			_persistence.create(pk);

		commerceAccountOrganizationRel.setCompanyId(RandomTestUtil.nextLong());

		commerceAccountOrganizationRel.setUserId(RandomTestUtil.nextLong());

		commerceAccountOrganizationRel.setUserName(
			RandomTestUtil.randomString());

		commerceAccountOrganizationRel.setCreateDate(RandomTestUtil.nextDate());

		commerceAccountOrganizationRel.setModifiedDate(
			RandomTestUtil.nextDate());

		_commerceAccountOrganizationRels.add(
			_persistence.update(commerceAccountOrganizationRel));

		return commerceAccountOrganizationRel;
	}

	private List<CommerceAccountOrganizationRel>
		_commerceAccountOrganizationRels =
			new ArrayList<CommerceAccountOrganizationRel>();
	private CommerceAccountOrganizationRelPersistence _persistence;
	private ClassLoader _dynamicQueryClassLoader;

}