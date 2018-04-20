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

import com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQueryFactoryUtil;
import com.liferay.portal.kernel.dao.orm.ProjectionFactoryUtil;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.RestrictionsFactoryUtil;
import com.liferay.portal.kernel.exception.NoSuchCompanyException;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.service.CompanyLocalServiceUtil;
import com.liferay.portal.kernel.service.persistence.CompanyPersistence;
import com.liferay.portal.kernel.service.persistence.CompanyUtil;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.transaction.Propagation;
import com.liferay.portal.kernel.util.IntegerWrapper;
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
public class CompanyPersistenceTest {
	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule = new AggregateTestRule(new LiferayIntegrationTestRule(),
			PersistenceTestRule.INSTANCE,
			new TransactionalTestRule(Propagation.REQUIRED));

	@Before
	public void setUp() {
		_persistence = CompanyUtil.getPersistence();

		Class<?> clazz = _persistence.getClass();

		_dynamicQueryClassLoader = clazz.getClassLoader();
	}

	@After
	public void tearDown() throws Exception {
		Iterator<Company> iterator = _companies.iterator();

		while (iterator.hasNext()) {
			_persistence.remove(iterator.next());

			iterator.remove();
		}
	}

	@Test
	public void testCreate() throws Exception {
		long pk = RandomTestUtil.nextLong();

		Company company = _persistence.create(pk);

		Assert.assertNotNull(company);

		Assert.assertEquals(company.getPrimaryKey(), pk);
	}

	@Test
	public void testRemove() throws Exception {
		Company newCompany = addCompany();

		_persistence.remove(newCompany);

		Company existingCompany = _persistence.fetchByPrimaryKey(newCompany.getPrimaryKey());

		Assert.assertNull(existingCompany);
	}

	@Test
	public void testUpdateNew() throws Exception {
		addCompany();
	}

	@Test
	public void testUpdateExisting() throws Exception {
		long pk = RandomTestUtil.nextLong();

		Company newCompany = _persistence.create(pk);

		newCompany.setMvccVersion(RandomTestUtil.nextLong());

		newCompany.setAccountId(RandomTestUtil.nextLong());

		newCompany.setWebId(RandomTestUtil.randomString());

		newCompany.setKey(RandomTestUtil.randomString());

		newCompany.setMx(RandomTestUtil.randomString());

		newCompany.setHomeURL(RandomTestUtil.randomString());

		newCompany.setLogoId(RandomTestUtil.nextLong());

		newCompany.setSystem(RandomTestUtil.randomBoolean());

		newCompany.setMaxUsers(RandomTestUtil.nextInt());

		newCompany.setActive(RandomTestUtil.randomBoolean());

		_companies.add(_persistence.update(newCompany));

		Company existingCompany = _persistence.findByPrimaryKey(newCompany.getPrimaryKey());

		Assert.assertEquals(existingCompany.getMvccVersion(),
			newCompany.getMvccVersion());
		Assert.assertEquals(existingCompany.getCompanyId(),
			newCompany.getCompanyId());
		Assert.assertEquals(existingCompany.getAccountId(),
			newCompany.getAccountId());
		Assert.assertEquals(existingCompany.getWebId(), newCompany.getWebId());
		Assert.assertEquals(existingCompany.getKey(), newCompany.getKey());
		Assert.assertEquals(existingCompany.getMx(), newCompany.getMx());
		Assert.assertEquals(existingCompany.getHomeURL(),
			newCompany.getHomeURL());
		Assert.assertEquals(existingCompany.getLogoId(), newCompany.getLogoId());
		Assert.assertEquals(existingCompany.isSystem(), newCompany.isSystem());
		Assert.assertEquals(existingCompany.getMaxUsers(),
			newCompany.getMaxUsers());
		Assert.assertEquals(existingCompany.isActive(), newCompany.isActive());
	}

	@Test
	public void testCountByWebId() throws Exception {
		_persistence.countByWebId("");

		_persistence.countByWebId("null");

		_persistence.countByWebId((String)null);
	}

	@Test
	public void testCountByMx() throws Exception {
		_persistence.countByMx("");

		_persistence.countByMx("null");

		_persistence.countByMx((String)null);
	}

	@Test
	public void testCountByLogoId() throws Exception {
		_persistence.countByLogoId(RandomTestUtil.nextLong());

		_persistence.countByLogoId(0L);
	}

	@Test
	public void testCountBySystem() throws Exception {
		_persistence.countBySystem(RandomTestUtil.randomBoolean());

		_persistence.countBySystem(RandomTestUtil.randomBoolean());
	}

	@Test
	public void testFindByPrimaryKeyExisting() throws Exception {
		Company newCompany = addCompany();

		Company existingCompany = _persistence.findByPrimaryKey(newCompany.getPrimaryKey());

		Assert.assertEquals(existingCompany, newCompany);
	}

	@Test(expected = NoSuchCompanyException.class)
	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		_persistence.findByPrimaryKey(pk);
	}

	@Test
	public void testFindAll() throws Exception {
		_persistence.findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			getOrderByComparator());
	}

	protected OrderByComparator<Company> getOrderByComparator() {
		return OrderByComparatorFactoryUtil.create("Company", "mvccVersion",
			true, "companyId", true, "accountId", true, "webId", true, "mx",
			true, "homeURL", true, "logoId", true, "system", true, "maxUsers",
			true, "active", true);
	}

	@Test
	public void testFetchByPrimaryKeyExisting() throws Exception {
		Company newCompany = addCompany();

		Company existingCompany = _persistence.fetchByPrimaryKey(newCompany.getPrimaryKey());

		Assert.assertEquals(existingCompany, newCompany);
	}

	@Test
	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		Company missingCompany = _persistence.fetchByPrimaryKey(pk);

		Assert.assertNull(missingCompany);
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereAllPrimaryKeysExist()
		throws Exception {
		Company newCompany1 = addCompany();
		Company newCompany2 = addCompany();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newCompany1.getPrimaryKey());
		primaryKeys.add(newCompany2.getPrimaryKey());

		Map<Serializable, Company> companies = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(2, companies.size());
		Assert.assertEquals(newCompany1,
			companies.get(newCompany1.getPrimaryKey()));
		Assert.assertEquals(newCompany2,
			companies.get(newCompany2.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereNoPrimaryKeysExist()
		throws Exception {
		long pk1 = RandomTestUtil.nextLong();

		long pk2 = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(pk1);
		primaryKeys.add(pk2);

		Map<Serializable, Company> companies = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(companies.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereSomePrimaryKeysExist()
		throws Exception {
		Company newCompany = addCompany();

		long pk = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newCompany.getPrimaryKey());
		primaryKeys.add(pk);

		Map<Serializable, Company> companies = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, companies.size());
		Assert.assertEquals(newCompany,
			companies.get(newCompany.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithNoPrimaryKeys()
		throws Exception {
		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		Map<Serializable, Company> companies = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(companies.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithOnePrimaryKey()
		throws Exception {
		Company newCompany = addCompany();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newCompany.getPrimaryKey());

		Map<Serializable, Company> companies = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, companies.size());
		Assert.assertEquals(newCompany,
			companies.get(newCompany.getPrimaryKey()));
	}

	@Test
	public void testActionableDynamicQuery() throws Exception {
		final IntegerWrapper count = new IntegerWrapper();

		ActionableDynamicQuery actionableDynamicQuery = CompanyLocalServiceUtil.getActionableDynamicQuery();

		actionableDynamicQuery.setPerformActionMethod(new ActionableDynamicQuery.PerformActionMethod<Company>() {
				@Override
				public void performAction(Company company) {
					Assert.assertNotNull(company);

					count.increment();
				}
			});

		actionableDynamicQuery.performActions();

		Assert.assertEquals(count.getValue(), _persistence.countAll());
	}

	@Test
	public void testDynamicQueryByPrimaryKeyExisting()
		throws Exception {
		Company newCompany = addCompany();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(Company.class,
				_dynamicQueryClassLoader);

		dynamicQuery.add(RestrictionsFactoryUtil.eq("companyId",
				newCompany.getCompanyId()));

		List<Company> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		Company existingCompany = result.get(0);

		Assert.assertEquals(existingCompany, newCompany);
	}

	@Test
	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(Company.class,
				_dynamicQueryClassLoader);

		dynamicQuery.add(RestrictionsFactoryUtil.eq("companyId",
				RandomTestUtil.nextLong()));

		List<Company> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testDynamicQueryByProjectionExisting()
		throws Exception {
		Company newCompany = addCompany();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(Company.class,
				_dynamicQueryClassLoader);

		dynamicQuery.setProjection(ProjectionFactoryUtil.property("companyId"));

		Object newCompanyId = newCompany.getCompanyId();

		dynamicQuery.add(RestrictionsFactoryUtil.in("companyId",
				new Object[] { newCompanyId }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		Object existingCompanyId = result.get(0);

		Assert.assertEquals(existingCompanyId, newCompanyId);
	}

	@Test
	public void testDynamicQueryByProjectionMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(Company.class,
				_dynamicQueryClassLoader);

		dynamicQuery.setProjection(ProjectionFactoryUtil.property("companyId"));

		dynamicQuery.add(RestrictionsFactoryUtil.in("companyId",
				new Object[] { RandomTestUtil.nextLong() }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testResetOriginalValues() throws Exception {
		Company newCompany = addCompany();

		_persistence.clearCache();

		Company existingCompany = _persistence.findByPrimaryKey(newCompany.getPrimaryKey());

		Assert.assertTrue(Objects.equals(existingCompany.getWebId(),
				ReflectionTestUtil.invoke(existingCompany, "getOriginalWebId",
					new Class<?>[0])));

		Assert.assertTrue(Objects.equals(existingCompany.getMx(),
				ReflectionTestUtil.invoke(existingCompany, "getOriginalMx",
					new Class<?>[0])));

		Assert.assertEquals(Long.valueOf(existingCompany.getLogoId()),
			ReflectionTestUtil.<Long>invoke(existingCompany,
				"getOriginalLogoId", new Class<?>[0]));
	}

	protected Company addCompany() throws Exception {
		long pk = RandomTestUtil.nextLong();

		Company company = _persistence.create(pk);

		company.setMvccVersion(RandomTestUtil.nextLong());

		company.setAccountId(RandomTestUtil.nextLong());

		company.setWebId(RandomTestUtil.randomString());

		company.setKey(RandomTestUtil.randomString());

		company.setMx(RandomTestUtil.randomString());

		company.setHomeURL(RandomTestUtil.randomString());

		company.setLogoId(RandomTestUtil.nextLong());

		company.setSystem(RandomTestUtil.randomBoolean());

		company.setMaxUsers(RandomTestUtil.nextInt());

		company.setActive(RandomTestUtil.randomBoolean());

		_companies.add(_persistence.update(company));

		return company;
	}

	private List<Company> _companies = new ArrayList<Company>();
	private CompanyPersistence _persistence;
	private ClassLoader _dynamicQueryClassLoader;
}