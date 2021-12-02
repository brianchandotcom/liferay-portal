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

package com.liferay.search.experiences.internal.instance.lifecycle;

import com.liferay.portal.instance.lifecycle.BasePortalInstanceLifecycleListener;
import com.liferay.portal.instance.lifecycle.PortalInstanceLifecycleListener;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.search.document.Document;
import com.liferay.portal.search.engine.adapter.SearchEngineAdapter;
import com.liferay.portal.search.engine.adapter.search.SearchSearchRequest;
import com.liferay.portal.search.engine.adapter.search.SearchSearchResponse;
import com.liferay.portal.search.hits.SearchHit;
import com.liferay.portal.search.hits.SearchHits;
import com.liferay.portal.search.index.IndexNameBuilder;
import com.liferay.portal.search.query.BooleanQuery;
import com.liferay.portal.search.query.Queries;
import com.liferay.search.experiences.internal.storage.SystemSXPElementStorage;
import com.liferay.search.experiences.model.SXPElement;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author André de Oliveira
 */
@Component(
	enabled = true, immediate = true,
	service = PortalInstanceLifecycleListener.class
)
public class SXPPortalInstanceLifecycleListener
	extends BasePortalInstanceLifecycleListener {

	@Override
	public void portalInstanceRegistered(Company company) throws Exception {
		List<SXPElement> systemSXPElements =
			SystemSXPElementStorage.getSXPElements();

		List<Long> companySystemSXPElementIds =
			_getIndexedCompanySystemSXPElementIds(company.getCompanyId());

		if (companySystemSXPElementIds.size() != systemSXPElements.size()) {

			// TODO reindex

		}
	}

	private List<Long> _getIndexedCompanySystemSXPElementIds(long companyId) {
		BooleanQuery booleanQuery = _queries.booleanQuery();

		SearchSearchRequest searchSearchRequest = new SearchSearchRequest();

		searchSearchRequest.setFetchSourceIncludes(
			new String[] {Field.ENTRY_CLASS_PK});
		searchSearchRequest.setIndexNames(
			_indexNameBuilder.getIndexName(companyId));
		searchSearchRequest.setPreferLocalCluster(false);

		booleanQuery.addMustQueryClauses(
			_queries.term("readonly", true),
			_queries.term(Field.ENTRY_CLASS_NAME, SXPElement.class.getName()));

		searchSearchRequest.setQuery(booleanQuery);
		searchSearchRequest.setSize(200);

		SearchSearchResponse searchSearchResponse =
			_searchEngineAdapter.execute(searchSearchRequest);

		SearchHits searchHits = searchSearchResponse.getSearchHits();

		List<SearchHit> hits = searchHits.getSearchHits();

		if (hits.isEmpty()) {
			return Collections.emptyList();
		}

		List<Long> ids = new ArrayList<>();

		hits.forEach(
			searchHit -> {
				Document document = searchHit.getDocument();

				ids.add(document.getLong(Field.ENTRY_CLASS_PK));
			});

		return ids;
	}

	@Reference
	private IndexNameBuilder _indexNameBuilder;

	@Reference
	private Queries _queries;

	@Reference
	private SearchEngineAdapter _searchEngineAdapter;

}