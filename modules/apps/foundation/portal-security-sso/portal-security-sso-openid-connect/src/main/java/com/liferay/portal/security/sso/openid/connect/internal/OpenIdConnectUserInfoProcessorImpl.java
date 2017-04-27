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

package com.liferay.portal.security.sso.openid.connect.internal;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.util.PwdGenerator;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.security.sso.openid.connect.OpenIdConnectServiceException;
import com.liferay.portal.security.sso.openid.connect.OpenIdConnectUserInfoProcessor;

import com.nimbusds.openid.connect.sdk.claims.UserInfo;

import java.util.Calendar;
import java.util.Locale;

import javax.mail.internet.InternetAddress;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Michael C. Han
 */
@Component(immediate = true, service = OpenIdConnectUserInfoProcessor.class)
public class OpenIdConnectUserInfoProcessorImpl
	implements OpenIdConnectUserInfoProcessor {

	@Override
	public long processUserInfo(UserInfo userInfo, long companyId)
		throws PortalException {

		Company company = _companyLocalService.getCompany(companyId);

		String firstName = userInfo.getGivenName();
		String lastName = userInfo.getFamilyName();

		InternetAddress internetAddress = userInfo.getEmail();

		String emailAddress = internetAddress.getAddress();

		User user = _userLocalService.fetchUserByEmailAddress(
			companyId, emailAddress);

		if (user != null) {
			return user.getUserId();
		}

		if (Validator.isNull(firstName) || Validator.isNull(lastName) ||
			Validator.isNull(emailAddress)) {

			throw new OpenIdConnectServiceException.UserInfoMissingException(
				"User information has missing fields");
		}

		long creatorUserId = 0;
		boolean autoPassword = false;

		String password1 = PwdGenerator.getPassword();

		String password2 = password1;

		boolean autoScreenName = true;
		String screenName = StringPool.BLANK;
		long facebookId = 0;
		Locale locale = company.getLocale();
		String middleName = userInfo.getMiddleName();
		long prefixId = 0;
		long suffixId = 0;
		boolean male = true;
		int birthdayMonth = Calendar.JANUARY;
		int birthdayDay = 1;
		int birthdayYear = 1970;
		String jobTitle = StringPool.BLANK;
		long[] groupIds = null;
		long[] organizationIds = null;
		long[] roleIds = null;
		long[] userGroupIds = null;
		boolean sendEmail = false;

		ServiceContext serviceContext = new ServiceContext();

		user = _userLocalService.addUser(
			creatorUserId, companyId, autoPassword, password1, password2,
			autoScreenName, screenName, emailAddress, facebookId, null, locale,
			firstName, middleName, lastName, prefixId, suffixId, male,
			birthdayMonth, birthdayDay, birthdayYear, jobTitle, groupIds,
			organizationIds, roleIds, userGroupIds, sendEmail, serviceContext);

		user = _userLocalService.updatePasswordReset(user.getUserId(), false);

		return user.getUserId();
	}

	@Reference
	private CompanyLocalService _companyLocalService;

	@Reference
	private UserLocalService _userLocalService;

}