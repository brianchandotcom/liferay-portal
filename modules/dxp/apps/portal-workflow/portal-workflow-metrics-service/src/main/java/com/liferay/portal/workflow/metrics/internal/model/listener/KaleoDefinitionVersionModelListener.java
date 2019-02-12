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
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.workflow.kaleo.model.KaleoDefinition;
import com.liferay.portal.workflow.kaleo.model.KaleoDefinitionVersion;
import com.liferay.portal.workflow.metrics.internal.search.index.WorkflowMetricsProcessIndexer;

import java.time.OffsetDateTime;
import java.time.ZoneId;

import java.util.Date;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

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

			Date date = kaleoDefinitionVersion.getCreateDate();

			OffsetDateTime offsetDateTime = OffsetDateTime.ofInstant(
				date.toInstant(), ZoneId.systemDefault());

			document.addKeyword(
				Field.getSortableFieldName("date"), offsetDateTime.toString());

			_workflowMetricsProcessIndexer.index(document);
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

			OffsetDateTime offsetDateTime = OffsetDateTime.now();

			document.addKeyword(
				Field.getSortableFieldName("date"), offsetDateTime.toString());

			document.addKeyword("deleted", true);

			_workflowMetricsProcessIndexer.update(document);
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

			Date date = kaleoDefinitionVersion.getModifiedDate();

			OffsetDateTime offsetDateTime = OffsetDateTime.ofInstant(
				date.toInstant(), ZoneId.systemDefault());

			document.addKeyword(
				Field.getSortableFieldName("date"), offsetDateTime.toString());

			_workflowMetricsProcessIndexer.update(document);
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
		document.addKeyword("version", kaleoDefinitionVersion.getVersion());
		document.addKeyword("userId", kaleoDefinitionVersion.getUserId());

		document.addUID(
			"WorkflowMetricsProcess",
			digest(
				kaleoDefinitionVersion.getCompanyId(),
				kaleoDefinitionVersion.getKaleoDefinitionVersionId()));

		return document;
	}

	@Reference
	private WorkflowMetricsProcessIndexer _workflowMetricsProcessIndexer;

}