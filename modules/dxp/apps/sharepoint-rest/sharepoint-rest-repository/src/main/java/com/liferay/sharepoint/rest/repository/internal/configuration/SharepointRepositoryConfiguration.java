/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.sharepoint.rest.repository.internal.configuration;

import aQute.bnd.annotation.metatype.Meta;

import com.liferay.portal.configuration.metatype.annotations.ExtendedObjectClassDefinition;

/**
 * @author Adolfo Pérez
 */
@ExtendedObjectClassDefinition(
	category = "documents-and-media", factoryInstanceLabelAttribute = "name",
	scope = ExtendedObjectClassDefinition.Scope.COMPANY
)
@Meta.OCD(
	factory = true,
	id = "com.liferay.sharepoint.rest.repository.internal.configuration.SharepointRepositoryConfiguration",
	localization = "content/Language",
	name = "sharepoint-repository-configuration-name"
)
public interface SharepointRepositoryConfiguration {

	@Meta.AD(name = "name", required = false)
	public String name();

	@Meta.AD(name = "authorization-grant-endpoint", required = false)
	public String authorizationGrantEndpoint();

	@Meta.AD(name = "authorization-token-endpoint", required = false)
	public String authorizationTokenEndpoint();

	@Meta.AD(name = "client-id", required = false)
	public String clientId();

	@Meta.AD(name = "client-secret", required = false)
	public String clientSecret();

	@Meta.AD(name = "scope", required = false)
	public String scope();

	@Meta.AD(name = "tenant-id", required = false)
	public String tenantId();

	@Meta.AD(name = "site-domain", required = false)
	public String siteDomain();

	@Meta.AD(name = "resource", required = false)
	public String resource();

}