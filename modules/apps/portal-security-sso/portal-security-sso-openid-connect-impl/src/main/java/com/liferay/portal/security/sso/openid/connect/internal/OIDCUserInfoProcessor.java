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

import com.liferay.portal.kernel.exception.UserEmailAddressException;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.Contact;
import com.liferay.portal.kernel.model.Country;
import com.liferay.portal.kernel.model.ListType;
import com.liferay.portal.kernel.model.Region;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.model.role.RoleConstants;
import com.liferay.portal.kernel.service.AddressLocalService;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.service.CountryLocalService;
import com.liferay.portal.kernel.service.ListTypeLocalService;
import com.liferay.portal.kernel.service.PhoneLocalService;
import com.liferay.portal.kernel.service.RegionLocalService;
import com.liferay.portal.kernel.service.RoleLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.Props;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.security.sso.openid.connect.OpenIdConnectServiceException;
import com.liferay.portal.security.sso.openid.connect.internal.exception.StrangersNotAllowedException;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Arthur Chan
 */
@Component(immediate = true, service = OIDCUserInfoProcessor.class)
public class OIDCUserInfoProcessor {

	public long processUserInfo(
			long companyId, String issuer, ServiceContext serviceContext,
			String userInfoJSON, String userInfoMapperJSON)
		throws Exception {

		long userId = _getUserId(companyId, userInfoJSON, userInfoMapperJSON);

		if (userId > 0) {
			return userId;
		}

		User user = _addUser(
			companyId, issuer, serviceContext, userInfoJSON,
			userInfoMapperJSON);

		try {
			_addAddress(serviceContext, user, userInfoJSON, userInfoMapperJSON);
		}
		catch (Exception exception) {
			if (_log.isWarnEnabled()) {
				_log.warn(exception);
			}
		}

		try {
			_addPhone(serviceContext, user, userInfoJSON, userInfoMapperJSON);
		}
		catch (Exception exception) {
			if (_log.isWarnEnabled()) {
				_log.warn(exception);
			}
		}

		return user.getUserId();
	}

	private void _addAddress(
			ServiceContext serviceContext, User user, String userInfoJSON,
			String userInfoMapperJSON)
		throws Exception {

		JSONObject userInfoMapperJSONObject = _jsonFactory.createJSONObject(
			userInfoMapperJSON);

		JSONObject addressMapperJSONObject =
			userInfoMapperJSONObject.getJSONObject("address");

		if (addressMapperJSONObject == null) {
			return;
		}

		JSONObject userInfoJSONObject = _jsonFactory.createJSONObject(
			userInfoJSON);

		String streetClaimString = _getClaimString(
			"street", addressMapperJSONObject, userInfoJSONObject);

		if (Validator.isNull(streetClaimString)) {
			return;
		}

		String[] streetClaimStringParts = streetClaimString.split("\n");

		Region region = null;
		Country country = null;

		String countryClaimString = _getClaimString(
			"country", addressMapperJSONObject, userInfoJSONObject);

		if (Validator.isNotNull(countryClaimString)) {
			if ((countryClaimString.charAt(0) >= '0') &&
				(countryClaimString.charAt(0) <= '9')) {

				country = _countryLocalService.getCountryByNumber(
					user.getCompanyId(), countryClaimString);
			}
			else if (countryClaimString.length() == 2) {
				country = _countryLocalService.fetchCountryByA2(
					user.getCompanyId(),
					StringUtil.toUpperCase(countryClaimString));
			}
			else if (countryClaimString.length() == 3) {
				country = _countryLocalService.fetchCountryByA3(
					user.getCompanyId(),
					StringUtil.toUpperCase(countryClaimString));
			}
			else {
				country = _countryLocalService.fetchCountryByName(
					user.getCompanyId(),
					StringUtil.toLowerCase(countryClaimString));
			}

			String regionCode = _getClaimString(
				"region", addressMapperJSONObject, userInfoJSONObject);

			if ((country != null) && Validator.isNotNull(regionCode)) {
				region = _regionLocalService.fetchRegion(
					country.getCountryId(), StringUtil.toUpperCase(regionCode));
			}
		}

		ListType listType = _listTypeLocalService.getListType(
			_getClaimString(
				"addressType", addressMapperJSONObject, userInfoJSONObject),
			Contact.class.getName() + ".address");

		if (listType == null) {
			List<ListType> listTypes = _listTypeLocalService.getListTypes(
				Contact.class.getName() + ".address");

			listType = listTypes.get(0);
		}

		_addressLocalService.addAddress(
			null, user.getUserId(), Contact.class.getName(),
			user.getContactId(), null, null,
			(streetClaimStringParts.length > 0) ? streetClaimStringParts[0] :
				null,
			(streetClaimStringParts.length > 1) ? streetClaimStringParts[1] :
				null,
			(streetClaimStringParts.length > 2) ? streetClaimStringParts[2] :
				null,
			_getClaimString(
				"city", addressMapperJSONObject, userInfoJSONObject),
			_getClaimString("zip", addressMapperJSONObject, userInfoJSONObject),
			(region == null) ? 0 : region.getRegionId(),
			(country == null) ? 0 : country.getCountryId(),
			listType.getListTypeId(), false, false, null, serviceContext);
	}

	private void _addPhone(
			ServiceContext serviceContext, User user, String userInfoJSON,
			String userInfoMapperJSON)
		throws Exception {

		JSONObject userInfoMapperJSONObject = _jsonFactory.createJSONObject(
			userInfoMapperJSON);

		JSONObject phoneMapperJSONObject =
			userInfoMapperJSONObject.getJSONObject("phone");

		if (phoneMapperJSONObject == null) {
			return;
		}

		JSONObject userInfoJSONObject = _jsonFactory.createJSONObject(
			userInfoJSON);

		String phoneClaimString = _getClaimString(
			"phone", phoneMapperJSONObject, userInfoJSONObject);

		if (Validator.isNull(phoneClaimString)) {
			return;
		}

		ListType listType = _listTypeLocalService.getListType(
			_getClaimString(
				"phoneType", phoneMapperJSONObject, userInfoJSONObject),
			Contact.class.getName() + ".phone");

		if (listType == null) {
			List<ListType> listTypes = _listTypeLocalService.getListTypes(
				Contact.class.getName() + ".phone");

			listType = listTypes.get(0);
		}

		_phoneLocalService.addPhone(
			user.getUserId(), Contact.class.getName(), user.getContactId(),
			phoneClaimString, null, listType.getListTypeId(), false,
			serviceContext);
	}

	private User _addUser(
			long companyId, String issuer, ServiceContext serviceContext,
			String userInfoJSON, String userInfoMapperJSON)
		throws Exception {

		JSONObject userInfoMapperJSONObject = _jsonFactory.createJSONObject(
			userInfoMapperJSON);

		JSONObject userMapperJSONObject =
			userInfoMapperJSONObject.getJSONObject("user");

		JSONObject userInfoJSONObject = _jsonFactory.createJSONObject(
			userInfoJSON);

		String emailAddress = _getClaimString(
			"emailAddress", userMapperJSONObject, userInfoJSONObject);

		if (Validator.isNull(emailAddress)) {
			throw new OpenIdConnectServiceException.UserMappingException(
				"Email address is null");
		}

		String firstName = _getClaimString(
			"firstName", userMapperJSONObject, userInfoJSONObject);

		if (Validator.isNull(firstName)) {
			throw new OpenIdConnectServiceException.UserMappingException(
				"First name is null");
		}

		String lastName = _getClaimString(
			"lastName", userMapperJSONObject, userInfoJSONObject);

		if (Validator.isNull(lastName)) {
			throw new OpenIdConnectServiceException.UserMappingException(
				"Last name is null");
		}

		_checkAddUser(companyId, emailAddress);

		long creatorUserId = 0;
		boolean autoPassword = true;
		String password1 = null;
		String password2 = null;
		String screenName = _getClaimString(
			"screenName", userMapperJSONObject, userInfoJSONObject);
		long prefixListTypeId = 0;
		long suffixListTypeId = 0;

		JSONObject contactMapperJSONObject =
			userInfoMapperJSONObject.getJSONObject("contact");

		int[] birthday = _getBirthday(
			contactMapperJSONObject, userInfoJSONObject);

		long[] groupIds = null;
		long[] organizationIds = null;

		long[] roleIds = _getRoleIds(
			companyId, userInfoJSONObject,
			userInfoMapperJSONObject.getJSONObject("users_roles"));

		if ((roleIds == null) || (roleIds.length == 0)) {
			roleIds = _getRoleIds(companyId, issuer);
		}

		long[] userGroupIds = null;
		boolean sendEmail = false;

		User user = _userLocalService.addUser(
			creatorUserId, companyId, autoPassword, password1, password2,
			Validator.isNull(screenName), screenName, emailAddress,
			_getLocale(companyId, userInfoJSONObject, userMapperJSONObject),
			firstName,
			_getClaimString(
				"middleName", userMapperJSONObject, userInfoJSONObject),
			lastName, prefixListTypeId, suffixListTypeId,
			_isMale(contactMapperJSONObject, userInfoJSONObject), birthday[1],
			birthday[2], birthday[0],
			_getClaimString(
				"jobTitle", userMapperJSONObject, userInfoJSONObject),
			groupIds, organizationIds, roleIds, userGroupIds, sendEmail,
			serviceContext);

		return _userLocalService.updatePasswordReset(user.getUserId(), false);
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

		String birthdateClaimString = _getClaimString(
			"birthdate", contactMapperJSONObject, userInfoJSONObject);

		if (Validator.isNull(birthdateClaimString)) {
			return birthday;
		}

		String[] birthdateClaimStringParts = birthdateClaimString.split("-");

		if (!birthdateClaimStringParts[0].equals("0000")) {
			birthday[0] = GetterUtil.getInteger(birthdateClaimStringParts[0]);
		}

		if (birthdateClaimStringParts.length == 3) {
			birthday[1] =
				GetterUtil.getInteger(birthdateClaimStringParts[1]) - 1;
			birthday[2] = GetterUtil.getInteger(birthdateClaimStringParts[2]);
		}

		return birthday;
	}

	private JSONArray _getClaimJSONArray(
		String key, JSONObject mapperJSONObject,
		JSONObject userInfoJSONObject) {

		Object claimObject = _getClaimObject(
			key, mapperJSONObject, userInfoJSONObject);

		if ((claimObject == null) || (claimObject instanceof JSONArray)) {
			return (JSONArray)claimObject;
		}

		return null;
	}

	private Object _getClaimObject(
		String key, JSONObject mapperJSONObject,
		JSONObject userInfoJSONObject) {

		String value = mapperJSONObject.getString(key);

		if (Validator.isNull(value)) {
			return null;
		}

		String[] valueParts = value.split("->");

		Object claimObject = userInfoJSONObject.get(valueParts[0]);

		for (int i = 1; i < valueParts.length; ++i) {
			JSONObject claimJSONObject = (JSONObject)claimObject;

			if (claimJSONObject != null) {
				claimObject = claimJSONObject.get(valueParts[i]);
			}
		}

		return claimObject;
	}

	private String _getClaimString(
		String key, JSONObject mapperJSONObject,
		JSONObject userInfoJSONObject) {

		Object claimObject = _getClaimObject(
			key, mapperJSONObject, userInfoJSONObject);

		if ((claimObject != null) && !(claimObject instanceof String)) {
			throw new IllegalArgumentException("Claim is not a string");
		}

		return (String)claimObject;
	}

	private Locale _getLocale(
			long companyId, JSONObject userInfoJSONObject,
			JSONObject userMapperJSONObject)
		throws Exception {

		String languageId = _getClaimString(
			"languageId", userMapperJSONObject, userInfoJSONObject);

		if (Validator.isNotNull(languageId)) {
			return new Locale(languageId);
		}

		Company company = _companyLocalService.getCompany(companyId);

		return company.getLocale();
	}

	private long[] _getRoleIds(
		long companyId, JSONObject userInfoJSONObject,
		JSONObject usersRolesMapperJSONObject) {

		if ((usersRolesMapperJSONObject == null) ||
			(usersRolesMapperJSONObject.length() < 1)) {

			return null;
		}

		JSONArray rolesJSONArray = _getClaimJSONArray(
			"roles", usersRolesMapperJSONObject, userInfoJSONObject);

		if (rolesJSONArray == null) {
			return null;
		}

		List<Long> roleIds = new ArrayList<>();

		for (int i = 0; i < rolesJSONArray.length(); ++i) {
			Role role = _roleLocalService.fetchRole(
				companyId, rolesJSONArray.getString(i));

			if (role == null) {
				if (_log.isWarnEnabled()) {
					_log.warn("No role name " + rolesJSONArray.getString(i));
				}

				continue;
			}

			roleIds.add(role.getRoleId());
		}

		if (roleIds.isEmpty()) {
			return null;
		}

		return ArrayUtil.toLongArray(roleIds);
	}

	private long[] _getRoleIds(long companyId, String issuer) {
		if (Validator.isNull(issuer) ||
			!Objects.equals(
				issuer,
				_props.get(
					"open.id.connect.user.info.processor.impl.issuer"))) {

			return null;
		}

		String roleName = _props.get(
			"open.id.connect.user.info.processor.impl.regular.role");

		if (Validator.isNull(roleName)) {
			return null;
		}

		Role role = _roleLocalService.fetchRole(companyId, roleName);

		if (role == null) {
			return null;
		}

		if (role.getType() == RoleConstants.TYPE_REGULAR) {
			return new long[] {role.getRoleId()};
		}

		if (_log.isInfoEnabled()) {
			_log.info("Role " + roleName + " is not a regular role");
		}

		return null;
	}

	private long _getUserId(
			long companyId, String userInfoJSON, String userInfoMapperJSON)
		throws Exception {

		JSONObject userInfoMapperJSONObject = _jsonFactory.createJSONObject(
			userInfoMapperJSON);

		JSONObject userMapperJSONObject =
			userInfoMapperJSONObject.getJSONObject("user");

		JSONObject userInfoJSONObject = _jsonFactory.createJSONObject(
			userInfoJSON);

		User user = _userLocalService.fetchUserByEmailAddress(
			companyId,
			_getClaimString(
				"emailAddress", userMapperJSONObject, userInfoJSONObject));

		if (user != null) {
			return user.getUserId();
		}

		return 0;
	}

	private boolean _isMale(
		JSONObject contactMapperJSONObject, JSONObject userInfoJSONObject) {

		String gender = _getClaimString(
			"gender", contactMapperJSONObject, userInfoJSONObject);

		if (Validator.isNull(gender) || gender.equals("male")) {
			return true;
		}

		return false;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		OIDCUserInfoProcessor.class);

	@Reference
	private AddressLocalService _addressLocalService;

	@Reference
	private CompanyLocalService _companyLocalService;

	@Reference
	private CountryLocalService _countryLocalService;

	@Reference
	private JSONFactory _jsonFactory;

	@Reference
	private ListTypeLocalService _listTypeLocalService;

	@Reference
	private PhoneLocalService _phoneLocalService;

	@Reference
	private Props _props;

	@Reference
	private RegionLocalService _regionLocalService;

	@Reference
	private RoleLocalService _roleLocalService;

	@Reference
	private UserLocalService _userLocalService;

}