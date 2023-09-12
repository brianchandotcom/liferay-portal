/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.layout.taglib.internal.struts;

import com.liferay.layout.taglib.internal.util.LayoutUtil;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.service.LayoutService;
import com.liferay.portal.kernel.servlet.ServletResponseUtil;
import com.liferay.portal.kernel.struts.StrutsAction;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.util.PropsValues;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Eudaldo Alonso
 */
@Component(property = "path=/portal/get_layouts", service = StrutsAction.class)
public class GetLayoutsStrutsAction implements StrutsAction {

	@Override
	public String execute(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse)
		throws Exception {

		boolean checkDisplayPage = ParamUtil.getBoolean(
			httpServletRequest, "checkDisplayPage");
		boolean enableCurrentPage = ParamUtil.getBoolean(
			httpServletRequest, "enableCurrentPage");
		long groupId = ParamUtil.getLong(httpServletRequest, "groupId");
		String itemSelectorReturnType = ParamUtil.getString(
			httpServletRequest, "itemSelectorReturnType");
		boolean privateLayout = ParamUtil.getBoolean(
			httpServletRequest, "privateLayout");
		long parentLayoutId = ParamUtil.getLong(
			httpServletRequest, "parentLayoutId");
		String[] selectedLayoutUuids = ParamUtil.getStringValues(
			httpServletRequest, "layoutUuid");

		int start = ParamUtil.getInteger(httpServletRequest, "start");

		start = Math.max(0, start);

		int pageSize = GetterUtil.getInteger(
			PropsValues.LAYOUT_MANAGE_PAGES_INITIAL_CHILDREN);

		int end = ParamUtil.getInteger(
			httpServletRequest, "end", start + pageSize);

		int startEndMax = Math.max(start, end);

		ServletResponseUtil.write(
			httpServletResponse,
			JSONUtil.put(
				"hasMoreElements",
				() -> {
					int childLayoutsCount = _layoutService.getLayoutsCount(
						groupId, privateLayout, parentLayoutId);

					if (childLayoutsCount > startEndMax) {
						return true;
					}

					return false;
				}
			).put(
				"items",
				LayoutUtil.getLayoutsJSONArray(
					checkDisplayPage, enableCurrentPage, groupId,
					httpServletRequest, itemSelectorReturnType, privateLayout,
					parentLayoutId, selectedLayoutUuids, start, end)
			).toString());

		return null;
	}

	@Reference
	private JSONFactory _jsonFactory;

	@Reference
	private LayoutService _layoutService;

}