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
import com.liferay.account.exception.AccountEntryDomainsException;
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
import com.liferay.portal.kernel.util.StringUtil;
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
		"javax.portlet.name=" + AccountsPortletKeys.ACCOUNT_ENTRIES_ADMIN,
		"mvc.command.name=/account_admin/edit_account_entry"
	},
	service = MVCActionCommand.class
)
public class EditAccountEntryMVCActionCommand extends BaseMVCActionCommand {

	protected AccountEntry addAccountEntry(ActionRequest actionRequest)
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		long parentAccountEntryId = ParamUtil.getInteger(
			actionRequest, "parentAccountEntryId");
		String name = ParamUtil.getString(actionRequest, "name");
		String description = ParamUtil.getString(actionRequest, "description");
		String domains = ParamUtil.getString(actionRequest, "domains");

		return _accountEntryLocalService.addAccountEntry(
			themeDisplay.getUserId(), parentAccountEntryId, name, description,
			StringUtil.split(domains), _getLogoBytes(actionRequest),
			_getStatus(actionRequest));
	}

	@Override
	protected void doProcessAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		String cmd = ParamUtil.getString(actionRequest, Constants.CMD);

		try {
			String redirect = ParamUtil.getString(actionRequest, "redirect");

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
			String mvcPath = "/account_entries_admin/edit_account_entry.jsp";

			if (e instanceof PrincipalException) {
				SessionErrors.add(actionRequest, e.getClass());

				mvcPath = "/account_entries_admin/error.jsp";
			}
			else if (e instanceof AccountEntryDomainsException) {
				SessionErrors.add(actionRequest, e.getClass());
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
		String domains = ParamUtil.getString(actionRequest, "domains");

		_accountEntryLocalService.updateAccountEntry(
			accountEntryId, parentAccountEntryId, name, description, deleteLogo,
			StringUtil.split(domains), _getLogoBytes(actionRequest),
			_getStatus(actionRequest));
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