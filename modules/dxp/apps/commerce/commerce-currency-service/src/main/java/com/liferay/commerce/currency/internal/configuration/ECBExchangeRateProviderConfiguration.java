/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.commerce.currency.internal.configuration;

import aQute.bnd.annotation.metatype.Meta;

import com.liferay.portal.configuration.metatype.annotations.ExtendedObjectClassDefinition;

/**
 * @author Alessio Antonio Rendina
 */
@ExtendedObjectClassDefinition(
	category = "commerce", scope = ExtendedObjectClassDefinition.Scope.SYSTEM
)
@Meta.OCD(
	id = "com.liferay.commerce.currency.internal.configuration.ECBExchangeRateProviderConfiguration",
	localization = "content/Language",
	name = "ecb-exchange-rate-provider-configuration-name"
)
public interface ECBExchangeRateProviderConfiguration {

	@Meta.AD(
		deflt = "http://www.ecb.europa.eu/stats/eurofxref/eurofxref-daily.xml",
		name = "european-central-bank-url", required = false
	)
	public String europeanCentralBankURL();

}