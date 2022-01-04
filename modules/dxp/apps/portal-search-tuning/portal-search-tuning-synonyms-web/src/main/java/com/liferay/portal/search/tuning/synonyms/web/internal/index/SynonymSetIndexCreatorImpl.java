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

package com.liferay.portal.search.tuning.synonyms.web.internal.index;

import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.search.engine.adapter.SearchEngineAdapter;
import com.liferay.portal.search.engine.adapter.index.CreateIndexRequest;
import com.liferay.portal.search.engine.adapter.index.DeleteIndexRequest;
import com.liferay.portal.search.tuning.synonyms.index.name.SynonymSetIndexName;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Adam Brandizzi
 */
@Component(service = SynonymSetIndexCreator.class)
public class SynonymSetIndexCreatorImpl implements SynonymSetIndexCreator {

	@Override
	public void create(SynonymSetIndexName synonymSetIndexName) {
		CreateIndexRequest createIndexRequest = new CreateIndexRequest(
			synonymSetIndexName.getIndexName());

		createIndexRequest.setSource(_readIndexSettings());

		_searchEngineAdapter.execute(createIndexRequest);
	}

	@Override
	public void delete(SynonymSetIndexName synonymSetIndexName) {
		DeleteIndexRequest deleteIndexRequest = new DeleteIndexRequest(
			synonymSetIndexName.getIndexName());

		_searchEngineAdapter.execute(deleteIndexRequest);
	}

	protected static final String INDEX_SETTINGS_RESOURCE_NAME =
		"/META-INF/search/liferay-search-tuning-synonyms-index.json";

	private String _readIndexSettings() {
		return StringUtil.read(getClass(), INDEX_SETTINGS_RESOURCE_NAME);
	}

	@Reference
	private SearchEngineAdapter _searchEngineAdapter;

}