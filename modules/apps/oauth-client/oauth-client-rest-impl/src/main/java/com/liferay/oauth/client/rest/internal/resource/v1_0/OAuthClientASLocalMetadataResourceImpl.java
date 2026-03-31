/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.oauth.client.rest.internal.resource.v1_0;

import com.liferay.exportimport.kernel.lar.PortletDataContext;
import com.liferay.exportimport.vulcan.batch.engine.ExportImportVulcanBatchEngineTaskItemDelegate;
import com.liferay.headless.delivery.dto.v1_0.util.CreatorUtil;
import com.liferay.oauth.client.constants.OAuthClientAdminPortletKeys;
import com.liferay.oauth.client.persistence.exception.NoSuchOAuthClientASLocalMetadataException;
import com.liferay.oauth.client.persistence.service.OAuthClientASLocalMetadataService;
import com.liferay.oauth.client.rest.dto.v1_0.OAuthClientASLocalMetadata;
import com.liferay.oauth.client.rest.resource.v1_0.OAuthClientASLocalMetadataResource;
import com.liferay.portal.kernel.feature.flag.FeatureFlagManagerUtil;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.vulcan.pagination.Page;

import java.util.ArrayList;
import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ServiceScope;

/**
 * @author Manuele Castro
 */
@Component(
	properties = "OSGI-INF/liferay/rest/v1_0/o-auth-client-as-local-metadata.properties",
	property = "export.import.vulcan.batch.engine.task.item.delegate=true",
	scope = ServiceScope.PROTOTYPE,
	service = OAuthClientASLocalMetadataResource.class
)
public class OAuthClientASLocalMetadataResourceImpl
	extends BaseOAuthClientASLocalMetadataResourceImpl
	implements ExportImportVulcanBatchEngineTaskItemDelegate
		<OAuthClientASLocalMetadata> {

	@Override
	public void deleteOAuthClientASLocalMetadataByExternalReferenceCode(
			String oAuthClientASLocalMetadataExternalReferenceCode)
		throws Exception {

		if (!FeatureFlagManagerUtil.isEnabled(
				contextCompany.getCompanyId(), "LPD-49855")) {

			throw new UnsupportedOperationException();
		}

		com.liferay.oauth.client.persistence.model.OAuthClientASLocalMetadata
			oAuthClientASLocalMetadata =
				_oAuthClientASLocalMetadataService.
					fetchOAuthClientASLocalMetadataByExternalReferenceCode(
						oAuthClientASLocalMetadataExternalReferenceCode,
						contextCompany.getCompanyId());

		if (oAuthClientASLocalMetadata == null) {
			throw new NoSuchOAuthClientASLocalMetadataException(
				"Unable to find OAuthClientASLocalMetadata with external " +
					"reference code " +
						oAuthClientASLocalMetadataExternalReferenceCode);
		}

		_oAuthClientASLocalMetadataService.deleteOAuthClientASLocalMetadata(
			oAuthClientASLocalMetadata.getOAuthClientASLocalMetadataId());
	}

	@Override
	public ExportImportDescriptor
		<com.liferay.oauth.client.persistence.model.OAuthClientASLocalMetadata>
			getExportImportDescriptor() {

		return new ExportImportDescriptor<>() {

			@Override
			public String getKey() {
				return OAuthClientASLocalMetadataResourceImpl.class.getName();
			}

			@Override
			public String getLabelLanguageKey() {
				return "oauth-client-as-local-metadata-entries";
			}

			@Override
			public Class
				<com.liferay.oauth.client.persistence.model.
					OAuthClientASLocalMetadata> getModelClass() {

				return com.liferay.oauth.client.persistence.model.
					OAuthClientASLocalMetadata.class;
			}

			@Override
			public String getPortletId() {
				return OAuthClientAdminPortletKeys.OAUTH_CLIENT_ADMIN;
			}

			@Override
			public Scope getScope() {
				return Scope.COMPANY;
			}

			@Override
			public boolean isActive(PortletDataContext portletDataContext) {
				if (!FeatureFlagManagerUtil.isEnabled(
						portletDataContext.getCompanyId(), "LPD-49855")) {

					return false;
				}

				return ExportImportDescriptor.super.isActive(
					portletDataContext);
			}

		};
	}

	@Override
	public OAuthClientASLocalMetadata
			getOAuthClientASLocalMetadataByExternalReferenceCode(
				String oAuthClientASLocalMetadataExternalReferenceCode)
		throws Exception {

		if (!FeatureFlagManagerUtil.isEnabled(
				contextCompany.getCompanyId(), "LPD-49855")) {

			throw new UnsupportedOperationException();
		}

		com.liferay.oauth.client.persistence.model.OAuthClientASLocalMetadata
			serviceBuilderOAuthClientASLocalMetadata =
				_oAuthClientASLocalMetadataService.
					getOAuthClientASLocalMetadataByExternalReferenceCode(
						oAuthClientASLocalMetadataExternalReferenceCode,
						contextCompany.getCompanyId());

		return _toOAuthClientASLocalMetadata(
			serviceBuilderOAuthClientASLocalMetadata);
	}

	@Override
	public Page<OAuthClientASLocalMetadata> getOAuthClientASLocalMetadatasPage()
		throws Exception {

		if (!FeatureFlagManagerUtil.isEnabled(
				contextCompany.getCompanyId(), "LPD-49855")) {

			throw new UnsupportedOperationException();
		}

		List<OAuthClientASLocalMetadata> oAuthClientASLocalMetadatas =
			new ArrayList<>();

		List
			<com.liferay.oauth.client.persistence.model.
				OAuthClientASLocalMetadata>
					serviceBuilderOAuthClientASLocalMetadatas =
						_oAuthClientASLocalMetadataService.
							getCompanyOAuthClientASLocalMetadata(
								contextCompany.getCompanyId());

		serviceBuilderOAuthClientASLocalMetadatas.forEach(
			serviceBuilderOAuthClientASLocalMetadata ->
				oAuthClientASLocalMetadatas.add(
					_toOAuthClientASLocalMetadata(
						serviceBuilderOAuthClientASLocalMetadata)));

		return Page.of(oAuthClientASLocalMetadatas);
	}

	@Override
	public OAuthClientASLocalMetadata postOAuthClientASLocalMetadata(
			OAuthClientASLocalMetadata oAuthClientASLocalMetadata)
		throws Exception {

		if (!FeatureFlagManagerUtil.isEnabled(
				contextCompany.getCompanyId(), "LPD-49855")) {

			throw new UnsupportedOperationException();
		}

		JSONObject metadataJSONObject = _jsonFactory.createJSONObject(
			oAuthClientASLocalMetadata.getMetadataJSON());

		String authorizationEndpoint = metadataJSONObject.getString(
			"authorization_endpoint");
		String jwksURI = metadataJSONObject.getString("jwks_uri");
		String[] supportedGrantTypes = _getStringArray(
			metadataJSONObject.getJSONArray("grant_types_supported"));
		String[] supportedScopes = _getStringArray(
			metadataJSONObject.getJSONArray("scopes_supported"));
		String[] supportedSubjectTypes = _getStringArray(
			metadataJSONObject.getJSONArray("subject_types_supported"));
		String tokenEndpoint = metadataJSONObject.getString("token_endpoint");
		String userInfoEndpoint = metadataJSONObject.getString(
			"userinfo_endpoint");

		JSONObject oAuthASMetadataJSONObject = _jsonFactory.createJSONObject(
			oAuthClientASLocalMetadata.getOAuthASMetadataJSON());

		String registrationEndpoint = oAuthASMetadataJSONObject.getString(
			"registration_endpoint");

		com.liferay.oauth.client.persistence.model.OAuthClientASLocalMetadata
			serviceBuilderOAuthClientASLocalMetadata =
				_oAuthClientASLocalMetadataService.
					addOAuthClientASLocalMetadata(
						oAuthClientASLocalMetadata.getExternalReferenceCode(),
						authorizationEndpoint,
						oAuthClientASLocalMetadata.getIssuer(), jwksURI,
						oAuthClientASLocalMetadata.getLocalWellKnownEnabled(),
						registrationEndpoint, supportedGrantTypes,
						supportedScopes, supportedSubjectTypes, tokenEndpoint,
						userInfoEndpoint);

		return _toOAuthClientASLocalMetadata(
			serviceBuilderOAuthClientASLocalMetadata);
	}

	@Override
	public OAuthClientASLocalMetadata
			putOAuthClientASLocalMetadataByExternalReferenceCode(
				String oAuthClientASLocalMetadataExternalReferenceCode,
				OAuthClientASLocalMetadata oAuthClientASLocalMetadata)
		throws Exception {

		if (!FeatureFlagManagerUtil.isEnabled(
				contextCompany.getCompanyId(), "LPD-49855")) {

			throw new UnsupportedOperationException();
		}

		com.liferay.oauth.client.persistence.model.OAuthClientASLocalMetadata
			serviceBuilderOAuthClientASLocalMetadata =
				_oAuthClientASLocalMetadataService.
					fetchOAuthClientASLocalMetadataByExternalReferenceCode(
						oAuthClientASLocalMetadataExternalReferenceCode,
						contextCompany.getCompanyId());

		oAuthClientASLocalMetadata.setExternalReferenceCode(
			() -> oAuthClientASLocalMetadataExternalReferenceCode);

		if (serviceBuilderOAuthClientASLocalMetadata != null) {
			JSONObject metadataJSONObject = _jsonFactory.createJSONObject(
				oAuthClientASLocalMetadata.getMetadataJSON());

			String authorizationEndpoint = metadataJSONObject.getString(
				"authorization_endpoint");
			String jwksURI = metadataJSONObject.getString("jwks_uri");
			String[] supportedGrantTypes = _getStringArray(
				metadataJSONObject.getJSONArray("grant_types_supported"));
			String[] supportedScopes = _getStringArray(
				metadataJSONObject.getJSONArray("scopes_supported"));
			String[] supportedSubjectTypes = _getStringArray(
				metadataJSONObject.getJSONArray("subject_types_supported"));
			String tokenEndpoint = metadataJSONObject.getString(
				"token_endpoint");
			String userInfoEndpoint = metadataJSONObject.getString(
				"userinfo_endpoint");

			JSONObject oAuthASMetadataJSONObject =
				_jsonFactory.createJSONObject(
					oAuthClientASLocalMetadata.getOAuthASMetadataJSON());

			String registrationEndpoint = oAuthASMetadataJSONObject.getString(
				"registration_endpoint");

			serviceBuilderOAuthClientASLocalMetadata =
				_oAuthClientASLocalMetadataService.
					updateOAuthClientASLocalMetadata(
						serviceBuilderOAuthClientASLocalMetadata.
							getOAuthClientASLocalMetadataId(),
						authorizationEndpoint,
						oAuthClientASLocalMetadata.getIssuer(), jwksURI,
						oAuthClientASLocalMetadata.getLocalWellKnownEnabled(),
						registrationEndpoint, supportedGrantTypes,
						supportedScopes, supportedSubjectTypes, tokenEndpoint,
						userInfoEndpoint);

			return _toOAuthClientASLocalMetadata(
				serviceBuilderOAuthClientASLocalMetadata);
		}

		return postOAuthClientASLocalMetadata(oAuthClientASLocalMetadata);
	}

	private String[] _getStringArray(JSONArray jsonArray) {
		if (jsonArray == null) {
			return new String[0];
		}

		return ArrayUtil.toStringArray(jsonArray);
	}

	private OAuthClientASLocalMetadata _toOAuthClientASLocalMetadata(
		com.liferay.oauth.client.persistence.model.OAuthClientASLocalMetadata
			serviceBuilderOAuthClientASLocalMetadata) {

		return new OAuthClientASLocalMetadata() {
			{
				setCreator(
					() -> CreatorUtil.toCreator(
						null, _portal,
						_userLocalService.fetchUser(
							serviceBuilderOAuthClientASLocalMetadata.
								getUserId())));
				setDateCreated(
					serviceBuilderOAuthClientASLocalMetadata::getCreateDate);
				setDateModified(
					serviceBuilderOAuthClientASLocalMetadata::getModifiedDate);
				setExternalReferenceCode(
					serviceBuilderOAuthClientASLocalMetadata::
						getExternalReferenceCode);
				setIssuer(serviceBuilderOAuthClientASLocalMetadata::getIssuer);
				setLocalWellKnownEnabled(
					serviceBuilderOAuthClientASLocalMetadata::
						getLocalWellKnownEnabled);
				setLocalWellKnownURI(
					serviceBuilderOAuthClientASLocalMetadata::
						getLocalWellKnownURI);
				setMetadataJSON(
					serviceBuilderOAuthClientASLocalMetadata::getMetadataJSON);
				setOAuthASLocalWellKnownURI(
					serviceBuilderOAuthClientASLocalMetadata::
						getOAuthASLocalWellKnownURI);
				setOAuthASMetadataJSON(
					serviceBuilderOAuthClientASLocalMetadata::
						getOAuthASMetadataJSON);
			}
		};
	}

	@Reference
	private JSONFactory _jsonFactory;

	@Reference
	private OAuthClientASLocalMetadataService
		_oAuthClientASLocalMetadataService;

	@Reference
	private Portal _portal;

	@Reference
	private UserLocalService _userLocalService;

}