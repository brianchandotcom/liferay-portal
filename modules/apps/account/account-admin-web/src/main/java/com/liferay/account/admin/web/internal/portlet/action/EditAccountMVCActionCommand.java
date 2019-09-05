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

package com.liferay.account.admin.web.internal.portlet.action;

import com.liferay.account.constants.AccountsPortletKeys;
import com.liferay.account.model.AccountEntry;
import com.liferay.account.service.AccountEntryLocalService;
import com.liferay.document.library.kernel.service.DLAppLocalService;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.Http;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.kernel.workflow.WorkflowConstants;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Albert Lee
 */
@Component(
	immediate = true,
	property = {
		"javax.portlet.name=" + AccountsPortletKeys.ACCOUNTS_ADMIN,
		"mvc.command.name=/account_admin/edit_account"
	},
	service = MVCActionCommand.class
)
public class EditAccountMVCActionCommand extends BaseMVCActionCommand {

	protected AccountEntry addAccountEntry(ActionRequest actionRequest)
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		long parentAccountEntryId = ParamUtil.getInteger(
			actionRequest, "parentAccountEntryId");
		String name = ParamUtil.getString(actionRequest, "name");
		String description = ParamUtil.getString(actionRequest, "description");

		byte[] logoBytes = _getLogoBytes(actionRequest);
		int status = _getStatus(actionRequest);

		return _accountEntryLocalService.addAccountEntry(
			themeDisplay.getUserId(), parentAccountEntryId, name, description,
			logoBytes, status);
	}

	@Override
	protected void doProcessAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		String cmd = ParamUtil.getString(actionRequest, Constants.CMD);
		String redirect = ParamUtil.getString(actionRequest, "redirect");

		try {
			if (cmd.equals(Constants.ADD)) {
				AccountEntry accountEntry = addAccountEntry(actionRequest);

				redirect = _http.setParameter(
					redirect, actionResponse.getNamespace() + "accountEntryId",
					accountEntry.getAccountEntryId());
			}
			else if (cmd.equals(Constants.UPDATE)) {
				updateAccountEntry(actionRequest);
			}

			if (Validator.isNotNull(redirect)) {
				sendRedirect(actionRequest, actionResponse, redirect);
			}
		}
		catch (Exception e) {
			String mvcPath = "/edit_account.jsp";

			if (e instanceof PrincipalException) {
				SessionErrors.add(actionRequest, e.getClass());

				mvcPath = "/error.jsp";
			}
			else {
				throw e;
			}

			actionResponse.setRenderParameter("mvcPath", mvcPath);
		}
	}

	protected void updateAccountEntry(ActionRequest actionRequest)
		throws Exception {

		long accountEntryId = ParamUtil.getLong(
			actionRequest, "accountEntryId");
		long parentAccountEntryId = ParamUtil.getInteger(
			actionRequest, "parentAccountEntryId");
		String name = ParamUtil.getString(actionRequest, "name");
		String description = ParamUtil.getString(actionRequest, "description");
		boolean deleteLogo = ParamUtil.getBoolean(actionRequest, "deleteLogo");

		byte[] logoBytes = _getLogoBytes(actionRequest);
		int status = _getStatus(actionRequest);

		_accountEntryLocalService.updateAccountEntry(
			accountEntryId, parentAccountEntryId, name, description, deleteLogo,
			logoBytes, status);
	}

	private byte[] _getLogoBytes(ActionRequest actionRequest) throws Exception {
		long fileEntryId = ParamUtil.getLong(actionRequest, "fileEntryId");

		if (fileEntryId == 0) {
			return null;
		}

		FileEntry fileEntry = _dlAppLocalService.getFileEntry(fileEntryId);

		return FileUtil.getBytes(fileEntry.getContentStream());
	}

	private int _getStatus(ActionRequest actionRequest) {
		boolean active = ParamUtil.getBoolean(actionRequest, "active");

		if (active) {
			return WorkflowConstants.STATUS_APPROVED;
		}

		return WorkflowConstants.STATUS_INACTIVE;
	}

	@Reference
	private AccountEntryLocalService _accountEntryLocalService;

	@Reference
	private DLAppLocalService _dlAppLocalService;

	@Reference
	private Http _http;

}