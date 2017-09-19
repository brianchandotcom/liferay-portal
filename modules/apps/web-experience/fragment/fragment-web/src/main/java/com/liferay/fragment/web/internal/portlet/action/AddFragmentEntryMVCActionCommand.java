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

package com.liferay.fragment.web.internal.portlet.action;

import com.liferay.fragment.constants.FragmentPortletKeys;
import com.liferay.fragment.exception.DuplicateFragmentEntryException;
import com.liferay.fragment.exception.FragmentEntryNameException;
import com.liferay.fragment.model.FragmentEntry;
import com.liferay.fragment.service.FragmentEntryService;
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
		"javax.portlet.name=" + FragmentPortletKeys.FRAGMENT,
		"mvc.command.name=addFragmentEntry"
	},
	service = MVCActionCommand.class
)
public class AddFragmentEntryMVCActionCommand extends BaseMVCActionCommand {

	@Override
	protected void doProcessAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		long fragmentCollectionId = ParamUtil.getLong(
			actionRequest, "fragmentCollectionId");

		String name = ParamUtil.getString(actionRequest, "name");
		String css = ParamUtil.getString(actionRequest, "cssContent");
		String js = ParamUtil.getString(actionRequest, "jsContent");
		String html = ParamUtil.getString(actionRequest, "htmlContent");

		try {
			ServiceContext serviceContext = ServiceContextFactory.getInstance(
				actionRequest);

			FragmentEntry fragmentEntry =
				_fragmentEntryService.addFragmentEntry(
					serviceContext.getScopeGroupId(), fragmentCollectionId,
					name, css, html, js, serviceContext);

			JSONObject jsonObject = JSONFactoryUtil.createJSONObject();

			jsonObject.put(
				"fragmentEntryId", fragmentEntry.getFragmentEntryId());

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

		if (pe instanceof DuplicateFragmentEntryException) {
			errorMessage = "a-fragment-entry-with-that-name-already-exists";
		}
		else if (pe instanceof FragmentEntryNameException) {
			errorMessage = "this-field-is-required";
		}

		ResourceBundle resourceBundle =
			_resourceBundleLoader.loadResourceBundle(themeDisplay.getLocale());

		jsonObject.put("error", LanguageUtil.get(resourceBundle, errorMessage));

		JSONPortletResponseUtil.writeJSON(
			actionRequest, actionResponse, jsonObject);
	}

	@Reference
	private FragmentEntryService _fragmentEntryService;

	@Reference(
		target = "(bundle.symbolic.name=com.liferay.fragment.web)", unbind = "-"
	)
	private ResourceBundleLoader _resourceBundleLoader;

}