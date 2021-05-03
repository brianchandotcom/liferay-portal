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

package com.liferay.portal.search.tuning.blueprints.misspellings.web.internal.index;

import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.search.engine.adapter.SearchEngineAdapter;
import com.liferay.portal.search.engine.adapter.index.CreateIndexRequest;
import com.liferay.portal.search.engine.adapter.index.DeleteIndexRequest;
import com.liferay.portal.search.tuning.blueprints.misspellings.index.name.MisspellingsIndexName;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Petteri Karttunen
 */
@Component(service = MisspellingsIndexCreator.class)
public class MisspellingsIndexCreatorImpl implements MisspellingsIndexCreator {

	@Override
	public void create(MisspellingsIndexName misspellingsIndexName) {
		CreateIndexRequest createIndexRequest = new CreateIndexRequest(
			misspellingsIndexName.getIndexName());

		createIndexRequest.setSource(readIndexSettings());

		_searchEngineAdapter.execute(createIndexRequest);
	}

	@Override
	public void delete(MisspellingsIndexName misspellingsIndexName) {
		DeleteIndexRequest deleteIndexRequest = new DeleteIndexRequest(
			misspellingsIndexName.getIndexName());

		_searchEngineAdapter.execute(deleteIndexRequest);
	}

	protected String readIndexSettings() {
		return StringUtil.read(getClass(), INDEX_SETTINGS_RESOURCE_NAME);
	}

	protected static final String INDEX_SETTINGS_RESOURCE_NAME =
		"/META-INF/search/liferay-search-tuning-blueprints-misspellings-" +
			"index.json";

	@Reference
	private SearchEngineAdapter _searchEngineAdapter;

}