/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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
import com.liferay.portal.security.permission.ActionKeys;
import com.liferay.portal.service.LayoutLocalServiceUtil;
import com.liferay.portal.service.LayoutServiceUtil;
import com.liferay.portal.service.LayoutSetBranchLocalServiceUtil;
import com.liferay.portal.service.permission.GroupPermissionUtil;
import com.liferay.portal.service.permission.LayoutPermissionUtil;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.PropsValues;
import com.liferay.portal.util.SessionClicks;
import com.liferay.portlet.sites.util.SitesUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * @author Brian Wing Shun Chan
 * @author Eduardo Lundgren
 * @author Bruno Basto
 * @author Marcellus Tavares
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

		List<LayoutTreeNode> layoutTreeNodes = _getLayoutTreeNodes(
			request, groupId, privateLayout, parentLayoutId, incomplete,
			expandedLayoutIds);

		return _toJSON(request, groupId, layoutTreeNodes);
	}

	private static List<Layout> _getLayoutAncestors(HttpServletRequest request)
		throws Exception {

		long selPlid = ParamUtil.getLong(request, "selPlid");

		if (selPlid == 0) {
			return Collections.emptyList();
		}

		List<Layout> layoutAncestors = LayoutServiceUtil.getAncestorLayouts(
			selPlid);

		Layout selLayout = LayoutLocalServiceUtil.getLayout(selPlid);

		layoutAncestors.add(selLayout);

		return layoutAncestors;
	}

	private static List<Layout> _getLayouts(
			HttpServletRequest request, long groupId, boolean privateLayout,
			long parentLayoutId, boolean incomplete)
		throws Exception {

		List<Layout> layouts = LayoutServiceUtil.getLayouts(
			groupId, privateLayout, parentLayoutId, incomplete,
			QueryUtil.ALL_POS, QueryUtil.ALL_POS);

		if (!_isPaginationEnabled(request)) {
			return layouts;
		}

		return _paginateLayouts(request, parentLayoutId, layouts);
	}

	private static List<LayoutTreeNode> _getLayoutTreeNodes(
			HttpServletRequest request, long groupId, boolean privateLayout,
			long parentLayoutId, boolean incomplete, long[] expandedLayoutIds)
		throws Exception {

		List<LayoutTreeNode> layoutTreeNodes = new ArrayList<LayoutTreeNode>();

		List<Layout> layoutAncestors = _getLayoutAncestors(request);

		List<Layout> layouts = _getLayouts(
			request, groupId, privateLayout, parentLayoutId, incomplete);

		for (Layout layout : layouts) {
			LayoutTreeNode layoutTreeNode = new LayoutTreeNode(layout);

			boolean isLayoutExpandable = _isLayoutExpandable(
				request, layoutAncestors, expandedLayoutIds, layout);

			if (isLayoutExpandable) {
				List<LayoutTreeNode> children = new ArrayList<LayoutTreeNode>();

				if (layout instanceof VirtualLayout) {
					VirtualLayout virtualLayout = (VirtualLayout)layout;

					children = _getLayoutTreeNodes(
						request, virtualLayout.getSourceGroupId(),
						virtualLayout.isPrivateLayout(),
						virtualLayout.getLayoutId(), incomplete,
						expandedLayoutIds);
				}
				else {
					children = _getLayoutTreeNodes(
						request, groupId, layout.isPrivateLayout(),
						layout.getLayoutId(), incomplete, expandedLayoutIds);
				}

				layoutTreeNode.setChildren(children);
			}

			layoutTreeNodes.add(layoutTreeNode);
		}

		return layoutTreeNodes;
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

	private static boolean _isLayoutExpandable(
		HttpServletRequest request, List<Layout> layoutAncestors,
		long[] expandedLayoutIds, Layout layout) {

		boolean expandParentLayouts = ParamUtil.getBoolean(
			request, "expandParentLayouts");

		if (expandParentLayouts || layoutAncestors.contains(layout) ||
			ArrayUtil.contains(expandedLayoutIds, layout.getLayoutId())) {

			return true;
		}

		return false;
	}

	private static boolean _isPaginationEnabled(HttpServletRequest request) {
		boolean paginate = ParamUtil.getBoolean(request, "paginate", true);

		if (paginate ||
			(PropsValues.LAYOUT_MANAGE_PAGES_INITIAL_CHILDREN > -1)) {

			return true;
		}

		return false;
	}

	private static List<Layout> _paginateLayouts(
			HttpServletRequest request, long parentLayoutId,
			List<Layout> layouts)
		throws Exception {

		int start = ParamUtil.getInteger(request, "start");

		start = Math.max(0, Math.min(start, layouts.size()));

		int end = ParamUtil.getInteger(
			request, "end",
			start + PropsValues.LAYOUT_MANAGE_PAGES_INITIAL_CHILDREN);

		int loadedLayoutsCount = _getLoadedLayoutsCount(
			request, parentLayoutId);

		if (loadedLayoutsCount > end) {
			end = loadedLayoutsCount;
		}

		end = Math.max(start, Math.min(end, layouts.size()));

		return layouts.subList(start, end);
	}

	private static String _toJSON(
			HttpServletRequest request, long groupId,
			List<LayoutTreeNode> layoutTreeNodes)
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)request.getAttribute(
			WebKeys.THEME_DISPLAY);

		JSONArray jsonArray = JSONFactoryUtil.createJSONArray();

		boolean hasManageLayoutsPermission = GroupPermissionUtil.contains(
			themeDisplay.getPermissionChecker(), groupId,
			ActionKeys.MANAGE_LAYOUTS);

		for (LayoutTreeNode layoutTreeNode : layoutTreeNodes) {
			JSONObject jsonObject = JSONFactoryUtil.createJSONObject();

			String childrenJSON = _toJSON(
				request, groupId, layoutTreeNode.getChildren());

			jsonObject.put(
				"children", JSONFactoryUtil.createJSONObject(childrenJSON));

			Layout layout = layoutTreeNode.getLayout();

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
			jsonObject.put("parentable", PortalUtil.isLayoutParentable(layout));
			jsonObject.put("parentLayoutId", layout.getParentLayoutId());
			jsonObject.put("plid", layout.getPlid());
			jsonObject.put("priority", layout.getPriority());
			jsonObject.put("privateLayout", layout.isPrivateLayout());
			jsonObject.put(
				"sortable",
					hasManageLayoutsPermission &&
					SitesUtil.isLayoutSortable(layout));
			jsonObject.put("type", layout.getType());
			jsonObject.put(
				"updateable",
				LayoutPermissionUtil.contains(
					themeDisplay.getPermissionChecker(), layout,
					ActionKeys.UPDATE));
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

				if (layoutRevision.isHead()) {
					jsonObject.put("layoutRevisionHead", true);
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
		responseJSONObject.put("total", layoutTreeNodes.size());

		return responseJSONObject.toString();
	}

	private static class LayoutTreeNode {

		public LayoutTreeNode(Layout layout) {
			_layout = layout;
		}

		public Layout getLayout() {
			return _layout;
		}

		public List<LayoutTreeNode> getChildren() {
			return _children;
		}

		public void setChildren(List<LayoutTreeNode> children) {
			_children = children;
		}

		private Layout _layout;
		private List<LayoutTreeNode> _children =
			new ArrayList<LayoutTreeNode>();

	}

}