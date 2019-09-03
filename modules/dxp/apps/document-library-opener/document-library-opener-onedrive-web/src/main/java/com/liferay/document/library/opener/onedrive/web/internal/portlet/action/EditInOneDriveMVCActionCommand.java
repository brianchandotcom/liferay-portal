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

package com.liferay.document.library.opener.onedrive.web.internal.portlet.action;

import com.liferay.document.library.constants.DLPortletKeys;
import com.liferay.document.library.kernel.service.DLAppService;
import com.liferay.document.library.opener.onedrive.web.internal.DLOpenerOneDriveFileReference;
import com.liferay.document.library.opener.onedrive.web.internal.DLOpenerOneDriveManager;
import com.liferay.document.library.opener.onedrive.web.internal.constants.DLOpenerOneDriveWebKeys;
import com.liferay.document.library.opener.onedrive.web.internal.oauth.OAuth2Controller;
import com.liferay.document.library.opener.onedrive.web.internal.oauth.OAuth2Manager;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.portlet.PortletURLFactory;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextFactory;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.transaction.Propagation;
import com.liferay.portal.kernel.transaction.TransactionConfig;
import com.liferay.portal.kernel.transaction.TransactionInvokerUtil;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.WebKeys;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletRequest;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
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

	@Activate
	public void activate() {
		_oAuth2Controller = new OAuth2Controller(
			_oAuth2Manager, _portal, _portletURLFactory);
	}

	@Deactivate
	public void deactivate() {
		_oAuth2Controller = null;
	}

	@Override
	protected void doProcessAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		_oAuth2Controller.execute(
			actionRequest, actionResponse, this::_executeCommand);
	}

	private DLOpenerOneDriveFileReference _checkOutOneDriveFileEntry(
			long fileEntryId, ServiceContext serviceContext)
		throws PortalException {

		_dlAppService.checkOutFileEntry(fileEntryId, serviceContext);

		return _dlOpenerOneDriveManager.checkOut(
			serviceContext.getUserId(),
			_dlAppService.getFileEntry(fileEntryId));
	}

	private JSONObject _executeCommand(ActionRequest actionRequest)
		throws PortalException {

		String cmd = ParamUtil.getString(actionRequest, Constants.CMD);
		long fileEntryId = ParamUtil.getLong(actionRequest, "fileEntryId");

		if (cmd.equals(Constants.CHECKOUT)) {
			try {
				ServiceContext serviceContext =
					ServiceContextFactory.getInstance(actionRequest);

				hideDefaultSuccessMessage(actionRequest);

				return _saveDLOpenerOneDriveFileReference(
					actionRequest,
					TransactionInvokerUtil.invoke(
						_transactionConfig,
						() -> _checkOutOneDriveFileEntry(
							fileEntryId, serviceContext)));
			}
			catch (PortalException pe) {
				throw pe;
			}
			catch (RuntimeException re) {
				throw re;
			}
			catch (Throwable throwable) {
				throw new PortalException(throwable);
			}
		}
		else if (cmd.equals(Constants.EDIT)) {
			ThemeDisplay themeDisplay =
				(ThemeDisplay)actionRequest.getAttribute(WebKeys.THEME_DISPLAY);

			return _saveDLOpenerOneDriveFileReference(
				actionRequest,
				_dlOpenerOneDriveManager.requestEditAccess(
					themeDisplay.getUserId(),
					_dlAppService.getFileEntry(fileEntryId)));
		}
		else {
			throw new IllegalArgumentException();
		}
	}

	private JSONObject _saveDLOpenerOneDriveFileReference(
		PortletRequest portletRequest,
		DLOpenerOneDriveFileReference dlOpenerOneDriveFileReference) {

		portletRequest.setAttribute(
			DLOpenerOneDriveWebKeys.DL_OPENER_ONE_DRIVE_FILE_REFERENCE,
			dlOpenerOneDriveFileReference);

		return JSONUtil.put(
			DLOpenerOneDriveWebKeys.DL_OPENER_ONE_DRIVE_FILE_REFERENCE,
			dlOpenerOneDriveFileReference);
	}

	@Reference
	private DLAppService _dlAppService;

	@Reference
	private DLOpenerOneDriveManager _dlOpenerOneDriveManager;

	private OAuth2Controller _oAuth2Controller;

	@Reference
	private OAuth2Manager _oAuth2Manager;

	@Reference
	private Portal _portal;

	@Reference
	private PortletURLFactory _portletURLFactory;

	private final TransactionConfig _transactionConfig =
		TransactionConfig.Factory.create(
			Propagation.REQUIRED, new Class<?>[] {Exception.class});

}