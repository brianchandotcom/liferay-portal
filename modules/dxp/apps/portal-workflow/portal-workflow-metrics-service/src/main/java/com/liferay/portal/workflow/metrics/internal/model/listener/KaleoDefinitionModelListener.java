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

package com.liferay.portal.workflow.metrics.internal.model.listener;

import com.liferay.portal.kernel.exception.ModelListenerException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.ModelListener;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.DocumentImpl;
import com.liferay.portal.workflow.kaleo.model.KaleoDefinition;

import org.osgi.service.component.annotations.Component;

/**
 * @author Inácio Nery
 */
@Component(immediate = true, service = ModelListener.class)
public class KaleoDefinitionModelListener
	extends BaseKaleoModelListener<KaleoDefinition> {

	@Override
	public void onAfterCreate(KaleoDefinition kaleoDefinition)
		throws ModelListenerException {

		try {
			addDocument(kaleoDefinition);
		}
		catch (Exception e) {
			_log.error(e, e);
		}
	}

	@Override
	public void onAfterRemove(KaleoDefinition kaleoDefinition)
		throws ModelListenerException {

		try {
			deleteDocument(kaleoDefinition);
		}
		catch (Exception e) {
			_log.error(e, e);
		}
	}

	@Override
	public void onAfterUpdate(KaleoDefinition kaleoDefinition)
		throws ModelListenerException {

		try {
			updateDocument(kaleoDefinition);
		}
		catch (Exception e) {
			_log.error(e, e);
		}
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
		document.addLocalizedText("title", kaleoDefinition.getTitleMap());
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

	private static final Log _log = LogFactoryUtil.getLog(
		KaleoDefinitionModelListener.class);

}