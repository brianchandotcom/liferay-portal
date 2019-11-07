/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.multi.factor.authentication.checker.email.otp.service.impl;

import com.liferay.multi.factor.authentication.checker.email.otp.model.EmailOTPEntry;
import com.liferay.multi.factor.authentication.checker.email.otp.service.base.EmailOTPEntryLocalServiceBaseImpl;
import com.liferay.portal.aop.AopService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.User;

import java.util.Date;

import org.osgi.service.component.annotations.Component;

/**
 * The implementation of the email otp entry local service.
 *
 * <p>
 * All custom service methods should be put in this class. Whenever methods are added, rerun ServiceBuilder to copy their definitions into the <code>com.liferay.multi.factor.authentication.checker.email.otp.service.EmailOTPEntryLocalService</code> interface.
 *
 * <p>
 * This is a local service. Methods of this service will not have security checks based on the propagated JAAS credentials because this service can only be accessed from within the same VM.
 * </p>
 *
 * @author arthurchan35
 * @see EmailOTPEntryLocalServiceBaseImpl
 */
@Component(
	property = "model.class.name=com.liferay.multi.factor.authentication.checker.email.otp.model.EmailOTPEntry",
	service = AopService.class
)
public class EmailOTPEntryLocalServiceImpl
	extends EmailOTPEntryLocalServiceBaseImpl {

	public EmailOTPEntry addEmailOTPEntry(long userId) throws PortalException {
		EmailOTPEntry emailOTPEntry =
			emailOTPEntryLocalService.fetchEntryByUserId(userId);

		if (emailOTPEntry != null) {
			throw new IllegalArgumentException(
				"There is already one Email OTP Entry for user " + userId);
		}

		User user = userLocalService.getUserById(userId);

		long entryId = counterLocalService.increment();

		emailOTPEntry = emailOTPEntryPersistence.create(entryId);

		emailOTPEntry.setCompanyId(user.getCompanyId());
		emailOTPEntry.setUserId(userId);
		emailOTPEntry.setUserName(user.getFullName());
		emailOTPEntry.setCreateDate(new Date());

		emailOTPEntryPersistence.update(emailOTPEntry);

		return emailOTPEntry;
	}

	public EmailOTPEntry fetchEntryByUserId(long userId) {
		return emailOTPEntryPersistence.fetchByUserId(userId);
	}

	public void resetFailedAttempts(long userId) {
		EmailOTPEntry emailOTPEntry =
			emailOTPEntryLocalService.fetchEntryByUserId(userId);

		if (emailOTPEntry == null) {
			if (_log.isInfoEnabled()) {
				_log.info(
					"Reset failed attempts on non existent user: " + userId);
			}

			return;
		}

		emailOTPEntry.setFailedAttempts(0);
		emailOTPEntry.setLastFailDate(null);
		emailOTPEntry.setLastFailIP(null);

		emailOTPEntryPersistence.update(emailOTPEntry);
	}

	public void updateAttempts(long userId, String userIP, boolean success) {
		EmailOTPEntry emailOTPEntry =
			emailOTPEntryLocalService.fetchEntryByUserId(userId);

		if (emailOTPEntry == null) {
			if (_log.isInfoEnabled()) {
				_log.info("Update attempts on non existent user: " + userId);
			}

			return;
		}

		if (success) {
			emailOTPEntry.setFailedAttempts(0);
			emailOTPEntry.setLastSuccessDate(new Date());
			emailOTPEntry.setLastSuccessIP(userIP);
			emailOTPEntry.setLastFailDate(null);
			emailOTPEntry.setLastFailIP(null);
		}
		else {
			emailOTPEntry.setFailedAttempts(
				emailOTPEntry.getFailedAttempts() + 1);
			emailOTPEntry.setLastFailDate(new Date());
			emailOTPEntry.setLastFailIP(userIP);
		}

		emailOTPEntryPersistence.update(emailOTPEntry);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		EmailOTPEntryLocalServiceImpl.class);

}