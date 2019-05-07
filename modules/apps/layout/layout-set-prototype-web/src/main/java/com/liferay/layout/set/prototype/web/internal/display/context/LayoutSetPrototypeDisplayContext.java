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
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItem;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItemList;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.ViewTypeItem;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.ViewTypeItemList;
import com.liferay.layout.set.prototype.constants.LayoutSetPrototypePortletKeys;
import com.liferay.portal.kernel.dao.search.EmptyOnClickRowChecker;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.model.LayoutSetPrototype;
import com.liferay.portal.kernel.portlet.PortalPreferences;
import com.liferay.portal.kernel.portlet.PortletPreferencesFactoryUtil;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.service.LayoutSetPrototypeLocalServiceUtil;
import com.liferay.portal.kernel.service.permission.PortalPermissionUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.kernel.util.comparator.LayoutSetPrototypeCreateDateComparator;

import java.util.List;
import java.util.Objects;

import javax.portlet.ActionRequest;
import javax.portlet.PortletURL;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Eudaldo Alonso
 */
public class LayoutSetPrototypeDisplayContext {

	public LayoutSetPrototypeDisplayContext(
		RenderRequest renderRequest, RenderResponse renderResponse,
		HttpServletRequest httpServletRequest) {

		_renderRequest = renderRequest;
		_renderResponse = renderResponse;
		_httpServletRequest = httpServletRequest;
	}

	public List<DropdownItem> getActionDropdownItems() {
		return new DropdownItemList() {
			{
				add(
					dropdownItem -> {
						dropdownItem.putData(
							"action", "deleteLayoutSetPrototypes");
						dropdownItem.setIcon("times-circle");
						dropdownItem.setLabel(
							LanguageUtil.get(_httpServletRequest, "delete"));
						dropdownItem.setQuickAction(true);
					});
			}
		};
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
		PortletURL clearResultsURL = getPortletURL();

		clearResultsURL.setParameter("orderByCol", getOrderByCol());
		clearResultsURL.setParameter("orderByType", getOrderByType());

		return clearResultsURL.toString();
	}

	public CreationMenu getCreationMenu() throws PortalException {
		PortletURL addLayoutSetPrototypeRenderURL =
			_renderResponse.createRenderURL();

		addLayoutSetPrototypeRenderURL.setParameter(
			"mvcPath", "/edit_layout_set_prototype.jsp");
		addLayoutSetPrototypeRenderURL.setParameter(
			"redirect", PortalUtil.getCurrentURL(_httpServletRequest));

		return new CreationMenu() {
			{
				addPrimaryDropdownItem(
					dropdownItem -> {
						dropdownItem.setHref(
							addLayoutSetPrototypeRenderURL.toString());
						dropdownItem.setLabel(
							LanguageUtil.get(_httpServletRequest, "add"));
					});
			}
		};
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
		return new DropdownItemList() {
			{
				addGroup(
					dropdownGroupItem -> {
						dropdownGroupItem.setDropdownItems(
							_getFilterNavigationDropdownItems());
						dropdownGroupItem.setLabel(
							LanguageUtil.get(
								_httpServletRequest, "filter-by-status"));
					});
				addGroup(
					dropdownGroupItem -> {
						dropdownGroupItem.setDropdownItems(
							_getOrderByDropdownItems());
						dropdownGroupItem.setLabel(
							LanguageUtil.get(_httpServletRequest, "order-by"));
					});
			}
		};
	}

	public String getOrderByCol() {
		if (Validator.isNotNull(_orderByCol)) {
			return _orderByCol;
		}

		_orderByCol = ParamUtil.getString(
			_httpServletRequest, "orderByCol", "create-date");

		return _orderByCol;
	}

	public String getOrderByType() {
		if (Validator.isNotNull(_orderByType)) {
			return _orderByType;
		}

		_orderByType = ParamUtil.getString(
			_httpServletRequest, "orderByType", "asc");

		return _orderByType;
	}

	public PortletURL getPortletURL() {
		return _renderResponse.createRenderURL();
	}

	public String getSearchActionURL() {
		PortletURL searchURL = getPortletURL();

		searchURL.setParameter("orderByCol", getOrderByCol());
		searchURL.setParameter("orderByType", getOrderByType());

		return searchURL.toString();
	}

	public SearchContainer getSearchContainer() {
		ThemeDisplay themeDisplay =
			(ThemeDisplay)_httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		SearchContainer searchContainer = new SearchContainer(
			_renderRequest, _renderResponse.createRenderURL(), null,
			"there-are-no-site-templates");

		searchContainer.setId("layoutSetPrototype");
		searchContainer.setRowChecker(
			new EmptyOnClickRowChecker(_renderResponse));

		boolean orderByAsc = false;

		if (getOrderByType().equals("asc")) {
			orderByAsc = true;
		}

		OrderByComparator<LayoutSetPrototype> orderByComparator =
			new LayoutSetPrototypeCreateDateComparator(orderByAsc);

		searchContainer.setOrderByCol(getOrderByCol());
		searchContainer.setOrderByComparator(orderByComparator);
		searchContainer.setOrderByType(getOrderByType());

		searchContainer.setTotal(getTotal());

		List results = LayoutSetPrototypeLocalServiceUtil.search(
			themeDisplay.getCompanyId(), getActive(),
			searchContainer.getStart(), searchContainer.getEnd(),
			searchContainer.getOrderByComparator());

		searchContainer.setResults(results);

		return searchContainer;
	}

	public String getSortingURL() {
		PortletURL sortingURL = getPortletURL();

		sortingURL.setParameter("keywords", _getKeywords());
		sortingURL.setParameter("orderByCol", getOrderByCol());
		sortingURL.setParameter(
			"orderByType",
			Objects.equals(getOrderByType(), "asc") ? "desc" : "asc");

		return sortingURL.toString();
	}

	public int getTotalItems() throws PortalException {
		SearchContainer searchContainer = getSearchContainer();

		return searchContainer.getTotal();
	}

	public List<ViewTypeItem> getViewTypeItems() {
		PortletURL portletURL = _renderResponse.createActionURL();

		portletURL.setParameter(
			ActionRequest.ACTION_NAME, "changeDisplayStyle");
		portletURL.setParameter(
			"redirect", PortalUtil.getCurrentURL(_httpServletRequest));

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
		if (getTotal() > 0) {
			return false;
		}

		if (!Objects.equals(getNavigation(), "all")) {
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

	protected String getNavigation() {
		if (Validator.isNotNull(_navigation)) {
			return _navigation;
		}

		_navigation = ParamUtil.getString(_httpServletRequest, "navigation");

		return _navigation;
	}

	protected int getTotal() {
		ThemeDisplay themeDisplay =
			(ThemeDisplay)_httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		return LayoutSetPrototypeLocalServiceUtil.searchCount(
			themeDisplay.getCompanyId(), getActive());
	}

	private List<DropdownItem> _getFilterNavigationDropdownItems() {
		String status = ParamUtil.getString(
			_httpServletRequest, "status", "all");

		return new DropdownItemList() {
			{
				add(
					dropdownItem -> {
						dropdownItem.setActive(status.equals("all"));
						dropdownItem.setHref(getPortletURL(), "status", "all");
						dropdownItem.setLabel(
							LanguageUtil.get(_httpServletRequest, "all"));
					});
				add(
					dropdownItem -> {
						dropdownItem.setActive(status.equals("active"));
						dropdownItem.setHref(
							getPortletURL(), "status", "active");
						dropdownItem.setLabel(
							LanguageUtil.get(_httpServletRequest, "active"));
					});
				add(
					dropdownItem -> {
						dropdownItem.setActive(status.equals("inactive"));
						dropdownItem.setHref(
							getPortletURL(), "status", "inactive");
						dropdownItem.setLabel(
							LanguageUtil.get(_httpServletRequest, "inactive"));
					});
			}
		};
	}

	private String _getKeywords() {
		if (_keywords == null) {
			_keywords = ParamUtil.getString(_httpServletRequest, "keywords");
		}

		return _keywords;
	}

	private List<DropdownItem> _getOrderByDropdownItems() {
		return new DropdownItemList() {
			{
				add(
					dropdownItem -> {
						dropdownItem.setActive(true);
						dropdownItem.setHref(
							getPortletURL(), "orderByCol", "createDate");
						dropdownItem.setLabel(
							LanguageUtil.get(
								_httpServletRequest, "create-date"));
					});
			}
		};
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