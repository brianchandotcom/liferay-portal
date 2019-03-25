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

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCResourceCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCResourceCommand;
import com.liferay.portal.kernel.servlet.ServletResponseUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.product.navigation.personal.menu.PersonalMenuEntry;
import com.liferay.product.navigation.personal.menu.constants.PersonalMenuPortletKeys;
import com.liferay.product.navigation.personal.menu.web.internal.util.PersonalMenuEntryRegistry;

import java.util.List;

import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;

import javax.servlet.http.HttpServletRequest;
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
			ResourceRequest resourceRequest, ResourceResponse resourceResponse)
		throws Exception {

		try {
			JSONArray jsonArray = _getPersonalMenuItemsJSONArray(
				_portal.getHttpServletRequest(resourceRequest));

			HttpServletResponse response = _portal.getHttpServletResponse(
				resourceResponse);

			response.setContentType(ContentTypes.APPLICATION_JSON);

			ServletResponseUtil.write(response, jsonArray.toString());
		}
		catch (Exception e) {
			_log.error(e, e);
		}
	}

	private JSONArray _getPersonalMenuEntriesAsJSONArray(
		HttpServletRequest request,
		List<PersonalMenuEntry> personalMenuEntries) {

		ThemeDisplay themeDisplay = (ThemeDisplay)request.getAttribute(
			WebKeys.THEME_DISPLAY);

		JSONArray jsonArray = JSONFactoryUtil.createJSONArray();

		for (PersonalMenuEntry personalMenuEntry : personalMenuEntries) {
			if (!personalMenuEntry.isShow(
					themeDisplay.getPermissionChecker())) {

				continue;
			}

			JSONObject jsonObject = JSONFactoryUtil.createJSONObject();

			try {
				jsonObject.put(
					"href", personalMenuEntry.getPortletURL(request));
			}
			catch (PortalException pe) {
				_log.error(pe, pe);
			}

			jsonObject.put(
				"label", personalMenuEntry.getLabel(themeDisplay.getLocale()));

			jsonArray.put(jsonObject);
		}

		return jsonArray;
	}

	private JSONArray _getPersonalMenuItemsJSONArray(
		HttpServletRequest request) {

		List<List<PersonalMenuEntry>> groupedPersonalMenuEntries =
			_personalMenuEntryRegistry.getGroupedPersonalMenuEntries();

		int size = groupedPersonalMenuEntries.size();

		JSONArray jsonArray = JSONFactoryUtil.createJSONArray();

		for (int i = 0; i < size; i++) {
			JSONObject jsonObject = JSONFactoryUtil.createJSONObject();

			jsonObject.put(
				"items",
				_getPersonalMenuEntriesAsJSONArray(
					request, groupedPersonalMenuEntries.get(i)));

			if (i < (size - 1)) {
				jsonObject.put("separator", true);
			}

			jsonObject.put("type", "group");

			jsonArray.put(jsonObject);
		}

		return jsonArray;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		GetPersonalMenuItemsMVCResourceCommand.class);

	@Reference
	private PersonalMenuEntryRegistry _personalMenuEntryRegistry;

	@Reference
	private Portal _portal;

}