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

import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.PortalRunMode;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.search.document.Document;
import com.liferay.portal.search.document.DocumentBuilder;
import com.liferay.portal.search.engine.adapter.document.BulkDocumentRequest;
import com.liferay.portal.search.engine.adapter.document.IndexDocumentRequest;
import com.liferay.portal.search.engine.adapter.document.UpdateDocumentRequest;
import com.liferay.portal.search.script.ScriptBuilder;
import com.liferay.portal.search.script.ScriptType;
import com.liferay.portal.workflow.metrics.internal.search.index.util.WorkflowMetricsIndexerUtil;
import com.liferay.portal.workflow.metrics.search.index.ProcessWorkflowMetricsIndexer;

import java.util.Date;
import java.util.Locale;
import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Inácio Nery
 */
@Component(immediate = true, service = ProcessWorkflowMetricsIndexer.class)
public class ProcessWorkflowMetricsIndexerImpl
	extends BaseWorkflowMetricsIndexer
	implements ProcessWorkflowMetricsIndexer {

	@Override
	public void addDocument(Document document) {
		if (searchEngineAdapter == null) {
			return;
		}

		BulkDocumentRequest bulkDocumentRequest = new BulkDocumentRequest();

		bulkDocumentRequest.addBulkableDocumentRequest(
			new IndexDocumentRequest(
				_instanceWorkflowMetricsIndex.getIndexName(
					document.getLong("companyId")),
				_createWorkflowMetricsInstanceDocument(
					document.getLong("companyId"),
					document.getLong("processId"))) {

				{
					setType(_instanceWorkflowMetricsIndex.getIndexType());
				}
			});

		bulkDocumentRequest.addBulkableDocumentRequest(
			new IndexDocumentRequest(
				_slaInstanceResultWorkflowMetricsIndexer.getIndexName(
					document.getLong("companyId")),
				_slaInstanceResultWorkflowMetricsIndexer.creatDefaultDocument(
					document.getLong("companyId"),
					document.getLong("processId"))) {

				{
					setType(
						_slaInstanceResultWorkflowMetricsIndexer.
							getIndexType());
				}
			});

		bulkDocumentRequest.addBulkableDocumentRequest(
			new IndexDocumentRequest(
				_processWorkflowMetricsIndex.getIndexName(
					document.getLong("companyId")),
				document) {

				{
					setType(_processWorkflowMetricsIndex.getIndexType());
				}
			});

		if (PortalRunMode.isTestMode()) {
			bulkDocumentRequest.setRefresh(true);
		}

		searchEngineAdapter.execute(bulkDocumentRequest);
	}

	@Override
	public Document addProcess(
		boolean active, long companyId, Date createDate, String description,
		Date modifiedDate, String name, long processId, String title,
		Map<Locale, String> titleMap, String version) {

		return addProcess(
			active, companyId, createDate, description, modifiedDate, name,
			processId, title, titleMap, version, new String[] {version});
	}

	@Override
	public Document addProcess(
		boolean active, long companyId, Date createDate, String description,
		Date modifiedDate, String name, long processId, String title,
		Map<Locale, String> titleMap, String version, String[] versions) {

		DocumentBuilder documentBuilder = documentBuilderFactory.builder();

		documentBuilder.setValue(
			"active", active
		).setLong(
			"companyId", companyId
		).setDate(
			"createDate", getDate(createDate)
		).setValue(
			"deleted", false
		).setString(
			"description", description
		).setDate(
			"modifiedDate", getDate(modifiedDate)
		).setString(
			"name", name
		).setLong(
			"processId", processId
		).setString(
			"title", title
		).setString(
			"uid", digest(companyId, processId)
		).setString(
			"version", version
		).setStrings(
			"versions", versions
		);

		setLocalizedField(documentBuilder, "title", titleMap);

		Document document = documentBuilder.build();

		workflowMetricsPortalExecutor.execute(() -> addDocument(document));

		return document;
	}

	@Override
	public void deleteProcess(long companyId, long processId) {
		DocumentBuilder documentBuilder = documentBuilderFactory.builder();

		documentBuilder.setLong(
			"companyId", companyId
		).setLong(
			"processId", processId
		).setString(
			"uid", digest(companyId, processId)
		);

		workflowMetricsPortalExecutor.execute(
			() -> deleteDocument(documentBuilder));
	}

	@Override
	public String getIndexName(long companyId) {
		return _processWorkflowMetricsIndex.getIndexName(companyId);
	}

	@Override
	public String getIndexType() {
		return _processWorkflowMetricsIndex.getIndexType();
	}

	@Override
	public Document updateProcess(
		Boolean active, long companyId, String description, Date modifiedDate,
		long processId, String title, Map<Locale, String> titleMap,
		String version) {

		DocumentBuilder documentBuilder = documentBuilderFactory.builder();

		if (active != null) {
			documentBuilder.setValue("active", active);
		}

		documentBuilder.setLong("companyId", companyId);

		if (description != null) {
			documentBuilder.setValue("description", description);
		}

		documentBuilder.setDate(
			"modifiedDate", getDate(modifiedDate)
		).setLong(
			"processId", processId
		);

		if (title != null) {
			documentBuilder.setValue("title", title);
		}

		documentBuilder.setString(
			"uid", digest(companyId, processId)
		).setValue(
			"version", version
		);

		if (MapUtil.isNotEmpty(titleMap)) {
			setLocalizedField(documentBuilder, "title", titleMap);
		}

		Document document = documentBuilder.build();

		workflowMetricsPortalExecutor.execute(
			() -> {
				updateDocument(document);

				ScriptBuilder scriptBuilder = scripts.builder();

				UpdateDocumentRequest updateDocumentRequest =
					new UpdateDocumentRequest(
						getIndexName(companyId),
						WorkflowMetricsIndexerUtil.digest(
							_processWorkflowMetricsIndex.getIndexType(),
							companyId, processId),
						scriptBuilder.idOrCode(
							StringUtil.read(
								getClass(),
								"dependencies/workflow-metrics-update-" +
									"process-versions-script.painless")
						).language(
							"painless"
						).putParameter(
							"version", version
						).scriptType(
							ScriptType.INLINE
						).build());

				if (PortalRunMode.isTestMode()) {
					updateDocumentRequest.setRefresh(true);
				}

				searchEngineAdapter.execute(updateDocumentRequest);
			});

		return document;
	}

	private Document _createWorkflowMetricsInstanceDocument(
		long companyId, long processId) {

		DocumentBuilder documentBuilder = documentBuilderFactory.builder();

		documentBuilder.setLong(
			"companyId", companyId
		).setValue(
			"completed", false
		).setValue(
			"deleted", false
		).setLong(
			"instanceId", 0L
		).setLong(
			"processId", processId
		).setString(
			"uid",
			WorkflowMetricsIndexerUtil.digest(
				_instanceWorkflowMetricsIndex.getIndexType(), companyId,
				processId)
		);

		return documentBuilder.build();
	}

	@Reference(target = "(workflow.metrics.index.entity.name=instance)")
	private WorkflowMetricsIndex _instanceWorkflowMetricsIndex;

	@Reference(target = "(workflow.metrics.index.entity.name=process)")
	private WorkflowMetricsIndex _processWorkflowMetricsIndex;

	@Reference
	private SLAInstanceResultWorkflowMetricsIndexer
		_slaInstanceResultWorkflowMetricsIndexer;

}