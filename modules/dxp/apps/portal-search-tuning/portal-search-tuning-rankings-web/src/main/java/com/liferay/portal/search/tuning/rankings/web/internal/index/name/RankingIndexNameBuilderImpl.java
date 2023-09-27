/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.tuning.rankings.web.internal.index.name;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.search.index.IndexNameBuilder;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Wade Cao
 * @author Adam Brandizzi
 */
@Component(service = RankingIndexNameBuilder.class)
public class RankingIndexNameBuilderImpl implements RankingIndexNameBuilder {

	@Override
	public RankingIndexName getRankingIndexName(long companyId) {
		return new RankingIndexNameImpl(
			_indexNameBuilder.getIndexName(companyId) + StringPool.DASH +
				RANKINGS_INDEX_NAME_SUFFIX);
	}

	protected static final String RANKINGS_INDEX_NAME_SUFFIX =
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

	@Reference
	private IndexNameBuilder _indexNameBuilder;

}