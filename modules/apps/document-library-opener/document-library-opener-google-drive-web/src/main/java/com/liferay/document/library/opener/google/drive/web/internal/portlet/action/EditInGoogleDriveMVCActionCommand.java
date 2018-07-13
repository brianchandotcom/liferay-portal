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

package com.liferay.document.library.opener.google.drive.web.internal.portlet.action;

import com.liferay.document.library.constants.DLPortletKeys;
import com.liferay.document.library.kernel.service.DLAppService;
import com.liferay.document.library.opener.google.drive.constants.DLOpenerGoogleDriveConstants;
import com.liferay.document.library.opener.google.drive.model.DLOpenerGoogleDriveFileReference;
import com.liferay.document.library.opener.google.drive.service.DLOpenerGoogleDriveManager;
import com.liferay.document.library.opener.google.drive.web.internal.constants.DLOpenerGoogleDriveWebConstants;
import com.liferay.document.library.opener.google.drive.web.internal.constants.DLOpenerGoogleDriveWebKeys;
import com.liferay.document.library.opener.google.drive.web.internal.util.OAuth2Helper;
import com.liferay.document.library.opener.google.drive.web.internal.util.State;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.portlet.LiferayPortletURL;
import com.liferay.portal.kernel.portlet.PortletURLFactoryUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextFactory;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.WebKeys;

import java.io.IOException;

import java.util.Optional;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletRequest;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Adolfo Pérez
 */
@Component(
	property = {
		"javax.portlet.name=" + DLPortletKeys.DOCUMENT_LIBRARY_ADMIN,
		"mvc.command.name=/document_library/edit_in_google_docs"
	},
	service = MVCActionCommand.class
)
public class EditInGoogleDriveMVCActionCommand extends BaseMVCActionCommand {

	@Override
	protected void doProcessAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		try {
			long fileEntryId = ParamUtil.getLong(actionRequest, "fileEntryId");

			FileEntry fileEntry = _dlAppService.getFileEntry(fileEntryId);

			if (_dlOpenerGoogleDriveManager.hasValidCredential(
					themeDisplay.getUserId())) {

				Optional<DLOpenerGoogleDriveFileReference>
					dlOpenerDriveFileReferenceOptional =
						DLOpenerGoogleDriveFileReference.
							captureDLOpenerGoogleDriveFileReference(
								() -> _executeCommand(
									actionRequest, fileEntry));

				dlOpenerDriveFileReferenceOptional.ifPresent(
					driveFileReference -> actionRequest.setAttribute(
						DLOpenerGoogleDriveWebKeys.
							DL_OPENER_GOOGLE_DRIVE_FILE_REFERENCE,
						driveFileReference));
			}
			else {
				_performAuthorizationFlow(actionRequest, actionResponse);
			}
		}
		catch (PortalException pe) {
			SessionErrors.add(actionRequest, pe.getClass());
		}
	}

	private void _executeCommand(
			ActionRequest actionRequest, FileEntry fileEntry)
		throws PortalException {

		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		String cmd = ParamUtil.getString(actionRequest, Constants.CMD);

		if (cmd.equals(
				DLOpenerGoogleDriveWebConstants.GOOGLE_DRIVE_CANCEL_CHECKOUT)) {

			_dlAppService.cancelCheckOut(fileEntry.getFileEntryId());
		}
		else if (cmd.equals(
					DLOpenerGoogleDriveWebConstants.GOOGLE_DRIVE_CHECKIN)) {

			boolean majorVersion = ParamUtil.getBoolean(
				actionRequest, "majorVersion");
			String changeLog = ParamUtil.getString(actionRequest, "changeLog");

			_dlAppService.checkInFileEntry(
				fileEntry.getFileEntryId(), majorVersion, changeLog,
				ServiceContextFactory.getInstance(actionRequest));
		}
		else if (cmd.equals(
					DLOpenerGoogleDriveWebConstants.GOOGLE_DRIVE_CHECKOUT)) {

			ServiceContext serviceContext = ServiceContextFactory.getInstance(
				actionRequest);

			serviceContext.setAttribute(
				DLOpenerGoogleDriveConstants.CHECK_OUT_IN_GOOGLE_DRIVE,
				Boolean.TRUE);

			_dlAppService.checkOutFileEntry(
				fileEntry.getFileEntryId(), serviceContext);
		}
		else if (cmd.equals(
					DLOpenerGoogleDriveWebConstants.GOOGLE_DRIVE_EDIT) &&
				 _dlOpenerGoogleDriveManager.isGoogleDriveFile(fileEntry)) {

			DLOpenerGoogleDriveFileReference.
				setCurrentDLOpenerGoogleDriveFileReference(
					_dlOpenerGoogleDriveManager.requestEditAccess(
						themeDisplay.getUserId(), fileEntry));
		}
		else {
			throw new IllegalArgumentException();
		}
	}

	private String _getFailureURL(PortletRequest portletRequest)
		throws PortalException {

		LiferayPortletURL liferayPortletURL = PortletURLFactoryUtil.create(
			portletRequest, DLPortletKeys.DOCUMENT_LIBRARY_ADMIN,
			_portal.getControlPanelPlid(portletRequest),
			PortletRequest.RENDER_PHASE);

		return liferayPortletURL.toString();
	}

	private String _getSuccessURL(PortletRequest portletRequest) {
		return _portal.getCurrentCompleteURL(
			_portal.getHttpServletRequest(portletRequest));
	}

	private void _performAuthorizationFlow(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws IOException, PortalException {

		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		String state = StringUtil.randomString(5);

		State.save(
			_portal.getOriginalServletRequest(
				_portal.getHttpServletRequest(actionRequest)),
			themeDisplay.getUserId(), _getSuccessURL(actionRequest),
			_getFailureURL(actionRequest), state);

		actionResponse.sendRedirect(
			_dlOpenerGoogleDriveManager.getAuthorizationURL(
				state, _oAuth2Helper.getRedirectUri(actionRequest)));
	}

	@Reference
	private DLAppService _dlAppService;

	@Reference
	private DLOpenerGoogleDriveManager _dlOpenerGoogleDriveManager;

	@Reference
	private OAuth2Helper _oAuth2Helper;

	@Reference
	private Portal _portal;

}