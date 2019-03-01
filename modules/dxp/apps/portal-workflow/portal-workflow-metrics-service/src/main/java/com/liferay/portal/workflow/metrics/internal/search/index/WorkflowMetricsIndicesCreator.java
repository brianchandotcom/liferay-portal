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

package com.liferay.portal.workflow.metrics.internal.search.index;

import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.module.framework.ModuleServiceLifecycle;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.TextFormatter;
import com.liferay.portal.search.engine.adapter.SearchEngineAdapter;
import com.liferay.portal.search.engine.adapter.index.CreateIndexRequest;
import com.liferay.portal.search.engine.adapter.index.IndicesExistsIndexRequest;
import com.liferay.portal.search.engine.adapter.index.IndicesExistsIndexResponse;

import java.util.Iterator;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Inácio Nery
 */
@Component(immediate = true, service = WorkflowMetricsIndicesCreator.class)
public class WorkflowMetricsIndicesCreator {

	@Activate
	protected void activate() throws Exception {
		JSONObject jsonObject = JSONFactoryUtil.createJSONObject(
			StringUtil.read(getClass(), "/META-INF/search/mappings.json"));

		Iterator<String> iterator = jsonObject.keys();

		while (iterator.hasNext()) {
			String indexType = iterator.next();

			String indexName = _getIndexName(indexType);

			IndicesExistsIndexRequest indicesExistsIndexRequest =
				new IndicesExistsIndexRequest(indexName);

			IndicesExistsIndexResponse indicesExistsIndexResponse =
				_searchEngineAdapter.execute(indicesExistsIndexRequest);

			if (indicesExistsIndexResponse.isExists()) {
				continue;
			}

			CreateIndexRequest createIndexRequest = new CreateIndexRequest(
				indexName);

			createIndexRequest.setSource(
				JSONUtil.put(
					"mappings",
					JSONUtil.put(indexType, jsonObject.getJSONObject(indexType))
				).toString());

			_searchEngineAdapter.execute(createIndexRequest);
		}
	}

	@Reference(target = ModuleServiceLifecycle.PORTAL_INITIALIZED, unbind = "-")
	protected void setModuleServiceLifecycle(
		ModuleServiceLifecycle moduleServiceLifecycle) {
	}

	private String _getIndexName(String key) {

		// WorkflowMetricsInstanceType to WorkflowMetricsInstance

		String indexName = key.substring(0, key.length() - 4);

		// WorkflowMetricsInstance to WorkflowMetricsInstances

		indexName = TextFormatter.formatPlural(indexName);

		// WorkflowMetricsInstances to workflow-metrics-instances

		return TextFormatter.format(indexName, TextFormatter.K);
	}

	@Reference
	private SearchEngineAdapter _searchEngineAdapter;

}