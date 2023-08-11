/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.item.selector.web.internal.display.context;

import com.liferay.frontend.taglib.clay.servlet.taglib.display.context.SearchContainerManagementToolbarDisplayContext;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItem;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItemList;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItemListBuilder;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.LabelItem;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.LabelItemListBuilder;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.feature.flag.FeatureFlagManagerUtil;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.model.Country;
import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.portlet.url.builder.PortletURLBuilder;
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
			() -> !FeatureFlagManagerUtil.isEnabled("LPS-144527"),
			dropdownGroupItem -> {
				dropdownGroupItem.setDropdownItems(getOrderByDropdownItems());
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

	public List<DropdownItem> getOrderByDropdownItems() {
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

	private final CommerceInventoryWarehouseItemSelectorViewDisplayContext
		_commerceInventoryWarehouseItemSelectorViewDisplayContext;
	private final ThemeDisplay _themeDisplay;

}