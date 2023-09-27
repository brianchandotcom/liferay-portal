/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.google.merchant.internal.configuration;

import aQute.bnd.annotation.metatype.Meta;

import com.liferay.portal.configuration.metatype.annotations.ExtendedObjectClassDefinition;

/**
 * @author Eric Chin
 */
@ExtendedObjectClassDefinition(
	category = "google-merchant",
	scope = ExtendedObjectClassDefinition.Scope.SYSTEM
)
@Meta.OCD(
	id = "com.liferay.commerce.google.merchant.internal.configuration.ProductDefinitionConfiguration",
	localization = "content/Language",
	name = "commerce-product-definition-configuration-name"
)
public interface ProductDefinitionConfiguration {

	@Meta.AD(
		deflt = "15", name = "product-definition-generator-time-interval",
		required = false
	)
	public int generatorTimeInterval();

	@Meta.AD(
		deflt = "MINUTE",
		name = "product-definition-generator-time-interval-unit",
		optionLabels = {"Day", "Hour", "Minute"},
		optionValues = {"DAY", "HOUR", "MINUTE"}, required = false
	)
	public String generatorTimeIntervalUnit();

	@Meta.AD(name = "cron-expression", required = false)
	public String cronExpression();

}