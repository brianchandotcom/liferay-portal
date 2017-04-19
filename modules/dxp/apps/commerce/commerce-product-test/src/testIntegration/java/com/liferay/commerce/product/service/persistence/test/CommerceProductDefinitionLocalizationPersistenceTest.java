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

package com.liferay.commerce.product.service.persistence.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;

import com.liferay.commerce.product.exception.NoSuchProductDefinitionLocalizationException;
import com.liferay.commerce.product.model.CommerceProductDefinitionLocalization;
import com.liferay.commerce.product.service.persistence.CommerceProductDefinitionLocalizationPersistence;
import com.liferay.commerce.product.service.persistence.CommerceProductDefinitionLocalizationUtil;

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
public class CommerceProductDefinitionLocalizationPersistenceTest {
	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule = new AggregateTestRule(new LiferayIntegrationTestRule(),
			PersistenceTestRule.INSTANCE,
			new TransactionalTestRule(Propagation.REQUIRED,
				"com.liferay.commerce.product.service"));

	@Before
	public void setUp() {
		_persistence = CommerceProductDefinitionLocalizationUtil.getPersistence();

		Class<?> clazz = _persistence.getClass();

		_dynamicQueryClassLoader = clazz.getClassLoader();
	}

	@After
	public void tearDown() throws Exception {
		Iterator<CommerceProductDefinitionLocalization> iterator = _commerceProductDefinitionLocalizations.iterator();

		while (iterator.hasNext()) {
			_persistence.remove(iterator.next());

			iterator.remove();
		}
	}

	@Test
	public void testCreate() throws Exception {
		long pk = RandomTestUtil.nextLong();

		CommerceProductDefinitionLocalization commerceProductDefinitionLocalization =
			_persistence.create(pk);

		Assert.assertNotNull(commerceProductDefinitionLocalization);

		Assert.assertEquals(commerceProductDefinitionLocalization.getPrimaryKey(),
			pk);
	}

	@Test
	public void testRemove() throws Exception {
		CommerceProductDefinitionLocalization newCommerceProductDefinitionLocalization =
			addCommerceProductDefinitionLocalization();

		_persistence.remove(newCommerceProductDefinitionLocalization);

		CommerceProductDefinitionLocalization existingCommerceProductDefinitionLocalization =
			_persistence.fetchByPrimaryKey(newCommerceProductDefinitionLocalization.getPrimaryKey());

		Assert.assertNull(existingCommerceProductDefinitionLocalization);
	}

	@Test
	public void testUpdateNew() throws Exception {
		addCommerceProductDefinitionLocalization();
	}

	@Test
	public void testUpdateExisting() throws Exception {
		long pk = RandomTestUtil.nextLong();

		CommerceProductDefinitionLocalization newCommerceProductDefinitionLocalization =
			_persistence.create(pk);

		newCommerceProductDefinitionLocalization.setMvccVersion(RandomTestUtil.nextLong());

		newCommerceProductDefinitionLocalization.setCompanyId(RandomTestUtil.nextLong());

		newCommerceProductDefinitionLocalization.setCommerceProductDefinitionPK(RandomTestUtil.nextLong());

		newCommerceProductDefinitionLocalization.setLanguageId(RandomTestUtil.randomString());

		newCommerceProductDefinitionLocalization.setTitle(RandomTestUtil.randomString());

		newCommerceProductDefinitionLocalization.setUrlTitle(RandomTestUtil.randomString());

		newCommerceProductDefinitionLocalization.setDescription(RandomTestUtil.randomString());

		_commerceProductDefinitionLocalizations.add(_persistence.update(
				newCommerceProductDefinitionLocalization));

		CommerceProductDefinitionLocalization existingCommerceProductDefinitionLocalization =
			_persistence.findByPrimaryKey(newCommerceProductDefinitionLocalization.getPrimaryKey());

		Assert.assertEquals(existingCommerceProductDefinitionLocalization.getMvccVersion(),
			newCommerceProductDefinitionLocalization.getMvccVersion());
		Assert.assertEquals(existingCommerceProductDefinitionLocalization.getCommerceProductDefinitionLocalizationId(),
			newCommerceProductDefinitionLocalization.getCommerceProductDefinitionLocalizationId());
		Assert.assertEquals(existingCommerceProductDefinitionLocalization.getCompanyId(),
			newCommerceProductDefinitionLocalization.getCompanyId());
		Assert.assertEquals(existingCommerceProductDefinitionLocalization.getCommerceProductDefinitionPK(),
			newCommerceProductDefinitionLocalization.getCommerceProductDefinitionPK());
		Assert.assertEquals(existingCommerceProductDefinitionLocalization.getLanguageId(),
			newCommerceProductDefinitionLocalization.getLanguageId());
		Assert.assertEquals(existingCommerceProductDefinitionLocalization.getTitle(),
			newCommerceProductDefinitionLocalization.getTitle());
		Assert.assertEquals(existingCommerceProductDefinitionLocalization.getUrlTitle(),
			newCommerceProductDefinitionLocalization.getUrlTitle());
		Assert.assertEquals(existingCommerceProductDefinitionLocalization.getDescription(),
			newCommerceProductDefinitionLocalization.getDescription());
	}

	@Test
	public void testCountByCommerceProductDefinitionPK()
		throws Exception {
		_persistence.countByCommerceProductDefinitionPK(RandomTestUtil.nextLong());

		_persistence.countByCommerceProductDefinitionPK(0L);
	}

	@Test
	public void testCountByCPD_L() throws Exception {
		_persistence.countByCPD_L(RandomTestUtil.nextLong(), StringPool.BLANK);

		_persistence.countByCPD_L(0L, StringPool.NULL);

		_persistence.countByCPD_L(0L, (String)null);
	}

	@Test
	public void testFindByPrimaryKeyExisting() throws Exception {
		CommerceProductDefinitionLocalization newCommerceProductDefinitionLocalization =
			addCommerceProductDefinitionLocalization();

		CommerceProductDefinitionLocalization existingCommerceProductDefinitionLocalization =
			_persistence.findByPrimaryKey(newCommerceProductDefinitionLocalization.getPrimaryKey());

		Assert.assertEquals(existingCommerceProductDefinitionLocalization,
			newCommerceProductDefinitionLocalization);
	}

	@Test(expected = NoSuchProductDefinitionLocalizationException.class)
	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		_persistence.findByPrimaryKey(pk);
	}

	@Test
	public void testFindAll() throws Exception {
		_persistence.findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			getOrderByComparator());
	}

	protected OrderByComparator<CommerceProductDefinitionLocalization> getOrderByComparator() {
		return OrderByComparatorFactoryUtil.create("CommerceProductDefinitionLocalization",
			"mvccVersion", true, "commerceProductDefinitionLocalizationId",
			true, "companyId", true, "commerceProductDefinitionPK", true,
			"languageId", true, "title", true, "urlTitle", true, "description",
			true);
	}

	@Test
	public void testFetchByPrimaryKeyExisting() throws Exception {
		CommerceProductDefinitionLocalization newCommerceProductDefinitionLocalization =
			addCommerceProductDefinitionLocalization();

		CommerceProductDefinitionLocalization existingCommerceProductDefinitionLocalization =
			_persistence.fetchByPrimaryKey(newCommerceProductDefinitionLocalization.getPrimaryKey());

		Assert.assertEquals(existingCommerceProductDefinitionLocalization,
			newCommerceProductDefinitionLocalization);
	}

	@Test
	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		CommerceProductDefinitionLocalization missingCommerceProductDefinitionLocalization =
			_persistence.fetchByPrimaryKey(pk);

		Assert.assertNull(missingCommerceProductDefinitionLocalization);
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereAllPrimaryKeysExist()
		throws Exception {
		CommerceProductDefinitionLocalization newCommerceProductDefinitionLocalization1 =
			addCommerceProductDefinitionLocalization();
		CommerceProductDefinitionLocalization newCommerceProductDefinitionLocalization2 =
			addCommerceProductDefinitionLocalization();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newCommerceProductDefinitionLocalization1.getPrimaryKey());
		primaryKeys.add(newCommerceProductDefinitionLocalization2.getPrimaryKey());

		Map<Serializable, CommerceProductDefinitionLocalization> commerceProductDefinitionLocalizations =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(2, commerceProductDefinitionLocalizations.size());
		Assert.assertEquals(newCommerceProductDefinitionLocalization1,
			commerceProductDefinitionLocalizations.get(
				newCommerceProductDefinitionLocalization1.getPrimaryKey()));
		Assert.assertEquals(newCommerceProductDefinitionLocalization2,
			commerceProductDefinitionLocalizations.get(
				newCommerceProductDefinitionLocalization2.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereNoPrimaryKeysExist()
		throws Exception {
		long pk1 = RandomTestUtil.nextLong();

		long pk2 = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(pk1);
		primaryKeys.add(pk2);

		Map<Serializable, CommerceProductDefinitionLocalization> commerceProductDefinitionLocalizations =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(commerceProductDefinitionLocalizations.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereSomePrimaryKeysExist()
		throws Exception {
		CommerceProductDefinitionLocalization newCommerceProductDefinitionLocalization =
			addCommerceProductDefinitionLocalization();

		long pk = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newCommerceProductDefinitionLocalization.getPrimaryKey());
		primaryKeys.add(pk);

		Map<Serializable, CommerceProductDefinitionLocalization> commerceProductDefinitionLocalizations =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, commerceProductDefinitionLocalizations.size());
		Assert.assertEquals(newCommerceProductDefinitionLocalization,
			commerceProductDefinitionLocalizations.get(
				newCommerceProductDefinitionLocalization.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithNoPrimaryKeys()
		throws Exception {
		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		Map<Serializable, CommerceProductDefinitionLocalization> commerceProductDefinitionLocalizations =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(commerceProductDefinitionLocalizations.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithOnePrimaryKey()
		throws Exception {
		CommerceProductDefinitionLocalization newCommerceProductDefinitionLocalization =
			addCommerceProductDefinitionLocalization();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newCommerceProductDefinitionLocalization.getPrimaryKey());

		Map<Serializable, CommerceProductDefinitionLocalization> commerceProductDefinitionLocalizations =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, commerceProductDefinitionLocalizations.size());
		Assert.assertEquals(newCommerceProductDefinitionLocalization,
			commerceProductDefinitionLocalizations.get(
				newCommerceProductDefinitionLocalization.getPrimaryKey()));
	}

	@Test
	public void testDynamicQueryByPrimaryKeyExisting()
		throws Exception {
		CommerceProductDefinitionLocalization newCommerceProductDefinitionLocalization =
			addCommerceProductDefinitionLocalization();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(CommerceProductDefinitionLocalization.class,
				_dynamicQueryClassLoader);

		dynamicQuery.add(RestrictionsFactoryUtil.eq(
				"commerceProductDefinitionLocalizationId",
				newCommerceProductDefinitionLocalization.getCommerceProductDefinitionLocalizationId()));

		List<CommerceProductDefinitionLocalization> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		CommerceProductDefinitionLocalization existingCommerceProductDefinitionLocalization =
			result.get(0);

		Assert.assertEquals(existingCommerceProductDefinitionLocalization,
			newCommerceProductDefinitionLocalization);
	}

	@Test
	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(CommerceProductDefinitionLocalization.class,
				_dynamicQueryClassLoader);

		dynamicQuery.add(RestrictionsFactoryUtil.eq(
				"commerceProductDefinitionLocalizationId",
				RandomTestUtil.nextLong()));

		List<CommerceProductDefinitionLocalization> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testDynamicQueryByProjectionExisting()
		throws Exception {
		CommerceProductDefinitionLocalization newCommerceProductDefinitionLocalization =
			addCommerceProductDefinitionLocalization();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(CommerceProductDefinitionLocalization.class,
				_dynamicQueryClassLoader);

		dynamicQuery.setProjection(ProjectionFactoryUtil.property(
				"commerceProductDefinitionLocalizationId"));

		Object newCommerceProductDefinitionLocalizationId = newCommerceProductDefinitionLocalization.getCommerceProductDefinitionLocalizationId();

		dynamicQuery.add(RestrictionsFactoryUtil.in(
				"commerceProductDefinitionLocalizationId",
				new Object[] { newCommerceProductDefinitionLocalizationId }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		Object existingCommerceProductDefinitionLocalizationId = result.get(0);

		Assert.assertEquals(existingCommerceProductDefinitionLocalizationId,
			newCommerceProductDefinitionLocalizationId);
	}

	@Test
	public void testDynamicQueryByProjectionMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(CommerceProductDefinitionLocalization.class,
				_dynamicQueryClassLoader);

		dynamicQuery.setProjection(ProjectionFactoryUtil.property(
				"commerceProductDefinitionLocalizationId"));

		dynamicQuery.add(RestrictionsFactoryUtil.in(
				"commerceProductDefinitionLocalizationId",
				new Object[] { RandomTestUtil.nextLong() }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testResetOriginalValues() throws Exception {
		CommerceProductDefinitionLocalization newCommerceProductDefinitionLocalization =
			addCommerceProductDefinitionLocalization();

		_persistence.clearCache();

		CommerceProductDefinitionLocalization existingCommerceProductDefinitionLocalization =
			_persistence.findByPrimaryKey(newCommerceProductDefinitionLocalization.getPrimaryKey());

		Assert.assertEquals(Long.valueOf(
				existingCommerceProductDefinitionLocalization.getCommerceProductDefinitionPK()),
			ReflectionTestUtil.<Long>invoke(
				existingCommerceProductDefinitionLocalization,
				"getOriginalCommerceProductDefinitionPK", new Class<?>[0]));
		Assert.assertTrue(Objects.equals(
				existingCommerceProductDefinitionLocalization.getLanguageId(),
				ReflectionTestUtil.invoke(
					existingCommerceProductDefinitionLocalization,
					"getOriginalLanguageId", new Class<?>[0])));
	}

	protected CommerceProductDefinitionLocalization addCommerceProductDefinitionLocalization()
		throws Exception {
		long pk = RandomTestUtil.nextLong();

		CommerceProductDefinitionLocalization commerceProductDefinitionLocalization =
			_persistence.create(pk);

		commerceProductDefinitionLocalization.setMvccVersion(RandomTestUtil.nextLong());

		commerceProductDefinitionLocalization.setCompanyId(RandomTestUtil.nextLong());

		commerceProductDefinitionLocalization.setCommerceProductDefinitionPK(RandomTestUtil.nextLong());

		commerceProductDefinitionLocalization.setLanguageId(RandomTestUtil.randomString());

		commerceProductDefinitionLocalization.setTitle(RandomTestUtil.randomString());

		commerceProductDefinitionLocalization.setUrlTitle(RandomTestUtil.randomString());

		commerceProductDefinitionLocalization.setDescription(RandomTestUtil.randomString());

		_commerceProductDefinitionLocalizations.add(_persistence.update(
				commerceProductDefinitionLocalization));

		return commerceProductDefinitionLocalization;
	}

	private List<CommerceProductDefinitionLocalization> _commerceProductDefinitionLocalizations =
		new ArrayList<CommerceProductDefinitionLocalization>();
	private CommerceProductDefinitionLocalizationPersistence _persistence;
	private ClassLoader _dynamicQueryClassLoader;
}