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

package com.liferay.external.reference.service.impl;

import com.liferay.external.reference.service.base.ERUserLocalServiceBaseImpl;
import com.liferay.portal.aop.AopService;
import com.liferay.portal.kernel.bean.BeanPropertiesUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Contact;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.model.UserGroupRole;
import com.liferay.portal.kernel.service.ServiceContext;

import java.util.List;
import java.util.Locale;

import org.osgi.service.component.annotations.Component;

/**
 * @author Dylan Rebelak
 */
@Component(
	property = "model.class.name=com.liferay.portal.kernel.model.User",
	service = AopService.class
)
public class ERUserLocalServiceImpl extends ERUserLocalServiceBaseImpl {

	@Override
	public User addOrUpdateUser(
			String externalReferenceCode, long creatorUserId, long companyId,
			boolean autoPassword, String password1, String password2,
			boolean autoScreenName, String screenName, String emailAddress,
			Locale locale, String firstName, String middleName, String lastName,
			long prefixId, long suffixId, boolean male, int birthdayMonth,
			int birthdayDay, int birthdayYear, String jobTitle, long[] groupIds,
			long[] organizationIds, long[] roleIds,
			List<UserGroupRole> userGroupRoles, long[] userGroupIds,
			boolean sendEmail, ServiceContext serviceContext)
		throws PortalException {

		User user = userLocalService.fetchUserByReferenceCode(
			companyId, externalReferenceCode);

		if (user == null) {
			user = userLocalService.addUser(
				creatorUserId, companyId, autoPassword, password1, password2,
				autoScreenName, screenName, emailAddress, locale, firstName,
				middleName, lastName, prefixId, suffixId, male, birthdayMonth,
				birthdayDay, birthdayYear, jobTitle, groupIds, organizationIds,
				roleIds, userGroupIds, sendEmail, serviceContext);

			user.setExternalReferenceCode(externalReferenceCode);

			user = userLocalService.updateUser(user);
		}
		else {
			Contact contact = user.getContact();

			long imageId = BeanPropertiesUtil.getLong(user, "portraitId");

			boolean hasPortrait = false;

			if (imageId > 0) {
				hasPortrait = true;
			}

			user = userLocalService.updateUser(
				user.getUserId(), null, password1, password2, false,
				user.getReminderQueryQuestion(), user.getReminderQueryAnswer(),
				screenName, emailAddress, hasPortrait, null,
				user.getLanguageId(), user.getTimeZoneId(), user.getGreeting(),
				user.getComments(), firstName, middleName, lastName, prefixId,
				suffixId, male, birthdayMonth, birthdayDay, birthdayYear,
				contact.getSmsSn(), contact.getFacebookSn(),
				contact.getJabberSn(), contact.getSkypeSn(),
				contact.getTwitterSn(), jobTitle, groupIds, organizationIds,
				roleIds, userGroupRoles, userGroupIds, serviceContext);
		}

		return user;
	}

}