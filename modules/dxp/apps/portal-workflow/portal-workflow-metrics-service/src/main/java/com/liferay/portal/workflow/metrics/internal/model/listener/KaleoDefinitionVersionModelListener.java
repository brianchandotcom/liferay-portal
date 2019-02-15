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
import com.liferay.portal.kernel.model.ModelListener;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.DocumentImpl;
import com.liferay.portal.workflow.kaleo.model.KaleoDefinition;
import com.liferay.portal.workflow.kaleo.model.KaleoDefinitionVersion;

import org.osgi.service.component.annotations.Component;

/**
 * @author Inácio Nery
 */
@Component(immediate = true, service = ModelListener.class)
public class KaleoDefinitionVersionModelListener
	extends BaseKaleoModelListener<KaleoDefinitionVersion> {

	@Override
	public void onAfterCreate(KaleoDefinitionVersion kaleoDefinitionVersion)
		throws ModelListenerException {

		try {
			Document document = createDocument(kaleoDefinitionVersion);

			addDocument(document, kaleoDefinitionVersion.getCreateDate());
		}
		catch (Exception e) {
			throw new ModelListenerException(e);
		}
	}

	@Override
	public void onAfterRemove(KaleoDefinitionVersion kaleoDefinitionVersion)
		throws ModelListenerException {

		try {
			Document document = createDocument(kaleoDefinitionVersion);

			deleteDocument(document);
		}
		catch (Exception e) {
			throw new ModelListenerException(e);
		}
	}

	@Override
	public void onAfterUpdate(KaleoDefinitionVersion kaleoDefinitionVersion)
		throws ModelListenerException {

		try {
			Document document = createDocument(kaleoDefinitionVersion);

			updateDocument(document, kaleoDefinitionVersion.getModifiedDate());
		}
		catch (Exception e) {
			throw new ModelListenerException(e);
		}
	}

	protected Document createDocument(
		KaleoDefinitionVersion kaleoDefinitionVersion) {

		Document document = new DocumentImpl();

		boolean active = false;

		KaleoDefinition kaleoDefinition =
			kaleoDefinitionVersion.fetchKaleoDefinition();

		if (kaleoDefinition != null) {
			active = kaleoDefinition.isActive();
		}

		document.addKeyword("active", active);
		document.addKeyword("companyId", kaleoDefinitionVersion.getCompanyId());
		document.addText(
			"description", kaleoDefinitionVersion.getDescription());
		document.addText("name", kaleoDefinitionVersion.getName());
		document.addKeyword(
			"processId", kaleoDefinitionVersion.getKaleoDefinitionVersionId());
		document.addLocalizedText(
			"title", kaleoDefinitionVersion.getTitleMap());
		document.addKeyword("userId", kaleoDefinitionVersion.getUserId());
		document.addKeyword("version", kaleoDefinitionVersion.getVersion());
		document.addUID(
			"WorkflowMetricsProcess",
			digest(
				kaleoDefinitionVersion.getCompanyId(),
				kaleoDefinitionVersion.getKaleoDefinitionVersionId()));

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

}