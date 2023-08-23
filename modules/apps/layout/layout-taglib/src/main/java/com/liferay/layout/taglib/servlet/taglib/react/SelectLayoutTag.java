/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.layout.taglib.servlet.taglib.react;

import com.liferay.item.selector.criteria.UUIDItemSelectorReturnType;
import com.liferay.layout.item.selector.LayoutItemSelectorReturnType;
import com.liferay.layout.taglib.internal.servlet.ServletContextUtil;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.LayoutConstants;
import com.liferay.portal.kernel.security.auth.AuthTokenUtil;
import com.liferay.portal.kernel.service.LayoutServiceUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.HttpComponentsUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.util.PropsValues;
import com.liferay.taglib.util.IncludeTag;

import java.util.List;
import java.util.Map;
import java.util.Objects;

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

	private Map<String, Object> _getConfigData() {
		return HashMapBuilder.<String, Object>put(
			"loadMoreItemsURL",
			() -> {
				HttpServletRequest httpServletRequest = getRequest();

				ThemeDisplay themeDisplay =
					(ThemeDisplay)httpServletRequest.getAttribute(
						WebKeys.THEME_DISPLAY);

				return themeDisplay.getPathMain() + "/portal/get_layouts";
			}
		).put(
			"maxPageSize",
			GetterUtil.getInteger(
				PropsValues.LAYOUT_MANAGE_PAGES_INITIAL_CHILDREN)
		).build();
	}

	private Map<String, Object> _getData() throws Exception {
		String[] selectedLayoutIds = ParamUtil.getStringValues(
			getRequest(), "layoutUuid");

		return HashMapBuilder.<String, Object>put(
			"checkDisplayPage", _checkDisplayPage
		).put(
			"config", this::_getConfigData
		).put(
			"groupId",
			() -> {
				HttpServletRequest httpServletRequest = getRequest();

				ThemeDisplay themeDisplay =
					(ThemeDisplay)httpServletRequest.getAttribute(
						WebKeys.THEME_DISPLAY);

				return themeDisplay.getScopeGroupId();
			}
		).put(
			"itemSelectorReturnType", _itemSelectorReturnType
		).put(
			"itemSelectorSaveEvent", _itemSelectorSaveEvent
		).put(
			"multiSelection", _multiSelection
		).put(
			"namespace", _namespace
		).put(
			"nodes", _getLayoutsJSONArray(selectedLayoutIds)
		).put(
			"privateLayout", _privateLayout
		).put(
			"selectedLayoutIds", selectedLayoutIds
		).build();
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

		List<Layout> layouts = LayoutServiceUtil.getLayouts(
			groupId, privateLayout, parentLayoutId, false, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS);

		for (Layout layout : layouts) {
			if (_isExcludedLayout(layout)) {
				continue;
			}

			JSONArray childrenJSONArray = _getLayoutsJSONArray(
				groupId, privateLayout, layout.getLayoutId(),
				selectedLayoutUuid);

			jsonArray.put(
				JSONUtil.put(
					"children",
					() -> {
						if (childrenJSONArray.length() > 0) {
							return childrenJSONArray;
						}

						return null;
					}
				).put(
					"disabled",
					() -> {
						if ((_checkDisplayPage &&
							 !layout.isContentDisplayPage()) ||
							(_enableCurrentPage &&
							 (layout.getPlid() == _getSelPlid()))) {

							return true;
						}

						return null;
					}
				).put(
					"groupId", layout.getGroupId()
				).put(
					"hasChildren", layout.hasChildren()
				).put(
					"icon", layout.getIcon()
				).put(
					"id", layout.getUuid()
				).put(
					"layoutId", layout.getLayoutId()
				).put(
					"name", layout.getName(themeDisplay.getLocale())
				).put(
					"paginated",
					() -> {
						int layoutsCount = LayoutServiceUtil.getLayoutsCount(
							groupId, layout.isPrivateLayout(),
							layout.getLayoutId());

						if (layoutsCount >
								PropsValues.
									LAYOUT_MANAGE_PAGES_INITIAL_CHILDREN) {

							return true;
						}

						return false;
					}
				).put(
					"payload", _getPayload(layout, themeDisplay)
				).put(
					"privateLayout", layout.isPrivateLayout()
				).put(
					"returnType", getItemSelectorReturnType()
				).put(
					"selected",
					() -> {
						if (ArrayUtil.contains(
								selectedLayoutUuid, layout.getUuid())) {

							return true;
						}

						return null;
					}
				).put(
					"url",
					PortalUtil.getLayoutRelativeURL(layout, themeDisplay, false)
				).put(
					"value", layout.getBreadcrumb(themeDisplay.getLocale())
				));
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
				"hasChildren", true
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
				"previewURL",
				() -> {
					String layoutURL = HttpComponentsUtil.addParameter(
						PortalUtil.getLayoutFullURL(layout, themeDisplay),
						"p_l_mode", Constants.PREVIEW);

					return HttpComponentsUtil.addParameter(
						layoutURL, "p_p_auth",
						AuthTokenUtil.getToken(getRequest()));
				}
			).put(
				"private", layout.isPrivateLayout()
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

	private boolean _isExcludedLayout(Layout layout) {
		if (!layout.isTypeContent()) {
			return false;
		}

		if (layout.fetchDraftLayout() != null) {
			return !layout.isPublished();
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
	private boolean _enableCurrentPage;
	private String _itemSelectorReturnType;
	private String _itemSelectorSaveEvent;
	private boolean _multiSelection;
	private String _namespace;
	private boolean _privateLayout;

}