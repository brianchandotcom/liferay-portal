/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.oauth.client.rest.internal.dto.v1_0.util;

import com.liferay.headless.delivery.dto.v1_0.util.CreatorUtil;
import com.liferay.oauth.client.persistence.service.OAuthClientPRLocalMetadataService;
import com.liferay.oauth.client.rest.dto.v1_0.OAuthClientPRLocalMetadata;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.Validator;

/**
 * @author Jorge García Jiménez
 */
public class OAuthClientPRLocalMetadataUtil {

	public static
		com.liferay.oauth.client.persistence.model.OAuthClientPRLocalMetadata
				addOAuthClientPRLocalMetadata(
					OAuthClientPRLocalMetadata oAuthClientPRLocalMetadata,
					OAuthClientPRLocalMetadataService
						oAuthClientPRLocalMetadataService)
			throws Exception {

		String metadataJSON = oAuthClientPRLocalMetadata.getMetadataJSON();

		if (Validator.isNull(metadataJSON)) {
			throw new IllegalArgumentException("Metadata JSON is required");
		}

		JSONObject metadataJSONObject = JSONFactoryUtil.createJSONObject(
			metadataJSON);

		return oAuthClientPRLocalMetadataService.addOAuthClientPRLocalMetadata(
			oAuthClientPRLocalMetadata.getExternalReferenceCode(),
			JSONUtil.toStringArray(
				metadataJSONObject.getJSONArray("authorization_servers")),
			JSONUtil.toStringArray(
				metadataJSONObject.getJSONArray("bearer_methods_supported")),
			GetterUtil.getBoolean(
				oAuthClientPRLocalMetadata.getLocalWellKnownEnabled()),
			oAuthClientPRLocalMetadata.getProtectedResourceURI(),
			metadataJSONObject.getString("resource_name"),
			JSONUtil.toStringArray(
				metadataJSONObject.getJSONArray("scopes_supported")));
	}

	public static OAuthClientPRLocalMetadata toOAuthClientPRLocalMetadata(
		Portal portal,
		com.liferay.oauth.client.persistence.model.OAuthClientPRLocalMetadata
			serviceBuilderOAuthClientPRLocalMetadata,
		User user) {

		return new OAuthClientPRLocalMetadata() {
			{
				setCreator(() -> CreatorUtil.toCreator(null, portal, user));
				setDateCreated(
					serviceBuilderOAuthClientPRLocalMetadata::getCreateDate);
				setDateModified(
					serviceBuilderOAuthClientPRLocalMetadata::getModifiedDate);
				setExternalReferenceCode(
					serviceBuilderOAuthClientPRLocalMetadata::
						getExternalReferenceCode);
				setLocalWellKnownEnabled(
					serviceBuilderOAuthClientPRLocalMetadata::
						getLocalWellKnownEnabled);
				setLocalWellKnownURI(
					serviceBuilderOAuthClientPRLocalMetadata::
						getLocalWellKnownURI);
				setMetadataJSON(
					serviceBuilderOAuthClientPRLocalMetadata::getMetadataJSON);
				setProtectedResourceURI(
					serviceBuilderOAuthClientPRLocalMetadata::
						getProtectedResourceURI);
			}
		};
	}

}