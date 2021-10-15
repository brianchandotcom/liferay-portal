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
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.search.searcher.SearchRequest;
import com.liferay.portal.search.searcher.SearchRequestBuilder;
import com.liferay.portal.search.searcher.SearchRequestBuilderFactory;
import com.liferay.portal.search.spi.searcher.SearchRequestContributor;
import com.liferay.search.experiences.blueprint.search.request.enhancer.SXPBlueprintSearchRequestEnhancer;
import com.liferay.search.experiences.model.SXPBlueprint;
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

		_contributeId(searchRequestBuilder);
		_contributeJSON(searchRequestBuilder);

		return searchRequestBuilder.build();
	}

	private void _contributeId(SearchRequestBuilder searchRequestBuilder) {
		Object object = searchRequestBuilder.withSearchContextGet(
			searchContext -> searchContext.getAttribute(
				"search.experiences.blueprint.id"));

		if (_log.isDebugEnabled()) {
			_log.debug("Search experiences blueprint ID " + object);
		}

		if (object == null) {
			return;
		}

		if (object instanceof Number) {
			_enhance(searchRequestBuilder, GetterUtil.getLong(object));

			return;
		}

		if (object instanceof String) {
			if (Validator.isNotNull((String)object)) {
				_enhance(
					searchRequestBuilder,
					GetterUtil.getLongValues(StringUtil.split((String)object)));
			}

			return;
		}

		throw new IllegalArgumentException("Invalid blueprint ID " + object);
	}

	private void _contributeJSON(SearchRequestBuilder searchRequestBuilder) {
		String sxpBlueprintJSON = searchRequestBuilder.withSearchContextGet(
			searchContext -> GetterUtil.getString(
				searchContext.getAttribute(
					"search.experiences.blueprint.json")));

		if (_log.isDebugEnabled()) {
			_log.debug("Search experiences blueprint JSON " + sxpBlueprintJSON);
		}

		if (Validator.isNotNull(sxpBlueprintJSON)) {
			_sxpBlueprintSearchRequestEnhancer.enhance(
				searchRequestBuilder, sxpBlueprintJSON);
		}
	}

	private void _enhance(
		SearchRequestBuilder searchRequestBuilder, long... sxpBlueprintIds) {

		for (long sxpBlueprintId : sxpBlueprintIds) {
			if (sxpBlueprintId != 0) {
				SXPBlueprint sxpBlueprint =
					_sxpBlueprintLocalService.fetchSXPBlueprint(sxpBlueprintId);

				if (_log.isDebugEnabled()) {
					_log.debug("Search experiences blueprint " + sxpBlueprint);
				}

				if (sxpBlueprint != null) {
					_sxpBlueprintSearchRequestEnhancer.enhance(
						searchRequestBuilder, sxpBlueprint);
				}
			}
		}
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