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

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.search.spi.model.index.contributor.ModelDocumentContributor;
import com.liferay.portal.workflow.kaleo.model.KaleoInstance;
import com.liferay.portal.workflow.kaleo.model.KaleoInstanceToken;
import com.liferay.portal.workflow.kaleo.runtime.util.WorkflowContextUtil;

import java.io.Serializable;

import java.util.Map;

import org.osgi.service.component.annotations.Component;

/**
 * @author Inácio Nery
 */
@Component(
	immediate = true,
	property = "indexer.class.name=com.liferay.portal.workflow.kaleo.model.KaleoInstance",
	service = ModelDocumentContributor.class
)
public class KaleoInstanceModelDocumentContributor
	implements ModelDocumentContributor<KaleoInstance> {

	@Override
	public void contribute(Document document, KaleoInstance kaleoInstance) {
		document.addDateSortable(
			Field.CREATE_DATE, kaleoInstance.getCreateDate());
		document.addDateSortable(
			Field.MODIFIED_DATE, kaleoInstance.getModifiedDate());
		document.addKeyword("className", kaleoInstance.getClassName());
		document.addKeyword("classPK", kaleoInstance.getClassPK());
		document.addKeywordSortable("completed", kaleoInstance.isCompleted());
		document.addDateSortable(
			"completionDate", kaleoInstance.getCompletionDate());

		try {
			Map<String, Serializable> workflowContext =
				WorkflowContextUtil.convert(kaleoInstance.getWorkflowContext());

			ServiceContext serviceContext = (ServiceContext)workflowContext.get(
				WorkflowConstants.CONTEXT_SERVICE_CONTEXT);

			KaleoInstanceToken rootKaleoInstanceToken =
				kaleoInstance.getRootKaleoInstanceToken(serviceContext);

			document.addKeywordSortable(
				"currentKaleoNodeName",
				rootKaleoInstanceToken.getCurrentKaleoNodeName());
		}
		catch (PortalException portalException) {
			if (_log.isWarnEnabled()) {
				_log.warn(portalException, portalException);
			}
		}

		document.addKeyword(
			"kaleoDefinitionName", kaleoInstance.getKaleoDefinitionName());
		document.addKeyword(
			"kaleoDefinitionVersionId",
			kaleoInstance.getKaleoDefinitionVersionId());
		document.addKeyword(
			"kaleoDefinitionVersion",
			kaleoInstance.getKaleoDefinitionVersion());
		document.addNumberSortable(
			"kaleoInstanceId", kaleoInstance.getKaleoInstanceId());
		document.addKeyword(
			"rootKaleoInstanceTokenId",
			kaleoInstance.getRootKaleoInstanceTokenId());
	}

	private static final Log _log = LogFactoryUtil.getLog(
		KaleoInstanceModelDocumentContributor.class);

}