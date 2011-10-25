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
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.util.CharPool;
import com.liferay.portal.kernel.util.Http;
import com.liferay.portal.kernel.util.HttpUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.User;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.UserLocalServiceUtil;
import com.liferay.portal.struts.ActionConstants;
import com.liferay.portal.struts.PortletAction;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.WebKeys;

import java.util.Calendar;
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

		String token = getAccessToken(
			themeDisplay.getCompanyId(), redirect, code);

		if (Validator.isNotNull(token)) {
			session.setAttribute(WebKeys.FACEBOOK_ACCESS_TOKEN, token);

			setFacebookCredentials(session, themeDisplay.getCompanyId(), token);
		}
		else {
			return mapping.findForward(ActionConstants.COMMON_REFERER);
		}

		if ((session.getAttribute(WebKeys.FACEBOOK_ACCESS_TOKEN) == null) ||
			(session.getAttribute(WebKeys.FACEBOOK_USER_EMAIL_ADDRESS) ==
				null)) {

			addUser(request, themeDisplay);
		}

		response.sendRedirect(redirect);

		return null;
	}

	protected void addUser(
			HttpServletRequest request, ThemeDisplay themeDisplay)
		throws Exception {

		request = PortalUtil.getOriginalServletRequest(request);

		HttpSession session = request.getSession();

		String token = (String)session.getAttribute(
			WebKeys.FACEBOOK_ACCESS_TOKEN);

		String url = HttpUtil.addParameter(
			FacebookConnectUtil.getGraphURL(themeDisplay.getCompanyId()) +
				"/me",
			"access_token", token);

		url = HttpUtil.addParameter(
			url, "fields",
			"email,first_name,last_name,birthday,gender,verified");

		Http.Options options = new Http.Options();

		options.setLocation(url);

		String content = HttpUtil.URLtoString(options);

		if (Validator.isNull(content)) {
			return;
		}

		JSONObject jsonObject = JSONFactoryUtil.createJSONObject(content);

		if (!jsonObject.getBoolean("verified")) {
			return;
		}

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
			creatorUserId, themeDisplay.getCompanyId(), autoPassword, password1,
			password2, autoScreenName, screenName, emailAddress, facebookId,
			openId, locale, firstName, middleName, lastName, prefixId, suffixId,
			male, birthdayMonth, birthdayDay, birthdayYear, jobTitle, groupIds,
			organizationIds, roleIds, userGroupIds, sendEmail, serviceContext);

		UserLocalServiceUtil.updateLastLogin(
			user.getUserId(), user.getLoginIP());

		UserLocalServiceUtil.updatePasswordReset(user.getUserId(), false);

		session.setAttribute(WebKeys.FACEBOOK_USER_EMAIL_ADDRESS, emailAddress);
	}

	protected String getAccessToken(
			long companyId, String redirect, String code)
		throws Exception {

		String url = HttpUtil.addParameter(
			FacebookConnectUtil.getAccessTokenURL(companyId), "client_id",
			FacebookConnectUtil.getAppId(companyId));

		url = HttpUtil.addParameter(url, "redirect_uri",
			FacebookConnectUtil.getRedirectURL(companyId));

		String facebookConnectRedirectURL = FacebookConnectUtil.getRedirectURL(
			companyId);

		facebookConnectRedirectURL = HttpUtil.addParameter(
			facebookConnectRedirectURL, "redirect", redirect);

		url = HttpUtil.addParameter(
			url, "redirect_uri", facebookConnectRedirectURL);
		url = HttpUtil.addParameter(
			url, "client_secret", FacebookConnectUtil.getAppSecret(companyId));
		url = HttpUtil.addParameter(url, "code", code);

		Http.Options options = new Http.Options();

		options.setLocation(url);
		options.setPost(true);

		String content = HttpUtil.URLtoString(options);

		if (Validator.isNotNull(content)) {
			int x = content.indexOf("access_token=");

			if (x >= 0) {
				int y = content.indexOf(CharPool.AMPERSAND, x);

				if (y < x) {
					y = content.length();
				}

				return content.substring(x + 13, y);
			}
		}

		return null;
	}

	protected void getFacebookCredentials(
			HttpSession session, long companyId, String token)
		throws Exception {

		JSONObject jsonObject = FacebookConnectUtil.getGraphResources(
			companyId, "/me", token, "id,email,verified");

		if ((jsonObject != null) && jsonObject.getBoolean("verified")) {
			String facebookId = jsonObject.getString("id");

			if (Validator.isNotNull(facebookId)) {
				session.setAttribute(WebKeys.FACEBOOK_USER_ID, facebookId);
			}

			String emailAddress = jsonObject.getString("email");

			if (Validator.isNotNull(emailAddress)) {
				try {
					UserLocalServiceUtil.getUserByEmailAddress(
						companyId, emailAddress);

					session.setAttribute(
						WebKeys.FACEBOOK_USER_EMAIL_ADDRESS, emailAddress);
				}
				catch (NoSuchUserException nsue) {
					session.removeAttribute(
						WebKeys.FACEBOOK_USER_EMAIL_ADDRESS);
				}
			}
		}
	}

	protected void setFacebookCredentials(
			HttpSession session, long companyId, String token)
		throws Exception {

		String url = HttpUtil.addParameter(
			FacebookConnectUtil.getGraphURL(companyId) + "/me", "access_token",
			token);

		url = HttpUtil.addParameter(url, "fields", "email,id,verified");

		Http.Options options = new Http.Options();

		options.setLocation(url);

		String content = HttpUtil.URLtoString(options);

		if (Validator.isNull(content)) {
			return;
		}

		JSONObject jsonObject = JSONFactoryUtil.createJSONObject(content);

		if (!jsonObject.getBoolean("verified")) {
			return;
		}

		String facebookId = jsonObject.getString("id");

		if (Validator.isNotNull(facebookId)) {
			session.setAttribute(WebKeys.FACEBOOK_USER_ID, facebookId);
		}

		String emailAddress = jsonObject.getString("email");

		if (Validator.isNotNull(emailAddress)) {
			try {
				UserLocalServiceUtil.getUserByEmailAddress(
					companyId, emailAddress);

				session.setAttribute(
					WebKeys.FACEBOOK_USER_EMAIL_ADDRESS, emailAddress);
			}
			catch (NoSuchUserException nsue) {
				session.removeAttribute(WebKeys.FACEBOOK_USER_EMAIL_ADDRESS);
			}
		}
	}

}