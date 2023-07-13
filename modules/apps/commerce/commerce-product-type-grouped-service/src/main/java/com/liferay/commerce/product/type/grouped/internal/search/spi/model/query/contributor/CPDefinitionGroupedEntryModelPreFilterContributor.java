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

package com.liferay.commerce.product.type.grouped.internal.search.spi.model.query.contributor;

import com.liferay.portal.kernel.search.BooleanClauseOccur;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.filter.BooleanFilter;
import com.liferay.portal.kernel.search.filter.ExistsFilter;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.search.spi.model.query.contributor.ModelPreFilterContributor;
import com.liferay.portal.search.spi.model.registrar.ModelSearchSettings;

import org.osgi.service.component.annotations.Component;

/**
 * @author Brian I. Kim
 */
@Component(
	property = "indexer.class.name=com.liferay.commerce.product.type.grouped.model.CPDefinitionGroupedEntry",
	service = ModelPreFilterContributor.class
)
public class CPDefinitionGroupedEntryModelPreFilterContributor
	implements ModelPreFilterContributor {

	@Override
	public void contribute(
		BooleanFilter booleanFilter, ModelSearchSettings modelSearchSettings,
		SearchContext searchContext) {

		_filterByCPDefinitionId(booleanFilter, searchContext);
	}

	private void _filterByCPDefinitionId(
		BooleanFilter booleanFilter, SearchContext searchContext) {

		long cpDefinitionId = GetterUtil.getLong(
			searchContext.getAttribute("cpDefinitionId"));

		if (cpDefinitionId == 0) {
			booleanFilter.add(
				new ExistsFilter("cpDefinitionId"),
				BooleanClauseOccur.MUST_NOT);
		}

		booleanFilter.addRequiredTerm("cpDefinitionId", cpDefinitionId);
	}

}