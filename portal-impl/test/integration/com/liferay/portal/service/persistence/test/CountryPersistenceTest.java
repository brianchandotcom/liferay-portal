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

package com.liferay.portal.service.persistence.test;

import com.liferay.portal.NoSuchCountryException;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQueryFactoryUtil;
import com.liferay.portal.kernel.dao.orm.ProjectionFactoryUtil;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.RestrictionsFactoryUtil;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.TransactionalTestRule;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.transaction.Propagation;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.OrderByComparatorFactoryUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.Country;
import com.liferay.portal.service.persistence.CountryPersistence;
import com.liferay.portal.service.persistence.CountryUtil;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PersistenceTestRule;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @generated
 */
public class CountryPersistenceTest {
	@Rule
	public final AggregateTestRule aggregateTestRule = new AggregateTestRule(new LiferayIntegrationTestRule(),
			PersistenceTestRule.INSTANCE,
			new TransactionalTestRule(Propagation.REQUIRED));

	@Before
	public void setUp() {
		_persistence = CountryUtil.getPersistence();

		Class<?> clazz = _persistence.getClass();

		_dynamicQueryClassLoader = clazz.getClassLoader();
	}

	@After
	public void tearDown() throws Exception {
		Iterator<Country> iterator = _countries.iterator();

		while (iterator.hasNext()) {
			_persistence.remove(iterator.next());

			iterator.remove();
		}
	}

	@Test
	public void testCreate() throws Exception {
		long pk = RandomTestUtil.nextLong();

		Country country = _persistence.create(pk);

		Assert.assertNotNull(country);

		Assert.assertEquals(country.getPrimaryKey(), pk);
	}

	@Test
	public void testRemove() throws Exception {
		Country newCountry = addCountry();

		_persistence.remove(newCountry);

		Country existingCountry = _persistence.fetchByPrimaryKey(newCountry.getPrimaryKey());

		Assert.assertNull(existingCountry);
	}

	@Test
	public void testUpdateNew() throws Exception {
		addCountry();
	}

	@Test
	public void testUpdateExisting() throws Exception {
		long pk = RandomTestUtil.nextLong();

		Country newCountry = _persistence.create(pk);

		newCountry.setMvccVersion(RandomTestUtil.nextLong());

		newCountry.setName(RandomTestUtil.randomString());

		newCountry.setA2(RandomTestUtil.randomString());

		newCountry.setA3(RandomTestUtil.randomString());

		newCountry.setNumber(RandomTestUtil.randomString());

		newCountry.setIdd(RandomTestUtil.randomString());

		newCountry.setZipRequired(RandomTestUtil.randomBoolean());

		newCountry.setActive(RandomTestUtil.randomBoolean());

		_countries.add(_persistence.update(newCountry));

		Country existingCountry = _persistence.findByPrimaryKey(newCountry.getPrimaryKey());

		Assert.assertEquals(existingCountry.getMvccVersion(),
			newCountry.getMvccVersion());
		Assert.assertEquals(existingCountry.getCountryId(),
			newCountry.getCountryId());
		Assert.assertEquals(existingCountry.getName(), newCountry.getName());
		Assert.assertEquals(existingCountry.getA2(), newCountry.getA2());
		Assert.assertEquals(existingCountry.getA3(), newCountry.getA3());
		Assert.assertEquals(existingCountry.getNumber(), newCountry.getNumber());
		Assert.assertEquals(existingCountry.getIdd(), newCountry.getIdd());
		Assert.assertEquals(existingCountry.getZipRequired(),
			newCountry.getZipRequired());
		Assert.assertEquals(existingCountry.getActive(), newCountry.getActive());
	}

	@Test
	public void testCountByName() throws Exception {
		_persistence.countByName(StringPool.BLANK);

		_persistence.countByName(StringPool.NULL);

		_persistence.countByName((String)null);
	}

	@Test
	public void testCountByA2() throws Exception {
		_persistence.countByA2(StringPool.BLANK);

		_persistence.countByA2(StringPool.NULL);

		_persistence.countByA2((String)null);
	}

	@Test
	public void testCountByA3() throws Exception {
		_persistence.countByA3(StringPool.BLANK);

		_persistence.countByA3(StringPool.NULL);

		_persistence.countByA3((String)null);
	}

	@Test
	public void testCountByActive() throws Exception {
		_persistence.countByActive(RandomTestUtil.randomBoolean());

		_persistence.countByActive(RandomTestUtil.randomBoolean());
	}

	@Test
	public void testFindByPrimaryKeyExisting() throws Exception {
		Country newCountry = addCountry();

		Country existingCountry = _persistence.findByPrimaryKey(newCountry.getPrimaryKey());

		Assert.assertEquals(existingCountry, newCountry);
	}

	@Test(expected = NoSuchCountryException.class)
	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		_persistence.findByPrimaryKey(pk);
	}

	@Test
	public void testFindAll() throws Exception {
		_persistence.findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			getOrderByComparator());
	}

	protected OrderByComparator<Country> getOrderByComparator() {
		return OrderByComparatorFactoryUtil.create("Country", "mvccVersion",
			true, "countryId", true, "name", true, "a2", true, "a3", true,
			"number", true, "idd", true, "zipRequired", true, "active", true);
	}

	@Test
	public void testFetchByPrimaryKeyExisting() throws Exception {
		Country newCountry = addCountry();

		Country existingCountry = _persistence.fetchByPrimaryKey(newCountry.getPrimaryKey());

		Assert.assertEquals(existingCountry, newCountry);
	}

	@Test
	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		Country missingCountry = _persistence.fetchByPrimaryKey(pk);

		Assert.assertNull(missingCountry);
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereAllPrimaryKeysExist()
		throws Exception {
		Country newCountry1 = addCountry();
		Country newCountry2 = addCountry();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newCountry1.getPrimaryKey());
		primaryKeys.add(newCountry2.getPrimaryKey());

		Map<Serializable, Country> countries = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(2, countries.size());
		Assert.assertEquals(newCountry1,
			countries.get(newCountry1.getPrimaryKey()));
		Assert.assertEquals(newCountry2,
			countries.get(newCountry2.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereNoPrimaryKeysExist()
		throws Exception {
		long pk1 = RandomTestUtil.nextLong();

		long pk2 = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(pk1);
		primaryKeys.add(pk2);

		Map<Serializable, Country> countries = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(countries.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereSomePrimaryKeysExist()
		throws Exception {
		Country newCountry = addCountry();

		long pk = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newCountry.getPrimaryKey());
		primaryKeys.add(pk);

		Map<Serializable, Country> countries = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, countries.size());
		Assert.assertEquals(newCountry,
			countries.get(newCountry.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithNoPrimaryKeys()
		throws Exception {
		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		Map<Serializable, Country> countries = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(countries.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithOnePrimaryKey()
		throws Exception {
		Country newCountry = addCountry();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newCountry.getPrimaryKey());

		Map<Serializable, Country> countries = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, countries.size());
		Assert.assertEquals(newCountry,
			countries.get(newCountry.getPrimaryKey()));
	}

	@Test
	public void testDynamicQueryByPrimaryKeyExisting()
		throws Exception {
		Country newCountry = addCountry();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(Country.class,
				_dynamicQueryClassLoader);

		dynamicQuery.add(RestrictionsFactoryUtil.eq("countryId",
				newCountry.getCountryId()));

		List<Country> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		Country existingCountry = result.get(0);

		Assert.assertEquals(existingCountry, newCountry);
	}

	@Test
	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(Country.class,
				_dynamicQueryClassLoader);

		dynamicQuery.add(RestrictionsFactoryUtil.eq("countryId",
				RandomTestUtil.nextLong()));

		List<Country> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testDynamicQueryByProjectionExisting()
		throws Exception {
		Country newCountry = addCountry();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(Country.class,
				_dynamicQueryClassLoader);

		dynamicQuery.setProjection(ProjectionFactoryUtil.property("countryId"));

		Object newCountryId = newCountry.getCountryId();

		dynamicQuery.add(RestrictionsFactoryUtil.in("countryId",
				new Object[] { newCountryId }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		Object existingCountryId = result.get(0);

		Assert.assertEquals(existingCountryId, newCountryId);
	}

	@Test
	public void testDynamicQueryByProjectionMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(Country.class,
				_dynamicQueryClassLoader);

		dynamicQuery.setProjection(ProjectionFactoryUtil.property("countryId"));

		dynamicQuery.add(RestrictionsFactoryUtil.in("countryId",
				new Object[] { RandomTestUtil.nextLong() }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testResetOriginalValues() throws Exception {
		Country newCountry = addCountry();

		_persistence.clearCache();

		Country existingCountry = _persistence.findByPrimaryKey(newCountry.getPrimaryKey());

		Assert.assertTrue(Validator.equals(existingCountry.getName(),
				ReflectionTestUtil.invoke(existingCountry, "getOriginalName",
					new Class<?>[0])));

		Assert.assertTrue(Validator.equals(existingCountry.getA2(),
				ReflectionTestUtil.invoke(existingCountry, "getOriginalA2",
					new Class<?>[0])));

		Assert.assertTrue(Validator.equals(existingCountry.getA3(),
				ReflectionTestUtil.invoke(existingCountry, "getOriginalA3",
					new Class<?>[0])));
	}

	protected Country addCountry() throws Exception {
		long pk = RandomTestUtil.nextLong();

		Country country = _persistence.create(pk);

		country.setMvccVersion(RandomTestUtil.nextLong());

		country.setName(RandomTestUtil.randomString());

		country.setA2(RandomTestUtil.randomString());

		country.setA3(RandomTestUtil.randomString());

		country.setNumber(RandomTestUtil.randomString());

		country.setIdd(RandomTestUtil.randomString());

		country.setZipRequired(RandomTestUtil.randomBoolean());

		country.setActive(RandomTestUtil.randomBoolean());

		_countries.add(_persistence.update(country));

		return country;
	}

	private List<Country> _countries = new ArrayList<Country>();
	private CountryPersistence _persistence;
	private ClassLoader _dynamicQueryClassLoader;
}