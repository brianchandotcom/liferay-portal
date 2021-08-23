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
import com.liferay.custom.elements.exception.NoSuchSourceException;
import com.liferay.custom.elements.model.CustomElementsSource;
import com.liferay.custom.elements.service.CustomElementsSourceLocalServiceUtil;
import com.liferay.custom.elements.service.persistence.CustomElementsSourcePersistence;
import com.liferay.custom.elements.service.persistence.CustomElementsSourceUtil;
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
public class CustomElementsSourcePersistenceTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(), PersistenceTestRule.INSTANCE,
			new TransactionalTestRule(
				Propagation.REQUIRED, "com.liferay.custom.elements.service"));

	@Before
	public void setUp() {
		_persistence = CustomElementsSourceUtil.getPersistence();

		Class<?> clazz = _persistence.getClass();

		_dynamicQueryClassLoader = clazz.getClassLoader();
	}

	@After
	public void tearDown() throws Exception {
		Iterator<CustomElementsSource> iterator =
			_customElementsSources.iterator();

		while (iterator.hasNext()) {
			_persistence.remove(iterator.next());

			iterator.remove();
		}
	}

	@Test
	public void testCreate() throws Exception {
		long pk = RandomTestUtil.nextLong();

		CustomElementsSource customElementsSource = _persistence.create(pk);

		Assert.assertNotNull(customElementsSource);

		Assert.assertEquals(customElementsSource.getPrimaryKey(), pk);
	}

	@Test
	public void testRemove() throws Exception {
		CustomElementsSource newCustomElementsSource =
			addCustomElementsSource();

		_persistence.remove(newCustomElementsSource);

		CustomElementsSource existingCustomElementsSource =
			_persistence.fetchByPrimaryKey(
				newCustomElementsSource.getPrimaryKey());

		Assert.assertNull(existingCustomElementsSource);
	}

	@Test
	public void testUpdateNew() throws Exception {
		addCustomElementsSource();
	}

	@Test
	public void testUpdateExisting() throws Exception {
		long pk = RandomTestUtil.nextLong();

		CustomElementsSource newCustomElementsSource = _persistence.create(pk);

		newCustomElementsSource.setMvccVersion(RandomTestUtil.nextLong());

		newCustomElementsSource.setUuid(RandomTestUtil.randomString());

		newCustomElementsSource.setCompanyId(RandomTestUtil.nextLong());

		newCustomElementsSource.setUserId(RandomTestUtil.nextLong());

		newCustomElementsSource.setUserName(RandomTestUtil.randomString());

		newCustomElementsSource.setCreateDate(RandomTestUtil.nextDate());

		newCustomElementsSource.setModifiedDate(RandomTestUtil.nextDate());

		newCustomElementsSource.setHTMLElementName(
			RandomTestUtil.randomString());

		newCustomElementsSource.setName(RandomTestUtil.randomString());

		newCustomElementsSource.setURLs(RandomTestUtil.randomString());

		_customElementsSources.add(
			_persistence.update(newCustomElementsSource));

		CustomElementsSource existingCustomElementsSource =
			_persistence.findByPrimaryKey(
				newCustomElementsSource.getPrimaryKey());

		Assert.assertEquals(
			existingCustomElementsSource.getMvccVersion(),
			newCustomElementsSource.getMvccVersion());
		Assert.assertEquals(
			existingCustomElementsSource.getUuid(),
			newCustomElementsSource.getUuid());
		Assert.assertEquals(
			existingCustomElementsSource.getCustomElementsSourceId(),
			newCustomElementsSource.getCustomElementsSourceId());
		Assert.assertEquals(
			existingCustomElementsSource.getCompanyId(),
			newCustomElementsSource.getCompanyId());
		Assert.assertEquals(
			existingCustomElementsSource.getUserId(),
			newCustomElementsSource.getUserId());
		Assert.assertEquals(
			existingCustomElementsSource.getUserName(),
			newCustomElementsSource.getUserName());
		Assert.assertEquals(
			Time.getShortTimestamp(
				existingCustomElementsSource.getCreateDate()),
			Time.getShortTimestamp(newCustomElementsSource.getCreateDate()));
		Assert.assertEquals(
			Time.getShortTimestamp(
				existingCustomElementsSource.getModifiedDate()),
			Time.getShortTimestamp(newCustomElementsSource.getModifiedDate()));
		Assert.assertEquals(
			existingCustomElementsSource.getHTMLElementName(),
			newCustomElementsSource.getHTMLElementName());
		Assert.assertEquals(
			existingCustomElementsSource.getName(),
			newCustomElementsSource.getName());
		Assert.assertEquals(
			existingCustomElementsSource.getURLs(),
			newCustomElementsSource.getURLs());
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
	public void testCountByCompanyId() throws Exception {
		_persistence.countByCompanyId(RandomTestUtil.nextLong());

		_persistence.countByCompanyId(0L);
	}

	@Test
	public void testFindByPrimaryKeyExisting() throws Exception {
		CustomElementsSource newCustomElementsSource =
			addCustomElementsSource();

		CustomElementsSource existingCustomElementsSource =
			_persistence.findByPrimaryKey(
				newCustomElementsSource.getPrimaryKey());

		Assert.assertEquals(
			existingCustomElementsSource, newCustomElementsSource);
	}

	@Test(expected = NoSuchSourceException.class)
	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		_persistence.findByPrimaryKey(pk);
	}

	@Test
	public void testFindAll() throws Exception {
		_persistence.findAll(
			QueryUtil.ALL_POS, QueryUtil.ALL_POS, getOrderByComparator());
	}

	protected OrderByComparator<CustomElementsSource> getOrderByComparator() {
		return OrderByComparatorFactoryUtil.create(
			"CustomElementsSource", "mvccVersion", true, "uuid", true,
			"customElementsSourceId", true, "companyId", true, "userId", true,
			"userName", true, "createDate", true, "modifiedDate", true,
			"htmlElementName", true, "name", true);
	}

	@Test
	public void testFetchByPrimaryKeyExisting() throws Exception {
		CustomElementsSource newCustomElementsSource =
			addCustomElementsSource();

		CustomElementsSource existingCustomElementsSource =
			_persistence.fetchByPrimaryKey(
				newCustomElementsSource.getPrimaryKey());

		Assert.assertEquals(
			existingCustomElementsSource, newCustomElementsSource);
	}

	@Test
	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		CustomElementsSource missingCustomElementsSource =
			_persistence.fetchByPrimaryKey(pk);

		Assert.assertNull(missingCustomElementsSource);
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereAllPrimaryKeysExist()
		throws Exception {

		CustomElementsSource newCustomElementsSource1 =
			addCustomElementsSource();
		CustomElementsSource newCustomElementsSource2 =
			addCustomElementsSource();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newCustomElementsSource1.getPrimaryKey());
		primaryKeys.add(newCustomElementsSource2.getPrimaryKey());

		Map<Serializable, CustomElementsSource> customElementsSources =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(2, customElementsSources.size());
		Assert.assertEquals(
			newCustomElementsSource1,
			customElementsSources.get(
				newCustomElementsSource1.getPrimaryKey()));
		Assert.assertEquals(
			newCustomElementsSource2,
			customElementsSources.get(
				newCustomElementsSource2.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereNoPrimaryKeysExist()
		throws Exception {

		long pk1 = RandomTestUtil.nextLong();

		long pk2 = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(pk1);
		primaryKeys.add(pk2);

		Map<Serializable, CustomElementsSource> customElementsSources =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(customElementsSources.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereSomePrimaryKeysExist()
		throws Exception {

		CustomElementsSource newCustomElementsSource =
			addCustomElementsSource();

		long pk = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newCustomElementsSource.getPrimaryKey());
		primaryKeys.add(pk);

		Map<Serializable, CustomElementsSource> customElementsSources =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, customElementsSources.size());
		Assert.assertEquals(
			newCustomElementsSource,
			customElementsSources.get(newCustomElementsSource.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithNoPrimaryKeys() throws Exception {
		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		Map<Serializable, CustomElementsSource> customElementsSources =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(customElementsSources.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithOnePrimaryKey() throws Exception {
		CustomElementsSource newCustomElementsSource =
			addCustomElementsSource();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newCustomElementsSource.getPrimaryKey());

		Map<Serializable, CustomElementsSource> customElementsSources =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, customElementsSources.size());
		Assert.assertEquals(
			newCustomElementsSource,
			customElementsSources.get(newCustomElementsSource.getPrimaryKey()));
	}

	@Test
	public void testActionableDynamicQuery() throws Exception {
		final IntegerWrapper count = new IntegerWrapper();

		ActionableDynamicQuery actionableDynamicQuery =
			CustomElementsSourceLocalServiceUtil.getActionableDynamicQuery();

		actionableDynamicQuery.setPerformActionMethod(
			new ActionableDynamicQuery.PerformActionMethod
				<CustomElementsSource>() {

				@Override
				public void performAction(
					CustomElementsSource customElementsSource) {

					Assert.assertNotNull(customElementsSource);

					count.increment();
				}

			});

		actionableDynamicQuery.performActions();

		Assert.assertEquals(count.getValue(), _persistence.countAll());
	}

	@Test
	public void testDynamicQueryByPrimaryKeyExisting() throws Exception {
		CustomElementsSource newCustomElementsSource =
			addCustomElementsSource();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			CustomElementsSource.class, _dynamicQueryClassLoader);

		dynamicQuery.add(
			RestrictionsFactoryUtil.eq(
				"customElementsSourceId",
				newCustomElementsSource.getCustomElementsSourceId()));

		List<CustomElementsSource> result = _persistence.findWithDynamicQuery(
			dynamicQuery);

		Assert.assertEquals(1, result.size());

		CustomElementsSource existingCustomElementsSource = result.get(0);

		Assert.assertEquals(
			existingCustomElementsSource, newCustomElementsSource);
	}

	@Test
	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			CustomElementsSource.class, _dynamicQueryClassLoader);

		dynamicQuery.add(
			RestrictionsFactoryUtil.eq(
				"customElementsSourceId", RandomTestUtil.nextLong()));

		List<CustomElementsSource> result = _persistence.findWithDynamicQuery(
			dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testDynamicQueryByProjectionExisting() throws Exception {
		CustomElementsSource newCustomElementsSource =
			addCustomElementsSource();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			CustomElementsSource.class, _dynamicQueryClassLoader);

		dynamicQuery.setProjection(
			ProjectionFactoryUtil.property("customElementsSourceId"));

		Object newCustomElementsSourceId =
			newCustomElementsSource.getCustomElementsSourceId();

		dynamicQuery.add(
			RestrictionsFactoryUtil.in(
				"customElementsSourceId",
				new Object[] {newCustomElementsSourceId}));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		Object existingCustomElementsSourceId = result.get(0);

		Assert.assertEquals(
			existingCustomElementsSourceId, newCustomElementsSourceId);
	}

	@Test
	public void testDynamicQueryByProjectionMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			CustomElementsSource.class, _dynamicQueryClassLoader);

		dynamicQuery.setProjection(
			ProjectionFactoryUtil.property("customElementsSourceId"));

		dynamicQuery.add(
			RestrictionsFactoryUtil.in(
				"customElementsSourceId",
				new Object[] {RandomTestUtil.nextLong()}));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	protected CustomElementsSource addCustomElementsSource() throws Exception {
		long pk = RandomTestUtil.nextLong();

		CustomElementsSource customElementsSource = _persistence.create(pk);

		customElementsSource.setMvccVersion(RandomTestUtil.nextLong());

		customElementsSource.setUuid(RandomTestUtil.randomString());

		customElementsSource.setCompanyId(RandomTestUtil.nextLong());

		customElementsSource.setUserId(RandomTestUtil.nextLong());

		customElementsSource.setUserName(RandomTestUtil.randomString());

		customElementsSource.setCreateDate(RandomTestUtil.nextDate());

		customElementsSource.setModifiedDate(RandomTestUtil.nextDate());

		customElementsSource.setHTMLElementName(RandomTestUtil.randomString());

		customElementsSource.setName(RandomTestUtil.randomString());

		customElementsSource.setURLs(RandomTestUtil.randomString());

		_customElementsSources.add(_persistence.update(customElementsSource));

		return customElementsSource;
	}

	private List<CustomElementsSource> _customElementsSources =
		new ArrayList<CustomElementsSource>();
	private CustomElementsSourcePersistence _persistence;
	private ClassLoader _dynamicQueryClassLoader;

}