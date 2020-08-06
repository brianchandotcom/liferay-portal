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

package com.liferay.portal.search.tuning.synonyms.web.internal.index.name;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.search.index.IndexNameBuilder;

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

	@Reference(unbind = "-")
	protected void setIndexNameBuilder(IndexNameBuilder indexNameBuilder) {
		_indexNameBuilder = indexNameBuilder;
	}

	protected static final String SYNONYMS_INDEX_NAME_SUFFIX =
		"search-tuning-synonyms";

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