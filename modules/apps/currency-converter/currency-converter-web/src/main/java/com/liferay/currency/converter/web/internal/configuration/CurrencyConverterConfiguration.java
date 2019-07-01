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

package com.liferay.currency.converter.web.internal.configuration;

import aQute.bnd.annotation.metatype.Meta;

import com.liferay.portal.configuration.metatype.annotations.ExtendedObjectClassDefinition;

/**
 * @author Peter Fellwock
 */
@ExtendedObjectClassDefinition(category = "localization")
@Meta.OCD(
	id = "com.liferay.currency.converter.web.internal.configuration.CurrencyConverterConfiguration",
	localization = "content/Language",
	name = "currency-converter-configuration-name"
)
public interface CurrencyConverterConfiguration {

	@Meta.AD(deflt = "GBP|CNY|EUR|JPY|USD", name = "symbols", required = false)
	public String[] symbols();

}