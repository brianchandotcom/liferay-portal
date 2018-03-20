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

package com.liferay.oauth2.provider.service.impl;

import com.liferay.oauth2.provider.constants.GrantType;
import com.liferay.oauth2.provider.constants.OAuth2ProviderActionKeys;
import com.liferay.oauth2.provider.constants.OAuth2ProviderConstants;
import com.liferay.oauth2.provider.exception.NoSuchOAuth2ApplicationException;
import com.liferay.oauth2.provider.model.OAuth2Application;
import com.liferay.oauth2.provider.service.base.OAuth2ApplicationServiceBaseImpl;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.jsonwebservice.JSONWebService;
import com.liferay.portal.kernel.jsonwebservice.JSONWebServiceMode;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.io.InputStream;

import java.util.List;

/**
 * @author Brian Wing Shun Chan
 */
@JSONWebService(mode = JSONWebServiceMode.IGNORE)
public class OAuth2ApplicationServiceImpl
	extends OAuth2ApplicationServiceBaseImpl {

	@Override
	public OAuth2Application addOAuth2Application(
			List<GrantType> allowedGrantTypesList, String clientId,
			int clientProfile, String clientSecret, String description,
			List<String> featuresList, String homePageURL, long iconFileEntryId,
			String name, String privacyPolicyURL, List<String> redirectURIsList,
			List<String> scopeAliasesList, ServiceContext serviceContext)
		throws PortalException {

		check(OAuth2ProviderActionKeys.ACTION_ADD_APPLICATION);

		User user = getUser();

		return oAuth2ApplicationLocalService.addOAuth2Application(
			user.getCompanyId(), user.getUserId(), user.getFullName(),
			allowedGrantTypesList, clientId, clientProfile, clientSecret,
			description, featuresList, homePageURL, iconFileEntryId, name,
			privacyPolicyURL, redirectURIsList, scopeAliasesList,
			serviceContext);
	}

	@Override
	public void check(OAuth2Application oAuth2Application, String action)
		throws PrincipalException {

		PermissionChecker permissionChecker = getPermissionChecker();

		if (permissionChecker.hasOwnerPermission(
				oAuth2Application.getCompanyId(),
				OAuth2Application.class.getName(),
				oAuth2Application.getOAuth2ApplicationId(),
				oAuth2Application.getUserId(), action)) {

			return;
		}

		if (!permissionChecker.hasPermission(
				0, OAuth2Application.class.getName(),
				oAuth2Application.getOAuth2ApplicationId(), action)) {

			throw new PrincipalException.MustHavePermission(
				permissionChecker, OAuth2Application.class.getName(),
				oAuth2Application.getOAuth2ApplicationId(), action);
		}
	}

	@Override
	public void check(String action) throws PrincipalException {
		PermissionChecker permissionChecker = getPermissionChecker();

		if (!permissionChecker.hasPermission(
				0, OAuth2ProviderConstants.RESOURCE_NAME, 0, action)) {

			throw new PrincipalException.MustHavePermission(
				permissionChecker, OAuth2ProviderConstants.RESOURCE_NAME, 0,
				action);
		}
	}

	@Override
	public OAuth2Application deleteOAuth2Application(long oAuth2ApplicationId)
		throws PortalException {

		OAuth2Application oAuth2Application =
			oAuth2ApplicationLocalService.getOAuth2Application(
				oAuth2ApplicationId);

		check(oAuth2Application, ActionKeys.DELETE);

		oAuth2Application =
			oAuth2ApplicationLocalService.deleteOAuth2Application(
				oAuth2ApplicationId);

		return oAuth2Application;
	}

	@Override
	public OAuth2Application fetchOAuth2Application(
			long companyId, String clientId)
		throws PrincipalException {

		OAuth2Application oAuth2Application =
			oAuth2ApplicationLocalService.fetchOAuth2Application(
				companyId, clientId);

		if (oAuth2Application != null) {
			check(oAuth2Application, ActionKeys.VIEW);
		}

		return oAuth2Application;
	}

	@Override
	public OAuth2Application getOAuth2Application(long oAuth2ApplicationId)
		throws PortalException {

		OAuth2Application oAuth2Application =
			oAuth2ApplicationLocalService.getOAuth2Application(
				oAuth2ApplicationId);

		check(oAuth2Application, ActionKeys.VIEW);

		return oAuth2Application;
	}

	@Override
	public OAuth2Application getOAuth2Application(
			long companyId, String clientId)
		throws NoSuchOAuth2ApplicationException, PrincipalException {

		OAuth2Application oAuth2Application =
			oAuth2ApplicationLocalService.getOAuth2Application(
				companyId, clientId);

		check(oAuth2Application, ActionKeys.VIEW);

		return oAuth2Application;
	}

	@Override
	public List<OAuth2Application> getOAuth2Applications(
		long companyId, int start, int end,
		OrderByComparator<OAuth2Application> orderByComparator) {

		return oAuth2ApplicationPersistence.filterFindByC(
			companyId, start, end, orderByComparator);
	}

	@Override
	public int getOAuth2ApplicationsCount(long companyId) {
		return oAuth2ApplicationPersistence.filterCountByC(companyId);
	}

	@Override
	public OAuth2Application updateIcon(
			long oAuth2ApplicationId, InputStream inputStream)
		throws PortalException {

		OAuth2Application oAuth2Application =
			oAuth2ApplicationLocalService.getOAuth2Application(
				oAuth2ApplicationId);

		check(oAuth2Application, ActionKeys.UPDATE);

		return oAuth2ApplicationLocalService.updateIcon(
			oAuth2ApplicationId, inputStream);
	}

	@Override
	public OAuth2Application updateOAuth2Application(
			long oAuth2ApplicationId, List<GrantType> allowedGrantTypesList,
			String clientId, int clientProfile, String clientSecret,
			String description, List<String> featuresList, String homePageURL,
			long iconFileEntryId, String name, String privacyPolicyURL,
			List<String> redirectURIsList, List<String> scopeAliasesList,
			ServiceContext serviceContext)
		throws PortalException {

		OAuth2Application oAuth2Application =
			oAuth2ApplicationLocalService.getOAuth2Application(
				oAuth2ApplicationId);

		check(oAuth2Application, ActionKeys.UPDATE);

		return oAuth2ApplicationLocalService.updateOAuth2Application(
			oAuth2ApplicationId, allowedGrantTypesList, clientId, clientProfile,
			clientSecret, description, featuresList, homePageURL,
			iconFileEntryId, name, privacyPolicyURL, redirectURIsList,
			scopeAliasesList, serviceContext);
	}

	@Override
	public OAuth2Application updateScopeAliases(
			long oAuth2ApplicationId, List<String> scopeAliasesList)
		throws PortalException {

		OAuth2Application oAuth2Application =
			oAuth2ApplicationLocalService.getOAuth2Application(
				oAuth2ApplicationId);

		check(oAuth2Application, ActionKeys.UPDATE);

		return oAuth2ApplicationLocalService.updateScopeAliases(
			oAuth2ApplicationId, scopeAliasesList);
	}

}