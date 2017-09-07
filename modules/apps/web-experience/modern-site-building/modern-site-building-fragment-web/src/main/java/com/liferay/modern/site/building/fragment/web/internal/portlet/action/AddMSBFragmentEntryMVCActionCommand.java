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

package com.liferay.modern.site.building.fragment.web.internal.portlet.action;

import com.liferay.modern.site.building.fragment.constants.MSBFragmentPortletKeys;
import com.liferay.modern.site.building.fragment.exception.DuplicateMSBFragmentEntryException;
import com.liferay.modern.site.building.fragment.exception.MSBFragmentEntryNameException;
import com.liferay.modern.site.building.fragment.model.MSBFragmentEntry;
import com.liferay.modern.site.building.fragment.service.MSBFragmentEntryService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.portlet.JSONPortletResponseUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextFactory;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.ResourceBundleLoader;
import com.liferay.portal.kernel.util.WebKeys;

import java.util.ResourceBundle;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Jürgen Kappler
 */
@Component(
	immediate = true,
	property = {
		"javax.portlet.name=" + MSBFragmentPortletKeys.MODERN_SITE_BUILDING_FRAGMENT,
		"mvc.command.name=addMSBFragmentEntry"
	},
	service = MVCActionCommand.class
)
public class AddMSBFragmentEntryMVCActionCommand extends BaseMVCActionCommand {

	@Override
	protected void doProcessAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		long msbFragmentCollectionId = ParamUtil.getLong(
			actionRequest, "msbFragmentCollectionId");

		String name = ParamUtil.getString(actionRequest, "name");
		String css = ParamUtil.getString(actionRequest, "cssContent");
		String js = ParamUtil.getString(actionRequest, "jsContent");
		String html = ParamUtil.getString(actionRequest, "htmlContent");

		try {
			ServiceContext serviceContext = ServiceContextFactory.getInstance(
				actionRequest);

			MSBFragmentEntry msbFragmentEntry =
				_msbFragmentEntryService.addMSBFragmentEntry(
					serviceContext.getScopeGroupId(), msbFragmentCollectionId,
					name, css, html, js, serviceContext);

			JSONObject jsonObject = JSONFactoryUtil.createJSONObject();

			jsonObject.put(
				"msbFragmentEntryId", msbFragmentEntry.getMsbFragmentEntryId());

			JSONPortletResponseUtil.writeJSON(
				actionRequest, actionResponse, jsonObject);
		}
		catch (PortalException pe) {
			handlePortalException(actionRequest, actionResponse, pe);
		}
	}

	protected void handlePortalException(
			ActionRequest actionRequest, ActionResponse actionResponse,
			PortalException pe)
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		JSONObject jsonObject = JSONFactoryUtil.createJSONObject();

		String errorMessage = "an-unexpected-error-occurred";

		if (pe instanceof DuplicateMSBFragmentEntryException) {
			errorMessage = "a-fragment-entry-with-that-name-already-exists";
		}
		else if (pe instanceof MSBFragmentEntryNameException) {
			errorMessage = "this-field-is-required";
		}

		ResourceBundle resourceBundle =
			_resourceBundleLoader.loadResourceBundle(themeDisplay.getLocale());

		jsonObject.put("error", LanguageUtil.get(resourceBundle, errorMessage));

		JSONPortletResponseUtil.writeJSON(
			actionRequest, actionResponse, jsonObject);
	}

	@Reference
	private MSBFragmentEntryService _msbFragmentEntryService;

	@Reference(
		target = "(bundle.symbolic.name=com.liferay.modern.site.building.fragment.web)",
		unbind = "-"
	)
	private ResourceBundleLoader _resourceBundleLoader;

}