/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.oauth.client.persistence.internal.security.permission.resource;

import com.liferay.oauth.client.persistence.model.OAuthClientASLocalMetadata;
import com.liferay.oauth.client.persistence.service.OAuthClientASLocalMetadataLocalService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.security.permission.resource.PortletResourcePermission;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Arthur Chan
 */
@Component(
	property = "model.class.name=com.liferay.oauth.client.persistence.model.OAuthClientASLocalMetadata",
	service = ModelResourcePermission.class
)
public class OAuthClientASLocalMetadataModelResourcePermission
	implements ModelResourcePermission<OAuthClientASLocalMetadata> {

	@Override
	public void check(
			PermissionChecker permissionChecker,
			long oAuthClientASLocalMetadataId, String actionId)
		throws PortalException {

		if (!contains(
				permissionChecker, oAuthClientASLocalMetadataId, actionId)) {

			throw new PrincipalException.MustHavePermission(
				permissionChecker, OAuthClientASLocalMetadata.class.getName(),
				oAuthClientASLocalMetadataId, actionId);
		}
	}

	@Override
	public void check(
			PermissionChecker permissionChecker,
			OAuthClientASLocalMetadata oAuthClientASLocalMetadata,
			String actionId)
		throws PortalException {

		if (!contains(
				permissionChecker, oAuthClientASLocalMetadata, actionId)) {

			throw new PrincipalException.MustHavePermission(
				permissionChecker, OAuthClientASLocalMetadata.class.getName(),
				oAuthClientASLocalMetadata.getOAuthClientASLocalMetadataId(),
				actionId);
		}
	}

	@Override
	public boolean contains(
			PermissionChecker permissionChecker,
			long oAuthClientASLocalMetadataId, String actionId)
		throws PortalException {

		return contains(
			permissionChecker,
			_oAuthClientASLocalMetadataLocalService.
				getOAuthClientASLocalMetadata(oAuthClientASLocalMetadataId),
			actionId);
	}

	@Override
	public boolean contains(
			PermissionChecker permissionChecker,
			OAuthClientASLocalMetadata oAuthClientASLocalMetadata,
			String actionId)
		throws PortalException {

		if (permissionChecker.hasOwnerPermission(
				oAuthClientASLocalMetadata.getCompanyId(),
				OAuthClientASLocalMetadata.class.getName(),
				oAuthClientASLocalMetadata.getOAuthClientASLocalMetadataId(),
				oAuthClientASLocalMetadata.getUserId(), actionId)) {

			return true;
		}

		return permissionChecker.hasPermission(
			null, OAuthClientASLocalMetadata.class.getName(),
			oAuthClientASLocalMetadata.getOAuthClientASLocalMetadataId(),
			actionId);
	}

	@Override
	public String getModelName() {
		return OAuthClientASLocalMetadata.class.getName();
	}

	@Override
	public PortletResourcePermission getPortletResourcePermission() {
		return _portletResourcePermission;
	}

	@Reference
	private OAuthClientASLocalMetadataLocalService
		_oAuthClientASLocalMetadataLocalService;

	@Reference(target = "(resource.name=com.liferay.oauth.client.persistence)")
	private PortletResourcePermission _portletResourcePermission;

}