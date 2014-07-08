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

import com.liferay.portal.NoSuchUserException;
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
import com.liferay.portal.model.User;
import com.liferay.portal.model.impl.UserModelImpl;
import com.liferay.portal.service.UserLocalServiceUtil;
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
public class UserPersistenceTest {
	@Before
	public void setUp() {
		_modelListeners = _persistence.getListeners();

		for (ModelListener<User> modelListener : _modelListeners) {
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

		for (ModelListener<User> modelListener : _modelListeners) {
			_persistence.registerListener(modelListener);
		}
	}

	@Test
	public void testCreate() throws Exception {
		long pk = RandomTestUtil.nextLong();

		User user = _persistence.create(pk);

		Assert.assertNotNull(user);

		Assert.assertEquals(user.getPrimaryKey(), pk);
	}

	@Test
	public void testRemove() throws Exception {
		User newUser = addUser();

		_persistence.remove(newUser);

		User existingUser = _persistence.fetchByPrimaryKey(newUser.getPrimaryKey());

		Assert.assertNull(existingUser);
	}

	@Test
	public void testUpdateNew() throws Exception {
		addUser();
	}

	@Test
	public void testUpdateExisting() throws Exception {
		long pk = RandomTestUtil.nextLong();

		User newUser = _persistence.create(pk);

		newUser.setMvccVersion(RandomTestUtil.nextLong());

		newUser.setUuid(RandomTestUtil.randomString());

		newUser.setCompanyId(RandomTestUtil.nextLong());

		newUser.setCreateDate(RandomTestUtil.nextDate());

		newUser.setModifiedDate(RandomTestUtil.nextDate());

		newUser.setDefaultUser(RandomTestUtil.randomBoolean());

		newUser.setContactId(RandomTestUtil.nextLong());

		newUser.setPassword(RandomTestUtil.randomString());

		newUser.setPasswordEncrypted(RandomTestUtil.randomBoolean());

		newUser.setPasswordReset(RandomTestUtil.randomBoolean());

		newUser.setPasswordModifiedDate(RandomTestUtil.nextDate());

		newUser.setDigest(RandomTestUtil.randomString());

		newUser.setReminderQueryQuestion(RandomTestUtil.randomString());

		newUser.setReminderQueryAnswer(RandomTestUtil.randomString());

		newUser.setGraceLoginCount(RandomTestUtil.nextInt());

		newUser.setScreenName(RandomTestUtil.randomString());

		newUser.setEmailAddress(RandomTestUtil.randomString());

		newUser.setFacebookId(RandomTestUtil.nextLong());

		newUser.setLdapServerId(RandomTestUtil.nextLong());

		newUser.setOpenId(RandomTestUtil.randomString());

		newUser.setPortraitId(RandomTestUtil.nextLong());

		newUser.setLanguageId(RandomTestUtil.randomString());

		newUser.setTimeZoneId(RandomTestUtil.randomString());

		newUser.setGreeting(RandomTestUtil.randomString());

		newUser.setComments(RandomTestUtil.randomString());

		newUser.setFirstName(RandomTestUtil.randomString());

		newUser.setMiddleName(RandomTestUtil.randomString());

		newUser.setLastName(RandomTestUtil.randomString());

		newUser.setJobTitle(RandomTestUtil.randomString());

		newUser.setLoginDate(RandomTestUtil.nextDate());

		newUser.setLoginIP(RandomTestUtil.randomString());

		newUser.setLastLoginDate(RandomTestUtil.nextDate());

		newUser.setLastLoginIP(RandomTestUtil.randomString());

		newUser.setLastFailedLoginDate(RandomTestUtil.nextDate());

		newUser.setFailedLoginAttempts(RandomTestUtil.nextInt());

		newUser.setLockout(RandomTestUtil.randomBoolean());

		newUser.setLockoutDate(RandomTestUtil.nextDate());

		newUser.setAgreedToTermsOfUse(RandomTestUtil.randomBoolean());

		newUser.setEmailAddressVerified(RandomTestUtil.randomBoolean());

		newUser.setStatus(RandomTestUtil.nextInt());

		_persistence.update(newUser);

		User existingUser = _persistence.findByPrimaryKey(newUser.getPrimaryKey());

		Assert.assertEquals(existingUser.getMvccVersion(),
			newUser.getMvccVersion());
		Assert.assertEquals(existingUser.getUuid(), newUser.getUuid());
		Assert.assertEquals(existingUser.getUserId(), newUser.getUserId());
		Assert.assertEquals(existingUser.getCompanyId(), newUser.getCompanyId());
		Assert.assertEquals(Time.getShortTimestamp(existingUser.getCreateDate()),
			Time.getShortTimestamp(newUser.getCreateDate()));
		Assert.assertEquals(Time.getShortTimestamp(
				existingUser.getModifiedDate()),
			Time.getShortTimestamp(newUser.getModifiedDate()));
		Assert.assertEquals(existingUser.getDefaultUser(),
			newUser.getDefaultUser());
		Assert.assertEquals(existingUser.getContactId(), newUser.getContactId());
		Assert.assertEquals(existingUser.getPassword(), newUser.getPassword());
		Assert.assertEquals(existingUser.getPasswordEncrypted(),
			newUser.getPasswordEncrypted());
		Assert.assertEquals(existingUser.getPasswordReset(),
			newUser.getPasswordReset());
		Assert.assertEquals(Time.getShortTimestamp(
				existingUser.getPasswordModifiedDate()),
			Time.getShortTimestamp(newUser.getPasswordModifiedDate()));
		Assert.assertEquals(existingUser.getDigest(), newUser.getDigest());
		Assert.assertEquals(existingUser.getReminderQueryQuestion(),
			newUser.getReminderQueryQuestion());
		Assert.assertEquals(existingUser.getReminderQueryAnswer(),
			newUser.getReminderQueryAnswer());
		Assert.assertEquals(existingUser.getGraceLoginCount(),
			newUser.getGraceLoginCount());
		Assert.assertEquals(existingUser.getScreenName(),
			newUser.getScreenName());
		Assert.assertEquals(existingUser.getEmailAddress(),
			newUser.getEmailAddress());
		Assert.assertEquals(existingUser.getFacebookId(),
			newUser.getFacebookId());
		Assert.assertEquals(existingUser.getLdapServerId(),
			newUser.getLdapServerId());
		Assert.assertEquals(existingUser.getOpenId(), newUser.getOpenId());
		Assert.assertEquals(existingUser.getPortraitId(),
			newUser.getPortraitId());
		Assert.assertEquals(existingUser.getLanguageId(),
			newUser.getLanguageId());
		Assert.assertEquals(existingUser.getTimeZoneId(),
			newUser.getTimeZoneId());
		Assert.assertEquals(existingUser.getGreeting(), newUser.getGreeting());
		Assert.assertEquals(existingUser.getComments(), newUser.getComments());
		Assert.assertEquals(existingUser.getFirstName(), newUser.getFirstName());
		Assert.assertEquals(existingUser.getMiddleName(),
			newUser.getMiddleName());
		Assert.assertEquals(existingUser.getLastName(), newUser.getLastName());
		Assert.assertEquals(existingUser.getJobTitle(), newUser.getJobTitle());
		Assert.assertEquals(Time.getShortTimestamp(existingUser.getLoginDate()),
			Time.getShortTimestamp(newUser.getLoginDate()));
		Assert.assertEquals(existingUser.getLoginIP(), newUser.getLoginIP());
		Assert.assertEquals(Time.getShortTimestamp(
				existingUser.getLastLoginDate()),
			Time.getShortTimestamp(newUser.getLastLoginDate()));
		Assert.assertEquals(existingUser.getLastLoginIP(),
			newUser.getLastLoginIP());
		Assert.assertEquals(Time.getShortTimestamp(
				existingUser.getLastFailedLoginDate()),
			Time.getShortTimestamp(newUser.getLastFailedLoginDate()));
		Assert.assertEquals(existingUser.getFailedLoginAttempts(),
			newUser.getFailedLoginAttempts());
		Assert.assertEquals(existingUser.getLockout(), newUser.getLockout());
		Assert.assertEquals(Time.getShortTimestamp(
				existingUser.getLockoutDate()),
			Time.getShortTimestamp(newUser.getLockoutDate()));
		Assert.assertEquals(existingUser.getAgreedToTermsOfUse(),
			newUser.getAgreedToTermsOfUse());
		Assert.assertEquals(existingUser.getEmailAddressVerified(),
			newUser.getEmailAddressVerified());
		Assert.assertEquals(existingUser.getStatus(), newUser.getStatus());
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
	public void testCountByContactId() {
		try {
			_persistence.countByContactId(RandomTestUtil.nextLong());

			_persistence.countByContactId(0L);
		}
		catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void testCountByEmailAddress() {
		try {
			_persistence.countByEmailAddress(StringPool.BLANK);

			_persistence.countByEmailAddress(StringPool.NULL);

			_persistence.countByEmailAddress((String)null);
		}
		catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void testCountByPortraitId() {
		try {
			_persistence.countByPortraitId(RandomTestUtil.nextLong());

			_persistence.countByPortraitId(0L);
		}
		catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void testCountByC_U() {
		try {
			_persistence.countByC_U(RandomTestUtil.nextLong(),
				RandomTestUtil.nextLong());

			_persistence.countByC_U(0L, 0L);
		}
		catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void testCountByC_CD() {
		try {
			_persistence.countByC_CD(RandomTestUtil.nextLong(),
				RandomTestUtil.nextDate());

			_persistence.countByC_CD(0L, RandomTestUtil.nextDate());
		}
		catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void testCountByC_MD() {
		try {
			_persistence.countByC_MD(RandomTestUtil.nextLong(),
				RandomTestUtil.nextDate());

			_persistence.countByC_MD(0L, RandomTestUtil.nextDate());
		}
		catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void testCountByC_DU() {
		try {
			_persistence.countByC_DU(RandomTestUtil.nextLong(),
				RandomTestUtil.randomBoolean());

			_persistence.countByC_DU(0L, RandomTestUtil.randomBoolean());
		}
		catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void testCountByC_SN() {
		try {
			_persistence.countByC_SN(RandomTestUtil.nextLong(), StringPool.BLANK);

			_persistence.countByC_SN(0L, StringPool.NULL);

			_persistence.countByC_SN(0L, (String)null);
		}
		catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void testCountByC_EA() {
		try {
			_persistence.countByC_EA(RandomTestUtil.nextLong(), StringPool.BLANK);

			_persistence.countByC_EA(0L, StringPool.NULL);

			_persistence.countByC_EA(0L, (String)null);
		}
		catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void testCountByC_FID() {
		try {
			_persistence.countByC_FID(RandomTestUtil.nextLong(),
				RandomTestUtil.nextLong());

			_persistence.countByC_FID(0L, 0L);
		}
		catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void testCountByC_O() {
		try {
			_persistence.countByC_O(RandomTestUtil.nextLong(), StringPool.BLANK);

			_persistence.countByC_O(0L, StringPool.NULL);

			_persistence.countByC_O(0L, (String)null);
		}
		catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void testCountByC_S() {
		try {
			_persistence.countByC_S(RandomTestUtil.nextLong(),
				RandomTestUtil.nextInt());

			_persistence.countByC_S(0L, 0);
		}
		catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void testCountByC_CD_MD() {
		try {
			_persistence.countByC_CD_MD(RandomTestUtil.nextLong(),
				RandomTestUtil.nextDate(), RandomTestUtil.nextDate());

			_persistence.countByC_CD_MD(0L, RandomTestUtil.nextDate(),
				RandomTestUtil.nextDate());
		}
		catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void testCountByC_DU_S() {
		try {
			_persistence.countByC_DU_S(RandomTestUtil.nextLong(),
				RandomTestUtil.randomBoolean(), RandomTestUtil.nextInt());

			_persistence.countByC_DU_S(0L, RandomTestUtil.randomBoolean(), 0);
		}
		catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void testFindByPrimaryKeyExisting() throws Exception {
		User newUser = addUser();

		User existingUser = _persistence.findByPrimaryKey(newUser.getPrimaryKey());

		Assert.assertEquals(existingUser, newUser);
	}

	@Test
	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		try {
			_persistence.findByPrimaryKey(pk);

			Assert.fail("Missing entity did not throw NoSuchUserException");
		}
		catch (NoSuchUserException nsee) {
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
		return OrderByComparatorFactoryUtil.create("User_", "mvccVersion",
			true, "uuid", true, "userId", true, "companyId", true,
			"createDate", true, "modifiedDate", true, "defaultUser", true,
			"contactId", true, "password", true, "passwordEncrypted", true,
			"passwordReset", true, "passwordModifiedDate", true, "digest",
			true, "reminderQueryQuestion", true, "reminderQueryAnswer", true,
			"graceLoginCount", true, "screenName", true, "emailAddress", true,
			"facebookId", true, "ldapServerId", true, "openId", true,
			"portraitId", true, "languageId", true, "timeZoneId", true,
			"greeting", true, "comments", true, "firstName", true,
			"middleName", true, "lastName", true, "jobTitle", true,
			"loginDate", true, "loginIP", true, "lastLoginDate", true,
			"lastLoginIP", true, "lastFailedLoginDate", true,
			"failedLoginAttempts", true, "lockout", true, "lockoutDate", true,
			"agreedToTermsOfUse", true, "emailAddressVerified", true, "status",
			true);
	}

	@Test
	public void testFetchByPrimaryKeyExisting() throws Exception {
		User newUser = addUser();

		User existingUser = _persistence.fetchByPrimaryKey(newUser.getPrimaryKey());

		Assert.assertEquals(existingUser, newUser);
	}

	@Test
	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		User missingUser = _persistence.fetchByPrimaryKey(pk);

		Assert.assertNull(missingUser);
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereAllPrimaryKeysExist()
		throws Exception {
		User newUser1 = addUser();
		User newUser2 = addUser();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newUser1.getPrimaryKey());
		primaryKeys.add(newUser2.getPrimaryKey());

		Map<Serializable, User> users = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(2, users.size());
		Assert.assertEquals(newUser1, users.get(newUser1.getPrimaryKey()));
		Assert.assertEquals(newUser2, users.get(newUser2.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereNoPrimaryKeysExist()
		throws Exception {
		long pk1 = RandomTestUtil.nextLong();

		long pk2 = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(pk1);
		primaryKeys.add(pk2);

		Map<Serializable, User> users = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(users.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereSomePrimaryKeysExist()
		throws Exception {
		User newUser = addUser();

		long pk = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newUser.getPrimaryKey());
		primaryKeys.add(pk);

		Map<Serializable, User> users = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, users.size());
		Assert.assertEquals(newUser, users.get(newUser.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithNoPrimaryKeys()
		throws Exception {
		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		Map<Serializable, User> users = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(users.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithOnePrimaryKey()
		throws Exception {
		User newUser = addUser();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newUser.getPrimaryKey());

		Map<Serializable, User> users = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, users.size());
		Assert.assertEquals(newUser, users.get(newUser.getPrimaryKey()));
	}

	@Test
	public void testActionableDynamicQuery() throws Exception {
		final IntegerWrapper count = new IntegerWrapper();

		ActionableDynamicQuery actionableDynamicQuery = UserLocalServiceUtil.getActionableDynamicQuery();

		actionableDynamicQuery.setPerformActionMethod(new ActionableDynamicQuery.PerformActionMethod() {
				@Override
				public void performAction(Object object) {
					User user = (User)object;

					Assert.assertNotNull(user);

					count.increment();
				}
			});

		actionableDynamicQuery.performActions();

		Assert.assertEquals(count.getValue(), _persistence.countAll());
	}

	@Test
	public void testDynamicQueryByPrimaryKeyExisting()
		throws Exception {
		User newUser = addUser();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(User.class,
				User.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("userId",
				newUser.getUserId()));

		List<User> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		User existingUser = result.get(0);

		Assert.assertEquals(existingUser, newUser);
	}

	@Test
	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(User.class,
				User.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("userId",
				RandomTestUtil.nextLong()));

		List<User> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testDynamicQueryByProjectionExisting()
		throws Exception {
		User newUser = addUser();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(User.class,
				User.class.getClassLoader());

		dynamicQuery.setProjection(ProjectionFactoryUtil.property("userId"));

		Object newUserId = newUser.getUserId();

		dynamicQuery.add(RestrictionsFactoryUtil.in("userId",
				new Object[] { newUserId }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		Object existingUserId = result.get(0);

		Assert.assertEquals(existingUserId, newUserId);
	}

	@Test
	public void testDynamicQueryByProjectionMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(User.class,
				User.class.getClassLoader());

		dynamicQuery.setProjection(ProjectionFactoryUtil.property("userId"));

		dynamicQuery.add(RestrictionsFactoryUtil.in("userId",
				new Object[] { RandomTestUtil.nextLong() }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testResetOriginalValues() throws Exception {
		if (!PropsValues.HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE) {
			return;
		}

		User newUser = addUser();

		_persistence.clearCache();

		UserModelImpl existingUserModelImpl = (UserModelImpl)_persistence.findByPrimaryKey(newUser.getPrimaryKey());

		Assert.assertEquals(existingUserModelImpl.getContactId(),
			existingUserModelImpl.getOriginalContactId());

		Assert.assertEquals(existingUserModelImpl.getPortraitId(),
			existingUserModelImpl.getOriginalPortraitId());

		Assert.assertEquals(existingUserModelImpl.getCompanyId(),
			existingUserModelImpl.getOriginalCompanyId());
		Assert.assertEquals(existingUserModelImpl.getUserId(),
			existingUserModelImpl.getOriginalUserId());

		Assert.assertEquals(existingUserModelImpl.getCompanyId(),
			existingUserModelImpl.getOriginalCompanyId());
		Assert.assertEquals(existingUserModelImpl.getDefaultUser(),
			existingUserModelImpl.getOriginalDefaultUser());

		Assert.assertEquals(existingUserModelImpl.getCompanyId(),
			existingUserModelImpl.getOriginalCompanyId());
		Assert.assertTrue(Validator.equals(
				existingUserModelImpl.getScreenName(),
				existingUserModelImpl.getOriginalScreenName()));

		Assert.assertEquals(existingUserModelImpl.getCompanyId(),
			existingUserModelImpl.getOriginalCompanyId());
		Assert.assertTrue(Validator.equals(
				existingUserModelImpl.getEmailAddress(),
				existingUserModelImpl.getOriginalEmailAddress()));

		Assert.assertEquals(existingUserModelImpl.getCompanyId(),
			existingUserModelImpl.getOriginalCompanyId());
		Assert.assertEquals(existingUserModelImpl.getFacebookId(),
			existingUserModelImpl.getOriginalFacebookId());

		Assert.assertEquals(existingUserModelImpl.getCompanyId(),
			existingUserModelImpl.getOriginalCompanyId());
		Assert.assertTrue(Validator.equals(existingUserModelImpl.getOpenId(),
				existingUserModelImpl.getOriginalOpenId()));
	}

	protected User addUser() throws Exception {
		long pk = RandomTestUtil.nextLong();

		User user = _persistence.create(pk);

		user.setMvccVersion(RandomTestUtil.nextLong());

		user.setUuid(RandomTestUtil.randomString());

		user.setCompanyId(RandomTestUtil.nextLong());

		user.setCreateDate(RandomTestUtil.nextDate());

		user.setModifiedDate(RandomTestUtil.nextDate());

		user.setDefaultUser(RandomTestUtil.randomBoolean());

		user.setContactId(RandomTestUtil.nextLong());

		user.setPassword(RandomTestUtil.randomString());

		user.setPasswordEncrypted(RandomTestUtil.randomBoolean());

		user.setPasswordReset(RandomTestUtil.randomBoolean());

		user.setPasswordModifiedDate(RandomTestUtil.nextDate());

		user.setDigest(RandomTestUtil.randomString());

		user.setReminderQueryQuestion(RandomTestUtil.randomString());

		user.setReminderQueryAnswer(RandomTestUtil.randomString());

		user.setGraceLoginCount(RandomTestUtil.nextInt());

		user.setScreenName(RandomTestUtil.randomString());

		user.setEmailAddress(RandomTestUtil.randomString());

		user.setFacebookId(RandomTestUtil.nextLong());

		user.setLdapServerId(RandomTestUtil.nextLong());

		user.setOpenId(RandomTestUtil.randomString());

		user.setPortraitId(RandomTestUtil.nextLong());

		user.setLanguageId(RandomTestUtil.randomString());

		user.setTimeZoneId(RandomTestUtil.randomString());

		user.setGreeting(RandomTestUtil.randomString());

		user.setComments(RandomTestUtil.randomString());

		user.setFirstName(RandomTestUtil.randomString());

		user.setMiddleName(RandomTestUtil.randomString());

		user.setLastName(RandomTestUtil.randomString());

		user.setJobTitle(RandomTestUtil.randomString());

		user.setLoginDate(RandomTestUtil.nextDate());

		user.setLoginIP(RandomTestUtil.randomString());

		user.setLastLoginDate(RandomTestUtil.nextDate());

		user.setLastLoginIP(RandomTestUtil.randomString());

		user.setLastFailedLoginDate(RandomTestUtil.nextDate());

		user.setFailedLoginAttempts(RandomTestUtil.nextInt());

		user.setLockout(RandomTestUtil.randomBoolean());

		user.setLockoutDate(RandomTestUtil.nextDate());

		user.setAgreedToTermsOfUse(RandomTestUtil.randomBoolean());

		user.setEmailAddressVerified(RandomTestUtil.randomBoolean());

		user.setStatus(RandomTestUtil.nextInt());

		_persistence.update(user);

		return user;
	}

	private static Log _log = LogFactoryUtil.getLog(UserPersistenceTest.class);
	private ModelListener<User>[] _modelListeners;
	private UserPersistence _persistence = (UserPersistence)PortalBeanLocatorUtil.locate(UserPersistence.class.getName());
	private TransactionalPersistenceAdvice _transactionalPersistenceAdvice = (TransactionalPersistenceAdvice)PortalBeanLocatorUtil.locate(TransactionalPersistenceAdvice.class.getName());
}