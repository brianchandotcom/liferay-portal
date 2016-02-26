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

package com.liferay.mail.kernel.service;

import com.liferay.mail.kernel.model.Filter;
import com.liferay.mail.kernel.model.MailMessage;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.transaction.Transactional;

import java.util.List;

import javax.mail.Session;

/**
 * @author Brian Wing Shun Chan
 */
@Transactional(rollbackFor = {PortalException.class, SystemException.class})
public interface MailService {

	public void addForward(
		long companyId, long userId, List<Filter> filters,
		List<String> emailAddresses, boolean leaveCopy);

	public void addUser(
		long companyId, long userId, String password, String firstName,
		String middleName, String lastName, String emailAddress);

	public void addVacationMessage(
		long companyId, long userId, String emailAddress,
		String vacationMessage);

	public void clearSession();

	public void deleteEmailAddress(long companyId, long userId);

	public void deleteUser(long companyId, long userId);

	public Session getSession();

	public void sendEmail(MailMessage mailMessage);

	public void updateBlocked(
		long companyId, long userId, List<String> blocked);

	public void updateEmailAddress(
		long companyId, long userId, String emailAddress);

	public void updatePassword(long companyId, long userId, String password);

}