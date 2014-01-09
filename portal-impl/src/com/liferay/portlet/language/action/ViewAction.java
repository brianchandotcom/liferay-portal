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

package com.liferay.portlet.language.action;

import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HttpUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.Contact;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.Layout;
import com.liferay.portal.model.User;
import com.liferay.portal.struts.PortletAction;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.Portal;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.PropsValues;
import com.liferay.portal.util.WebKeys;
import com.liferay.portlet.admin.util.AdminUtil;

import java.util.List;
import java.util.Locale;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletConfig;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.Globals;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * @author Brian Wing Shun Chan
 */
public class ViewAction extends PortletAction {

	@Override
	public void processAction(
			ActionMapping actionMapping, ActionForm actionForm,
			PortletConfig portletConfig, ActionRequest actionRequest,
			ActionResponse actionResponse)
		throws Exception {

		HttpServletRequest request = PortalUtil.getHttpServletRequest(
			actionRequest);
		HttpServletResponse response = PortalUtil.getHttpServletResponse(
			actionResponse);
		HttpSession session = request.getSession();

		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		String languageId = ParamUtil.getString(actionRequest, "languageId");

		Locale locale = LocaleUtil.fromLanguageId(languageId);

		List<Locale> availableLocales = ListUtil.fromArray(
			LanguageUtil.getAvailableLocales(themeDisplay.getSiteGroupId()));

		if (availableLocales.contains(locale)) {
			boolean persistState = ParamUtil.getBoolean(
				request, "persistState", true);

			if (themeDisplay.isSignedIn() && (persistState)) {
				User user = themeDisplay.getUser();

				Contact contact = user.getContact();

				AdminUtil.updateUser(
					actionRequest, user.getUserId(), user.getScreenName(),
					user.getEmailAddress(), user.getFacebookId(),
					user.getOpenId(), languageId, user.getTimeZoneId(),
					user.getGreeting(), user.getComments(), contact.getSmsSn(),
					contact.getAimSn(), contact.getFacebookSn(),
					contact.getIcqSn(), contact.getJabberSn(),
					contact.getMsnSn(), contact.getMySpaceSn(),
					contact.getSkypeSn(), contact.getTwitterSn(),
					contact.getYmSn());
			}

			session.setAttribute(Globals.LOCALE_KEY, locale);

			LanguageUtil.updateCookie(request, response, locale);
		}

		// Send redirect

		String redirect = ParamUtil.getString(actionRequest, "redirect");

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

		Layout layout = themeDisplay.getLayout();

		Group group = layout.getGroup();

		if (Validator.isNull(layoutURL) ||
			PortalUtil.isGroupFriendlyURL(
				layoutURL, group.getFriendlyURL(),
				layout.getFriendlyURL(locale))) {

			if (PropsValues.LOCALE_PREPEND_FRIENDLY_URL_STYLE == 0) {
				redirect = layoutURL;
			}
			else {
				redirect = PortalUtil.getGroupFriendlyURL(
					group, layout.isPrivateLayout(), themeDisplay, locale);
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

		int lifecycle = GetterUtil.getInteger(
			HttpUtil.getParameter(queryString, "p_p_lifecycle", false));

		if (lifecycle == 0) {
			redirect = redirect + queryString;
		}

		actionResponse.sendRedirect(redirect);
	}

	@Override
	public ActionForward render(
			ActionMapping actionMapping, ActionForm actionForm,
			PortletConfig portletConfig, RenderRequest renderRequest,
			RenderResponse renderResponse)
		throws Exception {

		return actionMapping.findForward("portlet.language.view");
	}

	@Override
	protected boolean isCheckMethodOnProcessAction() {
		return _CHECK_METHOD_ON_PROCESS_ACTION;
	}

	private static final boolean _CHECK_METHOD_ON_PROCESS_ACTION = false;

}