/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.multi.factor.authentication.email.otp.service.impl;

import com.liferay.multi.factor.authentication.email.otp.exception.DuplicateMFAEmailOTPEntryException;
import com.liferay.multi.factor.authentication.email.otp.exception.NoSuchEntryException;
import com.liferay.multi.factor.authentication.email.otp.model.MFAEmailOTPEntry;
import com.liferay.multi.factor.authentication.email.otp.service.base.MFAEmailOTPEntryLocalServiceBaseImpl;
import com.liferay.portal.aop.AopService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.UserLocalService;

import java.util.Date;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

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

		User user = _userLocalService.getUserById(userId);

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

	@Reference
	private UserLocalService _userLocalService;

}