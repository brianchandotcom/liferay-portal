/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.document.library.opener.one.drive.web.internal.portlet.action;

import com.liferay.document.library.constants.DLPortletKeys;
import com.liferay.document.library.kernel.model.DLVersionNumberIncrease;
import com.liferay.document.library.kernel.service.DLAppService;
import com.liferay.document.library.opener.constants.DLOpenerMimeTypes;
import com.liferay.document.library.opener.oauth.OAuth2State;
import com.liferay.document.library.opener.one.drive.web.internal.DLOpenerOneDriveFileReference;
import com.liferay.document.library.opener.one.drive.web.internal.DLOpenerOneDriveManager;
import com.liferay.document.library.opener.one.drive.web.internal.oauth.AccessToken;
import com.liferay.document.library.opener.one.drive.web.internal.oauth.OAuth2Manager;
import com.liferay.document.library.opener.one.drive.web.internal.oauth.OAuth2StateUtil;
import com.liferay.document.library.opener.upload.UniqueFileEntryTitleProvider;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.portlet.LiferayPortletURL;
import com.liferay.portal.kernel.portlet.PortletURLFactory;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextFactory;
import com.liferay.portal.kernel.transaction.Propagation;
import com.liferay.portal.kernel.transaction.TransactionConfig;
import com.liferay.portal.kernel.transaction.TransactionInvokerUtil;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.PwdGenerator;

import java.util.Optional;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletRequest;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Cristina González
 */
@Component(
	property = {
		"auth.token.ignore.mvc.action=true",
		"javax.portlet.name=" + DLPortletKeys.DOCUMENT_LIBRARY,
		"javax.portlet.name=" + DLPortletKeys.DOCUMENT_LIBRARY_ADMIN,
		"mvc.command.name=/document_library/edit_in_office365"
	},
	service = MVCActionCommand.class
)
public class EditInOneDriveMVCActionCommand extends BaseMVCActionCommand {

	@Override
	protected void doProcessAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		long companyId = _portal.getCompanyId(actionRequest);
		long userId = _portal.getUserId(actionRequest);

		Optional<AccessToken> accessTokenOptional =
			_oAuth2Manager.getAccessTokenOptional(companyId, userId);

		if (accessTokenOptional.isPresent()) {
			_executeCommand(
				actionRequest, actionResponse,
				ParamUtil.getLong(actionRequest, "fileEntryId"));
		}
		else {
			String state = PwdGenerator.getPassword(5);

			OAuth2StateUtil.save(
				_portal.getOriginalServletRequest(
					_portal.getHttpServletRequest(actionRequest)),
				new OAuth2State(
					userId, _getSuccessURL(actionRequest),
					_getFailureURL(actionRequest), state));

			actionResponse.sendRedirect(
				_oAuth2Manager.getAuthorizationURL(
					companyId, _portal.getPortalURL(actionRequest), state));
		}
	}

	private DLOpenerOneDriveFileReference _addDLOpenerOneDriveFileReference(
			long userId, long repositoryId, long folderId, String mimeType,
			ServiceContext serviceContext)
		throws PortalException {

		String title = _uniqueFileEntryTitleProvider.provide(
			serviceContext.getScopeGroupId(), folderId,
			serviceContext.getLocale());

		String sourceFileName = title;

		sourceFileName += DLOpenerMimeTypes.getMimeTypeExtension(mimeType);

		FileEntry fileEntry = _dlAppService.addFileEntry(
			repositoryId, folderId, sourceFileName, mimeType, title,
			StringPool.BLANK, StringPool.BLANK, new byte[0], serviceContext);

		_dlAppService.checkOutFileEntry(
			fileEntry.getFileEntryId(), serviceContext);

		return _dlOpenerOneDriveManager.createFile(userId, fileEntry);
	}

	private void _executeCommand(
			ActionRequest actionRequest, ActionResponse actionResponse,
			long fileEntryId)
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

				DLOpenerOneDriveFileReference dlOpenerOneDriveFileReference =
					TransactionInvokerUtil.invoke(
						_transactionConfig,
						() -> _addDLOpenerOneDriveFileReference(
							_portal.getUserId(actionRequest), repositoryId,
							folderId, contentType, serviceContext));

				actionRequest.setAttribute(
					"DL_OPENER_ONE_DRIVE_FILE_REFERENCE",
					dlOpenerOneDriveFileReference);

				hideDefaultSuccessMessage(actionRequest);

				actionResponse.sendRedirect(
					dlOpenerOneDriveFileReference.getUrl());
			}
			catch (Throwable throwable) {
				throw new PortalException(throwable);
			}
		}
		else if (cmd.equals(Constants.CANCEL_CHECKOUT)) {
			_dlAppService.cancelCheckOut(fileEntryId);
		}
	}

	private String _getFailureURL(PortletRequest portletRequest)
		throws PortalException {

		LiferayPortletURL liferayPortletURL = _portletURLFactory.create(
			portletRequest, _portal.getPortletId(portletRequest),
			_portal.getControlPanelPlid(portletRequest),
			PortletRequest.RENDER_PHASE);

		return liferayPortletURL.toString();
	}

	private String _getSuccessURL(PortletRequest portletRequest) {
		return _portal.getCurrentURL(
			_portal.getHttpServletRequest(portletRequest));
	}

	@Reference
	private DLAppService _dlAppService;

	@Reference
	private DLOpenerOneDriveManager _dlOpenerOneDriveManager;

	@Reference
	private OAuth2Manager _oAuth2Manager;

	@Reference
	private Portal _portal;

	@Reference
	private PortletURLFactory _portletURLFactory;

	private final TransactionConfig _transactionConfig =
		TransactionConfig.Factory.create(
			Propagation.REQUIRED, new Class<?>[] {Exception.class});

	@Reference
	private UniqueFileEntryTitleProvider _uniqueFileEntryTitleProvider;

}