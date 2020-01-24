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

package com.liferay.depot.web.internal.portlet.action;

import com.liferay.depot.exception.DepotEntryNameException;
import com.liferay.depot.model.DepotEntry;
import com.liferay.depot.service.DepotEntryService;
import com.liferay.depot.web.internal.constants.DepotPortletKeys;
import com.liferay.depot.web.internal.util.DepotEntryURLUtil;
import com.liferay.portal.kernel.exception.DuplicateGroupException;
import com.liferay.portal.kernel.exception.GroupKeyException;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.portlet.JSONPortletResponseUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.service.ServiceContextFactory;
import com.liferay.portal.kernel.servlet.MultiSessionMessages;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.LocalizationUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;

import java.util.Locale;
import java.util.Map;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletURL;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alejandro Tardín
 */
@Component(
	property = {
		"javax.portlet.name=" + DepotPortletKeys.DEPOT_ADMIN,
		"mvc.command.name=/depot_entry/add"
	},
	service = MVCActionCommand.class
)
public class AddDepotEntryMVCActionCommand extends BaseMVCActionCommand {

	@Override
	protected void doProcessAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		String name = ParamUtil.getString(actionRequest, "name");
		Map<Locale, String> nameMap = LocalizationUtil.getLocalizationMap(
			actionRequest, "name");

		if (Validator.isNotNull(name)) {
			nameMap.put(LocaleUtil.getDefault(), name);
		}

		Map<Locale, String> descriptionMap =
			LocalizationUtil.getLocalizationMap(actionRequest, "description");

		try {
			DepotEntry depotEntry = _depotEntryService.addDepotEntry(
				nameMap, descriptionMap,
				ServiceContextFactory.getInstance(
					DepotEntry.class.getName(), actionRequest));

			PortletURL editDepotURL =
				DepotEntryURLUtil.getEditDepotEntryPortletURL(
					depotEntry, ParamUtil.getString(actionRequest, "redirect"),
					_portal.getLiferayPortletRequest(actionRequest));

			MultiSessionMessages.add(
				actionRequest,
				DepotPortletKeys.DEPOT_ADMIN + "requestProcessed");

			JSONPortletResponseUtil.writeJSON(
				actionRequest, actionResponse,
				JSONUtil.put("redirectURL", editDepotURL.toString()));
		}
		catch (Exception exception) {
			ThemeDisplay themeDisplay =
				(ThemeDisplay)actionRequest.getAttribute(WebKeys.THEME_DISPLAY);

			JSONPortletResponseUtil.writeJSON(
				actionRequest, actionResponse,
				JSONUtil.put(
					"error", _getErrorMessage(exception, themeDisplay)));
		}
	}

	private String _getErrorMessage(
		Exception exception, ThemeDisplay themeDisplay) {

		if (exception instanceof DepotEntryNameException) {
			return LanguageUtil.get(
				themeDisplay.getRequest(), "please-enter-a-name");
		}

		if (exception instanceof DuplicateGroupException) {
			return LanguageUtil.get(
				themeDisplay.getRequest(), "please-enter-a-unique-name");
		}

		if (exception instanceof GroupKeyException) {
			return LanguageUtil.get(
				themeDisplay.getRequest(), "please-enter-a-valid-name");
		}

		return LanguageUtil.get(
			themeDisplay.getRequest(), "an-unexpected-error-occurred");
	}

	@Reference
	private DepotEntryService _depotEntryService;

	@Reference
	private Portal _portal;

}