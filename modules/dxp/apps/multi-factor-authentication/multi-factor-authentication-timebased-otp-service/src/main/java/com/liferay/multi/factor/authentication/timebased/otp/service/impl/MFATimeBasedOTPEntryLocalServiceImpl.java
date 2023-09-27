/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.multi.factor.authentication.timebased.otp.service.impl;

import com.liferay.multi.factor.authentication.timebased.otp.exception.DuplicateMFATimeBasedOTPEntryException;
import com.liferay.multi.factor.authentication.timebased.otp.exception.NoSuchEntryException;
import com.liferay.multi.factor.authentication.timebased.otp.model.MFATimeBasedOTPEntry;
import com.liferay.multi.factor.authentication.timebased.otp.service.base.MFATimeBasedOTPEntryLocalServiceBaseImpl;
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
	property = "model.class.name=com.liferay.multi.factor.authentication.timebased.otp.model.MFATimeBasedOTPEntry",
	service = AopService.class
)
public class MFATimeBasedOTPEntryLocalServiceImpl
	extends MFATimeBasedOTPEntryLocalServiceBaseImpl {

	@Override
	public MFATimeBasedOTPEntry addTimeBasedOTPEntry(
			long userId, String sharedSecret)
		throws PortalException {

		MFATimeBasedOTPEntry mfaTimeBasedOTPEntry =
			mfaTimeBasedOTPEntryPersistence.fetchByUserId(userId);

		if (mfaTimeBasedOTPEntry != null) {
			throw new DuplicateMFATimeBasedOTPEntryException(
				"User ID  " + userId);
		}

		mfaTimeBasedOTPEntry = mfaTimeBasedOTPEntryPersistence.create(
			counterLocalService.increment());

		User user = _userLocalService.getUserById(userId);

		mfaTimeBasedOTPEntry.setCompanyId(user.getCompanyId());

		mfaTimeBasedOTPEntry.setUserId(userId);
		mfaTimeBasedOTPEntry.setUserName(user.getFullName());
		mfaTimeBasedOTPEntry.setCreateDate(new Date());
		mfaTimeBasedOTPEntry.setSharedSecret(sharedSecret);

		return mfaTimeBasedOTPEntryPersistence.update(mfaTimeBasedOTPEntry);
	}

	@Override
	public MFATimeBasedOTPEntry fetchMFATimeBasedOTPEntryByUserId(long userId) {
		return mfaTimeBasedOTPEntryPersistence.fetchByUserId(userId);
	}

	@Override
	public MFATimeBasedOTPEntry resetFailedAttempts(long userId)
		throws PortalException {

		MFATimeBasedOTPEntry mfaTimeBasedOTPEntry =
			mfaTimeBasedOTPEntryPersistence.fetchByUserId(userId);

		if (mfaTimeBasedOTPEntry == null) {
			throw new NoSuchEntryException("User ID " + userId);
		}

		mfaTimeBasedOTPEntry.setFailedAttempts(0);
		mfaTimeBasedOTPEntry.setLastFailDate(null);
		mfaTimeBasedOTPEntry.setLastFailIP(null);

		return mfaTimeBasedOTPEntryPersistence.update(mfaTimeBasedOTPEntry);
	}

	@Override
	public MFATimeBasedOTPEntry updateAttempts(
			long userId, String ipAddress, boolean success)
		throws PortalException {

		MFATimeBasedOTPEntry mfaTimeBasedOTPEntry =
			mfaTimeBasedOTPEntryPersistence.fetchByUserId(userId);

		if (mfaTimeBasedOTPEntry == null) {
			throw new NoSuchEntryException("User ID " + userId);
		}

		if (success) {
			mfaTimeBasedOTPEntry.setFailedAttempts(0);
			mfaTimeBasedOTPEntry.setLastFailDate(null);
			mfaTimeBasedOTPEntry.setLastFailIP(null);
			mfaTimeBasedOTPEntry.setLastSuccessDate(new Date());
			mfaTimeBasedOTPEntry.setLastSuccessIP(ipAddress);
		}
		else {
			mfaTimeBasedOTPEntry.setFailedAttempts(
				mfaTimeBasedOTPEntry.getFailedAttempts() + 1);
			mfaTimeBasedOTPEntry.setLastFailDate(new Date());
			mfaTimeBasedOTPEntry.setLastFailIP(ipAddress);
		}

		return mfaTimeBasedOTPEntryPersistence.update(mfaTimeBasedOTPEntry);
	}

	@Reference
	private UserLocalService _userLocalService;

}