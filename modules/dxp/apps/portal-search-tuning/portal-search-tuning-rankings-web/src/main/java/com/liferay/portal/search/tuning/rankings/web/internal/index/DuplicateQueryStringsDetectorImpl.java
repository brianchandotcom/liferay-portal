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

package com.liferay.portal.search.tuning.rankings.web.internal.index;

import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.search.engine.adapter.SearchEngineAdapter;
import com.liferay.portal.search.engine.adapter.search.SearchSearchRequest;
import com.liferay.portal.search.engine.adapter.search.SearchSearchResponse;
import com.liferay.portal.search.query.BooleanQuery;
import com.liferay.portal.search.query.IdsQuery;
import com.liferay.portal.search.query.Queries;
import com.liferay.portal.search.query.Query;
import com.liferay.portal.search.query.TermsQuery;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.stream.Stream;

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
	public boolean detect(Criteria criteria) {
		Collection<String> queryStrings = criteria.getQueryStrings();

		if (queryStrings.isEmpty()) {
			return false;
		}

		SearchSearchResponse searchSearchResponse = searchEngineAdapter.execute(
			new SearchSearchRequest() {
				{
					setIndexNames(RankingIndexDefinition.INDEX_NAME);
					setQuery(getCriteriaQuery(criteria));
					setScoreEnabled(false);
					setSize(0);
				}
			});

		if (searchSearchResponse.getCount() > 0) {
			return true;
		}

		return false;
	}

	protected BooleanQuery getCriteriaQuery(Criteria criteria) {
		BooleanQuery booleanQuery = queries.booleanQuery();

		_addQueryClauses(
			booleanQuery::addFilterQueryClauses, getQueryStringsQuery(criteria),
			getIndexQuery(criteria));
		_addQueryClauses(
			booleanQuery::addMustNotQueryClauses,
			queries.term(RankingFields.INACTIVE, true),
			getUnlessRankingIdQuery(criteria));

		return booleanQuery;
	}

	protected Query getIndexQuery(Criteria criteria) {
		if (Validator.isBlank(criteria.getIndex())) {
			return null;
		}

		return queries.term(RankingFields.INDEX, criteria.getIndex());
	}

	protected TermsQuery getQueryStringsQuery(Criteria criteria) {
		TermsQuery termsQuery = queries.terms(
			RankingFields.QUERY_STRINGS_KEYWORD);

		Collection<String> queryStrings = criteria.getQueryStrings();

		termsQuery.addValues(queryStrings.toArray());

		return termsQuery;
	}

	protected IdsQuery getUnlessRankingIdQuery(Criteria criteria) {
		if (Validator.isBlank(criteria.getUnlessRankingId())) {
			return null;
		}

		IdsQuery idsQuery = queries.ids();

		idsQuery.addIds(criteria.getUnlessRankingId());

		return idsQuery;
	}

	@Reference
	protected Queries queries;

	@Reference
	protected SearchEngineAdapter searchEngineAdapter;

	protected static class CriteriaImpl implements Criteria {

		@Override
		public String getIndex() {
			return _index;
		}

		@Override
		public Collection<String> getQueryStrings() {
			return _queryStrings;
		}

		@Override
		public String getUnlessRankingId() {
			return _unlessRankingId;
		}

		protected CriteriaImpl(CriteriaImpl criteriaImpl) {
			if (criteriaImpl == null) {
				return;
			}

			_index = criteriaImpl._index;
			_queryStrings = new HashSet<>(criteriaImpl._queryStrings);
			_unlessRankingId = criteriaImpl._unlessRankingId;
		}

		protected static class BuilderImpl implements Criteria.Builder {

			@Override
			public Criteria build() {
				return new CriteriaImpl(_criteriaImpl);
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
			public BuilderImpl unlessRankingId(String unlessRankingId) {
				_criteriaImpl._unlessRankingId = unlessRankingId;

				return this;
			}

			private final CriteriaImpl _criteriaImpl = new CriteriaImpl(null);

		}

		private String _index;
		private Collection<String> _queryStrings = new HashSet<>();
		private String _unlessRankingId;

	}

	private void _addQueryClauses(Consumer<Query> consumer, Query... queries) {
		Stream.of(
			queries
		).filter(
			Objects::nonNull
		).forEach(
			consumer
		);
	}

}