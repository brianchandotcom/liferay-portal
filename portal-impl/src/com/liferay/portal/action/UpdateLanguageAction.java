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

import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.NoSuchLayoutException;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Contact;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.model.VirtualLayoutConstants;
import com.liferay.portal.kernel.portlet.FriendlyURLResolverRegistryUtil;
import com.liferay.portal.kernel.portlet.LayoutFriendlyURLSeparatorComposite;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.struts.Action;
import com.liferay.portal.struts.model.ActionForward;
import com.liferay.portal.struts.model.ActionMapping;
import com.liferay.portal.util.PropsValues;
import com.liferay.portlet.admin.util.AdminUtil;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * @author Brian Wing Shun Chan
 */
public class UpdateLanguageAction implements Action {

	@Override
	public ActionForward execute(
			ActionMapping actionMapping, HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse)
		throws Exception {

		ThemeDisplay themeDisplay =
			(ThemeDisplay)httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		String languageId = ParamUtil.getString(
			httpServletRequest, "languageId");

		Locale locale = LocaleUtil.fromLanguageId(languageId);

		if (LanguageUtil.isAvailableLocale(
				themeDisplay.getSiteGroupId(), locale)) {

			boolean persistState = ParamUtil.getBoolean(
				httpServletRequest, "persistState", true);

			if (themeDisplay.isSignedIn() && persistState) {
				User user = themeDisplay.getUser();

				Contact contact = user.getContact();

				AdminUtil.updateUser(
					httpServletRequest, user.getUserId(), user.getScreenName(),
					user.getEmailAddress(), user.getFacebookId(),
					user.getOpenId(), languageId, user.getTimeZoneId(),
					user.getGreeting(), user.getComments(), contact.getSmsSn(),
					contact.getFacebookSn(), contact.getJabberSn(),
					contact.getSkypeSn(), contact.getTwitterSn());
			}

			HttpSession httpSession = httpServletRequest.getSession();

			httpSession.setAttribute(WebKeys.LOCALE, locale);

			LanguageUtil.updateCookie(
				httpServletRequest, httpServletResponse, locale);
		}

		// Send redirect

		try {
			httpServletResponse.sendRedirect(
				getRedirect(httpServletRequest, themeDisplay, locale));
		}
		catch (IllegalArgumentException | NoSuchLayoutException exception) {
			httpServletResponse.sendError(
				HttpServletResponse.SC_BAD_REQUEST,
				httpServletRequest.getRequestURI());
		}

		return null;
	}

	public String getRedirect(
			HttpServletRequest httpServletRequest, ThemeDisplay themeDisplay,
			Locale locale)
		throws PortalException {

		String redirect = PortalUtil.escapeRedirect(
			ParamUtil.getString(httpServletRequest, "redirect"));

		if (Validator.isNull(redirect)) {
			throw new IllegalArgumentException();
		}

		String layoutURL = redirect;

		String friendlyURLSeparatorPart = StringPool.BLANK;
		String queryString = StringPool.BLANK;

		int questionIndex = redirect.indexOf(StringPool.QUESTION);

		if (questionIndex != -1) {
			queryString = redirect.substring(questionIndex);
			layoutURL = redirect.substring(0, questionIndex);
		}

		String friendlyURLSeparator = StringPool.BLANK;
		int friendlyURLSeparatorIndex = -1;

		for (String urlSeparator :
				FriendlyURLResolverRegistryUtil.getURLSeparators()) {

			if (VirtualLayoutConstants.CANONICAL_URL_SEPARATOR.equals(
					urlSeparator)) {

				continue;
			}

			friendlyURLSeparatorIndex = layoutURL.indexOf(urlSeparator);

			if (friendlyURLSeparatorIndex != -1) {
				friendlyURLSeparator = urlSeparator;

				break;
			}
		}

		Layout layout = themeDisplay.getLayout();

		if (friendlyURLSeparatorIndex != -1) {
			friendlyURLSeparatorPart = layoutURL.substring(
				friendlyURLSeparatorIndex);

			try {
				LayoutFriendlyURLSeparatorComposite
					layoutFriendlyURLSeparatorComposite =
						PortalUtil.getLayoutFriendlyURLSeparatorComposite(
							layout.getGroupId(), layout.isPrivateLayout(),
							friendlyURLSeparatorPart,
							httpServletRequest.getParameterMap(),
							HashMapBuilder.<String, Object>put(
								"request", httpServletRequest
							).build());

				friendlyURLSeparatorPart =
					layoutFriendlyURLSeparatorComposite.getFriendlyURL();
			}
			catch (NoSuchLayoutException noSuchLayoutException) {
				if (!Portal.FRIENDLY_URL_SEPARATOR.equals(
						friendlyURLSeparator)) {

					if (_log.isDebugEnabled()) {
						_log.debug(noSuchLayoutException);
					}

					throw noSuchLayoutException;
				}
			}

			layoutURL = layoutURL.substring(0, friendlyURLSeparatorIndex);
		}

		if (themeDisplay.isI18n()) {
			String i18nPath = themeDisplay.getI18nPath();

			Locale currentLocale = themeDisplay.getLocale();

			String currentLocalePath =
				StringPool.SLASH + currentLocale.toLanguageTag();

			if (layoutURL.startsWith(currentLocalePath)) {
				layoutURL = layoutURL.substring(currentLocalePath.length());
			}
			else if (layoutURL.startsWith(i18nPath)) {
				layoutURL = layoutURL.substring(i18nPath.length());
			}
		}

		if (!Validator.isBlank(themeDisplay.getPathMain()) &&
			layoutURL.startsWith(themeDisplay.getPathMain())) {

			redirect = layoutURL;
		}
		else if (isFriendlyURLResolver(layoutURL) ||
				 layout.isTypeControlPanel()) {

			redirect = layoutURL + friendlyURLSeparatorPart;
		}
		else if (layoutURL.equals(StringPool.SLASH) ||
				 isGroupFriendlyURL(
					 layout.getGroup(), layout, layoutURL, locale)) {

			if (PropsValues.LOCALE_PREPEND_FRIENDLY_URL_STYLE == 0) {
				redirect = layoutURL;
			}
			else {
				redirect = PortalUtil.getGroupFriendlyURL(
					layout.getLayoutSet(), themeDisplay, locale);
			}

			if (!redirect.endsWith(StringPool.SLASH) &&
				!friendlyURLSeparatorPart.startsWith(StringPool.SLASH)) {

				redirect += StringPool.SLASH;
			}

			if (Validator.isNotNull(friendlyURLSeparatorPart)) {
				redirect += friendlyURLSeparatorPart;
			}
		}
		else {
			if (PropsValues.LOCALE_PREPEND_FRIENDLY_URL_STYLE == 0) {
				redirect = PortalUtil.getLayoutURL(
					layout, themeDisplay, locale);
			}
			else {
				redirect = PortalUtil.getLayoutFriendlyURL(
					layout, themeDisplay, locale);
			}

			if (Validator.isNotNull(friendlyURLSeparatorPart)) {
				redirect += friendlyURLSeparatorPart;
			}
		}

		if (Validator.isNotNull(queryString)) {
			redirect = redirect + queryString;
		}

		return redirect;
	}

	protected boolean isFriendlyURLResolver(String layoutURL) {
		String[] urlSeparators =
			FriendlyURLResolverRegistryUtil.getURLSeparators();

		for (String urlSeparator : urlSeparators) {
			if (layoutURL.contains(urlSeparator)) {
				return true;
			}
		}

		return false;
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

		if ((layoutURLLocale != null) ||
			PortalUtil.isGroupFriendlyURL(
				layoutURL, group.getFriendlyURL(),
				layout.getFriendlyURL(locale))) {

			return true;
		}

		return false;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		UpdateLanguageAction.class);

}