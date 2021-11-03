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
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.search.spi.model.index.contributor.ModelDocumentContributor;
import com.liferay.portal.workflow.kaleo.definition.NodeType;
import com.liferay.portal.workflow.kaleo.model.KaleoInstance;
import com.liferay.portal.workflow.kaleo.model.KaleoInstanceToken;
import com.liferay.portal.workflow.kaleo.model.KaleoNode;
import com.liferay.portal.workflow.kaleo.service.KaleoInstanceTokenLocalService;
import com.liferay.portal.workflow.kaleo.service.KaleoNodeLocalService;

import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Inácio Nery
 */
@Component(
	immediate = true,
	property = "indexer.class.name=com.liferay.portal.workflow.kaleo.model.KaleoInstance",
	service = ModelDocumentContributor.class
)
public class KaleoInstanceModelDocumentContributor
	extends BaseKaleoModelDocumentContributor
	implements ModelDocumentContributor<KaleoInstance> {

	@Override
	public void contribute(Document document, KaleoInstance kaleoInstance) {
		document.addDateSortable(
			Field.CREATE_DATE, kaleoInstance.getCreateDate());
		document.addDateSortable(
			Field.MODIFIED_DATE, kaleoInstance.getModifiedDate());
		document.addKeyword("className", kaleoInstance.getClassName());
		document.addKeyword(
			Field.CLASS_NAME_ID,
			_portal.getClassNameId(kaleoInstance.getClassName()));
		document.addKeyword("classPK", kaleoInstance.getClassPK());
		document.addKeywordSortable("completed", kaleoInstance.isCompleted());
		document.addDateSortable(
			"completionDate", kaleoInstance.getCompletionDate());
		document.addKeywordSortable(
			"currentKaleoNodeName",
			Stream.of(
				_kaleoInstanceTokenLocalService.getKaleoInstanceTokens(
					kaleoInstance.getKaleoInstanceId())
			).flatMap(
				List::stream
			).map(
				KaleoInstanceToken::getCurrentKaleoNodeId
			).map(
				_kaleoNodeLocalService::fetchKaleoNode
			).filter(
				Objects::nonNull
			).filter(
				kaleoNode -> !Objects.equals(
					kaleoNode.getType(), NodeType.FORK.name())
			).map(
				KaleoNode::getName
			).toArray(
				String[]::new
			));

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

		addAssetEntryAttributes(
			kaleoInstance.getClassName(), kaleoInstance.getClassPK(), document,
			kaleoInstance.getGroupId());
	}

	@Reference
	private KaleoInstanceTokenLocalService _kaleoInstanceTokenLocalService;

	@Reference
	private KaleoNodeLocalService _kaleoNodeLocalService;

	@Reference
	private Portal _portal;

}