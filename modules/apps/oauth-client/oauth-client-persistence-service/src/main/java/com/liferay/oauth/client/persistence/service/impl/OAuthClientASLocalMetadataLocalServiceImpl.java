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

import com.liferay.oauth.client.persistence.exception.OAuthClientASLocalMetadataJSONException;
import com.liferay.oauth.client.persistence.model.OAuthClientASLocalMetadata;
import com.liferay.oauth.client.persistence.service.base.OAuthClientASLocalMetadataLocalServiceBaseImpl;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.aop.AopService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.GroupConstants;
import com.liferay.portal.kernel.model.ResourceConstants;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.ResourceLocalService;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.util.Base64;

import com.nimbusds.oauth2.sdk.ParseException;
import com.nimbusds.oauth2.sdk.as.AuthorizationServerMetadata;
import com.nimbusds.oauth2.sdk.util.JSONObjectUtils;
import com.nimbusds.openid.connect.sdk.op.OIDCProviderMetadata;

import java.net.URI;

import java.security.MessageDigest;

import java.util.List;

import net.minidev.json.JSONObject;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Arthur Chan
 */
@Component(
	property = "model.class.name=com.liferay.oauth.client.persistence.model.OAuthClientASLocalMetadata",
	service = AopService.class
)
public class OAuthClientASLocalMetadataLocalServiceImpl
	extends OAuthClientASLocalMetadataLocalServiceBaseImpl {

	@Override
	public OAuthClientASLocalMetadata addOAuthClientASLocalMetadata(
			long userId, String metadataJSON, String wellKnownURISuffix)
		throws PortalException {

		JSONObject metadataJSONObject = _getMetadataJSONObject(metadataJSON);

		_validateMetadataJSON(metadataJSONObject, wellKnownURISuffix);

		User user = _userLocalService.getUser(userId);

		OAuthClientASLocalMetadata oAuthClientASLocalMetadata =
			oAuthClientASLocalMetadataPersistence.create(
				counterLocalService.increment());

		oAuthClientASLocalMetadata.setCompanyId(user.getCompanyId());
		oAuthClientASLocalMetadata.setUserId(user.getUserId());
		oAuthClientASLocalMetadata.setUserName(user.getFullName());
		oAuthClientASLocalMetadata.setLocalWellKnownURI(
			_generateLocalWellKnownURI(
				metadataJSONObject.getAsString("issuer"), wellKnownURISuffix,
				oAuthClientASLocalMetadata.getOAuthClientASLocalMetadataId()));
		oAuthClientASLocalMetadata.setMetadataJSON(metadataJSON);

		oAuthClientASLocalMetadata =
			oAuthClientASLocalMetadataPersistence.update(
				oAuthClientASLocalMetadata);

		_resourceLocalService.addResources(
			oAuthClientASLocalMetadata.getCompanyId(),
			GroupConstants.DEFAULT_LIVE_GROUP_ID,
			oAuthClientASLocalMetadata.getUserId(),
			OAuthClientASLocalMetadata.class.getName(),
			oAuthClientASLocalMetadata.getOAuthClientASLocalMetadataId(), false,
			false, false);

		return oAuthClientASLocalMetadata;
	}

	@Override
	public OAuthClientASLocalMetadata deleteOAuthClientASLocalMetadata(
			long oAuthClientASLocalMetadataId)
		throws PortalException {

		OAuthClientASLocalMetadata oAuthClientASLocalMetadata =
			oAuthClientASLocalMetadataPersistence.findByPrimaryKey(
				oAuthClientASLocalMetadataId);

		return deleteOAuthClientASLocalMetadata(oAuthClientASLocalMetadata);
	}

	@Override
	public OAuthClientASLocalMetadata deleteOAuthClientASLocalMetadata(
			long companyId, String localWellKnownURI)
		throws PortalException {

		OAuthClientASLocalMetadata oAuthClientASLocalMetadata =
			oAuthClientASLocalMetadataPersistence.findByC_L(
				companyId, localWellKnownURI);

		return deleteOAuthClientASLocalMetadata(oAuthClientASLocalMetadata);
	}

	@Override
	public OAuthClientASLocalMetadata deleteOAuthClientASLocalMetadata(
			OAuthClientASLocalMetadata oAuthClientASLocalMetadata)
		throws PortalException {

		oAuthClientASLocalMetadata =
			oAuthClientASLocalMetadataPersistence.remove(
				oAuthClientASLocalMetadata);

		_resourceLocalService.deleteResource(
			oAuthClientASLocalMetadata.getCompanyId(),
			OAuthClientASLocalMetadata.class.getName(),
			ResourceConstants.SCOPE_INDIVIDUAL,
			oAuthClientASLocalMetadata.getOAuthClientASLocalMetadataId());

		return oAuthClientASLocalMetadata;
	}

	@Override
	public OAuthClientASLocalMetadata fetchOAuthClientASLocalMetadata(
		long companyId, String localWellKnownURI) {

		return oAuthClientASLocalMetadataPersistence.fetchByC_L(
			companyId, localWellKnownURI);
	}

	@Override
	public List<OAuthClientASLocalMetadata>
		getCompanyOAuthClientASLocalMetadata(long companyId) {

		return oAuthClientASLocalMetadataPersistence.findByCompanyId(companyId);
	}

	@Override
	public List<OAuthClientASLocalMetadata>
		getCompanyOAuthClientASLocalMetadata(
			long companyId, int start, int end) {

		return oAuthClientASLocalMetadataPersistence.findByCompanyId(
			companyId, start, end);
	}

	@Override
	public OAuthClientASLocalMetadata getOAuthClientASLocalMetadata(
			long companyId, String localWellKnownURI)
		throws PortalException {

		return oAuthClientASLocalMetadataPersistence.findByC_L(
			companyId, localWellKnownURI);
	}

	@Override
	public List<OAuthClientASLocalMetadata> getUserOAuthClientASLocalMetadata(
		long userId) {

		return oAuthClientASLocalMetadataPersistence.findByUserId(userId);
	}

	@Override
	public List<OAuthClientASLocalMetadata> getUserOAuthClientASLocalMetadata(
		long userId, int start, int end) {

		return oAuthClientASLocalMetadataPersistence.findByUserId(
			userId, start, end);
	}

	@Override
	public OAuthClientASLocalMetadata updateOAuthClientASLocalMetadata(
			long oAuthClientASLocalMetadataId, String metadataJSON,
			String wellKnownURISuffix)
		throws PortalException {

		JSONObject metadataJSONObject = _getMetadataJSONObject(metadataJSON);

		_validateMetadataJSON(metadataJSONObject, wellKnownURISuffix);

		OAuthClientASLocalMetadata oAuthClientASLocalMetadata =
			oAuthClientASLocalMetadataLocalService.
				getOAuthClientASLocalMetadata(oAuthClientASLocalMetadataId);

		oAuthClientASLocalMetadata.setMetadataJSON(metadataJSON);

		JSONObject currentMetadataJSONObject = _getMetadataJSONObject(
			oAuthClientASLocalMetadata.getMetadataJSON());

		String currentIssuer = currentMetadataJSONObject.getAsString("issuer");

		String currentLocalWellKnownURI =
			oAuthClientASLocalMetadata.getLocalWellKnownURI();

		if (!currentIssuer.equals(metadataJSONObject.getAsString("issuer")) ||
			!currentLocalWellKnownURI.contains(wellKnownURISuffix)) {

			oAuthClientASLocalMetadata.setLocalWellKnownURI(
				_generateLocalWellKnownURI(
					metadataJSONObject.getAsString("issuer"),
					wellKnownURISuffix,
					oAuthClientASLocalMetadata.
						getOAuthClientASLocalMetadataId()));
		}

		return oAuthClientASLocalMetadataPersistence.update(
			oAuthClientASLocalMetadata);
	}

	/**
	 * Manually inputted Authorization Server metadata, aka local metadata, does
	 * not have wellKnownURI as unique identifier, thus generating one for
	 * internal identification.
	 * Generation logic follows RFC8414#Section3
	 *
	 * @param issuer e.g. https://authserver.com/issuer01
	 * @param wellKnownURISuffix e.g. openid-configuration
	 * @param id unique id
	 *
	 * @return a generated well-known URI as unique identifier for this metadata
	 * used for internal identification.
	 *         e.g. https://authserver.com/.well-known/openid-configuration/issuer01/Base64(MD5(id))/local
	 */
	private String _generateLocalWellKnownURI(
			String issuer, String wellKnownURISuffix, long id)
		throws PortalException {

		try {
			MessageDigest messageDigest = MessageDigest.getInstance("MD5");

			URI issuerURI = URI.create(issuer);

			return StringBundler.concat(
				issuerURI.getScheme(), "://", issuerURI.getAuthority(),
				"/.well-known/", wellKnownURISuffix, issuerURI.getPath(), '/',
				Base64.encode(messageDigest.digest(_getBytes(id))), "/local");
		}
		catch (Exception exception) {
			throw new PortalException(exception);
		}
	}

	private byte[] _getBytes(long data) {
		return new byte[] {
			(byte)((data >> 56) & 0xff), (byte)((data >> 48) & 0xff),
			(byte)((data >> 40) & 0xff), (byte)((data >> 32) & 0xff),
			(byte)((data >> 24) & 0xff), (byte)((data >> 16) & 0xff),
			(byte)((data >> 8) & 0xff), (byte)((data >> 0) & 0xff)
		};
	}

	private JSONObject _getMetadataJSONObject(String metadataJSON)
		throws PortalException {

		try {
			return JSONObjectUtils.parse(metadataJSON);
		}
		catch (ParseException parseException) {
			throw new OAuthClientASLocalMetadataJSONException(parseException);
		}
	}

	private void _validateMetadataJSON(
			JSONObject metadataJSONObject, String wellKnownURISuffix)
		throws PortalException {

		if (wellKnownURISuffix.equals("openid-configuration")) {
			try {
				OIDCProviderMetadata.parse(metadataJSONObject);
			}
			catch (ParseException parseException) {
				throw new OAuthClientASLocalMetadataJSONException(
					parseException);
			}
		}
		else {
			try {
				AuthorizationServerMetadata.parse(metadataJSONObject);
			}
			catch (ParseException parseException) {
				throw new OAuthClientASLocalMetadataJSONException(
					parseException);
			}
		}
	}

	@Reference
	private ResourceLocalService _resourceLocalService;

	@Reference
	private UserLocalService _userLocalService;

}