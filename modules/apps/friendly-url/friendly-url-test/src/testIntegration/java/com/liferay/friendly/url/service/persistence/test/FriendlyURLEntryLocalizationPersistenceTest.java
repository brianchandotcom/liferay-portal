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
import com.liferay.friendly.url.exception.NoSuchFriendlyURLEntryLocalizationException;
import com.liferay.friendly.url.model.FriendlyURLEntryLocalization;
import com.liferay.friendly.url.service.persistence.FriendlyURLEntryLocalizationPersistence;
import com.liferay.friendly.url.service.persistence.FriendlyURLEntryLocalizationUtil;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQueryFactoryUtil;
import com.liferay.portal.kernel.dao.orm.ProjectionFactoryUtil;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.RestrictionsFactoryUtil;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.transaction.Propagation;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.OrderByComparatorFactoryUtil;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PersistenceTestRule;
import com.liferay.portal.test.rule.TransactionalTestRule;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
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
public class FriendlyURLEntryLocalizationPersistenceTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(), PersistenceTestRule.INSTANCE,
			new TransactionalTestRule(
				Propagation.REQUIRED, "com.liferay.friendly.url.service"));

	@Before
	public void setUp() {
		_persistence = FriendlyURLEntryLocalizationUtil.getPersistence();

		Class<?> clazz = _persistence.getClass();

		_dynamicQueryClassLoader = clazz.getClassLoader();
	}

	@After
	public void tearDown() throws Exception {
		Iterator<FriendlyURLEntryLocalization> iterator =
			_friendlyURLEntryLocalizations.iterator();

		while (iterator.hasNext()) {
			_persistence.remove(iterator.next());

			iterator.remove();
		}
	}

	@Test
	public void testCreate() throws Exception {
		long pk = RandomTestUtil.nextLong();

		FriendlyURLEntryLocalization friendlyURLEntryLocalization =
			_persistence.create(pk);

		Assert.assertNotNull(friendlyURLEntryLocalization);

		Assert.assertEquals(friendlyURLEntryLocalization.getPrimaryKey(), pk);
	}

	@Test
	public void testRemove() throws Exception {
		FriendlyURLEntryLocalization newFriendlyURLEntryLocalization =
			addFriendlyURLEntryLocalization();

		_persistence.remove(newFriendlyURLEntryLocalization);

		FriendlyURLEntryLocalization existingFriendlyURLEntryLocalization =
			_persistence.fetchByPrimaryKey(
				newFriendlyURLEntryLocalization.getPrimaryKey());

		Assert.assertNull(existingFriendlyURLEntryLocalization);
	}

	@Test
	public void testUpdateNew() throws Exception {
		addFriendlyURLEntryLocalization();
	}

	@Test
	public void testUpdateExisting() throws Exception {
		long pk = RandomTestUtil.nextLong();

		FriendlyURLEntryLocalization newFriendlyURLEntryLocalization =
			_persistence.create(pk);

		newFriendlyURLEntryLocalization.setMvccVersion(
			RandomTestUtil.nextLong());

		newFriendlyURLEntryLocalization.setCtCollectionId(
			RandomTestUtil.nextLong());

		newFriendlyURLEntryLocalization.setCompanyId(RandomTestUtil.nextLong());

		newFriendlyURLEntryLocalization.setFriendlyURLEntryId(
			RandomTestUtil.nextLong());

		newFriendlyURLEntryLocalization.setLanguageId(
			RandomTestUtil.randomString());

		newFriendlyURLEntryLocalization.setUrlTitle(
			RandomTestUtil.randomString());

		newFriendlyURLEntryLocalization.setGroupId(RandomTestUtil.nextLong());

		newFriendlyURLEntryLocalization.setClassNameId(
			RandomTestUtil.nextLong());

		newFriendlyURLEntryLocalization.setClassPK(RandomTestUtil.nextLong());

		_friendlyURLEntryLocalizations.add(
			_persistence.update(newFriendlyURLEntryLocalization));

		FriendlyURLEntryLocalization existingFriendlyURLEntryLocalization =
			_persistence.findByPrimaryKey(
				newFriendlyURLEntryLocalization.getPrimaryKey());

		Assert.assertEquals(
			existingFriendlyURLEntryLocalization.getMvccVersion(),
			newFriendlyURLEntryLocalization.getMvccVersion());
		Assert.assertEquals(
			existingFriendlyURLEntryLocalization.getCtCollectionId(),
			newFriendlyURLEntryLocalization.getCtCollectionId());
		Assert.assertEquals(
			existingFriendlyURLEntryLocalization.
				getFriendlyURLEntryLocalizationId(),
			newFriendlyURLEntryLocalization.
				getFriendlyURLEntryLocalizationId());
		Assert.assertEquals(
			existingFriendlyURLEntryLocalization.getCompanyId(),
			newFriendlyURLEntryLocalization.getCompanyId());
		Assert.assertEquals(
			existingFriendlyURLEntryLocalization.getFriendlyURLEntryId(),
			newFriendlyURLEntryLocalization.getFriendlyURLEntryId());
		Assert.assertEquals(
			existingFriendlyURLEntryLocalization.getLanguageId(),
			newFriendlyURLEntryLocalization.getLanguageId());
		Assert.assertEquals(
			existingFriendlyURLEntryLocalization.getUrlTitle(),
			newFriendlyURLEntryLocalization.getUrlTitle());
		Assert.assertEquals(
			existingFriendlyURLEntryLocalization.getGroupId(),
			newFriendlyURLEntryLocalization.getGroupId());
		Assert.assertEquals(
			existingFriendlyURLEntryLocalization.getClassNameId(),
			newFriendlyURLEntryLocalization.getClassNameId());
		Assert.assertEquals(
			existingFriendlyURLEntryLocalization.getClassPK(),
			newFriendlyURLEntryLocalization.getClassPK());
	}

	@Test
	public void testCountByFriendlyURLEntryId() throws Exception {
		_persistence.countByFriendlyURLEntryId(RandomTestUtil.nextLong());

		_persistence.countByFriendlyURLEntryId(0L);
	}

	@Test
	public void testCountByFriendlyURLEntryId_LanguageId() throws Exception {
		_persistence.countByFriendlyURLEntryId_LanguageId(
			RandomTestUtil.nextLong(), "");

		_persistence.countByFriendlyURLEntryId_LanguageId(0L, "null");

		_persistence.countByFriendlyURLEntryId_LanguageId(0L, (String)null);
	}

	@Test
	public void testCountByG_C_U() throws Exception {
		_persistence.countByG_C_U(
			RandomTestUtil.nextLong(), RandomTestUtil.nextLong(), "");

		_persistence.countByG_C_U(0L, 0L, "null");

		_persistence.countByG_C_U(0L, 0L, (String)null);
	}

	@Test
	public void testFindByPrimaryKeyExisting() throws Exception {
		FriendlyURLEntryLocalization newFriendlyURLEntryLocalization =
			addFriendlyURLEntryLocalization();

		FriendlyURLEntryLocalization existingFriendlyURLEntryLocalization =
			_persistence.findByPrimaryKey(
				newFriendlyURLEntryLocalization.getPrimaryKey());

		Assert.assertEquals(
			existingFriendlyURLEntryLocalization,
			newFriendlyURLEntryLocalization);
	}

	@Test(expected = NoSuchFriendlyURLEntryLocalizationException.class)
	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		_persistence.findByPrimaryKey(pk);
	}

	@Test
	public void testFindAll() throws Exception {
		_persistence.findAll(
			QueryUtil.ALL_POS, QueryUtil.ALL_POS, getOrderByComparator());
	}

	protected OrderByComparator<FriendlyURLEntryLocalization>
		getOrderByComparator() {

		return OrderByComparatorFactoryUtil.create(
			"FriendlyURLEntryLocalization", "mvccVersion", true,
			"ctCollectionId", true, "friendlyURLEntryLocalizationId", true,
			"companyId", true, "friendlyURLEntryId", true, "languageId", true,
			"urlTitle", true, "groupId", true, "classNameId", true, "classPK",
			true);
	}

	@Test
	public void testFetchByPrimaryKeyExisting() throws Exception {
		FriendlyURLEntryLocalization newFriendlyURLEntryLocalization =
			addFriendlyURLEntryLocalization();

		FriendlyURLEntryLocalization existingFriendlyURLEntryLocalization =
			_persistence.fetchByPrimaryKey(
				newFriendlyURLEntryLocalization.getPrimaryKey());

		Assert.assertEquals(
			existingFriendlyURLEntryLocalization,
			newFriendlyURLEntryLocalization);
	}

	@Test
	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		FriendlyURLEntryLocalization missingFriendlyURLEntryLocalization =
			_persistence.fetchByPrimaryKey(pk);

		Assert.assertNull(missingFriendlyURLEntryLocalization);
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereAllPrimaryKeysExist()
		throws Exception {

		FriendlyURLEntryLocalization newFriendlyURLEntryLocalization1 =
			addFriendlyURLEntryLocalization();
		FriendlyURLEntryLocalization newFriendlyURLEntryLocalization2 =
			addFriendlyURLEntryLocalization();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newFriendlyURLEntryLocalization1.getPrimaryKey());
		primaryKeys.add(newFriendlyURLEntryLocalization2.getPrimaryKey());

		Map<Serializable, FriendlyURLEntryLocalization>
			friendlyURLEntryLocalizations = _persistence.fetchByPrimaryKeys(
				primaryKeys);

		Assert.assertEquals(2, friendlyURLEntryLocalizations.size());
		Assert.assertEquals(
			newFriendlyURLEntryLocalization1,
			friendlyURLEntryLocalizations.get(
				newFriendlyURLEntryLocalization1.getPrimaryKey()));
		Assert.assertEquals(
			newFriendlyURLEntryLocalization2,
			friendlyURLEntryLocalizations.get(
				newFriendlyURLEntryLocalization2.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereNoPrimaryKeysExist()
		throws Exception {

		long pk1 = RandomTestUtil.nextLong();

		long pk2 = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(pk1);
		primaryKeys.add(pk2);

		Map<Serializable, FriendlyURLEntryLocalization>
			friendlyURLEntryLocalizations = _persistence.fetchByPrimaryKeys(
				primaryKeys);

		Assert.assertTrue(friendlyURLEntryLocalizations.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereSomePrimaryKeysExist()
		throws Exception {

		FriendlyURLEntryLocalization newFriendlyURLEntryLocalization =
			addFriendlyURLEntryLocalization();

		long pk = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newFriendlyURLEntryLocalization.getPrimaryKey());
		primaryKeys.add(pk);

		Map<Serializable, FriendlyURLEntryLocalization>
			friendlyURLEntryLocalizations = _persistence.fetchByPrimaryKeys(
				primaryKeys);

		Assert.assertEquals(1, friendlyURLEntryLocalizations.size());
		Assert.assertEquals(
			newFriendlyURLEntryLocalization,
			friendlyURLEntryLocalizations.get(
				newFriendlyURLEntryLocalization.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithNoPrimaryKeys() throws Exception {
		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		Map<Serializable, FriendlyURLEntryLocalization>
			friendlyURLEntryLocalizations = _persistence.fetchByPrimaryKeys(
				primaryKeys);

		Assert.assertTrue(friendlyURLEntryLocalizations.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithOnePrimaryKey() throws Exception {
		FriendlyURLEntryLocalization newFriendlyURLEntryLocalization =
			addFriendlyURLEntryLocalization();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newFriendlyURLEntryLocalization.getPrimaryKey());

		Map<Serializable, FriendlyURLEntryLocalization>
			friendlyURLEntryLocalizations = _persistence.fetchByPrimaryKeys(
				primaryKeys);

		Assert.assertEquals(1, friendlyURLEntryLocalizations.size());
		Assert.assertEquals(
			newFriendlyURLEntryLocalization,
			friendlyURLEntryLocalizations.get(
				newFriendlyURLEntryLocalization.getPrimaryKey()));
	}

	@Test
	public void testDynamicQueryByPrimaryKeyExisting() throws Exception {
		FriendlyURLEntryLocalization newFriendlyURLEntryLocalization =
			addFriendlyURLEntryLocalization();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			FriendlyURLEntryLocalization.class, _dynamicQueryClassLoader);

		dynamicQuery.add(
			RestrictionsFactoryUtil.eq(
				"friendlyURLEntryLocalizationId",
				newFriendlyURLEntryLocalization.
					getFriendlyURLEntryLocalizationId()));

		List<FriendlyURLEntryLocalization> result =
			_persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		FriendlyURLEntryLocalization existingFriendlyURLEntryLocalization =
			result.get(0);

		Assert.assertEquals(
			existingFriendlyURLEntryLocalization,
			newFriendlyURLEntryLocalization);
	}

	@Test
	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			FriendlyURLEntryLocalization.class, _dynamicQueryClassLoader);

		dynamicQuery.add(
			RestrictionsFactoryUtil.eq(
				"friendlyURLEntryLocalizationId", RandomTestUtil.nextLong()));

		List<FriendlyURLEntryLocalization> result =
			_persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testDynamicQueryByProjectionExisting() throws Exception {
		FriendlyURLEntryLocalization newFriendlyURLEntryLocalization =
			addFriendlyURLEntryLocalization();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			FriendlyURLEntryLocalization.class, _dynamicQueryClassLoader);

		dynamicQuery.setProjection(
			ProjectionFactoryUtil.property("friendlyURLEntryLocalizationId"));

		Object newFriendlyURLEntryLocalizationId =
			newFriendlyURLEntryLocalization.getFriendlyURLEntryLocalizationId();

		dynamicQuery.add(
			RestrictionsFactoryUtil.in(
				"friendlyURLEntryLocalizationId",
				new Object[] {newFriendlyURLEntryLocalizationId}));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		Object existingFriendlyURLEntryLocalizationId = result.get(0);

		Assert.assertEquals(
			existingFriendlyURLEntryLocalizationId,
			newFriendlyURLEntryLocalizationId);
	}

	@Test
	public void testDynamicQueryByProjectionMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			FriendlyURLEntryLocalization.class, _dynamicQueryClassLoader);

		dynamicQuery.setProjection(
			ProjectionFactoryUtil.property("friendlyURLEntryLocalizationId"));

		dynamicQuery.add(
			RestrictionsFactoryUtil.in(
				"friendlyURLEntryLocalizationId",
				new Object[] {RandomTestUtil.nextLong()}));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testResetOriginalValues() throws Exception {
		FriendlyURLEntryLocalization newFriendlyURLEntryLocalization =
			addFriendlyURLEntryLocalization();

		_persistence.clearCache();

		FriendlyURLEntryLocalization existingFriendlyURLEntryLocalization =
			_persistence.findByPrimaryKey(
				newFriendlyURLEntryLocalization.getPrimaryKey());

		Assert.assertEquals(
			Long.valueOf(
				existingFriendlyURLEntryLocalization.getFriendlyURLEntryId()),
			ReflectionTestUtil.<Long>invoke(
				existingFriendlyURLEntryLocalization,
				"getOriginalFriendlyURLEntryId", new Class<?>[0]));
		Assert.assertTrue(
			Objects.equals(
				existingFriendlyURLEntryLocalization.getLanguageId(),
				ReflectionTestUtil.invoke(
					existingFriendlyURLEntryLocalization,
					"getOriginalLanguageId", new Class<?>[0])));

		Assert.assertEquals(
			Long.valueOf(existingFriendlyURLEntryLocalization.getGroupId()),
			ReflectionTestUtil.<Long>invoke(
				existingFriendlyURLEntryLocalization, "getOriginalGroupId",
				new Class<?>[0]));
		Assert.assertEquals(
			Long.valueOf(existingFriendlyURLEntryLocalization.getClassNameId()),
			ReflectionTestUtil.<Long>invoke(
				existingFriendlyURLEntryLocalization, "getOriginalClassNameId",
				new Class<?>[0]));
		Assert.assertTrue(
			Objects.equals(
				existingFriendlyURLEntryLocalization.getUrlTitle(),
				ReflectionTestUtil.invoke(
					existingFriendlyURLEntryLocalization, "getOriginalUrlTitle",
					new Class<?>[0])));
	}

	protected FriendlyURLEntryLocalization addFriendlyURLEntryLocalization()
		throws Exception {

		long pk = RandomTestUtil.nextLong();

		FriendlyURLEntryLocalization friendlyURLEntryLocalization =
			_persistence.create(pk);

		friendlyURLEntryLocalization.setMvccVersion(RandomTestUtil.nextLong());

		friendlyURLEntryLocalization.setCtCollectionId(
			RandomTestUtil.nextLong());

		friendlyURLEntryLocalization.setCompanyId(RandomTestUtil.nextLong());

		friendlyURLEntryLocalization.setFriendlyURLEntryId(
			RandomTestUtil.nextLong());

		friendlyURLEntryLocalization.setLanguageId(
			RandomTestUtil.randomString());

		friendlyURLEntryLocalization.setUrlTitle(RandomTestUtil.randomString());

		friendlyURLEntryLocalization.setGroupId(RandomTestUtil.nextLong());

		friendlyURLEntryLocalization.setClassNameId(RandomTestUtil.nextLong());

		friendlyURLEntryLocalization.setClassPK(RandomTestUtil.nextLong());

		_friendlyURLEntryLocalizations.add(
			_persistence.update(friendlyURLEntryLocalization));

		return friendlyURLEntryLocalization;
	}

	private List<FriendlyURLEntryLocalization> _friendlyURLEntryLocalizations =
		new ArrayList<FriendlyURLEntryLocalization>();
	private FriendlyURLEntryLocalizationPersistence _persistence;
	private ClassLoader _dynamicQueryClassLoader;

}