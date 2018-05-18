/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.commerce.currency.internal.configuration;

import aQute.bnd.annotation.metatype.Meta;

import com.liferay.portal.configuration.metatype.annotations.ExtendedObjectClassDefinition;

import java.math.RoundingMode;

/**
 * @author Alessio Antonio Rendina
 */
@ExtendedObjectClassDefinition(
	category = "commerce", scope = ExtendedObjectClassDefinition.Scope.SYSTEM
)
@Meta.OCD(
	id = "com.liferay.commerce.currency.internal.configuration.RoundingTypeConfiguration",
	localization = "content/Language", name = "rounding-type-configuration-name"
)
public interface RoundingTypeConfiguration {

	@Meta.AD(deflt = "#.##", name = "format-pattern", required = false)
	public String formatPattern();

	@Meta.AD(deflt = "2", name = "maximum-fraction-digits", required = false)
	public int maximumFractionDigits();

	@Meta.AD(deflt = "2", name = "minimum-fraction-digits", required = false)
	public int minimumFractionDigits();

	@Meta.AD(deflt = "HALF_EVEN", name = "rounding-mode", required = false)
	public RoundingMode roundingMode();

}