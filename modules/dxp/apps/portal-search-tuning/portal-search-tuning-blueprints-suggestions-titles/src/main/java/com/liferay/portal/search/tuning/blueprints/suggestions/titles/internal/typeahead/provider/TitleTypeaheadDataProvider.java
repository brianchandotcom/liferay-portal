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

package com.liferay.portal.search.tuning.blueprints.suggestions.titles.internal.typeahead.provider;

import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.kernel.change.tracking.CTCollectionThreadLocal;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.search.document.Document;
import com.liferay.portal.search.hits.SearchHit;
import com.liferay.portal.search.hits.SearchHits;
import com.liferay.portal.search.index.IndexNameBuilder;
import com.liferay.portal.search.query.BooleanQuery;
import com.liferay.portal.search.query.MultiMatchQuery;
import com.liferay.portal.search.query.Queries;
import com.liferay.portal.search.query.Query;
import com.liferay.portal.search.searcher.SearchRequestBuilder;
import com.liferay.portal.search.searcher.SearchRequestBuilderFactory;
import com.liferay.portal.search.searcher.SearchResponse;
import com.liferay.portal.search.searcher.Searcher;
import com.liferay.portal.search.tuning.blueprints.suggestions.attributes.SuggestionsAttributes;
import com.liferay.portal.search.tuning.blueprints.suggestions.spi.provider.TypeaheadDataProvider;
import com.liferay.portal.search.tuning.blueprints.suggestions.suggestion.Suggestion;
import com.liferay.portal.search.tuning.blueprints.suggestions.titles.internal.configuration.TitleSuggestionsConfiguration;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Stream;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Modified;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Petteri Karttunen
 */
@Component(
	configurationPid = "com.liferay.portal.search.tuning.blueprints.suggestions.titles.internal.configuration.TitleSuggestionsConfiguration",
	immediate = true, property = "name=titles",
	service = TypeaheadDataProvider.class
)
public class TitleTypeaheadDataProvider implements TypeaheadDataProvider {

	@Override
	public List<Suggestion> getSuggestions(
		SuggestionsAttributes suggestionsAttributes) {

		if (!_titleSuggestionsConfiguration.enableTypeaheadDataProvider()) {
			return new ArrayList<>();
		}

		String[] modelIndexerClassNames = _getModelIndexerClassNames(
			suggestionsAttributes);

		if (modelIndexerClassNames.length == 0) {
			if (_log.isWarnEnabled()) {
				_log.warn("data.provider.titles.entry_class_names are not set");
			}

			return new ArrayList<>();
		}

		SearchHits searchHits = _search(
			_getQuery(suggestionsAttributes), modelIndexerClassNames,
			suggestionsAttributes);

		if (searchHits.getTotalHits() == 0) {
			return new ArrayList<>();
		}

		return _getResults(
			searchHits.getSearchHits(), suggestionsAttributes.getLanguageId());
	}

	@Override
	public int getWeight() {
		return _titleSuggestionsConfiguration.typeaheadDataProviderWeight();
	}

	@Activate
	@Modified
	protected void activate(Map<String, Object> properties) {
		_titleSuggestionsConfiguration = ConfigurableUtil.createConfigurable(
			TitleSuggestionsConfiguration.class, properties);
	}

	private void _addGroupFilterClause(
		BooleanQuery booleanQuery,
		SuggestionsAttributes suggestionsAttributes) {

		long groupId = suggestionsAttributes.getGroupId();

		if (groupId > 0) {
			booleanQuery.addFilterQueryClauses(
				_queries.term(
					Field.SCOPE_GROUP_ID, suggestionsAttributes.getGroupId()));
		}
	}

	private void _addHeadFilterClause(BooleanQuery booleanQuery) {
		BooleanQuery filterQuery = _queries.booleanQuery();

		BooleanQuery mustNotQuery = _queries.booleanQuery();

		mustNotQuery.addMustNotQueryClauses(
			_queries.term(
				"entryClassName", "com.liferay.journal.model.JournalArticle"));

		BooleanQuery mustQuery = _queries.booleanQuery();

		mustQuery.addMustQueryClauses(
			_queries.term(
				"entryClassName", "com.liferay.journal.model.JournalArticle"));

		mustQuery.addMustQueryClauses(_queries.term("head", true));

		filterQuery.addShouldQueryClauses(mustNotQuery, mustQuery);

		booleanQuery.addShouldQueryClauses(filterQuery);
	}

	private void _addHiddenFilterClause(BooleanQuery booleanQuery) {
		BooleanQuery filterQuery = _queries.booleanQuery();

		BooleanQuery mustNotQuery = _queries.booleanQuery();

		mustNotQuery.addMustNotQueryClauses(_queries.exists("hidden"));

		BooleanQuery mustQuery = _queries.booleanQuery();

		mustQuery.addMustQueryClauses(_queries.term("hidden", false));

		filterQuery.addShouldQueryClauses(mustNotQuery, mustQuery);

		booleanQuery.addShouldQueryClauses(filterQuery);
	}

	private void _addPublicationsFilterClause(BooleanQuery booleanQuery) {
		BooleanQuery filterQuery = _queries.booleanQuery();

		BooleanQuery mustNotQuery = _queries.booleanQuery();

		mustNotQuery.addMustNotQueryClauses(_queries.exists("ctCollectionId"));

		BooleanQuery mustQuery = _queries.booleanQuery();

		mustQuery.addMustQueryClauses(
			_queries.term(
				"ctCollectionId", CTCollectionThreadLocal.getCTCollectionId()));

		filterQuery.addShouldQueryClauses(mustNotQuery, mustQuery);

		booleanQuery.addShouldQueryClauses(filterQuery);
	}

	private void _addSchedulingFilterClause(BooleanQuery booleanQuery) {
		Date now = new Date();

		BooleanQuery filterQuery = _queries.booleanQuery();

		BooleanQuery mustNotQuery = _queries.booleanQuery();

		mustNotQuery.addMustNotQueryClauses(_queries.exists("displayDate"));

		BooleanQuery mustQuery1 = _queries.booleanQuery();

		mustQuery1.addMustQueryClauses(
			_queries.rangeTerm(
				"displayDate_sortable", true, true, Long.MIN_VALUE,
				now.getTime()));

		mustQuery1.addMustNotQueryClauses(_queries.exists("expirationDate"));

		BooleanQuery mustQuery2 = _queries.booleanQuery();

		mustQuery2.addMustQueryClauses(
			_queries.rangeTerm(
				"displayDate_sortable", true, true, Long.MIN_VALUE,
				now.getTime()));

		mustQuery2.addMustQueryClauses(
			_queries.rangeTerm(
				"expirationDate_sortable", true, true, now.getTime(),
				Long.MAX_VALUE));

		filterQuery.addShouldQueryClauses(mustNotQuery, mustQuery1, mustQuery2);

		booleanQuery.addShouldQueryClauses(filterQuery);
	}

	private void _addSearchClauses(
		BooleanQuery booleanQuery,
		SuggestionsAttributes suggestionsAttributes) {

		MultiMatchQuery multiMatchQuery = _queries.multiMatch(
			suggestionsAttributes.getKeywords(),
			_getFields(suggestionsAttributes.getLanguageId()));

		multiMatchQuery.setType(MultiMatchQuery.Type.BOOL_PREFIX);

		booleanQuery.addMustQueryClauses(multiMatchQuery);
	}

	private void _addStagingFilterClause(BooleanQuery booleanQuery) {
		BooleanQuery filterQuery = _queries.booleanQuery();

		BooleanQuery mustNotQuery = _queries.booleanQuery();

		mustNotQuery.addMustNotQueryClauses(_queries.exists("staginGroup"));

		BooleanQuery mustQuery = _queries.booleanQuery();

		mustQuery.addMustQueryClauses(_queries.term("stagingGroup", false));

		filterQuery.addShouldQueryClauses(mustNotQuery, mustQuery);

		booleanQuery.addShouldQueryClauses(filterQuery);
	}

	private void _addStatusFilterClause(BooleanQuery booleanQuery) {
		booleanQuery.addFilterQueryClauses(
			_queries.term(Field.STATUS, WorkflowConstants.STATUS_APPROVED));
	}

	private Set<String> _getFields(String languageId) {
		Set<String> fields = new HashSet<>();

		fields.add("localized_title_" + languageId);

		return fields;
	}

	private String[] _getModelIndexerClassNames(
		SuggestionsAttributes suggestionsAttributes) {

		Optional<Object> attributeOptional =
			suggestionsAttributes.getAttributeOptional(
				"data.provider.titles.entry_class_names");

		if (!attributeOptional.isPresent()) {
			return new String[0];
		}

		Object object = attributeOptional.get();

		if (!String[].class.isAssignableFrom(object.getClass())) {
			return new String[0];
		}

		return (String[])object;
	}

	private Query _getQuery(SuggestionsAttributes suggestionsAttributes) {
		BooleanQuery booleanQuery = _queries.booleanQuery();

		_addGroupFilterClause(booleanQuery, suggestionsAttributes);

		_addHeadFilterClause(booleanQuery);

		_addHiddenFilterClause(booleanQuery);

		_addPublicationsFilterClause(booleanQuery);

		_addSchedulingFilterClause(booleanQuery);

		_addStagingFilterClause(booleanQuery);

		_addStatusFilterClause(booleanQuery);

		_addSearchClauses(booleanQuery, suggestionsAttributes);

		return booleanQuery;
	}

	private List<Suggestion> _getResults(
		List<SearchHit> searchHits, String languageId) {

		List<Suggestion> suggestions = new ArrayList<>();

		Stream<SearchHit> stream = searchHits.stream();

		stream.forEach(
			searchHit -> {
				Document document = searchHit.getDocument();

				suggestions.add(
					new Suggestion(
						document.getString("localized_title_" + languageId),
						searchHit.getScore(), "titles"));
			});

		return suggestions;
	}

	private SearchHits _search(
		Query query, String[] modelIndexerClassNames,
		SuggestionsAttributes suggestionsAttributes) {

		SearchRequestBuilder searchRequestBuilder =
			_searchRequestBuilderFactory.builder(
			).companyId(
				suggestionsAttributes.getCompanyId()
			).emptySearchEnabled(
				true
			).excludeContributors(
				"com.liferay.portal.search.tuning.blueprints"
			).locale(
				LocaleUtil.fromLanguageId(suggestionsAttributes.getLanguageId())
			).modelIndexerClassNames(
				modelIndexerClassNames
			).entryClassNames(
				modelIndexerClassNames
			).query(
				query
			).size(
				suggestionsAttributes.getSize()
			).from(
				0
			);

		searchRequestBuilder.withSearchContext(
			searchContext -> searchContext.setAttribute(
				"search.full.query.suppress.indexer.provided.clauses",
				Boolean.TRUE));

		SearchResponse searchResponse = _searcher.search(
			searchRequestBuilder.build());

		return searchResponse.getSearchHits();
	}

	private static final Log _log = LogFactoryUtil.getLog(
		TitleTypeaheadDataProvider.class);

	@Reference
	private IndexNameBuilder _indexNameBuilder;

	@Reference
	private Queries _queries;

	@Reference
	private Searcher _searcher;

	@Reference
	private SearchRequestBuilderFactory _searchRequestBuilderFactory;

	private volatile TitleSuggestionsConfiguration
		_titleSuggestionsConfiguration;

}