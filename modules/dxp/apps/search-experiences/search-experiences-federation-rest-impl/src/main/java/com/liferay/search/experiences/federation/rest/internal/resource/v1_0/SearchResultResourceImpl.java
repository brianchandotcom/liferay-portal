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

package com.liferay.search.experiences.federation.rest.internal.resource.v1_0;

import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.search.document.Document;
import com.liferay.portal.search.filter.ComplexQueryPart;
import com.liferay.portal.search.hits.SearchHit;
import com.liferay.portal.search.hits.SearchHits;
import com.liferay.portal.search.searcher.SearchRequest;
import com.liferay.portal.search.searcher.SearchRequestBuilder;
import com.liferay.portal.search.searcher.SearchRequestBuilderFactory;
import com.liferay.portal.search.searcher.SearchResponse;
import com.liferay.portal.search.searcher.Searcher;
import com.liferay.search.experiences.federation.configuration.SearchConfiguration;
import com.liferay.search.experiences.federation.rest.dto.v1_0.Result;
import com.liferay.search.experiences.federation.rest.dto.v1_0.SearchResult;
import com.liferay.search.experiences.federation.rest.resource.v1_0.SearchResultResource;

import java.util.List;
import java.util.Map;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Modified;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ServiceScope;

/**
 * @author Bryan Engler
 */
@Component(
	configurationPid = "com.liferay.search.experiences.federation.configuration.FederationConfiguration",
	enabled = false,
	properties = "OSGI-INF/liferay/rest/v1_0/search-result.properties",
	scope = ServiceScope.PROTOTYPE, service = SearchResultResource.class
)
public class SearchResultResourceImpl extends BaseSearchResultResourceImpl {

	@Override
	public SearchResult getFederatedSearchResults(
			String labels, String locale, String query, Integer size,
			Integer page)
		throws Exception {

		SearchRequestBuilder searchRequestBuilder =
			searchRequestBuilderFactory.builder();

		searchRequestBuilder.from(page * size);
		searchRequestBuilder.indexes("federated-content");
		searchRequestBuilder.size(size);

		searchRequestBuilder.withSearchContext(
			searchContext -> {
				searchContext.setAttribute(
					"search.experiences.blueprint.id",
					searchConfiguration.blueprintId());
				searchContext.setKeywords(query);
			});

		SearchResponse searchResponse = searcher.search(
			searchRequestBuilder.build());

		SearchRequest searchRequest = searchResponse.getRequest();

		List<ComplexQueryPart> complexQueryParts =
			searchRequest.getComplexQueryParts();

		if (complexQueryParts.isEmpty()) {
			return new SearchResult();
		}

		SearchHits searchHits = searchResponse.getSearchHits();

		List<SearchHit> searchHitList = searchHits.getSearchHits();

		return _toSearchResult(searchHitList);
	}

	@Activate
	@Modified
	protected void activate(Map<String, Object> properties) {
		searchConfiguration = ConfigurableUtil.createConfigurable(
			SearchConfiguration.class, properties);
	}

	protected volatile SearchConfiguration searchConfiguration;

	@Reference
	protected Searcher searcher;

	@Reference
	protected SearchRequestBuilderFactory searchRequestBuilderFactory;

	private SearchResult _toSearchResult(List<SearchHit> searchHits)
		throws Exception {

		Result[] restResults = new Result[searchHits.size()];

		for (int i = 0; i < searchHits.size(); i++) {
			SearchHit searchHit = searchHits.get(i);

			Document document = searchHit.getDocument();

			String docTitle = document.getString(Field.TITLE);
			String link = document.getString("link");

			Result restResult = new Result() {
				{
					title = docTitle;
					url = link;
				}
			};

			restResults[i] = restResult;
		}

		return new SearchResult() {
			{
				results = restResults;
			}
		};
	}

}