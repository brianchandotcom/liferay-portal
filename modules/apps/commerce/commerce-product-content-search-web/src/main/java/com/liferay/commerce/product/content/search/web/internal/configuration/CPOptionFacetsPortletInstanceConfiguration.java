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

import com.liferay.portal.configuration.metatype.annotations.ExtendedObjectClassDefinition;

/**
 * @author Andrea Sbarra
 */
@ExtendedObjectClassDefinition(
	category = "catalog",
	scope = ExtendedObjectClassDefinition.Scope.PORTLET_INSTANCE
)
@Meta.OCD(
	id = "com.liferay.commerce.product.content.search.web.internal.configuration.CPOptionFacetsPortletInstanceConfiguration",
	localization = "content/Language",
	name = "commerce-product-option-facets-portlet-instance-configuration-name"
)
public interface CPOptionFacetsPortletInstanceConfiguration {

	@Meta.AD(deflt = "0", name = "display-style-group-id", required = false)
	public long displayStyleGroupId();

	@Meta.AD(name = "display-style", required = false)
	public String displayStyle();

	@Meta.AD(deflt = "10", name = "max-terms", required = false)
	public int getMaxTerms();

	@Meta.AD(deflt = "1", name = "frequency-treshold", required = false)
	public int getFrequencyThreshold();

	@Meta.AD(
		deflt = "100",
		name = "cp-option-facet-portlet-instance-configuration-limit-max-terms",
		required = false
	)
	public int limitMaxTerms();

	@Meta.AD(deflt = "true", name = "display-frequencies", required = false)
	public boolean showFrequencies();

}