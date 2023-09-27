/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.site.initializer.extender.configuration;

import aQute.bnd.annotation.metatype.Meta;

/**
 * @author José Abelenda
 */
@Meta.OCD(
	factory = true,
	id = "com.liferay.site.initializer.extender.configuration.SiteInitializerConfiguration"
)
public interface SiteInitializerConfiguration {

	@Meta.AD
	public String siteName();

}