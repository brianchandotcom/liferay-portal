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

import com.liferay.batch.planner.model.BatchPlannerPlanTable;
import com.liferay.frontend.taglib.clay.servlet.taglib.display.context.SearchContainerManagementToolbarDisplayContext;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.CreationMenu;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.CreationMenuBuilder;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItem;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItemBuilder;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItemList;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItemListBuilder;
import com.liferay.petra.portlet.url.builder.PortletURLBuilder;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PortalUtil;

import java.util.List;
import java.util.Objects;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Matija Petanjek
 */
public abstract class BaseSearchContainerManagementToolbarDisplayContext
	extends SearchContainerManagementToolbarDisplayContext {

	public BaseSearchContainerManagementToolbarDisplayContext(
		HttpServletRequest httpServletRequest,
		LiferayPortletRequest liferayPortletRequest,
		LiferayPortletResponse liferayPortletResponse,
		SearchContainer<?> searchContainer) {

		super(
			httpServletRequest, liferayPortletRequest, liferayPortletResponse,
			searchContainer);
	}

	@Override
	public String getClearResultsURL() {
		return PortletURLBuilder.create(
			getPortletURL()
		).setKeywords(
			StringPool.BLANK
		).setNavigation(
			"all"
		).setParameter(
			"searchByField", "name"
		).buildString();
	}

	@Override
	public CreationMenu getCreationMenu() {
		return CreationMenuBuilder.addDropdownItem(
			dropdownItem -> {
				dropdownItem.setHref(
					PortletURLBuilder.createRenderURL(
						liferayPortletResponse
					).setMVCRenderCommandName(
						"/batch_planner/edit_export_batch_planner_plan"
					).setBackURL(
						PortalUtil.getCurrentURL(httpServletRequest)
					).setNavigation(
						"export"
					).buildPortletURL());
				dropdownItem.setLabel(
					LanguageUtil.get(httpServletRequest, "export-file"));
			}
		).addDropdownItem(
			dropdownItem -> {
				dropdownItem.setHref(
					PortletURLBuilder.createRenderURL(
						liferayPortletResponse
					).setMVCRenderCommandName(
						"/batch_planner/edit_import_batch_planner_plan"
					).setBackURL(
						PortalUtil.getCurrentURL(httpServletRequest)
					).setNavigation(
						"import"
					).buildPortletURL());
				dropdownItem.setLabel(
					LanguageUtil.get(httpServletRequest, "import-file"));
			}
		).build();
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

	private DropdownItem _getSearchByDropdownItem(
		String fieldName, String label) {

		return DropdownItemBuilder.setActive(
			Objects.equals(_getSearchByField(), fieldName)
		).setHref(
			getPortletURL(), "searchByField", fieldName
		).setLabel(
			LanguageUtil.get(httpServletRequest, label)
		).build();
	}

	private List<DropdownItem> _getSearchByDropdownItems() {
		return DropdownItemListBuilder.add(
			_getSearchByDropdownItem(
				BatchPlannerPlanTable.INSTANCE.name.getName(), "name")
		).add(
			_getSearchByDropdownItem(
				BatchPlannerPlanTable.INSTANCE.internalClassName.getName(),
				"entity")
		).build();
	}

	private String _getSearchByDropdownItemsLabel() {
		return LanguageUtil.get(httpServletRequest, "search-by");
	}

	private String _getSearchByField() {
		return ParamUtil.getString(
			liferayPortletRequest, "searchByField",
			BatchPlannerPlanTable.INSTANCE.name.getName());
	}

}