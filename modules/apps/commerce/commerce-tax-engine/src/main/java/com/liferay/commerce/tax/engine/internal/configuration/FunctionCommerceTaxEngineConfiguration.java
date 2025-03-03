/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.tax.engine.internal.configuration;

import aQute.bnd.annotation.metatype.Meta;

import com.liferay.portal.configuration.metatype.annotations.ExtendedObjectClassDefinition;

/**
 * @author Ivica Cardic
 */
@ExtendedObjectClassDefinition(
	category = "tax", factoryInstanceLabelAttribute = "key",
	scope = ExtendedObjectClassDefinition.Scope.SYSTEM
)
@Meta.OCD(
	factory = true,
	id = "com.liferay.commerce.tax.engine.internal.configuration.FunctionCommerceTaxEngineConfiguration",
	name = "commerce-tax-engine-configuration-name"
)
public interface FunctionCommerceTaxEngineConfiguration {

	@Meta.AD(name = "key", required = false)
	public String key();

	@Meta.AD(name = "name", required = false)
	public String name();

	@Meta.AD(
		name = "oauth2-application-external-reference-code", required = false,
		type = Meta.Type.String
	)
	public String oAuth2ApplicationExternalReferenceCode();

	@Meta.AD(
		name = "tax-engine-type-settings", required = false,
		type = Meta.Type.String
	)
	public String taxEngineTypeSettings();

}