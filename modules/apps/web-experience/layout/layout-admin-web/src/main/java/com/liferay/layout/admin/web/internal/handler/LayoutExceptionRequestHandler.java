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

package com.liferay.layout.admin.web.internal.handler;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.LayoutNameException;
import com.liferay.portal.kernel.exception.LayoutTypeException;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.portlet.JSONPortletResponseUtil;
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
@Component(immediate = true, service = LayoutExceptionRequestHandler.class)
public class LayoutExceptionRequestHandler {

	public void handlePortalException(
			ActionRequest actionRequest, ActionResponse actionResponse,
			PortalException pe)
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		ResourceBundle resourceBundle =
			_resourceBundleLoader.loadResourceBundle(themeDisplay.getLocale());

		JSONObject jsonObject = null;

		if (pe instanceof LayoutNameException) {
			jsonObject = _handleLayoutNameException(resourceBundle);
		}
		else if (pe instanceof LayoutTypeException) {
			jsonObject = _handleLayoutTypeException(
				actionRequest, (LayoutTypeException)pe, themeDisplay,
				resourceBundle);
		}
		else {
			jsonObject = _handleUnexpectedException(themeDisplay);
		}

		JSONPortletResponseUtil.writeJSON(
			actionRequest, actionResponse, jsonObject);
	}

	private JSONObject _handleLayoutNameException(
		ResourceBundle resourceBundle) {

		JSONObject jsonObject = JSONFactoryUtil.createJSONObject();

		jsonObject.put(
			"error",
			LanguageUtil.get(
				resourceBundle, "please-enter-a-valid-name-for-the-page"));

		return jsonObject;
	}

	private JSONObject _handleLayoutTypeException(
		ActionRequest actionRequest, LayoutTypeException lte,
		ThemeDisplay themeDisplay, ResourceBundle resourceBundle) {

		if (!((lte.getType() == LayoutTypeException.FIRST_LAYOUT) ||
			  (lte.getType() == LayoutTypeException.NOT_INSTANCEABLE))) {

			return _handleUnexpectedException(themeDisplay);
		}

		String errorMessage = StringPool.BLANK;

		if (lte.getType() == LayoutTypeException.FIRST_LAYOUT) {
			errorMessage = "the-first-page-cannot-be-of-type-x";
		}
		else if (lte.getType() == LayoutTypeException.NOT_INSTANCEABLE) {
			errorMessage = "pages-of-type-x-cannot-be-selected";
		}

		String type = ParamUtil.getString(actionRequest, "type");

		JSONObject jsonObject = JSONFactoryUtil.createJSONObject();

		jsonObject.put(
			"error",
			LanguageUtil.format(
				resourceBundle, errorMessage, new String[] {type}));

		return jsonObject;
	}

	private JSONObject _handleUnexpectedException(ThemeDisplay themeDisplay) {
		JSONObject jsonObject = JSONFactoryUtil.createJSONObject();

		jsonObject.put(
			"error",
			LanguageUtil.get(
				themeDisplay.getLocale(), "an-unexpected-error-occurred"));

		return jsonObject;
	}

	@Reference(
		target = "(bundle.symbolic.name=com.liferay.layout.admin.web)",
		unbind = "-"
	)
	private ResourceBundleLoader _resourceBundleLoader;

}