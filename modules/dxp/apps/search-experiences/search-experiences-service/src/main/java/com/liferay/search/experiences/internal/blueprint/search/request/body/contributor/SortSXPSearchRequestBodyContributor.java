/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.search.experiences.internal.blueprint.search.request.body.contributor;

import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.search.searcher.SearchRequestBuilder;
import com.liferay.search.experiences.internal.blueprint.parameter.SXPParameterData;
import com.liferay.search.experiences.internal.blueprint.sort.SortConverter;
import com.liferay.search.experiences.rest.dto.v1_0.Configuration;
import com.liferay.search.experiences.rest.dto.v1_0.SortConfiguration;

/**
 * @author Petteri Karttunen
 */
public class SortSXPSearchRequestBodyContributor
	implements SXPSearchRequestBodyContributor {

	public SortSXPSearchRequestBodyContributor(SortConverter sortConverter) {
		_sortConverter = sortConverter;
	}

	@Override
	public void contribute(
		Configuration configuration, SearchRequestBuilder searchRequestBuilder,
		SXPParameterData sxpParameterData) {

		SortConfiguration sortConfiguration =
			configuration.getSortConfiguration();

		if (sortConfiguration == null) {
			return;
		}

		JSONArray jsonArray = (JSONArray)sortConfiguration.getSorts();

		if (jsonArray == null) {
			return;
		}

		for (int i = 0; i < jsonArray.length(); i++) {
			searchRequestBuilder.addSort(
				_sortConverter.convert(jsonArray.get(i)));
		}
	}

	@Override
	public String getName() {
		return "sort";
	}

	private final SortConverter _sortConverter;

}