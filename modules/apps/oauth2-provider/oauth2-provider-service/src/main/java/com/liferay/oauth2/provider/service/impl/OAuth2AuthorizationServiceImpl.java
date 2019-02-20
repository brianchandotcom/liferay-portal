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

import com.liferay.oauth2.provider.constants.OAuth2ProviderActionKeys;
import com.liferay.oauth2.provider.model.OAuth2Application;
import com.liferay.oauth2.provider.model.OAuth2Authorization;
import com.liferay.oauth2.provider.service.base.OAuth2AuthorizationServiceBaseImpl;
import com.liferay.portal.aop.AopService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.jsonwebservice.JSONWebService;
import com.liferay.portal.kernel.jsonwebservice.JSONWebServiceMode;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Brian Wing Shun Chan
 */
@Component(service = AopService.class)
@JSONWebService(mode = JSONWebServiceMode.IGNORE)
public class OAuth2AuthorizationServiceImpl
	extends OAuth2AuthorizationServiceBaseImpl {

	@Override
	public List<OAuth2Authorization> getApplicationOAuth2Authorizations(
			long oAuth2ApplicationId, int start, int end,
			OrderByComparator<OAuth2Authorization> orderByComparator)
		throws PortalException {

		_oAuth2ApplicationModelResourcePermission.check(
			getPermissionChecker(), oAuth2ApplicationId, ActionKeys.VIEW);

		return oAuth2AuthorizationLocalService.getOAuth2Authorizations(
			oAuth2ApplicationId, start, end, orderByComparator);
	}

	@Override
	public int getApplicationOAuth2AuthorizationsCount(long oAuth2ApplicationId)
		throws PortalException {

		_oAuth2ApplicationModelResourcePermission.check(
			getPermissionChecker(), oAuth2ApplicationId, ActionKeys.VIEW);

		return oAuth2AuthorizationLocalService.getOAuth2AuthorizationsCount(
			oAuth2ApplicationId);
	}

	@Override
	public List<OAuth2Authorization> getUserOAuth2Authorizations(
			int start, int end,
			OrderByComparator<OAuth2Authorization> orderByComparator)
		throws PortalException {

		User user = getUser();

		return oAuth2AuthorizationLocalService.getUserOAuth2Authorizations(
			user.getUserId(), start, end, orderByComparator);
	}

	@Override
	public int getUserOAuth2AuthorizationsCount() throws PortalException {
		User user = getUser();

		return oAuth2AuthorizationLocalService.getUserOAuth2AuthorizationsCount(
			user.getUserId());
	}

	@Override
	public void revokeOAuth2Authorization(long oAuth2AuthorizationId)
		throws PortalException {

		OAuth2Authorization oAuth2Authorization =
			oAuth2AuthorizationLocalService.getOAuth2Authorization(
				oAuth2AuthorizationId);

		User user = getUser();

		if (user.getUserId() != oAuth2Authorization.getUserId()) {
			_oAuth2ApplicationModelResourcePermission.check(
				getPermissionChecker(),
				oAuth2Authorization.getOAuth2ApplicationId(),
				OAuth2ProviderActionKeys.ACTION_REVOKE_TOKEN);
		}

		oAuth2AuthorizationLocalService.deleteOAuth2Authorization(
			oAuth2AuthorizationId);
	}

	@Reference(
		target = "(model.class.name=com.liferay.oauth2.provider.model.OAuth2Application)"
	)
	private ModelResourcePermission<OAuth2Application>
		_oAuth2ApplicationModelResourcePermission;

}