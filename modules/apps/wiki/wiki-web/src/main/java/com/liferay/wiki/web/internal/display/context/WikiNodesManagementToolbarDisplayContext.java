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

package com.liferay.wiki.web.internal.display.context;

import com.liferay.frontend.taglib.clay.servlet.taglib.util.CreationMenu;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItem;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItemList;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.SafeConsumer;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.ViewTypeItem;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.ViewTypeItemList;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.portlet.PortletURLUtil;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.trash.TrashHelper;
import com.liferay.wiki.web.internal.security.permission.resource.WikiResourcePermission;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import javax.portlet.PortletException;
import javax.portlet.PortletURL;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Alejandro Tardín
 */
public class WikiNodesManagementToolbarDisplayContext {

	public WikiNodesManagementToolbarDisplayContext(
		LiferayPortletRequest liferayPortletRequest,
		LiferayPortletResponse liferayPortletResponse, String displayStyle,
		SearchContainer searchContainer, TrashHelper trashHelper) {

		_liferayPortletRequest = liferayPortletRequest;
		_liferayPortletResponse = liferayPortletResponse;
		_displayStyle = displayStyle;
		_searchContainer = searchContainer;
		_trashHelper = trashHelper;

		_currentURLObj = PortletURLUtil.getCurrent(
			_liferayPortletRequest, _liferayPortletResponse);

		_request = liferayPortletRequest.getHttpServletRequest();

		_themeDisplay = (ThemeDisplay)_request.getAttribute(
			WebKeys.THEME_DISPLAY);
	}

	public List<DropdownItem> getActionDropdownItems() {
		return new DropdownItemList() {
			{
				add(
					SafeConsumer.ignore(
						dropdownItem -> {
							dropdownItem.setHref(
								StringBundler.concat(
									"javascript:",
									_liferayPortletResponse.getNamespace(),
									"deleteNodes();"));

							if (_trashHelper.isTrashEnabled(
									_themeDisplay.getScopeGroupId())) {

								dropdownItem.setIcon("trash");
								dropdownItem.setLabel(
									LanguageUtil.get(
										_request, "move-to-the-recycle-bin"));
							}
							else {
								dropdownItem.setIcon("times");
								dropdownItem.setLabel(
									LanguageUtil.get(_request, "delete"));
							}

							dropdownItem.setQuickAction(true);
						}));
			}
		};
	}

	public CreationMenu getCreationMenu() {
		if (!WikiResourcePermission.contains(
				_themeDisplay.getPermissionChecker(),
				_themeDisplay.getScopeGroupId(), ActionKeys.ADD_NODE)) {

			return null;
		}

		return new CreationMenu() {
			{
				addDropdownItem(
					dropdownItem -> {
						PortletURL viewNodesURL =
							_liferayPortletResponse.createRenderURL();

						viewNodesURL.setParameter(
							"mvcRenderCommandName", "/wiki_admin/view");

						dropdownItem.setHref(
							_liferayPortletResponse.createRenderURL(),
							"mvcRenderCommandName", "/wiki/edit_node",
							"redirect", viewNodesURL.toString());

						dropdownItem.setLabel(
							LanguageUtil.get(_request, "add-wiki"));
					});
			}
		};
	}

	public List<DropdownItem> getFilterDropdownItems() {
		return new DropdownItemList() {
			{
				addGroup(
					dropdownGroupItem -> {
						dropdownGroupItem.setDropdownItems(
							_getOrderByDropdownItems());
						dropdownGroupItem.setLabel(
							LanguageUtil.get(_request, "order-by"));
					});
			}
		};
	}

	public String getSortingOrder() {
		return _getOrderByType();
	}

	public PortletURL getSortingURL() throws PortletException {
		PortletURL sortingURL = _getPortletURL();

		sortingURL.setParameter("orderByCol", _getOrderByCol());

		sortingURL.setParameter(
			"orderByType",
			Objects.equals(_getOrderByType(), "asc") ? "desc" : "asc");

		return sortingURL;
	}

	public int getTotalItems() {
		return _searchContainer.getTotal();
	}

	public ViewTypeItemList getViewTypes() throws PortletException {
		return new ViewTypeItemList(_getPortletURL(), _displayStyle) {
			{
				ViewTypeItem cardViewTypeItem = addCardViewTypeItem();

				cardViewTypeItem.setDisabled(true);

				addListViewTypeItem();
				addTableViewTypeItem();
			}
		};
	}

	public boolean isDisabled() {
		return !_searchContainer.hasResults();
	}

	public boolean isSelectable() {
		return true;
	}

	public boolean isShowSearch() {
		return false;
	}

	private String _getOrderByCol() {
		return _searchContainer.getOrderByCol();
	}

	private List<DropdownItem> _getOrderByDropdownItems() {
		return new DropdownItemList() {
			{
				final Map<String, String> orderColumns = new HashMap<>();

				orderColumns.put("lastPostDate", "last-post-date");
				orderColumns.put("name", "name");

				for (Map.Entry<String, String> orderByColEntry :
						orderColumns.entrySet()) {

					String orderByCol = orderByColEntry.getKey();

					add(
						SafeConsumer.ignore(
							dropdownItem -> {
								dropdownItem.setActive(
									orderByCol.equals(_getOrderByCol()));
								dropdownItem.setHref(
									_getPortletURL(), "orderByCol", orderByCol);
								dropdownItem.setLabel(
									LanguageUtil.get(
										_request, orderByColEntry.getValue()));
							}));
				}
			}
		};
	}

	private String _getOrderByType() {
		return _searchContainer.getOrderByType();
	}

	private PortletURL _getPortletURL() throws PortletException {
		PortletURL portletURL = PortletURLUtil.clone(
			_currentURLObj, _liferayPortletResponse);

		portletURL.setParameter("mvcRenderCommandName", "/wiki_admin/view");

		return portletURL;
	}

	private final PortletURL _currentURLObj;
	private final String _displayStyle;
	private final LiferayPortletRequest _liferayPortletRequest;
	private final LiferayPortletResponse _liferayPortletResponse;
	private final HttpServletRequest _request;
	private final SearchContainer _searchContainer;
	private final ThemeDisplay _themeDisplay;
	private final TrashHelper _trashHelper;

}