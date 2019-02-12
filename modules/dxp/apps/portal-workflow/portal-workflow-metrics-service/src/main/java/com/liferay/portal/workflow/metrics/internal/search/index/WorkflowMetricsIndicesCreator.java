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

import com.liferay.portal.kernel.module.framework.ModuleServiceLifecycle;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.search.engine.adapter.SearchEngineAdapter;
import com.liferay.portal.search.engine.adapter.index.CreateIndexRequest;
import com.liferay.portal.search.engine.adapter.index.IndicesExistsIndexRequest;
import com.liferay.portal.search.engine.adapter.index.IndicesExistsIndexResponse;

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
		createIndex(
			"workflow-metrics-instances",
			"workflow-metrics-instances-type-mappings.json");
		createIndex(
			"workflow-metrics-processes",
			"workflow-metrics-processes-type-mappings.json");
		createIndex(
			"workflow-metrics-tasks",
			"workflow-metrics-tasks-type-mappings.json");
		createIndex(
			"workflow-metrics-tokens",
			"workflow-metrics-tokens-type-mappings.json");
	}

	protected void createIndex(String indexName, String typeMappingFileName) {
		IndicesExistsIndexRequest indicesExistsIndexRequest =
			new IndicesExistsIndexRequest(indexName);

		IndicesExistsIndexResponse indicesExistsIndexResponse =
			_searchEngineAdapter.execute(indicesExistsIndexRequest);

		if (!indicesExistsIndexResponse.isExists()) {
			CreateIndexRequest createIndexRequest = new CreateIndexRequest(
				indexName);

			String source = StringUtil.read(
				getClass(), "/META-INF/mappings/" + typeMappingFileName);

			createIndexRequest.setSource(source);

			_searchEngineAdapter.execute(createIndexRequest);
		}
	}

	@Reference(target = ModuleServiceLifecycle.PORTAL_INITIALIZED, unbind = "-")
	protected void setModuleServiceLifecycle(
		ModuleServiceLifecycle moduleServiceLifecycle) {
	}

	@Reference
	private SearchEngineAdapter _searchEngineAdapter;

}