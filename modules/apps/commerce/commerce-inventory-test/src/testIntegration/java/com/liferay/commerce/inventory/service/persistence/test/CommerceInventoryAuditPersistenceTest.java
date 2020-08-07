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

package com.liferay.commerce.inventory.service.persistence.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.commerce.inventory.exception.NoSuchInventoryAuditException;
import com.liferay.commerce.inventory.model.CommerceInventoryAudit;
import com.liferay.commerce.inventory.service.CommerceInventoryAuditLocalServiceUtil;
import com.liferay.commerce.inventory.service.persistence.CommerceInventoryAuditPersistence;
import com.liferay.commerce.inventory.service.persistence.CommerceInventoryAuditUtil;
import com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQueryFactoryUtil;
import com.liferay.portal.kernel.dao.orm.ProjectionFactoryUtil;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.RestrictionsFactoryUtil;
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
public class CommerceInventoryAuditPersistenceTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(), PersistenceTestRule.INSTANCE,
			new TransactionalTestRule(
				Propagation.REQUIRED,
				"com.liferay.commerce.inventory.service"));

	@Before
	public void setUp() {
		_persistence = CommerceInventoryAuditUtil.getPersistence();

		Class<?> clazz = _persistence.getClass();

		_dynamicQueryClassLoader = clazz.getClassLoader();
	}

	@After
	public void tearDown() throws Exception {
		Iterator<CommerceInventoryAudit> iterator =
			_commerceInventoryAudits.iterator();

		while (iterator.hasNext()) {
			_persistence.remove(iterator.next());

			iterator.remove();
		}
	}

	@Test
	public void testCreate() throws Exception {
		long pk = RandomTestUtil.nextLong();

		CommerceInventoryAudit commerceInventoryAudit = _persistence.create(pk);

		Assert.assertNotNull(commerceInventoryAudit);

		Assert.assertEquals(commerceInventoryAudit.getPrimaryKey(), pk);
	}

	@Test
	public void testRemove() throws Exception {
		CommerceInventoryAudit newCommerceInventoryAudit =
			addCommerceInventoryAudit();

		_persistence.remove(newCommerceInventoryAudit);

		CommerceInventoryAudit existingCommerceInventoryAudit =
			_persistence.fetchByPrimaryKey(
				newCommerceInventoryAudit.getPrimaryKey());

		Assert.assertNull(existingCommerceInventoryAudit);
	}

	@Test
	public void testUpdateNew() throws Exception {
		addCommerceInventoryAudit();
	}

	@Test
	public void testUpdateExisting() throws Exception {
		long pk = RandomTestUtil.nextLong();

		CommerceInventoryAudit newCommerceInventoryAudit = _persistence.create(
			pk);

		newCommerceInventoryAudit.setMvccVersion(RandomTestUtil.nextLong());

		newCommerceInventoryAudit.setCompanyId(RandomTestUtil.nextLong());

		newCommerceInventoryAudit.setUserId(RandomTestUtil.nextLong());

		newCommerceInventoryAudit.setUserName(RandomTestUtil.randomString());

		newCommerceInventoryAudit.setCreateDate(RandomTestUtil.nextDate());

		newCommerceInventoryAudit.setModifiedDate(RandomTestUtil.nextDate());

		newCommerceInventoryAudit.setSku(RandomTestUtil.randomString());

		newCommerceInventoryAudit.setLogType(RandomTestUtil.randomString());

		newCommerceInventoryAudit.setLogTypeSettings(
			RandomTestUtil.randomString());

		newCommerceInventoryAudit.setQuantity(RandomTestUtil.nextInt());

		_commerceInventoryAudits.add(
			_persistence.update(newCommerceInventoryAudit));

		CommerceInventoryAudit existingCommerceInventoryAudit =
			_persistence.findByPrimaryKey(
				newCommerceInventoryAudit.getPrimaryKey());

		Assert.assertEquals(
			existingCommerceInventoryAudit.getMvccVersion(),
			newCommerceInventoryAudit.getMvccVersion());
		Assert.assertEquals(
			existingCommerceInventoryAudit.getCommerceInventoryAuditId(),
			newCommerceInventoryAudit.getCommerceInventoryAuditId());
		Assert.assertEquals(
			existingCommerceInventoryAudit.getCompanyId(),
			newCommerceInventoryAudit.getCompanyId());
		Assert.assertEquals(
			existingCommerceInventoryAudit.getUserId(),
			newCommerceInventoryAudit.getUserId());
		Assert.assertEquals(
			existingCommerceInventoryAudit.getUserName(),
			newCommerceInventoryAudit.getUserName());
		Assert.assertEquals(
			Time.getShortTimestamp(
				existingCommerceInventoryAudit.getCreateDate()),
			Time.getShortTimestamp(newCommerceInventoryAudit.getCreateDate()));
		Assert.assertEquals(
			Time.getShortTimestamp(
				existingCommerceInventoryAudit.getModifiedDate()),
			Time.getShortTimestamp(
				newCommerceInventoryAudit.getModifiedDate()));
		Assert.assertEquals(
			existingCommerceInventoryAudit.getSku(),
			newCommerceInventoryAudit.getSku());
		Assert.assertEquals(
			existingCommerceInventoryAudit.getLogType(),
			newCommerceInventoryAudit.getLogType());
		Assert.assertEquals(
			existingCommerceInventoryAudit.getLogTypeSettings(),
			newCommerceInventoryAudit.getLogTypeSettings());
		Assert.assertEquals(
			existingCommerceInventoryAudit.getQuantity(),
			newCommerceInventoryAudit.getQuantity());
	}

	@Test
	public void testCountByLtCreateDate() throws Exception {
		_persistence.countByLtCreateDate(RandomTestUtil.nextDate());

		_persistence.countByLtCreateDate(RandomTestUtil.nextDate());
	}

	@Test
	public void testCountByC_S() throws Exception {
		_persistence.countByC_S(RandomTestUtil.nextLong(), "");

		_persistence.countByC_S(0L, "null");

		_persistence.countByC_S(0L, (String)null);
	}

	@Test
	public void testFindByPrimaryKeyExisting() throws Exception {
		CommerceInventoryAudit newCommerceInventoryAudit =
			addCommerceInventoryAudit();

		CommerceInventoryAudit existingCommerceInventoryAudit =
			_persistence.findByPrimaryKey(
				newCommerceInventoryAudit.getPrimaryKey());

		Assert.assertEquals(
			existingCommerceInventoryAudit, newCommerceInventoryAudit);
	}

	@Test(expected = NoSuchInventoryAuditException.class)
	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		_persistence.findByPrimaryKey(pk);
	}

	@Test
	public void testFindAll() throws Exception {
		_persistence.findAll(
			QueryUtil.ALL_POS, QueryUtil.ALL_POS, getOrderByComparator());
	}

	protected OrderByComparator<CommerceInventoryAudit> getOrderByComparator() {
		return OrderByComparatorFactoryUtil.create(
			"CIAudit", "mvccVersion", true, "commerceInventoryAuditId", true,
			"companyId", true, "userId", true, "userName", true, "createDate",
			true, "modifiedDate", true, "sku", true, "logType", true,
			"quantity", true);
	}

	@Test
	public void testFetchByPrimaryKeyExisting() throws Exception {
		CommerceInventoryAudit newCommerceInventoryAudit =
			addCommerceInventoryAudit();

		CommerceInventoryAudit existingCommerceInventoryAudit =
			_persistence.fetchByPrimaryKey(
				newCommerceInventoryAudit.getPrimaryKey());

		Assert.assertEquals(
			existingCommerceInventoryAudit, newCommerceInventoryAudit);
	}

	@Test
	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		CommerceInventoryAudit missingCommerceInventoryAudit =
			_persistence.fetchByPrimaryKey(pk);

		Assert.assertNull(missingCommerceInventoryAudit);
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereAllPrimaryKeysExist()
		throws Exception {

		CommerceInventoryAudit newCommerceInventoryAudit1 =
			addCommerceInventoryAudit();
		CommerceInventoryAudit newCommerceInventoryAudit2 =
			addCommerceInventoryAudit();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newCommerceInventoryAudit1.getPrimaryKey());
		primaryKeys.add(newCommerceInventoryAudit2.getPrimaryKey());

		Map<Serializable, CommerceInventoryAudit> commerceInventoryAudits =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(2, commerceInventoryAudits.size());
		Assert.assertEquals(
			newCommerceInventoryAudit1,
			commerceInventoryAudits.get(
				newCommerceInventoryAudit1.getPrimaryKey()));
		Assert.assertEquals(
			newCommerceInventoryAudit2,
			commerceInventoryAudits.get(
				newCommerceInventoryAudit2.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereNoPrimaryKeysExist()
		throws Exception {

		long pk1 = RandomTestUtil.nextLong();

		long pk2 = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(pk1);
		primaryKeys.add(pk2);

		Map<Serializable, CommerceInventoryAudit> commerceInventoryAudits =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(commerceInventoryAudits.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereSomePrimaryKeysExist()
		throws Exception {

		CommerceInventoryAudit newCommerceInventoryAudit =
			addCommerceInventoryAudit();

		long pk = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newCommerceInventoryAudit.getPrimaryKey());
		primaryKeys.add(pk);

		Map<Serializable, CommerceInventoryAudit> commerceInventoryAudits =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, commerceInventoryAudits.size());
		Assert.assertEquals(
			newCommerceInventoryAudit,
			commerceInventoryAudits.get(
				newCommerceInventoryAudit.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithNoPrimaryKeys() throws Exception {
		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		Map<Serializable, CommerceInventoryAudit> commerceInventoryAudits =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(commerceInventoryAudits.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithOnePrimaryKey() throws Exception {
		CommerceInventoryAudit newCommerceInventoryAudit =
			addCommerceInventoryAudit();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newCommerceInventoryAudit.getPrimaryKey());

		Map<Serializable, CommerceInventoryAudit> commerceInventoryAudits =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, commerceInventoryAudits.size());
		Assert.assertEquals(
			newCommerceInventoryAudit,
			commerceInventoryAudits.get(
				newCommerceInventoryAudit.getPrimaryKey()));
	}

	@Test
	public void testActionableDynamicQuery() throws Exception {
		final IntegerWrapper count = new IntegerWrapper();

		ActionableDynamicQuery actionableDynamicQuery =
			CommerceInventoryAuditLocalServiceUtil.getActionableDynamicQuery();

		actionableDynamicQuery.setPerformActionMethod(
			new ActionableDynamicQuery.PerformActionMethod
				<CommerceInventoryAudit>() {

				@Override
				public void performAction(
					CommerceInventoryAudit commerceInventoryAudit) {

					Assert.assertNotNull(commerceInventoryAudit);

					count.increment();
				}

			});

		actionableDynamicQuery.performActions();

		Assert.assertEquals(count.getValue(), _persistence.countAll());
	}

	@Test
	public void testDynamicQueryByPrimaryKeyExisting() throws Exception {
		CommerceInventoryAudit newCommerceInventoryAudit =
			addCommerceInventoryAudit();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			CommerceInventoryAudit.class, _dynamicQueryClassLoader);

		dynamicQuery.add(
			RestrictionsFactoryUtil.eq(
				"commerceInventoryAuditId",
				newCommerceInventoryAudit.getCommerceInventoryAuditId()));

		List<CommerceInventoryAudit> result = _persistence.findWithDynamicQuery(
			dynamicQuery);

		Assert.assertEquals(1, result.size());

		CommerceInventoryAudit existingCommerceInventoryAudit = result.get(0);

		Assert.assertEquals(
			existingCommerceInventoryAudit, newCommerceInventoryAudit);
	}

	@Test
	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			CommerceInventoryAudit.class, _dynamicQueryClassLoader);

		dynamicQuery.add(
			RestrictionsFactoryUtil.eq(
				"commerceInventoryAuditId", RandomTestUtil.nextLong()));

		List<CommerceInventoryAudit> result = _persistence.findWithDynamicQuery(
			dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testDynamicQueryByProjectionExisting() throws Exception {
		CommerceInventoryAudit newCommerceInventoryAudit =
			addCommerceInventoryAudit();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			CommerceInventoryAudit.class, _dynamicQueryClassLoader);

		dynamicQuery.setProjection(
			ProjectionFactoryUtil.property("commerceInventoryAuditId"));

		Object newCommerceInventoryAuditId =
			newCommerceInventoryAudit.getCommerceInventoryAuditId();

		dynamicQuery.add(
			RestrictionsFactoryUtil.in(
				"commerceInventoryAuditId",
				new Object[] {newCommerceInventoryAuditId}));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		Object existingCommerceInventoryAuditId = result.get(0);

		Assert.assertEquals(
			existingCommerceInventoryAuditId, newCommerceInventoryAuditId);
	}

	@Test
	public void testDynamicQueryByProjectionMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			CommerceInventoryAudit.class, _dynamicQueryClassLoader);

		dynamicQuery.setProjection(
			ProjectionFactoryUtil.property("commerceInventoryAuditId"));

		dynamicQuery.add(
			RestrictionsFactoryUtil.in(
				"commerceInventoryAuditId",
				new Object[] {RandomTestUtil.nextLong()}));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	protected CommerceInventoryAudit addCommerceInventoryAudit()
		throws Exception {

		long pk = RandomTestUtil.nextLong();

		CommerceInventoryAudit commerceInventoryAudit = _persistence.create(pk);

		commerceInventoryAudit.setMvccVersion(RandomTestUtil.nextLong());

		commerceInventoryAudit.setCompanyId(RandomTestUtil.nextLong());

		commerceInventoryAudit.setUserId(RandomTestUtil.nextLong());

		commerceInventoryAudit.setUserName(RandomTestUtil.randomString());

		commerceInventoryAudit.setCreateDate(RandomTestUtil.nextDate());

		commerceInventoryAudit.setModifiedDate(RandomTestUtil.nextDate());

		commerceInventoryAudit.setSku(RandomTestUtil.randomString());

		commerceInventoryAudit.setLogType(RandomTestUtil.randomString());

		commerceInventoryAudit.setLogTypeSettings(
			RandomTestUtil.randomString());

		commerceInventoryAudit.setQuantity(RandomTestUtil.nextInt());

		_commerceInventoryAudits.add(
			_persistence.update(commerceInventoryAudit));

		return commerceInventoryAudit;
	}

	private List<CommerceInventoryAudit> _commerceInventoryAudits =
		new ArrayList<CommerceInventoryAudit>();
	private CommerceInventoryAuditPersistence _persistence;
	private ClassLoader _dynamicQueryClassLoader;

}