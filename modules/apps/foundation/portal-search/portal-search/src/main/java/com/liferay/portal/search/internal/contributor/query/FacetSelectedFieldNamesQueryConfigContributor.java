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

package com.liferay.portal.search.internal.contributor.query;

import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.QueryConfig;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.facet.Facet;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.portal.search.spi.model.query.contributor.QueryConfigContributor;
import com.liferay.portal.search.spi.model.query.contributor.helper.QueryConfigContributorHelper;

import java.util.Map;
import java.util.Set;

import org.osgi.service.component.annotations.Component;

/**
 * @author Michael C. Han
 */
@Component(
	immediate = true,
	property = {
		"service.ranking=" +
			(DefaultSelectedFieldNamesQueryConfigContributor.RANKING - 1)
	},
	service = QueryConfigContributor.class
)
public class FacetSelectedFieldNamesQueryConfigContributor
	implements QueryConfigContributor {

	@Override
	public void contributeQueryConfigurations(
		SearchContext searchContext,
		QueryConfigContributorHelper queryConfigContributorHelper) {

		QueryConfig queryConfig = searchContext.getQueryConfig();

		String[] selectedFieldNames = queryConfig.getSelectedFieldNames();

		if (ArrayUtil.isEmpty(selectedFieldNames) ||
			(selectedFieldNames.length == 1) &&
			selectedFieldNames[0].equals(Field.ANY)) {

			return;
		}

		Set<String> selectedFieldNameSet = SetUtil.fromArray(
			selectedFieldNames);

		Map<String, Facet> facets = searchContext.getFacets();

		selectedFieldNameSet.addAll(facets.keySet());

		selectedFieldNames = selectedFieldNameSet.toArray(
			new String[selectedFieldNameSet.size()]);

		queryConfig.setSelectedFieldNames(selectedFieldNames);
	}

}