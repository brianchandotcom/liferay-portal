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

package com.liferay.portal.search.tuning.rankings.web.internal.index.name;

import com.liferay.petra.string.StringPool;

import org.osgi.service.component.annotations.Component;

/**
 * @author Wade Cao
 * @author Adam Brandizzi
 */
@Component(service = RankingIndexNameBuilder.class)
public class RankingIndexNameBuilderImpl implements RankingIndexNameBuilder {

	@Override
	public RankingIndexName getRankingIndexName(String companyIndexName) {
		return new RankingIndexNameImpl(
			companyIndexName + StringPool.MINUS + RANKINGS_INDEX_NAME);
	}

	protected static final String RANKINGS_INDEX_NAME =
		"search-tuning-rankings";

	protected class RankingIndexNameImpl implements RankingIndexName {

		public RankingIndexNameImpl(String indexName) {
			_indexName = indexName;
		}

		@Override
		public String getIndexName() {
			return _indexName;
		}

		private final String _indexName;

	}

}