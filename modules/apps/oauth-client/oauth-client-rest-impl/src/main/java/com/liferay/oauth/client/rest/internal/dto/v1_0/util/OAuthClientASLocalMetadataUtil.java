/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.oauth.client.rest.internal.dto.v1_0.util;

import com.liferay.headless.delivery.dto.v1_0.util.CreatorUtil;
import com.liferay.oauth.client.persistence.service.OAuthClientASLocalMetadataService;
import com.liferay.oauth.client.rest.dto.v1_0.OAuthClientASLocalMetadata;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.util.Portal;

/**
 * @author Manuele Castro
 */
public class OAuthClientASLocalMetadataUtil {

	public static
		com.liferay.oauth.client.persistence.model.OAuthClientASLocalMetadata
				addAuthClientASLocalMetadata(
					OAuthClientASLocalMetadata oAuthClientASLocalMetadata,
					OAuthClientASLocalMetadataService
						oAuthClientASLocalMetadataService)
			throws Exception {

		JSONObject metadataJSONObject = JSONFactoryUtil.createJSONObject(
			oAuthClientASLocalMetadata.getMetadataJSON());
		JSONObject oAuthASMetadataJSONObject = JSONFactoryUtil.createJSONObject(
			oAuthClientASLocalMetadata.getOAuthASMetadataJSON());

		return oAuthClientASLocalMetadataService.addOAuthClientASLocalMetadata(
			oAuthClientASLocalMetadata.getExternalReferenceCode(),
			metadataJSONObject.getString("authorization_endpoint"),
			oAuthClientASLocalMetadata.getIssuer(),
			metadataJSONObject.getString("jwks_uri"),
			oAuthClientASLocalMetadata.getLocalWellKnownEnabled(),
			oAuthASMetadataJSONObject.getString("registration_endpoint"),
			JSONUtil.toStringArray(
				metadataJSONObject.getJSONArray("grant_types_supported")),
			JSONUtil.toStringArray(
				metadataJSONObject.getJSONArray("scopes_supported")),
			JSONUtil.toStringArray(
				metadataJSONObject.getJSONArray("subject_types_supported")),
			metadataJSONObject.getString("token_endpoint"),
			metadataJSONObject.getString("userinfo_endpoint"));
	}

	public static OAuthClientASLocalMetadata toOAuthClientASLocalMetadata(
		Portal portal,
		com.liferay.oauth.client.persistence.model.OAuthClientASLocalMetadata
			serviceBuilderOAuthClientASLocalMetadata,
		User user) {

		return new OAuthClientASLocalMetadata() {
			{
				setCreator(() -> CreatorUtil.toCreator(null, portal, user));
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

}