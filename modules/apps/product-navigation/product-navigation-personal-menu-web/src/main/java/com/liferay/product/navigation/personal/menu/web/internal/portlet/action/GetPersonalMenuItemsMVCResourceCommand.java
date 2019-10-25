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

package com.liferay.product.navigation.personal.menu.web.internal.portlet.action;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCResourceCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCResourceCommand;
import com.liferay.portal.kernel.servlet.ServletResponseUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.Html;
import com.liferay.portal.kernel.util.Http;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.product.navigation.personal.menu.PersonalMenuEntry;
import com.liferay.product.navigation.personal.menu.constants.PersonalMenuPortletKeys;
import com.liferay.product.navigation.personal.menu.web.internal.PersonalMenuEntryRegistry;

import java.util.List;
import java.util.Locale;
import java.util.Objects;

import javax.portlet.PortletRequest;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;

import javax.servlet.http.HttpServletResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Pei-Jung Lan
 */
@Component(
	immediate = true,
	property = {
		"javax.portlet.name=" + PersonalMenuPortletKeys.PERSONAL_MENU,
		"mvc.command.name=/get_personal_menu_items"
	},
	service = MVCResourceCommand.class
)
public class GetPersonalMenuItemsMVCResourceCommand
	extends BaseMVCResourceCommand {

	@Override
	protected void doServeResource(
		ResourceRequest resourceRequest, ResourceResponse resourceResponse) {

		try {
			HttpServletResponse httpServletResponse =
				_portal.getHttpServletResponse(resourceResponse);

			httpServletResponse.setContentType(ContentTypes.APPLICATION_JSON);

			JSONArray jsonArray = _getPersonalMenuItemsJSONArray(
				resourceRequest);

			ServletResponseUtil.write(
				httpServletResponse, jsonArray.toString());
		}
		catch (Exception e) {
			_log.error(e, e);
		}
	}

	private JSONArray _getImpersonationItemsJSONArray(
			PortletRequest portletRequest, ThemeDisplay themeDisplay)
		throws PortalException {

		User realUser = themeDisplay.getRealUser();
		User user = themeDisplay.getUser();

		String currentURL = _http.removeParameter(
			ParamUtil.getString(portletRequest, "currentURL"), "doAsUserId");

		String userProfileURL = user.getDisplayURL(themeDisplay, false);
		String userDashboardURL = user.getDisplayURL(themeDisplay, true);

		userProfileURL = _http.getPath(userProfileURL);
		userDashboardURL = _http.getPath(userDashboardURL);

		if (currentURL.equals(userProfileURL)) {
			String realUserProfileURL = realUser.getDisplayURL(
				themeDisplay, false);

			currentURL = _http.getPath(realUserProfileURL);
		}
		else if (currentURL.equals(userDashboardURL)) {
			String realUserDashboardURL = realUser.getDisplayURL(
				themeDisplay, true);

			currentURL = _http.getPath(realUserDashboardURL);
		}

		JSONObject jsonObject1 = JSONUtil.put(
			"href", currentURL
		).put(
			"label",
			LanguageUtil.get(themeDisplay.getLocale(), "be-yourself-again")
		).put(
			"symbolRight", "change"
		);

		JSONArray jsonArray = JSONUtil.put(jsonObject1);

		Locale realUserLocale = realUser.getLocale();
		Locale userLocale = user.getLocale();

		if (!realUserLocale.equals(userLocale)) {
			String changeLanguageLabel = null;
			String doAsUserLanguageId = null;

			Locale locale = themeDisplay.getLocale();

			if (Objects.equals(
					locale.getLanguage(), realUserLocale.getLanguage()) &&
				Objects.equals(
					locale.getCountry(), realUserLocale.getCountry())) {

				changeLanguageLabel = LanguageUtil.format(
					realUserLocale, "use-x's-preferred-language-(x)",
					new String[] {
						_html.escape(user.getFullName()),
						userLocale.getDisplayLanguage(realUserLocale)
					},
					false);

				doAsUserLanguageId =
					userLocale.getLanguage() + "_" + userLocale.getCountry();
			}
			else {
				changeLanguageLabel = LanguageUtil.format(
					realUserLocale, "use-your-preferred-language-(x)",
					realUserLocale.getDisplayLanguage(realUserLocale), false);

				doAsUserLanguageId = StringUtil.add(
					realUserLocale.getLanguage(), realUserLocale.getCountry(),
					StringPool.UNDERLINE);
			}

			JSONObject jsonObject2 = JSONUtil.put(
				"href",
				_http.setParameter(
					ParamUtil.getString(portletRequest, "currentURL"),
					"doAsUserLanguageId", doAsUserLanguageId)
			).put(
				"label", changeLanguageLabel
			).put(
				"symbolRight", "globe"
			);

			jsonArray.put(jsonObject2);
		}

		return jsonArray;
	}

	private JSONArray _getPersonalMenuEntriesJSONArray(
			PortletRequest portletRequest,
			List<PersonalMenuEntry> personalMenuEntries)
		throws PortalException {

		JSONArray jsonArray = JSONFactoryUtil.createJSONArray();

		ThemeDisplay themeDisplay = (ThemeDisplay)portletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		for (PersonalMenuEntry personalMenuEntry : personalMenuEntries) {
			if (!personalMenuEntry.isShow(
					portletRequest, themeDisplay.getPermissionChecker())) {

				continue;
			}

			String portletId = ParamUtil.getString(portletRequest, "portletId");

			JSONObject jsonObject = JSONUtil.put(
				"active",
				personalMenuEntry.isActive(portletRequest, portletId));

			try {
				jsonObject.put(
					"href",
					personalMenuEntry.getPortletURL(
						_portal.getHttpServletRequest(portletRequest)));
			}
			catch (PortalException pe) {
				_log.error(pe, pe);
			}

			jsonObject.put(
				"label", personalMenuEntry.getLabel(themeDisplay.getLocale())
			).put(
				"symbolRight", personalMenuEntry.getIcon(portletRequest)
			);

			jsonArray.put(jsonObject);
		}

		return jsonArray;
	}

	private JSONArray _getPersonalMenuItemsJSONArray(
			PortletRequest portletRequest)
		throws PortalException {

		JSONArray jsonArray = JSONFactoryUtil.createJSONArray();

		List<List<PersonalMenuEntry>> groupedPersonalMenuEntries =
			_personalMenuEntryRegistry.getGroupedPersonalMenuEntries();

		ThemeDisplay themeDisplay = (ThemeDisplay)portletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		if (themeDisplay.isImpersonated()) {
			JSONObject impersonationJSONObject = JSONUtil.put(
				"items",
				_getImpersonationItemsJSONArray(portletRequest, themeDisplay));

			User user = themeDisplay.getUser();

			impersonationJSONObject.put(
				"label",
				StringUtil.appendParentheticalSuffix(
					user.getFullName(),
					LanguageUtil.get(themeDisplay.getLocale(), "impersonated"))
			).put(
				"type", "group"
			);

			jsonArray.put(impersonationJSONObject);
		}

		JSONObject dividerJSONObject = JSONUtil.put("type", "divider");

		for (List<PersonalMenuEntry> groupedPersonalMenuEntry :
				groupedPersonalMenuEntries) {

			JSONArray personalMenuEntriesJSONArray =
				_getPersonalMenuEntriesJSONArray(
					portletRequest, groupedPersonalMenuEntry);

			if (personalMenuEntriesJSONArray.length() == 0) {
				continue;
			}

			if (jsonArray.length() > 0) {
				jsonArray.put(dividerJSONObject);
			}

			JSONObject jsonObject = JSONUtil.put(
				"items", personalMenuEntriesJSONArray
			).put(
				"type", "group"
			);

			jsonArray.put(jsonObject);
		}

		if ((jsonArray.length() > 0) && !themeDisplay.isImpersonated()) {
			User user = themeDisplay.getUser();

			JSONObject jsonObject = (JSONObject)jsonArray.get(0);

			jsonObject.put("label", user.getFullName());
		}

		return jsonArray;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		GetPersonalMenuItemsMVCResourceCommand.class);

	@Reference
	private Html _html;

	@Reference
	private Http _http;

	@Reference
	private PersonalMenuEntryRegistry _personalMenuEntryRegistry;

	@Reference
	private Portal _portal;

}