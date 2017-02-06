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

import com.liferay.friendly.url.exception.NoSuchFriendlyURLLocalizationException;
import com.liferay.friendly.url.model.FriendlyURLLocalization;
import com.liferay.friendly.url.service.FriendlyURLLocalizationLocalServiceUtil;
import com.liferay.friendly.url.service.persistence.FriendlyURLLocalizationPersistence;
import com.liferay.friendly.url.service.persistence.FriendlyURLLocalizationUtil;

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
public class FriendlyURLLocalizationPersistenceTest {
	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule = new AggregateTestRule(new LiferayIntegrationTestRule(),
			PersistenceTestRule.INSTANCE,
			new TransactionalTestRule(Propagation.REQUIRED,
				"com.liferay.friendly.url.service"));

	@Before
	public void setUp() {
		_persistence = FriendlyURLLocalizationUtil.getPersistence();

		Class<?> clazz = _persistence.getClass();

		_dynamicQueryClassLoader = clazz.getClassLoader();
	}

	@After
	public void tearDown() throws Exception {
		Iterator<FriendlyURLLocalization> iterator = _friendlyURLLocalizations.iterator();

		while (iterator.hasNext()) {
			_persistence.remove(iterator.next());

			iterator.remove();
		}
	}

	@Test
	public void testCreate() throws Exception {
		long pk = RandomTestUtil.nextLong();

		FriendlyURLLocalization friendlyURLLocalization = _persistence.create(pk);

		Assert.assertNotNull(friendlyURLLocalization);

		Assert.assertEquals(friendlyURLLocalization.getPrimaryKey(), pk);
	}

	@Test
	public void testRemove() throws Exception {
		FriendlyURLLocalization newFriendlyURLLocalization = addFriendlyURLLocalization();

		_persistence.remove(newFriendlyURLLocalization);

		FriendlyURLLocalization existingFriendlyURLLocalization = _persistence.fetchByPrimaryKey(newFriendlyURLLocalization.getPrimaryKey());

		Assert.assertNull(existingFriendlyURLLocalization);
	}

	@Test
	public void testUpdateNew() throws Exception {
		addFriendlyURLLocalization();
	}

	@Test
	public void testUpdateExisting() throws Exception {
		long pk = RandomTestUtil.nextLong();

		FriendlyURLLocalization newFriendlyURLLocalization = _persistence.create(pk);

		newFriendlyURLLocalization.setGroupId(RandomTestUtil.nextLong());

		newFriendlyURLLocalization.setCompanyId(RandomTestUtil.nextLong());

		newFriendlyURLLocalization.setFriendlyURLId(RandomTestUtil.nextLong());

		newFriendlyURLLocalization.setUrlTitle(RandomTestUtil.randomString());

		newFriendlyURLLocalization.setLanguageId(RandomTestUtil.randomString());

		_friendlyURLLocalizations.add(_persistence.update(
				newFriendlyURLLocalization));

		FriendlyURLLocalization existingFriendlyURLLocalization = _persistence.findByPrimaryKey(newFriendlyURLLocalization.getPrimaryKey());

		Assert.assertEquals(existingFriendlyURLLocalization.getFriendlyURLLocalizationId(),
			newFriendlyURLLocalization.getFriendlyURLLocalizationId());
		Assert.assertEquals(existingFriendlyURLLocalization.getGroupId(),
			newFriendlyURLLocalization.getGroupId());
		Assert.assertEquals(existingFriendlyURLLocalization.getCompanyId(),
			newFriendlyURLLocalization.getCompanyId());
		Assert.assertEquals(existingFriendlyURLLocalization.getFriendlyURLId(),
			newFriendlyURLLocalization.getFriendlyURLId());
		Assert.assertEquals(existingFriendlyURLLocalization.getUrlTitle(),
			newFriendlyURLLocalization.getUrlTitle());
		Assert.assertEquals(existingFriendlyURLLocalization.getLanguageId(),
			newFriendlyURLLocalization.getLanguageId());
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
		FriendlyURLLocalization newFriendlyURLLocalization = addFriendlyURLLocalization();

		FriendlyURLLocalization existingFriendlyURLLocalization = _persistence.findByPrimaryKey(newFriendlyURLLocalization.getPrimaryKey());

		Assert.assertEquals(existingFriendlyURLLocalization,
			newFriendlyURLLocalization);
	}

	@Test(expected = NoSuchFriendlyURLLocalizationException.class)
	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		_persistence.findByPrimaryKey(pk);
	}

	@Test
	public void testFindAll() throws Exception {
		_persistence.findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			getOrderByComparator());
	}

	protected OrderByComparator<FriendlyURLLocalization> getOrderByComparator() {
		return OrderByComparatorFactoryUtil.create("FriendlyURLLocalization",
			"friendlyURLLocalizationId", true, "groupId", true, "companyId",
			true, "friendlyURLId", true, "urlTitle", true, "languageId", true);
	}

	@Test
	public void testFetchByPrimaryKeyExisting() throws Exception {
		FriendlyURLLocalization newFriendlyURLLocalization = addFriendlyURLLocalization();

		FriendlyURLLocalization existingFriendlyURLLocalization = _persistence.fetchByPrimaryKey(newFriendlyURLLocalization.getPrimaryKey());

		Assert.assertEquals(existingFriendlyURLLocalization,
			newFriendlyURLLocalization);
	}

	@Test
	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		FriendlyURLLocalization missingFriendlyURLLocalization = _persistence.fetchByPrimaryKey(pk);

		Assert.assertNull(missingFriendlyURLLocalization);
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereAllPrimaryKeysExist()
		throws Exception {
		FriendlyURLLocalization newFriendlyURLLocalization1 = addFriendlyURLLocalization();
		FriendlyURLLocalization newFriendlyURLLocalization2 = addFriendlyURLLocalization();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newFriendlyURLLocalization1.getPrimaryKey());
		primaryKeys.add(newFriendlyURLLocalization2.getPrimaryKey());

		Map<Serializable, FriendlyURLLocalization> friendlyURLLocalizations = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(2, friendlyURLLocalizations.size());
		Assert.assertEquals(newFriendlyURLLocalization1,
			friendlyURLLocalizations.get(
				newFriendlyURLLocalization1.getPrimaryKey()));
		Assert.assertEquals(newFriendlyURLLocalization2,
			friendlyURLLocalizations.get(
				newFriendlyURLLocalization2.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereNoPrimaryKeysExist()
		throws Exception {
		long pk1 = RandomTestUtil.nextLong();

		long pk2 = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(pk1);
		primaryKeys.add(pk2);

		Map<Serializable, FriendlyURLLocalization> friendlyURLLocalizations = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(friendlyURLLocalizations.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereSomePrimaryKeysExist()
		throws Exception {
		FriendlyURLLocalization newFriendlyURLLocalization = addFriendlyURLLocalization();

		long pk = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newFriendlyURLLocalization.getPrimaryKey());
		primaryKeys.add(pk);

		Map<Serializable, FriendlyURLLocalization> friendlyURLLocalizations = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, friendlyURLLocalizations.size());
		Assert.assertEquals(newFriendlyURLLocalization,
			friendlyURLLocalizations.get(
				newFriendlyURLLocalization.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithNoPrimaryKeys()
		throws Exception {
		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		Map<Serializable, FriendlyURLLocalization> friendlyURLLocalizations = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(friendlyURLLocalizations.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithOnePrimaryKey()
		throws Exception {
		FriendlyURLLocalization newFriendlyURLLocalization = addFriendlyURLLocalization();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newFriendlyURLLocalization.getPrimaryKey());

		Map<Serializable, FriendlyURLLocalization> friendlyURLLocalizations = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, friendlyURLLocalizations.size());
		Assert.assertEquals(newFriendlyURLLocalization,
			friendlyURLLocalizations.get(
				newFriendlyURLLocalization.getPrimaryKey()));
	}

	@Test
	public void testActionableDynamicQuery() throws Exception {
		final IntegerWrapper count = new IntegerWrapper();

		ActionableDynamicQuery actionableDynamicQuery = FriendlyURLLocalizationLocalServiceUtil.getActionableDynamicQuery();

		actionableDynamicQuery.setPerformActionMethod(new ActionableDynamicQuery.PerformActionMethod<FriendlyURLLocalization>() {
				@Override
				public void performAction(
					FriendlyURLLocalization friendlyURLLocalization) {
					Assert.assertNotNull(friendlyURLLocalization);

					count.increment();
				}
			});

		actionableDynamicQuery.performActions();

		Assert.assertEquals(count.getValue(), _persistence.countAll());
	}

	@Test
	public void testDynamicQueryByPrimaryKeyExisting()
		throws Exception {
		FriendlyURLLocalization newFriendlyURLLocalization = addFriendlyURLLocalization();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(FriendlyURLLocalization.class,
				_dynamicQueryClassLoader);

		dynamicQuery.add(RestrictionsFactoryUtil.eq(
				"friendlyURLLocalizationId",
				newFriendlyURLLocalization.getFriendlyURLLocalizationId()));

		List<FriendlyURLLocalization> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		FriendlyURLLocalization existingFriendlyURLLocalization = result.get(0);

		Assert.assertEquals(existingFriendlyURLLocalization,
			newFriendlyURLLocalization);
	}

	@Test
	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(FriendlyURLLocalization.class,
				_dynamicQueryClassLoader);

		dynamicQuery.add(RestrictionsFactoryUtil.eq(
				"friendlyURLLocalizationId", RandomTestUtil.nextLong()));

		List<FriendlyURLLocalization> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testDynamicQueryByProjectionExisting()
		throws Exception {
		FriendlyURLLocalization newFriendlyURLLocalization = addFriendlyURLLocalization();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(FriendlyURLLocalization.class,
				_dynamicQueryClassLoader);

		dynamicQuery.setProjection(ProjectionFactoryUtil.property(
				"friendlyURLLocalizationId"));

		Object newFriendlyURLLocalizationId = newFriendlyURLLocalization.getFriendlyURLLocalizationId();

		dynamicQuery.add(RestrictionsFactoryUtil.in(
				"friendlyURLLocalizationId",
				new Object[] { newFriendlyURLLocalizationId }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		Object existingFriendlyURLLocalizationId = result.get(0);

		Assert.assertEquals(existingFriendlyURLLocalizationId,
			newFriendlyURLLocalizationId);
	}

	@Test
	public void testDynamicQueryByProjectionMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(FriendlyURLLocalization.class,
				_dynamicQueryClassLoader);

		dynamicQuery.setProjection(ProjectionFactoryUtil.property(
				"friendlyURLLocalizationId"));

		dynamicQuery.add(RestrictionsFactoryUtil.in(
				"friendlyURLLocalizationId",
				new Object[] { RandomTestUtil.nextLong() }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testResetOriginalValues() throws Exception {
		FriendlyURLLocalization newFriendlyURLLocalization = addFriendlyURLLocalization();

		_persistence.clearCache();

		FriendlyURLLocalization existingFriendlyURLLocalization = _persistence.findByPrimaryKey(newFriendlyURLLocalization.getPrimaryKey());

		Assert.assertEquals(Long.valueOf(
				existingFriendlyURLLocalization.getGroupId()),
			ReflectionTestUtil.<Long>invoke(existingFriendlyURLLocalization,
				"getOriginalGroupId", new Class<?>[0]));
		Assert.assertEquals(Long.valueOf(
				existingFriendlyURLLocalization.getFriendlyURLId()),
			ReflectionTestUtil.<Long>invoke(existingFriendlyURLLocalization,
				"getOriginalFriendlyURLId", new Class<?>[0]));
		Assert.assertTrue(Objects.equals(
				existingFriendlyURLLocalization.getLanguageId(),
				ReflectionTestUtil.invoke(existingFriendlyURLLocalization,
					"getOriginalLanguageId", new Class<?>[0])));

		Assert.assertEquals(Long.valueOf(
				existingFriendlyURLLocalization.getGroupId()),
			ReflectionTestUtil.<Long>invoke(existingFriendlyURLLocalization,
				"getOriginalGroupId", new Class<?>[0]));
		Assert.assertTrue(Objects.equals(
				existingFriendlyURLLocalization.getUrlTitle(),
				ReflectionTestUtil.invoke(existingFriendlyURLLocalization,
					"getOriginalUrlTitle", new Class<?>[0])));
		Assert.assertTrue(Objects.equals(
				existingFriendlyURLLocalization.getLanguageId(),
				ReflectionTestUtil.invoke(existingFriendlyURLLocalization,
					"getOriginalLanguageId", new Class<?>[0])));
	}

	protected FriendlyURLLocalization addFriendlyURLLocalization()
		throws Exception {
		long pk = RandomTestUtil.nextLong();

		FriendlyURLLocalization friendlyURLLocalization = _persistence.create(pk);

		friendlyURLLocalization.setGroupId(RandomTestUtil.nextLong());

		friendlyURLLocalization.setCompanyId(RandomTestUtil.nextLong());

		friendlyURLLocalization.setFriendlyURLId(RandomTestUtil.nextLong());

		friendlyURLLocalization.setUrlTitle(RandomTestUtil.randomString());

		friendlyURLLocalization.setLanguageId(RandomTestUtil.randomString());

		_friendlyURLLocalizations.add(_persistence.update(
				friendlyURLLocalization));

		return friendlyURLLocalization;
	}

	private List<FriendlyURLLocalization> _friendlyURLLocalizations = new ArrayList<FriendlyURLLocalization>();
	private FriendlyURLLocalizationPersistence _persistence;
	private ClassLoader _dynamicQueryClassLoader;
}