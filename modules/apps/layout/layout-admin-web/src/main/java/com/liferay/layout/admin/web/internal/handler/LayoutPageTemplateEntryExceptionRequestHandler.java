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

import com.liferay.layout.page.template.exception.LayoutPageTemplateEntryNameException;
import com.liferay.layout.page.template.model.LayoutPageTemplateEntry;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.model.ModelHintsUtil;
import com.liferay.portal.kernel.portlet.JSONPortletResponseUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ResourceBundleUtil;
import com.liferay.portal.kernel.util.WebKeys;

import java.util.ResourceBundle;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;

import org.osgi.service.component.annotations.Component;

/**
 * @author Jürgen Kappler
 */
@Component(
	immediate = true,
	service = LayoutPageTemplateEntryExceptionRequestHandler.class
)
public class LayoutPageTemplateEntryExceptionRequestHandler {

	public JSONObject createErrorJSONObject(
		ActionRequest actionRequest, PortalException pe) {

		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		ResourceBundle resourceBundle = ResourceBundleUtil.getBundle(
			themeDisplay.getLocale(),
			LayoutPageTemplateEntryExceptionRequestHandler.class);

		String errorMessage = "an-unexpected-error-occurred";

		if (pe instanceof
				LayoutPageTemplateEntryNameException.MustNotBeDuplicate) {

			errorMessage = LanguageUtil.get(
				resourceBundle,
				"a-page-template-entry-with-that-name-already-exists");
		}
		else if (pe instanceof
					LayoutPageTemplateEntryNameException.MustNotBeNull) {

			errorMessage = LanguageUtil.get(
				resourceBundle, "name-must-not-be-empty");
		}
		else if (pe instanceof
					LayoutPageTemplateEntryNameException.
						MustNotContainInvalidCharacters) {

			LayoutPageTemplateEntryNameException.MustNotContainInvalidCharacters
				lptene =
					(LayoutPageTemplateEntryNameException.
						MustNotContainInvalidCharacters)pe;

			errorMessage = LanguageUtil.format(
				resourceBundle,
				"name-cannot-contain-the-following-invalid-character-x",
				lptene.character);
		}
		else if (pe instanceof
					LayoutPageTemplateEntryNameException.
						MustNotExceedMaximumSize) {

			int nameMaxLength = ModelHintsUtil.getMaxLength(
				LayoutPageTemplateEntry.class.getName(), "name");

			errorMessage = LanguageUtil.format(
				resourceBundle,
				"please-enter-a-name-with-fewer-than-x-characters",
				nameMaxLength);
		}

		return JSONUtil.put("error", errorMessage);
	}

	public void handlePortalException(
			ActionRequest actionRequest, ActionResponse actionResponse,
			PortalException pe)
		throws Exception {

		JSONObject errorJSONObject = createErrorJSONObject(actionRequest, pe);

		JSONPortletResponseUtil.writeJSON(
			actionRequest, actionResponse, errorJSONObject);
	}

}