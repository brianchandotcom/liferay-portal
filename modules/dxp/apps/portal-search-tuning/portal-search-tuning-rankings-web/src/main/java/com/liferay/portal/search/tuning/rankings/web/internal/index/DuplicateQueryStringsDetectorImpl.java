/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.tuning.rankings.web.internal.index;

import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.search.document.Document;
import com.liferay.portal.search.engine.adapter.SearchEngineAdapter;
import com.liferay.portal.search.engine.adapter.search.SearchSearchRequest;
import com.liferay.portal.search.engine.adapter.search.SearchSearchResponse;
import com.liferay.portal.search.hits.SearchHit;
import com.liferay.portal.search.hits.SearchHits;
import com.liferay.portal.search.query.BooleanQuery;
import com.liferay.portal.search.query.IdsQuery;
import com.liferay.portal.search.query.Queries;
import com.liferay.portal.search.query.Query;
import com.liferay.portal.search.query.TermsQuery;
import com.liferay.portal.search.tuning.rankings.web.internal.index.name.RankingIndexName;
import com.liferay.portal.search.tuning.rankings.web.internal.index.name.RankingIndexNameBuilder;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.function.Consumer;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author André de Oliveira
 */
@Component(service = DuplicateQueryStringsDetector.class)
public class DuplicateQueryStringsDetectorImpl
	implements DuplicateQueryStringsDetector {

	@Override
	public Criteria.Builder builder() {
		return new CriteriaImpl.BuilderImpl();
	}

	@Override
	public List<String> detect(Criteria criteria) {
		Collection<String> queryStrings = criteria.getQueryStrings();

		if (queryStrings.isEmpty()) {
			return Collections.emptyList();
		}

		SearchSearchResponse searchSearchResponse = searchEngineAdapter.execute(
			new SearchSearchRequest() {
				{
					RankingIndexName rankingIndexName =
						criteria.getRankingIndexName();

					setIndexNames(rankingIndexName.getIndexName());

					setQuery(_getCriteriaQuery(criteria));
					setScoreEnabled(false);
				}
			});

		SearchHits searchHits = searchSearchResponse.getSearchHits();

		List<String> duplicateQueryStrings = new ArrayList<>();

		for (SearchHit searchHit : searchHits.getSearchHits()) {
			duplicateQueryStrings.addAll(
				_getDuplicateQueryStrings(searchHit, queryStrings));
		}

		return duplicateQueryStrings;
	}

	@Reference
	protected Queries queries;

	@Reference
	protected RankingIndexNameBuilder rankingIndexNameBuilder;

	@Reference
	protected SearchEngineAdapter searchEngineAdapter;

	protected static class CriteriaImpl implements Criteria {

		@Override
		public String getGroupExternalReferenceCode() {
			return _groupExternalReferenceCode;
		}

		@Override
		public String getIndex() {
			return _index;
		}

		@Override
		public Collection<String> getQueryStrings() {
			return _queryStrings;
		}

		@Override
		public RankingIndexName getRankingIndexName() {
			return _rankingIndexName;
		}

		@Override
		public String getSXPBlueprintExternalReferenceCode() {
			return _sxpBlueprintExternalReferenceCode;
		}

		@Override
		public String getUnlessRankingDocumentId() {
			return _unlessRankingDocumentId;
		}

		protected CriteriaImpl(CriteriaImpl criteriaImpl) {
			if (criteriaImpl == null) {
				return;
			}

			_index = criteriaImpl._index;
			_queryStrings = new HashSet<>(criteriaImpl._queryStrings);
			_rankingIndexName = criteriaImpl._rankingIndexName;
			_unlessRankingDocumentId = criteriaImpl._unlessRankingDocumentId;
		}

		protected static class BuilderImpl implements Criteria.Builder {

			@Override
			public Criteria build() {
				return new CriteriaImpl(_criteriaImpl);
			}

			@Override
			public BuilderImpl groupExternalReferenceCode(
				String groupExternalReferenceCode) {

				_criteriaImpl._groupExternalReferenceCode =
					groupExternalReferenceCode;

				return this;
			}

			@Override
			public BuilderImpl index(String index) {
				_criteriaImpl._index = index;

				return this;
			}

			@Override
			public BuilderImpl queryStrings(Collection<String> queryStrings) {
				if (queryStrings == null) {
					_criteriaImpl._queryStrings = Collections.emptySet();
				}
				else {
					_criteriaImpl._queryStrings = new HashSet<>(queryStrings);
				}

				return this;
			}

			@Override
			public Builder rankingIndexName(RankingIndexName rankingIndexName) {
				_criteriaImpl._rankingIndexName = rankingIndexName;

				return this;
			}

			@Override
			public BuilderImpl sxpBlueprintExternalReferenceCode(
				String sxpBlueprintExternalReferenceCode) {

				_criteriaImpl._sxpBlueprintExternalReferenceCode =
					sxpBlueprintExternalReferenceCode;

				return this;
			}

			@Override
			public BuilderImpl unlessRankingDocumentId(
				String unlessRankingDocumentId) {

				_criteriaImpl._unlessRankingDocumentId =
					unlessRankingDocumentId;

				return this;
			}

			private final CriteriaImpl _criteriaImpl = new CriteriaImpl(null);

		}

		private String _groupExternalReferenceCode;
		private String _index;
		private Collection<String> _queryStrings = new HashSet<>();
		private RankingIndexName _rankingIndexName;
		private String _sxpBlueprintExternalReferenceCode;
		private String _unlessRankingDocumentId;

	}

	private void _addQueryClauses(Consumer<Query> consumer, Query... queries) {
		for (Query query : queries) {
			if (query != null) {
				consumer.accept(query);
			}
		}
	}

	private BooleanQuery _getCriteriaQuery(Criteria criteria) {
		BooleanQuery booleanQuery = queries.booleanQuery();

		_addQueryClauses(
			booleanQuery::addFilterQueryClauses,
			_getGroupExternalReferenceCodeQuery(criteria),
			_getIndexQuery(criteria), _getQueryStringsQuery(criteria),
			_getSXPBlueprintExternalReferenceCodeQuery(criteria));
		_addQueryClauses(
			booleanQuery::addMustNotQueryClauses,
			queries.term(RankingFields.INACTIVE, true),
			_getUnlessRankingIdQuery(criteria));

		return booleanQuery;
	}

	private Collection<String> _getDuplicateQueryStrings(
		SearchHit searchHit, Collection<String> queryStrings) {

		Document document = searchHit.getDocument();

		Collection<String> documentQueryStrings = document.getStrings(
			RankingFields.QUERY_STRINGS);

		documentQueryStrings.retainAll(queryStrings);

		return documentQueryStrings;
	}

	private Query _getGroupExternalReferenceCodeQuery(Criteria criteria) {
		if (Validator.isBlank(criteria.getGroupExternalReferenceCode())) {
			return null;
		}

		return queries.term(
			RankingFields.GROUP_EXTERNAL_REFERENCE_CODE,
			criteria.getGroupExternalReferenceCode());
	}

	private Query _getIndexQuery(Criteria criteria) {
		if (Validator.isBlank(criteria.getIndex())) {
			return null;
		}

		return queries.term(RankingFields.INDEX, criteria.getIndex());
	}

	private TermsQuery _getQueryStringsQuery(Criteria criteria) {
		TermsQuery termsQuery = queries.terms(
			RankingFields.QUERY_STRINGS_KEYWORD);

		Collection<String> queryStrings = criteria.getQueryStrings();

		termsQuery.addValues(queryStrings.toArray());

		return termsQuery;
	}

	private Query _getSXPBlueprintExternalReferenceCodeQuery(
		Criteria criteria) {

		if (Validator.isBlank(
				criteria.getSXPBlueprintExternalReferenceCode())) {

			return null;
		}

		return queries.term(
			RankingFields.SXP_BLUEPRINT_EXTERNAL_REFERENCE_CODE,
			criteria.getSXPBlueprintExternalReferenceCode());
	}

	private IdsQuery _getUnlessRankingIdQuery(Criteria criteria) {
		if (Validator.isBlank(criteria.getUnlessRankingDocumentId())) {
			return null;
		}

		IdsQuery idsQuery = queries.ids();

		idsQuery.addIds(criteria.getUnlessRankingDocumentId());

		return idsQuery;
	}

}