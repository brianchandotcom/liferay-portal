/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.oauth.client.persistence.service.impl;

import com.liferay.oauth.client.persistence.exception.DuplicateOAuthClientASIssuerException;
import com.liferay.oauth.client.persistence.exception.DuplicateOAuthClientASLocalMetadataException;
import com.liferay.oauth.client.persistence.exception.OAuthClientASLocalMetadataJSONException;
import com.liferay.oauth.client.persistence.exception.OAuthClientASLocalMetadataLocalWellKnownURIException;
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
import com.liferay.portal.kernel.util.OrderByComparator;

import com.nimbusds.oauth2.sdk.GrantType;
import com.nimbusds.oauth2.sdk.Scope;
import com.nimbusds.oauth2.sdk.as.AuthorizationServerMetadata;
import com.nimbusds.oauth2.sdk.id.Issuer;
import com.nimbusds.openid.connect.sdk.SubjectType;
import com.nimbusds.openid.connect.sdk.op.OIDCProviderMetadata;

import java.net.URI;

import java.security.MessageDigest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
			long userId, String authorizationEndpoint, Boolean enabled,
			String issuerString, String jwksUri, String[] supportedGrantTypes,
			String[] supportedScopes, String[] supportedSubjectTypes,
			String tokenEndpointString, String userinfoEndpoint)
		throws PortalException {

		User user = _userLocalService.getUser(userId);

		OAuthClientASLocalMetadata oAuthClientASLocalMetadata =
			oAuthClientASLocalMetadataPersistence.fetchByC_I(
				user.getCompanyId(), issuerString);

		if (oAuthClientASLocalMetadata != null) {
			throw new DuplicateOAuthClientASIssuerException();
		}

		String localWellKnownURIOIC = _generateLocalWellKnownURI(
			issuerString, tokenEndpointString, "openid-configuration");

		oAuthClientASLocalMetadata =
			oAuthClientASLocalMetadataPersistence.fetchByLocalWellKnownURI(
				localWellKnownURIOIC);

		if (oAuthClientASLocalMetadata != null) {
			throw new DuplicateOAuthClientASLocalMetadataException();
		}

		String metadataJSONOIC = _buildOpenIdConfigurationJSON(
			authorizationEndpoint, issuerString, jwksUri, supportedGrantTypes,
			supportedScopes, supportedSubjectTypes, tokenEndpointString,
			userinfoEndpoint);

		String metadataJSONOAS = _buildAuthorizationServerJSON(
			authorizationEndpoint, issuerString, jwksUri, supportedScopes,
			supportedGrantTypes, tokenEndpointString);

		oAuthClientASLocalMetadata =
			oAuthClientASLocalMetadataPersistence.create(
				counterLocalService.increment());

		oAuthClientASLocalMetadata.setCompanyId(user.getCompanyId());
		oAuthClientASLocalMetadata.setUserId(user.getUserId());
		oAuthClientASLocalMetadata.setUserName(user.getFullName());
		oAuthClientASLocalMetadata.setIssuer(issuerString);
		oAuthClientASLocalMetadata.setLocalWellKnownEnabled(enabled);
		oAuthClientASLocalMetadata.setLocalWellKnownURI(localWellKnownURIOIC);
		oAuthClientASLocalMetadata.setMetadataJSON(metadataJSONOIC);
		oAuthClientASLocalMetadata.setOAuthASLocalWellKnownURI(
			_generateLocalWellKnownURI(
				issuerString, null, "oauth-authorization-server"));
		oAuthClientASLocalMetadata.setOAuthASMetadataJSON(metadataJSONOAS);

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
	public OAuthClientASLocalMetadata addOAuthClientASLocalMetadata(
			long userId, String metadataJSON, String wellKnownURISuffix)
		throws PortalException {

		AuthorizationServerMetadata authorizationServerMetadata =
			_parseAuthorizationServerMetadata(metadataJSON, wellKnownURISuffix);

		String localWellKnownURI = _generateLocalWellKnownURI(
			String.valueOf(authorizationServerMetadata.getIssuer()),
			String.valueOf(authorizationServerMetadata.getTokenEndpointURI()),
			wellKnownURISuffix);

		OAuthClientASLocalMetadata oAuthClientASLocalMetadata =
			oAuthClientASLocalMetadataPersistence.fetchByLocalWellKnownURI(
				localWellKnownURI);

		if (oAuthClientASLocalMetadata != null) {
			throw new DuplicateOAuthClientASLocalMetadataException();
		}

		User user = _userLocalService.getUser(userId);

		oAuthClientASLocalMetadata =
			oAuthClientASLocalMetadataPersistence.create(
				counterLocalService.increment());

		oAuthClientASLocalMetadata.setCompanyId(user.getCompanyId());
		oAuthClientASLocalMetadata.setUserId(user.getUserId());
		oAuthClientASLocalMetadata.setUserName(user.getFullName());
		oAuthClientASLocalMetadata.setLocalWellKnownURI(localWellKnownURI);
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
	public OAuthClientASLocalMetadata deleteOAuthClientASLocalMetadata(
			String localWellKnownURI)
		throws PortalException {

		OAuthClientASLocalMetadata oAuthClientASLocalMetadata =
			oAuthClientASLocalMetadataPersistence.findByLocalWellKnownURI(
				localWellKnownURI);

		return deleteOAuthClientASLocalMetadata(oAuthClientASLocalMetadata);
	}

	@Override
	public OAuthClientASLocalMetadata fetchByOAuthClientASLocalMetadataId(
			long oAuthClientASLocalMetadataId)
		throws PortalException {

		return oAuthClientASLocalMetadataPersistence.
			fetchByOAuthClientASLocalMetadataId(oAuthClientASLocalMetadataId);
	}

	@Override
	public OAuthClientASLocalMetadata
			fetchIssuerByCompanyAuthClientASLocalMetadata(
				long companyId, String issuer)
		throws PortalException {

		return oAuthClientASLocalMetadataPersistence.fetchByC_I(
			companyId, issuer);
	}

	@Override
	public OAuthClientASLocalMetadata fetchOAuthClientASLocalMetadata(
		String localWellKnownURI) {

		return oAuthClientASLocalMetadataPersistence.fetchByLocalWellKnownURI(
			localWellKnownURI);
	}

	@Override
	public OAuthClientASLocalMetadata
		fetchOAuthClientASLocalMetadataByCompanyEnabled(
			long companyId, boolean enabled,
			OrderByComparator<OAuthClientASLocalMetadata> orderByComparator) {

		return oAuthClientASLocalMetadataPersistence.fetchByC_L_First(
			companyId, enabled, orderByComparator);
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
			String localWellKnownURI)
		throws PortalException {

		return oAuthClientASLocalMetadataPersistence.findByLocalWellKnownURI(
			localWellKnownURI);
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
			long oAuthClientASLocalMetadataId, String authorizationEndpoint,
			Boolean enabled, String issuerString, String jwksUri,
			String[] supportedGrantTypes, String[] supportedScopes,
			String[] supportedSubjectTypes, String tokenEndpointString,
			String userinfoEndpoint)
		throws PortalException {

		OAuthClientASLocalMetadata oAuthClientASLocalMetadata =
			oAuthClientASLocalMetadataLocalService.
				getOAuthClientASLocalMetadata(oAuthClientASLocalMetadataId);

		String currentLocalWellKnownURIOIC =
			oAuthClientASLocalMetadata.getLocalWellKnownURI();

		if (!issuerString.equals(
				String.valueOf(oAuthClientASLocalMetadata.getIssuer())) ||
			currentLocalWellKnownURIOIC.contains("openid-configuration")) {

			String metadataJSONOIC = _buildOpenIdConfigurationJSON(
				authorizationEndpoint, issuerString, jwksUri,
				supportedGrantTypes, supportedScopes, supportedSubjectTypes,
				tokenEndpointString, userinfoEndpoint);

			String metadataJSONOAS = _buildAuthorizationServerJSON(
				authorizationEndpoint, issuerString, jwksUri, supportedScopes,
				supportedGrantTypes, tokenEndpointString);

			oAuthClientASLocalMetadata.setIssuer(issuerString);
			oAuthClientASLocalMetadata.setLocalWellKnownEnabled(enabled);
			oAuthClientASLocalMetadata.setLocalWellKnownURI(
				_generateLocalWellKnownURI(
					issuerString, tokenEndpointString, "openid-configuration"));
			oAuthClientASLocalMetadata.setMetadataJSON(metadataJSONOIC);
			oAuthClientASLocalMetadata.setOAuthASLocalWellKnownURI(
				_generateLocalWellKnownURI(
					issuerString, null, "oauth-authorization-server"));
			oAuthClientASLocalMetadata.setOAuthASMetadataJSON(metadataJSONOAS);
		}

		return oAuthClientASLocalMetadataPersistence.update(
			oAuthClientASLocalMetadata);
	}

	@Override
	public OAuthClientASLocalMetadata updateOAuthClientASLocalMetadata(
			long oAuthClientASLocalMetadataId, String metadataJSON,
			String wellKnownURISuffix)
		throws PortalException {

		AuthorizationServerMetadata authorizationServerMetadata =
			_parseAuthorizationServerMetadata(metadataJSON, wellKnownURISuffix);

		OAuthClientASLocalMetadata oAuthClientASLocalMetadata =
			oAuthClientASLocalMetadataLocalService.
				getOAuthClientASLocalMetadata(oAuthClientASLocalMetadataId);

		AuthorizationServerMetadata currentAuthorizationServerMetadata =
			_parseAuthorizationServerMetadata(
				oAuthClientASLocalMetadata.getMetadataJSON(),
				wellKnownURISuffix);

		oAuthClientASLocalMetadata.setMetadataJSON(metadataJSON);

		String currentIssuer = String.valueOf(
			currentAuthorizationServerMetadata.getIssuer());
		String currentLocalWellKnownURI =
			oAuthClientASLocalMetadata.getLocalWellKnownURI();

		if (!currentIssuer.equals(
				String.valueOf(authorizationServerMetadata.getIssuer())) ||
			!currentLocalWellKnownURI.contains(wellKnownURISuffix)) {

			oAuthClientASLocalMetadata.setLocalWellKnownURI(
				_generateLocalWellKnownURI(
					String.valueOf(authorizationServerMetadata.getIssuer()),
					String.valueOf(
						authorizationServerMetadata.getTokenEndpointURI()),
					wellKnownURISuffix));
		}

		return oAuthClientASLocalMetadataPersistence.update(
			oAuthClientASLocalMetadata);
	}

	private String _buildAuthorizationServerJSON(
			String authorizationEndpoint, String issuerStr, String jwksUri,
			String[] supportedScopes, String[] supportedGrantTypes,
			String tokenEndpoint)
		throws PortalException {

		try {
			Issuer issuer = new Issuer(issuerStr);

			AuthorizationServerMetadata metadata =
				new AuthorizationServerMetadata(issuer);

			metadata.setAuthorizationEndpointURI(
				new URI(authorizationEndpoint));
			metadata.setJWKSetURI(new URI(jwksUri));
			metadata.setTokenEndpointURI(new URI(tokenEndpoint));

			Scope scope = new Scope();

			for (String s : supportedScopes) {
				scope.add(s);
			}

			metadata.setScopes(scope);

			GrantType[] grantTypes = new GrantType[supportedGrantTypes.length];

			for (int i = 0; i < supportedGrantTypes.length; i++) {
				grantTypes[i] = GrantType.parse(supportedGrantTypes[i]);
			}

			metadata.setGrantTypes(Arrays.asList(grantTypes));

			return metadata.toJSONObject(
			).toJSONString();
		}
		catch (Exception exception) {
			throw new OAuthClientASLocalMetadataJSONException(
				exception.getMessage(), exception);
		}
	}

	private String _buildOpenIdConfigurationJSON(
			String authorizationEndpoint, String issuerStr, String jwksUri,
			String[] supportedGrantTypes, String[] supportedScopes,
			String[] supportedSubjectTypes, String tokenEndpoint,
			String userinfoEndpoint)
		throws PortalException {

		try {
			Issuer issuer = new Issuer(issuerStr);

			List<SubjectType> subjectTypes = new ArrayList<>();

			if (supportedSubjectTypes != null) {
				for (String st : supportedSubjectTypes) {
					subjectTypes.add(SubjectType.parse(st));
				}
			}

			OIDCProviderMetadata metadata = new OIDCProviderMetadata(
				issuer, subjectTypes, new URI(jwksUri));

			metadata.setAuthorizationEndpointURI(
				new URI(authorizationEndpoint));
			metadata.setTokenEndpointURI(new URI(tokenEndpoint));

			Scope scope = new Scope();

			for (String s : supportedScopes) {
				scope.add(s);
			}

			metadata.setScopes(scope);

			metadata.setUserInfoEndpointURI(new URI(userinfoEndpoint));

			GrantType[] grantTypes = new GrantType[supportedGrantTypes.length];

			for (int i = 0; i < supportedGrantTypes.length; i++) {
				grantTypes[i] = GrantType.parse(supportedGrantTypes[i]);
			}

			metadata.setGrantTypes(Arrays.asList(grantTypes));

			return metadata.toJSONObject(
			).toJSONString();
		}
		catch (Exception exception) {
			throw new OAuthClientASLocalMetadataJSONException(
				exception.getMessage(), exception);
		}
	}

	private String _generateLocalWellKnownURI(
			String issuer, String tokenEndpoint, String wellKnownURISuffix)
		throws PortalException {

		try {
			URI issuerURI = URI.create(issuer);

			if (wellKnownURISuffix.equals("openid-configuration")) {
				MessageDigest messageDigest = MessageDigest.getInstance("MD5");

				return StringBundler.concat(
					issuerURI.getScheme(), "://", issuerURI.getAuthority(),
					"/.well-known/", wellKnownURISuffix, issuerURI.getPath(),
					'/',
					Base64.encodeToURL(
						messageDigest.digest(tokenEndpoint.getBytes())),
					"/local");
			}

			return StringBundler.concat(
				issuerURI.getScheme(), "://", issuerURI.getAuthority(),
				"/o/.well-known/", wellKnownURISuffix);
		}
		catch (Exception exception) {
			throw new OAuthClientASLocalMetadataLocalWellKnownURIException(
				exception);
		}
	}

	private AuthorizationServerMetadata _parseAuthorizationServerMetadata(
			String metadataJSON, String wellKnownURISuffix)
		throws PortalException {

		try {
			if (wellKnownURISuffix.equals("openid-configuration")) {
				return OIDCProviderMetadata.parse(metadataJSON);
			}

			return AuthorizationServerMetadata.parse(metadataJSON);
		}
		catch (Exception exception) {
			throw new OAuthClientASLocalMetadataJSONException(
				exception.getMessage(), exception);
		}
	}

	@Reference
	private ResourceLocalService _resourceLocalService;

	@Reference
	private UserLocalService _userLocalService;

}