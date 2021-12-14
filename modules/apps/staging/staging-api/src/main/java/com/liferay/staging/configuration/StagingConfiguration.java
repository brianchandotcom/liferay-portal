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
		deflt = "true", description = "publish-displayed-content-help",
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