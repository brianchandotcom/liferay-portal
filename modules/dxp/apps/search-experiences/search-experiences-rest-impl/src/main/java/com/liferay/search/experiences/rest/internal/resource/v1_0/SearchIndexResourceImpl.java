/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.search.experiences.rest.internal.resource.v1_0;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.search.engine.adapter.SearchEngineAdapter;
import com.liferay.portal.search.engine.adapter.index.GetIndexIndexRequest;
import com.liferay.portal.search.engine.adapter.index.GetIndexIndexResponse;
import com.liferay.portal.search.index.IndexNameBuilder;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.search.experiences.rest.dto.v1_0.SearchIndex;
import com.liferay.search.experiences.rest.resource.v1_0.SearchIndexResource;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ServiceScope;

/**
 * @author Brian Wing Shun Chan
 */
@Component(
	enabled = false,
	properties = "OSGI-INF/liferay/rest/v1_0/search-index.properties",
	scope = ServiceScope.PROTOTYPE, service = SearchIndexResource.class
)
public class SearchIndexResourceImpl extends BaseSearchIndexResourceImpl {

	@Override
	public Page<SearchIndex> getSearchIndexesPage() throws Exception {
		String prefix =
			_indexNameBuilder.getIndexName(contextCompany.getCompanyId()) +
				StringPool.DASH;

		GetIndexIndexResponse getIndexIndexResponse =
			_searchEngineAdapter.execute(
				new GetIndexIndexRequest(prefix + StringPool.STAR));

		return Page.of(
			transformToList(
				getIndexIndexResponse.getIndexNames(),
				indexName -> new SearchIndex() {
					{
						external = false;
						name = indexName.substring(prefix.length());
					}
				}));
	}

	@Reference
	private IndexNameBuilder _indexNameBuilder;

	@Reference
	private SearchEngineAdapter _searchEngineAdapter;

}