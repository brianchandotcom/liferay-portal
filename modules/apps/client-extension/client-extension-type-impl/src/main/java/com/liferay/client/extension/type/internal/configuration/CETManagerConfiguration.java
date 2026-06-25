/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.client.extension.type.internal.configuration;

import aQute.bnd.annotation.metatype.Meta;

import com.liferay.portal.configuration.metatype.annotations.ExtendedObjectClassDefinition;

/**
 * @author Drew Brokke
 */
@ExtendedObjectClassDefinition(category = "infrastructure")
@Meta.OCD(
	id = "com.liferay.client.extension.type.internal.configuration.CETManagerConfiguration",
	localization = "content/Language", name = "cet-manager-configuration-name"
)
public interface CETManagerConfiguration {

	@Meta.AD(
		deflt = "false", description = "cet-manager-cache-enabled-help",
		name = "cet-manager-cache-enabled", required = false
	)
	public boolean cacheEnabled();

}