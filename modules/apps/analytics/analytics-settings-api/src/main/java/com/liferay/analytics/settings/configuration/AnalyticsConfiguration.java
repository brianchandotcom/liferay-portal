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

package com.liferay.analytics.settings.configuration;

import aQute.bnd.annotation.metatype.Meta;

import com.liferay.portal.configuration.metatype.annotations.ExtendedObjectClassDefinition;

/**
 * @author Marcellus Tavares
 */
@ExtendedObjectClassDefinition(
	category = "analytics-cloud", generateUI = false,
	scope = ExtendedObjectClassDefinition.Scope.COMPANY
)
@Meta.OCD(
	id = "com.liferay.analytics.settings.configuration.AnalyticsConfiguration"
)
public interface AnalyticsConfiguration {

	@Meta.AD(required = false)
	public String hostsAllowed();

	@Meta.AD(required = false)
	public String liferayAnalyticsDataSourceId();

	@Meta.AD(required = false)
	public String liferayAnalyticsEndpointURL();

	@Meta.AD(required = false)
	public String liferayAnalyticsFaroBackendSecuritySignature();

	@Meta.AD(required = false)
	public String liferayAnalyticsURL();

	@Meta.AD(required = false)
	public String publicKey();

	@Meta.AD(required = false)
	public String siteReportingGrouping();

	@Meta.AD(required = false)
	public boolean syncAllContacts();

	@Meta.AD(required = false)
	public String[] syncedContactFieldNames();

	@Meta.AD(required = false)
	public String[] syncedGroupIds();

	@Meta.AD(required = false)
	public String[] syncedOrganizationIds();

	@Meta.AD(required = false)
	public String[] syncedUserFieldNames();

	@Meta.AD(required = false)
	public String[] syncedUserGroupIds();

	@Meta.AD(required = false)
	public String token();

}