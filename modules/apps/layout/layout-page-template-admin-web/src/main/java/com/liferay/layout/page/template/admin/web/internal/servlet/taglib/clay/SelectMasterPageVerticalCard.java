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

package com.liferay.layout.page.template.admin.web.internal.servlet.taglib.clay;

import com.liferay.frontend.taglib.clay.servlet.taglib.soy.VerticalCard;
import com.liferay.layout.page.template.model.LayoutPageTemplateEntry;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.WebKeys;

import java.util.HashMap;
import java.util.Map;

import javax.portlet.ActionRequest;
import javax.portlet.PortletURL;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Eudaldo Alonso
 */
public class SelectMasterPageVerticalCard implements VerticalCard {

	public SelectMasterPageVerticalCard(
		LayoutPageTemplateEntry layoutPageTemplateEntry,
		RenderRequest renderRequest, RenderResponse renderResponse) {

		_layoutPageTemplateEntry = layoutPageTemplateEntry;
		_renderResponse = renderResponse;

		_httpServletRequest = PortalUtil.getHttpServletRequest(renderRequest);
		_themeDisplay = (ThemeDisplay)renderRequest.getAttribute(
			WebKeys.THEME_DISPLAY);
	}

	@Override
	public Map<String, String> getData() {
		Map<String, String> data = new HashMap<>();

		String redirect = ParamUtil.getString(_httpServletRequest, "redirect");

		long layoutPageTemplateCollectionId = ParamUtil.getLong(
			_httpServletRequest, "layoutPageTemplateCollectionId");

		try {
			PortletURL addLayoutPageTemplateEntryURL =
				_renderResponse.createActionURL();

			addLayoutPageTemplateEntryURL.setParameter(
				ActionRequest.ACTION_NAME,
				"/layout_page_template/add_layout_page_template_entry");
			addLayoutPageTemplateEntryURL.setParameter(
				"redirect", _themeDisplay.getURLCurrent());
			addLayoutPageTemplateEntryURL.setParameter("backURL", redirect);
			addLayoutPageTemplateEntryURL.setParameter(
				"layoutPageTemplateCollectionId",
				String.valueOf(layoutPageTemplateCollectionId));
			addLayoutPageTemplateEntryURL.setParameter(
				"masterLayoutPlid",
				String.valueOf(_layoutPageTemplateEntry.getPlid()));

			data.put(
				"add-layout-page-template-entry-url",
				addLayoutPageTemplateEntryURL.toString());
		}
		catch (Exception e) {
		}

		return data;
	}

	@Override
	public String getElementClasses() {
		return "add-master-page-action-option card-interactive " +
			"card-interactive-primary";
	}

	@Override
	public String getIcon() {
		return "page";
	}

	@Override
	public String getImageSrc() {
		return _layoutPageTemplateEntry.getImagePreviewURL(_themeDisplay);
	}

	@Override
	public String getTitle() {
		return _layoutPageTemplateEntry.getName();
	}

	@Override
	public boolean isSelectable() {
		return false;
	}

	private final HttpServletRequest _httpServletRequest;
	private final LayoutPageTemplateEntry _layoutPageTemplateEntry;
	private final RenderResponse _renderResponse;
	private final ThemeDisplay _themeDisplay;

}