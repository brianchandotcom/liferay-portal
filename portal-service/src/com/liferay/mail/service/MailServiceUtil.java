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

package com.liferay.mail.service;

import com.liferay.mail.model.Filter;
import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.mail.MailMessage;
import com.liferay.portal.kernel.util.ReferenceRegistry;

import java.util.List;

import javax.mail.Session;

/**
 * @author Brian Wing Shun Chan
 */
public class MailServiceUtil {

	public static void addForward(
		long companyId, long userId, List<Filter> filters,
		List<String> emailAddresses, boolean leaveCopy) {

		getService().addForward(
			companyId, userId, filters, emailAddresses, leaveCopy);
	}

	public static void addUser(
		long companyId, long userId, String password, String firstName,
		String middleName, String lastName, String emailAddress) {

		getService().addUser(
			companyId, userId, password, firstName, middleName, lastName,
			emailAddress);
	}

	public static void addVacationMessage(
		long companyId, long userId, String emailAddress,
		String vacationMessage) {

		getService().addVacationMessage(
			companyId, userId, emailAddress, vacationMessage);
	}

	public static void clearSession() {
		getService().clearSession();
	}

	public static void deleteEmailAddress(long companyId, long userId) {
		getService().deleteEmailAddress(companyId, userId);
	}

	public static void deleteUser(long companyId, long userId) {
		getService().deleteUser(companyId, userId);
	}

	public static MailService getService() {
		if (_service == null) {
			_service = (MailService)PortalBeanLocatorUtil.locate(
				MailService.class.getName());

			ReferenceRegistry.registerReference(
				MailServiceUtil.class, "_service");
		}

		return _service;
	}

	public static Session getSession() throws SystemException {
		return getService().getSession();
	}

	public static void sendEmail(MailMessage mailMessage) {
		getService().sendEmail(mailMessage);
	}

	public static void updateBlocked(
		long companyId, long userId, List<String> blocked) {

		getService().updateBlocked(companyId, userId, blocked);
	}

	public static void updateEmailAddress(
		long companyId, long userId, String emailAddress) {

		getService().updateEmailAddress(companyId, userId, emailAddress);
	}

	public static void updatePassword(
		long companyId, long userId, String password) {

		getService().updatePassword(companyId, userId, password);
	}

	public void setService(MailService service) {
		_service = service;

		ReferenceRegistry.registerReference(MailServiceUtil.class, "_service");
	}

	private static MailService _service;

}