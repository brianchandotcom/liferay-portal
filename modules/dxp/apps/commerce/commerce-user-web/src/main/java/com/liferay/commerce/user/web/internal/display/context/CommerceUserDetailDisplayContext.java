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