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

package com.liferay.staging.taglib.internal.display.context;

import com.liferay.layout.util.LayoutsTree;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.LayoutConstants;
import com.liferay.portal.kernel.service.LayoutLocalServiceUtil;
import com.liferay.portal.kernel.service.LayoutServiceUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.util.PropsValues;
import com.liferay.staging.taglib.internal.servlet.ServletContextUtil;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.portlet.RenderResponse;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Eudaldo Alonso
 */
public class LayoutsTreeDisplayContext {

	public LayoutsTreeDisplayContext(
		Group group, long groupId, HttpServletRequest httpServletRequest,
		boolean privateLayout, RenderResponse renderResponse,
		long[] selectedLayoutIds, String treeId) {

		_group = group;
		_groupId = groupId;
		_httpServletRequest = httpServletRequest;
		_privateLayout = privateLayout;
		_renderResponse = renderResponse;
		_selectedLayoutIds = selectedLayoutIds;
		_treeId = treeId;

		_themeDisplay = (ThemeDisplay)_httpServletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);
	}

	public Map<String, Object> getPagesTreeData() throws Exception {
		return HashMapBuilder.<String, Object>put(
			"config",
			HashMapBuilder.<String, Object>put(
				"changeItemSelectionURL",
				_themeDisplay.getPathMain() + "/portal/session_tree_js_click"
			).put(
				"loadMoreItemsURL",
				_themeDisplay.getPathMain() + "/portal/get_layouts_tree"
			).put(
				"maxPageSize",
				GetterUtil.getInteger(
					PropsValues.LAYOUT_MANAGE_PAGES_INITIAL_CHILDREN)
			).put(
				"namespace", _renderResponse.getNamespace()
			).build()
		).put(
			"groupId", _groupId
		).put(
			"items", _getLayoutsJSONArray()
		).put(
			"privateLayout", _privateLayout
		).put(
			"selectedPlids", _getSelectedPlids()
		).put(
			"treeId", _treeId
		).build();
	}

	private JSONArray _getLayoutsJSONArray() throws Exception {
		JSONArray layoutsJSONArray = null;

		_httpServletRequest.setAttribute(
			"RETURN_LAYOUTS_AS_ARRAY", Boolean.TRUE);

		LayoutsTree layoutsTree = ServletContextUtil.getLayoutsTree();

		String layoutsJSON = layoutsTree.getLayoutsJSON(
			_httpServletRequest, _groupId, _privateLayout,
			LayoutConstants.DEFAULT_PARENT_LAYOUT_ID, _selectedLayoutIds, true,
			_treeId, null);

		if (layoutsJSON.startsWith(StringPool.OPEN_BRACKET)) {
			layoutsJSONArray = JSONFactoryUtil.createJSONArray(layoutsJSON);
		}
		else {
			layoutsJSONArray = JSONFactoryUtil.createJSONArray();
		}

		return JSONUtil.putAll(
			JSONUtil.put(
				"children", layoutsJSONArray
			).put(
				"hasChildren", true
			).put(
				"id", LayoutConstants.DEFAULT_PARENT_LAYOUT_ID
			).put(
				"name",
				_group.getLayoutRootNodeName(
					_privateLayout, _themeDisplay.getLocale())
			).put(
				"paginated",
				() -> {
					int layoutsCount = LayoutServiceUtil.getLayoutsCount(
						_groupId, _privateLayout,
						LayoutConstants.DEFAULT_PARENT_LAYOUT_ID);

					if (layoutsCount >
							PropsValues.LAYOUT_MANAGE_PAGES_INITIAL_CHILDREN) {

						return true;
					}

					return false;
				}
			));
	}

	private Set<Long> _getSelectedPlids() {
		Set<Long> plids = new HashSet<>();

		for (long layoutId : _selectedLayoutIds) {
			if (layoutId == 0) {
				plids.add(layoutId);

				continue;
			}

			Layout layout = LayoutLocalServiceUtil.fetchLayout(
				_groupId, false, layoutId);

			if (layout != null) {
				plids.add(layout.getPlid());
			}
		}

		return plids;
	}

	private final Group _group;
	private final long _groupId;
	private final HttpServletRequest _httpServletRequest;
	private final boolean _privateLayout;
	private final RenderResponse _renderResponse;
	private final long[] _selectedLayoutIds;
	private final ThemeDisplay _themeDisplay;
	private final String _treeId;

}