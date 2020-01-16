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

package com.liferay.layout.admin.web.internal.portlet.action;

import com.liferay.layout.admin.constants.LayoutAdminPortletKeys;
import com.liferay.layout.util.BulkLayoutConverter;
import com.liferay.layout.util.template.LayoutConversionResult;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.servlet.MultiSessionMessages;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.Http;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.WebKeys;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;

import javax.servlet.http.HttpServletResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Jürgen Kappler
 */
@Component(
	property = {
		"javax.portlet.name=" + LayoutAdminPortletKeys.GROUP_PAGES,
		"mvc.command.name=/layout/layout_conversion_preview"
	},
	service = MVCActionCommand.class
)
public class AddLayoutConversionPreviewMVCActionCommand
	extends BaseMVCActionCommand {

	@Override
	protected void doProcessAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		long plid = ParamUtil.getLong(actionRequest, "selPlid");

		try {
			HttpServletResponse httpServletResponse =
				_portal.getHttpServletResponse(actionResponse);

			ThemeDisplay themeDisplay =
				(ThemeDisplay)actionRequest.getAttribute(WebKeys.THEME_DISPLAY);

			LayoutConversionResult layoutConversionResult =
				_bulkLayoutConverter.generatePreviewLayout(
					plid, themeDisplay.getLocale());

			String layoutFullURL = _portal.getLayoutFullURL(
				layoutConversionResult.getDraftLayout(), themeDisplay);

			String redirect = ParamUtil.getString(actionRequest, "redirect");

			layoutFullURL = _http.setParameter(
				layoutFullURL, "p_l_back_url", redirect);

			layoutFullURL = _http.setParameter(
				layoutFullURL, "p_l_mode", Constants.EDIT);

			MultiSessionMessages.add(
				actionRequest, "layoutConversionWarningMessages",
				layoutConversionResult.getConversionWarningMessages());

			httpServletResponse.sendRedirect(layoutFullURL);
		}
		catch (Exception exception) {
			if (_log.isDebugEnabled()) {
				_log.debug(exception, exception);
			}
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		AddLayoutConversionPreviewMVCActionCommand.class);

	@Reference
	private BulkLayoutConverter _bulkLayoutConverter;

	@Reference
	private Http _http;

	@Reference
	private Portal _portal;

}