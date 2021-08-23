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

package com.liferay.custom.elements.service.persistence.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.custom.elements.exception.NoSuchPortletDescriptorException;
import com.liferay.custom.elements.model.CustomElementsPortletDescriptor;
import com.liferay.custom.elements.service.CustomElementsPortletDescriptorLocalServiceUtil;
import com.liferay.custom.elements.service.persistence.CustomElementsPortletDescriptorPersistence;
import com.liferay.custom.elements.service.persistence.CustomElementsPortletDescriptorUtil;
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
public class CustomElementsPortletDescriptorPersistenceTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(), PersistenceTestRule.INSTANCE,
			new TransactionalTestRule(
				Propagation.REQUIRED, "com.liferay.custom.elements.service"));

	@Before
	public void setUp() {
		_persistence = CustomElementsPortletDescriptorUtil.getPersistence();

		Class<?> clazz = _persistence.getClass();

		_dynamicQueryClassLoader = clazz.getClassLoader();
	}

	@After
	public void tearDown() throws Exception {
		Iterator<CustomElementsPortletDescriptor> iterator =
			_customElementsPortletDescriptors.iterator();

		while (iterator.hasNext()) {
			_persistence.remove(iterator.next());

			iterator.remove();
		}
	}

	@Test
	public void testCreate() throws Exception {
		long pk = RandomTestUtil.nextLong();

		CustomElementsPortletDescriptor customElementsPortletDescriptor =
			_persistence.create(pk);

		Assert.assertNotNull(customElementsPortletDescriptor);

		Assert.assertEquals(
			customElementsPortletDescriptor.getPrimaryKey(), pk);
	}

	@Test
	public void testRemove() throws Exception {
		CustomElementsPortletDescriptor newCustomElementsPortletDescriptor =
			addCustomElementsPortletDescriptor();

		_persistence.remove(newCustomElementsPortletDescriptor);

		CustomElementsPortletDescriptor
			existingCustomElementsPortletDescriptor =
				_persistence.fetchByPrimaryKey(
					newCustomElementsPortletDescriptor.getPrimaryKey());

		Assert.assertNull(existingCustomElementsPortletDescriptor);
	}

	@Test
	public void testUpdateNew() throws Exception {
		addCustomElementsPortletDescriptor();
	}

	@Test
	public void testUpdateExisting() throws Exception {
		long pk = RandomTestUtil.nextLong();

		CustomElementsPortletDescriptor newCustomElementsPortletDescriptor =
			_persistence.create(pk);

		newCustomElementsPortletDescriptor.setMvccVersion(
			RandomTestUtil.nextLong());

		newCustomElementsPortletDescriptor.setUuid(
			RandomTestUtil.randomString());

		newCustomElementsPortletDescriptor.setCompanyId(
			RandomTestUtil.nextLong());

		newCustomElementsPortletDescriptor.setUserId(RandomTestUtil.nextLong());

		newCustomElementsPortletDescriptor.setUserName(
			RandomTestUtil.randomString());

		newCustomElementsPortletDescriptor.setCreateDate(
			RandomTestUtil.nextDate());

		newCustomElementsPortletDescriptor.setModifiedDate(
			RandomTestUtil.nextDate());

		newCustomElementsPortletDescriptor.setCSSURLs(
			RandomTestUtil.randomString());

		newCustomElementsPortletDescriptor.setHTMLElementName(
			RandomTestUtil.randomString());

		newCustomElementsPortletDescriptor.setInstanceable(
			RandomTestUtil.randomBoolean());

		newCustomElementsPortletDescriptor.setName(
			RandomTestUtil.randomString());

		newCustomElementsPortletDescriptor.setProperties(
			RandomTestUtil.randomString());

		_customElementsPortletDescriptors.add(
			_persistence.update(newCustomElementsPortletDescriptor));

		CustomElementsPortletDescriptor
			existingCustomElementsPortletDescriptor =
				_persistence.findByPrimaryKey(
					newCustomElementsPortletDescriptor.getPrimaryKey());

		Assert.assertEquals(
			existingCustomElementsPortletDescriptor.getMvccVersion(),
			newCustomElementsPortletDescriptor.getMvccVersion());
		Assert.assertEquals(
			existingCustomElementsPortletDescriptor.getUuid(),
			newCustomElementsPortletDescriptor.getUuid());
		Assert.assertEquals(
			existingCustomElementsPortletDescriptor.
				getCustomElementsPortletDescriptorId(),
			newCustomElementsPortletDescriptor.
				getCustomElementsPortletDescriptorId());
		Assert.assertEquals(
			existingCustomElementsPortletDescriptor.getCompanyId(),
			newCustomElementsPortletDescriptor.getCompanyId());
		Assert.assertEquals(
			existingCustomElementsPortletDescriptor.getUserId(),
			newCustomElementsPortletDescriptor.getUserId());
		Assert.assertEquals(
			existingCustomElementsPortletDescriptor.getUserName(),
			newCustomElementsPortletDescriptor.getUserName());
		Assert.assertEquals(
			Time.getShortTimestamp(
				existingCustomElementsPortletDescriptor.getCreateDate()),
			Time.getShortTimestamp(
				newCustomElementsPortletDescriptor.getCreateDate()));
		Assert.assertEquals(
			Time.getShortTimestamp(
				existingCustomElementsPortletDescriptor.getModifiedDate()),
			Time.getShortTimestamp(
				newCustomElementsPortletDescriptor.getModifiedDate()));
		Assert.assertEquals(
			existingCustomElementsPortletDescriptor.getCSSURLs(),
			newCustomElementsPortletDescriptor.getCSSURLs());
		Assert.assertEquals(
			existingCustomElementsPortletDescriptor.getHTMLElementName(),
			newCustomElementsPortletDescriptor.getHTMLElementName());
		Assert.assertEquals(
			existingCustomElementsPortletDescriptor.isInstanceable(),
			newCustomElementsPortletDescriptor.isInstanceable());
		Assert.assertEquals(
			existingCustomElementsPortletDescriptor.getName(),
			newCustomElementsPortletDescriptor.getName());
		Assert.assertEquals(
			existingCustomElementsPortletDescriptor.getProperties(),
			newCustomElementsPortletDescriptor.getProperties());
	}

	@Test
	public void testCountByUuid() throws Exception {
		_persistence.countByUuid("");

		_persistence.countByUuid("null");

		_persistence.countByUuid((String)null);
	}

	@Test
	public void testCountByUuid_C() throws Exception {
		_persistence.countByUuid_C("", RandomTestUtil.nextLong());

		_persistence.countByUuid_C("null", 0L);

		_persistence.countByUuid_C((String)null, 0L);
	}

	@Test
	public void testFindByPrimaryKeyExisting() throws Exception {
		CustomElementsPortletDescriptor newCustomElementsPortletDescriptor =
			addCustomElementsPortletDescriptor();

		CustomElementsPortletDescriptor
			existingCustomElementsPortletDescriptor =
				_persistence.findByPrimaryKey(
					newCustomElementsPortletDescriptor.getPrimaryKey());

		Assert.assertEquals(
			existingCustomElementsPortletDescriptor,
			newCustomElementsPortletDescriptor);
	}

	@Test(expected = NoSuchPortletDescriptorException.class)
	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		_persistence.findByPrimaryKey(pk);
	}

	@Test
	public void testFindAll() throws Exception {
		_persistence.findAll(
			QueryUtil.ALL_POS, QueryUtil.ALL_POS, getOrderByComparator());
	}

	protected OrderByComparator<CustomElementsPortletDescriptor>
		getOrderByComparator() {

		return OrderByComparatorFactoryUtil.create(
			"CustomElementsPortletDesc", "mvccVersion", true, "uuid", true,
			"customElementsPortletDescriptorId", true, "companyId", true,
			"userId", true, "userName", true, "createDate", true,
			"modifiedDate", true, "htmlElementName", true, "instanceable", true,
			"name", true);
	}

	@Test
	public void testFetchByPrimaryKeyExisting() throws Exception {
		CustomElementsPortletDescriptor newCustomElementsPortletDescriptor =
			addCustomElementsPortletDescriptor();

		CustomElementsPortletDescriptor
			existingCustomElementsPortletDescriptor =
				_persistence.fetchByPrimaryKey(
					newCustomElementsPortletDescriptor.getPrimaryKey());

		Assert.assertEquals(
			existingCustomElementsPortletDescriptor,
			newCustomElementsPortletDescriptor);
	}

	@Test
	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		CustomElementsPortletDescriptor missingCustomElementsPortletDescriptor =
			_persistence.fetchByPrimaryKey(pk);

		Assert.assertNull(missingCustomElementsPortletDescriptor);
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereAllPrimaryKeysExist()
		throws Exception {

		CustomElementsPortletDescriptor newCustomElementsPortletDescriptor1 =
			addCustomElementsPortletDescriptor();
		CustomElementsPortletDescriptor newCustomElementsPortletDescriptor2 =
			addCustomElementsPortletDescriptor();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newCustomElementsPortletDescriptor1.getPrimaryKey());
		primaryKeys.add(newCustomElementsPortletDescriptor2.getPrimaryKey());

		Map<Serializable, CustomElementsPortletDescriptor>
			customElementsPortletDescriptors = _persistence.fetchByPrimaryKeys(
				primaryKeys);

		Assert.assertEquals(2, customElementsPortletDescriptors.size());
		Assert.assertEquals(
			newCustomElementsPortletDescriptor1,
			customElementsPortletDescriptors.get(
				newCustomElementsPortletDescriptor1.getPrimaryKey()));
		Assert.assertEquals(
			newCustomElementsPortletDescriptor2,
			customElementsPortletDescriptors.get(
				newCustomElementsPortletDescriptor2.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereNoPrimaryKeysExist()
		throws Exception {

		long pk1 = RandomTestUtil.nextLong();

		long pk2 = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(pk1);
		primaryKeys.add(pk2);

		Map<Serializable, CustomElementsPortletDescriptor>
			customElementsPortletDescriptors = _persistence.fetchByPrimaryKeys(
				primaryKeys);

		Assert.assertTrue(customElementsPortletDescriptors.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereSomePrimaryKeysExist()
		throws Exception {

		CustomElementsPortletDescriptor newCustomElementsPortletDescriptor =
			addCustomElementsPortletDescriptor();

		long pk = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newCustomElementsPortletDescriptor.getPrimaryKey());
		primaryKeys.add(pk);

		Map<Serializable, CustomElementsPortletDescriptor>
			customElementsPortletDescriptors = _persistence.fetchByPrimaryKeys(
				primaryKeys);

		Assert.assertEquals(1, customElementsPortletDescriptors.size());
		Assert.assertEquals(
			newCustomElementsPortletDescriptor,
			customElementsPortletDescriptors.get(
				newCustomElementsPortletDescriptor.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithNoPrimaryKeys() throws Exception {
		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		Map<Serializable, CustomElementsPortletDescriptor>
			customElementsPortletDescriptors = _persistence.fetchByPrimaryKeys(
				primaryKeys);

		Assert.assertTrue(customElementsPortletDescriptors.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithOnePrimaryKey() throws Exception {
		CustomElementsPortletDescriptor newCustomElementsPortletDescriptor =
			addCustomElementsPortletDescriptor();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newCustomElementsPortletDescriptor.getPrimaryKey());

		Map<Serializable, CustomElementsPortletDescriptor>
			customElementsPortletDescriptors = _persistence.fetchByPrimaryKeys(
				primaryKeys);

		Assert.assertEquals(1, customElementsPortletDescriptors.size());
		Assert.assertEquals(
			newCustomElementsPortletDescriptor,
			customElementsPortletDescriptors.get(
				newCustomElementsPortletDescriptor.getPrimaryKey()));
	}

	@Test
	public void testActionableDynamicQuery() throws Exception {
		final IntegerWrapper count = new IntegerWrapper();

		ActionableDynamicQuery actionableDynamicQuery =
			CustomElementsPortletDescriptorLocalServiceUtil.
				getActionableDynamicQuery();

		actionableDynamicQuery.setPerformActionMethod(
			new ActionableDynamicQuery.PerformActionMethod
				<CustomElementsPortletDescriptor>() {

				@Override
				public void performAction(
					CustomElementsPortletDescriptor
						customElementsPortletDescriptor) {

					Assert.assertNotNull(customElementsPortletDescriptor);

					count.increment();
				}

			});

		actionableDynamicQuery.performActions();

		Assert.assertEquals(count.getValue(), _persistence.countAll());
	}

	@Test
	public void testDynamicQueryByPrimaryKeyExisting() throws Exception {
		CustomElementsPortletDescriptor newCustomElementsPortletDescriptor =
			addCustomElementsPortletDescriptor();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			CustomElementsPortletDescriptor.class, _dynamicQueryClassLoader);

		dynamicQuery.add(
			RestrictionsFactoryUtil.eq(
				"customElementsPortletDescriptorId",
				newCustomElementsPortletDescriptor.
					getCustomElementsPortletDescriptorId()));

		List<CustomElementsPortletDescriptor> result =
			_persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		CustomElementsPortletDescriptor
			existingCustomElementsPortletDescriptor = result.get(0);

		Assert.assertEquals(
			existingCustomElementsPortletDescriptor,
			newCustomElementsPortletDescriptor);
	}

	@Test
	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			CustomElementsPortletDescriptor.class, _dynamicQueryClassLoader);

		dynamicQuery.add(
			RestrictionsFactoryUtil.eq(
				"customElementsPortletDescriptorId",
				RandomTestUtil.nextLong()));

		List<CustomElementsPortletDescriptor> result =
			_persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testDynamicQueryByProjectionExisting() throws Exception {
		CustomElementsPortletDescriptor newCustomElementsPortletDescriptor =
			addCustomElementsPortletDescriptor();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			CustomElementsPortletDescriptor.class, _dynamicQueryClassLoader);

		dynamicQuery.setProjection(
			ProjectionFactoryUtil.property(
				"customElementsPortletDescriptorId"));

		Object newCustomElementsPortletDescriptorId =
			newCustomElementsPortletDescriptor.
				getCustomElementsPortletDescriptorId();

		dynamicQuery.add(
			RestrictionsFactoryUtil.in(
				"customElementsPortletDescriptorId",
				new Object[] {newCustomElementsPortletDescriptorId}));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		Object existingCustomElementsPortletDescriptorId = result.get(0);

		Assert.assertEquals(
			existingCustomElementsPortletDescriptorId,
			newCustomElementsPortletDescriptorId);
	}

	@Test
	public void testDynamicQueryByProjectionMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			CustomElementsPortletDescriptor.class, _dynamicQueryClassLoader);

		dynamicQuery.setProjection(
			ProjectionFactoryUtil.property(
				"customElementsPortletDescriptorId"));

		dynamicQuery.add(
			RestrictionsFactoryUtil.in(
				"customElementsPortletDescriptorId",
				new Object[] {RandomTestUtil.nextLong()}));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	protected CustomElementsPortletDescriptor
			addCustomElementsPortletDescriptor()
		throws Exception {

		long pk = RandomTestUtil.nextLong();

		CustomElementsPortletDescriptor customElementsPortletDescriptor =
			_persistence.create(pk);

		customElementsPortletDescriptor.setMvccVersion(
			RandomTestUtil.nextLong());

		customElementsPortletDescriptor.setUuid(RandomTestUtil.randomString());

		customElementsPortletDescriptor.setCompanyId(RandomTestUtil.nextLong());

		customElementsPortletDescriptor.setUserId(RandomTestUtil.nextLong());

		customElementsPortletDescriptor.setUserName(
			RandomTestUtil.randomString());

		customElementsPortletDescriptor.setCreateDate(
			RandomTestUtil.nextDate());

		customElementsPortletDescriptor.setModifiedDate(
			RandomTestUtil.nextDate());

		customElementsPortletDescriptor.setCSSURLs(
			RandomTestUtil.randomString());

		customElementsPortletDescriptor.setHTMLElementName(
			RandomTestUtil.randomString());

		customElementsPortletDescriptor.setInstanceable(
			RandomTestUtil.randomBoolean());

		customElementsPortletDescriptor.setName(RandomTestUtil.randomString());

		customElementsPortletDescriptor.setProperties(
			RandomTestUtil.randomString());

		_customElementsPortletDescriptors.add(
			_persistence.update(customElementsPortletDescriptor));

		return customElementsPortletDescriptor;
	}

	private List<CustomElementsPortletDescriptor>
		_customElementsPortletDescriptors =
			new ArrayList<CustomElementsPortletDescriptor>();
	private CustomElementsPortletDescriptorPersistence _persistence;
	private ClassLoader _dynamicQueryClassLoader;

}