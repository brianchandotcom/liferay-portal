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
import com.liferay.oauth.client.persistence.model.OAuthClientEntry;
import com.liferay.oauth.client.persistence.service.base.OAuthClientEntryServiceBaseImpl;
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
		"json.web.service.context.path=OAuthClientEntry"
	},
	service = AopService.class
)
public class OAuthClientEntryServiceImpl
	extends OAuthClientEntryServiceBaseImpl {

	@Override
	public OAuthClientEntry addOAuthClientEntry(
			long userId, String authRequestParametersJSON,
			String authServerWellKnownURI, String infoJSON,
			String tokenRequestParametersJSON, String userInfoMapperJSON)
		throws PortalException {

		ModelResourcePermissionUtil.check(
			_oAuthClientEntryModelResourcePermission, getPermissionChecker(),
			GroupConstants.DEFAULT_LIVE_GROUP_ID, 0,
			OAuthClientPersistenceActionKeys.ACTION_ADD_OAUTH_CLIENT_ENTRY);

		return oAuthClientEntryLocalService.addOAuthClientEntry(
			userId, authRequestParametersJSON, authServerWellKnownURI, infoJSON,
			tokenRequestParametersJSON, userInfoMapperJSON);
	}

	@Override
	public OAuthClientEntry deleteOAuthClientEntry(long oAuthClientEntryId)
		throws PortalException {

		OAuthClientEntry oAuthClientEntry =
			oAuthClientEntryLocalService.getOAuthClientEntry(
				oAuthClientEntryId);

		_oAuthClientEntryModelResourcePermission.check(
			getPermissionChecker(), oAuthClientEntry, ActionKeys.DELETE);

		return oAuthClientEntryLocalService.deleteOAuthClientEntry(
			oAuthClientEntry);
	}

	@Override
	public OAuthClientEntry deleteOAuthClientEntry(
			long companyId, String authServerWellKnownURI, String clientId)
		throws PortalException {

		OAuthClientEntry oAuthClientEntry =
			oAuthClientEntryLocalService.getOAuthClientEntry(
				companyId, authServerWellKnownURI, clientId);

		_oAuthClientEntryModelResourcePermission.check(
			getPermissionChecker(), oAuthClientEntry, ActionKeys.DELETE);

		return oAuthClientEntryLocalService.deleteOAuthClientEntry(
			oAuthClientEntry);
	}

	@Override
	public List<OAuthClientEntry>
			getAuthServerWellKnownURISuffixOAuthClientEntries(
				long companyId, String authServerWellKnownURISuffix)
		throws PortalException {

		List<OAuthClientEntry> oAuthClientEntries =
			oAuthClientEntryLocalService.
				getAuthServerWellKnownURISuffixOAuthClientEntries(
					companyId, authServerWellKnownURISuffix);

		for (OAuthClientEntry oAuthClientEntry : oAuthClientEntries) {
			_oAuthClientEntryModelResourcePermission.check(
				getPermissionChecker(), oAuthClientEntry, ActionKeys.VIEW);
		}

		return oAuthClientEntries;
	}

	@Override
	public List<OAuthClientEntry> getCompanyOAuthClientEntries(long companyId) {
		return oAuthClientEntryPersistence.filterFindByCompanyId(companyId);
	}

	@Override
	public List<OAuthClientEntry> getCompanyOAuthClientEntries(
		long companyId, int start, int end) {

		return oAuthClientEntryPersistence.filterFindByCompanyId(
			companyId, start, end);
	}

	@Override
	public OAuthClientEntry getOAuthClientEntry(
			long companyId, String authServerWellKnownURI, String clientId)
		throws PortalException {

		OAuthClientEntry oAuthClientEntry =
			oAuthClientEntryLocalService.getOAuthClientEntry(
				companyId, authServerWellKnownURI, clientId);

		_oAuthClientEntryModelResourcePermission.check(
			getPermissionChecker(), oAuthClientEntry, ActionKeys.VIEW);

		return oAuthClientEntry;
	}

	@Override
	public List<OAuthClientEntry> getUserOAuthClientEntries(long userId) {
		return oAuthClientEntryPersistence.filterFindByUserId(userId);
	}

	@Override
	public List<OAuthClientEntry> getUserOAuthClientEntries(
		long userId, int start, int end) {

		return oAuthClientEntryPersistence.filterFindByUserId(
			userId, start, end);
	}

	@Override
	public OAuthClientEntry updateOAuthClientEntry(
			long oAuthClientEntryId, String authRequestParametersJSON,
			String authServerWellKnownURI, String infoJSON,
			String tokenRequestParametersJSON, String userInfoMapperJSON)
		throws PortalException {

		_oAuthClientEntryModelResourcePermission.check(
			getPermissionChecker(), oAuthClientEntryId, ActionKeys.UPDATE);

		return oAuthClientEntryLocalService.updateOAuthClientEntry(
			oAuthClientEntryId, authRequestParametersJSON,
			authServerWellKnownURI, infoJSON, tokenRequestParametersJSON,
			userInfoMapperJSON);
	}

	@Reference(
		target = "(model.class.name=com.liferay.oauth.client.persistence.model.OAuthClientEntry)"
	)
	private ModelResourcePermission<OAuthClientEntry>
		_oAuthClientEntryModelResourcePermission;

}