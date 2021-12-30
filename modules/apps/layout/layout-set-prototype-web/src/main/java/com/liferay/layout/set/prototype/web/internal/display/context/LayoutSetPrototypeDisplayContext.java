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

package com.liferay.layout.set.prototype.web.internal.display.context;

import com.liferay.frontend.taglib.clay.servlet.taglib.util.CreationMenu;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.CreationMenuBuilder;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItem;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItemListBuilder;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.ViewTypeItem;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.ViewTypeItemList;
import com.liferay.layout.set.prototype.constants.LayoutSetPrototypePortletKeys;
import com.liferay.petra.portlet.url.builder.PortletURLBuilder;
import com.liferay.portal.kernel.dao.search.EmptyOnClickRowChecker;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.model.LayoutSetPrototype;
import com.liferay.portal.kernel.portlet.PortalPreferences;
import com.liferay.portal.kernel.portlet.PortletPreferencesFactoryUtil;
import com.liferay.portal.kernel.portlet.SearchOrderByUtil;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.service.LayoutSetPrototypeLocalServiceUtil;
import com.liferay.portal.kernel.service.permission.PortalPermissionUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.kernel.util.comparator.LayoutSetPrototypeCreateDateComparator;

import java.util.List;
import java.util.Objects;

import javax.portlet.PortletURL;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Eudaldo Alonso
 */
public class LayoutSetPrototypeDisplayContext {

	public LayoutSetPrototypeDisplayContext(
		HttpServletRequest httpServletRequest, RenderRequest renderRequest,
		RenderResponse renderResponse) {

		_httpServletRequest = httpServletRequest;
		_renderRequest = renderRequest;
		_renderResponse = renderResponse;
	}

	public List<DropdownItem> getActionDropdownItems() {
		return DropdownItemListBuilder.add(
			dropdownItem -> {
				dropdownItem.putData("action", "deleteLayoutSetPrototypes");
				dropdownItem.setIcon("times-circle");
				dropdownItem.setLabel(
					LanguageUtil.get(_httpServletRequest, "delete"));
				dropdownItem.setQuickAction(true);
			}
		).build();
	}

	public Boolean getActive() {
		String status = ParamUtil.get(_httpServletRequest, "status", "all");

		if (status.equals("active")) {
			return true;
		}
		else if (status.equals("inactive")) {
			return false;
		}

		return null;
	}

	public String getClearResultsURL() {
		return PortletURLBuilder.create(
			getPortletURL()
		).setParameter(
			"orderByCol", getOrderByCol()
		).setParameter(
			"orderByType", getOrderByType()
		).buildString();
	}

	public CreationMenu getCreationMenu() throws PortalException {
		return CreationMenuBuilder.addPrimaryDropdownItem(
			dropdownItem -> {
				dropdownItem.setHref(
					PortletURLBuilder.createRenderURL(
						_renderResponse
					).setMVCPath(
						"/edit_layout_set_prototype.jsp"
					).setRedirect(
						PortalUtil.getCurrentURL(_httpServletRequest)
					).buildString());
				dropdownItem.setLabel(
					LanguageUtil.get(_httpServletRequest, "add"));
			}
		).build();
	}

	public String getDisplayStyle() {
		if (Validator.isNotNull(_displayStyle)) {
			return _displayStyle;
		}

		PortalPreferences portalPreferences =
			PortletPreferencesFactoryUtil.getPortalPreferences(
				_httpServletRequest);

		_displayStyle = portalPreferences.getValue(
			LayoutSetPrototypePortletKeys.LAYOUT_SET_PROTOTYPE, "display-style",
			"list");

		return _displayStyle;
	}

	public List<DropdownItem> getFilterDropdownItems() {
		return DropdownItemListBuilder.addGroup(
			dropdownGroupItem -> {
				dropdownGroupItem.setDropdownItems(
					_getFilterNavigationDropdownItems());
				dropdownGroupItem.setLabel(
					LanguageUtil.get(_httpServletRequest, "filter-by-status"));
			}
		).addGroup(
			dropdownGroupItem -> {
				dropdownGroupItem.setDropdownItems(_getOrderByDropdownItems());
				dropdownGroupItem.setLabel(
					LanguageUtil.get(_httpServletRequest, "order-by"));
			}
		).build();
	}

	public String getOrderByCol() {
		if (Validator.isNotNull(_orderByCol)) {
			return _orderByCol;
		}

		_orderByCol = SearchOrderByUtil.getOrderByCol(
			_httpServletRequest,
			LayoutSetPrototypePortletKeys.LAYOUT_SET_PROTOTYPE, "create-date");

		return _orderByCol;
	}

	public String getOrderByType() {
		if (Validator.isNotNull(_orderByType)) {
			return _orderByType;
		}

		_orderByType = SearchOrderByUtil.getOrderByType(
			_httpServletRequest,
			LayoutSetPrototypePortletKeys.LAYOUT_SET_PROTOTYPE, "asc");

		return _orderByType;
	}

	public PortletURL getPortletURL() {
		return _renderResponse.createRenderURL();
	}

	public String getSearchActionURL() {
		return PortletURLBuilder.create(
			getPortletURL()
		).setParameter(
			"orderByCol", getOrderByCol()
		).setParameter(
			"orderByType", getOrderByType()
		).buildString();
	}

	public SearchContainer<LayoutSetPrototype> getSearchContainer() {
		ThemeDisplay themeDisplay =
			(ThemeDisplay)_httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		SearchContainer<LayoutSetPrototype> searchContainer =
			new SearchContainer(
				_renderRequest, _renderResponse.createRenderURL(), null,
				"there-are-no-site-templates");

		searchContainer.setId("layoutSetPrototype");
		searchContainer.setOrderByCol(getOrderByCol());

		boolean orderByAsc = false;

		if (Objects.equals(getOrderByType(), "asc")) {
			orderByAsc = true;
		}

		searchContainer.setOrderByComparator(
			new LayoutSetPrototypeCreateDateComparator(orderByAsc));
		searchContainer.setOrderByType(getOrderByType());
		searchContainer.setResults(
			LayoutSetPrototypeLocalServiceUtil.search(
				themeDisplay.getCompanyId(), getActive(),
				searchContainer.getStart(), searchContainer.getEnd(),
				searchContainer.getOrderByComparator()));
		searchContainer.setRowChecker(
			new EmptyOnClickRowChecker(_renderResponse));
		searchContainer.setTotal(_getTotal());

		return searchContainer;
	}

	public String getSortingURL() {
		return PortletURLBuilder.create(
			getPortletURL()
		).setKeywords(
			_getKeywords()
		).setParameter(
			"orderByCol", getOrderByCol()
		).setParameter(
			"orderByType",
			Objects.equals(getOrderByType(), "asc") ? "desc" : "asc"
		).buildString();
	}

	public int getTotalItems() throws PortalException {
		SearchContainer<LayoutSetPrototype> searchContainer =
			getSearchContainer();

		return searchContainer.getTotal();
	}

	public List<ViewTypeItem> getViewTypeItems() {
		PortletURL portletURL = PortletURLBuilder.createActionURL(
			_renderResponse
		).setActionName(
			"changeDisplayStyle"
		).setRedirect(
			PortalUtil.getCurrentURL(_httpServletRequest)
		).buildPortletURL();

		return new ViewTypeItemList(portletURL, getDisplayStyle()) {
			{
				addCardViewTypeItem();
				addListViewTypeItem();
				addTableViewTypeItem();
			}
		};
	}

	public boolean isDescriptiveView() {
		if (Objects.equals(getDisplayStyle(), "descriptive")) {
			return true;
		}

		return false;
	}

	public boolean isDisabledManagementBar() {
		if ((_getTotal() > 0) || !Objects.equals(_getNavigation(), "all")) {
			return false;
		}

		return true;
	}

	public boolean isIconView() {
		if (Objects.equals(getDisplayStyle(), "icon")) {
			return true;
		}

		return false;
	}

	public boolean isListView() {
		if (Objects.equals(getDisplayStyle(), "list")) {
			return true;
		}

		return false;
	}

	public boolean isShowAddButton() {
		ThemeDisplay themeDisplay =
			(ThemeDisplay)_httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		if (PortalPermissionUtil.contains(
				themeDisplay.getPermissionChecker(),
				ActionKeys.ADD_LAYOUT_SET_PROTOTYPE)) {

			return true;
		}

		return false;
	}

	private List<DropdownItem> _getFilterNavigationDropdownItems() {
		String status = ParamUtil.getString(
			_httpServletRequest, "status", "all");

		return DropdownItemListBuilder.add(
			dropdownItem -> {
				dropdownItem.setActive(status.equals("all"));
				dropdownItem.setHref(getPortletURL(), "status", "all");
				dropdownItem.setLabel(
					LanguageUtil.get(_httpServletRequest, "all"));
			}
		).add(
			dropdownItem -> {
				dropdownItem.setActive(status.equals("active"));
				dropdownItem.setHref(getPortletURL(), "status", "active");
				dropdownItem.setLabel(
					LanguageUtil.get(_httpServletRequest, "active"));
			}
		).add(
			dropdownItem -> {
				dropdownItem.setActive(status.equals("inactive"));
				dropdownItem.setHref(getPortletURL(), "status", "inactive");
				dropdownItem.setLabel(
					LanguageUtil.get(_httpServletRequest, "inactive"));
			}
		).build();
	}

	private String _getKeywords() {
		if (_keywords == null) {
			_keywords = ParamUtil.getString(_httpServletRequest, "keywords");
		}

		return _keywords;
	}

	private String _getNavigation() {
		if (Validator.isNotNull(_navigation)) {
			return _navigation;
		}

		_navigation = ParamUtil.getString(_httpServletRequest, "navigation");

		return _navigation;
	}

	private List<DropdownItem> _getOrderByDropdownItems() {
		return DropdownItemListBuilder.add(
			dropdownItem -> {
				dropdownItem.setActive(true);
				dropdownItem.setHref(
					getPortletURL(), "orderByCol", "createDate");
				dropdownItem.setLabel(
					LanguageUtil.get(_httpServletRequest, "create-date"));
			}
		).build();
	}

	private int _getTotal() {
		ThemeDisplay themeDisplay =
			(ThemeDisplay)_httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		return LayoutSetPrototypeLocalServiceUtil.searchCount(
			themeDisplay.getCompanyId(), getActive());
	}

	private String _displayStyle;
	private final HttpServletRequest _httpServletRequest;
	private String _keywords;
	private String _navigation;
	private String _orderByCol;
	private String _orderByType;
	private final RenderRequest _renderRequest;
	private final RenderResponse _renderResponse;

}