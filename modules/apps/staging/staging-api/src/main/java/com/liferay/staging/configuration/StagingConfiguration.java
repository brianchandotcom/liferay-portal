/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.staging.configuration;

import aQute.bnd.annotation.metatype.Meta;

import com.liferay.portal.configuration.metatype.annotations.ExtendedObjectClassDefinition;

/**
 * @author Tamas Molnar
 */
@ExtendedObjectClassDefinition(
	category = "infrastructure",
	scope = ExtendedObjectClassDefinition.Scope.COMPANY
)
@Meta.OCD(
	description = "staging-configuration-description",
	id = "com.liferay.staging.configuration.StagingConfiguration",
	localization = "content/Language", name = "staging-configuration-name"
)
public interface StagingConfiguration {

	@Meta.AD(
		deflt = "true", description = "publish-parent-layouts-by-default-help",
		name = "publish-parent-layouts-by-default", required = false
	)
	public boolean publishParentLayoutsByDefault();

	@Meta.AD(
		deflt = "false", description = "publish-displayed-content-help",
		name = "publish-displayed-content", required = false
	)
	public boolean publishDisplayedContent();

	@Meta.AD(
		deflt = "true", description = "staging-delete-temp-lar-on-failure-help",
		name = "staging-delete-temp-lar-on-failure", required = false
	)
	public boolean stagingDeleteTempLAROnFailure();

	@Meta.AD(
		deflt = "true", description = "staging-delete-temp-lar-on-success-help",
		name = "staging-delete-temp-lar-on-success", required = false
	)
	public boolean stagingDeleteTempLAROnSuccess();

	@Meta.AD(
		deflt = "false",
		description = "staging-use-virtual-host-of-the-remote-site-help",
		name = "staging-use-virtual-host-of-the-remote-site", required = false
	)
	public boolean stagingUseVirtualHostForRemoteSite();

}