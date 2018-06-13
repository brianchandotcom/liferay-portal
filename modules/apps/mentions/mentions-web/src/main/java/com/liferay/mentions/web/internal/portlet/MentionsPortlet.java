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

package com.liferay.mentions.web.internal.portlet;

import com.liferay.mentions.constants.MentionsPortletKeys;
import com.liferay.mentions.util.MentionsUserFinder;
import com.liferay.mentions.util.MentionsUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.language.LanguageConstants;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;
import com.liferay.portal.kernel.servlet.ServletResponseUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.social.kernel.util.SocialInteractionsConfiguration;
import com.liferay.social.kernel.util.SocialInteractionsConfigurationUtil;
import com.liferay.taglib.util.LexiconUtil;
import com.liferay.users.admin.kernel.file.uploads.UserFileUploadsSettings;

import java.util.List;

import javax.portlet.Portlet;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Iván Zaera
 * @author Sergio González
 */
@Component(
	immediate = true,
	property = {
		"com.liferay.portlet.add-default-resource=true",
		"com.liferay.portlet.display-category=category.hidden",
		"com.liferay.portlet.icon=/icons/mentions.png",
		"javax.portlet.display-name=Mentions",
		"javax.portlet.expiration-cache=0",
		"javax.portlet.name=" + MentionsPortletKeys.MENTIONS,
		"javax.portlet.resource-bundle=content.Language",
		"javax.portlet.security-role-ref=administrator",
		"javax.portlet.supports.mime-type=text/html"
	},
	service = Portlet.class
)
public class MentionsPortlet extends MVCPortlet {

	@Override
	public void serveResource(
		ResourceRequest resourceRequest, ResourceResponse resourceResponse) {

		try {
			ThemeDisplay themeDisplay =
				(ThemeDisplay)resourceRequest.getAttribute(
					WebKeys.THEME_DISPLAY);

			if (!MentionsUtil.isMentionsEnabled(
					themeDisplay.getSiteGroupId())) {

				return;
			}

			HttpServletRequest request = _portal.getHttpServletRequest(
				resourceRequest);

			JSONArray jsonArray = getJSONArray(request);

			HttpServletResponse response = _portal.getHttpServletResponse(
				resourceResponse);

			response.setContentType(ContentTypes.APPLICATION_JSON);

			ServletResponseUtil.write(response, jsonArray.toString());
		}
		catch (Exception e) {
			_log.error(e, e);
		}
	}

	protected JSONArray getJSONArray(HttpServletRequest request)
		throws PortalException {

		JSONArray jsonArray = JSONFactoryUtil.createJSONArray();

		ThemeDisplay themeDisplay = (ThemeDisplay)request.getAttribute(
			WebKeys.THEME_DISPLAY);

		SocialInteractionsConfiguration socialInteractionsConfiguration =
			SocialInteractionsConfigurationUtil.
				getSocialInteractionsConfiguration(
					themeDisplay.getCompanyId(), MentionsPortletKeys.MENTIONS);

		String query = ParamUtil.getString(request, "query");

		List<User> users = _mentionsUserFinder.getUsers(
			themeDisplay.getCompanyId(), themeDisplay.getUserId(), query,
			socialInteractionsConfiguration);

		for (User user : users) {
			if (user.isDefaultUser() ||
				(themeDisplay.getUserId() == user.getUserId())) {

				continue;
			}

			JSONObject jsonObject = JSONFactoryUtil.createJSONObject();

			jsonObject.put("fullName", user.getFullName());

			String mention = "@" + user.getScreenName();

			String profileURL = user.getDisplayURL(themeDisplay);

			if (Validator.isNotNull(profileURL)) {
				mention = StringBundler.concat(
					"<a href=\"", profileURL, "\">@", user.getScreenName(),
					"</a>");
			}

			jsonObject.put("mention", mention);

			jsonObject.put(
				"portraitHTML", _getUserPortraitHTML(user, themeDisplay));
			jsonObject.put("screenName", user.getScreenName());

			jsonArray.put(jsonObject);
		}

		return jsonArray;
	}

	@Reference(unbind = "-")
	protected void setMentionsUserFinder(
		MentionsUserFinder mentionsUserFinder) {

		_mentionsUserFinder = mentionsUserFinder;
	}

	private String _getUserPortraitHTML(User user, ThemeDisplay themeDisplay)
		throws PortalException {

		boolean imageDefaultUseInitials =
			_userFileUploadsSettings.isImageDefaultUseInitials();

		long userPortraitId = 0;

		if (user != null) {
			userPortraitId = user.getPortraitId();

			if (LanguageConstants.VALUE_IMAGE.equals(
					LanguageUtil.get(
						user.getLocale(),
						LanguageConstants.KEY_USER_DEFAULT_PORTRAIT,
						LanguageConstants.VALUE_INITIALS))) {

				imageDefaultUseInitials = false;
			}
		}

		StringBundler sb = new StringBundler(5);

		sb.append("<div class=\"");

		if (imageDefaultUseInitials && (userPortraitId == 0)) {
			sb.append(LexiconUtil.getUserColorCssClass(user));
			sb.append(" user-icon user-icon-default\"><span>");
			sb.append(user.getInitials());
			sb.append("</span></div>");
		}
		else {
			sb.append(" aspect-ratio-bg-cover user-icon\" ");
			sb.append("style=\"background-image:url(");
			sb.append(HtmlUtil.escape(user.getPortraitURL(themeDisplay)));
			sb.append(")\"></div>");
		}

		return sb.toString();
	}

	private static final Log _log = LogFactoryUtil.getLog(
		MentionsPortlet.class);

	private MentionsUserFinder _mentionsUserFinder;

	@Reference
	private Portal _portal;

	@Reference
	private UserFileUploadsSettings _userFileUploadsSettings;

}