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

package com.liferay.oauth2.provider.rest.internal.endpoint.access.token.grant.handler;

import com.liferay.oauth2.provider.constants.OAuth2ProviderActionKeys;
import com.liferay.oauth2.provider.model.OAuth2Application;
import com.liferay.oauth2.provider.rest.internal.endpoint.constants.OAuth2ProviderRESTEndpointConstants;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.PermissionCheckerFactoryUtil;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.util.MapUtil;

import java.util.List;
import java.util.Objects;

import javax.ws.rs.core.MultivaluedMap;

import org.apache.cxf.rs.security.oauth2.common.Client;
import org.apache.cxf.rs.security.oauth2.common.ServerAccessToken;
import org.apache.cxf.rs.security.oauth2.provider.AccessTokenGrantHandler;
import org.apache.cxf.rs.security.oauth2.provider.OAuthServiceException;

import org.osgi.service.component.annotations.Reference;

/**
 * @author Tomas Polesovsky
 */
public abstract class BaseAccessTokenGrantHandler
	implements AccessTokenGrantHandler {

	@Override
	public ServerAccessToken createAccessToken(
			Client client, MultivaluedMap<String, String> params)
		throws OAuthServiceException {

		if (!isGrantHandlerEnabled()) {
			throw new OAuthServiceException("Grant handler is not enabled");
		}

		if (!hasPermission(client, params)) {
			throw new OAuthServiceException(
				"User does not have permission to create token");
		}

		AccessTokenGrantHandler accessTokenGrantHandler =
			getAccessTokenGrantHandler();

		return accessTokenGrantHandler.createAccessToken(client, params);
	}

	@Override
	public List<String> getSupportedGrantTypes() {
		AccessTokenGrantHandler accessTokenGrantHandler =
			getAccessTokenGrantHandler();

		return accessTokenGrantHandler.getSupportedGrantTypes();
	}

	protected boolean clientsMatch(Client client1, Client client2) {
		if (!Objects.equals(client1.getClientId(), client2.getClientId())) {
			return false;
		}

		String companyId1 = MapUtil.getString(
			client1.getProperties(),
			OAuth2ProviderRESTEndpointConstants.PROPERTY_KEY_COMPANY_ID);
		String companyId2 = MapUtil.getString(
			client2.getProperties(),
			OAuth2ProviderRESTEndpointConstants.PROPERTY_KEY_COMPANY_ID);

		if (Objects.equals(companyId1, companyId2)) {
			return true;
		}

		return false;
	}

	protected abstract AccessTokenGrantHandler getAccessTokenGrantHandler();

	protected boolean hasCreateTokenPermission(
		long userId, OAuth2Application oAuth2Application) {

		PermissionChecker permissionChecker;

		try {
			User user = userLocalService.getUserById(userId);

			permissionChecker = PermissionCheckerFactoryUtil.create(user);
		}
		catch (Exception e) {
			if (_log.isDebugEnabled()) {
				_log.debug(
					"Unable to create permission checker for user " + userId);
			}

			return false;
		}

		try {
			if (modelResourcePermission.contains(
					permissionChecker, oAuth2Application,
					OAuth2ProviderActionKeys.ACTION_CREATE_TOKEN)) {

				return true;
			}
		}
		catch (PortalException pe) {
			if (_log.isDebugEnabled()) {
				_log.debug(
					"Unable to check permissions for application " +
						oAuth2Application,
					pe);
			}
		}

		if (_log.isDebugEnabled()) {
			StringBundler sb = new StringBundler(5);

			sb.append("User ");
			sb.append(userId);
			sb.append(" does not have permission to create access token for ");
			sb.append("client ");
			sb.append(oAuth2Application.getClientId());

			_log.debug(sb.toString());
		}

		return false;
	}

	protected abstract boolean hasPermission(
		Client client, MultivaluedMap<String, String> params);

	protected abstract boolean isGrantHandlerEnabled();

	@Reference(
		target = "(model.class.name=com.liferay.oauth2.provider.model.OAuth2Application)"
	)
	protected ModelResourcePermission<OAuth2Application>
		modelResourcePermission;

	@Reference
	protected UserLocalService userLocalService;

	private static final Log _log = LogFactoryUtil.getLog(
		BaseAccessTokenGrantHandler.class);

}