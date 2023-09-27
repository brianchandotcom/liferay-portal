/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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