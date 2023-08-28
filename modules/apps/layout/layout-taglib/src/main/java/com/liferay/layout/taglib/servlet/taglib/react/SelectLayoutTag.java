/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.layout.taglib.servlet.taglib.react;

import com.liferay.layout.taglib.internal.servlet.ServletContextUtil;
import com.liferay.layout.taglib.internal.util.LayoutUtil;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.LayoutConstants;
import com.liferay.portal.kernel.service.LayoutServiceUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.util.PropsValues;
import com.liferay.taglib.util.IncludeTag;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.PageContext;

/**
 * @author Eudaldo Alonso
 * @author Marko Cikos
 */
public class SelectLayoutTag extends IncludeTag {

	public String getItemSelectorReturnType() {
		return _itemSelectorReturnType;
	}

	public String getItemSelectorSaveEvent() {
		return _itemSelectorSaveEvent;
	}

	public String getNamespace() {
		return _namespace;
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

	public void setCheckDisplayPage(boolean checkDisplayPage) {
		_checkDisplayPage = checkDisplayPage;
	}

	public void setEnableCurrentPage(boolean enableCurrentPage) {
		_enableCurrentPage = enableCurrentPage;
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

	public void setPrivateLayout(boolean privateLayout) {
		_privateLayout = privateLayout;
	}

	@Override
	protected void cleanUp() {
		super.cleanUp();

		_checkDisplayPage = false;
		_enableCurrentPage = false;
		_itemSelectorReturnType = null;
		_itemSelectorSaveEvent = null;
		_multiSelection = false;
		_namespace = null;
		_privateLayout = false;
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
			_log.error(exception);
		}
	}

	private Map<String, Object> _getData() throws Exception {
		HttpServletRequest httpServletRequest = getRequest();

		ThemeDisplay themeDisplay =
			(ThemeDisplay)httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		String[] selectedLayoutIds = ParamUtil.getStringValues(
			httpServletRequest, "layoutUuid");

		return HashMapBuilder.<String, Object>put(
			"checkDisplayPage", _checkDisplayPage
		).put(
			"config",
			HashMapBuilder.<String, Object>put(
				"loadMoreItemsURL",
				themeDisplay.getPathMain() + "/portal/get_layouts"
			).put(
				"maxPageSize",
				GetterUtil.getInteger(
					PropsValues.LAYOUT_MANAGE_PAGES_INITIAL_CHILDREN)
			).build()
		).put(
			"groupId", themeDisplay.getScopeGroupId()
		).put(
			"itemSelectorReturnType", _itemSelectorReturnType
		).put(
			"itemSelectorSaveEvent", _itemSelectorSaveEvent
		).put(
			"multiSelection", _multiSelection
		).put(
			"namespace", _namespace
		).put(
			"nodes", _getLayoutsJSONArray(selectedLayoutIds, themeDisplay)
		).put(
			"privateLayout", _privateLayout
		).put(
			"selectedLayoutIds", selectedLayoutIds
		).build();
	}

	private JSONArray _getLayoutsJSONArray(
			String[] selectedLayoutIds, ThemeDisplay themeDisplay)
		throws Exception {

		Group group = themeDisplay.getScopeGroup();

		if ((_privateLayout && !group.hasPrivateLayouts()) ||
			(!_privateLayout && !group.hasPublicLayouts())) {

			return JSONFactoryUtil.createJSONArray();
		}

		return JSONUtil.put(
			JSONUtil.put(
				"children",
				LayoutUtil.getLayoutsJSONArray(
					_checkDisplayPage, _enableCurrentPage,
					themeDisplay.getScopeGroupId(), getRequest(),
					_itemSelectorReturnType, _privateLayout, 0,
					selectedLayoutIds, 0,
					GetterUtil.getInteger(
						PropsValues.LAYOUT_MANAGE_PAGES_INITIAL_CHILDREN))
			).put(
				"disabled", true
			).put(
				"expanded", true
			).put(
				"hasChildren", true
			).put(
				"icon", "home"
			).put(
				"id", "0"
			).put(
				"name", themeDisplay.getScopeGroupName()
			).put(
				"paginated",
				() -> {
					int layoutsCount = LayoutServiceUtil.getLayoutsCount(
						themeDisplay.getScopeGroupId(), _privateLayout,
						LayoutConstants.DEFAULT_PARENT_LAYOUT_ID);

					if (layoutsCount >
							PropsValues.LAYOUT_MANAGE_PAGES_INITIAL_CHILDREN) {

						return true;
					}

					return false;
				}
			));
	}

	private static final String _PAGE = "/select_layout/page.jsp";

	private static final Log _log = LogFactoryUtil.getLog(
		SelectLayoutTag.class);

	private boolean _checkDisplayPage;
	private boolean _enableCurrentPage;
	private String _itemSelectorReturnType;
	private String _itemSelectorSaveEvent;
	private boolean _multiSelection;
	private String _namespace;
	private boolean _privateLayout;

}