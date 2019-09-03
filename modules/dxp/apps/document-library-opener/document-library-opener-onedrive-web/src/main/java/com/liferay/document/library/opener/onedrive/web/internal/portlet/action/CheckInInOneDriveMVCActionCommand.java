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
import com.liferay.document.library.kernel.model.DLVersionNumberIncrease;
import com.liferay.document.library.kernel.service.DLAppService;
import com.liferay.document.library.opener.onedrive.web.internal.oauth.OAuth2Controller;
import com.liferay.document.library.opener.onedrive.web.internal.oauth.OAuth2Manager;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.portlet.PortletURLFactory;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextFactory;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.ResourceBundleLoader;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;

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
		"mvc.command.name=/document_library/check_in_in_office365"
	},
	service = MVCActionCommand.class
)
public class CheckInInOneDriveMVCActionCommand extends BaseMVCActionCommand {

	@Activate
	public void activate() {
		_oAuth2Controller = new OAuth2Controller(
			_language, _oAuth2Manager, _portal, _portletURLFactory,
			_resourceBundleLoader);
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

	private JSONObject _executeCommand(ActionRequest actionRequest)
		throws PortalException {

		long fileEntryId = ParamUtil.getLong(actionRequest, "fileEntryId");

		DLVersionNumberIncrease dlVersionNumberIncrease =
			DLVersionNumberIncrease.valueOf(
				actionRequest.getParameter("versionIncrease"),
				DLVersionNumberIncrease.AUTOMATIC);
		String changeLog = ParamUtil.getString(actionRequest, "changeLog");

		ServiceContext serviceContext = ServiceContextFactory.getInstance(
			actionRequest);

		_dlAppService.checkInFileEntry(
			fileEntryId, dlVersionNumberIncrease, changeLog, serviceContext);

		return null;
	}

	@Reference
	private DLAppService _dlAppService;

	@Reference
	private Language _language;

	private OAuth2Controller _oAuth2Controller;

	@Reference
	private OAuth2Manager _oAuth2Manager;

	@Reference
	private Portal _portal;

	@Reference
	private PortletURLFactory _portletURLFactory;

	@Reference(
		target = "(bundle.symbolic.name=com.liferay.document.library.opener.onedrive.web)"
	)
	private ResourceBundleLoader _resourceBundleLoader;

}