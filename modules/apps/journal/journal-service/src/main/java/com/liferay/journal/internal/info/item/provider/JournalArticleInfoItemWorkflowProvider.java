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

package com.liferay.journal.internal.info.item.provider;

import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.info.item.provider.InfoItemWorkflowProvider;
import com.liferay.journal.constants.JournalArticleConstants;
import com.liferay.journal.constants.JournalFolderConstants;
import com.liferay.journal.model.JournalArticle;
import com.liferay.journal.model.JournalFolder;
import com.liferay.journal.service.JournalFolderLocalService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.service.WorkflowDefinitionLinkLocalService;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Adolfo Pérez
 */
@Component(service = InfoItemWorkflowProvider.class)
public class JournalArticleInfoItemWorkflowProvider
	implements InfoItemWorkflowProvider<JournalArticle> {

	@Override
	public int getStatus(JournalArticle article) {
		return article.getStatus();
	}

	@Override
	public boolean isWorkflowEnabled(JournalArticle article)
		throws PortalException {

		if (article.getClassNameId() >
				JournalArticleConstants.CLASS_NAME_ID_DEFAULT) {

			return false;
		}

		if (_hasInheritedWorkflowDefinitionLink(article)) {
			return true;
		}

		DDMStructure ddmStructure = article.getDDMStructure();

		if (_workflowDefinitionLinkLocalService.hasWorkflowDefinitionLink(
				article.getCompanyId(), article.getGroupId(),
				JournalFolder.class.getName(), article.getFolderId(),
				ddmStructure.getStructureId()) ||
			_workflowDefinitionLinkLocalService.hasWorkflowDefinitionLink(
				article.getCompanyId(), article.getGroupId(),
				JournalFolder.class.getName(),
				_journalFolderLocalService.getInheritedWorkflowFolderId(
					article.getFolderId()),
				ddmStructure.getStructureId()) ||
			_workflowDefinitionLinkLocalService.hasWorkflowDefinitionLink(
				article.getCompanyId(), article.getGroupId(),
				JournalFolder.class.getName(),
				_journalFolderLocalService.getInheritedWorkflowFolderId(
					article.getFolderId()),
				JournalArticleConstants.DDM_STRUCTURE_ID_ALL)) {

			return true;
		}

		return false;
	}

	private boolean _hasInheritedWorkflowDefinitionLink(JournalArticle article)
		throws PortalException {

		long folderId = _journalFolderLocalService.getInheritedWorkflowFolderId(
			article.getFolderId());

		if (folderId <= 0) {
			return _workflowDefinitionLinkLocalService.
				hasWorkflowDefinitionLink(
					article.getCompanyId(), article.getGroupId(),
					JournalArticle.class.getName());
		}

		JournalFolder folder = _journalFolderLocalService.getFolder(folderId);

		if (folder.getRestrictionType() ==
				JournalFolderConstants.RESTRICTION_TYPE_INHERIT) {

			return true;
		}

		return false;
	}

	@Reference
	private JournalFolderLocalService _journalFolderLocalService;

	@Reference
	private WorkflowDefinitionLinkLocalService
		_workflowDefinitionLinkLocalService;

}