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

import com.liferay.oauth.client.persistence.exception.DuplicateOAuthClientEntryException;
import com.liferay.oauth.client.persistence.exception.OAuthClientAuthServerTypeException;
import com.liferay.oauth.client.persistence.exception.OAuthClientEntryParametersJSONException;
import com.liferay.oauth.client.persistence.model.OAuthClientAuthServer;
import com.liferay.oauth.client.persistence.model.OAuthClientAuthServerTable;
import com.liferay.oauth.client.persistence.model.OAuthClientEntry;
import com.liferay.oauth.client.persistence.model.OAuthClientEntryTable;
import com.liferay.oauth.client.persistence.service.base.OAuthClientEntryLocalServiceBaseImpl;
import com.liferay.oauth.client.persistence.service.persistence.OAuthClientAuthServerPersistence;
import com.liferay.petra.sql.dsl.DSLQueryFactoryUtil;
import com.liferay.portal.aop.AopService;
import com.liferay.portal.kernel.exception.PortalException;
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
			String parametersJSON)
		throws PortalException {

		OAuthClientEntry oAuthClientEntry = oAuthClientEntryPersistence.create(
			counterLocalService.increment());

		User user = _userLocalService.getUser(userId);

		oAuthClientEntry.setCompanyId(user.getCompanyId());
		oAuthClientEntry.setUserId(user.getUserId());
		oAuthClientEntry.setUserName(user.getFullName());

		oAuthClientEntry.setAuthServerIssuer(authServerIssuer);
		oAuthClientEntry.setClientId(
			_validateAndGetClientId(
				user.getCompanyId(), authServerIssuer, infoJSON, parametersJSON,
				true));
		oAuthClientEntry.setInfoJSON(infoJSON);
		oAuthClientEntry.setParametersJSON(parametersJSON);

		oAuthClientEntry = oAuthClientEntryPersistence.update(oAuthClientEntry);

		_resourceLocalService.addResources(
			oAuthClientEntry.getCompanyId(),
			GroupConstants.DEFAULT_LIVE_GROUP_ID, oAuthClientEntry.getUserId(),
			OAuthClientEntry.class.getName(),
			oAuthClientEntry.getOAuthClientEntryId(), false, false, false);

		return oAuthClientEntry;
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
			long companyId, String authServerIssuer, String clientId)
		throws PortalException {

		OAuthClientEntry oAuthClientEntry =
			oAuthClientEntryPersistence.findByC_A_C(
				companyId, authServerIssuer, clientId);

		return deleteOAuthClientEntry(oAuthClientEntry);
	}

	@Override
	public OAuthClientEntry deleteOAuthClientEntry(
			OAuthClientEntry oAuthClientEntry)
		throws PortalException {

		oAuthClientEntry = oAuthClientEntryPersistence.remove(oAuthClientEntry);

		_resourceLocalService.deleteResource(
			oAuthClientEntry.getCompanyId(), OAuthClientEntry.class.getName(),
			ResourceConstants.SCOPE_INDIVIDUAL,
			oAuthClientEntry.getOAuthClientEntryId());

		return oAuthClientEntry;
	}

	@Override
	public OAuthClientEntry fetchOAuthClientEntry(
		long companyId, String authServerIssuer, String clientId) {

		return oAuthClientEntryPersistence.fetchByC_A_C(
			companyId, authServerIssuer, clientId);
	}

	@Override
	public List<OAuthClientEntry> getAuthServerIssuerOAuthClientEntries(
		long companyId, String authServerIssuer) {

		return oAuthClientEntryPersistence.findByC_A(
			companyId, authServerIssuer);
	}

	@Override
	public List<OAuthClientEntry> getAuthServerTypeOAuthClientEntries(
		long companyId, String authServerType) {

		return oAuthClientEntryPersistence.dslQuery(
			DSLQueryFactoryUtil.select(
				OAuthClientEntryTable.INSTANCE
			).from(
				OAuthClientEntryTable.INSTANCE
			).where(
				OAuthClientEntryTable.INSTANCE.authServerIssuer.in(
					DSLQueryFactoryUtil.select(
						OAuthClientAuthServerTable.INSTANCE.issuer
					).from(
						OAuthClientAuthServerTable.INSTANCE
					).where(
						OAuthClientAuthServerTable.INSTANCE.companyId.eq(
							companyId
						).and(
							OAuthClientAuthServerTable.INSTANCE.type.eq(
								authServerType)
						)
					))
			));
	}

	@Override
	public List<OAuthClientEntry> getCompanyOAuthClientEntries(long companyId) {
		return oAuthClientEntryPersistence.findByCompanyId(companyId);
	}

	@Override
	public OAuthClientEntry getOAuthClientEntry(
			long companyId, String authServerIssuer, String clientId)
		throws PortalException {

		return oAuthClientEntryPersistence.findByC_A_C(
			companyId, authServerIssuer, clientId);
	}

	@Override
	public List<OAuthClientEntry> getUserOAuthClientEntries(long userId) {
		return oAuthClientEntryPersistence.findByUserId(userId);
	}

	@Override
	public OAuthClientEntry updateOAuthClientEntry(
			long oAuthClientEntryId, String authServerIssuer, String infoJSON,
			String parametersJSON)
		throws PortalException {

		OAuthClientEntry oAuthClientEntry =
			oAuthClientEntryLocalService.getOAuthClientEntry(
				oAuthClientEntryId);

		oAuthClientEntry.setAuthServerIssuer(authServerIssuer);
		oAuthClientEntry.setClientId(
			_validateAndGetClientId(
				oAuthClientEntry.getCompanyId(), authServerIssuer, infoJSON,
				parametersJSON, false));
		oAuthClientEntry.setInfoJSON(infoJSON);
		oAuthClientEntry.setParametersJSON(parametersJSON);

		return oAuthClientEntryPersistence.update(oAuthClientEntry);
	}

	private String _validateAndGetClientId(
			long companyId, String authServerIssuer, String infoJSON,
			String parametersJSON, boolean add)
		throws PortalException {

		try {
			JSONObjectUtils.parse(parametersJSON);
		}
		catch (ParseException parseException) {
			throw new OAuthClientEntryParametersJSONException(parseException);
		}

		OAuthClientAuthServer oAuthClientAuthServer =
			_oAuthClientAuthServerPersistence.findByC_I(
				companyId, authServerIssuer);

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
						throw new DuplicateOAuthClientEntryException(
							"Client ID " + clientID.getValue());
					}
				}

				return clientID.getValue();
			}
			catch (ParseException parseException) {
				throw new OAuthClientAuthServerTypeException(
					"Unable to parse type: " + type, parseException);
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
						throw new DuplicateOAuthClientEntryException(
							"Client ID " + clientID.getValue());
					}
				}

				return clientID.getValue();
			}
			catch (ParseException parseException) {
				throw new OAuthClientAuthServerTypeException(
					"Unable to parse type: " + type, parseException);
			}
		}
		else {
			throw new OAuthClientAuthServerTypeException(
				"Invalid type: " + type);
		}
	}

	@Reference
	private OAuthClientAuthServerPersistence _oAuthClientAuthServerPersistence;

	@Reference
	private ResourceLocalService _resourceLocalService;

	@Reference
	private UserLocalService _userLocalService;

}