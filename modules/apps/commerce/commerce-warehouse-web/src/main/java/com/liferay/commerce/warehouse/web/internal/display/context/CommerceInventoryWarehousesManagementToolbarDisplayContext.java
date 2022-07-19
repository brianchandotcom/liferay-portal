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

package com.liferay.commerce.warehouse.web.internal.display.context;

import com.liferay.frontend.taglib.clay.servlet.taglib.display.context.SearchContainerManagementToolbarDisplayContext;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.CreationMenu;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.CreationMenuBuilder;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItem;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItemList;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItemListBuilder;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.LabelItem;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.LabelItemListBuilder;
import com.liferay.petra.portlet.url.builder.PortletURLBuilder;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.model.Country;
import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;

import java.util.List;
import java.util.Objects;

import javax.portlet.PortletURL;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Eudaldo Alonso
 */
public class CommerceInventoryWarehousesManagementToolbarDisplayContext
	extends SearchContainerManagementToolbarDisplayContext {

	public CommerceInventoryWarehousesManagementToolbarDisplayContext(
			CommerceInventoryWarehousesDisplayContext
				commerceInventoryWarehousesDisplayContext,
			HttpServletRequest httpServletRequest,
			LiferayPortletRequest liferayPortletRequest,
			LiferayPortletResponse liferayPortletResponse)
		throws PortalException {

		super(
			httpServletRequest, liferayPortletRequest, liferayPortletResponse,
			commerceInventoryWarehousesDisplayContext.getSearchContainer());

		_commerceInventoryWarehousesDisplayContext =
			commerceInventoryWarehousesDisplayContext;

		_themeDisplay = (ThemeDisplay)httpServletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);
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
	public String getComponentId() {
		return "commerceInventoryWarehousesManagementToolbar";
	}

	@Override
	public CreationMenu getCreationMenu() {
		return CreationMenuBuilder.addDropdownItem(
			dropdownItem -> {
				dropdownItem.setHref(
					liferayPortletResponse.createRenderURL(),
					"mvcRenderCommandName",
					"/commerce_inventory_warehouse" +
						"/edit_commerce_inventory_warehouse",
					"redirect", currentURLObj.toString(), "countryId",
					_commerceInventoryWarehousesDisplayContext.
						getCountryTwoLettersIsoCode());
				dropdownItem.setLabel(
					LanguageUtil.get(httpServletRequest, "add-warehouse"));
			}
		).build();
	}

	@Override
	public List<DropdownItem> getFilterDropdownItems() {
		return DropdownItemListBuilder.addGroup(
			dropdownGroupItem -> {
				dropdownGroupItem.setDropdownItems(
					_getFilterNavigationDropdownItems());
				dropdownGroupItem.setLabel(
					LanguageUtil.get(
						httpServletRequest, "filter-by-navigation"));
			}
		).addGroup(
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
		return LabelItemListBuilder.add(
			() ->
				Validator.isNotNull(getNavigation()) &&
				!Objects.equals(getNavigation(), "all"),
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
					LanguageUtil.get(httpServletRequest, getNavigation()));
			}
		).add(
			() -> Validator.isNotNull(
				_commerceInventoryWarehousesDisplayContext.
					getCountryTwoLettersIsoCode()),
			labelItem -> {
				labelItem.putData(
					"removeLabelURL",
					PortletURLBuilder.create(
						getPortletURL()
					).setParameter(
						"countryTwoLettersISOCode", StringPool.BLANK
					).buildString());
				labelItem.setDismissible(true);
				labelItem.setLabel(
					String.format(
						"%s: %s",
						LanguageUtil.get(httpServletRequest, "country"),
						_getCountryName()));
			}
		).build();
	}

	@Override
	public String getSearchActionURL() {
		PortletURL searchActionURL = getPortletURL();

		return searchActionURL.toString();
	}

	@Override
	public String getSearchContainerId() {
		return "commerceInventoryWarehouses";
	}

	@Override
	public Boolean isSelectable() {
		return false;
	}

	@Override
	protected String[] getDisplayViews() {
		return new String[] {"list"};
	}

	private String _getCountryName() throws PortalException {
		String countryTwoLettersIsoCode =
			_commerceInventoryWarehousesDisplayContext.
				getCountryTwoLettersIsoCode();

		if (Validator.isNull(countryTwoLettersIsoCode)) {
			return StringPool.BLANK;
		}

		Country country = _commerceInventoryWarehousesDisplayContext.getCountry(
			countryTwoLettersIsoCode);

		if (country == null) {
			return StringPool.BLANK;
		}

		return country.getName(_themeDisplay.getLocale());
	}

	private List<DropdownItem> _getFilterCountryDropdownItems() {
		return new DropdownItemList() {
			{
				String countryTwoLettersIsoCode =
					_commerceInventoryWarehousesDisplayContext.
						getCountryTwoLettersIsoCode();

				add(
					dropdownItem -> {
						dropdownItem.setActive(
							Validator.isNull(countryTwoLettersIsoCode));
						dropdownItem.setHref(
							getPortletURL(), "countryTwoLettersISOCode",
							StringPool.BLANK);
						dropdownItem.setLabel(
							LanguageUtil.get(httpServletRequest, "all"));
					});

				for (Country country :
						_commerceInventoryWarehousesDisplayContext.
							getCountries()) {

					add(
						dropdownItem -> {
							dropdownItem.setActive(
								Validator.isNotNull(countryTwoLettersIsoCode) &&
								countryTwoLettersIsoCode.equals(
									country.getA2()));
							dropdownItem.setHref(
								getPortletURL(), "countryTwoLettersISOCode",
								country.getA2());
							dropdownItem.setLabel(
								country.getName(_themeDisplay.getLocale()));
						});
				}
			}
		};
	}

	private List<DropdownItem> _getFilterNavigationDropdownItems() {
		DropdownItemList navigationDropdownitems = new DropdownItemList();

		for (String navigation : new String[] {"all", "active", "inactive"}) {
			navigationDropdownitems.add(
				dropdownItem -> {
					dropdownItem.setActive(
						Objects.equals(getNavigation(), navigation));
					dropdownItem.setHref(
						getPortletURL(), "navigation", navigation);
					dropdownItem.setLabel(
						LanguageUtil.get(httpServletRequest, navigation));
				});
		}

		return navigationDropdownitems;
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

	private final CommerceInventoryWarehousesDisplayContext
		_commerceInventoryWarehousesDisplayContext;
	private final ThemeDisplay _themeDisplay;

}