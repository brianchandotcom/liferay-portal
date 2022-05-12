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

import com.liferay.oauth.client.persistence.model.OAuthClientAuthServer;
import com.liferay.oauth.client.persistence.service.base.OAuthClientAuthServerLocalServiceBaseImpl;
import com.liferay.portal.aop.AopService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.GroupConstants;
import com.liferay.portal.kernel.model.ResourceConstants;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.ResourceLocalService;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.util.Validator;

import com.nimbusds.oauth2.sdk.ParseException;
import com.nimbusds.oauth2.sdk.as.AuthorizationServerMetadata;
import com.nimbusds.oauth2.sdk.id.Issuer;
import com.nimbusds.openid.connect.sdk.op.OIDCProviderMetadata;

import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Arthur Chan
 */
@Component(
	property = "model.class.name=com.liferay.oauth.client.persistence.model.OAuthClientAuthServer",
	service = AopService.class
)
public class OAuthClientAuthServerLocalServiceImpl
	extends OAuthClientAuthServerLocalServiceBaseImpl {

	@Override
	public OAuthClientAuthServer addOAuthClientAuthServer(
			long userId, String discoveryEndpoint, String metadataJSON,
			String type)
		throws PortalException {

		OAuthClientAuthServer oAuthClientAuthServer =
			oAuthClientAuthServerPersistence.create(
				counterLocalService.increment());

		User user = _userLocalService.getUser(userId);

		oAuthClientAuthServer.setCompanyId(user.getCompanyId());

		oAuthClientAuthServer.setUserId(user.getUserId());
		oAuthClientAuthServer.setUserName(user.getFullName());

		oAuthClientAuthServer.setDiscoveryEndpoint(discoveryEndpoint);
		oAuthClientAuthServer.setIssuer(
			_validateAndGetIssuer(user.getCompanyId(), metadataJSON, type));
		oAuthClientAuthServer.setMetadataJSON(metadataJSON);
		oAuthClientAuthServer.setType(type);

		oAuthClientAuthServer = oAuthClientAuthServerPersistence.update(
			oAuthClientAuthServer);

		_resourceLocalService.addResources(
			oAuthClientAuthServer.getCompanyId(),
			GroupConstants.DEFAULT_LIVE_GROUP_ID,
			oAuthClientAuthServer.getUserId(),
			OAuthClientAuthServer.class.getName(),
			oAuthClientAuthServer.getOAuthClientAuthServerId(), false, false,
			false);

		return oAuthClientAuthServer;
	}

	@Override
	public OAuthClientAuthServer deleteOAuthClientAuthServer(
			long oAuthClientAuthServerId)
		throws PortalException {

		OAuthClientAuthServer oAuthClientAuthServer =
			oAuthClientAuthServerPersistence.findByPrimaryKey(
				oAuthClientAuthServerId);

		return deleteOAuthClientAuthServer(oAuthClientAuthServer);
	}

	@Override
	public OAuthClientAuthServer deleteOAuthClientAuthServer(
			long companyId, String issuer)
		throws PortalException {

		OAuthClientAuthServer oAuthClientAuthServer =
			oAuthClientAuthServerPersistence.findByC_I(companyId, issuer);

		return deleteOAuthClientAuthServer(oAuthClientAuthServer);
	}

	@Override
	public OAuthClientAuthServer deleteOAuthClientAuthServer(
		OAuthClientAuthServer oAuthClientAuthServer) {

		try {
			_resourceLocalService.deleteResource(
				oAuthClientAuthServer.getCompanyId(),
				OAuthClientAuthServer.class.getName(),
				ResourceConstants.SCOPE_INDIVIDUAL,
				oAuthClientAuthServer.getOAuthClientAuthServerId());

			return oAuthClientAuthServerPersistence.remove(
				oAuthClientAuthServer);
		}
		catch (Exception exception) {
			_log.error(exception);

			return null;
		}
	}

	@Override
	public OAuthClientAuthServer fetchOAuthClientAuthServer(
		long companyId, String issuer) {

		return oAuthClientAuthServerPersistence.fetchByC_I(companyId, issuer);
	}

	@Override
	public List<OAuthClientAuthServer> getCompanyOAuthClientAuthServers(
		long companyId) {

		return oAuthClientAuthServerPersistence.findByCompanyId(companyId);
	}

	@Override
	public List<OAuthClientAuthServer> getCompanyOAuthClientAuthServers(
		long companyId, int start, int end) {

		return oAuthClientAuthServerPersistence.findByCompanyId(
			companyId, start, end);
	}

	@Override
	public OAuthClientAuthServer getOAuthClientAuthServer(
			long companyId, String issuer)
		throws PortalException {

		return oAuthClientAuthServerPersistence.findByC_I(companyId, issuer);
	}

	@Override
	public List<OAuthClientAuthServer> getOAuthClientAuthServers(
		long companyId, String type) {

		return oAuthClientAuthServerPersistence.findByC_T(companyId, type);
	}

	@Override
	public List<OAuthClientAuthServer> getUserOAuthClientAuthServers(
		long userId) {

		return oAuthClientAuthServerPersistence.findByUserId(userId);
	}

	@Override
	public List<OAuthClientAuthServer> getUserOAuthClientAuthServers(
		long userId, int start, int end) {

		return oAuthClientAuthServerPersistence.findByUserId(
			userId, start, end);
	}

	@Override
	public OAuthClientAuthServer updateOAuthClientAuthServer(
			long oAuthClientAuthServerId, String discoveryEndpoint,
			String metadataJSON, String type)
		throws PortalException {

		OAuthClientAuthServer oAuthClientAuthServer =
			oAuthClientAuthServerLocalService.getOAuthClientAuthServer(
				oAuthClientAuthServerId);

		oAuthClientAuthServer.setDiscoveryEndpoint(discoveryEndpoint);
		oAuthClientAuthServer.setIssuer(
			_validateAndGetIssuer(metadataJSON, type));
		oAuthClientAuthServer.setMetadataJSON(metadataJSON);
		oAuthClientAuthServer.setType(type);

		return oAuthClientAuthServerPersistence.update(oAuthClientAuthServer);
	}

	private String _validateAndGetIssuer(
			long companyId, String metadataJSON, String type)
		throws PortalException {

		String issuer = _validateAndGetIssuer(metadataJSON, type);

		if (oAuthClientAuthServerPersistence.fetchByC_I(companyId, issuer) !=
				null) {

			throw new PortalException(
				"There is an existing authorization server with issuer: " +
					issuer);
		}

		return issuer;
	}

	private String _validateAndGetIssuer(String metadataJSON, String type)
		throws PortalException {

		if (Validator.isNull(type)) {
			throw new PortalException("Unspecified Metadata Type");
		}

		if (type.equals("oauth-authorization-server")) {
			try {
				AuthorizationServerMetadata authorizationServerMetadata =
					AuthorizationServerMetadata.parse(metadataJSON);

				Issuer issuer = authorizationServerMetadata.getIssuer();

				return issuer.getValue();
			}
			catch (ParseException parseException) {
				throw new PortalException(parseException);
			}
		}
		else if (type.equals("openid-configuration")) {
			try {
				OIDCProviderMetadata oidcProviderMetadata =
					OIDCProviderMetadata.parse(metadataJSON);

				Issuer issuer = oidcProviderMetadata.getIssuer();

				return issuer.getValue();
			}
			catch (ParseException parseException) {
				throw new PortalException(parseException);
			}
		}
		else {
			throw new PortalException("Unrecognized Metadata Type");
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		OAuthClientAuthServerLocalServiceImpl.class);

	@Reference
	private ResourceLocalService _resourceLocalService;

	@Reference
	private UserLocalService _userLocalService;

}