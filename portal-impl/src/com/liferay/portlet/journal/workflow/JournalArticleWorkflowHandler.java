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

package com.liferay.portlet.journal.workflow;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.workflow.BaseWorkflowHandler;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.model.WorkflowDefinitionLink;
import com.liferay.portal.security.permission.ResourceActionsUtil;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.WorkflowDefinitionLinkLocalServiceUtil;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.PortletKeys;
import com.liferay.portlet.dynamicdatamapping.model.DDMStructure;
import com.liferay.portlet.dynamicdatamapping.service.DDMStructureLocalServiceUtil;
import com.liferay.portlet.journal.model.JournalArticle;
import com.liferay.portlet.journal.model.JournalArticleConstants;
import com.liferay.portlet.journal.model.JournalFolder;
import com.liferay.portlet.journal.service.JournalArticleLocalServiceUtil;
import com.liferay.portlet.journal.service.JournalFolderLocalServiceUtil;

import java.io.Serializable;

import java.util.Locale;
import java.util.Map;

/**
 * @author Bruno Farache
 * @author Marcellus Tavares
 * @author Juan Fernández
 * @author Julio Camarero
 */
public class JournalArticleWorkflowHandler
	extends BaseWorkflowHandler<JournalArticle> {

	@Override
	public String getClassName() {
		return JournalArticle.class.getName();
	}

	@Override
	public String getType(Locale locale) {
		return ResourceActionsUtil.getModelResource(locale, getClassName());
	}

	@Override
	public WorkflowDefinitionLink getWorkflowDefinitionLink(
			long companyId, long groupId, long classPK)
		throws PortalException {

		JournalArticle article = JournalArticleLocalServiceUtil.getArticle(
			classPK);

		long folderId =
			JournalFolderLocalServiceUtil.getInheritedWorkflowFolderId(
				article.getFolderId());

		DDMStructure ddmStructure = DDMStructureLocalServiceUtil.getStructure(
			article.getGroupId(),
			PortalUtil.getClassNameId(JournalArticle.class),
			article.getDDMStructureKey(), true);

		WorkflowDefinitionLink workflowDefinitionLink =
			WorkflowDefinitionLinkLocalServiceUtil.fetchWorkflowDefinitionLink(
				companyId, groupId, JournalFolder.class.getName(), folderId,
				ddmStructure.getStructureId(), true);

		if (workflowDefinitionLink == null) {
			workflowDefinitionLink =
				WorkflowDefinitionLinkLocalServiceUtil.
					fetchWorkflowDefinitionLink(
						companyId, groupId, JournalFolder.class.getName(),
						folderId, JournalArticleConstants.DDM_STRUCTURE_ID_ALL,
						true);
		}

		return workflowDefinitionLink;
	}

	@Override
	public boolean isVisible() {
		return _VISIBLE;
	}

	@Override
	public JournalArticle updateStatus(
			int status, Map<String, Serializable> workflowContext)
		throws PortalException {

		long userId = GetterUtil.getLong(
			(String)workflowContext.get(WorkflowConstants.CONTEXT_USER_ID));
		long classPK = GetterUtil.getLong(
			(String)workflowContext.get(
				WorkflowConstants.CONTEXT_ENTRY_CLASS_PK));

		JournalArticle article = JournalArticleLocalServiceUtil.getArticle(
			classPK);

		ServiceContext serviceContext = (ServiceContext)workflowContext.get(
			"serviceContext");

		String articleURL = PortalUtil.getControlPanelFullURL(
			serviceContext.getScopeGroupId(), PortletKeys.JOURNAL, null);

		return JournalArticleLocalServiceUtil.updateStatus(
			userId, article, status, articleURL, serviceContext,
			workflowContext);
	}

	@Override
	protected String getIconPath(ThemeDisplay themeDisplay) {
		return themeDisplay.getPathThemeImages() + "/common/history.png";
	}

	private static final boolean _VISIBLE = false;

}