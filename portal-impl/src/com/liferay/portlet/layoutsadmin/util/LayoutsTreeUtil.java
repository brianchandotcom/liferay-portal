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

import com.liferay.exportimport.kernel.staging.LayoutStagingUtil;
import com.liferay.exportimport.kernel.staging.StagingUtil;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.LayoutBranch;
import com.liferay.portal.kernel.model.LayoutConstants;
import com.liferay.portal.kernel.model.LayoutRevision;
import com.liferay.portal.kernel.model.LayoutSetBranch;
import com.liferay.portal.kernel.model.impl.VirtualLayout;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.service.LayoutLocalServiceUtil;
import com.liferay.portal.kernel.service.LayoutServiceUtil;
import com.liferay.portal.kernel.service.LayoutSetBranchLocalServiceUtil;
import com.liferay.portal.kernel.service.permission.GroupPermissionUtil;
import com.liferay.portal.kernel.service.permission.LayoutPermissionUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.SessionClicks;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.util.PropsValues;
import com.liferay.sites.kernel.util.SitesUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

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
			long layoutId, int max)
		throws Exception {

		Layout layout = LayoutLocalServiceUtil.getLayout(
			groupId, privateLayout, layoutId);

		long parentLayoutId = layout.getParentLayoutId();

		long includedLayoutIndex = LayoutServiceUtil.getLayoutsCount(
			groupId, privateLayout, parentLayoutId, layout.getPriority());

		int total = LayoutServiceUtil.getLayoutsCount(
			groupId, privateLayout, parentLayoutId);

		int start = (int)includedLayoutIndex - 1;
		int end = (int)includedLayoutIndex + max;

		if (end > total) {
			start = total - max;
			end = total;

			if (start < 0) {
				start = 0;
			}
		}

		List<Layout> layouts = LayoutServiceUtil.getLayouts(
			groupId, privateLayout, parentLayoutId, true, start, end);

		JSONObject jsonObject = _toJSONObject(request, groupId, layouts, total);

		List<Layout> ancestorLayouts = LayoutServiceUtil.getAncestorLayouts(
			layout.getPlid());

		long[] ancestorLayoutIds = new long[ancestorLayouts.size()];
		String[] ancestorLayoutNames = new String[ancestorLayouts.size()];

		Locale locale = PortalUtil.getLocale(request);

		for (int i = 0; i < ancestorLayouts.size(); i++) {
			Layout ancestorLayout = ancestorLayouts.get(i);

			ancestorLayoutIds[i] = ancestorLayout.getLayoutId();
			ancestorLayoutNames[i] = ancestorLayout.getName(locale);
		}

		jsonObject.put("ancestorLayoutIds", ancestorLayoutIds);
		jsonObject.put("ancestorLayoutNames", ancestorLayoutNames);

		jsonObject.put("start", start);

		return jsonObject.toString();
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

	private static Layout _fetchCurrentLayout(HttpServletRequest request) {
		long selPlid = ParamUtil.getLong(request, "selPlid");

		if (selPlid > 0) {
			return LayoutLocalServiceUtil.fetchLayout(selPlid);
		}

		ThemeDisplay themeDisplay = (ThemeDisplay)request.getAttribute(
			WebKeys.THEME_DISPLAY);

		Layout layout = themeDisplay.getLayout();

		if (!layout.isTypeControlPanel()) {
			return layout;
		}

		return null;
	}

	private static List<Layout> _getAncestorLayouts(HttpServletRequest request)
		throws Exception {

		Layout layout = _fetchCurrentLayout(request);

		if (layout == null) {
			return Collections.emptyList();
		}

		List<Layout> ancestorLayouts = LayoutServiceUtil.getAncestorLayouts(
			layout.getPlid());

		if (_log.isDebugEnabled()) {
			StringBundler sb = new StringBundler(7);

			sb.append("_getAncestorLayouts(plid=");
			sb.append(layout.getPlid());
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

		return new LayoutTreeNodes(layoutTreeNodes, layouts.size());
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

		JSONObject paginationJSONObject = JSONFactoryUtil.createJSONObject(
			paginationJSON);

		if (_log.isDebugEnabled()) {
			sb = new StringBundler(9);

			sb.append("_getLoadedLayoutsCount(key=");
			sb.append(key);
			sb.append(", layoutId=");
			sb.append(layoutId);
			sb.append(", paginationJSON=");
			sb.append(paginationJSON);
			sb.append(", paginationJSONObject");
			sb.append(paginationJSONObject);
			sb.append(StringPool.CLOSE_PARENTHESIS);

			_log.debug(sb.toString());
		}

		return paginationJSONObject.getInt(String.valueOf(layoutId), 0);
	}

	private static boolean _isDeleteable(
			Layout layout, ThemeDisplay themeDisplay)
		throws PortalException {

		if (!LayoutPermissionUtil.contains(
				themeDisplay.getPermissionChecker(), layout,
				ActionKeys.DELETE)) {

			return false;
		}

		Group group = layout.getGroup();

		if (group.isGuest() && !layout.isPrivateLayout() &&
			layout.isRootLayout() &&
			(LayoutLocalServiceUtil.getLayoutsCount(
				group, false, LayoutConstants.DEFAULT_PARENT_LAYOUT_ID) == 1)) {

			return false;
		}

		return true;
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

		JSONObject jsonObject = _toJSONObject(
			request, groupId, layoutTreeNodes);

		return jsonObject.toString();
	}

	private static JSONObject _toJSONObject(
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

			JSONObject childrenJSONObject = _toJSONObject(
				request, groupId, layoutTreeNode.getChildLayoutTreeNodes());

			jsonObject.put("children", childrenJSONObject);

			Layout layout = layoutTreeNode.getLayout();

			jsonObject.put("contentDisplayPage", layout.isContentDisplayPage());
			jsonObject.put("deleteable", _isDeleteable(layout, themeDisplay));
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
			jsonObject.put(
				"parentable",
				LayoutPermissionUtil.contains(
					themeDisplay.getPermissionChecker(), layout,
					ActionKeys.ADD_LAYOUT));
			jsonObject.put("parentLayoutId", layout.getParentLayoutId());
			jsonObject.put("plid", layout.getPlid());
			jsonObject.put("priority", layout.getPriority());
			jsonObject.put("privateLayout", layout.isPrivateLayout());
			jsonObject.put("regularURL", layout.getRegularURL(request));
			jsonObject.put(
				"sortable", hasManageLayoutsPermission &&
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

		return responseJSONObject;
	}

	private static JSONObject _toJSONObject(
			HttpServletRequest request, long groupId, List<Layout> layouts,
			int total)
		throws Exception {

		List<LayoutTreeNode> layoutTreeNodesList = new ArrayList<>();

		for (Layout layout : layouts) {
			LayoutTreeNode layoutTreeNode = new LayoutTreeNode(layout);

			layoutTreeNodesList.add(layoutTreeNode);
		}

		LayoutTreeNodes layoutTreeNodes = new LayoutTreeNodes(
			layoutTreeNodesList, total);

		return _toJSONObject(request, groupId, layoutTreeNodes);
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