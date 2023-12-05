/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.address.internal.configuration;

import aQute.bnd.annotation.metatype.Meta;

import com.liferay.portal.configuration.metatype.annotations.ExtendedObjectClassDefinition;

/**
 * @author Loc Pham
 */
@ExtendedObjectClassDefinition(
	category = "additional-countries",
	scope = ExtendedObjectClassDefinition.Scope.COMPANY
)
@Meta.OCD(
	id = "com.liferay.address.internal.configuration.AdditionalCountriesConfiguration",
	localization = "content/Language",
	name = "additional-countries-configuration-name"
)
public interface AdditionalCountriesConfiguration {

	@Meta.AD(
		description = "enable-iso-3166-countries-description",
		name = "enable-iso-3166-countries", required = false
	)
	public boolean enableISO3166Countries();

}