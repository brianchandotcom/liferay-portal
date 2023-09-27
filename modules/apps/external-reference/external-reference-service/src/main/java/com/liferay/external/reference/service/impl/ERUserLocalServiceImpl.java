/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.external.reference.service.impl;

import com.liferay.external.reference.service.base.ERUserLocalServiceBaseImpl;
import com.liferay.portal.aop.AopService;
import com.liferay.portal.kernel.bean.BeanProperties;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Contact;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.model.UserConstants;
import com.liferay.portal.kernel.model.UserGroupRole;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.UserLocalService;

import java.util.List;
import java.util.Locale;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

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
			long prefixListTypeId, long suffixListTypeId, boolean male,
			int birthdayMonth, int birthdayDay, int birthdayYear,
			String jobTitle, long[] groupIds, long[] organizationIds,
			long[] roleIds, List<UserGroupRole> userGroupRoles,
			long[] userGroupIds, boolean sendEmail,
			ServiceContext serviceContext)
		throws PortalException {

		User user = _userLocalService.fetchUserByExternalReferenceCode(
			externalReferenceCode, companyId);

		if (user == null) {
			user = _userLocalService.addUser(
				creatorUserId, companyId, autoPassword, password1, password2,
				autoScreenName, screenName, emailAddress, locale, firstName,
				middleName, lastName, prefixListTypeId, suffixListTypeId, male,
				birthdayMonth, birthdayDay, birthdayYear, jobTitle,
				UserConstants.TYPE_REGULAR, groupIds, organizationIds, roleIds,
				userGroupIds, sendEmail, serviceContext);

			user.setExternalReferenceCode(externalReferenceCode);

			user = _userLocalService.updateUser(user);
		}
		else {
			Contact contact = user.getContact();

			long imageId = _beanProperties.getLong(user, "portraitId");

			boolean hasPortrait = false;

			if (imageId > 0) {
				hasPortrait = true;
			}

			user = _userLocalService.updateUser(
				user.getUserId(), null, password1, password2, false,
				user.getReminderQueryQuestion(), user.getReminderQueryAnswer(),
				screenName, emailAddress, hasPortrait, null,
				user.getLanguageId(), user.getTimeZoneId(), user.getGreeting(),
				user.getComments(), firstName, middleName, lastName,
				prefixListTypeId, suffixListTypeId, male, birthdayMonth,
				birthdayDay, birthdayYear, contact.getSmsSn(),
				contact.getFacebookSn(), contact.getJabberSn(),
				contact.getSkypeSn(), contact.getTwitterSn(), jobTitle,
				groupIds, organizationIds, roleIds, userGroupRoles,
				userGroupIds, serviceContext);
		}

		return user;
	}

	@Reference
	private BeanProperties _beanProperties;

	@Reference
	private UserLocalService _userLocalService;

}