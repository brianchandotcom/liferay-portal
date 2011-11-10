/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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

import com.liferay.portal.NoSuchUserException;
import com.liferay.portal.kernel.facebook.FacebookConnectUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.util.CalendarFactoryUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.Contact;
import com.liferay.portal.model.User;
import com.liferay.portal.model.UserGroupRole;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.UserLocalServiceUtil;
import com.liferay.portal.struts.ActionConstants;
import com.liferay.portal.struts.PortletAction;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.WebKeys;

import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import javax.portlet.PortletConfig;
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
			return null;
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
			return null;
		}

		HttpSession session = request.getSession();

		String redirect = ParamUtil.getString(request, "redirect");

		String code = ParamUtil.getString(request, "code");

		String token = FacebookConnectUtil.getAccessToken(
			themeDisplay.getCompanyId(), redirect, code);

		if (Validator.isNotNull(token)) {
			session.setAttribute(WebKeys.FACEBOOK_ACCESS_TOKEN, token);

			setFacebookCredentials(session, themeDisplay.getCompanyId(), token);
		}
		else {
			return mapping.findForward(ActionConstants.COMMON_REFERER);
		}

		response.sendRedirect(redirect);

		return null;
	}

	protected void addUser(
			long companyId, HttpSession session, JSONObject jsonObject)
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
			creatorUserId, companyId, autoPassword, password1,
			password2, autoScreenName, screenName, emailAddress, facebookId,
			openId, locale, firstName, middleName, lastName, prefixId, suffixId,
			male, birthdayMonth, birthdayDay, birthdayYear, jobTitle, groupIds,
			organizationIds, roleIds, userGroupIds, sendEmail, serviceContext);

		UserLocalServiceUtil.updateLastLogin(
			user.getUserId(), user.getLoginIP());

		UserLocalServiceUtil.updatePasswordReset(user.getUserId(), false);

		UserLocalServiceUtil.updateEmailAddressVerified(user.getUserId(), true);

		session.setAttribute(WebKeys.FACEBOOK_USER_EMAIL_ADDRESS, emailAddress);
	}

	protected void setFacebookCredentials(
			HttpSession session, long companyId, String token)
		throws Exception {

		JSONObject jsonObject = FacebookConnectUtil.getGraphResources(
			companyId, "/me", token,
			"id,email,first_name,last_name,gender");

		if (jsonObject == null || jsonObject.getJSONObject("error") != null) {
			return;
		}

		User user = null;

		long facebookId = jsonObject.getLong("id");

		if (facebookId > 0) {
			session.setAttribute(
				WebKeys.FACEBOOK_USER_ID, jsonObject.getString("id"));

			try {
				user = UserLocalServiceUtil.getUserByFacebookId(
					companyId, facebookId);
			}
			catch (NoSuchUserException nsue) {
			}
		}

		String emailAddress = jsonObject.getString("email");

		if (user == null && Validator.isNotNull(emailAddress)) {
			session.setAttribute(
				WebKeys.FACEBOOK_USER_EMAIL_ADDRESS, emailAddress);

			try {
				user = UserLocalServiceUtil.getUserByEmailAddress(
					companyId, emailAddress);
			}
			catch (NoSuchUserException nsue) {
			}
		}

		if (user != null) {
			updateUser(companyId, session, user, jsonObject);
		}
		else {
			addUser(companyId, session, jsonObject);
		}
	}

	protected void updateUser(
			long companyId, HttpSession session, User user,
			JSONObject jsonObject)
		throws Exception {
	
		long facebookId = jsonObject.getLong("id");
		String emailAddress = jsonObject.getString("email");
		String firstName = jsonObject.getString("first_name");
		String lastName = jsonObject.getString("last_name");
		boolean male = Validator.equals(jsonObject.getString("gender"), "male");
	
		if ((user.getFacebookId() == facebookId) &&
			user.getEmailAddress().equals(emailAddress) &&
			user.getFirstName().equals(firstName) &&
			user.getLastName().equals(lastName) && (user.isMale() == male)) {
	
			return;
		}
	
		Calendar birthdayCal = CalendarFactoryUtil.getCalendar();
	
		birthdayCal.setTime(user.getContact().getBirthday());
	
		int birthdayMonth = birthdayCal.get(Calendar.MONTH);
		int birthdayDay = birthdayCal.get(Calendar.DAY_OF_MONTH);
		int birthdayYear = birthdayCal.get(Calendar.YEAR);
	
		Contact contact = user.getContact();
	
		long[] groupIds = null;
		long[] organizationIds = null;
		long[] roleIds = null;
		long[] userGroupIds = null;
	
		ServiceContext serviceContext = new ServiceContext();
	
		List<UserGroupRole> userGroupRoles = null;
	
		if (!user.getEmailAddress().equalsIgnoreCase(emailAddress)) {
			UserLocalServiceUtil.updateEmailAddress(
				user.getUserId(), StringPool.BLANK, emailAddress, emailAddress);
		}
	
		UserLocalServiceUtil.updateEmailAddressVerified(user.getUserId(), true);
	
		UserLocalServiceUtil.updateUser(
			user.getUserId(), StringPool.BLANK, StringPool.BLANK,
			StringPool.BLANK, false, user.getReminderQueryQuestion(),
			user.getReminderQueryAnswer(), user.getScreenName(),
			emailAddress, facebookId, user.getOpenId(), user.getLanguageId(),
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