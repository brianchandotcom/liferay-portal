/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.asset.auto.tagger.internal.configuration;

import aQute.bnd.annotation.metatype.Meta;

import com.liferay.portal.configuration.metatype.annotations.ExtendedObjectClassDefinition;

/**
 * @author Alejandro Tardín
 */
@ExtendedObjectClassDefinition(
	category = "assets", generateUI = false,
	scope = ExtendedObjectClassDefinition.Scope.GROUP
)
@Meta.OCD(
	id = "com.liferay.asset.auto.tagger.internal.configuration.AssetAutoTaggerGroupConfiguration",
	localization = "content/Language",
	name = "asset-auto-tagger-group-configuration-name"
)
public interface AssetAutoTaggerGroupConfiguration {

	/**
	 * Enables asset auto tagging.
	 */
	@Meta.AD(
		deflt = "true", name = "enabled[asset-auto-tagger-service]",
		required = false
	)
	public boolean enabled();

}