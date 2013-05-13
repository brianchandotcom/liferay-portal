/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.documentlibrary.workflow;

import com.liferay.portal.NoSuchWorkflowDefinitionLinkException;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.workflow.BaseWorkflowHandler;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.model.WorkflowDefinitionLink;
import com.liferay.portal.security.permission.ResourceActionsUtil;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.WorkflowDefinitionLinkLocalServiceUtil;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portlet.documentlibrary.model.DLFileEntry;
import com.liferay.portlet.documentlibrary.model.DLFileEntryTypeConstants;
import com.liferay.portlet.documentlibrary.model.DLFileVersion;
import com.liferay.portlet.documentlibrary.model.DLFolder;
import com.liferay.portlet.documentlibrary.model.DLFolderConstants;
import com.liferay.portlet.documentlibrary.service.DLFileEntryLocalServiceUtil;
import com.liferay.portlet.documentlibrary.service.DLFileVersionLocalServiceUtil;
import com.liferay.portlet.documentlibrary.service.DLFolderLocalServiceUtil;

import java.io.Serializable;

import java.util.Locale;
import java.util.Map;

/**
 * @author Bruno Farache
 * @author Jorge Ferrer
 * @author Alexander Chow
 */
public class DLFileEntryWorkflowHandler extends BaseWorkflowHandler {

	@Override
	public String getClassName() {
		return DLFileEntry.class.getName();
	}

	@Override
	public String getType(Locale locale) {
		return ResourceActionsUtil.getModelResource(locale, getClassName());
	}

	@Override
	public WorkflowDefinitionLink getWorkflowDefinitionLink(
			long companyId, long groupId, long classPK)
		throws PortalException, SystemException {

		DLFileVersion dlFileVersion =
			DLFileVersionLocalServiceUtil.getFileVersion(classPK);

		long folderId = dlFileVersion.getFolderId();

		while (folderId != DLFolderConstants.DEFAULT_PARENT_FOLDER_ID) {
			DLFolder dlFolder = DLFolderLocalServiceUtil.getFolder(folderId);

			if (dlFolder.isOverrideFileEntryTypes()) {
				break;
			}

			folderId = dlFolder.getParentFolderId();
		}

		try {
			return WorkflowDefinitionLinkLocalServiceUtil.
				getWorkflowDefinitionLink(
					companyId, groupId, DLFolder.class.getName(), folderId,
					dlFileVersion.getFileEntryTypeId(), true);
		}
		catch (NoSuchWorkflowDefinitionLinkException nswdle) {
			return WorkflowDefinitionLinkLocalServiceUtil.
				getWorkflowDefinitionLink(
					companyId, groupId, DLFolder.class.getName(), folderId,
					DLFileEntryTypeConstants.FILE_ENTRY_TYPE_ID_ALL, true);
		}
	}

	@Override
	public boolean isVisible() {
		return _VISIBLE;
	}

	@Override
	public DLFileEntry updateStatus(
			int status, Map<String, Serializable> workflowContext)
		throws PortalException, SystemException {

		long userId = GetterUtil.getLong(
			(String)workflowContext.get(WorkflowConstants.CONTEXT_USER_ID));
		long classPK = GetterUtil.getLong(
			(String)workflowContext.get(
				WorkflowConstants.CONTEXT_ENTRY_CLASS_PK));

		ServiceContext serviceContext = (ServiceContext)workflowContext.get(
			"serviceContext");

		return DLFileEntryLocalServiceUtil.updateStatus(
			userId, classPK, status, workflowContext, serviceContext);
	}

	@Override
	protected String getIconPath(ThemeDisplay themeDisplay) {
		return themeDisplay.getPathThemeImages() + "/common/clip.png";
	}

	private static final boolean _VISIBLE = false;

}