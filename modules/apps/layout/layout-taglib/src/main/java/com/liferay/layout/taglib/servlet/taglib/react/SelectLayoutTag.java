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

package com.liferay.layout.taglib.servlet.taglib.react;

import com.liferay.exportimport.kernel.staging.StagingUtil;
import com.liferay.item.selector.criteria.UUIDItemSelectorReturnType;
import com.liferay.layout.item.selector.LayoutItemSelectorReturnType;
import com.liferay.layout.taglib.internal.servlet.ServletContextUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.LayoutConstants;
import com.liferay.portal.kernel.service.LayoutLocalServiceUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.taglib.util.IncludeTag;

import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.PageContext;

/**
 * @author Eudaldo Alonso
 * @author Marko Cikos
 */
public class SelectLayoutTag extends IncludeTag {

	public String getComponentId() {
		return _componentId;
	}

	public boolean getFollowURLOnTitleClick() {
		return _followURLOnTitleClick;
	}

	public String getItemSelectorReturnType() {
		return _itemSelectorReturnType;
	}

	public String getItemSelectorSaveEvent() {
		return _itemSelectorSaveEvent;
	}

	public String getNamespace() {
		return _namespace;
	}

	/**
	 * @deprecated As of Cavanaugh (7.4.x), with no direct replacement
	 */
	@Deprecated
	public String getPathThemeImages() {
		return _pathThemeImages;
	}

	/**
	 * @deprecated As of Cavanaugh (7.4.x), with no direct replacement
	 */
	@Deprecated
	public String getViewType() {
		return _viewType;
	}

	public boolean isCheckDisplayPage() {
		return _checkDisplayPage;
	}

	public boolean isEnableCurrentPage() {
		return _enableCurrentPage;
	}

	public boolean isMultiSelection() {
		return _multiSelection;
	}

	public boolean isPrivateLayout() {
		return _privateLayout;
	}

	public boolean isShowHiddenLayouts() {
		return _showHiddenLayouts;
	}

	public void setCheckDisplayPage(boolean checkDisplayPage) {
		_checkDisplayPage = checkDisplayPage;
	}

	public void setComponentId(String componentId) {
		_componentId = componentId;
	}

	public void setEnableCurrentPage(boolean enableCurrentPage) {
		_enableCurrentPage = enableCurrentPage;
	}

	public void setFollowURLOnTitleClick(boolean followURLOnTitleClick) {
		_followURLOnTitleClick = followURLOnTitleClick;
	}

	public void setItemSelectorReturnType(String itemSelectorReturnType) {
		_itemSelectorReturnType = itemSelectorReturnType;
	}

	public void setItemSelectorSaveEvent(String itemSelectorSaveEvent) {
		_itemSelectorSaveEvent = itemSelectorSaveEvent;
	}

	public void setMultiSelection(boolean multiSelection) {
		_multiSelection = multiSelection;
	}

	public void setNamespace(String namespace) {
		_namespace = namespace;
	}

	@Override
	public void setPageContext(PageContext pageContext) {
		super.setPageContext(pageContext);

		setServletContext(ServletContextUtil.getServletContext());
	}

	/**
	 * @deprecated As of Cavanaugh (7.4.x), with no direct replacement
	 */
	@Deprecated
	public void setPathThemeImages(String pathThemeImages) {
		_pathThemeImages = pathThemeImages;
	}

	public void setPrivateLayout(boolean privateLayout) {
		_privateLayout = privateLayout;
	}

	public void setShowHiddenLayouts(boolean showHiddenLayouts) {
		_showHiddenLayouts = showHiddenLayouts;
	}

	/**
	 * @deprecated As of Cavanaugh (7.4.x), with no direct replacement
	 */
	@Deprecated
	public void setViewType(String viewType) {
		_viewType = viewType;
	}

	@Override
	protected void cleanUp() {
		super.cleanUp();

		_checkDisplayPage = false;
		_componentId = null;
		_enableCurrentPage = false;
		_followURLOnTitleClick = false;
		_itemSelectorReturnType = null;
		_itemSelectorSaveEvent = null;
		_multiSelection = false;
		_namespace = null;
		_pathThemeImages = null;
		_privateLayout = false;
		_showHiddenLayouts = false;
		_viewType = null;
	}

	@Override
	protected String getPage() {
		return _PAGE;
	}

	@Override
	protected void setAttributes(HttpServletRequest httpServletRequest) {
		try {
			httpServletRequest.setAttribute(
				"liferay-layout:select-layout:data", _getData());
		}
		catch (Exception exception) {
			_log.error(exception, exception);
		}
	}

	private Map<String, Object> _getData() throws Exception {
		String[] selectedLayoutIds = ParamUtil.getStringValues(
			getRequest(), "layoutUuid");

		return HashMapBuilder.<String, Object>put(
			"followURLOnTitleClick", _followURLOnTitleClick
		).put(
			"itemSelectorSaveEvent", _itemSelectorSaveEvent
		).put(
			"multiSelection", _multiSelection
		).put(
			"namespace", _namespace
		).put(
			"nodes", _getLayoutsJSONArray(selectedLayoutIds)
		).put(
			"selectedLayoutIds", selectedLayoutIds
		).build();
	}

	private String _getLayoutBreadcrumb(Layout layout) throws Exception {
		HttpServletRequest httpServletRequest = getRequest();

		ThemeDisplay themeDisplay =
			(ThemeDisplay)httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		Locale locale = themeDisplay.getLocale();

		List<Layout> ancestors = layout.getAncestors();

		StringBundler sb = new StringBundler((4 * ancestors.size()) + 5);

		if (layout.isPrivateLayout()) {
			sb.append(LanguageUtil.get(httpServletRequest, "private-pages"));
		}
		else {
			sb.append(LanguageUtil.get(httpServletRequest, "public-pages"));
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
			String[] selectedLayoutUuid)
		throws Exception {

		HttpServletRequest httpServletRequest = getRequest();

		ThemeDisplay themeDisplay =
			(ThemeDisplay)httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		JSONArray jsonArray = JSONFactoryUtil.createJSONArray();

		List<Layout> layouts = LayoutLocalServiceUtil.getLayouts(
			groupId, privateLayout, parentLayoutId);

		for (Layout layout : layouts) {
			if ((layout.isHidden() && !_showHiddenLayouts) ||
				_isContentLayoutDraft(layout) ||
				StagingUtil.isIncomplete(layout)) {

				continue;
			}

			JSONObject jsonObject = JSONFactoryUtil.createJSONObject();

			JSONArray childrenJSONArray = _getLayoutsJSONArray(
				groupId, privateLayout, layout.getLayoutId(),
				selectedLayoutUuid);

			if (childrenJSONArray.length() > 0) {
				jsonObject.put("children", childrenJSONArray);
			}

			if ((_checkDisplayPage && !layout.isContentDisplayPage()) ||
				(_enableCurrentPage && (layout.getPlid() == _getSelPlid()))) {

				jsonObject.put("disabled", true);
			}

			jsonObject.put(
				"groupId", layout.getGroupId()
			).put(
				"icon", "page"
			).put(
				"id", layout.getUuid()
			).put(
				"layoutId", layout.getLayoutId()
			).put(
				"name", layout.getName(themeDisplay.getLocale())
			).put(
				"payload", _getPayload(layout, themeDisplay)
			).put(
				"privateLayout", layout.isPrivateLayout()
			).put(
				"returnType", getItemSelectorReturnType()
			).put(
				"url",
				PortalUtil.getLayoutRelativeURL(layout, themeDisplay, false)
			);

			if (ArrayUtil.contains(selectedLayoutUuid, layout.getUuid())) {
				jsonObject.put("selected", true);
			}

			jsonObject.put("value", _getLayoutBreadcrumb(layout));

			jsonArray.put(jsonObject);
		}

		return jsonArray;
	}

	private JSONArray _getLayoutsJSONArray(String[] selectedLayoutIds)
		throws Exception {

		HttpServletRequest httpServletRequest = getRequest();

		ThemeDisplay themeDisplay =
			(ThemeDisplay)httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		Group group = themeDisplay.getScopeGroup();

		if ((_privateLayout && !group.hasPrivateLayouts()) ||
			(!_privateLayout && !group.hasPublicLayouts())) {

			return JSONFactoryUtil.createJSONArray();
		}

		return JSONUtil.put(
			JSONUtil.put(
				"children",
				_getLayoutsJSONArray(
					themeDisplay.getScopeGroupId(), _privateLayout, 0,
					selectedLayoutIds)
			).put(
				"disabled", true
			).put(
				"expanded", true
			).put(
				"icon", "home"
			).put(
				"id", "0"
			).put(
				"name", themeDisplay.getScopeGroupName()
			));
	}

	private String _getPayload(Layout layout, ThemeDisplay themeDisplay)
		throws Exception {

		if (Objects.equals(
				LayoutItemSelectorReturnType.class.getName(),
				getItemSelectorReturnType())) {

			return JSONUtil.put(
				"layoutId", layout.getLayoutId()
			).put(
				"name", layout.getName(themeDisplay.getLocale())
			).put(
				"plid", layout.getPlid()
			).put(
				"url", PortalUtil.getLayoutFullURL(layout, themeDisplay)
			).put(
				"uuid", layout.getUuid()
			).toString();
		}
		else if (Objects.equals(
					UUIDItemSelectorReturnType.class.getName(),
					getItemSelectorReturnType())) {

			return layout.getUuid();
		}

		return PortalUtil.getLayoutRelativeURL(layout, themeDisplay, false);
	}

	private long _getSelPlid() {
		return ParamUtil.getLong(
			getRequest(), "selPlid", LayoutConstants.DEFAULT_PLID);
	}

	private boolean _isContentLayoutDraft(Layout layout) {
		if (!layout.isTypeContent()) {
			return false;
		}

		Layout draftLayout = layout.fetchDraftLayout();

		if (draftLayout != null) {
			boolean published = GetterUtil.getBoolean(
				draftLayout.getTypeSettingsProperty("published"));

			return !published;
		}

		if (layout.isApproved() && !layout.isHidden() && !layout.isSystem()) {
			return false;
		}

		return true;
	}

	private static final String _PAGE = "/select_layout/page.jsp";

	private static final Log _log = LogFactoryUtil.getLog(
		SelectLayoutTag.class);

	private boolean _checkDisplayPage;
	private String _componentId;
	private boolean _enableCurrentPage;
	private boolean _followURLOnTitleClick;
	private String _itemSelectorReturnType;
	private String _itemSelectorSaveEvent;
	private boolean _multiSelection;
	private String _namespace;
	private String _pathThemeImages;
	private boolean _privateLayout;
	private boolean _showHiddenLayouts;
	private String _viewType;

}