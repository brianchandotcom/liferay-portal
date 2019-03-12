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

import com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.DocumentImpl;
import com.liferay.portal.search.engine.adapter.document.BulkDocumentRequest;
import com.liferay.portal.search.engine.adapter.document.IndexDocumentRequest;
import com.liferay.portal.workflow.kaleo.model.KaleoDefinition;
import com.liferay.portal.workflow.kaleo.service.KaleoDefinitionLocalService;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Inácio Nery
 */
@Component(immediate = true, service = ProcessWorkflowMetricsIndexer.class)
public class ProcessWorkflowMetricsIndexer
	extends BaseWorkflowMetricsIndexer<KaleoDefinition> {

	@Override
	public void addDocument(KaleoDefinition kaleoDefinition) {
		BulkDocumentRequest bulkDocumentRequest = new BulkDocumentRequest();

		IndexDocumentRequest processIndexDocumentRequest =
			new IndexDocumentRequest(
				getIndexName(), createDocument(kaleoDefinition));

		processIndexDocumentRequest.setType(getIndexType());

		bulkDocumentRequest.addBulkableDocumentRequest(
			processIndexDocumentRequest);

		IndexDocumentRequest instanceIndexDocumentRequest =
			new IndexDocumentRequest(
				_instanceWorkflowMetricsIndexer.getIndexName(),
				_createWorkflowMetricsInstanceDocument(kaleoDefinition));

		instanceIndexDocumentRequest.setType(
			_instanceWorkflowMetricsIndexer.getIndexType());

		bulkDocumentRequest.addBulkableDocumentRequest(
			instanceIndexDocumentRequest);

		searchEngineAdapter.execute(bulkDocumentRequest);
	}

	@Activate
	@Override
	protected void activate() throws Exception {
		super.activate();

		_instanceWorkflowMetricsIndexer.createIndex();
	}

	@Override
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
		document.addLocalizedKeyword(
			"title", kaleoDefinition.getTitleMap(), false, true);
		document.addKeyword("userId", kaleoDefinition.getUserId());
		document.addKeyword("version", kaleoDefinition.getVersion());

		return document;
	}

	@Override
	protected String getIndexName() {
		return "workflow-metrics-processes";
	}

	@Override
	protected String getIndexType() {
		return "WorkflowMetricsProcessType";
	}

	@Override
	protected void populateIndex() throws PortalException {
		ActionableDynamicQuery actionableDynamicQuery =
			_kaleoDefinitionLocalService.getActionableDynamicQuery();

		actionableDynamicQuery.setPerformActionMethod(
			(KaleoDefinition kaleoDefinition) -> addDocument(kaleoDefinition));

		actionableDynamicQuery.performActions();
	}

	private Document _createWorkflowMetricsInstanceDocument(
		KaleoDefinition kaleoDefinition) {

		Document document = new DocumentImpl();

		document.addUID(
			"WorkflowMetricsInstance",
			digest(
				kaleoDefinition.getCompanyId(),
				kaleoDefinition.getKaleoDefinitionId(), 0));
		document.addKeyword("companyId", kaleoDefinition.getCompanyId());
		document.addKeyword("completed", false);
		document.addDateSortable("createDate", kaleoDefinition.getCreateDate());
		document.addKeyword("deleted", false);
		document.addKeyword("instanceId", 0);
		document.addDateSortable(
			"modifiedDate", kaleoDefinition.getModifiedDate());
		document.addKeyword(
			"processId", kaleoDefinition.getKaleoDefinitionId());
		document.addKeyword("userId", kaleoDefinition.getUserId());

		return document;
	}

	@Reference
	private InstanceWorkflowMetricsIndexer _instanceWorkflowMetricsIndexer;

	@Reference
	private KaleoDefinitionLocalService _kaleoDefinitionLocalService;

}