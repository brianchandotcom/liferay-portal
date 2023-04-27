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

package com.liferay.document.library.web.internal.portlet.action;

import com.liferay.document.library.constants.DLPortletKeys;
import com.liferay.document.library.kernel.model.DLFolder;
import com.liferay.document.library.kernel.model.DLFolderConstants;
import com.liferay.document.library.kernel.service.DLAppService;
import com.liferay.document.library.kernel.service.DLFolderLocalService;
import com.liferay.document.library.repository.authorization.capability.AuthorizationCapability;
import com.liferay.document.library.web.internal.constants.DLWebKeys;
import com.liferay.document.library.web.internal.display.context.DLAdminDisplayContext;
import com.liferay.document.library.web.internal.display.context.DLAdminDisplayContextProvider;
import com.liferay.document.library.web.internal.display.context.DLAdminManagementToolbarDisplayContext;
import com.liferay.document.library.web.internal.display.context.DLViewFileEntryMetadataSetsDisplayContext;
import com.liferay.document.library.web.internal.helper.DLTrashHelper;
import com.liferay.dynamic.data.mapping.service.DDMStructureLinkLocalService;
import com.liferay.dynamic.data.mapping.service.DDMStructureService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCRenderCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.constants.MVCRenderConstants;
import com.liferay.portal.kernel.portlet.toolbar.contributor.PortletToolbarContributor;
import com.liferay.portal.kernel.repository.Repository;
import com.liferay.portal.kernel.repository.RepositoryProviderUtil;
import com.liferay.portal.kernel.repository.model.Folder;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.WebKeys;

import java.io.IOException;

import javax.portlet.PortletException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Iván Zaera
 */
@Component(
	property = {
		"javax.portlet.name=" + DLPortletKeys.DOCUMENT_LIBRARY,
		"javax.portlet.name=" + DLPortletKeys.DOCUMENT_LIBRARY_ADMIN,
		"mvc.command.name=/", "mvc.command.name=/document_library/view",
		"mvc.command.name=/document_library/view_folder"
	},
	service = MVCRenderCommand.class
)
public class DLViewMVCRenderCommand extends BaseFolderMVCRenderCommand {

	@Override
	public String render(
			RenderRequest renderRequest, RenderResponse renderResponse)
		throws PortletException {

		try {
			if (_pingFolderRepository(renderRequest, renderResponse)) {
				return MVCRenderConstants.MVC_PATH_VALUE_SKIP_DISPATCH;
			}

			renderRequest.setAttribute(
				DLWebKeys.DOCUMENT_LIBRARY_PORTLET_TOOLBAR_CONTRIBUTOR,
				_defaultDLPortletToolbarContributor);
			renderRequest.setAttribute(
				DLWebKeys.
					DOCUMENT_LIBRARY_VIEW_FILE_ENTRY_METADATA_SETS_DISPLAY_CONTEXT,
				new DLViewFileEntryMetadataSetsDisplayContext(
					_ddmStructureLinkLocalService, _ddmStructureService,
					_portal.getLiferayPortletRequest(renderRequest),
					_portal.getLiferayPortletResponse(renderResponse),
					_portal));
			renderRequest.setAttribute(
				WebKeys.DOCUMENT_LIBRARY_FOLDER, _getFolder(renderRequest));

			DLAdminDisplayContext dlAdminDisplayContext =
				_dlAdminDisplayContextProvider.getDLAdminDisplayContext(
					_portal.getHttpServletRequest(renderRequest),
					_portal.getHttpServletResponse(renderResponse));

			renderRequest.setAttribute(
				DLAdminDisplayContext.class.getName(), dlAdminDisplayContext);
			renderRequest.setAttribute(
				DLAdminManagementToolbarDisplayContext.class.getName(),
				_dlAdminDisplayContextProvider.
					getDLAdminManagementToolbarDisplayContext(
						_portal.getHttpServletRequest(renderRequest),
						_portal.getHttpServletResponse(renderResponse),
						dlAdminDisplayContext));

			return super.render(renderRequest, renderResponse);
		}
		catch (PortalException portalException) {
			SessionErrors.add(
				renderRequest, "repositoryPingFailed", portalException);

			return "/document_library/error.jsp";
		}
		catch (IOException ioException) {
			throw new PortletException(ioException);
		}
	}

	@Override
	protected DLTrashHelper getDLTrashHelper() {
		return _dlTrashHelper;
	}

	@Override
	protected String getPath() {
		return "/document_library/view.jsp";
	}

	private Folder _getFolder(RenderRequest renderRequest)
		throws PortalException {

		long folderId = ParamUtil.getLong(renderRequest, "folderId");

		if (folderId == DLFolderConstants.DEFAULT_PARENT_FOLDER_ID) {
			return null;
		}

		return _dlAppService.getFolder(folderId);
	}

	private boolean _pingFolderRepository(
			RenderRequest renderRequest, RenderResponse renderResponse)
		throws IOException, PortalException {

		String mvcRenderCommandName = ParamUtil.getString(
			renderRequest, "mvcRenderCommandName");

		if (!mvcRenderCommandName.equals("/document_library/view_folder")) {
			return false;
		}

		long folderId = ParamUtil.getLong(renderRequest, "folderId");

		if (folderId == DLFolderConstants.DEFAULT_PARENT_FOLDER_ID) {
			return false;
		}

		DLFolder dlFolder = _dlFolderLocalService.fetchDLFolder(folderId);

		if ((dlFolder == null) || !dlFolder.isMountPoint()) {
			return false;
		}

		Repository repository = RepositoryProviderUtil.getRepository(
			dlFolder.getRepositoryId());

		if (repository.isCapabilityProvided(AuthorizationCapability.class)) {
			AuthorizationCapability authorizationCapability =
				repository.getCapability(AuthorizationCapability.class);

			authorizationCapability.authorize(renderRequest, renderResponse);

			return authorizationCapability.hasCustomRedirectFlow(
				renderRequest, renderResponse);
		}

		_dlAppService.getFileEntriesCount(
			dlFolder.getRepositoryId(), dlFolder.getFolderId());

		return false;
	}

	@Reference
	private DDMStructureLinkLocalService _ddmStructureLinkLocalService;

	@Reference
	private DDMStructureService _ddmStructureService;

	@Reference(
		target = "(javax.portlet.name=" + DLPortletKeys.DOCUMENT_LIBRARY + ")"
	)
	private PortletToolbarContributor _defaultDLPortletToolbarContributor;

	@Reference
	private DLAdminDisplayContextProvider _dlAdminDisplayContextProvider;

	@Reference
	private DLAppService _dlAppService;

	@Reference
	private DLFolderLocalService _dlFolderLocalService;

	@Reference
	private DLTrashHelper _dlTrashHelper;

	@Reference
	private Portal _portal;

}