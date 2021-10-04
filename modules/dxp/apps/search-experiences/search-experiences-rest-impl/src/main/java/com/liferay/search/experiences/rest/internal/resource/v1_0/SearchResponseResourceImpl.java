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

package com.liferay.search.experiences.rest.internal.resource.v1_0;

import com.liferay.portal.search.searcher.SearchRequestBuilderFactory;
import com.liferay.portal.search.searcher.Searcher;
import com.liferay.search.experiences.rest.dto.v1_0.SXPBlueprint;
import com.liferay.search.experiences.rest.dto.v1_0.SearchResponse;
import com.liferay.search.experiences.rest.dto.v1_0.enhancer.SXPBlueprintSearchRequestEnhancer;
import com.liferay.search.experiences.rest.dto.v1_0.translator.SearchResponseTranslator;
import com.liferay.search.experiences.rest.dto.v1_0.util.ConfigurationUtil;
import com.liferay.search.experiences.rest.resource.v1_0.SearchResponseResource;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ServiceScope;

/**
 * @author Brian Wing Shun Chan
 * @author André de Oliveira
 */
@Component(
	properties = "OSGI-INF/liferay/rest/v1_0/search-response.properties",
	scope = ServiceScope.PROTOTYPE, service = SearchResponseResource.class
)
public class SearchResponseResourceImpl extends BaseSearchResponseResourceImpl {

	@Override
	public SearchResponse getSearch(
			Integer delta, String q, Integer start, String sxpBlueprintJSON)
		throws Exception {

		return _searchResponseTranslator.translate(
			_searcher.search(
				_searchRequestBuilderFactory.builder(
				).emptySearchEnabled(
					true
				).includeResponseString(
					true
				).companyId(
					contextCompany.getCompanyId()
				).from(
					_getFrom(delta, start)
				).queryString(
					q
				).size(
					delta
				).withSearchRequestBuilder(
					searchRequestBuilder -> {
						if (sxpBlueprintJSON != null) {
							_sxpBlueprintSearchRequestEnhancer.enhance(
								searchRequestBuilder,
								_toSXPBlueprint(sxpBlueprintJSON));
						}
					}
				).build()));
	}

	private Integer _getFrom(Integer delta, Integer start) {
		if ((delta == null) || (start == null)) {
			return null;
		}

		return start * delta;
	}

	private SXPBlueprint _toSXPBlueprint(String sxpBlueprintJSON) {
		SXPBlueprint sxpBlueprint = SXPBlueprint.toDTO(sxpBlueprintJSON);

		sxpBlueprint.setConfiguration(
			ConfigurationUtil.toConfiguration(
				String.valueOf(sxpBlueprint.getConfiguration())));

		return sxpBlueprint;
	}

	@Reference
	private Searcher _searcher;

	@Reference
	private SearchRequestBuilderFactory _searchRequestBuilderFactory;

	@Reference
	private SearchResponseTranslator _searchResponseTranslator;

	@Reference
	private SXPBlueprintSearchRequestEnhancer
		_sxpBlueprintSearchRequestEnhancer;

}