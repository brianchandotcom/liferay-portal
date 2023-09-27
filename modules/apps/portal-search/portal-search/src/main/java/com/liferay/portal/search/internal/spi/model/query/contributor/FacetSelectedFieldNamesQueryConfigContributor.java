/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.internal.spi.model.query.contributor;

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
	property = "service.ranking:Integer=" + (DefaultSelectedFieldNamesQueryConfigContributor.RANKING - 1),
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
			((selectedFieldNames.length == 1) &&
			 selectedFieldNames[0].equals(Field.ANY))) {

			return;
		}

		Set<String> selectedFieldNameSet = SetUtil.fromArray(
			selectedFieldNames);

		Map<String, Facet> facets = searchContext.getFacets();

		selectedFieldNameSet.addAll(facets.keySet());

		selectedFieldNames = selectedFieldNameSet.toArray(new String[0]);

		queryConfig.setSelectedFieldNames(selectedFieldNames);
	}

}