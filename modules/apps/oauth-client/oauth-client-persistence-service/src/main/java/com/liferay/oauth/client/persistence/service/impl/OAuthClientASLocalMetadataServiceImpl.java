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

package com.liferay.oauth.client.persistence.service.impl;

import com.liferay.oauth.client.persistence.constants.OAuthClientPersistenceActionKeys;
import com.liferay.oauth.client.persistence.model.OAuthClientASLocalMetadata;
import com.liferay.oauth.client.persistence.service.base.OAuthClientASLocalMetadataServiceBaseImpl;
import com.liferay.portal.aop.AopService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.GroupConstants;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermissionUtil;

import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Arthur Chan
 */
@Component(
	property = {
		"json.web.service.context.name=oauthclient",
		"json.web.service.context.path=OAuthClientASLocalMetadata"
	},
	service = AopService.class
)
public class OAuthClientASLocalMetadataServiceImpl
	extends OAuthClientASLocalMetadataServiceBaseImpl {

	@Override
	public OAuthClientASLocalMetadata addOAuthClientASLocalMetadata(
			long userId, String metadataJSON, String wellKnownURISuffix)
		throws PortalException {

		ModelResourcePermissionUtil.check(
			_oAuthClientASLocalMetadataModelResourcePermission,
			getPermissionChecker(), GroupConstants.DEFAULT_LIVE_GROUP_ID, 0,
			OAuthClientPersistenceActionKeys.
				ACTION_ADD_OAUTH_CLIENT_AS_LOCAL_METADATA);

		return oAuthClientASLocalMetadataLocalService.
			addOAuthClientASLocalMetadata(
				userId, metadataJSON, wellKnownURISuffix);
	}

	@Override
	public OAuthClientASLocalMetadata deleteOAuthClientASLocalMetadata(
			long oAuthClientASLocalMetadataId)
		throws PortalException {

		OAuthClientASLocalMetadata oAuthClientASLocalMetadata =
			oAuthClientASLocalMetadataLocalService.
				getOAuthClientASLocalMetadata(oAuthClientASLocalMetadataId);

		_oAuthClientASLocalMetadataModelResourcePermission.check(
			getPermissionChecker(), oAuthClientASLocalMetadata,
			ActionKeys.DELETE);

		return oAuthClientASLocalMetadataLocalService.
			deleteOAuthClientASLocalMetadata(oAuthClientASLocalMetadata);
	}

	@Override
	public OAuthClientASLocalMetadata deleteOAuthClientASLocalMetadata(
			long companyId, String localWellKnownURI)
		throws PortalException {

		OAuthClientASLocalMetadata oAuthClientASLocalMetadata =
			oAuthClientASLocalMetadataLocalService.
				getOAuthClientASLocalMetadata(companyId, localWellKnownURI);

		_oAuthClientASLocalMetadataModelResourcePermission.check(
			getPermissionChecker(), oAuthClientASLocalMetadata,
			ActionKeys.DELETE);

		return oAuthClientASLocalMetadataLocalService.
			deleteOAuthClientASLocalMetadata(oAuthClientASLocalMetadata);
	}

	@Override
	public List<OAuthClientASLocalMetadata>
		getCompanyOAuthClientASLocalMetadata(long companyId) {

		return oAuthClientASLocalMetadataPersistence.filterFindByCompanyId(
			companyId);
	}

	@Override
	public List<OAuthClientASLocalMetadata>
		getCompanyOAuthClientASLocalMetadata(
			long companyId, int start, int end) {

		return oAuthClientASLocalMetadataPersistence.filterFindByCompanyId(
			companyId, start, end);
	}

	@Override
	public OAuthClientASLocalMetadata getOAuthClientASLocalMetadata(
			long companyId, String localWellKnownURI)
		throws PortalException {

		OAuthClientASLocalMetadata oAuthClientASLocalMetadata =
			oAuthClientASLocalMetadataLocalService.
				getOAuthClientASLocalMetadata(companyId, localWellKnownURI);

		_oAuthClientASLocalMetadataModelResourcePermission.check(
			getPermissionChecker(), oAuthClientASLocalMetadata,
			ActionKeys.VIEW);

		return oAuthClientASLocalMetadata;
	}

	@Override
	public List<OAuthClientASLocalMetadata> getUserOAuthClientASLocalMetadata(
		long userId) {

		return oAuthClientASLocalMetadataPersistence.filterFindByUserId(userId);
	}

	@Override
	public List<OAuthClientASLocalMetadata> getUserOAuthClientASLocalMetadata(
		long userId, int start, int end) {

		return oAuthClientASLocalMetadataPersistence.filterFindByUserId(
			userId, start, end);
	}

	@Override
	public OAuthClientASLocalMetadata updateOAuthClientASLocalMetadata(
			long oAuthClientASLocalMetadataId, String metadataJSON,
			String wellKnownURISuffix)
		throws PortalException {

		_oAuthClientASLocalMetadataModelResourcePermission.check(
			getPermissionChecker(),
			oAuthClientASLocalMetadataLocalService.
				getOAuthClientASLocalMetadata(oAuthClientASLocalMetadataId),
			ActionKeys.UPDATE);

		return oAuthClientASLocalMetadataLocalService.
			updateOAuthClientASLocalMetadata(
				oAuthClientASLocalMetadataId, metadataJSON, wellKnownURISuffix);
	}

	@Reference(
		target = "(model.class.name=com.liferay.oauth.client.persistence.model.OAuthClientASLocalMetadata)"
	)
	private ModelResourcePermission<OAuthClientASLocalMetadata>
		_oAuthClientASLocalMetadataModelResourcePermission;

}