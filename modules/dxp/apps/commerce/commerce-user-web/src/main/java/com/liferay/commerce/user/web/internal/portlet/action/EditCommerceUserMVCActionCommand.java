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

package com.liferay.commerce.user.web.internal.portlet.action;

import com.liferay.commerce.user.constants.CommerceUserPortletKeys;
import com.liferay.commerce.user.service.CommerceUserService;
import com.liferay.document.library.kernel.service.DLAppLocalService;
import com.liferay.portal.kernel.bean.BeanParamUtil;
import com.liferay.portal.kernel.exception.ContactBirthdayException;
import com.liferay.portal.kernel.exception.ContactNameException;
import com.liferay.portal.kernel.exception.GroupFriendlyURLException;
import com.liferay.portal.kernel.exception.NoSuchUserException;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.UserEmailAddressException;
import com.liferay.portal.kernel.exception.UserFieldException;
import com.liferay.portal.kernel.exception.UserIdException;
import com.liferay.portal.kernel.exception.UserPasswordException;
import com.liferay.portal.kernel.exception.UserReminderQueryException;
import com.liferay.portal.kernel.exception.UserScreenNameException;
import com.liferay.portal.kernel.model.PasswordPolicy;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextFactory;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.CalendarFactoryUtil;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.util.PropsValues;
import com.liferay.users.admin.kernel.util.UsersAdmin;

import java.util.Calendar;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletSession;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alessio Antonio Rendina
 */
@Component(
	immediate = true,
	property = {
		"javax.portlet.name=" + CommerceUserPortletKeys.COMMERCE_USER,
		"mvc.command.name=editCommerceUser"
	},
	service = MVCActionCommand.class
)
public class EditCommerceUserMVCActionCommand extends BaseMVCActionCommand {

	@Override
	protected void doProcessAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		String cmd = ParamUtil.getString(actionRequest, Constants.CMD);

		try {
			if (cmd.equals(Constants.UPDATE)) {
				updateUser(actionRequest);
			}
			else if (cmd.equals("updatePassword")) {
				updatePassword(actionRequest);
			}
		}
		catch (Exception e) {
			if (e instanceof NoSuchUserException ||
				e instanceof PrincipalException) {

				SessionErrors.add(actionRequest, e.getClass());

				actionResponse.setRenderParameter("mvcPath", "/error.jsp");
			}
			else if (e instanceof ContactBirthdayException ||
					 e instanceof ContactNameException ||
					 e instanceof GroupFriendlyURLException ||
					 e instanceof UserEmailAddressException ||
					 e instanceof UserFieldException ||
					 e instanceof UserIdException ||
					 e instanceof UserPasswordException ||
					 e instanceof UserReminderQueryException ||
					 e instanceof UserScreenNameException) {

				SessionErrors.add(actionRequest, e.getClass(), e);
			}
			else {
				throw e;
			}
		}
	}

	protected void updatePassword(ActionRequest actionRequest)
		throws PortalException {

		long userId = ParamUtil.getLong(actionRequest, "userId");

		User user = _commerceUserService.getUser(userId);

		String newPassword1 = actionRequest.getParameter("password1");
		String newPassword2 = actionRequest.getParameter("password2");

		boolean passwordReset = ParamUtil.getBoolean(
			actionRequest, "passwordReset");

		PasswordPolicy passwordPolicy = user.getPasswordPolicy();

		if ((user.getLastLoginDate() == null) &&
			((passwordPolicy == null) ||
			 (passwordPolicy.isChangeable() &&
			  passwordPolicy.isChangeRequired()))) {

			passwordReset = true;
		}

		String reminderQueryQuestion = BeanParamUtil.getString(
			user, actionRequest, "reminderQueryQuestion");

		if (reminderQueryQuestion.equals(UsersAdmin.CUSTOM_QUESTION)) {
			reminderQueryQuestion = BeanParamUtil.getStringSilent(
				user, actionRequest, "reminderQueryCustomQuestion");
		}

		String reminderQueryAnswer = BeanParamUtil.getString(
			user, actionRequest, "reminderQueryAnswer");

		if (Validator.isNotNull(newPassword1) ||
			Validator.isNotNull(newPassword2)) {

			_commerceUserService.updatePassword(
				userId, newPassword1, newPassword2, passwordReset);
		}

		_commerceUserService.updatePasswordReset(userId, passwordReset);

		if (Validator.isNotNull(reminderQueryQuestion) &&
			Validator.isNotNull(reminderQueryAnswer)) {

			_commerceUserService.updateReminderQuery(
				userId, reminderQueryQuestion, reminderQueryAnswer);
		}

		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		if ((user.getUserId() == themeDisplay.getUserId()) &&
			PropsValues.SESSION_STORE_PASSWORD &&
			Validator.isNotNull(newPassword1)) {

			PortletSession portletSession = actionRequest.getPortletSession();

			portletSession.setAttribute(
				WebKeys.USER_PASSWORD, newPassword1,
				PortletSession.APPLICATION_SCOPE);
		}
	}

	protected void updateUser(ActionRequest actionRequest) throws Exception {
		long userId = ParamUtil.getLong(actionRequest, "userId");

		String screenName = ParamUtil.getString(actionRequest, "screenName");
		String emailAddress = ParamUtil.getString(
			actionRequest, "emailAddress");

		boolean deleteLogo = ParamUtil.getBoolean(actionRequest, "deleteLogo");

		byte[] portraitBytes = null;

		long fileEntryId = ParamUtil.getLong(actionRequest, "fileEntryId");

		if (fileEntryId > 0) {
			FileEntry fileEntry = _dlAppLocalService.getFileEntry(fileEntryId);

			portraitBytes = FileUtil.getBytes(fileEntry.getContentStream());
		}

		String languageId = ParamUtil.getString(actionRequest, "languageId");
		String firstName = ParamUtil.getString(actionRequest, "firstName");
		String middleName = ParamUtil.getString(actionRequest, "middleName");
		String lastName = ParamUtil.getString(actionRequest, "lastName");
		long prefixId = ParamUtil.getLong(actionRequest, "prefixId");
		long suffixId = ParamUtil.getLong(actionRequest, "suffixId");
		boolean male = ParamUtil.getBoolean(actionRequest, "male");

		Calendar birthdayCal = CalendarFactoryUtil.getCalendar();

		int birthdayMonth = ParamUtil.getInteger(
			actionRequest, "birthdayMonth", birthdayCal.get(Calendar.MONTH));
		int birthdayDay = ParamUtil.getInteger(
			actionRequest, "birthdayDay", birthdayCal.get(Calendar.DATE));
		int birthdayYear = ParamUtil.getInteger(
			actionRequest, "birthdayYear", birthdayCal.get(Calendar.YEAR));

		String jobTitle = ParamUtil.getString(actionRequest, "jobTitle");

		ServiceContext serviceContext = ServiceContextFactory.getInstance(
			User.class.getName(), actionRequest);

		_commerceUserService.updateUser(
			userId, screenName, emailAddress, !deleteLogo, portraitBytes,
			languageId, firstName, middleName, lastName, prefixId, suffixId,
			male, birthdayMonth, birthdayDay, birthdayYear, jobTitle,
			serviceContext);
	}

	@Reference
	private CommerceUserService _commerceUserService;

	@Reference
	private DLAppLocalService _dlAppLocalService;

}