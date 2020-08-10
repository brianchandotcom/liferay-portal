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

package com.liferay.commerce.product.content.search.web.internal.configuration;

import aQute.bnd.annotation.metatype.Meta;

import com.liferay.commerce.product.content.search.web.internal.constants.CPPriceRangeFacetsConstants;
import com.liferay.portal.configuration.metatype.annotations.ExtendedObjectClassDefinition;

/**
 * @author Alec Sloan
 */
@ExtendedObjectClassDefinition(
	category = "catalog",
	scope = ExtendedObjectClassDefinition.Scope.PORTLET_INSTANCE
)
@Meta.OCD(
	id = "com.liferay.commerce.product.content.search.web.internal.configuration.CPPriceRangeFacetsPortletInstanceConfiguration",
	localization = "content/Language",
	name = "commerce-product-price-range-facets-portlet-instance-configuration-name"
)
public interface CPPriceRangeFacetsPortletInstanceConfiguration {

	@Meta.AD(
		deflt = CPPriceRangeFacetsConstants.DEFAULT_PRICE_RANGES_JSON_ARRAY,
		description = "ranges-json-array-help", name = "ranges-json-array",
		required = false
	)
	public String rangesJSONArrayString();

	@Meta.AD(deflt = "true", name = "show-input-range", required = false)
	public boolean showInputRange();

}