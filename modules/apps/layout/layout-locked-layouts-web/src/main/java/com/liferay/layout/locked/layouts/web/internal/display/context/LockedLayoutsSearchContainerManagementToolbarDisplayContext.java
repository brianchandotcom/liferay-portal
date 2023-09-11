/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.layout.locked.layouts.web.internal.display.context;

import com.liferay.frontend.taglib.clay.servlet.taglib.display.context.SearchContainerManagementToolbarDisplayContext;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItem;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItemBuilder;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItemList;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItemListBuilder;
import com.liferay.layout.constants.LockedLayoutType;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.portlet.url.builder.PortletURLBuilder;
import com.liferay.portal.kernel.util.ListUtil;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Lourdes Fernández Besada
 */
public class LockedLayoutsSearchContainerManagementToolbarDisplayContext
	extends SearchContainerManagementToolbarDisplayContext {

	public LockedLayoutsSearchContainerManagementToolbarDisplayContext(
		HttpServletRequest httpServletRequest,
		LiferayPortletRequest liferayPortletRequest,
		LiferayPortletResponse liferayPortletResponse,
		LockedLayoutsDisplayContext lockedLayoutsDisplayContext) {

		super(
			httpServletRequest, liferayPortletRequest, liferayPortletResponse,
			lockedLayoutsDisplayContext.getSearchContainer());
	}

	@Override
	public List<DropdownItem> getActionDropdownItems() {
		return DropdownItemListBuilder.add(
			dropdownItem -> {
				dropdownItem.putData("action", "unlockLockedLayouts");
				dropdownItem.setIcon("unlock");
				dropdownItem.setLabel(
					LanguageUtil.get(httpServletRequest, "unlock"));
				dropdownItem.setQuickAction(true);
			}
		).build();
	}

	@Override
	public String getClearResultsURL() {
		return PortletURLBuilder.create(
			getPortletURL()
		).setKeywords(
			StringPool.BLANK
		).buildString();
	}

	@Override
	public List<DropdownItem> getFilterDropdownItems() {
		DropdownItemList dropdownItemList = DropdownItemListBuilder.addGroup(
			dropdownGroupItem -> {
				dropdownGroupItem.setDropdownItems(_getFilterDropdownItems());
				dropdownGroupItem.setLabel(
					LanguageUtil.get(httpServletRequest, "filter-by-type"));
			}
		).build();

		List<DropdownItem> filterDropdownItems = super.getFilterDropdownItems();

		if (ListUtil.isNotEmpty(filterDropdownItems)) {
			dropdownItemList.addAll(filterDropdownItems);
		}

		return dropdownItemList;
	}

	@Override
	public String getSearchContainerId() {
		return "lockedLayoutsSearchContainer";
	}

	@Override
	public String getSortingURL() {
		return null;
	}

	private List<DropdownItem> _getFilterDropdownItems() {
		List<DropdownItem> dropdownItems = DropdownItemListBuilder.add(
			dropdownItem -> {
				dropdownItem.setHref(getPortletURL(), "type", StringPool.BLANK);
				dropdownItem.setLabel(
					LanguageUtil.get(httpServletRequest, "all"));
			}
		).build();

		for (LockedLayoutType lockedLayoutType : LockedLayoutType.values()) {
			dropdownItems.add(
				DropdownItemBuilder.setHref(
					getPortletURL(), "type", lockedLayoutType.getValue()
				).setLabel(
					LanguageUtil.get(
						httpServletRequest, lockedLayoutType.getValue())
				).build());
		}

		return dropdownItems;
	}

}