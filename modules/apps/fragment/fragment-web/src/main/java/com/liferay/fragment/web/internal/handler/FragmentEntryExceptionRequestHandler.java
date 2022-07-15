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

package com.liferay.fragment.web.internal.handler;

import com.liferay.fragment.exception.FragmentEntryConfigurationException;
import com.liferay.fragment.exception.FragmentEntryContentException;
import com.liferay.fragment.exception.FragmentEntryFieldTypesException;
import com.liferay.fragment.exception.FragmentEntryNameException;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.JSONPortletResponseUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.WebKeys;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Jürgen Kappler
 */
@Component(
	immediate = true, service = FragmentEntryExceptionRequestHandler.class
)
public class FragmentEntryExceptionRequestHandler {

	public void handlePortalException(
			ActionRequest actionRequest, ActionResponse actionResponse,
			PortalException portalException)
		throws Exception {

		if (_log.isDebugEnabled()) {
			_log.debug(portalException);
		}

		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		String errorMessage = _language.get(
			themeDisplay.getRequest(), "an-unexpected-error-occurred");

		if (portalException instanceof FragmentEntryConfigurationException) {
			errorMessage = _language.get(
				themeDisplay.getRequest(),
				"please-provide-a-valid-configuration-for-the-fragment");
		}
		else if (portalException instanceof FragmentEntryContentException) {
			errorMessage = portalException.getLocalizedMessage();
		}
		else if (portalException instanceof FragmentEntryFieldTypesException) {
			errorMessage = _language.get(
				themeDisplay.getRequest(),
				"please-provide-a-valid-field-types-for-the-fragment");
		}
		else if (portalException instanceof FragmentEntryNameException) {
			errorMessage = _language.get(
				themeDisplay.getRequest(), "please-enter-a-valid-name");
		}
		else {
			_log.error(portalException);
		}

		JSONObject jsonObject = JSONUtil.put("error", errorMessage);

		JSONPortletResponseUtil.writeJSON(
			actionRequest, actionResponse, jsonObject);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		FragmentEntryExceptionRequestHandler.class);

	@Reference
	private Language _language;

}