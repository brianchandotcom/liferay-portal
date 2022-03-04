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
import com.liferay.document.library.kernel.model.DLVersionNumberIncrease;
import com.liferay.document.library.kernel.service.DLAppService;
import com.liferay.document.library.opener.constants.DLOpenerMimeTypes;
import com.liferay.document.library.opener.google.drive.web.internal.DLOpenerGoogleDriveFileReference;
import com.liferay.document.library.opener.google.drive.web.internal.DLOpenerGoogleDriveManager;
import com.liferay.document.library.opener.google.drive.web.internal.constants.DLOpenerGoogleDriveWebKeys;
import com.liferay.document.library.opener.google.drive.web.internal.helper.GoogleDrivePortletRequestAuthorizationHelper;
import com.liferay.document.library.opener.upload.UniqueFileEntryTitleProvider;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextFactory;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.transaction.Propagation;
import com.liferay.portal.kernel.transaction.TransactionConfig;
import com.liferay.portal.kernel.transaction.TransactionInvokerUtil;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.kernel.workflow.WorkflowConstants;

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
		"auth.token.ignore.mvc.action=true",
		"javax.portlet.name=" + DLPortletKeys.DOCUMENT_LIBRARY,
		"javax.portlet.name=" + DLPortletKeys.DOCUMENT_LIBRARY_ADMIN,
		"mvc.command.name=/document_library/edit_in_google_docs"
	},
	service = MVCActionCommand.class
)
public class EditInGoogleDocsMVCActionCommand extends BaseMVCActionCommand {

	@Override
	protected void doProcessAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		try {
			if (_dlOpenerGoogleDriveManager.hasValidCredential(
					themeDisplay.getCompanyId(), themeDisplay.getUserId())) {

				long fileEntryId = ParamUtil.getLong(
					actionRequest, "fileEntryId");

				_executeCommand(actionRequest, fileEntryId);
			}
			else {
				_googleDrivePortletRequestAuthorizationHelper.
					performAuthorizationFlow(actionRequest, actionResponse);
			}
		}
		catch (PortalException portalException) {
			SessionErrors.add(actionRequest, portalException.getClass());
		}
	}

	private DLOpenerGoogleDriveFileReference _addGoogleDriveFileEntry(
			long repositoryId, long folderId, String contentType,
			ServiceContext serviceContext)
		throws PortalException {

		String title = _uniqueFileEntryTitleProvider.provide(
			serviceContext.getScopeGroupId(), folderId,
			DLOpenerMimeTypes.getMimeTypeExtension(contentType),
			serviceContext.getLocale());

		serviceContext.setWorkflowAction(WorkflowConstants.ACTION_SAVE_DRAFT);

		FileEntry fileEntry = _dlAppService.addFileEntry(
			null, repositoryId, folderId, null, contentType, title, null,
			StringPool.BLANK, StringPool.BLANK, new byte[0], null, null,
			serviceContext);

		_dlAppService.checkOutFileEntry(
			fileEntry.getFileEntryId(), serviceContext);

		return _dlOpenerGoogleDriveManager.create(
			serviceContext.getUserId(), fileEntry);
	}

	private DLOpenerGoogleDriveFileReference _checkOutGoogleDriveFileEntry(
			long fileEntryId, ServiceContext serviceContext)
		throws PortalException {

		_dlAppService.checkOutFileEntry(fileEntryId, serviceContext);

		return _dlOpenerGoogleDriveManager.checkOut(
			serviceContext.getUserId(),
			_dlAppService.getFileEntry(fileEntryId));
	}

	private void _executeCommand(ActionRequest actionRequest, long fileEntryId)
		throws PortalException {

		String cmd = ParamUtil.getString(actionRequest, Constants.CMD);

		if (cmd.equals(Constants.ADD)) {
			try {
				long repositoryId = ParamUtil.getLong(
					actionRequest, "repositoryId");
				long folderId = ParamUtil.getLong(actionRequest, "folderId");
				String contentType = ParamUtil.getString(
					actionRequest, "contentType",
					DLOpenerMimeTypes.APPLICATION_VND_DOCX);

				ServiceContext serviceContext =
					ServiceContextFactory.getInstance(actionRequest);

				_saveDLOpenerGoogleDriveFileReference(
					actionRequest,
					TransactionInvokerUtil.invoke(
						_transactionConfig,
						() -> _addGoogleDriveFileEntry(
							repositoryId, folderId, contentType,
							serviceContext)));

				hideDefaultSuccessMessage(actionRequest);
			}
			catch (PortalException portalException) {
				throw portalException;
			}
			catch (Throwable throwable) {
				throw new PortalException(throwable);
			}
		}
		else if (cmd.equals(Constants.CANCEL_CHECKOUT)) {
			_dlAppService.cancelCheckOut(fileEntryId);
		}
		else if (cmd.equals(Constants.CHECKIN)) {
			DLVersionNumberIncrease dlVersionNumberIncrease =
				DLVersionNumberIncrease.valueOf(
					actionRequest.getParameter("versionIncrease"),
					DLVersionNumberIncrease.AUTOMATIC);

			String changeLog = ParamUtil.getString(actionRequest, "changeLog");

			ServiceContext serviceContext = ServiceContextFactory.getInstance(
				actionRequest);

			_dlAppService.checkInFileEntry(
				fileEntryId, dlVersionNumberIncrease, changeLog,
				serviceContext);
		}
		else if (cmd.equals(Constants.CHECKOUT)) {
			try {
				ServiceContext serviceContext =
					ServiceContextFactory.getInstance(actionRequest);

				_saveDLOpenerGoogleDriveFileReference(
					actionRequest,
					TransactionInvokerUtil.invoke(
						_transactionConfig,
						() -> _checkOutGoogleDriveFileEntry(
							fileEntryId, serviceContext)));

				hideDefaultSuccessMessage(actionRequest);
			}
			catch (PortalException portalException) {
				throw portalException;
			}
			catch (Throwable throwable) {
				throw new PortalException(throwable);
			}
		}
		else if (cmd.equals(Constants.EDIT)) {
			ThemeDisplay themeDisplay =
				(ThemeDisplay)actionRequest.getAttribute(WebKeys.THEME_DISPLAY);

			_saveDLOpenerGoogleDriveFileReference(
				actionRequest,
				_dlOpenerGoogleDriveManager.requestEditAccess(
					themeDisplay.getUserId(),
					_dlAppService.getFileEntry(fileEntryId)));
		}
		else {
			throw new IllegalArgumentException();
		}
	}

	private void _saveDLOpenerGoogleDriveFileReference(
		PortletRequest portletRequest,
		DLOpenerGoogleDriveFileReference dlOpenerGoogleDriveFileReference) {

		portletRequest.setAttribute(
			DLOpenerGoogleDriveWebKeys.DL_OPENER_GOOGLE_DRIVE_FILE_REFERENCE,
			dlOpenerGoogleDriveFileReference);
	}

	@Reference
	private DLAppService _dlAppService;

	@Reference
	private DLOpenerGoogleDriveManager _dlOpenerGoogleDriveManager;

	@Reference
	private GoogleDrivePortletRequestAuthorizationHelper
		_googleDrivePortletRequestAuthorizationHelper;

	private final TransactionConfig _transactionConfig =
		TransactionConfig.Factory.create(
			Propagation.REQUIRED, new Class<?>[] {Exception.class});

	@Reference
	private UniqueFileEntryTitleProvider _uniqueFileEntryTitleProvider;

}