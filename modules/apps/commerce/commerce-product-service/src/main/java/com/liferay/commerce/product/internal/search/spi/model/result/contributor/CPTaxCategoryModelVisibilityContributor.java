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

package com.liferay.commerce.product.internal.search.spi.model.result.contributor;

import com.liferay.commerce.product.service.CPTaxCategoryLocalService;
import com.liferay.portal.search.spi.model.result.contributor.ModelVisibilityContributor;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Mahmoud Azzam
 */
@Component(
	property = "indexer.class.name=com.liferay.commerce.product.model.CPTaxCategory",
	service = ModelVisibilityContributor.class
)
public class CPTaxCategoryModelVisibilityContributor
	implements ModelVisibilityContributor {

	@Override
	public boolean isVisible(long classPK, int active) {
		return true;
	}

	@Reference
	private CPTaxCategoryLocalService _cpTaxCategoryLocalService;

}