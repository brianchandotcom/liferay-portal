/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.account.validator.vies.internal.configuration;

import aQute.bnd.annotation.metatype.Meta;

import com.liferay.portal.configuration.metatype.annotations.ExtendedObjectClassDefinition;

/**
 * @author Crescenzo Rega
 */
@ExtendedObjectClassDefinition(
	category = "accounts", scope = ExtendedObjectClassDefinition.Scope.SYSTEM
)
@Meta.OCD(
	id = "com.liferay.account.validator.vies.internal.configuration.VIESClientConfiguration",
	localization = "content/Language", name = "vies-client-configuration-name"
)
public interface VIESClientConfiguration {

	@Meta.AD(
		deflt = "https://ec.europa.eu/taxation_customs/vies/rest-api/check-vat-number",
		description = "vies-endpoint-url-help", name = "vies-endpoint-url",
		required = false
	)
	public String viesEndpointURL();

}