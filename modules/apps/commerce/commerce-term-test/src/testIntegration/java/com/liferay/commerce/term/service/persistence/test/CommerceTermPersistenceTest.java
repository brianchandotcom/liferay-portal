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

package com.liferay.commerce.term.service.persistence.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.commerce.term.exception.NoSuchTermException;
import com.liferay.commerce.term.model.CommerceTerm;
import com.liferay.commerce.term.service.CommerceTermLocalServiceUtil;
import com.liferay.commerce.term.service.persistence.CommerceTermPersistence;
import com.liferay.commerce.term.service.persistence.CommerceTermUtil;
import com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQueryFactoryUtil;
import com.liferay.portal.kernel.dao.orm.ProjectionFactoryUtil;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.RestrictionsFactoryUtil;
import com.liferay.portal.kernel.dao.orm.Session;
import com.liferay.portal.kernel.test.AssertUtils;
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
public class CommerceTermPersistenceTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(), PersistenceTestRule.INSTANCE,
			new TransactionalTestRule(
				Propagation.REQUIRED, "com.liferay.commerce.term.service"));

	@Before
	public void setUp() {
		_persistence = CommerceTermUtil.getPersistence();

		Class<?> clazz = _persistence.getClass();

		_dynamicQueryClassLoader = clazz.getClassLoader();
	}

	@After
	public void tearDown() throws Exception {
		Iterator<CommerceTerm> iterator = _commerceTerms.iterator();

		while (iterator.hasNext()) {
			_persistence.remove(iterator.next());

			iterator.remove();
		}
	}

	@Test
	public void testCreate() throws Exception {
		long pk = RandomTestUtil.nextLong();

		CommerceTerm commerceTerm = _persistence.create(pk);

		Assert.assertNotNull(commerceTerm);

		Assert.assertEquals(commerceTerm.getPrimaryKey(), pk);
	}

	@Test
	public void testRemove() throws Exception {
		CommerceTerm newCommerceTerm = addCommerceTerm();

		_persistence.remove(newCommerceTerm);

		CommerceTerm existingCommerceTerm = _persistence.fetchByPrimaryKey(
			newCommerceTerm.getPrimaryKey());

		Assert.assertNull(existingCommerceTerm);
	}

	@Test
	public void testUpdateNew() throws Exception {
		addCommerceTerm();
	}

	@Test
	public void testUpdateExisting() throws Exception {
		long pk = RandomTestUtil.nextLong();

		CommerceTerm newCommerceTerm = _persistence.create(pk);

		newCommerceTerm.setMvccVersion(RandomTestUtil.nextLong());

		newCommerceTerm.setExternalReferenceCode(RandomTestUtil.randomString());

		newCommerceTerm.setCompanyId(RandomTestUtil.nextLong());

		newCommerceTerm.setUserId(RandomTestUtil.nextLong());

		newCommerceTerm.setUserName(RandomTestUtil.randomString());

		newCommerceTerm.setCreateDate(RandomTestUtil.nextDate());

		newCommerceTerm.setModifiedDate(RandomTestUtil.nextDate());

		newCommerceTerm.setActive(RandomTestUtil.randomBoolean());

		newCommerceTerm.setDescription(RandomTestUtil.randomString());

		newCommerceTerm.setDisplayDate(RandomTestUtil.nextDate());

		newCommerceTerm.setExpirationDate(RandomTestUtil.nextDate());

		newCommerceTerm.setLabel(RandomTestUtil.randomString());

		newCommerceTerm.setName(RandomTestUtil.randomString());

		newCommerceTerm.setPriority(RandomTestUtil.nextDouble());

		newCommerceTerm.setType(RandomTestUtil.randomString());

		newCommerceTerm.setTypeSettings(RandomTestUtil.randomString());

		newCommerceTerm.setLastPublishDate(RandomTestUtil.nextDate());

		newCommerceTerm.setStatus(RandomTestUtil.nextInt());

		newCommerceTerm.setStatusByUserId(RandomTestUtil.nextLong());

		newCommerceTerm.setStatusByUserName(RandomTestUtil.randomString());

		newCommerceTerm.setStatusDate(RandomTestUtil.nextDate());

		_commerceTerms.add(_persistence.update(newCommerceTerm));

		CommerceTerm existingCommerceTerm = _persistence.findByPrimaryKey(
			newCommerceTerm.getPrimaryKey());

		Assert.assertEquals(
			existingCommerceTerm.getMvccVersion(),
			newCommerceTerm.getMvccVersion());
		Assert.assertEquals(
			existingCommerceTerm.getExternalReferenceCode(),
			newCommerceTerm.getExternalReferenceCode());
		Assert.assertEquals(
			existingCommerceTerm.getCommerceTermId(),
			newCommerceTerm.getCommerceTermId());
		Assert.assertEquals(
			existingCommerceTerm.getCompanyId(),
			newCommerceTerm.getCompanyId());
		Assert.assertEquals(
			existingCommerceTerm.getUserId(), newCommerceTerm.getUserId());
		Assert.assertEquals(
			existingCommerceTerm.getUserName(), newCommerceTerm.getUserName());
		Assert.assertEquals(
			Time.getShortTimestamp(existingCommerceTerm.getCreateDate()),
			Time.getShortTimestamp(newCommerceTerm.getCreateDate()));
		Assert.assertEquals(
			Time.getShortTimestamp(existingCommerceTerm.getModifiedDate()),
			Time.getShortTimestamp(newCommerceTerm.getModifiedDate()));
		Assert.assertEquals(
			existingCommerceTerm.isActive(), newCommerceTerm.isActive());
		Assert.assertEquals(
			existingCommerceTerm.getDescription(),
			newCommerceTerm.getDescription());
		Assert.assertEquals(
			Time.getShortTimestamp(existingCommerceTerm.getDisplayDate()),
			Time.getShortTimestamp(newCommerceTerm.getDisplayDate()));
		Assert.assertEquals(
			Time.getShortTimestamp(existingCommerceTerm.getExpirationDate()),
			Time.getShortTimestamp(newCommerceTerm.getExpirationDate()));
		Assert.assertEquals(
			existingCommerceTerm.getLabel(), newCommerceTerm.getLabel());
		Assert.assertEquals(
			existingCommerceTerm.getName(), newCommerceTerm.getName());
		AssertUtils.assertEquals(
			existingCommerceTerm.getPriority(), newCommerceTerm.getPriority());
		Assert.assertEquals(
			existingCommerceTerm.getType(), newCommerceTerm.getType());
		Assert.assertEquals(
			existingCommerceTerm.getTypeSettings(),
			newCommerceTerm.getTypeSettings());
		Assert.assertEquals(
			Time.getShortTimestamp(existingCommerceTerm.getLastPublishDate()),
			Time.getShortTimestamp(newCommerceTerm.getLastPublishDate()));
		Assert.assertEquals(
			existingCommerceTerm.getStatus(), newCommerceTerm.getStatus());
		Assert.assertEquals(
			existingCommerceTerm.getStatusByUserId(),
			newCommerceTerm.getStatusByUserId());
		Assert.assertEquals(
			existingCommerceTerm.getStatusByUserName(),
			newCommerceTerm.getStatusByUserName());
		Assert.assertEquals(
			Time.getShortTimestamp(existingCommerceTerm.getStatusDate()),
			Time.getShortTimestamp(newCommerceTerm.getStatusDate()));
	}

	@Test
	public void testCountByC_A() throws Exception {
		_persistence.countByC_A(
			RandomTestUtil.nextLong(), RandomTestUtil.randomBoolean());

		_persistence.countByC_A(0L, RandomTestUtil.randomBoolean());
	}

	@Test
	public void testCountByC_LikeType() throws Exception {
		_persistence.countByC_LikeType(RandomTestUtil.nextLong(), "");

		_persistence.countByC_LikeType(0L, "null");

		_persistence.countByC_LikeType(0L, (String)null);
	}

	@Test
	public void testCountByLtD_S() throws Exception {
		_persistence.countByLtD_S(
			RandomTestUtil.nextDate(), RandomTestUtil.nextInt());

		_persistence.countByLtD_S(RandomTestUtil.nextDate(), 0);
	}

	@Test
	public void testCountByLtE_S() throws Exception {
		_persistence.countByLtE_S(
			RandomTestUtil.nextDate(), RandomTestUtil.nextInt());

		_persistence.countByLtE_S(RandomTestUtil.nextDate(), 0);
	}

	@Test
	public void testCountByC_A_LikeType() throws Exception {
		_persistence.countByC_A_LikeType(
			RandomTestUtil.nextLong(), RandomTestUtil.randomBoolean(), "");

		_persistence.countByC_A_LikeType(
			0L, RandomTestUtil.randomBoolean(), "null");

		_persistence.countByC_A_LikeType(
			0L, RandomTestUtil.randomBoolean(), (String)null);
	}

	@Test
	public void testCountByC_ERC() throws Exception {
		_persistence.countByC_ERC(RandomTestUtil.nextLong(), "");

		_persistence.countByC_ERC(0L, "null");

		_persistence.countByC_ERC(0L, (String)null);
	}

	@Test
	public void testFindByPrimaryKeyExisting() throws Exception {
		CommerceTerm newCommerceTerm = addCommerceTerm();

		CommerceTerm existingCommerceTerm = _persistence.findByPrimaryKey(
			newCommerceTerm.getPrimaryKey());

		Assert.assertEquals(existingCommerceTerm, newCommerceTerm);
	}

	@Test(expected = NoSuchTermException.class)
	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		_persistence.findByPrimaryKey(pk);
	}

	@Test
	public void testFindAll() throws Exception {
		_persistence.findAll(
			QueryUtil.ALL_POS, QueryUtil.ALL_POS, getOrderByComparator());
	}

	protected OrderByComparator<CommerceTerm> getOrderByComparator() {
		return OrderByComparatorFactoryUtil.create(
			"CommerceTerm", "mvccVersion", true, "externalReferenceCode", true,
			"commerceTermId", true, "companyId", true, "userId", true,
			"userName", true, "createDate", true, "modifiedDate", true,
			"active", true, "description", true, "displayDate", true,
			"expirationDate", true, "label", true, "name", true, "priority",
			true, "type", true, "typeSettings", true, "lastPublishDate", true,
			"status", true, "statusByUserId", true, "statusByUserName", true,
			"statusDate", true);
	}

	@Test
	public void testFetchByPrimaryKeyExisting() throws Exception {
		CommerceTerm newCommerceTerm = addCommerceTerm();

		CommerceTerm existingCommerceTerm = _persistence.fetchByPrimaryKey(
			newCommerceTerm.getPrimaryKey());

		Assert.assertEquals(existingCommerceTerm, newCommerceTerm);
	}

	@Test
	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		CommerceTerm missingCommerceTerm = _persistence.fetchByPrimaryKey(pk);

		Assert.assertNull(missingCommerceTerm);
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereAllPrimaryKeysExist()
		throws Exception {

		CommerceTerm newCommerceTerm1 = addCommerceTerm();
		CommerceTerm newCommerceTerm2 = addCommerceTerm();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newCommerceTerm1.getPrimaryKey());
		primaryKeys.add(newCommerceTerm2.getPrimaryKey());

		Map<Serializable, CommerceTerm> commerceTerms =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(2, commerceTerms.size());
		Assert.assertEquals(
			newCommerceTerm1,
			commerceTerms.get(newCommerceTerm1.getPrimaryKey()));
		Assert.assertEquals(
			newCommerceTerm2,
			commerceTerms.get(newCommerceTerm2.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereNoPrimaryKeysExist()
		throws Exception {

		long pk1 = RandomTestUtil.nextLong();

		long pk2 = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(pk1);
		primaryKeys.add(pk2);

		Map<Serializable, CommerceTerm> commerceTerms =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(commerceTerms.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereSomePrimaryKeysExist()
		throws Exception {

		CommerceTerm newCommerceTerm = addCommerceTerm();

		long pk = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newCommerceTerm.getPrimaryKey());
		primaryKeys.add(pk);

		Map<Serializable, CommerceTerm> commerceTerms =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, commerceTerms.size());
		Assert.assertEquals(
			newCommerceTerm,
			commerceTerms.get(newCommerceTerm.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithNoPrimaryKeys() throws Exception {
		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		Map<Serializable, CommerceTerm> commerceTerms =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(commerceTerms.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithOnePrimaryKey() throws Exception {
		CommerceTerm newCommerceTerm = addCommerceTerm();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newCommerceTerm.getPrimaryKey());

		Map<Serializable, CommerceTerm> commerceTerms =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, commerceTerms.size());
		Assert.assertEquals(
			newCommerceTerm,
			commerceTerms.get(newCommerceTerm.getPrimaryKey()));
	}

	@Test
	public void testActionableDynamicQuery() throws Exception {
		final IntegerWrapper count = new IntegerWrapper();

		ActionableDynamicQuery actionableDynamicQuery =
			CommerceTermLocalServiceUtil.getActionableDynamicQuery();

		actionableDynamicQuery.setPerformActionMethod(
			new ActionableDynamicQuery.PerformActionMethod<CommerceTerm>() {

				@Override
				public void performAction(CommerceTerm commerceTerm) {
					Assert.assertNotNull(commerceTerm);

					count.increment();
				}

			});

		actionableDynamicQuery.performActions();

		Assert.assertEquals(count.getValue(), _persistence.countAll());
	}

	@Test
	public void testDynamicQueryByPrimaryKeyExisting() throws Exception {
		CommerceTerm newCommerceTerm = addCommerceTerm();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			CommerceTerm.class, _dynamicQueryClassLoader);

		dynamicQuery.add(
			RestrictionsFactoryUtil.eq(
				"commerceTermId", newCommerceTerm.getCommerceTermId()));

		List<CommerceTerm> result = _persistence.findWithDynamicQuery(
			dynamicQuery);

		Assert.assertEquals(1, result.size());

		CommerceTerm existingCommerceTerm = result.get(0);

		Assert.assertEquals(existingCommerceTerm, newCommerceTerm);
	}

	@Test
	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			CommerceTerm.class, _dynamicQueryClassLoader);

		dynamicQuery.add(
			RestrictionsFactoryUtil.eq(
				"commerceTermId", RandomTestUtil.nextLong()));

		List<CommerceTerm> result = _persistence.findWithDynamicQuery(
			dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testDynamicQueryByProjectionExisting() throws Exception {
		CommerceTerm newCommerceTerm = addCommerceTerm();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			CommerceTerm.class, _dynamicQueryClassLoader);

		dynamicQuery.setProjection(
			ProjectionFactoryUtil.property("commerceTermId"));

		Object newCommerceTermId = newCommerceTerm.getCommerceTermId();

		dynamicQuery.add(
			RestrictionsFactoryUtil.in(
				"commerceTermId", new Object[] {newCommerceTermId}));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		Object existingCommerceTermId = result.get(0);

		Assert.assertEquals(existingCommerceTermId, newCommerceTermId);
	}

	@Test
	public void testDynamicQueryByProjectionMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			CommerceTerm.class, _dynamicQueryClassLoader);

		dynamicQuery.setProjection(
			ProjectionFactoryUtil.property("commerceTermId"));

		dynamicQuery.add(
			RestrictionsFactoryUtil.in(
				"commerceTermId", new Object[] {RandomTestUtil.nextLong()}));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testResetOriginalValues() throws Exception {
		CommerceTerm newCommerceTerm = addCommerceTerm();

		_persistence.clearCache();

		_assertOriginalValues(
			_persistence.findByPrimaryKey(newCommerceTerm.getPrimaryKey()));
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

		CommerceTerm newCommerceTerm = addCommerceTerm();

		if (clearSession) {
			Session session = _persistence.openSession();

			session.flush();

			session.clear();
		}

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			CommerceTerm.class, _dynamicQueryClassLoader);

		dynamicQuery.add(
			RestrictionsFactoryUtil.eq(
				"commerceTermId", newCommerceTerm.getCommerceTermId()));

		List<CommerceTerm> result = _persistence.findWithDynamicQuery(
			dynamicQuery);

		_assertOriginalValues(result.get(0));
	}

	private void _assertOriginalValues(CommerceTerm commerceTerm) {
		Assert.assertEquals(
			Long.valueOf(commerceTerm.getCompanyId()),
			ReflectionTestUtil.<Long>invoke(
				commerceTerm, "getColumnOriginalValue",
				new Class<?>[] {String.class}, "companyId"));
		Assert.assertEquals(
			commerceTerm.getExternalReferenceCode(),
			ReflectionTestUtil.invoke(
				commerceTerm, "getColumnOriginalValue",
				new Class<?>[] {String.class}, "externalReferenceCode"));
	}

	protected CommerceTerm addCommerceTerm() throws Exception {
		long pk = RandomTestUtil.nextLong();

		CommerceTerm commerceTerm = _persistence.create(pk);

		commerceTerm.setMvccVersion(RandomTestUtil.nextLong());

		commerceTerm.setExternalReferenceCode(RandomTestUtil.randomString());

		commerceTerm.setCompanyId(RandomTestUtil.nextLong());

		commerceTerm.setUserId(RandomTestUtil.nextLong());

		commerceTerm.setUserName(RandomTestUtil.randomString());

		commerceTerm.setCreateDate(RandomTestUtil.nextDate());

		commerceTerm.setModifiedDate(RandomTestUtil.nextDate());

		commerceTerm.setActive(RandomTestUtil.randomBoolean());

		commerceTerm.setDescription(RandomTestUtil.randomString());

		commerceTerm.setDisplayDate(RandomTestUtil.nextDate());

		commerceTerm.setExpirationDate(RandomTestUtil.nextDate());

		commerceTerm.setLabel(RandomTestUtil.randomString());

		commerceTerm.setName(RandomTestUtil.randomString());

		commerceTerm.setPriority(RandomTestUtil.nextDouble());

		commerceTerm.setType(RandomTestUtil.randomString());

		commerceTerm.setTypeSettings(RandomTestUtil.randomString());

		commerceTerm.setLastPublishDate(RandomTestUtil.nextDate());

		commerceTerm.setStatus(RandomTestUtil.nextInt());

		commerceTerm.setStatusByUserId(RandomTestUtil.nextLong());

		commerceTerm.setStatusByUserName(RandomTestUtil.randomString());

		commerceTerm.setStatusDate(RandomTestUtil.nextDate());

		_commerceTerms.add(_persistence.update(commerceTerm));

		return commerceTerm;
	}

	private List<CommerceTerm> _commerceTerms = new ArrayList<CommerceTerm>();
	private CommerceTermPersistence _persistence;
	private ClassLoader _dynamicQueryClassLoader;

}