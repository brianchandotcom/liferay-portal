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

import com.liferay.portal.kernel.facebook.FacebookConnectUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.portlet.LiferayWindowState;
import com.liferay.portal.kernel.util.CalendarFactoryUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.model.Contact;
import com.liferay.portal.model.User;
import com.liferay.portal.model.UserGroupRole;
import com.liferay.portal.security.auth.PrincipalException;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.UserLocalServiceUtil;
import com.liferay.portal.struts.ActionConstants;
import com.liferay.portal.struts.PortletAction;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortletKeys;
import com.liferay.portal.util.WebKeys;
import com.liferay.portlet.PortletURLFactoryUtil;

import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import javax.portlet.PortletConfig;
import javax.portlet.PortletMode;
import javax.portlet.PortletRequest;
import javax.portlet.PortletURL;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * @author Wilson Man
 * @author Sergio González
 * @author Mika Koivisto
 */
public class FacebookConnectAction extends PortletAction {

	@Override
	public ActionForward render(
			ActionMapping mapping, ActionForm form, PortletConfig portletConfig,
			RenderRequest renderRequest, RenderResponse renderResponse)
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)renderRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		if (!FacebookConnectUtil.isEnabled(themeDisplay.getCompanyId())) {
			return mapping.findForward("portlet.login.login");
		}

		return mapping.findForward("portlet.login.facebook_login");
	}

	@Override
	public ActionForward strutsExecute(
			ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response)
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)request.getAttribute(
			WebKeys.THEME_DISPLAY);

		if (!FacebookConnectUtil.isEnabled(themeDisplay.getCompanyId())) {
			throw new PrincipalException();
		}

		HttpSession session = request.getSession();

		String redirect = ParamUtil.getString(request, "redirect");

		String code = ParamUtil.getString(request, "code");

		String token = FacebookConnectUtil.getAccessToken(
			themeDisplay.getCompanyId(), redirect, code);

		if (Validator.isNotNull(token)) {
			session.setAttribute(WebKeys.FACEBOOK_ACCESS_TOKEN, token);

			User user = setFacebookCredentials(
				session, themeDisplay.getCompanyId(), token);

			if ((user != null) &&
				(user.getStatus() == WorkflowConstants.STATUS_INCOMPLETE)) {

				redirectUpdateAccount(request, response, user);

				return null;
			}
		}
		else {
			return mapping.findForward(ActionConstants.COMMON_REFERER_JSP);
		}

		response.sendRedirect(redirect);

		return null;
	}

	protected User addUser(
			HttpSession session, long companyId, JSONObject jsonObject)
		throws Exception {

		long creatorUserId = 0;
		boolean autoPassword = true;
		String password1 = StringPool.BLANK;
		String password2 = StringPool.BLANK;
		boolean autoScreenName = true;
		String screenName = StringPool.BLANK;
		String emailAddress = jsonObject.getString("email");
		long facebookId = jsonObject.getLong("id");
		String openId = StringPool.BLANK;
		Locale locale = LocaleUtil.getDefault();
		String firstName = jsonObject.getString("first_name");
		String middleName = StringPool.BLANK;
		String lastName = jsonObject.getString("last_name");
		int prefixId = 0;
		int suffixId = 0;
		boolean male = Validator.equals(jsonObject.getString("gender"), "male");
		int birthdayMonth = Calendar.JANUARY;
		int birthdayDay = 1;
		int birthdayYear = 1970;
		String jobTitle = StringPool.BLANK;
		long[] groupIds = null;
		long[] organizationIds = null;
		long[] roleIds = null;
		long[] userGroupIds = null;
		boolean sendEmail = true;

		ServiceContext serviceContext = new ServiceContext();

		User user = UserLocalServiceUtil.addUser(
			creatorUserId, companyId, autoPassword, password1, password2,
			autoScreenName, screenName, emailAddress, facebookId, openId,
			locale, firstName, middleName, lastName, prefixId, suffixId, male,
			birthdayMonth, birthdayDay, birthdayYear, jobTitle, groupIds,
			organizationIds, roleIds, userGroupIds, sendEmail, serviceContext);

		user = UserLocalServiceUtil.updateLastLogin(
			user.getUserId(), user.getLoginIP());

		user = UserLocalServiceUtil.updatePasswordReset(
			user.getUserId(), false);

		user = UserLocalServiceUtil.updateEmailAddressVerified(
			user.getUserId(), true);

		session.setAttribute(WebKeys.FACEBOOK_USER_EMAIL_ADDRESS, emailAddress);

		return user;
	}

	protected void redirectUpdateAccount(
			HttpServletRequest request, HttpServletResponse response, User user)
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)request.getAttribute(
			WebKeys.THEME_DISPLAY);

		PortletURL portletURL = PortletURLFactoryUtil.create(
			request, PortletKeys.LOGIN, themeDisplay.getPlid(),
			PortletRequest.RENDER_PHASE);

		portletURL.setParameter("saveLastPath", Boolean.FALSE.toString());
		portletURL.setParameter("struts_action", "/login/update_account");

		PortletURL redirectURL = PortletURLFactoryUtil.create(
			request, PortletKeys.FAST_LOGIN, themeDisplay.getPlid(),
			PortletRequest.RENDER_PHASE);

		redirectURL.setParameter("struts_action", "/login/login_redirect");
		redirectURL.setParameter("emailAddress", user.getEmailAddress());
		redirectURL.setParameter("anonymousUser", Boolean.FALSE.toString());
		redirectURL.setPortletMode(PortletMode.VIEW);
		redirectURL.setWindowState(LiferayWindowState.POP_UP);

		portletURL.setParameter("redirect", redirectURL.toString());
		portletURL.setParameter("userId", String.valueOf(user.getUserId()));
		portletURL.setParameter("emailAddress", user.getEmailAddress());
		portletURL.setParameter("firstName", user.getFirstName());
		portletURL.setParameter("lastName", user.getLastName());
		portletURL.setPortletMode(PortletMode.VIEW);
		portletURL.setWindowState(LiferayWindowState.POP_UP);

		response.sendRedirect(portletURL.toString());
	}

	protected User setFacebookCredentials(
			HttpSession session, long companyId, String token)
		throws Exception {

		JSONObject jsonObject = FacebookConnectUtil.getGraphResources(
			companyId, "/me", token,
			"id,email,first_name,last_name,gender");

		if ((jsonObject == null) ||
			(jsonObject.getJSONObject("error") != null)) {

			return null;
		}

		if (FacebookConnectUtil.isVerifiedAccountRequired(companyId) &&
			!jsonObject.getBoolean("verified")) {

			return null;
		}

		User user = null;

		long facebookId = jsonObject.getLong("id");

		if (facebookId > 0) {
			user = UserLocalServiceUtil.fetchUserByFacebookId(
				companyId, facebookId);

			if ((user != null) &&
				(user.getStatus() != WorkflowConstants.STATUS_INCOMPLETE)) {

				session.setAttribute(
					WebKeys.FACEBOOK_USER_ID, String.valueOf(facebookId));
			}
		}

		String emailAddress = jsonObject.getString("email");

		if ((user == null) && Validator.isNotNull(emailAddress)) {
			user = UserLocalServiceUtil.fetchUserByEmailAddress(
				companyId, emailAddress);

			if ((user != null) &&
				(user.getStatus() != WorkflowConstants.STATUS_INCOMPLETE)) {

				session.setAttribute(
					WebKeys.FACEBOOK_USER_EMAIL_ADDRESS, emailAddress);
			}
		}

		if (user != null) {
			if (user.getStatus() == WorkflowConstants.STATUS_INCOMPLETE) {
				session.setAttribute(
					WebKeys.FACEBOOK_INCOMPLETE_USER_ID, facebookId);

				user.setEmailAddress(jsonObject.getString("email"));
				user.setFirstName(jsonObject.getString("first_name"));
				user.setLastName(jsonObject.getString("last_name"));

				return user;
			}

			user = updateUser(user, jsonObject);
		}
		else {
			user = addUser(session, companyId, jsonObject);
		}

		return user;
	}

	protected User updateUser(User user, JSONObject jsonObject)
		throws Exception {

		long facebookId = jsonObject.getLong("id");
		String emailAddress = jsonObject.getString("email");
		String firstName = jsonObject.getString("first_name");
		String lastName = jsonObject.getString("last_name");
		boolean male = Validator.equals(jsonObject.getString("gender"), "male");

		if ((facebookId == user.getFacebookId()) &&
			emailAddress.equals(user.getEmailAddress()) &&
			firstName.equals(user.getFirstName()) &&
			lastName.equals(user.getLastName()) && (male == user.isMale())) {

			return user;
		}

		Contact contact = user.getContact();

		Calendar birthdayCal = CalendarFactoryUtil.getCalendar();

		birthdayCal.setTime(contact.getBirthday());

		int birthdayMonth = birthdayCal.get(Calendar.MONTH);
		int birthdayDay = birthdayCal.get(Calendar.DAY_OF_MONTH);
		int birthdayYear = birthdayCal.get(Calendar.YEAR);

		long[] groupIds = null;
		long[] organizationIds = null;
		long[] roleIds = null;
		List<UserGroupRole> userGroupRoles = null;
		long[] userGroupIds = null;

		ServiceContext serviceContext = new ServiceContext();

		if (!emailAddress.equalsIgnoreCase(user.getEmailAddress())) {
			UserLocalServiceUtil.updateEmailAddress(
				user.getUserId(), StringPool.BLANK, emailAddress, emailAddress);
		}

		UserLocalServiceUtil.updateEmailAddressVerified(user.getUserId(), true);

		return UserLocalServiceUtil.updateUser(
			user.getUserId(), StringPool.BLANK, StringPool.BLANK,
			StringPool.BLANK, false, user.getReminderQueryQuestion(),
			user.getReminderQueryAnswer(), user.getScreenName(), emailAddress,
			facebookId, user.getOpenId(), user.getLanguageId(),
			user.getTimeZoneId(), user.getGreeting(), user.getComments(),
			firstName, user.getMiddleName(), lastName, contact.getPrefixId(),
			contact.getSuffixId(), male, birthdayMonth, birthdayDay,
			birthdayYear, contact.getSmsSn(), contact.getAimSn(),
			contact.getFacebookSn(), contact.getIcqSn(), contact.getJabberSn(),
			contact.getMsnSn(), contact.getMySpaceSn(), contact.getSkypeSn(),
			contact.getTwitterSn(), contact.getYmSn(), contact.getJobTitle(),
			groupIds, organizationIds, roleIds, userGroupRoles, userGroupIds,
			serviceContext);
	}

}