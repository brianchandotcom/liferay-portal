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

package com.liferay.commerce.user.web.internal.display.context;

import com.liferay.commerce.user.service.CommerceUserService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Contact;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.util.CalendarFactoryUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.users.admin.configuration.UserFileUploadsConfiguration;

import java.util.Calendar;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Alessio Antonio Rendina
 */
public class CommerceUserDetailDisplayContext
	extends BaseCommerceUserDisplayContext {

	public CommerceUserDetailDisplayContext(
		CommerceUserService commerceUserService,
		HttpServletRequest httpServletRequest, Portal portal,
		UserFileUploadsConfiguration userFileUploadsConfiguration) {

		super(commerceUserService, httpServletRequest, portal);

		_userFileUploadsConfiguration = userFileUploadsConfiguration;
	}

	public Calendar getBirthday() throws PortalException {
		Calendar birthday = CalendarFactoryUtil.getCalendar();

		birthday.set(Calendar.MONTH, Calendar.JANUARY);
		birthday.set(Calendar.DATE, 1);
		birthday.set(Calendar.YEAR, 1970);

		User user = getSelectedUser();

		Contact selContact = user.getContact();

		if (selContact != null) {
			birthday.setTime(selContact.getBirthday());
		}

		return birthday;
	}

	public UserFileUploadsConfiguration getUserFileUploadsConfiguration() {
		return _userFileUploadsConfiguration;
	}

	private final UserFileUploadsConfiguration _userFileUploadsConfiguration;

}