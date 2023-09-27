/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.document.library.web.internal.portlet.action;

import com.liferay.document.library.configuration.DLSizeLimitConfigurationProvider;
import com.liferay.document.library.constants.DLPortletKeys;
import com.liferay.document.library.kernel.model.DLFolder;
import com.liferay.document.library.kernel.service.DLFolderLocalService;
import com.liferay.document.library.web.internal.exception.FolderSizeLimitExceededException;
import com.liferay.document.library.web.internal.helper.DLTrashHelper;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCRenderCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.constants.MVCRenderConstants;
import com.liferay.portal.kernel.repository.model.Folder;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;

import java.io.IOException;

import javax.portlet.PortletException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Marco Galluzzi
 */
@Component(
	property = {
		"javax.portlet.name=" + DLPortletKeys.DOCUMENT_LIBRARY,
		"javax.portlet.name=" + DLPortletKeys.DOCUMENT_LIBRARY_ADMIN,
		"javax.portlet.name=" + DLPortletKeys.MEDIA_GALLERY_DISPLAY,
		"mvc.command.name=/document_library/copy_folder"
	},
	service = MVCRenderCommand.class
)
public class CopyFolderMVCRenderCommand extends BaseFolderMVCRenderCommand {

	@Override
	public String render(
			RenderRequest renderRequest, RenderResponse renderResponse)
		throws PortletException {

		try {
			DLFolder dlFolder = _dlFolderLocalService.getDLFolder(
				ParamUtil.getLong(renderRequest, "sourceFolderId"));

			long dlFolderSize = _dlFolderLocalService.getFolderSize(
				dlFolder.getCompanyId(), dlFolder.getGroupId(),
				dlFolder.getTreePath());

			_validateCopyToSize(dlFolder, dlFolderSize);

			return super.render(renderRequest, renderResponse);
		}
		catch (FolderSizeLimitExceededException
					folderSizeLimitExceededException) {

			HttpServletRequest originalHttpServletRequest =
				_portal.getOriginalServletRequest(
					_portal.getHttpServletRequest(renderRequest));

			SessionErrors.add(
				originalHttpServletRequest.getSession(),
				folderSizeLimitExceededException.getClass(),
				folderSizeLimitExceededException);

			_sendRedirect(renderRequest, renderResponse);

			return MVCRenderConstants.MVC_PATH_VALUE_SKIP_DISPATCH;
		}
		catch (PortalException portalException) {
			throw new PortletException(portalException);
		}
	}

	@Override
	protected void checkPermissions(
			PermissionChecker permissionChecker, Folder folder)
		throws PortalException {

		_folderModelResourcePermission.check(
			permissionChecker, folder, ActionKeys.VIEW);
	}

	@Override
	protected DLTrashHelper getDLTrashHelper() {
		return _dlTrashHelper;
	}

	@Override
	protected String getPath() {
		return "/document_library/copy_folder.jsp";
	}

	private void _sendRedirect(
			RenderRequest renderRequest, RenderResponse renderResponse)
		throws PortletException {

		try {
			HttpServletResponse httpServletResponse =
				_portal.getHttpServletResponse(renderResponse);

			httpServletResponse.sendRedirect(
				ParamUtil.getString(renderRequest, "redirect"));
		}
		catch (IOException ioException) {
			throw new PortletException(ioException);
		}
	}

	private void _validateCopyToSize(DLFolder dlFolder, long folderSize)
		throws FolderSizeLimitExceededException {

		if (!DLCopyValidationUtil.isCopyToAllowed(
				_dlSizeLimitConfigurationProvider.getCompanyMaxSizeToCopy(
					dlFolder.getCompanyId()),
				_dlSizeLimitConfigurationProvider.getGroupMaxSizeToCopy(
					dlFolder.getGroupId()),
				_dlSizeLimitConfigurationProvider.getSystemMaxSizeToCopy(),
				folderSize)) {

			throw new FolderSizeLimitExceededException(
				DLCopyValidationUtil.getCopyToValidationMessage(
					_dlSizeLimitConfigurationProvider.getCompanyMaxSizeToCopy(
						dlFolder.getCompanyId()),
					_dlSizeLimitConfigurationProvider.getGroupMaxSizeToCopy(
						dlFolder.getGroupId()),
					_dlSizeLimitConfigurationProvider.getSystemMaxSizeToCopy(),
					folderSize));
		}
	}

	@Reference
	private DLFolderLocalService _dlFolderLocalService;

	@Reference
	private DLSizeLimitConfigurationProvider _dlSizeLimitConfigurationProvider;

	@Reference
	private DLTrashHelper _dlTrashHelper;

	@Reference(
		target = "(model.class.name=com.liferay.portal.kernel.repository.model.Folder)"
	)
	private volatile ModelResourcePermission<Folder>
		_folderModelResourcePermission;

	@Reference
	private Portal _portal;

}