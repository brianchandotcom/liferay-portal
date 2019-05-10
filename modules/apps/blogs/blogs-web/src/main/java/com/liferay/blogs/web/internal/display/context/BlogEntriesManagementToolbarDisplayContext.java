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

package com.liferay.blogs.web.internal.display.context;

import com.liferay.blogs.web.internal.security.permission.resource.BlogsPermission;
import com.liferay.frontend.taglib.clay.servlet.taglib.display.context.SearchContainerManagementToolbarDisplayContext;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.CreationMenu;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItem;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItemList;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.LabelItem;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.LabelItemList;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.ViewTypeItem;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.ViewTypeItemList;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.trash.TrashHelper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import javax.portlet.ActionRequest;
import javax.portlet.PortletURL;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Sergio González
 */
public class BlogEntriesManagementToolbarDisplayContext
	extends SearchContainerManagementToolbarDisplayContext {

	public BlogEntriesManagementToolbarDisplayContext(
		LiferayPortletRequest liferayPortletRequest,
		LiferayPortletResponse liferayPortletResponse,
		HttpServletRequest httpServletRequest, SearchContainer searchContainer,
		TrashHelper trashHelper, String displayStyle) {

		super(
			liferayPortletRequest, liferayPortletResponse, httpServletRequest,
			searchContainer);

		_trashHelper = trashHelper;
		_displayStyle = displayStyle;

		_themeDisplay = (ThemeDisplay)httpServletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);
	}

	@Override
	public List<DropdownItem> getActionDropdownItems() {
		return new DropdownItemList() {
			{
				add(
					dropdownItem -> {
						dropdownItem.putData("action", "deleteEntries");

						boolean trashEnabled = _trashHelper.isTrashEnabled(
							_themeDisplay.getScopeGroupId());

						dropdownItem.setIcon(
							trashEnabled ? "trash" : "times-circle");

						String label = "delete";

						if (trashEnabled) {
							label = "move-to-recycle-bin";
						}

						dropdownItem.setLabel(LanguageUtil.get(request, label));

						dropdownItem.setQuickAction(true);
					});
			}
		};
	}

	@Override
	public String getClearResultsURL() {
		return getSearchActionURL();
	}

	public Map<String, Object> getComponentContext() throws PortalException {
		Map<String, Object> context = new HashMap<>();

		String cmd = Constants.DELETE;

		if (_trashHelper.isTrashEnabled(_themeDisplay.getScopeGroup())) {
			cmd = Constants.MOVE_TO_TRASH;
		}

		context.put("deleteEntriesCmd", cmd);

		PortletURL deleteEntriesURL = liferayPortletResponse.createActionURL();

		deleteEntriesURL.setParameter(
			ActionRequest.ACTION_NAME, "/blogs/edit_entry");

		context.put("deleteEntriesURL", deleteEntriesURL.toString());

		context.put(
			"trashEnabled",
			_trashHelper.isTrashEnabled(_themeDisplay.getScopeGroupId()));

		return context;
	}

	@Override
	public CreationMenu getCreationMenu() {
		if (!BlogsPermission.contains(
				_themeDisplay.getPermissionChecker(),
				_themeDisplay.getScopeGroupId(), ActionKeys.ADD_ENTRY)) {

			return null;
		}

		return new CreationMenu() {
			{
				addDropdownItem(
					dropdownItem -> {
						dropdownItem.setHref(
							liferayPortletResponse.createRenderURL(),
							"mvcRenderCommandName", "/blogs/edit_entry",
							"redirect", currentURLObj.toString());
						dropdownItem.setLabel(
							LanguageUtil.get(request, "add-blog-entry"));
					});
			}
		};
	}

	@Override
	public String getDefaultEventHandler() {
		return "BLOG_ENTRIES_MANAGEMENT_TOOLBAR_DEFAULT_EVENT_HANDLER";
	}

	@Override
	public List<LabelItem> getFilterLabelItems() {
		if (!Objects.equals(getNavigation(), "mine")) {
			return null;
		}

		return new LabelItemList() {
			{
				add(
					labelItem -> {
						PortletURL removeLabelURL = getPortletURL();

						removeLabelURL.setParameter(
							"entriesNavigation", (String)null);

						labelItem.putData(
							"removeLabelURL", removeLabelURL.toString());

						labelItem.setCloseable(true);

						User user = _themeDisplay.getUser();

						String label = String.format(
							"%s: %s", LanguageUtil.get(request, "owner"),
							user.getFullName());

						labelItem.setLabel(label);
					});
			}
		};
	}

	@Override
	public String getSearchActionURL() {
		PortletURL searchURL = liferayPortletResponse.createRenderURL();

		searchURL.setParameter("mvcRenderCommandName", "/blogs/view");

		String navigation = ParamUtil.getString(
			request, "navigation", "entries");

		searchURL.setParameter("navigation", navigation);

		searchURL.setParameter("orderByCol", getOrderByCol());
		searchURL.setParameter("orderByType", getOrderByType());

		return searchURL.toString();
	}

	@Override
	public List<ViewTypeItem> getViewTypeItems() {
		PortletURL portletURL = liferayPortletResponse.createRenderURL();

		portletURL.setParameter("mvcRenderCommandName", "/blogs/view");

		if (searchContainer.getDelta() > 0) {
			portletURL.setParameter(
				"delta", String.valueOf(searchContainer.getDelta()));
		}

		portletURL.setParameter("orderBycol", searchContainer.getOrderByCol());
		portletURL.setParameter(
			"orderByType", searchContainer.getOrderByType());

		portletURL.setParameter("entriesNavigation", getNavigation());

		if (searchContainer.getCur() > 0) {
			portletURL.setParameter(
				"cur", String.valueOf(searchContainer.getCur()));
		}

		return new ViewTypeItemList(portletURL, _displayStyle) {
			{
				addCardViewTypeItem();

				addListViewTypeItem();

				addTableViewTypeItem();
			}
		};
	}

	@Override
	protected String[] getNavigationKeys() {
		return new String[] {"all", "mine"};
	}

	@Override
	protected String getNavigationParam() {
		return "entriesNavigation";
	}

	@Override
	protected List<DropdownItem> getOrderByDropdownItems() {
		return new DropdownItemList() {
			{
				add(
					dropdownItem -> {
						dropdownItem.setActive(
							Objects.equals(getOrderByCol(), "title"));
						dropdownItem.setHref(
							_getCurrentSortingURL(), "orderByCol", "title");
						dropdownItem.setLabel(
							LanguageUtil.get(request, "title"));
					});

				add(
					dropdownItem -> {
						dropdownItem.setActive(
							Objects.equals(getOrderByCol(), "display-date"));
						dropdownItem.setHref(
							_getCurrentSortingURL(), "orderByCol",
							"display-date");
						dropdownItem.setLabel(
							LanguageUtil.get(request, "display-date"));
					});
			}
		};
	}

	private PortletURL _getCurrentSortingURL() {
		PortletURL sortingURL = getPortletURL();

		sortingURL.setParameter("mvcRenderCommandName", "/blogs/view");

		sortingURL.setParameter(SearchContainer.DEFAULT_CUR_PARAM, "0");

		String keywords = ParamUtil.getString(request, "keywords");

		if (Validator.isNotNull(keywords)) {
			sortingURL.setParameter("keywords", keywords);
		}

		return sortingURL;
	}

	private final String _displayStyle;
	private final ThemeDisplay _themeDisplay;
	private final TrashHelper _trashHelper;

}