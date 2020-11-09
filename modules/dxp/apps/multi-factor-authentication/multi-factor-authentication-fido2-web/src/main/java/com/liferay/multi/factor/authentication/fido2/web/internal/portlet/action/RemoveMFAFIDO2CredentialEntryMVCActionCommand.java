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

package com.liferay.multi.factor.authentication.fido2.web.internal.portlet.action;

import com.liferay.multi.factor.authentication.fido2.credential.model.MFAFIDO2CredentialEntry;
import com.liferay.multi.factor.authentication.fido2.credential.service.MFAFIDO2CredentialEntryLocalService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.users.admin.constants.UsersAdminPortletKeys;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Arthur Chan
 */
@Component(
	property = {
		"javax.portlet.name=" + UsersAdminPortletKeys.MY_ACCOUNT,
		"javax.portlet.name=" + UsersAdminPortletKeys.USERS_ADMIN,
		"mvc.command.name=/multi-factor-authentication-fido2/remove_mfa_fido2_credential_entry"
	},
	service = MVCActionCommand.class
)
public class RemoveMFAFIDO2CredentialEntryMVCActionCommand
	extends BaseMVCActionCommand {

	@Override
	protected void doProcessAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		MFAFIDO2CredentialEntry mfaFIDO2CredentialEntry =
			_mfaFIDO2CredentialEntryLocalService.getMFAFIDO2CredentialEntry(
				ParamUtil.getLong(actionRequest, "mfaFIDO2CredentialEntryId"));

		if ((themeDisplay == null) ||
			(themeDisplay.getUserId() != mfaFIDO2CredentialEntry.getUserId())) {

			throw new PrincipalException();
		}

		try {
			_mfaFIDO2CredentialEntryLocalService.deleteMFAFIDO2CredentialEntry(
				mfaFIDO2CredentialEntry.getMfaFIDO2CredentialEntryId());
		}
		catch (PortalException portalException) {
			if (_log.isWarnEnabled()) {
				_log.warn(portalException, portalException);
			}
		}

		String redirect = _portal.escapeRedirect(
			ParamUtil.getString(actionRequest, "redirect"));

		if (Validator.isBlank(redirect)) {
			redirect = themeDisplay.getPortalURL();
		}

		actionResponse.sendRedirect(redirect);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		RemoveMFAFIDO2CredentialEntryMVCActionCommand.class);

	@Reference
	private MFAFIDO2CredentialEntryLocalService
		_mfaFIDO2CredentialEntryLocalService;

	@Reference
	private Portal _portal;

}