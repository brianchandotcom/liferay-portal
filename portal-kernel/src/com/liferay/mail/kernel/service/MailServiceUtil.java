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

import com.liferay.mail.kernel.model.Account;
import com.liferay.mail.kernel.model.MailMessage;
import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;

import javax.mail.Session;

/**
 * @author Brian Wing Shun Chan
 */
public class MailServiceUtil {

	public static void clearSession() {
		getService().clearSession();
	}

	public static MailService getService() {
		if (_mailService == null) {
			_mailService = (MailService)PortalBeanLocatorUtil.locate(
				MailService.class.getName());
		}

		return _mailService;
	}

	public static Session getSession() {
		return getService().getSession();
	}

	public static Session getSession(Account account) {
		return getService().getSession(account);
	}

	public static void sendEmail(MailMessage mailMessage) {
		getService().sendEmail(mailMessage);
	}

	public void setService(MailService mailService) {
		_mailService = mailService;
	}

	private static MailService _mailService;

}