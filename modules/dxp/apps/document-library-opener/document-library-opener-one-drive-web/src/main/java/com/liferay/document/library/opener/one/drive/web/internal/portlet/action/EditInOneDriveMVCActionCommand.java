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
import com.liferay.document.library.opener.oauth.OAuth2State;
import com.liferay.document.library.opener.one.drive.web.internal.oauth.AccessToken;
import com.liferay.document.library.opener.one.drive.web.internal.oauth.OAuth2Manager;
import com.liferay.document.library.opener.one.drive.web.internal.oauth.OAuth2StateUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.LiferayPortletURL;
import com.liferay.portal.kernel.portlet.PortletURLFactory;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
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
			_executeCommand(accessTokenOptional.get());
		}
		else {
			String state = PwdGenerator.getPassword(5);

			OAuth2StateUtil.save(
				_portal.getHttpServletRequest(actionRequest),
				new OAuth2State(
					userId, _getSuccessURL(actionRequest),
					_getFailureURL(actionRequest), state));

			actionResponse.sendRedirect(
				_oAuth2Manager.getAuthorizationURL(
					companyId, _portal.getPortalURL(actionRequest), state));
		}
	}

	private void _executeCommand(AccessToken accessToken) {
		if (_log.isDebugEnabled()) {
			_log.debug("Logged successfully " + accessToken.getAccessToken());
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

	private static final Log _log = LogFactoryUtil.getLog(
		EditInOneDriveMVCActionCommand.class);

	@Reference
	private OAuth2Manager _oAuth2Manager;

	@Reference
	private Portal _portal;

	@Reference
	private PortletURLFactory _portletURLFactory;

}