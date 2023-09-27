/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.currency.internal.configuration;

import aQute.bnd.annotation.metatype.Meta;

import com.liferay.portal.configuration.metatype.annotations.ExtendedObjectClassDefinition;

/**
 * @author Alessio Antonio Rendina
 */
@ExtendedObjectClassDefinition(
	category = "pricing", scope = ExtendedObjectClassDefinition.Scope.SYSTEM
)
@Meta.OCD(
	id = "com.liferay.commerce.currency.internal.configuration.ECBExchangeRateProviderConfiguration",
	localization = "content/Language",
	name = "ecb-exchange-rate-provider-configuration-name"
)
public interface ECBExchangeRateProviderConfiguration {

	@Meta.AD(
		deflt = "https://www.ecb.europa.eu/stats/eurofxref/eurofxref-daily.xml",
		name = "european-central-bank-url", required = false
	)
	public String europeanCentralBankURL();

	@Meta.AD(
		deflt = "3600",
		description = "european-central-bank-url-cache-expiration-time-description",
		name = "european-central-bank-url-cache-expiration-time",
		required = false
	)
	public long europeanCentralBankURLCacheExpirationTime();

}