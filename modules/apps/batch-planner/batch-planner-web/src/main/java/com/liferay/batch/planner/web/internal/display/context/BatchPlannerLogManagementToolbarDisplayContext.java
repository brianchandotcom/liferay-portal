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

package com.liferay.batch.planner.web.internal.display.context;

import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItem;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItemList;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItemListBuilder;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.LabelItem;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.LabelItemListBuilder;
import com.liferay.petra.portlet.url.builder.PortletURLBuilder;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.portlet.PortletURLUtil;
import com.liferay.portal.kernel.util.ParamUtil;

import java.util.List;
import java.util.Objects;

import javax.portlet.PortletURL;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Matija Petanjek
 */
public class BatchPlannerLogManagementToolbarDisplayContext
	extends BaseSearchContainerManagementToolbarDisplayContext {

	public BatchPlannerLogManagementToolbarDisplayContext(
		HttpServletRequest httpServletRequest,
		LiferayPortletRequest liferayPortletRequest,
		LiferayPortletResponse liferayPortletResponse,
		SearchContainer<?> searchContainer) {

		super(
			httpServletRequest, liferayPortletRequest, liferayPortletResponse,
			searchContainer);
	}

	@Override
	public List<DropdownItem> getFilterDropdownItems() {
		List<DropdownItem> searchByDropdownItems = _getSearchByDropdownItems();

		DropdownItemList filterDropdownItems = DropdownItemListBuilder.addAll(
			super.getFilterDropdownItems()
		).addGroup(
			() -> searchByDropdownItems != null,
			dropdownGroupItem -> {
				dropdownGroupItem.setDropdownItems(searchByDropdownItems);
				dropdownGroupItem.setLabel(_getSearchByDropdownItemsLabel());
			}
		).build();

		if (filterDropdownItems.isEmpty()) {
			return null;
		}

		return filterDropdownItems;
	}

	@Override
	public List<LabelItem> getFilterLabelItems() {
		return LabelItemListBuilder.add(
			() -> !Objects.equals(getNavigation(), "all"),
			labelItem -> {
				labelItem.putData(
					"removeLabelURL",
					PortletURLBuilder.create(
						getPortletURL()
					).setNavigation(
						(String)null
					).buildString());

				labelItem.setDismissible(true);

				labelItem.setLabel(
					String.format(
						"%s: %s",
						LanguageUtil.get(httpServletRequest, "action"),
						LanguageUtil.get(httpServletRequest, getNavigation())));
			}
		).build();
	}

	@Override
	public String getFilterNavigationDropdownItemsLabel() {
		return LanguageUtil.get(httpServletRequest, "filter-by-action");
	}

	@Override
	public String getSearchActionURL() {
		PortletURL searchActionURL = getPortletURL();

		return searchActionURL.toString();
	}

	@Override
	public Boolean isDisabled() {
		return false;
	}

	@Override
	public Boolean isSelectable() {
		return false;
	}

	@Override
	protected String getNavigation() {
		return ParamUtil.getString(
			liferayPortletRequest, getNavigationParam(), "all");
	}

	@Override
	protected String[] getNavigationKeys() {
		return new String[] {"all", "export", "import"};
	}

	@Override
	protected String[] getOrderByKeys() {
		return new String[] {"createDate"};
	}

	private List<DropdownItem> _getSearchByDropdownItems() {
		return DropdownItemListBuilder.add(
			dropdownItem -> {
				dropdownItem.setActive(
					Objects.equals(_getSearchByField(), "title"));

				dropdownItem.setLabel(
					LanguageUtil.get(httpServletRequest, "title"));

				dropdownItem.setHref(
					PortletURLUtil.clone(currentURLObj, liferayPortletResponse),
					"searchByField", "title");
			}
		).add(
			dropdownItem -> {
				dropdownItem.setActive(
					Objects.equals(_getSearchByField(), "entity"));

				dropdownItem.setLabel(
					LanguageUtil.get(httpServletRequest, "entity"));

				dropdownItem.setHref(
					PortletURLUtil.clone(currentURLObj, liferayPortletResponse),
					"searchByField", "entity");
			}
		).build();
	}

	private String _getSearchByDropdownItemsLabel() {
		return LanguageUtil.get(httpServletRequest, "search-by");
	}

	private String _getSearchByField() {
		return ParamUtil.getString(
			liferayPortletRequest, "searchByField", "title");
	}

}