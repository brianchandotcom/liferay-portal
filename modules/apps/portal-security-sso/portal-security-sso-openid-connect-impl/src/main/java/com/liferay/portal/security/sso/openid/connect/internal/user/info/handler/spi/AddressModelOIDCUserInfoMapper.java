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

import com.liferay.portal.kernel.model.Contact;
import com.liferay.portal.kernel.model.Country;
import com.liferay.portal.kernel.model.ListType;
import com.liferay.portal.kernel.model.Region;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.AddressLocalService;
import com.liferay.portal.kernel.service.CountryLocalService;
import com.liferay.portal.kernel.service.ListTypeLocalService;
import com.liferay.portal.kernel.service.RegionLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;

import com.nimbusds.jose.util.JSONObjectUtils;

import java.util.List;

import net.minidev.json.JSONObject;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Arthur Chan
 */
@Component(immediate = true, service = AddressModelOIDCUserInfoMapper.class)
public class AddressModelOIDCUserInfoMapper {

	public void process(
			long userId, ServiceContext serviceContext, String userInfoJSON,
			String userInfoMapperJSON)
		throws Exception {

		JSONObject userInfoMapperJSONObject = JSONObjectUtils.parse(
			userInfoMapperJSON);

		JSONObject addressMapperJSONObject = JSONObjectUtils.getJSONObject(
			userInfoMapperJSONObject, "address");

		if (addressMapperJSONObject == null) {
			return;
		}

		JSONObject userInfoJSONObject = JSONObjectUtils.parse(userInfoJSON);

		try {
			ListType contactAddressListType = _listTypeLocalService.getListType(
				OIDCUserInfoClaimUtil.getStringClaim(
					"addressType", addressMapperJSONObject, userInfoJSONObject),
				Contact.class.getName() + ".address");

			if (contactAddressListType == null) {

				// Type is not a must by contract, but required by Liferay

				List<ListType> contactAddressListTypes =
					_listTypeLocalService.getListTypes(
						Contact.class.getName() + ".address");

				contactAddressListType = contactAddressListTypes.get(0);
			}

			Country country = null;

			Region region = null;

			String countryClaim = OIDCUserInfoClaimUtil.getStringClaim(
				"country", addressMapperJSONObject, userInfoJSONObject);

			User user = _userLocalService.fetchUser(userId);

			if (Validator.isNotNull(countryClaim)) {
				if ((countryClaim.charAt(0) >= '0') &&
					(countryClaim.charAt(0) <= '9')) {

					country = _countryLocalService.getCountryByNumber(
						user.getCompanyId(), countryClaim);
				}
				else if (countryClaim.length() == 2) {
					country = _countryLocalService.fetchCountryByA2(
						user.getCompanyId(),
						StringUtil.toUpperCase(countryClaim));
				}
				else if (countryClaim.length() == 3) {
					country = _countryLocalService.fetchCountryByA3(
						user.getCompanyId(),
						StringUtil.toUpperCase(countryClaim));
				}
				else {
					country = _countryLocalService.fetchCountryByName(
						user.getCompanyId(),
						StringUtil.toLowerCase(countryClaim));
				}

				String regionClaim = OIDCUserInfoClaimUtil.getStringClaim(
					"region", addressMapperJSONObject, userInfoJSONObject);

				if ((country != null) && Validator.isNotNull(regionClaim)) {
					region = _regionLocalService.fetchRegion(
						country.getCountryId(),
						StringUtil.toUpperCase(regionClaim));
				}
			}

			String streetClaim = OIDCUserInfoClaimUtil.getStringClaim(
				"street", addressMapperJSONObject, userInfoJSONObject);

			String[] streetParts = streetClaim.split("\n");

			_addressLocalService.addAddress(
				null, user.getUserId(), Contact.class.getName(),
				user.getContactId(), null, null,
				(streetParts.length > 0) ? streetParts[0] : null,
				(streetParts.length > 1) ? streetParts[1] : null,
				(streetParts.length > 2) ? streetParts[2] : null,
				OIDCUserInfoClaimUtil.getStringClaim(
					"city", addressMapperJSONObject, userInfoJSONObject),
				OIDCUserInfoClaimUtil.getStringClaim(
					"zip", addressMapperJSONObject, userInfoJSONObject),
				(region == null) ? 0 : region.getRegionId(),
				(country == null) ? 0 : country.getCountryId(),
				contactAddressListType.getListTypeId(), false, false, null,
				serviceContext);
		}
		catch (Exception exception) {
			throw new Exception(
				"Unable to add address for user: " + userId, exception);
		}
	}

	@Reference
	private AddressLocalService _addressLocalService;

	@Reference
	private CountryLocalService _countryLocalService;

	@Reference
	private ListTypeLocalService _listTypeLocalService;

	@Reference
	private RegionLocalService _regionLocalService;

	@Reference
	private UserLocalService _userLocalService;

}