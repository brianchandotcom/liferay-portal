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

package com.liferay.login.web.portlet.action;

import com.liferay.login.web.constants.LoginPortletKeys;
import com.liferay.portal.CompanyMaxUsersException;
import com.liferay.portal.ContactNameException;
import com.liferay.portal.EmailAddressException;
import com.liferay.portal.GroupFriendlyURLException;
import com.liferay.portal.UserEmailAddressException;
import com.liferay.portal.kernel.captcha.CaptchaConfigurationException;
import com.liferay.portal.kernel.captcha.CaptchaTextException;
import com.liferay.portal.kernel.captcha.CaptchaUtil;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.JSONPortletResponseUtil;
import com.liferay.portal.kernel.portlet.LiferayWindowState;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.servlet.SessionMessages;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.JavaConstants;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.model.Company;
import com.liferay.portal.model.User;
import com.liferay.portal.security.auth.PrincipalException;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.ServiceContextFactory;
import com.liferay.portal.service.UserLocalService;
import com.liferay.portal.service.UserService;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.PropsValues;
import com.liferay.portlet.PortletURLFactoryUtil;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletConfig;
import javax.portlet.PortletRequest;
import javax.portlet.PortletURL;

import javax.servlet.http.HttpServletRequest;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Sergio González
 * @author Peter Fellwock
 */
@Component(
	property = {
		"javax.portlet.name=" + LoginPortletKeys.FAST_LOGIN,
		"javax.portlet.name=" + LoginPortletKeys.LOGIN,
		"mvc.command.name=/login/create_anonymous_account"
	},
	service = MVCActionCommand.class
)
public class CreateAnonymousAccountMVCActionCommand
	extends BaseMVCActionCommand {

	protected void addAnonymousUser(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		HttpServletRequest request = PortalUtil.getHttpServletRequest(
			actionRequest);

		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		boolean autoPassword = true;
		String password1 = null;
		String password2 = null;
		boolean autoScreenName = true;
		String screenName = null;
		String emailAddress = ParamUtil.getString(
			actionRequest, "emailAddress");
		long facebookId = 0;
		String openId = StringPool.BLANK;
		String firstName = ParamUtil.getString(actionRequest, "firstName");
		String lastName = ParamUtil.getString(actionRequest, "lastName");
		long prefixId = 0;
		long suffixId = 0;
		boolean male = true;
		int birthdayMonth = 0;
		int birthdayDay = 1;
		int birthdayYear = 1970;
		String jobTitle = null;
		long[] groupIds = null;
		long[] organizationIds = null;
		long[] roleIds = null;
		long[] userGroupIds = null;
		boolean sendEmail = false;

		ServiceContext serviceContext = ServiceContextFactory.getInstance(
			User.class.getName(), actionRequest);

		serviceContext.setAttribute("anonymousUser", true);

		if (PropsValues.CAPTCHA_CHECK_PORTAL_CREATE_ACCOUNT) {
			CaptchaUtil.check(actionRequest);
		}

		serviceContext.setWorkflowAction(WorkflowConstants.ACTION_SAVE_DRAFT);

		User user = _userService.addUser(
			themeDisplay.getCompanyId(), autoPassword, password1, password2,
			autoScreenName, screenName, emailAddress, facebookId, openId,
			themeDisplay.getLocale(), firstName, null, lastName, prefixId,
			suffixId, male, birthdayMonth, birthdayDay, birthdayYear, jobTitle,
			groupIds, organizationIds, roleIds, userGroupIds, sendEmail,
			serviceContext);

		_userLocalService.updateStatus(
			user.getUserId(), WorkflowConstants.STATUS_INCOMPLETE,
			new ServiceContext());

		// Session messages

		SessionMessages.add(request, "userAdded", user.getEmailAddress());
		SessionMessages.add(
			request, "userAddedPassword", user.getPasswordUnencrypted());
	}

	@Override
	protected void addSuccessMessage(
		ActionRequest actionRequest, ActionResponse actionResponse) {

		String portletId = (String)actionRequest.getAttribute(
			WebKeys.PORTLET_ID);

		if (!portletId.equals(LoginPortletKeys.FAST_LOGIN)) {
			super.addSuccessMessage(actionRequest, actionResponse);
		}
	}

	@Override
	protected void doProcessAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		Company company = themeDisplay.getCompany();

		if (!company.isStrangers()) {
			throw new PrincipalException.MustBeEnabled(
				company.getCompanyId(), PropsKeys.COMPANY_SECURITY_STRANGERS);
		}

		PortletConfig portletConfig = (PortletConfig)actionRequest.getAttribute(
			JavaConstants.JAVAX_PORTLET_CONFIG);

		String portletName = portletConfig.getPortletName();

		if (!portletName.equals(LoginPortletKeys.FAST_LOGIN)) {
			throw new PrincipalException("Unable to create anonymous account");
		}

		if (actionRequest.getRemoteUser() != null) {
			actionResponse.sendRedirect(themeDisplay.getPathMain());

			return;
		}

		String cmd = ParamUtil.getString(actionRequest, Constants.CMD);

		String emailAddress = ParamUtil.getString(
			actionRequest, "emailAddress");

		PortletURL portletURL = PortletURLFactoryUtil.create(
			actionRequest, LoginPortletKeys.FAST_LOGIN, themeDisplay.getPlid(),
			PortletRequest.RENDER_PHASE);

		portletURL.setParameter(
			"mvcRenderCommandName", "/login/login_redirect");
		portletURL.setParameter("emailAddress", emailAddress);
		portletURL.setParameter("anonymousUser", Boolean.TRUE.toString());
		portletURL.setWindowState(LiferayWindowState.POP_UP);

		JSONObject jsonObject = JSONFactoryUtil.createJSONObject();

		try {
			if (cmd.equals(Constants.ADD)) {
				addAnonymousUser(actionRequest, actionResponse);

				sendRedirect(
					actionRequest, actionResponse, portletURL.toString());
			}
			else if (cmd.equals(Constants.UPDATE)) {
				jsonObject = updateIncompleteUser(
					actionRequest, actionResponse);

				JSONPortletResponseUtil.writeJSON(
					actionRequest, actionResponse, jsonObject);
			}
		}
		catch (Exception e) {
			if (cmd.equals(Constants.UPDATE)) {
				jsonObject.putException(e);

				JSONPortletResponseUtil.writeJSON(
					actionRequest, actionResponse, jsonObject);
			}
			else if (e instanceof
						UserEmailAddressException.MustNotBeDuplicate) {

				User user = _userLocalService.getUserByEmailAddress(
					themeDisplay.getCompanyId(), emailAddress);

				if (user.getStatus() != WorkflowConstants.STATUS_INCOMPLETE) {
					SessionErrors.add(actionRequest, e.getClass());
				}
				else {
					sendRedirect(
						actionRequest, actionResponse, portletURL.toString());
				}
			}
			else if (e instanceof CaptchaConfigurationException ||
					 e instanceof CaptchaTextException ||
					 e instanceof CompanyMaxUsersException ||
					 e instanceof ContactNameException ||
					 e instanceof EmailAddressException ||
					 e instanceof GroupFriendlyURLException ||
					 e instanceof UserEmailAddressException) {

				SessionErrors.add(actionRequest, e.getClass(), e);
			}
			else {
				_log.error("Unable to create anonymous account", e);

				PortalUtil.sendError(e, actionRequest, actionResponse);
			}
		}
	}

	@Reference(unbind = "-")
	protected void setUserLocalService(UserLocalService userLocalService) {
		_userLocalService = userLocalService;
	}

	@Reference(unbind = "-")
	protected void setUserService(UserService userService) {
		_userService = userService;
	}

	protected JSONObject updateIncompleteUser(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		ServiceContext serviceContext = ServiceContextFactory.getInstance(
			User.class.getName(), actionRequest);

		boolean autoPassword = true;
		String password1 = null;
		String password2 = null;
		boolean autoScreenName = false;
		String screenName = null;
		String emailAddress = ParamUtil.getString(
			actionRequest, "emailAddress");
		long facebookId = 0;
		String openId = null;
		String firstName = null;
		String middleName = null;
		String lastName = null;
		long prefixId = 0;
		long suffixId = 0;
		boolean male = true;
		int birthdayMonth = 0;
		int birthdayDay = 1;
		int birthdayYear = 1970;
		String jobTitle = null;
		boolean updateUserInformation = false;
		boolean sendEmail = true;

		User user = _userService.updateIncompleteUser(
			themeDisplay.getCompanyId(), autoPassword, password1, password2,
			autoScreenName, screenName, emailAddress, facebookId, openId,
			themeDisplay.getLocale(), firstName, middleName, lastName, prefixId,
			suffixId, male, birthdayMonth, birthdayDay, birthdayYear, jobTitle,
			updateUserInformation, sendEmail, serviceContext);

		JSONObject jsonObject = JSONFactoryUtil.createJSONObject();

		if (user.getStatus() == WorkflowConstants.STATUS_APPROVED) {
			jsonObject.put("userStatus", "user_added");
		}
		else {
			jsonObject.put("userStatus", "user_pending");
		}

		return jsonObject;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		CreateAnonymousAccountMVCActionCommand.class);

	private volatile UserLocalService _userLocalService;
	private volatile UserService _userService;

}