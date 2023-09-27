/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.currency.configuration;

import aQute.bnd.annotation.metatype.Meta;

import com.liferay.portal.configuration.metatype.annotations.ExtendedObjectClassDefinition;

import java.math.RoundingMode;

/**
 * @author Alessio Antonio Rendina
 */
@ExtendedObjectClassDefinition(
	category = "pricing", scope = ExtendedObjectClassDefinition.Scope.SYSTEM
)
@Meta.OCD(
	id = "com.liferay.commerce.currency.configuration.RoundingTypeConfiguration",
	localization = "content/Language", name = "rounding-type-configuration-name"
)
public interface RoundingTypeConfiguration {

	@Meta.AD(deflt = "2", name = "maximum-fraction-digits", required = false)
	public int maximumFractionDigits();

	@Meta.AD(deflt = "2", name = "minimum-fraction-digits", required = false)
	public int minimumFractionDigits();

	@Meta.AD(deflt = "HALF_EVEN", name = "rounding-mode", required = false)
	public RoundingMode roundingMode();

}