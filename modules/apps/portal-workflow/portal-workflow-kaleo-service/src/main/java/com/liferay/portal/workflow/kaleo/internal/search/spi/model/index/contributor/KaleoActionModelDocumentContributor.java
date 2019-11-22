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
import com.liferay.portal.workflow.kaleo.model.KaleoAction;

import org.osgi.service.component.annotations.Component;

/**
 * @author Inácio Nery
 */
@Component(
	immediate = true,
	property = "indexer.class.name=com.liferay.portal.workflow.kaleo.model.KaleoAction",
	service = ModelDocumentContributor.class
)
public class KaleoActionModelDocumentContributor
	implements ModelDocumentContributor<KaleoAction> {

	@Override
	public void contribute(Document document, KaleoAction kaleoAction) {
		document.addDateSortable(
			Field.CREATE_DATE, kaleoAction.getCreateDate());
		document.addDateSortable(
			Field.MODIFIED_DATE, kaleoAction.getModifiedDate());
		document.addKeyword("description", kaleoAction.getDescription());
		document.addKeyword("executionType", kaleoAction.getExecutionType());
		document.addKeyword("kaleoActionId", kaleoAction.getKaleoActionId());
		document.addKeyword(
			"kaleoClassName", kaleoAction.getKaleoClassName(), false);
		document.addKeyword("kaleoClassPK", kaleoAction.getKaleoClassPK());
		document.addKeyword(
			"kaleoDefinitionVersionId",
			kaleoAction.getKaleoDefinitionVersionId());
		document.addKeyword("kaleoNodeName", kaleoAction.getKaleoNodeName());
		document.addKeyword("name", kaleoAction.getName());
		document.addKeyword("priority", kaleoAction.getPriority());
		document.addKeyword("script", kaleoAction.getScript());
		document.addKeyword("scriptLanguage", kaleoAction.getScriptLanguage());
		document.addKeyword(
			"scriptRequiredContexts", kaleoAction.getScriptRequiredContexts());
	}

}