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

package com.liferay.oauth2.provider.jsonws.internal.configuration;

import aQute.bnd.annotation.metatype.Meta;

import com.liferay.portal.configuration.metatype.annotations.ExtendedObjectClassDefinition;

/**
 * @author Tomas Polesovsky
 */
@ExtendedObjectClassDefinition(
	category = "oauth2", scope = ExtendedObjectClassDefinition.Scope.SYSTEM
)
@Meta.OCD(
	id = "com.liferay.oauth2.provider.jsonws.internal.configuration.OAuth2JSONWSConfiguration",
	localization = "content/Language", name = "oauth2-jsonws-configuration-name"
)
public interface OAuth2JSONWSConfiguration {

	@Meta.AD(
		deflt = "oauth2.application.description.JSONWS",
		description = "oauth2-jsonws-application-description-description",
		id = "oauth2.application.description",
		name = "oauth2-jsonws-application-description", required = false
	)
	public String applicationDescription();

	@Meta.AD(
		deflt = "true",
		description = "oauth2-jsonws-create-oauth2-sap-entries-on-startup-description",
		id = "oauth2.create.oauth2.sap.entries.on.startup",
		name = "oauth2-jsonws-create-oauth2-sap-entries-on-startup",
		required = false
	)
	public boolean createOAuth2SAPEntriesOnStartup();

	@Meta.AD(
		deflt = "true",
		description = "oauth2-remove-sap-entry-oauth2-prefix-description",
		id = "oauth2.remove.sap.entry.oauth2.prefix",
		name = "oauth2-remove-sap-entry-oauth2-prefix", required = false
	)
	public boolean removeSAPEntryOAuth2Prefix();

	@Meta.AD(
		deflt = "OAUTH2_",
		description = "oauth2-sap-entry-oauth2-prefix-description",
		id = "oauth2.sap.entry.oauth2.prefix",
		name = "oauth2-sap-entry-oauth2-prefix", required = false
	)
	public String sapEntryOAuth2Prefix();

}