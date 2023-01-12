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

package com.liferay.layout.internal.util;

import com.liferay.exportimport.kernel.staging.LayoutStagingUtil;
import com.liferay.exportimport.kernel.staging.Staging;
import com.liferay.layout.internal.action.provider.LayoutActionProvider;
import com.liferay.layout.security.permission.resource.LayoutContentModelResourcePermission;
import com.liferay.layout.util.LayoutsTree;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONSerializable;
import com.liferay.portal.kernel.language.Language;
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
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.service.LayoutRevisionLocalService;
import com.liferay.portal.kernel.service.LayoutService;
import com.liferay.portal.kernel.service.LayoutSetBranchLocalService;
import com.liferay.portal.kernel.service.permission.GroupPermission;
import com.liferay.portal.kernel.service.permission.LayoutPermission;
import com.liferay.portal.kernel.servlet.BrowserSniffer;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.SessionClicks;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.util.PropsValues;
import com.liferay.product.navigation.product.menu.constants.ProductNavigationProductMenuWebKeys;
import com.liferay.site.navigation.service.SiteNavigationMenuLocalService;
import com.liferay.sites.kernel.util.Sites;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Brian Wing Shun Chan
 * @author Eduardo Lundgren
 * @author Bruno Basto
 * @author Marcellus Tavares
 * @author Zsolt Szabó
 * @author Tibor Lipusz
 */
@Component(service = LayoutsTree.class)
public class LayoutsTreeImpl implements LayoutsTree {

	@Override
	public String getLayoutsJSON(
			HttpServletRequest httpServletRequest, long groupId,
			boolean includeActions, boolean privateLayout, long layoutId,
			int max, LayoutSetBranch layoutSetBranch)
		throws Exception {

		Layout layout = _layoutLocalService.getLayout(
			groupId, privateLayout, layoutId);

		long parentLayoutId = layout.getParentLayoutId();

		long includedLayoutIndex = _layoutService.getLayoutsCount(
			groupId, privateLayout, parentLayoutId, layout.getPriority());

		int total = _layoutService.getLayoutsCount(
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

		List<Layout> layouts = _layoutService.getLayouts(
			groupId, privateLayout, parentLayoutId, true, start, end);

		JSONSerializable jsonSerializable = _toJSONSerializable(
			httpServletRequest, groupId, includeActions, layouts, total,
			layoutSetBranch);

		List<Layout> ancestorLayouts = _layoutService.getAncestorLayouts(
			layout.getPlid());

		long[] ancestorLayoutIds = new long[ancestorLayouts.size()];
		String[] ancestorLayoutNames = new String[ancestorLayouts.size()];

		Locale locale = _portal.getLocale(httpServletRequest);

		for (int i = 0; i < ancestorLayouts.size(); i++) {
			Layout ancestorLayout = ancestorLayouts.get(i);

			ancestorLayoutIds[i] = ancestorLayout.getLayoutId();
			ancestorLayoutNames[i] = ancestorLayout.getName(locale);
		}

		if (jsonSerializable instanceof JSONObject) {
			JSONObject jsonObject = (JSONObject)jsonSerializable;

			jsonObject.put(
				"ancestorLayoutIds", ancestorLayoutIds
			).put(
				"ancestorLayoutNames", ancestorLayoutNames
			).put(
				"start", start
			);
		}

		return jsonSerializable.toString();
	}

	@Override
	public String getLayoutsJSON(
			HttpServletRequest httpServletRequest, long groupId,
			boolean includeActions, boolean privateLayout, long parentLayoutId,
			long[] expandedLayoutIds, boolean incomplete, String treeId,
			LayoutSetBranch layoutSetBranch)
		throws Exception {

		if (_log.isDebugEnabled()) {
			_log.debug(
				StringBundler.concat(
					"getLayoutsJSON(groupId=", groupId, ", privateLayout=",
					privateLayout, ", parentLayoutId=", parentLayoutId,
					", expandedLayoutIds=", expandedLayoutIds, ", incomplete=",
					incomplete, ", treeId=", treeId,
					StringPool.CLOSE_PARENTHESIS));
		}

		LayoutTreeNodes layoutTreeNodes = _getLayoutTreeNodes(
			httpServletRequest, groupId, privateLayout, parentLayoutId,
			incomplete, expandedLayoutIds, treeId, false);

		return _toJSON(
			httpServletRequest, groupId, includeActions, layoutTreeNodes,
			layoutSetBranch);
	}

	@Override
	public String getLayoutsJSON(
			HttpServletRequest httpServletRequest, long groupId,
			boolean includeActions, String treeId,
			LayoutSetBranch layoutSetBranch)
		throws Exception {

		if (_log.isDebugEnabled()) {
			_log.debug(
				StringBundler.concat(
					"getLayoutsJSON(groupId=", groupId, ", treeId=", treeId,
					StringPool.CLOSE_PARENTHESIS));
		}

		LayoutTreeNodes layoutTreeNodes = new LayoutTreeNodes();

		layoutTreeNodes.addAll(
			_getLayoutTreeNodes(
				httpServletRequest, groupId, true,
				LayoutConstants.DEFAULT_PARENT_LAYOUT_ID, false, null, treeId,
				false));
		layoutTreeNodes.addAll(
			_getLayoutTreeNodes(
				httpServletRequest, groupId, false,
				LayoutConstants.DEFAULT_PARENT_LAYOUT_ID, false, null, treeId,
				false));

		return _toJSON(
			httpServletRequest, groupId, includeActions, layoutTreeNodes,
			layoutSetBranch);
	}

	private Layout _fetchCurrentLayout(HttpServletRequest httpServletRequest) {
		long selPlid = ParamUtil.getLong(httpServletRequest, "selPlid");

		if (selPlid > 0) {
			return _layoutLocalService.fetchLayout(selPlid);
		}

		ThemeDisplay themeDisplay =
			(ThemeDisplay)httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		Layout layout = themeDisplay.getLayout();

		if (!layout.isTypeControlPanel()) {
			return layout;
		}

		return null;
	}

	private List<Layout> _getAncestorLayouts(
			HttpServletRequest httpServletRequest)
		throws Exception {

		Layout layout = _fetchCurrentLayout(httpServletRequest);

		if (layout == null) {
			return Collections.emptyList();
		}

		List<Layout> ancestorLayouts = _layoutService.getAncestorLayouts(
			layout.getPlid());

		if (_log.isDebugEnabled()) {
			_log.debug(
				StringBundler.concat(
					"Get ancestor layouts ", ancestorLayouts, " for layout ",
					layout));
		}

		ancestorLayouts.add(layout);

		return ancestorLayouts;
	}

	private Layout _getDraftLayout(Layout layout) {
		if (!layout.isTypeContent()) {
			return null;
		}

		Layout draftLayout = layout.fetchDraftLayout();

		if (draftLayout == null) {
			return null;
		}

		if (draftLayout.isDraft() || !layout.isPublished()) {
			return draftLayout;
		}

		return null;
	}

	private LayoutTreeNodes _getLayoutTreeNodes(
			HttpServletRequest httpServletRequest, long groupId,
			boolean privateLayout, long parentLayoutId, boolean incomplete,
			long[] expandedLayoutIds, String treeId, boolean childLayout)
		throws Exception {

		if (_log.isDebugEnabled()) {
			_log.debug(
				StringBundler.concat(
					"_getLayoutTreeNodes(groupId=", groupId, ", privateLayout=",
					privateLayout, ", parentLayoutId=", parentLayoutId,
					", expandedLayoutIds=", expandedLayoutIds, ", incomplete=",
					incomplete, ", treeId=", treeId,
					StringPool.CLOSE_PARENTHESIS));
		}

		int count = _layoutService.getLayoutsCount(
			groupId, privateLayout, parentLayoutId);

		if (count <= 0) {
			return new LayoutTreeNodes(Collections.emptyList(), count);
		}

		List<LayoutTreeNode> layoutTreeNodes = new ArrayList<>();

		List<Layout> ancestorLayouts = _getAncestorLayouts(httpServletRequest);

		List<Layout> layouts = _getPaginatedLayouts(
			httpServletRequest, groupId, privateLayout, parentLayoutId,
			incomplete, treeId, childLayout, count,
			_layoutLocalService.getLayoutsCount(
				_groupLocalService.getGroup(groupId), privateLayout,
				parentLayoutId));

		for (Layout layout : layouts) {
			LayoutTreeNode layoutTreeNode = new LayoutTreeNode(layout);

			LayoutTreeNodes childLayoutTreeNodes = null;

			if (_isExpandableLayout(
					httpServletRequest, ancestorLayouts, expandedLayoutIds,
					layout)) {

				if (layout instanceof VirtualLayout) {
					VirtualLayout virtualLayout = (VirtualLayout)layout;

					childLayoutTreeNodes = _getLayoutTreeNodes(
						httpServletRequest, virtualLayout.getSourceGroupId(),
						virtualLayout.isPrivateLayout(),
						virtualLayout.getLayoutId(), incomplete,
						expandedLayoutIds, treeId, true);
				}
				else {
					childLayoutTreeNodes = _getLayoutTreeNodes(
						httpServletRequest, groupId, layout.isPrivateLayout(),
						layout.getLayoutId(), incomplete, expandedLayoutIds,
						treeId, true);
				}
			}
			else {
				int childLayoutsCount = _layoutService.getLayoutsCount(
					groupId, privateLayout, layout.getLayoutId());

				childLayoutTreeNodes = new LayoutTreeNodes(
					new ArrayList<LayoutTreeNode>(), childLayoutsCount);
			}

			layoutTreeNode.setChildLayoutTreeNodes(childLayoutTreeNodes);

			layoutTreeNodes.add(layoutTreeNode);
		}

		return new LayoutTreeNodes(layoutTreeNodes, count);
	}

	private int _getLoadedLayoutsCount(
			HttpSession httpSession, long groupId, boolean privateLayout,
			long layoutId, String treeId)
		throws Exception {

		String key = StringBundler.concat(
			treeId, StringPool.COLON, groupId, StringPool.COLON, privateLayout,
			":Pagination");

		String paginationJSON = SessionClicks.get(
			httpSession, key, _jsonFactory.getNullJSON());

		JSONObject paginationJSONObject = _jsonFactory.createJSONObject(
			paginationJSON);

		if (_log.isDebugEnabled()) {
			_log.debug(
				StringBundler.concat(
					"_getLoadedLayoutsCount(key=", key, ", layoutId=", layoutId,
					", paginationJSON=", paginationJSON,
					", paginationJSONObject", paginationJSONObject,
					StringPool.CLOSE_PARENTHESIS));
		}

		return paginationJSONObject.getInt(String.valueOf(layoutId), 0);
	}

	private List<Layout> _getPaginatedLayouts(
			HttpServletRequest httpServletRequest, long groupId,
			boolean privateLayout, long parentLayoutId, boolean incomplete,
			String treeId, boolean childLayout, int count, int totalCount)
		throws Exception {

		if (!_isPaginationEnabled(httpServletRequest)) {
			return _layoutService.getLayouts(
				groupId, privateLayout, parentLayoutId, incomplete,
				QueryUtil.ALL_POS, QueryUtil.ALL_POS);
		}

		int loadedLayoutsCount = _getLoadedLayoutsCount(
			httpServletRequest.getSession(), groupId, privateLayout,
			parentLayoutId, treeId);

		int start = ParamUtil.getInteger(httpServletRequest, "start");

		start = Math.max(0, Math.min(start, count));

		int end = ParamUtil.getInteger(
			httpServletRequest, "end",
			start + PropsValues.LAYOUT_MANAGE_PAGES_INITIAL_CHILDREN);

		if (loadedLayoutsCount > end) {
			end = loadedLayoutsCount;
		}

		long loadMoreParentLayoutId = GetterUtil.getLong(
			httpServletRequest.getAttribute(
				ProductNavigationProductMenuWebKeys.LOAD_MORE_PARENT_LAYOUT_ID),
			-1);

		if (loadMoreParentLayoutId == parentLayoutId) {
			String key = StringBundler.concat(
				treeId, StringPool.COLON, groupId, StringPool.COLON,
				privateLayout, ":Pagination");

			String paginationJSON = SessionClicks.get(
				httpServletRequest.getSession(), key,
				_jsonFactory.getNullJSON());

			JSONObject paginationJSONObject = _jsonFactory.createJSONObject(
				paginationJSON);

			paginationJSONObject.put(String.valueOf(parentLayoutId), end);

			SessionClicks.put(
				httpServletRequest.getSession(), key,
				paginationJSONObject.toString());
		}

		end = Math.max(start, Math.min(end, count));

		if (_log.isDebugEnabled()) {
			_log.debug(
				StringBundler.concat(
					"_getPaginatedLayouts(loadedLayoutsCount=",
					loadedLayoutsCount, ", start=", start, ", end=", end,
					StringPool.CLOSE_PARENTHESIS));
		}

		if (childLayout &&
			(count > PropsValues.LAYOUT_MANAGE_PAGES_INITIAL_CHILDREN) &&
			(start == PropsValues.LAYOUT_MANAGE_PAGES_INITIAL_CHILDREN)) {

			start = end;
		}

		if (count != totalCount) {
			List<Layout> layouts = _layoutService.getLayouts(
				groupId, privateLayout, parentLayoutId, incomplete,
				QueryUtil.ALL_POS, QueryUtil.ALL_POS);

			return layouts.subList(start, end);
		}

		return _layoutService.getLayouts(
			groupId, privateLayout, parentLayoutId, incomplete, start, end);
	}

	private boolean _isDeleteable(
			Layout layout, ThemeDisplay themeDisplay,
			LayoutSetBranch layoutSetBranch)
		throws Exception {

		if (!_layoutPermission.contains(
				themeDisplay.getPermissionChecker(), layout,
				ActionKeys.DELETE)) {

			return false;
		}

		Group group = layout.getGroup();

		if (group.isGuest() && !layout.isPrivateLayout() &&
			layout.isRootLayout()) {

			int count = _layoutLocalService.getLayoutsCount(
				group, false, LayoutConstants.DEFAULT_PARENT_LAYOUT_ID);

			if (count == 1) {
				return false;
			}
		}

		if (layoutSetBranch != null) {
			List<LayoutRevision> layoutRevisions =
				_layoutRevisionLocalService.getLayoutRevisions(
					layoutSetBranch.getLayoutSetBranchId(), layout.getPlid());

			if (layoutRevisions.size() == 1) {
				LayoutRevision layoutRevision = layoutRevisions.get(0);

				if (layoutRevision.isIncomplete()) {
					return false;
				}
			}
		}

		return true;
	}

	private boolean _isExpandableLayout(
		HttpServletRequest httpServletRequest, List<Layout> ancestorLayouts,
		long[] expandedLayoutIds, Layout layout) {

		boolean expandParentLayouts = ParamUtil.getBoolean(
			httpServletRequest, "expandParentLayouts");

		if (expandParentLayouts || ancestorLayouts.contains(layout) ||
			ArrayUtil.contains(expandedLayoutIds, layout.getLayoutId())) {

			return true;
		}

		return false;
	}

	private boolean _isPaginationEnabled(
		HttpServletRequest httpServletRequest) {

		boolean paginate = ParamUtil.getBoolean(
			httpServletRequest, "paginate", true);

		if (paginate &&
			(PropsValues.LAYOUT_MANAGE_PAGES_INITIAL_CHILDREN > -1)) {

			return true;
		}

		return false;
	}

	private String _toJSON(
			HttpServletRequest httpServletRequest, long groupId,
			boolean includeActions, LayoutTreeNodes layoutTreeNodes,
			LayoutSetBranch layoutSetBranch)
		throws Exception {

		JSONSerializable jsonSerializable = _toJSONSerializable(
			httpServletRequest, groupId, includeActions, layoutTreeNodes,
			layoutSetBranch);

		return jsonSerializable.toString();
	}

	private JSONArray _toJSONArray(
			HttpServletRequest httpServletRequest, long groupId,
			boolean includeActions, LayoutTreeNodes layoutTreeNodes,
			LayoutSetBranch layoutSetBranch)
		throws Exception {

		if (_log.isDebugEnabled()) {
			_log.debug(
				StringBundler.concat(
					"Group ", groupId, " and layout tree nodes ",
					layoutTreeNodes));
		}

		ThemeDisplay themeDisplay =
			(ThemeDisplay)httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		JSONArray jsonArray = _jsonFactory.createJSONArray();

		boolean hasManageLayoutsPermission = _groupPermission.contains(
			themeDisplay.getPermissionChecker(), groupId,
			ActionKeys.MANAGE_LAYOUTS);
		boolean mobile = _browserSniffer.isMobile(httpServletRequest);

		Layout afterDeleteSelectedLayout = null;
		Layout secondLayout = null;

		int index = 0;

		for (LayoutTreeNode layoutTreeNode : layoutTreeNodes) {
			if (index == 1) {
				secondLayout = layoutTreeNode.getLayout();

				break;
			}

			index++;
		}

		for (LayoutTreeNode layoutTreeNode : layoutTreeNodes) {
			LayoutTreeNodes childLayoutTreeNodes =
				layoutTreeNode.getChildLayoutTreeNodes();

			JSONSerializable childrenJSONSerializable = _toJSONSerializable(
				httpServletRequest, groupId, includeActions,
				childLayoutTreeNodes, layoutSetBranch);

			Layout layout = layoutTreeNode.getLayout();

			JSONObject jsonObject = _jsonFactory.createJSONObject();

			if (includeActions) {
				LayoutActionProvider layoutActionProvider =
					new LayoutActionProvider(
						httpServletRequest, _language,
						_siteNavigationMenuLocalService);

				if ((afterDeleteSelectedLayout == null) &&
					(layout.getParentLayoutId() !=
						LayoutConstants.DEFAULT_PARENT_LAYOUT_ID)) {

					afterDeleteSelectedLayout = _layoutLocalService.fetchLayout(
						layout.getParentPlid());
				}

				if (afterDeleteSelectedLayout == null) {
					afterDeleteSelectedLayout = secondLayout;
				}

				jsonObject.put(
					"actions",
					layoutActionProvider.getActionsJSONArray(
						layout, afterDeleteSelectedLayout));

				afterDeleteSelectedLayout = layout;
			}

			if (childrenJSONSerializable instanceof JSONArray) {
				JSONArray childrenJSONArray =
					(JSONArray)childrenJSONSerializable;

				if (childrenJSONArray.length() > 0) {
					jsonObject.put("children", childrenJSONSerializable);
				}
			}
			else {
				jsonObject.put("children", childrenJSONSerializable);
			}

			jsonObject.put(
				"contentDisplayPage", layout.isContentDisplayPage()
			).put(
				"deleteable",
				_isDeleteable(layout, themeDisplay, layoutSetBranch)
			);

			Layout draftLayout = _getDraftLayout(layout);

			boolean hasUpdatePermission =
				_layoutPermission.containsLayoutUpdatePermission(
					themeDisplay.getPermissionChecker(), layout);

			if ((draftLayout != null) && hasUpdatePermission) {
				jsonObject.put("draftStatus", "draft");

				String draftLayoutURL = _portal.getLayoutFriendlyURL(
					draftLayout, themeDisplay);

				jsonObject.put("draftURL", draftLayoutURL);
			}

			jsonObject.put("friendlyURL", layout.getFriendlyURL());

			if (layout instanceof VirtualLayout) {
				VirtualLayout virtualLayout = (VirtualLayout)layout;

				jsonObject.put("groupId", virtualLayout.getSourceGroupId());
			}
			else {
				jsonObject.put("groupId", layout.getGroupId());
			}

			jsonObject.put(
				"hasChildren", layout.hasChildren()
			).put(
				"id", layout.getPlid()
			).put(
				"layoutId", layout.getLayoutId()
			);

			String layoutName = layout.getName(themeDisplay.getLocale());

			if ((draftLayout != null) &&
				(hasUpdatePermission || !layout.isPublished() ||
				 _layoutContentModelResourcePermission.contains(
					 themeDisplay.getPermissionChecker(), layout.getPlid(),
					 ActionKeys.UPDATE))) {

				layoutName = layoutName + StringPool.STAR;
			}

			jsonObject.put(
				"icon", layout.getIcon()
			).put(
				"name", layoutName
			);

			List<LayoutTreeNode> layoutTreeNodesList =
				childLayoutTreeNodes.getLayoutTreeNodesList();

			if (childLayoutTreeNodes.getTotal() != layoutTreeNodesList.size()) {
				jsonObject.put("paginated", true);
			}

			jsonObject.put(
				"parentable",
				_layoutPermission.contains(
					themeDisplay.getPermissionChecker(), layout,
					ActionKeys.ADD_LAYOUT)
			).put(
				"parentLayoutId", layout.getParentLayoutId()
			).put(
				"plid", layout.getPlid()
			).put(
				"priority", layout.getPriority()
			).put(
				"privateLayout", layout.isPrivateLayout()
			).put(
				"regularURL",
				() -> {
					if (hasUpdatePermission || layout.isPublished()) {
						return layout.getRegularURL(httpServletRequest);
					}

					return StringPool.BLANK;
				}
			).put(
				"sortable",
				hasManageLayoutsPermission && !mobile &&
				_sites.isLayoutSortable(layout)
			).put(
				"target",
				GetterUtil.getString(
					HtmlUtil.escape(layout.getTypeSettingsProperty("target")),
					"_self")
			).put(
				"type", layout.getType()
			).put(
				"updateable", hasUpdatePermission
			).put(
				"uuid", layout.getUuid()
			);

			LayoutRevision layoutRevision = LayoutStagingUtil.getLayoutRevision(
				layout);

			if (layoutRevision != null) {
				long layoutSetBranchId = layoutRevision.getLayoutSetBranchId();

				if (_staging.isIncomplete(layout, layoutSetBranchId)) {
					jsonObject.put("incomplete", true);
				}

				LayoutSetBranch boundLayoutSetBranch =
					_layoutSetBranchLocalService.getLayoutSetBranch(
						layoutSetBranchId);

				LayoutBranch layoutBranch = layoutRevision.getLayoutBranch();

				if (!layoutBranch.isMaster()) {
					jsonObject.put(
						"layoutBranchId", layoutBranch.getLayoutBranchId()
					).put(
						"layoutBranchName", layoutBranch.getName()
					);
				}

				if (layoutRevision.isHead()) {
					jsonObject.put("layoutRevisionHead", true);
				}

				jsonObject.put(
					"layoutRevisionId", layoutRevision.getLayoutRevisionId()
				).put(
					"layoutSetBranchId", layoutSetBranchId
				).put(
					"layoutSetBranchName", boundLayoutSetBranch.getName()
				);
			}

			if (Objects.equals(
					layout.getType(), LayoutConstants.TYPE_COLLECTION)) {

				jsonObject.put(
					"collectionPK",
					layout.getTypeSettingsProperty("collectionPK")
				).put(
					"collectionType",
					layout.getTypeSettingsProperty("collectionType")
				);
			}

			jsonArray.put(jsonObject);
		}

		return jsonArray;
	}

	private JSONSerializable _toJSONSerializable(
			HttpServletRequest httpServletRequest, long groupId,
			boolean includeActions, LayoutTreeNodes layoutTreeNodes,
			LayoutSetBranch layoutSetBranch)
		throws Exception {

		if (_log.isDebugEnabled()) {
			_log.debug(
				StringBundler.concat(
					"Group ", groupId, " and layout tree nodes ",
					layoutTreeNodes));
		}

		return _toJSONArray(
			httpServletRequest, groupId, includeActions, layoutTreeNodes,
			layoutSetBranch);
	}

	private JSONSerializable _toJSONSerializable(
			HttpServletRequest httpServletRequest, long groupId,
			boolean includeActions, List<Layout> layouts, int total,
			LayoutSetBranch layoutSetBranch)
		throws Exception {

		List<LayoutTreeNode> layoutTreeNodesList = new ArrayList<>();

		for (Layout layout : layouts) {
			LayoutTreeNode layoutTreeNode = new LayoutTreeNode(layout);

			layoutTreeNodesList.add(layoutTreeNode);
		}

		LayoutTreeNodes layoutTreeNodes = new LayoutTreeNodes(
			layoutTreeNodesList, total);

		return _toJSONSerializable(
			httpServletRequest, groupId, includeActions, layoutTreeNodes,
			layoutSetBranch);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		LayoutsTreeImpl.class);

	@Reference
	private BrowserSniffer _browserSniffer;

	@Reference
	private GroupLocalService _groupLocalService;

	@Reference
	private GroupPermission _groupPermission;

	@Reference
	private JSONFactory _jsonFactory;

	@Reference
	private Language _language;

	@Reference
	private LayoutContentModelResourcePermission
		_layoutContentModelResourcePermission;

	@Reference
	private LayoutLocalService _layoutLocalService;

	@Reference
	private LayoutPermission _layoutPermission;

	@Reference
	private LayoutRevisionLocalService _layoutRevisionLocalService;

	@Reference
	private LayoutService _layoutService;

	@Reference
	private LayoutSetBranchLocalService _layoutSetBranchLocalService;

	@Reference
	private Portal _portal;

	@Reference
	private SiteNavigationMenuLocalService _siteNavigationMenuLocalService;

	@Reference
	private Sites _sites;

	@Reference
	private Staging _staging;

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
			return StringBundler.concat(
				"{childLayoutTreeNodes=", _childLayoutTreeNodes, ", layout=",
				_layout, StringPool.CLOSE_CURLY_BRACE);
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
			return StringBundler.concat(
				"{layoutTreeNodesList=", _layoutTreeNodesList, ", total=",
				_total, StringPool.CLOSE_CURLY_BRACE);
		}

		private final List<LayoutTreeNode> _layoutTreeNodesList;
		private int _total;

	}

}