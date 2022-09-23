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

package com.liferay.portal.security.sso.openid.connect.internal.user.info.handler.spi;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.exception.UserEmailAddressException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.service.RoleLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.security.sso.openid.connect.OpenIdConnectServiceException;
import com.liferay.portal.security.sso.openid.connect.internal.exception.StrangersNotAllowedException;

import com.nimbusds.jose.util.JSONObjectUtils;

import java.util.Calendar;
import java.util.Locale;

import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Arthur Chan
 */
@Component(immediate = true, service = UserModelOIDCUserInfoMapper.class)
public class UserModelOIDCUserInfoMapper {

	public long generateUser(
			long companyId, long[] propertyRoleIds,
			ServiceContext serviceContext, String userInfoJSON,
			String userInfoMapperJSON)
		throws Exception {

		JSONObject userInfoJSONObject = JSONObjectUtils.parse(userInfoJSON);

		JSONObject userInfoMapperJSONObject = JSONObjectUtils.parse(
			userInfoMapperJSON);

		JSONObject userMapperJSONObject = JSONObjectUtils.getJSONObject(
			userInfoMapperJSONObject, "user");

		String emailAddress = OIDCUserInfoClaimUtil.getStringClaim(
			"emailAddress", userMapperJSONObject, userInfoJSONObject);
		String firstName = OIDCUserInfoClaimUtil.getStringClaim(
			"firstName", userMapperJSONObject, userInfoJSONObject);
		String lastName = OIDCUserInfoClaimUtil.getStringClaim(
			"lastName", userMapperJSONObject, userInfoJSONObject);

		if (Validator.isNull(firstName) || Validator.isNull(lastName) ||
			Validator.isNull(emailAddress)) {

			throw new OpenIdConnectServiceException.UserMappingException(
				StringBundler.concat(
					"Unable to map OpenId Connect user to the portal, missing ",
					"or invalid profile information: {emailAddresss=",
					emailAddress, ", firstName=", firstName, ", lastName=",
					lastName, "}"));
		}

		_checkAddUser(companyId, emailAddress);

		JSONObject contactMapperJSONObject = JSONObjectUtils.getJSONObject(
			userInfoMapperJSONObject, "contact");

		long creatorUserId = 0;
		boolean autoPassword = true;
		String password1 = null;
		String password2 = null;
		String screenName = OIDCUserInfoClaimUtil.getStringClaim(
			"screenName", userMapperJSONObject, userInfoJSONObject);
		long prefixId = 0;
		long suffixId = 0;
		int[] birthday = _getBirthday(
			contactMapperJSONObject, userInfoJSONObject);
		long[] groupIds = null;
		long[] organizationIds = null;
		long[] userGroupIds = null;
		boolean sendEmail = false;

		long[] roleIds = propertyRoleIds;

		if (propertyRoleIds == null) {
			roleIds = _getRoleIds(
				companyId, userInfoJSONObject,
				JSONObjectUtils.getJSONObject(
					userInfoMapperJSONObject, "users_roles"));
		}

		User user = _userLocalService.addUser(
			creatorUserId, companyId, autoPassword, password1, password2,
			Validator.isNull(screenName), screenName, emailAddress,
			_getLocale(companyId, userInfoJSONObject, userMapperJSONObject),
			firstName,
			OIDCUserInfoClaimUtil.getStringClaim(
				"middleName", userMapperJSONObject, userInfoJSONObject),
			lastName, prefixId, suffixId,
			_isMale(contactMapperJSONObject, userInfoJSONObject), birthday[1],
			birthday[2], birthday[0],
			OIDCUserInfoClaimUtil.getStringClaim(
				"jobTitle", userMapperJSONObject, userInfoJSONObject),
			groupIds, organizationIds, roleIds, userGroupIds, sendEmail,
			serviceContext);

		user = _userLocalService.updatePasswordReset(user.getUserId(), false);

		return user.getUserId();
	}

	public long getUserIdByEmailAddress(
			long companyId, String userInfoJSON, String userInfoMapperJSON)
		throws Exception {

		JSONObject userInfoJSONObject = JSONObjectUtils.parse(userInfoJSON);

		JSONObject userInfoMapperJSONObject = JSONObjectUtils.parse(
			userInfoMapperJSON);

		JSONObject userMapperJSONObject = JSONObjectUtils.getJSONObject(
			userInfoMapperJSONObject, "user");

		User user = _userLocalService.fetchUserByEmailAddress(
			companyId,
			OIDCUserInfoClaimUtil.getStringClaim(
				"emailAddress", userMapperJSONObject, userInfoJSONObject));

		if (user != null) {
			return user.getUserId();
		}

		return 0;
	}

	private void _checkAddUser(long companyId, String emailAddress)
		throws Exception {

		Company company = _companyLocalService.getCompany(companyId);

		if (!company.isStrangers()) {
			throw new StrangersNotAllowedException(companyId);
		}

		if (!company.isStrangersWithMx() &&
			company.hasCompanyMx(emailAddress)) {

			throw new UserEmailAddressException.MustNotUseCompanyMx(
				emailAddress);
		}
	}

	private int[] _getBirthday(
		JSONObject contactMapperJSONObject, JSONObject userInfoJSONObject) {

		int[] birthday = new int[3];

		birthday[0] = 1970;
		birthday[1] = Calendar.JANUARY;
		birthday[2] = 1;

		String birthdate;

		try {
			birthdate = OIDCUserInfoClaimUtil.getStringClaim(
				"birthdate", contactMapperJSONObject, userInfoJSONObject);

			if (Validator.isNull(birthdate)) {
				return birthday;
			}

			String[] birthdateParts = birthdate.split("-");

			if (!birthdateParts[0].equals("0000")) {
				birthday[0] = Integer.parseInt(birthdateParts[0]);
			}

			if (birthdateParts.length == 3) {
				birthday[1] = Integer.parseInt(birthdateParts[1]) - 1;
				birthday[2] = Integer.parseInt(birthdateParts[2]);
			}
		}
		catch (Exception exception) {
			if (_log.isWarnEnabled()) {
				_log.warn("Unable to parse user birthday, use default value");
			}
		}

		return birthday;
	}

	private Locale _getLocale(
		long companyId, JSONObject userInfoJSONObject,
		JSONObject userMapperJSONObject) {

		String languageId = null;

		try {
			languageId = OIDCUserInfoClaimUtil.getStringClaim(
				"languageId", userMapperJSONObject, userInfoJSONObject);
		}
		catch (Exception exception) {
			if (_log.isWarnEnabled()) {
				_log.warn("Unable to get locale from userInfo", exception);
			}
		}

		if (Validator.isNotNull(languageId)) {
			return new Locale(languageId);
		}

		try {
			Company company = _companyLocalService.getCompany(companyId);

			return company.getLocale();
		}
		catch (Exception exception) {
			if (_log.isWarnEnabled()) {
				_log.warn(
					"Unable to get locale from company " + companyId,
					exception);
			}

			return new Locale("en-us");
		}
	}

	private long[] _getRoleIds(
		long companyId, JSONObject userInfoJSONObject,
		JSONObject usersRolesMapperJSONObject) {

		if ((usersRolesMapperJSONObject == null) ||
			(usersRolesMapperJSONObject.size() < 1)) {

			return null;
		}

		try {
			JSONArray rolesJSONArray = OIDCUserInfoClaimUtil.getJSONArrayClaim(
				"roles", usersRolesMapperJSONObject, userInfoJSONObject);

			long[] roleIds = new long[rolesJSONArray.size()];

			for (int i = 0; i < rolesJSONArray.size(); ++i) {
				Role role = _roleLocalService.fetchRole(
					companyId, (String)rolesJSONArray.get(i));

				roleIds[i] = role.getRoleId();
			}

			return roleIds;
		}
		catch (Exception exception) {
			if (_log.isWarnEnabled()) {
				_log.warn("Unable to assign roles " + exception);
			}

			return null;
		}
	}

	private boolean _isMale(
		JSONObject contactMapperJSONObject, JSONObject userInfoJSONObject) {

		try {
			String gender = OIDCUserInfoClaimUtil.getStringClaim(
				"gender", contactMapperJSONObject, userInfoJSONObject);

			if (Validator.isNull(gender) || gender.equals("male")) {
				return true;
			}
		}
		catch (Exception exception) {
			if (_log.isWarnEnabled()) {
				_log.warn("Unable to determine user gender " + exception);
			}
		}

		return false;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		UserModelOIDCUserInfoMapper.class);

	@Reference
	private CompanyLocalService _companyLocalService;

	@Reference
	private RoleLocalService _roleLocalService;

	@Reference
	private UserLocalService _userLocalService;

}