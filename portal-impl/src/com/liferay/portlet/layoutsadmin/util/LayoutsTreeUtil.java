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

package com.liferay.portlet.layoutsadmin.util;

import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.model.Layout;
import com.liferay.portal.model.LayoutBranch;
import com.liferay.portal.model.LayoutConstants;
import com.liferay.portal.model.LayoutRevision;
import com.liferay.portal.model.LayoutSetBranch;
import com.liferay.portal.model.LayoutType;
import com.liferay.portal.model.impl.VirtualLayout;
import com.liferay.portal.security.permission.ActionKeys;
import com.liferay.portal.service.LayoutLocalServiceUtil;
import com.liferay.portal.service.LayoutServiceUtil;
import com.liferay.portal.service.LayoutSetBranchLocalServiceUtil;
import com.liferay.portal.service.permission.GroupPermissionUtil;
import com.liferay.portal.service.permission.LayoutPermissionUtil;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PropsValues;
import com.liferay.portal.util.SessionClicks;
import com.liferay.portlet.exportimport.staging.LayoutStagingUtil;
import com.liferay.portlet.exportimport.staging.StagingUtil;
import com.liferay.portlet.sites.util.SitesUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * @author Brian Wing Shun Chan
 * @author Eduardo Lundgren
 * @author Bruno Basto
 * @author Marcellus Tavares
 * @author Zsolt Szabó
 * @author Tibor Lipusz
 */
public class LayoutsTreeUtil {

	public static String getLayoutsJSON(
			HttpServletRequest request, long groupId, boolean privateLayout,
			long parentLayoutId, boolean incomplete, String treeId)
		throws Exception {

		return getLayoutsJSON(
			request, groupId, privateLayout, parentLayoutId, null, incomplete,
			treeId);
	}

	public static String getLayoutsJSON(
			HttpServletRequest request, long groupId, boolean privateLayout,
			long parentLayoutId, long[] expandedLayoutIds, boolean incomplete,
			String treeId)
		throws Exception {

		if (_log.isDebugEnabled()) {
			StringBundler sb = new StringBundler(13);

			sb.append("getLayoutsJSON(groupId=");
			sb.append(groupId);
			sb.append(", privateLayout=");
			sb.append(privateLayout);
			sb.append(", parentLayoutId=");
			sb.append(parentLayoutId);
			sb.append(", expandedLayoutIds=");
			sb.append(expandedLayoutIds);
			sb.append(", incomplete=");
			sb.append(incomplete);
			sb.append(", treeId=");
			sb.append(treeId);
			sb.append(StringPool.CLOSE_PARENTHESIS);

			_log.debug(sb.toString());
		}

		LayoutTreeNodes layoutTreeNodes = _getLayoutTreeNodes(
			request, groupId, privateLayout, parentLayoutId, incomplete,
			expandedLayoutIds, treeId);

		return _toJSON(request, groupId, layoutTreeNodes);
	}

	public static String getLayoutsJSON(
			HttpServletRequest request, long groupId, String treeId)
		throws Exception {

		if (_log.isDebugEnabled()) {
			StringBundler sb = new StringBundler(5);

			sb.append("getLayoutsJSON(groupId=");
			sb.append(groupId);
			sb.append(", treeId=");
			sb.append(treeId);
			sb.append(StringPool.CLOSE_PARENTHESIS);

			_log.debug(sb.toString());
		}

		LayoutTreeNodes layoutTreeNodes = new LayoutTreeNodes();

		layoutTreeNodes.addAll(
			_getLayoutTreeNodes(
				request, groupId, true,
				LayoutConstants.DEFAULT_PARENT_LAYOUT_ID, false, null, treeId));
		layoutTreeNodes.addAll(
			_getLayoutTreeNodes(
				request, groupId, false,
				LayoutConstants.DEFAULT_PARENT_LAYOUT_ID, false, null, treeId));

		return _toJSON(request, groupId, layoutTreeNodes);
	}

	private static List<Layout> _getAncestorLayouts(HttpServletRequest request)
		throws Exception {

		long selPlid = ParamUtil.getLong(request, "selPlid");

		if (selPlid == 0) {
			return Collections.emptyList();
		}

		List<Layout> ancestorLayouts = LayoutServiceUtil.getAncestorLayouts(
			selPlid);

		Layout layout = LayoutLocalServiceUtil.getLayout(selPlid);

		if (_log.isDebugEnabled()) {
			StringBundler sb = new StringBundler(7);

			sb.append("_getAncestorLayouts(selPlid=");
			sb.append(selPlid);
			sb.append(", ancestorLayouts=");
			sb.append(ancestorLayouts);
			sb.append(", layout=");
			sb.append(layout);
			sb.append(StringPool.CLOSE_PARENTHESIS);

			_log.debug(sb.toString());
		}

		ancestorLayouts.add(layout);

		return ancestorLayouts;
	}

	private static LayoutTreeNodes _getLayoutTreeNodes(
			HttpServletRequest request, long groupId, boolean privateLayout,
			long parentLayoutId, boolean incomplete, long[] expandedLayoutIds,
			String treeId)
		throws Exception {

		if (_log.isDebugEnabled()) {
			StringBundler sb = new StringBundler(13);

			sb.append("_getLayoutTreeNodes(groupId=");
			sb.append(groupId);
			sb.append(", privateLayout=");
			sb.append(privateLayout);
			sb.append(", parentLayoutId=");
			sb.append(parentLayoutId);
			sb.append(", expandedLayoutIds=");
			sb.append(expandedLayoutIds);
			sb.append(", incomplete=");
			sb.append(incomplete);
			sb.append(", treeId=");
			sb.append(treeId);
			sb.append(StringPool.CLOSE_PARENTHESIS);

			_log.debug(sb.toString());
		}

		List<LayoutTreeNode> layoutTreeNodes = new ArrayList<>();

		List<Layout> ancestorLayouts = _getAncestorLayouts(request);

		List<Layout> layouts = LayoutServiceUtil.getLayouts(
			groupId, privateLayout, parentLayoutId, incomplete,
			QueryUtil.ALL_POS, QueryUtil.ALL_POS);

		for (Layout layout :
				_paginateLayouts(
					request, groupId, privateLayout, parentLayoutId, layouts,
					treeId)) {

			LayoutTreeNode layoutTreeNode = new LayoutTreeNode(layout);

			LayoutTreeNodes childLayoutTreeNodes = null;

			if (_isExpandableLayout(
					request, ancestorLayouts, expandedLayoutIds, layout)) {

				if (layout instanceof VirtualLayout) {
					VirtualLayout virtualLayout = (VirtualLayout)layout;

					childLayoutTreeNodes = _getLayoutTreeNodes(
						request, virtualLayout.getSourceGroupId(),
						virtualLayout.isPrivateLayout(),
						virtualLayout.getLayoutId(), incomplete,
						expandedLayoutIds, treeId);
				}
				else {
					childLayoutTreeNodes = _getLayoutTreeNodes(
						request, groupId, layout.isPrivateLayout(),
						layout.getLayoutId(), incomplete, expandedLayoutIds,
						treeId);
				}
			}
			else {
				int childLayoutsCount = LayoutServiceUtil.getLayoutsCount(
					groupId, privateLayout, layout.getLayoutId());

				childLayoutTreeNodes = new LayoutTreeNodes(
					new ArrayList<LayoutTreeNode>(), childLayoutsCount);
			}

			layoutTreeNode.setChildLayoutTreeNodes(childLayoutTreeNodes);

			layoutTreeNodes.add(layoutTreeNode);
		}

		return new LayoutTreeNodes(layoutTreeNodes, layoutTreeNodes.size());
	}

	private static int _getLoadedLayoutsCount(
			HttpSession session, long groupId, boolean privateLayout,
			long layoutId, String treeId)
		throws Exception {

		StringBundler sb = new StringBundler(7);

		sb.append(treeId);
		sb.append(StringPool.COLON);
		sb.append(groupId);
		sb.append(StringPool.COLON);
		sb.append(privateLayout);
		sb.append(StringPool.COLON);
		sb.append("Pagination");

		String key = sb.toString();

		String paginationJSON = SessionClicks.get(
			session, key, JSONFactoryUtil.getNullJSON());

		if (_log.isDebugEnabled()) {
			sb = new StringBundler(9);

			sb.append("_getLoadedLayoutsCount(key=");
			sb.append(key);
			sb.append(", layoutId=");
			sb.append(layoutId);
			sb.append(", paginationJSON=");
			sb.append(paginationJSON);
		}

		JSONObject paginationJSONObject = JSONFactoryUtil.createJSONObject(
			paginationJSON);

		if (_log.isDebugEnabled()) {
			sb.append(", paginationJSONObject");
			sb.append(paginationJSONObject);
			sb.append(StringPool.CLOSE_PARENTHESIS);

			_log.debug(sb.toString());
		}

		return paginationJSONObject.getInt(String.valueOf(layoutId), 0);
	}

	private static boolean _isExpandableLayout(
		HttpServletRequest request, List<Layout> ancestorLayouts,
		long[] expandedLayoutIds, Layout layout) {

		boolean expandParentLayouts = ParamUtil.getBoolean(
			request, "expandParentLayouts");

		if (expandParentLayouts || ancestorLayouts.contains(layout) ||
			ArrayUtil.contains(expandedLayoutIds, layout.getLayoutId())) {

			return true;
		}

		return false;
	}

	private static boolean _isPaginationEnabled(HttpServletRequest request) {
		boolean paginate = ParamUtil.getBoolean(request, "paginate", true);

		if (paginate &&
			(PropsValues.LAYOUT_MANAGE_PAGES_INITIAL_CHILDREN > -1)) {

			return true;
		}

		return false;
	}

	private static List<Layout> _paginateLayouts(
			HttpServletRequest request, long groupId, boolean privateLayout,
			long parentLayoutId, List<Layout> layouts, String treeId)
		throws Exception {

		if (!_isPaginationEnabled(request)) {
			return layouts;
		}

		HttpSession session = request.getSession();

		int loadedLayoutsCount = _getLoadedLayoutsCount(
			session, groupId, privateLayout, parentLayoutId, treeId);

		int start = ParamUtil.getInteger(request, "start");

		start = Math.max(0, Math.min(start, layouts.size()));

		int end = ParamUtil.getInteger(
			request, "end",
			start + PropsValues.LAYOUT_MANAGE_PAGES_INITIAL_CHILDREN);

		if (loadedLayoutsCount > end) {
			end = loadedLayoutsCount;
		}

		end = Math.max(start, Math.min(end, layouts.size()));

		if (_log.isDebugEnabled()) {
			StringBundler sb = new StringBundler(7);

			sb.append("_paginateLayouts(loadedLayoutsCount=");
			sb.append(loadedLayoutsCount);
			sb.append(", start=");
			sb.append(start);
			sb.append(", end=");
			sb.append(end);
			sb.append(StringPool.CLOSE_PARENTHESIS);

			_log.debug(sb.toString());
		}

		return layouts.subList(start, end);
	}

	private static String _toJSON(
			HttpServletRequest request, long groupId,
			LayoutTreeNodes layoutTreeNodes)
		throws Exception {

		if (_log.isDebugEnabled()) {
			StringBundler sb = new StringBundler(5);

			sb.append("_toJSON(groupId=");
			sb.append(groupId);
			sb.append(", layoutTreeNodes=");
			sb.append(layoutTreeNodes);
			sb.append(StringPool.CLOSE_PARENTHESIS);

			_log.debug(sb.toString());
		}

		ThemeDisplay themeDisplay = (ThemeDisplay)request.getAttribute(
			WebKeys.THEME_DISPLAY);

		JSONArray jsonArray = JSONFactoryUtil.createJSONArray();

		boolean hasManageLayoutsPermission = GroupPermissionUtil.contains(
			themeDisplay.getPermissionChecker(), groupId,
			ActionKeys.MANAGE_LAYOUTS);

		for (LayoutTreeNode layoutTreeNode : layoutTreeNodes) {
			JSONObject jsonObject = JSONFactoryUtil.createJSONObject();

			String childrenJSON = _toJSON(
				request, groupId, layoutTreeNode.getChildLayoutTreeNodes());

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

			LayoutType layoutType = layout.getLayoutType();

			jsonObject.put("parentable", layoutType.isParentable());

			jsonObject.put("parentLayoutId", layout.getParentLayoutId());
			jsonObject.put("plid", layout.getPlid());
			jsonObject.put("priority", layout.getPriority());
			jsonObject.put("privateLayout", layout.isPrivateLayout());
			jsonObject.put("regularURL", layout.getRegularURL(request));
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
				long layoutSetBranchId = layoutRevision.getLayoutSetBranchId();

				if (StagingUtil.isIncomplete(layout, layoutSetBranchId)) {
					jsonObject.put("incomplete", true);
				}

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
		responseJSONObject.put("total", layoutTreeNodes.getTotal());

		return responseJSONObject.toString();
	}

	private static final Log _log = LogFactoryUtil.getLog(
		LayoutsTreeUtil.class);

	private static class LayoutTreeNode {

		public LayoutTreeNode(Layout layout) {
			_layout = layout;
		}

		public LayoutTreeNodes getChildLayoutTreeNodes() {
			return _childLayoutTreeNodes;
		}

		public Layout getLayout() {
			return _layout;
		}

		public void setChildLayoutTreeNodes(
			LayoutTreeNodes childLayoutTreeNodes) {

			_childLayoutTreeNodes = childLayoutTreeNodes;
		}

		@Override
		public String toString() {
			StringBundler sb = new StringBundler(5);

			sb.append("{childLayoutTreeNodes=");
			sb.append(_childLayoutTreeNodes);
			sb.append(", layout=");
			sb.append(_layout);
			sb.append(StringPool.CLOSE_CURLY_BRACE);

			return sb.toString();
		}

		private LayoutTreeNodes _childLayoutTreeNodes = new LayoutTreeNodes();
		private final Layout _layout;

	}

	private static class LayoutTreeNodes implements Iterable<LayoutTreeNode> {

		public LayoutTreeNodes() {
			_layoutTreeNodesList = new ArrayList<>();
		}

		public LayoutTreeNodes(
			List<LayoutTreeNode> layoutTreeNodesList, int total) {

			_layoutTreeNodesList = layoutTreeNodesList;
			_total = total;
		}

		public void addAll(LayoutTreeNodes layoutTreeNodes) {
			_layoutTreeNodesList.addAll(
				layoutTreeNodes.getLayoutTreeNodesList());

			_total += layoutTreeNodes.getTotal();
		}

		public List<LayoutTreeNode> getLayoutTreeNodesList() {
			return _layoutTreeNodesList;
		}

		public int getTotal() {
			return _total;
		}

		@Override
		public Iterator<LayoutTreeNode> iterator() {
			return _layoutTreeNodesList.iterator();
		}

		@Override
		public String toString() {
			StringBundler sb = new StringBundler(5);

			sb.append("{layoutTreeNodesList=");
			sb.append(_layoutTreeNodesList);
			sb.append(", total=");
			sb.append(_total);
			sb.append(StringPool.CLOSE_CURLY_BRACE);

			return sb.toString();
		}

		private final List<LayoutTreeNode> _layoutTreeNodesList;
		private int _total;

	}

}