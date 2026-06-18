/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.oauth.client.rest.internal.dto.v1_0.util;

import com.liferay.headless.delivery.dto.v1_0.util.CreatorUtil;
import com.liferay.oauth.client.rest.dto.v1_0.OAuthClientPRLocalMetadata;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.util.Portal;

/**
 * @author Jorge García Jiménez
 */
public class OAuthClientPRLocalMetadataUtil {

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