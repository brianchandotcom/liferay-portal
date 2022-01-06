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
import com.liferay.portal.workflow.metrics.model.AddProcessRequest;
import com.liferay.portal.workflow.metrics.model.DeleteProcessRequest;
import com.liferay.portal.workflow.metrics.model.UpdateProcessRequest;
import com.liferay.portal.workflow.metrics.search.index.ProcessWorkflowMetricsIndexer;

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
	public Document addProcess(AddProcessRequest addProcessRequest) {
		DocumentBuilder documentBuilder = documentBuilderFactory.builder();

		documentBuilder.setValue(
			"active", addProcessRequest.isActive()
		).setLong(
			"companyId", addProcessRequest.getCompanyId()
		).setDate(
			"createDate", getDate(addProcessRequest.getCreateDate())
		).setValue(
			"deleted", false
		).setString(
			"description", addProcessRequest.getDescription()
		).setDate(
			"modifiedDate", getDate(addProcessRequest.getModifiedDate())
		).setString(
			"name", addProcessRequest.getName()
		).setLong(
			"processId", addProcessRequest.getProcessId()
		).setString(
			"title", addProcessRequest.getTitle()
		).setString(
			"uid",
			digest(
				addProcessRequest.getCompanyId(),
				addProcessRequest.getProcessId())
		).setString(
			"version", addProcessRequest.getVersion()
		).setStrings(
			"versions", addProcessRequest.getVersions()
		);

		setLocalizedField(
			documentBuilder, "title", addProcessRequest.getTitleMap());

		Document document = documentBuilder.build();

		workflowMetricsPortalExecutor.execute(() -> addDocument(document));

		return document;
	}

	@Override
	public void deleteProcess(DeleteProcessRequest deleteProcessRequest) {
		DocumentBuilder documentBuilder = documentBuilderFactory.builder();

		documentBuilder.setLong(
			"companyId", deleteProcessRequest.getCompanyId()
		).setLong(
			"processId", deleteProcessRequest.getProcessId()
		).setString(
			"uid",
			digest(
				deleteProcessRequest.getCompanyId(),
				deleteProcessRequest.getProcessId())
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
	public Document updateProcess(UpdateProcessRequest updateProcessRequest) {
		DocumentBuilder documentBuilder = documentBuilderFactory.builder();

		if (updateProcessRequest.getActive() != null) {
			documentBuilder.setValue(
				"active", updateProcessRequest.getActive());
		}

		documentBuilder.setLong(
			"companyId", updateProcessRequest.getCompanyId());

		if (updateProcessRequest.getDescription() != null) {
			documentBuilder.setValue(
				"description", updateProcessRequest.getDescription());
		}

		documentBuilder.setDate(
			"modifiedDate", getDate(updateProcessRequest.getModifiedDate())
		).setLong(
			"processId", updateProcessRequest.getProcessId()
		);

		if (updateProcessRequest.getTitle() != null) {
			documentBuilder.setValue("title", updateProcessRequest.getTitle());
		}

		documentBuilder.setString(
			"uid",
			digest(
				updateProcessRequest.getCompanyId(),
				updateProcessRequest.getProcessId())
		).setValue(
			"version", updateProcessRequest.getVersion()
		);

		if (MapUtil.isNotEmpty(updateProcessRequest.getTitleMap())) {
			setLocalizedField(
				documentBuilder, "title", updateProcessRequest.getTitleMap());
		}

		Document document = documentBuilder.build();

		workflowMetricsPortalExecutor.execute(
			() -> {
				updateDocument(document);

				ScriptBuilder scriptBuilder = scripts.builder();

				UpdateDocumentRequest updateDocumentRequest =
					new UpdateDocumentRequest(
						getIndexName(updateProcessRequest.getCompanyId()),
						WorkflowMetricsIndexerUtil.digest(
							_processWorkflowMetricsIndex.getIndexType(),
							updateProcessRequest.getCompanyId(),
							updateProcessRequest.getProcessId()),
						scriptBuilder.idOrCode(
							StringUtil.read(
								getClass(),
								"dependencies/workflow-metrics-update-" +
									"process-versions-script.painless")
						).language(
							"painless"
						).putParameter(
							"version", updateProcessRequest.getVersion()
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

		documentBuilder.setValue(
			"active", true
		).setLong(
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