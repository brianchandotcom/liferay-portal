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

package com.liferay.trash.web.internal.display.context;

import com.liferay.frontend.taglib.clay.servlet.taglib.display.context.SearchContainerManagementToolbarDisplayContext;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItem;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItemList;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.ViewTypeItem;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.security.permission.ResourceActionsUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.trash.TrashHandler;
import com.liferay.portal.kernel.trash.TrashHandlerRegistryUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;

import java.util.List;
import java.util.Objects;

import javax.portlet.PortletURL;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Pavel Savinov
 */
public class TrashManagementToolbarDisplayContext
	extends SearchContainerManagementToolbarDisplayContext {

	public TrashManagementToolbarDisplayContext(
			LiferayPortletRequest liferayPortletRequest,
			LiferayPortletResponse liferayPortletResponse,
			HttpServletRequest request, TrashDisplayContext trashDisplayContext)
		throws PortalException {

		super(
			liferayPortletRequest, liferayPortletResponse, request,
			trashDisplayContext.getEntrySearch());

		_trashDisplayContext = trashDisplayContext;
	}

	@Override
	public List<DropdownItem> getActionDropdownItems() {
		return new DropdownItemList() {
			{
				add(
					dropdownItem -> {
						dropdownItem.putData("action", "deleteSelectedEntries");
						dropdownItem.setIcon("times-circle");
						dropdownItem.setLabel(
							LanguageUtil.get(request, "delete"));
						dropdownItem.setQuickAction(true);
					});
			}
		};
	}

	@Override
	public String getClearResultsURL() {
		PortletURL clearResultsURL = getPortletURL();

		clearResultsURL.setParameter("keywords", StringPool.BLANK);

		return clearResultsURL.toString();
	}

	@Override
	public String getComponentId() {
		return "trashWebManagementToolbar";
	}

	@Override
	public String getDefaultEventHandler() {
		return "TRASH_ENTRIES_MANAGEMENT_TOOLBAR_DEFAULT_EVENT_HANDLER";
	}

	@Override
	public List<DropdownItem> getFilterDropdownItems() {
		return new DropdownItemList() {
			{
				addGroup(
					dropdownGroupItem -> {
						dropdownGroupItem.setDropdownItems(
							_getFilterNavigationDropdownItems());
						dropdownGroupItem.setLabel(
							LanguageUtil.get(request, "filter-by-navigation"));
					});

				addGroup(
					dropdownGroupItem -> {
						dropdownGroupItem.setDropdownItems(
							_getOrderByDropdownItems());
						dropdownGroupItem.setLabel(
							LanguageUtil.get(request, "order-by"));
					});
			}
		};
	}

	@Override
	public String getInfoPanelId() {
		return "infoPanelId";
	}

	@Override
	public String getSearchActionURL() {
		PortletURL searchActionURL = getPortletURL();

		return searchActionURL.toString();
	}

	@Override
	public String getSearchContainerId() {
		return "trash";
	}

	@Override
	public String getSortingURL() {
		PortletURL sortingURL = getPortletURL();

		sortingURL.setParameter(
			"orderByType",
			Objects.equals(getOrderByType(), "asc") ? "desc" : "asc");

		return sortingURL.toString();
	}

	@Override
	public List<ViewTypeItem> getViewTypeItems() {
		return _trashDisplayContext.getViewTypeItems();
	}

	@Override
	public Boolean isDisabled() {
		if (searchContainer.getTotal() > 0) {
			return false;
		}

		if (Validator.isNotNull(_trashDisplayContext.getKeywords())) {
			return false;
		}

		return true;
	}

	@Override
	public Boolean isShowInfoButton() {
		return true;
	}

	@Override
	protected String getOrderByCol() {
		return _trashDisplayContext.getOrderByCol();
	}

	@Override
	protected String getOrderByType() {
		return _trashDisplayContext.getOrderByType();
	}

	private List<DropdownItem> _getFilterNavigationDropdownItems() {
		ThemeDisplay themeDisplay = (ThemeDisplay)request.getAttribute(
			WebKeys.THEME_DISPLAY);

		return new DropdownItemList() {
			{
				add(
					dropdownItem -> {
						dropdownItem.setActive(
							Objects.equals(getNavigation(), "all"));
						dropdownItem.setHref(
							getPortletURL(), "navigation", "all");
						dropdownItem.setLabel(LanguageUtil.get(request, "all"));
					});

				for (TrashHandler trashHandler :
						TrashHandlerRegistryUtil.getTrashHandlers()) {

					add(
						dropdownItem -> {
							dropdownItem.setActive(
								Objects.equals(
									getNavigation(),
									trashHandler.getClassName()));
							dropdownItem.setHref(
								getPortletURL(), "navigation",
								trashHandler.getClassName());
							dropdownItem.setLabel(
								ResourceActionsUtil.getModelResource(
									themeDisplay.getLocale(),
									trashHandler.getClassName()));
						});
				}
			}
		};
	}

	private List<DropdownItem> _getOrderByDropdownItems() {
		return new DropdownItemList() {
			{
				add(
					dropdownItem -> {
						dropdownItem.setActive(
							Objects.equals(getOrderByCol(), "removed-date"));
						dropdownItem.setHref(
							getPortletURL(), "orderByCol", "removed-date");
						dropdownItem.setLabel(
							LanguageUtil.get(request, "removed-date"));
					});
			}
		};
	}

	private final TrashDisplayContext _trashDisplayContext;

}