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

package com.liferay.commerce.item.selector.web.internal.display.context;

import com.liferay.frontend.taglib.clay.servlet.taglib.display.context.SearchContainerManagementToolbarDisplayContext;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItem;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItemList;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItemListBuilder;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.LabelItem;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.LabelItemListBuilder;
import com.liferay.petra.portlet.url.builder.PortletURLBuilder;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.model.Country;
import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.WebKeys;

import java.util.List;
import java.util.Objects;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Eudaldo Alonso
 */
public class CommerceInventoryWarehouseManagementToolbarDisplayContext
	extends SearchContainerManagementToolbarDisplayContext {

	public CommerceInventoryWarehouseManagementToolbarDisplayContext(
			CommerceInventoryWarehouseItemSelectorViewDisplayContext
				commerceInventoryWarehouseItemSelectorViewDisplayContext,
			HttpServletRequest httpServletRequest,
			LiferayPortletRequest liferayPortletRequest,
			LiferayPortletResponse liferayPortletResponse)
		throws PortalException {

		super(
			httpServletRequest, liferayPortletRequest, liferayPortletResponse,
			commerceInventoryWarehouseItemSelectorViewDisplayContext.
				getSearchContainer());

		_commerceInventoryWarehouseItemSelectorViewDisplayContext =
			commerceInventoryWarehouseItemSelectorViewDisplayContext;

		_themeDisplay = (ThemeDisplay)httpServletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);
	}

	@Override
	public String getComponentId() {
		return "commerceInventoryWarehousesManagementToolbar";
	}

	@Override
	public List<DropdownItem> getFilterDropdownItems() {
		return DropdownItemListBuilder.addGroup(
			dropdownGroupItem -> {
				dropdownGroupItem.setDropdownItems(
					_getFilterCountryDropdownItems());
				dropdownGroupItem.setLabel(
					LanguageUtil.get(httpServletRequest, "filter-by-country"));
			}
		).addGroup(
			dropdownGroupItem -> {
				dropdownGroupItem.setDropdownItems(_getOrderByDropdownItems());
				dropdownGroupItem.setLabel(
					LanguageUtil.get(httpServletRequest, "order-by"));
			}
		).build();
	}

	@Override
	public List<LabelItem> getFilterLabelItems() {
		long countryId =
			_commerceInventoryWarehouseItemSelectorViewDisplayContext.
				getCountryId();

		return LabelItemListBuilder.add(
			() -> countryId > 0,
			labelItem -> {
				labelItem.putData(
					"removeLabelURL",
					PortletURLBuilder.create(
						getPortletURL()
					).setParameter(
						"countryId", -1
					).buildString());
				labelItem.setDismissible(true);
				labelItem.setLabel(
					String.format(
						"%s: %s",
						LanguageUtil.get(httpServletRequest, "country"),
						_commerceInventoryWarehouseItemSelectorViewDisplayContext.
							getCountryName()));
			}
		).build();
	}

	@Override
	public String getSearchContainerId() {
		return "commerceInventoryWarehouses";
	}

	@Override
	protected String[] getDisplayViews() {
		return new String[] {"list"};
	}

	private List<DropdownItem> _getFilterCountryDropdownItems() {
		return new DropdownItemList() {
			{
				long countryId =
					_commerceInventoryWarehouseItemSelectorViewDisplayContext.
						getCountryId();

				add(
					dropdownItem -> {
						dropdownItem.setActive(countryId == -1);
						dropdownItem.setHref(getPortletURL(), "countryId", -1);
						dropdownItem.setLabel(
							LanguageUtil.get(httpServletRequest, "all"));
					});

				for (Country country :
						_commerceInventoryWarehouseItemSelectorViewDisplayContext.
							getCountries()) {

					add(
						dropdownItem -> {
							dropdownItem.setActive(
								countryId == country.getCountryId());
							dropdownItem.setHref(
								getPortletURL(), "countryId",
								country.getCountryId());
							dropdownItem.setLabel(
								country.getName(_themeDisplay.getLocale()));
						});
				}
			}
		};
	}

	private List<DropdownItem> _getOrderByDropdownItems() {
		return DropdownItemListBuilder.add(
			dropdownItem -> {
				dropdownItem.setActive(Objects.equals(getOrderByCol(), "city"));
				dropdownItem.setHref(getPortletURL(), "orderByCol", "city");
				dropdownItem.setLabel(
					LanguageUtil.get(httpServletRequest, "city"));
			}
		).add(
			dropdownItem -> {
				dropdownItem.setActive(Objects.equals(getOrderByCol(), "name"));
				dropdownItem.setHref(getPortletURL(), "orderByCol", "name");
				dropdownItem.setLabel(
					LanguageUtil.get(httpServletRequest, "name"));
			}
		).build();
	}

	private final CommerceInventoryWarehouseItemSelectorViewDisplayContext
		_commerceInventoryWarehouseItemSelectorViewDisplayContext;
	private final ThemeDisplay _themeDisplay;

}