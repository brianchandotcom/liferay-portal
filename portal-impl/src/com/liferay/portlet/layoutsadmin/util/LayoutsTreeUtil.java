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

import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.staging.LayoutStagingUtil;
import com.liferay.portal.kernel.staging.StagingUtil;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.model.Layout;
import com.liferay.portal.model.LayoutBranch;
import com.liferay.portal.model.LayoutRevision;
import com.liferay.portal.model.LayoutSetBranch;
import com.liferay.portal.model.User;
import com.liferay.portal.model.impl.VirtualLayout;
import com.liferay.portal.service.LayoutLocalServiceUtil;
import com.liferay.portal.service.LayoutServiceUtil;
import com.liferay.portal.service.LayoutSetBranchLocalServiceUtil;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PropsValues;
import com.liferay.portal.util.SessionClicks;
import com.liferay.portlet.sites.util.SitesUtil;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * @author Brian Wing Shun Chan
 * @author Eduardo Lundgren
 * @author Bruno Basto
 */
public class LayoutsTreeUtil {

	public static String getLayoutsJSON(
			HttpServletRequest request, long groupId, boolean privateLayout,
			long parentLayoutId, boolean incomplete)
		throws Exception {

		return getLayoutsJSON(
			request, groupId, privateLayout, parentLayoutId, null, incomplete);
	}

	public static String getLayoutsJSON(
			HttpServletRequest request, long groupId, boolean privateLayout,
			long parentLayoutId, long[] expandedLayoutIds, boolean incomplete)
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)request.getAttribute(
			WebKeys.THEME_DISPLAY);

		JSONArray jsonArray = JSONFactoryUtil.createJSONArray();

		List<Layout> layoutAncestors = null;

		List<Layout> layouts = LayoutServiceUtil.getLayouts(
			groupId, privateLayout, parentLayoutId, incomplete,
			QueryUtil.ALL_POS, QueryUtil.ALL_POS);

		long selPlid = ParamUtil.getLong(request, "selPlid");

		if (selPlid != 0) {
			Layout selLayout = LayoutLocalServiceUtil.getLayout(selPlid);

			layoutAncestors = LayoutServiceUtil.getAncestorLayouts(selPlid);

			layoutAncestors.add(selLayout);
		}

		int start = 0;
		int end = layouts.size();

		if (PropsValues.LAYOUT_MANAGE_PAGES_INITIAL_CHILDREN > -1) {
			start = ParamUtil.getInteger(request, "start");
			start = Math.max(0, Math.min(start, layouts.size()));

			end = ParamUtil.getInteger(
				request, "end",
				start + PropsValues.LAYOUT_MANAGE_PAGES_INITIAL_CHILDREN);

			int loadedLayoutsCount = _getLoadedLayoutsCount(
				request, parentLayoutId);

			if (loadedLayoutsCount > end) {
				end = loadedLayoutsCount;
			}

			end = Math.max(start, Math.min(end, layouts.size()));
		}

		for (Layout layout : layouts.subList(start, end)) {
			JSONObject jsonObject = JSONFactoryUtil.createJSONObject();

			if ((layoutAncestors != null) && layoutAncestors.contains(layout) ||
				ArrayUtil.contains(expandedLayoutIds, layout.getLayoutId())) {

				String childrenJSON = StringPool.BLANK;

				if (layout instanceof VirtualLayout) {
					VirtualLayout virtualLayout = (VirtualLayout)layout;

					childrenJSON = getLayoutsJSON(
						request, virtualLayout.getSourceGroupId(),
						virtualLayout.isPrivateLayout(),
						virtualLayout.getLayoutId(), expandedLayoutIds,
						incomplete);

				}
				else {
					childrenJSON = getLayoutsJSON(
						request, groupId, layout.isPrivateLayout(),
						layout.getLayoutId(), expandedLayoutIds, incomplete);
				}

				jsonObject.put(
					"children", JSONFactoryUtil.createJSONObject(childrenJSON));
			}

			jsonObject.put("contentDisplayPage", layout.isContentDisplayPage());
			jsonObject.put("friendlyURL", layout.getFriendlyURL());

			if (layout instanceof VirtualLayout) {
				VirtualLayout virtualLayout = (VirtualLayout)layout;

				jsonObject.put("groupId", virtualLayout.getSourceGroupId());
			}
			else {
				jsonObject.put("groupId", layout.getGroupId());
			}

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

		JSONObject responseJSONObject = JSONFactoryUtil.createJSONObject();

		responseJSONObject.put("layouts", jsonArray);
		responseJSONObject.put("total", layouts.size());

		return responseJSONObject.toString();
	}

	private static int _getLoadedLayoutsCount(
			HttpServletRequest request, long layoutId)
		throws Exception {

		HttpSession session = request.getSession();

		String treeId = ParamUtil.getString(request, "treeId");
		long groupId = ParamUtil.getLong(request, "groupId");
		boolean privateLayout = ParamUtil.getBoolean(request, "privateLayout");

		StringBundler sb = new StringBundler(7);

		sb.append(treeId);
		sb.append(StringPool.COLON);
		sb.append(groupId);
		sb.append(StringPool.COLON);
		sb.append(privateLayout);
		sb.append(StringPool.COLON);
		sb.append("Pagination");

		String paginationJSON = SessionClicks.get(
			session, sb.toString(), JSONFactoryUtil.getNullJSON());

		JSONObject paginationJSONObject = JSONFactoryUtil.createJSONObject(
			paginationJSON);

		return paginationJSONObject.getInt(String.valueOf(layoutId), 0);
	}

}