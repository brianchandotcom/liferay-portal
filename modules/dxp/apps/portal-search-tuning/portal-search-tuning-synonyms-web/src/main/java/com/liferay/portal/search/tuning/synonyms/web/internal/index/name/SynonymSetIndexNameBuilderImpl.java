/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.tuning.synonyms.web.internal.index.name;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.search.index.IndexNameBuilder;
import com.liferay.portal.search.tuning.synonyms.index.name.SynonymSetIndexName;
import com.liferay.portal.search.tuning.synonyms.index.name.SynonymSetIndexNameBuilder;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Adam Brandizzi
 */
@Component(service = SynonymSetIndexNameBuilder.class)
public class SynonymSetIndexNameBuilderImpl
	implements SynonymSetIndexNameBuilder {

	@Override
	public SynonymSetIndexName getSynonymSetIndexName(long companyId) {
		return new SynonymSetIndexNameImpl(
			_indexNameBuilder.getIndexName(companyId) + StringPool.DASH +
				SYNONYMS_INDEX_NAME_SUFFIX);
	}

	protected static final String SYNONYMS_INDEX_NAME_SUFFIX =
		"search-tuning-synonyms";

	@Reference
	private IndexNameBuilder _indexNameBuilder;

	private static class SynonymSetIndexNameImpl
		implements SynonymSetIndexName {

		public SynonymSetIndexNameImpl(String indexName) {
			_indexName = indexName;
		}

		@Override
		public String getIndexName() {
			return _indexName;
		}

		private final String _indexName;

	}

}