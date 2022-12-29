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

package com.liferay.layout.internal.struts;

import com.liferay.layout.util.LayoutsTree;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.service.LayoutService;
import com.liferay.portal.kernel.servlet.ServletResponseUtil;
import com.liferay.portal.kernel.struts.StrutsAction;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.util.PropsValues;
import com.liferay.product.navigation.product.menu.constants.ProductNavigationProductMenuWebKeys;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Eudaldo Alonso
 */
@Component(
	property = "path=/portal/get_layouts_tree", service = StrutsAction.class
)
public class GetLayoutsTreeStrutsAction implements StrutsAction {

	@Override
	public String execute(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse)
		throws Exception {

		httpServletRequest.setAttribute(
			ProductNavigationProductMenuWebKeys.RETURN_LAYOUTS_AS_ARRAY,
			Boolean.TRUE);

		ThemeDisplay themeDisplay =
			(ThemeDisplay)httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		long groupId = ParamUtil.getLong(
			httpServletRequest, "groupId", themeDisplay.getScopeGroupId());

		boolean privateLayout = ParamUtil.getBoolean(
			httpServletRequest, "privateLayout");
		long parentLayoutId = ParamUtil.getLong(
			httpServletRequest, "parentLayoutId");
		boolean incomplete = ParamUtil.getBoolean(
			httpServletRequest, "incomplete", true);

		ServletResponseUtil.write(
			httpServletResponse,
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

					int end = ParamUtil.getInteger(
						httpServletRequest, "end", start + pageSize);

					end = Math.max(start, end);

					if (childLayoutsCount > end) {
						return true;
					}

					return false;
				}
			).put(
				"items",
				_jsonFactory.createJSONArray(
					_layoutsTree.getLayoutsJSON(
						httpServletRequest, groupId, false, privateLayout,
						parentLayoutId, null, incomplete,
						"productMenuPagesTree", null))
			).toString());

		return null;
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