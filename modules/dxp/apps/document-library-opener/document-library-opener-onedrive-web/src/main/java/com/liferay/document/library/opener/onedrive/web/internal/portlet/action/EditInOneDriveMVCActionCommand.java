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
import com.liferay.document.library.opener.onedrive.web.internal.oauth.OAuth2Controller;
import com.liferay.document.library.opener.onedrive.web.internal.oauth.OAuth2ControllerFactory;
import com.liferay.document.library.opener.onedrive.web.internal.portlet.action.util.OneDriveURLUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.portlet.LiferayPortletURL;
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
import com.liferay.portal.kernel.util.ResourceBundleLoader;
import com.liferay.portal.kernel.util.WebKeys;

import java.util.Locale;
import java.util.ResourceBundle;

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

		OAuth2Controller oAuth2Controller = getOAuth2Controller();

		oAuth2Controller.execute(
			actionRequest, actionResponse, this::_executeCommand);
	}

	protected OAuth2Controller getOAuth2Controller() {
		return _oAuth2ControllerFactory.getJSONOAuth2Controller(
			this::_getSuccessURL);
	}

	private DLOpenerOneDriveFileReference _checkOutOneDriveFileEntry(
			long fileEntryId, ServiceContext serviceContext)
		throws PortalException {

		_dlAppService.checkOutFileEntry(fileEntryId, serviceContext);

		return _dlOpenerOneDriveManager.checkOut(
			serviceContext.getUserId(),
			_dlAppService.getFileEntry(fileEntryId));
	}

	private JSONObject _executeCommand(PortletRequest portletRequest)
		throws PortalException {

		String cmd = ParamUtil.getString(portletRequest, Constants.CMD);
		long fileEntryId = ParamUtil.getLong(portletRequest, "fileEntryId");

		if (cmd.equals(Constants.CHECKOUT)) {
			try {
				ServiceContext serviceContext =
					ServiceContextFactory.getInstance(portletRequest);

				DLOpenerOneDriveFileReference dlOpenerOneDriveFileReference =
					TransactionInvokerUtil.invoke(
						_transactionConfig,
						() -> _checkOutOneDriveFileEntry(
							fileEntryId, serviceContext));

				String oneDriveBackgroundTaskStatusURL =
					OneDriveURLUtil.getBackgroundTaskStatusURL(
						dlOpenerOneDriveFileReference, _portal, portletRequest,
						_portletURLFactory);

				return JSONUtil.put(
					"dialogMessage",
					_translate(
						_portal.getLocale(portletRequest),
						"you-are-being-redirected-to-an-external-editor-to-" +
							"edit-this-document")
				).put(
					"oneDriveBackgroundTaskStatusURL",
					oneDriveBackgroundTaskStatusURL
				);
			}
			catch (PortalException | RuntimeException e) {
				throw e;
			}
			catch (Throwable throwable) {
				throw new PortalException(throwable);
			}
		}
		else if (cmd.equals(Constants.EDIT)) {
			ThemeDisplay themeDisplay =
				(ThemeDisplay)portletRequest.getAttribute(
					WebKeys.THEME_DISPLAY);

			DLOpenerOneDriveFileReference dlOpenerOneDriveFileReference =
				_dlOpenerOneDriveManager.requestEditAccess(
					themeDisplay.getUserId(),
					_dlAppService.getFileEntry(fileEntryId));

			String oneDriveBackgroundTaskStatusURL =
				OneDriveURLUtil.getBackgroundTaskStatusURL(
					dlOpenerOneDriveFileReference, _portal, portletRequest,
					_portletURLFactory);

			return JSONUtil.put(
				"dialogMessage",
				_translate(
					_portal.getLocale(portletRequest),
					"you-are-being-redirected-to-an-external-editor-to-edit-" +
						"this-document")
			).put(
				"oneDriveBackgroundTaskStatusURL",
				oneDriveBackgroundTaskStatusURL
			);
		}
		else {
			throw new IllegalArgumentException();
		}
	}

	private String _getSuccessURL(PortletRequest portletRequest) {
		LiferayPortletURL liferayPortletURL = _portletURLFactory.create(
			portletRequest, _portal.getPortletId(portletRequest),
			PortletRequest.ACTION_PHASE);

		liferayPortletURL.setParameters(portletRequest.getParameterMap());

		liferayPortletURL.setParameter(
			ActionRequest.ACTION_NAME,
			"/document_library/edit_in_office365_and_redirect");

		return liferayPortletURL.toString();
	}

	private String _translate(Locale locale, String key) {
		ResourceBundle resourceBundle =
			_resourceBundleLoader.loadResourceBundle(locale);

		return _language.get(resourceBundle, key);
	}

	@Reference
	private DLAppService _dlAppService;

	@Reference
	private DLOpenerOneDriveManager _dlOpenerOneDriveManager;

	@Reference
	private Language _language;

	@Reference
	private OAuth2ControllerFactory _oAuth2ControllerFactory;

	@Reference
	private Portal _portal;

	@Reference
	private PortletURLFactory _portletURLFactory;

	@Reference(
		target = "(bundle.symbolic.name=com.liferay.document.library.opener.onedrive.web)"
	)
	private ResourceBundleLoader _resourceBundleLoader;

	private final TransactionConfig _transactionConfig =
		TransactionConfig.Factory.create(
			Propagation.REQUIRED, new Class<?>[] {Exception.class});

}