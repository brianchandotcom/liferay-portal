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

import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.json.JSONException;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.module.framework.ModuleServiceLifecycle;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.DocumentImpl;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.TextFormatter;
import com.liferay.portal.search.engine.adapter.SearchEngineAdapter;
import com.liferay.portal.search.engine.adapter.document.IndexDocumentRequest;
import com.liferay.portal.search.engine.adapter.index.CreateIndexRequest;
import com.liferay.portal.search.engine.adapter.index.IndicesExistsIndexRequest;
import com.liferay.portal.search.engine.adapter.index.IndicesExistsIndexResponse;
import com.liferay.portal.workflow.kaleo.model.KaleoDefinition;
import com.liferay.portal.workflow.kaleo.model.KaleoDefinitionVersion;
import com.liferay.portal.workflow.kaleo.model.KaleoInstance;
import com.liferay.portal.workflow.kaleo.model.KaleoTask;
import com.liferay.portal.workflow.kaleo.model.KaleoTaskInstanceToken;
import com.liferay.portal.workflow.kaleo.service.KaleoDefinitionLocalService;
import com.liferay.portal.workflow.kaleo.service.KaleoDefinitionVersionLocalService;
import com.liferay.portal.workflow.kaleo.service.KaleoInstanceLocalService;
import com.liferay.portal.workflow.kaleo.service.KaleoTaskInstanceTokenLocalService;
import com.liferay.portal.workflow.kaleo.service.KaleoTaskLocalService;

import java.io.Serializable;

import java.time.Duration;

import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.codec.digest.DigestUtils;

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
		createIndices();

		populateWorkflowMetricsInstance();
		populateWorkflowMetricsProcess();
		populateWorkflowMetricsTask();
		populateWorkflowMetricsToken();
	}

	protected void addDocument(
		String indexName, String indexType, Document document) {

		IndexDocumentRequest indexDocumentRequest = new IndexDocumentRequest(
			indexName, document);

		indexDocumentRequest.setType(indexType);

		_searchEngineAdapter.execute(indexDocumentRequest);
	}

	protected Document createDocument(KaleoDefinition kaleoDefinition) {
		Document document = new DocumentImpl();

		document.addUID(
			"WorkflowMetricsProcess",
			digest(
				kaleoDefinition.getCompanyId(),
				kaleoDefinition.getKaleoDefinitionId()));

		document.addKeyword("active", kaleoDefinition.isActive());
		document.addKeyword("companyId", kaleoDefinition.getCompanyId());
		document.addDateSortable("createDate", kaleoDefinition.getCreateDate());
		document.addKeyword("deleted", false);
		document.addText("description", kaleoDefinition.getDescription());
		document.addDateSortable(
			"modifiedDate", kaleoDefinition.getModifiedDate());
		document.addKeyword("name", kaleoDefinition.getName());
		document.addKeyword(
			"processId", kaleoDefinition.getKaleoDefinitionId());
		document.addLocalizedText("title", kaleoDefinition.getTitleMap());
		document.addKeyword("userId", kaleoDefinition.getUserId());
		document.addKeyword("version", kaleoDefinition.getVersion());

		return document;
	}

	protected Document createDocument(KaleoInstance kaleoInstance) {
		Document document = new DocumentImpl();

		Date createDate = kaleoInstance.getCreateDate();

		document.addUID(
			"InstanceWorkflowMetrics",
			digest(
				kaleoInstance.getCompanyId(),
				kaleoInstance.getKaleoInstanceId(),
				kaleoInstance.getKaleoDefinitionVersionId()));
		document.addKeyword("className", kaleoInstance.getClassName());
		document.addKeyword("classPK", kaleoInstance.getClassPK());
		document.addKeyword("companyId", kaleoInstance.getCompanyId());
		document.addKeyword("completed", kaleoInstance.isCompleted());
		document.addDateSortable("createDate", createDate);
		document.addKeyword("deleted", false);
		document.addKeyword("instanceId", kaleoInstance.getKaleoInstanceId());
		document.addDateSortable(
			"modifiedDate", kaleoInstance.getModifiedDate());

		KaleoDefinitionVersion kaleoDefinitionVersion =
			_kaleoDefinitionVersionLocalService.fetchKaleoDefinitionVersion(
				kaleoInstance.getKaleoDefinitionVersionId());

		if (kaleoDefinitionVersion != null) {
			KaleoDefinition kaleoDefinition =
				kaleoDefinitionVersion.fetchKaleoDefinition();

			if (kaleoDefinition != null) {
				document.addKeyword(
					"processId", kaleoDefinition.getKaleoDefinitionId());
			}
		}

		document.addKeyword("userId", kaleoInstance.getUserId());

		if (kaleoInstance.isCompleted()) {
			Date completionDate = kaleoInstance.getCompletionDate();

			document.addDateSortable("completionDate", completionDate);

			Duration duration = Duration.between(
				createDate.toInstant(), completionDate.toInstant());

			document.addNumber("duration", duration.toMillis());
		}

		return document;
	}

	protected Document createDocument(KaleoTask kaleoTask) {
		Document document = new DocumentImpl();

		document.addUID(
			"TaskWorkflowMetrics",
			digest(
				kaleoTask.getCompanyId(), kaleoTask.getKaleoTaskId(),
				kaleoTask.getKaleoDefinitionVersionId()));
		document.addKeyword("companyId", kaleoTask.getCompanyId());
		document.addDateSortable("createDate", kaleoTask.getCreateDate());
		document.addDateSortable("modifiedDate", kaleoTask.getModifiedDate());
		document.addKeyword("name", kaleoTask.getName());

		KaleoDefinitionVersion kaleoDefinitionVersion =
			_kaleoDefinitionVersionLocalService.fetchKaleoDefinitionVersion(
				kaleoTask.getKaleoDefinitionVersionId());

		if (kaleoDefinitionVersion != null) {
			KaleoDefinition kaleoDefinition =
				kaleoDefinitionVersion.fetchKaleoDefinition();

			if (kaleoDefinition != null) {
				document.addKeyword(
					"processId", kaleoDefinition.getKaleoDefinitionId());
			}
		}

		document.addKeyword("taskId", kaleoTask.getKaleoTaskId());

		return document;
	}

	protected Document createDocument(
		KaleoTaskInstanceToken kaleoTaskInstanceToken) {

		Document document = new DocumentImpl();

		document.addUID(
			"WorkflowMetricsToken",
			digest(
				kaleoTaskInstanceToken.getCompanyId(),
				kaleoTaskInstanceToken.getKaleoInstanceId(),
				kaleoTaskInstanceToken.getKaleoTaskId(),
				kaleoTaskInstanceToken.getKaleoInstanceTokenId(),
				kaleoTaskInstanceToken.getKaleoDefinitionVersionId()));
		document.addKeyword("className", kaleoTaskInstanceToken.getClassName());
		document.addKeyword("classPK", kaleoTaskInstanceToken.getClassPK());
		document.addKeyword("companyId", kaleoTaskInstanceToken.getCompanyId());
		document.addKeyword("completed", kaleoTaskInstanceToken.isCompleted());
		document.addDateSortable(
			"createDate", kaleoTaskInstanceToken.getCreateDate());
		document.addKeyword("deleted", false);
		document.addKeyword(
			"instanceId", kaleoTaskInstanceToken.getKaleoInstanceId());
		document.addDateSortable(
			"modifiedDate", kaleoTaskInstanceToken.getModifiedDate());

		KaleoDefinitionVersion kaleoDefinitionVersion =
			_kaleoDefinitionVersionLocalService.fetchKaleoDefinitionVersion(
				kaleoTaskInstanceToken.getKaleoDefinitionVersionId());

		if (kaleoDefinitionVersion != null) {
			KaleoDefinition kaleoDefinition =
				kaleoDefinitionVersion.fetchKaleoDefinition();

			if (kaleoDefinition != null) {
				document.addKeyword(
					"processId", kaleoDefinition.getKaleoDefinitionId());
			}
		}

		document.addKeyword("taskId", kaleoTaskInstanceToken.getKaleoTaskId());
		document.addKeyword(
			"tokenId", kaleoTaskInstanceToken.getKaleoInstanceTokenId());
		document.addKeyword("userId", kaleoTaskInstanceToken.getUserId());

		if (kaleoTaskInstanceToken.isCompleted()) {
			Date completionDate = kaleoTaskInstanceToken.getCompletionDate();

			document.addDateSortable("completionDate", completionDate);

			Date createDate = kaleoTaskInstanceToken.getCreateDate();

			Duration duration = Duration.between(
				createDate.toInstant(), completionDate.toInstant());

			document.addNumber("duration", duration.toMillis());
		}

		return document;
	}

	protected void createIndices() throws JSONException {
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

	protected String digest(Serializable... parts) {
		StringBuilder sb = new StringBuilder();

		for (Serializable part : parts) {
			sb.append(part);
		}

		return DigestUtils.sha256Hex(sb.toString());
	}

	protected void populateWorkflowMetricsInstance() {
		List<KaleoInstance> kaleoInstances =
			_kaleoInstanceLocalService.getKaleoInstances(
				QueryUtil.ALL_POS, QueryUtil.ALL_POS);

		for (KaleoInstance kaleoInstance : kaleoInstances) {
			addDocument(
				"workflow-metrics-instances", "WorkflowMetricsInstanceType",
				createDocument(kaleoInstance));
		}
	}

	protected void populateWorkflowMetricsProcess() {
		List<KaleoDefinition> kaleoDefinitions =
			_kaleoDefinitionLocalService.getKaleoDefinitions(
				QueryUtil.ALL_POS, QueryUtil.ALL_POS);

		for (KaleoDefinition kaleoDefinition : kaleoDefinitions) {
			addDocument(
				"workflow-metrics-processes", "WorkflowMetricsProcessType",
				createDocument(kaleoDefinition));
		}
	}

	protected void populateWorkflowMetricsTask() {
		List<KaleoTask> kaleoTasks = _kaleoTaskLocalService.getKaleoTasks(
			QueryUtil.ALL_POS, QueryUtil.ALL_POS);

		for (KaleoTask kaleoTask : kaleoTasks) {
			addDocument(
				"workflow-metrics-tasks", "WorkflowMetricsTaskType",
				createDocument(kaleoTask));
		}
	}

	protected void populateWorkflowMetricsToken() {
		List<KaleoTaskInstanceToken> kaleoTaskInstanceTokens =
			_kaleoTaskInstanceTokenLocalService.getKaleoTaskInstanceTokens(
				QueryUtil.ALL_POS, QueryUtil.ALL_POS);

		for (KaleoTaskInstanceToken kaleoTaskInstanceToken :
				kaleoTaskInstanceTokens) {

			addDocument(
				"workflow-metrics-tokens", "WorkflowMetricsTokenType",
				createDocument(kaleoTaskInstanceToken));
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
	private KaleoDefinitionLocalService _kaleoDefinitionLocalService;

	@Reference
	private KaleoDefinitionVersionLocalService
		_kaleoDefinitionVersionLocalService;

	@Reference
	private KaleoInstanceLocalService _kaleoInstanceLocalService;

	@Reference
	private KaleoTaskInstanceTokenLocalService
		_kaleoTaskInstanceTokenLocalService;

	@Reference
	private KaleoTaskLocalService _kaleoTaskLocalService;

	@Reference
	private SearchEngineAdapter _searchEngineAdapter;

}