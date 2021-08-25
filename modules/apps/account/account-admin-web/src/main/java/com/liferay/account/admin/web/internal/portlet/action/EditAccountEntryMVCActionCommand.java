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

import com.liferay.account.constants.AccountConstants;
import com.liferay.account.constants.AccountPortletKeys;
import com.liferay.account.exception.DuplicateAccountEntryExternalReferenceCodeException;
import com.liferay.account.model.AccountEntry;
import com.liferay.account.service.AccountEntryLocalService;
import com.liferay.account.service.AccountEntryUserRelLocalService;
import com.liferay.document.library.kernel.service.DLAppLocalService;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.service.ServiceContextFactory;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.transaction.Propagation;
import com.liferay.portal.kernel.transaction.TransactionConfig;
import com.liferay.portal.kernel.transaction.TransactionInvokerUtil;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.Http;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.kernel.workflow.WorkflowConstants;

import java.util.Objects;

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
		"javax.portlet.name=" + AccountPortletKeys.ACCOUNT_ENTRIES_ADMIN,
		"javax.portlet.name=" + AccountPortletKeys.ACCOUNT_ENTRIES_MANAGEMENT,
		"mvc.command.name=/account_admin/edit_account_entry"
	},
	service = MVCActionCommand.class
)
public class EditAccountEntryMVCActionCommand extends BaseMVCActionCommand {

	protected AccountEntry addAccountEntry(ActionRequest actionRequest)
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		String name = ParamUtil.getString(actionRequest, "name");
		String description = ParamUtil.getString(actionRequest, "description");
		String[] domains = new String[0];
		String emailAddress = ParamUtil.getString(
			actionRequest, "emailAddress");
		String taxIdNumber = ParamUtil.getString(actionRequest, "taxIdNumber");

		String type = ParamUtil.getString(
			actionRequest, "type",
			AccountConstants.ACCOUNT_ENTRY_TYPE_BUSINESS);

		if (Objects.equals(
				AccountConstants.ACCOUNT_ENTRY_TYPE_BUSINESS, type)) {

			domains = ParamUtil.getStringValues(actionRequest, "domains");
		}

		AccountEntry accountEntry = _accountEntryLocalService.addAccountEntry(
			themeDisplay.getUserId(), AccountConstants.ACCOUNT_ENTRY_ID_DEFAULT,
			name, description, domains, emailAddress,
			_getLogoBytes(actionRequest), taxIdNumber, type,
			_getStatus(actionRequest),
			ServiceContextFactory.getInstance(
				AccountEntry.class.getName(), actionRequest));

		return _accountEntryLocalService.updateExternalReferenceCode(
			accountEntry,
			ParamUtil.getString(actionRequest, "externalReferenceCode"));
	}

	@Override
	protected void doProcessAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		String cmd = ParamUtil.getString(actionRequest, Constants.CMD);

		try {
			TransactionInvokerUtil.invoke(
				_transactionConfig,
				() -> {
					String redirect = ParamUtil.getString(
						actionRequest, "redirect");

					if (cmd.equals(Constants.ADD)) {
						AccountEntry accountEntry = addAccountEntry(
							actionRequest);

						redirect = _http.setParameter(
							redirect,
							actionResponse.getNamespace() + "accountEntryId",
							accountEntry.getAccountEntryId());
					}
					else if (cmd.equals(Constants.UPDATE)) {
						updateAccountEntry(actionRequest);
					}

					if (Validator.isNotNull(redirect)) {
						sendRedirect(actionRequest, actionResponse, redirect);
					}

					return null;
				});
		}
		catch (Exception exception) {
			if (exception instanceof PrincipalException) {
				SessionErrors.add(actionRequest, exception.getClass());

				actionResponse.setRenderParameter(
					"mvcPath", "/account_entries_admin/error.jsp");
			}
			else if (exception instanceof
						DuplicateAccountEntryExternalReferenceCodeException) {

				SessionErrors.add(actionRequest, exception.getClass());

				hideDefaultErrorMessage(actionRequest);

				actionResponse.setRenderParameter(
					"mvcRenderCommandName",
					"/account_admin/edit_account_entry");
			}
			else {
				throw exception;
			}
		}
		catch (Throwable throwable) {
			throw new Exception(throwable);
		}
	}

	protected void updateAccountEntry(ActionRequest actionRequest)
		throws Exception {

		long accountEntryId = ParamUtil.getLong(
			actionRequest, "accountEntryId");

		AccountEntry accountEntry = _accountEntryLocalService.getAccountEntry(
			accountEntryId);

		String name = ParamUtil.getString(actionRequest, "name");
		String description = ParamUtil.getString(actionRequest, "description");
		boolean deleteLogo = ParamUtil.getBoolean(actionRequest, "deleteLogo");
		String[] domains = ParamUtil.getStringValues(actionRequest, "domains");
		String emailAddress = ParamUtil.getString(
			actionRequest, "emailAddress");
		String taxIdNumber = ParamUtil.getString(actionRequest, "taxIdNumber");

		accountEntry = _accountEntryLocalService.updateAccountEntry(
			accountEntryId, accountEntry.getParentAccountEntryId(), name,
			description, deleteLogo, domains, emailAddress,
			_getLogoBytes(actionRequest), taxIdNumber,
			_getStatus(actionRequest),
			ServiceContextFactory.getInstance(
				AccountEntry.class.getName(), actionRequest));

		accountEntry = _accountEntryLocalService.updateExternalReferenceCode(
			accountEntry,
			ParamUtil.getString(actionRequest, "externalReferenceCode"));

		if (Objects.equals(
				AccountConstants.ACCOUNT_ENTRY_TYPE_PERSON,
				accountEntry.getType())) {

			long personAccountEntryUserId = ParamUtil.getLong(
				actionRequest, "personAccountEntryUserId");

			_accountEntryUserRelLocalService.setPersonTypeAccountEntryUser(
				accountEntryId, personAccountEntryUserId);
		}
		else {
			if (_log.isDebugEnabled()) {
				_log.debug(
					"Skipping user updates for business account entry: " +
						accountEntryId);
			}
		}
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

	private static final Log _log = LogFactoryUtil.getLog(
		EditAccountEntryMVCActionCommand.class);

	private static final TransactionConfig _transactionConfig =
		TransactionConfig.Factory.create(
			Propagation.REQUIRED, new Class<?>[] {Exception.class});

	@Reference
	private AccountEntryLocalService _accountEntryLocalService;

	@Reference
	private AccountEntryUserRelLocalService _accountEntryUserRelLocalService;

	@Reference
	private DLAppLocalService _dlAppLocalService;

	@Reference
	private Http _http;

}