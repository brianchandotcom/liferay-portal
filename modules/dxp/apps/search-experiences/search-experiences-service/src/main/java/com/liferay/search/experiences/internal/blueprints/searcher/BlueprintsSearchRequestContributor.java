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

package com.liferay.search.experiences.internal.blueprints.searcher;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.search.searcher.SearchRequest;
import com.liferay.portal.search.searcher.SearchRequestBuilder;
import com.liferay.portal.search.searcher.SearchRequestBuilderFactory;
import com.liferay.portal.search.spi.searcher.SearchRequestContributor;
import com.liferay.search.experiences.internal.blueprints.attributes.BlueprintAttributes;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Petteri Karttunen
 */
@Component(
	immediate = true,
	property = "search.request.contributor.id=com.liferay.search.experiences.blueprints",
	service = SearchRequestContributor.class
)
public class BlueprintsSearchRequestContributor
	implements SearchRequestContributor {

	@Override
	public SearchRequest contribute(SearchRequest searchRequest) {
		SearchRequestBuilder searchRequestBuilder =
			_searchRequestBuilderFactory.builder(searchRequest);

		long blueprintId = searchRequestBuilder.withSearchContextGet(
			searchContext -> GetterUtil.getLong(
				searchContext.getAttribute("search.experiences.blueprint.id")));

		if (_log.isDebugEnabled()) {
			_log.debug(
				"Executing Search Blueprints search request contributor");
			_log.debug("Blueprint ID " + blueprintId);
		}

		if (blueprintId == 0) {
			if (_log.isDebugEnabled()) {
				_log.debug("Blueprint must be set in search context");
			}

			return searchRequest;
		}

		BlueprintAttributes blueprintAttributes =
			_searchRequestToBlueprintAttributesTranslator.translate(
				searchRequest);

		// TODO IN THE NEXT PULL REQUEST....

		_log.error(
			StringBundler.concat(
				"TODO! apply blueprint to search request", searchRequestBuilder,
				blueprintId, blueprintAttributes));

		return searchRequestBuilder.build();
	}

	private static final Log _log = LogFactoryUtil.getLog(
		BlueprintsSearchRequestContributor.class);

	@Reference
	private SearchRequestBuilderFactory _searchRequestBuilderFactory;

	@Reference
	private SearchRequestToBlueprintAttributesTranslator
		_searchRequestToBlueprintAttributesTranslator;

}