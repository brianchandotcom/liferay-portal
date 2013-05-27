/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.login.action;

import com.liferay.portal.CompanyMaxUsersException;
import com.liferay.portal.ContactFirstNameException;
import com.liferay.portal.ContactFullNameException;
import com.liferay.portal.ContactLastNameException;
import com.liferay.portal.DuplicateUserEmailAddressException;
import com.liferay.portal.EmailAddressException;
import com.liferay.portal.GroupFriendlyURLException;
import com.liferay.portal.ReservedUserEmailAddressException;
import com.liferay.portal.UserEmailAddressException;
import com.liferay.portal.kernel.captcha.CaptchaTextException;
import com.liferay.portal.kernel.captcha.CaptchaUtil;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.LiferayWindowState;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.servlet.SessionMessages;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.model.User;
import com.liferay.portal.security.auth.PrincipalException;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.ServiceContextFactory;
import com.liferay.portal.service.UserLocalServiceUtil;
import com.liferay.portal.service.UserServiceUtil;
import com.liferay.portal.struts.PortletAction;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.PortletKeys;
import com.liferay.portal.util.PropsValues;
import com.liferay.portal.util.WebKeys;
import com.liferay.portlet.PortletURLFactoryUtil;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletConfig;
import javax.portlet.PortletRequest;
import javax.portlet.PortletURL;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * @author Sergio González
 */
public class CreateAnonymousAccountAction extends PortletAction {

	@Override
	public void processAction(
			ActionMapping mapping, ActionForm form, PortletConfig portletConfig,
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		String portletName = portletConfig.getPortletName();

		if (!portletName.equals(PortletKeys.FAST_LOGIN)) {
			throw new PrincipalException();
		}

		if (actionRequest.getRemoteUser() != null) {
			actionResponse.sendRedirect(themeDisplay.getPathMain());

			return;
		}

		String cmd = ParamUtil.getString(actionRequest, Constants.CMD);

		String emailAddress = ParamUtil.getString(
			actionRequest, "emailAddress");

		PortletURL portletURL = PortletURLFactoryUtil.create(
			actionRequest, PortletKeys.FAST_LOGIN, themeDisplay.getPlid(),
			PortletRequest.RENDER_PHASE);

		portletURL.setParameter("struts_action", "/login/login_redirect");
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

				writeJSON(actionRequest, actionResponse, jsonObject);
			}
		}
		catch (Exception e) {
			if (cmd.equals(Constants.UPDATE)) {
				jsonObject.putException(e);

				writeJSON(actionRequest, actionResponse, jsonObject);
			}
			else if (e instanceof DuplicateUserEmailAddressException) {
				User user = UserLocalServiceUtil.getUserByEmailAddress(
					themeDisplay.getCompanyId(), emailAddress);

				if (user.getStatus() != WorkflowConstants.STATUS_INCOMPLETE) {
					SessionErrors.add(actionRequest, e.getClass());
				}
				else {
					sendRedirect(
						actionRequest, actionResponse, portletURL.toString());
				}
			}
			else if (e instanceof CaptchaTextException ||
					 e instanceof CompanyMaxUsersException ||
					 e instanceof ContactFirstNameException ||
					 e instanceof ContactFullNameException ||
					 e instanceof ContactLastNameException ||
					 e instanceof EmailAddressException ||
					 e instanceof GroupFriendlyURLException ||
					 e instanceof ReservedUserEmailAddressException ||
					 e instanceof UserEmailAddressException) {

				SessionErrors.add(actionRequest, e.getClass(), e);
			}
			else {
				_log.error("Unable to create anonymous account", e);

				PortalUtil.sendError(e, actionRequest, actionResponse);
			}
		}
	}

	@Override
	public ActionForward render(
			ActionMapping mapping, ActionForm form, PortletConfig portletConfig,
			RenderRequest renderRequest, RenderResponse renderResponse)
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)renderRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		String portletName = portletConfig.getPortletName();

		if (!portletName.equals(PortletKeys.FAST_LOGIN)) {
			return mapping.findForward("portlet.login.login");
		}

		renderResponse.setTitle(themeDisplay.translate("anonymous-account"));

		return mapping.findForward("portlet.login.create_anonymous_account");
	}

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
		int prefixId = 0;
		int suffixId = 0;
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

		User user = UserServiceUtil.addUser(
			themeDisplay.getCompanyId(), autoPassword, password1, password2,
			autoScreenName, screenName, emailAddress, facebookId, openId,
			themeDisplay.getLocale(), firstName, null, lastName, prefixId,
			suffixId, male, birthdayMonth, birthdayDay, birthdayYear, jobTitle,
			groupIds, organizationIds, roleIds, userGroupIds, sendEmail,
			serviceContext);

		UserLocalServiceUtil.updateStatus(
			user.getUserId(), WorkflowConstants.STATUS_INCOMPLETE);

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

		if (!portletId.equals(PortletKeys.FAST_LOGIN)) {
			super.addSuccessMessage(actionRequest, actionResponse);
		}
	}

	@Override
	protected boolean isCheckMethodOnProcessAction() {
		return _CHECK_METHOD_ON_PROCESS_ACTION;
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
		int prefixId = 0;
		int suffixId = 0;
		boolean male = true;
		int birthdayMonth = 0;
		int birthdayDay = 1;
		int birthdayYear = 1970;
		String jobTitle = null;
		boolean updateUserInformation = false;
		boolean sendEmail = true;

		User user = UserServiceUtil.updateIncompleteUser(
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

	private static final boolean _CHECK_METHOD_ON_PROCESS_ACTION = false;

	private static Log _log = LogFactoryUtil.getLog(
		CreateAnonymousAccountAction.class);

}