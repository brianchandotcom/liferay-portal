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

import com.liferay.portal.NoSuchUserException;
import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQueryFactoryUtil;
import com.liferay.portal.kernel.dao.orm.ProjectionFactoryUtil;
import com.liferay.portal.kernel.dao.orm.RestrictionsFactoryUtil;
import com.liferay.portal.kernel.util.Time;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.User;
import com.liferay.portal.model.impl.UserModelImpl;
import com.liferay.portal.service.persistence.BasePersistenceTestCase;
import com.liferay.portal.util.PropsValues;

import java.util.List;

/**
 * @author Brian Wing Shun Chan
 */
public class UserPersistenceTest extends BasePersistenceTestCase {
	@Override
	public void setUp() throws Exception {
		super.setUp();

		_persistence = (UserPersistence)PortalBeanLocatorUtil.locate(UserPersistence.class.getName());
	}

	public void testCreate() throws Exception {
		long pk = nextLong();

		User user = _persistence.create(pk);

		assertNotNull(user);

		assertEquals(user.getPrimaryKey(), pk);
	}

	public void testRemove() throws Exception {
		User newUser = addUser();

		_persistence.remove(newUser);

		User existingUser = _persistence.fetchByPrimaryKey(newUser.getPrimaryKey());

		assertNull(existingUser);
	}

	public void testUpdateNew() throws Exception {
		addUser();
	}

	public void testUpdateExisting() throws Exception {
		long pk = nextLong();

		User newUser = _persistence.create(pk);

		newUser.setUuid(randomString());

		newUser.setCompanyId(nextLong());

		newUser.setCreateDate(nextDate());

		newUser.setModifiedDate(nextDate());

		newUser.setDefaultUser(randomBoolean());

		newUser.setContactId(nextLong());

		newUser.setPassword(randomString());

		newUser.setPasswordEncrypted(randomBoolean());

		newUser.setPasswordReset(randomBoolean());

		newUser.setPasswordModifiedDate(nextDate());

		newUser.setDigest(randomString());

		newUser.setReminderQueryQuestion(randomString());

		newUser.setReminderQueryAnswer(randomString());

		newUser.setGraceLoginCount(nextInt());

		newUser.setScreenName(randomString());

		newUser.setEmailAddress(randomString());

		newUser.setFacebookId(nextLong());

		newUser.setOpenId(randomString());

		newUser.setPortraitId(nextLong());

		newUser.setLanguageId(randomString());

		newUser.setTimeZoneId(randomString());

		newUser.setGreeting(randomString());

		newUser.setComments(randomString());

		newUser.setFirstName(randomString());

		newUser.setMiddleName(randomString());

		newUser.setLastName(randomString());

		newUser.setJobTitle(randomString());

		newUser.setLoginDate(nextDate());

		newUser.setLoginIP(randomString());

		newUser.setLastLoginDate(nextDate());

		newUser.setLastLoginIP(randomString());

		newUser.setLastFailedLoginDate(nextDate());

		newUser.setFailedLoginAttempts(nextInt());

		newUser.setLockout(randomBoolean());

		newUser.setLockoutDate(nextDate());

		newUser.setAgreedToTermsOfUse(randomBoolean());

		newUser.setEmailAddressVerified(randomBoolean());

		newUser.setStatus(nextInt());

		_persistence.update(newUser, false);

		User existingUser = _persistence.findByPrimaryKey(newUser.getPrimaryKey());

		assertEquals(existingUser.getUuid(), newUser.getUuid());
		assertEquals(existingUser.getUserId(), newUser.getUserId());
		assertEquals(existingUser.getCompanyId(), newUser.getCompanyId());
		assertEquals(Time.getShortTimestamp(existingUser.getCreateDate()),
			Time.getShortTimestamp(newUser.getCreateDate()));
		assertEquals(Time.getShortTimestamp(existingUser.getModifiedDate()),
			Time.getShortTimestamp(newUser.getModifiedDate()));
		assertEquals(existingUser.getDefaultUser(), newUser.getDefaultUser());
		assertEquals(existingUser.getContactId(), newUser.getContactId());
		assertEquals(existingUser.getPassword(), newUser.getPassword());
		assertEquals(existingUser.getPasswordEncrypted(),
			newUser.getPasswordEncrypted());
		assertEquals(existingUser.getPasswordReset(), newUser.getPasswordReset());
		assertEquals(Time.getShortTimestamp(
				existingUser.getPasswordModifiedDate()),
			Time.getShortTimestamp(newUser.getPasswordModifiedDate()));
		assertEquals(existingUser.getDigest(), newUser.getDigest());
		assertEquals(existingUser.getReminderQueryQuestion(),
			newUser.getReminderQueryQuestion());
		assertEquals(existingUser.getReminderQueryAnswer(),
			newUser.getReminderQueryAnswer());
		assertEquals(existingUser.getGraceLoginCount(),
			newUser.getGraceLoginCount());
		assertEquals(existingUser.getScreenName(), newUser.getScreenName());
		assertEquals(existingUser.getEmailAddress(), newUser.getEmailAddress());
		assertEquals(existingUser.getFacebookId(), newUser.getFacebookId());
		assertEquals(existingUser.getOpenId(), newUser.getOpenId());
		assertEquals(existingUser.getPortraitId(), newUser.getPortraitId());
		assertEquals(existingUser.getLanguageId(), newUser.getLanguageId());
		assertEquals(existingUser.getTimeZoneId(), newUser.getTimeZoneId());
		assertEquals(existingUser.getGreeting(), newUser.getGreeting());
		assertEquals(existingUser.getComments(), newUser.getComments());
		assertEquals(existingUser.getFirstName(), newUser.getFirstName());
		assertEquals(existingUser.getMiddleName(), newUser.getMiddleName());
		assertEquals(existingUser.getLastName(), newUser.getLastName());
		assertEquals(existingUser.getJobTitle(), newUser.getJobTitle());
		assertEquals(Time.getShortTimestamp(existingUser.getLoginDate()),
			Time.getShortTimestamp(newUser.getLoginDate()));
		assertEquals(existingUser.getLoginIP(), newUser.getLoginIP());
		assertEquals(Time.getShortTimestamp(existingUser.getLastLoginDate()),
			Time.getShortTimestamp(newUser.getLastLoginDate()));
		assertEquals(existingUser.getLastLoginIP(), newUser.getLastLoginIP());
		assertEquals(Time.getShortTimestamp(
				existingUser.getLastFailedLoginDate()),
			Time.getShortTimestamp(newUser.getLastFailedLoginDate()));
		assertEquals(existingUser.getFailedLoginAttempts(),
			newUser.getFailedLoginAttempts());
		assertEquals(existingUser.getLockout(), newUser.getLockout());
		assertEquals(Time.getShortTimestamp(existingUser.getLockoutDate()),
			Time.getShortTimestamp(newUser.getLockoutDate()));
		assertEquals(existingUser.getAgreedToTermsOfUse(),
			newUser.getAgreedToTermsOfUse());
		assertEquals(existingUser.getEmailAddressVerified(),
			newUser.getEmailAddressVerified());
		assertEquals(existingUser.getStatus(), newUser.getStatus());
	}

	public void testFindByPrimaryKeyExisting() throws Exception {
		User newUser = addUser();

		User existingUser = _persistence.findByPrimaryKey(newUser.getPrimaryKey());

		assertEquals(existingUser, newUser);
	}

	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = nextLong();

		try {
			_persistence.findByPrimaryKey(pk);

			fail("Missing entity did not throw NoSuchUserException");
		}
		catch (NoSuchUserException nsee) {
		}
	}

	public void testFetchByPrimaryKeyExisting() throws Exception {
		User newUser = addUser();

		User existingUser = _persistence.fetchByPrimaryKey(newUser.getPrimaryKey());

		assertEquals(existingUser, newUser);
	}

	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = nextLong();

		User missingUser = _persistence.fetchByPrimaryKey(pk);

		assertNull(missingUser);
	}

	public void testDynamicQueryByPrimaryKeyExisting()
		throws Exception {
		User newUser = addUser();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(User.class,
				User.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("userId",
				newUser.getUserId()));

		List<User> result = _persistence.findWithDynamicQuery(dynamicQuery);

		assertEquals(1, result.size());

		User existingUser = result.get(0);

		assertEquals(existingUser, newUser);
	}

	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(User.class,
				User.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("userId", nextLong()));

		List<User> result = _persistence.findWithDynamicQuery(dynamicQuery);

		assertEquals(0, result.size());
	}

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

		assertEquals(1, result.size());

		Object existingUserId = result.get(0);

		assertEquals(existingUserId, newUserId);
	}

	public void testDynamicQueryByProjectionMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(User.class,
				User.class.getClassLoader());

		dynamicQuery.setProjection(ProjectionFactoryUtil.property("userId"));

		dynamicQuery.add(RestrictionsFactoryUtil.in("userId",
				new Object[] { nextLong() }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		assertEquals(0, result.size());
	}

	public void testResetOriginalValues() throws Exception {
		if (!PropsValues.HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE) {
			return;
		}

		User newUser = addUser();

		_persistence.clearCache();

		UserModelImpl existingUserModelImpl = (UserModelImpl)_persistence.findByPrimaryKey(newUser.getPrimaryKey());

		assertEquals(existingUserModelImpl.getContactId(),
			existingUserModelImpl.getOriginalContactId());

		assertEquals(existingUserModelImpl.getPortraitId(),
			existingUserModelImpl.getOriginalPortraitId());

		assertEquals(existingUserModelImpl.getCompanyId(),
			existingUserModelImpl.getOriginalCompanyId());
		assertEquals(existingUserModelImpl.getUserId(),
			existingUserModelImpl.getOriginalUserId());

		assertEquals(existingUserModelImpl.getCompanyId(),
			existingUserModelImpl.getOriginalCompanyId());
		assertEquals(existingUserModelImpl.getDefaultUser(),
			existingUserModelImpl.getOriginalDefaultUser());

		assertEquals(existingUserModelImpl.getCompanyId(),
			existingUserModelImpl.getOriginalCompanyId());
		assertTrue(Validator.equals(existingUserModelImpl.getScreenName(),
				existingUserModelImpl.getOriginalScreenName()));

		assertEquals(existingUserModelImpl.getCompanyId(),
			existingUserModelImpl.getOriginalCompanyId());
		assertTrue(Validator.equals(existingUserModelImpl.getEmailAddress(),
				existingUserModelImpl.getOriginalEmailAddress()));

		assertEquals(existingUserModelImpl.getCompanyId(),
			existingUserModelImpl.getOriginalCompanyId());
		assertEquals(existingUserModelImpl.getFacebookId(),
			existingUserModelImpl.getOriginalFacebookId());

		assertEquals(existingUserModelImpl.getCompanyId(),
			existingUserModelImpl.getOriginalCompanyId());
		assertTrue(Validator.equals(existingUserModelImpl.getOpenId(),
				existingUserModelImpl.getOriginalOpenId()));
	}

	protected User addUser() throws Exception {
		long pk = nextLong();

		User user = _persistence.create(pk);

		user.setUuid(randomString());

		user.setCompanyId(nextLong());

		user.setCreateDate(nextDate());

		user.setModifiedDate(nextDate());

		user.setDefaultUser(randomBoolean());

		user.setContactId(nextLong());

		user.setPassword(randomString());

		user.setPasswordEncrypted(randomBoolean());

		user.setPasswordReset(randomBoolean());

		user.setPasswordModifiedDate(nextDate());

		user.setDigest(randomString());

		user.setReminderQueryQuestion(randomString());

		user.setReminderQueryAnswer(randomString());

		user.setGraceLoginCount(nextInt());

		user.setScreenName(randomString());

		user.setEmailAddress(randomString());

		user.setFacebookId(nextLong());

		user.setOpenId(randomString());

		user.setPortraitId(nextLong());

		user.setLanguageId(randomString());

		user.setTimeZoneId(randomString());

		user.setGreeting(randomString());

		user.setComments(randomString());

		user.setFirstName(randomString());

		user.setMiddleName(randomString());

		user.setLastName(randomString());

		user.setJobTitle(randomString());

		user.setLoginDate(nextDate());

		user.setLoginIP(randomString());

		user.setLastLoginDate(nextDate());

		user.setLastLoginIP(randomString());

		user.setLastFailedLoginDate(nextDate());

		user.setFailedLoginAttempts(nextInt());

		user.setLockout(randomBoolean());

		user.setLockoutDate(nextDate());

		user.setAgreedToTermsOfUse(randomBoolean());

		user.setEmailAddressVerified(randomBoolean());

		user.setStatus(nextInt());

		_persistence.update(user, false);

		return user;
	}

	private UserPersistence _persistence;
}