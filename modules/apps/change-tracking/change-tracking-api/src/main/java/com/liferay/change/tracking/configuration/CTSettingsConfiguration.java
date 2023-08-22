/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.change.tracking.configuration;

import aQute.bnd.annotation.metatype.Meta;

import com.liferay.portal.configuration.metatype.annotations.ExtendedObjectClassDefinition;

/**
 * @author David Truong
 */
@ExtendedObjectClassDefinition(
	category = "publications",
	scope = ExtendedObjectClassDefinition.Scope.COMPANY, strictScope = true
)
@Meta.OCD(
	id = "com.liferay.change.tracking.configuration.CTSettingsConfiguration",
	localization = "content/Language",
	name = "publications-settings-configuration-name"
)
public interface CTSettingsConfiguration {

	@Meta.AD(
		deflt = "0", name = "default-ct-collection-template-id",
		required = false
	)
	public long defaultCTCollectionTemplateId();

	@Meta.AD(
		deflt = "0", name = "default-sandbox-ct-collection-template-id",
		required = false
	)
	public long defaultSandboxCTCollectionTemplateId();

	@Meta.AD(deflt = "false", name = "enabled", required = false)
	public boolean enabled();

	@Meta.AD(deflt = "false", name = "remote-enabled", required = false)
	public boolean remoteEnabled();

	@Meta.AD(name = "remote-client-id", required = false)
	public String remoteClientId();

	@Meta.AD(name = "remote-client-secret", required = false)
	public String remoteClientSecret();

	@Meta.AD(deflt = "false", name = "sandbox-enabled", required = false)
	public boolean sandboxEnabled();

	@Meta.AD(
		deflt = "false", name = "allow-unapproved-changes", required = false
	)
	public boolean unapprovedChangesAllowed();

}