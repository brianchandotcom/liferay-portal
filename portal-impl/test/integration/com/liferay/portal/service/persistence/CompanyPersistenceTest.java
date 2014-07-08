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

import com.liferay.portal.NoSuchCompanyException;
import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQueryFactoryUtil;
import com.liferay.portal.kernel.dao.orm.ProjectionFactoryUtil;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.RestrictionsFactoryUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.test.ExecutionTestListeners;
import com.liferay.portal.kernel.util.IntegerWrapper;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.OrderByComparatorFactoryUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.Company;
import com.liferay.portal.model.ModelListener;
import com.liferay.portal.model.impl.CompanyModelImpl;
import com.liferay.portal.service.CompanyLocalServiceUtil;
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
public class CompanyPersistenceTest {
	@Before
	public void setUp() {
		_modelListeners = _persistence.getListeners();

		for (ModelListener<Company> modelListener : _modelListeners) {
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

		for (ModelListener<Company> modelListener : _modelListeners) {
			_persistence.registerListener(modelListener);
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

		_persistence.update(newCompany);

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
		Assert.assertEquals(existingCompany.getSystem(), newCompany.getSystem());
		Assert.assertEquals(existingCompany.getMaxUsers(),
			newCompany.getMaxUsers());
		Assert.assertEquals(existingCompany.getActive(), newCompany.getActive());
	}

	@Test
	public void testCountByWebId() {
		try {
			_persistence.countByWebId(StringPool.BLANK);

			_persistence.countByWebId(StringPool.NULL);

			_persistence.countByWebId((String)null);
		}
		catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void testCountByMx() {
		try {
			_persistence.countByMx(StringPool.BLANK);

			_persistence.countByMx(StringPool.NULL);

			_persistence.countByMx((String)null);
		}
		catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void testCountByLogoId() {
		try {
			_persistence.countByLogoId(RandomTestUtil.nextLong());

			_persistence.countByLogoId(0L);
		}
		catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void testCountBySystem() {
		try {
			_persistence.countBySystem(RandomTestUtil.randomBoolean());

			_persistence.countBySystem(RandomTestUtil.randomBoolean());
		}
		catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void testFindByPrimaryKeyExisting() throws Exception {
		Company newCompany = addCompany();

		Company existingCompany = _persistence.findByPrimaryKey(newCompany.getPrimaryKey());

		Assert.assertEquals(existingCompany, newCompany);
	}

	@Test
	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		try {
			_persistence.findByPrimaryKey(pk);

			Assert.fail("Missing entity did not throw NoSuchCompanyException");
		}
		catch (NoSuchCompanyException nsee) {
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
		return OrderByComparatorFactoryUtil.create("Company", "mvccVersion",
			true, "companyId", true, "accountId", true, "webId", true, "key",
			true, "mx", true, "homeURL", true, "logoId", true, "system", true,
			"maxUsers", true, "active", true);
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

		actionableDynamicQuery.setPerformActionMethod(new ActionableDynamicQuery.PerformActionMethod() {
				@Override
				public void performAction(Object object) {
					Company company = (Company)object;

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
				Company.class.getClassLoader());

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
				Company.class.getClassLoader());

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
				Company.class.getClassLoader());

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
				Company.class.getClassLoader());

		dynamicQuery.setProjection(ProjectionFactoryUtil.property("companyId"));

		dynamicQuery.add(RestrictionsFactoryUtil.in("companyId",
				new Object[] { RandomTestUtil.nextLong() }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testResetOriginalValues() throws Exception {
		if (!PropsValues.HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE) {
			return;
		}

		Company newCompany = addCompany();

		_persistence.clearCache();

		CompanyModelImpl existingCompanyModelImpl = (CompanyModelImpl)_persistence.findByPrimaryKey(newCompany.getPrimaryKey());

		Assert.assertTrue(Validator.equals(
				existingCompanyModelImpl.getWebId(),
				existingCompanyModelImpl.getOriginalWebId()));

		Assert.assertTrue(Validator.equals(existingCompanyModelImpl.getMx(),
				existingCompanyModelImpl.getOriginalMx()));

		Assert.assertEquals(existingCompanyModelImpl.getLogoId(),
			existingCompanyModelImpl.getOriginalLogoId());
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

		_persistence.update(company);

		return company;
	}

	private static Log _log = LogFactoryUtil.getLog(CompanyPersistenceTest.class);
	private ModelListener<Company>[] _modelListeners;
	private CompanyPersistence _persistence = (CompanyPersistence)PortalBeanLocatorUtil.locate(CompanyPersistence.class.getName());
	private TransactionalPersistenceAdvice _transactionalPersistenceAdvice = (TransactionalPersistenceAdvice)PortalBeanLocatorUtil.locate(TransactionalPersistenceAdvice.class.getName());
}