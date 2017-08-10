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
	id = "com.liferay.sharepoint.repository.internal.configuration.SharepointOAuth2Configuration",
	localization = "content/Language",
	name = "sharepoint-oauth2-configuration-name"
)
public interface SharepointOAuth2Configuration {

	@Meta.AD
	public String name();

	@Meta.AD
	public String authorizationGrantEndpoint();

	@Meta.AD
	public String authorizationTokenEndpoint();

	@Meta.AD
	public String clientId();

	@Meta.AD
	public String clientSecret();

	@Meta.AD
	public String scope();

	@Meta.AD
	public String tenantId();

	@Meta.AD
	public String siteDomain();

	@Meta.AD
	public String resource();

}