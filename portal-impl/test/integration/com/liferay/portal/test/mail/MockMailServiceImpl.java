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

package com.liferay.portal.test.mail;

import com.liferay.mail.model.Filter;
import com.liferay.mail.service.MailService;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.mail.MailMessage;

import java.util.List;

import javax.mail.Session;

/**
 * @author Sergio González
 */
public class MockMailServiceImpl implements MailService {

	@Override
	public void addForward(
		long companyId, long userId, List<Filter> filters,
		List<String> emailAddresses, boolean leaveCopy) {
	}

	@Override
	public void addUser(
		long companyId, long userId, String password, String firstName,
		String middleName, String lastName, String emailAddress) {
	}

	@Override
	public void addVacationMessage(
		long companyId, long userId, String emailAddress,
		String vacationMessage) {
	}

	@Override
	public void clearSession() {
	}

	@Override
	public void deleteEmailAddress(long companyId, long userId) {
	}

	@Override
	public void deleteUser(long companyId, long userId) {
	}

	@Override
	public Session getSession() throws SystemException {
		return null;
	}

	@Override
	public void sendEmail(MailMessage mailMessage) {
	}

	@Override
	public void updateBlocked(
		long companyId, long userId, List<String> blocked) {
	}

	@Override
	public void updateEmailAddress(
		long companyId, long userId, String emailAddress) {
	}

	@Override
	public void updatePassword(long companyId, long userId, String password) {
	}

}