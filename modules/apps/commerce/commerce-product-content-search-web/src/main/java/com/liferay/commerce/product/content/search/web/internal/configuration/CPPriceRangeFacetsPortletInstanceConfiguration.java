/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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