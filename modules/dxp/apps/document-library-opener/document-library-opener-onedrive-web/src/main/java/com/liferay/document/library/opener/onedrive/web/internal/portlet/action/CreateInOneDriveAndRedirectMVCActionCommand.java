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
import com.liferay.document.library.opener.onedrive.web.internal.oauth.OAuth2Controller;
import com.liferay.document.library.opener.onedrive.web.internal.oauth.OAuth2ControllerFactory;
import com.liferay.document.library.opener.onedrive.web.internal.portlet.action.util.CreateInOneDriveHelper;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;

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
		"mvc.command.name=/document_library/create_in_office365_and_redirect"
	},
	service = MVCActionCommand.class
)
public class CreateInOneDriveAndRedirectMVCActionCommand
	extends BaseMVCActionCommand {

	@Override
	protected void doProcessAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		OAuth2Controller oAuth2Controller =
			_oAuth2ControllerFactory.getOAuth2Controller();

		OAuth2Controller.OAuth2ExecutorWithRedirect oAuth2ExecutorWithRedirect =
			oAuth2Controller.new OAuth2ExecutorWithRedirect();

		oAuth2ExecutorWithRedirect.execute(
			actionRequest, actionResponse,
			_createInOneDriveHelper::executeCommand);
	}

	@Reference
	private CreateInOneDriveHelper _createInOneDriveHelper;

	@Reference
	private OAuth2ControllerFactory _oAuth2ControllerFactory;

}