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

package com.liferay.multi.factor.authentication.email.otp.service.impl;

import com.liferay.multi.factor.authentication.email.otp.exception.DuplicateMFAEmailOTPEntryException;
import com.liferay.multi.factor.authentication.email.otp.exception.NoSuchEntryException;
import com.liferay.multi.factor.authentication.email.otp.model.MFAEmailOTPEntry;
import com.liferay.multi.factor.authentication.email.otp.service.base.MFAEmailOTPEntryLocalServiceBaseImpl;
import com.liferay.portal.aop.AopService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.User;

import java.util.Date;

import org.osgi.service.component.annotations.Component;

/**
 * @author Arthur Chan
 */
@Component(
	property = "model.class.name=com.liferay.multi.factor.authentication.email.otp.model.MFAEmailOTPEntry",
	service = AopService.class
)
public class MFAEmailOTPEntryLocalServiceImpl
	extends MFAEmailOTPEntryLocalServiceBaseImpl {

	@Override
	public MFAEmailOTPEntry addMFAEmailOTPEntry(long userId)
		throws PortalException {

		MFAEmailOTPEntry mfaEmailOTPEntry =
			mfaEmailOTPEntryPersistence.fetchByUserId(userId);

		if (mfaEmailOTPEntry != null) {
			throw new DuplicateMFAEmailOTPEntryException("User ID " + userId);
		}

		mfaEmailOTPEntry = mfaEmailOTPEntryPersistence.create(
			counterLocalService.increment());

		User user = userLocalService.getUserById(userId);

		mfaEmailOTPEntry.setCompanyId(user.getCompanyId());
		mfaEmailOTPEntry.setUserId(user.getUserId());
		mfaEmailOTPEntry.setUserName(user.getFullName());

		mfaEmailOTPEntry.setCreateDate(new Date());
		mfaEmailOTPEntry.setModifiedDate(new Date());

		return mfaEmailOTPEntryPersistence.update(mfaEmailOTPEntry);
	}

	@Override
	public MFAEmailOTPEntry fetchMFAEmailOTPEntryByUserId(long userId) {
		return mfaEmailOTPEntryPersistence.fetchByUserId(userId);
	}

	@Override
	public MFAEmailOTPEntry resetFailedAttempts(long userId)
		throws PortalException {

		MFAEmailOTPEntry mfaEmailOTPEntry =
			mfaEmailOTPEntryPersistence.fetchByUserId(userId);

		if (mfaEmailOTPEntry == null) {
			throw new NoSuchEntryException("User ID " + userId);
		}

		mfaEmailOTPEntry.setFailedAttempts(0);
		mfaEmailOTPEntry.setLastFailDate(null);
		mfaEmailOTPEntry.setLastFailIP(null);

		return mfaEmailOTPEntryPersistence.update(mfaEmailOTPEntry);
	}

	@Override
	public MFAEmailOTPEntry updateAttempts(
			long userId, String ip, boolean success)
		throws PortalException {

		MFAEmailOTPEntry mfaEmailOTPEntry =
			mfaEmailOTPEntryPersistence.fetchByUserId(userId);

		if (mfaEmailOTPEntry == null) {
			throw new NoSuchEntryException("User ID " + userId);
		}

		if (success) {
			mfaEmailOTPEntry.setFailedAttempts(0);
			mfaEmailOTPEntry.setLastFailDate(null);
			mfaEmailOTPEntry.setLastFailIP(null);
			mfaEmailOTPEntry.setLastSuccessDate(new Date());
			mfaEmailOTPEntry.setLastSuccessIP(ip);
		}
		else {
			mfaEmailOTPEntry.setFailedAttempts(
				mfaEmailOTPEntry.getFailedAttempts() + 1);
			mfaEmailOTPEntry.setLastFailDate(new Date());
			mfaEmailOTPEntry.setLastFailIP(ip);
		}

		return mfaEmailOTPEntryPersistence.update(mfaEmailOTPEntry);
	}

}