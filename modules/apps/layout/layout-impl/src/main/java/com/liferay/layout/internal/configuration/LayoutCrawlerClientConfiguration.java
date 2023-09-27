/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.layout.internal.configuration;

import aQute.bnd.annotation.metatype.Meta;

import com.liferay.portal.configuration.metatype.annotations.ExtendedObjectClassDefinition;

/**
 * @author dnebinger
 * @deprecated As of Cavanaugh (7.4.x), with no direct replacement
 */
@Deprecated
@ExtendedObjectClassDefinition(
	category = "pages", scope = ExtendedObjectClassDefinition.Scope.GROUP
)
@Meta.OCD(
	description = "layout-crawler-client-configuration-description",
	id = "com.liferay.layout.internal.configuration.LayoutCrawlerClientConfiguration",
	localization = "content/Language",
	name = "layout-crawler-client-configuration-name"
)
public interface LayoutCrawlerClientConfiguration {

	@Meta.AD(
		deflt = "false",
		description = "layout-crawler-client-configuration-enabled-description",
		name = "enabled", required = false
	)
	public boolean enabled();

	@Meta.AD(
		deflt = "localhost",
		description = "layout-crawler-client-configuration-hostname-description",
		name = "hostname", required = false
	)
	public String hostName();

	@Meta.AD(
		deflt = "8080",
		description = "layout-crawler-client-configuration-port-description",
		name = "port", required = false
	)
	public int port();

	@Meta.AD(
		deflt = "false",
		description = "layout-crawler-client-configuration-secure-description",
		name = "secure", required = false
	)
	public boolean secure();

}