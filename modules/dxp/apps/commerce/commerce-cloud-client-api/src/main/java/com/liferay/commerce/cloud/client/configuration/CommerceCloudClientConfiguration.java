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

package com.liferay.commerce.cloud.client.configuration;

import aQute.bnd.annotation.metatype.Meta;

import com.liferay.commerce.cloud.client.constants.CommerceCloudClientConstants;
import com.liferay.portal.configuration.metatype.annotations.ExtendedObjectClassDefinition;

/**
 * @author Andrea Di Giorgi
 */
@ExtendedObjectClassDefinition(
	category = "commerce", scope = ExtendedObjectClassDefinition.Scope.SYSTEM
)
@Meta.OCD(
	id = CommerceCloudClientConstants.CONFIGURATION_PID,
	localization = "content/Language",
	name = "commerce-cloud-client-configuration-name"
)
public interface CommerceCloudClientConfiguration {

	@Meta.AD(
		deflt = "1", name = "order-forecast-sync-check-interval",
		required = false
	)
	public int orderForecastSyncCheckInterval();

	@Meta.AD(name = "order-forecast-sync-enabled", required = false)
	public boolean orderForecastSyncEnabled();

	@Meta.AD(
		deflt = "10", name = "order-forecast-sync-status", required = false
	)
	public int orderForecastSyncStatus();

	@Meta.AD(name = "project-id", required = false)
	public String projectId();

	@Meta.AD(name = "server-url", required = false)
	public String serverUrl();

}