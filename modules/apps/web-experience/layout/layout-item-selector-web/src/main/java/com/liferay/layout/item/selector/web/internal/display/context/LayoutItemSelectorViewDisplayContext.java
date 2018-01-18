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

package com.liferay.layout.item.selector.web.internal.display.context;

import com.liferay.exportimport.kernel.staging.StagingUtil;
import com.liferay.layout.item.selector.criterion.LayoutItemSelectorCriterion;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.LayoutConstants;
import com.liferay.portal.kernel.service.LayoutLocalServiceUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.ResourceBundleLoader;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.WebKeys;

import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Roberto Díaz
 */
public class LayoutItemSelectorViewDisplayContext {

	public LayoutItemSelectorViewDisplayContext(
		HttpServletRequest request,
		LayoutItemSelectorCriterion layoutItemSelectorCriterion,
		String itemSelectedEventName, ResourceBundleLoader resourceBundleLoader,
		boolean privateLayout) {

		_request = request;
		_layoutItemSelectorCriterion = layoutItemSelectorCriterion;
		_itemSelectedEventName = itemSelectedEventName;
		_privateLayout = privateLayout;
	}

	public String getItemSelectedEventName() {
		return _itemSelectedEventName;
	}

	public JSONArray getLayoutsJSONArray() throws Exception {
		JSONArray layoutsJSONArray = JSONFactoryUtil.createJSONArray();

		ThemeDisplay themeDisplay = (ThemeDisplay)_request.getAttribute(
			WebKeys.THEME_DISPLAY);

		String layoutUuid = ParamUtil.getString(_request, "layoutUuid");

		JSONArray jsonArray = _getLayoutsJSONArray(
			themeDisplay.getScopeGroupId(), _privateLayout, 0, layoutUuid);

		JSONObject jsonObject = JSONFactoryUtil.createJSONObject();

		jsonObject.put("children", jsonArray);
		jsonObject.put("disabled", true);
		jsonObject.put("expanded", true);
		jsonObject.put("icon", "home");
		jsonObject.put("id", "0");
		jsonObject.put("name", themeDisplay.getScopeGroupName());

		layoutsJSONArray.put(jsonObject);

		return layoutsJSONArray;
	}

	public boolean isFollowURLOnTitleClick() {
		return _layoutItemSelectorCriterion.isFollowURLOnTitleClick();
	}

	private String _getLayoutBreadcrumb(Layout layout) throws Exception {
		ThemeDisplay themeDisplay = (ThemeDisplay)_request.getAttribute(
			WebKeys.THEME_DISPLAY);

		Locale locale = themeDisplay.getLocale();

		List<Layout> ancestors = layout.getAncestors();

		StringBundler sb = new StringBundler(4 * ancestors.size() + 5);

		if (layout.isPrivateLayout()) {
			sb.append(LanguageUtil.get(_request, "private-pages"));
		}
		else {
			sb.append(LanguageUtil.get(_request, "public-pages"));
		}

		sb.append(StringPool.SPACE);
		sb.append(StringPool.GREATER_THAN);
		sb.append(StringPool.SPACE);

		Collections.reverse(ancestors);

		for (Layout ancestor : ancestors) {
			sb.append(HtmlUtil.escape(ancestor.getName(locale)));
			sb.append(StringPool.SPACE);
			sb.append(StringPool.GREATER_THAN);
			sb.append(StringPool.SPACE);
		}

		sb.append(HtmlUtil.escape(layout.getName(locale)));

		return sb.toString();
	}

	private JSONArray _getLayoutsJSONArray(
			long groupId, boolean privateLayout, long parentLayoutId,
			String selectedLayoutUuid)
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)_request.getAttribute(
			WebKeys.THEME_DISPLAY);

		JSONArray jsonArray = JSONFactoryUtil.createJSONArray();

		List<Layout> layouts = LayoutLocalServiceUtil.getLayouts(
			groupId, privateLayout, parentLayoutId);

		for (Layout layout : layouts) {
			if (StagingUtil.isIncomplete(layout)) {
				continue;
			}

			JSONObject jsonObject = JSONFactoryUtil.createJSONObject();

			JSONArray childrenJSONArray = _getLayoutsJSONArray(
				groupId, privateLayout, layout.getLayoutId(),
				selectedLayoutUuid);

			if (childrenJSONArray.length() > 0) {
				jsonObject.put("children", childrenJSONArray);
			}

			if ((_layoutItemSelectorCriterion.isCheckDisplayPage() &&
				 !layout.isContentDisplayPage()) ||
				(!_layoutItemSelectorCriterion.isEnableCurrentPage() &&
				 (layout.getPlid() == _getSelPlid()))) {

				jsonObject.put("disabled", true);
			}

			jsonObject.put("groupId", layout.getGroupId());
			jsonObject.put("icon", "page");
			jsonObject.put("id", layout.getUuid());
			jsonObject.put("layoutId", layout.getLayoutId());
			jsonObject.put("name", layout.getName(themeDisplay.getLocale()));
			jsonObject.put("privateLayout", layout.isPrivateLayout());
			jsonObject.put(
				"url", PortalUtil.getLayoutURL(layout, themeDisplay));

			if (Objects.equals(layout.getUuid(), selectedLayoutUuid)) {
				jsonObject.put("selected", true);
			}

			jsonObject.put("value", _getLayoutBreadcrumb(layout));

			jsonArray.put(jsonObject);
		}

		return jsonArray;
	}

	private long _getSelPlid() {
		return ParamUtil.getLong(
			_request, "selPlid", LayoutConstants.DEFAULT_PLID);
	}

	private final String _itemSelectedEventName;
	private final LayoutItemSelectorCriterion _layoutItemSelectorCriterion;
	private final boolean _privateLayout;
	private final HttpServletRequest _request;

}