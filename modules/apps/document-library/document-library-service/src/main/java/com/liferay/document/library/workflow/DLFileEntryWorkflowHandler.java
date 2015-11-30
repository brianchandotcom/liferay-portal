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

package com.liferay.document.library.workflow;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.workflow.BaseWorkflowHandler;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.kernel.workflow.WorkflowHandler;
import com.liferay.portal.model.WorkflowDefinitionLink;
import com.liferay.portal.security.permission.ResourceActionsUtil;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.WorkflowDefinitionLinkLocalService;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portlet.asset.AssetRendererFactoryRegistryUtil;
import com.liferay.portlet.asset.model.AssetRenderer;
import com.liferay.portlet.asset.model.AssetRendererFactory;
import com.liferay.portlet.documentlibrary.model.DLFileEntry;
import com.liferay.portlet.documentlibrary.model.DLFileEntryTypeConstants;
import com.liferay.portlet.documentlibrary.model.DLFileVersion;
import com.liferay.portlet.documentlibrary.model.DLFolder;
import com.liferay.portlet.documentlibrary.model.DLFolderConstants;
import com.liferay.portlet.documentlibrary.service.DLFileEntryLocalService;
import com.liferay.portlet.documentlibrary.service.DLFileVersionLocalService;
import com.liferay.portlet.documentlibrary.service.DLFolderLocalService;

import java.io.Serializable;

import java.util.Locale;
import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Bruno Farache
 * @author Jorge Ferrer
 * @author Alexander Chow
 */
@Component(
	property = {
		"model.class.name=com.liferay.portlet.documentlibrary.model.DLFileEntry"
	},
	service = WorkflowHandler.class
)
public class DLFileEntryWorkflowHandler
	extends BaseWorkflowHandler<DLFileEntry> {

	@Override
	public AssetRenderer<DLFileEntry> getAssetRenderer(long classPK)
		throws PortalException {

		AssetRendererFactory<DLFileEntry> assetRendererFactory =
			getAssetRendererFactory();

		if (assetRendererFactory != null) {
			DLFileVersion dlFileVersion =
				_dlFileVersionLocalService.getFileVersion(classPK);

			return assetRendererFactory.getAssetRenderer(
				dlFileVersion.getFileEntryId(),
				AssetRendererFactory.TYPE_LATEST);
		}
		else {
			return null;
		}
	}

	@Override
	public AssetRendererFactory<DLFileEntry> getAssetRendererFactory() {
		return (AssetRendererFactory<DLFileEntry>)
			AssetRendererFactoryRegistryUtil.getAssetRendererFactoryByClassName(
				getClassName());
	}

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
		throws PortalException {

		DLFileVersion dlFileVersion = _dlFileVersionLocalService.getFileVersion(
			classPK);

		long folderId = dlFileVersion.getFolderId();

		while (folderId != DLFolderConstants.DEFAULT_PARENT_FOLDER_ID) {
			DLFolder dlFolder = _dlFolderLocalService.getFolder(folderId);

			if (dlFolder.getRestrictionType() !=
					DLFolderConstants.RESTRICTION_TYPE_INHERIT) {

				break;
			}

			folderId = dlFolder.getParentFolderId();
		}

		WorkflowDefinitionLink workflowDefinitionLink =
			_workflowDefinitionLinkLocalService.fetchWorkflowDefinitionLink(
				companyId, groupId, DLFolder.class.getName(), folderId,
				dlFileVersion.getFileEntryTypeId(), true);

		if (workflowDefinitionLink == null) {
			workflowDefinitionLink =
				_workflowDefinitionLinkLocalService.fetchWorkflowDefinitionLink(
					companyId, groupId, DLFolder.class.getName(), folderId,
					DLFileEntryTypeConstants.FILE_ENTRY_TYPE_ID_ALL, true);
		}

		return workflowDefinitionLink;
	}

	@Override
	public boolean isVisible() {
		return _VISIBLE;
	}

	@Override
	public DLFileEntry updateStatus(
			int status, Map<String, Serializable> workflowContext)
		throws PortalException {

		long userId = GetterUtil.getLong(
			(String)workflowContext.get(WorkflowConstants.CONTEXT_USER_ID));
		long classPK = GetterUtil.getLong(
			(String)workflowContext.get(
				WorkflowConstants.CONTEXT_ENTRY_CLASS_PK));

		ServiceContext serviceContext = (ServiceContext)workflowContext.get(
			"serviceContext");

		return _dlFileEntryLocalService.updateStatus(
			userId, classPK, status, serviceContext, workflowContext);
	}

	@Override
	protected String getIconPath(ThemeDisplay themeDisplay) {
		return themeDisplay.getPathThemeImages() + "/common/clip.png";
	}

	@Reference(unbind = "-")
	protected void setDLFileEntryLocalService(
		DLFileEntryLocalService dlFileEntryLocalService) {

		_dlFileEntryLocalService = dlFileEntryLocalService;
	}

	@Reference(unbind = "-")
	protected void setDLFileVersionLocalService(
		DLFileVersionLocalService dlFileVersionLocalService) {

		_dlFileVersionLocalService = dlFileVersionLocalService;
	}

	@Reference(unbind = "-")
	protected void setDLFolderLocalService(
		DLFolderLocalService dlFolderLocalService) {

		_dlFolderLocalService = dlFolderLocalService;
	}

	@Reference(unbind = "-")
	protected void setWorkflowDefinitionLinkLocalService(
		WorkflowDefinitionLinkLocalService workflowDefinitionLinkLocalService) {

		_workflowDefinitionLinkLocalService =
			workflowDefinitionLinkLocalService;
	}

	private static final boolean _VISIBLE = false;

	private volatile DLFileEntryLocalService _dlFileEntryLocalService;
	private volatile DLFileVersionLocalService _dlFileVersionLocalService;
	private volatile DLFolderLocalService _dlFolderLocalService;
	private volatile WorkflowDefinitionLinkLocalService
		_workflowDefinitionLinkLocalService;

}