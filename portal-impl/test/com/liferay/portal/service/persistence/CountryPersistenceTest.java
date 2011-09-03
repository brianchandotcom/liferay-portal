/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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
import com.liferay.portal.kernel.dao.orm.RestrictionsFactoryUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.Country;
import com.liferay.portal.model.impl.CountryModelImpl;
import com.liferay.portal.service.persistence.BasePersistenceTestCase;
import com.liferay.portal.util.PropsValues;

import java.util.List;

/**
 * @author Brian Wing Shun Chan
 */
public class CountryPersistenceTest extends BasePersistenceTestCase {
	@Override
	public void setUp() throws Exception {
		super.setUp();

		_persistence = (CountryPersistence)PortalBeanLocatorUtil.locate(CountryPersistence.class.getName());
	}

	public void testCreate() throws Exception {
		long pk = nextLong();

		Country country = _persistence.create(pk);

		assertNotNull(country);

		assertEquals(country.getPrimaryKey(), pk);
	}

	public void testRemove() throws Exception {
		Country newCountry = addCountry();

		_persistence.remove(newCountry);

		Country existingCountry = _persistence.fetchByPrimaryKey(newCountry.getPrimaryKey());

		assertNull(existingCountry);
	}

	public void testUpdateNew() throws Exception {
		addCountry();
	}

	public void testUpdateExisting() throws Exception {
		long pk = nextLong();

		Country newCountry = _persistence.create(pk);

		newCountry.setName(randomString());

		newCountry.setA2(randomString());

		newCountry.setA3(randomString());

		newCountry.setNumber(randomString());

		newCountry.setIdd(randomString());

		newCountry.setZipRequired(randomBoolean());

		newCountry.setActive(randomBoolean());

		_persistence.update(newCountry, false);

		Country existingCountry = _persistence.findByPrimaryKey(newCountry.getPrimaryKey());

		assertEquals(existingCountry.getCountryId(), newCountry.getCountryId());
		assertEquals(existingCountry.getName(), newCountry.getName());
		assertEquals(existingCountry.getA2(), newCountry.getA2());
		assertEquals(existingCountry.getA3(), newCountry.getA3());
		assertEquals(existingCountry.getNumber(), newCountry.getNumber());
		assertEquals(existingCountry.getIdd(), newCountry.getIdd());
		assertEquals(existingCountry.getZipRequired(),
			newCountry.getZipRequired());
		assertEquals(existingCountry.getActive(), newCountry.getActive());
	}

	public void testFindByPrimaryKeyExisting() throws Exception {
		Country newCountry = addCountry();

		Country existingCountry = _persistence.findByPrimaryKey(newCountry.getPrimaryKey());

		assertEquals(existingCountry, newCountry);
	}

	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = nextLong();

		try {
			_persistence.findByPrimaryKey(pk);

			fail("Missing entity did not throw NoSuchCountryException");
		}
		catch (NoSuchCountryException nsee) {
		}
	}

	public void testFetchByPrimaryKeyExisting() throws Exception {
		Country newCountry = addCountry();

		Country existingCountry = _persistence.fetchByPrimaryKey(newCountry.getPrimaryKey());

		assertEquals(existingCountry, newCountry);
	}

	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = nextLong();

		Country missingCountry = _persistence.fetchByPrimaryKey(pk);

		assertNull(missingCountry);
	}

	public void testDynamicQueryByPrimaryKeyExisting()
		throws Exception {
		Country newCountry = addCountry();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(Country.class,
				Country.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("countryId",
				newCountry.getCountryId()));

		List<Country> result = _persistence.findWithDynamicQuery(dynamicQuery);

		assertEquals(1, result.size());

		Country existingCountry = result.get(0);

		assertEquals(existingCountry, newCountry);
	}

	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(Country.class,
				Country.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("countryId", nextLong()));

		List<Country> result = _persistence.findWithDynamicQuery(dynamicQuery);

		assertEquals(0, result.size());
	}

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

		assertEquals(1, result.size());

		Object existingCountryId = result.get(0);

		assertEquals(existingCountryId, newCountryId);
	}

	public void testDynamicQueryByProjectionMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(Country.class,
				Country.class.getClassLoader());

		dynamicQuery.setProjection(ProjectionFactoryUtil.property("countryId"));

		dynamicQuery.add(RestrictionsFactoryUtil.in("countryId",
				new Object[] { nextLong() }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		assertEquals(0, result.size());
	}

	public void testResetOriginalValues() throws Exception {
		if (!PropsValues.HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE) {
			return;
		}

		Country newCountry = addCountry();

		_persistence.clearCache();

		CountryModelImpl existingCountryModelImpl = (CountryModelImpl)_persistence.findByPrimaryKey(newCountry.getPrimaryKey());

		assertTrue(Validator.equals(existingCountryModelImpl.getName(),
				existingCountryModelImpl.getOriginalName()));

		assertTrue(Validator.equals(existingCountryModelImpl.getA2(),
				existingCountryModelImpl.getOriginalA2()));

		assertTrue(Validator.equals(existingCountryModelImpl.getA3(),
				existingCountryModelImpl.getOriginalA3()));
	}

	protected Country addCountry() throws Exception {
		long pk = nextLong();

		Country country = _persistence.create(pk);

		country.setName(randomString());

		country.setA2(randomString());

		country.setA3(randomString());

		country.setNumber(randomString());

		country.setIdd(randomString());

		country.setZipRequired(randomBoolean());

		country.setActive(randomBoolean());

		_persistence.update(country, false);

		return country;
	}

	private CountryPersistence _persistence;
}