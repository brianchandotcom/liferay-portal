/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.search.experiences.internal.blueprint.search.spi.searcher;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.search.searcher.SearchRequest;
import com.liferay.portal.search.searcher.SearchRequestBuilder;
import com.liferay.portal.search.searcher.SearchRequestBuilderFactory;
import com.liferay.portal.search.spi.searcher.SearchRequestContributor;
import com.liferay.search.experiences.model.SXPBlueprint;
import com.liferay.search.experiences.rest.dto.v1_0.enhancer.SXPBlueprintSearchRequestEnhancer;
import com.liferay.search.experiences.rest.dto.v1_0.util.SXPBlueprintUtil;
import com.liferay.search.experiences.service.SXPBlueprintLocalService;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Petteri Karttunen
 */
@Component(
	immediate = true,
	property = "search.request.contributor.id=com.liferay.search.experiences.blueprint",
	service = SearchRequestContributor.class
)
public class SXPBlueprintSearchRequestContributor
	implements SearchRequestContributor {

	@Override
	public SearchRequest contribute(SearchRequest searchRequest) {
		SearchRequestBuilder searchRequestBuilder =
			_searchRequestBuilderFactory.builder(searchRequest);

		long sxpBlueprintId = searchRequestBuilder.withSearchContextGet(
			searchContext -> GetterUtil.getLong(
				searchContext.getAttribute("search.experiences.blueprint.id")));

		if (_log.isDebugEnabled()) {
			_log.debug("Search experiences blueprint ID " + sxpBlueprintId);
		}

		if (sxpBlueprintId == 0) {
			return searchRequest;
		}

		SXPBlueprint sxpBlueprint = _sxpBlueprintLocalService.fetchSXPBlueprint(
			sxpBlueprintId);

		if (_log.isDebugEnabled()) {
			_log.debug("Search experiences blueprint " + sxpBlueprint);
		}

		if (sxpBlueprint == null) {
			return searchRequest;
		}

		_sxpBlueprintSearchRequestEnhancer.enhance(
			searchRequestBuilder,
			SXPBlueprintUtil.toSXPBlueprint(sxpBlueprint));

		return searchRequestBuilder.build();
	}

	private static final Log _log = LogFactoryUtil.getLog(
		SXPBlueprintSearchRequestContributor.class);

	@Reference
	private SearchRequestBuilderFactory _searchRequestBuilderFactory;

	@Reference
	private SXPBlueprintLocalService _sxpBlueprintLocalService;

	@Reference
	private SXPBlueprintSearchRequestEnhancer
		_sxpBlueprintSearchRequestEnhancer;

}