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

package com.liferay.layout.taglib.servlet.taglib.soy;

import com.liferay.exportimport.kernel.staging.StagingUtil;
import com.liferay.frontend.js.loader.modules.extender.npm.NPMResolver;
import com.liferay.frontend.taglib.soy.servlet.taglib.ComponentRendererTag;
import com.liferay.frontend.taglib.util.TagAccessor;
import com.liferay.frontend.taglib.util.TagResourceHandler;
import com.liferay.layout.taglib.internal.frontend.js.loader.modules.extender.npm.NPMResolverProvider;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.LayoutConstants;
import com.liferay.portal.kernel.service.LayoutLocalServiceUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.WebKeys;

import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.PageContext;

/**
 * @author Eudaldo Alonso
 */
public class SelectLayoutTag extends ComponentRendererTag {

	@Override
	public int doStartTag() {
		Map<String, Object> context = getContext();

		if (context.get("followURLOnTitleClick") == null) {
			putValue("followURLOnTitleClick", false);
		}

		if (context.get("itemSelectorSaveEvent") == null) {
			putValue(
				"itemSelectorSaveEvent",
				context.get("namespace") + "selectLayout");
		}

		try {
			putValue("nodes", _getLayoutsJSONArray());
		}
		catch (Exception e) {
			return SKIP_BODY;
		}

		if (context.get("multiSelection") == null) {
			putValue("multiSelection", false);
		}

		if (context.get("viewType") == null) {
			putValue("viewType", "tree");
		}

		setTemplateNamespace("com.liferay.layout.taglib.SelectLayout.render");

		_tagResourceHandler.outputBundleStyleSheet(
			"select_layout/css/main.css");

		return super.doStartTag();
	}

	@Override
	public String getModule() {
		NPMResolver npmResolver = NPMResolverProvider.getNPMResolver();

		if (npmResolver == null) {
			return StringPool.BLANK;
		}

		return npmResolver.resolveModuleName(
			"layout-taglib/select_layout/js/SelectLayout.es");
	}

	public void setCheckDisplayPage(boolean checkDisplayPage) {
		putValue("checkDisplayPage", checkDisplayPage);
	}

	public void setEnableCurrentPage(boolean enableCurrentPage) {
		putValue("enableCurrentPage", enableCurrentPage);
	}

	public void setFollowURLOnTitleClick(boolean followURLOnTitleClick) {
		putValue("followURLOnTitleClick", followURLOnTitleClick);
	}

	public void setItemSelectorSaveEvent(String itemSelectorSaveEvent) {
		putValue("itemSelectorSaveEvent", itemSelectorSaveEvent);
	}

	public void setMultiSelection(boolean multiSelection) {
		putValue("multiSelection", multiSelection);
	}

	public void setNamespace(String namespace) {
		putValue("namespace", namespace);
	}

	public void setPathThemeImages(String pathThemeImages) {
		putValue("pathThemeImages", pathThemeImages);
	}

	public void setPrivateLayout(boolean privateLayout) {
		putValue("privateLayout", privateLayout);
	}

	public void setShowHiddenLayouts(boolean showHiddenLayouts) {
		putValue("showHiddenLayouts", showHiddenLayouts);
	}

	public void setViewType(String viewType) {
		putValue("viewType", viewType);
	}

	private String _getLayoutBreadcrumb(Layout layout) throws Exception {
		ThemeDisplay themeDisplay = (ThemeDisplay)request.getAttribute(
			WebKeys.THEME_DISPLAY);

		Locale locale = themeDisplay.getLocale();

		List<Layout> ancestors = layout.getAncestors();

		StringBundler sb = new StringBundler(4 * ancestors.size() + 5);

		if (layout.isPrivateLayout()) {
			sb.append(LanguageUtil.get(request, "private-pages"));
		}
		else {
			sb.append(LanguageUtil.get(request, "public-pages"));
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

	private JSONArray _getLayoutsJSONArray() throws Exception {
		ThemeDisplay themeDisplay = (ThemeDisplay)request.getAttribute(
			WebKeys.THEME_DISPLAY);

		String layoutUuid = ParamUtil.getString(request, "layoutUuid");

		JSONArray jsonArray = _getLayoutsJSONArray(
			themeDisplay.getScopeGroupId(), _getPrivateLayout(), 0, layoutUuid);

		JSONObject jsonObject = JSONUtil.put(
			"children", jsonArray
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
		);

		return JSONUtil.put(jsonObject);
	}

	private JSONArray _getLayoutsJSONArray(
			long groupId, boolean privateLayout, long parentLayoutId,
			String selectedLayoutUuid)
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)request.getAttribute(
			WebKeys.THEME_DISPLAY);

		JSONArray jsonArray = JSONFactoryUtil.createJSONArray();

		List<Layout> layouts = LayoutLocalServiceUtil.getLayouts(
			groupId, privateLayout, parentLayoutId);

		for (Layout layout : layouts) {
			if ((layout.isHidden() && !_isShowHiddenLayouts()) ||
				(Objects.equals(
					layout.getType(), LayoutConstants.TYPE_CONTENT) &&
				 Objects.equals(
					 layout.getCreateDate(), layout.getPublishDate())) ||
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

			if ((_isCheckDisplayPage() && !layout.isContentDisplayPage()) ||
				(!_isEnableCurrentPage() &&
				 (layout.getPlid() == _getSelPlid()))) {

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
				"privateLayout", layout.isPrivateLayout()
			).put(
				"url", PortalUtil.getLayoutRelativeURL(layout, themeDisplay)
			);

			if (Objects.equals(layout.getUuid(), selectedLayoutUuid)) {
				jsonObject.put("selected", true);
			}

			jsonObject.put("value", _getLayoutBreadcrumb(layout));

			jsonArray.put(jsonObject);
		}

		return jsonArray;
	}

	private PageContext _getPageContext() {
		return pageContext;
	}

	private boolean _getPrivateLayout() {
		Map<String, Object> context = getContext();

		return GetterUtil.getBoolean(context.get("privateLayout"));
	}

	private long _getSelPlid() {
		return ParamUtil.getLong(
			request, "selPlid", LayoutConstants.DEFAULT_PLID);
	}

	private boolean _isCheckDisplayPage() {
		Map<String, Object> context = getContext();

		return GetterUtil.getBoolean(context.get("checkDisplayPage"));
	}

	private boolean _isEnableCurrentPage() {
		Map<String, Object> context = getContext();

		return GetterUtil.getBoolean(context.get("enableCurrentPage"));
	}

	private boolean _isShowHiddenLayouts() {
		Map<String, Object> context = getContext();

		return GetterUtil.getBoolean(context.get("showHiddenLayouts"));
	}

	private final TagResourceHandler _tagResourceHandler =
		new TagResourceHandler(
			SelectLayoutTag.class,
			new TagAccessor() {

				@Override
				public PageContext getPageContext() {
					return SelectLayoutTag.this._getPageContext();
				}

				@Override
				public HttpServletRequest getRequest() {
					return SelectLayoutTag.this.getRequest();
				}

			});

}