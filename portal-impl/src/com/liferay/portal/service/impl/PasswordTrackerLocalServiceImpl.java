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

package com.liferay.portal.service.impl;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.model.PasswordPolicy;
import com.liferay.portal.model.PasswordTracker;
import com.liferay.portal.model.User;
import com.liferay.portal.security.pwd.PasswordEncryptorUtil;
import com.liferay.portal.service.base.PasswordTrackerLocalServiceBaseImpl;

import java.util.Date;
import java.util.List;

/**
 * @author Brian Wing Shun Chan
 * @author Scott Lee
 */
public class PasswordTrackerLocalServiceImpl
	extends PasswordTrackerLocalServiceBaseImpl {

	@Override
	public void deletePasswordTrackers(long userId) throws SystemException {
		passwordTrackerPersistence.removeByUserId(userId);
	}

	@Override
	public boolean isSameAsCurrentPassword(long userId, String newClearTextPwd)
		throws PortalException, SystemException {

		User user = userPersistence.findByPrimaryKey(userId);

		String currentPwd = user.getPassword();

		if (user.isPasswordEncrypted()) {
			String newEncPwd = PasswordEncryptorUtil.encrypt(
				newClearTextPwd, user.getPassword());

			if (currentPwd.equals(newEncPwd)) {
				return true;
			}
			else {
				return false;
			}
		}
		else {
			if (currentPwd.equals(newClearTextPwd)) {
				return true;
			}
			else {
				return false;
			}
		}
	}

	@Override
	public boolean isValidPassword(long userId, String newClearTextPwd)
		throws PortalException, SystemException {

		PasswordPolicy passwordPolicy =
			passwordPolicyLocalService.getPasswordPolicyByUserId(userId);

		if ((passwordPolicy == null) || !passwordPolicy.getHistory()) {
			return true;
		}

		// Check password history

		int historyCount = 1;

		List<PasswordTracker> passwordTrackers =
			passwordTrackerPersistence.findByUserId(userId);

		for (PasswordTracker passwordTracker : passwordTrackers) {
			if (historyCount >= passwordPolicy.getHistoryCount()) {
				break;
			}

			String oldEncPwd = passwordTracker.getPassword();
			String newEncPwd = PasswordEncryptorUtil.encrypt(
				newClearTextPwd, oldEncPwd);

			if (oldEncPwd.equals(newEncPwd)) {
				return false;
			}

			historyCount++;
		}

		return true;
	}

	@Override
	public void trackPassword(long userId, String encPassword)
		throws PortalException, SystemException {

		PasswordPolicy passwordPolicy =
			passwordPolicyLocalService.getPasswordPolicyByUserId(userId);

		if ((passwordPolicy != null) && passwordPolicy.isHistory()) {
			long passwordTrackerId = counterLocalService.increment();

			PasswordTracker passwordTracker = passwordTrackerPersistence.create(
				passwordTrackerId);

			passwordTracker.setUserId(userId);
			passwordTracker.setCreateDate(new Date());
			passwordTracker.setPassword(encPassword);

			passwordTrackerPersistence.update(passwordTracker);
		}
	}

}