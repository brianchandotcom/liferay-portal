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

package com.liferay.commerce.machine.learning.internal.search.index.helper;

import com.liferay.portal.kernel.json.JSONException;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.search.engine.adapter.SearchEngineAdapter;
import com.liferay.portal.search.engine.adapter.index.CreateIndexRequest;
import com.liferay.portal.search.engine.adapter.index.DeleteIndexRequest;
import com.liferay.portal.search.engine.adapter.index.IndicesExistsIndexRequest;
import com.liferay.portal.search.engine.adapter.index.IndicesExistsIndexResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Riccardo Ferrari
 */
@Component(
	enabled = false, immediate = true,
	service = CommerceMLSearchEngineHelper.class
)
public class CommerceMLSearchEngineHelper {

	public void createIndex(String indexName, String indexMappingFileName) {
		if (_indicesExists(indexName)) {
			if (_log.isDebugEnabled()) {
				_log.debug(String.format("Index %s already exist", indexName));
			}

			return;
		}

		CreateIndexRequest createIndexRequest = new CreateIndexRequest(
			indexName);

		try {
			createIndexRequest.setSource(
				JSONUtil.put(
					"mappings",
					JSONFactoryUtil.createJSONObject(
						StringUtil.read(getClass(), indexMappingFileName))
				).put(
					"settings",
					JSONFactoryUtil.createJSONObject(
						StringUtil.read(
							getClass(), "/META-INF/search/settings.json"))
				).toString());
		}
		catch (JSONException jsonException) {
			_log.error(jsonException, jsonException);
		}

		searchEngineAdapter.execute(createIndexRequest);

		if (_log.isDebugEnabled()) {
			_log.debug(
				String.format("Index %s created successfully", indexName));
		}
	}

	public void dropIndex(String indexName) {
		if (!_indicesExists(indexName)) {
			if (_log.isDebugEnabled()) {
				_log.debug(String.format("Index %s does not exist", indexName));
			}

			return;
		}

		DeleteIndexRequest deleteIndexRequest = new DeleteIndexRequest(
			indexName);

		searchEngineAdapter.execute(deleteIndexRequest);

		if (_log.isDebugEnabled()) {
			_log.debug(
				String.format("Index %s dropped successfully", indexName));
		}
	}

	@Reference
	protected SearchEngineAdapter searchEngineAdapter;

	private boolean _indicesExists(String indexName) {
		IndicesExistsIndexRequest indicesExistsIndexRequest =
			new IndicesExistsIndexRequest(indexName);

		IndicesExistsIndexResponse indicesExistsIndexResponse =
			searchEngineAdapter.execute(indicesExistsIndexRequest);

		return indicesExistsIndexResponse.isExists();
	}

	private static final Log _log = LogFactoryUtil.getLog(
		CommerceMLSearchEngineHelper.class);

}