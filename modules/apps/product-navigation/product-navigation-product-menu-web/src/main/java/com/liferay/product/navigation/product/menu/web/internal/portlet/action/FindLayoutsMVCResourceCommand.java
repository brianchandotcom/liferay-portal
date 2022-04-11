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

package com.liferay.product.navigation.product.menu.web.internal.portlet.action;

import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.LayoutConstants;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCResourceCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCResourceCommand;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.servlet.ServletResponseUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.product.navigation.product.menu.constants.ProductNavigationProductMenuPortletKeys;

import java.util.Collections;
import java.util.List;
import java.util.Locale;

import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;

import javax.servlet.http.HttpServletResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Pavel Savinov
 */
@Component(
	immediate = true,
	property = {
		"javax.portlet.name=" + ProductNavigationProductMenuPortletKeys.PRODUCT_NAVIGATION_PRODUCT_MENU,
		"mvc.command.name=/product_navigation_product_menu/find_layouts"
	},
	service = MVCResourceCommand.class
)
public class FindLayoutsMVCResourceCommand extends BaseMVCResourceCommand {

	@Override
	protected void doServeResource(
			ResourceRequest resourceRequest, ResourceResponse resourceResponse)
		throws Exception {

		String keywords = ParamUtil.getString(resourceRequest, "keywords");

		JSONObject jsonObject = JSONFactoryUtil.createJSONObject();

		HttpServletResponse httpServletResponse =
			_portal.getHttpServletResponse(resourceResponse);

		httpServletResponse.setContentType(ContentTypes.APPLICATION_JSON);

		if (Validator.isNull(keywords)) {
			jsonObject.put(
				"layouts", JSONFactoryUtil.createJSONArray()
			).put(
				"totalCount", 0
			);

			ServletResponseUtil.write(
				httpServletResponse, jsonObject.toString());

			return;
		}

		ThemeDisplay themeDisplay = (ThemeDisplay)resourceRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		JSONArray jsonArray = JSONFactoryUtil.createJSONArray();

		List<Layout> layouts = _layoutLocalService.getLayouts(
			themeDisplay.getSiteGroupId(), keywords,
			new String[] {
				LayoutConstants.TYPE_COLLECTION, LayoutConstants.TYPE_CONTENT,
				LayoutConstants.TYPE_EMBEDDED,
				LayoutConstants.TYPE_LINK_TO_LAYOUT,
				LayoutConstants.TYPE_FULL_PAGE_APPLICATION,
				LayoutConstants.TYPE_PANEL, LayoutConstants.TYPE_PORTLET,
				LayoutConstants.TYPE_URL
			},
			new int[] {WorkflowConstants.STATUS_ANY}, 0, 10, null);

		for (Layout layout : layouts) {
			JSONArray layoutPathJSONArray = _getLayoutPathJSONArray(
				layout, themeDisplay.getLocale());

			jsonArray.put(
				JSONUtil.put(
					"name", layout.getName(themeDisplay.getLocale())
				).put(
					"path", layoutPathJSONArray
				).put(
					"url", _portal.getLayoutFullURL(layout, themeDisplay)
				));
		}

		jsonObject.put("layouts", jsonArray);

		int totalCount = _layoutLocalService.getLayoutsCount(
			themeDisplay.getSiteGroupId(), keywords,
			new String[] {
				LayoutConstants.TYPE_COLLECTION, LayoutConstants.TYPE_CONTENT,
				LayoutConstants.TYPE_EMBEDDED,
				LayoutConstants.TYPE_LINK_TO_LAYOUT,
				LayoutConstants.TYPE_FULL_PAGE_APPLICATION,
				LayoutConstants.TYPE_PANEL, LayoutConstants.TYPE_PORTLET,
				LayoutConstants.TYPE_URL
			},
			new int[] {WorkflowConstants.STATUS_ANY});

		jsonObject.put("totalCount", totalCount);

		ServletResponseUtil.write(httpServletResponse, jsonObject.toString());
	}

	private JSONArray _getLayoutPathJSONArray(Layout layout, Locale locale)
		throws Exception {

		JSONArray jsonArray = JSONFactoryUtil.createJSONArray();

		List<Layout> ancestorLayouts = layout.getAncestors();

		Collections.reverse(ancestorLayouts);

		for (Layout ancestorLayout : ancestorLayouts) {
			jsonArray.put(HtmlUtil.escape(ancestorLayout.getName(locale)));
		}

		return jsonArray;
	}

	@Reference
	private LayoutLocalService _layoutLocalService;

	@Reference
	private Portal _portal;

}