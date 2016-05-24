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

package com.liferay.portal.action;

import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.model.Contact;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.CharPool;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.util.PropsValues;
import com.liferay.portlet.admin.util.AdminUtil;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.Globals;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * @author Brian Wing Shun Chan
 */
public class UpdateLanguageAction extends Action {

	@Override
	public ActionForward execute(
			ActionMapping actionMapping, ActionForm actionForm,
			HttpServletRequest request, HttpServletResponse response)
		throws Exception {

		HttpSession session = request.getSession();

		ThemeDisplay themeDisplay = (ThemeDisplay)request.getAttribute(
			WebKeys.THEME_DISPLAY);

		String languageId = ParamUtil.getString(request, "languageId");

		Locale locale = LocaleUtil.fromLanguageId(languageId);

		if (LanguageUtil.isAvailableLocale(
				themeDisplay.getSiteGroupId(), locale)) {

			boolean persistState = ParamUtil.getBoolean(
				request, "persistState", true);

			if (themeDisplay.isSignedIn() && (persistState)) {
				User user = themeDisplay.getUser();

				Contact contact = user.getContact();

				AdminUtil.updateUser(
					request, user.getUserId(), user.getScreenName(),
					user.getEmailAddress(), user.getFacebookId(),
					user.getOpenId(), languageId, user.getTimeZoneId(),
					user.getGreeting(), user.getComments(), contact.getSmsSn(),
					contact.getFacebookSn(), contact.getJabberSn(),
					contact.getSkypeSn(), contact.getTwitterSn());
			}

			session.setAttribute(Globals.LOCALE_KEY, locale);

			LanguageUtil.updateCookie(request, response, locale);
		}

		// Send redirect

		String redirect = ParamUtil.getString(request, "redirect");

		String layoutURL = StringPool.BLANK;
		String queryString = StringPool.BLANK;

		int pos = redirect.indexOf(Portal.FRIENDLY_URL_SEPARATOR);

		if (pos == -1) {
			pos = redirect.indexOf(StringPool.QUESTION);
		}

		if (pos != -1) {
			layoutURL = redirect.substring(0, pos);
			queryString = redirect.substring(pos);
		}
		else {
			layoutURL = redirect;
		}

		Layout layout = themeDisplay.getLayout();

		if (isGroupFriendlyURL(layout.getGroup(), layout, layoutURL, locale)) {
			if (PropsValues.LOCALE_PREPEND_FRIENDLY_URL_STYLE == 0) {
				redirect = layoutURL;
			}
			else {
				redirect = PortalUtil.getGroupFriendlyURL(
					layout.getLayoutSet(), themeDisplay, locale);
			}
		}
		else {
			if (PropsValues.LOCALE_PREPEND_FRIENDLY_URL_STYLE == 0) {
				if (themeDisplay.isI18n()) {
					redirect = layout.getFriendlyURL(locale);
				}
				else {
					redirect = PortalUtil.getLayoutURL(
						layout, themeDisplay, locale);
				}
			}
			else {
				redirect = PortalUtil.getLayoutFriendlyURL(
					layout, themeDisplay, locale);
			}
		}

		if (layout.isTypeControlPanel()) {
			redirect = redirect + queryString;
		}

		response.sendRedirect(redirect);

		return null;
	}

	protected boolean isGroupFriendlyURL(
		Group group, Layout layout, String layoutURL, Locale locale) {

		if (Validator.isNull(layoutURL)) {
			return true;
		}

		int pos = layoutURL.lastIndexOf(CharPool.SLASH);

		String layoutURLLanguageId = layoutURL.substring(pos + 1);

		Locale layoutURLLocale = LocaleUtil.fromLanguageId(
			layoutURLLanguageId, true, false);

		if (layoutURLLocale != null) {
			return true;
		}

		if (PortalUtil.isGroupFriendlyURL(
				layoutURL, group.getFriendlyURL(),
				layout.getFriendlyURL(locale))) {

			return true;
		}

		return false;
	}

}