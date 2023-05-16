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

import com.liferay.layout.util.LayoutsTree;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.portlet.JSONPortletResponseUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCResourceCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCResourceCommand;
import com.liferay.portal.kernel.service.LayoutService;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.SessionClicks;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.util.PropsValues;
import com.liferay.product.navigation.product.menu.constants.ProductNavigationProductMenuPortletKeys;

import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;

import javax.servlet.http.HttpServletRequest;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Rubén Pulido
 */
@Component(
	property = {
		"javax.portlet.name=" + ProductNavigationProductMenuPortletKeys.PRODUCT_NAVIGATION_PRODUCT_MENU,
		"mvc.command.name=/product_navigation_product_menu/get_layouts"
	},
	service = MVCResourceCommand.class
)
public class GetLayoutsMVCResourceCommand extends BaseMVCResourceCommand {

	@Override
	protected void doServeResource(
			ResourceRequest resourceRequest, ResourceResponse resourceResponse)
		throws Exception {

		HttpServletRequest httpServletRequest = _portal.getHttpServletRequest(
			resourceRequest);

		ThemeDisplay themeDisplay =
			(ThemeDisplay)httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		boolean incomplete = ParamUtil.getBoolean(
			httpServletRequest, "incomplete", true);
		long parentLayoutId = ParamUtil.getLong(
			httpServletRequest, "parentLayoutId");
		boolean privateLayout = ParamUtil.getBoolean(
			httpServletRequest, "privateLayout");

		JSONPortletResponseUtil.writeJSON(
			resourceRequest, resourceResponse,
			JSONUtil.put(
				"hasMoreElements",
				() -> {
					int childLayoutsCount = _layoutService.getLayoutsCount(
						themeDisplay.getScopeGroupId(), privateLayout,
						parentLayoutId);

					int start = ParamUtil.getInteger(
						httpServletRequest, "start");

					start = Math.max(0, start);

					int pageSize = GetterUtil.getInteger(
						PropsValues.LAYOUT_MANAGE_PAGES_INITIAL_CHILDREN);

					String key = StringBundler.concat(
						"productMenuPagesTree:", themeDisplay.getScopeGroupId(),
						StringPool.COLON, privateLayout, ":Pagination");

					String paginationJSON = SessionClicks.get(
						httpServletRequest.getSession(), key,
						_jsonFactory.getNullJSON());

					JSONObject paginationJSONObject =
						_jsonFactory.createJSONObject(paginationJSON);

					int loadedLayoutsCount = paginationJSONObject.getInt(
						String.valueOf(parentLayoutId), 0);

					int end = ParamUtil.getInteger(
						httpServletRequest, "end", start + pageSize);

					if (loadedLayoutsCount > end) {
						end = loadedLayoutsCount;
					}

					end = Math.max(start, end);

					if (childLayoutsCount > end) {
						return true;
					}

					return false;
				}
			).put(
				"items",
				_layoutsTree.getLayoutsJSONArray(
					null, themeDisplay.getScopeGroupId(), httpServletRequest,
					true, incomplete, true, parentLayoutId, privateLayout,
					"productMenuPagesTree")
			));
	}

	@Reference
	private JSONFactory _jsonFactory;

	@Reference
	private LayoutService _layoutService;

	@Reference
	private LayoutsTree _layoutsTree;

	@Reference
	private Portal _portal;

}