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
import com.liferay.portal.kernel.model.ListType;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.ListTypeLocalService;
import com.liferay.portal.kernel.service.PhoneLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.UserLocalService;

import com.nimbusds.jose.util.JSONObjectUtils;

import java.util.List;

import net.minidev.json.JSONObject;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Arthur Chan
 */
@Component(
	immediate = true, service = OpenIdConnectPhoneUserMapperProcessor.class
)
public class OpenIdConnectPhoneUserMapperProcessor {

	public void process(
			long userId, ServiceContext serviceContext, String userInfoJSON,
			String userInfoMapperJSON)
		throws Exception {

		JSONObject userInfoMapperJSONObject = JSONObjectUtils.parse(
			userInfoMapperJSON);

		JSONObject phoneMapperJSONObject = JSONObjectUtils.getJSONObject(
			userInfoMapperJSONObject, "phone");

		if (phoneMapperJSONObject == null) {
			return;
		}

		JSONObject userInfoJSONObject = JSONObjectUtils.parse(userInfoJSON);

		try {
			ListType contactPhoneListType = _listTypeLocalService.getListType(
				OpenIdConnectUserInfoClaimUtil.getStringClaim(
					"phoneType", phoneMapperJSONObject, userInfoJSONObject),
				Contact.class.getName() + ".phone");

			if (contactPhoneListType == null) {

				// Type is not a must by contract, but required by Liferay

				List<ListType> contactPhoneListTypes =
					_listTypeLocalService.getListTypes(
						Contact.class.getName() + ".phone");

				contactPhoneListType = contactPhoneListTypes.get(0);
			}

			User user = _userLocalService.fetchUser(userId);

			_phoneLocalService.addPhone(
				user.getUserId(), Contact.class.getName(), user.getContactId(),
				OpenIdConnectUserInfoClaimUtil.getStringClaim(
					"phone", phoneMapperJSONObject, userInfoJSONObject),
				null, contactPhoneListType.getListTypeId(), false,
				serviceContext);
		}
		catch (Exception exception) {
			throw new Exception(
				"Unable to add phone for user: " + userId, exception);
		}
	}

	@Reference
	private ListTypeLocalService _listTypeLocalService;

	@Reference
	private PhoneLocalService _phoneLocalService;

	@Reference
	private UserLocalService _userLocalService;

}