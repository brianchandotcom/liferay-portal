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

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.osgi.service.component.annotations.Component;

/**
 * @author André de Oliveira
 */
@Component(service = RankingCriteriaBuilderFactory.class)
public class RankingCriteriaBuilderFactoryImpl
	implements RankingCriteriaBuilderFactory {

	@Override
	public RankingCriteriaBuilder builder() {
		return new RankingCriteriaBuilderImpl();
	}

	public static class RankingCriteriaBuilderImpl
		implements RankingCriteriaBuilder {

		@Override
		public RankingCriteriaBuilder aliases(String... aliases) {
			_rankingCriteriaImpl._aliases.clear();

			Collections.addAll(_rankingCriteriaImpl._aliases, aliases);

			return this;
		}

		@Override
		public RankingCriteria build() {
			return new RankingCriteriaImpl(_rankingCriteriaImpl);
		}

		@Override
		public RankingCriteriaBuilder id(String id) {
			_rankingCriteriaImpl._id = id;

			return this;
		}

		@Override
		public RankingCriteriaBuilder index(String index) {
			_rankingCriteriaImpl._index = index;

			return this;
		}

		@Override
		public RankingCriteriaBuilder queryString(String queryString) {
			_rankingCriteriaImpl._queryString = queryString;

			return this;
		}

		private final RankingCriteriaImpl _rankingCriteriaImpl =
			new RankingCriteriaImpl();

	}

	public static class RankingCriteriaImpl implements RankingCriteria {

		public RankingCriteriaImpl() {
		}

		public RankingCriteriaImpl(RankingCriteriaImpl rankingCriteriaImpl) {
			_index = rankingCriteriaImpl._index;
			_id = rankingCriteriaImpl._id;
			_queryString = rankingCriteriaImpl._queryString;
			_aliases = new ArrayList<>(rankingCriteriaImpl._aliases);
		}

		@Override
		public List<String> getAliases() {
			return Collections.unmodifiableList(_aliases);
		}

		@Override
		public String getId() {
			return _id;
		}

		@Override
		public String getIndex() {
			return _index;
		}

		@Override
		public String getQueryString() {
			return _queryString;
		}

		private List _aliases = new ArrayList<>();
		private String _id;
		private String _index;
		private String _queryString;

	}

}