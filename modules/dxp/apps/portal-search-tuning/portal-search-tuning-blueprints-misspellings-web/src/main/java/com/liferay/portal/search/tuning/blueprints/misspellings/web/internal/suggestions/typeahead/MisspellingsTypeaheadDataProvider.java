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

package com.liferay.portal.search.tuning.blueprints.misspellings.web.internal.suggestions.typeahead;

import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.search.document.Document;
import com.liferay.portal.search.engine.adapter.SearchEngineAdapter;
import com.liferay.portal.search.engine.adapter.search.SearchSearchRequest;
import com.liferay.portal.search.engine.adapter.search.SearchSearchResponse;
import com.liferay.portal.search.hits.SearchHit;
import com.liferay.portal.search.hits.SearchHits;
import com.liferay.portal.search.query.BooleanQuery;
import com.liferay.portal.search.query.MatchQuery;
import com.liferay.portal.search.query.Queries;
import com.liferay.portal.search.query.Query;
import com.liferay.portal.search.tuning.blueprints.misspellings.configuration.MisspellingsConfiguration;
import com.liferay.portal.search.tuning.blueprints.misspellings.index.name.MisspellingsIndexName;
import com.liferay.portal.search.tuning.blueprints.misspellings.index.name.MisspellingsIndexNameBuilder;
import com.liferay.portal.search.tuning.blueprints.misspellings.web.internal.index.MisspellingSetFields;
import com.liferay.portal.search.tuning.blueprints.misspellings.web.internal.util.MisspellingsQueryHelper;
import com.liferay.portal.search.tuning.blueprints.suggestions.attributes.SuggestionsAttributes;
import com.liferay.portal.search.tuning.blueprints.suggestions.spi.provider.TypeaheadDataProvider;
import com.liferay.portal.search.tuning.blueprints.suggestions.suggestion.Suggestion;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Modified;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Petteri Karttunen
 */
@Component(
	configurationPid = "com.liferay.portal.search.tuning.blueprints.misspellings.configuration.MisspellingsConfiguration",
	immediate = true, property = "name=misspellings",
	service = TypeaheadDataProvider.class
)
public class MisspellingsTypeaheadDataProvider
	implements TypeaheadDataProvider {

	@Override
	public List<Suggestion> getSuggestions(
		SuggestionsAttributes suggestionsAttributes) {

		if (!_misspellingsConfiguration.enableTypeaheadDataProvider()) {
			return new ArrayList<>();
		}

		SearchHits searchHits = _search(
			_getQuery(suggestionsAttributes), suggestionsAttributes);

		if (searchHits.getTotalHits() == 0) {
			return new ArrayList<>();
		}

		return _getResults(searchHits.getSearchHits());
	}

	@Override
	public int getWeight() {
		return _misspellingsConfiguration.typeaheadDataProviderWeight();
	}

	@Activate
	@Modified
	protected void activate(Map<String, Object> properties) {
		_misspellingsConfiguration = ConfigurableUtil.createConfigurable(
			MisspellingsConfiguration.class, properties);
	}

	private void _addSearchClauses(
		BooleanQuery booleanQuery,
		SuggestionsAttributes suggestionsAttributes) {

		MatchQuery matchQuery = _queries.match(
			MisspellingSetFields.MISSPELLINGS,
			suggestionsAttributes.getKeywords());

		matchQuery.setFuzziness(0F);

		booleanQuery.addMustQueryClauses(matchQuery);
	}

	private Query _getQuery(SuggestionsAttributes suggestionsAttributes) {
		BooleanQuery booleanQuery = _queries.booleanQuery();

		_misspellingsQueryHelper.addGroupFilterClause(
			booleanQuery, suggestionsAttributes.getGroupId());

		_misspellingsQueryHelper.addLanguageFilterClause(
			booleanQuery, suggestionsAttributes.getLanguageId());

		_addSearchClauses(booleanQuery, suggestionsAttributes);

		return booleanQuery;
	}

	private List<Suggestion> _getResults(List<SearchHit> searchHits) {
		List<Suggestion> suggestions = new ArrayList<>();

		Stream<SearchHit> stream = searchHits.stream();

		stream.forEach(
			searchHit -> {
				Document document = searchHit.getDocument();

				suggestions.add(
					new Suggestion(
						document.getString(MisspellingSetFields.PHRASE),
						searchHit.getScore(), "misspellings"));
			});

		return suggestions;
	}

	private SearchHits _search(
		Query query, SuggestionsAttributes suggestionsAttributes) {

		SearchSearchRequest searchSearchRequest = new SearchSearchRequest();

		MisspellingsIndexName misspellingsIndexName =
			_misspellingsIndexNameBuilder.getMisspellingsIndexName(
				suggestionsAttributes.getCompanyId());

		searchSearchRequest.setFetchSource(true);
		searchSearchRequest.setIndexNames(misspellingsIndexName.getIndexName());
		searchSearchRequest.setPreferLocalCluster(false);
		searchSearchRequest.setQuery(query);
		searchSearchRequest.setSize(suggestionsAttributes.getSize());
		searchSearchRequest.setStart(0);

		SearchSearchResponse searchSearchResponse =
			_searchEngineAdapter.execute(searchSearchRequest);

		return searchSearchResponse.getSearchHits();
	}

	private volatile MisspellingsConfiguration _misspellingsConfiguration;

	@Reference
	private MisspellingsIndexNameBuilder _misspellingsIndexNameBuilder;

	@Reference
	private MisspellingsQueryHelper _misspellingsQueryHelper;

	@Reference
	private Queries _queries;

	@Reference
	private SearchEngineAdapter _searchEngineAdapter;

}