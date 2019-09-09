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
import com.liferay.document.library.opener.onedrive.web.internal.oauth.OAuth2ControllerFactory;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextFactory;
import com.liferay.portal.kernel.util.ParamUtil;

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
		"mvc.command.name=/document_library/check_in_in_office365"
	},
	service = MVCActionCommand.class
)
public class CheckInInOneDriveMVCActionCommand extends BaseMVCActionCommand {

	@Override
	protected void doProcessAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		OAuth2Controller oAuth2Controller =
			_oAuth2ControllerFactory.getRedirectingOAuth2Controller();

		oAuth2Controller.execute(
			actionRequest, actionResponse, this::_executeCommand);
	}

	private JSONObject _executeCommand(PortletRequest portletRequest)
		throws PortalException {

		long fileEntryId = ParamUtil.getLong(portletRequest, "fileEntryId");

		DLVersionNumberIncrease dlVersionNumberIncrease =
			DLVersionNumberIncrease.valueOf(
				portletRequest.getParameter("versionIncrease"),
				DLVersionNumberIncrease.AUTOMATIC);
		String changeLog = ParamUtil.getString(portletRequest, "changeLog");

		ServiceContext serviceContext = ServiceContextFactory.getInstance(
			portletRequest);

		_dlAppService.checkInFileEntry(
			fileEntryId, dlVersionNumberIncrease, changeLog, serviceContext);

		return null;
	}

	@Reference
	private DLAppService _dlAppService;

	@Reference
	private OAuth2ControllerFactory _oAuth2ControllerFactory;

}