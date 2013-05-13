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

package com.liferay.mail.util;

import com.liferay.mail.model.Filter;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

import java.util.List;

/**
 * @author Brian Wing Shun Chan
 */
public class DummyHook implements Hook {

	@Override
	public void addForward(
		long companyId, long userId, List<Filter> filters,
		List<String> emailAddresses, boolean leaveCopy) {

		if (_log.isDebugEnabled()) {
			_log.debug("addForward");
		}
	}

	@Override
	public void addUser(
		long companyId, long userId, String password, String firstName,
		String middleName, String lastName, String emailAddress) {

		if (_log.isDebugEnabled()) {
			_log.debug("addUser");
		}
	}

	@Override
	public void addVacationMessage(
		long companyId, long userId, String emailAddress,
		String vacationMessage) {

		if (_log.isDebugEnabled()) {
			_log.debug("addVacationMessage");
		}
	}

	@Override
	public void deleteEmailAddress(long companyId, long userId) {
		if (_log.isDebugEnabled()) {
			_log.debug("deleteEmailAddress");
		}
	}

	@Override
	public void deleteUser(long companyId, long userId) {
		if (_log.isDebugEnabled()) {
			_log.debug("deleteUser");
		}
	}

	@Override
	public void updateBlocked(
		long companyId, long userId, List<String> blocked) {

		if (_log.isDebugEnabled()) {
			_log.debug("updateBlocked");
		}
	}

	@Override
	public void updateEmailAddress(
		long companyId, long userId, String emailAddress) {

		if (_log.isDebugEnabled()) {
			_log.debug("updateEmailAddress");
		}
	}

	@Override
	public void updatePassword(long companyId, long userId, String password) {
		if (_log.isDebugEnabled()) {
			_log.debug("updatePassword");
		}
	}

	private static Log _log = LogFactoryUtil.getLog(DummyHook.class);

}