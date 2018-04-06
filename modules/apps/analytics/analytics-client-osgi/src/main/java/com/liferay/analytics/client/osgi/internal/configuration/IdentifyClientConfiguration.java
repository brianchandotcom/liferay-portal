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

package com.liferay.analytics.client.osgi.internal.configuration;

import aQute.bnd.annotation.metatype.Meta;

import com.liferay.portal.configuration.metatype.annotations.ExtendedObjectClassDefinition;

/**
 * @author Inácio Nery
 */
@ExtendedObjectClassDefinition(category = "analytics")
@Meta.OCD(
	id = "com.liferay.analytics.client.osgi.internal.configuration.IdentifyClientConfiguration",
	localization = "content/Language",
	name = "identify-client-configuration-name"
)
public interface IdentifyClientConfiguration {

	@Meta.AD(
		deflt = "contacts-prod.liferay.com", name = "identify-gateway-host",
		required = false
	)
	public String identifyGatewayHost();

	@Meta.AD(
		deflt = "/identity", name = "identify-gateway-path", required = false
	)
	public String identifyGatewayPath();

	@Meta.AD(deflt = "443", name = "identify-gateway-port", required = false)
	public String identifyGatewayPort();

	@Meta.AD(
		deflt = "https", name = "identify-gateway-protocol", required = false
	)
	public String identifyGatewayProtocol();

}