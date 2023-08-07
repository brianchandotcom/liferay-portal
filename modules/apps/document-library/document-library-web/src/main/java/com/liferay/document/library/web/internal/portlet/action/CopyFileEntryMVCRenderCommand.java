/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.document.library.web.internal.portlet.action;

import com.liferay.document.library.configuration.DLSizeLimitConfigurationProvider;
import com.liferay.document.library.constants.DLPortletKeys;
import com.liferay.document.library.kernel.service.DLFileEntryLocalService;
import com.liferay.document.library.web.internal.exception.FileEntrySizeLimitExceededException;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCRenderCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.constants.MVCRenderConstants;
import com.liferay.portal.kernel.repository.model.FileEntry;
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
		"mvc.command.name=/document_library/copy_file_entry"
	},
	service = MVCRenderCommand.class
)
public class CopyFileEntryMVCRenderCommand
	extends BaseFileEntryMVCRenderCommand {

	@Override
	public String render(
			RenderRequest renderRequest, RenderResponse renderResponse)
		throws PortletException {

		try {
			FileEntry fileEntry = ActionUtil.getFileEntry(renderRequest);

			if (_dlSizeLimitConfigurationProvider.isCopyToAllowed(
					fileEntry.getCompanyId(), fileEntry.getGroupId(),
					fileEntry.getSize())) {

				return super.render(renderRequest, renderResponse);
			}

			HttpServletRequest originalHttpServletRequest =
				_portal.getOriginalServletRequest(
					_portal.getHttpServletRequest(renderRequest));

			SessionErrors.add(
				originalHttpServletRequest.getSession(),
				FileEntrySizeLimitExceededException.class,
				new FileEntrySizeLimitExceededException(
					_language.format(
						originalHttpServletRequest,
						"file-cannot-be-copied-because-it-exceeds-the-limit-" +
							"defined-in-x-Settings-x",
						_dlSizeLimitConfigurationProvider.getCopyToFailInfo(
							fileEntry.getCompanyId(), fileEntry.getGroupId(),
							fileEntry.getSize(),
							_portal.getLocale(renderRequest)))));

			_sendRedirect(renderRequest, renderResponse);

			return MVCRenderConstants.MVC_PATH_VALUE_SKIP_DISPATCH;
		}
		catch (PortalException portalException) {
			throw new PortletException(portalException);
		}
	}

	protected void checkPermissions(
			PermissionChecker permissionChecker, FileEntry fileEntry)
		throws PortalException {

		_fileEntryModelResourcePermission.check(
			permissionChecker, fileEntry, ActionKeys.DOWNLOAD);
		_fileEntryModelResourcePermission.check(
			permissionChecker, fileEntry, ActionKeys.VIEW);
	}

	protected String getPath() {
		return "/document_library/copy_file_entry.jsp";
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

	@Reference
	private DLFileEntryLocalService _dlFileEntryLocalService;

	@Reference
	private DLSizeLimitConfigurationProvider _dlSizeLimitConfigurationProvider;

	@Reference(
		target = "(model.class.name=com.liferay.portal.kernel.repository.model.FileEntry)"
	)
	private volatile ModelResourcePermission<FileEntry>
		_fileEntryModelResourcePermission;

	@Reference
	private Language _language;

	@Reference
	private Portal _portal;

}