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
import com.liferay.commerce.account.exception.NoSuchAccountGroupException;
import com.liferay.commerce.account.model.CommerceAccountGroup;
import com.liferay.commerce.account.service.CommerceAccountGroupLocalServiceUtil;
import com.liferay.commerce.account.service.persistence.CommerceAccountGroupPersistence;
import com.liferay.commerce.account.service.persistence.CommerceAccountGroupUtil;
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
public class CommerceAccountGroupPersistenceTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(), PersistenceTestRule.INSTANCE,
			new TransactionalTestRule(
				Propagation.REQUIRED, "com.liferay.commerce.account.service"));

	@Before
	public void setUp() {
		_persistence = CommerceAccountGroupUtil.getPersistence();

		Class<?> clazz = _persistence.getClass();

		_dynamicQueryClassLoader = clazz.getClassLoader();
	}

	@After
	public void tearDown() throws Exception {
		Iterator<CommerceAccountGroup> iterator =
			_commerceAccountGroups.iterator();

		while (iterator.hasNext()) {
			_persistence.remove(iterator.next());

			iterator.remove();
		}
	}

	@Test
	public void testCreate() throws Exception {
		long pk = RandomTestUtil.nextLong();

		CommerceAccountGroup commerceAccountGroup = _persistence.create(pk);

		Assert.assertNotNull(commerceAccountGroup);

		Assert.assertEquals(commerceAccountGroup.getPrimaryKey(), pk);
	}

	@Test
	public void testRemove() throws Exception {
		CommerceAccountGroup newCommerceAccountGroup =
			addCommerceAccountGroup();

		_persistence.remove(newCommerceAccountGroup);

		CommerceAccountGroup existingCommerceAccountGroup =
			_persistence.fetchByPrimaryKey(
				newCommerceAccountGroup.getPrimaryKey());

		Assert.assertNull(existingCommerceAccountGroup);
	}

	@Test
	public void testUpdateNew() throws Exception {
		addCommerceAccountGroup();
	}

	@Test
	public void testUpdateExisting() throws Exception {
		long pk = RandomTestUtil.nextLong();

		CommerceAccountGroup newCommerceAccountGroup = _persistence.create(pk);

		newCommerceAccountGroup.setExternalReferenceCode(
			RandomTestUtil.randomString());

		newCommerceAccountGroup.setCompanyId(RandomTestUtil.nextLong());

		newCommerceAccountGroup.setUserId(RandomTestUtil.nextLong());

		newCommerceAccountGroup.setUserName(RandomTestUtil.randomString());

		newCommerceAccountGroup.setCreateDate(RandomTestUtil.nextDate());

		newCommerceAccountGroup.setModifiedDate(RandomTestUtil.nextDate());

		newCommerceAccountGroup.setName(RandomTestUtil.randomString());

		newCommerceAccountGroup.setType(RandomTestUtil.nextInt());

		newCommerceAccountGroup.setSystem(RandomTestUtil.randomBoolean());

		_commerceAccountGroups.add(
			_persistence.update(newCommerceAccountGroup));

		CommerceAccountGroup existingCommerceAccountGroup =
			_persistence.findByPrimaryKey(
				newCommerceAccountGroup.getPrimaryKey());

		Assert.assertEquals(
			existingCommerceAccountGroup.getExternalReferenceCode(),
			newCommerceAccountGroup.getExternalReferenceCode());
		Assert.assertEquals(
			existingCommerceAccountGroup.getCommerceAccountGroupId(),
			newCommerceAccountGroup.getCommerceAccountGroupId());
		Assert.assertEquals(
			existingCommerceAccountGroup.getCompanyId(),
			newCommerceAccountGroup.getCompanyId());
		Assert.assertEquals(
			existingCommerceAccountGroup.getUserId(),
			newCommerceAccountGroup.getUserId());
		Assert.assertEquals(
			existingCommerceAccountGroup.getUserName(),
			newCommerceAccountGroup.getUserName());
		Assert.assertEquals(
			Time.getShortTimestamp(
				existingCommerceAccountGroup.getCreateDate()),
			Time.getShortTimestamp(newCommerceAccountGroup.getCreateDate()));
		Assert.assertEquals(
			Time.getShortTimestamp(
				existingCommerceAccountGroup.getModifiedDate()),
			Time.getShortTimestamp(newCommerceAccountGroup.getModifiedDate()));
		Assert.assertEquals(
			existingCommerceAccountGroup.getName(),
			newCommerceAccountGroup.getName());
		Assert.assertEquals(
			existingCommerceAccountGroup.getType(),
			newCommerceAccountGroup.getType());
		Assert.assertEquals(
			existingCommerceAccountGroup.isSystem(),
			newCommerceAccountGroup.isSystem());
	}

	@Test
	public void testCountByCommerceAccountGroupIds() throws Exception {
		_persistence.countByCommerceAccountGroupIds(RandomTestUtil.nextLong());

		_persistence.countByCommerceAccountGroupIds(0L);
	}

	@Test
	public void testCountByCommerceAccountGroupIdsArrayable() throws Exception {
		_persistence.countByCommerceAccountGroupIds(
			new long[] {RandomTestUtil.nextLong(), 0L});
	}

	@Test
	public void testCountByCompanyId() throws Exception {
		_persistence.countByCompanyId(RandomTestUtil.nextLong());

		_persistence.countByCompanyId(0L);
	}

	@Test
	public void testCountByC_T() throws Exception {
		_persistence.countByC_T(
			RandomTestUtil.nextLong(), RandomTestUtil.nextInt());

		_persistence.countByC_T(0L, 0);
	}

	@Test
	public void testCountByC_ERC() throws Exception {
		_persistence.countByC_ERC(RandomTestUtil.nextLong(), "");

		_persistence.countByC_ERC(0L, "null");

		_persistence.countByC_ERC(0L, (String)null);
	}

	@Test
	public void testFindByPrimaryKeyExisting() throws Exception {
		CommerceAccountGroup newCommerceAccountGroup =
			addCommerceAccountGroup();

		CommerceAccountGroup existingCommerceAccountGroup =
			_persistence.findByPrimaryKey(
				newCommerceAccountGroup.getPrimaryKey());

		Assert.assertEquals(
			existingCommerceAccountGroup, newCommerceAccountGroup);
	}

	@Test(expected = NoSuchAccountGroupException.class)
	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		_persistence.findByPrimaryKey(pk);
	}

	@Test
	public void testFindAll() throws Exception {
		_persistence.findAll(
			QueryUtil.ALL_POS, QueryUtil.ALL_POS, getOrderByComparator());
	}

	protected OrderByComparator<CommerceAccountGroup> getOrderByComparator() {
		return OrderByComparatorFactoryUtil.create(
			"CommerceAccountGroup", "externalReferenceCode", true,
			"commerceAccountGroupId", true, "companyId", true, "userId", true,
			"userName", true, "createDate", true, "modifiedDate", true, "name",
			true, "type", true, "system", true);
	}

	@Test
	public void testFetchByPrimaryKeyExisting() throws Exception {
		CommerceAccountGroup newCommerceAccountGroup =
			addCommerceAccountGroup();

		CommerceAccountGroup existingCommerceAccountGroup =
			_persistence.fetchByPrimaryKey(
				newCommerceAccountGroup.getPrimaryKey());

		Assert.assertEquals(
			existingCommerceAccountGroup, newCommerceAccountGroup);
	}

	@Test
	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		CommerceAccountGroup missingCommerceAccountGroup =
			_persistence.fetchByPrimaryKey(pk);

		Assert.assertNull(missingCommerceAccountGroup);
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereAllPrimaryKeysExist()
		throws Exception {

		CommerceAccountGroup newCommerceAccountGroup1 =
			addCommerceAccountGroup();
		CommerceAccountGroup newCommerceAccountGroup2 =
			addCommerceAccountGroup();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newCommerceAccountGroup1.getPrimaryKey());
		primaryKeys.add(newCommerceAccountGroup2.getPrimaryKey());

		Map<Serializable, CommerceAccountGroup> commerceAccountGroups =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(2, commerceAccountGroups.size());
		Assert.assertEquals(
			newCommerceAccountGroup1,
			commerceAccountGroups.get(
				newCommerceAccountGroup1.getPrimaryKey()));
		Assert.assertEquals(
			newCommerceAccountGroup2,
			commerceAccountGroups.get(
				newCommerceAccountGroup2.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereNoPrimaryKeysExist()
		throws Exception {

		long pk1 = RandomTestUtil.nextLong();

		long pk2 = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(pk1);
		primaryKeys.add(pk2);

		Map<Serializable, CommerceAccountGroup> commerceAccountGroups =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(commerceAccountGroups.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereSomePrimaryKeysExist()
		throws Exception {

		CommerceAccountGroup newCommerceAccountGroup =
			addCommerceAccountGroup();

		long pk = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newCommerceAccountGroup.getPrimaryKey());
		primaryKeys.add(pk);

		Map<Serializable, CommerceAccountGroup> commerceAccountGroups =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, commerceAccountGroups.size());
		Assert.assertEquals(
			newCommerceAccountGroup,
			commerceAccountGroups.get(newCommerceAccountGroup.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithNoPrimaryKeys() throws Exception {
		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		Map<Serializable, CommerceAccountGroup> commerceAccountGroups =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(commerceAccountGroups.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithOnePrimaryKey() throws Exception {
		CommerceAccountGroup newCommerceAccountGroup =
			addCommerceAccountGroup();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newCommerceAccountGroup.getPrimaryKey());

		Map<Serializable, CommerceAccountGroup> commerceAccountGroups =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, commerceAccountGroups.size());
		Assert.assertEquals(
			newCommerceAccountGroup,
			commerceAccountGroups.get(newCommerceAccountGroup.getPrimaryKey()));
	}

	@Test
	public void testActionableDynamicQuery() throws Exception {
		final IntegerWrapper count = new IntegerWrapper();

		ActionableDynamicQuery actionableDynamicQuery =
			CommerceAccountGroupLocalServiceUtil.getActionableDynamicQuery();

		actionableDynamicQuery.setPerformActionMethod(
			new ActionableDynamicQuery.PerformActionMethod
				<CommerceAccountGroup>() {

				@Override
				public void performAction(
					CommerceAccountGroup commerceAccountGroup) {

					Assert.assertNotNull(commerceAccountGroup);

					count.increment();
				}

			});

		actionableDynamicQuery.performActions();

		Assert.assertEquals(count.getValue(), _persistence.countAll());
	}

	@Test
	public void testDynamicQueryByPrimaryKeyExisting() throws Exception {
		CommerceAccountGroup newCommerceAccountGroup =
			addCommerceAccountGroup();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			CommerceAccountGroup.class, _dynamicQueryClassLoader);

		dynamicQuery.add(
			RestrictionsFactoryUtil.eq(
				"commerceAccountGroupId",
				newCommerceAccountGroup.getCommerceAccountGroupId()));

		List<CommerceAccountGroup> result = _persistence.findWithDynamicQuery(
			dynamicQuery);

		Assert.assertEquals(1, result.size());

		CommerceAccountGroup existingCommerceAccountGroup = result.get(0);

		Assert.assertEquals(
			existingCommerceAccountGroup, newCommerceAccountGroup);
	}

	@Test
	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			CommerceAccountGroup.class, _dynamicQueryClassLoader);

		dynamicQuery.add(
			RestrictionsFactoryUtil.eq(
				"commerceAccountGroupId", RandomTestUtil.nextLong()));

		List<CommerceAccountGroup> result = _persistence.findWithDynamicQuery(
			dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testDynamicQueryByProjectionExisting() throws Exception {
		CommerceAccountGroup newCommerceAccountGroup =
			addCommerceAccountGroup();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			CommerceAccountGroup.class, _dynamicQueryClassLoader);

		dynamicQuery.setProjection(
			ProjectionFactoryUtil.property("commerceAccountGroupId"));

		Object newCommerceAccountGroupId =
			newCommerceAccountGroup.getCommerceAccountGroupId();

		dynamicQuery.add(
			RestrictionsFactoryUtil.in(
				"commerceAccountGroupId",
				new Object[] {newCommerceAccountGroupId}));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		Object existingCommerceAccountGroupId = result.get(0);

		Assert.assertEquals(
			existingCommerceAccountGroupId, newCommerceAccountGroupId);
	}

	@Test
	public void testDynamicQueryByProjectionMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			CommerceAccountGroup.class, _dynamicQueryClassLoader);

		dynamicQuery.setProjection(
			ProjectionFactoryUtil.property("commerceAccountGroupId"));

		dynamicQuery.add(
			RestrictionsFactoryUtil.in(
				"commerceAccountGroupId",
				new Object[] {RandomTestUtil.nextLong()}));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testResetOriginalValues() throws Exception {
		CommerceAccountGroup newCommerceAccountGroup =
			addCommerceAccountGroup();

		_persistence.clearCache();

		_assertOriginalValues(
			_persistence.findByPrimaryKey(
				newCommerceAccountGroup.getPrimaryKey()));
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

		CommerceAccountGroup newCommerceAccountGroup =
			addCommerceAccountGroup();

		if (clearSession) {
			Session session = _persistence.openSession();

			session.flush();

			session.clear();
		}

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			CommerceAccountGroup.class, _dynamicQueryClassLoader);

		dynamicQuery.add(
			RestrictionsFactoryUtil.eq(
				"commerceAccountGroupId",
				newCommerceAccountGroup.getCommerceAccountGroupId()));

		List<CommerceAccountGroup> result = _persistence.findWithDynamicQuery(
			dynamicQuery);

		_assertOriginalValues(result.get(0));
	}

	private void _assertOriginalValues(
		CommerceAccountGroup commerceAccountGroup) {

		Assert.assertEquals(
			Long.valueOf(commerceAccountGroup.getCompanyId()),
			ReflectionTestUtil.<Long>invoke(
				commerceAccountGroup, "getColumnOriginalValue",
				new Class<?>[] {String.class}, "companyId"));
		Assert.assertEquals(
			commerceAccountGroup.getExternalReferenceCode(),
			ReflectionTestUtil.invoke(
				commerceAccountGroup, "getColumnOriginalValue",
				new Class<?>[] {String.class}, "externalReferenceCode"));
	}

	protected CommerceAccountGroup addCommerceAccountGroup() throws Exception {
		long pk = RandomTestUtil.nextLong();

		CommerceAccountGroup commerceAccountGroup = _persistence.create(pk);

		commerceAccountGroup.setExternalReferenceCode(
			RandomTestUtil.randomString());

		commerceAccountGroup.setCompanyId(RandomTestUtil.nextLong());

		commerceAccountGroup.setUserId(RandomTestUtil.nextLong());

		commerceAccountGroup.setUserName(RandomTestUtil.randomString());

		commerceAccountGroup.setCreateDate(RandomTestUtil.nextDate());

		commerceAccountGroup.setModifiedDate(RandomTestUtil.nextDate());

		commerceAccountGroup.setName(RandomTestUtil.randomString());

		commerceAccountGroup.setType(RandomTestUtil.nextInt());

		commerceAccountGroup.setSystem(RandomTestUtil.randomBoolean());

		_commerceAccountGroups.add(_persistence.update(commerceAccountGroup));

		return commerceAccountGroup;
	}

	private List<CommerceAccountGroup> _commerceAccountGroups =
		new ArrayList<CommerceAccountGroup>();
	private CommerceAccountGroupPersistence _persistence;
	private ClassLoader _dynamicQueryClassLoader;

}