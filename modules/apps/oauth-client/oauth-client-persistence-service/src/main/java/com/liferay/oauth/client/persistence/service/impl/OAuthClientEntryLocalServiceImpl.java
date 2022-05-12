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
import com.liferay.oauth.client.persistence.model.OAuthClientEntry;
import com.liferay.oauth.client.persistence.service.OAuthClientAuthServerLocalService;
import com.liferay.oauth.client.persistence.service.base.OAuthClientEntryLocalServiceBaseImpl;
import com.liferay.portal.aop.AopService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.GroupConstants;
import com.liferay.portal.kernel.model.ResourceConstants;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.ResourceLocalService;
import com.liferay.portal.kernel.service.UserLocalService;

import com.nimbusds.oauth2.sdk.ParseException;
import com.nimbusds.oauth2.sdk.client.ClientInformation;
import com.nimbusds.oauth2.sdk.id.ClientID;
import com.nimbusds.oauth2.sdk.util.JSONObjectUtils;
import com.nimbusds.openid.connect.sdk.rp.OIDCClientInformation;

import java.util.ArrayList;
import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Arthur Chan
 */
@Component(
	property = "model.class.name=com.liferay.oauth.client.persistence.model.OAuthClientEntry",
	service = AopService.class
)
public class OAuthClientEntryLocalServiceImpl
	extends OAuthClientEntryLocalServiceBaseImpl {

	@Override
	public OAuthClientEntry addOAuthClientEntry(
			long userId, String authServerIssuer, String infoJSON,
			String requestParametersJSON)
		throws PortalException {

		OAuthClientEntry oAuthClientEntry = oAuthClientEntryPersistence.create(
			counterLocalService.increment());

		User user = _userLocalService.getUser(userId);

		oAuthClientEntry.setCompanyId(user.getCompanyId());

		oAuthClientEntry.setUserId(userId);
		oAuthClientEntry.setUserName(user.getFullName());
		oAuthClientEntry.setAuthServerIssuer(authServerIssuer);
		oAuthClientEntry.setClientId(
			_validateAndGetClientId(
				user.getCompanyId(), authServerIssuer, infoJSON,
				requestParametersJSON, true));
		oAuthClientEntry.setInfoJSON(infoJSON);
		oAuthClientEntry.setRequestParametersJSON(requestParametersJSON);

		_resourceLocalService.addResources(
			oAuthClientEntry.getCompanyId(),
			GroupConstants.DEFAULT_LIVE_GROUP_ID, oAuthClientEntry.getUserId(),
			OAuthClientEntry.class.getName(),
			oAuthClientEntry.getOAuthClientEntryId(), false, false, false);

		return oAuthClientEntryPersistence.update(oAuthClientEntry);
	}

	@Override
	public OAuthClientEntry deleteOAuthClientEntry(long oAuthClientEntryId)
		throws PortalException {

		OAuthClientEntry oAuthClientEntry =
			oAuthClientEntryPersistence.findByPrimaryKey(oAuthClientEntryId);

		return deleteOAuthClientEntry(oAuthClientEntry);
	}

	@Override
	public OAuthClientEntry deleteOAuthClientEntry(
			long companyId, String authServerIssuer, String clientID)
		throws PortalException {

		OAuthClientEntry oAuthClientEntry =
			oAuthClientEntryPersistence.findByC_A_C(
				companyId, authServerIssuer, clientID);

		return deleteOAuthClientEntry(oAuthClientEntry);
	}

	@Override
	public OAuthClientEntry deleteOAuthClientEntry(
		OAuthClientEntry oAuthClientEntry) {

		try {
			_resourceLocalService.deleteResource(
				oAuthClientEntry.getCompanyId(),
				OAuthClientEntry.class.getName(),
				ResourceConstants.SCOPE_INDIVIDUAL,
				oAuthClientEntry.getOAuthClientEntryId());

			return oAuthClientEntryPersistence.remove(oAuthClientEntry);
		}
		catch (Exception exception) {
			_log.error(exception);

			return null;
		}
	}

	@Override
	public OAuthClientEntry fetchOAuthClientEntry(
		long companyId, String authServerIssuer, String clientID) {

		return oAuthClientEntryPersistence.fetchByC_A_C(
			companyId, authServerIssuer, clientID);
	}

	@Override
	public List<OAuthClientEntry> getCompanyOAuthClientEntries(long companyId) {
		return oAuthClientEntryPersistence.findByCompanyId(companyId);
	}

	@Override
	public List<OAuthClientEntry> getOAuthClientEntries(
		long companyId, String authServerIssuer) {

		return oAuthClientEntryPersistence.findByC_A(
			companyId, authServerIssuer);
	}

	@Override
	public List<OAuthClientEntry> getOAuthClientEntriesByAuthServerType(
		long companyId, String authServerType) {

		List<OAuthClientAuthServer> oAuthClientAuthServers =
			_oAuthClientAuthServerLocalService.getOAuthClientAuthServers(
				companyId, authServerType);

		List<OAuthClientEntry> oAuthClientEntries = new ArrayList<>();

		for (OAuthClientAuthServer oAuthClientAuthServer :
				oAuthClientAuthServers) {

			oAuthClientEntries.addAll(
				oAuthClientEntryLocalService.getOAuthClientEntries(
					companyId, oAuthClientAuthServer.getIssuer()));
		}

		return oAuthClientEntries;
	}

	@Override
	public OAuthClientEntry getOAuthClientEntry(
			long companyId, String authServerIssuer, String clientID)
		throws PortalException {

		return oAuthClientEntryPersistence.findByC_A_C(
			companyId, authServerIssuer, clientID);
	}

	@Override
	public List<OAuthClientEntry> getUserOAuthClientEntries(long userId) {
		return oAuthClientEntryPersistence.findByUserId(userId);
	}

	@Override
	public OAuthClientEntry updateOAuthClientEntry(
			long oAuthClientEntryId, String authServerIssuer, String infoJSON,
			String requestParametersJSON)
		throws PortalException {

		OAuthClientEntry oAuthClientEntry =
			oAuthClientEntryLocalService.getOAuthClientEntry(
				oAuthClientEntryId);

		String clientID = _validateAndGetClientId(
			oAuthClientEntry.getCompanyId(), authServerIssuer, infoJSON,
			requestParametersJSON, false);

		oAuthClientEntry.setAuthServerIssuer(authServerIssuer);
		oAuthClientEntry.setClientId(clientID);
		oAuthClientEntry.setInfoJSON(infoJSON);
		oAuthClientEntry.setRequestParamsJSON(requestParametersJSON);

		return oAuthClientEntryPersistence.update(oAuthClientEntry);
	}

	private String _validateAndGetClientId(
			long companyId, String authServerIssuer, String infoJSON,
			String requestParametersJSON, boolean add)
		throws PortalException {

		OAuthClientAuthServer oAuthClientAuthServer =
			_oAuthClientAuthServerLocalService.fetchOAuthClientAuthServer(
				companyId, authServerIssuer);

		if (oAuthClientAuthServer == null) {
			throw new PortalException(
				"No such OAuth Client Authorization server issuer: " +
					authServerIssuer);
		}

		try {
			JSONObjectUtils.parse(requestParametersJSON);
		}
		catch (ParseException parseException) {
			throw new PortalException(parseException);
		}

		String type = oAuthClientAuthServer.getType();

		if (type.equals("oauth-authorization-server")) {
			try {
				ClientInformation clientInformation = ClientInformation.parse(
					JSONObjectUtils.parse(infoJSON));

				ClientID clientID = clientInformation.getID();

				if (add) {
					OAuthClientEntry oAuthClientEntry =
						oAuthClientEntryPersistence.fetchByC_A_C(
							companyId, authServerIssuer, clientID.getValue());

					if (oAuthClientEntry != null) {
						throw new PortalException(
							"There is an existing OAuth Client Entry: " +
								clientID.getValue());
					}
				}

				return clientID.getValue();
			}
			catch (ParseException parseException) {
				throw new PortalException(parseException);
			}
		}
		else if (type.equals("openid-configuration")) {
			try {
				OIDCClientInformation oidcClientInformation =
					OIDCClientInformation.parse(
						JSONObjectUtils.parse(infoJSON));

				ClientID clientID = oidcClientInformation.getID();

				if (add) {
					OAuthClientEntry oAuthClientEntry =
						oAuthClientEntryPersistence.fetchByC_A_C(
							companyId, authServerIssuer, clientID.getValue());

					if (oAuthClientEntry != null) {
						throw new PortalException(
							"There is an existing OAuth Client Entry: " +
								clientID.getValue());
					}
				}

				return clientID.getValue();
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
		OAuthClientEntryLocalServiceImpl.class);

	@Reference
	private OAuthClientAuthServerLocalService
		_oAuthClientAuthServerLocalService;

	@Reference
	private ResourceLocalService _resourceLocalService;

	@Reference
	private UserLocalService _userLocalService;

}