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

package com.liferay.portal.workflow.reports.internal.search.index;

import com.liferay.portal.kernel.module.framework.ModuleServiceLifecycle;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.search.engine.adapter.SearchEngineAdapter;
import com.liferay.portal.search.engine.adapter.index.CreateIndexRequest;
import com.liferay.portal.search.engine.adapter.index.IndicesExistsIndexRequest;
import com.liferay.portal.search.engine.adapter.index.IndicesExistsIndexResponse;
import com.liferay.portal.workflow.reports.internal.constants.WorkflowIndexConstants;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Inácio Nery
 */
@Component(immediate = true, service = WorkflowIndicesCreator.class)
public class WorkflowIndicesCreator {

	@Activate
	protected void activate() throws Exception {
		createIndex(
			WorkflowIndexConstants.INDEX_NAME_WORKFLOW_EVENTS,
			WorkflowIndexConstants.TYPE_MAPPING_FILE_NAME_WORKFLOW_EVENTS);
		createIndex(
			WorkflowIndexConstants.INDEX_NAME_WORKFLOW_INSTANCES,
			WorkflowIndexConstants.TYPE_MAPPING_FILE_NAME_WORKFLOW_INSTANCES);
		createIndex(
			WorkflowIndexConstants.INDEX_NAME_WORKFLOW_PROCESSES,
			WorkflowIndexConstants.TYPE_MAPPING_FILE_NAME_WORKFLOW_PROCESSES);
		createIndex(
			WorkflowIndexConstants.INDEX_NAME_WORKFLOW_TASKS,
			WorkflowIndexConstants.TYPE_MAPPING_FILE_NAME_WORKFLOW_TASKS);
	}

	protected void createIndex(String indexName, String typeMappingFileName) {
		IndicesExistsIndexRequest indicesExistsIndexRequest =
			new IndicesExistsIndexRequest(indexName);

		IndicesExistsIndexResponse indicesExistsIndexResponse =
			_searchEngineAdapter.execute(indicesExistsIndexRequest);

		if (!indicesExistsIndexResponse.isExists()) {
			CreateIndexRequest createIndexRequest = new CreateIndexRequest(
				indexName);

			String source = StringUtil.read(getClass(), typeMappingFileName);

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