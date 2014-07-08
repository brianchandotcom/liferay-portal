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

package com.liferay.portal.service.persistence;

import com.liferay.portal.NoSuchCountryException;
import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQueryFactoryUtil;
import com.liferay.portal.kernel.dao.orm.ProjectionFactoryUtil;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.RestrictionsFactoryUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.test.ExecutionTestListeners;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.OrderByComparatorFactoryUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.Country;
import com.liferay.portal.model.ModelListener;
import com.liferay.portal.model.impl.CountryModelImpl;
import com.liferay.portal.service.persistence.BasePersistence;
import com.liferay.portal.service.persistence.PersistenceExecutionTestListener;
import com.liferay.portal.test.LiferayPersistenceIntegrationJUnitTestRunner;
import com.liferay.portal.test.persistence.test.TransactionalPersistenceAdvice;
import com.liferay.portal.util.PropsValues;
import com.liferay.portal.util.test.RandomTestUtil;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import org.junit.runner.RunWith;

import java.io.Serializable;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author Brian Wing Shun Chan
 */
@ExecutionTestListeners(listeners =  {
	PersistenceExecutionTestListener.class})
@RunWith(LiferayPersistenceIntegrationJUnitTestRunner.class)
public class CountryPersistenceTest {
	@Before
	public void setUp() {
		_modelListeners = _persistence.getListeners();

		for (ModelListener<Country> modelListener : _modelListeners) {
			_persistence.unregisterListener(modelListener);
		}
	}

	@After
	public void tearDown() throws Exception {
		Map<Serializable, BasePersistence<?>> basePersistences = _transactionalPersistenceAdvice.getBasePersistences();

		Set<Serializable> primaryKeys = basePersistences.keySet();

		for (Serializable primaryKey : primaryKeys) {
			BasePersistence<?> basePersistence = basePersistences.get(primaryKey);

			try {
				basePersistence.remove(primaryKey);
			}
			catch (Exception e) {
				if (_log.isDebugEnabled()) {
					_log.debug("The model with primary key " + primaryKey +
						" was already deleted");
				}
			}
		}

		_transactionalPersistenceAdvice.reset();

		for (ModelListener<Country> modelListener : _modelListeners) {
			_persistence.registerListener(modelListener);
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

		_persistence.update(newCountry);

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
	public void testCountByName() {
		try {
			_persistence.countByName(StringPool.BLANK);

			_persistence.countByName(StringPool.NULL);

			_persistence.countByName((String)null);
		}
		catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void testCountByA2() {
		try {
			_persistence.countByA2(StringPool.BLANK);

			_persistence.countByA2(StringPool.NULL);

			_persistence.countByA2((String)null);
		}
		catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void testCountByA3() {
		try {
			_persistence.countByA3(StringPool.BLANK);

			_persistence.countByA3(StringPool.NULL);

			_persistence.countByA3((String)null);
		}
		catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void testCountByActive() {
		try {
			_persistence.countByActive(RandomTestUtil.randomBoolean());

			_persistence.countByActive(RandomTestUtil.randomBoolean());
		}
		catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void testFindByPrimaryKeyExisting() throws Exception {
		Country newCountry = addCountry();

		Country existingCountry = _persistence.findByPrimaryKey(newCountry.getPrimaryKey());

		Assert.assertEquals(existingCountry, newCountry);
	}

	@Test
	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		try {
			_persistence.findByPrimaryKey(pk);

			Assert.fail("Missing entity did not throw NoSuchCountryException");
		}
		catch (NoSuchCountryException nsee) {
		}
	}

	@Test
	public void testFindAll() throws Exception {
		try {
			_persistence.findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS,
				getOrderByComparator());
		}
		catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	protected OrderByComparator getOrderByComparator() {
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
				Country.class.getClassLoader());

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
				Country.class.getClassLoader());

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
				Country.class.getClassLoader());

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
				Country.class.getClassLoader());

		dynamicQuery.setProjection(ProjectionFactoryUtil.property("countryId"));

		dynamicQuery.add(RestrictionsFactoryUtil.in("countryId",
				new Object[] { RandomTestUtil.nextLong() }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testResetOriginalValues() throws Exception {
		if (!PropsValues.HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE) {
			return;
		}

		Country newCountry = addCountry();

		_persistence.clearCache();

		CountryModelImpl existingCountryModelImpl = (CountryModelImpl)_persistence.findByPrimaryKey(newCountry.getPrimaryKey());

		Assert.assertTrue(Validator.equals(existingCountryModelImpl.getName(),
				existingCountryModelImpl.getOriginalName()));

		Assert.assertTrue(Validator.equals(existingCountryModelImpl.getA2(),
				existingCountryModelImpl.getOriginalA2()));

		Assert.assertTrue(Validator.equals(existingCountryModelImpl.getA3(),
				existingCountryModelImpl.getOriginalA3()));
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

		_persistence.update(country);

		return country;
	}

	private static Log _log = LogFactoryUtil.getLog(CountryPersistenceTest.class);
	private ModelListener<Country>[] _modelListeners;
	private CountryPersistence _persistence = (CountryPersistence)PortalBeanLocatorUtil.locate(CountryPersistence.class.getName());
	private TransactionalPersistenceAdvice _transactionalPersistenceAdvice = (TransactionalPersistenceAdvice)PortalBeanLocatorUtil.locate(TransactionalPersistenceAdvice.class.getName());
}