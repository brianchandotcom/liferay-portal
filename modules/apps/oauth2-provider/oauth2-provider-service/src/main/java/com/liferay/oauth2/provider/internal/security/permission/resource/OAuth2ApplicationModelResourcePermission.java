/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.oauth2.provider.internal.security.permission.resource;

import com.liferay.oauth2.provider.constants.OAuth2ProviderConstants;
import com.liferay.oauth2.provider.model.OAuth2Application;
import com.liferay.oauth2.provider.service.OAuth2ApplicationLocalService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.security.permission.resource.PortletResourcePermission;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Tomas Polesovsky
 */
@Component(
	property = "model.class.name=com.liferay.oauth2.provider.model.OAuth2Application",
	service = ModelResourcePermission.class
)
public class OAuth2ApplicationModelResourcePermission
	implements ModelResourcePermission<OAuth2Application> {

	@Override
	public void check(
			PermissionChecker permissionChecker, long oAuth2ApplicationId,
			String actionId)
		throws PortalException {

		if (!contains(permissionChecker, oAuth2ApplicationId, actionId)) {
			throw new PrincipalException.MustHavePermission(
				permissionChecker, OAuth2Application.class.getName(),
				oAuth2ApplicationId, actionId);
		}
	}

	@Override
	public void check(
			PermissionChecker permissionChecker,
			OAuth2Application oAuth2Application, String actionId)
		throws PortalException {

		if (!contains(permissionChecker, oAuth2Application, actionId)) {
			throw new PrincipalException.MustHavePermission(
				permissionChecker, OAuth2Application.class.getName(),
				oAuth2Application.getOAuth2ApplicationId(), actionId);
		}
	}

	@Override
	public boolean contains(
			PermissionChecker permissionChecker, long oAuth2ApplicationId,
			String actionId)
		throws PortalException {

		return contains(
			permissionChecker,
			_oAuth2ApplicationLocalService.getOAuth2Application(
				oAuth2ApplicationId),
			actionId);
	}

	@Override
	public boolean contains(
			PermissionChecker permissionChecker,
			OAuth2Application oAuth2Application, String actionId)
		throws PortalException {

		if (permissionChecker.hasOwnerPermission(
				oAuth2Application.getCompanyId(),
				OAuth2Application.class.getName(),
				oAuth2Application.getOAuth2ApplicationId(),
				oAuth2Application.getUserId(), actionId)) {

			return true;
		}

		return permissionChecker.hasPermission(
			null, OAuth2Application.class.getName(),
			oAuth2Application.getOAuth2ApplicationId(), actionId);
	}

	@Override
	public String getModelName() {
		return OAuth2Application.class.getName();
	}

	@Override
	public PortletResourcePermission getPortletResourcePermission() {
		return _portletResourcePermission;
	}

	@Reference
	private OAuth2ApplicationLocalService _oAuth2ApplicationLocalService;

	@Reference(
		target = "(resource.name=" + OAuth2ProviderConstants.RESOURCE_NAME + ")"
	)
	private PortletResourcePermission _portletResourcePermission;

}