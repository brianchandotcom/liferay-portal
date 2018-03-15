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

package com.liferay.saml.opensaml.integration.internal.transport.configuration;

import aQute.bnd.annotation.metatype.Meta;

import com.liferay.portal.configuration.metatype.annotations.ExtendedObjectClassDefinition;

/**
 * @author Michael C. Han
 */
@ExtendedObjectClassDefinition(category = "foundation")
@Meta.OCD(
	id = "com.liferay.saml.opensaml.integration.internal.transport.configuration.HttpClientFactoryConfiguration",
	localization = "content/Language",
	name = "http-client-factory-configuration-name"
)
public interface HttpClientFactoryConfiguration {

	@Meta.AD(deflt = "20", required = false)
	public int defaultMaxConnectionsPerRoute();

	@Meta.AD(deflt = "20", required = false)
	public int maxTotalConnections();

	@Meta.AD(
		deflt = "60000", name = "connection-manager-timeout", required = false
	)
	public int getConnectionManagerTimeout();

	@Meta.AD(deflt = "60000", name = "so-timeout", required = false)
	public int getSoTimeout();

}