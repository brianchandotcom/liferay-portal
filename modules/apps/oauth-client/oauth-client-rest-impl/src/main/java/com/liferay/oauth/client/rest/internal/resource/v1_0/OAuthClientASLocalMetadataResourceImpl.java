/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.oauth.client.rest.internal.resource.v1_0;

import com.liferay.oauth.client.rest.resource.v1_0.OAuthClientASLocalMetadataResource;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.ServiceScope;

/**
 * @author Manuele Castro
 */
@Component(
	properties = "OSGI-INF/liferay/rest/v1_0/o-auth-client-as-local-metadata.properties",
	scope = ServiceScope.PROTOTYPE,
	service = OAuthClientASLocalMetadataResource.class
)
public class OAuthClientASLocalMetadataResourceImpl
	extends BaseOAuthClientASLocalMetadataResourceImpl {
}