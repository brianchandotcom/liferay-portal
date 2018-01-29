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

package com.liferay.sharepoint.repository.internal.configuration;

import aQute.bnd.annotation.metatype.Meta;

import com.liferay.portal.configuration.metatype.annotations.ExtendedObjectClassDefinition;

/**
 * @author Adolfo Pérez
 */
@ExtendedObjectClassDefinition(
	category = "collaboration", factoryInstanceLabelAttribute = "name"
)
@Meta.OCD(
	factory = true,
	id = "com.liferay.sharepoint.repository.internal.configuration.SharepointRepositoryConfiguration",
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