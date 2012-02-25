/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

import com.liferay.portal.NoSuchCompanyException;
import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQueryFactoryUtil;
import com.liferay.portal.kernel.dao.orm.ProjectionFactoryUtil;
import com.liferay.portal.kernel.dao.orm.RestrictionsFactoryUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.Company;
import com.liferay.portal.model.impl.CompanyModelImpl;
import com.liferay.portal.service.persistence.BasePersistenceTestCase;
import com.liferay.portal.util.PropsValues;

import java.util.List;

/**
 * @author Brian Wing Shun Chan
 */
public class CompanyPersistenceTest extends BasePersistenceTestCase {
	@Override
	public void setUp() throws Exception {
		super.setUp();

		_persistence = (CompanyPersistence)PortalBeanLocatorUtil.locate(CompanyPersistence.class.getName());
	}

	public void testCreate() throws Exception {
		long pk = nextLong();

		Company company = _persistence.create(pk);

		assertNotNull(company);

		assertEquals(company.getPrimaryKey(), pk);
	}

	public void testRemove() throws Exception {
		Company newCompany = addCompany();

		_persistence.remove(newCompany);

		Company existingCompany = _persistence.fetchByPrimaryKey(newCompany.getPrimaryKey());

		assertNull(existingCompany);
	}

	public void testUpdateNew() throws Exception {
		addCompany();
	}

	public void testUpdateExisting() throws Exception {
		long pk = nextLong();

		Company newCompany = _persistence.create(pk);

		newCompany.setAccountId(nextLong());

		newCompany.setWebId(randomString());

		newCompany.setKey(randomString());

		newCompany.setMx(randomString());

		newCompany.setHomeURL(randomString());

		newCompany.setLogoId(nextLong());

		newCompany.setSystem(randomBoolean());

		newCompany.setMaxUsers(nextInt());

		newCompany.setActive(randomBoolean());

		_persistence.update(newCompany, false);

		Company existingCompany = _persistence.findByPrimaryKey(newCompany.getPrimaryKey());

		assertEquals(existingCompany.getCompanyId(), newCompany.getCompanyId());
		assertEquals(existingCompany.getAccountId(), newCompany.getAccountId());
		assertEquals(existingCompany.getWebId(), newCompany.getWebId());
		assertEquals(existingCompany.getKey(), newCompany.getKey());
		assertEquals(existingCompany.getMx(), newCompany.getMx());
		assertEquals(existingCompany.getHomeURL(), newCompany.getHomeURL());
		assertEquals(existingCompany.getLogoId(), newCompany.getLogoId());
		assertEquals(existingCompany.getSystem(), newCompany.getSystem());
		assertEquals(existingCompany.getMaxUsers(), newCompany.getMaxUsers());
		assertEquals(existingCompany.getActive(), newCompany.getActive());
	}

	public void testFindByPrimaryKeyExisting() throws Exception {
		Company newCompany = addCompany();

		Company existingCompany = _persistence.findByPrimaryKey(newCompany.getPrimaryKey());

		assertEquals(existingCompany, newCompany);
	}

	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = nextLong();

		try {
			_persistence.findByPrimaryKey(pk);

			fail("Missing entity did not throw NoSuchCompanyException");
		}
		catch (NoSuchCompanyException nsee) {
		}
	}

	public void testFetchByPrimaryKeyExisting() throws Exception {
		Company newCompany = addCompany();

		Company existingCompany = _persistence.fetchByPrimaryKey(newCompany.getPrimaryKey());

		assertEquals(existingCompany, newCompany);
	}

	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = nextLong();

		Company missingCompany = _persistence.fetchByPrimaryKey(pk);

		assertNull(missingCompany);
	}

	public void testDynamicQueryByPrimaryKeyExisting()
		throws Exception {
		Company newCompany = addCompany();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(Company.class,
				Company.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("companyId",
				newCompany.getCompanyId()));

		List<Company> result = _persistence.findWithDynamicQuery(dynamicQuery);

		assertEquals(1, result.size());

		Company existingCompany = result.get(0);

		assertEquals(existingCompany, newCompany);
	}

	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(Company.class,
				Company.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("companyId", nextLong()));

		List<Company> result = _persistence.findWithDynamicQuery(dynamicQuery);

		assertEquals(0, result.size());
	}

	public void testDynamicQueryByProjectionExisting()
		throws Exception {
		Company newCompany = addCompany();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(Company.class,
				Company.class.getClassLoader());

		dynamicQuery.setProjection(ProjectionFactoryUtil.property("companyId"));

		Object newCompanyId = newCompany.getCompanyId();

		dynamicQuery.add(RestrictionsFactoryUtil.in("companyId",
				new Object[] { newCompanyId }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		assertEquals(1, result.size());

		Object existingCompanyId = result.get(0);

		assertEquals(existingCompanyId, newCompanyId);
	}

	public void testDynamicQueryByProjectionMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(Company.class,
				Company.class.getClassLoader());

		dynamicQuery.setProjection(ProjectionFactoryUtil.property("companyId"));

		dynamicQuery.add(RestrictionsFactoryUtil.in("companyId",
				new Object[] { nextLong() }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		assertEquals(0, result.size());
	}

	public void testResetOriginalValues() throws Exception {
		if (!PropsValues.HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE) {
			return;
		}

		Company newCompany = addCompany();

		_persistence.clearCache();

		CompanyModelImpl existingCompanyModelImpl = (CompanyModelImpl)_persistence.findByPrimaryKey(newCompany.getPrimaryKey());

		assertTrue(Validator.equals(existingCompanyModelImpl.getWebId(),
				existingCompanyModelImpl.getOriginalWebId()));

		assertTrue(Validator.equals(existingCompanyModelImpl.getMx(),
				existingCompanyModelImpl.getOriginalMx()));

		assertEquals(existingCompanyModelImpl.getLogoId(),
			existingCompanyModelImpl.getOriginalLogoId());
	}

	protected Company addCompany() throws Exception {
		long pk = nextLong();

		Company company = _persistence.create(pk);

		company.setAccountId(nextLong());

		company.setWebId(randomString());

		company.setKey(randomString());

		company.setMx(randomString());

		company.setHomeURL(randomString());

		company.setLogoId(nextLong());

		company.setSystem(randomBoolean());

		company.setMaxUsers(nextInt());

		company.setActive(randomBoolean());

		_persistence.update(company, false);

		return company;
	}

	private CompanyPersistence _persistence;
}