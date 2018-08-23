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

package com.liferay.asset.list.service.persistence.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;

import com.liferay.asset.list.exception.NoSuchEntryLocalizationException;
import com.liferay.asset.list.model.AssetListEntryLocalization;
import com.liferay.asset.list.service.persistence.AssetListEntryLocalizationPersistence;
import com.liferay.asset.list.service.persistence.AssetListEntryLocalizationUtil;

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
public class AssetListEntryLocalizationPersistenceTest {
	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule = new AggregateTestRule(new LiferayIntegrationTestRule(),
			PersistenceTestRule.INSTANCE,
			new TransactionalTestRule(Propagation.REQUIRED,
				"com.liferay.asset.list.service"));

	@Before
	public void setUp() {
		_persistence = AssetListEntryLocalizationUtil.getPersistence();

		Class<?> clazz = _persistence.getClass();

		_dynamicQueryClassLoader = clazz.getClassLoader();
	}

	@After
	public void tearDown() throws Exception {
		Iterator<AssetListEntryLocalization> iterator = _assetListEntryLocalizations.iterator();

		while (iterator.hasNext()) {
			_persistence.remove(iterator.next());

			iterator.remove();
		}
	}

	@Test
	public void testCreate() throws Exception {
		long pk = RandomTestUtil.nextLong();

		AssetListEntryLocalization assetListEntryLocalization = _persistence.create(pk);

		Assert.assertNotNull(assetListEntryLocalization);

		Assert.assertEquals(assetListEntryLocalization.getPrimaryKey(), pk);
	}

	@Test
	public void testRemove() throws Exception {
		AssetListEntryLocalization newAssetListEntryLocalization = addAssetListEntryLocalization();

		_persistence.remove(newAssetListEntryLocalization);

		AssetListEntryLocalization existingAssetListEntryLocalization = _persistence.fetchByPrimaryKey(newAssetListEntryLocalization.getPrimaryKey());

		Assert.assertNull(existingAssetListEntryLocalization);
	}

	@Test
	public void testUpdateNew() throws Exception {
		addAssetListEntryLocalization();
	}

	@Test
	public void testUpdateExisting() throws Exception {
		long pk = RandomTestUtil.nextLong();

		AssetListEntryLocalization newAssetListEntryLocalization = _persistence.create(pk);

		newAssetListEntryLocalization.setMvccVersion(RandomTestUtil.nextLong());

		newAssetListEntryLocalization.setCompanyId(RandomTestUtil.nextLong());

		newAssetListEntryLocalization.setAssetListEntryId(RandomTestUtil.nextLong());

		newAssetListEntryLocalization.setLanguageId(RandomTestUtil.randomString());

		newAssetListEntryLocalization.setTitle(RandomTestUtil.randomString());

		newAssetListEntryLocalization.setDescription(RandomTestUtil.randomString());

		newAssetListEntryLocalization.setGroupId(RandomTestUtil.nextLong());

		_assetListEntryLocalizations.add(_persistence.update(
				newAssetListEntryLocalization));

		AssetListEntryLocalization existingAssetListEntryLocalization = _persistence.findByPrimaryKey(newAssetListEntryLocalization.getPrimaryKey());

		Assert.assertEquals(existingAssetListEntryLocalization.getMvccVersion(),
			newAssetListEntryLocalization.getMvccVersion());
		Assert.assertEquals(existingAssetListEntryLocalization.getAssetListEntryLocalizationId(),
			newAssetListEntryLocalization.getAssetListEntryLocalizationId());
		Assert.assertEquals(existingAssetListEntryLocalization.getCompanyId(),
			newAssetListEntryLocalization.getCompanyId());
		Assert.assertEquals(existingAssetListEntryLocalization.getAssetListEntryId(),
			newAssetListEntryLocalization.getAssetListEntryId());
		Assert.assertEquals(existingAssetListEntryLocalization.getLanguageId(),
			newAssetListEntryLocalization.getLanguageId());
		Assert.assertEquals(existingAssetListEntryLocalization.getTitle(),
			newAssetListEntryLocalization.getTitle());
		Assert.assertEquals(existingAssetListEntryLocalization.getDescription(),
			newAssetListEntryLocalization.getDescription());
		Assert.assertEquals(existingAssetListEntryLocalization.getGroupId(),
			newAssetListEntryLocalization.getGroupId());
	}

	@Test
	public void testCountByAssetListEntryId() throws Exception {
		_persistence.countByAssetListEntryId(RandomTestUtil.nextLong());

		_persistence.countByAssetListEntryId(0L);
	}

	@Test
	public void testCountByAssetListEntryId_LanguageId()
		throws Exception {
		_persistence.countByAssetListEntryId_LanguageId(RandomTestUtil.nextLong(),
			"");

		_persistence.countByAssetListEntryId_LanguageId(0L, "null");

		_persistence.countByAssetListEntryId_LanguageId(0L, (String)null);
	}

	@Test
	public void testCountByG_LikeT() throws Exception {
		_persistence.countByG_LikeT(RandomTestUtil.nextLong(), "");

		_persistence.countByG_LikeT(0L, "null");

		_persistence.countByG_LikeT(0L, (String)null);
	}

	@Test
	public void testFindByPrimaryKeyExisting() throws Exception {
		AssetListEntryLocalization newAssetListEntryLocalization = addAssetListEntryLocalization();

		AssetListEntryLocalization existingAssetListEntryLocalization = _persistence.findByPrimaryKey(newAssetListEntryLocalization.getPrimaryKey());

		Assert.assertEquals(existingAssetListEntryLocalization,
			newAssetListEntryLocalization);
	}

	@Test(expected = NoSuchEntryLocalizationException.class)
	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		_persistence.findByPrimaryKey(pk);
	}

	@Test
	public void testFindAll() throws Exception {
		_persistence.findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			getOrderByComparator());
	}

	protected OrderByComparator<AssetListEntryLocalization> getOrderByComparator() {
		return OrderByComparatorFactoryUtil.create("AssetListEntryLocalization",
			"mvccVersion", true, "assetListEntryLocalizationId", true,
			"companyId", true, "assetListEntryId", true, "languageId", true,
			"title", true, "description", true, "groupId", true);
	}

	@Test
	public void testFetchByPrimaryKeyExisting() throws Exception {
		AssetListEntryLocalization newAssetListEntryLocalization = addAssetListEntryLocalization();

		AssetListEntryLocalization existingAssetListEntryLocalization = _persistence.fetchByPrimaryKey(newAssetListEntryLocalization.getPrimaryKey());

		Assert.assertEquals(existingAssetListEntryLocalization,
			newAssetListEntryLocalization);
	}

	@Test
	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		AssetListEntryLocalization missingAssetListEntryLocalization = _persistence.fetchByPrimaryKey(pk);

		Assert.assertNull(missingAssetListEntryLocalization);
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereAllPrimaryKeysExist()
		throws Exception {
		AssetListEntryLocalization newAssetListEntryLocalization1 = addAssetListEntryLocalization();
		AssetListEntryLocalization newAssetListEntryLocalization2 = addAssetListEntryLocalization();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newAssetListEntryLocalization1.getPrimaryKey());
		primaryKeys.add(newAssetListEntryLocalization2.getPrimaryKey());

		Map<Serializable, AssetListEntryLocalization> assetListEntryLocalizations =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(2, assetListEntryLocalizations.size());
		Assert.assertEquals(newAssetListEntryLocalization1,
			assetListEntryLocalizations.get(
				newAssetListEntryLocalization1.getPrimaryKey()));
		Assert.assertEquals(newAssetListEntryLocalization2,
			assetListEntryLocalizations.get(
				newAssetListEntryLocalization2.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereNoPrimaryKeysExist()
		throws Exception {
		long pk1 = RandomTestUtil.nextLong();

		long pk2 = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(pk1);
		primaryKeys.add(pk2);

		Map<Serializable, AssetListEntryLocalization> assetListEntryLocalizations =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(assetListEntryLocalizations.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereSomePrimaryKeysExist()
		throws Exception {
		AssetListEntryLocalization newAssetListEntryLocalization = addAssetListEntryLocalization();

		long pk = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newAssetListEntryLocalization.getPrimaryKey());
		primaryKeys.add(pk);

		Map<Serializable, AssetListEntryLocalization> assetListEntryLocalizations =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, assetListEntryLocalizations.size());
		Assert.assertEquals(newAssetListEntryLocalization,
			assetListEntryLocalizations.get(
				newAssetListEntryLocalization.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithNoPrimaryKeys()
		throws Exception {
		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		Map<Serializable, AssetListEntryLocalization> assetListEntryLocalizations =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(assetListEntryLocalizations.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithOnePrimaryKey()
		throws Exception {
		AssetListEntryLocalization newAssetListEntryLocalization = addAssetListEntryLocalization();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newAssetListEntryLocalization.getPrimaryKey());

		Map<Serializable, AssetListEntryLocalization> assetListEntryLocalizations =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, assetListEntryLocalizations.size());
		Assert.assertEquals(newAssetListEntryLocalization,
			assetListEntryLocalizations.get(
				newAssetListEntryLocalization.getPrimaryKey()));
	}

	@Test
	public void testDynamicQueryByPrimaryKeyExisting()
		throws Exception {
		AssetListEntryLocalization newAssetListEntryLocalization = addAssetListEntryLocalization();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(AssetListEntryLocalization.class,
				_dynamicQueryClassLoader);

		dynamicQuery.add(RestrictionsFactoryUtil.eq(
				"assetListEntryLocalizationId",
				newAssetListEntryLocalization.getAssetListEntryLocalizationId()));

		List<AssetListEntryLocalization> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		AssetListEntryLocalization existingAssetListEntryLocalization = result.get(0);

		Assert.assertEquals(existingAssetListEntryLocalization,
			newAssetListEntryLocalization);
	}

	@Test
	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(AssetListEntryLocalization.class,
				_dynamicQueryClassLoader);

		dynamicQuery.add(RestrictionsFactoryUtil.eq(
				"assetListEntryLocalizationId", RandomTestUtil.nextLong()));

		List<AssetListEntryLocalization> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testDynamicQueryByProjectionExisting()
		throws Exception {
		AssetListEntryLocalization newAssetListEntryLocalization = addAssetListEntryLocalization();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(AssetListEntryLocalization.class,
				_dynamicQueryClassLoader);

		dynamicQuery.setProjection(ProjectionFactoryUtil.property(
				"assetListEntryLocalizationId"));

		Object newAssetListEntryLocalizationId = newAssetListEntryLocalization.getAssetListEntryLocalizationId();

		dynamicQuery.add(RestrictionsFactoryUtil.in(
				"assetListEntryLocalizationId",
				new Object[] { newAssetListEntryLocalizationId }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		Object existingAssetListEntryLocalizationId = result.get(0);

		Assert.assertEquals(existingAssetListEntryLocalizationId,
			newAssetListEntryLocalizationId);
	}

	@Test
	public void testDynamicQueryByProjectionMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(AssetListEntryLocalization.class,
				_dynamicQueryClassLoader);

		dynamicQuery.setProjection(ProjectionFactoryUtil.property(
				"assetListEntryLocalizationId"));

		dynamicQuery.add(RestrictionsFactoryUtil.in(
				"assetListEntryLocalizationId",
				new Object[] { RandomTestUtil.nextLong() }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testResetOriginalValues() throws Exception {
		AssetListEntryLocalization newAssetListEntryLocalization = addAssetListEntryLocalization();

		_persistence.clearCache();

		AssetListEntryLocalization existingAssetListEntryLocalization = _persistence.findByPrimaryKey(newAssetListEntryLocalization.getPrimaryKey());

		Assert.assertEquals(Long.valueOf(
				existingAssetListEntryLocalization.getAssetListEntryId()),
			ReflectionTestUtil.<Long>invoke(
				existingAssetListEntryLocalization,
				"getOriginalAssetListEntryId", new Class<?>[0]));
		Assert.assertTrue(Objects.equals(
				existingAssetListEntryLocalization.getLanguageId(),
				ReflectionTestUtil.invoke(existingAssetListEntryLocalization,
					"getOriginalLanguageId", new Class<?>[0])));
	}

	protected AssetListEntryLocalization addAssetListEntryLocalization()
		throws Exception {
		long pk = RandomTestUtil.nextLong();

		AssetListEntryLocalization assetListEntryLocalization = _persistence.create(pk);

		assetListEntryLocalization.setMvccVersion(RandomTestUtil.nextLong());

		assetListEntryLocalization.setCompanyId(RandomTestUtil.nextLong());

		assetListEntryLocalization.setAssetListEntryId(RandomTestUtil.nextLong());

		assetListEntryLocalization.setLanguageId(RandomTestUtil.randomString());

		assetListEntryLocalization.setTitle(RandomTestUtil.randomString());

		assetListEntryLocalization.setDescription(RandomTestUtil.randomString());

		assetListEntryLocalization.setGroupId(RandomTestUtil.nextLong());

		_assetListEntryLocalizations.add(_persistence.update(
				assetListEntryLocalization));

		return assetListEntryLocalization;
	}

	private List<AssetListEntryLocalization> _assetListEntryLocalizations = new ArrayList<AssetListEntryLocalization>();
	private AssetListEntryLocalizationPersistence _persistence;
	private ClassLoader _dynamicQueryClassLoader;
}