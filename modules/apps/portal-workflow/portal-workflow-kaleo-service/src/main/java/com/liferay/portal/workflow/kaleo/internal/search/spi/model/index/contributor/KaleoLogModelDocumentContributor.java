/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.workflow.kaleo.internal.search.spi.model.index.contributor;

import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.search.spi.model.index.contributor.ModelDocumentContributor;
import com.liferay.portal.workflow.kaleo.model.KaleoLog;

import org.osgi.service.component.annotations.Component;

/**
 * @author Rafael Praxedes
 */
@Component(
	property = "indexer.class.name=com.liferay.portal.workflow.kaleo.model.KaleoLog",
	service = ModelDocumentContributor.class
)
public class KaleoLogModelDocumentContributor
	implements ModelDocumentContributor<KaleoLog> {

	@Override
	public void contribute(Document document, KaleoLog kaleoLog) {
		document.addKeyword("comment", kaleoLog.getComment());
		document.addKeyword(
			"currentAssigneeClassName", kaleoLog.getCurrentAssigneeClassName());
		document.addKeyword(
			"currentAssigneeClassPK", kaleoLog.getCurrentAssigneeClassPK());
		document.addNumberSortable("duration", kaleoLog.getDuration());
		document.addDateSortable("endDate", kaleoLog.getEndDate());
		document.addKeyword(
			"kaleoActionClassName", kaleoLog.getKaleoClassName());
		document.addKeyword("kaleoActionClassPK", kaleoLog.getKaleoClassPK());
		document.addKeyword(
			"kaleoActionDescription", kaleoLog.getKaleoActionDescription());
		document.addKeyword("kaleoActionId", kaleoLog.getKaleoActionId());
		document.addKeyword("kaleoActionName", kaleoLog.getKaleoActionName());
		document.addKeyword(
			"kaleoDefinitionVersionId", kaleoLog.getKaleoDefinitionVersionId());
		document.addKeyword("kaleoInstanceId", kaleoLog.getKaleoInstanceId());
		document.addKeyword(
			"kaleoInstanceTokenId", kaleoLog.getKaleoInstanceTokenId());
		document.addNumberSortable("kaleoLogId", kaleoLog.getKaleoLogId());
		document.addKeyword("kaleoNodeName", kaleoLog.getKaleoNodeName());
		document.addKeyword(
			"kaleoTaskInstanceTokenId", kaleoLog.getKaleoTaskInstanceTokenId());
		document.addKeyword(
			"previousAssigneeClassName",
			kaleoLog.getPreviousAssigneeClassName());
		document.addKeyword(
			"previousAssigneeClassPK", kaleoLog.getPreviousAssigneeClassPK());
		document.addKeyword(
			"previousKaleoNodeId", kaleoLog.getPreviousKaleoNodeId());
		document.addKeyword(
			"previousKaleoNodeName", kaleoLog.getPreviousKaleoNodeName());
		document.addDateSortable("startDate", kaleoLog.getStartDate());
		document.addKeyword(
			"terminalKaleoNode", kaleoLog.isTerminalKaleoNode());
		document.addKeyword("type", kaleoLog.getType());
	}

}