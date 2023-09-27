/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.payment.service.persistence.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.commerce.payment.exception.NoSuchPaymentEntryException;
import com.liferay.commerce.payment.model.CommercePaymentEntry;
import com.liferay.commerce.payment.service.CommercePaymentEntryLocalServiceUtil;
import com.liferay.commerce.payment.service.persistence.CommercePaymentEntryPersistence;
import com.liferay.commerce.payment.service.persistence.CommercePaymentEntryUtil;
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

import java.math.BigDecimal;

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
public class CommercePaymentEntryPersistenceTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(), PersistenceTestRule.INSTANCE,
			new TransactionalTestRule(
				Propagation.REQUIRED, "com.liferay.commerce.payment.service"));

	@Before
	public void setUp() {
		_persistence = CommercePaymentEntryUtil.getPersistence();

		Class<?> clazz = _persistence.getClass();

		_dynamicQueryClassLoader = clazz.getClassLoader();
	}

	@After
	public void tearDown() throws Exception {
		Iterator<CommercePaymentEntry> iterator =
			_commercePaymentEntries.iterator();

		while (iterator.hasNext()) {
			_persistence.remove(iterator.next());

			iterator.remove();
		}
	}

	@Test
	public void testCreate() throws Exception {
		long pk = RandomTestUtil.nextLong();

		CommercePaymentEntry commercePaymentEntry = _persistence.create(pk);

		Assert.assertNotNull(commercePaymentEntry);

		Assert.assertEquals(commercePaymentEntry.getPrimaryKey(), pk);
	}

	@Test
	public void testRemove() throws Exception {
		CommercePaymentEntry newCommercePaymentEntry =
			addCommercePaymentEntry();

		_persistence.remove(newCommercePaymentEntry);

		CommercePaymentEntry existingCommercePaymentEntry =
			_persistence.fetchByPrimaryKey(
				newCommercePaymentEntry.getPrimaryKey());

		Assert.assertNull(existingCommercePaymentEntry);
	}

	@Test
	public void testUpdateNew() throws Exception {
		addCommercePaymentEntry();
	}

	@Test
	public void testUpdateExisting() throws Exception {
		long pk = RandomTestUtil.nextLong();

		CommercePaymentEntry newCommercePaymentEntry = _persistence.create(pk);

		newCommercePaymentEntry.setMvccVersion(RandomTestUtil.nextLong());

		newCommercePaymentEntry.setCompanyId(RandomTestUtil.nextLong());

		newCommercePaymentEntry.setUserId(RandomTestUtil.nextLong());

		newCommercePaymentEntry.setUserName(RandomTestUtil.randomString());

		newCommercePaymentEntry.setCreateDate(RandomTestUtil.nextDate());

		newCommercePaymentEntry.setModifiedDate(RandomTestUtil.nextDate());

		newCommercePaymentEntry.setClassNameId(RandomTestUtil.nextLong());

		newCommercePaymentEntry.setClassPK(RandomTestUtil.nextLong());

		newCommercePaymentEntry.setCommerceChannelId(RandomTestUtil.nextLong());

		newCommercePaymentEntry.setAmount(
			new BigDecimal(RandomTestUtil.nextDouble()));

		newCommercePaymentEntry.setCallbackURL(RandomTestUtil.randomString());

		newCommercePaymentEntry.setCurrencyCode(RandomTestUtil.randomString());

		newCommercePaymentEntry.setPaymentIntegrationKey(
			RandomTestUtil.randomString());

		newCommercePaymentEntry.setPaymentIntegrationType(
			RandomTestUtil.nextInt());

		newCommercePaymentEntry.setPaymentStatus(RandomTestUtil.nextInt());

		newCommercePaymentEntry.setRedirectURL(RandomTestUtil.randomString());

		newCommercePaymentEntry.setTransactionCode(
			RandomTestUtil.randomString());

		_commercePaymentEntries.add(
			_persistence.update(newCommercePaymentEntry));

		CommercePaymentEntry existingCommercePaymentEntry =
			_persistence.findByPrimaryKey(
				newCommercePaymentEntry.getPrimaryKey());

		Assert.assertEquals(
			existingCommercePaymentEntry.getMvccVersion(),
			newCommercePaymentEntry.getMvccVersion());
		Assert.assertEquals(
			existingCommercePaymentEntry.getCommercePaymentEntryId(),
			newCommercePaymentEntry.getCommercePaymentEntryId());
		Assert.assertEquals(
			existingCommercePaymentEntry.getCompanyId(),
			newCommercePaymentEntry.getCompanyId());
		Assert.assertEquals(
			existingCommercePaymentEntry.getUserId(),
			newCommercePaymentEntry.getUserId());
		Assert.assertEquals(
			existingCommercePaymentEntry.getUserName(),
			newCommercePaymentEntry.getUserName());
		Assert.assertEquals(
			Time.getShortTimestamp(
				existingCommercePaymentEntry.getCreateDate()),
			Time.getShortTimestamp(newCommercePaymentEntry.getCreateDate()));
		Assert.assertEquals(
			Time.getShortTimestamp(
				existingCommercePaymentEntry.getModifiedDate()),
			Time.getShortTimestamp(newCommercePaymentEntry.getModifiedDate()));
		Assert.assertEquals(
			existingCommercePaymentEntry.getClassNameId(),
			newCommercePaymentEntry.getClassNameId());
		Assert.assertEquals(
			existingCommercePaymentEntry.getClassPK(),
			newCommercePaymentEntry.getClassPK());
		Assert.assertEquals(
			existingCommercePaymentEntry.getCommerceChannelId(),
			newCommercePaymentEntry.getCommerceChannelId());
		Assert.assertEquals(
			existingCommercePaymentEntry.getAmount(),
			newCommercePaymentEntry.getAmount());
		Assert.assertEquals(
			existingCommercePaymentEntry.getCallbackURL(),
			newCommercePaymentEntry.getCallbackURL());
		Assert.assertEquals(
			existingCommercePaymentEntry.getCurrencyCode(),
			newCommercePaymentEntry.getCurrencyCode());
		Assert.assertEquals(
			existingCommercePaymentEntry.getPaymentIntegrationKey(),
			newCommercePaymentEntry.getPaymentIntegrationKey());
		Assert.assertEquals(
			existingCommercePaymentEntry.getPaymentIntegrationType(),
			newCommercePaymentEntry.getPaymentIntegrationType());
		Assert.assertEquals(
			existingCommercePaymentEntry.getPaymentStatus(),
			newCommercePaymentEntry.getPaymentStatus());
		Assert.assertEquals(
			existingCommercePaymentEntry.getRedirectURL(),
			newCommercePaymentEntry.getRedirectURL());
		Assert.assertEquals(
			existingCommercePaymentEntry.getTransactionCode(),
			newCommercePaymentEntry.getTransactionCode());
	}

	@Test
	public void testCountByCompanyId() throws Exception {
		_persistence.countByCompanyId(RandomTestUtil.nextLong());

		_persistence.countByCompanyId(0L);
	}

	@Test
	public void testCountByC_C_C() throws Exception {
		_persistence.countByC_C_C(
			RandomTestUtil.nextLong(), RandomTestUtil.nextLong(),
			RandomTestUtil.nextLong());

		_persistence.countByC_C_C(0L, 0L, 0L);
	}

	@Test
	public void testFindByPrimaryKeyExisting() throws Exception {
		CommercePaymentEntry newCommercePaymentEntry =
			addCommercePaymentEntry();

		CommercePaymentEntry existingCommercePaymentEntry =
			_persistence.findByPrimaryKey(
				newCommercePaymentEntry.getPrimaryKey());

		Assert.assertEquals(
			existingCommercePaymentEntry, newCommercePaymentEntry);
	}

	@Test(expected = NoSuchPaymentEntryException.class)
	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		_persistence.findByPrimaryKey(pk);
	}

	@Test
	public void testFindAll() throws Exception {
		_persistence.findAll(
			QueryUtil.ALL_POS, QueryUtil.ALL_POS, getOrderByComparator());
	}

	protected OrderByComparator<CommercePaymentEntry> getOrderByComparator() {
		return OrderByComparatorFactoryUtil.create(
			"CommercePaymentEntry", "mvccVersion", true,
			"commercePaymentEntryId", true, "companyId", true, "userId", true,
			"userName", true, "createDate", true, "modifiedDate", true,
			"classNameId", true, "classPK", true, "commerceChannelId", true,
			"amount", true, "currencyCode", true, "paymentIntegrationKey", true,
			"paymentIntegrationType", true, "paymentStatus", true,
			"transactionCode", true);
	}

	@Test
	public void testFetchByPrimaryKeyExisting() throws Exception {
		CommercePaymentEntry newCommercePaymentEntry =
			addCommercePaymentEntry();

		CommercePaymentEntry existingCommercePaymentEntry =
			_persistence.fetchByPrimaryKey(
				newCommercePaymentEntry.getPrimaryKey());

		Assert.assertEquals(
			existingCommercePaymentEntry, newCommercePaymentEntry);
	}

	@Test
	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		CommercePaymentEntry missingCommercePaymentEntry =
			_persistence.fetchByPrimaryKey(pk);

		Assert.assertNull(missingCommercePaymentEntry);
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereAllPrimaryKeysExist()
		throws Exception {

		CommercePaymentEntry newCommercePaymentEntry1 =
			addCommercePaymentEntry();
		CommercePaymentEntry newCommercePaymentEntry2 =
			addCommercePaymentEntry();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newCommercePaymentEntry1.getPrimaryKey());
		primaryKeys.add(newCommercePaymentEntry2.getPrimaryKey());

		Map<Serializable, CommercePaymentEntry> commercePaymentEntries =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(2, commercePaymentEntries.size());
		Assert.assertEquals(
			newCommercePaymentEntry1,
			commercePaymentEntries.get(
				newCommercePaymentEntry1.getPrimaryKey()));
		Assert.assertEquals(
			newCommercePaymentEntry2,
			commercePaymentEntries.get(
				newCommercePaymentEntry2.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereNoPrimaryKeysExist()
		throws Exception {

		long pk1 = RandomTestUtil.nextLong();

		long pk2 = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(pk1);
		primaryKeys.add(pk2);

		Map<Serializable, CommercePaymentEntry> commercePaymentEntries =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(commercePaymentEntries.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereSomePrimaryKeysExist()
		throws Exception {

		CommercePaymentEntry newCommercePaymentEntry =
			addCommercePaymentEntry();

		long pk = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newCommercePaymentEntry.getPrimaryKey());
		primaryKeys.add(pk);

		Map<Serializable, CommercePaymentEntry> commercePaymentEntries =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, commercePaymentEntries.size());
		Assert.assertEquals(
			newCommercePaymentEntry,
			commercePaymentEntries.get(
				newCommercePaymentEntry.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithNoPrimaryKeys() throws Exception {
		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		Map<Serializable, CommercePaymentEntry> commercePaymentEntries =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(commercePaymentEntries.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithOnePrimaryKey() throws Exception {
		CommercePaymentEntry newCommercePaymentEntry =
			addCommercePaymentEntry();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newCommercePaymentEntry.getPrimaryKey());

		Map<Serializable, CommercePaymentEntry> commercePaymentEntries =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, commercePaymentEntries.size());
		Assert.assertEquals(
			newCommercePaymentEntry,
			commercePaymentEntries.get(
				newCommercePaymentEntry.getPrimaryKey()));
	}

	@Test
	public void testActionableDynamicQuery() throws Exception {
		final IntegerWrapper count = new IntegerWrapper();

		ActionableDynamicQuery actionableDynamicQuery =
			CommercePaymentEntryLocalServiceUtil.getActionableDynamicQuery();

		actionableDynamicQuery.setPerformActionMethod(
			new ActionableDynamicQuery.PerformActionMethod
				<CommercePaymentEntry>() {

				@Override
				public void performAction(
					CommercePaymentEntry commercePaymentEntry) {

					Assert.assertNotNull(commercePaymentEntry);

					count.increment();
				}

			});

		actionableDynamicQuery.performActions();

		Assert.assertEquals(count.getValue(), _persistence.countAll());
	}

	@Test
	public void testDynamicQueryByPrimaryKeyExisting() throws Exception {
		CommercePaymentEntry newCommercePaymentEntry =
			addCommercePaymentEntry();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			CommercePaymentEntry.class, _dynamicQueryClassLoader);

		dynamicQuery.add(
			RestrictionsFactoryUtil.eq(
				"commercePaymentEntryId",
				newCommercePaymentEntry.getCommercePaymentEntryId()));

		List<CommercePaymentEntry> result = _persistence.findWithDynamicQuery(
			dynamicQuery);

		Assert.assertEquals(1, result.size());

		CommercePaymentEntry existingCommercePaymentEntry = result.get(0);

		Assert.assertEquals(
			existingCommercePaymentEntry, newCommercePaymentEntry);
	}

	@Test
	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			CommercePaymentEntry.class, _dynamicQueryClassLoader);

		dynamicQuery.add(
			RestrictionsFactoryUtil.eq(
				"commercePaymentEntryId", RandomTestUtil.nextLong()));

		List<CommercePaymentEntry> result = _persistence.findWithDynamicQuery(
			dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testDynamicQueryByProjectionExisting() throws Exception {
		CommercePaymentEntry newCommercePaymentEntry =
			addCommercePaymentEntry();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			CommercePaymentEntry.class, _dynamicQueryClassLoader);

		dynamicQuery.setProjection(
			ProjectionFactoryUtil.property("commercePaymentEntryId"));

		Object newCommercePaymentEntryId =
			newCommercePaymentEntry.getCommercePaymentEntryId();

		dynamicQuery.add(
			RestrictionsFactoryUtil.in(
				"commercePaymentEntryId",
				new Object[] {newCommercePaymentEntryId}));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		Object existingCommercePaymentEntryId = result.get(0);

		Assert.assertEquals(
			existingCommercePaymentEntryId, newCommercePaymentEntryId);
	}

	@Test
	public void testDynamicQueryByProjectionMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			CommercePaymentEntry.class, _dynamicQueryClassLoader);

		dynamicQuery.setProjection(
			ProjectionFactoryUtil.property("commercePaymentEntryId"));

		dynamicQuery.add(
			RestrictionsFactoryUtil.in(
				"commercePaymentEntryId",
				new Object[] {RandomTestUtil.nextLong()}));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	protected CommercePaymentEntry addCommercePaymentEntry() throws Exception {
		long pk = RandomTestUtil.nextLong();

		CommercePaymentEntry commercePaymentEntry = _persistence.create(pk);

		commercePaymentEntry.setMvccVersion(RandomTestUtil.nextLong());

		commercePaymentEntry.setCompanyId(RandomTestUtil.nextLong());

		commercePaymentEntry.setUserId(RandomTestUtil.nextLong());

		commercePaymentEntry.setUserName(RandomTestUtil.randomString());

		commercePaymentEntry.setCreateDate(RandomTestUtil.nextDate());

		commercePaymentEntry.setModifiedDate(RandomTestUtil.nextDate());

		commercePaymentEntry.setClassNameId(RandomTestUtil.nextLong());

		commercePaymentEntry.setClassPK(RandomTestUtil.nextLong());

		commercePaymentEntry.setCommerceChannelId(RandomTestUtil.nextLong());

		commercePaymentEntry.setAmount(
			new BigDecimal(RandomTestUtil.nextDouble()));

		commercePaymentEntry.setCallbackURL(RandomTestUtil.randomString());

		commercePaymentEntry.setCurrencyCode(RandomTestUtil.randomString());

		commercePaymentEntry.setPaymentIntegrationKey(
			RandomTestUtil.randomString());

		commercePaymentEntry.setPaymentIntegrationType(
			RandomTestUtil.nextInt());

		commercePaymentEntry.setPaymentStatus(RandomTestUtil.nextInt());

		commercePaymentEntry.setRedirectURL(RandomTestUtil.randomString());

		commercePaymentEntry.setTransactionCode(RandomTestUtil.randomString());

		_commercePaymentEntries.add(_persistence.update(commercePaymentEntry));

		return commercePaymentEntry;
	}

	private List<CommercePaymentEntry> _commercePaymentEntries =
		new ArrayList<CommercePaymentEntry>();
	private CommercePaymentEntryPersistence _persistence;
	private ClassLoader _dynamicQueryClassLoader;

}