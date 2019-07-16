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

package com.liferay.message.boards.web.internal.display.context;

import com.liferay.frontend.taglib.clay.servlet.taglib.util.CreationMenu;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItem;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItemList;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.LabelItem;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.LabelItemList;
import com.liferay.message.boards.constants.MBCategoryConstants;
import com.liferay.message.boards.constants.MBPortletKeys;
import com.liferay.message.boards.model.MBCategory;
import com.liferay.message.boards.model.MBMessage;
import com.liferay.message.boards.model.MBThread;
import com.liferay.message.boards.util.comparator.CategoryModifiedDateComparator;
import com.liferay.message.boards.util.comparator.CategoryTitleComparator;
import com.liferay.message.boards.util.comparator.MBObjectsComparator;
import com.liferay.message.boards.util.comparator.ThreadModifiedDateComparator;
import com.liferay.message.boards.util.comparator.ThreadTitleComparator;
import com.liferay.message.boards.web.internal.security.permission.MBCategoryPermission;
import com.liferay.message.boards.web.internal.security.permission.MBMessagePermission;
import com.liferay.message.boards.web.internal.util.MBUtil;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.portlet.PortalPreferences;
import com.liferay.portal.kernel.portlet.PortletPreferencesFactoryUtil;
import com.liferay.portal.kernel.portlet.PortletURLUtil;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.trash.TrashHelper;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.portlet.PortletException;
import javax.portlet.PortletURL;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Sergio González
 */
public class MBEntriesManagementToolbarDisplayContext {

	public MBEntriesManagementToolbarDisplayContext(
		LiferayPortletRequest liferayPortletRequest,
		LiferayPortletResponse liferayPortletResponse,
		HttpServletRequest httpServletRequest, PortletURL currentURLObj,
		TrashHelper trashHelper) {

		_liferayPortletRequest = liferayPortletRequest;
		_liferayPortletResponse = liferayPortletResponse;
		_httpServletRequest = httpServletRequest;
		_currentURLObj = currentURLObj;
		_trashHelper = trashHelper;

		_portalPreferences = PortletPreferencesFactoryUtil.getPortalPreferences(
			liferayPortletRequest);
		_themeDisplay = (ThemeDisplay)_httpServletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);
	}

	public List<DropdownItem> getActionDropdownItems() {
		return new DropdownItemList() {
			{
				ThemeDisplay themeDisplay =
					(ThemeDisplay)_httpServletRequest.getAttribute(
						WebKeys.THEME_DISPLAY);

				add(
					dropdownItem -> {
						dropdownItem.putData("action", "deleteEntries");

						boolean trashEnabled = _trashHelper.isTrashEnabled(
							themeDisplay.getScopeGroupId());

						dropdownItem.setIcon(
							trashEnabled ? "trash" : "times-circle");

						String label = "delete";

						if (trashEnabled) {
							label = "move-to-recycle-bin";
						}

						dropdownItem.setLabel(
							LanguageUtil.get(_httpServletRequest, label));

						dropdownItem.setQuickAction(true);
					});

				add(
					dropdownItem -> {
						dropdownItem.putData("action", "lockEntries");
						dropdownItem.setIcon("lock");
						dropdownItem.setLabel(
							LanguageUtil.get(_httpServletRequest, "lock"));

						dropdownItem.setQuickAction(true);
					});

				add(
					dropdownItem -> {
						dropdownItem.putData("action", "unlockEntries");
						dropdownItem.setIcon("unlock");
						dropdownItem.setLabel(
							LanguageUtil.get(_httpServletRequest, "unlock"));
						dropdownItem.setQuickAction(true);
					});
			}
		};
	}

	public List<String> getAvailableActions(MBCategory category)
		throws PortalException {

		List<String> availableActions = new ArrayList<>();

		if (MBCategoryPermission.contains(
				_themeDisplay.getPermissionChecker(), category,
				ActionKeys.DELETE)) {

			availableActions.add("deleteEntries");
		}

		return availableActions;
	}

	public List<String> getAvailableActions(MBMessage message)
		throws PortalException {

		List<String> availableActions = new ArrayList<>();

		PermissionChecker permissionChecker =
			_themeDisplay.getPermissionChecker();

		if (MBMessagePermission.contains(
				permissionChecker, message, ActionKeys.DELETE)) {

			availableActions.add("deleteEntries");
		}

		if (MBCategoryPermission.contains(
				permissionChecker, message.getGroupId(),
				message.getCategoryId(), ActionKeys.LOCK_THREAD)) {

			MBThread thread = message.getThread();

			if ((thread != null) && thread.isLocked()) {
				availableActions.add("unlockEntries");
			}
			else {
				availableActions.add("lockEntries");
			}
		}

		return availableActions;
	}

	public CreationMenu getCreationMenu() throws PortalException {
		CreationMenu creationMenu = null;

		MBCategory category = (MBCategory)_httpServletRequest.getAttribute(
			WebKeys.MESSAGE_BOARDS_CATEGORY);

		long categoryId = MBUtil.getCategoryId(_httpServletRequest, category);

		ThemeDisplay themeDisplay =
			(ThemeDisplay)_httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		if (MBCategoryPermission.contains(
				themeDisplay.getPermissionChecker(),
				themeDisplay.getScopeGroupId(), categoryId,
				ActionKeys.ADD_CATEGORY)) {

			if (creationMenu == null) {
				creationMenu = new CreationMenu();
			}

			creationMenu.addDropdownItem(
				dropdownItem -> {
					dropdownItem.setHref(
						_liferayPortletResponse.createRenderURL(),
						"mvcRenderCommandName", "/message_boards/edit_category",
						"redirect", _currentURLObj.toString(),
						"parentCategoryId", String.valueOf(categoryId));

					dropdownItem.setLabel(
						LanguageUtil.get(
							_httpServletRequest, "category[message-board]"));
				});
		}

		if (MBCategoryPermission.contains(
				themeDisplay.getPermissionChecker(),
				themeDisplay.getScopeGroupId(), categoryId,
				ActionKeys.ADD_MESSAGE)) {

			if (creationMenu == null) {
				creationMenu = new CreationMenu();
			}

			creationMenu.addDropdownItem(
				dropdownItem -> {
					dropdownItem.setHref(
						_liferayPortletResponse.createRenderURL(),
						"mvcRenderCommandName", "/message_boards/edit_message",
						"redirect", _currentURLObj.toString(), "mbCategoryId",
						String.valueOf(categoryId));
					dropdownItem.setLabel(
						LanguageUtil.get(_httpServletRequest, "thread"));
				});
		}

		return creationMenu;
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
								_httpServletRequest, "filter-by-navigation"));
					});

				String entriesNavigation = _getEntriesNavigation();

				if (!entriesNavigation.equals("all")) {
					addGroup(
						dropdownGroupItem -> {
							dropdownGroupItem.setDropdownItems(
								_getOrderByDropdownItems());
							dropdownGroupItem.setLabel(
								LanguageUtil.get(
									_httpServletRequest, "order-by"));
						});
				}
			}
		};
	}

	public List<LabelItem> getFilterLabelItems() {
		return new LabelItemList() {
			{
				String entriesNavigation = _getEntriesNavigation();

				if (entriesNavigation.equals("threads") ||
					entriesNavigation.equals("categories")) {

					add(
						labelItem -> {
							PortletURL removeLabelURL = PortletURLUtil.clone(
								_currentURLObj, _liferayPortletResponse);

							removeLabelURL.setParameter(
								"entriesNavigation", (String)null);

							labelItem.putData(
								"removeLabelURL", removeLabelURL.toString());

							labelItem.setCloseable(true);
							labelItem.setLabel(
								LanguageUtil.get(
									_httpServletRequest, entriesNavigation));
						});
				}
			}
		};
	}

	public String getOrderByCol() {
		if (_orderByCol != null) {
			return _orderByCol;
		}

		String orderByCol = ParamUtil.getString(
			_httpServletRequest, "orderByCol");

		if (Validator.isNotNull(orderByCol)) {
			_portalPreferences.setValue(
				MBPortletKeys.MESSAGE_BOARDS_ADMIN, "order-by-col", orderByCol);
		}
		else {
			orderByCol = _portalPreferences.getValue(
				MBPortletKeys.MESSAGE_BOARDS_ADMIN, "order-by-col",
				"modified-date");
		}

		_orderByCol = orderByCol;

		return _orderByCol;
	}

	public String getOrderByType() {
		if (_orderByType != null) {
			return _orderByType;
		}

		String orderByType = ParamUtil.getString(
			_httpServletRequest, "orderByType");

		if (Validator.isNotNull(orderByType)) {
			_portalPreferences.setValue(
				MBPortletKeys.MESSAGE_BOARDS_ADMIN, "order-by-type",
				orderByType);
		}
		else {
			orderByType = _portalPreferences.getValue(
				MBPortletKeys.MESSAGE_BOARDS_ADMIN, "order-by-type", "asc");
		}

		_orderByType = orderByType;

		return _orderByType;
	}

	public PortletURL getPortletURL() {
		MBCategory category = (MBCategory)_httpServletRequest.getAttribute(
			WebKeys.MESSAGE_BOARDS_CATEGORY);

		long categoryId = MBUtil.getCategoryId(_httpServletRequest, category);

		PortletURL portletURL = _liferayPortletResponse.createRenderURL();

		if (categoryId == MBCategoryConstants.DEFAULT_PARENT_CATEGORY_ID) {
			portletURL.setParameter(
				"mvcRenderCommandName", "/message_boards/view");
		}
		else {
			portletURL.setParameter(
				"mvcRenderCommandName", "/message_boards/view_category");
			portletURL.setParameter("mbCategoryId", String.valueOf(categoryId));
		}

		String keywords = ParamUtil.getString(_httpServletRequest, "keywords");

		if (Validator.isNotNull(keywords)) {
			portletURL.setParameter("keywords", keywords);
		}

		String orderByCol = getOrderByCol();

		if (Validator.isNotNull(orderByCol)) {
			portletURL.setParameter("orderByCol", orderByCol);
		}

		String orderByType = getOrderByType();

		if (Validator.isNotNull(orderByType)) {
			portletURL.setParameter("orderByType", orderByType);
		}

		return portletURL;
	}

	public String getSearchActionURL() {
		PortletURL searchURL = _liferayPortletResponse.createRenderURL();

		searchURL.setParameter(
			"mvcRenderCommandName", "/message_boards_admin/search");
		searchURL.setParameter("redirect", _currentURLObj.toString());

		MBCategory category = (MBCategory)_httpServletRequest.getAttribute(
			WebKeys.MESSAGE_BOARDS_CATEGORY);

		long categoryId = MBUtil.getCategoryId(_httpServletRequest, category);

		searchURL.setParameter(
			"breadcrumbsCategoryId", String.valueOf(categoryId));
		searchURL.setParameter("searchCategoryId", String.valueOf(categoryId));

		return searchURL.toString();
	}

	public PortletURL getSortingURL() throws PortletException {
		PortletURL sortingURL = _getCurrentSortingURL();

		sortingURL.setParameter(
			"orderByType",
			Objects.equals(getOrderByType(), "asc") ? "desc" : "asc");

		return sortingURL;
	}

	public void populateOrder(SearchContainer searchContainer) {
		OrderByComparator orderByComparator = null;

		String orderByCol = getOrderByCol();

		boolean orderByAsc = false;

		String orderByType = getOrderByType();

		if (orderByType.equals("asc")) {
			orderByAsc = true;
		}

		String entriesNavigation = _getEntriesNavigation();

		if (entriesNavigation.equals("all")) {
			orderByComparator = new MBObjectsComparator(orderByAsc);
		}
		else if (entriesNavigation.equals("threads")) {
			if (orderByCol.equals("modified-date")) {
				orderByComparator = new ThreadModifiedDateComparator(
					orderByAsc);
			}
			else if (orderByCol.equals("title")) {
				orderByComparator = new ThreadTitleComparator<>(orderByAsc);
			}
		}
		else if (entriesNavigation.equals("categories")) {
			if (orderByCol.equals("modified-date")) {
				orderByComparator = new CategoryModifiedDateComparator(
					orderByAsc);
			}
			else if (orderByCol.equals("title")) {
				orderByComparator = new CategoryTitleComparator<>(orderByAsc);
			}
		}

		searchContainer.setOrderByCol(orderByCol);
		searchContainer.setOrderByComparator(orderByComparator);
		searchContainer.setOrderByType(orderByType);
	}

	private PortletURL _getCurrentSortingURL() throws PortletException {
		PortletURL sortingURL = PortletURLUtil.clone(
			_currentURLObj, _liferayPortletResponse);

		MBCategory category = (MBCategory)_httpServletRequest.getAttribute(
			WebKeys.MESSAGE_BOARDS_CATEGORY);

		long categoryId = MBUtil.getCategoryId(_httpServletRequest, category);

		if (categoryId == MBCategoryConstants.DEFAULT_PARENT_CATEGORY_ID) {
			sortingURL.setParameter(
				"mvcRenderCommandName", "/message_boards/view");
		}
		else {
			sortingURL.setParameter(
				"mvcRenderCommandName", "/message_boards/view_category");
			sortingURL.setParameter("mbCategoryId", String.valueOf(categoryId));
		}

		sortingURL.setParameter(SearchContainer.DEFAULT_CUR_PARAM, "0");

		String keywords = ParamUtil.getString(_httpServletRequest, "keywords");

		if (Validator.isNotNull(keywords)) {
			sortingURL.setParameter("keywords", keywords);
		}

		return sortingURL;
	}

	private String _getEntriesNavigation() {
		return ParamUtil.getString(
			_httpServletRequest, "entriesNavigation", "all");
	}

	private List<DropdownItem> _getFilterNavigationDropdownItems() {
		String entriesNavigation = _getEntriesNavigation();

		return new DropdownItemList() {
			{
				add(
					dropdownItem -> {
						dropdownItem.setActive(entriesNavigation.equals("all"));

						PortletURL navigationPortletURL = PortletURLUtil.clone(
							_currentURLObj, _liferayPortletResponse);

						dropdownItem.setHref(
							navigationPortletURL, "entriesNavigation", "all");

						dropdownItem.setLabel(
							LanguageUtil.get(_httpServletRequest, "all"));
					});

				add(
					dropdownItem -> {
						dropdownItem.setActive(
							entriesNavigation.equals("threads"));

						PortletURL navigationPortletURL = PortletURLUtil.clone(
							_currentURLObj, _liferayPortletResponse);

						dropdownItem.setHref(
							navigationPortletURL, "entriesNavigation",
							"threads");

						dropdownItem.setLabel(
							LanguageUtil.get(_httpServletRequest, "threads"));
					});

				add(
					dropdownItem -> {
						dropdownItem.setActive(
							entriesNavigation.equals("categories"));

						PortletURL navigationPortletURL = PortletURLUtil.clone(
							_currentURLObj, _liferayPortletResponse);

						dropdownItem.setHref(
							navigationPortletURL, "entriesNavigation",
							"categories");

						dropdownItem.setLabel(
							LanguageUtil.get(
								_httpServletRequest, "categories"));
					});
			}
		};
	}

	private List<DropdownItem> _getOrderByDropdownItems() {
		return new DropdownItemList() {
			{
				add(
					dropdownItem -> {
						dropdownItem.setActive(
							Objects.equals(getOrderByCol(), "title"));
						dropdownItem.setHref(
							_getCurrentSortingURL(), "orderByCol", "title");
						dropdownItem.setLabel(
							LanguageUtil.get(_httpServletRequest, "title"));
					});

				add(
					dropdownItem -> {
						dropdownItem.setActive(
							Objects.equals(getOrderByCol(), "modified-date"));
						dropdownItem.setHref(
							_getCurrentSortingURL(), "orderByCol",
							"modified-date");
						dropdownItem.setLabel(
							LanguageUtil.get(
								_httpServletRequest, "modified-date"));
					});
			}
		};
	}

	private final PortletURL _currentURLObj;
	private final HttpServletRequest _httpServletRequest;
	private final LiferayPortletRequest _liferayPortletRequest;
	private final LiferayPortletResponse _liferayPortletResponse;
	private String _orderByCol;
	private String _orderByType;
	private final PortalPreferences _portalPreferences;
	private final ThemeDisplay _themeDisplay;
	private final TrashHelper _trashHelper;

}