/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.layoutsadmin.util;

import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.staging.LayoutStagingUtil;
import com.liferay.portal.kernel.staging.StagingUtil;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.model.Layout;
import com.liferay.portal.model.LayoutBranch;
import com.liferay.portal.model.LayoutRevision;
import com.liferay.portal.model.LayoutSetBranch;
import com.liferay.portal.model.User;
import com.liferay.portal.model.impl.VirtualLayout;
import com.liferay.portal.service.LayoutLocalServiceUtil;
import com.liferay.portal.service.LayoutSetBranchLocalServiceUtil;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PropsValues;
import com.liferay.portlet.sites.util.SitesUtil;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Brian Wing Shun Chan
 * @author Eduardo Lundgren
 * @author Bruno Basto
 */
public class LayoutsTreeUtil {

	public static List<Layout> getLayouts(
			HttpServletRequest request, long groupId, boolean privateLayout,
			long parentLayoutId)
		throws Exception {

		boolean incomplete = ParamUtil.getBoolean(request, "incomplete", true);
		int start = ParamUtil.getInteger(request, "start");
		int end = start + PropsValues.LAYOUT_MANAGE_PAGES_INITIAL_CHILDREN;

		return LayoutLocalServiceUtil.getLayouts(
			groupId, privateLayout, parentLayoutId, incomplete, start, end);
	}

	public static String getLayoutsJSON(
			HttpServletRequest request, long groupId, boolean privateLayout,
			long parentLayoutId)
		throws Exception {

		return getLayoutsJSON(
			request, groupId, privateLayout, parentLayoutId, null);
	}

	public static String getLayoutsJSON(
			HttpServletRequest request, long groupId, boolean privateLayout,
			long parentLayoutId, long[] expandedLayoutIds)
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)request.getAttribute(
			WebKeys.THEME_DISPLAY);

		JSONArray jsonArray = JSONFactoryUtil.createJSONArray();

		List<Layout> layoutAncestors = null;

		long selPlid = ParamUtil.getLong(request, "selPlid");

		if (selPlid != 0) {
			Layout selLayout = LayoutLocalServiceUtil.getLayout(selPlid);

			layoutAncestors = selLayout.getAncestors();
		}

		List<Layout> layouts = getLayouts(
			request, groupId, privateLayout, parentLayoutId);

		for (Layout layout : layouts) {
			JSONObject jsonObject = JSONFactoryUtil.createJSONObject();

			if ((layoutAncestors != null) && layoutAncestors.contains(layout) ||
				ArrayUtil.contains(expandedLayoutIds, layout.getLayoutId())) {

				String childrenJSON = getLayoutsJSON(
					request, groupId, layout.getPrivateLayout(),
					layout.getLayoutId(), expandedLayoutIds);

				jsonObject.put(
					"children", JSONFactoryUtil.createJSONArray(childrenJSON));
			}

			jsonObject.put("contentDisplayPage", layout.isContentDisplayPage());
			jsonObject.put("friendlyURL", layout.getFriendlyURL());
			jsonObject.put("hasChildren", layout.hasChildren());
			jsonObject.put("layoutId", layout.getLayoutId());
			jsonObject.put("name", layout.getName(themeDisplay.getLocale()));
			jsonObject.put("parentLayoutId", layout.getParentLayoutId());
			jsonObject.put("plid", layout.getPlid());
			jsonObject.put("priority", layout.getPriority());
			jsonObject.put("privateLayout", layout.isPrivateLayout());
			jsonObject.put("type", layout.getType());
			jsonObject.put("updateable", SitesUtil.isLayoutUpdateable(layout));
			jsonObject.put("uuid", layout.getUuid());

			if (layout instanceof VirtualLayout) {
				VirtualLayout virtualLayout = (VirtualLayout)layout;

				jsonObject.put("groupId", virtualLayout.getSourceGroupId());
			}
			else {
				jsonObject.put("groupId", layout.getGroupId());
			}

			LayoutRevision layoutRevision = LayoutStagingUtil.getLayoutRevision(
				layout);

			if (layoutRevision != null) {
				User user = themeDisplay.getUser();

				long recentLayoutSetBranchId =
					StagingUtil.getRecentLayoutSetBranchId(
						user, layout.getLayoutSet().getLayoutSetId());

				if (StagingUtil.isIncomplete(layout, recentLayoutSetBranchId)) {
					jsonObject.put("incomplete", true);
				}

				long layoutSetBranchId = layoutRevision.getLayoutSetBranchId();

				LayoutSetBranch layoutSetBranch =
					LayoutSetBranchLocalServiceUtil.getLayoutSetBranch(
						layoutSetBranchId);

				LayoutBranch layoutBranch = layoutRevision.getLayoutBranch();

				if (!layoutBranch.isMaster()) {
					jsonObject.put(
						"layoutBranchId", layoutBranch.getLayoutBranchId());
					jsonObject.put("layoutBranchName", layoutBranch.getName());
				}

				jsonObject.put(
					"layoutRevisionId", layoutRevision.getLayoutRevisionId());
				jsonObject.put("layoutSetBranchId", layoutSetBranchId);
				jsonObject.put(
					"layoutSetBranchName", layoutSetBranch.getName());
			}

			jsonArray.put(jsonObject);
		}

		return jsonArray.toString();
	}

}