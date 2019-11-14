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
import com.liferay.layout.seo.model.LayoutSEOEntry;
import com.liferay.layout.seo.service.LayoutSEOEntryLocalService;
import com.liferay.layout.seo.service.LayoutSEOEntryService;
import com.liferay.portal.events.EventsProcessorUtil;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.LayoutTypePortlet;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextFactory;
import com.liferay.portal.kernel.servlet.MultiSessionMessages;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.LocalizationUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;

import java.util.Locale;
import java.util.Map;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alejandro Tardín
 */
@Component(
	immediate = true,
	property = {
		"javax.portlet.name=" + LayoutAdminPortletKeys.GROUP_PAGES,
		"mvc.command.name=/layout/edit_open_graph"
	},
	service = MVCActionCommand.class
)
public class EditOpenGraphMVCActionCommand extends BaseMVCActionCommand {

	@Override
	protected void doProcessAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		long groupId = ParamUtil.getLong(actionRequest, "groupId");
		boolean privateLayout = ParamUtil.getBoolean(
			actionRequest, "privateLayout");
		long layoutId = ParamUtil.getLong(actionRequest, "layoutId");

		ServiceContext serviceContext = ServiceContextFactory.getInstance(
			Layout.class.getName(), actionRequest);

		Layout layout = _layoutLocalService.getLayout(
			groupId, privateLayout, layoutId);

		boolean openGraphDescriptionEnabled = ParamUtil.getBoolean(
			actionRequest, "openGraphDescriptionEnabled");
		Map<Locale, String> openGraphDescriptionMap =
			LocalizationUtil.getLocalizationMap(
				actionRequest, "openGraphDescription");
		long openGraphImageFileEntryId = ParamUtil.getLong(
			actionRequest, "openGraphImageFileEntryId");
		boolean openGraphTitleEnabled = ParamUtil.getBoolean(
			actionRequest, "openGraphTitleEnabled");
		Map<Locale, String> openGraphTitleMap =
			LocalizationUtil.getLocalizationMap(
				actionRequest, "openGraphTitle");

		LayoutSEOEntry layoutSEOEntry =
			_layoutSEOEntryLocalService.fetchLayoutSEOEntry(
				groupId, privateLayout, layoutId);

		boolean canonicalURLEnabled = false;

		if ((layoutSEOEntry != null) &&
			layoutSEOEntry.isCanonicalURLEnabled()) {

			canonicalURLEnabled = true;
		}

		Map<Locale, String> canonicalURLMap = null;

		if (layoutSEOEntry != null) {
			canonicalURLMap = layoutSEOEntry.getCanonicalURLMap();
		}

		_layoutSEOEntryService.updateLayoutSEOEntry(
			groupId, privateLayout, layoutId, canonicalURLEnabled,
			canonicalURLMap, openGraphDescriptionEnabled,
			openGraphDescriptionMap, openGraphImageFileEntryId,
			openGraphTitleEnabled, openGraphTitleMap, serviceContext);

		Layout draftLayout = _layoutLocalService.fetchLayout(
			_portal.getClassNameId(Layout.class), layout.getPlid());

		if (draftLayout != null) {
			_layoutSEOEntryService.updateLayoutSEOEntry(
				groupId, privateLayout, draftLayout.getLayoutId(),
				canonicalURLEnabled, canonicalURLMap,
				openGraphDescriptionEnabled, openGraphDescriptionMap,
				openGraphImageFileEntryId, openGraphTitleEnabled,
				openGraphTitleMap, serviceContext);
		}

		LayoutTypePortlet layoutTypePortlet =
			(LayoutTypePortlet)layout.getLayoutType();

		EventsProcessorUtil.process(
			PropsKeys.LAYOUT_CONFIGURATION_ACTION_UPDATE,
			layoutTypePortlet.getConfigurationActionUpdate(),
			_portal.getHttpServletRequest(actionRequest),
			_portal.getHttpServletResponse(actionResponse));

		String redirect = ParamUtil.getString(actionRequest, "redirect");

		if (Validator.isNull(redirect)) {
			ThemeDisplay themeDisplay =
				(ThemeDisplay)actionRequest.getAttribute(WebKeys.THEME_DISPLAY);

			redirect = _portal.getLayoutFullURL(layout, themeDisplay);
		}

		String portletResource = ParamUtil.getString(
			actionRequest, "portletResource");

		MultiSessionMessages.add(
			actionRequest, portletResource + "layoutUpdated", layout);

		actionRequest.setAttribute(WebKeys.REDIRECT, redirect);
	}

	@Reference
	private LayoutLocalService _layoutLocalService;

	@Reference
	private LayoutSEOEntryLocalService _layoutSEOEntryLocalService;

	@Reference
	private LayoutSEOEntryService _layoutSEOEntryService;

	@Reference
	private Portal _portal;

}