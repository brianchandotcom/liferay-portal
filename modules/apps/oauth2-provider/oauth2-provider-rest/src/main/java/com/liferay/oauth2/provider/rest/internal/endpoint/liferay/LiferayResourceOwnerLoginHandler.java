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

package com.liferay.oauth2.provider.rest.internal.endpoint.liferay;

import com.liferay.oauth2.provider.rest.internal.endpoint.constants.OAuth2ProviderRESTEndpointConstants;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.CompanyConstants;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.security.auth.Authenticator;
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.MapUtil;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.apache.cxf.rs.security.oauth2.common.Client;
import org.apache.cxf.rs.security.oauth2.common.UserSubject;
import org.apache.cxf.rs.security.oauth2.grants.owner.ResourceOwnerLoginHandler;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Carlos Sierra Andrés
 */
@Component(service = ResourceOwnerLoginHandler.class)
public class LiferayResourceOwnerLoginHandler
	implements ResourceOwnerLoginHandler {

	@Override
	public UserSubject createSubject(
		Client client, String login, String password) {

		try {
			User user = _authenticateUser(login, password);

			if (user == null) {
				return null;
			}

			UserSubject userSubject = new UserSubject(
				user.getLogin(), String.valueOf(user.getUserId()));

			Map<String, String> properties = userSubject.getProperties();

			properties.put(
				OAuth2ProviderRESTEndpointConstants.PROPERTY_KEY_COMPANY_ID,
				String.valueOf(user.getCompanyId()));

			return userSubject;
		}
		catch (PortalException portalException) {
			_log.error(portalException, portalException);

			return null;
		}
	}

	private User _authenticateUser(String login, String password) {
		int authResult = Authenticator.FAILURE;

		Company company = _companyLocalService.fetchCompany(
			CompanyThreadLocal.getCompanyId());

		String authType = company.getAuthType();

		Map<String, Object> resultsMap = new HashMap<>();

		try {
			if (authType.equals(CompanyConstants.AUTH_TYPE_EA)) {
				authResult = _userLocalService.authenticateByEmailAddress(
					company.getCompanyId(), login, password,
					Collections.emptyMap(), Collections.emptyMap(), resultsMap);
			}
			else if (authType.equals(CompanyConstants.AUTH_TYPE_SN)) {
				authResult = _userLocalService.authenticateByScreenName(
					company.getCompanyId(), login, password,
					Collections.emptyMap(), Collections.emptyMap(), resultsMap);
			}
			else if (authType.equals(CompanyConstants.AUTH_TYPE_ID)) {
				authResult = _userLocalService.authenticateByUserId(
					company.getCompanyId(), GetterUtil.getLong(login), password,
					Collections.emptyMap(), Collections.emptyMap(), resultsMap);
			}
		}
		catch (PortalException portalException) {
			_log.error(portalException, portalException);

			return null;
		}

		if (authResult == Authenticator.FAILURE) {
			return null;
		}

		long userId = MapUtil.getLong(resultsMap, "userId", -1);

		if (userId == -1) {
			return null;
		}

		return _userLocalService.fetchUser(userId);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		LiferayResourceOwnerLoginHandler.class);

	@Reference
	private CompanyLocalService _companyLocalService;

	@Reference
	private UserLocalService _userLocalService;

}