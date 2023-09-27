/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.search.experiences.internal.ml.embedding.instance.lifecycle;

import com.liferay.portal.instance.lifecycle.BasePortalInstanceLifecycleListener;
import com.liferay.portal.instance.lifecycle.PortalInstanceLifecycleListener;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONException;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.search.engine.SearchEngineInformation;
import com.liferay.portal.search.engine.adapter.SearchEngineAdapter;
import com.liferay.portal.search.engine.adapter.index.IndicesExistsIndexRequest;
import com.liferay.portal.search.engine.adapter.index.IndicesExistsIndexResponse;
import com.liferay.portal.search.engine.adapter.index.PutMappingIndexRequest;
import com.liferay.portal.search.index.IndexInformation;
import com.liferay.portal.search.index.IndexNameBuilder;

import java.util.Objects;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Petteri Karttunen
 */
@Component(enabled = false, service = PortalInstanceLifecycleListener.class)
public class TextEmbeddingFieldCreationPortalInstanceLifecycleListener
	extends BasePortalInstanceLifecycleListener {

	@Override
	public void portalInstanceRegistered(Company company) throws Exception {
		if (Objects.equals(
				_searchEngineInformation.getVendorString(), "Solr")) {

			return;
		}

		String indexName = _indexNameBuilder.getIndexName(
			company.getCompanyId());

		if (!_isExists(indexName)) {
			return;
		}

		JSONArray jsonArray = _getDynamicTemplatesJSONArray(indexName);

		if (_hasTextEmbeddingDynamicTemplates(jsonArray)) {
			return;
		}

		jsonArray = JSONUtil.concat(
			jsonArray,
			_jsonFactory.createJSONArray(
				StringUtil.read(
					getClass(),
					"dependencies/text-embedding-dynamic-templates.json")));

		_searchEngineAdapter.execute(
			new PutMappingIndexRequest(
				new String[] {
					_indexNameBuilder.getIndexName(company.getCompanyId())
				},
				"_doc",
				JSONUtil.put(
					"dynamic_templates", jsonArray
				).toString()));
	}

	private JSONArray _getDynamicTemplatesJSONArray(String indexName) {
		try {
			return JSONUtil.getValueAsJSONArray(
				_jsonFactory.createJSONObject(
					_indexInformation.getFieldMappings(indexName)),
				"JSONObject/" + indexName, "JSONObject/mappings",
				"JSONArray/dynamic_templates");
		}
		catch (JSONException jsonException) {
			_log.error(jsonException);
		}

		return _jsonFactory.createJSONArray();
	}

	private boolean _hasTextEmbeddingDynamicTemplates(JSONArray jsonArray) {
		for (int i = 0; i < jsonArray.length(); i++) {
			JSONObject jsonObject = jsonArray.getJSONObject(i);

			if (jsonObject.has("template_text_embedding_256")) {
				return true;
			}
		}

		return false;
	}

	private boolean _isExists(String indexName) {
		IndicesExistsIndexRequest indicesExistsIndexRequest =
			new IndicesExistsIndexRequest(indexName);

		indicesExistsIndexRequest.setPreferLocalCluster(false);

		IndicesExistsIndexResponse indicesExistsIndexResponse =
			_searchEngineAdapter.execute(indicesExistsIndexRequest);

		return indicesExistsIndexResponse.isExists();
	}

	private static final Log _log = LogFactoryUtil.getLog(
		TextEmbeddingFieldCreationPortalInstanceLifecycleListener.class);

	@Reference
	private IndexInformation _indexInformation;

	@Reference
	private IndexNameBuilder _indexNameBuilder;

	@Reference
	private JSONFactory _jsonFactory;

	@Reference
	private SearchEngineAdapter _searchEngineAdapter;

	@Reference
	private SearchEngineInformation _searchEngineInformation;

}