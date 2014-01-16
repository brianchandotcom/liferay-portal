/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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
import com.liferay.portal.kernel.util.Time;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.PasswordPolicy;
import com.liferay.portal.model.impl.PasswordPolicyModelImpl;
import com.liferay.portal.service.ServiceTestUtil;
import com.liferay.portal.service.persistence.BasePersistence;
import com.liferay.portal.service.persistence.PersistenceExecutionTestListener;
import com.liferay.portal.test.LiferayPersistenceIntegrationJUnitTestRunner;
import com.liferay.portal.test.persistence.TransactionalPersistenceAdvice;
import com.liferay.portal.util.PropsValues;

import org.junit.After;
import org.junit.Assert;
import org.junit.Test;

import org.junit.runner.RunWith;

import java.io.Serializable;

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
	}

	@Test
	public void testCreate() throws Exception {
		long pk = ServiceTestUtil.nextLong();

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
		long pk = ServiceTestUtil.nextLong();

		PasswordPolicy newPasswordPolicy = _persistence.create(pk);

		newPasswordPolicy.setUuid(ServiceTestUtil.randomString());

		newPasswordPolicy.setCompanyId(ServiceTestUtil.nextLong());

		newPasswordPolicy.setUserId(ServiceTestUtil.nextLong());

		newPasswordPolicy.setUserName(ServiceTestUtil.randomString());

		newPasswordPolicy.setCreateDate(ServiceTestUtil.nextDate());

		newPasswordPolicy.setModifiedDate(ServiceTestUtil.nextDate());

		newPasswordPolicy.setDefaultPolicy(ServiceTestUtil.randomBoolean());

		newPasswordPolicy.setName(ServiceTestUtil.randomString());

		newPasswordPolicy.setDescription(ServiceTestUtil.randomString());

		newPasswordPolicy.setChangeable(ServiceTestUtil.randomBoolean());

		newPasswordPolicy.setChangeRequired(ServiceTestUtil.randomBoolean());

		newPasswordPolicy.setMinAge(ServiceTestUtil.nextLong());

		newPasswordPolicy.setCheckSyntax(ServiceTestUtil.randomBoolean());

		newPasswordPolicy.setAllowDictionaryWords(ServiceTestUtil.randomBoolean());

		newPasswordPolicy.setMinAlphanumeric(ServiceTestUtil.nextInt());

		newPasswordPolicy.setMinLength(ServiceTestUtil.nextInt());

		newPasswordPolicy.setMinLowerCase(ServiceTestUtil.nextInt());

		newPasswordPolicy.setMinNumbers(ServiceTestUtil.nextInt());

		newPasswordPolicy.setMinSymbols(ServiceTestUtil.nextInt());

		newPasswordPolicy.setMinUpperCase(ServiceTestUtil.nextInt());

		newPasswordPolicy.setRegex(ServiceTestUtil.randomString());

		newPasswordPolicy.setHistory(ServiceTestUtil.randomBoolean());

		newPasswordPolicy.setHistoryCount(ServiceTestUtil.nextInt());

		newPasswordPolicy.setExpireable(ServiceTestUtil.randomBoolean());

		newPasswordPolicy.setMaxAge(ServiceTestUtil.nextLong());

		newPasswordPolicy.setWarningTime(ServiceTestUtil.nextLong());

		newPasswordPolicy.setGraceLimit(ServiceTestUtil.nextInt());

		newPasswordPolicy.setLockout(ServiceTestUtil.randomBoolean());

		newPasswordPolicy.setMaxFailure(ServiceTestUtil.nextInt());

		newPasswordPolicy.setLockoutDuration(ServiceTestUtil.nextLong());

		newPasswordPolicy.setRequireUnlock(ServiceTestUtil.randomBoolean());

		newPasswordPolicy.setResetFailureCount(ServiceTestUtil.nextLong());

		newPasswordPolicy.setResetTicketMaxAge(ServiceTestUtil.nextLong());

		newPasswordPolicy.setMvccVersion(ServiceTestUtil.nextLong());

		_persistence.update(newPasswordPolicy);

		PasswordPolicy existingPasswordPolicy = _persistence.findByPrimaryKey(newPasswordPolicy.getPrimaryKey());

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
		Assert.assertEquals(existingPasswordPolicy.getMvccVersion(),
			newPasswordPolicy.getMvccVersion());
	}

	@Test
	public void testFindByPrimaryKeyExisting() throws Exception {
		PasswordPolicy newPasswordPolicy = addPasswordPolicy();

		PasswordPolicy existingPasswordPolicy = _persistence.findByPrimaryKey(newPasswordPolicy.getPrimaryKey());

		Assert.assertEquals(existingPasswordPolicy, newPasswordPolicy);
	}

	@Test
	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = ServiceTestUtil.nextLong();

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
		return OrderByComparatorFactoryUtil.create("PasswordPolicy", "uuid",
			true, "passwordPolicyId", true, "companyId", true, "userId", true,
			"userName", true, "createDate", true, "modifiedDate", true,
			"defaultPolicy", true, "name", true, "description", true,
			"changeable", true, "changeRequired", true, "minAge", true,
			"checkSyntax", true, "allowDictionaryWords", true,
			"minAlphanumeric", true, "minLength", true, "minLowerCase", true,
			"minNumbers", true, "minSymbols", true, "minUpperCase", true,
			"regex", true, "history", true, "historyCount", true, "expireable",
			true, "maxAge", true, "warningTime", true, "graceLimit", true,
			"lockout", true, "maxFailure", true, "lockoutDuration", true,
			"requireUnlock", true, "resetFailureCount", true,
			"resetTicketMaxAge", true, "mvccVersion", true);
	}

	@Test
	public void testFetchByPrimaryKeyExisting() throws Exception {
		PasswordPolicy newPasswordPolicy = addPasswordPolicy();

		PasswordPolicy existingPasswordPolicy = _persistence.fetchByPrimaryKey(newPasswordPolicy.getPrimaryKey());

		Assert.assertEquals(existingPasswordPolicy, newPasswordPolicy);
	}

	@Test
	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = ServiceTestUtil.nextLong();

		PasswordPolicy missingPasswordPolicy = _persistence.fetchByPrimaryKey(pk);

		Assert.assertNull(missingPasswordPolicy);
	}

	@Test
	public void testActionableDynamicQuery() throws Exception {
		final IntegerWrapper count = new IntegerWrapper();

		ActionableDynamicQuery actionableDynamicQuery = new PasswordPolicyActionableDynamicQuery() {
				@Override
				protected void performAction(Object object) {
					PasswordPolicy passwordPolicy = (PasswordPolicy)object;

					Assert.assertNotNull(passwordPolicy);

					count.increment();
				}
			};

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
				ServiceTestUtil.nextLong()));

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
				new Object[] { ServiceTestUtil.nextLong() }));

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
		long pk = ServiceTestUtil.nextLong();

		PasswordPolicy passwordPolicy = _persistence.create(pk);

		passwordPolicy.setUuid(ServiceTestUtil.randomString());

		passwordPolicy.setCompanyId(ServiceTestUtil.nextLong());

		passwordPolicy.setUserId(ServiceTestUtil.nextLong());

		passwordPolicy.setUserName(ServiceTestUtil.randomString());

		passwordPolicy.setCreateDate(ServiceTestUtil.nextDate());

		passwordPolicy.setModifiedDate(ServiceTestUtil.nextDate());

		passwordPolicy.setDefaultPolicy(ServiceTestUtil.randomBoolean());

		passwordPolicy.setName(ServiceTestUtil.randomString());

		passwordPolicy.setDescription(ServiceTestUtil.randomString());

		passwordPolicy.setChangeable(ServiceTestUtil.randomBoolean());

		passwordPolicy.setChangeRequired(ServiceTestUtil.randomBoolean());

		passwordPolicy.setMinAge(ServiceTestUtil.nextLong());

		passwordPolicy.setCheckSyntax(ServiceTestUtil.randomBoolean());

		passwordPolicy.setAllowDictionaryWords(ServiceTestUtil.randomBoolean());

		passwordPolicy.setMinAlphanumeric(ServiceTestUtil.nextInt());

		passwordPolicy.setMinLength(ServiceTestUtil.nextInt());

		passwordPolicy.setMinLowerCase(ServiceTestUtil.nextInt());

		passwordPolicy.setMinNumbers(ServiceTestUtil.nextInt());

		passwordPolicy.setMinSymbols(ServiceTestUtil.nextInt());

		passwordPolicy.setMinUpperCase(ServiceTestUtil.nextInt());

		passwordPolicy.setRegex(ServiceTestUtil.randomString());

		passwordPolicy.setHistory(ServiceTestUtil.randomBoolean());

		passwordPolicy.setHistoryCount(ServiceTestUtil.nextInt());

		passwordPolicy.setExpireable(ServiceTestUtil.randomBoolean());

		passwordPolicy.setMaxAge(ServiceTestUtil.nextLong());

		passwordPolicy.setWarningTime(ServiceTestUtil.nextLong());

		passwordPolicy.setGraceLimit(ServiceTestUtil.nextInt());

		passwordPolicy.setLockout(ServiceTestUtil.randomBoolean());

		passwordPolicy.setMaxFailure(ServiceTestUtil.nextInt());

		passwordPolicy.setLockoutDuration(ServiceTestUtil.nextLong());

		passwordPolicy.setRequireUnlock(ServiceTestUtil.randomBoolean());

		passwordPolicy.setResetFailureCount(ServiceTestUtil.nextLong());

		passwordPolicy.setResetTicketMaxAge(ServiceTestUtil.nextLong());

		passwordPolicy.setMvccVersion(ServiceTestUtil.nextLong());

		_persistence.update(passwordPolicy);

		return passwordPolicy;
	}

	private static Log _log = LogFactoryUtil.getLog(PasswordPolicyPersistenceTest.class);
	private PasswordPolicyPersistence _persistence = (PasswordPolicyPersistence)PortalBeanLocatorUtil.locate(PasswordPolicyPersistence.class.getName());
	private TransactionalPersistenceAdvice _transactionalPersistenceAdvice = (TransactionalPersistenceAdvice)PortalBeanLocatorUtil.locate(TransactionalPersistenceAdvice.class.getName());
}