/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.site.internal.configuration;

import aQute.bnd.annotation.metatype.Meta;

import com.liferay.portal.configuration.metatype.annotations.ExtendedObjectClassDefinition;

/**
 * @author Dante Wang
 */
@ExtendedObjectClassDefinition(
	category = "seo", scope = ExtendedObjectClassDefinition.Scope.COMPANY
)
@Meta.OCD(
	description = "site-virtual-host-configuration-description",
	id = "com.liferay.site.internal.configuration.SiteVirtualHostConfiguration",
	localization = "content/Language",
	name = "site-virtual-host-configuration-name"
)
public interface SiteVirtualHostConfiguration {

	@Meta.AD(
		deflt = "false",
		description = "allow-default-instance-url-bypass-description",
		name = "allow-default-instance-url-bypass", required = false
	)
	public boolean allowDefaultInstanceURLBypass();

	@Meta.AD(
		deflt = "false",
		description = "strict-virtual-host-mode-enabled-description",
		name = "strict-virtual-host-mode-enabled", required = false
	)
	public boolean strictModeEnabled();

}