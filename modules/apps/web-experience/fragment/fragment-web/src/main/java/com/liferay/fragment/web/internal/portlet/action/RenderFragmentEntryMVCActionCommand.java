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
import com.liferay.fragment.service.FragmentEntryService;
import com.liferay.fragment.util.FragmentRenderUtil;
import com.liferay.fragment.web.internal.handler.FragmentEntryExceptionRequestHandler;
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
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.ResourceBundleLoader;
import com.liferay.portal.kernel.util.WebKeys;

import java.util.ResourceBundle;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Pablo Molina
 */
@Component(
	immediate = true,
	property = {
		"javax.portlet.name=" + FragmentPortletKeys.FRAGMENT,
		"mvc.command.name=/fragment/render_fragment_entry"
	},
	service = MVCActionCommand.class
)
public class RenderFragmentEntryMVCActionCommand extends BaseMVCActionCommand {

	@Override
	protected void doProcessAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		long fragmentEntryId = ParamUtil.getLong(
			actionRequest, "fragmentEntryId");

		String css = ParamUtil.getString(actionRequest, "css");
		String html = ParamUtil.getString(actionRequest, "html");
		String js = ParamUtil.getString(actionRequest, "js");

		JSONObject jsonObject = JSONFactoryUtil.createJSONObject();

		try {
			String content = FragmentRenderUtil.renderFragment(
				fragmentEntryId, css, html, js);

			jsonObject.put("content", content);
		}
		catch (PortalException pe) {
			ServiceContext serviceContext = ServiceContextFactory.getInstance(
				actionRequest);

			ThemeDisplay themeDisplay =
				(ThemeDisplay)actionRequest.getAttribute(WebKeys.THEME_DISPLAY);

			ResourceBundle resourceBundle =
				_resourceBundleLoader.loadResourceBundle(
					themeDisplay.getLocale());

			hideDefaultSuccessMessage(actionRequest);

			jsonObject.put(
				"error",
				LanguageUtil.get(
					resourceBundle, "an-unexpected-error-occurred"));
		}

		JSONPortletResponseUtil.writeJSON(
			actionRequest, actionResponse, jsonObject);
	}

	@Reference
	private FragmentEntryExceptionRequestHandler
		_fragmentEntryExceptionRequestHandler;

	@Reference
	private FragmentEntryService _fragmentEntryService;

	@Reference
	private Portal _portal;

	@Reference(
		target = "(bundle.symbolic.name=com.liferay.fragment.web)", unbind = "-"
	)
	private ResourceBundleLoader _resourceBundleLoader;

}