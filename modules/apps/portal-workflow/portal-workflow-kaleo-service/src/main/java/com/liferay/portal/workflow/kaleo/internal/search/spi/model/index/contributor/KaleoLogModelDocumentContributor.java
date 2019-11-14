/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.portal.workflow.kaleo.internal.search.spi.model.index.contributor;

import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.search.spi.model.index.contributor.ModelDocumentContributor;
import com.liferay.portal.workflow.kaleo.model.KaleoLog;

import org.osgi.service.component.annotations.Component;

/**
 * @author Rafael Praxedes
 */
@Component(
	immediate = true,
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
			"kaleoActionDescription", kaleoLog.getKaleoActionDescription());
		document.addKeyword("kaleoActionId", kaleoLog.getKaleoActionId());
		document.addKeyword("kaleoActionName", kaleoLog.getKaleoActionName());
		document.addKeyword(
			"kaleoActionClassName", kaleoLog.getKaleoClassName());
		document.addKeyword("kaleoActionClassPK", kaleoLog.getKaleoClassPK());
		document.addKeyword(
			"kaleoDefinitionVersionId", kaleoLog.getKaleoDefinitionVersionId());
		document.addKeyword("kaleoInstanceId", kaleoLog.getKaleoInstanceId());
		document.addKeyword(
			"kaleoInstanceTokenId", kaleoLog.getKaleoInstanceTokenId());
		document.addKeyword("kaleoLogId", kaleoLog.getKaleoLogId());
		document.addKeyword("kaleoNodeName", kaleoLog.getKaleoNodeName());
		document.addKeyword(
			"kaleoTaskInstanceTokenId", kaleoLog.getKaleoTaskInstanceTokenId());
		document.addDateSortable(
			Field.MODIFIED_DATE, kaleoLog.getModifiedDate());
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