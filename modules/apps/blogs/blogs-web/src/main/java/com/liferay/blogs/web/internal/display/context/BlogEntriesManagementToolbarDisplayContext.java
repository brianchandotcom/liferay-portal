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

import com.liferay.blogs.model.BlogsEntry;
import com.liferay.blogs.web.internal.security.permission.resource.BlogsPermission;
import com.liferay.frontend.taglib.clay.servlet.taglib.display.context.SearchContainerManagementToolbarDisplayContext;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.CreationMenu;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.CreationMenuBuilder;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItem;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItemListBuilder;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.LabelItem;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.LabelItemListBuilder;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.ViewTypeItem;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.ViewTypeItemList;
import com.liferay.petra.portlet.url.builder.PortletURLBuilder;
import com.liferay.petra.reflect.ReflectionUtil;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.trash.TrashHelper;

import java.util.List;
import java.util.Map;
import java.util.Objects;

import javax.portlet.PortletURL;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Sergio González
 */
public class BlogEntriesManagementToolbarDisplayContext
	extends SearchContainerManagementToolbarDisplayContext {

	public BlogEntriesManagementToolbarDisplayContext(
		HttpServletRequest httpServletRequest,
		LiferayPortletRequest liferayPortletRequest,
		LiferayPortletResponse liferayPortletResponse,
		SearchContainer<BlogsEntry> searchContainer, TrashHelper trashHelper,
		String displayStyle) {

		super(
			httpServletRequest, liferayPortletRequest, liferayPortletResponse,
			searchContainer);

		_trashHelper = trashHelper;
		_displayStyle = displayStyle;

		_themeDisplay = (ThemeDisplay)httpServletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);
	}

	@Override
	public List<DropdownItem> getActionDropdownItems() {
		return DropdownItemListBuilder.add(
			dropdownItem -> {
				dropdownItem.putData("action", "deleteEntries");

				boolean trashEnabled = _trashHelper.isTrashEnabled(
					_themeDisplay.getScopeGroupId());

				dropdownItem.setIcon(trashEnabled ? "trash" : "times-circle");

				String label = "delete";

				if (trashEnabled) {
					label = "move-to-recycle-bin";
				}

				dropdownItem.setLabel(
					LanguageUtil.get(httpServletRequest, label));

				dropdownItem.setQuickAction(true);
			}
		).build();
	}

	@Override
	public Map<String, Object> getAdditionalProps() {
		return HashMapBuilder.<String, Object>put(
			"deleteEntriesCmd",
			() -> {
				if (_isTrashEnabled()) {
					return Constants.MOVE_TO_TRASH;
				}

				return Constants.DELETE;
			}
		).put(
			"deleteEntriesURL",
			() -> PortletURLBuilder.createActionURL(
				liferayPortletResponse
			).setActionName(
				"/blogs/edit_entry"
			).buildString()
		).put(
			"trashEnabled", _isTrashEnabled()
		).build();
	}

	@Override
	public String getClearResultsURL() {
		return getSearchActionURL();
	}

	@Override
	public CreationMenu getCreationMenu() {
		if (!BlogsPermission.contains(
				_themeDisplay.getPermissionChecker(),
				_themeDisplay.getScopeGroupId(), ActionKeys.ADD_ENTRY)) {

			return null;
		}

		return CreationMenuBuilder.addDropdownItem(
			dropdownItem -> {
				dropdownItem.setHref(
					liferayPortletResponse.createRenderURL(),
					"mvcRenderCommandName", "/blogs/edit_entry", "redirect",
					currentURLObj.toString());
				dropdownItem.setLabel(
					LanguageUtil.get(httpServletRequest, "add-blog-entry"));
			}
		).build();
	}

	@Override
	public List<LabelItem> getFilterLabelItems() {
		if (!Objects.equals(getNavigation(), "mine")) {
			return null;
		}

		return LabelItemListBuilder.add(
			labelItem -> {
				labelItem.putData(
					"removeLabelURL",
					PortletURLBuilder.create(
						getPortletURL()
					).setParameter(
						"entriesNavigation", (String)null
					).buildString());

				labelItem.setCloseable(true);

				User user = _themeDisplay.getUser();

				String label = String.format(
					"%s: %s", LanguageUtil.get(httpServletRequest, "owner"),
					user.getFullName());

				labelItem.setLabel(label);
			}
		).build();
	}

	@Override
	public String getSearchActionURL() {
		String navigation = ParamUtil.getString(
			httpServletRequest, "navigation", "entries");

		return PortletURLBuilder.createRenderURL(
			liferayPortletResponse
		).setMVCRenderCommandName(
			"/blogs/search"
		).setNavigation(
			navigation
		).setParameter(
			"orderByCol",
			() -> {
				String mvcRenderCommandName = ParamUtil.getString(
					httpServletRequest, "mvcRenderCommandName");

				if (mvcRenderCommandName.equals("/blogs/search")) {
					return getOrderByCol();
				}

				return null;
			}
		).setParameter(
			"orderByType",
			() -> {
				String mvcRenderCommandName = ParamUtil.getString(
					httpServletRequest, "mvcRenderCommandName");

				if (mvcRenderCommandName.equals("/blogs/search")) {
					return getOrderByType();
				}

				return null;
			}
		).buildString();
	}

	@Override
	public List<ViewTypeItem> getViewTypeItems() {
		PortletURL portletURL = PortletURLBuilder.createRenderURL(
			liferayPortletResponse
		).setMVCRenderCommandName(
			"/blogs/view"
		).buildPortletURL();

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
		return DropdownItemListBuilder.add(
			dropdownItem -> {
				dropdownItem.setActive(
					Objects.equals(getOrderByCol(), "title"));
				dropdownItem.setHref(
					_getCurrentSortingURL(), "orderByCol", "title");
				dropdownItem.setLabel(
					LanguageUtil.get(httpServletRequest, "title"));
			}
		).add(
			dropdownItem -> {
				dropdownItem.setActive(
					Objects.equals(getOrderByCol(), "display-date"));
				dropdownItem.setHref(
					_getCurrentSortingURL(), "orderByCol", "display-date");
				dropdownItem.setLabel(
					LanguageUtil.get(httpServletRequest, "display-date"));
			}
		).add(
			this::_isSearch,
			dropdownItem -> {
				dropdownItem.setActive(
					Objects.equals(getOrderByCol(), "relevance"));
				dropdownItem.setHref(
					_getCurrentSortingURL(), "orderByCol", "relevance");
				dropdownItem.setLabel(
					LanguageUtil.get(httpServletRequest, "relevance"));
			}
		).build();
	}

	private PortletURL _getCurrentSortingURL() {
		return PortletURLBuilder.create(
			getPortletURL()
		).setMVCRenderCommandName(
			"/blogs/view"
		).setKeywords(
			() -> {
				if (_isSearch()) {
					return _getKeywords();
				}

				return null;
			}
		).setParameter(
			SearchContainer.DEFAULT_CUR_PARAM, "0"
		).buildPortletURL();
	}

	private String _getKeywords() {
		if (Validator.isNotNull(_keywords)) {
			return _keywords;
		}

		_keywords = ParamUtil.getString(httpServletRequest, "keywords");

		return _keywords;
	}

	private boolean _isSearch() {
		if (Validator.isNull(_getKeywords())) {
			return false;
		}

		return true;
	}

	private boolean _isTrashEnabled() {
		try {
			return _trashHelper.isTrashEnabled(
				PortalUtil.getScopeGroupId(httpServletRequest));
		}
		catch (PortalException portalException) {
			return ReflectionUtil.throwException(portalException);
		}
	}

	private final String _displayStyle;
	private String _keywords;
	private final ThemeDisplay _themeDisplay;
	private final TrashHelper _trashHelper;

}