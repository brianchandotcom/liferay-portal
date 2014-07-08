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

import com.liferay.portal.NoSuchPasswordPolicyException;
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
import com.liferay.portal.kernel.util.Time;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.ModelListener;
import com.liferay.portal.model.PasswordPolicy;
import com.liferay.portal.model.impl.PasswordPolicyModelImpl;
import com.liferay.portal.service.PasswordPolicyLocalServiceUtil;
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
public class PasswordPolicyPersistenceTest {
	@Before
	public void setUp() {
		_modelListeners = _persistence.getListeners();

		for (ModelListener<PasswordPolicy> modelListener : _modelListeners) {
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

		for (ModelListener<PasswordPolicy> modelListener : _modelListeners) {
			_persistence.registerListener(modelListener);
		}
	}

	@Test
	public void testCreate() throws Exception {
		long pk = RandomTestUtil.nextLong();

		PasswordPolicy passwordPolicy = _persistence.create(pk);

		Assert.assertNotNull(passwordPolicy);

		Assert.assertEquals(passwordPolicy.getPrimaryKey(), pk);
	}

	@Test
	public void testRemove() throws Exception {
		PasswordPolicy newPasswordPolicy = addPasswordPolicy();

		_persistence.remove(newPasswordPolicy);

		PasswordPolicy existingPasswordPolicy = _persistence.fetchByPrimaryKey(newPasswordPolicy.getPrimaryKey());

		Assert.assertNull(existingPasswordPolicy);
	}

	@Test
	public void testUpdateNew() throws Exception {
		addPasswordPolicy();
	}

	@Test
	public void testUpdateExisting() throws Exception {
		long pk = RandomTestUtil.nextLong();

		PasswordPolicy newPasswordPolicy = _persistence.create(pk);

		newPasswordPolicy.setMvccVersion(RandomTestUtil.nextLong());

		newPasswordPolicy.setUuid(RandomTestUtil.randomString());

		newPasswordPolicy.setCompanyId(RandomTestUtil.nextLong());

		newPasswordPolicy.setUserId(RandomTestUtil.nextLong());

		newPasswordPolicy.setUserName(RandomTestUtil.randomString());

		newPasswordPolicy.setCreateDate(RandomTestUtil.nextDate());

		newPasswordPolicy.setModifiedDate(RandomTestUtil.nextDate());

		newPasswordPolicy.setDefaultPolicy(RandomTestUtil.randomBoolean());

		newPasswordPolicy.setName(RandomTestUtil.randomString());

		newPasswordPolicy.setDescription(RandomTestUtil.randomString());

		newPasswordPolicy.setChangeable(RandomTestUtil.randomBoolean());

		newPasswordPolicy.setChangeRequired(RandomTestUtil.randomBoolean());

		newPasswordPolicy.setMinAge(RandomTestUtil.nextLong());

		newPasswordPolicy.setCheckSyntax(RandomTestUtil.randomBoolean());

		newPasswordPolicy.setAllowDictionaryWords(RandomTestUtil.randomBoolean());

		newPasswordPolicy.setMinAlphanumeric(RandomTestUtil.nextInt());

		newPasswordPolicy.setMinLength(RandomTestUtil.nextInt());

		newPasswordPolicy.setMinLowerCase(RandomTestUtil.nextInt());

		newPasswordPolicy.setMinNumbers(RandomTestUtil.nextInt());

		newPasswordPolicy.setMinSymbols(RandomTestUtil.nextInt());

		newPasswordPolicy.setMinUpperCase(RandomTestUtil.nextInt());

		newPasswordPolicy.setRegex(RandomTestUtil.randomString());

		newPasswordPolicy.setHistory(RandomTestUtil.randomBoolean());

		newPasswordPolicy.setHistoryCount(RandomTestUtil.nextInt());

		newPasswordPolicy.setExpireable(RandomTestUtil.randomBoolean());

		newPasswordPolicy.setMaxAge(RandomTestUtil.nextLong());

		newPasswordPolicy.setWarningTime(RandomTestUtil.nextLong());

		newPasswordPolicy.setGraceLimit(RandomTestUtil.nextInt());

		newPasswordPolicy.setLockout(RandomTestUtil.randomBoolean());

		newPasswordPolicy.setMaxFailure(RandomTestUtil.nextInt());

		newPasswordPolicy.setLockoutDuration(RandomTestUtil.nextLong());

		newPasswordPolicy.setRequireUnlock(RandomTestUtil.randomBoolean());

		newPasswordPolicy.setResetFailureCount(RandomTestUtil.nextLong());

		newPasswordPolicy.setResetTicketMaxAge(RandomTestUtil.nextLong());

		_persistence.update(newPasswordPolicy);

		PasswordPolicy existingPasswordPolicy = _persistence.findByPrimaryKey(newPasswordPolicy.getPrimaryKey());

		Assert.assertEquals(existingPasswordPolicy.getMvccVersion(),
			newPasswordPolicy.getMvccVersion());
		Assert.assertEquals(existingPasswordPolicy.getUuid(),
			newPasswordPolicy.getUuid());
		Assert.assertEquals(existingPasswordPolicy.getPasswordPolicyId(),
			newPasswordPolicy.getPasswordPolicyId());
		Assert.assertEquals(existingPasswordPolicy.getCompanyId(),
			newPasswordPolicy.getCompanyId());
		Assert.assertEquals(existingPasswordPolicy.getUserId(),
			newPasswordPolicy.getUserId());
		Assert.assertEquals(existingPasswordPolicy.getUserName(),
			newPasswordPolicy.getUserName());
		Assert.assertEquals(Time.getShortTimestamp(
				existingPasswordPolicy.getCreateDate()),
			Time.getShortTimestamp(newPasswordPolicy.getCreateDate()));
		Assert.assertEquals(Time.getShortTimestamp(
				existingPasswordPolicy.getModifiedDate()),
			Time.getShortTimestamp(newPasswordPolicy.getModifiedDate()));
		Assert.assertEquals(existingPasswordPolicy.getDefaultPolicy(),
			newPasswordPolicy.getDefaultPolicy());
		Assert.assertEquals(existingPasswordPolicy.getName(),
			newPasswordPolicy.getName());
		Assert.assertEquals(existingPasswordPolicy.getDescription(),
			newPasswordPolicy.getDescription());
		Assert.assertEquals(existingPasswordPolicy.getChangeable(),
			newPasswordPolicy.getChangeable());
		Assert.assertEquals(existingPasswordPolicy.getChangeRequired(),
			newPasswordPolicy.getChangeRequired());
		Assert.assertEquals(existingPasswordPolicy.getMinAge(),
			newPasswordPolicy.getMinAge());
		Assert.assertEquals(existingPasswordPolicy.getCheckSyntax(),
			newPasswordPolicy.getCheckSyntax());
		Assert.assertEquals(existingPasswordPolicy.getAllowDictionaryWords(),
			newPasswordPolicy.getAllowDictionaryWords());
		Assert.assertEquals(existingPasswordPolicy.getMinAlphanumeric(),
			newPasswordPolicy.getMinAlphanumeric());
		Assert.assertEquals(existingPasswordPolicy.getMinLength(),
			newPasswordPolicy.getMinLength());
		Assert.assertEquals(existingPasswordPolicy.getMinLowerCase(),
			newPasswordPolicy.getMinLowerCase());
		Assert.assertEquals(existingPasswordPolicy.getMinNumbers(),
			newPasswordPolicy.getMinNumbers());
		Assert.assertEquals(existingPasswordPolicy.getMinSymbols(),
			newPasswordPolicy.getMinSymbols());
		Assert.assertEquals(existingPasswordPolicy.getMinUpperCase(),
			newPasswordPolicy.getMinUpperCase());
		Assert.assertEquals(existingPasswordPolicy.getRegex(),
			newPasswordPolicy.getRegex());
		Assert.assertEquals(existingPasswordPolicy.getHistory(),
			newPasswordPolicy.getHistory());
		Assert.assertEquals(existingPasswordPolicy.getHistoryCount(),
			newPasswordPolicy.getHistoryCount());
		Assert.assertEquals(existingPasswordPolicy.getExpireable(),
			newPasswordPolicy.getExpireable());
		Assert.assertEquals(existingPasswordPolicy.getMaxAge(),
			newPasswordPolicy.getMaxAge());
		Assert.assertEquals(existingPasswordPolicy.getWarningTime(),
			newPasswordPolicy.getWarningTime());
		Assert.assertEquals(existingPasswordPolicy.getGraceLimit(),
			newPasswordPolicy.getGraceLimit());
		Assert.assertEquals(existingPasswordPolicy.getLockout(),
			newPasswordPolicy.getLockout());
		Assert.assertEquals(existingPasswordPolicy.getMaxFailure(),
			newPasswordPolicy.getMaxFailure());
		Assert.assertEquals(existingPasswordPolicy.getLockoutDuration(),
			newPasswordPolicy.getLockoutDuration());
		Assert.assertEquals(existingPasswordPolicy.getRequireUnlock(),
			newPasswordPolicy.getRequireUnlock());
		Assert.assertEquals(existingPasswordPolicy.getResetFailureCount(),
			newPasswordPolicy.getResetFailureCount());
		Assert.assertEquals(existingPasswordPolicy.getResetTicketMaxAge(),
			newPasswordPolicy.getResetTicketMaxAge());
	}

	@Test
	public void testCountByUuid() {
		try {
			_persistence.countByUuid(StringPool.BLANK);

			_persistence.countByUuid(StringPool.NULL);

			_persistence.countByUuid((String)null);
		}
		catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void testCountByUuid_C() {
		try {
			_persistence.countByUuid_C(StringPool.BLANK,
				RandomTestUtil.nextLong());

			_persistence.countByUuid_C(StringPool.NULL, 0L);

			_persistence.countByUuid_C((String)null, 0L);
		}
		catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void testCountByCompanyId() {
		try {
			_persistence.countByCompanyId(RandomTestUtil.nextLong());

			_persistence.countByCompanyId(0L);
		}
		catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void testCountByC_DP() {
		try {
			_persistence.countByC_DP(RandomTestUtil.nextLong(),
				RandomTestUtil.randomBoolean());

			_persistence.countByC_DP(0L, RandomTestUtil.randomBoolean());
		}
		catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void testCountByC_N() {
		try {
			_persistence.countByC_N(RandomTestUtil.nextLong(), StringPool.BLANK);

			_persistence.countByC_N(0L, StringPool.NULL);

			_persistence.countByC_N(0L, (String)null);
		}
		catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void testFindByPrimaryKeyExisting() throws Exception {
		PasswordPolicy newPasswordPolicy = addPasswordPolicy();

		PasswordPolicy existingPasswordPolicy = _persistence.findByPrimaryKey(newPasswordPolicy.getPrimaryKey());

		Assert.assertEquals(existingPasswordPolicy, newPasswordPolicy);
	}

	@Test
	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		try {
			_persistence.findByPrimaryKey(pk);

			Assert.fail(
				"Missing entity did not throw NoSuchPasswordPolicyException");
		}
		catch (NoSuchPasswordPolicyException nsee) {
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
		return OrderByComparatorFactoryUtil.create("PasswordPolicy",
			"mvccVersion", true, "uuid", true, "passwordPolicyId", true,
			"companyId", true, "userId", true, "userName", true, "createDate",
			true, "modifiedDate", true, "defaultPolicy", true, "name", true,
			"description", true, "changeable", true, "changeRequired", true,
			"minAge", true, "checkSyntax", true, "allowDictionaryWords", true,
			"minAlphanumeric", true, "minLength", true, "minLowerCase", true,
			"minNumbers", true, "minSymbols", true, "minUpperCase", true,
			"regex", true, "history", true, "historyCount", true, "expireable",
			true, "maxAge", true, "warningTime", true, "graceLimit", true,
			"lockout", true, "maxFailure", true, "lockoutDuration", true,
			"requireUnlock", true, "resetFailureCount", true,
			"resetTicketMaxAge", true);
	}

	@Test
	public void testFetchByPrimaryKeyExisting() throws Exception {
		PasswordPolicy newPasswordPolicy = addPasswordPolicy();

		PasswordPolicy existingPasswordPolicy = _persistence.fetchByPrimaryKey(newPasswordPolicy.getPrimaryKey());

		Assert.assertEquals(existingPasswordPolicy, newPasswordPolicy);
	}

	@Test
	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		PasswordPolicy missingPasswordPolicy = _persistence.fetchByPrimaryKey(pk);

		Assert.assertNull(missingPasswordPolicy);
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereAllPrimaryKeysExist()
		throws Exception {
		PasswordPolicy newPasswordPolicy1 = addPasswordPolicy();
		PasswordPolicy newPasswordPolicy2 = addPasswordPolicy();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newPasswordPolicy1.getPrimaryKey());
		primaryKeys.add(newPasswordPolicy2.getPrimaryKey());

		Map<Serializable, PasswordPolicy> passwordPolicies = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(2, passwordPolicies.size());
		Assert.assertEquals(newPasswordPolicy1,
			passwordPolicies.get(newPasswordPolicy1.getPrimaryKey()));
		Assert.assertEquals(newPasswordPolicy2,
			passwordPolicies.get(newPasswordPolicy2.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereNoPrimaryKeysExist()
		throws Exception {
		long pk1 = RandomTestUtil.nextLong();

		long pk2 = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(pk1);
		primaryKeys.add(pk2);

		Map<Serializable, PasswordPolicy> passwordPolicies = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(passwordPolicies.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereSomePrimaryKeysExist()
		throws Exception {
		PasswordPolicy newPasswordPolicy = addPasswordPolicy();

		long pk = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newPasswordPolicy.getPrimaryKey());
		primaryKeys.add(pk);

		Map<Serializable, PasswordPolicy> passwordPolicies = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, passwordPolicies.size());
		Assert.assertEquals(newPasswordPolicy,
			passwordPolicies.get(newPasswordPolicy.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithNoPrimaryKeys()
		throws Exception {
		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		Map<Serializable, PasswordPolicy> passwordPolicies = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(passwordPolicies.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithOnePrimaryKey()
		throws Exception {
		PasswordPolicy newPasswordPolicy = addPasswordPolicy();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newPasswordPolicy.getPrimaryKey());

		Map<Serializable, PasswordPolicy> passwordPolicies = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, passwordPolicies.size());
		Assert.assertEquals(newPasswordPolicy,
			passwordPolicies.get(newPasswordPolicy.getPrimaryKey()));
	}

	@Test
	public void testActionableDynamicQuery() throws Exception {
		final IntegerWrapper count = new IntegerWrapper();

		ActionableDynamicQuery actionableDynamicQuery = PasswordPolicyLocalServiceUtil.getActionableDynamicQuery();

		actionableDynamicQuery.setPerformActionMethod(new ActionableDynamicQuery.PerformActionMethod() {
				@Override
				public void performAction(Object object) {
					PasswordPolicy passwordPolicy = (PasswordPolicy)object;

					Assert.assertNotNull(passwordPolicy);

					count.increment();
				}
			});

		actionableDynamicQuery.performActions();

		Assert.assertEquals(count.getValue(), _persistence.countAll());
	}

	@Test
	public void testDynamicQueryByPrimaryKeyExisting()
		throws Exception {
		PasswordPolicy newPasswordPolicy = addPasswordPolicy();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(PasswordPolicy.class,
				PasswordPolicy.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("passwordPolicyId",
				newPasswordPolicy.getPasswordPolicyId()));

		List<PasswordPolicy> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		PasswordPolicy existingPasswordPolicy = result.get(0);

		Assert.assertEquals(existingPasswordPolicy, newPasswordPolicy);
	}

	@Test
	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(PasswordPolicy.class,
				PasswordPolicy.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("passwordPolicyId",
				RandomTestUtil.nextLong()));

		List<PasswordPolicy> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testDynamicQueryByProjectionExisting()
		throws Exception {
		PasswordPolicy newPasswordPolicy = addPasswordPolicy();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(PasswordPolicy.class,
				PasswordPolicy.class.getClassLoader());

		dynamicQuery.setProjection(ProjectionFactoryUtil.property(
				"passwordPolicyId"));

		Object newPasswordPolicyId = newPasswordPolicy.getPasswordPolicyId();

		dynamicQuery.add(RestrictionsFactoryUtil.in("passwordPolicyId",
				new Object[] { newPasswordPolicyId }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		Object existingPasswordPolicyId = result.get(0);

		Assert.assertEquals(existingPasswordPolicyId, newPasswordPolicyId);
	}

	@Test
	public void testDynamicQueryByProjectionMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(PasswordPolicy.class,
				PasswordPolicy.class.getClassLoader());

		dynamicQuery.setProjection(ProjectionFactoryUtil.property(
				"passwordPolicyId"));

		dynamicQuery.add(RestrictionsFactoryUtil.in("passwordPolicyId",
				new Object[] { RandomTestUtil.nextLong() }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testResetOriginalValues() throws Exception {
		if (!PropsValues.HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE) {
			return;
		}

		PasswordPolicy newPasswordPolicy = addPasswordPolicy();

		_persistence.clearCache();

		PasswordPolicyModelImpl existingPasswordPolicyModelImpl = (PasswordPolicyModelImpl)_persistence.findByPrimaryKey(newPasswordPolicy.getPrimaryKey());

		Assert.assertEquals(existingPasswordPolicyModelImpl.getCompanyId(),
			existingPasswordPolicyModelImpl.getOriginalCompanyId());
		Assert.assertEquals(existingPasswordPolicyModelImpl.getDefaultPolicy(),
			existingPasswordPolicyModelImpl.getOriginalDefaultPolicy());

		Assert.assertEquals(existingPasswordPolicyModelImpl.getCompanyId(),
			existingPasswordPolicyModelImpl.getOriginalCompanyId());
		Assert.assertTrue(Validator.equals(
				existingPasswordPolicyModelImpl.getName(),
				existingPasswordPolicyModelImpl.getOriginalName()));
	}

	protected PasswordPolicy addPasswordPolicy() throws Exception {
		long pk = RandomTestUtil.nextLong();

		PasswordPolicy passwordPolicy = _persistence.create(pk);

		passwordPolicy.setMvccVersion(RandomTestUtil.nextLong());

		passwordPolicy.setUuid(RandomTestUtil.randomString());

		passwordPolicy.setCompanyId(RandomTestUtil.nextLong());

		passwordPolicy.setUserId(RandomTestUtil.nextLong());

		passwordPolicy.setUserName(RandomTestUtil.randomString());

		passwordPolicy.setCreateDate(RandomTestUtil.nextDate());

		passwordPolicy.setModifiedDate(RandomTestUtil.nextDate());

		passwordPolicy.setDefaultPolicy(RandomTestUtil.randomBoolean());

		passwordPolicy.setName(RandomTestUtil.randomString());

		passwordPolicy.setDescription(RandomTestUtil.randomString());

		passwordPolicy.setChangeable(RandomTestUtil.randomBoolean());

		passwordPolicy.setChangeRequired(RandomTestUtil.randomBoolean());

		passwordPolicy.setMinAge(RandomTestUtil.nextLong());

		passwordPolicy.setCheckSyntax(RandomTestUtil.randomBoolean());

		passwordPolicy.setAllowDictionaryWords(RandomTestUtil.randomBoolean());

		passwordPolicy.setMinAlphanumeric(RandomTestUtil.nextInt());

		passwordPolicy.setMinLength(RandomTestUtil.nextInt());

		passwordPolicy.setMinLowerCase(RandomTestUtil.nextInt());

		passwordPolicy.setMinNumbers(RandomTestUtil.nextInt());

		passwordPolicy.setMinSymbols(RandomTestUtil.nextInt());

		passwordPolicy.setMinUpperCase(RandomTestUtil.nextInt());

		passwordPolicy.setRegex(RandomTestUtil.randomString());

		passwordPolicy.setHistory(RandomTestUtil.randomBoolean());

		passwordPolicy.setHistoryCount(RandomTestUtil.nextInt());

		passwordPolicy.setExpireable(RandomTestUtil.randomBoolean());

		passwordPolicy.setMaxAge(RandomTestUtil.nextLong());

		passwordPolicy.setWarningTime(RandomTestUtil.nextLong());

		passwordPolicy.setGraceLimit(RandomTestUtil.nextInt());

		passwordPolicy.setLockout(RandomTestUtil.randomBoolean());

		passwordPolicy.setMaxFailure(RandomTestUtil.nextInt());

		passwordPolicy.setLockoutDuration(RandomTestUtil.nextLong());

		passwordPolicy.setRequireUnlock(RandomTestUtil.randomBoolean());

		passwordPolicy.setResetFailureCount(RandomTestUtil.nextLong());

		passwordPolicy.setResetTicketMaxAge(RandomTestUtil.nextLong());

		_persistence.update(passwordPolicy);

		return passwordPolicy;
	}

	private static Log _log = LogFactoryUtil.getLog(PasswordPolicyPersistenceTest.class);
	private ModelListener<PasswordPolicy>[] _modelListeners;
	private PasswordPolicyPersistence _persistence = (PasswordPolicyPersistence)PortalBeanLocatorUtil.locate(PasswordPolicyPersistence.class.getName());
	private TransactionalPersistenceAdvice _transactionalPersistenceAdvice = (TransactionalPersistenceAdvice)PortalBeanLocatorUtil.locate(TransactionalPersistenceAdvice.class.getName());
}