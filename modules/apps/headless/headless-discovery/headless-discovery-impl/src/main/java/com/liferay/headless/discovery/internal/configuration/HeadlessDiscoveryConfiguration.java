/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.discovery.internal.configuration;

import aQute.bnd.annotation.metatype.Meta;

/**
 * @author Javier Gamarra
 */
@Meta.OCD(
	description = "headless-discovery-description",
	id = "com.liferay.headless.discovery.internal.configuration.HeadlessDiscoveryConfiguration",
	localization = "content/Language",
	name = "headless-discovery-configuration-name"
)
public interface HeadlessDiscoveryConfiguration {

	@Meta.AD(deflt = "true", name = "enable-api-explorer", required = false)
	public boolean enableAPIExplorer();

}