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

package com.liferay.friendly.url.service.persistence.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;

import com.liferay.friendly.url.exception.NoSuchFriendlyURLTitleLocalizationException;
import com.liferay.friendly.url.model.FriendlyURLTitleLocalization;
import com.liferay.friendly.url.service.FriendlyURLTitleLocalizationLocalServiceUtil;
import com.liferay.friendly.url.service.persistence.FriendlyURLTitleLocalizationPersistence;
import com.liferay.friendly.url.service.persistence.FriendlyURLTitleLocalizationUtil;

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
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PersistenceTestRule;
import com.liferay.portal.test.rule.TransactionalTestRule;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import org.junit.runner.RunWith;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/**
 * @generated
 */
@RunWith(Arquillian.class)
public class FriendlyURLTitleLocalizationPersistenceTest {
	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule = new AggregateTestRule(new LiferayIntegrationTestRule(),
			PersistenceTestRule.INSTANCE,
			new TransactionalTestRule(Propagation.REQUIRED,
				"com.liferay.friendly.url.service"));

	@Before
	public void setUp() {
		_persistence = FriendlyURLTitleLocalizationUtil.getPersistence();

		Class<?> clazz = _persistence.getClass();

		_dynamicQueryClassLoader = clazz.getClassLoader();
	}

	@After
	public void tearDown() throws Exception {
		Iterator<FriendlyURLTitleLocalization> iterator = _friendlyURLTitleLocalizations.iterator();

		while (iterator.hasNext()) {
			_persistence.remove(iterator.next());

			iterator.remove();
		}
	}

	@Test
	public void testCreate() throws Exception {
		long pk = RandomTestUtil.nextLong();

		FriendlyURLTitleLocalization friendlyURLTitleLocalization = _persistence.create(pk);

		Assert.assertNotNull(friendlyURLTitleLocalization);

		Assert.assertEquals(friendlyURLTitleLocalization.getPrimaryKey(), pk);
	}

	@Test
	public void testRemove() throws Exception {
		FriendlyURLTitleLocalization newFriendlyURLTitleLocalization = addFriendlyURLTitleLocalization();

		_persistence.remove(newFriendlyURLTitleLocalization);

		FriendlyURLTitleLocalization existingFriendlyURLTitleLocalization = _persistence.fetchByPrimaryKey(newFriendlyURLTitleLocalization.getPrimaryKey());

		Assert.assertNull(existingFriendlyURLTitleLocalization);
	}

	@Test
	public void testUpdateNew() throws Exception {
		addFriendlyURLTitleLocalization();
	}

	@Test
	public void testUpdateExisting() throws Exception {
		long pk = RandomTestUtil.nextLong();

		FriendlyURLTitleLocalization newFriendlyURLTitleLocalization = _persistence.create(pk);

		newFriendlyURLTitleLocalization.setGroupId(RandomTestUtil.nextLong());

		newFriendlyURLTitleLocalization.setCompanyId(RandomTestUtil.nextLong());

		newFriendlyURLTitleLocalization.setFriendlyURLId(RandomTestUtil.nextLong());

		newFriendlyURLTitleLocalization.setUrlTitle(RandomTestUtil.randomString());

		newFriendlyURLTitleLocalization.setLanguageId(RandomTestUtil.randomString());

		_friendlyURLTitleLocalizations.add(_persistence.update(
				newFriendlyURLTitleLocalization));

		FriendlyURLTitleLocalization existingFriendlyURLTitleLocalization = _persistence.findByPrimaryKey(newFriendlyURLTitleLocalization.getPrimaryKey());

		Assert.assertEquals(existingFriendlyURLTitleLocalization.getFriendlyURLTitleLocalizationId(),
			newFriendlyURLTitleLocalization.getFriendlyURLTitleLocalizationId());
		Assert.assertEquals(existingFriendlyURLTitleLocalization.getGroupId(),
			newFriendlyURLTitleLocalization.getGroupId());
		Assert.assertEquals(existingFriendlyURLTitleLocalization.getCompanyId(),
			newFriendlyURLTitleLocalization.getCompanyId());
		Assert.assertEquals(existingFriendlyURLTitleLocalization.getFriendlyURLId(),
			newFriendlyURLTitleLocalization.getFriendlyURLId());
		Assert.assertEquals(existingFriendlyURLTitleLocalization.getUrlTitle(),
			newFriendlyURLTitleLocalization.getUrlTitle());
		Assert.assertEquals(existingFriendlyURLTitleLocalization.getLanguageId(),
			newFriendlyURLTitleLocalization.getLanguageId());
	}

	@Test
	public void testCountByG_F() throws Exception {
		_persistence.countByG_F(RandomTestUtil.nextLong(),
			RandomTestUtil.nextLong());

		_persistence.countByG_F(0L, 0L);
	}

	@Test
	public void testCountByG_F_L() throws Exception {
		_persistence.countByG_F_L(RandomTestUtil.nextLong(),
			RandomTestUtil.nextLong(), StringPool.BLANK);

		_persistence.countByG_F_L(0L, 0L, StringPool.NULL);

		_persistence.countByG_F_L(0L, 0L, (String)null);
	}

	@Test
	public void testCountByG_U_L() throws Exception {
		_persistence.countByG_U_L(RandomTestUtil.nextLong(), StringPool.BLANK,
			StringPool.BLANK);

		_persistence.countByG_U_L(0L, StringPool.NULL, StringPool.NULL);

		_persistence.countByG_U_L(0L, (String)null, (String)null);
	}

	@Test
	public void testFindByPrimaryKeyExisting() throws Exception {
		FriendlyURLTitleLocalization newFriendlyURLTitleLocalization = addFriendlyURLTitleLocalization();

		FriendlyURLTitleLocalization existingFriendlyURLTitleLocalization = _persistence.findByPrimaryKey(newFriendlyURLTitleLocalization.getPrimaryKey());

		Assert.assertEquals(existingFriendlyURLTitleLocalization,
			newFriendlyURLTitleLocalization);
	}

	@Test(expected = NoSuchFriendlyURLTitleLocalizationException.class)
	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		_persistence.findByPrimaryKey(pk);
	}

	@Test
	public void testFindAll() throws Exception {
		_persistence.findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			getOrderByComparator());
	}

	protected OrderByComparator<FriendlyURLTitleLocalization> getOrderByComparator() {
		return OrderByComparatorFactoryUtil.create("FriendlyURLTitleLocalization",
			"friendlyURLTitleLocalizationId", true, "groupId", true,
			"companyId", true, "friendlyURLId", true, "urlTitle", true,
			"languageId", true);
	}

	@Test
	public void testFetchByPrimaryKeyExisting() throws Exception {
		FriendlyURLTitleLocalization newFriendlyURLTitleLocalization = addFriendlyURLTitleLocalization();

		FriendlyURLTitleLocalization existingFriendlyURLTitleLocalization = _persistence.fetchByPrimaryKey(newFriendlyURLTitleLocalization.getPrimaryKey());

		Assert.assertEquals(existingFriendlyURLTitleLocalization,
			newFriendlyURLTitleLocalization);
	}

	@Test
	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		FriendlyURLTitleLocalization missingFriendlyURLTitleLocalization = _persistence.fetchByPrimaryKey(pk);

		Assert.assertNull(missingFriendlyURLTitleLocalization);
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereAllPrimaryKeysExist()
		throws Exception {
		FriendlyURLTitleLocalization newFriendlyURLTitleLocalization1 = addFriendlyURLTitleLocalization();
		FriendlyURLTitleLocalization newFriendlyURLTitleLocalization2 = addFriendlyURLTitleLocalization();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newFriendlyURLTitleLocalization1.getPrimaryKey());
		primaryKeys.add(newFriendlyURLTitleLocalization2.getPrimaryKey());

		Map<Serializable, FriendlyURLTitleLocalization> friendlyURLTitleLocalizations =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(2, friendlyURLTitleLocalizations.size());
		Assert.assertEquals(newFriendlyURLTitleLocalization1,
			friendlyURLTitleLocalizations.get(
				newFriendlyURLTitleLocalization1.getPrimaryKey()));
		Assert.assertEquals(newFriendlyURLTitleLocalization2,
			friendlyURLTitleLocalizations.get(
				newFriendlyURLTitleLocalization2.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereNoPrimaryKeysExist()
		throws Exception {
		long pk1 = RandomTestUtil.nextLong();

		long pk2 = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(pk1);
		primaryKeys.add(pk2);

		Map<Serializable, FriendlyURLTitleLocalization> friendlyURLTitleLocalizations =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(friendlyURLTitleLocalizations.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereSomePrimaryKeysExist()
		throws Exception {
		FriendlyURLTitleLocalization newFriendlyURLTitleLocalization = addFriendlyURLTitleLocalization();

		long pk = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newFriendlyURLTitleLocalization.getPrimaryKey());
		primaryKeys.add(pk);

		Map<Serializable, FriendlyURLTitleLocalization> friendlyURLTitleLocalizations =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, friendlyURLTitleLocalizations.size());
		Assert.assertEquals(newFriendlyURLTitleLocalization,
			friendlyURLTitleLocalizations.get(
				newFriendlyURLTitleLocalization.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithNoPrimaryKeys()
		throws Exception {
		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		Map<Serializable, FriendlyURLTitleLocalization> friendlyURLTitleLocalizations =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(friendlyURLTitleLocalizations.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithOnePrimaryKey()
		throws Exception {
		FriendlyURLTitleLocalization newFriendlyURLTitleLocalization = addFriendlyURLTitleLocalization();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newFriendlyURLTitleLocalization.getPrimaryKey());

		Map<Serializable, FriendlyURLTitleLocalization> friendlyURLTitleLocalizations =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, friendlyURLTitleLocalizations.size());
		Assert.assertEquals(newFriendlyURLTitleLocalization,
			friendlyURLTitleLocalizations.get(
				newFriendlyURLTitleLocalization.getPrimaryKey()));
	}

	@Test
	public void testActionableDynamicQuery() throws Exception {
		final IntegerWrapper count = new IntegerWrapper();

		ActionableDynamicQuery actionableDynamicQuery = FriendlyURLTitleLocalizationLocalServiceUtil.getActionableDynamicQuery();

		actionableDynamicQuery.setPerformActionMethod(new ActionableDynamicQuery.PerformActionMethod<FriendlyURLTitleLocalization>() {
				@Override
				public void performAction(
					FriendlyURLTitleLocalization friendlyURLTitleLocalization) {
					Assert.assertNotNull(friendlyURLTitleLocalization);

					count.increment();
				}
			});

		actionableDynamicQuery.performActions();

		Assert.assertEquals(count.getValue(), _persistence.countAll());
	}

	@Test
	public void testDynamicQueryByPrimaryKeyExisting()
		throws Exception {
		FriendlyURLTitleLocalization newFriendlyURLTitleLocalization = addFriendlyURLTitleLocalization();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(FriendlyURLTitleLocalization.class,
				_dynamicQueryClassLoader);

		dynamicQuery.add(RestrictionsFactoryUtil.eq(
				"friendlyURLTitleLocalizationId",
				newFriendlyURLTitleLocalization.getFriendlyURLTitleLocalizationId()));

		List<FriendlyURLTitleLocalization> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		FriendlyURLTitleLocalization existingFriendlyURLTitleLocalization = result.get(0);

		Assert.assertEquals(existingFriendlyURLTitleLocalization,
			newFriendlyURLTitleLocalization);
	}

	@Test
	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(FriendlyURLTitleLocalization.class,
				_dynamicQueryClassLoader);

		dynamicQuery.add(RestrictionsFactoryUtil.eq(
				"friendlyURLTitleLocalizationId", RandomTestUtil.nextLong()));

		List<FriendlyURLTitleLocalization> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testDynamicQueryByProjectionExisting()
		throws Exception {
		FriendlyURLTitleLocalization newFriendlyURLTitleLocalization = addFriendlyURLTitleLocalization();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(FriendlyURLTitleLocalization.class,
				_dynamicQueryClassLoader);

		dynamicQuery.setProjection(ProjectionFactoryUtil.property(
				"friendlyURLTitleLocalizationId"));

		Object newFriendlyURLTitleLocalizationId = newFriendlyURLTitleLocalization.getFriendlyURLTitleLocalizationId();

		dynamicQuery.add(RestrictionsFactoryUtil.in(
				"friendlyURLTitleLocalizationId",
				new Object[] { newFriendlyURLTitleLocalizationId }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		Object existingFriendlyURLTitleLocalizationId = result.get(0);

		Assert.assertEquals(existingFriendlyURLTitleLocalizationId,
			newFriendlyURLTitleLocalizationId);
	}

	@Test
	public void testDynamicQueryByProjectionMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(FriendlyURLTitleLocalization.class,
				_dynamicQueryClassLoader);

		dynamicQuery.setProjection(ProjectionFactoryUtil.property(
				"friendlyURLTitleLocalizationId"));

		dynamicQuery.add(RestrictionsFactoryUtil.in(
				"friendlyURLTitleLocalizationId",
				new Object[] { RandomTestUtil.nextLong() }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testResetOriginalValues() throws Exception {
		FriendlyURLTitleLocalization newFriendlyURLTitleLocalization = addFriendlyURLTitleLocalization();

		_persistence.clearCache();

		FriendlyURLTitleLocalization existingFriendlyURLTitleLocalization = _persistence.findByPrimaryKey(newFriendlyURLTitleLocalization.getPrimaryKey());

		Assert.assertEquals(Long.valueOf(
				existingFriendlyURLTitleLocalization.getGroupId()),
			ReflectionTestUtil.<Long>invoke(
				existingFriendlyURLTitleLocalization, "getOriginalGroupId",
				new Class<?>[0]));
		Assert.assertEquals(Long.valueOf(
				existingFriendlyURLTitleLocalization.getFriendlyURLId()),
			ReflectionTestUtil.<Long>invoke(
				existingFriendlyURLTitleLocalization,
				"getOriginalFriendlyURLId", new Class<?>[0]));
		Assert.assertTrue(Objects.equals(
				existingFriendlyURLTitleLocalization.getLanguageId(),
				ReflectionTestUtil.invoke(
					existingFriendlyURLTitleLocalization,
					"getOriginalLanguageId", new Class<?>[0])));

		Assert.assertEquals(Long.valueOf(
				existingFriendlyURLTitleLocalization.getGroupId()),
			ReflectionTestUtil.<Long>invoke(
				existingFriendlyURLTitleLocalization, "getOriginalGroupId",
				new Class<?>[0]));
		Assert.assertTrue(Objects.equals(
				existingFriendlyURLTitleLocalization.getUrlTitle(),
				ReflectionTestUtil.invoke(
					existingFriendlyURLTitleLocalization,
					"getOriginalUrlTitle", new Class<?>[0])));
		Assert.assertTrue(Objects.equals(
				existingFriendlyURLTitleLocalization.getLanguageId(),
				ReflectionTestUtil.invoke(
					existingFriendlyURLTitleLocalization,
					"getOriginalLanguageId", new Class<?>[0])));
	}

	protected FriendlyURLTitleLocalization addFriendlyURLTitleLocalization()
		throws Exception {
		long pk = RandomTestUtil.nextLong();

		FriendlyURLTitleLocalization friendlyURLTitleLocalization = _persistence.create(pk);

		friendlyURLTitleLocalization.setGroupId(RandomTestUtil.nextLong());

		friendlyURLTitleLocalization.setCompanyId(RandomTestUtil.nextLong());

		friendlyURLTitleLocalization.setFriendlyURLId(RandomTestUtil.nextLong());

		friendlyURLTitleLocalization.setUrlTitle(RandomTestUtil.randomString());

		friendlyURLTitleLocalization.setLanguageId(RandomTestUtil.randomString());

		_friendlyURLTitleLocalizations.add(_persistence.update(
				friendlyURLTitleLocalization));

		return friendlyURLTitleLocalization;
	}

	private List<FriendlyURLTitleLocalization> _friendlyURLTitleLocalizations = new ArrayList<FriendlyURLTitleLocalization>();
	private FriendlyURLTitleLocalizationPersistence _persistence;
	private ClassLoader _dynamicQueryClassLoader;
}