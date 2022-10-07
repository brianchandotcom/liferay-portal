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

import com.liferay.account.admin.web.internal.portlet.action.util.TicketUtil;
import com.liferay.account.constants.AccountPortletKeys;
import com.liferay.account.service.AccountEntryUserRelLocalService;
import com.liferay.account.service.AccountRoleLocalService;
import com.liferay.document.library.kernel.service.DLAppLocalService;
import com.liferay.portal.aop.AopService;
import com.liferay.portal.kernel.exception.NoSuchTicketException;
import com.liferay.portal.kernel.model.Ticket;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextFactory;
import com.liferay.portal.kernel.service.TicketLocalService;
import com.liferay.portal.kernel.service.UserService;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.servlet.SessionMessages;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.transaction.Transactional;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.util.PropsValues;

import java.time.Month;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletException;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Pei-Jung Lan
 */
@Component(
	immediate = true,
	property = {
		"javax.portlet.name=" + AccountPortletKeys.ACCOUNT_USERS_REGISTRATION,
		"mvc.command.name=/account_admin/create_account_user"
	},
	service = AopService.class
)
public class CreateAccountUserMVCActionCommand
	extends BaseMVCActionCommand implements AopService, MVCActionCommand {

	@Override
	@Transactional(rollbackFor = Exception.class)
	public boolean processAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws PortletException {

		return super.processAction(actionRequest, actionResponse);
	}

	@Override
	protected void doProcessAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		Ticket ticket = TicketUtil.getTicket(
			actionRequest, _ticketLocalService);

		if (ticket == null) {
			SessionErrors.add(actionRequest, NoSuchTicketException.class);

			actionResponse.setRenderParameter(
				"mvcPath", "/account_user_registration/error.jsp");
		}

		User user = _addUser(actionRequest);

		_ticketLocalService.deleteTicket(ticket);

		if (user.getStatus() == WorkflowConstants.STATUS_APPROVED) {
			SessionMessages.add(
				_portal.getHttpServletRequest(actionRequest), "userAdded",
				user.getEmailAddress());
		}
		else {
			SessionMessages.add(
				_portal.getHttpServletRequest(actionRequest), "userPending",
				user.getEmailAddress());
		}

		sendRedirect(actionRequest, actionResponse);
	}

	private User _addUser(ActionRequest actionRequest) throws Exception {
		boolean autoPassword = true;
		String password1 = null;
		String password2 = null;
		boolean autoScreenName = false;
		String screenName = ParamUtil.getString(actionRequest, "screenName");
		String emailAddress = ParamUtil.getString(
			actionRequest, "emailAddress");
		String languageId = ParamUtil.getString(actionRequest, "languageId");
		String firstName = ParamUtil.getString(actionRequest, "firstName");
		String middleName = ParamUtil.getString(actionRequest, "middleName");
		String lastName = ParamUtil.getString(actionRequest, "lastName");
		long prefixListTypeId = ParamUtil.getInteger(
			actionRequest, "prefixListTypeId");
		long suffixListTypeId = ParamUtil.getInteger(
			actionRequest, "suffixListTypeId");
		boolean male = true;
		int birthdayMonth = Month.JANUARY.getValue();
		int birthdayDay = 1;
		int birthdayYear = 1970;
		String jobTitle = ParamUtil.getString(actionRequest, "jobTitle");
		long[] groupIds = null;
		long[] organizationIds = null;
		long[] roleIds = null;
		long[] userGroupIds = null;
		boolean sendEmail = true;

		ServiceContext serviceContext = ServiceContextFactory.getInstance(
			User.class.getName(), actionRequest);

		if (PropsValues.LOGIN_CREATE_ACCOUNT_ALLOW_CUSTOM_PASSWORD) {
			autoPassword = false;

			password1 = ParamUtil.getString(actionRequest, "password1");
			password2 = ParamUtil.getString(actionRequest, "password2");
		}

		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		User user = _userService.addUser(
			themeDisplay.getCompanyId(), autoPassword, password1, password2,
			autoScreenName, screenName, emailAddress,
			LocaleUtil.fromLanguageId(languageId), firstName, middleName,
			lastName, prefixListTypeId, suffixListTypeId, male, birthdayMonth,
			birthdayDay, birthdayYear, jobTitle, groupIds, organizationIds,
			roleIds, userGroupIds, sendEmail, serviceContext);

		byte[] portraitBytes = null;

		long fileEntryId = ParamUtil.getLong(actionRequest, "fileEntryId");

		if (fileEntryId > 0) {
			FileEntry fileEntry = _dlAppLocalService.getFileEntry(fileEntryId);

			portraitBytes = FileUtil.getBytes(fileEntry.getContentStream());
		}

		if (portraitBytes != null) {
			_userService.updatePortrait(user.getUserId(), portraitBytes);
		}

		long accountEntryId = ParamUtil.getLong(
			actionRequest, "accountEntryId");

		_accountEntryUserRelLocalService.addAccountEntryUserRels(
			accountEntryId, new long[] {user.getUserId()});

		long[] accountRoleIds = ParamUtil.getLongValues(
			actionRequest, "accountRoleIds");

		if (ArrayUtil.isNotEmpty(accountRoleIds)) {
			_accountRoleLocalService.associateUser(
				accountEntryId, accountRoleIds, user.getUserId());
		}

		return user;
	}

	@Reference
	private AccountEntryUserRelLocalService _accountEntryUserRelLocalService;

	@Reference
	private AccountRoleLocalService _accountRoleLocalService;

	@Reference
	private DLAppLocalService _dlAppLocalService;

	@Reference
	private Portal _portal;

	@Reference
	private TicketLocalService _ticketLocalService;

	@Reference
	private UserService _userService;

}